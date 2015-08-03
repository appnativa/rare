/*
 * @(#)SliderComponent.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import com.appnativa.rare.platform.swing.ui.view.SliderView;

public class SliderComponent extends aSliderComponent {
  public SliderComponent() {
    this(new SliderView());
  }

  public SliderComponent(SliderView view) {
    super(view);
    slider = view;
    ((SliderView) slider).setChangeListener(this);
  }
}
