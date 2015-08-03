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

import java.util.EventListener;

/**
 * A popup menu listener
 *
 * @version 1.10 11/17/05
 * @author Arnaud Weber
 */
public interface iPopupMenuListener extends EventListener {

  /**
   * This method is called when the popup menu is canceled
   */
  void popupMenuCanceled(ExpansionEvent e);

  /**
   * This method is called before the popup menu becomes invisible
   * Note that a JPopupMenu can become invisible any time
   */
  void popupMenuWillBecomeInvisible(ExpansionEvent e);

  /**
   *  This method is called before the popup menu becomes visible
   */
  void popupMenuWillBecomeVisible(ExpansionEvent e);
}
