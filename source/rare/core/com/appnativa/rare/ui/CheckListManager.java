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

package com.appnativa.rare.ui;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.util.IntList;

import java.util.ArrayList;
import java.util.List;

public class CheckListManager implements iDataModelListener {
  protected int                  checkCount;
  protected iPlatformIcon        checkedIcon;
  protected iPlatformIcon        indeterminateIcon;
  protected iPlatformIcon        uncheckedIcon;
  private int                    childItemCount;
  private int                    expandableColumn;
  private iPlatformListDataModel listModel;
  private boolean                manageSelections;

  public CheckListManager(boolean manageSelections, int expandableColumn) {
    this.manageSelections = manageSelections;
    this.expandableColumn = expandableColumn;
  }

  public void clear() {
    if (listModel != null) {
      clear(listModel);
    }
  }

  @Override
  public void contentsChanged(Object source) {
    iPlatformListDataModel list = (iPlatformListDataModel) source;

    if (expandableColumn > -2) {
      RenderableDataItem item = (list.size() == 0)
                                ? null
                                : list.get(0);

      if (item == null) {
        childItemCount = 0;
      } else {
        RenderableDataItem parent = item;

        while(item != null) {
          item = item.getParentItem();

          if (item != null) {
            parent = item;
          }
        }

        countListItems(parent);
      }
    } else {
      countListItems(list);
    }
  }

  @Override
  public void contentsChanged(Object source, int index0, int index1) {
    int size = index1 - index0;

    if ((size == childItemCount) || (expandableColumn < -1)) {
      contentsChanged(source);
    }
  }

  public int countListItems(List<RenderableDataItem> list) {
    childItemCount = countListItemsEx(list);

    return childItemCount;
  }

  public void dispose() {}

  @Override
  public void intervalAdded(Object source, int index0, int index1) {
    if (expandableColumn < -1) {
      childItemCount += Math.abs(index1 - index0);
    }
  }

  @Override
  public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
    if (expandableColumn < -1) {
      childItemCount -= Math.abs(index1 - index0);

      if (childItemCount <= 0) {
        contentsChanged(source);
      }
    }
  }

  @Override
  public void structureChanged(Object source) {
    contentsChanged(source);
  }

  public boolean toggleManagedRow(int row) {
    return toggleManagedRow(row, listModel.get(row));
  }

  public boolean toggleManagedRow(int row, RenderableDataItem parent) {
    boolean checked = parent.isStateFlagSet(iConstants.ITEM_SELECTED);

    if (checked && parent.isStateFlagSet(iConstants.ITEM_INDETERMINATE)) {
      checked = false;
    }

    checked = !checked;

    boolean dirty = updateManagedItem(row, parent, checked);

    if (manageSelections) {
      parent = parent.getParentItem();

      if (parent != null) {
        if (checked) {
          if (!parent.isStateFlagSet(iConstants.ITEM_SELECTED)) {
            setIndeterminate(-1, parent, true);
            dirty = makeIndeterminateUpwards(parent);
          } else if (checkCount == childItemCount) {
            if (setSelectedUpwards(parent, true)) {
              dirty = true;
            }
          }
        } else {
          if (checkCount == 0) {
            if (setSelectedUpwards(parent, false)) {
              dirty = true;
            }
          } else if (checkIndeterminate(parent, expandableColumn)) {
            dirty = true;
          }
        }
      }
    }

    return dirty;
  }

  public boolean toggleRow(int row) {
    return toggleRow(row, listModel.get(row));
  }

  public boolean toggleRow(int row, RenderableDataItem item) {
    boolean checked = !item.isStateFlagSet(iConstants.ITEM_SELECTED);

    setSelected(item, checked);

    if (manageSelections) {
      RenderableDataItem parent = item.getParentItem();

      if (parent != null) {
        if (checked) {
          if (!parent.isStateFlagSet(iConstants.ITEM_SELECTED)) {
            setIndeterminate(-1, parent, true);

            return makeIndeterminateUpwards(parent);
          } else if (checkCount == childItemCount) {
            return setSelectedUpwards(parent, true);
          }
        } else {
          if (checkCount == 0) {
            return setSelectedUpwards(parent, false);
          }

          return checkIndeterminate(parent, expandableColumn);
        }
      }
    }

    return false;
  }

  public void setCheckedRows(int[] indices) {
    List<RenderableDataItem> list = listModel;

    clear(list);

    if (indices == null) {
      return;
    }

    for (int i : indices) {
      RenderableDataItem       item  = list.get(i);
      List<RenderableDataItem> clist = getChildren(item);

      if ((clist != null) && (clist.size() > 0)) {
        updateManagedItem(i, item, true);

        continue;
      }

      toggleRow(i, item);

      if (manageSelections) {
        RenderableDataItem parent = item.getParentItem();

        if (parent != null) {
          setIndeterminate(-1, parent, true);
        }
      }
    }
  }

  public void setExpandableColumn(int expandableColumn) {
    this.expandableColumn = expandableColumn;
  }

  public void setIcons(iPlatformIcon checked, iPlatformIcon unchecked, iPlatformIcon indeterminate) {
    checkedIcon       = checked;
    uncheckedIcon     = unchecked;
    indeterminateIcon = indeterminate;
  }

  public boolean setIndeterminate(int row, RenderableDataItem item, boolean indeterminate) {
    boolean dirty = false;

    if (indeterminate) {
      dirty = !item.isStateFlagSet(iConstants.ITEM_INDETERMINATE);

      if (setSelected(item, true)) {
        dirty = true;
      }

      item.setStateFlag(iConstants.ITEM_INDETERMINATE);
    } else {
      dirty = item.isStateFlagSet(iConstants.ITEM_INDETERMINATE);
      item.unsetStateFlag(iConstants.ITEM_INDETERMINATE);
    }

    return dirty;
  }

  public void setListModel(iPlatformListDataModel listModel) {
    if (this.listModel != null) {
      this.listModel.removeDataModelListener(this);
    }

    this.listModel = listModel;

    if (listModel != null) {
      this.listModel.removeDataModelListener(this);
      this.listModel.addDataModelListener(this);
      contentsChanged(listModel);
    } else {
      childItemCount = 0;
    }
  }

  public void setManageSelections(boolean manageSelections) {
    this.manageSelections = manageSelections;
  }

  public int getCheckedCount() {
    return checkCount;
  }

  public iPlatformIcon getCheckedIcon() {
    return checkedIcon;
  }

  public List<RenderableDataItem> getCheckedRows(List<RenderableDataItem> list) {
    return getCheckedRows(list, null);
  }

  public int[] getCheckedIndexes(List<RenderableDataItem> list) {
    int     len   = list.size();
    IntList rlist = null;

    for (int i = 0; i < len; i++) {
      RenderableDataItem item = list.get(i);

      if (item.isStateFlagSet(iConstants.ITEM_SELECTED)) {
        if (rlist == null) {
          rlist = new IntList();
        }

        rlist.add(i);
      }
    }

    return (rlist == null)
           ? null
           : rlist.toArray();
  }

  public int getExpandableColumn() {
    return expandableColumn;
  }

  public iPlatformIcon getIndeterminateIcon() {
    return indeterminateIcon;
  }

  public iPlatformListDataModel getListModel() {
    return listModel;
  }

  public iPlatformIcon getRowIcon(int row) {
    RenderableDataItem item = listModel.get(row);

    return getRowIcon(row, item);
  }

  public iPlatformIcon getRowIcon(int row, RenderableDataItem item) {
    if (!item.isStateFlagSet(iConstants.ITEM_SELECTED)) {
      return uncheckedIcon;
    }

    if (item.isStateFlagSet(iConstants.ITEM_INDETERMINATE)) {
      return indeterminateIcon;
    }

    return checkedIcon;
  }

  public iPlatformIcon getUncheckedIcon() {
    return uncheckedIcon;
  }

  public boolean hasCheckedRows() {
    return checkCount > 0;
  }

  public boolean isManageSelections() {
    return manageSelections;
  }

  public boolean isRowChecked(int row) {
    RenderableDataItem item = listModel.get(row);

    return item.isStateFlagSet(iConstants.ITEM_SELECTED);
  }

  protected boolean checkIndeterminate(RenderableDataItem parent, int expandableColumn) {
    List<RenderableDataItem> list = getChildren(parent);
    int                      len  = (list == null)
                                    ? 0
                                    : list.size();

    if (len == 0) {
      return false;
    }

    int count = 0;

    for (int i = 0; i < len; i++) {
      if (list.get(i).isStateFlagSet(iConstants.ITEM_SELECTED)) {
        count++;
      }
    }

    if (count == 0) {
      setSelected(parent, false);

      if (checkCount > 0) {
        makeIndeterminateUpwards(parent.getParentItem());
      } else if (checkCount == 0) {
        setSelectedUpwards(parent, false);
      }

      return true;
    } else if (count == len) {
      setSelected(parent, true);

      if (checkCount >= childItemCount) {    //can be greater for trees where the root node is also selected
        return setSelectedUpwards(parent.getParentItem(), true);
      }

      return makeIndeterminateUpwards(parent.getParentItem());
    } else {
      setIndeterminate(-1, parent, true);
      makeIndeterminateUpwards(parent.getParentItem());

      return true;
    }
  }

  protected void clear(List<RenderableDataItem> list) {
    int len = list.size();

    for (int i = 0; i < len; i++) {
      RenderableDataItem item = list.get(i);

      setSelected(item, false);

      List<RenderableDataItem> clist = getChildren(item);

      if ((clist != null) && (clist.size() > 0)) {
        clear(clist);
      }
    }
  }

  protected boolean updateManagedItem(int row, RenderableDataItem parent, boolean checked) {
    setSelected(parent, checked);

    boolean                  deep = false;
    List<RenderableDataItem> list = getChildren(parent);
    int                      len  = (list == null)
                                    ? 0
                                    : list.size();

    for (int i = 0; i < len; i++) {
      RenderableDataItem       item  = list.get(i);
      List<RenderableDataItem> clist = getChildren(item);

      if ((clist != null) && (clist.size() > 0)) {
        updateManagedItem(i, item, checked);
        deep = true;
      } else if (item.isStateFlagSet(iConstants.ITEM_SELECTED) != checked) {
        setSelected(item, checked);
        deep = true;
      }
    }

    return deep;
  }

  protected List<RenderableDataItem> getCheckedRows(List<RenderableDataItem> list, List<RenderableDataItem> rlist) {
    int len = list.size();

    for (int i = 0; i < len; i++) {
      RenderableDataItem item = list.get(i);

      if (item.isStateFlagSet(iConstants.ITEM_SELECTED)) {
        if (rlist == null) {
          rlist = new ArrayList<RenderableDataItem>();
        }

        rlist.add(item);

        List<RenderableDataItem> clist = getChildren(item);

        if ((clist != null) && (clist.size() > 0)) {
          getCheckedRows(clist, rlist);
        }
      }
    }

    return rlist;
  }

  protected List<RenderableDataItem> getChildren(RenderableDataItem parent) {
    if (expandableColumn < -1) {
      return null;
    }

    RenderableDataItem item = (expandableColumn == -1)
                              ? parent
                              : parent.get(expandableColumn);

    return (item == null)
           ? null
           : item.getItems();
  }

  private int countListItemsEx(List<RenderableDataItem> list) {
    int len      = list.size();
    int children = len;

    if (expandableColumn > -2) {
      children = 0;

      for (int i = 0; i < len; i++) {
        List<RenderableDataItem> clist = getChildren(list.get(i));

        children++;

        if ((clist != null) && (clist.size() > 0)) {
          children += countListItemsEx(clist);
        }
      }
    }

    return children;
  }

  private boolean makeIndeterminateUpwards(RenderableDataItem parent) {
    boolean dirty = false;

    while(parent != null) {
      if (setIndeterminate(-1, parent, true)) {
        dirty = true;
      }

      parent = parent.getParentItem();
    }

    return dirty;
  }

  private boolean setSelected(RenderableDataItem item, boolean selected) {
    boolean dirty = item.isStateFlagSet(iConstants.ITEM_INDETERMINATE);

    if (dirty) {
      item.unsetStateFlag(iConstants.ITEM_INDETERMINATE);
    }

    if (selected) {
      if (!item.isStateFlagSet(iConstants.ITEM_SELECTED)) {
        item.setStateFlag(iConstants.ITEM_SELECTED);

        if ((expandableColumn < -1) || (item.getParentItem() != null)) {    //don't count the root
          checkCount++;
        }

        dirty = true;
      }
    } else {
      if (item.isStateFlagSet(iConstants.ITEM_SELECTED)) {
        item.unsetStateFlag(iConstants.ITEM_SELECTED);

        if ((expandableColumn < -1) || (item.getParentItem() != null)) {    //don't count the root
          checkCount--;
        }

        if (checkCount < 0) {
          checkCount = 0;
        }

        dirty = true;
      }
    }

    return dirty;
  }

  private boolean setSelectedUpwards(RenderableDataItem parent, boolean checked) {
    boolean dirty = false;

    while(parent != null) {
      if (setSelected(parent, checked)) {
        dirty = true;
      }

      parent = parent.getParentItem();
    }

    return dirty;
  }
}
