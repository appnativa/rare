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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.spot.Browser;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.WaitCursorHandler;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aWebBrowser extends aPlatformViewer {
  protected boolean  generateBrowserExceptions = true;
  protected boolean  handleWaitCursor          = true;
  protected boolean  startedWaitcursor;
  protected iBrowser theBrowser;
  private boolean    autoInsertMetaContent;

  /**
   * Constructs a new instance
   */
  public aWebBrowser() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aWebBrowser(iContainer parent) {
    super(parent);
    widgetType = WidgetType.WebBrowser;
  }

  @Override
  public void clearContents() {
    if (theBrowser != null) {
      theBrowser.clearContents();
    }
  }

  /**
   * Configures the browser
   *
   * @param vcfg
   *          the browser's configuration
   */
  @Override
  public void configure(Viewer vcfg) {
    configureEx((Browser) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
    handleDataURL(vcfg, true);
  }

  @Override
  public void handleActionLink(ActionLink link, boolean deferred) {
    try {
      setURL(link.getURL(this));
    } catch(IOException ex) {
      this.handleException(ex);
    }
  }

  public void hideWaitCursorIfShowing() {
    if (handleWaitCursor && startedWaitcursor) {
      startedWaitcursor = false;
      WaitCursorHandler.stopWaitCursor(getDataComponent());
    }
  }

  @Override
  public void reload(boolean context) {
    if (theBrowser != null) {
      theBrowser.reload();
    }
  }

  /**
   * Stops the loading of the browser
   */
  public void stopLoading() {
    if (theBrowser != null) {
      theBrowser.stopLoading();
    }
  }

  @Override
  public iCancelableFuture setDataLink(ActionLink link, boolean defer) {
    if (link != null) {
      try {
        setURL(JavaURLConnection.toExternalForm(link.getURL(this)));
      } catch(MalformedURLException e) {
        throw new ApplicationException(e);
      }
    }

    return null;
  }

  @Override
  public void setDataURL(URL url) {
    setURL(JavaURLConnection.toExternalForm(url));
  }

  public void setGenerateBrowserExceptions(boolean generate) {
    this.generateBrowserExceptions = generate;
  }

  /**
   * Sets the HTML for the browser
   *
   * @param html
   *          the HTML to set
   *
   */
  public void setHTML(String html) {
    checkConfigure();
    loadContent(html, "text/html", null);
  }

  /**
   * Sets the HTML for the browser
   *
   * @param baseHref
   *          the base HREF for the data
   * @param html
   *          the HTML to set
   *
   */
  public void setHTML(String baseHref, String html) {
    checkConfigure();
    loadContent(html, "text/html", baseHref);
  }

  /**
   * @param handleWaitCursor
   *          the handleWaitCursor to set
   */
  public void setHandleWaitCursor(boolean handleWaitCursor) {
    this.handleWaitCursor = handleWaitCursor;
  }

  public void setPlainText(String text) {
    checkConfigure();
    loadContent(text, "text/plain", null);
  }

  public void setScaleToFit(boolean scaleToFit) {}

  public void setURL(final String url) {
    checkConfigure();

    if (Platform.isUIThread()) {
      setURLEx(url);
    } else {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          setURLEx(url);
        }
      });
    }
  }

  /**
   * Sets the URL for the browser
   *
   * @param url
   *          the URL
   * @throws IOException
   */
  public void setURL(URL url) throws IOException {
    setURL(url.toExternalForm());
  }

  @Override
  public void setValue(Object value) {
    try {
      if (value instanceof URL) {
        setURL((URL) value);
      } else if (value == null) {
        clearContents();
      } else {
        setHTML(value.toString());
      }
    } catch(IOException ex) {
      handleException(ex);
    }
  }

  public boolean getGenerateBrowserExceptions() {
    return generateBrowserExceptions;
  }

  public String getLocation() {
    return theBrowser.getLocation();
  }

  @Override
  public Object getSelection() {
    return isDesignMode()
           ? null
           : getLocation();
  }

  public URL getURL() throws MalformedURLException {
    String s = getLocation();

    return ((s == null) || (s.length() == 0))
           ? null
           : new URL(s);
  }

  @Override
  public boolean hasValue() {
    String s = getLocation();

    return (s != null) && (s.length() != 0) &&!s.equals("about:blank");
  }

  @Override
  protected void handleCustomProperties(Widget cfg, Map<String, Object> properties) {
    super.handleCustomProperties(cfg, properties);

    Object o = properties.get("scaleToFit");

    if (o instanceof String) {
      setScaleToFit("true".equals(o));
    } else if (o instanceof Boolean) {
      setScaleToFit(((Boolean) o).booleanValue());
    }

    o = properties.get("autoInsertMetaContent");

    if (o instanceof String) {
      autoInsertMetaContent = "true".equals(o);
    } else if (o instanceof Boolean) {
      autoInsertMetaContent = ((Boolean) o).booleanValue();
    }
  }

  /**
   * @return the handleWaitCursor
   */
  public boolean isHandleWaitCursor() {
    return handleWaitCursor;
  }

  protected void checkConfigure() {
    if (theBrowser == null) {
      theBrowser    = createWebView(new Browser());
      dataComponent = formComponent = theBrowser.getComponent();
    }
  }

  @Override
  protected void clearConfiguration(boolean dispose) {
    super.clearConfiguration(dispose);

    if (dispose) {
      hideWaitCursorIfShowing();

      if (theBrowser != null) {
        theBrowser.dispose();
      }

      theBrowser = null;
    } else {
      clearContents();
    }
  }

  /**
   * Configures the browser
   *
   * @param cfg
   *          the browser's configuration
   */
  protected void configureEx(Browser cfg) {
    theBrowser    = createWebView(cfg);
    dataComponent = formComponent = theBrowser.getComponent();
    configureEx(cfg, true, false, true);
  }

  protected abstract iBrowser createWebView(Browser cfg);

  protected void loadContent(String content, final String contentType, final String baseHref) {
    if (autoInsertMetaContent) {
      int width = getWidth();

      if ((width < 50) && (getParent() != null)) {
        width = getParent().getWidth();
      }

      if (width < 50) {
        UIDimension size = UIScreen.getUsableSize();

        if (size.width < size.height) {
          width = (int) size.width;
        } else {
          width = (int) size.width;

          int pwidth = (int) UIScreen.fromPlatformPixels(width);

          if (pwidth > 800) {
            width = width / 2;
          }
        }
      }

      int n = content.indexOf("<head>");

      if (n == -1) {
        String meta = "<head><meta name=\"viewport\" content=\"width=" + getWidth() + "\"/></head>";

        n = content.indexOf("<body>");

        if (n != -1) {
          content = content.substring(0, n) + meta + content.substring(n);
        }
      } else {
        if (n != -1) {
          String meta = "<meta name=\"viewport\" content=\"width=" + getWidth() + "\"/>";

          content = content.substring(0, n + 6) + meta + content.substring(n + 6);
        }
      }
    }

    if (Platform.isUIThread()) {
      theBrowser.loadContent(content, contentType, baseHref);
    } else {
      final String s = content;

      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          theBrowser.loadContent(s, contentType, baseHref);
        }
      });
    }
  }

  protected void startWaitCursor() {
    if (handleWaitCursor) {
      startedWaitcursor = true;
      WaitCursorHandler.startWaitCursor(null, null);
    }
  }

  protected void setURLEx(String url) {
    if (isDesignMode()) {
      return;
    }

    theBrowser.load(url);
  }

  public boolean isAutoInsertMetaContent() {
    return autoInsertMetaContent;
  }

  public void setAutoInsertMetaContent(boolean autoInsertMetaContent) {
    this.autoInsertMetaContent = autoInsertMetaContent;
  }

  public interface iBrowser {
    void clearContents();

    void dispose();

    void load(String url);

    void loadContent(String content, String contentType, String baseHref);

    void reload();

    void stopLoading();

    iPlatformComponent getComponent();

    String getLocation();
  }
}
