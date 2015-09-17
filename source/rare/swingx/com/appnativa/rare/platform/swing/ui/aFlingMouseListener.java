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

import com.appnativa.rare.Platform;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

public abstract class aFlingMouseListener extends MouseInputAdapter {
  protected int        flingThresholdD;
  protected int        flingThresholdT;
  protected boolean    flinged;
  protected MouseEvent startE;
  protected long       startTime;

  public aFlingMouseListener() {}

  @Override
  public void mouseDragged(MouseEvent e) {
    do {
      if (flinged) {
        break;
      }

      if (startE == null) {
        startE = e;

        break;
      }

      long   time     = e.getWhen() - startE.getWhen();
      float  xx       = e.getX() - startE.getX();
      float  yy       = e.getY() - startE.getY();
      double distance = Math.sqrt((xx * xx) + (yy * yy));

      if (distance < flingThresholdD) {
        if (time > flingThresholdT) {    //start over
          startTime = e.getWhen();
          startE    = e;
        }

        return;
      }

      flinged = true;

      float velocityX = (e.getX() - startE.getX()) / (e.getWhen() - startE.getWhen());
      float velocityY = (e.getY() - startE.getY()) / (e.getWhen() - startE.getWhen());

      onFling(e.getComponent(), startE, e, velocityX, velocityY);
    } while(false);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
    flinged = false;
    startE  = null;

    if (flingThresholdD == 0) {
      flingThresholdD = Platform.getUIDefaults().getInt("Rare.Pointer.flingThresholdDistance", 50);
      flingThresholdT = Platform.getUIDefaults().getInt("Rare.Pointer.flingThresholdMilliseconds", 100);
    }
  }

  public abstract void onFling(Object view, MouseEvent me1, MouseEvent me2, float velocityX, float velocityY);
}
