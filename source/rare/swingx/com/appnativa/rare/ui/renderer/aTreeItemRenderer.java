/*
 * @(#)aTreeItemRenderer.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.renderer;

import com.appnativa.rare.ui.iPlatformListView;

public abstract class aTreeItemRenderer extends ListItemRenderer {
  protected boolean expanded;
  protected boolean leaf;

  public aTreeItemRenderer(iPlatformListView listView,boolean handleSelction) {
    super(listView,handleSelction);
  }


  public void setItemState(boolean isLeaf, boolean isExpanded) {
    leaf     = isLeaf;
    expanded = isExpanded;
  }
}
