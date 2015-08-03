/*
 * @(#)aDropListener.java   2009-09-01
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.dnd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.EventObject;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreePath;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.widget.iWidget;

/**
 * @autho Don DeCoteau
 */
public abstract class aDropListener implements DropTargetListener {
  protected int insertHotspot = 5;

  /**  */
  protected Point endPoint;

  /**  */
  protected DragGlassPane glassPane;

  /**  */
  protected boolean insertMode;

  /**  */
  protected Rectangle itemBounds;

  /**  */
  protected boolean onMode;

  /**  */
  protected Point startPoint;

  /**  */
  protected DefaultTransferHandler transferHandler;
  private boolean                  lastCanDrop;
  private Point                    lastPoint;
  private boolean                  uninstall;

  /**
   * Constructs a new instance
   *
   * @param th {@inheritDoc}
   * @param insert {@inheritDoc}
   * @param on {@inheritDoc}
   */
  public aDropListener(DefaultTransferHandler th, boolean insert, boolean on) {
    onMode          = on;
    insertMode      = insert;
    transferHandler = th;
  }

  @Override
  public void dragEnter(DropTargetDragEvent dtde) {
    Component comp     = dtde.getDropTargetContext().getComponent();
    Point     location = dtde.getLocation();
    int action=DefaultTransferHandler.fromSwingAction(dtde.getDropAction());

    glassPane = null;

    JRootPane root = SwingUtilities.getRootPane(comp);

    if (root != null) {
      Component c = root.getGlassPane();

      if (c instanceof DragGlassPane) {
        glassPane = (DragGlassPane) c;
      }
    }

    if (glassPane == null) {
      glassPane = DragSourceAdapter.installGlassPane(comp, null, endPoint);
      uninstall = true;
    } else {
      uninstall = false;
    }

    itemBounds = null;
    startPoint = null;
    endPoint   = null;
    lastPoint  = null;

    if (glassPane != null) {
      glassPane.setDropMode(onMode, insertMode);
    }

    DropInformation drop = transferHandler.getDropInformation(comp);

    drop.clear();
    drop.setDropAction(action);
    drop.setTransferable(new TransferableWrapper(dtde.getTransferable()));

    if (!canAcceptDrop(comp, location, drop) || scriptRejected(comp, dtde, drop,iConstants.EVENT_DRAGENTER)) {
      dtde.rejectDrag();
    } else {
      dtde.acceptDrag(action);
    }
  }

  @Override
  public void dragExit(DropTargetEvent dte) {
    lastPoint = null;
    resetGlassPane();
    Component comp     = dte.getDropTargetContext().getComponent();
    DropInformation drop = transferHandler.getDropInformation(comp);

    drop.clear();
    scriptRejected(dte.getDropTargetContext().getComponent(), dte, drop,iConstants.EVENT_DRAGEXIT);
  }

  @Override
  public void dragOver(DropTargetDragEvent dtde) {
    Component       comp    = dtde.getDropTargetContext().getComponent();
    DropInformation drop    = transferHandler.getDropInformation(comp);
    Point           p       = dtde.getLocation();
    boolean         canDrop = lastCanDrop;
    int action=DefaultTransferHandler.fromSwingAction(dtde.getDropAction());
    if ((lastPoint == null) ||!p.equals(lastPoint)) {
      lastPoint = p;
      drop.clear();
      drop.setTargetWidget(Platform.findWidgetForComponent(comp));
      drop.setSourceWidget(Platform.findWidgetForComponent(dtde.getSource()));
      drop.setDropAction(action);
      drop.setTransferable(new TransferableWrapper(dtde.getTransferable()));
      lastCanDrop = canDrop = canAcceptDrop(comp, p, drop);
    }

    if (!canDrop || scriptRejected(comp, dtde, drop,iConstants.EVENT_DRAGOVER)) {
      itemBounds = null;
      startPoint = null;
      endPoint   = null;
      dtde.rejectDrag();
    } else {
      dtde.acceptDrag(action);
    }
  }

  @Override
  public void drop(DropTargetDropEvent dtde) {
    resetGlassPane();

    if (!acceptDrop(dtde.getDropTargetContext().getComponent())) {
      dtde.rejectDrop();

      return;
    }
    int action=DefaultTransferHandler.fromSwingAction(dtde.getDropAction());

    Component c = dtde.getDropTargetContext().getComponent();

    if (c instanceof RootPaneContainer) {
      c = ((RootPaneContainer) c).getRootPane();
    }

    if (c instanceof JComponent) {
      DropInformation drop = transferHandler.getDropInformation(c);

      if (drop != null) {
        drop.setDropAction(action);
      }

      dtde.acceptDrop(action);
      dtde.dropComplete(transferHandler.importData((JComponent) c, dtde.getTransferable()));
    }
  }

  @Override
  public void dropActionChanged(DropTargetDragEvent dtde) {}

  public void setModes(boolean on, boolean insert) {
    onMode     = on;
    insertMode = insert;
  }

  boolean isBottomHotspot() {
    if ((itemBounds == null) || (startPoint == null)) {
      return false;
    }

    return startPoint.y > (itemBounds.y + itemBounds.height - insertHotspot);
  }

  /**
   * Returns whether the specified component can accept a drop
   *
   * @param comp the component
   * @return true to accept a drop; false otherwise
   */
  protected abstract boolean acceptDrop(Component comp);

  /**
   * Returns whether the specified component can accept a drop
   *
   * @param comp the component
   * @param pt the drop point
   *
   * @param drop the drop information
   * @return true to accept a drop; false otherwise
   */
  protected abstract boolean canAcceptDrop(Component comp, Point pt, DropInformation drop);

  protected boolean isValidDropPoint(JComponent comp, Point pt) {
    if (insertMode && (itemBounds != null)) {
      if (pt.y <= itemBounds.y + insertHotspot) {
        startPoint = itemBounds.getLocation();
        endPoint   = new Point(startPoint.x + itemBounds.width, startPoint.y);
      } else if (pt.y >= itemBounds.y + itemBounds.height - insertHotspot) {
        startPoint = new Point(itemBounds.x, itemBounds.y + itemBounds.height);
        endPoint   = new Point(startPoint.x + itemBounds.width, startPoint.y);
      } else {
        startPoint = endPoint = null;
      }
    } else {
      startPoint = endPoint = null;
    }

    if (!onMode && (startPoint == null) && (itemBounds != null)) {
      return false;
    }

    if (!insertMode && (startPoint != null)) {
      return false;
    }

    Component pane = (glassPane == null)
                     ? comp.getRootPane().getGlassPane()
                     : glassPane;

    if (itemBounds != null) {
      itemBounds = SwingUtilities.convertRectangle(comp, itemBounds, pane);
    }

    if ((startPoint != null) && (endPoint != null)) {
      startPoint = SwingUtilities.convertPoint(comp, startPoint, pane);
      endPoint   = SwingUtilities.convertPoint(comp, endPoint, pane);
    }

    return true;
  }

  private boolean scriptRejected(Component comp,EventObject e, DropInformation drop, String event) {
    iWidget w =Platform.getWidgetForComponent(Platform.getPlatformComponent(comp));

    if (w!=null && w.isEventEnabled(event)) {
      DataEvent d = new DataEvent(comp, drop);
      d.setTriggerEvent(e);

      try {
        w.executeEvent(event, d);

        return d.isConsumed();
      } catch(Exception ex) {
        w.getAppContext().getDefaultExceptionHandler().ignoreException(event, ex);
      }
    }

    return false;
  }

  private void resetGlassPane() {
    if (glassPane != null) {
      glassPane.setDropPoint(null, null, null);

      if (uninstall) {
        glassPane.uninstall();
      }
    }
  }

  /**
   *
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  public static class ListDropListener extends aDropListener {
    private int   rowIndex = -1;
    private Point tpt      = new Point();

    public ListDropListener(DefaultTransferHandler th, boolean insert, boolean on) {
      super(th, insert, on);
    }

    @Override
    protected boolean acceptDrop(Component comp) {
      return rowIndex > -1;
    }

    @Override
    protected boolean canAcceptDrop(Component comp, Point pt, DropInformation drop) {
      JList list = (JList) comp;

      tpt.setLocation(pt.x, pt.y + insertHotspot);
      rowIndex = list.locationToIndex(tpt);

      Rectangle rect = null;

      if ((rowIndex > -1) && (rowIndex == list.getModel().getSize() - 1)) {
        rect = list.getCellBounds(rowIndex, rowIndex);

        if (!rect.contains(tpt)) {
          rowIndex = -1;
        }
      }

      startPoint = endPoint = null;

      boolean accept = false;

      if (rowIndex > -1) {
        itemBounds = (rect == null)
                     ? list.getCellBounds(rowIndex, rowIndex)
                     : rect;

        if (isValidDropPoint(list, pt)) {
          drop.setDropPoint(new UIPoint(pt.x,pt.y));
          drop.setDropIndex(rowIndex);
          drop.setInsertAtEnd(isBottomHotspot() && (rowIndex == list.getModel().getSize() - 1));
          drop.setInsertMode(startPoint != null);

          if (!transferHandler.canImport(list, drop)) {
            startPoint = null;
            endPoint   = null;
            itemBounds = null;
          } else {
            accept = true;
          }
        } else {
          startPoint = null;
          endPoint   = null;
          itemBounds = null;
        }
      } else if (insertMode) {
        rowIndex   = 0;
        itemBounds = null;

        if (isValidDropPoint(list, pt)) {
          drop.setDropIndex(0);
          drop.setInsertMode(true);
          drop.setInsertAtEnd(true);

          if (!transferHandler.canImport(list, drop)) {
            startPoint = null;
            endPoint   = null;
            itemBounds = null;
          } else {
            accept = true;
          }
        }
      }

      if (glassPane != null) {
        glassPane.setDropPoint(startPoint, endPoint, itemBounds);
      }

      return accept;
    }
  }


  /**
   *
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  public static class TableDropListener extends aDropListener {
    private int   rowIndex = -1;
    private Point tpt      = new Point();

    public TableDropListener(DefaultTransferHandler th, boolean insert, boolean on) {
      super(th, insert, on);
    }

    @Override
    protected boolean acceptDrop(Component comp) {
      return rowIndex > -1;
    }

    @Override
    protected boolean canAcceptDrop(Component comp, Point pt, DropInformation drop) {
      JTable table = (JTable) comp;

      tpt.setLocation(pt.x, pt.y + insertHotspot);
      rowIndex   = table.rowAtPoint(tpt);
      startPoint = endPoint = null;

      boolean accept = false;

      if (rowIndex > -1) {
        itemBounds       = table.getCellRect(rowIndex, 0, true);
        itemBounds.width = table.getWidth();

        if (isValidDropPoint(table, pt)) {
          drop.setDropPoint(new UIPoint(pt.x,pt.y));
          drop.setDropIndex(rowIndex);
          drop.setInsertAtEnd(false);
          drop.setInsertMode(startPoint != null);

          if (table.getColumnSelectionAllowed()) {
            drop.setDropColumn(table.columnAtPoint(pt));
          } else {
            drop.setDropColumn(-1);
          }

          if (!transferHandler.canImport(table, drop)) {
            startPoint = null;
            endPoint   = null;
            itemBounds = null;
          } else {
            accept = true;
          }
        } else {
          startPoint = null;
          endPoint   = null;
          itemBounds = null;
        }
      } else if (insertMode) {
        rowIndex   = 0;
        itemBounds = null;

        if (isValidDropPoint(table, pt)) {
          drop.setDropIndex(0);
          drop.setInsertMode(true);
          drop.setInsertAtEnd(true);

          if (!transferHandler.canImport(table, drop)) {
            startPoint = null;
            endPoint   = null;
            itemBounds = null;
          } else {
            accept = true;
          }
        }
      }

      if (glassPane != null) {
        glassPane.setDropPoint(startPoint, endPoint, itemBounds);
      }

      return accept;
    }
  }


  public static class TextComponentDropLIstener extends WidgetDropListener {
    public TextComponentDropLIstener(DefaultTransferHandler th) {
      super(th);
    }

    @Override
    protected boolean canAcceptDrop(Component comp, Point pt, DropInformation drop) {
      if (!super.canAcceptDrop(comp, pt, drop)) {
        return false;
      }

      JTextComponent tc  = (JTextComponent) comp;
      int            pos = tc.viewToModel(pt);

      if (pos < 0) {
        return false;
      }

      try {
        Rectangle r = tc.modelToView(pos);

        r.x        -= 1;
        r.width    = 2;
        itemBounds = r;
        glassPane.setDropPoint(null, null, r);
        glassPane.setItemBoundsColor(Color.black);

        return true;
      } catch(BadLocationException ex) {
        return false;
      }
    }
  }


  /**
   *
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  public static class TreeDropListener extends aDropListener {
    private long     canAcceptDropEnterTime = -1;
    private TreePath lastTreePath           = null;
    private TreePath treePath               = null;
    private int      rowIndex;

    public TreeDropListener(DefaultTransferHandler th, boolean insert, boolean on) {
      super(th, insert, on);
    }

    @Override
    protected boolean acceptDrop(Component comp) {
      return rowIndex > -1;
    }

    @Override
    protected boolean canAcceptDrop(Component comp, Point pt, DropInformation drop) {
      JTree tree = (JTree) comp;

      treePath   = tree.getPathForLocation(pt.x, pt.y + insertHotspot);
      startPoint = endPoint = null;

      boolean accept = false;

      if (treePath != null) {
        itemBounds = tree.getPathBounds(treePath);

        if (isValidDropPoint(tree, pt)) {
          drop.setDropPoint(new UIPoint(pt.x,pt.y));
          rowIndex = tree.getRowForPath(treePath);
          drop.setDropIndex(rowIndex);
          drop.setInsertMode(startPoint != null);
          drop.setInsertAtEnd(false);

          if (!transferHandler.canImport(tree, drop)) {
            startPoint = null;
            endPoint   = null;
            itemBounds = null;
          } else {
            accept = true;
          }
        } else {
          startPoint = null;
          endPoint   = null;
          itemBounds = null;
        }

        if (treePath == lastTreePath) {
          if (System.currentTimeMillis() > (canAcceptDropEnterTime + 2000)) {
            tree.expandPath(treePath);
          }
        } else {
          lastTreePath           = treePath;
          canAcceptDropEnterTime = System.currentTimeMillis();
        }
      } else if (insertMode) {
        rowIndex   = 0;
        itemBounds = null;

        if (isValidDropPoint(tree, pt)) {
          drop.setDropIndex(0);
          drop.setInsertMode(true);
          drop.setInsertAtEnd(true);

          if (!transferHandler.canImport(tree, drop)) {
            startPoint = null;
            endPoint   = null;
            itemBounds = null;
          } else {
            accept = true;
          }
        }
      }

      if (glassPane != null) {
        glassPane.setDropPoint(startPoint, endPoint, itemBounds);
      }

      return accept;
    }
  }


  public static class WidgetDropListener extends aDropListener {
    public WidgetDropListener(DefaultTransferHandler th) {
      super(th, false, true);
    }

    @Override
    protected boolean acceptDrop(Component comp) {
      return true;
    }

    @Override
    protected boolean canAcceptDrop(Component comp, Point pt, DropInformation drop) {
      boolean dropOk = false;

      if (comp instanceof JComponent) {
        drop.setDropPoint(new UIPoint(pt.x,pt.y));
        dropOk = transferHandler.canImport((JComponent) comp, drop);
      }

      return dropOk;
    }
  }


  /**
   * A listener for drop events on a window
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  public static class WindowDropListener extends aDropListener {

    /**
     * Constructs a new instance
     *
     * @param th the transfer handler
     */
    public WindowDropListener(DefaultTransferHandler th) {
      super(th, false, true);
    }

    @Override
    protected boolean acceptDrop(Component comp) {
      return true;
    }

    @Override
    protected boolean canAcceptDrop(Component comp, Point pt, DropInformation drop) {
      boolean dropOk = false;

      if (comp instanceof RootPaneContainer) {
        drop.setDropPoint(new UIPoint(pt.x,pt.y));
        dropOk = transferHandler.canImport(((RootPaneContainer) comp).getRootPane(), drop);
      } else if (comp instanceof JComponent) {
        dropOk = transferHandler.canImport((JComponent) comp, drop);
      }

      return dropOk;
    }
  }
}
