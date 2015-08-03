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

import android.content.res.Resources;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class OffsettingDrawable extends Drawable {
  Drawable drawable;
  int      leftOffset;
  int      topOffset;

  public OffsettingDrawable(Drawable drawable) {
    super();
    this.drawable = drawable;
  }

  public void clearColorFilter() {
    drawable.clearColorFilter();
  }

  public void draw(Canvas canvas) {
    drawable.draw(canvas);
  }

  public boolean equals(Object o) {
    return drawable.equals(o);
  }

  public Callback getCallback() {
    return drawable.getCallback();
  }

  public int getChangingConfigurations() {
    return drawable.getChangingConfigurations();
  }

  public ConstantState getConstantState() {
    return drawable.getConstantState();
  }

  public Drawable getCurrent() {
    return drawable.getCurrent();
  }

  public int getIntrinsicHeight() {
    return drawable.getIntrinsicHeight();
  }

  public int getIntrinsicWidth() {
    return drawable.getIntrinsicWidth();
  }

  public int getMinimumHeight() {
    return drawable.getMinimumHeight();
  }

  public int getMinimumWidth() {
    return drawable.getMinimumWidth();
  }

  public int getOpacity() {
    return drawable.getOpacity();
  }

  public boolean getPadding(Rect padding) {
    return drawable.getPadding(padding);
  }

  public int[] getState() {
    return drawable.getState();
  }

  public Region getTransparentRegion() {
    return drawable.getTransparentRegion();
  }

  public int hashCode() {
    return drawable.hashCode();
  }

  public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs)
          throws XmlPullParserException, IOException {
    drawable.inflate(r, parser, attrs);
  }

  public void invalidateSelf() {
    drawable.invalidateSelf();
  }

  public boolean isStateful() {
    return drawable.isStateful();
  }

  public void jumpToCurrentState() {
    drawable.jumpToCurrentState();
  }

  public Drawable mutate() {
    return drawable.mutate();
  }

  public void scheduleSelf(Runnable what, long when) {
    drawable.scheduleSelf(what, when);
  }

  public void setAlpha(int alpha) {
    drawable.setAlpha(alpha);
  }

  public void setBounds(int left, int top, int right, int bottom) {
    drawable.setBounds(left + leftOffset, top + topOffset, right, bottom);
  }

  public void setBounds(Rect bounds) {
    drawable.setBounds(bounds);
  }

  public void setChangingConfigurations(int configs) {
    drawable.setChangingConfigurations(configs);
  }

  public void setColorFilter(ColorFilter cf) {
    drawable.setColorFilter(cf);
  }

  public void setColorFilter(int color, Mode mode) {
    drawable.setColorFilter(color, mode);
  }

  public void setDither(boolean dither) {
    drawable.setDither(dither);
  }

  public void setFilterBitmap(boolean filter) {
    drawable.setFilterBitmap(filter);
  }

  public void setOffset(int left, int top) {
    leftOffset = left;
    topOffset  = top;
  }

  public boolean setState(int[] stateSet) {
    return drawable.setState(stateSet);
  }

  public boolean setVisible(boolean visible, boolean restart) {
    return drawable.setVisible(visible, restart);
  }

  public String toString() {
    return drawable.toString();
  }

  public void unscheduleSelf(Runnable what) {
    drawable.unscheduleSelf(what);
  }
}
