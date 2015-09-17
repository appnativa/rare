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
