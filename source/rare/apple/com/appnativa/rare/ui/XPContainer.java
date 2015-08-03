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

import com.appnativa.rare.platform.apple.ui.view.ParentView;

public class XPContainer extends Container {
  protected boolean  cacheInvalidated;
  private UIInsets   insets;
  protected UIInsets margin;

  public XPContainer() {}

  public XPContainer(Object view) {
    super((ParentView) view);
  }

  @Override
  public void revalidate() {
    super.revalidate();
    cacheInvalidated = true;
  }

  public void setMargin(UIInsets margin) {
    this.margin = margin;
  }

  @Override
  public UIInsets getInsets(UIInsets in) {
    if (margin == null) {
      return super.getInsets(in);
    } else {
      return super.getInsets(in).addInsets(margin);
    }
  }

  public UIInsets getMargin() {
    return margin;
  }

  protected void populateMeasuredSizeCache(boolean populateMin) {
    populateMeasuredSizeCache(this, populateMin);
    cacheInvalidated = false;
  }

  protected UIInsets getInsetsEx() {
    iPlatformBorder b = getBorder();

    if ((b == null) && (margin == null)) {
      return null;
    }

    if (insets == null) {
      insets = new UIInsets();
    }

    if (b == null) {
      return insets.set(margin);
    }

    if (margin == null) {
      return b.getBorderInsets(insets);
    }

    return b.getBorderInsets(insets).addInsets(margin);
  }
}
