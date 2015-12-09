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

import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.ui.event.iDataModelListener;

import java.util.List;

public class ScrollContainer extends Container implements iDataModelListener, iAppleLayoutManager {
  protected boolean            preferedSizeDirty = true;
  protected UIDimension        rowHeaderSize     = new UIDimension();
  protected UIDimension        rowFooterSize     = new UIDimension();
  protected UIDimension        mainComponentSize = new UIDimension();
  protected iPlatformComponent mainComponent;

  public ScrollContainer(ParentView view) {
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
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    float width  = 0;
    float height = 0;
    int   len    = getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent c = getComponentAt(i);

      c.getMinimumSize(size, maxWidth);
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
      iPlatformComponent c = getComponentAt(i);

      c.setForeground(fg);
    }
  }

  @Override
  public void setBackground(UIColor bg) {
    backgroundColor = bg;

    int len = getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent c = getComponentAt(i);

      c.setBackground(bg);
    }
  }

  @Override
  public void setFont(UIFont f) {
    super.setFont(f);

    int len = getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent c = getComponentAt(i);

      c.setFont(f);
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    float width  = 0;
    float height = 0;

    if (preferedSizeDirty) {
      int                len  = getComponentCount();
      iPlatformComponent main = null;

      for (int i = 0; i < len; i++) {
        iPlatformComponent c = getComponentAt(i);

        if (c == mainComponent) {
          main = c;
        } else {
          c.getPreferredSize(size);

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
      mainComponentSize.setSize(size);
      height            = Math.max(height, size.height);
      width             += size.width;
      preferedSizeDirty = false;
    } else {
      height = Math.max(height, rowHeaderSize.height);
      height = Math.max(height, mainComponentSize.height);
      height = Math.max(height, rowFooterSize.height);
      width  = rowHeaderSize.width + mainComponentSize.width + rowFooterSize.width;
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

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    super.add(c, constraints, position);

    if (mainComponent == null) {
      mainComponent = c;
    }
  }

  public void layout(float width, float height) {
    iPlatformComponent main   = null;
    iPlatformComponent header = null;
    iPlatformComponent footer = null;
    int                len    = getComponentCount();

    for (int i = 0; i < len; i++) {
      iPlatformComponent c = getComponentAt(i);

      if (c == mainComponent) {
        main = c;
      } else {
        if (main == null) {
          header = c;

          if (preferedSizeDirty) {
            c.getPreferredSize(rowHeaderSize);
          }
        } else {
          footer = c;

          if (preferedSizeDirty) {
            c.getPreferredSize(rowFooterSize);
          }
        }
      }
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
      header.setBounds(x, y, rowHeaderSize.width, height);
      x += rowHeaderSize.width;
      w -= (x - ((in == null)
                 ? 0
                 : in.left));
    }

    if (footer != null) {
      footer.setBounds(width - rowFooterSize.width + ((in == null)
              ? 0
              : in.left), y, rowFooterSize.width, height);
      w -= rowFooterSize.width;
    }

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

  public iPlatformComponent getMainComponent() {
    return mainComponent;
  }

  public void setMainComponent(iPlatformComponent mainComponent) {
    this.mainComponent = mainComponent;
  }

  @Override
  public void layout(ParentView view, float width, float height) {
    layout(width, height);
  }

  @Override
  public void invalidateLayout() {
    preferedSizeDirty = true;
  }
}
