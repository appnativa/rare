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

import com.appnativa.rare.ui.text.iTextAttributes;

/**
 * A document change event for notifying event handlers about
 * changes to a document
 *
 * @author Don DeCoteau
 */
public class DocumentChangeEvent extends ChangeEvent {
  private iTextAttributes attributeSet;
  private EventType       eventType;
  private String          linkHref;
  private Object          linkItem;

  /**
   * Enumeration of possible event types
   */
  public static enum EventType {

    /** unknown/unclassified event */
    UNKNOWN,

    /** insertion event */
    INSERT,

    /** delete event */
    DELETE,

    /** modification state change event */
    MODIFICATION_STATE,

    /** style change event */
    STYLE_CHANGE,

    /** style navigation event */
    STYLE_NAVIGATION,

    /** entered a hyper link */
    LINK_ENTERED,

    /** exited a hyper link */
    LINK_EXITED,

    /** clicked on a hyper link */
    LINK_CLICKED,
  }

  /**
   * Creates a new instance
   *
   * @param source the source of the event
   */
  public DocumentChangeEvent(Object source) {
    super(source);
  }

  /**
   * Creates a new instance
   *
   * @param source the source of the event
   */
  public DocumentChangeEvent(Object source, EventType type) {
    super(source);
    eventType = type;
  }

  /**
   * Creates a new hyperlink event
   * @param source the source of the event
   * @param type the hyperlink event type
   * @param item the item for the link (can be a url or and icon)
   * @param href the description of the link. This may be useful when attempting to form a URL resulted in a MalformedURLException.
   *              The description provides the text (href) used when attempting to form the URL
   * @return a new hyperlink event
   */
  public static DocumentChangeEvent hyperlinkEvent(Object source, EventType type, Object item, String href) {
    DocumentChangeEvent de = new DocumentChangeEvent(source);

    de.eventType = type;
    de.linkItem  = item;
    de.linkHref  = href;

    return de;
  }

  /**
   * Creates a modification change event
   *
   * @param source the source of the event
   *
   * @return a modification change event
   */
  public static DocumentChangeEvent modificationEvent(Object source) {
    DocumentChangeEvent e = new DocumentChangeEvent(source);

    e.eventType = EventType.MODIFICATION_STATE;

    return e;
  }

  /**
   * Creates a new style navigation event
   *
   * @param source the source of the event
   * @param set the attribute set for the style at the current cursor position
   *
   * @return a style navigation event
   *
   */
  public static DocumentChangeEvent navigationEvent(Object source, iTextAttributes set) {
    DocumentChangeEvent e = new DocumentChangeEvent(source);

    e.eventType    = EventType.STYLE_NAVIGATION;
    e.attributeSet = set;

    return e;
  }

  /**
   * Gets the text name of the event
   * @return  the text name of the event
   */
  public String getEventName() {
    return (eventType == null)
           ? ""
           : eventType.name();
  }

  /**
   * Gets the type of change event
   *
   * @return the type of change event
   */
  public EventType getEventType() {
    return eventType;
  }

  /**
   * @return the linkHref
   */
  public String getLinkHref() {
    return linkHref;
  }

  /**
   * Get the link item (can be a URL or an icon) for a hyperlink event
   * @return the link URLK for a hyperlink event
   */
  public Object getLinkItem() {
    return linkItem;
  }

  /**
   * Gets the attribute set representing the navigation style for a navigation
   * style change event
   *
   * @return the navigation style
   */
  public iTextAttributes getNavigationStyle() {
    return attributeSet;
  }

  /**
   * Returns whether the event represents a hyperlink click event
   * @return true if the event represents a hyperlink click event; false otherwise
   */
  public boolean isHyperlinkClickEvent() {
    switch(eventType) {
      case LINK_CLICKED :
        return true;

      default :
        break;
    }

    return false;
  }

  /**
   * Returns whether the event represents a hyperlink event
   * @return true if the event represents a hyperlink event; false otherwise
   */
  public boolean isHyperlinkEvent() {
    switch(eventType) {
      case LINK_EXITED :
      case LINK_ENTERED :
      case LINK_CLICKED :
        return true;

      default :
        break;
    }

    return false;
  }

  /**
   * Returns whether the event is a modification event
   *
   * @return true if the event is a modification event; false otherwise
   */
  public boolean isModificationEvent() {
    return eventType == EventType.MODIFICATION_STATE;
  }

  /**
   * Returns whether the event is a style change event
   *
   * @return true if the event is a style change event; false otherwise
   */
  public boolean isStyleChangeEvent() {
    return eventType == EventType.STYLE_CHANGE;
  }

  /**
   * Returns whether the event is a style navigation event
   *
   * @return true if the event is a style navigation event; false otherwise
   */
  public boolean isStyleNavigationEvent() {
    return eventType == EventType.STYLE_NAVIGATION;
  }
}
