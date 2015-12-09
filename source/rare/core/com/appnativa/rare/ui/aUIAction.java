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
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.ActionItem;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.widget.iWidget;
import com.google.j2objc.annotations.Weak;

import java.beans.PropertyChangeListener;

/**
 * A class representing an executable action
 *
 * @author Don DeCoteau
 */
public abstract class aUIAction implements iActionListener {
  public static final String ICON = "Icon";
  public static final String LARGE_ICON = "LargeIcon";
  public static final String SHORT_DESCRIPTION = "ShortDescription";
  public static final String DISABLED_ICON = "DisabledIcon";
  public static final String ACTION_TEXT = "ActionText";
  public static final String ACTION_NAME = "ActionName";
  public static final String ENABLED = iConstants.PROPERTY_ENABLED;
  protected boolean           enabled = true;
  protected iActionListener   actionListener;
  protected String            actionName;
  protected String            actionPropertyName;
  protected Object            actionScript;
  protected CharSequence      actionText;
  @Weak
  protected iWidget           contextWidget;
  protected iPlatformIcon     disabledIcon;
  protected boolean           disposed;
  protected boolean           enabledIfHasValueOnly;
  protected boolean           enabledOnSelectionOnly;
  protected String            groupName;
  protected iPlatformIcon     largeIcon;
  protected Object            linkedData;
  protected EventListenerList listenerList;
  protected CharSequence      longDescription;
  protected int               mnemonic;
  protected iPlatformIcon     selectedIcon;
  protected CharSequence      shortDescription;
  protected iPlatformIcon     smallIcon;
  
  /**
   * Constructs a new instance
   *
   * @param a an action to copy from
   */
  public aUIAction(aUIAction a) {
    super();
    setActionText(a.actionText);
    setShortDescription(a.shortDescription);
    setLongDescription(a.longDescription);
    setIcon(a.smallIcon);
    setMnemonic(a.mnemonic);
    setActionName(a.actionName);
    this.setActionListener(a);
  }

  /**
   * Constructs a new instance
   *
   * @param name the name of the action
   */
  public aUIAction(String name) {
    super();
    setActionName(name);
  }

  /**
   * Creates a new instance of UIAction
   *
   *
   * @param context the context for the action
   * @param item the item to use to configure the action
   */
  public aUIAction(iWidget context, ActionItem item) {
    super();
    this.contextWidget = context;

    if (context == null) {
      context = Platform.getContextRootViewer();
    }

    String        text = context.expandString(item.value.getValue(), false);
    iPlatformIcon icon = context.getIcon(item.icon);
    int           len  = 0;
    char          mn   = 0;

    if (text == null) {
      text = "";
    }

    int n = text.indexOf('&');

    if (n != -1) {
      if (n == (len - 1)) {
        text = text.substring(0, n);
      } else if (n == 0) {
        mn   = text.charAt(1);
        text = text.substring(1);
      } else {
        mn   = text.charAt(n + 1);
        text = text.substring(0, n) + text.substring(n + 1);
      }
    }

    setActionText(text);

    if (mn != 0) {
      setMnemonic(mn);
    }

    disabledIcon = context.getIcon(item.disabledIcon);
    selectedIcon = context.getIcon(item.selectedIcon);
    linkedData   = item.linkedData.getValue();
    setActionName(item.name.getValue());

    if (item.getActionLink() != null) {
      actionListener = new ActionLink(context, item.getActionLink());
    } else {
      actionScript = item.spot_getAttribute("onAction");
    }

    enabledOnSelectionOnly = item.enabledOnSelectionOnly.booleanValue();
    enabledIfHasValueOnly  = item.enabledIfHasValueOnly.booleanValue();

    if (icon != null) {
      setIcon(icon);
    }

    setEnabled(item.enabled.booleanValue());

    String tip = item.tooltip.getValue();

    if ((tip != null) && (tip.length() > 0)) {
      tip = context.expandString(tip, false);
      setShortDescription(tip);
    }

    groupName = item.groupName.getValue();
  }

  /**
   * Constructs a new instance
   *
   * @param name the name of the action
   * @param text the text
   * @param icon the icon
   */
  public aUIAction(String name, CharSequence text, iPlatformIcon icon) {
    super();
    setActionName(name);
    setActionText(text);
    setIcon(icon);
  }

  /**
   * Constructs a new instance
   *
   * @param name the name of the action
   * @param text the text
   * @param icon the icon
   * @param desc the description of the action
   */
  public aUIAction(String name, CharSequence text, iPlatformIcon icon, String desc) {
    super();
    setActionName(name);
    setActionText(text);
    setIcon(icon);
    setActionDescription(desc);
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the action event
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (isDisposed()) {
      return;
    }

    if (actionListener != null) {
      actionListener.actionPerformed(new ActionEvent(this, e));

      return;
    }

    iPlatformComponent c       = getActionComponent(e);
    iWidget            context = (c == null)
                                 ? null
                                 : Platform.findWidgetForComponent(c);

    if (context == null) {
      context = this.contextWidget;

      if (context == null) {
        context = Platform.findWidgetForComponent(e.getComponent());

        if (context == null) {
          context = Platform.getContextRootViewer();
        }
      } else if (context.getWidgetType() == null) {
        context = context.getAppContext().getRootViewer();
      }
    }

    if (context == null) {
      return;
    }

    iScriptHandler sh = context.getViewer().getScriptHandler();

    if (actionScript instanceof String) {
      actionScript = sh.compile(context.getScriptingContext(), getActionName(), (String) actionScript);
    }

    if (actionScript != null) {
      aWidgetListener.execute(context, sh, actionScript, iConstants.EVENT_ACTION, e);
    }
  }

  public synchronized void addPropertyChangeListener(PropertyChangeListener pcl) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    listenerList.add(PropertyChangeListener.class, pcl);
  }

  /**
   * Configures extra button options like selected icon, etc
   * that are not part of the base action
   *
   * @param button the button to configure
   */
  public void configureExtraButtonOptions(iActionComponent button) {
    if (disabledIcon != null) {
      button.setDisabledIcon(disabledIcon);
    }

    if (getSelectedIcon() != null) {
      button.setSelectedIcon(getSelectedIcon());
    }
  }

  /**
   * Creates an action from the specified action item
   * @param context the context
   * @param item the action item
   *
   * @return the new action
   */
  public static UIAction createAction(iWidget context, ActionItem item) {
    return new UIAction(context, item);
  }

  /**
   * DIsposes of the action
   */
  public void dispose() {
    if (disposed) {
      return;
    }

    if (listenerList != null) {
      listenerList.clear();
    }

    disposed       = true;
    linkedData     = null;
    actionListener = null;
    actionScript   = null;
    contextWidget  = null;
    listenerList   = null;
  }

  public synchronized void removePropertyChangeListener(PropertyChangeListener pcl) {
    if ((listenerList != null) && (pcl != null)) {
      listenerList.remove(PropertyChangeListener.class, pcl);
      if(pcl instanceof Component) {
        iWidget w=((Component)pcl).getWidget();
        if(w==contextWidget) {
          contextWidget=null;
        }
      }
    }
  }

  /**
   * Populates a renderable data item from an action
   * @param item the item to populate (if null a new one is created)
   * @param setaction true to the the item's action listener ot his action; false otherwise
   *
   * @return the populated item
   */
  public RenderableDataItem toDataItem(RenderableDataItem item, boolean setaction) {
    if (item == null) {
      item = new RenderableDataItem();
    }

    item.setType(RenderableDataItem.TYPE_STRING);
    item.setTooltip(getShortDescription());
    item.setValue(this.getActionText());
    item.setIcon(this.getIcon());
    item.setDisabledIcon(this.getDisabledIcon());
    item.setLinkedData(this);

    if (setaction) {
      item.setActionListener(this);
    }

    return item;
  }

  @Override
  public String toString() {
    String name = this.getActionName();

    return (name == null)
           ? ":" + this.getActionText()
           : name + ":" + this.getActionText();
  }

  /**
   * Sets the values for the action
   *
   * @param text the text
   * @param icon the icon
   * @param desc the description of the action
   */
  public void set(String text, String desc, iPlatformIcon icon) {
    setActionText(text);
    setActionDescription(desc);
    setIcon(icon);
  }

  /**
   *  Sets the description for the action
   *
   *  @param description the description for the action
   */
  public void setActionDescription(String description) {
    setShortDescription(description);
  }

  /**
   * Sets the action listener for the action
   *
   * @param l the listener
   */
  public void setActionListener(iActionListener l) {
    this.actionListener = l;
  }

  /**
   * Sets the actionText of the action
   *
   * @param actionName the actionText of the action
   */
  public void setActionName(String actionName) {
    if (actionName != null) {
      if (actionName.startsWith("Rare")) {
        actionPropertyName = actionName;
      } else {
        actionPropertyName = iConstants.APP_ACTION_PREFIX + actionName;
      }
    }

    String ot = this.actionName;

    this.actionName = actionName;

    if ((listenerList != null) && (ot != actionName) && ((actionName != null) &&!actionName.equals(ot))) {
      firePropertyChange(ACTION_NAME, ot, actionName);
    }
  }

  /**
   * Sets the action script for the action
   *
   * @param actionScript the script to execute when the action is activated
   */
  public void setActionScript(Object actionScript) {
    this.actionScript = actionScript;
  }

  /**
   * Sets the text for the action
   *
   * @param text the text for the action
   */
  public void setActionText(CharSequence text) {
    int len = 0;
    int mn  = 0;

    if (text == null) {
      text = "";
    }

    if (text instanceof String) {
      String s = (String) text;
      int    n = s.indexOf('_');

      if (n != -1) {
        if (n == (len - 1)) {
          text = s.substring(0, n);
        } else if (n == 0) {
          mn   = s.charAt(1);
          text = s.substring(1);
        } else {
          mn   = s.charAt(n + 1);
          text = s.substring(0, n) + s.substring(n + 1);
        }
      }
    }

    CharSequence ot = actionText;

    actionText = text;

    if (mn != 0) {
      mnemonic = mn;
    }

    if ((listenerList != null) && (ot != actionText) && ((actionText != null) &&!actionText.equals(ot))) {
      firePropertyChange(ACTION_TEXT, ot, actionText);
    }
  }

  /**
   * Sets the context
   *
   * @param context the context
   */
  public void setContext(iWidget context) {
    this.contextWidget = context;
  }

  /**
   * Sets the disabled icon for the action
   *
   * @param icon the disabled icon for the action
   */
  public void setDisabledIcon(iPlatformIcon icon) {
    iPlatformIcon oc = disabledIcon;

    this.disabledIcon = icon;

    if ((listenerList != null) && (oc != icon)) {
      firePropertyChange(DISABLED_ICON, oc, icon);
    }
  }

  public void setEnabled(boolean enabled) {
    if (enabled != this.enabled) {
      this.enabled = enabled;
      firePropertyChange(ENABLED,!enabled, enabled);
    }
  }

  /**
   * Sets whether the item's action is only enabled when there is a value in a supported component
   *
   * @param enabled true to enable only if the associated component has a value; false otherwise
   */
  public void setEnabledIfHasValueOnly(boolean enabled) {
    this.enabledIfHasValueOnly = enabled;
  }

  /**
   * Sets whether the item's action is only enabled when there is a selection in a supported component
   *
   * @param enabled true to enable only on selection; false otherwise
   */
  public void setEnabledOnSelectionOnly(boolean enabled) {
    this.enabledOnSelectionOnly = enabled;
  }

  /**
   * Sets the group actionText for the action
   *
   * @param groupName the group actionText for the action
   */
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /**
   * Sets the icon for the action
   *
   * @param icon the icon for the action
   */
  public void setIcon(iPlatformIcon icon) {
    iPlatformIcon oc = this.smallIcon;

    this.smallIcon = icon;

    if (oc != smallIcon) {
      firePropertyChange(ICON, oc, smallIcon);
    }
  }

  /**
   * @param largeIcon the largeIcon to set
   */
  public void setLargeIcon(iPlatformIcon largeIcon) {
    iPlatformIcon oc = this.largeIcon;

    this.largeIcon = largeIcon;

    if ((listenerList != null) && (oc != smallIcon)) {
      firePropertyChange(LARGE_ICON, oc, largeIcon);
    }
  }

  /**
   * Sets the linked data associated with  the action
   *
   * @param data the linked data associated with  the action
   */
  public void setLinkedData(Object data) {
    this.linkedData = data;
  }

  /**
   * @param longDescription the longDescription to set
   */
  public void setLongDescription(CharSequence longDescription) {
    CharSequence ot = this.longDescription;

    this.longDescription = longDescription;

    if ((listenerList != null) && (ot != shortDescription)
        && ((shortDescription != null) &&!shortDescription.equals(ot))) {
      firePropertyChange(SHORT_DESCRIPTION, ot, shortDescription);
    }
  }

  /**
   * @param mnemonic the mnemonic to set
   */
  public void setMnemonic(int mnemonic) {
    this.mnemonic = mnemonic;
  }

  /**
   *  Sets the selected icon
   *
   * @param icon the selected icon to set
   */
  public void setSelectedIcon(iPlatformIcon icon) {
    this.selectedIcon = icon;
  }

  /**
   * @param showrDescription the showrDescription to set
   */
  public void setShortDescription(CharSequence showrDescription) {
    CharSequence ot = this.shortDescription;

    this.shortDescription = showrDescription;

    if ((ot != shortDescription) && ((shortDescription != null) &&!shortDescription.equals(ot))) {
      firePropertyChange(SHORT_DESCRIPTION, ot, shortDescription);
    }
  }


  /**
   * Sets the text for the action
   *
   * @param text the text for the action
   */
  public void setValue(String text) {
    setActionText(text);
  }

  /**
   * gets the description for the action
   *
   * @return the description for the action
   */
  public CharSequence getActionDescription() {
    return shortDescription;
  }

  /**
   * Gets the action listener
   *
   * @return the action listener
   */
  public iActionListener getActionListener() {
    return actionListener;
  }

  /**
   * Gets the name of the action
   *
   * @return the name of the action
   */
  public String getActionName() {
    return actionName;
  }

  /**
   * Returns the actionText of the property used to register support for this action.
   * This is than actionText that would be used with iPlatformComponent.getClientProperty
   *
   * @return the property actionText
   *
   */
  public String getActionPropertyName() {
    return (actionPropertyName == null)
           ? getActionName()
           : actionPropertyName;
  }

  /**
   * Gets the text for the action
   *
   * @return the text for the action
   */
  public CharSequence getActionText() {
    return actionText;
  }

  /**
   * Gets the context
   *
   * @return the context
   */
  public iWidget getContext() {
    return contextWidget;
  }

  /**
   * Gets the disabled icon
   *
   * @return the disabled icon
   */
  public iPlatformIcon getDisabledIcon() {
    if ((disabledIcon == null) && (smallIcon != null)) {
      disabledIcon = getIcon().getDisabledVersion();
    }

    return disabledIcon;
  }


  /**
   * Gets the disabled icon if one was set
   *
   * @return the disabled icon of null if one was not set
   */
  public iPlatformIcon getDisabledIconEx() {
    return disabledIcon;
  }

  /**
   * Gets the group actionText for the action
   *
   * @return group actionText for the action
   */
  public String getGroupName() {
    return groupName;
  }

  /**
   * Gets the icon
   *
   * @return the icon
   */
  public iPlatformIcon getIcon() {
    return smallIcon;
  }

  /**
   * @return the largeIcon
   */
  public iPlatformIcon getLargeIcon() {
    return largeIcon;
  }

  /**
   * Gets the linked for the action
   *
   * @return the linked for the action
   */
  public Object getLinkedData() {
    return linkedData;
  }

  /**
   * @return the longDescription
   */
  public CharSequence getLongDescription() {
    return longDescription;
  }

  /**
   * @return the mnemonic
   */
  public int getMnemonic() {
    return mnemonic;
  }

  public PropertyChangeListener[] getPropertyChangeListeners() {
    if (listenerList == null) {
      return null;
    }

    return listenerList.getListeners(PropertyChangeListener.class);
  }

  /**
   * Gets the selected icon
   *
   * @return the selected icon
   */
  public iPlatformIcon getSelectedIcon() {
    return selectedIcon;
  }

  /**
   * @return the showrDescription
   */
  public CharSequence getShortDescription() {
    return shortDescription;
  }
 
  /**
   * Returns whether an action will be performed when actionPerformed is called
   *
   * @return true is an action will be performed; false otherwise
   */
  public boolean hasActionToPerform() {
    return (actionListener != null) || (actionScript != null);
  }

  /**
   * Returns whether the action was disposed
   *
   * @return true if it was disposed; false otherwise
   */
  public boolean isDisposed() {
    return disposed;
  }

  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Gets whether the item's  action is only enabled when there is a value in the associated component
   *
   * @return true if it is; false otherwise
   */
  public boolean isEnabledIfHasValueOnly() {
    return enabledIfHasValueOnly;
  }

  /**
   * Gets whether the item's  action is only enabled when there is a selection in the associated component
   *
   * @return true if it is; false otherwise
   */
  public boolean isEnabledOnSelectionOnly() {
    return enabledOnSelectionOnly;
  }

  /**
   * Gets the component for the action event
   *
   * @param e the action event
   *
   * @return the component for the action event or null if the component cannot be determined
   */
  protected iPlatformComponent getActionComponent(ActionEvent e) {
    return (e == null)
           ? null
           : e.getComponent();
  }

  private void firePropertyChange(String property, Object oldValue, Object newValue) {
    if (listenerList != null) {
      Utils.firePropertyChangeEvent(this, listenerList, property, oldValue, newValue);
    }
  }
}
