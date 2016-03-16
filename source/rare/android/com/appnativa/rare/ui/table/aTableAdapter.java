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

import android.annotation.SuppressLint;

import android.content.Context;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import android.widget.ListView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.platform.android.ui.view.ListViewEx.iListViewRow;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.ui.table.iTableComponent.TableType;
import com.appnativa.rare.ui.tree.TreeViewEx;
import com.appnativa.rare.ui.tree.iTreeItem;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IdentityArrayList;
import com.appnativa.util.iFilter;
import com.appnativa.util.iFilterableList;

import java.util.List;

/**
 *
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public abstract class aTableAdapter extends aDataItemTableModel {
  static RenderableDataItem             NULL_ITEM = new RenderableDataItem();
  protected int                         viewTypes = 1;
  protected UIColor                     alternatingColor;
  protected Paint                       alternatingColorPaint;
  protected boolean                     autoSizeColumns;
  protected boolean                     autoSizeRows;
  protected String[]                    characterSections;
  protected Drawable                    checkMarkBoxDrawable;
  protected Drawable                    checkMarkDrawable;
  protected int                         checkboxHeight;
  protected int                         checkboxWidth;
  protected Column                      columnDescription;
  protected iPlatformRenderingComponent defaultRenderer;
  protected boolean                     deletingAllowed;
  protected Paint                       dividerPaint;
  protected boolean                     draggingAllowed;
  protected UIColor                     foregroundColor;
  protected boolean                     hasCustomRenderers;
  protected int                         indicatorHeight;
  protected int                         indicatorWidth;
  protected boolean                     initialized;
  protected iPlatformItemRenderer       itemRenderer;
  protected boolean                     linkedSelection;
  protected iPlatformComponent          listComponent;
  protected TableRow                    measuringRow;
  protected int                         minRowHeight;
  protected UIDimension                 preferedSize;
  protected iPlatformRenderingComponent renderingComponent;
  protected int                         rowHeight;
  protected boolean                     showHorizontalLines;
  protected boolean                     showSelection;
  protected boolean                     showVerticalLines;
  protected int                         tallestLine;
  private final DataSetObservable       mDataSetObservable = new DataSetObservable();
  protected boolean                     selectable         = true;
  protected boolean                     filteringEnabled   = true;
  IdentityArrayList<DataSetObserver>    dataSetObservers   = new IdentityArrayList<DataSetObserver>();
  private boolean                       columnSelectionAllowed;
  private boolean                       editing;
  private boolean                       showRootHandles;
  TableType                             tableType;
  TableHeader                           header;
  long columnMovedTime;
  static {
    NULL_ITEM.setVisible(false);
    NULL_ITEM.setSelectable(false);
  }

  public aTableAdapter() {}

  public aTableAdapter(iWidget widget) {
    setWidget(widget);
  }

  public void addEx(RenderableDataItem row) {
    addRowEx(row);
  }

  public boolean areAllItemsEnabled() {
    return false;
  }

  @Override
  public void dispose() {
    mDataSetObservable.notifyInvalidated();
    dataSetObservers.clear();
    super.dispose();
  }

  /**
   * Notifies the attached View that the underlying data has been changed and it
   * should refresh itself.
   */
  public void notifyDataSetChanged() {
    if (isEventsEnabled()) {
      mDataSetObservable.notifyChanged();
    }
  }

  public void paintEmptyRowColumns(iPlatformGraphics g, float x, float y, float width, float height) {
    if (showHorizontalLines) {
      height--;
    }

    ((TableComponent) listComponent).getTableHeader().paintEmptyRowColumns(g, y, height, width);
  }

  public void registerDataSetObserver(DataSetObserver observer) {
    mDataSetObservable.registerObserver(observer);
    dataSetObservers.addIfNotPresent(observer);
  }

  @Override
  public void resetModel(List<Column> columns, iFilterableList<RenderableDataItem> rows) {
    super.resetModel(columns, rows);
  }

  public void unregisterDataSetObserver(DataSetObserver observer) {
    if (dataSetObservers.contains(observer)) {
      dataSetObservers.remove(observer);
      mDataSetObservable.unregisterObserver(observer);
    }
  }

  public void updateForInsertionOrDeletion() {
    int start = rowCount;

    rowCount = rowData.size();

    int end = rowCount;

    if (end > start) {
      this.rowsInserted(start, end - 1);
    } else if (start > end) {
      this.rowsDeleted(end, start - 1);
    }
  }

  /**
   * @param alternatingColor
   *          the alternatingColor to set
   */
  public void setAlternatingColor(UIColor alternatingColor) {
    this.alternatingColor = alternatingColor;

    if (alternatingColor != null) {
      if (alternatingColorPaint == null) {
        alternatingColorPaint = new Paint();
        alternatingColorPaint.setStyle(Style.FILL);
      }

      alternatingColorPaint.setColor(alternatingColor.getColor());
    }
  }

  public void setAutoSizeColumns(boolean autoSizeColumns) {
    this.autoSizeColumns = autoSizeColumns;
  }

  public void setAutoSizeRows(boolean autoSizeRows) {
    this.autoSizeRows = autoSizeRows;
  }

  /**
   * @param checkMarkBoxDrawable
   *          the checkMarkBoxDrawable to set
   */
  public void setCheckMarkBoxDrawable(Drawable checkMarkBoxDrawable) {
    this.checkMarkBoxDrawable = checkMarkBoxDrawable;
  }

  /**
   * @param checkMarkDrawable
   *          the checkMarkDrawable to set
   */
  public void setCheckMarkDrawable(Drawable checkMarkDrawable) {
    this.checkMarkDrawable = checkMarkDrawable;
  }

  public void setColumnDescription(Column column) {
    this.columnDescription = column;
  }

  public void setEditing(boolean editing) {
    this.editing = editing;
  }

  /**
   * @param filteringEnabled
   *          the filteringEnabled to set
   */
  public void setFilteringEnabled(boolean filteringEnabled) {
    this.filteringEnabled = filteringEnabled;
  }

  /**
   * @param foregroundColor
   *          the foregroundColor to set
   */
  public void setForegroundColor(UIColor foregroundColor) {
    this.foregroundColor = foregroundColor;
  }

  /**
   * @param hasCustomRenderers
   *          the hasCustomRenderers to set
   */
  public void setHasCustomRenderers(boolean hasCustomRenderers) {
    this.hasCustomRenderers = hasCustomRenderers;
  }

  /**
   * @param itemRenderer
   *          the itemRenderer to set
   */
  public void setItemRenderer(iPlatformItemRenderer itemRenderer) {
    this.itemRenderer = itemRenderer;
  }

  public void setLinkedSelection(boolean linkedSelection) {
    this.linkedSelection = linkedSelection;
  }

  public void setMinRowHeight(int min) {
    this.minRowHeight = min;

    if (rowHeight < min) {
      rowHeight = min;
    }
  }

  /**
   * @param rowHeight
   *          the rowHeight to set
   */
  public void setRowHeight(int rowHeight) {
    if (rowHeight < minRowHeight) {
      rowHeight = minRowHeight;
    }

    this.rowHeight = rowHeight;
  }

  /**
   * @param selectable
   *          the selectable to set
   */
  public void setSelectable(boolean selectable) {
    this.selectable = selectable;
  }

  public void setShowHorizontalLines(boolean show) {
    showHorizontalLines = show;
  }

  public void setShowVerticalLines(boolean show) {
    showVerticalLines = show;
  }

  public void setWidget(iWidget widget) {
    this.theWidget = widget;
  }

  public void setListComponent(iPlatformComponent c) {
    listComponent = c;

    if (c instanceof TableComponent) {
      tableType = ((TableComponent) c).getTableType();
      header    = ((TableComponent) c).getHeader();
    }
  }

  public RenderableDataItem get(int index) {
    RenderableDataItem di = super.get(index);

    if ((columnDescription != null) &&!di.isConverted()) {
      columnDescription.convert(theWidget, di);
    }

    return di;
  }

  /**
   * @return the alternatingRowColor
   */
  public UIColor getAlternatingColor() {
    return alternatingColor;
  }

  public Paint getAlternatingColorPaint() {
    return alternatingColorPaint;
  }

  /**
   * @return the checkMarkBoxDrawable
   */
  public Drawable getCheckMarkBoxDrawable() {
    return checkMarkBoxDrawable;
  }

  /**
   * @return the checkMarkDrawable
   */
  public Drawable getCheckMarkDrawable() {
    return checkMarkDrawable;
  }

  public Column getColumnDescription() {
    return columnDescription;
  }

  public int getCount() {
    return getRowCount();
  }

  public void columnMoved(int form, int to) {
    columnMovedTime=System.currentTimeMillis();
  }
  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    return getView(position, convertView, parent);
  }

  public Object getItem(int position) {
    return get(position);
  }

  public long getItemId(int position) {
    return position;
  }

  public iPlatformItemRenderer getItemRenderer() {
    return itemRenderer;
  }

  public int getItemViewType(int position) {
    return (alternatingColor != null)
           ? (position % 2)
           : 0;
  }

  public iFilter getLastFilter() {
    return rowData.getLastFilter();
  }

  public int getSelectionPaintEndX(int currentEndX) {
    return ((TableComponent) listComponent).getTableHeader().getSelectionPaintEndX(currentEndX);
  }

  public int getSelectionPaintStartX(int currentStartX) {
    return ((TableComponent) listComponent).getTableHeader().getSelectionPaintStartX(currentStartX);
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    final boolean isSelected       = ((parent instanceof ListView) && (position < rowCount))
                                     ? ((ListView) parent).isItemChecked(position)
                                     : false;
    final boolean useAlternatColor = position % 2 == 1;

    return getViewEx(position, (position < rowCount)
                               ? get(position)
                               : null, useAlternatColor, isSelected, convertView, parent);
  }

  public int getViewTypeCount() {
    return 1;
  }

  public boolean hasStableIds() {
    return false;
  }

  public boolean isAutoSizeColumns() {
    return autoSizeColumns;
  }

  public boolean isAutoSizeRows() {
    return autoSizeRows;
  }

  public boolean isEditing() {
    return editing;
  }

  public boolean isEnabled(int position) {
    if (rowData == null) {    // possible when disposed during a muti-touch event
      return false;
    }

    return selectable && get(position).isEnabled();
  }

  /**
   * @return the filteringEnabled
   */
  public boolean isFilteringEnabled() {
    return filteringEnabled;
  }

  public boolean isLinkedSelection() {
    return linkedSelection;
  }

  /**
   * @return the selectable
   */
  public boolean isSelectable() {
    return selectable;
  }

  Drawable getCheckmarkDrawableEx(UIColor fg) {
    if (checkMarkDrawable == null) {
      if ((selectionType == SelectionType.CHECKED_LEFT) || (selectionType == SelectionType.CHECKED_RIGHT)) {
        if (fg == null) {
          fg = Platform.getUIDefaults().getColor("Label.foreground");
        }

        if (fg.isDarkColor()) {
          checkMarkDrawable = Platform.getUIDefaults().getDrawable("Rare.List.darkCheckedIcon");
        } else {
          checkMarkDrawable = Platform.getUIDefaults().getDrawable("Rare.List.lightCheckedIcon");
        }
      }
    }

    return checkMarkDrawable;
  }

  protected void createDefaultRenderer(View parent) {
    if (defaultRenderer == null) {
      defaultRenderer = new UILabelRenderer(parent.getContext());
    }
  }

  // protected void fireTableCellUpdated(int row, int col) {
  // updateRowHeightForTallestLine();
  // notifyDataSetChanged();
  // }
  protected void fireTableDataChanged() {
    notifyDataSetChanged();
    super.fireTableDataChanged();
  }

  protected void fireTableRowsDeleted(int firstRow, int lastRow) {
    notifyDataSetChanged();
    super.fireTableRowsDeleted(firstRow, lastRow);
  }

  protected void fireTableRowsDeleted(int index0, int index1, List<RenderableDataItem> removed) {
    notifyDataSetChanged();
    super.fireTableRowsDeleted(index0, index1, removed);
  }

  protected void fireTableRowsInserted(int firstRow, int lastRow) {
    notifyDataSetChanged();
    super.fireTableRowsInserted(firstRow, lastRow);
  }

  protected void fireTableRowsUpdated(int firstRow, int lastRow) {
    notifyDataSetChanged();
    super.fireTableRowsUpdated(firstRow, lastRow);
  }

  @Override
  public void setShowLastDivider(boolean show) {}

  protected void fireTableStructureChanged() {
    notifyDataSetChanged();
    super.fireTableStructureChanged();
  }

  protected EventListenerList getListenerList() {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    return listenerList;
  }

  protected TableRow getViewEx(int row, RenderableDataItem item, boolean useAlternatColor, boolean isSelected,
                               View convertView, View parent) {
    if (defaultRenderer == null) {
      createDefaultRenderer(parent);
    }

    ListViewEx lv = null;

    if (parent instanceof ListViewEx) {
      lv = (ListViewEx) parent;
    }

    if ((lv != null) &&!initialized) {
      initialized     = true;
      indicatorWidth  = lv.getIndicatorWidth();
      indicatorHeight = lv.getIndicatorHeight();
      dividerPaint    = lv.getDividerPaint();
      dividerPaint.setStrokeWidth(ScreenUtils.PLATFORM_PIXELS_1 / 2);

      if (lv instanceof TreeViewEx) {
        showRootHandles = ((TreeViewEx) lv).isShowRootHandles();
      }

      columnSelectionAllowed = lv.getHeader().isColumnSelectionAllowed();
    }

    TableRow           rc   = null;
    iPlatformComponent list = listComponent;

    if (convertView instanceof TableRow) {
      rc = (TableRow) convertView;
      rc.prepareForReuse(lv, row, isSelected);
    }

    if (rc == null) {
      rc = new TableRow(parent.getContext());
    }

    if (list != null) {
      rc.render(parent, list, itemRenderer, row, item, isSelected, isSelected);
    } else {
      list = null;
    }

    return rc;
  }

  @SuppressLint({ "ViewConstructor", "ClickableViewAccessibility" })
  public class TableRow extends ViewGroupEx implements iListViewRow {
    iPlatformIcon               indicator;
    int                         position;
    iPlatformRenderingComponent renderers[];
    boolean                     selected;
    int                         indent;
    boolean                     hilight;
    long myColumnMovedTime;
    public TableRow(Context context) {
      super(context);
      setMeasureType(MeasureType.HORIZONTAL);
      myColumnMovedTime=columnMovedTime;
    }

    @Override
    public void draw(Canvas canvas) {
      graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

      int                             top          = getScrollY();
      int                             right        = getWidth();
      int                             bottom       = getHeight();
      int                             left         = getScrollX();
      final iPlatformComponentPainter cp           = componentPainter;
      final boolean                   hasContainer = deletingAllowed || draggingAllowed;

      if (!hasContainer && (alternatingColorPaint != null) && (position % 2 == 1)) {
        canvas.drawRect(left, top, right, bottom, alternatingColorPaint);
      }

      boolean showvlines;

      if (cp == null) {
        showvlines = paintCanvas(canvas);
        super.draw(canvas);
      } else {
        cp.paint(graphics, left, top, right - left, bottom - top, iPlatformPainter.UNKNOWN, false);
        showvlines = paintCanvas(canvas);
        super.draw(canvas);
        cp.paint(graphics, left, top, right - left, bottom - top, iPlatformPainter.UNKNOWN, true);
      }

      int len = getChildCount();

      if (showvlines) {
        int   sx = getScrollX();
        Paint p  = dividerPaint;
        View  fv = null;

        for (int i = 0; i < len; i++) {
          View v = getChildAt(i);

          if (v.getVisibility() == View.GONE) {
            continue;
          }

          if (fv == null) {
            fv = v;

            continue;
          }

          int x = v.getLeft();

          if (x >= sx) {
            canvas.drawLine(x, 0, x, bottom, p);
          }
        }

        if (tableType != null) {
          switch(tableType) {
            case MAIN :
              canvas.drawLine(right - dividerPaint.getStrokeWidth(), 0, right - dividerPaint.getStrokeWidth(), bottom,
                              p);
              canvas.drawLine(left, 0, left, bottom, p);

              break;

            default :
              break;
          }
        }
      }

      if (showHorizontalLines &&!hasContainer) {
        bottom -= dividerPaint.getStrokeWidth();
        canvas.drawLine(left, bottom, right, bottom, dividerPaint);
      }

      graphics.clear();
    }

    public void prepareForReuse(ListViewEx lv, int position, boolean isSelected) {
    }

    public void render(View parent, iPlatformComponent list, iPlatformItemRenderer lr, int row,
                       RenderableDataItem rowItem,boolean isSelected, boolean isChecked) {
      iPlatformRenderingComponent rc;
      TableHeader        h    = header;

      this.removeAllViews();
      position  = row;
      selected  = isSelected;
      indicator = null;
      hilight   = false;

      CharSequence text;
      final int    len =h.getColumnCount();
      if(myColumnMovedTime!=columnMovedTime) {
        renderers=null;
        myColumnMovedTime=columnMovedTime;
      }
      if ((renderers == null) || (renderers.length < len)) {
        iPlatformRenderingComponent a[] = new iPlatformRenderingComponent[len];

        if (renderers != null) {
          System.arraycopy(renderers, 0, a, 0, renderers.length);
        }

        renderers = a;
      }

      indent = 0;

      if (parent instanceof TreeViewEx) {
        final TreeViewEx tv = (TreeViewEx) parent;
        iTreeItem        ti = tv.getTreeItem(rowItem);

        if (ti == null) {
          Platform.debugLog("TreeHandler disposed before table model");

          return;
        }

        if (showRootHandles &&!ti.isLeaf()) {
          if (ti.isExpanded()) {
            indicator = tv.getExpandedIcon();
          } else {
            indicator = tv.getCollapsedIcon();
          }

          indent = (ti.getDepth() - 1) * tv.getIndentBy();

          if (indent < 0) {
            indent = 0;
          }
        } else {
          indicator = null;
        }
      }

      Column             c;
      View               v;
      int                span;
      RenderableDataItem item;
      TableRowParams     lp        = null;
      final iWidget      w         = list.getWidget();
      
      boolean            canselcol = h.isColumnSelectionAllowed();

      for (int i = 0; i < len; i++) {
        c    = h.getColumnForViewAt(i);
        item = rowItem.getItemEx(i);

        if (item == null) {
          item = NULL_ITEM;
        } else {
          if (!item.isConverted()) {
            final iDataConverter cvt = c.getDataConverter();

            if (cvt != null) {
              item.convert(w, c.getType(), cvt, c.getValueContext());
            }
          }
        }

        span = item.getColumnSpan();

        iPlatformComponent comp = item.getRenderingComponent();

        if (comp == null) {
          rc = renderers[i];

          if (rc == null) {
            rc = c.getCellRenderer();

            if (rc != null) {
              rc = (iPlatformRenderingComponent) rc.newCopy();
            } else {
              rc = (iPlatformRenderingComponent) defaultRenderer.newCopy();
            }

            renderers[i] = rc;
          }

          if (item.isVisible()) {
            boolean selected = isSelected;

            if (canselcol &&!h.isColumnSelected(i)) {
              selected = false;
            }

            text = lr.configureRenderingComponent(list, rc, item, row, selected, false, c, rowItem);
            v    = (View) rc.getComponent(text, item).getView();
          } else {
            rc.clearRenderer();
            v = (View) rc.getComponent("", item).getView();
          }
        } else {
          comp.removeFromParent();
          v = (View) comp.getView();
        }

        Object o = v.getLayoutParams();

        if (!(o instanceof TableRowParams)) {
          lp = new TableRowParams(i, span);
        } else {
          lp = (TableRowParams) o;
          lp.reset(i, span);
        }

        if (span != 1) {
          if (span < 1) {
            span = len;
          }

          lp = null;

          try {
            lp = (TableRowParams) item.getModelData();
          } catch(Exception e) {
            Platform.debugLog("Model data on an item bing used from non-layout");
          }

          if (lp == null) {
            lp = new TableRowParams(i, span);
            item.setModelData(lp);
          } else {
            lp.columnSpan = span;
          }
        }

        if (i == 0) {
          int pad = ScreenUtils.PLATFORM_PIXELS_2 + indent;

          if (indicator != null) {
            pad += indicatorWidth;
          }

          if (selectionType == SelectionType.CHECKED_LEFT) {
            pad += checkboxWidth;
          }

          v.setPadding(pad, v.getPaddingTop(), v.getPaddingRight(), v.getPaddingBottom());
        }

        i += span - 1;
        this.addView(v,lp);
        v.setVisibility(c.isVisible()
                        ? VISIBLE
                        : GONE);
        v.getPaddingLeft();    //force the padding to resolve otherwise we can get a loop if we call get padding during drawing
      }
    }

    public int getColumnAtX(float x) {
      int len = getChildCount();

      for (int i = 0; i < len; i++) {
        View v = getChildAt(i);

        if (v.getVisibility() == View.GONE) {
          continue;
        }

        if ((x > v.getLeft()) && (x <= v.getRight())) {
          ViewGroup.LayoutParams lp = v.getLayoutParams();

          if (lp instanceof TableRowParams) {
            return ((TableRowParams) lp).columnIndex;
          }

          break;
        }
      }

      return -1;
    }

    public iPlatformIcon getIndicator() {
      return indicator;
    }

    public int getPreferredWidth(iPlatformComponent list, iPlatformItemRenderer lr, iPlatformRenderingComponent rc,
                                 int row, RenderableDataItem item, RenderableDataItem rowItem, Column c) {
      View v;

      if (item == null) {
        item = NULL_ITEM;
      }

      iPlatformComponent comp = item.getRenderingComponent();

      if (comp == null) {
        CharSequence text = lr.configureRenderingComponent(list, rc, item, row, false, false, c, rowItem);

        v = (View) rc.getComponent(text, item).getView();
      } else {
        v = (View) comp.getView();
      }

      v.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

      return v.getMeasuredWidth();
    }

    public int getSuggestedMinimumHeight() {
      if (minRowHeight > 0) {
        return minRowHeight;
      }

      return rowHeight;
    }

    public int getViewIndexAtX(float x) {
      int len = getChildCount();

      for (int i = 0; i < len; i++) {
        View v = getChildAt(i);

        if (v.getVisibility() == View.GONE) {
          continue;
        }

        if ((x > v.getLeft()) && (x <= v.getRight())) {
          return i;
        }
      }

      return -1;
    }

    protected boolean paintCanvas(Canvas canvas) {
      int         top          = getScrollY();
      int         left         = getScrollX();
      int         right        = getWidth();
      int         bottom       = getHeight();
      boolean     hasContainer = deletingAllowed || draggingAllowed;
      boolean     showvlines   = showVerticalLines &&!hasContainer;
      boolean     pressed      = isPressed();
      PaintBucket selectionPaint;

      if (hilight) {
        selectionPaint = itemRenderer.getAutoHilightPaint();
        pressed        = false;
      } else {
        selectionPaint = (selected && selectable)
                         ? itemRenderer.getSelectionPaintForExternalPainter(false)
                         : null;
      }

      if (pressed || (selectionPaint != null)) {
        int sx = 0,
            ex = 0;

        if (pressed && columnSelectionAllowed) {
          TableComponent tc = (TableComponent) listComponent;
          UIPoint        p  = tc.getPressedPoint();
          int            n  = getViewIndexAtX((int) p.x);

          if (n != -1) {
            View               v    = getChildAt(n);
            int                col  = tc.convertViewIndexToModel(n);
            RenderableDataItem item = (col > -1)
                                      ? getItemEx(position, col)
                                      : null;

            if ((item != null) && item.isSelectable()) {
              sx = v.getLeft();
              ex = v.getRight();
            }
          }
        } else {
          sx = getSelectionPaintStartX(0);
          ex = getSelectionPaintEndX(right);
        }

        if (ex != sx) {
          if (pressed) {
            PaintBucket pb = itemRenderer.getPressedPaint();

            if (pb != null) {
              if (selectionPaint != null) {
                showvlines = false;
              }

              pb.getCachedComponentPainter().paint(this, canvas, sx, 0, ex - sx, bottom, iPainter.UNKNOWN);
            }
          } else if (!hasContainer && (selectionPaint != null)) {
            showvlines = false;
            selectionPaint.getCachedComponentPainter().paint(this, canvas, sx, 0, ex - sx, bottom, iPainter.UNKNOWN);
          }
        }
      }

      if (indicator != null) {
        top  = (getHeight() - indicatorHeight) / 2;
        left = 2 + indent;
        indicator.paint(canvas, left, top, indicatorWidth, indicatorHeight);
      }

      return showvlines;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
      int                    left   = getPaddingLeft();
      int                    top    = getPaddingTop();
      int                    bottom = b - t - getPaddingBottom();
      int                    right  = r - l - getPaddingRight();
      int                    len    = getChildCount();
      View                   v;
      ViewGroup.LayoutParams lp;
      int                    d  = 0;    // showVerticalLines ? ScreenUtils.PLATFORM_PIXELS_1 : 0;
      View                   lv = null;
      int                    w, span, col;
      Column                 c;
      TableHeader        h    = header;
      Column[] columns=h.getColumns();
      int[] viewPositions=h.viewPositions;

      for (int i = 0; i < len; i++) {
        v = getChildAt(i);

        if (v.getVisibility() == View.GONE) {
          continue;
        }

        lp = v.getLayoutParams();

        if (!(lp instanceof TableRowParams)) {
          continue;
        }

        col  = ((TableRowParams) lp).columnIndex;
        c    = columns[col];
        span = ((TableRowParams) lp).columnSpan;

        if (span == 1) {
          w = c.getWidth();
        } else {
          w = TableHelper.getSpanWidth(col, span, columns,viewPositions);
        }

        int mw = v.getMeasuredWidth();
        int mh = v.getMeasuredHeight();

        if ((mw != w) || (mh != bottom)) {
          measureExactly(v, w, bottom);
        }

        w += d;
        r = left + w;

        if (r > right) {
          r = right;
        }

        v.layout(left, top, r - d, bottom);
        left = r;
        lv   = v;
      }

      if ((lv != null) && (lv.getRight() != right)) {
        lv.layout(lv.getLeft(), top, right, bottom);
      }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      int     width   = MeasureSpec.getSize(widthMeasureSpec);
      int     height  = MeasureSpec.getSize(heightMeasureSpec);
      boolean excatly = false;
      int     d       = showVerticalLines
                        ? ScreenUtils.PLATFORM_PIXELS_1
                        : 0;

      if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
        excatly = true;

        if (!isAutoSizeRows()) {
          height = rowHeight;
        } else if (header != null) {
          height = header.getRowHeight(position, itemRenderer, rowHeight);
        }
      }

      int                    childHeight = height;
      int                    childHMS    = MeasureSpec.makeMeasureSpec(childHeight, (childHeight < 1)
              ? MeasureSpec.UNSPECIFIED
              : MeasureSpec.EXACTLY);
      int                    len         = getChildCount();
      View                   v;
      ViewGroup.LayoutParams lp;
      float                  maxWidth  = 0;
      float                  maxHeight = 0;
      int                    w, span, col;
      Column                 c;
      int                    x = 0;
      TableHeader        h    = header;
      Column[] columns=h.getColumns();
      int[] viewPositions=h.viewPositions;

      for (int i = 0; i < len; i++) {
        v = getChildAt(i);

        if (v.getVisibility() == View.GONE) {
          continue;
        }

        lp = v.getLayoutParams();

        if (!(lp instanceof TableRowParams)) {
          continue;
        }

        col  = ((TableRowParams) lp).columnIndex;
        c    = columns[col];
        span = ((TableRowParams) lp).columnSpan;

        if (span == 1) {
          w = c.calculatePreferedWidth(listComponent, width);
        } else {
          w = TableHelper.getPreferredSpanWidth(listComponent, width, col, span, columns,viewPositions);
        }

        if ((width > 0) && (x + w > width)) {
          w = width - x - d;
        }

        x += w + d;
        v.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY), childHMS);
        measureSize.height = v.getMeasuredHeight();
        maxWidth           += w + d;
        maxHeight          = Math.max(maxHeight, measureSize.height);
      }

      if (!isAutoSizeRows()) {
        maxHeight = rowHeight;
      }

      maxHeight = Math.max(maxHeight, height);
      maxHeight += getPaddingBottom() + getPaddingTop();

      if (maxWidth > d) {
        maxWidth -= d;
      }

      measureSize.width  = maxWidth;
      measureSize.height = maxHeight;
      setMeasuredDimension(resolveSize(measureSize.intWidth(), widthMeasureSpec),
                           resolveSize(measureSize.intHeight(), heightMeasureSpec));

      if (excatly) {
        childHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        for (int i = 0; i < len; i++) {
          v = getChildAt(i);

          if (v.getMeasuredHeight() != childHeight) {
            measureExactly(v, v.getMeasuredWidth(), childHeight);
          }
        }
      }
    }

    protected ListViewEx getListView() {
      ViewParent vp = getParent();

      while(vp != null) {
        if (vp instanceof ListViewEx) {
          return (ListViewEx) vp;
        }

        vp = vp.getParent();
      }

      return null;
    }

    @Override
    public void setHilight(boolean hilight) {
      this.hilight = hilight;
      invalidate();
    }
  }


  static class TableRowParams extends ViewGroup.LayoutParams {
    public int columnIndex;
    public int columnSpan;

    public TableRowParams(int index, int span) {
      super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      columnIndex = index;
      columnSpan  = span;
    }

    public void reset(int index, int span) {
      columnIndex = index;
      columnSpan  = span;
    }
  }
}
