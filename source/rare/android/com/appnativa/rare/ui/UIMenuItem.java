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

import android.view.MenuItem;

import com.appnativa.rare.iConstants;

/**
 * A class representing a menu item
 *
 * @author Don DeCoteau
 */
public class UIMenuItem extends aUIMenuItem {
  public final static UIMenuItem MENU_SEPARATOR = new UIMenuItem(iConstants.MENU_SEPARATOR_NAME);

  /** the menu component item */
  protected MenuItem menuItem;

  public UIMenuItem() {}

  /**
   * Constructs a new instance
   *
   * @param text the menu text
   */
  public UIMenuItem(CharSequence text) {
    super(text, null, null);
  }

  /**
   * Constructs a new instance
   *
   * @param item the item
   */
  public UIMenuItem(MenuItem item) {
    super();
    menuItem      = item;
    originalValue = item.getTitle();
    theType       = RenderableDataItem.TYPE_STRING;
  }

  /**
   * Constructs a new instance
   *
   * @param item the item
   */
  public UIMenuItem(RenderableDataItem item) {
    super(item);
  }

  /**
   * Constructs a new instance
   *
   * @param a an action for the menu
   */
  public UIMenuItem(UIAction a) {
    super(a);
  }

  /**
   * Constructs a new instance
   *
   * @param text the text
   * @param mn the mnemonic
   */
  public UIMenuItem(CharSequence text, int mn) {
    super(text, mn);
  }

  /**
   * Constructs a new instance
   *
   * @param text the text
   * @param icon the icon
   */
  public UIMenuItem(CharSequence text, iPlatformIcon icon) {
    super(text, icon);
  }

  /**
   * Constructs a new instance
   *
   * @param a an action for the menu
   * @param checkbox true for a checkbox menu item; false otherwise
   */
  public UIMenuItem(UIAction a, boolean checkbox) {
    super(a, checkbox);
  }

  /**
   * Constructs a new instance
   *
   * @param text the text
   * @param icon the icon
   * @param data the data to associate with the menu item
   */
  public UIMenuItem(CharSequence text, iPlatformIcon icon, Object data, boolean checkbox) {
    super(text, icon, data);
    setCheckable(checkbox);
  }

  public void clear() {
    super.clear();
    menuItem = null;
  }

  public Object clone() {
    return new UIMenuItem(menuItem);
  }

  /**
   * @param checkable the checkable to set
   */
  public void setCheckable(boolean checkable) {
    super.setCheckable(checkable);

    if (menuItem != null) {
      menuItem.setCheckable(checkable);
    }
  }

  public void setEnabled(boolean b) {
    super.setEnabled(b);

    if (menuItem != null) {
      menuItem.setEnabled(b);
    }
  }

  /**
   * Sets the keyboard mnemonic for the item
   * @param c the mnemonic
   */
  public void setMnemonic(char c) {
    mnemonic = c;

    if (menuItem != null) {
      menuItem.setAlphabeticShortcut(c);
    }
  }

  /**
   * Sets the selected state of the menu
   *
   * @param selected true for selected; false otherwise
   */
  public void setSelected(boolean selected) {
    super.setSelected(selected);
  }

  /**
   * Sets the text for the menu
   * @param value the text
   */
  public void setText(CharSequence value) {
    super.setText(value);

    if (menuItem != null) {
      menuItem.setTitle(value);
    }
  }

  public void setVisible(boolean b) {
    super.setVisible(b);

    if (menuItem != null) {
      menuItem.setVisible(b);
    }
  }

  /**
   * Gets the native menu component for the item
   * @return the native menu component for the item
   */
  public MenuItem getMenuItem() {
    return menuItem;
  }

  void setMenuItem(MenuItem item) {
    menuItem = item;
  }

  @Override
  public boolean isSeparator() {
    return this == MENU_SEPARATOR;
  }

  @Override
  protected void setNativeItemIcon(iPlatformIcon icon) {
    if (menuItem != null) {
      menuItem.setIcon((icon == null)
                       ? null
                       : icon.getDrawable(null));
    }
  }
}
