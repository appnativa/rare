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

package com.appnativa.rare.viewer;

import com.appnativa.rare.ExecutionHandler;
import com.appnativa.rare.iExecutionHandler;
import com.appnativa.rare.spot.Carousel;
import com.appnativa.rare.ui.CarouselContainer;
import com.appnativa.rare.ui.carousel.aCarouselPanel;
import com.appnativa.rare.ui.carousel.aCarouselPanel.DataType;

/**
 *
 * @author Don DeCcoteau
 */
public class CarouselViewer extends aCarouselViewer {
  @Override
  protected iExecutionHandler createExecutionHandler(int threads) {
    return new ExecutionHandler(threads);
  }

  @Override
  protected aCarouselPanel createComponents(Carousel cfg, DataType type) {
    CarouselContainer comp = new CarouselContainer(dataType, getAppContext().getActivity());

    formComponent = comp;
    dataComponent = comp.getCarouselPanel();

    return comp.getCarouselPanel();
  }
}
