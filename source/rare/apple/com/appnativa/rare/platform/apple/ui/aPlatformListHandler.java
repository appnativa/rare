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

package com.appnativa.rare.platform.apple.ui;

import com.appnativa.rare.platform.apple.ui.view.ListView;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.platform.apple.ui.view.aTableBasedView;
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
  public void setAlternatingRowColor(UIColor color) {
    if (listView instanceof aTableBasedView) {
      ((aTableBasedView) listView).setAlternatingRowColor(color);
    }
  }

  @Override
  public void setDividerLine(UIColor c, UIStroke stroke) {
    if (listView instanceof aTableBasedView) {
      ((aTableBasedView) listView).setDividerLine(c, stroke);
    }
  }

  @Override
  public void setListSelectable(boolean selectable) {
    if (listView instanceof aTableBasedView) {
      ((aTableBasedView) listView).setSelectable(selectable);
    }
  }

  @Override
  public void setListSelectionType(SelectionType type) {
    final View v = getView();

    if (v instanceof aTableBasedView) {
      ((aTableBasedView) v).setSelectionType(type);
    }
  }

  @Override
  public UIColor getAlternatingRowColor() {
    if (listView instanceof aTableBasedView) {
      return ((aTableBasedView) listView).getAlternatingRowColor();
    }

    return null;
  }

  @Override
  public iPlatformComponent getContainerComponent() {
    iPlatformComponent c = getListComponent();

    if ((c != null) && (c.getParent() instanceof ContainerPanel)) {
      c = c.getParent();
    }

    return c;
  }

  public static iHyperlinkListener getItemLinkListener(iPlatformComponent c) {
//  if (c != null) {
//    return (iHyperlinkListener) c.getClientProperty(HYPERLINK_LISTENER_KEY);
//  }
    return null;
  }

  @Override
  public aListItemRenderer getItemRenderer() {
    if (listView instanceof ListView) {
      return ((ListView) listView).getItemRenderer();
    }

    return null;
  }

  @Override
  public iPlatformComponent getListComponent() {
    iPlatformComponent c = Component.fromView(getView());

    if (c == null) {
      c = new Container(getView());
    }

    return c;
  }

  @Override
  public SelectionType getListSelectionType() {
    final View v = getView();

    if (v instanceof aTableBasedView) {
      return ((aTableBasedView) v).getSelectionType();
    }

    return SelectionType.ROW_ON_TOP;
  }

  @Override
  public UIRectangle getRowBounds(int row0, int row1) {
    return new UIRectangle(0, 0, 0, 0);
  }

  @Override
  public int getRowHeight() {
    final View v = getView();

    if (v instanceof aTableBasedView) {
      return ((aTableBasedView) v).getRowHeight();
    }

    return -1;
  }

  @Override
  public int getRowIndexAt(float x, float y) {
    final View v = getView();

    if (v instanceof aTableBasedView) {
      return ((aTableBasedView) v).rowAtPoint(x, y);
    }

    return -1;
  }

  public ParentView getView() {
    return (ParentView) listView;
  }

  @Override
  public boolean isMultipleSelectionAllowed() {
    if (listView instanceof aTableBasedView) {
      return ((aTableBasedView) listView).isMultipleSelectionAllowed();
    }

    return false;
  }

  @Override
  protected void setRowHeightEx(int height) {
    final View v = getView();

    if (v instanceof aTableBasedView) {
      ((aTableBasedView) v).setRowHeight(height);
    }
  }

  @Override
  protected void setSelectedIndexEx(int index) {
    if (listView instanceof aTableBasedView) {
      ((aTableBasedView) listView).setSelectedIndex(index);
    }
  }

  @Override
  protected void setSelectedIndexesEx(int[] indices) {
    if ((listView instanceof aTableBasedView)) {
      ((aTableBasedView) listView).setSelectedIndexes(indices);
    }
  }
}
