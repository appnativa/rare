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

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.util.Helper;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public class RenderableDataItemTransferable implements iTransferable {
  protected iPlatformIcon dataIcon;

  /** the data items */
  protected List<RenderableDataItem> dataItems;

  /**
   * Creates a new instance
   *
   * @param items the data items
   */
  public RenderableDataItemTransferable(List<RenderableDataItem> items) {
    dataItems = items;
  }

  /**
   * Creates a new instance
   *
   * @param item the data item
   */
  public RenderableDataItemTransferable(RenderableDataItem item) {
    dataItems = Arrays.asList(item);
  }

  public void clear() {
    dataItems = null;
    dataIcon  = null;
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
   * Returns an icon that establishes the look of the transfer.
   *
   * @return the icon
   */
  @Override
  public iPlatformIcon getDataIcon() {
    return dataIcon;
  }

  public List<RenderableDataItem> getItems() {
    return dataItems;
  }

  @Override
  public Object getTransferData(TransferFlavor flavor)
          throws com.appnativa.rare.ui.dnd.UnsupportedFlavorException, IOException {
    if (flavor == TransferFlavor.itemFlavor) {
      return dataItems;
    }

    if (flavor == TransferFlavor.stringFlavor) {
      return Helper.toString(dataItems, "\n");
    }

    return null;
  }

  @Override
  public TransferFlavor[] getTransferFlavors() {
    return new TransferFlavor[] { TransferFlavor.itemFlavor };
  }

  @Override
  public boolean isTransferFlavorSupported(TransferFlavor flavor) {
    return flavor == TransferFlavor.itemFlavor;
  }
}
