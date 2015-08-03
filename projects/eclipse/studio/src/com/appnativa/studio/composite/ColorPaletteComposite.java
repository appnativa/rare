/*
 * @(#)ColorChooser.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

public class ColorPaletteComposite extends Composite implements PaintListener, MouseListener {
  private static Color[]    colorPalette40;
  int                       colorCount = 40;
  int                       columns    = 8;
  int                       rows       = 5;
  UIColor                   selectedColor;
  int                       selectedIndex;
  Color                     swtColor;
  private EventListenerList listenerList = new EventListenerList();
  private ChangeEvent       changeEvent;

  public ColorPaletteComposite(Composite parent, int style) {
    super(parent, style);
    addPaintListener(this);
    addMouseListener(this);
    changeEvent = new ChangeEvent(this);
  }

  public void addChangeListener(iChangeListener l) {
    listenerList.add(iChangeListener.class, l);
  }

  @Override
  public void dispose() {
    super.dispose();

    if (swtColor != null) {
      swtColor.dispose();
      swtColor = null;
    }
  }

  public int findIndex(UIColor c) {
    Color[] a = getColors();

    for (int i = 0; i < a.length; i++) {
      if ((a[i].getRed() == c.getRed()) && (a[i].getGreen() == c.getGreen()) && (a[i].getBlue() == c.getBlue())) {
        return i;
      }
    }

    return -1;
  }
  public boolean isSelectedColor(Color c) {
    if(c==null || selectedColor==null) {
      return false;
    }
   return selectedColor.getRed() == c.getRed() && selectedColor.getGreen() == c.getGreen() && selectedColor.getBlue() == c.getBlue();
  }

  @Override
  public void mouseDoubleClick(MouseEvent e) {}

  @Override
  public void mouseDown(MouseEvent e) {}

  @Override
  public void mouseUp(MouseEvent e) {
    int n = getColorAtLocation(e.x, e.y);

    if (n != -1) {
      select(n);
    }
  }

  public void paintControl(PaintEvent e) {
    Point p      = getSize();
    int   width  = p.x;
    int   height = p.y;
    int   x      = 0;
    int   y      = 0;
    GC    g      = e.gc;
    float f      = (width / columns);

    f = Math.min(height / rows, f);

    int d2   = ScreenUtils.PLATFORM_PIXELS_4;
    int size = (int) Math.floor(f);

    x = (width - (size * columns)) / 2;
    y = (height - (size * rows)) / 2;

    int     ox    = x;
    int     sw    = ScreenUtils.PLATFORM_PIXELS_1;
    Color[] a     = getColors();
    int     dsize = size - d2 - d2;

    for (int i = 0; i < colorCount; i++) {
      if ((i > 0) && (i % columns == 0)) {
        y += size;
        x = ox;
      }

      g.setBackground(a[i]);
      g.fillRectangle(x + d2, y + d2, dsize, dsize);
      g.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
      g.setLineWidth(sw);
      g.drawRectangle(x + d2, y + d2, dsize, dsize);

      if (isSelectedColor(a[i])) {
        g.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
        g.drawRectangle(x, y, size, size);
      }

      x += size;
    }
  }

  public void removeChangeListener(iChangeListener l) {
    listenerList.remove(iChangeListener.class, l);
  }

  public void setSelectedColor(UIColor color) {
    this.selectedColor = color;
    selectedIndex      = (color == null)
                         ? -1
                         : findIndex(color);

    if ((selectedIndex == -1) && (swtColor != null)) {
      swtColor.dispose();
    }

    swtColor = null;
  }

  public int getColorAtLocation(float mx, float my) {
    Point p      = getSize();
    float width  = p.x;
    float height = p.y;
    float f      = (width / columns);

    f = Math.min(height / rows, f);

    int   size = (int) Math.floor(f);
    float x    = (width - (size * columns)) / 2;
    float y    = (height - (size * rows)) / 2;
    float ox   = x;

    for (int i = 0; i < colorCount; i++) {
      if ((i > 0) && (i % columns == 0)) {
        y += size;
        x = ox;
      }

      if ((mx > x) && (my > y) && (mx < (x + size)) && (my < (y + size))) {
        return i;
      }

      x += size;
    }

    return -1;
  }

  public static Color[] getColorPalette40() {
    if (colorPalette40 == null) {
      colorPalette40 = new Color[] {
        newColor(0, 0, 0), newColor(153, 51, 0), newColor(51, 51, 0), newColor(0, 51, 0), newColor(0, 51, 102),
        newColor(0, 0, 128), newColor(51, 51, 153), newColor(51, 51, 51), newColor(128, 0, 0), newColor(255, 102, 0),
        newColor(128, 128, 0), newColor(0, 128, 0), newColor(0, 128, 128), newColor(0, 0, 255), newColor(102, 102, 153),
        newColor(128, 128, 128), newColor(255, 0, 0), newColor(255, 153, 0), newColor(153, 204, 0),
        newColor(51, 153, 102), newColor(51, 204, 204), newColor(51, 102, 255), newColor(128, 0, 128),
        newColor(153, 153, 153), newColor(255, 0, 255), newColor(255, 204, 0), newColor(255, 255, 0),
        newColor(0, 255, 0), newColor(0, 255, 255), newColor(0, 204, 255), newColor(153, 51, 102),
        newColor(192, 192, 192), newColor(255, 153, 204), newColor(255, 204, 153), newColor(255, 255, 153),
        newColor(204, 255, 204), newColor(204, 255, 255), newColor(153, 204, 255), newColor(204, 153, 255),
        newColor(255, 255, 255)
      };
    }

    return colorPalette40;
  }

  public Color getSWTColor() {
    if (selectedIndex != -1) {
      return getColors()[selectedIndex];
    }

    if (selectedColor == null) {
      return SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND);
    }

    if (swtColor == null) {
      swtColor = new Color(Display.getDefault(), selectedColor.getRed(), selectedColor.getGreen(),
                           selectedColor.getBlue());
    }

    return swtColor;
  }

  public UIColor getSelectedColor() {
    return selectedColor;
  }

  static Color newColor(int red, int green, int blue) {
    return new Color(Display.getDefault(), red, green, blue);
  }

  @Override
  protected void checkSubclass() {}

  protected void select(int index) {
    if ((index != selectedIndex)) {
      Color c = getColors()[index];

      if (swtColor != null) {
        swtColor.dispose();
        swtColor = null;
      }

      selectedColor = new UIColor(c.getRed(), c.getGreen(), c.getBlue());
      selectedIndex = index;
      this.redraw();

      if (listenerList.hasListeners(iChangeListener.class)) {
        Utils.fireChangeEvent(listenerList, changeEvent);
      }
    }
  }

  protected Color[] getColors() {
    return getColorPalette40();
  }
}
