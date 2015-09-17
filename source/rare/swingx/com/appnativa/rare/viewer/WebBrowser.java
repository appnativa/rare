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
