/*
 * @(#)PropertyChangeListenerProxy.java   2013-02-16
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.beans;

import java.util.EventListener;

public class PropertyChangeListenerProxy implements PropertyChangeListener {
  final PropertyChangeListener listener;
  final String                 propertyName;

  public PropertyChangeListenerProxy(String propertyName, PropertyChangeListener listener) {
    this.listener     = listener;
    this.propertyName = propertyName;
  }

  public void propertyChange(PropertyChangeEvent event) {
    String name = (event == null)
                  ? null
                  : event.getPropertyName();

    if ((name == null)
        ? propertyName == null
        : name.equals(propertyName)) {
      listener.propertyChange(event);
    }
  }

  public String getPropertyName() {
    return propertyName;
  }

  public EventListener getListener() {
    return listener;
  }
}
