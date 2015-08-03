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

package com.appnativa.rare.viewer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.Form;
import com.appnativa.rare.spot.GroupBox;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.AbsolutePanel;
import com.appnativa.rare.ui.FlowPanel;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aAbsolutePanel;
import com.appnativa.rare.ui.aFormsPanel;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.iCustomLayoutManager;
import com.appnativa.rare.ui.iCustomLayoutManager.iConstraintsConverter;
import com.appnativa.rare.ui.iFormsPanel;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.LineWidget;
import com.appnativa.rare.widget.aGroupableButton;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.IntList;
import com.appnativa.util.SNumber;

import com.jgoodies.forms.layout.CellConstraints;

import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A widget that provides a visual grouping of other widgets.
 *
 * @author Don DeCoteau
 */
public abstract class aGroupBoxViewer extends aContainer {

  /** specified that widgets will be created with events disabled */
  protected boolean disableWidgetEvents;

  /** the form layout */
  protected Layout formLayout;

  /** whether the form has files that require MIME multi-part encoding */
  protected boolean hasMultipartContent;

  /** the layout object */
  protected Object layoutObject;

  /** list of files to be submitted with a form */
  protected List<String> submissionFiles;

  /** the name of the filed that represents the files to be submitted */
  protected String submissionFilesField;

  /** the submission type of the group box's value */
  protected int   submitValueType;
  private boolean changeEventsEnabled = true;

  /**
   * Constructs a new instance
   */
  public aGroupBoxViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aGroupBoxViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.GroupBox;
  }

  /**
   * Adds and configures widgets to the specified panel
   *
   * @param parent
   *          the parent of the widgets to be added
   * @param panel
   *          the panel to add the widgets to
   * @param info
   *          the information about the widgets
   *
   * @return a point representing the number of rows and columns that were
   *         utilized for the widgets
   */
  public static UIPoint addAndConfigureWidgets(iContainer parent, iParentComponent panel, FormInfo info) {
    Widget          wc;
    Layout          formLayout = info.formLayout;
    SPOTSet         widgets    = info.widgets;
    int             rspace     = info.rowSpacing;
    int             cspace     = info.columnSpacing;
    int[][]         rg         = info.rowGrouping;
    int[][]         cg         = info.columnGrouping;
    SPOTSet         painters   = info.painters;
    int             len        = (widgets == null)
                                 ? 0
                                 : widgets.size();
    CellConstraints cc;
    iWidget         w;
    iFormsPanel     fp   = (panel instanceof iFormsPanel)
                           ? (iFormsPanel) panel
                           : null;
    IntList         cols = null,
                    rows = null;

    if (formLayout == Layout.FORMS) {
      cols = new IntList();
      rows = new IntList();
    }

    for (int i = 0; i < len; i++) {
      wc = (Widget) widgets.getEx(i);
      w  = parent.addWidget(panel, wc, formLayout);
      cc = null;

      if (formLayout == Layout.FORMS) {
        cc = fp.getCellConstraints(w.getContainerComponent());
        cols.add(cc.gridX - 1);
        rows.add(cc.gridY - 1);
      }
    }

    if (formLayout == Layout.FORMS) {
      if (rg != null) {
        fp.setRowGroups(rg);
      }

      if (cg != null) {
        fp.setColumnGroups(cg);
      }

      fp.setCellPainters(Utils.configureCellPainters(parent, painters));

      return new UIPoint(fp.getColumnCount(), fp.getRowCount());
    } else if ((formLayout == Layout.TABLE) && (parent instanceof aGroupBoxViewer)) {
      UIPoint p = addTableWidgets(fp, (ArrayList<ComponentCC>) info.layoutObject, false);

      fp.addRowSpacing(rspace);
      fp.addColumnSpacing(cspace);

      return p;
    } else {
      return new UIPoint(0, 0);
    }
  }

  /**
   * Adds a table layout widget
   *
   * @param widget
   *          the widget. This can be null when you don't have a widget but do
   *          have a configuration
   * @param comp
   *          the component (can be null)
   * @param cfg
   *          the configuration
   * @param list
   *          to hold the component constraints info
   * @return the cell constraints
   */
  public static CellConstraints addTableWidget(iWidget widget, iPlatformComponent comp, Widget cfg,
          ArrayList<ComponentCC> list) {
    UIRectangle tas;

    if (list.size() > 0) {
      tas = list.get(0).tas;

      if (list.get(0).cc == null) {
        list.clear();
      }
    } else {
      tas = new UIRectangle(0, 0, 1, 1);
    }

    String sx = cfg.bounds.x.getValue();
    String sy = cfg.bounds.y.getValue();
    int    x  = cfg.bounds.getX();
    int    y  = cfg.bounds.getY();

    if (y == -1) {
      y = (int) tas.y;
    } else if (sy.startsWith("+")) {
      y = (int) tas.y + y;
    }

    if (x == -1) {
      x = (int) tas.x;

      if (x >= tas.width) {
        x = 0;
        y++;
      }
    } else if (sx.startsWith("+")) {
      x = (int) tas.x + x;
    }

    if (x >= tas.width) {
      tas.width = x + 1;
    }

    if (y >= tas.height) {
      tas.height = y + 1;
    }

    tas.x = x;
    tas.y = y;

    CellConstraints cc = Utils.createCellConstraints(cfg, new CellConstraints(), tas);

    tas.x = cc.gridX;    // createCellConstraints automatically increments;

    ComponentCC ccc = new ComponentCC(comp, cc);

    ccc.tas = tas;
    list.add(ccc);

    return cc;
  }

  /**
   * Creates constraints that can be used when adding a widget to the form
   *
   * @param x
   *          the x-position
   * @param y
   *          the y-position
   *
   * @return a constraint object
   */
  public Object createCellConstraints(int x, int y) {
    return createCellConstraints(x, y, 1, 1, HorizontalAlign.AUTO, VerticalAlign.AUTO);
  }

  /**
   * Creates constraints that can be used when adding a widget to the form
   *
   * @param x
   *          the x-position
   * @param y
   *          the y-position
   *
   * @param width
   *          the width
   * @param height
   *          the height
   * @return a constraint object
   */
  public Object createCellConstraints(int x, int y, int width, int height) {
    return createCellConstraints(x, y, width, height, HorizontalAlign.AUTO, VerticalAlign.AUTO);
  }

  /**
   *
   * Creates constraints that can be used when adding a widget to the form
   *
   * @param x
   *          the x-position
   * @param y
   *          the y-position
   *
   * @param colSpan
   *          the number of columns to span
   * @param rowSpan
   *          the number of rows to span
   * @param ha
   *          the horizontal alignment
   * @param va
   *          the vertical alignment
   * @return a constraint object
   */
  public Object createCellConstraints(int x, int y, int colSpan, int rowSpan, HorizontalAlign ha, VerticalAlign va) {
    if (formLayout == Layout.ABSOLUTE) {
      return Utils.createAbsoluteConstraints(x, y, colSpan, rowSpan);
    }

    return Utils.createCellConstraints(x, y, colSpan, rowSpan, ha, va);
  }

  /**
   * Adds widgets to a panel using a table layout
   *
   * @param panel
   *          the panel
   * @param list
   *          the list of components and constraints
   * @return a point representing the number of rows and columns that were
   *         utilized for the widgets
   */
  public static UIPoint addTableWidgets(iFormsPanel panel, ArrayList<ComponentCC> list, boolean fillRows) {
    int             len = list.size();
    CellConstraints cc;
    ComponentCC     ccc;
    int             n;
    int             rows = 0;
    int             cols = 0;

    for (int i = 0; i < len; i++) {
      ccc = list.get(i);
      cc  = ccc.cc;

      if (cc == null) {
        return new UIPoint(0, 0);    // no widgets
      }

      n = cc.gridY + cc.gridHeight - 1;

      if (n > rows) {
        rows = n;
      }

      n = cc.gridX + cc.gridWidth - 1;

      if (n > cols) {
        cols = n;
      }
    }

    boolean[] crows = new boolean[rows];
    boolean[] ccols = new boolean[cols];

    for (int i = 0; i < len; i++) {
      ccc = list.get(i);
      cc  = ccc.cc;

      if (cc.vAlign.abbreviation() == 'f') {
        n = cc.gridY + cc.gridHeight - 2;

        if (n < rows) {
          crows[n] = true;
        }
      }

      if (cc.hAlign.abbreviation() == 'f') {
        n = cc.gridX + cc.gridWidth - 2;

        if (n < cols) {
          ccols[n] = true;
        }
      }
    }

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < rows; i++) {
      if (i != 0) {
        sb.append(',');
      }

      if (fillRows) {
        if (crows[i]) {
          sb.append("FILL:DEFAULT:GROW");
        } else {
          sb.append("FILL:DEFAULT:NONE");
        }
      } else {
        if (crows[i]) {
          sb.append("CENTER:DEFAULT:GROW");
        } else {
          sb.append("CENTER:DEFAULT:NONE");
        }
      }
    }

    String rstr = sb.toString();

    sb.setLength(0);

    for (int i = 0; i < cols; i++) {
      if (i != 0) {
        sb.append(',');
      }

      if (ccols[i]) {
        sb.append("FILL:DEFAULT:GROW");
      } else {
        sb.append("FILL:DEFAULT:NONE");
      }
    }

    String cstr = sb.toString();

    panel.setLayout(cstr, rstr);

    for (int i = 0; i < len; i++) {
      ccc = list.get(i);

      if (ccc.component != null) {
        try {
          panel.add(ccc.component, ccc.cc);
        } catch(Exception e) {
          e.printStackTrace();

          return new UIPoint(cols, rows);
        }
      }
    }

    return new UIPoint(cols, rows);
  }

  public static void adjustCellConstraintsForWidgetType(CellConstraints cc, iWidget widget) {
    if (cc.hAlign == CellConstraints.DEFAULT) {
      switch(widget.getWidgetType()) {
        case PushButton :
        case Navigator :
          cc.hAlign = CellConstraints.CENTER;

          break;

        case Line :
          if (((LineWidget) widget).isHorizontal()) {
            cc.hAlign = CellConstraints.FILL;
          }

          break;

        case Bean :
          cc.hAlign = CellConstraints.FILL;

          break;

        default :
          if (widget instanceof iViewer) {
            cc.hAlign = CellConstraints.FILL;
          }

          break;
      }
    }

    if (cc.vAlign == CellConstraints.DEFAULT) {
      switch(widget.getWidgetType()) {
        case Line :
          if (!((LineWidget) widget).isHorizontal()) {
            cc.vAlign = CellConstraints.FILL;
          }

          break;

        default :
          if (widget instanceof iViewer) {
            cc.vAlign = CellConstraints.FILL;
          }

          break;
      }
    }
  }

  @Override
  public void configure(Viewer vcfg) {
    vcfg = checkForURLConfig(vcfg);
    configureEx((GroupBox) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Moves a widget to a speified location in the form
   *
   * @param widget
   *          the widget to move
   * @param x
   *          the x-position for the widget
   * @param y
   *          the y-position for the widget
   * @param colSpan
   *          the number of columns to use
   * @param rowSpan
   *          the number of rows to use
   *
   * @throws ApplicationException
   *           if the specified widget is not a child of this viewer
   */
  public void moveWidget(iWidget widget, int x, int y, int colSpan, int rowSpan) {
    if (widget.getParent() != this) {
      throw new ApplicationException(Platform.getResourceAsString("Rare.text.notMyChild"));
    }

    switch(formLayout) {
      case ABSOLUTE :
        aAbsolutePanel ap = (aAbsolutePanel) dataComponent;
        UIRectangle    r  = (UIRectangle) ap.getComponentConstraints(widget.getContainerComponent());

        if (r != null) {
          r.setLocation(x, y);
        }

        break;

      case TABLE :
      case FORMS :
        aFormsPanel     fp = (aFormsPanel) dataComponent;
        CellConstraints cc = (CellConstraints) fp.getComponentConstraints(widget.getContainerComponent());

        fp.getFormLayout().removeLayoutComponent(widget.getContainerComponent());
        cc.gridX      = x;
        cc.gridY      = y;
        cc.gridWidth  = colSpan;
        cc.gridHeight = rowSpan;
        fp.getFormLayout().addLayoutComponent(widget.getContainerComponent(), cc);

        break;

      default :
        break;
    }
  }

  @Override
  public boolean requestFocus() {
    if (isDesignMode()) {
      return true;
    }

    if (dataComponent instanceof iFormsPanel) {
      iFormsPanel fp   = (iFormsPanel) dataComponent;
      int         rows = fp.getRowCount();
      int         cols = fp.getColumnCount();
      iWidget     w;

      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          iPlatformComponent pc = fp.getGridComponentAt(c, r);

          w = (pc == null)
              ? null
              : pc.getWidget();

          if ((w != null) && w.requestFocus()) {
            return true;
          }
        }
      }

      return false;
    }

    return true;
  }

  @Override
  public void reset() {
    resetForm();
  }

  /**
   * Resets the form Synonym for resetForm()
   */
  @Override
  public void resetForm() {
    int len = widgetList.size();

    for (int i = 0; i < len; i++) {
      widgetList.get(i).reset();
    }
  }

  @Override
  public void writeHTTPContent(boolean first, Writer writer, String boundary) {
    switch(submitValueType) {
      case GroupBox.CSubmitValue.viewer_linked_data :
      case GroupBox.CSubmitValue.viewer_value :
        super.writeHTTPContent(first, writer, boundary);

        return;
    }

    int     len = widgetList.size();
    iWidget a;

    for (int i = 0; i < len; i++) {
      a = widgetList.get(i);

      if (a.isSubmittable() && a.isValidForSubmission(true)) {
        a.writeHTTPContent(first, writer, boundary);
      }
    }
  }

  @Override
  public boolean writeHTTPValue(boolean first, Writer writer) {
    switch(submitValueType) {
      case GroupBox.CSubmitValue.viewer_linked_data :
      case GroupBox.CSubmitValue.viewer_value :
        return super.writeHTTPValue(first, writer);
    }

    int     len = widgetList.size();
    iWidget a;

    for (int i = 0; i < len; i++) {
      a = widgetList.get(i);

      if (a.isSubmittable() && a.isValidForSubmission(true)) {
        if (a.writeHTTPValue(first, writer)) {
          first = false;
        }
      }
    }

    return !first;
  }

  @Override
  public boolean writeJSONValue(boolean first, Writer writer) {
    try {
      int     len = widgetList.size();
      iWidget a;

      for (int i = 0; i < len; i++) {
        a = widgetList.get(i);

        if (a.isSubmittable() && a.isValidForSubmission(true)) {
          if (a.writeJSONValue(first, writer)) {
            first = false;
          }
        }
      }

      writer.write("\n}");

      return !first;
    } catch(IOException e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  /**
   * Sets whether selection changes will cause events to be fired when the
   * widget's value changes
   *
   * @param enabled
   *          true to enable; false to disable
   */
  public void setChangeEventsEnabled(boolean enabled) {
    changeEventsEnabled = enabled;
  }

  /**
   * Sets whether widgets will be created with their event handling disabled
   *
   * @param disable
   *          true if widgets will be created with their event handling
   *          disabled; false otherwise
   */
  public void setDisableWidgetEvents(boolean disable) {
    this.disableWidgetEvents = disable;
  }

  /**
   * Sets a list of files that are to be submitted along with the form
   *
   * @param name
   *          the field name for the files
   * @param submissionFiles
   *          the list of files to submit
   */
  public void setSubmissionFiles(String name, List<String> submissionFiles) {
    submissionFilesField = name;
    this.submissionFiles = submissionFiles;
    hasMultipartContent  = true;
  }

  @Override
  public void setValue(Object value) {
    if (value != theValue) {
      Object oldValue = theValue;

      theValue = value;

      aWidgetListener wl = this.getWidgetListener();

      if (isChangeEventsEnabled() && (wl != null) && wl.isEnabled(iConstants.EVENT_CHANGE)) {
        ItemChangeEvent e = new ItemChangeEvent(this.getContainerComponent(), oldValue, theValue);

        wl.itemChanged(e);

        if (e.isRejected()) {
          theValue = oldValue;
        }
      }
    }
  }

  public Layout getFormLayout() {
    return formLayout;
  }

  @Override
  public Object getHTTPFormValue() {
    switch(submitValueType) {
      case GroupBox.CSubmitValue.viewer_linked_data :
        return getLinkedData();

      case GroupBox.CSubmitValue.viewer_value :
        return toString(this);
    }

    return getHTTPValuesHash();
  }

  @Override
  public Object getSelection() {
    iWidget w     = null;
    iWidget rbw   = null;
    int     len   = widgetList.size();
    String  gname = null;

    for (int i = 0; i < len; i++) {
      w = widgetList.get(i);

      if (w.getWidgetType() != WidgetType.RadioButton) {
        continue;
      }

      if ((gname != null) &&!gname.equals(((aGroupableButton) w).getGroupName())) {
        rbw = null;

        break;
      }

      rbw = w;

      if (gname == null) {
        gname = ((aGroupableButton) w).getGroupName();
      }
    }

    if (rbw != null) {
      return ((aGroupableButton) rbw).getSelectedButton();
    }

    return null;
  }

  /**
   * Gets a list of files that will be submitted with the form
   *
   * @return a list of files that will be submitted with the form
   */
  public List<String> getSubmissionFiles() {
    return submissionFiles;
  }

  /**
   * Gets the name of the field for the submission files
   *
   * @return the name of the field for the submission files
   */
  public String getSubmissionFilesField() {
    return submissionFilesField;
  }

  @Override
  public Object getValue() {
    return theValue;
  }

  @Override
  public int getWidgetCount() {
    return widgetList.size();
  }

  /**
   * Gets whether selection changes will cause events to be fired when the
   * widget's value changes
   *
   * @return true for enabled; false for disabled
   */
  public boolean isChangeEventsEnabled() {
    return changeEventsEnabled;
  }

  /**
   * Returns whether widgets will be created with their event handling disabled
   *
   * @return true if widgets will be created with their event handling disabled;
   *         false otherwise
   */
  public boolean isDisableWidgetEvents() {
    return disableWidgetEvents;
  }

  @Override
  public boolean isValidForSubmission(boolean showerror) {
    if (!super.isValidForSubmission(showerror)) {
      return false;
    }

    switch(submitValueType) {
      case GroupBox.CSubmitValue.viewer_linked_data :
        return getLinkedData() != null;

      case GroupBox.CSubmitValue.viewer_value :
        return getValue() != null;
    }

    return false;
  }

  static int[][] createGrouping(SPOTSet set) {
    int[][] a   = null;
    int     len = (set == null)
                  ? 0
                  : set.size();

    if (len > 0) {
      a = new int[len][];

      for (int i = 0; i < len; i++) {
        a[i] = SNumber.parseIntegers(set.stringValueAt(i));
      }
    }

    return a;
  }

  static CellConstraints getLastCC(ArrayList<ComponentCC> list) {
    int         len = list.size();
    ComponentCC c;

    for (int i = len - 1; i >= 0; i--) {
      c = list.get(i);

      if (c.widget) {
        return c.cc;
      }
    }

    return null;
  }

  /**
   * This method adds a widget to an absolute layout panel
   *
   * @param panel
   *          the panel to add the widget to.
   * @param widget
   *          the widget
   * @param cfg
   *          the widget configuration
   */
  protected void addAbsoluteWidget(iParentComponent panel, iWidget widget, Widget cfg) {
    int x = cfg.bounds.getXPixels();
    int y = cfg.bounds.getYPixels();
    int w = cfg.bounds.getWidthPixels(panel);
    int h = cfg.bounds.getHeightPixels(panel);

    if (panel != null) {    // panel will be null if we just want to create a
      // widget and not add it to any panel yet (e.g. popup
      // widget )
      if ((x == -1) && (y == -1)) {
        panel.add(widget.getContainerComponent(), createAbsoluteLayoutConstraints(x, y, 0, 0));
      } else {
        panel.add(widget.getContainerComponent(), createAbsoluteLayoutConstraints(x, y, w, h));
      }
    }
  }

  /**
   * This method adds a widget to the specified panel
   *
   * @param panel
   *          the panel to add the widget to.
   * @param widget
   *          the widget
   * @param cfg
   *          the widget configuration
   * @param cvt
   *          the constraints converter
   */
  protected void addCustomWidget(iParentComponent panel, iWidget widget, Widget cfg,
                                 iCustomLayoutManager.iConstraintsConverter cvt) {
    Object constraints = (cvt == null)
                         ? null
                         : cvt.getConstraints(cfg);

    if (panel != null) {    // panel will be null if we just want to create a
      // widget and not add it to any panel yet (e.g. popup
      // widget )
      if (constraints == null) {
        panel.add(widget.getContainerComponent());
      } else {
        panel.add(widget.getContainerComponent(), constraints);
      }
    }
  }

  /**
   * This method adds a widget to an absolute layout panel
   *
   * @param panel
   *          the panel to add the widget to.
   * @param widget
   *          the widget
   * @param cfg
   *          the widget configuration
   */
  protected void addFlowWidget(iParentComponent panel, iWidget widget, Widget cfg) {
    if (panel != null) {    // panel will be null if we just want to create a
      // widget and not add it to any panel yet (e.g. popup
      // widget )
      panel.add(widget.getContainerComponent(), createFlowLayoutConstraints(cfg));
    }
  }

  /**
   * This method adds a widget to the specified panel
   *
   * @param panel
   *          the panel to add the widget to.
   * @param widget
   *          the widget
   * @param cfg
   *          the widget's configuration
   * @param cc
   *          the cell constraints to reuse
   */
  protected void addFormsWidget(iFormsPanel panel, iWidget widget, Widget cfg, CellConstraints cc) {
    if (panel != null) {    // panel will be null if we just want to create a
      // widget and not add it to any panel yet (e.g. popup
      // widget )
      cc = Utils.createCellConstraints(cfg, cc, null);
      adjustCellConstraintsForWidgetType(cc, widget);

      int rows = panel.getRowCount();
      int cols = panel.getColumnCount();

      if (cc.gridX > cols) {
        cc.gridX = cols;
      }

      if (cc.gridY > rows) {
        cc.gridY = rows;
      }

      if (cc.gridWidth == -1) {
        cc.gridWidth = cols;
      }

      if (cc.gridHeight == -1) {
        cc.gridHeight = rows;
      }

      int d = rows - cc.gridY + 1;

      if (d < cc.gridHeight) {
        cc.gridHeight = d;
      }

      d = cols - cc.gridX + 1;

      if (d < cc.gridWidth) {
        cc.gridWidth = d;
      }

      try {
        panel.add(widget.getContainerComponent(), cc);
      } catch(Exception e) {
        throw DataParser.invalidConfigurationException(getAppContext(), e, cfg);
      }
    }
  }

  /**
   *
   * This method adds a widget to the specified panel
   *
   * @param panel
   *          the panel to add the widget to.
   * @param cfg
   *          the widget configuration
   * @param layout
   *          the form layout
   * @return the newly created widget
   *
   */
  @Override
  protected iWidget addWidgetEx(iParentComponent panel, Widget cfg, Layout layout) {
    iWidget a = createWidget(cfg);

    if (a == null) {
      return null;
    }

    if (a.isMultipartContent()) {
      hasMultipartContent = true;
    }

    switch(layout) {
      case ABSOLUTE :
        addAbsoluteWidget(panel, a, cfg);

        break;

      case TABLE :
        addTableWidget(a, a.getContainerComponent(), cfg, (ArrayList<ComponentCC>) layoutObject);

        break;

      case FORMS :
        addFormsWidget((iFormsPanel) panel, a, cfg, new CellConstraints());

        break;

      case CUSTOM :
        addCustomWidget(panel, a, cfg, (iConstraintsConverter) layoutObject);

        break;

      case FLOW :
        if (panel != null) {
          panel.add(a.getContainerComponent());
        }

        break;

      default :
        break;
    }

    return a;
  }

  @Override
  protected void clearConfiguration(boolean dispose) {
    super.clearConfiguration(dispose);

    if (layoutObject instanceof ArrayList) {
      ArrayList list = (ArrayList) layoutObject;
      int       len  = list.size();

      try {
        for (int i = 0; i < len; i++) {
          ((ComponentCC) list.get(i)).clear();
        }
      } catch(Throwable e) {
        Platform.ignoreException("", e);
      }

      list.clear();
    }

    if (submissionFiles != null) {
      submissionFiles.clear();
    }

    if (dispose) {
      formLayout      = null;
      layoutObject    = null;
      submissionFiles = null;
    }
  }

  /**
   * Configures a group box
   *
   * @param gb
   *          the group box configuration
   */
  protected void configureEx(GroupBox gb) {
    FormInfo info = new FormInfo();

    info.widgets        = gb.widgets;
    info.rowSpacing     = ScreenUtils.platformPixels(gb.rowSpacing.intValue());
    info.columnSpacing  = ScreenUtils.platformPixels(gb.columnSpacing.intValue());
    info.rowGrouping    = createGrouping(gb.getRowGrouping());
    info.columnGrouping = createGrouping(gb.getColumnGrouping());
    info.painters       = gb.getCellPainters();
    createComponents(gb, info);
    info.formLayout   = formLayout;
    info.layoutObject = layoutObject;
    submitValueType   = gb.submitValue.intValue();

    UIPoint p = addAndConfigureWidgets(this, (iParentComponent) dataComponent, info);

    if ((formLayout == Layout.TABLE) && isDesignMode()) {
      if (gb.rows.intValue() < p.y) {
        gb.rows.setValue(p.y);
      }

      if (gb.columns.intValue() < p.x) {
        gb.columns.setValue(p.x);
      }
    }

    this.configureGenericDnD(dataComponent, gb);
    info.clear();
  }

  protected Object createAbsoluteLayoutConstraints(int x, int y, int width, int height) {
    return Utils.createAbsoluteConstraints(x, y, width, height);
  }

  /**
   * Creates the absolute layout panel
   *
   * @return the forms panel
   */
  protected iPlatformComponent createAbsoluteLayoutPanel(GroupBox gb) {
    return new AbsolutePanel(this);
  }

  /**
   * Creates the components for the viewer
   *
   * @param gb
   *          the configuration
   * @param info
   *          the form indo
   */
  protected void createComponents(GroupBox gb, FormInfo info) {
    switch(gb.layout.intValue()) {
      case Form.CLayout.table : {
        formLayout    = Layout.TABLE;
        formComponent = dataComponent = createFormsPanel(1, 1);
        ((iFormsPanel) dataComponent).setTableLayout(true);
        configureEx(gb, true, false, true);
        setupScrollPane(gb);
        layoutObject = new ArrayList<ComponentCC>();

        ComponentCC ccc = new ComponentCC(null, null);

        ccc.tas        = new UIRectangle(gb.columns.intValue(), gb.rows.intValue());
        ccc.tas.width  = (ccc.tas.width < 1)
                         ? 1
                         : ccc.tas.width;
        ccc.tas.height = (ccc.tas.height < 1)
                         ? 1
                         : ccc.tas.height;
        ((ArrayList<ComponentCC>) layoutObject).add(ccc);

        break;
      }

      case Form.CLayout.forms : {
        formLayout    = Layout.FORMS;
        formComponent = dataComponent = createFormsPanel();
        setupScrollPane(gb);
        configureEx(gb, true, false, true);

        String cols = gb.columns.getValue();
        String rows = gb.rows.getValue();

        if ((cols == null) || (cols.length() == 0)) {
          cols = "FILL:PREF:NONE";
        }

        if ((rows == null) || (rows.length() == 0)) {
          rows = "CENTER:DEFAULT:NONE";
        }

        ((iFormsPanel) dataComponent).setLayout(cols, rows);

        break;
      }

      case Form.CLayout.custom : {
        formLayout = Layout.CUSTOM;

        iCustomLayoutManager lm = null;
        String               s  = gb.layout.spot_getAttribute("layoutManager");

        lm = (s == null)
             ? null
             : (iCustomLayoutManager) Platform.createObject(s);

        if (lm == null) {
          throw new NullPointerException("layoutManager attribute cannot be null for custom layouts");
        }

        formComponent = dataComponent = lm.createComponent(this, gb);
        layoutObject  = lm.getConstraintsConverter();
        setupScrollPane(gb);
        configureEx(gb, true, false, true);

        break;
      }

      case Form.CLayout.flow : {
        formLayout    = Layout.FLOW;
        formComponent = dataComponent = createFlowPanel(gb, info.columnSpacing, info.rowSpacing);
        configureEx(gb, true, false, true);
        setupScrollPane(gb);

        break;
      }

      default : {
        formLayout    = Layout.ABSOLUTE;
        formComponent = dataComponent = createAbsoluteLayoutPanel(gb);
        setupScrollPane(gb);
        configureEx(gb, true, false, true);

        break;
      }
    }
  }

  protected Object createFlowLayoutConstraints(Widget cfg) {
    CellConstraints cc = new CellConstraints();

    Utils.createCellConstraints(cfg, cc, null);

    return cc;
  }

  /**
   * Creates the forms panel using the specified layout
   *
   * @param columnSpacing
   *          the column spacing
   * @param rowSpacing
   *          the row spacing
   * @return the forms panel
   */
  protected iPlatformComponent createFlowPanel(GroupBox gb, int columnSpacing, int rowSpacing) {
    FlowPanel p = new FlowPanel(this);

    p.setColumnSpacing(columnSpacing);
    p.setRowSpacing(rowSpacing);

    return p;
  }

  /**
   * Creates the forms panel with no initial layout
   *
   * @return the forms panel
   */
  protected abstract iPlatformComponent createFormsPanel();

  /**
   * Creates the forms panel using the specified number of rows and columns
   *
   * @param rows
   *          the number of rows
   * @param cols
   *          the number of columns
   *
   * @return the forms panel
   */
  protected abstract iPlatformComponent createFormsPanel(int rows, int cols);

  protected void setupScrollPane(GroupBox gb) {
    if (gb.getScrollPane() != null) {
      formComponent = PlatformHelper.makeScrollPane(this, gb.getScrollPane(), formComponent);
      formComponent.setOpaque(false);
    }
  }

  public static class ComponentCC {
    public CellConstraints    cc;
    public iPlatformComponent component;
    public Object             linkedData;
    public UIRectangle        tas;
    boolean                   widget = true;

    public ComponentCC(iPlatformComponent component, CellConstraints cc) {
      this.component = component;
      this.cc        = cc;
    }

    public void clear() {
      cc         = null;
      component  = null;
      linkedData = null;
      widget     = true;
    }
  }


  public static class FormInfo {
    public int     columnGrouping[][];
    public int     columnSpacing;
    public String  customInfo;
    public Layout  formLayout;
    public Object  layoutObject;
    public SPOTSet painters;
    public int     rowGrouping[][];
    public int     rowSpacing;
    public SPOTSet widgets;

    public void clear() {
      if (layoutObject instanceof List) {
        ((List) layoutObject).clear();
      }

      if (layoutObject instanceof Map) {
        ((Map) layoutObject).clear();
      }

      layoutObject = null;
      formLayout   = null;
      widgets      = null;
      painters     = null;
    }
  }
}
