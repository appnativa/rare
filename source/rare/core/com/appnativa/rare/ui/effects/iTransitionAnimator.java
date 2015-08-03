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

package com.appnativa.rare.ui.effects;

import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;

public interface iTransitionAnimator {

  /**
   * Starts the animation. At this point the incomming component is in the
   * target container
   *
   * @param target the target container
   * @param bounds the bounds for widgets in the container
   * @param cb callback to be notified when the animation is complete.
   */
  void animate(iParentComponent target, UIRectangle bounds, iFunctionCallback cb);

  /**
   * Cancels the transition animation
   */
  void cancel();

  /**
   * Disposes of the the transition animation
   */
  void dispose();

  /**
   * Sets the incomming component. At this point the component is in the target container.
   * The target calls this method right after this component is placed in the container
   * @param comp the component
   */
  void setIncommingComponent(iPlatformComponent comp);

  /**
   * Sets the outgoing component. At this point the component is still in the target container.
   * The target container calls this method to setup the transition while the component is still in the target
   *
   * @param comp the component
   */
  void setOutgoingComponent(iPlatformComponent comp);

  /**
   * Called to retrieve the animation for
   * the 'in' component in a stacked animation
   *
   * @return the animation
   */
  iPlatformAnimator getInAnimator();

  /**
   * Called to retrieve the animation for
   * the 'out' component in a stacked animation
   *
   * @return the animation
   */
  iPlatformAnimator getOutAnimator();

  /**
   * Returns whether or not the animation is running
   * @return true if the animation is running; false otherwise
   */
  boolean isRunning();

  boolean isAutoDispose();

  void setAutoDispose(boolean autoDispose);

  /**
   * Sets a callback to be called when the transition animation is complete
   * @param cb the callback
   */
  void setCallback(iFunctionCallback cb);

  /**
   * Tells the animator that the outgoing components
   * will persist beyond the end of the animation
   *
   * @param persists true if it will persist; false (the default) otherwise
   */
  void setOutgoingPersists(boolean persists);

  /**
   * Tells the animator that the outgoing should be hidden
   * during the transition.
   *
   * @param hidden true if it should be hidden; false (the default) otherwise
   */
  void setOutgoingHidden(boolean hidden);
}
