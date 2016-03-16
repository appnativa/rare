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
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.platform.apple.ui.view.ImageView;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.effects.SizeAnimation;
import com.appnativa.rare.ui.effects.aAnimator.BoundsChanger;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.ScaleGestureObject;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.widget.iWidget;
import com.google.j2objc.annotations.Weak;
import com.google.j2objc.annotations.WeakOuter;

/*-[
 #import "RAREImageWrapper.h"
 #include "com/appnativa/rare/ui/effects/iAnimatorListener.h"
 ]-*/
public class ImagePanel extends aImagePanel {
  MouseHandler mouseHandler;
  ScaleHandler scaleHandler;

  public ImagePanel(iWidget context) {
    this(false);
  }

  public ImagePanel(boolean useTransforms) {
    super(new ImageViewEx(useTransforms));
    ((ImageViewEx) view).panel = this;
    this.usingTransforms       = useTransforms;
    isContextFlipped           = true;
  }

  @Override
  public UIImage getRenderedImage() {
    ImageView v = getImageView();

    if (v == null) {
      return super.getRenderedImage();
    }

    return isImageFitted()
           ? v.capture()
           : v.captureInRect(destBounds, getWidth(), getHeight());
  }

  @Override
  protected void handleSizeChangeAnimation(BoundsChanger bc) {
    updateTransformsEx(bc);
  }

  protected native void animateTo(float x, float y, float width, float height, Object uiview,
                                  iPlatformAnimator animator)
  /*-[
  #if TARGET_OS_IPHONE
    UIView* view=(UIView*)uiview;
    CGRect frame=CGRectMake(x, y, width, height);
    if(animator) {
      [animateSizeChangeListener_ animationStartedWithRAREiPlatformAnimator: animator];
    }
    [view layoutIfNeeded];
    [UIView animateWithDuration:0.2f delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
      view.frame=frame;
    } completion:^(BOOL finished) {
      if(animator) {
        [animateSizeChangeListener_ animationEndedWithRAREiPlatformAnimator: animator];
      }
    }];
  #else
  #endif
  ]-*/
  ;

  protected native void animateAndRotateTo(float x, float y, float width, float height, float rotation, Object uiview,
          iPlatformAnimator animator)
  /*-[
  #if TARGET_OS_IPHONE
  UIView* view=(UIView*)uiview;
  CGRect frame=CGRectMake(x, y, width, height);

  if(animator) {
    [animateSizeChangeListener_ animationStartedWithRAREiPlatformAnimator: animator];
  }
  [view layoutIfNeeded];
  [UIView animateWithDuration:0.2f delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
    view.frame=frame;
    if(rotation==0) {
      view.layer.transform=CATransform3DIdentity;
    }
    else{
      view.layer.transform = CATransform3DMakeRotation(rotation / 180.0 * M_PI, 0.0, 0.0, 1.0);
    }
  } completion:^(BOOL finished) {
    if(animator) {
      [animateSizeChangeListener_ animationEndedWithRAREiPlatformAnimator: animator];
    }
  }];
  #else
  #endif
  ]-*/
  ;

  @Override
  protected void imageLoaded() {
    ImageView v = getImageView();

    if (v != null) {
      v.setImage(theImage);
      updateTransforms();
    }

    super.imageLoaded();
  }

  @Override
  protected void imageNoYetLoaded() {
    ImageView v = getImageView();

    if ((v != null) && (v.getImage() != null)) {
      v.setImage(null);
    }

    super.imageNoYetLoaded();
  }
  
  @Override
  public void setAutoScale(boolean auto) {
    super.setAutoScale(auto);

    if (auto && (mouseHandler == null)) {
      addMouseMotionListener(mouseHandler = new MouseHandler());
      addMouseListener(mouseHandler);
    }
  }

  @Override
  public void setMovingAllowed(boolean allowed) {
    super.setMovingAllowed(allowed);

    if (allowed && (mouseHandler == null)) {
      addMouseMotionListener(mouseHandler = new MouseHandler());
      addMouseListener(mouseHandler);
    }
  }

  @Override
  public void setMovingOnlyAllowedWhenToLarge(boolean allowed) {
    super.setMovingOnlyAllowedWhenToLarge(allowed);

    if (allowed && (mouseHandler == null)) {
      addMouseMotionListener(mouseHandler = new MouseHandler());
      addMouseListener(mouseHandler);
    }
  }

  @Override
  public void setImageBorder(iPlatformBorder imageBorder) {
    super.setImageBorder(imageBorder);

    ImageView v = getImageView();

    if (v != null) {
      v.setBorder(imageBorder);
      v.setImage(null);
      v.setImage(theImage);
    }
  }

  @Override
  public void setPreserveAspectRatio(boolean preserveAspectRatio) {
    super.setPreserveAspectRatio(preserveAspectRatio);

    ImageView v = getImageView();

    if (v != null) {
      v.setPreserveAspectRatio(preserveAspectRatio, fillViewport);
    }
  }

  @Override
  public void setFillViewport(boolean fillViewport) {
    super.setFillViewport(fillViewport);

    ImageView v = getImageView();

    if (v != null) {
      v.setPreserveAspectRatio(preserveAspectRatio, fillViewport);
    }
  }

  @Override
  public void setSelectionColor(UIColor color) {
    super.setSelectionColor(color);

    ImageView v = getImageView();

    if (v != null) {
      v.setSelectionColor(color);
    }
  }

  @Override
  public void setSelectionShape(iPlatformShape selection) {
    super.setSelectionShape(selection);

    ImageView v = getImageView();

    if (v != null) {
      v.setSelection(selection);
    }
  }

  @Override
  public void setSelectionStroke(UIStroke selectionStroke) {
    super.setSelectionStroke(selectionStroke);

    ImageView v = getImageView();

    if (v != null) {
      v.setSelectionStroke(selectionStroke);
    }
  }

  @Override
  public void setZoomingAllowed(boolean allowed) {
    super.setZoomingAllowed(allowed);

    if (allowed && (scaleHandler == null)) {
      addScaleListener(scaleHandler = new ScaleHandler());
    }
  }

  @Override
  protected void addSpinnerComponent(iPlatformComponent c) {
    ImageView v = getImageView();

    if (v != null) {
      v.add(-1, c.getView());
    } else {
      add(c);
    }
  }

  @Override
  protected void removeSpinnerComponent(iPlatformComponent c) {
    ImageView v = getImageView();

    if (v != null) {
      v.removeChild(c.getView());
    } else {
      remove(c);
    }
  }

  @Override
  protected void adjustDestForAspectRatio(float width, float height, boolean fit, boolean move) {
    super.adjustDestForAspectRatio(width, height, fit, move);

    if (autoScale &&!zoomingAllowed) {
      destBounds.x      = 0;
      destBounds.y      = 0;
      destBounds.width  = width;
      destBounds.height = height;
    } else {
      if ((rotation == 90) || (rotation == 270)) {
        if (move) {
          destBounds.x = (width - destBounds.height) / 2;
          destBounds.y = (height - destBounds.width) / 2;
        }
      }
    }
  }

  @Override
  protected void updateTransforms() {
    updateTransformsEx(null);
  }

  protected void updateTransformsEx(BoundsChanger bc) {
    ImageView v = getImageView();

    if (v == null) {
      return;
    }

    float    x  = 0;
    float    y  = 0;
    UIInsets in = getInsetsEx();

    if (in != null) {
      x += in.left;
      y += in.top;
    }

    if (bc != null) {
      float dw = bc.from.width;
      float dh = bc.from.height;

      if ((rotation == 90) || (rotation == 270)) {
        float n = dh;

        dh = dw;
        dw = n;
      }

      v.setBounds(x + bc.from.x, y + bc.from.y, dw, dh);
    }

    float dw = destBounds.width;
    float dh = destBounds.height;

    if ((rotation == 90) || (rotation == 270)) {
      float n = dh;

      dh = dw;
      dw = n;
    }

    if (rotation != v.getRotation()) {
      if (animatingRotationChange) {
        iPlatformAnimator a = (animateSizeChangeListener == null)
                              ? null
                              : new SizeAnimation();

        animateAndRotateTo(x + destBounds.x, y + destBounds.y, dw, dh, rotation, v.getProxy(), a);

        return;
      }
    }

    if (animateBoundsChange) {
      iPlatformAnimator a = (animateSizeChangeListener == null)
                            ? null
                            : new SizeAnimation();

      animateTo(x + destBounds.x, y + destBounds.y, dw, dh, v.getProxy(), a);
    } else {
      v.setRotation(rotation);
      v.setBounds(x + destBounds.x, y + destBounds.y, dw, dh);
    }
  }

  protected ImageView getImageView() {
    return ((ImageViewEx) view).imageView;
  }

  static class ImageViewEx extends ParentView {
    ImageView  imageView;
    @Weak
    ImagePanel panel;
    boolean    useTransforms;

    public ImageViewEx(boolean useTransforms) {
      super(createAPView());
      this.useTransforms = useTransforms;
      setPaintHandlerEnabled(true);

      if (useTransforms) {
        imageView = new ImageView();
        imageView.setVisible(false);
        imageView.setManageVisibility(true);
        add(-1, imageView);
      }
    }

    @Override
    protected void disposeEx() {
      if (imageView != null) {
        removeChild(imageView);
        imageView.dispose();
      }

      super.disposeEx();
      imageView = null;
    }

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      super.paintBackground(g, v, rect);

      if (imageView == null) {
        panel.paint(g, rect.x, rect.y, rect.width, rect.height);
      }
    }
  }


  @WeakOuter
  class MouseHandler implements iMouseMotionListener, iMouseListener {
    float           lastX;
    float           lastY;
    float           startX;
    float           startY;
    protected float slop = Platform.isTouchDevice()
                           ? 10
                           : 4;

    @Override
    public void mouseDragged(MouseEvent e) {
      if (movingAllowed || movingAllowedWhenToLarge) {
        if (!movingAllowed && movingAllowedWhenToLarge && isImageFitted()) {
          return;
        }

        float lx = lastX;
        float ly = lastY;

        lastX = e.getX();
        lastY = e.getY();

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
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
      lastX = e.getX();
      lastY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if ((e.getClickCount() > 1) && zoomingAllowed) {
        zoomOnPoint(e.getX(), e.getY());
      }
    }

    @Override
    public void pressCanceled(MouseEvent e) {}

    @Override
    public boolean wantsLongPress() {
      return false;
    }

    @Override
    public boolean wantsMouseMovedEvents() {
      return false;
    }
  }


  @WeakOuter
  class ScaleHandler implements iGestureListener {
    @Override
    public void onFling(Object view, MouseEvent e1, MouseEvent e2, float velocityX, float velocityY) {}

    @Override
    public void onLongPress(Object view, MouseEvent e) {}

    @Override
    public void onRotate(Object view, int type, float rotation, float velocity, float focusX, float focusY) {}

    @Override
    public void onScaleEvent(Object view, int type, Object sgd, float factor) {
      ScaleGestureObject so = (ScaleGestureObject) sgd;

      switch(type) {
        case SCALE_BEGIN :
          pinchZoomHandlerStart(so.getFocusX(), so.getFocusY());

          break;

        case SCALE_END :
          pinchZoomHandlerEnd(so.getFocusX(), so.getFocusY());

          break;

        default :
          pinchZoomHandlerScale(so.getFocusX(), so.getFocusY(), so.getScaleFactor());

          return;
      }
    }
  }
}
