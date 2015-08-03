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

import com.appnativa.rare.widget.iWidget;

import java.io.IOException;

import java.net.URL;

public class UIResizerPanel extends ImagePanel {
  FakeImage fakeImage;

  public UIResizerPanel(iWidget context) {
    super(context);
    fakeImage = new FakeImage(50, 50);
    setImageEx(fakeImage, -1, -1);
    setMovingAllowed(true);
    setZoomingAllowed(true);
    setRotatingAllowed(false);
  }

  public void setBandBounds(int x, int y, int width, int height) {
    fakeImage.setSize(width, height);
    init(-1, -1, true);
    moveImage(x, y);
  }

  public UIRectangle getBanddBounds() {
    return new UIRectangle(destBounds);
  }

  @Override
  public void setImage(UIImage img) {}

  @Override
  public void setImage(UIImage img, int width, int height) {}

  @Override
  public void setImage(URL url) throws IOException {}

  static class FakeImage extends UIImage {
    int width;
    int height;

    public FakeImage(int width, int height) {}

    @Override
    public String toString() {
      return "FakeImage";
    }

    @Override
    public String getLocation() {
      return "FakeImage";
    }

    @Override
    public boolean isLoaded() {
      return true;
    }

    @Override
    public boolean isLoaded(iImageObserver is) {
      return true;
    }

    public void setSize(int width, int height) {
      this.width  = width;
      this.height = height;
    }

    @Override
    public int getHeight() {
      return height;
    }

    @Override
    public int getWidth() {
      return width;
    }

    @Override
    public UIImage getSlice(int x, int y, int width, int height) {
      return new FakeImage(width, height);
    }

    @Override
    public UIImage getSubimage(int x, int y, int width, int height) {
      return new FakeImage(width, height);
    }

    @Override
    public void dispose() {}
  }
}
