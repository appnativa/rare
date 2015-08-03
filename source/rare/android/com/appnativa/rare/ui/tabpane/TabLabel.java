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

import android.content.Context;

import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIDimension;

public class TabLabel extends ActionComponent implements iTabLabel {
  public TabLabel(Context context) {
    super(new LabelView(context));
  }

  public TabLabel(Context context, UIAction a) {
    this(context);
    setAction(a);
  }

  public void setIsSelectedTab(boolean selected) {
    setSelected(selected);
    invalidate();
  }

  public void setMinHeight(int minTabHeight) {}

  public void setText(CharSequence buttonText) {
    super.setText(buttonText);
  }

  @Override
  public UIDimension getPreferredSize(UIDimension size) {
    size       = super.getPreferredSize(size);
    size.width += ScreenUtils.PLATFORM_PIXELS_4;

    return size;
  }
}
