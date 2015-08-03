/*
 * @(#)LineWidget.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.widget;

import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.platform.swing.ui.view.LineView;
import com.appnativa.rare.spot.Line;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.aLineHelper;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.viewer.iContainer;

/**
 * A widget that displays a horizontal or vertical line and, can optionally have
 * a label at either ends of the line.
 *
 * @author Don DeCoteau
 */
public class LineWidget extends aLineWidget {

  /**
   * Constructs a new instance.
   *
   * @param parent
   *          the widget's parent
   */
  public LineWidget(iContainer parent) {
    super(parent);
  }

  @Override
  public void setHorizontal(boolean horizontal) {
    if (formComponent instanceof BorderPanel) {
      ((BorderPanel) formComponent).setHorizontal(horizontal);
    }

    super.setHorizontal(horizontal);
  }

  @Override
  protected iActionComponent createLabel(String text, iPlatformIcon icon, boolean left) {
    boolean         horiz = isHorizontal();
    LabelView       tv    = new LabelView();
    ActionComponent l     = new ActionComponent(tv);

    tv.setText(text);
    l.setMargin(new UIInsets(2));

    if (icon != null) {
      l.setIcon(icon);
    }

    if (left) {
      if (horiz) {
        l.setAlignment(HorizontalAlign.TRAILING, VerticalAlign.CENTER);
      } else {
        l.setAlignment(HorizontalAlign.CENTER, VerticalAlign.BOTTOM);
      }

      l.setIconPosition(horiz
                        ? IconPosition.TRAILING
                        : IconPosition.BOTTOM_CENTER);
    } else {
      if (horiz) {
        l.setAlignment(HorizontalAlign.LEADING, VerticalAlign.CENTER);
      } else {
        l.setAlignment(HorizontalAlign.CENTER, VerticalAlign.TOP);
      }

      l.setIconPosition(horiz
                        ? IconPosition.LEADING
                        : IconPosition.TOP_CENTER);
    }

    return l;
  }

  @Override
  protected aLineHelper createLineHelperAndComponents(Line cfg) {
    LineView view = new LineView();

    dataComponent = formComponent = new Component(view);

    return view.getLineHelper();
  }
}
