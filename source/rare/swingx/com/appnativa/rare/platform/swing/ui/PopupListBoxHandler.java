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

package com.appnativa.rare.platform.swing.ui;

import com.appnativa.rare.platform.swing.ui.view.ListView;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.widget.iWidget;

public class PopupListBoxHandler extends ListBoxListHandler {
  iPlatformComponent containerComponent;
  iPlatformComponent listComponent;

  public PopupListBoxHandler(iWidget context, iPlatformListDataModel model, boolean forMenu) {
    super(new ListView(model), model);
    listComponent      = new Container(getView());
    containerComponent = listComponent;
    ((ListView) getView()).getList().setAsPopupList(true);

    ListItemRenderer lr = new ListItemRenderer((ListView) getView(), true);

    ((ListView) getView()).setItemRenderer(lr);    // set after we set the rendering defaults because we need insets to be set
    PainterUtils.setPopupListRenderingDefaults(this, lr, forMenu);
  }

  @Override
  public void dispose() {
    if (containerComponent != null) {
      containerComponent.dispose();
    }

    super.dispose();
    containerComponent = null;
    listComponent      = null;
  }

  @Override
  public iPlatformComponent getContainerComponent() {
    return containerComponent;
  }

  @Override
  public iPlatformComponent getListComponent() {
    return listComponent;
  }
}
