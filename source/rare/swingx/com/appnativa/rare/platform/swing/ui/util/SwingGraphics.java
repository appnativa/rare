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

package com.appnativa.rare.platform.swing.ui.util;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.GraphicsComposite;
import com.appnativa.rare.ui.Transform;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.UIStroke.Cap;
import com.appnativa.rare.ui.UIStroke.Join;
import com.appnativa.rare.ui.iComposite;
import com.appnativa.rare.ui.iComposite.CompositeType;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformImage;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.iTransform;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.util.IdentityArrayList;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Don DeCoteau
 */
public class SwingGraphics implements iPlatformGraphics {
  private static final AffineTransform nullTransform = new AffineTransform();
  UIStroke                             lineStroke    = new UIStroke();;
  int                                  state         = 0;
  Graphics2D                           graphics;
  iPlatformPaint                       paint;
  IdentityArrayList<Graphics2D>        stack;
  private UIColor                      color;
  private iPlatformComponent           component;
  private boolean                      componentPainterClipped;
  private iComposite                   composite;
  private UIFont                       font;
  private UIImage                      image;
  private Object                       oldAntiliasing;
  private int                          rotation;
  private iTransform                   transform;
  private java.awt.Component           view;

  public SwingGraphics(Graphics g) {
    set(g, (java.awt.Component) null);
  }

  public SwingGraphics(UIImage image) {
    this(image.getBufferedImage().createGraphics());
    this.image = image;
  }

  public SwingGraphics(Graphics g, iPlatformComponent c) {
    set(g, c);
  }

  public SwingGraphics(Graphics g, java.awt.Component c) {
    set(g, c);
  }

  public void clear() {
    if (graphics != null) {
      restoreToState(0);
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAntiliasing);
    }

    graphics  = null;
    view      = null;
    component = null;
  }

  @Override
  public void clearRect(float x, float y, float width, float height) {
    clearRect(null, x, y, width, height);
  }

  @Override
  public void clearRect(UIColor bg, float x, float y, float width, float height) {
    Graphics2D g = graphics;

    if (bg == null) {
      Composite oc = g.getComposite();

      g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
      g.fillRect(sp(x), sp(y), ss(width), ss(height));
      g.setComposite(oc);
    } else {
      Color c = g.getColor();

      g.setColor(bg);
      g.fillRect(sp(x), sp(y), ss(width), ss(height));
      g.setColor(c);
    }
  }

  @Override
  public void clipRect(float x, float y, float width, float height) {
    graphics.clipRect(sp(x), sp(y), ss(width), ss(height));
  }

  @Override
  public void clipRect(float x, float y, float width, float height, Op op) {
    switch(op) {
      case REPLACE :
        graphics.setClip(sp(x), sp(y), ss(width), ss(height));

        break;

      case DIFFERENCE : {
        Shape s = graphics.getClip();
        Area  a = new Area(s);

        a.subtract(new Area(new Rectangle2D.Float(x, y, width, height)));
        graphics.setClip(a);

        break;
      }

      case INTERSECT : {
        Shape s = graphics.getClip();
        Area  a = new Area(s);

        a.intersect(new Area(new Rectangle2D.Float(x, y, width, height)));
        graphics.setClip(a);

        break;
      }

      default :
        graphics.clipRect(sp(x), sp(y), ss(width), ss(height));

        break;
    }
  }

  @Override
  public void clipShape(iPlatformShape shape) {
    clipShape(shape, Op.UNION);
  }

  @Override
  public void clipShape(iPlatformShape shape, Op op) {
    switch(op) {
      case REPLACE :
        graphics.setClip(shape.getShape());

        break;

      case DIFFERENCE : {
        Shape s = graphics.getClip();
        Area  a = new Area(s);

        a.subtract(new Area(shape.getShape()));
        graphics.setClip(a);

        break;
      }

      case INTERSECT : {
        Shape s = graphics.getClip();
        Area  a = new Area(s);

        a.intersect(new Area(shape.getShape()));
        graphics.setClip(a);

        break;
      }

      default :
        graphics.clip(shape.getShape());

        break;
    }
  }

  @Override
  public boolean didComponentPainterClip() {
    return componentPainterClipped;
  }

  @Override
  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

  public void drawChars(char[] data, int offset, int length, float x, float y) {
    graphics.drawChars(data, offset, length, sp(x), sp(y));
  }

  @Override
  public void drawChars(char[] data, int offset, int length, float x, float y, float height) {
    graphics.drawChars(data, offset, length, (int) x, (int) (y + height));
  }

  @Override
  public void drawImage(iPlatformImage img, float x, float y) {
    Image iimg = img.getImage();

    if (iimg == null) {
      return;
    }

    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics.drawImage(iimg, sp(x), sp(y), null);
  }

  @Override
  public void drawImage(iPlatformImage img, float x, float y, iComposite composite) {
    Image iimg = img.getImage();

    if (iimg == null) {
      return;
    }

    Composite c = (composite == null)
                  ? null
                  : setCompositeEx(composite);

    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics.drawImage(iimg, sp(x), sp(y), null);

    if (c != null) {
      graphics.setComposite(c);
    }
  }

  @Override
  public void drawImage(iPlatformImage img, float x, float y, float width, float height) {
    Image iimg = img.getImage();

    if (iimg == null) {
      return;
    }

    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics.drawImage(iimg, sp(x), sp(y), ss(width), ss(height), null);
  }

  @Override
  public void drawImage(iPlatformImage img, UIRectangle src, UIRectangle dst, iComposite composite) {
    drawImage(img, src, dst, null, composite);
  }

  @Override
  public void drawImage(iPlatformImage img, UIRectangle src, UIRectangle dst, ScalingType scalingType,
                        iComposite composite) {
    Image iimg = img.getImage();

    if (iimg == null) {
      return;
    }

    Composite      c  = (composite == null)
                        ? null
                        : setCompositeEx(composite);
    RenderingHints rh = graphics.getRenderingHints();

    if (scalingType == null) {
      scalingType = ScalingType.BILINEAR;
    }

    if (scalingType != null) {
      Object o;

      switch(scalingType) {
        case BICUBIC :
        case BICUBIC_CACHED :
        case PROGRESSIVE_BICUBIC :
        case PROGRESSIVE_BICUBIC_CACHED :
          o = RenderingHints.VALUE_INTERPOLATION_BICUBIC;

          break;

        case BILINEAR :
        case BILINEAR_CACHED :
        case PROGRESSIVE_BILINEAR :
        case PROGRESSIVE_BILINEAR_CACHED :
          o = RenderingHints.VALUE_INTERPOLATION_BILINEAR;

          break;

        default :
          o = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;

          break;
      }

      graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, o);
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    int sx1 = sp(src.x);
    int sy1 = sp(src.y);
    int sx2 = sp(src.x + src.width);
    int sy2 = sp(src.y + src.height);
    int dx1 = sp(dst.x);
    int dy1 = sp(dst.y);
    int dx2 = sp(dst.x + dst.width);
    int dy2 = sp(dst.y + dst.height);

    graphics.drawImage(iimg, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);

    if (scalingType != null) {
      graphics.setRenderingHints(rh);
    }

    if (c != null) {
      graphics.setComposite(c);
    }
  }

  @Override
  public void drawLine(float x1, float y1, float x2, float y2) {
    graphics.drawLine(sp(x1), sp(y1), sp(x2), sp(y2));
  }

  @Override
  public void drawRect(float x, float y, float width, float height) {
    graphics.drawRect(sp(x), sp(y), ss(width), ss(height));
  }

  @Override
  public void drawRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    graphics.drawRoundRect(sp(x), sp(y), ss(width), ss(height), ss(arcWidth), ss(arcHeight));
  }

  @Override
  public void drawShape(iPlatformShape path, float x, float y) {
    int xx = sp(x);
    int yy = sp(y);

    graphics.translate(xx, yy);
    graphics.draw(path.getShape());
    graphics.translate(-xx, -yy);
  }

  public void drawString(String str, float x, float y) {
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.drawString(str, sp(x), sp(y));
  }

  @Override
  public void drawString(String str, float x, float y, float height) {
    graphics.drawString(str, x, y + height);
  }

  @Override
  public void fillRect(float x, float y, float width, float height) {
    graphics.fillRect(sp(x), sp(y), ss(width), ss(height));
  }

  @Override
  public void fillRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    graphics.fillRoundRect(sp(x), sp(y), ss(width), ss(height), ss(arcWidth), ss(arcHeight));
  }

  @Override
  public void fillShape(iPlatformShape path, float x, float y) {
    int xx = sp(x);
    int yy = sp(y);

    graphics.translate(xx, yy);
    graphics.fill(path.getShape());
    graphics.translate(-xx, -yy);
  }

  public static SwingGraphics fromGraphics(Graphics g, java.awt.Component c, SwingGraphics graphics) {
    if (graphics == null) {
      graphics = new SwingGraphics(g, c);
    } else {
      graphics.set(g, c);
    }

    return graphics;
  }

  @Override
  public void restoreState() {
    if ((stack != null) &&!stack.isEmpty()) {
      graphics.dispose();
      graphics       = stack.remove(stack.size() - 1);
      oldAntiliasing = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
      font           = null;
      color          = null;
    }
  }

  @Override
  public void restoreToState(int state) {
    int len = (stack == null)
              ? 0
              : stack.size();

    while((state < len) && (len > 0)) {
      graphics.dispose();
      graphics = stack.remove(stack.size() - 1);
      len--;
    }

    oldAntiliasing = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    font           = null;
    color          = null;
  }

  @Override
  public void rotate(int degrees) {
    graphics.rotate(Math.toRadians(degrees));
  }

  @Override
  public int saveState() {
    if (stack == null) {
      stack = new IdentityArrayList<Graphics2D>();
    }

    stack.add(graphics);
    graphics = (Graphics2D) graphics.create();

    return stack.size() - 1;
  }

  @Override
  public void scale(float sx, float sy) {
    graphics.scale(sx, sy);
  }

  @Override
  public void translate(float x, float y) {
    graphics.translate(sp(x), sp(y));
  }

  @Override
  public void set(Graphics g, iPlatformComponent c) {
    set(g, (c == null)
           ? null
           : c.getView());
    component = c;
  }

  public void set(Graphics g, java.awt.Component c) {
    graphics = (Graphics2D) g;
    view     = c;
    loadStroke(lineStroke);
    oldAntiliasing = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    componentPainterClipped = false;
    component               = null;
  }

  @Override
  public void setColor(UIColor c) {
    graphics.setColor(c);
    color = c;
  }

  @Override
  public void setComponent(iPlatformComponent c) {
    view      = (c == null)
                ? null
                : c.getView();
    component = c;
  }

  @Override
  public void setComponentPainterClipped(boolean componentPainterClipped) {
    this.componentPainterClipped = componentPainterClipped;
  }

  @Override
  public void setComposite(iComposite composite) {
    if (composite == null) {
      composite = GraphicsComposite.DEFAULT_COMPOSITE;
    }

    setCompositeEx(composite);
    this.composite = composite;
  }

  private Composite setCompositeEx(iComposite composite) {
    Composite ac = null;

    if (composite != null) {
      GraphicsComposite c = (GraphicsComposite) composite;

      ac = (Composite) c.getPlatformComposite();

      if (ac == null) {
        if (c.getCompositeType() == CompositeType.COPY) {
          Rectangle r = graphics.getClipBounds();

          clearRect(r.x, r.y, r.width, r.height);
          ac = SwingHelper.getInstance(CompositeType.SRC_OVER, c.getAlpha());
        } else {
          ac = SwingHelper.getInstance(c.getCompositeType(), c.getAlpha());
          c.setPlatformComposite(ac);
        }
      }
    }

    if (ac != null) {
      Composite c = graphics.getComposite();

      if (c == null) {
        c = AlphaComposite.SrcOver;
      }

      graphics.setComposite(ac);

      return c;
    }

    return null;
  }

  @Override
  public void setFont(UIFont f) {
    graphics.setFont(f);
    font = f;
  }

  public void setGraphics(Graphics g) {
    restoreToState(0);
    graphics = (Graphics2D) g;
    loadStroke(lineStroke);
  }

  @Override
  public void setPaint(iPlatformPaint p) {
    paint = p;

    if (p != null) {
      graphics.setPaint(p.getPaint());
    }
  }

  @Override
  public void setRenderingOptions(boolean anti_aliasing, boolean speed) {
    if (anti_aliasing) {
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    } else {
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    if (speed) {
      graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
    } else {
      graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
    }
  }

  @Override
  public void setRotation(int rotation) {
    this.rotation = rotation;

    AffineTransform a = graphics.getTransform();

    if (a == null) {
      a = AffineTransform.getRotateInstance(Math.toRadians(rotation));
    } else {
      a.rotate(Math.toRadians(rotation));
    }

    graphics.setTransform(a);
  }

  @Override
  public void setStroke(UIStroke stroke) {
    lineStroke = stroke;

    if (stroke == null) {
      graphics.setStroke(null);
    } else {
      graphics.setStroke(SwingHelper.getStroke(stroke));
    }
  }

  @Override
  public void setStrokeWidth(float f) {
    if (lineStroke == null) {
      lineStroke = new UIStroke(f);
    } else {
      lineStroke.setWidth(f);
    }

    graphics.setStroke(SwingHelper.getStroke(lineStroke));
  }

  @Override
  public void setTransform(iTransform transform) {
    if (transform != null) {
      graphics.setTransform((AffineTransform) transform.getPlatformTransform());
    } else {
      graphics.setTransform(nullTransform);
    }

    this.transform = transform;
  }

  public UIImage getBackingImage() {
    return image;
  }

  @Override
  public iPlatformShape getClip() {
    Shape s = graphics.getClip();

    if (s == null) {
      return null;
    }

    return new ShapeShape(s);
  }

  @Override
  public UIRectangle getClipBounds() {
    return new UIRectangle(graphics.getClipBounds());
  }

  @Override
  public UIColor getColor() {
    if (color == null) {
      color = UIColor.fromColor(graphics.getColor());
    }

    return color;
  }

  @Override
  public iPlatformComponent getComponent() {
    if ((component == null) && (view != null)) {
      component = Platform.findPlatformComponent(view);
    }

    return component;
  }

  @Override
  public iComposite getComposite() {
    return composite;
  }

  @Override
  public UIFont getFont() {
    if (font == null) {
      font = UIFont.fromFont(graphics.getFont());
    }

    return font;
  }

  @Override
  public Graphics2D getGraphics() {
    return graphics;
  }

  @Override
  public iPlatformPaint getPaint() {
    if (paint == null) {
      Paint c = graphics.getPaint();

      if (c instanceof Color) {
        paint = UIColor.fromColor((Color) c);
      } else {
        paint = new SwingPaint(c);
      }
    }

    return paint;
  }

  @Override
  public int getRotation() {
    return rotation;
  }

  @Override
  public UIStroke getStroke() {
    return lineStroke;
  }

  @Override
  public float getStrokeWidth() {
    return lineStroke.width;
  }

  public java.awt.Component getSwingComponent() {
    return view;
  }

  @Override
  public iTransform getTransform() {
    if (transform == null) {
      AffineTransform af = graphics.getTransform();

      if (af == null) {
        af = nullTransform;
      }

      transform = new Transform(af);
    }

    return transform;
  }

  @Override
  public java.awt.Component getView() {
    return view;
  }

  @Override
  public boolean isDisposed() {
    return graphics == null;
  }

  protected void loadStroke(UIStroke stroke) {
    Stroke s = graphics.getStroke();

    if (!(s instanceof BasicStroke)) {
      return;
    }

    BasicStroke bs = (BasicStroke) s;

    stroke.setWidth(bs.getLineWidth());
    stroke.setDashInterval(bs.getDashArray(), bs.getDashPhase());
    stroke.miterLimit = bs.getMiterLimit();

    switch(bs.getEndCap()) {
      case BasicStroke.CAP_BUTT :
        stroke.cap = Cap.BUTT;

        break;

      case BasicStroke.CAP_ROUND :
        stroke.cap = Cap.ROUND;

        break;

      default :
        stroke.cap = Cap.SQUARE;

        break;
    }

    switch(bs.getLineJoin()) {
      case BasicStroke.JOIN_BEVEL :
        stroke.join = Join.BEVEL;

        break;

      case BasicStroke.JOIN_ROUND :
        stroke.join = Join.ROUND;

        break;

      default :
        stroke.join = Join.MITER;

        break;
    }
  }

  private int sp(float n) {
    return Math.round(n);
  }

  private int ss(float n) {
    return (int) Math.ceil(n);
  }
}
