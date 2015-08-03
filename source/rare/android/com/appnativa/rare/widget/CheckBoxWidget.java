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

import com.appnativa.rare.platform.android.ui.view.CheckBoxView;
import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.CheckBox;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.viewer.iContainer;

/**
 * A widget that allows a user to select one or more items utilizing checking / unchecking metaphor.
 *
 * @author Don DeCoteau
 */
public class CheckBoxWidget extends aCheckBoxWidget {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public CheckBoxWidget(iContainer parent) {
    super(parent);
  }

  /**
   * Sets the state of the checkbox
   *
   * @param state the state
   */
  public void setState(State state) {
    if ((state == State.INDETERMINATE) && (dataComponent.getView() instanceof CheckBoxView)) {
      ((CheckBoxView) dataComponent.getView()).setIndeterminate();
    } else {
      setSelected(state != State.DESELECTED);
    }
  }

  /**
   * Gets the current state of the checkbox
   *
   * @return the current state of the checkbox
   */
  public State getState() {
    if ((dataComponent.getView() instanceof CheckBoxView)
        && ((CheckBoxView) dataComponent.getView()).isIndeterminate()) {
      return State.INDETERMINATE;
    } else {
      return isSelected()
             ? State.SELECTED
             : State.DESELECTED;
    }
  }

  @Override
  protected ActionComponent createButton(Button cfg) {
    return new ActionComponent(getAppContext().getComponentCreator().getCheckBox(getViewer(), (CheckBox) cfg));
  }
}
