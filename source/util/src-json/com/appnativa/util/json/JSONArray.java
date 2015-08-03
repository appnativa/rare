/**
 * Copyright (c) 2002 JSON.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * The Software shall be used for Good, not Evil.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.appnativa.util.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.appnativa.util.FilterableList;
import com.appnativa.util.aFilterableList;
import com.appnativa.util.iFilter;
import com.appnativa.util.iFilterableList;
import com.appnativa.util.json.JSONTokener.iWatcher;

/**
 * A JSONArray is an ordered sequence of values. Its external text form is a
 * string wrapped in square brackets with commas separating the values. The
 * internal form is an object having <code>get</code> and <code>opt</code>
 * methods for accessing the values by index, and <code>put</code> methods for
 * adding or replacing values. The values can be any of these types:
 * <code>Boolean</code>, <code>JSONArray</code>, <code>JSONObject</code>,
 * <code>Number</code>, <code>String</code>, or the
 * <code>JSONObject.NULL object</code>.
 * <p>
 * The constructor can convert a JSON text into a Java object. The
 * <code>toString</code> method converts to JSON text.
 * <p>
 * A <code>get</code> method returns a value if one can be found, and throws an
 * exception if one cannot be found. An <code>opt</code> method returns a
 * default value instead of throwing an exception, and so is useful for
 * obtaining optional values.
 * <p>
 * The generic <code>get()</code> and <code>opt()</code> methods return an
 * object which you can cast or query for type. There are also typed
 * <code>get</code> and <code>opt</code> methods that do type checking and type
 * coercion for you.
 * <p>
 * The texts produced by the <code>toString</code> methods strictly conform to
 * JSON syntax rules. The constructors are more forgiving in the texts they will
 * accept:
 * <ul>
 * <li>An extra <code>,</code>&nbsp;<small>(comma)</small> may appear just
 * before the closing bracket.</li>
 * <li>The <code>null</code> value will be inserted when there is <code>,</code>
 * &nbsp;<small>(comma)</small> elision.</li>
 * <li>Strings may be quoted with <code>'</code>&nbsp;<small>(single
 * quote)</small>.</li>
 * <li>Strings do not need to be quoted at all if they do not begin with a quote
 * or single quote, and if they do not contain leading or trailing spaces, and
 * if they do not contain any of these characters:
 * <code>{ } [ ] / \ : , = ; #</code> and if they do not look like numbers and
 * if they are not the reserved words <code>true</code>, <code>false</code>, or
 * <code>null</code>.</li>
 * <li>Values can be separated by <code>;</code> <small>(semicolon)</small> as
 * well as by <code>,</code> <small>(comma)</small>.</li>
 * <li>Numbers may have the <code>0-</code> <small>(octal)</small> or
 * <code>0x-</code> <small>(hex)</small> prefix.</li>
 * </ul>
 *
 * @author JSON.org
 * @version 2009-04-13
 */
public class JSONArray implements List, Cloneable {
  protected iFilterableList filterableList;

  /**
   * The arrayList where the JSONArray's properties are kept.
   */
  protected ArrayList       myArrayList;

  protected String          name;

  /**
   * Construct an empty JSONArray.
   */
  public JSONArray() {
    this.myArrayList = new ArrayList();
  }

  /**
   * Construct a JSONArray from a Collection.
   * 
   * @param collection
   *          A Collection.
   */
  public JSONArray(Collection collection) {
    this.myArrayList = (collection == null) ? new ArrayList() : new ArrayList(collection);
  }

  /**
   * Construct a JSONArray from a JSONTokener.
   * 
   * @param x
   *          A JSONTokener
   * @throws JSONException
   *           If there is a syntax error.
   */
  public JSONArray(JSONTokener x) throws JSONException {
    this(null, x);
  }

  /**
   * Construct a JSONArray from an array
   * 
   * @throws JSONException
   *           If not an array.
   */
  public JSONArray(List array) throws JSONException {
    this();

    int length = array.size();

    for (int i = 0; i < length; i += 1) {
      this.put(array.get(i));
    }
  }

  /**
   * Construct a JSONArray from a source JSON text.
   * 
   * @param source
   *          A string that begins with <code>[</code>&nbsp;<small>(left
   *          bracket)</small> and ends with <code>]</code>&nbsp;<small>(right
   *          bracket)</small>.
   * @throws JSONException
   *           If there is a syntax error.
   */
  public JSONArray(String source) throws JSONException {
    this(new JSONTokener(source));
  }

  /**
   * Construct a JSONArray from a JSONTokener.
   * 
   * @param name
   *          the name of the array or null
   * @param x
   *          A JSONTokener
   * @throws JSONException
   *           If there is a syntax error.
   */
  public JSONArray(String name, JSONTokener x) throws JSONException {
    this();
    this.setName(name);
    iWatcher watcher = x.getWatcher();
    char c = x.nextClean();
    char q;
    if (watcher != null) {
      watcher.willParseArray(this);
    }
    if (c == '[') {
      q = ']';
    } else if (c == '(') {
      q = ')';
    } else {
      throw x.syntaxError("A JSONArray text must start with '['");
    }
    try {
      if (x.nextClean() == ']') {
        return;
      }

      x.back();
      Object value;
      for (;;) {
        if (x.nextClean() == ',') {
          x.back();
          value = null;
        } else {
          x.back();
          value = x.nextValue(name);
        }
        if (watcher != null) {
          value = watcher.valueEncountered(this, name, value);
          if (x.isTerminateParsing()) {
            return;
          }
          if (value != null) {
            myArrayList.add(value);
          }
        } else {
          myArrayList.add(value);
        }
        c = x.nextClean();

        switch (c) {
          case ';':
          case ',':
            if (x.nextClean() == ']') {
              return;
            }

            x.back();

            break;

          case ']':
          case ')':
            if (q != c) {
              throw x.syntaxError("Expected a '" + new Character(q) + "'");
            }

            return;

          default:
            throw x.syntaxError("Expected a ',' or ']'");
        }
      }
    } finally {
      if (watcher != null) {
        watcher.didParseArray(this);
      }

    }
  }

  public void add(int index, Object element) {
    myArrayList.add(index, element);
  }

  public boolean add(Object e) {
    return myArrayList.add(e);
  }

  public boolean addAll(Collection c) {
    return myArrayList.addAll(c);
  }

  public boolean addAll(int index, Collection c) {
    return myArrayList.addAll(index, c);
  }

  public int chop(int len) {
    return aFilterableList.chop(myArrayList, len);
  }

  public void clear() {
    myArrayList.clear();
  }

  public Object clone() {
    return new JSONArray(new ArrayList(this.myArrayList));
  }

  public boolean contains(Object o) {
    return myArrayList.contains(o);
  }

  public boolean containsAll(Collection c) {
    return myArrayList.containsAll(c);
  }

  public boolean equals(Object o) {
    return myArrayList.equals(o);
  }

  public JSONObject findJSONObject(String key, iFilter filter) {
    return findJSONObject(key, filter, 0);
  }

  public JSONObject findJSONObject(String key, iFilter filter, int startIndex) {
    ArrayList list = myArrayList;
    int len = myArrayList.size();
    for (int i = startIndex; i < len; i++) {
      JSONObject o = (JSONObject) list.get(i);
      Object v = o.opt(key);
      if (v != null && filter.passes(v, null)) {
        return o;
      }
    }
    return null;
  }

  public JSONObject findJSONObject(String key, String value) {
    return findJSONObject(key, value, 0);
  }

  public JSONObject findJSONObject(String key, String value, int startIndex) {
    ArrayList list = myArrayList;
    int len = myArrayList.size();
    for (int i = startIndex; i < len; i++) {
      JSONObject o = (JSONObject) list.get(i);
      Object v = o.opt(key);
      if (v != null && v.equals(value)) {
        return o;
      }
    }
    return null;
  }

  public int findJSONObjectIndex(String key, iFilter filter) {
    return findJSONObjectIndex(key, filter, 0);
  }

  public int findJSONObjectIndex(String key, iFilter filter, int startIndex) {
    ArrayList list = myArrayList;
    int len = myArrayList.size();
    for (int i = startIndex; i < len; i++) {
      JSONObject o = (JSONObject) list.get(i);
      Object v = o.opt(key);
      if (v != null && filter.passes(v, null)) {
        return i;
      }
    }
    return -1;
  }

  public int findJSONObjectIndex(String key, String value, int startIndex) {
    ArrayList list = myArrayList;
    int len = myArrayList.size();
    for (int i = startIndex; i < len; i++) {
      JSONObject o = (JSONObject) list.get(i);
      Object v = o.opt(key);
      if (v != null && v.equals(value)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Get the object value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return An object value.
   * @throws JSONException
   *           If there is no value for the index.
   */
  public Object get(int index) throws JSONException {
    Object o = opt(index);

    if (o == null) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }

    return o;
  }

  /**
   * Get the boolean value associated with an index. The string values "true"
   * and "false" are converted to boolean.
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The truth.
   * @throws JSONException
   *           If there is no value for the index or if the value is not
   *           convertable to boolean.
   */
  public boolean getBoolean(int index) throws JSONException {
    Object o = get(index);

    if (o.equals(Boolean.FALSE) || ((o instanceof String) && ((String) o).equalsIgnoreCase("false"))) {
      return false;
    } else if (o.equals(Boolean.TRUE) || ((o instanceof String) && ((String) o).equalsIgnoreCase("true"))) {
      return true;
    }

    throw new JSONException("JSONArray[" + index + "] is not a Boolean.");
  }

  /**
   * Get the double value associated with an index.
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   * @throws JSONException
   *           If the key is not found or if the value cannot be converted to a
   *           number.
   */
  public double getDouble(int index) throws JSONException {
    Object o = get(index);

    try {
      return (o instanceof Number) ? ((Number) o).doubleValue() : Double.valueOf((String) o).doubleValue();
    } catch (Exception e) {
      throw new JSONException("JSONArray[" + index + "] is not a number.");
    }
  }

  /**
   * Gets the filterable list instance for this JSON array
   * 
   * @return the filterable list instance for this JSON array
   */
  public iFilterableList getiFilterableList() {
    if (filterableList == null) {
      filterableList = new FilterableList(myArrayList);
    }

    return filterableList;
  }

  /**
   * Get the int value associated with an index.
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   * @throws JSONException
   *           If the key is not found or if the value cannot be converted to a
   *           number. if the value cannot be converted to a number.
   */
  public int getInt(int index) throws JSONException {
    Object o = get(index);

    return (o instanceof Number) ? ((Number) o).intValue() : (int) getDouble(index);
  }

  /**
   * Get the JSONArray associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A JSONArray value.
   * @throws JSONException
   *           If there is no value for the index. or if the value is not a
   *           JSONArray
   */
  public JSONArray getJSONArray(int index) throws JSONException {
    Object o = get(index);

    if (o instanceof JSONArray) {
      return (JSONArray) o;
    }

    throw new JSONException("JSONArray[" + index + "] is not a JSONArray.");
  }

  /**
   * Get the JSONObject associated with an index.
   * 
   * @param index
   *          subscript
   * @return A JSONObject value.
   * @throws JSONException
   *           If there is no value for the index or if the value is not a
   *           JSONObject
   */
  public JSONObject getJSONObject(int index) throws JSONException {
    Object o = get(index);

    if (o instanceof JSONObject) {
      return (JSONObject) o;
    }

    throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
  }

  /**
   * Get the number of elements in the JSONArray, included nulls.
   *
   * @return The length (or size).
   */
  public int getLength() {
    return this.myArrayList.size();
  }

  /**
   * Get the long value associated with an index.
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   * @throws JSONException
   *           If the key is not found or if the value cannot be converted to a
   *           number.
   */
  public long getLong(int index) throws JSONException {
    Object o = get(index);

    return (o instanceof Number) ? ((Number) o).longValue() : (long) getDouble(index);
  }

  /**
   * Gets the name of the array
   * 
   * @return the name of the array or null of no name was specified
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the backing list for the JSON array
   * 
   * @return the backing list for the JSON array
   */
  public List getObjectList() {
    return myArrayList;
  }

  /**
   * Get the string associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A string value.
   * @throws JSONException
   *           If there is no value for the index.
   */
  public String getString(int index) throws JSONException {
    return get(index).toString();
  }

  public int hashCode() {
    return myArrayList.hashCode();
  }

  public int indexOf(Object o) {
    return myArrayList.indexOf(o);
  }

  public boolean isEmpty() {
    return myArrayList.isEmpty();
  }

  /**
   * Determine if the value is null.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return true if the value at the index is null, or if there is no value.
   */
  public boolean isNull(int index) {
    return JSONObject.NULL.equals(opt(index));
  }

  public Iterator iterator() {
    return myArrayList.iterator();
  }

  public String join() {
    return join(",");
  }

  /**
   * Make a string from the contents of this JSONArray. The
   * <code>separator</code> string is inserted between each element. Warning:
   * This method assumes that the data structure is acyclical.
   * 
   * @param separator
   *          A string that will be inserted between the elements.
   * @return a string.
   * @throws JSONException
   *           If the array contains an invalid number.
   */
  public String join(String separator) throws JSONException {
    int len = length();
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < len; i += 1) {
      if (i > 0) {
        sb.append(separator);
      }

      sb.append(JSONObject.valueToString(this.myArrayList.get(i)));
    }

    return sb.toString();
  }

  public int lastIndexOf(Object o) {
    return myArrayList.lastIndexOf(o);
  }

  /**
   * Get the number of elements in the JSONArray, included nulls.
   *
   * @return The length (or size).
   */
  public int length() {
    return this.myArrayList.size();
  }

  public ListIterator listIterator() {
    return myArrayList.listIterator();
  }

  public ListIterator listIterator(int index) {
    return myArrayList.listIterator(index);
  }

  /**
   * Get the optional object value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return An object value, or null if there is no object at that index.
   */
  public Object opt(int index) {
    return ((index < 0) || (index >= length())) ? null : this.myArrayList.get(index);
  }

  /**
   * Get the optional boolean value associated with an index. It returns false
   * if there is no value at that index, or if the value is not Boolean.TRUE or
   * the String "true".
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The truth.
   */
  public boolean optBoolean(int index) {
    return optBoolean(index, false);
  }

  /**
   * Get the optional boolean value associated with an index. It returns the
   * defaultValue if there is no value at that index or if it is not a Boolean
   * or the String "true" or "false" (case insensitive).
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          A boolean default.
   * @return The truth.
   */
  public boolean optBoolean(int index, boolean defaultValue) {
    try {
      return getBoolean(index);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Get the optional double value associated with an index. NaN is returned if
   * there is no value for the index, or if the value is not a number and cannot
   * be converted to a number.
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   */
  public double optDouble(int index) {
    return optDouble(index, Double.NaN);
  }

  /**
   * Get the optional double value associated with an index. The defaultValue is
   * returned if there is no value for the index, or if the value is not a
   * number and cannot be converted to a number.
   *
   * @param index
   *          subscript
   * @param defaultValue
   *          The default value.
   * @return The value.
   */
  public double optDouble(int index, double defaultValue) {
    try {
      return getDouble(index);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Get the optional int value associated with an index. Zero is returned if
   * there is no value for the index, or if the value is not a number and cannot
   * be converted to a number.
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   */
  public int optInt(int index) {
    return optInt(index, 0);
  }

  /**
   * Get the optional int value associated with an index. The defaultValue is
   * returned if there is no value for the index, or if the value is not a
   * number and cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          The default value.
   * @return The value.
   */
  public int optInt(int index, int defaultValue) {
    try {
      return getInt(index);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Get the optional JSONArray associated with an index.
   * 
   * @param index
   *          subscript
   * @return A JSONArray value, or null if the index has no value, or if the
   *         value is not a JSONArray.
   */
  public JSONArray optJSONArray(int index) {
    Object o = opt(index);

    return (o instanceof JSONArray) ? (JSONArray) o : null;
  }

  /**
   * Get the optional JSONObject associated with an index. Null is returned if
   * the key is not found, or null if the index has no value, or if the value is
   * not a JSONObject.
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A JSONObject value.
   */
  public JSONObject optJSONObject(int index) {
    Object o = opt(index);

    return (o instanceof JSONObject) ? (JSONObject) o : null;
  }

  /**
   * Get the optional long value associated with an index. Zero is returned if
   * there is no value for the index, or if the value is not a number and cannot
   * be converted to a number.
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   */
  public long optLong(int index) {
    return optLong(index, 0);
  }

  /**
   * Get the optional long value associated with an index. The defaultValue is
   * returned if there is no value for the index, or if the value is not a
   * number and cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          The default value.
   * @return The value.
   */
  public long optLong(int index, long defaultValue) {
    try {
      return getLong(index);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Get the optional string value associated with an index. It returns an empty
   * string if there is no value at that index. If the value is not a string and
   * is not null, then it is coverted to a string.
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A String value.
   */
  public String optString(int index) {
    return optString(index, "");
  }

  /**
   * Get the optional string associated with an index. The defaultValue is
   * returned if the key is not found.
   *
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          The default value.
   * @return A String value.
   */
  public String optString(int index, String defaultValue) {
    Object o = opt(index);

    return (o != null) ? o.toString() : defaultValue;
  }

  /**
   * Append a boolean value. This increases the array's length by one.
   *
   * @param value
   *          A boolean value.
   * @return this.
   */
  public JSONArray put(boolean value) {
    put(value ? Boolean.TRUE : Boolean.FALSE);

    return this;
  }

  /**
   * Put a value in the JSONArray, where the value will be a JSONArray which is
   * produced from a Collection.
   * 
   * @param value
   *          A Collection value.
   * @return this.
   */
  public JSONArray put(Collection value) {
    put(new JSONArray(value));

    return this;
  }

  /**
   * Append a double value. This increases the array's length by one.
   *
   * @param value
   *          A double value.
   * @throws JSONException
   *           if the value is not finite.
   * @return this.
   */
  public JSONArray put(double value) throws JSONException {
    Double d = new Double(value);

    JSONObject.testValidity(d);
    put(d);

    return this;
  }

  /**
   * Append an int value. This increases the array's length by one.
   *
   * @param value
   *          An int value.
   * @return this.
   */
  public JSONArray put(int value) {
    put(new Integer(value));

    return this;
  }

  /**
   * Put or replace a boolean value in the JSONArray. If the index is greater
   * than the length of the JSONArray, then null elements will be added as
   * necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          A boolean value.
   * @return this.
   * @throws JSONException
   *           If the index is negative.
   */
  public JSONArray put(int index, boolean value) throws JSONException {
    put(index, value ? Boolean.TRUE : Boolean.FALSE);

    return this;
  }

  /**
   * Put a value in the JSONArray, where the value will be a JSONArray which is
   * produced from a Collection.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          A Collection value.
   * @return this.
   * @throws JSONException
   *           If the index is negative or if the value is not finite.
   */
  public JSONArray put(int index, Collection value) throws JSONException {
    put(index, new JSONArray(value));

    return this;
  }

  /**
   * Put or replace a double value. If the index is greater than the length of
   * the JSONArray, then null elements will be added as necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          A double value.
   * @return this.
   * @throws JSONException
   *           If the index is negative or if the value is not finite.
   */
  public JSONArray put(int index, double value) throws JSONException {
    put(index, new Double(value));

    return this;
  }

  /**
   * Put or replace an int value. If the index is greater than the length of the
   * JSONArray, then null elements will be added as necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          An int value.
   * @return this.
   * @throws JSONException
   *           If the index is negative.
   */
  public JSONArray put(int index, int value) throws JSONException {
    put(index, new Integer(value));

    return this;
  }

  /**
   * Put or replace a long value. If the index is greater than the length of the
   * JSONArray, then null elements will be added as necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          A long value.
   * @return this.
   * @throws JSONException
   *           If the index is negative.
   */
  public JSONArray put(int index, long value) throws JSONException {
    put(index, new Long(value));

    return this;
  }

  /**
   * Put a value in the JSONArray, where the value will be a JSONObject which is
   * produced from a Map.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          The Map value.
   * @return this.
   * @throws JSONException
   *           If the index is negative or if the the value is an invalid
   *           number.
   */
  public JSONArray put(int index, Map value) throws JSONException {
    if (!(value instanceof JSONObject)) {
      value = new JSONObject(value);
    }

    JSONObject.testValidity(value);

    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }

    if (index < length()) {
      this.myArrayList.set(index, value);
    } else {
      while (index != length()) {
        put(JSONObject.NULL);
      }

      put(value);
    }

    return this;
  }

  /**
   * Put or replace an object value in the JSONArray. If the index is greater
   * than the length of the JSONArray, then null elements will be added as
   * necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          The value to put into the array. The value should be a Boolean,
   *          Double, Integer, JSONArray, JSONObject, Long, or String, or the
   *          JSONObject.NULL object.
   * @return this.
   * @throws JSONException
   *           If the index is negative or if the the value is an invalid
   *           number.
   */
  public JSONArray put(int index, Object value) throws JSONException {
    JSONObject.testValidity(value);

    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }

    if (index < length()) {
      this.myArrayList.set(index, value);
    } else {
      while (index != length()) {
        put(JSONObject.NULL);
      }

      put(value);
    }

    return this;
  }

  /**
   * Append an long value. This increases the array's length by one.
   *
   * @param value
   *          A long value.
   * @return this.
   */
  public JSONArray put(long value) {
    put(new Long(value));

    return this;
  }

  /**
   * Put a value in the JSONArray, where the value will be a JSONObject which is
   * produced from a Map.
   * 
   * @param value
   *          A Map value.
   * @return this.
   */
  public JSONArray put(Map value) {
    if (!(value instanceof JSONObject)) {
      value = new JSONObject(value);
    }

    this.myArrayList.add(value);

    return this;
  }

  /**
   * Append an object value. This increases the array's length by one.
   * 
   * @param value
   *          An object value. The value should be a Boolean, Double, Integer,
   *          JSONArray, JSONObject, Long, or String, or the JSONObject.NULL
   *          object.
   * @return this.
   */
  public JSONArray put(Object value) {
    this.myArrayList.add(value);

    return this;
  }

  /**
   * Remove an index and close the hole.
   * 
   * @param index
   *          The index of the element to be removed.
   * @return The value that was associated with the index, or null if there was
   *         no value.
   */
  public Object remove(int index) {
    Object o = opt(index);

    this.myArrayList.remove(index);

    return o;
  }

  public boolean remove(Object o) {
    return myArrayList.remove(o);
  }

  public boolean removeAll(Collection c) {
    return myArrayList.removeAll(c);
  }

  public boolean retainAll(Collection c) {
    return myArrayList.retainAll(c);
  }

  public Object set(int index, Object element) {
    return myArrayList.set(index, element);
  }

  /**
   * Sets the name of the array
   * 
   * @param name
   *          the name of the array
   */
  public void setName(String name) {
    this.name = name;
  }

  public int size() {
    return this.myArrayList.size();
  }

  public JSONArray slice(int start) {
    return slice(start, size());
  }

  public JSONArray slice(int start, int end) {
    List list = myArrayList;
    int len = list.size();

    if (end < 0) {
      end = len - end;
    }

    if (start >= end) {
      return new JSONArray();
    }

    if (start < 0) {
      throw new IllegalArgumentException("start<0");
    }

    ArrayList fl = new ArrayList(end - start);

    while (start < end) {
      fl.add(list.get(start++));
    }

    return new JSONArray(fl);
  }

  public void sort() {
    Collections.sort(myArrayList);
  }

  public void sort(Comparator c) {
    Collections.sort(myArrayList, c);
  }

  public JSONArray splice(int index, int howMany) {
    return splice(index, howMany, (Object[]) null);
  }

  public JSONArray splice(int index, int howMany, Object... e) {
    return spliceList(index, howMany, (e == null) ? null : Arrays.asList(e));
  }

  public JSONArray spliceList(int index, int howMany, List e) {
    List list = myArrayList;
    int len = list.size();

    if (index < 0) {
      index = len + index;
    }

    if (index < 0) {
      throw new IllegalArgumentException("index=" + index);
    }

    int i = index;

    if (len > i + howMany) {
      len = i + howMany;
    }

    List rlist = new ArrayList((i < len) ? len - i : 0);

    while (i < len) {
      rlist.add(list.get(index));
      list.remove(index);
      i++;
    }

    if (e != null) {
      if (index >= list.size()) {
        addAll(e);
      } else {
        addAll(index, e);
      }
    }

    return new JSONArray(list);
  }

  public List subList(int fromIndex, int toIndex) {
    return myArrayList.subList(fromIndex, toIndex);
  }

  public Object[] toArray() {
    return myArrayList.toArray();
  }

  public Object[] toArray(Object[] a) {
    return myArrayList.toArray(a);
  }

  /**
   * Produce a JSONObject by combining a JSONArray of names with the values of
   * this JSONArray.
   * 
   * @param names
   *          A JSONArray containing a list of key strings. These will be paired
   *          with the values.
   * @return A JSONObject, or null if there are no names or if this JSONArray
   *         has no values.
   * @throws JSONException
   *           If any of the names are null.
   */
  public JSONObject toJSONObject(JSONArray names) throws JSONException {
    if ((names == null) || (names.length() == 0) || (length() == 0)) {
      return null;
    }

    JSONObject jo = new JSONObject();

    for (int i = 0; i < names.length(); i += 1) {
      jo.put(names.getString(i), this.opt(i));
    }

    return jo;
  }

  /**
   * Make a JSON text of this JSONArray. For compactness, no unnecessary
   * whitespace is added. If it is not possible to produce a syntactically
   * correct JSON text then null will be returned instead. This could occur if
   * the array contains an invalid number.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   *
   * @return a printable, displayable, transmittable representation of the
   *         array.
   */
  public String toString() {
    try {
      return '[' + join(",") + ']';
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Make a prettyprinted JSON text of this JSONArray. Warning: This method
   * assumes that the data structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @return a printable, displayable, transmittable representation of the
   *         object, beginning with <code>[</code>&nbsp;<small>(left
   *         bracket)</small> and ending with <code>]</code>&nbsp;<small>(right
   *         bracket)</small>.
   * @throws JSONException
   */
  public String toString(int indentFactor) throws JSONException {
    return toString(indentFactor, 0);
  }

  /**
   * Write the contents of the JSONArray as JSON text to a writer. For
   * compactness, no whitespace is added.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   *
   * @return The writer.
   * @throws JSONException
   */
  public Writer write(Writer writer) throws JSONException {
    try {
      boolean b = false;
      int len = length();

      writer.write('[');

      for (int i = 0; i < len; i += 1) {
        if (b) {
          writer.write(',');
        }

        Object v = this.myArrayList.get(i);

        if (v instanceof JSONObject) {
          ((JSONObject) v).write(writer);
        } else if (v instanceof JSONArray) {
          ((JSONArray) v).write(writer);
        } else {
          writer.write(JSONObject.valueToString(v));
        }

        b = true;
      }

      writer.write(']');

      return writer;
    } catch (IOException e) {
      throw new JSONException(e);
    }
  }

  /**
   * Make a prettyprinted JSON text of this JSONArray. Warning: This method
   * assumes that the data structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @param indent
   *          The indention of the top level.
   * @return a printable, displayable, transmittable representation of the
   *         array.
   * @throws JSONException
   */
  String toString(int indentFactor, int indent) throws JSONException {
    int len = length();

    if (len == 0) {
      return "[]";
    }

    int i;
    StringBuffer sb = new StringBuffer("[");

    if (len == 1) {
      sb.append(JSONObject.valueToString(this.myArrayList.get(0), indentFactor, indent));
    } else {
      int newindent = indent + indentFactor;

      sb.append('\n');

      for (i = 0; i < len; i += 1) {
        if (i > 0) {
          sb.append(",\n");
        }

        for (int j = 0; j < newindent; j += 1) {
          sb.append(' ');
        }

        sb.append(JSONObject.valueToString(this.myArrayList.get(i), indentFactor, newindent));
      }

      sb.append('\n');

      for (i = 0; i < indent; i += 1) {
        sb.append(' ');
      }
    }

    sb.append(']');

    return sb.toString();
  }

}
