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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap that supports null values
 * @author Don DeCoteau
 *
 */
public class ConcurrentHashMapEx extends ConcurrentHashMap {
  private static final long serialVersionUID = 1L;
  private static Object     NULL             = new Object();

  public ConcurrentHashMapEx() {}

  public ConcurrentHashMapEx(int initialCapacity) {
    super(initialCapacity);
  }

  public ConcurrentHashMapEx(Map map) {
    super(map);
  }

  public ConcurrentHashMapEx(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public ConcurrentHashMapEx(int initialCapacity, float loadFactor, int concurrencyLevel) {
    super(initialCapacity, loadFactor, concurrencyLevel);
  }

  @Override
  public Object put(Object key, Object value) {
    Object o = super.put(key, (value == null)
                              ? NULL
                              : value);

    return (o == NULL)
           ? null
           : o;
  }

  @Override
  public Object remove(Object key) {
    Object o = super.remove(key);

    return (o == NULL)
           ? null
           : o;
  }

  @Override
  public Object get(Object key) {
    Object o = super.get(key);

    return (o == NULL)
           ? null
           : o;
  }
}
