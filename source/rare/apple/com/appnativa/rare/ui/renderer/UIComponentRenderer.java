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
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformRenderingComponent;

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
  public Object createNewNativeView() {
    if (renderingComponent != null) {
      try {
        return renderingComponent.getView().getClass().newInstance();
      } catch(Exception e) {
        throw new ApplicationException(e);
      }
    }

    throw new ApplicationException("renderingComponent is null");
  }

  @Override
  public void dispose() {
    config = null;

    if (renderingComponent != null) {
      renderingComponent.dispose();
      renderingComponent = null;
    }
  }

  @Override
  public void clearRenderer() {
    super.clearRenderer();

    if (renderingComponent != null) {
      renderingComponent.getView().clearVisualState();
    }
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
  public void prepareForReuse(int row, int column) {
    if (renderingComponent != null) {
      renderingComponent.getView().clearVisualState();
    }
  }

  @Override
  public void setAlignment(HorizontalAlign ha, VerticalAlign va) {}

  @Override
  public void setNativeView(Object proxy) {
    if (renderingComponent != null) {
      renderingComponent.getView().setProxy(proxy);
    }
  }

  public View getNativeView() {
    return renderingComponent.getView();
  }

  @Override
  public void setRenderingView(View view) {}
}
