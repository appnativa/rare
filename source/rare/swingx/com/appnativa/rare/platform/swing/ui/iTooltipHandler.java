/*
 * @(#)iTooltipHandler.java   2009-06-24
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JToolTip;

import com.appnativa.rare.widget.iWidget;

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
  Point getToolTipLocation(iWidget w,MouseEvent event);

  /**
   * Returns the string to be used as the tooltip for <i>event</i>.
   *
   * @param w the widget
   * @param event  the <code>MouseEvent</code> that caused the
   *          <code>ToolTipManager</code> to show the tooltip
   * @return the text or null for no tooltip
   */
  String getToolTipText(iWidget w,MouseEvent event);
}
