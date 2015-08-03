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

package com.appnativa.rare.ui.event;

/**
 * A class representing a change event for an editable item
 *
 * @author Don DeCoteau
 */
public class ItemChangeEvent extends ChangeEvent {
  private Object  oldValue, newValue;
  private String  rejectMessage;
  private boolean rejected;

  /**
   * Creates a new instance
   *
   * @param source the source of the event
   * @param oldValue the old value
   * @param newValue the new value
   */
  public ItemChangeEvent(Object source, Object oldValue, Object newValue) {
    super(source);
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  /**
   * Rejects the change causing the item to revert to it's old value
   *
   * @param message the rejection message
   *
   */
  public void reject(String message) {
    rejectMessage = message;
    rejected      = true;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("oldValue=");

    if (oldValue == null) {
      s.append("null");
    } else {
      s.append('"').append(oldValue).append('"');
    }

    s.append(", newValue=");

    if (newValue == null) {
      s.append("null");
    } else {
      s.append('"').append(newValue).append('"');
    }

    return s.toString();
  }

  /**
   * Allow the new value to be replaced with the specified value
   *
   * @param value the new value
   */
  public void setNewValue(Object value) {
    this.newValue = value;
  }

  /**
   * Gets the new value
   *
   * @return the new value
   */
  public Object getNewValue() {
    return newValue;
  }

  /**
   * Gets the old value
   *
   * @return the old value
   */
  public Object getOldValue() {
    return oldValue;
  }

  public String getRejectionMessage() {
    return rejectMessage;
  }

  public boolean isRejected() {
    return rejected;
  }
}
