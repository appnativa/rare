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

package com.appnativa.rare.ui.chart.aicharts;

import android.content.Context;

import android.graphics.Canvas;

import android.util.AttributeSet;

import android.view.View;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.view.ViewHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.chart.aicharts.ChartHandler.CustomAdapter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

import com.artfulbits.aiCharts.Base.ChartAxis.LabelsAdapter;
import com.artfulbits.aiCharts.ChartView;

/**
 *
 * @author Don DeCoteau
 */
public class ChartViewEx extends ChartView implements iPainterSupport {
  boolean                   blockPaint;
  iPlatformComponentPainter componentPainter;
  protected AndroidGraphics graphics;

  public ChartViewEx(Context context) {
    this(context, null);
  }

  public ChartViewEx(Context context, AttributeSet attrs) {
    super(context, attrs);
    setBackgroundDrawable(NullDrawable.getInstance());

    if (attrs == null) {
      UIColor bg = Platform.getUIDefaults().getColor("Rare.Chart.background");

      if (bg == null) {
        bg = ColorUtils.getBackground();
      }

      if (bg != null) {
        setBackgroundColor(bg.getColor());
      }
    }
  }

  public ChartViewEx(Context context, int chartId) {
    super(context, chartId);
  }

  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
  }

  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ViewHelper.onDetachedFromWindow(this);
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }

  public void blockPainting() {
    blockPaint = true;
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);

    LabelsAdapter a = getChart().getAreas().get(0).getDefaultYAxis().getLabelsAdapter();

    if (a instanceof CustomAdapter) {
      ((CustomAdapter) a).layoutChanged();
    }

    a = getChart().getAreas().get(0).getDefaultXAxis().getLabelsAdapter();

    if (a instanceof CustomAdapter) {
      ((CustomAdapter) a).layoutChanged();
    }
  }

  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

  public void draw(Canvas canvas) {
    graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

    final iPlatformComponentPainter cp = componentPainter;

    if (cp == null) {
      super.draw(canvas);
    } else {
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, false);
      super.draw(canvas);
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, true);
    }

    graphics.clear();
  }

  public void unblockPainting() {
    blockPaint = false;
    invalidate();
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }
}
