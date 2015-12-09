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

import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.event.iActionListener;

/*-[
#import "com/appnativa/rare/ui/text/HTMLCharSequence.h"
#import "RAREAPMenuItem.h"
#import "RAREAPMenu.h"
 ]-*/
public class Menu extends MenuItem implements iMenuBarComponent {
  private static Menu  menuBar;
  protected UIFont     font;
  protected UIMenuItem subMenuProxy;
  protected Object     userData;

  public Menu() {
    super(createMenuProxy(false));
  }

  public Menu(String text) {
    super(createMenuProxy(false));
    setText(text);
  }

  protected Menu(Object proxy) {
    super(proxy);
  }

  @Override
  public void add(UIMenuItem mi) {
    add(-1, mi);
  }

  public void add(int pos, UIMenuItem mi) {
    if (mi instanceof UIMenu) {
      UIMenu     um  = (UIMenu) mi;
      UIMenuItem sub = new UIMenuItem(mi.getOriginalValue(), mi.getIcon(), mi.getLinkedData(), false);

      if (mi.getDisabledIcon() != null) {
        sub.setDisabledIcon(mi.getDisabledIcon());
      }

      Menu m = (Menu) um.getMenuItem();

      m.subMenuProxy = sub;
      addMenu(pos, um, sub);
    } else {
      addMenuItem(pos, mi);
    }
  }

  @Override
  public void addActionListener(iActionListener l) {}

  @Override
  public void dispose() {
    if (subMenuProxy != null) {
      subMenuProxy.dispose();
    }

    proxy        = null;
    subMenuProxy = null;
    userData     = null;
    font         = null;
  }

  @Override
  public native void remove(UIMenuItem mi)
  /*-[
    [((RAREAPMenu*)proxy_) removeItem: (RAREAPMenuItem*)[mi getProxy]];
  ]-*/
  ;

  @Override
  public void removeActionListener(iActionListener l) {}

  @Override
  public native void removeAll()
  /*-[
    [((RAREAPMenu*)proxy_) removeAllItems];
  ]-*/
  ;

  public native void revalidate()
  /*-[
    [((RAREAPMenu*)proxy_) update];
  ]-*/
  ;

  @Override
  public void setEnabled(boolean enabled) {
    if (subMenuProxy != null) {
      subMenuProxy.setEnabled(enabled);
    }
  }

  public native void setFont(UIFont font)
  /*-[
    font_ = font;
    if(font!=nil) {
      [((RAREAPMenu*)proxy_) setAPFont: font->proxy_];
    }
  ]-*/
  ;

  @Override
  public void setIcon(iPlatformIcon icon) {}

  @Override
  public void setSelected(boolean selected) {}

  @Override
  public void setText(CharSequence text) {
    if (subMenuProxy != null) {
      subMenuProxy.setText(text);
    } else {
      setTextEx(text);
    }
  }

  @Override
  public void setTitle(CharSequence title) {}

  @Override
  public void setTitleIcon(iPlatformIcon icon) {}

  @Override
  public void setUserData(Object data) {
    userData = data;
  }

  @Override
  public void setVisible(boolean visible) {
    if (isMenuBar()) {
      setMenuBarVisible(visible);
    } else if (subMenuProxy != null) {
      subMenuProxy.setVisible(visible);
    }
  }

  public static Menu getMenuBar() {
    if (menuBar == null) {
      menuBar = new Menu(createMenuProxy(true));
    }

    return menuBar;
  }

  @Override
  public CharSequence getText() {
    if (subMenuProxy != null) {
      return subMenuProxy.getText();
    } else {
      return text;
    }
  }

  @Override
  public iActionComponent getTitleComponent() {
    return null;
  }

  @Override
  public Object getUserData() {
    return userData;
  }

  public native boolean hasParentMenu()
  /*-[
    return [((RAREAPMenu*)proxy_) hasParentMenu];
  ]-*/
  ;

  @Override
  public boolean hasTitleComponent() {
    return false;
  }

  public boolean isEnabled() {
    if (isMenuBar()) {
      return true;
    }

    if (subMenuProxy != null) {
      return subMenuProxy.isEnabled();
    }

    return true;
  }

  public boolean isMenuBar() {
    return this == menuBar;
  }

  @Override
  public boolean isVisible() {
    if (isMenuBar()) {
      return isMenuBarVisible();
    }

    if (subMenuProxy != null) {
      return subMenuProxy.isVisible();
    }

    return true;
  }
  
  public native void setHeaderView(View view)
  /*-[
    RAREAPMenu* menu=(RAREAPMenu*)proxy_;
    [menu setHeader: view ? (UIView*)[view getProxy] : nil];
  ]-*/
  ;

  native void addMenu(int pos, UIMenu menu, UIMenuItem subProxy)
  /*-[
    RAREAPMenu* m=(RAREAPMenu*)proxy_;
    RAREAPMenu* subMenu=(RAREAPMenu*)[menu getProxy];
    RAREAPMenuItem* subItem=[subProxy getProxy];
    if(pos<0) {
      [m addItem: subItem];
    }
    else {
      [m insertItem: subItem atIndex: pos];
    }
    [m setSubmenu: subMenu forItem: subItem];
  ]-*/
  ;

  static native Object createMenuProxy(boolean mainMenu)
  /*-[
    return [[RAREAPMenu alloc] initWithTitle: @"" keyEquivalent: @""];
  ]-*/
  ;

  native void setMenuBarVisible(boolean visible)
  /*-[
    [RAREAPMenu setMenuBarVisible: visible];
  ]-*/
  ;

  native boolean isMenuBarVisible()
  /*-[
    return [RAREAPMenu menuBarVisible];
  ]-*/
  ;

  protected native void addMenuItem(int pos, UIMenuItem mi)
  /*-[
    RAREAPMenu* menu=(RAREAPMenu*)proxy_;
    if(pos<0) {
      [menu addItem: (RAREAPMenuItem*)[mi getProxy]];
    }
    else {
      [menu insertItem: (RAREAPMenuItem*)[mi getProxy] atIndex: pos];
    }
  ]-*/
  ;

  protected native void setTextEx(CharSequence text)
  /*-[
    text= text==nil ? @"" : text;
    text_=text;
    [((RAREAPMenu*)proxy_) setTitle: text.description];
  ]-*/
  ;
}
