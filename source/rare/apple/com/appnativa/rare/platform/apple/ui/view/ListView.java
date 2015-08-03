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
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.CheckListManager;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iGestureListener;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.iToolBar;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.renderer.UIComponentRenderer;
import com.appnativa.rare.ui.table.TableHelper;
import com.appnativa.rare.ui.tree.iTreeItem;
import com.appnativa.rare.viewer.aListViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharacterIndex;
import com.appnativa.util.IntList;
import com.appnativa.util.ObjectHolder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/*-[
 #import "RAREAPListView.h"
 #import "APView+Component.h"
 #import "com/appnativa/rare/ui/UIInsets.h"
 ]-*/
public class ListView extends aPlatformTableBasedView {
  protected UIInsets              rinsets           = new UIInsets();
  protected int                   checkboxLeftXSlop = INDICATOR_SLOP;
  protected UIInsets              borderInsets;
  protected CheckListManager      checkListManager;
  protected boolean               columnSizesInitialized;
  protected UIAction[]            editActions;
  protected iFunctionCallback     editModeNotifier;
  protected iToolBar              editToolbar;
  protected boolean               editing;
  protected iPlatformComponent    editingComponent;
  protected iGestureListener      flingGestureListener;
  protected boolean               linkedSelection;
  protected UIAction              markAll;
  protected iIOSRenderingCallback renderingCallback;
  private CharacterIndex          characterIndex;
  private SectionIndex            sectionIndex;
  private UIComponentRenderer     sectionRenderer;

  public ListView() {
    super(createProxy(false));
  }

  public ListView(boolean grouped) {
    super(createProxy(grouped));
  }

  protected ListView(Object proxy) {
    super(proxy);
  }

  public void actionPerformed(ActionEvent e, int index) {
    super.actionPerformed(e);
  }

  @Override
  public void addSelectionIndex(int index) {
    super.addSelectionIndex(index);

    if (linkedSelection && (checkListManager != null)) {
      if (!checkListManager.isRowChecked(index)) {
        toggleCheckedState(index);
      }
    }
  }

  @Override
  public void borderChanged(iPlatformBorder newBorder) {
    super.borderChanged(newBorder);

    if (!isTable() &&!isTree()) {
      borderInsets = (newBorder == null)
                     ? null
                     : newBorder.getBorderInsets(borderInsets);

      if (borderInsets != null) {
        borderInsets.addInsets(rinsets);
      }
    }
  }

  public void clearCheckedItems() {
    if (linkedSelection) {
      clearSelections();
    } else if (checkListManager != null) {
      checkListManager.clear();
    }
  }

  @Override
  public void clearSelections() {
    super.clearSelections();

    if (linkedSelection && (checkListManager != null)) {
      checkListManager.clear();
    }
  }

  public void editModeChangeAllMarks(boolean mark) {
    listModel.editModeChangeAllMarks(mark);
    repaintVisibleRows();
    updateActions();
  }

  public void hideRowEditingComponent(boolean animate) {
    int row = editingRow;

    editingRow = -1;

    if (row > -1) {
      RowView v = getViewForRow(row);

      if (v != null) {
        v.hideRowEditingComponent(animate);
      }
    }
  }

  public void hideRowEditingComponentEx() {
    int row = editingRow;

    editingRow = -1;

    if (row > -1) {
      RowView v = (row < getRowCount())
                  ? getViewForRow(row)
                  : null;

      if (v != null) {
        v.hideRowEditingComponent(false);
      }
    }
  }

  @Override
  public void paintRow(RowView view, AppleGraphics g, RenderableDataItem item, UIRectangle rect, iTreeItem ti) {
    if (checkListManager != null) {
      view.checkboxIcon = checkListManager.getRowIcon(view.row, item);
    }

    view.paint(g, rect);
  }

  @Override
  public void renderItem(int row, RenderableDataItem item, RowView view, boolean isSelected, boolean isPressed,
                         iTreeItem ti) {
    if (item == null) {
      item = NULL_ITEM;
    }

    view.row = row;

    if ((renderingCallback != null) && renderingCallback.renderItem(row, item, view, isSelected, isPressed, ti)) {
      return;
    }

    RowViewEx rv = (RowViewEx) view;

    if (item.hasCustomProperties()) {
      rv.setCustomProperties(item);
    }

    iPlatformComponent c = item.getRenderingComponent();

    if (c != null) {
      rv.setRenderingView(c.getView());
    } else {
      iPlatformRenderingComponent rc = ((RowViewEx) view).renderingComponent;

      if (rc == null) {
        rc = (iPlatformRenderingComponent) view.getComponent();
      }

      CharSequence text = itemRenderer.configureRenderingComponent(component, rc, item, row, isSelected, isPressed,
                            null, null);

      rc.getComponent(text, item);
    }

    UIInsets in = borderInsets;

    if (in != null) {
      rv.setMargin(in.top, in.right, in.bottom, in.left);
    }

    rv.setEditing(editing);
  }

  public void renderSection(Object contentProxy, Object labelProxy) {
    if ((sectionIndex != null) && (sectionIndex.sectionPrototype != null)) {
      if (sectionRenderer == null) {
        SectionHeader v = new SectionHeader(contentProxy, labelProxy);

        sectionRenderer = new UIComponentRenderer(new Component(v));
      } else {
        ((SectionHeader) sectionRenderer.getNativeView()).reset(contentProxy, labelProxy);
      }

      sectionRenderer.prepareForReuse(0, 0);
      itemRenderer.configureRenderingComponent(component, sectionRenderer, sectionIndex.sectionPrototype, 0, false,
              false, null, null);
    }
  }

  @Override
  public void rowsDeleted(int firstRow, int lastRow) {
    if (sectionIndex != null) {
      updateSectionIndex();
    }

    super.rowsDeleted(firstRow, lastRow);
  }

  @Override
  public void rowsInserted(int firstRow, int lastRow) {
    if (sectionIndex != null) {
      updateSectionIndex();
    }

    super.rowsInserted(firstRow, lastRow);
  }

  public void startEditing(UIAction[] actions, final boolean animate) {
    this.editing = true;
    listModel.setEditing(true);

    if (getSelectedIndex() != -1) {
      clearSelections();
      revalidate();
    }

    if (actions == null) {
      if (deletingAllowed && editingSelectionAllowed) {
        actions    = new UIAction[2];
        actions[0] = new UIAction("Rare.action.markAll", Platform.getResourceAsString("Rare.action.markAll"), null);
        actions[1] = new UIAction("Rare.action.delete", Platform.getResourceAsString("Rare.action.delete"), null);
      } else if (deletingAllowed) {
        actions    = new UIAction[1];
        actions[0] = new UIAction("Rare.action.delete", Platform.getResourceAsString("Rare.action.delete"), null);
      } else if (editingSelectionAllowed) {
        actions    = new UIAction[1];
        actions[0] = new UIAction("Rare.action.markAll", Platform.getResourceAsString("Rare.action.markAll"), null);
      }
    }

    if ((actions != null) && (actions.length > 0) && (editToolbar != null)) {
      this.editActions = actions;

      iToolBar tb       = editToolbar;
      boolean  isDelete = false;

      for (UIAction a : actions) {
        if ("Rare.action.delete".equalsIgnoreCase(a.getActionName())) {
          a.setActionListener(new iActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              editModeDeleteMarkedItems();
            }
          });
          a.setEnabledOnSelectionOnly(true);
          isDelete = true;
        } else if ("Rare.action.markAll".equalsIgnoreCase(a.getActionName())) {
          a.setActionListener(new iActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              editModeChangeAllMarks(listModel.editModeGetMarkCount() < listModel.size());
            }
          });
          markAll = a;
        }

        iWidget pb = tb.add(a);

        if (isDelete) {
          ((ActionComponent) pb.getDataComponent()).setPainterHolder(PainterUtils.createRedHyperlinkPainterHolder());
          a.setEnabled(false);
        }

        isDelete = false;
      }

      tb.getComponent().setVisible(true);
    }

    repaintVisibleRows();
    setEditing(true, animate);

    if (editModeNotifier != null) {
      editModeNotifier.finished(false, Platform.findWidgetForComponent(this));
    }
  }

  public void stopEditing(boolean animate) {
    this.editing = false;
    listModel.setEditing(false);
    setEditing(false, animate);

    if (editToolbar != null) {
      editToolbar.getComponent().setVisible(false);
    }

    if (editToolbar != null) {
      editToolbar.getComponent().setVisible(false);
      editToolbar.removeAllWidgets();
    }

    if (editModeNotifier != null) {
      editModeNotifier.finished(true, Platform.findWidgetForComponent(this));
    }

    repaintVisibleRows();
  }

  public void updateSectionIndex() {
    CharacterIndex     ci        = listModel.getFilteringIndex();
    RenderableDataItem prototype = sectionIndex.sectionPrototype;

    sectionIndex = null;

    if (ci != null) {
      setSectionIndex(ci, listModel.size(), prototype);
    }
  }

  public void setCheckboxLeftXSlop(int checkboxLeftXSlop) {
    this.checkboxLeftXSlop = checkboxLeftXSlop;
  }

  public void setCheckedRows(int[] indices) {
    if (checkListManager != null) {
      checkListManager.setCheckedRows(indices);
      repaint();
    }
  }

  public void setEditModeNotifier(iFunctionCallback editModeNotifier) {
    this.editModeNotifier = editModeNotifier;
  }

  public void setEditingToolbar(iToolBar tb) {
    editToolbar = tb;

    if (tb != null) {
      tb.getComponent().setVisible(false);
    }
  }

  public void setIcons(iPlatformIcon checked, iPlatformIcon unchecked, iPlatformIcon indeterminate) {
    checkboxHeight = checked.getIconHeight();
    checkboxWidth  = checked.getIconWidth();
    checkboxHeight = Math.max(checkboxHeight, unchecked.getIconHeight());
    checkboxWidth  = Math.max(checkboxWidth, unchecked.getIconWidth());

    if (checkListManager == null) {
      checkListManager = createCheckListManager();
    }

    checkListManager.setIcons(checked, unchecked, indeterminate);
    calculateOffset();
  }

  @Override
  public void setItemRenderer(ListItemRenderer lr) {
    super.setItemRenderer(lr);
    rinsets.set(lr.getInsets());
    calculateOffset();
  }

  public void setLinkSelection(boolean linked) {
    linkedSelection = linked;
  }

  @Override
  public void setListModel(iPlatformListDataModel listModel) {
    if (this.listModel != null) {
      this.listModel.removeDataModelListener(this);
    }

    this.listModel = listModel;

    if (listModel != null) {
      listModel.addDataModelListener(this);

      if (checkListManager != null) {
        checkListManager.setListModel(listModel);
      }
    }

    setListModelEx(listModel);
  }

  @Override
  public native void setPaintHandlerEnabled(boolean enabled)
  /*-[
    [((RAREAPListView*)proxy_) setPaintHandlerEnabled: enabled];
    [((RAREAPListView*)proxy_) setSimplePaint: [self isSingleColorPainter]];
  ]-*/
  ;

  public void setRenderingCallback(iIOSRenderingCallback cb) {
    renderingCallback = cb;
  }

  public void setRowChecked(int row, boolean checked) {
    if (checkListManager != null) {
      if (checkListManager.isRowChecked(row) != checked) {
        toggleCheckedState(row);
      }
    }
  }

  public void setRowEditingComponent(iPlatformComponent c, boolean centerVertically) {
    editingComponent                 = c;
    centerEditingComponentVertically = centerVertically;

    if (c != null) {
      needsContentView = true;

      if (flingGestureListener == null) {
        flingGestureListener = new RowEditingGestureListener();
      }

      this.setRowEditingGestureListener(flingGestureListener);
    } else if (flingGestureListener != null) {
      flingGestureListener = null;
      this.setRowEditingGestureListener(flingGestureListener);
    }
  }

  public void setSectionIndex(SectionIndex sectionIndex) {
    this.sectionIndex = sectionIndex;
  }

  public void setSectionIndex(CharacterIndex ci, int listSize, RenderableDataItem headerPrototype) {
    Map<Character, Integer> indexMap = ci.getIndexMap();
    Iterator                it       = indexMap.entrySet().iterator();
    int                     len      = indexMap.size();
    int                     i        = 0;
    ObjectHolder            a[]      = new ObjectHolder[len + 1];

    while(it.hasNext()) {
      Entry e = (Entry) it.next();

      a[i++] = new ObjectHolder(e.getKey(), e.getValue());
    }

    a[len] = new ObjectHolder(Character.valueOf('z'), Integer.valueOf(listSize));    // make
    // sure
    // it
    // sorts
    // last;
    Arrays.sort(a, new Comparator() {
      @Override
      public int compare(Object o1, Object o2) {
        return ((Integer) ((ObjectHolder) o1).value).compareTo((Integer) ((ObjectHolder) o2).value);
      }
    });
    i = 0;

    int[]    position = new int[len];
    int[]    length   = new int[len];
    String[] titles   = new String[len];
    int      n        = 0;

    while(i < len) {
      Character c     = (Character) a[i].type;
      int       start = ((Integer) a[i++].value).intValue();
      int       end   = ((Integer) a[i].value).intValue();

      position[n] = start;
      length[n]   = end - start;
      titles[n]   = String.valueOf(c);
      n++;
      start = end;
    }

    sectionIndex = new SectionIndex(titles, position, length, headerPrototype);
  }

  @Override
  public void setSelectedIndex(int index) {
    super.setSelectedIndex(index);

    if (linkedSelection && (checkListManager != null)) {
      if (!checkListManager.isRowChecked(index)) {
        toggleCheckedState(index);
      }
    }
  }

  public native void setShowSectionIndex(boolean show)
  /*-[
    [((RAREAPListView*)proxy_) setUseSectionIndex: show];
  ]-*/
  ;

  @Override
  public native void setSingleClickAction(boolean singleClickAction)
  /*-[
    singleClickAction_=YES;
    [((RAREAPListView*)proxy_) setSingleClickAction: singleClickAction];
  ]-*/
  ;

  public int getCheckboxLeftXSlop() {
    return checkboxLeftXSlop;
  }

  public iFunctionCallback getEditModeNotifier() {
    return editModeNotifier;
  }

  @Override
  public ListItemRenderer getItemRenderer() {
    return itemRenderer;
  }

  public void getPreferredSize(UIDimension size, float maxWidth) {
    TableHelper.calculateListSize(listModel, component, itemRenderer, null, size, -1, (int) maxWidth, rowHeight);

    int h = TableHelper.getPreferredListHeight(Component.findFromView(this), Math.max(minVisibleRows, visibleRows),
              rowHeight, getRowCount());

    size.height = Math.max(h, size.height);
  }

  public iTreeItem getTreeItem(RenderableDataItem item) {
    return null;
  }

  public boolean hasCheckedRows() {
    return (checkListManager != null) && (checkListManager.hasCheckedRows());
  }

  public boolean isRowChecked(int row) {
    return (checkListManager != null) && checkListManager.isRowChecked(row);
  }

  @Override
  public boolean isScrollView() {
    return true;
  }

  @Override
  public boolean isSingleClickAction() {
    return singleClickAction;
  }

  protected void calculateOffset() {
    int left  = 0;
    int right = 0;

    if (checkboxWidth > 0) {
      if (selectionType == SelectionType.CHECKED_LEFT) {
        left = checkboxWidth + PAD_SIZE;
      } else {
        right = checkboxWidth + PAD_SIZE;
      }
    }

    updateRenderInsetsForCheckBox(left, right);
  }

  protected boolean canDrag(RenderableDataItem item) {
    return draggingAllowed && item.isDraggingAllowed();
  }

  protected boolean checForLinkedSelection(int row, boolean selected) {
    if (linkedSelection && (checkListManager != null)) {
      if (checkListManager.isRowChecked(row) != selected) {
        return checkListManager.toggleRow(row);
      }
    }

    return false;
  }

  @Override
  protected boolean checkForCellHotspot(int row, float x, float y, float width, float height) {
    if ((checkListManager != null) &&!linkedSelection && isOnCheckBox(x, y, width, height, getIndent(row))) {
      if (checkListManager.toggleRow(row)) {
        repaint();
      } else {
        repaintRow(row);
      }

      return true;
    }

    return false;
  }

  protected CheckListManager createCheckListManager() {
    CheckListManager cm = new CheckListManager(false, -2);

    if (listModel != null) {
      cm.setListModel(listModel);
    }

    return cm;
  }

  @Override
  protected void disposeEx() {
    itemRenderer = null;

    if (listModel != null) {
      listModel.removeDataModelListener(this);
    }

    if (sectionRenderer != null) {
      sectionRenderer.dispose();
      sectionRenderer = null;
    }

    super.disposeEx();
  }

  protected void editModeDeleteMarkedItems() {
    aListViewer lv = (aListViewer) Component.fromView(this).getWidget();

    lv.removeAll(Arrays.asList(listModel.editModeGetMarkedItems()));
    listModel.editModeClearMarks();

    if (autoEndEditing) {
      stopEditing(true);
    } else {
      updateActions();
    }
  }

  protected int geIndent(int row) {
    return 0;
  }

  @Override
  protected void itemDeselected(int index) {
    checForLinkedSelection(index, false);
    super.itemDeselected(index);
  }

  @Override
  protected void itemSelected(int index) {
    checForLinkedSelection(index, true);
    super.itemSelected(index);
  }

  @Override
  protected void move(int from, int to) {
    super.move(from, to);
    updateActions();
  }

  @Override
  protected void move(IntList from, int to) {
    super.move(from, to);
    updateActions();
  }

  protected void toggleCheckedState(int row) {
    checkListManager.toggleRow(row);
    repaintRow(row);
  }

  protected void toggleEditModeItemChecked(int row) {
    listModel.editModeToggleMark(row);
    repaintRow(row);
    updateActions();
  }

  protected void updateActions() {
    if (this.editActions != null) {
      int     mc        = listModel.editModeGetMarkCount();
      int     size      = listModel.size();
      boolean hasMarks  = mc > 0;
      boolean allMarked = (mc == size) && (mc != 0);

      if (markAll != null) {
        markAll.setActionText(Platform.getResourceAsString(allMarked
                ? "Rare.action.unmarkAll"
                : "Rare.action.markAll"));
      }

      for (UIAction a : editActions) {
        a.setEnabled(size > 0);

        if (a.isEnabledOnSelectionOnly()) {
          a.setEnabled(hasMarks);
        }
      }
    }
  }

  protected void updateRenderInsetsForCheckBox(float left, float right) {
    if (itemRenderer != null) {
      UIInsets in = itemRenderer.getInsets();

      in.set(rinsets);
      in.left  += left;
      in.right += right;
    }
  }

  protected native void setListModelEx(iPlatformListDataModel listModel)
  /*-[
    [((RAREAPListView*)proxy_) setList: listModel];
  ]-*/
  ;

  protected native void setRowEditingGestureListener(iGestureListener l)
  /*-[
    [((RAREAPListView*)proxy_) setRowEditorGestureListener: l];
  ]-*/
  ;

  protected int getIndent(int row) {
    return 0;
  }

  protected boolean isOnCheckBox(float x, float y, float width, float height, int indent) {
    float sy = (height - checkboxHeight) / 2;

    if ((y < sy - INDICATOR_SLOP) || (y > (sy + checkboxHeight + INDICATOR_SLOP))) {
      return false;
    }

    float sx;
    int   slop = INDICATOR_SLOP;

    if (selectionType == SelectionType.CHECKED_RIGHT) {
      sx = width - rightOffset - PAD_SIZE - checkboxWidth;
      ;
    } else {
      sx   = leftOffset + PAD_SIZE + indent;
      slop = checkboxLeftXSlop;
    }

    return (x >= sx - slop) && (x <= (sx + checkboxWidth + slop));
  }

  protected boolean isTree() {
    return false;
  }

  private native static Object createProxy(boolean grouped)
  /*-[
  #if TARGET_OS_IPHONE
    UITableViewStyle style=grouped ? UITableViewStyleGrouped : UITableViewStylePlain;
    return [[[RAREAPListView alloc]initWithStyle: style ] configureForList];
  #else
    return [[[RAREAPListView alloc]init] configureForList];
  #endif

  ]-*/
  ;

  /**
   * This interface allows for customizing the rendering of UITableVIew cells
   */
  public interface iIOSRenderingCallback {

    /**
     * Called to render an item into a UITableViewCell which is a the proxy for
     * a RowView
     *
     * @param row
     *          the row
     * @param item
     *          the item
     * @param view
     *          the view
     * @param isSelected
     *          whether the item is selected
     * @param isPressed
     *          whether the item is pressed
     * @param ti
     *          handle to the tree item if in a tree table
     * @return true if rendering was handled false to let the engine render
     */
    boolean renderItem(int row, RenderableDataItem item, RowView view, boolean isSelected, boolean isPressed,
                       iTreeItem ti);
  }


  class RowEditingGestureListener implements iGestureListener {
    @Override
    public void onFling(Object view, MouseEvent e1, MouseEvent e2, float velocityX, float velocityY) {
      int     row = rowAtPoint((int) e1.getX(), (int) e1.getY());
      RowView v   = (row == -1)
                    ? null
                    : getViewForRow(row);

      if (editingRow == -1) {
        if (e1.getX() > e2.getX()) {
          editingRow    = row;
          lastEditedRow = row;
          v.showRowEditingComponent(editingComponent, true);
        }
      } else {
        if (e1.getX() < e2.getX()) {
          v.hideRowEditingComponent(true);
          editingRow = -1;
        }
      }
    }

    @Override
    public void onLongPress(Object view, MouseEvent e) {}

    @Override
    public void onRotate(Object view, int type, float rotation, float velocity, float focusX, float focusY) {}

    @Override
    public void onScaleEvent(Object view, int type, Object sgd, float factor) {}
  }


  static class SectionHeader extends View {
    Object label;

    public SectionHeader(Object nsview, Object label) {
      super(nsview);
      this.label = label;
    }

    public void reset(Object contentProxy, Object labelProxy) {
      setProxy(contentProxy);
      label = labelProxy;
    }

    @Override
    public native void setFont(UIFont font)
    /*-[
      if(font!=font_) {
        font_ = font;
        if(font!=nil) {
          [((UILabel*)label_) setFont: (UIFont*)[font getIOSProxy]];
        }
      }
    ]-*/
    ;

    @Override
    protected native void setForegroundColorEx(UIColor fg)
    /*-[
      if(fg!=nil) {
        [((UILabel*)label_) setTextColor: fg.getAPColor];
      }
    ]-*/
    ;
  }
}
