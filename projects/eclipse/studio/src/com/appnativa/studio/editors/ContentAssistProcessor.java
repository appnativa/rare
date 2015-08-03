/*
 * @(#)ContentAssistProcessor.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import com.appnativa.spot.SPOTNode;
import com.appnativa.util.MutableInteger;

public class ContentAssistProcessor implements IContentAssistProcessor {
  public ContentAssistProcessor() {}

  @Override
  public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, final int caretOffset) {
    List<ICompletionProposal> proposals = null;

    try {
      do {
        final IDocument doc            = viewer.getDocument();
        final int       line           = doc.getLineOfOffset(caretOffset);
        final int       lineStart      = doc.getLineOffset(line);
        final int       documentLength = doc.getLength();

        if (caretOffset == documentLength) {
          if (caretOffset == 0) {
            proposals = objects(caretOffset, caretOffset, null);
          }

          break;
        }

        String     filter          = null;
        int        dotOffset       = caretOffset;
        int        firstCharOffset = EditorUtils.skipPreviousWhiteSpace(doc, caretOffset, lineStart);
        int        delimiterOffset = getDelimiterOffset(doc, firstCharOffset, lineStart);
        boolean    attributes      = false;
        final char c               = doc.getChar(delimiterOffset);

        if ((c == '\"') || (c == '\'')) {
          break;
        }

        filter = doc.get(delimiterOffset + 1, firstCharOffset - delimiterOffset).trim().toLowerCase();

        if (filter.equals("]")) {
          filter = "";
        }

        dotOffset -= filter.length();

        if ((delimiterOffset == lineStart) && (line == 0)) {
          proposals = objects(dotOffset, caretOffset, filter);

          break;
        }

        if ((c != '[') && (delimiterOffset != lineStart)) {
          int n = EditorUtils.findCharacter(doc, delimiterOffset - 1, lineStart, '[', '\0');

          if (n != -1) {
            attributes      = true;
            delimiterOffset = n;
          }
        }

        if ((c == '[') || attributes) {
          attributes = true;

          int n = EditorUtils.findCharacter(doc, delimiterOffset - 1, lineStart, ':', '\0');

          if (n == -1) {
            n = EditorUtils.findCharacter(doc, delimiterOffset - 1, lineStart, '}', '\0');
          }

          if (n != -1) {
            delimiterOffset = n;
          }
        }

        String         name = null;
        MutableInteger rpos = new MutableInteger(0);

        if ((delimiterOffset != -1) && (delimiterOffset > lineStart) && (doc.getChar(delimiterOffset) == ':')) {
          delimiterOffset = EditorUtils.skipPreviousWhiteSpace(doc, delimiterOffset - 1, lineStart) + 1;
          firstCharOffset = EditorUtils.getWordStartOffset(doc, delimiterOffset, lineStart);
          name            = doc.get(firstCharOffset, delimiterOffset - firstCharOffset);
          rpos.set(firstCharOffset);
        } else {
          name = EditorUtils.findName(doc, delimiterOffset - 1, rpos);
        }

        if (name == null) {
          break;
        }

        SPOTNode element = null;
        SPOTNode parent  = Character.isUpperCase(name.charAt(0))
                           ? SDFSyntax.getNode(name)
                           : null;

        if (parent == null) {
          String s = null;

          while((parent == null) && (s = EditorUtils.findName(doc, rpos.getValue() - 1, rpos)) != null) {
            parent = SDFSyntax.getNode(s);
          }

          element = findElement(doc, parent, name, rpos);
        } else {
          element = parent;
        }

        if (element == null) {
          break;
        }

        switch(c) {
          case ':' :
            proposals = enumerated(element, dotOffset, caretOffset, filter);

            break;

          default :
            if (attributes) {
              proposals = attributes(element, dotOffset, caretOffset, filter);
            } else {
              proposals = properties(element, dotOffset, caretOffset, filter);
            }

            break;
        }
      } while(false);
    } catch(Exception ex) {
      ex.printStackTrace();
    }

    return (proposals == null)
           ? null
           : proposals.toArray(new ICompletionProposal[proposals.size()]);
  }

  @Override
  public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public char[] getCompletionProposalAutoActivationCharacters() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public char[] getContextInformationAutoActivationCharacters() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IContextInformationValidator getContextInformationValidator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getErrorMessage() {
    // TODO Auto-generated method stub
    return null;
  }

  static SPOTNode deepFind(SPOTNode parent, String property) {
    SPOTNode x = parent.elementFor(property);

    if (x != null) {
      return x;
    }

    while(parent != null) {
      String s = (parent.extendsType == null)
                 ? parent.elementType
                 : parent.extendsType;

      parent = SDFSyntax.getNode(s);

      if (parent != null) {
        x = parent.elementFor(property);

        if (x != null) {
          return x;
        }
      }
    }

    return null;
  }

  static List<ICompletionProposal> properties(SPOTNode node, int dotOffset, int caretOffset, String filter) {
    if ((node == null) ||!node.isContainer()) {
      if ((node != null) && node.isEnumType()) {
        return enumerated(node, dotOffset, caretOffset, filter);
      }

      return null;
    }

    SPOTNode                  x;
    int                       len   = node.childNodes.size();
    List<ICompletionProposal> items = new ArrayList<ICompletionProposal>(len);
    String                    clz;
    String                    s;

    while(node != null) {
      clz = " (" + node.elementName + ")";

      for (int i = 0; i < len; i++) {
        x = node.childNodes.get(i);

        if ((x.elementName != null) && (x.elementName.length() > 0)) {
          if ((filter != null) &&!x.elementName.toLowerCase().startsWith(filter)) {
            continue;
          }

          s = x.elementType;

          if ("PrintableString".equals(s)) {
            s = "String";
          }

          if (s != null) {
            s += clz;
          }

          items.add(new SDFCompletionItem(SDFCompletionItem.Type.PROPERTY, x, x.elementName, s, dotOffset,
                                          caretOffset));
        }
      }

      node = SDFSyntax.getNode(node.extendsType);

      if (node != null) {
        len = node.childNodes.size();
      }
    }

    return items;
  }

  static SPOTNode getClassNode(SPOTNode node) {
    SPOTNode x = null;

    while(true) {
      String s = (node.extendsType == null)
                 ? node.elementType
                 : node.extendsType;

      x = SDFSyntax.getNode(s);

      if (x == null) {
        if (node.isSetType() && node.isContainer()) {
          node = node.childNodes.get(0);

          continue;
        }
      }

      break;
    }

    return x;
  }

  static String getPreviousName(IDocument doc, int end, MutableInteger rpos) throws BadLocationException {
    int  pos = end;
    char c   = 0;

    while(pos > -1) {
      c = doc.getChar(pos);

      if (Character.isLetter(c)) {
        break;
      }

      pos--;
    }

    end = pos + 1;

    while(pos > -1) {
      c = doc.getChar(pos);

      if (Character.isWhitespace(c) || (c == '}') || (c == '{') || (c == ';') || (c == ':')) {
        pos++;

        break;
      }

      pos--;
    }

    if (pos == -1) {
      pos = 0;
    }

    c = doc.getChar(pos);

    if (pos == end) {
      return null;
    }

    String name = doc.get(pos, end - pos);

    rpos.set(pos);

    return name;
  }

  private static void addCompletionItems(Iterator<String> it, List<ICompletionProposal> list, String prop,
          SDFCompletionItem.Type type, int dotOffset, int caretOffset, String filter) {
    while(it.hasNext()) {
      String text = it.next();

      if ((filter != null) &&!text.toLowerCase().startsWith(filter)) {
        continue;
      }

      list.add(new SDFCompletionItem(type, text, prop, dotOffset, caretOffset));
    }
  }

  private static List<ICompletionProposal> attributes(SPOTNode x, int dotOffset, int caretOffset, String filter) {
    List<ICompletionProposal> items = new ArrayList<ICompletionProposal>();

    if (x.hasAttributes()) {
      addCompletionItems(x.theAttributes.keySet().iterator(), items, x.elementName, SDFCompletionItem.Type.ATTRIBUTE,
                         dotOffset, caretOffset, filter);
    }

    while((x = getClassNode(x)) != null) {
      if (x.hasAttributes()) {
        addCompletionItems(x.theAttributes.keySet().iterator(), items, x.elementName, SDFCompletionItem.Type.ATTRIBUTE,
                           dotOffset, caretOffset, filter);
      }
    }

    return items;
  }

  private static List<ICompletionProposal> enumerated(SPOTNode node, int dotOffset, int caretOffset, String filter) {
    String                    a[]   = node.theChoices;
    String                    b[]   = node.theChoicesComments;
    int                       len   = (a == null)
                                      ? 0
                                      : a.length;
    List<ICompletionProposal> items = new ArrayList<ICompletionProposal>(len);
    String                    clz   = " (" + node.elementName + ")";
    String                    s;

    if (node.isBooleanType()) {
      if ((filter == null) || "true".startsWith(filter)) {
        items.add(new SDFCompletionItem(SDFCompletionItem.Type.ENUMERATED, "true", clz, null, dotOffset, caretOffset));
      }

      if ((filter == null) || "false".startsWith(filter)) {
        items.add(new SDFCompletionItem(SDFCompletionItem.Type.ENUMERATED, "false", clz, null, dotOffset, caretOffset));
      }
    } else {
      for (int i = 0; i < len; i++) {
        s = a[i];

        if ((filter != null) &&!s.toLowerCase().startsWith(filter)) {
          continue;
        }

        items.add(new SDFCompletionItem(SDFCompletionItem.Type.ENUMERATED, s, clz, b[i], dotOffset, caretOffset));
      }
    }

    return items;
  }

  private SPOTNode findElement(IDocument doc, SPOTNode parent, String property, MutableInteger rpos)
          throws BadLocationException {
    ArrayList<String> list = null;
    String            name;

    while(parent == null) {
      name = EditorUtils.findName(doc, rpos.getValue() - 1, rpos);

      if (name == null) {
        return null;
      }

      parent = Character.isUpperCase(name.charAt(0))
               ? SDFSyntax.getNode(name)
               : null;

      if (parent == null) {
        if (list == null) {
          list = new ArrayList<String>(3);
        }

        list.add(name);
      }
    }

    SPOTNode element = parent;

    if ((property != null) && (parent != null)) {
      SPOTNode a[] = findProperty(parent, property, list);

      if (a != null) {
        element = a[1];

        while(element.isSetType()) {
          element = element.elementAt(0);
        }

        if (!element.isContainer()) {
          parent = SDFSyntax.getNode(element.elementType);

          if (parent != null) {
            element = parent;
          }
        }
      }
    }

    return element;
  }

  private static SPOTNode[] findProperty(SPOTNode parent, String property, List<String> path) {
    SPOTNode oparent = parent;
    SPOTNode node    = null;
    int      len     = (path == null)
                       ? 0
                       : path.size();
    String   s;

    for (int i = len - 1; i >= 0; i--) {
      s = path.get(i);

      if (s == null) {
        continue;
      }

      node = deepFind(parent, s);

      if (node == null) {
        break;
      }

      parent = getClassNode(node);

      if (parent == null) {
        parent = node;

        break;
      }
    }

    if (parent == null) {
      parent = oparent;
    }

    node = deepFind(parent, property);

    if (node != null) {
      SPOTNode n[] = new SPOTNode[2];

      n[0] = parent;
      n[1] = node;

      return n;
    }

    return null;
  }

  private static List<ICompletionProposal> objects(int dotOffset, int caretOffset, String filter) {
    SPOTNode                  x;
    int                       len   = SDFSyntax.getNodeCount();
    List<ICompletionProposal> items = new ArrayList<ICompletionProposal>(len);

    for (int i = 0; i < len; i++) {
      x = SDFSyntax.getNodeAt(i);

      if ((x.elementName != null) && (x.elementName.length() > 0)) {
        if ((filter != null) &&!x.elementName.toLowerCase().startsWith(filter)) {
          continue;
        }

        items.add(new SDFCompletionItem(SDFCompletionItem.Type.CLASS, x.elementName, null, dotOffset, caretOffset));
      }
    }

    return items;
  }

  private int getDelimiterOffset(IDocument doc, int offset, int lineStart) throws BadLocationException {
    while(offset > lineStart) {
      final char c = doc.getChar(offset);

      if ((c == '=') || (c == ':') || (c == ',') || (c == '{') || (c == ';') || (c == '{') || (c == '[') || (c == '\"')
          || (c == '\'') || (c == '\n') || (c == '\r')) {
        return offset;
      }

      offset--;
    }

    return lineStart;
  }
}
