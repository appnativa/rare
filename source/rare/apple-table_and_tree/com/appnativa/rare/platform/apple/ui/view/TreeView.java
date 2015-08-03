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
import com.appnativa.rare.ui.CheckListManager;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontMetrics;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.renderer.aTreeItemRenderer;
import com.appnativa.rare.ui.table.TableView;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.rare.ui.tree.aDataItemTreeModel;
import com.appnativa.rare.ui.tree.iExpansionHandler;
import com.appnativa.rare.ui.tree.iTree;
import com.appnativa.rare.ui.tree.iTreeItem;

/*-[
#import "RAREAPListView.h"
#import "com/appnativa/rare/ui/UIInsets.h"
]-*/
public class TreeView extends TableView implements iTree {
  int                          lastUpX = -1;
  int                          lastUpY = -1;
  long                         lastUpTime;
  boolean                      twistyMarginOfErrorSet;
  protected int                indentBy                = ScreenUtils.platformPixels(Platform.isTouchDevice()
          ? 22
          : 16);
  protected boolean            showRootHandles         = true;
  protected boolean            showRoot                = true;
  protected boolean            manageCheckboxSelection = true;
  protected boolean            autoScrollOnExpansion   = true;
  protected iPlatformIcon      collapsedIcon;
  protected boolean            expandableStateLocked;
  protected iPlatformIcon      expandedIcon;
  protected iExpansionHandler  expansionHandler;
  protected boolean            indentBySet;
  protected boolean            rootNodeCollapsible;
  protected boolean            singleClickToggle;
  protected boolean            toggleOnTwistyOnly;
  protected aDataItemTreeModel treeModel;
  protected int                twistyMarginOfError;

  public TreeView() {
    this(createTreeProxy());
  }

  public TreeView(Object proxy) {
    super(proxy);
    twistyMarginOfError = INDICATOR_SLOP;
    setTreeIcons(Platform.getResourceAsIcon("Rare.Tree.expandedIcon"),
                 Platform.getResourceAsIcon("Rare.Tree.collapsedIcon"));
  }

  @Override
  public void paintRow(RowView view, AppleGraphics g, RenderableDataItem item, UIRectangle rect, iTreeItem ti) {
    if (ti == null) {
      ti = treeModel.getTreeItem(item);
    }

    int     depth    = 0;
    boolean expanded = false;
    boolean leaf     = true;

    if (expandedIcon == null) {
      loadIcons();
    }

    if (ti != null) {
      depth = ti.getDepth();

      if (!showRoot) {
        depth = Math.max(depth - 1, 0);
      }

      expanded = ti.isExpanded();
      leaf     = ti.isLeaf();
    }

    depth       *= indentBy;
    view.indent = ScreenUtils.PLATFORM_PIXELS_2 + depth;

    if (showRootHandles) {
      if (leaf) {
        view.indicator = null;
      } else {
        view.indicator = expanded
                         ? expandedIcon
                         : collapsedIcon;
      }
    } else {
      view.indicator = null;
    }

    super.paintRow(view, g, item, rect, ti);
  }

  @Override
  public void renderItem(int row, RenderableDataItem item, RowView view, boolean isSelected, boolean hasFocus,
                         iTreeItem ti) {
    boolean empty = (item == null) || (item == NULL_ITEM);

    view.row = row;

    if (empty) {
      item = NULL_ITEM;
    } else {
      if (ti == null) {
        ti = treeModel.getTreeItem(item);
      }
    }

    if (isTable()) {    //we are a tree table
      super.renderItem(row, item, view, isSelected, hasFocus, ti);

      return;
    }

    boolean expanded = false;
    boolean leaf     = true;
    int     depth    = 0;

    if (!empty) {
      if (ti != null) {
        leaf     = ti.isLeaf();
        expanded = ti.isExpanded();
        depth    = ti.getDepth();

        if (!showRoot) {
          depth = Math.max(depth - 1, 0);
        }
      }

      depth *= indentBy;
      depth += checkboxWidth;
      depth += indicatorWidth;

      if (!leaf) {
        depth += ICON_GAP;
      }

      ((aTreeItemRenderer) itemRenderer).setItemState(leaf, expanded, depth);
    } else {
      ((aTreeItemRenderer) itemRenderer).prepareForEmptyItem();
    }

    iPlatformComponent c = item.getRenderingComponent();

    if (c != null) {
      ((RowViewEx) view).setRenderingView(c.getView());
    } else {
      iPlatformRenderingComponent rc = ((RowViewEx) view).renderingComponent;

      if (rc == null) {
        rc = (iPlatformRenderingComponent) view.getComponent();
      }

      CharSequence text = itemRenderer.configureRenderingComponent(component, rc, item, row, isSelected, hasFocus,
                            null, null);

      rc.getComponent(text, item);
    }
  }

  public void setAutoScrollOnExpansion(boolean autoScrollOnExpansion) {
    this.autoScrollOnExpansion = autoScrollOnExpansion;
  }

  @Override
  public void setExpandableStateLocked(boolean locked) {
    expandableStateLocked = locked;
  }

  @Override
  public void setExpansionHandler(iExpansionHandler expansionHandler) {
    this.expansionHandler = expansionHandler;
  }

  @Override
  public void setIcons(iPlatformIcon checked, iPlatformIcon unchecked, iPlatformIcon indeterminate) {
    super.setIcons(checked, unchecked, indeterminate);
  }

  @Override
  public void setIndentBy(int indent) {
    indentBy    = indent;
    indentBySet = true;
  }

  public void setItemRenderer(aTreeItemRenderer lr) {
    super.setItemRenderer(lr);
  }

  public void setManageCheckboxSelection(boolean manageCheckboxSelection) {
    this.manageCheckboxSelection = manageCheckboxSelection;

    if (checkListManager != null) {
      checkListManager.setManageSelections(manageCheckboxSelection);
    }
  }

  @Override
  public void setRootNodeCollapsible(boolean collapsible) {
    rootNodeCollapsible = collapsible;
  }

  @Override
  public void setSelectionType(SelectionType type) {
    super.setSelectionType(type);

    if (!twistyMarginOfErrorSet) {
      if (type == SelectionType.CHECKED_LEFT) {
        if (checkListManager != null) {
          twistyMarginOfError = 0;
          setCheckboxLeftXSlop(0);
        } else {
          twistyMarginOfError = INDICATOR_SLOP;
          setCheckboxLeftXSlop(INDICATOR_SLOP);
        }
      } else {
        twistyMarginOfError = INDICATOR_SLOP;
        setCheckboxLeftXSlop(INDICATOR_SLOP);
      }
    }
  }

  @Override
  public void setShowRoot(boolean show) {
    showRoot = show;
  }

  @Override
  public void setShowRootHandles(boolean show) {
    showRootHandles = show;

    if (show) {
      loadIcons();
    } else {
      indicatorWidth  = 0;
      indicatorHeight = 0;
      calculateOffset();
    }
  }

  @Override
  public void setSingleClickToggle(boolean singleClickToggle) {
    this.singleClickToggle = singleClickToggle;
  }

  @Override
  public void setToggleOnTwistyOnly(boolean twistyOnly) {
    this.toggleOnTwistyOnly = twistyOnly;
  }

  public void setTreeIcons(iPlatformIcon expaneded, iPlatformIcon collapsed) {
    expandedIcon    = expaneded;
    collapsedIcon   = collapsed;
    indicatorWidth  = 0;
    indicatorHeight = 0;

    if (showRootHandles) {
      if (expaneded != null) {
        indicatorWidth  = expaneded.getIconWidth();
        indicatorHeight = expaneded.getIconHeight();
      }

      if (collapsed != null) {
        indicatorWidth  = Math.max(indicatorWidth, collapsed.getIconWidth());
        indicatorHeight = Math.max(indicatorHeight, collapsed.getIconHeight());
      }
    }

    calculateOffset();
  }

  public void setTreeModel(DataItemTreeModel treeModel) {
    this.treeModel = treeModel;
    setListModel(treeModel.getListModel());
  }

  @Override
  public void setTwistyMarginOfError(int twistyMarginOfError) {
    this.twistyMarginOfError = twistyMarginOfError;
    twistyMarginOfErrorSet   = true;
  }

  public iPlatformIcon getCollapsedIcon() {
    return collapsedIcon;
  }

  public int getCount() {
    return (listModel == null)
           ? 0
           : listModel.size();
  }

  public iPlatformIcon getExpandedIcon() {
    return expandedIcon;
  }

  public iExpansionHandler getExpansionHandler() {
    return expansionHandler;
  }

  @Override
  public int getIndent(int row) {
    RenderableDataItem item = listModel.get(row);
    iTreeItem          ti   = treeModel.getTreeItem(item);

    if (ti == null) {
      return 0;
    }

    int depth = ti.getDepth();

    if (!showRoot) {
      depth = Math.max(depth - 1, 0);
    }

    depth *= indentBy;

    return depth + indicatorWidth + ICON_GAP;
  }

  @Override
  public int getIndentBy() {
    return indentBy;
  }

  public int getIndicatorHeight() {
    return indicatorHeight;
  }

  @Override
  public int getIndicatorWidth() {
    return indicatorWidth;
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {
    if (isTable()) {
      super.getPreferredSize(size, maxWidth);

      return;
    }

    UIFont f = component.getFont();

    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    float rh = getRowHeight();

    if (rh < 1) {
      rh = FontUtils.getDefaultLineHeight();
    }

    size.height = (int) rh * visibleRows;

    int                    ch   = FontUtils.getCharacterWidth(f);
    int                    w    = ch * 5;
    iPlatformListDataModel list = listModel;

    if ((list != null) && (list.size() > 0)) {
      UIFontMetrics fm    = UIFontMetrics.getMetrics(f);
      int           len   = list.size();
      iTreeItem     ti    = null;
      int           depth = 0;

      for (int i = 0; i < len; i++) {
        RenderableDataItem item = list.get(i);

        ti = treeModel.getTreeItem(item);

        if (ti != null) {
          depth = ti.getDepth();

          if (!showRoot) {
            depth = Math.max(depth - 1, 0);
          }
        }

        depth *= indentBy;

        if (len > 20) {
          w = Math.max(w, ch * listModel.get(i).toString().length() + depth);
        } else {
          w = Math.max(w, 48 + fm.stringWidth(listModel.get(i).toString()) + depth);
        }
      }
    }

    if (itemRenderer != null) {
      UIInsets in = itemRenderer.getInsets();

      w += in.left + in.right;
    }

    size.width = w;
  }

  @Override
  public iTreeItem getTreeItem(RenderableDataItem item) {
    return (treeModel == null)
           ? null
           : treeModel.getTreeItem(item);
  }

  public aDataItemTreeModel getTreeModel() {
    return treeModel;
  }

  @Override
  public int getTwistyMarginOfError() {
    return twistyMarginOfError;
  }

  public boolean isAutoScrollOnExpansion() {
    return autoScrollOnExpansion;
  }

  @Override
  public boolean isExpandableStateLocked() {
    return expandableStateLocked;
  }

  public boolean isManageCheckboxSelection() {
    return manageCheckboxSelection;
  }

  @Override
  public boolean isRootNodeCollapsible() {
    return rootNodeCollapsible;
  }

  @Override
  public boolean isShowRoot() {
    return showRoot;
  }

  public boolean isShowRootHandles() {
    return showRootHandles;
  }

  @Override
  public boolean isSingleClickToggle() {
    return singleClickToggle;
  }

  @Override
  public boolean isToggleOnTwistyOnly() {
    return toggleOnTwistyOnly;
  }

  @Override
  protected boolean checkForCellHotspot(int row, float x, float y, float width, float height) {
    RenderableDataItem item = listModel.get(row);
    iTreeItem          ti   = getTreeItem(item);

    if (ti == null) {
      return false;
    }

    int depth = ti.getDepth();

    if (!showRoot) {
      depth = Math.max(depth - 1, 0);
    }

    depth *= indentBy;

    float sx = leftOffset + depth + PAD_SIZE;

    if ((checkListManager != null) &&!linkedSelection
        && isOnCheckBox(x, y, width, height, (int) (sx + indicatorWidth + ICON_GAP))) {
      if (ti.isLeaf()) {
        if (checkListManager.toggleRow(row, item)) {
          repaint();
        } else {
          repaintRow(row);
        }
      } else {
        if (checkListManager.toggleManagedRow(row, item)) {
          repaint();
        } else {
          repaintRow(row);
        }
      }

      return true;
    }

    if (toggleOnTwistyOnly) {
      float sy = (height - indicatorHeight) / 2;

      if ((y < sy - INDICATOR_SLOP) || (y > (sy + indicatorHeight + INDICATOR_SLOP))) {
        return false;
      }

      if ((x > sx - twistyMarginOfError) && (x < (sx + indicatorWidth + twistyMarginOfError))) {
        return handleExpansion(ti, row, true);
      }
    }

    return false;
  }

  @Override
  protected CheckListManager createCheckListManager() {
    if (!twistyMarginOfErrorSet) {
      if (selectionType == SelectionType.CHECKED_LEFT) {
        twistyMarginOfError = 0;
        setCheckboxLeftXSlop(0);
      } else {
        twistyMarginOfError = INDICATOR_SLOP;
        setCheckboxLeftXSlop(INDICATOR_SLOP);
      }
    }

    CheckListManager cm = new CheckListManager(false, -1);

    cm.setManageSelections(manageCheckboxSelection);

    if (listModel != null) {
      cm.setListModel(listModel);
    }

    return cm;
  }

  @Override
  protected void createHeader() {}

  @Override
  protected void disposeEx() {
    super.disposeEx();

    if (treeModel != null) {
      treeModel.dispose();
    }

    treeModel        = null;
    expansionHandler = null;
    expandedIcon     = null;
    collapsedIcon    = null;
  }

  protected boolean handleExpansion(iTreeItem ti, int position, boolean userDriven) {
    if ((expansionHandler != null) && (!expandableStateLocked ||!userDriven)) {
      if (!ti.isLeaf()) {
        int scrollTo = expansionHandler.toggleExpansion(this, ti, position);

        scrollTo = Math.min(scrollTo, getCount() - 1);

        if (scrollTo > 0) {
          if (isRowChecked(position)) {
            for (int i = position + 1; i <= scrollTo; i++) {
              if (!isRowChecked(i)) {
                setRowChecked(i, true);
              }
            }
          }

          if (autoScrollOnExpansion) {
            scrollRowToVisible(scrollTo);
          }
        }

        return true;
      }
    }

    return false;
  }

  @Override
  protected void itemSelected(int index) {
    if (!toggleOnTwistyOnly) {
      RenderableDataItem item = listModel.get(index);
      iTreeItem          ti   = getTreeItem(item);

      if ((ti != null) &&!ti.isLeaf()) {
        handleExpansion(ti, index, true);
      }
    }

    super.itemSelected(index);
  }

  protected void loadIcons() {
    if (collapsedIcon == null) {
      collapsedIcon = Platform.getUIDefaults().getIcon("Rare.Tree.collapsedIcon");
    }

    if (expandedIcon == null) {
      expandedIcon = Platform.getUIDefaults().getIcon("Rare.Tree.expandedIcon");
    }

    setTreeIcons(expandedIcon, collapsedIcon);
  }

  @Override
  protected void toggleCheckedState(int row) {
    RenderableDataItem item = listModel.get(row);
    iTreeItem          ti   = getTreeItem(item);

    if ((ti != null) &&!ti.isLeaf()) {
      boolean deep = checkListManager.toggleManagedRow(row, item);

      if (manageCheckboxSelection && ti.isExpanded()) {
        if (deep) {
          repaint();
        } else {
          int ec = treeModel.getExpandableColumn();
          int cc = (ec == -1)
                   ? item.size()
                   : item.get(ec).size();

          repaintRows(row, row + cc);
        }
      }
    } else {
      boolean deep = checkListManager.toggleRow(row, item);

      if (deep) {
        repaint();
      } else {
        repaintRow(row);

        if (manageCheckboxSelection) {
          RenderableDataItem parent = item.getParentItem();

          if (parent != null) {
            repaintRow(parent);
          }
        }
      }
    }
  }

  @Override
  protected void updateRenderInsetsForCheckBox(float left, float right) {
    if (itemRenderer != null) {
      UIInsets in = itemRenderer.getInsets();

      in.set(rinsets);
      in.left  += left;
      in.right += right;
    }
  }

  protected boolean isOnCheckBox(float x, float width, int indent) {
    if (selectionType == SelectionType.CHECKED_RIGHT) {
      return (x >= width - PAD_SIZE - checkboxWidth) && (x < (width - PAD_SIZE));
    }

    return (x > indent) && (x < indent + checkboxWidth + PAD_SIZE);
  }

  @Override
  protected boolean isTable() {
    return false;
  }

  @Override
  protected boolean isTree() {
    return true;
  }

  private native static Object createTreeProxy()
  /*-[
    return [[[RAREAPListView alloc]init] configureForTree];
  ]-*/
  ;

  private int getIndicatorWidth(UIImage image) {
    int w = image.getWidth();

    if (w > indicatorWidth) {
      indicatorWidth = w;
    }

    return indicatorWidth;
  }
}
