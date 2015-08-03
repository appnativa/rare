/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.util;

import com.google.j2objc.annotations.Weak;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/*
 * The following is:
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * Manages a list of listeners to be notified when a property changes. Listeners
 * subscribe to be notified of all property changes, or of changes to a single
 * named property.
 *
 * <p>This class is thread safe. No locking is necessary when subscribing or
 * unsubscribing listeners, or when publishing events. Callers should be careful
 * when publishing events because listeners may not be thread safe.
 */
public class PropertyChangeSupportEx {

  /**
   * All listeners, including PropertyChangeListenerProxy listeners that are
   * only be notified when the assigned property is changed. This list may be
   * modified concurrently!
   */
  private transient List<PropertyChangeListener> listeners = new CopyOnWriteArrayList<PropertyChangeListener>();
  @Weak
  private transient Object                       sourceBean;

  /**
   * Creates a new instance that uses the source bean as source for any event.
   *
   * @param sourceBean
   *            the bean used as source for all events.
   */
  public PropertyChangeSupportEx(Object sourceBean) {
    if (sourceBean == null) {
      throw new NullPointerException();
    }

    this.sourceBean = sourceBean;
  }

  /**
   * Subscribes {@code listener} to change notifications for all properties.
   * If the listener is already subscribed, it will receive an additional
   * notification. If the listener is null, this method silently does nothing.
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    if (listener != null) {
      listeners.add(listener);
    }
  }

  /**
   * Subscribes {@code listener} to change notifications for the property
   * named {@code propertyName}. If the listener is already subscribed, it
   * will receive an additional notification when the property changes. If the
   * property name or listener is null, this method silently does nothing.
   */
  public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    if ((listener != null) && (propertyName != null)) {
      listeners.add(new PropertyChangeListenerProxy(propertyName, listener));
    }
  }

  public void dispose() {
    PropertyChangeListener[] a = getPropertyChangeListeners();

    if (a != null) {
      for (PropertyChangeListener l : a) {
        removePropertyChangeListener(l);
      }
    }
  }

  /**
   * Fires a property change of a boolean property with the given name. If the
   * old value and the new value are not null and equal the event will not be
   * fired.
   *
   * @param propertyName
   *            the property name
   * @param index
   *            the index of the changed property
   * @param oldValue
   *            the old value
   * @param newValue
   *            the new value
   */
  public void fireIndexedPropertyChange(String propertyName, int index, boolean oldValue, boolean newValue) {
    if (oldValue != newValue) {
      fireIndexedPropertyChange(propertyName, index, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
    }
  }

  /**
   * Fires a property change of an integer property with the given name. If
   * the old value and the new value are not null and equal the event will not
   * be fired.
   *
   * @param propertyName
   *            the property name
   * @param index
   *            the index of the changed property
   * @param oldValue
   *            the old value
   * @param newValue
   *            the new value
   */
  public void fireIndexedPropertyChange(String propertyName, int index, int oldValue, int newValue) {
    if (oldValue != newValue) {
      fireIndexedPropertyChange(propertyName, index, Integer.valueOf(oldValue), Integer.valueOf(newValue));
    }
  }

  /**
   * Fires an {@link IndexedPropertyChangeEvent} with the given name, old
   * value, new value and index. As source the bean used to initialize this
   * instance is used. If the old value and the new value are not null and
   * equal the event will not be fired.
   *
   * @param propertyName
   *            the name of the property
   * @param index
   *            the index
   * @param oldValue
   *            the old value of the property
   * @param newValue
   *            the new value of the property
   */
  public void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
    firePropertyChange(new IndexedPropertyChangeEvent(sourceBean, propertyName, oldValue, newValue, index));
  }

  /**
   * Publishes a property change event to all listeners of that property. If
   * the event's old and new values are equal (but non-null), no event will be
   * published.
   */
  public void firePropertyChange(PropertyChangeEvent event) {
    String propertyName = event.getPropertyName();
    Object oldValue     = event.getOldValue();
    Object newValue     = event.getNewValue();

    if ((newValue != null) && (oldValue != null) && newValue.equals(oldValue)) {
      return;
    }

notifyEachListener:
    for (PropertyChangeListener p : listeners) {
      // unwrap listener proxies until we get a mismatched name or the real listener
      while(p instanceof PropertyChangeListenerProxy) {
        PropertyChangeListenerProxy proxy = (PropertyChangeListenerProxy) p;

        if (!equal(proxy.getPropertyName(), propertyName)) {
          continue notifyEachListener;
        }

        p = (PropertyChangeListener) proxy.getListener();
      }

      p.propertyChange(event);
    }
  }

  /**
   * Fires a property change of a boolean property with the given name. If the
   * old value and the new value are not null and equal the event will not be
   * fired.
   *
   * @param propertyName
   *            the property name
   * @param oldValue
   *            the old value
   * @param newValue
   *            the new value
   */
  public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
    firePropertyChange(propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
  }

  /**
   * Fires a property change of an integer property with the given name. If
   * the old value and the new value are not null and equal the event will not
   * be fired.
   *
   * @param propertyName
   *            the property name
   * @param oldValue
   *            the old value
   * @param newValue
   *            the new value
   */
  public void firePropertyChange(String propertyName, int oldValue, int newValue) {
    firePropertyChange(propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
  }

  /**
   * Fires a {@link PropertyChangeEvent} with the given name, old value and
   * new value. As source the bean used to initialize this instance is used.
   * If the old value and the new value are not null and equal the event will
   * not be fired.
   *
   * @param propertyName
   *            the name of the property
   * @param oldValue
   *            the old value of the property
   * @param newValue
   *            the new value of the property
   */
  public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    firePropertyChange(new PropertyChangeEvent(sourceBean, propertyName, oldValue, newValue));
  }

  /**
   * Unsubscribes {@code listener} from change notifications for all
   * properties. If the listener has multiple subscriptions, it will receive
   * one fewer notification when properties change. If the property name or
   * listener is null or not subscribed, this method silently does nothing.
   */
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    for (PropertyChangeListener p : listeners) {
      if (equals(null, listener, p)) {
        listeners.remove(p);

        return;
      }
    }
  }

  /**
   * Unsubscribes {@code listener} from change notifications for the property
   * named {@code propertyName}. If multiple subscriptions exist for {@code
   * listener}, it will receive one fewer notifications when the property
   * changes. If the property name or listener is null or not subscribed, this
   * method silently does nothing.
   */
  public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    for (PropertyChangeListener p : listeners) {
      if (equals(propertyName, listener, p)) {
        listeners.remove(p);

        return;
      }
    }
  }

  /**
   * Returns all subscribers. This includes both listeners subscribed to all
   * property changes and listeners subscribed to a single property.
   */
  public PropertyChangeListener[] getPropertyChangeListeners() {
    return listeners.toArray(new PropertyChangeListener[0]);    // 0 to avoid synchronization
  }

  /**
   * Returns the subscribers to be notified when {@code propertyName} changes.
   * This includes both listeners subscribed to all property changes and
   * listeners subscribed to the named property only.
   */
  public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
    List<PropertyChangeListener> result = new ArrayList<PropertyChangeListener>();

    for (PropertyChangeListener p : listeners) {
      if ((p instanceof PropertyChangeListenerProxy)
          && equal(propertyName, ((PropertyChangeListenerProxy) p).getPropertyName())) {
        result.add(p);
      }
    }

    return result.toArray(new PropertyChangeListener[result.size()]);
  }

  /**
   * Returns true if there are listeners registered to the property with the
   * given name.
   *
   * @param propertyName
   *            the name of the property
   * @return true if there are listeners registered to that property, false
   *         otherwise.
   */
  public boolean hasListeners(String propertyName) {
    for (PropertyChangeListener p : listeners) {
      if (!(p instanceof PropertyChangeListenerProxy)
          || equal(propertyName, ((PropertyChangeListenerProxy) p).getPropertyName())) {
        return true;
      }
    }

    return false;
  }

  private boolean equal(Object a, Object b) {
    return (a == b) || ((a != null) && a.equals(b));
  }

  /**
   * Returns true if two chains of PropertyChangeListenerProxies have the same
   * names in the same order and bottom out in the same event listener. This
   * method's signature is asymmetric to avoid allocating a proxy: if
   * non-null, {@code aName} represents the first property name and {@code a}
   * is its listener.
   */
  private boolean equals(String aName, EventListener a, EventListener b) {
    /*
     * Each iteration of the loop attempts to match a pair of property names
     * from a and b. If they don't match, the chains must not be equal!
     */
    while(b instanceof PropertyChangeListenerProxy) {
      PropertyChangeListenerProxy bProxy = (PropertyChangeListenerProxy) b;      // unwrap b
      String                      bName  = bProxy.getPropertyName();

      b = bProxy.getListener();

      if (aName == null) {
        if (!(a instanceof PropertyChangeListenerProxy)) {
          return false;
        }

        PropertyChangeListenerProxy aProxy = (PropertyChangeListenerProxy) a;    // unwrap a

        aName = aProxy.getPropertyName();
        a     = aProxy.getListener();
      }

      if (!equal(aName, bName)) {
        return false;    // not equal; a and b subscribe to different properties
      }

      aName = null;
    }

    return (aName == null) && equal(a, b);
  }
}
