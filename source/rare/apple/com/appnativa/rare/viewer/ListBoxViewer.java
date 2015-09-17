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
import com.appnativa.rare.platform.apple.ui.DataItemListModel;
import com.appnativa.rare.platform.apple.ui.ListBoxListHandler;
import com.appnativa.rare.platform.apple.ui.util.AppleHelper;
import com.appnativa.rare.platform.apple.ui.view.ListView;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ListComponent;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.iListView.EditingMode;
import com.appnativa.rare.ui.iToolBar;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilterableList;

import java.util.List;
import java.util.Map;

/**
 *  A widget that allows a user to select one or more choices from a
 *  scrollable list of items.
 *
 *  @author Don DeCoteau
 */
public class ListBoxViewer extends aListViewer {
  int selectedIndexes[];

  /**
   * Constructs a new instance
   */
  public ListBoxViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
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
    ((ListView) getDataView()).startEditing(actions, animate);
  }

  @Override
  public void stopEditing(boolean animate) {
    ((ListView) getDataView()).stopEditing(animate);
  }

  @Override
  public void setAutoSizeRowsToFit(boolean autoSize) {
    ((ListView) getDataView()).setAutoSizeRows(autoSize);
  }

  @Override
  public void setEditModeNotifier(iFunctionCallback cb) {
    ((ListView) getDataView()).setEditModeNotifier(cb);
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

    if ((selectionMode == SelectionMode.NONE) || (selectionMode == SelectionMode.INVISIBLE)) {
      v.getItemRenderer().setSelectionPainted(false);
    }
  }

  @Override
  public void setShowLastDivider(boolean show) {
    ((ListView) getDataView()).setShowLastDivider(show);
  }

  @Override
  public int getEditingRow() {
    ListView v = (ListView) getDataView();

    return v.getEditingRow();
  }

  @Override
  public int getLastEditedRow() {
    ListView v = (ListView) getDataView();

    return v.getLastEditedRow();
  }

  static void handleCustomProperties(ListView v, Widget cfg, Map<String, Object> properties) {
    String s = (String) properties.get("cellStyle");

    if (s != null) {
      v.setCellStyle(s);
    }

    s = (String) properties.get("accessoryType");

    if (s != null) {
      v.setAccessoryType(s, false);
    }

    s = (String) properties.get("separatorStyle");

    if (s != null) {
      v.setSeparatorStyle(s);
    }

    s = (String) properties.get("editingAccessoryType");

    if (s != null) {
      v.setAccessoryType(s, true);
    }

    s = (String) properties.get("allowsSelectionDuringEditing");

    if ((s != null) && s.equals("true")) {
      v.setAllowsSelectionDuringEditing(true);
    }
  }

  @Override
  protected RenderableDataItem createDefaultSectionProrotype() {
    if (Platform.isIOS()) {
      return null;
    }

    return super.createDefaultSectionProrotype();
  }

  @Override
  protected void createModelAndComponents(Viewer vcfg) {
    ListBox cfg = (ListBox) vcfg;

    listModel = new DataItemListModel();

    ListView list = getAppContext().getComponentCreator().getList(this, cfg);

    dataComponent = formComponent = new ListComponent(list);
    formComponent = AppleHelper.configureScrollPane(this, formComponent, list, cfg.getScrollPane());

    ListItemRenderer lr = new ListItemRenderer();

    list.setItemRenderer(lr);
    listComponent = new ListBoxListHandler(list, listModel);
    list.setListModel(listModel);

    iToolBar tb = createEditingToolbarIfNecessary(cfg);

    if (tb != null) {
      list.setEditingToolbar(tb);
    }
  }

  @Override
  protected void handleCustomProperties(Widget cfg, Map<String, Object> properties) {
    super.handleCustomProperties(cfg, properties);
    handleCustomProperties((ListView) getDataView(), cfg, properties);
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
    if( Platform.getUIDefaults().getBoolean("Rare.Table.repaintOnRotation", true)){
      ((ListView) getDataView()).repaintVisibleRows();
    }
  }

  @Override
  protected List<RenderableDataItem> updateListWithFilteringIndex(FilterableList<RenderableDataItem> list) {
    if (Platform.isIOS()) {
      ((ListView) getDataView()).setSectionIndex(list.getFilteringIndex(), list.size(), prototypeSectionItem);

      return list;
    }

    return super.updateListWithFilteringIndex(list);
  }

  @Override
  protected void setEditingMode(EditingMode mode, boolean autoEnd, boolean allowSwiping) {
    ListView v = (ListView) getDataView();

    v.setEditingMode(mode);
    v.setAutoEndEditing(autoEnd);
    v.setEditingSwipingAllowed(allowSwiping);
  }

  @Override
  protected void setFlingThreshold(int threshold) {
    ((ListView) getDataView()).setFlingThreshold(threshold);
  }

  @Override
  protected void setSelectFlinged(boolean select) {
    ((ListView) getDataView()).setSelectFlinged(select);
  }

  @Override
  protected void setWholeViewFling(boolean wholeViewFling) {
    ((ListView) getDataView()).setWholeViewFling(wholeViewFling);
  }
}
