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

package com.appnativa.rare.ui.painter;

import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iComposite;

/**
 * An interface for components that support overlay images.
 *
 * @author Don DeCoteau
 */
public interface iImagePainter extends iPlatformPainter {

  /**
   * Scaling type for image scaling
   */
  public static enum ScalingType {
    NEAREST_NEIGHBOR(0, false, false), BILINEAR(1, false, false), BICUBIC(2, false, false),
    NEAREST_NEIGHBOR_CACHED(3, true, false), BILINEAR_CACHED(4, true, false), BICUBIC_CACHED(5, true, false),
    PROGRESSIVE_NEAREST_NEIGHBOR(6, false, true), PROGRESSIVE_BILINEAR(7, false, true),
    PROGRESSIVE_BICUBIC(8, false, true), PROGRESSIVE_NEAREST_NEIGHBOR_CACHED(9, true, true),
    PROGRESSIVE_BILINEAR_CACHED(10, true, true), PROGRESSIVE_BICUBIC_CACHED(11, true, true);

    private final boolean cached;
    private final boolean progressive;
    private final int     value;

    ScalingType(int value, boolean cached, boolean progressive) {
      this.value       = value;
      this.cached      = cached;
      this.progressive = progressive;
    }

    public int getInterpolationType() {
      return value;
    }

    public int getValue() {
      return value;
    }

    public boolean isCached() {
      return cached;
    }

    public boolean isProgressive() {
      return progressive;
    }
  }

  /**
   * Sets the composite for the image
   *
   * @param composite the composite type for the image
   */
  void setComposite(iComposite composite);

  /**
   * Sets the image
   *
   * @param img the image
   */
  void setImage(UIImage img);

  /**
   * Sets the image alpha
   *
   * @param alpha the image alpha (a number between 0 and 1)
   */
  void setImageAlpha(float alpha);

  /**
   * Sets the render space for the image
   *
   * @param space the render space for the image
   */
  @Override
  void setRenderSpace(RenderSpace space);

  /**
   * Sets the render type for the image
   *
   * @param type the render type for the image
   */
  @Override
  void setRenderType(RenderType type);

  /**
   * Sets the scaling type for the image
   *
   * @param type the scaling type for the image
   */
  void setScalingType(ScalingType type);

  /**
   * sets the source location of the image (typically the url)
   *
   * @param location the source location of the image
   */
  void setSourceLocation(String location);

  /**
   * Gets the composite type for the image
   *
   * @return the composite type for the image
   */
  iComposite getComposite();

  /**
   * Get the current overlay image
   *
   * @return the current overlay image
   */
  UIImage getImage();

  /**
   * Returns the render type for the image
   *
   * @return the render type for the image
   */
  @Override
  RenderType getRenderType();

  /**
   * Gets the scaling type for the image
   *
   * @return the scaling type for the image
   */
  ScalingType getScalingType();

  /**
   * Gets the source location of the image (typically the url)
   *
   * @return the source location of the image
   */
  String getSourceLocation();
}
