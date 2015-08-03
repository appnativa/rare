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
import com.appnativa.rare.ui.SimpleColorStateList;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.text.HTMLCharSequence;
import com.appnativa.rare.widget.aGroupableButton;

/*-[
 #import "RAREUIControl.h"
 ]-*/
public class CustomButtonView extends View {
  protected IconPosition    iconPosition = IconPosition.LEADING;
  protected iChangeListener changeListener;
  private iActionListener   actionListener;
  private ChangeEvent       changeEvent;
  private boolean           hasScalingIcon;

  public CustomButtonView() {
    super(createProxy());
    setFont(FontUtils.getDefaultFont());

    UIColor fg = Platform.getUIDefaults().getColor("Rare.Button.foreground");

    if (fg == null) {
      fg = ColorUtils.getForeground();
    }

    setForegroundColor(fg);
  }

  public CustomButtonView(Object uiview) {
    super(uiview);
  }

  protected void checkForegroundColor() {
    PainterHolder ph    = (componentPainter == null)
                          ? null
                          : componentPainter.getPainterHolder();
    ButtonState   state = ButtonState.DEFAULT;
    UIColor       fg    = null;

    if (!enabled) {
      state = isSelected()
              ? ButtonState.DISABLED_SELECTED
              : ButtonState.DISABLED;
    } else {
      if (isPressed()) {
        state = isSelected()
                ? ButtonState.PRESSED_SELECTED
                : ButtonState.PRESSED;
      } else if (isSelected()) {
        state = ButtonState.SELECTED;
      }
    }

    if (ph != null) {
      fg = ph.getForeground(state);
    }

    if (fg != null) {
      setForegroundColorEx(fg);
    } else if (foregroundColor == ColorUtils.getForeground()) {
      if (enabled) {
        setForegroundColorEx(foregroundColor);
      } else {
        setForegroundColorEx(ColorUtils.getDisabledForeground());
      }
    } else if (foregroundColor instanceof UIColorShade) {
      SimpleColorStateList csl = ((UIColorShade) foregroundColor).getColorStateList();

      if (csl != null) {
        setForegroundColorEx(csl.getColor(state));
      }
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

  public void performClick() {
    actionPerformed();
  }

  public void selectionChanged(final boolean selected) {
    if (component != null) {
      aGroupableButton b = (aGroupableButton) component.getWidget();

      b.selectionChanged(selected);
    }

    handleChangeEvent();
  }

  @Override
  public void setActionListener(iActionListener l) {
    actionListener = l;
  }

  public native void setAutoRepeats(int interval)
  /*-[
    [((RAREUIControl*)proxy_) setContinuous: interval>0];
    if(interval>0) {
      float finterval=interval/1000.0;
      [((RAREUIControl*)proxy_) setPeriodicDelay: finterval interval: finterval];
    }
  ]-*/
  ;

  public native void setCallNeedsDisplayOnSuper(boolean needs)
  /*-[
    ((RAREUIControl*)proxy_).callNeedsDisplayOnSuper=needs;
  ]-*/
  ;

  public void setChangeListener(iChangeListener l) {
    this.changeListener = l;
  }

  public native void setDisabledIcon(iPlatformIcon icon)
  /*-[
    ((RAREUIControl*)proxy_).disabledIcon=icon;
  ]-*/
  ;

  public native void setDisabledSelectedIcon(iPlatformIcon icon)
  /*-[
    ((RAREUIControl*)proxy_).disabledSelectedIcon=icon;
  ]-*/
  ;

  public native void setFont(UIFont font)
  /*-[
    font_ = font;
    if(font) {
      ((RAREUIControl*)proxy_).font=(UIFont*)[font getIOSProxy];
    }
  ]-*/
  ;

  public native void setIcon(iPlatformIcon icon)
  /*-[
    ((RAREUIControl*)proxy_).icon=icon;
  ]-*/
  ;

  public native void setIconGap(int iconGap)
  /*-[
    ((RAREUIControl*)proxy_).iconGap=iconGap;
   ]-*/
  ;

  public IconPosition setIconPosition() {
    return iconPosition;
  }

  public native void setIconPosition(IconPosition ip)
  /*-[
    if (ip == nil) {
      ip = [RARERenderableDataItem_IconPositionEnum LEADING];
    }
    iconPosition_ = ip;
    ((RAREUIControl*)proxy_).iconPosition=ip.ordinal;
  ]-*/
  ;

  public native void setMargin(float top, float right, float bottom, float left)
  /*-[
    [((RAREUIControl*)proxy_) setInsetsWithTop: top right: right bottom: bottom left: left];
  ]-*/
  ;

  public native void setPressedIcon(iPlatformIcon icon)
  /*-[
    ((RAREUIControl*)proxy_).pressedIcon=icon;
  ]-*/
  ;

  public native void setPressedSelectedIcon(iPlatformIcon icon)
  /*-[
    ((RAREUIControl*)proxy_).pressedSelectedIcon=icon;
  ]-*/
  ;

  public native void setSelected(boolean selected)
  /*-[
    [((RAREUIControl*)proxy_) setSelected:selected];
   ]-*/
  ;

  public native void setSelectedIcon(iPlatformIcon icon)
  /*-[
    ((RAREUIControl*)proxy_).selectedIcon=icon;
  ]-*/
  ;

  public void setText(CharSequence text) {
    setTextEx(HTMLCharSequence.checkSequence(text, getFontAlways()));
  }

  public native void setTextAlignment(HorizontalAlign hal, VerticalAlign val)
  /*-[
    RAREUIControl* button=(RAREUIControl*)proxy_;
    button.textHorizontalAlignment= hal.ordinal;
    button.textVerticalAlignment= val.ordinal;
  ]-*/
  ;

  public native void setTextEx(CharSequence text)
  /*-[
    [((RAREUIControl*)proxy_) setCharSequence: text];
  ]-*/
  ;

  public native void setUnderlined(boolean underlined)
  /*-[
    ((RAREUIControl*)proxy_).isUnderlined=underlined;
  ]-*/
  ;

  public native void setWordWrap(boolean wrap)
  /*-[
    [((RAREUIControl*)proxy_) setWrapText: wrap];
  ]-*/
  ;

  public native iPlatformIcon getDisabledIcon()
  /*-[
    return ((RAREUIControl*)proxy_).disabledIcon;
  ]-*/
  ;

  public native iPlatformIcon getIcon()
  /*-[
    return ((RAREUIControl*)proxy_).icon;
  ]-*/
  ;

  public native int getIconGap()
  /*-[
    return ((RAREUIControl*)proxy_).iconGap;
   ]-*/
  ;

  @Override
  public UIInsets getMargin() {
    UIInsets in = new UIInsets();

    getMarginEx(in);

    return in;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    getPreferredSize(size, 0);
  }

  public native iPlatformIcon getPressedIcon()
  /*-[
    return ((RAREUIControl*)proxy_).pressedIcon;
  ]-*/
  ;

  public native iPlatformIcon getSelectedIcon()
  /*-[
    return ((RAREUIControl*)proxy_).selectedIcon;
  ]-*/
  ;

  public native String getText()
  /*-[
    return [((RAREUIControl*)proxy_) getText];
  ]-*/
  ;

  public native boolean isPressed()
  /*-[
    return [((RAREUIControl*)proxy_) isPressed];
  ]-*/
  ;

  public native boolean isSelected()
  /*-[
    return [((RAREUIControl*)proxy_) isSelected];
  ]-*/
  ;

  public native boolean isWordWrap()
  /*-[
    return [((RAREUIControl*)proxy_) isWrapText];
  ]-*/
  ;

  native static Object createProxy()
  /*-[
    return [RAREUIControl new];
  ]-*/
  ;

  void setHasScalingIcon(boolean hasScalingIcon) {
    this.hasScalingIcon = hasScalingIcon;
  }

  native void getMarginEx(UIInsets insets)
  /*-[
    [((RAREUIControl*)proxy_) getInsets: insets];
  ]-*/
  ;

  boolean isHasScalingIcon() {
    return hasScalingIcon;
  }

  protected void actionPerformed() {
    if (actionListener != null) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          if (actionListener != null) {
            ActionEvent e = new ActionEvent(CustomButtonView.this);

            actionListener.actionPerformed(e);
          }
        }
      });
    }
  }

  @Override
  protected void disposeEx() {
    super.disposeEx();
    actionListener = null;
    changeListener = null;
    changeEvent    = null;
  }

  protected void handleChangeEvent() {
    if (changeListener != null) {
      if (changeEvent == null) {
        changeEvent = new ChangeEvent(this);
      }

      changeListener.stateChanged(changeEvent);
    }
  }

  @Override
  protected native void setEnabledEx(boolean b)
  /*-[
    RAREUIControl* v=(RAREUIControl*)proxy_;
    v.enabled=b;
  ]-*/
  ;

  protected native void setForegroundColorEx(UIColor fg)
  /*-[
    if(fg) {
      [((RAREUIControl*)proxy_) setTextColor: fg.getAPColor];
    }
  ]-*/
  ;
}
