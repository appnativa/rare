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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;

import com.jgoodies.forms.layout.CellConstraints;

import java.util.ArrayList;

public class aFlowPanel extends XPContainer {
  protected boolean                       needsMultitplePasses;
  protected boolean                       secondPass;
  protected boolean                       useContainerWidth;
  private int                             columnSpacing = 2;
  protected ArrayList<iPlatformComponent> layoutlist    = new ArrayList<iPlatformComponent>();
  private int                             rowSpacing    = 2;
  private int                             preferredSizeWidth;

  public aFlowPanel(Object view) {
    super(view);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    super.add(c, constraints, position);

    if (!(constraints instanceof CellConstraints)) {
      constraints = new CellConstraints();
    }

    c.putClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY, constraints);
    cacheInvalidated = true;
  }

  @Override
  public void setBounds(float x, float y, float w, float h) {
    if (needsMultitplePasses) {
      if (secondPass) {
        secondPass = false;
      } else {
        secondPass = true;
        requestRevalidationForBoundsChange();
      }
    }

    super.setBounds(x, y, w, h);
  }

  @Override
  public boolean heightChangesBasedOnWidth() {
    return true;
  }

  public void setColumnSpacing(int columnSpacing) {
    this.columnSpacing = columnSpacing;
  }

  public void setRowSpacing(int rowSpacing) {
    this.rowSpacing = rowSpacing;
  }

  public int getColumnSpacing() {
    return columnSpacing;
  }

  public int getRowSpacing() {
    return rowSpacing;
  }

  protected void layout(float width, float height) {
    UIInsets in = getInsets(computeInsets);
    float    x  = in.left;
    float    y  = in.top;
    float    w;
    float    h = 0;

    width  -= (in.left + in.right);
    height -= (in.top + in.bottom);

    iPlatformComponent c;
    int                len = getComponentCount();

    if (cacheInvalidated) {
      populateSizeCache();
    }

    ArrayList<iPlatformComponent> list = layoutlist;

    list.clear();

    for (int i = 0; i < len; i++) {
      c = getComponentAt(i);

      if (!c.isVisible()) {
        continue;
      }

      CellConstraints cc = (CellConstraints) c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);

      w = cc.gridWidth;

      if (x + w > width) {
        layout(list, in.left, y, (h > height)
                                 ? height
                                 : h);
        x = in.left;
        y += h + rowSpacing;
        list.clear();
        height -= h;
        h      = 0;

        if (height < 1) {
          break;
        }
      }

      list.add(c);
      x += w + columnSpacing;
      h = Math.max(h, cc.gridHeight);
    }

    if (!list.isEmpty()) {
      layout(list, in.left, y, (h > height)
                               ? height
                               : h);
    }
  }

  protected void layout(ArrayList<iPlatformComponent> list, float x, float y, float height) {
    int   len = list.size();
    float yy;
    float h;

    for (int i = 0; i < len; i++) {
      iPlatformComponent c  = list.get(i);
      CellConstraints    cc = (CellConstraints) c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);

      h = cc.gridHeight;

      if (h > height) {
        h = height;
      }

      switch(cc.vAlign.abbreviation()) {
        case 't' :
          yy = y;

          break;

        case 'c' :
          yy = y + (height - h);

          break;

        case 'f' :
          yy = 0;
          h  = height;

          break;

        default :
          yy = y + height - h;

          break;
      }

      c.setBounds(x, yy, cc.gridWidth, h);
      x += cc.gridWidth + columnSpacing;
    }
  }

  protected void populateSizeCache() {
    iPlatformComponent c;
    int                len  = getComponentCount();
    UIDimension        size = new UIDimension();

    for (int i = 0; i < len; i++) {
      c = getComponentAt(i);

      if (!c.isVisible()) {
        continue;
      }

      CellConstraints cc = (CellConstraints) c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);

      c.getPreferredSize(size);
      cc.gridHeight = (int) size.height;
      cc.gridWidth  = (int) size.width;
      c.getMinimumSize(size);
      cc.gridY = (int) size.height;
      cc.gridX = (int) size.width;
    }

    cacheInvalidated = false;
  }

  protected void requestRevalidationForBoundsChange() {
    Platform.invokeLater(new Runnable() {
      @Override
      public void run() {
        revalidate();
      }
    });
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    if (preferredSizeWidth > 0) {
      getPreferredSizeEx(size, 0);

      return;
    }

    if (cacheInvalidated) {
      populateSizeCache();
    }

    size.setSize(0, 0);

    iPlatformComponent c;
    int                len = getComponentCount();

    for (int i = 0; i < len; i++) {
      c = getComponentAt(i);

      if (!c.isVisible()) {
        continue;
      }

      c.getPreferredSize(size);

      break;
    }

    if ((preferredSizeWidth > 0) || (needsMultitplePasses && secondPass)) {
      float w = size.width;

      getPreferredSizeEx(size, 0);
      size.width = w;
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (preferredSizeWidth > 0) {
      maxWidth = preferredSizeWidth;
    } else if (needsMultitplePasses && secondPass) {
      maxWidth = getWidth();
    }

    if (maxWidth > 0) {
      getSizeForWidth(size, maxWidth);
    } else {
      if (cacheInvalidated) {
        populateSizeCache();
      }

      int                height = 0;
      int                width  = 0;
      iPlatformComponent c;
      int                len = getComponentCount();

      for (int i = 0; i < len; i++) {
        c = getComponentAt(i);

        if (!c.isVisible()) {
          continue;
        }

        CellConstraints cc = getMeasuredCellConstraints(c, true);

        width  += cc.gridWidth + columnSpacing;
        height = Math.max(height, cc.gridHeight);
      }

      if (len > 0) {
        width -= columnSpacing;
      }

      size.width  = width;
      size.height = height;
    }
  }

  public float getPreferredHeight(int width) {
    UIDimension size = new UIDimension();

    getSizeForWidth(size, width);

    return size.height;
  }

  protected void getSizeForWidth(UIDimension size, float maxWidth) {
    if (cacheInvalidated) {
      populateSizeCache();
    }

    UIInsets in          = getInsets(computeInsets);
    int      height      = 0;
    int      width       = 0;
    float    totalHeight = in.top + in.bottom;
    int      totalWidth  = 0;

    maxWidth -= in.left + in.right;

    iPlatformComponent c;
    int                len = getComponentCount();

    for (int i = 0; i < len; i++) {
      c = getComponentAt(i);

      if (!c.isVisible()) {
        continue;
      }

      CellConstraints cc = getMeasuredCellConstraints(c, true);

      width += cc.gridWidth;

      if (width > maxWidth) {
        totalHeight += height + rowSpacing;
        totalWidth  = Math.max(width - cc.gridWidth, totalWidth);
        width       = cc.gridWidth;
        height      = 0;
      }

      width  += columnSpacing;
      height = Math.max(height, cc.gridHeight);
    }

    if (height > 0) {
      totalHeight += height;
    } else {
      totalHeight = Math.max(totalHeight - rowSpacing, in.top + in.bottom);
    }

    size.width  = totalWidth;
    size.height = totalHeight;
  }

  public boolean isNeedsMultitplePasses() {
    return needsMultitplePasses;
  }

  public void setNeedsMultitplePasses(boolean needsMultitplePasses) {
    this.needsMultitplePasses = needsMultitplePasses;
  }

  public int getPreferredSizeWidth() {
    return preferredSizeWidth;
  }

  public void setPreferredSizeWidth(int preferredSizeWidth) {
    this.preferredSizeWidth = preferredSizeWidth;
  }
}
