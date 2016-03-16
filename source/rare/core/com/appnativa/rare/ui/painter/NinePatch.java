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

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageHelper;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.aUIImage;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iPlatformGraphics;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a 9-Patch bitmap.
 * Ported from the Android project
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class NinePatch implements iImageObserver {
  public static final String EXTENSION_9PATCH = ".9.png";
  UIColor                    newColor;
  UIColor                    oldColor;
  private int[]              column;
  private List<UIRectangle>  mFixed;
  private Pair<Integer>      mHorizontalPadding;
  private List<UIRectangle>  mHorizontalPatches;
  private float              mHorizontalPatchesSum;
  private boolean            mHorizontalStartWithPatch;
  protected aUIImage         mImage;
  private List<UIRectangle>  mPatches;
  private int                mRemainderHorizontal;
  private int                mRemainderVertical;
  private Pair<Integer>      mVerticalPadding;
  private List<UIRectangle>  mVerticalPatches;
  private float              mVerticalPatchesSum;
  private boolean            mVerticalStartWithPatch;
  private int[]              row;

  public NinePatch(aUIImage image) {
    this(image, null, null);
  }

  protected NinePatch() {}

  public NinePatch(aUIImage image, UIColor newColor, UIColor oldColor) {
    mImage = image;

    if (image.isImageLoaded(this)) {
      if (newColor != null) {
        changeNinePatchColor(newColor, oldColor);
      }

      findPatches();
    } else {
      this.newColor = newColor;
      this.oldColor = oldColor;
    }
  }

  public void changeNinePatchColor(UIColor newColor, UIColor oldColor) {
    if ((newColor == null) || (oldColor == null)) {
      return;
    }

    if ((mImage == null) ||!mImage.isImageLoaded(this)) {
      this.newColor = newColor;
      this.oldColor = oldColor;

      return;
    }

    int       w  = mImage.getWidth();
    int       h  = mImage.getHeight();
    int       nc = newColor.getRGB();
    final int nr = (nc >> 16) & 0xff;
    final int ng = (nc >> 8) & 0xff;
    final int nb = nc & 0xff;
    int       oc = oldColor.getRGB();
    final int or = (oc >> 16) & 0xff;
    final int og = (oc >> 8) & 0xff;
    final int ob = oc & 0xff;

    for (int y = 1; y < h; ++y) {
      for (int x = 1; x < w; ++x) {
        final int c = mImage.getPixel(x, y);
        final int a = (c >> 24) & 0xff;
        final int r = (c >> 16) & 0xff;
        final int g = (c >> 8) & 0xff;
        final int b = c & 0xff;

        if ((a == 0) || (Math.abs(r - or) > 5) || (Math.abs(g - og) > 5) || (Math.abs(b - ob) > 5)) {
          continue;
        }

        mImage.setPixel(x, y, (a << 24) | (nr << 16) | (ng << 8) | nb);
      }
    }

    this.newColor = null;
    this.oldColor = null;
  }

  public void draw(iPlatformGraphics g, float x, float y, float scaledWidth, float scaledHeight) {
    if ((mImage == null) || (scaledWidth <= 1) || (scaledHeight <= 1) || (mPatches == null)) {
      return;
    }

    if (mPatches.size() == 0) {
      g.drawImage(mImage, x, y, scaledWidth, scaledHeight);

      return;
    }

    g.translate(x, y);
    x = y = 0;
    computePatches((int) Math.ceil(scaledWidth), (int) Math.ceil(scaledHeight));

    int     fixedIndex      = 0;
    int     horizontalIndex = 0;
    int     verticalIndex   = 0;
    int     patchIndex      = 0;
    boolean hStretch;
    boolean vStretch;
    float   vWeightSum = 1.0f;
    float   vRemainder = mRemainderVertical;

    vStretch = mVerticalStartWithPatch;

    UIRectangle src = new UIRectangle();
    UIRectangle dst = new UIRectangle();

    while(y < scaledHeight - 1) {
      hStretch = mHorizontalStartWithPatch;

      int   height     = 0;
      float vExtra     = 0.0f;
      float hWeightSum = 1.0f;
      float hRemainder = mRemainderHorizontal;

      while(x < scaledWidth - 1) {
        UIRectangle r;

        if (!vStretch) {
          if (hStretch) {
            r = mHorizontalPatches.get(horizontalIndex++);

            float extra = r.width / mHorizontalPatchesSum;
            int   width = (int) (extra * hRemainder / hWeightSum);

            hWeightSum -= extra;
            hRemainder -= width;
            src.setBounds(r);
            dst.set(x, y, width, r.height);
            g.drawImage(mImage, src, dst, null, null);
            x += width;
          } else {
            r = mFixed.get(fixedIndex++);
            src.setBounds(r);
            dst.set(x, y, r.width, r.height);
            g.drawImage(mImage, src, dst, null, null);
            x += r.width;
          }

          height = (int) r.height;
        } else {
          if (hStretch) {
            r      = mPatches.get(patchIndex++);
            vExtra = r.height / mVerticalPatchesSum;
            height = (int) (vExtra * vRemainder / vWeightSum);

            float extra = r.width / mHorizontalPatchesSum;
            int   width = (int) (extra * hRemainder / hWeightSum);

            hWeightSum -= extra;
            hRemainder -= width;
            src.setBounds(r);
            dst.set(x, y, width, height);
            g.drawImage(mImage, src, dst, null, null);
            x += width;
          } else {
            r      = mVerticalPatches.get(verticalIndex++);
            vExtra = r.height / mVerticalPatchesSum;
            height = (int) (vExtra * vRemainder / vWeightSum);
            src.setBounds(r);
            dst.set(x, y, r.width, height);
            g.drawImage(mImage, src, dst, null, null);
            x += r.width;
          }
        }

        hStretch = !hStretch;
      }

      x = 0;
      y += height;

      if (vStretch) {
        vWeightSum -= vExtra;
        vRemainder -= height;
      }

      vStretch = !vStretch;
    }
  }

  @Override
  public void imageLoaded(UIImage image) {
    if (newColor != null) {
      changeNinePatchColor(newColor, oldColor);
    }

    findPatches();
  }

  public boolean isImageLoaded(iImageObserver is) {
    return mImage.isImageLoaded(is);
  }

  public static NinePatch load(URL url) throws IOException {
    return new NinePatch(UIImageHelper.createImage(url, true, 1));
  }

  public int getHeight() {
    return mImage.getHeight() - 2;
  }

  public aUIImage getImage() {
    return mImage;
  }

  /**
   * Stores the padding into the passed in array
   *
   * @param padding array of left, top, right, bottom padding
   */
  public void getPadding(int[] padding) {
    padding[0] = mHorizontalPadding.mFirst;     // left
    padding[2] = mHorizontalPadding.mSecond;    // right
    padding[1] = mVerticalPadding.mFirst;       // top
    padding[3] = mVerticalPadding.mSecond;      // bottom
  }

  public int getWidth() {
    return mImage.getWidth() - 2;
  }

  void computePatches(int scaledWidth, int scaledHeight) {
    boolean measuredWidth       = false;
    boolean endRow              = true;
    int     remainderHorizontal = 0;
    int     remainderVertical   = 0;

    if (mFixed.size() > 0) {
      int start = (int) mFixed.get(0).y;

      for (UIRectangle rect : mFixed) {
        if (rect.y > start) {
          endRow        = true;
          measuredWidth = true;
        }

        if (!measuredWidth) {
          remainderHorizontal += rect.width;
        }

        if (endRow) {
          remainderVertical += rect.height;
          endRow            = false;
          start             = (int) rect.y;
        }
      }
    }

    mRemainderHorizontal  = scaledWidth - remainderHorizontal;
    mRemainderVertical    = scaledHeight - remainderVertical;
    mHorizontalPatchesSum = 0;

    if (mHorizontalPatches.size() > 0) {
      int start = -1;

      for (UIRectangle rect : mHorizontalPatches) {
        if (rect.x > start) {
          mHorizontalPatchesSum += rect.width;
          start                 = (int) rect.x;
        }
      }
    } else {
      int start = -1;

      for (UIRectangle rect : mPatches) {
        if (rect.x > start) {
          mHorizontalPatchesSum += rect.width;
          start                 = (int) rect.x;
        }
      }
    }

    mVerticalPatchesSum = 0;

    if (mVerticalPatches.size() > 0) {
      int start = -1;

      for (UIRectangle rect : mVerticalPatches) {
        if (rect.y > start) {
          mVerticalPatchesSum += rect.height;
          start               = (int) rect.y;
        }
      }
    } else {
      int start = -1;

      for (UIRectangle rect : mPatches) {
        if (rect.y > start) {
          mVerticalPatchesSum += rect.height;
          start               = (int) rect.y;
        }
      }
    }
  }

  public List<UIRectangle> getPatches() {
    return mFixed;
  }

  private void findPatches() {
    int width  = mImage.getWidth();
    int height = mImage.getHeight();

    row    = mImage.getPixels(row, 0, 0, width, 1);
    column = mImage.getPixels(column, 0, 0, 1, height);

    boolean[]                 result = new boolean[1];
    Pair<List<Pair<Integer>>> left   = getPatches(column, result);

    mVerticalStartWithPatch = result[0];
    result                  = new boolean[1];

    Pair<List<Pair<Integer>>> top = getPatches(row, result);

    mHorizontalStartWithPatch = result[0];
    mFixed                    = getRectangles(left.mFirst, top.mFirst);
    mPatches                  = getRectangles(left.mSecond, top.mSecond);

    if (mFixed.size() > 0) {
      mHorizontalPatches = getRectangles(left.mFirst, top.mSecond);
      mVerticalPatches   = getRectangles(left.mSecond, top.mFirst);
    } else {
      if (((List)top.mFirst).size() > 0) {
        mHorizontalPatches = new ArrayList<UIRectangle>(0);
        mVerticalPatches   = getVerticalRectangles(top.mFirst);
      } else if (((List)left.mFirst).size() > 0) {
        mHorizontalPatches = getHorizontalRectangles(left.mFirst);
        mVerticalPatches   = new ArrayList<UIRectangle>(0);
      } else {
        mHorizontalPatches = mVerticalPatches = new ArrayList<UIRectangle>(0);
      }
    }

    row                = mImage.getPixels(row, 0, height - 1, width, 1);
    column             = mImage.getPixels(column, width - 1, 0, 1, height);
    top                = getPatches(row, result);
    mHorizontalPadding = getPadding(top.mFirst);
    left               = getPatches(column, result);
    mVerticalPadding   = getPadding(left.mFirst);
  }

  private List<UIRectangle> getHorizontalRectangles(List<Pair<Integer>> leftPairs) {
    List<UIRectangle> rectangles = new ArrayList<UIRectangle>();

    for (Pair<Integer> left : leftPairs) {
      int y      = left.mFirst;
      int height = left.mSecond - left.mFirst;

      rectangles.add(new UIRectangle(1, y, mImage.getWidth() - 2, height));
    }

    return rectangles;
  }

  private Pair<Integer> getPadding(List<Pair<Integer>> pairs) {
    if (pairs.size() == 0) {
      return new Pair<Integer>(0, 0);
    } else if (pairs.size() == 1) {
      if (pairs.get(0).mFirst == 1) {
        return new Pair<Integer>(pairs.get(0).mSecond - pairs.get(0).mFirst, 0);
      } else {
        return new Pair<Integer>(0, pairs.get(0).mSecond - pairs.get(0).mFirst);
      }
    } else {
      int index = pairs.size() - 1;

      return new Pair<Integer>(pairs.get(0).mSecond - pairs.get(0).mFirst,
                      pairs.get(index).mSecond - pairs.get(index).mFirst);
    }
  }

  private Pair<List<Pair<Integer>>> getPatches(int[] pixels, boolean[] startWithPatch) {
    int                 lastIndex = 1;
    int                 lastPixel = pixels[1];
    boolean             first     = true;
    List<Pair<Integer>> fixed     = new ArrayList<Pair<Integer>>();
    List<Pair<Integer>> patches   = new ArrayList<Pair<Integer>>();

    for (int i = 1; i < pixels.length - 1; i++) {
      int pixel = pixels[i];

      if (pixel != lastPixel) {
        if (lastPixel == 0xFF000000) {
          if (first) {
            startWithPatch[0] = true;
          }

          patches.add(new Pair<Integer>(lastIndex, i));
        } else {
          fixed.add(new Pair<Integer>(lastIndex, i));
        }

        first     = false;
        lastIndex = i;
        lastPixel = pixel;
      }
    }

    if (lastPixel == 0xFF000000) {
      if (first) {
        startWithPatch[0] = true;
      }

      patches.add(new Pair<Integer>(lastIndex, pixels.length - 1));
    } else {
      fixed.add(new Pair<Integer>(lastIndex, pixels.length - 1));
    }

    if (patches.size() == 0) {
      patches.add(new Pair<Integer>(1, pixels.length - 1));
      startWithPatch[0] = true;
      fixed.clear();
    }

    return new Pair<List<Pair<Integer>>>(fixed, patches);
  }

  private List<UIRectangle> getRectangles(List<Pair<Integer>> leftPairs, List<Pair<Integer>> topPairs) {
    List<UIRectangle> rectangles = new ArrayList<UIRectangle>();

    for (Pair<Integer> left : leftPairs) {
      int y      = left.mFirst;
      int height = left.mSecond - left.mFirst;

      for (Pair<Integer> top : topPairs) {
        int x     = top.mFirst;
        int width = top.mSecond - top.mFirst;

        rectangles.add(new UIRectangle(x, y, width, height));
      }
    }

    return rectangles;
  }

  private List<UIRectangle> getVerticalRectangles(List<Pair<Integer>> topPairs) {
    List<UIRectangle> rectangles = new ArrayList<UIRectangle>();

    for (Pair<Integer> top : topPairs) {
      int x     = top.mFirst;
      int width = top.mSecond - top.mFirst;

      rectangles.add(new UIRectangle(x, 1, width, mImage.getHeight() - 2));
    }

    return rectangles;
  }

  static class Pair<E> {
    E mFirst;
    E mSecond;

    Pair(E first, E second) {
      mFirst  = first;
      mSecond = second;
    }

    @Override
    public String toString() {
      return "Pair[" + mFirst + ", " + mSecond + "]";
    }
  }
}
