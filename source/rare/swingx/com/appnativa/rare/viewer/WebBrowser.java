/*
 * @(#)WebBrowser.java   2011-12-23
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import com.appnativa.rare.platform.swing.ui.view.HTMLViewer;
import com.appnativa.rare.spot.Browser;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 *
 * @author Don DeCoteau
 */
public class WebBrowser extends aWebBrowser {
  static {
    javafx.application.Platform.setImplicitExit(false);
  }

  HTMLViewer htmlViewer;

  /**
   * Constructs a new instance
   */
  public WebBrowser() {
    super();
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public WebBrowser(iContainer parent) {
    super(parent);
  }

  @Override
  public void setScaleToFit(boolean scaleToFit) {
    htmlViewer.setScaleToFit(scaleToFit);
  }

  @Override
  protected iBrowser createWebView(Browser cfg) {
    htmlViewer = new HTMLViewer();

    return new FXBrowser();
  }

  protected class FXBrowser implements iBrowser {
    public FXBrowser() {}

    @Override
    public void dispose() {
      if (htmlViewer != null) {
        htmlViewer.dispose();
        htmlViewer = null;
      }
    }

    @Override
    public void load(final String url) {
      if (htmlViewer != null) {
        htmlViewer.load(url);
      }
    }

    @Override
    public void loadContent(final String content, final String contentType, final String baseHref) {
      if (htmlViewer != null) {
        htmlViewer.loadContent(content, contentType, baseHref);
      }
    }

    @Override
    public void reload() {
      if (htmlViewer != null) {
        htmlViewer.reload();
      }
    }

    @Override
    public void stopLoading() {
      if (htmlViewer != null) {
        htmlViewer.stopLoading();
      }
    }

    @Override
    public iPlatformComponent getComponent() {
      return new Component(htmlViewer);
    }

    @Override
    public String getLocation() {
      return htmlViewer.getURL();
    }

    @Override
    public void clearContents() {
      if (htmlViewer != null) {
        htmlViewer.clearContents();
      }
    }
  }
}
