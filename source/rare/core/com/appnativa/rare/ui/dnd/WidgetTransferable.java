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

import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.widget.iWidget;

import java.io.IOException;

/**
 *
 * @author Don DeCoteau
 */
public class WidgetTransferable implements iTransferable {

  /** the widget */
  protected iWidget       theWidget;
  protected iPlatformIcon dataIcon;
  protected Object        exportEndPoint;
  protected Object        exportStartPoint;

  /**
   * Creates a new instance of WidgetTransferable
   *
   * @param widget the widget
   */
  public WidgetTransferable(iWidget widget) {
    theWidget = widget;
  }

  public void clear() {
    theWidget        = null;
    exportEndPoint   = null;
    dataIcon         = null;
    exportStartPoint = null;
  }

  /**
   * Sets the icon that establishes the look of the transfer.
   *
   * @param icon the icon
   */
  public void setDataIcon(iPlatformIcon icon) {
    this.dataIcon = icon;
  }

  /**
   * Sets the end point for the export
   *
   * @param exportEndPoint widget specific transfer export end point
   */
  public void setExportEndPoint(Object exportEndPoint) {
    this.exportEndPoint = exportEndPoint;
  }

  /**
   * Sets the starting point for the export
   *
   * @param exportStartPoint widget specific transfer export starting point
   */
  public void setExportStartPoint(Object exportStartPoint) {
    this.exportStartPoint = exportStartPoint;
  }

  /**
   * Returns an icon that establishes the look of the transfer.
   *
   * @return the icon
   */
  @Override
  public iPlatformIcon getDataIcon() {
    if (dataIcon == null) {
      theWidget.getIcon();
    }

    return dataIcon;
  }

  /**
   * Get the end point for the export
   *
   * @return widget specific transfer export end point
   */
  public Object getExportEndPoint() {
    return exportEndPoint;
  }

  /**
   * Get the starting point for the export
   *
   * @return widget specific transfer export starting point
   */
  public Object getExportStartPoint() {
    return exportStartPoint;
  }

  @Override
  public Object getTransferData(TransferFlavor flavor)
          throws com.appnativa.rare.ui.dnd.UnsupportedFlavorException, IOException {
    if (flavor == TransferFlavor.widgetFlavor) {
      return theWidget;
    }

    return theWidget.getTransferData(flavor, this);
  }

  @Override
  public TransferFlavor[] getTransferFlavors() {
    String[] a = theWidget.getExportableDataFlavors();

    if (a == null) {
      TransferFlavor tf = TransferFlavor.stringFlavor;

      return (TransferFlavor[]) tf.getPlatformFlavor();
    }

    return TransferFlavor.createSupportedDataFlavors(a, true);
  }

  /**
   * Gets the widget that this transferable is associated with
   *
   * @return the widget
   */
  public iWidget getWidget() {
    return theWidget;
  }

  @Override
  public boolean isTransferFlavorSupported(TransferFlavor flavor) {
    if (flavor == TransferFlavor.widgetFlavor) {
      return true;
    }

    if ((flavor == TransferFlavor.itemFlavor) && (theWidget instanceof iListHandler)) {
      return true;
    }

    return theWidget.canExport(flavor);
  }
}
