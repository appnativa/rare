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

package com.appnativa.rare.ui;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JRootPaneEx;
import com.appnativa.rare.ui.aWindowManager.WindowType;
import com.appnativa.rare.ui.iWindowManager.iFrame;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.FocusEvent;
import com.appnativa.rare.ui.event.WindowEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.SNumber;

/**
 *
 * @author Don DeCoteau
 */
public class Frame extends Container implements iFrame, WindowListener {
  protected int                 menuOffset            = 0;
  protected boolean             disposeOfNativeWindow = true;
  boolean                       undecorated;
  boolean                       transparent;
  boolean                       modal;
  protected iPlatformAppContext appContext;
  protected iPlatformMenuBar    menuBar;
  protected iStatusBar          statusBar;
  protected iTarget             target;
  protected iWidget             titleWidget;
  protected iToolBarHolder      toolbarHolder;
  protected Window              window;
  protected WindowViewer        windowViewer;
  private boolean               autoDispose;
  private boolean               locationSet;
  private JPanel                menuToolbarPanel;
  private RootPaneContainer     rootPaneContainer;
  private boolean               sizeSet;
  private OverlayContainer      overlayContainer;
  private TitlePane             titlePane;
  private WindowType            windowType;
  private boolean               runtimeDecorations;

  public Frame(iPlatformAppContext app, Window win, RootPaneContainer rpc, WindowType type) {
    super(rpc.getRootPane());
    this.window            = win;
    this.appContext        = app;
    this.rootPaneContainer = rpc;
    this.windowType        = type;
    rpc.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", Boolean.FALSE);
    win.addWindowListener(this);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    if (c != null) {
      iParentComponent pc = (iParentComponent) Component.fromView((JComponent) rootPaneContainer.getContentPane());

      pc.add(c);
    }
  }

  public void addManagedOverlay(iPlatformComponent c) {
    if (c != null) {
      getOverlayContainer().addManagedOverlay(c);
    }
  }

  public void addOverlay(iPlatformComponent c) {
    if (c != null) {
      getOverlayContainer().addOverlay(c);
    }
  }

  @Override
  public void addWindowListener(iWindowListener l) {
    getEventListenerList().add(iWindowListener.class, l);
  }

  @Override
  public void center() {
    ScreenUtils.centerOnScreen(this);
  }

  @Override
  public void close() {
    if (window != null) {
      window.dispatchEvent(new java.awt.event.WindowEvent(window, java.awt.event.WindowEvent.WINDOW_CLOSING));
    }
  }

  @Override
  public void disposeEx() {
    Window w = window;

    window = null;

    if (w != null) {
      try {
        // w.removeWindowListener(this);
      } catch(Exception ignore) {}

      if (disposeOfNativeWindow) {
        w.dispose();
      }

      if (target != null) {
        target.dispose(true);
      }
      // Don't dispose because the WindowViewer owns them and will dispose of
      // them
      // if (menuBar != null) {
      // menuBar.dispose();
      // }
      //
      // if (statusBar != null) {
      // statusBar.dispose();
      // }
      //
      // if (toolbarHolder != null) {
      // toolbarHolder.dispose();
      // }

      if (titleWidget != null) {
        titleWidget.dispose();
      }

      if (disposeOfNativeWindow) {
        super.disposeEx();
      }

      titleWidget   = null;
      toolbarHolder = null;
      statusBar     = null;
      window        = null;
      windowViewer  = null;
      menuBar       = null;
      target        = null;
      appContext    = null;
    }
  }

  @Override
  public void disposeOfWindow() {
    dispose();
  }

  public void finishWindowSetup(Map options) {
    if (titleWidget == null) {
      boolean createTitle = false;

      if (!undecorated) {
        if (window instanceof JDialog) {
          createTitle = ((JDialog) window).isUndecorated();
        } else if (window instanceof JFrame) {
          createTitle = ((JFrame) window).isUndecorated();
        }
      }

      if (createTitle) {
        JRootPane rp = rootPaneContainer.getRootPane();

        if (rp instanceof JRootPaneEx) {
          boolean show=Platform.getUIDefaults().getBoolean("Rare.Dialog.showCloseButton", true);
          String s=options!=null ? (String)options.get("showCloseButton") : null;
          if(s!=null) {
            show=SNumber.booleanValue(s);
          }
          iActionListener l=null;
          if(show) {
            l= new iActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                windowViewer.close();
              }
            };
          }
          titlePane = TitlePane.createDialogTitle(windowViewer, l);
          windowViewer.addWindowDragger(titlePane);
          ((JRootPaneEx) rp).setTitlePane(titlePane.getJComponent());
          runtimeDecorations=true;
        }
      }
    }

    String s = (String) options.get("keystrokes");

    if (s != null) {
      SwingHelper.configureKeystrokes(windowViewer, rootPaneContainer.getRootPane(), s,
                                      JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    Utils.setupWindowOptions(this, options);
  }

  @Override
  public UIRectangle getBounds() {
    return SwingHelper.setUIRectangle(null, window.getBounds());
  }

  @Override
  public iPlatformComponent getComponent() {
    return this;
  }

  @Override
  public UIRectangle getInnerBounds(UIRectangle rect) {
    if (rect == null) {
      rect = new UIRectangle();
    }

    if (rootPaneContainer.getRootPane() instanceof JRootPaneEx) {
      int h;

      ((JRootPaneEx) rootPaneContainer.getRootPane()).getInnerBounds(rect);

      if ((toolbarHolder != null) && toolbarHolder.getComponent().isVisible()) {
        rect.height -= h = toolbarHolder.getComponent().getHeight();
        rect.y      += h;
      }

      if ((statusBar != null) && statusBar.getComponent().isVisible()) {
        rect.height -= h = statusBar.getComponent().getHeight();
        rect.y      += h;
      }

      return rect;
    } else {
      return super.getInnerBounds(rect);
    }
  }

  @Override
  public UIDimension getInnerSize(UIDimension size) {
    UIRectangle rect = getInnerBounds(null);

    if (size == null) {
      size = new UIDimension();
    }

    size.width  = UIScreen.snapToSize(rect.width);
    size.height = UIScreen.snapToSize(rect.height);

    return size;
  }

  @Override
  public iPlatformMenuBar getMenuBar() {
    return menuBar;
  }

  public RootPaneContainer getRootPaneContainer() {
    return rootPaneContainer;
  }

  /**
   * Get the x-position of the window's current screen location
   *
   * @return the x-position of the window's current screen location
   */
  @Override
  public int getScreenX() {
    return window.getX();
  }

  /**
   * Get the y-position of the window's current screen location
   *
   * @return the y-position of the window's current screen location
   */
  @Override
  public int getScreenY() {
    return window.getY();
  }

  @Override
  public iStatusBar getStatusBar() {
    return statusBar;
  }

  @Override
  public iTarget getTarget() {
    return target;
  }

  @Override
  public String getTargetName() {
    return (target == null)
           ? null
           : target.getName();
  }

  @Override
  public String getTitle() {
    if (window instanceof java.awt.Frame) {
      return ((java.awt.Frame) window).getTitle();
    } else if (window instanceof java.awt.Dialog) {
      return ((java.awt.Dialog) window).getTitle();
    }

    return null;
  }

  @Override
  public iToolBarHolder getToolBarHolder() {
    return toolbarHolder;
  }

  @Override
  public Object getUIWindow() {
    return window;
  }

  @Override
  public iContainer getViewer() {
    return windowViewer;
  }

  @Override
  public iWidget getWidget() {
    return windowViewer;
  }

  public WindowType getWindowType() {
    return windowType;
  }

  public WindowViewer getWindowViewer() {
    return windowViewer;
  }

  @Override
  public void hideWindow() {
    window.setVisible(false);
  }

  public boolean isAutoDispose() {
    return autoDispose;
  }

  public boolean isClosingAllowed() {
    return (windowViewer == null)
           ? true
           : windowViewer.isClosingAllowed();
  }

  public boolean isDialog() {
    return windowType == WindowType.DIALOG;
  }

  public boolean isPopup() {
    return windowType == WindowType.POPUP;
  }

  public boolean isTransparent() {
    return transparent;
  }

  public boolean isUndecorated() {
    return undecorated;
  }

  public boolean isUsesRuntimeDecorations() {
    return runtimeDecorations;
  }

  @Override
  public void moveBy(float x, float y) {
    if ((x != 0) && (y != 0)) {
      x += window.getX();
      y += window.getY();
      window.setLocation((int) x, (int) y);
    }
  }

  @Override
  public void moveTo(float x, float y) {
    window.setLocation((int) x, (int) y);
  }

  @Override
  public void pack() {
    window.pack();
  }

  @Override
  public void remove(iPlatformComponent c) {
    if (c != null) {
      iParentComponent pc = (iParentComponent) Component.fromView((JComponent) rootPaneContainer.getContentPane());

      pc.remove(c);
    }
  }

  @Override
  public void removeAll() {
    iParentComponent pc = (iParentComponent) Component.fromView((JComponent) rootPaneContainer.getContentPane());

    pc.removeAll();

    if (overlayContainer != null) {
      overlayContainer.dispose();
      overlayContainer = null;
    }
  }

  public void removeOverlay(iPlatformComponent c) {
    if ((c != null) && (overlayContainer != null)) {
      overlayContainer.removeOverlay(c);
    }
  }

  @Override
  public void removeWindowListener(iWindowListener l) {
    if (listenerList != null) {
      listenerList.remove(iWindowListener.class, l);
    }
  }

  public void setAutoDispose(boolean autoDispose) {
    this.autoDispose = autoDispose;
  }

  @Override
  public void setBackground(UIColor bg) {
    super.setBackground(bg);

    iParentComponent pc = (iParentComponent) Component.fromView((JComponent) rootPaneContainer.getContentPane());

    pc.setBackground(bg);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    setLocation(x, y);
    setSize(width, height);
  }

  @Override
  public void setCanClose(boolean can) {
    if (windowViewer != null) {
      windowViewer.setCanClose(can);
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    JRootPane rp = rootPaneContainer.getRootPane();

    if (rp instanceof JRootPaneEx) {
      ((JRootPaneEx)rp).setComponentPainter(cp);
    }
  }

  public void setDefaultButton(PushButtonWidget widget) {
    java.awt.Component b = widget.getContainerComponentEx().getView();

    if (b instanceof JButton) {
      rootPaneContainer.getRootPane().setDefaultButton((JButton) b);
    }
  }

  public void setIcon(iPlatformIcon icon) {
    if (titlePane != null) {
      titlePane.setIcon(icon);
    } else {
      if (icon == null) {
        window.setIconImage(null);
      } else if (icon instanceof UIImageIcon) {
        window.setIconImage(((UIImageIcon) icon).getImage().getImage());
      }
    }
  }

  public void setLocation(int x, int y) {
    window.setLocation(x, y);
    locationSet = true;
  }

  @Override
  public void setMenuBar(iPlatformMenuBar mb) {
    iPlatformMenuBar omb = menuBar;
    JRootPane        rp  = rootPaneContainer.getRootPane();

    if (rp instanceof JRootPaneEx) {
      if (omb != null) {
        if (omb.getMenuBarComponent() instanceof ActionBar) {
          ((JRootPaneEx) rp).setTitlePane(null);
        } else {
          rp.setJMenuBar(null);
        }
      }

      if (mb != null) {
        if (mb.getMenuBarComponent() instanceof ActionBar) {
          ((JRootPaneEx) rp).setTitlePane(mb.getContainerComponent().getView());
        } else {
          rp.setJMenuBar(mb.getMenuBar());
        }
      }
    } else {
      JComponent jm = (mb == null)
                      ? null
                      : mb.getContainerComponent().getView();

      setMenuOrToolBarComponent(jm, true);
    }

    menuBar = mb;

  }

  public void setMovable(boolean movable) {}

  public void setPartialLocation(UIPoint uiPoint) {}

  public void setPartialSize(UIDimension uiDimension) {}

  @Override
  public void setResizable(boolean resizable) {
    if (window instanceof java.awt.Frame) {
      ((java.awt.Frame) window).setResizable(resizable);
    } else if (window instanceof java.awt.Dialog) {
      ((java.awt.Dialog) window).setResizable(resizable);
    }
  }

  public void setRootPaneContainer(RootPaneContainer rootPaneContainer) {
    this.rootPaneContainer = rootPaneContainer;
  }

  public void setSize(int width, int height) {
    sizeSet = true;
    window.setSize(width, height);
  }

  @Override
  public void setStatusBar(iStatusBar sb) {
    statusBar = sb;

    if (rootPaneContainer.getRootPane() instanceof JRootPaneEx) {
      BorderPanel bp = (BorderPanel) Component.fromView((JComponent) rootPaneContainer.getContentPane());

      bp.setBottomView((sb == null)
                       ? null
                       : sb.getComponent());
    }
  }

  public void setTarget(iTarget target) {
    this.target = target;
  }

  @Override
  public void setTitle(String title) {
    if (titlePane != null) {
      titlePane.setTitle(title);
    }

    if (window instanceof java.awt.Frame) {
      ((java.awt.Frame) window).setTitle(title);
    } else if (window instanceof java.awt.Dialog) {
      ((java.awt.Dialog) window).setTitle(title);
    }
  }

  public void setTitleWidget(iWidget widget) {
    titleWidget = widget;

    JComponent c  = (widget == null)
                    ? null
                    : widget.getContainerComponent().getView();
    JRootPane  rp = rootPaneContainer.getRootPane();

    if (rp instanceof JRootPaneEx) {
      ((JRootPaneEx) rp).setTitlePane(c);
    }
  }

  @Override
  public void setToolBarHolder(iToolBarHolder tbh) {
    if (rootPaneContainer.getRootPane() instanceof JRootPaneEx) {
      BorderPanel bp = (BorderPanel) Component.fromView((JComponent) rootPaneContainer.getContentPane());

      bp.setTopView((tbh == null)
                    ? null
                    : tbh.getComponent());
    } else {
      JComponent c = (tbh == null)
                     ? null
                     : tbh.getComponent().getView();

      setMenuOrToolBarComponent(c, false);
    }

    toolbarHolder = tbh;

  }

  @Override
  public void setVisible(boolean visible) {
    if (window != null) {
      if (visible) {
        Platform.getAppContext().closePopupWindows(false);

        if (!sizeSet) {
          pack();
        }

        if (!locationSet) {
          WindowViewer top = Platform.getWindowViewer();

          if (top == windowViewer) {
            ScreenUtils.centerOnScreen(this);
          } else {
            ScreenUtils.centerOnWindow(windowViewer, top);
          }
        }
      }

      if (visible && modal) {
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            window.setVisible(true);
          }
        });
      } else {
        window.setVisible(visible);
      }

      if (visible) {
        Platform.getAppContext().getActiveWindows().addIfNotPresent(this);

        JButton b = rootPaneContainer.getRootPane().getDefaultButton();

        if (b != null) {
          b.requestFocusInWindow();
        }
      } else {
        Platform.getAppContext().getActiveWindows().remove(this);
      }
    }
  }

  @Override
  public void showWindow() {
    setVisible(true);
  }

  @Override
  public void showWindow(int x, int y) {
    window.setLocation(x, y);
    setVisible(true);
  }

  @Override
  public void toBack() {
    window.toBack();
  }

  @Override
  public void toFront() {
    window.toFront();
  }

  @Override
  public void update() {
    revalidate();
    repaint();
  }

  @Override
  public void windowActivated(java.awt.event.WindowEvent e) {
    if ((getWidgetListener() != null) && getWidgetListener().isEnabled(iConstants.EVENT_FOCUS)) {
      FocusEvent fe = new FocusEvent(this, true, false);

      getWidgetListener().execute(iConstants.EVENT_FOCUS, fe);
    }
  }

  @Override
  public void windowClosed(java.awt.event.WindowEvent e) {
    Utils.fireWindowEvent(listenerList, this, WindowEvent.Type.Closed);
  }

  @Override
  public void windowClosing(java.awt.event.WindowEvent e) {
    WindowEvent we = Utils.fireWindowEvent(listenerList, this, WindowEvent.Type.WillClose);

    if ((we == null) ||!we.isConsumed()) {
      if ((windowViewer != null) && windowViewer.isClosingAllowed()) {
        Window w = e.getWindow();

        w.removeWindowListener(this);

        if (Platform.getAppContext().getWindowManager().getMainWindow() == this) {
          Platform.getAppContext().exit();
        } else {
          windowViewer.dispose();
        }
      }
    }
  }

  @Override
  public void windowDeactivated(java.awt.event.WindowEvent e) {
    if ((getWidgetListener() != null) && getWidgetListener().isEnabled(iConstants.EVENT_BLUR)) {
      FocusEvent fe = new FocusEvent(this, false, false);

      getWidgetListener().execute(iConstants.EVENT_BLUR, fe);
    }
  }

  @Override
  public void windowDeiconified(java.awt.event.WindowEvent e) {
    if ((getWidgetListener() != null) && getWidgetListener().isEnabled(iConstants.EVENT_HAS_EXPANDED)) {
      getWidgetListener().execute(iConstants.EVENT_HAS_EXPANDED, new ExpansionEvent(windowViewer,ExpansionEvent.Type.HAS_EXPANDED));
    }
  }

  @Override
  public void windowIconified(java.awt.event.WindowEvent e) {
    if ((getWidgetListener() != null) && getWidgetListener().isEnabled(iConstants.EVENT_HAS_COLLAPSED)) {
      getWidgetListener().execute(iConstants.EVENT_HAS_COLLAPSED, new ExpansionEvent(windowViewer,ExpansionEvent.Type.HAS_COLLAPSED));
    }
  }

  @Override
  public void windowOpened(java.awt.event.WindowEvent e) {
    Utils.fireWindowEvent(listenerList, this, WindowEvent.Type.Opened);
  }

  private aWidgetListener getWidgetListener() {
    return (windowViewer == null)
           ? null
           : windowViewer.getWidgetListener();
  }

  private void setMenuOrToolBarComponent(JComponent c, boolean menu) {
    if (c == null) {
      if (menuToolbarPanel != null) {
        BorderLayout bl = ((BorderLayout) menuToolbarPanel.getLayout());

        c = (JComponent) bl.getLayoutComponent(menu
                ? BorderLayout.BEFORE_FIRST_LINE
                : BorderLayout.CENTER);

        if (c != null) {
          menuToolbarPanel.remove(c);
        }
      }
    } else {
      if (menuToolbarPanel == null) {
        menuToolbarPanel = new JPanel(new BorderLayout());
        menuToolbarPanel.setOpaque(false);
        rootPaneContainer.getRootPane().getContentPane().add(menuToolbarPanel, BorderLayout.BEFORE_FIRST_LINE);
      }

      if (menu) {
        menuToolbarPanel.add(c, BorderLayout.BEFORE_FIRST_LINE);
      } else {
        menuToolbarPanel.add(c, BorderLayout.CENTER);
      }
    }
  }

  public void setWindowViewer(WindowViewer windowViewer) {
    this.windowViewer = windowViewer;

    iParentComponent pc = (iParentComponent) Component.fromView((JComponent) rootPaneContainer.getContentPane());

    pc.setWidget(windowViewer);
  }

  OverlayContainer getOverlayContainer() {
    if (overlayContainer == null) {
      iParentComponent pc = (iParentComponent) Component.fromView((JComponent) rootPaneContainer.getContentPane());

      if (pc instanceof BorderPanel) {
        BorderPanel        p  = (BorderPanel) pc;
        iPlatformComponent oc = p.getCenterView();

        overlayContainer = new OverlayContainer(oc);
        p.setCenterView(overlayContainer);
      } else {
        throw new ApplicationException("cannot add overlay to curent window content pane");
      }
    }

    return overlayContainer;
  }
}
