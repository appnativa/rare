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

package com.appnativa.rare.ui;

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.ColorConverter;
import com.appnativa.rare.converters.StringConverter;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.spot.ItemDescription;
import com.appnativa.rare.ui.ScreenUtils.Unit;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.SNumber;
import com.appnativa.util.SimpleDateFormatEx;
import com.appnativa.util.iFilterableList;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public class Column extends RenderableDataItem {

  /**  */
  public static final int FOOTER = 2;

  /**  */
  public static final int FOOTER_INDEX = 4;

  /**  */
  public static final int FOOTER_INDEX_NORMAL = 8;

  /**  */
  public static final int FOOTER_NORMAL = 6;

  /**  */
  public static final int HEADER = 1;

  /**  */
  public static final int HEADER_INDEX = 3;

  /**  */
  public static final int HEADER_INDEX_NORMAL = 7;

  /**  */
  public static final int HEADER_NORMAL = 5;
  public static final int INDEX_NORMAL  = 9;

  /**  */
  public static final int    NORMAL                = 0;
  public static boolean      defaultHeaderWordWrap = true;
  protected final static int WIDTH_TYPE_MAX        = 2;
  protected final static int WIDTH_TYPE_MIN        = 1;
  protected final static int WIDTH_TYPE_NORMAL     = 0;

  /**  */
  public float maxWidth = 0;

  /**  */
  public float minWidth = 0;

  /**  */
  public float preferedWidth = 0;

  /**  */
  public boolean sortable = true;

  /**  */
  public boolean sizeFixed = false;
  public boolean showable  = true;

  /**  */
  public boolean moveable = true;
  public boolean hideable = true;

  /**  */
  public boolean wordWrap       = false;
  public boolean headerWordWrap = defaultHeaderWordWrap;

  /**  */
  public boolean            overrideSelectionBackground = false;
  public String             categoryName;
  public String             columnName;
  public Unit               widthUnit;
  int                       firstTable = 0;
  protected iActionListener headerActionListener;
  protected UIFont          headerFont;
  protected SPOTSet         menuSet;
  protected UIPopupMenu     popupMenu;
  private CharSequence      columnTitle           = "";
  private boolean           indexColumn           = false;
  private boolean           headerColumn          = false;
  private boolean           footerColumn          = false;
  private int               renderType            = NORMAL;
  private VerticalAlign     headerVerticalAlign   = VerticalAlign.AUTO;
  private IconPosition      headerIconPosition    = IconPosition.AUTO;
  private HorizontalAlign   headerHorizontalAlign = HorizontalAlign.AUTO;

  /**  */
  protected RenderDetail              renderDetail = RenderDetail.AUTO;
  private iPlatformRenderingComponent cellRenderer;
  private iPlatformRenderingComponent headerCellRenderer;
  private Class                       dataClass;
  private iPlatformIcon               headerIcon;
  private PaintBucket                 headerPainter;
  private PaintBucket                 headerRolloverPainter;
  private PaintBucket                 headerSelectionPainter;
  private PaintBucket                 itemPainter;
  private PaintBucket                 itemSelectionPainter;
  private Unit                        maxWidthUnit;
  private Unit                        minWidthUnit;
  protected float                     headerIconScaleFactor;
  protected boolean                   scaleHeaderIcon;
  

  @Override
  public float getIconScaleFactor() {
    return iconScaleFactor;
  }

  public boolean isScaleHeaderIcon() {
    return scaleIcon && (headerIconScaleFactor > 0);
  }

  public void setScaleHeaderIcon(boolean scale, float scaleFactor) {
    scaleHeaderIcon            = scale;
    this.headerIconScaleFactor = scaleFactor;
  }

  public float getHeaderIconScaleFactor() {
    return headerIconScaleFactor;
  }

  private iPlatformCellEditingComponent cellEditor;

  private UIInsets insets;

  /**
   * Render detail
   */
  public enum RenderDetail {
    AUTO, ALL, TEXT_AND_ICON, TEXT_ONLY, ICON_ONLY
  }

  /**
   * Constructs a new column
   */
  public Column() {
    super();
  }

  /**
   * Constructs a new column
   *
   * @param title
   *          the column's title
   */
  public Column(CharSequence title) {
    this(title, null, TYPE_STRING, null, null);
  }

  /**
   * Constructs a new column
   *
   * @param title
   *          the column's title
   * @param type
   *          the column type
   */
  public Column(CharSequence title, int type) {
    this(title, null, type, null, null);
  }

  /**
   * Constructs a new column
   *
   * @param title
   *          the column's title
   * @param type
   *          the column type
   * @param data
   *          linked data for the column
   */
  public Column(CharSequence title, int type, Object data) {
    this(title, null, type, data, null);
  }

  /**
   * Constructs a new column
   *
   * @param title
   *          the column's title
   * @param value
   *          the value for the column
   * @param type
   *          the column type
   * @param data
   *          linked data for the column
   * @param icon
   *          the column's icon
   */
  public Column(CharSequence title, Object value, int type, Object data, iPlatformIcon icon) {
    super(value, type, data, icon);
    setColumnTitle(title);
  }

  public int calculateMinimumWidth(iPlatformComponent c, float tableWidth) {
    if (minWidth > 0) {
      return ScreenUtils.toPlatformPixelWidth(minWidth, minWidthUnit, c, tableWidth, false);
    }

    if (!sizeFixed) {
      return 0;
    }

    int n = (preferedWidth == 0)
            ? 0
            : ScreenUtils.toPlatformPixelWidth(preferedWidth, widthUnit, c, tableWidth, false);

    if (maxWidth > 0) {
      n = Math.max(ScreenUtils.toPlatformPixelWidth(maxWidth, maxWidthUnit, c, tableWidth, false), n);
    }

    return n;
  }

  public int calculatePreferedWidth(iPlatformComponent c, float tableWidth) {
    int n = (preferedWidth == 0)
            ? 0
            : ScreenUtils.toPlatformPixelWidth(preferedWidth, widthUnit, c, tableWidth, false);

    if (minWidth > 0) {
      n = Math.min(ScreenUtils.toPlatformPixelWidth(minWidth, minWidthUnit, c, tableWidth, false), n);
    }

    if (maxWidth > 0) {
      n = Math.max(ScreenUtils.toPlatformPixelWidth(maxWidth, maxWidthUnit, c, tableWidth, false), n);
    }

    return n;
  }

  @Override
  public Object clone() {
    Column item = (Column) super.clone();

    item.firstTable = 0;

    iFilterableList<RenderableDataItem> subs = item.subItems;
    int                                 len  = (subs == null)
            ? 0
            : subs.size();

    if (len > 0) {
      iFilterableList<RenderableDataItem> tsubs = subItems;

      for (int i = 0; i < len; i++) {
        subs.add((RenderableDataItem) tsubs.get(i).clone());
      }
    }

    return item;
  }

  public void convert(iWidget w, RenderableDataItem di) {
    iDataConverter cvt = getDataConverter();

    if (cvt != null) {
      di.convert(w, getType(), cvt, getValueContext());
    }
  }

  @Override
  public boolean equals(Object o) {
    return o == this;
  }

  public Object convert(iWidget w, String value) {
    iDataConverter cvt = getDataConverter();
    Object         ctx = getValueContext();

    if (cvt == null) {
      return value;
    }

    if (ctx == null) {
      ctx = getValueContext();
    }

    return cvt.objectFromString(w, value, ctx);
  }

  public Object convert(iWidget w, RenderableDataItem di, String value) {
    iDataConverter cvt = di.getDataConverter();
    Object         ctx = di.getValueContext();

    if (cvt == null) {
      cvt = getDataConverter();
    }

    if (cvt == null) {
      return value;
    }

    if (ctx == null) {
      ctx = getValueContext();
    }

    return cvt.objectFromString(w, value, ctx);
  }

  public RenderableDataItem convertToItem() {
    setFont(this.headerFont);
    setValue(columnTitle);
    setVerticalAlignment(headerVerticalAlign);
    setHorizontalAlignment(headerHorizontalAlign);
    setIconPosition(headerIconPosition);

    return this;
  }

  public static int fromSPOTType(int type) {
    switch(type) {
      case ItemDescription.CValueType.string_type :
        type = TYPE_STRING;

        break;

      case ItemDescription.CValueType.date_type :
        type = TYPE_DATE;

        break;

      case ItemDescription.CValueType.date_time_type :
        type = TYPE_DATETIME;

        break;

      case ItemDescription.CValueType.time_type :
        type = TYPE_TIME;

        break;

      case ItemDescription.CValueType.array_type :
        type = TYPE_ARRAY;

        break;

      case ItemDescription.CValueType.struct_type :
        type = TYPE_STRUCT;

        break;

      case ItemDescription.CValueType.integer_type :
        type = TYPE_INTEGER;

        break;

      case ItemDescription.CValueType.decimal_type :
        type = TYPE_DECIMAL;

        break;

      case ItemDescription.CValueType.boolean_type :
        type = TYPE_BOOLEAN;

        break;

      case ItemDescription.CValueType.widget_type :
        type = TYPE_WIDGET;

        break;

      default :
        type = TYPE_UNKNOWN;

        break;
    }

    return type;
  }

  public void setupConverter(iWidget widget, String name, SPOTPrintableString e) throws Exception {
    String     context = null;
    Comparable min     = null,
               max     = null;

    if (e != null) {
      context = e.getValue();

      String s = e.spot_getAttribute("minimum");

      if ((s != null) && (s.length() > 0)) {
        min = s;
      }

      s = e.spot_getAttribute("maximum");

      if ((s != null) && (s.length() > 0)) {
        max = s;
      }
    }

    setupConverter(widget, name, context, min, max, true);
  }

  public void setupConverter(iWidget widget, String name, String context, Object min, Object max, boolean convertRange)
          throws Exception {
    Class cls = null;

    if (name != null) {
      cls = Platform.getDataConverterClass(name);

      if ((context == null) && (cls == StringConverter.class)) {
        context = StringConverter.EXPANDER_CONTEXT.getName();
      }
    }

    if (cls == null) {
      cls = getDefaultConverterClass(getType());

      if ((cls == null) && (getJavaClass() == UIColor.class)) {
        cls = ColorConverter.class;
      }
    }

    if (cls == null) {
      return;
    }

    dataConverter = Platform.getDataConverter(cls);

    if (dataClass != null) {
      dataConverter.setObjectClass(dataClass);
    }

    if (context != null) {
      setValueContext(dataConverter.createContext(widget, context));
    }

    if (convertRange) {
      if (min instanceof String) {
        min = dataConverter.objectFromString(widget, (String) min);
      }

      if (max instanceof String) {
        max = dataConverter.objectFromString(widget, (String) max);
      }
    }

    dataConverter.setMaxValue((Comparable) max);
    dataConverter.setMinValue((Comparable) min);

    if (cellRenderer == null) {
      cellRenderer = dataConverter.getRenderer(widget, valueContext);
    }
  }

  public final CharSequence toCharSequence(iWidget w, RenderableDataItem di) {
    return di.toCharSequence(w, dataConverter, valueContext);
  }

  public CharSequence toCharSequence(iWidget w, RenderableDataItem di, Object value) {
    return di.toCharSequence(w, value, dataConverter, valueContext);
  }

  @Override
  public String toString() {
    if (theValue == null) {
      CharSequence s = this.getColumnTitle();

      return (s == null)
             ? ""
             : s.toString();
    }

    return super.toString();
  }

  public void setCellRenderer(iPlatformRenderingComponent r) {
    cellRenderer = r;
  }

  public void setCellRenderer(String cls)
          throws InstantiationException, ClassNotFoundException, IllegalAccessException {
    Class c = Platform.loadClass(cls);

    cellRenderer = (iPlatformRenderingComponent) c.newInstance();
  }

  public void setCellEditor(iPlatformCellEditingComponent editor) {
    cellEditor = editor;
  }

  public iPlatformCellEditingComponent getCellEditor() {
    return cellEditor;
  }

  public void setColumnMaxWidth(String s) {
    setColumnWidth(s, WIDTH_TYPE_MAX);
  }

  public void setColumnMenu(SPOTSet menu) {
    menuSet = menu;

    if (menuSet != null) {
      menuSet.spot_setParent(null);    // break relationship to prevent memory leak
    }
  }

  public void setColumnMenu(UIPopupMenu menu) {
    popupMenu = menu;
    menuSet   = null;
  }

  public void setColumnMinWidth(String s) {
    setColumnWidth(s, WIDTH_TYPE_MIN);
  }

  public void setColumnTitle(CharSequence columnTitle) {
    this.columnTitle = (columnTitle == null)
                       ? ""
                       : columnTitle;
  }

  public void setColumnWidth(SPOTPrintableString width) {
    setColumnWidth(width.getValue(), WIDTH_TYPE_NORMAL);
    minWidth = 0;
    maxWidth = 0;

    String s = width.spot_getAttribute("min");

    if (s != null) {
      setColumnWidth(s, WIDTH_TYPE_MIN);
    }

    s = width.spot_getAttribute("max");

    if (s != null) {
      setColumnWidth(s, WIDTH_TYPE_MAX);
    }
  }

  public void setColumnWidth(String s) {
    setColumnWidth(s, WIDTH_TYPE_NORMAL);
  }

  public void setDateTimeFormat(String format) {
    setValueContext(new SimpleDateFormatEx(format));
  }

  public void setHeaderActionListener(iActionListener l) {
    headerActionListener = l;
  }

  public void setHeaderFont(UIFont headerFont) {
    this.headerFont = headerFont;
  }

  public void setHeaderHorizontalAlignment(HorizontalAlign align) {
    headerHorizontalAlign = align;
  }

  public void setHeaderIcon(iPlatformIcon headerIcon) {
    this.headerIcon = headerIcon;
  }

  public void setHeaderIconPosition(IconPosition position) {
    headerIconPosition = position;
  }

  public void setHeaderPainter(PaintBucket pb) {
    headerPainter = pb;
  }
  
  public void setMargin(int top, int right, int bottom, int left) {
    if(insets==null) {
      insets=new UIInsets(top, right, bottom, left);
    }
    else {
      insets.set(top, right, bottom, left);
    }
  }

  public void setMargin(UIInsets in) {
    insets=in;
  }
  
  public UIInsets getMargin() {
    return insets;
  }
  
  public void addMargin(UIInsets in) {
    if(insets!=null) {
      in.addInsets(insets);
    }
  }
  
  /**
   * Sets the rollover painter for the header
   *
   * @param pb
   *          the rollover painter for the header
   */
  public void setHeaderRollOverPainter(PaintBucket pb) {
    this.headerRolloverPainter = pb;
  }

  /**
   * Sets the selected painter for the header
   *
   * @param pb
   *          the selected painter for the header
   */
  public void setHeaderSelectionPainter(PaintBucket pb) {
    this.headerSelectionPainter = pb;
  }

  public void setHeaderVerticalAlignment(VerticalAlign align) {
    headerVerticalAlign = align;
  }

  /**
   * @param itemPainter
   *          the itemPainter to set
   */
  public void setItemPainter(PaintBucket itemPainter) {
    this.itemPainter = itemPainter;
  }

  public void setItemSelectionPainter(PaintBucket pb) {
    itemSelectionPainter = pb;
  }

  public void setJavaClass(Class cls) {
    dataClass = cls;
  }

  public void setNumberFormat(String format) {
    if ((format == null) || (format.length() == 0)) {
      return;
    }

    NumberFormat nf = NumberFormat.getInstance();

    if (!format.equals("*")) {
      if (nf instanceof DecimalFormat) {
        ((DecimalFormat) nf).applyPattern(format);
      }
    }

    setValueContext(nf);
  }

  public void setRenderDetail(RenderDetail renderDetail) {
    this.renderDetail = renderDetail;
  }

  public void setRenderType(int renderType) {
    this.renderType = renderType;

    if (renderType == INDEX_NORMAL) {
      this.renderType = NORMAL;
      indexColumn     = true;

      return;
    }

    headerColumn = false;
    indexColumn  = false;
    footerColumn = false;

    switch(renderType) {
      case HEADER_INDEX_NORMAL :
      case HEADER_INDEX:
      case HEADER_NORMAL :
      case HEADER :
        headerColumn = true;

        break;

      case FOOTER_INDEX_NORMAL :
      case FOOTER_INDEX:
      case FOOTER_NORMAL :
      case FOOTER :
        footerColumn = true;

        break;

      default :
        break;
    }

    switch(renderType) {
      case HEADER_INDEX:
      case HEADER_INDEX_NORMAL :
      case FOOTER_INDEX:
      case FOOTER_INDEX_NORMAL :
        indexColumn = true;

        break;

      default :
        break;
    }

    switch(renderType) {
      case HEADER :
      case HEADER_INDEX:
      case FOOTER :
      case FOOTER_INDEX:
        itemPainter = ColorUtils.getBackgroundBucket();

        break;

      default :
        break;
    }
  }

  public iPlatformRenderingComponent getCellRenderer() {
    return cellRenderer;
  }

  public CharSequence getColumnTitle() {
    return columnTitle;
  }

  public iActionListener getHeaderActionListener() {
    return headerActionListener;
  }

  public iPlatformComponentPainter getHeaderComponentPainter() {
    if ((headerSelectionPainter != null) || (headerRolloverPainter != null)) {
      PainterHolder      ph = new PainterHolder(headerPainter, headerSelectionPainter, headerRolloverPainter, null,
                                null);
      UIComponentPainter cp = new UIComponentPainter();

      cp.setPainterHolder(ph);

      return cp;
    }

    return (headerPainter == null)
           ? null
           : headerPainter.getCachedComponentPainter();
  }

  public UIFont getHeaderFont() {
    return headerFont;
  }

  public HorizontalAlign getHeaderHorizontalAlignment() {
    return headerHorizontalAlign;
  }

  public iPlatformIcon getHeaderIcon() {
    return headerIcon;
  }

  public IconPosition getHeaderIconPosition() {
    return headerIconPosition;
  }

  public PaintBucket getHeaderPainter() {
    return headerPainter;
  }

  /**
   * Gets the rollover painter for the header
   *
   * @return the rollover painter for the header
   */
  public PaintBucket getHeaderRollOverPainter() {
    return headerRolloverPainter;
  }

  /**
   * Gets the selected painter for the header
   *
   * @return the selected painter for the header
   */
  public PaintBucket getHeaderSelectionPainter() {
    return headerSelectionPainter;
  }

  public VerticalAlign getHeaderVerticalAlignment() {
    return (headerVerticalAlign == VerticalAlign.AUTO)
           ? VerticalAlign.CENTER
           : headerVerticalAlign;
  }

  /**
   * @return the itemPainter
   */
  public PaintBucket getItemPainter() {
    return itemPainter;
  }

  public PaintBucket getItemPainter(boolean selected) {
    return selected
           ? ((itemSelectionPainter == null)
              ? itemPainter
              : itemSelectionPainter)
           : itemPainter;
  }

  public PaintBucket getItemSelectionPainter() {
    return itemSelectionPainter;
  }

  public UIPopupMenu getPopupMenu(iWidget context) {
    if (popupMenu != null) {
      popupMenu.setContextWidget(context);

      return popupMenu;
    }

    if ((menuSet != null)) {
      UIPopupMenu menu = new UIPopupMenu(context, menuSet);

      menuSet   = null;
      popupMenu = menu;

      return menu;
    }

    return null;
  }

  public RenderDetail getRenderDetail() {
    return renderDetail;
  }

  public int getRenderType() {
    return renderType;
  }

  public boolean hasPopupMenu() {
    return (menuSet != null) || (popupMenu != null);
  }

  public boolean isFooterColumn() {
    return footerColumn;
  }

  public boolean isHeaderColumn() {
    return headerColumn;
  }

  public boolean isHeaderOrFooterColumn() {
    return headerColumn || footerColumn;
  }

  public boolean isIndexColumn() {
    return indexColumn;
  }

  public boolean isRenderNormal() {
    if ((renderType == Column.NORMAL) || (renderType == Column.HEADER_NORMAL)
        || (renderType == Column.HEADER_INDEX_NORMAL) || (renderType == Column.FOOTER_NORMAL)
        || (renderType == Column.FOOTER_INDEX_NORMAL)) {
      return true;
    }

    return false;
  }

  @Override
  protected void finalize() throws Throwable {
    if (cellRenderer != null) {
      try {
        cellRenderer.dispose();
      } catch(Throwable ignore) {}
    }

    if (popupMenu != null) {
      try {
        popupMenu.dispose();
      } catch(Throwable ignore) {}
    }

    super.finalize();
  }

  protected void setColumnWidth(String s, int type) {
    Unit    unit  = null;
    float   width = 0;
    boolean fixed = false;

    if ((s != null) && (s.length() > 0)) {
      if (s.endsWith("!")) {
        unit  = ScreenUtils.getUnit(s, s.length() - 1);
        width = SNumber.floatValue(s);
        fixed = true;
      } else {
        unit  = ScreenUtils.getUnit(s, s.length());
        width = SNumber.floatValue(s);
      }

      if (width < 0) {
        width = 0;
      }
    }

    switch(type) {
      case WIDTH_TYPE_MIN :
        minWidthUnit = unit;
        minWidth     = width;

        break;

      case WIDTH_TYPE_MAX :
        maxWidthUnit = unit;
        maxWidth     = width;

        break;

      default :
        widthUnit     = unit;
        preferedWidth = width;
        sizeFixed     = fixed;

        break;
    }
  }

  public iPlatformRenderingComponent getHeaderCellRenderer() {
    return headerCellRenderer;
  }

  public void setHeaderCellRenderer(iPlatformRenderingComponent headerCellRenderer) {
    this.headerCellRenderer = headerCellRenderer;
  }
}
