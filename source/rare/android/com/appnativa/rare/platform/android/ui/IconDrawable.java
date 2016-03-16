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

package com.appnativa.rare.platform.android.ui;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import android.view.View;

import android.widget.TextView;

import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;

/**
 *
 * @author Don DeCoteau
 */
public class IconDrawable implements iPlatformIcon {
  private Drawable drawable;

  public IconDrawable() {}

  public IconDrawable(Drawable drawable) {
    this.drawable = drawable;
  }

  /**
   * @param drawable the drawable to set
   */
  public void setDrawable(Drawable drawable) {
    this.drawable = drawable;
  }

  public iPlatformIcon getDisabledVersion() {
    return this;
  }

  /**
   * @return the drawable
   */
  public Drawable getDrawable(View view) {
    return drawable;
  }

  public int getIconHeight() {
    return getDrawable(null).getMinimumHeight();
  }

  public int getIconWidth() {
    return getDrawable(null).getMinimumWidth();
  }

  public void setState(int[] state) {
    drawable.setState(state);
  }

  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    View v = g.getView();

    if ((v instanceof TextView) && drawable.isStateful()) {
      drawable.setState(((TextView) v).getDrawableState());
    }

    paint(g.getCanvas(), UIScreen.snapToPosition(x), UIScreen.snapToPosition(y), UIScreen.snapToSize(width),
          UIScreen.snapToSize(height));
  }

  public void paint(Canvas g, float x, float y, float width, float height) {
    drawable.setBounds((int) x, (int) y, (int) (x + width), (int) (y + height));
    drawable.draw(g);
  }

  @Override
  public Drawable createDrawable(View view) {
    return drawable;
  }
}
