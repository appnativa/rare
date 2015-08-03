/*
 * @(#)SizingAnimation.java
 * 
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.effects;

import com.appnativa.rare.ui.RenderType;


public class SizeAnimation extends aSizeAnimation {
  public SizeAnimation() {
    super();
  }

  public SizeAnimation(boolean horizontal) {
    super(horizontal);
  }

  public SizeAnimation(boolean horizontal, boolean diagonal, RenderType anchor) {
    super(horizontal, diagonal, anchor);
  }
}
