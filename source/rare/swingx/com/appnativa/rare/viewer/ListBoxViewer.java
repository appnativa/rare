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

import javax.swing.ActionMap;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.platform.swing.ui.DataItemListModel;
import com.appnativa.rare.platform.swing.ui.EditableListBoxListHandler;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.ListView;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScrollPanePanel;
import com.appnativa.rare.ui.iListView.EditingMode;
import com.appnativa.rare.ui.iToolBar;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.widget.iWidget;

/**
 *  A widget that allows a user to select one or more choices from a
 *  scrollable list of items.
 *
 *  @author Don DeCoteau
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
   * @param parent the parent
   */
  public ListBoxViewer(iContainer parent) {
    super(parent);
    widgetType             = WidgetType.ListBox;
    initiallySelectedIndex = -1;
  }

  @Override
  public void configure(Viewer vcfg) {
    configureEx((ListBox) vcfg);
    handleDataURL(vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Creates a new listbox widget
   *
   * @param parent the parent
   * @param cfg the configuration
   *
   * @return the listbox widget
   */
  public static ListBoxViewer create(iContainer parent, ListBox cfg) {
    ListBoxViewer widget = new ListBoxViewer(parent);

    widget.configure(cfg);

    return widget;
  }

  @Override
  public void dispose() {
    if (!isDisposable()) {
      return;
    }

    super.dispose();

    if (listModel != null) {
      listModel.dispose();
    }

    listModel = null;
  }

  /**
   * Notifies the list that the contents of the specified row has changed
   *
   * @param index the index of the row that changed
   */
  @Override
  public void repaintRow(int index) {
    if (listModel != null) {
      listModel.rowChanged(index);
    }
  }

  /**
   * Notifies the list that the contents of the specified row has changed
   *
   * @param item the instance of the row that changed
   */
  @Override
  public void rowChanged(RenderableDataItem item) {
    if (listModel != null) {
      listModel.rowChanged(item);
    }
  }

  /**
   * Notifies the list that the contents of the specified range of rows have changed
   *
   * @param firstRow the first row
   * @param lastRow the last row
   */
  @Override
  public void rowsChanged(int firstRow, int lastRow) {
    listModel.rowsChanged(firstRow, lastRow);
  }

  @Override
  public void setAutoSizeRowsToFit(boolean autoSize) {
    ListView v = (ListView) getDataView();

    v.setAutoSizeRows(autoSize);
  }
  
  @Override
  public void setEditModeListener(iExpansionListener l) {
    ListView v = (ListView) getDataView();
    v.setEditModeListener(l);
  }
  
  @Override
  public void setRowEditModeListener(iExpansionListener l) {
    ListView v = (ListView) getDataView();
    v.setRowEditModeListener(l);
  }

  @Override
  public void setItemDescription(Column itemDescription) {
    super.setItemDescription(itemDescription);

    if (itemDescription != null) {
      ((ListView) getDataView()).getItemRenderer().setItemDescription(itemDescription);

      if (itemDescription.getFont() != null) {
        dataComponent.setFont(itemDescription.getFont());
        formComponent.setFont(itemDescription.getFont());
      }
    }
  }

  @Override
  public void setRowEditingWidget(iWidget widget, boolean centerVertically) {
    super.setRowEditingWidget(widget, centerVertically);
    ListView v = (ListView) getDataView();
    v.setRowEditingComponent(widget.getContainerComponent(), centerVertically);
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    super.setSelectionMode(selectionMode);

    ListView v = (ListView) getDataView();

    v.setSelectionMode(selectionMode);
  }

  @Override
  public void setShowLastDivider(boolean show) {
    ListView v = (ListView) getDataView();

    v.setShowLastDivider(show);
  }

  @Override
  protected void createModelAndComponents(Viewer vcfg) {
    ListBox cfg = (ListBox) vcfg;

    listModel = new DataItemListModel();

    ListView  list = getAppContext().getComponentCreator().getList(this, cfg);
    ActionMap am   = list.getList().getActionMap();

    am.put("Rare.origSelectNextRow", am.get("selectNextRow"));
    am.put("Rare.origSelectPreviousRow", am.get("selectPreviousRow"));
    am.put("selectNextRow", ActionHelper.selectNextRow);
    am.put("selectPreviousRow", ActionHelper.selectPreviousRow);
    formComponent = dataComponent = new ScrollPanePanel(list);
    formComponent=SwingHelper.configureScrollPane(this,formComponent, list, cfg.getScrollPane());
    list.setItemRenderer(new ListItemRenderer(list, true));
    listComponent = new EditableListBoxListHandler(list, listModel);
    list.setListModel(listModel);

    iToolBar tb = createEditingToolbarIfNecessary(cfg);

    if (tb != null) {
      list.setEditModeToolBar(tb);
    }

    registerWithWidget(list.getListComponent());
  }

  @Override
  protected void setEditingMode(EditingMode mode, boolean autoEnd, boolean allowSwiping) {
    ListView v = (ListView) getDataView();

    v.setAutoEndEditing(autoEnd);
    v.setEditingMode(mode);
    v.setEditingSwipingAllowed(allowSwiping);
  }
}
