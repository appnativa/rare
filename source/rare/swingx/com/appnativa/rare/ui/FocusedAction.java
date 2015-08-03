/*
 * @(#)FocusedAction.java 2008-01-19
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import javax.swing.Action;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.spot.ActionItem;
import com.appnativa.rare.ui.dnd.iTransferSupport;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;

/**
 * A focused action
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public class FocusedAction extends aFocusedAction implements Action {
  private iPlatformComponent _focusedComponent;

  /**
   * Constructs a new instance
   *
   * @param name the name of the action
   */
  public FocusedAction(String name) {
    super(name);
    update();
  }

  /**
   * Constructs a new instance
   *
   * @param a the action
   */
  public FocusedAction(UIAction a) {
    super(a);
    update();
  }

  /**
   * Constructs a new instance
   *
   *
   * @param context the context
   * @param item the item
   */
  public FocusedAction(iWidget context, ActionItem item) {
    super(context, item);
    update();
  }

  public FocusedAction(String name, String text, iPlatformIcon icon) {
    super(name, text, icon);
    update();
  }

  @Override
  public void dispose() {
    super.dispose();

    iPlatformComponent c = _focusedComponent;

    _focusedComponent = null;

    if (c != null) {
      //TODO: remove listeners
    }
  }

  public void refresh() {
    iPlatformComponent c = getFocusedComponent();

    if (c != null) {
      iWidget w = aWidget.getWidgetForComponent(c);

      if ((w == null) ||!isActionSupported(w, c)) {
        setEnabled(false);
      } else {
        updateEnabledFromTarget();
      }
    } else {
      setEnabled(false);
    }
  }

  @Override
  public void update() {
    update(Platform.getAppContext().getPermanentFocusOwner());
  }

  @Override
  public void update(iPlatformComponent comp) {
    if ((comp != null) &&!comp.isDisplayable()) {
      comp = null;
    }

    setFocusedComponent(comp);
  }

  protected void updateEnabledFromTarget() {
    iPlatformComponent c  = getFocusedComponent();
    boolean            on = true;

    if (this.isEnabledOnSelectionOnly()) {
      if (c instanceof iTransferSupport) {
        on = ((iTransferSupport) c).hasSelection();
      } else {
        iWidget w = aWidget.getWidgetForComponent(c);

        if ((w != null) &&!w.hasSelection()) {
          on = false;
        }
      }
    } else if (this.isEnabledIfHasValueOnly()) {
      iWidget w = aWidget.getWidgetForComponent(c);

      if ((w != null) &&!w.hasValue()) {
        on = false;
      }
    }

    setEnabled(on);
  }

  protected void setFocusedComponent(iPlatformComponent component) {
    boolean validTarget = false;

    if (component != null) {
      String name = getActionPropertyName();

      if ((name != null) && ActionHelper.isEnabled(component, name)) {
        iWidget w = aWidget.getWidgetForComponent(component);

        validTarget = isActionSupported(w, component);
      }
    }

    if (!validTarget) {
      setFocusedComponentEx(null);
    } else {
      setFocusedComponentEx(component);
      updateEnabledFromTarget();
    }
  }

  protected void setFocusedComponentEx(iPlatformComponent component) {
    if (_focusedComponent != null) {
      //TODO: remove listeners
    }

    if ((component != null) &&!component.isDisplayable()) {
      component = null;
    }

    _focusedComponent = component;

    if (_focusedComponent != null) {
      //TODO: add listener
      if (isEnabledOnSelectionOnly()) {
        //TODO: add listener
      }
    } else {
      setEnabled(false);
    }
  }

  @Override
  protected iPlatformComponent getActionComponent(ActionEvent e) {
    iPlatformComponent c = super.getActionComponent(e);

    return (c == null)
           ? _focusedComponent
           : c;
  }

  protected iPlatformComponent getActionComponent(java.awt.event.ActionEvent e) {
    Object             source = e.getSource();
    iPlatformComponent c      = null;

    if (source != null) {
      c = Platform.getPlatformComponent(source);
    }

    return (c == null)
           ? _focusedComponent
           : c;
  }

  protected iPlatformComponent getFocusedComponent() {
    return _focusedComponent;
  }

  protected boolean isActionSupported(iWidget w, iPlatformComponent component) {
    return true;
  }
}
