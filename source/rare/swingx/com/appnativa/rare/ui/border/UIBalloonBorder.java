/*
 * @(#)UIBalloonBorder.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;

/**
 *
 *  @author Don DeCoteau
 */
public class UIBalloonBorder extends aUIBalloonBorder {
  public UIBalloonBorder(UIColor color) {
    super(color);
  }

  public UIBalloonBorder(UIColor color, float thickness) {
    super(color, thickness);
  }

  public UIBalloonBorder(UIColor color, float thickness, float arc) {
    super(color, thickness, arc);
  }

  public UIBalloonBorder(UIColor color, float thickness, float arc, PeakLocation pl) {
    super(color, thickness, arc, pl);
  }

  public UIBalloonBorder(UIColor color, float thickness, float arc, PeakLocation pl, float peakSize) {
    super(color, thickness, arc, pl, peakSize);
  }
}
