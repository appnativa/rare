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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import android.view.View;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.platform.android.ui.view.ViewEx;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.carousel.aCarouselPanel;
import com.appnativa.rare.ui.effects.ValueRangeAnimator;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.util.IdentityArrayList;

@SuppressLint("NewApi")
public class CarouselPanel extends aCarouselPanel implements iAnimatorValueListener {
  IdentityArrayList<iPlatformComponent> components = new IdentityArrayList<iPlatformComponent>();
  ValueRangeAnimator                    animator;

  public CarouselPanel(DataType dataType, Context context) {
    super(dataType);
    setView(new CarouselView(context));
  }

  public void add(iPlatformComponent c, Object constraints, int position) {
    components.addIfNotPresent(c);

    final View v = (View) c.getView();

    if (v.getParent() == view) {
      return;
    }

    ((CarouselView) view).addChieldEx(v);
  }

  public void animateSelect(int index) {
    if (index == -1) {
      index = 0;
    }

    if ((animator != null) && (index != getSelectedIndex())) {
      animator.stop();
      animator.setRange(getSelectedIndex(), index);
      animator.start();
    } else {
      updateContent(index);
    }
  }

  public void onClick(View v) {
    iPlatformComponent c = Component.fromView(v);

    if (c != null) {
      Integer index = (Integer) c.getClientProperty(RARE_CAROUSEL_ITEM);

      if ((index != null) && (index != getSelectedIndex())) {
        animateSelect(index);
      }
    }
  }

  public void remove(iPlatformComponent c) {
    if (c != null) {
      components.remove(c);
      ((CarouselView) view).removeViewInLayout(c.getView());
    }
  }

  public void removeAll() {
    super.removeAll();
    components.clear();
  }

  public void stopAnimation() {
    if (animator != null) {
      animator.stop();
    }
  }

  public void valueChanged(iPlatformAnimator animator, float value) {
    int val = (int) value;

    if (val != getSelectedIndex()) {
      updateContent(val);
    }
  }

  public iPlatformComponent getComponentAt(int index) {
    return components.get(index);
  }

  public int getComponentCount() {
    return components.size();
  }

  public boolean isAnimating() {
    if (animator == null) {
      return false;
    }

    return animator.isRunning();
  }

  float[] createPerspectiveFloats(float ulx, float uly, float urx, float ury, float lrx, float lry, float llx,
                                  float lly) {
    return new float[] {
      ulx, uly, urx, ury, lrx, lry, llx, lly
    };
  }

  protected void bringToFrontOrClip(iPlatformComponent c, float clipWidth, boolean leftSide) {
    c.getView().bringToFront();
  }

  protected UIImage createImageFromComponent(iPlatformComponent c) {
    return ImageUtils.createImage(c);
  }

  protected iPlatformComponent createLayoutComponent() {
    ContentView p;
    Context     ctx = getView().getContext();

    if ((imageCreator != null) || usesImagesAlways) {
      p = new ContentView(ctx, preserveAspectRatio, true, reflectionFraction, reflectionOpacity);
    } else {
      switch(dataType) {
        case WIDGET_URLS :
          p = new ContentView(ctx, reflectionFraction, reflectionOpacity);

          break;

        case IMAGE_URLS :
          p = new ContentView(ctx, preserveAspectRatio, true, reflectionFraction, reflectionOpacity);

          break;

        default :
          p = new ContentView(ctx, new UILabelRenderer(ctx), reflectionFraction, reflectionOpacity);

          break;
      }
    }

    Component c = new Component(p);

    return c;
  }

  protected void renderComponentContent(iPlatformComponent dst, RenderableDataItem src) {
    boolean sel = dst == centerComponent;

    itemRenderer.configureRenderingComponent(this, ((ContentView) dst.getView()).renderer, src, 0, sel, sel, null,
            null);
  }

  @Override
  protected void clearComponentContent(iPlatformComponent c) {
    c.putClientProperty(RARE_CAROUSEL_ITEM, null);
    ((ContentView) c.getView()).clearContent();
  }

  protected void updateComponentContent(iPlatformComponent dst, iPlatformComponent src, CharSequence title) {
    ((ContentView) dst.getView()).setContentView(src.getView(), title);
  }

  protected void updateComponentContent(iPlatformComponent dst, UIImage src, CharSequence title) {
    ((ContentView) dst.getView()).setImage(src, title);
  }

  protected void setPerspectiveFilter(iPlatformComponent c, float width, float height, boolean left, int pos) {
    Matrix  matrix          = new Matrix();
    float[] src             = createPerspectiveFloats(0, 0, width, 0, width, height, 0, height);
    float   f               = perspectiveFraction + (pos * .01f);
    float   perspectiveDiff = f * height;
    float[] dst;

    if (left) {
      dst = createPerspectiveFloats(0, 0, width, perspectiveDiff, width, height - perspectiveDiff, 0, height);
    } else {
      dst = createPerspectiveFloats(0, perspectiveDiff, width, 0, width, height, 0, height - perspectiveDiff);
    }

    matrix.setPolyToPoly(src, 0, dst, 0, 4);
    ((ContentView) c.getView()).setMatrixEx(matrix);
  }

  class CarouselView extends ViewGroupEx {
    public CarouselView(Context context) {
      super(context);
      setMeasureType(MeasureType.HORIZONTAL);
    }

    public void addChieldEx(View child) {
      this.addViewInLayout(child, -1, generateDefaultLayoutParams(), true);
    }

    @Override
    public void bringChildToFront(View child) {
      blockRequestLayout = true;
      super.bringChildToFront(child);
      blockRequestLayout = false;
    }

    @Override
    public void bringToFront() {
      blockRequestLayout = true;
      super.bringToFront();
      blockRequestLayout = false;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
      blockRequestLayout = true;
      CarouselPanel.this.layout(0, 0, r - l, b - t);
      blockRequestLayout = false;

      int i = (adjustable != null)
              ? (int) adjustable.getValue()
              : 0;

      updateContent(Math.max(i, 0));
    }
  }


  static class ContentView extends ViewGroupEx {
    View                contentView;
    ImagePanel          imageView;
    ReflectionViewGroup reflectionView;
    UILabelRenderer     renderer;
    LabelView           titleLabel;

    public ContentView(Context ctx, float rfraction, float ropacity) {
      super(ctx);

      if (rfraction > 0) {
        ReflectionViewGroup view = new ReflectionViewGroup(ctx, rfraction, ropacity);

        addChild(view);
        reflectionView = view;
      }
    }

    public ContentView(Context ctx, UILabelRenderer renderer, float rfraction, float ropacity) {
      this(ctx, rfraction, ropacity);
      this.renderer = renderer;
      addChild(renderer.getView());
    }

    public ContentView(Context ctx, boolean preserveAspectRatio, boolean autoScale, float rfraction, float ropacity) {
      this(ctx, 0, 0);
      imageView = new ImagePanel(ctx);
      imageView.setPreserveAspectRatio(preserveAspectRatio);
      imageView.setFillViewport(true);
      imageView.setAutoScale(autoScale);
      addChild(imageView.getView());
      contentView = imageView.getView();
    }

    public void clearContent() {
      if (imageView != null) {
        imageView.setImage((UIImage) null);
      }

      if (renderer != null) {
        renderer.clearRenderer();
      }

      if (titleLabel != null) {
        titleLabel.setIcon(null);
        titleLabel.setText(null);
      }
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
    public void requestLayout() {}

    public void setContentView(View view, CharSequence title) {
      setTitle(title);

      if (contentView == view) {
        return;
      }

      if (contentView != null) {
        removeChild(contentView);
      }

      contentView = view;

      if (view != null) {
        addChild(view);
      }
    }

    public void setImage(UIImage image, CharSequence title) {
      setTitle(title);
      imageView.setImage(image);
    }

    public void setTitle(CharSequence title) {
      if (title == null) {
        if (titleLabel != null) {
          titleLabel.setVisibility(GONE);
          ;
        }
      } else {
        if (titleLabel == null) {
          createTitleLabel();
        }

        titleLabel.setText(title.toString());
        titleLabel.setVisibility(VISIBLE);
        ;
      }
    }

    void addChild(View view) {
      if (reflectionView != null) {
        reflectionView.setSourceView(view);
      } else {
        addViewInLayout(view, -1, generateDefaultLayoutParams(), true);
      }
    }

    void removeChild(View view) {
      if (reflectionView != null) {
        reflectionView.setSourceView(null);
      } else {
        removeViewInLayout(view);
      }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      View v = (reflectionView == null)
               ? contentView
               : reflectionView;

      if ((v == null) || (v.getVisibility() == View.GONE)) {
        return;
      }

      int theight = 0;
      int pl      = getPaddingLeft();
      int pr      = getPaddingRight();
      int pt      = getPaddingTop();
      int pb      = getPaddingBottom();

      right  -= pr;
      top    += pt;
      left   += pl;
      bottom -= pb;

      if ((titleLabel != null) && (titleLabel.getVisibility() != View.GONE)) {
        theight = titleLabel.getMeasuredHeight();
      }

      blockRequestLayout = true;

      iPlatformComponent c = Component.fromView(v);

      c.setBounds(pl, pt, right - left, bottom - top - theight);

      if ((titleLabel != null) && (titleLabel.getVisibility() != View.GONE)) {
        titleLabel.layout(pl, bottom - top - theight, right, bottom - top);
      }

      blockRequestLayout = false;
    }

    private void createTitleLabel() {
      titleLabel = new LabelView(getContext());
      addView(titleLabel);
    }
  }


  static class ReflectionView extends ViewEx {
    Bitmap       reflection;
    float        rfraction;
    float        ropacity;
    Bitmap       source;
    private View sourceView;

    public ReflectionView(Context context, float rfraction, float ropacity) {
      super(context);
      this.rfraction = rfraction;
      this.ropacity  = ropacity;
    }

    public void setSourceView(View sourceView) {
      this.sourceView = sourceView;
    }

    public View getSourceView() {
      return sourceView;
    }

    public int getSuggestedMinimumHeight() {
      if (sourceView == null) {
        return 0;
      }

      return (int) (sourceView.getMeasuredHeight() * rfraction);
    }

    public int getSuggestedMinimumWidth() {
      return (sourceView == null)
             ? 0
             : sourceView.getMeasuredWidth();
    }

    protected void onDraw(Canvas canvas) {
      if (sourceView == null) {
        return;
      }
    }
  }


  static class ReflectionViewGroup extends ViewGroupEx {
    ReflectionView reflection;

    public ReflectionViewGroup(Context context, float rfraction, float ropacity) {
      super(context);
      reflection = new ReflectionView(context, rfraction, ropacity);
      addView(reflection);
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

    public void setSourceView(View view) {
      if (reflection.getSourceView() != null) {
        removeViewInLayout(reflection.getSourceView());
      }

      reflection.setSourceView(view);

      if (view != null) {
        addViewInLayout(view, -1, generateDefaultLayoutParams(), true);
      }

      invalidate();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      View v = reflection.getSourceView();

      if ((v == null) || (v.getVisibility() == View.GONE)) {
        return;
      }

      int height  = bottom - top;
      int rheight = (int) (reflection.rfraction * height);

      v.layout(0, 0, right - left, bottom - top - rheight);
      reflection.layout(0, bottom - rheight, right - left, bottom - top);
    }
  }


  @Override
  protected void clearPerspectiveFilter(iPlatformComponent c) {
    ((ContentView) c.getView()).setMatrixEx(null);
  }

  @Override
  protected void setComposite(iPlatformComponent component, GraphicsComposite composite) {
    float alpha = (composite == null)
                  ? 1f
                  : composite.getAlpha();

    ((ContentView) component.getView()).setAlpha(alpha);
  }
}
