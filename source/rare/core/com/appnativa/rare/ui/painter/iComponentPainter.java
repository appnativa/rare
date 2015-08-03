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

package com.appnativa.rare.ui.painter;

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.border.SharedLineBorder;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;

import java.beans.PropertyChangeListener;

/**
 * Handle custom component painting
 * This painter is called twice. Once at the beginning of the paint cycle and
 * once at the end of the paint cycle
 *
 * @author Don DeCoteau
 */
public interface iComponentPainter extends iBackgroundPainter {
  public static String PROPERTY_BACKGROUND_COLOR           = "BACKGROUND_COLOR";
  public static String PROPERTY_BACKGROUND_OVERLAY_PAINTER = "BACKGROUND_OVERLAY_PAINTER";
  public static String PROPERTY_BACKGROUND_PAINTER         = "BACKGROUND_PAINTER";
  public static String PROPERTY_BORDER                     = "BORDER";
  public static String PROPERTY_DISPOSED                   = "DISPOSED";
  public static String PROPERTY_OVERLAY_PAINTER            = "OVERLAY_PAINTER";
  public static String PROPERTY_PAINTER_HOLDER             = "PROPERTY_PAINTER_HOLDER";

  void addPropertyChangeListener(PropertyChangeListener listener);

  void clear();

  void copyFrom(iPlatformComponentPainter cp);

  /**
   * Performs the painting
   *
   * @param g
   *          the graphics object
   * @param x
   *          the x position for the shape
   * @param y
   *          the y position for the shape
   * @param width
   *          the width of the area to get a shape for
   * @param height
   *          the height of the area to get a shape for
   * @param orientation
   *          the orientation. One of iPlatformPainter.HORIZONTAL, iPlatformPainter.VERTICAL,
   *          iPlatformPainter.UNKNOWN
   * @param end
   *          true if this is the end of the draw cycle; fals if it is the begining
   */
  void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation, boolean end);

  void removePropertyChangeListener(PropertyChangeListener listener);

  /**
   * Sets the background overlay painter
   * This painter paints over the background right after the background is painted
   * and before the contents are painted
   *
   * @param bop the painter
   */
  void setBackgroundOverlayPainter(iPlatformPainter bop);

  /**
   * Sets the background color that will be used to paint the component's background
   *
   * @param bg the color this color will replace any set background painters and replace the normal painter background
   * for any installed painter holder.
   */
  void setBackgroundColor(UIColor bg);

  /**
   * Sets the background color that will be used to paint the component's background
   *
   * @param bg the color this color will replace any set background painters
   *
   * @param checkOthers true to check and fix any installed painter holder; false to just set the background
   */
  void setBackgroundColor(UIColor bg, boolean checkOthers);

  /**
   * Sets the painter that will paint the component's background
   *
   * @param bp the painter
   * @param checkOthers true to check and fix any installed background/painter holder; false to just set the painter
   */
  void setBackgroundPainter(iBackgroundPainter bp, boolean checkOthers);

  /**
   * Sets the border
   * @param border the border
   */
  void setBorder(iPlatformBorder border);

  /**
   * Sets a foreground color that can be used for foreground drawing.
   * By default painters don't do foreground drawing so the is generally
   * for use external to the painter
   *
   * @param fg the color to set
   */
  void setForegroundColor(UIColor fg);

  /**
   * Sets a foreground color that can be used for foreground drawing when the component
   * is disabled. You should only calls this method if you also called {@link iComponentPainter#setForegroundColor(UIColor)}
   *
   * By default painters don't do foreground drawing so the is generally
   * for use external to the painter
   *
   * @param fg the color to set
   */
  void setDisabledForegroundColor(UIColor fg);

  /**
   * Sets the overlay painter.
   * This painter paints after the component's contents have been painted
   *
   * @param op the painter
   */
  void setOverlayPainter(iPlatformPainter op);

  /**
   * Sets a paint holder that contains painters for different component states
   * @param painterHolder the paint holder
   */
  void setPainterHolder(PainterHolder painterHolder);

  void setSharedBorder(SharedLineBorder sharedBorder);

  /**
   * Gets the overlay background painter.
   * This painter paints over the background right after the background is painted
   * and before the contents are painted
   *
   * @return the painter
   */
  iPlatformPainter getBackgroundOverlayPainter();

  /**
   * Gets the gradient painter
   *
   * @return the gradient painter
   */
  iBackgroundPainter getBackgroundPainter();

  /**
   * Gets the border set on this painter;
   *
   * @return the border
   */
  iPlatformBorder getBorder();

  /**
   * Gets the border set on this painter or for an associated paint holder;
   *
   * @return the border
   */
  iPlatformBorder getBorderAlways();

  /**
   * Gets a foreground color that can be used for foreground drawing.
   * By default painters don't do fore ground drawing so the is generally
   * for use external to the painter
   *
   * @return a color for foreground drawing or null if one was not set
   */
  UIColor getForegroundColor();

  /**
   * Gets the overlay painter.
   * This painter paints after the component's contents have been painted
   *
   * @return the overlay painter
   */
  iPlatformPainter getOverlayPainter();

  /**
   * Gets a paint holder that contains painters for different component states
   *
   * @return  a paint holder if one was set; others null
   */
  PainterHolder getPainterHolder();

  SharedLineBorder getSharedBorder();

  /**
   * Returns whether background painting is enabled (image or gradient)
   *
   * @return true if background painting is enabled; false otherwise
   */
  boolean isBackgroundPaintEnabled();
}
