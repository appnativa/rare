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

import android.graphics.Canvas;

import android.util.AttributeSet;

import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.aLineHelper;
import com.appnativa.rare.ui.painter.iPainterSupport;

/**
 *
 * @author Don DeCoteau
 */
public class LineView extends ViewEx implements iPainterSupport, iComponentView {
  private LineHelper lineHelper;

  /**
   * Creates a new instance of LineComponent
   * @param context the context
   */
  public LineView(Context context) {
    this(context, true);
  }

  public LineView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setFocusable(false);
    lineHelper = new LineHelper(true);
  }

  /**
   * Constructs a new instance
   *
   * @param context the context
   * @param horizontal true for horizontal; false for vertical
   */
  public LineView(Context context, boolean horizontal) {
    super(context);
    setFocusable(false);
    lineHelper = new LineHelper(horizontal);
  }

  @Override
  public void onDraw(Canvas g) {
    int l = getPaddingLeft();
    int r = getPaddingRight();
    int b = getPaddingBottom();
    int t = getPaddingTop();

    lineHelper.paint(graphics, l, t, getWidth() - l - r, getHeight() - t - b);
  }

  public aLineHelper getLineHelper() {
    return lineHelper;
  }

  @Override
  public int getSuggestedMinimumHeight() {
    return lineHelper.isHorizontal()
           ? ScreenUtils.PLATFORM_PIXELS_1
           : UIScreen.snapToSize(lineHelper.getThickness());
  }

  @Override
  public int getSuggestedMinimumWidth() {
    return !lineHelper.isHorizontal()
           ? ScreenUtils.PLATFORM_PIXELS_1
           : UIScreen.snapToSize(lineHelper.getThickness());
  }

  class LineHelper extends aLineHelper {
    public LineHelper(boolean horizontal) {
      super(horizontal);
    }

    @Override
    protected void thicknessRecalculated() {
    }
  }
}
