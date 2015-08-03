/*
 * @(#)aPlatformViewer.java
 *
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import javax.swing.JComponent;

import com.appnativa.rare.platform.swing.ui.view.FrameView;
import com.appnativa.rare.platform.swing.ui.view.PopupWindow;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.print.iPageSetup;

/**
 * Base class for viewers
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public abstract class aPlatformViewer extends aViewer {
  private boolean locked;

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the parent
   */
  public aPlatformViewer(iContainer parent) {
    super(parent);
  }

  @Override
  public iPageSetup createPageSetup() {
    return null;
  }


  public void setLocked(boolean locked) {
    JComponent c = formComponent.getView();

    if (c instanceof FrameView) {
      ((FrameView) c).setLocked(locked);
    }

    this.locked = locked;
  }

  public boolean isLocked() {
    return locked;
  }

  @Override
  protected iPopup createPopup(int width, int height) {
    iPopup popup = new PopupWindow();

    popup.setSize(width, height);
    return popup;
  }

}
