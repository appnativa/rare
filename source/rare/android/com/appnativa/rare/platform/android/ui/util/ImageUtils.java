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

package com.appnativa.rare.platform.android.ui.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;

import android.util.Log;

import android.view.View;

import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iPainter;

/*
*  Portions of code Licensed to the Apache Software Foundation (ASF) under one or more
*  contributor license agreements.  See the NOTICE file distributed with
*  this work for additional information regarding copyright ownership.
*  The ASF licenses this file to You under the Apache License, Version 2.0
*  (the "License"); you may not use this file except in compliance with
*  the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
 */
public class ImageUtils {
  // CLUT entry size, {Red, Green, Black}
  private static final int CLUT_ENTRY_SIZE = 3;

  public static void blurBitmap(Bitmap bm) {
    blurBitmap1(bm);
  }

  /**
   *   Blur the image
   * @param bm the bitmap to blur
   */
  public static void blurBitmap1(Bitmap bm) {
    int        k      = 5;
    int        alpha  = 0,
               pixval = 0,
               tsumB  = 0;
    final long t1, t2;

    t1 = System.currentTimeMillis();

    int       sumB;
    final int width  = bm.getWidth();
    final int height = bm.getHeight();

    // Do the blurring by moving in the horizontal direction
    for (int x = k; x < (width - k); x++) {
      for (int y = 0; y <= (height - 1); y++) {
        pixval = alpha = tsumB = 0;

        for (int i = x - k; i <= x + k; i++) {
          pixval = bm.getPixel(i, y);
          tsumB  += pixval & 0x000000FF;

          if (i == x) {
            alpha = (pixval & 0xFF000000) >> 24;
          }
        }

        sumB = (int) (tsumB / ((2 * k + 1)));
        bm.setPixel(x, y, Color.argb(alpha, sumB, sumB, sumB));
      }
    }    // end of both for loops

    // Do the blurring by moving in the vertical direction
    for (int x = 0; x <= (width - 1); x++) {
      for (int y = k; y < (height - k); y++) {
        pixval = alpha = tsumB = 0;

        for (int j = y - k; j <= y + k; j++) {
          pixval = bm.getPixel(x, j);
          tsumB  += pixval & 0x000000FF;

          if (j == y) {
            alpha = (pixval & 0xFF000000) >> 24;
          }
        }

        sumB = (int) (tsumB / ((2 * k + 1)));
        bm.setPixel(x, y, Color.argb(alpha, sumB, sumB, sumB));
      }
    }    // end of both for loops

    t2 = System.currentTimeMillis();
    Log.i("UIImage Processing", "Blurring Operation took " + (t2 - t1) + " ms");
  }

  /**
   * Blur the image
   */
  public static void blurBitmap2(Bitmap bm) {

    /**
     * BLURRING STARTS HERE
     */
    int k = 5;
    // int R,G,B;
    // R=G=B=0;
    // int factor=(2*k+1)*(2*k+1);
    int Aval   = 0,
        pixval = 0,
        tsumB  = 0;
    // Bitmap newBm=bm.copy(bm.getConfig(),true);
    final long t1, t2;

    t1 = System.currentTimeMillis();

    int sumB;

    // Do the blurring by moving in the horizontal direction
    for (int x = k; x < (bm.getWidth() - k); x++) {
      for (int y = 0; y <= (bm.getHeight() - 1); y++) {
        pixval = Aval = tsumB = 0;

        for (int i = x - k; i <= x + k; i++) {
          pixval = bm.getPixel(i, y);
          tsumB  += pixval & 0x000000FF;

          if (i == x) {
            Aval = (pixval & 0xFF000000) >> 24;
          }
        }

        sumB = (int) (tsumB / ((2 * k + 1)));
        bm.setPixel(x, y, Color.argb(Aval, sumB, sumB, sumB));
      }
    }    // end of both for loops

    // Do the blurring by moving in the vertical direction
    for (int x = 0; x <= (bm.getWidth() - 1); x++) {
      for (int y = k; y < (bm.getHeight() - k); y++) {
        pixval = Aval = tsumB = 0;

        for (int j = y - k; j <= y + k; j++) {
          pixval = bm.getPixel(x, j);
          tsumB  += pixval & 0x000000FF;

          if (j == y) {
            Aval = (pixval & 0xFF000000) >> 24;
          }
        }

        sumB = (int) (tsumB / ((2 * k + 1)));
        bm.setPixel(x, y, Color.argb(Aval, sumB, sumB, sumB));
      }
    }    // end of both for loops

    /**
     * BLURRING ENDS HERE
     */
    t2 = System.currentTimeMillis();
    Log.i("UIImage Processing", "Blurring Operation took " + (t2 - t1) + " ms");
  }

  public void blurImage2(Bitmap bm, int radius) {
    // Log time duration
    long timeDuration = System.currentTimeMillis();

    // Invalid radius
    if (radius < 1) {
      Log.i("Blur UIImage", "Invalid radius");

      return;
    }

    int[] pix = new int[bm.getWidth() * bm.getHeight()];

    bm.getPixels(pix, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());

    int w   = bm.getWidth();
    int h   = bm.getHeight();
    int wm  = w - 1;
    int hm  = h - 1;
    int wh  = w * h;
    int div = radius + radius + 1;
    int r[] = new int[wh];
    int g[] = new int[wh];
    int b[] = new int[wh];
    int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
    int vmin[] = new int[Math.max(w, h)];
    int divsum = (div + 1) >> 1;

    divsum *= divsum;

    int dv[] = new int[256 * divsum];

    for (i = 0; i < 256 * divsum; i++) {
      dv[i] = (i / divsum);
    }

    yw = yi = 0;

    int[][] stack = new int[div][3];
    int     stackpointer;
    int     stackstart;
    int[]   sir;
    int     rbs;
    int     r1 = radius + 1;
    int     routsum, goutsum, boutsum;
    int     rinsum, ginsum, binsum;

    for (y = 0; y < h; y++) {
      rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;

      for (i = -radius; i <= radius; i++) {
        p      = pix[yi + Math.min(wm, Math.max(i, 0))];
        sir    = stack[i + radius];
        sir[0] = (p & 0xff0000) >> 16;
        sir[1] = (p & 0x00ff00) >> 8;
        sir[2] = (p & 0x0000ff);
        rbs    = r1 - Math.abs(i);
        rsum   += sir[0] * rbs;
        gsum   += sir[1] * rbs;
        bsum   += sir[2] * rbs;

        if (i > 0) {
          rinsum += sir[0];
          ginsum += sir[1];
          binsum += sir[2];
        } else {
          routsum += sir[0];
          goutsum += sir[1];
          boutsum += sir[2];
        }
      }

      stackpointer = radius;

      for (x = 0; x < w; x++) {
        r[yi]      = dv[rsum];
        g[yi]      = dv[gsum];
        b[yi]      = dv[bsum];
        rsum       -= routsum;
        gsum       -= goutsum;
        bsum       -= boutsum;
        stackstart = stackpointer - radius + div;
        sir        = stack[stackstart % div];
        routsum    -= sir[0];
        goutsum    -= sir[1];
        boutsum    -= sir[2];

        if (y == 0) {
          vmin[x] = Math.min(x + radius + 1, wm);
        }

        p            = pix[yw + vmin[x]];
        sir[0]       = (p & 0xff0000) >> 16;
        sir[1]       = (p & 0x00ff00) >> 8;
        sir[2]       = (p & 0x0000ff);
        rinsum       += sir[0];
        ginsum       += sir[1];
        binsum       += sir[2];
        rsum         += rinsum;
        gsum         += ginsum;
        bsum         += binsum;
        stackpointer = (stackpointer + 1) % div;
        sir          = stack[(stackpointer) % div];
        routsum      += sir[0];
        goutsum      += sir[1];
        boutsum      += sir[2];
        rinsum       -= sir[0];
        ginsum       -= sir[1];
        binsum       -= sir[2];
        yi++;
      }

      yw += w;
    }

    for (x = 0; x < w; x++) {
      rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
      yp     = -radius * w;

      for (i = -radius; i <= radius; i++) {
        yi     = Math.max(0, yp) + x;
        sir    = stack[i + radius];
        sir[0] = r[yi];
        sir[1] = g[yi];
        sir[2] = b[yi];
        rbs    = r1 - Math.abs(i);
        rsum   += r[yi] * rbs;
        gsum   += g[yi] * rbs;
        bsum   += b[yi] * rbs;

        if (i > 0) {
          rinsum += sir[0];
          ginsum += sir[1];
          binsum += sir[2];
        } else {
          routsum += sir[0];
          goutsum += sir[1];
          boutsum += sir[2];
        }

        if (i < hm) {
          yp += w;
        }
      }

      yi           = x;
      stackpointer = radius;

      for (y = 0; y < h; y++) {
        pix[yi]    = 0xff000000 | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];
        rsum       -= routsum;
        gsum       -= goutsum;
        bsum       -= boutsum;
        stackstart = stackpointer - radius + div;
        sir        = stack[stackstart % div];
        routsum    -= sir[0];
        goutsum    -= sir[1];
        boutsum    -= sir[2];

        if (x == 0) {
          vmin[y] = Math.min(y + r1, hm) * w;
        }

        p            = x + vmin[y];
        sir[0]       = r[p];
        sir[1]       = g[p];
        sir[2]       = b[p];
        rinsum       += sir[0];
        ginsum       += sir[1];
        binsum       += sir[2];
        rsum         += rinsum;
        gsum         += ginsum;
        bsum         += binsum;
        stackpointer = (stackpointer + 1) % div;
        sir          = stack[stackpointer];
        routsum      += sir[0];
        goutsum      += sir[1];
        boutsum      += sir[2];
        rinsum       -= sir[0];
        ginsum       -= sir[1];
        binsum       -= sir[2];
        yi           += w;
      }
    }

    bm.setPixels(pix, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
    timeDuration = System.currentTimeMillis() - timeDuration;
    Log.i("UIImage Processing", "Blur Operation took " + (timeDuration) + " ms");
  }

  public static Bitmap changeColor(Bitmap bmp, int color) {
    int   w      = bmp.getWidth();
    int   h      = bmp.getHeight();
    int[] pixels = new int[w * h];

    bmp.getPixels(pixels, 0, w, 0, 0, w, h);

    int   pos   = 0;
    float hsv[] = new float[3];

    Color.colorToHSV(color, hsv);

    float hue = hsv[0];
    float sat = hsv[1];
    float val = hsv[2];

    hsv[0] = 0;
    hsv[1] = 0;
    hsv[2] = 0;

    for (int y = 0; y < h; ++y) {
      for (int x = 0; x < w; ++x) {
        final int c = pixels[pos + x];
        final int a = (c >> 24) & 0xff;

        Color.colorToHSV(c, hsv);
        hsv[0]          += hue;
        hsv[1]          += sat;
        hsv[2]          += val;
        pixels[pos + x] = Color.HSVToColor(a, hsv);
      }

      pos += w;
    }

    if (!bmp.isMutable()) {
      bmp = bmp.copy(bmp.getConfig(), true);
    }

    bmp.setPixels(pixels, 0, w, 0, 0, w, h);

    return bmp;
  }

  public static void clearArea(Canvas g, int x, int y, int width, int height) {
    g.save(Canvas.CLIP_SAVE_FLAG);
    g.clipRect(x, y, x + width, y + height);
    g.drawColor(0, Mode.CLEAR);
    g.restore();
  }

  public static Bitmap convertToGrayscale(Bitmap bm, float saturation) {
    Bitmap      gbm = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas      c   = new Canvas(gbm);
    Paint       p   = new Paint();
    ColorMatrix cm  = new ColorMatrix();

    cm.setSaturation(saturation);

    ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(cm);

    p.setColorFilter(cmcf);
    c.drawBitmap(bm, 0, 0, p);

    return gbm;
  }

  public static Bitmap createBitmap(View v) {
    Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas g = new Canvas(b);

    v.draw(g);

    return b;
  }

  public static Bitmap createCompatibleBitmap(int width, int height) {
    return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
  }

  public static UIImage createCompatibleImage(int width, int height) {
    return new UIImage(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
  }

  public static Bitmap createDisabledImage(Bitmap image) {
    return convertToGrayscale(image, 0f);
  }

  public static UIImage createImage(iPlatformIcon icon) {
    Bitmap bm = Bitmap.createBitmap(icon.getIconWidth(), icon.getIconHeight(), Bitmap.Config.ARGB_8888);
    Canvas g  = new Canvas(bm);

    icon.paint(g, 0, 0, icon.getIconWidth(), icon.getIconHeight());

    return new UIImage(bm);
  }

  public static UIImage createImage(iPlatformComponent c) {
    return createImage(c.getView());
  }

  public static UIImage createImage(View v) {
    int w = v.getWidth();
    int h = v.getHeight();

    if ((w == 0) || (h == 0)) {
      return null;
    }

    Bitmap b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    Canvas g = new Canvas(b);

    v.draw(g);

    return new UIImage(b);
  }

  public static UIImage createImage(iPlatformComponent c, int x, int y, int width, int height, iPainter bg) {
    return createImage(c.getView(), x, y, width, height, bg);
  }

  public static UIImage createImage(View v, int x, int y, int width, int height, iPainter bg) {
    Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

    if ((x == 0) && (y == 0) && (width == b.getWidth()) && (height == b.getHeight())) {
      return new UIImage(b);
    }

    Canvas g = new Canvas(b);

    if (bg != null) {
      iPlatformGraphics pg = new AndroidGraphics(b);

      bg.paint(pg, 0, 0, width, height, iPainter.UNKNOWN);
      pg.dispose();
    }

    g.translate(x, y);
    v.draw(g);

    return new UIImage(b);
  }

  public static Bitmap createReflection(Bitmap originalImage, int y, int rheight, float opacity, int gap) {
    int    width  = originalImage.getWidth();
    int    height = originalImage.getHeight();
    Matrix matrix = new Matrix();

    matrix.preScale(1, -1);

    // Create a Bitmap with the flip matix applied to it.
    // We only want the bottom half of the image
    Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, rheight, width, rheight, matrix, false);
    // Create a new bitmap with same width but taller to fit reflection
    Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + rheight), Config.ARGB_8888);
    // Create a new Canvas with the bitmap that's big enough for
    // the image plus gap plus reflection
    Canvas canvas = new Canvas(bitmapWithReflection);

    // Draw in the original image
    canvas.drawBitmap(originalImage, 0, 0, null);

    // Draw in the gap
    Paint deafaultPaint = new Paint();

    canvas.drawRect(0, height, width, height + gap, deafaultPaint);
    // Draw in the reflection
    canvas.drawBitmap(reflectionImage, 0, height + gap, null);

    // Create a shader that is a linear gradient that covers the reflection
    Paint          paint  = new Paint();
    LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + gap,
                              0x70ffffff, 0x00ffffff, TileMode.CLAMP);

    // Set the paint to use this shader (linear gradient)
    paint.setShader(shader);
    // Set the Transfer mode to be porter duff and destination in
    paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
    // Draw a rectangle using the paint with our linear gradient
    canvas.drawRect(0, height, width, bitmapWithReflection.getHeight(), paint);

    return bitmapWithReflection;
  }

  public static void addReflection(Bitmap originalImage, int y, int rheight, float opacity, int gap) {
    int    width  = originalImage.getWidth();
    int    height = originalImage.getHeight();
    Matrix matrix = new Matrix();

    matrix.preScale(1, -1);

    // Create a Bitmap with the flip matix applied to it.
    // We only want the bottom half of the image
    Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, rheight, width, rheight, matrix, false);
    // Create a new Canvas with the bitmap that's big enough for
    // the image plus gap plus reflection
    Canvas canvas = new Canvas(originalImage);
    // Draw in the gap
    Paint deafaultPaint = new Paint();

    canvas.drawRect(0, height, width, height + gap - rheight, deafaultPaint);
    // Draw in the reflection
    canvas.drawBitmap(reflectionImage, 0, height + gap - rheight, null);

    // Create a shader that is a linear gradient that covers the reflection
    Paint          paint  = new Paint();
    LinearGradient shader = new LinearGradient(0, height - rheight, 0, height, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);

    // Set the paint to use this shader (linear gradient)
    paint.setShader(shader);
    // Set the Transfer mode to be porter duff and destination in
    paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
    // Draw a rectangle using the paint with our linear gradient
    canvas.drawRect(0, height - rheight, width, height, paint);
  }

  /**
   *     Convert a TS 131.102 image instance of code scheme '11' into Bitmap
   *     @param data The raw data
   *     @param length The length of image body
   *     @return The bitmap
   * @author Igor V. Stolyarov
   */
  public static Bitmap parseToBnW(byte[] data, int length) {
    int   valueIndex  = 0;
    int   width       = data[valueIndex++] & 0xFF;
    int   height      = data[valueIndex++] & 0xFF;
    int   numOfPixels = width * height;
    int[] pixels      = new int[numOfPixels];
    int   pixelIndex  = 0;
    int   bitIndex    = 7;
    byte  currentByte = 0x00;

    while(pixelIndex < numOfPixels) {
      // reassign data and index for every byte (8 bits).
      if (pixelIndex % 8 == 0) {
        currentByte = data[valueIndex++];
        bitIndex    = 7;
      }

      pixels[pixelIndex++] = bitToBnW((currentByte >> bitIndex--) & 0x01);
    }

    if (pixelIndex != numOfPixels) {
      Log.d("IconLoader", "parseToBnW; size error");
    }

    return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
  }

  /**
   * a TS 131.102 image instance of code scheme '11' into color Bitmap
   *
   * @param data The raw data
   * @param length the length of image body
   * @param transparency with or without transparency
   * @param clut coulor lookup table
   * @return The color bitmap
   * @author Igor V. Stolyarov
   */
  public static Bitmap parseToRGB(byte[] data, int length, boolean transparency, byte[] clut) {
    int valueIndex       = 0;
    int width            = data[valueIndex++] & 0xFF;
    int height           = data[valueIndex++] & 0xFF;
    int bitsPerImg       = data[valueIndex++] & 0xFF;
    int numOfClutEntries = data[valueIndex++] & 0xFF;

    if (true == transparency) {
      clut[numOfClutEntries - 1] = Color.TRANSPARENT;
    }

    int   numOfPixels = width * height;
    int[] pixels      = new int[numOfPixels];

    valueIndex = 6;

    int     pixelIndex      = 0;
    int     bitsStartOffset = 8 - bitsPerImg;
    int     bitIndex        = bitsStartOffset;
    byte    currentByte     = data[valueIndex++];
    int     mask            = getMask(bitsPerImg);
    boolean bitsOverlaps    = (8 % bitsPerImg == 0);

    while(pixelIndex < numOfPixels) {
      // reassign data and index for every byte (8 bits).
      if (bitIndex < 0) {
        currentByte = data[valueIndex++];
        bitIndex    = bitsOverlaps
                      ? (bitsStartOffset)
                      : (bitIndex * -1);
      }

      int clutEntry = ((currentByte >> bitIndex) & mask);
      int clutIndex = clutEntry * CLUT_ENTRY_SIZE;

      pixels[pixelIndex++] = Color.rgb(clut[clutIndex], clut[clutIndex + 1], clut[clutIndex + 2]);
      bitIndex             -= bitsPerImg;
    }

    return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
  }

  public static Bitmap rotate(Bitmap bmp, int deg) {
    int    w   = bmp.getWidth();
    int    h   = bmp.getHeight();
    Matrix mtx = new Matrix();

    mtx.postRotate(deg);

    return Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
  }

  public static Bitmap rotateLeft(Bitmap bmp) {
    return rotate(bmp, -90);
  }

  public static Bitmap rotateRight(Bitmap bmp) {
    return rotate(bmp, 90);
  }

  public static UIImage getScaledInstance(UIImage img, int width, int height, Object scaling, boolean progressive) {
    return new UIImage(Bitmap.createScaledBitmap(img.getBitmap(), width, height, progressive));
  }

  public static Matrix getXFlipTransform(int width) {
    Matrix m = new Matrix();

    m.preScale(-1.0f, 1.0f);
    m.postTranslate(width, 0);

    return m;
  }

  public static Matrix getYFlipTransform(int height) {
    Matrix m = new Matrix();

    m.preScale(1.0f, -1.0f);
    m.postTranslate(0, height);

    return m;
  }

  /**
   * Decode one bit to a black and white color:
   * 0 is black
   * 1 is white
   * @param bit to decode
   * @return RGB color
   * @author Igor V. Stolyarov
   */
  private static int bitToBnW(int bit) {
    if (bit == 1) {
      return Color.WHITE;
    } else {
      return Color.BLACK;
    }
  }

  /**
   * Calculate bit mask for a given number of bits. The mask should enable to
   * make a bitwise and to the given number of bits.
   * @param numOfBits number of bits to calculate mask for.
   * @return bit mask
   * @author Igor V. Stolyarov
   */
  private static int getMask(int numOfBits) {
    int mask = 0x00;

    switch(numOfBits) {
      case 1 :
        mask = 0x01;

        break;

      case 2 :
        mask = 0x03;

        break;

      case 3 :
        mask = 0x07;

        break;

      case 4 :
        mask = 0x0F;

        break;

      case 5 :
        mask = 0x1F;

        break;

      case 6 :
        mask = 0x3F;

        break;

      case 7 :
        mask = 0x7F;

        break;

      case 8 :
        mask = 0xFF;

        break;
    }

    return mask;
  }

  public static void addReflection(Bitmap bmp, int height, float opacity, int gap) {}

  public static Bitmap createImageIfNecessary(Bitmap bitmap, int width, int height, Config cfg) {
    if ((bitmap == null) || (bitmap.getWidth() != width) || (bitmap.getHeight() != height)) {
      bitmap = Bitmap.createBitmap(width, height, cfg);
    }

    return bitmap;
  }
}
