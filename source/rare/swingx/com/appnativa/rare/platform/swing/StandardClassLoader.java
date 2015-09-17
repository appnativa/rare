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

import java.net.URL;
import java.net.URLClassLoader;

/**
 * A class loader that maintains a URL repository that can be added to dynamically.
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class StandardClassLoader extends URLClassLoader {

  /**
   * Constructs a new instance
   */
  public StandardClassLoader() {
    super(new URL[0], StandardClassLoader.class.getClassLoader());
  }

  /**
   * Constructs a new instance
   *
   * @param loader the parent class loader
   */
  public StandardClassLoader(ClassLoader loader) {
    super(new URL[0], loader);
  }

  /**
   * Adds a JAR URL to the repository
   *
   * @param url  the URL to add
   */
  public void addRepository(URL url) {
    super.addURL(url);
  }
}
