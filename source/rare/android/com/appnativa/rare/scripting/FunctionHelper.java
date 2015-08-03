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

package com.appnativa.rare.scripting;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import android.os.Build.VERSION;

import android.view.Gravity;
import android.view.View;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.IconDrawable;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.ui.UIBorderIcon;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.effects.iAnimator;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.util.SNumber;

import java.lang.reflect.Constructor;

public class FunctionHelper extends aFunctionHelper {
  public static iPlatformIcon createColorIcon(final UIColor color, final int width, final int height,
          final iPlatformBorder border) {
    Drawable d = new PaintDrawable(color.getColor());

    d.setBounds(0, 0, width, height);

    if (border != null) {
      d = new LayerDrawable(new Drawable[] { border.getDrawable(null), d });
    }

    return new IconDrawable(d);
  }

  public static iPlatformIcon createEmptyIcon(final int width, final int height, final UIColor borderColor) {
    iPlatformIcon ic = new NullDrawable(width, height);

    if (borderColor == null) {
      return ic;
    }

    return new UIBorderIcon(new UILineBorder(borderColor), ic);
  }

  public static UIImage createTextImage(String text, UIFont font, UIColor fg, UIColor bg, iPlatformBorder b,
          boolean square) {
    UILabelRenderer label = new UILabelRenderer(AppContext.getAndroidContext());

    if (font != null) {
      label.setFont(font);
    }

    if (fg != null) {
      label.setForeground(fg);
    }

    if (bg != null) {
      label.setBackground(bg);
    }

    if (b != null) {
      label.setBorder(b);
    }

    UIDimension d = label.getPreferredSize();

    if (square) {
      if (d.width > d.height) {
        d.height = d.width;
      } else {
        d.width = d.height;
      }

      label.setGravity(Gravity.CENTER);
    }

    label.setBounds(0, 0, d.width, d.height);

    return ImageUtils.createImage((View) label.getComponent(text, null).getView());
  }

  public static iAnimator createValueAnimator(double start, double end, double inc, boolean accelerate,
          boolean decelerate, iAnimatorValueListener l) {
    if (VERSION.SDK_INT > 10) {
      try {
        Class       cls = Class.forName("com.appnativa.rare.platform.android.ui.effects.ValueAnimatorEx");
        Constructor c   = cls.getConstructor(double.class, double.class, double.class, boolean.class, boolean.class,
                            iAnimatorValueListener.class);

        return (iAnimator) c.newInstance(start, end, inc, accelerate, decelerate, l);
      } catch(Exception e) {}
    }

    return null;
  }

  public static String getLocation() {
    try {
      LocationManager lm =
        (LocationManager) AppContext.getContext().getActivity().getSystemService(Context.LOCATION_SERVICE);
      Criteria c = new Criteria();

      c.setAccuracy(Criteria.ACCURACY_FINE);

      Location l = lm.getLastKnownLocation(lm.getBestProvider(c, false));

      if (l == null) {
        l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
      }

      if (l == null) {
        return null;
      }

      return SNumber.toString(l.getLatitude()) + "," + SNumber.toString(l.getLongitude()) + ","
             + SNumber.toString(l.getAltitude()) + "," + SNumber.toString(l.getAccuracy()) + ","
             + SNumber.toString(l.getTime());
    } catch(Exception e) {
      Platform.ignoreException("getLocation", e);

      return null;
    }
  }
}
