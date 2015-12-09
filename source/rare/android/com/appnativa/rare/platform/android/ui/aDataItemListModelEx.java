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

package com.appnativa.rare.platform.android.ui;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.CheckedTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SectionIndexer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.platform.android.ui.view.ListViewEx.iListViewRow;
import com.appnativa.rare.ui.CheckListManager;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.PainterUtils.GripperIcon;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.aDataItemListModel;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iListView.EditingMode;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.renderer.RendererContainer;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.ui.table.TableHelper;
import com.appnativa.rare.viewer.aListViewer;
import com.appnativa.rare.widget.aPlatformWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.iStringConverter;

import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aDataItemListModelEx extends aDataItemListModel
        implements iStringConverter, iPlatformListDataModel, Filterable, SectionIndexer {
  public static final int               PAD_SIZE           = 0;
  public static final int               ICON_GAP           = ScreenUtils.PLATFORM_PIXELS_4;
  protected int                         viewTypes          = 1;
  protected boolean                     showLastDivider    = true;
  protected SelectionType               selectionType      = SelectionType.ROW_ON_BOTTOM;
  protected boolean                     selectable         = true;
  protected final DataSetObservable     mDataSetObservable = new DataSetObservable();
  protected boolean                     filteringEnabled   = true;
  protected AFilter                     aFilter;
  protected UIColor                     alternatingColor;
  protected Paint                       alternatingColorPaint;
  protected boolean                     autoSizeRows;
  protected String[]                    characterSections;
  protected CheckListManager            checkListManager;
  protected int                         checkboxHeight;
  protected int                         checkboxWidth;
  protected boolean                     deletingAllowed;
  protected Paint                       dividerPaint;
  protected boolean                     draggingAllowed;
  protected UIColor                     foregroundColor;
  protected boolean                     hasCustomRenderers;
  protected int                         indent;
  protected int                         indicatorHeight;
  protected int                         indicatorWidth;
  protected iPlatformItemRenderer       itemRenderer;
  protected iPlatformComponent          listComponent;
  protected int                         minRowHeight;
  protected UIDimension                 preferedSize;
  protected iPlatformRenderingComponent renderingComponent;
  protected int                         rowHeight;
  private iPlatformIcon                 accessoryIcon;
  private GripperIcon                   draggableIcon;
  private boolean                       editingSelectionAllowed;
  private boolean                       needsRowContainer;
  private int                           rowCount;

  /** Creates a new instance of DataItemListModel */
  public aDataItemListModelEx() {
    super();
  }

  /**
   * Creates a new instance of DataItemComboBoxModel that is a copy of this one
   *
   * @param m
   *          the model
   */
  public aDataItemListModelEx(aDataItemListModelEx m) {
    super(m);
    this.selectionType      = m.selectionType;
    this.hasCustomRenderers = m.hasCustomRenderers;
    this.rowHeight          = m.rowHeight;
    this.foregroundColor    = m.foregroundColor;
    this.alternatingColor   = m.alternatingColor;
    this.selectable         = m.selectable;

    if (alternatingColor != null) {
      setAlternatingColor(alternatingColor);
    }
  }

  /**
   * Creates a new instance of DataItemListModel
   *
   * @param widget
   *          {@inheritDoc}
   * @param column
   *          {@inheritDoc}
   */
  public aDataItemListModelEx(iWidget widget, Column column) {
    super(widget, column);
  }

  public boolean areAllItemsEnabled() {
    return false;
  }

  public void clearEx() {
    super.clearEx();
    rowCount = 0;
  }

  public void dispose() {
    mDataSetObservable.notifyInvalidated();
    clearEx();
    theWidget = null;
  }

  /**
   * Notifies the attached View that the underlying data has been changed and it
   * should refresh itself.
   */
  public void notifyDataSetChanged() {
    characterSections = null;
    rowCount          = size();
    mDataSetObservable.notifyChanged();
  }

  public void refreshItems() {
    characterSections = null;
    rowCount          = size();
    mDataSetObservable.notifyChanged();
  }

  public void registerDataSetObserver(DataSetObserver observer) {
    mDataSetObservable.registerObserver(observer);
  }

  public void rowsChanged(int... index) {
    notifyDataSetChanged();
  }

  public void unregisterDataSetObserver(DataSetObserver observer) {
    mDataSetObservable.unregisterObserver(observer);
  }

  public void setAccessoryType(String type) {
    accessoryIcon     = null;
    needsRowContainer = true;
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

  public void setAutoSizeRows(boolean autoSizeRows) {
    this.autoSizeRows = autoSizeRows;
  }

  public void setCheckListManager(CheckListManager clm) {
    checkListManager = clm;
    checkboxHeight   = clm.getCheckedIcon().getIconHeight();
    checkboxWidth    = clm.getCheckedIcon().getIconWidth();
    checkboxHeight   = Math.max(checkboxHeight, clm.getUncheckedIcon().getIconHeight());
    checkboxWidth    = Math.max(checkboxWidth, clm.getUncheckedIcon().getIconWidth());
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

  @Override
  public void setRowEditingSupported(boolean supported) {
    if(supported) {
      needsRowContainer=true;
    }
  }
  
  public void setSelectable(boolean selectable) {
    this.selectable = selectable;
  }

  /**
   * @param selectionType
   *          the selectionType to set
   */
  public void setSelectionType(SelectionType type) {
    this.selectionType = type;
  }

  public void setShowLastDivider(boolean showLastDivider) {
    this.showLastDivider = showLastDivider;
  }

  public void setWidget(iWidget widget) {
    super.setWidget(widget);
  }

  @Override
  public UIColor getAlternatingColor() {
    return alternatingColor;
  }

  public Paint getAlternatingColorPaint() {
    return alternatingColorPaint;
  }

  public int getCount() {
    return rowCount;
  }

  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    return getView(position, convertView, parent);
  }

  public Filter getFilter() {
    if (aFilter == null) {
      aFilter = new AFilter();
    }

    return aFilter;
  }

  /**
   * @return the foregroundColor
   */
  public UIColor getForegroundColor() {
    return foregroundColor;
  }

  public Object getItem(int position) {
    return get(position);
  }

  public long getItemId(int position) {
    return position;
  }

  /**
   * @return the itemRenderer
   */
  public iPlatformItemRenderer getItemRenderer() {
    return itemRenderer;
  }

  public int getItemViewType(int position) {
    if (hasCustomRenderers) {
      final RenderableDataItem item = get(position);

      if ((item != null) && (item.getRenderingComponent() != null)) {
        return 1;
      }
    }

    return 0;
  }

  /**
   * @return the minRowHeight
   */
  public int getMinRowHeight() {
    return minRowHeight;
  }

  public int getPositionForSection(int section) {
    String a[] = (String[]) getSections();

    if (section > a.length) {
      section = a.length - 1;
    }

    String s = a[section];

    return characterIndex.getPosition(s.charAt(0));
  }

  public void getPreferredSize(UIDimension size, float maxWidth) {
    final iPlatformComponent c = (theWidget == null)
                                 ? null
                                 : theWidget.getDataComponent();

    TableHelper.calculateListSize(this, c, getItemRenderer(), null, size, -1, (int) Math.ceil(maxWidth),
                                  (minRowHeight == 0)
                                  ? rowHeight
                                  : minRowHeight);
  }

  public UIDimension getPreferredSize(int row, UIDimension d) {
    if (d == null) {
      d = new UIDimension();
    }

    final iPlatformComponent c = (theWidget == null)
                                 ? null
                                 : theWidget.getDataComponent();

    if (renderingComponent == null) {
      Context a = (theWidget == null)
                  ? AppContext.getAndroidContext()
                  : ((aPlatformWidget) theWidget).getAndroidContext();

      renderingComponent = createRenderingComponent(a, (c == null)
              ? null
              : c.getForeground());
    }

    iPlatformRenderingComponent rc = renderingComponent;
    iPlatformComponent          list;
    RenderableDataItem          item = get(row);
    View                        v;

    if (item != null) {
      list = item.getRenderingComponent();

      if (list instanceof iPlatformRenderingComponent) {
        rc = (iPlatformRenderingComponent) list;
      } else if (list != null) {
        v = (View) list.getView();
        v.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        d.width  = v.getMeasuredWidth();
        d.height = v.getMeasuredHeight();

        return d;
      }
    }

    CharSequence text = itemRenderer.configureRenderingComponent(c, rc, item, row, false, false, columnDescription,
                          null);

    v = (View) rc.getComponent(text, item).getView();
    v.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
    d.width  = v.getMeasuredWidth();
    d.height = v.getMeasuredHeight();

    return d;
  }

  /**
   * @return the rowHeight
   */
  public int getRowHeight() {
    return rowHeight;
  }

  public int getSectionForPosition(int position) {
    RenderableDataItem di = get(position);
    String             s  = di.toString();

    if ((s == null) || (s.length() == 0)) {
      return 0;
    }

    int n = characterIndex.getPosition(s.charAt(0));

    return (n == -1)
           ? 0
           : n;
  }

  public Object[] getSections() {
    if (characterSections == null) {
      if (characterIndex == null) {
        rebuilItemIndex();
      }

      characterSections = characterIndex.getCharactersAsStrings();
    }

    return characterSections;
  }

  /**
   * @return the selectionType
   */
  public SelectionType getSelectionType() {
    return selectionType;
  }

  public int getSize() {
    return this.size();
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    iPlatformRenderingComponent rc = null;
    iPlatformComponent          list;
    RenderableDataItem          item = get(position);

    if (item != null) {
      list = item.getRenderingComponent();

      if (list instanceof iPlatformRenderingComponent) {
        rc = (iPlatformRenderingComponent) list;
      } else if (list != null) {
        return (View) list.getView();
      }
    }

    if (listComponent == null) {
      initializeListComponent(parent);
    }

    list = listComponent;

    if (list == null) {
      list          = new Container(parent);
      listComponent = list;
      list.setWidget(getWidget());
    }

    if (renderingComponent == null) {
      renderingComponent = createRenderingComponent(parent.getContext(), list.getForeground());
    }

    final boolean isSelected = (!editing && ((parent instanceof ListView) && (position < rowCount)))
                               ? ((ListView) parent).isItemChecked(position)
                               : false;

    if (convertView != null) {
      iPlatformComponent c = Component.fromView(convertView);

      if (c instanceof iPlatformRenderingComponent) {
        rc = (iPlatformRenderingComponent) c;
      }
    }

    if (rc == null) {
      rc = createRenderingComponent(parent.getContext(), (foregroundColor == null)
              ? list.getForeground()
              : foregroundColor);
    }

    View v = rc.getComponent().getView();

    if (v instanceof ListRow) {
      ((ListRow) v).prepareForReuse(parent, position, isSelected);
      ((ListRow) v).fixedHeight = item.getHeight();
    } else if (v instanceof ListRowContainer) {
      ((ListRowContainer) v).prepareForReuse(parent, position, isSelected);
    } else if (v instanceof CheckedTextView) {
      ((CheckedTextView) v).setChecked(isSelected);
    } else {
      needsRowContainer = true;
    }

    CharSequence text = itemRenderer.configureRenderingComponent(list, rc, item, position, isSelected, false,
                          columnDescription, null);

    rc.getComponent(text, item);

    if (needsRowContainer) {
      ListRowContainer lc;

      if (!(v instanceof ListRowContainer)) {
        RendererContainer rcc = new RendererContainer(rc);

        v  = rcc.getComponent().getView();
        lc = (ListRowContainer) v;
        lc.hilightPainter=itemRenderer.getAutoHilightPaint().getCachedComponentPainter();
        PaintBucket pb = itemRenderer.getPressedPaint();

        if (pb != null) {
          lc.pressedPainter = pb.getCachedComponentPainter();
        }

        pb = itemRenderer.getSelectionPaint();

        if (pb != null) {
          lc.selectedPainter = pb.getCachedComponentPainter();
        }

        lc.dividerPaint          = dividerPaint;
        lc.alternatingColorPaint = alternatingColorPaint;
        lc.prepareForReuse(parent, position, isSelected);
        lc.showSelections = editingSelectionAllowed;
      } else {
        lc = (ListRowContainer) v;
      }

      if (accessoryIcon != null) {
        lc.accessoryIcon = accessoryIcon;
      }

      lc.selected = isSelected;
      lc.setDraggableIcon(item.isDraggingAllowed()
                          ? draggableIcon
                          : null);
      lc.setInListEditingMode(editing);

      if (draggingAllowed && (parent instanceof ListViewEx) && ((ListViewEx) parent).getReorderingRow() == position) {
        lc.setVisibility(View.INVISIBLE);
      }
    }

    return v;
  }

  public int getViewTypeCount() {
    return hasCustomRenderers
           ? 2
           : 1;
  }

  public boolean hasStableIds() {
    return false;
  }

  public boolean isAutoSizeRows() {
    return autoSizeRows;
  }

  public boolean isEnabled(int position) {
    return selectable && get(position).isEnabled();
  }

  /**
   * @return the filteringEnabled
   */
  public boolean isFilteringEnabled() {
    return filteringEnabled;
  }

  /**
   * @return the selectable
   */
  public boolean isSelectable() {
    return selectable;
  }

  public boolean isShowLastDivider() {
    return showLastDivider;
  }

  protected ListRow createListRow(Context context) {
    return new ListRow(context);
  }

  protected iPlatformRenderingComponent createRenderingComponent(Context context, UIColor fg) {
    if (columnDescription != null) {
      iPlatformRenderingComponent rc = columnDescription.getCellRenderer();

      if (rc != null) {
        return (iPlatformRenderingComponent) rc.newCopy();
      }
    }

    ListRow rc = createListRow(context);

    if (fg != null) {
      fg.setTextColor(rc);
    }

    UILabelRenderer lr = new UILabelRenderer(rc);

    if (autoSizeRows || ((columnDescription != null) && (columnDescription.wordWrap))) {
      lr.setWordWrap(true);
    }

    return lr;
  }

  protected void fireContentsChanged(Object source) {
    preferedSize = null;

    if (eventsEnabled) {
      notifyDataSetChanged();
      super.fireContentsChanged(source);
    }
  }

  protected void fireContentsChanged(Object source, int index0, int index1) {
    preferedSize = null;

    if (eventsEnabled) {
      notifyDataSetChanged();
      super.fireContentsChanged(source, index0, index1);
    }
  }

  protected void fireIntervalAdded(Object source, int index0, int index1) {
    preferedSize = null;

    if (eventsEnabled) {
      notifyDataSetChanged();
      super.fireIntervalAdded(source, index0, index1);
    }
  }

  protected void fireIntervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
    preferedSize = null;

    if (eventsEnabled) {
      notifyDataSetChanged();
      super.fireIntervalRemoved(source, index0, index1, removed);
    }
  }

  protected void initializeListComponent(View parent) {
    listComponent = Platform.findPlatformComponent(parent);

    if ((listComponent != null) && (listComponent.getView() instanceof ListViewEx)) {
      ListViewEx lv = (ListViewEx) listComponent.getView();

      dividerPaint = lv.getDividerPaint();

      if (listComponent.getWidget() instanceof aListViewer) {
        EditingMode mode = lv.getEditingMode();

        draggingAllowed         = (mode == EditingMode.REORDERING) || (mode == EditingMode.REORDERING_AND_SELECTION);
        editingSelectionAllowed = (mode == EditingMode.SELECTION) || (mode == EditingMode.REORDERING_AND_SELECTION);
        deletingAllowed         = listComponent.getWidget().canDelete();
        needsRowContainer       = deletingAllowed || draggingAllowed || editingSelectionAllowed || needsRowContainer;
        if (draggingAllowed) {
          draggableIcon = new GripperIcon(false, listComponent.getForeground());
          draggableIcon.setSize(ScreenUtils.platformPixels(24), ScreenUtils.platformPixels(24));
        }
      }
    }
  }

  public class ListRow extends LabelView implements iListViewRow{
    public int           indent;
    public iPlatformIcon indicator;
    public int           position;
    public boolean       selected;
    public int           fixedHeight;
    private boolean hilight;

    public ListRow(Context context) {
      super(context);
      setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
                                       Gravity.CENTER_VERTICAL | Gravity.LEFT));
      setBackground(NullDrawable.getInstance());
    }

    @Override
    public void draw(Canvas canvas) {
      int       right        = getWidth();
      int       bottom       = getHeight();
      final int d            = ScreenUtils.PLATFORM_PIXELS_1;
      boolean   hasContainer = draggingAllowed || deletingAllowed;
      boolean   showh        = (dividerPaint != null) &&!hasContainer;

      if (!showLastDivider && (position == rowCount - 1)) {
        if (rowHeight + getBottom() - 1 > listComponent.getHeight()) {
          showh = false;
        }
      }

      if (showh) {
        bottom -= d;
      }

      if (!hasContainer) {
        if ((alternatingColorPaint != null) && (position % 2 == 1)) {
          canvas.drawRect(0, 0, right, bottom, alternatingColorPaint);
        }

        if (isPressed()) {
          PaintBucket pb = itemRenderer.getPressedPaint();

          if (pb != null) {
            pb.getCachedComponentPainter().paint(this, canvas, 0, 0, right, bottom, iPainter.UNKNOWN);
          }
        } else {
          PaintBucket pb;
          if(hilight) {
            pb=itemRenderer.getAutoHilightPaint();
          }
          else { 
            pb= (selected && selectable)
                                       ? itemRenderer.getSelectionPaintForExternalPainter(false)
                                       : null;
          }
          if (pb != null) {
            pb.getCachedComponentPainter().paint(this, canvas, 0, 0, right, bottom, iPainter.UNKNOWN);
          }
        }
      }

      super.draw(canvas);

      int left = PAD_SIZE + indent + getPaddingLeft();

      if (indicator != null) {
        indicator.paint(canvas, left, (getHeight() - indicatorHeight) / 2, indicatorWidth, indicatorHeight);
      }

      if (checkListManager != null) {
        iPlatformIcon icon = checkListManager.getRowIcon(position);

        if (selectionType == SelectionType.CHECKED_LEFT) {
          if (indicatorWidth > 0) {
            left += indicatorWidth + ICON_GAP;
          }

          icon.paint(canvas, left, (getHeight() - checkboxHeight) / 2, checkboxWidth, checkboxHeight);
        } else {
          icon.paint(canvas, getWidth() - getPaddingRight() - checkboxWidth + ICON_GAP,
                     (getHeight() - checkboxHeight) / 2, checkboxWidth, checkboxHeight);
        }
      }

      if (showh &&!hasContainer) {
        canvas.drawRect(0, bottom, right, bottom + d, dividerPaint);
      }
    }

    public void prepareForReuse(View parent, int row, boolean selected) {
      position      = row;
      this.selected = selected;
      indent        = 0;
      indicator     = null;
      fixedHeight   = 0;
      hilight=false;
      ListViewEx lv=(ListViewEx)parent;
      int ci=lv.getContextMenuIndex();
      if(ci!=-1) {
        if(ci==row || (selected && lv.isItemChecked(ci))) {
          hilight=true;
        }
      }
    }

    @Override
    public int getCompoundPaddingLeft() {
      int pad = super.getCompoundPaddingLeft() + indent + indicatorWidth + PAD_SIZE;

      if ((checkboxWidth > 0) && (selectionType == SelectionType.CHECKED_LEFT)) {
        pad += checkboxWidth;

        if (indicatorWidth > 0) {
          pad += ICON_GAP;
        }
      }

      return pad;
    }

    @Override
    public int getCompoundPaddingRight() {
      if ((checkboxWidth > 0) && (selectionType == SelectionType.CHECKED_RIGHT)) {
        return super.getCompoundPaddingRight() + checkboxWidth + PAD_SIZE;
      }

      return super.getCompoundPaddingRight();
    }

    public iPlatformIcon getIndicator() {
      return indicator;
    }

    public int getSuggestedMinimumHeight() {
      int rh = minRowHeight;

      if (rh == 0) {
        rh = rowHeight;
      }

      if (rh < checkboxHeight) {
        rh = checkboxHeight;
      }

      return (rh > 0)
             ? rh
             : super.getSuggestedMinimumHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      if (!autoSizeRows && (rowHeight > 0)) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((fixedHeight > 0)
                ? fixedHeight
                : rowHeight, MeasureSpec.EXACTLY);
      }

      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setHilight(boolean hilight) {
      this.hilight=hilight;
      invalidate();
    }
  }


  class AFilter extends Filter {
    FilterResults results = new FilterResults();

    protected FilterResults performFiltering(CharSequence cs) {
      results.count  = 0;
      results.values = null;

      if (filteringEnabled) {
        if (cs == null) {
          if (aDataItemListModelEx.this.unfilter()) {
            results.count  = size();
            results.values = getFilteredList();
          }
        } else if (aDataItemListModelEx.this.filter(cs.toString(), false)) {
          results.count  = size();
          results.values = getFilteredList();
        }
      }

      return results;
    }

    @Override
    protected void publishResults(CharSequence cs, FilterResults fr) {
      if (results.values == null) {
        notifyDataSetChanged();

        return;
      }

      notifyDataSetChanged();
    }
  }
}
