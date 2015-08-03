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

package com.appnativa.rare.viewer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.ToolBar;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.aLinearPanel;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iToolBar;
import com.appnativa.rare.widget.BeanWidget;
import com.appnativa.rare.widget.LineWidget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;

import java.util.Collections;

/**
 * A viewer that holds a set of commonly used actions that can be general or
 * context sensitive
 *
 * @author Don DeCoteau
 */
public abstract class aToolBarViewer extends aContainer implements iToolBar {
  private static PushButton toolbar_button;
  private static PushButton hyperlink_toolbar_button;

  static {
    toolbar_button = new PushButton();
    toolbar_button.buttonStyle.setValue(PushButton.CButtonStyle.toolbar);
    hyperlink_toolbar_button = new PushButton();
    hyperlink_toolbar_button.buttonStyle.setValue(PushButton.CButtonStyle.hyperlink_always_underline);
  }

  private boolean buttonShowTextDefault;

  /**
   * Constructs a new instance
   */
  public aToolBarViewer() {
    this(null);
    actAsFormViewer = true;
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aToolBarViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.ToolBar;
  }

  @Override
  public void addWidget(iWidget widget) {
    add(widget);
  }

  @Override
  public iWidget add(iPlatformComponent comp) {
    return add(null, comp);
  }

  @Override
  public iWidget add(UIAction a) {
    return add(null, a);
  }

  @Override
  public iWidget add(Widget cfg) {
    return addWidget(cfg);
  }

  @Override
  public void add(iWidget widget) {
    addComponentEx(widget.getContainerComponent());

    String name = widget.getName();

    if ((name != null) && (name.length() > 0)) {
      registerNamedItem(name, widget);
    }

    this.registerWidget(widget);
  }

  /**
   * Reverses the order the widgets are display in. This useful for changing
   * button positions based on device orientation
   */
  public void reverseWidgetOrder() {
    if (widgetList != null) {
      Collections.reverse(widgetList);
      ((aLinearPanel) dataComponent).reverseComponentOrder();
    }
  }

  @Override
  public iWidget add(String name, iPlatformComponent comp) {
    iWidget w = new BeanWidget(name, this, comp, comp, null);

    addComponentEx(comp);
    w.setParent(this);
    registerNamedItem(name, w);
    registerWidget(w);

    return w;
  }

  @Override
  public iWidget add(String name, UIAction a) {
    PushButton       cfg = (a.getIcon() == null)
                           ? hyperlink_toolbar_button
                           : toolbar_button;
    PushButtonWidget w   = PushButtonWidget.create(this, cfg);

    ((iActionComponent) w.getDataComponent()).setAction(a);

    if (name == null) {
      name = a.getActionName();
    }

    if (name != null) {
      w.setName(name);
    }

    addComponentEx(w.getContainerComponent());
    w.setParent(this);
    registerNamedItem(w.getName(), w);
    registerWidget(w);

    return w;
  }

  /**
   * Adds default actions to the toolbar (cut, copy, paste, .etc)
   */
  public void addDefaultActions() {
    add(ActionHelper.getUndoAction());
    add(ActionHelper.getRedoAction());
    add(ActionHelper.getCutAction());
    add(ActionHelper.getCopyAction());
    add(ActionHelper.getPasteAction());
  }

  public void addExpader() {
    Bean cfg = new Bean();

    cfg.name.setValue(iConstants.MENU_EXPANSION_NAME);
    addWidget(cfg);
  }

  /**
   * Sets/Unsets the specified widget as and expander for the toolbar This
   * assumes that the widget is a child of the toolbar.
   *
   * @param widget
   *          the widget
   * @param expander
   *          true if the widget is an expander; false otherwise
   */
  public void setAsExpander(iWidget widget, boolean expander) {
    widget.getContainerComponent().putClientProperty(iConstants.MENU_EXPANSION_NAME, expander
            ? Boolean.TRUE
            : Boolean.FALSE);
  }

  @Override
  public void addSeparator() {
    Bean cfg = new Bean();

    cfg.name.setValue(iConstants.MENU_SEPARATOR_NAME);
    addWidget(cfg);
  }

  @Override
  public iWidget addWidget(Widget cfg) {
    iWidget w = createWidget(this, cfg);

    if (w == null) {    // we had an error that was handled, allow the program to
      // continue so that it can be debugged
      return null;
    }

    if (w instanceof PushButtonWidget) {
      if (!buttonShowTextDefault &&!(((Button) cfg).showText).spot_valueWasSet() && (w.getIcon() != null)) {
        ((PushButtonWidget) w).setShowText(false);
        ((PushButtonWidget) w).setTooltip(((PushButtonWidget) w).getValueAsString());
      } else if (!((Button) cfg).showText.booleanValue()) {
        ((PushButtonWidget) w).setTooltip(((PushButtonWidget) w).getValueAsString());
      }
    } else if (w instanceof BeanWidget) {
      setParentHorizontal((BeanWidget) w, isHorizontal());
    }

    addComponentEx(w.getContainerComponent());

    String name = w.getName();

    if ((name != null) && (name.length() > 0)) {
      registerNamedItem(name, w);
    }

    this.registerWidget(w);

    return w;
  }

  @Override
  public void configure(Viewer vcfg) {
    vcfg = checkForURLConfig(vcfg);
    configureEx((ToolBar) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Creates the toolbar components the dataComponent is assumed to be a
   * aLinearPanel
   */
  protected abstract void createComponents(boolean horizontal);

  @Override
  public void dispose() {
    if (!isDisposable()) {
      return;
    }

    super.dispose();
  }

  @Override
  public void targetAcquired(iTarget target) {
    super.targetAcquired(target);
  }

  @Override
  public iWidget removeWidget(String name) {
    iWidget w = getWidget(name);

    if (w != null) {
      removeWidget(w);
    }

    return w;
  }

  @Override
  public void setHorizontal(boolean horizontal) {
    if (horizontal != isHorizontal()) {
      ((aLinearPanel) dataComponent).setHorizontal(horizontal);

      for (iWidget w : getWidgetList()) {
        if (w instanceof LineWidget) {
          ((LineWidget) w).setHorizontal(!horizontal);
        } else if (w instanceof BeanWidget) {
          setParentHorizontal((BeanWidget) w, horizontal);
        }
      }
    }
  }

  protected void setParentHorizontal(BeanWidget widget, boolean horizontal) {}

  @Override
  public void setSretchButtonsToFillSpace(boolean stretch) {
    if (dataComponent instanceof aLinearPanel) {
      aLinearPanel lp   = (aLinearPanel) dataComponent;
      String       spec = stretch
                          ? "FILL:DEFAULT:GROW"
                          : "FILL:DEFAULT:NONE";

      if (lp.isHorizontal()) {
        lp.setColumnSpec(spec);
      } else {
        lp.setRowSpec(spec);
      }
    }
  }

  @Override
  public void setToolbarName(String name) {
    super.setTitle(name);
  }

  @Override
  public iPlatformComponent getComponent() {
    return dataComponent;
  }

  @Override
  public String getToolbarName() {
    return getTitle();
  }

  @Override
  public boolean isHolder() {
    return false;
  }

  /**
   * Returns whether the toolbar is horizontal
   *
   * @return true if the toolbar is horizontal; false otherwise
   */
  public boolean isHorizontal() {
    return ((aLinearPanel) dataComponent).isHorizontal();
  }

  public void setComponentSpacing(int spacing) {
    ((aLinearPanel) dataComponent).setSpacing(spacing);
  }

  public int getComponentSpacing(int spacing) {
    return ((aLinearPanel) dataComponent).getSpacing();
  }

  protected void addComponentEx(iPlatformComponent component) {
    ((aLinearPanel) dataComponent).addComponent(component);

    if (!isEnabled()) {
      switch(disableBehavior) {
        case DISABLE_CONTAINER :
          break;

        case DISABLE_WIDGETS :
          component.setEnabled(false);

          break;

        case DISABLE_BOTH :
          component.setEnabled(false);

          break;
      }
    }
  }

  /**
   * Configures the viewer (doe not fire the configure event)
   *
   * @param cfg
   *          the viewer configuration
   */
  protected void configureEx(ToolBar cfg) {
    setActAsFormViewer(cfg.actAsFormViewer.booleanValue());
    buttonShowTextDefault = cfg.buttonShowTextDefault.booleanValue();

    String name = cfg.title.getValue();

    if (name == null) {
      name = cfg.name.getValue();
    }

    if (name == null) {
      name = "Standard";
    }

    createComponents(cfg.horizontal.booleanValue());
    setToolbarName(name);
    configureEx(cfg, true, false, true);
    ((aLinearPanel) dataComponent).setSpacing(Platform.getUIDefaults().getInt("Rare.ToolBar.spacing",
            (int) Math.ceil(ScreenUtils.PLATFORM_PIXELS_2)));

    if (!cfg.focusable.spot_valueWasSet()) {
      dataComponent.setFocusable(false);
    }

    if (!cfg.horizontal.booleanValue()) {
      this.setHorizontal(false);
    }

    SPOTSet set = cfg.widgets;
    int     len = set.getCount();

    startedParsing();

    for (int i = 0; i < len; i++) {
      addWidget((Widget) set.getEx(i));
    }

    finishedParsing();
  }
}
