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

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.android.PlatformImpl;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.PopupListBoxHandler;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.view.EditTextEx;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.widget.iWidget;

public class ComboBoxComponent extends aComboBoxComponent {
  public ComboBoxComponent(iWidget context) {
    super(new ComboBoxView(context.getAppContext().getActivity()));
  }

  public iListHandler createListHandler(iPlatformListDataModel model) {
    return new ComboBoxListHandler(getWidget(), this, model);
  }

  public iPlatformListHandler createListHandler(iWidget context, iPlatformListDataModel model) {
    return new ComboBoxListHandler(context, this, model);
  }

  public void showPopup() {
    if (PlatformHelper.isKeyboardVisible()) {
      PlatformHelper.hideVirtualKeyboard(this);
      ((PlatformImpl) Platform.getPlatform()).invokeLater(new Runnable() {
        public void run() {
          showPopupEx();
        }
      }, 100);
    } else {
      showPopupEx();
    }
  }

  @Override
  protected void willShowPopup(iPopup p, UIRectangle bounds) {
    if (usingDefaultBorder) {
      if (popupBounds.y < 0) {
        popupBounds.y += ScreenUtils.PLATFORM_PIXELS_2;
      } else if (popupBounds.y == 0) {
        popupBounds.y -= ScreenUtils.PLATFORM_PIXELS_2;
      }
    }
  }

  public void setVisibleCharacters(int count) {
    if (editor instanceof EditTextEx) {
      ((EditText) editor).setEms((int) Math.ceil((float) (count * 12) / 16f));
    }
  }

  protected iPlatformTextEditor createEditor() {
    EditTextEx e = EditTextEx.createEditText(((View) view).getContext());
    int i=e.getInputType();
    i ^= InputType.TYPE_TEXT_FLAG_AUTO_CORRECT;
    i|=InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
    e.setInputType(i);
    e.setPadding(0, 0, 0, 0);
    e.setBackground(NullDrawable.getInstance());

    int[][] states = new int[2][];

    states[0] = new int[] { -android.R.attr.state_enabled };
    states[1] = new int[0];
    e.setTextColor(new ColorStateList(states, new int[] { Color.LTGRAY, Color.BLACK }));
    e.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
    e.setSingleLine(true);
    e.setEllipsize(TruncateAt.END);
    ((ActionComponent) e.getComponent()).setAutoAdjustSize(false);

    return e;
  }

  public static class ComboBoxListHandler extends PopupListBoxHandler {
    aComboBoxComponent cb;

    public ComboBoxListHandler(iWidget context, aComboBoxComponent cb, iPlatformListDataModel model) {
      super(context, model, false);
      this.cb = cb;
    }

    @Override
    public void clear() {
      super.clear();

      if (cb != null) {
        cb.setEditorValue("");
      }
    }

    public void setSelectedIndex(int index) {
      super.setSelectedIndex(index);

      if (cb != null) {
        cb.setEditorValue(getSelectedItem());
      }
    }
  }


  static class ComboBoxView extends ViewGroupEx {
    public ComboBoxView(Context context) {
      super(context);
      setMeasureType(MeasureType.HORIZONTAL);
    }

    public void draw(Canvas canvas) {
      if (matrix != null) {
        canvas.concat(matrix);
      }

      graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

      final iPlatformComponentPainter cp = componentPainter;

      if (cp == null) {
        super.draw(canvas);
      } else {
        cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, false);
        super.draw(canvas);
        cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, true);
      }

      graphics.clear();
    }

    protected void onDraw(Canvas canvas) {
      aComboBoxComponent cb = (aComboBoxComponent) (aComboBoxComponent) Component.fromView(this);

      cb.paintIcon(graphics);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      ((aComboBoxComponent) Component.fromView(this)).layout(right - left, bottom - top);
    }
  }
}
