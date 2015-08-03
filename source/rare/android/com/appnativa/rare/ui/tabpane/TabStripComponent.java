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

package com.appnativa.rare.ui.tabpane;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PorterDuff.Mode;

import android.view.MotionEvent;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

public class TabStripComponent extends aTabStripComponent {
  public TabStripComponent(Context context) {
    super(new TabStripView(context));
  }

  @Override
  public void setTabPainter(aPlatformTabPainter painter) {
    ((TabStripView) view).setTabPainter(painter);
  }

  @Override
  public aPlatformTabPainter getTabPainter() {
    return ((TabStripView) view).getTabPainter();
  }

  static class TabStripView extends ViewGroupEx {
    int                 flipHeight;
    Matrix              flipMatrix;
    Matrix              paintMatrix;
    int                 rotation;
    Bitmap              stripBitmap;
    AndroidGraphics     tabGraphics;
    aPlatformTabPainter tabPainter;

    public TabStripView(Context context) {
      super(context);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
      if (event.getAction() != MotionEvent.ACTION_UP) {
        return true;
      }

      UIInsets   in = new UIInsets(getPaddingTop(), getPaddingRight(), getPaddingBottom(), getPaddingLeft());
      MouseEvent e  = new MouseEvent(this, event, 0);

      if (TabStripComponent.handleMousePressed(e, tabPainter, in, getWidth(), getHeight())) {
        invalidate();
      }

      return true;
    }

    public void draw(Canvas canvas) {
      if (!tabPainter.isHandlesRightLeftRotation()) {
        switch(tabPainter.getPosition()) {
          case RIGHT :
            canvas.rotate(90);
            canvas.translate(0, -getWidth());

            break;

          case LEFT :
            canvas.rotate(-90);
            canvas.translate(-getHeight(), 0);

            break;

          default :
            break;
        }
      }

      if (matrix != null) {
        canvas.concat(matrix);
      }

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

    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
      if (!tabPainter.isHorizontal() &&!tabPainter.isHandlesRightLeftRotation()) {
        tabPainter.layout(0, 0, bottom - top, right - left);
      } else {
        tabPainter.layout(0, 0, right - left, bottom - top);
      }

      if ((tabPainter.getPosition() == Location.BOTTOM) &&!tabPainter.isHandlesBottomRotation()) {
        int width  = (right - left);
        int height = (bottom - top);

        if ((flipMatrix == null) || (flipHeight != height)) {
          flipHeight = (bottom - top);
          flipMatrix = ImageUtils.getYFlipTransform(flipHeight);
        }

        if ((stripBitmap == null) || (stripBitmap.getWidth() != width) || (stripBitmap.getHeight() != height)) {
          stripBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
      } else {
        if (stripBitmap != null) {
          stripBitmap.recycle();
          stripBitmap = null;
        }

        flipMatrix = null;
      }
    }

    public void setTabPainter(aPlatformTabPainter tabPainter) {
      if (this.tabPainter != null) {
        this.tabPainter.setHeader(null);
      }

      this.tabPainter = tabPainter;

      if (this.tabPainter != null) {
        this.tabPainter.setHeader((Container) Component.fromView(this));
      }
    }

    public aPlatformTabPainter getTabPainter() {
      return tabPainter;
    }

    protected void onDraw(Canvas canvas) {
      if (tabPainter != null) {
        Canvas          oc = null;
        AndroidGraphics g  = graphics;

        if (stripBitmap != null) {
          oc     = canvas;
          canvas = new Canvas(stripBitmap);
          canvas.drawColor(0, Mode.CLEAR);
          tabGraphics = AndroidGraphics.fromGraphics(canvas, this, tabGraphics);
          g           = tabGraphics;
        }

        int       left   = getPaddingLeft();
        int       right  = getPaddingRight();
        int       bottom = getPaddingBottom();
        int       top    = getPaddingTop();
        final int width  = getWidth() - right - left;
        final int height = getHeight() - bottom - top;

        tabPainter.paint(g, left, top, width, height);

        if (tabGraphics != null) {
          tabGraphics.clear();
        }

        if (oc != null) {
          oc.drawBitmap(stripBitmap, flipMatrix, null);
        }
      }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      if ((MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY)
          && (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY)) {
        measureSize.width  = MeasureSpec.getSize(widthMeasureSpec);
        measureSize.height = MeasureSpec.getSize(heightMeasureSpec);
      } else {
        Component.fromView(this).getPreferredSize(measureSize);
      }

      setMeasuredDimension(resolveSize(measureSize.intWidth(), widthMeasureSpec),
                           resolveSize(measureSize.intHeight(), heightMeasureSpec));
    }

    @Override
    protected int getSuggestedMinimum(boolean forHeight) {
      int     padding = tabPainter.getTabsPadding();
      Integer min     = Platform.getUIDefaults().getInteger("Rare.TabPane.minimumTabHeight");

      if (min == null) {
        min = 0;
      }

      return Math.max(tabPainter.getTabsHeight() + padding, min);
    }
  }
}
