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

package com.appnativa.rare.ui.tree;

import com.appnativa.rare.exception.ExpandVetoException;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iTreeHandler;

/**
 *
 * @author Don DeCoteau
 */
public class TreeComponent extends Container {
  iTreeHandler treeHandler;

  /**
   * Creates a new instance
   *
   * @param list the list box
   * @param pane the scroll pane
   * @param selectionMode the selection mode
   */
  public TreeComponent(final TreeViewEx list, DataItemTreeModel lm) {
    super(list);
    treeHandler = new aTreeHandler(list, lm) {
      protected boolean hasListeners() {
        return listenerList != null;
      }
      protected EventListenerList getEventListenerList() {
        return TreeComponent.this.getEventListenerList();
      }
      public boolean isAutoScrollOnExpansion() {
        return list.isAutoScrollOnExpansion();
      }
      public void setAutoScrollOnExpansion(boolean autoScrollOnExpansion) {
        list.setAutoScrollOnExpansion(autoScrollOnExpansion);
      }
    };
    treeHandler.addExpansionListener(new iExpansionListener() {
      @Override
      public void itemWillExpand(ExpansionEvent event) throws ExpandVetoException {}
      @Override
      public void itemWillCollapse(ExpansionEvent event) throws ExpandVetoException {
        Object o = event.getItem();

        if (o instanceof iTreeItem) {
          iTreeItem ti = (iTreeItem) o;
          int       n  = ((aTreeHandler) treeHandler).indexOfInList(ti.getData());

          if (n != -1) {
            list.clearSelectionsInRange(n + 1, ti.getChildCount());
          }
        }
      }
    });
    list.setTreeModel(lm);
    lm.setTree(list);
    setForeground(ColorUtils.getListForeground());
    setBackground(ColorUtils.getListBackground());

    UIColor c = ColorUtils.getListDisabledForeground();

    if (c == null) {
      c = ColorUtils.getDisabledForeground();
    }

    setDisabledColor(c);
  }

  public void dispose() {
    if (view instanceof TreeViewEx) {
      ((TreeViewEx) view).setTreeModel(null);
    }

    super.dispose();
  }

  public void setTreeIcons(iPlatformIcon expanded, iPlatformIcon collapsed) {
    ((TreeViewEx) view).setTreeIcons(expanded, collapsed);
  }

  public iTreeHandler getTreeHandler() {
    return treeHandler;
  }
}
