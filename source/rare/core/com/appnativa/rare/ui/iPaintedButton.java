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

package com.appnativa.rare.ui;

import com.appnativa.rare.ui.painter.PainterHolder;

/**
 * Interface for buttons that support background painting.
 *
 * @author Don DeCoteau
 */
public interface iPaintedButton {

  /**
   * Button states
   */
  public static enum ButtonState {

    /** button is in the default state */
    DEFAULT,

    /** the mouse is over the button */
    ROLLOVER,

    /** the button is pressed */
    PRESSED,

    /** button is selected */
    SELECTED,

    /** the button is pressed and selected */
    PRESSED_SELECTED,

    /** button is disabled */
    DISABLED,

    /** button is disabled and selected */
    DISABLED_SELECTED;

    public boolean isPressed() {
      return (this == PRESSED) || (this == PRESSED_SELECTED);
    }
  }

  /**
   * Sets the painter holder for the button
   *
   * @param ph
   *          the painter holder for the button
   */
  public void setPainterHolder(PainterHolder ph);

  /**
   * Gets the painter holder for the button
   *
   * @return the painter holder for the button
   */
  public PainterHolder getPainterHolder();

  /**
   * Sets whether or not roll-over is enabled for the button
   *
   * @param enabled
   *          true for enabled; false otherwise
   */
  void setRolloverEnabled(boolean enabled);

  /**
   * Gets whether or not roll-over is enabled for the button
   *
   * @return true for enabled; false otherwise
   */
  boolean isRollOverEnabled();
}
