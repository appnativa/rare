/*
 * @(#)ImagePanel.java   2012-02-23
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.Timer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.ui.effects.ValueRangeAnimator;
import com.appnativa.rare.ui.effects.aAnimator.BoundsChanger;
import com.appnativa.rare.ui.effects.aTransformFilter;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.widget.iWidget;

public class ImagePanel extends aImagePanel {
  MouseHandler             mouseHandler;
  UIImage                  transformedImage;
  private aTransformFilter transformFilter;
  long                     lastPinchZoom;
  Timer                    timer;

  public ImagePanel(boolean useTransforms) {
    super(new ImageView());
    ((ImageView) view).panel = this;
  }

  public ImagePanel(iWidget context) {
    this(false);
  }

  @Override
  public void setMovingAllowed(boolean allowed) {
    super.setMovingAllowed(allowed);

    if (allowed) {
      setupMouseHandler();
    }
  }

  @Override
  protected void handleSizeChangeAnimation(final BoundsChanger bc) {
    ValueRangeAnimator a = new ValueRangeAnimator(0f, 1f);
    a.addValueListener(new iAnimatorValueListener() {

      @Override
      public void valueChanged(iPlatformAnimator animator, float value) {
        destBounds.x = bc.getX(value);
        destBounds.y = bc.getY(value);
        destBounds.width = bc.getWidth(value);
        destBounds.height = bc.getHeight(value);
        repaint();
      }
    });
    a.addListener(new iAnimatorListener() {

      @Override
      public void animationStarted(iPlatformAnimator animator) {
        if (animateSizeChangeListener != null) {
          animateSizeChangeListener.animationStarted(animator);
        }
      }

      @Override
      public void animationEnded(iPlatformAnimator animator) {
        destBounds.setBounds(bc.to);
        animatingSizeChange = false;
        if (animateSizeChangeListener != null) {
          animateSizeChangeListener.animationEnded(animator);
        }
      }
    });
    destBounds.setBounds(bc.from);
    animatingSizeChange = true;
    a.start();
  }

  public void setTransformFilter(aTransformFilter transformFilter) {
    this.transformFilter = transformFilter;
  }

  @Override
  public void setMovingOnlyAllowedWhenToLarge(boolean allowed) {
    super.setMovingOnlyAllowedWhenToLarge(allowed);
    if (allowed) {
      setupMouseHandler();
    }
  }

  @Override
  public void setZoomingAllowed(boolean allowed) {
    super.setZoomingAllowed(allowed);
    if (allowed) {
      setupMouseHandler();
    }
  }

  public aTransformFilter getTransformFilter() {
    return transformFilter;
  }

  @Override
  protected void addSpinnerComponent(iPlatformComponent c) {
    ((ImageView) view).add(c.getView());
  }

 @Override
  protected void imageLoaded() {
    repaint();
    transformedImage = null;
  }

  @Override
  protected void removeSpinnerComponent(iPlatformComponent c) {
    ((ImageView) view).remove(c.getView());
  }

  @Override
  protected void updateTransforms() {
    if (center) {
      adjustDestForAspectRatio(getWidth(), getHeight(), false, true);
      center = false;
    }
  }

  @Override
  protected UIImage getPaintImage() {
    if (transformFilter != null) {
      if (transformedImage == null) {
        transformedImage = transformFilter.filter(theImage);
      }

      return transformedImage;
    }

    return super.getPaintImage();
  }

  void setupMouseHandler() {
    if (mouseHandler == null) {
      view.addMouseListener(mouseHandler = new MouseHandler());
      view.addMouseMotionListener(mouseHandler);
      view.addMouseWheelListener(mouseHandler);
    }

  }

  static class ImageView extends JPanelEx {
    ImagePanel panel;

    public ImageView() {
      super();
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      panel.paint(graphics, 0, 0, getWidth(), getHeight());
    }
  }

  class MouseHandler extends MouseAdapter implements MouseWheelListener {
    float           lastX;
    float           lastY;
    float           startX;
    float           startY;
    protected float slop = Platform.isTouchDevice() ? 10 : 4;
    UIPoint         pt   = new UIPoint();

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

      if ((e.getClickCount() > 1) && zoomingAllowed) {
        setMousePoint(e);
        zoomOnPoint(pt.x, pt.y);
      }
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
      if (movingAllowed || movingAllowedWhenToLarge) {
        if (!movingAllowed && movingAllowedWhenToLarge && isImageFitted()) {
          return;
        }
        float lx = lastX;
        float ly = lastY;

        setMousePoint(e);
        lastY = pt.y;
        lastX = pt.x;

        if ((Math.abs(startX - lastX) < slop) && (Math.abs(startY - lastY) < slop)) {
          lastX = lx;
          lastY = ly;

          return;
        }

        lx = lastX - lx;
        ly = lastY - ly;

        if ((lx != 0) || (ly != 0)) {
          moveImage(lx, ly);
        }
      }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
      setMousePoint(e);
      startY = lastY = pt.y;
      startX = lastX = pt.x;
    }

    void setMousePoint(java.awt.event.MouseEvent e) {
      pt.x = e.getX();
      pt.y = e.getY();
      if (rotation != 0) {
        Utils.transformPoint(pt, rotation, getWidth(), getHeight());
      }
    }

    void checkPinchZoom() {
      if (lastPinchZoom + timer.getDelay() < System.currentTimeMillis()) {
        timer.stop();
        firePropertyChange(PROPERTY_PINCHZOOM, Boolean.TRUE, Boolean.FALSE);
      }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      center = false;
      if (timer == null) {
        int timeout = Platform.getUIDefaults().getInt("Rare.Pointer.mouseWheelTimeout", 300);
        timer = new Timer(timeout, new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            checkPinchZoom();
          }
        });
      }
      if (!timer.isRunning()) {
        firePropertyChange(PROPERTY_PINCHZOOM, Boolean.FALSE, Boolean.TRUE);
        timer.start();
      }
      lastPinchZoom = System.currentTimeMillis();
      float scroll = -e.getWheelRotation();

      float scale = theScale + (scroll / 100);
      setScale(scale, true);
    }
  }
}
