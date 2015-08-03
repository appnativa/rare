/*
 * @(#)Application.java   2008-07-05
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import com.appnativa.rare.spot.Viewer;
import com.appnativa.spot.SDFNode;
import com.appnativa.spot.SPOTException;

/**
 *
 * @author decoteaud
 */
public class Application extends Viewer {
  public com.appnativa.rare.spot.Application application;
  public URL                                  viewerContextURL;

  public Application() {
    application = new com.appnativa.rare.spot.Application();
  }

  public Application(com.appnativa.rare.spot.Application app) {
    application = app;
  }

  public boolean fromSDF(SDFNode node) throws SPOTException {
    return application.fromSDF(node);
  }

  public boolean toSDF(Writer out, String classname, int depth, boolean validate, boolean outputempty)
          throws IOException {
    return application.toSDF(out, classname, depth, validate, outputempty);
  }

  public String toString(String classname) {
    return application.toString(null);
  }

}
