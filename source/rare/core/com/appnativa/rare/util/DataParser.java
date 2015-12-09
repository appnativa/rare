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

package com.appnativa.rare.util;

import com.appnativa.rare.Platform;
import com.appnativa.rare.TemplateHandler;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.exception.InvalidConfigurationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.ComboBox;
import com.appnativa.rare.spot.GridPane;
import com.appnativa.rare.spot.GroupBox;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.Region;
import com.appnativa.rare.spot.SplitPane;
import com.appnativa.rare.spot.StackPane;
import com.appnativa.rare.spot.TabPane;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.spot.WidgetPane;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.painter.iImagePainter;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SDFNode;
import com.appnativa.spot.SPOTAny;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;
import com.appnativa.util.ObjectHolder;
import com.appnativa.util.Streams;
import com.appnativa.util.UtilityMap;
import com.appnativa.util.iURLResolver;
import com.appnativa.util.json.JSONObject;

import java.io.Reader;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public class DataParser {
  private static final boolean _validate                             = Platform.isDebugEnabled();
  public static boolean        INLINE_SELECTED_STACKPANE_VIEWER_URLS = true;
  public static boolean        INLINE_REGION_VIEWER_URLS             = false;
  public static boolean        DISABLE_ALL_INLINING_OF_URLS          = false;

  public static Viewer checkAndInstantiateViewer(iWidget context, String mimeType, iURLConnection conn)
          throws Exception {
    if (mimeType == null) {
      mimeType = conn.getContentType();
    }

    if (mimeType.startsWith(iConstants.RML_MIME_TYPE) || mimeType.startsWith(iConstants.TEXT_MIME_TYPE)
        || mimeType.startsWith(iConstants.SDF_MIME_TYPE)) {
      return Utils.getViewerConfiguration((Widget) loadSPOTObjectSDF(context, conn.getReader(), null, mimeType,
              conn.getURL()));
    } else if (mimeType.startsWith(iConstants.XML_MIME_TYPE)) {
      throw new UnsupportedOperationException("XML format not supported");
    } else {
      return null;
    }
  }

  public static void checkForImages(iWidget context, Widget cfg, URL contextURL) throws Exception {
    boolean loadBackground = false;
    boolean loadOverlay    = false;

    while(cfg.bgImageURL.getValue() != null) {
      if ("true".equals(cfg.bgImageURL.spot_getAttribute("waitForLoad"))) {
        loadBackground = true;

        break;
      }

      if ("false".equals(cfg.bgImageURL.spot_getAttribute("deferred"))) {
        loadBackground = true;

        break;
      }

      break;
    }

    while(cfg.overlayImageURL.getValue() != null) {
      if ("true".equals(cfg.overlayImageURL.spot_getAttribute("waitForLoad"))) {
        loadOverlay = true;

        break;
      }

      if ("false".equals(cfg.overlayImageURL.spot_getAttribute("deferred"))) {
        loadOverlay = true;

        break;
      }

      break;
    }

    if (loadBackground) {
      iImagePainter p = Utils.configureImage(context, null, cfg.bgImageURL, false);

      cfg.bgImageURL.spot_setLinkedData(new ObjectHolder(p));
    }

    if (loadOverlay) {
      iImagePainter p = Utils.configureImage(context, null, cfg.overlayImageURL, false);

      cfg.overlayImageURL.spot_setLinkedData(new ObjectHolder(p));
    }
  }

  public static Viewer instantiateViewer(iWidget ctx, SDFNode node) throws Exception {
    Viewer                    v   = null;
    final iPlatformAppContext app = ctx.getAppContext();

    if (node != null) {
      String type = node.getNodeName();

      if (type.indexOf('.') == -1) {
        type = Platform.RARE_SPOT_PACKAGE_NAME + "." + type;
      }

      Class           cls     = PlatformHelper.loadClass(type);
      Widget          element = (Widget) cls.newInstance();
      TemplateHandler th      = TemplateHandler.getInstance(ctx, null);

      if (th != null) {
        element.spot_setTemplateHandler(th);
      }

      if (!element.fromSDF(node)) {
        String s = app.getResourceAsString("Rare.runtime.text.invalidConfigurationEx");

        throw new ApplicationException(Helper.expandString(s, element.toString()));
      }

      if (element instanceof Viewer) {
        v = (Viewer) element;
      } else {
        v = new WidgetPane(element);
      }
    }

    return v;
  }

  public static Viewer instantiateViewer(iWidget context, String mimeType, Reader r, URL contextURL) throws Exception {
    if (iConstants.XML_MIME_TYPE.equalsIgnoreCase(mimeType)) {
      throw new UnsupportedOperationException("XML format not supported");
    } else {
      return Utils.getViewerConfiguration((Widget) loadSPOTObjectSDF(context, r, null, mimeType, contextURL));
    }
  }

  /**
   * Returns an invalid configuration exception
   *
   * @param app
   *          the application context
   * @param e
   *          the initiating exception
   *
   * @return an invalid configuration exception
   */
  public static RuntimeException invalidConfigurationException(iPlatformAppContext app, Throwable e) {
    return invalidConfigurationException(app, e, (String) null);
  }

  public static RuntimeException invalidConfigurationException(iPlatformAppContext app, URL sourceURL) {
    String s = app.getResourceAsString("Rare.runtime.text.invalidConfiguration");

    s = Helper.expandString(s, (sourceURL == null)
                               ? app.getResourceAsString("Rare.runtime.text.unknown")
                               : sourceURL.toExternalForm());

    return new InvalidConfigurationException(s);
  }

  /**
   * Returns an invalid configuration exception
   *
   * @param app
   *          the application context
   * @param cfg
   *          the configuration that caused the exception
   *
   * @return an invalid configuration exception
   */
  public static RuntimeException invalidConfigurationException(iPlatformAppContext app, Widget cfg) {
    String s = app.getResourceAsString("Rare.runtime.text.invalidConfiguration");

    s = Helper.expandString(s, app.getResourceAsString("Rare.runtime.text.unknown"));
    s += "\r\n" + cfg.toString();

    return new InvalidConfigurationException(s);
  }

  public static RuntimeException invalidConfigurationException(String spec, String value) {
    return new InvalidConfigurationException(Helper.expandString(spec, value));
  }

  public static RuntimeException invalidConfigurationException(iPlatformAppContext app, Throwable e, ActionLink link) {
    String s = null;

    if (link != null) {
      try {
        URL u = link.getURL(null);

        if (u != null) {
          s = u.toString();
        } else {
          Widget w = link.getViewerConfiguration();

          if (w != null) {
            return invalidConfigurationException(app, e, w);
          }
        }
      } catch(Exception ignored) {}
    }

    return invalidConfigurationException(app, e, s);
  }

  public static RuntimeException invalidConfigurationException(iPlatformAppContext app, Throwable e, String url) {
    String s = app.getResourceAsString("Rare.runtime.text.invalidConfiguration");

    if (url == null) {
      s = Helper.expandString(s, app.getResourceAsString("Rare.runtime.text.unknown"));
    } else {
      s = Helper.expandString(s, url);
    }

    String em = Helper.exceptionString(e);

    return new InvalidConfigurationException(s + "\r\n" + em, e);
  }

  public static RuntimeException invalidConfigurationException(iPlatformAppContext app, Throwable e, Widget cfg) {
    String s = app.getResourceAsString("Rare.runtime.text.invalidConfiguration");

    s = Helper.expandString(s, app.getResourceAsString("Rare.runtime.text.unknown"));
    s += "\r\n" + ApplicationException.getMessageEx(e);
    s += "\r\n" + cfg.toString();

    return new InvalidConfigurationException(s, e);
  }

  public static RuntimeException invalidConfigurationException(iPlatformAppContext app, URL sourceURL, String msg) {
    String s = app.getResourceAsString("Rare.runtime.text.invalidConfiguration");

    s = Helper.expandString(s, (sourceURL == null)
                               ? app.getResourceAsString("Rare.runtime.text.unknown")
                               : sourceURL.toExternalForm());
    s += "\r\n" + msg;

    return new InvalidConfigurationException(s);
  }

  public static RuntimeException invalidConfigurationException(iPlatformAppContext app, URL url, Widget cfg) {
    String s   = app.getResourceAsString("Rare.runtime.text.invalidConfiguration");
    String ctx = (url == null)
                 ? app.getResourceAsString("Rare.runtime.text.unknown")
                 : url.toString();

    s = Helper.expandString(s, ctx);
    s += "\r\n" + cfg.toString();

    return new InvalidConfigurationException(s);
  }

  public static iSPOTElement loadSPOTObject(iWidget ctx, iURLConnection conn, iSPOTElement element) throws Exception {
    String       mime = conn.getContentType();
    iSPOTElement e;
    Reader       r   = conn.getReader();
    URL          url = conn.getURL();

    if (!Utils.isValidBaseURL(url)) {
      url = ctx.getViewer().getContextURL();
    }

    if ((mime != null) && mime.startsWith(iConstants.XML_MIME_TYPE)) {
      throw new UnsupportedOperationException("XML format not supported");
    } else {
      e = loadSPOTObjectSDF(ctx, r, element, mime, url);
    }

    return e;
  }

  public static iSPOTElement loadSPOTObjectSDF(iWidget context, Reader reader, iSPOTElement element, String mime,
          final URL contextURL)
          throws Exception {
    final iPlatformAppContext app = context.getAppContext();
    iURLResolver              ur  = (iURLResolver) context;
    final boolean             dm  = context.isDesignMode();
    SDFNode                   root;
    String                    s;

    try {
      if (DISABLE_ALL_INLINING_OF_URLS || dm) {
        root = SDFNode.parseForReformat(reader, ur, (contextURL == null)
                ? null
                : contextURL.toString());
      } else {
        root = SDFNode.parse(reader, ur, (contextURL == null)
                                         ? null
                                         : contextURL.toString(), dm);
      }
    } catch(Exception ex) {
      s = (contextURL == null)
          ? null
          : contextURL.toString();

      throw invalidConfigurationException(app, ex, s);
    }

    SDFNode node = root.getFirstBlockNode();

    if (node == null) {
      s = app.getResourceAsString("Rare.runtime.text.invalidConfigurationEx");

      throw new ApplicationException(Helper.expandString(s, contextURL.toString()));
    }

    if (element == null) {
      String  type  = node.getNodeName();
      boolean isset = false;

      if (type.equals("Set") && node.hasAttributes()) {
        s = (String) node.getNodeAttributes().get("type");

        if (s != null) {
          type  = s;
          isset = true;
        }
      }

      if (type.indexOf('.') == -1) {
        type = Platform.RARE_SPOT_PACKAGE_NAME + "." + type;
      }

      Class cls = PlatformHelper.loadClass(type);

      element = (iSPOTElement) cls.newInstance();

      if (isset) {
        SPOTSet set = new SPOTSet("Set", element, -1, -1, true);

        set.spot_setName("Set");
        element = set;
      }
    }

    TemplateHandler th = TemplateHandler.getInstance(context, contextURL);

    if ((element instanceof SPOTSequence) && (th != null)) {
      ((SPOTSequence) element).spot_setTemplateHandler(th);
    }

    if (!element.fromSDF(node)) {
      s = app.getResourceAsString("Rare.runtime.text.invalidConfigurationEx");

      throw new ApplicationException(Helper.expandString(s, element.toString()));
    }

    SDFNode first = root.getFirstNode();

    if ((first != node) && first.isComment()) {
      ArrayList<String> comments = null;
      String            comment  = first.getComment();
      int               len      = root.getChildCount();

      for (int i = 1; i < len; i++) {
        first = root.getChildNode(i);

        if (first == node) {
          break;
        }

        if (first.isComment()) {
          if (comments == null) {
            comments = new ArrayList<String>(2);
            comments.add(comment);
          }

          comments.add(first.getComment());
        }
      }

      if (comments != null) {
        element.spot_setHeaderComments(comments.toArray(new String[comments.size()]));
      } else {
        element.spot_setHeaderComments(new String[] { comment });
      }
    }

    s = Streams.readerToString(reader);

    if (s != null) {
      s = s.trim();
    }

    if ((s != null) && (s.length() > 0)) {
      element.spot_setLinkedData(s);
    }

    if (_validate) {
      s = element.spot_checkRangeValidityStr();

      if ((s != null) && (s.length() > 0)) {
        Platform.ignoreException(s, null);
      }
    }

    if (!dm && (element instanceof Viewer)) {
      element = checkElement(context, element, contextURL);
    }

    return element;
  }

  public static SPOTSet loadSPOTSet(iWidget ctx, ActionLink link, Class cls) throws Exception {
    SPOTSet set = new SPOTSet("Set", cls);

    try {
      return (SPOTSet) loadSPOTObject(ctx, link.getConnection(), set);
    } finally {
      link.close();
    }
  }

  public static SPOTSet loadSPOTSet(iWidget ctx, iURLConnection conn, Class cls) throws Exception {
    SPOTSet set = new SPOTSet("Set", cls);

    return (SPOTSet) loadSPOTObject(ctx, conn, set);
  }

  public static SPOTSet loadSPOTSet(iWidget ctx, SPOTPrintableString url, Class cls) throws Exception {
    if ((url == null) || (url.getValue() == null)) {
      return null;
    }

    ActionLink link = new ActionLink(ctx, url);
    SPOTSet    set  = new SPOTSet("Set", cls);

    try {
      return (SPOTSet) loadSPOTObject(ctx, link.getConnection(), set);
    } finally {
      link.close();
    }
  }

  public static ObjectHolder loadScriptURL(iWidget context, Viewer cfg, URL contextURL) throws Exception {
    ObjectHolder h = null;

    if (cfg.scriptURL.getValue() != null) {
      h = new ObjectHolder(null);
      cfg.scriptURL.spot_setLinkedData(h);

      iPlatformAppContext app  = context.getAppContext();
      ActionLink          link = new ActionLink(context, cfg.scriptURL);

      link.setContextURL(contextURL);

      try {
        boolean runonce = "true".equalsIgnoreCase(cfg.scriptURL.spot_getAttribute("runOnce"));
        String  type    = link.getContentType();
        String  code    = app.loadScriptCode(link, runonce);

        if (code != null) {
          if (link.isInlineURL()) {
            if (!link.isMimeTypeSet() || "text/plain".equals(type)) {
              type = app.getDefaultScrptingLanguage();
            }
          } else {
            type = app.getScriptType(link);
          }

          h.set(type, code);
        }

        cfg.scriptURL.spot_setLinkedData(h);
      } catch(Exception e) {
        h.value = e;
        app.resetRunOnce(link);    // allow the script to be reloaded and rerun
      }
    }

    return h;
  }

  /**
   * Parses name/value pairs from the specified string. THe mimeType attribute
   * is checked to ascertain the format of the data (the default is name=value
   * pairs separated by a semicolon if a delimiter attribute is present that
   * that delimiter is used instead).
   *
   * @param data
   *          the data to parse
   * @return the map of values
   */
  public static Map<String, Object> parseNameValuePairs(SPOTPrintableString data) {
    String type = data.spot_getAttribute("mimeType");

    if ((type == null) || (type.length() == 0) || (!type.equals("json") &&!type.equals("application/json"))) {
      String delim = data.spot_getAttribute("delimiter");
      char   c     = ((delim != null) && (delim.length() > 0))
                     ? delim.charAt(0)
                     : ';';

      return CharScanner.parseOptionString(data.getValue(), c);
    }

    String s = data.getValue();

    s = s.trim();

    if (!s.startsWith("{") &&!s.endsWith("}")) {
      s = "{" + s + "}";
    }

    JSONObject o = new JSONObject(s);

    return o.getObjectMap();
  }

  public static SPOTSequence resolveReference(iWidget context, SPOTSequence seq) {
    if (context == null) {
      context = Platform.getContextRootViewer();
    }

    String s = seq.spot_getAttribute("url");

    if ((s != null) && (s.length() > 0)) {
      try {
        DataParser.loadSPOTObject(context, context.getAppContext().openConnection(context.getURL(s)), seq);
      } catch(Exception ex) {
        Platform.ignoreException(null, ex);
      }
    }

    return seq;
  }

  public static SPOTSet resolveSet(iWidget context, SPOTSet set, Class cls) {
    if (context == null) {
      context = Platform.getContextRootViewer();
    }

    if ((set == null) || (set.size() > 0)) {
      return set;
    }

    String s = set.spot_getAttribute("url");

    if ((s != null) && (s.length() > 0)) {
      try {
        set = DataParser.loadSPOTSet(context, context.getAppContext().openConnection(context.getURL(s)), cls);
      } catch(Exception ex) {
        Platform.ignoreException(null, ex);
      }
    }

    return set;
  }

  public static Map<String, Object> getConfigStruct(Map<String, Object> options) {
    Object o = options.get("config");

    if (o instanceof Map) {
      return (Map<String, Object>) o;
    }

    Map<String, Object>             config = new HashMap<String, Object>();
    Iterator<Entry<String, Object>> it     = options.entrySet().iterator();

    while(it.hasNext()) {
      Entry<String, Object> e = it.next();
      String                s = e.getKey();

      if (s.startsWith("config.")) {
        config.put(s.substring(7), e.getValue());
        it.remove();
      }
    }

    return config;
  }

  public static UtilityMap getOptionsMapFromCustomProperties(Map<String, Object> options) {
    Object o = options.get("config");

    if (o instanceof Map) {
      return new UtilityMap((Map) o);
    }

    return new UtilityMap(options, "config.");
  }

  static iSPOTElement checkForConfigURL(iWidget context, GroupBox vcfg, URL contextURL) throws Exception {
    if (vcfg.dataURL.getValue() != null) {
      ActionLink link = new ActionLink(context, vcfg.dataURL);

      try {
        link.setContextURL(contextURL);
        vcfg.dataURL.spot_clear();

        GroupBox cfg = vcfg.getClass().newInstance();

        DataParser.loadSPOTObject(context, link.getConnection(), cfg);
        cfg.spot_copySharedMemberValuesEx(vcfg);
        Map map = vcfg.spot_getAttributesEx();
        if(map!=null) {
          cfg.spot_addAttributes(map);
        }
        vcfg = cfg;
      } catch(Exception e) {
        return vcfg;
      } finally {
        link.closeEx();
      }
    }

    final SPOTSet set = vcfg.widgets;
    final int     len = set.size();
    iSPOTElement  e;

    for (int i = 0; i < len; i++) {
      final Object cfg = set.getEx(i);

      if (cfg instanceof Viewer) {
        e = checkElement(context, (Viewer) cfg, contextURL);

        if (cfg != e) {
          ((SPOTAny) set.get(i)).setValue(e);
        }
      }
    }

    return vcfg;
  }

  static void checkForConfigURL(iWidget context, SPOTSet set, URL contextURL) throws Exception {
    final int     len    = set.size();
    final boolean inline = INLINE_REGION_VIEWER_URLS;

    for (int i = 0; i < len; i++) {
      final Region r   = (Region) set.get(i);
      final Object cfg = r.viewer.getValue();

      if (cfg instanceof GroupBox) {
        checkForConfigURL(context, (GroupBox) cfg, contextURL);
      } else if (cfg instanceof StackPane) {
        checkForConfigURL(context, (StackPane) cfg, contextURL);
      } else if (cfg instanceof WidgetPane) {
        checkForConfigURL(context, (WidgetPane) cfg, contextURL);
      } else if (cfg instanceof GridPane) {
        checkForConfigURL(context, ((GridPane) cfg).regions, contextURL);
      } else if (r.dataURL.getValue() != null) {
        if ("false".equals(r.dataURL.spot_getAttribute("deferred")) || inline) {
          ActionLink link = new ActionLink(context, r.dataURL);

          try {
            link.setContextURL(contextURL);
            r.viewer.setValue(DataParser.loadSPOTObject(context, link.getConnection(), null));
          } finally {
            link.closeEx();
          }
        }
      }

      if (cfg instanceof Widget) {
        checkForImages(context, (Widget) cfg, contextURL);
      }
    }
  }

  static iSPOTElement checkForConfigURL(iWidget context, StackPane vcfg, URL contextURL) {
    SPOTSet set = vcfg.getViewerURLsReference();
    int     n   = vcfg.selectedIndex.intValue();

    if (!INLINE_SELECTED_STACKPANE_VIEWER_URLS) {
      n = -1;
    }

    boolean loadall = !vcfg.loadOnActivation.booleanValue();
    int     len     = set.size();

    try {
      for (int i = 0; i < len; i++) {
        SPOTPrintableString ps = (SPOTPrintableString) set.get(i);

        if (!loadall) {
          String s = ps.spot_getAttribute("deferred");

          if ("true".equals(s)) {
            continue;
          }

          if ((i != n) &&!"false".equals(s)) {
            continue;
          }
        }

        ActionLink link = new ActionLink(context, ps);

        try {
          link.setContextURL(contextURL);

          Viewer cfg = (Viewer) DataParser.loadSPOTObject(context, link.getConnection(), null);

          ps.spot_setLinkedData(cfg);
        } finally {
          link.closeEx();
        }
      }

      set = vcfg.getViewers();
      len = (set == null)
            ? 0
            : set.size();

      for (int i = 0; i < len; i++) {
        final SPOTAny any = (SPOTAny) set.get(i);
        final Object  cfg = any.getValue();

        if (cfg instanceof GroupBox) {
          any.setValue(checkForConfigURL(context, (GroupBox) cfg, contextURL));
        } else if ((cfg instanceof StackPane)) {
          any.setValue(checkForConfigURL(context, (StackPane) cfg, contextURL));
        } else if ((cfg instanceof WidgetPane)) {
          any.setValue(checkForConfigURL(context, (WidgetPane) cfg, contextURL));
        } else if ((cfg instanceof TabPane)) {
          any.setValue(checkForConfigURL(context, (TabPane) cfg, contextURL));
        }

        if (cfg instanceof Widget) {
          checkForImages(context, (Widget) cfg, contextURL);
        }
      }
    } catch(Exception ignore) {}

    return vcfg;
  }

  static iSPOTElement checkForConfigURL(iWidget context, TabPane vcfg, URL contextURL) throws Exception {
    final SPOTSet set = vcfg.tabs;
    int           n   = vcfg.selectedIndex.intValue();
    int           len = (set == null)
                        ? 0
                        : set.size();

    for (int i = 0; i < len; i++) {
      Region r = (Region) set.get(i);

      if (r.viewer.getValue() != null) {
        checkElement(context, r.viewer.getValue(), contextURL);
      } else if (i == n) {
        if (r.dataURL.getValue() != null) {
          if ("false".equals(r.dataURL.spot_getAttribute("deferred"))) {
            ActionLink link = new ActionLink(context, r.dataURL);

            try {
              link.setContextURL(contextURL);
              r.viewer.setValue(DataParser.loadSPOTObject(context, link.getConnection(), null));
            } finally {
              link.closeEx();
            }
          }
        }
      }
    }

    return vcfg;
  }

  static iSPOTElement checkForConfigURL(iWidget context, WidgetPane vcfg, URL contextURL) {
    try {
      if (vcfg.dataURL.getValue() == null) {
        if (vcfg.widget.getValue() != null) {
          checkElement(context, vcfg.widget.getValue(), contextURL);
        }

        return vcfg;
      }

      String s = vcfg.dataURL.spot_getAttribute("deferred");

      if ("true".equals(s)) {
        return vcfg;
      }

      ActionLink link = new ActionLink(context, vcfg.dataURL);

      try {
        link.setContextURL(contextURL);

        Viewer cfg = (Viewer) DataParser.loadSPOTObject(context, link.getConnection(), null);

        vcfg.dataURL.spot_setLinkedData(cfg);
      } finally {
        link.closeEx();
      }
    } catch(Exception ignore) {}

    return vcfg;
  }

  static iSPOTElement loadElement(iWidget context, URL contextURL, String url) {
    try {
      ActionLink link = new ActionLink(context, url, null);

      try {
        link.setContextURL(contextURL);

        return DataParser.loadSPOTObject(context, link.getConnection(), null);
      } finally {
        link.closeEx();
      }
    } catch(Exception ignore) {
      return null;
    }
  }

  public static iSPOTElement checkElement(iWidget context, iSPOTElement element, URL contextURL) throws Exception {
    if (!DISABLE_ALL_INLINING_OF_URLS &&!context.isDesignMode()) {
      if (element instanceof Viewer) {
        Viewer viewer = (Viewer) element;

        if (viewer.scriptURL.spot_hasValue()) {
          String s = viewer.scriptURL.spot_getAttribute("inline");

          if (!"true".equals(s)) {
            DataParser.loadScriptURL(context, viewer, contextURL);
          }
        }
      }

      if (element instanceof GridPane) {
        checkForConfigURL(context, ((GridPane) element).regions, contextURL);
      } else if (element instanceof SplitPane) {
        checkForConfigURL(context, ((SplitPane) element).regions, contextURL);
      } else if (element instanceof TabPane) {
        checkForConfigURL(context, ((TabPane) element).tabs, contextURL);
        checkForConfigURL(context, ((TabPane) element), contextURL);
      } else if (element instanceof GroupBox) {
        element = checkForConfigURL(context, ((GroupBox) element), contextURL);
      } else if (element instanceof StackPane) {
        checkForConfigURL(context, ((StackPane) element), contextURL);
      } else if (element instanceof WidgetPane) {
        checkForConfigURL(context, ((WidgetPane) element), contextURL);
      } else if (element instanceof ComboBox) {
        ComboBox cb = (ComboBox) element;

        if ((cb.componentType.getValue() == ComboBox.CComponentType.widget) && (cb.popupWidget.getValue() == null)) {
          String url = cb.popupWidget.spot_getAttribute("url");

          if (url != null) {
            cb.popupWidget.spot_setLinkedData(loadElement(context, contextURL, url));
          }
        }
      } else if (element instanceof PushButton) {
        PushButton pb = (PushButton) element;

        if (pb.popupWidget.getValue() == null) {
          String url = pb.popupWidget.spot_getAttribute("url");

          if ((url != null) &&!"false".equalsIgnoreCase(pb.popupWidget.spot_getAttribute("deferred"))) {
            pb.popupWidget.spot_setLinkedData(loadElement(context, contextURL, url));
          }
        }
      } else if (element instanceof MainWindow) {
        MainWindow   mw = (MainWindow) element;
        iSPOTElement e  = mw.getMenuBar();

        if (e != null) {
          checkElement(context, e, contextURL);
        }

        e = mw.getStatusBar();

        if (e != null) {
          checkElement(context, e, contextURL);
        }

        e = mw.viewer.getValue();

        if (e != null) {
          checkElement(context, e, contextURL);
        }

        SPOTSet set = mw.getToolbars();

        if (set != null) {
          checkForConfigURL(context, set, contextURL);
        }
      } else if (element instanceof Widget) {
        checkForImages(context, (Widget) element, contextURL);
      }
    }

    return element;
  }
}
