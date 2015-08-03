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

package com.appnativa.rare;

import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.widget.iWidget;

import java.io.IOException;
import java.io.Reader;

import java.util.Collection;

/**
 * An interface for entities that can parse data items.
 *
 * @author Don DeCoteau
 */
public interface iDataItemParser {

  /**
   * Parses data items form the given link and returns a collection
   *
   * @param context the widget context
   * @param link the link
   * @param columnCount the number of columns the data contains (can be  -1 for unknown)
   *
   * @return a collection containing the parsed data
   * @throws IOException
   */
  Collection<RenderableDataItem> parse(iWidget context, ActionLink link, int columnCount) throws IOException;

  /**
   * Parses data items form the given link
   *
   * @param context the widget context
   * @param link the link
   * @param columnCount the number of columns the data contains (can be  -1 for unknown)
   * @param callback a callback handler to call to process the parsed items
   * @throws IOException
   */
  void parse(iWidget context, ActionLink link, int columnCount, iDataItemParserCallback callback) throws IOException;

  /**
   * Parses data items form the given reader and returns a collection
   *
   * @param context the widget context
   * @param reader the reader
   * @param mimeType the mime type for the reader's data
   * @param columnCount the number of columns the data contains (can be  -1 for unknown)
   *
   * @return a collection containing the parsed data
   * @throws IOException
   */
  Collection<RenderableDataItem> parse(iWidget context, Reader reader, String mimeType, int columnCount)
          throws IOException;

  /**
   * Parses data items form the given reader
   *
   * @param context the widget context
   * @param reader the reader
   * @param mimeType the mime type for the reader's data
   * @param columnCount the number of columns the data contains (can be  -1 for unknown)
   * @param callback a callback handler to call to process the parsed items
   * @throws IOException
   */
  void parse(iWidget context, Reader reader, String mimeType, int columnCount, iDataItemParserCallback callback)
          throws IOException;
}
