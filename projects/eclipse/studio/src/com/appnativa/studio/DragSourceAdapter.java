/*
 * @(#)DragSourceAdapter.java   2008-04-21
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.awt.Point;
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

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 *
 * @author decoteaud
 */
public class DragSourceAdapter extends DragSource
        implements DragGestureListener, DragSourceListener, DragSourceMotionListener {
  private static final DragSourceAdapter dragSourceAdapter = new DragSourceAdapter();
  DesignPane                             dragGlassPane;

  public DragSourceAdapter() {
    addDragSourceMotionListener(this);
    getDefaultDragSource().addDragSourceListener(this);
  }

  public static void configure(JComponent comp) {
    dragSourceAdapter.configureEx(comp);
  }

  public void dragDropEnd(DragSourceDropEvent e) {
    DragSourceContext dsc = e.getDragSourceContext();
    JComponent        c   = (JComponent) dsc.getComponent();

    if (!(c instanceof DesignPane)) {
      return;
    }

    if (e.getDropSuccess()) {
      callExportDone(c, c.getTransferHandler(), dsc.getTransferable(), e.getDropAction());
    } else {
      callExportDone(c, c.getTransferHandler(), dsc.getTransferable(), DnDConstants.ACTION_NONE);
    }
  }

  public void dragEnter(DragSourceDragEvent e) {}

  public void dragExit(DragSourceEvent e) {}

  public void dragGestureRecognized(DragGestureEvent e) {
    JComponent c = (JComponent) e.getComponent();

    if (!(c instanceof DesignPane)) {
      return;
    }

    dragGlassPane = (DesignPane) c;

    if (dragGlassPane.canStartDrag(e.getDragOrigin())) {
      Transferable t = dragGlassPane.createTransferable();

      try {
         e.startDrag(null, dragGlassPane.getDragImage(), new Point(5, 5), t, this);
        //e.startDrag(null, t, this);

        return;
      } catch(RuntimeException re) {}
    } else {
      dragGlassPane = null;
    }
  }

  public void dragMouseMoved(DragSourceDragEvent dsde) {}

  public void dragOver(DragSourceDragEvent e) {}

  public void dropActionChanged(DragSourceDragEvent e) {}

  protected void callExportDone(JComponent c, TransferHandler th, Transferable t, int action) {}

  private void configureEx(JComponent comp) {
    createDefaultDragGestureRecognizer(comp, DnDConstants.ACTION_COPY_OR_MOVE, this);
  }
}
