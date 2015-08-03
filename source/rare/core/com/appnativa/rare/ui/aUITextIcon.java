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

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.rare.ui.painter.aUIPainter;
import com.appnativa.rare.ui.painter.aUIPlatformPainter;

public abstract class aUITextIcon extends aUIPlatformPainter implements iPlatformIcon, Cloneable {
  protected iPlatformBorder  border;
  protected iPlatformIcon    disabledIcon;
  protected int              heightPad;
  protected iActionComponent label;
  protected UIDimension      size;
  protected boolean          sizeSet;
  protected boolean          square;
  protected int              widthPad;

  /**
   * Creates a new instance
   *
   * @param text the text for the icon
   */
  public aUITextIcon(CharSequence text) {
    this(text, null, null, null);
  }

  /**
   * Creates a new instance
   *
   * @param text the text for the icon
   * @param fg the foreground color
   */
  public aUITextIcon(CharSequence text, UIColor fg) {
    this(text, fg, null, null);
  }

  /**
   * Creates a new instance
   *
   * @param text the text for the icon
   * @param font the font for the text
   * @param fg the foreground color
   * @param border the border
   */
  public aUITextIcon(CharSequence text, UIColor fg, UIFont font, iPlatformBorder border) {
    label = createComponent();
    PlatformHelper.setTextAlignment(label, RenderableDataItem.HorizontalAlign.CENTER,
                                    RenderableDataItem.VerticalAlign.CENTER);

    if (font != null) {
      label.setFont(font);
    }

    setBorder(border);
    setText(text);
  }

  @Override
  public Object clone() {
    aUITextIcon ic = (aUITextIcon) super.clone();

    ic.label = (iActionComponent) label.copy();

    return ic;
  }

  public void setBackgroundColor(UIColor bg) {
    label.setBackground(bg);
  }

  public void setBorder(iPlatformBorder border) {
    if (border != this.border) {
      this.border = border;
      label.setBorder(border);
    }
  }

  public void setFont(UIFont font) {
    label.setFont(font);
  }

  public void setForegroundColor(UIColor fg) {
    label.setForeground(fg);
  }

  public void setPadding(int width, int height) {
    heightPad = height;
    widthPad  = width;
    updateSize();
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    UIRectangle rect = aUIPainter.getRenderLocation(g.getComponent(), renderSpace, renderType, x, y, width, height,
                         size.width, size.height, null);

    this.paint(g, rect.x, rect.y, size.width, size.height);
  }

  /**
   * Sets the render type
   *
   * @param type the render type
   */
  @Override
  public void setRenderType(RenderType type) {
    this.renderType = type;

    if (type == null) {
      type = RenderType.CENTERED;
    }

    switch(type) {
      case UPPER_LEFT :
        PlatformHelper.setTextAlignment(label, RenderableDataItem.HorizontalAlign.LEADING,
                                        RenderableDataItem.VerticalAlign.TOP);

        break;

      case UPPER_MIDDLE :
        PlatformHelper.setTextAlignment(label, RenderableDataItem.HorizontalAlign.CENTER,
                                        RenderableDataItem.VerticalAlign.TOP);

        break;

      case UPPER_RIGHT :
        PlatformHelper.setTextAlignment(label, RenderableDataItem.HorizontalAlign.TRAILING,
                                        RenderableDataItem.VerticalAlign.TOP);

        break;

      case LOWER_LEFT :
        PlatformHelper.setTextAlignment(label, RenderableDataItem.HorizontalAlign.LEADING,
                                        RenderableDataItem.VerticalAlign.BOTTOM);

        break;

      case LOWER_MIDDLE :
        PlatformHelper.setTextAlignment(label, RenderableDataItem.HorizontalAlign.CENTER,
                                        RenderableDataItem.VerticalAlign.BOTTOM);

        break;

      case LOWER_RIGHT :
        PlatformHelper.setTextAlignment(label, RenderableDataItem.HorizontalAlign.TRAILING,
                                        RenderableDataItem.VerticalAlign.BOTTOM);

        break;

      case LEFT_MIDDLE :
        PlatformHelper.setTextAlignment(label, RenderableDataItem.HorizontalAlign.LEADING,
                                        RenderableDataItem.VerticalAlign.CENTER);

        break;

      case RIGHT_MIDDLE :
        PlatformHelper.setTextAlignment(label, RenderableDataItem.HorizontalAlign.TRAILING,
                                        RenderableDataItem.VerticalAlign.CENTER);

        break;

      default :
        PlatformHelper.setTextAlignment(label, RenderableDataItem.HorizontalAlign.CENTER,
                                        RenderableDataItem.VerticalAlign.CENTER);

        break;
    }
  }

  /**
   * Sets the icon size
   *
   * @param size the size of the icon
   */
  public void setSize(UIDimension size) {
    if (size == null) {
      sizeSet = false;
      updateSize();
    } else {
      sizeSet = true;
      label.setSize(size.width, size.height);
      this.size.setSize(size);
    }
  }

  public void setSquare(boolean square) {
    this.square = square;
    sizeSet     = false;

    if (square) {
      final float s = Math.max(size.width, size.height);

      size.setSize(s, s);
      label.setSize(size.width, size.height);
    }
  }

  /**
   *  Sets the icon's text
   *
   * @param text the text for the icon
   */
  public void setText(CharSequence text) {
    label.setText(text);
    updateSize();
  }

  @Override
  public UIColor getBackgroundColor() {
    return label.getBackground();
  }

  public iPlatformBorder getBorder() {
    return border;
  }

  @Override
  public iPlatformIcon getDisabledVersion() {
    if (disabledIcon == null) {
      aUITextIcon ic = (aUITextIcon) clone();

      ic.label.setEnabled(false);
      disabledIcon = ic;
    }

    return disabledIcon;
  }

  @Override
  public Displayed getDisplayed() {
    return displayed;
  }

  /**
   * Gets the height of the icon
   *
   * @return the height of the icon
   */
  @Override
  public int getIconHeight() {
    return size.intHeight();
  }

  /**
   * Gets the width of the icon
   *
   * @return  the width of the icon
   */
  @Override
  public int getIconWidth() {
    return size.intWidth();
  }

  /**
   * Gets the label component that render's the text
   *
   * @return the label component that renders the text
   */
  public iActionComponent getLabel() {
    return label;
  }

  @Override
  public RenderSpace getRenderSpace() {
    return renderSpace;
  }

  @Override
  public RenderType getRenderType() {
    return renderType;
  }

  /**
   *  Gets the icon's text
   *
   * @return the text for the icon
   */
  public CharSequence getText() {
    return label.getText();
  }

  @Override
  public boolean isSingleColorPainter() {
    return false;
  }

  public boolean isSquare() {
    return square;
  }

  protected abstract iActionComponent createComponent();

  protected void updateSize() {
    if (!sizeSet) {
      size             = label.getPreferredSize();
      this.size.width  += widthPad;
      this.size.height += heightPad;
      label.setSize(size.width, size.height);
      setSquare(square);
    }
  }
}
