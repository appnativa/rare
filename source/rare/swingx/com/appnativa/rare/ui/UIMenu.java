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
import com.appnativa.rare.iWeakReference;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;

import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * A class representing a top-level menu
 *
 * @author Don DeCoteau
 */
public class UIMenu extends aUIMenu implements PopupMenuListener, ActionListener {
  static ArrayList<iWeakReference> visibleMenus = new ArrayList<iWeakReference>();

  /**
   * Constructs a new instance
   */
  public UIMenu() {
    this(new JMenuEx(""));
  }

  /**
   * Constructs a new instance
   *
   * @param item the menu component
   */
  public UIMenu(JMenuEx item) {
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
    this();
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
    this();
    this.contextWidget = context;
    configure(menus, false);

    if (addTextItems) {
      MenuUtils.addTextActions(this, false, false);
    }
  }

  /**
   * Constructs a new instance
   *
   * @param text the text
   * @param icon the icon
   * @param data the data to associate with the menu item
   */
  public UIMenu(String text, iPlatformIcon icon, Object data) {
    this(new JMenuEx(text));
    setIcon(icon);
    setLinkedData(data);
  }

  @Override
  public void actionPerformed(java.awt.event.ActionEvent e) {
    fire(contextWidget);
  }

  public static void closeVisibleMenus() {
    int len = visibleMenus.size();

    if (len > 0) {
      ArrayList<iWeakReference> list = new ArrayList<iWeakReference>(visibleMenus);

      for (iWeakReference ref : list) {
        UIMenu m = (UIMenu) ref.get();

        if ((m != null) && (m.menuItem != null)) {
          m.setVisible(false);
        }
      }
    }
  }

  @Override
  public void setVisible(boolean b) {
    super.setVisible(b);

    if (menuItem != null) {
      ((JMenu) menuItem).getPopupMenu().setVisible(false);
    }
  }

  /**
   * Adds the specified component as a new menu item
   *
   * @param item the component to add
   *
   */
  @Override
  public void add(iPlatformComponent item) {
    add(new UIMenuItem(item.getView()));
  }

  @Override
  public void clear() {
    if (menuItem != null) {
      ((JMenu) menuItem).removeAll();
    }

    super.clear();
  }

  /**
   * Copies the contents of this menu to the specified
   *  menu component
   *
   * @param menu the menu to copy to
   */
  public void copyTo(JPopupMenu menu) {
    List<RenderableDataItem> items = getItems();
    int                      len   = (items == null)
                                     ? 0
                                     : items.size();
    int                      i     = 0;
    UIMenuItem               mi;

    while(i < len) {
      mi = (UIMenuItem) items.get(i++);
      menu.add(mi.getMenuItem());
    }
  }

  @Override
  public void dispose() {
    if (menuItem != null) {
      ((JMenu) menuItem).removeActionListener(this);
    }

    super.dispose();
  }

  @Override
  public void popupMenuCanceled(PopupMenuEvent e) {}

  @Override
  public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    visibleMenus.add(Platform.createWeakReference(this));
    menuWillBecomeInvisible();

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

  void removeFromVisibleList() {
    synchronized(visibleMenus) {
      visibleMenus.add(Platform.createWeakReference(this));

      int len = visibleMenus.size();

      for (int i = 0; i < len; i++) {
        iWeakReference ref = visibleMenus.get(i);

        if (ref.get() == this) {
          visibleMenus.remove(i);
          ref.clear();

          break;
        }
      }
    }
  }

  @Override
  public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
    menuWillBecomeVisible();

    synchronized(visibleMenus) {
      visibleMenus.add(Platform.createWeakReference(this));
    }
  }

  @Override
  public void setNativeItem(int pos, UIMenuItem mi) {
    JComponent item = mi.getMenuItem();

    menuItem.remove(pos);

    if (item != null) {
      menuItem.add(mi.getMenuItem(), pos);
    }
  }

  /**
   * Gets the JavaFX menu component
   *
   * @return  the JavaFX menu component
   */
  public JMenu getMenu() {
    return (JMenu) menuItem;
  }

  @Override
  protected void addToNativeMenu(int pos, UIMenuItem mi) {
    JComponent item = mi.getMenuItem();

    if (item != null) {
      if (pos < 0) {
        getMenu().add(item);
      } else {
        getMenu().add(item, pos);
      }
    }
  }

  @Override
  protected void removeNativeItem(UIMenuItem mi) {
    if ((mi != null) && (mi.getMenuItem() != null)) {
      menuItem.remove(getMenuItem());
    }
  }

  @Override
  protected iWidget getInvoker() {
    return Platform.findWidgetForComponent(menuItem);
  }

  @Override
  protected boolean hasParentMenu() {
    return (menuItem != null) && (getParentMenu() != null);
  }

  static class JMenuEx extends JMenu {
    public JMenuEx() {
      this("");
    }

    public JMenuEx(String text) {
      super(text);
    }
  }
}
