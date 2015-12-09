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

package com.appnativa.rare.platform.swing.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.WaitCursorHandler;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.widget.aWidget;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.util.Set;

import javax.swing.SwingUtilities;

public class HTMLViewer extends JFXPanel {
  LoadListener    loadListener;
  WebView         webView;
  private boolean handleWaitCursor;
  private boolean scaleToFit;

  public HTMLViewer() {
    javafx.application.Platform.runLater(new Runnable() {
      @Override
      public void run() {
        initFX();
      }
    });
  }

  public void setWindowProperty(final String name, final Object value) {
    javafx.application.Platform.runLater(new Runnable() {
      @Override
      public void run() {
        JSObject window = (JSObject) webView.getEngine().executeScript("window");

        window.setMember(name, value);
      }
    });
  }

  public void executeScript(final String script, final iFunctionCallback cb) {
    javafx.application.Platform.runLater(new Runnable() {
      @Override
      public void run() {
        final Object value = webView.getEngine().executeScript(script);

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

  public void clearContents() {
    javafx.application.Platform.runLater(new Runnable() {
      @Override
      public void run() {
        webView.getEngine().load("about:blank");
      }
    });
  }

  public void dispose() {
    if ((webView != null) && (loadListener != null)) {
      javafx.application.Platform.runLater(new Runnable() {
        @Override
        public void run() {
          if ((webView != null) && (loadListener != null)) {
            try {
              Worker w = webView.getEngine().getLoadWorker();

              if (w != null) {
                w.stateProperty().removeListener(loadListener);
              }
            } catch(Exception e) {
              Platform.ignoreException("disposing WebView", e);
            }

            webView      = null;
            loadListener = null;
          }
        }
      });
    }
  }

  public void load(final String url) {
    javafx.application.Platform.runLater(new Runnable() {
      @Override
      public void run() {
        checkIfListenerAdded();
        webView.getEngine().load(url);

        if (handleWaitCursor) {
          WaitCursorHandler.startWaitCursor(Component.fromView(HTMLViewer.this), null);
        }
      }
    });
  }

  public void loadContent(final String content, final String contentType, final String baseHref) {
    javafx.application.Platform.runLater(new Runnable() {
      @Override
      public void run() {
        checkIfListenerAdded();
        webView.getEngine().loadContent(content, contentType);

        if (handleWaitCursor) {
          WaitCursorHandler.startWaitCursor(Component.fromView(HTMLViewer.this), null);
        }
      }
    });
  }

  public void reload() {
    javafx.application.Platform.runLater(new Runnable() {
      @Override
      public void run() {
        webView.getEngine().reload();
      }
    });
  }

  public void stopLoading() {
    javafx.application.Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Worker w = webView.getEngine().getLoadWorker();

        if (w != null) {
          w.cancel();
        }
      }
    });
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);

    if (webView != null) {
      resizeWebView();
    }
  }

  public void setHandleWaitCursor(boolean handleWaitCursor) {
    this.handleWaitCursor = handleWaitCursor;
  }

  public void setScaleToFit(boolean scaleToFit) {
    this.scaleToFit = scaleToFit;
  }

  public String getURL() {
    return webView.getEngine().getLocation();
  }

  public boolean isHandleWaitCursor() {
    return handleWaitCursor;
  }

  private void checkIfListenerAdded() {
    if ((loadListener == null) && (webView != null)) {
      Worker w = webView.getEngine().getLoadWorker();

      if (w != null) {
        loadListener = new LoadListener();
        w.stateProperty().addListener(loadListener);
      }
    }
  }

  private void initFX() {
    Group group = new Group();
    Scene scene = new Scene(group);

    setScene(scene);
    webView = new WebView();
    group.getChildren().add(webView);
  }

  private void resizeWebView() {
    javafx.application.Platform.runLater(new Runnable() {
      @Override
      public void run() {
        webView.setPrefWidth(getWidth());
        webView.setPrefHeight(getHeight());
        scaleToFit();
      }
    });
  }

  private void scaleToFit() {
    if (scaleToFit) {
      try {
        //Object o = webView.getEngine().executeScript("document.body.clientWidth");
        //double  n = SNumber.doubleValue(o.toString());
        double    n       = 0;
        Set<Node> scrolls = webView.lookupAll(".scroll-bar");

        for (Node node : scrolls) {
          if (node instanceof ScrollBar) {
            ScrollBar sb = (ScrollBar) node;

            if (sb.getOrientation() == Orientation.HORIZONTAL) {
              n = sb.getMax() + 100;
            }
          }
        }

        if (n > 0) {
          n = (float) webView.getWidth() / n;
          webView.getEngine().executeScript("document.body.style.zoom = " + n);
        }
      } catch(Throwable ignore) {
        ignore.printStackTrace();
      }
    }
  }

  protected class LoadListener implements ChangeListener<Worker.State> {
    protected boolean firstLoad = true;

    @Override
    public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
      boolean         failed = false;
      final Component comp   = (Component) Component.fromView(HTMLViewer.this);
      final aWidget   w      = (aWidget) Platform.findWidgetForComponent(comp);

      if (firstLoad) {
        firstLoad = false;
        resizeWebView();
      }

      switch(newValue) {
        case FAILED :
          failed = true;
        //$FALL-THROUGH$
        case CANCELLED :
        case SUCCEEDED : {
          final boolean ffailed = failed;

          scaleToFit();
          SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              if (w.isEventEnabled(iConstants.EVENT_FINISHED_LOADING)) {
                DataEvent e = new DataEvent(webView, null);

                if (ffailed) {
                  e.markAsFailureEvent();
                }

                w.fireEvent(iConstants.EVENT_FINISHED_LOADING, e);
              }

              if (isHandleWaitCursor()) {
                WaitCursorHandler.stopWaitCursor(comp);
              }
            }
          });
        }

        break;

        case RUNNING :
          SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              if (w.isEventEnabled(iConstants.EVENT_STARTED_LOADING)) {
                w.fireEvent(iConstants.EVENT_STARTED_LOADING, new DataEvent(webView, null));
              }
            }
          });

          break;

        default :
          break;
      }
    }
  }
}
