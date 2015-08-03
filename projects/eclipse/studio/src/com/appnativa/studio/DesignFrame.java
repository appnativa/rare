/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appnativa.studio;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JRootPaneEx;
import com.appnativa.rare.ui.ActionBar;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformMenuBar;
import com.appnativa.rare.ui.iStatusBar;
import com.appnativa.rare.ui.iToolBarHolder;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public class DesignFrame extends Container implements iWindow {
  JPanel                        menuToolbarPanel;
  protected int                 menuOffset  = 0;
  boolean                       undecorated = false;
  boolean                       transparent = false;
  protected iPlatformAppContext appContext;
  protected iPlatformMenuBar    menuBar;
  protected iStatusBar          statusBar;
  protected iTarget             target;
  protected iWidget             titleWidget;
  protected iToolBarHolder      toolbarHolder;
  protected JInternalFrame      window;
  protected WindowViewer        windowViewer;
  private boolean               autoDispose;
  private boolean               dialog;
  private boolean               locationSet;
  private boolean               modal;
  private boolean               popup;
  private RootPaneContainer     rootPaneContainer;
  private boolean               sizeSet;

  public DesignFrame(iPlatformAppContext app) {
    super(new JInternalFrameEx());
    this.window = (JInternalFrame) view;
    appContext = app;

    JComponent p = new JComponent() {
      @Override
      protected void paintComponent(Graphics g) {
        g.setColor(ColorUtils.DISABLED_TRANSPARENT_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
      }
    };

    p.setOpaque(false);
    window.setGlassPane(p);
    rootPaneContainer = window;
  }

  public void add(iPlatformComponent c, Object constraints, int position) {
    if (c != null) {
      iParentComponent pc = (iParentComponent) Component.fromView((JComponent) rootPaneContainer.getContentPane());

      pc.add(c);
    }
  }

  public void addOverlay(iPlatformComponent c) {
  }

  public void addWindowListener(iWindowListener l) {
    getEventListenerList().add(iWindowListener.class, l);
  }

  public void center() {
    SwingHelper.centerOnScreen(window);
  }

  public void close() {
    if (window != null) {
      window.dispose();
    }
  }

  public void disposeEx() {
    JInternalFrame w = window;

    window = null;

    if (w != null) {
      w.dispose();

      if (target != null) {
        target.dispose(false);
      }

      if (menuBar != null) {
        menuBar.dispose();
      }

      if (statusBar != null) {
        statusBar.dispose();
      }

      if (toolbarHolder != null) {
        toolbarHolder.dispose();
      }

      if (titleWidget != null) {
        titleWidget.dispose();
      }

      super.disposeEx();
      titleWidget = null;
      toolbarHolder = null;
      statusBar = null;
      window = null;
      windowViewer = null;
      menuBar = null;
      target = null;
      appContext = null;
    }
  }

  public void disposeOfWindow() {
    dispose();
  }

  public void hideWindow() {
    window.setVisible(false);
  }

  public void moveBy(float x, float y) {
    if ((x != 0) && (y != 0)) {
      x += window.getX();
      y += window.getY();
      window.setLocation((int)x, (int)y);
    }
  }

  public void moveTo(float x, float y) {
    window.setLocation((int)x, (int)y);
  }

  public void pack() {
    window.pack();
  }

  public void removeOverlay(iPlatformComponent c) {
    // TODO Auto-generated method stub
  }

  public void removeWindowListener(iWindowListener l) {
    if (listenerList != null) {
      listenerList.remove(iWindowListener.class, l);
    }
  }

  public void showWindow() {
    setVisible(true);
  }

  public void showWindow(int x, int y) {
    window.setLocation(x, y);
    setVisible(true);
  }

  public void toBack() {
    window.toBack();
  }

  public void toFront() {
    window.toFront();
  }

  public void update() {
    revalidate();
    repaint();
  }

  public void setAutoDispose(boolean autoDispose) {
    this.autoDispose = autoDispose;
  }

  public void setBounds(int x, int y, int width, int height) {
    setLocation(x, y);
    setSize(width, height);
  }

  public void setCanClose(boolean can) {
    if (windowViewer != null) {
      windowViewer.setCanClose(can);
    }
  }

  public void setDefaultButton(PushButtonWidget widget) {
    java.awt.Component b = widget.getContainerComponentEx().getView();

    if (b instanceof JButton) {
      rootPaneContainer.getRootPane().setDefaultButton((JButton) b);
    }
  }

  public void setLocation(float x, float y) {
    window.setLocation((int)x, (int)y);
    locationSet = true;
  }

  @Override
  public void setLocked(boolean lock) {
    super.setLocked(lock);
    window.getGlassPane().setVisible(lock);
  }

  public iPlatformMenuBar setMenuBar(iPlatformMenuBar mb) {
    iPlatformMenuBar omb = menuBar;
    JRootPane rp = rootPaneContainer.getRootPane();

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
      JComponent jm = (mb == null) ? null : mb.getContainerComponent().getView();

      setMenuOrToolBarComponent(jm, true);
    }

    menuBar = mb;

    return omb;
  }

  public void setMovable(boolean movable) {
  }

  public void setPartialLocation(UIPoint uiPoint) {
  }

  public void setPartialSize(UIDimension uiDimension) {
    // TODO Auto-generated method stub
  }

  public void setResizable(boolean resizable) {
    window.setResizable(resizable);
  }

  public void setRootPaneContainer(RootPaneContainer rootPaneContainer) {
    this.rootPaneContainer = rootPaneContainer;
  }

  public void setSize(float width, float height) {
    sizeSet = true;
    window.setSize((int)width, (int)height);
  }

  public iStatusBar setStatusBar(iStatusBar sb) {
    iStatusBar osb = statusBar;

    if (rootPaneContainer.getRootPane() instanceof JRootPaneEx) {
      BorderPanel bp = (BorderPanel) Component.fromView((JComponent) rootPaneContainer.getContentPane());

      bp.setBottomView((sb == null) ? null : sb.getComponent());
    }
    statusBar = sb;
    return osb;
  }

  public void setTarget(iTarget target) {
    this.target = target;
  }

  public void setTitle(String title) {
    window.setTitle(title);
  }

  public void setTitleWidget(iWidget widget) {
    titleWidget = widget;

    JComponent c = ((widget == null) ? null : widget.getContainerComponent().getView());
    JRootPane rp = rootPaneContainer.getRootPane();

    if (rp instanceof JRootPaneEx) {
      ((JRootPaneEx) rp).setTitlePane(c);
    }
  }

  public iToolBarHolder setToolBarHolder(iToolBarHolder tbh) {
    iToolBarHolder otbh = toolbarHolder;

    toolbarHolder = tbh;

    JComponent c = (otbh == null) ? null : otbh.getComponent().getView();

    if (c != null) {
      rootPaneContainer.getContentPane().remove(c);
    }

    c = (tbh == null) ? null : tbh.getComponent().getView();
    setMenuOrToolBarComponent(c, false);

    return otbh;
  }

  public void setViewer(WindowViewer wv) {
    windowViewer = wv;

    iParentComponent pc = (iParentComponent) Component.fromView((JComponent) rootPaneContainer.getContentPane());

    pc.setWidget(wv);
  }

  public void setVisible(boolean visible) {
    if (window != null) {
      if (visible) {
        Platform.getAppContext().closePopupWindows(false);

        if (!sizeSet) {
          pack();
        }

        if (!locationSet) {
          SwingHelper.centerOnScreen(window);
        }
      }

      if (visible && modal) {
        Platform.invokeLater(new Runnable() {
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

  public iPlatformComponent getComponent() {
    return this;
  }

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
  public int getScreenX() {
    return (int) window.getX();
  }

  /**
   * Get the y-position of the window's current screen location
   *
   * @return the y-position of the window's current screen location
   */
  public int getScreenY() {
    return (int) window.getY();
  }

  public iStatusBar getStatusBar() {
    return statusBar;
  }

  public iTarget getTarget() {
    return target;
  }

  public String getTargetName() {
    return (target == null) ? null : target.getName();
  }

  public String getTitle() {
    return window.getTitle();
  }

  public iToolBarHolder getToolBarHolder() {
    return toolbarHolder;
  }

  public Object getUIWindow() {
    return window;
  }

  public iContainer getViewer() {
    return windowViewer;
  }

  public WindowViewer getWindowViewer() {
    return windowViewer;
  }

  public boolean isAutoDispose() {
    return autoDispose;
  }

  public boolean isClosingAllowed() {
    return (windowViewer == null) ? true : windowViewer.isClosingAllowed();
  }

  public boolean isDialog() {
    return dialog;
  }

  public boolean isPopup() {
    return popup;
  }

  public boolean isTransparent() {
    return undecorated;
  }

  public boolean isUndecorated() {
    return undecorated;
  }

  private void setMenuOrToolBarComponent(JComponent c, boolean menu) {
    if (c != null) {
      if (menuToolbarPanel == null) {
        menuToolbarPanel = new JPanel(new BorderLayout());
        menuToolbarPanel.setOpaque(false);
        rootPaneContainer.getRootPane().getContentPane().add(menuToolbarPanel, BorderLayout.BEFORE_FIRST_LINE);
      }

      if (menu) {
        menuToolbarPanel.add(c, BorderLayout.BEFORE_FIRST_LINE);
      } else {
        menuToolbarPanel.add(c);
      }
    }
  }
//=================
//Used for debuging purposes when un commented
//  protected static class JRootPaneExX extends JRootPaneEx {
//    @Override
//    protected LayoutManager createRootLayout() {
//      return new RootLayoutExX();
//    }
//
//    protected java.awt.Container createContentPane() {
//      BorderPanel bp = new BorderPanel(new BorderLayoutViewXX());
//      JComponent c = bp.getJComponent();
//
//      c.setOpaque(true);
//      c.setName(this.getName() + ".contentPane");
//
//      return c;
//    }
//
//    static class BorderLayoutViewXX extends BorderLayoutView {
//      public BorderLayoutViewXX() {
//        super();
//      }
//
//      @Override
//      public void remove(java.awt.Component comp) {
//        super.remove(comp);
//      }
//      @Override
//      public void add(iPlatformComponent c, Object constraints, int position) {
//        super.add(c, constraints, position);
//      }
//      protected void layoutContainerEx(int width, int height) {
//        super.layoutContainerEx(width, height);
//      }
//    }
//
//    protected class RootLayoutExX extends RootLayoutEx {
//      @Override
//      public void layoutContainer(java.awt.Container parent) {
//        super.layoutContainer(parent);
//      }
//    }
//  }
//

  static class JInternalFrameEx extends JInternalFrame {
    @Override
    protected JRootPane createRootPane() {
      return new JRootPaneEx();
    }
    
    @Override
    public void dispose() {
      ((JRootPaneEx)getRootPane()).disposeOfPane();
      super.dispose();
    }
  }
}
