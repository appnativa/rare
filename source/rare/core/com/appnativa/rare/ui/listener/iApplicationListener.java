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

package com.appnativa.rare.ui.listener;

import com.appnativa.rare.iPlatformAppContext;

import java.util.EventListener;

/**
 * An interface is used to notify listeners
 * of application level events.
 *
 */
public interface iApplicationListener extends EventListener {

  /**
   * Called to determine if the application can be closed
   *
   * @param app the application context
   *
   * @return true if the application can be closed; false otherwise
   */
  public boolean allowClosing(iPlatformAppContext app);

  /**
   * Notification that the application is about to close.
   *
   * @param app the application context
   */
  public void applicationClosing(iPlatformAppContext app);

  /**
   * Called after the application has finished initializing.
   *
   * @param app the application context
   */
  public void applicationInitialized(iPlatformAppContext app);

  /**
   * Called when the application is paused
   *
   * @param app the application context
   */
  public void applicationPaused(iPlatformAppContext app);

  /**
   * Called when the application resumes after a pause
   *
   * @param app the application context
   */
  public void applicationResumed(iPlatformAppContext app);
}
