/*
 * @(#)DesignPlatformImpl.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.platform.swing.PlatformImpl;

public class DesignPlatformImpl extends PlatformImpl {
  static DesignPlatformImpl instance;
  static AppContext         mainContext;
  URLClassLoader           swingProjectClassLoader;
  boolean                   classLoaderChecked;

  public DesignPlatformImpl(AppContext appContext) {
    super(appContext);
    mainContext = appContext;
    instance = this;
  }

  public static void switchContext(DesignAppContext context) {
    if ((context != null) && !context.isDisposed()) {
      instance.switchContextEx(context);
    }
  }

  @Override
  public iPlatformAppContext getAppContext() {
    if ((appContext != null) && appContext.isDisposed()) {
      appContext = mainContext;
    }

    return appContext;
  }

  @Override
  public Class loadClass(String name) throws ClassNotFoundException {
    try {
      return super.loadClass(name);
    } catch (ClassNotFoundException e) {
      if (!classLoaderChecked) {
        classLoaderChecked = true;
        try {
          IProject p=Studio.findSwingProject();
          if(p!=null) {
            p.open(null /* IProgressMonitor */);
            IJavaProject javaProject = JavaCore.create(p); 
            String[] classPathEntries = JavaRuntime.computeDefaultRuntimeClassPath(javaProject);
            List<URL> urlList = new ArrayList<URL>();
            for (int i = 0; i < classPathEntries.length; i++) {
             String entry = classPathEntries[i];
             IPath path = new Path(entry);
             URL url = path.toFile().toURI().toURL();
             urlList.add(url);
            }
            ClassLoader parentClassLoader = javaProject.getClass().getClassLoader();
            URL[] urls = (URL[]) urlList.toArray(new URL[urlList.size()]);
            swingProjectClassLoader = new URLClassLoader(urls, parentClassLoader);            
          }
        } catch (Exception ee) {
          ignoreException(null, ee);
        }
      }
      if (swingProjectClassLoader != null) {
        return swingProjectClassLoader.loadClass(name);
      } else {
        throw e;
      }
    }
  }

  private void switchContextEx(DesignAppContext context) {
    if (context != appContext) {
      appContext = context;
      context.makeActiveContext();
    }
  }
}
