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

import java.lang.ref.SoftReference;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * This class provides for the cacheing of generic objects in a thread-safe concurrent manner.
 * It provides for the automatic purging of stale objects, either synchronously, or asynchronously.
 * <p>Synchronous purging occurs when a new object is added to the cache via the <code>put()</code>
 * method, if the <code>purgeInline</code> flag is set to<code> true</code>. In this
 * scenario the cache checks to see if it needs to be purged and if so calls the
 * purge function.
 *
 * <p>Asynchronous purging takes place via whatever method you choose when inline purging is not used.
 *
 * <p>With inline purging, purges only occur when the cache is full or overflowing. Therefore, to
 * prevent continuous purging (once the cache becomes full), multiple objects are
 * purged from the cache during a purge cycle. The number of objects purged during
 * a purge cycle is determined by the purge ratio. The purge ratio is a decimal
 * value between 0.01 and 0.75. The purge ratio is multiplied by the cache size
 * and the resulting value determines the number of objects to be removed from the
 * cache. The default value for the purge ratio is 0.25. That means that during a
 * purge cycle 1/4 of the objects will be discarded.
 *
 * @author Don DeCoteau
 */
public class ObjectCache implements Runnable {
  private static CacheComparator        cacheComparator = new CacheComparator();
  private static iCacheReferenceCreator referenceCreator;
  public Future                         purgeFurture;
  ConcurrentHashMap                     cache;
  private int                           bufferSize = Integer.MAX_VALUE;

  /** flag indicating if the purge function is running */
  protected volatile boolean purgeRunning = false;

  /** the percentage of elements discarded on each purge */
  private float      puregRatio   = .25f;
  private boolean    purgeInline  = true;
  private boolean    strongBuffer = false;
  private int        maxSizeReached;
  private iScheduler purgeHandler;

  public ObjectCache() {
    cache = new ConcurrentHashMap();
  }

  public ObjectCache(int initialCapacity) {
    cache = new ConcurrentHashMap(initialCapacity);
  }

  public ObjectCache(int initialCapacity, float loadFactor, int concurrencyLevel) {
    cache = new ConcurrentHashMap(initialCapacity, loadFactor, concurrencyLevel);
  }

  /**
   * Empties the cache
   */
  public void clear() {
    if (cache.size() > maxSizeReached) {
      maxSizeReached = cache.size();
    }

    iCacheReference[] a = (iCacheReference[]) cache.values().toArray(new iCacheReference[cache.size()]);
    iCacheReference   c;
    int               len = (a == null)
                            ? 0
                            : a.length;
    int               i   = 0;

    cache.clear();
    i = 0;

    while(i < len) {
      c = a[i++];

      if (c != null) {
        c.clear();
      }
    }
  }

  /**
   * Purges the cache
   */
  public void purge() {
    run();
  }

  /**
   *  Associates the specified value with the specified key in this map
   *  (optional operation).  If the map previously contained a mapping for
   *  the key, the old value is replaced by the specified value.
   *
   *  @param key key with which the specified value is to be associated
   *  @param value value to be associated with the specified key
   *  @return the previous value associated with <tt>key</tt>, or
   *          <tt>null</tt> if there was no mapping for <tt>key</tt>.
   *          (A <tt>null</tt> return can also indicate that the map
   *          previously associated <tt>null</tt> with <tt>key</tt>,
   *          if the implementation supports <tt>null</tt> values.)
   */
  public Object put(Object key, Object value) {
    int cacheSize = cache.size();

    if (cacheSize > maxSizeReached) {
      maxSizeReached = cacheSize;
    }

    if (cacheSize > bufferSize) {
      if (purgeHandler != null) {
        if (!purgeRunning) {
          purgeHandler.scheduleTask(this);
        }
      } else if (isPurgeInline()) {
        run();
      }
    }

    iCacheReference c = createCacheReference(key, value);

    if (strongBuffer) {
      c.makeStrong();
    }

    c     = (iCacheReference) cache.put(key, c);
    value = (c == null)
            ? null
            : c.get();

    if (c != null) {
      c.clear();
    }

    return value;
  }

  /**
   * Removes the mapping for a key from this map if it is present
   *
   * @param key key whose mapping is to be removed from the map
   * @return the previous value associated with <tt>key</tt>, or
   *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
   */
  public Object remove(Object key) {
    iCacheReference c = (iCacheReference) cache.remove(key);
    Object          v = (c == null)
                        ? null
                        : c.get();

    if (c != null) {
      c.clear();
    }

    return v;
  }

  /**
   * Run method for the timer task. If the cache is full, it will cause the cache
   * to be purged.
   */
  public void run() {
    if (purgeRunning) {
      return;
    }

    try {
      int cacheSize = cache.size();

      if (cacheSize > maxSizeReached) {
        maxSizeReached = cacheSize;
      }

      if (cacheSize < bufferSize) {
        return;
      }

      purgeRunning = true;

      int dump = (int) (puregRatio * bufferSize);

      if (dump < 1) {
        dump = 1;
      }

      iCacheReference[] a = (iCacheReference[]) cache.values().toArray(new iCacheReference[cache.size()]);
      iCacheReference   c;
      int               len = (a == null)
                              ? 0
                              : a.length;
      int               i   = 0;

      Arrays.sort(a, 0, len, cacheComparator);
      i = 0;

      while(i < dump) {
        c = a[i++];

        if (c != null) {
          Object key = c.getKey();

          if (key != null) {
            cache.remove(key);
          }

          c.clear();
        }
      }
    } finally {
      purgeRunning = false;
      purgeFurture = null;
    }
  }

  /**
   * Gets the number of entries in the cache
   * @return the number of entries in the cache
   */
  public int size() {
    return cache.size();
  }

  /**
   * Sets the size of the cache buffer
   *
   * @param size  the size
   */
  public void setBufferSize(int size) {
    this.bufferSize = size;
  }

  /**
   * Sets the value of the <code>puregRatio</code> attribute. Values can range from <b>0.01</b>
   * to <b>0.75</b>.
   *
   * @param ratio  the ratio
   */
  public void setPuregRatio(float ratio) {
    this.puregRatio = ratio;
  }

  /**
   * Sets the purge handler to use to purge objects.
   * Setting as handler will automatically disable inline purging
   * @param handler the purge handler
   */
  public void setPurgeHandler(iScheduler handler) {
    this.purgeHandler = handler;
  }

  /**
   * Sets whether cache purging will take place in-line.
   * IF true then then a full cache will be purged during
   * attempts to add to the cache other the cache is expected to
   * be purgeg manually.
   *
   * @param purgeInline if the purge should happen in-line; false otherwise
   */
  public void setPurgeInline(boolean purgeInline) {
    this.purgeInline = purgeInline;
  }

  public static void setReferenceCreator(iCacheReferenceCreator creator) {
    referenceCreator = creator;
  }

  /**
   * Set the cache to use strong references for the entries in the cache.
   * This prevents the objects on the cache from being automatically
   * garbage collected. Objects in the case can only be removed via the purge method.
   *
   * <p>
   * This method must be called prior to populating the cache.
   * </p>
   * @param strong true to use string references; false otherwise
   */
  public void setStrongReferences(boolean strong) {
    this.strongBuffer = strong;
  }

  /**
   *   Returns the value to which the specified key is mapped,
   *   or null if this map contains no mapping for the key.
   *
   *   @param key the key whose associated value is to be returned
   *   @return the value to which the specified key is mapped, or
   *           null if this map contains no mapping for the key
   */
  public Object get(Object key) {
    iCacheReference c = (iCacheReference) cache.get(key);

    if (c == null) {
      return null;
    }

    Object v = c.get();

    if (v == null) {
      cache.remove(key);
      c.clear();
    }

    return v;
  }

  /**
   * Get the size of the buffer. This is the maximim
   * number of entries that the cache can contain
   * before purging is initiated.
   *
   * @return the buffer size
   */
  public int getBufferSize() {
    return bufferSize;
  }

  /**
   * Gets the number of entries in the cache
   * @return the number of entries in the cache
   */
  public int getCacheSize() {
    return cache.size();
  }

  /**
   * Gets the value of the <code>puregRatio</code> attribute.
   *
   * @return   an object of type <code>double</code> representing the <code>puregRatio</code>
   *           attribute
   */
  public double getPuregRatio() {
    return puregRatio;
  }

  /**
   * Returns <tt>true</tt> if this map contains no key-value mappings.
   *
   * @return <tt>true</tt> if this map contains no key-value mappings
   */
  public boolean isEmpty() {
    return cache.size() == 0;
  }

  /**
   * Returns whether cache purging will take place in-line.
   * If true then then a full cache will be purged during
   * attempts to add to the cache other the cache is expected to
   * be purgeg manually.
   *
   * @return true if the purge will happen in-line; false otherwise
   */
  public boolean isPurgeInline() {
    return purgeInline;
  }

  /**
   * Returns whehter this cache will utilize strong references
   * @return true if using string references; false otherwise
   */
  public boolean isStrongReferences() {
    return strongBuffer;
  }

  protected iCacheReference createCacheReference(Object key, Object value) {
    if (referenceCreator != null) {
      return referenceCreator.createCacheReference(key, value);
    }

    return new CacheReference(key, value);
  }

  public static interface iCacheReference {
    void clear();

    iCacheReference makeStrong();

    Object get();

    Object getKey();

    long getTimestamp();
  }


  public static interface iCacheReferenceCreator {
    iCacheReference createCacheReference(Object key, Object value);
  }


  protected static class CacheComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      iCacheReference c1 = (iCacheReference) o1;
      iCacheReference c2 = (iCacheReference) o2;

      if ((c1 == null) || (c2 == null)) {
        if (c1 == c2) {
          return 0;
        }

        return (c1 == null)
               ? -1
               : 1;
      }

      return (int) (c1.getTimestamp() - c2.getTimestamp());
    }
  }


  /**
   * Class representing a cachable reference
   * @param
   * @param
   */
  protected static class CacheReference extends SoftReference implements iCacheReference {
    Object key;
    Object strong;
    long   timestamp;

    public CacheReference(Object key, Object referent) {
      super(referent);
      this.key  = key;
      timestamp = System.currentTimeMillis();
    }

    public void clear() {
      super.clear();
      key    = null;
      strong = null;
    }

    public iCacheReference makeStrong() {
      strong = super.get();

      return this;
    }

    public Object get() {
      timestamp = System.currentTimeMillis();

      return super.get();
    }

    public Object getKey() {
      return key;
    }

    public long getTimestamp() {
      return timestamp;
    }
  }
}
