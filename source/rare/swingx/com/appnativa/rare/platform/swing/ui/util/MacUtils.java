/*
 * @(#)MacUtils.java   2010-03-07
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.util;

import java.awt.Image;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.ui.UIMenuItem;
import com.appnativa.rare.ui.aWindowManager;
import com.appnativa.rare.ui.iPlatformWindowManager;




/**
 *
 * @author Don DeCoteau
 */
public class MacUtils extends ApplicationAdapter {
  private static MacUtils _instance;
  UIMenuItem              aboutHandler;
  UIMenuItem              openFileHandler;
  UIMenuItem              preferencesHandler;
  UIMenuItem              printHandler;

  public MacUtils() {
    Application.getApplication().addApplicationListener(this);
  }

  @Override
  public void handleAbout(ApplicationEvent e) {
    if (aboutHandler != null) {
    	doClick(aboutHandler.getMenuItem());
      e.setHandled(true);
    } else {
      iPlatformWindowManager wm = AppContext.getContext().getWindowManager();

      if (wm instanceof aWindowManager) {
        ((aWindowManager) wm).showAboutDialog();
        e.setHandled(true);
      }
    }
  }

  public static boolean handleApplicationMenu(UIMenuItem item) {
    String name = item.getName();

    if (name != null) {
      if (iConstants.OPTIONS_ACTION_NAME.equals(name)) {
        getInstance().preferencesHandler = item;
        Application.getApplication().setEnabledPreferencesMenu(true);

        return true;
      }

      if (iConstants.PRINT_ACTION_NAME.equals(name)) {
        getInstance().printHandler = item;

        return true;
      }

      if (iConstants.OPEN_ACTION_NAME.equals(name)) {
        getInstance().openFileHandler = item;

        return true;
      }

      if (iConstants.ABOUT_ACTION_NAME.equals(name)) {
        getInstance().aboutHandler = item;
        Application.getApplication().setEnabledAboutMenu(true);

        return true;
      }
    }

    return false;
  }

  @Override
  public void handleOpenFile(ApplicationEvent e) {
    if (openFileHandler != null) {
    	doClick(openFileHandler.getMenuItem());
      e.setHandled(true);
    }
  }

  private void doClick(JComponent menuItem) {
  	if(menuItem instanceof JMenuItem) {
  		((JMenuItem)menuItem).doClick();
  	}
	}

	@Override
  public void handlePreferences(ApplicationEvent e) {
    if (preferencesHandler != null) {
    	doClick(preferencesHandler.getMenuItem());
      e.setHandled(true);
    }
  }

  @Override
  public void handlePrintFile(ApplicationEvent e) {
    if (printHandler != null) {
    	doClick(printHandler.getMenuItem());
      e.setHandled(true);
    }
  }

  @Override
  public void handleQuit(ApplicationEvent e) {
    e.setHandled(false);

    iPlatformAppContext app = AppContext.getContext();


    if (app != null) {
      app.exit();
    }
  }

  public static void setDockIconImage(Image image) {

    try {
      Application app = Application.getApplication();
      Method m=app.getClass().getMethod("setDockIconImage", Image.class);
      m.invoke(app, image);
     } catch(Throwable e) {
    }
  }

  public static Image getDockIconImage() {

    try {
      Application app = Application.getApplication();
      Method m=app.getClass().getMethod("getDockIconImage", Image.class);
      return (Image) m.invoke(app);
     } catch(Throwable e) {
       return null;
    }
  }

  public static void setDockIconBadge(String badge) {

    try {
      Application app = Application.getApplication();
      Method m=app.getClass().getMethod("setDockIconBadge", String.class);
      m.invoke(app, badge);
     } catch(Throwable e) {
    }
  }

  public static String getDockIconBadge(String badge) {

    try {
      Application app = Application.getApplication();
      Method m=app.getClass().getMethod("getDockIconBadge", String.class);
      return (String)m.invoke(app);
     } catch(Throwable e) {
       return null;
    }
  }
 

  public static MacUtils getInstance() {
    if (_instance == null) {
      _instance = new MacUtils();
    }

    return _instance;
  }

}
