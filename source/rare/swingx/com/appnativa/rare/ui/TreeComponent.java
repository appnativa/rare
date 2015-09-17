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

package com.appnativa.rare.ui;

import com.appnativa.rare.platform.swing.ui.DataItemListModel;
import com.appnativa.rare.platform.swing.ui.view.TreeView;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.rare.ui.tree.aDataItemTreeModel;
import com.appnativa.rare.ui.tree.aTreeHandler;

/**
 * @author Don DeCoteau
 */
public class TreeComponent extends Container {
  iTreeHandler      treeHandler;
  DataItemTreeModel treeModel;

  /**
   * Creates a new instance
   *
   * @param list the list box
   */
  public TreeComponent(TreeView list) {
    super(list);

    DataItemTreeModel lm = new DataItemTreeModel();

    lm.setListModel(new DataItemListModel());
    treeModel = lm;
    lm.setTree(list);
    treeHandler = new aTreeHandler(list, lm) {
      @Override
      protected boolean hasListeners() {
        return listenerList != null;
      }
      @Override
      protected EventListenerList getEventListenerList() {
        return TreeComponent.this.getEventListenerList();
      }
      @Override
      public boolean isAutoScrollOnExpansion() {
        return ((TreeView) view).isAutoScrollOnExpansion();
      }
      @Override
      public void setAutoScrollOnExpansion(boolean autoScrollOnExpansion) {
        ((TreeView) view).setAutoScrollOnExpansion(autoScrollOnExpansion);
      }
    };
    list.setTreeModel(lm);
  }

  public iPlatformListDataModel getListModel() {
    return treeModel.getListModel();
  }

  public iTreeHandler getTreeHandler() {
    return treeHandler;
  }

  public aDataItemTreeModel getTreeModel() {
    return treeModel;
  }

  @Override
  protected void disposeEx() {
    ((TreeView) view).dispose();
  }

  public void setTreeIcons(iPlatformIcon expanded, iPlatformIcon collapsed) {
    ((TreeView) view).setTreeIcons(expanded, collapsed);
  }
}
