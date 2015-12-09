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

import android.annotation.SuppressLint;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Rect;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.view.ButtonViewEx;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.widget.aPlatformWidget;
import com.appnativa.rare.widget.iWidget;

/**
 * A panel that manages a set of buttons
 *
 * @author Don DeCoteau
 */
public class NavigatorPanel extends aNavigatorPanel {

  /**
   * Constructs a new instance
   *
   * @param context
   *          the context
   * @param backIcon
   *          the icon for the back button
   */
  public NavigatorPanel(iWidget context, iPlatformIcon backIcon) {
    super(new NavigatorLayout(((aPlatformWidget) context).getAndroidContext()));
    ((NavigatorLayout) getView()).panel = this;
    initializePanel(backIcon);
  }

  protected iButton createBackButton(iPlatformIcon icon) {
    return new BackButton(icon);
  }

  protected iButton createButton(UIAction a) {
    return new Button(a);
  }

  protected iButton createButton(String text, iPlatformIcon icon) {
    return new Button(text, icon);
  }

  protected Object createButtonConstraints() {
    return new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
  }

  class BackButton extends Button {
    float padding;

    /**
     * Constructs a new instance
     */
    public BackButton(iPlatformIcon icon) {
      super(null, icon);
      setEnabled(false);
      ((ButtonView) getView()).isBack = true;
      getView().setOnClickListener(new OnClickListener() {
        public void onClick(View view) {
          backup();
        }
      });
    }

    public void setInsets(UIInsets n) {
      padding = n.left + n.right - 8;

      if (padding < 0) {
        padding = 0;
      }

      padding /= 2;

      UIEmptyBorder   in  = new UIEmptyBorder(n.top - 1, n.left - 1, n.bottom - 1, n.right - 1);
      iPlatformBorder out = (componentPainter == null)
                            ? null
                            : componentPainter.getBorder();

      if (out != null) {
        setBorder(new UICompoundBorder(out, in));
      } else {
        setBorder(in);
      }
    }

    public UIColor getBackground() {
      return NavigatorPanel.this.getBackground();
    }

    public iPlatformComponentPainter getComponentPainter() {
      return ((ButtonView) getView()).isPressed
             ? null
             : NavigatorPanel.this.getComponentPainter();
    }

    public UIDimension getPreferredSize() {
      UIDimension d = super.getPreferredSize();

      d.width -= (d.height / 2 - 1);
      d.width += padding;

      return d;
    }
  }


  @SuppressLint("ClickableViewAccessibility")
  class ButtonView extends ButtonViewEx {
    AndroidGraphics graphics;
    Rect            hit;
    boolean         isBack;
    boolean         isPressed;
    UIRectangle     rect;
    boolean         wasPressed;

    public ButtonView(Context context) {
      super(context, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
      if ((me.getAction() == MotionEvent.ACTION_DOWN) && isEnabled()) {
        if (isToggle() && (selectedButton != null) && (selectedButton.getView() == this) &&!alwaysFireAction) {
          return super.onTouchEvent(me);
        }

        isPressed  = true;
        wasPressed = true;

        if (isBack) {
          invalidate();
        } else {
          repaintPanel();
        }
      } else if (me.getAction() == MotionEvent.ACTION_UP) {
        isPressed  = false;
        wasPressed = false;

        if (isBack) {
          hit = null;
          invalidate();
        } else {
          repaintPanel();
        }
      }

      return super.onTouchEvent(me);
    }

    @Override
    public boolean isEnabled() {
      if (!NavigatorPanel.this.isEnabled()) {
        return false;
      }

      return super.isEnabled();
    }

    protected void onDraw(Canvas canvas) {
      if (isBack) {
        graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

        if (rect == null) {
          rect = new UIRectangle();
        }

        rect.width  = getWidth();
        rect.height = getHeight();
        paintButton(graphics, rect, isPressed);
      }

      super.onDraw(canvas);
    }
  }


  static class NavigatorLayout extends ViewGroupEx {
    NavigatorPanel panel;
    UIRectangle    rect;

    public NavigatorLayout(Context context) {
      super(context);
      measureType = MeasureType.HORIZONTAL;
    }

    public void draw(Canvas canvas) {
      if (matrix != null) {
        canvas.concat(matrix);
      }

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

    @Override
    protected void onDraw(Canvas canvas) {
      if (rect == null) {
        rect = new UIRectangle();
      }

      rect.width  = getWidth();
      rect.height = getHeight();
      panel.paintPanel(graphics, rect);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      panel.layout(right - left, bottom - top);
    }
  }


  /**
   * Class for buttons on the panel
   */
  protected class Button extends aButton {

    /**
     * Creates a new instance
     *
     * @param text
     *          the button's text
     */
    public Button(String text) {
      this(text, null, null);
    }

    /**
     * Creates a new instance
     *
     * @param a
     *          the button's action
     */
    public Button(UIAction a) {
      this(null, null, a);
    }

    /**
     * Creates a new instance
     *
     * @param text
     *          the button's text
     * @param icon
     *          the button's icon
     */
    public Button(String text, iPlatformIcon icon) {
      this(text, icon, null);
    }

    /**
     * Creates a new instance
     *
     * @param text
     *          the button's text
     * @param icon
     *          the button's icon
     *
     * @param a
     *          the button's action
     */
    protected Button(String text, iPlatformIcon icon, UIAction a) {
      setView(new ButtonView(NavigatorPanel.this.getView().getContext()));
      view.setBackground(NullDrawable.getInstance());
      view.setOnClickListener(this);
      initialize(text, icon, a);
    }

    public void doClick() {
      final boolean b = alwaysFireAction;

      try {
        alwaysFireAction = true;
        super.doClick();
      } finally {
        alwaysFireAction = b;
      }
    }

    @Override
    public void onClick(View view) {
      if (panelType == PanelType.HIERARCHICAL) {
        removeChildren(this);
      }

      if (isSelected() && isToggle() &&!alwaysFireAction) {
        return;
      }

      setSelected(!selected);
      repaintPanel();
      super.onClick(view);
    }

    public UIRectangle getBounds(UIRectangle rect) {
      if (rect == null) {
        rect = new UIRectangle();
      }

      View v = getView();

      rect.x      = v.getLeft();
      rect.y      = v.getRight();
      rect.height = v.getHeight();
      rect.width  = v.getWidth();

      return rect;
    }

    /**
     * Tests if the button is pressed
     *
     * @return true if it is; false otherwise
     */
    public boolean isPressed() {
      return ((ButtonView) getView()).isPressed;
    }
  }
}
