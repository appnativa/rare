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

import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.widget.iWidget;

/**
 * An interface for data converters.
 *
 * @author Don DeCoteau
 */
public interface iDataConverter {

  /**
   * Converts an object to a string
   *
   * @param widget the widget the object is for
   * @param object the object to convert
   *
   * @return a string representation of the object
   */
  public CharSequence objectToString(iWidget widget, Object object);

  /**
   * Converts an object to a string
   *
   * @param widget the widget the object is for
   * @param object the object to convert
   * @param context context information about the value
   *
   * @return a string representation of the object
   */
  public CharSequence objectToString(iWidget widget, Object object, Object context);

  /**
   * Creates a context value from the specified string
   *
   * @param widget the widget the object is for
   * @param value the string representing the context
   *
   * @return the newly created context
   */
  Object createContext(iWidget widget, String value);

  /**
   * Creates an object from its string representation
   *
   * @param widget the widget the object is for
   * @param value the string representation of the object
   * @return the object represented by the specified string
   */
  Object objectFromString(iWidget widget, String value);

  /**
   * Creates an object from its string representation
   *
   * @param widget the widget the object is for
   * @param value the string representation of the object
   * @param context context information about the object
   * @return the object represented by the specified string
   */
  Object objectFromString(iWidget widget, String value, Object context);

  /**
   * Returns whether objects created by this converter are immutable
   *
   * @param context context information about the value
   *
   * @return true if objects created by this converter are immutable; false otherwise
   */
  boolean objectsAreImmutable(Object context);

  /**
   * Gets the maximum allowed value
   *
   * @param max the maximum allowed value
   */
  void setMaxValue(Comparable max);

  /**
   * Sets the minimum allowed value
   *
   * @param min the minimum allowed value
   */
  void setMinValue(Comparable min);

  /**
   * Set the object class for the converter
   *
   * @param cls the class of the type of data that this converter is valid for
   */
  void setObjectClass(Class cls);

  /**
   * Gets the maximum allowed value
   *
   * @return the maximum allowed value
   */
  Comparable getMaxValue();

  /**
   * Gets the minimum allowed value
   *
   * @return the minimum allowed value
   */
  Comparable getMinValue();

  /**
   * Returns the object class for the specified context
   *
   * @param context the context
   *
   * @return the class of the type of data that this context is valid for
   */
  Class getObjectClass(Object context);

  /**
   * Gets a rendering component for rendering objects that this converter handlers
   *
   * @param widget the widget the object is for
   * @param context context information to use toe determine the type of renderer
   *
   * @return a rendering component for rendering objects that this converter handlers
   */
  iPlatformRenderingComponent getRenderer(iWidget widget, Object context);
}
