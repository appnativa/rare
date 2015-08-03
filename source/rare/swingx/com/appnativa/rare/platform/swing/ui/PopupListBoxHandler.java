/*
 * @(#)PopupListBoxHandler.java
 * 
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
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
