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

import java.net.URL;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.iComposite.CompositeType;
import com.appnativa.rare.ui.effects.aAnimator.BoundsChanger;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.util.SNumber;

/**
 * A panel that displays an image and supports moving, scaling, etc.
 *
 * @author Don DeCoteau
 */
public abstract class aImagePanel extends XPContainer implements iPainterSupport, iImageObserver {
  public static String         PROPERTY_PINCHZOOM = "pinchZoom";
  public static String         PROPERTY_ZOOM      = "zoom";
  public static String         PROPERTY_ROTATION  = "rotation";
  protected float              maximumScale       = 2;
  protected float              minimumScale       = 0.1f;
  protected int                moveIncrement      = 10;
  protected int                rotation           = 0;
  protected ScalingType        scalingType        = ScalingType.BILINEAR;
  protected boolean            movingAllowed      = false;
  protected UIRectangle        destBounds         = new UIRectangle();
  protected UIRectangle        srcBounds          = new UIRectangle();
  protected AnimationComponent animationComponent;
  protected boolean            autoScale;
  protected boolean            center;
  protected boolean            centerInitially;
  protected iPlatformBorder    imageBorder;
  protected UIInsets           imageBorderInsets;
  protected PinchZoomHandler   pinchZoom;
  protected boolean            preserveAspectRatio;
  protected boolean            rotatingAllowed;
  protected float              scaleIncrement;
  protected iPlatformShape     selection;
  protected UIColor            selectionColor;
  protected UIStroke           selectionStroke;
  protected boolean            showZoomTooltip;
  protected iPlatformComponent spinnerComponent;
  protected UIImage            theImage;
  protected UIImage            originalImage;
  protected float              theScale;
  protected boolean            useSpinner;
  protected boolean            userSelectionAllowed;
  protected boolean            usingTransforms;
  protected boolean            zoomingAllowed;
  protected int                oldWidth;
  protected int                oldHeight;
  protected boolean            movingAllowedWhenToLarge;
  protected boolean            fillViewport;
  protected boolean            animateBoundsChange;
  protected boolean            animatingSizeChange;
  protected boolean            animatingRotationChange;
  protected iAnimatorListener  animateSizeChangeListener;
  protected iComposite         imageComposite;
  protected boolean            isContextFlipped;
  private boolean              disposeImageOnChange;
  protected boolean            pinchZoomPanelOnly;

  /**
   * Constructs a new instance
   *
   */
  public aImagePanel() {
    super();
    init(-1, -1, true);
  }

  /**
   * Constructs a new instance
   *
   * @param view
   *          the view
   */
  public aImagePanel(Object view) {
    super(view);
    init(-1, -1, true);
  }

  
  public boolean canZoomIn() {
    return theScale > maximumScale;
  }

  
  public boolean canZoomOut() {
    return theScale > minimumScale;
  }

  public void cancelLoading() {}

  
  public void centerImage() {
    centerOrFitImage(true);
    update();
  }

  
  public void fitImage() {
    centerOrFitImage(false);
    update();
  }

  protected void centerOrFitImage(boolean center) {
    int      width  = getWidth();
    int      height = getHeight();
    UIInsets in     = getPaintInsets();

    if (in != null) {
      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);
    }

    adjustDestForAspectRatio(width, height, !center, true);
  }

  public void centerOn(int x, int y) {
    centerOnEx(x, y);
    update();
  }

  
  public void clear() {
    initValues();

    if (disposeImageOnChange && (theImage != null)) {
      theImage.dispose();
    }

    theImage      = null;
    originalImage = null;
    destBounds.set(0, 0, 0, 0);
  }

  
  @Override
  public void dispose() {
    super.dispose();

    if (theImage != null) {
      theImage.dispose();
    }

    if ((originalImage != null) && (originalImage != theImage)) {
      originalImage.dispose();
    }

    theImage      = null;
    originalImage = null;
    scalingType   = null;
  }

  
  @Override
  public void imageLoaded(UIImage image) {
    if (image != null) {
      if (image != theImage) {
        setImage(image, -1, -1);
      } else {
        if (Platform.isUIThread()) {
          init(-1, -1, true);
        } else {
          Platform.invokeLater(new Runnable() {
            
            @Override
            public void run() {
              init(-1, -1, true);
            }
          });
        }
      }
    }
  }

  /**
   * Move the image by the specified amount Note: once the image is off screen
   * subsequent calls are ignored
   *
   * @param dx
   *          the amount to move in the x-direction
   * @param dy
   *          the amount to move in the y-direction
   */
  public void moveImage(float dx, float dy) {
    dx = destBounds.x + dx;
    dy = destBounds.y + dy;

    if (dx < -destBounds.width) {
      dx = -destBounds.width;
    }

    if (dy < -destBounds.height) {
      dy = -destBounds.height;
    }

    int      width  = getWidth();
    int      height = getHeight();
    UIInsets in     = getPaintInsets();

    if (in != null) {
      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);
    }

    if (dx > width) {
      dx = width;
    }

    if (dy > height) {
      dy = height;
    }

    if (!SNumber.isEqual(dx, destBounds.x) ||!SNumber.isEqual(dy, destBounds.y)) {
      destBounds.x = dx;
      destBounds.y = dy;

      if (usingTransforms) {
        updateTransforms();
      } else {
        repaint();
      }
    }
  }

  public void pinchZoomHandlerInitialize() {
    if (pinchZoom == null) {
      pinchZoom = new PinchZoomHandler(minimumScale, maximumScale);
    }
  }

  public void pinchZoomHandlerStart(float x, float y) {
    if (pinchZoom == null) {
      pinchZoomHandlerInitialize();
    }

    pinchZoom.resetRange(minimumScale, maximumScale);
    pinchZoom.resetBounds(destBounds, getImageWidth(), getImageHeight(), theScale);
    pinchZoom.scaleStart(x, y);
    this.firePropertyChange(PROPERTY_PINCHZOOM, Boolean.FALSE, Boolean.TRUE);
  }

  
  public void resetImage() {
    theImage = originalImage;
    setScale(1.0f, false);
    setRotation(0);
    init(-1, -1, true);
    update();
  }

  protected void setRotation(final int rotation) {
    if (this.rotation != rotation) {
      final int oldRotation = this.rotation;

      this.rotation = rotation;
      Platform.invokeLater(new Runnable() {
        
        @Override
        public void run() {
          firePropertyChange(PROPERTY_ROTATION, oldRotation, rotation);
        }
      });
    }
  }

  
  public void rotateLeft() {
    if (theImage != null) {
      rotation -= 90;
      postRotate();
    }
  }

  
  public void rotateRight() {
    if (theImage != null) {
      rotation += 90;
      postRotate();
    }
  }

  
  public void zoom(int percent) {
    center = false;

    if (theImage != null) {
      float sc = percent / 100f;

      setScale(sc, true);
    }
  }

  
  public void zoomIn() {
    center = false;

    if (theImage != null) {
      float sc = theScale;

      sc += scaleIncrement;
      setScale(sc, true);
    }
  }

  
  public void zoomOut() {
    center = false;

    if (theImage != null) {
      float sc = theScale;

      sc -= scaleIncrement;
      setScale(sc, true);
    }
  }

  public void zoomTo(float scale, float x, float y) {
    center = false;

    if (setScale(scale, false)) {
      scale = theScale;
      x     = x * scale - destBounds.x;
      y     = y * scale - destBounds.y;
      centerOnEx(x, y);
      update();
    }
  }

  
  public void setAutoScale(boolean auto) {
    this.autoScale = auto;
  }

  
  public void setCenterInitially(boolean centerInitially) {
    this.centerInitially = centerInitially;
    this.center          = centerInitially;
  }

  
  public void setDisposeImageOnChange(boolean dispose) {
    disposeImageOnChange = dispose;
  }

  
  public boolean isDisposeImageOnChange() {
    return disposeImageOnChange;
  }

  
  public void setImage(UIImage img) {
    setImage(img, -1, -1);
  }

  public void setImage(URL url) throws java.io.IOException {
    setImage(PlatformHelper.createImage(url, true, 0));
  }

  
  public void setImage(final UIImage img, final int width, final int height) {
    if (Platform.isUIThread()) {
      setImageEx(img, width, height);
    } else {
      Platform.invokeLater(new Runnable() {
        
        @Override
        public void run() {
          setImageEx(img, width, height);
        }
      });
    }
  }

  
  public void setImageBorder(iPlatformBorder imageBorder) {
    this.imageBorder  = imageBorder;
    imageBorderInsets = (imageBorder == null)
                        ? null
                        : imageBorder.getBorderInsets(imageBorderInsets);
  }

  public void setImageAlpha(float alpha) {
    if ((int) Math.ceil(alpha * 100) >= 100) {
      imageComposite = null;
    } else {
      imageComposite = new GraphicsComposite(CompositeType.SRC_OVER, alpha);
    }
  }

  public void setImageComposite(iComposite composite) {
    imageComposite = composite;
  }

  public float getImageAlpha() {
    return (imageComposite == null)
           ? 1f
           : imageComposite.getAlpha();
  }

  
  public void setMaximumZoom(int percent) {
    if ((percent > 0) && (percent < 5000)) {
      maximumScale = percent / 100f;
    }
  }

  
  public void setMinimumZoom(int percent) {
    if ((percent > 0) && (percent < 5000)) {
      minimumScale = percent / 100f;
    }
  }

  /**
   * The amount toe move the image by when the keyboard is used to move the
   * image
   *
   * @param increment
   *          the increment
   */
  public void setMoveIncrement(int increment) {
    this.moveIncrement = increment;
  }

  public void setMovingAllowed(boolean allowed) {
    this.movingAllowed = allowed;
  }

  /**
   * Sets that moving is only allowed when the image (zoomed in or otherwise) if
   * to large to fit within the pane.
   *
   * @param allowed
   *          true to allow false otherwise
   */
  public void setMovingOnlyAllowedWhenToLarge(boolean allowed) {
    this.movingAllowedWhenToLarge = allowed;
  }

  public void setPreserveAspectRatio(boolean preserveAspectRatio) {
    this.preserveAspectRatio = preserveAspectRatio;
  }

  public void setRotatingAllowed(boolean allowed) {
    this.rotatingAllowed = allowed;
  }

  
  public void setScalingType(ScalingType type) {
    this.scalingType = type;
  }

  
  @Override
  public void setBounds(float x, float y, float width, float height) {
    UIRectangle from = animateBoundsChange
                       ? new UIRectangle(destBounds)
                       : null;

    super.setBounds(x, y, width, height);

    if (usingTransforms || animateBoundsChange) {
      UIInsets in = getPaintInsets();

      if (in != null) {
        width  -= (in.left + in.right);
        height -= (in.top + in.bottom);
      }

      int iwidth  = Math.round(width);
      int iheight = Math.round(height);

      if ((iwidth != oldWidth) || (iheight != oldHeight)) {
        oldWidth  = iwidth;
        oldHeight = iheight;

        if (center) {
          centerOrFitImage(true);
        } else {
          if (isAutoScale()) {
            if (preserveAspectRatio) {
              adjustDestForAspectRatio(width, height, true, true);
            } else {
              destBounds.setBounds(destBounds.x, destBounds.y, width, height);
            }
          }
        }
      }
    }

    if (from != null) {
      BoundsChanger bc = new BoundsChanger(from, new UIRectangle(destBounds));

      if (bc.isSizeDifferent()) {
        bc.from.x += bc.wdiff / 2;
        bc.from.y += bc.hdiff / 2;
        bc.updateDiffs();
        handleSizeChangeAnimation(bc);

        return;
      }
    }

    if (usingTransforms) {
      updateTransforms();
    }

    if (spinnerComponent != null) {
      UIDimension d = spinnerComponent.getPreferredSize();

      spinnerComponent.setBounds((width - d.width) / 2, (height - d.height) / 2, d.width, d.height);
    }
  }

  
  public void setSelectionColor(UIColor color) {
    this.selectionColor = color;

    if (usingTransforms) {
      updateSelection();
    } else {
      repaint();
    }
  }

  public void setSelectionShape(iPlatformShape selection) {
    this.selection = selection;

    if (usingTransforms) {
      updateSelection();
    } else {
      repaint();
    }
  }

  public void setSelectionStroke(UIStroke selectionStroke) {
    this.selectionStroke = selectionStroke;

    if (usingTransforms) {
      updateSelection();
    } else {
      repaint();
    }
  }

  public void setUseSpinner(boolean spinner) {
    useSpinner = spinner;
  }

  
  public void setUserSelectionAllowed(boolean allowed) {
    this.userSelectionAllowed = allowed;
  }

  
  public void setZoomIncrementPercent(int percent) {
    if (percent > 100) {
      percent = 100;
    }

    scaleIncrement = (percent) / 100f;

    if (scaleIncrement == 0) {
      scaleIncrement = 0.1f;
    }
  }

  
  public void setZoomingAllowed(boolean allowed) {
    this.zoomingAllowed = allowed;
  }

  
  public iPlatformComponent getComponent() {
    return this;
  }

  
  public UIImage getImage() {
    return theImage;
  }

  
  public iPlatformBorder getImageBorder() {
    return imageBorder;
  }

  
  public int getImageHeight() {
    return (theImage == null)
           ? 0
           : theImage.getHeight();
  }

  
  public int getImageRotation() {
    return rotation;
  }

  
  public float getImageScale() {
    return theScale;
  }

    public UIImage getImageWithCurrentRotation() {
    if (!usingTransforms && (theImage != null) && theImage.isLoaded()) {
      return UIImageHelper.rotateImage(theImage, rotation);
    }

    return theImage;
  }

  
  public int getImageWidth() {
    return (theImage == null)
           ? 0
           : theImage.getWidth();
  }

  public int getImageScaleWidth() {
    return (theImage == null)
           ? 0
           : (int)destBounds.width;
  }

  public int getImageScaleHeight() {
    return (theImage == null)
           ? 0
           : (int)destBounds.height;
  }

  public int getMaximumZoom() {
    return (int) (maximumScale * 100);
  }

  public int getMinimumZoom() {
    return (int) (minimumScale * 100);
  }

  /**
   * @return the moveIncrement
   */
  public int getMoveIncrement() {
    return moveIncrement;
  }

  public ScalingType getScalingType() {
    return scalingType;
  }

  
  public iPlatformShape getSelection() {
    return selection;
  }

  /**
   * @return the selectionColor
   */
  public UIColor getSelectionColor() {
    return selectionColor;
  }

  public iPlatformShape getSelectionShape() {
    return selection;
  }

  /**
   * @return the selectionStroke
   */
  public UIStroke getSelectionStroke() {
    return selectionStroke;
  }

  
  public String getSource() {
    return (theImage == null)
           ? null
           : theImage.getLocation();
  }

  
  public UIImage getRenderedImage() {
    int      width  = getWidth();
    int      height = getHeight();
    UIInsets in     = getInsetsEx();
    float    x      = 0;
    float    y      = 0;

    if (in != null) {
      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);
      x      = -in.left;
      y      = -in.top;
    }

    int w = UIScreen.snapToSize(destBounds.width);
    int h = UIScreen.snapToSize(destBounds.height);

    if (rotation != 0) {
      int n = w;

      w = h;
      h = n;
    }

    w = Math.min(w, width);
    h = Math.min(h, height);

    UIImage           img = new UIImage(w, h);
    iPlatformGraphics g   = img.createGraphics();

    animatingSizeChange = true;    // prevent auto scale updates
    setupRenderedImageGraphics(g, w, h);
    paint(g, x, y, w, h);
    animatingSizeChange = false;
    g.dispose();

    return img;
  }

  protected void setupRenderedImageGraphics(iPlatformGraphics g, int width, int height) {}

  public UIImage getSubImage(iPlatformShape shape) {
    if (!theImage.isLoaded(this)) {
      return null;
    }

    UIRectangle r = shape.getBounds();

    return theImage.getSubimage((int) r.x, (int) r.y, (int) r.width, (int) r.height);
  }

  
  public int getZoomPercent() {
    return (int) (theScale * 100);
  }

  public boolean hasImage() {
    return theImage != null;
  }

  public boolean hasValue() {
    return theImage != null;
  }

  /**
   * Whether the image should be automatically scaled to fit the size of the
   * panel
   *
   * @return true if it should; false otherwise
   */
  public boolean isAutoScale() {
    return autoScale;
  }

  public boolean isMovingAllowed() {
    return movingAllowed;
  }

  public boolean isPanningAllowed() {
    return false;
  }

  public boolean isRotatingAllowed() {
    return rotatingAllowed;
  }

  public boolean isShowZoomTooltip() {
    return showZoomTooltip;
  }

  public boolean isTextSelectionAllowed() {
    return false;
  }

  /**
   * @return the userSelectionAllowed
   */
  public boolean isUserSelectionAllowed() {
    return userSelectionAllowed;
  }

  public boolean isZoomingAllowed() {
    return zoomingAllowed;
  }

  protected abstract void addSpinnerComponent(iPlatformComponent c);

  protected void adjustDestForAspectRatio(float width, float height, boolean fit, boolean move) {
    UIImage img = getPaintImage();

    if ((img == null) ||!img.isLoaded()) {
      destBounds.width  = -1;
      destBounds.height = -1;

      return;
    }

    float iw = img.getWidth();
    float ih = img.getHeight();

    if ((((rotation == 90) || (rotation == 270)))) {
      float n = height;

      height = width;
      width  = n;
    }

    if (iw > 200) {
      destBounds.width = 1;
    }

    float ws    = width / iw;
    float hs    = height / ih;
    float scale = fillViewport
                  ? Math.max(hs, ws)
                  : Math.min(hs, ws);

    if (scale == 0) {
      scale = 1;
    } else if (!fit) {
      scale = (scale > 1)
              ? 1
              : scale;
    }

    setScale(scale, false);
    scale = theScale;
    ih    *= scale;
    iw    *= scale;

    if (move) {
      destBounds.x = (width - iw) / 2;
      destBounds.y = (height - ih) / 2;
    }

    destBounds.width  = iw;
    destBounds.height = ih;
  }

  protected void centerOnEx(float x, float y) {
    x = x - destBounds.x;
    y = y - destBounds.y;

    int      width  = getWidth();
    int      height = getHeight();
    UIInsets in     = getPaintInsets();

    if (in != null) {
      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);
    }

    x            = (width / 2) - x;
    y            = (height / 2) - y;
    destBounds.x = x;
    destBounds.y = y;
  }

  protected void imageLoaded() {
    repaint();
  }

  protected void imageNoYetLoaded() {}

  protected void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    UIImage image = getPaintImage();

    if ((image == null) ||!image.isLoaded()) {
      return;
    }

    if ((destBounds.width == -1) || (destBounds.height == -1)) {
      if (image.isLoaded()) {
        init(-1, -1, false);
      } else {
        return;
      }
    }

    UIInsets in = getInsetsEx();

    if (in != null) {
      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);
      x      += in.left;
      y      += in.top;
    }

    in = imageBorderInsets;

    if (in != null) {
      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);
      x      += in.left;
      y      += in.top;
    }

    int iwidth  = Math.round(width);
    int iheight = Math.round(height);

    if (!animatingSizeChange) {
      if (center) {
        center = false;
        centerOrFitImage(true);
      } else if (isAutoScale() && ((iwidth != oldWidth) || (iheight != oldHeight))) {
        if (preserveAspectRatio) {
          adjustDestForAspectRatio(width, height, true, true);
        } else {
          destBounds.setBounds(destBounds.x, destBounds.y, width, height);
        }
      }

      oldHeight = iheight;
      oldWidth  = iwidth;
    }

    float iw      = destBounds.width;
    float ih      = destBounds.height;
    int   degrees = isContextFlipped
                    ? (360 - rotation)
                    : rotation;

    if (degrees == 360) {
      degrees = 0;
    }

    switch(degrees) {
      case 90 :
        x += width;

        break;

      case 180 :
        x += width;
        y += height;

        break;

      case 270 :
        y += height;

        break;

      default :
        break;
    }

    g.saveState();
    g.translate(x, y);

    if (imageComposite != null) {
      g.setComposite(imageComposite);
    }

    if ((imageBorder != null) &&!imageBorder.isPaintLast()) {
      imageBorder.paint(g, destBounds.x - in.left, destBounds.y - in.top, iw + (in.left + in.right),
                        ih + (in.top + in.bottom), false);
    }

    if (degrees != 0) {
      g.setRotation(degrees);
    }

    if (!pinchZoomPanelOnly) {
      g.drawImage(image, srcBounds, destBounds, scalingType, null);
    }

    if ((imageBorder != null) && imageBorder.isPaintLast()) {
      if (degrees != 0) {
        g.setRotation(0);
        degrees = 0;
      }

      imageBorder.paint(g, destBounds.x - in.left, destBounds.y - in.top, iw + (in.left + in.right),
                        ih + (in.top + in.bottom), true);
    }

    if (selection != null) {
      if (degrees != 0) {
        g.setRotation(0);
        degrees = 0;
      }

      if (selectionStroke != null) {
        g.setStroke(selectionStroke);
      }

      if (selectionColor != null) {
        g.setColor(selectionColor);
      } else {
        g.setColor(UIColorHelper.getColor("Rare.textHighlight"));
      }

      g.drawShape(selection, x, y);
    }

    g.restoreState();
  }

  protected void pinchZoomHandlerEnd(float x, float y) {
    if (pinchZoom != null) {
      pinchZoom.scaleEnd(x, y);
    }

    this.firePropertyChange(PROPERTY_PINCHZOOM, Boolean.TRUE, Boolean.FALSE);
  }

  protected void pinchZoomHandlerScale(float x, float y, float scale) {
    if (pinchZoom.scale(x, y, scale)) {
      updateFromPinchZoomHandler(pinchZoom);
    }
  }

  protected abstract void removeSpinnerComponent(iPlatformComponent c);

  protected boolean setScale(float scale, boolean repaint) {
    scale = Math.max(scale, minimumScale);
    scale = Math.min(scale, maximumScale);

    int oldZoom = (int) (theScale * 100);
    int newZoom = (int) (scale * 100);

    theScale = scale;

    if (oldZoom != newZoom) {
      if (repaint) {
        update();
      }

      fireZoomChange(oldZoom, newZoom);

      return true;
    }

    return false;
  }

  protected void fireZoomChange(final int oldZoom, final int newZoom) {
    Platform.invokeLater(new Runnable() {
      
      @Override
      public void run() {
        firePropertyChange(PROPERTY_ZOOM, oldZoom, newZoom);
      }
    });
  }

  protected void update() {
    destBounds.height = (srcBounds.height * theScale);
    destBounds.width  = (srcBounds.width * theScale);

    if (usingTransforms) {
      updateTransforms();
    } else {
      repaint();
    }
  }

  protected void updateFromPinchZoomHandler(PinchZoomHandler h) {
    center = false;
    h.getBounds(destBounds);
    setScale(h.getScale(), true);
  }

  protected void updateSelection() {}

  protected void updateTransforms() {}

  public boolean isImageLargerThanViewPort() {
    return (srcBounds.width > destBounds.width) || (srcBounds.height > destBounds.height);
  }

  protected void zoomOnPoint(float x, float y) {
    center = false;

    if (isAutoScale()) {
      if (isImageLargerThanViewPort()) {
        if ((theScale * 100) > 99) {
          fitImage();
        } else {
          pinchZoomHandlerInitialize();
          pinchZoom.resetRange(minimumScale, maximumScale);
          pinchZoom.resetBounds(destBounds, getImageWidth(), getImageHeight(), theScale);
          pinchZoom.doubleTabScale(x, y, 1 / theScale);
          updateFromPinchZoomHandler(pinchZoom);
        }
      } else {
        if (theScale > 1) {
          centerImage();
        } else {
          fitImage();
        }
      }
    } else {
      if (theScale >= 2) {
        centerImage();
      } else {
        pinchZoomHandlerInitialize();
        pinchZoom.resetRange(minimumScale, maximumScale);
        pinchZoom.resetBounds(destBounds, getImageWidth(), getImageHeight(), theScale);
        pinchZoom.doubleTabScale(x, y, 1 / theScale * 3);
        updateFromPinchZoomHandler(pinchZoom);
      }
    }
  }

  protected abstract void handleSizeChangeAnimation(BoundsChanger bc);

  protected void setImageEx(UIImage img, int width, int height) {
    if (disposeImageOnChange && (theImage != null) && (theImage != img)) {
      theImage.dispose();
    }

    theImage      = img;
    originalImage = img;
    center        = centerInitially;
    init(width, height, true);
    repaint();
  }

  protected UIImage getPaintImage() {
    return theImage;
  }

  protected UIInsets getPaintInsets() {
    UIInsets in = getInsetsEx();

    if (!usingTransforms && (imageBorderInsets != null)) {
      if (in == null) {
        return imageBorderInsets;
      } else {
        in.addInsets(imageBorderInsets);
      }
    }

    return in;
  }

  
  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (isAnimating()) {
      getSize(size);
    } else if (theImage != null) {
      size.setSize(srcBounds.width, srcBounds.height);

      if (rotation % 90 != 0) {
        float n = size.width;

        size.width  = size.height;
        size.height = n;
      }
    } else {
      size.width  = 0;
      size.height = 0;
    }
  }

  private void handleSpinner(boolean show) {
    if (show) {
      if (useSpinner) {
        if (spinnerComponent == null) {
          iActionComponent comp = PlatformHelper.createLabel(this);

          comp.setIcon(UISpriteIcon.getDefaultSpinner());
          spinnerComponent = comp;
        }

        if (spinnerComponent != null) {
          UIDimension d  = spinnerComponent.getPreferredSize();
          UIDimension md = getSize();

          spinnerComponent.setBounds((md.width - d.width) / 2, (md.height - d.height) / 2, d.width, d.height);
          addSpinnerComponent(spinnerComponent);
        }
      }
    } else if (spinnerComponent != null) {
      removeSpinnerComponent(spinnerComponent);
    }
  }

  protected void init(int iw, int ih, boolean reset) {
    boolean loaded = (theImage != null) && theImage.isLoaded(this);

    if (reset) {
      initValues();
    }

    int imageWidth  = iw;
    int imageHeight = ih;

    if (loaded) {
      if (iw < 1) {
        imageWidth = theImage.getWidth();
      }

      if (ih < 1) {
        imageHeight = theImage.getHeight();
      }
    }

    srcBounds.setBounds(0, 0, imageWidth, imageHeight);

    int width  = getWidth();
    int height = getHeight();

    if (!loaded || (width == 0) || (height == 0)) {
      destBounds.set(0, 0, -1, -1);
    } else if (reset) {
      UIInsets in = getPaintInsets();

      if (in != null) {
        width  -= (in.left + in.right);
        height -= (in.top + in.bottom);
      }

      if (autoScale) {
        if ((imageWidth > width) || (imageHeight > height)) {
          if (preserveAspectRatio) {
            fitImage();
          } else {
            destBounds.setBounds(0, 0, width, height);
          }
        } else {
          adjustDestForAspectRatio(width, height, true, true);
        }

        oldWidth  = width;
        oldHeight = height;
      } else {
        destBounds.setBounds(0, 0, width, height);
      }
    }

    if (loaded) {
      handleSpinner(false);
      imageLoaded();

      if (widget != null) {
        widget.finishedLoading();
      }
    } else {
      handleSpinner(true);
      imageNoYetLoaded();
    }
  }

  protected void initValues() {
    theScale       = 1.0f;
    scaleIncrement = 0.01f;
    rotation       = 0;
    center         = centerInitially;
    oldHeight      = 0;
    oldWidth       = 0;
  }

  protected void postRotate() {
    rotation = rotation % 360;

    if (rotation < 0) {
      rotation += 360;
    }

    if (rotation == 0) {
      theImage = originalImage;
    }

    postRotateEx();
    centerOrFitImage(false);
    update();
  }

  protected void postRotateEx() {}

  
  public boolean isImageFitted() {
    if ((destBounds.x > -1) && (destBounds.y > -1)) {
      UIInsets in = getPaintInsets();
      int      w  = getWidth();
      int      h  = getHeight();

      if (in != null) {
        w -= (in.left + in.right);
        h -= (in.top + in.bottom);
      }

      if (rotation != 0) {
        return (destBounds.width <= h) && (destBounds.height <= w);
      }

      return (destBounds.width <= w) && (destBounds.height <= h);
    }

    return false;
  }

  
  public boolean isFillViewport() {
    return fillViewport;
  }

  
  public void setFillViewport(boolean fillViewport) {
    this.fillViewport = fillViewport;
  }

  
  public boolean isAnimateBoundsChange() {
    return animateBoundsChange;
  }

  
  public void setAnimateBoundsChange(boolean animateBoundsChange, iAnimatorListener listener) {
    this.animateBoundsChange  = animateBoundsChange;
    animateSizeChangeListener = listener;
  }

  public boolean isAnimatingRotationChange() {
    return animatingRotationChange;
  }

  public void setAnimatingRotationChange(boolean animatingRotationChange) {
    this.animatingRotationChange = animatingRotationChange;
  }
}
