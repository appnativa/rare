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

package com.appnativa.rare.viewer;

import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

/**
 * Interface for targets for viewers
 *
 * @author     Don DeCoteau
 */
public interface iTarget {

  /** a string representing the name of the menu bar target */
  public static String TARGET_MENUBAR = "_menubar";

  /** a string representing the name of the new popup target */
  public static String TARGET_NEW_POPUP = "_new_popup";

  /** a string representing the name of the new window target */
  public static String TARGET_NEW_WINDOW = "_new_window";

  /** a string representing the name of the null target */
  public static String TARGET_NULL = "_null";

  /** a string representing the name of the parent target */
  public static String TARGET_PARENT = "_parent";

  /** a string representing the name of the self */
  public static String TARGET_SELF = "_self";

  /** a string representing the name of the toolbar target */
  public static String TARGET_TOOLBAR = "_toolbar";

  /** a string representing the name of the workspace target */
  public static String TARGET_WORKSPACE = "_workspace";

  /**
   * Activates the target causing it to take the appropriate steps
   * to gain focus
   */
  public void activate();

  /**
   * Disposes the target and optionally the currently hosted viewer
   *
   * @param disposeviewer true to dispose the currently hosted viewer; false otherwise
   */
  public void dispose(boolean disposeviewer);

  /**
   * Reloads the currently hosted viewer
   */
  public void reloadViewer();

  /**
   * Removes the currently hosted viewer
   *
   * @return the currently hosted viewer or no if one does not exist
   */
  public iViewer removeViewer();

  /**
   * Sets data to associate with the target
   *
   * @param data the data
   */
  public void setLinkedData(Object data);

  /**
   * Set the viewer to be hosted
   *
   * @param viewer the viewer
   *
   * @return the previously hosted viewer
   */
  public iViewer setViewer(iViewer viewer);

  /**
   * Sets the target's visible status
   *
   * @param visible <CODE>true</CODE> for visible; <CODE>false</CODE> otherwise
   */
  public void setVisible(boolean visible);

  /**
   * Gets the component that contain all the visual
   * elements necessary the display and manage the target
   *
   * @return the target's container
   */
  public iParentComponent getContainerComponent();

  /**
   * Gets the data associated with the target
   *
   * @return data the data
   */
  public Object getLinkedData();

  /**
   * Gets the name of the target
   *
   * @return the name of the target
   */
  public String getName();

  /**
   * Gets the dimension of the target's container
   *
   * @return the dimension of the target's container
   */
  public UIDimension getTargetSize();

  /**
   * Gets the currently hosted viewer
   *
   * @return the currently hosted viewer
   */
  public iViewer getViewer();

  /**
   * Returns whether the target is headless
   *
   * @return true if it is; false otherwise
   */
  public boolean isHeadless();

  /**
   * Returns whether the target is a popup window
   *
   * @return true if it is a popup window; false otherwise
   */
  public boolean isPopupWindow();

  /**
   * Returns whether the target is visible
   *
   * @return true if it is visible; false otherwise
   */
  public boolean isVisible();

  /**
   *  Causes the target to repaint
   */
  void repaint();

  /**
   * Causes the target to revalidate and repaint
   */
  void update();

  /**
   * Sets a background painter for the  target
   * @param painter the painter
   */
  void setBackgroundPainter(iBackgroundPainter painter);

  /**
   * Sets a component painter for the  target
   * @param painter the painter
   */
  void setComponentPainter(iPlatformComponentPainter painter);

  /**
   * Sets the locked state of a target
   *
   * @param lock true to lock the target; false to unlock
   *
   * @see #isLocked
   */
  void setLocked(boolean lock);

  /**
   * Sets the transition animator for the target
   * @param animator the animator
   */
  void setTransitionAnimator(iTransitionAnimator animator);

  /**
   * Gets the transition animator for the target
   * @return the transition animator for the target or null
   */
  iTransitionAnimator getTransitionAnimator();

  /**
   * <p>
   * Sets the render type for the target.
   * By default the render type is {@code RenderType#STRETCHED}.
   * The widget alignment properties of the configuration object
   * can be used to alter the render type.
   * </p>
   * <p>
   * If you want to control the layout of a widget within a target
   * then use the render type {@code RenderType#XY}. This is usefull for
   * animating the contents of a target
   * </p>
   * <p>
   * If a viewer has a method the set the render type, use that method instead of this one
   * </p>
   * @param renderType the render type
   *
   * @return true if the rendertype can be changed; false otherwise
   * @see WidgetPaneViewer#setRenderType(RenderType)
   * @see CollapsiblePaneViewer#setRenderType(RenderType)
   */
  boolean setRenderType(RenderType renderType);

  /**
   * Gets the render type for the target
   * @return the render type for the target or null if the target does no support the use if render types
   */
  RenderType getRenderType();

  /**
   * Returns whether the target is locked. Users cannot interact with a locked target
   *
   * @return true if the locked is locked; false otherwise
   */
  boolean isLocked();

  public interface iListener {
    void viewerRemoved(iViewer v);

    void viewerSet(iViewer v);
  }
}
