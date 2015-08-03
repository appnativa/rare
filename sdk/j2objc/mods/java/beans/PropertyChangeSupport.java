/*
 * @(#)PropertyChangeSupport.java   2013-02-16
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.beans;

import com.google.j2objc.annotations.Weak;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages a list of listeners to be notified when a property changes. Listeners
 * subscribe to be notified of all property changes, or of changes to a single
 * named property.
 *
 * <p>This class is thread safe. No locking is necessary when subscribing or
 * unsubscribing listeners, or when publishing events. Callers should be careful
 * when publishing events because listeners may not be thread safe.
 */
public class PropertyChangeSupport {

  /**
   * All listeners, including PropertyChangeListenerProxy listeners that are
   * only be notified when the assigned property is changed. This list may be
   * modified concurrently!
   */
  private transient List   listeners = new CopyOnWriteArrayList();
  @Weak
  private transient Object sourceBean;

  /**
   * Creates a new instance that uses the source bean as source for any event.
   *
   * @param sourceBean
   *            the bean used as source for all events.
   */
  public PropertyChangeSupport(Object sourceBean) {
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
    for (Object o : listeners) {
      PropertyChangeListener p = (PropertyChangeListener) o;
      // unwrap listener proxies until we get a mismatched name or the real listener
      while(p instanceof PropertyChangeListenerProxy) {
        PropertyChangeListenerProxy proxy = (PropertyChangeListenerProxy) p;

        if (!equal(proxy.getPropertyName(), propertyName)) {
          p=null;
          break;
        }

        p = (PropertyChangeListener) proxy.getListener();
      }
      if(p!=null) {
        p.propertyChange(event);
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
    for (Object o : listeners) {
      PropertyChangeListener p = (PropertyChangeListener) o;

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
    for (Object o : listeners) {
      PropertyChangeListener p = (PropertyChangeListener) o;

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
    PropertyChangeListener[] a = new PropertyChangeListener[listeners.size()];

    listeners.toArray(a);

    return a;
  }

  /**
   * Returns the subscribers to be notified when {@code propertyName} changes.
   * This includes both listeners subscribed to all property changes and
   * listeners subscribed to the named property only.
   */
  public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
    List<PropertyChangeListener> result = new ArrayList<PropertyChangeListener>();

    for (Object o : listeners) {
      PropertyChangeListener p = (PropertyChangeListener) o;

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
    for (Object o : listeners) {
      PropertyChangeListener p = (PropertyChangeListener) o;

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
