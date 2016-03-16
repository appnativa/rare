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

package com.appnativa.rare.ui.renderer;

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.viewer.TreeViewer;

/**
 * An item render for tree type components
 *
 * @author     Don DeCoteau
 */
public class TreeItemRenderer extends ListItemRenderer {
  protected boolean    expanded;
  protected boolean    leaf;
  protected TreeViewer treeViewer;

  public TreeItemRenderer(TreeViewer treeViewer) {
    super();
    this.treeViewer = treeViewer;
  }

  protected void setIconAndAlignment(iPlatformRenderingComponent rc, CharSequence text,RenderableDataItem item, RenderableDataItem row,
                                  Column col, boolean enabled, boolean center, boolean top, boolean seticon,
                                  boolean oexpanded) {
    iPlatformIcon icon  = null;
    iPlatformIcon dicon = null;

    if (seticon) {
      if (expanded) {
        icon = item.getAlternateIcon();
      }

      if (icon == null) {
        icon = item.getIcon();
      }

      if (!enabled) {
        dicon = item.getDisabledIcon();

        if ((dicon == null) && (icon != null)) {
          dicon = PlatformHelper.createDisabledIconIfNeeded(icon);
          item.setDisabledIcon(dicon);
        } else if (dicon == null) {
          if (!leaf) {
            dicon = expanded
                    ? treeViewer.getDisabledFolderOpenIcon()
                    : treeViewer.getDisabledFolderClosedIcon();
          } else {
            dicon = treeViewer.getDisabledLeafIcon();
          }
        }
      } else if (icon == null) {
        if (!leaf) {
          icon = expanded
                 ? treeViewer.getFolderOpenIcon()
                 : treeViewer.getFolderClosedIcon();
        } else {
          icon = treeViewer.getLeafIcon();
        }
      }
    }

    super.setIconAndAlignment(rc,text, item, row, col, enabled, center, top, false, expanded);

    if (seticon) {
      rc.setIcon(enabled
                 ? icon
                 : dicon);
    }
  }

  public void setItemState(boolean isLeaf, boolean isExpanded) {
    leaf     = isLeaf;
    expanded = isExpanded;
  }
}
