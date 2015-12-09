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

import android.annotation.TargetApi;

import android.content.Intent;

import android.graphics.Bitmap;

import android.net.Uri;

import android.os.Build;

import android.webkit.HttpAuthHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.BrowserException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.android.ui.view.WebViewEx;
import com.appnativa.rare.spot.Browser;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.widget.aPlatformWidget;

/**
 *
 * @author Don DeCoteau
 */
public class WebBrowser extends aWebBrowser {
  boolean                         supportZoom     = true;
  boolean                         useZoomControls = true;
  protected WebViewEx             webView;
  protected Client                webviewClient;
  private iHttpAuthRequestHandler httpAuthRequestHandler;
  private boolean                 loadInline;

  /**
   * Constructs a new instance
   */
  public WebBrowser() {
    super(null);
  }

  /**
   * Constructs a new instance
   *
   * @param fv
   *          the widget's parent
   */
  public WebBrowser(iContainer parent) {
    super(parent);
  }

  @Override
  public void onConfigurationChanged(boolean reset) {
    if (webView != null) {
      webView.onConfigurationChanged(reset);
      webView.setUseZoomControls(useZoomControls);
      webView.getSettings().setSupportZoom(supportZoom);
    }

    super.onConfigurationChanged(reset);
  }

  /**
   * Executes the specified script within the context of the browser
   *
   * @param script the script to execute
   * @param cb an optional callback to receive the result of the execution
   */
  @TargetApi(Build.VERSION_CODES.KITKAT)
  public void executeScript(final String script, final iFunctionCallback cb) {
    webView.evaluateJavascript(script, new ValueCallback() {
      @Override
      public void onReceiveValue(final Object value) {
        if (cb != null) {
          Platform.invokeLater(new Runnable() {
            @Override
            public void run() {
              cb.finished(false, value);
            }
          });
        }
      }
    });
  }

  /**
   * Sets a property on the current DOM window
   * in the browser
   *
   * @param name the name of the property to set
   * @param value the value of the property
   */
  public void setWindowProperty(String name, Object value) {
    webView.addJavascriptInterface(value, name);
  }

  /**
   * Sets Whether the browser automatically wraps content when the user zooms in
   * and out (default is true)
   *
   * @param autoWrap
   *          true to auto warp; false otherwise
   */
  public void setAutoWrapOnZoom(boolean autoWrap) {
    webView.getSettings().setUseWideViewPort(!autoWrap);
  }

  /**
   * @param handleWaitCursor
   *          the handleWaitCursor to set
   */
  @Override
  public void setHandleWaitCursor(boolean handleWaitCursor) {
    super.setHandleWaitCursor(handleWaitCursor);

    if (handleWaitCursor) {
      if (webviewClient == null) {
        webviewClient = new Client();
        webView.setWebViewClient(webviewClient);
      }

      webviewClient.setLoadInline(loadInline);
    }
  }

  public void setHttpAuthRequestHandler(iHttpAuthRequestHandler httpAuthRequestHandler) {
    this.httpAuthRequestHandler = httpAuthRequestHandler;
  }

  public void setLoadInline(boolean loadInline) {
    this.loadInline = loadInline;
  }

  @Override
  public void setScaleToFit(boolean scaleToFit) {
    webView.getSettings().setLoadWithOverviewMode(scaleToFit);
    webView.getSettings().setUseWideViewPort(scaleToFit);
  }

  public void setSupporZoom(boolean support) {
    supportZoom = support;
    webView.getSettings().setSupportZoom(support);
  }

  public void setUseZoomControls(boolean use) {
    useZoomControls = use;
    webView.setUseZoomControls(use);
  }

  public iHttpAuthRequestHandler getHttpAuthRequestHandler() {
    return httpAuthRequestHandler;
  }

  /**
   * Gets Whether the browser automatically wraps content when the user zoom in
   * and out (default is true)
   *
   * @return true if auto wrap is on; false otherwise
   */
  public boolean isAutoWrapOnZoom(boolean autoWrap) {
    return !webView.getSettings().getUseWideViewPort();
  }

  public boolean isLoadInline() {
    return loadInline;
  }

  /**
   * Configures the browser
   *
   * @param cfg
   *          the browser's configuration
   */
  @Override
  protected void configureEx(Browser cfg) {
    super.configureEx(cfg);

    UIFont f = dataComponent.getFont();

    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    webView.getSettings().setDefaultFontSize(f.getSize());

    if (webView.getSettings().getDefaultFixedFontSize() > f.getSize()) {
      webView.getSettings().setDefaultFixedFontSize(f.getSize());
    }
  }

  @Override
  protected iBrowser createWebView(Browser cfg) {
    webView       = new WebViewEx(getAndroidContext());
    webviewClient = new Client();
    webView.setWebViewClient(webviewClient);
    webView.setUseZoomControls(useZoomControls);
    webView.getSettings().setSupportZoom(supportZoom);
    webView.getSettings().setJavaScriptEnabled(true);

    return new AndroidBrowser();
  }

  public static interface iHttpAuthRequestHandler {
    Object onReceivedHttpAuthRequest(WebBrowser browser, Object platformHandler, String location, String realm);
  }


  protected class AndroidBrowser implements iBrowser {
    @Override
    public void dispose() {
      if (webView != null) {
        try {
          webView.dispose();
        } catch(Throwable ignore) {}
      }

      webView = null;
    }

    @Override
    public void load(String url) {
      webView.loadUrl(url);
    }

    @Override
    public void loadContent(String content, String contentType, String baseHref) {
      webView.loadDataWithBaseURL(null, content, contentType, "iso-8859-1", baseHref);
    }

    @Override
    public void reload() {
      webView.reload();
    }

    @Override
    public void stopLoading() {
      webView.stopLoading();
    }

    @Override
    public iPlatformComponent getComponent() {
      return new Container(webView);
    }

    @Override
    public String getLocation() {
      return webView.getUrl();
    }

    @Override
    public void clearContents() {
      webView.loadUrl("about:blank");
    }
  }


  protected static class Client extends WebViewClient {
    private boolean loadInline;

    public Client() {}

    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      onPageFinished(view, url, false, null);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);

      WebBrowser w = (WebBrowser) aPlatformWidget.getWidgetForView(view);

      if ((w != null) && w.isHandleWaitCursor()) {
        Platform.getAppContext().getAsyncLoadStatusHandler().loadStarted(w, new ActionLink(url), null);
      }

      if ((w != null) && (w.getWidgetListener() != null) && w.isEventEnabled(iConstants.EVENT_STARTED_LOADING)) {
        w.fireEvent(iConstants.EVENT_STARTED_LOADING, new DataEvent(view, url, null));
      }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
      super.onReceivedError(view, errorCode, description, failingUrl);
      onPageFinished(view, failingUrl, true, description);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
      WebBrowser w = (WebBrowser) aPlatformWidget.getWidgetForView(view);

      if ((w != null) && (w.httpAuthRequestHandler != null)) {
        w.httpAuthRequestHandler.onReceivedHttpAuthRequest(w, handler, host, realm);

        return;
      }

      String[] up = view.getHttpAuthUsernamePassword(host, realm);

      if ((up != null) && (up.length == 2)) {
        handler.proceed(up[0], up[1]);
      } else {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);

        if (w != null) {
          w.handleException(new BrowserException(401, host, "Unauthorized"));
        } else {
          throw new BrowserException(401, host, "Unauthorized");
        }
      }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      WebBrowser w = (WebBrowser) aPlatformWidget.getWidgetForView(view);

      if ((w != null) && (w.getWidgetListener() != null) && w.isEventEnabled(iConstants.EVENT_CHANGE)) {
        DataEvent e = new DataEvent(view, url, null);

        w.getWidgetListener().evaluate(iConstants.EVENT_CHANGE, e, true);

        if (e.isConsumed()) {
          return true;
        }
      }

      if (!loadInline) {
        Intent i = new Intent(Intent.ACTION_VIEW);

        i.setData(Uri.parse(url));
        view.getContext().startActivity(i);
      }

      return !loadInline;
    }

    public void setLoadInline(boolean loadInline) {
      this.loadInline = loadInline;
    }

    private void onPageFinished(WebView view, String url, boolean failed, String reason) {
      WebBrowser w = (WebBrowser) aPlatformWidget.getWidgetForView(view);

      if ((w != null) && w.isHandleWaitCursor()) {
        Platform.getAppContext().getAsyncLoadStatusHandler().loadCompleted(w, new ActionLink(url));
      }

      if ((w != null) && (w.getWidgetListener() != null) && w.isEventEnabled(iConstants.EVENT_FINISHED_LOADING)) {
        DataEvent e = new DataEvent(view, url, reason);

        if (failed) {
          e.markAsFailureEvent();
        }

        w.fireEvent(iConstants.EVENT_FINISHED_LOADING, e);
      }
    }
  }
}
