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

import com.appnativa.rare.platform.apple.ui.view.FormsView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.iPlatformRenderingComponent;

public class UICompositeRenderer extends aCompositeRenderer {
  public UICompositeRenderer() {
    super();
  }

  public UICompositeRenderer(iPlatformRenderingComponent rc) {
    super(rc);
  }

  @Override
  public Object createNewNativeView() {
    return FormsView.createProxy();
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    iPlatformRenderingComponent rc = (renderingComponent == null)
                                     ? new UILabelRenderer()
                                     : renderingComponent.newCopy();
    return setupNewCopy( new UICompositeRenderer(rc));
  }

  @Override
  public void prepareForReuse(int row, int column) {
    if (renderingComponent != null) {
      renderingComponent.prepareForReuse(row, column);
    }

    iconLabel.prepareForReuse(row, column);
    view.clearVisualState();
  }

  @Override
  public void clearRenderer() {
    super.clearRenderer();
    view.clearVisualState();
  }

  @Override
  public void setNativeView(Object proxy) {
    getView().setProxy(proxy);
  }

  @Override
  public void setRenderingView(View view) {
    super.setView(view);
  }
}
