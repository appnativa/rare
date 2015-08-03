/*
 * @(#)PartitionScanner.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class PartitionScanner extends RuleBasedPartitionScanner {
  public final static String SDF_ATTRIBUTES   = "__sdf_attributes";
  public final static String SDF_CLASS        = "__sdf_class";
  public final static String SDF_COMMENT      = "__sdf_comment";
  public final static String SDF_PREFORMATTED = "__sdf_preformatted";
  public final static String SDF_STRING       = "__sdf_string";

  public PartitionScanner() {
    IToken           sdfClass        = new Token(SDF_CLASS);
    IToken           sdfComment      = new Token(SDF_COMMENT);
    IToken           sdfAttributes   = new Token(SDF_ATTRIBUTES);
    IToken           sdfPreformatted = new Token(SDF_PREFORMATTED);
    IToken           sdfString       = new Token(SDF_STRING);
    IPredicateRule[] rules           = new IPredicateRule[] {
      new MultiLineRule("/*", "*/", sdfComment), new EndOfLineRule("//", sdfComment),
      new MultiLineRule("\"", "\"", sdfString, '\\'), new SingleLineRule("'", "'", sdfString, '\\'),
      new SDFSyntax.ClassRule(sdfClass), new SDFSyntax.PreformattedRule(sdfPreformatted),
      new SDFSyntax.AttributesRule(sdfAttributes),
    };

    setPredicateRules(rules);
  }
}
