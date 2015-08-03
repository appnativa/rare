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

package com.appnativa.rare.ui;

import android.content.Context;

import android.graphics.Canvas;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.CarouselPanel.ContentView;
import com.appnativa.rare.ui.carousel.MotionHandler;
import com.appnativa.rare.ui.carousel.MotionHandler.iScrollingParent;
import com.appnativa.rare.ui.carousel.aCarouselPanel;
import com.appnativa.rare.ui.carousel.aCarouselPanel.DataType;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

public class CarouselContainer extends BorderPanel {
  CarouselPanel panel;
  ScrollPanel   scrollPanel;

  public CarouselContainer(DataType dataType, Context context) {
    super();
    panel = new CarouselPanel(dataType, context);
    panel.setAdjustable(new Adjustable());
    scrollPanel = new ScrollPanel(new ScrollView(context), panel);
    scrollPanel.add(panel);
    setCenterView(scrollPanel);
  }

  public void refreshItems() {
    panel.refreshItems();
    revalidate();
  }

  @Override
  public void dispose() {
    super.dispose();
    panel       = null;
    scrollPanel = null;
  }

  public void setScrollBarOpacity(float opacity) {}

  public void setShowScrollBar(boolean show) {}

  public CarouselPanel getCarouselPanel() {
    return panel;
  }

  class Adjustable extends aAdjustable {
    protected EventListenerList getEventListenerList() {
      return CarouselContainer.this.getEventListenerList();
    }

    protected boolean hasListener() {
      return CarouselContainer.this.listenerList != null;
    }

    @Override
    public boolean isAdjusting() {
      return scrollPanel.isScrolling();
    }
  }


  static class ScrollPanel extends Container implements iScrollingParent {
    private MotionHandler motionHandler;

    public ScrollPanel(ScrollView view, CarouselPanel panel) {
      super(view);
      motionHandler = new MotionHandler(panel, this);
      this.addMouseListener(motionHandler);
      this.addMouseMotionListener(motionHandler);
    }

    public boolean isScrolling() {
      return motionHandler.isScrolling();
    }

    protected void getMinimumSizeEx(UIDimension size) {
      size.setSize(0, 0);

      if (getComponentCount() == 1) {
        iPlatformComponent c = getComponentAt(0);

        if (c.isVisible()) {
          c.getMinimumSize(size);
        }
      }
    }

    protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
      size.setSize(0, 0);

      if (getComponentCount() == 1) {
        iPlatformComponent c = getComponentAt(0);

        if (c.isVisible()) {
          c.getPreferredSize(size);
        }
      }
    }

    @Override
    public int getIndexOfComponentAt(float x, float y) {
      View v = ViewGroupEx.getViewtAt((ScrollView) view, x, y, true);

      while(v != null) {
        if (v instanceof ContentView) {
          iPlatformComponent c     = Component.fromView(v);
          Integer            index = (Integer) c.getClientProperty(aCarouselPanel.RARE_CAROUSEL_ITEM);

          return (index == null)
                 ? -1
                 : index.intValue();
        }

        ViewParent vp = v.getParent();

        if (!(vp instanceof View)) {
          break;
        }

        v = (View) vp;
      }

      return -1;
    }
  }


  class ScrollView extends ViewGroupEx {
    public ScrollView(Context context) {
      super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
      return true;
    }

    public void draw(Canvas canvas) {
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

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      scrollPanel.motionHandler.layoutChild(right - left, bottom - top);
    }
  }
}
