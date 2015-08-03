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

package com.appnativa.spot;

import com.appnativa.util.Helper;
import com.appnativa.util.iPackageHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

public class SPOTHelper {
  private static Class[]        _setRefType   = new Class[] { iSPOTElement.class };
  private final static HashMap  _toSpotName   = new HashMap();
  private final static HashMap  _fromSpotName = new HashMap();
  private static iPackageHelper packageHelper;

  static {
    String pkg = iSPOTConstants.SPOT_PACKAGE_NAME + ".";

    _toSpotName.put("String", pkg + "SPOTPrintableString");
    _toSpotName.put("PrintableString", pkg + "SPOTPrintableString");
    _toSpotName.put("OctetString", pkg + "SPOTOctetString");
    _toSpotName.put("Integer", pkg + "SPOTInteger");
    _toSpotName.put("Real", pkg + "SPOTReal");
    _toSpotName.put("Set", pkg + "SPOTSet");
    _toSpotName.put("Sequence", pkg + "SPOTSequence");
    _toSpotName.put("Any", pkg + "SPOTAny");
    _toSpotName.put("DateTime", pkg + "SPOTDateTime");
    _toSpotName.put("Date", pkg + "SPOTDate");
    _toSpotName.put("Time", pkg + "SPOTTime");
    _toSpotName.put("Boolean", pkg + "SPOTBoolean");
    _toSpotName.put("ByteString", pkg + "SPOTByteString");
    _toSpotName.put("Enumerated", pkg + "SPOTEnumerated");
    _fromSpotName.put(pkg + "SPOTPrintableString", "String");
    _fromSpotName.put(pkg + "SPOTOctetString", "OctetString");
    _fromSpotName.put(pkg + "SPOTInteger", "Integer");
    _fromSpotName.put(pkg + "SPOTReal", "Real");
    _fromSpotName.put(pkg + "SPOTSet", "Set");
    _fromSpotName.put(pkg + "SPOTSequence", "Sequence");
    _fromSpotName.put(pkg + "SPOTAny", "Any");
    _fromSpotName.put(pkg + "SPOTDateTime", "DateTime");
    _fromSpotName.put(pkg + "SPOTDate", "Date");
    _fromSpotName.put(pkg + "SPOTTime", "Time");
    _fromSpotName.put(pkg + "SPOTBoolean", "Boolean");
    _fromSpotName.put(pkg + "SPOTByteString", "ByteString");
    _fromSpotName.put(pkg + "SPOTEnumerated", "Enumerated");
  }

  private SPOTHelper() {}

  /**
   * Creates a defined by class
   *
   * @return the class
   */
  public static Class createDefinedByClass(iSPOTElement parent, String definedBy) {
    if (definedBy == null) {
      return null;
    }

    try {
      String s = definedBy;

      if (definedBy.indexOf('.') == -1) {
        StringBuilder sb = new StringBuilder();

        sb.append(getPackageName(parent.getClass()));
        sb.append('.');

        if (!definedBy.startsWith("SPOT")) {
          sb.append("SPOT");
        }

        sb.append(definedBy);
        s = sb.toString();
      } else {
        s = s.replace(':', '.');
      }

      return loadClass(s);
    } catch(ClassNotFoundException e) {
      throw new SPOTException(e);
    }
  }

  public static Class loadClass(String name) throws ClassNotFoundException {
    if (packageHelper != null) {
      return packageHelper.loadClass(name);
    }

    return Class.forName(name);
  }

  public static Class getFieldClass(Field field) {
    if (packageHelper == null) {
      try {
        packageHelper = (iPackageHelper) Class.forName("com.appnativa.util.JavaPackageHelper").newInstance();
      } catch(Exception e) {
        e.printStackTrace();
      }
    }

    if (packageHelper != null) {
      return packageHelper.getFieldClass(field);
    }

    return null;
  }

  public static String getPackageName(Class cls) {
    if (packageHelper == null) {
      try {
        packageHelper = (iPackageHelper) Class.forName("com.appnativa.util.JavaPackageHelper").newInstance();
      } catch(Exception e) {
        e.printStackTrace();
      }
    }

    if (packageHelper != null) {
      return packageHelper.getPackageName(cls);
    }

    return null;
  }

  public static String createDefinedByString(iSPOTElement parent, String definedby) {
    String s = getPackageName(parent.getClass());

    if (definedby.startsWith(s)) {
      s = definedby.substring(s.length() + 1);

      if (s.startsWith("SPOT")) {
        definedby = s.substring("SPOT".length());
      }
    }

    return definedby;
  }

  public static iSPOTElement elementFromName(Map refClassMap, iSPOTElement parent, String name) {
    try {
      StringBuilder sb = new StringBuilder("get");

      sb.append(name).append("Reference");

      char c = Character.toUpperCase(name.charAt(0));

      sb.setCharAt(3, c);

      String       s = sb.toString();
      Method       m = parent.getClass().getMethod(s, (Class[]) null);
      iSPOTElement e = (iSPOTElement) m.invoke(parent, (Object[]) null);

      if (e != null) {
        refClassMap.put(name, e.getClass());

        return e;
      }
    } catch(NoSuchMethodException ex) {}
    catch(Exception ex) {
      throw new SPOTException(Helper.pealException(ex));
    }

    Class cls = (Class) refClassMap.get(name);

    if (cls == null) {
      try {
        Field f = parent.getClass().getDeclaredField(name);

        cls = getFieldClass(f);
        refClassMap.put(name, cls);
      } catch(Exception ex) {
        return null;
      }
    }

    try {
      return (iSPOTElement) cls.newInstance();
    } catch(Exception ex1) {
      return null;
    }
  }

  /**
   * Sets the value of a referenced variable
   *
   * @param parent the parent object
   * @param name the name of the variable
   * @param element iSPOTElement the element representing the field
   */
  public static void setReferenceVariable(iSPOTElement parent, String name, iSPOTElement element) {
    try {
      StringBuilder sb = new StringBuilder("set");

      sb.append(name);

      char c = Character.toUpperCase(name.charAt(0));

      sb.setCharAt(3, c);

      String s = sb.toString();
      Method m = parent.getClass().getMethod(s, _setRefType);

      m.invoke(parent, element);
    } catch(Exception ex) {
      throw new SPOTException(ex);
    }
  }

  public static String getRelativeClassName(iSPOTElement obj) {
    if (obj == null) {
      return null;
    }

    String s = (String) _fromSpotName.get(obj.spot_getClassName());

    if (s != null) {
      return s;
    }

    String       spot = iSPOTConstants.SPOT_PACKAGE_NAME;
    String       rel  = null;
    iSPOTElement o    = obj;

    while(o != null) {
      s = getPackageName(o.getClass());

      if (!s.equals(spot)) {
        rel = s + ".";

        break;
      }

      o = o.spot_getParent();
    }

    s = obj.spot_getClassName();

    if ((rel != null) && s.startsWith(rel)) {
      s = s.substring(rel.length());
    }

    return s;
  }

  public static String resolveClassName(iSPOTElement obj, String type) {
    if (type == null) {
      return null;
    }

    String s = (String) _toSpotName.get(type);

    if (s != null) {
      return s;
    }

    int n = type.indexOf('.');

    if (n == -1) {
      String spot = iSPOTConstants.SPOT_PACKAGE_NAME;
      String rel  = null;

      while(obj != null) {
        s = getPackageName(obj.getClass());

        if (!s.equals(spot)) {
          rel = s;

          break;
        }

        obj = obj.spot_getParent();
      }

      if (rel == null) {
        rel = spot;
      }

      type = rel + "." + type;
    } else {
      type = type.replace(':', '.');
    }

    return type;
  }

  /**
   * Retrieves the SPOT name for the specified class.
   *
   * @return The name of the element
   */
  public static String getRelativeShortName(Class caller, Class cls) {
    String s = cls.getName();

    do {
      String ss = (String) _fromSpotName.get(s);

      if (ss != null) {
        s = ss;

        break;
      }

      if (!getPackageName(caller.getClass()).equals(getPackageName(cls))) {
        break;
      }

      int i = s.lastIndexOf('.');

      if (i != -1) {
        s = s.substring(i + 1);
      }
    } while(false);

    return s;
  }

  public static iPackageHelper getPackageHelper() {
    return packageHelper;
  }

  public static void setPackageHelper(iPackageHelper helper) {
    packageHelper = helper;
  }
}
