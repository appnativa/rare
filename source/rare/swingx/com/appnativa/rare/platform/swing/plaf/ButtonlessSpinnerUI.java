/*
 * @(#)ButtonlessSpinnerUI.java   2009-01-20
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.plaf;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSpinnerUI;

/**
 * A SpinnerUI that does no create any default buttons
 *
 * @author Don DeCoteau
 */
public class ButtonlessSpinnerUI extends BasicSpinnerUI {

  public static ComponentUI createUI(JComponent c) {
    return new ButtonlessSpinnerUI();
  }

  public static ButtonlessSpinnerUI getInstance() {
    return new ButtonlessSpinnerUI();
  }

  @Override
  protected Component createNextButton() {
    return null;
  }

  @Override
  protected Component createPreviousButton() {
    return null;
  }

  @Override
  protected JComponent createEditor() {
    return spinner.getEditor();
  }

  @Override
  protected void installDefaults() {
    spinner.setLayout(createLayout());
    LookAndFeel.installBorder(spinner, "Spinner.border");
    LookAndFeel.installColorsAndFont(spinner, "Spinner.background", "Spinner.foreground", "Spinner.font");
  }
}
