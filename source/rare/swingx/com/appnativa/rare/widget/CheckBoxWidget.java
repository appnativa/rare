/*
 * @(#)CheckBoxWidget.java
 * 
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.widget;

import com.appnativa.rare.platform.swing.ui.view.CheckBoxView;
import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.CheckBox;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.viewer.iContainer;

/**
 *  A widget that allows a user to select one or more items utilizing checking / unchecking metaphor.
 *
 *  @author Don DeCoteau
 */
public class CheckBoxWidget extends aCheckBoxWidget {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public CheckBoxWidget(iContainer parent) {
    super(parent);
  }

  /**
   * Creates a new checkbox widget
   *
   * @param parent the parent
   * @param cfg the configuration
   *
   * @return the checkbox widget
   */
  public static CheckBoxWidget create(iContainer parent, CheckBox cfg) {
    CheckBoxWidget widget = new CheckBoxWidget(parent);

    widget.configure(cfg);

    return widget;
  }

  /**
   * Sets the state of the checkbox
   *
   * @param state the state
   */
  @Override
  public void setState(State state) {
    ((CheckBoxView) dataComponent.getView()).setState(state);
  }

  /**
   * Gets the current state of the checkbox
   *
   * @return the current state of the checkbox
   */
  @Override
  public State getState() {
    return ((CheckBoxView) dataComponent.getView()).getState();
  }

  @Override
  protected iActionComponent createButton(Button cfg) {
    return new ActionComponent(getAppContext().getComponentCreator().getCheckBox(getViewer(), (CheckBox) cfg));
  }

  @Override
  protected void postConfigure(Button vcfg) {}
}
