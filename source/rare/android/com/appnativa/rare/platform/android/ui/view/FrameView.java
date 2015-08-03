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
import android.view.ViewGroup;

import android.widget.FrameLayout;

import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.rare.ui.painter.aUIPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class FrameView extends FrameLayout implements iPainterSupport, iComponentView {
  int                                 height;
  int                                 width;
  protected RenderType                renderType = RenderType.STRETCHED;
  protected iPlatformComponentPainter componentPainter;
  protected AndroidGraphics           graphics;
  private UIDimension                 measureSize = new UIDimension();
  private RenderSpace                 renderSpace = RenderSpace.WITHIN_MARGIN;

  public FrameView(Context context) {
    this(context, null);
  }

  public FrameView(Context context, AttributeSet attrs) {
    super(context, attrs);

    if (attrs == null) {
      this.setBackgroundDrawable(NullDrawable.getInstance());
    }
  }

  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
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

  public static void layoutChild(RenderType renderType, ViewGroup parent, View v, int left, int top, int right,
                                 int bottom) {
    left   += parent.getPaddingLeft();
    right  -= parent.getPaddingRight();
    bottom -= parent.getPaddingBottom();
    top    += parent.getPaddingTop();

    final int width   = right - left;
    final int height  = bottom - top;
    int       vwidth  = v.getMeasuredWidth();
    int       vheight = v.getMeasuredHeight();

    vheight = (vheight > height)
              ? height
              : vheight;
    vwidth  = (vwidth > width)
              ? width
              : vwidth;
    left    = parent.getPaddingLeft();
    top     = parent.getPaddingTop();

    int x = left;
    int y = top;

    switch(renderType) {
      case UPPER_LEFT :
        break;

      case UPPER_MIDDLE :
        x = Math.max(left, (width - vwidth) / 2);

        break;

      case UPPER_RIGHT :
        x = Math.max(left, right - vwidth);

        break;

      case LOWER_LEFT :
        y = Math.max(top, bottom - vheight);

        break;

      case LOWER_MIDDLE :
        x = Math.max(left, (width - vwidth) / 2);
        y = Math.max(top, bottom - vheight);

        break;

      case LOWER_RIGHT :
        x = Math.max(left, right - vwidth);
        y = Math.max(top, bottom - vheight);

        break;

      case LEFT_MIDDLE :
        y = Math.max(top, (height - vheight) / 2);

        break;

      case RIGHT_MIDDLE :
        x = Math.max(left, right - vwidth);
        y = Math.max(top, (height - vheight) / 2);

        break;

      case CENTERED :
        x = Math.max(left, (width - vwidth) / 2);
        y = Math.max(top, (height - vheight) / 2);

        break;

      case STRETCHED :
        vwidth  = width;
        vheight = height;

        break;

      case STRETCH_WIDTH :
        vwidth = width;

        break;

      case STRETCH_HEIGHT :
        vheight = height;

        break;

      case STRETCH_WIDTH_MIDDLE :
        y      = Math.max(top, (height - vheight) / 2);
        vwidth = width;

        break;

      case STRETCH_HEIGHT_MIDDLE :
        x       = Math.max(left, (width - vwidth) / 2);
        vheight = height;

        break;

      default :
        break;
    }

    final ViewGroup.LayoutParams lp = v.getLayoutParams();

    if (lp != null) {
      lp.width  = vwidth;
      lp.height = vheight;
    }

    Component comp = Component.fromView(v);

    if (comp != null) {
      comp.setBounds(x, y, vwidth, vheight);
    } else {
      v.measure(MeasureSpec.makeMeasureSpec(vwidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(vheight, MeasureSpec.EXACTLY));
      v.layout(x, y, x + vwidth, y + vheight);
    }
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public void setRenderSpace(RenderSpace renderSpace) {
    this.renderSpace = renderSpace;
  }

  public void setSize(int width, int height) {
    setMeasuredDimension(width, height);
  }

  /**
   * Sets the view render type
   *
   * @param rt the render type
   */
  public void setViewRenderType(RenderType rt) {
    renderType = rt;
    requestLayout();
    invalidate();
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public RenderSpace getRenderSpace() {
    return renderSpace;
  }

  public int getSuggestedMinimumHeight() {
    if (height > 0) {
      return height;
    }

    return Math.max(super.getSuggestedMinimumHeight(), getSuggestedMinimum(true));
  }

  public int getSuggestedMinimumWidth() {
    if (width > 0) {
      return width;
    }

    return Math.max(super.getSuggestedMinimumWidth(), getSuggestedMinimum(false));
  }

  /**
   * Gets the view render type
   *
   * @param rt the render type
   */
  public RenderType getViewRenderType() {
    return renderType;
  }

  protected void callSuperDraw(Canvas canvas) {
    super.draw(canvas);
  }

  protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
    Component c = Component.fromView(child);

    if (c == null) {
      super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
    } else {
      c.getPreferredSize(measureSize);
      measureSize.width  += child.getPaddingLeft() + child.getPaddingRight();
      measureSize.height += child.getPaddingTop() + child.getPaddingBottom();

      int size = MeasureSpec.getSize(parentWidthMeasureSpec);

      if ((size > 0) && (size < measureSize.width)) {
        measureSize.width = size;
      }

      size = MeasureSpec.getSize(parentHeightMeasureSpec);

      if ((size > 0) && (size < measureSize.height)) {
        measureSize.height = size;
      }

      child.measure(MeasureSpec.makeMeasureSpec(measureSize.intWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measureSize.intHeight(), MeasureSpec.EXACTLY));
    }
  }

  protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
          int parentHeightMeasureSpec, int heightUsed) {
    Component c = Component.fromView(child);

    if (c == null) {
      super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
    } else {
      c.getPreferredSize(measureSize);

      int size = MeasureSpec.getSize(parentWidthMeasureSpec);

      if ((size > 0) && (size < measureSize.width)) {
        measureSize.width = size;
      }

      size = MeasureSpec.getSize(parentHeightMeasureSpec);

      if ((size > 0) && (size < measureSize.height)) {
        measureSize.height = size;
      }

      child.measure(MeasureSpec.makeMeasureSpec(measureSize.intWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measureSize.intHeight(), MeasureSpec.EXACTLY));
    }
  }

  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
  }

  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ViewHelper.onDetachedFromWindow(this);
  }

  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    if (renderType == RenderType.XY) {
      return;
    }

    Container container = (Container) Component.fromView(this);

    if (container != null) {
      int len = container.getComponentCount();

      if ((len > 0) &&!container.isAnimating()) {
        iPlatformComponent c      = container.getComponentAt(len - 1);
        float              iw     = 0;
        float              ih     = 0;
        final float        width  = right - left;
        final float        height = bottom - top;

        if (renderType != RenderType.STRETCHED) {
          UIDimension d = c.getPreferredSize();

          iw = d.width;
          ih = d.height;
        }

        UIRectangle rect = aUIPainter.getRenderLocation(container, renderSpace, renderType, 0, 0, width, height, iw,
                             ih, null);

        for (int i = 0; i < len; i++) {
          c = container.getComponentAt(i);

          if (c.isVisible()) {
            c.setBounds(rect.x, rect.y, rect.width, rect.height);
          }
        }
      }
    } else if ((getChildCount() > 0) &&!ViewGroupEx.isAnimating(this)) {
      layoutChild((renderType == null)
                  ? RenderType.STRETCHED
                  : renderType, this, getChildAt(0), left, top, right, bottom);
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if ((MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY)
        && (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY)) {
      setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    } else if (!ViewGroupEx.isAnimating(this)) {
      setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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

  protected int getSuggestedMinimum(boolean forHeight) {
    final int len    = getChildCount();
    int       width  = 0;
    int       height = 0;

    for (int i = 0; i < len; i++) {
      View v = getChildAt(i);

      if (v instanceof iComponentView) {
        height = Math.max(((iComponentView) v).getSuggestedMinimumHeight(), height);
        width  = Math.max(((iComponentView) v).getSuggestedMinimumWidth(), width);
      }
    }

    height += getPaddingBottom() + getPaddingTop();
    width  += getPaddingLeft() + getPaddingRight();

    return forHeight
           ? height
           : width;
  }
}
