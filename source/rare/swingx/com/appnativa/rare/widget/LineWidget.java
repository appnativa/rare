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

import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.platform.swing.ui.view.LineView;
import com.appnativa.rare.spot.Line;
import com.appnativa.rare.ui.ActionComponent;
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
