/*
 * @(#)aPlatformPainter.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.painter;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingPaint;
import com.appnativa.rare.ui.iPlatformPaint;

public abstract class aUIPlatformPainter extends aUIPainter {
  protected SwingGraphics graphics;
  protected SwingPaint platformPaint;
  public aUIPlatformPainter() {
    super();
  }

  @Override
  public void paint(Component c, Graphics g, boolean hasValue) {
    paint(c, (Graphics2D) g, 0, 0, c.getWidth(), c.getHeight(), hasValue, iPainter.UNKNOWN);
  }

  @Override
  public void paint(Component c, Graphics2D g, int x, int y, int width, int height, boolean hasValue, int orientation) {
    graphics = SwingGraphics.fromGraphics(g, c, graphics);
    paint(graphics, x, y, width, height, orientation);
    graphics.clear();
  }

  public Paint getPaintEx(float width, float height) {
    return null;
  }
  
  @Override
  public iPlatformPaint getPaint(float width, float height) {
  	Paint p=getPaintEx(width, height);
  	if(p==null) {
  		return null;
  	}
  	if(platformPaint==null) {
  		platformPaint=new SwingPaint(p);
  	}
  	else {
  		platformPaint.setPaint(p);
  	}
    return platformPaint;
  }
}
