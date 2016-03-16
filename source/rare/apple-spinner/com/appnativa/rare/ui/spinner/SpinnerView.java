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

package com.appnativa.rare.ui.spinner;

import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.aSpinnerComponent;
import com.appnativa.rare.ui.painter.UIComponentPainter;

public class SpinnerView extends ParentView implements iAppleLayoutManager {
  public SpinnerView() {
    super(createAPView());
    setLayoutManager(this);
    setPaintHandlerEnabled(true);
  }

  @Override
  public void layout(ParentView view, float width, float height) {
    ((aSpinnerComponent) view.getComponent()).layout(width, height);
  }

  @Override
  public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
    super.paintBackground(g, v, rect);

    aSpinnerComponent sp = (aSpinnerComponent) component;

    sp.paintButtons(g, rect);
  }
  @Override
  public void updateChildrenForColorChange() {
    super.updateChildrenForColorChange();
    aSpinnerComponent sp = (aSpinnerComponent) component;
    UIComponentPainter.updatePainterHolderModCount(sp.getButtonPainterHolder());
  }
}
