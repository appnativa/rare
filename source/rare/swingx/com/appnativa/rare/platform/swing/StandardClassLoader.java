/*
 * @(#)StandardClassLoader.java   2007-07-10
 * 
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
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
