/*
 * @(#)ListRowContainer.java
 *
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.PainterUtils.GripperIcon;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iListView.EditingMode;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.effects.ValueRangeAnimator;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

public class ListRowContainer extends JPanelEx {
  int                                 editorX = 0;
  Rectangle                           editorBounds;
  ListView                            parent;
  boolean                             showSelections;
  protected UIColor                   alternatingColorPaint;
  protected ValueRangeAnimator        animator;
  protected boolean                   checked;
  protected iPlatformIcon             checkedIcon;
  protected JComponent                contentView;
  protected int                       contentViewX;
  protected UIColor                   dividerPaint;
  protected iPlatformIcon             draggableIcon;
  protected boolean                   editing;
  protected iPlatformComponent        editingComponent;
  protected boolean                   editorVerticallyCentered;
  protected int                       maxIconHeight;
  protected iPlatformComponentPainter pressedPainter;
  protected boolean                   truncateContentWhenEditing;
  protected iPlatformIcon             uncheckedIcon;
  private int                         animateX;
  private int                         contentViewPrefWidth;
  private int                         position;
  int measuringMaxWidth;

  public ListRowContainer(ListView parent) {
    super();
    this.parent    = parent;
    showSelections = (parent.editingMode == EditingMode.SELECTION)
                     || (parent.editingMode == EditingMode.REORDERING_AND_SELECTION);
  }

  public void hideRowEditingComponent(boolean animate) {
    if (editingComponent != null) {
      if (animator != null) {
        animator.cancel();
      }

      if (animate) {
        animateDeleteButton(false);
      } else {
        removeButton();
      }
    }
  }

  public void prepareForReuse(int position, boolean checked, int animateX, JComponent content, iPlatformIcon ic) {
    this.position      = position;
    this.checked       = checked;
    this.animateX      = animateX;
    this.draggableIcon = ic;

    animator             = null;
    setContentView(content);
  }

  @Override
  public void repaint() {}

  @Override
  public void revalidate() {}

  public void showRowEditingComponent(iPlatformComponent c, boolean animate, boolean centerVertically) {
    editorVerticallyCentered = centerVertically;
    editingComponent         = c;

    if (editorBounds == null) {
      editorBounds = new Rectangle();
    }

    if (c.getView().getParent() != null) {
      ((JComponent) c.getView().getParent()).remove(c.getView());
    }

    addEditingComponent(c);

    float         width  = getWidth();
    float         height = getHeight();
    float         w;
    UIDimension d = editingComponent.getPreferredSize();

    w = d.width;

    float h = d.height;

    if (!editorVerticallyCentered) {
      h = getHeight();
    }

    float tt = (height - h) / 2;

    editingComponent.setBounds(width, tt, w, h);

    if (animate) {
      animateDeleteButton(true);
    } else {
      editorX = (int)Math.ceil(w);
    }
  }

  public void toggleCheckedState() {
    checked = !checked;
    invalidate();
  }

  public void setAnimateX(int x) {
    animateX = x;
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);

    if (height > 0 && contentView!=null) {

      x = contentViewX;
      int cw=width-x;
      if (editingComponent != null) {
        UIDimension d = editingComponent.getSize();
        int         w = d.intWidth();
        int         h = d.intHeight();

        if (!editorVerticallyCentered) {
          h = getHeight();
        }

        int tt = (height - h) / 2;

        editingComponent.setBounds(width - editorX + animateX, tt, w, h);
        editorBounds.setBounds(width - editorX + animateX, tt, w, h);

        if ((contentViewPrefWidth > (width - w)) &&!truncateContentWhenEditing) {
          x -= editorX;
        }
      }
      cw-=animateX;
      if ((draggableIcon != null) && editing) {
        int w = draggableIcon.getIconWidth()+animateX;
        cw -= w;
        cw -= ScreenUtils.PLATFORM_PIXELS_8;
      }
      Component.fromView(contentView).setBounds(x + animateX, 0, cw, height);
    }
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

  public void setContentView(JComponent contentView) {
    if (this.contentView != null) {
      remove(this.contentView);
    }

    add(contentView);
    this.contentView = contentView;
  }

  public void setDraggableIcon(iPlatformIcon draggableIcon) {
    this.draggableIcon = draggableIcon;
    calculateIconRequirements();
  }

  public void setEditing(boolean editing) {
    if (this.editing != editing) {
      this.editing = editing;
      checked      = false;

      if (editing && (uncheckedIcon == null)) {
        initializeIcons();
      }

      animateX = 0;
      calculateIconRequirements();
    }
  }

  public void setEditingAnimationPosition(int animateX) {}

  public void setUncheckedIcon(iPlatformIcon uncheckedIcon) {
    this.uncheckedIcon = uncheckedIcon;
    calculateIconRequirements();
  }

  public iPlatformIcon getCheckedIcon() {
    return checkedIcon;
  }

  public JComponent getContentView() {
    return contentView;
  }

  public int getContentViewX() {
    return contentViewX;
  }

  public Rectangle getDeleteBounds() {
    return editorBounds;
  }

  public iPlatformComponent getDeleteButton() {
    return editingComponent;
  }

  public iPlatformIcon getDraggableIcon() {
    return draggableIcon;
  }
  @Override
  public Dimension getPreferredSize() {
    Dimension d = super.getPreferredSize();
    if(measuringMaxWidth>0) {
      d.width=measuringMaxWidth;
    }
    d.height+=8;

    return d;
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    int count     = getComponentCount();
    float maxHeight = 0;

    maxWidth = 0;
    
    for (int i = 0; i < count; i++) {
      JComponent child = (JComponent) getComponent(i);

      if (child.isVisible()) {
        iPlatformComponent pc = Component.fromView(child);

        pc.getPreferredSize(size,measuringMaxWidth);
        maxWidth  += size.width;
        maxHeight = Math.max(maxHeight, size.height);

        if (child == contentView) {
          contentViewPrefWidth = size.intWidth();
        }
      }
    }

    maxWidth    += contentViewX;
    maxHeight   = Math.max(maxHeight, maxIconHeight);
    size.width  = maxWidth;
    size.height = maxHeight+2;
  }

  public iPlatformIcon getUncheckedIcon() {
    return uncheckedIcon;
  }

  public boolean isChecked() {
    return checked;
  }

  public boolean isEditing() {
    return editing;
  }

  protected void addEditingComponent(iPlatformComponent b) {
    add(b.getView());
  }

  protected void animateDeleteButton(final boolean in) {
    if (in) {
      animator = ValueRangeAnimator.ofFloat(0f, 1f);
      editorX  = 0;
    } else {
      animator = ValueRangeAnimator.ofFloat(1f, 0f);
      editorX  = editingComponent.getWidth();
    }

    animator.setDuration(150);
    animator.addListener(new iAnimatorListener() {
      @Override
      public void animationStarted(iPlatformAnimator animation) {}
      @Override
      public void animationEnded(iPlatformAnimator animation) {
        animator = null;

        if (!in) {
          removeButton();
        } else {
          editorX = editingComponent.getWidth();
        }

        parent.repaint();
      }
    });
    animator.addValueListener(new iAnimatorValueListener() {
      @Override
      public void valueChanged(iPlatformAnimator animator, float fv) {
        editorX = (int) (fv * editingComponent.getWidth());
        parent.repaintRow(parent.editingRow);
      }
    });
    animator.start();
  }

  protected void calculateIconRequirements() {
    int h = 0;
    int w = 0;

    if (editing && showSelections) {
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

  @Override
  protected void paintComponent(Graphics g) {
    int       right  = getWidth();
    int       bottom = getHeight();
    boolean   showh  = dividerPaint != null;
    final int d      = ScreenUtils.PLATFORM_PIXELS_1;
    Color     c      = g.getColor();

    if ((alternatingColorPaint != null) && (position % 2 == 1)) {
      g.setColor(alternatingColorPaint);
      g.fillRect(0, 0, right, bottom);
    }

    if (isPressed() && (pressedPainter != null)) {
      pressedPainter.paint(graphics, 0, 0, right, bottom, iPainter.UNKNOWN);
    }

    super.paintComponent(g);

    if (editing) {
      int           h;
      int           cleft  = contentView.getX();
      int           cright = cleft + contentView.getWidth();
      iPlatformIcon icon   = checked
                             ? checkedIcon
                             : uncheckedIcon;

      if ((icon != null) && showSelections) {
        h = icon.getIconHeight();
        icon.paint(graphics, cleft - ScreenUtils.PLATFORM_PIXELS_4 + animateX - icon.getIconWidth(), (bottom - h) / 2,
                   icon.getIconWidth(), h);
      }

      if ((draggableIcon != null)) {
        if (draggableIcon instanceof GripperIcon) {
          draggableIcon.paint(graphics, cright + ScreenUtils.PLATFORM_PIXELS_4 - (animateX * 2), 0,
                              draggableIcon.getIconWidth(), bottom);
        } else {
          h = draggableIcon.getIconHeight();
          draggableIcon.paint(graphics, cright + ScreenUtils.PLATFORM_PIXELS_4 - (animateX * 2), (bottom - h) / 2,
                              draggableIcon.getIconWidth(), h);
        }
      }
    }

    if (showh) {
      bottom -= d;
    }

    if (showh) {
      g.setColor(dividerPaint);
      g.fillRect(0, bottom, right, bottom + d);
    }

    g.setColor(c);
  }

  protected void removeButton() {
    if (editingComponent != null) {
      remove(editingComponent.getView());
      editingComponent = null;

      int row = parent.editingRow;

      if (row != -1) {
        parent.repaintRow(row);
        parent.editingRow = -1;
      }
    }
  }

  private boolean isPressed() {
    return false;
  }
}
