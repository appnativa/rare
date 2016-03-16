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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontMetrics;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.painter.aUIPainter;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

public abstract class aCanvasRenderingContext2D extends aUIPainter implements iContext {
  iPlatformGraphics     graphics;
  protected ScalingType scalingType = ScalingType.BILINEAR;

  /** the image that is used for compositing */
  protected UIImage compositeImage;

  /** the current path */
  protected iPlatformPath currentPath;

  /** the current context state */
  protected ContextState currentState;
  protected boolean      repaintCalled;

  /** the state stack */
  protected List<ContextState> stateStack;

  /** the canvas */
  protected iCanvas theCanvas;

  /** the image that is used for rendering */
  protected UIImage theImage;

  /** Whether the canvas should be optimized for compositing operations */
  protected boolean optimizeForCompositing;

  public aCanvasRenderingContext2D(iCanvas canvas) {
    theCanvas    = canvas;
    currentState = new ContextState();
    stateStack   = new ArrayList<ContextState>();
    currentPath  = PlatformHelper.createPath();
    setDisposable(true);
  }

  public void arc(float x, float y, float radius, float startAngle, float endAngle) {
    currentPath.arc(x, y, radius, startAngle, endAngle, false);
  }

  @Override
  public void arc(float x, float y, float radius, float startAngle, float endAngle, boolean antiClockwise) {
    currentPath.arc(x, y, radius, startAngle, endAngle, antiClockwise);
  }

  @Override
  public void arcTo(float x0, float y0, float x1, float y1, float radius) {
    currentPath.arcTo(x0, y0, x1, y1, radius);
  }

  @Override
  public void beginPath() {
    currentPath.reset();
  }

  @Override
  public void bezierCurveTo(float cp1x, float cp1y, float cp2x, float cp2y, float x, float y) {
    currentPath.cubicTo(cp1x, cp1y, cp2x, cp2y, x, y);
  }

  @Override
  public void blur() {
    theImage.blurImage();

    if (!repaintCalled) {
      getCanvas().repaint();
      repaintCalled = true;
    }
  }

  @Override
  public void clear() {
    clearEx();
    currentState = new ContextState();
    currentPath.reset();

    iPlatformGraphics g = getGraphics(false);

    g.clearRect(0, 0, theImage.getWidth(), theImage.getHeight());
  }

  @Override
  public void clearRect(float x, float y, float width, float height) {
    iPlatformGraphics g = getGraphics(false);

    g.clearRect(x, y, width, height);
  }

  @Override
  public void clip() {
    iPlatformShape clip = currentState.getClip();
    iPlatformPath  p    = currentPath.copy();

    p.close();

    if (clip == null) {
      currentState.setClip(p);
    } else {
      iPlatformGraphics g = getGraphics();

      g.clipShape(clip);
      currentState.setClip(g.getClip());
    }
  }

  @Override
  public void closePath() {
    currentPath.close();
  }

  @Override
  public iImageData createImageData(iImageData imagedata) {
    return createImageData(imagedata.getWidth(), imagedata.getWidth());
  }

  @Override
  public iImageData createImageData(int sw, int sh) {
    ByteBuffer b = ByteBuffer.allocate(sw * sh * 4);

    b = b.order(ByteOrder.BIG_ENDIAN);

    return new BytePixelData(b, sw, sh);
  }

  @Override
  public void createReflection(int y, int height, float opacity, int gap) {
    if (theCanvas.getHeight() < y + height + gap + height) {
      return;
    }

     theImage.addReflectionImage(y, height, opacity, gap);

    if (!repaintCalled) {
      theCanvas.repaint();
      repaintCalled = true;
    }
  }

  public void drawImage(iCanvas canvas, float x, float y) {
    iPlatformGraphics g = getGraphics(false);

    g.drawImage(canvas.getImage(false), x, y);
  }

  @Override
  public void drawImage(iImageElement img, float x, float y) {
    iPlatformGraphics g = getGraphics(false);

    g.drawImage(img.getImage(), (x + 0), (y + 0));
  }

  @Override
  public void drawImage(UIImage img, float x, float y) {
    iPlatformGraphics g = getGraphics(false);

    g.drawImage(img, x, y);
  }

  @Override
  public void drawImage(iImageElement img, float x, float y, float width, float height) {
    iPlatformGraphics g = getGraphics(false);

    g.drawImage(img.getImage(), (x + 0), (y + 0), (width + 0), (height + 0));
  }

  @Override
  public void drawImage(UIImage img, float x, float y, float width, float height) {
    iPlatformGraphics g = getGraphics(false);

    g.drawImage(img, x, y, width, height);
  }

  @Override
  public void drawImage(iImageElement img, float sx, float sy, float swidth, float sheight, float dx, float dy,
                        float dwidth, float dheight) {
    drawImage(img.getImage(), sx, sy, swidth, sheight, dx, dy, dwidth, dheight);
  }

  @Override
  public void drawImage(UIImage img, float sx, float sy, float swidth, float sheight, float dx, float dy, float dwidth,
                        float dheight) {
    iPlatformGraphics g   = getGraphics(false);
    UIRectangle       src = new UIRectangle(sx, sy, swidth, sheight);
    UIRectangle       dst = new UIRectangle(dx, dy, dwidth, dheight);

    g.drawImage(img, src, dst, null);
  }

  @Override
  public void fill() {
    iPlatformGraphics g = getGraphics(false);

    //currentPath.close();
    g.fillShape(currentPath, 0, 0);
  }

  @Override
  public void fill(iPlatformShape shape) {
    iPlatformGraphics g = getGraphics(false);

    g.fillShape(shape, 0, 0);
  }

  @Override
  public void fillRect(float x, float y, float width, float height) {
    if ((height == 0) || (width == 0)) {
      return;
    }

    iPlatformGraphics g = getGraphics(false);

    g.fillRect(x, y, width, height);
  }

  @Override
  public void fillRoundedRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    if ((height == 0) || (width == 0)) {
      return;
    }

    iPlatformGraphics g = getGraphics(false);

    g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  @Override
  public void fillText(String text, float x, float y) {
    fillText(text, x, y, -1);
  }

  public void fillText(String text, float x, float y, float maxWidth) {
    iPlatformGraphics g = getGraphics(false);

    drawText(g, text, x, y, maxWidth);
  }

  @Override
  public void fillText(String text, float x, float y, int maxWidth) {
    iPlatformGraphics g = getGraphics(false);

    drawText(g, text, x, y, maxWidth);
  }

  @Override
  public void lineTo(float x, float y) {
    currentPath.lineTo(x, y);
    currentPath.moveTo(x, y);
  }

  @Override
  public iTextMetrics measureText(String text) {
    if (text == null) {
      text = "";
    }

    UIFontMetrics fm = new UIFontMetrics(currentState.getUIFont());

    return new TextMetrics(fm.stringWidth(text));
  }

  @Override
  public void moveTo(float x, float y) {
    currentPath.moveTo(x, y);
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    if (theImage != null) {
      render(g);
    }
  }

  @Override
  public void putImageData(iImageData imagedata, int dx, int dy) {
    putImageData(imagedata, dx, dy, 0, 0, imagedata.getWidth(), imagedata.getHeight());
  }

  @Override
  public void putImageData(iImageData imagedata, int dx, int dy, int dirtyX, int dirtyY, int dirtyWidth,
                           int dirtyHeight) {
    int w = imagedata.getWidth();
    int h = imagedata.getHeight();

    if (dirtyX < 0) {
      dirtyWidth += dirtyX;
      dirtyX     = 0;
    }

    if (dirtyY < 0) {
      dirtyHeight += dirtyY;
      dirtyY      = 0;
    }

    if (dirtyX + dirtyWidth > w) {
      dirtyWidth = w - dirtyX;
    }

    if (dirtyY + dirtyHeight > h) {
      dirtyHeight = h - dirtyY;
    }

    if ((dirtyHeight > 0) && (dirtyWidth > 0)) {
      w = theImage.getWidth();
      h = theImage.getHeight();

      if (dirtyX + dirtyWidth > w) {
        dirtyWidth = w - dirtyX;
      }

      if (dirtyY + dirtyHeight > h) {
        dirtyHeight = h - dirtyY;
      }

      if ((dirtyHeight > 0) && (dirtyWidth > 0)) {
        imagedata.updateImage(theImage, dx, dy, dirtyWidth, dirtyHeight);
      }

      if (!repaintCalled) {
        getCanvas().repaint();
        repaintCalled = true;
      }
    }
  }

  @Override
  public void quadraticCurveTo(float cpx, float cpy, float x, float y) {
    currentPath.quadTo(cpx, cpy, x, y);
  }

  @Override
  public void rect(float startX, float startY, float width, float height) {
    iPlatformPath p = currentPath;

    p.drawRect(startX, startY, width, height);
  }

  @Override
  public void render(iPlatformGraphics g) {
    repaintCalled = false;
    if(theImage!=null) {
      g.setRenderingOptions(true, false);
      g.drawImage(theImage, 0, 0);
  
      if (!optimizeForCompositing && (compositeImage != null) &&!(graphics instanceof aCompositingGraphics)) {
        compositeImage.dispose();
        compositeImage = null;
      }
    }
  }

  @Override
  protected void disposeEx() {
    if (compositeImage != null) {
      compositeImage.dispose();
      compositeImage = null;
    }

    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }

    if (theImage != null) {
      theImage.dispose();
    }

    if (stateStack != null) {
      clearEx();
    }

    if (currentState != null) {
      currentState.dispose();
      currentState = null;
    }
  }

  protected void clearEx() {
    for (ContextState s : stateStack) {
      s.dispose();
    }

    stateStack.clear();
    currentState.dispose();
  }

  @Override
  public void restore() {
    if (stateStack.size() > 0) {
      currentState = stateStack.remove(stateStack.size() - 1);
    }

    if (graphics != null) {
      graphics.restoreState();
    }
  }

  @Override
  public void rotate(float angle) {
    currentState.rotate(angle);
  }

  @Override
  public void save() {
    stateStack.add(currentState);
    currentState = currentState.copy();

    if (graphics != null) {
      graphics.saveState();
    }
  }

  @Override
  public void scale(float x, float y) {
    currentState.scale(x, y);
  }

  @Override
  public void stroke() {
    iPlatformGraphics g = getGraphics(true);

    g.drawShape(currentPath, 0, 0);
  }

  @Override
  public void stroke(iPlatformShape shape) {
    iPlatformGraphics g = getGraphics(true);

    g.drawShape(shape, 0, 0);
  }

  @Override
  public void strokeRect(float x, float y, float width, float height) {
    iPlatformGraphics g = getGraphics(true);

    g.drawRect(x, y, width, height);
  }

  @Override
  public void strokeRoundedRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    iPlatformGraphics g = getGraphics(true);

    g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  @Override
  public void strokeText(String text, float x, float y) {
    strokeText(text, x, y, -1);
  }

  public void strokeText(String text, float x, float y, float maxWidth) {
    iPlatformGraphics g = getGraphics(true);

    drawText(g, text, x, y, maxWidth);
  }

  @Override
  public void strokeText(String text, float x, float y, int maxWidth) {
    iPlatformGraphics g = getGraphics(true);

    drawText(g, text, x, y, maxWidth);
  }

  @Override
  public void transform(float m11, float m12, float m21, float m22, float dx, float dy) {
    currentState.transform(m11, m12, m21, m22, dx, dy);
  }

  @Override
  public void translate(float x, float y) {
    currentState.translate(x, y);
  }

  @Override
  public void setFillStyle(iCanvasGradient grad) {
    currentState.setFillStyle(grad);
  }

  public void setFillStyle(iCanvasPattern pattern) {
    currentState.setFillStyle(pattern);
  }

  @Override
  public void setFillStyle(String color) {
    setFillStyle(ColorUtils.getColor(color));
  }

  @Override
  public void setFillStyle(UIColor color) {
    currentState.setFillStyle(new CanvasColor(color));
  }

  @Override
  public void setFont(String font) {
    currentState.setFont(font);
  }

  @Override
  public void setGlobalAlpha(float alpha) {
    currentState.setGlobalAlpha(alpha);
  }

  @Override
  public void setGlobalCompositeOperation(String globalCompositeOperation) {
    currentState.setGlobalCompositeOperation(globalCompositeOperation);
  }

  @Override
  public void setLineCap(String lineCap) {
    currentState.setLineCap(lineCap);
  }

  @Override
  public void setLineJoin(String lineJoin) {
    currentState.setLineJoin(lineJoin);
  }

  @Override
  public void setLineWidth(float width) {
    currentState.setLineWidth(width);
  }

  @Override
  public void setMiterLimit(float miterLimit) {
    currentState.setMiterLimit(miterLimit);
  }

  @Override
  public void setScalingType(ScalingType scalingType) {
    this.scalingType = scalingType;
  }

  @Override
  public void setSize(int width, int height) {
    if ((width > 0) && (height > 0)) {
      if (graphics != null) {
        graphics.dispose();
      }

      theImage      = createImageIfNecessary(theImage, width, height);
      graphics      = theImage.createGraphics();
      repaintCalled = false;
      graphics.clearRect(0, 0, width, height);
      setupGraphics(graphics);
    }
  }

  @Override
  public void setStrokeStyle(iCanvasGradient grad) {
    currentState.setStrokeStyle(grad);
  }

  public void setStrokeStyle(iCanvasPattern pattern) {
    currentState.setStrokeStyle(pattern);
  }

  @Override
  public void setStrokeStyle(String color) {
    setStrokeStyle(ColorUtils.getColor(color));
  }

  @Override
  public void setStrokeStyle(UIColor color) {
    currentState.setStrokeStyle(new CanvasColor(color));
  }

  @Override
  public void setTextAlign(String value) {
    currentState.setTextAlign(TextAlign.valueOf(value.toLowerCase(Locale.US)));
  }

  @Override
  public void setTextBaseline(String value) {
    currentState.setTextBaseline(TextBaseline.valueOf(value.toLowerCase(Locale.US)));
  }

  @Override
  public void setTransform(float m11, float m12, float m21, float m22, float dx, float dy) {
    currentState.setTransform(m11, m12, m21, m22, dx, dy);
  }

  @Override
  public iCanvas getCanvas() {
    return theCanvas;
  }

  @Override
  public String getFont() {
    return currentState.getFont();
  }

  @Override
  public float getGlobalAlpha() {
    return currentState.getGlobalAlpha();
  }

  @Override
  public String getGlobalCompositeOperation() {
    return currentState.getGlobalCompositeOperation();
  }

  public iPainter getIPainter() {
    return this;
  }

  @Override
  public UIImage getImage(boolean copy) {
    UIImage img = theImage;

    if ((img != null) && copy) {
      img = (UIImage) img.clone();
    }

    return img;
  }

  @Override
  public iImageData getImageData(int sx, int sy, int sw, int sh) {
    int w  = theImage.getWidth();
    int h  = theImage.getHeight();
    int dw = Math.min(w - sx, sw);
    int dh = Math.min(h - sy, sh);

    return new BytePixelData(theImage.getImageBytes(sx, sy, dw, dh), sw, sh);
  }

  @Override
  public String getLineCap() {
    return currentState.getLineCap();
  }

  @Override
  public String getLineJoin() {
    return currentState.getLineJoin();
  }

  @Override
  public float getLineWidth() {
    return currentState.getLineWidth();
  }

  @Override
  public float getMiterLimit() {
    return currentState.getMiterLimit();
  }

  @Override
  public iPlatformPainter getPainter() {
    return this;
  }

  /**
   * @return the scalingType
   */
  @Override
  public ScalingType getScalingType() {
    return scalingType;
  }

  @Override
  public String getTextAlign() {
    return currentState.getTextAlign().toString();
  }

  @Override
  public String getTextBaseline() {
    return currentState.getTextBaseline().toString();
  }

  @Override
  public boolean isSingleColorPainter() {
    return false;
  }

  protected abstract UIImage createImageIfNecessary(UIImage img, int width, int height);

  protected void initImage(boolean force) {
    int width  = getCanvas().getWidth();
    int height = getCanvas().getHeight();

    if (force) {
      if (width < 1) {
        width = 1;
      }

      if (height < 1) {
        height = 1;
      }
    }

    setSize(width, height);
  }

  protected void setupGraphics(iPlatformGraphics g) {
    graphics.setRenderingOptions(true, false);
  }

  protected void setImageBytes(UIImage img, int x, int y, int width, int height, ByteBuffer b) {
    int xx = x;
    int yy = y;

    for (y = 0; y < height; y++) {
      for (x = 0; x < width; x++) {
        img.setPixel(xx + x, yy + y, b.getInt());
      }
    }
  }

  protected UIFontMetrics setTextFont(iPlatformGraphics g, String s, float maxWidth) {
    UIFont        f  = currentState.getUIFont();
    UIFontMetrics fm = UIFontMetrics.getMetrics(f);

    if (maxWidth == -1) {
      g.setFont(f);
    } else {
      int w = fm.stringWidth(s);

      while((w > maxWidth) && (f.getSize() > 6)) {
        f  = f.deriveFontSize(f.getSize() - 1);
        fm = UIFontMetrics.getMetrics(f);
        w  = fm.stringWidth(s);
      }

      g.setFont(f);
    }

    return fm;
  }

  protected ByteBuffer getImageBytes(UIImage img, int x, int y, int width, int height) {
    ByteBuffer b = ByteBuffer.allocate(width * height * 4);

    b = b.order(ByteOrder.BIG_ENDIAN);

    int xx = x;
    int yy = y;

    for (y = 0; y < height; y++) {
      for (x = 0; x < width; x++) {
        int p = img.getPixel(xx + x, yy + y);

        b.put((byte) (p >>> 24));
        b.put((byte) (p >>> 16));
        b.put((byte) (p >>> 8));
        b.put((byte) p);
      }
    }

    return b;
  }

  protected UIFontMetrics getUIFontMetrics(String s, int maxWidth) {
    UIFont        f  = currentState.getUIFont();
    UIFontMetrics fm = new UIFontMetrics(f);

    if (maxWidth > -1) {
      int w = fm.stringWidth(s);

      while((w > maxWidth) && (f.getSize() > 6)) {
        f = f.deriveFontSize(f.getSize() - 1);
        fm.setFont(f);
        w = fm.stringWidth(s);
      }
    }

    return fm;
  }

  protected abstract aCompositingGraphics createCompositingGraphics(iPlatformGraphics g, UIImage image);

  protected iPlatformGraphics checkForCompositing(iPlatformGraphics g) {
    switch(currentState.alphaComposite.getCompositeType()) {
      case SRC_IN :
      case SRC_OUT :
      case DST_IN :
      case DST_ATOP :
      case DARKEN :
        compositeImage = createImageIfNecessary(compositeImage, theImage.getWidth(), theImage.getHeight());

        return createCompositingGraphics(g, compositeImage);

      default :
        if (graphics instanceof aCompositingGraphics) {
          graphics = ((aCompositingGraphics) graphics).release();
        }

        return graphics;
    }
  }

  private void drawText(iPlatformGraphics g, String text, float x, float y, float maxWidth) {
    TextAlign     t  = currentState.getTextAlign();
    UIFontMetrics fm = setTextFont(g, text, maxWidth);

    if (!getCanvas().getCanvasComponent().isLeftToRight()) {
      if (t == TextAlign.start) {
        t = TextAlign.right;
      } else if (t == TextAlign.end) {
        t = TextAlign.left;
      }
    }

    int w = fm.stringWidth(text);

    switch(t) {
      case left :
      case start :
        break;

      case right :
      case end :
        x -= w;

        break;

      case center :
        x -= (w / 2);

        break;
    }

    switch(currentState.getTextBaseline()) {
      case top :
        y += fm.getAscent();

        break;

      case hanging :
        y += fm.getAscent() - fm.getLeading();

        break;

      case alphabetic :
        y += fm.getLeading();

        break;

      case ideographic :
        y -= (fm.getDescent());

        break;

      case bottom :
        y -= (fm.getLeading() + fm.getDescent());

        break;

      case middle :
        y += (fm.getAscent() - fm.getDescent()) / 2;

        break;
    }

    g.drawString(text, x, y, fm.getHeight());
  }

  private iPlatformGraphics getGraphics() {
    if (graphics == null) {
      initImage(true);
    }

    if (!repaintCalled) {
      getCanvas().repaint();
      repaintCalled = true;
    }

    graphics = checkForCompositing(graphics);
    currentState.setValues(graphics, true);

    return graphics;
  }

  private iPlatformGraphics getGraphics(boolean strokePaint) {
    if (graphics == null) {
      initImage(true);
    }

    if (!repaintCalled) {
      getCanvas().repaint();
      repaintCalled = true;
    }

    graphics = checkForCompositing(graphics);
    currentState.setValues(graphics, strokePaint);

    return graphics;
  }

  public boolean isOptimizeForCompositing() {
    return optimizeForCompositing;
  }

  public void setOptimizeForCompositing(boolean optimizeForCompositing) {
    this.optimizeForCompositing = optimizeForCompositing;
  }

  public static class TextMetrics implements iTextMetrics {
    int width;

    public TextMetrics(int width) {
      this.width = width;
    }

    @Override
    public int getWidth() {
      return width;
    }
  }
}
