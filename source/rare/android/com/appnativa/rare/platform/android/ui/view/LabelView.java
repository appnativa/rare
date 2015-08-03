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

import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.URLSpan;

import android.util.AttributeSet;

import android.view.Gravity;
import android.view.View;

import android.widget.CheckedTextView;
import android.widget.TextView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.IconDrawable;
import com.appnativa.rare.platform.android.ui.MultiStateIcon;
import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class LabelView extends CheckedTextView implements iPainterSupport, iComponentView, iImageObserver {
  iPlatformComponentPainter componentPainter;
  protected AndroidGraphics graphics;
  private int               iconGap      = 4;
  private Orientation       orientation  = Orientation.HORIZONTAL;
  private IconPosition      iconPosition = IconPosition.LEADING;
  private iPlatformIcon     icon;
  private int               iconPadBottom;
  private int               iconPadLeft;
  private int               iconPadRight;
  private int               iconPadTop;
  private boolean           blockRequestLayout;

  public LabelView(Context context) {
    this(context, null);
  }

  public LabelView(Context context, AttributeSet attrs) {
    super(context, attrs);
    ColorUtils.getForeground().setTextColor(this);
    setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
    FontUtils.getDefaultFont().setupTextView(this);
    setMaxLines(Short.MAX_VALUE);
    setSingleLine(false);
    setEllipsize(null);
  }

  public static CharSequence checkText(CharSequence text, ImageGetter imageGetter) {
    if (text instanceof String) {
      final String s = (String) text;

      if (s.startsWith("<html>") || s.endsWith("</html")) {
        if (imageGetter == null) {
          imageGetter = Platform.getAppContext().getWindowViewer();
        }

        text = Html.fromHtml(s);
      }
    }

    return text;
  }

  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

  public static int drawIcon(Canvas g, final TextView view, final iPlatformIcon icon, final IconPosition ip,
                             final int iconGap, int centeredOffset) {
    if ((icon == null) || (icon.getIconHeight() < 1)) {
      return 0;
    }

    int          x;
    int          y;
    final Layout l       = view.getLayout();
    final int    gravity = view.getGravity();

    if (l == null) {
      return 0;
    }

    int height = view.getHeight();
    int width  = view.getWidth();
    int lpad   = view.getPaddingLeft();
    int tpad   = view.getPaddingTop();
    int rpad   = view.getPaddingRight();
    int bpad   = view.getPaddingBottom();

    switch(ip) {
      case RIGHT :
      case TRAILING :
        x = (int) (view.getCompoundPaddingLeft() + l.getLineRight(0) + iconGap);

        if (getTextWidth(l) == 0) {
          y = (height - icon.getIconHeight() - tpad - bpad) / 2 + tpad;
          x = (width - icon.getIconWidth() - rpad - lpad) / 2 + lpad + centeredOffset;
        } else if (isTopAligned(gravity)) {
          y = view.getCompoundPaddingTop();
        } else if (isBottomAligned(gravity)) {
          y = height - view.getCompoundPaddingBottom() - icon.getIconHeight();
        } else {
          y = (height - icon.getIconHeight() - tpad - bpad) / 2 + tpad;
        }

        break;

      case RIGHT_JUSTIFIED :
        x = view.getWidth() - view.getPaddingRight() - icon.getIconWidth() - iconGap;

        if (getTextWidth(l) == 0) {
          y = (height - icon.getIconHeight() - tpad - bpad) / 2 + tpad + centeredOffset;
        } else if (isTopAligned(gravity)) {
          y = view.getCompoundPaddingTop();
        } else if (isBottomAligned(gravity)) {
          y = height - view.getCompoundPaddingBottom() - icon.getIconHeight();
        } else {
          y = (height - icon.getIconHeight() - tpad - bpad) / 2 + tpad;
        }

        break;

      case TOP_CENTER :
        if (getTextWidth(l) == 0) {
          y = (height - icon.getIconHeight() - tpad - bpad) / 2 + tpad;
        } else {
          if (isTopAligned(gravity)) {
            y = tpad;
          } else if (isVerticalCenter(gravity)) {
            y = ((height - l.getHeight() - icon.getIconHeight() - iconGap) / 2);
          } else {
            y = height - view.getCompoundPaddingBottom() - l.getHeight() - iconGap - icon.getIconHeight();
          }
        }

        x = (view.getWidth() - icon.getIconWidth()) / 2;

        break;

      case BOTTOM_CENTER :
        if (getTextWidth(l) == 0) {
          y = (height - icon.getIconHeight() - tpad - bpad) / 2 + tpad;
        } else {
          if (isTopAligned(gravity)) {
            y = view.getPaddingTop() + l.getHeight() + iconGap - icon.getIconHeight();
          } else if (isVerticalCenter(gravity)) {
            y = ((height - l.getHeight() - icon.getIconHeight() - iconGap) / 2) + l.getHeight() + iconGap;
          } else {
            y = height - view.getPaddingBottom() - icon.getIconHeight();
          }
        }

        x = (width - icon.getIconWidth() - rpad - lpad) / 2 + lpad;

        break;

      case TOP_LEFT :
        x = (int) (view.getCompoundPaddingLeft() + l.getLineLeft(0) - icon.getIconWidth() - iconGap);
        y = view.getCompoundPaddingTop() + l.getLineTop(0);

        break;

      case TOP_RIGHT :
        x = width - view.getPaddingRight() - icon.getIconWidth() - iconGap;
        y = view.getCompoundPaddingTop();

        break;

      default :
        x = (int) (view.getCompoundPaddingLeft() + l.getLineLeft(0) - icon.getIconWidth() - iconGap);

        if (getTextWidth(l) == 0) {
          x = (width - icon.getIconWidth() - rpad - lpad) / 2 + lpad + centeredOffset;
          y = (height - icon.getIconHeight() - tpad - bpad) / 2 + tpad;
        } else if (isTopAligned(gravity)) {
          y = view.getCompoundPaddingTop();
        } else if (isBottomAligned(gravity)) {
          y = height - view.getCompoundPaddingBottom() - icon.getIconHeight();
        } else {
          y = (height - icon.getIconHeight() - tpad - bpad) / 2 + tpad;
        }

        break;
    }

    if (x < 0) {
      x = 0;
    }

    if (y < 0) {
      y = 0;
    }

    if (icon instanceof MultiStateIcon) {
      ((MultiStateIcon) icon).updateState(view);
    } else if (icon instanceof IconDrawable) {
      ((IconDrawable) icon).setState(view.getDrawableState());
    }

    icon.paint(g, x + view.getScrollX(), y + view.getScrollY(), width - x, height - y);

    return x;
  }

  @Override
  public void imageLoaded(UIImage image) {
    if ((icon instanceof UIImageIcon) && ((UIImageIcon) icon).getImage() == image) {
      setIcon(icon);
    }
  }

  public static Spannable stripUnderlines(Spannable s) {
    URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);

    for (URLSpan span : spans) {
      int start = s.getSpanStart(span);
      int end   = s.getSpanEnd(span);

      s.removeSpan(span);
      span = new URLSpanNoUnderline(span.getURL());
      s.setSpan(span, start, end, 0);
    }

    return s;
  }

  public static void stripUnderlines(TextView tv) {
    CharSequence cs = tv.getText();

    if (cs instanceof Spannable) {
      tv.setText(stripUnderlines((Spannable) cs));
    }
  }

  public String toString() {
    CharSequence s = getText();

    return (s == null)
           ? ""
           : s.toString();
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public static boolean setDrawableLocation(final TextView view, final Drawable icon, final IconPosition ip,
          final int iconGap) {
    if (icon == null) {
      return true;
    }

    int          x;
    int          y;
    final Layout l       = view.getLayout();
    final int    gravity = view.getGravity();

    if (l == null) {
      return false;
    }

    switch(ip) {
      case RIGHT :
      case TRAILING :
        x = (int) (view.getCompoundPaddingLeft() + l.getLineRight(0) + iconGap);

        if (getTextWidth(l) == 0) {
          y = (view.getHeight() - icon.getIntrinsicHeight()) / 2;
        } else if (isTopAligned(gravity)) {
          y = view.getCompoundPaddingTop();
        } else if (isBottomAligned(gravity)) {
          y = view.getHeight() - view.getCompoundPaddingBottom() - icon.getIntrinsicHeight();
        } else {
          y = (view.getHeight() - icon.getIntrinsicHeight()) / 2;
        }

        break;

      case RIGHT_JUSTIFIED :
        x = view.getWidth() - view.getPaddingRight() - icon.getIntrinsicWidth() - iconGap;

        if (getTextWidth(l) == 0) {
          y = (view.getHeight() - icon.getIntrinsicHeight()) / 2;
        } else if (isTopAligned(gravity)) {
          y = view.getCompoundPaddingTop();
        } else if (isBottomAligned(gravity)) {
          y = view.getHeight() - view.getCompoundPaddingBottom() - icon.getIntrinsicHeight();
        } else {
          y = (view.getHeight() - icon.getIntrinsicHeight()) / 2;
        }

        break;

      case TOP_CENTER :
        if (getTextWidth(l) == 0) {
          y = (view.getHeight() - icon.getIntrinsicHeight()) / 2;
        } else {
          if (isTopAligned(gravity)) {
            y = view.getPaddingTop();
          } else if (isVerticalCenter(gravity)) {
            y = ((view.getHeight() - l.getHeight() - icon.getIntrinsicHeight() - iconGap) / 2);    // - icon.getIntrinsicHeight()- iconGap;
          } else {
            y = view.getHeight() - view.getCompoundPaddingBottom() - l.getHeight() - iconGap
                - icon.getIntrinsicHeight();
          }
        }

        x = (view.getWidth() - icon.getIntrinsicWidth()) / 2;

        break;

      case BOTTOM_CENTER :
        if (getTextWidth(l) == 0) {
          y = (view.getHeight() - icon.getIntrinsicHeight()) / 2;
        } else {
          if (isTopAligned(gravity)) {
            y = view.getPaddingTop() + l.getHeight() + iconGap - icon.getIntrinsicHeight();
          } else if (isVerticalCenter(gravity)) {
            y = ((view.getHeight() - l.getHeight() - icon.getIntrinsicHeight() - iconGap) / 2) + l.getHeight()
                + iconGap;
          } else {
            y = view.getHeight() - view.getPaddingBottom() - icon.getIntrinsicHeight();
          }
        }

        x = (view.getWidth() - icon.getIntrinsicWidth()) / 2;

        break;

      default :
        x = (int) (view.getCompoundPaddingLeft() + l.getLineLeft(0) - icon.getIntrinsicWidth() - iconGap);

        if (getTextWidth(l) == 0) {
          y = (view.getHeight() - icon.getIntrinsicHeight()) / 2;
        } else if (isTopAligned(gravity)) {
          y = view.getCompoundPaddingTop();
        } else if (isBottomAligned(gravity)) {
          y = view.getHeight() - view.getCompoundPaddingBottom() - icon.getIntrinsicHeight();
        } else {
          y = (view.getHeight() - icon.getIntrinsicHeight()) / 2;
        }

        break;
    }

    if (x < 0) {
      x = 0;
    }

    if (y < 0) {
      y = 0;
    }

    icon.setBounds(x, y, x + icon.getIntrinsicWidth(), y + icon.getIntrinsicHeight());

    return true;
  }

  @Override
  public void requestLayout() {
    if (!blockRequestLayout) {
      super.requestLayout();
    }
  }

  public void setIcon(iPlatformIcon icon) {
    this.icon   = icon;
    iconPadLeft = iconPadRight = iconPadTop = iconPadBottom = 0;

    if (icon != null) {
      if ((icon instanceof UIImageIcon) &&!((UIImageIcon) icon).isImageLoaded(this)) {
        return;
      }

      switch(iconPosition) {
        case RIGHT :
        case RIGHT_JUSTIFIED :
        case TRAILING :
          iconPadRight = icon.getIconWidth();

          break;

        case TOP_CENTER :
          iconPadTop = icon.getIconHeight();

          break;

        case TOP_LEFT :
          iconPadLeft = icon.getIconWidth();

          break;

        case TOP_RIGHT :
          iconPadRight = icon.getIconWidth();

          break;

        case BOTTOM_CENTER :
          iconPadBottom = icon.getIconHeight();

          break;

        case BOTTOM_LEFT :
          iconPadLeft = icon.getIconWidth();

          break;

        case BOTTOM_RIGHT :
          iconPadRight = icon.getIconWidth();

          break;

        default :
          iconPadLeft = icon.getIconWidth();

          break;
      }
    }

    invalidate();
  }

  /**
   * @param iconGap
   *          the iconGap to set
   */
  public void setIconGap(int iconGap) {
    this.iconGap = iconGap;
  }

  /**
   * @param iconPosition
   *          the iconPosition to set
   */
  public void setIconPosition(IconPosition iconPosition) {
    if (this.iconPosition != iconPosition) {
      this.iconPosition = iconPosition;
      setIcon(this.icon);
    }
  }

  /**
   * Sets the orientation of the views text
   * @param orientation
   *          the orientation to set
   */
  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }

  public void setSize(int width, int height) {
    final int x = getLeft();
    final int y = getTop();

    setFrame(x, y, x + width, y + height);
  }

  public void setText(CharSequence text, BufferType type) {
    text = checkText(text, (ImageGetter) Platform.findWidgetForComponent(Component.fromView(this)));
    super.setText(text, type);
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public int getCompoundPaddingBottom() {
    return super.getCompoundPaddingBottom() + iconPadBottom + (((iconPadBottom == 0) || (length() == 0))
            ? 0
            : iconGap);
  }

  @Override
  public int getCompoundPaddingLeft() {
    return super.getCompoundPaddingLeft() + iconPadLeft + (((iconPadLeft == 0) || (length() == 0))
            ? 0
            : iconGap);
  }

  @Override
  public int getCompoundPaddingRight() {
    return super.getCompoundPaddingRight() + iconPadRight + (((iconPadRight == 0) || (length() == 0))
            ? 0
            : iconGap);
  }

  @Override
  public int getCompoundPaddingTop() {
    return super.getCompoundPaddingTop() + iconPadTop + (((iconPadTop == 0) || (length() == 0))
            ? 0
            : iconGap);
  }

  /**
   * @return the icon
   */
  public iPlatformIcon getIcon() {
    return icon;
  }

  /**
   * @return the iconGap
   */
  public int getIconGap() {
    return iconGap;
  }

  /**
   * @return the iconPosition
   */
  public IconPosition getIconPosition() {
    return iconPosition;
  }

  /**
   * @return the orientation
   */
  public Orientation getOrientation() {
    return orientation;
  }

  public int getSuggestedMinimumHeight() {
    if (orientation == Orientation.HORIZONTAL) {
      return Math.max(((length() == 0)
                       ? 0
                       : super.getSuggestedMinimumHeight()), getIconHeight());
    } else {
      return Math.max(((length() == 0)
                       ? 0
                       : super.getSuggestedMinimumWidth()), getIconWidth());
    }
  }

  public int getSuggestedMinimumWidth() {
    if (orientation == Orientation.HORIZONTAL) {
      return Math.max(((length() == 0)
                       ? 0
                       : super.getSuggestedMinimumWidth()), getIconWidth());
    } else {
      return Math.max(((length() == 0)
                       ? 0
                       : super.getSuggestedMinimumHeight()), getIconHeight());
    }
  }

  public static int getTextWidth(Layout l) {
    int       w = 0;
    float     n;
    final int len = l.getLineCount();

    for (int i = 0; i < len; i++) {
      n = l.getLineMax(i);

      if (n > w) {
        w = (int) n;
      }
    }

    return w;
  }

  public static boolean isBottomAligned(int gravity) {
    return ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM);
  }

  public static boolean isHorizontalCenter(int gravity) {
    return ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL);
  }

  public static boolean isHorizontalFill(int gravity) {
    return ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL);
  }

  public static boolean isLeftAligned(int gravity) {
    return ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.LEFT);
  }

  public static boolean isRightAligned(int gravity) {
    return ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.RIGHT);
  }

  public static boolean isTopAligned(int gravity) {
    return ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP);
  }

  public static boolean isVerticalCenter(int gravity) {
    return ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER_VERTICAL);
  }

  public static boolean isVerticalFill(int gravity) {
    return ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL);
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
    graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

    final iPlatformComponentPainter cp = componentPainter;

    if (cp != null) {
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, false);
    }

    super.onDraw(canvas);

    if (icon != null) {
      drawIcon(canvas, this, icon, iconPosition, iconGap, 0);
    }

    if (cp != null) {
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, true);
    }

    graphics.clear();

    switch(orientation) {
      case VERTICAL_DOWN :
        canvas.translate(0, getWidth());
        canvas.rotate(90);

        break;

      case VERTICAL_UP :
        canvas.rotate(-90);
        canvas.translate(-getHeight(), 0);

        break;

      default :
        break;
    }
  }

  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (length() == 0) {
      int xpad = getPaddingLeft() + getPaddingRight();
      int ypad = getPaddingTop() + getPaddingBottom();

      setMeasuredDimension(resolveSize(getIconWidth() + xpad, widthMeasureSpec),
                           resolveSize(getIconHeight() + ypad, heightMeasureSpec));
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    if (orientation != Orientation.HORIZONTAL) {
      setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
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

  private int getIconHeight() {
    return (icon == null)
           ? 0
           : icon.getIconHeight();
  }

  private int getIconWidth() {
    return (icon == null)
           ? 0
           : icon.getIconWidth();
  }

  public boolean isBlockLayoutRequest() {
    return blockRequestLayout;
  }

  public void setBlockRequestLayout(boolean blockRequestLayout) {
    this.blockRequestLayout = blockRequestLayout;
  }

  private static class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String url) {
      super(url);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
      super.updateDrawState(ds);
      ds.setUnderlineText(false);
    }
  }
}
