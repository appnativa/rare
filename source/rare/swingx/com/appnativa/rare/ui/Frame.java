/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appnativa.rare.ui;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.WindowListener;
import java.util.HashMap;
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
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JDialogEx;
import com.appnativa.rare.platform.swing.ui.view.JFrameEx;
import com.appnativa.rare.platform.swing.ui.view.JRootPaneEx;
import com.appnativa.rare.platform.swing.ui.view.PopupWindow;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.StatusBar;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.FocusEvent;
import com.appnativa.rare.ui.event.WindowEvent;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.SNumber;

/**
 *
 * @author Don DeCoteau
 */
public class Frame extends Container implements iWindow, WindowListener {
  protected int                 menuOffset            = 0;
  protected boolean             disposeOfNativeWindow = true;
  boolean                       undecorated           = false;
  boolean                       transparent           = false;
  protected iPlatformAppContext appContext;
  protected iPlatformMenuBar    menuBar;
  protected iStatusBar          statusBar;
  protected iTarget             target;
  protected iWidget             titleWidget;
  protected iToolBarHolder      toolbarHolder;
  protected Window              window;
  protected WindowViewer        windowViewer;
  private boolean               autoDispose;
  private boolean               dialog;
  private boolean               locationSet;
  private JPanel                menuToolbarPanel;
  private boolean               modal;
  private boolean               popup;
  private RootPaneContainer     rootPaneContainer;
  private boolean               sizeSet;
  private OverlayContainer      overlayContainer;

  public Frame(iPlatformAppContext app, Window win, RootPaneContainer rpc) {
    super(rpc.getRootPane());
    this.window = win;
    appContext = app;
    rootPaneContainer = rpc;
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
      window.dispose();
    }
  }

  @Override
  public void disposeEx() {
    Window w = window;

    window = null;

    if (w != null) {
      try {
        w.removeWindowListener(this);
      } catch (Exception ignore) {
      }

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

  @Override
  public void disposeOfWindow() {
    dispose();
  }

  @Override
  public iWidget getWidget() {
    return windowViewer;
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
      if (toolbarHolder != null && toolbarHolder.getComponent().isVisible()) {
        rect.height -= h = toolbarHolder.getComponent().getHeight();
        rect.y += h;
      }
      if (statusBar != null && statusBar.getComponent().isVisible()) {
        rect.height -= h = statusBar.getComponent().getHeight();
        rect.y += h;
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
    size.width = UIScreen.snapToSize(rect.width);
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
    return (target == null) ? null : target.getName();
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

  private aWidgetListener getWidgetListener() {
    return (windowViewer == null) ? null : windowViewer.getWidgetListener();
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
    if (c != null && overlayContainer != null) {
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
  public UIRectangle getBounds() {
    return SwingHelper.setUIRectangle(null, window.getBounds());
  }

  @Override
  public void setCanClose(boolean can) {
    if (windowViewer != null) {
      windowViewer.setCanClose(can);
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    iParentComponent pc = (iParentComponent) Component.fromView((JComponent) rootPaneContainer.getContentPane());

    pc.setComponentPainter(cp);
  }

  public void setDefaultButton(PushButtonWidget widget) {
    java.awt.Component b = widget.getContainerComponentEx().getView();

    if (b instanceof JButton) {
      rootPaneContainer.getRootPane().setDefaultButton((JButton) b);
    }
  }

  public void setLocation(int x, int y) {
    window.setLocation(x, y);
    locationSet = true;
  }

  @Override
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

  private void setMenuOrToolBarComponent(JComponent c, boolean menu) {
    if (c == null) {
      if (menuToolbarPanel != null) {
        BorderLayout bl = ((BorderLayout) menuToolbarPanel.getLayout());

        c = (JComponent) bl.getLayoutComponent(menu ? BorderLayout.BEFORE_FIRST_LINE : BorderLayout.CENTER);

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

  public void setMovable(boolean movable) {
  }

  public void setPartialLocation(UIPoint uiPoint) {
  }

  public void setPartialSize(UIDimension uiDimension) {
  }

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
  public iStatusBar setStatusBar(iStatusBar sb) {
    iStatusBar osb = statusBar;
    statusBar = sb;

    if (rootPaneContainer.getRootPane() instanceof JRootPaneEx) {
      BorderPanel bp = (BorderPanel) Component.fromView((JComponent) rootPaneContainer.getContentPane());

      bp.setBottomView((sb == null) ? null : sb.getComponent());
    }

    return osb;
  }

  public void setTarget(iTarget target) {
    this.target = target;
  }

  @Override
  public void setTitle(String title) {
    if (window instanceof java.awt.Frame) {
      ((java.awt.Frame) window).setTitle(title);
    } else if (window instanceof java.awt.Dialog) {
      ((java.awt.Dialog) window).setTitle(title);
    }
  }

  public void setTitleWidget(iWidget widget) {
    titleWidget = widget;

    JComponent c = (widget == null) ? null : widget.getContainerComponent().getView();
    JRootPane rp = rootPaneContainer.getRootPane();

    if (rp instanceof JRootPaneEx) {
      ((JRootPaneEx) rp).setTitlePane(c);
    }
  }

  @Override
  public iToolBarHolder setToolBarHolder(iToolBarHolder tbh) {
    iToolBarHolder otbh = toolbarHolder;

    if (rootPaneContainer.getRootPane() instanceof JRootPaneEx) {
      BorderPanel bp = (BorderPanel) Component.fromView((JComponent) rootPaneContainer.getContentPane());

      bp.setTopView((tbh == null) ? null : tbh.getComponent());
    } else {
      JComponent c = (tbh == null) ? null : tbh.getComponent().getView();

      setMenuOrToolBarComponent(c, false);
    }

    toolbarHolder = tbh;

    return otbh;
  }

  protected void setupWindow(WindowViewer parent, Map options, boolean emulate) {
    if (parent == null) {
      parent = appContext.getWindowViewer();
    }

    Frame main = (Frame) appContext.getWindowManager().getMainWindow().getComponent();
    iPlatformComponent mainComponent = main.getComponent();
    iScriptHandler sh = parent.getScriptHandler();

    windowViewer = new WindowViewer(appContext, target.getName(), this, parent, sh);
    sh.setScriptingContext(windowViewer, null, null, null, true);

    if (emulate) {
      PaintBucket pb = (PaintBucket) mainComponent.getClientProperty(iConstants.RARE_PAINTBUCKET_PROPERTY);

      pb.install(this);

      ActionLink link = (ActionLink) mainComponent.getClientProperty(iConstants.RARE_WINDOW_TITLELINK);

      if (link != null) {
        iWidget w = aContainer.createWidget(windowViewer, link);

        if (w != null) {
          setTitleWidget(w);
        }
      }
    }

    if (options == null) {
      return;
    }

    Object o = options.get("cpborder");
    iPlatformBorder b = null;

    if (o instanceof iPlatformBorder) {
      b = (iPlatformBorder) o;

      if (o != null) {
        b = UIBorderHelper.createBorder(o.toString());

        if (b != null) {
          setBorder(b);

          // if ("true".equalsIgnoreCase((String)
          // options.get("useborderforsizing"))) {
          // WindowAdapter.makeWindowResizable(theWindow);
          // }
        }
      }
    }

    String s = (String) options.get("status");

    if ((s != null) && (s.length() > 0)) {
      s = s.trim();

      if (statusBar == null) {
        StatusBar sb = null;

        if (SNumber.booleanValue((String) options.get("resizable"))
            && !"false".equalsIgnoreCase((String) options.get("resizeCorner"))) {
          sb = new StatusBar();
          sb.showMemoryUsage.setValue(false);
          sb.showInsertOverwrite.setValue(false);
          sb.showTime.setValue(false);
          sb.showResizeCorner.setValue(true);
        }

        setStatusBar(appContext.getWindowManager().createStatusBar(sb));
      }

      statusBar.showMessage(s);
    }

    s = (String) options.get("keystrokes");

    if (s != null) {
      SwingHelper.configureKeystrokes(parent, rootPaneContainer.getRootPane(), s, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    Map map = WidgetListener.createEventMap(options);

    if (map != null) {
      windowViewer.setWidgetListener(new WidgetListener(windowViewer, map, windowViewer.getScriptHandler()));
    }
    Utils.setupWindowOptions(this, map);
  }

  public void setViewer(WindowViewer wv) {
    windowViewer = wv;

    if (wv != null) {
      iParentComponent pc = (iParentComponent) Component.fromView((JComponent) rootPaneContainer.getContentPane());

      pc.setWidget(wv);
    }
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
      FocusEvent fe = new FocusEvent(this, FocusEvent.FOCUS_GAINED, false);

      getWidgetListener().execute(iConstants.EVENT_FOCUS, fe);
    }
  }

  @Override
  public void windowClosed(java.awt.event.WindowEvent e) {
    if ((listenerList != null) && listenerList.hasListeners(iWindowListener.class)) {
      Utils.fireWindowEvent(listenerList, new WindowEvent(this, WindowEvent.Type.Closed));
    }
  }

  @Override
  public void windowClosing(java.awt.event.WindowEvent e) {
    if ((listenerList != null) && listenerList.hasListeners(iWindowListener.class)) {
      Utils.fireWindowEvent(listenerList, new WindowEvent(this, WindowEvent.Type.WillClose));
    }

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

  @Override
  public void windowDeactivated(java.awt.event.WindowEvent e) {
    if ((getWidgetListener() != null) && getWidgetListener().isEnabled(iConstants.EVENT_BLUR)) {
      FocusEvent fe = new FocusEvent(this, FocusEvent.FOCUS_LOST, false);

      getWidgetListener().execute(iConstants.EVENT_BLUR, fe);
    }
  }

  @Override
  public void windowDeiconified(java.awt.event.WindowEvent e) {
    if ((getWidgetListener() != null) && getWidgetListener().isEnabled(iConstants.EVENT_HAS_EXPANDED)) {
      getWidgetListener().execute(iConstants.EVENT_HAS_EXPANDED, new ExpansionEvent(windowViewer));
    }
  }

  @Override
  public void windowIconified(java.awt.event.WindowEvent e) {
    if ((getWidgetListener() != null) && getWidgetListener().isEnabled(iConstants.EVENT_HAS_COLLAPSED)) {
      getWidgetListener().execute(iConstants.EVENT_HAS_COLLAPSED, new ExpansionEvent(windowViewer));
    }
  }

  @Override
  public void windowOpened(java.awt.event.WindowEvent e) {
    if ((listenerList != null) && listenerList.hasListeners(iWindowListener.class)) {
      Utils.fireWindowEvent(listenerList, new WindowEvent(this, WindowEvent.Type.Opened));
    }

  }

  public static Frame create(iWidget context, String title, String target, boolean modal, boolean decorated) {
    Map map = new HashMap();

    if (title != null) {
      map.put("title", title);
    }

    map.put("modal", modal);
    map.put("decorated", decorated);

    return createFromOptions(context, target, map);
  }

  public static Frame createFromOptions(iWidget context, String targetName, Map options) {
    iPlatformAppContext app = context.getAppContext();
    boolean modal = true;
    boolean dialog = false;
    boolean popup = false;
    String type = "frame";
    Object o;
    WindowViewer parentv = Platform.getWindowViewer(context);

    if (parentv == null) {
      parentv = (WindowViewer) Platform.getWindowViewer().getTop();
    }

    Window parent = (Window) parentv.getUIWindow();

    o = options.get("modal");

    if (o instanceof Boolean) {
      modal = (Boolean) o;
    } else if (o instanceof String) {
      modal = SNumber.booleanValue((String) o);
    }

    type = (String) options.get("windowtype");

    if (type == null) {
      type = "frame";
    }

    Window win;

    o = options.get("emulatemainwindow");

    if (o == null) {
      o = app.getUIDefaults().get("Rare.emulateMainWindow");
    }

    boolean emulate = ((o == Boolean.TRUE) || "true".equals(o));
    boolean undecorated = false;
    boolean transparent = false;

    if (emulate) {
      Frame main = (Frame) app.getWindowManager().getMainWindow().getComponent();

      undecorated = main.isUndecorated();
      transparent = main.isTransparent();
    }

    o = options.get("opaque");

    if (o != null) {
      transparent = "false".equals(o) || (o == Boolean.FALSE);
    }

    o = options.get("decorated");

    if (o != null) {
      undecorated = "false".equals(o) || (o == Boolean.FALSE);
    }

    if (type.equalsIgnoreCase("dialog")) {
      dialog = true;

      JDialog d = new JDialogEx(parent, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);

      d.setBackground(ColorUtils.getBackground());
      d.setUndecorated(undecorated);

      if (transparent) {
        d.setBackground(ColorUtils.TRANSPARENT_COLOR);
      } else {
        d.setBackground(ColorUtils.getBackground());
      }

      win = d;
      popup = true;
    } else if (type.equalsIgnoreCase("popup")) {
      PopupWindow p = new PopupWindow(parent, context);

      if (!transparent) {
        p.setBackground(ColorUtils.getBackground());
      }

      if (modal) {
        p.setModal(true);
      }

      win = p;
      popup = true;
    } else if (type.equalsIgnoreCase("popup_orphan")) {
      PopupWindow p = new PopupWindow(context);

      if (!transparent) {
        p.setBackground(ColorUtils.getBackground());
      }

      if (modal) {
        p.setModal(true);
      }

      win = p;
    } else {
      JFrame f = new JFrameEx();

      f.setUndecorated(undecorated);

      if (transparent) {
        f.setBackground(ColorUtils.TRANSPARENT_COLOR);
      } else {
        f.setBackground(ColorUtils.getBackground());
      }

      win = f;
    }

    if (targetName == null) {
      targetName = "_new_window_" + Integer.toHexString(win.hashCode());
    }

    Frame frame = new Frame(app, win, (RootPaneContainer) win);

    frame.target = new WindowTarget(app, targetName, frame);
    frame.dialog = dialog;
    frame.popup = popup;
    frame.modal = modal;
    frame.setupWindow(parentv, options, emulate);

    return frame;
  }

  OverlayContainer getOverlayContainer() {
    if (overlayContainer == null) {
      iParentComponent pc = (iParentComponent) Component.fromView((JComponent) rootPaneContainer.getContentPane());
      if (pc instanceof BorderPanel) {
        BorderPanel p = (BorderPanel) pc;
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
