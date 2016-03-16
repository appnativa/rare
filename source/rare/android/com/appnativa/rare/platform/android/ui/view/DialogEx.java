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

package com.appnativa.rare.platform.android.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.android.MainActivity;
import com.appnativa.rare.ui.Frame;
import com.appnativa.util.IdentityArrayList;

/**
 *
 * @author Don DeCoteau
 */
public class DialogEx extends Dialog {
  protected Frame   frame;
  protected boolean undecorated;

  public DialogEx(Context context) {
    super(context);
    setCancelable(true);
    setCanceledOnTouchOutside(false);
  }

  public DialogEx(Context context, int style) {
    super(context, style);
    undecorated = style == com.appnativa.rare.ui.WindowManager.undecoratedStyle;
    setCancelable(true);
    setCanceledOnTouchOutside(false);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    IdentityArrayList aw = Platform.getAppContext().getActiveWindows();
    Object            o  = aw.get(aw.size() - 1);

    if (o instanceof PopupWindowEx) {
      PopupWindowEx p = (PopupWindowEx) o;

      if (p.isTransient()) {
        p.dismiss();

        return true;
      }
    }

    return super.dispatchTouchEvent(ev);
  }

  @Override
  public void dismiss() {
    
    if (frame != null) {
      PlatformHelper.hideVirtualKeyboard(frame);
      frame.close();

      return;
    }

    super.dismiss();
  }

  public void dispose() {
    Platform.getAppContext().getActiveWindows().remove(this);
    if (frame != null) {
      PlatformHelper.hideVirtualKeyboard(frame);
      return;
    }
    frame = null;

    if (isShowing()) {
      super.dismiss();
    }
  }

  @Override
  public void show() {
    // Set the dialog to not focusable.
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

    MainActivity ma = (MainActivity) (Platform.getAppContext().getActivity());

    if (ma.isUseFullScreen()) {
      ma.setUseFullScreen(getWindow(), true);
    }

    // Show the dialog with NavBar hidden.
    super.show();
    // Set the dialog to focusable again.
    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

    if (isShowing()) {
      Platform.getAppContext().getActiveWindows().addIfNotPresent(this);
    }
  }

  public void setFrame(Frame frame) {
    this.frame = frame;
  }

  public Frame getFrame() {
    return frame;
  }

  public boolean isUndecorated() {
    return undecorated;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    if (frame != null) {
      frame.reset(getWindow());
    }
  }
}
