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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.UIStroke.Cap;
import com.appnativa.rare.ui.UIStroke.Join;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UIBackgroundPainter;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iGradientPainter;
import com.appnativa.rare.ui.painter.iGradientPainter.Direction;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.iSPOTElement;

import com.google.j2objc.annotations.Weak;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PainterUtils {
  private static String                                            COMBOBOX_BUTTON_KEY      =
    "_Rare.ComboBox.button.painterHolder";
  private static String                                            COMBOBOX_KEY             =
    "_Rare.ComboBox.painterHolder";
  private static String                                            HYPERLINK_KEY            =
    "_Rare.PushButton.hyperlink.painterHolder";
  private static String                                            PUSHBUTTON_KEY           =
    "_Rare.PushButton.painterHolder";
  private static String                                            RED_HYPERLINK_KEY        =
    "_Rare.PushButton.hyperlink.redPainterHolder";
  private static String                                            SPINNER_BUTTON_KEY       =
    "_Rare.Spinner.button.painterHolder";
  private static String                                            TOGGLE_TOOLBARBUTTON_KEY =
    "_Rare.PushButton.toolbar.toggle.painterHolder";
  private static String                                            TOOLBARBUTTON_KEY        =
    "_Rare.PushButton.toolbar.painterHolder";
  protected static final HashMap<String, iComposite.CompositeType> compositeMap             =
    new HashMap<String, iComposite.CompositeType>();

  static {
    compositeMap.put("source-atop", iComposite.CompositeType.SRC_ATOP);
    compositeMap.put("source-in", iComposite.CompositeType.SRC_IN);
    compositeMap.put("source-out", iComposite.CompositeType.SRC_OUT);
    compositeMap.put("source-over", iComposite.CompositeType.SRC_OVER);
    compositeMap.put("destination-atop", iComposite.CompositeType.DST_ATOP);
    compositeMap.put("destination-in", iComposite.CompositeType.DST_IN);
    compositeMap.put("destination-out", iComposite.CompositeType.DST_OUT);
    compositeMap.put("destination-over", iComposite.CompositeType.DST_OVER);
    compositeMap.put("lighter", iComposite.CompositeType.LIGHTEN);
    compositeMap.put("darker", iComposite.CompositeType.DARKEN);
    compositeMap.put("xor", iComposite.CompositeType.XOR);
    compositeMap.put("copy", iComposite.CompositeType.COPY);
  }

  private PainterUtils() {}

  public static iPlatformComponentPainter createProgressPopupPainter() {
    Object o = Platform.getAppContext().getUIDefaults().get("Rare.ProgressPopup.background");

    if (o instanceof PaintBucket) {
      return ((PaintBucket) o).getCachedComponentPainter();
    }

    iBackgroundPainter bp =
      Platform.getAppContext().getUIDefaults().getBackgroundPainter("Rare.ProgressPopup.background");

    if (bp == null) {
      bp = new UISimpleBackgroundPainter(ColorUtils.getBackground());
    }

    UICompoundBorder b = new UICompoundBorder();

    b.setInsideBorder(new UIEmptyBorder(ScreenUtils.PLATFORM_PIXELS_16, ScreenUtils.PLATFORM_PIXELS_8,
            ScreenUtils.PLATFORM_PIXELS_16, ScreenUtils.PLATFORM_PIXELS_8));
    b.setOutsideBorder(new UILineBorder(UILineBorder.getDefaultLineColor(), ScreenUtils.PLATFORM_PIXELS_2,
            ScreenUtils.PLATFORM_PIXELS_4, ScreenUtils.PLATFORM_PIXELS_4));

    UIComponentPainter cp = new UIComponentPainter();

    cp.setBackgroundPainter(bp, false);
    cp.setBorder(b);

    return cp;
  }

  public static PainterHolder createButtonPaintHolder() {
    PainterHolder ph = (PainterHolder) Platform.getUIDefaults().get(PUSHBUTTON_KEY);

    if (ph == null) {
      ph = PainterHolder.create("Rare.PushButton.", false, true, false);

      if (ph.disabledForegroundColor == null) {
        ph.disabledForegroundColor = ColorUtils.getDisabledForeground();
      }

      if (ph.normalPainter == null) {
        ph.normalPainter = getUnpressedButtonPaintBucket();
        ph.normalPainter.setBorder(BorderUtils.getDefaultButtonBorder());
      }

      if ((ph.pressedPainter == null) && (ph.selectedPainter == null)) {
        ph.pressedPainter = getPressedButtonPaintBucket();
      }

      Platform.getUIDefaults().put(PUSHBUTTON_KEY, ph);
      ph.setShared(true);
    }

    return ph;
  }

  public static PainterHolder createComboBoxButtonPaintHolder() {
    PainterHolder ph = (PainterHolder) Platform.getUIDefaults().get(COMBOBOX_BUTTON_KEY);

    if (ph == null) {
      ph = PainterHolder.create("Rare.ComboBox.button.", false, true, false);

      if (ph.disabledForegroundColor == null) {
        ph.disabledForegroundColor = ColorUtils.getDisabledForeground();
      }

      if (ph.normalPainter == null) {
        ph.normalPainter  = getUnpressedButtonPaintBucket();
        ph.pressedPainter = getPressedButtonPaintBucket();
      }

      Platform.getUIDefaults().put(COMBOBOX_BUTTON_KEY, ph);
      ph.setShared(true);
    }

    return ph;
  }

  public static PainterHolder createComboBoxPaintHolder() {
    PainterHolder ph = (PainterHolder) Platform.getUIDefaults().get(COMBOBOX_KEY);

    if (ph == null) {
      ph = PainterHolder.create("Rare.ComboBox.", false, true, false);

      if (ph.disabledForegroundColor == null) {
        ph.disabledForegroundColor = ColorUtils.getDisabledForeground();
      }

      if (ph.normalPainter == null) {
        ph.normalPainter =
          new PaintBucket((UIBackgroundPainter) UIBackgroundPainter.BGCOLOR_GRADIENT_PAINTER_MID.clone());
      }

      Platform.getUIDefaults().put(COMBOBOX_KEY, ph);
      ph.setShared(true);
    }

    return ph;
  }

  public static PainterHolder createHyperlinkPaintHolder() {
    PainterHolder ph = (PainterHolder) Platform.getUIDefaults().get(HYPERLINK_KEY);

    if (ph == null) {
      ph = PainterHolder.create("Rare.PushButton.hyperlink.", false, true, false);

      if (ph.disabledForegroundColor == null) {
        ph.disabledForegroundColor = ColorUtils.getDisabledForeground();
      }

      if (ph.normalPainter == null) {
        ph.normalPainter = new PaintBucket();

        int size = ScreenUtils.platformPixels(Platform.isTouchDevice()
                ? 8
                : 4);

        ph.normalPainter.setBorder(new UIEmptyBorder(size));
      }

      if (ph.foregroundColor == null) {
        UIColor c = Platform.getUIDefaults().getColor("Rare.PushButton.hyperlinkColor");

        if (c == null) {
          c = ColorUtils.getForeground();
        }

        ph.foregroundColor = c;
      }

      if (ph.pressedPainter == null) {
        ph.pressedPainter = new PaintBucket();
        ph.pressedPainter.setBorder(ph.normalPainter.getBorder());
        ph.pressedPainter.setForegroundColor(ColorUtils.getPressedVersion(ph.foregroundColor));
      }

      Platform.getUIDefaults().put(HYPERLINK_KEY, ph);
      ph.setShared(true);
    }

    return ph;
  }

  public static PainterHolder createPaintHolderFromAttributes(iWidget context, Map attrs) {
    if ((attrs == null) || attrs.isEmpty()) {
      return null;
    }

    PaintBucket   pb    = null;
    PaintBucket   ppb   = null;
    PaintBucket   rpb   = null;
    PaintBucket   dpb   = null;
    iPlatformIcon icon  = null;
    iPlatformIcon ricon = null;
    iPlatformIcon dicon = null;
    iPlatformIcon picon = null;
    iPlatformIcon sicon = null;
    UIColor       fg    = null;
    UIColor       dfg   = null;
    String        s     = (String) attrs.get("bgColor");

    if (s != null) {
      pb = ColorUtils.getPaintBucket(s);
    }

    s = (String) attrs.get("pressedBgColor");

    if (s != null) {
      ppb = ColorUtils.getPaintBucket(s);
    }

    s = (String) attrs.get("rolloverBgColor");

    if (s != null) {
      rpb = ColorUtils.getPaintBucket(s);
    }

    s = (String) attrs.get("disabledBgColor");

    if (s != null) {
      dpb = ColorUtils.getPaintBucket(s);
    }

    s = (String) attrs.get("Icon");

    if (s != null) {
      icon = context.getIcon(s, null);
    }

    s = (String) attrs.get("pressedIcon");

    if (s != null) {
      picon = context.getIcon(s, null);
    }

    s = (String) attrs.get("rolloverIcon");

    if (s != null) {
      ricon = context.getIcon(s, null);
    }

    s = (String) attrs.get("disabledIcon");

    if (s != null) {
      dicon = context.getIcon(s, null);
    }

    s = (String) attrs.get("selectedIcon");

    if (s != null) {
      sicon = context.getIcon(s, null);
    }

    s = (String) attrs.get("disabledFgColor");

    if (s != null) {
      dfg = ColorUtils.getColor(s);
    }

    s = (String) attrs.get("fgColor");

    if (s != null) {
      fg = ColorUtils.getColor(s);
    }

    if ((fg == null) && (dfg == null) && (pb == null) && (ppb == null) && (rpb == null) && (dpb == null)
        && (icon == null) && (sicon == null) && (ricon == null) && (dicon == null) && (picon == null)) {
      return null;
    }

    PainterHolder ph = new PainterHolder(icon, sicon, ricon, picon, dicon);

    ph.normalPainter           = pb;
    ph.pressedPainter          = ppb;
    ph.rolloverPainter         = rpb;
    ph.disabledPainter         = dpb;
    ph.foregroundColor         = fg;
    ph.disabledForegroundColor = dfg;

    return ph;
  }

  public static PainterHolder createPaintHolderFromSequence(iWidget context, SPOTSequence seq) {
    PaintBucket   pb    = null;
    PaintBucket   ppb   = null;
    PaintBucket   rpb   = null;
    PaintBucket   dpb   = null;
    iPlatformIcon icon  = null;
    iPlatformIcon ricon = null;
    iPlatformIcon dicon = null;
    iPlatformIcon picon = null;
    iPlatformIcon sicon = null;
    UIColor       fg    = null;
    UIColor       dfg   = null;
    iSPOTElement  s     = seq.spot_elementFor("bgColor");

    if (s instanceof SPOTPrintableString) {
      pb = ColorUtils.getPaintBucket((SPOTPrintableString) s);
    }

    s = seq.spot_elementFor("pressedBgColor");

    if (s instanceof SPOTPrintableString) {
      ppb = ColorUtils.getPaintBucket((SPOTPrintableString) s);
    }

    s = seq.spot_elementFor("rolloverBgColor");

    if (s instanceof SPOTPrintableString) {
      rpb = ColorUtils.getPaintBucket((SPOTPrintableString) s);
    }

    s = seq.spot_elementFor("disabledBgColor");

    if (s instanceof SPOTPrintableString) {
      dpb = ColorUtils.getPaintBucket((SPOTPrintableString) s);
    }

    s = seq.spot_elementFor("icon");

    if (s instanceof SPOTPrintableString) {
      icon = context.getIcon((SPOTPrintableString) s);
    }

    s = seq.spot_elementFor("pressedIcon");

    if (s instanceof SPOTPrintableString) {
      picon = context.getIcon((SPOTPrintableString) s);
    }

    s = seq.spot_elementFor("rolloverIcon");

    if (s instanceof SPOTPrintableString) {
      ricon = context.getIcon((SPOTPrintableString) s);
    }

    s = seq.spot_elementFor("disabledIcon");

    if (s instanceof SPOTPrintableString) {
      dicon = context.getIcon((SPOTPrintableString) s);
    }

    s = seq.spot_elementFor("selectedIcon");

    if (s instanceof SPOTPrintableString) {
      sicon = context.getIcon((SPOTPrintableString) s);
    }

    s = seq.spot_elementFor("disabledFgColor");

    if (s instanceof SPOTPrintableString) {
      dfg = ColorUtils.getColor(((SPOTPrintableString) s).stringValue());
    }

    s = seq.spot_elementFor("fgColor");

    if (s instanceof SPOTPrintableString) {
      fg = ColorUtils.getColor(((SPOTPrintableString) s).stringValue());
    }

    if ((fg == null) && (dfg == null) && (pb == null) && (ppb == null) && (rpb == null) && (dpb == null)
        && (icon == null) && (sicon != null) && (ricon == null) && (dicon == null) && (picon == null)) {
      return null;
    }

    PainterHolder ph = new PainterHolder(icon, sicon, ricon, picon, dicon);

    ph.normalPainter           = pb;
    ph.pressedPainter          = ppb;
    ph.rolloverPainter         = rpb;
    ph.disabledPainter         = dpb;
    ph.foregroundColor         = fg;
    ph.disabledForegroundColor = dfg;

    return ph;
  }

  public static PainterHolder createRedHyperlinkPainterHolder() {
    PainterHolder ph = (PainterHolder) Platform.getUIDefaults().get(RED_HYPERLINK_KEY);

    if (ph == null) {
      ph = new PainterHolder();
      ph.setForegroundColor(UIColor.RED);
      ph.setDisabledForegroundColor(ColorUtils.getDisabledVersion(UIColor.RED));

      PaintBucket pp = new PaintBucket();

      pp.setForegroundColor(ColorUtils.getPressedVersion(UIColor.RED));
      ph.setPressedPainter(pp);
      Platform.getUIDefaults().put(RED_HYPERLINK_KEY, ph);
    }

    return ph;
  }

  public static PainterHolder createSpinnerButtonPaintHolder() {
    PainterHolder ph = (PainterHolder) Platform.getUIDefaults().get(SPINNER_BUTTON_KEY);

    if (ph == null) {
      ph = PainterHolder.create("Rare.Spinner.button.", false, false, false);

      if (ph.foregroundColor == null) {
        ph.foregroundColor = ColorUtils.getForeground();
      }

      if (ph.disabledForegroundColor == null) {
        ph.disabledForegroundColor = ColorUtils.getDisabledForeground();
      }

      if (ph.normalPainter == null) {
        ph.normalPainter  = ColorUtils.getPaintBucket("Rare.background,Rare.backgroundDkShadow");
        ph.pressedPainter = getPressedButtonPaintBucket();
      }

      Platform.getUIDefaults().put(SPINNER_BUTTON_KEY, ph);
      ph.setShared(true);
    }

    return ph;
  }

  public static PaintBucket getPressedButtonPaintBucket() {
    PaintBucket pb = Platform.getUIDefaults().getPaintBucket("Rare.PushButton.pressedPainter");

    if (pb != null) {
      return pb;
    }

    return ColorUtils.getPaintBucket("Rare.backgroundDkShadow,Rare.background");
  }

  public static PaintBucket getUnpressedButtonPaintBucket() {
    PaintBucket pb = Platform.getUIDefaults().getPaintBucket("Rare.PushButton.painter");

    if (pb != null) {
      return pb;
    }

    return ColorUtils.getPaintBucket("Rare.background,Rare.backgroundDkShadow");
  }

  public static PainterHolder createToggleToolBarButtonPaintHolder() {
    PainterHolder ph = (PainterHolder) Platform.getUIDefaults().get(TOGGLE_TOOLBARBUTTON_KEY);

    if (ph == null) {
      ph = PainterHolder.create("Rare.PushButton.toolbar.toggle.", false, false, false);

      if (ph == null) {
        ph = createToolBarButtonPaintHolder();
      } else {
        if (ph.foregroundColor == null) {
          ph.foregroundColor = ColorUtils.getForeground();
        }

        if (ph.disabledForegroundColor == null) {
          ph.disabledForegroundColor = ColorUtils.getDisabledForeground();
        }

        if (ph.pressedPainter == null) {
          ph.pressedPainter = getPressedButtonPaintBucket();
          ph.pressedPainter.setBorder(BorderUtils.getToolbarPressedButtonBorder());

          if (ph.selectedPainter == null) {
            ph.selectedPainter = ColorUtils.getPaintBucket("Rare.backgroundDkShadow");
            ;
            ph.selectedPainter.setBorder(ph.pressedPainter.getBorder());
          }
        }

        if (ph.normalPainter == null) {
          ph.normalPainter = new PaintBucket();
        }

        if (ph.normalPainter.getBorder() == null) {
          UIInsets insets = ph.pressedPainter.getBorder().getBorderInsets(new UIInsets());

          ph.normalPainter.setBorder(new UIEmptyBorder(insets));
        }
      }

      Platform.getUIDefaults().put(TOGGLE_TOOLBARBUTTON_KEY, ph);
      ph.setShared(true);
    }

    return ph;
  }

  public static PainterHolder createToolBarButtonPaintHolder() {
    PainterHolder ph = (PainterHolder) Platform.getUIDefaults().get(TOOLBARBUTTON_KEY);

    if (ph == null) {
      ph = PainterHolder.create("Rare.PushButton.toolbar.", false, false, false);

      if (ph.disabledForegroundColor == null) {
        ph.disabledForegroundColor = ColorUtils.getDisabledForeground();
      }

      if (ph.rolloverPainter == null) {
        ph.rolloverPainter = ColorUtils.getPaintBucket("Rare.background,Rare.backgroundDkShadow");
        ph.rolloverPainter.setBorder(BorderUtils.getToolbarPressedButtonBorder());
      }

      if (ph.pressedPainter == null) {
        ph.pressedPainter = ph.rolloverPainter;
      }

      if (ph.normalPainter == null) {
        ph.normalPainter = new PaintBucket();

        iPlatformBorder b = ph.pressedPainter.getBorder();

        if (b == null) {
          int size = ScreenUtils.platformPixels(Platform.isTouchDevice()
                  ? 8
                  : 4);

          ph.normalPainter.setBorder(new UIEmptyBorder(size));
        } else {
          ph.normalPainter.setBorder(new UIEmptyBorder(b.getBorderInsets((UIInsets) null)));
        }
      }

      Platform.getUIDefaults().put(TOOLBARBUTTON_KEY, ph);
      ph.setShared(true);
    }

    return ph;
  }

  public static iPlatformPath drawArrow(iPlatformPath p, float width, float height, boolean down) {
    if (p == null) {
      p = PlatformHelper.createPath();
    } else {
      p.reset();
    }

    float y   = 0;
    float x   = 0;
    float w   = (width / 2);
    float len = w / 2;

    x += len - 1;
    y += Math.floor((height / 2) - (len / 2));

    if (down) {
      p.moveTo(x, y);
      p.lineTo(x + w, y);
      p.lineTo(x + len, y + len);
      p.lineTo(x, y);
    } else {
      p.moveTo(x + len, y);
      p.lineTo(x + w, y + len);
      p.lineTo(x, y + len);
      p.lineTo(x + len, y);
    }

    return p;
  }

  public static iPlatformPath drawTwisty(iPlatformPath p, float width, float height, boolean down) {
    if (p == null) {
      p = PlatformHelper.createPath();
    } else {
      p.reset();
    }

    float w   = down
                ? (width / 2)
                : (width / 1.5f);
    float len = w / 2;

    if (down) {
      float x = 2;
      float y = 2;

      p.moveTo(x, y + w);
      p.lineTo(x + w, y + w);
      p.lineTo(x + w, y);
      p.lineTo(x, y + w);
    } else {
      float x = 2;
      float y = (float) Math.floor((height - w) / 2);

      p.moveTo(x, y);
      p.lineTo(x + len, y + len);
      p.lineTo(x, y + w);
      p.lineTo(x, y);
    }

    return p;
  }

  public static void setPopupListRenderingDefaults(iListHandler lh, aListItemRenderer renderer, boolean forMenu) {
    iPlatformComponent list = lh.getListComponent();
    UIProperties       p    = Platform.getUIDefaults();
    UIColor            c    = p.getColor("Rare.ComboBox.list.selectedForeground");
    PaintBucket        pb   = p.getPaintBucket("Rare.ComboBox.list.selectedBackground");

    if ((c != null) || (pb != null)) {
      if (pb == null) {
        pb = (PaintBucket) Platform.getAppContext().getSelectionPainter().clone();
        pb.setForegroundColor(c);
      }

      renderer.setSelectionPaint(pb);
    }

    c = p.getColor("Rare.ComboBox.list.foreground");

    if (c == null) {
      c = ColorUtils.getListForeground();
    }

    list.setForeground(c);
    c = p.getColor("Rare.ComboBox.list.disabledForeground");

    if (c != null) {
      c = ColorUtils.getListDisabledForeground();
    }

    if (c == null) {
      c = ColorUtils.getDisabledForeground();
    }

    list.setDisabledColor(c);
    c = p.getColor("Rare.ComboBox.list.background");

    if (c == null) {
      c = ColorUtils.getListBackground();
    }

    lh.getContainerComponent().setBackground(c);
    c = null;

    float d = ScreenUtils.PLATFORM_PIXELS_2;

    renderer.setInsets(new UIInsets(d, d, d, ScreenUtils.PLATFORM_PIXELS_4));

    boolean showDivider = Platform.getUIDefaults().getBoolean("Rare.ComboBox.list.showDivider",
                            Platform.isTouchableDevice() || forMenu);

    if (showDivider) {
      if (forMenu) {
        c = Platform.getUIDefaults().getColor("Rare.PopupMenu.list.dividerLineColor");
      }

      if (c == null) {
        c = Platform.getUIDefaults().getColor("Rare.ComboBox.list.dividerLineColor");
      }

      if (c == null) {
        c = ColorUtils.getListDividerColor();
      }

      lh.setShowDivider(true);
      lh.setDividerLine(c, UIStroke.SOLID_STROKE);
    } else {
      lh.setShowDivider(false);
    }

    lh.setSingleClickAction(true);
    lh.setAutoHilight(true);
    lh.setRowHeight(ScreenUtils.toPlatformPixelHeight(PlatformHelper.getDefaultRowHeight(), list, 100));
  }

  public static iComposite getComposite(String type, float alpha) {
    type = type.toLowerCase(Locale.US);

    iComposite.CompositeType ct = compositeMap.get(type);

    if (ct == null) {
      String s = type.toUpperCase(Locale.US);

      try {
        ct = iComposite.CompositeType.valueOf(s);
      } catch(Throwable ignore) {}
    }

    if (ct == null) {
      throw new IllegalArgumentException(type);
    }

    return new GraphicsComposite(type, ct, alpha);
  }

  public static iComposite.CompositeType getCompositeType(String type) {
    type = type.toLowerCase(Locale.US);

    return compositeMap.get(type);
  }

  public static class ChevronIcon extends aPlatformIcon {
    int                          iconSize = UIScreen.platformPixels(24);
    protected iPlatformPath      circle;
    protected UIColor            circleColor;
    protected iPlatformPainter   circlePaint;
    protected UIColor            dcc;
    protected boolean            horizontal;
    protected iPlatformComponent owner;
    protected boolean            up;
    private UIColor              chevronColor;
    private UIColor              disabledChevronColor;
    private UIColor              disabledCircleColor;

    /**
     * Constructs a new instance
     *
     * @param up
     *          {@inheritDoc}
     */
    public ChevronIcon(iPlatformComponent owner, boolean up) {
      this.up = up;
    }

    @Override
    public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
      if (circleColor == null) {
        circleColor          = Platform.getUIDefaults().getColor("Rare.ChevronIcon.circleColor");
        disabledCircleColor  = Platform.getUIDefaults().getColor("Rare.ChevronIcon.disabledCircleColor");
        chevronColor         = Platform.getUIDefaults().getColor("Rare.ChevronIcon.color");
        disabledChevronColor = Platform.getUIDefaults().getColor("Rare.ChevronIcon.disabledColor");

        if (circleColor == null) {
          circleColor = UILineBorder.getDefaultLineColor();
        }

        if (disabledCircleColor == null) {
          disabledCircleColor = UILineBorder.getDefaultDisabledColor();
        }

        if (chevronColor == null) {
          chevronColor = ColorUtils.getForeground();
        }
      }

      float size = iconSize - ScreenUtils.PLATFORM_PIXELS_4;

      y += ScreenUtils.PLATFORM_PIXELS_1;
      x += ScreenUtils.PLATFORM_PIXELS_1;

      if (circlePaint == null) {
        UIBackgroundPainter bp = new UIBackgroundPainter(ColorUtils.getBackground(), circleColor, Direction.CENTER);

        bp.setGradientType(iGradientPainter.Type.RADIAL);
        // bp.setBackgroundColor(circleColor);
        circlePaint = bp;
      }

      float          sw    = g.getStrokeWidth();
      UIColor        c     = g.getColor();
      iPlatformPaint paint = g.getPaint();

      g.setPaint(circlePaint.getPaint(size, size));
      g.fillRoundRect(x, y, size, size, size, size);

      UIColor cc;

      if ((getOwner() == null) || getOwner().isEnabled()) {
        g.setColor(circleColor);
        cc = chevronColor;
      } else {
        g.setColor(disabledCircleColor);
        cc = disabledChevronColor;
      }

      g.setStrokeWidth(ScreenUtils.platformPixelsf(1.5f));
      g.drawRoundRect(x, y, size, size, size, size);
      g.setColor(cc);

      float f  = Math.max(1, size / ScreenUtils.PLATFORM_PIXELS_16);
      float n3 = ScreenUtils.PLATFORM_PIXELS_3 * f;
      float n4 = ScreenUtils.PLATFORM_PIXELS_4 * f;
      float n6 = ScreenUtils.PLATFORM_PIXELS_6 * f;

      if (horizontal) {
        x += n4 + ScreenUtils.PLATFORM_PIXELS_1;
        y += n4;

        if (up) {
          g.drawLine(x + n3, y, x, y + n3);
          g.drawLine(x + n3, y, x + n3, y + n6);
          x += n4;
          g.drawLine(x + n3, y, x, y + n3);
          g.drawLine(x + n3, y, x + n3, y + n6);
        } else {
          g.drawLine(x, y, x + n3, y + n3);
          g.drawLine(x + n3, y + n3, x, y + n6);
          x += n4;
          g.drawLine(x, y, x + n3, y + n3);
          g.drawLine(x + n3, y + n3, x, y + n6);
        }
      } else {
        x += n4 + ScreenUtils.PLATFORM_PIXELS_1;
        y += n4;

        if (up) {
          g.drawLine(x + n3, y, x, y + n3);
          g.drawLine(x + n3, y, x + n6, y + n3);
          y += n4;
          g.drawLine(x + n3, y, x, y + n3);
          g.drawLine(x + n3, y, x + n6, y + n3);
        } else {
          g.drawLine(x, y, x + n3, y + n3);
          g.drawLine(x + n3, y + n3, x + n6, y);
          y += n4;
          g.drawLine(x, y, x + n3, y + n3);
          g.drawLine(x + n3, y + n3, x + n6, y);
        }
      }

      g.setStrokeWidth(sw);
      g.setColor(c);
      g.setPaint(paint);
    }

    public void setDirection(boolean up) {
      this.up = up;
    }

    public void setIconSize(int size) {
      int n = UIScreen.platformPixels(24);

      iconSize = Math.max(n, size);
    }

    public void setOwner(iPlatformComponent owner) {
      this.owner = owner;
    }

    @Override
    public iPlatformIcon getDisabledVersion() {
      return this;
    }

    @Override
    public int getIconHeight() {
      return iconSize;
    }

    @Override
    public int getIconWidth() {
      return iconSize;
    }

    public iPlatformComponent getOwner() {
      return owner;
    }

    protected void setHorizontal(boolean horizontal) {
      this.horizontal = horizontal;
    }

    protected boolean isHorizontal() {
      return horizontal;
    }
  }


  public static class GripperIcon extends aPlatformIcon {
    protected int     iconWidth  = ScreenUtils.platformPixels(16);
    protected int     iconHeight = ScreenUtils.platformPixels(16);
    protected UIColor colorOne;
    protected UIColor colorTwo;
    protected boolean vertical;

    public GripperIcon(boolean vertical) {
      this(vertical, Platform.getUIDefaults().getColor("Rare.backgroundShadow"),
           Platform.getUIDefaults().getColor("Rare.backgroundLtShadow"));
    }

    public GripperIcon(boolean vertical, UIColor fg) {
      this.vertical = vertical;
      setColors(new UIColorShade(fg, (255 * -25) / 100), new UIColorShade(fg, (255 * -15) / 100));
    }

    public GripperIcon(boolean vertical, UIColor colorOne, UIColor colorTwo) {
      this.vertical = vertical;
      setColors(colorOne, colorTwo);
    }

    @Override
    public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
      float pm     = ScreenUtils.getPixelMultiplier();
      float h      = vertical
                     ? height
                     : width;
      int   four   = (int) Math.max(4, (4 * (pm / 1.5f)));
      int   length = (int) Math.min(9 * pm, (h - 6) / four) * 2;
      int   lines  = 3;

      if (vertical) {
        while(lines * four > (width + 1)) {
          lines--;
        }

        y += (height - length) / 2;
        x = (int) Math.ceil(width / 2);

        if (lines == 3) {
          x -= four;
        } else if (lines == 2) {
          x -= (four / 2);
        }
      } else {
        while(lines * four > (width + 1)) {
          lines--;
        }

        x += (width - length) / 2;
        y = (int) Math.ceil(height / 2);

        if (lines == 3) {
          y -= four;
        } else if (lines == 2) {
          y -= (four / 2);
        }
      }

      for (int i = 0; i < lines; i++) {
        while(lines * four > (height + 1)) {
          lines--;
        }

        if (vertical) {
          g.setColor(colorOne);
          g.drawLine(x, y, x, y + length);
          g.setColor(colorTwo);
          g.drawLine(x + 1, y, x + 1, y + length);
          x += four;
        } else {
          g.setColor(colorOne);
          g.drawLine(x, y, x + length, y);
          g.setColor(colorTwo);
          g.drawLine(x, y + 1, x + length, y + 1);
          y += four;
        }
      }
    }

    public void setColors(UIColor colorOne, UIColor colorTwo) {
      this.colorOne = colorOne;
      this.colorTwo = colorTwo;
    }

    public void setSize(int width, int height) {
      iconWidth  = width;
      iconHeight = height;
    }

    public void setVertical(boolean vertical) {
      this.vertical = vertical;
    }

    @Override
    public iPlatformIcon getDisabledVersion() {
      return null;
    }

    @Override
    public int getIconHeight() {
      return iconHeight;
    }

    @Override
    public int getIconWidth() {
      return iconWidth;
    }

    public boolean isVertical() {
      return vertical;
    }
  }


  public static class ListEditorIcon extends aPlatformIcon {
    protected static UIStroke checkmarkStroke = new UIStroke(ScreenUtils.PLATFORM_PIXELS_2, Cap.ROUND, Join.MITER);
    protected static UIStroke borderStroke    = new UIStroke(ScreenUtils.PLATFORM_PIXELS_1);
    protected UIColor         borderColor;
    protected UIColor         checkMarkColor;
    protected float           checkMarkSize;
    protected boolean         checked;
    protected UIColor         checkedBorderColor;
    protected UIColor         checkedFillColor;
    protected float           circleSize;
    protected UIColor         fillColor;
    protected float           iconSize;

    public ListEditorIcon(boolean checked) {
      this.checked = checked;
      iconSize     = UIScreen.platformPixels(24);
      setFillColor(Platform.getUIDefaults().getColor("Rare.List.EditIcon.fillColor"));
      checkMarkColor     = Platform.getUIDefaults().getColor("Rare.List.EditIcon.checkMarkColor");
      borderColor        = Platform.getUIDefaults().getColor("Rare.List.EditIcon.borderColor");
      checkedBorderColor = Platform.getUIDefaults().getColor("Rare.List.EditIcon.borderColor");
      checkedFillColor   = Platform.getUIDefaults().getColor("Rare.List.EditIcon.checkedFillColor");

      if (borderColor == null) {
        borderColor = UIColor.GRAY;
      }

      if (checkedBorderColor == null) {
        checkedBorderColor = borderColor;
      }

      if (borderColor.getAlpha() == 0) {
        borderColor = null;
      }

      if (checkedBorderColor.getAlpha() == 0) {
        checkedBorderColor = null;
      }

      if (checkMarkColor == null) {
        checkMarkColor = borderColor;

        if (checkMarkColor == null) {
          checkMarkColor = ColorUtils.getListForeground();
        }
      }

      float size = iconSize - ScreenUtils.PLATFORM_PIXELS_4;

      circleSize = size;

      if (checkedBorderColor == null) {
        checkMarkSize = size;
      } else {
        checkMarkSize = (int) Math.floor(Math.sqrt((size * size) / 2));

        if (checkMarkSize % 2 == 1) {
          checkMarkSize--;
        }
      }
    }

    @Override
    public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
      UIStroke s = g.getStroke();
      UIColor  c = g.getColor();

      g.translate(x, y);

      if (borderColor != null) {
        drawCircle(g);
      }

      if (checked) {
        drawCheckmark(g);
      }

      g.setColor(c);
      g.setStroke(s);
      g.translate(-x, -y);
    }

    public void setBorderColor(UIColor borderColor) {
      this.borderColor = borderColor;
    }

    public void setCheckMarkColor(UIColor checkMarkColor) {
      this.checkMarkColor = checkMarkColor;
    }

    public void setChecked(boolean checked) {
      this.checked = checked;
    }

    public void setCheckedBorderColor(UIColor checkedBorderColor) {
      this.checkedBorderColor = checkedBorderColor;
    }

    public void setCheckedFillColor(UIColor checkedFillColor) {
      this.checkedFillColor = checkedFillColor;
    }

    public void setFillColor(UIColor fillColor) {
      this.fillColor = fillColor;
    }

    public UIColor getBorderColor() {
      return borderColor;
    }

    public UIColor getCheckMarkColor() {
      return checkMarkColor;
    }

    public UIColor getCheckedBorderColor() {
      return checkedBorderColor;
    }

    public UIColor getCheckedFillColor() {
      return checkedFillColor;
    }

    @Override
    public iPlatformIcon getDisabledVersion() {
      return this;
    }

    public UIColor getFillColor() {
      return fillColor;
    }

    @Override
    public int getIconHeight() {
      return (int) iconSize;
    }

    @Override
    public int getIconWidth() {
      return (int) iconSize;
    }

    protected void drawCheckmark(iPlatformGraphics g) {
      float size = checkMarkSize;
      float p1   = ScreenUtils.PLATFORM_PIXELS_1;
      float d2   = iconSize - size;
      float loc  = d2 / 2;

      if (checkMarkSize == circleSize) {
        d2 = (iconSize - size) * 2;
      } else {
        d2 = circleSize - size;
      }

      float d = d2 / 2 - p1;

      g.translate(loc, loc);

      int h3 = (int) Math.ceil(size / 3f);

      g.setStroke(checkmarkStroke);
      g.setColor(checkMarkColor);
      g.drawLine(d, d2, h3 + p1, size - h3 + p1);
      g.drawLine(h3 + p1, size - h3 + p1, size - d - p1, h3);
      g.translate(-loc, -loc);
    }

    protected void drawCircle(iPlatformGraphics g) {
      float size = circleSize;
      float d    = (iconSize - size) / 2;

      g.setStroke(borderStroke);

      float   p1 = ScreenUtils.PLATFORM_PIXELS_1;
      UIColor cc = checked
                   ? checkedFillColor
                   : fillColor;

      if (cc != null) {
        iPlatformPainter p = ColorUtils.getPainter(cc);

        if (p == null) {
          g.setColor(cc);
        } else {
          g.setPaint(p.getPaint(size, size));
        }

        g.fillRoundRect(d + p1, d + p1, size - p1, size - p1, size - p1, size - p1);
      }

      UIColor bc = checked
                   ? checkedBorderColor
                   : borderColor;

      if (bc != null) {
        g.setColor(bc);
        g.drawRoundRect(d, d, size, size, size, size);
      }
    }
  }


  /**
   *
   *
   * @version 0.3, 2007-05-14
   * @author Don DeCoteau
   */
  public static class TwistyIcon extends aPlatformIcon {
    static UIColor borderColor;
    static UIColor collapsedColor;
    static UIColor disabledColor;
    static UIColor expandedColor;

    /**  */
    boolean            up       = true;
    int                iconSize = UIScreen.platformPixels(Platform.isTouchDevice()
            ? 16
            : 8);
    @Weak
    iPlatformComponent owner;
    iPlatformPath      path;

    /**
     * Constructs a new instance
     *
     * @param up
     *          {@inheritDoc}
     */
    public TwistyIcon(iPlatformComponent owner, boolean up) {
      this.up    = up;
      this.owner = owner;

      if (expandedColor == null) {
        expandedColor  = Platform.getUIDefaults().getColor("Rare.TwistyIcon.expandedColor");
        collapsedColor = Platform.getUIDefaults().getColor("Rare.TwistyIcon.collapsedColor");
        borderColor    = Platform.getUIDefaults().getColor("Rare.TwistyIcon.borderColor");
        disabledColor  = Platform.getUIDefaults().getColor("Rare.TwistyIcon.disabledColor");

        if (collapsedColor == null) {
          collapsedColor = UIColor.WHITE;
        }

        if (expandedColor == null) {
          expandedColor = UIColor.DARKGRAY;
        }

        if (borderColor == null) {
          borderColor = UIColor.GRAY;
        }

        if (disabledColor == null) {
          disabledColor = UIColor.LIGHTGRAY;
        }
      }
    }

    @Override
    public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
      float         sw   = g.getStrokeWidth();
      UIColor       c    = g.getColor();
      float         size = iconSize;
      iPlatformPath p    = drawTwisty(path, size, size, !up);

      path = p;

      if ((owner == null) || owner.isEnabled()) {
        g.setColor(up
                   ? collapsedColor
                   : expandedColor);
      } else {
        g.setColor(disabledColor);
      }

      int off = iconSize / 8;

      x += off;

      if (up) {
        y += (height - iconSize) / 2;
      } else {
        y += off;
      }

      g.fillShape(p, x, y);
      g.setStrokeWidth(1f);
      g.setColor(borderColor);
      g.drawShape(p, x, y);
      g.setStrokeWidth(sw);
      g.setColor(c);
    }

    public void setDirection(boolean up) {
      this.up = up;
    }

    public void setIconSize(int size) {
      int n = UIScreen.platformPixels(Platform.isTouchDevice()
                                      ? 16
                                      : 8);

      iconSize = Math.max(n, size);
    }

    public void setOwner(iPlatformComponent owner) {
      this.owner = owner;
    }

    @Override
    public iPlatformIcon getDisabledVersion() {
      return this;
    }

    @Override
    public int getIconHeight() {
      return iconSize;
    }

    @Override
    public int getIconWidth() {
      return iconSize;
    }

    public iPlatformComponent getOwner() {
      return owner;
    }
  }
}
