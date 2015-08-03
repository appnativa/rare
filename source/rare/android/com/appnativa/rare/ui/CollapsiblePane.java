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

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.view.BorderLayoutView;
import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.ui.PainterUtils.ChevronIcon;
import com.appnativa.rare.ui.PainterUtils.TwistyIcon;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.MouseEvent;

/**
 *
 * @author Don DeCoteau
 */
@SuppressLint("ClickableViewAccessibility")
public class CollapsiblePane extends aCollapsiblePane {
  AndroidGraphics graphics;

  /**
   * Creates a new instance of CollapsiblePane
   * @param context the android context
   */
  public CollapsiblePane(Context context) {
    this(context, null, null);
  }

  /**
   * Creates a new instance of CollapsiblePane
   *
   * @param context the android context
   * @param title the title
   * @param c the component
   */
  public CollapsiblePane(Context context, String title, iPlatformComponent c) {
    super(new BorderLayoutView(context));
    eventObject              = new ExpansionEvent(this);
    toggleOnTitleSingleClick = true;
    initComponents();

    if (title != null) {
      setTitleText(title);
    }

    if (c != null) {
      add(c);
    }
  }

  protected void createAndAddTitleLabel() {
    titleComponent = new TitleComponent(this);
    setTopView(titleComponent);
  }

  protected iPlatformIcon createChevronIcon(boolean up) {
    return new ChevronIcon(this, up) {
      @Override
      public void paint(Canvas g, float x, float y, float width, float height) {
        graphics = AndroidGraphics.fromGraphics(g, view, graphics);
        paint(graphics, x, y, width, height);
      }
    };
  }

  protected iPlatformIcon createTwistyIcon(boolean up) {
    return new TwistyIcon(this, up) {
      @Override
      public void paint(Canvas g, float x, float y, float width, float height) {
        graphics = AndroidGraphics.fromGraphics(g, view, graphics);
        paint(graphics, x, y, width, height);
      }
    };
  }

  static class TitleComponent extends aTitleComponent implements View.OnTouchListener {
    CollapsiblePane pane;

    TitleComponent(CollapsiblePane pane) {
      super();
      this.pane = pane;

      LabelView title = new LabelView(view.getContext());

      titleLabel = new ActionComponent(title);
      view.setOnTouchListener(this);
      setCenterView(titleLabel);
    }

    public boolean onTouch(View view, MotionEvent me) {
      if (!pane.userControllable ||!isEnabled()) {
        return false;
      }

      if (me.getAction() != MotionEvent.ACTION_UP) {
        return true;    //so we get called back on mouse up
      }

      MouseEvent         e = new MouseEvent(view, me, 0);
      iPlatformComponent n = iconOnLeft
                             ? getRightView()
                             : getLeftView();

      if (n != null) {
        UIRectangle r = new UIRectangle(n.getLocationOnScreen(), n.getSize());

        if (r.contains((int) e.getScreenX(), (int) e.getScreenY())) {
          pane.togglePane();

          return true;
        }
      }

      if ((pane.toggleOnTitleSingleClick || (e.getClickCount() > 1))) {
        pane.togglePane();

        return true;
      }

      return super.onTouch(view, me);
    }

    public void setToolTipText(CharSequence text) {}

    @Override
    protected iActionComponent createIconComponent() {
      return new ActionComponent(new LabelView(view.getContext()));
    }
  }


  class TitleView extends LabelView implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    GestureDetector touchHandler;

    public TitleView(Context context) {
      super(context);
      touchHandler = new GestureDetector(context, this);
      touchHandler.setIsLongpressEnabled(false);
      touchHandler.setOnDoubleTapListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
      touchHandler.onTouchEvent(event);

      return true;
    }

    public boolean onDoubleTap(MotionEvent me) {
      if (isEnabled() &&!toggleOnTitleSingleClick &&!isOverIcon((int) me.getX(), (int) me.getY())) {
        togglePane();
      }

      return true;
    }

    public boolean onDoubleTapEvent(MotionEvent me) {
      return false;
    }

    public boolean onDown(MotionEvent me) {
      return false;
    }

    public boolean onFling(MotionEvent me, MotionEvent me1, float f, float f1) {
      return false;
    }

    public void onLongPress(MotionEvent me) {}

    public boolean onScroll(MotionEvent me, MotionEvent me1, float f, float f1) {
      return false;
    }

    public void onShowPress(MotionEvent me) {}

    public boolean onSingleTapConfirmed(MotionEvent me) {
      if (!userControllable) {
        return false;
      }

      if (isEnabled() && (isOverIcon((int) me.getX(), (int) me.getY()) || toggleOnTitleSingleClick)) {
        togglePane();
      }

      return true;
    }

    public boolean onSingleTapUp(MotionEvent me) {
      return false;
    }

    public boolean isOverIcon(int x, int y) {
      iPlatformIcon ic = paneExpanded
                         ? collapseIcon
                         : expandIcon;

      if (ic == null) {
        return false;
      }

      int iy = (getHeight() - ic.getIconHeight()) / 2;

      if (titleIconOnLeft) {
        if ((x >= (getWidth() - ic.getIconWidth())) && (y >= iy) && (y <= (iy + ic.getIconHeight()))) {
          return true;
        }
      } else {
        if ((x < ic.getIconWidth()) && (y >= iy) && (y <= (iy + ic.getIconHeight()))) {
          return true;
        }
      }

      return false;
    }
  }
}
