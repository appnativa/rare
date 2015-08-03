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

import android.content.Context;

import android.view.View;

import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import com.appnativa.rare.platform.android.ui.aDataItemListModelEx;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.renderer.TreeItemRenderer;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.widget.iWidget;

public class TreeListAdapter extends aDataItemListModelEx implements ListAdapter, SpinnerAdapter {

  /** Creates a new instance of DataItemListModel */
  public TreeListAdapter() {
    super();
  }

  /**
   * Creates a new instance of DataItemComboBoxModel
   * that is a copy of this one
   * @param m the model
   */
  public TreeListAdapter(TreeListAdapter m) {
    super(m);
  }

  /**
   * Creates a new instance of DataItemListModel
   *
   * @param widget {@inheritDoc}
   * @param column {@inheritDoc}
   */
  public TreeListAdapter(iWidget widget, Column column) {
    super(widget, column);
  }

  public TreeListAdapter copy() {
    return new TreeListAdapter(this);
  }

  @Override
  protected ListRow createListRow(Context context) {
    return new TreeRow(context);
  }

  protected iPlatformRenderingComponent createRenderingComponent(Context context, UIColor fg) {
    if (columnDescription != null) {
      iPlatformRenderingComponent rc = columnDescription.getCellRenderer();

      if (rc != null) {
        return (iPlatformRenderingComponent) rc.newCopy();
      }
    }

    TreeRow rc = new TreeRow(context);

    if (fg != null) {
      fg.setTextColor(rc);
    }

    UILabelRenderer lr = new UILabelRenderer(rc);

    return lr;
  }

  protected class TreeRow extends ListRow {
    public TreeRow(Context context) {
      super(context);
    }

    @Override
    public void prepareForReuse(View parent, int row, boolean selected) {
      super.prepareForReuse(parent, row, selected);

      if (parent instanceof TreeViewEx) {
        TreeViewEx         tv       = (TreeViewEx) parent;
        RenderableDataItem item     = get(row);
        iTreeItem          ti       = tv.getTreeItem(item);
        boolean            leaf     = true;
        int                depth    = 0;
        boolean            expanded = false;

        if (ti != null) {
          leaf     = ti.isLeaf();
          expanded = ti.isExpanded();
          depth    = ti.getDepth();
        }

        if (tv.isShowRootHandles()) {
          indicatorWidth  = tv.getIndicatorWidth();
          indicatorHeight = tv.getIndicatorHeight();

          if (!leaf) {
            if (expanded) {
              indicator = tv.getExpandedIcon();
            } else {
              indicator = tv.getCollapsedIcon();
            }
          }
        } else {
          indicatorWidth  = 0;
          indicatorHeight = 0;
          indicator       = null;
        }

        ((TreeItemRenderer) itemRenderer).setItemState(leaf, expanded);

        if (tv.isShowRoot()) {
          indent = depth * tv.getIndentBy();
        } else {
          indent = (depth - 1) * tv.getIndentBy();

          if (indent < 0) {
            indent = 0;
          }
        }
      }
    }
  }
}
