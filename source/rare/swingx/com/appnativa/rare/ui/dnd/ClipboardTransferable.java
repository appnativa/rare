/*
 * @(#)ClipboardTransferable.java   2007-07-10
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public class ClipboardTransferable extends WidgetTransferableEx {
  Map clipboardDataMap;

  /**
   * Constructs a new instance
   *
   * @param widget {@inheritDoc}
   */
  public ClipboardTransferable(iWidget widget) {
    super(widget);
  }

  @Override
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    TransferFlavor tf = TransferFlavor.fromPlatformFlavor(flavor);

    if (clipboardDataMap == null) {
      clipboardDataMap = new HashMap();
    }

    if (!clipboardDataMap.containsKey(tf)) {
      clipboardDataMap.put(flavor, theWidget.getTransferData(tf, this));
    }

    return clipboardDataMap.get(flavor);
  }
}
