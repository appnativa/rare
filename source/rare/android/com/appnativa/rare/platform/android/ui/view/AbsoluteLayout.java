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

import android.util.AttributeSet;

import android.view.View;
import android.view.ViewGroup;

/**
 * A layout that lets you specify exact locations (x/y coordinates) of its
 * children. Absolute layouts are less flexible and harder to maintain than
 * other types of layouts without absolute positioning.
 *
 * <p><strong>XML attributes</strong></p> <p> See {@link
 * android.R.styleable#ViewGroup ViewGroup Attributes}, {@link
 * android.R.styleable#View View Attributes}</p>
 *
 */
public class AbsoluteLayout extends ViewGroup {
  public AbsoluteLayout(Context context) {
    super(context);
  }

  public AbsoluteLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  // Override to allow type-checking of LayoutParams.
  @Override
  protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
    return p instanceof AbsoluteLayout.LayoutParams;
  }

  /**
   * Returns a set of layout parameters with a width of
   * {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT},
   * a height of {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT}
   * and with the coordinates (0, 0).
   */
  @Override
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0);
  }

  @Override
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
    return new LayoutParams(p);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int count        = getChildCount();
    int mPaddingLeft = getPaddingLeft();
    int mPaddingTop  = getPaddingTop();

    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);

      if (child.getVisibility() != GONE) {
        AbsoluteLayout.LayoutParams lp        = (AbsoluteLayout.LayoutParams) child.getLayoutParams();
        int                         childLeft = mPaddingLeft + lp.x;
        int                         childTop  = mPaddingTop + lp.y;

        child.layout(childLeft, childTop, childLeft + child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
      }
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int count          = getChildCount();
    int maxHeight      = 0;
    int maxWidth       = 0;
    int mPaddingLeft   = getPaddingLeft();
    int mPaddingRight  = getPaddingRight();
    int mPaddingTop    = getPaddingTop();
    int mPaddingBottom = getPaddingBottom();

    // Find out how big everyone wants to be
    measureChildren(widthMeasureSpec, heightMeasureSpec);

    // Find rightmost and bottom-most child
    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);

      if (child.getVisibility() != GONE) {
        int                         childRight;
        int                         childBottom;
        AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams) child.getLayoutParams();

        childRight  = lp.x + child.getMeasuredWidth();
        childBottom = lp.y + child.getMeasuredHeight();
        maxWidth    = Math.max(maxWidth, childRight);
        maxHeight   = Math.max(maxHeight, childBottom);
      }
    }

    // Account for padding too
    maxWidth  += mPaddingLeft + mPaddingRight;
    maxHeight += mPaddingTop + mPaddingBottom;
    // Check against minimum height and width
    maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
    maxWidth  = Math.max(maxWidth, getSuggestedMinimumWidth());
    setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec), resolveSize(maxHeight, heightMeasureSpec));
  }

  /**
   * Per-child layout information associated with AbsoluteLayout.
   * See
   * {@link android.R.styleable#AbsoluteLayout_Layout Absolute Layout Attributes}
   * for a list of all child view attributes that this class supports.
   */
  public static class LayoutParams extends ViewGroup.LayoutParams {

    /**
     * The horizontal, or X, location of the child within the view group.
     */
    public int x;

    /**
     * The vertical, or Y, location of the child within the view group.
     */
    public int y;

    /**
     * {@inheritDoc}
     */
    public LayoutParams(ViewGroup.LayoutParams source) {
      super(source);
    }

    /**
     * Creates a new set of layout parameters with the specified width,
     * height and location.
     *
     * @param width the width, either {@link #MATCH_PARENT},
     *         {@link #WRAP_CONTENT} or a fixed size in pixels
     * @param height the height, either {@link #MATCH_PARENT},
     *         {@link #WRAP_CONTENT} or a fixed size in pixels
     * @param x the X location of the child
     * @param y the Y location of the child
     */
    public LayoutParams(int width, int height, int x, int y) {
      super(width, height);
      this.x = x;
      this.y = y;
    }
  }
}
