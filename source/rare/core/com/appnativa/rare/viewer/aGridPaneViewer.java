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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.CollapsibleInfo;
import com.appnativa.rare.spot.GridPane;
import com.appnativa.rare.spot.Region;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iCollapsible;
import com.appnativa.rare.ui.iFormsPanel;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.viewer.aGroupBoxViewer.ComponentCC;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

/**
 * A viewer utilizing a table layout. Each cell in the grid is a region
 * The region's configuration defines the type of viewer, the viewers
 * location and how the viewer will be laid out within the cell.
 *
 * @author     Don DeCoteau
 */
public abstract class aGridPaneViewer extends aPlatformRegionViewer {
  private static ColumnSpec ZERO_COL_SPEC = new ColumnSpec(ColumnSpec.DEFAULT, Sizes.ZERO, 0);
  private static RowSpec    ZERO_ROW_SPEC = new RowSpec(RowSpec.DEFAULT, Sizes.ZERO, 0);

  /** column spacing */
  protected int columnSpacing;

  /** row spacing */
  protected int                          rowSpacing;
  protected int                          targetID = 0;
  protected iFormsPanel                  gridPanel;
  protected int                          rowCount;
  protected HashMap<Integer, ColumnSpec> savedColumnSpecs;
  protected HashMap<Integer, RowSpec>    savedRowSpecs;

  /**
   * Constructs a new instance
   */
  public aGridPaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public aGridPaneViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.GridPane;
  }

  /**
   * Adds a new row to the grid
   *
   * @param horizPreferred true for preferred size horizontal fill; false for maximum
   * @param vertPreferred true for preferred size vertical fill; false for maximum
   * @param cinfo optional collapsible information
   *
   * @return an array representing the target for each column in the new row
   */
  public iTarget[] addColumn(boolean horizPreferred, boolean vertPreferred, CollapsibleInfo cinfo) {
    int col = columnCount * 2 - 1;

    if (columnCount > 0) {
      gridPanel.addSpacerColumn(columnSpacing);
      col++;
    }

    if (rowCount == 0) {
      gridPanel.addRow(0, "FILL:DEFAULT:GROW(1.0)");
      rowCount = 1;
    }

    if (col < 0) {
      col = 0;
    }

    gridPanel.addColumn(col, "FILL:DEFAULT:GROW(1.0)");

    CellConstraints  cc;
    String           name;
    iTarget          t[] = new iTarget[rowCount];
    iParentComponent panel;

    for (int i = 0; i < rowCount; i++) {
      cc = gridPanel.createConstraints(i * 2, col, 1, 1);

      if (!horizPreferred) {
        cc.hAlign = CellConstraints.FILL;
      }

      if (!vertPreferred) {
        cc.vAlign = CellConstraints.FILL;
      }

      name  = generateTargetName("view#" + targetID++);
      panel = createPanel(cinfo);
      t[i]  = createTarget(name, panel);
      gridPanel.add(panel, cc);
    }

    columnCount++;

    return t;
  }

  /**
   * Changes the vertical fill for a region
   *
   * @param pos the position of the region
   * @param preferred true to only consume space to accommodate it's viewer preferred size;
   *                   false to take as much space as possible
   */
  public void setRegionVerticalFill(int pos, boolean preferred) {
    iTarget         t  = getRegion(pos);
    CellConstraints cc = gridPanel.getCellConstraints(t.getContainerComponent());

    if (cc != null) {
      gridPanel.setRowSpec(cc.gridY - 1, preferred
                                         ? "FILL:DEFAULT:NONE"
                                         : "FILL:DEFAULT:GROW");
    }
  }

  /**
   * Changes the horizontal fill for a region
   *
   * @param pos the position of the region
   * @param preferred true to only consume space to accommodate it's viewer preferred size;
   *                   false to take as much space as possible
   */
  public void setRegionHorizontalFill(int pos, boolean preferred) {
    iTarget         t  = getRegion(pos);
    CellConstraints cc = gridPanel.getCellConstraints(t.getContainerComponent());

    if (cc != null) {
      gridPanel.setColumnSpec(cc.gridX - 1, preferred
              ? "FILL:DEFAULT:NONE"
              : "FILL:DEFAULT:GROW");
    }
  }

  /**
   * Adds a new row to the grid
   *
   * @param horizPreferred true for preferred size horizontal fill; false for maximum
   * @param vertPreferred true for preferred size vertical fill; false for maximum
   * @param cinfo optional collapsible information
   *
   * @return an array representing the target for each column in the new row
   */
  public iTarget[] addRow(boolean horizPreferred, boolean vertPreferred, CollapsibleInfo cinfo) {
    int row = rowCount * 2 - 1;

    if (columnCount == 0) {
      gridPanel.addColumn(1, "FILL:DEFAULT:GROW");
      columnCount = 1;
    }

    if (rowCount > 0) {
      gridPanel.addSpacerRow(rowSpacing);
      row++;
    }

    if (row < 0) {
      row = 0;
    }

    gridPanel.addRow(row, "FILL:DEFAULT:GROW");

    CellConstraints  cc;
    String           name;
    iTarget          t[] = new iTarget[columnCount];
    iParentComponent panel;

    for (int i = 0; i < columnCount; i++) {
      cc = gridPanel.createConstraints(row, i * 2, 1, 1);

      if (!horizPreferred) {
        cc.hAlign = CellConstraints.FILL;
      }

      if (!vertPreferred) {
        cc.hAlign = CellConstraints.FILL;
      }

      name  = generateTargetName("view#" + targetID++);
      panel = createPanel(cinfo);
      panel.setOpaque(false);
      t[i] = createTarget(name, panel);
      gridPanel.add(panel, cc);
    }

    rowCount++;

    return t;
  }

  @Override
  public void clearContents() {
    clearContents(false);
  }

  /**
   * Removes all rows and columns
   * @param disposeViewers true to dispose viewers; false others
   */
  public void clearGrid(boolean disposeViewers) {
    this.clearConfigurationEx(disposeViewers);
    gridPanel.removeAll();
    gridPanel.setLayout("d", "d");
    rowCount    = 0;
    columnCount = 0;
  }

  /**
   * Configures the grid pane
   *
   * @param vcfg the grid pane's configuration
   */
  @Override
  public void configure(Viewer vcfg) {
    configureEx((GridPane) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Removes a column
   *
   * @param col the zero-based column index
   * @param disposeViewers true to dispose viewers; false others
   */
  public void removeColumn(int col, boolean disposeViewers) {
    if ((col < 0) || (col >= columnCount)) {
      return;
    }

    col = col * 2 + 1;

    iPlatformComponent c;
    iTarget            t[] = new iTarget[rowCount];

    for (int i = 0; i < rowCount; i++) {
      c = gridPanel.getGridComponentAt(col, i * 2 + 1);

      if (c != null) {
        t[i] = Utils.getTargetForComponent(c);
      }
    }

    col = (col - 1) / 2;
    gridPanel.removeColumn(col);

    for (int i = 0; i < rowCount; i++) {
      if (t[i] != null) {
        this.removeTarget(t[i]);
        t[i].dispose(disposeViewers);
      }
    }
  }

  /**
   * Removes a row
   *
   * @param row the zero-based row index
   * @param disposeViewers true to dispose viewers; false others
   */
  public void removeRow(int row, boolean disposeViewers) {
    if ((row < 0) || (row >= rowCount)) {
      return;
    }

    row = row * 2 + 1;

    iPlatformComponent c;
    iTarget            t[] = new iTarget[columnCount];

    for (int i = 0; i < columnCount; i++) {
      c = gridPanel.getGridComponentAt(i * 2 + 1, row);

      if (c != null) {
        t[i] = Utils.getTargetForComponent(c);
      }
    }

    for (int i = 0; i < columnCount; i++) {
      if (t[i] != null) {
        this.removeTarget(t[i]);
        t[i].dispose(disposeViewers);
      }
    }

    row = (row - 1) / 2;
    gridPanel.removeRow(row);
  }

  /**
   * Gets the number of columns in the grid
   *
   * @return the number of columns in the grid
   */
  public int getColumnCount() {
    return columnCount;
  }

  /**
   * Gets the number of rows in the grid
   *
   * @return the number of rows in the grid
   */
  public int getRowCount() {
    return rowCount;
  }

  /**
   * Clears the configuration
   *
   * @param dispose true to clear and dispose; false to just clear
   */
  @Override
  protected void clearConfiguration(boolean dispose) {
    super.clearConfiguration(true);

    if (dispose) {
      if (savedColumnSpecs != null) {
        savedColumnSpecs.clear();
      }

      if (savedRowSpecs != null) {
        savedRowSpecs.clear();
      }

      gridPanel        = null;
      savedColumnSpecs = null;
      savedRowSpecs    = null;
    }
  }

  /**
   *  Configures events configured via a CollapsibleInfo object
   *
   *  @param pane the collapsible pane
   *  @param info the collapsible info
   */
  @Override
  protected void configureCollapsibleEvents(iCollapsible pane, CollapsibleInfo info) {
    Map map = aWidgetListener.createEventMap(info.spot_getAttributesEx());

    if ((map != null) && (map.size() > 0)) {
      CollapsibleListener l = new CollapsibleListener(this, map);

      pane.addExpandedListener(l);
      pane.addExpansionListener(l);
    }
  }

  protected abstract iFormsPanel createFormsPanel();

  /**
   * Configures the grid pane
   *
   * @param cfg the grid pane's configuration
   */
  protected void configureEx(GridPane cfg) {
    gridPanel = createFormsPanel();
    gridPanel.setTableLayout(true);
    formComponent   = dataComponent = gridPanel.getComponent();
    actAsFormViewer = cfg.actAsFormViewer.booleanValue();
    configureEx(cfg, true, false, true);

    int[][] rg = aGroupBoxViewer.createGrouping(cfg.getRowGrouping());
    int[][] cg = aGroupBoxViewer.createGrouping(cfg.getColumnGrouping());

    if (cfg.getScrollPane() != null) {
      formComponent = PlatformHelper.makeScrollPane(this, cfg.getScrollPane(), formComponent);
      dataComponent.setOpaque(false);
    }

    isSubmittable = true;

    int                    len = cfg.regions.getCount();
    Region                 region;
    iParentComponent       panel;
    CellConstraints        cc;
    String                 name;
    ArrayList<ComponentCC> layoutObject = new ArrayList<ComponentCC>(len);
    ComponentCC            ccc          = new ComponentCC(null, null);

    ccc.tas        = new UIRectangle(cfg.columns.intValue(), cfg.rows.intValue());
    ccc.tas.width  = (ccc.tas.width < 1)
                     ? 1
                     : ccc.tas.width;
    ccc.tas.height = (ccc.tas.height < 1)
                     ? 1
                     : ccc.tas.height;
    layoutObject.add(ccc);

    boolean dm = isDesignMode();
    Widget  wc = new Widget();

    if (dm && (len == 0)) {
      len = 1;
      cfg.regions.add(new Region());
    }

    Collections.sort(cfg.regions, new RegionSorter());

    for (int i = 0; i < len; i++) {
      region = (Region) cfg.regions.get(i);
      name   = dm
               ? null
               : region.name.getValue();

      CollapsibleInfo cinfo = region.getCollapsibleInfo();

      panel = createPanel(cinfo);
      wc.bounds.setValues(region);
      registerWithWidget(panel);

      switch(region.verticalFill.intValue()) {
        case Region.CHorizontalFill.maximum :
          wc.verticalAlign.setValue(Widget.CVerticalAlign.full);

          break;

        default :
          wc.verticalAlign.setValue(Widget.CVerticalAlign.auto);

          break;
      }

      switch(region.horizontalFill.intValue()) {
        case Region.CHorizontalFill.maximum :
          wc.horizontalAlign.setValue(Widget.CHorizontalAlign.full);

          break;

        default :
          wc.horizontalAlign.setValue(Widget.CHorizontalAlign.auto);

          break;
      }

      if (name == null) {
        name = generateTargetName(region);
      }

      cc = aGroupBoxViewer.addTableWidget(null, createTarget(name, panel, region).getContainerComponent(), wc,
              layoutObject);

      if (dm && (cc != null)) {
        region.x.setValue(cc.gridX - 1);
        region.y.setValue(cc.gridY - 1);
      }
    }

    columnSpacing = ScreenUtils.platformPixels(cfg.columnSpacing.intValue());
    rowSpacing    = ScreenUtils.platformPixels(cfg.rowSpacing.intValue());

    UIPoint p = aGroupBoxViewer.addTableWidgets(gridPanel, layoutObject, true);

    gridPanel.addRowSpacing(rowSpacing);
    gridPanel.addColumnSpacing(columnSpacing);
    rowCount    = (int) p.y;
    columnCount = (int) p.x;

    if (rg != null) {
      fixGroupings(rg);
      gridPanel.getFormLayout().setRowGroups(rg);
    }

    if (cg != null) {
      fixGroupings(cg);
      gridPanel.getFormLayout().setColumnGroups(cg);
    }

    if (dm) {
      if (cfg.rows.intValue() < p.y) {
        cfg.rows.setValue(p.y);
      }

      if (cfg.columns.intValue() < p.x) {
        cfg.columns.setValue(p.x);
      }
    }

    iTarget t;

    len = getRegionCount();

    for (int i = 0; i < len; i++) {
      t = getRegion(i);

      if (!t.isVisible()) {
        targetHidden(t);
      }
    }
  }

  protected void targetHidden(iTarget t) {
    CellConstraints cc = gridPanel.getCellConstraints(t.getContainerComponent());

    if (cc == null) {
      return;
    }

    int       row = cc.gridY;
    int       col = cc.gridX;
    boolean[] b   = gridPanel.isColumnRowComponentsHidden(col, row);

    if (b[0]) {
      if (savedRowSpecs == null) {
        savedRowSpecs = new HashMap<Integer, RowSpec>(3);
      }

      if (savedRowSpecs.get(row) == null) {
        savedRowSpecs.put(row, gridPanel.getFormLayout().getRowSpec(row));
      }

      gridPanel.getFormLayout().setRowSpec(row, ZERO_ROW_SPEC);
    }

    if (b[1]) {
      if (savedColumnSpecs == null) {
        savedColumnSpecs = new HashMap<Integer, ColumnSpec>(3);
      }

      if (savedColumnSpecs.get(col) == null) {
        savedColumnSpecs.put(col, gridPanel.getFormLayout().getColumnSpec(col));
      }

      gridPanel.getFormLayout().setColumnSpec(col, ZERO_COL_SPEC);
    }
  }

  protected void targetShown(iTarget t) {
    CellConstraints cc = gridPanel.getCellConstraints(t.getContainerComponent());

    if (cc == null) {
      return;
    }

    int       row = cc.gridY;
    int       col = cc.gridX;
    boolean[] b   = gridPanel.isColumnRowComponentsHidden(col, row);

    if (!b[0]) {
      RowSpec spec = (savedRowSpecs == null)
                     ? null
                     : savedRowSpecs.get(row);

      if (spec != null) {
        gridPanel.getFormLayout().setRowSpec(row, spec);
      }
    }

    if (!b[1]) {
      ColumnSpec spec = (savedColumnSpecs == null)
                        ? null
                        : savedColumnSpecs.get(col);

      if (spec != null) {
        gridPanel.getFormLayout().setColumnSpec(col, spec);
      }
    }

    update();
  }

  @Override
  protected void targetVisibilityChanged(iTarget t, boolean visibile) {
    if (visibile) {
      targetShown(t);
    } else {
      targetHidden(t);
    }
  }

  private void fixGroup(int[] g) {
    if (g != null) {
      for (int i = 0; i < g.length; i++) {
        g[i] = ((g[i] - 1) * 2) + 1;
      }
    }
  }

  private void fixGroupings(int[][] g) {
    if (g != null) {
      for (int i = 0; i < g.length; i++) {
        fixGroup(g[i]);
      }
    }
  }

  private String generateTargetName(Region r) {
    return generateTargetName("view#" + targetID++);
  }
}
