/*
 * @(#)FormViewer.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

/**
 *  A viewer that host other widgets and viewers and allows their values to be
 *  submitted via the HTTP protocol
 *
 *  @author Don DeCoteau
 */
public class FormViewer extends aFormViewer {

  /**
   *  Constructs a new instance
   */
  public FormViewer() {
    super(null);
  }

  /**
   *  Constructs a new instance
   *
   *  @param parent
   *           the widget's parent
   */
  public FormViewer(iContainer parent) {
    super(parent);
  }
}
