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

package com.appnativa.rare.viewer;

import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 * A viewer for holding arbitrary components.
 * This viewer is meant to be instantiated programmatically.
 *
 * @author Don DeCoteau
 */
public class BeanViewer extends aPlatformViewer {
  protected static Viewer DEFAULT_CFG = new Viewer();

  public BeanViewer(iContainer parent) {
    super(parent);
  }

  public BeanViewer(iContainer parent, iPlatformComponent comp) {
    super(parent);
    formComponent = dataComponent = comp;
    configure(DEFAULT_CFG);
  }

  public BeanViewer(iContainer parent, iPlatformComponent fcomp, iPlatformComponent dcomp) {
    super(parent);
    formComponent = fcomp;
    formComponent = dcomp;
    configure(DEFAULT_CFG);
  }

  public void configure(iPlatformComponent comp, Viewer cfg) {
    formComponent = dataComponent = comp;
    configure((cfg == null)
              ? DEFAULT_CFG
              : cfg);
  }

  @Override
  public void configure(Viewer cfg) {
    super.configureEx(cfg, true, false, true);
  }
}
