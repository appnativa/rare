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

import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;

/**
 * An interface representing a frame within the main window.
 *
 * @author Don DeCoteau
 */
public interface iFrame {

  /**
   * Disposes of the frame
   */
  public void dispose();

  /**
   * Resets the frame
   */
  public void reset();

  /**
   * Sets the visibility of the frame
   *
   * @param visible true to make the frame visible; false otherwise
   */
  public void setFrameVisible(boolean visible);

  /**
   * Sets the icon for the frame
   *
   * @param icon the frame's icon
   */
  public void setIcon(iPlatformIcon icon);

  /**
   * Sets the title for the frame
   *
   * @param title the frame's title
   */
  public void setTitle(String title);

  /**
   * Sets the title and the icon for the frame
   *
   * @param title the frame's title
   * @param icon the frame's icon
   */
  public void setTitleAndIcon(String title, iPlatformIcon icon);

  /**
   * Sets the component to use for the frames title
   *
   * @param comp the component
   */
  public void setTitleComponent(iPlatformComponent comp);

  /**
   * Sets the title font for the frame
   *
   * @param f font
   */
  public void setTitleFont(UIFont f);

  /**
   * Get the viewer hosted by the frame's target
   *
   * @return the viewer hosted by the frame's target
   */
  public iViewer getContent();

  /**
   * Gets the component that is used as the frame
   *
   * @return the component
   */
  public iPlatformComponent getFrameComponent();

  /**
   * Get the frame's target
   *
   * @return the frame's target
   */
  public iTarget getTarget();

  /**
   * Get the  name of frame's target
   *
   * @return the name frame's target
   */
  public String getTargetName();

  /**
   * Gets the component that is used for the frames title
   *
   * @return the component
   */
  public iPlatformComponent getTitleComponent();
}
