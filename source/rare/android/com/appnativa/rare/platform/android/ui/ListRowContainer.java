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

package com.appnativa.rare.platform.android.ui;

import android.annotation.SuppressLint;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Paint;

import android.view.View;
import android.view.ViewGroup;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.aDataItemListModelEx.ListRow;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.PainterUtils.GripperIcon;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.effects.ValueRangeAnimator;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

public class ListRowContainer extends ViewGroupEx {
  protected iPlatformIcon             accessoryIcon;
  protected iPlatformComponent        accessoryView;
  protected Paint                     alternatingColorPaint;
  protected ValueRangeAnimator        animator;
  protected boolean                   checked;
  protected iPlatformIcon             checkedIcon;
  protected View                      contentView;
  protected int                       contentViewX;
  protected Paint                     dividerPaint;
  protected iPlatformIcon             draggableIcon;
  protected iPlatformIcon             editingAccessoryIcon;
  protected iPlatformComponent        editingAccessoryView;
  protected iPlatformComponent        editingComponent;
  protected boolean                   editorVerticallyCentered;
  protected boolean                   listEditingMode;
  protected int                       maxIconHeight;
  protected iPlatformComponentPainter pressedPainter;
  protected iPlatformComponentPainter selectedPainter;
  protected boolean                   showSelections;
  protected boolean                   truncateContentWhenEditing;
  protected iPlatformIcon             uncheckedIcon;
  private int                         animateX;
  private boolean                     buttonAnimationOnly;
  private int                         position;
  UIDimension                         anamitingContentSize;
  protected boolean                   selected;

  public ListRowContainer(Context context) {
    super(context);
  }

  public ListRowContainer(View content) {
    super(content.getContext());
    addView(content);
    this.contentView = content;
  }

  @Override
  public void draw(Canvas canvas) {
    int       right  = getWidth();
    int       bottom = getHeight();
    boolean   showh  = dividerPaint != null;
    final int d      = ScreenUtils.PLATFORM_PIXELS_1;

    if ((alternatingColorPaint != null) && (position % 2 == 1)) {
      canvas.drawRect(0, 0, right, bottom, alternatingColorPaint);
    }

    if (isPressed()) {
      if (pressedPainter != null) {
        pressedPainter.paint(this, canvas, 0, 0, right, bottom, iPainter.UNKNOWN);
      }
    } else if (selected) {
      if (selectedPainter != null) {
        selectedPainter.paint(this, canvas, 0, 0, right, bottom, iPainter.UNKNOWN);
      }
    }

    super.draw(canvas);

    if (listEditingMode) {
      int           h;
      int           cleft  = contentView.getLeft();
      int           cright = contentView.getRight();
      iPlatformIcon icon   = null;

      if (showSelections) {
        icon = checked
               ? checkedIcon
               : uncheckedIcon;
      }

      if (icon != null) {
        h = icon.getIconHeight();
        icon.paint(canvas, cleft - ScreenUtils.PLATFORM_PIXELS_4 + animateX - icon.getIconWidth(), (bottom - h) / 2,
                   icon.getIconWidth(), h);
      }

      if ((draggableIcon != null)) {
        if (draggableIcon instanceof GripperIcon) {
          draggableIcon.paint(canvas, cright + ScreenUtils.PLATFORM_PIXELS_4 - (animateX * 2), 0,
                              draggableIcon.getIconWidth(), bottom);
        } else {
          h = draggableIcon.getIconHeight();
          draggableIcon.paint(canvas, cright + ScreenUtils.PLATFORM_PIXELS_4 - (animateX * 2), (bottom - h) / 2,
                              draggableIcon.getIconWidth(), h);
        }
      } else {
        iPlatformComponent v = listEditingMode
                               ? editingAccessoryView
                               : accessoryView;

        if (v == null) {
          v = accessoryView;
        }

        if (v == null) {
          iPlatformIcon ic = listEditingMode
                             ? editingAccessoryIcon
                             : accessoryIcon;

          if (ic == null) {
            ic = accessoryIcon;
          }

          if (ic != null) {
            h = ic.getIconHeight();
            ic.paint(canvas, cright + ScreenUtils.PLATFORM_PIXELS_4 - (animateX * 2), (bottom - h) / 2,
                     ic.getIconWidth(), h);
          }
        }
      }
    }

    if (showh) {
      bottom -= d;
    }

    if (showh) {
      canvas.drawRect(0, bottom, right, bottom + d, dividerPaint);
    }
  }

  public void hideRowEditingComponent(boolean animate) {
    if (editingComponent != null) {
      if (animator != null) {
        animator.cancel();
      }

      if (animate) {
        animateEditingComponent(false);
      } else {
        removeEditingComponent();
      }
    }
  }

  public void prepareForReuse(View parent, int position, boolean isSelected) {
    this.position = position;

    if (editingComponent != null) {
      removeEditingComponent();
    }

    animator             = null;
    contentViewX         = 0;
    listEditingMode      = false;
    checked              = false;
    accessoryIcon        = null;
    editingAccessoryIcon = null;
    accessoryView        = null;
    editingAccessoryView = null;

    if (contentView instanceof ListRow) {
      ((ListRow) contentView).prepareForReuse(parent, position, isSelected);
    }
  }

  @SuppressLint("WrongCall")
  public void showRowEditingComponent(iPlatformComponent component, boolean animate, boolean centerVertically) {
    editorVerticallyCentered = centerVertically;
    editingComponent         = component;

    if (component.getView().getParent() != null) {
      ((ViewGroup) component.getView().getParent()).removeView(component.getView());
    }

    addEditingComponent(component);

    float       width  = getWidth();
    float       height = getHeight();
    float       w;
    UIDimension d = editingComponent.getPreferredSize();

    w = d.width;

    float h = d.height;

    if (!editorVerticallyCentered) {
      h = getHeight();
    }

    float tt = (height - h) / 2;

    editingComponent.setBounds(width, tt, w, h);
    buttonAnimationOnly = truncateContentWhenEditing;

    if (animate) {
      animateEditingComponent(true);
    } else {
      onLayout(true, 0, 0, getWidth(), getHeight());
    }
  }

  public void toggleEditingModeCheckedState() {
    checked = !checked;
    invalidate();
  }

  public void setAnimateX(int x) {
    animateX = x;
  }

  public void setChecked(boolean checked) {
    if (this.checked != checked) {
      this.checked = checked;
      invalidate();
    }
  }

  public void setCheckedIcon(iPlatformIcon checkedIcon) {
    this.checkedIcon = checkedIcon;
    calculateIconRequirements();
  }

  public void setContentView(View contentView) {
    if (this.contentView != null) {
      removeView(this.contentView);
    }

    addView(contentView);
    this.contentView = contentView;
  }

  public void setDraggableIcon(iPlatformIcon draggableIcon) {
    this.draggableIcon = draggableIcon;

    if (draggableIcon != null) {
      accessoryIcon        = null;
      editingAccessoryIcon = null;
      accessoryView        = null;
      editingAccessoryView = null;
    }

    calculateIconRequirements();
  }

  public void setInListEditingMode(boolean editing) {
    if (this.listEditingMode != editing) {
      this.listEditingMode = editing;
      checked              = false;

      if (editing && (uncheckedIcon == null)) {
        initializeIcons();
      }

      animateX = 0;
      calculateIconRequirements();
      requestLayout();
      invalidate();
    }
  }

  public void setShowSelections(boolean showSelections) {
    this.showSelections = showSelections;
  }

  public void setUncheckedIcon(iPlatformIcon uncheckedIcon) {
    this.uncheckedIcon = uncheckedIcon;
    calculateIconRequirements();
  }

  public iPlatformIcon getCheckedIcon() {
    return checkedIcon;
  }

  public View getContentView() {
    return contentView;
  }

  public int getContentViewX() {
    return contentViewX;
  }

  public iPlatformIcon getDraggableIcon() {
    return draggableIcon;
  }

  public iPlatformComponent getRowEditingComponent() {
    return editingComponent;
  }

  public iPlatformIcon getUncheckedIcon() {
    return uncheckedIcon;
  }

  public boolean isCheckedInEditingMode() {
    return checked;
  }

  public boolean isInListEditingMode() {
    return listEditingMode;
  }

  protected void addEditingComponent(iPlatformComponent c) {
    addView(c.getView());
    contentView.requestLayout();
    contentView.invalidate();
  }

  protected void animateEditingComponent(final boolean in) {
    if (in) {
      animator = ValueRangeAnimator.ofFloat(0f, 1f);
    } else {
      animator = ValueRangeAnimator.ofFloat(1f, 0f);
    }

    animator.setDuration(250);
    animator.addListener(new iAnimatorListener() {
      @Override
      public void animationEnded(iPlatformAnimator source) {
        if (animator != null) {
          animator.dispose();
          animator = null;

          if (!in) {
            removeEditingComponent();
          }

          requestLayout();
        }
      }
      @Override
      public void animationStarted(iPlatformAnimator source) {}
    });
    animator.addValueListener(new iAnimatorValueListener() {
      @Override
      public void valueChanged(iPlatformAnimator animator, float value) {
        int width   = getWidth();
        int dbwidth = editingComponent.getWidth();
        int v       = (int) (value * dbwidth);

        if (buttonAnimationOnly) {
          Component.fromView(contentView).setBounds(contentViewX, 0, anamitingContentSize.width - v,
                             anamitingContentSize.height);
        } else {
          Component.fromView(contentView).setBounds(contentViewX - v, 0, anamitingContentSize.width,
                             anamitingContentSize.height);
        }

        editingComponent.setBounds(width - v, editingComponent.getY(), dbwidth, editingComponent.getHeight());
        invalidate();
      }
    });
    anamitingContentSize = Component.fromView(contentView).getSize();
    animator.start();
  }

  protected void calculateIconRequirements() {
    int h = 0;
    int w = 0;

    if (listEditingMode && showSelections) {
      if (uncheckedIcon == null) {
        initializeIcons();
      }

      if (draggableIcon != null) {
        h = draggableIcon.getIconHeight();
      }

      if (checkedIcon != null) {
        h = Math.max(h, checkedIcon.getIconHeight());
        w = Math.max(w, checkedIcon.getIconWidth());
      }

      if (uncheckedIcon != null) {
        h = Math.max(h, uncheckedIcon.getIconHeight());
        w = Math.max(w, uncheckedIcon.getIconWidth());
      }
    }

    contentViewX  = (w > 0)
                    ? w + ScreenUtils.PLATFORM_PIXELS_4
                    : 0;
    maxIconHeight = (h > 0)
                    ? h + ScreenUtils.PLATFORM_PIXELS_4
                    : 0;
  }

  protected int[] createIntValue(int size) {
    int a[] = new int[size];

    for (int i = 0; i < size; i++) {
      a[i] = i;
    }

    return a;
  }

  protected void initializeIcons() {
    uncheckedIcon = Platform.getResourceAsIcon("Rare.List.editorUncheckedIcon");
    checkedIcon   = Platform.getResourceAsIcon("Rare.List.editorCheckedIcon");
  }

  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    if (animator == null) {
      float height = bottom - top;
      float cw     = right - left;
      float x      = contentViewX;
      float w;
      float h;

      cw -= x;
      cw -= animateX;

      if ((draggableIcon != null) && listEditingMode) {
        w  = draggableIcon.getIconWidth() + ScreenUtils.PLATFORM_PIXELS_8;
        cw -= (w + animateX);
      }

      if (editingComponent != null) {
        UIDimension d = editingComponent.getSize();

        w = d.width;
        h = d.height;

        if (!editorVerticallyCentered) {
          h = getHeight();
        }

        float tt = (height - h) / 2;

        editingComponent.setBounds(right - w + animateX, tt, w, h);
        right -= w;

        if (!truncateContentWhenEditing) {
          x -= w;
        }
      }

      iPlatformComponent v = listEditingMode
                             ? editingAccessoryView
                             : accessoryView;

      w = 0;

      if (v == null) {
        v = accessoryView;
      }

      if (v != null) {
        UIDimension size = v.getPreferredSize();

        w = size.width;
        h = size.height;
        v.setBounds(right - w, (height - h) / 2, w, h);
      } else {
        iPlatformIcon ic = listEditingMode
                           ? editingAccessoryIcon
                           : accessoryIcon;

        if (ic == null) {
          ic = accessoryIcon;
        }

        if (ic != null) {
          w = ic.getIconWidth() + ScreenUtils.PLATFORM_PIXELS_4;
        }
      }

      Component.fromView(contentView).setBounds(x + animateX, 0, cw, height);
    } else {
      if (anamitingContentSize != null) {
        left = contentView.getLeft();
        top  = contentView.getTop();
        contentView.layout(left, top, left + anamitingContentSize.intWidth(), bottom);
      }
    }
  }

  protected void onMeasureEx(int widthMeasureSpec, int heightMeasureSpec) {
    int count     = getChildCount();
    int maxHeight = 0;
    int maxWidth  = 0;

    measureChildren(widthMeasureSpec, heightMeasureSpec);

    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);

      if (child.getVisibility() != GONE) {
        maxWidth  += child.getMeasuredWidth() + viewGap;
        maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
      }
    }

    maxWidth  += contentViewX;
    maxWidth  += getPaddingLeft() + getPaddingRight();
    maxHeight += getPaddingTop() + getPaddingBottom();
    maxHeight = Math.max(maxHeight, maxIconHeight);
    maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
    maxWidth  = Math.max(maxWidth, getSuggestedMinimumWidth());
    setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec), resolveSize(maxHeight, heightMeasureSpec));
  }

  protected void removeEditingComponent() {
    if (editingComponent != null) {
      removeView(editingComponent.getView());
      editingComponent = null;
      contentView.layout(contentViewX, 0, getWidth(), getHeight());
      contentView.invalidate();
    }
  }
}
