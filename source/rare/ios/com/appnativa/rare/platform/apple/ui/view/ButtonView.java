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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.text.HTMLCharSequence;

/*-[
 #import "RAREAPButton.h"
 #import "APView+Component.h"
 ]-*/
public class ButtonView extends ControlView {
  protected iPlatformIcon icon;
  protected iPlatformIcon disabledIcon;
  protected iPlatformIcon pressedIcon;
  protected iPlatformIcon selectedIcon;
  protected int           iconGap      = 4;
  IconPosition            iconPosition = IconPosition.LEADING;
  iActionListener actionListener;

  public ButtonView() {
    super(createProxy());
    addTarget();

    if (!Platform.getAppContext().isPlatformColorTheme()) {
      setFont(FontUtils.getDefaultFont());

      UIColor fg = Platform.getUIDefaults().getColor("Rare.Button.foreground");

      if (fg == null) {
        fg = ColorUtils.getForeground();
      }

      setForegroundColor(fg);
    }
  }


  @Override
  public void setActionListener(iActionListener l) {
    actionListener = l;
  }

  @Override
  protected native void setEnabledEx(boolean enabled)
  /*-[
        ((UIControl*)proxy_).enabled=enabled;
  ]-*/
  ;

  @Override
  protected void disposeEx() {
    super.disposeEx();
    actionListener = null;
    icon           = null;
    disabledIcon   = null;
    pressedIcon    = null;
    selectedIcon   = null;
  }

  protected void actionPerformed() {
    if (actionListener != null) {
      ActionEvent e = new ActionEvent(this);

      actionListener.actionPerformed(e);
    }
  }

  @Override
  public void borderChanged(iPlatformBorder newBorder) {
    super.borderChanged(newBorder);

    if (newBorder == null) {
      setMargin(2, 2, 2, 2);
    } else {
      setMargin(newBorder.getBorderInsets(null));
    }
  }

  @Override
  public void performClick() {
    actionPerformed();
  }

  protected ButtonView(Object nsview) {
    super(nsview);
  }

  @Override
  public native void setFont(UIFont font)
  /*-[
    font_ = font;
    if(font) {
      [((UIButton*)proxy_).titleLabel setFont: (UIFont*)[font getIOSProxy]];
    }
  ]-*/
  ;

  @Override
  public void getMinimumSize(UIDimension size, float maxWidth) {
    getPreferredSize(size, maxWidth);
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {
    super.getPreferredSize(size, maxWidth);
  }

  @Override
  public native void setIcon(iPlatformIcon icon)
  /*-[
    icon_=icon;
    [((UIButton*)proxy_) setIcon: icon];
  ]-*/
  ;

  @Override
  public UIInsets getMargin() {
    UIInsets in = new UIInsets();

    getMarginEx(in);

    return in;
  }

  public iPlatformIcon getIcon() {
    return icon;
  }

  @Override
  public native String getText()
  /*-[
        return ((UIButton*)proxy_).currentTitle;
  ]-*/
  ;

  @Override
  public native boolean isWordWrap()
  /*-[
        return [((UIButton*)proxy_) isWrapText];
  ]-*/
  ;

  @Override
  public native void setWordWrap(boolean wrap)
  /*-[
        [((UIButton*)proxy_) setWrapText: wrap];
  ]-*/
  ;

  @Override
  public void setText(CharSequence text) {
    setTextEx(HTMLCharSequence.checkSequence(text, getFontAlways()));
  }

  public native void setTextEx(CharSequence text)
  /*-[
        [((UIButton*)proxy_) setCharSequence: text];
  ]-*/
  ;

  public void onStateChanged(int newState) {}

  public native void setAutoRepeats(int interval)
  /*-[
    [((UIButton*)proxy_) setContinuous: interval>0];
    if(interval>0) {
                float finterval=interval/1000.0;
        [((UIButton*)proxy_) setPeriodicDelay: finterval interval: finterval];
    }
  ]-*/
  ;

  public native void setDefaultButton(boolean b)
  /*-[
  ]-*/
  ;

  public native void setEscapeButton(boolean b)
  /*-[
  ]-*/
  ;

  @Override
  public native void setIconGap(int gap)
  /*-[
    iconGap_=gap;
        [((UIButton*)proxy_) setIconGap:gap];
   ]-*/
  ;

  @Override
  public native void setTextAlignment(HorizontalAlign hal, VerticalAlign val)
  /*-[
        UIButton* button=(UIButton*)proxy_;
        [button setTextHorizontalAlignment: hal.ordinal];
        [button setTextVerticalAlignment: val.ordinal];
  ]-*/
  ;

  @Override
  public native void setIconPosition(IconPosition iconPosition)
  /*-[
  if (iconPosition == nil) {
    iconPosition = [RARERenderableDataItem_IconPositionEnum LEADING];
  }
  iconPosition_ = iconPosition;
  [((UIButton*)proxy_)setIconPosition: iconPosition.ordinal];
  ]-*/
  ;

  public native void setMargin(int top, int right, int bottom, int left)
  /*-[
      [((UIButton*)proxy_) setInsetsWithTop: top right: right bottom: bottom left: left];
  ]-*/
  ;

  private native static Object createProxy()
  /*-[
    return [UIButton buttonWithType: UIButtonTypeRoundedRect];
  ]-*/
  ;

  @Override
  public iPlatformIcon getDisabledIcon() {
    return disabledIcon;
  }

  @Override
  public native void setDisabledIcon(iPlatformIcon icon)
  /*-[
     disabledIcon_ = icon;
     [((UIButton*)proxy_) setDisabledIcon: icon];
   ]-*/
  ;

  @Override
  public iPlatformIcon getPressedIcon() {
    return pressedIcon;
  }

  @Override
  public native void setPressedIcon(iPlatformIcon icon)
  /*-[
    pressedIcon_ = icon;
    [((UIButton*)proxy_) setPressedIcon: icon];
  ]-*/
  ;

  @Override
  public iPlatformIcon getSelectedIcon() {
    return selectedIcon;
  }

  @Override
  public native void setSelectedIcon(iPlatformIcon icon)
  /*-[
     selectedIcon_ = icon;
     [((UIButton*)proxy_) setSelectedIcon: icon];
   ]-*/
  ;

  protected void buttonClicked(Object sender) {
    if (mouseListener == null) {
      actionPerformed();
    }
  }

  private native void addTarget()
  /*-[
    [((UIButton*)proxy_) addTarget:self action:@selector(buttonClickedWithId:) forControlEvents:UIControlEventTouchUpInside];
  ]-*/
  ;

  @Override
  public int getIconGap() {
    return iconGap;
  }

  native void getMarginEx(UIInsets insets)
  /*-[
    [((UIButton*)proxy_) getInsets: insets];
  ]-*/
  ;
}
