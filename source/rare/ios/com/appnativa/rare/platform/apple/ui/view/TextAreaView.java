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

import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.text.HTMLCharSequence;

/*-[
 #import "RAREAPTextView.h"
 #import "RAREAPScrollView.h"
 #import "APView+Component.h"
 ]-*/

/**
 *
 * @author Don DeCoteau
 */
public class TextAreaView extends aTextEditorView {
  ContainerPanel container;

  public TextAreaView() {
    super(createProxy());
  }

  public TextAreaView(String text) {
    this();
    setText(text);
  }

  public native void removeNativeBorder()
  /*-[
    ((RAREAPTextView*)proxy_).layer.borderWidth=0;
  ]-*/
  ;

  public native void setAllowTextAttributes(boolean allow)
  /*-[
    ((RAREAPTextView*)proxy_).allowsEditingTextAttributes=allow;
  ]-*/
  ;

  public native void setAutoShowKeyboard(boolean show)
  /*-[
   [((RAREAPTextView*)proxy_) setAutoShowKeyboard: show];
   ]-*/
  ;

  @Override
  public native void setEditable(boolean editable)
  /*-[
    [((RAREAPTextView*)proxy_) setEditable: editable];
  ]-*/
  ;

  public native void setFont(UIFont font)
  /*-[
     font_ = font;
     if(font) {
       [((RAREAPTextView*)proxy_) setFont: (UIFont*)[font getIOSProxy]];
     }
   ]-*/
  ;

  public void setMargin(int top, int right, int bottom, int left) {}

  public native void setShowKeyBoard(boolean show)
  /*-[
             [((RAREAPTextView*)proxy_) setShowKeyBoard: show];
     ]-*/
  ;

  public void setText(CharSequence text) {
    setTextEx(HTMLCharSequence.checkSequence(text, getFontAlways()));
  }

  public native void setTextEx(CharSequence text)
  /*-[
     [((RAREAPTextView*)proxy_) setCharSequence: text];
   ]-*/
  ;

  public void setVisibleLines(int lines) {}

  @Override
  public iPlatformComponent getComponent() {
    if (component == null) {
      component = new Component(this);
    }

    return component;
  }

  @Override
  public native String getHtmlText()
  /*-[
     return [((RAREAPTextView*)proxy_) getHTML];
  ]-*/
  ;

  @Override
  public String getPlainText() {
    return getText();
  }

  @Override
  public native int getSelectionEnd()
  /*-[
    NSRange r=((RAREAPTextView*)proxy_).selectedRangeEx;
    return (int)(r.location+r.length);
   ]-*/
  ;

  @Override
  public native int getSelectionStart()
  /*-[
    return (int)((RAREAPTextView*)proxy_).selectedRangeEx.location;
   ]-*/
  ;

  public native String getText()
  /*-[
    return [((RAREAPTextView*)proxy_) text];
   ]-*/
  ;

  @Override
  public native int getTextLength()
  /*-[
    return (int)((RAREAPTextView*)proxy_).text.length;
   ]-*/
  ;

  @Override
  public native boolean isEditable()
  /*-[
    return [((RAREAPTextView*)proxy_) isEditable];
  ]-*/
  ;

  @Override
  public boolean isScrollView() {
    return true;
  }

  @Override
  protected void disposeEx() {
    super.disposeEx();
    container = null;
  }

  protected native void setForegroundColorEx(UIColor fg)
  /*-[
    if(fg) {
      ((RAREAPTextView*)proxy_).textColor=[fg getAPColor];
    }
  ]-*/
  ;

  private static native Object createProxy()
  /*-[
    RAREAPTextView *f= [[RAREAPTextView alloc]init];
    [f setEditable:YES];
    return f;
  ]-*/
  ;
}
