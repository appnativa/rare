/*
 * @(#)MenuBarViewer.java   2011-12-26
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import com.appnativa.rare.platform.swing.ui.view.JMenuBarEx;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.ui.ActionBar;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.iMenuBarComponent;

/**
 * A viewer for menu items on a menu bar.
 *
 * @author Don DeCoteau
 */
public class MenuBarViewer extends aMenuBarViewer {

  /**
   * Constructs a new instance
   */
  public MenuBarViewer() {
    super(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public MenuBarViewer(iContainer parent) {
    super(parent);
  }

  /**
   * Creates a menu bar from the specified configuration
   *
   * @param context the viewer context for the menu bar
   * @param cfg the menu bar configuration
   *
   * @return a handle to a new menu bar
   */
  public static MenuBarViewer createMenuBar(iContainer context, MenuBar cfg) {
    MenuBarViewer v = new MenuBarViewer();

    v.setParent(context);
    v.create(cfg);

    return v;
  }

  @Override
  public JMenuBarEx getMenuBar() {
    return (JMenuBarEx) menuBar;
  }

  /**
   * Creates the actual menu bar component
   *
   * @param cfg the configuration
   * @return the menu bar component
   */
  @Override
  protected iMenuBarComponent createMenuBarAndComponents(MenuBar cfg) {
    if("true".equals(cfg.spot_getAttribute("installAsActionBar"))) {
      ActionBar ab=new ActionBar(this,cfg);
      formComponent=dataComponent=ab;
      return ab;
    }

    JMenuBarEx b = new JMenuBarEx();

    dataComponent = formComponent = new Component(b);

    return b;
  }
}
