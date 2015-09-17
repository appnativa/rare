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
      return new UIComponentRenderer(
          Platform.getAppContext().getWindowViewer().createWidget(config).getContainerComponent(), config);
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
