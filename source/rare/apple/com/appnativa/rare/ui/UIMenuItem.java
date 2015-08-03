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
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.widget.iWidget;

import java.beans.PropertyChangeListener;

/**
 * A class representing a menu item
 *
 * @author Don DeCoteau
 */
public class UIMenuItem extends aUIMenuItem implements PropertyChangeListener {

  /** the menu component item */
  protected MenuItem menuItem;

  public UIMenuItem() {}

  /**
   * Constructs a new instance
   *
   * @param text the menu text
   */
  public UIMenuItem(CharSequence text) {
    this(text, null, null, false);
  }

  /**
   * Constructs a new instance
   *
   * @param item the item
   */
  public UIMenuItem(MenuItem item) {
    super();
    menuItem = item;
    setupItem();
    originalValue = item.getText();
    theType       = RenderableDataItem.TYPE_STRING;
  }

  /**
   * Constructs a new instance
   *
   * @param item the item
   */
  public UIMenuItem(RenderableDataItem item) {
    super(item);
    menuItem = new MenuItem(false);
    setupItem();
  }

  /**
   * Constructs a new instance
   *
   * @param a an action for the menu
   */
  public UIMenuItem(UIAction a) {
    super(a);
    menuItem = new MenuItem(false);
    setupItem();
  }

  /**
   * Constructs a new instance
   *
   * @param text the text
   * @param icon the icon
   */
  public UIMenuItem(CharSequence text, iPlatformIcon icon) {
    this(text, icon, null, false);
  }

  /**
   * Constructs a new instance
   *
   * @param a an action for the menu
   * @param checkbox true for a check box menu item; false otherwise
   */
  public UIMenuItem(final UIAction a, boolean checkbox) {
    this(a, new MenuItem(checkbox));
  }

  /**
   * Constructs a new instance
   *
   * @param a an action for the menu
   * @param item the java FX menu item
   */
  public UIMenuItem(final UIAction a, MenuItem item) {
    super();
    menuItem = item;
    setupItem();
    setAction(a);
  }

  /**
   * Constructs a new instance
   *
   * @param text the text
   * @param icon the icon
   * @param data the data to associate with the menu item
   * @param checkbox true for a checkbox menu item; false otherwise
   */
  public UIMenuItem(CharSequence text, iPlatformIcon icon, Object data, boolean checkbox) {
    super(null, icon, data);
    menuItem = new MenuItem(checkbox);
    setupItem();
    setValue(text);
  }

  /**
   * Disposes of the menu item
   */
  @Override
  public void dispose() {
    super.dispose();

    if (menuItem != null) {
      menuItem.dispose();
    }

    this.menuItem = null;
  }

  public void handle(ActionEvent e) {
    if (menuItem == null) {
      return;
    }

    iWidget w = Platform.findWidgetForComponent(menuItem);

    if (w == null) {
      w = getContextWidget();
    }

    if (w == null) {
      w = Platform.getContextRootViewer();
    }

    if (w == null) {
      return;
    }

//  if (w instanceof MenuBarViewer) {
//    ((MenuBarViewer) w).setSelectedItem(mi);
//  }
//
    iScriptHandler scriptHandler = w.getScriptHandler();
    Object         o             = getActionScript();

    if (o != null) {
      if (o instanceof String) {
        String name = getName();

        if (name == null) {
          name = getText();
        }

        name = "onAction." + name;
        o    = scriptHandler.compile(w.getScriptingContext(), name, (String) o);
        setActionScript(o);
      }

      com.appnativa.rare.ui.event.ActionEvent ae = new com.appnativa.rare.ui.event.ActionEvent(this, e);

      aWidgetListener.execute(w, scriptHandler, o, iConstants.EVENT_ACTION, ae);
    }
  }

  public void updateText() {
    if (hasVars && (menuItem != null) && (originalValue != null)) {
      iWidget w = (contextWidget == null)
                  ? Platform.getContextRootViewer()
                  : contextWidget;

      menuItem.setText(w.expandString((String) originalValue, false));
    }
  }

  @Override
  public void setCheckable(boolean checkable) {
    super.setCheckable(checkable);

    if (menuItem != null) {
      menuItem.setCheckable(checkable);
    }
  }

  @Override
  public void setMnemonic(char mn) {}

  /**
   * Sets the selected state of the menu
   *
   * @param selected true for selected; false otherwise
   */
  @Override
  public void setSelected(boolean selected) {
    super.setSelected(selected);
    this.selected = selected;
    menuItem.setSelected(checkable);
  }

  /**
   * Sets the text for the menu
   * @param text the text
   */
  @Override
  public void setText(CharSequence text) {
    super.setText(text);

    if (menuItem != null) {
      menuItem.setText(text);
    }
  }

  @Override
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

  public Object getProxy() {
    return (menuItem == null)
           ? null
           : menuItem.getProxy();
  }

  @Override
  public boolean isSeparator() {
    return menuItem.isSeparator();
  }

  void setMenuItem(MenuItem item) {
    menuItem = item;
  }

  protected void setupItem() {
    menuItem.setUserData(this);
  }

  @Override
  protected void setNativeItemIcon(iPlatformIcon icon) {
    if (menuItem != null) {
      menuItem.setIcon(icon);
    }
  }
}
