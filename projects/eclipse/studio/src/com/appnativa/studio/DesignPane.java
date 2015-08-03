/*
 * @(#)DesignPane.java   2009-12-26
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoableEdit;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iAppContext;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.ui.text.UndoManagerEx;
import com.appnativa.rare.platform.swing.ui.text.UndoManagerEx.iModNotifier;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.FrameView;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.platform.swing.ui.view.ScrollPaneEx;
import com.appnativa.rare.platform.swing.ui.view.UtilityPanel;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.MenuUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIMenu;
import com.appnativa.rare.ui.UISoundHelper;
import com.appnativa.rare.ui.iLayoutManager;
import com.appnativa.rare.ui.iLayoutManager.iLayoutTracker;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iTabDocument;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.dnd.TransferFlavor;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.viewer.aRegionViewer;
import com.appnativa.rare.viewer.aTabPaneViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.rare.widget.iWidget.WidgetType;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.Helper;

/**
 *
 * @author decoteaud
 */
public class DesignPane extends UtilityPanel implements HierarchyListener, iModNotifier, iLayoutTracker {
  private static int               DRAG_SLOP      = 10;
  private static long              sequenceNumber = 0;
  public boolean                   dropTrackingCanceled;
  Rectangle                        bounds         = new Rectangle();
  Rectangle                        unionRect      = new Rectangle();
  SelectionArea                    trackingArea   = new SelectionArea();
  SelectionArea                    activeArea     = new SelectionArea();
  iAppContext                      appContext;
  UILineBorder                     cellBorder;
  ChangeEvent                      changeEvent;
  com.appnativa.rare.ui.Container  component;
  Image                            dragImage;
  UIColor                          gridColor;
  Rectangle                        imageRect;
  UIColor                          lfSelectionColor;
  Stroke                           lineStroke;
  boolean                          okToDrop;
  ScrollPaneEx                     scrollPane;
  UILineBorder                     selectionBorder;
  UIColor                          selectionColor;
  AtomicInteger                    syncCounter;
  UIColor                          trackingColor;
  private ArrayList<SelectionArea> selections     = new ArrayList<SelectionArea>();
  private boolean                  modified       = false;
  private boolean                  focusOwner     = true;
  private boolean                  fireEvents     = true;
  private boolean                  absoluteLayout = false;
  private boolean                  showGrid       = true;
  private URL                      baseURL;
  private JComponent               borderComponent;
  private UIColor                  canvasColor;
  private int                      dropAction;
  private boolean                  dropTracking;
  private JComponent               gridComponent;
  private boolean                  hasHadSelection;
  private boolean                  motionTracking;
  private Point                    mouseDown;
  private Point                    mouseScreenDown;
  private final long               paneID;
  private iContainer               rootWidget;
  // private List<Widget> selectedConfigs;
  private iWidget                  selectedWidget;
  private boolean                  selectionEventsDisabled;
  private UtilityPanel             topPanel;
  private UndoManagerEx            undoManager;
  private DesignValueEditor        valueEditor;

  public DesignPane(UtilityPanel top, AtomicInteger ai) {
    super();

    if (JPanelEx.SYSTEM_PAINTER == null) {
      JPanelEx.SYSTEM_PAINTER = new DesignModePainter();
      ColorUtils.DISABLED_TRANSPARENT_COLOR = new UIColor(0, 0, 0, 64);
    }

    component = new DesignPaneContainer(this);
    paneID = sequenceNumber++;
    topPanel = top;
    syncCounter = ai;
    appContext = Platform.getAppContext();
    setOpaque(false);
    this.undoManager = new UndoManagerEx();
    this.undoManager.setModNotifier(this);
    gridColor = UIColor.GREEN;
    trackingColor = UIColor.DARKGRAY;
    lineStroke = SwingHelper.DASHED_STROKE;

    UILineBorder b = new UILineBorder(trackingColor, 2, 4);

    cellBorder = b;
    b = new UILineBorder(UIColor.BLUE, 2, 4);
    selectionBorder = b;
    trackingArea.tracking = true;
    this.addHierarchyListener(this);
    // DragSourceAdapter.configure(this);
    // new DropTarget(this, new PaneDropTargetListener());
    this.setAutoscrolls(true);

    PaneMouseAdapter ma = new PaneMouseAdapter();

    this.addMouseMotionListener(ma);
    this.addMouseListener(ma);
    changeEvent = new ChangeEvent(this);
    ActionHelper.registerUndoManager(component, undoManager);
    ActionHelper.setDeleteEnabled(component, true);
    ActionHelper.setCopyEnabled(component, true);
    ActionHelper.setCutEnabled(component, true);
    ActionHelper.setPasteEnabled(component, true);
    ActionHelper.setSelectAllEnabled(component, true);
    ActionHelper.registerCutCopyPasteBindings(component, true);
    setTransferHandler(new TransferHandler() {
      protected Transferable createTransferable(JComponent c) {
        return new DesignTransferable((WidgetList) getSelection(), paneID);
      }

      public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
        return DesignTransferable.hasSupportedFlavor(transferFlavors);
      }

      public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException {
        DesignPane.this.exportToClipboard(clip, action);
      }

      public boolean importData(JComponent comp, Transferable t) {
        return DesignPane.this.importData(t);
      }
    });
    topPanel.setLayoutTracker(this);
    this.setFocusable(true);
    this.setRequestFocusEnabled(true);
    addKeyListener(new KeyboardHandler());
    // KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new
    // KeyEventDispatcher() {
    // public boolean dispatchKeyEvent(KeyEvent e) {
    // System.out.println("Got key event!");
    //
    // return false;
    // }
    // });
  }

  public void addDesignListener(iDesignListener l) {
    listenerList.add(iDesignListener.class, l);
  }

  public void addSelectedWidget(iWidget w) {
    if (w == selectedWidget) {
      return;
    }

    setSelectedWidgetEx(w, true);
  }

  DesignCompoundEdit editCapturer;

  public void startEditsCapture() {
    if (editCapturer == null) {
      editCapturer = new DesignCompoundEdit(this);
    }
  }

  public void endEditsCapture(boolean discard, boolean update, Widget wc) {
    DesignCompoundEdit e = editCapturer;
    editCapturer = null;
    if (!discard && e.hasEdits()) {
      addUndoableEdit(e, update, wc);
    }
  }

  public boolean hasCapturedEdits() {
    return editCapturer != null && editCapturer.hasEdits();
  }

  public void addUndoableEdit(UndoableEdit e, boolean update, Widget wc) {
    if (e instanceof DesignCompoundEdit) {
      ((DesignCompoundEdit) e).end(); // design edits should be completed prior
                                      // to adding to the undo manager
    }
    if (editCapturer != null) {
      editCapturer.addEdit(e);
    } else {
      if (undoManager != null) {
        undoManager.undoableEditHappened(new UndoableEditEvent(this, e));
      }

      if (wc != null) {
        requestReload(wc);
      } else if (update) {
        update();
      }

      fireEvent(new UndoableEditEvent(this, e));
    }
  }

  public void addUndoableEditListener(UndoableEditListener l) {
    listenerList.add(UndoableEditListener.class, l);
  }

  public boolean canCopy() {
    if (hasSelection()) {
      return true;
    }

    if ((getCurrentColumn() == 0) && (getCurrentRow() == 0)) {
      return true;
    }

    return false;
  }

  public boolean canDelete() {
    return (selectedWidget != rootWidget) && (selectedWidget != null);
  }

  public boolean canImport(DataFlavor[] flavors) {
    if (!canPaste()) {
      return false;
    }

    return this.getTransferHandler().canImport(this, flavors);
  }

  public boolean canImport(List<TransferFlavor> flavors) {
    if (!canPaste()) {
      return false;
    }

    return DesignTransferable.hasSupportedFlavor(flavors);
  }

  public boolean canMove() {
    return hasSelection();
  }

  public boolean canPaste() {
    return activeArea.valid && (rootWidget instanceof iContainer);
  }

  public boolean canSelectAll() {
    iWidget w = activeArea.gridWidget;

    if (w == null) {
      w = rootWidget;
    }

    return (w != null) && (w.getWidgetCount() > 0);
  }

  public void clean() {
    undoManager.resetModCount();
    modified = false;
  }

  public void clearSelections() {
    iWidget w = activeArea.gridWidget;

    if (w == null) {
      w = rootWidget;
    }

    boolean designc = w instanceof DesignContainer;

    if (designc) {
      w = ((DesignContainer) w).getWidget();
    }

    selectedWidget = w;
    activeArea.reset();
    selections.clear();
    FormsUtils.getSelectionAreaFromWidget(this, w, activeArea, null);
    activeArea.cellSelected = false;

    if (designc) {
      activeArea.cellSelected = true;
      selections.add(activeArea);
    } else {
      activeArea.cellSelected = false;
    }

    setupGridComponent(w);
  }

  public void copy() {
    getTransferHandler().exportToClipboard(this, ActionHelper.getClipboard(), TransferHandler.COPY);
  }

  public void cut() {
    getTransferHandler().exportToClipboard(this, ActionHelper.getClipboard(), TransferHandler.MOVE);
  }

  public Object deleteSelectedData(boolean returnData) {
    DesignCompoundEdit edits = new DesignCompoundEdit(this);
    UndoableEdit e;
    iWidget w;
    Object o = this.getSelectedWidgetConfigsEx(false);

    for (SelectionArea a : selections) {
      w = a.selectionWidget;

      if ((w != null) && (w != rootWidget)) {
        e = FormChanger.removeWidget(this, (SPOTSequence) w.getParent().getLinkedData(), w, (Widget) w.getLinkedData(), true);

        if (e != null) {
          edits.addEdit(e);
          a.selectionWidget = null;
        }
      }
    }

    if (edits.hasEdits()) {
      activeArea.selectionWidget = null;
      selections.clear();
      selections.add(activeArea);
      selectedWidget = activeArea.gridWidget;
      this.addUndoableEdit(edits.peal(), false, null);
      requestReload(null);
    }

    return o;
  }

  public static Component findPane(Container container) {
    int len = container.getComponentCount();

    for (int i = 0; i < len; i++) {
      Component c = container.getComponent(i);

      if (c instanceof DesignPane) {
        return c;
      }

      if (c instanceof Container) {
        c = findPane((Container) c);

        if (c != null) {
          return c;
        }
      }
    }

    return null;
  }

  public void focusGained() {
    focusOwner = true;
    changeFocusStateColor();
  }

  public void focusLost() {
    focusOwner = false;
    changeFocusStateColor();
  }

  public void handleDrop(Point p, Widget cfg, iWidget selected) {
    if (p == null) {
      FormsUtils.handleDrop(this, cfg);
    } else {
      FormsUtils.handleDrop(this, p, cfg);
    }
  }

  public void handleSingleMouseClick(Point p) {
    if (motionTracking) {
      DesignEvent designEvent = new DesignEvent(this, paneOwner);
      designEvent.setWidget(FormsUtils.getWidgetAtPoint(DesignPane.this, p));
      designEvent.setEvent(EventType.MOTION_TRACKING_CLICK);
      designEvent.setData(p);
      fireEvent(designEvent);
    }
  }

  public void hierarchyChanged(HierarchyEvent e) {
    if ((e.getChangeFlags() & HierarchyEvent.PARENT_CHANGED) != 0) {
      Component c = getParent();

      while ((c != null) && !(c instanceof ScrollPaneEx)) {
        c = c.getParent();
      }

      if (c instanceof ScrollPaneEx) {
        scrollPane = (ScrollPaneEx) c;
        this.removeHierarchyListener(this);
        scrollPane.setComponentPainter(new SelectionPainter());
        scrollPane.setBorder(new UIEmptyBorder(3));
        handleCanvasColor(scrollPane, canvasColor);

        if (rootWidget != null) {
          setSelectedWidget(rootWidget);
        }
      }
    }
  }

  public void inplaceEdit() {
    if (valueEditor == null) {
      valueEditor = new DesignValueEditor(DesignPane.this);
    }

    valueEditor.edit(selectedWidget);
  }

  @Override
  public void layoutPerformed(iLayoutManager lm) {
    if (!activeArea.isValid()) {
      updateEx(false);
    }
  }

  public void modified(UndoManagerEx um, int count, UndoableEdit edit) {
    syncCounter.addAndGet(1);

    boolean mod = count != 0;

    if (mod != modified) {
      modified = mod;
      DesignEvent designEvent = new DesignEvent(this, paneOwner);
      designEvent.setWidget(rootWidget);
      designEvent.setEvent(EventType.DOCUMENT_MODIFIED);
      fireEvent(designEvent);
    }
  }

  public void paste() {
    Clipboard clipboard = ActionHelper.getClipboard();

    getTransferHandler().importData(this, clipboard.getContents(null));
  }

  public void removeDesignListener(iDesignListener l) {
    listenerList.add(iDesignListener.class, l);
  }

  public void requestReload(final Widget wc) {
    if (!SwingUtilities.isEventDispatchThread()) {
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          requestReload(wc);
        }
      });

      return;
    }

    DesignEvent designEvent = new DesignEvent(this, paneOwner);
    designEvent.setWidget(selectedWidget);
    designEvent.setEvent(EventType.RELOAD_REQUEST);
    designEvent.setData(wc);

    // if (wc == null) {
    // selectedConfigs = this.getSelectedWidgetConfigsEx(true);
    // } else {
    // selectedConfigs = Collections.singletonList(wc);
    // }

    fireEvent(designEvent);
  }

  public void selectAll() {
    iContainer p = activeArea.gridWidget;

    if (p == null) {
      p = rootWidget.getContainerViewer();
    }

    List<iWidget> widgets = p.getWidgetList();

    if ((widgets == null) || (widgets.size() == 0)) {
      return;
    }

    selections.clear();
    activeArea.reset();

    iWidget anchor = selectedWidget;

    if ((anchor == null) || (anchor == rootWidget) || (anchor == p)) {
      anchor = widgets.get(0);
    }

    setSelectedWidgets(widgets, anchor);
  }

  public void selectByElementEquals(iSPOTElement e) {
    iWidget w = findByElementEquals(rootWidget, e);

    if (w != null) {
      setSelectedWidget(w);
    }
  }

  public void setupBorders(JComponent comp) {
    borderComponent = comp;

    PaneBorder pb = new PaneBorder();

    comp.setBorder(new CompoundBorder(comp.getBorder(), pb));
  }

  public Rectangle shatter() {
    Container c = getParent();

    if (c != null) {
      c.remove(this);
    }
    if (component != null) {
      component.dispose();
    }
    if (undoManager != null) {
      undoManager.setModNotifier(null);
    }
    selectedWidget = null;
    rootWidget = null;
    scrollPane = null;
    component = null;
    undoManager = null;
    ActionHelper.clearFocusedComponentReferences();
    return new Rectangle(activeArea.selectionPoint.x, activeArea.selectionPoint.y, activeArea.rowColumn.x, activeArea.rowColumn.y);
  }

  public void update() {
    if (SwingUtilities.isEventDispatchThread()) {
      updateEx(true);
    } else {
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          updateEx(true);
        }
      });
    }
  }

  public boolean updateDropTracking(Point p, boolean dndlocal) {
    if (rootWidget == null) {
      return false;
    }
    if (!(rootWidget instanceof iContainer)) {
      trackingArea.setValid(false);

      return false;
    }

    Rectangle ir = null;

    if (imageRect != null) {
      ir = imageRect.getBounds();
      imageRect.x = p.x - 5;
      imageRect.y = p.y - 5;

      if ((ir.x == imageRect.x) && (ir.y == imageRect.y)) {
        ir = null;
      } else {
        ir = ir.union(imageRect);
      }
    }

    Rectangle ob = trackingArea.isValid() ? trackingArea.getComponentLocation() : new Rectangle();

    FormsUtils.getSelectionAreaFromPoint(this, p, trackingArea);

    if (trackingArea.isValid()) {
      iWidget w = trackingArea.selectionWidget;
      iViewer v = (w == null) ? trackingArea.gridWidget : w.getViewer();

      trackingArea.setValid(false);
      loop: do {
        if ((w != null) && dndlocal) {
          if ((w == selectedWidget) || isChildOf(selectedWidget, w)) {
            break;
          }
        }

        if ((w == null) && (trackingArea.gridWidget == selectedWidget) && (selectedWidget != null)) {
          switch (selectedWidget.getWidgetType()) {
            case WidgetPane:
            case CollapsiblePane:
            case SplitPane:
            case GridPane:
            case Form:
            case GroupBox:
            case TabPane:
              break;

            default:
              break loop;
          }
        }

        if ((v != null) && isLocked(v)) {
          break;
        }

        trackingArea.setValid(true);
      } while (false);
    }

    JComponent c = gridComponent;

    setupGridComponent(trackingArea.gridWidget);

    if (c != gridComponent) {
      repaint();
    } else {
      Rectangle nb = trackingArea.getComponentLocation();

      if (!ob.equals(nb)) {
        ob.grow(3, 3);

        if (ir == null) {
          ir = ob.getBounds();
        } else {
          ir = ir.union(ob);
        }

        ob = nb; // SwingUtilities.convertRectangle(c, trackingArea.srcBounds,
                 // this);
        ob.grow(3, 3);
        ir = ir.union(ob);
        scrollPane.paintImmediately(ir);
        paintImmediately(ir);
      } else if (ir != null) {
        scrollPane.paintImmediately(ir);
        paintImmediately(ir);
      }
    }

    return trackingArea.isValid();
  }

  public void willPerformLayout(iLayoutManager lm) {
  }

  public void setBaseURL(URL url) {
    this.baseURL = url;
  }

  public void setCanvasColor(UIColor color) {
    this.canvasColor = color;

    if (scrollPane != null) {
      handleCanvasColor(scrollPane, color);
    }
  }

  public void setCursor(Cursor cursor) {
    if (cursor == null) {
      cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    }

    super.setCursor(cursor);
  }

  public void setGridColor(UIColor c) {
    if (c != null) {
      gridColor = c;
      requestRepaint();
    }
  }

  public void setMotionTracking(boolean tracking) {
    this.motionTracking = tracking;

    if (tracking) {
      trackingArea.reset();
    }

    requestRepaint();
  }

  Object paneOwner;

  public void setOwner(Object owner) {
    paneOwner = owner;
  }

  public void setReloadedRootWidget(final iContainer w) {
    if (rootWidget == null) {
      setRootWidget(w, null);
    } else {
      rootWidget = w;
      selectedWidget = null;
      gridComponent = null;
      recalculateSelections();
      lockViewer(w);
    }
  }

  public void setRootWidget(iContainer w, Rectangle a) {
    rootWidget = w;
    selectedWidget = null;

    if (!isLocked(w)) {
      lockViewer(w);
    }

    activeArea.reset();
    selections.clear();
    undoManager.discardAllEdits();

    if (a != null) {
      activeArea.rowColumn.setLocation(a.width, a.height);
      activeArea.selectionPoint.setLocation(a.x, a.y);
    }
  }

  public void setSelectedCell(final int row, final int column) {
    Studio.runInSwingThread(new Runnable() {

      @Override
      public void run() {
        if (activeArea.gridWidget != null) {
          SelectionArea area = new SelectionArea();

          if (FormsUtils.getSelectionAreaFromCell(DesignPane.this, (iContainer) activeArea.gridWidget, area, row, column)) {
            selectArea(area, 0);
          }
        }
      }
    });
  }

  public void setSelectedWidget(final iWidget w) {
    Studio.runInSwingThread(new Runnable() {

      @Override
      public void run() {
        if ((w == selectedWidget) && (getSelectionCount() == 1)) {
          return;
        }

        setSelectedWidgetEx(w, false);
      }
    });
  }

  public void setSelectedWidgets(List<iWidget> widgets, iWidget anchor) {
    if (!setSelectedWidgetsEx(widgets, anchor, false)) {
      setSelectedWidget(rootWidget);
    } else {
      finishSelectionChanges(selectedWidget);
    }
  }

  public void setSelectionColor(UIColor c) {
    if (c != null) {
      selectionBorder.setLineColor(c);
      requestRepaint();
    }
  }

  public void setShowGrid(boolean show) {
    if (this.showGrid != show) {
      showGrid = show;
      requestRepaint();
    }
  }

  public void setTrackingColor(UIColor c) {
    if (c != null) {
      cellBorder.setLineColor(c);
      trackingColor = c;
      requestRepaint();
    }
  }

  public URL getBaseURL() {
    return baseURL;
  }

  public int getCurrentColumn() {
    return activeArea.rowColumn.x;
  }

  public int getCurrentRow() {
    return activeArea.rowColumn.y;
  }

  public BufferedImage getDragImage() {
    return Utilities.getBufferedImage(selectedWidget);
  }

  public UIColor getGridColor() {
    return gridColor;
  }

  public iWidget getGridWidget() {
    return (activeArea.gridWidget == null) ? rootWidget : activeArea.gridWidget;
  }

  public int getModCount() {
    return undoManager.getModCount();
  }

  public Object getOwner() {
    return paneOwner;
  }

  public iContainer getRootWidget() {
    return rootWidget;
  }

  public iWidget getSelectedWidget() {
    return selectedWidget;
  }

  public List<Widget> getSelectedWidgetConfigs() {
    return getSelectedWidgetConfigsEx(true);
  }

  public List<iWidget> getSelectedWidgets() {
    ArrayList<iWidget> list = new ArrayList<iWidget>(selections.size());
    iWidget w;

    if ((selectedWidget != rootWidget) && (selectedWidget != null)) {
      list.add(selectedWidget);
    }

    for (SelectionArea a : selections) {
      w = a.selectionWidget;

      if ((w == null) || (w == selectedWidget)) {
        continue;
      }

      list.add(w);
    }

    if (list.size() == 0) {
      list.add(rootWidget);
    }

    return list;
  }

  public Object getSelection() {
    if (selectedWidget != null) {
      WidgetList list = new WidgetList(selections.size());

      if (selectedWidget == rootWidget) {
        list.add((Widget) ((Widget) selectedWidget.getLinkedData()).clone());
      } else {
        iWidget w;

        for (SelectionArea a : selections) {
          w = a.selectionWidget;

          if (w == null) {
            continue;
          }

          list.add((Widget) ((Widget) w.getLinkedData()).clone());
        }
      }

      if (list.size() == 0) {
        list.add((Widget) ((Widget) selectedWidget.getLinkedData()).clone());
      }

      return list;
    }

    return null;
  }

  public SelectionArea getSelectionArea() {
    return activeArea;
  }

  public void prepareForReload() {
    for (SelectionArea a : selections) {
      a.resetForReload();
    }
    rootWidget = null;
    gridComponent = null;
    dropTracking = false;

  }

  public int getSelectionCount() {
    int count = 0;

    for (SelectionArea a : selections) {
      if (a.selectionWidget != null) {
        count++;
      }
    }

    return (count == 0) ? 1 : count;
  }

  public UtilityPanel getTopPanel() {
    return topPanel;
  }

  public UndoManagerEx getUndoManager() {
    return undoManager;
  }

  public boolean hasSelection() {
    return (selectedWidget != null);
  }

  public boolean isDirty() {
    return undoManager.getModCount() != 0;
  }

  public boolean isDropTracking() {
    return dropTracking;
  }

  public boolean isInLockedViewer(iWidget w) {
    if ((rootWidget == null) || (w == null)) {
      return false;
    }

    iViewer v = w.getViewer();

    while ((v != null) && (v != rootWidget)) {
      if (v.getViewerActionLink() != null) {
        return true;
      }

      v = v.getParent();
    }

    return false;
  }

  public boolean isLocked(iViewer v) {
    iPlatformComponent c = v.getContainerComponent();

    return c.isLocked();
  }

  public boolean isMotionTracking() {
    return motionTracking;
  }

  public boolean isShowGrid() {
    return showGrid;
  }

  boolean canStartDrag(Point location) {
    iWidget w = FormsUtils.getWidgetAtPoint(this, location);

    if (motionTracking || (w == null) || (w == rootWidget) || (w != selectedWidget)) {
      return false;
    }

    if ((selections.size() > 1) && !absoluteLayout) {
      return false;
    }

    if ((w instanceof aWidget) && ((aWidget) w).getLinkedData() instanceof Widget) {
      dragImage = getDragImage();

      if (dragImage != null) {
        imageRect = new Rectangle(dragImage.getWidth(null), dragImage.getHeight(null));
      }

      // setCursor(null);
      return true;
    }

    return false;
  }

  Transferable createTransferable() {
    return new DesignTransferable((WidgetList) getSelection(), paneID);
  }

  void dragMove(Component source, Point p) {
    JScrollPane sp = scrollPane;

    if (sp == null) {
      return;
    }

    JViewport vp = sp.getViewport();

    SwingUtilities.convertPointFromScreen(p, vp);

    Rectangle r = new Rectangle(p.x, p.y, 10, 10);

    vp.scrollRectToVisible(r);
  }

  void finishSelectionChanges(iWidget oldSelection) {
    if (!selectionEventsDisabled) {
      boolean news = (selectedWidget != null) && (selectedWidget != rootWidget);
      boolean olds = (oldSelection != null) && (oldSelection != rootWidget);

      if (olds != news) {
        this.firePropertyChange(iConstants.RARE_SELECTION_PROPERTY, Boolean.valueOf(olds), Boolean.valueOf(news));
      }
      DesignEvent designEvent = new DesignEvent(this, paneOwner);

      designEvent.setWidget(selectedWidget);
      designEvent.setEvent(EventType.SELECTION_CHANGED);
      fireEvent(designEvent);
    }

    ActionHelper.updateDefaultActions(component);
    requestRepaint();
  }

  void fireWidgetActionEvent() {
    DesignEvent designEvent = new DesignEvent(this, paneOwner);
    designEvent.setWidget(selectedWidget);
    designEvent.setEvent(EventType.WIDGET_ACTION);
    fireEvent(designEvent);
  }

  boolean handleShiftSelect(SelectionArea area) {
    Rectangle r = new Rectangle(area.rowColumn.x, area.rowColumn.y, 1, 1);

    r.add(activeArea.rowColumn.x, activeArea.rowColumn.y);

    if (activeArea.rowColumn.x > area.rowColumn.x) {
      r.width++;
    }

    if (activeArea.rowColumn.y > area.rowColumn.y) {
      r.height++;
    }

    List<iWidget> widgets = FormsUtils.getWidgets(area.gridWidget, r);

    if (widgets != null) {
      // selections.clear();
      widgets.remove(activeArea.selectionWidget);
      widgets.remove(area.selectionWidget);
    }

    return setSelectedWidgetsEx(widgets, null, true);
  }

  int indexOfBoundsSelection(Rectangle r) {
    int len = selections.size();
    SelectionArea a;

    for (int i = 0; i < len; i++) {
      a = selections.get(i);

      if (a.getComponentLocation().equals(r)) {
        return i;
      }
    }

    return -1;
  }

  int indexOfWidgetSelection(iWidget w) {
    int len = selections.size();
    SelectionArea a;

    for (int i = 0; i < len; i++) {
      a = selections.get(i);

      if (a.selectionWidget == w) {
        return i;
      }
    }

    return -1;
  }

  void paintRegionsBorders(Graphics g, Component c, aRegionViewer rv) {
    int len = rv.getRegionCount();
    Component cc;
    Point p;

    for (int i = 0; i < len; i++) {
      cc = rv.getRegion(i).getContainerComponent().getView();
      p = cc.getLocation();
      g.drawRect(p.x + 1, p.y + 1, cc.getWidth() - 2, cc.getHeight() - 2);
    }

    g.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
  }

  void paintSelections(Graphics g) {
    SelectionArea sa = activeArea;
    Border b;
    Border cb = cellBorder;
    Border sb = selectionBorder;

    for (SelectionArea a : selections) {
      if (a != sa) {
        b = (a.selectionWidget == null) ? cb : sb;
        a.paintBorderEx(this, g, b, false, false);
      }
    }

    if (sa.isValid() && sa.cellSelected) {
      if ((sa.selectionWidget == null)) {
        if (!dropTracking) {
          sa.paintBorderEx(this, g, cb, false, true);
        }
      } else {
        sa.paintBorderEx(this, g, sb, false, true);
      }
    }
  }

  void paintWidgetGrid(iWidget w, Component c, Graphics2D g) {
    boolean show = showGrid;

    if (dropTracking || motionTracking) {
      show = true;
    }

    if (!show) {
      return;
    }
    // We only want to show the root widget selected when we last clicked ouside
    // of it's bounds
    if ((selectedWidget == rootWidget) && (activeArea.selectionWidget == rootWidget)) {
      if (!activeArea.srcBounds.contains(activeArea.selectionPoint)) {
        return;
      }
    }

    if (c != gridComponent) {
      return;
    }

    if (!FormsUtils.paintGridLines(g, gridComponent, lineStroke, gridColor)) {
      Stroke stroke = g.getStroke();
      Color color = g.getColor();

      g.setColor(gridColor);
      g.setStroke(lineStroke);

      switch (w.getWidgetType()) {
        case SplitPane:
          paintRegionsBorders(g, c, (aRegionViewer) w);

          break;

        default:
          g.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
      }

      g.setColor(color);
      g.setStroke(stroke);
    }
  }

  boolean performSelection(SelectionArea area, int modifiers) {
    boolean add = isControlDown(modifiers) || isShiftDown(modifiers) || isAltDown(modifiers);
    iWidget w = area.selectionWidget;
    // if (add && (area.selectionWidget == null)) {
    // return false;
    // }
    // if (w == rootWidget) {
    // area.selectionWidget = null;
    // w = null;
    // }
    int n;

    if (w == null) {
      n = indexOfBoundsSelection(area.getComponentLocation());
    } else {
      n = indexOfWidgetSelection(w);
    }

    if ((n != -1) && !isAltDown(modifiers) && add) { // selected and not
                                                     // changing primary
                                                     // selection
      selections.remove(n);

      if (w == selectedWidget) {
        selectedWidget = null;
        activeArea.reset();
      }

      return true;
    }

    if (isAltDown(modifiers) || !add) {
      activeArea = area;

      if (selectedWidget != area.selectionWidget) {
        selectedWidget = area.selectionWidget;
      }

      if (selectedWidget == null) {
        selectedWidget = area.gridWidget;
      }

      if (selectedWidget == null) {
        selectedWidget = rootWidget;
      }

      iWidget p = activeArea.gridWidget;

      if (p == null) {
        p = rootWidget;
      }

      setupGridComponent(p);
    }

    if (!add) {
      selections.clear();
      selections.add(area);
    } else if (n == -1) {
      selections.add(area);
    } else {
      selections.set(n, area);
    }

    return true;
  }

  //
  // void recalculateSelectionRects(List<SelectionArea> list, iWidget gw) {
  // Point gridSize = null;
  //
  // if (gw != null) {
  // gridSize = FormsUtils.getGridSize((iContainer) gw);
  //
  // if (gridSize != null) {
  // gridSize.x--;
  // gridSize.y--;
  // }
  // }
  //
  // Point p;
  //
  // for (SelectionArea a : list) {
  // if (a.selectionWidget != null) {
  // continue;
  // }
  //
  // if (gridSize != null) {
  // p = a.rowColumn;
  //
  // if (p.x > gridSize.x) {
  // p.x = gridSize.x;
  // }
  //
  // if (p.y > gridSize.y) {
  // p.y = gridSize.y;
  // }
  //
  // if (p.x < 0) {
  // p.x = 0;
  // }
  //
  // if (p.y < 0) {
  // p.y = 0;
  // }
  //
  // SelectionArea aa = new SelectionArea();
  //
  // if (FormsUtils.getSelectionAreaFromCell(DesignPane.this, (iContainer) gw,
  // aa, p.y, p.x)) {
  // selectArea(aa, MouseEvent.CTRL_DOWN_MASK);
  //
  // continue;
  // }
  // }
  //
  // p = a.getComponentLocation().getLocation();
  // p.x += 4;
  // p.y += 4;
  // selectWidgetAtPoint(p, MouseEvent.CTRL_DOWN_MASK);
  // }
  // }

  void recalculateSelections() {
    if (rootWidget == null) {
      return;
    }
    try {
      List<SelectionArea> list = (List<SelectionArea>) selections.clone();
      selections.clear();
      if (!activeArea.revalidate()) {
        activeArea.reset();
      }
      selectedWidget = null;
      iWidget w;
      int len = list.size();
      iContainer root = rootWidget.getContainerViewer();
      SelectionArea area = new SelectionArea();
      for (int i = 0; i < len; i++) {
        SelectionArea a = list.get(i);
        if (a == activeArea) {
          if (a.isValid()) {
            selections.add(a);
          }
        } else if (a.selectionWidgetConfig instanceof Widget) {
          w = Utilities.findWidget(root, (Widget) a.selectionWidgetConfig);
          if (w != null) {
            FormsUtils.getSelectionAreaFromWidget(this, w, area, null);

            if (performSelection(area, MouseEvent.CTRL_DOWN_MASK)) {
              area = new SelectionArea();
            }
          }
        } else if (a.gridWidgetConfig instanceof Widget) {
          w = Utilities.findWidget(root, (Widget) a.gridWidgetConfig);
          if (w != null) {
            recalculateSelectionRect(a, w);
            area = new SelectionArea();
          }
        }
      }
      if (activeArea.gridWidget != null) {
        setupGridComponent(activeArea.gridWidget);
        selectedWidget = activeArea.selectionWidget;
      } else {
        clearSelections();
      }
    } finally {
      finishSelectionChanges(null);
    }
  }

  void recalculateSelectionRect(SelectionArea a, iWidget gw) {
    Point gridSize = null;

    if (gw != null) {
      gridSize = FormsUtils.getGridSize((iContainer) gw);

      if (gridSize != null) {
        gridSize.x--;
        gridSize.y--;
      }
    }

    Point p;

    if (gridSize != null) {
      p = a.rowColumn;

      if (p.x > gridSize.x) {
        p.x = gridSize.x;
      }

      if (p.y > gridSize.y) {
        p.y = gridSize.y;
      }

      if (p.x < 0) {
        p.x = 0;
      }

      if (p.y < 0) {
        p.y = 0;
      }

      SelectionArea aa = new SelectionArea();

      if (FormsUtils.getSelectionAreaFromCell(DesignPane.this, (iContainer) gw, aa, p.y, p.x)) {
        selectArea(aa, MouseEvent.CTRL_DOWN_MASK);

        return;
      }
    }

    p = a.getComponentLocation().getLocation();
    p.x += 4;
    p.y += 4;
    selectWidgetAtPoint(p, MouseEvent.CTRL_DOWN_MASK);

  }

  // void recalculateSelections(iWidget gw) {
  // if (rootWidget == null) {
  // return;
  // }
  //
  // if ((this.selectedConfigs == null) || (selectedConfigs.size() == 0)) {
  // List<SelectionArea> list = (List<SelectionArea>) selections.clone();
  //
  // selections.clear();
  // recalculateSelectionRects(list, gw);
  //
  // if (selectedWidget == null) {
  // clearSelections();
  // finishSelectionChanges(null);
  // }
  //
  // return;
  // }
  //
  // try {
  // selections.clear();
  // activeArea.reset();
  // selectedWidget = null;
  //
  // iWidget w;
  // int len = selectedConfigs.size();
  // iContainer root = rootWidget.getContainerViewer();
  // SelectionArea area = new SelectionArea();
  //
  // for (int i = 0; i < len; i++) {
  // w = Utilities.findWidget(root, selectedConfigs.get(i));
  //
  // if (w != null) {
  // FormsUtils.getSelectionAreaFromWidget(this, w, area, null);
  //
  // if (performSelection(area, 0)) {
  // area = new SelectionArea();
  // }
  // }
  // }
  //
  // if (selections.size() > 0) {
  // selectedWidget = selections.get(0).selectionWidget;
  // }
  //
  // if (selectedWidget == null) {
  // clearSelections();
  // }
  // } finally {
  // selectedConfigs = null;
  // finishSelectionChanges(null);
  // }
  // }
  void requestDesignSwitch() {
    DesignEvent designEvent = new DesignEvent(this, paneOwner);
    designEvent.setWidget(rootWidget);
    designEvent.setEvent(EventType.SWITCH_TO_DESIGN);
    fireEvent(designEvent);
  }

  void requestSourceSwitch() {
    DesignEvent designEvent = new DesignEvent(this, paneOwner);
    designEvent.setWidget(rootWidget);
    designEvent.setEvent(EventType.SWITCH_TO_SOURCE);
    fireEvent(designEvent);
  }

  iWidget selectArea(SelectionArea area, int modifiers) {
    iWidget ow = selectedWidget;
    boolean ok = false;

    if (isShiftDown(modifiers) && (ow != null) && (ow != rootWidget) && (ow == activeArea.selectionWidget)) {
      selections.clear();
      selections.add(activeArea);
      handleShiftSelect(area);
      ok = true;

      if (area.selectionWidget != ow) {
        performSelection(area, modifiers);
      }
    } else {
      if (performSelection(area, modifiers)) {
        ok = true;
      }
    }

    if (ok) {
      finishSelectionChanges(ow);
    }

    return selectedWidget;
  }

  iWidget selectWidgetAtPoint(Point p, int modifiers) {
    SelectionArea area = new SelectionArea();

    FormsUtils.getSelectionAreaFromPoint(this, p, area);

    return selectArea(area, modifiers);
  }

  void sendEvent(EventType event, Object data) {
    DesignEvent designEvent = new DesignEvent(this, paneOwner);
    designEvent.setWidget(selectedWidget);
    designEvent.setEvent(event);
    designEvent.setData(data);
    fireEvent(designEvent);
  }

  boolean setSelectedWidgetsEx(List<iWidget> widgets, iWidget anchor, boolean add) {
    if (!add) {
      selections.clear();
      selectedWidget = null;
      activeArea.reset();
    }

    if ((widgets == null) || (widgets.size() == 0)) {
      return false;
    }

    boolean ok = false;
    SelectionArea a = new SelectionArea();
    int alt = MouseEvent.CTRL_MASK | MouseEvent.ALT_MASK;

    for (iWidget w : widgets) {
      FormsUtils.getSelectionAreaFromWidget(this, w, a, null);

      if (performSelection(a, (w == anchor) ? alt : MouseEvent.CTRL_MASK)) {
        ok = true;
        a = new SelectionArea();
      }
    }

    return ok;
  }

  List<Widget> getSelectedWidgetConfigsEx(boolean rootOk) {
    WidgetList list = new WidgetList(selections.size());
    iWidget w;

    if (activeArea.selectionWidget != null) {
      list.add((Widget) activeArea.selectionWidget.getLinkedData());
    }

    for (SelectionArea a : selections) {
      if (a.isValid()) {
        continue;
      }
      w = a.selectionWidget;

      if ((w == null) || (w == selectedWidget)) {
        continue;
      }

      list.add(((Widget) w.getLinkedData()));
    }

    if ((list.size() == 0) && (activeArea.gridWidget != null) && activeArea.gridWidget != rootWidget) {
      list.add((Widget) activeArea.gridWidget.getLinkedData());
    }

    if ((list.size() == 0) && rootOk) {
      list.add(((Widget) rootWidget.getLinkedData()));
    }

    return (list.size() == 0) ? null : list;
  }

  SelectionArea getTrackingArea() {
    return trackingArea;
  }

  protected void dragEnter(Point p, Transferable t) {
    dragImage = null;
    imageRect = null;

    try {
      iWidget source;

      if (t == null) {
        source = getSelectedWidget();
      } else {
        source = (iWidget) t.getTransferData((DataFlavor) TransferFlavor.widgetFlavor.getPlatformFlavor());
      }

      if ((source != null) && (source.getWidgetType() == WidgetType.ListBox)) {
        Icon icon = source.getDragIcon();

        if (icon instanceof ImageIcon) {
          dragImage = ((ImageIcon) icon).getImage();
        }
      } else if (source == null) {
        dragImage = getDragImage();
      }

      if (dragImage != null) {
        imageRect = new Rectangle(dragImage.getWidth(null), dragImage.getHeight(null));
      }
    } catch (Exception ex) {
      Platform.ignoreException("dragEnter", ex);
    }

    dropTracking = true;
    motionTracking = false;
  }

  protected void dragExit() {
    imageRect = null;
    dragImage = null;
    dropTracking = false;
    motionTracking = false;
    trackingArea.setValid(false);
    setupGridComponent(activeArea.gridWidget);
    requestRepaint();
  }

  protected void dragOver(Point p, Transferable t) {
    boolean ok = updateDropTracking(p, true);

    if (!ok) {
      trackingArea.setValid(false);
      clearDropRectAndCursor(p);
    }
  }

  protected void drop(int action, Point p, Object o, boolean local) {
    try {
      imageRect = null;
      dragImage = null;
      setupGridComponent(activeArea.gridWidget);

      Widget w = null;

      if (o instanceof List) {
        List l = (List) o;

        if (l.size() > 0) {
          w = (Widget) l.get(0);
        }
      }

      if (local && (action & DnDConstants.ACTION_COPY) == 0) {
        w = null;
      }

      if ((action & DnDConstants.ACTION_COPY) > 0) {
        local = false;
      }

      if ((w == null) && !local) {
        UISoundHelper.errorSound();
        requestRepaint();
      } else {
        handleDrop(p, w, selectedWidget);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      requestRepaint();
    } finally {
      trackingArea.setValid(false);
      dropTracking = false;
      motionTracking = false;
    }
  }

  protected iWidget findByElementEquals(iContainer fv, iSPOTElement e) {
    Object o = fv.getLinkedData();

    if ((o instanceof iSPOTElement) && e.equals(o)) {
      return fv;
    }

    List<String> names = fv.getWidgetNames();

    if (names == null) {
      return null;
    }

    for (String s : names) {
      iWidget w = fv.getWidget(s);
      o = (w == null) ? null : w.getLinkedData();

      if ((o instanceof iSPOTElement) && e.equals(o)) {
        return w;
      }

      if (w instanceof iContainer) {
        iWidget ww = findByElementEquals((iContainer) w, e);

        if (ww != null) {
          return ww;
        }
      }
    }

    return null;
  }

  protected void paintChildren(Graphics g) {
    super.paintChildren(g);
  }

  protected void paintComponent(Graphics g) {
    if ((motionTracking || dropTracking) && trackingArea.isValid()) {
      trackingArea.paintBorderEx(this, g, cellBorder, true, false);

      if (imageRect != null) {
        g.drawImage(dragImage, imageRect.x, imageRect.y, imageRect.width, imageRect.height, null);
      }
    }
  }

  protected void stopDraggin(boolean cancel) {
  }

  protected void updateEx(boolean repaint) {
    if (rootWidget != null) {
      trackingArea.setValid(false);

      // if (selectedConfigs == null) {
      // selectedConfigs = getSelectedWidgetConfigsEx(true);
      // }

      selectionEventsDisabled = hasHadSelection;
      hasHadSelection = true;

      try {
        recalculateSelections();

        if (repaint) {
          requestRepaint();
        }
      } finally {
        selectionEventsDisabled = false;
      }
    }
  }

  protected void setSelectedWidgetEx(iWidget w, boolean add) {
    SelectionArea area = new SelectionArea();
    iWidget ow = selectedWidget;

    if (w != null) {
      Component c = w.getContainerComponent().getView();

      if (!c.isShowing()) {
        Utilities.expandAllCollapsible(w);
        SwingHelper.makeComponentVisibleToUser(c);
      }

      FormsUtils.getSelectionAreaFromWidgetEx(this, w, area, null, true);
    } else {
      area.reset();
    }

    if (performSelection(area, add ? MouseEvent.CTRL_MASK : 0)) {
      finishSelectionChanges(ow);
    }

    repaint();
  }

  private void changeFocusStateColor() {
    JScrollPane sp = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, DesignPane.this);

    if ((sp != null) && (sp.getBorder() instanceof UICompoundBorder)) {
      Border b = ((UICompoundBorder) sp.getBorder()).getOutsideBorder();

      if (b instanceof UILineBorder) {
        ((UILineBorder) b).setLineColor(focusOwner ? UIColor.CYAN : UIColor.LIGHTGRAY);
        sp.repaint();
      }
    }
  }

  private void clearDropRectAndCursor(Point p) {
    if (imageRect != null) {
      int x = imageRect.x;
      int y = imageRect.y;

      imageRect.x = p.x - 5;
      imageRect.y = p.y - 5;

      if ((x != imageRect.x) || (y != imageRect.y)) {
        unionRect.setBounds(x, y, imageRect.width, imageRect.height);
        unionRect.createUnion(imageRect);
        paintImmediately(unionRect);
      }
    }

    if (trackingArea.isValid()) {
      trackingArea.repaint();
    }
  }

  private void exportToClipboard(Clipboard clip, int action) throws IllegalStateException {
    if (action == TransferHandler.NONE) {
      return;
    }

    WidgetList list = (WidgetList) getSelection();

    if ((list == null) || (list.size() == 0)) {
      return;
    }

    DesignTransferable t = new DesignTransferable(list, paneID);

    try {
      clip.setContents(t, null);
      ActionHelper.getPasteAction().update();

      if (action == TransferHandler.MOVE) {
        deleteSelectedData(false);
      }
    } catch (IllegalStateException ise) {
      ise.printStackTrace();
    }
  }

  private void fireEvent(DesignEvent e) {
    if (fireEvents) {
      Object[] listeners = listenerList.getListenerList();

      // Process the listeners last to first, notifying
      // those that are interested in this event
      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iDesignListener.class) {
          ((iDesignListener) listeners[i + 1]).somethingHappened(e);
        }
      }
    }
  }

  private void fireEvent(UndoableEditEvent e) {
    if (fireEvents) {
      Object[] listeners = listenerList.getListenerList();

      // Process the listeners last to first, notifying
      // those that are interested in this event
      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == UndoableEditListener.class) {
          ((UndoableEditListener) listeners[i + 1]).undoableEditHappened(e);
        }
      }
    }
  }

  private void handleCanvasColor(iPainterSupport p, UIColor c) {
    if (c == null) {
      p.getComponentPainter().setBackgroundPainter(Studio.getCheckeredImagePainter(), false);
    } else {
      p.getComponentPainter().setBackgroundPainter(new UISimpleBackgroundPainter(c), false);
    }
  }

  private boolean importData(Transferable t) {
    boolean imported = true;

    try {
      if (t.isDataFlavorSupported(DesignTransferable.getMainDataFlavor()) && (activeArea.gridWidget != null)) {
        List<Widget> list = (List<Widget>) t.getTransferData(DesignTransferable.getMainDataFlavor());

        for (Widget wc : list) {
          wc = (Widget) wc.clone();
          FormsUtils.handleDropEx(this, wc, new DnDPopupMenu(), false);
        }
      }
    } catch (Exception e) {
      imported = false;
    }

    return imported;
  }

  private void lockViewer(iViewer c) {
    iViewer v;

    if (c == null) {
      return;
    }

    if (c != rootWidget && isLockable(c)) {
      c.setEnabled(false);
      c.getContainerComponent().setLocked(true);

      return;
    }

    int len;

    if (c instanceof aRegionViewer) {
      aRegionViewer rv = (aRegionViewer) c;

      len = rv.getRegionCount();

      iTarget t;

      for (int i = 0; i < len; i++) {
        t = rv.getRegion(i);

        if (t == null) {
          continue;
        }

        v = t.getViewer();

        if ((v != null) && isLockable(v)) {
          t.setLocked(true);
        } else if (v != null) {
          lockViewer(v);
        }
      }

      return;
    }

    if (c instanceof aTabPaneViewer) {
      aTabPaneViewer tv = (aTabPaneViewer) c;
      iTabDocument doc = tv.getSelectedTabDocument();
      iTarget t = (doc == null) ? null : doc.getTarget();

      if (t != null) {
        v = t.getViewer();

        if ((v != null) && (v.getViewerActionLink() != null)) {
          t.setLocked(true);
        }
      }

      return;
    }

    if (!(c instanceof iContainer)) {
      return;
    }

    len = c.getWidgetCount();

    if (len == 0) {
      return;
    }

    List<iWidget> list = ((iContainer) c).getWidgetList();

    for (int i = 0; i < len; i++) {
      v = list.get(i).getViewer();

      if (v != c) {
        lockViewer(v);
      }
    }
  }

  public void requestRepaint() {
    if (scrollPane != null) {
      scrollPane.repaint();
    }
    repaint();

    if (borderComponent != null) {
      borderComponent.repaint();
    }
    if (gridComponent != null) {
      gridComponent.repaint();
    }
  }

  private void setupGridComponent(iWidget w) {
    w = (w == null) ? null : w.getContainerViewer();

    if (w == null) {
      w = rootWidget;
    }

    gridComponent = w.getDataComponent().getView();

    if (!(gridComponent instanceof UtilityPanel)) {
      gridComponent = w.getContainerComponent().getView();
    }
  }

  private void showPopupMenu(MouseEvent e) {
    UIMenu menu = new UIMenu();
    Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), rootWidget.getContainerComponent().getView());

    MenuUtils.addTextActions(menu, false, false);
    FormsUtils.addWidgetMenu(this, menu, p, activeArea.gridWidget);

    JPopupMenu pm = new JPopupMenu();

    menu.copyTo(pm);
    pm.show(this, e.getX(), e.getY());
  }

  private Point getMouseLocation() {
    Point mp = MouseInfo.getPointerInfo().getLocation();
    Point sp = getLocationOnScreen();

    return new Point(mp.x - sp.x, mp.y - sp.y);
  }

  private boolean isAltDown(int modifiers) {
    return (modifiers & MouseEvent.ALT_MASK) != 0;
  }

  private boolean isChildOf(iWidget parent, iWidget w) {
    iWidget p;

    do {
      p = w.getParent();

      if (p == parent) {
        return true;
      }

      w = p;
    } while (w != null);

    return false;
  }

  private boolean isControlDown(int modifiers) {
    return (modifiers & MouseEvent.CTRL_MASK) != 0;
  }

  private boolean isShiftDown(int modifiers) {
    return (modifiers & MouseEvent.SHIFT_MASK) != 0;
  }

  private boolean isLockable(iViewer v) {
    try {
      return v.getViewerActionLink() != null && v.getViewerActionLink().getURL(v) != null;
    } catch (MalformedURLException e) {
      return true;
    }
  }

  public static interface DesignEdit extends UndoableEdit {
    void setReloadRequired(boolean reloadRequired);

    DesignPane getDesignPane();

    boolean isReloadRequired();
  }

  public static class DesignCompoundEdit extends CompoundEdit implements DesignEdit {
    DesignPane      pane;
    private boolean reloadRequired;

    DesignCompoundEdit(DesignPane pane) {
      this.pane = pane;
    }

    public boolean addEdit(UndoableEdit anEdit) {
      if (anEdit instanceof DesignCompoundEdit) {
        // addEdits((DesignCompoundEdit)anEdit);
        // return true;
        ((DesignCompoundEdit) anEdit).end();
      }

      boolean ok = super.addEdit(anEdit);

      if (ok) {
        if (anEdit instanceof DesignEdit) {
          DesignEdit de = (DesignEdit) anEdit;

          if (de.isReloadRequired()) {
            this.setReloadRequired(true);
            de.setReloadRequired(false);
          }
        }
      }

      return ok;
    }

    public void addEdits(DesignCompoundEdit edits) {
      if (edits != null) {
        for (UndoableEdit e : edits.edits) {
          addEdit(e);
        }
      }
    }

    public UndoableEdit peal() {
      if (edits.size() == 1) {
        UndoableEdit e = edits.firstElement();

        if (this.isReloadRequired()) {
          if (e instanceof DesignEdit) {
            DesignEdit de = (DesignEdit) e;

            de.setReloadRequired(true);
          } else {
            return this;
          }
        }

        return e;
      }

      return this;
    }

    public void redo() throws CannotRedoException {
      super.redo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }

    public void undo() throws CannotUndoException {
      super.undo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }

    public void setReloadRequired(boolean reloadRequired) {
      this.reloadRequired = reloadRequired;
    }

    public DesignPane getDesignPane() {
      return pane;
    }

    public boolean hasEdits() {
      return edits.size() > 0;
    }

    public boolean isReloadRequired() {
      if (reloadRequired) {
        return true;
      }

      for (UndoableEdit e : edits) {
        if ((e instanceof DesignEdit) && ((DesignEdit) e).isReloadRequired()) {
          return true;
        }
      }

      return false;
    }
  }

  public static class DesignModePainter extends UIComponentPainter {
    @Override
    public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation, boolean end) {
      if (g.getComponent() == null) {
        return;
      }

      iWidget w = g.getComponent().getWidget();

      if ((w == null) || !w.isDesignMode()) {
        return;
      }

      DesignPane pane = findGlassPane(w);

      if (pane == null) {
        return;
      }

      if (w.getViewer().getViewerActionLink() == null) {
        pane.paintWidgetGrid(w, g.getComponent().getView(), g.getGraphics());
      }
    }

    DesignPane findGlassPane(iWidget w) {
      Component parent = w.getContainerComponent().getView();

      while (parent != null) {
        if (parent instanceof FrameView) {
          if (((FrameView) parent).getGlassPane() instanceof DesignPane) {
            return (DesignPane) ((FrameView) parent).getGlassPane();
          }
        }

        parent = parent.getParent();
      }

      return null;
    }
  }

  public static class DesignUndoableEdit extends AbstractUndoableEdit implements DesignEdit {
    protected DesignPane pane;
    private boolean      reloadRequired;

    public DesignUndoableEdit(DesignPane pane) {
      this.pane = pane;
    }

    public void setReloadRequired(boolean reloadRequired) {
      this.reloadRequired = reloadRequired;
    }

    public DesignPane getDesignPane() {
      return pane;
    }

    public boolean isReloadRequired() {
      return reloadRequired;
    }
  }

  class KeyboardHandler extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
      int row = getCurrentRow();
      int col = getCurrentColumn();
      boolean ok = true;
      iWidget gw = getGridWidget();

      if (e.isControlDown()) {
        dropAction = DnDConstants.ACTION_COPY;
      } else {
        dropAction = DnDConstants.ACTION_MOVE;
      }

      if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        if (dropTracking) {
          try {
            setCursor(Cursor.getDefaultCursor());
          } finally {
            dropTrackingCanceled = true;
            dropTracking = false;
            motionTracking = false;
            requestRepaint();
          }
        }
      } else {
        if (gw instanceof iContainer) {
          switch (e.getKeyCode()) {

            case KeyEvent.VK_UP:
              row--;

              break;

            case KeyEvent.VK_DOWN:
              row++;

              break;

            case KeyEvent.VK_LEFT:
              col--;

              break;

            case KeyEvent.VK_RIGHT:
              col++;

              break;

            default:
              ok = false;
          }

          if (ok) {
            SelectionArea area = new SelectionArea();

            if (FormsUtils.getSelectionAreaFromCell(DesignPane.this, (iContainer) gw, area, row, col)) {
              selectArea(area, 0);
            }
          }
        }
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      if (!e.isControlDown()) {
        dropAction = DnDConstants.ACTION_MOVE;
      }
    }
  }

  class PaneBorder implements Border {
    public void paintBorder(Component comp, Graphics g, int x, int y, int width, int height) {
      if (!dropTracking) {
        Point r = null, c = null;

        if (!activeArea.isValid()) {
          g.setColor(trackingColor);
          g.fillRect(0, 3, 3, 3);
          g.fillRect(3, 0, 3, 3);
        } else if (activeArea.gridWidget != null) {
          Rectangle b = activeArea.getComponentLocation();

          r = b.getLocation();
          c = b.getLocation();
          r.y += (b.height / 2);
          c.x += (b.width / 2);
          r.y--;
          c.x--;
          g.setColor(trackingColor);
          g.fillRect(0, r.y, 3, 3);
          g.fillRect(c.x, 0, 3, 3);
        }
      }
    }

    public Insets getBorderInsets(Component c) {
      return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
      return false;
    }
  }

  class PaneDropTargetListener implements DropTargetListener {
    public void dragEnter(DropTargetDragEvent dtde) {
      if (!dtde.isDataFlavorSupported(DesignTransferable.getMainDataFlavor())) {
        dragImage = null;
        imageRect = null;

        return;
      }

      DesignPane.this.dragEnter(dtde.getLocation(), dtde.getTransferable());
    }

    public void dragExit(DropTargetEvent dte) {
      DesignPane.this.dragExit();
    }

    public void dragOver(DropTargetDragEvent dtde) {
      boolean local = dtde.isDataFlavorSupported(DesignTransferable.getLocalDataFlavor());
      boolean ok = false;
      // don't trust DnD location on mac coming through SWT
      Point p = dtde.getLocation();

      System.out.println(p);

      if (dtde.isDataFlavorSupported(DesignTransferable.getMainDataFlavor())) {
        ok = updateDropTracking(p, local);
      } else {
        trackingArea.setValid(false);
        clearDropRectAndCursor(p);
        ok = false;
      }

      if (ok) {
        dtde.acceptDrag(dtde.getDropAction());
      } else {
        dtde.rejectDrag();
      }
    }

    public void drop(DropTargetDropEvent dtde) {
      try {
        imageRect = null;
        dragImage = null;
        setupGridComponent(activeArea.gridWidget);

        Transferable t = dtde.getTransferable();
        Object o = t.getTransferData(DesignTransferable.getMainDataFlavor());
        Widget w = null;

        if (o instanceof List) {
          List l = (List) o;

          if (l.size() > 0) {
            w = (Widget) l.get(0);
          }
        }

        boolean local = false;

        if (t.isDataFlavorSupported(DesignTransferable.getLocalDataFlavor())) {
          local = ((Long) t.getTransferData(DesignTransferable.getLocalDataFlavor())) == paneID;

          if (local && (dtde.getDropAction() & DnDConstants.ACTION_COPY) == 0) {
            w = null;
          }
        }

        if ((dtde.getDropAction() & DnDConstants.ACTION_COPY) > 0) {
          local = false;
        }

        if ((w == null) && !local) {
          UISoundHelper.errorSound();
          requestRepaint();
        } else {
          // don't trust DnD location on mac coming through SWT
          handleDrop(getMouseLocation(), w, selectedWidget);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      } finally {
        trackingArea.setValid(false);
        dropTracking = false;
        motionTracking = false;
      }
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }
  }

  class PaneMouseAdapter extends MouseInputAdapter {
    private long mouseDownTime;

    public void mouseClicked(MouseEvent e) {
      if (e.getClickCount() > 1) {
        if (!motionTracking) {
          fireWidgetActionEvent();
        }
      }
    }

    public void mouseDragged(MouseEvent e) {
      if (!dropTracking && !dropTrackingCanceled && !wasDropTracking && (selectedWidget != null) && (selectedWidget != rootWidget)) {
        if ((Math.abs(e.getX() - mouseDown.x) > DRAG_SLOP) || (Math.abs(e.getY() - mouseDown.y) > DRAG_SLOP)) {
          dropTracking = true;
          motionTracking = true;
          okToDrop = false;
          dropAction = DnDConstants.ACTION_MOVE;
          dragImage = getDragImage();
          setCursor(DragSource.DefaultMoveNoDrop);

          if (dragImage != null) {
            imageRect = new Rectangle(dragImage.getWidth(null), dragImage.getHeight(null));
          }
        }
      }

      if (motionTracking) {
        okToDrop = updateDropTracking(e.getPoint(), true);

        if (dropAction == DnDConstants.ACTION_COPY) {
          setCursor(okToDrop ? DragSource.DefaultCopyDrop : DragSource.DefaultCopyNoDrop);
        } else {
          setCursor(okToDrop ? DragSource.DefaultMoveDrop : DragSource.DefaultMoveNoDrop);
        }
      }
    }

    public void mouseEntered(MouseEvent e) {
      dropTracking = wasDropTracking;
      wasDropTracking = false;
    }

    boolean wasDropTracking;

    public void mouseExited(MouseEvent e) {
      wasDropTracking = dropTracking;
      dropTracking = false;
      if (wasDropTracking) {
        requestRepaint();
        setCursor(Cursor.getDefaultCursor());
      }
    }

    public void mouseMoved(MouseEvent e) {
      if (motionTracking) {
        updateDropTracking(e.getPoint(), false);
      }
    }

    public void mousePressed(MouseEvent e) {
      mouseDown = e.getPoint();
      mouseScreenDown = e.getLocationOnScreen();
      dropTrackingCanceled = false;
      mouseDownTime = System.currentTimeMillis();

      if (!isFocusOwner()) {
        requestFocusInWindow();
      }

      if (!motionTracking) {
        if (rootWidget != null && !SwingUtilities.isRightMouseButton(e)) {
          Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), rootWidget.getContainerComponent().getView());
          selectWidgetAtPoint(p, e.getModifiers());
        }
      }

      if (e.isPopupTrigger()) {
        showPopupMenu(e);
      }
    }

    public void mouseReleased(MouseEvent e) {
      wasDropTracking = false;
      if (dropTracking) {
        try {
          setCursor(Cursor.getDefaultCursor());

          if (okToDrop) {
            Object o = getSelection();

            if (o != null) {
              drop(dropAction, e.getPoint(), o, true);
            }
          }
        } finally {
          dropTracking = false;
          motionTracking = false;
        }
      }

      if (e.isPopupTrigger()) {
        showPopupMenu(e);
      } else {
        if (PlatformHelper.isMouseClick(mouseScreenDown, mouseDownTime, e)) {
          handleSingleMouseClick(e.getPoint());
        }
      }
    }
  }

  class SelectionArea {
    final Point     rowColumn      = new Point();
    final Point     selectionPoint = new Point();
    final Rectangle srcBounds      = new Rectangle();
    Object          constraints;
    iContainer      gridWidget;
    iSPOTElement    gridWidgetConfig;
    iWidget         selectionWidget;
    iSPOTElement    selectionWidgetConfig;
    private boolean cellSelected   = true;
    private boolean tracking;
    private boolean valid;

    public SelectionArea copy() {
      SelectionArea a = new SelectionArea();

      a.set(this);

      return a;
    }

    public void paintBorder(Component c, Graphics g, boolean pane) {
      if (isValid()) {
        if (!tracking && (selectedWidget != null) && (selectedWidget != rootWidget)) {
          paintBorderEx(c, g, selectionBorder, pane, true);
        } else {
          paintBorderEx(c, g, cellBorder, pane, false);
        }
      }
    }

    public void paintBorderEx(Component c, Graphics g, Border border, boolean pane, boolean offset) {
      Rectangle r = getComponentLocation();
      if (r != null) {
        if (pane) {
          r.x -= 3;
          r.y -= 3;
        }

        int d = offset ? 2 : 0;

        border.paintBorder(c, g, r.x - d, r.y - d, r.width + (d * 2), r.height + (d * 2));
      }
    }

    public void reset() {
      setValid(false);
      constraints = null;
      selectionWidget = null;
      cellSelected = true;
      gridWidget = null;
    }

    public void resetForReload() {
      if (gridWidget != null) {
        gridWidgetConfig = (iSPOTElement) gridWidget.getLinkedData();
      }

      gridWidget = null;

      if (selectionWidget != null) {
        selectionWidgetConfig = (iSPOTElement) selectionWidget.getLinkedData();
      }

      selectionWidget = null;
    }

    public boolean revalidate() {
      boolean ok = false;
      boolean sel = cellSelected;
      Point p = new Point(selectionPoint);
      do {
        if (selectionWidgetConfig != null) {
          iWidget w = findByElementEquals(rootWidget, selectionWidgetConfig);

          selectionWidgetConfig = null;
          selectedWidget = w;

          if ((w != null) && FormsUtils.getSelectionAreaFromWidget(DesignPane.this, w, this, this.selectionPoint)) {
            ok = true;
          }

          break;
        }

        if (gridWidgetConfig != null) {
          iWidget w = findByElementEquals(rootWidget, gridWidgetConfig);

          gridWidgetConfig = null;
          if (w instanceof iContainer) {
            gridWidget = (iContainer) w;
          } else {
            gridWidget = null;
            w = null;
          }

          if (w != null) {
            if (FormsUtils.getSelectionAreaFromCell(DesignPane.this, gridWidget, this, rowColumn.y, rowColumn.x)) {
              ok = true;
            }
          }

          break;
        } else {
          gridWidget = null;
        }
      } while (false);
      if (ok) {
        cellSelected = sel;
        if (srcBounds.contains(p)) {
          selectionPoint.setLocation(p);
        }
      }
      return ok;
    }

    public void setValid(boolean valid) {
      this.valid = valid;
    }

    public Rectangle getComponentLocation() {
      Rectangle r = new Rectangle();
      Component gc = FormsUtils.getPaintBounds(DesignPane.this, this, r);
      if (gc == null) {
        valid = false;
        return null;
      }
      Component p = gc;

      while (p != scrollPane) {
        r.x += p.getX();
        r.y += p.getY();
        p = p.getParent();
      }

      return r;
    }

    public boolean isValid() {
      return valid;
    }

    void set(SelectionArea area) {
      this.valid = area.valid;
      this.selectionWidget = area.selectionWidget;
      this.gridWidget = area.gridWidget;
      this.constraints = area.constraints;
      this.tracking = area.tracking;
      this.cellSelected = area.cellSelected;
      this.selectionPoint.setLocation(area.selectionPoint);
      this.rowColumn.setLocation(area.rowColumn);
    }

    private void repaint() {
      Rectangle r = getComponentLocation();
      int d = 2;

      scrollPane.repaint(r.x - d, r.y - d, r.width + (d * 2), r.height + (d * 2));
    }
  }

  class SelectionPainter extends UIComponentPainter {
    @Override
    public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation, boolean end) {
      super.paint(g, x, y, width, height, orientation, end);

      if ((end == true) && (selections.size() > 0)) {
        Graphics2D g2 = g.getGraphics();
        JViewport vp = (scrollPane == null) ? null : scrollPane.getViewport();

        if (vp == null) {
          return;
        }

        if ((valueEditor == null) || !valueEditor.isEditing()) {
          Shape clip = g2.getClip();

          g.clipRect(0, 0, vp.getWidth() + 6, vp.getHeight() + 6);
          // Point p = vp.getViewPosition();
          // translate to pane space
          // g2.translate(-p.x + 3, -p.y + 3);
          paintSelections(g2);
          // g2.translate(p.x - 3, p.y - 3);
          g2.setClip(clip);
        }
      }
    }
  }

  static class WidgetList extends ArrayList<Widget> {
    public WidgetList() {
      super();
    }

    public WidgetList(int len) {
      super(len);
    }

    public String toString() {
      return Helper.toString(this, "\n");
    }
  }
}
