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

import java.util.Comparator;
import java.util.List;

import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.android.ui.BaseAdapterListModel;
import com.appnativa.rare.platform.android.ui.DataItemListModel;
import com.appnativa.rare.platform.android.ui.ListBoxListHandler;
import com.appnativa.rare.platform.android.ui.aAdapterListHandler;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.ListComponent;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.iListView.EditingMode;
import com.appnativa.rare.ui.iToolBar;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.rare.widget.iWidget;

/**
 * A widget that allows a user to select one or more choices from a
 * scrollable list of items.
 *
 * @author Don DeCoteau
 */
public class ListBoxViewer extends aListViewer {

  /**
   * Constructs a new instance
   */
  public ListBoxViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param fv the widget's parent
   */
  public ListBoxViewer(iContainer parent) {
    super(parent);
    widgetType             = WidgetType.ListBox;
    initiallySelectedIndex = -1;
  }

  @Override
  public void setEditModeListener(iExpansionListener l) {
    ListViewEx v = (ListViewEx) getDataView();
    v.setEditModeListener(l);
  }
  
  @Override
  public void setRowEditModeListener(iExpansionListener l) {
    ListViewEx v = (ListViewEx) getDataView();
    v.setRowEditModeListener(l);
  }

  @Override
  public void configure(Viewer vcfg) {
    configureEx((ListBox) vcfg);
    ((ListViewEx) getDataView()).viewConfigured();
    handleDataURL(vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Sorts the widget's data
   * Sorts without triggering any events.
   *
   * @param descending true to sort in descending order; false otherwise
   */
  public List<RenderableDataItem> sortEx(boolean descending) {
    sortEx(descending
           ? SubItemComparator.getReverseOrderComparatorInstance()
           : null);

    return this;
  }

  /**
   * Sorts the widget's data.
   * Sorts without triggering any events.
   *
   * @param comparator the comparator to use for the sort
   */
  public List<RenderableDataItem> sortEx(Comparator comparator) {
    listModel.sortEx(comparator);

    return this;
  }

  @Override
  public void startEditing(boolean animate, UIAction... actions) {
    ((ListViewEx) getDataView()).startEditing(actions, animate);
  }

  @Override
  public void stopEditing(boolean animate) {
    ((ListViewEx) getDataView()).stopEditing(animate);
  }

  @Override
  public void setAutoSizeRowsToFit(boolean autoSize) {
    listModel.setAutoSizeRows(autoSize);
  }

  @Override
  public void setRowEditingWidget(iWidget widget, boolean centerVertically) {
    super.setRowEditingWidget(widget, centerVertically);
    if (getDataView() instanceof ListViewEx) {
      ((ListViewEx) getDataView()).setRowEditingComponent(widget.getContainerComponent(), centerVertically);
    }
  }

  @Override
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

    listModel.setSelectable(selectable);
  }

  @Override
  public void setShowLastDivider(boolean show) {
    ((ListViewEx) getDataView()).setShowLastDivider(show);
  }

  /**
   * Creates the List for use by the viewer
   *
   * @param view the list view
   * @param cfg the list configuration
   * @return  the list for use by the viewer
   */
  protected aAdapterListHandler createListComponent(AbsListView view, ListBox cfg) {
    return new ListBoxListHandler((ListViewEx) view, listModel);
  }

  /**
   * Creates the view for use by the viewer
   *
   * @param cfg the list configuration
   *
   * @return  the view for use by the viewer
   */
  protected ListViewEx createListView(ListBox cfg) {
    if (cfg.selectionMode.intValue() == ListBox.CSelectionMode.multiple) {
      selectAllAllowed = true;
    }

    return getAppContext().getComponentCreator().getList(this, cfg);
  }

  @Override
  protected void createModelAndComponents(Viewer vcfg) {
    ListBox cfg = (ListBox) vcfg;

    listModel = new DataItemListModel();

    ListViewEx list = createListView(cfg);

    dataComponent = formComponent = new ListComponent(list);
    formComponent = AndroidHelper.configureScrollPane(this, formComponent, list, cfg.getScrollPane());

    if (cfg.indexForFiltering.booleanValue()) {
      list.setFastScrollEnabled(true);
      list.setFastScrollAlwaysVisible(true);
      listModel = new BaseAdapterListModel((DataItemListModel) listModel);
    }

    listComponent = createListComponent(list, cfg);

    ListItemRenderer lr = new ListItemRenderer(false);

    listModel.setItemRenderer(lr);

    if (list instanceof ListView) {
      ((ListViewEx) list).setAdapter((ListAdapter) listModel);

      iToolBar tb = createEditingToolbarIfNecessary(cfg);

      if (tb != null) {
        ((ListViewEx) list).setEditingToolbar(tb);
      }
    }
  }

  @Override
  protected void setEditingMode(EditingMode mode, boolean autoEnd, boolean allowSwiping) {
    if (getDataView() instanceof ListViewEx) {
      ListViewEx v = (ListViewEx) getDataView();

      v.setEditingMode(mode);
      v.setAutoEndEditing(autoEnd);
      v.setEditingSwipingAllowed(allowSwiping);
    }
  }

  @Override
  protected void setFlingThreshold(int threshold) {
    if (getDataView() instanceof ListViewEx) {
      ((ListViewEx) getDataView()).setFlingThreshold(threshold);
    }
  }

  @Override
  protected void setSelectFlinged(boolean select) {
    if (getDataView() instanceof ListViewEx) {
      ((ListViewEx) getDataView()).setSelectFlinged(select);
    }
  }

  @Override
  public int getEditingRow() {
    if (getDataView() instanceof ListViewEx) {
      return ((ListViewEx) getDataView()).getEditingRow();
    }

    return -1;
  }

  @Override
  public int getLastEditedRow() {
    return ((ListViewEx) getDataView()).getLastEditedRow();
  }

  @Override
  protected void setWholeViewFling(boolean wholeViewFling) {
    if (getDataView() instanceof ListViewEx) {
      ((ListViewEx) getDataView()).setWholeViewFling(wholeViewFling);
    }
  }
}
