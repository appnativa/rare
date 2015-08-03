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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.BasicSelectionModel;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIPopupMenu;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.XPContainer;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iComponentPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.ui.renderer.aFormsLayoutRenderer;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.ui.table.iTableComponent.GridViewType;
import com.appnativa.rare.ui.table.iTableComponent.TableType;
import com.appnativa.rare.viewer.TableViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilterableList;
import com.appnativa.util.iFilterableList;

import java.util.Arrays;
import java.util.List;

public abstract class aTableHeader extends XPContainer implements iImageObserver {
  static protected int                        columnSizePad = ScreenUtils.PLATFORM_PIXELS_2;
  protected static iPlatformIcon              sort_asc, sort_dsc;
  protected Column[]                          columns           = new Column[0];
  protected Column[]                          multiTableColumns = null;
  protected UIInsets                          cellInsets        = new UIInsets(ScreenUtils.PLATFORM_PIXELS_2);
  protected UIInsets                          headerPadding     = new UIInsets(ScreenUtils.PLATFORM_PIXELS_2);
  protected int                               pressedColumn     = -1;
  protected int                               sortColumn        = -1;
  protected boolean                           showHeaderMargin  = true;
  protected boolean                           autoSizedColumns;
  protected UIColor                           bottomMarginColor;
  protected boolean                           chagingColumns;
  protected boolean                           columnSelectionAllowed;
  protected boolean                           descending;
  protected GridViewType                      gridViewMode;
  protected PaintBucket                       headerCellPainter;
  protected String                            headerHeight;
  protected int                               headerHeightNum;
  protected boolean                           headerTracksSections;
  protected int                               iconPadding;
  protected UIColor                           marginColor;
  protected int                               measuredHeight;
  protected UIDimension                       preferredSize;
  protected PaintBucket                       pressedHeaderPainter;
  protected boolean                           propertyTabe;
  protected List<UIAction>                    propertyTableActions;
  protected Column                            propertyTableHeaderColumnDescription;
  protected UILabelRenderer                   renderingComponent;
  protected BasicSelectionModel               selectionModel;
  protected int                               selectionPaintEndCol;
  protected int                               selectionPaintStartCol;
  protected boolean                           sortingAllowed = true;
  private UIDimension                         computeSize;
  iFilterableList<RenderableDataItem>         originalItems;
  private int                                 flatCount;
  private iFilterableList<RenderableDataItem> wrappRows;
  protected boolean                           paintLeftMargin;
  protected boolean                           paintRightMargin;
  private int                                 lastVisibleColumn;
  private int                                 firstVisibleColumn;

  enum SizeType { MIN, PREFERRED, FIT }

  protected aTableHeader() {
    marginColor       = TableHelper.getMarginColor();
    bottomMarginColor = TableHelper.getBottomMarginColor(marginColor);
    headerHeightNum   = calculateMinHeight();
  }

  public void setMultiTableColumns(List<Column> columns) {
    multiTableColumns = (columns == null)
                        ? null
                        : columns.toArray(new Column[columns.size()]);
  }

  public Column[] getMultiTableColumns() {
    return multiTableColumns;
  }

  public void addSpace(aListItemRenderer renderer, int extra) {
    Column[]  cols = columns;
    final int len  = cols.length;
    Column    c;
    int       i;
    int       count = 0;

    for (i = 0; i < len; i++) {
      if ((c = cols[i]).isVisible() &&!c.sizeFixed) {
        count++;
      }
    }

    if (count == 0) {
      return;
    }

    int    size = (extra / count);
    Column lc   = null;

    for (i = 0; i < len; i++) {
      if ((c = cols[i]).isVisible() &&!c.sizeFixed) {
        c.setWidth(c.getWidth() + size);
        lc = c;
      }
    }

    size = extra - (size * count);

    if ((size > 0) && (lc != null)) {
      lc.setWidth(lc.getWidth() + size);
    }
  }

  public int calculateColumnWidth(iTableModel tm, int col, Column c, int maxRows, UIDimension size, SizeType type) {
    RenderableDataItem    rowItem, item;
    int                   len      = Math.min(maxRows, tm.size());
    iPlatformItemRenderer renderer = getTableComponent().getItemRenderer();

    getColumnSize(renderer, col, c, type, getTableComponentEx().getWidth(), size);

    boolean preferred = type != SizeType.MIN;
    float   width     = size.width;
    float   height    = size.height;

    for (int i = 0; i < len; i++) {
      rowItem = tm.getRow(i);
      item    = rowItem.getItemEx(col);

      if ((item != null) && (item.getColumnSpan() == 1)) {
        TableHelper.calculateItemSize(this, renderer, c, item, i, rowItem, size, preferred
                ? 0
                : c.getWidth(), 0);

        if (size.width > width) {
          width = size.width;
        }
      }
    }

    size.height = height;
    size.width  = width;

    return (int) Math.ceil(width);
  }

  public void clearSelection() {
    if (selectionModel != null) {
      selectionModel.clearSelection();
    }
  }

  public boolean columnInRowClicked(int row, int col) {
    if (selectionModel != null) {
      RenderableDataItem item = ((TableComponent) getTableComponent()).getModel().getItemAt(row, col);

      if (!item.isSelectable()) {
        return false;
      }

      selectionModel.clearAndSelect(col);
    }

    return true;
  }

  public static Column[] createGridColumns(Column[] columns, List<RenderableDataItem> rows) {
    Column[] a     = columns;
    int      count = 0;
    int      len   = rows.size();

    for (int i = 0; i < len; i++) {
      int n = rows.get(i).size();

      if (n > count) {
        count = n;
      }
    }

    if (columns.length <= count) {
      a   = new Column[count];
      len = columns.length;
      System.arraycopy(columns, 0, a, 0, len);

      Column c = columns[len - 1];

      for (int i = len; i < count; i++) {
        a[i] = c;
      }
    }

    return a;
  }

  @Override
  public void dispose() {
    if (renderingComponent != null) {
      renderingComponent.dispose();
    }

    super.dispose();
    pressedHeaderPainter = null;
    renderingComponent   = null;
    columns              = null;
  }

  public boolean handleGridView(int width, int height, int rowHeght, boolean contentsChanged) {
    boolean changed = false;

    if (!chagingColumns) {
      Column                              c            = columns[0];
      RenderableDataItem                  selectedItem = contentsChanged
              ? null
              : ((TableViewer) getTableComponentEx().getWidget()).getSelectedItem();
      int                                 w            = c.calculatePreferedWidth(getTableComponentEx(), width);
      iFilterableList<RenderableDataItem> rows;

      if (contentsChanged || (originalItems == null)) {
        rows = getTableComponent().getModel().getRowsEx();

        if (rows.isEmpty()) {
          if (columns.length == 1) {
            return false;
          }

          wrappRows = null;
        }

        originalItems = rows;
        flatCount     = TableHelper.getFlatItemCount(rows);
      } else {
        rows = originalItems;
      }

      int n;
      int dx = 0;

      if (gridViewMode == GridViewType.VERTICAL_WRAP) {
        n = (int) Math.max(Math.floor((float) width / (float) (w + 1)), 1);

        if (!c.sizeFixed) {
          dx = (int) Math.floor((width - (n * w)) / n);
        }
      } else {
        float h = (float) Math.max(Math.floor((float) height / (float) rowHeght), 1);

        n = (int) Math.max(Math.floor((float) width / (float) (w + 1)), 1);

        if (n * h < flatCount) {
          n = (int) Math.max(Math.ceil(flatCount / h), 1);
        }

        if (!c.sizeFixed) {
          dx = (int) Math.floor((width - (n * w)) / n);
        }
      }

      if (dx < 0) {
        dx = 0;
      }

      updateGridColumnWidths(w + dx);

      if ((n != columns.length) || contentsChanged) {
        lastVisibleColumn  = -1;
        firstVisibleColumn = -1;
        chagingColumns     = true;
        columns            = getWrappedColumns(n);
        wrappRows          = wrapItems(rows, n);

        if (wrappRows == rows) {
          wrappRows = null;
        } else {
          rows = wrappRows;
        }

        setColumnsEx();
        resetTableModel(rows);

        if (selectedItem != null) {
          TableViewer tv = ((TableViewer) getTableComponentEx().getWidget());

          tv.setSelectedItem(selectedItem);
        }

        chagingColumns = false;
        changed        = true;
      }
    }

    if (changed) {
      setSize(getWidth(), 0);
    }

    return changed;
  }

  public boolean handleMouseRelease(MouseEvent e) {
    return true;
  }

  @Override
  public void imageLoaded(UIImage image) {
    getTableComponentEx().revalidate();
    getTableComponentEx().repaint();
  }

  public boolean paintColumn(Column col, iPlatformGraphics g, float x, float y, float width, float height) {
    if (!isGridView()) {
      PaintBucket       pb = col.getItemPainter();
      iComponentPainter cp = (pb == null)
                             ? null
                             : pb.getCachedComponentPainter();

      if (cp != null) {
        cp.paint(g, x, y, width, height, iPainter.UNKNOWN);

        return true;
      }
    }

    return false;
  }

  public boolean paintColumn(int i, iPlatformGraphics g, float x, float y, float width, float height) {
    if ((i > -1) && (i < columns.length)) {
      return paintColumn(columns[i], g, x, y, width, height);
    }

    return false;
  }

  public void reduceColumnSizes(iPlatformItemRenderer renderer, int toMuch) {
    Column[]    cols = columns;
    final int   len  = cols.length;
    Column      c;
    UIDimension size = new UIDimension();
    int         i;
    int         count = 0;

    for (i = 0; i < len; i++) {
      if ((c = cols[i]).isVisible()) {
        count++;

        if (c.preferedWidth != 0) {
          continue;
        }

        getColumnSize(renderer, i, c, SizeType.PREFERRED, getWidth(), size);
        c.setWidth(size.intWidth());
        toMuch -= size.width;

        if (toMuch < 1) {
          break;
        }
      }
    }

    if ((toMuch < 1) || (count == 0)) {
      return;
    }

    toMuch = (int) Math.ceil((float) toMuch / (float) count);

    for (i = 0; i < len; i++) {
      if ((c = cols[i]).isVisible()) {
        c.setWidth(Math.max(c.getWidth() - toMuch, 0));
      }
    }
  }

  @Override
  public void revalidate() {
    super.revalidate();
    preferredSize = null;
  }

  public boolean sizeColumnsToFitTableData() {
    int len = columns.length;

    if (len == 0) {
      return false;
    }

    int     maxRows = 500;
    Integer max     = Platform.getUIDefaults().getInteger("Rare.Table.sizeColumnsToFitMaxRows");

    if ((max != null) && (max.intValue() > 0)) {
      maxRows = max.intValue();
    }

    iTableModel tm = getTableComponent().getModel();
    Column      c;
    int         width  = 0;
    int         height = 0;
    UIDimension size   = computeSize;

    if (size == null) {
      size = computeSize = new UIDimension();
    }

    int w;
    int d = ScreenUtils.PLATFORM_PIXELS_1;

    for (int i = 0; i < len; i++) {
      c = columns[i];

      if (c.isVisible()) {
        if (c.sizeFixed) {
          w = c.calculateMinimumWidth(this, 100);
        } else {
          w = calculateColumnWidth(tm, i, c, maxRows, size, SizeType.FIT);
        }

        c.setWidth(w);
        height = Math.max(size.intHeight(), height);
        width  += w + d;
      }
    }

    autoSizedColumns = true;
    measuredHeight   = height;

    if (preferredSize == null) {
      preferredSize = new UIDimension(width, height);
    } else {
      preferredSize.setSize(width, height);
    }

    return true;
  }

  public void sort(Column c) {
    Column[]  cols = this.columns;
    final int len  = cols.length;

    for (int i = 0; i < len; i++) {
      if (cols[i] == c) {
        getTableComponent().sort(i);

        break;
      }
    }
  }

  @Override
  public boolean wantsLongPress() {
    return true;
  }

  public static iFilterableList<RenderableDataItem> wrapItems(iFilterableList<RenderableDataItem> rows, int count) {
    int len = rows.size();

    if (len == 0) {
      return rows;
    }

    RenderableDataItem row = new RenderableDataItem();

    row.ensureCapacity(count);

    FilterableList<RenderableDataItem> nrows = new FilterableList<RenderableDataItem>((len > 5)
            ? len + 1
            : 5);
    int                                j     = 0;

    for (int i = 0; i < len; i++) {
      RenderableDataItem orow = rows.get(i);
      int                n    = orow.size();

      for (int p = 0; p < n; p++) {
        row.add(orow.get(p));
        j++;

        if (j == count) {
          nrows.add(row);
          row = new RenderableDataItem();
          row.ensureCapacity(count);
          j = 0;
        }
      }
    }

    len = row.size();

    if (len > 0) {
      if (len < count) {
        for (int i = len; i < count; i++) {
          RenderableDataItem item = new RenderableDataItem();

          item.setSelectable(false);
          item.setVisible(false);
          row.add(item);
        }
      }

      nrows.add(row);
    }

    return nrows;
  }

  public void setBottomMarginColor(UIColor bottomMarginColor) {
    this.bottomMarginColor = bottomMarginColor;
  }

  public abstract void setColumnIcon(int col, iPlatformIcon icon);

  public void setColumnSelectionAllowed(boolean columnSelectionAllowed) {
    this.columnSelectionAllowed = columnSelectionAllowed;
  }

  public abstract void setColumnTitle(int col, CharSequence title);

  public void setColumnVisible(int col, boolean visible) {
    lastVisibleColumn  = -1;
    firstVisibleColumn = -1;
    setColumnVisibleEx(col, visible);
  }

  protected abstract void setColumnVisibleEx(int col, boolean visible);

  public void setColumns(List<Column> columns) {
    measuredHeight     = 0;
    lastVisibleColumn  = -1;
    firstVisibleColumn = -1;

    if (getComponentPainter() == null) {
      setupDafaultPainter();
    }

    iconPadding = 0;

    int      len       = columns.size();
    boolean  touchable = false;
    Column[] cols      = new Column[len];

    for (int i = 0; i < len; i++) {
      Column c = columns.get(i);

      cols[i] = c;

      if ((c.getHeaderActionListener() != null) || c.hasPopupMenu()) {
        touchable = true;
      }

      if (c.getCellRenderer() instanceof aFormsLayoutRenderer) {
        ((aFormsLayoutRenderer) c.getCellRenderer()).dataModelChanged(getTableComponent().getModel());
      }
    }

    if (sortingAllowed) {
      iconPadding = getSortIcon(true).getIconHeight() / 2;
      touchable   = true;
    }

    if (touchable) {
      if (Platform.isTouchDevice() && (headerHeight == null)) {
        headerHeight    = "1.75ln";
        headerHeightNum = calculateMinHeight();
      }
    }

    this.columns  = cols;
    preferredSize = null;
    resetSelectionPaintColumns();
    setColumnsEx();
  }

  @Override
  public void setFont(UIFont f) {
    super.setFont(f);
    measuredHeight  = 0;
    headerHeightNum = calculateMinHeight();
  }

  public void setGridViewType(GridViewType gridViewMode) {
    this.gridViewMode = gridViewMode;
  }

  /**
   * @param headerPainter
   *          the headerPainter to set
   */
  public void setHeaderCellPainter(PaintBucket headerPainter) {
    this.headerCellPainter = headerPainter;

    if (headerPainter != null) {
      if (headerPainter.getBorder() != null) {
        cellInsets = headerPainter.getBorder().getBorderInsets(cellInsets);
      }

      if (headerPainter.getForegroundColor() != null) {
        setForeground(headerPainter.getForegroundColor());
      }

      if (headerPainter.getFont() != null) {
        setFont(headerPainter.getFont());
      }
    }
  }

  public void setHeaderHeight(String height) {
    headerHeight    = height;
    headerHeightNum = calculateMinHeight();
  }

  public void setHeaderTracksSections(boolean headerTracksSections) {
    this.headerTracksSections = headerTracksSections;
  }

  /**
   * @param marginColor
   *          the marginColor to set
   */
  public void setMarginColor(UIColor marginColor) {
    if ((marginColor != null) && (marginColor.getAlpha() == 0)) {
      marginColor = null;
    }

    if (this.bottomMarginColor == this.marginColor) {
      this.bottomMarginColor = null;
    }

    this.marginColor = marginColor;

    if (this.bottomMarginColor == null) {
      this.bottomMarginColor = marginColor;
    }
  }

  /**
   * @param pb
   *          the headerPainter to set
   */
  public void setPressedHeaderPainter(PaintBucket pb) {
    this.pressedHeaderPainter = pb;
  }

  public void setPropertyTabe(boolean propertyTabe) {
    this.propertyTabe = propertyTabe;
  }

  public void setPropertyTableActions(List<UIAction> propertyTableActions) {
    this.propertyTableActions = propertyTableActions;
  }

  public void setPropertyTableHeaderColumnDescription(Column propertyTableHeaderColumnDescription) {
    this.propertyTableHeaderColumnDescription = propertyTableHeaderColumnDescription;
  }

  public void setSelectedIndex(int index) {
    if (selectionModel != null) {
      selectionModel.clearAndSelect(index);
    }
  }

  public void setSelectedIndices(int[] indices) {
    if (selectionModel != null) {
      selectionModel.clearAndSelect(indices);
    }
  }

  public void setShowHeaderMargin(boolean showHeaderMargin) {
    this.showHeaderMargin = showHeaderMargin;
  }

  public void setSortColumn(int sortColumn, boolean descending) {
    if (sortingAllowed) {
      if ((this.sortColumn != -1) && (this.sortColumn != sortColumn) && isShowing()) {
        repaintColumn(this.sortColumn);
      }

      this.sortColumn = sortColumn;
      this.descending = descending;

      if ((this.sortColumn != -1) && isShowing()) {
        repaintColumn(this.sortColumn);
      }
    }
  }

  /**
   * @param sortingAllowed
   *          the sortingAllowed to set
   */
  public void setSortingAllowed(boolean sortingAllowed) {
    this.sortingAllowed = sortingAllowed;
  }

  public UIColor getBottomMarginColor() {
    return bottomMarginColor;
  }

  public Column getColumnAt(int index) {
    if ((index > -1) && (index < columns.length)) {
      return columns[index];
    }

    return null;
  }

  public abstract Column getColumnForViewAt(int viewColumn);

  public int getColumnCount() {
    return columns.length;
  }

  public abstract int getColumnIndexAt(float x, float y);

  public Column[] getColumns() {
    return columns;
  }

  public int getDynamicColumnsWidth() {
    if ((columns == null) || (columns.length == 0)) {
      return 0;
    }

    int n = columns[0].calculatePreferedWidth(getTableComponentEx(), 400);

    return n * columns.length;
  }

  public int getFirstVisibleColumnInView() {
    UIRectangle r = getTableComponent().getViewRect();
    int         n = getColumnIndexAt(r.x, 0);

    if (n == -1) {
      if (r.x == 0) {
        n = getFirstVisibleColumn();
      } else {
        n = getLastVisibleColumn();
      }

      if (n != -1) {
        n = getTableComponent().convertModelIndexToView(n);
      }
    }

    return n;
  }

  public int getLastVisibleColumnInView() {
    UIRectangle r = getTableComponent().getViewRect();
    int         n = getColumnIndexAt(r.x + r.width - 1, 0);

    if (n == -1) {
      n = getLastVisibleColumn();

      if (n != -1) {
        n = getTableComponent().convertModelIndexToView(n);
      }
    }

    return n;
  }

  public int getFirstVisibleColumn() {
    if (firstVisibleColumn == -1) {
      Column[]  cols = columns;
      final int len  = cols.length;

      for (int i = 0; i < len; i++) {
        if (cols[i].isVisible()) {
          firstVisibleColumn = i;

          break;
        }
      }
    }

    return firstVisibleColumn;
  }

  public int getLastVisibleColumn() {
    if (lastVisibleColumn == -1) {
      Column[]  cols = columns;
      final int len  = cols.length;

      for (int i = len - 1; i >= 0; i--) {
        if (cols[i].isVisible()) {
          lastVisibleColumn = i;

          break;
        }
      }
    }

    return lastVisibleColumn;
  }

  public int getColumnX(int column) {
    Column[]  cols = columns;
    final int len  = cols.length;
    int       x    = 0;
    int       d    = ScreenUtils.PLATFORM_PIXELS_1;

    for (int i = 0; i < len; i++) {
      if (i == column) {
        break;
      }

      if (cols[i].isVisible()) {
        x += cols[i].getWidth();

        if (i > 0) {
          x += d;
        }
      }
    }

    return x;
  }

  public GridViewType getGridViewType() {
    return gridViewMode;
  }

  public UIPoint getHotspotPopupLocation(int column) {
    return null;
  }

  public UIColor getMarginColor() {
    return marginColor;
  }

  public int getMeasuredHeight() {
    if (measuredHeight == 0) {
      return 0;
    }

    return (int) Math.ceil(measuredHeight + headerPadding.top + headerPadding.bottom + ScreenUtils.PLATFORM_PIXELS_1);
  }

  @Override
  public UIDimension getMinimumSize(UIDimension size) {
    if (size == null) {
      size = new UIDimension();
    }

    getSize(size, true, 100);

    return size;
  }

  public void getColumnSize(iPlatformItemRenderer renderer, int col, Column c, SizeType type, float tableWidth,
                            UIDimension size) {
    UIInsets    in = cellInsets;
    PaintBucket pb = c.getHeaderPainter();

    if (pb != null) {
      iPlatformBorder b = pb.getBorder();

      if (b != null) {
        in = b.getBorderInsets(new UIInsets());
      }
    }

    if (renderingComponent == null) {
      renderingComponent = new UILabelRenderer();
    }

    UIFont f = c.getHeaderFont();

    if (f == null) {
      f = getFont();
    }

    renderer.configureRenderingComponent(this, renderingComponent, c, 0, false, false, null, null);
    renderingComponent.setWordWrap(c.headerWordWrap);

    if (f != null) {
      renderingComponent.setFont(f);
    }

    renderingComponent.getComponent(c.getColumnTitle(), c).getPreferredSize(size);
    size.width  += in.left + in.right;
    size.height += in.top + in.bottom;

    int w = 0;

    switch(type) {
      case MIN :
        w = c.calculateMinimumWidth(getTableComponentEx(), (tableWidth == 0)
                ? 100
                : tableWidth);

        break;

      case PREFERRED :
        if (c.preferedWidth != 0) {
          w = c.calculatePreferedWidth(getTableComponentEx(), (tableWidth == 0)
                  ? 100
                  : tableWidth);
        }

        break;

      default :
        break;
    }

    size.width  = Math.max(size.width, w) + columnSizePad;
    size.height += ScreenUtils.PLATFORM_PIXELS_1 + iconPadding;
  }

  @Override
  public UIDimension getPreferredSize(UIDimension size) {
    if (size == null) {
      size = new UIDimension();
    }

    if (preferredSize != null) {
      size.setSize(preferredSize);

      return size;
    }

    size = super.getPreferredSize(size);

    if (preferredSize == null) {
      preferredSize = new UIDimension(size);
    } else {
      preferredSize.setSize(size);
    }

    return size;
  }

  @Override
  public void getPreferredSizeEx(UIDimension size, float maxWidth) {
    getSize(size, false, maxWidth);
  }

  public int getPreferredHeight() {
    if (preferredSize != null) {
      return preferredSize.intHeight();
    }

    return getPreferredSize(null).intHeight();
  }

  public List<UIAction> getPropertyTableActions() {
    return propertyTableActions;
  }

  public Column getPropertyTableHeaderColumnDescription() {
    return propertyTableHeaderColumnDescription;
  }

  public int getSelectedColumn() {
    return (selectionModel == null)
           ? -1
           : selectionModel.getLeadIndex();
  }

  public int getSelectedColumnCount() {
    return (selectionModel == null)
           ? 0
           : selectionModel.getSelectionCount();
  }

  public int[] getSelectedColumnIndices() {
    return (selectionModel == null)
           ? null
           : selectionModel.getSelectedIndices();
  }

  public int getSelectionPaintEndColumn() {
    return selectionPaintEndCol;
  }

  public int getSelectionPaintStartColumn() {
    return selectionPaintStartCol;
  }

  public int getGridColumnWidth(UIDimension reuseableSize) {
    Column c = columns[0];

    if (reuseableSize == null) {
      reuseableSize = new UIDimension();
    }

    aListItemRenderer renderer = (aListItemRenderer) getTableComponent().getItemRenderer();

    getColumnSize(renderer, 0, c, SizeType.PREFERRED, 0, reuseableSize);

    return reuseableSize.intWidth() + ScreenUtils.PLATFORM_PIXELS_1;
  }

  public int getOriginalsGridViewsRowCount() {
    return flatCount;
  }

  public void getSize(UIDimension size, boolean minimum, float tableWidth) {
    int      width  = 0;
    int      height = 0;
    Column[] cols   = columns;
    int      len    = cols.length;
    Column   c;

    if (gridViewMode == GridViewType.VERTICAL_WRAP) {
      len = 1;
    }

    aListItemRenderer renderer = (aListItemRenderer) getTableComponent().getItemRenderer();

    if (autoSizedColumns && (measuredHeight > 0)) {
      height = measuredHeight;
    }

    int d = ScreenUtils.PLATFORM_PIXELS_1;

    for (int i = 0; i < len; i++) {
      if ((c = cols[i]).isVisible()) {
        if (autoSizedColumns && (measuredHeight > 0)) {
          width += c.getWidth() + d;
        } else {
          getColumnSize(renderer, i, c, minimum
                                        ? SizeType.MIN
                                        : SizeType.PREFERRED, tableWidth, size);
          width  += size.width + d;
          height = Math.max(height, size.intHeight());
        }
      }
    }

    measuredHeight = height;
    size.width     = width;
    size.height    = height;
    size.width     += headerPadding.left + headerPadding.right;
    size.height    += headerPadding.top + headerPadding.bottom;
    size.height    += d;

    if (headerHeightNum > size.height) {
      size.height = headerHeightNum;
    }
  }

  public int getSpanWidth(int start, int span) {
    return TableHelper.getSpanWidth(start, span, columns);
  }

  public abstract iTableComponent getTableComponent();

  protected iPlatformComponent getTableComponentEx() {
    return getTableComponent().getPlatformComponent();
  }

  public int getVisibleColumnCount() {
    Column[]  cols  = columns;
    final int len   = cols.length;
    int       count = 0;

    for (int i = 0; i < len; i++) {
      if (cols[i].isVisible()) {
        count++;
      }
    }

    return count;
  }

  /**
   * Get the height of a row for the table that the header belongs to.
   * <p>
   * This method is here so that it can be shared across platforms. It caches
   * the height of the height property of the renderable data item for the row
   * <p>
   *
   * @param row
   *          the row
   * @param renderer
   *          the render to use to calculate the height
   * @param defaultHeight
   *          the default table row height
   * @return the height of the row
   */
  public int getRowHeight(int row, iPlatformItemRenderer renderer, int defaultHeight) {
    iTableComponent    tc          = getTableComponent();
    iPlatformComponent pc          = tc.getPlatformComponent();
    iTableModel        tm          = tc.getModel();
    RenderableDataItem rowItem     = tm.getRow(row);
    RenderableDataItem mainRowItem = tm.getRow(row);
    int                h           = rowItem.getHeight();

    if (h < 1) {
      Column[] columns = multiTableColumns;

      if (columns == null) {
        columns = this.columns;
      } else {
        tm          = ((TableViewer) pc.getWidget()).getTableComponent().getModel();
        mainRowItem = tm.getRow(row);
        h           = mainRowItem.getHeight();
      }

      if (h < 1) {
        h = TableHelper.calculateRowHeight(pc, renderer, tm, row, columns, false, defaultHeight);
      }

      rowItem.setHeight(h);

      if (mainRowItem != null) {
        mainRowItem.setHeight(h);
      }
    }

    return h;
  }

  @Override
  public int getWidth() {
    if (!isVisible()) {
      Column[]  cols  = columns;
      final int len   = cols.length;
      int       width = 0;

      for (int i = 0; i < len; i++) {
        if (cols[i].isVisible()) {
          width += cols[i].getWidth();
        }
      }

      return width;
    }

    return super.getWidth();
  }

  public boolean isAutoSizedColumns() {
    return autoSizedColumns;
  }

  public boolean isColumnSelectionAllowed() {
    return columnSelectionAllowed;
  }

  public boolean isColumnVisible(int col) {
    return getColumnAt(col).isVisible();
  }

  public boolean isGridView() {
    return gridViewMode != null;
  }

  public boolean isHeaderTracksSections() {
    return headerTracksSections;
  }

  public boolean isPropertyTabe() {
    return propertyTabe;
  }

  public boolean isShowHeaderMargin() {
    return showHeaderMargin;
  }

  /**
   * @return the sortingAllowed
   */
  public boolean isSortingAllowed() {
    return sortingAllowed;
  }

  protected int calculateMinHeight() {
    int h = 0;

    if (headerHeight != null) {
      h = ScreenUtils.toPlatformPixelHeight(headerHeight, this, 100);
    }

    String s = Platform.getUIDefaults().getString("Rare.Table.minimumTouchableHeaderHeight");

    if (s != null) {
      int hh = ScreenUtils.toPlatformPixelHeight(s, this, 100);

      h = Math.max(hh, h);
    }

    return h;
  }

  protected boolean handleMousePress(MouseEvent e) {
    return true;
  }

  protected void repaintColumn(int col) {
    repaint();
  }

  public int getColumnIndexForClickedColumn(int col) {
    iTableComponent tc   = getTableComponent();
    TableType       type = tc.getTableType();

    if ((type == null) || (type == TableType.HEADER)) {
      return col;
    }

    iTableComponent mtc = ((TableViewer) getWidget()).getTableComponent();

    tc = mtc.getRowHeaderTable();

    if (tc != null) {
      col += tc.getColumnCount();
    }

    if (type == TableType.FOOTER) {
      col += mtc.getMainTable().getColumnCount();
    }

    return col;
  }

  protected void resetSelectionPaintColumns() {
    Column[] cols = columns;
    Column   c;
    int      len = cols.length;

    selectionPaintStartCol = 0;
    selectionPaintEndCol   = len - 1;

    for (int i = 0; i < len; i++) {
      c = cols[i];

      if (!c.overrideSelectionBackground && c.isVisible()) {
        break;
      }

      selectionPaintStartCol++;
    }

    for (int i = len - 1; i > selectionPaintStartCol; i--) {
      c = cols[i];

      if (c.isVisible() &&!c.overrideSelectionBackground) {
        break;
      }

      selectionPaintEndCol--;
    }
  }

  protected void resetTableModel(iFilterableList<RenderableDataItem> rows) {
    getTableComponent().getModel().resetModel(Arrays.asList(columns), rows);
  }

  protected void setupDafaultPainter() {
    if (headerCellPainter == null) {
      PaintBucket pb;

      if (propertyTabe) {
        pb = new PaintBucket(ColorUtils.getBackground());
      } else {
        pb = TableHelper.getDefaultPainter(getBackgroundEx());
      }

      setComponentPainter(pb.getCachedComponentPainter());
    }
  }

  protected abstract void tableHadInteraction();

  protected void updateGridColumnWidths(int width) {
    for (Column c : columns) {
      c.setWidth(width);
    }
  }

  protected abstract void setColumnPressed(int col, boolean pressed);

  protected abstract void setColumnsEx();

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    getSize(size, true, 100);
  }

  protected PaintBucket getPressedPainter() {
    if (pressedHeaderPainter == null) {
      pressedHeaderPainter = TableHelper.getPressedPainter(getBackground());
    }

    return pressedHeaderPainter;
  }

  protected iPlatformIcon getSortIcon(boolean descending) {
    if (sort_asc == null) {
      sort_asc = Platform.getAppContext().getResourceAsIcon("Rare.icon.sort_asc");
      sort_dsc = Platform.getAppContext().getResourceAsIcon("Rare.icon.sort_dsc");
    }

    return descending
           ? sort_dsc
           : sort_asc;
  }

  protected Column[] getWrappedColumns(int count) {
    Column   c    = columns[0];
    Column[] list = new Column[count];

    for (int i = 0; i < count; i++) {
      list[i] = c;
    }

    return list;
  }

  public boolean isPaintRightMargin() {
    return paintRightMargin;
  }

  public void setPaintRightMargin(boolean paintRightMargin) {
    this.paintRightMargin = paintRightMargin;
  }

  public boolean isPaintLeftMargin() {
    return paintLeftMargin;
  }

  public void setPaintLeftMargin(boolean paintLeftMargin) {
    this.paintLeftMargin = paintLeftMargin;
  }

  protected class MouseListener implements iMouseListener, iMouseMotionListener {
    UIPoint       downPoint;
    float         downY;
    private int   originalPressedColumn = -1;
    private float slop                  = PlatformHelper.getTouchSlop();

    public MouseListener() {}

    @Override
    public void mouseDragged(MouseEvent e) {
      if (downPoint != null) {
        float x = e.getX();
        float y = e.getY();

        if ((Math.abs(x - downPoint.x) > slop) || (Math.abs(y - downPoint.y) > slop)) {
          pressCanceled(e);
        }
      }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      if (originalPressedColumn != -1) {
        UIPoint p = e.getPoint();
        int     n = getColumnIndexAt((int) p.x, (int) p.y);

        if (n == originalPressedColumn) {
          pressedColumn = originalPressedColumn;
          setColumnPressed(pressedColumn, true);

          return;
        }
      }
    }

    @Override
    public void mouseExited(MouseEvent e) {
      if (pressedColumn != -1) {
        setColumnPressed(pressedColumn, false);
        pressedColumn = -1;
      }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
      pressedColumn = -1;

      if (!getTableComponentEx().hasHadInteraction()) {
        tableHadInteraction();
      }

      if (!handleMousePress(e)) {
        return;
      }

      UIPoint p = e.getPoint();
      int     n = getColumnIndexAt((int) p.x, (int) p.y);

      if (n != -1) {
        Column  c = getColumnAt(n);
        iWidget w = Platform.findWidgetForComponent(aTableHeader.this);

        if (e.isPopupTrigger()) {
          UIPopupMenu menu = c.getPopupMenu(w);

          if (menu != null) {
            p = e.getLocationOnScreen();
            menu.show(aTableHeader.this, (int) p.x, (int) p.y);
            e.consume();
          }
        } else if (c.isSelectable()
                   && (isSortingAllowed() || (c.getHeaderActionListener() != null) || (c.getPopupMenu(w) != null))) {
          pressedColumn         = n;
          downPoint             = p;
          originalPressedColumn = n;
          setColumnPressed(n, true);
        }
      }

      e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if (!handleMouseRelease(e)) {
        return;
      }

      int pc = pressedColumn;

      originalPressedColumn = pressedColumn = -1;

      if (pc != -1) {
        setColumnPressed(pc, false);
      }

      UIPoint p = e.getPoint();
      int     n = getColumnIndexAt((int) p.x, (int) p.y);

      if (n == -1) {
        return;
      }

      iWidget w = Platform.findWidgetForComponent(aTableHeader.this);
      Column  c = getColumnAt(n);

      if (e.isPopupTrigger()) {
        UIPopupMenu menu = c.getPopupMenu(w);

        if (menu != null) {
          p = e.getLocationOnScreen();
          menu.show(aTableHeader.this, (int) p.x, (int) p.y);
        }
      } else if (pc != -1) {
        if (c.getHeaderActionListener() != null) {
          c.getHeaderActionListener().actionPerformed(new ActionEvent(c));
        } else if (isSortingAllowed()) {
          ((TableViewer) getWidget()).sort(getColumnIndexForClickedColumn(n),
                                           !((TableViewer) getWidget()).isSortDescending());
        }
      }
    }

    @Override
    public void pressCanceled(MouseEvent e) {
      if (pressedColumn != -1) {
        setColumnPressed(pressedColumn, false);
        originalPressedColumn = pressedColumn = -1;
      }
    }

    @Override
    public boolean wantsLongPress() {
      return true;
    }

    @Override
    public boolean wantsMouseMovedEvents() {
      return false;
    }
  }
}
