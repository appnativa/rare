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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPaintedButton;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.CharScanner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Don DeCoteau
 */
public class PainterHolder implements Cloneable {
  public boolean       usesPresedAsOverlay = true;
  public UIColor       disabledForegroundColor;
  public iPlatformIcon disabledIcon;
  public PaintBucket   disabledPainter;
  public iPlatformIcon disabledSelectedIcon;
  public PaintBucket   disabledSelectedPainter;
  public UIColor       foregroundColor;
  public iPlatformIcon normalIcon;
  public PaintBucket   normalPainter;
  public iPlatformIcon pressedIcon;
  public PaintBucket   pressedPainter;
  public iPlatformIcon pressedSelectedIcon;
  public iPlatformIcon rolloverIcon;
  public PaintBucket   rolloverPainter;
  public iPlatformIcon selectedIcon;
  public PaintBucket   selectedPainter;
  UIComponentPainter   componentPainter;
  private boolean      shared;

  public PainterHolder() {}

  public PainterHolder(iBackgroundPainter selected, iBackgroundPainter rollover, iBackgroundPainter pressed) {
    this.selectedPainter = new PaintBucket(selected);
    this.rolloverPainter = new PaintBucket(rollover);
    this.pressedPainter  = new PaintBucket(pressed);
  }

  public PainterHolder(iBackgroundPainter selected, iBackgroundPainter rollover, iBackgroundPainter pressed,
                       iBackgroundPainter disabled) {
    this.selectedPainter = new PaintBucket(selected);
    this.rolloverPainter = new PaintBucket(rollover);
    this.pressedPainter  = new PaintBucket(pressed);
    this.disabledPainter = new PaintBucket(disabled);
  }

  public PainterHolder(iPlatformIcon normalIcon, iPlatformIcon selectedIcon, iPlatformIcon rolloverIcon,
                       iPlatformIcon pressedIcon, iPlatformIcon disabledIcon) {
    this.selectedIcon = selectedIcon;
    this.rolloverIcon = rolloverIcon;
    this.pressedIcon  = pressedIcon;
    this.normalIcon   = normalIcon;
    this.disabledIcon = disabledIcon;
  }

  public PainterHolder(PaintBucket normalPainter, PaintBucket selectedPainter, PaintBucket rolloverPainter,
                       PaintBucket pressedPainter, PaintBucket disabledPainter) {
    this.selectedPainter = selectedPainter;
    this.rolloverPainter = rolloverPainter;
    this.pressedPainter  = pressedPainter;
    this.normalPainter   = normalPainter;
    this.disabledPainter = disabledPainter;
  }

  public void clear() {
    this.selectedIcon            = null;
    this.rolloverIcon            = null;
    this.pressedIcon             = null;
    this.normalIcon              = null;
    this.disabledIcon            = null;
    this.selectedPainter         = null;
    this.rolloverPainter         = null;
    this.pressedPainter          = null;
    this.normalPainter           = null;
    this.disabledPainter         = null;
    this.disabledForegroundColor = null;
    this.disabledSelectedIcon    = null;
    this.disabledSelectedPainter = null;
    this.pressedSelectedIcon     = null;
    this.foregroundColor         = null;
  }

  /**
   * Creates and returns a copy of this object.
   *
   * @return a copy of this object.
   */
  @Override
  public Object clone() {
    try {
      PainterHolder ph = (PainterHolder) super.clone();

      ph.shared = false;

      return ph;
    } catch(CloneNotSupportedException ex) {
      throw new InternalError();
    }
  }

  public void dispose() {
    clear();
  }

  public UIColor getBackground(iPaintedButton.ButtonState state) {
    PaintBucket pb = getPaintBucket(state, true);
    UIColor     c  = (pb == null)
                     ? null
                     : pb.getBackgroundColor();

    if ((c == null) && (normalPainter != null)) {
      c = normalPainter.getBackgroundColor();
    }

    return c;
  }

  public iPlatformPainter getBackgroundOverlayPainter(ButtonState state) {
    PaintBucket      pb = getPaintBucket(state, true);
    iPlatformPainter p  = (pb == null)
                          ? null
                          : pb.getImagePainter();

    if ((p == null) && (normalPainter != null)) {
      p = normalPainter.getImagePainter();
    }

    return p;
  }

  public iBackgroundPainter getBackgroundPainter(ButtonState state) {
    PaintBucket        pb = getPaintBucket(state, true);
    iBackgroundPainter p  = (pb == null)
                            ? null
                            : pb.getBackgroundPainter();

    if ((p == null) && (normalPainter != null)) {
      p = normalPainter.getBackgroundPainter();
    }

    return p;
  }

  public iPlatformBorder getBorder(ButtonState state) {
    PaintBucket     pb = getPaintBucket(state, true);
    iPlatformBorder b  = (pb == null)
                         ? null
                         : pb.getBorder();

    if ((b == null) && (normalPainter != null)) {
      b = normalPainter.getBorder();
    }

    return b;
  }

  public UIComponentPainter getConfiguredComponentPainter(ButtonState state) {
    iPlatformBorder    b = getBorder(state);
    iBackgroundPainter p = getBackgroundPainter(state);

    if (componentPainter == null) {
      componentPainter = new UIComponentPainter();
    } else {
      componentPainter.clear();
    }

    componentPainter.setBorder(b);
    componentPainter.setBackgroundPainter(p, false);

    if (p == null) {
      componentPainter.setBackgroundColor(getBackground(state));
    }

    componentPainter.setBackgroundOverlayPainter(getBackgroundOverlayPainter(state));

    return componentPainter;
  }

  public iPlatformIcon getDisabledIcon() {
    return disabledIcon;
  }

  public PaintBucket getDisabledPainter() {
    return disabledPainter;
  }

  /**
   * Gets the foreground for the button state
   *
   * @param state
   *          the button state
   * @return the color
   */
  public UIColor getForeground(iPaintedButton.ButtonState state) {
    return getForeground(state, false);
  }

  /**
   * Gets the foreground for the button state
   *
   * @param state
   *          the button state
   * @param returnDefaults
   *          true to return the system defaults if the holder does no have a
   *          value
   * @return the color
   */
  public UIColor getForeground(iPaintedButton.ButtonState state, boolean returnDefaults) {
    UIColor color = null;

    switch(state) {
      case DISABLED :
      case DISABLED_SELECTED :
        if (disabledForegroundColor != null) {
          color = disabledForegroundColor;
        } else {
          color = (disabledPainter == null)
                  ? null
                  : disabledPainter.getForegroundColor();

          if ((color == null) && returnDefaults) {
            color = ColorUtils.getDisabledForeground();
          }
        }

        break;

      default :
        PaintBucket pb = getPaintBucket(state, true);

        color = (pb == null)
                ? null
                : pb.getForegroundColor();

        break;
    }

    if (color == null) {
      color = foregroundColor;
    }

    if ((color == null) && returnDefaults) {
      color = UIColorHelper.getForeground();
    }

    return color;
  }

  /**
   * Gets the icon for the button state
   *
   * @param state
   *          the button state
   * @return the icon
   */
  public iPlatformIcon getIcon(iPaintedButton.ButtonState state) {
    switch(state) {
      case DISABLED :
        if (disabledIcon != null) {
          return disabledIcon;
        }

        break;

      case DISABLED_SELECTED :
        if (disabledSelectedIcon != null) {
          return disabledSelectedIcon;
        }

        if (disabledIcon != null) {
          return disabledIcon;
        }

        break;

      case PRESSED :
        if (pressedIcon != null) {
          return pressedIcon;
        }

        break;

      case PRESSED_SELECTED :
        if (pressedSelectedIcon != null) {
          return pressedSelectedIcon;
        }

        if (selectedIcon != null) {
          return selectedIcon;
        }

        break;

      case SELECTED :
        if (selectedIcon != null) {
          return selectedIcon;
        }

        break;

      case ROLLOVER :
        if (rolloverIcon != null) {
          return rolloverIcon;
        }

        break;

      default :
        break;
    }

    return normalIcon;
  }

  public iPlatformIcon getNormalIcon() {
    return normalIcon;
  }

  public PaintBucket getNormalPainter() {
    return normalPainter;
  }

  public PaintBucket getPaintBucket(iPaintedButton.ButtonState state, boolean defaultToNormal) {
    PaintBucket pb = null;

    switch(state) {
      case DISABLED :
      case DISABLED_SELECTED :
        pb = disabledPainter;

        break;

      case PRESSED :
        pb = pressedPainter;

        if (pb == null) {
          pb = rolloverPainter;
        }

        if (pb == null) {
          pb = selectedPainter;
        }

        break;

      case PRESSED_SELECTED :
        pb = pressedPainter;

        if (pb == null) {
          pb = selectedPainter;
        }

        if (pb == null) {
          pb = rolloverPainter;
        }

        break;

      case SELECTED :
        pb = selectedPainter;

        break;

      case ROLLOVER :
        pb = rolloverPainter;

        break;

      default :
        break;
    }

    if ((pb == null) && defaultToNormal) {
      pb = normalPainter;
    }

    return pb;
  }

  public iPlatformIcon getPressedIcon() {
    return pressedIcon;
  }

  public PaintBucket getPressedPainter() {
    return pressedPainter;
  }

  public iPlatformIcon getRolloverIcon() {
    return rolloverIcon;
  }

  public PaintBucket getRolloverPainter() {
    return rolloverPainter;
  }

  public iPlatformIcon getSelectedIcon() {
    return selectedIcon;
  }

  public PaintBucket getSelectedPainter() {
    return selectedPainter;
  }

  public void handleEnabled(iActionComponent c) {
    if (c.isEnabled()) {
      if ((disabledIcon != null) || (disabledPainter != null)) {
        if (disabledIcon != null) {
          c.setIcon(normalIcon);
        }

        if (disabledPainter != null) {
          c.setComponentPainter(normalPainter.getComponentPainter());
        }

        c.repaint();
      }
    } else {
      if ((disabledIcon != null) || (disabledPainter != null)) {
        if (disabledIcon != null) {
          c.setIcon(disabledIcon);
        }

        if (disabledPainter != null) {
          c.setComponentPainter(disabledPainter.getComponentPainter());
        }

        c.repaint();
      }
    }
  }

  public void handleMouseOut(iActionComponent c) {
    if ((rolloverIcon != null) || (rolloverPainter != null)) {
      if (rolloverIcon != null) {
        c.setIcon(normalIcon);
      }

      if (rolloverPainter != null) {
        c.setComponentPainter(normalPainter.getComponentPainter());
      }

      c.repaint();
    }
  }

  public void handleMouseOver(iActionComponent c) {
    if ((rolloverIcon != null) || (rolloverPainter != null)) {
      if (rolloverIcon != null) {
        c.setIcon(rolloverIcon);
      }

      if (rolloverPainter != null) {
        c.setComponentPainter(rolloverPainter.getComponentPainter());
      }

      c.repaint();
    }
  }

  public void handleMousePressed(iActionComponent c) {
    if ((pressedIcon != null) || (pressedPainter != null)) {
      if (pressedIcon != null) {
        c.setIcon(pressedIcon);
      }

      if (pressedPainter != null) {
        c.setComponentPainter(pressedPainter.getComponentPainter());
      }

      c.repaint();
    } else {
      handleMouseOver(c);
    }
  }

  public boolean isBackgroundPaintEnabled() {
    if ((selectedPainter != null) || (pressedPainter != null) || (normalPainter != null) || (disabledPainter != null)) {
      return true;
    }

    return false;
  }

  public boolean isShared() {
    return shared;
  }

  public boolean needsChangeHandler() {
    if ((disabledIcon != null) || (disabledPainter != null)) {
      return true;
    }

    return false;
  }

  public boolean needsMouseHandler() {
    if ((rolloverIcon != null) || (rolloverPainter != null)) {
      return true;
    }

    if ((pressedIcon != null) || (pressedPainter != null)) {
      return true;
    }

    return false;
  }

  public void paint(iPlatformGraphics g, float x, float y, float width, float height, ButtonState state,
                    int orientation, boolean paintIcon) {
    getConfiguredComponentPainter(state).paint(g, x, y, width, height, orientation);

    if (paintIcon) {
      iPlatformIcon icon = getIcon(state);

      if (icon != null) {
        icon.paint(g, x, y, width, height);
      }
    }
  }

  public void setDisabledForegroundColor(UIColor color) {
    disabledForegroundColor = color;
  }

  public void setDisabledIcon(iPlatformIcon disabledIcon) {
    this.disabledIcon = disabledIcon;
  }

  public void setDisabledPainter(PaintBucket disabledPainter) {
    this.disabledPainter = disabledPainter;
  }

  public void setForegroundColor(UIColor color) {
    foregroundColor = color;
  }

  public void setNormalIcon(iPlatformIcon normalIcon) {
    this.normalIcon = normalIcon;
  }

  public void setNormalPainter(PaintBucket normalPainter) {
    this.normalPainter = normalPainter;
  }

  public void setPressedIcon(iPlatformIcon pressedIcon) {
    this.pressedIcon = pressedIcon;
  }

  public void setPressedPainter(PaintBucket pressedPainter) {
    this.pressedPainter = pressedPainter;
  }

  public void setRolloverIcon(iPlatformIcon rolloverIcon) {
    this.rolloverIcon = rolloverIcon;
  }

  public void setRolloverPainter(PaintBucket rolloverPainter) {
    this.rolloverPainter = rolloverPainter;
  }

  public void setSelectedIcon(iPlatformIcon selectedIcon) {
    this.selectedIcon = selectedIcon;
  }

  public void setSelectedPainter(PaintBucket selectedPainter) {
    this.selectedPainter = selectedPainter;
  }

  public void setShared(boolean shared) {
    this.shared = shared;
  }

  /**
   * Creates a painter holder using attributes of the specified configuration
   * object
   *
   * @param context
   *          the context
   * @param cfg
   *          the configuration object
   * @return the painter holder or null
   */
  public static PainterHolder create(iWidget context, iSPOTElement cfg) {
    PaintBucket pp  = null,
                dp  = null,
                np  = null,
                sp  = null,
                dsp = null;
    UIColor     fg  = null,
                dfg = null;
    String      s;

    s = cfg.spot_getAttribute("fgColor");

    if (s != null) {
      fg = UIColorHelper.getColor(s);
    }

    s = cfg.spot_getAttribute("disabledColor");

    if (s != null) {
      dfg = UIColorHelper.getColor(s);
    }

    s = cfg.spot_getAttribute("bgColor");

    if (s == null) {
      s = cfg.spot_getAttribute("normalPainter");
    }

    if ((s != null) && (s.length() > 0)) {
      np = UIColorHelper.getPaintBucket(s);
    }

    s = cfg.spot_getAttribute("pressedPainter");

    if ((s != null) && (s.length() > 0)) {
      pp = UIColorHelper.getPaintBucket(s);
    }

    s = cfg.spot_getAttribute("disabledPainter");

    if ((s != null) && (s.length() > 0)) {
      dp = UIColorHelper.getPaintBucket(s);
    }

    s = cfg.spot_getAttribute("selectedPainter");

    if ((s != null) && (s.length() > 0)) {
      sp = UIColorHelper.getPaintBucket(s);
    }

    s = cfg.spot_getAttribute("disabledSelectedPainter");

    if ((s != null) && (s.length() > 0)) {
      dsp = UIColorHelper.getPaintBucket(s);
    }

    PainterHolder ph = null;

    if ((pp != null) || (dp != null) || (sp != null) || (dsp != null)) {
      ph                         = new PainterHolder(np, sp, null, pp, dp);
      ph.disabledSelectedPainter = dsp;
    }

    iPlatformIcon ni = null,
                  si = null,
                  di = null,
                  pi = null;

    s = cfg.spot_getAttribute("icon");

    if (s == null) {
      s = cfg.spot_getAttribute("normalIcon");
    }

    if ((s != null) && (s.length() > 0)) {
      ni = context.getIcon(s, null);
    }

    s = cfg.spot_getAttribute("selectedIcon");

    if ((s != null) && (s.length() > 0)) {
      si = context.getIcon(s, null);
    }

    s = cfg.spot_getAttribute("disabledIcon");

    if ((s != null) && (s.length() > 0)) {
      di = context.getIcon(s, null);
    }

    s = cfg.spot_getAttribute("pressedIcon");

    if ((s != null) && (s.length() > 0)) {
      pi = context.getIcon(s, null);
    }

    if ((pi != null) || (si != null) || (ni != null) || (di != null) || (fg != null) || (dfg != null)) {
      if (ph == null) {
        ph = new PainterHolder(ni, si, null, pi, di);
      } else {
        ph.disabledIcon = di;
        ph.normalIcon   = ni;
        ph.selectedIcon = si;
        ph.pressedIcon  = pi;
      }

      ph.foregroundColor         = fg;
      ph.disabledForegroundColor = dfg;
    }

    return ph;
  }

  public static PainterHolder create(String keyPrefix, boolean iconsOnly, boolean paintOnly, boolean themed) {
    StringBuilder       sb    = new StringBuilder(keyPrefix);
    int                 sblen = sb.length();
    PainterHolder       ph    = new PainterHolder();
    iPlatformAppContext app   = Platform.getAppContext();
    UIProperties        props = app.getUIDefaults();
    String              theme = themed
                                ? (PlatformHelper.isDarkTheme()
                                   ? "dark"
                                   : "light")
                                : null;

    sb.append("foreground");

    if (themed) {
      sb.append(theme);
    }

    ph.foregroundColor = props.getColor(sb.toString());
    sb.setLength(sblen);
    sb.append("disabledForeground");

    if (themed) {
      sb.append(theme);
    }

    ph.disabledForegroundColor = props.getColor(sb.toString());

    if (!paintOnly) {
      sb.setLength(sblen);
      sb.append("icon");

      if (themed) {
        sb.append(theme);
      }

      ph.normalIcon = app.getResourceAsIconEx(sb.toString());
      sb.setLength(sblen);
      sb.append("disabledIcon");

      if (themed) {
        sb.append(theme);
      }

      ph.disabledIcon = app.getResourceAsIconEx(sb.toString());
      sb.setLength(sblen);
      sb.append("disabledSelectedIcon");

      if (themed) {
        sb.append(theme);
      }

      ph.disabledSelectedIcon = app.getResourceAsIconEx(sb.toString());
      sb.setLength(sblen);
      sb.append("pressedIcon");

      if (themed) {
        sb.append(theme);
      }

      ph.pressedIcon = app.getResourceAsIconEx(sb.toString());
      sb.setLength(sblen);
      sb.append("pressedSelectedIcon");

      if (themed) {
        sb.append(theme);
      }

      ph.pressedSelectedIcon = app.getResourceAsIconEx(sb.toString());
      sb.setLength(sblen);
      sb.append("selectedIcon");

      if (themed) {
        sb.append(theme);
      }

      ph.selectedIcon = app.getResourceAsIconEx(sb.toString());
    }

    if (!iconsOnly) {
      sb.setLength(sblen);
      sb.append("painter");

      if (themed) {
        sb.append(theme);
      }

      ph.normalPainter = props.getPaintBucket(sb.toString());

      if (ph.normalPainter == null) {
        sb.setLength(sblen);
        sb.append("background");
        ph.normalPainter = Platform.getUIDefaults().getPaintBucket(sb.toString());
      }

      sb.setLength(sblen);
      sb.append("disabledPainter");

      if (themed) {
        sb.append(theme);
      }

      ph.disabledPainter = props.getPaintBucket(sb.toString());
      sb.setLength(sblen);
      sb.append("pressedPainter");

      if (themed) {
        sb.append(theme);
      }

      ph.pressedPainter = props.getPaintBucket(sb.toString());
      sb.setLength(sblen);
      sb.append("selectedPainter");

      if (themed) {
        sb.append(theme);
      }

      ph.selectedPainter = props.getPaintBucket(sb.toString());
    }

    return ph;
  }

  /**
   * Returns a PainterHolder for a map representing an icon state list
   *
   * @param map
   *          the icon state map
   *
   * @return a PainterHolder for a map representing an icon state list
   */
  @SuppressWarnings("resource")
  public static PainterHolder createFromIconStateMap(Map<String, String> map, CharScanner sc) {
    if (map == null) {
      return null;
    }

    if (sc == null) {
      sc = new CharScanner();
    }

    String                          icon;
    Entry<String, String>           e;
    Iterator<Entry<String, String>> it = map.entrySet().iterator();
    iPlatformIcon                   ic;
    int                             a[];
    List<String>                    list = new ArrayList<String>(3);
    PainterHolder                   ph   = new PainterHolder();
    iPlatformAppContext             app  = Platform.getAppContext();

    while(it.hasNext()) {
      e    = it.next();
      icon = e.getValue();
      ic   = app.getResourceAsIcon(icon);
      icon = e.getKey();
      sc.reset(icon);
      sc.toLowerCase();

      if ("normal".equals(icon)) {
        ph.normalIcon = ic;

        continue;
      }

      list = sc.getTokens(',', true, list);
      a    = new int[list.size()];

      for (int n = 0; n < a.length; n++) {
        String s = list.get(n);

        if (s.equals("disabled") || s.endsWith("not_enabled")) {
          ph.disabledIcon = ic;
        } else if (s.equals("selected")) {
          ph.selectedIcon = ic;
        } else if (s.equals("selected_disabled") || s.equals("selecteddisabled")) {
          ph.disabledSelectedIcon = ic;
        } else if (s.equals("rollover")) {
          ph.rolloverIcon = ic;
        } else if (s.equals("pressed")) {
          ph.pressedIcon = ic;
        }
      }
    }

    return ph;
  }
}
