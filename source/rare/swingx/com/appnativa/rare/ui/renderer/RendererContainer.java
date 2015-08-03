/*
 * @(#)RendererContainer.java
 *
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.renderer;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.view.ListRowContainer;
import com.appnativa.rare.platform.swing.ui.view.ListView;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

public class RendererContainer extends aRendererContainer {
  public RendererContainer(ListView parent, iPlatformRenderingComponent rc) {
    super(rc);

    ListRowContainer lc = new ListRowContainer(parent);

    setView(lc);
  }

  public void setRenderingComponent(iPlatformRenderingComponent rc) {
    if (this.renderingComponent != rc) {
      this.renderingComponent = rc;
      ((ListRowContainer) view).setContentView(rc.getComponent().getView());
    }
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
    setComponentPainter(null);
    getView().setBackground(null);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    setComponentPainterEx(cp);
  }
 }
