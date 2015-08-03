/*
 * @(#)SequencePropertyDescriptor.java
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

public class SequencePropertyDescriptor extends PropertyDescriptor implements iPropertyDescriptor {
  private iSPOTElement element;
  private Font valueFont;
  private Font font;

  public SequencePropertyDescriptor(iSPOTElement e) {
    super(e.spot_getName(), e.spot_getName());
    this.element = e;
  }

  @Override
  public CellEditor createPropertyEditor(Composite parent) {
    return null;
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
    return font;
  }

  public Font getValueFont() {
    return valueFont;
  }

  public void setFont(Font font) {
    this.font = font;
  }

  public void setValueFont(Font font) {
    this.valueFont = font;
  }
}
