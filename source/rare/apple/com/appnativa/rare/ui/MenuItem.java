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

import com.appnativa.rare.platform.apple.ui.util.ImageUtils;

/*-[
#include "RAREAPMenuItem.h"
#include "com/appnativa/rare/ui/text/HTMLCharSequence.h"
]-*/
public class MenuItem {
  protected boolean      checkbox;
  protected Object       proxy;
  protected CharSequence text;

  public MenuItem(boolean checkbox) {
    this(createProxy(checkbox));
    this.checkbox = checkbox;
  }

  public MenuItem(Object proxy) {
    this.proxy = proxy;
  }

  public void dispose() {
    proxy = null;
  }

  public native void setCheckable(boolean checkable)
  /*-[
    checkbox_=checkable;
  ]-*/
  ;

  public native void setEnabled(boolean enabled)
  /*-[
    [((RAREAPMenuItem*)proxy_) setEnabled: enabled];
  ]-*/
  ;

  public void setIcon(iPlatformIcon icon) {
    UIImage img = ImageUtils.createImage(icon);

    if (img != null) {
      setImage(img.getProxy());
    }
  }

  public native void setSelected(boolean selected)
  /*-[
    if(checkbox_) {
      [((RAREAPMenuItem*)proxy_) setSelected: selected];
    }
  ]-*/
  ;

  public native void setText(CharSequence text)
  /*-[
    text= text==nil ? @"" : text;
    text_=text;
      if ([text isKindOfClass:[RAREHTMLCharSequence class]]) {
        [((RAREAPMenuItem*)proxy_) setAttributedTitle:(NSAttributedString *)[((RAREHTMLCharSequence*)text) getAttributedText]];
      }
      else {
        [((RAREAPMenuItem*)proxy_) setTitle: text.description];
      }
    ]-*/
  ;

  public native void setUserData(Object data)
  /*-[
    [((RAREAPMenuItem*)proxy_) setRepresentedObject: data];
  ]-*/
  ;

  public native void setVisible(boolean visible)
  /*-[
    [((RAREAPMenuItem*)proxy_) setVisible: visible];
  ]-*/
  ;

  public Object getProxy() {
    return proxy;
  }

  public CharSequence getText() {
    return text;
  }

  public native Object getUserData()
  /*-[
    return [((RAREAPMenuItem*)proxy_) representedObject];
  ]-*/
  ;

  public boolean isSeparator() {
    return false;
  }

  static native Object createProxy(boolean checkbox)
  /*-[
    RAREAPMenuItem* mi=[[RAREAPMenuItem alloc] initWithTitle: @"" keyEquivalent: @""];
    return mi;
  ]-*/
  ;

  protected native void setImage(Object apimage)
  /*-[
    [((RAREAPMenuItem*)proxy_) setAPImage:apimage];
  ]-*/
  ;

  protected native Object getParentUserData()
  /*-[
    return [((RAREAPMenuItem*)proxy_) getParentRepresentedObject];
  ]-*/
  ;
}
