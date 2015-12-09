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

package com.appnativa.rare.ui.table;

import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
/*-[
#import "RAREAPView.h"
]-*/
import com.appnativa.rare.ui.UIColor;

public class MultiTableLayout extends ParentView implements iAppleLayoutManager {
  public MultiTableLayout() {
    super(createProxy());
    setLayoutManager(this);
  }

  @Override
  public boolean setBackgroundColorEx(UIColor bg) {
//    return super.setBackgroundColorEx(bg);
    return true;
  }

  @Override
  public void layout(ParentView view, float width, float height) {
    ((MultiTableContainer) component).layout(width, height);
  }

  static native Object createProxy()
  /*-[
    return [[RAREAPView alloc]init];
  ]-*/
  ;
}
