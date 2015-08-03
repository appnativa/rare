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

import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.util.Map;

/**
 * Interface for components that can be used as renderers
 *
 * @author Don DeCoteau
 */
public interface iRenderingComponent {

  /**
   * Sets the component's background color
   *
   * @param bg the background color
   */
  public void setBackground(UIColor bg);

  /**
   * Sets the component's border
   *
   * @param b the border
   */
  public void setBorder(iPlatformBorder b);

  /**
   * Sets the component painter for the renderer
   *
   * @param cp the component painter for the renderer
   */
  public void setComponentPainter(iPlatformComponentPainter cp);

  /**
   * Sets whether the component is enabled
   * @param enabled true for enabled; false otherwise
   */
  public void setEnabled(boolean enabled);

  /**
   * Sets the widget's font
   * @param font the font for the item
   */
  public void setFont(UIFont font);

  /**
   * Sets the component's foreground color
   *
   * @param fg the foreground color
   */
  public void setForeground(UIColor fg);

  /**
   * Sets the component's icon
   *
   * @param icon the component's icon
   */
  public void setIcon(iPlatformIcon icon);

  /**
   *  Sets the icon position
   *
   *  @param position the icon position
   */
  public void setIconPosition(IconPosition position);

  /**
   * Sets rendering options
   *
   * @param options the rendering option
   */
  public void setOptions(Map<String, Object> options);

  /**
   * Dispose of the renderer
   */
  void dispose();

  /**
   * Set the alignment for the content
   *
   * @param ha the horizontal alignment
   * @param va the vertical alignment
   */
  void setAlignment(HorizontalAlign ha, VerticalAlign va);

  /**
   * Sets the margin for the renderer's content
   *
   * @param insets the insets;
   */
  void setMargin(UIInsets insets);

  /**
   * Sets the orientation for the content
   * @param o the orientation
   */
  void setOrientation(Orientation o);

  /**
   * Sets whether text is wrapped or not
   * @param wrap
   */
  void setWordWrap(boolean wrap);

  /**
   * Get the component for the renderer
   * @return the component for the renderer
   */
  iPlatformComponent getComponent();

  /**
   * Removes the render visual elements (text, background, border ,icon)
   */
  void clearRenderer();

  void setScaleIcon(boolean scale, float scaleFactor);
}
