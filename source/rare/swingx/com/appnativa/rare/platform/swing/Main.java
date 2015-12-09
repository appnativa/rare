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
import com.appnativa.rare.iConstants;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.ui.util.MacUtils;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.util.DataParser;
import com.appnativa.spot.SDFNode;
import com.appnativa.util.CharScanner;
import com.appnativa.util.OrderedProperties;
import com.appnativa.util.SNumber;

import java.io.File;
import java.io.FileInputStream;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

/**
 * The main application
 *
 * @author Don DeCoteau
 */
public class Main extends Rare {
  static {
    try {
      System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    } catch(Throwable ignore) {}
  }

  public Main() {
    super();
  }

  /**
   * Constructs a new instance
   */
  public Main(String[] args) {
    super();

    if (Platform.isMac()) {
      try {
        MacUtils.getInstance();

        String s = System.getProperty("rare.dock.icon", System.getProperty("jnlp.dock.icon", null));

        if ((s == null) && (MacUtils.getDockIconImage() == null)) {
          s = "com/appnativa/rare/resources/rare32.png";
        }

        if (s != null) {
          try {
            URL url = Platform.getAppContext().getResourceURL(s);

            if (url != null) {
              MacUtils.setDockIconImage(ImageIO.read(url));
            }
          } catch(Throwable e) {}
        }

        s = System.getProperty("apple.laf.useScreenMenuBar", "");

        if (s.length() == 0) {
          System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
      } catch(Exception e) {
        ignoreStartupException(null, e);
      }
    }

    try {
      OrderedProperties props = new OrderedProperties();

      if (!webContext) {
        try {
          FileInputStream f = new FileInputStream("rare.properties");

          props.load(f);
          f.close();
        } catch(Exception ignore) {}
      }

      info = initialize(args, props);
    } catch(Throwable e) {
      String loc = (info == null)
                   ? null
                   : info.applicationFile;

      showStartupError(loc, e, true);

      return;
    }

    commandLineArgs = Arrays.asList(args);
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
        String loc = (url != null)
                     ? JavaURLConnection.toExternalForm(url)
                     : info.applicationFile;

        showStartupError(loc, e, true);
      }
    }
  }
  
  /**
   * Called when the escape is pressed in the main window
   */
  public void onEscapeKeyPressed() {
    
  }
  
  protected void embedInitialize(URL url, Application app) throws Exception {
    commandLineArgs = Collections.EMPTY_LIST;
    info            = new StartupInfo(JavaURLConnection.toExternalForm(url), null, null, false, false, commandLineArgs);

    if (app == null) {
      createApplicationObject(new URL[] { url }, info.local);
    } else {
      setupApplicationObject(url, app, "text/x-sdf");
    }
  }

  @Override
  public void exit() {
    super.exit();
  }

  public static void main(String[] args) {
    Main m = new Main(args);

    m.start();
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

    addLibs(props.getProperty("libs"));
    setupSecurity(props);
    configureFromProperties(props);

    List<String> cmdargs = new ArrayList<String>();

    for (int i = 0; i < len; i++) {
      s = args[i];

      if (s == null) {
        continue;
      }

      if (s.startsWith("-")) {
        if (s.startsWith("-S")) {
          cmdargs.add(s.substring(3));
        } else if (!webContext) {
          if (s.equalsIgnoreCase("-libs")) {
            addLibs(args[++i]);
          } else if (s.equalsIgnoreCase("-shell")) {
            shellEnabled = true;
          } else if (s.equalsIgnoreCase("-port") && (i + 1) < len) {
            shellPort = SNumber.intValue(args[++i]);
          } else if (s.equalsIgnoreCase("-dumpinfo") && (i + 1) < len) {
            infoFile = args[++i];
          } else if (s.equalsIgnoreCase("-dumpxml")) {
            dumpXML = true;
          } else if (s.equalsIgnoreCase("-dumpsdf")) {
            dumpSDF = true;
          }
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

    if (shellEnabled && (shellPort < 1)) {
      usage();
      System.exit(1);
    }

    if (isShellEnabled()) {
      try {
        PlatformHelper.loadClass("net.wimpi.telnetd.shell.Shell");
      } catch(Exception e) {
        e.printStackTrace();
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
        } else if (webContext) {
          url = new URL(WebStartHelper.getCodeBase(), file);
        } else {
          url = new URL(file);
        }
      } else if (file.startsWith("file:")) {
        url = new URL(file);
      } else {
        if (file.endsWith("/")) {
          if (new File(file + "application.sdf").exists()) {
            file = file + "application.sdf";
          } else if (new File(file + "application.xml").exists()) {
            file = file + "application.xml";
          }
        }

        File f = new File(file);

        f   = f.getCanonicalFile();
        url = f.toURI().toURL();
      }
    } catch(Throwable ex) {
      abort(ex);

      return null;
    }

    if (url == null) {
      throw new ApplicationException(Platform.getResourceAsString("loadError") + ":" + file);
    }

    if (webContext) {
      validateWebStartURL(url);
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
        mime = "text/x-rml";
      }

      if (mime.startsWith(iConstants.SDF_MIME_TYPE) || mime.startsWith(iConstants.RML_MIME_TYPE)) {
        sdf = true;
      } else if (file.endsWith(".sdf") || file.endsWith(".rml")) {
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

  private void usage() {
    String us    = appContext.getResourceAsString("Rare.runtime.text.usage");
    String url   = appContext.getResourceAsString("Rare.runtime.text.usageUrl");
    String port  = appContext.getResourceAsString("Rare.runtime.text.usagePort");
    String shell = appContext.getResourceAsString("Rare.runtime.text.usageShell");
    String s     = getClass().getName();

    System.err.println(us + " " + s + " url [-shell -port <portNumber>]");
    System.err.println("\turl \t- : " + url);
    System.err.println("\t-shell \t- : " + shell);
    System.err.println("\t-port \t- : " + port);
  }

  /**
   * Checks to see if web start is supported for the specified URL
   * THe URL must reference the local host or has a host name
   * that ends with one of the suffix strings in the webStartDomainSuffix
   * resource string.
   *
   * NOTE: You will need to modify the Bundle.properties in the runtime jar
   *       in order to support other that local webstart access. Once you make this change
   *       you will need to sign the jars with your certificate
   */
  private void validateWebStartURL(URL u) throws MalformedURLException {
    String  url     = JavaURLConnection.toExternalForm(u);
    String  origUrl = url;
    boolean valid   = false;
    // check to see if http urls are valid for webstart
    // to enable set the webStartHTTP in the bundle.properties file and resign the jar
    boolean http = "true".equalsIgnoreCase(getResourceBundle().getString("webStartHTTP"));    // whether to allow http urls for webstart (otherise only jars and file)

    do {
      String prot = u.getProtocol().toLowerCase(Locale.ENGLISH);
      String host = null;

      try {
        if (url.startsWith("jar:")) {
          url  = url.substring(4);
          http = true;
        } else if (url.startsWith(iConstants.LIB_PREFIX)) {
          url  = url.substring(iConstants.LIB_PREFIX_LENGTH);
          http = true;
        }

        if (url != origUrl) {
          u    = new URL(appContext.getDocumentBase(), url);
          prot = u.getProtocol().toLowerCase(Locale.ENGLISH);
        }

        host = u.getHost().toLowerCase(Locale.ENGLISH);

        if (host.equals("localhost") || host.equals("127.0.0.1") || prot.equals("file")) {
          valid = true;

          break;
        }
      } catch(Exception e) {
        ignoreException(null, e);

        break;
      }

      if (prot.startsWith("http") &&!http) {
        throw new ApplicationException(getResourceBundle().getString("cannotLaunchHTTP"));
      }

      try {
        // check what domains are allowed to use werstart
        // //set the domains in the webStartDomainSuffix propertyin the bundle.properties file and resign the jar
        String domains = getResourceBundle().getString("webStartDomainSuffix");

        domains = domains.trim();

        if ((domains.length() == 0) || domains.equals("*")) {
          break;
        }

        List<String> list = CharScanner.getTokens(domains, ',', true);
        int          len  = list.size();

        for (int i = 0; i < len; i++) {
          if (host.endsWith(list.get(i))) {
            valid = true;

            break;
          }
        }
      } catch(Exception e) {
        ignoreException(null, e);
      }
    } while(false);

    if (!valid) {
      throw new ApplicationException(getResourceBundle().getString("cannotLaunchDomain"));
    }
  }

  private boolean isShellEnabled() {
    return shellEnabled;
  }
}
