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

package com.appnativa.rare.platform.apple;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.apple.ui.view.Window;
import com.appnativa.rare.util.DataParser;
import com.appnativa.spot.SDFNode;
import com.appnativa.util.OrderedProperties;

import java.io.File;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * The main application
 *
 * @author Don DeCoteau
 */
public class Main extends Rare {

  /**
   * Constructs a new instance
   */
  public Main(String[] args) {
    super();

    try {
      OrderedProperties props = new OrderedProperties();

      info = initialize(args, props);
    } catch(Throwable e) {
      e.printStackTrace();
      exit();

      return;
    }

    argsInitialized();

    String file = info.applicationFile;
    URL    url  = null;

    if (file != null) {
      try {
        info.applicationFile = file;
        url                  = resolveApplicationURL(info.applicationFile);

        if (info.dumpSDF || info.dumpXML) {
          dump(url, info.dumpXML);
          System.exit(0);
        }

        createApplicationObject(new URL[] { url }, info.local);
      } catch(Exception e) {
        e.printStackTrace();
        exit();
      }
    }
  }

  @Override
  public void exit() {
    super.exit();
  }

  public void launch(Object nswindow) {
    ((AppContext) appContext).mainWindow = new Window(nswindow);
    start();
  }

  /**
   * Initializes the application
   *
   * @param args the application arguments
   *
   * @throws Exception
   */
  protected StartupInfo initialize(String[] args, OrderedProperties props) throws Exception {
    String  file     = null;
    boolean dumpXML  = false;
    boolean dumpSDF  = false;
    String  infoFile = null;
    String  local    = null;
    String  s;
    int     len = args.length;

    if (props == null) {
      props = new OrderedProperties();
    }

    List<String> cmdargs = new ArrayList<String>();

    for (int i = 0; i < len; i++) {
      s = args[i];

      if (s == null) {
        continue;
      }

      if (s.startsWith("-")) {
        if (s.startsWith("-S")) {
          cmdargs.add(s.substring(3));
        }
      } else if (file != null) {
        local = s;
      } else {
        file = s;
      }
    }

    if (file == null) {
      file = props.getProperty("application");

      if (file == null) {
        file = props.getProperty("applicationURL");
      }

      if (file == null) {
        file = props.getProperty("application");
      }
    }

    return new StartupInfo(file, infoFile, local, dumpXML, dumpSDF, cmdargs);
  }

  @Override
  protected URL resolveApplicationURL(String file) throws MalformedURLException {
    URL url = null;

    if (file == null) {
      throw new ApplicationException(Platform.getResourceAsString("loadError"));
    }

    try {
      if (file.startsWith("http") || file.startsWith("jar:") || file.startsWith(iConstants.LIB_PREFIX)) {
        if (file.startsWith(iConstants.LIB_PREFIX)) {
          url = this.resolveLibURL(file);
        } else {
          url = new URL(file);
        }
      } else if (file.startsWith("file:")) {
        url = new URL(file);
      } else {
        if (file.endsWith("/")) {
          if (new File(file + "application.sdf").exists()) {
            file = file + "application.sdf";
          }
        }

        File f = new File(file);

        f   = f.getCanonicalFile();
        url = PlatformHelper.fileToURL(f);
      }
    } catch(Throwable ex) {
      abort(ex);

      return null;
    }

    if (url == null) {
      throw new ApplicationException(Platform.getResourceAsString("loadError") + ":" + file);
    }

    return url;
  }

  private void dump(URL url, boolean dumpXML) {
    try {
      String        mime   = null;
      String        file   = url.getFile();
      boolean       xml    = false;
      boolean       sdf    = false;
      Reader        stream = null;
      URLConnection con    = url.openConnection();

      if (con instanceof HttpURLConnection) {
        int code = ((HttpURLConnection) con).getResponseCode();

        if (code == 401) {
          System.exit(0);
        }
      }

      mime = con.getContentType();

      if (mime == null) {
        mime = "text/x-sdf";
      }

      if (mime.startsWith(iConstants.SDF_MIME_TYPE)) {
        sdf = true;
      } else if (file.endsWith(".sdf")) {
        sdf = true;
      }

      if (xml || sdf) {
        stream = Rare.getReader(con);
      } else {
        System.exit(0);
      }

      setContextURL(url);

      StringWriter sw = new StringWriter();
      PrintWriter  pw = new PrintWriter(sw);

      DataParser.loadSPOTObjectSDF(getRootViewer(), stream, null, mime, url).toSDF(pw);
      pw.flush();

      SDFNode node  = SDFNode.parseForReformat(new StringReader(sw.toString()), null, file);
      SDFNode node2 = node.getFirstNode();

      if (node2 != null) {
        node = node2;
      }

      String name = node.getNodeName();

      if ((name != null) && name.startsWith("com.appnativa.rare.spot.")) {
        name = name.substring("com.appnativa.rare.spot.".length());
        node.setNodeName(name);
      }

      System.out.println(node.toString());
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private URL resolveLibURL(String file) throws UnsupportedEncodingException, MalformedURLException {
    return appContext.getResourceURL(file.substring(iConstants.LIB_PREFIX_LENGTH));
  }
}
