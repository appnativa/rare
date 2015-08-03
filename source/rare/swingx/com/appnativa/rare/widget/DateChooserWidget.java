/*
 * @(#)DateChooserWidget.java   2010-07-07
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.widget;

import com.appnativa.rare.viewer.iContainer;

/**
 * A widget that allows a date and optionally a time to be selected. Unlike a spinner
 * this widget utilizes a calendar metaphor. Multiply calendar can be display
 * by choosing a multiple display type and specifying the number of columns
 * and or rows that the chooser will use.
 *
 * @author Don DeCoteau
 */
public class DateChooserWidget extends aDateChooserWidget {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public DateChooserWidget(iContainer parent) {
    super(parent);
  }

 
}
