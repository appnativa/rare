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

package com.appnativa.rare.platform.android.ui;

import android.graphics.Canvas;

import android.view.View;

import com.appnativa.rare.platform.android.ui.util.ImageHelper;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.aPlatformIcon;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.util.IdentityArrayList;

public class MultiStateIcon extends aPlatformIcon implements iImageObserver {
  public static final int                   DEFAULT      = 0;
  public static final int                   SELECTED     = 1;
  public static final int                   PRESSED      = 2;
  public static final int                   DISABLED     = 3;
  protected int                             currentState = DEFAULT;
  iPlatformIcon[]                           icons        = new iPlatformIcon[4];
  int                                       iconHeight;
  int                                       iconWidth;
  private iImageObserver                    imageObserver;
  private IdentityArrayList<iImageObserver> observersList;

  public MultiStateIcon() {}

  @Override
  public iPlatformIcon getDisabledVersion() {
    return this;
  }

  public iPlatformIcon getIcon(int state) {
    if ((state > -1) && (state < icons.length)) {
      return icons[state];
    }

    return null;
  }

  @Override
  public int getIconHeight() {
    return iconHeight;
  }

  @Override
  public int getIconWidth() {
    return iconWidth;
  }

  @Override
  public void imageLoaded(UIImage image) {
    updateSize();
    notifyObservers();
  }

  public boolean isIconsLoaded(iImageObserver is) {
    if ((iconWidth > 0) && (iconHeight > 0)) {
      return true;
    }

    if ((is != null) && (is != this.imageObserver)) {
      if (this.imageObserver == null) {
        this.imageObserver = is;
      } else {
        if (observersList == null) {
          observersList = new IdentityArrayList<iImageObserver>(3);
        }

        observersList.addIfNotPresent(is);
      }
    }

    return false;
  }

  @Override
  public void paint(Canvas g, float x, float y, float width, float height) {
    iPlatformIcon icon = icons[currentState];

    if (icon == null) {
      icon = icons[DEFAULT];
    }

    if (icon != null) {
      icon.paint(g, x, y, width, height);
    }
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    View v = g.getView();

    if (v != null) {
      updateState(v);
    }

    iPlatformIcon icon = icons[currentState];

    if (icon == null) {
      icon = icons[DEFAULT];
    }

    if (icon != null) {
      icon.paint(g, x, y, width, height);
    }
  }

  public void setDisabledIcon(iPlatformIcon icon) {
    icons[DISABLED] = icon;
    checkIcon(icon);
  }

  public void setIcon(iPlatformIcon icon) {
    icons[DEFAULT] = icon;
    checkIcon(icon);
  }

  public void setPressedIcon(iPlatformIcon icon) {
    icons[PRESSED] = icon;
    checkIcon(icon);
  }

  public void setSelectedIcon(iPlatformIcon icon) {
    icons[SELECTED] = icon;
    checkIcon(icon);
  }

  public void updateState(View v) {
    currentState = DEFAULT;

    if (!v.isEnabled()) {
      currentState = DISABLED;

      if ((icons[DISABLED] == null) && (icons[DEFAULT] != null)) {
        icons[DISABLED] = ImageHelper.createDisabledIcon(icons[DEFAULT]);
      }
    } else if (v.isSelected()) {
      currentState = SELECTED;
    } else if (v.isPressed()) {
      currentState = PRESSED;
    }
  }

  protected void checkIcon(iPlatformIcon icon) {
    if (icon != null) {
      if ((icon instanceof UIImageIcon) &&!((UIImageIcon) icon).isImageLoaded(this)) {
        return;
      }

      updateSize();
    }
  }

  protected void notifyObserver(iImageObserver is) {
    if (is != null) {
      try {
        is.imageLoaded(null);
      } catch(Exception ignore) {}
    }
  }

  protected synchronized void notifyObservers() {
    if (imageObserver != null) {
      notifyObserver(imageObserver);
    }

    if (observersList != null) {
      for (iImageObserver is : observersList) {
        notifyObserver(is);
      }
    }

    imageObserver = null;
    observersList = null;
  }

  protected void updateSize() {
    iconWidth  = 0;
    iconHeight = 0;

    for (iPlatformIcon icon : icons) {
      if (icon != null) {
        iconWidth  = Math.max(iconWidth, icon.getIconWidth());
        iconHeight = Math.max(iconHeight, icon.getIconHeight());
      }
    }
  }
}
