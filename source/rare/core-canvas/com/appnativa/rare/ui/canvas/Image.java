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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.scripting.WidgetContext;
import com.appnativa.rare.scripting.iScriptingContextSupport;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.canvas.iContext.iImageElement;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public class Image implements iImageElement, iImageObserver, iScriptingContextSupport {
  private iWidget       context;
  private UIImage       image;
  private Object        onabort;
  private Object        onerror;
  private Object        onload;
  private int[]         pixelData;
  private String        src;
  private WidgetContext scriptingContext;

  public Image() {}

  public Image(String src) {
    setSrc(src);
  }

  public Image(UIImage image) {
    this.image = image;
  }

  public Image(int width, int height) {
    image = new UIImage(width, height);
  }

  public void blur() {
    image.blurImage();
  }

  public void addReflection(int y, int height, float opacity, int gap) {
    if (image != null) {
      image.addReflectionImage(y, height, opacity, gap);
    }
  }

  @Override
  public void imageLoaded(UIImage image) {}

  public void set(int index, int value) {
    final int w = image.getWidth();

    image.setPixel(index / w, index % (w + 1), value);
  }

  /**
   * @param context the context to set
   */
  public void setContext(iWidget context) {
    this.context = context;
  }

  /**
   * @param onabort the onabort to set
   */
  public void setOnabort(Object onabort) {
    this.onabort = onabort;
  }

  /**
   * @param onerror the onerror to set
   */
  public void setOnerror(Object onerror) {
    this.onerror = onerror;
  }

  /**
   * @param onload the onload to set
   */
  public void setOnload(Object onload) {
    this.onload = onload;
  }

  public void setPixels(int[] pixels, int x, int y, int width, int height) {
    image.setPixels(pixels, x, y, width, height);
  }

  /**
   * @param src the src to set
   */
  public void setSrc(String src) {
    this.src = src;

    if (src != null) {
      try {
        image = Platform.getContextRootViewer().getImage(src);
        if(!image.isLoaded()) {
          image.load();;
        }
        if (onload != null) {
          executeEvent(src + "_onLoad", onload);
        }
      } catch(Exception e) {
        executeEvent(src + "_onError", onerror);
      }
    }
  }

  public int get(int index) {
    final int w = image.getWidth();

    return image.getPixel(index / w, index % (w + 1));
  }

  /**
   * @return the context
   */
  public iWidget getContext() {
    return (context != null)
           ? context
           : Platform.getContextRootViewer();
  }

  public int getHeight() {
    return (image == null)
           ? 0
           : image.getHeight();
  }

  @Override
  public UIImage getImage() {
    return image;
  }

  @Override
  public UIImage getImage(int x, int y, int width, int height) {
    if (image == null) {
      return null;
    }

    return image.getSubimage(x, y, width, height);
  }

  /**
   * @return the onabort
   */
  public Object getOnabort() {
    return onabort;
  }

  /**
   * @return the onerror
   */
  public Object getOnerror() {
    return onerror;
  }

  /**
   * @return the onload
   */
  public Object getOnload() {
    return onload;
  }

  public int[] getPixels() {
    if (pixelData == null) {
      int w = image.getWidth();
      int h = image.getHeight();

      pixelData = new int[w * h];
      image.getPixels(pixelData, 0, 0, w, h);
    }

    return pixelData;
  }

  public int[] getPixels(int x, int y, int width, int height) {
    int pixels[] = new int[width * height];

    image.getPixels(pixels, x, y, width, height);

    return pixels;
  }

  /**
   * @return the src
   */
  public String getSrc() {
    return src;
  }

  public int getWidth() {
    return (image == null)
           ? 0
           : image.getWidth();
  }

  @Override
  public WidgetContext getScriptingContext() {
    if (scriptingContext == null) {
      scriptingContext = Platform.getAppContext().getScriptingManager().createScriptingContext(this);
      scriptingContext.needsJSRetention=true;
    }

    return scriptingContext;
  }

  protected void executeEvent(String event, Object code) {
    if (event != null) {
      iPlatformAppContext app = getContext().getAppContext();

      aWidgetListener.execute(app, event, code);
    }
  }
}
