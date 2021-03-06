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

package com.appnativa.rare.ui.celleditor;

import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iPlatformCellEditingComponent;
import com.appnativa.rare.viewer.FormViewer;
import com.appnativa.rare.widget.iWidget;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.TreeCellEditor;

/**
 * A generic widget-based cell editor
 *
 * @author Don DeCoteau
 */
public class WidgetEditor extends AbstractCellEditor
        implements iPlatformCellEditingComponent, TreeCellEditor, iActionListener {
  Component       lastContainer;
  iWidget         theWidget;
  Widget          widgetCfg;
  private boolean returnFocusContainer = true;

  /**
   * Creates a new instance of WidgetEditor
   */
  public WidgetEditor() {}

  /**
   * Creates a new instance of WidgetEditor
   *
   * @param w the widget
   * @param cfg the configuration that created the widget
   */
  public WidgetEditor(iWidget w, Widget cfg) {
    setWidget(w, cfg);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    stopCellEditing();
  }

  @Override
  public void cancelCellEditing() {
    super.cancelCellEditing();
    resetFocus();
  }

  @Override
  public boolean stopCellEditing() {
    boolean stop = super.stopCellEditing();

    if (stop) {
      resetFocus();
    }

    return stop;
  }

  private void resetFocus() {
    if (returnFocusContainer && (lastContainer != null)) {
      lastContainer.requestFocusInWindow();
    }

    lastContainer = null;
  }

  @Override
  public Object clone() {
    iWidget w = FormViewer.createWidget(theWidget.getContainerViewer(), widgetCfg);

    return new WidgetEditor(w, widgetCfg);
  }

  public void setCellEditorValue(Object value) {
    theWidget.setValue(value);
  }

  /**
   * @param returnFocusContainer the returnFocusContainer to set
   */
  public void setReturnFocusContainer(boolean returnFocusContainer) {
    this.returnFocusContainer = returnFocusContainer;
  }

  public void setWidget(iWidget w, Widget cfg) {
    theWidget = w;
    widgetCfg = cfg;

    if (theWidget instanceof iActionable) {
      ((iActionable) theWidget).addActionListener(this);
    }
  }

  public Component getCellEditorComponent(JComponent component, Object value, boolean isSelected, int row) {
    theWidget.setValue(value);

    return theWidget.getContainerComponent().getView();
  }

  @Override
  public Object getCellEditorValue() {
    Object o = theWidget.getSelection();

    if (o instanceof RenderableDataItem) {
      return ((RenderableDataItem) o).getValue();
    }

    return o;
  }

  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    theWidget.setValue(value);
    lastContainer = table;

    return theWidget.getContainerComponent().getView();
  }

  @Override
  public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
          boolean leaf, int row) {
    theWidget.setValue(value);
    lastContainer = tree;

    return theWidget.getContainerComponent().getView();
  }

  public iWidget getWidget() {
    return theWidget;
  }

  /**
   * @return the returnFocusContainer
   */
  public boolean isReturnFocusContainer() {
    return returnFocusContainer;
  }
}
