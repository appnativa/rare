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
import com.appnativa.rare.widget.iWidget;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.io.IOException;

/**
 *
 * @author Don DeCoteau
 */
public class WidgetTransferableEx extends WidgetTransferable implements Transferable {

  /**
   * Creates a new instance of WidgetTransferable
   *
   * @param widget the widget
   */
  public WidgetTransferableEx(iWidget widget) {
    super(widget);
  }

  @Override
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    TransferFlavor tf = TransferFlavor.fromPlatformFlavor(flavor);

    if (tf == TransferFlavor.widgetFlavor) {
      return theWidget;
    }

    return theWidget.getTransferData(tf, this);
  }

  @Override
  public DataFlavor[] getTransferDataFlavors() {
    String[] a = theWidget.getExportableDataFlavors();

    if (a == null) {
      TransferFlavor tf = TransferFlavor.stringFlavor;

      return (DataFlavor[]) tf.getPlatformFlavor();
    }

    TransferFlavor[] f = TransferFlavor.createSupportedDataFlavors(a, true);

    if (f == null) {
      return null;
    }

    DataFlavor[] d = new DataFlavor[f.length];

    for (int i = 0; i < f.length; i++) {
      d[i] = (DataFlavor) f[i].getPlatformFlavor();
    }

    return d;
  }

  /**
   * {@inheritDoc}
   *
   * @param flavor {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  public boolean isDataFlavorSupported(DataFlavor flavor) {
    TransferFlavor tf = TransferFlavor.fromPlatformFlavor(flavor);

    if (tf == TransferFlavor.widgetFlavor) {
      return true;
    }

    if ((tf == TransferFlavor.itemFlavor) && (theWidget instanceof iListHandler)) {
      return true;
    }

    return theWidget.canExport(tf);
  }
}
