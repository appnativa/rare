/*
 * @(#)DesignWindowViewer.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.viewer.WindowViewer;

public class DesignWindowViewer extends WindowViewer {
  private DesignWindowManager windowManager;

  public DesignWindowViewer(DesignWindowManager wm, iPlatformAppContext ctx, String name, iWindow win,
                            WindowViewer parent, iScriptHandler sh) {
    super(ctx, name, win, parent, sh);
    setDesignMode(true);
    windowManager=wm;
  }

  @Override
  public void dispose() {
    DesignWindowManager wm = windowManager;

    windowManager = null;

    if (wm != null) {
      wm.dispose();
    } else {
      super.dispose();
    }
  }
}
