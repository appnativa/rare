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

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.widget.iWidget;

import java.io.Reader;
import java.io.StringReader;

/**
 * Converts a string/stream to a widget instance
 *
 * @author Don DeCoteau
 */
public class WidgetConverter extends aConverter {

  /**
   * Creates a new instance of
   */
  public WidgetConverter() {}

  @Override
  public Object objectFromString(iWidget widget, String value, Object context) {
    Reader                 stream = new StringReader(value);
    WidgetConverterContext ctx    = null;

    if (context instanceof WidgetConverterContext) {
      ctx = (WidgetConverterContext) context;
    }

    if (ctx == null) {
      if (widget == null) {
        widget = Platform.getContextRootViewer();
      }
    }

    Widget wc;

    try {
      wc = (Widget) DataParser.loadSPOTObjectSDF(widget, stream, null, null, null);
    } catch(Exception ex) {
      if (ex instanceof RuntimeException) {
        throw(RuntimeException) ex;
      } else {
        throw new ApplicationException(ex);
      }
    }

    return aContainer.createWidget(widget.getContainerViewer(), wc);
  }
}
