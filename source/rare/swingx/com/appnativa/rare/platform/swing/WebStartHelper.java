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

package com.appnativa.rare.platform.swing;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.ui.iPlatformWindowManager;
import com.appnativa.rare.ui.iStatusBar;
import com.appnativa.rare.widget.iWidget;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import java.net.URL;

import javax.jnlp.BasicService;
import javax.jnlp.ClipboardService;
import javax.jnlp.DownloadService;
import javax.jnlp.DownloadServiceListener;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

/**
 *
 * @author Don DeCoteau
 */
public class WebStartHelper {
  private volatile static ADownloadServiceListener downloadServiceListener;

  /** Creates a new instance of WebStartHelper */
  public WebStartHelper() {}

  public static boolean browseURL(URL url) {
    try {
      return getBasicService().showDocument(url);
    } catch(Exception e) {
      return false;
    }
  }

  /**
   * Copies the specified value to the system clipboard
   * making it available to other applications.
   *
   * @param value the value to copy to the clipboard
   */
  public static void copyToClipboard(String value) {
    if (value != null) {
      StringSelection ss = new StringSelection(value);

      getClipboardService().setContents(ss);
    }
  }

  public static void loadResource(URL ref) throws java.io.IOException {
    if (downloadServiceListener == null) {
      downloadServiceListener = new ADownloadServiceListener();
    }

    getDownloadService().loadResource(ref, null, downloadServiceListener);
  }

  /**
   * Returns the contents of the system clipboard as a string.
   * Return null if the clipboard is empty of if its contents
   * cannot be represented as a string.
   *
   * @return the contents of the system clipboard or null
   */
  public static String getClipboardContents() {
    Transferable t = getClipboardService().getContents();

    if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
      try {
        return (String) t.getTransferData(DataFlavor.stringFlavor);
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }
    }

    return null;
  }

  public static URL getCodeBase() {
    return getBasicService().getCodeBase();
  }

  public static Object getService(String name) {
    try {
      int n = name.indexOf('.');

      if (n == -1) {
        name = "javax.jnlp." + name;
      }

      return javax.jnlp.ServiceManager.lookup(name);
    } catch(UnavailableServiceException ex) {
      throw new ApplicationException(ex);
    }
  }

  private static BasicService getBasicService() {
    try {
      return (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
    } catch(UnavailableServiceException ex) {
      throw new ApplicationException(ex);
    }
  }

  private static ClipboardService getClipboardService() {
    try {
      return (ClipboardService) ServiceManager.lookup("javax.jnlp.ClipboardService");
    } catch(UnavailableServiceException ex) {
      throw new ApplicationException(ex);
    }
  }

  private static DownloadService getDownloadService() {
    try {
      return (DownloadService) ServiceManager.lookup("javax.jnlp.DownloadService");
    } catch(UnavailableServiceException ex) {
      throw new ApplicationException(ex);
    }
  }

  private static class ADownloadServiceListener implements DownloadServiceListener {
    boolean initialized;

    @Override
    public void downloadFailed(URL uRL, String string) {
      if (initialized) {
        iStatusBar sb = getStatusBar();

        if (sb != null) {
          sb.progressComplete();
        }
      }
    }

    @Override
    public void progress(URL uRL, String string, long l, long l0, int percent) {
      getStatusBar();
    }

    @Override
    public void upgradingArchive(URL uRL, String string, int i, int percent) {
      getStatusBar();
    }

    @Override
    public void validating(URL uRL, String string, long l, long l0, int percent) {
      getStatusBar();
    }

    public iStatusBar getStatusBar() {
      iStatusBar             sb = null;
      iWidget                w  = Platform.getContextRootViewer();
      iPlatformWindowManager wm = (w == null)
                                  ? null
                                  : w.getAppContext().getWindowManager();

      if (wm != null) {
        sb = wm.getStatusBar();
      }

      if (sb != null) {
        if (!initialized) {
          sb.progressStartIndeterminate(false);
          initialized = true;
        }
      }

      return sb;
    }
  }
}
