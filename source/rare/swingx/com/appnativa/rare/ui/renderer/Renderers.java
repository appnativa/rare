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
import com.appnativa.rare.ui.iPlatformRenderingComponent;

/**
 *
 * @author Don DeCoteau
 */
public class Renderers {

  /** Creates a new instance of Renderers */
  private Renderers() {}

  public static iPlatformRenderingComponent createLabelRenderer() {
    return new UILabelRenderer();
  }

  public static iPlatformRenderingComponent createRenderer(String className) throws ClassNotFoundException {
    if (className.indexOf('.') == -1) {
      if (!className.startsWith("UI")) {
        className = "UI" + className;
      }

      className = "com.appnativa.rare.ui.renderer." + className;
    }

    return (iPlatformRenderingComponent) Platform.createObject(className);
  }

  public static iPlatformRenderingComponent setupNewCopy(iPlatformRenderingComponent oldrc,
          iPlatformRenderingComponent newrc) {
    return newrc;
  }
}
