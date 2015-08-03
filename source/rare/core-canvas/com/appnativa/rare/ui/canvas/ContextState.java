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

import com.appnativa.rare.ui.GraphicsComposite;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.Transform;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.canvas.iContext.TextAlign;
import com.appnativa.rare.ui.canvas.iContext.TextBaseline;
import com.appnativa.rare.ui.canvas.iContext.iCanvasPaint;
import com.appnativa.rare.ui.iComposite;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.iTransform;
import com.appnativa.util.CharScanner;
import com.appnativa.util.SNumber;

import java.util.HashMap;

/**
 *
 * @author Don DeCoteau
 */
public class ContextState implements Cloneable {
  private static final UIStroke initial_stroke = new UIStroke(1, UIStroke.Cap.BUTT, UIStroke.Join.MITER, 10f);
  private static final UIFont   initial_font   = new UIFont("sans-serif", UIFont.PLAIN,
                                                   (int) ScreenUtils.getCssPixelSize(10f));
  private static final CanvasColor               black       = new CanvasColor(UIColor.BLACK);
  protected static final HashMap<String, String> fontSizeMap = new HashMap<String, String>();
  private static iTransform                      nullTransform;
  protected String                               fontString   = "10px sans-serif";
  protected float                                globalAlpha  = 1f;
  protected float                                lineWidth    = 1f;
  protected float                                miterLimit   = 10f;
  protected TextBaseline                         textBaseline = TextBaseline.alphabetic;
  protected TextAlign                            textAlign    = TextAlign.start;
  protected iCanvasPaint                         strokeStyle  = black;
  protected UIStroke                             stroke       = initial_stroke;
  protected UIStroke.Join                        lineJoin     = UIStroke.Join.MITER;
  protected UIStroke.Cap                         lineCap      = UIStroke.Cap.BUTT;
  protected UIFont                               font         = initial_font;
  protected iCanvasPaint                         fillStyle    = black;
  protected iComposite                           alphaComposite;
  protected iPlatformShape                       clip;
  protected boolean                              lineDirty;
  protected iTransform                           transform;
  protected boolean                              valuesDirty;

  public ContextState() {
    if (nullTransform == null) {
      nullTransform = createTransform();
    }

    transform      = createTransform();
    alphaComposite = new GraphicsComposite("source-over", iComposite.CompositeType.SRC_OVER, 1);
  }

  public void dispose() {
    fillStyle      = null;
    transform      = null;
    strokeStyle    = null;
    stroke         = null;
    clip           = null;
    alphaComposite = null;
    font           = null;
  }

  @Override
  public Object clone() {
    try {
      ContextState cs = (ContextState) super.clone();

      cs.fillStyle   = fillStyle.cloneCopy();
      cs.strokeStyle = strokeStyle.cloneCopy();
      cs.transform   = transform.cloneCopy();

      return cs;
    } catch(CloneNotSupportedException ex) {
      throw new RuntimeException(ex);
    }
  }

  public ContextState copy() {
    return (ContextState) clone();
  }

  public void dirty() {
    valuesDirty = true;
  }

  public void rotate(float angle) {
    transform.rotate(angle);
    valuesDirty = true;
  }

  public void scale(float sx, float sy) {
    transform.scale(sx, sy);
    valuesDirty = true;
  }

  public void transform(float m11, float m12, float m21, float m22, float dx, float dy) {
    transform.concatenate(m11, m12, m21, m22, dx, dy);
    valuesDirty = true;
  }

  public void translate(float x, float y) {
    transform.translate(x, y);
    valuesDirty = true;
  }

  /**
   * @param clip the clip to set
   */
  public void setClip(iPlatformShape clip) {
    this.clip   = clip;
    valuesDirty = true;
  }

  public void setComposite(iComposite composite) {
    if (composite != null) {
      alphaComposite = composite;
      valuesDirty    = true;
    }
  }

  /**
   * @param fillStyle the fillStyle to set
   */
  public void setFillStyle(iCanvasPaint fillStyle) {
    this.fillStyle = fillStyle;
  }

  /**
   * @param cssfont the font to set
   */
  @SuppressWarnings("resource")
  public void setFont(String cssfont) {
    if (cssfont == null) {
      fontString = "10px sans-serif";
      font       = initial_font;

      return;
    }

    CharScanner sc = new CharScanner(cssfont);

    sc.trim();

    int    style = UIFont.PLAIN;
    String size  = null;
    String s;
    int    tok[];

    while((tok = sc.findToken(' ', true, false)) != null) {
      tok = sc.trim(tok);

      if (tok == null) {
        break;
      }

      s    = sc.getToken(tok);
      size = getFontSize(s);

      if (size != null) {
        break;
      }

      if (s.equals("bold")) {
        style |= UIFont.BOLD;
      } else if (s.equals("italic")) {
        style |= UIFont.ITALIC;
      } else if (Character.isDigit(s.charAt(0)) && (SNumber.intValue(s) > 600)) {
        style |= UIFont.BOLD;
      }
    }

    sc.trim();

    String family = sc.getLeftOver();
    UIFont f      = UIFontHelper.getFont(font, family, style, size, false);

    if (f != null) {
      this.font       = f;
      this.fontString = cssfont;
    }
  }

  public void setGlobalAlpha(float alpha) {
    if (alpha < 0) {
      alpha = 0;
    }

    if (alpha > 1) {
      alpha = 1f;
    }

    if (alpha != globalAlpha) {
      this.globalAlpha = alpha;
      alphaComposite   = alphaComposite.derive(alpha);
      valuesDirty      = true;
    }
  }

  public void setGlobalCompositeOperation(String composite) {
    iComposite.CompositeType ct = PainterUtils.getCompositeType(composite);

    if (ct != null) {
      alphaComposite = new GraphicsComposite(composite, ct, globalAlpha);
      valuesDirty    = true;
    }
  }

  public void setLineCap(String cap) {
    if ("butt".equals(cap)) {
      setLineCap(UIStroke.Cap.BUTT);
    } else if ("square".equals(cap)) {
      setLineCap(UIStroke.Cap.SQUARE);
    } else if ("round".equals(cap)) {
      setLineCap(UIStroke.Cap.ROUND);
    }
  }

  public void setLineCap(UIStroke.Cap cap) {
    lineCap     = cap;
    lineDirty   = true;
    valuesDirty = true;
  }

  public void setLineJoin(String join) {
    if ("bevel".equals(join)) {
      setLineJoin(UIStroke.Join.BEVEL);
    } else if ("miter".equals(join)) {
      setLineJoin(UIStroke.Join.MITER);
    } else {
      setLineJoin(UIStroke.Join.ROUND);
    }
  }

  public void setLineJoin(UIStroke.Join join) {
    lineJoin    = join;
    lineDirty   = true;
    valuesDirty = true;
  }

  public void setLineWidth(float width) {
    lineWidth   = width;
    lineDirty   = true;
    valuesDirty = true;
  }

  public void setMiterLimit(float limit) {
    miterLimit  = limit;
    lineDirty   = true;
    valuesDirty = true;
  }

  /**
   * @param strokeStyle the strokeStyle to set
   */
  public void setStrokeStyle(iCanvasPaint strokeStyle) {
    this.strokeStyle = strokeStyle;
  }

  /**
   * @param textAlign the textAlign to set
   */
  public void setTextAlign(TextAlign textAlign) {
    this.textAlign = textAlign;
  }

  /**
   * @param textBaseline the textBaseline to set
   */
  public void setTextBaseline(TextBaseline textBaseline) {
    this.textBaseline = textBaseline;
  }

  public void setTransform(float m11, float m12, float m21, float m22, float dx, float dy) {
    transform.setTransform(m11, m12, m21, m22, dx, dy);
    valuesDirty = true;
  }

  public void setValues(iPlatformGraphics g, final boolean strokePaint) {
    if (valuesDirty) {
      g.setRenderingOptions(true, false);
      valuesDirty = false;

      if (lineDirty) {
        lineDirty = false;
        stroke    = new UIStroke(lineWidth, lineCap, lineJoin, miterLimit);
      }

      g.setStroke(stroke);
      g.setComposite(alphaComposite);
      g.setTransform(transform);

      if (clip != null) {
        g.clipShape(clip);
      }

      g.setFont(font);
    }

    if (strokePaint) {
      g.setPaint(strokeStyle.getPaint());
    } else {
      g.setPaint(fillStyle.getPaint());
    }
  }

  /**
   * @return the clip
   */
  public iPlatformShape getClip() {
    return clip;
  }

  public iComposite getComposite() {
    return alphaComposite;
  }

  /**
   * @return the fillStyle
   */
  public iCanvasPaint getFillStyle() {
    return fillStyle;
  }

  /**
   * @return the font
   */
  public String getFont() {
    return fontString;
  }

  /**
   * @return the globalAlpha
   */
  public float getGlobalAlpha() {
    return globalAlpha;
  }

  public String getGlobalCompositeOperation() {
    return alphaComposite.getName();
  }

  public String getLineCap() {
    switch(lineCap) {
      case SQUARE :
        return "square";

      case ROUND :
        return "round";

      default :
        return "butt";
    }
  }

  public String getLineJoin() {
    switch(lineJoin) {
      case BEVEL :
        return "bevel";

      case ROUND :
        return "round";

      default :
        return "miter";
    }
  }

  public UIStroke getLineStroke() {
    return stroke;
  }

  /**
   * @return the lineWidth
   */
  public float getLineWidth() {
    return lineWidth;
  }

  /**
   * @return the miterLimit
   */
  public float getMiterLimit() {
    return miterLimit;
  }

  /**
   * @return the strokeStyle
   */
  public iCanvasPaint getStrokeStyle() {
    return strokeStyle;
  }

  /**
   * @return the textAlign
   */
  public TextAlign getTextAlign() {
    return textAlign;
  }

  /**
   * @return the textBaseline
   */
  public TextBaseline getTextBaseline() {
    return textBaseline;
  }

  public iTransform getTransform() {
    return transform;
  }

  public UIFont getUIFont() {
    return font;
  }

  public boolean isDirty() {
    return valuesDirty;
  }

  protected iTransform createTransform() {
    return new Transform();
  }

  protected static void initalizeMaps() {
    fontSizeMap.put("xx-small", "60%");
    fontSizeMap.put("x-small", "75%");
    fontSizeMap.put("small", "89%");
    fontSizeMap.put("medium", "100%");
    fontSizeMap.put("large", "120%");
    fontSizeMap.put("x-large", "150%");
    fontSizeMap.put("xx-large", "200%");
    fontSizeMap.put("larger", "120%");
    fontSizeMap.put("smaller", "80%");
  }

  private String getFontSize(String s) {
    String size = fontSizeMap.get(s);

    if (size == null) {
      int n = s.indexOf('/');

      if (n != -1) {
        s = s.substring(0, n);
      }

      if (Character.isDigit(s.charAt(0))) {
        size = s;
      }
    }

    return size;
  }
}
