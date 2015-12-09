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

package com.appnativa.rare.widget;

import android.view.View;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.android.PlatformImpl;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.view.PopupWindowEx;
import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.viewer.iContainer;

/**
 * A widget that provides the user a way to trigger some predefined action
 *
 * @author Don DeCoteau
 */
public class PushButtonWidget extends aPushButtonWidget {

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public PushButtonWidget(iContainer parent) {
    super(parent);
  }

  @Override
  public void showPopupWidget() {
    if (PlatformHelper.isKeyboardVisible()) {
      PlatformHelper.hideVirtualKeyboard(this);
      ((PlatformImpl) Platform.getPlatform()).invokeLater(new Runnable() {
        @Override
        public void run() {
          showPopupWidgetEx();
        }
      }, 200);
    } else {
      showPopupWidgetEx();
    }
  }

  @Override
  public void setValue(Object value) {
    CharSequence s;

    if (value instanceof CharSequence) {
      s = (CharSequence) value;
    } else {
      theValue = value;
      s        = toString(this);
    }

    setText(s);
  }

  @Override
  protected void configurePainters(PushButton cfg, int buttonStyle) {
    if (buttonStyle != PushButton.CButtonStyle.platform) {
      View v = getDataView();

      v.setPadding(0, 0, 0, 0);
      getDataView().setBackground(NullDrawable.getInstance());
    }

    super.configurePainters(cfg, buttonStyle);

    if (buttonStyle != PushButton.CButtonStyle.platform) {}
  }

  @Override
  protected ActionComponent createButton(Button cfg) {
    return new ActionComponent(getAppContext().getComponentCreator().getButton(getViewer(), (PushButton) cfg));
  }

  @Override
  protected iPopup createPopupComponent() {
    PopupWindowEx win = new PopupWindowEx(getAppContext().getActivity());

    win.setAnimationStyle(0);

    return win;
  }

  @Override
  protected void updatePopupComponent(iPopup popup) {
    ((PopupWindowEx) popup).update();
  }
}
