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
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.widget.LabelWidget;
import com.appnativa.rare.widget.iWidget;

public class TitlePane extends BorderPanel {
  LabelWidget                iconWidget;
  LabelWidget                titleWidget;
  private boolean            iconOnLeft = true;
  private iPlatformComponent otherComponent;

  public TitlePane(iWidget context) {
    super(context);
    titleWidget = LabelWidget.create(context.getContainerViewer());
    setCenterView(titleWidget.getContainerComponent());

    Object bg = Platform.getUIDefaults().get("Rare.TitlePane.background");

    if (bg == null) {
      bg = ColorUtils.getBackground();
    }

    if (bg != null) {
      Utils.setBackground(this, bg);
    }

    UIColor fg = Platform.getUIDefaults().getColor("Rare.TitlePane.foreground");

    if (fg != null) {
      titleWidget.setForeground(fg);
    }

    UIFont f = Platform.getUIDefaults().getFont("Rare.TitlePane.font");

    if (f == null) {
      f = FontUtils.getDefaultFont();
      f = f.deriveFontSize(f.getSize2D() + 2);
    }

    titleWidget.setFont(f);
    setBorder(getTitlePaneBorder());
  }

  @Override
  public void dispose() {
    super.dispose();

    if (titleWidget != null) {
      titleWidget.dispose();
    }

    if (iconWidget != null) {
      iconWidget.dispose();
    }

    iconWidget     = null;
    titleWidget    = null;
    otherComponent = null;
  }

  public void setIcon(iPlatformIcon icon) {
    if (iconWidget == null) {
      iconWidget = LabelWidget.create(titleWidget.getContainerViewer());

      if (iconOnLeft) {
        setLeftView(iconWidget.getContainerComponent());
      } else {
        setRightView(iconWidget.getContainerComponent());
      }
    }

    iconWidget.setIcon(icon);
  }

  public void setIconOnLeft(boolean iconOnLeft) {
    if (this.iconOnLeft != iconOnLeft) {
      this.iconOnLeft = iconOnLeft;
      setEdgeComponents();
    }
  }

  public void setOtherComponent(iPlatformComponent c) {
    otherComponent = c;
    setEdgeComponents();
  }

  public void setTitle(String title) {
    titleWidget.setValue(title);
  }

  public static iPlatformBorder getTitlePaneBorder() {
    iPlatformBorder b = Platform.getUIDefaults().getBorder("Rare.TitlePane.border");

    if (b == null) {
      UIColor c = Platform.getUIDefaults().getColor("Rare.TitlePane.borderColor");

      if (c == null) {
        c = Platform.getUIDefaults().getColor("Rare.Alert.title.lineColor");
      }

      if (c == null) {
        c = ColorUtils.getDisabledForeground();
      }

      b = new UIMatteBorder(0, 0, 2, 0, c);
      b = new UICompoundBorder(b, new UIEmptyBorder(ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_2,
              ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_8));
      Platform.getUIDefaults().put("Rare.TitlePane.border", b);
    }

    return b;
  }

  protected void setEdgeComponents() {
    setRightView(null);
    setLeftView(null);

    if (iconOnLeft) {
      if (iconWidget != null) {
        setLeftView(iconWidget.getContainerComponent());
      }

      if (otherComponent != null) {
        setRightView(otherComponent);
      }
    } else {
      if (iconWidget != null) {
        setRightView(iconWidget.getContainerComponent());
      }

      if (otherComponent != null) {
        setLeftView(otherComponent);
      }
    }
  }

  protected boolean isIconOnLeft() {
    return iconOnLeft;
  }
}
