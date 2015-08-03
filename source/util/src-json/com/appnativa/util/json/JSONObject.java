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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.appnativa.util.SNumber;
import com.appnativa.util.json.JSONTokener.iWatcher;

/**
 * A JSONObject is an unordered collection of name/value pairs. Its external
 * form is a string wrapped in curly braces with colons between the names and
 * values, and commas between the values and names. The internal form is an
 * object having <code>get</code> and <code>opt</code> methods for accessing the
 * values by name, and <code>put</code> methods for adding or replacing values
 * by name. The values can be any of these types: <code>Boolean</code>,
 * <code>JSONArray</code>, <code>JSONObject</code>, <code>Number</code>,
 * <code>String</code>, or the <code>JSONObject.NULL</code> object. A JSONObject
 * constructor can be used to convert an external form JSON text into an
 * internal form whose values can be retrieved with the <code>get</code> and
 * <code>opt</code> methods, or to convert values into a JSON text using the
 * <code>put</code> and <code>toString</code> methods. A <code>get</code> method
 * returns a value if one can be found, and throws an exception if one cannot be
 * found. An <code>opt</code> method returns a default value instead of throwing
 * an exception, and so is useful for obtaining optional values.
 * <p>
 * The generic <code>get()</code> and <code>opt()</code> methods return an
 * object, which you can cast or query for type. There are also typed
 * <code>get</code> and <code>opt</code> methods that do type checking and type
 * coercion for you.
 * <p>
 * The <code>put</code> methods adds values to an object. For example,
 * 
 * <pre>
 * myString = new JSONObject().put(&quot;JSON&quot;, &quot;Hello, World!&quot;).toString();
 * </pre>
 * 
 * produces the string <code>{"JSON": "Hello, World"}</code>.
 * <p>
 * The texts produced by the <code>toString</code> methods strictly conform to
 * the JSON syntax rules. The constructors are more forgiving in the texts they
 * will accept:
 * <ul>
 * <li>An extra <code>,</code>&nbsp;<small>(comma)</small> may appear just
 * before the closing brace.</li>
 * <li>Strings may be quoted with <code>'</code>&nbsp;<small>(single
 * quote)</small>.</li>
 * <li>Strings do not need to be quoted at all if they do not begin with a quote
 * or single quote, and if they do not contain leading or trailing spaces, and
 * if they do not contain any of these characters:
 * <code>{ } [ ] / \ : , = ; #</code> and if they do not look like numbers and
 * if they are not the reserved words <code>true</code>, <code>false</code>, or
 * <code>null</code>.</li>
 * <li>Keys can be followed by <code>=</code> or <code>=></code> as well as by
 * <code>:</code>.</li>
 * <li>Values can be followed by <code>;</code> <small>(semicolon)</small> as
 * well as by <code>,</code> <small>(comma)</small>.</li>
 * <li>Numbers may have the <code>0-</code> <small>(octal)</small> or
 * <code>0x-</code> <small>(hex)</small> prefix.</li>
 * </ul>
 * 
 * <p>
 * <b>Don DeCoteau</b> - Add use of SNumber instead on Long or Double
 * </p>
 * 
 * @author JSON.org
 * @version 2009-03-06
 */
public class JSONObject implements Map, Cloneable {

  public static boolean QUOTE_FOR_7BIT = false;

  /**
   * JSONObject.NULL is equivalent to the value that JavaScript calls null,
   * whilst Java's null is equivalent to the value that JavaScript calls
   * undefined.
   */
  private static final class Null {

    /**
     * There is only intended to be a single instance of the NULL object, so the
     * clone method returns itself.
     * 
     * @return NULL.
     */
    protected final Object clone() {
      return this;
    }

    /**
     * A Null object is equal to the null value and to itself.
     * 
     * @param object
     *          An object to test for nullness.
     * @return true if the object parameter is the JSONObject.NULL object or
     *         null.
     */
    public boolean equals(Object object) {
      return object == null || object == this;
    }

    /**
     * Get the "null" string value.
     * 
     * @return The string "null".
     */
    public String toString() {
      return "null";
    }

  }

  /**
   * The map where the JSONObject's properties are kept.
   */
  protected Map              map;
  protected String           name;

  /**
   * It is sometimes more convenient and less ambiguous to have a
   * <code>NULL</code> object than to use Java's <code>null</code> value.
   * <code>JSONObject.NULL.equals(null)</code> returns <code>true</code>.
   * <code>JSONObject.NULL.toString()</code> returns <code>"null"</code>.
   */
  public static final Object NULL = new Null();

  /**
   * Construct an empty JSONObject.
   */
  public JSONObject() {
    this.map = new HashMap();
  }

  /**
   * Construct a JSONObject from a subset of another JSONObject. An array of
   * strings is used to identify the keys that should be copied. Missing keys
   * are ignored.
   * 
   * @param jo
   *          A JSONObject.
   * @param names
   *          An array of strings.
   * @exception JSONException
   *              If a value is a non-finite number or if a name is duplicated.
   */
  public JSONObject(JSONObject jo, String[] names) throws JSONException {
    this();
    for (int i = 0; i < names.length; i += 1) {
      putOnce(names[i], jo.opt(names[i]));
    }
  }

  /**
   * Construct a JSONObject from a JSONTokener.
   * 
   * @param x
   *          A JSONTokener object containing the source string.
   * @throws JSONException
   *           If there is a syntax error in the source string or a duplicated
   *           key.
   */
  public JSONObject(JSONTokener x) throws JSONException {
    this(null, x);
  }

  /**
   * Construct a JSONObject from a JSONTokener.
   * 
   * @param name
   *          the name of this object
   * @param x
   *          A JSONTokener object containing the source string.
   * @throws JSONException
   *           If there is a syntax error in the source string or a duplicated
   *           key.
   */
  public JSONObject(String name, JSONTokener x) throws JSONException {
    this();
    char c;
    String key;
    this.name = name;
    iWatcher watcher = x.getWatcher();
    String valueName;
    final boolean usePath = x.isUsePathAsName();

    if (x.nextClean() != '{') {
      throw x.syntaxError("A JSONObject text must begin with '{'");
    }
    if (watcher != null) {
      watcher.willParseObject(this);
    }
    try {
      for (;;) {
        c = x.nextClean();
        switch (c) {
          case 0:
            throw x.syntaxError("A JSONObject text must end with '}'");
          case '}':
            return;
          default:
            x.back();
            key = x.nextValue(null).toString();
        }

        /*
         * The key is followed by ':'. We will also tolerate '=' or '=>'.
         */

        c = x.nextClean();
        if (c == '=') {
          if (x.next() != '>') {
            x.back();
          }
        } else if (c != ':') {
          throw x.syntaxError("Expected a ':' after a key");
        }
        valueName = usePath ? x.makePathName(name, key) : key;
        if (watcher != null && !watcher.keyEncountered(this, valueName)) {
          x.setTerminateParsing(true);
          return;
        }

        Object value = x.nextValue(valueName);
        if (watcher != null) {
          value = watcher.valueEncountered(this, valueName, value);
          if (x.isTerminateParsing()) {
            return;
          }
          if (value != null) {
            putOnce(key, value);
          }
        } else {
          putOnce(key, value);
        }

        /*
         * Pairs are separated by ','. We will also tolerate ';'.
         */

        switch (x.nextClean()) {
          case ';':
          case ',':
            if (x.nextClean() == '}') {
              return;
            }
            x.back();
            break;
          case '}':
            return;
          default:
            throw x.syntaxError("Expected a ',' or '}'");
        }
      }
    } finally {
      if (watcher != null) {
        watcher.didParseObject(this);
      }

    }
  }

  /**
   * Construct a JSONObject from a Map.
   *
   * @param map
   *          A map object that can be used to initialize the contents of the
   *          JSONObject.
   */
  public JSONObject(Map map) {
    this.map = (map == null) ? new HashMap() : map;
  }

  public Object clone() {
    return new JSONObject(new HashMap(this.map));
  }

  /**
   * Construct a JSONObject from an Object, using reflection to find the public
   * members. The resulting JSONObject's keys will be the strings from the names
   * array, and the values will be the field values associated with those keys
   * in the object. If a key is not found or not visible, then it will not be
   * copied into the new JSONObject.
   * 
   * @param object
   *          An object that has fields that should be used to make a
   *          JSONObject.
   * @param names
   *          An array of strings, the names of the fields to be obtained from
   *          the object.
   */
  public JSONObject(Object object, String names[]) {
    this();
    Class c = object.getClass();
    for (int i = 0; i < names.length; i += 1) {
      String name = names[i];
      try {
        putOpt(name, c.getField(name).get(object));
      } catch (Exception e) {
        /* forget about it */
      }
    }
  }

  /**
   * Construct a JSONObject from a source JSON text string. This is the most
   * commonly used JSONObject constructor.
   * 
   * @param source
   *          A string beginning with <code>{</code>&nbsp;<small>(left
   *          brace)</small> and ending with <code>}</code>&nbsp;<small>(right
   *          brace)</small>.
   * @exception JSONException
   *              If there is a syntax error in the source string or a
   *              duplicated key.
   */
  public JSONObject(String source) throws JSONException {
    this(new JSONTokener(source));
  }

  /**
   * Accumulate values under a key. It is similar to the put method except that
   * if there is already an object stored under the key then a JSONArray is
   * stored under the key to hold all of the accumulated values. If there is
   * already a JSONArray, then the new value is appended to it. In contrast, the
   * put method replaces the previous value.
   * 
   * @param key
   *          A key string.
   * @param value
   *          An object to be accumulated under the key.
   * @return this.
   * @throws JSONException
   *           If the value is an invalid number or if the key is null.
   */
  public JSONObject accumulate(String key, Object value) throws JSONException {
    testValidity(value);
    Object o = opt(key);
    if (o == null) {
      put(key, value instanceof JSONArray ? new JSONArray().put(value) : value);
    } else if (o instanceof JSONArray) {
      ((JSONArray) o).put(value);
    } else {
      put(key, new JSONArray().put(o).put(value));
    }
    return this;
  }

  /**
   * Append values to the array under a key. If the key does not exist in the
   * JSONObject, then the key is put in the JSONObject with its value being a
   * JSONArray containing the value parameter. If the key was already associated
   * with a JSONArray, then the value parameter is appended to it.
   * 
   * @param key
   *          A key string.
   * @param value
   *          An object to be accumulated under the key.
   * @return this.
   * @throws JSONException
   *           If the key is null or if the current value associated with the
   *           key is not a JSONArray.
   */
  public JSONObject append(String key, Object value) throws JSONException {
    testValidity(value);
    Object o = opt(key);
    if (o == null) {
      put(key, new JSONArray().put(value));
    } else if (o instanceof JSONArray) {
      put(key, ((JSONArray) o).put(value));
    } else {
      throw new JSONException("JSONObject[" + key + "] is not a JSONArray.");
    }
    return this;
  }

  /**
   * Produce a string from a double. The string "null" will be returned if the
   * number is not finite.
   * 
   * @param d
   *          A double.
   * @return A String.
   */
  static public String doubleToString(double d) {
    if (Double.isInfinite(d) || Double.isNaN(d)) {
      return "null";
    }

    // Shave off trailing zeros and decimal point, if possible.

    String s = Double.toString(d);
    if (s.indexOf('.') > 0 && s.indexOf('e') < 0 && s.indexOf('E') < 0) {
      while (s.endsWith("0")) {
        s = s.substring(0, s.length() - 1);
      }
      if (s.endsWith(".")) {
        s = s.substring(0, s.length() - 1);
      }
    }
    return s;
  }

  /**
   * Get the boolean value associated with a key.
   *
   * @param key
   *          A key string.
   * @return The truth.
   * @throws JSONException
   *           if the value is not a Boolean or the String "true" or "false".
   */
  public boolean getBoolean(String key) throws JSONException {
    Object o = get(key);
    if (o != null) {
      if (o.equals(Boolean.FALSE) || (o instanceof String && ((String) o).equalsIgnoreCase("false"))) {
        return false;
      } else if (o.equals(Boolean.TRUE) || (o instanceof String && ((String) o).equalsIgnoreCase("true"))) {
        return true;
      }
    }
    throw new JSONException("JSONObject[" + quote(key) + "] is not a Boolean.");
  }

  /**
   * Get the double value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return The numeric value.
   * @throws JSONException
   *           if the key is not found or if the value is not a Number object
   *           and cannot be converted to a number.
   */
  public double getDouble(String key) throws JSONException {
    Object o = get(key);
    try {
      if (o instanceof String) {
        return SNumber.doubleValue((String) o);
      }
      //      return o instanceof Number ? ((Number) o).doubleValue() : Double.valueOf((String) o).doubleValue();
      return ((Number) o).doubleValue();
    } catch (Exception e) {
      throw new JSONException("JSONObject[" + quote(key) + "] is not a number.");
    }
  }

  /**
   * Get the int value associated with a key. If the number value is too large
   * for an int, it will be clipped.
   *
   * @param key
   *          A key string.
   * @return The integer value.
   * @throws JSONException
   *           if the key is not found or if the value cannot be converted to an
   *           integer.
   */
  public int getInt(String key) throws JSONException {
    Object o = get(key);
    return o instanceof Number ? ((Number) o).intValue() : (int) getDouble(key);
  }

  /**
   * Get the JSONArray value associated with a key.
   *
   * @param key
   *          A key string.
   * @return A JSONArray which is the value.
   * @throws JSONException
   *           if the key is not found or if the value is not a JSONArray.
   */
  public JSONArray getJSONArray(String key) throws JSONException {
    Object o = get(key);
    if (o instanceof JSONArray) {
      return (JSONArray) o;
    }
    if (o == null) {
      return null;
    }
    throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONArray.");
  }

  /**
   * Get the JSONObject value associated with a key.
   *
   * @param key
   *          A key string.
   * @return A JSONObject which is the value.
   * @throws JSONException
   *           if the key is not found or if the value is not a JSONObject.
   */
  public JSONObject getJSONObject(String key) throws JSONException {
    Object o = get(key);
    if (o instanceof JSONObject) {
      return (JSONObject) o;
    }
    if (o == null) {
      return null;
    }
    throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONObject.");
  }

  /**
   * Get the long value associated with a key. If the number value is too long
   * for a long, it will be clipped.
   *
   * @param key
   *          A key string.
   * @return The long value.
   * @throws JSONException
   *           if the key is not found or if the value cannot be converted to a
   *           long.
   */
  public long getLong(String key) throws JSONException {
    Object o = get(key);
    return o instanceof Number ? ((Number) o).longValue() : (long) getDouble(key);
  }

  /**
   * Get an array of field names from a JSONObject.
   *
   * @return An array of field names, or null if there are no names.
   */
  public static String[] getNames(JSONObject jo) {
    int length = jo.length();
    if (length == 0) {
      return null;
    }
    Iterator i = jo.keys();
    String[] names = new String[length];
    int j = 0;
    while (i.hasNext()) {
      names[j] = (String) i.next();
      j += 1;
    }
    return names;
  }

  /**
   * Gets the name of this object
   * 
   * @return the name of this object or null of no name was specified
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of this object
   * 
   * @param name
   *          the name of this object
   */

  /**
   * Get the string associated with a key.
   *
   * @param key
   *          A key string.
   * @return A string which is the value.
   * @throws JSONException
   *           if the key is not found.
   */
  public String getString(String key) throws JSONException {
    return get(key).toString();
  }

  /**
   * Determine if the JSONObject contains a specific key.
   * 
   * @param key
   *          A key string.
   * @return true if the key exists in the JSONObject.
   */
  public boolean has(String key) {
    return this.map.containsKey(key);
  }

  /**
   * Determine if the value associated with the key is null or if there is no
   * value.
   * 
   * @param key
   *          A key string.
   * @return true if there is no value associated with the key or if the value
   *         is the JSONObject.NULL object.
   */
  public boolean isNull(String key) {
    return JSONObject.NULL.equals(opt(key));
  }

  /**
   * Get an enumeration of the keys of the JSONObject.
   *
   * @return An iterator of the keys.
   */
  public Iterator keys() {
    return this.map.keySet().iterator();
  }

  /**
   * Get the number of keys stored in the JSONObject.
   *
   * @return The number of keys in the JSONObject.
   */
  public int length() {
    return this.map.size();
  }

  /**
   * Produce a JSONArray containing the names of the elements of this
   * JSONObject.
   * 
   * @return A JSONArray containing the key strings, or null if the JSONObject
   *         is empty.
   */
  public JSONArray names() {
    JSONArray ja = new JSONArray();
    Iterator keys = keys();
    while (keys.hasNext()) {
      ja.put(keys.next());
    }
    return ja.length() == 0 ? null : ja;
  }

  /**
   * Produce a string from a Number.
   * 
   * @param n
   *          A Number
   * @return A String.
   * @throws JSONException
   *           If n is a non-finite number.
   */
  static public String numberToString(Number n) throws JSONException {
    if (n == null) {
      throw new JSONException("Null pointer");
    }
    testValidity(n);

    // Shave off trailing zeros and decimal point, if possible.

    String s = n.toString();
    if (s.indexOf('.') > 0 && s.indexOf('e') < 0 && s.indexOf('E') < 0) {
      while (s.endsWith("0")) {
        s = s.substring(0, s.length() - 1);
      }
      if (s.endsWith(".")) {
        s = s.substring(0, s.length() - 1);
      }
    }
    return s;
  }

  /**
   * Get an optional value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return An object which is the value, or null if there is no value.
   */
  public Object opt(String key) {
    return key == null ? null : this.map.get(key);
  }

  /**
   * Get an optional boolean associated with a key. It returns false if there is
   * no such key, or if the value is not Boolean.TRUE or the String "true".
   *
   * @param key
   *          A key string.
   * @return The truth.
   */
  public boolean optBoolean(String key) {
    return optBoolean(key, false);
  }

  /**
   * Get an optional boolean associated with a key. It returns the defaultValue
   * if there is no such key, or if it is not a Boolean or the String "true" or
   * "false" (case insensitive).
   *
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return The truth.
   */
  public boolean optBoolean(String key, boolean defaultValue) {
    Object o = map.get(key);
    if (o != null) {
      if (o.equals(Boolean.FALSE) || (o instanceof String && ((String) o).equalsIgnoreCase("false"))) {
        return false;
      } else if (o.equals(Boolean.TRUE) || (o instanceof String && ((String) o).equalsIgnoreCase("true"))) {
        return true;
      }
    }
    return defaultValue;
  }

  /**
   * Put a key/value pair in the JSONObject, where the value will be a JSONArray
   * which is produced from a Collection.
   * 
   * @param key
   *          A key string.
   * @param value
   *          A Collection value.
   * @return this.
   * @throws JSONException
   */
  public JSONObject put(String key, Collection value) throws JSONException {
    if (value instanceof JSONArray) {
      map.put(key, value);
    } else {
      map.put(key, new JSONArray(value));
    }
    return this;
  }

  /**
   * Get an optional double associated with a key, or NaN if there is no such
   * key or if its value is not a number. If the value is a string, an attempt
   * will be made to evaluate it as a number.
   *
   * @param key
   *          A string which is the key.
   * @return An object which is the value.
   */
  public double optDouble(String key) {
    return optDouble(key, Double.NaN);
  }

  /**
   * Get an optional double associated with a key, or the defaultValue if there
   * is no such key or if its value is not a number. If the value is a string,
   * an attempt will be made to evaluate it as a number.
   *
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return An object which is the value.
   */
  public double optDouble(String key, double defaultValue) {
    try {
      Object o = opt(key);
      if (o != null) {
        return o instanceof Number ? ((Number) o).doubleValue() : new Double((String) o).doubleValue();
      }
    } catch (Exception e) {
    }
    return defaultValue;
  }

  /**
   * Get an optional int value associated with a key, or zero if there is no
   * such key or if the value is not a number. If the value is a string, an
   * attempt will be made to evaluate it as a number.
   *
   * @param key
   *          A key string.
   * @return An object which is the value.
   */
  public int optInt(String key) {
    return optInt(key, 0);
  }

  /**
   * Get an optional int value associated with a key, or the default if there is
   * no such key or if the value is not a number. If the value is a string, an
   * attempt will be made to evaluate it as a number.
   *
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return An object which is the value.
   */
  public int optInt(String key, int defaultValue) {
    try {
      if (map.containsKey(key)) {
        return getInt(key);
      }
    } catch (Exception e) {
    }
    return defaultValue;
  }

  /**
   * Get an optional JSONArray associated with a key. It returns null if there
   * is no such key, or if its value is not a JSONArray.
   *
   * @param key
   *          A key string.
   * @return A JSONArray which is the value.
   */
  public JSONArray optJSONArray(String key) {
    Object o = opt(key);
    return o instanceof JSONArray ? (JSONArray) o : null;
  }

  /**
   * Get an optional JSONObject associated with a key. It returns null if there
   * is no such key, or if its value is not a JSONObject.
   *
   * @param key
   *          A key string.
   * @return A JSONObject which is the value.
   */
  public JSONObject optJSONObject(String key) {
    Object o = opt(key);
    return o instanceof JSONObject ? (JSONObject) o : null;
  }

  /**
   * Get an optional long value associated with a key, or zero if there is no
   * such key or if the value is not a number. If the value is a string, an
   * attempt will be made to evaluate it as a number.
   *
   * @param key
   *          A key string.
   * @return An object which is the value.
   */
  public long optLong(String key) {
    return optLong(key, 0);
  }

  /**
   * Get an optional long value associated with a key, or the default if there
   * is no such key or if the value is not a number. If the value is a string,
   * an attempt will be made to evaluate it as a number.
   *
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return An object which is the value.
   */
  public long optLong(String key, long defaultValue) {
    try {
      if (map.containsKey(key)) {
        return getLong(key);
      }
    } catch (Exception e) {
    }
    return defaultValue;
  }

  /**
   * Get an optional string associated with a key. It returns an empty string if
   * there is no such key. If the value is not a string and is not null, then it
   * is coverted to a string.
   *
   * @param key
   *          A key string.
   * @return A string which is the value.
   */
  public String optString(String key) {
    return optString(key, "");
  }

  /**
   * Get an optional string associated with a key. It returns the defaultValue
   * if there is no such key.
   *
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return A string which is the value.
   */
  public String optString(String key, String defaultValue) {
    Object o = opt(key);
    return o != null ? o.toString() : defaultValue;
  }

  /**
   * Put a key/boolean pair in the JSONObject.
   *
   * @param key
   *          A key string.
   * @param value
   *          A boolean which is the value.
   * @return this.
   * @throws JSONException
   *           If the key is null.
   */
  public JSONObject put(String key, boolean value) throws JSONException {
    put(key, value ? Boolean.TRUE : Boolean.FALSE);
    return this;
  }

  /**
   * Put a key/double pair in the JSONObject.
   *
   * @param key
   *          A key string.
   * @param value
   *          A double which is the value.
   * @return this.
   * @throws JSONException
   *           If the key is null or if the number is invalid.
   */
  public JSONObject put(String key, double value) throws JSONException {
    put(key, new Double(value));
    return this;
  }

  /**
   * Put a key/int pair in the JSONObject.
   *
   * @param key
   *          A key string.
   * @param value
   *          An int which is the value.
   * @return this.
   * @throws JSONException
   *           If the key is null.
   */
  public JSONObject put(String key, int value) throws JSONException {
    put(key, Long.valueOf(value));
    return this;
  }

  /**
   * Put a key/long pair in the JSONObject.
   *
   * @param key
   *          A key string.
   * @param value
   *          A long which is the value.
   * @return this.
   * @throws JSONException
   *           If the key is null.
   */
  public JSONObject put(String key, long value) throws JSONException {
    put(key, Long.valueOf(value));
    return this;
  }

  /**
   * Put a key/value pair in the JSONObject, where the value will be a
   * JSONObject which is produced from a Map.
   * 
   * @param key
   *          A key string.
   * @param value
   *          A Map value.
   * @return this.
   * @throws JSONException
   */
  public JSONObject put(String key, Map value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (value != null) {
      if (!(value instanceof JSONObject)) {
        value = new JSONObject(value);
      }
      this.map.put(key, value);
    } else {
      remove(key);
    }
    return this;
  }

  /**
   * Put a key/value pair in the JSONObject. If the value is null, then the key
   * will be removed from the JSONObject if it is present.
   * 
   * @param key
   *          A key string.
   * @param value
   *          An object which is the value. It should be of one of these types:
   *          Boolean, Double, Integer, JSONArray, JSONObject, Long, String, or
   *          the JSONObject.NULL object.
   * @return this.
   * @throws JSONException
   *           If the value is non-finite number or if the key is null.
   */
  public JSONObject put(String key, Object value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (value != null) {
      testValidity(value);
      this.map.put(key, value);
    } else {
      remove(key);
    }
    return this;
  }

  /**
   * Put a key/value pair in the JSONObject, but only if the key and the value
   * are both non-null, and only if there is not already a member with that
   * name.
   * 
   * @param key
   * @param value
   * @return his.
   * @throws JSONException
   *           if the key is a duplicate
   */
  public JSONObject putOnce(String key, Object value) throws JSONException {
    if (key != null && value != null) {
      if (opt(key) != null) {
        throw new JSONException("Duplicate key \"" + key + "\"");
      }
      put(key, value);
    }
    return this;
  }

  /**
   * Put a key/value pair in the JSONObject, but only if the key and the value
   * are both non-null.
   * 
   * @param key
   *          A key string.
   * @param value
   *          An object which is the value. It should be of one of these types:
   *          Boolean, Double, Integer, JSONArray, JSONObject, Long, String, or
   *          the JSONObject.NULL object.
   * @return this.
   * @throws JSONException
   *           If the value is a non-finite number.
   */
  public JSONObject putOpt(String key, Object value) throws JSONException {
    if (key != null && value != null) {
      put(key, value);
    }
    return this;
  }

  /**
   * Produce a string in double quotes with backslash sequences in all the right
   * places. A backslash will be inserted within </, allowing JSON text to be
   * delivered in HTML. In JSON text, a string cannot contain a control
   * character or an unescaped quote or backslash.
   * 
   * @param string
   *          A String
   * @return A String correctly formatted for insertion in a JSON text.
   */
  public static String quote(String string) {
    if (string == null || string.length() == 0) {
      return "\"\"";
    }

    char b;
    char c = 0;
    int i;
    int len = string.length();
    StringBuilder sb = new StringBuilder(len + 4);
    String t;

    sb.append('"');
    for (i = 0; i < len; i += 1) {
      b = c;
      c = string.charAt(i);
      switch (c) {
        case '\\':
        case '"':
          sb.append('\\');
          sb.append(c);
          break;
        case '/':
          if (b == '<') {
            sb.append('\\');
          }
          sb.append(c);
          break;
        case '\b':
          sb.append("\\b");
          break;
        case '\t':
          sb.append("\\t");
          break;
        case '\n':
          sb.append("\\n");
          break;
        case '\f':
          sb.append("\\f");
          break;
        case '\r':
          sb.append("\\r");
          break;
        default:
          if (c < ' ' || (c > 126 && QUOTE_FOR_7BIT) || (c >= '\u0080' && c < '\u00a0') || (c >= '\u2000' && c < '\u2100')) {
            t = "000" + Integer.toHexString(c);
            sb.append("\\u" + t.substring(t.length() - 4));
          } else {
            sb.append(c);
          }
      }
    }
    sb.append('"');
    return sb.toString();
  }

  /**
   * Remove a name and its value, if present.
   * 
   * @param key
   *          The name to be removed.
   * @return The value that was associated with the name, or null if there was
   *         no value.
   */
  public Object remove(String key) {
    return this.map.remove(key);
  }

  /**
   * Get an enumeration of the keys of the JSONObject. The keys will be sorted
   * alphabetically.
   *
   * @return An iterator of the keys.
   */
  public Iterator sortedKeys() {
    return new TreeSet(this.map.keySet()).iterator();
  }

  /**
   * Try to convert a string into a number, boolean, or null. If the string
   * can't be converted, return the string.
   * 
   * @param s
   *          A String.
   * @return A simple JSON value.
   */
  static public Object stringToValue(String s) {
    if (s.equals("")) {
      return s;
    }
    if (s.equalsIgnoreCase("true")) {
      return Boolean.TRUE;
    }
    if (s.equalsIgnoreCase("false")) {
      return Boolean.FALSE;
    }
    if (s.equalsIgnoreCase("null")) {
      return JSONObject.NULL;
    }

    /*
     * If it might be a number, try converting it. We support the 0- and 0x-
     * conventions. If a number cannot be produced, then the value will just be
     * a string. Note that the 0-, 0x-, plus, and implied string conventions are
     * non-standard. A JSON parser is free to accept non-JSON forms as long as
     * it accepts all correct JSON forms.
     */

    char b = s.charAt(0);
    if ((b >= '0' && b <= '9') || b == '.' || b == '-' || b == '+') {
      if (b == '0') {
        if (s.length() > 2 && (s.charAt(1) == 'x' || s.charAt(1) == 'X')) {
          try {
            return Long.valueOf(Long.parseLong(s.substring(2), 16));
          } catch (Exception e) {
            /* Ignore the error */
          }
        } else {
          try {
            return Long.valueOf(Long.parseLong(s, 8));
          } catch (Exception e) {
            /* Ignore the error */
          }
        }
      }
      try {
        return new SNumber(s);
        //        if (s.indexOf('.') > -1 || s.indexOf('e') > -1 || s.indexOf('E') > -1) {
        //          return Double.valueOf(s);
        //        } else {
        //          return new Long(s);
        //        }
      } catch (Exception f) {
        /* Ignore the error */
      }
    }
    return s;
  }

  /**
   * Throw an exception if the object is an NaN or infinite number.
   * 
   * @param o
   *          The object to test.
   * @throws JSONException
   *           If o is a non-finite number.
   */
  static void testValidity(Object o) throws JSONException {
    if (o != null) {
      if (o instanceof Double) {
        if (((Double) o).isInfinite() || ((Double) o).isNaN()) {
          throw new JSONException("JSON does not allow non-finite numbers.");
        }
      } else if (o instanceof Float) {
        if (((Float) o).isInfinite() || ((Float) o).isNaN()) {
          throw new JSONException("JSON does not allow non-finite numbers.");
        }
      }
    }
  }

  /**
   * Produce a JSONArray containing the values of the members of this
   * JSONObject.
   * 
   * @param names
   *          A JSONArray containing a list of key strings. This determines the
   *          sequence of the values in the result.
   * @return A JSONArray of values.
   * @throws JSONException
   *           If any of the values are non-finite numbers.
   */
  public JSONArray toJSONArray(JSONArray names) throws JSONException {
    if (names == null || names.length() == 0) {
      return null;
    }
    JSONArray ja = new JSONArray();
    for (int i = 0; i < names.length(); i += 1) {
      ja.put(this.opt(names.getString(i)));
    }
    return ja;
  }

  /**
   * Make a JSON text of this JSONObject. For compactness, no whitespace is
   * added. If this would not result in a syntactically correct JSON text, then
   * null will be returned instead.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   *
   * @return a printable, displayable, portable, transmittable representation of
   *         the object, beginning with <code>{</code>&nbsp;<small>(left
   *         brace)</small> and ending with <code>}</code>&nbsp;<small>(right
   *         brace)</small>.
   */
  public String toString() {
    try {
      Iterator keys = keys();
      StringBuilder sb = new StringBuilder("{");

      while (keys.hasNext()) {
        if (sb.length() > 1) {
          sb.append(',');
        }
        Object o = keys.next();
        sb.append(quote(o.toString()));
        sb.append(':');
        sb.append(valueToString(this.map.get(o)));
      }
      sb.append('}');
      return sb.toString();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Make a prettyprinted JSON text of this JSONObject.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @return a printable, displayable, portable, transmittable representation of
   *         the object, beginning with <code>{</code>&nbsp;<small>(left
   *         brace)</small> and ending with <code>}</code>&nbsp;<small>(right
   *         brace)</small>.
   * @throws JSONException
   *           If the object contains an invalid number.
   */
  public String toString(int indentFactor) throws JSONException {
    return toString(indentFactor, 0);
  }

  /**
   * Make a prettyprinted JSON text of this JSONObject.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @param indent
   *          The indentation of the top level.
   * @return a printable, displayable, transmittable representation of the
   *         object, beginning with <code>{</code>&nbsp;<small>(left
   *         brace)</small> and ending with <code>}</code>&nbsp;<small>(right
   *         brace)</small>.
   * @throws JSONException
   *           If the object contains an invalid number.
   */
  String toString(int indentFactor, int indent) throws JSONException {
    int j;
    int n = length();
    if (n == 0) {
      return "{}";
    }
    Iterator keys = sortedKeys();
    StringBuilder sb = new StringBuilder("{");
    int newindent = indent + indentFactor;
    Object o;
    if (n == 1) {
      o = keys.next();
      sb.append(quote(o.toString()));
      sb.append(": ");
      sb.append(valueToString(this.map.get(o), indentFactor, indent));
    } else {
      while (keys.hasNext()) {
        o = keys.next();
        if (sb.length() > 1) {
          sb.append(",\n");
        } else {
          sb.append('\n');
        }
        for (j = 0; j < newindent; j += 1) {
          sb.append(' ');
        }
        sb.append(quote(o.toString()));
        sb.append(": ");
        sb.append(valueToString(this.map.get(o), indentFactor, newindent));
      }
      if (sb.length() > 1) {
        sb.append('\n');
        for (j = 0; j < indent; j += 1) {
          sb.append(' ');
        }
      }
    }
    sb.append('}');
    return sb.toString();
  }

  /**
   * Make a JSON text of an Object value. If the object has an
   * value.toJSONString() method, then that method will be used to produce the
   * JSON text. The method is required to produce a strictly conforming text. If
   * the object does not contain a toJSONString method (which is the most common
   * case), then a text will be produced by other means. If the value is an
   * array or Collection, then a JSONArray will be made from it and its
   * toJSONString method will be called. If the value is a MAP, then a
   * JSONObject will be made from it and its toJSONString method will be called.
   * Otherwise, the value's toString method will be called, and the result will
   * be quoted.
   *
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @param value
   *          The value to be serialized.
   * @return a printable, displayable, transmittable representation of the
   *         object, beginning with <code>{</code>&nbsp;<small>(left
   *         brace)</small> and ending with <code>}</code>&nbsp;<small>(right
   *         brace)</small>.
   * @throws JSONException
   *           If the value is or contains an invalid number.
   */
  static String valueToString(Object value) throws JSONException {
    if (value == null || value.equals(null)) {
      return "null";
    }
    if (value instanceof JSONString) {
      Object o;
      try {
        o = ((JSONString) value).toJSONString();
      } catch (Exception e) {
        throw new JSONException(e);
      }
      if (o instanceof String) {
        return (String) o;
      }
      throw new JSONException("Bad value from toJSONString: " + o);
    }
    if (value instanceof Number) {
      return numberToString((Number) value);
    }
    if (value instanceof Boolean || value instanceof JSONObject || value instanceof JSONArray) {
      return value.toString();
    }
    if (value instanceof Map) {
      return new JSONObject((Map) value).toString();
    }
    if (value instanceof Collection) {
      return new JSONArray((Collection) value).toString();
    }
    if (value instanceof Object[]) {
      return new JSONArray(Arrays.asList((Object[]) value)).toString();
    }
    return quote(value.toString());
  }

  /**
   * Make a prettyprinted JSON text of an object value.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @param value
   *          The value to be serialized.
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @param indent
   *          The indentation of the top level.
   * @return a printable, displayable, transmittable representation of the
   *         object, beginning with <code>{</code>&nbsp;<small>(left
   *         brace)</small> and ending with <code>}</code>&nbsp;<small>(right
   *         brace)</small>.
   * @throws JSONException
   *           If the object contains an invalid number.
   */
  static String valueToString(Object value, int indentFactor, int indent) throws JSONException {
    if (value == null || value.equals(null)) {
      return "null";
    }
    try {
      if (value instanceof JSONString) {
        Object o = ((JSONString) value).toJSONString();
        if (o instanceof String) {
          return (String) o;
        }
      }
    } catch (Exception e) {
      /* forget about it */
    }
    if (value instanceof Number) {
      return numberToString((Number) value);
    }
    if (value instanceof Boolean) {
      return value.toString();
    }
    if (value instanceof JSONObject) {
      return ((JSONObject) value).toString(indentFactor, indent);
    }
    if (value instanceof JSONArray) {
      return ((JSONArray) value).toString(indentFactor, indent);
    }
    if (value instanceof Map) {
      return new JSONObject((Map) value).toString(indentFactor, indent);
    }
    if (value instanceof Collection) {
      return new JSONArray((Collection) value).toString(indentFactor, indent);
    }
    if (value instanceof Object[]) {
      return new JSONArray(Arrays.asList((Object[]) value)).toString();
    }
    return quote(value.toString());
  }

  /**
   * Write the contents of the JSONObject as JSON text to a writer. For
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
      Iterator keys = keys();
      writer.write('{');

      while (keys.hasNext()) {
        if (b) {
          writer.write(',');
        }
        Object k = keys.next();
        writer.write(quote(k.toString()));
        writer.write(':');
        Object v = this.map.get(k);
        if (v instanceof JSONObject) {
          ((JSONObject) v).write(writer);
        } else if (v instanceof JSONArray) {
          ((JSONArray) v).write(writer);
        } else {
          writer.write(valueToString(v));
        }
        b = true;
      }
      writer.write('}');
      return writer;
    } catch (IOException e) {
      throw new JSONException(e);
    }
  }

  public Collection values() {
    return map.values();
  }

  public int size() {
    return map.size();
  }

  public Object remove(Object key) {
    return map.remove(key);
  }

  public void putAll(Map m) {
    map.putAll(m);
  }

  public Object put(Object key, Object value) {
    return map.put(key, value);
  }

  public Set keySet() {
    return map.keySet();
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  public int hashCode() {
    return map.hashCode();
  }

  public Object opt(String... key) {

    int len = key.length;
    if (len == 1) {
      return map.get(key);
    }
    JSONObject o = (JSONObject) map.get(key[0]);
    if(o==null) {
      return null;
    }
    for (int i = 1; o != null && i < len - 1; i++) {
      o = o.optJSONObject(key[i]);
      if(o==null) {
        return null;
      }
    }
    return o.opt(key[len - 1]);
  }

  public Object get(Object key) {
    return map.get(key);
  }

  public boolean equals(Object o) {
    return map.equals(o);
  }

  public Set entrySet() {
    return map.entrySet();
  }

  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }

  public boolean containsKey(Object key) {
    return map.containsKey(key);
  }

  public void clear() {
    map.clear();
  }

  /**
   * Gets the backing map for the JSON object
   * 
   * @return the backing map for the JSON object
   */
  public Map getObjectMap() {
    return map;
  }

}
