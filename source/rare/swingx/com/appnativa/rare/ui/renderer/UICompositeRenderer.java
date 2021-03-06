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

import com.appnativa.rare.ui.iPlatformRenderingComponent;

public class UICompositeRenderer extends aCompositeRenderer {
  public UICompositeRenderer() {
    super();
  }

  public UICompositeRenderer(iPlatformRenderingComponent rc) {
    super(rc);
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    iPlatformRenderingComponent rc = (renderingComponent == null)
                                     ? new UILabelRenderer()
                                     : renderingComponent.newCopy();
    UICompositeRenderer         cr = new UICompositeRenderer(rc);

    cr.setIconPosition(iconPosition);
    cr.backgroundSurface = backgroundSurface;
    Renderers.setupNewCopy(this, cr);

    return cr;
  }

  @Override
  public void prepareForReuse(int row, int column) {
    iconLabel.prepareForReuse(row, column);
    setComponentPainter(null);
    getView().setBackground(null);
  }
}
