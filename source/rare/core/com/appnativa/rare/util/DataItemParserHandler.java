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

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iDataCollection;
import com.appnativa.rare.iDataItemParser;
import com.appnativa.rare.iDataItemParserCallback;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.DataCollection;
import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.widget.aPlatformWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.FilterableList;
import com.appnativa.util.MutableInteger;
import com.appnativa.util.ObjectHolder;

import java.io.IOException;
import java.io.Reader;

import java.net.URL;

import java.nio.channels.ClosedChannelException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class DataItemParserHandler extends DataItemCollection implements iDataItemParserCallback, Runnable {
  private boolean                        tabular = false;
  private ActionLink                     actionLink;
  private iFunctionCallback              callback;
  private RenderableDataItem             currentRow;
  private boolean                        hasRowInfo;
  private LinkedList<RenderableDataItem> levelStack;
  private RenderableDataItem             rootItem;
  private iWidget                        widget;
  static HashMap<String, Class>          customParsers;
  static boolean                         hasCustomParsers =
    Platform.getUIDefaults().getBoolean("Rare.DataItemParser.hasCustom", false);

  /**
   * Constructs a new instance
   *
   * @param v
   *          {@inheritDoc}
   */
  public DataItemParserHandler(iWidget v) {
    super("no-name", null);

    if (v == null) {
      throw new NullPointerException();
    }

    widget = v;
  }

  /**
   * Constructs a new instance
   *
   * @param v
   *          {@inheritDoc}
   * @param list
   *          {@inheritDoc}
   */
  public DataItemParserHandler(iWidget v, List<RenderableDataItem> list) {
    super("no-name", list);

    if (v == null) {
      throw new NullPointerException();
    }

    widget = v;
  }

  @Override
  public void addParsedRow(RenderableDataItem row) {
    if (!hasRowInfo) {
      theList.add(row);

      return;
    }

    Object o = row.getModelData();
    int    l = 1;

    if (o instanceof MutableInteger) {
      MutableInteger level = (MutableInteger) o;

      row.setModelData(null);
      l = level.intValue();

      if (l < 1) {
        l = 1;
      }
    }

    if ((l < 2) || (currentRow == null)) {
      theList.add(row);
      currentRow = row;

      if (levelStack != null) {
        levelStack.clear();
      }
    } else {
      RenderableDataItem ci = null;

      if (levelStack == null) {
        levelStack = new LinkedList<RenderableDataItem>();
      }

      int currentLevel = levelStack.size() + 1;

      if (currentLevel > l) {
        while(currentLevel > l) {
          levelStack.poll();
          currentLevel--;
        }

        levelStack.peek().add(row);
      } else if (currentLevel == l) {
        levelStack.peek().add(row);
      } else {
        ci = currentRow;
        ci = ci.getItem(0);
        ci.add(row);

        if (levelStack.peek() != currentRow) {
          levelStack.add(0, ci);
        }
      }

      currentRow = row;
    }
  }

  public static iDataCollection createCollection(iPlatformAppContext ctx, DataCollection dc, iFunctionCallback cb) {
    Map<String, Object> attributes = null;
    ActionLink          link       = ActionLink.getActionLink(ctx.getRootViewer(), dc.dataURL, 0);

    if (dc.attributes.getValue() != null) {
      attributes = DataParser.parseNameValuePairs(dc.attributes);
    }

    boolean tabular = dc.tabular.booleanValue();

    return createCollection(ctx, dc.name.getValue(), link, attributes, tabular, cb);
  }

  public static iDataCollection createCollection(iPlatformAppContext ctx, String name, ActionLink link,
          Map<String, Object> attributes, boolean tabular, iFunctionCallback cb) {
    DataItemParserHandler p = new DataItemParserHandler(ctx.getRootViewer());

    p.setActionLink(link);
    p.widget  = ctx.getRootViewer();
    p.theName = name;
    p.tabular = tabular;

    if ((attributes != null) && (attributes.size() > 0)) {
      p.attributeMap = attributes;
    }

    if (cb != null) {
      p.load(cb);
    }

    return p;
  }

  @Override
  public RenderableDataItem createItem(Object value) {
    if (value instanceof DataItem) {
      return aPlatformWidget.populateItem(widget, (DataItem) value, null);
    }

    return new RenderableDataItem(value);
  }

  @Override
  public RenderableDataItem createItem(Object value, int type, Object data, Object icon, Object color) {
    iPlatformIcon ic = null;
    UIColor       c  = null;

    if (icon != null) {
      ic = (icon instanceof String)
           ? getIcon((String) icon, null)
           : (iPlatformIcon) icon;
    }

    if (color != null) {
      c = (color instanceof String)
          ? UIColorHelper.getColor((String) color)
          : (UIColor) color;
    }

    RenderableDataItem di = new RenderableDataItem(value, type, data, ic);

    if (c != null) {
      di.setForeground(c);
    }

    return di;
  }

  @Override
  public RenderableDataItem createItemEx(DataItem item) {
    return aPlatformWidget.populateItem(widget, item, null);
  }

  @Override
  public void disposeCollection() {
    super.disposeCollection();
    actionLink = null;
  }

  @Override
  public void finishedParsing() {
    if (levelStack != null) {
      levelStack.clear();
    }

    levelStack = null;
    currentRow = null;
  }

  public void load(iFunctionCallback cb) {
    callback = cb;

    if (actionLink == null) {
      return;
    }

    if ((cb == null) &&!actionLink.isDeferred()) {
      run();
    } else {
      Platform.getAppContext().executeBackgroundTask(this);
    }
  }

  /**
   * Parses the items represented by the specified link
   *
   * @param context
   *          the context;
   * @param link
   *          the link
   * @param columnCount
   *          the number of expected columns (use -1 for unknown)
   *
   * @return the list of parsed items
   *
   * @throws Exception
   */
  public static List<RenderableDataItem> parse(iWidget context, ActionLink link, int columnCount) throws IOException {
    if (link.isCollection()) {
      Collection<RenderableDataItem> coll = link.getCollection();

      if (coll instanceof List) {
        return (List<RenderableDataItem>) coll;
      } else {
        return new ArrayList<RenderableDataItem>(coll);
      }
    } else {
      DataItemParserHandler p = new DataItemParserHandler(context);

      p.setActionLink(link);
      parse(context, link, columnCount, p);

      return p.theList;
    }
  }

  /**
   * Parses the items represented by the specified set
   *
   * @param context
   *          the context
   * @param items
   *          the set of items
   *
   * @param list
   *          the list to use to store the parsed items
   *
   */
  public static void parse(iWidget context, SPOTSet items, List<RenderableDataItem> list) {
    if (context == null) {
      throw new NullPointerException();
    }

    int len = (items == null)
              ? 0
              : items.getCount();

    if (len > 0) {
      DataItem item;

      for (int i = 0; i < len; i++) {
        item = (DataItem) items.getEx(i);
        list.add(aPlatformWidget.populateItem(context, item, null));
      }
    }
  }

  protected static void parseCollection(iWidget context, ActionLink link, int columnCount,
          iDataItemParserCallback callback)
          throws IOException {
    Collection<RenderableDataItem> coll = link.getCollection();

    callback.startedParsing();

    for (RenderableDataItem row : coll) {
      callback.addParsedRow(row);
    }

    callback.finishedParsing();
  }

  public static void parse(iWidget context, ActionLink link, int columnCount, iDataItemParserCallback callback)
          throws IOException {
    if (link.isCollection()) {
      parseCollection(context, link, columnCount, callback);

      return;
    }

    iDataItemParser dp = createCustomParser(link.getParserClassName());

    if (dp != null) {
      dp.parse(context, link, columnCount, callback);

      return;
    }

    try {
      Reader reader   = link.getReader();
      String mimeType = link.getContentType();

      link.setSeparatorsFromMimeType(mimeType, true);

      if (mimeType == null) {
        mimeType = iConstants.TEXT_MIME_TYPE;
      } else {
        dp = createCustomParserFromMime(mimeType);

        if (dp != null) {
          dp.parse(context, link, columnCount, callback);

          return;
        }
      }

      if (mimeType.startsWith(iConstants.XML_MIME_TYPE)) {
        throw new UnsupportedOperationException("XML format not supported");
      } else if (mimeType.startsWith(iConstants.SDF_MIME_TYPE) || mimeType.startsWith(iConstants.RML_MIME_TYPE)) {
        DataItem items;

        try {
          items = (DataItem) DataParser.loadSPOTObjectSDF(context, reader, new DataItem(false), mimeType,
                  link.getConnection().getURL());
          parse(context, columnCount, items, callback);
        } catch(Exception ex) {
          throw ApplicationException.runtimeException(ex);
        }
      } else if (mimeType.startsWith(iConstants.JSON_MIME_TYPE) || mimeType.startsWith("text/x-json")) {
        DataItemJSONParser parser = new DataItemJSONParser(reader);

        parser.parse(context, columnCount > 0, callback);
      } else {
        DataItemCSVParser parser = new DataItemCSVParser(reader, link.getColumnSeparator(),
                                     link.getLinkedDataSeparator());

        parser.setRowInfoSeparator(link.getRowInfoSeparator());
        parser.setUnescape(link.isUnescape());
        parser.parse(context, columnCount, callback);
      }
    } catch(ClosedChannelException e) {}
    finally {
      link.close();
    }
  }

  public static void parse(iWidget context, int cols, DataItem items, iDataItemParserCallback callback)
          throws IOException {
    SPOTSet set = DataParser.resolveSet(context, items.getSubItems(), DataItem.class);

    callback.startedParsing();

    RenderableDataItem di;

    try {
      di = parseItem(items, callback, false);
      callback.setRootItem(di);

      if (set != null) {
        int      len = set.getCount();
        DataItem item;

        for (int i = 0; i < len; i++) {
          item = (DataItem) set.get(i);
          di   = parseItem(item, callback, true);
          callback.addParsedRow(di);
        }
      }
    } finally {
      if (!context.isDisposed()) {
        callback.finishedParsing();
      }
    }
  }

  public static void parse(iWidget context, String mimeType, Reader reader, int columnCount,
                           iDataItemParserCallback callback, URL contextURL)
          throws Exception {
    if (mimeType == null) {
      mimeType = iConstants.TEXT_MIME_TYPE;
    }

    iDataItemParser dp = createCustomParser(mimeType);

    if (dp != null) {
      dp.parse(context, reader, mimeType, columnCount, callback);

      return;
    }

    if (mimeType.startsWith(iConstants.XML_MIME_TYPE)) {
      throw new UnsupportedOperationException("XML format not supported");
    } else if (mimeType.startsWith(iConstants.SDF_MIME_TYPE) || mimeType.startsWith(iConstants.RML_MIME_TYPE)) {
      DataItem items = (DataItem) DataParser.loadSPOTObjectSDF(context, reader, new DataItem(false), mimeType,
                         contextURL);

      parse(context, columnCount, items, callback);
    } else if (mimeType.startsWith(iConstants.JSON_MIME_TYPE) || mimeType.startsWith("text/x-json")) {
      DataItemJSONParser parser = new DataItemJSONParser(reader);

      parser.parse(context, columnCount > 0, callback);
    } else {
      char              sep    = ActionLink.getSeparatorCharacter(mimeType, ActionLink.MIME_COLUMN_SEPARATOR_PREFIX);
      char              ldsep  = ActionLink.getSeparatorCharacter(mimeType, ActionLink.MIME_LD_SEPARATOR_PREFIX);
      DataItemCSVParser parser = new DataItemCSVParser(reader, sep, ldsep);

      parser.parse(context, columnCount, callback);
    }
  }

  public static RenderableDataItem parseItem(DataItem item, iDataItemParserCallback callback, boolean parseSubs) {
    RenderableDataItem di = callback.createItemEx(item);

    if (parseSubs) {
      SPOTSet set = item.getSubItems();
      int     len = (set == null)
                    ? 0
                    : set.getCount();

      if (len > 0) {
        di.ensureCapacity(len);

        for (int i = 0; i < len; i++) {
          di.add(parseItem((DataItem) set.get(i), callback, true));
        }
      }
    }

    return di;
  }

  @Override
  public void refresh(iWidget context) throws IOException {
    synchronized(this) {
      clearCollection();

      if (context == null) {
        context = widget;
      }

      if (actionLink != null) {
        parse(context, actionLink, tabular
                                   ? -1
                                   : 0, this);
      }

      loaded = true;
    }
  }

  @Override
  public void run() {
    iFunctionCallback cb = callback;

    callback = null;

    try {
      refresh(widget);

      if (cb != null) {
        callCallback(theList, false, cb);
      }
    } catch(Exception e) {
      if (cb == null) {
        throw ApplicationException.runtimeException(e);
      } else {
        callCallback(e, true, cb);
      }
    }
  }

  @Override
  public void startedParsing() {
    if (theList == null) {
      theList = new FilterableList<RenderableDataItem>();
    }
  }

  @Override
  public void setRootItem(RenderableDataItem item) {
    rootItem = item;
  }

  public iPlatformIcon getIcon(String icon, String description) {
    return (widget == null)
           ? null
           : widget.getIcon(icon, description);
  }

  /**
   * Gets the root item to the parser hierarchy
   *
   * @return the root item to the parser hierarchy
   */
  public RenderableDataItem getRootItem() {
    return rootItem;
  }

  @Override
  protected List<RenderableDataItem> getList(iWidget context) {
    synchronized(this) {
      if ((actionLink != null) && (theList == null)) {
        try {
          refresh(context);
        } catch(IOException e) {
          throw new ApplicationException(e);
        }
      }

      return super.getList(context);
    }
  }

  private void callCallback(final Object returnValue, final boolean canceled, final iFunctionCallback cb) {
    final ObjectHolder h = new ObjectHolder(actionLink, null, returnValue);

    Platform.invokeLater(new Runnable() {
      @Override
      public void run() {
        if (canceled) {
          cb.finished(true, h);
        } else {
          cb.finished(false, h);
        }
      }
    });
  }

  private static iDataItemParser createCustomParser(String name) {
    if (name == null) {
      return null;
    }

    if (name.startsWith(iConstants.SCRIPT_PREFIX)) {
      return new ScriptDataParser(name.substring(iConstants.SCRIPT_PREFIX_LENGTH));
    }

    try {
      return (iDataItemParser) (Platform.loadClass(name)).newInstance();
    } catch(Exception e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  private static iDataItemParser createCustomParserFromMime(String mimeType) {
    String name = null;
    int    n    = mimeType.indexOf("parser=");

    if (n != -1) {
      n += "parser=".length();

      int p = mimeType.indexOf(';', n);

      if (p == -1) {
        p = mimeType.length();
      }

      name = mimeType.substring(n, p).trim();

      if (name.length() == 0) {
        name = null;
      }
    }

    if (name == null) {
      if (hasCustomParsers) {
        int    p = mimeType.indexOf(';');
        String s = mimeType;

        if (p != -1) {
          s = mimeType.substring(0, p);
        }

        name = "Rare.DataItemParser." + s.replace('/', '_');

        Class cls = (customParsers == null)
                    ? null
                    : customParsers.get(name);

        if (cls == null) {
          name = Platform.getUIDefaults().getString(name);

          if (name != null) {
            try {
              cls = Platform.loadClass(s);

              if (customParsers == null) {
                customParsers = new HashMap<String, Class>(3);
              }

              customParsers.put(s, cls);
            } catch(ClassNotFoundException e) {
              throw new ApplicationException(e);
            }
          } else {
            return null;
          }
        }

        try {
          return (iDataItemParser) cls.newInstance();
        } catch(Exception e) {
          throw new ApplicationException(e);
        }
      }

      return null;
    }

    return createCustomParser(name);
  }

  private void setActionLink(ActionLink link) {
    this.actionLink = link;
    this.hasRowInfo = link.getRowInfoSeparator() != 0;
  }

  public static class ScriptDataParser implements iDataItemParser {
    Object                          code;
    DataEvent                       dataEvent;
    private iDataItemParserCallback callback;
    private int                     columnCount;
    private ActionLink              link;
    private Reader                  reader;
    private boolean                 unescape;

    ScriptDataParser(String code) {
      this.code = code;
    }

    @Override
    public Collection<RenderableDataItem> parse(iWidget context, ActionLink link, int columnCount) {
      return (Collection<RenderableDataItem>) this.parse(context, link, null, columnCount, null);
    }

    @Override
    public void parse(iWidget context, ActionLink link, int columnCount, iDataItemParserCallback callback) {
      this.parse(context, link, null, columnCount, callback);
    }

    @Override
    public Collection<RenderableDataItem> parse(iWidget context, Reader reader, String mimeType, int columnCount) {
      return (Collection<RenderableDataItem>) this.parse(context, null, reader, columnCount, null);
    }

    @Override
    public void parse(iWidget context, Reader reader, String mimeType, int columnCount,
                      iDataItemParserCallback callback) {
      this.parse(context, null, reader, columnCount, callback);
    }

    public void setUnescape(boolean unescape) {
      this.unescape = unescape;
    }

    public iDataItemParserCallback getCallback() {
      return callback;
    }

    public int getColumnCount() {
      return columnCount;
    }

    public ActionLink getLink() {
      return link;
    }

    public Reader getReader() throws IOException {
      if (link != null) {
        return link.getReader();
      }

      return reader;
    }

    public boolean isUnescape() {
      return unescape;
    }

    Object parse(iWidget context, ActionLink link, Reader reader, int columnCount, iDataItemParserCallback callback) {
      try {
        this.link        = link;
        this.columnCount = columnCount;
        this.callback    = callback;
        this.reader      = reader;

        if (context == null) {
          context = Platform.getContextRootViewer();
        }

        if (dataEvent == null) {
          dataEvent = new DataEvent(context, this);
        } else {
          dataEvent.setData(this);
          dataEvent.setTarget(context);
        }

        iScriptHandler sh = context.getScriptHandler();

        return Platform.evaluate(context, sh, code, dataEvent);
      } finally {
        if (link != null) {
          link.close();
        }
      }
    }
  }
}
