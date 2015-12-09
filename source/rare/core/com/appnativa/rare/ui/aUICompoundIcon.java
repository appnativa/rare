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

import com.appnativa.util.iCancelable;

public abstract class aUICompoundIcon extends aPlatformIcon implements Cloneable, iCancelable {
  protected int             iconHeight = -1;
  protected int             iconWidth  = -1;
  protected iPlatformIcon   disabledIcon;
  protected UIPoint[]       iconLocations;
  protected iPlatformIcon[] icons;
  protected Object          linkedData;
  private boolean           horizontal;
  protected boolean         composite = true;

  /**
   * Creates a new instance
   */
  public aUICompoundIcon() {}

  /**
   * Creates a new instance
   */
  public aUICompoundIcon(iPlatformIcon[] icons) {
    this.icons = icons;
  }

  /**
   * Creates a new instance
   *
   * @param firstIcon the first icon
   * @param secondIcon the second  icon
   */
  public aUICompoundIcon(iPlatformIcon firstIcon, iPlatformIcon secondIcon) {
    this(new iPlatformIcon[] { firstIcon, secondIcon });
  }

  @Override
  public void cancel(boolean canInterrupt) {
    for (iPlatformIcon ic : icons) {
      if (ic instanceof iCancelable) {
        ((iCancelable) ic).cancel(canInterrupt);
      }
    }
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException ex) {
      throw new InternalError(ex.toString());
    }
  }

  public static UICompoundIcon create(iPlatformIcon... icons) {
    return new UICompoundIcon(icons);
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    float     xx;
    float     yy;
    float     w;
    float     h;
    final int len = icons.length;

    for (int i = 0; i < len; i++) {
      iPlatformIcon icon = icons[i];
      UIPoint       p    = (iconLocations != null)
                           ? iconLocations[i]
                           : null;

      if (icon != null) {
        xx = x;
        yy = y;
        w  = width;
        h  = height;

        if (p != null) {
          xx += p.x;
          yy += p.y;
          w  -= p.y;
          h  -= p.y;
        }

        icon.paint(g, xx, yy, w, h);
      }
    }
  }

  /**
   * Sets the first icon
   *
   * @param icon the icon
   */
  public void setFirstIcon(iPlatformIcon icon) {
    icons[0] = icon;
  }

  /**
   * Sets the location of the first icon
   * @param x the x-position
   * @param y the y-position
   */
  public void setFirstIconLocation(int x, int y) {
    if (iconLocations == null) {
      iconLocations = new UIPoint[icons.length];
    }

    iconLocations[0] = new UIPoint(x, y);
  }

  /**
   * Sets the icon height
   *
   * @param iconHeight the height to set
   */
  public void setIconHeight(int iconHeight) {
    this.iconHeight = iconHeight;
  }

  /**
   * Sets the icon width
   *
   * @param iconWidth the width to set
   */
  public void setIconWidth(int iconWidth) {
    this.iconWidth = iconWidth;
  }

  /**
   * Sets the linked data for the icon
   *
   * @param linkedData the linked data for the icon
   */
  public void setLinkedData(Object linkedData) {
    this.linkedData = linkedData;
  }

  /**
   * Sets the second icon
   *
   * @param icon the icon
   */
  public void setSecondIcon(iPlatformIcon icon) {
    icons[1] = icon;
  }

  /**
   * Sets the location of the second icon
   * @param x the x-position
   * @param y the y-position
   */
  public void setSecondIconLocation(int x, int y) {
    if (iconLocations == null) {
      iconLocations = new UIPoint[icons.length];
    }

    iconLocations[1] = new UIPoint(x, y);
  }

  public void alignSideBySide(boolean horizontal, int gap, boolean center) {
    this.horizontal = horizontal;
    this.composite  = false;

    int len = icons.length;

    if (iconLocations == null) {
      iconLocations = new UIPoint[len];
    }

    int n = 0;

    iconWidth  = getIconWidth();
    iconHeight = getIconHeight();

    for (int i = 0; i < len; i++) {
      iPlatformIcon ic = icons[i];

      if (horizontal) {
        iconLocations[i] = new UIPoint(n, 0);
        n                += ic.getIconWidth();

        if (center) {
          iconLocations[i].y = (iconHeight - ic.getIconHeight()) / 2;
        }

        n += gap;
      } else {
        iconLocations[i] = new UIPoint(0, n);

        if (center) {
          iconLocations[i].x = (iconWidth - ic.getIconWidth()) / 2;
        }

        n += ic.getIconHeight();

        n += gap;
      }
    }

    if (horizontal) {
      iconWidth += gap * (len - 1);
    } else {
      iconHeight += gap * (len - 1);
    }
  }

  @Override
  public iPlatformIcon getDisabledVersion() {
    if (disabledIcon == null) {
      aUICompoundIcon b = (aUICompoundIcon) clone();

      disabledIcon = b;

      int len = icons.length;

      for (int i = 0; i < len; i++) {
        iPlatformIcon ic = icons[i];

        b.icons[i] = (ic == null)
                     ? null
                     : ic.getDisabledVersion();
      }
    }

    return disabledIcon;
  }

  /**
   * Gets the first icon
   *
   * @return the icon
   */
  public iPlatformIcon getFirstIcon() {
    return icons[0];
  }

  /**
   * Gets the location of the first icon
   *
   * @return the first icon location or null if no explicitly set
   */
  public UIPoint getFirstIconLocation() {
    return (iconLocations == null)
           ? null
           : iconLocations[0];
  }

  /**
   * Returns the icon's height.
   *
   * @return an int specifying the fixed height of the icon.
   */
  @Override
  public int getIconHeight() {
    if (iconHeight != -1) {
      return iconHeight;
    }

    int h = 0;

    for (iPlatformIcon ic : icons) {
      if (ic != null) {
        if (horizontal || composite) {
          h = Math.max(h, ic.getIconHeight());
        } else {
          h += ic.getIconHeight();
        }
      }
    }

    return h;
  }

  /**
   * Returns the icon's width.
   *
   * @return an int specifying the fixed width of the icon.
   */
  @Override
  public int getIconWidth() {
    if (iconWidth != -1) {
      return iconWidth;
    }

    int w = 0;

    for (iPlatformIcon ic : icons) {
      if (ic != null) {
        if (horizontal) {
          w += ic.getIconWidth();
        } else {
          w = Math.max(w, ic.getIconWidth());
        }
      }
    }

    return w;
  }

  /**
   * Gets the linked data for the icon
   *
   * @return the linked data for the icon
   */
  public Object getLinkedData() {
    return linkedData;
  }

  /**
   * Gets the second icon
   *
   * @return the icon
   */
  public iPlatformIcon getSecondIcon() {
    return icons[1];
  }

  /**
   * Gets the location of the second icon
   *
   * @return the second icon location or null if no explicitly set
   */
  public UIPoint getSecondIconLocation() {
    return (iconLocations == null)
           ? null
           : iconLocations[0];
  }

  @Override
  public boolean isCanceled() {
    for (iPlatformIcon ic : icons) {
      if (ic instanceof iCancelable) {
        return ((iCancelable) ic).isCanceled();
      }
    }

    return false;
  }

  @Override
  public boolean isDone() {
    for (iPlatformIcon ic : icons) {
      if (ic instanceof iCancelable) {
        if (!((iCancelable) ic).isDone()) {
          return false;
        }
      }
    }

    return true;
  }

  public boolean isHorizontal() {
    return horizontal;
  }

  public void setHorizontal(boolean horizontal) {
    this.horizontal = horizontal;
  }
}
