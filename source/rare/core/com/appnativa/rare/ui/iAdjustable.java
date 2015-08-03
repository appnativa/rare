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

import com.appnativa.rare.ui.event.iChangeListener;

public interface iAdjustable {

  /**
   * Sets the minimum value of the adjustable object.
   * @param min the minimum value
   */
  void setMinimum(double min);

  /**
   * Gets the minimum value of the adjustable object.
   * @return the minimum value of the adjustable object
   */
  double getMinimum();

  /**
   * Sets the maximum value of the adjustable object.
   * @param max the maximum value
   */
  void setMaximum(double max);

  /**
   * Gets the maximum value of the adjustable object.
   * @return the maximum value of the adjustable object
   */
  double getMaximum();

  /**
   * Sets the unit value increment for the adjustable object.
   * @param inc the unit increment
   */
  void setUnitIncrement(double inc);

  /**
   * Gets the unit value increment for the adjustable object.
   * @return the unit value increment for the adjustable object
   */
  double getUnitIncrement();

  /**
   * Sets the block value increment for the adjustable object.
   * @param inc the block increment
   */
  void setBlockIncrement(double inc);

  /**
   * Gets the block value increment for the adjustable object.
   * @return the block value increment for the adjustable object
   */
  double getBlockIncrement();

  /**
   * Sets the length of the proportional indicator of the
   * adjustable object.
   * @param v the length of the indicator
   */
  void setVisibleAmount(double v);

  /**
   * Gets the length of the proportional indicator.
   * @return the length of the proportional indicator
   */
  double getVisibleAmount();

  /**
   * Sets the current value of the adjustable object. If
   * the value supplied is less than <code>minimum</code>
   * or greater than <code>maximum</code> - <code>visibleAmount</code>,
   * then one of those values is substituted, as appropriate.
   * <p>
   * Calling this method does not fire an
   * <code>AdjustmentEvent</code>.
   *
   * @param v the current value, between <code>minimum</code>
   *    and <code>maximum</code> - <code>visibleAmount</code>
   */
  void setValue(double v);

  /**
   * Gets the current value of the adjustable object.
   * @return the current value of the adjustable object
   */
  double getValue();

  /**
   * Returns <code>true</code> if item is in the process of adjusting
   *
   * @return <code>true</code> if this is one of multiple
   *         adjustment events, otherwise returns <code>false</code>
   * @since 1.4
   */
  boolean isAdjusting();

  /**
   * Adds a listener to receive change events when the value of
   * the adjustable object changes.
   * @param l the listener to receive events
   */
  void addChangeListener(iChangeListener l);

  /**
   * Removes a change listener.
   * @param l the listener being removed
   */
  void removeChangeListener(iChangeListener l);
}
