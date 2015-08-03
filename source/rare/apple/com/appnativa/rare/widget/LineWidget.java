/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.widget;

import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.platform.apple.ui.view.LineView;
import com.appnativa.rare.spot.Line;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Component;
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
    boolean   horiz = isHorizontal();
    LabelView tv    = new LabelView();

    tv.setText(text);
    tv.setMargin(2, 2, 2, 2);

    ActionComponent l = new ActionComponent(tv);

    if (icon != null) {
      l.setIcon(icon);
    }

    if (left) {
      l.setIconPosition(horiz
                        ? IconPosition.TRAILING
                        : IconPosition.BOTTOM_CENTER);
    } else {
      l.setIconPosition(horiz
                        ? IconPosition.LEADING
                        : IconPosition.TOP_CENTER);
    }

    return l;
  }

  @Override
  protected aLineHelper createLineHelperAndComponents(Line cfg) {
    LineView view = getAppContext().getComponentCreator().getLine(getViewer(), cfg);

    dataComponent = formComponent = new Component(view);

    return view.getLineHelper();
  }
}
