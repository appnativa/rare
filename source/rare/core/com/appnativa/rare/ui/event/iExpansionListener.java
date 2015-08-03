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

import java.util.EventListener;

/**
 * An interface for listening for pre-expansion and pre-collapsing events.
 *
 * @author Don DeCoteau
 */
public interface iExpansionListener extends EventListener {

  /**
   * Called when an item will collapse
   *
   * @param event the event
   *
   * @throws ExpandVetoException if the collapsing should be vetoed
   */
  public void itemWillCollapse(ExpansionEvent event) throws ExpandVetoException;

  /**
   * Called when an item will expand
   *
   * @param event the event
   *
   * @throws ExpandVetoException if the expansion should be vetoed
   */
  public void itemWillExpand(ExpansionEvent event) throws ExpandVetoException;
}
