/*
 * @(#)HTMLViewer.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.util.Set;

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

import javax.swing.SwingUtilities;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.WaitCursorHandler;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.widget.aWidget;

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