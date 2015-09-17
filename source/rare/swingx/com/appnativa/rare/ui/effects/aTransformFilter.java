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

package com.appnativa.rare.ui.effects;

/*
Copyright 2006 Jerry Huxtable

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 * An abstract superclass for filters which distort images in some way. The subclass only needs to override
 * two methods to provide the mapping between source and destination pixels.
 */
public abstract class aTransformFilter extends aBufferedImageOp {

  /**
   * Use bilinear interpolation.
   */
  public final static int BILINEAR = 1;

  /**
   * Clamp pixels to the image edges.
   */
  public final static int CLAMP = 1;

  /**
   * Use nearest-neighbout interpolation.
   */
  public final static int NEAREST_NEIGHBOUR = 0;

  /**
   * Wrap pixels off the edge onto the oppsoite edge.
   */
  public final static int WRAP = 2;

  /**
   * Treat pixels off the edge as zero.
   */
  public final static int ZERO = 0;

  /**
   * The action to take for pixels off the image edge.
   */
  protected int edgeAction = ZERO;

  /**
   * The type of interpolation to use.
   */
  protected int interpolation = BILINEAR;

  /**
   * The input image rectangle.
   */
  protected Rectangle originalSpace;

  /**
   * The output image rectangle.
   */
  protected Rectangle transformedSpace;

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dst) {
    final int width  = src.getWidth();
    final int height = src.getHeight();

    originalSpace    = new Rectangle(0, 0, width, height);
    transformedSpace = new Rectangle(0, 0, width, height);
    transformSpace(transformedSpace);

    if (dst == null) {
      ColorModel dstCM = src.getColorModel();

      dst = new BufferedImage(dstCM,
                              dstCM.createCompatibleWritableRaster(transformedSpace.width, transformedSpace.height),
                              dstCM.isAlphaPremultiplied(), null);
    }

    final int[] inPixels = getRGB(src, 0, 0, width, height, null);

    if (interpolation == NEAREST_NEIGHBOUR) {
      return filterPixelsNN(dst, width, height, inPixels, transformedSpace);
    }

    final int   srcWidth   = width;
    final int   srcHeight  = height;
    final int   srcWidth1  = width - 1;
    final int   srcHeight1 = height - 1;
    final int   outWidth   = transformedSpace.width;
    final int   outHeight  = transformedSpace.height;
    final int[] outPixels  = (allocator == null)
                             ? new int[outWidth]
                             : allocator.newIntArray(outWidth, true);
    int         outX, outY;

    outX = transformedSpace.x;
    outY = transformedSpace.y;

    float[] out = new float[2];

    for (int y = 0; y < outHeight; y++) {
      for (int x = 0; x < outWidth; x++) {
        transformInverse(outX + x, outY + y, out);

        int   srcX    = (int) Math.floor(out[0]);
        int   srcY    = (int) Math.floor(out[1]);
        float xWeight = out[0] - srcX;
        float yWeight = out[1] - srcY;
        int   nw, ne, sw, se;

        if ((srcX >= 0) && (srcX < srcWidth1) && (srcY >= 0) && (srcY < srcHeight1)) {
          // Easy case, all corners are in the image
          int i = srcWidth * srcY + srcX;

          nw = inPixels[i];
          ne = inPixels[i + 1];
          sw = inPixels[i + srcWidth];
          se = inPixels[i + srcWidth + 1];
        } else {
          // Some of the corners are off the image
          nw = getPixel(inPixels, srcX, srcY, srcWidth, srcHeight);
          ne = getPixel(inPixels, srcX + 1, srcY, srcWidth, srcHeight);
          sw = getPixel(inPixels, srcX, srcY + 1, srcWidth, srcHeight);
          se = getPixel(inPixels, srcX + 1, srcY + 1, srcWidth, srcHeight);
        }

        outPixels[x] = ImageMath.bilinearInterpolate(xWeight, yWeight, nw, ne, sw, se);
      }

      setRGB(dst, 0, y, transformedSpace.width, 1, outPixels);
    }

    return dst;
  }

  /**
   * Set the action to perform for pixels off the edge of the image.
   * @param edgeAction one of ZERO, CLAMP or WRAP
   * @see #getEdgeAction
   */
  public void setEdgeAction(int edgeAction) {
    this.edgeAction = edgeAction;
  }

  /**
   * Set the type of interpolation to perform.
   * @param interpolation one of NEAREST_NEIGHBOUR or BILINEAR
   * @see #getInterpolation
   */
  public void setInterpolation(int interpolation) {
    this.interpolation = interpolation;
  }

  /**
   * Get the action to perform for pixels off the edge of the image.
   * @return one of ZERO, CLAMP or WRAP
   * @see #setEdgeAction
   */
  public int getEdgeAction() {
    return edgeAction;
  }

  /**
   * Get the type of interpolation to perform.
   * @return one of NEAREST_NEIGHBOUR or BILINEAR
   * @see #setInterpolation
   */
  public int getInterpolation() {
    return interpolation;
  }

  protected BufferedImage filterPixelsNN(BufferedImage dst, int width, int height, int[] inPixels,
          Rectangle transformedSpace) {
    final int   srcWidth  = width;
    final int   srcHeight = height;
    final int   outWidth  = transformedSpace.width;
    final int   outHeight = transformedSpace.height;
    int         outX, outY, srcX, srcY;
    final int[] outPixels = (allocator == null)
                            ? new int[outWidth]
                            : allocator.newIntArray(outWidth, true);

    outX = transformedSpace.x;
    outY = transformedSpace.y;

    final int[]   rgb = new int[4];
    final float[] out = new float[2];

    for (int y = 0; y < outHeight; y++) {
      for (int x = 0; x < outWidth; x++) {
        transformInverse(outX + x, outY + y, out);
        srcX = (int) out[0];
        srcY = (int) out[1];

        // int casting rounds towards zero, so we check out[0] < 0, not srcX < 0
        if ((out[0] < 0) || (srcX >= srcWidth) || (out[1] < 0) || (srcY >= srcHeight)) {
          int p;

          switch(edgeAction) {
            case ZERO :
            default :
              p = 0;

              break;

            case WRAP :
              p = inPixels[(ImageMath.mod(srcY, srcHeight) * srcWidth) + ImageMath.mod(srcX, srcWidth)];

              break;

            case CLAMP :
              p = inPixels[(ImageMath.clamp(srcY, 0, srcHeight - 1) * srcWidth) + ImageMath.clamp(srcX, 0, srcWidth - 1)];

              break;
          }

          outPixels[x] = p;
        } else {
          int i = srcWidth * srcY + srcX;

          rgb[0]       = inPixels[i];
          outPixels[x] = inPixels[i];
        }
      }

      setRGB(dst, 0, y, transformedSpace.width, 1, outPixels);
    }

    return dst;
  }

  /**
   * Inverse transform a point. This method needs to be overriden by all subclasses.
   * @param x the X position of the pixel in the output image
   * @param y the Y position of the pixel in the output image
   * @param out the position of the pixel in the input image
   */
  protected abstract void transformInverse(int x, int y, float[] out);

  /**
   * Forward transform a rectangle. Used to determine the size of the output image.
   * @param rect the rectangle to transform
   */
  protected void transformSpace(Rectangle rect) {}

  final private int getPixel(int[] pixels, int x, int y, int width, int height) {
    if ((x < 0) || (x >= width) || (y < 0) || (y >= height)) {
      switch(edgeAction) {
        case ZERO :
        default :
          return 0;

        case WRAP :
          return pixels[(ImageMath.mod(y, height) * width) + ImageMath.mod(x, width)];

        case CLAMP :
          return pixels[(ImageMath.clamp(y, 0, height - 1) * width) + ImageMath.clamp(x, 0, width - 1)];
      }
    }

    return pixels[y * width + x];
  }

  private iAllocator allocator;

  /**
   * @return the allocator
   */
  public iAllocator getAllocator() {
    return allocator;
  }

  /**
   * @param allocator the allocator to set
   */
  public void setAllocator(iAllocator allocator) {
    this.allocator = allocator;
  }

  public static interface iAllocator {
    int[] newIntArray(int size, boolean canbeLarger);
  }
}
