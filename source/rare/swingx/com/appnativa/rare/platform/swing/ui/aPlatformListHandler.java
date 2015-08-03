/*
 * @(#)aPlatformListHandler.java   2014-01-19
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui;

import java.awt.Rectangle;

import javax.swing.JComponent;

import com.appnativa.rare.platform.swing.ui.view.ListView;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.aListHandler;
import com.appnativa.rare.ui.iListView;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.listener.iHyperlinkListener;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.ui.table.TableView;
import com.appnativa.util.IntList;

public abstract class aPlatformListHandler extends aListHandler {
  public aPlatformListHandler(iListView view) {
    super(view);
  }

  public aPlatformListHandler(iListView view, iPlatformListDataModel model) {
    super(view, model);
  }

  public static void installItemLinkListener(iPlatformComponent c, iHyperlinkListener l) {
//  if (c != null) {
//    c.putClientProperty(HYPERLINK_LISTENER_KEY, l);
//  }
  }

  @Override
  public void selectAll() {
    if (listView instanceof ListView) {
      ((ListView) listView).selectAll();
    } else if (listView instanceof ListView) {
      ((TableView) listView).selectAll();
    }
  }
 
  @Override
  public void setAlternatingRowColor(UIColor color) {
    listView.setAlternatingRowColor(color);
  }

  @Override
  public void setDividerLine(UIColor c, UIStroke stroke) {
    ((ListView) listView).setDividerLine(c, stroke);
  }

  @Override
  public void setListSelectable(boolean selectable) {
    if (listView instanceof ListView) {
      ((ListView) listView).setSelectable(selectable);
    } else if (listView instanceof TableView) {
      ((TableView) listView).setSelectable(selectable);
    }
    
  }

  @Override
  public void setListSelectionType(SelectionType type) {
    if (listView instanceof ListView) {
      ((ListView) listView).setSelectionType(type);
    } else if (listView instanceof TableView) {
      ((TableView) listView).setSelectionType(type);
    }
  }

  @Override
  public UIColor getAlternatingRowColor() {
    return listView.getAlternatingRowColor();
  }

  @Override
  public iPlatformComponent getContainerComponent() {
    iPlatformComponent c = getListComponent();

    if ((c != null) && (c.getParent() instanceof ContainerPanel)) {
      c = c.getParent();
    }

    return c;
  }

  public javax.swing.JComponent getDataView() {
    if (listView instanceof ListView) {
      return ((ListView) listView).getList();
    }

    return (JComponent) listView;
  }

  public static iHyperlinkListener getItemLinkListener(iPlatformComponent c) {
//  if (c != null) {
//    return (iHyperlinkListener) c.getClientProperty(HYPERLINK_LISTENER_KEY);
//  }
    return null;
  }

  @Override
  public aListItemRenderer getItemRenderer() {
    return listView.getItemRenderer();
  }

  @Override
  public iPlatformComponent getListComponent() {
    iPlatformComponent c = Component.fromView(getDataView());

    if (c == null) {
      c = new Container(getDataView());
    }

    return c;
  }

  @Override
  public SelectionType getListSelectionType() {
    if (listView instanceof ListView) {
      return ((ListView) listView).getSelectionType();
    } else if (listView instanceof TableView) {
      return ((TableView) listView).getSelectionType();
    }

    return SelectionType.ROW_ON_BOTTOM;
  }

  @Override
  public UIRectangle getRowBounds(int firstRow, int lastRow) {
    Rectangle r=null;
    if (listView instanceof ListView) {
      r=((ListView) listView).getRowBounds(firstRow, lastRow);
    } else if (listView instanceof ListView) {
      r=((TableView) listView).getRowBounds(firstRow, lastRow);
    }

    if (r == null) {
      return null;
    } else {
      return new UIRectangle(r.x, r.y, r.width, r.height);
    }
  }

  @Override
  public int getRowHeight() {

    if (listView instanceof ListView) {
      return ((ListView) listView).getRowHeight();
    }

    if (listView instanceof TableView) {
      return ((TableView) listView).getRowHeight();
    }

    return -1;
  }

  @Override
  public int getRowIndexAt(float x, float y) {
    if (listView instanceof ListView) {
      return ((ListView) listView).getRowAtPoint((int)x, (int)y);
    }

    if (listView instanceof TableView) {
      return ((TableView) listView).getRowAtPoint((int)x, (int)y);
    }

    return -1;
  }

  public javax.swing.JComponent getView() {
    return (JComponent) listView;
  }

  @Override
  public boolean isMultipleSelectionAllowed() {
    if (listView instanceof ListView) {
      return ((ListView) listView).isMultipleSelectionAllowed();
    }

    if (listView instanceof TableView) {
      return ((TableView) listView).isMultipleSelectionAllowed();
    }

    return false;
  }

  @Override
  protected void setRowHeightEx(int height) {

    if (listView instanceof ListView) {
      ((ListView) listView).setRowHeight(height);
    } else if (listView instanceof TableView) {
      ((TableView) listView).setRowHeight(height);
    }
  }

  @Override
  protected void setSelectedIndexEx(int index) {
    if (listView instanceof ListView) {
      ((ListView) listView).setSelectedIndex(index);
    } else if (listView instanceof TableView) {
      ((TableView) listView).setSelectedIndex(index);
    }
  }

  @Override
  protected void setSelectedIndexesEx(int[] indices) {
    if (listView instanceof ListView) {
      ((ListView) listView).setSelectedIndices(indices);
    } else if (listView instanceof TableView) {
      ((TableView) listView).setSelectedIndices(indices);
    }
  }

  class SelectionManager {
    IntList selections;
  }
}
