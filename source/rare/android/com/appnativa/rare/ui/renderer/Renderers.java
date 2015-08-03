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

import android.content.Context;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.ui.iPlatformRenderingComponent;

import java.lang.reflect.Constructor;

/**
 *
 * @author Don DeCoteau
 */
public class Renderers {

  /** Creates a new instance of Renderers */
  private Renderers() {}

  public static iPlatformRenderingComponent createLabelRenderer(Context context) {
    return new UILabelRenderer(context);
  }

  public static iPlatformRenderingComponent createRenderer(Class cls, Context context) {
    try {
      Constructor c = cls.getDeclaredConstructor(new Class[] { Context.class });

      return (iPlatformRenderingComponent) c.newInstance(context);
    } catch(Exception ex) {
      throw ApplicationException.runtimeException(ex);
    }
  }

  public static iPlatformRenderingComponent createRenderer(String className, Context context)
          throws ClassNotFoundException {
    if (className == null) {
      return createLabelRenderer(context);
    }

    if (className.indexOf('.') == -1) {
      if (!className.startsWith("UI")) {
        className = "UI" + className;
      }

      className = UILabelRenderer.class.getPackage().getName() + "." + className;
    }

    return createRenderer(Platform.loadClass(className), context);
  }

  public static iPlatformRenderingComponent setupNewCopy(iPlatformRenderingComponent oldrc,
          iPlatformRenderingComponent newrc) {
    return newrc;
  }

  public static iPlatformRenderingComponent getRenderer(Class cls, Context context) {
    try {
      Constructor c = cls.getDeclaredConstructor(new Class[] { Context.class });

      return (iPlatformRenderingComponent) c.newInstance(context);
    } catch(Exception ex) {
      throw ApplicationException.runtimeException(ex);
    }
  }
}
