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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.listener.aMouseAdapter;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.widget.iWidget;

public class ColorPalettePanel extends UIPanel implements iActionable, iActionListener {
  int                          columns;
  int                          rows;
  private int                  selectedIndex = -1;
  private int                  colorCount;
  private ColorPalette         colorPalette;
  private boolean              inPopup;
  private iPlatformListHandler listHandler;
  private UIColor              selectedColor;
  private boolean              useList;

  public ColorPalettePanel(iWidget context) {
    this.widget = context;

    Listener l = new Listener();

    this.addMouseListener(l);
    this.addKeyListener(l);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    select(listHandler.getSelectedIndex());
  }

  @Override
  public void addActionListener(iActionListener l) {
    getEventListenerList().add(iActionListener.class, l);
  }

  @Override
  public void removeActionListener(iActionListener l) {
    if (listenerList != null) {
      listenerList.remove(iActionListener.class, l);
    }
  }

  public void setColorPalette(ColorPalette palette) {
    this.colorPalette = palette;
    colorCount        = palette.getColors().length;

    if (listHandler != null) {
      listHandler.setAll(palette.createListItems());
    }

    rows    = palette.getRows();
    columns = palette.getColumns();
    repaint();
  }

  public void setInPopup(boolean inPopup) {
    this.inPopup = inPopup;
    setFocusable(!inPopup);
  }

  public void setSelectedColor(UIColor color) {
    this.selectedColor = color;
    selectedIndex      = (color == null)
                         ? -1
                         : findIndex(color);
    selectedIndexUpdated();
  }

  public void setUseList(boolean useList) {
    this.useList = useList;

    if (useList) {
      iPlatformListDataModel model = PlatformHelper.createListDataModel(widget, null);

      if (colorPalette != null) {
        model.addAll(colorPalette.createListItems());
      }

      listHandler = PlatformHelper.createPopupListBoxHandler(widget, model, false);
      listHandler.addActionListener(this);
      add(listHandler.getContainerComponent());
    }
  }

  public int getColorAtLocation(float mx, float my) {
    if (!useList) {
      UIInsets in     = getInsetsEx();
      float    width  = getWidth();
      float    height = getHeight();

      if (in != null) {
        width  -= (in.left - in.right);
        height -= (in.top - in.bottom);
      }

      float f = (width / columns);

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
    }

    return -1;
  }

  public ColorPalette getColorPalette() {
    return colorPalette;
  }

  public UIColor getSelectedColor() {
    return selectedColor;
  }

  @Override
  public CharSequence getToolTipText(int x, int y) {
    int n = getColorAtLocation(x, y);

    if (n != -1) {
      return getColors()[n].toString();
    }

    return null;
  }

  public boolean isInPopup() {
    return inPopup;
  }

  public boolean isUseList() {
    return useList;
  }

  protected int findIndex(UIColor c) {
    UIColor[] a = getColors();

    for (int i = 0; i < a.length; i++) {
      if (c.equals(a[i])) {
        return i;
      }
    }

    return -1;
  }

  protected int getelectedIndex() {
    return selectedIndex;
  }

  @Override
  protected void layout(float width, float height) {
    if (listHandler != null) {
      UIInsets in = getInsetsEx();
      float    x  = 0;
      float    y  = 0;

      if (in != null) {
        x      = in.left;
        y      = in.right;
        width  -= (in.left + in.right);
        height -= (in.top + in.bottom);
      }

      listHandler.getContainerComponent().setBounds(x, y, width, height);
    }
  }

  @Override
  protected void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    if (!useList) {
      UIInsets in = getInsetsEx();

      if (in != null) {
        width  -= (in.left - in.right);
        height -= (in.top - in.bottom);
      }

      g.saveState();
      g.setRenderingOptions(false, false);

      float f = (width / columns);

      f = Math.min(height / rows, f);

      float d2   = ScreenUtils.PLATFORM_PIXELS_4;
      int   size = (int) Math.floor(f);

      x = (width - (size * columns)) / 2;
      y = (height - (size * rows)) / 2;

      float     ox    = x;
      float     sw    = ScreenUtils.PLATFORM_PIXELS_1;
      UIColor[] a     = getColors();
      float     dsize = size - d2 - d2;

      for (int i = 0; i < colorCount; i++) {
        if ((i > 0) && (i % columns == 0)) {
          y += size;
          x = ox;
        }

        g.setColor(a[i]);
        g.fillRect(x + d2, y + d2, dsize, dsize);
        g.setColor(UIColor.BLACK);
        g.setStrokeWidth(sw);
        g.drawRect(x + d2, y + d2, dsize, dsize);

        if ((selectedColor != null) && selectedColor.equals(a[i])) {
          if (focusPaint == null) {
            focusPaint = Platform.getAppContext().getWidgetFocusPainter();
          }

          if (focusPaint != null) {
            UIComponentPainter.paint(g, x, y, size, size, focusPaint);
          }
        }

        x += size;
      }

      g.restoreState();
    }
  }

  protected void select(int index) {
    if ((index != selectedIndex) || isInPopup()) {
      selectedColor = getColors()[index];
      selectedIndex = index;
      selectedIndexUpdated();

      if (!useList) {
        repaint();
      }

      if ((listenerList != null) && listenerList.hasListeners(iActionListener.class)) {
        Utils.fireActionEvent(listenerList, new ActionEvent(this));
      }
    }
  }

  protected void selecteNextColor(boolean down) {
    int n = selectedIndex;

    if (n == -1) {
      n = 0;
    } else {
      if (down) {
        n += columns;
      } else {
        n++;
      }
    }

    UIColor[] a = getColors();

    if (n >= a.length) {
      n %= a.length;
    }

    selectedIndex = n;
    selectedColor = a[n];
    selectedIndexUpdated();
    repaint();
  }

  protected void selectePreviousColor(boolean up) {
    int n = selectedIndex;

    if (n == -1) {
      n = colorCount - 1;
    } else {
      if (up) {
        n -= columns;
      } else {
        n--;
      }
    }

    UIColor[] a = getColors();

    if (n < 0) {
      n = a.length + n;
    }

    selectedIndex = n;
    selectedColor = a[n];
    selectedIndexUpdated();
    repaint();
  }

  protected void selectedIndexUpdated() {
    if (listHandler != null) {
      listHandler.setSelectedIndex(selectedIndex);
    }
  }

  protected UIColor[] getColors() {
    if (colorPalette == null) {
      setColorPalette(ColorPalette.getColorPalette40());
    }

    return colorPalette.getColors();
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    if (listHandler != null) {
      listHandler.getContainerComponent().getMinimumSize(size);
    } else {
      if (Platform.isTouchDevice()) {
        size.width  = ScreenUtils.PLATFORM_PIXELS_10 * 2 * columns;
        size.height = ScreenUtils.PLATFORM_PIXELS_10 * 2 * rows;
      } else {
        size.width  = ScreenUtils.PLATFORM_PIXELS_10 * columns;
        size.height = ScreenUtils.PLATFORM_PIXELS_10 * rows;
      }
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (listHandler != null) {
      listHandler.getContainerComponent().getPreferredSize(size, maxWidth);
    } else {
      size.width  = ScreenUtils.PLATFORM_PIXELS_16 * 2 * columns;
      size.height = ScreenUtils.PLATFORM_PIXELS_16 * 2 * rows;
    }
  }

  @Override
  protected boolean hasToolTips() {
    return false;
  }

  class Listener extends aMouseAdapter implements iKeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.isEndPressed()) {
        if (e.isShiftKeyPressed()) {
          select(getColors().length - 1);
        } else {
          int n = selectedIndex / columns;

          n++;
          select((n * columns) - 1);
        }
      } else if (e.isHomePressed()) {
        if (e.isShiftKeyPressed()) {
          select(0);
        } else {
          int n = selectedIndex / columns;

          select(n * columns);
        }
      } else if (e.isDownArrowKeyPressed()) {
        selecteNextColor(true);
      } else if (e.isUpArrowKeyPressed()) {
        selectePreviousColor(true);
      } else if (e.isRightArrowKeyPressed()) {
        selecteNextColor(false);
      } else if (e.isLeftArrowKeyPressed()) {
        selectePreviousColor(false);
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
      if (!isFocusOwner() && isFocusable()) {
        requestFocus(isInPopup());
      }

      int n = getColorAtLocation(e.getX(), e.getY());

      if (n != -1) {
        select(n);
      }
    }
  }
}
