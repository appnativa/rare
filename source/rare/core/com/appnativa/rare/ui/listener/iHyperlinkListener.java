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

package com.appnativa.rare.ui.listener;

import com.appnativa.rare.ui.event.MouseEvent;

/**
 * An interface for managing hyperlink actions
 *
 * @author Don Decoteau
 */
public interface iHyperlinkListener {

  /**
   * Invoked when the link has been clicked
   *
   * @param source the source object
   * @param item the item that was clicked on (can be url or icon)
   * @param href the href for the click (can be null if clicked on an icon)
   * @param e the mouse event for the click
   */
  void linkClicked(Object source, Object item, String href, MouseEvent e);

  /**
   * Invoked when the mouse enters the link
   *
   * @param source the source object
   * @param item the item that was clicked on (can be url or icon)
   * @param href the href for the click (can be null if clicked on an icon)
   * @param e the mouse event for the click
   */
  void linkEntered(Object source, Object item, String href, MouseEvent e);

  /**
   * Invoked when the mouse exits the link
   *
   * @param source the source object
   * @param item the item that was clicked on (can be url or icon)
   * @param href the href for the click (can be null if clicked on an icon)
   * @param e the mouse event for the click
   */
  void linkExited(Object source, Object item, String href, MouseEvent e);
}
