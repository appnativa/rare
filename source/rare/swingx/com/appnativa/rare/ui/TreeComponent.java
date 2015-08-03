/*
 * @(#)TreeComponent.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
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
