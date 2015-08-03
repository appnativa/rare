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

package com.appnativa.rare.platform.apple.ui.util;

import com.appnativa.util.ObjectCache.iCacheReference;

import com.google.j2objc.annotations.Weak;

public class AppleCacheReference implements iCacheReference {
  Object       key;
  Object       strong;
  @Weak
  Object       value;
  private long timestamp;

  public AppleCacheReference(Object key, Object value) {
    super();
    this.key   = key;
    this.value = value;
    timestamp  = System.currentTimeMillis();
  }

  @Override
  public void clear() {
    strong = null;
    value  = null;
  }

  @Override
  public iCacheReference makeStrong() {
    strong = value;

    return this;
  }

  @Override
  public Object get() {
    return value;
  }

  @Override
  public Object getKey() {
    return key;
  }

  @Override
  public long getTimestamp() {
    return timestamp;
  }
}
