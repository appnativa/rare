/*
 * @(#)SpinnerWidget.java   2010-03-14
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.widget;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.Spinner;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.SpinnerComponent;
import com.appnativa.rare.ui.WidgetListener;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.spinner.iSpinner;
import com.appnativa.rare.ui.spinner.iSpinnerEditor;
import com.appnativa.rare.viewer.iContainer;

/**
 * A widget that allows the user to enter a value via a text field
 * or adjust the value by either clicking on a up or down arrow,
 * or by holding the arrow down, causing the value in the text box
 * to increase or decrease depending on the direction of the arrow being pressed.
 *
 * @author Don DeCoteau
 */
public class SpinnerWidget extends aSpinnerWidget {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public SpinnerWidget(iContainer parent) {
    super(parent);
  }

  @Override
  protected iSpinner createSpinnerAndComponents(Spinner cfg) {
    SpinnerComponent spinner = new SpinnerComponent();

    dataComponent = formComponent = spinner;

    return spinner;
  }

  @Override
  protected void initializeListeners(aWidgetListener listener) {
    WidgetListener l = (WidgetListener) listener;

    super.initializeListeners(l);

    if ((l != null) && l.isKeyEventsEnabled()) {
      getDataComponentEx().addKeyListener(l);
    }
  }

  @Override
  protected void registerEditorWithWidget(iSpinnerEditor editor) {
    if (editor != null) {
      Component c = (Component) editor.getComponent();

      registerWithWidget(c);

      WidgetListener l = (WidgetListener) getWidgetListener();

      if (l != null) {
        if (l.isKeyEventsEnabled()) {
          c.addKeyListener(l);
        }

        if (l.isEnabled(iConstants.EVENT_BLUR) || l.isEnabled(iConstants.EVENT_BLUR)) {
          c.addFocusListener(l);
        }
      }
    }
  }

  @Override
  protected void unregisterEditorWithWidget(iSpinnerEditor editor) {
    if (editor != null) {
      WidgetListener l = (WidgetListener) getWidgetListener();

      if (l != null) {
        Component c = (Component) editor.getComponent();

        if (c != null) {
          c.removeKeyListener(l);
          c.addFocusListener(l);
        }
      }
    }
  }
}
