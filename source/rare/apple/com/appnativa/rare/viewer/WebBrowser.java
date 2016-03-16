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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.platform.apple.ui.view.WebView;
import com.appnativa.rare.platform.apple.ui.view.WebView.iLoadListener;
import com.appnativa.rare.spot.Browser;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.event.DataEvent;

/**
 * Native Web Browser viewer
 * 
 * @author Don DeCoteau
 */
public class WebBrowser extends aWebBrowser {
  protected WebView webView;

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
  public void dispose() {
    super.dispose();
    webView = null;
  }

  /**
   * Executes the specified script within the context of the browser
   *
   * @param script
   *          the script to execute
   * @param cb
   *          an optional callback to receive the result of the execution
   */
  public void executeScript(final String script, final iFunctionCallback cb) {
    webView.executeScript(script, cb);
  }

  /**
   * Sets a property on the current DOM window
   * in the browser
   *
   * @param name the name of the property to set
   * @param value the value of the property
   */
  public void setWindowProperty(String name, Object value) {
    webView.setWindowProperty(name, value);
  }

  @Override
  public void setScaleToFit(boolean scaleToFit) {
    webView.setScaleToFit(scaleToFit);
  }

   @Override
  protected void handleViewerConfigurationChanged(boolean reset) {
     webView.handleViewerConfigurationChanged();
     super.handleViewerConfigurationChanged(reset);
  }
   
  @Override
  protected iBrowser createWebView(Browser cfg) {
    webView = new WebView();
    dataComponent = formComponent = new Container(webView);
    stackWaitCursors=false;
    return new AppleBrowser();
  }

  protected class AppleBrowser implements iBrowser, iLoadListener {
    String loadingUrl;

    AppleBrowser() {
      webView.setLoadListener(this);
    }

    @Override
    public void clearContents() {
      webView.clearContents();
    }

    @Override
    public void dispose() {
      if (webView != null) {
        try {
          webView.stopLoading();
        } catch (Throwable ignore) {
        }
      }
    }

    @Override
    public void load(String url) {
      webView.load(url);
    }

    @Override
    public void loadContent(String content, String contentType, String baseHref) {
      webView.loadContent(content, contentType, baseHref);
    }

    @Override
    public void loadFailed(WebView view, String reason) {
      loadFinished(view, true, reason);
    }

    @Override
    public void loadFinished(WebView view) {
      loadFinished(view, false, null);
    }
    
    public void loadFinished(WebView view, boolean failed, String reason) {
      hideWaitCursor();
      if (webView != null) {
        if (isEventEnabled(iConstants.EVENT_FINISHED_LOADING)) {
          DataEvent e = new DataEvent(webView, webView.getURL(), reason);

          if (failed) {
            e.markAsFailureEvent();
          }

          fireEvent(iConstants.EVENT_FINISHED_LOADING, e);
        }
      }

    }

    @Override
    public void loadStarted(WebView view) {
      if (webView != null && loadingUrl!=null) {
        if (isEventEnabled(iConstants.EVENT_STARTED_LOADING)) {
          DataEvent e = new DataEvent(webView, loadingUrl, null);

          fireEvent(iConstants.EVENT_STARTED_LOADING, e);
        }
      }
      loadingUrl=null;
    }

    @Override
    public boolean loadWillStart(WebView view, String url, int loadType) {
      if (webView != null) {
        if ((getWidgetListener() != null) && isEventEnabled(iConstants.EVENT_CHANGE)) {
          DataEvent e = new DataEvent(view, url, null);

          getWidgetListener().evaluate(iConstants.EVENT_CHANGE, e, true);

          if (e.isConsumed()) {
            return false;
          }
        }
        loadingUrl=url;
        showWaitCursor();
        return true;
      } else {
        return false;
      }
    }

    @Override
    public void reload() {
      if (webView != null) {
        webView.reload();
      }
    }

    @Override
    public void stopLoading() {
      if (webView != null) {
        webView.stopLoading();
      }
    }

    @Override
    public iPlatformComponent getComponent() {
      return formComponent;
    }

    @Override
    public String getLocation() {
      return webView.getURL();
    }

    @Override
    public Object getNativeBrowser() {
      return webView;
    }
  }
}
