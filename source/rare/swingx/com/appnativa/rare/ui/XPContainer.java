/*
 * @(#)XPContainer.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import javax.swing.JComponent;

public class XPContainer extends Container {
  protected boolean cacheInvalidated;
  private UIInsets  insets;
  protected UIInsets  margin;

  public XPContainer() {
    super();
    useBorderInSizeCalculation = true;
  }

  public XPContainer(Object view) {
    super((JComponent) view);
    useBorderInSizeCalculation = true;
  }

  @Override
  public void revalidate() {
    super.revalidate();
    cacheInvalidated = true;
  }

  public void setMargin(UIInsets margin) {
    this.margin = margin;
  }

  @Override
  public UIInsets getInsets(UIInsets in) {
    if (margin == null) {
      return super.getInsets(in);
    } else {
      return super.getInsets(in).addInsets(margin);
    }
  }

  public UIInsets getMargin() {
    return margin;
  }
  
  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    super.getPreferredSizeEx(size, maxWidth);
    UIInsets in=margin;
    if(margin!=null) {
      size.width+=in.left+in.right;
      size.height+=in.top+in.bottom;
    }
  }
  
  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    super.getMinimumSizeEx(size);
    UIInsets in=margin;
    if(margin!=null) {
      size.width+=in.left+in.right;
      size.height+=in.top+in.bottom;
    }
  }
  
  protected void populateMeasuredSizeCache(boolean populateMin) {
    populateMeasuredSizeCache(this, populateMin);
    cacheInvalidated = false;
  }

  protected UIInsets getInsetsEx() {
    iPlatformBorder b = getBorder();

    if ((b == null) && (margin == null)) {
      return null;
    }

    if (insets == null) {
      insets = new UIInsets();
    }
    if(b==null) {
      return insets.set(margin);
    }
    
    if(margin==null) {
      return b.getBorderInsets(insets);
    }

    return b.getBorderInsets(insets).addInsets(margin);
  }
}
