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

import android.content.Context;

import android.util.AttributeSet;

import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.aLineHelper.Line;

public class SeparatorView extends LineView {
  boolean lineCreated;

  public SeparatorView(Context context) {
    super(context);
  }

  public SeparatorView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SeparatorView(Context context, boolean horizontal) {
    super(context, horizontal);
  }

  @Override
  public int getSuggestedMinimumHeight() {
    return ScreenUtils.PLATFORM_PIXELS_8;
  }

  @Override
  public int getSuggestedMinimumWidth() {
    return ScreenUtils.PLATFORM_PIXELS_8;
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    if (!lineCreated) {
      lineCreated = true;

      Line l = getLineHelper().createLine();

      l.setLeftOffset(ScreenUtils.PLATFORM_PIXELS_2);
      l.setRightOffset(ScreenUtils.PLATFORM_PIXELS_2);
      getLineHelper().addLine(l);
    }

    super.onLayout(changed, left, top, right, bottom);
    getLineHelper().setHorizontal(getWidth() > getHeight());
  }
}
