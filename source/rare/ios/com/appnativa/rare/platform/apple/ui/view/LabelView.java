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

/*-[
 #import "RAREAPLabelView.h"
 #import "APView+Component.h"
 ]-*/
import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.apple.ui.iApplePainterSupport;
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
import com.appnativa.rare.ui.text.HTMLCharSequence;

public class LabelView extends View implements iApplePainterSupport {
  protected boolean apProxy = true;

  public LabelView() {
    this(createProxy());
  }

  public LabelView(Object proxy) {
    super(proxy);

    if (!Platform.getAppContext().isPlatformColorTheme()) {
      setFont(FontUtils.getDefaultFont());
      setForegroundColor(ColorUtils.getForeground());
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
  public native void setWordWrap(boolean wrap)
  /*-[
        [((UILabel*)proxy_) setWrapText: wrap];
  ]-*/
  ;

  @Override
  public native boolean isWordWrap()
  /*-[
        return [((UILabel*)proxy_) isWrapText];
  ]-*/
  ;

  @Override
  public native void setMargin(float top, float right, float bottom, float left)
  /*-[
     [((UILabel*)proxy_) setInsetsWithTop: top right: right bottom: bottom left: left];
  ]-*/
  ;

  @Override
  public native void setFont(UIFont font)
  /*-[
     if(font!=font_) {
        font_ = font;
        if(font!=nil) {
        [((UILabel*)proxy_) setFont: (UIFont*)[font getIOSProxy]];
      }
    }
  ]-*/
  ;

  @Override
  protected native void setForegroundColorEx(UIColor fg)
  /*-[
    if(fg!=nil) {
      [((UILabel*)proxy_) setTextColor: fg.getAPColor];
    }
  ]-*/
  ;

  @Override
  public native void setIcon(iPlatformIcon icon)
  /*-[
     [((UILabel*)proxy_) setIcon: icon];
   ]-*/
  ;

  @Override
  public native void setIconGap(int iconGap)
  /*-[
    [((UILabel*)proxy_) setIconGap: iconGap];
   ]-*/
  ;

  @Override
  public native void setIconPosition(IconPosition iconPosition)
  /*-[
    if (iconPosition == nil) {
      iconPosition = [RARERenderableDataItem_IconPositionEnum LEADING];
    }
    [((UILabel*)proxy_) setIconPosition: iconPosition.ordinal];
  ]-*/
  ;

  @Override
  public native void setTextAlignment(HorizontalAlign hal, VerticalAlign val)
  /*-[
    UILabel* label=(UILabel*)proxy_;
    [label setTextHorizontalAlignment: hal.ordinal];
    [label setTextVerticalAlignment: val.ordinal];
  ]-*/
  ;

  @Override
  public native void getMinimumSize(UIDimension size,float maxWidth)
  /*-[
    CGSize s= [((UILabel*)proxy_) intrinsicContentSize];
    size->width_=0;
    size->height_=s.height;
  ]-*/
  ;

  @Override
  public void setText(CharSequence text) {
    setTextEx(HTMLCharSequence.checkSequence(text, getFontAlways()));
  }

  @Override
  public UIInsets getMargin() {
    UIInsets in = new UIInsets();

    if (apProxy) {
      getMarginEx(in);
    }

    return in;
  }

  @Override
  public native CharSequence getText()
  /*-[
   return [((UILabel*)proxy_) getCharSequence];
  ]-*/
  ;

  public native void setTextEx(CharSequence text)
  /*-[
    [((UILabel*)proxy_) setCharSequence: text];
  ]-*/
  ;

  public native void setPrefColumnWidth(int width)
  /*-[
    ((UILabel*)proxy_).preferredMaxLayoutWidth=width;
  ]-*/
  ;

  @Override
  public native boolean isPressed()
  /*-[
        return [((UILabel*)proxy_) isPressed];
  ]-*/
  ;

  public static native Object createProxy()
  /*-[
        return [[RAREAPLabelView alloc]init];
  ]-*/
  ;

  @Override
  public boolean isMouseTransparent() {
    return (mouseListener == null) || ((mouseMotionListener == null) &&!mouseGestureListenerAdded);
  }

  native void getMarginEx(UIInsets insets)
  /*-[
    [((RAREAPLabelView*)proxy_) getInsets: insets];
  ]-*/
  ;
}
