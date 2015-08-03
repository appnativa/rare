/*
 * @(#)LabelWidget.java   2010-10-06
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.widget;

import com.appnativa.rare.spot.Label;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.viewer.iContainer;

/**
 * A widgets that provides a means displaying a small amount of text, an image or both.
 *
 * @author Don DeCoteau
 */
public class LabelWidget extends aLabelWidget {

  /**
   * Constructs a new instance.
   *
   * @param parent the widget's parent
   */
  public LabelWidget(iContainer parent) {
    super(parent);
  }

  

  @Override
  protected iActionComponent createActionComponent(Label cfg) {
    return new ActionComponent(getAppContext().getComponentCreator().getLabel(getViewer(), cfg));
  }
}
