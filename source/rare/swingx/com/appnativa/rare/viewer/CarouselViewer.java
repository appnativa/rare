/*
 * @(#)CoverFlowViewer.java   2012-04-30
 * 
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
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

  public CarouselViewer() {
    super();
  }

  public CarouselViewer(iContainer parent) {
    super(parent);
  }

  @Override
  protected iExecutionHandler createExecutionHandler(int threads) {
    return new ExecutionHandler(threads);
  }

  @Override
  protected aCarouselPanel createComponents(Carousel cfg, DataType type) {
    CarouselContainer comp = new CarouselContainer(dataType);
    formComponent = comp;
    dataComponent = comp.getCarouselPanel();
    return comp.getCarouselPanel();
  }
}