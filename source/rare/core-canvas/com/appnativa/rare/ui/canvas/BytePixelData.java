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

package com.appnativa.rare.ui.canvas;

import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.canvas.iContext.iImageData;

import java.nio.ByteBuffer;

public class BytePixelData implements iImageData {
  public Uint8ClampedArray data;
  public final int         height;
  public final int         width;
  byte[]                   byteData;

  public BytePixelData(ByteBuffer bb, int width, int height) {
    this.data   = new Uint8ClampedArray(bb);
    this.width  = width;
    this.height = height;
  }

  @Override
  public void updateImage(UIImage image, int dx, int dy, int dirtyWidth, int dirtyHeight) {
    image.setImageBytes(dx, dy, dirtyWidth, dirtyHeight, data.buffer);
  }

  @Override
  public Uint8ClampedArray getData() {
    return data;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }
}
