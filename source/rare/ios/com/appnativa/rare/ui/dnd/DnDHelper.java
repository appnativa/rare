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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.ui.dnd.TransferFlavor;
import com.appnativa.rare.ui.dnd.iFlavorCreator;

public class DnDHelper implements iFlavorCreator {
  private static DnDHelper _instance;
  TransferFlavor           fileFlavor     = new TransferFlavor(iConstants.DATAFLAVOR_FILE, "application/x-file");
  TransferFlavor           fileListFlavor = new TransferFlavor(iConstants.DATAFLAVOR_FILE_LIST,
                                              "application/x-file-list");
  TransferFlavor           htmlFlavor     = new TransferFlavor(iConstants.DATAFLAVOR_HTML, "text/html");
  TransferFlavor           imageFlavor    = new TransferFlavor(iConstants.DATAFLAVOR_IMAGE, "image/png", "image/gif",
                                              "image/jpg");
  TransferFlavor           rtfFlavor      = new TransferFlavor(iConstants.DATAFLAVOR_RTF, "text/rtf");
  TransferFlavor           stringFlavor   = new TransferFlavor(iConstants.DATAFLAVOR_TEXT, "text/plain");
  TransferFlavor           urlFlavor      = new TransferFlavor(iConstants.DATAFLAVOR_URL, "application/x-url");
  TransferFlavor           urlListFlavor  = new TransferFlavor(iConstants.DATAFLAVOR_URL, "application/x-url-list");

  protected DnDHelper() {}

  public TransferFlavor createFlavor(String name, String... mimeTypes) {
    return new TransferFlavor(name, mimeTypes);
  }

  public TransferFlavor getFileFlavor() {
    return fileFlavor;
  }

  public TransferFlavor getFileListFlavor() {
    return fileListFlavor;
  }

  public TransferFlavor getHTMLFlavor() {
    return htmlFlavor;
  }

  public TransferFlavor getImageFlavor() {
    return imageFlavor;
  }

  public static DnDHelper getInstance() {
    if (_instance == null) {
      _instance = new DnDHelper();
    }

    return _instance;
  }

  public TransferFlavor getRTFFlavor() {
    return rtfFlavor;
  }

  public TransferFlavor getStringFlavor() {
    return stringFlavor;
  }

  public TransferFlavor getURLFlavor() {
    return urlFlavor;
  }

  public TransferFlavor getURLListFlavor() {
    return urlListFlavor;
  }

  @Override
  public TransferFlavor createFlavor(Object platformFlavor) {
    String mime = "application/x-ios";

    return new TransferFlavor(mime, platformFlavor, mime);
  }
}
