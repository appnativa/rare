/*
 * @(#)SDFCompletionItem.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import com.appnativa.spot.SPOTNode;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension5;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension6;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

public class SDFCompletionItem
        implements ICompletionProposal, ICompletionProposalExtension5, ICompletionProposalExtension6 {
  private int            caretOffset;
  private String         comment;
  private final int      dotOffset;
  private final SPOTNode node;
  private final String   right;
  private StyledString   styledString;
  private final String   text;
  private final Type     type;

  public enum Type {
    PROPERTY, ATTRIBUTE, CLASS, ENUMERATED;
  }

  public SDFCompletionItem(Type type, String text, String right, int dotOffset, int caretOffset) {
    this.type        = type;
    this.node        = null;
    this.text        = text;
    this.right       = right;
    this.dotOffset   = dotOffset;
    this.caretOffset = caretOffset;
  }

  public SDFCompletionItem(Type type, SPOTNode node, String text, String right, int dotOffset, int caretOffset) {
    this.type        = type;
    this.node        = node;
    this.text        = text;
    this.right       = right;
    this.dotOffset   = dotOffset;
    this.caretOffset = caretOffset;
  }

  public SDFCompletionItem(Type type, String text, String right, String comment, int dotOffset, int caretOffset) {
    this.type        = type;
    this.node        = null;
    this.text        = text;
    this.right       = right;
    this.comment     = comment;
    this.dotOffset   = dotOffset;
    this.caretOffset = caretOffset;
  }

  @Override
  public void apply(IDocument doc) {
    try {
      doc.replace(dotOffset, caretOffset - dotOffset, text + getExtra());
    } catch(BadLocationException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public String getAdditionalProposalInfo() {
    return getComment();
  }

  @Override
  public Object getAdditionalProposalInfo(IProgressMonitor arg0) {
    return getComment();
  }

  public String getComment() {
    if ((comment == null) && (node != null)) {
      comment = node.theDescription;

      if (comment == null) {
        comment = node.theComment;
      }
      // if (comment != null && comment.length() > 0) {
      // StringBuilder sb = new StringBuilder();
      // sb.append("<html><b>").append(node.elementType).append("</b><br/><br>");
      // int n = comment.indexOf('~');
      // if (n == -1) {
      // sb.append(comment);
      // } else {
      // sb.append(comment.substring(0, n));
      // }
      // sb.append("</html>");
      // comment = sb.toString();
      // }
    }

    return comment;
  }

  @Override
  public IContextInformation getContextInformation() {
    return null;
  }

  @Override
  public String getDisplayString() {
    if (right != null) {
      return text + " - " + right;
    }

    return text;
  }

  @Override
  public Image getImage() {
    return null;
  }

  @Override
  public Point getSelection(IDocument document) {
    int add = 0;

    switch(type) {
      case CLASS :
        add = 2;

        break;

      case ATTRIBUTE :
        add = 1;

        break;

      case ENUMERATED :
        break;

      default :
        if (node != null) {
          add = 2;
        }
    }

    return new Point(dotOffset + text.length() + add, 0);
  }

  @Override
  public StyledString getStyledDisplayString() {
    if (styledString == null) {
      styledString = new StyledString();
      styledString.append(text, StyledString.QUALIFIER_STYLER);

      if (right != null) {
        styledString.append(" - ");
        styledString.append(text, StyledString.DECORATIONS_STYLER);
      }
    }

    return styledString;
  }

  private String getExtra() {
    switch(type) {
      case CLASS :
        return "{ }";

      case ATTRIBUTE :
        return "=";

      case ENUMERATED :
        return "";

      default :
        if (node == null) {
          return "";
        }

        if (node.isSetType() || (SPOTNode.findType(node.elementType) == -1)) {
          return "{ }";
        } else {
          return ": ";
        }
    }
  }
}
