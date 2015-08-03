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

package com.appnativa.rare.platform.android.ui;

import android.view.ViewGroup;

import android.widget.ListAdapter;

import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.ListComponent;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.widget.iWidget;

public class PopupListBoxHandler extends ListBoxListHandler {
  @SuppressWarnings("unused")
  public PopupListBoxHandler(iWidget context, iPlatformListDataModel model, boolean forMenu) {
    super(new ListViewEx(context.getAppContext().getActivity()), model);

    ListViewEx             list = (ListViewEx) this.getAdapterView();
    ViewGroup.LayoutParams lp   = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);

    list.setLayoutParams(lp);
    list.setShowLastDivider(false);
    list.setAdapter((ListAdapter) listModel);

    Component comp = new ListComponent(list);

    list.setVerticalFadingEdgeEnabled(true);

    ListItemRenderer lr = new ListItemRenderer();

    model.setItemRenderer(lr);
    PainterUtils.setPopupListRenderingDefaults(this, lr, forMenu);
  }

  public iPlatformComponent getListComponent() {
    iPlatformComponent c = super.getListComponent();

    if (c == null) {
      c = new Component(getAdapterView());
    }

    return c;
  }
}
