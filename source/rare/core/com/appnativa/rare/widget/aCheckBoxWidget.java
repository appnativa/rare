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
import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.CheckBox;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.viewer.iContainer;

public abstract class aCheckBoxWidget extends aGroupableButton {

  /**
   * The set of check box states
   */
  public static enum State {

    /** the checkbox is selected */
    SELECTED,

    /** the checkbox is neither selected or unselected */
    INDETERMINATE,

    /** the checkbox is unselected */
    DESELECTED
  }

  /** the initial state of the checkbox */
  protected State initialState = State.DESELECTED;

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public aCheckBoxWidget(iContainer parent) {
    super(parent);
    widgetType = WidgetType.CheckBox;
  }

  @Override
  public void configure(Widget cfg) {
    configureEx((CheckBox) cfg);
    configurePainters((Button) cfg);
    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public void dispose() {
    super.dispose();
    initialState = null;
  }

  @Override
  public void reset() {
    setState(initialState);
  }

  /**
   * Sets the checked state of the checkbox
   *
   * @param checked true the check the checkbox; false otherwise
   */
  public void setChecked(boolean checked) {
    setState(checked
             ? State.SELECTED
             : State.DESELECTED);
  }

  /**
   * Set the checkbox to its indeterminate state
   */
  public void setIndeterminate() {
    setState(State.INDETERMINATE);
  }

  /**
   * Returns whether the checkbox is in an indeterminate state
   *
   * @return true if the checkbox is in an indeterminate state; false otherwise
   */
  public boolean isIndeterminate() {
    return getState() == State.INDETERMINATE;
  }

  /**
   * Sets the state of the checkbox
   *
   * @param state the state
   */
  public void setState(int state) {
    State newstate = null;

    switch(state) {
      case CheckBox.CInitialState.selected :
        newstate = State.SELECTED;

        break;

      case CheckBox.CInitialState.indeterminate :
        newstate = State.INDETERMINATE;

        break;

      case CheckBox.CInitialState.deselected :
        newstate = State.DESELECTED;

        break;
    }

    if (newstate != null) {
      setState(newstate);
    }
  }

  /**
   * Sets the state of the checkbox
   *
   * @param state the state
   */
  public abstract void setState(State state);

  @Override
  public void setValue(Object value) {
    if (value == null) {
      setChecked(false);
    } else if (value instanceof Boolean) {
      setChecked((Boolean) value);
    } else {
      super.setValue(value);
    }
  }

  @Override
  public Object getHTTPFormValue() {
    if (isIndeterminate()) {
      if (submitValueType == Button.CSubmitValue.state_zero_one) {
        return "2";
      }

      return "indeterminate";
    }

    return super.getHTTPFormValue();
  }

  @Override
  public Object getValue() {
    if (isIndeterminate()) {
      return null;
    }

    return isSelected();
  }
  
  /**
   * Gets the current state of the checkbox
   *
   * @return the current state of the checkbox
   */
  public abstract State getState();

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
        if ("indeterminate".equals(value)) {
          setIndeterminate();
        } else {
          setSelected("selected".equals(value));
        }

        break;

      case Button.CSubmitValue.state_zero_one :
        if ("2".equals(value)) {
          setIndeterminate();
        } else {
          setSelected("1".equals(value));
        }

        break;

      default :
        if ("indeterminate".equals(value)) {
          setIndeterminate();
        } else {
          setSelected("on".equals(value));
        }

        break;
    }
  }

  /**
   * Gets the checked state of the checkbox
   *
   * @return true if the checkbox is checked; false otherwise
   */
  public boolean isChecked() {
    return isSelected();
  }
 
  /**
   * Returns whether the check box is selected by default
   *
   * @return true if the check box is selected by default; false otherwise
   */
  public boolean isDefaultChecked() {
    return initialState != State.DESELECTED;
  }

  /**
   * Creates a new checkbox widget
   *
   * @param parent the parent
   * @param cfg the configuration
   *
   * @return the checkbox widget
   */
  public static CheckBoxWidget create(iContainer parent, CheckBox cfg) {
    CheckBoxWidget widget = new CheckBoxWidget(parent);

    widget.configure(cfg);

    return widget;
  }

  @Override
  protected void postConfigure(Button vcfg) {
    CheckBox cfg = (CheckBox) vcfg;

    initialState = cfg.selected.booleanValue()
                   ? State.SELECTED
                   : State.DESELECTED;

    if (cfg.initialState.spot_valueWasSet()) {
      switch(cfg.initialState.intValue()) {
        case CheckBox.CInitialState.selected :
          initialState = State.SELECTED;

          break;

        case CheckBox.CInitialState.indeterminate :
          initialState = State.INDETERMINATE;

          break;

        default :
          initialState = State.DESELECTED;

          break;
      }
    }

    setState(initialState);
  }
}
