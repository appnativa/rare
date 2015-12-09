/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appnativa.studio;

import java.util.Map;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.ui.view.JRootPaneEx;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.WindowPane;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iWindowManager.iFrame;
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
public class DesignFrame extends WindowPane implements iFrame {
  JPanel                        menuToolbarPanel;
  protected int                 menuOffset  = 0;
  boolean                       undecorated = false;
  boolean                       transparent = false;
  protected iPlatformAppContext appContext;
  protected iTarget             target;
  protected WindowViewer        windowViewer;
  private boolean               autoDispose;
  private boolean               dialog;
  private boolean               popup;

  public DesignFrame(iPlatformAppContext app) {
    super(app.getRootViewer());
    appContext = app;
  }

  public void addOverlay(iPlatformComponent c) {
  }

  public void addWindowListener(iWindowListener l) {
    getEventListenerList().add(iWindowListener.class, l);
  }

  public void center() {
  }

  public void close() {
  }

  public void disposeEx() {
    if (target != null) {
      target.dispose(false);
    }

    super.disposeEx();
    windowViewer = null;
    target = null;
    appContext = null;
  }

  public void disposeOfWindow() {
    dispose();
  }

  public void hideWindow() {
  }

  public void moveBy(float x, float y) {
  }

  public void moveTo(float x, float y) {
  }

  public void pack() {
  }

  public void removeOverlay(iPlatformComponent c) {
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
    setVisible(true);
  }

  public void toBack() {
  }

  public void toFront() {
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
  }

  @Override
  public void setLocked(boolean lock) {
    setEnabled(!locked);
  }

  public void setMovable(boolean movable) {
  }

  public void setPartialLocation(UIPoint uiPoint) {
  }

  public void setPartialSize(UIDimension uiDimension) {
  }

  public void setResizable(boolean resizable) {
  }

  public void setTarget(iTarget target) {
    this.target = target;
  }

  public void setTitleWidget(iWidget widget) {
    super.setTitileBar(widget==null ? null : widget.getContainerComponent());
  }

  /**
   * Get the x-position of the window's current screen location
   *
   * @return the x-position of the window's current screen location
   */
  public int getScreenX() {
    return (int) getX();
  }

  /**
   * Get the y-position of the window's current screen location
   *
   * @return the y-position of the window's current screen location
   */
  public int getScreenY() {
    return (int) getY();
  }

  public iTarget getTarget() {
    return target;
  }

  public String getTargetName() {
    return (target == null) ? null : target.getName();
  }

  public Object getUIWindow() {
    return getView();
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

  @Override
  public void setWindowViewer(WindowViewer w) {
    windowViewer = w;
    setWidget(w);
  }

  @Override
  public void finishWindowSetup(Map options) {
  }
}
