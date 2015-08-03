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

package com.appnativa.rare.ui.effects;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.appnativa.rare.ui.UIImage;

/**
 *
 * @author Don DeCoteau
 */
public class AnimationImage extends UIImage {
  public AnimationImage(AnimationDrawable drawable) {
    this(drawable, 0);
  }

  public AnimationImage(AnimationDrawable drawable, int resID) {}

  public void setAnimation(AnimationDrawable drawable, int resID) {
    this.drawable = drawable;
    this.resID    = resID;
  }

  @Override
  public Bitmap getBitmap() {
    Drawable d = ((AnimationDrawable) drawable).getCurrent();

    if (d == null) {
      d = ((AnimationDrawable) drawable).getFrame(0);
    }

    return (d instanceof BitmapDrawable)
           ? ((BitmapDrawable) d).getBitmap()
           : null;
  }
}
