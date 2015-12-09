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
import android.graphics.Canvas;
import android.widget.EditText;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.ui.spinner.DateEditor;
import com.appnativa.rare.ui.spinner.DatePickerEditor;
import com.appnativa.rare.ui.spinner.DefaultEditor;
import com.appnativa.rare.ui.spinner.ListEditor;
import com.appnativa.rare.ui.spinner.NumberEditor;
import com.appnativa.rare.ui.spinner.NumberPickerEditor;
import com.appnativa.rare.ui.spinner.SpinnerDateModel;
import com.appnativa.rare.ui.spinner.SpinnerListModel;
import com.appnativa.rare.ui.spinner.SpinnerNumberModel;
import com.appnativa.rare.ui.spinner.iSpinnerEditor;
import com.appnativa.rare.ui.spinner.iSpinnerModel;

public class SpinnerComponent extends aSpinnerComponent {
  public SpinnerComponent(Context ctx) {
    super(new SpinnerView(ctx));
  }

  @Override
  public void setVisibleCharacters(int count) {
    if (editor instanceof DefaultEditor) {
      EditText e = (EditText) editor.getComponent().getView();

      e.setMaxEms(count);
    }
  }

  protected iSpinnerEditor createEditor(iSpinnerModel model) {
    Context ctx = getView().getContext();

    if (model instanceof SpinnerNumberModel) {
      if (!useDesktopStyleEditor &&!((SpinnerNumberModel) model).isSupportDecimalValues()) {
        buttonsVisible = false;

        return new NumberPickerEditor(ctx, (SpinnerNumberModel) model, this);
      }
    }
    else if (!useDesktopStyleEditor && model instanceof SpinnerDateModel) {
      buttonsVisible = false;
      DatePickerEditor e= new DatePickerEditor(ctx, (SpinnerDateModel)model, this);
      e.finishConfiguring(getWidget());
      return e;
    }

    setupSpinnerForDesktopStyle();

    if (model instanceof SpinnerNumberModel) {
      return new NumberEditor(ctx, (SpinnerNumberModel) model);
    }

    if (model instanceof SpinnerListModel) {
      return new ListEditor(ctx, (SpinnerListModel) model);
    }

    if (model instanceof SpinnerDateModel) {
      return new DateEditor(ctx, (SpinnerDateModel) model);
    }

    return new DefaultEditor(ctx, model);
  }

  @Override
  protected aListItemRenderer createListItemRenderer() {
    return new ListItemRenderer();
  }

  static class SpinnerView extends ViewGroupEx {
    private UIRectangle rect;

    public SpinnerView(Context context) {
      super(context);
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
      aSpinnerComponent sp = (aSpinnerComponent) Component.fromView(this);

      if (rect == null) {
        rect = new UIRectangle();
      }

      rect.setBounds(0, 0, getWidth(), getHeight());
      sp.paintButtons(graphics, rect);
      super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      int              height = bottom - top - getPaddingBottom() - getPaddingTop();
      int              width  = right - left - getPaddingRight() - getPaddingLeft();
      SpinnerComponent sp     = (SpinnerComponent) Component.fromView(this);

      sp.layout(width, height);
    }
  }
}
