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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.Link;
import com.appnativa.rare.spot.MenuItem;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;

public class MenuUtils {
  private static final UIMenuItem[] EMPTY_MENU_ARRAY = new UIMenuItem[0];

  private MenuUtils() {}

  public static void addTextActions(UIMenu menu, boolean find, boolean replace) {
    UIMenuItem item = new UIMenuItem(PlatformHelper.getUndoAction());

    menu.registerItem(iConstants.UNDO_ACTION_NAME, item);
    menu.add(item);
    item = new UIMenuItem(PlatformHelper.getRedoAction());
    menu.registerItem(iConstants.UNDO_ACTION_NAME, item);
    menu.add(item);
    menu.addSeparator();
    item = new UIMenuItem(PlatformHelper.getCutAction());
    menu.registerItem(iConstants.CUT_ACTION_NAME, item);
    menu.add(item);
    item = new UIMenuItem(PlatformHelper.getCopyAction());
    menu.registerItem(iConstants.COPY_ACTION_NAME, item);
    menu.add(item);
    item = new UIMenuItem(PlatformHelper.getPasteAction());
    menu.registerItem(iConstants.PASTE_ACTION_NAME, item);
    menu.add(item);
    item = new UIMenuItem(PlatformHelper.getDeleteAction());
    menu.registerItem(iConstants.DELETE_ACTION_NAME, item);
    menu.add(item);
    menu.addSeparator();
    item = new UIMenuItem(PlatformHelper.getSelectAllAction());
    menu.registerItem(iConstants.SELECTALL_ACTION_NAME, item);
    menu.add(item);
  }

  /**
   *  Creates a new menu
   *
   * @param text the text
   * @param icon the icon
   * @param data associated data
   *
   * @return the newly created menu
   */
  public static UIMenu createMenu(String text, iPlatformIcon icon, Object data) {
    UIMenu m = new UIMenu("", icon, data);

    Utils.setMnemonicAndText(m, text);

    return m;
  }

  /**
   * Creates a menu item object from the specified configuration
   *
   * @param context the widget context
   * @param item the item configuration
   *
   * @return a <code>UIMenuItem</code> object
   */
  public static UIMenuItem createMenuItem(iWidget context, MenuItem item) {
    return createMenuItem(context, item, false);
  }

  /**
   * Creates a menu item object from the specified configuration
   *
   * @param context the widget context
   * @param set the set of item configurations
   *
   * @return an array of <code>UIMenuItem</code> objects
   */
  public static UIMenuItem[] createMenuItems(iWidget context, SPOTSet set) {
    if (set == null) {
      return EMPTY_MENU_ARRAY;
    }

    int          len = set.getCount();
    UIMenuItem[] a   = new UIMenuItem[len];

    for (int i = 0; i < len; i++) {
      a[i] = createMenuItem(context, (MenuItem) set.get(i));
      a[i].setContextWidget(context);
    }

    return a;
  }

  /**
   * Creates a menu item object from the specified configuration
   *
   * @param context the widget context
   * @param set the set of item configurations
   *
   * @return an array of <code>UIMenuItem</code> objects
   */
  public static UIMenuItem[] createMenus(iWidget context, SPOTSet set) {
    if (set == null) {
      return EMPTY_MENU_ARRAY;
    }

    int          len = set.getCount();
    UIMenuItem[] a   = new UIMenuItem[len];

    for (int i = 0; i < len; i++) {
      a[i] = createMenuItem(context, (MenuItem) set.get(i), true);
      a[i].setContextWidget(context);
    }

    return a;
  }

  /**
   * Creates a menu item based of the specified name
   *
   * @param context the context
   * @param name the name of a predefined menu item or action
   * @param hasSubs true iof the menu has subs; false otherwise
   * @param topLevelMenu true is this menu is for the top level of a menu bar; false otherwise
   * @return a <code>UIMenuItem</code> object
   */
  public static UIMenuItem createNamedMenu(iWidget context, String name, boolean hasSubs, boolean topLevelMenu) {
    UIMenuItem item;
    UIMenu     m;
    UIAction   a = context.getAppContext().getAction(name);

    if (a != null) {
      item = PlatformHelper.createMenuItem(a, topLevelMenu);
      item.setName(name);

      return item;
    }

    if ((name == null) || (name.length() == 0)) {
      return null;
    }

    if (name.equals(iConstants.UNDO_ACTION_NAME)) {
      item = new UIMenuItem(PlatformHelper.getUndoAction());
      item.setName(name);

      return item;
    }

    if (name.equals(iConstants.REDO_ACTION_NAME)) {
      item = new UIMenuItem(PlatformHelper.getRedoAction());
      item.setName(name);

      return item;
    }

    if (name.equals(iConstants.CUT_ACTION_NAME)) {
      item = new UIMenuItem(PlatformHelper.getCutAction());
      item.setName(name);

      return item;
    }

    if (name.equals(iConstants.COPY_ACTION_NAME)) {
      item = new UIMenuItem(PlatformHelper.getCopyAction());
      item.setName(name);

      return item;
    }

    if (name.equals(iConstants.PASTE_ACTION_NAME)) {
      item = new UIMenuItem(PlatformHelper.getPasteAction());
      item.setName(name);

      return item;
    }

    if (name.equals(iConstants.SELECTALL_ACTION_NAME)) {
      item = new UIMenuItem(PlatformHelper.getSelectAllAction());
      item.setName(name);

      return item;
    }

    if (name.equals(iConstants.MENU_SEPARATOR_NAME)) {
      return PlatformHelper.getSeparatorMenuItem();
    }

    if (name.equals(iConstants.EXIT_ACTION_NAME)) {
      item = UIMenuItem.createMenuItem("E_xit");
      item.setName(name);

      final iPlatformAppContext app = context.getAppContext();

      item.setActionListener(new iActionListener() {
        @Override
        public void actionPerformed(com.appnativa.rare.ui.event.ActionEvent e) {
          if (app != null) {
            app.exit();
          }
        }
      });
      item.setIcon(context.getAppContext().getResourceAsIcon("Rare.icon.empty"));

      return item;
    }

    if (name.equals(iConstants.EDIT_MENU_NAME)) {
      m = createMenu("_Edit", null, null);
      m.setName(name);

      if (!hasSubs) {
        addTextActions(m, false, false);
      }

      return m;
    }

    if (name.equals(iConstants.FILE_MENU_NAME)) {
      m = createMenu("_File", null, null);
      m.setName(name);

      if (!hasSubs) {
        addFileActions(context, m);
      }

      return m;
    }

    if (name.equals(iConstants.TOOLS_MENU_NAME)) {
      m = createMenu("_Tools", null, null);
      m.setName(name);

      return m;
    }

    if (name.equals(iConstants.HELP_MENU_NAME)) {
      m = createMenu("_Help", null, null);
      m.setName(name);

      return m;
    }

    return null;
  }

  /**
   * Populates a popup menu with the specified set of items
   *
   * @param menu the popup menu to add the item to (can be null)
   * @param context the context widget
   * @param menus a set of menu item configurations to use to create the menu
   * @param addDefaults true to add text menu items (cut, copy, paste, etc.) to the menu; false otherwise
   * @return a popup menu populated with the specified items
   */
  public static UIPopupMenu createPopupMenu(UIPopupMenu menu, iWidget context, SPOTSet menus, boolean addDefaults) {
    if (menu == null) {
      menu = new UIPopupMenu();
      menu.setContextWidget(context);
    }

    if (menus != null) {
      String s = menus.spot_getAttribute(iConstants.ATTRIBUTE_ON_ACTION);

      if ((s != null) && (s.length() > 0)) {
        menu.setActionScript(aWidgetListener.processEventString(s));
      }
    }

    UIMenuItem[] menuItems = createMenuItems(context, menus);
    UIMenuItem   mi        = null;
    String       name;

    for (int i = 0; i < menuItems.length; i++) {
      mi = menuItems[i];

      if ((mi == null) || (mi.isSeparator())) {
        menu.addSeparator();
      } else {
        menu.add(mi);
        name = mi.getName();

        if (name != null) {
          menu.registerItem(name, mi);
        }
      }
    }

    if (addDefaults) {
      if (mi != null) {
        menu.addSeparator();
      }

      menu.add(new UIMenuItem(PlatformHelper.getCutAction()));
      menu.add(new UIMenuItem(PlatformHelper.getCopyAction()));
      menu.add(new UIMenuItem(PlatformHelper.getPasteAction()));

      if (context.canSelectAll()) {
        menu.addSeparator();
        menu.add(new UIMenuItem(PlatformHelper.getSelectAllAction()));
      }
    }

    return menu;
  }

  public static UIMenuItem getSeparatorItem() {
    return PlatformHelper.getSeparatorMenuItem();
  }

  private static void addFileActions(iWidget context, UIMenu menu) {
    UIMenuItem item = createNamedMenu(context, iConstants.EXIT_ACTION_NAME, false, false);

    menu.addSeparator();
    menu.add(item);
  }

  private static UIMenuItem createMenuItem(iWidget context, MenuItem item, boolean forceTopLevel) {
    String        text  = context.expandString(item.value.getValue(), false);
    String        name  = item.name.getValue();
    iPlatformIcon icon  = context.getIcon(item.icon);
    iPlatformIcon dicon = context.getIcon(item.disabledIcon);
    Object        data  = item.linkedData.getValue();
    UIMenuItem    mi    = null;
    SPOTSet       subs  = item.getSubMenu();
    String        s;

    if (name != null) {
      if (name.equals(iConstants.MENU_SEPARATOR_NAME)) {
        mi = PlatformHelper.getSeparatorMenuItem();
      } else {
        mi = createNamedMenu(context, name, (subs != null) && (subs.size() > 0), forceTopLevel);

        if (mi != null) {
          if (data != null) {
            mi.setLinkedData(data);
          }

          if ((text != null) && (text.length() > 0)) {
            Utils.setMnemonicAndText(mi, text);
          }
        }
      }
    }

    if ((mi == null) && (name == null) && ((text == null) || (text.length() == 0))) {
      mi = PlatformHelper.getSeparatorMenuItem();
    }

    do {
      if (subs != null) {
        if (mi == null) {
          mi = createMenu(text, icon, data);
        }

        s = item.spot_getAttribute(iConstants.ATTRIBUTE_ON_ACTION);

        if ((s != null) && (s.length() > 0)) {
          mi.setActionScript(s);
        }

        UIMenuItem[] a = createMenuItems(context, subs);

        mi.setItems(a, a.length);

        if (item.enabledOnSelectionOnly.spot_valueWasSet()) {
          mi.setEnabledOnSelection(item.enabledOnSelectionOnly.booleanValue());
        }

        break;
      }

      if (!(mi instanceof UIMenu)) {
        if (mi == null) {
          if (item.checkbox.booleanValue()) {
            mi = UIMenuItem.createCheckboxMenuItem(text, icon, data);
            mi.setSelected(item.selected.booleanValue());
          } else if (forceTopLevel) {
            mi = createMenu(text, icon, data);
          } else {
            mi = UIMenuItem.createMenuItem(text, icon, data);
          }
        }

        Link link = item.getActionLink();

        if (link != null) {
          mi.setActionListener(new ActionLink(context, link));
        } else {
//        ((UIMenuItem) mi).addActionListener(menuActionListener);
          s = item.spot_getAttribute(iConstants.ATTRIBUTE_ON_ACTION);

          if ((s != null) && (s.length() > 0)) {
            mi.setActionScript(aWidgetListener.processEventString(s));
          }
        }

        if (item.enabledOnSelectionOnly.spot_valueWasSet()) {
          mi.setEnabledOnSelection(item.enabledOnSelectionOnly.booleanValue());
        }
      }
    } while(false);

    if (dicon != null) {
      mi.setDisabledIcon(dicon);
    }

    if (item.enabled.spot_valueWasSet()) {
      mi.setEnabled(item.enabled.booleanValue());
    }

    s = item.shortcutKeystroke.getValue();

    if ((s != null) && (s.length() > 0)) {
      PlatformHelper.setShortcut(mi, s);
    }

    if ((name != null) && (name.length() > 0) && (mi.getName() == null)) {
      mi.setName(name);
    }

    if (context.isDesignMode()) {
      mi.setLinkedData(item);
    }

    return mi;
  }

  public static RenderableDataItem createDataItem(iWidget context, MenuItem item) {
    String             text  = context.expandString(item.value.getValue(), false);
    iPlatformIcon      icon  = context.getIcon(item.icon);
    iPlatformIcon      dicon = context.getIcon(item.disabledIcon);
    Object             data  = item.linkedData.getValue();
    RenderableDataItem mi    = new RenderableDataItem(text, RenderableDataItem.TYPE_STRING, data, icon);
    String             name  = item.name.getValue();

    mi.setLinkedData(name);

    if (dicon != null) {
      mi.setDisabledIcon(dicon);
    }

    if (item.enabled.spot_valueWasSet()) {
      mi.setEnabled(item.enabled.booleanValue());
    }

    String s = item.spot_getAttribute(iConstants.ATTRIBUTE_ON_ACTION);

    if ((s != null) && (s.length() > 0)) {
      mi.setActionCode(context, s);
    } else {
      if (name != null) {
        UIAction a = context.getAppContext().getAction(name);

        if (a != null) {
          mi.setActionListener(a);
        }
      }
    }

    return mi;
  }
}
