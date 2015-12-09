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

  @Override
  public void clearColorFilter() {
    drawable.clearColorFilter();
  }

  @Override
  public void draw(Canvas canvas) {
    drawable.draw(canvas);
  }

  @Override
  public boolean equals(Object o) {
    return drawable.equals(o);
  }

  @Override
  public Callback getCallback() {
    return drawable.getCallback();
  }

  @Override
  public int getChangingConfigurations() {
    return drawable.getChangingConfigurations();
  }

  @Override
  public ConstantState getConstantState() {
    return drawable.getConstantState();
  }

  @Override
  public Drawable getCurrent() {
    return drawable.getCurrent();
  }

  @Override
  public int getIntrinsicHeight() {
    return drawable.getIntrinsicHeight();
  }

  @Override
  public int getIntrinsicWidth() {
    return drawable.getIntrinsicWidth();
  }

  @Override
  public int getMinimumHeight() {
    return drawable.getMinimumHeight();
  }

  @Override
  public int getMinimumWidth() {
    return drawable.getMinimumWidth();
  }

  @Override
  public int getOpacity() {
    return drawable.getOpacity();
  }

  @Override
  public boolean getPadding(Rect padding) {
    return drawable.getPadding(padding);
  }

  @Override
  public int[] getState() {
    return drawable.getState();
  }

  @Override
  public Region getTransparentRegion() {
    return drawable.getTransparentRegion();
  }

  @Override
  public int hashCode() {
    return drawable.hashCode();
  }

  @Override
  public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs)
          throws XmlPullParserException, IOException {
    drawable.inflate(r, parser, attrs);
  }

  @Override
  public void invalidateSelf() {
    drawable.invalidateSelf();
  }

  @Override
  public boolean isStateful() {
    return drawable.isStateful();
  }

  @Override
  public void jumpToCurrentState() {
    drawable.jumpToCurrentState();
  }

  @Override
  public Drawable mutate() {
    return drawable.mutate();
  }

  @Override
  public void scheduleSelf(Runnable what, long when) {
    drawable.scheduleSelf(what, when);
  }

  @Override
  public void setAlpha(int alpha) {
    drawable.setAlpha(alpha);
  }

  @Override
  public void setBounds(int left, int top, int right, int bottom) {
    drawable.setBounds(left + leftOffset, top + topOffset, right, bottom);
  }

  @Override
  public void setBounds(Rect bounds) {
    drawable.setBounds(bounds);
  }

  @Override
  public void setChangingConfigurations(int configs) {
    drawable.setChangingConfigurations(configs);
  }

  @Override
  public void setColorFilter(ColorFilter cf) {
    drawable.setColorFilter(cf);
  }

  @Override
  public void setColorFilter(int color, Mode mode) {
    drawable.setColorFilter(color, mode);
  }

  @Override
  public void setDither(boolean dither) {
    drawable.setDither(dither);
  }

  @Override
  public void setFilterBitmap(boolean filter) {
    drawable.setFilterBitmap(filter);
  }

  public void setOffset(int left, int top) {
    leftOffset = left;
    topOffset  = top;
  }

  @Override
  public boolean setState(int[] stateSet) {
    return drawable.setState(stateSet);
  }

  @Override
  public boolean setVisible(boolean visible, boolean restart) {
    return drawable.setVisible(visible, restart);
  }

  @Override
  public String toString() {
    return drawable.toString();
  }

  @Override
  public void unscheduleSelf(Runnable what) {
    drawable.unscheduleSelf(what);
  }
}
