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

package com.appnativa.rare.platform.swing.ui.view;

import com.appnativa.rare.ui.UIDimension;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

public class SpacerView extends JComponent implements iView {
  protected int height;
  protected int width;

  public SpacerView() {
    super();
    setFocusable(false);
  }

  public SpacerView(int width, int height) {
    super();
    this.width  = width;
    this.height = height;
  }

  @Override
  public void setTransformEx(AffineTransform tx) {}

  @Override
  public Dimension getMinimumSize() {
    return new Dimension(0, 0);
  }

  @Override
  public void getMinimumSize(UIDimension size, int maxWidth) {
    size.width  = (width > -1)
                  ? width
                  : 0;
    size.height = (height > -1)
                  ? height
                  : 0;
  }

  @Override
  public Dimension getPreferredSize() {
    int w = (width > -1)
            ? width
            : 0;
    int h = (height > -1)
            ? height
            : 0;

    return new Dimension(w, h);
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    size.width  = (width > -1)
                  ? width
                  : Short.MAX_VALUE;
    size.height = (height > -1)
                  ? height
                  : Short.MIN_VALUE;
  }

  @Override
  public AffineTransform getTransformEx() {
    return null;
  }
}
