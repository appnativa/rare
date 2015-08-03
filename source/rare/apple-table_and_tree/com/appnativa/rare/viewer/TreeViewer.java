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

package com.appnativa.rare.viewer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.apple.ui.ListBoxListHandler;
import com.appnativa.rare.platform.apple.ui.util.AppleHelper;
import com.appnativa.rare.platform.apple.ui.view.ListView;
import com.appnativa.rare.platform.apple.ui.view.TreeView;
import com.appnativa.rare.spot.Tree;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.TreeComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.renderer.TreeItemRenderer;

import java.util.Map;

/**
 *  A viewer that presents a hierarchical view of information as an outline
 *  that can be collapsed and expanded.
 *
 *  @author     Don DeCoteau
 */
public class TreeViewer extends aTreeViewer {
  private int[] selectedIndexes;

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

  @Override
  public void setAutoSizeRowsToFit(boolean autoSize) {
    ((TreeComponent) dataComponent).setAutoSizeRows(autoSize);
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    super.setSelectionMode(selectionMode);

    TreeView v = (TreeView) getDataView();

    v.setSelectionMode(selectionMode);

    if ((selectionMode == SelectionMode.NONE) || (selectionMode == SelectionMode.INVISIBLE)) {
      v.getItemRenderer().setSelectionPainted(false);
    }
  }

  @Override
  public void setShowLastDivider(boolean show) {
    ((TreeView) getDataView()).setShowLastDivider(show);
  }

  @Override
  public void setTreeIcons(iPlatformIcon expanded, iPlatformIcon collapsed) {
    ((TreeComponent) dataComponent).setTreeIcons(expanded, collapsed);
  }

  @Override
  protected void createModelAndComponents(Viewer vcfg) {
    Tree     cfg  = (Tree) vcfg;
    TreeView tree = new TreeView();

    dataComponent = formComponent = new TreeComponent(tree);

    TreeComponent tc = (TreeComponent) dataComponent;

    treeHandler = tc.getTreeHandler();
    treeModel   = tc.getTreeModel();
    listModel   = tc.getListModel();
    listModel.setWidget(this);
    formComponent = AppleHelper.configureScrollPane(this, formComponent, tree, cfg.getScrollPane());

    TreeItemRenderer lr = new TreeItemRenderer(this);

    tree.setItemRenderer(lr);
    listComponent = new ListBoxListHandler(tree, listModel);
  }

  @Override
  protected void handleCustomProperties(Widget cfg, Map<String, Object> properties) {
    super.handleCustomProperties(cfg, properties);
    ListBoxViewer.handleCustomProperties((ListView) getDataView(), cfg, properties);
  }

  @Override
  protected void handleViewerConfigurationChanged(boolean reset) {
    int n[] = getSelectedIndexes();

    if ((n == null) || (n.length == 0)) {
      if ((selectedIndexes != null) && (selectedIndexes.length > 0)) {
        boolean enabled = isChangeEventsEnabled();

        setChangeEventsEnabled(false);
        setSelectedIndexes(selectedIndexes);
        setChangeEventsEnabled(enabled);
      }
    }

    selectedIndexes = null;
    super.handleViewerConfigurationChanged(reset);
  }

  @Override
  protected void handleViewerConfigurationWillChange(Object newConfig) {
    selectedIndexes = getSelectedIndexes();
    super.handleViewerConfigurationWillChange(newConfig);
  }

  protected static void registerForUse() {
    Platform.getAppContext().registerWidgetClass(Platform.getSPOTName(Tree.class), TreeViewer.class);
  }

  @Override
  protected void setFlingThreshold(int threshold) {
    ((TreeView) getDataView()).setFlingThreshold(threshold);
  }

  @Override
  protected void setSelectFlinged(boolean select) {
    ((TreeView) getDataView()).setSelectFlinged(select);
  }

  @Override
  protected void setWholeViewFling(boolean wholeViewFling) {
    ((TreeView) getDataView()).setWholeViewFling(wholeViewFling);
  }
}
