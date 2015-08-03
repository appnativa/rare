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

package com.appnativa.rare;

import android.app.Activity;

import android.graphics.drawable.Drawable;

import com.appnativa.rare.platform.android.MainActivity;
import com.appnativa.rare.platform.android.aActivityListener;
import com.appnativa.rare.ui.iPlatformComponentFactory;

public interface iPlatformAppContext extends iAppContext {
  public boolean alwaysUseHeavyTargets();

  public void unlockOrientation();

  public void setActivityListener(aActivityListener l);

  public void setContentView(MainActivity a) throws Exception;

  public Activity getActivity();

  public aActivityListener getActivityListener();

  /**
   * Gets a handle to the object that is responsible for creating UI components
   *
   * @return a handle to the object that is responsible for creating UI
   *         components
   */
  public iPlatformComponentFactory getComponentCreator();

  /**
   * Get the id for an android layout
   * @param name the name of the layout
   * @return the Integer representing the layout or null if not found
   */
  public Integer getLayoutId(String name);

  /**
   * Gets a drawable resource that is part of the applications resource bundle
   *
   * @param name
   *          the name of the drawable
   *
   * @return the drawable image with the specified name
   */
  public Drawable getResourceAsDrawable(String name);

  /**
   * Get the id for an android resource
   * @param name the fully qualified name of the resource
   * @return the Integer representing the resource  or null if not found
   */
  public Integer getResourceId(String name);

  public boolean hasKeyboard();

  public boolean isOverlapAutoToolTips();
}
