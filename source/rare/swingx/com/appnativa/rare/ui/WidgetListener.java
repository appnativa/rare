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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.listener.iViewListener;
import com.appnativa.rare.widget.iWidget;

import java.util.Map;

import javax.swing.JComponent;

public class WidgetListener extends aWidgetListener
        implements iFocusListener, iTextChangeListener, iKeyListener, iMouseListener, iMouseMotionListener,
                   iViewListener {
  protected boolean    flingEnabled;
  protected int        flingThresholdD;
  protected int        flingThresholdT;
  protected boolean    flinged;
  protected MouseEvent startE;
  protected long       startTime;
  private boolean      flingEnabledChecked;

  public WidgetListener(iWidget widget, Map map, iScriptHandler sh) {
    super(widget, map, sh);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    super.mouseDragged(e);

    do {
      if (!flingEnabled || flinged) {
        break;
      }

      if (startE == null) {
        startE = e;

        break;
      }

      long   time     = e.getEventTime() - startE.getEventTime();
      float  xx       = e.getX() - startE.getX();
      float  yy       = e.getY() - startE.getY();
      double distance = Math.sqrt((xx * xx) + (yy * yy));

      if (distance < flingThresholdD) {
        if (time > flingThresholdT) {    //start over
          startTime = e.getEventTime();
          startE    = e;
        }

        return;
      }

      flinged = true;

      float velocityX = 1000 * (e.getX() - startE.getX()) / (e.getEventTime() - startE.getEventTime());
      float velocityY = 1000 * (e.getY() - startE.getY()) / (e.getEventTime() - startE.getEventTime());

      onFling(e.getComponent(), startE, e, velocityX, velocityY);
    } while(false);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
    flinged = false;
    startE  = null;

    if (!flingEnabledChecked) {
      flingEnabledChecked = true;
      flingEnabled        = isEnabled(iConstants.EVENT_FLING);
      flingThresholdD     = Platform.getUIDefaults().getInt("Rare.Pointer.flingThresholdDistance", 50);
      flingThresholdT     = Platform.getUIDefaults().getInt("Rare.Pointer.flingThresholdMilliseconds", 100);
    }
  }

  @Override
  public boolean isMouseEventsEnabled() {
    return mouseEventsEnabled || mouseMotionEventsEnabled;
  }

  @Override
  protected iPlatformComponent getSource(Object v) {
    if (v instanceof iPlatformComponent) {
      return (iPlatformComponent) v;
    }

    while((v != null) &&!(v instanceof JComponent)) {
      v = ((java.awt.Component) v).getParent();
    }

    iPlatformComponent c = (v == null)
                           ? null
                           : Component.fromView((JComponent) v);

    if (c == null) {
      c = theWidget.getDataComponent();
    }

    return c;
  }
}
