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

import android.graphics.drawable.Drawable;

import android.os.Build.VERSION;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.ActionBarHandler;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;

public class UIMenu extends aUIMenu implements iPlatformMenuBar {
  protected CharSequence header;
  protected Drawable     headerDrawable;
  protected Menu         popupMenu;

  /**
   * Constructs a new instance
   *
   * @param context
   *          the context
   * @param cfg
   *          the menu configuration
   */
  public UIMenu() {}

  /**
   * Constructs a new instance
   *
   * @param context
   *          the context
   * @param cfg
   *          the menu configuration
   */
  public UIMenu(iWidget context, MenuBar cfg) {
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
   * @param context
   *          the context
   * @param menus
   *          the menu set
   * @param addTextItems
   *          whether the text menu items (copy, cut, paste) should be added to
   *          this menu
   */
  public UIMenu(iWidget context, SPOTSet menus, boolean addTextItems) {
    this.contextWidget = context;
    configure(menus, false);
  }

  public UIMenu(String text, iPlatformIcon icon, Object data) {
    setValue(text);
    setIcon(icon);
    setLinkedData(data);
  }

  @Override
  public void add(iPlatformComponent item) {}

  public void setHeader(iPlatformComponent comp) {
    if (popupMenu instanceof ContextMenu) {
      ((ContextMenu) popupMenu).setHeaderView(comp==null ? null : comp.getView());
    }
    
  }

  public UIMenuItem createCheckboxMenuItem(String text, iPlatformIcon icon, Object data) {
    return new UIMenuItem(text, icon, data, true);
  }

  public void createMenu(Menu menu) {
    popupMenu = menu;

    int        len = size();
    UIMenuItem mi;

    for (int i = 0; i < len; i++) {
      mi = (UIMenuItem) get(i);

      MenuItem item = menu.add(mi.getText());

      mi.setMenuItem(item);

      iPlatformIcon icon = mi.getIcon();

      if (icon != null) {
        item.setIcon(icon.getDrawable(null));
      }

      item.setEnabled(mi.isEnabled());
      item.setVisible(mi.isVisible());

      if (mi.isCheckable()) {
        item.setCheckable(true);
        item.setChecked(mi.isSelected());
      }

      ActionBarHandler.legacyMenu(item);
    }

    if (menu instanceof ContextMenu) {
      menuWillBecomeVisible(menu);

      if (header != null) {
        ((ContextMenu) popupMenu).setHeaderTitle(header);
      }

      if (headerDrawable != null) {
        ((ContextMenu) popupMenu).setHeaderIcon(headerDrawable);
      }

      popupMenu = null;
    }
  }

  public UIMenu createMenu(String text, iPlatformIcon icon, Object data) {
    return new UIMenu(text, icon, data);
  }

  public UIMenuItem createMenuItem(String text, iPlatformIcon icon, Object data) {
    return new UIMenuItem(text, icon, data, false);
  }

  public boolean handleMenuItemSeleceted(MenuItem menu) {
    int len = size();

    for (int i = 0; i < len; i++) {
      UIMenuItem mi = (UIMenuItem) get(i);

      if (mi.getMenuItem() == menu) {
        itemSelected(mi);

        return true;
      }
    }

    return false;
  }

  public void menuClosed(Menu menu) {
    if (menu instanceof ContextMenu) {
      int len = size();

      for (int i = 0; i < len; i++) {
        UIMenuItem mi = (UIMenuItem) get(i);

        mi.setMenuItem(null);
      }
    }
  }

  public void menuWillBecomeVisible(Menu menu) {
    if ((popupMenu != menu) || (actionScript == null)) {
      return;
    }

    super.menuWillBecomeVisible();
  }

  public void toggleVisibility() {
    if (ActionBarHandler.isVisible(AppContext.getContext().getActivity())) {
      ActionBarHandler.hide(AppContext.getContext().getActivity());
    } else {
      ActionBarHandler.show(AppContext.getContext().getActivity());
    }
  }

  public void setEnabled(boolean enabled) {}

  /**
   * @param headerDrawable
   *          the headerDrawable to set
   */
  public void setHeaderDrawable(Drawable headerDrawable) {
    this.headerDrawable = headerDrawable;

    if (popupMenu instanceof ContextMenu) {
      ((ContextMenu) popupMenu).setHeaderIcon(getHeaderDrawable());
    }
  }

  public void setIcon(iPlatformIcon icon) {
    super.setIcon(icon);
    headerDrawable = (icon == null)
                     ? null
                     : icon.getDrawable(null);

    if (popupMenu instanceof ContextMenu) {
      ((ContextMenu) popupMenu).setHeaderIcon(headerDrawable);
    }
  }

  @Override
  public void setNativeItem(int pos, UIMenuItem mi) {}

  public void setValue(Object value) {
    if (value instanceof CharSequence) {
      header = (CharSequence) value;
    } else {
      header = (value == null)
               ? null
               : value.toString();
    }

    if (popupMenu instanceof ContextMenu) {
      ((ContextMenu) popupMenu).setHeaderTitle(header);
    }
  }

  public void setVisible(boolean visible) {
    if (visible) {
      ActionBarHandler.show(AppContext.getContext().getActivity());
    } else {
      ActionBarHandler.hide(AppContext.getContext().getActivity());
    }
  }

  public iPlatformComponent getContainerComponent() {
    return null;
  }

  /**
   * @return the headerDrawable
   */
  public Drawable getHeaderDrawable() {
    return headerDrawable;
  }

  public UIMenu getMenu(String name) {
    UIMenuItem mi = super.getMenuItem(name);

    if (mi instanceof UIMenu) {
      return (UIMenu) mi;
    }

    return null;
  }

  public Object getValue() {
    return header;
  }

  public boolean isEnabled() {
    return true;
  }

  public boolean isNativeActionBar() {
    //this method will only be called when this menu is used for the native action bar
    return true;
  }

  public boolean isVisible() {
    return ActionBarHandler.isVisible(AppContext.getContext().getActivity());
  }

  @Override
  protected void addToNativeMenu(int pos, UIMenuItem mi) {}

  protected void itemSelected(UIMenuItem menu) {
    if (menu.isCheckable()) {
      menu.setSelected(!menu.isSelected());
    } else {
      menu.setSelected(true);
    }

    ActionEvent e = new ActionEvent(menu);

    if (menu.getActionListener() != null) {
      menu.getActionListener().actionPerformed(e);

      return;
    }

    if (menu.getActionScript() != null) {
      final iWidget w = contextWidget;

      aWidgetListener.execute(w, w.getScriptHandler(), menu.getActionScript(), iConstants.EVENT_ACTION, e);
    }
  }

  @Override
  protected void removeNativeItem(UIMenuItem mi) {
    if ((popupMenu != null) && (mi.getMenuItem() != null)) {
      popupMenu.removeItem(mi.getMenuItem().getItemId());
    }
  }

  @Override
  protected iWidget getInvoker() {
    return contextWidget;
  }

  @Override
  protected boolean hasParentMenu() {
    return false;
  }

  protected boolean isAddHomeMenu() {
    return VERSION.SDK_INT < 11;
  }

  public iMenuBarComponent getMenuBarComponent() {
    return null;
  }
}
