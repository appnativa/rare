/*
 * @(#)DesignTransferable.java   2008-05-04
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.dnd.TransferFlavor;
import com.appnativa.util.Helper;

/**
 *
 * @author decoteaud
 */
public class DesignTransferable implements Transferable {
  static DataFlavor flavors[] = new DataFlavor[7];
  static DataFlavor LOCAL;

  static {
    try {
      LOCAL      = new DataFlavor("text/DesignTransferable;class=java.lang.String");
      flavors[0] = SwingHelper.createSageDataFlavor("WigetConfigs");
      flavors[1] = LOCAL;
      flavors[2] = new DataFlavor("text/x-sdf;class=java.lang.String");
      flavors[3] = new DataFlavor("text/plain;class=java.lang.String");
      flavors[4] = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=java.lang.String");
      flavors[5] = new DataFlavor("text/plain;class=java.io.Reader");
      flavors[6] = DataFlavor.stringFlavor;
    } catch(Exception ignore) {}
  }

  List<Widget>       widgets;
  private final long localID;

  public DesignTransferable(List<Widget> cfgs, long localID) {
    widgets      = cfgs;
    this.localID = localID;
  }

  public static DataFlavor getLocalDataFlavor() {
    return LOCAL;
  }

  public static DataFlavor getMainDataFlavor() {
    return flavors[0];
  }

  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    if (flavors[0].equals(flavor)) {
      return widgets;
    }

    if (flavor.equals(TransferFlavor.widgetFlavor.getPlatformFlavor())) {
      return null;
    }

    if (flavor == LOCAL) {
      return localID;
    }

    String s = Helper.toString(widgets, "\n");

    if (flavors[5].equals(flavor)) {
      return new StringReader(s);
    }

    return s;
  }

  public DataFlavor[] getTransferDataFlavors() {
    return flavors;
  }

  public static boolean hasSupportedFlavor(DataFlavor a[]) {
    for (DataFlavor f : a) {
      if (f.equals(flavors[0])) {
        return true;
      }
    }

    return false;
  }

  public static boolean hasSupportedFlavor(List<TransferFlavor> list) {
    for (TransferFlavor f : list) {
      if(f.hasPlatformFlavor(flavors[0])) {
        return true;
      }
    }

    return false;
  }

  public boolean isDataFlavorSupported(DataFlavor flavor) {
    if (flavor == LOCAL) {
      return true;
    }

    int len = flavors.length;

    for (int i = 0; i < len; i++) {
      if (flavors[i].equals(flavor)) {
        return true;
      }
    }

    return false;
  }
}
