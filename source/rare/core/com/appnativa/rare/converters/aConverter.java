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

package com.appnativa.rare.converters;

import java.io.IOException;
import java.io.Reader;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FormatException;
import com.appnativa.util.Helper;
import com.appnativa.util.Streams;
import com.appnativa.util.StringReaderEx;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aConverter implements iDataConverter {
  Class                objectClass;
  protected Comparable maxValue;
  protected Comparable minValue;
  protected Object     theContext;
  protected iWidget    widget;

  /** Creates a new instance of aConverter */
  public aConverter() {}

  @Override
  public Object createContext(iWidget widget, String value) {
    return value;
  }

  public static iDataConverter createConverter(iWidget widget, String name, int type) throws Exception {
    Class cls = null;

    if (name != null) {
      cls = Platform.getDataConverterClass(name);
    }

    if (cls == null) {
      cls = RenderableDataItem.getDefaultConverterClass(type);
    }

    if (cls == null) {
      return null;
    }

    return Platform.getDataConverter(cls);
  }

  public static FormatException formatException(iWidget widget, String value, Class cls) {
    iPlatformAppContext app = (widget == null)
                              ? Platform.getAppContext()
                              : widget.getAppContext();

    return new FormatException(app.getResourceAsString("Rare.runtime.text.cannotConvert"), value, cls.getName());
  }

  public static FormatException formatException(iWidget widget, String format, String value) {
    iPlatformAppContext app = (widget == null)
                              ? Platform.getAppContext()
                              : widget.getAppContext();

    return new FormatException(app.getResourceAsString("Rare.runtime.text.formatInvalid"), value, format);
  }

  public Object fromString(String string, ConverterContext ctx) {
    Object o = null;

    if (ctx != null) {
      o = ctx.getUserObject();
    }

    if (o == null) {
      o = theContext;
    }

    return objectFromString(widget, string, o);
  }

  @Override
  public Object objectFromString(iWidget widget, String value) {
    return objectFromString(widget, value, null);
  }

  @Override
  public abstract Object objectFromString(iWidget widget, String value, Object context);

  @Override
  public CharSequence objectToString(iWidget widget, Object object) {
    return objectToString(getWidgetEx(), object, null);
  }

  @Override
  public CharSequence objectToString(iWidget widget, Object object, Object context) {
    return (object == null)
           ? null
           : object.toString();
  }

  @Override
  public boolean objectsAreImmutable(Object context) {
    return false;
  }

  public CharSequence readerToString(Reader r) {
    try {
      if (r instanceof StringReaderEx) {
        return ((StringReaderEx) r).getString();
      }

      return Streams.readerToString(r);
    } catch(IOException ex) {
      Platform.ignoreException(null, ex);

      return null;
    }
  }

  public static void showRangeError(Comparable minValue, Comparable maxValue) {
    Platform.getAppContext().getWindowViewer().setStatus(getRangeError(minValue, maxValue));
  }

  public boolean supportFromString(String string, ConverterContext ctx) {
    return true;
  }

  public boolean supportToString(Object object, ConverterContext ctx) {
    return true;
  }

  public CharSequence toString(Object object, ConverterContext ctx) {
    Object o = null;

    if (ctx != null) {
      o = ctx.getUserObject();
    }

    if (o == null) {
      o = theContext;
    }

    return objectToString(getWidgetEx(), object, o);
  }

  @Override
  public void setMaxValue(Comparable maxValue) {
    this.maxValue = maxValue;
  }

  @Override
  public void setMinValue(Comparable minValue) {
    this.minValue = minValue;
  }

  @Override
  public void setObjectClass(Class cls) {
    objectClass = cls;
  }

  /**
   * @param widget the widget to set
   */
  public void setWidget(iWidget widget) {
    this.widget = widget;
  }

  @Override
  public Comparable getMaxValue() {
    return maxValue;
  }

  @Override
  public Comparable getMinValue() {
    return minValue;
  }

  @Override
  public Class getObjectClass(Object context) {
    return (objectClass == null)
           ? Object.class
           : objectClass;
  }

  public static String getRangeError(Comparable minValue, Comparable maxValue) {
    String              s;
    iPlatformAppContext app = Platform.getAppContext();

    if ((minValue != null) && (maxValue != null)) {
      s = Helper.expandString(app.getResourceAsString("Rare.runtime.text.fieldValueNotInRange"), minValue.toString(),
                              maxValue.toString());
    } else if (minValue != null) {
      s = Helper.expandString(app.getResourceAsString("Rare.runtime.text.fieldValueToSmall"), minValue.toString());
    } else if (maxValue != null) {
      s = Helper.expandString(app.getResourceAsString("Rare.runtime.text.fieldValueToBig"), maxValue.toString());
    } else {
      s = app.getResourceAsString("Rare.runtime.text.fieldInvalidMessage");
    }

    return s;
  }

  @Override
  public iPlatformRenderingComponent getRenderer(iWidget widget, Object context) {
    return null;
  }

  /**
   * @return the widget
   */
  public iWidget getWidget() {
    return widget;
  }

  private iWidget getWidgetEx() {
    iWidget w = getWidget();

    return (w == null)
           ? Platform.getContextRootViewer()
           : w;
  }
}
