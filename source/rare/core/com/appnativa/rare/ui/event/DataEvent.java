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

import com.appnativa.rare.widget.iWidget;

import com.google.j2objc.annotations.Weak;

import java.util.EventObject;

/**
 * An arbitrary event for passing data data to event handlers
 *
 * @author Don DeCoteau
 */
public class DataEvent extends ChangeEvent {

  /** the event specific data */
  protected Object    eventData;
  protected boolean   rejected;
  @Weak
  private Object      eventTarget;
  private EventObject triggerEvent;

  /**
   * Creates a new instance
   *
   * @param source the source of the event
   * @param data the data for the event
   */
  public DataEvent(Object source, Object data) {
    super(source);
    eventData = data;
  }

  /**
   * Creates a new instance of CollapsibleEvent
   *
   * @param source the source of the event
   * @param target the event target
   * @param item the data item for the event}
   */
  public DataEvent(Object source, Object target, Object item) {
    super(source);
    eventData   = item;
    eventTarget = target;
  }

  public void reject() {
    rejected = true;
  }

  /**
   * Returns a String representation of this DataEvent
   *
   * @return  A a String representation of this DataEvent.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("DataEvent");

    if (eventTarget instanceof iWidget) {
      sb.append("\n  target=").append(((iWidget) eventTarget).getName());
    }

    if (eventData != null) {
      sb.append("\n  data=").append(eventData.toString());
    }

    return sb.toString();
  }

  /**
   * Sets the data associated with the event
   *
   * @param data the data associated with the event
   */
  public void setData(Object data) {
    eventData = data;
  }

  /**
   * Sets the target of the event
   *
   * @param target the target of the event
   */
  public void setTarget(Object target) {
    this.eventTarget = target;
  }

  /**
   * @param triggerEvent the triggerEvent to set
   */
  public void setTriggerEvent(EventObject triggerEvent) {
    this.triggerEvent = triggerEvent;
  }

  /**
   * Gets the data associated with the event
   *
   * @return the data associated with the event
   */
  public Object getData() {
    return eventData;
  }

  /**
   * Gets the target of the event
   *
   * @return the target of the event
   */
  public Object getTarget() {
    return eventTarget;
  }

  /**
   * @return the triggerEvent
   */
  public EventObject getTriggerEvent() {
    return triggerEvent;
  }
}
