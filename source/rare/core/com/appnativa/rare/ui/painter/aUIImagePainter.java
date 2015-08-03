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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iWeakReference;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aAppContext;
import com.appnativa.rare.platform.iConfigurationListener;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.GraphicsComposite;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageHelper;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iComposite;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.util.SNumber;

import java.io.IOException;

import java.net.URL;

import java.util.Locale;

public abstract class aUIImagePainter extends aUIPlatformPainter
        implements iImagePainter, iBackgroundPainter, Cloneable, iImageObserver, iPlatformPaint {
  protected iComposite   composite   = GraphicsComposite.DEFAULT_COMPOSITE;
  protected ScalingType  scalingType = ScalingType.BILINEAR;
  protected String       _toString;
  protected boolean      checkAlternate;
  protected boolean      isNinePatch;
  protected String       sourceLocation;
  protected UIImage      theImage;
  private int            toStringModCount;
  private ConfigListener configListener;

  public aUIImagePainter() {}

  /**
   *  Creates a new instance of ImagePainter
   *
   *  @param image the image
   */
  public aUIImagePainter(UIImage image) {
    if (image != null) {
      setImage(image);
    }
  }

  /**
   * Creates a new instance of ImagePainter
   *
   * @param image the image
   * @param alpha the alpha
   * @param type the render type
   */
  public aUIImagePainter(UIImage image, int alpha, RenderType type) {
    setImage(image);
    setImageAlpha(alpha);
    renderType = type;
    displayed  = Displayed.ALWAYS;
  }

  /**
   * Creates a new instance of ImagePainter
   *
   * @param image the image
   * @param alpha the alpha
   * @param type the render type
   * @param displayed the display criteria
   */
  public aUIImagePainter(UIImage image, int alpha, RenderType type, Displayed displayed) {
    setImage(image);
    setImageAlpha(alpha);
    renderType     = type;
    this.displayed = displayed;
  }

  @Override
  public iBackgroundPainter alpha(int alpha) {
    aUIImagePainter p = (aUIImagePainter) clone();

    p.setImageAlpha(alpha);

    return p;
  }

  /**
   * Clears the contents of the painter
   */
  public void clear() {
    theImage    = null;
    renderType  = RenderType.TILED;
    displayed   = Displayed.ALWAYS;
    renderSpace = RenderSpace.WITHIN_BORDER;
    _toString   = null;
  }

  public static void drawTiledImage(iPlatformGraphics g, UIImage img, int width, int height, iComposite composite) {
    drawTiledImage(g, img, 0, 0, 0, 0, width, height, composite);
  }

  public static void drawTiledImage(iPlatformGraphics g, UIImage img, float baseX, float baseY, float x, float y,
                                    float width, float height, iComposite composite) {
    if (!img.isLoaded() || (width == 0) || (height == 0)) {
      return;
    }

    g.saveState();
    g.clipRect(x, y, width, height);

    int iwidth  = img.getWidth();
    int iheight = img.getHeight();

    if ((iheight < 1) || (iwidth < 1)) {
      return;
    }

    float ix   = 0;
    float iy   = baseY;
    float maxx = x + width - 1;
    float maxy = y + height - 1;

    for (;;) {
      ix = baseX;

      if ((iy + iheight) >= y) {
        for (;;) {
          if ((ix + iwidth) >= x) {
            g.drawImage(img, ix, iy, composite);
          }

          ix += iwidth;

          if (ix >= maxx) {
            break;
          }
        }
      }

      iy += iheight;

      if (iy >= maxy) {
        break;
      }
    }

    g.restoreState();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof aUIImagePainter)) {
      return false;
    }

    aUIImagePainter ip = (aUIImagePainter) obj;

    if ((sourceLocation != null) && (sourceLocation.length() > 0)) {
      return toString().equals(ip.toString());
    }

    if ((!composite.equals(ip.composite)) || (renderSpace != ip.renderSpace) || (renderType != ip.renderType)
        || (theImage != ip.theImage)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;

    hash = 43 * hash + toString().hashCode();

    return hash;
  }

  @Override
  public void imageLoaded(UIImage image) {
    modCount++;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float w, float h, int orientation) {
    if ((theImage == null) ||!theImage.isLoaded() || (w == 0) || (h == 0)) {
      return;
    }

    if (isNinePatch) {
      theImage.getNinePatch(true).draw(g, x, y, w, h);

      return;
    }

    UIImage image = theImage;
    float   px    = x;
    float   py    = y;

    if (renderType == RenderType.TILED) {
      drawTiledImage(g, image, x, y, px, py, w, h, composite);
    } else {
      float   iw    = image.getWidth();
      float   ih    = image.getHeight();
      float   oih   = ih;
      float   oiw   = iw;
      boolean drawn = false;

      y = 0;
      x = 0;

      switch(renderType) {
        case HORIZONTAL_TILE :
          drawn = true;
          drawTiledImage(g, image, x, y, px, py, w, ih, composite);

          break;

        case VERTICAL_TILE :
          drawn = true;
          drawTiledImage(g, image, x, y, px, py, iw, h, composite);

          break;

        case STRETCHED :
          if ((w != iw) || (h != ih)) {
            iw = w;
            ih = h;
          }

          break;

        case STRETCH_WIDTH :
        case STRETCH_WIDTH_MIDDLE :
          if ((w != iw)) {
            iw = w;
          }

          break;

        case STRETCH_HEIGHT :
        case STRETCH_HEIGHT_MIDDLE :
          if ((h != ih)) {
            ih = h;
          }

          break;

        case UPPER_LEFT :
          break;

        case UPPER_RIGHT :
          x = w - iw;

          break;

        case LOWER_LEFT :
          y = h - ih;

          break;

        case LOWER_RIGHT :
          x = w - iw;
          y = h - ih;

          break;

        case LOWER_MIDDLE :
          x = (w - iw) / 2;
          y = h - ih;

          break;

        case UPPER_MIDDLE :
          x = (w - iw) / 2;

          break;

        case LEFT_MIDDLE :
          y = (h - ih) / 2;

          break;

        case RIGHT_MIDDLE :
          x = w - iw;
          y = (h - ih) / 2;

          break;

        case CENTERED :
          x = (w - iw) / 2;
          y = (h - ih) / 2;

          break;

        default :
      }

      if (!drawn) {
        x += px;
        y += py;

        UIRectangle src = new UIRectangle(0, 0, oiw, oih);
        UIRectangle dst = new UIRectangle(x, y, iw, ih);

        g.drawImage(image, src, dst, scalingType, (composite == GraphicsComposite.DEFAULT_COMPOSITE)
                ? null
                : composite);
      }
    }
  }

  @Override
  public String toString() {
    if ((_toString == null) || (toStringModCount != modCount)) {
      toStringModCount = modCount;
      _toString        = toStringEx();
    }

    return _toString;
  }

  public String toStringEx() {
    StringBuilder sb = new StringBuilder();

    sb.append(sourceLocation);

    boolean bracket = false;

    if (composite.getAlpha() != 1) {
      sb.append(" [opacity=").append(String.valueOf(composite.getAlpha()));
      bracket = true;
    }

    if (renderType != RenderType.TILED) {
      if (!bracket) {
        sb.append("[");
        bracket = true;
      } else {
        sb.append(", ");
      }

      sb.append("renderType =\"").append(renderType.toString().toLowerCase(Locale.US));
      sb.append("\"");
    }

    if (renderSpace != RenderSpace.COMPONENT) {
      if (!bracket) {
        sb.append("[");
        bracket = true;
      } else {
        sb.append(", ");
      }

      sb.append("renderSpace =\"").append(renderSpace.toString().toLowerCase(Locale.US));
      sb.append("\"");
    }

    if (displayed != Displayed.ALWAYS) {
      if (!bracket) {
        sb.append("[");
        bracket = true;
      } else {
        sb.append(", ");
      }

      sb.append("displayed =\"").append(displayed.toString().toLowerCase(Locale.US));
      sb.append("\"");
    }

    if (composite.getCompositeType() != iComposite.CompositeType.SRC_OVER) {
      if (!bracket) {
        sb.append("[");
        bracket = true;
      } else {
        sb.append(", ");
      }

      sb.append("composite =\"").append(composite.getCompositeType().toString().toLowerCase(Locale.US));
      sb.append("\"");
    }

    if (scalingType != ScalingType.BILINEAR) {
      if (!bracket) {
        sb.append("[");
        bracket = true;
      } else {
        sb.append(", ");
      }

      sb.append("scaling =\"").append(scalingType.toString().toLowerCase(Locale.US));
      sb.append("\"");
    }

    if (bracket) {
      sb.append(" ]");
    }

    return sb.toString();
  }

  /**
   * Sets the composite type for the image
   *
   * @param composite the composite type for the image
   */
  @Override
  public void setComposite(iComposite composite) {
    this.composite = composite;
    _toString      = null;
  }

  /**
   * Sets the image display criteria
   *
   * @param imageDisplayCriteria the image display criteria
   */
  @Override
  public void setDisplayed(Displayed imageDisplayCriteria) {
    this.displayed = imageDisplayCriteria;
    _toString      = null;
  }

  @Override
  public void setImage(UIImage image) {
    modCount++;
    theImage  = image;
    _toString = null;

    if (image != null) {
      image.isLoaded(this);
      isNinePatch    = image.isNinePatch();
      checkAlternate = image.getResourceName() != null;

      if (checkAlternate && (configListener == null)) {
        Platform.getAppContext().addConfigurationListener(configListener = new ConfigListener(this));
      }
    } else {
      isNinePatch    = false;
      checkAlternate = false;
    }
  }

  @Override
  public void setImageAlpha(float alpha) {
    if (alpha > 1) {
      alpha = alpha / 255;
    }

    if (!SNumber.isEqual(composite.getAlpha(), alpha)) {
      modCount++;
      _toString = null;
      composite = composite.derive(alpha);
    }
  }

  /**
   * Sets the url to use to retrieve the image
   *
   * @param url the URL
   *
   * @throws java.io.IOException
   */
  public void setImageURL(URL url) throws IOException {
    modCount++;
    setImage(UIImageHelper.createImage(url, false, 0));
    setSourceLocation(JavaURLConnection.toExternalForm(url));
  }

  /**
   * Sets the image render space
   *
   * @param space the image render space
   */
  @Override
  public void setRenderSpace(RenderSpace space) {
    modCount++;
    this.renderSpace = space;
    _toString        = null;
  }

  @Override
  public void setRenderType(RenderType type) {
    modCount++;
    renderType = type;
    _toString  = null;

    switch(type) {
      case STRETCH_HEIGHT :
      case STRETCH_HEIGHT_MIDDLE :
      case STRETCH_WIDTH :
      case STRETCH_WIDTH_MIDDLE :
      case STRETCHED :
        break;

      default :
        checkAlternate = false;
    }
  }

  /**
   * Sets the scaling type for the image
   *
   * @param type the scaling type for the image
   */
  @Override
  public void setScalingType(ScalingType type) {
    modCount++;
    this.scalingType = type;
  }

  @Override
  public void setSourceLocation(String location) {
    sourceLocation = location;
    _toString      = null;
  }

  @Override
  public iComposite getComposite() {
    return composite;
  }

  /**
   * Gets the height of the icon
   *
   * @return the height of the icon
   */
  public int getIconHeight() {
    return (theImage == null)
           ? 0
           : theImage.getHeight();
  }

  /**
   * Gets the width of the icon
   *
   * @return  the width of the icon
   */
  public int getIconWidth() {
    return (theImage == null)
           ? 0
           : theImage.getWidth();
  }

  @Override
  public UIImage getImage() {
    return theImage;
  }

  @Override
  public iPlatformPaint getPaint(float width, float height) {
    return this;
  }

  public UIColor getPlatformPaintColor() {
    return ColorUtils.NULL_COLOR;
  }

  @Override
  public ScalingType getScalingType() {
    return scalingType;
  }

  @Override
  public String getSourceLocation() {
    return sourceLocation;
  }

  public boolean isPainter() {
    return true;
  }

  @Override
  public boolean isSingleColorPainter() {
    return false;
  }

  @Override
  protected void disposeEx() {
    if (configListener != null) {
      Platform.getAppContext().removeConfigurationListener(configListener);
      configListener = null;
    }

    clear();
    super.disposeEx();
  }

  protected void onConfigurationWillChange(Object newConfig) {
    if (checkAlternate && (theImage != null) && theImage.isLoaded()) {
      String  name = theImage.getResourceName();
      boolean land = ScreenUtils.isWiderForConfiguration(newConfig);
      UIImage img  = (name == null)
                     ? null
                     : ((aAppContext) Platform.getAppContext()).getManagedResource(name, land);

      if ((img != null) && (img != theImage)) {
        setImage(img);
        checkAlternate = true;
      } else {
        checkAlternate = false;
      }
    }
  }

  static class ConfigListener implements iConfigurationListener {
    iWeakReference painter;

    public ConfigListener(aUIImagePainter painter) {
      this.painter = PlatformHelper.createWeakReference(painter);
    }

    @Override
    public void onConfigurationChanged(Object changes) {}

    @Override
    public void onConfigurationWillChange(Object newConfig) {
      aUIImagePainter p = (aUIImagePainter) painter.get();

      if (p == null) {
        final iConfigurationListener l = this;

        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            Platform.getAppContext().removeConfigurationListener(l);
          }
        });
      } else {
        p.onConfigurationWillChange(newConfig);
      }
    }
  }
}
