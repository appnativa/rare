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

package com.appnativa.rare.widget;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.ActionItem;
import com.appnativa.rare.spot.Margin;
import com.appnativa.rare.spot.Navigator;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.aNavigatorPanel;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iNavigatorPanel;
import com.appnativa.rare.ui.iNavigatorPanel.PanelType;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.spot.SPOTSet;

import com.google.j2objc.annotations.Weak;

import java.util.Locale;

/**
 * A widget that provides the navigation of a hierarchy as a horizontal series
 * of click-able buttons that identify the path taken.
 * <p>
 * A home and back button can be provided to allow the user to back up or go
 * directly back to the beginning.
 * </p>
 *
 * @author Don DeCoteau
 */
public abstract class aNavigatorWidget extends aPlatformWidget {
  protected iNavigatorPanel panel;

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aNavigatorWidget(iContainer parent) {
    super(parent);
    widgetType    = WidgetType.Navigator;
    isSubmittable = false;
  }

  /**
   * Adds a action to the navigation panel
   *
   * @param a
   *          the action for the button
   *
   * @return the newly created button
   */
  public iActionComponent addAction(UIAction a) {
    iActionComponent nb = panel.addButton(a);

    dataComponent.revalidate();
    dataComponent.repaint();

    return nb;
  }

  /**
   * Adds a action to the navigation panel
   *
   * @param text
   *          the text for the button
   * @param link
   *          the link to activate when the button is pressed
   *
   * @return the newly created button
   */
  public iActionComponent addAction(String text, ActionLink link) {
    UIAction a = new UIAction(text);

    a.setActionListener(link);
    a.setContext(this);

    return addAction(a);
  }

  /**
   * Adds a action to the navigation panel
   *
   * @param text
   *          the text for the button
   * @param code
   *          the code to execute when the button is pressed
   *
   * @return the newly created button
   */
  public iActionComponent addAction(String text, String code) {
    UIAction a = new UIAction(text);

    a.setActionScript(code);
    a.setContext(this);

    return addAction(a);
  }

  /**
   * Backs up one place in the hierarchy
   */
  public void backup() {
    panel.backup();
  }

  /**
   * Click the button at the given index
   *
   * @param index
   *          the button's index
   */
  public void click(int index) {
    panel.click(index);
  }

  /**
   * Click the button at the given index
   *
   * @param name the name of the action
   */
  public void click(String name) {
    final int n = indexForName(name);

    if (n != -1) {
      click(n);
    }
  }

  @Override
  public void configure(Widget cfg) {
    configureEx((Navigator) cfg);
    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Creates a new navigator widget
   *
   * @param parent
   *          the parent
   * @param cfg
   *          the configuration
   *
   * @return the navigator widget
   */
  public static NavigatorWidget create(iContainer parent, Navigator cfg) {
    NavigatorWidget widget = new NavigatorWidget(parent);

    widget.configure(cfg);

    return widget;
  }

  @Override
  public void dispose() {
    super.dispose();
    panel = null;
  }

  /**
   * Navigates to the home position
   */
  public void home() {
    panel.home();
    update();
  }

  /**
   * Returns the index for the given action name
   *
   * @param name
   *          the name of the action
   * @return the index for the given action name
   */
  public int indexForName(String name) {
    return panel.indexOf(name);
  }

  /**
   * Removes the action at the specified index
   *
   * @param index
   *          the index of the action
   */
  public void removeAction(int index) {
    panel.removeButton(index);
  }

  /**
   * Removes the action with the specified name
   *
   * @param name
   *          the name of the action
   */
  public void removeAction(String name) {
    panel.removeButton(name);
  }

  /**
   * Sets whether the action at the specified index is enabled
   *
   * @param index
   *          the index
   * @param enabled
   *          true to enable; false to disable
   */
  public void setActionEnabled(int index, boolean enabled) {
    panel.setActionEnabled(index, enabled);
  }

  /**
   * Sets whether the action at the specified index is visible
   *
   * @param index
   *          the index
   * @param visible
   *          true to show; false to hide
   */
  public void setActionVisible(int index, boolean visible) {
    panel.getButton(index).setVisible(visible);
    update();
  }

  /**
   * Sets whether the action event is always fired for toggle type buttons. By
   * default the action is only fired if the button is not currently selected
   *
   * @param always
   *          true to fire the event even if the button is already selected;
   *          false otherwise
   */
  public void setAlwaysFireAction(boolean always) {
    panel.setAlwaysFireAction(always);
  }

  /**
   * Sets the type of navigator panel
   *
   * @param type
   *          the type of navigator panel
   */
  public void setPanelType(PanelType type) {
    panel.setPanelType(type);
  }

  /**
   * Selects the action at the specified index
   *
   * @param index
   *          the index of the action to select
   */
  public void setSelectedIndex(int index) {
    panel.setSelectedIndex(index);
  }

  /**
   * Selects the action with the specified name
   *
   * @param name
   *          the name of the button to select
   */
  public void setSelectedName(String name) {
    panel.setSelectedButton(name);
  }

  /**
   * Gets whether to show only icons or toe show both icons and text
   *
   * @param icon
   *          true true to show icon only; false to show icon and text
   */
  public void setShowIconsOnly(boolean icon) {
    panel.setShowIconsOnly(icon);
  }

  @Override
  public void setValue(Object value) {
    iActionComponent b = panel.getSelectedButton();
    String           s = null;

    if (value instanceof String) {
      s = (String) value;
    } else if (value != null) {
      s = value.toString();
    }

    if ((s != null) && (b != null)) {
      b.setText(s);
    }
  }

  /**
   * Gets the action at the specified index
   *
   * @param index
   *          the index of the action
   *
   * @return the action
   */
  public UIAction getAction(int index) {
    return panel.getAction(index);
  }

  /**
   * Gets the action with the specified name
   *
   * @param name
   *          the name of the action
   *
   * @return the action or null
   */
  public UIAction getAction(String name) {
    final int index = indexForName(name);

    return (index == -1)
           ? null
           : getAction(index);
  }

  /**
   * Gets the navigation component that actually manages the navigation buttons
   *
   * @return the navigation component
   */
  public iNavigatorPanel getNavigatorPanel() {
    return panel;
  }

  /**
   * Gets the type of navigator panel
   *
   * @return the type of navigator panel
   */
  public PanelType getPanelType() {
    return panel.getPanelType();
  }

  /**
   * Gets the selected action
   *
   * @return the selected action
   */
  public UIAction getSelectedAction() {
    return panel.getSelectedAction();
  }

  /**
   * Returns the index of the selected button to toggle panel types
   *
   * @return the index of the selected button to toggle panel types
   */
  public int getSelectedIndex() {
    return panel.getSelectedIndex();
  }

  /**
   * Gets the name of the selected action
   *
   * @return the name of the selected action
   */
  public String getSelectedName() {
    UIAction a = panel.getSelectedAction();

    return (a == null)
           ? null
           : a.getActionName();
  }

  @Override
  public Object getSelection() {
    iActionComponent b = panel.getSelectedButton();

    return (b == null)
           ? null
           : b.getText();
  }

  @Override
  public String getValueAsString() {
    return getSelectedName();
  }

  /**
   * Gets whether the action at the specified index is enabled
   *
   * @param index
   *          the index
   * @return true if it is enabled; false if it is disabled
   */
  public boolean isActionEnabled(int index) {
    return panel.isActionEnabled(index);
  }

  /**
   * Gets whether the action at the specified index is visible
   *
   * @param index
   *          the index
   * @return true if visible; false to otherwise
   */
  public boolean isActionVisible(int index) {
    return panel.getButton(index).isVisible();
  }

  /**
   * Gets whether the action event is always fired for toggle type buttons. By
   * default the action is only fired if the button is not currently selected
   *
   * @return true if the event is always fired; false otherwise
   */
  public boolean isAlwaysFireAction() {
    return panel.isAlwaysFireAction();
  }

  /**
   * Gets whether to show only icons or toe show both icons and text
   *
   * @return the showText true if only icons will be shown; false otherwise
   */
  public boolean isShowIconsOnly() {
    return panel.isShowIconsOnly();
  }

  /**
   * Configures the navigator
   *
   * @param cfg
   *          the navigator's configuration
   */

  /**
   * Configures the navigator
   *
   * @param cfg
   *          the navigator's configuration
   */
  protected void configureEx(Navigator cfg) {
    final aNavigatorPanel np = createNavigatorPanel();

    panel = np;

    iNavigatorPanel.PanelType pt;

    switch(cfg.type.intValue()) {
      case Navigator.CType.toggle :
        pt = iNavigatorPanel.PanelType.TOGGLE;

        break;

      case Navigator.CType.option :
        pt = iNavigatorPanel.PanelType.OPTION;

        break;

      default :
        pt = iNavigatorPanel.PanelType.HIERARCHICAL;

        break;
    }

    np.setPanelType(pt);
    formComponent = dataComponent = np;

    if (cfg.showBackButton.booleanValue() && (np.isHiearchical())) {
      BorderPanelEx p = new BorderPanelEx(createBorderLayoutView(), np);

      p.setPadding(new UIInsets(0, 0, 0, ScreenUtils.PLATFORM_PIXELS_4));
      p.add(np.getBackButton(), Location.LEFT);
      p.add(np, Location.CENTER);
      formComponent = p;
    }

    configure(cfg, true, false, false, true);

    UIColor sc = UIColorHelper.getColor(cfg.separatorLineColor.getValue());

    if (sc != null) {
      np.setSeparatorLineColor(sc);
    }

    np.setShowIconsOnly(cfg.showIconsOnly.booleanValue());

    if (cfg.iconPosition.spot_valueWasSet()) {
      np.setIconPosition(IconPosition.valueOf(cfg.iconPosition.stringValue().toUpperCase(Locale.US)));
    }

    PaintBucket               pb;
    iPlatformComponentPainter cp;
    UIFont                    font = null;
    UIColor                   fg   = null;

    pb = UIColorHelper.getPaintBucket(this, cfg.getSelectionPainter());
    cp = (pb == null)
         ? null
         : pb.getComponentPainter(true);

    if (cp != null) {
      np.setSelectedPainter(cp);
    }

    if (pb != null) {
      font = pb.getFont();
      fg   = pb.getForegroundColor();
    }

    pb = UIColorHelper.getPaintBucket(this, cfg.getPressedPainter());
    cp = (pb == null)
         ? null
         : pb.getComponentPainter(true);

    if (cp != null) {
      np.setPressedPainter(cp);
    }

    if (pb != null) {
      if (fg == null) {
        fg = pb.getForegroundColor();
      }

      if (font == null) {
        font = pb.getFont();
      }
    }

    np.setSelectedFont(font);
    np.setSelectedForeground(fg);

    if (cfg.buttonsSameSize.booleanValue()) {
      np.setButtonsSameSize(true);
    }

    Margin m = cfg.getContentPadding();

    if (m != null) {
      np.setInsets(m.getInsets());
    }

    SPOTSet    set = cfg.actions;
    int        len = set.getCount();
    ActionItem item;

    for (int i = 0; i < len; i++) {
      item = (ActionItem) set.get(i);

      iActionComponent b = np.addButton(UIAction.createAction(this, item));

      if ("false".equals(item.spot_getAttribute("visible"))) {
        b.setVisible(false);
      }
    }

    int n = cfg.selectedIndex.intValue();

    if (n > -1) {
      np.setSelectedIndex(n);
    }
  }

  protected abstract Object createBorderLayoutView();

  /**
   * Create the navigator panel
   *
   * @return the navigator panel
   */
  protected abstract aNavigatorPanel createNavigatorPanel();

  static class BorderPanelEx extends BorderPanel {
    @Weak
    aNavigatorPanel np;

    BorderPanelEx(Object view, aNavigatorPanel np) {
      super(view);
      this.np = np;
    }

    @Override
    public void setBackground(UIColor bg) {
      np.setBackground(bg);
    }

    @Override
    public void setBorder(iPlatformBorder b) {
      np.setBorder(b);
    }

    @Override
    public void setComponentPainter(iPlatformComponentPainter cp) {
      np.setComponentPainter(cp);
    }

    @Override
    public void setFont(UIFont f) {
      np.setFont(f);
    }

    @Override
    public void setForeground(UIColor fg) {
      np.setForeground(fg);
    }

    @Override
    public iPlatformComponentPainter getComponentPainter() {
      return np.getComponentPainter();
    }

    @Override
    protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
      super.getMinimumSizeEx(size, maxWidth);
      size.width += ScreenUtils.PLATFORM_PIXELS_4;
    }

    @Override
    protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
      super.getPreferredSizeEx(size, maxWidth);
      size.width += ScreenUtils.PLATFORM_PIXELS_4;
    }
  }
}
