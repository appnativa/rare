/*
 * @(#)Renderers.java   2011-07-18
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
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


  public static iPlatformRenderingComponent createRenderer(String className)
          throws ClassNotFoundException {
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
