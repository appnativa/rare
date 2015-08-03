/*
 * @(#)OutlineContentProvider.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import java.util.ArrayList;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.appnativa.studio.DesignPane;
import com.appnativa.studio.Studio;
import com.appnativa.studio.RMLDocument;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTNode;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.iSPOTElement;

public class OutlineContentProvider implements ITreeContentProvider {
  protected final static String CLASS_POSITIONS = "__class_positions";
  Node                          rootElement;
  protected IPositionUpdater    positionUpdater = new DefaultPositionUpdater(CLASS_POSITIONS);
  private int                   cNextPos;
  private IDocumentProvider     documentProvider;
  private IEditorInput          editorInput;
  private int                   fRangeEnd;

  public OutlineContentProvider(IDocumentProvider documentProvider) {
    this.documentProvider = documentProvider;
  }

  @Override
  public void dispose() {
    if (rootElement != null) {
      rootElement.clear();
    }
  }

  public void editorPageChanged(Viewer viewer) {
    if (editorInput != null) {
      IDocument document = documentProvider.getDocument(editorInput);

      if (document != null) {
        RMLDocument d = Studio.getSelectedDocument();
        DesignPane pane=d==null ? null : d.getDesignPane();

        if (pane == null || pane.getRootWidget()==null) {
          rootElement = parseNodes(document);
        } else {
          rootElement = new Node(pane.getRootWidget());
        }
        viewer.refresh();
      }
    }
  }

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    if (oldInput != null) {
      IDocument document = documentProvider.getDocument(oldInput);

      if (document != null) {
        try {
          document.removePositionCategory(CLASS_POSITIONS);
        } catch(BadPositionCategoryException x) {}

        document.removePositionUpdater(positionUpdater);
      }
    }

    editorInput = (IEditorInput) newInput;

    if (newInput != null) {
      IDocument document = documentProvider.getDocument(newInput);

      if (document != null) {
        document.addPositionCategory(CLASS_POSITIONS);
        document.addPositionUpdater(positionUpdater);
        editorPageChanged(viewer);
      }
    }

    viewer.refresh();
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    if (parentElement == editorInput) {
      if (rootElement == null) {
        return new Object[0];
      }

      return new Object[] { rootElement };
    } else {
      Node node = (Node) parentElement;

      if (node.children == null) {
        return new Object[0];
      }

      return node.children.toArray();
    }
  }

  @Override
  public Object[] getElements(Object inputElement) {
    if (rootElement == null) {
      return new Object[0];
    }

    return new Object[] { rootElement };
  }

  @Override
  public Object getParent(Object element) {
    if (element == editorInput) {
      return null;
    }

    return ((Node) element).parent;
  }

  public String getText(Node node) throws BadLocationException {
    IDocument document = (editorInput == null)
                         ? null
                         : documentProvider.getDocument(editorInput);

    if ((document == null) || (node.position == null)) {
      return null;
    }

    int len    = document.getLength();
    int offset = node.position.offset;
    int length = len - offset;

    if (length > 0) {
      return document.get(offset, length);
    }

    return null;
  }

  @Override
  public boolean hasChildren(Object element) {
    if (element == editorInput) {
      return true;
    }

    return ((Node) element).children != null;
  }

  protected void addPosition(IDocument doc, Position position) {
    try {
      doc.addPosition(CLASS_POSITIONS, position);
    } catch(BadLocationException e) {
      e.printStackTrace();
    } catch(BadPositionCategoryException e) {
      e.printStackTrace();
    }
  }

  protected void recursiveTokens(Node parent, int depth, IDocument doc) throws BadLocationException {
    int wp = ' ',
        n  = 0,
        a  = 0,
        m  = 0,
        e  = 0;

    while(cNextPos < fRangeEnd) {
      char ch = doc.getChar(cNextPos++);

      switch(ch) {
        case '{' :
          int  startOffset = cNextPos - 2;
          Node node        = getNode(doc, startOffset);

          if (node != null) {
            node.parent = parent;
            addPosition(doc, node.position);
            parent.addChild(node);
            recursiveTokens(node, depth + 1, doc);
          } else {
            recursiveTokens(parent, depth + 1, doc);
          }

          break;

        case '}' :
          return;

        case ':' :
          if ((wp == '{') || (wp == ';') || Character.isWhitespace(wp)) {
            if ((n == 'n') && (a == 'a') && (m == 'm') && (e == 'e')) {
              setName(parent, doc);
            }
          }

          break;

        default :
          wp = n;
          n  = a;
          a  = m;
          m  = e;
          e  = ch;

          break;
      }
    }
  }

  private Node parseNodes(IDocument doc) {
    cNextPos  = 0;
    fRangeEnd = doc.getLength();

    if (rootElement != null) {
      rootElement.clear();
    }

    rootElement = null;

    Node node = new Node();

    try {
      recursiveTokens(node, 0, doc);
    } catch(BadLocationException e) {
      e.printStackTrace();
    }

    return (node.children == null)
           ? null
           : node.children.get(0);
  }

  private void removeWhitespace(IDocument doc) throws BadLocationException {
    while(cNextPos < fRangeEnd) {
      char ch = doc.getChar(cNextPos);

      if (!Character.isWhitespace(ch)) {
        break;
      }

      cNextPos++;
    }
  }

  private void setName(Node parent, IDocument doc) throws BadLocationException {
    removeWhitespace(doc);

    char ch = doc.getChar(cNextPos++);

    if ((ch != '\"') && (ch != '\'')) {
      return;
    }

    char qc  = ch;
    int  pos = cNextPos;

    while(cNextPos < fRangeEnd) {
      ch = doc.getChar(cNextPos++);

      if (ch == qc) {
        break;
      }

      if ((ch == '{') || (ch == '}') || (ch == '\n')) {
        cNextPos--;

        return;
      }
    }

    if (ch != qc) {
      return;
    }

    parent.name += " (" + doc.get(pos, cNextPos - pos - 1) + ")";
  }

  private Node getNode(IDocument doc, int end) throws BadLocationException {
    int  pos = end;
    char c   = 0;

    while(pos > -1) {
      c = doc.getChar(pos);

      if (Character.isLetter(c) || (c == '{') || (c == '}') || (c == ';') || (c == '\'') || (c == '\"') || (c == ':')
          || (c == '\n')) {
        break;
      }

      pos--;
    }

    end = pos + 1;

    while(pos > -1) {
      c = doc.getChar(pos);

      if (Character.isWhitespace(c) || (c == '}') || (c == '{') || (c == ';') || (c == ':') || (c == '\'')
          || (c == '\"')) {
        pos++;

        break;
      }

      pos--;
    }

    if (pos == -1) {
      pos = 0;
    }

    c = doc.getChar(pos);

    if ((pos == end) ||!Character.isUpperCase(c)) {
      return null;
    }

    String   name = doc.get(pos, end - pos);
    SPOTNode node = SDFSyntax.getNode(name);

    return (node == null)
           ? null
           : new Node(name, pos, end - pos, node);
  }

  public static class Node {
    ArrayList<Node> children;
    int             length;
    String          name;
    SPOTNode        node;
    int             offset;
    Node            parent;
    Position        position;
    iWidget         widget;
    String typeName;
    public Node() {}

    public Node(iWidget widget) {
      this.widget = widget;
      SPOTSequence e=(SPOTSequence) widget.getLinkedData();
      iSPOTElement na=e.spot_elementFor("name");
      this.name   = na==null ? null : na.spot_stringValue();
      this.typeName=e.spot_getClassShortName();
      if(name==null) {
        name=typeName;
      }
      if (widget instanceof iContainer) {
        iContainer c   = (iContainer) widget;
        int        len = c.getWidgetCount();

        for (int i = 0; i < len; i++) {
          addChild(new Node(c.getWidget(i)));
        }
      }
    }

    public Node(String name, int offset, int length, SPOTNode node) {
      this.name     = name;
      this.position = new Position(offset, length);
      this.node     = node;
      typeName=node==null ? null : node.elementName;
    }

    public void addChild(Node child) {
      if (children == null) {
        children = new ArrayList<OutlineContentProvider.Node>(3);
      }

      children.add(child);
    }

    public void clear() {
      parent = null;
      node   = null;
      widget = null;

      if (children != null) {
        for (Node n : children) {
          n.clear();
        }

        children = null;
      }
    }

    public int getChildCount() {
      return (children == null)
             ? 0
             : children.size();
    }

    public String getTypeName() {
      return typeName;
    }
  }
}
