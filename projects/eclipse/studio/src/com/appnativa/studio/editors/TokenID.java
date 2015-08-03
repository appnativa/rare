/*
 * @(#)TokenID.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;

public class TokenID {
  public static final Token CLASS           = new Token(new TextAttribute(RMLEditor.CLASS, null, SWT.BOLD));
  public static final Token KEYWORD         = new Token(new TextAttribute(RMLEditor.KEYWORD));
  public static final Token IDENTIFIER      = new Token(new TextAttribute(RMLEditor.KEYWORD));
  public static final Token NORMAL          = new Token(new TextAttribute(RMLEditor.DEFAULT));
  public static final Token OPERATOR        = new Token(new TextAttribute(RMLEditor.DEFAULT));
  public static final Token LBRACE          = new Token(new TextAttribute(RMLEditor.DEFAULT));
  public static final Token EQUAL           = new Token(new TextAttribute(RMLEditor.DEFAULT));
  public static final Token COMMA           = new Token(new TextAttribute(RMLEditor.DEFAULT));
  public static final Token RBRACE          = new Token(new TextAttribute(RMLEditor.DEFAULT));
  public static final Token COLON           = new Token(new TextAttribute(RMLEditor.DEFAULT));
  public static final Token SEMI_COLON      = new Token(new TextAttribute(RMLEditor.DEFAULT));
  public static final Token ERROR           = new Token(new TextAttribute(RMLEditor.STRING));
  public static final Token WHITESPACE      = new Token(new TextAttribute(RMLEditor.STRING));
  public static final Token WARNING         = new Token(new TextAttribute(RMLEditor.ERROR));
  public static final Token STRING_LITERAL  = new Token(new TextAttribute(RMLEditor.STRING));
  public static final Token SCRIPT          = new Token(new TextAttribute(RMLEditor.STRING));
  public static final Token PREFORMATTED    = new Token(new TextAttribute(RMLEditor.PREFORMATTED, null, SWT.ITALIC));
  public static final Token NUMERIC_LITERAL = new Token(new TextAttribute(RMLEditor.NUMBER));
  public static final Token INVALID         = new Token(new TextAttribute(RMLEditor.ERROR));
  public static final Token CONSTANT        = new Token(new TextAttribute(null, null, SWT.ITALIC));
  public static final Token COMMENT         = new Token(new TextAttribute(RMLEditor.COMMENT));
  public static final Token ATTRIBUTE       = new Token(new TextAttribute(RMLEditor.ATTRIBUTE));
}
