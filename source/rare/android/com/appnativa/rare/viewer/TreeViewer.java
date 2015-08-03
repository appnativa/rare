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

import android.widget.ListAdapter;
import android.widget.ListView;

import com.appnativa.rare.platform.android.ui.ListBoxListHandler;
import com.appnativa.rare.platform.android.ui.aDataItemListModelEx;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.spot.Tree;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iTreeHandler;
import com.appnativa.rare.ui.renderer.TreeItemRenderer;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.rare.ui.tree.TreeComponent;
import com.appnativa.rare.ui.tree.TreeListAdapter;
import com.appnativa.rare.ui.tree.TreeViewEx;

/**
 * A viewer that presents a hierarchical view of information as an outline
 * that can be collapsed and expanded.
 *
 * @author     Don DeCoteau
 */
public class TreeViewer extends aTreeViewer implements iTreeHandler {

  /**
   * Constructs a new instance
   *
   */
  public TreeViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param fv the parent
   */
  public TreeViewer(iContainer parent) {
    super(parent);
  }

  /**
   * Cancels tree editing (if editing)
   */
  public void cancelEditing() {
    if (treeHandler != null) {}
  }

  public void stopEditing() {}

  public void setAutoSizeRowsToFit(boolean autoSize) {}

  public void setSelectionMode(SelectionMode selectionMode) {
    super.setSelectionMode(selectionMode);

    boolean selectable = true;
    boolean invisible  = false;
    int     sm;

    switch(selectionMode) {
      case SINGLE :
        sm = ListView.CHOICE_MODE_SINGLE;

        break;

      case BLOCK :
      case MULTIPLE :
        sm               = ListView.CHOICE_MODE_MULTIPLE;
        selectAllAllowed = true;

        break;

      case INVISIBLE :
        sm        = ListView.CHOICE_MODE_SINGLE;
        invisible = true;

        break;

      default :
        sm         = ListView.CHOICE_MODE_NONE;
        selectable = false;
        invisible  = true;

        break;
    }

    ListView list = (ListView) getDataView();

    list.setChoiceMode(sm);

    if (invisible) {
      getItemRenderer().setSelectionPainted(false);
    }

    ((aDataItemListModelEx) listModel).setSelectable(selectable);
  }

  @Override
  public void setShowLastDivider(boolean show) {
    ((ListViewEx) getDataView()).setShowLastDivider(show);
  }

  @Override
  public void setTreeIcons(iPlatformIcon expanded, iPlatformIcon collapsed) {
    ((TreeComponent) dataComponent).setTreeIcons(expanded, collapsed);
  }

  /**
   * Configures the listbox
   *
   * @param cfg the listbox's configuration
   */
  protected void configureEx(Tree cfg) {
    super.configureEx(cfg);
    ((ListViewEx) getDataView()).viewConfigured();
  }

  protected void createModelAndComponents(Viewer vcfg) {
    Tree       cfg  = (Tree) vcfg;
    TreeViewEx tree = getAppContext().getComponentCreator().getTree(this, cfg);

    treeModel = new DataItemTreeModel();
    listModel = new TreeListAdapter(this, null);
    ((DataItemTreeModel) treeModel).setListModel((TreeListAdapter) listModel);
    dataComponent = formComponent = new TreeComponent(tree, (DataItemTreeModel) treeModel);
    treeHandler   = ((TreeComponent) dataComponent).getTreeHandler();
    formComponent = AndroidHelper.configureScrollPane(this, formComponent, tree, cfg.getScrollPane());
    tree.setAdapter((ListAdapter) listModel);
    listComponent = new ListBoxListHandler(tree, listModel);

    TreeItemRenderer lr = new TreeItemRenderer(this);

    lr.setConverter(treeModel);
    listModel.setItemRenderer(lr);
  }

  protected void setFlingThreshold(int threshold) {
    ((TreeViewEx) getDataView()).setFlingThreshold(threshold);
  }

  protected void setSelectFlinged(boolean select) {
    ((TreeViewEx) getDataView()).setSelectFlinged(true);
  }

  protected void setWholeViewFling(boolean wholeViewFling) {
    ((TreeViewEx) getDataView()).setWholeViewFling(wholeViewFling);
  }
}
