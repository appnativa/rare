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
import com.appnativa.rare.TemplateHandler;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.LabelWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSet;

public class TitlePane extends BorderPanel {
  protected LabelWidget        iconWidget;
  protected LabelWidget        titleWidget;
  protected boolean            iconOnLeft = true;
  protected iPlatformComponent otherComponent;
  private static Boolean hasTemplate;
  public TitlePane(iWidget context) {
    super(context);
    if(hasTemplate==null)  {
      hasTemplate=TemplateHandler.hasWidgetTemplate("Rare.TitlePane.title");
    }
    String template=hasTemplate ? "Rare.TitlePane.title" : "Rare.Alert.title";
    Label l = (Label) Platform.getWindowViewer().createConfigurationObject("Label", template);
    SPOTSet borders = l.getBorders();
    l.setBorders(null);
    SPOTPrintableString ps=(SPOTPrintableString) l.bgColor.clone();
    l.bgColor.spot_clear();
    titleWidget = LabelWidget.create(context.getContainerViewer(), l);
    setCenterView(titleWidget.getContainerComponent());
    
    Object bg=null;
    if(ps.getValue()!=null) {
      bg=ColorUtils.getBackgroundColor(ps);
    }
    if(bg==null) {
      bg= Platform.getUIDefaults().get("Rare.TitlePane.background");
    }

    if (bg == null) {
      bg = Platform.getUIDefaults().getColor("Rare.Alert.title.backgroundColor");
    }

    if (bg == null) {
      bg = ColorUtils.getBackground();
    }

    if (bg != null) {
      Utils.setBackground(this, bg);
    }
    if(l.fgColor.getValue()==null) {
      UIColor fg = Platform.getUIDefaults().getColor("Rare.TitlePane.foreground");
  
      if (fg == null) {
        fg = Platform.getUIDefaults().getColor("Rare.Alert.foregroundColor");
      }
  
      if (fg != null) {
        titleWidget.setForeground(fg);
      }
    }

    UIFont f = Platform.getUIDefaults().getFont("Rare.TitlePane.font");

    if ((f == null) &&!l.font.spot_hasValue()) {
      f = UIFontHelper.getDefaultFont();
      f = f.deriveFontSize(f.getSize2D() + 2);
    }

    if (f != null) {
      titleWidget.setFont(f);
    }
    iPlatformBorder b=null;
    if(borders!=null) {
      b=BorderUtils.createBorder(titleWidget, borders, null);
    }

    if(b!=null) {
      setBorder(b);
    }
    else {
      setBorder(getTitlePaneBorder());
    }
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
      Label l = (Label) Platform.getWindowViewer().createConfigurationObject("Label", "Rare.Dialog.iconWidget");

      iconWidget = LabelWidget.create(titleWidget.getContainerViewer(), l);

      if (l.getBorders() == null) {
        iconWidget.setBorder(new UIEmptyBorder(0, UIScreen.PLATFORM_PIXELS_8, 0, 0));
      }

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
        c = Platform.getUIDefaults().getColor("Rare.Alert.lineColor");
      }

      if (c == null) {
        c = UILineBorder.getDefaultLineColor();
      }

      b = new UIMatteBorder(0, 0, ScreenUtils.PLATFORM_PIXELS_2, 0, c);
      b = new UICompoundBorder(b, new UIEmptyBorder(ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_2,
              ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_4));
      Platform.getUIDefaults().put("Rare.TitlePane.border", b);
    }

    return b;
  }

  public static TitlePane createDialogTitle(iWidget context, iActionListener closeAction) {
    WindowViewer w  = context.getWindow().getWindow();
    TitlePane    tp = new TitlePane(context);

    tp.setTitle(w.getTitle());

    if (Platform.getUIDefaults().getBoolean("Rare.Dialog.showIcon", true)) {
      tp.setIcon(w.getIcon());
    }

    if (closeAction!=null) {
      iActionComponent close = PlatformHelper.createNakedButton(context.getContainerComponent(), false, 0);

      close.addActionListener(closeAction);
      close.setIcon(Platform.getResourceAsIcon("Rare.icon.close"));
      tp.setOtherComponent(close);
    }

    return tp;
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
