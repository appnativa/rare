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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A map that provides some utility functions ontop
 * of another map
 *
 * @author Don DeCoteau
 *
 */
public class UtilityMap implements Map {
  private String keyPrefix;
  private Map    map;

  /**
   * Creates a new instance
   * @param map backing map
   */
  public UtilityMap(Map map) {
    this.map = map;
  }

  /**
   * Creates a new instance
   * @param map backing map
   * @param keyPrefix a string to use to prefix key passed in to map functions
   */
  public UtilityMap(Map map, String keyPrefix) {
    this.map       = map;
    this.keyPrefix = keyPrefix;
  }

  public void clear() {
    map.clear();
  }

  public boolean containsKey(Object key) {
    if (keyPrefix != null) {
      key = keyPrefix + key.toString();
    }

    return map.containsKey(key);
  }

  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }

  public Set entrySet() {
    return map.entrySet();
  }

  public boolean equals(Object o) {
    return map.equals(o);
  }

  public int hashCode() {
    return map.hashCode();
  }

  public Set keySet() {
    return map.keySet();
  }

  public Object put(Object key, Object value) {
    return getMap().put(key, value);
  }

  public void putAll(Map m) {
    getMap().putAll(m);
  }

  public Object remove(Object key) {
    if (keyPrefix != null) {
      key = keyPrefix + key.toString();
    }

    return getMap().remove(key);
  }

  public boolean removeBoolean(Object key, boolean def) {
    Object o = remove(key);

    if (o == null) {
      return def;
    }

    if (o instanceof Boolean) {
      return ((Boolean) o).booleanValue();
    }

    return o.equals("true");
  }

  public float removeFloat(Object key, float def) {
    Object o = remove(key);

    if (o == null) {
      return def;
    }

    if (o instanceof Number) {
      return ((Number) o).floatValue();
    }

    return SNumber.floatValue(o.toString());
  }

  public double removeInt(Object key, double def) {
    Object o = remove(key);

    if (o == null) {
      return def;
    }

    if (o instanceof Number) {
      return ((Number) o).doubleValue();
    }

    return SNumber.doubleValue(o.toString());
  }

  public int removeInt(Object key, int def) {
    Object o = remove(key);

    if (o == null) {
      return def;
    }

    if (o instanceof Number) {
      return ((Number) o).intValue();
    }

    return SNumber.intValue(o.toString());
  }

  public long removeLong(Object key, long def) {
    Object o = remove(key);

    if (o == null) {
      return def;
    }

    if (o instanceof Number) {
      return ((Number) o).longValue();
    }

    return SNumber.longValue(o.toString());
  }

  public String removeString(Object key) {
    Object o = remove(key);

    if (o == null) {
      return null;
    }

    if (o instanceof String) {
      return ((String) o);
    }

    return o.toString();
  }

  public int size() {
    return map.size();
  }

  public Collection values() {
    return map.values();
  }

  public void setKeyPrefix(String keyPrefix) {
    this.keyPrefix = keyPrefix;
  }

  public void setMap(Map map) {
    this.map = map;
  }

  public Object get(Object key) {
    if (keyPrefix != null) {
      key = keyPrefix + key.toString();
    }

    return map.get(key);
  }

  public boolean getBoolean(Object key, boolean def) {
    Object o = get(key);

    if (o == null) {
      return def;
    }

    if (o instanceof Boolean) {
      return ((Boolean) o).booleanValue();
    }

    return o.equals("true");
  }

  public float getFloat(Object key, float def) {
    Object o = get(key);

    if (o == null) {
      return def;
    }

    if (o instanceof Number) {
      return ((Number) o).floatValue();
    }

    return SNumber.floatValue(o.toString());
  }

  public double getInt(Object key, double def) {
    Object o = get(key);

    if (o == null) {
      return def;
    }

    if (o instanceof Number) {
      return ((Number) o).doubleValue();
    }

    return SNumber.doubleValue(o.toString());
  }

  public int getInt(Object key, int def) {
    Object o = get(key);

    if (o == null) {
      return def;
    }

    if (o instanceof Number) {
      return ((Number) o).intValue();
    }

    return SNumber.intValue(o.toString());
  }

  public String getKeyPrefix() {
    return keyPrefix;
  }

  public long getLong(Object key, long def) {
    Object o = get(key);

    if (o == null) {
      return def;
    }

    if (o instanceof Number) {
      return ((Number) o).longValue();
    }

    return SNumber.longValue(o.toString());
  }

  public Map getMap() {
    return map;
  }

  public String getString(Object key) {
    Object o = get(key);

    if (o == null) {
      return null;
    }

    if (o instanceof String) {
      return ((String) o);
    }

    return o.toString();
  }

  public boolean isEmpty() {
    return getMap().isEmpty();
  }
}
