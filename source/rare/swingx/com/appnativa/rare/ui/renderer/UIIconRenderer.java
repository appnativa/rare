/*
 * @(#)UIIconRenderer.java   2012-01-10
 * 
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.renderer;

import javax.swing.JLabel;

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformRenderingComponent;

/**
 * A label renderer that only displays an icon
 *
 * @author Don DeCoteau
 */
public class UIIconRenderer extends UILabelRenderer {
  public UIIconRenderer() {
    super();
  }

  public UIIconRenderer(JLabel label) {
    super(label);
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    return Renderers.setupNewCopy(this, new UIIconRenderer());
  }

  /**
   * Overridden to do nothing
   *
   * @param text the text to set
   */
  @Override
  public void setText(CharSequence text) {}

  public iPlatformComponent getComponent(Object value, RenderableDataItem item) {
    setText("");

    return this;
  }
}
