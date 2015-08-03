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

import android.graphics.drawable.Drawable;

import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.util.PainterUtils;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class UIProperties extends aUIProperties {
  public UIProperties() {
    super();
  }

  public iBackgroundPainter getBackgroundPainter(String key) {
    Object o = map.get(key);

    if (o instanceof iBackgroundPainter) {
      return (iBackgroundPainter) o;
    }

    Drawable d = getDrawable(key);

    return (d == null)
           ? null
           : new UISimpleBackgroundPainter(d, 0);
  }

  public Drawable getDrawable(String key) {
    Object o = map.get(key);

    if (o instanceof Drawable) {
      return (Drawable) o;
    }

    if (o instanceof UIColor) {
      return ((UIColor) o).getDrawable();
    }

    if (o instanceof iPlatformIcon) {
      return ((iPlatformIcon) o).getDrawable(null);
    }

    if (o instanceof iPlatformPainter) {
      return ((iPlatformPainter) o).getDrawable(null);
    }

    if (o instanceof PaintBucket) {
      return PainterUtils.getDrawable((PaintBucket) o, null);
    }

    if (o instanceof iPlatformBorder) {
      return ((iPlatformBorder) o).getDrawable(null);
    }

    if (o instanceof String) {
      return AppContext.getContext().getResourceAsDrawable((String) o);
    }

    return null;
  }

  public iPlatformPainter getPainter(String key) {
    Object o = map.get(key);

    if (o instanceof iPlatformPainter) {
      return (iPlatformPainter) o;
    }

    Drawable d = getDrawable(key);

    return (d == null)
           ? null
           : new UISimpleBackgroundPainter(d, 0);
  }
}
