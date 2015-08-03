/*
 * @(#)FormsView.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.painter.UICellPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormLayout.LayoutInfo;

public class FormsView extends UtilityPanel {
  protected FormLayout.LayoutInfo        layoutInfo;
  protected iPlatformPainter[] painters;
  private UIDimension          size   = new UIDimension();
  private UIInsets             insets = new UIInsets();
  private FormLayout           layout;
  protected boolean cacheLayoutInfo;

  public FormsView() {
    this(new FormLayout());
  }

  public FormsView(FormLayout layout) {
    super();
    this.layout = layout;
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    c.putClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY, constraints);
    add(c.getView());

    CellConstraints cc = (CellConstraints) constraints;

    layout.addLayoutComponent(c, cc);
  }

  public void dispose() {
    if (painters != null) {
      Arrays.fill(painters, null);
    }
  }

  @Override
  public void invalidateLayout(java.awt.Container target) {
    if (layout != null) {
      layout.invalidateCaches();
    }
  }

  @Override
  public Dimension minimumLayoutSize(java.awt.Container parent) {
    getMinimumSize(size);

    return SwingHelper.setDimension(null, size);
  }

  @Override
  public Dimension preferredLayoutSize(java.awt.Container parent) {
    getPreferredSize(size);

    return SwingHelper.setDimension(null, size);
  }

  @Override
  public void remove(iPlatformComponent c) {
    if (c != null) {
      remove(c.getView());
      layout.removeLayoutComponent(c);
    }
  }

  @Override
  public void removeAll() {
    super.removeAll();
    layout.invalidateCaches();
  }

  public void requestLayout() {
    if (layout != null) {
      layout.invalidateCaches();
      revalidate();
    }
  }

  public void setCellPainters(iPlatformPainter[] painters) {
    this.painters = painters;
  }

  public CellConstraints getCellConstraints(iPlatformComponent c) {
    return (CellConstraints) c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);
  }

  @Override
  public Object getConstraints(iPlatformComponent c) {
    return c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);
  }

  public iPlatformComponent getFormComponentAt(int col, int row) {
    CellConstraints cc;
    Container       container = (Container) Component.fromView(this);
    int             len       = container.getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent c = container.getComponentAt(i);

      cc = (CellConstraints) c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);
      if(cc==null) {
        continue;
      }
      if ((cc.gridX == col) && (cc.gridY == row)) {
        return c;
      }
    }

    return null;
  }

  public FormLayout getFormLayout() {
    return layout;
  }
  
  public void setFormLayout(FormLayout layout) {
    this.layout=layout;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    Container container = (Container) Component.fromView(this);

    getFormLayout().getMinimumSize(container, size);
  }

  public void getPreferredSize(UIDimension size) {
    Container container = (Container) Component.fromView(this);

    getFormLayout().getPreferredSize(container, size, 0);
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
  protected void layoutContainerEx(int width, int height) {
    Container container = (Container) Component.fromView(this);

    if (layout == null || container==null || container.isDisposed()) {
      return;
    }

    int x[], y[];

    if (layoutInfo != null) {
      x = layoutInfo.columnOrigins;
      y = layoutInfo.rowOrigins;
    } else {
      size.setSize(width, height);
      insets = container.getInsets(insets);
      layout.initializeColAndRowComponentLists(container);
      
      LayoutInfo layoutInfo = layout.computeLayout(container, size, insets);

      x = layoutInfo.columnOrigins;
      y = layoutInfo.rowOrigins;
    }

    layout.layoutComponents(container, x, y);
    adjustPainters(x, y);
  }

  /**
   * Performs the painting of the cell painters
   * @param g the graphics context
   */
  protected void paintCells(iPlatformGraphics g) {
    if (painters != null) {
      g.saveState();

      try {
        int       len = painters.length;
        final int w   = getWidth();
        final int h   = getHeight();

        for (int i = 0; i < len; i++) {
          painters[i].paint(g, 0, 0, w, h, iPainter.UNKNOWN);
        }
      } finally {
        g.restoreState();
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    paintCells(graphics);
  }
}
