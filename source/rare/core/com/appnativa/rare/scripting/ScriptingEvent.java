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

package com.appnativa.rare.scripting;

import com.appnativa.rare.Platform;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.ui.event.DataLoadEvent;
import com.appnativa.rare.ui.event.EventBase;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.widget.iWidget;

import com.google.j2objc.annotations.Weak;

import java.util.EventObject;

/**
 * This object represents the state of an event, such as
 * the element in which the event occurred, the state of the keyboard keys,
 * the location of the mouse, and the state of the mouse buttons, etc.
 *
 * @author Don DeCoteau
 */
public class ScriptingEvent implements Cloneable, iScriptingContextSupport {

  /** event name */
  protected String eventType;

  /** event related widget */
  @Weak
  protected Object relatedWidget;

  /** event return value */
  protected boolean returnValue;

  /** event source widget */
  @Weak
  protected Object sourceWidget;

  /** UI event object */
  protected EventObject uiEvent;

  /** if this is an invoke later event */
  private boolean invokeLater;

  /** script handler */
  @Weak
  private iScriptHandler scriptHandler;
  private WidgetContext  scriptingContext;

  /**
   * Constructs a new instance
   *
   * @param sh the script handler
   * @param type the event type
   * @param e the UI event object
   * @param source the source of the event
   * @param related the releated object in this event
   */
  public ScriptingEvent(iScriptHandler sh, String type, EventObject e, Object source, Object related) {
    setEvent(sh, type, e, source, related);
  }

  /**
   * Creates a new instance of ScriptingEvent
   */
  protected ScriptingEvent() {}

  /**
   * Consumes the event if it is consumable
   */
  public void consume() {
    if (uiEvent instanceof EventBase) {
      ((EventBase) uiEvent).consume();
    }
  }

  public void dispose() {
    if (scriptingContext != null) {
      scriptingContext.dispose();
    }

    scriptingContext = null;
  }

  /**
   * Creates and returns a copy of the event
   *
   * @return a copy of the event
   */
  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException ignore) {
      return new ScriptingEvent(scriptHandler, eventType, uiEvent, sourceWidget, relatedWidget);
    }
  }

  /**
   * Returns a string representation of the event
   *
   * @return  a string representation of the event
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(eventType);

    sb.append("->");

    if (sourceWidget instanceof iWidget) {
      sb.append("src=").append(((iWidget) sourceWidget).getName()).append(" ");
    }

    if (relatedWidget instanceof iWidget) {
      sb.append("from=").append(((iWidget) relatedWidget).getName());
    }

    if (uiEvent != null) {
      sb.append("\nuiEvent=").append(uiEvent.toString());
    }

    return sb.toString();
  }

  /**
   * @param invokeLater the invokeLater to set
   */
  public void setInvokeLater(boolean invokeLater) {
    this.invokeLater = invokeLater;
  }

  /**
   * Returrns the action link for a data load event
   *
   * @return the action link for a data load event
   */
  public ActionLink getActionLink() {
    if (uiEvent instanceof DataLoadEvent) {
      return ((DataLoadEvent) uiEvent).getActionLink();
    }

    return null;
  }

  /**
   * Returns whether the alt key is down
   * @return true if it is false otherwise
   */
  public boolean getAltKey() {
    if (uiEvent instanceof KeyEvent) {
      return ((KeyEvent) uiEvent).isAltKeyPressed();
    }

    return false;
  }

  /**
   * Returns which mouse button was clicked when an event was triggered
   *
   * @return 0 if no button was pressed; other 1=left, 2=right, 4=middle
   */
  public int getButton() {
    return 1;
  }

  /**
   * Returns the Unicode character code associated with the key that caused the event.
   *
   * @return the character code
   */
  public int getCharCode() {
    if (uiEvent instanceof KeyEvent) {
      int n = ((KeyEvent) uiEvent).getKeyChar();

      return (n == 10)
             ? 13
             : n;
    }

    return -1;
  }

  /**
   * Returns the number of mouse clicks associated with this event.
   *
   * @return the number of mouse clicks associated with this event.
   */
  public int getClickCount() {
    if (uiEvent instanceof MouseEvent) {
      return ((MouseEvent) uiEvent).getClickCount();
    }

    return 0;
  }

  /**
   * Returns the x-coordinate of the mouse pointer's position relative to the client
   * area of the window, excluding window decorations and scroll bars.
   *
   * @return the x-coordinate
   */
  public float getClientX() {
    if (uiEvent instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) uiEvent;

      return e.getWindowX();
    }

    return -1;
  }

  /**
   * Returns the y-coordinate of the mouse pointer's position relative to the client
   * area of the window, excluding window decorations and scroll bars.
   *
   * @return the y-coordinate
   */
  public float getClientY() {
    if (uiEvent instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) uiEvent;

      return e.getWindowY();
    }

    return -1;
  }

  /**
   * Returns the underlying UI component that triggered the event
   *
   * @return the underlying UI component that triggered the event
   */
  public iPlatformComponent getComponent() {
    return PlatformHelper.componentForEvent(uiEvent);
  }

  /**
   * Returns whether the control key is down
   * @return true if it is false otherwise
   */
  public boolean getCtrlKey() {
    return false;
  }

  /**
   * Returns the data associated with the event
   *
   * @return the data associated with the event or null
   */
  public Object getData() {
    if (uiEvent instanceof ItemChangeEvent) {
      return ((ItemChangeEvent) uiEvent).getNewValue();
    }

    if (uiEvent instanceof DataEvent) {
      return ((DataEvent) uiEvent).getData();
    }

    if (uiEvent instanceof DataLoadEvent) {
      return ((DataLoadEvent) uiEvent).getActionLink();
    }

    return null;
  }

  /**
   * Returns the widget that caused the event to be triggered on the source (toElement) widget
   *
   * @return the widget
   */
  public Object getFromElement() {
    return relatedWidget;
  }

  /**
   * Returns the java keycode associated with the key that caused the event.
   *
   * @return the java key code
   */
  public int getKeyCode() {
    if (uiEvent instanceof KeyEvent) {
      int n = ((KeyEvent) uiEvent).getKeyCode();

      return (n == 10)
             ? 13
             : n;
    }

    return -1;
  }

  /**
   * Returns the x-coordinate of the mouse pointer's position relative to the object firing the event.
   *
   * @return the x-coordinate of the mouse pointer's position relative to the object firing the event.
   */
  public float getOffsetLeft() {
    if (uiEvent instanceof MouseEvent) {
      return ((MouseEvent) uiEvent).getX();
    }

    return -1;
  }

  /**
   * Returns the y-coordinate of the mouse pointer's position relative to the object firing the event.
   *
   * @return the y-coordinate of the mouse pointer's position relative to the object firing the event.
   */
  public float getOffsetTop() {
    if (uiEvent instanceof MouseEvent) {
      return ((MouseEvent) uiEvent).getY();
    }

    return -1;
  }

  /**
   * Returns the x-coordinate of the mouse pointer's position relative to the client
   * area of the window, excluding window decorations and scroll bars.
   *
   * @return the x-coordinate
   */
  public float getPageX() {
    if (uiEvent instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) uiEvent;

      return e.getWindowX();
    }

    return -1;
  }

  /**
   * Returns the y-coordinate of the mouse pointer's position relative to the client
   * area of the window, excluding window decorations and scroll bars.
   *
   * @return the y-coordinate
   */
  public float getPageY() {
    if (uiEvent instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) uiEvent;

      return e.getWindowY();
    }

    return -1;
  }

  /**
   * Returns the widget that caused the event to be triggered on the source (toElement) widget
   *
   * @return the widget
   */
  public Object getRelatedTarget() {
    return relatedWidget;
  }

  /**
   * Returns the y-coordinate of the mouse pointer's position on the screen
   *
   * @return the y-coordinate of the mouse pointer's position on the screen
   */
  public float getSceeenY() {
    if (uiEvent instanceof MouseEvent) {
      return ((MouseEvent) uiEvent).getScreenY();
    }

    return -1;
  }

  /**
   * Returns the x-coordinate of the mouse pointer's position relative to the object firing the event.
   *
   * @return the x-coordinate of the mouse pointer's position relative to the object firing the event.
   */
  public float getScreenX() {
    if (uiEvent instanceof MouseEvent) {
      return ((MouseEvent) uiEvent).getScreenX();
    }

    return -1;
  }

  /**
   * Gets the script handler associated with the event
   * @return the script handler associated with the event
   */
  public iScriptHandler getScriptHandler() {
    return scriptHandler;
  }

  /**
   * Returns whether the shift key is down
   * @return true if it is false otherwise
   */
  public boolean getShiftKey() {
    if (uiEvent instanceof KeyEvent) {
      return ((KeyEvent) uiEvent).isShiftKeyPressed();
    }

    return false;
  }

  /**
   * Returns the widget that triggered the event
   *
   * @return the widget that triggered the event
   */
  public Object getSource() {
    return sourceWidget;
  }

  /**
   * Gets the scripting context for the source widget
   *
   * @return the scripting context for the source widget
   */
  public WidgetContext getSourceContext() {
    if (sourceWidget instanceof iWidget) {
      return ((iWidget) sourceWidget).getScriptingContext();
    }

    return null;
  }

  /**
   * Returns the widget that triggered the event
   *
   * @return the widget that triggered the event
   */
  public Object getSrcElement() {
    return sourceWidget;
  }

  /**
   * Gets the scripting context for the target widget
   *
   * @return the scripting context for the target widget
   */
  public WidgetContext getTargetContext() {
    if (relatedWidget instanceof iWidget) {
      return ((iWidget) relatedWidget).getScriptingContext();
    }

    return null;
  }

  /**
   * Returns the widget that triggered the event
   *
   * @return the widget that triggered the event
   */
  public Object getToElement() {
    return sourceWidget;
  }

  /**
   * Returns the event name
   *
   * @return the event name
   */
  public String getType() {
    return eventType;
  }

  /**
   * Returns the underlying UI event object
   *
   * @return the underlying UI event object
   */
  public EventObject getUIEvent() {
    return uiEvent;
  }

  /**
   * Returns the underlying UI event name
   *
   * @return the underlying UI event object
   */
  public String getUIEventName() {
    return (uiEvent == null)
           ? ""
           : uiEvent.getClass().getSimpleName();
  }

  /**
   * Returns the java keycode associated with the key that caused the event.
   *
   * @return the java key code
   */
  public int getWhich() {
    if (uiEvent instanceof KeyEvent) {
      return ((KeyEvent) uiEvent).getWhich();
    }

    return -1;
  }

  /**
   * @return the invokeLater
   */
  public boolean isInvokeLater() {
    return invokeLater;
  }

  /**
   * Returns whether the left mouse button was pressed for a mouse event
   *
   * @return true if it was pressed; false otherwise
   */
  public boolean isLeftMouseButton() {
    return (getButton() & 1) > 0;
  }

  /**
   * Returns whether or not the "META" key was pressed when an event was triggered
   *
   * @return true if it was pressed; false otherwise
   */
  public boolean isMetaKey() {
    if (uiEvent instanceof KeyEvent) {
      return ((KeyEvent) uiEvent).isMetaKeyPressed();
    }

    return false;
  }

  /**
   * Returns whether the middle mouse button was pressed for a mouse event
   *
   * @return true if it was pressed; false otherwise
   */
  public boolean isMiddleMouseButton() {
    return (getButton() & 4) > 0;
  }

  /**
   * Gets the return value from the event.
   *
   * @return true  the value from the event is returned (the default) otherwise
   * false indicates that the default action of the event on the source object is canceled.
   */
  public boolean isReturnValue() {
    return returnValue;
  }

  /**
   * Returns whether the right mouse button was pressed for a mouse event
   *
   * @return true if it was pressed; false otherwise
   */
  public boolean isRightMouseButton() {
    return (getButton() & 2) > 0;
  }

  /**
   * Returns whether the event is the trigger for a popup
   *
   * @return true if it is; false otherwise
   */
  public boolean isPopupTrigger() {
    if (uiEvent instanceof MouseEvent) {
      return ((MouseEvent) uiEvent).isPopupTrigger();
    }

    return false;
  }

  /**
   * Returns mouse wheel detail
   * @return mouse wheel detail
   */
  public int getDetail() {
    return getDetailY();
  }

  /**
   * Returns mouse fling X detail
   * @return mouse fling X detail
   */
  public int getDetailX() {
    if (uiEvent instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) uiEvent;

      return (int) e.getGestureX();
    }

    return 0;
  }

  /**
   * Returns mouse fling Y detail
   * @return mouse fling Y detail
   */
  public int getDetailY() {
    if (uiEvent instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) uiEvent;

      return (int) e.getGestureY();
    }

    return 0;
  }

  /**
   * Returns mouse wheel delta
   * @return mouse wheel delta
   */
  public int getWheelDelta() {
    return getDetailY() * 120;
  }

  /**
   * Returns whether or not the "Shift" key was pressed when an event was triggered
   *
   * @return true if it was pressed; false otherwise
   */
  public boolean isShiftKey() {
    if (uiEvent instanceof KeyEvent) {
      return ((KeyEvent) uiEvent).isShiftKeyPressed();
    }

    return false;
  }

  /**
   *  Returns whether or not the "Esc/Back key was pressed when an event was triggered
   *
   *  @return true if it was pressed; false otherwise
   */
  public boolean isEscapeKey() {
    if (uiEvent instanceof KeyEvent) {
      return ((KeyEvent) uiEvent).isEscapeKeyPressed();
    }

    return false;
  }

  /**
   * Sets the event object state
   *
   * @param sh the script handler
   * @param type the event type
   * @param e the UI event object
   * @param source the source of the event
   * @param related the releated object in this event
   */
  protected void setEvent(iScriptHandler sh, String type, EventObject e, Object source, Object related) {
    scriptHandler = sh;
    eventType     = type;
    uiEvent       = e;
    returnValue   = true;
    sourceWidget  = source;
    relatedWidget = related;
  }

  @Override
  public WidgetContext getScriptingContext() {
    if (scriptingContext == null) {
      scriptingContext = Platform.getAppContext().getScriptingManager().createScriptingContext(this);
    }

    return scriptingContext;
  }
}
