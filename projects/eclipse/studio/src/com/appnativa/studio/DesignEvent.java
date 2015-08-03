/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appnativa.studio;

import java.util.EventObject;

import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author decoteaud
 */
public class DesignEvent extends EventObject {
  private Object    data;
  private EventType event;
  private Object    owner;
  private iWidget   widget;

  public DesignEvent(Object source, Object owner) {
    super(source);
    this.owner = owner;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public void setEvent(EventType event) {
    this.event = event;
  }

  public void setWidget(iWidget widget) {
    this.widget = widget;
  }

  public Object getData() {
    return data;
  }

  public EventType getEvent() {
    return event;
  }

  public Object getOwner() {
    return owner;
  }

  public iWidget getWidget() {
    return widget;
  }
}
