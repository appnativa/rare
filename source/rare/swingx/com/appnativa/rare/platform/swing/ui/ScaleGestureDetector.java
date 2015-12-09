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

package com.appnativa.rare.platform.swing.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.Timer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iGestureListener;
import com.appnativa.rare.ui.event.ScaleGestureObject;
import com.appnativa.rare.widget.aWidget;

public class ScaleGestureDetector extends ScaleGestureObject implements MouseListener, MouseWheelListener {
  int     state = -1;
  long    lastScrollTime;
  Timer   timer;
  aWidget widget;

  public ScaleGestureDetector(aWidget w) {
    super();
    widget = w;
    widget.getDataComponent().getView().addMouseWheelListener(this);
    widget.getDataComponent().getView().addMouseListener(this);
  }

  public void dispose() {
    if ((widget != null) &&!widget.isDisposed()) {
      widget.getDataComponent().getView().removeMouseWheelListener(this);
      widget.getDataComponent().getView().removeMouseListener(this);
    }

    widget = null;
  }

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    reset(0, 0);

    if (state != -1) {
      if (timer != null) {
        timer.stop();
      }

      state = -1;
      fireEvent(iGestureListener.SCALE_END, null);
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    lastScrollTime = e.getWhen();

    if (timer == null) {
      int timeout = Platform.getUIDefaults().getInt("Rare.Pointer.mouseWheelTimeout", 200);

      timer = new Timer(timeout, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          checkTimer();
        }
      });
    }

    if (!timer.isRunning()) {
      fireEvent((state == -1)
                ? iGestureListener.SCALE_BEGIN
                : iGestureListener.SCALE, e);
      timer.start();
    }
  }

  public void setEvent(MouseWheelEvent e) {
    focusX = e.getX();
    focusY = e.getY();

    float scroll = -e.getWheelRotation();

    scaleFactor += (scroll / 100);
    scroll      += currentSpan.x;
    setCurrentSpan(scroll, currentSpan.y);
  }

  protected void fireEvent(int state, MouseWheelEvent e) {
    aWidget         w  = widget;
    aWidgetListener wl = w.getWidgetListener();

    if (e != null) {
      setEvent(e);
    }

    switch(state) {
      case iGestureListener.SCALE_BEGIN :
        wl.onScaleEvent(this, state, this, 1);
        this.state = iGestureListener.SCALE;
        wl.onScaleEvent(this, iGestureListener.SCALE, this, getScaleFactor());

        break;

      case iGestureListener.SCALE :
        wl.onScaleEvent(this, iGestureListener.SCALE, this, getScaleFactor());

        break;

      default :
        wl.onScaleEvent(this, iGestureListener.SCALE_END, this, getScaleFactor());
        this.state  = -1;
        scaleFactor = 1;

        break;
    }
  }

  void checkTimer() {
    if (lastScrollTime + timer.getDelay() < System.currentTimeMillis()) {
      timer.stop();
      fireEvent(iGestureListener.SCALE_END, null);
    }
  }
}
