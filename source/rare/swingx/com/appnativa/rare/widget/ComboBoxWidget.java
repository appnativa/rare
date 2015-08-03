/*
 * @(#)ComboBoxWidget.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.widget;

import com.appnativa.rare.platform.swing.ui.DataItemListModel;
import com.appnativa.rare.spot.ComboBox;
import com.appnativa.rare.ui.ComboBoxComponent;
import com.appnativa.rare.ui.aComboBoxComponent;
import com.appnativa.rare.viewer.iContainer;

/**
 *  A widget that allows a user to select one or more choices from a
 *  scrollable list of items.
 *
 *  @author Don DeCoteau
 */
public class ComboBoxWidget extends aComboBoxWidget {

  /**
   *  Constructs a new instance
   */
  public ComboBoxWidget() {
    this(null);
  }

  /**
   *  Constructs a new instance
   *
   *  @param parent the widget's parent
   */
  public ComboBoxWidget(iContainer parent) {
    super(parent);
  }

  @Override
  protected aComboBoxComponent createModelAndComponents(ComboBox cfg) {
    ComboBoxComponent cb = new ComboBoxComponent();

    dataComponent = formComponent = cb;
    listModel     = new DataItemListModel();
    return cb;
  }
}
