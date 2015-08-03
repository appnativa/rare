/*
 * @(#)UIComponentRenderer.java   2012-02-08
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.renderer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

/**
 * A renderer that wraps a platform component.
 *
 * @author Don DeCoteau
 */
public class UIComponentRenderer extends aComponentRenderer implements iPlatformRenderingComponent {
  public UIComponentRenderer() {
    this(null);
  }

  public UIComponentRenderer(iPlatformComponent comp) {
    super(comp);
  }

  public UIComponentRenderer(iPlatformComponent comp, Widget config) {
    super(comp, config);
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    if (config != null) {
      return new UIComponentRenderer(Platform.getAppContext().getWindowViewer().createWidget(config).getContainerComponent(),
          config);
    }

    return new UIComponentRenderer(renderingComponent.copy());
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    ((Component) renderingComponent).setComponentPainterEx(cp);
  }

  @Override
  public void prepareForReuse(int row, int column) {
    setComponentPainter(null);
    renderingComponent.getView().setBackground(null);
  }
}
