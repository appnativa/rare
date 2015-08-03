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

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.GridCell.CBorder;
import com.appnativa.rare.spot.Margin;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.border.UIBackBorder;
import com.appnativa.rare.ui.border.UIBalloonBorder;
import com.appnativa.rare.ui.border.UIBevelBorder;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIDropShadowBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UIEtchedBorder;
import com.appnativa.rare.ui.border.UIFrameBorder;
import com.appnativa.rare.ui.border.UIIconBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.border.UITitledBorder;
import com.appnativa.rare.ui.border.aUIBalloonBorder.PeakLocation;
import com.appnativa.rare.ui.border.aUIFrameBorder;
import com.appnativa.rare.ui.border.aUILineBorder;
import com.appnativa.rare.ui.painter.NinePatch;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SDFNode;
import com.appnativa.spot.SPOTEnumerated;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.SNumber;

import java.io.Reader;
import java.io.StringReader;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class BorderUtils {
  public static final UIEmptyBorder EMPTY_BORDER                    = new UIEmptyBorder(0, 0, 0, 0);
  public static final String        FOCUSBORDER_CONFIGURED_PROPERTY = "__RARE_FOCUSBORDER_CONFIGURED_PROPERTY";
  public static final UIEmptyBorder ONE_POINT_EMPTY_BORDER          = new UIEmptyBorder(ScreenUtils.PLATFORM_PIXELS_1);
  public static final String        TITLEBORDER_CONFIGURED_PROPERTY = "__RARE_TITLEBORDER_CONFIGURED_PROPERTY";
  public static final UIEmptyBorder TWO_POINT_EMPTY_BORDER          = new UIEmptyBorder(ScreenUtils.PLATFORM_PIXELS_2);
  public static final UIEmptyBorder TWO_LEFT_POINT_EMPTY_BORDER     = new UIEmptyBorder(0, 0, 0,
                                                                        ScreenUtils.PLATFORM_PIXELS_2, true);
  public static final UIEmptyBorder THREE_LEFT_POINT_EMPTY_BORDER = new UIEmptyBorder(0, 0, 0,
                                                                      ScreenUtils.PLATFORM_PIXELS_2, true);
  public static final UIEmptyBorder TEXT_PADDING_BORDER = new UIEmptyBorder(ScreenUtils.PLATFORM_PIXELS_2,
                                                            ScreenUtils.PLATFORM_PIXELS_2,
                                                            ScreenUtils.PLATFORM_PIXELS_2,
                                                            ScreenUtils.PLATFORM_PIXELS_4);
  public static final UIEmptyBorder ONE_TOP_POINT_EMPTY_BORDER = new UIEmptyBorder(ScreenUtils.PLATFORM_PIXELS_1, 0, 0,
                                                                   0);
  public static final UIEmptyBorder ONE_LEFT_POINT_EMPTY_BORDER = new UIEmptyBorder(0, 0, 0,
                                                                    ScreenUtils.PLATFORM_PIXELS_1, true);
  public static final UIEmptyBorder ONE_BOTTOM_POINT_EMPTY_BORDER = new UIEmptyBorder(0, 0,
                                                                      ScreenUtils.PLATFORM_PIXELS_1, 0);
  private static iPlatformBorder defaultButtonBorder;
  private static UILineBorder    textareaBorder;
  private static UILineBorder    textfieldBorder;
  private static iPlatformBorder toolbarPressedButtonBorder;
  private static iPlatformBorder widgetBorder;

  public static iPlatformPath createBackBorderPath(iPlatformPath p, float x, float y, float width, float height,
          float aw, float ah, boolean noLeft) {
    if (p == null) {
      p = PlatformHelper.createPath();
    }

    float h2 = height / 2 - 1;

    height--;
    width--;

    if (noLeft) {
      p.moveTo(x + width, y + height);
      p.lineTo(x + aw, y + height);
      p.quadTo(x, y + height, x, y + height - ah);
      p.lineTo(x, y + ah);
      p.quadTo(x, y, x + aw, y);
      p.lineTo(x + width, y);
      p.moveTo(x + width, y);
    } else {
      p.moveTo(x, y + h2);
      p.lineTo(x + h2, y);
      p.lineTo(x + width - aw, y);
      p.quadTo(x + width, y, x + width, y + ah);
      p.lineTo(x + width, y + height - ah);
      p.quadTo(x + width, y + height, x + width - aw, y + height);
      p.lineTo(x + h2, y + height);
      p.lineTo(x, y + h2);
      p.moveTo(x, y + h2);
    }

    p.close();

    return p;
  }

  public static iPlatformPath createBalloonLLBPath(iPlatformPath p, float width, float height, float arc,
          float peakSize, float peakOffset) {
    if (p == null) {
      p = PlatformHelper.createPath();
    }

    final float peak = peakSize;
    final float off  = peakOffset;

    p.moveTo(0, arc);
    p.quadTo(0, 0, arc, 0);
    p.lineTo(width - arc, 0);
    p.quadTo(width, 0, width, arc);
    p.lineTo(width, height - arc - peak);
    p.quadTo(width, height - peak, width - arc, height - peak);
    p.lineTo(arc + peak * 2 + off, height - peak);
    p.lineTo(arc + peak + off, height);
    p.lineTo(arc + off, height - peak);
    p.lineTo(arc, height - peak);
    p.quadTo(0, height - peak, 0, height - arc - peak);
    p.lineTo(0, arc);
    p.close();

    return p;
  }

  public static iPlatformPath createBalloonLLLPath(iPlatformPath p, float width, float height, float arc,
          float peakSize, float peakOffset) {
    if (p == null) {
      p = PlatformHelper.createPath();
    }

    final float peak = peakSize;
    final float off  = peakOffset;

    p.moveTo(peak, arc);
    p.quadTo(peak, 0, peak + arc, 0);
    p.lineTo(width - arc, 0);
    p.quadTo(width, 0, width, arc);
    p.lineTo(width, height - arc);
    p.quadTo(width, height, width - arc, height);
    p.lineTo(arc + peak, height);
    p.quadTo(peak, height, peak, height - arc);
    p.lineTo(peak, height - arc - off);
    p.lineTo(0, height - arc - peak - off);
    p.lineTo(peak, height - arc - peak * 2 - off);
    p.lineTo(peak, arc);
    p.close();

    return p;
  }

  public static iPlatformPath createBalloonLRBPath(iPlatformPath p, float width, float height, float arc,
          float peakSize, float peakOffset) {
    if (p == null) {
      p = PlatformHelper.createPath();
    }

    final float peak = peakSize;
    final float off  = peakOffset;

    p.moveTo(0, arc);
    p.quadTo(0, 0, arc, 0);
    p.lineTo(width - arc, 0);
    p.quadTo(width, 0, width, arc);
    p.lineTo(width, height - arc - peak);
    p.quadTo(width, height - peak, width - arc, height - peak);
    p.lineTo(width - arc - off, height - peak);
    p.lineTo(width - arc - peak - off, height);
    p.lineTo(width - arc - peak * 2 - off, height - peak);
    p.lineTo(arc, height - peak);
    p.quadTo(0, height - peak, 0, height - arc - peak);
    p.lineTo(0, arc);
    p.close();

    return p;
  }

  public static iPlatformPath createBalloonLRRPath(iPlatformPath p, float width, float height, float arc,
          float peakSize, float peakOffset) {
    if (p == null) {
      p = PlatformHelper.createPath();
    }

    final float peak = peakSize;
    final float off  = peakOffset;

    p.moveTo(0, arc);
    p.quadTo(0, 0, arc, 0);
    p.lineTo(width - arc - peak, 0);
    p.quadTo(width - peak, 0, width - peak, arc);
    p.lineTo(width - peak, height - arc - (peak * 2) - off);
    p.lineTo(width, height - arc - peak - off);
    p.lineTo(width - peak, height - arc - off);
    p.lineTo(width - peak, height - arc);
    p.quadTo(width - peak, height, width - arc - peak, height);
    p.lineTo(arc, height);
    p.quadTo(0, height, 0, height - arc);
    p.lineTo(0, arc);
    p.close();

    return p;
  }

  public static iPlatformPath createBalloonULLPath(iPlatformPath p, float width, float height, float arc,
          float peakSize, float peakOffset) {
    if (p == null) {
      p = PlatformHelper.createPath();
    }

    final float peak = peakSize;
    final float off  = peakOffset;

    p.moveTo(peak, arc);
    p.quadTo(peak, 0, peak + arc, 0);
    p.lineTo(width - arc, 0);
    p.quadTo(width, 0, width, arc);
    p.lineTo(width, height - arc);
    p.quadTo(width, height, width - arc, height);
    p.lineTo(arc + peak, height);
    p.quadTo(peak, height, peak, height - arc);
    p.lineTo(peak, arc + peak * 2 + off);
    p.lineTo(0, arc + peak + off);
    p.lineTo(peak, arc + off);
    p.lineTo(peak, arc);
    p.close();

    return p;
  }

  public static iPlatformPath createBalloonULTPath(iPlatformPath p, float width, float height, float arc,
          float peakSize, float peakOffset) {
    if (p == null) {
      p = PlatformHelper.createPath();
    }

    final float peak = peakSize;
    final float off  = peakOffset;

    p.moveTo(0, peak + arc);
    p.quadTo(0, peak, arc, peak);
    p.lineTo(off, peak);
    p.lineTo(peak + off, 0);
    p.lineTo(peak * 2 + off, peak);
    p.lineTo(width - arc, peak);
    p.quadTo(width, peak, width, peak + arc);
    p.lineTo(width, height - arc);
    p.quadTo(width, height, width - arc, height);
    p.lineTo(arc, height);
    p.quadTo(0, height, 0, height - arc);
    p.lineTo(0, peak + arc);
    p.close();

    return p;
  }

  public static iPlatformPath createBalloonURRPath(iPlatformPath p, float width, float height, float arc,
          float peakSize, float peakOffset) {
    if (p == null) {
      p = PlatformHelper.createPath();
    }

    final float peak = peakSize;
    final float off  = peakOffset;

    p.moveTo(0, arc);
    p.quadTo(0, 0, arc, 0);
    p.lineTo(width - arc - peak, 0);
    p.quadTo(width - peak, 0, width - peak, arc);
    p.lineTo(width - peak, arc + off);
    p.lineTo(width, arc + peak + off);
    p.lineTo(width - peak, arc + peak * 2 + off);
    p.lineTo(width - peak, height - arc - peak);
    p.quadTo(width - peak, height, width - arc - peak, height);
    p.lineTo(arc, height);
    p.quadTo(0, height, 0, height - arc);
    p.lineTo(0, arc);
    p.close();

    return p;
  }

  public static iPlatformPath createBalloonURTPath(iPlatformPath p, float width, float height, float arc,
          float peakSize, float peakOffset) {
    if (p == null) {
      p = PlatformHelper.createPath();
    }

    final float peak = peakSize;
    final float off  = peakOffset;

    p.moveTo(0, peak + arc);
    p.quadTo(0, peak, arc, peak);
    p.lineTo(width - arc - peak * 2 - off, peak);
    p.lineTo(width - arc - peak - off, 0);
    p.lineTo(width - arc - off, peak);
    p.lineTo(width - arc, peak);
    p.quadTo(width, peak, width, peak + arc);
    p.lineTo(width, height - arc);
    p.quadTo(width, height, width - arc, height);
    p.lineTo(arc, height);
    p.quadTo(0, height, 0, height - arc);
    p.lineTo(0, peak + arc);
    p.close();

    return p;
  }

  /**
   * Creates the border for the specified border type.
   *
   * @param context
   *          the context
   * @param btype
   *          the border type
   * @param attrs
   *          border attributes
   *
   * @return the the created border
   */
  public static iPlatformBorder createBorder(iWidget context, int btype, Map<String, String> attrs) {
    iPlatformBorder border    = null;
    UIColor         c         = null;
    UIColor[]       ca        = null;
    String          color     = null;
    float           thickness = 0;
    float           arcW      = 0;
    float           arcH      = 0;
    String          s;
    UIInsets        in         = null;
    iPlatformIcon[] icons      = null;
    boolean         flatBottom = false;
    boolean         flatTop    = false;
    boolean         flatLeft   = false;
    boolean         flatRight  = false;
    boolean         noBottom   = false;
    boolean         noTop      = false;
    boolean         padForArc  = true;
    boolean         top        = false;
    boolean         left       = false;
    boolean         bottom     = true;
    boolean         right      = true;
    UIBevelBorder   bb;

    if (attrs != null) {
      color = attrs.get("color");
      s     = attrs.get("cornerArc");

      if (s != null) {
        char[] a = s.toCharArray();

        arcW = SNumber.intValue(a, 0, a.length, false);

        if (arcW < 0) {
          arcW = 0;
        }

        int i = s.indexOf(',');

        if (i != -1) {
          arcH = SNumber.intValue(a, i + 1, a.length - (i + 1), false);
        }

        if (arcH < 1) {
          arcH = arcW;
        }
      }

      arcH *= ScreenUtils.PLATFORM_PIXELS_1;
      arcW *= ScreenUtils.PLATFORM_PIXELS_1;
      s    = attrs.get("thickness");

      if (s != null) {
        thickness = SNumber.floatValue(s);

        if (thickness < 0) {
          thickness = 0;
        } else if (SNumber.isEqual(1, thickness)) {
          thickness = UILineBorder.onePixelThickness;
        } else if (thickness > 1) {
          thickness *= ScreenUtils.PLATFORM_PIXELS_1;
        }
      }

      noBottom   = "true".equalsIgnoreCase(attrs.get("noBottom"));
      noTop      = "true".equalsIgnoreCase(attrs.get("noTop"));
      flatBottom = "true".equalsIgnoreCase(attrs.get("flatBottom"));
      flatTop    = "true".equalsIgnoreCase(attrs.get("flatTop"));
      flatLeft   = "true".equalsIgnoreCase(attrs.get("flatLeft"));
      flatRight  = "true".equalsIgnoreCase(attrs.get("flatRight"));
      padForArc  = !"false".equalsIgnoreCase(attrs.get("padForArc"));
    }

    switch(btype) {
      case Widget.CBorder.none :
        border = EMPTY_BORDER;

        break;

      case Widget.CBorder.raised :
      case Widget.CBorder.lowered : {
        if (color == null) {
          ca = getBevelColors(context, false);
        } else {
          ca = ColorUtils.getColors(color, 2);
        }

        bb = new UIBevelBorder((btype == Widget.CBorder.raised)
                               ? UIBevelBorder.RAISED
                               : UIBevelBorder.LOWERED, false);
        bb.setFourColorBevel(false);
        bb.setColors(ca[0], ca[1]);
        border = bb;

        break;
      }

      case Widget.CBorder.bevel_raised :
      case Widget.CBorder.bevel_lowered : {
        bb = new UIBevelBorder((btype == Widget.CBorder.bevel_raised)
                               ? UIBevelBorder.RAISED
                               : UIBevelBorder.LOWERED, true);

        if (color == null) {
          ca = getBevelColors(context, true);
        } else {
          ca = ColorUtils.getColors(color, 4);
        }

        bb.setFourColorBevel(true);
        bb.setColors(ca[0], ca[1], ca[2], ca[3]);
        border = bb;

        break;
      }

      case Widget.CBorder.etched_raised :
      case Widget.CBorder.etched_lowered : {
        if (color == null) {
          ca = getEtchedColors(context);
        } else {
          ca = ColorUtils.getColors(color, 2);
        }

        border = new UIEtchedBorder((btype == Widget.CBorder.etched_raised)
                                    ? UIEtchedBorder.RAISED
                                    : UIEtchedBorder.LOWERED, ca[0], ca[1]);

        break;
      }

      case Widget.CBorder.titled :
      case Widget.CBorder.group_box :
        border = createTitledBorder(context, attrs, color, thickness, true);

        break;

      case Widget.CBorder.line : {
        ca = (color == null)
             ? null
             : ColorUtils.getColors(color, 2);
        c  = ((ca == null) || (ca[0] == null))
             ? UILineBorder.getDefaultLineColor()
             : ca[0];

        if (thickness < .25) {
          thickness = (thickness == 0)
                      ? UILineBorder.onePixelThickness
                      : ScreenUtils.PLATFORM_PIXELS_1;
        }

        aUILineBorder lb;

        lb = UILineBorder.createBorder(context, c, thickness, arcW, arcH);

        if ((ca != null) && (ca[1] != null)) {
          lb = lb.setHilightColor(ca[1]);
        } else if ((ca == null) || (ca[0] == null)) {
          lb = lb.setHilightColor(Platform.getUIDefaults().getColor("controlHighlight"));
        }

        if (noBottom) {
          lb = lb.setNoBottom(true);
        } else if (flatBottom) {
          lb = lb.setFlatBottom(true);
        }

        if (noTop) {
          lb = lb.setNoTop(true);
        } else if (flatTop) {
          lb = lb.setFlatTop(true);
        }

        if (flatLeft) {
          lb = lb.setFlatLeft(flatLeft);
        }

        if (flatRight) {
          lb = lb.setFlatRight(flatRight);
        }

        if (attrs != null) {
          if ("true".equalsIgnoreCase(attrs.get("noLeft"))) {
            lb = lb.setNoLeft(true);
          }

          if ("true".equalsIgnoreCase(attrs.get("noRight"))) {
            lb = lb.setNoRight(true);
          }

          s = attrs.get("style");

          if (s != null) {
            lb = lb.setLineStyle(s);
          }

          s = attrs.get("insets");

          if (s != null) {
            in = Utils.getInsets(s);

            if (in != null) {
              lb = lb.setInsets(in);
            }
          }
        }

        border = lb;

        break;
      }

      case Widget.CBorder.icon : {
        if (attrs != null) {
          s = attrs.get("insets");

          if ((s == null) || (s.length() == 0)) {
            border = new UIIconBorder(0, 0, 0, 0);
          } else {
            border = new UIIconBorder(Utils.getInsets(s));
          }

          s = attrs.get("icon");

          if (s != null) {
            if (s.toLowerCase(Locale.US).endsWith(".9.png")) {
              try {
                ((UIIconBorder) border).setNinePatch(new NinePatch(UIImageHelper.createImage(context.getURL(s), true,
                        1)));
              } catch(Exception ex) {
                Platform.ignoreException("UIIconBorder:" + s, ex);
              }
            } else if (s.startsWith("drawable/")) {
              ((UIIconBorder) border).setPattern(context.getAppContext().getResourceAsImage(s));
            } else {
              icons = Utils.getIcons(context, s, 4);

              if (icons != null) {
                ((UIIconBorder) border).setIcons(icons[0], icons[1], icons[2], icons[3]);
              }
            }
          }

          s = attrs.get("renderType");

          if ((s != null) || (attrs.get("opacity") != null)) {
            RenderType types[] = Utils.getRenderTypes(s, 4);

            in = Utils.getInsets(attrs.get("opacity"));

            if (in == null) {
              in = new UIInsets(ScreenUtils.PLATFORM_PIXELS_1 * 32);
            }

            if (types != null) {
              ((UIIconBorder) border).setRenderInfo(types[0], (int) in.top, types[1], (int) in.left, types[2],
                      (int) in.bottom, types[3], (int) in.right);
            }
          }
        } else {
          border = new UIIconBorder(0, 0, 0, 0);
        }

        break;
      }

      case Widget.CBorder.back : {
        c = (color == null)
            ? UILineBorder.getDefaultLineColor()
            : ColorUtils.getColor(color);

        if (thickness < .25) {
          thickness = (thickness == 0)
                      ? UILineBorder.onePixelThickness
                      : ScreenUtils.PLATFORM_PIXELS_1;
        }

        border = new UIBackBorder(c, thickness, arcW, arcH);

        if (attrs != null) {
          if ("true".equalsIgnoreCase(attrs.get("noLeft"))) {
            ((UIBackBorder) border).setNoLeft(true);
          }
        }

        break;
      }

      case Widget.CBorder.balloon : {
        c = (color == null)
            ? UILineBorder.getDefaultLineColor()
            : ColorUtils.getColor(color);

        if (thickness < .25) {
          thickness = (thickness == 0)
                      ? UILineBorder.onePixelThickness
                      : ScreenUtils.PLATFORM_PIXELS_1;
        }

        border = new UIBalloonBorder(c, thickness, Math.max(arcW, arcH));

        boolean autoLocate = true;

        if (attrs != null) {
          s = attrs.get("style");

          if (s != null) {
            try {
              ((UIBalloonBorder) border).setPeakLocation(PeakLocation.valueOf(s.toUpperCase(Locale.US)));
            } catch(Exception e) {
              Platform.ignoreException("bad balloon border peak location in style attribute", e);
            }
          }

          s = attrs.get("insets");

          if (s != null) {
            int n = s.indexOf(',');

            if (n != -1) {
              int ps = SNumber.intValue(s.substring(n + 1).trim());

              if (ps != 0) {
                ((UIBalloonBorder) border).setPeakSize(ScreenUtils.platformPixels(ps));
              }

              s = s.substring(0, n).trim();

              int po = SNumber.intValue(s);

              if (po != 0) {
                autoLocate = false;
                ((UIBalloonBorder) border).setPeakOffset(ScreenUtils.platformPixels(po));
              }
            }
          }
        }

        ((UIBalloonBorder) border).setAutoLocatePeak(autoLocate);

        break;
      }

      case Widget.CBorder.matte : {
        c = (color == null)
            ? UILineBorder.getDefaultLineColor()
            : ColorUtils.getColor(color);

        if (thickness < .25) {
          thickness = ScreenUtils.PLATFORM_PIXELS_1;
        }

        s      = attrs.get("insets");
        in     = Utils.getInsets(s);
        border = new UIMatteBorder(in, c);
        s      = (attrs == null)
                 ? null
                 : attrs.get("style");

        if (s != null) {
          ((UIMatteBorder) border).setLineStyle(s);
        }

        break;
      }

      case Widget.CBorder.standard :
        break;

      case Widget.CBorder.frame_raised : {
        if (color == null) {
          ca     = getBevelColors(context, true);
          border = new UIFrameBorder(aUIFrameBorder.RAISED, ca[0], ca[1], ca[2], ca[3]);
        } else {
          ca = ColorUtils.getColors(color, 2);

          if (ca.length > 2) {
            border = new UIFrameBorder(aUIFrameBorder.RAISED, ca[0], ca[1], ca[2], (ca.length > 3)
                    ? ca[3]
                    : null);
          } else {
            if ((ca[0] == null) || (ca[1] == null)) {
              ca     = getBevelColors(context, true);
              border = new UIFrameBorder(aUIFrameBorder.RAISED, ca[0], ca[1], ca[2], ca[3]);
            }
          }
        }

        break;
      }

      case Widget.CBorder.frame_lowered : {
        if (color == null) {
          ca     = getBevelColors(context, false);
          border = new UIFrameBorder(aUIFrameBorder.LOWERED, ca[0], ca[1]);
        } else {
          ca = ColorUtils.getColors(color, 2);

          if (ca.length > 2) {
            border = new UIFrameBorder(aUIFrameBorder.LOWERED, ca[0], ca[1], ca[2], (ca.length > 3)
                    ? ca[3]
                    : null);
          } else {
            if ((ca[0] == null) || (ca[1] == null)) {
              ca     = getBevelColors(context, false);
              border = new UIFrameBorder(aUIFrameBorder.LOWERED, ca[0], ca[1]);
            }
          }
        }

        break;
      }

      case Widget.CBorder.drop_shadow : {
        c = (color == null)
            ? UIDropShadowBorder.getDefaultShadowColor()
            : ColorUtils.getColor(color);

        if (thickness < 1) {
          thickness = ScreenUtils.PLATFORM_PIXELS_5;
        }

        if (arcW < 1) {
          arcW = ScreenUtils.PLATFORM_PIXELS_6 + ScreenUtils.PLATFORM_PIXELS_6;
        }

        top    = false;
        left   = false;
        bottom = true;
        right  = true;

        if (attrs != null) {
          s  = attrs.get("insets");
          in = Utils.getInsets(s);

          if (in != null) {
            top    = in.top > 0;
            left   = in.left > 0;
            bottom = in.bottom > 0;
            right  = in.right > 0;
          }
        }

        border = new UIDropShadowBorder(c, thickness, .5f, arcW, top, left, bottom, right);

        break;
      }

      case Widget.CBorder.shadow : {
        c = (color == null)
            ? UIDropShadowBorder.getDefaultShadowColor()
            : ColorUtils.getColor(color);

        if (thickness < 1) {
          thickness = ScreenUtils.PLATFORM_PIXELS_5;
        }

        if (arcW < 1) {
          arcW = ScreenUtils.PLATFORM_PIXELS_6 + ScreenUtils.PLATFORM_PIXELS_6;
        }

        top    = true;
        left   = true;
        bottom = true;
        right  = true;

        if (attrs != null) {
          s  = attrs.get("insets");
          in = Utils.getInsets(s);

          if (in != null) {
            top    = in.top > 0;
            left   = in.left > 0;
            bottom = in.bottom > 0;
            right  = in.right > 0;
          }
        }

        border = new UIDropShadowBorder(c, thickness, .5f, arcW, top, left, bottom, right);

        break;
      }

      case Widget.CBorder.empty : {
        if (attrs != null) {
          s = attrs.get("insets");

          if ((s == null) || (s.length() == 0)) {
            border = EMPTY_BORDER;
          } else {
            border = new UIEmptyBorder(Utils.getInsets(s));
          }
        } else {
          border = EMPTY_BORDER;
        }

        break;
      }

      case Widget.CBorder.custom : {
        s = (attrs == null)
            ? null
            : attrs.get("class");

        if (s != null) {
          if (s.startsWith(iConstants.RESOURCE_PREFIX)) {
            border = Platform.getUIDefaults().getBorder(s.substring(iConstants.RESOURCE_PREFIX_LENGTH));
          } else {
            try {
              Class       cls         = Platform.loadClass(s);
              Constructor constructor = PlatformHelper.getConstructor(cls, Map.class);

              if (constructor != null) {
                border = (iPlatformBorder) constructor.newInstance(attrs);
              } else {
                border = (iPlatformBorder) cls.newInstance();
              }
            } catch(Exception ex) {
              if (context.isDesignMode()) {
                Platform.debugLog("Unable to load custom border class:" + s);
              } else {
                throw ApplicationException.runtimeException(ex);
              }
            }
          }
        }

        if (border instanceof aUILineBorder) {
          aUILineBorder lb = (aUILineBorder) border;

          lb.setCornerArc(arcW);

          if (thickness < .25) {
            thickness = (thickness == 0)
                        ? UILineBorder.onePixelThickness
                        : ScreenUtils.PLATFORM_PIXELS_1;
          }

          lb.setThickness(thickness);

          if (color != null) {
            lb.setLineColor(ColorUtils.getColor(color));
          }

          if (noBottom) {
            lb = lb.setNoBottom(true);
          } else if (flatBottom) {
            lb = lb.setFlatBottom(true);
          }

          if (noTop) {
            lb = lb.setNoTop(true);
          } else if (flatTop) {
            lb = lb.setFlatTop(true);
          }

          if (flatLeft) {
            lb = lb.setFlatLeft(flatLeft);
          }

          if (flatRight) {
            lb = lb.setFlatRight(flatRight);
          }

          if (attrs != null) {
            if ("true".equalsIgnoreCase(attrs.get("noLeft"))) {
              lb = lb.setNoLeft(true);
            }

            if ("true".equalsIgnoreCase(attrs.get("noRight"))) {
              lb = lb.setNoRight(true);
            }

            s = attrs.get("style");

            if (s != null) {
              lb = lb.setLineStyle(s);
            }

            s = attrs.get("insets");

            if (s != null) {
              in = Utils.getInsets(s);

              if (in != null) {
                lb = lb.setInsets(in);
              }
            }
          }
        }

        break;
      }

      default :
        break;
    }

    if (border != null) {
      s = (attrs == null)
          ? null
          : attrs.get("customProperties");

      if ((s != null) && (s.length() > 0) && (border != null)) {
        border.handleCustomProperties(CharScanner.parseOptionString(s, ';'));
      }
    }

    if (border instanceof UIBevelBorder) {
      if (flatBottom) {
        ((UIBevelBorder) border).setFlatBottom(true);
      }

      if (noBottom) {
        ((UIBevelBorder) border).setNoBottom(true);
      }
    }

    if (border != null) {
      border.setPadForArc(padForArc);
    }

    return border;
  }

  /**
   * Creates a border for a set of border definitions
   *
   * @param context
   *          the context
   * @param borders
   *          the set of border definitions
   * @param standard
   *          the standard border for the component that this border is for
   * @return the border or null
   */
  public static iPlatformBorder createBorder(iWidget context, SPOTSet borders, iPlatformBorder standard) {
    if (borders == null) {
      return null;
    }

    int len = borders.getCount();

    if (len == 0) {
      return null;
    }

    SPOTEnumerated e;

    if (len == 1) {
      e = (SPOTEnumerated) borders.getEx(0);

      return createBorder(context, e.intValue(), e.spot_getAttributesEx());
    }

    ArrayList<iPlatformBorder> list = new ArrayList<iPlatformBorder>(len);

    for (int i = 0; i < len; i++) {
      e = (SPOTEnumerated) borders.getEx(i);

      iPlatformBorder b = createBorder(context, e.intValue(), e.spot_getAttributesEx());

      if ((b == null) && (e.intValue() == Widget.CBorder.standard)) {
        b = standard;
      }

      if (b != null) {
        list.add(b);
      }
    }

    len = list.size();

    if (len == 0) {
      return null;
    }

    if (len == 1) {
      return list.get(0);
    }

    if (len == 2) {
      return new UICompoundBorder(list.get(0), list.get(1));
    }

    return new UICompoundBorder(list.toArray(new iPlatformBorder[len]));
  }

  @SuppressWarnings("resource")
  public static iPlatformBorder createBorder(iWidget context, String border, iPlatformBorder standard) {
    if (border == null) {
      return null;
    }

    if ("none".equals(border)) {
      return EMPTY_BORDER;
    }

    try {
      Reader r;

      if (!border.startsWith("{") &&!border.startsWith("borders")) {
        r = new CharArray(border.length() + 2).set('{').append(border).append('}');
      } else {
        r = new StringReader(border);
      }

      SDFNode node = SDFNode.parse(r, context.getURLResolver(), null, false);

      node = node.getFirstDataNode();

      SPOTSet borders = new SPOTSet("border", new CBorder(null, null, CBorder.standard, "standard", true), -1, -1,
                                    true);

      borders.fromSDF(node);

      return createBorder(context, borders, standard);
    } catch(Exception e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  public static iPlatformBorder createEmptyBorder(iWidget context, Margin m) {
    return new UIEmptyBorder(m.getInsets());
  }

  /**
   * Creates a new platform path object
   *
   * @return a new platform path object
   */
  public static iPlatformPath createPath() {
    return PlatformHelper.createPath();
  }

  public static iPlatformPath createLineBorderPath(iPlatformPath p, float width, float height, float aw, float ah,
          boolean flatBottom, boolean flatTop, boolean noTop, boolean noBottom, boolean noLeft, boolean noRight) {
    return createLineBorderPath(p, width, height, aw, ah, flatBottom, flatTop, false, false, noTop, noBottom, noLeft,
                                noRight);
  }

  public static iPlatformPath createLineBorderPath(iPlatformPath p, float width, float height, float aw, float ah,
          boolean flatBottom, boolean flatTop, boolean flatLeft, boolean flatRight, boolean noTop, boolean noBottom,
          boolean noLeft, boolean noRight) {
    if (p == null) {
      p = PlatformHelper.createPath();
    }

    if (((aw == 0) && (ah == 0)) || (flatBottom && flatTop && flatLeft && flatRight)) {
      p.moveTo(0, 0);

      if (!noTop) {
        p.lineTo(width, 0);
      } else {
        p.moveTo(width, 0);
      }

      if (!noRight) {
        p.lineTo(width, height);
      } else {
        p.moveTo(width, height);
      }

      if (!noBottom) {
        p.lineTo(0, height);
      } else {
        p.moveTo(0, height);
      }

      if (!noLeft) {
        p.lineTo(0, 0);
      } else {
        p.moveTo(0, 0);
      }

      p.moveTo(0, 0);
      // p.close();

      return p;
    }

    if (noLeft || flatLeft) {
      p.moveTo(0, 0);
      p.lineTo(width - aw, 0);
      p.quadTo(width, 0, width, ah);
      p.lineTo(width, height - ah);
      p.quadTo(width, height, width - aw, height);
      p.lineTo(0, height);

      if (flatLeft) {
        p.lineTo(0, 0);
      } else {
        p.moveTo(0, height);
      }
    } else if (noRight || flatRight) {
      p.moveTo(width, height);
      p.lineTo(aw, height);
      p.quadTo(0, height, 0, height - ah);
      p.lineTo(0, ah);
      p.quadTo(0, 0, aw, 0);
      p.lineTo(width, 0);

      if (flatTop) {
        p.lineTo(0, 0);
      } else {
        p.moveTo(width, 0);
      }
    } else if (flatTop) {
      p.moveTo(0, 0);
      p.lineTo(0, height - ah);
      p.quadTo(0, height, aw, height);
      p.lineTo(width - aw, height);
      p.quadTo(width, height, width, height - ah);
      p.lineTo(width, 0);

      if (noTop) {
        p.moveTo(width, 0);
      } else {
        p.lineTo(0, 0);
      }
    } else if (flatBottom) {
      p.moveTo(0, height);
      p.lineTo(0, ah);
      p.quadTo(0, 0, aw, 0);
      p.lineTo(width - aw, 0);
      p.quadTo(width, 0, width, ah);
      p.lineTo(width, height);

      if (noBottom) {
        p.moveTo(width, height);
      } else {
        p.lineTo(0, height);
      }
    } else {
      p.drawRoundedRect(0, 0, width, height, aw, ah);
    }

    p.close();

    return p;
  }

  public static UIDropShadowBorder createShadowBorder(float thickness) {
    UIColor c    = UIDropShadowBorder.getDefaultShadowColor();
    float   arcW = ScreenUtils.PLATFORM_PIXELS_5;

    return new UIDropShadowBorder(c, thickness, .5f, arcW, true, true, true, true);
  }

  public static UIColor findBorderColor(iPlatformBorder b) {
    if (b instanceof UILineBorder) {
      return ((UILineBorder) b).getLineColor();
    }

    if (b instanceof UIMatteBorder) {
      return ((UIMatteBorder) b).getLineColor();
    }

    if (b instanceof UICompoundBorder) {
      UIColor c = findBorderColor(((UICompoundBorder) b).getOutsideBorder());

      if (c == null) {
        c = findBorderColor(((UICompoundBorder) b).getInsideBorder());
      }

      return c;
    }

    return null;
  }

  public static UIColor[] getBevelColors(UIColor c, boolean four) {
    UIColor[] ca;

    if ((c == null) || c.equals(UIColor.BLACK)) {
      c = ColorUtils.getBackground();
    }

    if (c.isDarkColor()) {
      c = c.brighter();
    }

    if (four) {
      ca    = new UIColor[4];
      ca[3] = c.darker().darker();
      ca[2] = c.darker();
      ca[1] = c.brighterBrighter();
      ca[0] = c.brighter();
    } else {
      ca    = new UIColor[2];
      ca[0] = c.darkerDarker();
      ca[1] = c.brighter();
    }

    return ca;
  }

  public static UILineBorder getButtonBorder(int pointInset) {
    UIColor c = Platform.getUIDefaults().getColor("Rare.PushButton.borderColor");

    if (c == null) {
      c = UILineBorder.getDefaultLineColor().darker();
    }

    float        th = Platform.isTouchDevice()
                      ? ScreenUtils.platformPixels(1.5f)
                      : ScreenUtils.PLATFORM_PIXELS_1;
    UILineBorder b  = new UILineBorder(c, th, ScreenUtils.PLATFORM_PIXELS_6);
    int          n  = ScreenUtils.platformPixels(pointInset);

    b.setInsetsPadding(n, n, n, n);

    return b;
  }

  public static iPlatformBorder getDefaultButtonBorder() {
    if (defaultButtonBorder == null) {
      UIColor c = Platform.getUIDefaults().getColor("Rare.PushButton.borderColor");

      if (c == null) {
        c = UILineBorder.getDefaultLineColor().darker();
      }

      float        th     = Platform.isTouchDevice()
                            ? ScreenUtils.platformPixels(1.5f)
                            : ScreenUtils.PLATFORM_PIXELS_1;
      UILineBorder b      = new UILineBorder(c, th, ScreenUtils.PLATFORM_PIXELS_6);
      UIInsets     insets = Platform.getUIDefaults().getInsets("Rare.PushButton.borderInsets");

      if (insets != null) {
        b.setInsetsPadding(insets.top, insets.right, insets.bottom, insets.left);
      } else {
        int tb = ScreenUtils.platformPixels(Platform.isTouchDevice()
                ? 8
                : 2);
        int lr = ScreenUtils.platformPixels(8);

        b.setInsetsPadding(tb, lr, tb, lr);
      }

      defaultButtonBorder = b;
    }

    return defaultButtonBorder;
  }

  public static UILineBorder getDefaultComboBoxBorder() {
    UIColor c = Platform.getUIDefaults().getColor("Rare.Widget.borderColor");

    if (c == null) {
      c = UILineBorder.getDefaultLineColor().darker();
    }

    return new UILineBorder(c, ScreenUtils.PLATFORM_PIXELS_1, ScreenUtils.PLATFORM_PIXELS_6);
  }

  public static iPlatformBorder getDefaultWidgetBorder() {
    if (widgetBorder == null) {
      UIColor c = Platform.getUIDefaults().getColor("Rare.Widget.borderColor");

      if (c == null) {
        c = UILineBorder.getDefaultLineColor().darker();
      }

      widgetBorder = new UILineBorder(c, ScreenUtils.PLATFORM_PIXELS_1, ScreenUtils.PLATFORM_PIXELS_6);
    }

    return widgetBorder;
  }

  public static UIColor[] getEtchedColors(UIColor c) {
    UIColor[] ca;

    if (c == null) {
      c = ColorUtils.getBackground();
    }

    ca    = new UIColor[2];
    ca[0] = c.brighter();
    ca[1] = c.darker();

    return ca;
  }

  public static iPlatformBorder getNewDefaultWidgetBorder() {
    UIColor c = Platform.getUIDefaults().getColor("Rare.Widget.borderColor");

    if (c == null) {
      c = UILineBorder.getDefaultLineColor().darker();
    }

    return new UILineBorder(c, ScreenUtils.PLATFORM_PIXELS_1, ScreenUtils.platformPixels(6));
  }

  public static iPlatformBorder getTextAreaBorder() {
    if (textareaBorder == null) {
      UIColor c = Platform.getUIDefaults().getColor("Rare.Widget.borderColor");

      if (c == null) {
        c = UILineBorder.getDefaultLineColor().darker();
      }

      UILineBorder b = new UILineBorder(c, ScreenUtils.PLATFORM_PIXELS_1, 0);

      b.setInsetsPadding(ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_2,
                         ScreenUtils.PLATFORM_PIXELS_2);
      textareaBorder = b;
    }

    return textareaBorder;
  }

  public static iPlatformBorder getTextFieldBorder() {
    if (textfieldBorder == null) {
      UIColor c = Platform.getUIDefaults().getColor("Rare.Widget.borderColor");

      if (c == null) {
        c = UILineBorder.getDefaultLineColor().darker();
      }

      UILineBorder b = new UILineBorder(c, ScreenUtils.PLATFORM_PIXELS_1, ScreenUtils.PLATFORM_PIXELS_6);

      b.setInsetsPadding(2, 2, 2, 2);
      textfieldBorder = b;
    }

    return textfieldBorder;
  }

  public static iPlatformBorder getToolbarPressedButtonBorder() {
    if (toolbarPressedButtonBorder == null) {
      UIColor c = Platform.getUIDefaults().getColor("Rare.PushButton.borderColor");

      if (c == null) {
        c = UILineBorder.getDefaultLineColor().darker();
      }

      UIBevelBorder b  = new UIBevelBorder(UIBevelBorder.LOWERED, false);
      UIColor[]     ca = getBevelColors(Platform.getContextRootViewer(), false);

      b.setColors(ca[0], ca[1]);

      UIInsets insets = Platform.getUIDefaults().getInsets("Rare.PushButton.borderInsets");

      if (insets != null) {
        b.setInsetsPadding(insets.top, insets.right, insets.bottom, insets.left);
      } else {
        int tb = ScreenUtils.platformPixels(Platform.isTouchDevice()
                ? 8
                : 4);

        b.setInsetsPadding(tb, tb, tb, tb);
      }

      toolbarPressedButtonBorder = b;
    }

    return toolbarPressedButtonBorder;
  }

  /**
   * Sets the component's border from the specified set of borders The specified
   * border/title combination may require the component to be embedded in
   * another component. If this is the case the new component is returned
   * otherwise the original component is returned.
   *
   * @param context
   *          the context
   * @param comp
   *          the component
   * @param borders
   *          the set of borders
   * @param title
   *          the component's title
   * @param tdisplay
   *          the title display option
   * @param margin
   *          the margin between the component and its border
   *
   * @return the passed in component or a new component that has the passed in
   *         component embedded in it
   */
  public static iPlatformComponent setBorderType(iWidget context, iPlatformComponent comp, SPOTSet borders,
          String title, int tdisplay, iPlatformBorder margin, boolean lockable) {
    iPlatformBorder    border    = createBorder(context, borders, comp.getBorder());
    iPlatformComponent panel     = comp;
    boolean            titleOnly = false;
    UITitledBorder     tb        = null;

    if (border == null) {
      border = panel.getBorder();
    }

    if (border instanceof UITitledBorder) {
      tb = (UITitledBorder) border;
    } else {
      iPlatformComponent c = context.getContainerComponent();

      tb = (UITitledBorder) comp.getClientProperty(TITLEBORDER_CONFIGURED_PROPERTY);

      if (c != comp) {
        comp.putClientProperty(TITLEBORDER_CONFIGURED_PROPERTY, tb);
      }
    }

    if ((border instanceof UIIconBorder) && ((UIIconBorder) border).getOverLayBorder() instanceof UITitledBorder) {
      tb = (UITitledBorder) ((UIIconBorder) border).getOverLayBorder();
    }

    if (margin != null) {
      if (border == null) {
        border = panel.getBorder();
      }

      if (border == null) {
        border = margin;
      } else {
        border = new UICompoundBorder(border, margin);
      }
    }

    if ((title != null) && (title.length() > 0) && (tdisplay != -1)
        && (tdisplay != Widget.CTitleLocation.not_displayed)) {
      if (tb != null) {
        tb.setTitle(title);
      } else {
        tb     = new UITitledBorder((border == null)
                                    ? EMPTY_BORDER
                                    : border, title);
        border = tb;

        iPlatformComponent c = context.getContainerComponent();
        Object             o = c.getClientProperty(FOCUSBORDER_CONFIGURED_PROPERTY);

        if (o != null) {
          panel.putClientProperty(FOCUSBORDER_CONFIGURED_PROPERTY, o);
        }
      }

      switch(tdisplay) {
        case Widget.CTitleLocation.auto :
          if (!(border instanceof UIEtchedBorder) &&!(border instanceof UITitledBorder)) {
            tb.setTitlePosition(UITitledBorder.TOP);
            tb.setTitleJustification(UITitledBorder.LEADING);
          }

          break;

        case Widget.CTitleLocation.frame_top_left :
          tb.setTitlePosition(UITitledBorder.TOP);
          tb.setTitleJustification(UITitledBorder.LEADING);

          break;

        case Widget.CTitleLocation.frame_top_center :
          tb.setTitlePosition(UITitledBorder.TOP);
          tb.setTitleJustification(UITitledBorder.CENTER);

          break;

        case Widget.CTitleLocation.frame_top_right :
          tb.setTitlePosition(UITitledBorder.TOP);
          tb.setTitleJustification(UITitledBorder.TRAILING);

          break;

        case Widget.CTitleLocation.top_left :
          tb.setTitlePosition(titleOnly
                              ? UITitledBorder.TOP
                              : UITitledBorder.ABOVE_TOP);
          tb.setTitleJustification(UITitledBorder.LEADING);

          break;

        case Widget.CTitleLocation.top_right :
          tb.setTitlePosition(titleOnly
                              ? UITitledBorder.TOP
                              : UITitledBorder.ABOVE_TOP);
          tb.setTitleJustification(UITitledBorder.TRAILING);

          break;

        case Widget.CTitleLocation.top_center :
          tb.setTitlePosition(titleOnly
                              ? UITitledBorder.TOP
                              : UITitledBorder.ABOVE_TOP);
          tb.setTitleJustification(UITitledBorder.CENTER);

          break;

        case Widget.CTitleLocation.bottom_left :
          tb.setTitlePosition(UITitledBorder.BELOW_BOTTOM);
          tb.setTitleJustification(UITitledBorder.LEADING);

          break;

        case Widget.CTitleLocation.bottom_center :
          tb.setTitlePosition(UITitledBorder.BELOW_BOTTOM);
          tb.setTitleJustification(UITitledBorder.CENTER);

          break;

        case Widget.CTitleLocation.bottom_right :
          tb.setTitlePosition(UITitledBorder.BELOW_BOTTOM);
          tb.setTitleJustification(UITitledBorder.TRAILING);

          break;

        case Widget.CTitleLocation.frame_bottom_left :
          tb.setTitlePosition(UITitledBorder.BOTTOM);
          tb.setTitleJustification(UITitledBorder.LEADING);

          break;

        case Widget.CTitleLocation.frame_bottom_center :
          tb.setTitlePosition(UITitledBorder.BOTTOM);
          tb.setTitleJustification(UITitledBorder.CENTER);

          break;

        case Widget.CTitleLocation.frame_bottom_right :
          tb.setTitlePosition(UITitledBorder.BOTTOM);
          tb.setTitleJustification(UITitledBorder.TRAILING);

          break;

        case Widget.CTitleLocation.inside_top_left :
          tb.setTitlePosition(UITitledBorder.BELOW_TOP);
          tb.setTitleJustification(UITitledBorder.LEADING);

          break;

        case Widget.CTitleLocation.inside_top_center :
          tb.setTitlePosition(UITitledBorder.BELOW_TOP);
          tb.setTitleJustification(UITitledBorder.CENTER);

          break;

        case Widget.CTitleLocation.inside_top_right :
          tb.setTitlePosition(UITitledBorder.BELOW_TOP);
          tb.setTitleJustification(UITitledBorder.TRAILING);

          break;

        case Widget.CTitleLocation.inside_bottom_left :
          tb.setTitlePosition(UITitledBorder.ABOVE_BOTTOM);
          tb.setTitleJustification(UITitledBorder.LEADING);

          break;

        case Widget.CTitleLocation.inside_bottom_center :
          tb.setTitlePosition(UITitledBorder.ABOVE_BOTTOM);
          tb.setTitleJustification(UITitledBorder.CENTER);

          break;

        case Widget.CTitleLocation.inside_bottom_right :
          tb.setTitlePosition(UITitledBorder.ABOVE_BOTTOM);
          tb.setTitleJustification(UITitledBorder.TRAILING);

          break;

        default :
          break;
      }
      // comp.setBorder(tb);
    }

    if (tb != null) {
      comp.putClientProperty(TITLEBORDER_CONFIGURED_PROPERTY, tb);
    }

    if (border != null) {
      panel = PlatformHelper.createBorderPanel(comp, border);
    }

    if ((border != null) && (panel.getBorder() != border)) {
      panel.setBorder(border);
    }

    return panel;
  }

  public static iPlatformComponent setBorderType(iWidget context, iPlatformComponent comp, Widget cfg, boolean title,
          boolean margin) {
    String          s        = null;
    int             tdisplay = -1;
    iPlatformBorder m        = null;

    if (title) {
      s        = cfg.title.getValue();
      tdisplay = cfg.titleLocation.intValue();
    }

    if (margin) {
      if (cfg.getContentPadding() != null) {
        if (comp instanceof XPContainer) {
          ((XPContainer) comp).setMargin(cfg.getContentPadding().getInsets());
        } else {
          m = new UIEmptyBorder(cfg.getContentPadding().getInsets());
        }
      }
    }

    return setBorderType(context, comp, cfg.getBorders(), s, tdisplay, m, false);
  }

  private static UITitledBorder createTitledBorder(iWidget context, Map<String, String> attrs, String color,
          float thickness, boolean always) {
    String  title = null;
    UIColor c     = null;

    if (attrs != null) {
      title = attrs.get("title");

      if (title != null) {
        title = context.expandString(title, false);
      }
    }

    if ((title == null) &&!always) {
      return null;
    }

    UITitledBorder border = new UITitledBorder((title == null)
            ? ""
            : title);

    if (attrs != null) {
      if (thickness == 0) {
        thickness = ScreenUtils.PLATFORM_PIXELS_1;
      }

      UITitledBorder b  = border;
      UILineBorder   lb = null;

      b.setTitleLocation(attrs.get("titleLocation"));

      if (color != null) {
        c = ColorUtils.getColor(color);

        if (c != null) {
          b.setBorder(lb = new UILineBorder(c, thickness, true));
        }
      }

      color = attrs.get("titleColor");

      if (color != null) {
        c = ColorUtils.getColor(color);

        if (c != null) {
          b.setTitleColor(c);
        }
      }

      String s = attrs.get("titleFont");
      UIFont f = FontUtils.parseFont(context, b.getTitleFont(), s);

      if (f != null) {
        b.setTitleFont(f);
      }

      s = attrs.get("cornerArc");

      if ((s != null) && (s.length() > 0)) {
        int n = SNumber.intValue(s);

        if (lb == null) {
          lb = new UILineBorder(UILineBorder.getDefaultLineColor(), thickness, true);
          b.setBorder(lb);
        }

        lb.setCornerArc(n);
      }

      s = attrs.get("style");

      if ((s != null) && (s.length() > 0)) {
        if (lb == null) {
          lb = new UILineBorder(UILineBorder.getDefaultLineColor(), thickness, true);
          b.setBorder(lb);
        }

        lb.setLineStyle(s);
      }

      s = attrs.get("padForArc");

      if ((s != null) &&!s.equals("true")) {
        if (lb == null) {
          lb = new UILineBorder(UILineBorder.getDefaultLineColor(), thickness, true);
          b.setBorder(lb);
        }

        lb.setPadForArc(false);
      }
    }

    return border;
  }

  private static UIColor[] getBevelColors(iWidget widget, boolean four) {
    return getBevelColors((widget == null)
                          ? null
                          : widget.getBackground(), four);
  }

  private static UIColor[] getEtchedColors(iWidget widget) {
    return getEtchedColors((widget == null)
                           ? null
                           : widget.getBackground());
  }
}
