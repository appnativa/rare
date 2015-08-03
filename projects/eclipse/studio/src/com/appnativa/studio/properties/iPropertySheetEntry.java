package com.appnativa.studio.properties;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

public interface iPropertySheetEntry {
  String getComparable();
  IPropertyDescriptor getDescriptor();
}
