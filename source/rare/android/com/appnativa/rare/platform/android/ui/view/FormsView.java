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

package com.appnativa.rare.platform.android.ui.view;

import android.content.Context;

import android.graphics.Canvas;

import android.view.View;
import android.view.ViewGroup;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.UICellPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormLayout.LayoutInfo;

import java.util.Arrays;

public class FormsView extends ViewGroupEx {
  protected iPlatformPainter[] painters;
  private UIDimension          size   = new UIDimension();
  private UIInsets             insets = new UIInsets();
  private FormLayout           layout;

  public FormsView(Context context) {
    this(context, new FormLayout());
  }

  public FormsView(Context context, FormLayout layout) {
    super(context);
    this.layout = layout;
  }

  public void addView(View child, int index, LayoutParams params) {
    super.addView(child, index, params);

    CellConstraintsEx constraints = (CellConstraintsEx) params;

    layout.addLayoutComponent(Component.fromView(child), constraints.cc);
  }

  public void setFormLayout(FormLayout layout) {
    this.layout = layout;
  }

  public ViewGroup.LayoutParams createLayoutParams(com.jgoodies.forms.layout.CellConstraints cc) {
    return new CellConstraintsEx(cc);
  }

  @Override
  public void dispose() {
    super.dispose();

    if (painters != null) {
      Arrays.fill(painters, null);
    }
  }

  public void draw(Canvas canvas) {
    if (matrix != null) {
      canvas.concat(matrix);
    }

    graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

    final iPlatformComponentPainter cp = componentPainter;

    if (cp == null) {
      super.draw(canvas);
    } else {
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, false);
      super.draw(canvas);
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, true);
    }

    graphics.clear();
  }

  public void requestLayout() {
    if (layout != null) {
      getLayout().invalidateCaches();
      super.requestLayout();
    }
  }

  /**
   * Sets cell painters for the panel
   *
   * @param painters the painters
   */
  public void setCellPainters(iPlatformPainter[] painters) {
    this.painters = painters;
  }

  public CellConstraints getCellConstraints(View view) {
    return ((CellConstraintsEx) view.getLayoutParams()).cc;
  }

  /**
   * Gets the cell painters for the panel
   *
   * @return the painters
   */
  public iPlatformPainter[] getCellPainters() {
    return painters;
  }

  public iPlatformComponent getComponentAt(int col, int row) {
    int             len = getChildCount();
    CellConstraints cc;
    View            v;

    for (int i = 0; i < len; i++) {
      v  = getChildAt(i);
      cc = ((CellConstraintsEx) v.getLayoutParams()).cc;

      if ((cc.gridX == col) && (cc.gridY == row)) {
        return Component.fromView(v);
      }
    }

    return null;
  }

  public CellConstraints getConstraints(iPlatformComponent c) {
    Object o = c.getView().getLayoutParams();

    if (o instanceof CellConstraintsEx) {
      return ((CellConstraintsEx) o).cc;
    }

    return null;
  }

  public FormLayout getLayout() {
    return layout;
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

  protected void callSuperDraw(Canvas canvas) {
    super.draw(canvas);
  }

  @Override
  protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
    return p instanceof CellConstraintsEx;
  }

  /**
   *   Returns a set of layout parameters with a width of
   *   {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT},
   *   a height of {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT}
   *   and with the coordinates (0, 0).
   */
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return new CellConstraintsEx();
  }

  @Override
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
    if (p instanceof CellConstraintsEx) {
      return (LayoutParams) ((CellConstraintsEx) p).clone();
    }

    return new CellConstraintsEx();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    paintCells(canvas);
  }

  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    if (isAnimating(this)) {
      return;
    }

    iParentComponent container = (iParentComponent) Component.fromView(this);
    int              x[], y[];

    size.setSize(r - l, b - t);
    insets.set(getPaddingTop(), getPaddingRight(), getPaddingBottom(), getPaddingLeft());
    layout.initializeColAndRowComponentLists(container);

    LayoutInfo li = layout.computeLayout(container, size, insets);

    x = li.columnOrigins;
    y = li.rowOrigins;
    layout.layoutComponents(container, x, y);
    adjustPainters(x, y);
  }

  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    iParentComponent container = (iParentComponent) Component.fromView(this);

    if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED) {
      int width  = MeasureSpec.getSize(widthMeasureSpec);
      int height = MeasureSpec.getSize(heightMeasureSpec);

      if (height == 0) {
        height = Short.MAX_VALUE;
      }

      if ((MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY)
          && (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY)) {
        measureSize.width  = width;
        measureSize.height = height;
      } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
        container.getPreferredSize(measureSize, width);
        measureSize.height = Math.min(measureSize.height, height);
      } else {
        getLayout().calculateLayoutSize(container, width, height, measureSize);

        if (height == Short.MAX_VALUE) {
          measureSize.height += getPaddingTop() + getPaddingBottom();
        } else {
          measureSize.height = height;
        }
      }
    } else {
      container.getPreferredSize(measureSize);
    }

    setMeasuredDimension(resolveSize(measureSize.intWidth(), widthMeasureSpec),
                         resolveSize(measureSize.intHeight(), heightMeasureSpec));
  }

  /**
   *   Performs the painting of the cell painters
   *   @param g the graphics context
   */
  protected void paintCells(Canvas g) {
    if (painters != null) {
      // g.save();
      try {
        int       len = painters.length;
        final int w   = getWidth();
        final int h   = getHeight();

        for (int i = 0; i < len; i++) {
          painters[i].paint(this, g, 0, 0, w, h, iPlatformPainter.UNKNOWN);
        }
      } finally {
        // g.restore();
      }
    }
  }

  protected int getSuggestedMinimum(boolean forHeight) {
    iParentComponent container = (iParentComponent) Component.fromView(this);

    container.getMinimumSize(measureSize);

    return forHeight
           ? measureSize.intHeight()
           : measureSize.intWidth();
  }

  protected class CellConstraintsEx extends ViewGroup.LayoutParams implements Cloneable {
    protected com.jgoodies.forms.layout.CellConstraints cc;

    public CellConstraintsEx() {
      super(WRAP_CONTENT, WRAP_CONTENT);
      cc = new com.jgoodies.forms.layout.CellConstraints();
    }

    public CellConstraintsEx(com.jgoodies.forms.layout.CellConstraints cc) {
      super(WRAP_CONTENT, WRAP_CONTENT);
      this.cc = cc;
    }

    protected Object clone() {
      try {
        CellConstraintsEx c = (CellConstraintsEx) super.clone();

        c.cc = (com.jgoodies.forms.layout.CellConstraints) cc.clone();

        return c;
      } catch(CloneNotSupportedException e) {
        throw new InternalError("clone not supported");
      }
    }
  }
}
