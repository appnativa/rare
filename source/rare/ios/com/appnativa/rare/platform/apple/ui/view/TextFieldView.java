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

import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.text.HTMLCharSequence;

/*-[
 #import "RAREAPTextField.h"
 #import "APView+Component.h"
 ]-*/

/**
 *
 * @author Don DeCoteau
 */
public class TextFieldView extends aTextEditorView {
  protected boolean marginSet;
  public TextFieldView() {
    this(createProxy());
  }

  public TextFieldView(Object nsview) {
    super(nsview);
  }

  @Override
  public native void removeNativeBorder()
  /*-[
    if(!marginSet_) {
      [((RAREAPTextField*)proxy_) setInsetsWithTop: 2 right: 2 bottom: 2 left: 4];
    }
    ((RAREAPTextField*)proxy_).borderStyle=UITextBorderStyleNone;
  ]-*/
  ;

  @Override
  public native void setAutoShowKeyboard(boolean show)
  /*-[
    [((RAREAPTextField*)proxy_) setAutoShowKeyboard: show];
  ]-*/
  ;

  @Override
  public void setBorder(iPlatformBorder b) {
    super.setBorder(b);

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
  public native void setEditable(boolean editable)
  /*-[
     [((RAREAPTextField*)proxy_) setEditable: editable];
   ]-*/
  ;

  @Override
  public native void setEmptyFieldColor(UIColor color)
  /*-[
    ((RAREAPTextField*)proxy_).emptyFieldColor=color ? (UIColor*)color.getAPColor : nil;
  ]-*/
  ;

  @Override
  public native void setEmptyFieldFont(UIFont font)
  /*-[
    ((RAREAPTextField*)proxy_).emptyFieldFont=font ? (UIFont*)font.getIOSProxy : nil;
  ]-*/
  ;

  @Override
  public native void setEmptyFieldText(String text)
  /*-[
    ((RAREAPTextField*)proxy_).placeholder=(text && text.length>0) ? text : nil;
  ]-*/
  ;

  @Override
  public native void setFont(UIFont font)
  /*-[
   font_ = font;
   if(font) {
     [((RAREAPTextField*)proxy_) setFont: (UIFont*)[font getIOSProxy]];
   }
   ]-*/
  ;

  @Override
  public native void setMargin(float top, float right, float bottom, float left)
  /*-[
    marginSet_=YES;
    [((RAREAPTextField*)proxy_) setInsetsWithTop: top right: right bottom: bottom left: left];
  ]-*/
  ;

  public native void setShowKeyBoard(boolean show)
  /*-[
    [((RAREAPTextField*)proxy_) setShowKeyBoard: show];
  ]-*/
  ;

  public native void setShowMenu(boolean show)
  /*-[
    [((RAREAPTextField*)proxy_) setShowMenu: show];
  ]-*/
  ;

  @Override
  public void setText(CharSequence text) {
    setTextEx(HTMLCharSequence.checkSequence(text, getFontAlways()));
  }

  public native void setText(String text)
  /*-[
   ((RAREAPTextField*)proxy_).text=text;
  ]-*/
  ;

  @Override
  public native void setTextAlignment(HorizontalAlign hal, VerticalAlign val)
  /*-[
    [((RAREAPTextField*)proxy_)  setTextHorizontalAlignment: hal.ordinal];
  ]-*/
  ;

  public native void setTextEx(CharSequence text)
  /*-[
     [((RAREAPTextField*)proxy_) setCharSequence: text];
   ]-*/
  ;

  @Override
  public native String getHtmlText()
  /*-[
    return [((RAREAPTextField*)proxy_) getHTML];
  ]-*/
  ;

  @Override
  public UIInsets getMargin() {
    UIInsets in = new UIInsets();

    getMarginEx(in);

    return in;
  }

  @Override
  public native String getPlainText()
  /*-[
    return ((RAREAPTextField*)proxy_).text;
  ]-*/
  ;

  @Override
  public native int getSelectionEnd()
  /*-[
    NSRange r=((RAREAPTextField*)proxy_).selectedRangeEx;
    if(r.length==0 && r.location==0) {
      int n=[self getTextLength];
      if(n<cursorPosition_) {
        cursorPosition_=n;
      }
      return cursorPosition_;
    }
    else {
      cursorPosition_=(int)r.location;
      return (int)(r.location+r.length);
    }
   ]-*/
  ;

  @Override
  public native int getSelectionStart()
  /*-[
    NSRange r=((RAREAPTextField*)proxy_).selectedRangeEx;
    if(r.length==0 && r.location==0) {
      int n=[self getTextLength];
      if(n<cursorPosition_) {
        cursorPosition_=n;
      }
      return cursorPosition_;
    }
    else {
      cursorPosition_=(int)r.location;
      return cursorPosition_;
    }
   ]-*/
  ;

  @Override
  public native CharSequence getText()
  /*-[
    return ((RAREAPTextField*)proxy_).text;
  ]-*/
  ;

  @Override
  public native int getTextLength()
  /*-[
    return (int)((RAREAPTextField*)proxy_).text.length;
  ]-*/
  ;

  @Override
  public native boolean isEditable()
  /*-[
    return [((RAREAPTextField*)proxy_) isEditable];
  ]-*/
  ;

  native void getMarginEx(UIInsets insets)
  /*-[
    [((RAREAPTextField*)proxy_) getInsets: insets];
  ]-*/
  ;

  @Override
  protected native void setForegroundColorEx(UIColor fg)
  /*-[
    if(fg) {
      ((RAREAPTextField*)proxy_).textColor=[fg getAPColor];
    }
  ]-*/
  ;

  private static native Object createProxy()
  /*-[
    RAREAPTextField *f= [[RAREAPTextField alloc]init];
    f.allowsEditingTextAttributes=NO;
    [f setEditable:YES];
    return f;
  ]-*/
  ;

  public native void setSuggestionsEnabled(boolean enabled)
  /*-[
    ((RAREAPTextField*)proxy_).autocorrectionType = enabled ? UITextAutocorrectionTypeYes : UITextAutocorrectionTypeNo;
  ]-*/
  ;
}
