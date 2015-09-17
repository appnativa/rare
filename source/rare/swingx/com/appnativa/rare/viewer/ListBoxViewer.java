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
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.platform.swing.ui.DataItemListModel;
import com.appnativa.rare.platform.swing.ui.ListBoxListHandler;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.ListView;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScrollPanePanel;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UISelectionModelGroup;
import com.appnativa.rare.ui.iListView.EditingMode;
import com.appnativa.rare.ui.iToolBar;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.widget.iWidget;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JList;

/**
 *  A widget that allows a user to select one or more choices from a
 *  scrollable list of items.
 *
 *  @author Don DeCoteau
 */
public class ListBoxViewer extends aListViewer {

  /** action to select the next row */
  protected static Action selectNextRow = new AbstractAction("selectNextRow") {
    @Override
    public void actionPerformed(ActionEvent e) {
      Object o = (e == null)
                 ? null
                 : e.getSource();

      if (o instanceof JList) {
        JList         list = (JList) o;
        int           row  = list.getLeadSelectionIndex();
        ListBoxViewer v    = (ListBoxViewer) Platform.getWidgetForComponent(list);

        if ((v != null) && (v.selectionModelGroup != null)) {
          v.selectionModelGroup.selectNextRow(row, list.getSelectionModel());
        } else {
          UISelectionModelGroup.selectNextRow(v, row);
        }
      }
    }
  };

  /** action to select the previous row */
  protected static Action selectPreviousRow = new AbstractAction("selectPreviousRow") {
    @Override
    public void actionPerformed(ActionEvent e) {
      Object o = (e == null)
                 ? null
                 : e.getSource();

      if (o instanceof JList) {
        JList         list = (JList) o;
        int           row  = list.getLeadSelectionIndex();
        ListBoxViewer v    = (ListBoxViewer) Platform.getWidgetForComponent(list);

        if ((v != null) && (v.selectionModelGroup != null)) {
          v.selectionModelGroup.selectPreviousRow(row, list.getSelectionModel());
        } else {
          UISelectionModelGroup.selectPreviousRow(v, row);
        }
      }
    }
  };

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
  public void rowChanged(int index) {
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
  public void startEditing(boolean animate, UIAction... actions) {
    ListView v = (ListView) getDataView();

    v.startEditing(actions, animate, draggingAllowed);
  }

  @Override
  public void stopEditing(boolean animate) {
    ListView v = (ListView) getDataView();

    v.stopEditing(animate);
  }

  @Override
  public void setAutoSizeRowsToFit(boolean autoSize) {
    ListView v = (ListView) getDataView();

    v.setAutoSizeRows(autoSize);
  }

  @Override
  public void setEditModeNotifier(iFunctionCallback cb) {
    ListView v = (ListView) getDataView();

    v.setEditModeNotifier(cb);
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
  public int getEditingRow() {
    return ((ListView) getDataView()).getEditingRow();
  }

  @Override
  public int getLastEditedRow() {
    return ((ListView) getDataView()).getLastEditedRow();
  }

  @Override
  public boolean isEditing() {
    ListView v = (ListView) getDataView();

    return v.isEditing();
  }

  @Override
  protected void createModelAndComponents(Viewer vcfg) {
    ListBox cfg = (ListBox) vcfg;

    listModel = new DataItemListModel();

    ListView  list = getAppContext().getComponentCreator().getList(this, cfg);
    ActionMap am   = list.getList().getActionMap();

    am.put("Rare.origSelectNextRow", am.get("selectNextRow"));
    am.put("Rare.origSelectPreviousRow", am.get("selectPreviousRow"));
    am.put("selectNextRow", selectNextRow);
    am.put("selectPreviousRow", selectPreviousRow);
    dataComponent = formComponent = new ScrollPanePanel(list);
    SwingHelper.configureScrollPane(this, list, cfg.getScrollPane());
    list.setItemRenderer(new ListItemRenderer(list, true));
    listComponent = new ListBoxListHandler(list, listModel);
    list.setListModel(listModel);

    iToolBar tb = createEditingToolbarIfNecessary(cfg);

    if (tb != null) {
      list.setEditingToolbar(tb);
    }
  }

  @Override
  protected void setEditingMode(EditingMode mode, boolean autoEnd, boolean allowSwiping) {
    ListView v = (ListView) getDataView();

    v.setAutoEndEditing(autoEnd);
    v.setEditingMode(mode);
    v.setEditingSwipingAllowed(allowSwiping);
  }
}
