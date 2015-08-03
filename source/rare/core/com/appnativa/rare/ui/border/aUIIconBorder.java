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

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.NinePatch;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aUIIconBorder extends aUIPlatformBorder {
  protected boolean         leftRight = true;
  protected iPlatformIcon   bottomIcon;
  protected UIImagePainter  bottomPainter;
  protected RenderType      bottomRt;
  protected UIInsets        insets;
  protected float           left, right, top, bottom;
  protected iPlatformIcon   leftIcon;
  protected float           leftPad;
  protected UIImagePainter  leftPainter;
  protected RenderType      leftRt;
  protected NinePatch       ninePatch;
  protected iPlatformBorder overLayBorder;
  protected UIImage         pattern;
  protected iPlatformIcon   rightIcon;
  protected UIImagePainter  rightPainter;
  protected RenderType      rightRt;
  protected iPlatformIcon   topIcon;
  protected float           topPad;
  protected UIImagePainter  topPainter;
  protected RenderType      topRt;

  /**
   * Creates an icon border with the specified insets.
   * @param insets the insets of the border
   */
  public aUIIconBorder(UIInsets insets) {
    setInsets(insets);
  }

  /**
   * Creates an icon border with the specified insets.
   * @param top the top inset of the border
   * @param left the left inset of the border
   * @param bottom the bottom inset of the border
   * @param right the right inset of the border
   */
  public aUIIconBorder(int top, int left, int bottom, int right) {
    this.top    = top;
    this.right  = right;
    this.bottom = bottom;
    this.left   = left;
  }

  @Override
  public Object clone() {
    aUIIconBorder b = (aUIIconBorder) super.clone();

    b.insets = null;

    return b;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean end) {
    if (end != isPaintLast()) {
      return;
    }

    if (ninePatch != null) {
      ninePatch.draw(g, x, y, width, height);

      return;
    }

    if (pattern != null) {
      paintPattern(pattern, g, x, y, width, height);

      return;
    }

    float ox      = x,
          oy      = y,
          owidth  = width,
          oheight = height;
    float xx;
    float yy;
    float tbx = x;
    float lry = y;
    float lrh = height;
    float tbw = width;

    if (leftRight) {
      tbx += left;
      tbw -= (left + right);
    } else {
      lry += top;
      lrh -= (top + bottom);
    }

    if (overLayBorder != null) {
      overLayBorder.paint(g, ox, oy, owidth, oheight, false);
    }

    if (topIcon != null) {
      if (topPainter != null) {
        paintPainter(topPainter, g, tbx, y, tbw, topIcon.getIconHeight());
      } else {
        xx = x + ((width - topIcon.getIconWidth()) / 2);
        yy = y;
        paintIcon(topIcon, g, xx, yy);
      }
    }

    if (bottomIcon != null) {
      if (bottomPainter != null) {
        yy = y + height - bottomIcon.getIconHeight();
        paintPainter(bottomPainter, g, tbx, yy, tbw, bottomIcon.getIconHeight());
        height -= bottomIcon.getIconHeight();
      } else {
        xx = x + ((width - bottomIcon.getIconWidth()) / 2) + left;
        yy = y + height - bottomIcon.getIconHeight();
        paintIcon(bottomIcon, g, xx, yy);
      }
    }

    if (leftIcon != null) {
      xx = x + left;

      if (leftPainter != null) {
        paintPainter(leftPainter, g, x, lry, leftIcon.getIconWidth(), lrh);
      } else {
        yy = y + ((height - leftIcon.getIconHeight()) / 2);
        paintIcon(leftIcon, g, xx, yy);
      }
    }

    if (rightIcon != null) {
      xx = x + width - rightIcon.getIconWidth() - right;

      if (rightPainter != null) {
        paintPainter(rightPainter, g, xx, lry, rightIcon.getIconWidth(), lrh);
      } else {
        yy = y + ((height - rightIcon.getIconHeight()) / 2);
        paintIcon(rightIcon, g, xx, yy);
      }
    }

    if (overLayBorder != null) {
      overLayBorder.paint(g, ox, oy, owidth, oheight, true);
    }
  }

  /**
   * Sets the bottom icon
   *
   * @param icon the icon
   */
  public void setBottomIcon(iPlatformIcon icon) {
    this.bottomIcon = icon;
    bottomPainter   = createImagePainter(bottomIcon, bottomRt, 0, bottomPainter);
  }

  /**
   *  Sets the icons for the border
   *
   *  @param top the top icon of the border
   *  @param left the left icon of the border
   *  @param bottom the bottom icon of the border
   *  @param right the right icon of the border
   */
  public void setIcons(iPlatformIcon top, iPlatformIcon left, iPlatformIcon bottom, iPlatformIcon right) {
    topIcon       = top;
    leftIcon      = left;
    bottomIcon    = bottom;
    rightIcon     = right;
    topPainter    = createImagePainter(topIcon, topRt, 100, topPainter);
    leftPainter   = createImagePainter(leftIcon, leftRt, 100, leftPainter);
    bottomPainter = createImagePainter(bottomIcon, bottomRt, 100, bottomPainter);
    rightPainter  = createImagePainter(rightIcon, rightRt, 100, rightPainter);
  }

  public void setInsets(UIInsets insets) {
    this.top    = insets.top;
    this.right  = insets.right;
    this.bottom = insets.bottom;
    this.left   = insets.left;
  }

  public void setInsets(int top, int left, int bottom, int right) {
    this.top    = top;
    this.right  = right;
    this.bottom = bottom;
    this.left   = left;
  }

  /**
   * Sets the left icon
   *
   * @param icon the icon
   */
  public void setLeftIcon(iPlatformIcon icon) {
    this.leftIcon = icon;
    leftPainter   = createImagePainter(leftIcon, leftRt, 100, leftPainter);
  }

  public void setLeftPad(int leftPad) {
    this.leftPad = leftPad;
  }

  public void setNinePatch(NinePatch ninePatch) {
    this.ninePatch = ninePatch;
  }

  public void setOverLayBorder(iPlatformBorder overLayBorder) {
    this.overLayBorder = overLayBorder;
  }

  /**
   * Sets the image containing the pattern
   *
   * @param image the image
   */
  public void setPattern(UIImage image) {
    pattern = image;
  }

  public void setPrecedence(boolean leftRight) {
    this.leftRight = leftRight;
  }

  public void setRenderInfo(RenderType top, int topop, RenderType left, int leftop, RenderType bottom, int bottomop,
                            RenderType right, int rightop) {
    topRt         = top;
    bottomRt      = bottom;
    leftRt        = left;
    rightRt       = right;
    topPainter    = createImagePainter(topIcon, topRt, topop, topPainter);
    leftPainter   = createImagePainter(leftIcon, leftRt, leftop, leftPainter);
    bottomPainter = createImagePainter(bottomIcon, bottomRt, bottomop, bottomPainter);
    rightPainter  = createImagePainter(rightIcon, rightRt, rightop, rightPainter);
  }

  /**
   * Sets the right icon
   *
   * @param icon the icon
   */
  public void setRightIcon(iPlatformIcon icon) {
    this.rightIcon = icon;
    rightPainter   = createImagePainter(rightIcon, rightRt, 100, rightPainter);
  }

  /**
   * Sets the top icon
   *
   * @param icon the icon
   */
  public void setTopIcon(iPlatformIcon icon) {
    this.topIcon = icon;
    topPainter   = createImagePainter(topIcon, topRt, 100, topPainter);
  }

  public void setTopPad(int topPad) {
    this.topPad = topPad;
  }

  /**
   * Reinitialize the insets parameter with this iBorder's current UIInsets.
   * @param insets the object to be reinitialized
   */
  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    if (insets == null) {
      insets = new UIInsets(0, 0, 0, 0);
    }

    insets.left   = left;
    insets.top    = top;
    insets.right  = right;
    insets.bottom = bottom;

    return addjustInsets(insets);
  }

  /**
   * Gets the bottom icon
   *
   *
   * @return the bottom icon
   */
  public iPlatformIcon getBottomIcon() {
    return bottomIcon;
  }

  /**
   * Gets the left icon
   *
   *
   * @return the left icon
   */
  public iPlatformIcon getLeftIcon() {
    return leftIcon;
  }

  public float getLeftPad() {
    return leftPad;
  }

  public iPlatformBorder getOverLayBorder() {
    return overLayBorder;
  }

  /**
   * Gets the right icon
   *
   * @return  the right icon
   */
  public iPlatformIcon getRightIcon() {
    return rightIcon;
  }

  /**
   * Gets the top icon
   *
   *
   * @return the top icon
   */
  public iPlatformIcon getTopIcon() {
    return topIcon;
  }

  public float getTopPad() {
    return topPad;
  }

  public boolean isLeftRightPrecedence() {
    return this.leftRight;
  }

  protected UIInsets addjustInsets(UIInsets insets) {
    int n;

    if (leftIcon != null) {
      n = leftIcon.getIconWidth();

      if (n > -1) {
        insets.left += n;
      }
    }

    if (topIcon != null) {
      n = topIcon.getIconHeight();

      if (n > -1) {
        insets.top += n;
      }
    }

    if (rightIcon != null) {
      n = rightIcon.getIconWidth();

      if (n > -1) {
        insets.right += n;
      }
    }

    if (bottomIcon != null) {
      n = bottomIcon.getIconHeight();

      if (n > -1) {
        insets.bottom += n;
      }
    }

    insets.left += leftPad;
    insets.top  += topPad;

    return insets;
  }

  protected UIImagePainter createImagePainter(iPlatformIcon icon, RenderType rt, int alpha, UIImagePainter ip) {
    if ((icon != null) && (rt != null) && (icon instanceof UIImageIcon)) {
      if (ip == null) {
        ip = new UIImagePainter();
      } else {
        ip.clear();
      }

      ip.setImage(((UIImageIcon) icon).getImage());
      ip.setRenderType(rt);

      if (alpha != 0) {
        ip.setImageAlpha(alpha);
      }
    } else if (ip != null) {
      ip.clear();
      ip = null;
    }

    return ip;
  }

  protected void paintIcon(iPlatformIcon icon, iPlatformGraphics g, float x, float y) {
    icon.paint(g, x, y, icon.getIconWidth(), icon.getIconHeight());
  }

  protected void paintPainter(iPlatformPainter p, iPlatformGraphics g, float x, float y, float width, float height) {
    p.paint(g, x, y, width, height, iPainter.UNKNOWN);
  }

  protected abstract void paintPattern(UIImage image, iPlatformGraphics g, float x, float y, float width, float height);
}
