/*
 * @(#)UIPopupMenu.java   2012-02-17
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import com.appnativa.rare.Platform;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;

/**
 * A viewer for menu items on a menu bar.
 *
 * @author Don DeCoteau
 */
public class UIPopupMenu extends UIMenu {
  public UIPopupMenu() {
    super();
    
  }

  public UIPopupMenu(iWidget context, MenuBar cfg) {
    super(context, cfg);
  }

  public UIPopupMenu(iWidget context, SPOTSet menus) {
    super(context, menus);
  }

  public UIPopupMenu(iWidget context, SPOTSet menus, boolean addTextItems) {
    super(context, menus, addTextItems);
  }
  @Override
  public void show(iWidget context, boolean modal) {
    show(context, null, modal);
  }

  /**
   * Shows the popup menu
   *
   * @param x the x-position
   * @param y the y-position
   */
  public void show(int x, int y) {
    iWidget            w     = this.getContextWidget();
    iPlatformComponent owner = null;

    if (w != null) {
      owner = w.getDataComponent();
    }

    show(owner, x, y);
  }

  /**
   * Shows the popup menu
   *
   * @param owner the owner of the menu
   * @param x the x-position
   * @param y the y-position
   */
  @Override
  public void show(iPlatformComponent owner, int x, int y) {
    JPopupMenu p = getPopupMenu();
    iWidget    w = this.getContextWidget();

    if (w == null) {
      w = Platform.findWidgetForComponent(owner);
    }

    if (owner == null) {
      owner = w.getContainerComponent();
    }

    p.show(owner.getView(), x, y);
  }

  /**
   * Gets the SWING popup menu component
   *
   * @return the SWING popup menu component
   */
  public JPopupMenu getPopupMenu() {
    JPopupMenu p = ((JMenu) getMenuItem()).getPopupMenu();

    p.removePopupMenuListener(this);
    p.addPopupMenuListener(this);

    return p;
  }
}
