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

package com.appnativa.rare.platform.android.ui.view;

import android.widget.BaseAdapter;

import com.appnativa.util.ObjectCache;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aCachingAdapter extends BaseAdapter {
  protected ObjectCache objectCache;
  protected List        objectKeys;

  public aCachingAdapter(int capacity, int cache) {
    this(capacity, new ObjectCache(cache));
  }

  public aCachingAdapter(int capacity, ObjectCache cache) {
    objectKeys = new ArrayList(capacity);

    if (cache == null) {
      throw new NullPointerException("cache can't be null");
    }

    this.objectCache = cache;
  }

  public void add(Object key, Object obj) {
    objectKeys.add(key);

    if (obj != null) {
      objectCache.put(key, obj);
    }
  }

  public void clear() {
    objectKeys.clear();
    objectCache.clear();
  }

  public Object clear(int position) {
    return objectCache.remove(objectKeys.get(position));
  }

  public int indexOf(Object key) {
    return objectKeys.indexOf(key);
  }

  public Object remove(int position) {
    Object obj = objectCache.remove(objectKeys.get(position));

    objectKeys.remove(position);

    return obj;
  }

  public Object set(int position, Object key, Object value) {
    objectKeys.set(position, key);

    return objectCache.put(key, value);
  }

  /**
   * @param objectCache the objectCache to set
   */
  public void setObjectCache(ObjectCache objectCache) {
    this.objectCache = objectCache;
  }

  @Override
  public int getCount() {
    return objectKeys.size();
  }

  @Override
  public Object getItem(int position) {
    Object s   = objectKeys.get(position);
    Object obj = getObjectCache().get(s);

    if (obj == null) {
      obj = createNewItem(s);
      getObjectCache().put(s, obj);
    }

    return obj;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public Object getKey(int position) {
    return objectKeys.get(position);
  }

  /**
   * @return the objectCache
   */
  public ObjectCache getObjectCache() {
    return objectCache;
  }

  protected abstract Object createNewItem(Object key);
}
