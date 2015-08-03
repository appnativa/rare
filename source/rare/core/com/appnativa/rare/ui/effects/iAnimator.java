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

import com.appnativa.rare.ui.iPlatformComponent;

import java.util.Map;

/**
 * An interface for animating widgets
 *
 * @author Don DeCcoteau
 */
public interface iAnimator extends Cloneable {

  /** value for infinite animation */
  public static int INFINITE = -1;

  /**
   * Direction is used to set the initial direction in which the animation
   * starts.
   */
  public static enum Direction {

    /**
     * cycle proceeds forward
     */
    FORWARD,

    /** cycle proceeds backward */
    BACKWARD,
  }

  void addListener(iAnimatorListener l);

  /**
   * Animates a widget
   *
   * @param c
   *          the component to animate
   * @param postAnimateAction
   *          the post animation action can be and action, action listener,
   *          runnable or script code
   */
  void animate(iPlatformComponent c, Object postAnimateAction);

  /**
   * Cancels the animation
   */
  void cancel();

  /**
   * Dispose of the animator
   */
  void dispose();

  /**
   * Handles custom animation properties
   *
   * @param map
   *          the map of properties
   */
  void handleProperties(Map map);

  void removeListener(iAnimatorListener l);

  /**
   * Stops the animation
   */
  void stop();

  /**
   * Sets the fraction of the timing cycle that will be spent accelerating at
   * the beginning. The default acceleration value is 0 (no acceleration).
   *
   * @param acceleration
   *          value from 0 to 1
   *
   * @throws IllegalArgumentException
   * @throws IllegalStateException
   *           if animation is already running; this parameter may only be
   *           changed prior to starting the animation or after the animation
   *           has ended
   */
  void setAcceleration(float acceleration);

  void setAutoReverse(boolean booleanValue);

  /**
   * Sets the fraction of the timing cycle that will be spent decelerating at
   * the end. The default deceleration value is 0 (no deceleration).
   *
   * @param deceleration
   *          value from 0 to 1
   * @throws IllegalArgumentException
   * @throws IllegalStateException
   *           if animation is already running; this parameter may only be
   *           changed prior to starting the animation or after the animation
   *           has ended
   */
  void setDeceleration(float deceleration);

  /**
   * Sets the direction for the initial animation cycle.
   *
   * @param direction
   *          initial animation cycle direction
   * @throws IllegalStateException
   *           if animation is already running; this parameter may only be
   *           changed prior to starting the animation or after the animation
   *           has ended
   */
  void setDirection(Direction direction);

  /**
   * Sets the duration for the animation
   *
   * @param duration
   *          the length of the animation, in milliseconds. This value can also
   *          be {@link #INFINITE}, meaning the animation will run until
   *          manually stopped.
   * @throws IllegalStateException
   *           if animation is already running; this parameter may only be
   *           changed prior to starting the animation or after the animation
   *           has ended
   * @see #isRunning()
   * @see #stop()
   */
  void setDuration(int duration);

  /**
   * Sets the number of times the animation cycle will repeat. The default value
   * is 0.
   *
   * @param repeatCount
   *          Number of times the animation cycle will repeat. This value may be
   *          >= 0 or {@link #INFINITE} for animations that repeat indefinitely.
   *
   * @throws IllegalStateException
   *           if animation is already running; this parameter may only be
   *           changed prior to starting the animation or after the animation
   *           has ended
   */
  void setRepeatCount(int repeatCount);

  /**
   * Returns whether the animation is running
   *
   * @return true if the animation is running; false otherwise
   */
  boolean isRunning();

  /**
   * Gets the direction of the animation
   *
   * @return the direction of the animation
   */
  Direction getDirection();

  /**
   * Clones the animation
   *
   * @return a copy of the animation
   */
  Object clone();

  /**
   * Sets whether a container should adjust the animation to fit its orientation
   * and directionality
   *
   * @return true if it should; false otherwise
   */
  boolean isAutoOrient();

  /**
   * Sets whether a container should adjust the animation to fit its orientation
   * and directionality
   *
   * @param auto
   *          true if it should; false otherwise
   */
  void setAutoOrient(boolean auto);

  /**
   * Gets whether the animation is a sizing animation
   *
   * @return true if it is; false otherwise
   */
  boolean isSizingAnimation();

  /**
   * Restores the component to it's original state. This only meaningful for
   * states not managed by its parent (e.g. size and location)
   *
   * @param comp
   *          the component
   */
  void restoreComponent(iPlatformComponent comp);

  void setViewEventsEnabled(iPlatformComponent c, boolean enabled);

  public void setAnimatingProperty(iPlatformComponent c, boolean animating);
}
