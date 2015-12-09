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

package com.appnativa.rare.platform;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.appnativa.rare.Platform;
import com.appnativa.rare.TemplateHandler;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatform;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.NetHelper;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.AlertPanel;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.UIScreen.ScreenSize;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.util.DataItemParserHandler;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharArray;
import com.appnativa.util.ObjectHolder;
import com.appnativa.util.OrderedProperties;
import com.appnativa.util.SNumber;
import com.appnativa.util.iCancelable;
import com.appnativa.util.json.JSONObject;
import com.appnativa.util.json.JSONTokener;

public abstract class aPlatform implements iPlatform {
  protected Validator validator;
  private double      javaVersion;

  @Override
  public void addJarURL(URL url) {}

  @Override
  public File createCacheFile(String name) {
    File f = getCacheDir();

    return (f == null)
           ? null
           : new File(f, "uc_" + name);
  }

  @Override
  public void debugLog(String msg) {
    if (getAppContext().isDebugEnabled()) {
      System.err.println(msg);
    }
  }

  @Override
  public boolean deleteDirectory(File path) {
    if (path.exists()) {
      File[] files = path.listFiles();

      for (int i = 0; i < files.length; i++) {
        if (files[i].isDirectory()) {
          deleteDirectory(files[i]);
        } else {
          try {
            files[i].delete();
          } catch(Exception ignore) {}
        }
      }
    }

    try {
      return (path.delete());
    } catch(Exception ignore) {
      return false;
    }
  }

  @Override
  public boolean isDesktop() {
    return false;
  }

  public void errorMessage(String title, String message) {
    getAppContext().getWindowViewer().error(title, message, null);
  }

  @Override
  public Object evaluate(iWidget w, iScriptHandler sh, Object code, String event, EventObject e) {
    return aWidgetListener.evaluate(w, sh, code, event, e);
  }

  @Override
  public void execute(iWidget w, iScriptHandler sh, Object code, String event, EventObject e) {
    aWidgetListener.execute(w, sh, code, event, e);
  }

  @Override
  public void handlePlatformProperties(iWidget widget, Widget cfg, Map<String, Object> properties) {}

  @Override
  public void ignoreException(String msg, Throwable throwable) {
    try {
      aAppContext.getDefaultExceptionHandler(null).ignoreException(msg, throwable);
    } catch(Exception ignore) {
      if (getAppContext().isDebugEnabled()) {
        if (msg != null) {
          System.err.println(msg);
        }

        if (throwable != null) {
          throwable.printStackTrace(System.err);
        }
      }
    }
  }

  public void infoMessage(String title, String message) {
    infoMessage(title, message, null);
  }

  public void infoMessage(String title, String message, iFunctionCallback cb) {
    AlertPanel d = AlertPanel.ok(getAppContext().getWindowViewer(), title, message, null);

    d.showDialog(cb);
  }

  @SuppressWarnings("resource")
  @Override
  public Map<String, UIImageIcon> loadResourceIcons(iPlatformAppContext app, Map<String, UIImageIcon> appIcons,
          ActionLink link, boolean clear, boolean defaultDeferred)
          throws IOException {
    if (appIcons == null) {
      appIcons = new HashMap<String, UIImageIcon>();
    }

    Validator validator = getValidator();

    try {
      OrderedProperties icons = new OrderedProperties();

      icons.setSlashComment(true);
      icons.setPreserveDuplicates(true);
      icons.load(link.getReader());

      Iterator    it = icons.entrySet().iterator();
      Map.Entry   me;
      String      s, key;
      String      value;
      boolean     deferred;
      float       density;
      String      description;
      URL         url;
      UIImageIcon ic;
      UIRectangle slice;
      UIDimension size;
      ScalingType scaling;
      String      appRoot = null;
      iWidget     context = link.getContext();
      String      screen  = UIScreen.getRelativeScreenSizeName();
      UIImageIcon empty   = app.getResourceAsIcon("Rare.icon.empty");

      if (context == null) {
        context = app.getRootViewer();
      }
      CharArray ca=new CharArray();
      final Map<String, Object> map     = validator.options;
      URL                       linkURL = link.isInlineURL()
              ? context.getViewer().getContextURL()
              : link.getURL(context);
              
      while(it.hasNext()) {
        map.clear();
        me    = (Entry) it.next();
        key   = (String) me.getKey();
        value = icons.stripComment(me.getValue(), validator);

        if ((key != null) && (key.length() > 0) && (value != null) && (value.length() > 0)) {
          description = null;
          slice       = null;
          size        = null;
          density     = PlatformHelper.getScaledImageDensity();
          deferred    = defaultDeferred;
          scaling     = ScalingType.BILINEAR;
          ca.set(key);
          ca.toLowerCase();
          ca.replace('.', '_');
          key=ca.toString();
          if (!map.isEmpty()) {
            s = (String) map.get("screen");

            if ((s != null) &&!s.equals(screen)) {
              continue;
            }

            Iterator<String> mit = map.keySet().iterator();

            while(mit.hasNext()) {
              s = mit.next();

              if (s.equals("deferred")) {
                deferred = "true".equalsIgnoreCase((String) map.get(s));
              } else if (s.startsWith("alt")) {
                description = (String) map.get(s);
              } else if (s.equals("density")) {
                density = SNumber.floatValue((String) map.get(s));
              } else if (s.startsWith("slice")) {
                slice = Utils.getRectangle((String) map.get(s));
              } else if (s.startsWith("size")) {
                size = Utils.getSize((String) map.get(s));
              } else if (s.startsWith("scaling")) {
                try {
                  scaling = ScalingType.valueOf(((String) map.get(s)).toUpperCase(Locale.US));
                } catch(Exception e) {
                  Platform.ignoreException("invalid scaling type", e);
                }
              }
            }
          }

          if (value.length() > 0) {
            if ((appRoot != null) && value.startsWith("/")) {
              value = appRoot + value;
            }

            if (value.startsWith(iConstants.RESOURCE_PREFIX)) {
              value = value.substring(iConstants.RESOURCE_PREFIX_LENGTH);
              ic    = appIcons.get(value);

              if (ic == null) {
                ic = app.getResourceAsIcon(value);
              }

              if (ic == null) {
                continue;
              }
            } else {
              if (description == null) {
                description = iConstants.RESOURCE_PREFIX + key;
              }

              url = ((aAppContext) app).RARE.createURL(app, linkURL, value);
              ic  = (url == null)
                    ? empty
                    : PlatformHelper.createIcon(url, description, true, density);

              if (ic == null) {
                continue;
              }

              if (slice != null) {
                ic = (UIImageIcon) ic.getSlice(null, slice);
              }

              ic.setScalingType(scaling);

              if (size != null) {
                ic.scale(size.intWidth(), size.intHeight());
              }
            }

            if (!deferred) {
              PlatformHelper.loadIcon(app, ic);
            }

            appIcons.put(key, ic);
          }
        }
      }
    } finally {
      validator.clear();
      link.close();
    }

    return appIcons;
  }

  @Override
  public Map<String, String> loadResourceStrings(iPlatformAppContext app, Map<String, String> appStrings,
          ActionLink link, boolean clear)
          throws IOException {
    try {
      Reader r = link.getReader();

      if (appStrings == null) {
        appStrings = new OrderedProperties();
      } else if (clear) {
        appStrings.clear();
      }

      if (appStrings instanceof OrderedProperties) {
        ((OrderedProperties) appStrings).load(r);
      } else {
        OrderedProperties p = new OrderedProperties();

        p.setStripLeadingSpaces(false);
        p.load(r);
        appStrings.putAll(p);
      }
    } finally {
      link.close();
    }

    return appStrings;
  }

  @Override
  public void loadUIProperties(iWidget context, ActionLink link, UIProperties defs) throws IOException {
    try {
      OrderedProperties props = new OrderedProperties();

      props.setSlashComment(true);
      props.setPreserveDuplicates(true);
      props.load(link.getReader());

      Iterator  it = props.entrySet().iterator();
      Map.Entry me;
      Object    v;
      String    value;
      String    s;
      Validator validator = getValidator();
      UIColor   bg        = null;
      UIColor   fg        = null;

      while(it.hasNext()) {
        me    = (Entry) it.next();
        s     = (String) me.getKey();
        value = props.stripComment(me.getValue(), validator);

        if ((s != null) && (s.length() > 0) && (value != null) && (value.length() > 0)) {
          v = resolveUIProperty(context, s, value);

          if (v != null) {
            defs.put(s, v);

            if (s.equals("Rare.background")) {
              bg = (UIColor) v;

              if (fg != null) {
                ((aAppContext) getAppContext()).setThemeColors(fg, bg, false);
                fg = null;
                bg = null;
              }
            } else if (s.equals("Rare.backgroundShadow")) {
              defs.put("controlShadow", v);
            } else if (s.equals("Rare.foreground")) {
              fg = (UIColor) v;

              if (bg != null) {
                ((aAppContext) getAppContext()).setThemeColors(fg, bg, false);
                fg = null;
                bg = null;
              }
            } else if (s.equals("Rare.font.default")) {
              defs.put("Rare.font.default", v);
              UIFontHelper.setDefaultFont((UIFont) v);
            } else if (s.equals("Rare.templateURL")) {
              try {
                String url = (String) v;

                TemplateHandler.setDefaultTemplate(context.getAppContext(), url, false);
              } catch(MalformedURLException ex) {
                throw new RuntimeException(ex);
              }
            } else {
              handleUIProperty(context, defs, s, v);
            }
          } else if (!s.startsWith("//")) {
            Platform.ignoreException("loadUIProperties-Invalid:" + s + "=" + value, null);
          }
        }
      }

      if ((bg != null) && (fg != null)) {
        ((aAppContext) getAppContext()).setThemeColors(fg, bg, false);
      }

      validator.clear();
      s = defs.getString("Rare.ralativeScreenSize");

      if (s != null) {
        ScreenUtils.setScreenSize(ScreenSize.valueOf(s));
      }

      Integer d = defs.getInteger("Rare.net.readTimeout");

      if (d != null) {
        ActionLink.defaultReadTimeout = d.intValue();
      }
    } finally {
      link.close();
    }
  }

  public Object resolveUIProperty(iWidget context, String name, String propvalue) {
    return Utils.resolveUIProperty(context, name, propvalue);
  }

  @Override
  public iCancelable sendFormData(final iWidget context, final ActionLink link, final Map<String, Object> data,
                                  final boolean multipart, final ActionLink.ReturnDataType type,
                                  final iFunctionCallback cb) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        boolean canceled = false;
        Object  ret      = null;
        iWidget w        = context;

        if (w == null) {
          w = link.getContext();
        }

        try {
          link.sendFormDataEx(context, data, multipart);
          ret = new ObjectHolder(link, link.getContentType(), getLinkContent(w, link, type));
        } catch(Exception e) {
          canceled = true;
          ret      = e;
        } finally {
          link.closeEx();
        }

        if (cb != null) {
          final boolean b = canceled;
          final Object  r = ret;

          Platform.invokeLater(new Runnable() {
            @Override
            public void run() {
              cb.finished(b, r);
            }
          });
        }
      }
    };

    return getAppContext().executeBackgroundTask(r);
  }

  @Override
  public iCancelable uploadData(final iWidget context, final ActionLink link, final Object data, final String name,
                                final String mimeType, final String fileName, final ActionLink.ReturnDataType type,
                                final iFunctionCallback cb) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        boolean canceled = false;
        Object  ret      = null;
        iWidget w        = context;

        if (w == null) {
          w = link.getContext();
        }

        try {
          link.uploadDataEx(data, name, mimeType, fileName);
          ret = new ObjectHolder(link, link.getContentType(), getLinkContent(w, link, type));
        } catch(Exception e) {
          canceled = true;
          ret      = e;
        } finally {
          link.closeEx();
        }

        if (cb != null) {
          final boolean b = canceled;
          final Object  r = ret;

          Platform.invokeLater(new Runnable() {
            @Override
            public void run() {
              cb.finished(b, r);
            }
          });
        }
      }
    };

    return getAppContext().executeBackgroundTask(r);
  }

  @Override
  public iCancelable getContent(final iWidget context, final ActionLink link, final ActionLink.ReturnDataType type,
                                final iFunctionCallback cb) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        boolean canceled = false;
        Object  ret      = null;
        iWidget w        = context;

        if (w == null) {
          w = link.getContext();
        }

        try {
          ret = new ObjectHolder(link, link.getContentType(), getLinkContent(w, link, type));
        } catch(Exception e) {
          canceled = true;
          ret      = e;
        } finally {
          link.closeEx();
        }

        if (cb != null) {
          final boolean b = canceled;
          final Object  r = ret;

          Platform.invokeLater(new Runnable() {
            @Override
            public void run() {
              cb.finished(b, r);
            }
          });
        }
      }
    };

    return getAppContext().executeBackgroundTask(r);
  }

  @Override
  public List getCookieList() {
    return NetHelper.getCookieList();
  }

  @Override
  public String getCookies() {
    return NetHelper.getCookies();
  }

  @Override
  public double getJavaVersion() {
    if (javaVersion == 0) {
      String s = "1.5";

      try {
        s = System.getProperty("java.version", "1.5");
      } catch(Exception ignore) {}

      final int len = s.length();
      int       i   = 0;

      while(i < len) {
        if (Character.isDigit(s.charAt(i))) {
          break;
        }
      }

      if (i < len) {
        javaVersion = SNumber.doubleValue(s.substring(i));
      }

      if (javaVersion == 0) {
        javaVersion = 1.5f;
      }
    }

    return javaVersion;
  }

  @Override
  public boolean isEmbedded() {
    return false;
  }

  @Override
  public boolean isInSandbox() {
    return false;
  }

  @Override
  public boolean isTouchDevice() {
    return false;
  }

  @Override
  public boolean isTouchableDevice() {
    return false;
  }
  
  public boolean hasPhysicalKeyboard() {
    Object o=Platform.getUIDefaults().get("Rare.hasPhysicalKeyboard");
    if(o instanceof Boolean) {
      return ((Boolean)o).booleanValue();
    }
    return PlatformHelper.hasPhysicalKeyboard();
  }
  
  public boolean hasPointingDevice() {
    Object o=Platform.getUIDefaults().get("Rare.hasPointingDevice");
    if(o instanceof Boolean) {
      return ((Boolean)o).booleanValue();
    }
    return PlatformHelper.hasPointingDevice();
  }

  protected void handleUIProperty(iWidget context, UIProperties defs, String property, Object value) {}

  protected Object getLinkContent(iWidget w, ActionLink link, final ActionLink.ReturnDataType type) throws Exception {
    switch(type) {
      case STRING :
        return link.getContentAsString();

      case LINES : {
        Reader         r = link.getReader();
        BufferedReader br;

        if (r instanceof BufferedReader) {
          br = (BufferedReader) r;
        } else {
          br = new BufferedReader(r, 1024);
        }

        ArrayList list = new ArrayList();
        String    s;

        while((s = br.readLine()) != null) {
          list.add(s);

          return list;
        }
      }

      case JSON :
        return new JSONObject(new JSONTokener(link.getReader()));

      case LIST :
        return DataItemParserHandler.parse(w, link, 0);

      case TABLE :
        return DataItemParserHandler.parse(w, link, -1);

      case BYTES :
        return link.getContentAsBytes();

      case CONFIG :
        try {
          return DataParser.loadSPOTObject(w, link.getConnection(), null);
        } finally {
          link.closeEx();
        }
      case IMAGE :
      case ICON :
        URL     url = link.getURL(w);
        UIImage img = PlatformHelper.createImage(url, false, link.getImageDensity());

        return (type == ActionLink.ReturnDataType.IMAGE)
               ? img
               : new UIImageIcon(img);

      default :
        return null;
    }
  }

  protected Validator getValidator() {
    if (validator == null) {
      validator = new Validator(((aAppContext) getAppContext()).RARE);
    }

    return validator;
  }
}
