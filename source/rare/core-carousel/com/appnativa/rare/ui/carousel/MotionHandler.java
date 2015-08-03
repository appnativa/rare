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

package com.appnativa.rare.ui.carousel;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.util.SNumber;

import com.google.j2objc.annotations.Weak;

public class MotionHandler implements iMouseListener, iMouseMotionListener {
  @Weak
  aCarouselPanel   panel;
  @Weak
  iScrollingParent parentView;

  public MotionHandler(aCarouselPanel panel, iScrollingParent parentView) {
    super();
    this.panel      = panel;
    this.parentView = parentView;
    threshold       = ScreenUtils.platformPixelsf(!Platform.isTouchDevice()
            ? 5
            : 20);
  }

  public MotionHandler(aCarouselPanel panel) {
    this(panel, null);
  }

  float   mouseDownX  = 0;
  float   overFlow    = ScreenUtils.platformPixelsf(50);
  float   scrollX     = overFlow / -2;
  float   scrollXBase = scrollX;
  boolean dragged;
  float   lastX;
  float   threshold;

  @Override
  public void mouseMoved(MouseEvent e) {}

  public void setScrollingComponent(iScrollingParent c) {
    parentView = c;
  }

  @Override
  public boolean wantsMouseMovedEvents() {
    return false;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    float x         = e.getX();
    float distanceX = mouseDownX - x;

    if (!dragged && (Math.abs(distanceX) < threshold)) {
      return;
    }

    dragged = true;

    float nx = lastX - x;

    if (Math.abs(nx) < threshold) {
      return;
    }

    lastX = x;

    if (distanceX > 0) {
      distanceX = Math.min(distanceX, overFlow);
    } else if (distanceX < 0) {
      distanceX = Math.max(distanceX, -overFlow);
    }

    if (nx < 0) {
      if (panel.getSelectedIndex() > 0) {
        panel.scrollRight();
        scrollX = scrollXBase;
      } else {
        scrollX = scrollXBase - distanceX;
      }
    } else {
      if (panel.getSelectedIndex() + 1 < panel.getItemCount()) {
        panel.scrollLeft();
        scrollX = scrollXBase;
      } else {
        scrollX = scrollXBase - distanceX;
      }
    }

    if (!SNumber.isEqual(scrollX, scrollXBase)) {
      panel.setBounds(scrollX, 0, parentView.getWidth() + overFlow, parentView.getHeight());
    }

    parentView.repaint();
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    mouseDownX = e.getX();
    lastX      = mouseDownX;
    dragged    = false;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (!SNumber.isEqual(scrollX, scrollXBase)) {
      scrollX = scrollXBase;
      panel.setBounds(scrollX, 0, parentView.getWidth() + overFlow, parentView.getHeight());
      parentView.repaint();
    } else if (!dragged) {
      int index = parentView.getIndexOfComponentAt(mouseDownX, parentView.getHeight() / 2);

      if ((index != -1) && (index != panel.getSelectedIndex())) {
        panel.animateSelect(index);
      }
    }

    if (dragged) {    // notify that we have stopped scrolling
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          panel.fireChangeEvent();
        }
      });
    }

    dragged = false;
  }

  @Override
  public void pressCanceled(MouseEvent e) {
    mouseReleased(e);
  }

  @Override
  public boolean wantsLongPress() {
    return false;
  }

  public float getScrollX() {
    return scrollX;
  }

  public boolean isScrolling() {
    return dragged;
  }

  public float getOverflow() {
    return overFlow;
  }

  public interface iScrollingParent extends iParentComponent {
    int getIndexOfComponentAt(float x, float y);
  }


  public void layoutChild(float width, float height) {
    panel.setBounds(scrollX, 0, width + overFlow, height);
  }

  public void dispose() {
    parentView = null;
    panel      = null;
  }
}
