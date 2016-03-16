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

public class DrawableWrapper extends Drawable {
  protected Drawable drawable;
  int                width  = -1;
  int                height = -1;

  public DrawableWrapper(Drawable drawable) {
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
  public int getAlpha() {
    return drawable.getAlpha();
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
    return (height == -1)
           ? drawable.getIntrinsicHeight()
           : height;
  }

  @Override
  public int getIntrinsicWidth() {
    return (width == -1)
           ? drawable.getIntrinsicWidth()
           : width;
  }

  public void setIntrinsicWidthEx(int width) {
    this.width = width;
  }

  public void setIntrinsicHeightEx(int height) {
    this.height = height;
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
  public boolean isAutoMirrored() {
    return drawable.isAutoMirrored();
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
  public void setAutoMirrored(boolean mirrored) {
    drawable.setAutoMirrored(mirrored);
  }

  @Override
  public void setBounds(int left, int top, int right, int bottom) {
    drawable.setBounds(left, top, right, bottom);
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
