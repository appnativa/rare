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

package com.appnativa.rare.ui;

import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;

/**
 * A class representing a top-level menu
 *
 * @author Don DeCoteau
 */
public class UIMenu extends aUIMenu {

  /**
   * Constructs a new instance
   */
  public UIMenu() {
    this(new Menu(""));
  }

  /**
   * Constructs a new instance
   *
   * @param item the menu component
   */
  public UIMenu(Menu item) {
    super();
    menuItem = item;
  }

  /**
   * Constructs a new instance
   *
   * @param text the text
   */
  public UIMenu(String text) {
    this(text, null, null);
  }

  /**
   * Constructs a new instance
   *
   * @param context
   *          the context
   * @param cfg
   *          the menu configuration
   */
  public UIMenu(iWidget context, MenuBar cfg) {
    this(new Menu(""));
    this.contextWidget = context;
    configure(cfg);
  }

  /**
   * Constructs a new instance
   *
   * @param context
   *          the context
   * @param menus
   *          the menu set
   */
  public UIMenu(iWidget context, SPOTSet menus) {
    this(context, menus, false);
  }

  /**
   * Constructs a new instance
   *
   * @param text the text
   * @param icon the icon
   */
  public UIMenu(String text, iPlatformIcon icon) {
    this(text, icon, null);
  }

  /**
   * Constructs a new instance
   *
   * @param context
   *          the context
   * @param menus
   *          the menu set
   * @param addTextItems
   *          whether the text menu items (copy, cut, paste) should be added to
   *          this menu
   */
  public UIMenu(iWidget context, SPOTSet menus, boolean addTextItems) {
    this(new Menu(""));
    this.contextWidget = context;
    configure(menus, false);
  }

  /**
   * Constructs a new instance
   *
   * @param text the text
   * @param icon the icon
   * @param data the data to associate with the menu item
   */
  public UIMenu(String text, iPlatformIcon icon, Object data) {
    this(new Menu(text));
    setIcon(icon);
    setLinkedData(data);
  }

  /**
   * Adds the specified component as a new menu item
   *
   * @param item the component to add
   *
   */
  @Override
  public void add(iPlatformComponent item) {
    add(new UIMenuItem(new CustomMenuItem(item.getView())));
  }

  @Override
  public void clear() {
    if (menuItem != null) {
      //((Menu) menuItem).removeAll();
    }

    super.clear();
  }

  @Override
  public void setNativeItem(int pos, UIMenuItem mi) {
    //removeNativeItem(mi);
    //addToNativeMenu(pos, mi);
  }

  /**
   * Gets the JavaFX menu component
   *
   * @return  the JavaFX menu component
   */
  public Menu getMenu() {
    return ((Menu) getMenuItem());
  }

  @Override
  protected void addToNativeMenu(int pos, UIMenuItem mi) {
    //((Menu) menuItem).add(pos,mi);
  }

  @Override
  protected void removeNativeItem(UIMenuItem mi) {
    //((Menu) menuItem).remove(mi);
  }

  @Override
  protected void setupItem() {
    super.setupItem();
  }

  @Override
  protected boolean hasParentMenu() {
    return (menuItem == null)
           ? false
           : ((Menu) menuItem).hasParentMenu();
  }
}
