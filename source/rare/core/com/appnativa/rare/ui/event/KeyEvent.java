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

import com.appnativa.rare.platform.EventHelper;

/**
 *
 * @author Don DeCoteau
 */
public class KeyEvent extends InputEvent {
  public static enum Type { KEY_UP, KEY_DOWN, KEY_TYPED, KEY_UNKNOWN}

  public KeyEvent(Object source, Object ke) {
    super(source, ke);
  }

  public int getKeyChar() {
    return EventHelper.getKeyChar(nativeEvent);
  }

  public int getKeyCode() {
    return EventHelper.getKeyCode(nativeEvent);
  }

  public Type getEventType() {
    return EventHelper.getKeyType(nativeEvent);
  }

  public int getWhich() {
    return EventHelper.getWhich(nativeEvent);
  }

  public boolean isAltKeyPressed() {
    return EventHelper.isAltKeyPressed(nativeEvent);
  }

  public boolean isBackspaceKeyPressed() {
    return EventHelper.isBackspaceKeyPressed(nativeEvent);
  }

  public boolean isDeleteKeyPressed() {
    return EventHelper.isDeleteKeyPressed(nativeEvent);
  }

  public boolean isDownArrowKeyPressed() {
    return EventHelper.isDownArrowKeyPressed(nativeEvent);
  }

  public boolean isEndPressed() {
    return EventHelper.isEndKeyPressed(nativeEvent);
  }

  public boolean isEnterKeyPressed() {
    return EventHelper.isEnterKeyPressed(nativeEvent);
  }

  public boolean isEscapeKeyPressed() {
    return EventHelper.isEscapeKeyPressed(nativeEvent);
  }

  public boolean isHomePressed() {
    return EventHelper.isHomeKeyPressed(nativeEvent);
  }

  public boolean isLeftArrowKeyPressed() {
    return EventHelper.isLeftArrowKeyPressed(nativeEvent);
  }

  public boolean isMetaKeyPressed() {
    return EventHelper.isMetaKeyPressed(nativeEvent);
  }

  public boolean isPageDownKeyPressed() {
    return EventHelper.isPageDownKeyPressed(nativeEvent);
  }

  public boolean isPageUpKeyPressed() {
    return EventHelper.isPageUpKeyPressed(nativeEvent);
  }

  public boolean isRightArrowKeyPressed() {
    return EventHelper.isRightArrowKeyPressed(nativeEvent);
  }

  public boolean isShiftKeyPressed() {
    return EventHelper.isShiftKeyPressed(nativeEvent);
  }

  public boolean isTabKeyPressed() {
    return EventHelper.isTabKeyPressed(nativeEvent);
  }

  public boolean isUpArrowKeyPressed() {
    return EventHelper.isUpArrowKeyPressed(nativeEvent);
  }

  /**
   * Returns whether of the this event represents and event of the specified type
   *
   * @param type the type
   * @return true if it does; false otherwise
   */
  public boolean isType(Type type) {
    return type == getEventType();
  }
}
