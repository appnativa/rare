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

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.appnativa.rare.ui.CheckListManager;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.renderer.aTreeItemRenderer;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.rare.ui.tree.aDataItemTreeModel;
import com.appnativa.rare.ui.tree.iExpansionHandler;
import com.appnativa.rare.ui.tree.iTree;
import com.appnativa.rare.ui.tree.iTreeItem;

public class TreeTableView extends TableView implements iTree {
  protected int                indentBy              = ScreenUtils.platformPixels(16);
  protected boolean            showRootHandles       = true;
  protected boolean            showRoot              = true;
  protected boolean            autoScrollOnExpansion = true;
  protected int                twistyMarginOfError   = ScreenUtils.PLATFORM_PIXELS_4;
  protected iPlatformIcon      collapsedIcon;
  protected boolean            expandableStateLocked;
  protected iPlatformIcon      expandedIcon;
  protected iExpansionHandler  expansionHandler;
  protected boolean            indentBySet;
  protected boolean            rootNodeCollapsible;
  protected boolean            singleClickToggle;
  protected boolean            toggleOnTwistyOnly;
  protected aDataItemTreeModel treeModel;
  private boolean              manageSelections      = true;
  private boolean              parentItemsSelectable = true;

  public TreeTableView() {
    super();
    setMouseOverideListener(createOverrideListener());
  }

  @Override
  public boolean canChangeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
    iTreeItem ti = getTreeItem(getItemAt(rowIndex));

    if (ti == null) {
      return super.canChangeSelection(rowIndex, columnIndex, toggle, extend);
    }

    if (!ti.getData().isSelectable()) {
      return false;
    }

    return parentItemsSelectable || ti.isLeaf();
  }

  @Override
  public void dispose() {
    super.dispose();

    if (treeModel != null) {
      treeModel.dispose();
    }

    treeModel        = null;
    expansionHandler = null;
    expandedIcon     = null;
    collapsedIcon    = null;
  }

  @Override
  public iPlatformRenderingComponent prepareRendererForReuse(iPlatformRenderingComponent rc, int row, int col) {
    rc = super.prepareRendererForReuse(rc, row, col);

    iPlatformIcon indicator = null;
    int           depth     = 0;
    boolean       empty     = row >= getRowCount();

    if (!empty && (col < 1)) {
      RenderableDataItem item     = getItemAt(row);
      iTreeItem          ti       = getTreeItem(item);
      boolean            expanded = false;
      boolean            leaf     = true;

      if (ti != null) {
        depth = ti.getDepth();

        if (!showRoot) {
          depth = Math.max(depth - 1, 0);
        }

        expanded = ti.isExpanded();
        leaf     = ti.isLeaf();
      }

      ((aTreeItemRenderer) itemRenderer).setItemState(leaf, expanded);
      depth *= indentBy;

      if (showRootHandles || (treeModel.getRoot() != item)) {
        if (!leaf) {
          indicator = expanded
                      ? expandedIcon
                      : collapsedIcon;
        }
      }
    }

    setTreeInfoForRow(rc, indicator, depth);

    return rc;
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
  public void setIndentBy(int indent) {
    indentBy    = indent;
    indentBySet = true;
  }

  public void setManageChildNodeSelections(boolean manage) {
    manageSelections = manage;

    if (checkListManager != null) {
      checkListManager.setManageSelections(manage);
    }
  }

  public void setParentItemsSelectable(boolean parentItemsSelectable) {
    this.parentItemsSelectable = parentItemsSelectable;
  }

  @Override
  public void setRootNodeCollapsible(boolean collapsible) {
    rootNodeCollapsible = collapsible;
  }

  @Override
  public void setShowRoot(boolean show) {
    showRoot = show;
  }

  @Override
  public void setShowRootHandles(boolean show) {
    if (showRootHandles != show) {
      showRootHandles = show;

      if (!show) {
        indicatorWidth  = 0;
        indicatorHeight = 0;
        calculateOffset();
      } else {
        setTreeIcons(expandedIcon, collapsedIcon);
      }
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

    calculateOffset();
  }

  public void setTreeModel(DataItemTreeModel treeModel) {
    this.treeModel = treeModel;
  }

  @Override
  public void setTwistyMarginOfError(int twistyMarginOfError) {
    this.twistyMarginOfError = twistyMarginOfError;
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

  public boolean isParentItemsSelectable() {
    return parentItemsSelectable;
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
    RenderableDataItem item = getItemAt(row);
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

    float sy = (height - indicatorHeight) / 2;

    if ((y < sy - INDICATOR_SLOP) || (y > (sy + indicatorHeight + INDICATOR_SLOP))) {
      return false;
    }

    if ((x > sx - twistyMarginOfError) && (x < (sx + indicatorWidth + twistyMarginOfError))) {
      return handleExpansion(ti, row, true);
    }

    return false;
  }

  protected CheckListManager createCheckListManager() {
    return new CheckListManager(manageSelections, (treeModel == null)
            ? -1
            : treeModel.getExpandableColumn());
  }

  protected MouseListener createOverrideListener() {
    return new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (!isEnabled()) {
          return;
        }

        int       row = getRowAtPoint(e.getPoint());
        Rectangle r   = (row == -1)
                        ? null
                        : getCellBounds(row);

        if (r != null) {
          if (checkForCellHotspot(row, e.getX(), e.getY() - r.y, r.width, r.height)) {
            e.consume();
          }
        }
      }
    };
  }

  protected int geIndent(int row) {
    RenderableDataItem item = getItemAt(row);
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

  protected boolean handleExpansion(iTreeItem ti, int position, boolean userDriven) {
    if ((expansionHandler != null) && (!expandableStateLocked ||!userDriven)) {
      if (!ti.isLeaf()) {
        int scrollTo = expansionHandler.toggleExpansion(this, ti, position);

        scrollTo = Math.min(scrollTo, getRowCount() - 1);

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

  protected boolean handledExpansion(int index, float x, float y) {
    RenderableDataItem item = getItemAt(index);
    iTreeItem          ti   = treeModel.getTreeItem(item);

    if ((ti == null) || ti.isLeaf()) {
      return false;
    }

    if (!toggleOnTwistyOnly) {
      return handleExpansion(ti, index, true);
    }

    int depth = ti.getDepth();

    if (!showRoot) {
      depth = Math.max(depth - 1, 0);
    }

    depth *= indentBy;
    depth += twistyMarginOfError + leftOffset + indicatorWidth;

    if (x < depth) {
      return handleExpansion(ti, index, true);
    }

    return false;
  }

  protected void toggleCheckedState(int row) {
    RenderableDataItem item = getItemAt(row);
    iTreeItem          ti   = getTreeItem(item);

    if ((ti != null) &&!ti.isLeaf()) {
      boolean deep = checkListManager.toggleManagedRow(row, item);

      if (manageSelections && ti.isExpanded()) {
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

        if (manageSelections) {
          RenderableDataItem parent = item.getParentItem();

          if (parent != null) {
            repaintRow(parent);
          }
        }
      }
    }
  }

  protected void setLinkedSelectionChecked(int row, boolean checked) {
    iTreeItem ti = getTreeItem(getItemAt(row));

    if ((ti == null) || ti.isLeaf()) {
      setRowChecked(row, checked);
    }
  }
}
