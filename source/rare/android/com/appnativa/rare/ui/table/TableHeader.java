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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.appnativa.rare.platform.android.iContextMenuInfoHandler;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.platform.android.ui.view.iDrawCallback;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.BasicSelectionModel;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.aUIComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.ui.table.iTableComponent.TableType;

import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public class TableHeader extends aTableHeader implements iDrawCallback {
  UIColor           alternatingColor;
  AndroidGraphics   columnGraphics;
  boolean           columnsReordered;
  TableComponent    table;
  TableType tableType;
  

  public TableHeader(Context context, TableComponent table) {
    super();

    TableHeaderLayout v = new TableHeaderLayout(context);

    v.header = this;
    setView(v);
    this.table = table;

    MouseListener l = new MouseListener();

    addMouseListener(l);
    addMouseMotionListener(l);
  }

  @Override
  public void afterOnDraw(View v, Canvas canvas) {
    if (v instanceof ListViewEx) {
      drawVerticalLines((ViewGroup) view, (ListViewEx) v, canvas);
    }
  }

  @Override
  public void beforeOnDraw(View v, Canvas canvas) {
    paintColumnHilites((ViewGroup) view, v, canvas);
  }

  public int convertModelIndexToView(int index) {
    if (!columnsReordered) {
      return index;
    }

    ViewGroup header = (ViewGroup) view;
    final int len    = header.getChildCount();
    View      v;

    for (int i = 0; i < len; i++) {
      v = header.getChildAt(i);

      if (v instanceof ColumnView) {
        ColumnView cv = (ColumnView) v;

        if (cv.dataIndex == index) {
          return i;
        }
      }
    }

    return -1;
  }

  public int convertViewIndexToModel(int index) {
    ColumnView cv = getColumnView(index);

    return (cv == null)
           ? -1
           : cv.dataIndex;
  }

  @Override
  public void dispose() {
    super.dispose();
    this.table = null;
  }

  @Override
  public void moveColumn(int from, int to) {
    ViewGroup vg=(ViewGroup) view;
    View cv = vg.getChildAt(from);

    vg.removeViewAt(from);
    vg.addView(cv, to);
    ListAdapter a = table.getListView().getAdapter();
    if(a instanceof aTableAdapter) {
      ((aTableAdapter)a).columnMoved(from, to);
    }
    columnMoved(from,to);
  }

  public void paintEmptyRowColumns(iPlatformGraphics g, float y, float height, float width) {
    ViewGroup header = (ViewGroup) view;
    final int len    = header.getChildCount();
    View      v;
    Column[]  a    = columns;
    int       clen = a.length;
    View      lv   = null;

    for (int i = len - 1; i > -1; i--) {
      v = header.getChildAt(i);

      if ((v.getVisibility() == View.VISIBLE) && (v instanceof ColumnView)) {
        lv = v;

        break;
      }
    }

    final int d = table.isShowVerticalLines()
                  ? ScreenUtils.PLATFORM_PIXELS_1
                  : 0;

    for (int i = 0; i < len; i++) {
      v = header.getChildAt(i);

      if ((v.getVisibility() == View.VISIBLE) && (v instanceof ColumnView)) {
        ColumnView cv = (ColumnView) v;

        if ((cv.dataIndex < 0) || (cv.dataIndex >= clen)) {
          continue;
        }

        if (cv == lv) {
          paintColumn(a[cv.dataIndex], g, v.getLeft(), y, width - v.getLeft(), height);

          break;
        } else {
          paintColumn(a[cv.dataIndex], g, v.getLeft(), y, v.getWidth() - d, height);
        }
      }
    }
  }

  public void setAlternatingColor(UIColor color) {
    this.alternatingColor = color;
  }

  @Override
  public void setBounds(int x, int y, int w, int h) {
    if (!isGridView()) {
      super.setBounds(x, y, w, h);
    }
  }

  public void setColumnDataIndex(int col, int index) {
    getColumnView(col).setDataIndex(index);
  }

  @Override
  public void setColumnIcon(int col, iPlatformIcon icon) {
    ColumnView v = getColumnView(col);

    columns[v.dataIndex].setHeaderIcon(icon);
    v.setIcon((icon == null)
              ? NullDrawable.getInstance()
              : icon);
    revalidate();
  }

  @Override
  public void setColumnTitle(int col, CharSequence title) {
    ColumnView v = getColumnView(col);

    v.setText(title);
    columns[v.dataIndex].setColumnTitle(title);
    revalidate();
  }

  @Override
  protected void setColumnVisibleEx(int col, boolean visible) {
    ColumnView v = getColumnView(col);

    columns[v.dataIndex].setVisible(visible);
    v.setVisibility(visible
                    ? View.VISIBLE
                    : View.GONE);
    resetSelectionPaintColumns();
    setColumnsEx();
  }

  @Override
  public void setColumns(List<Column> columns) {
    ((TableHeaderLayout) view).removeAllViews();
    super.setColumns(columns);
  }

  @Override
  public void setFont(UIFont f) {
    super.setFont(f);

    if (f != null) {
      ((TableHeaderLayout) view).updateFontAnColor(null, f);
    }

    revalidate();
  }

  @Override
  public void setForeground(UIColor fg) {
    super.setForeground(fg);

    if (fg != null) {
      ((TableHeaderLayout) view).updateFontAnColor(fg, null);
    }
  }

  public UIColor getAlternatingColor() {
    return alternatingColor;
  }

  public int getColumnDataIndex(int col) {
    return getColumnView(col).getDataIndex();
  }

  @Override
  public Column getColumnForViewAt(int viewColumn) {
    return columns[getColumnView(viewColumn).getDataIndex()];
  }

  @Override
  public int getColumnIndexAt(float x, float y) {
    int len = columns.length;
    int pos = 0;

    for (int i = 0; i < len; i++) {
      Column c = columns[i];

      if (c.isVisible()) {
        if ((x >= pos) && (x < pos + c.getWidth())) {
          return i;
        }

        pos += c.getWidth() + 1;
      }
    }

    return -1;
  }

  public int getSelectionPaintEndX(int currentEndX) {
    if (selectionPaintEndCol == columns.length - 1) {
      return currentEndX;
    }

    ColumnView v = getColumnView(selectionPaintEndCol);

    return v.getRight();
  }

  public int getSelectionPaintStartX(int currentStartX) {
    if (selectionPaintStartCol == 0) {
      return currentStartX;
    }

    ColumnView v = getColumnView(selectionPaintStartCol);

    return v.getLeft() - (table.isShowVerticalLines()
                          ? ScreenUtils.PLATFORM_PIXELS_1
                          : 0);
  }

  @Override
  public iTableComponent getTableComponent() {
    return table;
  }

  public TableHeaderLayout getTableHeaderLayout() {
    return (TableHeaderLayout) view;
  }

  public boolean isColumnSelected(int col) {
    return (selectionModel == null)
           ? false
           : selectionModel.isSelected(col);
  }

  protected iPlatformComponent configureColumn(ActionComponent b, Column c) {
    LabelView tv = (LabelView) b.getView();
    UIFont    f  = c.getHeaderFont();

    if (f == null) {
      f = font;
    }

    if (f != null) {
      b.setFont(f);
    }

    tv.setIconPosition(c.getHeaderIconPosition());

    if (fgColor != null) {
      fgColor.setTextColor(tv);
    }

    if (c.getHeaderIcon() != null) {
      tv.setIcon(c.getHeaderIcon());
    }

    b.setWordWrap(c.headerWordWrap);
    tv.setGravity(AndroidHelper.getGravity(c.getHeaderHorizontalAlignment(), c.getHeaderVerticalAlignment()));

    return b;
  }

  protected void drawVerticalLines(ViewGroup header, ListViewEx table, Canvas g) {
    Paint p = table.getDividerPaint();

    if (p == null) {
      return;
    }

    int       h   = table.getHeight();
    final int len = header.getChildCount();
    View      v;

    for (int i = 1; i < len; i++) {
      v = header.getChildAt(i);

      if (v.getVisibility() == View.VISIBLE) {
        int x = v.getLeft() - 1;

        g.drawLine(x, 0, x, h, p);
      }
    }
  }

  protected void paintColumnHilites(ViewGroup header, View table, Canvas g) {
    if (alternatingColor == null) {
      return;
    }

    Paint p = new Paint();

    p.setAntiAlias(true);
    p.setColor(alternatingColor.getColor());

    int       h   = table.getHeight();
    int       n   = 0;
    final int len = header.getChildCount();
    View      v;

    for (int i = 1; i < len; i++) {
      v = header.getChildAt(i);

      if (v.getVisibility() == View.VISIBLE) {
        if (n % 2 == 0) {
          g.drawRect(v.getLeft(), 0, v.getRight(), h, p);
        }

        n++;
      }
    }
  }

  @Override
  protected void repaintColumn(int col) {
    ColumnView v = getColumnView(col);

    v.invalidate();
  }

  @Override
  protected void tableHadInteraction() {
    table.hadInteraction();
  }

  @Override
  protected void setColumnPressed(int col, boolean pressed) {
    ViewGroup th = (ViewGroup) view;
    View      v  = th.getChildAt(col);

    v.setPressed(pressed);
    v.invalidate();
  }

  @Override
  protected void setColumnsEx() {
    Column[]          cols = this.columns;
    final int         len  = cols.length;
    TableHeaderLayout tl   = (TableHeaderLayout) view;

    tl.removeAllViews();

    Context context = tl.getContext();
    Column  col;
    int vp[]=viewPositions;

    for (int i = 0; i < len; i++) {
      col = cols[vp[i]];

      iPlatformComponent c = createColumnComponent(col, context, i);

      if (!col.isVisible()) {
        c.setVisible(false);
      }

      tl.addViewEx(c.getView());
    }

    if (columnSelectionAllowed) {
      selectionModel = new BasicSelectionModel(len);
    }

    tableType = table.getTableType();
  }

  protected iPlatformComponent getColumnComponent(int col) {
    return Component.fromView(getColumnView(col));
  }

  protected final ColumnView getColumnView(int col) {
    return (ColumnView) ((ViewGroup) view).getChildAt(col);
  }

  private iPlatformComponent createColumnComponent(Column c, Context context, int index) {
    if (c.getRenderingComponent() != null) {
      return c.getRenderingComponent();
    }

    ColumnView tv = new ColumnView(context, c, index);

    tv.setBackground(NullDrawable.getInstance());
    tv.setText(c.getColumnTitle());

    return configureColumn(new UILabelRenderer(tv), c);
  }

  public static class TableHeaderLayout extends ViewGroupEx {
    boolean     headerBgSet = false;
    TableHeader header;
    int         oldWidth;
    int         pressedColumn;

    public TableHeaderLayout(Context context) {
      super(context);
      setMeasureType(MeasureType.HORIZONTAL);
      heightPad = 5;
    }

    public int convertColumnIndexToView(int index) {
      final int len = getChildCount();

      for (int i = 0; i < len; i++) {
        ColumnView v = (ColumnView) getChildAt(i);

        if (v.dataIndex == index) {
          return i;
        }
      }

      return -1;
    }

    public void addViewEx(View v) {
      addView(v,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    public void draw(Canvas canvas) {
      if ((componentPainter == null) &&!headerBgSet) {
        headerBgSet = true;

        PaintBucket pb = TableHelper.getDefaultPainter(header.getBackgroundEx());

        pb.install(header);
      }

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

      if (header.showHeaderMargin) {
        Paint p = new Paint();
        int   d = ScreenUtils.PLATFORM_PIXELS_1;

        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(d);

        int       h   = getHeight();
        final int len = getChildCount();
        if(header.marginColor!=null) {
          p.setColor(header.marginColor.getColor());
  
          View v;
  
          d = 0;
  
          for (int i = 1; i < len; i++) {
            v = getChildAt(i);
  
            if (v.getVisibility() == View.VISIBLE) {
              int x = v.getLeft() - d;
  
              canvas.drawLine(x, 0, x, h, p);
            }
          }
  
          if (header.tableType != null) {
            int right = getWidth();
  
            switch(header.tableType) {
              case MAIN :
                canvas.drawLine(right - d, 0, right - d, h, p);
                canvas.drawLine(0, 0, 0, h, p);
  
                break;
  
              default :
                break;
            }
          }
        }
        if (header.bottomMarginColor != null) {
          p.setColor(header.bottomMarginColor.getColor());
          canvas.drawLine(0, h - .75f, getWidth(), h - .75f, p);
        }
      }

      graphics.clear();
    }

    public void updateFontAnColor(UIColor color, UIFont font) {
      if (header == null) {
        return;
      }

      int len = getChildCount();

      for (int i = 0; i < len; i++) {
        View v = getChildAt(i);

        if (v instanceof ColumnView) {
          if (color != null) {
            color.setTextColor((ColumnView) v);
          }

          if (font != null) {
            font.setupTextView((ColumnView) v);
          }
        }
      }
    }

    public void setMeasuredDimensionEx(int width, int height) {
      setMeasuredDimension(width, height);
    }

    public int getColumnX(int col) {
      ColumnView v = (ColumnView) getChildAt(col);

      return (int) v.getX();
    }
    
    public void moveColumn(int from, int to) {
      View v=getChildAt(from);
      addView(v, to);
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
      int       height = b - t - getPaddingTop() - getPaddingBottom();
      final int len    = getChildCount();
      int       w;
      int       x    = getPaddingLeft();
      int       y    = getPaddingTop();
      Column[]  cols = header.columns;
      int vp[]=header.viewPositions;
      int       d    = 0;    // ScreenUtils.PLATFORM_PIXELS_1;
      View      lv   = null;

      for (int i = 0; i < len; i++) {
        View v = getChildAt(i);

        if (v.getVisibility() != View.GONE) {
          w = cols[vp[i]].getWidth();
          v.layout(x, y, x + w, height);
          x  += w + d;
          lv = v;
        }
      }

      if ((lv != null) && (lv.getRight() != r)) {
        x = (int) lv.getX();
        lv.layout(x, y, r, height);
      }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      if (header.table.autoSizeColumns &&!header.autoSizedColumns) {
        header.sizeColumnsToFitTableData();
      }

      int width  = MeasureSpec.getSize(widthMeasureSpec);
      int height = MeasureSpec.getSize(heightMeasureSpec);

      if ((MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY)
          && header.table.isColumnSizesInitialized()) {
        if (header.isGridView()) {
          measureSize.width  = width - getPaddingLeft() - getPaddingRight();
          measureSize.height = 0;
        } else {
          boolean  hexactly = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY;
          Column[] cols     = header.columns;
          int vp[]=header.viewPositions;

          height -= getPaddingTop() - getPaddingBottom();

          final int len = getChildCount();
          int       w;
          int       x = 0;
          int       heightSpec;

          if (hexactly) {
            heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
          } else {
            heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
          }

          int d = ScreenUtils.PLATFORM_PIXELS_1;

          for (int i = 0; i < len; i++) {
            View   v = getChildAt(i);
            Column c = cols[vp[i]];

            if (v.getVisibility() != View.GONE) {
              w = c.getWidth() + d;

              if (x + w > width) {
                w = width - x;
              }

              v.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY), heightSpec);
              height = Math.max(height, v.getMeasuredHeight());
              x      += w;
            }
          }

          if (height < header.headerHeightNum) {
            height = header.headerHeightNum;
          }

          measureSize.setSize(width, height);
        }
      } else {
        header.getSize(measureSize, false, Math.max(width - getPaddingLeft() - getPaddingRight(), 0));
      }

      setMeasuredDimension(resolveSize(measureSize.intWidth(), widthMeasureSpec),
                           resolveSize(measureSize.intHeight(), heightMeasureSpec));
    }
  }


  class ColumnView extends LabelView implements iContextMenuInfoHandler {
    Column  column;
    int     dataIndex;
    boolean pressed;

    public ColumnView(Context context, Column column, int dataIndex) {
      super(context);
      this.column    = column;
      this.dataIndex = dataIndex;

      PaintBucket pb = column.getHeaderPainter();

      if (pb == null) {
        pb = headerCellPainter;
      }

      if (pb != null) {
        iPlatformBorder b = pb.getBorder();

        if (b != null) {
          UIInsets in = b.getBorderInsets(new UIInsets());

          setPadding(in.intLeft(), in.intTop(), in.intRight() + 1, in.intBottom());
        }
      } else {
        UIInsets in = cellInsets;

        setPadding(in.intLeft(), in.intTop(), in.intRight() + 1, in.intBottom());
      }

      if (column.hasPopupMenu()) {
        // Platform.getAppContext().getActivity().registerForContextMenu(this);
      }
    }

    @Override
    public void draw(Canvas canvas) {
      PaintBucket pb = null;

      if (isPressed()) {
        pb = column.getHeaderSelectionPainter();

        if (pb == null) {
          pb = getPressedPainter();
        }
      }

      if (pb == null) {
        pb = column.getHeaderPainter();
      }

      if (pb == null) {
        pb = headerCellPainter;
      }

      if (pb != null) {
        columnGraphics = AndroidGraphics.fromGraphics(canvas, this, columnGraphics);

        if (showHeaderMargin) {
          int d1 = ScreenUtils.PLATFORM_PIXELS_1;
          int sx = getScrollX();

          if (sx >= 0) {    // should never happen
            d1 = 0;
          }

          aUIComponentPainter.paint(columnGraphics, sx + d1, getScrollY(), getWidth() - d1, getHeight(), pb);
        } else {
          aUIComponentPainter.paint(columnGraphics, getScrollX(), getScrollY(), getWidth(), getHeight(), pb);
        }

        columnGraphics.clear();
      }

      super.draw(canvas);

      if (sortColumn == dataIndex) {
        drawSortIcon(canvas, getSortIcon(descending));
      }
    }

    @Override
    public void handleContextMenuInfo(View v, ContextMenuInfo menuInfo) {}

    @Override
    public void setComponentPainter(iPlatformComponentPainter cp) {}

    public void setDataIndex(int dataIndex) {
      this.dataIndex = dataIndex;
    }

    @Override
    public int getCompoundPaddingTop() {
      return super.getCompoundPaddingTop() + iconPadding;
    }

    public int getDataIndex() {
      return dataIndex;
    }

    protected void drawSortIcon(Canvas canvas, iPlatformIcon icon) {
      int w = icon.getIconWidth();
      int h = icon.getIconHeight();
      int x = (getWidth() - w) / 2;
      int y = (int) getY() + ScreenUtils.PLATFORM_PIXELS_1;

      icon.paint(canvas, x, y, w, h);
    }
  }
}
