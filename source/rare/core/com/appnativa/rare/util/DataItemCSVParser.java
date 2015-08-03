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

import com.appnativa.rare.TemplateHandler;
import com.appnativa.rare.iDataItemParserCallback;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.spot.ItemDescription;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SDFNode;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.MutableInteger;
import com.appnativa.util.SNumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import java.nio.channels.ClosedChannelException;

import java.text.ParseException;

/**
 * Parser for csv type format
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class DataItemCSVParser {
  protected char           ldSep       = 0;
  protected char           riSeparator = '\0';
  protected CharScanner    scanner     = new CharScanner();
  protected CharScanner    fscanner    = new CharScanner();
  protected boolean        unescape    = false;
  protected BufferedReader biReader;
  protected char           colSep;
  protected MutableInteger itemLevel;
  protected CharArray      quotedCleaner;

  public DataItemCSVParser() {
    scanner.setParameternCharacters('{', '}');
    fscanner.setParameternCharacters('{', '}');
    colSep = ActionLink.DEFAULT_COL_SEPARATOR;
  }

  /**
   * Constructs a new instance
   * @param reader the reader
   */
  public DataItemCSVParser(Reader reader) {
    scanner.setParameternCharacters('{', '}');
    fscanner.setParameternCharacters('{', '}');
    reset(reader, ActionLink.DEFAULT_COL_SEPARATOR, (char) 0);
  }

  /**
   * Constructs a new instance
   *
   * @param input {@inheritDoc}
   * @param colsep {@inheritDoc}
   * @param ldsep {@inheritDoc}
   */
  public DataItemCSVParser(Reader input, char colsep, char ldsep) {
    this(input);
    reset(input, colsep, ldsep);
  }

  public void parse(iWidget context, int cols, iDataItemParserCallback callback) throws IOException {
    CharScanner        sc  = scanner;
    CharScanner        fsc = fscanner;
    BufferedReader     bi  = biReader;
    String             line;
    int                n;
    int                p;
    char               c = colSep;
    char[]             a;
    RenderableDataItem item;
    RenderableDataItem row;
    int                ocols = cols;
    DataItem           di;

    callback.startedParsing();

    if (cols < 0) {
      cols = Integer.MAX_VALUE;
    }

    if (colSep == 0) {
      cols = 0;
    }

    try {
      while((line = bi.readLine()) != null) {
        line = line.trim();

        if (line.length() == 0) {
          continue;
        }

        sc.reset(line);
        sc.trim();

        if (sc.getLength() == 0) {
          continue;
        }

        a   = sc.getContent();
        row = null;

        if (riSeparator != 0) {
          n = sc.indexOf(riSeparator, false, false);

          if (n == -1) {
            itemLevel.setValue(1);
          } else {
            p = sc.getPosition();
            itemLevel.setValue(SNumber.intValue(a, p, n - p, false));
            p = CharScanner.indexOf(a, p, n - p, '{');

            if (p != -1) {
              fsc.reset(a, p, n - p, false);
              di = new DataItem();
              parseItem(context, fsc, di);
              row           = callback.createItemEx(di);
              row.modelData = itemLevel;
            }

            sc.consume(n + 1);
          }
        }

        if (cols == 0) {
          row = parseColumn(context, sc, callback, false);

          if (itemLevel != null) {
            row.modelData = itemLevel;
          }
        } else {
          if (row == null) {
            row = callback.createItem(null, RenderableDataItem.TYPE_STRING, null, null, null);

            if (itemLevel != null) {
              row.modelData = itemLevel;
            }
          }

          if (ocols > 0) {
            row.ensureCapacity(ocols);
          }

          int count = 0;

          while(true) {
            n = sc.indexOf(c, true, true);

            if (n == -1) {
              item = parseColumn(context, sc, callback, false);
              sc.consume(sc.getLength());
              row.add(item);
              count++;

              break;
            } else {
              p = sc.getRelPosition();
              n = n - p;
              fsc.reset(a, p, n, false);
              sc.consume(n + 1);
              item = parseColumn(context, fsc, callback, false);
            }

            if (item == null) {
              item = callback.createItem(null, RenderableDataItem.TYPE_STRING, null, null, null);
            }

            row.add(item);
            count++;
          }

          if (count > ocols) {
            ocols = count;
          }
        }

        callback.addParsedRow(row);
      }
    } catch(ClosedChannelException e) {}
    finally {
      callback.finishedParsing();
    }
  }

  public Column parseColumnItem(iWidget context, String value, iDataItemParserCallback callback) throws IOException {
    if (value == null) {
      return createColumn(value, callback);
    }

    int n = value.indexOf('{');

    if (n == -1) {
      if (ldSep != 0) {
        n = value.indexOf(ldSep);
      }

      if (n == -1) {
        return createColumn(value, callback);
      }
    }

    scanner.reset(value);

    RenderableDataItem item = parseColumn(context, scanner, callback, true);

    if (item instanceof Column) {
      return (Column) item;
    }

    Column c = new Column();

    c.copy(item);
    c.setColumnTitle(item.toString());
    c.setValue(null);

    return c;
  }

  public RenderableDataItem parseDataItem(iWidget context, String value, iDataItemParserCallback callback)
          throws IOException {
    if (value == null) {
      return callback.createItem(value);
    }

    int n = value.indexOf('{');

    if ((n > 0) && (value.charAt(n - 1) == '\\')) {
      n = -1;
    }

    if (n == -1) {
      if (ldSep != 0) {
        n = value.indexOf(ldSep);
      }

      if ((n > 0) && (value.charAt(n - 1) == '\\')) {
        n = -1;
      }

      if (n == -1) {
        return callback.createItem(value);
      }
    }

    scanner.reset(value);

    return parseColumn(context, scanner, callback, false);
  }

  /**
   * Resets the parser to use the specified options
   *
   * @param input the input for the data
   * @param colsep the column separator
   * @param ldsep the linked data separator
   */
  public void reset(Reader input, char colsep, char ldsep) {
    if (input != null) {
      if (input instanceof BufferedReader) {
        biReader = (BufferedReader) input;
      } else {
        biReader = new BufferedReader(input, 255);
      }
    }

    colSep = colsep;
    ldSep  = ldsep;
  }

  public void setLinkedDataSeparator(char sep) {
    this.ldSep = sep;
  }

  public void setRowInfoSeparator(char sep) {
    this.riSeparator = sep;

    if (riSeparator != 0) {
      itemLevel = new MutableInteger(0);
    }
  }

  public void setUnescape(boolean unescape) {
    this.unescape = unescape;
  }

  public char getRowInfoSeparator() {
    return riSeparator;
  }

  RenderableDataItem parseColumn(iWidget context, CharScanner sc, iDataItemParserCallback callback,
                                 boolean itemdescription)
          throws IOException {
    RenderableDataItem di = null;

    sc.trim();

    String value      = null;
    String linkedData = null;
    int    n, p;
    int    len = sc.getLength();
    int    pos = sc.getPosition();
    char   a[] = sc.getContent();

    n = sc.indexOf('{', true, false);

    if (ldSep != 0) {
      p = CharScanner.indexOf(a, pos, (n == -1)
                                      ? len
                                      : n - pos, ldSep);

      if (p != -1) {
        len        = p - pos;
        linkedData = new String(a, pos, len);
        sc.consume(len + 1);
        pos = sc.getPosition();
      }
    }

    p = (n == -1)
        ? -1
        : sc.lastIndexOf('}', true, false);

    if ((n == -1) || (p == -1)) {
      len   = sc.getLength();
      value = unescape
              ? CharScanner.unescapeEx(a, pos, len)
              : cleanQuoted(a, pos, len);
      di    = callback.createItem(value, RenderableDataItem.TYPE_STRING, linkedData, null, null);
    } else {
      int br = sc.indexOfNonWhiteSpace(p + 1);

      if ((br != -1) && (a[br] == '[')) {
        br = sc.indexOf(']', true, false);

        if (br != -1) {
          p = br;
        }
      }

      if (n == sc.getPosition()) {
        p++;
        n += sc.getLength();

        if (p < n) {
          value = CharScanner.unescapeEx(a, p, n - p);
          sc.chop(n - p);
        }
      } else {
        pos   = sc.getPosition();
        len   = n - sc.getPosition();
        value = unescape
                ? CharScanner.unescapeEx(a, pos, len)
                : cleanQuoted(a, pos, len);
        sc.consume(len);
        len = sc.getLength() - p;

        if ((value == null) && (len > 0)) {
          pos   = sc.getPosition() + sc.getLength() - len;
          value = unescape
                  ? CharScanner.unescapeEx(a, pos, len)
                  : cleanQuoted(a, pos, len);
          sc.consume(len);
        }
      }

      if (itemdescription) {
        ItemDescription item = new ItemDescription();

        parseItem(context, sc, item);

        if ((value != null) && (item.title.getValue() == null)) {
          item.title.setValue(value);
        }

        if ((linkedData != null) && (item.linkedData.getValue() == null)) {
          item.linkedData.setValue(linkedData);
        }

        di = callback.createItem(item);
      } else {
        DataItem item = new DataItem();

        parseItem(context, sc, item);

        if ((value != null) && (item.value.getValue() == null)) {
          item.value.setValue(value);
        }

        if ((linkedData != null) && (item.linkedData.getValue() == null)) {
          item.linkedData.setValue(linkedData);
        }

        di = callback.createItemEx(item);
      }
    }

    return di;
  }

  void parseItem(iWidget context, Reader reader, SPOTSequence item) throws IOException {
    SDFNode node = SDFNode.parse(reader, context.getURLResolver(), null, false);

    node = node.getFirstNode();

    SDFNode tnode = node.getNode("templateName");
    String  name  = (tnode == null)
                    ? null
                    : tnode.getNodeValue();

    if (name != null) {
      String tname = (tnode == null)
                     ? null
                     : tnode.getNodeAttribute("context");

      TemplateHandler.getInstance(context, null).applyTemplate(item, name, tname);
    }

    item.fromSDF(node);
  }

  private String cleanQuoted(char[] a, int pos, int len) {
    try {
      CharArray ret = quotedCleaner;

      if (ret == null) {
        ret = new CharArray();
      } else {
        ret._length = 0;
      }

      CharScanner.cleanQuoted(a, pos, len, ret);

      return ret.toString();
    } catch(ParseException e) {
      return new String(a, pos, len);
    }
  }

  private Column createColumn(String value, iDataItemParserCallback callback) {
    ItemDescription di = new ItemDescription();

    di.title.setValue(value);

    return (Column) callback.createItem(di);
  }
}
