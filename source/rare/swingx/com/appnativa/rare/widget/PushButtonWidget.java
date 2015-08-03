/*
 * @(#)PushButtonWidget.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.widget;

import javax.swing.AbstractButton;

import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.viewer.ToolBarViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;

/**
 *  A widget that provides the user a way to trigger some predefined action
 *
 *  @author Don DeCoteau
 */
public class PushButtonWidget extends aPushButtonWidget {

  /**
   *  Constructs a new instance
   *
   *  @param parent the widget's parent
   */
  public PushButtonWidget(iContainer parent) {
    super(parent);
  }


	@Override
  protected iActionComponent createButton(Button cfg) {
    AbstractButton v;
    iViewer    viewer = getViewer();
    PushButton pb     = (PushButton) cfg;

    if ((viewer instanceof ToolBarViewer) &&!pb.buttonStyle.spot_valueWasSet()) {
      v = getAppContext().getComponentCreator().getToolbarButton(viewer, pb);
    } else {
      v = getAppContext().getComponentCreator().getButton(viewer, pb);
    }

    return new ActionComponent(v);
  }

  @Override
  protected void updatePopupComponent(iPopup popup) {}
}
