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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.WindowTarget;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPopup;
/*-[
 #import "AppleHelper.h"
 #import "APView+Component.h"
 #import "RAREAPPopupWindow.h"
 ]-*/
import com.appnativa.util.IdentityArrayList;

import java.util.Map;

public class PopupWindow extends Window implements iPopup {
  float           x                     = Short.MIN_VALUE;
  float           y                     = Short.MIN_VALUE;
  private boolean requestFocusWhenShown = true;
  EventListenerList listenerList;

  public PopupWindow() {
    this(false);
  }

  public PopupWindow(boolean decorated) {
    this(createProxy());

    if (decorated) {
      setDecorated(decorated);
    }
  }
  @Override
  public void disposeEx() {
    super.disposeEx();
    listenerList=null;
  }
  protected PopupWindow(Object nsview) {
    super(nsview);
  }

  @Override
  public void addPopupMenuListener(iPopupMenuListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();

      iPopupMenuListener pl = new iPopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(ExpansionEvent e) {
          if (listenerList != null) {    // can be null if disposed was called
            Utils.firePopupEvent(listenerList, e, true);
          }
        }
        @Override
        public void popupMenuWillBecomeInvisible(ExpansionEvent e) {
          if (listenerList != null) {    // can be null if disposed was called
            Utils.firePopupEvent(listenerList, e, false);
          }
        }
        @Override
        public void popupMenuCanceled(ExpansionEvent e) {
          if (listenerList != null) {    // can be null if disposed was called
            Utils.firePopupCanceledEvent(listenerList, e);
          }
        }
      };

      setPopupMenuListener(pl);
    }

    listenerList.add(iPopupMenuListener.class, l);
  }

  @Override
  public native void cancelPopup(boolean useAnimation)
  /*-[
    [((RAREAPPopupWindow*)proxy_) cancelPopup: useAnimation];
  ]-*/
  ;

  @Override
  public void hidePopup() {
    setVisible(false);
  }

  @Override
  public void removePopupMenuListener(iPopupMenuListener l) {
    if (listenerList != null) {
      listenerList.remove(iPopupMenuListener.class, l);
    }
  }

  @Override
  public void showModalPopup() {
    setModal(true);
    showPopup(null, Short.MIN_VALUE, Short.MIN_VALUE);
  }

  @Override
  public void showPopup() {
    showPopup(null, x, y);
  }
  
  @Override
  public void showPopup(float x, float y) {
    showPopup(null, x, y);
  }

  @Override
  protected void setVisibleEx(boolean visible) {
    super.setVisibleEx(visible);

    if (!visible) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          dispose();
        }
      });
    }
  }

  @Override
  public void showPopup(iPlatformComponent ref, float x, float y) {
    this.x = x;
    this.y = y;
    PlatformHelper.hideVirtualKeyboard(ref);

    if (!sizeSet) {
      pack();
    }

    do {
      if ((x == Short.MIN_VALUE) && (y == Short.MIN_VALUE) && (ref == null)) {
        centerOnScreen();

        break;
      }

      if (x == Short.MIN_VALUE) {
        y = (ScreenUtils.getSize().width - getWidth()) / 2;
      }

      if (y == Short.MIN_VALUE) {
        y = (ScreenUtils.getSize().height - getHeight()) / 2;
      }

      if (ref != null) {
        UIPoint loc = ref.getLocationOnScreen();

        y += loc.y;
        x += loc.x;

        UIDimension size = ref.getOrientedSize(null);

        y += size.height;
      }

      setLocation(Math.round(x), Math.round(y));
    } while(false);

    setVisible(true);

    if (requestFocusWhenShown) {
      requestFocus();
    }
  }

  @Override
  public native void setDecorated(boolean decorated)
  /*-[
    [((RAREAPPopupWindow*)proxy_) setDecorated: decorated];
  ]-*/
  ;

  @Override
  public native void setFocusable(boolean focusable)
  /*-[
    ((RAREAPPopupWindow*)proxy_).focusable=focusable;
  ]-*/
  ;

  @Override
  public void setOptions(Map<String, String> options) {
  }

  @Override
  public void setPopupLocation(float x, float y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public void setPopupOwner(iPlatformComponent component) {
    // TODO Auto-generated method stub
  }

  public void setRequestFocusWhenShown(boolean requestFocusWhenShown) {
    this.requestFocusWhenShown = requestFocusWhenShown;
  }

  @Override
  public native void setTimeout(int timeout)
  /*-[
        ((RAREAPPopupWindow*)proxy_).timeout=(NSInteger)ceil(timeout/1000.0);
  ]-*/
  ;

  @Override
  protected boolean handleOutsideTouch() {
    if (isTransient()) {
      IdentityArrayList windows = Platform.getAppContext().getActiveWindows();
      int               len     = windows.size();

      if ((len > 0) && (windows.get(len - 1) == this)) {
        cancelPopup(true);

        return true;
      }
      return false;
    }
    return true;
  }

  @Override
  public native void setTransient(boolean istransient)
  /*-[
    ((RAREAPPopupWindow*)proxy_).transient=istransient;
  ]-*/
  ;

  @Override
  public void getPreferredSize(UIDimension size) {
    getPreferredSize(size, 0);
  }

  @Override
  public native boolean isFocusable()
  /*-[
    return ((RAREAPPopupWindow*)proxy_).focusable;
  ]-*/
  ;

  public boolean isRequestFocusWhenShown() {
    return requestFocusWhenShown;
  }

  @Override
  public native boolean isTransient()
  /*-[
    return ((RAREAPPopupWindow*)proxy_).transient;
  ]-*/
  ;

  protected static native Object createProxy()
  /*-[
    return [RAREAPPopupWindow new];
  ]-*/
  ;

  @Override
  protected void createTarget() {
    String name = "_popup_window_" + Integer.toHexString((int)hashCode());

    target = new WindowTarget(Platform.getAppContext(), name, rootPane);
  }

  protected native void setPopupMenuListener(iPopupMenuListener l)
  /*-[
    [((RAREAPPopupWindow*)proxy_) setPopupMenuListener: l];
  ]-*/
  ;
}
