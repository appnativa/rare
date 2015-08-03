/*
 * @(#)WidgetProperty.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import com.appnativa.studio.RMLDocument.WidgetAdaptable;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.iSPOTElement;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class WidgetProperty extends SequenceProperty {
  private WidgetAdaptable adaptable;

  public WidgetProperty(WidgetAdaptable adaptable, SPOTSequence element) {
    super(element);
    this.adaptable = adaptable;
  }

  public WidgetProperty(WidgetAdaptable adaptable, SPOTSequence sequence, IPropertyDescriptor[] propertyDescriptors) {
    super(sequence, propertyDescriptors);
    this.adaptable = adaptable;
  }

  public void dispose() {
    adaptable = null;
  }

  protected void valueChanged(iSPOTElement e, Object value) {
    if (adaptable != null) {
      adaptable.propertyChanged(e, value, false);
    }
  }
}
