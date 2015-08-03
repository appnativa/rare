/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.widget;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.NumberSpinner;
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

  /**
   * Creates the spinner and the data and form components and
   *
   * @param cfg the configuration
   * @return the spinner interface
   */
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

  protected static void registerForUse() {
    Platform.getAppContext().registerWidgetClass(Platform.getSPOTName(Spinner.class), SpinnerWidget.class);
  }

  @Override
  protected void setupNumericSpinner(iSpinner spinner, NumberSpinner cfg) {
    if (Platform.isIOS() && cfg.supportDecimalValues.booleanValue()) {
      spinner.setUseDesktopStyleEditor(true);
    }

    super.setupNumericSpinner(spinner, cfg);

    if (!spinner.isUseDesktopStyleEditor()) {
      if ((cfg.getBorders() != null)
          || ((cfg.bgColor.getValue() != null) &&!cfg.bgColor.getValue().equals("transparent"))) {
        dataComponent.getView().makeTransparent();
        spinner.getEditor().getComponent().getView().makeTransparent();
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
