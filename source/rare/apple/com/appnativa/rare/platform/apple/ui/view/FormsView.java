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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iLayoutManager;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.painter.UICellPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/*-[
 #import "RAREAPView.h"
 ]-*/
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormLayout.LayoutInfo;

import java.util.Arrays;

public class FormsView extends ParentView implements iAppleLayoutManager, iLayoutManager {
  FormLayout.LayoutInfo        layoutInfo;
  protected iPlatformPainter[] painters;
  private UIDimension          size        = new UIDimension();
  private UIDimension          measureSize = new UIDimension();
  private UIInsets             insets      = new UIInsets();
  private FormLayout           layout;
  private iLayoutTracker       layoutTracker;

  public FormsView() {
    super(createProxy());
    setLayoutManager(this);
    this.layout = new FormLayout();
  }

  public FormsView(FormLayout layout) {
    super(createProxy());
    setLayoutManager(this);
    this.layout = layout;
  }

  public FormsView(Object proxy, FormLayout layout) {
    super(proxy);
    setLayoutManager(this);
    this.layout = layout;
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    c.putClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY, constraints);
    add(position, c.getView());

    CellConstraints cc = (CellConstraints) constraints;

    layout.addLayoutComponent(c, cc);
  }

  public static native Object createProxy()
  /*-[
    return [[RAREAPView alloc]init];
  ]-*/
  ;

  public void setFormLayout(FormLayout layout) {
    this.layout = layout;
  }

  @Override
  public void invalidateLayout() {
    if (layout != null) {
      layout.invalidateCaches();
    }
  }

  @Override
  public void layout(ParentView view, float width, float height) {
    if (layout == null) {
      return;
    }

    Container container = (Container) component;

    if (container.isPartOfAnimation()) {
      return;
    }

    if (layoutTracker != null) {
      layoutTracker.willPerformLayout(this);
    }

    int x[], y[];

    if (layoutInfo != null) {
      x = layoutInfo.columnOrigins;
      y = layoutInfo.rowOrigins;
    } else {
      size.setSize((int) width, (int) height);
      container.getInsets(insets);
      layout.initializeColAndRowComponentLists(container);

      LayoutInfo layoutInfo = layout.computeLayout(container, size, insets);

      x = layoutInfo.columnOrigins;
      y = layoutInfo.rowOrigins;
    }

    layout.layoutComponents(container, x, y);
    adjustPainters(x, y);

    if (layoutTracker != null) {
      layoutTracker.layoutPerformed(this);
    }
  }

  @Override
  public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
    super.paintBackground(g, v, rect);
    paintCells(g, rect);
  }

  @Override
  public void remove(iPlatformComponent c) {
    if (c != null) {
      removeChild(c.getView());
      layout.removeLayoutComponent(c);
    }
  }

  @Override
  public void removeAll() {
    super.removeChildren();
    layout.invalidateCaches();
  }

  @Override
  public void revalidate() {
    if (layout != null) {
      layout.invalidateCaches();
      super.revalidate();
    }
  }

  public void setCellPainters(iPlatformPainter[] painters) {
    this.painters = painters;

    if (painters != null) {
      setPaintHandlerEnabled(true);
    }
  }

  @Override
  public void setLayoutTracker(iLayoutTracker tracker) {
    layoutTracker = tracker;
  }

  public CellConstraints getCellConstraints(iPlatformComponent c) {
    return (CellConstraints) c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);
  }

  public iPlatformComponent getComponentAt(int col, int row) {
    CellConstraints cc;
    Container       container = (Container) component;
    int             len       = container.getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent c = container.getComponentAt(i);

      cc = (CellConstraints) c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);

      if ((cc.gridX == col) && (cc.gridY == row)) {
        return c;
      }
    }

    return null;
  }

  @Override
  public Object getConstraints(iPlatformComponent c) {
    return c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);
  }

  public FormLayout getLayout() {
    return layout;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    getLayout().getMinimumSize((iParentComponent) Component.fromView(this), size);
  }

  protected void adjustPainters(int[] columnOrigins, int[] rowOrigins) {
    int len = (painters == null)
              ? 0
              : painters.length;

    if (len == 0) {
      return;
    }

    int rlen = rowOrigins.length;
    int clen = columnOrigins.length;

    for (int i = 0; i < len; i++) {
      UICellPainter cp = (UICellPainter) painters[i];
      int           x1 = cp.column;
      int           y1 = cp.row;
      int           x2 = cp.column + cp.columnSpan;
      int           y2 = cp.row + cp.rowSpan;

      if ((x1 < 0) || (y1 < 0) || (y2 < 0) || (x2 < 0)) {
        continue;
      }

      if (y2 >= rlen) {
        y2 = rlen - 1;
      }

      if ((x1 >= clen) || (y1 >= rlen)) {
        cp.width  = 0;
        cp.height = 0;

        continue;
      }

      if (x2 >= clen) {
        x2 = clen - 1;
      }

      if ((x2 < clen) && (y2 < rlen)) {
        cp.x      = columnOrigins[x1];
        cp.y      = rowOrigins[y1];
        cp.width  = columnOrigins[x2] - cp.x;
        cp.height = rowOrigins[y2] - cp.y;
      }
    }
  }

  @Override
  protected void disposeEx() {
    if (layout != null) {
      layout.invalidateCaches();
      layout = null;
    }

    if (painters != null) {
      Arrays.fill(painters, null);
    }

    layoutTracker = null;
    painters      = null;
    super.disposeEx();
  }

  /**
   * Performs the painting of the cell painters
   *
   * @param g
   *          the graphics context
   */
  protected void paintCells(iPlatformGraphics g, UIRectangle rect) {
    if (painters != null) {
      int len = painters.length;

      for (int i = 0; i < len; i++) {
        painters[i].paint(g, rect.x, rect.x, rect.width, rect.height, iPlatformPainter.UNKNOWN);
      }
    }
  }
}
