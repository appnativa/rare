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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.text.Html.ImageGetter;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iObservableImage;
import com.appnativa.rare.ui.iPaintedButton;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class ButtonViewEx extends Button implements iPainterSupport, iComponentView, iImageObserver {
  int                          autoRepeatDelay;
  iPlatformComponentPainter    componentPainter;
  AndroidGraphics              graphics;
  iPlatformIcon                icon;
  OnLongClickListener          longClickListener;
  volatile boolean             repeatCanceled;
  boolean                      transparent;
  private int                  iconGap      = 4;
  private IconPosition         iconPosition = IconPosition.LEADING;
  private Orientation          orientation  = Orientation.HORIZONTAL;
  private Paint                arrowPaint;
  private static iPlatformPath arrowPath;
  private int                  arrowWidth;
  private boolean              drawArrow;
  private iPlatformIcon        drawIcon;
  private boolean              hyperlink;
  private int                  iconPadBottom;
  private int                  iconPadLeft;
  private int                  iconPadRight;
  private int                  iconPadTop;
  private boolean              invalidateParent;
  private Handler              mHandler;
  private Runnable             mRunnable;
  private OnClickListener      onClickListener;
  private boolean              underlined;

  public ButtonViewEx(Context context, AttributeSet attrs) {
    super(context, attrs);
    setGravity(Gravity.CENTER);

    UIColor c = Platform.getUIDefaults().getColor("Rare.Button.foreground");
    if (c == null) {
      c=ColorUtils.getForeground();
    }
    c.setTextColor(this);

    FontUtils.getDefaultFont().setupTextView(this);
    setMaxLines(Short.MAX_VALUE);
    setSingleLine(false);
    setEllipsize(null);
  }

  public ButtonViewEx(Context context, boolean platform) {
    this(context, null);

    if (!platform) {
      makeTransparent();
    }
  }

  @Override
  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

  @Override
  public void imageLoaded(UIImage image) {
    if ((icon instanceof UIImageIcon) && ((UIImageIcon) icon).getImage() == image) {
      setIcon(icon);
    }
  }

  @Override
  public void invalidate() {
    if (invalidateParent) {
      ViewGroup vp = (ViewGroup) getParent();

      if (vp != null) {
        vp.invalidate();

        return;
      }
    }

    super.invalidate();
  }

  @Override
  public void invalidate(Rect dirty) {
    if (invalidateParent) {
      ViewGroup vp = (ViewGroup) getParent();

      if (vp != null) {
        vp.invalidate();

        return;
      }
    }

    super.invalidate(dirty);
  }

  @Override
  public void invalidate(int l, int t, int r, int b) {
    if (invalidateParent) {
      ViewGroup vp = (ViewGroup) getParent();

      if (vp != null) {
        vp.invalidate();

        return;
      }
    }

    super.invalidate(l, t, r, b);
  }

  public void makeTransparent() {
    setBackground(NullDrawable.getInstance());
    setMinimumHeight(0);
    setMinimumWidth(0);
    setMinHeight(0);
    setMinWidth(0);
  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) || (keyCode == KeyEvent.KEYCODE_ENTER)) {
      cancelAutoRepeat();
    }

    return super.onKeyUp(keyCode, event);
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    cancelLongpressIfRequired(event);

    return super.onTouchEvent(event);
  }

  @Override
  public boolean onTrackballEvent(MotionEvent event) {
    cancelLongpressIfRequired(event);

    return super.onTrackballEvent(event);
  }

  @Override
  public void onWindowFocusChanged(boolean hasWindowFocus) {
    super.onWindowFocusChanged(hasWindowFocus);

    if (!hasWindowFocus) {
      cancelAutoRepeat();
    }
  }

  @Override
  public String toString() {
    CharSequence s = getText();

    return (s == null)
           ? ""
           : s.toString();
  }

  public void setAutoRepeats(int delay) {
    if (delay < 1) {
      if (longClickListener != null) {
        setOnLongClickListener(null);
      }

      longClickListener = null;

      return;
    }

    autoRepeatDelay = delay;

    if (longClickListener != null) {
      return;
    }

    mHandler  = new Handler();
    mRunnable = new Runnable() {
      @Override
      public void run() {
        if (repeatCanceled ||!isPressed()) {
          invalidate();

          return;
        }

        if (onClickListener != null) {
          onClickListener.onClick(ButtonViewEx.this);
          mHandler.postDelayed(this, autoRepeatDelay);
        }
      }
    };
    longClickListener = new OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        repeatCanceled = false;
        mHandler.post(mRunnable);

        return true;
      }
    };
    setOnLongClickListener(longClickListener);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
    resolveStateValues();
  }

  public void setDrawArrow(boolean draw) {
    if (this.drawArrow != draw) {
      this.drawArrow = draw;

      if (draw) {
        if (arrowPath == null) {
          arrowPath = PainterUtils.drawArrow(arrowPath, ScreenUtils.PLATFORM_PIXELS_16, ScreenUtils.PLATFORM_PIXELS_16,
                                             true);
        }

        arrowWidth = ScreenUtils.PLATFORM_PIXELS_16;
      } else {
        arrowWidth = 0;
      }

      invalidate();
    }
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);

    if (!enabled) {
      repeatCanceled = true;
    }
  }

  public void setHyperlink(boolean hyperlink) {
    this.hyperlink = hyperlink;
  }

  public void setIcon(iPlatformIcon icon) {
    this.icon     = icon;
    this.drawIcon = icon;
    iconPadLeft   = iconPadRight = iconPadTop = iconPadBottom = 0;

    if (icon != null) {
      if ((icon instanceof iObservableImage) &&!((iObservableImage) icon).isImageLoaded(this)) {
        return;
      }

      resolveStateValues();

      switch(iconPosition) {
        case RIGHT :
        case RIGHT_JUSTIFIED :
        case TRAILING :
          iconPadRight = icon.getIconWidth() + iconGap;

          break;

        case TOP_CENTER :
          iconPadTop = icon.getIconHeight() + iconGap;

          break;

        case TOP_LEFT :
          iconPadTop  = icon.getIconHeight() + iconGap;
          iconPadLeft = icon.getIconWidth() + iconGap;

          break;

        case TOP_RIGHT :
          iconPadTop   = icon.getIconHeight() + iconGap;
          iconPadRight = icon.getIconWidth() + iconGap;

          break;

        case BOTTOM_CENTER :
          iconPadBottom = icon.getIconHeight() + iconGap;

          break;

        case BOTTOM_LEFT :
          iconPadBottom = icon.getIconHeight() + iconGap;
          iconPadLeft   = icon.getIconWidth() + iconGap;

          break;

        case BOTTOM_RIGHT :
          iconPadBottom = icon.getIconHeight() + iconGap;
          iconPadRight  = icon.getIconWidth() + iconGap;

          break;

        default :
          iconPadLeft = icon.getIconWidth() + iconGap;

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

  public void setInvalidateParent(boolean invalidateParent) {
    this.invalidateParent = invalidateParent;
  }

  @Override
  public void setOnClickListener(OnClickListener l) {
    super.setOnClickListener(l);
    onClickListener = l;
  }

  /**
   * Sets the orientation of the views text
   *
   * @param orientation
   *          the orientation to set
   */
  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }

  @Override
  public void setText(CharSequence text, BufferType type) {
    text = LabelView.checkText(text, (ImageGetter) Platform.findWidgetForComponent(Component.fromView(this)));

    if (underlined && (text != null)) {
      SpannableString content = new SpannableString(text);

      content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
      text = content;
    }

    super.setText(text, type);
    invalidate();
  }

  public iPaintedButton.ButtonState getButtonState() {
    return Utils.getState(isEnabled(), isPressed(), isSelected(), false);
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public int getCompoundPaddingBottom() {
    return super.getCompoundPaddingBottom() + iconPadBottom;
  }

  @Override
  public int getCompoundPaddingLeft() {
    return super.getCompoundPaddingLeft() + iconPadLeft;
  }

  @Override
  public int getCompoundPaddingRight() {
    return super.getCompoundPaddingRight() + iconPadRight + arrowWidth;
  }

  @Override
  public int getCompoundPaddingTop() {
    return super.getCompoundPaddingTop() + iconPadTop;
  }

  /**
   * @return the orientation
   */
  public Orientation getOrientation() {
    return orientation;
  }

  @Override
  public int getSuggestedMinimumHeight() {
    if (orientation == Orientation.HORIZONTAL) {
      return Math.max(super.getSuggestedMinimumHeight(), getIconHeight());
    } else {
      return Math.max(super.getSuggestedMinimumWidth(), getIconWidth());
    }
  }

  @Override
  public int getSuggestedMinimumWidth() {
    if (orientation == Orientation.HORIZONTAL) {
      return Math.max(super.getSuggestedMinimumWidth(), getIconWidth());
    } else {
      return Math.max(super.getSuggestedMinimumHeight(), getIconHeight());
    }
  }

  public final View getView() {
    return this;
  }

  public boolean isHyperlink() {
    return hyperlink;
  }

  public boolean isInvalidateParent() {
    return invalidateParent;
  }

  @Override
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    resolveStateValues();
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
  }

  @Override
  protected void onDetachedFromWindow() {
    repeatCanceled = true;
    super.onDetachedFromWindow();
    ViewHelper.onDetachedFromWindow(this);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

    final iPlatformComponentPainter cp = componentPainter;

    if (cp != null) {
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, false);
    }

    super.onDraw(canvas);

    if (drawIcon != null) {
      LabelView.drawIcon(canvas, this, drawIcon, iconPosition, iconGap, drawArrow
              ? -ScreenUtils.PLATFORM_PIXELS_4
              : 0);
    }

    if (drawArrow && (arrowPath != null)) {
      int x = getScrollX() + getWidth() - ScreenUtils.PLATFORM_PIXELS_16;

      if (arrowPaint == null) {
        arrowPaint = new Paint();
      }

      arrowPaint.setColor(getTextColors().getColorForState(getDrawableState(), ColorUtils.getForeground().getColor()));

      int h = getHeight();
      int y = (h / 2) - ScreenUtils.PLATFORM_PIXELS_4;

      canvas.translate(x, y);
      canvas.drawPath(arrowPath.getPath(), arrowPaint);
      canvas.translate(-x, -y);
    }

    if (cp != null) {
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, true);
    }

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

    graphics.clear();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    if (orientation != Orientation.HORIZONTAL) {
      int w = getMeasuredWidth();
      int h = getMeasuredHeight();

      setMeasuredDimension(h, w);
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }

  protected void resolveStateValues() {
    if (componentPainter != null) {
      PainterHolder ph = componentPainter.getPainterHolder();
      if (ph != null) {
        invalidate();
        ButtonState bs = getButtonState();

        drawIcon = ph.getIcon(bs);
      }
    }

    if (drawIcon == null) {
      drawIcon = icon;
    }
  }

  private void cancelAutoRepeat() {
    repeatCanceled = true;
  }

  private void cancelLongpressIfRequired(MotionEvent event) {
    if ((event.getAction() == MotionEvent.ACTION_CANCEL) || (event.getAction() == MotionEvent.ACTION_UP)) {
      cancelAutoRepeat();
    }
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

  public void setUnderlined(boolean underlined) {
    this.underlined = underlined;
  }
}
