package com.appnativa.rare.ui;

import com.appnativa.rare.ui.event.iExpansionListener;

public interface iEditableListHandler {

  /**
   * Get the count of marked items for a list in edit mode
   * @return the count of marked items for a list in edit mode
   */
  public int getEditModeMarkCount();

  /**
   * Gets the last row that was edited. This value is preserver as long as
   * changes have not been made to the data model. Use this method when you
   * actions that are triggered from a custom row editor
   *
   * @return the last row that was edited or the current row being edited (of a
   *         row is currently being edited)
   */
  public int getLastEditedRow();

  /**
   * Gets whether the list is currently being edited
   * @return true if it is; false otherwise
   */
  boolean isEditing();

  /**
   * Clears  the selection marks for all list items
   * when in edit mode
   */
  public void clearEditModeMarks();

  /**
   * Returns whether an item is marked
   * when in edit mode
   *
   * @param index the index of the item
   */
  public boolean isEditModeItemMarked(int index);

  /**
   * Toggles and items marked state
   * when in edit mode
   *
   * @param index the index of the item
   */
  public void toggleEditModeItemMark(int index);

  /**
   * Sets the marked state of an item
   * when in edit mode
   *
   * @param index the index of the item
   * @param mark true to mark the item ; false to un-mark the item
   */
  public void setEditModeItemMark(int index, boolean mark);

  /**
   * Sets the marked state of all items
   * when in edit mode
   *
   * @param mark true to mark the items; false to un-mark the items
   */
  public void setEditModeAllItemMarks(boolean mark);

  /**
   * Gets the indices of the items that are marked when the list is in edit mode
   * @return the indices of the items that are marked when the list is in edit mode
   */
  public int[] getEditModeMarkedIndices();

  /**
   * Gets the items that are marked when the list is in edit mode
   * @return the items that are marked when the list is in edit mode
   */
  public RenderableDataItem[] getEditModeMarkedItems();

  /**
   * Gets the current row being edited
   *
   * @return the current row being edited or -1 if no row is being edited
   */
  public int getEditingRow();

  /**
   * Starts editing of the lists allowing items to be added, deleted, or
   * reordered (based on the configured support for these actions)
   *
   * @param actions
   *          a set of custom actions for the editing toolbar. Use null to get
   *          the default toolbar. Use and empty array to have no toolbar
   * @param animate
   *          true to animate (if supported on the platform); false otherwise
   */
  public void startEditing(boolean animate, UIAction... actions);

  /**
   * Stop the list or row editing
   *
   * @param animate
   *          true to animate (if supported on the platform); false otherwise
   */
  public void stopEditing(boolean animate);

  /**
   * Sets a listener to be called when the edit mode on a ROW in the list is about to start or stop
   * When editing is about to start the onWillExpand method will be called.
   * When editing is about to stop the onWillCollapse method will be called
   *
   * @param l the listener
   */
  public void setRowEditModeListener(iExpansionListener l);

  /**
   * Sets a listener to be called when the edit mode on the list is about to start or stop
   * When editing is about to start the onWillExpand method will be called.
   * When editing is about to stop the onWillCollapse method will be called
   *
   * @param l the listener
   */
  public void setEditModeListener(iExpansionListener l);

  /**
   * Sets a component to use to edit row items. This component will be displayed when
   * the user swipes on a row. The component can be a button or a container
   * containing buttons (e.g. a toolbar or form)
   *
   * @param c
   *          the component to use as a row item editor
   * @param centerVertically
   *          true to center the component vertically; false to have the
   *          component fill the rows vertical space
   */
  public void setRowEditingComponent(iPlatformComponent c, boolean centerVertically);

  /**
   * Gets the toolbar that will be used for marking and deleting list items
   * @return the toolbar that will be used for marking and deleting list items
   */
  public iToolBar getEditModeToolBar();

  /**
   * Sets the toolbar that will be used for marking and deleting list items
   * @param tb the toolbar that will be used for marking and deleting list items
   */
  public void setEditModeToolBar(iToolBar tb);
}
