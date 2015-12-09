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
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.UITarget;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.WindowPane;
import com.appnativa.rare.ui.WindowTarget;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.effects.iAnimator.Direction;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IdentityArrayList;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * A popup window
 *
 * @author Don DeCoteau
 */
public class PopupWindow extends JDialog implements iPopup, WindowFocusListener {
  float                       x = Short.MIN_VALUE;
  float                       y = Short.MIN_VALUE;
  boolean                     canceled;
  Component                   content;
  iWidget                     contextWidget;
  iPlatformComponent          owner;
  WindowPane                  windowPane;
  UITarget                    target;
  int                         timeout;
  protected iPlatformAnimator animator;
  protected EventListenerList listenerList;
  protected boolean           visibleIsforward;
  private AWTEventListener    _awtEventListener;
  private boolean             istransient;
  private boolean             sizeSet;

  /**
   * Creates a new instance
   *
   */
  public PopupWindow() {
    this((Window) Platform.getWindowViewer().getUIWindow(), Platform.getWindowViewer());
  }

  /**
   * Creates a new instance of PopupFrame
   *
   * @param frame
   *          the frame
   * @param context
   *          the context
   */
  public PopupWindow(iWidget context) {
    this((Window) context.getWindow().getUIWindow(), context);
  }

  /**
   * Creates a new instance
   *
   * @param frame
   *          the frame
   * @param context
   *          the context
   */
  public PopupWindow(Window frame, iWidget context) {
    super(frame);
    initialize(context);
  }

  @Override
  public void addPopupMenuListener(iPopupMenuListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    listenerList.add(iPopupMenuListener.class, l);
  }

  @Override
  public void setBackgroundColor(UIColor bg) {
    super.setBackground(bg);
  }

  @Override
  public void cancelPopup(boolean useAnimation) {
    canceled = true;

    if (useAnimation && (animator != null)) {
      setVisible(false);
    } else {
      setVisibleEx(false);
    }
  }

  @Override
  public void hidePopup() {
    setVisible(false);
  }

  @Override
  public void removePopupMenuListener(iPopupMenuListener l) {
    if (listenerList != null) {
      listenerList.remove(iPopupMenuListener.class, l);
    }
  }

  @Override
  public void showModalPopup() {
    setFocusable(true);
    setModal(true);
    setVisible(true);
  }

  @Override
  public void showPopup() {
    showPopup(owner, x, y);
  }

  @Override
  public void showPopup(float x, float y) {
    showPopup(owner, x, y);
  }

  public void showPopup(int x, int y) {
    showPopup(owner, x, y);
  }

  @Override
  public void showPopup(iPlatformComponent ref, float x, float y) {
    if (ref != null) {
      owner = ref;
    }

    this.x = x;
    this.y = y;
    setVisible(true);
  }

  @Override
  public void windowGainedFocus(WindowEvent e) {
    windowPane.windowGainedFocus();
  }

  @Override
  public void windowLostFocus(WindowEvent e) {
    windowPane.windowLostFocus();
  }

  @Override
  public void setAnimator(iPlatformAnimator animator) {
    this.animator = animator;

    if (this.animator != null) {
      visibleIsforward = animator.getDirection() == Direction.FORWARD;
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter painter) {
    windowPane.setComponentPainter(painter);
  }

  @Override
  public void setContent(iPlatformComponent component) {
    windowPane.setContent(component);
    content = (component == null)
              ? null
              : component.getView();
  }

  @Override
  public void setFocusable(boolean focusable) {
    super.setFocusable(focusable);
    setFocusableWindowState(focusable);
  }

  @Override
  public void setMovable(boolean moveble) {}

  @Override
  public void setOptions(Map<String, String> options) {}

  @Override
  public void setPopupLocation(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public void setPopupLocation(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public void setPopupOwner(iPlatformComponent component) {
    owner = component;
  }

  @Override
  public void setResizable(boolean resizable) {}

  @Override
  public void setSize(float width, float height) {
    super.setSize(UIScreen.snapToSize(width), UIScreen.snapToSize(height));
    sizeSet = true;
  }

  @Override
  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  @Override
  public void setTitle(String title) {
    windowPane.setTitle(title);
  }

  @Override
  public void setTransient(boolean istransient) {
    this.istransient = istransient;
    setFocusable(!istransient);
    setModal(!istransient);
  }

  @Override
  public iViewer setViewer(iViewer viewer) {
    return target.setViewer(viewer);
  }

  @Override
  public void dispose() {
    Platform.getAppContext().getActiveWindows().remove(this);
    if (_awtEventListener != null) {
      removeMouseEventHandler();
    }

    if (target != null) {
      target.dispose(false);
    }

    target = null;

    if (windowPane != null) {
      setContentPane(new JPanel());
      windowPane.dispose();
    }

    windowPane = null;
    super.dispose();
  }

  @Override
  public void setVisible(final boolean visible) {
    if (visible == isVisible()) {
      return;
    }

    if (animator == null) {
      setVisibleEx(visible);
    } else {
      if (visible) {
        windowPane.setVisible(false);
        setVisibleEx(visible);
      }

      aAnimator.setupTogglingAnimator(windowPane, animator, visible, visibleIsforward);
      animator.addListener(new iAnimatorListener() {
        @Override
        public void animationStarted(iPlatformAnimator source) {
          if (windowPane != null) {
            windowPane.setVisible(true);
          }
        }
        @Override
        public void animationEnded(iPlatformAnimator source) {
          source.removeListener(this);
          source.setDirection(visibleIsforward
                              ? Direction.FORWARD
                              : Direction.BACKWARD);    // reset

          // direction
          if (windowPane != null) {
            if (!visible) {
              setVisibleEx(false);
            } else {
              windowPane.revalidate();
              repaint();
            }
          }
        }
      });
      animator.animate(windowPane, null);
    }
  }

  public void setWindowIcon(iPlatformIcon icon) {
    windowPane.setIcon(icon);
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension d = super.getPreferredSize();

    if (content instanceof ScrollPaneEx) {
      ScrollPaneEx s = (ScrollPaneEx) content;

      d.width += s.getVerticalScrollBar().getPreferredSize().width;
    }

    return d;
  }

  @Override
  public void getPreferredSize(UIDimension size) {
    windowPane.getPreferredSize(size);
  }

  /**
   * Gets the window's title
   *
   * @return the window's title
   */
  @Override
  public String getTitle() {
    return windowPane.getTitle();
  }

  @Override
  public WindowPane getWindowPane() {
    return windowPane;
  }

  @Override
  public boolean isTransient() {
    return istransient;
  }

  /**
   * Called by the constructor to set up the <code>JRootPane</code>.
   *
   * @return a new <code>JRootPane</code>
   * @see javax.swing.JRootPane
   */
  @Override
  protected JRootPane createRootPane() {
    if (windowPane == null) {
      windowPane = new WindowPane(Platform.getWindowViewer().getTop());
    }

    return new JRootPaneEx() {
      @Override
      protected java.awt.Container createContentPane() {
        return windowPane.getJComponent();
      }
    };
  }

  protected void handleComponentEvent(ComponentEvent event) {
    if ((event.getID() == ComponentEvent.COMPONENT_HIDDEN) && (owner != null) &&!owner.isShowing()) {
      cancelPopup(true);
    }
  }

  protected void handleMouseEvent(MouseEvent event) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      Component comp = event.getComponent();

      if ((comp != this) && isTransient() && (SwingUtilities.windowForComponent(comp) != this)) {
        IdentityArrayList windows = Platform.getAppContext().getActiveWindows();
        int               len     = windows.size();

        if ((len > 0) && (windows.get(len - 1) == this)) {
          event.consume();
          cancelPopup(true);
        }
      }
    }
  }

  protected void handleWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      if ((owner != null) && (e.getSource() == SwingUtilities.windowForComponent(owner.getView()))) {
        cancelPopup(false);
      }
    } else if (isTransient() && (e.getID() == WindowEvent.WINDOW_DEACTIVATED)) {
      if ((e.getWindow() == this) ||!getFocusableWindowState()) {
        cancelPopup(false);
      }
    }
  }

  protected void initialize() {
    if (!(this.getContentPane() instanceof JComponent)) {
      this.setContentPane(new UtilityPanel());
    }

    this.addWindowFocusListener(this);

    AbstractAction action = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        processWindowEvent(new WindowEvent(PopupWindow.this, WindowEvent.WINDOW_CLOSING));
      }
    };
    KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

    super.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(stroke, "cancel");
    super.getRootPane().getActionMap().put("cancel", action);
  }

  protected void initialize(iWidget context) {
    contextWidget = context;
    windowPane    = new WindowPane(context);

    String targetName = "_popup_window_" + Integer.toHexString(hashCode());

    target = new WindowTarget(Platform.getAppContext(), targetName, windowPane);
    setContentPane(windowPane.getJComponent());
    setUndecorated(true);
    setFocusable(false);
    setBackground(ColorUtils.TRANSPARENT_COLOR);
    getRootPane().putClientProperty("Window.shadow", Boolean.FALSE);
    getRootPane().putClientProperty("apple.awt.draggableWindowBackground", Boolean.FALSE);
  }

  protected void preparePopup() {
    if (timeout > 0) {
      ActionListener l = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          cancelPopup(true);
        }
      };
      Timer t = new Timer(timeout, l);

      t.setRepeats(false);
      t.start();
    }

    if (isTransient()) {}
  }

  protected void setVisibleEx(boolean visible) {
    if (visible) {
      canceled = false;

      if (listenerList != null) {    // can be null if disposed was called
        ExpansionEvent  e = new ExpansionEvent(PopupWindow.this,ExpansionEvent.Type.WILL_EXPAND);
        Utils.firePopupEvent(listenerList, e, true);
      }

      if (!sizeSet) {
        pack();
      }

      float xx = x;
      float yy = y;

      if ((x == Short.MIN_VALUE) || (y == Short.MIN_VALUE)) {
        Component c;

        if (owner != null) {
          c = owner.getView();
        } else if (contextWidget != null) {
          c = contextWidget.getContainerComponent().getView();
        } else {
          c = Platform.getWindowViewer().getContainerView();
        }

        Point p = SwingHelper.getCenterPoint(c, this);

        if (xx == Short.MIN_VALUE) {
          xx = p.x;
        }

        if (yy == Short.MIN_VALUE) {
          yy = p.y;
        }
      } else if (owner != null) {
        UIPoint     loc  = owner.getLocationOnScreen(null);
        UIDimension size = owner.getOrientedSize(null);

        xx += loc.x;
        yy += loc.y + size.height;
      }

      setLocation(UIScreen.snapToPosition(xx), UIScreen.snapToPosition(yy));

    } else {
      removeMouseEventHandler();

      if (listenerList != null) {    // can be null if disposed was called
        ExpansionEvent e = new ExpansionEvent(PopupWindow.this,ExpansionEvent.Type.WILL_COLLAPSE);
        Utils.firePopupEvent(listenerList, e, false);
      }
    }
    if(visible) {
      if (istransient) {
        addMouseEventHandler();
        setModalityType(ModalityType.MODELESS);
      }
      else {
        setFocusable(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
      }
    }
    super.setVisible(visible);

    if (visible) {
      Platform.getAppContext().getActiveWindows().add(this);

      if (timeout > 0) {
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            if (isVisible()) {
              setVisible(false);
            }
          }
        }, timeout);
      }
    } else {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          dispose();
        }
      });
    }
  }

  private void addMouseEventHandler() {
    if (_awtEventListener == null) {
      _awtEventListener = new AWTEventListener() {
        @Override
        public void eventDispatched(AWTEvent event) {
          if (event instanceof WindowEvent) {
            handleWindowEvent((WindowEvent) event);
          } else if (event instanceof MouseEvent) {
            handleMouseEvent((MouseEvent) event);
          } else if (event instanceof ComponentEvent) {
            handleComponentEvent((ComponentEvent) event);
          }
        }
      };
    }

    try {
      java.security.AccessController.doPrivileged(new java.security.PrivilegedAction<Object>() {
        @Override
        public Object run() {
          Toolkit.getDefaultToolkit().addAWTEventListener(_awtEventListener,
                  AWTEvent.MOUSE_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK | AWTEvent.COMPONENT_EVENT_MASK);

          return null;
        }
      });
    } catch(SecurityException e) {
      throw new RuntimeException(e);
    }
  }

  private void removeMouseEventHandler() {
    if (_awtEventListener != null) {
      try {
        java.security.AccessController.doPrivileged(new java.security.PrivilegedAction<Object>() {
          @Override
          public Object run() {
            Toolkit.getDefaultToolkit().removeAWTEventListener(_awtEventListener);
            _awtEventListener = null;

            return null;
          }
        });
      } catch(SecurityException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
