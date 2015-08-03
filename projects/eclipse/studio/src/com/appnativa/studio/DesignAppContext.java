/*
 * @(#)DesignAppContext.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.io.IOException;
import java.net.URL;

import javax.swing.UIManager;

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.platform.swing.Rare;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColor;

public class DesignAppContext extends AppContext {
  public String defaultManagedResourcePath;

  public DesignAppContext(Rare instance) {
    super(instance, true);
  }

  @Override
  public boolean alwaysUseHeavyTargets() {
    return true;
  }

  @Override
  public boolean isDesignContext() {
    return true;
  }

  public void makeActiveContext() {
    _instance = this;
    setupSwingUIDefaults();
  }

  public URL makeResourceURL(String name, String extension) {
    URL url = super.makeResourceURL(name, extension);

    if (url != null) {
      try {
        url = org.eclipse.core.runtime.FileLocator.toFileURL(url);
      } catch (IOException ignore) {
      }
    }

    return url;
  }

  public boolean isDisposed() {
    return RARE == null;
  }

  @Override
  protected String getDefaultManagedResourcePath() {
    if (defaultManagedResourcePath != null) {
      return defaultManagedResourcePath;
    }

    return super.getDefaultManagedResourcePath();
  }

  public void initializeSWING() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      PlatformHelper.initialize();
      try {
        PlatformHelper.defaultFontUpdated(FontUtils.getSystemFont());
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception ignore) {
      ignore.printStackTrace();
    }
    setupUIDefaults(UIColor.BLACK, UIColor.WHITE);
  }
}
