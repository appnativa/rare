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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.widget.iWidget;

/**
 * A class representing a menu item
 *
 * @author Don DeCoteau
 */
public class UIMenuItem extends aUIMenuItem implements ActionListener {
  public final static UIMenuItem MENU_SEPARATOR = new UIMenuItem(iConstants.MENU_SEPARATOR_NAME);

  /** key for associating the SWING component wwhit this object */

  /** the menu component item */
  protected JComponent component;
  protected boolean    disposed;

  /** the menu item */
  protected JMenuItem menuItem;

  public UIMenuItem() {}

  /**
   * Constructs a new instance
   *
   * @param text the menu text
   */
  public UIMenuItem(CharSequence text) {
    super(text, null, null);
  }

  public UIMenuItem(JComponent comp) {
    component = comp;
  }

  /**
   * Constructs a new instance
   *
   * @param item the item
   */
  public UIMenuItem(JMenuItem item) {
    super();
    setMenuItem(item);
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
   * @param item the item
   */
  public UIMenuItem(UIAction a, JMenuItem item) {
    super();
    setMenuItem(item);
    setAction(a);
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

  @Override
  public void actionPerformed(ActionEvent e) {
    this.fire(getContextWidget());
  }

  @Override
  public Object clone() {
    if (component != null) {
      try {
        return new UIMenuItem(component.getClass().newInstance());
      } catch(Exception e) {
        throw new ApplicationException(e);
      }
    }

    return new UIMenuItem(menuItem);
  }

  @Override
  public void dispose() {
    if (!disposed && isDisposable()) {
      component = null;
      menuItem  = null;
      super.dispose();
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
  public void setActionScript(Object action) {
    super.setActionScript(action);

    if (menuItem != null) {
      menuItem.removeActionListener(this);
      menuItem.addActionListener(this);
    }
  }

  /**
   * @param checkable the checkable to set
   */
  @Override
  public void setCheckable(boolean checkable) {
    super.setCheckable(checkable);

    if (menuItem instanceof JCheckBoxMenuItem) {}
  }

  @Override
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
  @Override
  public void setMnemonic(char c) {
    mnemonic = c;

    if (menuItem != null) {
      menuItem.setMnemonic(c);
    }
  }

  /**
   * Sets the selected state of the menu
   *
   * @param selected true for selected; false otherwise
   */
  @Override
  public void setSelected(boolean selected) {
    super.setSelected(selected);
  }

  /**
   * Sets the text for the menu
   * @param value the text
   */
  @Override
  public void setText(CharSequence value) {
    super.setText(value);

    if (menuItem != null) {
      menuItem.setText((value == null)
                       ? ""
                       : value.toString());
    }
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);

    if (menuItem != null) {
      menuItem.setVisible(visible);
    }
  }

  /**
   * Gets the native menu component for the item
   * @return the native menu component for the item
   */
  public JComponent getMenuItem() {
    if (component != null) {
      return component;
    }

    if ((menuItem == null) &&!disposed) {
      menuItem = checkable
                 ? new JCheckBoxMenuItemEx()
                 : new JMenuItemEx();

      menuItem.addActionListener(this);

      menuItem.setText((theValue == null)
                       ? ""
                       : theValue.toString());
      menuItem.setToolTipText(null);

      iPlatformIcon icon = this.getIcon();

      if (!checkable && (icon == null)) {
        icon = Platform.getResourceAsIconEx("Rare.MenuItem.defaultIcon");

        if (icon == null) {
          icon = Platform.getResourceAsIconEx("Rare.icon.empty16");
        }
      }

      menuItem.setIcon(icon);
    }

    return menuItem;
  }

  @Override
  public boolean isSeparator() {
    return this == MENU_SEPARATOR;
  }

  void setMenuItem(JMenuItem item) {
    menuItem = item;
    item.removeActionListener(this);
    item.addActionListener(this);
  }

  @Override
  protected void setNativeItemIcon(iPlatformIcon icon) {
    if (menuItem != null) {
      menuItem.setIcon(icon);
    }
  }

  public static class JCheckBoxMenuItemEx extends JCheckBoxMenuItem {
    public JCheckBoxMenuItemEx() {
      super();
      setForeground(ColorUtils.getForeground());
      setBackground(ColorUtils.getBackground());
    }

    public JCheckBoxMenuItemEx(Action a) {
      super(a);
      setForeground(ColorUtils.getForeground());
      setBackground(ColorUtils.getBackground());
    }

    @Override
    public Icon getDisabledIcon() {
      return getIcon();
    }
  }


  public static class JMenuItemEx extends JMenuItem {

    public JMenuItemEx() {
      super();
    }

    public JMenuItemEx(Action a) {
      super(a);
    }

    @Override
    public Icon getDisabledIcon() {
      return super.getIcon();
    }
  }
  public static class JMenuEx extends JMenu {

    public JMenuEx() {
      super();
    }

    public JMenuEx(Action a) {
      super(a);
    }

    @Override
    public Icon getDisabledIcon() {
      if (getAction() instanceof UIAction) {
        return ((UIAction) getAction()).getDisabledIcon();
      }

      return super.getDisabledIcon();
    }
  }

}
