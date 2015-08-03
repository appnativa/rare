/*
 * @(#)UIMenuItem.java   2010-11-15
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.platform.swing.ui.util.ImageHelper;
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
    menuItem      = item;
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
    super(a);
    menuItem = item;
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
    this.fire(contextWidget);
  }

  @Override
  public void clear() {
    super.clear();
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
    if (!disposed) {
      disposed = true;
      component=null;
      menuItem=null;
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

      if (action != null) {
        menuItem.setAction(action);
      } else {
        menuItem.addActionListener(this);
      }

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
  }

  @Override
  protected void setNativeItemIcon(iPlatformIcon icon) {
    if (menuItem != null) {
      menuItem.setIcon(icon);
    }
  }

  static class JCheckBoxMenuItemEx extends JCheckBoxMenuItem {
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
      if (getAction() instanceof UIAction) {
        return ((UIAction) getAction()).getDisabledIcon();
      }

      return super.getDisabledIcon();
    }
  }


  static class JMenuItemEx extends JMenuItem {
    Icon disabledIcon;

    public JMenuItemEx() {
      super();
    }

    public JMenuItemEx(Action a) {
      super(a);
    }

    @Override
    public Icon getDisabledIcon() {
      if (getAction() instanceof UIAction) {
        Icon ic = ((UIAction) getAction()).getDisabledIcon();

        if (ic != null) {
          ic = getIcon();

          if (ic instanceof UIImageIcon) {
            ic = ((UIImageIcon) ic).getDisabledVersion();
          } else if (ic != null) {
            ic = ImageHelper.createDisabledIcon(this, ic);
          }

          setDisabledIcon(ic);

          return ic;
        }
      }

      return super.getDisabledIcon();
    }
  }
}
