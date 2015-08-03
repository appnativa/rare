package com.appnativa.studio.properties;

import java.beans.PropertyChangeListener;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.appnativa.spot.iSPOTElement;

public interface iPropertyDescriptor {
  Control showPropertyEditor(Composite parent,Object eventSource,PropertyChangeListener listener, PropertyEditors propertyEditors);
  iSPOTElement getElement();
  Font getFont();
  Font getValueFont();
  void setFont(Font font);
  void setValueFont(Font font);
}
