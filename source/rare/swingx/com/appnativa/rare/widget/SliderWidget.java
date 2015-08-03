/*
 * @(#)SliderWidget.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.widget;

import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.platform.swing.ui.view.SliderView;
import com.appnativa.rare.spot.Slider;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.SliderComponent;
import com.appnativa.rare.ui.aSliderComponent;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.viewer.iContainer;

/**
 *  A widget that lets the user graphically select a value by sliding
 *  a knob within a bounded interval.
 *  <p>
 *  The slider can show both major tick marks, and minor tick marks between the major ones.  The number of
 *  values between the tick marks is controlled with
 *  <code>setMajorTickSpacing</code> and <code>setMinorTickSpacing</code>.
 *  <p>
 *  Sliders can also print text labels at regular intervals (or at
 *  arbitrary locations) along the slider track.
 *
 *  @author Don DeCoteau
 */
public class SliderWidget extends aSliderWidget {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public SliderWidget(iContainer parent) {
    super(parent);
  }

  @Override
  protected iActionComponent createLabel() {
    return new ActionComponent(new LabelView());
  }

  @Override
  protected aSliderComponent createSliderAndComponents(Slider cfg) {
    SliderView slider = getAppContext().getComponentCreator().getSlider(getViewer(), cfg);

    dataComponent = formComponent = new SliderComponent(slider);

    return (aSliderComponent) dataComponent;
  }
}
