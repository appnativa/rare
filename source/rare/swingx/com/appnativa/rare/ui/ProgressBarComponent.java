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

package com.appnativa.rare.ui;

import com.appnativa.rare.platform.swing.ui.view.ProgressBarView;

import java.awt.Dimension;

import javax.swing.JProgressBar;

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
    if (size > 0) {
      pb.setPreferredSize(new Dimension(size, size));
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
