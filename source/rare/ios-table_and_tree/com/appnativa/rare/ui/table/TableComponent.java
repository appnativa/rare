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

package com.appnativa.rare.ui.table;

import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.ScrollView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.ScrollPanePanel;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;

public class TableComponent extends aTableComponent implements iScrollerSupport {
  TableHeader     headerComponent;
  ScrollPanePanel listComponent;
  private int     measuredHeaderHeight;

  public TableComponent(Table cfg, boolean main) {
    this(new TableView(), cfg, main);
  }

  public TableComponent(TableView table, Table cfg, boolean main) {
    super();
    horizontalScroll = main && isHorizontalScollEnabled(cfg);

    if (horizontalScroll) {
      ScrollView sv = new ScrollView();

      sv.setHasVerticalScrollBar(false);
      setView(sv);

      if (UIScrollingEdgePainter.isPaintHorizontalScrollEdge()) {
        UIScrollingEdgePainter sp = (UIScrollingEdgePainter) UIScrollingEdgePainter.getInstance().clone();

        sp.setScrollerSupport(sv);
        sv.setScrollingEdgePainter(sp);
      }

      Container c = new Container(new TableContainer());

      initialize(table, cfg);
      c.add(headerComponent = table.getHeader());
      c.add(listComponent = new ScrollPanePanel(tableView));
      add(c);
      table.setHorizontalScollEnabled(true);
    } else {
      setView(new TableContainer());
      initialize(table, cfg);
      add(headerComponent = table.getHeader());
      add(listComponent = new ScrollPanePanel(tableView));
    }

    if (UIScrollingEdgePainter.isPaintVerticalScrollEdge()) {
      table.setScrollingEdgePainter(UIScrollingEdgePainter.getInstance());
    }

    tableView.setTableComponent(this);
    tableView.setUsePainterBorder(false);
  }

  @Override
  public UIPoint getContentOffset() {
    UIPoint p = tableView.getContentOffset();

    if (horizontalScroll) {
      UIPoint p2 = ((ScrollView) view).getContentOffset();

      p.x = p2.x;
    }

    return p;
  }

  @Override
  public boolean isScrolling() {
    if (horizontalScroll) {
      if (((ScrollView) view).isScrolling()) {
        return true;
      }
    }

    return tableView.isScrollingEx();
  }

  @Override
  public boolean isAtLeftEdge() {
    if (horizontalScroll) {
      return ((ScrollView) view).isAtLeftEdge();
    }

    return true;
  }

  @Override
  public boolean isAtRightEdge() {
    if (horizontalScroll) {
      return ((ScrollView) view).isAtRightEdge();
    }

    return true;
  }

  @Override
  public void dispose() {
    super.dispose();
    headerComponent = null;
    listComponent   = null;
  }

  @Override
  public void setForeground(UIColor fg) {
    super.setForeground(fg);

    if (listComponent != null) {
      listComponent.setForeground(fg);
    }
  }

  @Override
  public int getColumnIndexAt(float x, float y) {
    return tableHeader.getColumnIndexAt(x, y);
  }

  @Override
  public int getFirstVisibleColumnIndex() {
    return tableHeader.getFirstVisibleColumnInView();
  }

  @Override
  public int getLastVisibleColumnIndex() {
    return tableHeader.getLastVisibleColumnInView();
  }

  @Override
  protected Component getTable() {
    return listComponent;
  }

  protected class TableContainer extends ParentView implements iAppleLayoutManager {
    UIDimension headerSize = new UIDimension();

    public TableContainer() {
      super(View.createAPView());
      setLayoutManager(this);
      setUsePainterBackgroundColor(false);
    }

    @Override
    public void layout(ParentView view, float width, float height) {
      UIInsets in = getInsets(null);

      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);
      tableView.updateColumnSizes((int) width, (int) height);

      if ((headerSize.height == 0) && tableHeader.isVisible()) {
        tableHeader.getPreferredSizeEx(headerSize, (int) width);
      }

      int hh = 0;

      if (tableHeader.isVisible()) {
        hh = (measuredHeaderHeight > 0)
             ? measuredHeaderHeight
             : headerSize.intHeight();
        tableHeader.setBounds(in.left, in.top, width, hh);
      }

      tableView.setBounds(in.left, in.top + hh, width, height - hh);
    }

    public boolean needsScrollView() {
      return false;
    }

    public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
      tableView.setScrollingEdgePainter(painter);
    }

    public UIScrollingEdgePainter getScrollingEdgePainter() {
      return tableView.getScrollingEdgePainter();
    }

    @Override
    public void getMinimumSize(UIDimension size) {
      if (headerSize.height == 0) {
        tableHeader.getPreferredSizeEx(headerSize, 0);
      }

      size.height = headerSize.height * 2;
      size.width  = size.height;
    }

    @Override
    public void getPreferredSize(UIDimension size, float maxWidth) {
      tableView.getPreferredSize(size, maxWidth);
      tableHeader.getPreferredSizeEx(headerSize, maxWidth);
      size.width = headerSize.width;

      if (tableHeader.isVisible()) {
        size.height += headerSize.height;
      }

      UIInsets in = getInsets(null);

      size.width  += (in.left + in.right);
      size.height += (in.top + in.bottom);
    }

    @Override
    public boolean isScrollView() {
      return true;
    }

    public boolean isScrolling() {
      return tableView.isScrolling();
    }
  }


  @Override
  protected void scrollBy(float x, float y) {
    if (view instanceof ScrollView) {
      ScrollView sv = (ScrollView) view;
      UIPoint    pt = sv.getContentOffset();

      x += pt.x;
      y += pt.y;
      sv.setContentOffset(x, y);
    }
  }

  @Override
  public void setSelectedColumnIndex(int index) {
    tableHeader.setSelectedIndex(index);
  }

  @Override
  public void setSelectedColumnIndices(int[] indices) {
    tableHeader.setSelectedIndices(indices);
  }

  @Override
  public UIRectangle getViewRect() {
    UIPoint pt = tableView.getContentOffset();

    if (view instanceof ScrollView) {
      pt.x = ((ScrollView) view).getContentOffset().x;
    }

    float           w = view.getWidth();
    float           h = view.getHeight();
    iPlatformBorder b = getBorder();

    if (b != null) {
      UIInsets in = b.getBorderInsets(new UIInsets());

      w -= (in.left + in.right);
      h -= (in.top + in.bottom);
    }

    return new UIRectangle(pt.x, pt.y, UIScreen.snapToSize(w), UIScreen.snapToSize(h));
  }

  public void setMeasuredHeaderHeight(int height) {
    measuredHeaderHeight = height;
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    if (view instanceof ScrollView) {
      ((ScrollView) view).setScrollingEdgePainter(painter);
    } else {
      ((TableContainer) view).setScrollingEdgePainter(painter);
    }
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    if (view instanceof ScrollView) {
      return ((ScrollView) view).getScrollingEdgePainter();
    } else {
      return ((TableContainer) view).getScrollingEdgePainter();
    }
  }

  @Override
  public void scrollToBottomEdge() {
    tableView.scrollToBottomEdge();
  }

  @Override
  public void scrollToLeftEdge() {
    if (view instanceof ScrollView) {
      ((ScrollView) view).scrollToLeftEdge();
    }
  }

  @Override
  public void scrollToRightEdge() {
    if (view instanceof ScrollView) {
      ((ScrollView) view).scrollToRightEdge();
    }
  }

  @Override
  public void scrollToTopEdge() {
    tableView.scrollToTopEdge();
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {
    if (view instanceof ScrollView) {
      ((ScrollView) view).moveLeftRight(left, block);
    }
  }

  @Override
  public void moveUpDown(boolean up, boolean block) {
    tableView.moveUpDown(up, block);
  }
}
