/*
 * @(#)WaitCursorHandler.java   2011-11-15
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.platform.swing.ui.view.PopupWindow;
import com.appnativa.rare.platform.swing.ui.view.ProgressBarView;
import com.appnativa.rare.platform.swing.ui.view.UtilityPanel;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.BeanViewer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IdentityArrayList;
import com.appnativa.util.MutableInteger;
import com.appnativa.util.iCancelable;

/**
 * Wait cursor handler
 *
 * @author Don DeCoteau
 */
public class WaitCursorHandler {
  private final static MouseAdapter           mouseAdapter   = new MouseAdapter() {
                                                             };
  private static IdentityArrayList<JRootPane> lockedPanes    = new IdentityArrayList<JRootPane>();
  private static final MutableInteger         cursorCount    = new MutableInteger(0);
  private static Cursor                       WAIT_CURSOR    = new Cursor(Cursor.WAIT_CURSOR);
  private static Cursor                       DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
  private static JDialog                      progressDialog;
  private static LabelView                    progressDialogLabel;

  static iPlatformComponentPainter componentPainter;

  /** Creates a new instance of CursorHandler */
  private WaitCursorHandler() {
  }

  public static void hideProgressPopup(iPlatformComponent comp, boolean force) {
    stopWaitCursor(comp, force);
  }

  public static void showProgressPopup(iPlatformComponent comp, CharSequence message) {
    showProgressPopup(comp, message, null);
  }

  public static void showProgressPopup(iPlatformComponent component, final CharSequence message, final iCancelable cancelable) {
    synchronized (cursorCount) {
      int cnt = cursorCount.getAndIncrement();
      if (cnt > 0) {
        return;
      }
      Runnable r = new Runnable() {

        @Override
        public void run() {
          if (componentPainter == null) {
            componentPainter = PainterUtils.createProgressPopupPainter();
          }
          UIColor fg=componentPainter.getForegroundColor();
          if(fg==null) {
            fg=ColorUtils.getForeground();
          }
          Window win = (Window) Platform.getAppContext().getWindowManager().getUIWindow();
          PopupWindow d = new PopupWindow(win, null);

          LabelView l = new LabelView(message == null ? "" : message.toString());

          l.setBorder(new UIEmptyBorder(4, 4, 4, 4));
          l.setForeground(fg);
          l.setWordWrap(true);
          UtilityPanel p = new UtilityPanel(l);
          if(Platform.isInitialized()) {
            ActionComponent pb=new ActionComponent(new LabelView());
            pb.setIcon(UISpriteIcon.getDefaultSpinner());
            p.add(pb.getJComponent(), java.awt.BorderLayout.WEST);
          }
          else {
            ProgressBarView pb = new ProgressBarView();
            pb.setPreferredSize(new Dimension(32, 32));
            pb.setIndeterminate(true);
            p.add(pb, java.awt.BorderLayout.WEST);
          }
          Container pc=new Container(p);
          if(cancelable!=null) {
            iActionComponent b = PlatformHelper.createNakedButton(pc, false, 0);
            b.setIcon(Platform.getResourceAsIcon("Rare.icon.cancel"));
            b.setForeground(fg);
            b.addActionListener(new iActionListener() {
              
              @Override
              public void actionPerformed(com.appnativa.rare.ui.event.ActionEvent e) {
                if (progressDialog != null) {
                  if (cancelable != null) {
                    cancelable.cancel(true);
                  }

                  progressDialog.setVisible(false);
                }
              }
            });
            p.add(b.getView(), java.awt.BorderLayout.EAST);
          }
          BeanViewer bv = new BeanViewer(Platform.getWindowViewer(), pc);
          bv.setComponentPainter(componentPainter);
          d.setViewer(bv);
          d.pack();
          KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

          d.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              if (progressDialog != null) {
                if (cancelable != null) {
                  cancelable.cancel(true);
                }

                progressDialog.setVisible(false);
              }
            }
          }, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
          progressDialog = d;
          progressDialogLabel = l;
          SwingHelper.center(win, d);
          JRootPane root = null;
          if (win instanceof RootPaneContainer) {
            root = ((RootPaneContainer) win).getRootPane();
          }

          if (root != null) {
            root.getGlassPane().addMouseListener(mouseAdapter);
            root.getGlassPane().setVisible(true);
            lockedPanes.addIfNotPresent(root);
          }
          d.setVisible(true);
        }
      };

      SwingUtilities.invokeLater(r);
    }
  }

  /**
   * Sets cursor to Wait cursor
   *
   * @param comp
   *          the component
   */
  public static void startWaitCursor(final iPlatformComponent comp, final iCancelable cancelable) {
    if (Platform.isUIThread()) {
      startWaitCursorEx(comp == null ? null : comp.getView(), cancelable,  Platform.getUIDefaults().getInt("Rare.WaitCursorHandler.delay", 200));

      return;
    }

    Runnable r = new Runnable() {
      @Override
      public void run() {
        startWaitCursorEx(comp == null ? null : comp.getView(), cancelable,  Platform.getUIDefaults().getInt("Rare.WaitCursorHandler.delay", 200));
      }
    };

    Platform.invokeLater(r);
  }

  /**
   * Sets cursor to Wait cursor
   *
   * @param comp
   *          the component
   */
  public static void startWaitCursor(final iPlatformComponent comp, final iCancelable cancelable, final int delay) {
    if (Platform.isUIThread()) {
      startWaitCursorEx(comp == null ? null : comp.getView(), cancelable, delay);

      return;
    }

    Runnable r = new Runnable() {
      @Override
      public void run() {
        startWaitCursorEx(comp == null ? null : comp.getView(), cancelable, delay);
      }
    };

    Platform.invokeLater(r);
  }

  /**
   * Sets cursor for specified component to normal cursor
   *
   * @param comp
   *          the component
   */
  public static void stopWaitCursor(boolean force) {
    WindowViewer w = Platform.getWindowViewer();

    if (w != null) {
      stopWaitCursor(w.getComponent(), force);
    }
  }

  /**
   * Sets cursor for specified component to normal cursor
   *
   * @param comp
   *          the component
   */
  public static void stopWaitCursor(iPlatformComponent comp) {
    stopWaitCursor(comp, false);
  }

  /**
   * Sets cursor for specified component to normal cursor
   *
   * @param comp
   *          the component
   * @param force
   *          force the cursor change
   */
  public static void stopWaitCursor(final iPlatformComponent comp, final boolean force) {
    if (Platform.isUIThread()) {
      stopWaitCursorEx(comp == null ? null : comp.getView(), force);

      return;
    }

    Runnable r = new Runnable() {
      @Override
      public void run() {
        stopWaitCursorEx(comp == null ? null : comp.getView(), force);
      }
    };

    Platform.invokeLater(r);
  }

  public static void updateProgressPopup(final iPlatformComponent comp, final CharSequence message) {
    if ((progressDialog == null) || (progressDialogLabel == null)) {
      return;
    }

    Runnable r = new Runnable() {
      @Override
      public void run() {
        if (progressDialogLabel != null) {
          progressDialogLabel.setText(message.toString());
        }
      }
    };

    SwingUtilities.invokeLater(r);
  }

  private static JRootPane getRootPane(Component comp) {
    JRootPane root = null;

    if (comp != null) {
      root = SwingUtilities.getRootPane(comp);

      if (root != null) {
        return root;
      }
    }

    iWidget w = Platform.getWindowViewer();

    if (w != null && !w.isDisposed()) {
      return SwingUtilities.getRootPane(w.getContainerComponent().getView());
    }

    return null;
  }

  private static void startWaitCursorEx(final Component comp, final iCancelable cancelable, int dealy) {
    if (comp != null) {
      synchronized (cursorCount) {
        int cnt = cursorCount.getAndIncrement();
        if (cnt == 0) {
          JRootPane root = getRootPane(comp);

          if (root != null) {
            root.getGlassPane().setCursor(WAIT_CURSOR);
            root.getGlassPane().addMouseListener(mouseAdapter);
            root.getGlassPane().setVisible(true);
            lockedPanes.addIfNotPresent(root);

            if (cancelable != null) {
              root.getGlassPane().addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                  if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    try {
                      cancelable.cancel(true);
                    } catch (Exception ignore) {
                    }

                    stopWaitCursor(comp == null ? null : Platform.findPlatformComponent(comp));
                  }
                }
              });
            }
          }
        }
      }
    }
  }

  private static void stopWaitCursorEx(Component comp, boolean force) {
    synchronized (cursorCount) {
      if (force) {
        cursorCount.set(0);
      }

      int cnt = cursorCount.decrementAndGet();

      if (cnt < 1) {
        cursorCount.set(0);
        if (progressDialog != null) {
          try {
            progressDialog.setVisible(false);
          } catch (Exception ignore) {
          }
          progressDialog = null;
          progressDialogLabel = null;
        } 
        if(!Platform.isShuttingDown()) {
  
          JRootPane root = getRootPane(comp);
  
          if (root != null) {
            root.getGlassPane().setCursor(DEFAULT_CURSOR);
            root.getGlassPane().removeMouseListener(mouseAdapter);
            root.getGlassPane().setVisible(false);
          }
  
          try {
            for (JRootPane p : lockedPanes) {
              Component g = p.getGlassPane();
  
              if (g != null) {
                g.setCursor(DEFAULT_CURSOR);
                g.removeMouseListener(mouseAdapter);
                g.setVisible(false);
              }
            }
          } catch (Exception e) {
            Platform.ignoreException(null, e);
          }
  
          lockedPanes.clear();
        }
      }
    }
  }
}
