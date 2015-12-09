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
import com.appnativa.rare.spot.RadioButton;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.viewer.iContainer;

/**
 * A widget that allows a user to select one, and only one option from a set of alternatives.
 *
 * @author Don DeCoteau
 */
public class RadioButtonWidget extends aGroupableButton {

  /** if the radio button was initially selected */
  protected boolean initiallySelected;

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public RadioButtonWidget(iContainer parent) {
    super(parent);
    widgetType = WidgetType.RadioButton;
  }

  @Override
  public void configure(Widget cfg) {
    configureEx((RadioButton) cfg);
    configurePainters((Button) cfg);
    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public Object getHTTPFormValue() {
    return isSelected()
           ? super.getHTTPFormValue()
           : null;
  }

  @Override
  public boolean isValidForSubmission(boolean showerror) {
    return isSelected();
  }

  @Override
  public void reset() {
    setSelected(initiallySelected);
  }

  @Override
  protected ActionComponent createButton(Button cfg) {
    return new ActionComponent(getAppContext().getComponentCreator().getRadioButton(getViewer(), (RadioButton) cfg));
  }
}
