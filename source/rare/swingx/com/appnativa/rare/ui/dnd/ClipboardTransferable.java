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

import com.appnativa.rare.widget.iWidget;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

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
