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

package com.appnativa.rare.util;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.dnd.DnDConstants;
import com.appnativa.rare.ui.dnd.DropInformation;
import com.appnativa.rare.ui.dnd.RenderableDataItemTransferable;
import com.appnativa.rare.ui.dnd.TransferFlavor;
import com.appnativa.rare.ui.dnd.iTransferable;
import com.appnativa.rare.ui.event.FocusEvent;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iHyperlinkListener;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IntList;
import com.appnativa.util.SNumber;
import com.appnativa.util.StringCache;

import com.google.j2objc.annotations.Weak;

import java.util.Arrays;
import java.util.List;

public class ListHelper {
  public static final String  CALL_SUPER_METHOD      = new String();
  private final static String HYPERLINK_LISTENER_KEY = "__RARE.ListHelper.linkListener__";

  public static enum RunType { REFRESH, CLEAR }

  /**
   * Copies the currently selected items
   *
   * @param listComponent the component to copy the selected items to
   * @param index the index at which to copy the items
   * @param insertMode whether the items should be inserted at the specified index or added to the item at the index
   * @param delete whether the selected items should be deleted after they are copied
   */
  public static void copySelectedItems(iListHandler listComponent, int index, boolean insertMode, boolean delete) {
    if (index == listComponent.getSelectedIndex()) {    // ignore self drop
      return;
    }

    boolean end = (index == listComponent.size()) || (index == -1);

    if (end) {
      RenderableDataItem a[] = (RenderableDataItem[]) (delete
              ? listComponent.deleteSelectedData(true)
              : listComponent.getSelections());

      if (a != null) {
        if (!delete) {
          a = RenderableDataItem.deepCopy(a);
        }

        listComponent.addAll(Arrays.asList(a));
      }

      return;
    }

    RenderableDataItem item = listComponent.get(index);
    RenderableDataItem a[]  = (RenderableDataItem[]) (delete
            ? listComponent.deleteSelectedData(true)
            : listComponent.getSelections());

    if (delete) {
      index = listComponent.indexOf(item);

      if (index == -1) {
        index = listComponent.size();
      }
    } else {
      a = RenderableDataItem.deepCopy(a);
    }

    listComponent.addAll(index, Arrays.asList(a));
  }

  /**
   * Deletes the specified items selected data
   *
   * @param listComponent the component to delete from
   * @param sels the indexes to delete
   * @param returnData whether the data representing the currently selection should be returned
   * @return the specified items data or null
   */
  public static Object deleteItems(iListHandler listComponent, int sels[], boolean returnData) {
    if (sels == null) {
      return null;
    }

    int                  len = sels.length;
    RenderableDataItem[] a   = returnData
                               ? new RenderableDataItem[len]
                               : null;

    if (returnData) {
      for (int i = 0; i < len; i++) {
        a[i] = listComponent.get(sels[i]);
      }
    }

    for (int i = len - 1; i >= 0; i--) {
      listComponent.remove(sels[i]);
    }

    return a;
  }

  /**
   * Process a focus event and returns whether the component should become the focus
   * owner
   *
   * @param listComponent the component
   * @param e the focus event
   * @param focusOwner true if the component is the current focus owner ; false otherwise
   * @return true if the component is the current focus owner; false otherwise
   */
  public static boolean focusEvent(iListHandler listComponent, FocusEvent e, boolean focusOwner) {
    if (e.getID() == FocusEvent.FOCUS_GAINED) {
      boolean hs = listComponent.hasSelection();

      if (!focusOwner && hs) {
        listComponent.getListComponent().repaint();
      } else if (listComponent.isHandleFirstFocusSelection() &&!hs && (listComponent.size() > 0)) {
        listComponent.setSelectedIndex(0);
      }

      focusOwner = true;
    } else {
      iPlatformComponent fc = Platform.getAppContext().getPermanentFocusOwner();

      if (fc == null) {
        focusOwner = false;
      }

      if (!e.isTemporary() || (e.getOppositeComponent() == null)) {
        focusOwner = false;
      }

      if (!focusOwner && listComponent.hasSelection()) {
        listComponent.getListComponent().repaint();
      }
    }

    return focusOwner;
  }

  public static boolean importData(aWidget widget, iListHandler listComponent, final iTransferable t,
                                   final DropInformation drop) {
    if ((drop == null) || (t == null)) {
      return false;
    }

    boolean                  delete = drop.getDropAction() == DnDConstants.ACTION_MOVE;
    List<RenderableDataItem> items;
    iWidget                  w = drop.getSourceWidget();
    int                      index;

    if (drop.isInsertAtEnd()) {
      index = listComponent.size();
    } else {
      index = drop.getDropIndex();
    }

    if (t instanceof RenderableDataItemTransferable) {
      items = ((RenderableDataItemTransferable) t).getItems();
    } else {
      if (w != null) {
        boolean text = !t.isTransferFlavorSupported(TransferFlavor.itemFlavor);

        items = PlatformHelper.getDropedItems(widget, w, true, text);
      } else {
        try {
          items = Utils.getItems(drop.getText(), widget, false);
        } catch(Exception ex) {
          return false;
        }
      }
    }

    if (w == widget) {
      RenderableDataItem mi = (index == -1)
                              ? null
                              : listComponent.get(index);

      if (delete) {
        listComponent.removeAll(items);

        if (mi != null) {
          int n = listComponent.indexOf(mi);

          if (n < index) {
            index = n + 1;
          }
        }
      }
    } else if (delete && (w instanceof iListHandler)) {
      ((iListHandler) w).removeAll(items);
    }

    if (drop.isInsertAtEnd()) {
      listComponent.addAll(items);
    } else {
      listComponent.addAll(index, items, drop.isInsertMode());
    }

    return true;
  }

  public static void installItemLinkListener(iPlatformComponent c, iHyperlinkListener l) {
    if (c != null) {
      c.putClientProperty(HYPERLINK_LISTENER_KEY, l);
    }
  }

  public static void runLater(iListHandler listComponent, RunType type) {
    Platform.invokeLater(new Runner(listComponent, type));
  }

  public static void runOnEventThread(iListHandler listComponent, RunType type) {
    if (Platform.isUIThread()) {
      runIt(listComponent, type);
    } else {
      Platform.invokeLater(new Runner(listComponent, type));
    }
  }

  public static void setSelections(iListHandler listComponent, Object[] a) {
    int len = (a == null)
              ? 0
              : a.length;

    if (len == 0) {
      return;
    }

    IntList list = new IntList(len);
    int     n;

    for (int i = 0; i < len; i++) {
      n = listComponent.indexOf(a[i]);

      if (n > -1) {
        list.add(n);
      }
    }

    if (list.size() > 0) {
      listComponent.setSelectedIndexes(list.toArray());
    }
  }

  /**
   * Gets a widget attribute for a component
   *
   * @param listComponent the component
   * @param name the name of the attribute
   * @return the attribute's value
   */
  public static String getWidgetAttribute(iListHandler listComponent, String name) {
    int n   = name.indexOf('[');
    int row = -1;

    if (n != -1) {
      row  = SNumber.intValue(name.substring(n + 1));
      name = name.substring(0, n);
    }

    if (row != -1) {
      RenderableDataItem di = listComponent.get(row);

      if (name.equals(iConstants.WIDGET_ATT_VALUE)) {
        return (di == null)
               ? null
               : di.toString();
      }

      if (name.equals(iConstants.WIDGET_ATT_DATA)) {
        if (di != null) {
          Object o = di.getLinkedData();

          return (o == null)
                 ? null
                 : o.toString();
        }
      }

      return null;
    } else {
      if (name.equals(iConstants.WIDGET_ATT_SELECTION_ROWS)) {
        int[] rows = listComponent.getSelectedIndexes();

        return (rows == null)
               ? ""
               : Arrays.toString(rows);
      }

      if (name.equals(iConstants.WIDGET_ATT_ROW_COUNT)) {
        return StringCache.valueOf(listComponent.size());
      }

      return CALL_SUPER_METHOD;
    }
  }

  private static void runIt(iListHandler listComponent, RunType type) {
    if (listComponent == null) {
      return;
    }

    switch(type) {
      case REFRESH :
        listComponent.refreshItems();

        break;

      case CLEAR :
        listComponent.clear();

        break;

      default :
        break;
    }
  }

  protected static class Runner implements Runnable {
    @Weak
    iListHandler listComponent;
    RunType      type;

    Runner(iListHandler listComponent, RunType type) {
      this.listComponent = listComponent;
      this.type          = type;
    }

    @Override
    public void run() {
      runIt(listComponent, type);
    }
  }
}
