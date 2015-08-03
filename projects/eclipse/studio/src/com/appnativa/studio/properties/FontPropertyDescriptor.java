/*
 * @(#)FontPropertyDescriptor.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import java.beans.PropertyChangeListener;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.appnativa.spot.iSPOTElement;

public class FontPropertyDescriptor extends PropertyDescriptor implements iPropertyDescriptor {
  private iSPOTElement element;

  public FontPropertyDescriptor(iSPOTElement e) {
    super(e.spot_getName(), e.spot_getName());
    this.element = e;
  }

  /**
   * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
   */
  public CellEditor createPropertyEditor(Composite parent) {
    CellEditor editor = new FontDialogCellEditor(parent);

    if (getValidator() != null) {
      editor.setValidator(getValidator());
    }

    return editor;
  }

  public boolean showPropertyEditor(Composite parent) {
    return false;
  }


  @Override
  public iSPOTElement getElement() {
    return element;
  }

  @Override
  public Control showPropertyEditor(Composite parent,
      Object eventSource, PropertyChangeListener listener, PropertyEditors propertyEditors) {
    return null;
  }

  @Override
  public Font getFont() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Font getValueFont() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setFont(Font font) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setValueFont(Font font) {
    // TODO Auto-generated method stub
    
  }
}
