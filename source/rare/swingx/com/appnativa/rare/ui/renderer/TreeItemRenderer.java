/*
 * @(#)TreeItemRenderer.java   2012-01-11
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.renderer;

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListView;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.viewer.TreeViewer;

/**
 * An item render for tree type components
 *
 * @author     Don DeCoteau
 */
public class TreeItemRenderer extends aTreeItemRenderer {
  protected boolean    showItemIcons = true;
  protected TreeViewer treeViewer;

  public TreeItemRenderer(TreeViewer treeViewer) {
    super((iPlatformListView) treeViewer.getDataComponent().getView(),true);
    this.treeViewer = treeViewer;
  }

  @Override
  public void setIconAndAlignment(iPlatformRenderingComponent rc, RenderableDataItem item, RenderableDataItem row,
                                  Column col, boolean enabled, boolean center, boolean top, boolean seticon,
                                  boolean oexpanded) {
    if (showItemIcons) {
      iPlatformIcon icon  = null;
      iPlatformIcon dicon = null;

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

      super.setIconAndAlignment(rc, item, row, col, enabled, center, top, false, expanded);
      rc.setIcon(enabled
                 ? icon
                 : dicon);
    } else {
      super.setIconAndAlignment(rc, item, row, col, enabled, center, top, true, oexpanded);
    }
  }
}
