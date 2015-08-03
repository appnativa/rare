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

/*-[
 #import "AppleHelper.h"
 ]-*/
import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.util.ImageUtils;
import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.carousel.aCarouselPanel;
import com.appnativa.rare.ui.effects.iAnimator;
import com.appnativa.rare.ui.renderer.UILabelRenderer;

import com.google.j2objc.annotations.Weak;
import com.google.j2objc.annotations.WeakOuter;

public class CarouselPanel extends aCarouselPanel {
  public CarouselPanel(DataType dataType) {
    super(dataType);
    setView(new CarouselView());
  }

  public void valueChanged(iAnimator animator, double value) {
    int val = (int) value;

    if (val != getSelectedIndex()) {
      updateContent(val);
    }
  }

  @Override
  protected void clearComponentContent(iPlatformComponent c) {
    c.putClientProperty(RARE_CAROUSEL_ITEM, null);
    ((ContentView) c.getView()).clearContent();
  }

  @Override
  public boolean isAnimating() {
    return false;
  }

  native Object createPerspectiveTransform(float x, float y, float width, float height, float ulx, float uly,
          float urx, float ury, float lrx, float lry, float llx, float lly)
  /*-[
   CATransform3D tx=[AppleHelper rectToQuadWithX: x Y: y width:width height:height quadTLX:ulx quadTLY:uly quadTRX:urx quadTRY:ury quadBLX:llx quadBLY:lly quadBRX:lrx quadBRY:lry];
   float angle=30.0 * M_PI / 180.0f;
   float f;
   if(ury==0) {
   angle*=-1;
   f=(lly/height);
   }
   else {
   f=(lry/height);
   }
   tx.m34 = f/-500 ;
   //  tx = CATransform3DRotate(tx, angle, 0.0f, 1.0f, 0.0f);
   //  tx = CATransform3DTranslate(tx, 0.0f, -height/4, 0.0f);
   return [NSValue value:&tx withObjCType:@encode(CATransform3D)];
   ]-*/
  ;

  @Override
  protected void bringToFrontOrClip(iPlatformComponent c, float clipWidth, boolean leftSide) {
    c.getView().bringToFront();
  }

  @Override
  protected void clearPerspectiveFilter(iPlatformComponent c) {
    ((ContentView) c.getView()).set3DTransform(null);
  }

  @Override
  protected void setComposite(iPlatformComponent component, GraphicsComposite composite) {
    float alpha = (composite == null)
                  ? 1f
                  : composite.getAlpha();

    ((ContentView) component.getView()).setAlpha(alpha);
  }

  @Override
  protected UIImage createImageFromComponent(iPlatformComponent c) {
    return ImageUtils.createImage(c);
  }

  @Override
  protected iPlatformComponent createLayoutComponent() {
    ContentView p;

    if ((imageCreator != null) || usesImagesAlways) {
      p = new ContentView(preserveAspectRatio, true, reflectionFraction, reflectionOpacity);
    } else {
      switch(dataType) {
        case WIDGET_URLS :
          p = new ContentView(reflectionFraction, reflectionOpacity);

          break;

        case IMAGE_URLS :
          p = new ContentView(preserveAspectRatio, true, reflectionFraction, reflectionOpacity);

          break;

        default :
          p = new ContentView(new UILabelRenderer(), reflectionFraction, reflectionOpacity);

          break;
      }
    }

    Component c = new Component(p);

    return c;
  }

  @Override
  protected void renderComponentContent(iPlatformComponent dst, RenderableDataItem src) {
    boolean sel = dst == centerComponent;

    itemRenderer.configureRenderingComponent(this, ((ContentView) dst.getView()).renderer, src, 0, sel, sel, null,
            null);
  }

  @Override
  protected void updateComponentContent(iPlatformComponent dst, iPlatformComponent src, CharSequence title) {
    ((ContentView) dst.getView()).setContentView(src.getView(), title);
  }

  @Override
  protected void updateComponentContent(iPlatformComponent dst, UIImage src, CharSequence title) {
    ((ContentView) dst.getView()).setImage(src, title);
  }

  @Override
  protected void setPerspectiveFilter(iPlatformComponent c, float width, float height, boolean left, int pos) {
    float f               = perspectiveFraction + (pos * .01f);
    float perspectiveDiff = f * height;
    float x               = 0;
    float y               = height;

    height *= 2;
    width  *= 2;

    Object tx;

    if (left) {
      tx = createPerspectiveTransform(x, y, width, height, 0, 0, width, perspectiveDiff, width,
                                      height - perspectiveDiff, 0, height);
    } else {
      tx = createPerspectiveTransform(x, y, width, height, 0, perspectiveDiff, width, 0, width, height, 0,
                                      height - perspectiveDiff);
    }

    ((ContentView) c.getView()).set3DTransform(tx);
  }

  @WeakOuter
  class CarouselView extends ParentView implements iAppleLayoutManager {
    public CarouselView() {
      super(createAPView());
      setLayoutManager(this);
    }

    @Override
    public void layout(ParentView view, float width, float height) {
      //
      CarouselPanel.this.layout(0, 0, width, height);

      int i = (adjustable != null)
              ? (int) adjustable.getValue()
              : 0;

      updateContent(Math.max(i, 0));
    }
  }


  static class ContentView extends ParentView implements iAppleLayoutManager {
    View                contentView;
    ImagePanel          imageView;
    ReflectionViewGroup reflectionView;
    UILabelRenderer     renderer;
    LabelView           titleLabel;

    public ContentView(float rfraction, float ropacity) {
      super(createAPView());
      setLayoutManager(this);

      if (rfraction > 0) {
        reflectionView = new ReflectionViewGroup(rfraction, ropacity);
        add(-1, reflectionView);
      }
    }

    public ContentView(UILabelRenderer renderer, float rfraction, float ropacity) {
      this(rfraction, ropacity);
      this.renderer = renderer;
      addChild(renderer.getView());
    }

    public ContentView(boolean preserveAspectRatio, boolean autoScale, float rfraction, float ropacity) {
      this(0, 0);
      imageView = new ImagePanel(true);
      imageView.setPreserveAspectRatio(preserveAspectRatio);
      imageView.setFillViewport(true);
      imageView.setAutoScale(autoScale);
      addChild(contentView = imageView.getView());
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

    @Override
    public void layout(ParentView view, float width, float height) {
      iPlatformBorder b  = view.getComponent().getBorder();
      UIInsets        in = (b == null)
                           ? null
                           : b.getBorderInsets(null);
      View            v  = (reflectionView == null)
                           ? contentView
                           : reflectionView;

      if ((v == null) ||!v.isVisible()) {
        return;
      }

      float theight = 0;
      float pl      = (in == null)
                      ? 0
                      : in.left;
      float pr      = (in == null)
                      ? 0
                      : in.right;
      float pt      = (in == null)
                      ? 0
                      : in.top;
      float pb      = (in == null)
                      ? 0
                      : in.bottom;

      width  -= (pr + pl);
      height -= (pt + pb);

      if ((titleLabel != null) && (titleLabel.isVisible())) {
        UIDimension size = new UIDimension();

        titleLabel.getPreferredSize(size, 0);
        theight = size.height;
      }

      v.getComponent().setBounds(pl, pt, (int) width, (int) height - theight);

      if ((titleLabel != null) &&!titleLabel.isVisible()) {
        titleLabel.setBounds(pl, (int) height - theight, (int) width, theight);
      }
    }

    public void setContentView(View view, CharSequence title) {
      setTitle(title);

      if (contentView == view) {
        return;
      }

      if (contentView != null) {
        removeView(contentView);
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
          titleLabel.setVisible(false);
          ;
        }
      } else {
        if (titleLabel == null) {
          createTitleLabel();
        }

        titleLabel.setText(title.toString());
        titleLabel.setVisible(true);
      }
    }

    void addChild(View view) {
      if (reflectionView != null) {
        reflectionView.setSourceView(view);
      } else {
        add(-1, view);
      }
    }

    void removeView(View view) {
      if (reflectionView != null) {
        reflectionView.setSourceView(null);
      } else {
        removeChild(view);
      }
    }

    @Override
    protected void disposeEx() {
      if (imageView != null) {
        imageView.dispose();
      }

      if (reflectionView != null) {
        reflectionView.dispose();
      }

      if (titleLabel != null) {
        titleLabel.dispose();
      }

      if (renderer != null) {
        renderer.dispose();
      }

      imageView   = null;
      contentView = null;
      titleLabel  = null;
      renderer    = null;
      super.disposeEx();
    }

    private void createTitleLabel() {
      titleLabel = new LabelView();
      add(-1, titleLabel);
    }
  }


  static class ReflectionView extends View {
    UIImage      reflection;
    float        rfraction;
    float        ropacity;
    UIImage      source;
    @Weak
    private View sourceView;

    public ReflectionView(float rfraction, float ropacity) {
      super(createAPView());
      this.rfraction = rfraction;
      this.ropacity  = ropacity;
    }

    public void setSourceView(View sourceView) {
      this.sourceView = sourceView;
    }

    @Override
    public void getPreferredSize(UIDimension size, float maxWidth) {
      if (sourceView == null) {
        size.setSize(0, 0);
      } else {
        sourceView.getPreferredSize(size, maxWidth);
        size.height *= rfraction;
      }
    }

    public View getSourceView() {
      return sourceView;
    }

    @Override
    protected void disposeEx() {
      source     = null;
      sourceView = null;
      reflection = null;
      super.disposeEx();
    }
  }


  static class ReflectionViewGroup extends ParentView implements iAppleLayoutManager {
    ReflectionView reflection;

    public ReflectionViewGroup(float rfraction, float ropacity) {
      super(createAPView());
      reflection = new ReflectionView(rfraction, ropacity);
      add(-1, reflection);
    }

    @Override
    public void layout(ParentView view, float width, float height) {
      View v = reflection.getSourceView();

      if ((v == null) ||!v.isVisible()) {
        return;
      }

      int rheight = (int) (reflection.rfraction * height);

      v.setBounds(0, 0, width, height - rheight);
      reflection.setBounds(0, height - rheight, width, rheight);
    }

    public void setSourceView(View view) {
      if (reflection.getSourceView() != null) {
        removeChild(reflection.getSourceView());
      }

      reflection.setSourceView(view);

      if (view != null) {
        add(-1, view);
      }

      this.revalidate();
    }

    @Override
    protected void disposeEx() {
      if (reflection != null) {
        reflection.dispose();
      }

      reflection = null;
      super.disposeEx();
    }
  }
}
