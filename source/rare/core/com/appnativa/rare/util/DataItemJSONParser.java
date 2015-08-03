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

package com.appnativa.rare.util;

import com.appnativa.rare.iDataItemParserCallback;
import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharArray;
import com.appnativa.util.json.JSONArray;
import com.appnativa.util.json.JSONException;
import com.appnativa.util.json.JSONObject;
import com.appnativa.util.json.JSONStructuredNode;
import com.appnativa.util.json.JSONTokener;

import java.io.IOException;
import java.io.Reader;

import java.util.List;

/**
 * Parser for csv type format
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public class DataItemJSONParser {
  protected boolean   unescape = false;
  protected CharArray quotedCleaner;
  protected Reader    sourceReader;

  public DataItemJSONParser() {}

  /**
   * Constructs a new instance
   *
   * @param reader
   *          the reader
   */
  public DataItemJSONParser(Reader reader) {
    reset(reader);
  }

  public void parse(iWidget context, boolean tabular, iDataItemParserCallback callback) throws IOException {
    JSONObject json = null;

    try {
      JSONTokener t = new JSONTokener(sourceReader);

      json = new JSONObject(t);
    } catch(JSONException e) {
      callback.startedParsing();

      throw new IOException(e);
    }

    parse(context, json, callback);
  }

  public void parse(iWidget context, JSONObject json, iDataItemParserCallback callback) throws IOException {
    callback.startedParsing();

    JSONArray columns = (json == null)
                        ? null
                        : json.optJSONArray("_columns");
    JSONArray rows    = (json == null)
                        ? null
                        : json.optJSONArray("_rows");

    try {
      if (rows != null) {
        addRows(null, columns, rows, callback);
      }

      callback.finishedParsing();
    } catch(Exception e) {
      if (!(e instanceof IOException)) {
        e = new IOException(e);
      }

      throw(IOException) e;
    }
  }

  /**
   * Resets the parser to use the specified options
   *
   * @param input
   *          the input for the data
   */
  public void reset(Reader input) {
    if (input != null) {
      sourceReader = input;
    }
  }

  public void setUnescape(boolean unescape) {
    this.unescape = unescape;
  }

  protected void addRows(List<RenderableDataItem> list, JSONArray columns, JSONArray rows,
                         iDataItemParserCallback callback) {
    int len = rows.size();

    for (int i = 0; i < len; i++) {
      RenderableDataItem row;

      if (columns == null) {
        row = createRow(rows.getJSONArray(i), callback);
      } else {
        row = createRow(columns, rows.getJSONObject(i), callback);
      }

      if (row != null) {
        if (list == null) {
          callback.addParsedRow(row);
        } else {
          list.add(row);
        }
      }
    }
  }

  protected void addItems(List<RenderableDataItem> list, JSONArray array, iDataItemParserCallback callback) {
    RenderableDataItem item;
    int                len = array.size();

    for (int i = 0; i < len; i++) {
      item = null;

      Object o = array.get(i);

      if (o instanceof JSONArray) {
        item = callback.createItem(null);

        JSONArray a = (JSONArray) o;

        item.ensureCapacity(a.size());
        addItems(item.getItems(), a, callback);
      } else if (o instanceof JSONObject) {
        item = createItem((JSONObject) o, callback);
      } else {
        item = callback.createItem(o);
      }

      if ((item == null) && (list != null)) {
        callback.createItem(null);
      }

      if (list == null) {
        if (item != null) {
          callback.addParsedRow(item);
        }
      } else {
        list.add((item == null)
                 ? callback.createItem(null)
                 : item);
      }
    }
  }

  protected RenderableDataItem createItem(JSONObject json, iDataItemParserCallback callback) {
    JSONArray columns = json.optJSONArray("_columns");
    JSONArray rows    = json.optJSONArray("_rows");

    if ((columns != null) && (rows != null)) {
      RenderableDataItem row = new RenderableDataItem();

      row.ensureCapacity(rows.size());
      addRows(row.getItems(), columns, rows, callback);
    }

    if (rows != null) {
      RenderableDataItem item = callback.createItem(null);

      item.ensureCapacity(rows.size());
      addItems(item.getItems(), rows, callback);

      return item;
    }

    JSONStructuredNode node = new JSONStructuredNode("row", json);
    DataItem           di   = new DataItem();

    di.fromStructuredNode(node);

    return callback.createItemEx(di);
  }

  protected RenderableDataItem createRow(JSONArray array, iDataItemParserCallback callback) {
    RenderableDataItem row = new RenderableDataItem();
    int                len = array.size();

    if (len > 0) {
      row.ensureCapacity(len);
      addItems(row.getItems(), array, callback);
    }

    return row;
  }

  protected RenderableDataItem createRow(JSONArray columns, JSONObject json, iDataItemParserCallback callback) {
    RenderableDataItem row = new RenderableDataItem();
    RenderableDataItem item;
    int                len = columns.size();

    row.ensureCapacity(len);

    for (int i = 0; i < len; i++) {
      String col = columns.getString(i);
      Object o   = json.opt(col);

      if (o instanceof JSONArray) {
        item = callback.createItem(null);

        JSONArray a = (JSONArray) o;

        item.ensureCapacity(a.size());
        addItems(item.getItems(), a, callback);
      }

      if (o instanceof JSONObject) {
        item = createItem((JSONObject) o, callback);
      } else {
        item = callback.createItem(o);
      }

      row.add(item);
    }

    return row;
  }
}
