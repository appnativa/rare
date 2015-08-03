package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;

public class SharedLineBorder extends aSharedLineBorder {

  public SharedLineBorder(UIColor color) {
    super(color);
  }

  public SharedLineBorder(UIColor color, float thickness) {
    super(color, thickness);
  }

  public SharedLineBorder(UIColor color, float thickness, boolean roundedCorners) {
    super(color, thickness, roundedCorners);
  }

  public SharedLineBorder(UIColor color, float thickness, int arc) {
    super(color, thickness, arc);
  }

  public SharedLineBorder(UIColor color, float thickness, int arcWidth, int arcHeight) {
    super(color, thickness, arcWidth, arcHeight);
  }

}
