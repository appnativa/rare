/*
 * @(#)DragSourceAdapter.java   2009-09-01
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.dnd;

import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.util.EventObject;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public class DragSourceAdapter extends DragSource
        implements DragGestureListener, DragSourceListener, DragSourceMotionListener {
  static boolean                         customDragger;
  private static final DragSourceAdapter dragSourceAdapter = new DragSourceAdapter();
  private static TransferHandler         textTransferHandler;
  DragGlassPane                          dragGlassPane = new DragGlassPane();
  boolean                                scrolls;

  public DragSourceAdapter() {
    this.addDragSourceMotionListener(this);
    getDefaultDragSource().addDragSourceListener(this);
  }

  public static void configure(JComponent comp) {
    dragSourceAdapter.configureEx(comp);
  }

  public static void configure(JComponent comp, boolean nonWidget) {
    dragSourceAdapter.configureEx(comp);

    if (nonWidget) {
      if ((comp instanceof JLabel) || (comp instanceof AbstractButton)) {
        if (textTransferHandler == null) {
          textTransferHandler = new TransferHandler("text") {
            @Override
            protected Transferable createTransferable(JComponent c) {
              String s = "";

              if (c instanceof AbstractButton) {
                s = ((AbstractButton) c).getText();
              } else if (c instanceof JLabel) {
                s = ((JLabel) c).getText();
              }

              return new StringSelection(s);
            }
          };
        }

        comp.setTransferHandler(textTransferHandler);
      }
    }
  }

  @Override
  public void dragDropEnd(DragSourceDropEvent e) {
    if (customDragger) {
      customDragger = false;
      dragGlassPane.uninstall();

      DragSourceContext dsc = e.getDragSourceContext();
      JComponent        c   = (JComponent) dsc.getComponent();

      if (e.getDropSuccess()) {
        callExportDone(c, c.getTransferHandler(), dsc.getTransferable(), e.getDropAction());
      } else {
        callExportDone(c, c.getTransferHandler(), dsc.getTransferable(), DnDConstants.ACTION_NONE);
      }

      c.setAutoscrolls(scrolls);
    }

    fireEvent(e.getDragSourceContext().getComponent(), e, iConstants.EVENT_DRAGEND);
  }

  @Override
  public void dragEnter(DragSourceDragEvent e) {}

  @Override
  public void dragExit(DragSourceEvent e) {}

  @Override
  public void dragGestureRecognized(DragGestureEvent e) {
    JComponent      c  = (JComponent) e.getComponent();
    iWidget         w  = Platform.getWidgetForComponent(e.getComponent());
    TransferHandler th = c.getTransferHandler();
    Transferable    t  = null;

    if ((w == null) && (th != null)) {
      th.exportAsDrag(c, e.getTriggerEvent(), TransferHandler.COPY);

      return;
    }

    if ((w != null) && w.canDrag(new DragEvent(c, e))) {
      if (th instanceof DefaultTransferHandler) {
        t = ((DefaultTransferHandler) th).createTransferable(c);
      }
    }

    if ((w != null) && (t != null)) {
      t       = new WidgetTransferableEx(w);
      scrolls = c.getAutoscrolls();
      c.setAutoscrolls(false);

      try {
        dragGlassPane.install(e.getComponent(), w.getDragIcon(), e.getDragOrigin());
        customDragger = true;
        e.startDrag(null, dragGlassPane.getDragImage(), new Point(5, 5), t, this);

        return;
      } catch(RuntimeException ignore) {
        customDragger = false;
        c.setAutoscrolls(scrolls);
      }
    }

    dragGlassPane.uninstall();
    callExportDone(c, th, t, DnDConstants.ACTION_NONE);
  }

  @Override
  public void dragMouseMoved(DragSourceDragEvent e) {
    dragGlassPane.setDragPoint(e.getLocation());
    fireEvent(e.getDragSourceContext().getComponent(), e, iConstants.EVENT_DRAG);
  }

  @Override
  public void dragOver(DragSourceDragEvent e) {}

  @Override
  public void dropActionChanged(DragSourceDragEvent e) {}

  public static DragGlassPane installGlassPane(Component c, Icon icon, Point p) {
    dragSourceAdapter.dragGlassPane.install(c, icon, p);

    return dragSourceAdapter.dragGlassPane;
  }

  public static void uninstallGlassPane() {
    dragSourceAdapter.dragGlassPane.uninstall();
  }

  public static boolean isDragging() {
    return customDragger;
  }

  protected void callExportDone(JComponent c, TransferHandler th, Transferable t, int action) {
    customDragger = false;

    try {
      if (th instanceof DefaultTransferHandler) {
        ((DefaultTransferHandler) th).exportDone(c, t, action);
      }
    } finally {
      if (t instanceof WidgetTransferableEx) {
        ((WidgetTransferableEx) t).clear();
      }
    }
  }

  private void configureEx(JComponent comp) {
    if (comp instanceof JTable) {
      ((JTable) comp).setDragEnabled(true);
    } else if (comp instanceof JTree) {
      ((JTree) comp).setDragEnabled(true);
    } else if (comp instanceof JList) {
      ((JList) comp).setDragEnabled(true);
    } else if (comp instanceof JTextComponent) {
      ((JTextComponent) comp).setDragEnabled(true);
    } else {
      createDefaultDragGestureRecognizer(comp, DnDConstants.ACTION_COPY_OR_MOVE, this);
    }
  }

  private void fireEvent(Component comp, EventObject e, String event) {
    iWidget w = Platform.getWidgetForComponent(comp);

    if ((w != null) && w.isEventEnabled(event)) {
      try {
        w.executeEvent(event, e);
      } catch(Exception ex) {
        w.getAppContext().getDefaultExceptionHandler().ignoreException(event, ex);
      }
    }
  }
}
