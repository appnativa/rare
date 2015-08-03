/*
 * @(#)SplitPaneViewer.java   2012-01-03
 * 
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;


/**
 * A viewer that splits its section of the screen
 * into multiple resizable regions.
 *
 * @author     Don DeCoteau
 */
public class SplitPaneViewer extends aSplitPaneViewer {

  /**
   * Constructs a new instance
   */
  public SplitPaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public SplitPaneViewer(iContainer parent) {
    super(parent);
  }

}
