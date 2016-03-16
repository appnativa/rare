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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.Margin;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iChangeable;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.util.SNumber;

/**
 * Base class for group-able buttons of <code>AbstractButton</code> type
 *
 * @author Don DeCoteau
 */
public abstract class aGroupableButton extends aPlatformWidget implements iActionable, iChangeable {

  /** whether the button's text should be shown */
  protected boolean showText = true;

  /** the group the button belongs to */
  protected ButtonGroup buttonGroup;

  /** the button's text */
  protected CharSequence buttonText;

  /** the name group the button belongs to */
  protected String groupName;

  /** the button's initial value */
  protected String initialValue;

  /** the submit value type */
  protected int     submitValueType;
  protected boolean allowDeselection = false;
  private boolean   initiallySelected;

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aGroupableButton(iContainer parent) {
    super(parent);
  }

  @Override
  public void setActionListener(iActionListener al) {
    addActionListener(al);
  }

  @Override
  public void addActionListener(iActionListener l) {
    iActionComponent b = (iActionComponent) dataComponent;

    b.addActionListener(l);
  }

  @Override
  public void addChangeListener(iChangeListener l) {
    iActionComponent b = (iActionComponent) dataComponent;

    b.addChangeListener(l);
  }

  @Override
  public void clearContents() {
    super.clearContents();
    setValue("");
  }

  /**
   * Clears the current selection of the button or the button's group
   */
  public void clearSelection() {
    if (buttonGroup != null) {
      buttonGroup.clearSelection();
    } else {
      this.setSelected(false);
    }
  }

  /**
   * Clicks the button
   */
  public void click() {
    ((iActionComponent) dataComponent).doClick();
  }

  @Override
  public void dispose() {
    if (buttonGroup != null) {
      buttonGroup.removeEx(this);
    }

    super.dispose();
    buttonGroup = null;
  }

  /**
   * Get the button group that the button belongs to
   *
   * @return the button group that the button belongs to (or null)
   */
  public ButtonGroup getButtonGroup() {
    return buttonGroup;
  }

  /**
   * Returns the name of the group the button belongs to
   *
   * @return the name of the group the button belongs to
   */
  public String getGroupName() {
    return groupName;
  }

  @Override
  public Object getHTTPFormValue() {
    switch(submitValueType) {
      case Button.CSubmitValue.value_property :
        return isSelected()
               ? getCustomProperty("value")
               : null;

      case Button.CSubmitValue.text :
        return isSelected()
               ? ((iActionComponent) dataComponent).getText()
               : null;

      case Button.CSubmitValue.state_selected_deselected :
        return isSelected()
               ? "selected"
               : "deselected";

      case Button.CSubmitValue.state_zero_one :
        return isSelected()
               ? "1"
               : "0";

      default :
        return isSelected()
               ? "on"
               : "off";
    }
  }

  @Override
  public iPlatformIcon getIcon() {
    return ((iActionComponent) dataComponent).getIcon();
  }

  /**
   * Returns the selected button in the group
   *
   * @return the selected button in the group
   */
  public aGroupableButton getSelectedButton() {
    if (buttonGroup == null) {
      return isSelected()
             ? this
             : null;
    }

    return buttonGroup.getSelectedButton();
  }

  /**
   * Gets the name of the selected button in the button group that this button
   * belongs to. If this button does not belong to a group then this button's
   * name is returned if it is currently selected.
   *
   * @return the name of the selected button
   */
  public String getSelectedButtonName() {
    aGroupableButton b = getSelectedButton();

    return (b == null)
           ? null
           : b.getName();
  }

  /**
   * Gets the selected button in the button group that this button belongs to.
   * If this button does not belong to a group then this button object is
   * returned if it is currently selected.
   */
  @Override
  public Object getSelection() {
    return getSelectedButtonName();
  }

  @Override
  public String getSelectionAsString() {
    return getSelectedButtonName();
  }

  @Override
  public Object getValue() {
    if ((widgetType == WidgetType.CheckBox) || (widgetType == WidgetType.RadioButton)) {
      return isSelected();
    }

    return buttonText;
  }

  @Override
  public Object getValue(iWidget context) {
    // TODO Auto-generated method stub
    return super.getValue(context);
  }

  @Override
  public String getValueAsString() {
    return (buttonText == null)
           ? ""
           : buttonText.toString();
  }

  @Override
  public boolean isSelected() {
    return ((iActionComponent) dataComponent).isSelected();
  }

  @Override
  public boolean isValidForSubmission(boolean showerror) {
    return isSelected();
  }

  @Override
  public void removeActionListener(iActionListener l) {
    iActionComponent b = (iActionComponent) dataComponent;

    if (b != null) {
      b.removeActionListener(l);
    }
  }

  @Override
  public void removeChangeListener(iChangeListener l) {
    iActionComponent b = (iActionComponent) dataComponent;

    b.removeChangeListener(l);
  }

  @Override
  public void reset() {
    super.reset();

    if (initialValue != null) {
      buttonText = expandString(initialValue, false);
      setValue(buttonText);
    }

    setSelected(initiallySelected);
  }

  public void selectionChanged(boolean selected) {
    if (buttonGroup != null) {
      buttonGroup.selectionChanged(this, selected);
    }
  }

  @Override
  public void setFromHTTPFormValue(Object value) {
    switch(submitValueType) {
      case Button.CSubmitValue.value_property :
        setCustomProperty("value", value);

        break;

      case Button.CSubmitValue.text :
        setValue((value == null)
                 ? ""
                 : value.toString());

        break;

      case Button.CSubmitValue.state_selected_deselected :
        setSelected("selected".equals(value));

        break;

      case Button.CSubmitValue.state_zero_one :
        setSelected("1".equals(value));

        break;

      default :
        setSelected("on".equals(value));
    }
  }

  /**
   * Sets the name of the group that the button belongs to. If the name is null
   * then the button is simply removed from it existing button group
   *
   * @param name
   *          the group name.
   */
  public void setGroupName(String name) {
    if (buttonGroup != null) {
      buttonGroup.remove(this);
      buttonGroup = null;
    }

    if (name != null) {
      String      gkey = "_buttonGroup." + name;
      ButtonGroup g    = (ButtonGroup) getFormViewer().getNamedItem(gkey);

      if (g == null) {
        g = new ButtonGroup(name);
        getFormViewer().registerNamedItem(gkey, g);
      }

      buttonGroup = g;
      g.add(this);
    }

    groupName = name;
  }

  @Override
  public void setIcon(iPlatformIcon icon) {
    ((iActionComponent) dataComponent).setIcon(icon);
  }

  /**
   * Convenience method for setting the value of a widget
   *
   * @param value
   *          the value
   */
  @Override
  public void setInnerHTML(String value) {
    if (value != null) {
      value = "<html>" + value + "</html>";
    }

    setValue(value);
  }

  @Override
  public void setSelected(boolean selected) {
    ((iActionComponent) dataComponent).setSelected(selected);
  }

  /**
   * Sets whether the button's text should be shown
   *
   * @param show
   *          true if the button's text should be shown; false otherwise
   */
  public void setShowText(boolean show) {
    if (show != showText) {
      showText = show;

      if (show) {
        ((iActionComponent) dataComponent).setText(buttonText);
      } else {
        ((iActionComponent) dataComponent).setText("");
      }
    }
  }

  /**
   * Sets the text for the button
   *
   * @param text
   *          the text for the button
   */
  public void setText(CharSequence text) {
    buttonText = (text == null)
                 ? ""
                 : text;

    if (showText) {
      ((iActionComponent) dataComponent).setText(buttonText);
    }
  }

  @Override
  public void setValue(Object value) {
    if (value instanceof Boolean) {
      setSelected((Boolean) value);
    } else {
      CharSequence s;

      if (value instanceof CharSequence) {
        s = (CharSequence) value;
      } else {
        s = (value == null)
            ? ""
            : toString(this, value, null);
      }

      buttonText = s;

      if (showText) {
        ((iActionComponent) dataComponent).setText(buttonText);
      }
    }
  }

  /**
   * Set the buttons icon and text using the values in the specified item
   *
   * @param item
   *          the item to get the icon an text from
   */
  public void setValueFromItem(RenderableDataItem item) {
    setValue(item.toString());
    setIcon(item.getIcon());
    setDisabledIcon(item.getDisabledIcon());
    setAlternateIcon(item.getAlternateIcon());
  }

  protected void configureEx(Button cfg) {
    allowDeselection = false;

    iActionComponent comp = createButton(cfg);

    dataComponent = formComponent = comp;
    configure(cfg, true, false, false, true);
    configureEx(comp, cfg);

    String name = cfg.name.getValue();

    if (name != null) {
      UIAction a = getAppContext().getAction(name);

      if (a != null) {
        comp.setAction(a);

        if (cfg.spot_getAttribute("onAction") != null) {
          comp.removeActionListener(a);
        }

        if (buttonText == null) {
          buttonText = comp.getText();
        }
      }
    }

    Margin m = cfg.getContentPadding();

    if (m != null) {
      UIInsets in = m.getInsets();

      if (!m.top.spot_valueWasSet()) {
        in.top = -1;
      }

      if (!m.left.spot_valueWasSet()) {
        in.left = -1;
      }

      if (!m.bottom.spot_valueWasSet()) {
        in.bottom = -1;
      }

      if (!m.right.spot_valueWasSet()) {
        in.right = -1;
      }

      comp.setMargin(in);
    }

    if (!showText) {
      comp.setText("");
    }

    if (buttonText != null) {
      setValue(buttonText);
    }

    comp.setSelected(initiallySelected = cfg.selected.booleanValue());
  }

  /**
   * Configures the button
   *
   * @param comp
   *          the button
   * @param cfg
   *          the configuration
   */
  protected void configureEx(iActionComponent comp, Button cfg) {
    submitValueType  = cfg.submitValue.intValue();
    allowDeselection = "true".equalsIgnoreCase(cfg.groupName.spot_getAttribute("allowDeselection"));

    iPlatformIcon icon = getIcon(cfg.icon);

    initialValue = cfg.value.getValue();

    if (initialValue != null) {
      buttonText = expandString(initialValue, false);
    }

    setGroupName(cfg.groupName.getValue());

    if ((cfg.textHAlignment.intValue() != Button.CTextHAlignment.auto)
        || (cfg.textVAlignment.intValue() != Button.CTextVAlignment.auto)) {
      PlatformHelper.setTextAlignment(comp, Utils.getHorizontalAlignment(cfg.textHAlignment.intValue()),
                                      Utils.getVerticalAlignment(cfg.textVAlignment.intValue()));
    }

    if (cfg.iconPosition.intValue() != Button.CIconPosition.auto) {
      comp.setIconPosition(Utils.getIconPosition(cfg.iconPosition.intValue()));
    }

    if (cfg.wordWrap.spot_valueWasSet() && getAppContext().okForOS(cfg.wordWrap)) {
      comp.setWordWrap(cfg.wordWrap.booleanValue());
    }

    if (cfg.showText.spot_valueWasSet()) {
      showText = cfg.showText.booleanValue();
    } else if (icon == null) {
      showText = true;
    }

    Margin m = cfg.getContentPadding();

    if (m != null) {
      comp.setMargin(m.getInsets());
    }

    if (icon != null) {
      setIcon(icon);
    }

    icon = getIcon(cfg.selectedIcon);

    if (icon != null) {
      comp.setSelectedIcon(icon);
    }

    icon = getIcon(cfg.pressedIcon);

    if (icon != null) {
      comp.setPressedIcon(icon);
    }

    icon = getIcon(cfg.disabledIcon);

    if (icon != null) {
      comp.setDisabledIcon(icon);
    }

    if (cfg.scaleIcon.booleanValue()) {
      float f = SNumber.floatValue(cfg.scaleIcon.spot_getAttribute("percent"));

      if (f > 1) {
        f = f / 100;
      }

      if (f <= 0) {
        f = 1;
      }

      comp.setScaleIcon(true, f);
    }

    if (!Platform.isTouchDevice()) {
      if(!cfg.focusPainted.spot_valueWasSet() || cfg.focusPainted.booleanValue()) {
        setFocusPainted(true);
      }
    }
  }

  protected void configurePainters(Button cfg) {
    PaintBucket pp = ColorUtils.configure(this, cfg.getPressedPainter(), null);
    PaintBucket sp = ColorUtils.configure(this, cfg.getSelectionPainter(), null);
    PaintBucket dp = ColorUtils.configure(this, cfg.getDisabledPainter(), null);

    if ((pp != null) || (sp != null) || (dp != null)) {
      if ((pp == null) && (sp == null)) {
        pp = PainterUtils.createButtonPaintHolder().pressedPainter;
      }

      PainterHolder p = new PainterHolder(null, sp, null, pp, dp);

      getComponentPainter(true, true).setPainterHolder(p);
    }
  }

  protected abstract iActionComponent createButton(Button cfg);

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if ((l != null) && l.isChangeEventEnabled()) {
      addChangeListener(l);
    }
  }

  protected boolean isTextView() {
    return true;
  }

  protected void postConfigure(Button cfg) {}
}
