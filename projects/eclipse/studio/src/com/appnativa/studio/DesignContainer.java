/*
 * @(#)DesignContainer.java   2008-08-17
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import com.appnativa.rare.spot.WidgetPane;
import com.appnativa.rare.viewer.WidgetPaneViewer;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author decoteaud
 */
public class DesignContainer extends WidgetPaneViewer {
  public DesignContainer(iWidget w) {
    setDesignMode(true);
    WidgetPane p = new WidgetPane();
    p.autoResizeWidget.setValue(true);
    p.local.setValue(true);
    p.name.setValue("Design Container");
    configure(p);
    setWidget(w);
  }

  public void register() {}

  public void unregister() {}

  public Object getLinkedData() {
    return theWidget.getLinkedData();
  }
}
