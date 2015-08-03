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

import android.R;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import android.text.Html.ImageGetter;

import android.util.AttributeSet;

import android.view.View;

import android.widget.CheckBox;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.iComponentView;
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
public class CheckBoxView extends CheckBox implements iPainterSupport, iComponentView {
  private static final int[] INDETERMINATE_STATE_SET = { R.attr.state_middle };
  iPlatformComponentPainter  componentPainter;
  boolean                    indeterminate;
  OnCheckedChangeListener    onCheckedChangeListener;
  protected AndroidGraphics  graphics;
  private boolean            isTriState;

  public CheckBoxView(Context context) {
    this(context, null);
  }

  public CheckBoxView(Context context, AttributeSet attrs) {
    super(context, attrs);

    String  s  = Platform.getUIDefaults().getString("Rare.CheckBox.drawableName");
    UIColor fg = ColorUtils.getForeground();

    fg.setTextColor(this);

    if (s != null) {
      Drawable d = Platform.getAppContext().getResourceAsDrawable(s);

      if (d != null) {
        setButtonDrawable(d);
      }
    } else {
      if (fg.isDarkColor()) {
        setButtonDrawable(Platform.getAppContext().getResourceAsDrawable("Rare.btn.checkbox.light"));
      } else {
        setButtonDrawable(Platform.getAppContext().getResourceAsDrawable("Rare.btn.checkbox.dark"));
      }
    }

    FontUtils.getDefaultFont().setupTextView(this);
    setMaxLines(Short.MAX_VALUE);
    setSingleLine(false);
    setEllipsize(null);
  }

  public CheckBoxView(Context context, boolean tristate) {
    this(context, null);
    this.isTriState = tristate;
  }

  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

  public iPaintedButton.ButtonState getButtonState() {
    return Utils.getState(isEnabled(), isPressed(), isSelected(), false);
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public int getSuggestedMinimumHeight() {
    return super.getSuggestedMinimumHeight();
  }

  public int getSuggestedMinimumWidth() {
    return super.getSuggestedMinimumWidth();
  }

  public boolean isIndeterminate() {
    return indeterminate;
  }

  @Override
  public boolean performClick() {
    if (isTriState &&!indeterminate && isChecked()) {
      indeterminate = true;
      refreshDrawableState();

      if (onCheckedChangeListener != null) {
        onCheckedChangeListener.onCheckedChanged(this, true);

        return true;
      }

      return false;
    } else {
      indeterminate = false;

      return super.performClick();
    }
  }

  @Override
  public void setChecked(boolean checked) {
    indeterminate = false;
    super.setChecked(checked);
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public void setIndeterminate() {
    setChecked(true);
    refreshDrawableState();

    if (onCheckedChangeListener != null) {
      onCheckedChangeListener.onCheckedChanged(this, true);
    }
  }

  @Override
  public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
    super.setOnCheckedChangeListener(listener);
    onCheckedChangeListener = listener;
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

  @Override
  protected int[] onCreateDrawableState(int extraSpace) {
    int[] states = super.onCreateDrawableState(extraSpace + 1);

    if (indeterminate) {
      mergeDrawableStates(states, INDETERMINATE_STATE_SET);
    }

    return states;
  }

  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ViewHelper.onDetachedFromWindow(this);
  }

  protected void onDraw(Canvas canvas) {
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
