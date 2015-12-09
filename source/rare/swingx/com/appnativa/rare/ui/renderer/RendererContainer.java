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

import com.appnativa.rare.platform.swing.ui.view.ListRowContainer;
import com.appnativa.rare.platform.swing.ui.view.ListView;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

public class RendererContainer extends aRendererContainer {
  public RendererContainer(ListView parent, iPlatformRenderingComponent rc) {
    super(rc);

    ListRowContainer lc = new ListRowContainer(parent);

    setView(lc);
  }

  public void setRenderingComponent(iPlatformRenderingComponent rc) {
    if (this.renderingComponent != rc) {
      this.renderingComponent = rc;
      ((ListRowContainer) view).setContentView(rc.getComponent().getView());
    }
  }


  @Override
  public void prepareForReuse(int row, int column) {
    setComponentPainter(null);
    getView().setBackground(null);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    setComponentPainterEx(cp);
  }
}
