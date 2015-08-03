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

import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;

public interface iImagePanel {
  boolean canZoomIn();

  boolean canZoomOut();

  void centerImage();

  void clear();

  void resetImage();

  void rotateLeft();

  void rotateRight();

  void zoom(int percent);

  void zoomIn();

  boolean isImageFitted();

  void zoomOut();

  void fitImage();

  boolean isFillViewport();

  void setFillViewport(boolean fillViewport);

  void setMovingOnlyAllowedWhenToLarge(boolean allowed);

  void setAutoScale(boolean autoScale);

  void setCenterInitially(boolean centerInitially);

  /**
   * Sets whether the panel should dispose the current image when a new image is
   * set. By default the panel does NOT dispose of the old image.
   *
   * @param dispose true to dispose; false otherwise
   */
  void setDisposeImageOnChange(boolean dispose);

  /**
   * Gets whether the panel should dispose the current image when a new image is
   * set. By default the panel does NOT dispose of the old image.
   *
   * @return true to dispose; false otherwise
   */
  boolean isDisposeImageOnChange();

  void setImage(UIImage image);

  void setImage(UIImage image, int width, int height);

  void setImageBorder(iPlatformBorder border);

  void setMaximumZoom(int maxZoom);

  void setMinimumZoom(int minZoom);

  void setMovingAllowed(boolean allowed);

  void setPreserveAspectRatio(boolean preserve);

  void setScalingType(ScalingType type);

  void setSelectionColor(UIColor color);

  void setUserSelectionAllowed(boolean allowed);

  void setZoomIncrementPercent(int zoomIncrement);

  void setZoomingAllowed(boolean allowed);

  iPlatformComponent getComponent();

  UIImage getImage();

  iPlatformBorder getImageBorder();

  /**
   * @return the imageHeight
   */
  int getImageHeight();

  int getImageRotation();

  /**
   * @return the imageWidth
   */
  int getImageWidth();

  iPlatformShape getSelection();

  String getSource();

  UIImage getRenderedImage();

  int getZoomPercent();

  boolean isAnimateBoundsChange();

  void setAnimateBoundsChange(boolean animateSizeChanges, iAnimatorListener listener);
}
