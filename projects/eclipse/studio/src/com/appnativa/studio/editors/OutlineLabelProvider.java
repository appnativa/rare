/*
 * @(#)OutlineLabelProvider.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import com.appnativa.studio.Studio;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class OutlineLabelProvider implements ILabelProvider {
  public OutlineLabelProvider() {
    super();
  }

  public void addListener(ILabelProviderListener listener) {}

  public void dispose() {}

  public void removeListener(ILabelProviderListener listener) {}

  public Image getImage(Object element) {
    if (element instanceof OutlineContentProvider.Node) {
      OutlineContentProvider.Node node = (OutlineContentProvider.Node) element;
      String                      s    = node.getTypeName();

      return (s == null)
             ? null
             : Studio.getWidgetIcon(s);
    }

    return null;
  }

  public String getText(Object element) {
    if (element instanceof OutlineContentProvider.Node) {
      OutlineContentProvider.Node node = (OutlineContentProvider.Node) element;

      return node.name;
    }

    return null;
  }

  public boolean isLabelProperty(Object element, String property) {
    return false;
  }
}
