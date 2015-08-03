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

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.view.LabelView;

/**
 * An icon that draws text
 *
 * @author Don DeCoteau
 */
public class UITextIcon extends aUITextIcon {
  protected TextDrawable drawable;

  public UITextIcon(CharSequence text) {
    super(text);
  }

  public UITextIcon(CharSequence text, UIColor fg) {
    super(text, fg);
  }

  public UITextIcon(CharSequence text, UIColor fg, UIFont font, iPlatformBorder border) {
    super(text, fg, font, border);
  }

  public void paint(Canvas g, float x, float y, float width, float height) {
    drawable.setBounds((int) x, (int) y, (int) size.width, (int) size.height);
    drawable.draw(g);
  }

  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    paint(g.getView(), g.getCanvas(), UIScreen.snapToPosition(x), UIScreen.snapToPosition(y),
          UIScreen.snapToSize(width), UIScreen.snapToSize(height), 0);
  }

  public void paint(View c, Canvas g, float x, float y, float width, float height, int orientation) {
    paint(g, x, y, width, height);
  }

  public Drawable getDrawable(View view) {
    return drawable;
  }

  protected iActionComponent createComponent() {
    LabelView tv = new LabelView(AppContext.getAndroidContext());

    tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
    tv.setGravity(Gravity.CENTER);
    drawable = new TextDrawable();

    return new ActionComponent(tv);
  }

  class TextDrawable extends ColorDrawable {
    public void draw(Canvas canvas) {
      Rect      r = getBounds();
      LabelView v = (LabelView) label.getView();

      v.onPreDraw();

      int count = canvas.save();

      canvas.translate(r.left, r.top);
      v.draw(canvas);
      canvas.restoreToCount(count);
    }

    public int getIntrinsicHeight() {
      return getIconHeight();
    }

    public int getIntrinsicWidth() {
      return getIconWidth();
    }
  }
}
