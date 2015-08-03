/*
 * @(#)ProgressBarComponent.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Dimension;

import javax.swing.JProgressBar;

import com.appnativa.rare.platform.swing.ui.view.ProgressBarView;

public class ProgressBarComponent extends Component implements iProgressBar {
  float        maximim;
  float        minimim;
  JProgressBar pb;

  public ProgressBarComponent() {
    this(new ProgressBarView());
  }

  public ProgressBarComponent(JProgressBar component) {
    super(component);
    pb = component;
    setMaximum(100);
    setMinimum(0);
  }

  @Override
  public void setIndeterminate(boolean indeterminate) {
    pb.setIndeterminate(indeterminate);
  }

  @Override
  public void setMaximum(int maximum) {
    pb.setMaximum(maximum);
  }

  @Override
  public void setMinimum(int minimum) {
    pb.setMinimum(minimum);
  }

  @Override
  public void setValue(int value) {
    pb.setValue(value);
  }

  @Override
  public iPlatformComponent getComponent() {
    return this;
  }

  @Override
  public void setGraphicSize(int size) {
    if(size>0) {
      pb.setPreferredSize(new Dimension(size,size));
    }
  }
  
  @Override
  public int getValue() {
    return pb.getValue();
  }

  @Override
  public boolean isIndeterminate() {
    return pb.isIndeterminate();
  }
}
