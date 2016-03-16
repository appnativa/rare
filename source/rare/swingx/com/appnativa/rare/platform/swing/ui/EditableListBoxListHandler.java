package com.appnativa.rare.platform.swing.ui;

import com.appnativa.rare.platform.swing.ui.view.ListView;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.iEditableListHandler;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iToolBar;
import com.appnativa.rare.ui.event.iExpansionListener;

public class EditableListBoxListHandler extends aPlatformListHandler implements iEditableListHandler {
  ListView listView;

  public EditableListBoxListHandler(ListView view) {
    super(view);
    listView = view;
  }

  public EditableListBoxListHandler(ListView view, iPlatformListDataModel model) {
    super(view, model);
    listView = view;
  }

  public int getEditModeMarkCount() {
    return listView.getEditModeMarkCount();
  }

  public int getLastEditedRow() {
    return listView.getLastEditedRow();
  }

  public boolean isEditing() {
    return listView.isEditing();
  }

  public void clearEditModeMarks() {
    listView.clearEditModeMarks();
  }

  public boolean isEditModeItemMarked(int index) {
    return listView.isEditModeItemMarked(index);
  }

  public void toggleEditModeItemMark(int index) {
    listView.toggleEditModeItemMark(index);
  }

  public void setEditModeItemMark(int index, boolean mark) {
    listView.setEditModeItemMark(index, mark);
  }

  public void setEditModeAllItemMarks(boolean mark) {
    listView.setEditModeAllItemMarks(mark);
  }

  public int[] getEditModeMarkedIndices() {
    return listView.getEditModeMarkedIndices();
  }

  public RenderableDataItem[] getEditModeMarkedItems() {
    return listView.getEditModeMarkedItems();
  }

  public int getEditingRow() {
    return listView.getEditingRow();
  }

  public void startEditing(boolean animate, UIAction... actions) {
    listView.startEditing(animate, actions);
  }

  public void stopEditing(boolean animate) {
    listView.stopEditing(animate);
  }

  public void setEditModeListener(iExpansionListener l) {
    listView.setEditModeListener(l);
  }

  public void setRowEditingComponent(iPlatformComponent c, boolean centerVertically) {
    listView.setRowEditingComponent(c, centerVertically);
  }

  public void setRowEditModeListener(iExpansionListener l) {
    listView.setRowEditModeListener(l);
  }

  @Override
  public iToolBar getEditModeToolBar() {
    return listView.getEditModeToolBar();
  }

  @Override
  public void setEditModeToolBar(iToolBar tb) {
    listView.setEditModeToolBar(tb);
  }
  
}
