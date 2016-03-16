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

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.painter.UIComponentPainter;

public abstract class aPlatformTabPainter extends aTabPainter {
  @Override
  protected iTabLabel createNewRenderer(UIAction a) {
    return new TabLabel(a);
  }
  
  public void updatePaintersModCount() {
    if(normalComponentPainter!=null) {
      normalComponentPainter.updateModCount();
    }
    else if(tabPainter!=null) {
      UIComponentPainter.updateBucketModCount(tabPainter);
    }
    if(selectedComponentPainter!=null) {
      selectedComponentPainter.updateModCount();
    }
    else if(selectedPainter!=null) {
      UIComponentPainter.updateBucketModCount(selectedPainter);
    }
  }
}
