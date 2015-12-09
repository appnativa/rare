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

import com.appnativa.rare.exception.ExpandVetoException;
import com.appnativa.rare.widget.iWidget;

import com.google.j2objc.annotations.Weak;

/**
 * An expansion event
 *
 * @author Don DeCoteau
 */
public class ExpansionEvent extends EventBase {
  Type type;

  public static enum Type {
    WILL_EXPAND, WILL_COLLAPSE, HAS_EXPANDED, HAS_COLLAPSED
  }

  @Weak
  private Object eventItem;

  /**
   *  Creates a new instance of CollapsibleEvent
   *
   * @param type the event type
   *  @param source the source
   */
  public ExpansionEvent(Object source, Type type) {
    super(source);
    this.type = type;
  }

  /**
   * Creates a new instance of CollapsibleEvent
   *
   * @param source the source
   * @param type the event type
   * @param item the item associated with the event
   */
  public ExpansionEvent(Object source, Type type, Object item) {
    super(source);
    this.type = type;
    eventItem = item;
  }

  /**
   * Gets the type of the event
   * @return the type of the event
   */
  public Type getEventType() {
    return type;
  }

  /**
   * Rejects the expansion
   *
   * @throws ExpandVetoException
   */
  public void reject() {
    consumed = true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ExpansionEvent");

    if (getSource() instanceof iWidget) {
      sb.append("\n  source=").append(((iWidget) getSource()).getName());
    } else {
      sb.append("\n  source=").append(getSource().toString());
    }

    if (eventItem != null) {
      sb.append("eventItem=").append(eventItem.toString());
    }

    return sb.toString();
  }

  /**
   * Sets the item associated with the event
   *
   * @param item the item associated with the event
   */
  public void setItem(Object item) {
    eventItem = item;
  }

  /**
   * Gets the item associated with the event
   *
   * @return the item associated with the event
   */
  public Object getItem() {
    return eventItem;
  }

  /**
   * Returns whether of the this event represents and event of the specified type
   *
   * @param type the type
   * @return true if it does; false otherwise
   */
  public boolean isType(Type type) {
    return this.type == type;
  }
}
