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

package com.appnativa.rare.ui.dnd;

import com.appnativa.rare.Platform;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.widget.iWidget;

import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.RootPaneContainer;
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Don DeCoteau
 */
public class DefaultTransferHandler extends TransferHandler {
  private static DefaultTransferHandler defaultTransferHandler;
  iWidget                               sourceWidget;

  /**  */
  DropInformation dropInformation = new DropInformation(null);

  /**
   * Constructs a new instance
   *
   */
  public DefaultTransferHandler() {}

  @Override
  public boolean canImport(TransferHandler.TransferSupport support) {
    Component c = support.getComponent();

    if (c instanceof RootPaneContainer) {    //only used for windows
      c = ((RootPaneContainer) c).getRootPane();
    }

    iWidget w = Platform.getWidgetForComponent(c);

    if (w != null) {
      DropInformation drop = getDropInformation(c);

      drop.clear();
      drop.setTargetWidget(w);
      drop.setSourceWidget(sourceWidget);

      if (support.isDrop()) {
        Point p = support.getDropLocation().getDropPoint();

        drop.setDropPoint(new UIPoint(p.x, p.y));
        drop.setDropAction(fromSwingAction(support.getDropAction()));
      } else {
        drop.setDropAction(DnDConstants.ACTION_COPY);
      }

      return w.canImport(TransferableWrapper.getTransferFlavors(support.getDataFlavors()), drop);
    }

    return super.canImport(support);
  }

  @Override
  public boolean canImport(JComponent c, DataFlavor[] flavors) {
    iWidget w = Platform.getWidgetForComponent(c);

    if (w == null) {
      return false;
    }

    DropInformation drop = getDropInformation(c);

    drop.clear();

    return w.canImport(TransferableWrapper.getTransferFlavors(flavors), drop);
  }

  public boolean canImport(JComponent c, DropInformation dropInfo) {
    if (dropInfo == null) {
      return false;
    }

    iWidget w = Platform.getWidgetForComponent(c);

    if (w == null) {
      return super.canImport(
          c, TransferableWrapper.getTransferDataFlavors(dropInfo.getTransferable().getTransferFlavors()));
    }

    return w.canImport(dropInfo.getTransferable().getTransferFlavors(), dropInfo);
  }

  public Transferable createTransferableEx(JComponent c) {
    return createTransferable(c);
  }

  @Override
  public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException {
    action = fromSwingAction(action);

    if (((action == COPY) || (action == MOVE)) && (getSourceActions(comp) & action) != 0) {
      Transferable t = createClipboardTransferable(comp);

      if (t != null) {
        try {
          clip.setContents(t, null);
          exportDone(comp, t, action);

          return;
        } catch(IllegalStateException ise) {
          exportDone(comp, t, NONE);

          throw ise;
        }
      }
    }

    exportDone(comp, null, NONE);
  }

  public static int fromSwingAction(int action) {
    switch(action) {
      case java.awt.dnd.DnDConstants.ACTION_COPY :
        return DnDConstants.ACTION_COPY;

      case java.awt.dnd.DnDConstants.ACTION_MOVE :
        return DnDConstants.ACTION_MOVE;

      case java.awt.dnd.DnDConstants.ACTION_COPY_OR_MOVE :
        return DnDConstants.ACTION_MOVE;

      case java.awt.dnd.DnDConstants.ACTION_LINK :
        return DnDConstants.ACTION_LINK;

      default :
        return DnDConstants.ACTION_NONE;
    }
  }

  @Override
  public boolean importData(TransferSupport support) {
    Component c = support.getComponent();

    if (c instanceof RootPaneContainer) {    //only used for windows
      c = ((RootPaneContainer) c).getRootPane();
    }

    iWidget w = Platform.getWidgetForComponent(c);

    if (w != null) {
      DropInformation drop = getDropInformation(c);

      drop.clear();

      if (support.isDrop()) {
        Point p = support.getDropLocation().getDropPoint();

        drop.setDropPoint(new UIPoint(p.x, p.y));
        drop.setDropAction(fromSwingAction(support.getDropAction()));
      } else {
        drop.setDropAction(DnDConstants.ACTION_COPY);
      }

      return w.importData(new TransferableWrapper(support.getTransferable()), drop);
    }

    return super.importData(support);
  }

  @Override
  public boolean importData(JComponent c, Transferable t) {
    iWidget         w    = Platform.getWidgetForComponent(c);
    DropInformation drop = getDropInformation(c);

    if ((w == null) || (drop == null)) {
      return super.importData(c, t);
    }

    boolean imported = false;

    try {
      iTransferable it;

      if (t instanceof iTransferable) {
        it = (iTransferable) t;
      } else {
        it = new TransferableWrapper(t);
      }

      imported = w.importData(it, drop);
    } finally {
      if (!imported) {
        drop.clear();
      }
    }

    return imported;
  }

  /**
   * Sets the transfer handler mode for a component based on its widget configuration
   *
   * @param widget the widget the component belongs to
   * @param comp the component
   * @param dm drop mode
   */
  public static void setTransferHandler(iWidget widget, JComponent comp, int dm) {
    if (defaultTransferHandler == null) {
      defaultTransferHandler = new DefaultTransferHandler();
    }

    DefaultTransferHandler th     = defaultTransferHandler;
    boolean                insert = true;
    boolean                on     = true;

    switch(dm) {
      case Widget.CDropTracking.auto :
        if (comp instanceof JTextComponent) {
          if (widget.getAppContext().areAllTextFieldsDroppable()) {
            insert = false;
          }
        }

        break;

      case Widget.CDropTracking.insert_item :
        on = false;

        break;

      case Widget.CDropTracking.on_or_insert :
        break;

      case Widget.CDropTracking.on_item :
        insert = false;

        break;

      default :
        break;
    }

    if (comp instanceof JTree) {
      new DropTarget(comp, new aDropListener.TreeDropListener(th, insert, on));
    } else if (comp instanceof JList) {
      new DropTarget(comp, new aDropListener.ListDropListener(th, insert, on));
    } else if (comp instanceof JTable) {
      new DropTarget(comp, new aDropListener.TableDropListener(th, insert, on));
    } else if (comp instanceof JTextComponent) {
      ((JTextComponent) comp).setDragEnabled(true);
      // new DropTarget(comp, new aDropListener.TextComponentDropLIstener(th));
    } else {
      new DropTarget(comp, new aDropListener.WidgetDropListener(th));
    }

    comp.setTransferHandler(th);
  }

  public DropInformation getDropInformation(Component c) {
    // only one drop operation at a time so we can reuse the object;
    return dropInformation;
  }

  public static DefaultTransferHandler getInstance() {
    if (defaultTransferHandler == null) {
      defaultTransferHandler = new DefaultTransferHandler();
    }

    return defaultTransferHandler;
  }

  @Override
  public int getSourceActions(JComponent c) {
    iWidget w = Platform.getWidgetForComponent(c);

    sourceWidget = w;

    if (w == null) {
      return TransferHandler.NONE;
    }

    if (w.canCopy()) {
      return w.canMove()
             ? TransferHandler.COPY_OR_MOVE
             : TransferHandler.COPY;
    }

    if (w.canMove()) {
      return TransferHandler.MOVE;
    }

    return TransferHandler.NONE;
  }

  @Override
  public Icon getVisualRepresentation(Transferable t) {
    Icon icon = null;

    if (t instanceof WidgetTransferableEx) {
      icon = ((WidgetTransferableEx) t).getDataIcon();
    }

    return (icon == null)
           ? super.getVisualRepresentation(t)
           : icon;
  }

  protected Transferable createClipboardTransferable(JComponent c) {
    iWidget w = Platform.getWidgetForComponent(c);

    return (w == null)
           ? null
           : new ClipboardTransferable(w);
  }

  @Override
  protected Transferable createTransferable(JComponent c) {
    iWidget w = Platform.getWidgetForComponent(c);

    return (w == null)
           ? null
           : new WidgetTransferableEx(w);
  }

  @Override
  protected void exportDone(JComponent c, Transferable t, int action) {
    action = fromSwingAction(action);

    iWidget         w    = Platform.getWidgetForComponent(c);
    DropInformation drop = getDropInformation(c);

    if ((w == null) || (drop == null)) {
      super.exportDone(c, t, action);
    } else {
      try {
        drop.setDropAction(action);

        iTransferable it;

        if (t instanceof iTransferable) {
          it = (iTransferable) t;
        } else {
          it = new TransferableWrapper(t);
        }

        w.dataExported(it, drop);
      } finally {
        drop.clear();
      }
    }
  }
}
