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

package com.appnativa.rare.viewer;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.spot.MenuItem;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.MenuUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIMenu;
import com.appnativa.rare.ui.UIMenuItem;
import com.appnativa.rare.ui.event.ScriptActionListener;
import com.appnativa.rare.ui.iMenuBarComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformMenuBar;
import com.appnativa.rare.util.DataParser;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.FilterableList;

import java.util.Collections;
import java.util.List;

public abstract class aMenuBarViewer extends aContainer implements iPlatformMenuBar {

  /** the name of the main menu bar */
  public static final String  MENUBAR_NAME = "MenuBar";
  protected UIMenu            debugMenu;
  protected iMenuBarComponent menuBar;
  protected UIMenuItem        menuItems[];

  /** the last selected menu item */
  protected UIMenuItem selectedItem;

  /**
   * Constructs a new instance
   */
  public aMenuBarViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public aMenuBarViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.MenuBar;
  }

  @Override
  public void clearContents() {
    super.clearContents();

    if (menuBar != null) {
      menuBar.removeAll();
    }
  }

  /**
   * Configures the menu viewer
   *
   * @param vcfg the viewer configuration
   */
  @Override
  public void configure(Viewer vcfg) {
    configureEx((MenuBar) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Configures the popup menu for a widget
   *
   * @param comp
   *          the component to attach the menu to
   * @param cfg
   *          the widget configuration
   * @param textMenus
   *          true to create text menus (cut/copy/paste/) for the widget; false
   *          otherwise
   */
  @Override
  protected void configureMenus(iPlatformComponent comp, Widget cfg, boolean textMenus) {}

  /**
   * Creates a menu bar component given the specified configuration
   *
   * @param cfg the configuration
   *
   * @return the menu bar component
   */
  public iMenuBarComponent create(MenuBar cfg) {
    SPOTSet menus = cfg.getPopupMenu();
    String  s;

    if (menus == null) {
      s = cfg.dataURL.getValue();

      if ((s != null) && (s.length() > 0)) {
        iSPOTElement item;
        ActionLink   link = ActionLink.getActionLink(this, cfg.dataURL, 0);

        try {
          this.viewerActionLink = link;
          item                  = DataParser.loadSPOTObject(this, link.getConnection(), null);

          if (item instanceof MenuItem) {
            menus = ((MenuItem) item).getSubMenu();
          } else if (item instanceof MenuBar) {
            cfg   = (MenuBar) item;
            menus = cfg.getPopupMenu();
          }
        } catch(Exception ex) {
          throw DataParser.invalidConfigurationException(getAppContext(), ex, s);
        }
      }
    }

    menuBar = createMenuBarAndComponents(cfg);
    super.configureEx(cfg, true, false, true);
    s = cfg.spot_getAttribute(iConstants.ATTRIBUTE_ON_ACTION);

    if ((s != null) && (s.length() > 0)) {
      menuBar.addActionListener(new ScriptActionListener(this, s));
    }

    int len = (menus == null)
              ? 0
              : menus.getCount();

    if (len > 0) {
      menuItems = MenuUtils.createMenus(this, menus);

      UIMenuItem mi;
      String     name;

      for (int i = 0; i < menuItems.length; i++) {
        mi   = menuItems[i];
        name = mi.getName();
        menuBar.add(mi);

        if (name != null) {
          this.registerNamedItem(name, mi);
        }
      }
    }

    if (!isDesignMode()) {
      addDebugOptions();
    }

    return menuBar;
  }

  /**
   * Creates a menu item object from a data item
   *
   * @param item data item
   *
   * @return the new <code>UIMenuItem</code> object
   */
  public UIMenuItem createCheckBoxMenuItem(RenderableDataItem item) {
    UIMenuItem m = UIMenuItem.createCheckboxMenuItem(item);

    m.setContextWidget(this);

    return m;
  }

  @Override
  public UIMenuItem createCheckboxMenuItem(String text, iPlatformIcon icon, Object data) {
    UIMenuItem m = UIMenuItem.createCheckboxMenuItem(text, icon, data);

    m.setContextWidget(this);

    return m;
  }

  @Override
  public UIMenu createMenu(String text, iPlatformIcon icon, Object data) {
    UIMenu m = MenuUtils.createMenu(text, icon, data);

    m.setContextWidget(this);

    return m;
  }

  /**
   * Creates a menu item object from the specified configuration
   *
   * @param item the item configuration
   *
   * @return an array of <code>UIMenuItem</code> objects
   */
  public UIMenuItem createMenuItem(MenuItem item) {
    return MenuUtils.createMenuItem(this, item);
  }

  /**
   * Creates a menu item object from a data item
   *
   * @param item data item
   *
   * @return the new <code>UIMenuItem</code> object
   */
  public UIMenuItem createMenuItem(RenderableDataItem item) {
    UIMenuItem m = new UIMenuItem(item);

    m.setContextWidget(this);

    return m;
  }

  /**
   * Creates a menu item object form the specified action
   *
   * @param a the action
   *
   * @param checkbox true to create a checkbox menu item; false otherwise
   * @return an array of <code>UIMenuItem</code> objects
   */
  public UIMenuItem createMenuItem(UIAction a, boolean checkbox) {
    return new UIMenuItem(a, checkbox);
  }

  @Override
  public UIMenuItem createMenuItem(String text, iPlatformIcon icon, Object data) {
    UIMenuItem m = UIMenuItem.createMenuItem(text, icon, data);

    m.setContextWidget(this);

    return m;
  }

  /**
   * Creates a menu item
   *
   * @param text the text for the menu
   * @param icon the icon for the menu
   * @param data the linked data for the menu
   * @param code the code to execute when the menu is selected
   *
   * @return the menu item
   */
  public UIMenuItem createMenuItem(String text, iPlatformIcon icon, Object data, String code) {
    UIMenuItem m = UIMenuItem.createMenuItem(text, icon, data);

    m.setContextWidget(this);
    m.setActionScript(code);

    return m;
  }

  /**
   * Creates an array of menu item object from the specified configuration
   *
   * @param set the set of item configurations
   *
   * @return an array of <code>UIMenuItem</code> objects
   */
  public List<UIMenuItem> createMenuItems(SPOTSet set) {
    if (set == null) {
      return Collections.emptyList();
    }

    UIMenuItem[]               a = MenuUtils.createMenuItems(this, set);
    FilterableList<UIMenuItem> l = new FilterableList<UIMenuItem>(a.length);

    l.addAll(a, a.length);

    return l;
  }

  @Override
  public void hidePopupContainer() {}

  public void removeAll() {
    menuBar.removeAll();
  }

  @Override
  public void toggleVisibility() {
    setVisible(!isVisible());
  }

  @Override
  public void setItemEnabled(String name, boolean enabled) {
    if (name == null) {
      return;
    }

    UIMenuItem item = (UIMenuItem) this.getNamedItem(name);

    if (item == null) {
      return;
    }

    item.setEnabled(enabled);
  }

  @Override
  public void setItemSelected(String name, boolean selected) {
    if (name == null) {
      return;
    }

    UIMenuItem item = (UIMenuItem) this.getNamedItem(name);

    if (item == null) {
      return;
    }

    item.setSelected(selected);
  }

  @Override
  public void setItemVisible(String name, boolean visible) {
    if (name == null) {
      return;
    }

    UIMenuItem item = (UIMenuItem) this.getNamedItem(name);

    if (item == null) {
      return;
    }

    item.setVisible(visible);
  }

  /**
   * Sets the item that will be returned when
   * the getSelection() method is called
   *
   * @param mi the menu item
   */
  public void setSelectedItem(UIMenuItem mi) {
    selectedItem = mi;
  }

  @Override
  public void setVisible(boolean visible) {
    if (menuBar != null) {
      menuBar.setVisible(visible);
    }
  }

  @Override
  public UIMenu getMenu(String name) {
    Object o = this.getNamedItem(name);

    if (o instanceof UIMenu) {
      return (UIMenu) o;
    }

    return null;
  }

  @Override
  public iMenuBarComponent getMenuBarComponent() {
    return menuBar;
  }

  @Override
  public Object getSelection() {
    return selectedItem;
  }

  /**
   * Gets the item that can be used as a menu separator
   *
   * @return the item that can be used as a menu separator
   */
  public UIMenuItem getSeparatorItem() {
    return MenuUtils.getSeparatorItem();
  }

  @Override
  public boolean hasSelection() {
    return false;
  }

  @Override
  public boolean isItemEnabled(String name) {
    if (name == null) {
      return false;
    }

    UIMenuItem item = (UIMenuItem) this.getNamedItem(name);

    if (item == null) {
      return false;
    }

    return item.isEnabled();
  }

  @Override
  public boolean isItemSelected(String name) {
    if (name == null) {
      return false;
    }

    UIMenuItem item = (UIMenuItem) this.getNamedItem(name);

    if (item == null) {
      return false;
    }

    return item.isSelected();
  }

  @Override
  public boolean isItemVisible(String name) {
    if (name == null) {
      return false;
    }

    UIMenuItem item = (UIMenuItem) this.getNamedItem(name);

    if (item == null) {
      return false;
    }

    return item.isVisible();
  }

  @Override
  protected void clearConfiguration(boolean dispose) {
    super.clearConfiguration(dispose);

    if (menuBar != null) {
      menuBar.removeAll();
    }

    UIMenuItem[] a   = menuItems;
    int          len = (a == null)
                       ? 0
                       : a.length;

    for (int i = 0; i < len; i++) {
      a[i].dispose();
      a[i] = null;
    }

    if ((debugMenu != null) && dispose) {
      debugMenu.dispose();
      debugMenu = null;
    }

    menuItems    = null;
    selectedItem = null;
  }

  /**
   * Configures the menu viewer
   *
   * @param cfg the viewer configuration
   */
  protected void configureEx(MenuBar cfg) {
    create(cfg);
  }

  /**
   * Creates the actual menu bar component
   *
   * @param cfg the configuration
   * @return the menu bar component
   */
  protected abstract iMenuBarComponent createMenuBarAndComponents(MenuBar cfg);

  private void addDebugOptions() {}
}
