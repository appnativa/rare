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

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Paint;

import android.view.MotionEvent;
import android.view.View;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.view.SpacerView;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.widget.iWidget;

public class SplitPanePanel extends aSplitPanePanel {
  AndroidGraphics       graphics;
  protected Paint       dragPaint;
  private SplitPaneView splitPaneView;

  public SplitPanePanel(Context context) {
    super();
    setView(new SplitPaneView(context));
    splitPaneView    = (SplitPaneView) view;
    changeEvent      = new ChangeEvent(this);
    continuousLayout = false;
  }

  public SplitPanePanel(iWidget context) {
    this(context.getAppContext().getActivity());
  }

  public void addChangeListener(iChangeListener l) {
    getEventListenerList().add(iChangeListener.class, l);
  }

  public Divider createDivider() {
    return new Divider(new DividerView(splitPaneView.getContext()));
  }

  public void removeChangeListener(iChangeListener l) {
    if (listenerList != null) {
      listenerList.remove(iChangeListener.class, l);
    }
  }

  int getWindowX(MotionEvent e) {
    return UIScreen.snapToPosition(e.getRawX());
  }

  int getWindowY(MotionEvent e) {
    return UIScreen.snapToPosition(e.getRawY());
  }

  class SplitPaneView extends ViewGroupEx {
    UIDimension size = new UIDimension();
    UIColor     dragColor;
    int         dragPos;

    public SplitPaneView(Context context) {
      super(context);
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

      if (!continuousLayout && dragging && (dragPos > 0)) {
        if (leftToRight) {
          if (dividerDragPainter != null) {
            dividerDragPainter.paint(this, canvas, dragPos, 0, dividerSize, getHeight(), iPainter.HORIZONTAL);
          } else if (dragPaint != null) {
            canvas.drawRect(dragPos, 0, dragPos + dividerSize, getBottom(), dragPaint);
          }
        } else {
          if (dividerDragPainter != null) {
            dividerDragPainter.paint(this, canvas, 0, dragPos, getWidth(), dividerSize, iPainter.HORIZONTAL);
          } else if (dragPaint != null) {
            canvas.drawRect(0, dragPos, getRight(), dragPos + dividerSize, dragPaint);
          }
        }
      }
    }

    protected void dividerDrag(MotionEvent me) {
      int d = leftToRight
              ? (getWindowX(me) - dragMarkX)
              : (getWindowY(me) - dragMarkY);

      if ((Math.abs(d) > ((float) dividerSize / 2f))) {
        dragPos = 0;

        if (continuousLayout) {
          resizeViaDivider(dragDivider, d);
        } else {
          View v = dragDivider.getView();

          if (leftToRight) {
            dragPos = Math.max(v.getLeft() + d, getPaddingLeft());
          } else {
            dragPos = Math.max(v.getTop() + d, getPaddingTop());
          }

          repaint();
        }
      }
    }

    protected void dividerDragFinished(MotionEvent me) {
      dragging       = false;
      draggingInited = false;
      dragPaint      = null;

      if (dragDivider == null) {
        return;
      }

      int d = leftToRight
              ? (getWindowX(me) - dragMarkX)
              : (getWindowY(me) - dragMarkY);

      if (!continuousLayout && (Math.abs(d) > ((float) dividerSize / 2f))) {
        resizeViaDivider(dragDivider, d);
      }

      dragDivider.draggingStopped();
      dragDivider = null;
      repaint();
    }

    protected void dividerDragStarted(DividerView v, MotionEvent me) {
      Divider d = (Divider) Component.fromView(v);

      dragging    = true;
      dragDivider = d;
      dragMarkX   = getWindowX(me);
      dragMarkY   = getWindowY(me);
      dragPos     = 0;
      setupDrag(d);

      if (dividerDragPainter == null) {
        if (dragColor == null) {
          dragColor = getDragColor(true);
        }

        dragPaint = new Paint();
        dragPaint.setColor(dragColor.getColor());
      }

      draggingInited = true;
      dividerDrag(me);
      repaint();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      UIInsets in = getInsets(computeInsets);

      size.setSize(right - left, bottom - top);
      layoutContainer(size, in);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      setMeasureType(leftToRight
                     ? MeasureType.HORIZONTAL
                     : MeasureType.VERTICAL);
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected boolean resizeViaDivider(Divider d, int delta) {
      boolean ret = SplitPanePanel.this.resizeViaDivider(d, delta);

      if (ret) {
        revalidate();
        repaint();

        if (listenerList != null) {
          Utils.fireChangeEvent(listenerList, changeEvent);
        }
      }

      return ret;
    }
  }


  protected class DividerView extends SpacerView {
    private iPlatformIcon gripper;

    public DividerView(Context context) {
      super(context);
    }

    public DividerView(Context context, int width, int height) {
      super(context, width, height);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
      if (userResizeable) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
          splitPaneView.dividerDragStarted(this, me);

          return true;
        }

        if (me.getAction() == MotionEvent.ACTION_MOVE) {
          splitPaneView.dividerDrag(me);

          return true;
        }

        if ((me.getAction() == MotionEvent.ACTION_UP) || (me.getAction() == MotionEvent.ACTION_CANCEL)) {
          splitPaneView.dividerDragFinished(me);

          return true;
        }
      }

      if (dragging) {
        return true;
      }

      return super.dispatchTouchEvent(me);
    }

    /**
     * @param gripper
     *          the gripper to set
     */
    public void setGripper(iPlatformIcon gripper) {
      this.gripper = gripper;
    }

    /**
     * @return the gripper
     */
    public iPlatformIcon getGripper() {
      return gripper;
    }

    protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);

      int width  = getWidth();
      int height = getHeight();

      if (continuousLayout && dragging) {
        if (dragPaint != null) {
          canvas.drawRect(0, 0, width, height, dragPaint);
        } else if (dividerDragPainter != null) {
          dividerDragPainter.paint(this, canvas, 0, 0, width, height, iPainter.HORIZONTAL);
        }
      }

      if ((gripper == null) && showGripper) {
        gripper = new SplitPaneGripperIcon() {
          @Override
          public void paint(Canvas g, float x, float y, float width, float height) {
            graphics = AndroidGraphics.fromGraphics(g, splitPaneView, graphics);
            paint(graphics, x, y, width, height);
          }
        };
      }

      if (gripper != null) {
        gripper.paint(canvas, 0, 0, width, height);
      }
    }
  }
}
