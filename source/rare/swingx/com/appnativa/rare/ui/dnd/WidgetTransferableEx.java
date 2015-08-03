/*
 * @(#)WidgetTransferable.java   2007-07-10
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.widget.iWidget;

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
