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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.iListHandler.SelectionMode;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iListView;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.table.TableHelper;
import com.appnativa.rare.ui.tree.iTreeItem;
import com.appnativa.rare.viewer.aListViewer;
import com.appnativa.util.IntList;

import com.google.j2objc.annotations.Weak;
import com.google.j2objc.annotations.WeakOuter;

import java.beans.PropertyChangeEvent;

import java.util.List;

public abstract class aTableBasedView extends ParentView implements iActionListener, iDataModelListener, iListView {
  protected static final int       ICON_GAP                     = ScreenUtils.PLATFORM_PIXELS_4;
  protected static final int       PAD_SIZE                     = 2;
  protected static final int       SELECTION_ICON_SIZE          = ScreenUtils.platformPixels(24);
  protected static int             INDICATOR_SLOP               = ScreenUtils.platformPixels(Platform.isTouchDevice()
          ? 24
          : 5);
  protected int[]                  EMPTY_ARRAY                  = new int[0];
  protected int                    editingRow                   = -1;
  protected int                    lastEditedRow                = -1;
  protected boolean                selectable                   = true;
  protected boolean                fixedRowSize                 = true;
  protected boolean                editable                     = false;
  protected boolean                showLastDivider              = true;
  protected boolean                extendBackgroundRendering    = true;
  protected boolean                allowsSelectionDuringEditing = false;
  protected iActionListener        actionListener;
  protected UIColor                alternatingColor;
  protected boolean                alternatingColumns;
  protected boolean                autoEndEditing;
  protected boolean                centerEditingComponentVertically;
  protected iItemChangeListener    changeListener;
  protected int                    checkboxHeight;
  protected int                    checkboxWidth;
  protected boolean                deletingAllowed;
  protected UIColor                dividerLineColor;
  protected UIStroke               dividerStroke;
  protected boolean                draggingAllowed;
  protected EditingMode            editingMode;
  protected boolean                editingSelectionAllowed;
  protected boolean                editingSwipingAllowed;
  protected int                    effectiveMinRowHeight;
  protected int                    indicatorHeight;
  protected int                    indicatorWidth;
  protected ListItemRenderer       itemRenderer;
  protected int                    leftOffset;
  @Weak()
  protected iPlatformListDataModel listModel;
  protected PaintBucket            lostFoucsSelectionPainter;
  protected int                    minRowHeight;
  protected int                    minVisibleRows;
  protected int                    popupMenuIndex;
  protected PaintBucket            pressedPainter;
  protected int                    rightOffset;
  protected int                    rowHeight;
  protected PaintBucket            selectionPainter;
  protected SelectionType          selectionType;
  protected boolean                showDivider;
  protected boolean                singleClickAction;
  protected int                    visibleRows;
  protected boolean                keepSelectionVisible;

  protected aTableBasedView(Object nsview) {
    super(nsview);
    selectionPainter          = Platform.getAppContext().getSelectionPainter();
    lostFoucsSelectionPainter = Platform.getAppContext().getLostFocusSelectionPainter();

    if (!Platform.getAppContext().isPlatformColorTheme()) {
      setBackgroundColor(ColorUtils.getListBackground());
      setForegroundColor(ColorUtils.getListForeground());
      pressedPainter = Platform.getUIDefaults().getPaintBucket("Rare.List.pressedPainter");
    }
  }

  @Override
  public void setBounds(float x, float y, float w, float h) {
    super.setBounds(x, y, w, h);

    if (keepSelectionVisible) {
      makeSelectionVisible();
    }
  }

  @Override
  public boolean isKeepSelectionVisible() {
    return keepSelectionVisible;
  }

  @Override
  public void setKeepSelectionVisible(boolean keepSelectionVisible) {
    this.keepSelectionVisible = keepSelectionVisible;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (actionListener != null) {
      actionListener.actionPerformed(e);
    }
  }

  @Override
  public abstract void addSelectionIndex(int index);

  @Override
  public void clearPopupMenuIndex() {
    popupMenuIndex = -1;
  }

  @Override
  public void contentsChanged(Object source) {
    if (!fixedRowSize) {
      resetHeightInfo(0, listModel.size() - 1);
    }

    lastEditedRow = -1;
    refreshItems();
  }

  @Override
  public void contentsChanged(Object source, int index0, int index1) {
    if (!fixedRowSize) {
      resetHeightInfo(index0, index1);
    }

    rowsChanged(index0, index1);
  }

  public abstract void editCell(int row, int col);

  @Override
  public void intervalAdded(Object source, int index0, int index1) {
    if (!fixedRowSize) {
      resetHeightInfo(index0, index1);
    }

    lastEditedRow = -1;
    refreshItems();
  }

  @Override
  public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
    lastEditedRow = -1;
    refreshItems();
  }

  public void paintRow(RowView view, AppleGraphics g, RenderableDataItem item, UIRectangle rect, iTreeItem ti) {
    view.paint(g, rect);
  }

  @Override
  public void propertyChange(PropertyChangeEvent e) {
    super.propertyChange(e);

    if (border != null) {
      UIInsets in = border.getBorderInsets(null);

      leftOffset  = in.intLeft();
      rightOffset = in.intRight();
    } else {
      leftOffset  = 0;
      rightOffset = 0;
    }
  }

  public abstract void refreshItems();

  public abstract void repaintRow(int index);

  public void repaintRow(RenderableDataItem item) {
    int n = listModel.indexOf(item);

    if (n != -1) {
      repaintRow(n);
    }
  }

  public abstract int rowAtPoint(float x, float y);

  public abstract void rowChanged(int index);

  public void rowChanged(RenderableDataItem item) {
    int n = listModel.indexOf(item);

    if (n != -1) {
      rowChanged(n);
    }
  }

  public abstract void rowsChanged(int firstRow, int lastRow);

  public void rowsDeleted(int firstRow, int lastRow) {
    lastEditedRow = -1;
    refreshItems();
  }

  public void rowsInserted(int firstRow, int lastRow) {
    lastEditedRow = -1;
    refreshItems();
  }

  @Override
  public void structureChanged(Object source) {
    if (!fixedRowSize) {
      resetHeightInfo(0, listModel.size() - 1);
    }

    lastEditedRow = -1;
    refreshItems();
  }

  @Override
  public void setActionListener(iActionListener actionListener) {
    this.actionListener = actionListener;
  }

  public void setAllowsSelectionDuringEditing(boolean allowsSelectionDuringEditing) {
    this.allowsSelectionDuringEditing = allowsSelectionDuringEditing;
  }

  public void setAlternatingColor(UIColor alternatingColor) {
    this.alternatingColor = alternatingColor;
  }

  public void setAlternatingColumns(boolean alternatingColumns) {
    this.alternatingColumns = alternatingColumns;
  }

  @Override
  public void setAlternatingRowColor(UIColor alternatingColor) {
    this.alternatingColor = alternatingColor;
  }

  public void setAutoEndEditing(boolean autoEndEditing) {
    this.autoEndEditing = autoEndEditing;
  }

  @Override
  public void setAutoHilight(boolean autoHilight) {}

  @Override
  public void makeSelectionVisible() {
    int index = getSelectedIndex();

    if (index != -1) {
      scrollRowToVisible(index);
    }
  }

  @Override
  public void setDividerLine(UIColor color, UIStroke stroke) {
    dividerLineColor = color;
    dividerStroke    = stroke;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  @Override
  public void setEditingMode(EditingMode mode) {
    if (mode == null) {
      mode = EditingMode.NONE;
    }

    aListViewer lv = (aListViewer) Component.fromView(this).getWidget();

    editingMode     = mode;
    draggingAllowed = (mode == EditingMode.REORDERING) || (mode == EditingMode.REORDERING_AND_SELECTION)
                      || (mode == EditingMode.REORDERING_AND_DELETEING);
    editingSelectionAllowed = (mode == EditingMode.SELECTION) || (mode == EditingMode.REORDERING_AND_SELECTION);
    deletingAllowed         = lv.canDelete();
  }

  public void setEditingSwipingAllowed(boolean editingSwipingAllowed) {
    this.editingSwipingAllowed = editingSwipingAllowed;
  }

  public void setExtendBackgroundRendering(boolean extendBackgroundRendering) {
    this.extendBackgroundRendering = extendBackgroundRendering;
  }

  public void setFlingThreshold(int i) {}

  public void setItemRenderer(ListItemRenderer lr) {
    itemRenderer = lr;
  }

  public void setListModel(iPlatformListDataModel listModel) {
    this.listModel = listModel;
    lastEditedRow  = -1;
  }

  public void setMinRowHeight(int min) {
    minRowHeight = min;

    if (min > getRowHeight()) {
      setRowHeight(min);
    } else if (rowHeight == 0) {
      effectiveMinRowHeight = min;
    }
  }

  @Override
  public void setMinimumVisibleRowCount(int rows) {
    minVisibleRows = rows;
  }

  @Override
  public void setRowHeight(int height) {
    if (height < minRowHeight) {
      height = minRowHeight;
    }

    this.rowHeight = height;
    setRowHeightEx(height);
    effectiveMinRowHeight = height;
  }

  public void setSelectFlinged(boolean b) {}

  @Override
  public abstract void setSelectable(boolean selectable);

  public abstract void setSelectedIndex(int index);

  public abstract void setSelectedIndexes(int[] indices);

  public void setSelectedItem(RenderableDataItem value) {
    if (listModel != null) {
      int n = RenderableDataItem.findValueEx(listModel, value);

      if (n != -1) {
        setSelectedIndex(n);
      }
    }
  }

  @Override
  public void setSelectionChangeListener(iItemChangeListener changeListener) {
    this.changeListener = changeListener;
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    switch(selectionMode) {
      case NONE :
        setMultipleSelection(false);
        setSelectable(false);

        break;

      case INVISIBLE :
        setMultipleSelection(false);
        selectable = true;

        break;

      case MULTIPLE :
        setMultipleSelection(true);

        break;

      default :
        setMultipleSelection(false);

        break;
    }
  }

  public void setSelectionType(SelectionType type) {
    this.selectionType = type;
  }

  @Override
  public void setShowDivider(boolean show) {
    showDivider = show;
  }

  public void setShowLastDivider(boolean show) {
    showLastDivider = show;
  }

  @Override
  public void setVisibleRowCount(int rows) {
    visibleRows = rows;
  }

  public void setWholeViewFling(boolean b) {}

  public iActionListener getActionListener() {
    return actionListener;
  }

  public UIColor getAlternatingColor() {
    return alternatingColor;
  }

  @Override
  public UIColor getAlternatingRowColor() {
    return alternatingColumns
           ? null
           : alternatingColor;
  }

  public iItemChangeListener getChangeListener() {
    return changeListener;
  }

  public int getEditingRow() {
    return editingRow;
  }

  @Override
  public int getHilightedIndex() {
    return -1;
  }

  public int getLastEditedRow() {
    return lastEditedRow;
  }

  @Override
  public iPlatformComponent getListComponent() {
    return getComponent();
  }

  public int getMinRowHeight() {
    return minRowHeight;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    int h  = TableHelper.getMinimumListHeight(Component.findFromView(this), minVisibleRows, rowHeight);
    int ch = ScreenUtils.toPlatformPixels(1, component, true);

    size.width  = ch * 3;
    size.height = h;
  }

  @Override
  public int getPopupMenuIndex() {
    return popupMenuIndex;
  }

  @Override
  public float getPreferredHeight(int row) {
    return getRowHeight();
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {
    int h = TableHelper.getPreferredListHeight(Component.findFromView(this), Math.max(minVisibleRows, visibleRows),
              rowHeight, getRowCount());
    int ch = ScreenUtils.toPlatformPixels(1, component, true);
    int w  = ch * 5;

    size.width  = Math.max(w, size.width);
    size.height = Math.max(h, size.height);
  }

  public int getRowCount() {
    return (listModel != null)
           ? listModel.size()
           : 0;
  }

  public abstract int getRowHeight();

  @Override
  public abstract int getSelectedIndex();

  public SelectionType getSelectionType() {
    return selectionType;
  }

  public int getVisibleRowCount() {
    return visibleRows;
  }

  public boolean isAllowsSelectionDuringEditing() {
    return allowsSelectionDuringEditing;
  }

  public boolean isAlternatingColumns() {
    return alternatingColumns;
  }

  public boolean isAutoEndEditing() {
    return autoEndEditing;
  }

  @Override
  public boolean isAutoHilight() {
    return false;
  }

  public boolean isEditingSwipingAllowed() {
    return editingSwipingAllowed;
  }

  public boolean isExtendBackgroundRendering() {
    return extendBackgroundRendering;
  }

  public abstract boolean isMultipleSelectionAllowed();

  public abstract boolean isRowSelected(int row);

  @Override
  public boolean isRowSizeFixed() {
    return fixedRowSize;
  }

  @Override
  public boolean isSelectable() {
    return selectable;
  }

  protected boolean checkForCellHotspot(int row, float x, float y, float width, float height) {
    return false;
  }

  @Override
  protected void disposeEx() {
    changeListener = null;
    actionListener = null;
    listModel      = null;
    super.disposeEx();
  }

  /**
   * Call back for views that want want every click event
   *
   * @param e
   *          the mouse event. The event is only valid for the duration of the
   *          call and is not to be saved
   * @param mouseDown
   *          true if this is a mouse down event; false for a mouse up
   */
  protected void mouseClicked(MouseEvent e, boolean mouseDown, float width, float height) {
    pressed = mouseDown;
  }

  protected void resetHeightInfo(int index0, int index1) {
    index1++;

    iPlatformListDataModel list = listModel;
    int                    len  = list.size();

    if (index1 > len) {
      index1 = len;
    }

    for (int i = index0; i < index1; i++) {
      list.get(i).setHeight(0);
    }
  }

  protected void selectionChanged(int oldIndex, int newIndex) {
    if (changeListener != null) {
      ItemChangeEvent e = new ItemChangeEvent(this, oldIndex, newIndex);

      changeListener.itemChanged(e);
    }
  }

  protected void selectionChanged(IntList oldIndexes, IntList newIndexes) {
    if (changeListener != null) {
      ItemChangeEvent e = new ItemChangeEvent(this, oldIndexes, newIndexes);

      changeListener.itemChanged(e);
    }
  }

  protected abstract void setupNewRenderingCell(Object nativeView);

  protected abstract void setMultipleSelection(boolean multiple);

  protected abstract void setRowHeightEx(int height);

  protected PaintBucket getPressedPainter() {
    return itemRenderer.getPressedPaint();
  }

  protected float getSelectionPaintEndX(float currentEndX) {
    return currentEndX;
  }

  protected float getSelectionPaintStartX(float currentStartX) {
    return currentStartX;
  }

  protected PaintBucket getSelectionPainter() {
    if (!isEditing()) {
      return itemRenderer.getSelectionPaintForExternalPainter(false);
    }

    return null;
  }

  protected RowView getViewForRow(int index) {
    return null;
  }

  protected boolean isSelectable(int row, RenderableDataItem item, iTreeItem ti) {
    if (!selectable || (row < 0)) {
      return false;
    }

    if (item == null) {
      item = listModel.get(row);
    }

    return item.isSelectable();
  }

  protected boolean isSelectable(int row, int col, RenderableDataItem item) {
    if (!selectable || (row < 0)) {
      return false;
    }

    if (item == null) {
      item = listModel.get(row);
    }

    if (!item.isSelectable()) {
      return false;
    }

    if (col == -1) {
      return true;
    }

    item = item.getItemEx(col);

    return (item != null) && item.isSelectable();
  }

  @WeakOuter
  public class RowView extends ParentView {
    public int           indent = 0;
    public iPlatformIcon checkboxIcon;
    public int           column;
    public iPlatformIcon indicator;
    public int           row;
    boolean              checkedInEditMode;
    private boolean      editing;

    public RowView(Object proxy) {
      super(proxy);
    }

    public void hideRowEditingComponent(boolean animate) {}

    public void paint(AppleGraphics g, UIRectangle rect) {
      paintBackground(g, this, rect);
      paintOverlay(g, this, rect);

      float left = rect.x + indent + PAD_SIZE + leftOffset;

      if (indicator != null) {
        final float top = (rect.height - indicatorHeight) / 2;

        indicator.paint(g, left, top, indicatorWidth, indicatorHeight);
      }

      if (checkboxIcon != null) {
        final float top = (rect.height - checkboxHeight) / 2;

        if (selectionType == SelectionType.CHECKED_RIGHT) {
          left = rect.width - PAD_SIZE - checkboxWidth - rightOffset;
        } else if (indicatorWidth > 0) {
          left += ICON_GAP + indicatorWidth;
        }

        checkboxIcon.paint(g, left, top, checkboxWidth, checkboxHeight);
      }
    }

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      float height = rect.height;

      if (showDivider) {
        height--;
      }

      if (componentPainter != null) {
        componentPainter.paint(g, rect.x, rect.y, rect.width, height, iPainter.UNKNOWN);
      }

      float sx = getSelectionPaintStartX(rect.x);
      float ex = getSelectionPaintEndX(rect.x + rect.width);

      if (isPressed()) {
        PaintBucket pb = itemRenderer.getPressedPaint();

        if (pb != null) {
          UIComponentPainter.paint(g, sx, rect.y, ex - sx, height, pb);
        }
      } else if (!editing && isSelected()) {
        PaintBucket pb = itemRenderer.getSelectionPaintForExternalPainter(false);

        if (pb != null) {
          UIComponentPainter.paint(g, sx, rect.y, ex - sx, height, pb);
        }
      }
    }

    public void showRowEditingComponent(iPlatformComponent component, boolean animate) {}

    @Override
    public void setComponentPainter(iPlatformComponentPainter cp) {
      this.componentPainter = cp;
    }

    public void setEditing(boolean editing) {
      this.editing = editing;

      if (editing) {
        checkedInEditMode = listModel.editModeIsItemMarked(row);
      }
    }

    public void setImage(UIImage image) {}

    public void setNativeView(Object proxy) {
      setProxy(proxy);
    }

    @Override
    public void setPaintHandlerEnabled(boolean enabled) {}

    @Override
    protected void disposeEx() {
      indicator = null;
      super.disposeEx();
    }

    protected void prepareForReuse(int row, int col) {
      this.row    = row;
      this.column = col;
      clearVisualState();
    }

    @Override
    protected void setFocusListenerEnabled(boolean enabled) {}

    protected void setKeyBoardHandlerEnabled(boolean enabled) {}

    protected void setMouseHandlerEnabled(boolean enabled) {}

    protected void setMouseMotionHandlerEnabled(boolean enabled) {}
  }
}
