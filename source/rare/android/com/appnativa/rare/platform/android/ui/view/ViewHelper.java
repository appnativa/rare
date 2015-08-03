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

package com.appnativa.rare.platform.android.ui.view;

import android.view.View;

import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.event.iAndroidViewListener;

/**
 *
 * @author Don DeCoteau
 */
public class ViewHelper {
  public static void onAttachedToWindow(View view) {
    iAndroidViewListener vl = getViewListener(view);

    if (vl != null) {
      vl.onAttachedToWindow(view);
    }
  }

  public static void onDetachedFromWindow(View view) {
    iAndroidViewListener vl = getViewListener(view);

    if (vl != null) {
      vl.onDetachedFromWindow(view);
    }
  }

  public static void onSizeChanged(View view, int w, int h, int oldw, int oldh) {
    iAndroidViewListener vl = getViewListener(view);

    if (vl != null) {
      vl.onSizeChanged(view, w, h, oldw, oldh);
    }
  }

  public static void onVisibilityChanged(View view, View changedView, int visibility) {
    iAndroidViewListener vl = getViewListener(changedView);

    if (vl != null) {
      vl.onVisibilityChanged(view, changedView, visibility);
    }
  }

  public static iAndroidViewListener getViewListener(View view) {
    Component c = Component.fromView(view);

    return (c == null)
           ? null
           : c.getViewListener();
  }
}
