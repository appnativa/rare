/*
 * @(#)TransferableWrapper.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.ArrayList;

import com.appnativa.rare.ui.iPlatformIcon;

public class TransferableWrapper implements iTransferable {
  protected iPlatformIcon dataIcon;
  protected Transferable  transferable;

  public TransferableWrapper(Transferable transferable) {
    this.transferable = transferable;
  }

  public void setDataIcon(iPlatformIcon icon) {
    dataIcon = icon;
  }

  @Override
  public iPlatformIcon getDataIcon() {
    return dataIcon;
  }

  @Override
  public Object getTransferData(TransferFlavor flavor) throws UnsupportedFlavorException, IOException {
    Object o = flavor.getPlatformFlavor();

    if (o instanceof DataFlavor[]) {
      DataFlavor[] a = (DataFlavor[]) o;

      for (int i = 0; i < a.length; i++) {
        if (transferable.isDataFlavorSupported(a[i])) {
          try {
            return transferable.getTransferData(a[i]);
          } catch(java.awt.datatransfer.UnsupportedFlavorException ignore) {}
        }
      }

      return null;
    } else {
      if (transferable.isDataFlavorSupported((DataFlavor) o)) {
        try {
          return transferable.getTransferData((DataFlavor) o);
        } catch(java.awt.datatransfer.UnsupportedFlavorException ignore) {}
      }
    }

    return null;
  }

  public static DataFlavor[] getTransferDataFlavors(TransferFlavor[] flavors) {
    if (flavors == null) {
      return null;
    }

    ArrayList<DataFlavor> list = new ArrayList<DataFlavor>(flavors.length);

    for (int i = 0; i < flavors.length; i++) {
      Object o = flavors[i].getPlatformFlavor();;

      if (o instanceof DataFlavor[]) {
        DataFlavor[] a = (DataFlavor[]) o;

        for (int n = 0; n < a.length; n++) {
          list.add(a[i]);
        }
      } else {
        list.add((DataFlavor) o);
      }
    }

    return list.toArray(new DataFlavor[list.size()]);
  }

  @Override
  public TransferFlavor[] getTransferFlavors() {
    return getTransferFlavors(transferable.getTransferDataFlavors());
  }

  public static TransferFlavor[] getTransferFlavors(DataFlavor[] flavors) {
    if (flavors == null) {
      return null;
    }

    TransferFlavor[] a = new TransferFlavor[flavors.length];

    for (int i = 0; i < flavors.length; i++) {
      a[i] = TransferFlavor.fromPlatformFlavor(flavors[i]);
    }

    return a;
  }

  @Override
  public boolean isTransferFlavorSupported(TransferFlavor flavor) {
    Object o = flavor.getPlatformFlavor();

    if (o instanceof DataFlavor[]) {
      DataFlavor[] a = (DataFlavor[]) o;

      for (int i = 0; i < a.length; i++) {
        if (transferable.isDataFlavorSupported(a[i])) {
          return true;
        }
      }

      return false;
    } else {
      return transferable.isDataFlavorSupported((DataFlavor) o);
    }
  }
}
