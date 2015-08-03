/*
 * @(#)UIFont.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import javax.swing.plaf.UIResource;

public class UIFont extends Font {
  private float   relativeSize = 1f;
  private float   baseSize;
  private boolean strikeThrough;
  private boolean underline;

  public UIFont(Font font) {
    super(font);
    baseSize = getSize2D();
  }

  public UIFont(Map<? extends AttributedCharacterIterator.Attribute, ?> attributes) {
    super(attributes);
    baseSize = getSize2D();
  }

  public UIFont(UIFont font) {
    super(font);
    baseSize = getSize2D();
  }

  public UIFont(Font font, float rs) {
    super(font);
    relativeSize = rs;
    baseSize     = getSize2D();
  }

  public UIFont(UIFont font, float rs) {
    super(font);
    relativeSize = rs;
    baseSize     = getSize2D();
  }

  public UIFont(Font font, float rs, float bs) {
    super(font);
    relativeSize = rs;
    baseSize     = bs;
  }

  public UIFont(String name, int style, float size) {
    super(name, style, (int) size);
    baseSize = size;
  }

  public UIFont(UIFont font, float rs, float bs) {
    super(font);
    relativeSize = rs;
    baseSize     = bs;
  }

  private UIFont(String name, int style, float size, float bsize, float rs) {
    super(name, style, (int) size);
    baseSize     = bsize;
    relativeSize = rs;
  }

  public UIFont deriveBold() {
    return deriveFont(BOLD, size);
  }

  public UIFont deriveBoldItalic() {
    return deriveFont(BOLD | ITALIC, size);
  }

  @Override
  public UIFont deriveFont(float size) {
    float bs = (getSize2D() - baseSize + size);

    return new UIFont(super.deriveFont(size), relativeSize, bs);
  }

  public UIFont deriveFontSize(float size) {
    float bs = (getSize2D() - baseSize + size);

    return new UIFont(super.deriveFont(size), relativeSize, bs);
  }

  @Override
  public UIFont deriveFont(int style) {
    return deriveFont(style, size);
  }

  @Override
  public UIFont deriveFont(int style, float size) {
    float bs = (getSize2D() - baseSize + size);

    return new UIFont(super.deriveFont(style, size), relativeSize, bs);
  }

  public UIFont deriveFontEx(float size2d, float nrs) {
    if (nrs == relativeSize) {
      return this;
    }

    return new UIFont(super.deriveFont(size2d), nrs, baseSize);
  }

  public UIFont deriveItalic() {
    return deriveFont(ITALIC, size);
  }

  public UIFont deriveRelativeFont(float nrs) {
    if (nrs == relativeSize) {
      return this;
    }

    return new UIFont(super.deriveFont(baseSize * nrs), nrs, baseSize);
  }

  public UIFontResource deriveRelativeUIResourceFont(float nrs) {
    if ((nrs == relativeSize) && (this instanceof UIFontResource)) {
      return (UIFontResource) this;
    }

    return new UIFontResource(super.deriveFont(nrs * baseSize), nrs, baseSize);
  }

  public UIFont deriveStrikethrough() {
    Map a = getAttributes();

    a.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);

    return new UIFont(a);
  }

  public UIFont derive(boolean strikethrough, boolean underlined) {
    Map a = getAttributes();
    if(strikethrough) {
      a.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
    }
    else {
      a.remove(TextAttribute.STRIKETHROUGH);
    }
    if(underlined) {
      a.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    }
    else {
      a.remove(TextAttribute.UNDERLINE);
    }

    return new UIFont(a);
  }

  public UIFontResource deriveUIResourceFont(int style) {
    return new UIFontResource(super.deriveFont(style), relativeSize, baseSize);
  }

  public UIFontResource deriveUIResourceFont(float size2d, float nrs) {
    if ((nrs == relativeSize) && (this instanceof UIFontResource)) {
      return (UIFontResource) this;
    }

    return new UIFontResource(super.deriveFont(size2d), nrs, baseSize);
  }

  public UIFont deriveUnderline() {
    Map a = getAttributes();

    a.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

    return new UIFont(a);
  }

  public static final UIFont fromFont(Font font) {
    if (font instanceof UIFont) {
      return (UIFont) font;
    }

    return (font == null)
           ? null
           : new UIFont(font);
  }

  /**
   * @param baseSize the baseSize to set
   */
  public void setBaseSize(float baseSize) {
    this.baseSize = baseSize;
  }

  /**
   * @param relativeSize the relativeSize to set
   */
  public void setRelativeSize(float relativeSize) {
    this.relativeSize = relativeSize;
  }

  public void setStrikeThrough(boolean strikeThrough) {
    this.strikeThrough = strikeThrough;
  }

  public void setUnderline(boolean underline) {
    this.underline = underline;
  }

  /**
   * @return the baseSize
   */
  public float getBaseSize() {
    return baseSize;
  }

  /**
   * @return the relativeSize
   */
  public float getRelativeSize() {
    return relativeSize;
  }

  public boolean isStrikeThrough() {
    return strikeThrough;
  }

  public boolean isUnderline() {
    return underline;
  }

  public static class UIFontResource extends UIFont implements UIResource {
    public UIFontResource(Font font) {
      super(font);
    }

    public UIFontResource(Font font, float rs) {
      super(font, rs);
    }

    public UIFontResource(Font font, float rs, float bs) {
      super(font, rs, bs);
    }

    public UIFontResource(String name, int style, int size) {
      super(name, style, size);
    }
  }
}
