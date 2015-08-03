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

import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.view.View;

import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class SpacerView extends View implements iPainterSupport, iComponentView {
  iPlatformComponentPainter componentPainter;
  protected AndroidGraphics graphics;
  private Bitmap            bitmap;
  private int               height;
  private int               width;

  public SpacerView(Context context) {
    super(context);
  }

  public SpacerView(Context context, Bitmap bitmap) {
    super(context);
    this.width  = bitmap.getWidth();
    this.height = bitmap.getHeight();
    this.bitmap = bitmap;
  }

  public SpacerView(Context context, int width, int height) {
    super(context);
    this.width  = width;
    this.height = height;
  }

  public void clearBitmap() {
    Bitmap b = bitmap;

    bitmap = null;

    if (b != null) {
      b.recycle();
    }
  }

  public void dispose() {
    if (bitmap != null) {
      bitmap.recycle();
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

  public void setBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;

    if (bitmap != null) {
      this.width  = bitmap.getWidth();
      this.height = bitmap.getHeight();
    }

    requestLayout();
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  /**
   * @param height the height to set
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * @param width the width to set
   */
  public void setWidth(int width) {
    this.width = width;
  }

  public Bitmap getBitmap() {
    return bitmap;
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public int getSuggestedMinimumHeight() {
    return height;
  }

  @Override
  public int getSuggestedMinimumWidth() {
    return width;
  }

  public final View getView() {
    return this;
  }

  protected void onDraw(Canvas canvas) {
    if (bitmap != null) {
      canvas.drawBitmap(bitmap, 0, 0, null);
    }
  }

  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int w = (width < 0)
            ? 0
            : width;
    int h = (height < 0)
            ? 0
            : height;

    if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY) {
      widthMeasureSpec = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
    }

    if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
      heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
    }

    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}
