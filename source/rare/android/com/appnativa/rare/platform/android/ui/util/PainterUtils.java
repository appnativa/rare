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

package com.appnativa.rare.platform.android.ui.util;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;

import android.util.StateSet;

import android.view.View;

import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iComposite.CompositeType;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iGradientPainter.Direction;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

public class PainterUtils {
  public static final int[] STATE_SELECTED   = { android.R.attr.state_selected };
  public static final int[] STATE_UNPRESSED  = { -android.R.attr.state_pressed };
  public static final int[] STATE_PRESSED    = { android.R.attr.state_pressed };
  public static final int[] STATE_ENABLED    = { android.R.attr.state_enabled };
  public static final int[] STATE_DISABLED   = { -android.R.attr.state_enabled };
  public static final int[] STATE_DESELECTED = { -android.R.attr.state_selected };

  /**
   * Install the paint values for the specified component
   *
   * @param c the component
   */
  public static void installPaint(PaintBucket pb, iPainterSupport c) {
    iPlatformComponentPainter cp = pb.getComponentPainter(true);
    View                      v  = (c instanceof View)
                                   ? (View) c
                                   : null;

    if (cp != null) {
      c.setComponentPainter(cp);

      if ((pb.getBorder() != null) && (v != null)) {
        UIInsets in = pb.getBorder().getBorderInsets(null);

        v.setPadding(in.intLeft(), in.intTop(), in.intRight(), in.intBottom());
      }

      return;
    }

    if (v != null) {
      if (pb.getBackgroundColor() != null) {
        v.setBackgroundDrawable(pb.getBackgroundColor().getDrawable());
      } else if (pb.getBorder() != null) {
        v.setBackgroundDrawable(pb.getBorder().getDrawable(null));
      }
    }
  }

  public static UIColor getBackground(PainterHolder ph, View v, UIColor def, int[] state) {
    final PaintBucket pb = getPaintBucket(ph, v, state);
    final UIColor     bg = (pb == null)
                           ? null
                           : pb.getBackgroundColor();

    return (bg == null)
           ? def
           : bg;
  }

  /**
   * Gets the background overlay painter for the button state
   *
   * @param v
   *          the view
   * @param def
   *          the default value
   * @return the background overlay painter for the button state
   */
  public static iPlatformPainter getBackgroundOverlayPainter(PainterHolder ph, View v, iPlatformPainter def,
          int[] state) {
    final PaintBucket      pb = getPaintBucket(ph, v, state);
    final iPlatformPainter op = (pb == null)
                                ? null
                                : pb.getImagePainter();

    return (op == null)
           ? def
           : op;
  }

  /**
   * Gets the background painter for the button state
   *
   * @param v
   *          the view
   * @param def
   *          the default value
   * @return the background painter for the button state
   */
  public static iBackgroundPainter getBackgroundPainter(PainterHolder ph, View v, iBackgroundPainter def, int[] state) {
    final PaintBucket        pb = getPaintBucket(ph, v, state);
    final iBackgroundPainter bp = (pb == null)
                                  ? null
                                  : pb.getBackgroundPainter();

    return (bp == null)
           ? def
           : bp;
  }

  /**
   * Gets the border for the button state Only return a border if the button
   * does no paints it's own border
   *
   * @param v
   *          the view
   * @param def
   *          the default value
   * @return the border
   */
  public static iPlatformBorder getBorder(PainterHolder ph, View v, iPlatformBorder def, int[] state) {
    final PaintBucket     pb = getPaintBucket(ph, v, state);
    final iPlatformBorder b  = (pb == null)
                               ? null
                               : pb.getBorder();

    return (b == null)
           ? def
           : b;
  }

  public static Drawable getDrawable(PaintBucket pb, View view) {
    if ((pb.getImagePainter() == null) && (pb.getBorder() == null)) {
      if (pb.getBackgroundPainter() != null) {
        return pb.getBackgroundPainter().getDrawable(view);
      }

      if (pb.getBackgroundColor() != null) {
        return pb.getBackgroundColor().getDrawable();
      }
    }

    if ((pb.getBorder() == null) || (pb.getImagePainter() == null)) {
      if (pb.getBorder() != null) {
        return pb.getBorder().getDrawable(view);
      }

      if (pb.getImagePainter() != null) {
        return pb.getImagePainter().getDrawable(view);
      }
    }

    iPlatformComponentPainter cp = pb.getComponentPainter(false);

    return (cp == null)
           ? NullDrawable.getInstance()
           : cp.getDrawable(null);
  }

  public static Drawable getDrawable(RenderableDataItem item, View view) {
    iPlatformComponentPainter cp = item.getComponentPainter();
    UIColor                   bg = item.getBackground();
    iPlatformBorder           b  = (iPlatformBorder) item.getBorder();

    if (cp != null) {
      if (cp instanceof UIComponentPainter) {
        if (((UIComponentPainter) cp).getBorder() == null) {
          ((UIComponentPainter) cp).setBorder(b);
        }
      }

      return cp.getDrawable(view);
    }

    Drawable d1 = (bg == null)
                  ? null
                  : bg.getDrawable();
    Drawable d2 = (b == null)
                  ? null
                  : b.getDrawable(view);

    if ((d1 == null) || (d2 == null)) {
      return (d1 == null)
             ? d2
             : d1;
    }

    return new LayerDrawable(new Drawable[] { d1, d2 });
  }

  /**
   * Gets the foreground for the button state Only return a border if the button
   * does no paints it's own border
   *
   * @param v
   *          the view
   * @param def
   *          the default value
   * @return the color
   */
  public static UIColor getForeground(PainterHolder ph, View v, UIColor def, int[] state) {
    final PaintBucket pb = getPaintBucket(ph, v, state);
    final UIColor     fg = (pb == null)
                           ? null
                           : pb.getForegroundColor();

    return (fg == null)
           ? def
           : fg;
  }

  public static GradientDrawable.Orientation getGradientOrientation(Direction direction) {
    switch(direction) {
      case VERTICAL_BOTTOM :
        return GradientDrawable.Orientation.BOTTOM_TOP;

      case HORIZONTAL_LEFT :
        return GradientDrawable.Orientation.LEFT_RIGHT;

      case HORIZONTAL_RIGHT :
        return GradientDrawable.Orientation.RIGHT_LEFT;

      case DIAGONAL_TOP_LEFT :
        return GradientDrawable.Orientation.TL_BR;

      case DIAGONAL_TOP_RIGHT :
        return GradientDrawable.Orientation.TR_BL;

      case DIAGONAL_BOTTOM_LEFT :
        return GradientDrawable.Orientation.BL_TR;

      case DIAGONAL_BOTTOM_RIGHT :
        return GradientDrawable.Orientation.BR_TL;

      case CENTER :
        return GradientDrawable.Orientation.LEFT_RIGHT;

      default :
        return GradientDrawable.Orientation.TOP_BOTTOM;
    }
  }

  public static iPlatformIcon getIcon(PainterHolder ph, View v, boolean pressed) {
    if (!v.isEnabled() && (ph.disabledIcon != null)) {
      return ph.disabledIcon;
    }

    if (pressed && (ph.pressedIcon != null)) {
      return ph.pressedIcon;
    }

    if (v.isSelected() && (ph.selectedIcon != null)) {
      return ph.selectedIcon;
    }

    return ph.normalIcon;
  }

  public static PaintBucket getPaintBucket(PainterHolder ph, View v, int state[]) {
    if (!v.isEnabled() && (ph.disabledPainter != null)) {
      return ph.disabledPainter;
    }

    if (ph.pressedPainter != null) {
      if (StateSet.stateSetMatches(STATE_PRESSED, state)) {
        return ph.pressedPainter;
      }
    }

    if ((ph.selectedPainter != null) && v.isSelected()) {
      return ph.selectedPainter;
    }

    return ph.normalPainter;
  }

  public static PorterDuff.Mode getPorterDuffMode(CompositeType type) {
    switch(type) {
      case SRC_ATOP :
        return PorterDuff.Mode.SRC_ATOP;

      case SRC_IN :
        return PorterDuff.Mode.SRC_IN;

      case SRC_OUT :
        return PorterDuff.Mode.SRC_OUT;

      case DST_OVER :
        return PorterDuff.Mode.DST_OVER;

      case DST_ATOP :
        return PorterDuff.Mode.DST_ATOP;

      case DST_IN :
        return PorterDuff.Mode.DST_IN;

      case DST_OUT :
        return PorterDuff.Mode.DST_OUT;

      case XOR :
        return PorterDuff.Mode.XOR;

      case LIGHTEN :
        return PorterDuff.Mode.LIGHTEN;

      case DARKEN :
        return PorterDuff.Mode.DARKEN;

      case CLEAR :
        return PorterDuff.Mode.CLEAR;

      default :
        return PorterDuff.Mode.SRC_OVER;
    }
  }
}
