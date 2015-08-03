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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.iFilterableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class aUIMenu extends UIMenuItem {

  /** name map for holding registered named objects */
  protected HashMap<String, Object> nameMap;
  protected UIMenuItem              selectedItem;

  public aUIMenu() {
    super();
  }

  /**
   * Adds the specified component as a new menu item
   *
   * @param item the component to add
   *
   */
  public abstract void add(iPlatformComponent item);

  @Override
  public boolean add(RenderableDataItem item) {
    UIMenuItem mi;

    if (!(item instanceof RenderableDataItem)) {
      mi = new UIMenuItem(item);
    } else {
      mi = (UIMenuItem) item;
    }

    super.add(mi);

    if (!mi.isSelected()) {
      register(mi);
    }

    addToNativeMenu(-1, mi);

    return true;
  }

  /**
   * Adds a new item for the specified action
   * @param a the action
   *
   * @return the newly added menu item
   */
  public UIMenuItem add(UIAction a) {
    UIMenuItem mi = new UIMenuItem(a, false);

    add(mi);

    return mi;
  }

  @Override
  public void add(int pos, RenderableDataItem item) {
    UIMenuItem mi;

    if (!(item instanceof RenderableDataItem)) {
      mi = new UIMenuItem(item);
    } else {
      mi = (UIMenuItem) item;
    }

    super.add(pos, mi);

    if (!mi.isSelected()) {
      register(mi);
    }

    addToNativeMenu(pos, mi);
  }

  /**
   * Adds a new item for the specified action
   *
   * @param pos the position at which to add the item
   * @param a the action
   *
   * @return the newly added item
   */
  public UIMenuItem add(int pos, UIAction a) {
    UIMenuItem mi = new UIMenuItem(a);

    add(pos, mi);

    return mi;
  }

  /**
   * Adds a separator component to the menu
   */
  public void addSeparator() {
    add(MenuUtils.getSeparatorItem());
  }

  /**
   * Add a separator component to the menu
   *
   * @param pos the position at which to insert the separator
   */
  public void addSeparator(int pos) {
    add(pos, MenuUtils.getSeparatorItem());
  }

  @Override
  public void clear() {
    List<RenderableDataItem> items = getItems();
    int                      len   = (items == null)
                                     ? 0
                                     : items.size();
    int                      i     = 0;

    while(i < len) {
      ((UIMenuItem) items.get(i++)).dispose();
    }

    super.clear();

    if (nameMap != null) {
      nameMap.clear();
    }
  }

  @Override
  public void clearSubItems() {
    super.clearSubItems();

    if (nameMap != null) {
      nameMap.clear();
    }
  }

  @Override
  public void dispose() {
    if (nameMap != null) {
      nameMap.clear();
    }

    super.dispose();
    nameMap      = null;
    selectedItem = null;
  }

  /**
   * Registers the specified menu item with this item
   *
   * @param name the name of the item
   * @param mi the item to register
   *
   * @return the previous item registered with that name
   */
  public Object registerItem(String name, Object mi) {
    if (nameMap == null) {
      nameMap = new HashMap<String, Object>();
    }

    return nameMap.put(name, mi);
  }

  @Override
  public RenderableDataItem remove(int pos) {
    UIMenuItem mi = (UIMenuItem) super.remove(pos);

    if (mi != null) {
      unregister(mi);
      mi.setParentMenu(null);
      removeNativeItem(mi);
    }

    return mi;
  }

  @Override
  public boolean remove(Object o) {
    int n = indexOf(o);

    if (n != -1) {
      remove(n);

      return true;
    }

    return false;
  }

  public void show(iWidget context, boolean modal) {
    show(context, null, modal);
  }

  public void show(iPlatformComponent owner, int x, int y) {
    if (ScreenUtils.isSmallScreen() || Platform.getUIDefaults().getBoolean("Rare.PopupMenu.showModal", false)) {
      show(owner.getWidget(), owner, true);

      return;
    }

    PopupList list = new PopupList(owner.getWidget());

    list.setItems(getItems(), null, true, size());
    list.showPopup(null, x, y);
  }

  public void show(iWidget context, iPlatformComponent owner, boolean modal) {
    PopupList list = new PopupList(context);

    if (owner == null) {
      owner = context.getContainerComponent();
    }

    list.setItems(getItems(), null, true, size());

    if (modal) {
      list.showModalPopup(!PlatformHelper.hasEscapeButton()
                          || Platform.getUIDefaults().getBoolean("Rare.Menu.showCloseButton", false));
    } else {
      UIRectangle r = new UIRectangle();

      Utils.getProposedPopupBounds(r, owner, list.getPreferredSize(), 0, HorizontalAlign.AUTO, list.getPopupBorder(),
                                   false);
      list.showPopup(owner, (int) r.x, (int) r.y);
    }
  }

  /**
   * Unregisters a menu
   * @param name the name of the menu to unregister
   *
   * @return the object that was registered with that name
   */
  public Object unregisterItem(String name) {
    if (nameMap != null) {
      return nameMap.remove(name);
    }

    return null;
  }

  @Override
  public RenderableDataItem setItem(int pos, RenderableDataItem item) {
    UIMenuItem newmi = (UIMenuItem) item;
    UIMenuItem mi    = (UIMenuItem) super.setItem(pos, newmi);

    setNativeItem(pos, newmi);
    register(newmi);

    if (mi != null) {
      unregister(mi);
    }

    return mi;
  }

  /**
   * Changes the enabled state of the named item
   *
   * @param name the name of the item to register
   * @param enabled true to enable the item; false otherwise
   */
  public void setItemEnabled(String name, boolean enabled) {
    UIMenuItem item = getMenuItem(name);

    if (item == null) {
      return;
    }

    item.setEnabled(enabled);
  }

  /**
   * Changes the selected state of the named item
   *
   * @param name the name of the item to register
   * @param selected true to select the item; false otherwise
   */
  public void setItemSelected(String name, boolean selected) {
    UIMenuItem item = getMenuItem(name);

    if (item == null) {
      return;
    }

    item.setSelected(selected);
  }

  /**
   * Changes the visible state of the named item
   *
   * @param name the name of the item
   * @param visible true to make the item visible; false otherwise
   */
  public void setItemVisible(String name, boolean visible) {
    UIMenuItem item = getMenuItem(name);

    if (item == null) {
      return;
    }

    item.setVisible(visible);
  }

  @Override
  public void setItems(List<RenderableDataItem> items) {
    clear();

    int count = (items == null)
                ? 0
                : items.size();

    ensureCapacity(count);

    for (int i = 0; i < count; i++) {
      add(items.get(i));
    }
  }

  @Override
  public void setItems(RenderableDataItem[] items, int count) {
    clear();
    ensureCapacity(count);

    for (int i = 0; i < count; i++) {
      add(items[i]);
    }
  }

  public abstract void setNativeItem(int pos, UIMenuItem mi);

  /**
   * Sets the selected item
   *
   * @param item the selected item
   */
  public void setSelectedItem(UIMenuItem item) {
    this.selectedItem = item;
  }

  /**
   * Sets the item's value
   *
   * @param menu the value for the item
   */
  public void setValue(aUIMenu menu) {
    clear();
    addAll(menu);
  }

  @Override
  public void setValue(Object value) {
    if (value instanceof aUIMenu) {
      setValue((aUIMenu) value);
    } else {
      super.setValue(value);
    }
  }

  /**
   *  Gets the menu at the specified index
   *
   * @param index the index
   *
   *  @return the menu item
   */
  public UIMenuItem getMenuItem(int index) {
    return (UIMenuItem) get(index);
  }

  /**
   *  Gets the named menu item
   *
   * @param name the name of the item
   *
   *  @return the named menu item
   */
  public UIMenuItem getMenuItem(String name) {
    if (nameMap == null) {
      return null;
    }

    return (UIMenuItem) nameMap.get(name);
  }

  /**
   * Gets the selected item
   *
   * @return the selected item
   */
  public UIMenuItem getSelectedItem() {
    return selectedItem;
  }

  /**
   * Returns a map of the sub-items that the menu contains
   *
   * @return a map of the sub-items that the menu contains
   */
  public Map<String, Object> getSubs() {
    if (nameMap == null) {
      nameMap = new HashMap<String, Object>();
    }

    return nameMap;
  }

  /**
   * Returns whether the named item is enabled
   *
   * @param name the name of the item
   *
   * @return true if it is enabled; false otherwise
   */
  public boolean isItemEnabled(String name) {
    UIMenuItem item = getMenuItem(name);

    if (item == null) {
      return false;
    }

    return item.isEnabled();
  }

  /**
   * Returns whether the named item is selected
   *
   * @param name the name of the item
   *
   * @return true if it is selected; false otherwise
   */
  public boolean isItemSelected(String name) {
    UIMenuItem item = getMenuItem(name);

    if (item == null) {
      return false;
    }

    return item.isSelected();
  }

  /**
   * Returns whether the named item is visible
   *
   * @param name the name of the item
   *
   * @return true if it is visible; false otherwise
   */
  public boolean isItemVisible(String name) {
    UIMenuItem item = getMenuItem(name);

    if (item == null) {
      return false;
    }

    return item.isVisible();
  }

  protected abstract void addToNativeMenu(int pos, UIMenuItem mi);

  protected void configure(com.appnativa.rare.spot.MenuBar cfg) {
    SPOTSet menus = cfg.getPopupMenu();
    String  s;
    iWidget context = contextWidget;

    if (menus == null) {
      s = cfg.dataURL.getValue();

      if ((s != null) && (s.length() > 0)) {
        iSPOTElement item;

        try {
          ActionLink link = ActionLink.getActionLink(context, cfg.dataURL, 0);

          item = DataParser.loadSPOTObject(context, link.getConnection(), null);

          if (item instanceof com.appnativa.rare.spot.MenuItem) {
            menus = ((com.appnativa.rare.spot.MenuItem) item).getSubMenu();
          } else if (item instanceof com.appnativa.rare.spot.MenuBar) {
            cfg   = (com.appnativa.rare.spot.MenuBar) item;
            menus = cfg.getPopupMenu();
          }
        } catch(Exception ex) {
          throw DataParser.invalidConfigurationException(context.getAppContext(), ex, s);
        }
      }
    }

    s = cfg.spot_getAttribute(iConstants.ATTRIBUTE_ON_ACTION);

    if ((s != null) && (s.length() > 0)) {
      actionScript = s;
    }

    configure(menus, isAddHomeMenu());
  }

  protected void configure(SPOTSet menus, boolean addHome) {
    int          len = (menus == null)
                       ? 0
                       : menus.getCount();
    UIMenuItem[] menuItems;

    if (len > 0) {
      menuItems = MenuUtils.createMenuItems(contextWidget, menus);
      addAll(menuItems);

      String s = menus.spot_getAttribute(iConstants.ATTRIBUTE_ON_ACTION);

      if ((s != null) && (s.length() > 0)) {
        actionScript = s;
      }
    }

    UIAction home = Platform.getAppContext().getAction("Rare.action.home");

    if (addHome && (home != null)) {
      add(new UIMenuItem(home));
    }
  }

  /**
   * Disables focused actions on the specified menu
   *
   * @param menu the menu
   * @param context the context. if specified cut/copy/paste actions will remain enabled if the widget supports them
   */
  protected void disableFocusedActions(aUIMenu menu, iWidget context) {
    iFilterableList<RenderableDataItem> list = menu.getItems();
    UIMenuItem                          mi;
    int                                 len = list.size();

    for (int i = 0; i < len; i++) {
      mi = (UIMenuItem) list.get(i);

      if (mi instanceof aUIMenu) {
        disableFocusedActions((UIMenu) mi, context);
      } else {
        UIAction a = mi.getAction();

        if (a instanceof aFocusedAction) {
          if ((context != null) && context.canCopy() && (a == ActionHelper.getCopyAction())) {
            a.setEnabled(true);
          } else if ((context != null) && context.canCut() && (a == ActionHelper.getCutAction())) {
            a.setEnabled(true);
          } else if ((context != null) && context.canPaste() && (a == ActionHelper.getPasteAction())) {
            a.setEnabled(true);
          } else if ((context != null) && context.canDelete() && (a == ActionHelper.getDeleteAction())) {
            a.setEnabled(true);
          } else if ((context != null) && context.canSelectAll() && (a == ActionHelper.getSelectAllAction())) {
            a.setEnabled(true);
          } else if ((context != null) && ((a == ActionHelper.getUndoAction()) || (a == ActionHelper.getRedoAction()))
                     && context.canUndoOrRedo()) {
            a.setEnabled(true);
          } else {
            a.setEnabled(false);
          }
        }
      }
    }
  }

  protected void menuWillBecomeInvisible() {
    iWidget invoker = hasParentMenu()
                      ? null
                      : getInvoker();

    if ((invoker != null) &&!invoker.getDataComponent().isFocusable()) {
      resetFocusedActions(this);
    }

    final iWidget ctx = getContextWidget();

    if (ctx instanceof iListHandler) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          if (!ctx.isDisposed()) {
            ((iListHandler) ctx).clearPopupMenuIndex();
          }
        }
      });
    }
  }

  protected void menuWillBecomeVisible() {
    iWidget invoker = hasParentMenu()
                      ? null
                      : getInvoker();

    if ((invoker != null) &&!invoker.getDataComponent().isFocusable()) {
      disableFocusedActions(this, invoker);
    } else {
      resetFocusedActions(this);
    }

    iWidget ctx = getContextWidget();

    if (this.getActionScript() != null) {
      if (ctx == null) {
        ctx = Platform.getContextRootViewer();
      }

      aWidgetListener.evaluate(ctx, ctx.getScriptHandler(), getActionScript(), new ExpansionEvent(this));
    }
  }

  protected abstract void removeNativeItem(UIMenuItem mi);

  /**
   * Resets focused actions on the specified menu
   *
   * @param menu the menu
   */
  protected void resetFocusedActions(aUIMenu menu) {
    iFilterableList<RenderableDataItem> list = menu.getItems();
    UIMenuItem                          mi;
    int                                 len = list.size();

    for (int i = 0; i < len; i++) {
      mi = (UIMenuItem) list.get(i);

      if (mi instanceof aUIMenu) {
        resetFocusedActions((UIMenu) mi);
      } else {
        UIAction a = mi.getAction();

        if (a instanceof aFocusedAction) {
          ((aFocusedAction) a).update();
        }
      }
    }
  }

  protected iWidget getInvoker() {
    return contextWidget;
  }

  protected abstract boolean hasParentMenu();

  protected boolean isAddHomeMenu() {
    return false;
  }

  private void register(UIMenuItem mi) {
    String name = mi.getName();

    if ((name != null) && (name.length() > 0)) {
      this.registerItem(name, mi);
    }

    mi.setParentItem(this);
  }

  private void unregister(UIMenuItem mi) {
    String name = mi.getName();

    if ((name != null) && (name.length() > 0)) {
      unregisterItem(name);
    }

    mi.setParentItem(null);
  }
}
