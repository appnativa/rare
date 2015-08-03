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

package com.appnativa.rare.ui.table;

import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.table.TableComponent;

import java.util.List;

public class MultiTableContainer extends Container implements iDataModelListener {
  boolean     preferedSizeDirty = true;
  UIDimension rowHeaderSize     = new UIDimension();
  UIDimension rowFooterSize     = new UIDimension();
  UIDimension mainTableSize     = new UIDimension();

  public MultiTableContainer(MultiTableLayout view) {
    super(view);
  }

  @Override
  public void contentsChanged(Object source) {
    preferedSizeDirty = true;
  }

  @Override
  public void contentsChanged(Object source, int index0, int index1) {
    preferedSizeDirty = true;
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    float width  = 0;
    float height = 0;
    int   len    = getComponentCount();

    for (int i = 0; i < len; i++) {
      TableComponent tc = (TableComponent) getComponentAt(i);

      tc.getMinimumSize(size);
      height = Math.max(height, size.height);
      width  += size.width;
    }

    height      = Math.max(height, size.height);
    width       += size.width;
    size.width  = width;
    size.height = height;
  }

  @Override
  public void setForeground(UIColor fg) {
    super.setForeground(fg);

    int len = getComponentCount();

    for (int i = 0; i < len; i++) {
      TableComponent tc = (TableComponent) getComponentAt(i);

      tc.setForeground(fg);
    }
  }

  @Override
  public void setBackground(UIColor bg) {
    backgroundColor = bg;

    int len = getComponentCount();

    for (int i = 0; i < len; i++) {
      TableComponent tc = (TableComponent) getComponentAt(i);

      tc.setBackground(bg);
    }
  }

  @Override
  public void setFont(UIFont f) {
    super.setFont(f);

    int len = getComponentCount();

    for (int i = 0; i < len; i++) {
      TableComponent tc = (TableComponent) getComponentAt(i);

      tc.setFont(f);
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    float width  = 0;
    float height = 0;

    if (preferedSizeDirty) {
      int            len  = getComponentCount();
      TableComponent main = null;

      for (int i = 0; i < len; i++) {
        TableComponent tc = (TableComponent) getComponentAt(i);

        if (tc.isMainTable()) {
          main = tc;
        } else {
          tc.getPreferredSize(size);

          if (main == null) {
            rowHeaderSize.setSize(size);
          } else {
            rowFooterSize.setSize(size);
          }

          height = Math.max(height, size.height);
          width  += size.width;
        }
      }

      maxWidth = maxWidth - width;
      main.getPreferredSize(size, Math.max(maxWidth, 0));
      mainTableSize.setSize(size);
      height            = Math.max(height, size.height);
      width             += size.width;
      preferedSizeDirty = false;
    } else {
      height = Math.max(height, rowHeaderSize.height);
      height = Math.max(height, mainTableSize.height);
      height = Math.max(height, rowFooterSize.height);
      width  = rowHeaderSize.width + mainTableSize.width + rowFooterSize.width;
    }

    size.width  = width;
    size.height = height;
  }

  @Override
  public void intervalAdded(Object source, int index0, int index1) {
    preferedSizeDirty = true;
  }

  @Override
  public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
    preferedSizeDirty = true;
  }

  public void layout(float width, float height) {
    TableComponent main   = null;
    TableComponent header = null;
    TableComponent footer = null;
    int            len    = getComponentCount();
    int            hh     = 0;

    for (int i = 0; i < len; i++) {
      TableComponent tc = (TableComponent) getComponentAt(i);

      if (tc.isMainTable()) {
        main = tc;
      } else {
        if (main == null) {
          header = tc;

          if (preferedSizeDirty) {
            tc.getPreferredSize(rowHeaderSize);
          }
        } else {
          footer = tc;

          if (preferedSizeDirty) {
            tc.getPreferredSize(rowFooterSize);
          }
        }
      }

      hh = Math.max(hh, tc.getTableHeader().getPreferredSize().intHeight());
    }

    float           x  = 0;
    float           y  = 0;
    iPlatformBorder b  = getBorder();
    UIInsets        in = (b == null)
                         ? null
                         : b.getBorderInsetsEx(new UIInsets());

    if (in != null) {
      x      = in.left;
      width  -= (in.left + in.right);
      y      = in.top;
      height -= (in.top + in.bottom);
    }

    float w = width;

    if (header != null) {
      header.setMeasuredHeaderHeight(hh);
      header.setBounds(x, y, rowHeaderSize.width, height);
      x += rowHeaderSize.width;
      w -= (x - ((in == null)
                 ? 0
                 : in.left));
    }

    if (footer != null) {
      footer.setMeasuredHeaderHeight(hh);
      footer.setBounds(width - rowFooterSize.width + ((in == null)
              ? 0
              : in.left), y, rowFooterSize.width, height);
      w -= rowFooterSize.width;
    }

    main.setMeasuredHeaderHeight(hh);
    main.setBounds(x, y, w, height);
  }

  @Override
  public void revalidate() {
    super.revalidate();
    preferedSizeDirty = true;
  }

  @Override
  public void structureChanged(Object source) {
    preferedSizeDirty = true;
  }
}
