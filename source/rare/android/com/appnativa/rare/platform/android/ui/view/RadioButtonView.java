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

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import android.text.Html.ImageGetter;

import android.util.AttributeSet;

import android.view.View;

import android.widget.RadioButton;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iPaintedButton;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class RadioButtonView extends RadioButton implements iPainterSupport {
  iPlatformComponentPainter componentPainter;
  protected AndroidGraphics graphics;
  OffsettingDrawable        offsettingDrawable;

  public RadioButtonView(Context context) {
    this(context, null);
  }

  @Override
  public void setButtonDrawable(Drawable d) {
    if (d != null) {
      offsettingDrawable = new OffsettingDrawable(d);
    } else {
      offsettingDrawable = null;
    }

    super.setButtonDrawable(offsettingDrawable);
  }

  public RadioButtonView(Context context, AttributeSet attrs) {
    super(context, attrs);

    UIColor fg = ColorUtils.getForeground();

    fg.setTextColor(this);

    String s = Platform.getUIDefaults().getString("Rare.RadioButton.drawableName");

    if (s != null) {
      Drawable d = Platform.getAppContext().getResourceAsDrawable(s);

      if (d != null) {
        setButtonDrawable(d);
      }
    } else {
      if (fg.isDarkColor()) {
        setButtonDrawable(Platform.getAppContext().getResourceAsDrawable("Rare.btn.radio.light"));
      } else {
        setButtonDrawable(Platform.getAppContext().getResourceAsDrawable("Rare.btn.radio.dark"));
      }
    }

    FontUtils.getDefaultFont().setupTextView(this);
    setMaxLines(Short.MAX_VALUE);
    setSingleLine(false);
    setEllipsize(null);
  }

  public iPaintedButton.ButtonState getButtonState() {
    return Utils.getState(isEnabled(), isPressed(), isSelected(), false);
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public final View getView() {
    return this;
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public void setText(CharSequence text, BufferType type) {
    text = LabelView.checkText(text, (ImageGetter) Platform.findWidgetForComponent(Component.fromView(this)));
    super.setText(text, type);
  }

  public String toString() {
    CharSequence s = getText();

    return (s == null)
           ? ""
           : s.toString();
  }

  @Override
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    resolveStateValues();
  }

  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
  }

  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ViewHelper.onDetachedFromWindow(this);
  }

  protected void onDraw(Canvas canvas) {
    if (offsettingDrawable != null) {
      CharSequence s = getText();

      if ((s == null) || (s.length() == 0)) {
        offsettingDrawable.setOffset((getWidth() - offsettingDrawable.getIntrinsicWidth()) / 2, 0);
      }
    }

    graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

    final iPlatformComponentPainter cp = componentPainter;

    if (cp == null) {
      super.onDraw(canvas);
    } else {
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, false);
      super.onDraw(canvas);
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, true);
    }

    graphics.clear();
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }

  protected void resolveStateValues() {
    if (componentPainter != null) {
      PainterHolder ph = componentPainter.getPainterHolder();

      if (ph != null) {
        ButtonState bs = getButtonState();
        UIColor     fg = ph.getForeground(bs, true);

        if (fg != null) {
          setTextColor(fg.getColor());
        }
      }
    }
  }
}
