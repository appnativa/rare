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

/**
 *  A type identifying when a render-able element is displayed
 */
public enum Displayed {

  /** the item is always displayed */
  ALWAYS,

  /** the item is always displayed when the component does not have focus */
  WHEN_NOT_FOCUSED,

  /** the item is displayed only when the component does not have focus */
  WHEN_FOCUSED,

  /** the item is always displayed when the component window does not have focus */
  WHEN_WINDOW_NOT_FOCUSED,

  /** the item is displayed only when the component window has focus */
  WHEN_WINDOW_FOCUSED,

  /** the item is always displayed when the component widget does not have focus */
  WHEN_WIDGET_NOT_FOCUSED,

  /** the item is displayed only when the component's widget has focus */
  WHEN_WIDGET_FOCUSED,

  /** the item is always displayed when the component's parent does not have focus */
  WHEN_PARENT_WIDGET_NOT_FOCUSED,

  /** the item is displayed only when the component's parent has focus */
  WHEN_PARENT_WIDGET_FOCUSED,

  /** the item is always displayed when none of the component's children has focus */
  WHEN_CHILD_WIDGET_NOT_FOCUSED,

  /** the item is displayed only when the component child has focus */
  WHEN_CHILD_WIDGET_FOCUSED,

  /** the item is always displayed when the component does not have focus an does not have a value */
  WHEN_EMPTY,

  /** the item is displayed only before the user interacts with the component and is removed completely after the first user interaction */
  BEFORE_INTERACTION,

  /** the item is displayed only before the component first receives focus */
  BEFORE_FIRST_FOCUS,
}
