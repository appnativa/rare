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

package com.appnativa.rare.ui.border;

import android.app.Activity;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.util.ImageHelper;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.util.CharArray;

public class UIDropShadowBorder extends aUIDropShadowBorder {
  private NinePatchDrawable drawable;

  public UIDropShadowBorder() {
    super();
  }

  public UIDropShadowBorder(boolean showLeftShadow) {
    super(showLeftShadow);
  }

  public UIDropShadowBorder(UIColor shadowColor, float shadowSize) {
    super(shadowColor, shadowSize);
  }

  public UIDropShadowBorder(UIColor shadowColor, float shadowSize, float shadowOpacity, float cornerSize,
                            boolean showTopShadow, boolean showLeftShadow, boolean showBottomShadow,
                            boolean showRightShadow) {
    super(shadowColor, shadowSize, shadowOpacity, cornerSize, showTopShadow, showLeftShadow, showBottomShadow,
          showRightShadow);
  }

  public boolean getPadding(Rect padding) {
    float top    = showTopShadow
                   ? shadowSize
                   : 0;
    float left   = showLeftShadow
                   ? shadowSize
                   : 0;
    float bottom = showBottomShadow
                   ? shadowSize
                   : 0;
    float right  = showRightShadow
                   ? shadowSize
                   : 0;

    padding.set((int) left, (int) top, (int) right, (int) bottom);

    return true;
  }

  protected Drawable getDrawableEx() {
    return ((UIImage) ninePatch.getImage()).getDrawable();
  }

  @SuppressWarnings("resource")
  protected UIImage getImage(String name) {
    CharArray ca = new CharArray(name);

    ca.toLowerCase();
    ca.replace('.', '_');

    Activity a  = AppContext.getContext().getActivity();
    int      id = AppContext.getResourceId(a, "drawable", ca.toString());

    return getImageEx(id);
  }

  protected UIImage getImageEx(int id) {
    Context ctx  = AppContext.getContext().getActivity();
    Rect    rect = new Rect();

    getPadding(rect);

    BitmapFactory.Options opts = new BitmapFactory.Options();

    opts.inPreferredConfig = Bitmap.Config.ARGB_8888;

    Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), id, opts);

    if (shadowColor.getColor() != UIColor.BLACK.getColor()) {
      drawable = ImageHelper.createNinePatchDrawable(ctx, bmp, rect, shadowColor, UIColor.BLACK);
    } else {
      drawable = ImageHelper.createNinePatchDrawable(ctx, bmp, rect);
    }

    drawable.setDither(true);

    return new UIImage(drawable, id);
  }
}
