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

import com.appnativa.util.Streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;

import java.util.Properties;

/**
 *
 * @author Don DeCoteau
 */
public class Constants {

  /**  */
  public final static String TEXT_SYNTAX_ERROR_WIDGETS = getResourceAsString("text_syntax_error_widgets");

  /**  */
  public final static String TEXT_SYNTAX_ERROR_LOAD = getResourceAsString("text_syntax_error_load");

  /**  */
  public final static String TEXT_SHELL_FAILURE = getResourceAsString("text_shell_failure");

  /**  */
  public final static String TEXT_RESOURCE_NOT_FOUND = getResourceAsString("text_resource_not_found");

  /**  */
  public final static String TEXT_PREFIX_ENABLED = getResourceAsString("text_prefix_enabled");

  /**  */
  public final static String TEXT_PREFIX_DISABLED = getResourceAsString("text_prefix_disabled");

  /**  */
  public final static String TEXT_GOODBYE = getResourceAsString("text_goodbye");

  /**  */
  public final static String TEXT_EXIT_QUESTION = getResourceAsString("text_exit_question");

  /**  */
  public final static String TEXT_ENGINE_NOT_FOUND = getResourceAsString("text_engine_not_found");

  /**  */
  public final static String TEXT_ENGINE_LOADED = getResourceAsString("text_engine_loaded");

  /**  */
  public final static String TEXT_CHOOSE_LANGUAGE = getResourceAsString("text_choose_language");

  /**  */
  public final static String TEXT_APP_RELOADED = getResourceAsString("text_reload");
  // ResourceBundle for the app
  private volatile static Properties resources;

  private Constants() {}

  public static Reader getResourceAsReader(String file) throws IOException {
    String s = Constants.class.getName();
    int    n = s.lastIndexOf('.');

    if (n != -1) {
      s = s.substring(0, n);
    }

    String name = "/" + s + "/";

    name = name.replace('.', '/') + file;

    URL url = Constants.class.getResource(name);

    if (url == null) {
      return null;
    }

    InputStream in = url.openStream();

    return new InputStreamReader(in);
  }

  public static InputStream getResourceAsStream(String file) throws IOException {
    String s = Constants.class.getName();
    int    n = s.lastIndexOf('.');

    if (n != -1) {
      s = s.substring(0, n);
    }

    String name = "/" + s + "/";

    name = name.replace('.', '/') + file;

    URL url = Constants.class.getResource(name);

    if (url == null) {
      return null;
    }

    return url.openStream();
  }

  public static String getResourceAsString(String name) {
    if (resources == null) {
      resources = new Properties();

      try {
        resources.load(getResourceAsStream("scripting.properties"));
      } catch(Exception ex) {
        return "missing scripting.properties file";
      }
      // ResourceBundle.getBundle(Constants.class.getPackage().getName() + ".scripting"); //jdk1.6 bug
    }

    return resources.getProperty(name);
  }

  public static String getResourceStreamAsString(String file) throws IOException {
    return Streams.readerToString(getResourceAsReader(file));
  }
}
