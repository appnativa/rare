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

package com.appnativa.rare.scripting;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.util.Streams;

import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.ScriptRuntime.MessageProvider;

import java.io.IOException;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.PropertyResourceBundle;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;

/**
 *
 * @author Don DeCoteau
 */
public class ScriptManager extends aJavaScriptManager {

  /**
   * Constructs a new instance
   * @param ctx the application context
   * @param app the application configuration
   */
  public ScriptManager(iPlatformAppContext ctx, Application app) {
    super(ctx, app);
  }

  public ScriptManager(iPlatformAppContext app, ScriptEngine engine, Bindings bindings, Frame frame, String scriptName,
                       String source) {
    super(app, engine, bindings, frame, scriptName, source);
  }

  public iScriptHandler getRootHandler(iPlatformAppContext app, iWindow frame, String type, String name, String source,
          boolean share) {
    ScriptEngine engine = getEngine(type, false, null);

    if (name == null) {
      if (source == null) {
        name = scriptName;
      } else {
        name = "window";
      }
    }

    return new ScriptManager(app, engine, share
            ? mainWindowBindings
            : null, (Frame) frame, name, source);
  }

  protected void addLoadedScript(String name) {}

  protected void initializeRhino() {
    super.initializeRhino();
    RhinoContextFactory.setOptimizationEnabled(false);
    ScriptRuntime.messageProvider = new MyMessageProvider();
  }

  protected void configureDebuggingInfo(ScriptEngine engine, ScriptContext ctx, String name, String source) {}

  protected WindowViewer createWindowViewer(iPlatformAppContext ctx, String name, Object win, WindowViewer parent,
          iScriptHandler sh) {
    return new WindowViewer(ctx, name, (Frame) win, parent, sh);
  }

  protected void handleScriptTracing(String source) {}

  protected void loadDebugger() {}

  protected void setupDebugger() {}

  protected void setupScriptingShell(ScriptEngine engine) {}

  protected String spiClassNameForJavascripEngineFactory() {
    return null;
  }

  static class MyMessageProvider implements MessageProvider {
    boolean                bundleLoaded;
    PropertyResourceBundle resources;

    public String getMessage(String messageId, Object[] arguments) {
      if (!bundleLoaded) {
        getBundle();
      }

      String msg = null;

      if (resources != null) {
        msg = resources.getString(messageId);
      }

      if (msg != null) {
        /*
         * It's OK to format the string, even if 'arguments' is null;
         * we need to format it anyway, to make double ''s collapse to
         * single 's.
         */
        MessageFormat formatter = new MessageFormat(msg);

        return formatter.format(arguments);
      }

      return ("Could not format Rhino error: no message resource found for message property " + messageId);
    }

    String toBundleName(String baseName, Locale locale) {
      String language = locale.getLanguage();
      String country  = locale.getCountry();
      String variant  = locale.getVariant();

      if ((language.length() == 0) && (country.length() == 0) && (variant.length() == 0)) {
        return baseName + ".properties";
      }

      StringBuilder sb = new StringBuilder(baseName);

      sb.append('_');

      if (variant.length() != 0) {
        sb.append(language).append('_').append(country).append('_').append(variant);
      } else if (country.length() != 0) {
        sb.append(language).append('_').append(country);
      } else {
        sb.append(language);
      }

      sb.append(".properties");

      return sb.toString();
    }

    void getBundle() {
      bundleLoaded = true;

      try {
        Integer id = AppContext.getResourceId(AppContext.getAndroidContext(), "raw/rare_raw_rhinomessages");

        resources = new PropertyResourceBundle(AppContext.getAndroidContext().getResources().openRawResource(id));
      } catch(IOException ex) {
        Platform.ignoreException("RhinoMessages.properties", ex);
      }
    }
  }


  @Override
  protected String getInitScript(String name) {
    try {
      int n = name.lastIndexOf('.');

      if (n != -1) {
        name = name.substring(0, n);
      }

      Integer id = AppContext.getResourceId(AppContext.getAndroidContext(), "raw/rare_raw_" + name);

      if ((id != null) && (id != 0)) {
        return Streams.streamToString(AppContext.getAndroidContext().getResources().openRawResource(id));
      }
    } catch(IOException ex) {
      Platform.ignoreException("RhinoMessages.properties", ex);

      return null;
    }

    return null;
  }
}
