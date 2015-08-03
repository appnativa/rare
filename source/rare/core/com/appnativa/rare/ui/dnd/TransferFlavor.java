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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Don DeCoteau
 */
public class TransferFlavor {
  public static final String          CUSTOM_DATAFLAVOR_MIME_TYPE = "application/x-com-appnativa-rare-custom-item";
  public static final String          WIDGET_DATAFLAVOR_MIME_TYPE = "application/x-com-appnativa-rare-widget";
  public static final TransferFlavor  fileListFlavor;
  public static final TransferFlavor  htmlFlavor;
  public static final TransferFlavor  imageFlavor;
  public final static TransferFlavor  itemFlavor;
  public static final TransferFlavor  stringFlavor;
  public static final TransferFlavor  urlFlavor;
  public static final TransferFlavor  urlListFlavor;
  public static final TransferFlavor  widgetFlavor;
  private static final iFlavorCreator flavorCreator;

  static {
    flavorCreator = Platform.getPlatform().getTransferFlavorCreator();
    itemFlavor    = flavorCreator.createFlavor(iConstants.DATAFLAVOR_DATA_ITEM,
            "application/x-com-appnativa-rare-data-item");
    widgetFlavor   = flavorCreator.createFlavor(iConstants.DATAFLAVOR_WIDGET, WIDGET_DATAFLAVOR_MIME_TYPE);
    stringFlavor   = flavorCreator.getStringFlavor();
    imageFlavor    = flavorCreator.getImageFlavor();
    htmlFlavor     = flavorCreator.getHTMLFlavor();
    fileListFlavor = flavorCreator.getFileListFlavor();
    urlFlavor      = flavorCreator.getURLFlavor();
    urlListFlavor  = flavorCreator.getURLListFlavor();
  }

  private ArrayList<String> mimeTypes = new ArrayList<String>();
  private String            name;
  private Object            platfromFlavor;

  public TransferFlavor(String name, Object platfromFlavor, Set<String> types) {
    this.name = name;
    this.mimeTypes.addAll(types);
    this.platfromFlavor = platfromFlavor;
  }

  public TransferFlavor(String name, Object platfromFlavor, String... types) {
    this.name = name;

    for (String s : types) {
      mimeTypes.add(s);
    }

    this.platfromFlavor = platfromFlavor;
  }

  public static void addDataFlavor(String name, List<TransferFlavor> list, boolean export) {
    do {
      if (name == null) {
        break;
      }

      if (name.indexOf('/') != -1) {
        list.add(flavorCreator.createFlavor(name, name));

        break;
      }

      if (name.equals(iConstants.DATAFLAVOR_TEXT)) {
        list.add(TransferFlavor.stringFlavor);

        break;
      }

      if (name.equals(iConstants.DATAFLAVOR_HTML)) {
        list.add(htmlFlavor);

        break;
      }

      if (name.equals(iConstants.DATAFLAVOR_IMAGE)) {
        list.add(TransferFlavor.imageFlavor);

        break;
      }

      if (name.equals(iConstants.DATAFLAVOR_DATA_ITEM)) {
        list.add(itemFlavor);

        break;
      }

      if (name.equals(iConstants.DATAFLAVOR_URL)) {
        list.add(urlFlavor);

        break;
      }

      list.add(flavorCreator.createFlavor(name, CUSTOM_DATAFLAVOR_MIME_TYPE));
    } while(false);
  }

  public static TransferFlavor[] createSupportedDataFlavors(String[] flavorNames, boolean export) {
    int len = (flavorNames == null)
              ? 0
              : flavorNames.length;

    if (len == 0) {
      return null;
    }

    ArrayList<TransferFlavor> list = new ArrayList<TransferFlavor>(len);
    int                       i;

    if (flavorNames != null) {
      for (i = 0; i < flavorNames.length; i++) {
        addDataFlavor(flavorNames[i], list, export);
      }
    }

    return list.toArray(new TransferFlavor[list.size()]);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof TransferFlavor)) {
      return false;
    }

    TransferFlavor df = (TransferFlavor) o;

    return df.mimeTypes.equals(mimeTypes);
  }

  public static TransferFlavor fromPlatformFlavor(Object flavor) {
    if (stringFlavor.hasPlatformFlavor(flavor)) {
      return stringFlavor;
    }

    if (itemFlavor.hasPlatformFlavor(flavor)) {
      return itemFlavor;
    }

    if (widgetFlavor.hasPlatformFlavor(flavor)) {
      return widgetFlavor;
    }

    if (urlFlavor.hasPlatformFlavor(flavor)) {
      return urlFlavor;
    }

    if (urlListFlavor.hasPlatformFlavor(flavor)) {
      return urlListFlavor;
    }

    if (htmlFlavor.hasPlatformFlavor(flavor)) {
      return htmlFlavor;
    }

    if (imageFlavor.hasPlatformFlavor(flavor)) {
      return imageFlavor;
    }

    return flavorCreator.createFlavor(flavor);
  }

  public static TransferFlavor getDataFlavor(String[] flavorNames, String name) {
    String flavor = null;
    int    len    = (flavorNames == null)
                    ? 0
                    : flavorNames.length;

    for (int i = 0; i < len; i++) {
      if (name.equals(flavorNames[i])) {
        flavor = name;

        break;
      }
    }

    if (flavor == null) {
      return null;
    }

    if (flavor.equals(iConstants.DATAFLAVOR_TEXT)) {
      return TransferFlavor.stringFlavor;
    }

    if (flavor.equals(iConstants.DATAFLAVOR_IMAGE)) {
      return TransferFlavor.imageFlavor;
    }

    if (flavor.contains("/")) {
      return flavorCreator.createFlavor(flavor, flavor);
    }

    return flavorCreator.createFlavor(name, CUSTOM_DATAFLAVOR_MIME_TYPE);
  }

  public static TransferFlavor getDataFlavor(TransferFlavor[] supportedFlavors, String flavor) {
    boolean url       = false;
    boolean mimeCheck = false;
    String  urllist   = null;

    if (supportedFlavors == null) {
      return null;
    }

    if (iConstants.DATAFLAVOR_TEXT.equals(flavor)) {
      return getStringDataFlavor(supportedFlavors);
    }

    if (iConstants.DATAFLAVOR_HTML.equals(flavor)) {
      return htmlFlavor;
    }

    if (flavor.equals(iConstants.DATAFLAVOR_IMAGE)) {
      flavor = TransferFlavor.imageFlavor.getName();
    } else if (flavor.equals(iConstants.DATAFLAVOR_URL)) {
      url     = true;
      flavor  = urlFlavor.getName();
      urllist = urlListFlavor.getName();
    }

    int len = supportedFlavors.length;

    if (flavor.indexOf('/') != -1) {
      mimeCheck = true;
    }

    for (int i = 0; i < len; i++) {
      if (url) {
        String name = supportedFlavors[i].getName();

        if (name.equals(flavor) || name.equals(urllist)) {
          return supportedFlavors[i];
        }
      } else {
        if (mimeCheck) {
          if (supportedFlavors[i].isMimeTypeSupported(flavor)) {
            return supportedFlavors[i];
          }
        } else {
          if (supportedFlavors[i].getName().equals(flavor)) {
            return supportedFlavors[i];
          }
        }
      }
    }

    return null;
  }

  public static String getDataFlavorName(String[] flavorNames, TransferFlavor flavor) {
    if (flavorNames == null) {
      return null;
    }

    int    len = flavorNames.length;
    String name;

    if (flavor.isMimeTypeSupported(CUSTOM_DATAFLAVOR_MIME_TYPE)) {
      for (int i = 0; i < len; i++) {
        if (flavor.isMimeTypeSupported(CUSTOM_DATAFLAVOR_MIME_TYPE)) {
          name = flavorNames[i];

          if ((name != null) && name.equals(flavor.getName())) {
            return name;
          }
        }
      }

      return null;
    } else {
      for (int i = 0; i < len; i++) {
        name = flavorNames[i];

        if ((name != null) && name.equals(iConstants.DATAFLAVOR_TEXT)) {
          return flavor.equals(TransferFlavor.stringFlavor)
                 ? name
                 : null;
        } else if ((name != null) && name.equals(iConstants.DATAFLAVOR_IMAGE)) {
          return flavor.equals(TransferFlavor.imageFlavor)
                 ? name
                 : null;
        } else if ((name != null) && name.equals(iConstants.DATAFLAVOR_DATA_ITEM)) {
          return name;
        } else if (flavor.getName().equals(name)) {
          return name;
        }
      }
    }

    return null;
  }

  public List<String> getMimeTypes() {
    return Collections.unmodifiableList(mimeTypes);
  }

  public String getName() {
    return name;
  }

  public Object getPlatformFlavor() {
    return this.platfromFlavor;
  }

  public Reader getReaderForText(iTransferable transferable) throws UnsupportedFlavorException, IOException {
    String s = (String) transferable.getTransferData(stringFlavor);

    return new StringReader(s);
  }

  public static TransferFlavor getStringDataFlavor(TransferFlavor[] flavor) {
    final int len = flavor.length;

    for (int i = 0; i < len; i++) {
      if (flavor[i] == stringFlavor) {
        return flavor[i];
      }
    }

    return null;
  }

  public boolean hasPlatformFlavor(Object flavor) {
    if (platfromFlavor instanceof Object[]) {
      Object[] a = (Object[]) platfromFlavor;

      for (Object o : a) {
        if ((o == flavor) || o.equals(flavor)) {
          return true;
        }
      }
    } else if ((platfromFlavor == flavor) || flavor.equals(platfromFlavor)) {
      return true;
    }

    return false;
  }

  public static boolean isDataFlavorSupported(String[] flavorNames, TransferFlavor flavor) {
    if (flavorNames == null) {
      return false;
    }

    int    len = flavorNames.length;
    String name;

    if (flavor.isMimeTypeSupported(CUSTOM_DATAFLAVOR_MIME_TYPE)) {
      for (int i = 0; i < len; i++) {
        name = flavorNames[i];

        if ((name != null) && name.equals(flavor.getName())) {
          return true;
        }
      }

      return false;
    } else {
      for (int i = 0; i < len; i++) {
        name = flavorNames[i];

        if (name == null) {
          continue;
        }

        if (name.equals(iConstants.DATAFLAVOR_TEXT)) {
          return flavor.equals(stringFlavor);
        } else if (name.equals(iConstants.DATAFLAVOR_IMAGE)) {
          return flavor.equals(imageFlavor);
        } else if (name.equals(iConstants.DATAFLAVOR_HTML)) {
          return flavor.equals(htmlFlavor);
        } else if (name.equals(iConstants.DATAFLAVOR_DATA_ITEM)) {
          return true;
        } else if (name.equals(iConstants.DATAFLAVOR_URL)) {
          return flavor.equals(urlFlavor) || flavor.equals(urlListFlavor);
        } else if (name.equals(iConstants.DATAFLAVOR_FILE)) {
          return flavor.equals(fileListFlavor);
        } else {
          if (flavor.getName().equals(name) || flavor.isMimeTypeSupported(name)) {
            return true;
          }
        }
      }
    }

    return false;
  }

  public static boolean isDataFlavorSupported(TransferFlavor[] supportedFlavors, String flavor) {
    return getDataFlavor(supportedFlavors, flavor) != null;
  }

  public boolean isStringFlavor() {
    return this == stringFlavor;
  }

  public boolean isWidgetFlavor() {
    return this == widgetFlavor;
  }

  public boolean isItemFlavor() {
    return this == itemFlavor;
  }

  public boolean isMimeTypeSupported(String type) {
    return this.mimeTypes.contains(type);
  }
}
