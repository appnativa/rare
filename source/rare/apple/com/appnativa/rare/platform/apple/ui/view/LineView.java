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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.aLineHelper;

/*-[
 #import "RAREAPView.h"
 ]-*/
public class LineView extends View {
  private LineHelper lineHelper;

  public LineView() {
    super(createProxy());
    lineHelper = new LineHelper(true);
    super.setPaintHandlerEnabled(true);
  }

  @Override
  public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
    super.paintBackground(g, v, rect);
    lineHelper.paint(g, rect.x, rect.y, rect.width, rect.height);
  }

  public aLineHelper getLineHelper() {
    return lineHelper;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    size.width = size.height = UIScreen.snapToSize(lineHelper.getThickness());
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {
    float th = lineHelper.getThickness();

    if (lineHelper.isStandardLine()) {
      th *= 2;
    }

    size.width = size.height = UIScreen.snapToSize(th);
  }

  @Override
  public boolean isMouseTransparent() {
    return (mouseListener == null) || ((mouseMotionListener == null) &&!mouseGestureListenerAdded);
  }

  @Override
  protected void disposeEx() {
    super.disposeEx();
    lineHelper = null;
  }

  @Override
  protected void setEnabledEx(boolean b) {}

  private native static Object createProxy()
  /*-[
    return [[RAREAPView alloc]init];
  ]-*/
  ;

  class LineHelper extends aLineHelper {
    public LineHelper(boolean horizontal) {
      super(horizontal);
    }

    @Override
    protected void thicknessRecalculated() {}
  }
}
