/*
 * @(#)FormsUtils.java   2009-12-31
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.undo.UndoableEdit;

import com.appnativa.jgoodies.forms.layout.CellConstraints;
import com.appnativa.jgoodies.forms.layout.FormLayout;
import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.BorderLayoutView;
import com.appnativa.rare.platform.swing.ui.view.FormsView;
import com.appnativa.rare.platform.swing.ui.view.GlassPanel;
import com.appnativa.rare.platform.swing.ui.view.UtilityPanel;
import com.appnativa.rare.spot.Region;
import com.appnativa.rare.spot.ToolBar;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.FormsPanel;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIMenu;
import com.appnativa.rare.ui.UIMenuItem;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UISoundHelper;
import com.appnativa.rare.ui.aAbsolutePanel;
import com.appnativa.rare.ui.aFormsPanel;
import com.appnativa.rare.ui.iFormsPanel;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.viewer.GridPaneViewer;
import com.appnativa.rare.viewer.SplitPaneViewer;
import com.appnativa.rare.viewer.StackPaneViewer;
import com.appnativa.rare.viewer.ToolBarViewer;
import com.appnativa.rare.viewer.WidgetPaneViewer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aGroupBoxViewer;
import com.appnativa.rare.viewer.aRegionViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iContainer.Layout;
import com.appnativa.rare.viewer.iFormViewer;
import com.appnativa.rare.viewer.iTabPaneViewer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.studio.DesignPane.SelectionArea;
import com.appnativa.util.Helper;
import com.appnativa.util.IdentityArrayList;
import com.appnativa.util.SNumber;
import com.appnativa.util.StringCache;

/**
 *
 * @author decoteaud
 */
public class FormsUtils {

  /**
   * Finds the lowest level target in the specified component's hierarchy
   *
   * @param c the component
   *
   * @return the lowest level target in the specified component's hierarchy
   */
  public static iTarget findTargetForComponent(Component c) {
    iTarget            t  = null;
    iPlatformComponent pc = Platform.getPlatformComponent(c);

    while((t == null) && (pc != null)) {
      t = (iTarget) pc.getClientProperty(iConstants.RARE_TARGET_COMPONENT_PROPERTY);

      if (t == null) {
        pc = pc.getParent();
      }
    }

    return t;
  }

  public static void handleDrop(DesignPane pane, Widget cfg) {
    SelectionArea area = pane.getSelectionArea();
    Point         p    = area.selectionPoint;

    handleDrop(pane, area, p, cfg, false);
  }

  public static void handleDrop(DesignPane pane, Point p, Widget cfg) {
    handleDrop(pane, pane.getTrackingArea(), p, cfg, true);
  }

  public static void handleDrop(DesignPane pane, SelectionArea area, Point p, Widget cfg, boolean tracking) {
    if (area.gridWidget == null ) {
      UISoundHelper.errorSound();
      pane.repaint();

      return;
    }

    if ((area.selectionWidget != null) && (area.selectionWidget != area.gridWidget)) {
      DnDPopupMenu popup = new DnDPopupMenu(pane, area.gridWidget, cfg, tracking);

      popup.show(pane, p.x, p.y);
    } else {
      handleDropEx(pane, cfg, new DnDPopupMenu(), tracking);
    }
  }

  public static void handleDropEx(final DesignPane pane, Widget cfg, DnDPopupMenu popup, boolean tracking) {
    UndoableEdit  e;
    SelectionArea tarea     = tracking
                              ? pane.getTrackingArea()
                              : pane.getSelectionArea();
    iWidget       droppedOn = tarea.selectionWidget;
    iContainer    parent    = tarea.gridWidget;
    iWidget       dropping  = pane.getSelectedWidget();

    if ((dropping != null) && (cfg != null) && (cfg != dropping.getLinkedData())) {
      dropping = null;
    }

    if (droppedOn == parent) {
      droppedOn = null;
    }

    iContainer  pDroppedOn;
    Constraints cDroppedOn = getConstraints(parent, tarea);

    if (droppedOn != null) {
      pDroppedOn = droppedOn.getParent();
    } else {
      pDroppedOn = tarea.gridWidget;
    }

    if (cfg != null) {
      if (popup.isReplace() && (droppedOn != null)) {
        e = FormChanger.replaceWidget(pane, pDroppedOn, droppedOn, null, cfg, true);
      } else {
        String name = cfg.name.getValue();

        if ((name != null) && (name.length() > 0) && (pDroppedOn.getFormViewer().getWidget(name) != null)) {
          cfg.name.setValue(makeNewName(pDroppedOn.getFormViewer(), name));
        }

        e = FormChanger.addWidget(pane, (SPOTSequence) pDroppedOn.getLinkedData(), cfg, cDroppedOn.location, true);
      }
    } else {
      cfg = (Widget) dropping.getLinkedData();

      if (droppedOn != null) {
        if (popup.isSwap()) {
          SPOTSequence p1 = (SPOTSequence) dropping.getParent().getLinkedData();
          SPOTSequence p2 = (SPOTSequence) pDroppedOn.getLinkedData();

          e = FormChanger.swapWidgets(pane, p1, p2, cfg, (Widget) droppedOn.getLinkedData(), true);
        } else if (popup.isInsert()) {
          e = FormChanger.addWidget(pane, (SPOTSequence) pDroppedOn.getLinkedData(), cfg, cDroppedOn.location, true);
        } else if (popup.isAppend()) {
          cDroppedOn.location.position=-1;
          e = FormChanger.addWidget(pane, (SPOTSequence) pDroppedOn.getLinkedData(), cfg, cDroppedOn.location, true);
        } else {
          e = FormChanger.replaceWidget(pane, pDroppedOn, droppedOn, dropping, (Widget) dropping.getLinkedData(), true);
        }
      } else {
        e = FormChanger.moveWidget(pane, pDroppedOn, dropping, cDroppedOn, true);
      }
    }

    tarea.setValid(false);

    if (e == null) {
      UISoundHelper.errorSound();

      return;
    }
    
    pane.addUndoableEdit(e, true, cfg);
  }

  public static FormLayout getFormLayout(JComponent panel) {
    if (panel instanceof BorderLayoutView) {
      return null;
    }

    if (panel instanceof FormsView) {
      return ((FormsView) panel).getFormLayout();
    }

    return null;
  }

  public static Point getGridSize(iContainer parent) {
    switch(parent.getWidgetType()) {
      case SplitPane :
        int len = ((SplitPaneViewer) parent).getRegionCount();

        if (((SplitPaneViewer) parent).isTopToBottom()) {
          return new Point(0, len);
        }

        return new Point(len, 0);

      case GroupBox :
      case Form :
        Layout lm = ((aGroupBoxViewer) parent).getFormLayout();

        if ((lm == Layout.FORMS) || (lm == Layout.TABLE)) {
          FormLayout fl = ((aFormsPanel) parent.getDataComponent()).getFormLayout();
          Point      p  = new Point(fl.getColumnCount(), fl.getRowCount());

          if (lm == Layout.TABLE) {
            p.x = p.x / 2 + 1;
            p.y = p.y / 2 + 1;
          }

          return p;
        }

        return new Point(1, 1);
        
      case GridPane :
        GridPaneViewer g = (GridPaneViewer) parent;

        return new Point(g.getColumnCount(), g.getRowCount());

      case TabPane :
        return new Point(1, 1);

      default :
        break;
    }

    return null;
  }

  public static boolean getSelectionAreaFromCell(DesignPane pane, iContainer parent, SelectionArea area, int row,
          int col) {
    iPlatformComponent panel = parent.getDataComponent();

    area.reset();

    if ((row < 0) || (col < 0)) {
      return false;
    }

    switch(parent.getWidgetType()) {
      case Form :
      case GroupBox :
      case GridPane :
        Layout lm = Layout.FORMS;

        if (parent instanceof aGroupBoxViewer) {
          lm = ((aGroupBoxViewer) parent).getFormLayout();
        }

        if ((lm == Layout.FORMS) || (lm == Layout.TABLE)) {
          FormLayout fl    = (FormLayout) ((iFormsPanel) panel).getFormLayout();
          boolean    table = lm == Layout.TABLE;

          if (table) {
            row *= 2;
            col *= 2;
          }

          if ((col >= fl.getColumnCount()) || (row >= fl.getRowCount())) {
            break;
          }

          area.setValid(true);

          CellConstraints cc = new CellConstraints(col + 1, row + 1);

          area.constraints = cc;
          setRectFromCellConstraints(panel.getView(), fl, cc, area.srcBounds, false);
          area.selectionPoint.setLocation(area.getComponentLocation().getLocation());

          if (table) {
            area.rowColumn.setLocation(cc.gridX / 2, cc.gridY / 2);
          } else {
            area.rowColumn.setLocation(cc.gridX - 1, cc.gridY - 1);
          }

          area.selectionWidget = Platform.getWidgetForComponent(fl.getComponentAt((iParentComponent) panel, cc.gridX,
                  cc.gridY));
          area.gridWidget = parent;
        }

        break;

      case SplitPane :
        int pos = ((SplitPaneViewer) parent).isTopToBottom()
                  ? row
                  : col;

        if (pos < ((SplitPaneViewer) parent).getRegionCount()) {
          area.constraints = pos;

          iTarget   t = ((aRegionViewer) parent).getRegion(pos);
          iWidget   w = t.getViewer();
          Component c = (w == null)
                        ? t.getContainerComponent().getView()
                        : w.getContainerComponent().getView();
          Point     p = c.getLocation();

          setArea(pane, area, parent, w, c, p);
          setRowColumn(parent, t, area);
          area.gridWidget = parent;
        }

        break;

      case StackPane :
        area.constraints = null;
        area.gridWidget  = parent;

        break;

      default :
        break;
    }

    return area.isValid();
  }

  @SuppressWarnings("fallthrough")
  public static void getSelectionAreaFromPoint(DesignPane pane, Point p, SelectionArea area) {
    area.reset();

    iWidget w = getWidgetAtPoint(pane, p);

    if (w == null || w.getLinkedData()==null) {
      w = pane.getRootWidget();
    }

    iContainer parent = w.getContainerViewer();
    Component  c      = w.getContainerComponent().getView();

    if ((parent != null) && pane.isLocked(parent)) {
      w      = parent;
      parent = w.getParent();
      c      = w.getContainerComponent().getView();
    }

    if (parent == null) {
      return;
    }

    if (!c.isShowing()) {
      Utilities.expandAllCollapsible(w);
      SwingHelper.makeComponentVisibleToUser(c);
    }

    iPlatformComponent panel = parent.getDataComponent();

    switch(parent.getWidgetType()) {
      case Form :
      case GroupBox :
        Layout lm = ((aGroupBoxViewer) parent).getFormLayout();

        if ((lm == Layout.FORMS) || (lm == Layout.TABLE)) {
          getSelectionAreaFromPointEx(pane, parent, w, panel.getView(), p, area);

          break;
        }
      // fallthrough
      default :
        getSelectionAreaFromWidget(pane, w, area, p);

        break;
    }
  }

  public static boolean getSelectionAreaFromWidget(DesignPane pane, iWidget w, SelectionArea area, Point p) {
    return getSelectionAreaFromWidgetEx(pane, w, area, p, false);
  }

  public static boolean getSelectionAreaFromWidgetEx(DesignPane pane, iWidget w, SelectionArea area, Point p,
          boolean goUp) {
    boolean isRoot      = w == pane.getRootWidget();
    boolean isContainer = w instanceof iContainer;
    boolean valid       = true;

    switch(w.getWidgetType()) {
      case Table :
        isContainer = false;

        break;

      default :
        break;
    }
    if (goUp) {
      isContainer = false;
    }

    iContainer parent = (isRoot || isContainer)
                        ? w.getContainerViewer()
                        : w.getParent();

    if (parent == null) {
      return false;
    }

    if ((parent instanceof WindowViewer) && isContainer) {
      parent = w.getContainerViewer();
    }

    JComponent         panel = parent.getDataComponent().getView();
    iPlatformComponent c     = w.getContainerComponent();

    area.reset();

    switch(parent.getWidgetType()) {
      case Window:
        c=((WindowViewer)parent).getTarget().getContainerComponent();
        break;
      case Form :
      case GroupBox :
        Layout lm = ((aGroupBoxViewer) parent).getFormLayout();

        if ((lm == Layout.FORMS) || (lm == Layout.TABLE)) {
          CellConstraints cc;

          if (w == parent) {
            cc = new CellConstraints(1, 1);
          } else {
            cc = getCellConstraints(panel, c);
          }

          if (cc == null) {
            break;
          }

          area.setValid(true);
          area.constraints = cc;
          setRectFromCellConstraints(panel, ((FormsView) panel).getFormLayout(), cc, area.srcBounds, false);
          area.selectionPoint.setLocation((p == null)
                                          ? area.getComponentLocation().getLocation()
                                          : p);
          area.rowColumn.setLocation(cc.gridX - 1, cc.gridY - 1);
          area.selectionWidget = w;
          area.gridWidget      = parent;
        }
        else {
          area.selectionPoint.setLocation((p == null)
              ? area.getComponentLocation().getLocation()
              : p);
          
        }

        break;

      case SplitPane :
      case GridPane :
        iTarget t = null;

        if (isContainer) {
          if (p != null) {
            Point     pp = SwingUtilities.convertPoint(pane, p, panel);
            Component tc = panel.getComponentAt(pp);

            t = findTargetForComponent(tc);

            if (t != null) {

              area.constraints = ((aRegionViewer) parent).getRegionIndex(t);
              if((Integer)area.constraints==-1) {
                t=null;
              }
              else {
                c                = getComponent(tc);
              }
            }
          }
        }

        if (t == null) {
          int n = ((aRegionViewer) parent).getRegionIndex(w);

          area.constraints = n;

          if (n != -1) {
            t = ((aRegionViewer) parent).getRegion(n);
          }
        }

        if (t != null) {
          setRowColumn(parent, t, area);
          w = t.getViewer();
        }

        break;

      case WidgetPane :
      case CollapsiblePane :
        break;

      case TabPane :
        area.constraints = 0;

        break;

      case ToolBar :
        SPOTSet set = ((ToolBar) ((ToolBarViewer) parent).getLinkedData()).widgets;

        area.constraints = set.indexOfEx((Widget) w.getLinkedData());

        break;

      default :
        break;
    }

    if (!area.isValid() && (c != null)) {
      if (!valid) {
        w = null;
      }

      setArea(pane, area, parent, w, c.getView(), p);
    }
//
//    if (area.isValid() && isContainer && isRoot) {
//      area.selectionWidget = null;
//    }

    return area.isValid();
  }

  public static iWidget getWidgetAtPoint(DesignPane pane, Point p) {
    iWidget w = pane.getRootWidget();

    if (w == null) {
      return null;
    }

    Component c = w.getContainerComponent().getView();

    c = getDeepestComponentAt(c, p.x, p.y, pane);
    w = Platform.findWidgetForComponent(c);

    if ((w != null) && (w == pane.getRootWidget().getParent())) {
      w = null;
    }

    return w;
  }

  public static iWidget getWidgetAtPointEx(DesignPane pane, Point p) {
    Component c = pane.getParent();

    c = getDeepestComponentAtEx(c, p.x, p.y, pane);

    iWidget w = Platform.findWidgetForComponent(c);

    if ((w != null) && (w == pane.getRootWidget().getParent())) {
      w = null;
    }

    return w;
  }

  static void addWidgetMenu(final DesignPane pane, UIMenu menu, Point p, iContainer parent) {
    if (parent == null) {
      return;
    }
    iWidget w=getWidgetAtPointEx(pane, p);
    if(w==null || w==p) {
      w = pane.getSelectedWidget();
    }
    iViewer v = (w == null)
                ? null
                : w.getViewer();

    if (v == parent) {
      switch(parent.getWidgetType()) {
        case TabPane :
          v = ((iTabPaneViewer) parent).getSelectedTabViewer();

          break;

        case SplitPane :
        case GridPane :
          v = findLockedRegionViewer((aRegionViewer) parent, pane, p);

          break;

        case CollapsiblePane :
        case WidgetPane :
          w = ((WidgetPaneViewer) parent).getWidget();
          v = (w instanceof iViewer)
              ? (iViewer) v
              : null;

          break;

        case StackPane :
          v = ((StackPaneViewer) parent).getActiveViewer();

          break;

        default :
          break;
      }
    }

    menu.addSeparator(0);

    if ((v != null) && (v.getViewerActionLink() != null) &&!v.getViewerActionLink().isInlineURL()) {
      try {
        URL u=v.getViewerActionLink().getURL(parent);
        if(u!=null) {
          menu.add(0, openLinkedViewerMenuItem(pane, u));
        }
      } catch(MalformedURLException ex) {}
    }

    if (w != null) {
      String s = Studio.getResourceAsString("Studio.text.design.edit");

      s = Helper.expandString(s, w.getWidgetType().toString());

      UIMenuItem m = UIMenuItem.createMenuItem(s);

      m.setIcon(Platform.getResourceAsIcon("Rare.icon.empty"));
      m.setActionListener(new iActionListener() {
        @Override
        public void actionPerformed(com.appnativa.rare.ui.event.ActionEvent e) {
          pane.fireWidgetActionEvent();
        }
      });
      m.setEnabled(false);
      menu.add(0, m);

      switch(w.getWidgetType()) {
        case TabPane :
        case SplitPane :
        case Form :
        case TextField :
        case PushButton :
        case RadioButton :
        case CheckBox :
        case Label :
        case Spinner :
        case Table :
          m.setEnabled(true);

          break;

        case Bean :
          SPOTSequence wc = (Widget) w.getLinkedData();

          if (wc.spot_getLinkedData() instanceof SPOTSequence) {
            m.setEnabled(true);
          }

          break;

        default :
          break;
      }
    }
  }

  static void adjustForBorder(Rectangle r, iPlatformComponent c) {
    iPlatformBorder b = c.getBorder();

    if (b != null) {
      UIInsets in = b.getBorderInsets((UIInsets) null);

      r.x      += in.left;
      r.y      += in.top;
      r.width  -= (in.left + in.right);
      r.height -= (in.top + in.bottom);
    }
  }

  static void fixGridLocation(iContainer parent, Point rc) {
    iPlatformComponent panel = parent.getDataComponent();
    int                row   = rc.y;
    int                col   = rc.x;

    switch(parent.getWidgetType()) {
      case Form :
      case GroupBox :
      case GridPane :
        Layout lm = Layout.FORMS;

        if (parent instanceof aGroupBoxViewer) {
          lm = ((aGroupBoxViewer) parent).getFormLayout();
        }

        if ((lm == Layout.FORMS) || (lm == Layout.TABLE)) {
          FormLayout fl    = (FormLayout) ((iFormsPanel) panel).getFormLayout();
          boolean    table = lm == Layout.TABLE;

          if ((row < 0) || (col < 0)) {
            col = (col < 0)
                  ? 0
                  : col;
            row = (row < 0)
                  ? 0
                  : row;

            break;
          }

          if (table) {
            row *= 2;
            col *= 2;
          }

          if (col >= fl.getColumnCount()) {
            col = fl.getColumnCount() - 1;
          }

          if (row >= fl.getRowCount()) {
            row = fl.getRowCount() - 1;
          }
        }

        break;

      default :
        break;
    }

    rc.x = col;
    rc.y = row;
  }

  static UIMenuItem openLinkedViewerMenuItem(final DesignPane pane, final URL url) {
    String file = url.getPath();
    int    n    = file.lastIndexOf('/');

    if (n == -1) {
      n=file.lastIndexOf('\\');
    }

    if (n != -1) {
      file = file.substring(n + 1);
    }

    String text = Studio.getResourceAsString("Studio.text.openLink");

    text = Helper.expandString(text, file);

    UIMenuItem mi = new UIMenuItem(text);

    mi.setActionListener(new iActionListener() {
      @Override
      public void actionPerformed(com.appnativa.rare.ui.event.ActionEvent e) {
        pane.sendEvent(EventType.OPEN_DOCUMENT, url);
      }
    });

    return mi;
  }

  static boolean paintGridLines(Graphics2D g, JComponent panel, Stroke stroke, Color lineColor) {
    FormLayout fl = getFormLayout(panel);

    if (fl == null) {
      return false;
    }

    FormLayout.LayoutInfo info = fl.getLayoutInfo((iParentComponent) getComponent(panel));
    int[]                 cols = info.columnOrigins;
    int[]                 rows = info.rowOrigins;
    int                   x    = info.getX();
    int                   y    = info.getY();
    int                   w    = info.getWidth();
    int                   h    = info.getHeight();
    Color                 oc   = g.getColor();
    Stroke                os   = g.getStroke();

    g.setColor(lineColor);
    g.setStroke(stroke);

    for (int col = 0; col < cols.length; col++) {
      g.drawLine(cols[col], y, cols[col], y + h);
    }

    // Paint the row bounds.
    for (int row = 0; row < rows.length; row++) {
      g.drawLine(x, rows[row], x + w, rows[row]);
    }

    if (x == 0) {
      g.drawLine(0, y + h - 1, w - 1, y + h - 1);
    }

    if (y == 0) {
      g.drawLine(x + w - 1, 0, x + w - 1, h - 1);
    }

    g.setColor(oc);
    g.setStroke(os);

    return true;
  }

  static void setArea(DesignPane pane, SelectionArea area, iContainer parent, iWidget w, Component c, Point p) {
    area.setValid(true);
    c.getBounds(area.srcBounds);
    //area.srcBounds.setLocation(0, 0);
    area.selectionPoint.setLocation((p == null)
                                    ? new Point()
                                    : p);
    area.selectionWidget = w;
    area.gridWidget      = parent;
  }

  static void setRectFromCellConstraints(JComponent panel, FormLayout fl, CellConstraints cc, Rectangle bounds,
          boolean singleCell) {
    FormLayout.LayoutInfo info       = fl.getLayoutInfo((iParentComponent) getComponent(panel));
    int[]                 cols       = info.columnOrigins;
    int[]                 rows       = info.rowOrigins;
    int                   gridX      = cc.gridX - 1;
    int                   gridY      = cc.gridY - 1;
    int                   gridWidth  = cc.gridWidth;
    int                   gridHeight = cc.gridHeight;

    if (singleCell) {
      gridHeight = gridWidth = 1;
    }

    bounds.x      = cols[gridX];
    bounds.y      = rows[gridY];
    bounds.width  = cols[gridX + gridWidth] - bounds.x;
    bounds.height = rows[gridY + gridHeight] - bounds.y;
  }

  static List<iWidget> getAllWidgets(iContainer parent, List<iWidget> widgets) {
    if (widgets == null) {
      widgets = new IdentityArrayList<iWidget>();
    }

    List<iWidget> list = parent.getWidgetList();

    if (list != null) {
      widgets.addAll(list);

      for (iWidget w : list) {
        if ((w instanceof iContainer) && (w.getWidgetCount() > 0)) {
          getAllWidgets((iContainer) w, widgets);
        } else {
          widgets.add(w);
        }
      }
    }

    return widgets;
  }

  static Component getPaintBounds(DesignPane pane, SelectionArea a, Rectangle r) {
    iContainer parent = a.gridWidget;

    if (parent == null) {
      parent = pane.getRootWidget();
    }

    iPlatformComponent panel = parent.getDataComponent();
    if(panel==null) {
      r.setBounds(0, 0, 0, 0);
      return null;
    }
    Component          gc    = panel.getView();

    switch(parent.getWidgetType()) {
      case Form :
      case GroupBox :
        Layout lm = Layout.FORMS;

        if (parent instanceof aGroupBoxViewer) {
          lm = ((aGroupBoxViewer) parent).getFormLayout();
        }
        
        Object cc = a.constraints;
        if(a.selectionWidget==a.gridWidget) {
          cc=null;
          lm = Layout.CUSTOM;
        }
        switch(lm) {
          case FORMS :
          case TABLE :
            if (cc instanceof CellConstraints) {
              FormLayout fl = (FormLayout) ((iFormsPanel) panel).getFormLayout();

              setRectFromCellConstraints(panel.getView(), fl, (CellConstraints) cc, r, false);
            } else {
              iWidget w = a.selectionWidget;

              if (w == null) {
                w = a.gridWidget;
              }

              if (w == null) {
                gc = pane.getRootWidget().getContainerComponent().getView();
                gc.getBounds(r);
              } else {
                SelectionArea area = a.copy();

                if (getSelectionAreaFromWidgetEx(pane, w, area, null, true)) {
                  gc = getPaintBounds(pane, area, r);
                } else {
                  gc = w.getContainerComponent().getView();
                  gc.getBounds(r);
                }
              }
            }

            break;

          default :
            if (cc instanceof UIRectangle) {
              UIRectangle rr = (UIRectangle) cc;

              r.setBounds((int)rr.x, (int)rr.y, (int)rr.width, (int)rr.height);
            } else if (a.selectionWidget != null) {
              a.selectionWidget.getContainerComponent().getView().getBounds(r);
              if(a.selectionWidget==a.gridWidget) {
                r.setLocation(0, 0);
              }
            } else {
              panel.getView().getBounds(r);
              r.setLocation(0, 0);
            }

            break;
        }

        break;

      case SplitPane :
      case GridPane :
        iTarget t = null;

        if (a.constraints instanceof Integer) {
          t = ((aRegionViewer) parent).getRegion((Integer) a.constraints);
        }

        if (t == null) {
          if (a.selectionWidget != null) {
            int n = ((aRegionViewer) parent).getRegionIndex(a.selectionWidget);

            if (n != -1) {
              t = ((aRegionViewer) parent).getRegion(n);
            }
          }
        }

        if (t == null) {
          gc = panel.getView();
          gc.getBounds(r);
          gc = gc.getParent();
        } else {
          gc = t.getContainerComponent().getView();
          gc.getBounds(r);
          gc = gc.getParent();
        }

        break;

      case StackPane :
      case WidgetPane :
        gc = panel.getView();
        gc.getBounds(r);
        gc = gc.getParent();
        adjustForBorder(r, panel);

        break;

      case CollapsiblePane :
        gc = ((SplitPaneViewer) parent).getTarget().getContainerComponent().getView();
        gc.getBounds(r);
        gc = gc.getParent();

        break;

      case Window :
        gc = ((WindowViewer) parent).getTarget().getContainerComponent().getView();
        gc.getBounds(r);
        gc = gc.getParent();

        break;

      default :
        gc = a.selectionWidget==null ? panel.getView() : a.selectionWidget.getContainerComponent().getView();
        gc.getBounds(r);
        gc = gc.getParent();

        break;
    }

    return gc;
  }

  static Rectangle getRectangle(iPlatformComponent panel, int col, int row) {
    if ((col < 0) || (row < 0)) {
      return null;
    }

    if (panel instanceof aFormsPanel) {
      aFormsPanel fp = (aFormsPanel) panel;

      if ((row >= fp.getRowCount()) || (col >= fp.getColumnCount())) {
        return null;
      }

      CellConstraints cc = new CellConstraints(col + 1, row + 1);
      Rectangle       r  = new Rectangle();

      FormsUtils.setRectFromCellConstraints(fp.getView(), fp.getFormLayout(), cc, r, true);

      return r;
    }

    return null;
  }

  static List<iWidget> getWidgets(iContainer parent, Rectangle r) {
    List<iWidget> list  = parent.getWidgetList();
    JComponent    panel = parent.getDataComponent().getView();
    int           len   = list.size();
    int           x     = 0;
    int           y     = 0;

    if (len > 0) {
      list = new ArrayList<iWidget>(list);
    }

    for (int i = len - 1; i >= 0; i--) {
      iWidget            w  = list.get(i);;
      iPlatformComponent c  = w.getContainerComponent();
      CellConstraints    cc = getCellConstraints(panel, c);

      if (cc == null) {
        continue;
      }

      x = cc.gridX - 1;
      y = cc.gridY - 1;

      if (!r.contains(x, y)) {
        list.remove(i);
      }
    }

    return list;
  }

  private static iViewer findLockedRegionViewer(aRegionViewer parent, DesignPane pane, Point p) {
    iWidget w = getWidgetAtPointEx(pane, p);

    while((w != null) && (w != parent)) {
      if (w instanceof iViewer) {
        int n = parent.getRegionIndex(w);

        if (n != -1) {
          return parent.getRegion(n).getViewer();
        }
      }

      w = w.getParent();
    }

    return null;
  }

  private static iWidget findWidgetInCell(JComponent panel, int row, int col, boolean checkSpan) {
    iPlatformComponent c;
    CellConstraints    cc;
    int                len = panel.getComponentCount();

    row++;
    col++;

    for (int i = 0; i < len; i++) {
      c = getComponent(panel.getComponent(i));

      if (c == null) {
        continue;
      }

      cc = getCellConstraints(panel, c);

      if (cc == null) {
        continue;
      }

      if ((cc.gridX == col) && (cc.gridY == row)) {
        return Platform.findWidgetForComponent(c);
      }

      if (checkSpan) {
        int xend = cc.gridX + cc.gridWidth;
        int yend = cc.gridY + cc.gridHeight;

        if ((col >= cc.gridX) && (col < xend)) {
          if ((row >= cc.gridY) && (row < yend)) {
            return Platform.findWidgetForComponent(c);
          }
        }
      }
    }

    return null;
  }

  private static String makeNewName(iFormViewer fv, String name) {
    for (int i = 1; i < 1000; i++) {
      String s = name + i;

      if (fv.getWidget(s) == null) {
        return s;
      }
    }

    return name;
  }

  private static void setRowColumn(iContainer parent, iTarget t, SelectionArea area) {
    int row = 0;
    int col = 0;

    area.selectionWidget = t.getViewer();

    if (parent instanceof SplitPaneViewer) {
      SplitPaneViewer sp = (SplitPaneViewer) parent;

      if (sp.isTopToBottom()) {
        row = sp.getRegionIndex(t);
      } else {
        col = sp.getRegionIndex(t);
      }
    } else {
      Region r = (Region) t.getLinkedData();

      if (r != null) {
        row = r.getY();
        col = r.getX();
      }
    }

    if (row < 0) {
      row = 0;
    }

    if (col < 0) {
      col = 0;
    }

    area.rowColumn.setLocation(col, row);
  }

  private static CellConstraints getCellConstraints(JComponent panel, iPlatformComponent c) {
    if (panel instanceof FormsView) {
      return ((FormsView) panel).getCellConstraints(c);
    }

    if (panel instanceof BorderLayoutView) {
      return ((BorderLayoutView) panel).getCellConstraints(c);
    }

    return null;
  }

  private static iPlatformComponent getComponent(Component c) {
    if (c instanceof JComponent) {
      return (iPlatformComponent) ((JComponent) c).getClientProperty(
          com.appnativa.rare.ui.Component.RARE_COMPONENT_PROXY_PROPERTY);
    }

    return null;
  }

  private static Constraints getConstraints(iContainer parent, SelectionArea area) {
    Constraints c = new Constraints(area.constraints, -1);

    if (parent != null) {
      iPlatformComponent panel = parent.getDataComponent();

      if (!(c.constraints instanceof CellConstraints)) {
        if (panel instanceof aAbsolutePanel) {
          c.constraints = new UIRectangle(area.selectionPoint.x, area.selectionPoint.y, -1, -1);
        } else if (c.constraints instanceof Integer) {
          c.location.position = (Integer) c.constraints;
        } else if (area.selectionWidget != null) {
          c.setPositionFor(area.selectionWidget.getContainerComponent().getView());
        }
      } else {
        CellConstraints cc = (CellConstraints) c.constraints;
        int             x  = cc.gridX - 1;
        int             y  = cc.gridY - 1;

        if ((panel instanceof aFormsPanel) && ((aFormsPanel) panel).isTableLayout()) {
          x /= 2;
          y /= 2;
        }

        c.location.setLocation(x, y, -1);
      }
    } else {
      c.location.setLocation("-1", "-1", -1);
    }

    return c;
  }

  private static Component getDeepestComponentAt(Component parent, int x, int y, Component skip) {
    if ((parent == null) ||!parent.contains(x, y)) {
      return null;
    }

    if (parent instanceof Container) {
      Component components[] = ((Container) parent).getComponents();

      for (int i = 0; i < components.length; i++) {
        Component comp = components[i];

        if (comp == skip) {
          continue;
        }

        if (comp instanceof UtilityPanel) {
          if (((UtilityPanel) comp).isLocked()) {
            continue;
          }
        }

        if ((comp != null) && comp.isVisible()) {
          Point loc = comp.getLocation();

          if (comp instanceof Container) {
            comp = getDeepestComponentAt(comp, x - loc.x, y - loc.y, skip);
          } else {
            comp = comp.getComponentAt(x - loc.x, y - loc.y);
          }

          if ((comp != null) && comp.isVisible()) {
            return comp;
          }
        }
      }
    }

    return parent;
  }

  private static Component getDeepestComponentAtEx(Component parent, int x, int y, Component skip) {
    if ((parent == null) ||!parent.contains(x, y)) {
      return null;
    }

    if (parent instanceof Container) {
      Component components[] = ((Container) parent).getComponents();

      for (int i = 0; i < components.length; i++) {
        Component comp = components[i];

        if (comp == skip) {
          continue;
        }

        if ((comp != null) && comp.isVisible()) {
          Point loc = comp.getLocation();

          if (comp instanceof Container) {
            comp = getDeepestComponentAtEx(comp, x - loc.x, y - loc.y, skip);
          } else {
            comp = comp.getComponentAt(x - loc.x, y - loc.y);
          }

          if ((comp != null) && comp.isVisible()) {
            if(comp instanceof GlassPanel) {
              iTarget t=findTargetForComponent(comp);
              if(t!=null && t.getViewer()!=null) {
                comp=t.getViewer().getContainerComponent().getView();
              }
            }
            return comp;
          }
        }
      }
    }

    return parent;
  }

  private static void getSelectionAreaFromPointEx(DesignPane pane, iContainer parent, iWidget wp, JComponent panel,
          Point p, SelectionArea area) {
    FormLayout      fl     = getFormLayout(panel);
    CellConstraints cc     = null;
    FormsPanel      fpanel = (FormsPanel) Platform.getPlatformComponent(panel);
    boolean         table  = fpanel.isTableLayout();

    area.reset();
    area.selectionPoint.setLocation(p);
    p = SwingUtilities.convertPoint(pane, p, panel);

    iWidget w = wp;

    if ((wp != null) && (wp.getParent() != parent)) {
      wp = null;
    }

    if (wp != null) {
      cc = ((FormsView) panel).getCellConstraints(wp.getContainerComponent());
    }

    if ((cc == null) && ((wp == null) || (wp.getContainerComponent().getParent() == panel))) {
      cc = fpanel.getCellConstraints(new UIPoint(p.x, p.y));

      if (cc != null) {
        wp = findWidgetInCell(panel, cc.gridY - 1, cc.gridX - 1, true);

        if (wp != null) {
          CellConstraints cc1 = ((FormsView) panel).getCellConstraints(wp.getContainerComponent());

          if (cc1 != null) {
            cc = cc1;
          }
        } else {
          if (table) {
            if ((cc.gridX % 2 == 0) || (cc.gridY % 2 == 0)) {
              cc = null;
            }
          }
        }
      }
    }

    if (cc != null) {
      setRectFromCellConstraints(panel, fl, cc, area.srcBounds, false);

      if (table) {
        area.rowColumn.setLocation(cc.gridX / 2, cc.gridY / 2);
      } else {
        area.rowColumn.setLocation(cc.gridX - 1, cc.gridY - 1);
      }

      area.constraints = cc;
      area.setValid(true);
      area.selectionWidget = wp;
      area.gridWidget      = parent;

      if (area.selectionWidget == parent) {
        area.selectionWidget = null;
      }
    } else if (w != null) {
      area.setValid(true);

      Component c = w.getContainerComponent().getView();

      c.getBounds(area.srcBounds);
      //area.srcBounds.setLocation(0, 0);
      area.selectionPoint.setLocation((p == null)
                                      ? area.getComponentLocation().getLocation()
                                      : p);
      if(w==parent && w!=pane.getRootWidget()) {
        parent=w.getParent();
      }
      area.selectionWidget = w;
      area.gridWidget      = parent;
    }
  }

  static class Constraints {
    Location location = new Location();
    Object   constraints;

    public Constraints() {}

    public Constraints(Object constraints, int position) {
      this.constraints  = constraints;
      location.position = position;
    }

    public void setPositionFor(Component c) {
      location.position = -1;

      Container p   = (c == null)
                      ? null
                      : c.getParent();
      int       len = (p == null)
                      ? 0
                      : p.getComponentCount();

      for (int i = 0; i < len; i++) {
        if (p.getComponent(i) == c) {
          location.position = i;

          break;
        }
      }
    }
  }


  static class Location {
    int    position = -1;
    String x;
    String y;

    public Location() {}

    public Location(Point p) {
      this(p.x, p.y);
    }

    public Location(int x, int y) {
      setLocation(x, y, -1);
    }

    public Location(String x, String y) {
      setLocation(x, y, -1);
    }

    public Location(int x, int y, int position) {
      setLocation(x, y, position);
    }

    public Location(String x, String y, int position) {
      setLocation(x, y, position);
    }

    public void setLocation(Widget wc, int position) {
      x             = wc.bounds.x.getValue();
      y             = wc.bounds.y.getValue();
      this.position = position;
    }

    public void setLocation(int x, int y, int position) {
      this.x        = StringCache.valueOf(x);
      this.y        = StringCache.valueOf(y);
      this.position = position;
    }

    public void setLocation(String x, String y, int position) {
      this.x        = x;
      this.y        = y;
      this.position = position;
    }

    public void setX(int x) {
      this.x = StringCache.valueOf(x);
    }

    public void setY(int y) {
      this.y = StringCache.valueOf(y);
    }
    public boolean isValid() {
      if(x==null || x.length()==0 || x.equals("-1")) return false;
      if(y==null || y.length()==0 || y.equals("-1")) return false;
      return true;
    }
    public int getX() {
      return SNumber.intValue(x);
    }

    public int getY() {
      return SNumber.intValue(y);
    }
  }
}
