/**
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 */

package com.appnativa.studio.properties;

import org.eclipse.ui.views.properties.IPropertySheetEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * A category in a PropertySheet used to group <code>IPropertySheetEntry</code>
 * entries so they are displayed together.
 */
class PropertySheetCategory {
  private List    entries          = new ArrayList();
  private boolean shouldAutoExpand = true;
  private String  categoryName;

  /**
   * Create a PropertySheet category with name.
   * @param name
   */
  public PropertySheetCategory(String name) {
    categoryName = name;
  }

  /**
   * Add an <code>IPropertySheetEntry</code> to the list
   * of entries in this category.
   * @param entry
   */
  public void addEntry(IPropertySheetEntry entry) {
    entries.add(entry);
  }

  /**
   * Removes all of the entries in this category.
   * Doing so allows us to reuse this category entry.
   */
  public void removeAllEntries() {
    entries = new ArrayList();
  }

  /**
   * Sets if this category should be automatically
   * expanded.
   * @param autoExpand
   */
  public void setAutoExpand(boolean autoExpand) {
    shouldAutoExpand = autoExpand;
  }

  /**
   * Returns <code>true</code> if this category should be automatically
   * expanded. The default value is <code>true</code>.
   *
   * @return <code>true</code> if this category should be automatically
   * expanded, <code>false</code> otherwise
   */
  public boolean getAutoExpand() {
    return shouldAutoExpand;
  }

  /**
   * Return the category name.
   * @return the category name
   */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   * Returns the entries in this category.
   *
   * @return the entries in this category
   */
  public IPropertySheetEntry[] getChildEntries() {
    return (IPropertySheetEntry[]) entries.toArray(new IPropertySheetEntry[entries.size()]);
  }
}
