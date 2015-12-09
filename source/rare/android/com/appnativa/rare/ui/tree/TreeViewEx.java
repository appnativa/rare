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

package com.appnativa.rare.ui.tree;

import android.annotation.SuppressLint;

import android.content.Context;

import android.view.MotionEvent;
import android.view.View;

import android.widget.Adapter;

import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.ui.CheckListManager;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListDataModel;

/**
 *
 * @author Don DeCoteau
 */
public class TreeViewEx extends ListViewEx implements iTree {
  int                        lastUpX = -1;
  int                        lastUpY = -1;
  long                       lastUpTime;
  private boolean            showRootHandles         = true;
  private boolean            showRoot                = true;
  private boolean            manageCheckboxSelection = true;;
  private boolean            autoScrollOnExpansion   = true;
  private iPlatformIcon      collapsedIcon;
  private boolean            expandableStateLocked;
  private iPlatformIcon      expandedIcon;
  private iExpansionHandler  expansionHandler;
  private boolean            rootNodeCollapsible;
  private boolean            singleClickToggle;
  private boolean            toggleOnTwistyOnly;
  private aDataItemTreeModel treeModel;
  private int                twistyMarginOfError;
  private boolean            twistyMarginOfErrorSet;
  private boolean            parentItemsSelectable = true;

  public TreeViewEx(Context context) {
    super(context);
    twistyMarginOfError = INDICATOR_SLOP;
    indentBy            = ScreenUtils.platformPixels(16);
  }

  @SuppressLint("ClickableViewAccessibility")
  public boolean onTouchEvent(MotionEvent ev) {
    if (toggleOnTwistyOnly) {
      if ((ev.getAction() == MotionEvent.ACTION_UP) || (ev.getAction() == MotionEvent.ACTION_DOWN)) {
        lastUpX    = (int) ev.getX();
        lastUpY    = (int) ev.getY();
        lastUpTime = System.currentTimeMillis();
      }
    }

    return super.onTouchEvent(ev);
  }

  public boolean performItemClick(View view, int position, long id) {
    if (ignoreLastItemClick && (lastClickTime + 100) > System.currentTimeMillis()) {
      ignoreLastItemClick = false;

      return true;
    }

    RenderableDataItem item = (RenderableDataItem) getAdapter().getItem(position);
    iTreeItem          ti   = getTreeItem(item);

    if (ti == null) {
      return super.performItemClick(view, position, id);
    }

    boolean handled = false;

    if (toggleOnTwistyOnly && (System.currentTimeMillis() - lastUpTime) < 500) {
      if (showRootHandles && (ti != null) &&!ti.isLeaf() && (lastUpX < (indicatorWidth + twistyMarginOfError))) {
        handleExpansion(ti, position, true);

        return true;
      }
    }

    if (!toggleOnTwistyOnly && handleExpansion(ti, position, true)) {
      handled = true;
    }
    return handled
           ? true
           : super.performItemClick(view, position, id);
  }

  @Override
  protected boolean isSelectable(RenderableDataItem item) {
    if (!super.isSelectable(item)) {
      return false;
    }

    if (!parentItemsSelectable) {
      iTreeItem ti = getTreeItem(item);

      if ((ti != null) &&!ti.isLeaf()) {
        return false;
      }
    }

    return true;
  }
   @Override
  public void dispose() {
    super.dispose();
    treeModel=null;
  }
   
  public void setAutoScrollOnExpansion(boolean autoScrollOnExpansion) {
    this.autoScrollOnExpansion = autoScrollOnExpansion;
  }

  public void setExpandableStateLocked(boolean locked) {
    expandableStateLocked = locked;
  }

  public void setExpansionHandler(iExpansionHandler expansionHandler) {
    this.expansionHandler = expansionHandler;
  }

  public void setIndentBy(int indent) {
    indentBy = indent;
  }

  public void setManageChildNodeSelections(boolean manage) {
    manageCheckboxSelection = manage;
  }

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

  public void setShowRoot(boolean show) {
    showRoot = show;
  }

  public void setShowRootHandles(boolean show) {
    showRootHandles = show;
  }

  public void setSingleClickToggle(boolean singleClickToggle) {
    this.singleClickToggle = singleClickToggle;
  }

  public void setToggleOnTwistyOnly(boolean twistyOnly) {
    this.toggleOnTwistyOnly = twistyOnly;
  }

  public void setTreeIcons(iPlatformIcon expanded, iPlatformIcon collapsed) {
    expandedIcon    = expanded;
    collapsedIcon   = collapsed;
    indicatorWidth  = 0;
    indicatorHeight = 0;

    if (expanded != null) {
      indicatorWidth  = expanded.getIconWidth();
      indicatorHeight = expanded.getIconHeight();
    }

    if (collapsed != null) {
      indicatorWidth  = Math.max(indicatorWidth, collapsed.getIconWidth());
      indicatorHeight = Math.max(indicatorHeight, collapsed.getIconHeight());
    }
  }

  public void setTreeModel(aDataItemTreeModel treeModel) {
    this.treeModel = treeModel;
  }

  public void setTwistyMarginOfError(int twistyMarginOfError) {
    this.twistyMarginOfError = twistyMarginOfError;
    twistyMarginOfErrorSet   = true;
  }

  public iPlatformIcon getCollapsedIcon() {
    return collapsedIcon;
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

    return depth + (showRootHandles
                    ? indicatorWidth
                    : 0) + ICON_GAP;
  }

  @Override
  public int getIndicatorHeight() {
    return showRootHandles
           ? indicatorHeight
           : 0;
  }

  @Override
  public int getIndicatorWidth() {
    return showRootHandles
           ? indicatorWidth
           : 0;
  }

  public iTreeItem getTreeItem(RenderableDataItem item) {
    return (treeModel == null)
           ? null
           : treeModel.getTreeItem(item);
  }

  public aDataItemTreeModel getTreeModel() {
    return treeModel;
  }

  public int getTwistyMarginOfError() {
    return twistyMarginOfError;
  }

  public boolean isAutoScrollOnExpansion() {
    return autoScrollOnExpansion;
  }

  public boolean isExpandableStateLocked() {
    return expandableStateLocked;
  }

  public boolean isRootNodeCollapsible() {
    return rootNodeCollapsible;
  }

  public boolean isShowRoot() {
    return showRoot;
  }

  public boolean isShowRootHandles() {
    return showRootHandles;
  }

  public boolean isSingleClickToggle() {
    return singleClickToggle;
  }

  public boolean isToggleOnTwistyOnly() {
    return toggleOnTwistyOnly;
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

    CheckListManager cm = new CheckListManager(manageCheckboxSelection, (treeModel == null)
            ? -1
            : treeModel.getExpandableColumn());
    Adapter          a  = getAdapter();

    if (a instanceof iPlatformListDataModel) {
      cm.setListModel((iPlatformListDataModel) a);;
    }

    return cm;
  }

  protected boolean handleExpansion(iTreeItem ti, int position, boolean userDriven) {
    if ((expansionHandler != null) && (!expandableStateLocked ||!userDriven)) {
      if (!ti.isLeaf()) {
        int scrollTo = expansionHandler.toggleExpansion(this, ti, position);

        scrollTo = Math.min(scrollTo, getCount() - 1);

        if (scrollTo > 0) {
          if (isItemChecked(position)) {
            for (int i = position + 1; i <= scrollTo; i++) {
              if (!isItemChecked(i)) {
                //  setItemChecked(i, true);
              }
            }
          }

          if (autoScrollOnExpansion) {
            smoothScrollToPosition(scrollTo, position);
          }
        }

        return true;
      }
    }

    return false;
  }

  public void clearSelectionsInRange(int start, int len) {
    len += start;

    for (int i = start; i < len; i++) {
      if (isItemChecked(i)) {
        setItemChecked(i, false);
      }
    }
  }
  
  @Override
  protected void toggleCheckedState(int row) {
    RenderableDataItem item = listModel.get(row);
    iTreeItem          ti   = getTreeItem(item);

    if ((ti != null) &&!ti.isLeaf()) {
      boolean deep = checkListManager.toggleManagedRow(row, item);

      if (manageCheckboxSelection && ti.isExpanded()) {
        if (deep) {
          invalidate();
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
        invalidate();
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

  public boolean isParentItemsSelectable() {
    return parentItemsSelectable;
  }

  public void setParentItemsSelectable(boolean parentItemsSelectable) {
    this.parentItemsSelectable = parentItemsSelectable;
  }
}
