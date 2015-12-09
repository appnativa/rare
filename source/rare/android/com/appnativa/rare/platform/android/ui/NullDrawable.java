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

package com.appnativa.rare.platform.android.ui;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.util.StateSet;

import android.view.View;

import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.util.PainterUtils;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.iPlatformShape;

import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class NullDrawable extends ColorDrawable implements iPlatformIcon, iPlatformBorder {
  private static NullDrawable _instance = new NullDrawable(false);
  int                         height;
  boolean                     stateful;
  int                         width;
  protected UIInsets          insets;

  public NullDrawable(int width, int height) {
    this.width  = Math.max(width, 0);
    this.height = Math.max(height, 0);
  }

  public NullDrawable(int top, int right, int bottom, int left) {
    setInsets(top, right, bottom, left);
  }

  private NullDrawable(boolean stateful) {
    this.stateful = stateful;
  }

  @Override
  public void clip(iPlatformGraphics g, float x, float y, float width, float height) {}

  public void clip(View view, Canvas g, float x, float y, float width, float height) {}

  @Override
  public boolean clipsContents() {
    return false;
  }

  @Override
  public final void draw(Canvas canvas) {}

  @Override
  public void paint(Canvas g, float x, float y, float width, float height) {}

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {}

  @Override
  public final void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean last) {}

  public final void paint(View view, Canvas g, float x, float y, float width, float height, boolean last) {}

  public void setInsets(int top, int right, int bottom, int left) {
    if (insets == null) {
      insets = new UIInsets(top, right, bottom, left);
    } else {
      insets.set(top, right, bottom, left);
    }

    this.invalidateSelf();
  }

  @Override
  public void setPadForArc(boolean pad) {}

  @Override
  public float getArcHeight() {
    return 0;
  }

  @Override
  public float getArcWidth() {
    return 0;
  }

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    if (insets == null) {
      insets = new UIInsets();
    }

    if (this.insets == null) {
      insets.set(0, 0, 0, 0);
    } else {
      insets.set(this.insets);
    }

    return insets;
  }

  @Override
  public UIInsets getBorderInsetsEx(UIInsets insets) {
    if (insets == null) {
      insets = new UIInsets();
    } else {
      insets.set(0, 0, 0, 0);
    }

    return insets;
  }

  @Override
  public iPlatformIcon getDisabledVersion() {
    return this;
  }

  @Override
  public Drawable getDrawable(View view) {
    return this;
  }

  @Override
  public int getIconHeight() {
    return height;
  }

  @Override
  public int getIconWidth() {
    return width;
  }

  public static NullDrawable getInstance() {
    return _instance;
  }

  @Override
  public int getIntrinsicHeight() {
    return height;
  }

  @Override
  public int getIntrinsicWidth() {
    return width;
  }

  @Override
  public int getMinimumHeight() {
    return height;
  }

  @Override
  public int getMinimumWidth() {
    return width;
  }

  @Override
  public int getModCount() {
    return 0;
  }

  @Override
  public boolean getPadding(Rect padding) {
    if (this.insets != null) {
      AndroidHelper.insetsToRect(this.insets, padding);

      return true;
    }

    return super.getPadding(padding);
  }

  @Override
  public iPlatformShape getShape(iPlatformGraphics g, float x, float y, float width, float height) {
    return null;
  }

  public static NullDrawable getStatefulInstance() {
    return new NullDrawable(true);
  }

  @Override
  public boolean isPadForArc() {
    return false;
  }

  @Override
  public boolean isPaintLast() {
    return false;
  }

  @Override
  public boolean isRectangular() {
    return true;
  }

  @Override
  public boolean isStateful() {
    return stateful;
  }

  @Override
  protected boolean onStateChange(int[] state) {
    if ((state == null) ||!stateful) {
      return false;
    }

    boolean changed = false;

    if (StateSet.stateSetMatches(PainterUtils.STATE_DISABLED, state)
        || StateSet.stateSetMatches(PainterUtils.STATE_ENABLED, state)) {
      changed = true;
    } else {
      changed = StateSet.stateSetMatches(PainterUtils.STATE_PRESSED, state)
                || StateSet.stateSetMatches(PainterUtils.STATE_UNPRESSED, state);

      if (!changed) {
        changed = StateSet.stateSetMatches(PainterUtils.STATE_SELECTED, state)
                  || StateSet.stateSetMatches(PainterUtils.STATE_DESELECTED, state);
      }
    }

    if (changed) {
      invalidateSelf();
    }

    return changed;
  }

  protected void setInsets(UIInsets insets) {
    this.insets = insets;
  }

  @Override
  public void handleCustomProperties(Map map) {}

  @Override
  public iPlatformPath getPath(iPlatformPath p, float x, float y, float width, float height, boolean forClip) {
    return null;
  }

  @Override
  public boolean isFocusAware() {
    return false;
  }

  @Override
  public boolean isEnabledStateAware() {
    return false;
  }
}
