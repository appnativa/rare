/*
 * @(#)TreeViewer.java   2013-11-17
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import com.appnativa.rare.platform.swing.ui.ListBoxListHandler;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.TreeView;
import com.appnativa.rare.spot.Tree;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.TreeComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.renderer.TreeItemRenderer;

/**
 *  A viewer that presents a hierarchical view of information as an outline
 *  that can be collapsed and expanded.
 *
 *  @author     Don DeCoteau
 */
public class TreeViewer extends aTreeViewer {

  /**
   * Constructs a new instance
   */
  public TreeViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the parent
   */
  public TreeViewer(iContainer parent) {
    super(parent);
  }

  /**
   * Cancels tree editing (if editing)
   */
  @Override
  public void cancelEditing() {
    if (treeHandler != null) {}
  }

  public void stopEditing() {}

  @Override
  public void setAutoSizeRowsToFit(boolean autoSize) {
    TreeView v = (TreeView) getDataView();

    v.setAutoSizeRows(autoSize);
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    super.setSelectionMode(selectionMode);

    TreeView v = (TreeView) getDataView();

    v.setSelectionMode(selectionMode);
  }

  @Override
  public void setShowLastDivider(boolean show) {
    TreeView v = (TreeView) getDataView();

    v.setShowLastDivider(show);
  }

  @Override
  public void setTreeIcons(iPlatformIcon expanded, iPlatformIcon collapsed) {
    ((TreeComponent) dataComponent).setTreeIcons(expanded, collapsed);
  }

  @Override
  protected void createModelAndComponents(Viewer vcfg) {
    Tree     cfg  = (Tree) vcfg;
    TreeView tree = getAppContext().getComponentCreator().getTree(this, cfg);

    dataComponent = formComponent = new TreeComponent(tree);

    TreeComponent tc = (TreeComponent) dataComponent;

    treeHandler = tc.getTreeHandler();
    treeModel   = tc.getTreeModel();
    listModel   = tc.getListModel();
    listModel.setWidget(this);
    SwingHelper.configureScrollPane(this, tree, cfg.getScrollPane());
    tree.setItemRenderer(new TreeItemRenderer(this));
    listComponent = new ListBoxListHandler(tree, listModel);
  }

}
