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

import com.google.j2objc.annotations.Weak;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.List;

public abstract class aUIMenuItem extends RenderableDataItem implements PropertyChangeListener {
  protected UIAction action;
  protected Object   actionScript;
  protected boolean  checkable;
  @Weak
  protected iWidget  contextWidget;
  protected boolean  enabledOnSelection;

  /** whether the original value has embedded variables */
  protected boolean hasVars;
  protected int     mnemonic;

  /** the item's original value */
  protected CharSequence originalValue;

  /** the parent menu component item */
  protected boolean            selected;
  protected String             theName;
  protected boolean            disposable;

  public aUIMenuItem() {}

  /**
   * Constructs a new instance
   *
   * @param text
   *          the menu text
   */
  public aUIMenuItem(CharSequence text) {
    this(text, null, null);
  }

  /**
   * Constructs a new instance
   *
   * @param text
   *          the text
   * @param mn
   *          the mnemonic
   */
  public aUIMenuItem(CharSequence text, int mn) {
    this(text, null, null);
    mnemonic = mn;
  }

  /**
   * Constructs a new instance
   *
   * @param text
   *          the text
   * @param icon
   *          the icon
   */
  public aUIMenuItem(CharSequence text, iPlatformIcon icon) {
    this(text, icon, null);
  }

  /**
   * Constructs a new instance
   *
   * @param text
   *          the text
   * @param icon
   *          the icon
   * @param data
   *          the data to associate with the menu item
   */
  public aUIMenuItem(CharSequence text, iPlatformIcon icon, Object data) {
    setValue(text);
    setIcon(icon);
    linkedData = data;
  }

  /**
   * Constructs a new instance
   *
   * @param item
   *          the item
   */
  public aUIMenuItem(RenderableDataItem item) {
    this(item.toString(), item.getIcon(), item.getLinkedData());
    this.setActionListener(item.getActionListener());
    this.setDisabledIcon(item.getDisabledIcon());
    this.setEnabled(item.isEnabled());
    this.setLinkedData(item.getLinkedData());
  }

  /**
   * Constructs a new instance
   *
   * @param a
   *          an action for the menu
   */
  public aUIMenuItem(UIAction a) {
    this(a, false);
  }

  /**
   * Constructs a new instance
   *
   * @param a
   *          an action for the menu
   * @param checkbox
   *          true for a checkbox menu item; false otherwise
   */
  public aUIMenuItem(UIAction a, boolean checkbox) {
    checkable = checkbox;
    theType   = RenderableDataItem.TYPE_STRING;
    setAction(a);
  }

  /**
   * Disposes of the menu item
   */
  @Override
  public void dispose() {
    if(isDisposable()) {
      valueContext = null;
  
      if (action != null) {
        action.removePropertyChangeListener(this);
        action = null;
      }
  
      actionListener = null;
      actionScript   = null;
      contextWidget  = null;
      parentItem     = null;
  
      List<RenderableDataItem> items = getItems();
      int                      len   = (items == null)
                                       ? 0
                                       : items.size();
      int                      i     = 0;
  
      while(i < len) {
        ((UIMenuItem) items.get(i++)).dispose();
      }
  
      super.clear();
    }
  }

  public void fire(iWidget context) {
    iWidget w = context;

    if (w == null) {
      w = getContextWidget();

      if (w == null) {
        w = Platform.getContextRootViewer();
      }

      if (w == null) {
        return;
      }
    } else if (actionListener instanceof UIAction) {
      ((UIAction) actionListener).setContext(context);
    }

    if (actionListener != null) {
      actionListener.actionPerformed(new ActionEvent(this));

      return;
    }

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

      ActionEvent ae = new ActionEvent(this);

      aWidgetListener.execute(w, scriptHandler, o, iConstants.EVENT_ACTION, ae);
    }
  }

  public UIAction getAction() {
    return action;
  }

  /**
   * Gets the action script for the item
   *
   * @return the action script for the item
   */
  public Object getActionScript() {
    return actionScript;
  }

  /**
   * Gets the context widget for the menu
   *
   * @return the context widget for the menu
   */
  public iWidget getContextWidget() {
    if(contextWidget==null) {
      aUIMenu parent=getParentMenu();
      if(parent!=null) {
        return parent.getInvoker();
      }
    }
    return contextWidget;
  }

  /**
   * Gets the disabled icon for the item
   *
   * @return the disabled icon for the item
   */
  public iPlatformIcon getDisabledIcon() {
    if ((disabledIcon != null) && (displayIcon != null)) {
      return disabledIcon.getDisabledVersion();
    }

    return disabledIcon;
  }

  /**
   * @return the mnemonic
   */
  public int getMnemonic() {
    return mnemonic;
  }

  /**
   * Gets the name of the item
   *
   * @return the name of the item
   */
  public String getName() {
    return theName;
  }

  /**
   * Gets the original value of the menu item (the value prior to the removal of
   * any embedded mnemonics or variables)
   *
   * @return the original value of the menu item
   */
  public CharSequence getOriginalValue() {
    return originalValue;
  }

  /**
   * Get the parent of this item
   *
   * @return the parent of this item
   */
  public aUIMenu getParentMenu() {
    return (aUIMenu)parentItem;
  }

  /**
   * Gets the item's text
   *
   * @return the item's text
   */
  public String getText() {
    return (theValue == null)
           ? ""
           : theValue.toString();
  }

  @Override
  public int getType() {
    return (checkable)
           ? RenderableDataItem.TYPE_BOOLEAN
           : RenderableDataItem.TYPE_STRING;
  }

  @Override
  public Object getValue() {
    return originalValue;
  }

  /**
   * Returns whether the item's original value had any embedded variables
   *
   * @return true if it hade embedded variables; false otherwise
   */
  public boolean hasVariables() {
    return hasVars;
  }

  /**
   * @return the checkable
   */
  public boolean isCheckable() {
    return checkable;
  }

  /**
   * Gets whether or not the menu is disposable (true by default);
   * @return true if disposable; false otherwise
   */
  public boolean isDisposable() {
    return disposable;
  }

  /**
   * Gets whether the item's action is only enabled when there is a selection in
   * a the associated component
   *
   * @return true if it is; false otherwise
   */
  public boolean isEnabledOnSelection() {
    return enabledOnSelection;
  }

  /**
   * Gets the selected state of the menu
   *
   * @return true for selected; false otherwise
   */
  public boolean isSelected() {
    return selected;
  }

  public abstract boolean isSeparator();

  @Override
  public void propertyChange(PropertyChangeEvent pce) {
    if (!(pce.getSource() instanceof UIAction)) {
      return;
    }

    UIAction a        = (UIAction) pce.getSource();
    String   property = pce.getPropertyName();

    if (property == "enabled") {
      if (a.isEnabled()) {
        setEnabled(true);

        if (a.getIcon() != null) {
          setIcon(a.getIcon());
        }
      } else {
        setEnabled(false);

        if (a.getDisabledIcon() != null) {
          setIcon(a.getDisabledIcon());
        }
      }
    } else if (property == "ActionText") {
      setValue(pce.getNewValue());
      setMnemonic((char) a.getMnemonic());
    } else if (property == "SmallIcon") {
      if (a.isEnabled()) {
        setIcon((iPlatformIcon) pce.getNewValue());
      } else {
        if (a.getDisabledIcon() == null) {
          setIcon((iPlatformIcon) pce.getNewValue());
        }
      }
    } else if (property == "DisabledIcon") {
      if (!a.isEnabled()) {
        setIcon((iPlatformIcon) pce.getNewValue());
      }
    }
  }

  public void setAction(UIAction a) {
    setValue(a.getActionText());
    enabledOnSelection = a.isEnabledOnSelectionOnly();
    theName            = a.getActionName();

    if (a.getDisabledIconEx() != null) {
      setDisabledIcon(a.getDisabledIconEx());
    }

    if (a.isEnabled()) {
      setIcon(a.getIcon());
      setEnabled(true);
    } else {
      setEnabled(false);
      setIcon(a.getDisabledIcon());
    }

    this.setLinkedData(a.getLinkedData());
    action = a;
    action.addPropertyChangeListener(this);
    setActionListener(action);
  }

  public void setActionScript(Object action) {
    actionScript = action;
  }

  /**
   * @param checkable
   *          the checkable to set
   */
  public void setCheckable(boolean checkable) {
    this.checkable = checkable;
  }

  /**
   * Sets the context widget for the menu
   *
   * @param context
   *          the context widget for the menu
   */
  public void setContextWidget(iWidget context) {
    contextWidget = context;
  }

  /**
   * Sets whether or not the menu is disposable (true by default);
   * @param disposable true if disposable; false otherwise
   */
  public void setDisposable(boolean disposable) {
    this.disposable = disposable;
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    updateNativeItemIconForState(enabled);
  }

  public void setEnabledOnSelection(boolean booleanValue) {}

  @Override
  public void setIcon(iPlatformIcon icon) {
    super.setIcon(icon);
    updateNativeItemIconForState(isEnabled());
  }

  /**
   * Sets the keyboard mnemonic for the item
   *
   * @param c
   *          the mnemonic
   */
  public void setMnemonic(char c) {
    mnemonic = c;
  }

  /**
   * Sets the name of the item
   *
   * @param name
   *          the name of the item
   */
  public void setName(String name) {
    this.theName = name;
  }

  /**
   * Sets the parent menu item
   *
   * @param parent
   *          the parent
   */
  public void setParentMenu(aUIMenu parent) {
    this.parentItem = parent;
  }

  /**
   * Sets the selected state of the menu
   *
   * @param selected
   *          true for selected; false otherwise
   */
  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  /**
   * Sets the text for the menu
   *
   * @param text
   *          the text
   */
  public void setText(CharSequence text) {
    theValue = text;
  }

  @Override
  public void setValue(Object value) {
    if (value instanceof String) {
      originalValue = (CharSequence) value;
      Utils.setMnemonicAndText(this, (String) value);
      hasVars = ((String) value).indexOf('{') != -1;
    } else if (value instanceof CharSequence) {
      originalValue = (CharSequence) value;
      setText((CharSequence) value);
    }
  }

  @Override
  public String toString() {
    return getText();
  }

  @Override
  public void toString(StringBuilder sb, String tab) {
    sb.append(tab).append(toString());
  }

  protected abstract void setNativeItemIcon(iPlatformIcon icon);

  protected void updateNativeItemIconForState(boolean enabled) {
    iPlatformIcon icon = isEnabled()
                         ? getIcon()
                         : getDisabledIcon();

    if (icon == null) {
      icon = getIcon();
    }

    setNativeItemIcon(icon);
  }

  /**
   * Creates a checkbox menu item
   *
   * @param text
   *          the text
   * @param icon
   *          the icon
   * @param data
   *          the data to associate with the menu item
   *
   * @return a checkbox menu item
   */
  public static UIMenuItem createCheckboxMenuItem(CharSequence text, iPlatformIcon icon, Object data) {
    UIMenuItem menu = new UIMenuItem(text, icon, data, true);

    return menu;
  }

  /**
   * Creates a checkbox menu item
   *
   *
   * @param item
   *          the data item;
   * @return a checkbox menu item
   */
  public static UIMenuItem createCheckboxMenuItem(RenderableDataItem item) {
    String        text = item.toString();
    iPlatformIcon icon = item.getIcon();
    Object        data = item.getLinkedData();
    UIMenuItem    mi   = new UIMenuItem(text, icon, data, true);

    mi.setActionListener(item.getActionListener());
    mi.setDisabledIcon(item.getDisabledIcon());
    mi.setEnabled(item.isEnabled());

    return mi;
  }

  /**
   * Creates a checkbox menu item
   *
   * @param text
   *          the text
   *
   * @return a checkbox menu item
   */
  public static UIMenuItem createCheckboxMenuItem(String text) {
    return createCheckboxMenuItem(text, null, null);
  }

  /**
   * Creates a checkbox menu item
   *
   * @param text
   *          the text
   * @param icon
   *          the icon
   *
   * @return a checkbox menu item
   */
  public static UIMenuItem createCheckboxMenuItem(String text, iPlatformIcon icon) {
    return createCheckboxMenuItem(text, icon, null);
  }

  /**
   * Creates a menu item
   *
   * @param text
   *          the text
   *
   * @return a menu item
   */
  public static UIMenuItem createMenuItem(CharSequence text) {
    return createMenuItem(text, null, null);
  }

  /**
   * Creates a menu item
   *
   * @param text
   *          the text
   * @param icon
   *          the icon
   *
   * @return a menu item
   */
  public static UIMenuItem createMenuItem(CharSequence text, iPlatformIcon icon) {
    return createMenuItem(text, icon, null);
  }

  /**
   * Creates a menu item
   *
   * @param text
   *          the text
   * @param icon
   *          the icon
   * @param data
   *          the data to associate with the menu item
   *
   * @return a menu item
   */
  public static UIMenuItem createMenuItem(CharSequence text, iPlatformIcon icon, Object data) {
    return createMenuItem(text, icon, data, null);
  }

  /**
   * Creates a menu item
   *
   * @param text
   *          the text
   * @param icon
   *          the icon
   * @param data
   *          the data to associate with the menu item
   * @param action
   *          the action script code for the menu
   *
   * @return a menu item
   */
  public static UIMenuItem createMenuItem(CharSequence text, iPlatformIcon icon, Object data, Object action) {
    UIMenuItem mi = new UIMenuItem(text, icon, data, false);

    if (action != null) {
      mi.setActionScript(action);
    }

    return mi;
  }
}
