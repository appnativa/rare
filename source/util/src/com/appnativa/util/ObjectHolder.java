/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.util;

/**
 * An object for holding other objects with ans associated type
 * ad a source
 *
 * @author Don DeCcoteau
 */
public class ObjectHolder implements Cloneable {
  public long   flags;
  public Object source;
  public Object type;
  public Object value;

  /**
   * Creates a new holder
   * @param value the value
   */
  public ObjectHolder(Object value) {
    set(null, value, 0);
  }

  /**
   * Creates a new holder
   * @param holder a holde to copy
   */
  public ObjectHolder(ObjectHolder holder) {
    if (holder != null) {
      set(holder.type, holder.value, holder.flags);
    }
  }

  /**
   * Creates a new holder
   *
   * @param value the value
   * @param flags associated flags
   */
  public ObjectHolder(Object value, long flags) {
    set(type, null, flags);
  }

  /**
   * Creates a new holder
   *
   * @param type  the type
   * @param value the value
   */
  public ObjectHolder(Object type, Object value) {
    set(type, value);
  }

  /**
   * Creates a new holder
   *
   * @param type  the type
   * @param value the value
   * @param flags associated flags
   */
  public ObjectHolder(Object type, Object value, long flags) {
    set(type, value, flags);
  }

  /**
   * Creates a new holder
   *
   * @param source the source
   * @param type  the type
   * @param value the value
   */
  public ObjectHolder(Object source, Object type, Object value) {
    set(type, value);
    this.source = source;
  }

  /**
   * Creates a new holder
   *
   * @param source the source
   * @param type  the type
   * @param value the value
   * @param flags associated flags
   */
  public ObjectHolder(Object source, Object type, Object value, long flags) {
    set(type, value, flags);
    this.source = source;
  }

  /**
   * Clears the holder of it's value
   */
  public void clear() {
    this.source = null;
    this.type   = null;
    this.value  = null;
    this.flags  = 0;
  }

  /**
   * Clears the holder of it's value
   */
  public void dispose() {
    clear();
  }

  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      throw new InternalError("Clone suhould be supported");
    }
  }

  public String toString() {
    if ((type == null) && (value == null)) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    if (source != null) {
      sb.append("[").append(source).append("]");
    }

    if (type != null) {
      sb.append(type);
    }

    sb.append("=");

    if (value != null) {
      sb.append(value);
    }

    if (flags != 0) {
      sb.append("|").append(flags);
    }

    return sb.toString();
  }

  public boolean typeEquals(Object type) {
    if (type == null) {
      return this.type == null;
    } else {
      return type.equals(this.type);
    }
  }

  public boolean valueEquals(Object value) {
    if (value == null) {
      return this.value == null;
    } else {
      return value.equals(this.value);
    }
  }

  public final void set(Object type, Object value) {
    this.type  = type;
    this.value = value;
  }

  public final void set(Object type, Object value, long flags) {
    this.type  = type;
    this.value = value;
    this.flags = flags;
  }

  public void setSource(Object source) {
    this.source = source;
  }

  public void setType(Object type) {
    this.type = type;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public Object getSource() {
    return source;
  }

  public Object getType() {
    return type;
  }

  public Object getValue() {
    return value;
  }
}
