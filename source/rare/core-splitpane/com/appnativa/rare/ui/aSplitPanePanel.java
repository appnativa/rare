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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.PainterUtils.GripperIcon;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.util.IdentityArrayList;
import com.appnativa.util.SNumber;

import java.util.ArrayList;

public abstract class aSplitPanePanel extends XPContainer {
  protected boolean                               autoOrient            = true;
  protected boolean                               showGripper           = true;
  protected boolean                               userResizeable        = true;
  protected boolean                               leftToRight           = true;
  protected boolean                               autoAdjustProportions = true;
  protected iPlatformComponent                    afterView;
  protected int                                   afterViewIndex;
  protected float                                 afterViewSize;
  protected iPlatformComponent                    beforeView;
  protected int                                   beforeViewIndex;
  protected float                                 beforeViewSize;
  protected boolean                               continuousLayout;
  protected UIMatteBorder                         dividerBorder;
  protected iPlatformComponentPainter             dividerDragPainter;
  protected iPlatformComponentPainter             dividerPainter;
  protected float                                 dividerSize;
  protected float                                 dividerSizes;
  protected Divider                               dragDivider;
  protected int                                   dragMarkX;
  protected int                                   dragMarkY;
  protected boolean                               dragging;
  protected boolean                               draggingInited;
  protected iPlatformIcon                         gripperIcon;
  protected boolean                               initiallyEven;
  protected IdentityArrayList<iPlatformComponent> noGrowList;
  protected float[]                               originalSplitPorpotions;
  protected float[]                               splitPorpotions;
  protected boolean                               paintDividerDragAllways;
  protected float[]                               tempPorpotions;
  protected iTransitionAnimator                   transitionAnimator;
  protected int                                   minimumSizePosition = -1;
  UIDimension                                     minimumSizePositionSize;
  protected boolean                               checkVisibility;

  public aSplitPanePanel() {
    super();
    initializeView();
  }

  public aSplitPanePanel(Object view) {
    super(view);
    initializeView();
  }

  public int getMinimumSizePanePosition() {
    return minimumSizePosition;
  }

  public void setUseMinimumSizeOfPaneAt(int panePosition) {
    minimumSizePosition = panePosition;

    if (panePosition > -1) {
      if (minimumSizePositionSize == null) {
        minimumSizePositionSize = new UIDimension();
      }

      calculateMinimumSizePositionSize();
    }
  }

  @Override
  public void revalidate() {
    super.revalidate();

    if (minimumSizePositionSize != null) {
      minimumSizePositionSize.width = 0;
    }
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    if ((position == -1) && (getPaneCount() > 0)) {
      super.add(createDivider(), null, -1);
      dividerSizes += dividerSize;
    }

    if ((constraints instanceof Boolean) && Boolean.FALSE.equals(constraints)) {
      if (noGrowList == null) {
        noGrowList = new IdentityArrayList<iPlatformComponent>(2);
      }

      noGrowList.add(c);
    }

    super.add(c, null, -1);
    checkVisibility = true;
  }

  public abstract void addChangeListener(iChangeListener l);

  public void checkOrientation(Object newConfig) {}

  public abstract Divider createDivider();

  @Override
  public void dispose() {
    if (!isDisposed()) {
      int len = getComponentCount();

      for (int i = 0; i < len; i++) {
        iPlatformComponent node = getComponentAt(i);

        if (node instanceof Divider) {
          ((Divider) node).dispose();
        }
      }

      super.dispose();

      if (noGrowList != null) {
        noGrowList.clear();
      }

      if (dividerDragPainter != null) {
        dividerDragPainter.dispose();
      }

      if (dividerPainter != null) {
        dividerPainter.dispose();
      }

      if ((transitionAnimator != null) && transitionAnimator.isAutoDispose()) {
        transitionAnimator.dispose();
      }

      transitionAnimator = null;
      afterView          = null;
      beforeView         = null;
      dividerBorder      = null;
      dividerDragPainter = null;
      dividerPainter     = null;
      dragDivider        = null;
      gripperIcon        = null;
      noGrowList         = null;
    }
  }

  public void paneVisibilityChanged() {
    iPlatformComponent v;
    float              removed    = 0;
    int                n          = 0;
    float              visible    = 0;
    boolean            visibility = true;
    int                len        = getComponentCount();

    for (int i = 0; i < len; i++) {
      v = getComponentAt(i);

      if (v instanceof Divider) {
        if ((i == 1) && (visibility == false)) {
          v.setVisible(false);
        }

        continue;
      }

      visibility = v.isVisible();

      if (visibility == false) {
        removed += splitPorpotions[n];
      } else {
        tempPorpotions[n] = 0;
        visible++;
      }

      if (i > 0) {
        v = getComponentAt(i - 1);

        if (v instanceof Divider) {
          if ((i != 2) || (getComponentAt(0).isVisible())) {
            v.setVisible(visibility);
          }
        }
      }

      n++;
    }

    if ((removed > 0) && (visible > 0)) {
      removed /= visible;
    } else {
      removed = 0;
    }

    len = tempPorpotions.length;

    for (int i = 0; i < len; i++) {
      tempPorpotions[i] = splitPorpotions[i] + removed;
    }

    checkVisibility = false;
    dividerSizes    = 0;

    for (int i = 0; i < len; i++) {
      iPlatformComponent node = getComponentAt(i);

      if ((node instanceof Divider) && node.isVisible()) {
        dividerSizes += dividerSize;
      }
    }
  }

  @Override
  public void remove(iPlatformComponent c) {
    int n = indexOf(c);

    if (n != -1) {
      iPlatformComponent d = null;

      if (getComponentCount() > 2) {
        if (n == 0) {
          d = getComponentAt(1);
        } else {
          d = getComponentAt(n - 1);
        }
      }

      super.remove(c);

      if (d instanceof Divider) {
        super.remove(d);
      }

      if (noGrowList != null) {
        noGrowList.remove(c);
      }
    }
  }

  @Override
  public void removeAll() {
    super.removeAll();
    dividerSizes = 0;

    if (noGrowList != null) {
      noGrowList.clear();
    }
  }

  public abstract void removeChangeListener(iChangeListener l);

  public void reverseRegions() {
    int                           len  = getComponentCount();
    ArrayList<iPlatformComponent> list = new ArrayList<iPlatformComponent>(len);

    for (int i = 0; i < len; i++) {
      iPlatformComponent pc = getComponentAt(i);

      if (!(pc instanceof Divider)) {
        list.add(pc);
      }
    }

    removeAll();
    reverse(splitPorpotions);
    reverse(tempPorpotions);
    len = list.size();

    for (int i = len - 1; i >= 0; i--) {
      add(list.get(i));
    }
  }

  public void toggleOrientation(boolean splitEvenly) {
    if (splitEvenly) {
      initiallyEven = true;
    }

    setLeftToRightSplitEx(!leftToRight);
  }

  public void setAutoAdjustProportions(boolean autoAdjustProportions) {
    this.autoAdjustProportions = autoAdjustProportions;
  }

  public void setContinuousLayout(boolean continuous) {
    continuousLayout = continuous;
  }

  public void setDividerDragPainter(iPlatformComponentPainter painter) {
    this.dividerDragPainter = painter;
  }

  public void setDividerIcon(iPlatformComponentPainter painter) {
    this.dividerPainter = painter;
  }

  public void setDividerSize(float size) {
    if (size != dividerSize) {
      float os = dividerSize;

      dividerSize = size;

      if (os == 0) {
        addDividers();
      }
    }

    revalidate();
    repaint();
  }

  /**
   * @param gripperIcon
   *          the gripperIcon to set
   */
  public void setGripperIcon(iPlatformIcon gripperIcon) {
    this.gripperIcon = gripperIcon;
  }

  public void setInitiallyEven(boolean even) {
    initiallyEven = even;
  }

  public void setLeftToRightSplit(boolean leftToRight) {
    autoOrient = false;
    setLeftToRightSplitEx(leftToRight);
  }

  public void setOneTouchExpandable(boolean oneTouch) {}

  public void setProportions(float... props) {
    setProportionsEx(createProporions(props, props.length + 1));
  }

  public void setShowGripper(boolean show) {
    showGripper = show;
  }

  public void setTopToBottom(boolean topToBottom) {
    setLeftToRightSplit(!topToBottom);
  }

  public void setTransitionAnimator(iTransitionAnimator animator) {
    transitionAnimator = animator;
  }

  public void setUserResizeable(boolean userResizeable) {
    this.userResizeable = userResizeable;
  }

  /**
   * @return the dividerSize
   */
  public float getDividerSize() {
    return dividerSize;
  }

  /**
   * @return the gripperIcon
   */
  public iPlatformIcon getGripperIcon() {
    return gripperIcon;
  }

  @Override
  public void getMinimumSizeEx(UIDimension size, float maxWidth) {
    float width  = 0;
    float height = 0;
    int   len    = getComponentCount();
    int   cpos   = 0;

    for (int i = 0; i < len; i++) {
      iPlatformComponent c = getComponentAt(i);

      c.getMinimumSize(size);

      if ((minimumSizePosition > -1) &&!(c instanceof Divider)) {
        if (minimumSizePosition == cpos) {
          minimumSizePositionSize.setSize(size);
        }

        cpos++;
      }

      if (leftToRight) {
        width  += size.width;
        height = Math.max(height, size.height);
      } else {
        height += size.height;
        width  = Math.max(width, size.width);
      }

      size.width  = width;
      size.height = height;
    }
  }

  public int getPaneCount() {
    int len   = getComponentCount();
    int count = 0;

    for (int i = 0; i < len; i++) {
      iPlatformComponent node = getComponentAt(i);

      if (!(node instanceof Divider)) {
        count++;
      }
    }

    return count;
  }

  public float[] getProportions() {
    return splitPorpotions;
  }

  public iTransitionAnimator getTransitionAnimator() {
    return transitionAnimator;
  }

  public boolean isAutoAdjustProportions() {
    return autoAdjustProportions;
  }

  public boolean isTopToBottom() {
    return !leftToRight;
  }

  public boolean isUserResizeable() {
    return userResizeable;
  }

  void reverse(float[] array) {
    for (int i = 0; i < array.length / 2; i++) {
      float f = array[i];

      array[i]                    = array[array.length - i - 1];
      array[array.length - i - 1] = f;
    }
  }

  protected float[] createProporions(float[] props, int len) {
    int i = 0;

    if ((props == null) || (props.length == 0)) {
      props = new float[len];

      float p = 1f / len;

      for (i = 0; i < props.length; i++) {
        props[i] = p;
      }
    } else {
      if (props.length <= len) {
        float p = 0;

        for (i = 0; i < props.length; i++) {
          p += props[i];
        }

        p = (1f - p) / (len - props.length);

        if (p < 0) {
          p = 0;
        }

        float[] d = new float[len];

        for (i = 0; i < len; i++) {
          if (i >= props.length) {
            d[i] = p;
          } else {
            d[i] = props[i];
          }
        }

        props = d;
      }
    }

    return props;
  }

  protected void fireChangeEvent() {
    if (listenerList == null) {
      return;
    }

    if (changeEvent == null) {
      changeEvent = new ChangeEvent(this);
    }

    Utils.fireChangeEvent(listenerList, changeEvent);
  }

  protected void initializeView() {
    paintDividerDragAllways = Platform.isTouchableDevice();
    leftToRight             = ScreenUtils.isWider();

    UIProperties defs = Platform.getUIDefaults();
    Integer      n    = defs.getInteger("Rare.SplitPane.dividerSize");

    if (n != null) {
      dividerSize = ScreenUtils.platformPixels(n);
    } else {
      dividerSize = ScreenUtils.platformPixels(8);

      if (paintDividerDragAllways) {
        dividerSize = Math.max(dividerSize, ScreenUtils.platformPixels(16));
      }
    }

    gripperIcon = defs.getIcon("Rare.SplitPane.gripperIcon");

    PaintBucket pb = defs.getPaintBucket("Rare.SplitPane.dividerPainter");

    if (pb == null) {
      UIColor c = defs.getColor("Rare.SplitPane.dividerBorderColor");

      dividerPainter = new UIComponentPainter();

      if (c != null) {
        dividerPainter.setBorder(dividerBorder = new UIMatteBorder(0, 1, 0, 1, c));
      }
    } else {
      dividerPainter = pb.getComponentPainter(true);
    }

    PaintBucket dpb = defs.getPaintBucket("Rare.SplitPane.dividerDragPainter");

    if (dpb == null) {
      dpb = (pb == null)
            ? null
            : (PaintBucket) pb.clone();
    }

    if (dpb != null) {
      dpb.setBackgroundColor(getDragColor(true));
      dividerDragPainter = dpb.getComponentPainter(true);
    } else {
      dividerDragPainter = (iPlatformComponentPainter) ((UIComponentPainter) dividerPainter).clone();
      dividerDragPainter.setBackgroundColor(getDragColor(true));
    }
  }

  protected boolean canChangeOrientationInCurrentState() {
    return true;
  }

  protected void layoutContainer(UIDimension size, UIInsets in) {
    if (checkVisibility) {
      paneVisibilityChanged();
    }

    int left   = in.intLeft();
    int top    = in.intTop();
    int width  = (int) Math.ceil(size.width - left - in.right);
    int height = (int) Math.ceil(size.height - top - in.bottom);
    int w      = width;
    int h      = height;

    if (autoOrient && canChangeOrientationInCurrentState()) {
      boolean t2b = (width > height)
                    ? false
                    : true;

      if (t2b != isTopToBottom()) {
        float props[] = originalSplitPorpotions;

        if (props != null) {
          setProportionsEx(originalSplitPorpotions);
        }

        toggleOrientation(false);
      }
    }

    if ((tempPorpotions == null) || initiallyEven) {
      initiallyEven = false;
      setProportionsEx(createProporions(null, getPaneCount()));
    }

    if ((minimumSizePosition > -1) &&!dragging) {
      adjustForMinimumSizeComponent(width, height);
    } else if (autoAdjustProportions &&!dragging) {
      adjustProprotions(width, height);
    }

    if (leftToRight) {
      w -= dividerSizes;
    } else {
      h -= dividerSizes;
    }

    int   len     = getComponentCount();
    float props[] = tempPorpotions;
    int   n       = 0;
    int   j       = 0;
    int   pos     = leftToRight
                    ? left
                    : top;
    int   end     = props.length - 1;

    for (int i = 0; i < len; i++) {
      iPlatformComponent v = getComponentAt(i);

      if (v instanceof Divider) {
        if (v.isVisible()) {
          if (leftToRight) {
            v.setBounds(pos, top, dividerSize, height);
            pos += dividerSize;
          } else {
            v.setBounds(left, pos, width, dividerSize);
            pos += dividerSize;
          }
        }

        continue;
      }

      if (!v.isVisible()) {
        j++;

        continue;
      }

      if (leftToRight) {
        if (j == end) {
          v.setBounds(pos, top, width - pos, height);

          break;
        } else {
          iPlatformComponent vv = null;

          if (dragging && (v == afterView) && (vv = getNextDivider(i + 1, len)) != null) {
            n = vv.getX() - pos;
            j++;
          } else {
            n = (int) Math.ceil(props[j++] * w);
          }

          v.setBounds(pos, top, n, height);
          pos += n;
        }
      } else {
        if (j == end) {
          v.setBounds(left, pos, width, height - pos);

          break;
        } else {
          iPlatformComponent vv = null;

          if (dragging && (v == afterView) && (vv = getNextDivider(i + 1, len)) != null) {
            n = vv.getY() - pos;
            j++;
          } else {
            n = (int) Math.ceil(props[j++] * h);
          }

          v.setBounds(left, pos, width, n);
          pos += n;
        }
      }
    }
  }

  protected boolean resizeViaDivider(Divider d, float delta) {
    if (delta == 0) {
      return false;
    }

    UIInsets           in      = getInsets(computeInsets);
    int                before  = beforeViewIndex;
    int                after   = afterViewIndex;
    iPlatformComponent beforev = beforeView;
    iPlatformComponent afterv  = afterView;

    if ((beforev == null) || (afterv == null)) {
      return false;
    }

    float       size, mw;
    UIDimension bsize = beforev.getMinimumSize();
    UIDimension asize = afterv.getMinimumSize();

    if (leftToRight) {
      mw   = (delta < 0)
             ? bsize.width
             : asize.width;
      size = getWidth() - in.left - in.right - dividerSizes;
    } else {
      mw   = (delta < 0)
             ? bsize.height
             : asize.height;
      size = getHeight() - in.top - in.bottom - dividerSizes;
    }

    float w;
    float props[] = tempPorpotions;

    if (delta < 0) {
      w = beforeViewSize + delta;

      if (w < mw) {
        w = mw;
      }

      if (SNumber.isEqual(w, beforeViewSize)) {
        return false;
      }

      props[before] = w / size;
      delta         = w - beforeViewSize;
      w             = afterViewSize - delta;
      props[after]  = w / size;
    } else {
      w = afterViewSize - delta;

      if (w < mw) {
        w = mw;
      }

      if (SNumber.isEqual(w, afterViewSize)) {
        return false;
      }

      delta         = afterViewSize - w;
      props[after]  = w / size;
      w             = beforeViewSize + delta;
      props[before] = w / size;
    }

    if (allPanesVisible()) {
      System.arraycopy(props, 0, splitPorpotions, 0, props.length);
    }

    minimumSizePosition = -1;

    return true;
  }

  protected void setupDrag(Divider d) {
    beforeView = null;
    afterView  = null;

    iPlatformComponent lv      = null;
    int                j       = -1;
    iPlatformComponent beforev = null;
    iPlatformComponent afterv  = null;
    int                before  = 0;
    int                after   = 0;
    int                len     = getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent v = getComponentAt(i);

      if (v == d) {
        beforev = lv;
        before  = j;

        continue;
      }

      if (v instanceof Divider) {
        continue;
      }

      lv = v;
      j++;

      if (beforev != null) {
        afterv = lv;
        after  = j;

        break;
      }
    }

    d.draggingStarted();

    if ((beforev == null) || (afterv == null)) {
      return;
    }

    beforeViewIndex = before;
    beforeView      = beforev;
    afterViewIndex  = after;
    afterView       = afterv;
    beforeViewSize  = leftToRight
                      ? beforev.getWidth()
                      : beforev.getHeight();
    afterViewSize   = leftToRight
                      ? afterv.getWidth()
                      : afterv.getHeight();
  }

  protected void setLeftToRightSplitEx(boolean leftToRight) {
    if (this.leftToRight != leftToRight) {
      this.leftToRight = leftToRight;

      if (dividerBorder != null) {
        if (leftToRight) {
          dividerBorder.setInsets(0, 1, 0, 1);
        } else {
          dividerBorder.setInsets(1, 0, 1, 0);
        }
      }

      int len = getComponentCount();

      for (int i = 0; i < len; i++) {
        iPlatformComponent node = getComponentAt(i);

        if (node instanceof Divider) {
          ((Divider) node).setType(leftToRight);
        }
      }

      revalidate();
    }
  }

  protected void setProportionsEx(float[] props) {
    tempPorpotions          = new float[props.length];
    splitPorpotions         = new float[props.length];
    originalSplitPorpotions = new float[props.length];
    System.arraycopy(props, 0, originalSplitPorpotions, 0, props.length);
    System.arraycopy(props, 0, tempPorpotions, 0, props.length);
    System.arraycopy(props, 0, splitPorpotions, 0, props.length);
  }

  protected UIColor getDragColor(boolean always) {
    UIColor c = Platform.getUIDefaults().getColor("Rare.SplitPane.dragColor");

    if ((c == null) && always) {
      c = ColorUtils.getBackground();

      if (c.isDarkColor()) {
        c = c.light(50);
      } else {
        c = c.light(-50);
      }

      c = c.alpha(128);
    }

    return c;
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (maxWidth > 0) {
      getSizeForWidth(size, maxWidth);
    } else {
      float width  = 0;
      float height = 0;
      int   len    = getComponentCount();

      for (int i = 0; i < len; i++) {
        getComponentAt(i).getPreferredSize(size);

        if (leftToRight) {
          width  += size.width + dividerSize;
          height = Math.max(height, size.height);
        } else {
          height += size.height + dividerSize;
          width  = Math.max(width, size.width);
        }

        size.width  = width;
        size.height = height;
      }
    }
  }

  protected void getSizeForWidth(UIDimension size, float maxWidth) {
    UIInsets insets    = getInsets(computeInsets);
    float    width     = maxWidth - insets.left - insets.right;
    float    maxHeight = insets.top + insets.bottom;

    if (leftToRight) {
      width -= dividerSizes;
    }

    if ((tempPorpotions == null) || initiallyEven) {
      initiallyEven = false;
      setProportionsEx(createProporions(null, getPaneCount()));
    }

    int   len     = getComponentCount();
    float props[] = tempPorpotions;
    float n       = 0;
    int   j       = 0;

    for (int i = 0; i < len; i++) {
      iPlatformComponent v = getComponentAt(i);

      if (v instanceof Divider) {
        if (!leftToRight && v.isVisible()) {
          maxHeight += dividerSize;
        }

        continue;
      }

      if (!v.isVisible()) {
        j++;

        continue;
      }

      if (leftToRight) {
        n = (props[j++] * width);
        v.getPreferredSize(size, (int) n);
        maxHeight = Math.max(maxHeight, size.height);
      } else {
        v.getPreferredSize(size, width);
        maxHeight += size.height;
      }
    }
  }

  /**
   * Expects to be called when there are no dividers
   */
  private void addDividers() {
    if (dividerSize < 1) {
      return;
    }

    int len = getComponentCount() - 1;

    dividerSizes = 0;

    while(len-- > 1) {
      dividerSizes += dividerSize;
      super.add(createDivider(), null, -1);
    }
  }

  private void adjustForMinimumSizeComponent(float width, float height) {
    float availableSize;
    float size;

    if (minimumSizePositionSize.width == 0) {
      calculateMinimumSizePositionSize();
    }

    if (leftToRight) {
      availableSize = width - dividerSizes;
      size          = minimumSizePositionSize.width;
    } else {
      availableSize = height - dividerSizes;
      size          = minimumSizePositionSize.height;
    }

    int   pos     = minimumSizePosition;
    float props[] = tempPorpotions;
    int   plen    = props.length;

    if (size > availableSize) {
      size = availableSize;
    }

    props[pos]    = size / availableSize;
    availableSize -= size;

    float fraction = (1 - props[pos]) / (plen - 1);

    for (int i = 0; i < plen; i++) {
      if (i != pos) {
        props[i] = fraction;
      }
    }
  }

  protected void calculateMinimumSizePositionSize() {
    int len  = getComponentCount();
    int cpos = 0;

    for (int i = 0; i < len; i++) {
      iPlatformComponent c = getComponentAt(i);

      if (!(c instanceof Divider)) {
        if (minimumSizePosition == cpos) {
          c.getMinimumSize(minimumSizePositionSize);

          break;
        }

        cpos++;
      }
    }
  }

  private void adjustProprotions(float width, float height) {
    float availableSize;

    if (leftToRight) {
      availableSize = width - dividerSizes;
    } else {
      availableSize = height - dividerSizes;
    }

    int         len       = getComponentCount();
    float       props[]   = tempPorpotions;
    int         plen      = tempPorpotions.length;
    float       sizes[]   = new float[plen];
    float       nsizes[]  = new float[plen];
    UIDimension size      = new UIDimension();
    int         totalSize = 0;
    int         j         = 0;

    for (int i = 0; i < len; i++) {
      iPlatformComponent v = getComponentAt(i);

      if (!(v instanceof Divider) && v.isVisible()) {
        v.getMinimumSize(size);
        sizes[j]  = leftToRight
                    ? size.width
                    : size.height;
        totalSize += sizes[j];
        j++;
      }
    }

    if (totalSize > availableSize) {
      return;
    }

    float   leftOver = availableSize - totalSize;
    float   dx;
    int     next     = plen;
    boolean foundOne = false;

    for (int i = 0; i < plen; i++) {
      nsizes[i] = props[i] * availableSize;

      if (nsizes[i] < sizes[i]) {
        foundOne  = true;
        dx        = sizes[i] - nsizes[i];
        nsizes[i] = sizes[i];
        leftOver  -= dx;

        if (leftOver < 0) {
          next = i + 1;

          break;
        }
      }
    }

    if (!foundOne) {
      return;
    }

    if (next < plen) {
      j  = plen - next;
      dx = leftOver / j;

      for (int i = next; i < plen; i++) {
        nsizes[i] += dx;
      }
    }

    for (int i = 0; i < plen; i++) {
      props[i] = nsizes[i] / availableSize;
    }
  }

  private boolean allPanesVisible() {
    int len = getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent v = getComponentAt(i);

      if (!(v instanceof Divider) &&!v.isVisible()) {
        return false;
      }
    }

    return true;
  }

  private iPlatformComponent getNextDivider(int pos, int len) {
    for (int i = pos; i < len; i++) {
      iPlatformComponent n = getComponentAt(i);

      if ((n instanceof Divider) && n.isVisible()) {
        return n;
      }
    }

    return null;
  }

  public boolean isAutoOrient() {
    return autoOrient;
  }

  public void setAutoOrient(boolean autoOrient) {
    this.autoOrient = autoOrient;
  }

  protected class Divider extends XPComponent {
    public Divider(Object view) {
      super(view);
      setType(leftToRight);
      setComponentPainter(dividerPainter);
    }

    public void draggingStarted() {
      if (paintDividerDragAllways ||!continuousLayout) {
        setComponentPainter(dividerDragPainter);
        repaint();
      }
    }

    public void draggingStopped() {
      if (paintDividerDragAllways ||!continuousLayout) {
        setComponentPainter(dividerPainter);
        repaint();
      }
    }

    public void setType(boolean horizontal) {
      if (horizontal) {
        setCursor(UICursor.getCursor("COL-RESIZE"));
      } else {
        setCursor(UICursor.getCursor("ROW-RESIZE"));
      }
    }

    public boolean isManaged() {
      return true;
    }
  }


  protected class SplitPaneGripperIcon extends GripperIcon {
    public SplitPaneGripperIcon() {
      super(leftToRight);
    }

    @Override
    public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
      vertical = leftToRight;
      super.paint(g, x, y, width, height);
    }
  }
}
