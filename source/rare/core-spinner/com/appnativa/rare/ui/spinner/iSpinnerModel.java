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

package com.appnativa.rare.ui.spinner;

import com.appnativa.rare.ui.event.iChangeListener;

/**
 *
 * @author Don DeCoteau
 */
public interface iSpinnerModel {

  /**
   * Adds a <code>iChangeListener</code> to the model's listener list.  The
   * <code>ChangeListeners</code> must be notified when models <code>value</code>
   * changes.
   *
   * @param l the iChangeListener to add
   * @see #removeChangeListener
   */
  void addChangeListener(iChangeListener l);

  /**
   * Removes a <code>iChangeListener</code> from the model's listener list.
   *
   * @param l the iChangeListener to remove
   * @see #addChangeListener
   */
  void removeChangeListener(iChangeListener l);

  /**
   * Changes current value of the model, typically this value is displayed
   * by the <code>editor</code> part of a  <code>JSpinner</code>.
   * If the <code>SpinnerModel</code> implementation doesn't support
   * the specified value then an <code>IllegalArgumentException</code>
   * is thrown.  For example a <code>SpinnerModel</code> for numbers might
   * only support values that are integer multiples of ten. In
   * that case, <code>model.setValue(new Number(11))</code>
   * would throw an exception.
   *
   * @throws IllegalArgumentException if <code>value</code> isn't allowed
   * @see #getValue
   */
  void setValue(Object value);

  /**
   * Return the object in the sequence that comes after the object returned
   * by <code>getValue()</code>. If the end of the sequence has been reached
   * then return null.  Calling this method does not effect <code>value</code>.
   *
   * @return the next legal value or null if one doesn't exist
   * @see #getValue
   * @see #getPreviousValue
   */
  Object getNextValue();

  /**
   * Return the object in the sequence that comes before the object returned
   * by <code>getValue()</code>.  If the end of the sequence has been reached then
   * return null. Calling this method does not effect <code>value</code>.
   *
   * @return the previous legal value or null if one doesn't exist
   * @see #getValue
   * @see #getNextValue
   */
  Object getPreviousValue();

  /**
   * The <i>current element</i> of the sequence.  This element is usually
   * displayed by the <code>editor</code> part of a <code>JSpinner</code>.
   *
   * @return the current spinner value.
   * @see #setValue
   */
  Object getValue();

  boolean isEditable();

  boolean isCircular();

  Object fromString(String value);

  String toString(Object value);
}
