/*
 * @(#)PropertyScanner.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import com.appnativa.studio.editors.SDFSyntax.NodeContext;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;

public class PropertyScanner implements ITokenScanner {
  NodeContext      state = NodeContext.ATTRIBUTE;
  IDocument        document;
  int              endOffset;
  boolean          error;
  int              length;
  int              offset;
  RuleBasedScanner scanner;
  int              tokenOffset;

  public PropertyScanner() {
    scanner = new RuleBasedScanner();

    IRule[] rules = new IRule[] {
      new SDFSyntax.ClassRule(TokenID.CLASS), new MultiLineRule("\"", "\"", TokenID.STRING_LITERAL, '\\'),
      new SingleLineRule("'", "'", TokenID.STRING_LITERAL, '\\'), new SDFSyntax.PropertyRule(TokenID.IDENTIFIER),
      new SDFSyntax.ConstantRule(TokenID.CONSTANT),
      //new MultiLineRule("/*", "*/", TokenID.COMMENT),
      //new EndOfLineRule("//>", TokenID.COMMENT),
      //new SDFSyntax.PreformattedRule(),
      new SDFSyntax.NumberRuleEx(), new SDFSyntax.OperatorRule(new char[] { ';' }, TokenID.SEMI_COLON),
    };

    scanner.setRules(rules);
    scanner.setDefaultReturnToken(TokenID.NORMAL);
  }

  public IToken nextToken() {
    return scanner.nextToken();
  }

  public IToken nextToken1() {
    IToken t = scanner.nextToken();

    if (t.isEOF() || t.isOther() || t.isUndefined()) {
      return t;
    }

    switch(state) {
      case ATTRIBUTE :
        if (t != TokenID.ATTRIBUTE) {
          t = errorToken();
        }

        state = NodeContext.EQUAL;

        break;

      case EQUAL :
        if (t != TokenID.EQUAL) {
          t = errorToken();
        }

        state = NodeContext.ATTRUBUTE_VALUE;

        break;

      case ATTRUBUTE_VALUE :
        if ((t != TokenID.STRING_LITERAL) && (t != TokenID.NUMERIC_LITERAL) && (t != TokenID.CONSTANT)) {
          t = errorToken();
        }

        state = NodeContext.COMMA;

        break;

      default :
        break;
    }

    return t;
  }

  @Override
  public void setRange(IDocument document, int offset, int length) {
    scanner.setRange(document, offset, length);
    this.document = document;
    this.offset   = offset;
    this.length   = length;
    state         = NodeContext.ATTRIBUTE;
  }

  @Override
  public int getTokenLength() {
    if (!error) {
      return scanner.getTokenLength();
    }

    return offset - tokenOffset;
  }

  @Override
  public int getTokenOffset() {
    if (!error) {
      return scanner.getTokenOffset();
    }

    return tokenOffset;
  }

  protected IToken errorToken() {
    offset      += length;
    tokenOffset = scanner.getTokenOffset();

    return TokenID.ERROR;
  }
}
