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

import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iPlatformBorder;

/*-[
 #import "com/appnativa/rare/ui/text/HTMLCharSequence.h"
 ]-*/
public class ControlView extends View {
  protected iChangeListener changeListener;

  public ControlView(Object uicontrol) {
    super(uicontrol);
  }

  @Override
  public void setChangeListener(iChangeListener l) {
    changeListener = l;
  }

  protected ControlView() {}

  @Override
  public void borderChanged(iPlatformBorder newBorder) {
    super.borderChanged(newBorder);

    if (newBorder == null) {
      setMargin(2, 2, 2, 2);
    } else {
      setMargin(newBorder.getBorderInsets(null));
    }
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
  public native void setSelected(boolean selected)
  /*-[
    ((UIControl*)proxy_).selected=selected;
  ]-*/
  ;

  @Override
  public native boolean isPressed()
  /*-[
    return ((UIControl*)proxy_).highlighted;
  ]-*/
  ;

  @Override
  public native boolean isSelected()
  /*-[
    return ((UIControl*)proxy_).selected;
  ]-*/
  ;

  @Override
  protected native void setEnabledEx(boolean b)
  /*-[
    UIView* v=(UIView*)proxy_;
    v.userInteractionEnabled = b;
  ]-*/
  ;
}
