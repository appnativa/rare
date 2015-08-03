/*
 * @(#)AttributePropertyDescriptor.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import java.beans.PropertyChangeListener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.appnativa.spot.iSPOTElement;

public class AttributePropertyDescriptor extends GenericPropertyDescriptor {
  public AttributePropertyDescriptor(iSPOTElement e) {
    this(e,null);
  }

  public AttributePropertyDescriptor(iSPOTElement e, String[] labels) {
    super(e, labels);
    e.spot_setLinkedData(PropertyEditors.ATTRIBUTES_EDITOR);
    if (labels != null) {
      setEditorCreator(EditorHelper.listEditorCreator);
      useComboxLabelProvider = false;
    }
  }

  public Control showPropertyEditor(Composite parent, Object eventSource, PropertyChangeListener listener,
                                    PropertyEditors propertyEditors) {
    return propertyEditors.showTextEditor(parent, eventSource, element, listener);
  }
}
