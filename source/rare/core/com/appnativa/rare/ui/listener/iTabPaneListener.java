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

import com.appnativa.rare.ui.iTabDocument;

import java.util.EventListener;

/**
 * Interface  for tab document event listeners.
 *
 * @author Don DeCoteau
 */
public interface iTabPaneListener extends EventListener {

  /**
   * A tab was activated
   *
   * @param doc the tab document was activated
   */
  void tabActivated(iTabDocument doc);

  /**
   * A tab was closed
   *
   * @param doc the tab document was closed
   */
  void tabClosed(iTabDocument doc);

  /**
   * A tab was deactivated
   *
   * @param doc the tab document was deactivated
   */
  void tabDeactivated(iTabDocument doc);

  /**
   * A tab was opened
   *
   * @param doc the tab document was opened
   */
  void tabOpened(iTabDocument doc);

  /**
   * A tab is about to close
   *
   * @param doc the tab document that is about to close
   */
  void tabWillClose(iTabDocument doc);
}
