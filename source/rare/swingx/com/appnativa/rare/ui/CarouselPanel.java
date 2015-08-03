/*
 * @(#)CarouselPanel.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Composite;
import java.awt.Insets;

import javax.swing.JComponent;

import com.appnativa.rare.platform.swing.ui.UIProxyBorder;
import com.appnativa.rare.platform.swing.ui.util.ImageUtils;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.ui.carousel.aCarouselPanel;
import com.appnativa.rare.ui.effects.PerspectiveFilter;
import com.appnativa.rare.ui.effects.iAnimator;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.util.IdentityArrayList;

public class CarouselPanel extends aCarouselPanel {
  protected IdentityArrayList<iPlatformComponent> componentList = new IdentityArrayList<iPlatformComponent>();

  public CarouselPanel(DataType dataType) {
    super(dataType);
    setView(new CarouselView());
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    if (c != null) {
      componentList.add(c);
    }

    super.add(c, constraints, position);
  }

  @Override
  public void remove(iPlatformComponent c) {
    super.remove(c);

    if (c != null) {
      componentList.remove(c);
    }
  }

  @Override
  public void removeAll() {
    super.removeAll();
    componentList.clear();
  }

  @Override
  public void stopAnimation() {
  }

  public void valueChanged(iAnimator animator, double value) {
    int val = (int) value;

    if (val != getSelectedIndex()) {
      updateContent(val);
    }
  }

  @Override
  public iPlatformComponent getComponentAt(int index) {
    return componentList.get(index);
  }

  @Override
  public int getComponentCount() {
    return componentList.size();
  }

  @Override
  public boolean isAnimating() {
    return false;
  }

  PerspectiveFilter createPerspectiveTransform(float x, float y, float width, float height, float ulx, float uly, float urx,
      float ury, float lrx, float lry, float llx, float lly) {
    return new PerspectiveFilter(ulx, uly, urx, ury, lrx, lry, llx, lly);
  }

  @Override
  protected void animateMove(float dx) {
  }

  @Override
  protected void bringToFrontOrClip(iPlatformComponent c, float clipWidth, boolean leftSide) {
    JComponent comp = c.getView();

    comp.getParent().setComponentZOrder(comp, 0);
  }

  @Override
  protected void clearPerspectiveFilter(iPlatformComponent c) {
    ((ContentView) c.getView()).set3DTransform(null);
  }

  @Override
  protected UIImage createImageFromComponent(iPlatformComponent c) {
    return ImageUtils.createImage(c.getView());
  }

  @Override
  protected iPlatformComponent createLayoutComponent() {
    ContentView p;

    if (imageCreator != null || usesImagesAlways) {
      p = new ContentView(preserveAspectRatio, true, reflectionFraction, reflectionOpacity);
    } else {
      switch (dataType) {
        case WIDGET_URLS:
          p = new ContentView(reflectionFraction, reflectionOpacity);

          break;

        case IMAGE_URLS:
          p = new ContentView(preserveAspectRatio, true, reflectionFraction, reflectionOpacity);

          break;

        default:
          p = new ContentView(new UILabelRenderer(), reflectionFraction, reflectionOpacity);
          break;
      }
    }
    Component c = new Component(p);

    c.setComponentPainter(cellPainter);

    return c;
  }
  
  @Override
  protected void setComposite(iPlatformComponent component, GraphicsComposite composite) {
    Composite c=null;
    if(composite!=null) {
      c=(Composite) composite.getPlatformComposite();
      if(c==null) {
        c = SwingHelper.resolveInstance(composite);
      }
    }
    ((ContentView) component.getView()).setComposite(c);      
  }
  
  @Override
  protected void renderComponentContent(iPlatformComponent dst, RenderableDataItem src) {
    boolean sel = getDataIndex(dst) == selectedIndex;

    itemRenderer.configureRenderingComponent(this, ((ContentView) dst.getView()).renderer, src, 0, sel, sel, null, null);
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
  protected void clearComponentContent(iPlatformComponent c) {
     c.putClientProperty(RARE_CAROUSEL_ITEM, null);
     ((ContentView) c.getView()).clearContent();
  }
   
  @Override
  protected void setPerspectiveFilter(iPlatformComponent c, float width, float height, boolean left, int pos) {
    float f = perspectiveFraction + (pos * .01f);
    float perspectiveDiff = f * height;
    float x = 0;
    float y = height;

    height *= 2;
    width *= 2;

    PerspectiveFilter tx;

    if (left) {
      tx = createPerspectiveTransform(x, y, width, height, 0, 0, width, perspectiveDiff, width, height - perspectiveDiff, 0, height);
    } else {
      tx = createPerspectiveTransform(x, y, width, height, 0, perspectiveDiff, width, 0, width, height, 0, height - perspectiveDiff);
    }

    ((ContentView) c.getView()).set3DTransform(tx);
  }

  class CarouselView extends JPanelEx {
    public CarouselView() {
      super();
      super.setOpaque(false);
    }

    @Override
    public void setOpaque(boolean isOpaque) {
    }

    @Override
    public void getMinimumSize(UIDimension size) {
      CarouselPanel.this.getMinimumSize(size);
    }

    public void getPreferredSize(UIDimension size) {
      CarouselPanel.this.getPreferredSize(size);
    }

    @Override
    public boolean isOpaque() {
      return false;
    }

    int getSelectionFromView(java.awt.Component v) {
      while (v instanceof JComponent) {
        if (v instanceof ContentView) {
          iPlatformComponent c = Component.fromView((JComponent) v);
          Integer index = (Integer) c.getClientProperty(RARE_CAROUSEL_ITEM);

          return (index == null) ? -1 : index.intValue();
        }

        v = v.getParent();
      }

      return -1;
    }

    @Override
    protected void layoutContainerEx(int width, int height) {
      CarouselPanel.this.layout(0, 0, width, height);

      int i = (adjustable != null) ? (int) adjustable.getValue() : 0;
      updateContent(Math.max(i, 0));
    }
  }

  static class ContentView extends JPanelEx {
    JComponent          contentView;
    ImagePanel          imageView;
    ReflectionViewGroup reflectionView;
    UILabelRenderer     renderer;
    LabelView           titleLabel;

    public ContentView(float rfraction, float ropacity) {
      super();
      setOpaque(false);

      if (rfraction > 0) {
        reflectionView = new ReflectionViewGroup(rfraction, ropacity);
        add(reflectionView);
      }
    }

    public void clearContent() {
      if(imageView!=null) {
        imageView.setImage((UIImage)null);
      }
      if(renderer!=null) {
        renderer.clearRenderer();
      }
      if(titleLabel!=null) {
        titleLabel.setIcon(null);
        titleLabel.setText(null);
      }
    }

    public ContentView(UILabelRenderer renderer, float rfraction, float ropacity) {
      this(rfraction, ropacity);
      this.renderer = renderer;
      addChild(renderer.getView());
    }

    public ContentView(boolean preserveAspectRatio, boolean autoScale, float rfraction, float ropacity) {
      this(0, 0);
      imageView = new ImagePanel(false) {
        @Override
        public void repaint() {
          super.repaint();
          ContentView.this.repaint();
        }
      };
      imageView.setPreserveAspectRatio(preserveAspectRatio);
      imageView.setFillViewport(true);
      imageView.setAutoScale(autoScale);
      addChild(imageView.getView());
      contentView = imageView.getView();
    }

    public void set3DTransform(PerspectiveFilter tx) {
      if (imageView != null) {
      }

      imageView.setTransformFilter(tx);
    }

    public void setContentView(JComponent view, CharSequence title) {
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

      if (image != imageView.getImage()) {
        imageView.setImage(image);
        repaint();
        imageView.repaint();
      }
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

        String s = title.toString();

        if (!titleLabel.isVisible()) {
          titleLabel.setVisible(true);
        }

        if (!titleLabel.getText().equals(s)) {
          titleLabel.setText(s);
        }
      }
    }

    @Override
    public boolean isOpaque() {
      return false;
    }

    void addChild(JComponent view) {
      if (reflectionView != null) {
        reflectionView.setSourceView(view);
      } else {
        add(view);
      }
    }

    void removeView(JComponent view) {
      if (reflectionView != null) {
        reflectionView.setSourceView(null);
      } else {
        remove(view);
      }
    }

    @Override
    protected void layoutContainerEx(int width, int height) {
      iPlatformBorder b = UIProxyBorder.fromBorder(getBorder());
      Insets in = (b == null) ? null : b.getBorderInsets(this);
      JComponent v = (reflectionView == null) ? contentView : reflectionView;

      if ((v == null) || !v.isVisible()) {
        return;
      }

      int theight = 0;
      int pl = (in == null) ? 0 : in.left;
      int pr = (in == null) ? 0 : in.right;
      int pt = (in == null) ? 0 : in.top;
      int pb = (in == null) ? 0 : in.bottom;

      width -= (pr + pl);
      height -= (pt + pb);

      if ((titleLabel != null) && (titleLabel.isVisible())) {
        theight = titleLabel.getPreferredHeight(-1);
      }
      iPlatformComponent c = Component.fromView(v);

      c.setBounds(pl, pt, width, height - theight);

      if ((titleLabel != null) && titleLabel.isVisible()) {
        titleLabel.setBounds(pl, height - theight, width, theight);
      }
    }

    private void createTitleLabel() {
      titleLabel = new LabelView();
      add(titleLabel);
    }
  }

  static class ReflectionView extends JPanelEx {
    UIImage            reflection;
    float              rfraction;
    float              ropacity;
    UIImage            source;
    private JComponent sourceView;

    public ReflectionView(float rfraction, float ropacity) {
      super();
      this.rfraction = rfraction;
      this.ropacity = ropacity;
    }

    public void setSourceView(JComponent sourceView) {
      this.sourceView = sourceView;
    }

    public void getPreferredSize(UIDimension size) {
      if (sourceView == null) {
        size.setSize(0, 0);
      } else {
        SwingHelper.setUIDimension(size, sourceView.getPreferredSize());
        size.height *= rfraction;
      }
    }

    public JComponent getSourceView() {
      return sourceView;
    }

    @Override
    public boolean isOpaque() {
      return false;
    }
  }

  static class ReflectionViewGroup extends JPanelEx {
    ReflectionView reflection;

    public ReflectionViewGroup(float rfraction, float ropacity) {
      super();
      reflection = new ReflectionView(rfraction, ropacity);
      add(reflection);
    }

    public void setSourceView(JComponent view) {
      if (reflection.getSourceView() != null) {
        remove(reflection.getSourceView());
      }

      reflection.setSourceView(view);

      if (view != null) {
        add(view);
      }

      this.revalidate();
    }

    @Override
    public boolean isOpaque() {
      return false;
    }

    @Override
    protected void layoutContainerEx(int width, int height) {
      JComponent v = reflection.getSourceView();

      if ((v == null) || !v.isVisible()) {
        return;
      }

      int rheight = (int) (reflection.rfraction * height);

      v.setBounds(0, 0, width, height - rheight);
      reflection.setBounds(0, height - rheight, width, rheight);
    }
  }
}
