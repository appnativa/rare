/*
 * @(#)UICompositeRenderer.java   2012-02-08
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.renderer;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformRenderingComponent;

public class UICompositeRenderer extends aCompositeRenderer {
  public UICompositeRenderer() {
    super();
  }

  public UICompositeRenderer(iPlatformRenderingComponent rc) {
    super(rc);
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    iPlatformRenderingComponent rc = (renderingComponent == null) ? new UILabelRenderer() : renderingComponent.newCopy();
    UICompositeRenderer cr = new UICompositeRenderer(rc);

    cr.setIconPosition(iconPosition);
    cr.backgroundSurface = backgroundSurface;
    Renderers.setupNewCopy(this, cr);

    return cr;
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    super.getMinimumSizeEx(size);
    Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);
    if (i != null && i.intValue() > size.height) {
      size.height = i.intValue();
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    super.getPreferredSizeEx(size, maxWidth);
    Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);
    if (i != null && i.intValue() > size.height) {
      size.height = i.intValue();
    }
  }

  @Override
  public void prepareForReuse(int row, int column) {
    iconLabel.prepareForReuse(row, column);
    setComponentPainter(null);
    getView().setBackground(null);
  }

}
