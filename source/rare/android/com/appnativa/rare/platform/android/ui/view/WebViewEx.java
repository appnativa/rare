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

import android.util.AttributeSet;

import android.view.View;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class WebViewEx extends WebView implements iPainterSupport, iComponentView {
  iPlatformComponentPainter componentPainter;
  Boolean                   saveUseZoomControls;
  protected AndroidGraphics graphics;
  boolean                   disposed;

  public WebViewEx(Context context) {
    super(context);
  }

  public WebViewEx(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public WebViewEx(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public WebViewEx(Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
    super(context, attrs, defStyle, privateBrowsing);
  }

  public void draw(Canvas canvas) {
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

  public void dispose() {
    disposed = true;
    removeAllViews();

    if (getWindowToken() == null) {
      try {
        destroy();
      } catch(Throwable ignore) {}
    }
  }

  public void onConfigurationChanged(boolean reset) {
    super.onConfigurationChanged(getContext().getResources().getConfiguration());
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public void setUseZoomControls(boolean use) {
    saveUseZoomControls = use;
    getSettings().setBuiltInZoomControls(use);
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

  protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    if (saveUseZoomControls != null) {
      getSettings().setBuiltInZoomControls(saveUseZoomControls);
    }

    ViewHelper.onAttachedToWindow(this);
  }

  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();

    if (disposed) {
      try {
        destroy();
      } catch(Throwable ignore) {}
      ;
    } else {
      try {
        WebSettings s = getSettings();

        if (s != null) {
          s.setBuiltInZoomControls(false);
        }
      } catch(Throwable ignore) {}

      ViewHelper.onDetachedFromWindow(this);
    }
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }
}
