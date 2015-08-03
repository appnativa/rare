/*
 * @(#)DocumentSetup.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;

public class DocumentSetup implements IDocumentSetupParticipant {
  public static final String PARTITIONING = "com.appnativa.studio";

  public void setup(IDocument document) {
    if (document instanceof IDocumentExtension3) {
      IDocumentPartitioner partitioner = new FastPartitioner(new PartitionScanner(),
                                           new String[] { PartitionScanner.SDF_COMMENT,
              PartitionScanner.SDF_CLASS, PartitionScanner.SDF_ATTRIBUTES, PartitionScanner.SDF_PREFORMATTED });

      partitioner.connect(document);
      ((IDocumentExtension3) document).setDocumentPartitioner(PARTITIONING, partitioner);
    }
  }
}
