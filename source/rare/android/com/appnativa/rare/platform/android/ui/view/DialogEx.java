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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.WindowManager;

/**
 *
 * @author Don DeCoteau
 */
public class DialogEx extends Dialog {
  Frame           frame;
  boolean         undecorated;
  private boolean usRuntimeDecorations;

  public DialogEx(Context context) {
    super(context);
    setCancelable(true);
    setCanceledOnTouchOutside(false);
  }

  public DialogEx(Context context, boolean rareDecorated) {
    super(context);
    setCancelable(true);
    setCanceledOnTouchOutside(false);
  }

  public DialogEx(Context context, int style) {
    super(context, style);
    undecorated = style == WindowManager.undecoratedStyle;
    setCancelable(true);
    setCanceledOnTouchOutside(false);
  }

  public void dismiss() {
    if (frame != null) {
      frame.close();

      return;
    }

    super.dismiss();
  }

  public void dispose() {
    Platform.getAppContext().getActiveWindows().remove(this);
    frame = null;

    if (isShowing()) {
      super.dismiss();
    }
  }

  @Override
  public void show() {
    super.show();

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

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (frame != null) {
      frame.reset(getWindow(), null);
    }
  }

  public boolean isUsRuntimeDecorations() {
    return usRuntimeDecorations;
  }

  public void setUsRuntimeDecorations(boolean usRuntimeDecorations) {
    this.usRuntimeDecorations = usRuntimeDecorations;
  }
}
