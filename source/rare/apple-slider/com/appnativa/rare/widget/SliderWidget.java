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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.platform.apple.ui.view.SliderView;
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.spot.Slider;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.SliderComponent;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.aSliderComponent;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iGradientPainter;
import com.appnativa.rare.ui.painter.iGradientPainter.Direction;
import com.appnativa.rare.viewer.iContainer;

/**
 *  A widget that lets the user graphically select a value by sliding
 *  a knob within a bounded interval.
 *  <p>
 *  The slider can show both major tick marks, and minor tick marks between the major ones.  The number of
 *  values between the tick marks is controlled with
 *  <code>setMajorTickSpacing</code> and <code>setMinorTickSpacing</code>.
 *  <p>
 *  Sliders can also print text labels at regular intervals (or at
 *  arbitrary locations) along the slider track.
 *
 *  @author Don DeCoteau
 */
public class SliderWidget extends aSliderWidget {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public SliderWidget(iContainer parent) {
    super(parent);
  }

  @Override
  protected iActionComponent createLabel() {
    return new ActionComponent(new LabelView());
  }

  @Override
  protected aSliderComponent createSliderAndComponents(Slider cfg) {
    SliderView slider = new SliderView();
    GridCell   gc     = cfg.getTrackPainter();

    if (gc != null) {
      PaintBucket pb = ColorUtils.configure(getViewer(), gc, null);

      if (!cfg.horizontal.booleanValue()) {
        iGradientPainter gp = pb.getGradientPainter();

        if (gp != null) {
          switch(gp.getGradientDirection()) {
            case VERTICAL_TOP :
              gp.setGradientDirection(Direction.HORIZONTAL_LEFT);

              break;

            case VERTICAL_BOTTOM :
              gp.setGradientDirection(Direction.HORIZONTAL_RIGHT);

              break;

            case HORIZONTAL_LEFT :
              gp.setGradientDirection(Direction.VERTICAL_TOP);

              break;

            case HORIZONTAL_RIGHT :
              gp.setGradientDirection(Direction.VERTICAL_BOTTOM);

              break;

            default :
              break;
          }
        }
      }

      slider.setTrackPainter(pb.getPainter());

      int w = gc.width.intValue();

      if (w > 0) {
        slider.setTrackPainterWidth(w);
      }
    }

    iPlatformIcon icon = getViewer().getIcon(cfg.thumbIcon);

    if (icon instanceof UIImageIcon) {
      slider.setThumbIcon((UIImageIcon) icon);
    }

    dataComponent = formComponent = new SliderComponent(slider);

    return (aSliderComponent) dataComponent;
  }

  protected static void registerForUse() {
    Platform.getAppContext().registerWidgetClass(Platform.getSPOTName(Slider.class), SliderWidget.class);
  }
}
