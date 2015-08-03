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

package com.appnativa.rare.ui.painter;

import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import android.view.View;

import com.appnativa.rare.platform.android.ui.PainterDrawable;
import com.appnativa.rare.platform.android.ui.util.AndroidPaint;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;

public abstract class aUIPlatformPainter extends aUIPainter {
  protected AndroidPaint ipaint;

  public aUIPlatformPainter() {
    super();
  }

  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    paint(g.getView(), g.getCanvas(), UIScreen.snapToPosition(x), UIScreen.snapToPosition(y),
          UIScreen.snapToSize(width), UIScreen.snapToSize(height), orientation);
  }

  public iPlatformPaint getPaint(float width, float height) {
    Shader s = getShader(width, height);

    if (s == null) {
      return null;
    }

    if (ipaint == null) {
      ipaint = new AndroidPaint(s);
    } else {
      ipaint.setShader(s);
    }

    return ipaint;
  }

  public Shader getShader(float width, float height) {
    return null;
  }

  public Drawable getDrawable(View view) {
    return new PainterDrawable(view, this);
  }
}
