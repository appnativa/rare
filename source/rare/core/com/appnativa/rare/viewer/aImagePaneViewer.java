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

package com.appnativa.rare.viewer;

import java.io.IOException;
import java.util.Locale;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.ImagePane;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.aImagePanel;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.util.SNumber;

public abstract class aImagePaneViewer extends aPlatformViewer {
  protected int         maxZoom       = 1000;
  protected int         minZoom       = 10;
  protected int         zoomIncrement = 0;
  protected ScalingType scalingType   = ScalingType.BILINEAR;
  protected boolean     autoScale;

  /** image panel */
  protected aImagePanel imageView;
  protected boolean             movingAllowed;
  protected boolean             panningAllowed;
  protected boolean             rotatingAllowed;
  protected boolean             userSelectionAllowed;
  protected boolean             zoomingAllowed;
  protected boolean             disposeOnChangeSet;

  /**
   * Constructs a new instance
   */
  public aImagePaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aImagePaneViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.ImagePane;
  }

  /**
   * Centers the image so that it fits within the viewers bounds
   */
  public void centerImage() {
    if (imageView != null) {
      imageView.centerImage();
    }
  }

  /**
   * Returns whether or not the image is currently fitted to be contained within
   * the bounds of the viewer.
   *
   * @return true if fitted; false if parts of the image are truncated
   */
  public boolean isImageFitted() {
    return imageView.isImageFitted();
  }

  @Override
  public void clearContents() {
    if (imageView != null) {
      imageView.clear();
    }
  }

  /**
   * Configures the image pane
   *
   * @param vcfg
   *          the image pane's configuration
   */
  @Override
  public void configure(Viewer vcfg) {
    configureEx((ImagePane) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Get a version of the image as it is currently being
   * rendered in the pane
   *
   * @return the rendered image
   */
  public UIImage getRenderedImage() {
    return imageView.getRenderedImage();
  }

  @Override
  public void handleActionLink(ActionLink link, boolean deferred) {
    try {
      UIImage img = PlatformHelper.createImage(link.getURL(this), deferred, link.getImageDensity());

      if (!disposeOnChangeSet) {
        imageView.setDisposeImageOnChange(true);
      }

      imageView.setImage(img);
    } catch(IOException ex) {
      getAppContext().getDefaultExceptionHandler().handleException(ex);
    }
  }

  /**
   * Resets the image to its default scale and rotation
   */
  public void resetImage() {
    imageView.resetImage();
  }

  /**
   * Rotates the image left by 90 degrees
   */
  public void rotateLeft() {
    imageView.rotateLeft();
  }

  /**
   * Rotates the image right by 90 degrees
   */
  public void rotateRight() {
    imageView.rotateRight();
  }

  /**
   * Zooms in by one scale increment
   */
  public void zoomIn() {
    imageView.zoomIn();
  }

  /**
   * Zooms out by one scale increment
   */
  public void zoomOut() {
    imageView.zoomOut();
  }

  /**
   * Sets that moving is only allowed when the image (zoomed in or otherwise) if
   * to large to fit within the pane.
   *
   * @param allowed
   *          true to allow false otherwise
   */
  public void setMovingOnlyAllowedWhenToLarge(boolean allowed) {
    imageView.setMovingOnlyAllowedWhenToLarge(allowed);
  }

  /**
   * Sets the image to display
   *
   * @param image
   *          the image
   */
  public void setImage(UIImage image) {
    setImage(image, -1, -1);
  }

  /**
   * Sets the image to display
   *
   * @param image
   *          the image
   * @param width
   *          the width for the image panel
   * @param height
   *          the height for the image panel
   */
  public void setImage(final UIImage image, final int width, final int height) {
    if (!disposeOnChangeSet) {
      imageView.setDisposeImageOnChange(false);
    }

    imageView.setImage(image, width, height);
  }

  /**
   * Sets whether the panel should dispose the current image when a new image is
   * set. By default, the viewer only disposes the image when
   * the viewer creates the image via URL/link
   *
   * @param dispose true to dispose; false otherwise
   */
  public void setDisposeImageOnChange(boolean dispose) {
    disposeOnChangeSet = true;
    imageView.setDisposeImageOnChange(dispose);
  }

  /**
   * Gets whether the panel should dispose the current image when a new image is
   * set. By default the panel does NOT dispose of the old image
   *
   * @return true to dispose; false otherwise
   */
  public boolean isDisposeImageOnChange() {
    return imageView.isDisposeImageOnChange();
  }

  /**
   * Sets a border to be drawn around the image
   *
   * @param border
   *          the border
   */
  public void setImageBorder(iPlatformBorder border) {
    if (imageView != null) {
      imageView.setImageBorder(border);
    }
  }

  /**
   * Sets whether moving the image should be allowed
   *
   * @param allowed
   *          true if it should; false otherwise
   */
  public void setMovingAllowed(boolean allowed) {
    this.movingAllowed = allowed;

    if (imageView != null) {
      imageView.setMovingAllowed(allowed);
    }
  }

  public void setPreserveAspectRatio(boolean preserve) {
    if (imageView != null) {
      imageView.setPreserveAspectRatio(preserve);
    }
  }

  /**
   * Sets whether rotating of the image should be allowed
   *
   * @param allowed
   *          true if it should; false otherwise
   */
  public void setRotatingAllowed(boolean allowed) {
    this.rotatingAllowed = allowed;
  }

  /**
   * Sets the scaling type for the image
   *
   * @param type
   *          the scaling type for the image
   */
  public void setScalingType(ScalingType type) {
    this.scalingType = type;

    if (imageView != null) {
      imageView.setScalingType(type);
    }
  }

  /**
   * Sets the maximum zoom in percent. The default is 200%.
   *
   * @param percent the percent
   */
  public void setMaximumZoom(int percent) {
    imageView.setMaximumZoom(maxZoom);
  }

  /**
   * Sets the minimum zoom in percent. The default is 10%.
   *
   * @param percent the percent
   */
  public void setMinimumZoom(int percent) {
    imageView.setMinimumZoom(maxZoom);
  }

  /**
   * Sets the color for selections
   *
   * @param paint
   *          the paint to set
   */
  public void setSelectionColor(UIColor paint) {
    if (imageView != null) {
      imageView.setSelectionColor(paint);
    }
  }

  public abstract void setUseSpinner(boolean spinner);

  /**
   * Sets whether portions of the image can be selected by the user
   *
   * @param allowed
   *          true if it should; false otherwise
   */
  public void setUserSelectionAllowed(boolean allowed) {
    this.userSelectionAllowed = allowed;

    if (imageView != null) {
      imageView.setUserSelectionAllowed(allowed);
    }
  }

  /**
   * Zooms the image
   *
   * @param percent
   *          the percent to zoom the image
   */
  public void setZoomPercent(int percent) {
    if (imageView == null) {
      return;
    }

    imageView.zoom(percent);
  }

  /**
   * Sets whether zooming of the image should be allowed
   *
   * @param allowed
   *          true if it should; false otherwise
   */
  public void setZoomingAllowed(boolean allowed) {
    this.zoomingAllowed = allowed;
    imageView.setZoomingAllowed(allowed);
  }

  /**
   * Gets the current image
   *
   * @return the current image
   */
  public UIImage getImage() {
    return imageView.getImage();
  }

  public iPlatformBorder getImageBorder() {
    if (imageView != null) {
      return imageView.getImageBorder();
    }

    return null;
  }

  /**
   * Get the rotation of the image
   *
   * @return the current image rotation in degrees
   */
  public int getImageRotation() {
    return imageView.getImageRotation();
  }

  /**
   * Get the current image scale
   *
   * @return the current image scale
   */
  public float getImageScale() {
    return imageView.getImageScale();
  }

  /**
   * Get the image panel that is being used to render the image
   *
   * @return the image panel that is being used to render the image
   */
  public aImagePanel getImagePanel() {
    return imageView;
  }

  /**
   * Gets the scaling type for the image
   *
   * @return the scaling type for the image
   */
  public ScalingType getScalingType() {
    return scalingType;
  }

  @Override
  public Object getSelection() {
    if (imageView != null) {
      return imageView.getSelection();
    }

    return null;
  }

  @Override
  public String getSelectionAsString() {
    return null;
  }

  /**
   * Gets the current zoom percent
   *
   * @return the current zoom percent
   */
  public int getZoomPercent() {
    return imageView.getZoomPercent();
  }

  /**
   * Returns whether moving of the image should be allowed
   *
   * @return true if it is; false otherwise
   */
  public boolean isMovingAllowed() {
    return movingAllowed;
  }

  /**
   * Returns whether panning of the image should be allowed
   *
   * @return true if it should; false otherwise
   */
  public boolean isPanningAllowed() {
    return panningAllowed;
  }

  /**
   * Returns whether rotating of the image should be allowed
   *
   * @return true if it should; false otherwise
   */
  public boolean isRotatingAllowed() {
    return rotatingAllowed;
  }

  /**
   * Gets whether portions of the image can be selected by the user
   *
   * @return true if it allowed; false otherwise
   */
  public boolean isUserSelectionAllowed() {
    return userSelectionAllowed;
  }

  /**
   * Gets whether zooming of the image is allowed
   *
   * @return true if it is; false otherwise
   */
  public boolean isZoomingAllowed() {
    return zoomingAllowed;
  }

  @Override
  protected void clearConfiguration(boolean dispose) {
    clearContents();

    if (dispose) {
      imageView   = null;
      scalingType = null;
    } else {
      setRotatingAllowed(false);
      setZoomingAllowed(false);
    }
  }

  /**
   * Configures the image pane
   *
   * @param cfg
   *          the image pane's configuration
   */
  protected void configureEx(ImagePane cfg) {
    String s;

    // set scaling type before panel creation so sub-classes can use it
    if (cfg.scaling.spot_valueWasSet()) {
      s = cfg.scaling.stringValue();

      try {
        scalingType = ScalingType.valueOf(s.toUpperCase(Locale.ENGLISH));
      } catch(Exception e) {
        Platform.ignoreException(s, e);
      }
    }

    imageView     = createPanel(cfg);
    formComponent = dataComponent = imageView.getComponent();
    configureEx(cfg, true, false, true);
    zoomingAllowed       = cfg.zoomingAllowed.booleanValue();
    rotatingAllowed      = cfg.rotatingAllowed.booleanValue();
    movingAllowed        = cfg.movingAllowed.booleanValue();
    autoScale            = cfg.autoScale.booleanValue();
    userSelectionAllowed = cfg.userSelectionAllowed.booleanValue();
    s                    = cfg.zoomingAllowed.spot_getAttribute("maximum");

    if (s != null) {
      maxZoom = SNumber.intValue(s);
    }

    s = cfg.zoomingAllowed.spot_getAttribute("minimum");

    if (s != null) {
      minZoom = SNumber.intValue(s);
    }

    zoomIncrement = SNumber.intValue(cfg.zoomingAllowed.spot_getAttribute("increment"));
    imageView.setCenterInitially(cfg.centerImage.booleanValue());
    imageView.setMovingAllowed(isMovingAllowed());
    imageView.setZoomingAllowed(isZoomingAllowed());
    imageView.setAutoScale(autoScale);
    imageView.setScalingType(scalingType);
    imageView.setUserSelectionAllowed(userSelectionAllowed);
    imageView.setPreserveAspectRatio(cfg.preserveAspectRatio.booleanValue());

    if ("true".equalsIgnoreCase(cfg.preserveAspectRatio.spot_getAttribute("fill"))) {
      imageView.setFillViewport(true);
    }

    if (zoomIncrement != 0) {
      imageView.setZoomIncrementPercent(zoomIncrement);
    }

    if (minZoom > 0) {
      imageView.setMinimumZoom(minZoom);
    }

    if (maxZoom > 0) {
      imageView.setMaximumZoom(maxZoom);
    }

    if (cfg.focusable.spot_valueWasSet()) {
      setFocusable(cfg.focusable.booleanValue());
    } else {
      setFocusable(false);
    }

    s = cfg.dataURL.getValue();

    if ((s != null) && s.startsWith("resource:")) {
      setImage(getAppContext().getResourceAsImage(s.substring(9)));
    } else if (s != null) {
      handleDataURL(cfg);
    }
  }

  public boolean isFillViewport() {
    return imageView.isFillViewport();
  }

  public void setFillViewport(boolean fillViewport) {
    imageView.setFillViewport(fillViewport);
  }

  public boolean isAnimateBoundsChange() {
    return imageView.isAnimateBoundsChange();
  }

  public void setAnimateBoundsChange(boolean animateBoundsChange, iAnimatorListener listener) {
    imageView.setAnimateBoundsChange(animateBoundsChange, listener);
  }

  public void fitImage() {
    imageView.fitImage();
  }

  protected abstract aImagePanel createPanel(ImagePane cfg);
}
