/*
 * @(#)FormChanger.java   2008-06-12
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.GridPane;
import com.appnativa.rare.spot.GroupBox;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.spot.MenuItem;
import com.appnativa.rare.spot.Region;
import com.appnativa.rare.spot.SplitPane;
import com.appnativa.rare.spot.StackPane;
import com.appnativa.rare.spot.StatusBar;
import com.appnativa.rare.spot.Tab;
import com.appnativa.rare.spot.TabPane;
import com.appnativa.rare.spot.ToolBar;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.spot.WidgetPane;
import com.appnativa.rare.ui.FormsPanel;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UISoundHelper;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iGradientPainter;
import com.appnativa.rare.ui.painter.iImagePainter;
import com.appnativa.rare.viewer.GridPaneViewer;
import com.appnativa.rare.viewer.GroupBoxViewer;
import com.appnativa.rare.viewer.aRegionViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.rare.widget.iWidget.WidgetType;
import com.appnativa.spot.SPOTAny;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.studio.DesignPane.DesignCompoundEdit;
import com.appnativa.studio.DesignPane.DesignEdit;
import com.appnativa.studio.DesignPane.DesignUndoableEdit;
import com.appnativa.studio.FormsUtils.Constraints;
import com.appnativa.studio.FormsUtils.Location;
import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;
import com.appnativa.util.MutableInteger;
import com.appnativa.util.SNumber;
import com.appnativa.util.StringCache;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 *
 * @author decoteaud
 */
public class FormChanger {
  static ColumnSpec     emptyColumnSpec = ColumnSpec.decode("14dlux");
  static RowSpec        emptyRowSpec    = RowSpec.decode("14dluy");
  static String         smallSpacer     = "2dlu";
  static String         spacer          = "4dlu";
  private static String NO_CHANGE       = "NO_CHANGE";

  public static enum Alignment {
    LEFT, RIGHT, TOP, BOTTOM, CENTER_HORIZONTAL, CENTER_VERTICAL, FULL_VERTICAL, FULL_HORIZONTAL, FULL, CENTER
  }

  public static void addColumn(DesignPane pane, iWidget parent, int col) {
    String             value = null;
    DesignCompoundEdit edits = null;

    switch(parent.getWidgetType()) {
      case Form :
      case GroupBox :
        GroupBox gb = (GroupBox) parent.getLinkedData();
        int      count;

        if (gb.layout.intValue() == GroupBox.CLayout.forms) {
          FormLayout fl = new FormLayout(gb.columns.getValue(), gb.rows.getValue());

          if ((col < 0) || (col >= fl.getColumnCount())) {
            fl.appendColumn(emptyColumnSpec);
          } else {
            fl.insertColumn((iParentComponent) parent.getDataComponent(), col + 1, emptyColumnSpec);
          }

          value = FormsPanel.getColumns(fl, -1);
          count = fl.getColumnCount();
        } else {
          count = ((FormsPanel) parent.getDataComponent()).getColumnCount() / 2;
          count++;
          value = StringCache.valueOf(count);
        }

        SPOTSet set = ((GroupBox) parent.getLinkedData()).widgets;

        edits = changeColumns(pane, set, col, 1, count);

        DesignUndoableEdit e = changeValue(pane, gb, "columns", value);

        if (e != null) {
          edits.addEdit(e);
        }

        break;

      case SplitPane : {
        SplitPane sp = (SplitPane) parent.getLinkedData();

        set = sp.regions;

        Region r;

        if ((col < 0) || (col >= set.size())) {
          set.add(r = new Region());
          col = set.size() - 1;
        } else {
          set.add(col, r = new Region());
        }

        edits = new DesignCompoundEdit(pane);

        if (sp.getSplitProportions() != null) {
          edits.addEdit(new SPOTReferenceDeleteUndoableEdit(pane, sp, "splitProportions", sp.getSplitProportions()));
          sp.setSplitProportions((iSPOTElement)null);
        }

        edits.addEdit(new SPOTSetUndoableEdit(pane, set, col, null, r));

        break;
      }

      case GridPane : {
        GridPane gp = (GridPane) parent.getLinkedData();

        set = gp.regions;

        int rows = ((GridPaneViewer) parent).getRowCount();
        int cols = ((GridPaneViewer) parent).getColumnCount() + 1;

        if ((col < 0) || (col >= cols)) {
          col = cols - 1;
        }

        edits = changeRegionColumns(pane, set, col, 1, cols);

        Region r;

        for (int i = 0; i < rows; i++) {
          r = new Region();
          r.x.setValue(col);
          r.y.setValue(i);
          set.add(r);
          edits.addEdit(new SPOTSetUndoableEdit(pane, set, col, null, r));
        }

        e = changeValue(pane, gp, "columns", StringCache.valueOf(cols));

        if (e != null) {
          edits.addEdit(e);
        }

        break;
      }

      default :
        break;
    }

    if ((edits != null) && edits.hasEdits()) {
      edits.setReloadRequired(true);
      pane.addUndoableEdit(edits, false, null);
      pane.requestReload(null);
    }
  }

  public static void addRow(DesignPane pane, iWidget parent, int row) {
    String             value = null;
    DesignCompoundEdit edits = null;
    DesignUndoableEdit e;
    SPOTSet            set;

    switch(parent.getWidgetType()) {
      case Form :
      case GroupBox :
        GroupBox gb = (GroupBox) parent.getLinkedData();
        int      count;

        if (gb.layout.intValue() == GroupBox.CLayout.forms) {
          FormLayout fl = new FormLayout(gb.columns.getValue(), gb.rows.getValue());

          if ((row < 0) || (row >= fl.getRowCount())) {
            fl.appendRow(emptyRowSpec);
          } else {
            fl.insertRow((iParentComponent) parent.getDataComponent(), row + 1, emptyRowSpec);
          }

          value = FormsPanel.getRows(fl, -1);
          count = fl.getRowCount();
        } else {
          count = ((FormsPanel) parent.getDataComponent()).getRowCount() / 2;
          count++;
          value = StringCache.valueOf(count);
        }

        set   = ((GroupBox) parent.getLinkedData()).widgets;
        edits = changeRows(pane, set, row, 1, count);
        e     = changeValue(pane, gb, "rows", value);

        if (e != null) {
          edits.addEdit(e);
        }

        break;

      case SplitPane : {
        SplitPane sp = (SplitPane) parent.getLinkedData();

        set = sp.regions;

        Region r;

        if ((row < 0) || (row >= set.size())) {
          set.add(r = new Region());
          row = set.size() - 1;
        } else {
          set.add(row, r = new Region());
        }

        edits = new DesignCompoundEdit(pane);

        if (sp.getSplitProportions() != null) {
          edits.addEdit(new SPOTReferenceDeleteUndoableEdit(pane, sp, "splitProportions", sp.getSplitProportions()));
          sp.setSplitProportions((iSPOTElement)null);
        }

        edits.addEdit(new SPOTSetUndoableEdit(pane, set, row, null, r));

        break;
      }

      case GridPane : {
        GridPane gp = (GridPane) parent.getLinkedData();

        set = gp.regions;

        int rows = ((GridPaneViewer) parent).getRowCount() + 1;
        int cols = ((GridPaneViewer) parent).getColumnCount();

        if ((row < 0) || (row >= rows)) {
          row = rows - 1;
        }

        edits = changeRegionRows(pane, set, row, 1, rows);

        Region r;

        for (int i = 0; i < cols; i++) {
          r = new Region();
          r.x.setValue(i);
          r.y.setValue(row);
          set.add(r);
          edits.addEdit(new SPOTSetUndoableEdit(pane, set, row, null, r));
        }

        e = changeValue(pane, gp, "rows", StringCache.valueOf(rows));

        if (e != null) {
          edits.addEdit(e);
        }

        break;
      }

      default :
        UISoundHelper.errorSound();

        break;
    }

    if ((edits != null) && edits.hasEdits()) {
      edits.setReloadRequired(true);
      pane.addUndoableEdit(edits, false, null);
      pane.requestReload(null);
    }
  }

//  public static DesignUndoableEdit addWidget(DesignPane pane, iContainer p, Widget wc, Location location,
//          boolean undoable) {
//    if (p == null) {
//      return null;
//    }
//
//    SPOTSet set   = null;
//    int     index = location.position;
//
//    switch(p.getWidgetType()) {
//      case Form :
//      case GroupBox :
//        set = ((GroupBox) p.getLinkedData()).widgets;
//        wc.bounds.x.setValue(location.x);
//        wc.bounds.y.setValue(location.y);
//
//        if (set.indexOfEx(wc) == -1) {
//          set.add(wc);
//          checkLayout(p, location.x, location.y, true);
//        }
//
//        break;
//
//      case ToolBar :
//        set = ((ToolBar) p.getLinkedData()).widgets;
//        set.removeEx(wc);
//        set.add(index, wc);
//
//        break;
//
//      case MenuBar :
//        if ((wc instanceof Bean)) {
//          Bean b = (Bean) wc;
//
//          if (b.beanClass.equals("javax.swing.JMenu")) {
//            MenuItem mi = (MenuItem) b.spot_getLinkedData();
//
//            if (mi == null) {
//              mi = new MenuItem();
//              mi.value.setValue("Menu");
//              b.spot_setLinkedData(mi);
//            }
//
//            set = ((MenuBar) p.getLinkedData()).getPopupMenuReference();
//            set.removeEx(mi);
//            set.add(index, mi);
//
//            break;
//          }
//        }
//
//        return null;
//
//      case WidgetPane :
//      case CollapsiblePane :
//        ((WidgetPane) p.getLinkedData()).widget.setValue(wc);
//
//        break;
//
//      case Window :
//        MainWindow mw = ((Application) p.getLinkedData()).application.getMainWindow();
//
//        if (wc instanceof StatusBar) {
//          mw.setStatusBar(wc);
//        } else if (wc instanceof MenuBar) {
//          mw.setMenuBar(wc);
//        } else {
//          mw.viewer.setValue(wc);
//        }
//
//        break;
//
//      case TabPane : {
//        int n = ((iTabPaneViewer) p).getSelectedTab();
//
//        if (n == -1) {
//          n = 0;
//        }
//
//        Object o = p.getLinkedData();
//
//        set = ((TabPane) o).tabs;
//
//        if (set.size() <= n) {
//          return null;
//        }
//
//        if (!(wc instanceof Viewer)) {
//          wc = new WidgetPane(wc);
//        }
//
//        ((Tab) set.get(n)).viewer.setValue(wc);
//
//        break;
//      }
//
//      case StackPane : {
//        StackPane o = (StackPane) p.getLinkedData();
//
//        set = o.getViewersReference();
//
//        if (!(wc instanceof Viewer)) {
//          wc = new WidgetPane(wc);
//        }
//
//        set.add(new SPOTAny(wc));
//
//        break;
//      }
//
//      case SplitPane :
//      case GridPane :
//        if (p instanceof GridPaneViewer) {
//          set = ((GridPane) p.getLinkedData()).regions;
//        } else {
//          set = ((SplitPane) p.getLinkedData()).regions;
//        }
//
//        if (index == -1) {
//          index = 0;
//        }
//
//        if ((set != null) && (index != -1) && (index < set.size())) {
//          if (!(wc instanceof Viewer)) {
//            wc = new WidgetPane(wc);
//          }
//
//          ((Region) set.getEx(index)).viewer.setValue(wc);
//        } else {
//          return null;
//        }
//
//        break;
//
//      default :
//        return null;
//    }
//
//    if (undoable) {
//      AddRemoveUndoableEdit e = new AddRemoveUndoableEdit(pane, p, wc, false, location);
//
//      return e;
//    }
//
//    return null;
//  }
  public static DesignUndoableEdit addWidget(DesignPane pane, SPOTSequence pwc, Widget wc, Location location,
          boolean undoable) {
    SPOTSet set   = null;
    int     index = location.position;

    switch(getWidgetType(pwc)) {
      case Form :
      case GroupBox :
        GroupBox gb=(GroupBox)pwc;
        set = ((GroupBox) pwc).widgets;
        if(gb.layout.intValue()==GroupBox.CLayout.flow) {
          if(!addto(set, wc, index)) {
            return null;
          }
        }
        else {
          if(!location.isValid()) {
            return null;
          }
          wc.bounds.x.setValue(location.x);
          wc.bounds.y.setValue(location.y);
          if (set.indexOfEx(wc) == -1) {
            set.add(wc);
            checkLayout((GroupBox) pwc, location.x, location.y, true);
          }
        }
        break;

      case ToolBar :
        set = ((ToolBar) pwc).widgets;
        if(!addto(set, wc, location.position)) {
          return null;
        }

        break;

      case MenuBar :
        if ((wc instanceof Bean)) {
          Bean b = (Bean) wc;

          if (b.beanClass.equals("javax.swing.JMenu")) {
            MenuItem mi = (MenuItem) b.spot_getLinkedData();

            if (mi == null) {
              mi = new MenuItem();
              mi.value.setValue("Menu");
              b.spot_setLinkedData(mi);
            }

            set = ((MenuBar) pwc).getPopupMenuReference();
            if(!addto(set, wc, location.position)) {
              return null;
            }

            break;
          }
        }

        return null;

      case WidgetPane :
      case CollapsiblePane :
        ((WidgetPane) pwc).widget.setValue(wc);

        break;

      case Window :
        MainWindow mw = (MainWindow) pwc;

        if (wc instanceof StatusBar) {
          mw.setStatusBar(wc);
        } else if (wc instanceof MenuBar) {
          mw.setMenuBar(wc);
        } else {
          mw.viewer.setValue(wc);
        }

        break;

      case TabPane : {
        int n = ((TabPane) pwc).selectedIndex.intValue();

        if (n == -1) {
          n = 0;
        }

        set = ((TabPane) pwc).tabs;

        if (set.size() <= n) {
          return null;
        }

        if (!(wc instanceof Viewer)) {
          wc = new WidgetPane(wc);
        }

        ((Tab) set.get(n)).viewer.setValue(wc);

        break;
      }

      case StackPane : {
        StackPane o = (StackPane) pwc;

        set = o.getViewersReference();

        if (!(wc instanceof Viewer)) {
          wc = new WidgetPane(wc);
        }

        set.add(index,new SPOTAny(wc));

        break;
      }

      case SplitPane :
      case GridPane :
        if (pwc instanceof GridPane) {
          set = ((GridPane) pwc).regions;
        } else {
          set = ((SplitPane) pwc).regions;
        }

        if (index == -1) {
          index = 0;
        }

        if ((set != null) && (index != -1) && (index < set.size())) {
          if (!(wc instanceof Viewer)) {
            wc = new WidgetPane(wc);
          }

          ((Region) set.getEx(index)).viewer.setValue(wc);
        } else {
          return null;
        }

        break;

      default :
        return null;
    }

    if (undoable) {
      AddRemoveUndoableEdit e = new AddRemoveUndoableEdit(pane, pwc, wc, false, location);

      return e;
    }

    return null;
  }

  public static void align(DesignPane pane, iWidget parent, List<Widget> widgets, Alignment alignment) {
    DesignCompoundEdit edits = new DesignCompoundEdit(pane);
    iSPOTElement       e;
    String             value;

    for (Widget w : widgets) {
      switch(alignment) {
        case LEFT :
          e     = w.horizontalAlign;
          value = "left";

          break;

        case RIGHT :
          e     = w.horizontalAlign;
          value = "right";

          break;

        case CENTER_HORIZONTAL :
          e     = w.horizontalAlign;
          value = "center";

          break;

        case FULL_HORIZONTAL :
          e     = w.horizontalAlign;
          value = "full";

          break;

        case TOP :
          e     = w.verticalAlign;
          value = "top";

          break;

        case BOTTOM :
          e     = w.verticalAlign;
          value = "bottom";

          break;

        case CENTER_VERTICAL :
          e     = w.verticalAlign;
          value = "center";

          break;

        case FULL_VERTICAL :
          e     = w.verticalAlign;
          value = "full";

          break;

        case FULL :
          e     = w.verticalAlign;
          value = "full";
          changeValue(pane, e, value, edits);
          e = w.horizontalAlign;
          changeValue(pane, e, value, edits);
          e     = null;
          value = null;

          break;

        case CENTER :
          e     = w.verticalAlign;
          value = "center";
          changeValue(pane, e, value, edits);
          e = w.horizontalAlign;
          changeValue(pane, e, value, edits);
          e     = null;
          value = null;

          break;

        default :
          e     = null;
          value = null;

          break;
      }

      changeValue(pane, e, value, edits);
    }

    if ((edits != null) && edits.hasEdits()) {
      edits.setReloadRequired(true);
      pane.addUndoableEdit(edits, false, null);
      pane.requestReload(null);
    }
  }

  public static void changeAttribute(DesignPane pane, iSPOTElement e, String name, Object object) {
    String value = resolveAttribute(e, name, object);

    if (value != NO_CHANGE) {
      String ov = e.spot_getAttribute(name);

      e.spot_setAttribute(name, value);

      if ((ov != null) && (ov.length() == 0)) {
        ov = null;
      }

      pane.addUndoableEdit(new AttributeUndoableEdit(pane, e, name, ov, value), false, null);
    }
  }

  public static void changeAttributes(DesignPane pane, iSPOTElement e, Map<String, String> map) {
    DesignCompoundEdit edit = changeAttributesEx(pane, e, map, true);

    if ((edit != null) && edit.hasEdits()) {
      pane.addUndoableEdit(edit, false, null);
    }
  }

  public static void changeAttributes(DesignPane pane, List<SPOTSequence> list, List<String> path,
          Map<String, String> map, boolean undoable) {
    DesignCompoundEdit edits = undoable
                               ? new DesignCompoundEdit(pane)
                               : null;
    DesignCompoundEdit e;
    iSPOTElement       ee;

    for (SPOTSequence w : list) {
      if (w == null) {
        continue;
      }

      ee = getElement(w, path);

      if (ee == null) {
        continue;
      }

      e = changeAttributesEx(pane, ee, map, undoable);

      if (e != null) {
        edits.addEdits(e);
      }
    }

    if ((edits != null) && edits.hasEdits()) {
      pane.addUndoableEdit(edits, false, null);
    }
  }

  public static void changeAttributes(DesignPane pane, List<SPOTSequence> list, String name, Object object) {
    String             ov;
    boolean            change;
    DesignCompoundEdit edits =  new DesignCompoundEdit(pane);
    String             value = (object == null)
                               ? null
                               : object.toString();

    if ((value != null) && (value.length() == 0)) {
      value = null;
    }

    for (SPOTSequence e : list) {
      if (e == null) {
        continue;
      }

      ov = e.spot_getAttribute(name);

      if ((ov != null) && (ov.length() == 0)) {
        ov = null;
      }

      if (value == null) {
        change = ov != null;
      } else {
        change = !value.equals(ov);
      }

      if (change) {
        e.spot_setAttribute(name, value);

        edits.addEdit(new AttributeUndoableEdit(pane, e, name, e.spot_getAttribute(name), value));
      }
    }

    if (edits.hasEdits()) {
      pane.addUndoableEdit(edits, false, null);
    }
  }

  public static void changeColumnSpec(DesignPane pane, iWidget parent, int col, String spec, int group) {
    String value = null;

    switch(parent.getWidgetType()) {
      case Form :
      case GroupBox :
        if (parent.getDataComponent() instanceof FormsPanel) {
          GroupBox   gb = (GroupBox) parent.getLinkedData();
          FormLayout fl = new FormLayout(gb.columns.getValue(), gb.rows.getValue());

          fl.setColumnSpec(col + 1, ColumnSpec.decode(spec));
          value = FormsPanel.getColumns(fl, -1);
          changeSpec(pane, parent, "columns", value, group, false, col);
        } else {
          UISoundHelper.errorSound();
        }

        break;

      default :
        break;
    }
  }

  public static UndoableEdit changeConstraints(DesignPane pane, iWidget w, int newPosition, boolean undoable) {
    iContainer p = w.getParent();

    switch(p.getWidgetType()) {
      case ToolBar :
        SPOTSet set = ((ToolBar) p.getLinkedData()).widgets;
        int     i   = set.indexOfEx((iSPOTElement) w.getLinkedData());

        if (i == -1) {
          return null;
        }

        iSPOTElement o = (iSPOTElement) set.remove(i);

        set.add(newPosition, o);

        if (undoable) {
          return new InsertUndoableEdit(pane, w, newPosition, i);
        }

        break;

      default :
        break;
    }

    return null;
  }

  public static void changeEvent(DesignPane pane, iSPOTElement e, String name, Object object) {
    String value = resolveAttribute(e, name, object);

    if (value != NO_CHANGE) {
      e.spot_setAttribute(name, value);

      String ov = e.spot_getAttribute(name);

      if ((ov != null) && (ov.length() == 0)) {
        ov = null;
      }

      AttributeUndoableEdit ue = new AttributeUndoableEdit(pane, e, name, ov, value);

      pane.addUndoableEdit(ue, false, null);
    }
  }

  public static void changeEvents(DesignPane pane, List<Widget> list, String name, Object object) {
    String ov;
    String value = (object == null)
                   ? null
                   : object.toString();

    if ((value != null) && (value.length() == 0)) {
      value = null;
    }

    DesignCompoundEdit edits = new DesignCompoundEdit(pane);

    for (Widget w : list) {
      if (w == null) {
        continue;
      }

      ov = w.spot_getAttribute(name);

      if ((ov != null) && (ov.length() == 0)) {
        ov = null;
      }

      if (ov == null) {
        if (value == null) {
          continue;
        }
      } else if (ov.equals(value)) {
        continue;
      }

      w.spot_setAttribute(name, value);
      edits.addEdit(new AttributeUndoableEdit(pane, w, name, ov, value));
    }

    if (edits.hasEdits()) {
      pane.addUndoableEdit(edits, false, null);
    }
  }

  public static void changeRowSpec(DesignPane pane, iWidget parent, int row, String spec, int group) {
    String value = null;

    switch(parent.getWidgetType()) {
      case Form :
      case GroupBox :
        if (parent.getDataComponent() instanceof FormsPanel) {
          GroupBox   gb = (GroupBox) parent.getLinkedData();
          FormLayout fl = new FormLayout(gb.columns.getValue(), gb.rows.getValue());

          fl.setRowSpec(row + 1, RowSpec.decode(spec));
          value = FormsPanel.getRows(fl, -1);
          changeSpec(pane, parent, "rows", value, group, true, row);
        } else {
          UISoundHelper.errorSound();
        }

        break;

      default :
        break;
    }
  }

  public static void changeValue(DesignPane pane, iSPOTElement e, Object object) throws MalformedURLException {
    if (e != null) {
      RenderableDataItem item = null;

      if (object instanceof RenderableDataItem) {
        item   = (RenderableDataItem) object;
        object = item.getValue();
      }

      changeValueEx(pane, e, item, object);
    }
  }

  public static Object changeValue(DesignPane pane, iSPOTElement e, Object object, boolean update,
                                   boolean reloadRequiredForUndo, MutableInteger changed)
          throws MalformedURLException {
    if (e != null) {
      RenderableDataItem item = null;

      if (object instanceof RenderableDataItem) {
        item   = (RenderableDataItem) object;
        object = item.getValue();
      }

      DesignUndoableEdit ue = changeValueEx(pane, e, item, object);

      if (ue != null) {
        ue.setReloadRequired(reloadRequiredForUndo);
        pane.addUndoableEdit(ue, update, null);
        changed.setValue(1);
      }
    }

    return object;
  }

  public static Object changeValues(DesignPane pane, List<SPOTSequence> list, iSPOTElement e, Object object, MutableInteger changed)
          throws MalformedURLException {
    if (e != null) {
      if (list.size() == 1) {
        return changeValue(pane, e, object, false, true, changed);
      }

      RenderableDataItem item = null;

      if (object instanceof RenderableDataItem) {
        item   = (RenderableDataItem) object;
        object = item.getValue();
      }

      DesignCompoundEdit edits = new DesignCompoundEdit(pane);
      DesignUndoableEdit ue;
      List<String>       path = createNamePath(list.get(0), e);
      String             name = (path.size() == 1)
                                ? path.get(0)
                                : null;

      for (SPOTSequence seq : list) {
        if (seq == null) {
          continue;
        }

        if (name == null) {
          e = getElement(seq, path);
        } else {
          e = seq.spot_elementForEx(name);
        }

        if (e == null) {
          continue;
        }

        ue = changeValueEx(pane, e, item, object);

        if (ue != null) {
          edits.addEdit(ue);
        }
      }

      if (edits.hasEdits()) {
        if (!edits.isReloadRequired()) {
          edits.setReloadRequired(true);
        }

        pane.addUndoableEdit(edits, false, null);
      }

      changed.setValue(edits.hasEdits()
                       ? 1
                       : 0);
    } else {
      changed.setValue(0);
    }

    return object;
  }

  public static List<String> createNamePath(SPOTSequence root, iSPOTElement e) {
    List<String> list = new ArrayList<String>();

    list.add(e.spot_getName());

    iSPOTElement p = (iSPOTElement) e.spot_getParent();

    while((p != root) && (p != null) && (p.spot_getParent() != null)) {
      if (!(p instanceof SPOTSequence)) {
        break;
      }

      list.add(0, p.spot_getName());
      p = p.spot_getParent();
    }

    return list;
  }

  public static UndoableEdit moveWidget(DesignPane pane, iContainer p, iWidget w, Constraints constraints,
          boolean undoable) {
    Widget       wc = (Widget) w.getLinkedData();
    UndoableEdit e1 = removeWidget(pane, (SPOTSequence) w.getParent().getLinkedData(), w, wc, undoable);

    if (e1 == null) {
      return null;
    }

    DesignCompoundEdit e = new DesignCompoundEdit(pane);

    e.addEdit(e1);
    e1 = addWidget(pane, (SPOTSequence) p.getLinkedData(), wc, constraints.location, undoable);

    if (e1 != null) {
      e.addEdit(e1);
    }

    return e;
  }

  public static void removeColumn(DesignPane pane, iContainer parent, int col) {
    String             value = null;
    DesignCompoundEdit edits = null;
    DesignEdit         e     = null;
    SPOTSet            set;

    switch(parent.getWidgetType()) {
      case Form :
      case GroupBox : {
        FormsPanel fp = (FormsPanel) parent.getDataComponent();

        if ((col >= fp.getColumnCount()) || (fp.getColumnCount() == 1)) {
          UISoundHelper.errorSound();

          return;
        }

        set   = ((GroupBox) parent.getLinkedData()).widgets;
        edits = removeWidgets(pane, parent, set, col, false);
        e     = changeColumns(pane, set, col, -1, fp.getColumnCount() - 1);

        if (e != null) {
          edits.addEdit(e);
        }

        value = fp.getColumns(col);
        e     = changeValue(pane, (SPOTSequence) parent.getLinkedData(), "columns", value);

        if (e != null) {
          edits.addEdit(e);
        }

        break;
      }

      case GridPane : {
        GridPaneViewer gp = (GridPaneViewer) parent;

        if ((col >= gp.getColumnCount()) || (gp.getColumnCount() == 1)) {
          UISoundHelper.errorSound();

          return;
        }

        set   = ((GridPane) parent.getLinkedData()).regions;
        edits = removeRegions(pane, parent, set, col, false);
        e     = changeRegionColumns(pane, set, col, -1, gp.getColumnCount() - 1);

        if (e != null) {
          edits.addEdit(e);
        }

        value = StringCache.valueOf(gp.getColumnCount() - 1);
        e     = changeValue(pane, (SPOTSequence) parent.getLinkedData(), "columns", value);

        if (e != null) {
          edits.addEdit(e);
        }

        break;
      }

      case SplitPane : {
        SplitPane sp = (SplitPane) parent.getLinkedData();

        set = sp.regions;

        if ((col < 0) || (col >= set.size()) || (set.size() < 3)) {
          UISoundHelper.errorSound();

          return;
        }

        Region r = (Region) set.remove(col);

        edits = new DesignCompoundEdit(pane);

        if (sp.getSplitProportions() != null) {
          edits.addEdit(new SPOTReferenceDeleteUndoableEdit(pane, sp, "splitProportions", sp.getSplitProportions()));
          sp.setSplitProportions((iSPOTElement)null);
        }

        edits.addEdit(new SPOTSetUndoableEdit(pane, set, col, r, null));

        break;
      }

      default :
        break;
    }

    if ((edits != null) && edits.hasEdits()) {
      edits.setReloadRequired(true);
      pane.addUndoableEdit(edits, false, null);
      pane.requestReload(null);
    }
  }

  public static void removeRow(DesignPane pane, iContainer parent, int row) {
    String             value = null;
    DesignCompoundEdit edits = null;
    DesignEdit         e;
    SPOTSet            set;

    switch(parent.getWidgetType()) {
      case Form :
      case GroupBox : {
        FormsPanel fp = (FormsPanel) parent.getDataComponent();

        if ((row >= fp.getRowCount()) || (fp.getRowCount() == 1)) {
          UISoundHelper.errorSound();

          return;
        }

        set   = ((GroupBox) parent.getLinkedData()).widgets;
        edits = removeWidgets(pane, parent, set, row, true);
        e     = changeRows(pane, set, row, -1, fp.getRowCount() - 1);

        if (e != null) {
          edits.addEdit(e);
        }

        value = fp.getRows(row);
        e     = changeValue(pane, (SPOTSequence) parent.getLinkedData(), "rows", value);

        if (e != null) {
          edits.addEdit(e);
        }

        break;
      }

      case GridPane : {
        GridPaneViewer gp = (GridPaneViewer) parent;

        if ((row >= gp.getRowCount()) || (gp.getRowCount() == 1)) {
          UISoundHelper.errorSound();

          return;
        }

        set   = ((GridPane) parent.getLinkedData()).regions;
        edits = removeRegions(pane, parent, set, row, true);
        e     = changeRegionRows(pane, set, row, -1, gp.getRowCount() - 1);

        if (e != null) {
          edits.addEdit(e);
        }

        value = StringCache.valueOf(gp.getRowCount() - 1);
        e     = changeValue(pane, (SPOTSequence) parent.getLinkedData(), "rows", value);

        if (e != null) {
          edits.addEdit(e);
        }

        break;
      }

      case SplitPane : {
        SplitPane sp = (SplitPane) parent.getLinkedData();

        set = sp.regions;

        if ((row < 0) || (row >= set.size()) || (set.size() < 3)) {
          UISoundHelper.errorSound();

          return;
        }

        Region r = (Region) set.getEx(row);

        edits = new DesignCompoundEdit(pane);

        if (sp.getSplitProportions() != null) {
          edits.addEdit(new SPOTReferenceDeleteUndoableEdit(pane, sp, "splitProportions", sp.getSplitProportions()));
          sp.setSplitProportions((iSPOTElement)null);
        }

        edits.addEdit(new SPOTSetUndoableEdit(pane, set, row, r, null));

        break;
      }

      default :
        break;
    }

    if ((edits != null) && edits.hasEdits()) {
      edits.setReloadRequired(true);
      pane.addUndoableEdit(edits, false, null);
      pane.requestReload(null);
    }
  }

//  public static UndoableEdit removeWidget(DesignPane pane, iContainer p, iWidget w, Widget wc, boolean undoable) {
//    if (p == null) {
//      return null;
//    }
//
//    SPOTSet set    = null;
//    boolean reload = true;
//    int     index  = -1;
//    boolean removed;
//
//    if (wc == null) {
//      wc = (Widget) w.getLinkedData();
//    }
//
//    SPOTSequence pld = (SPOTSequence) p.getLinkedData();
//
//    switch(p.getWidgetType()) {
//      case GroupBox :
//      case Form :
//        set     = ((GroupBox) pld).widgets;
//        removed = set.removeEx(wc) != -1;
//
//        if (removed) {
//          checkLayout(p, wc.bounds.x.getValue(), wc.bounds.y.getValue(), false);
//        }
//
//        break;
//
//      case ToolBar :
//        set     = ((ToolBar) pld).widgets;
//        index   = set.removeEx(wc);
//        removed = index != -1;
//
//        break;
//
//      case MenuBar :
//        if ((wc instanceof Bean)) {
//          Bean b = (Bean) wc;
//
//          if (b.beanClass.equals("javax.swing.JMenu")) {
//            MenuItem mi = (MenuItem) b.spot_getLinkedData();
//
//            if (mi == null) {
//              removed = false;
//
//              break;
//            }
//
//            set     = ((MenuBar) p.getLinkedData()).getPopupMenuReference();
//            index   = set.removeEx(mi);
//            removed = index != -1;
//
//            break;
//          }
//        }
//
//        removed = false;
//
//        break;
//
//      case WidgetPane :
//      case CollapsiblePane :
//        if (((WidgetPane) pld).widget.getValue() == wc) {
//          ((WidgetPane) pld).widget.setValue((Widget) null);
//          removed = true;
//        } else {
//          removed = false;
//        }
//
//        break;
//
//      case Window :
//        MainWindow mw = ((Application) pld).application.getMainWindow();
//
//        if (wc instanceof StatusBar) {
//          if (mw.getStatusBar() == wc) {
//            mw.setStatusBar(null);
//            removed = true;
//          } else {
//            removed = false;
//          }
//        } else if (wc instanceof MenuBar) {
//          if (mw.getMenuBar() == wc) {
//            mw.setMenuBar(null);
//            removed = true;
//          } else {
//            removed = false;
//          }
//        } else {
//          if (mw.viewer.getValue() == wc) {
//            mw.viewer.setValue((Widget) null);
//            removed = true;
//          } else {
//            removed = false;
//          }
//        }
//
//        break;
//
//      case TabPane : {
//        int n = ((iTabPaneViewer) p).getSelectedTab();
//
//        if (n == -1) {
//          n = 0;
//        }
//
//        set = ((TabPane) pld).tabs;
//
//        if (set.size() <= n) {
//          removed = false;
//
//          break;
//        }
//
//        if (((Tab) set.get(n)).viewer.getValue() == wc) {
//          ((Tab) set.get(n)).viewer.setValue((Widget) null);
//          removed = true;
//        } else {
//          removed = false;
//        }
//
//        break;
//      }
//
//      case StackPane : {
//        int n = ((StackPaneViewer) p).getActiveViewerIndex();
//
//        if (n == -1) {
//          n = 0;
//        }
//
//        StackPane o = (StackPane) p.getLinkedData();
//
//        set = o.getViewersReference();
//
//        if (set.size() <= n) {
//          removed = false;
//
//          break;
//        }
//
//        if (set.getEx(n) == wc) {
//          set.remove(n);
//          removed = true;
//        } else {
//          removed = false;
//        }
//
//        break;
//      }
//
//      case SplitPane :
//      case GridPane :
//        if (p instanceof GridPaneViewer) {
//          set = ((GridPane) pld).regions;
//        } else {
//          set = ((SplitPane) pld).regions;
//        }
//
//        index = getRegionIndex(set, wc);
//
//        if ((set != null) && (index != -1) && (index < set.size())) {
//          ((Region) set.getEx(index)).viewer.setValue((Widget) null);
//          removed = true;
//        } else {
//          removed = false;
//        }
//
//        break;
//
//      default :
//        removed = false;
//
//        break;
//    }
//
//    if (!removed) {
//      iSPOTElement any = wc.spot_getParent();
//
//      if (any instanceof SPOTAny) {
//        ((SPOTAny) any).setValue((iSPOTElement) null);
//
//        if (undoable) {
//          SPOTUndoableEdit e = new SPOTUndoableEdit(pane, any, wc, null);
//
//          e.setReloadRequired(reload);
//
//          return e;
//        }
//      }
//
//      return null;
//    }
//
//    if (undoable) {
//      Location              loc = new Location(wc.bounds.getX(), wc.bounds.getY(), index);
//      AddRemoveUndoableEdit e   = new AddRemoveUndoableEdit(pane, p, wc, true, loc);
//
//      e.setReloadRequired(reload);
//
//      return e;
//    }
//
//    return null;
//  }
  public static UndoableEdit removeWidget(DesignPane pane, SPOTSequence pld, iWidget w, Widget wc, boolean undoable) {
    if (pld == null) {
      return null;
    }

    SPOTSet set    = null;
    boolean reload = true;
    int     index  = -1;
    boolean removed;

    if (wc == null) {
      wc = (Widget) w.getLinkedData();
    }

    switch(getWidgetType(pld)) {
      case GroupBox :
      case Form :
        set     = ((GroupBox) pld).widgets;
        removed = set.removeEx(wc) != -1;

        if (removed) {
          checkLayout((GroupBox) pld, wc.bounds.x.getValue(), wc.bounds.y.getValue(), false);
        }

        break;

      case ToolBar :
        set     = ((ToolBar) pld).widgets;
        index   = set.removeEx(wc);
        removed = index != -1;

        break;

      case MenuBar :
        if ((wc instanceof Bean)) {
          Bean b = (Bean) wc;

          if (b.beanClass.equals("javax.swing.JMenu")) {
            MenuItem mi = (MenuItem) b.spot_getLinkedData();

            if (mi == null) {
              removed = false;

              break;
            }

            set     = ((MenuBar) pld).getPopupMenuReference();
            index   = set.removeEx(mi);
            removed = index != -1;

            break;
          }
        }

        removed = false;

        break;

      case WidgetPane :
      case CollapsiblePane :
        if (((WidgetPane) pld).widget.getValue() == wc) {
          ((WidgetPane) pld).widget.setValue((Widget) null);
          removed = true;
        } else {
          removed = false;
        }

        break;

      case Window :
        MainWindow mw = ((MainWindow) pld);

        if (wc instanceof StatusBar) {
          if (mw.getStatusBar() == wc) {
            mw.setStatusBar(null);
            removed = true;
          } else {
            removed = false;
          }
        } else if (wc instanceof MenuBar) {
          if (mw.getMenuBar() == wc) {
            mw.setMenuBar(null);
            removed = true;
          } else {
            removed = false;
          }
        } else {
          if (mw.viewer.getValue() == wc) {
            mw.viewer.setValue((Widget) null);
            removed = true;
          } else {
            removed = false;
          }
        }

        break;

      case TabPane : {
        int n = ((TabPane) pld).selectedIndex.intValue();

        if (n == -1) {
          n = 0;
        }

        set = ((TabPane) pld).tabs;

        if (set.size() <= n) {
          removed = false;

          break;
        }

        if (((Tab) set.get(n)).viewer.getValue() == wc) {
          ((Tab) set.get(n)).viewer.setValue((Widget) null);
          removed = true;
        } else {
          removed = false;
        }

        break;
      }

      case StackPane : {
        int n = ((StackPane) pld).selectedIndex.intValue();

        if (n == -1) {
          n = 0;
        }

        StackPane o = (StackPane) pld;

        set = o.getViewersReference();

        if (set.size() <= n) {
          removed = false;

          break;
        }

        if (set.getEx(n) == wc) {
          set.remove(n);
          removed = true;
        } else {
          removed = false;
        }

        break;
      }

      case SplitPane :
      case GridPane :
        if (pld instanceof GridPane) {
          set = ((GridPane) pld).regions;
        } else {
          set = ((SplitPane) pld).regions;
        }

        index = getRegionIndex(set, wc);

        if ((set != null) && (index != -1) && (index < set.size())) {
          ((Region) set.getEx(index)).viewer.setValue((Widget) null);
          removed = true;
        } else {
          removed = false;
        }

        break;

      default :
        removed = false;

        break;
    }

    if (!removed) {
      iSPOTElement any = wc.spot_getParent();

      if (any instanceof SPOTAny) {
        ((SPOTAny) any).setValue((iSPOTElement) null);

        if (undoable) {
          SPOTUndoableEdit e = new SPOTUndoableEdit(pane, any, wc, null);

          e.setReloadRequired(reload);

          return e;
        }
      }

      return null;
    }

    if (undoable) {
      Location              loc = new Location(wc.bounds.getX(), wc.bounds.getY(), index);
      AddRemoveUndoableEdit e   = new AddRemoveUndoableEdit(pane, pld, wc, true, loc);

      e.setReloadRequired(reload);

      return e;
    }

    return null;
  }

  public static UndoableEdit replaceWidget(DesignPane pane, iContainer parent, iWidget w1, iWidget w2, Widget wc2,
          boolean undoable) {
    iContainer p1   = w1.getParent();
    iContainer p2   = (w2 == null)
                      ? null
                      : w2.getParent();
    Widget     wc1  = (Widget) w1.getLinkedData();
    Location   loc1 = new Location();

    loc1.setLocation(wc1, -1);

    switch(p1.getWidgetType()) {
      case ToolBar :
        loc1.position = ((ToolBar) p1.getLinkedData()).widgets.indexOfEx(wc1);

        break;

      case SplitPane :
      case GridPane :
        loc1.position = ((aRegionViewer) p1).getRegionIndex(w1);

        break;

      default :
        break;
    }

    DesignCompoundEdit edits = new DesignCompoundEdit(pane);
    UndoableEdit       e     = removeWidget(pane, (SPOTSequence) p1.getLinkedData(), w1, wc1, undoable);

    if (e != null) {
      edits.addEdit(e);
    }

    if (w2 != null) {
      e = removeWidget(pane, (SPOTSequence) p2.getLinkedData(), w2, wc2, undoable);

      if (e != null) {
        edits.addEdit(e);
      }
    }

    e = addWidget(pane, (SPOTSequence) p1.getLinkedData(), wc2, loc1, undoable);

    if (e != null) {
      edits.addEdit(e);
    }

    if (!edits.hasEdits()) {
      return null;
    }

    return undoable
           ? edits
           : null;
  }

  public static void setColumnAsSpacer(DesignPane pane, iWidget parent, int col, boolean small) {
    setAsSpacer(pane, parent, col, false, small);
  }

  public static void setRowAsSpacer(DesignPane pane, iWidget parent, int row, boolean small) {
    setAsSpacer(pane, parent, row, true, small);
  }

  static DesignCompoundEdit changeAttributesEx(DesignPane pane, iSPOTElement e, Map<String, String> map,
          boolean undoable) {
    DesignCompoundEdit              edits = undoable
            ? new DesignCompoundEdit(pane)
            : null;
    Entry<String, String>           en;
    Iterator<Entry<String, String>> it = map.entrySet().iterator();
    String                          name;
    String                          value;

    while(it.hasNext()) {
      en    = it.next();
      name  = en.getKey();
      value = en.getValue();
      value = resolveAttribute(e, name, value);

      if (value != NO_CHANGE) {
        String ov = e.spot_getAttribute(name);

        e.spot_setAttribute(name, value);

        if ((ov != null) && (ov.length() == 0)) {
          ov = null;
        }

        if (undoable) {
          edits.addEdit(new AttributeUndoableEdit(pane, e, name, ov, value));
        }
      }
    }

    return edits;
  }

  static DesignCompoundEdit changeColumns(DesignPane pane, SPOTSet set, int sx, int dx, int cols) {
    int                len   = (set == null)
                               ? 0
                               : set.size();
    DesignCompoundEdit edits = new DesignCompoundEdit(pane);
    Widget             wc;
    int                x;

    if (dx < 0) {
      sx--;
    }

    int span;

    for (int i = 0; i < len; i++) {
      wc = (Widget) set.getEx(i);
      x  = wc.bounds.getX();

      if (x != -1) {
        x += dx;

        if (x > sx) {
          edits.addEdit(changeValue(pane, wc.bounds.x, StringCache.valueOf(x), true));
        } else {    // reset it back
          x -= dx;
        }

        if (dx < 0) {
          span = (int) wc.columnSpan.getValue();

          if (span > 1) {
            int ns = cols - x;

            if (ns < span) {
              edits.addEdit(changeValue(pane, wc.columnSpan, StringCache.valueOf(ns), true));
            }
          }
        }
      }
    }

    return edits;
  }

  static DesignCompoundEdit changeConstraints(DesignPane pane, iWidget w, int x, int y, boolean undoable) {
    Widget             wc    = (Widget) w.getLinkedData();
    DesignCompoundEdit edits = new DesignCompoundEdit(pane);
    UndoableEdit       e     = changeValue(pane, wc.bounds.x, StringCache.valueOf(x), undoable);

    if (e != null) {
      edits.addEdit(e);
    }

    e = changeValue(pane, wc.bounds.y, StringCache.valueOf(y), undoable);

    if (e != null) {
      edits.addEdit(e);
    }

    return edits;
  }

  static DesignCompoundEdit changeRegionColumns(DesignPane pane, SPOTSet set, int sx, int dx, int cols) {
    int                len   = (set == null)
                               ? 0
                               : set.size();
    DesignCompoundEdit edits = new DesignCompoundEdit(pane);
    Region             r;
    int                x;

    if (dx < 0) {
      sx--;
    }

    for (int i = 0; i < len; i++) {
      r = (Region) set.getEx(i);
      x = r.getX();

      if (x != -1) {
        x += dx;

        if (x > sx) {
          edits.addEdit(changeValue(pane, r.x, StringCache.valueOf(x), true));
        }
      }
    }

    return edits;
  }

  static DesignCompoundEdit changeRegionRows(DesignPane pane, SPOTSet set, int sy, int dy, int rows) {
    int                len   = (set == null)
                               ? 0
                               : set.size();
    DesignCompoundEdit edits = new DesignCompoundEdit(pane);
    Region             r;
    int                y;

    if (dy < 0) {
      sy--;
    }

    for (int i = 0; i < len; i++) {
      r = (Region) set.getEx(i);
      y = r.getY();

      if (y != -1) {
        y += dy;

        if (y > sy) {
          edits.addEdit(changeValue(pane, r.y, StringCache.valueOf(y), true));
        }
      }
    }

    return edits;
  }

  static DesignCompoundEdit changeRows(DesignPane pane, SPOTSet set, int sy, int dy, int rows) {
    int                len   = (set == null)
                               ? 0
                               : set.size();
    DesignCompoundEdit edits = new DesignCompoundEdit(pane);
    Widget             wc;
    int                y;
    int                span;

    if (dy < 0) {
      sy--;
    }

    for (int i = 0; i < len; i++) {
      wc = (Widget) set.getEx(i);
      y  = wc.bounds.getY();

      if (y != -1) {
        y += dy;

        if (y > sy) {
          edits.addEdit(changeValue(pane, wc.bounds.y, StringCache.valueOf(y), true));
        } else {    // reset it back
          y -= dy;
        }

        if (dy < 0) {
          span = (int) wc.rowSpan.getValue();

          if (span > 1) {
            int ns = rows - y;

            if (ns < span) {
              edits.addEdit(changeValue(pane, wc.rowSpan, StringCache.valueOf(ns), true));
            }
          }
        }
      }
    }

    return edits;
  }

  static void changeSpec(DesignPane pane, iWidget parent, String name, String value, int group, boolean row, int pos) {
    DesignEdit e1 = changeValue(pane, (SPOTSequence) parent.getLinkedData(), name, value);
    DesignEdit e2 = modifyGroup(pane, parent, group, pos, row);

    if ((e1 != null) || (e2 != null)) {
      if (e2 == null) {
        e1.setReloadRequired(true);
        pane.addUndoableEdit(e1, false, null);
      } else if (e1 == null) {
        e2.setReloadRequired(true);
        pane.addUndoableEdit(e2, false, null);
      } else {
        DesignCompoundEdit edits = new DesignCompoundEdit(pane);

        edits.addEdit(e1);
        edits.addEdit(e2);
        edits.setReloadRequired(true);
        pane.addUndoableEdit(edits, false, null);
      }

      pane.requestReload(null);
    }
  }

  public static DesignUndoableEdit changeValue(DesignPane pane, iSPOTElement e, iSPOTElement value, boolean undoable) {
    if ((e instanceof SPOTAny) &&!(value instanceof SPOTAny)) {
      SPOTAny      a  = (SPOTAny) e;
      iSPOTElement ov = a.getValue();

      if (ov == value) {
        return null;
      }

      a.setValue(value);

      if (undoable) {
        return new SPOTUndoableEdit(pane, e, ov, value);
      }
    } else {
      iSPOTElement ov = (iSPOTElement) e.clone();

      if (value != null) {
        e.spot_copy(value, false);
      } else {
        e.spot_clear();
      }

      if (undoable) {
        return new SPOTUndoableEdit(pane, e, ov, value);
      }
    }

    return null;
  }

  static DesignUndoableEdit changeValue(DesignPane pane, iSPOTElement e, String value, boolean undoable) {
    String ov = e.spot_stringValue();

    if ((value != null) && value.equals(ov)) {
      return null;
    }

    e.spot_clearAttributes();

    int n = (value == null)
            ? -1
            : value.indexOf('[');

    if (n != -1) {
      CharScanner sc = new CharScanner(value);
      String      s  = sc.nextToken('[', true, true, true, true);

      e.spot_setValue(s);
      CharScanner.parseOptionStringEx(sc, e.spot_getAttributesEx(), ',', true);
    } else {
      e.spot_setValue(value);
    }

    if (undoable) {
      return new SPOTUndoableEdit(pane, e, ov, value);
    }

    return null;
  }

  static DesignUndoableEdit changeValue(DesignPane pane, SPOTSequence seq, String name, String value) {
    iSPOTElement e  = seq.spot_elementFor(name);
    String       ov = e.spot_stringValue();

    if ((value != null) && value.equals(ov)) {
      return null;
    }

    e.spot_setValue(value);

    return new SPOTUndoableEdit(pane, e, ov, value);
  }

  static DesignUndoableEdit changeValueEx(DesignPane pane, iSPOTElement e, RenderableDataItem item, Object object)
          throws MalformedURLException {
    String             value = null;
    DesignUndoableEdit ue    = null;

    if (object instanceof UIColorShade) {
      iBackgroundPainter bp = ((UIColorShade) object).getBackgroundPainter();
      iGradientPainter   gp = null;

      if (bp instanceof iGradientPainter) {
        gp = (iGradientPainter) bp;
      }

      if ((gp != null) && ((UIColorShade) object).getColorKey() == null) {
        SPOTPrintableString nv = new SPOTPrintableString();

        Utilities.setConfiguration(nv, gp);
        ue = changeValue(pane, e, nv, true);
      } else {
        ue = changeValue(pane, e, object.toString(), true);
      }
    } else if (object instanceof UIColor) {
      ue = changeValue(pane, e, ((UIColor) object).toString(), true);
    } else if (object instanceof iImagePainter) {
      SPOTPrintableString nv = new SPOTPrintableString();

      Utilities.setConfiguration(pane.getBaseURL(), nv, (iImagePainter) object);
      ue = changeValue(pane, e, nv, true);
    } else if (object instanceof File) {
      File file = (File) object;

      if (file.getName().length() > 0) {
        try {
          value = URLHelper.makeRelativePath(pane.getBaseURL(), file);
        } catch(Exception ex) {
          throw new MalformedURLException(file.getPath());
        }
      }

      ue = changeValue(pane, e, value, true);
    } else if (object instanceof String[]) {
      if (Arrays.equals(((SPOTSet) e).stringValues(), (String[]) object)) {
        return null;
      }

      SPOTSet set = (SPOTSet) e.clone();

      set.spot_clear();
      set.setValue((String[]) object);
      ue = changeValue(pane, e, set, true);
    } else if (object instanceof float[]) {
      if (Arrays.equals(((SPOTSet) e).floatValues(), (float[]) object)) {
        return null;
      }

      SPOTSet set = (SPOTSet) e.clone();

      set.spot_clear();

      float[] f   = (float[]) object;
      double  d[] = new double[f.length];

      for (int i = 0; i < f.length; i++) {
        d[i] = f[i];
      }

      set.setValue(d);
      ue = changeValue(pane, e, set, true);
    } else if (object instanceof double[]) {
      if (Arrays.equals(((SPOTSet) e).doubleValues(), (double[]) object)) {
        return null;
      }

      SPOTSet set = (SPOTSet) e.clone();

      set.spot_clear();
      set.setValue((double[]) object);
      ue = changeValue(pane, e, set, true);
    } else if (object instanceof long[]) {
      if (Arrays.equals(((SPOTSet) e).longValues(), (long[]) object)) {
        return null;
      }

      SPOTSet set = (SPOTSet) e.clone();

      set.spot_clear();
      set.setValue((long[]) object);
      ue = changeValue(pane, e, set, true);
    } else if (object instanceof long[]) {
      SPOTSet set = (SPOTSet) e.clone();

      set.spot_clear();
      set.setValue((long[]) object);
      ue = changeValue(pane, e, set, true);
    } else if (object instanceof iSPOTElement) {
      ue = changeValue(pane, e, (iSPOTElement) object, true);
    } else {
      if (object != null) {
        value = (item == null)
                ? object.toString()
                : item.toString();
        ue    = changeValue(pane, e, value, true);
      } else {
        ue = changeValue(pane, e, (iSPOTElement) object, true);
      }
    }

    return ue;
  }

  @SuppressWarnings("resource")
  static DesignEdit modifyGroup(DesignPane pane, iWidget parent, int group, int pos, boolean row) {
    if ((group < 1) ||!(parent instanceof GroupBoxViewer)) {
      return null;
    }

    pos++;

    GroupBox           gb  = (GroupBox) parent.getLinkedData();
    SPOTSet            set = row
                             ? gb.getRowGroupingReference()
                             : gb.getColumnGroupingReference();
    int                len = set.size();
    CharArray          ca  = new CharArray();
    String             s   = "," + StringCache.valueOf(pos) + ',';
    int                n;
    DesignUndoableEdit del = null;
    DesignUndoableEdit e   = null;

    for (int i = 0; i < len; i++) {
      ca.set(',').append(set.stringValueAt(i)).append(',');
      n = ca.indexOf(s);

      if (n != -1) {
        if (i + 1 == group) {
          return null;
        }

        ca.remove(n, n + s.length());

        if ((ca._length > 0) && (ca.A[ca._length - 1] == ',')) {
          ca._length--;
        }

        if ((ca._length > 0) && (ca.A[0] == ',')) {
          ca.deleteCharAt(0);
        }

        if (ca.length() == 0) {
          del = new SPOTSetUndoableEdit(pane, set, i, set.getEx(i));
          len--;
          set.remove(i);

          break;
        }

        del = changeValue(pane, set.getEx(i), ca.toString(), true);

        break;
      }
    }

    if (group > len) {
      SPOTPrintableString ps = new SPOTPrintableString(StringCache.valueOf(pos));

      set.add(ps);
      e = new SPOTSetUndoableEdit(pane, set, set.size() - 1, null, ps);
    } else {
      s = StringCache.valueOf(pos);

      String       gs   = set.stringValueAt(group - 1);
      List<String> strs = CharScanner.getTokens(gs, ',', true);

      len = strs.size();

      int i = 0;

      for (i = 0; i < len; i++) {
        String ss = strs.get(i);

        n = SNumber.intValue(ss);

        if (n == pos) {
          return null;
        }

        if (n > pos) {
          strs.add(i, s);

          break;
        }
      }

      if (i == len) {
        strs.add(s);
        len++;
      }

      StringBuilder sb = new StringBuilder();

      sb.append(strs.get(0));

      for (i = 1; i < len; i++) {
        sb.append(',');
        sb.append(strs.get(i));
      }

      gs = sb.toString();
      e  = changeValue(pane, set.getEx(group - 1), gs, true);
    }

    if ((del != null) && (e != null)) {
      DesignCompoundEdit edits = new DesignCompoundEdit(pane);

      edits.addEdit(del);
      edits.addEdit(e);

      return edits;
    }

    return (del == null)
           ? e
           : del;
  }

  static DesignCompoundEdit removeRegions(DesignPane pane, iContainer p, SPOTSet set, int position, boolean row) {
    DesignCompoundEdit edits = new DesignCompoundEdit(pane);
    int                len   = (set == null)
                               ? 0
                               : set.size();
    Region             r;

    for (int i = len - 1; i >= 0; i--) {
      r = (Region) set.getEx(i);

      if ((row && (r.getY() == position)) || (!row && (r.getX() == position))) {
        set.remove(i);
        edits.addEdit(new SPOTSetUndoableEdit(pane, set, i, r));
      }
    }

    return edits;
  }

  static DesignCompoundEdit removeWidgets(DesignPane pane, iContainer p, SPOTSet set, int position, boolean row) {
    DesignCompoundEdit edits = new DesignCompoundEdit(pane);
    int                len   = (set == null)
                               ? 0
                               : set.size();
    Widget             wc;
    Location           loc = new Location();
    SPOTSequence       seq = (SPOTSequence) p.getLinkedData();

    for (int i = len - 1; i >= 0; i--) {
      wc = (Widget) set.getEx(i);
      loc.setLocation(wc, i);

      if ((row && (loc.getY() == position)) || (!row && (loc.getX() == position))) {
        set.remove(i);
        edits.addEdit(new AddRemoveUndoableEdit(pane, seq, wc, true, loc));
        loc = new Location();
      }
    }

    return edits;
  }

  static String resolveAttribute(iSPOTElement e, String name, Object object) {
    String value = (object == null)
                   ? null
                   : object.toString();
    String ov    = e.spot_getAttribute(name);

    if ((ov != null) && (ov.length() == 0)) {
      ov = null;
    }

    if ((value != null) && (value.length() == 0)) {
      value = null;
    }

    boolean change = false;

    if (value == null) {
      change = ov != null;
    } else {
      change = !value.equals(ov);
    }

    return change
           ? value
           : NO_CHANGE;
  }
  static Region findRegion(SPOTSet set, Widget w) {
    for(Object o:set) {
      Region r=(Region)o;
      if(r.viewer.getValue()==w) {
        return r;
      }
    }
    return null;
  }
  static DesignEdit swapWidgets(DesignPane pane,  SPOTSequence p1, SPOTSequence p2,Widget wc1, Widget wc2, boolean undoable) {
    if (p1 !=p2) {
      return swapWidgetsDifferentParent(pane, p1,p2,wc1, wc2, undoable);
    }


    switch(getWidgetType(p1)) {
      case Form :
      case GroupBox :
        GroupBox gb=(GroupBox)p1;
        if(gb.layout.intValue()==GroupBox.CLayout.flow) {
          if(!swap(gb.widgets,wc1,wc2)) {
            return null;
          }
        }
        else {
          String x1 = wc1.bounds.x.getValue();
          String y1 = wc1.bounds.y.getValue();
  
          wc1.bounds.x.setValue(wc2.bounds.x.getValue());
          wc1.bounds.y.setValue(wc2.bounds.y.getValue());
          wc2.bounds.x.setValue(x1);
          wc2.bounds.y.setValue(y1);
        }
        if (undoable) {
          return new SwapUndoableEdit(pane, p1,p2,wc1, wc2);
        }
        break;

      case GridPane :
      case SplitPane : {
        SPOTSet set;
        if(p1 instanceof GridPane) {
          set=((GridPane)p1).regions;
        }
        else {
          set=((SplitPane)p1).regions;
        }
        Region              r1   = findRegion(set,wc1);
        Region              r2   = findRegion(set,wc2);
        if(r1==null || r2==null) {
          return null;
        }
        SPOTPrintableString url1 = (SPOTPrintableString) r1.dataURL.clone();

        wc1 = (Widget) r1.viewer.getValue();
        r1.dataURL.spot_copy(r2.dataURL, false);
        r1.viewer.setValue(r2.viewer.getValue());
        r2.dataURL.spot_copy(url1, false);
        r2.viewer.setValue(wc1);

        if (undoable) {
          return new SwapUndoableEdit(pane, p1,p2,wc1, wc2);
        }

        break;
      }
      case ToolBar : {
        if(!swap(((ToolBar) p1).widgets,wc1,wc2)) {
          return null;
        }

        if (undoable) {
          return new SwapUndoableEdit(pane, p1,p2,wc1, wc2);
        }

        break;
      }

      case MenuBar :
        SPOTSet  set = ((MenuBar) p1).getPopupMenuReference();
        MenuItem m1  = (MenuItem) wc1.spot_getLinkedData();
        MenuItem m2  = (MenuItem) wc2.spot_getLinkedData();
        if(!swap(set,m1,m2)) {
          return null;
        }

        if (undoable) {
          return new SwapUndoableEdit(pane,p1,p2, wc1, wc2);
        }

        break;

      default :
        break;
    }

    return null;
  }
  private static boolean swap(SPOTSet set, iSPOTElement e1,iSPOTElement e2) {
    int      i1  = set.indexOfEx(e1);
    int      i2  = set.indexOfEx(e2);

    if ((i1 == -1) || (i2 == -1)) {
      return false;
    }

    set.set(i2, e1);
    set.set(i1, e2); 
    return true;
  }
  private static boolean addto(SPOTSet set, iSPOTElement e,int position) {
    int      n= set.indexOfEx(e);
    
    if(n!=-1) {
      if(n==position || (position==-1 && n==set.size()-1)) {
        return false;
      }
      set.remove(n);
    }
    if(position<0 || position>=set.size()) {
      set.add(e);
    }
    else {
      set.add(position,e);
    }
    return true;
  }
  
  static DesignEdit swapWidgetsDifferentParent(DesignPane pane, SPOTSequence p1, SPOTSequence p2,Widget wc1, Widget wc2, boolean undoable) {
    Location   loc1 = new Location();
    Location   loc2 = new Location();

    loc1.setLocation(wc1, -1);
    loc2.setLocation(wc2, -1);

    switch(getWidgetType(p1)) {
      case ToolBar :
        loc1.position = ((ToolBar) p1).widgets.indexOfEx(wc1);

        break;

      case SplitPane :
        loc1.position = getRegionIndex(((SplitPane) p1).regions,wc1);

        break;
      case GridPane :
        loc1.position = getRegionIndex(((GridPane) p1).regions,wc1);

        break;

      default :
        break;
    }
    switch(getWidgetType(p2)) {
    case ToolBar :
      loc2.position = ((ToolBar) p2).widgets.indexOfEx(wc2);

      break;

    case SplitPane :
      loc2.position = getRegionIndex(((SplitPane) p2).regions,wc2);

      break;
    case GridPane :
      loc2.position = getRegionIndex(((GridPane) p2).regions,wc2);

      break;

    default :
      break;
  }


    DesignCompoundEdit edits = new DesignCompoundEdit(pane);
    UndoableEdit       e     = removeWidget(pane, p1, null, wc1, undoable);

    if (e != null) {
      edits.addEdit(e);
    }

    e = removeWidget(pane, (SPOTSequence) p2, null, wc2, undoable);

    if (e != null) {
      edits.addEdit(e);
    }

    e = addWidget(pane, (SPOTSequence) p1, wc2, loc1, undoable);

    if (e != null) {
      edits.addEdit(e);
    }

    e = addWidget(pane, (SPOTSequence) p2, wc1, loc2, undoable);

    if (e != null) {
      edits.addEdit(e);
    }

    if (!edits.hasEdits()) {
      return null;
    }

    return undoable
           ? edits
           : null;
  }

  static void setAsSpacer(DesignPane pane, iWidget parent, int pos, boolean row, boolean small) {
    String             value = null;
    String             name;
    DesignUndoableEdit e;

    switch(parent.getWidgetType()) {
      case Form :
      case GroupBox :
        GroupBox   gb = (GroupBox) parent.getLinkedData();
        FormLayout fl = new FormLayout(gb.columns.getValue(), gb.rows.getValue());

        if (row) {
          fl.setRowSpec(pos + 1, RowSpec.decode(small
                  ? smallSpacer
                  : spacer));
          name  = "rows";
          value = FormsPanel.getRows(fl, -1);
        } else {
          fl.setColumnSpec(pos + 1, ColumnSpec.decode(small
                  ? smallSpacer
                  : spacer));
          name  = "columns";
          value = FormsPanel.getColumns(fl, -1);
        }

        e = changeValue(pane, (SPOTSequence) parent.getLinkedData(), name, value);

        if (e != null) {
          e.setReloadRequired(true);
          pane.addUndoableEdit(e, false, null);
          pane.requestReload(null);
        }

        break;

      default :
        break;
    }
  }

  static iSPOTElement getElement(SPOTSequence w, List<String> path) {
    iSPOTElement e = w;

    for (String s : path) {
      e = ((SPOTSequence) e).spot_elementForEx(s);
    }

    return e;
  }

  private static void changeValue(DesignPane pane, iSPOTElement e, String value, DesignCompoundEdit edits) {
    if ((e != null) && (value != null)) {
      String ov = e.spot_stringValue();

      if (!value.equals(ov)) {
        e.spot_setValue(value);
        edits.addEdit(new SPOTUndoableEdit(pane, e, ov, value));
      }
    }
  }

  private static void checkLayout(GroupBox gb, String sx, String sy, boolean add) {
    do {
      if ((sx == null) || (sy == null)) {
        break;
      }

      if (gb.layout.intValue() != GroupBox.CLayout.forms) {
        break;
      }

      sx = sx.trim();
      sy = sy.trim();

      int          x    = SNumber.intValue(sx);
      int          y    = SNumber.intValue(sy);
      List<String> rows = CharScanner.getTokens(gb.rows.getValue(), ',', true);
      List<String> cols = CharScanner.getTokens(gb.columns.getValue(), ',', true);

      if (y<0 || rows.size() <= y) {
        break;
      }

      if (x<0 || cols.size() <= x) {
        break;
      }

      if (add) {
        if (cols.get(x).toLowerCase(Locale.ENGLISH).endsWith("dlux")) {
          cols.set(x, "d");
          gb.columns.setValue(Helper.toString(cols, ","));
        }

        if (rows.get(y).toLowerCase(Locale.ENGLISH).endsWith("dluy")) {
          rows.set(y, "d");
          gb.rows.setValue(Helper.toString(rows, ","));
        }
      } else {
        if (cols.get(x).equalsIgnoreCase("d") &&!hasComponentsInColumn(gb, sx)) {
          cols.set(x, "14dlux");
          gb.columns.setValue(Helper.toString(cols, ","));
        }

        if (rows.get(y).equalsIgnoreCase("d") &&!hasComponentsInRow(gb, sy)) {
          rows.set(y, "14dluy");
          gb.rows.setValue(Helper.toString(rows, ","));
        }
      }
    } while(false);
  }

  private static int getRegionIndex(SPOTSet set, Widget wc) {
    Region r;
    int    len = (set == null)
                 ? 0
                 : set.size();

    for (int i = 0; i < len; i++) {
      r = (Region) set.get(i);

      if (r.viewer.getValue() == wc) {
        return i;
      }
    }

    return -1;
  }

  private static WidgetType getWidgetType(SPOTSequence seq) {
    if(seq instanceof MainWindow) {
      return WidgetType.Window;
    }
    try {
      return WidgetType.valueOf(seq.spot_getClassShortName());
    } catch(Exception e) {
      
      return WidgetType.Custom;
    }
  }

  private static boolean hasComponentsInColumn(GroupBox gb, String sx) {
    SPOTSet set = gb.widgets;
    int     len = set.getCount();

    for (int i = 0; i < len; i++) {
      Widget w = (Widget) set.getEx(i);
      String x = w.bounds.x.getValue();

      if ((x != null) && x.trim().equals(sx)) {
        return true;
      }
    }

    return false;
  }

  private static boolean hasComponentsInRow(GroupBox gb, String sy) {
    SPOTSet set = gb.widgets;
    int     len = set.getCount();

    for (int i = 0; i < len; i++) {
      Widget w = (Widget) set.getEx(i);
      String y = w.bounds.y.getValue();

      if ((y != null) && y.trim().equals(sy)) {
        return true;
      }
    }

    return false;
  }

  static class AddRemoveUndoableEdit extends DesignUndoableEdit {
    Widget       addedWidget;
    Location     location;
    SPOTSequence parent;
    boolean      remove;
    Widget       widget;

    public AddRemoveUndoableEdit(DesignPane pane, SPOTSequence parent, Widget widget, boolean remove, Location loc) {
      super(pane);
      this.remove   = remove;
      this.parent   = parent;
      this.widget   = widget;
      this.location = loc;

      if (!remove) {
        addedWidget = widget;
      }

      this.setReloadRequired(true);
    }

    public void redo() throws CannotRedoException {
      remove = !remove;

      try {
        doTheDo();
        super.redo();
      } finally {
        remove = !remove;
      }
    }

    public void undo() throws CannotUndoException {
      super.undo();
      doTheDo();
    }

    public Widget getAddedWidget() {
      return addedWidget;
    }

    void doTheDo() throws CannotUndoException {
      if (remove) {
        addWidget(pane, parent, widget, location, false);
        addedWidget = widget;
      } else {
        removeWidget(pane, parent, null, widget, false);
        addedWidget = null;
      }

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }
  }


  static class AttributeUndoableEdit extends DesignUndoableEdit {
    String       attributeName;
    iSPOTElement element;
    String       newValue;
    String       oldValue;

    public AttributeUndoableEdit(DesignPane pane, iSPOTElement e, String name, String ov, String nv) {
      super(pane);
      element       = e;
      attributeName = name;
      oldValue      = ov;
      newValue      = nv;
    }

    public void redo() throws CannotRedoException {
      element.spot_setAttribute(attributeName, newValue);
      element.spot_cleanAttributes();
      super.redo();
    }

    public void undo() throws CannotUndoException {
      element.spot_setAttribute(attributeName, oldValue);
      element.spot_cleanAttributes();
      super.undo();
    }
  }


  static class InsertUndoableEdit extends DesignUndoableEdit {
    int     newPosition;
    int     oldPosition;
    iWidget widget;

    private InsertUndoableEdit(DesignPane pane, iWidget w, int npos, int opos) {
      super(pane);
      this.widget = w;
      newPosition = npos;
      oldPosition = opos;
      this.setReloadRequired(true);
    }

    public void redo() throws CannotRedoException {
      changeConstraints(pane, widget, newPosition, false);
      super.redo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }

    public void undo() throws CannotUndoException {
      changeConstraints(pane, widget, oldPosition, false);
      super.undo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }
  }


  static class RegionComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      Region  r1   = (Region) o1;
      Region  r2   = (Region) o2;
      SNumber num1 = new SNumber(r1.y.stringValue() + "." + r1.x.stringValue());
      SNumber num2 = new SNumber(r2.y.stringValue() + "." + r2.x.stringValue());

      return num1.compareTo(num2);
    }
  }


  static class SPOTReferenceDeleteUndoableEdit extends DesignUndoableEdit {
    int          index;
    String       name;
    iSPOTElement oldValue;
    SPOTSequence parent;

    private SPOTReferenceDeleteUndoableEdit(DesignPane pane, SPOTSequence parent, String name, iSPOTElement ov) {
      super(pane);
      oldValue    = ov;
      this.parent = parent;
      this.name   = name;
      this.setReloadRequired(true);
    }

    public void redo() throws CannotRedoException {
      parent.spot_setReferenceVariable(name, null);
      super.redo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }

    public void undo() throws CannotUndoException {
      parent.spot_setReferenceVariable(name, oldValue);
      super.undo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }
  }

  public static void addEditForElementSwap(DesignPane pane,SPOTSet set,int fromIndex, int toIndex) {
    iSPOTElement nv=set.getEx(fromIndex);
    iSPOTElement ov=set.getEx(toIndex);
    DesignCompoundEdit edits=new DesignCompoundEdit(pane);
    edits.addEdit(new SPOTSetUndoableEdit(pane, set, toIndex, ov, nv));
    edits.addEdit(new SPOTSetUndoableEdit(pane, set, fromIndex, nv, ov));
    pane.addUndoableEdit(edits, false, null);
  }
  
  public static void addEditForElementDelete(DesignPane pane,SPOTSet set,int index) {
    iSPOTElement ov=set.getEx(index);
    pane.addUndoableEdit(new SPOTSetUndoableEdit(pane, set, index, ov), false, null);
  }
  
  public static void addEditForElementInsert(DesignPane pane,SPOTSet set,int index,iSPOTElement nv) {
    pane.addUndoableEdit(new SPOTSetUndoableEdit(pane, set, index, null,nv), false, null);
  }
  
  static class SPOTSetUndoableEdit extends DesignUndoableEdit {
    SPOTSet      element;
    int          index;
    iSPOTElement newValue;
    iSPOTElement oldValue;

    private SPOTSetUndoableEdit(DesignPane pane, SPOTSet set, int index, iSPOTElement ov) {
      super(pane);
      this.index = index;
      element    = set;
      oldValue   = ov;
    }

    private SPOTSetUndoableEdit(DesignPane pane, SPOTSet set, int index, iSPOTElement ov, iSPOTElement nv) {
      super(pane);
      this.index = index;
      element    = set;
      oldValue   = ov;
      newValue   = nv;
    }

    public void redo() throws CannotRedoException {
      if (oldValue == null) {
        element.add(index, newValue);
      } else if (newValue != null) {
        element.set(index, newValue);    // was a swap or add
      } else {
        element.remove(index);           // was a delete
      }

      super.redo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }

    public void undo() throws CannotUndoException {
      if (oldValue == null) {            // was an add
        element.remove(index);
      } else if (newValue != null) {
        element.set(index, oldValue);    // was a swap
      } else {
        element.add(index, oldValue);    // was a delete
      }

      super.undo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }
  }


  static class SPOTUndoableEdit extends DesignUndoableEdit {
    iSPOTElement element;
    Object       newValue;
    Object       oldValue;

    public SPOTUndoableEdit(DesignPane pane, iSPOTElement e, iSPOTElement ov, iSPOTElement nv) {
      super(pane);
      element  = e;
      oldValue = ov;
      newValue = nv;
    }

    public SPOTUndoableEdit(DesignPane pane, iSPOTElement e, String ov, String nv) {
      super(pane);
      element  = e;
      oldValue = ov;
      newValue = nv;
    }

    public void redo() throws CannotRedoException {
      if (element instanceof SPOTAny) {
        changeValue(pane, element, (iSPOTElement) newValue, false);
      } else if (newValue instanceof String) {
        element.spot_setValue((String) newValue);
      } else {
        if (newValue == null) {
          element.spot_clear();
        } else {
          element.spot_copy((iSPOTElement) newValue, false);
        }
      }

      super.redo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }

    public void undo() throws CannotUndoException {
      if (element instanceof SPOTAny) {
        changeValue(pane, element, (iSPOTElement) oldValue, false);
      } else if (oldValue instanceof String) {
        element.spot_setValue((String) oldValue);
      } else {
        if (oldValue == null) {
          element.spot_clear();
        } else {
          element.spot_copy((iSPOTElement) oldValue, false);
        }
      }

      super.undo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }
  }


  static class SwapUndoableEdit extends DesignUndoableEdit {
    Widget widget1;
    Widget widget2;
    SPOTSequence parent1;
    SPOTSequence parent2;
    private SwapUndoableEdit(DesignPane pane, SPOTSequence p1, SPOTSequence p2,Widget w1, Widget w2) {
      super(pane);
      this.widget1 = w1;
      this.widget2 = w2;
      this.pane    = pane;
      this.parent1=p1;
      this.parent2=p2;
      this.setReloadRequired(true);
    }

    public void redo() throws CannotRedoException {
      swapWidgets(pane, parent1,parent2,widget1, widget2, false);
      super.redo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }

    public void undo() throws CannotUndoException {
      swapWidgets(pane, parent1,parent2,widget1, widget2, false);
      super.undo();

      if (isReloadRequired()) {
        pane.requestReload(null);
      }
    }
  }
}
