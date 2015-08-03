/*
 * @(#)GenericPropertyDescriptor.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import java.beans.PropertyChangeListener;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.ComboBoxLabelProvider;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.appnativa.studio.Utilities;
import com.appnativa.spot.SPOTAny;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;

public class GenericPropertyDescriptor extends TextPropertyDescriptor implements iPropertyDescriptor {
  protected iEditorCreator editorCreator;
  protected Object         editorData;
  protected iSPOTElement   element;
  protected String[]       labels;
  protected Font font;
  protected boolean useComboxLabelProvider;
  protected Font valueFont;

  public GenericPropertyDescriptor(iSPOTElement e) {
    super(getID(e), e.spot_getName());
    this.element = e;
  }

  public GenericPropertyDescriptor(iSPOTElement e, String[] labels) {
    super(e.spot_getName(), e.spot_getName());
    this.element = e;
    this.labels  = labels;
  }

  @Override
  public CellEditor createPropertyEditor(Composite parent) {
    if (EditorHelper.useFullEditors) {
      return null;
    }

    if (editorCreator == null) {
      return super.createPropertyEditor(parent);
    }

    CellEditor editor = editorCreator.createPropertyEditor(parent, this);

    if ((getValidator() != null) && (editor != null)) {
      editor.setValidator(getValidator());
    }

    return editor;
  }

  public Control showPropertyEditor(Composite parent, Object eventSource, PropertyChangeListener listener,
                                    PropertyEditors propertyEditors) {
    if (editorCreator != null) {
      return editorCreator.showFullPropertyEditor(parent, this, eventSource, listener, propertyEditors);
    }
    if(element instanceof SPOTSequence || element instanceof SPOTSet || element instanceof SPOTAny) {
      return propertyEditors.showText(parent, Utilities.toSDF(element));
    }
    return propertyEditors.showTextEditor(parent, eventSource, element, listener);
  }

  public void setEditorCreator(iEditorCreator editorCreator) {
    this.editorCreator = editorCreator;
  }

  public void setEditorData(Object editorData) {
    this.editorData = editorData;
  }

  public iEditorCreator getEditorCreator() {
    return editorCreator;
  }

  public Object getEditorData() {
    return editorData;
  }

  public iSPOTElement getElement() {
    return element;
  }

  public static String getID(iSPOTElement e) {
    return e.spot_getName();
  }

  public ILabelProvider getLabelProvider() {
    if (!useComboxLabelProvider || (labels == null) || isLabelProviderSet()) {
      return super.getLabelProvider();
    }

    return new ComboBoxLabelProvider(labels);
  }

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

  public interface iEditorCreator {
    CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property);

    Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
                                   PropertyChangeListener listener, PropertyEditors propertyEditors);
  }

  public String[] getListValues() {
    return labels;
  }
}
