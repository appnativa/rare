/*
 * @(#)FlinMouseListener.java
 * 
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import com.appnativa.rare.Platform;

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
