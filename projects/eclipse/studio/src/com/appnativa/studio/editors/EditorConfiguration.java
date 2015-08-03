/*
 * @(#)EditorConfiguration.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class EditorConfiguration extends SourceViewerConfiguration {
  private AttributeScanner attributeScanner;
  private RuleBasedScanner classScanner;
  private RuleBasedScanner commentScanner;
  private RMLEditor        editor;
  private RuleBasedScanner preformattedScanner;
  private PropertyScanner  propertyScanner;
  private RuleBasedScanner quoteScanner;

  public EditorConfiguration(RMLEditor editor) {
    this.editor = editor;
  }

  public AttributeScanner getAttributeScanner() {
    if (attributeScanner == null) {
      attributeScanner = new AttributeScanner();
    }

    return attributeScanner;
  }

  public RuleBasedScanner getClassScanner() {
    if (classScanner == null) {
      classScanner = new RuleBasedScanner();
      classScanner.setDefaultReturnToken(TokenID.CLASS);
    }

    return classScanner;
  }

  public RuleBasedScanner getCommentScanner() {
    if (commentScanner == null) {
      commentScanner = new RuleBasedScanner();
      commentScanner.setDefaultReturnToken(TokenID.COMMENT);
    }

    return commentScanner;
  }

  @Override
  public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
    return new String[] { IDocument.DEFAULT_CONTENT_TYPE, PartitionScanner.SDF_COMMENT, PartitionScanner.SDF_CLASS,
                          PartitionScanner.SDF_ATTRIBUTES, PartitionScanner.SDF_PREFORMATTED };
  }

  public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
    ContentAssistant       assistant = new ContentAssistant();
    ContentAssistProcessor p         = new ContentAssistProcessor();

    assistant.setContentAssistProcessor(p, PartitionScanner.SDF_ATTRIBUTES);
    assistant.setContentAssistProcessor(p, IDocument.DEFAULT_CONTENT_TYPE);
    assistant.enableAutoActivation(true);
    assistant.setAutoActivationDelay(500);
    assistant.setProposalPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
    assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);

    return assistant;
  }

  public RuleBasedScanner getPreformattedScanner() {
    if (preformattedScanner == null) {
      preformattedScanner = new RuleBasedScanner();
      preformattedScanner.setDefaultReturnToken(TokenID.PREFORMATTED);
    }

    return preformattedScanner;
  }

  public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
    PresentationReconciler reconciler = new PresentationReconciler();

    reconciler.setDocumentPartitioning(DocumentSetup.PARTITIONING);

    DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getClassScanner());

    reconciler.setDamager(dr, PartitionScanner.SDF_CLASS);
    reconciler.setRepairer(dr, PartitionScanner.SDF_CLASS);
    dr = new DefaultDamagerRepairer(getAttributeScanner());
    reconciler.setDamager(dr, PartitionScanner.SDF_ATTRIBUTES);
    reconciler.setRepairer(dr, PartitionScanner.SDF_ATTRIBUTES);
    dr = new DefaultDamagerRepairer(getPropertyScanner());
    reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
    reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
    dr = new DefaultDamagerRepairer(getCommentScanner());
    reconciler.setDamager(dr, PartitionScanner.SDF_COMMENT);
    reconciler.setRepairer(dr, PartitionScanner.SDF_COMMENT);
    dr = new DefaultDamagerRepairer(getQuoteScanner());
    reconciler.setDamager(dr, PartitionScanner.SDF_STRING);
    reconciler.setRepairer(dr, PartitionScanner.SDF_STRING);
    dr = new DefaultDamagerRepairer(getPreformattedScanner());
    reconciler.setDamager(dr, PartitionScanner.SDF_PREFORMATTED);
    reconciler.setRepairer(dr, PartitionScanner.SDF_PREFORMATTED);

    return reconciler;
  }

  public PropertyScanner getPropertyScanner() {
    if (propertyScanner == null) {
      propertyScanner = new PropertyScanner();
    }

    return propertyScanner;
  }

  public RuleBasedScanner getQuoteScanner() {
    if (quoteScanner == null) {
      quoteScanner = new RuleBasedScanner();
      quoteScanner.setDefaultReturnToken(TokenID.STRING_LITERAL);
    }

    return commentScanner;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.eclipse.jface.text.source.SourceViewerConfiguration#getReconciler(org
   * .eclipse.jface.text.source.ISourceViewer)
   */
  public IReconciler getReconciler(ISourceViewer sourceViewer) {
    ReconcilingStrategy strategy = new ReconcilingStrategy();

    strategy.setEditor(editor);

    MonoReconciler reconciler = new MonoReconciler(strategy, false);

    return reconciler;
  }
  //
  // public IContentFormatter getContentFormatter(ISourceViewer sourceViewer)
  // {
  // ContentFormatter formatter = new ContentFormatter();
  // XMLFormattingStrategy formattingStrategy = new XMLFormattingStrategy();
  // DefaultFormattingStrategy defaultStrategy = new
  // DefaultFormattingStrategy();
  // TextFormattingStrategy textStrategy = new TextFormattingStrategy();
  // DocTypeFormattingStrategy doctypeStrategy = new
  // DocTypeFormattingStrategy();
  // PIFormattingStrategy piStrategy = new PIFormattingStrategy();
  // formatter.setFormattingStrategy(defaultStrategy,
  // IDocument.DEFAULT_CONTENT_TYPE);
  // formatter.setFormattingStrategy(textStrategy,
  // XMLPartitionScanner.XML_TEXT);
  // formatter.setFormattingStrategy(doctypeStrategy,
  // XMLPartitionScanner.XML_DOCTYPE);
  // formatter.setFormattingStrategy(piStrategy, XMLPartitionScanner.XML_PI);
  // formatter.setFormattingStrategy(textStrategy,
  // XMLPartitionScanner.XML_CDATA);
  // formatter.setFormattingStrategy(formattingStrategy,
  // XMLPartitionScanner.XML_START_TAG);
  // formatter.setFormattingStrategy(formattingStrategy,
  // XMLPartitionScanner.XML_END_TAG);
  //
  // return formatter;
  // }
  // static class ContentFormatter implements IContentFormatter {
  //
  // }
}
