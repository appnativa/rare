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

package com.appnativa.rare.platform.swing.ui;

import com.appnativa.rare.widget.iWidget;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JToolTip;

/**
 * An interface for constructing tooltip information.
 *
 * @author Don DeCoteau
 */
public interface iTooltipHandler {

  /**
   * Returns the instance of <code>JToolTip</code> that should be used
   * to display the tooltip.
   *
   * @param w the widget
   *
   * @return the <code>JToolTip</code> used to display this toolTip
   */
  JToolTip createToolTip(iWidget w);

  /**
   * Returns the tooltip location in this component's coordinate system.
   * If <code>null</code> is returned, Swing will choose a location.
   * The default implementation returns <code>null</code>.
   *
   * @param w the widget
   * @param event  the <code>MouseEvent</code> that caused the
   *          <code>ToolTipManager</code> to show the tooltip
   * @return the location or null for the default
   */
  Point getToolTipLocation(iWidget w, MouseEvent event);

  /**
   * Returns the string to be used as the tooltip for <i>event</i>.
   *
   * @param w the widget
   * @param event  the <code>MouseEvent</code> that caused the
   *          <code>ToolTipManager</code> to show the tooltip
   * @return the text or null for no tooltip
   */
  String getToolTipText(iWidget w, MouseEvent event);
}
