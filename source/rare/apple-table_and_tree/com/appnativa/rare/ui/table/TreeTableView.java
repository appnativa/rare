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

import com.appnativa.rare.platform.apple.ui.view.TreeView;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.tree.iTreeItem;
/*-[
 #import "RAREAPTableView.h"
 ]-*/
;

public class TreeTableView extends TreeView {
  protected boolean parentItemsSelectable = true;

  public TreeTableView() {
    super(createTreeTableProxy());
  }

  public void setParentItemsSelectable(boolean parentItemsSelectable) {
    this.parentItemsSelectable = parentItemsSelectable;
  }

  public boolean isParentItemsSelectable() {
    return parentItemsSelectable;
  }

  @Override
  protected void createHeader() {
    header = new TableHeader(this, getHeaderProxy());
  }

  protected native static Object createTreeTableProxy()
  /*-[
    RAREAPTableView* v=[[[RAREAPTableView alloc]init] configureForTreeTable];
    return v;
  ]-*/
  ;

  @Override
  protected void updateRenderInsetsForCheckBox(float left, float right) {}

  @Override
  protected boolean isSelectable(int row, RenderableDataItem item, iTreeItem ti) {
    if (!selectable || (row < 0)) {
      return false;
    }

    item = (ti == null)
           ? listModel.get(row)
           : ti.getData();

    if (item == null) {
      item = listModel.get(row);
    }

    if (!item.isSelectable()) {
      return false;
    }

    if (!parentItemsSelectable && (ti != null) &&!ti.isLeaf()) {
      return false;
    }

    return true;
  }

  @Override
  protected boolean isTable() {
    return true;
  }
}
