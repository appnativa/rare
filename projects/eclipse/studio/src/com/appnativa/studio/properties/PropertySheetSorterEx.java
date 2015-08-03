/*
 * @(#)PropertySheetSorterEx.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetSorter;

import java.text.Collator;

public class PropertySheetSorterEx extends PropertySheetSorter {
  public PropertySheetSorterEx() {
    // TODO Auto-generated constructor stub
  }

  public PropertySheetSorterEx(Collator collator) {
    super(collator);
  }

  public int compare(IPropertySheetEntry entryA, IPropertySheetEntry entryB) {
    if ((entryA instanceof iPropertySheetEntry) && (entryB instanceof iPropertySheetEntry)) {
      iPropertySheetEntry a = (iPropertySheetEntry) entryA;
      iPropertySheetEntry b = (iPropertySheetEntry) entryB;

      return getCollator().compare(a.getComparable(), b.getComparable());
    }

    return super.compare(entryA, entryB);
  }
}
