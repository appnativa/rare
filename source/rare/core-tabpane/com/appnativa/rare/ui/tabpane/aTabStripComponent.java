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

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.XPContainer;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.MouseEvent;

public abstract class aTabStripComponent extends XPContainer {
  protected aTabStripComponent(Object view) {
    super(view);
  }

  public abstract void setTabPainter(aPlatformTabPainter painter);

  @Override
  public void getMinimumSizeEx(UIDimension size) {
    getTabPainter().getMinimumSize(size);
  }

  @Override
  public void getPreferredSizeEx(UIDimension size, float maxWidth) {
    getTabPainter().getPreferredSize(size);
  }

  public static boolean handleMousePressed(MouseEvent event, aTabPainter tabPainter, UIInsets in, float width,
          float height) {
    float x       = event.getX();
    float y       = event.getY();
    int   padding = tabPainter.getTabsPadding();

    width  -= (in.left + in.right);
    height -= (in.top + in.bottom);

    float n;

    switch(tabPainter.getPosition()) {
      case BOTTOM :
        if (!tabPainter.isHandlesBottomRotation()) {
          y = height - y;
        }

        break;

      case RIGHT :
        if (!tabPainter.isHandlesRightLeftRotation()) {
          n      = x;
          x      = y;
          y      = width - padding - n;
          n      = width;
          width  = height;
          height = n;
        }

        break;

      case LEFT :
        if (!tabPainter.isHandlesRightLeftRotation()) {
          n      = x;
          x      = height - y;
          y      = n - padding;
          n      = width;
          width  = height;
          height = n;
        }

        break;

      default :
        y -= padding;

        break;
    }

    int tab = tabPainter.findTab(x, y, in.left, in.top, width, height);

    if (tab == -1) {
      tabPainter.checkForAndHandleMoreAction(x, y, in.left, in.top, width, height);
    } else if ((tab != -1) && (tab != tabPainter.getSelectedTab())) {
      UIAction a = tabPainter.getTab(tab);

      if (a.isEnabled()) {
        a.actionPerformed(new ActionEvent(a));

        return true;
      }
    }

    return false;
  }

  public abstract aPlatformTabPainter getTabPainter();
}
