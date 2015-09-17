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

package com.appnativa.rare.widget;

import com.appnativa.rare.Platform;
import com.appnativa.rare.TemplateHandler;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iDataItemParserCallback;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iWidgetCustomizer;
import com.appnativa.rare.iWorkerTask;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.CollectionURLConnection;
import com.appnativa.rare.net.FormHelper;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.URLEncoder;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.scripting.WidgetContext;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.spot.ItemDescription;
import com.appnativa.rare.spot.Link;
import com.appnativa.rare.spot.Margin;
import com.appnativa.rare.spot.Rectangle;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageHelper;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIPopupMenu;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.WidgetListener;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.dnd.DragEvent;
import com.appnativa.rare.ui.dnd.DropInformation;
import com.appnativa.rare.ui.dnd.TransferFlavor;
import com.appnativa.rare.ui.dnd.iTransferable;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.ui.event.FocusEvent;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.ScriptActionListener;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iChangeable;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.iTabDocument;
import com.appnativa.rare.ui.iTreeHandler;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.util.DataItemCSVParser;
import com.appnativa.rare.util.DataItemParserHandler;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.util.JSONHelper;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iFormViewer;
import com.appnativa.rare.viewer.iTabPaneViewer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.CharScanner;
import com.appnativa.util.FilterableList;
import com.appnativa.util.Helper;
import com.appnativa.util.ObjectHolder;
import com.appnativa.util.SNumber;
import com.appnativa.util.iCancelable;
import com.appnativa.util.iURLResolver;

import com.google.j2objc.annotations.Weak;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Base class for all widgets
 *
 * @author Don DeCoteau
 */
@SuppressWarnings("unchecked")
public abstract class aWidget extends RenderableDataItem
        implements iWidget, iActionListener, iDataItemParserCallback, iURLResolver, iImageObserver {
  // Per-thread scanner
  protected static ThreadLocal<CharScanner> perThreadScanner = new ThreadLocal<CharScanner>() {
    @Override
    protected synchronized CharScanner initialValue() {
      return new CharScanner();
    }
  };
  // Per-thread scanner
  protected static ThreadLocal<DataItemCSVParser> perThreadParser = new ThreadLocal<DataItemCSVParser>() {
    @Override
    protected synchronized DataItemCSVParser initialValue() {
      return new DataItemCSVParser();
    }
  };

  /** the number of columns the widget's supports */
  protected int columnCount = 0;

  /** whether copying is allowed */
  protected boolean copyingAllowed = true;

  /** whether the form is submit-able */
  protected boolean isSubmittable = true;

  /** the type of widget */
  protected WidgetType widgetType = WidgetType.Bean;

  /** whether the container is allowed to disable this widget */
  protected boolean allowContainerToDisable = true;

  /** the link this is currently loading */
  protected ActionLink activeLink;

  /** the component the manages the widget's data */
  protected iPlatformComponent dataComponent;

  /** whether deleting is allowed */
  protected boolean deletingAllowed;

  /** whether the widget is disposed */
  protected boolean disposed;

  /** the drop mode for DnD support */
  protected int dropMode;

  /** whether dropping is allowed */
  protected boolean droppingAllowed;

  /** whether the item must explicitly import a flavor in order to be dropped on */
  protected boolean explicitItemImport;

  /** whether file dropping is supported */
  protected boolean fileDroppingAllowed;

  /** functions associated with data flavors */
  protected HashMap<String, Object> flavorFunctions;

  /** the component the gets added to forms; */
  protected iPlatformComponent formComponent;

  /** whether an exception is currently be handled by this widget */
  protected boolean inExceptionHandler;

  /** holds items that have special attributes that can be set as properties */
  protected ItemAttributes itemAttributes;
  protected SPOTSet        menuSet;
  @Weak()
  protected iContainer     parentContainer;

  /** whether pasting is allowed */
  protected boolean     pastingAllowed;
  protected iPopup      popupContainer;
  protected UIPopupMenu popupMenu;

  /** the size of the widget's prompt label */
  protected int           promptWidth;
  protected boolean       required;
  protected WidgetContext scriptingContex;

  /** whether select all is allowed for the widget */
  protected boolean selectAllAllowed;

  /** whether a default popup menu should be shown for the widget */
  protected boolean showDefaultMenu;

  /** the source URL for the widget's data */
  protected URL sourceURL;

  /** the label for the widget's prompt */
  protected iActionComponent titleLabel;

  /** whether url dropping is supported */
  protected boolean urlDroppingAllowed;

  /** link that the data was loaded from */
  protected ActionLink widgetDataLink;

  /** the listener for widget event's */
  protected aWidgetListener widgetListener;

  /** the widget's name */
  protected String widgetName;

  /** the widget's title (or prompt) */
  protected String widgetTitle;

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   * @param fcomp
   *          the form component
   * @param dcomp
   *          the data component
   */
  public aWidget(iContainer parent, iPlatformComponent fcomp, iPlatformComponent dcomp) {
    this(parent);
    formComponent = fcomp;
    dataComponent = dcomp;
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  protected aWidget(iContainer parent) {
    parentContainer = parent;
    setParentOnAdd  = false;
  }

  /**
   * Invoked automatically when the links underlying action is triggered. This
   * will cause the link to be activated.
   *
   * @param e
   *          the event that triggered the action
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (actionListener != null) {
      actionListener.actionPerformed(e);
    }

    if ((getWidgetListener() != null) && getWidgetListener().isActionEventEnabled()) {
      getWidgetListener().actionPerformed(e);
    }
  }

  @Override
  public void addData(Map map) {
    if (map == null) {
      setCustomProperties(null);
    } else {
      if (getCustomProperties() == null) {
        setCustomProperties(new HashMap(map));
      } else {
        getCustomProperties().putAll(map);
      }
    }
  }

  /**
   * Adds a event listener for the widget
   *
   * @param event
   *          the event name HTML event names are supported
   * @param code
   *          the code
   * @param bubbling
   *          the parameter is for HTML compatibility only (it is ignored)
   */
  public void addEventListener(String event, Object code, boolean bubbling) {
    this.setEventHandler(event, code, true);
  }

  /**
   * Convenience method for adding an arbitrary item to the list
   *
   * @param item
   *          the item to add
   */
  public void addObject(Object item) {
    add(createItem(item));
  }

  /**
   * Convenience method for inserting an arbitrary item to the list
   *
   * @param index
   *          the index where the item is to be inserted
   * @param item
   *          the item to insert
   */
  public void addObject(int index, Object item) {
    add(index, createItem(item));
  }

  /**
   * Convenience method for adding an arbitrary set of items item to the list
   *
   * @param list
   *          the items to add
   */
  public void addObjects(List list) {
    if (list != null) {
      Iterator it = list.iterator();

      while(it.hasNext()) {
        addObject(it.next());
      }
    }
  }

  /**
   * Convenience method for adding an arbitrary set of items item to the list
   *
   * @param items
   *          the items to add
   */
  public void addObjects(Object[] items) {
    addObjects(items, 0, (items == null)
                         ? 0
                         : items.length);
  }

  /**
   * Convenience method for adding an arbitrary set of items item to the list
   *
   * @param index
   *          the index where the items are to be inserted
   * @param list
   *          the items to add
   */
  public void addObjects(int index, List list) {
    if (list != null) {
      Iterator it = list.iterator();

      while(it.hasNext()) {
        addObject(index++, it.next());
      }
    }
  }

  /**
   * Convenience method for adding an arbitrary set of items item to the list
   *
   * @param items
   *          the items to add
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of items to add
   */
  public void addObjects(Object[] items, int pos, int len) {
    len += pos;

    while(pos < len) {
      addObject(items[pos++]);
    }
  }

  @Override
  public void addParsedRow(RenderableDataItem row) {
    add(row);
  }

  /**
   * Adds a set of name/value pairs to the widget's data map
   *
   * @param options
   *          a string containing a list of name value pairs. The name and value
   *          are separated by and equals sign (=)
   * @param token
   *          the token that separates each pair
   *
   * @throws ParseException
   *           if a format error occurs
   */
  public void addToDataMap(String options, char token) throws ParseException {
    Map map = getCustomProperties();

    if (map == null) {
      map = new HashMap();
      setCustomProperties(map);
    }

    CharScanner.parseOptionString(options, map, token, true);
  }

  @Override
  public void animate(String animation, iFunctionCallback cb) {
    final iPlatformAnimator pa = Platform.getWindowViewer().createAnimator(animation);

    if (pa != null) {
      pa.addListener(new iAnimatorListener() {
        @Override
        public void animationStarted(iPlatformAnimator source) {}
        @Override
        public void animationEnded(iPlatformAnimator source) {
          Platform.invokeLater(new Runnable() {
            @Override
            public void run() {
              pa.dispose();
            }
          });
        }
      });
      animate(pa, cb);
    } else {
      Platform.debugLog("Unknown annimation:" + animation);
    }
  }

  @Override
  public void animate(iPlatformAnimator animator, iFunctionCallback cb) {
    animator.animate(getContainerComponent(), cb);
  }

  /**
   * Adds a event listener for the widget
   *
   * @param event
   *          the event name HTML event names are supported
   * @param code
   *          the code
   */
  public void attachEvent(String event, Object code) {
    this.setEventHandler(event, code, true);
  }

  /**
   * Performs Haptic Feedback on capable devices
   */
  public abstract void buzz();

  @Override
  public boolean canCopy() {
    return copyingAllowed;
  }

  @Override
  public boolean canCut() {
    return copyingAllowed && deletingAllowed;
  }

  @Override
  public boolean canDelete() {
    return this.deletingAllowed;
  }

  @Override
  public boolean canDrag(DragEvent ge) {
    if (!draggingAllowed) {
      return false;
    }

    Object o = getSelection();

    if (o instanceof RenderableDataItem) {
      if (!((RenderableDataItem) o).isDraggingAllowed()) {
        return false;
      }
    }

    aWidgetListener wl = this.getWidgetListener();

    if ((wl != null) && wl.isEnabled(iConstants.EVENT_DRAGSTART)) {
      DataEvent e = new DataEvent(this, ge);

      try {
        wl.evaluate(iConstants.EVENT_DRAGSTART, e, false);

        return !e.isConsumed();
      } catch(Exception ex) {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean canDrop() {
    return droppingAllowed;
  }

  @Override
  public boolean canExport(TransferFlavor flavor) {
    String[] eflavors = null;
    Object   o        = getSelection();

    if (o != null) {
      if (o instanceof RenderableDataItem) {
        if (!((RenderableDataItem) o).isDraggingAllowed()) {
          return false;
        }

        eflavors = ((RenderableDataItem) o).getExportableDataFlavors();
      }
    }

    if (eflavors == null) {
      eflavors = exportDataFlavors;
    }

    if ((flavor == null) || (eflavors == null)) {
      return false;
    }

    return TransferFlavor.isDataFlavorSupported(eflavors, flavor);
  }

  @Override
  public boolean canImport(TransferFlavor[] flavors, DropInformation drop) {
    if (!droppingAllowed &&!pastingAllowed) {
      return false;
    }

    Object   o        = getDropTarget(drop);
    String[] iflavors = null;

    if (o instanceof RenderableDataItem) {
      iflavors = ((RenderableDataItem) o).getImportableDataFlavors();

      if ((iflavors == null) && (!explicitItemImport || drop.isInsertMode())) {
        iflavors = importDataFlavors;
      }
    } else {
      iflavors = importDataFlavors;
    }

    if ((flavors == null) || (iflavors == null)) {
      return false;
    }

    int len = flavors.length;

    for (int i = 0; i < len; i++) {
      if (TransferFlavor.isDataFlavorSupported(iflavors, flavors[i])) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean canMove() {
    return copyingAllowed && deletingAllowed;
  }

  @Override
  public boolean canPaste() {
    return this.pastingAllowed;
  }

  @Override
  public boolean canRedo() {
    return false;
  }

  @Override
  public boolean canSelectAll() {
    return selectAllAllowed;
  }

  @Override
  public boolean canUndo() {
    return false;
  }

  @Override
  public boolean canUndoOrRedo() {
    return false;
  }

  /**
   * Clears the contents of the widget
   */
  @Override
  public void clear() {
    clearContents();
  }

  @Override
  public void clearContents() {
    theValue = null;
  }

  @Override
  public void clearContextData() {
    if (getCustomProperties() != null) {
      getCustomProperties().clear();
    }
  }

  @Override
  public abstract void configure(Widget cfg);

  /**
   * Converts the specified item using this widgets data type converters
   *
   * @param item
   *          the item to convert
   */
  public void convert(RenderableDataItem item) {}

  /**
   * Creates a column from an item description
   *
   * @param id
   *          the item description
   * @return the newly created column
   */
  public Column createColumn(ItemDescription id) {
    String        s;
    iPlatformIcon icon = id.icon.spot_hasValue()
                         ? getIcon(id.icon)
                         : null;
    int           type = Column.fromSPOTType(id.valueType.intValue());

    s = id.title.getValue();

    if ((s == null) && (icon == null)) {
      s = "";
    }

    s = expandString(s, false);

    Column di = createColumn(s, id.value.getValue(), type, id.linkedData.getValue(), icon);

    s = id.spot_getAttribute("onAction");

    if ((s != null) && (s.length() > 0)) {
      di.setHeaderActionListener(new ScriptActionListener(this, di, s));
    }

    if (id.editable.spot_hasValue()) {
      di.setEditable(id.editable.booleanValue());
    }

    switch(id.renderType.intValue()) {
      case ItemDescription.CRenderType.header :
        di.setRenderType(Column.HEADER);

        break;

      case ItemDescription.CRenderType.header_index :
        di.setRenderType(Column.HEADER_INDEX);

        break;

      case ItemDescription.CRenderType.normal_index :
        di.setRenderType(Column.INDEX_NORMAL);

        break;

      case ItemDescription.CRenderType.footer :
        di.setRenderType(Column.FOOTER);

        break;

      case ItemDescription.CRenderType.footer_index :
        di.setRenderType(Column.FOOTER_INDEX);

        break;

      case ItemDescription.CRenderType.header_normal :
        di.setRenderType(Column.HEADER_NORMAL);

        break;

      case ItemDescription.CRenderType.footer_normal :
        di.setRenderType(Column.FOOTER_NORMAL);

        break;

      case ItemDescription.CRenderType.header_index_normal :
        di.setRenderType(Column.HEADER_INDEX_NORMAL);

        break;

      case ItemDescription.CRenderType.footer_index_normal :
        di.setRenderType(Column.FOOTER_INDEX_NORMAL);

        break;

      default :
        di.setRenderType(Column.NORMAL);

        break;
    }

    di.sortable       = id.sortable.booleanValue();
    di.hideable       = id.hideable.booleanValue();
    di.showable       = id.showable.booleanValue();
    di.wordWrap       = id.wordWrap.booleanValue();
    di.headerWordWrap = id.headerWordWrap.booleanValue();
    di.moveable       = id.moveable.booleanValue();

    if (!di.showable ||!id.visible.booleanValue()) {
      di.setVisible(false);
    }

    try {
      s = id.customTypeClass.getValue();

      if (s != null) {
        di.setJavaClass(Platform.loadClass(s));
      }

      s = id.rendererClass.getValue();

      if (s != null) {
        iPlatformRenderingComponent rc = PlatformHelper.createRenderer(s, this);

        s = id.rendererClass.spot_getAttribute("options");

        if (s != null) {
          rc.setOptions(CharScanner.parseOptionString(s, ','));
        }

        di.setCellRenderer(rc);
      }

      di.setupConverter(this, id.converterClass.getValue(), id.valueContext);
      s = id.linkedDataconverterClass.getValue();

      if ((s != null) && (s.length() > 0)) {
        iDataConverter cvt = Platform.getDataConverter(Platform.getDataConverterClass(s));

        if (cvt != null) {
          di.setLinkedDataConverter(cvt);
          s = id.linkedDataContext.getValue();

          if (s != null) {
            di.setLinkedDataContext(cvt.createContext(this, s));
          }
        }
      }
    } catch(Exception ex) {
      handleException(ex);
    }

    s = id.description.getValue();

    if ((s != null) && (s.length() > 0)) {
      di.setTooltip(s);
    }

    if (id.scaleIcon.booleanValue()) {
      float f = SNumber.floatValue(id.scaleIcon.spot_getAttribute("percent"));

      if (f > 1) {
        f = f / 100;
      }

      if (f <= 0) {
        f = 1;
      }

      di.setScaleIcon(true, f);
    }

    if (id.headerScaleIcon.booleanValue()) {
      float f = SNumber.floatValue(id.headerScaleIcon.spot_getAttribute("percent"));

      if (f > 1) {
        f = f / 100;
      }

      if (f <= 0) {
        f = 1;
      }

      di.setScaleHeaderIcon(true, f);
    }

    switch(id.renderDetail.intValue()) {
      case ItemDescription.CRenderDetail.icon_only :
        di.setRenderDetail(Column.RenderDetail.ICON_ONLY);

        break;

      case ItemDescription.CRenderDetail.text_only :
        di.setRenderDetail(Column.RenderDetail.TEXT_ONLY);

        break;

      default :
        break;
    }

    switch(id.orientation.intValue()) {
      case ItemDescription.COrientation.horizontal :
        di.setOrientation(RenderableDataItem.Orientation.HORIZONTAL);

        break;

      case ItemDescription.COrientation.vertical_up :
        di.setOrientation(RenderableDataItem.Orientation.VERTICAL_DOWN);

        break;

      case ItemDescription.COrientation.vertical_down :
        di.setOrientation(RenderableDataItem.Orientation.VERTICAL_DOWN);

        break;
    }

    if (id.headerIcon.spot_hasValue()) {
      di.setHeaderIcon(getIcon(id.headerIcon));
    }

    di.columnName   = id.name.getValue();
    di.categoryName = id.category.getValue();
    di.setColumnWidth(id.width);
    s = id.customProperties.getValue();

    if ((s != null) && (s.length() > 0)) {
      di.setCustomProperties(DataParser.parseNameValuePairs(id.customProperties));
    }

    di.setColumnMenu(id.getPopupMenu());
    di.overrideSelectionBackground = id.overrideSelectionBackground.booleanValue();
    di.setHorizontalAlignment(id.getHorizontalAlignment());
    di.setVerticalAlignment(id.getVerticalAlignment());
    di.setIconPosition(id.getIconPosition());
    di.setHeaderHorizontalAlignment(id.getHeaderHorizontalAlignment());
    di.setHeaderVerticalAlignment(id.getHeaderVerticalAlignment());
    di.setHeaderIconPosition(id.getHeaderIconPosition());
    di.setCursorName(id.cursorName.getValue());
    s = id.cursorName.spot_getAttribute("cursorDisplayType");

    if ((s != null) && (s.length() > 0)) {
      di.setCursorDisplayType(CursorDisplayType.valueOf(s.toUpperCase(Locale.US)));
    }

    if ((di.getType() == Column.TYPE_INTEGER) || (di.getType() == Column.TYPE_DECIMAL)) {
      if (di.getHorizontalAlignment() == HorizontalAlign.AUTO) {
        di.setHorizontalAlignment(HorizontalAlign.TRAILING);

        if (di.getHeaderHorizontalAlignment() == HorizontalAlign.AUTO) {
          di.setHeaderHorizontalAlignment(HorizontalAlign.TRAILING);
        }
      }
    }

    GridCell cell = id.getHeaderCell();

    s = id.headerColor.getValue();

    if ((cell != null) || (s != null)) {
      PaintBucket pb = new PaintBucket();

      di.setHeaderPainter(pb);

      if (cell != null) {
        UIColorHelper.configure(this, cell, pb);
      }

      if (s != null) {
        pb.setForegroundColor(getColor(s));
      }
    }

    s = id.fgColor.getValue();

    if ((s != null) && (s.length() > 0)) {
      di.setForeground(getColor(s));
    }

    UIFont f = getFont(id.font);

    if (f != null) {
      di.setFont(f);
    }

    f = getFont(id.headerFont);

    if (f != null) {
      di.setHeaderFont(f);
    }

    Link l = id.getActionLink();

    if (l != null) {
      di.setActionListener(new ActionLink(this, l));
    }

    cell = id.getSelectionGridCell();

    if (cell != null) {
      di.setItemSelectionPainter(UIColorHelper.configure(this, cell, null));
    }

    cell = id.getGridCell();

    if (cell != null) {
      di.setItemPainter(UIColorHelper.configure(this, cell, null));
    }

    cell = id.getHeaderRolloverCell();

    if (cell != null) {
      di.setHeaderRollOverPainter(UIColorHelper.configure(this, cell, null));
    }

    cell = id.getHeaderSelectionCell();

    if (cell != null) {
      di.setHeaderSelectionPainter(UIColorHelper.configure(this, cell, null));
    }

    SPOTSet set = DataParser.resolveSet(this, id.getSubItems(), ItemDescription.class);
    int     len = (set == null)
                  ? 0
                  : set.size();

    for (int i = 0; i < len; i++) {
      di.add(createColumn((ItemDescription) set.get(i)));
    }

    return di;
  }

  /**
   * Creates a new column
   *
   * @param title
   *          the column's title
   * @return the newly created column
   */
  public Column createColumn(String title) {
    return new Column(title);
  }

  /**
   * Creates a new column
   *
   * @param title
   *          the column's title
   * @param value
   *          the column's value
   * @param type
   *          the column's type
   * @param data
   *          the linked data
   * @param icon
   *          a reference to the icon for the column
   *
   * @return the newly created column
   */
  public Column createColumn(String title, Object value, int type, Object data, iPlatformIcon icon) {
    return new Column(title, value, type, data, icon);
  }

  /**
   * Creates a renderable data item for the specified value
   *
   * @param value
   *          the value to create the item for
   *
   * @return the newly created item
   */
  @Override
  public RenderableDataItem createItem(Object value) {
    RenderableDataItem item;

    if (value instanceof String) {
      Object o = convert((String) value);

      if (o instanceof RenderableDataItem) {
        item = (RenderableDataItem) o;
      } else {
        item = createItem(o, null, null);

        if (o == value) {
          item.setConverted(false);
        }
      }
    } else if (value instanceof RenderableDataItem) {
      item = (RenderableDataItem) value;
    } else if (value instanceof DataItem) {
      item = populateItem(this, (DataItem) value, null);
    } else if (value instanceof ItemDescription) {
      Column c = createColumn((ItemDescription) value);

      item = c.convertToItem();
    } else {
      item = createItem(value, null, null);
    }

    return item;
  }

  /**
   * Creates a new UNKNOWN data type renderable data item
   *
   * @param value
   *          the item's value
   * @param data
   *          the item's linked data
   * @param icon
   *          the item's display icon reference
   *
   * @return the newly created item
   */
  public RenderableDataItem createItem(Object value, Object data, Object icon) {
    int type = RenderableDataItem.TYPE_UNKNOWN;

    if ((value == null) || (value instanceof String)) {
      type = RenderableDataItem.TYPE_STRING;
    }

    return createItem(value, type, data, icon, null);
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
          ? getColor((String) color)
          : (UIColor) color;
    }

    return new RenderableDataItem(value, type, data, ic, c);
  }

  /**
   * Creates an array for holding items
   *
   * @param size
   *          the size of the array
   *
   * @return an empty array
   */
  public RenderableDataItem[] createItemArray(int size) {
    return new RenderableDataItem[size];
  }

  @Override
  public RenderableDataItem createItemEx(DataItem item) {
    return populateItem(this, item, null);
  }

  /**
   * Creates a list for holding items
   *
   * @param capacity
   *          the initial capacity for the list
   *
   * @return an empty list
   */
  public List<RenderableDataItem> createItemList(int capacity) {
    if (capacity > 0) {
      return new FilterableList<RenderableDataItem>(capacity);
    } else {
      return new FilterableList<RenderableDataItem>();
    }
  }

  /**
   * Creates a map from the specified option string
   *
   * @param options
   *          a string containing a list of name/value pairs. The name and value
   *          are separated by and equals sign (=)
   * @param token
   *          the token that separates each pair
   *
   * @return a map containing the name/value pairs
   *
   * @throws ParseException
   *           if a format error occurs
   */
  public Map createOptionMap(String options, char token) throws ParseException {
    HashMap map = new HashMap();

    CharScanner.parseOptionString(options, map, token, false);

    return map;
  }

  /**
   * Creates a new renderable data item that is configured to hold the specified
   * number of sub-items
   *
   * @param capacity
   *          the initial capacity
   * @param populate
   *          whether the sub-items array should be populated
   * @return the newly created row item
   */
  public RenderableDataItem createRow(int capacity, boolean populate) {
    RenderableDataItem row = new RenderableDataItem();

    row.ensureCapacity(capacity);

    if (populate) {
      for (int i = 0; i < capacity; i++) {
        RenderableDataItem di = createItem((Object) null);

        row.add(di);
      }
    }

    return row;
  }

  @Override
  public void dataExported(iTransferable transferable, DropInformation drop) {
    aWidgetListener wl = getWidgetListener();

    if ((wl != null) && wl.isEnabled(iConstants.EVENT_DROPEND)) {
      DataEvent e = new DataEvent(this, drop);

      wl.evaluate(iConstants.EVENT_DROPEND, e, false);
    } else {
      dataExportedEx(transferable, drop);
    }
  }

  /**
   * Called when data has been exported from the widget via and Drag and Drop
   * operation. The default action is to deleted the selected data if the drop
   * action was a move operation.
   *
   * @param transferable
   *          the transferable
   * @param drop
   *          the drop information
   */
  public void dataExportedEx(iTransferable transferable, DropInformation drop) {
    iWidget w = drop.getTargetWidget();

    if ((w != this) && drop.isMoveAction() && deletingAllowed) {
      removeSelectedData(false);
    }
  }

  /**
   * Deletes the currently selected data from the widget
   *
   * @param returnData
   *          true to return the deleted data; false otherwise
   *
   * @return the deleted data or null of no data was selected or the
   *         <b>returnData</b> flag was false
   */
  public Object removeSelectedData(boolean returnData) {
    return null;
  }

  @Override
  public void dispatchKeyEvent(KeyEvent e) {
    getDataComponent().dispatchEvent(e);
  }

  @Override
  public void dispatchMouseEvent(MouseEvent e) {
    getDataComponent().dispatchEvent(e);
  }

  @Override
  public void dispose() {
    if (isDisposed()) {
      return;
    }

    // System.out.println("Disposing "+getName());
    super.dispose();
    disposed = true;

    ActionLink l = activeLink;

    if ((l != null) &&!Platform.isShuttingDown()) {
      activeLink = null;

      try {
        l.close();
      } catch(Throwable ignore) {}
    }

    try {
      TemplateHandler.disposing(this);

      iWidgetCustomizer customizer = Platform.getAppContext().getWidgetCustomizer();

      if (customizer != null) {
        try {
          customizer.widgetDisposed(this);
        } catch(Exception e) {
          Platform.ignoreException(null, e);
        }
      }

      if (scriptingContex != null) {
        scriptingContex.dispose();
        scriptingContex = null;
      }

      iFormViewer fv = getFormViewer();

      if (fv != null) {
        fv.unregisterFormWidget(this);
      }

      if (getDataComponent() != null) {
        unregisterWithWidget(getDataComponent());
        unregisterWithWidget(getContainerComponent());
        uninitializeListeners(getWidgetListener());
      } else {
        Platform.debugLog("null dataComponent during dispose");
      }

      if (widgetListener != null) {
        widgetListener.dispose();
        widgetListener = null;
      }

      if (flavorFunctions != null) {
        flavorFunctions.clear();
      }

      if (widgetDataLink != null) {
        widgetDataLink.clear();
      }

      if (getDataComponent() != null) {
        getDataComponent().dispose();
      }

      if (getContainerComponent() != null) {
        getContainerComponent().dispose();
      }

      if (popupMenu != null) {
        popupMenu.dispose();
      }

      if (popupContainer != null) {
        popupContainer.dispose();
      }
    } catch(Throwable e) {
      Platform.ignoreException("dispose exception", e);
    }

    dataComponent     = null;
    formComponent     = null;
    parentContainer   = null;
    popupContainer    = null;
    popupMenu         = null;
    actionListener    = null;
    widgetDataLink    = null;
    widgetTitle       = null;
    sourceURL         = null;
    scriptingContex   = null;
    exportDataFlavors = null;
    importDataFlavors = null;
    flavorFunctions   = null;
    linkedData        = null;
    menuSet           = null;
  }

  /**
   * Indicates whether some other widget is "equal to" this one. The equality
   * test is an identity test (if o==this has the value true)
   *
   * @param o
   *          the object to test against
   *
   * @return true if this object is the same as the object argument; false
   *         otherwise.
   */
  @Override
  public boolean equals(Object o) {
    return o == this;
  }

  public Object evaluateCode(Object code) {
    return aWidgetListener.evaluate(this, getScriptHandler(), code, "evaluateCode", null);
  }

  public void executeCode(Object code) {
    aWidgetListener.execute(this, getScriptHandler(), code, "executeCode", null);
  }

  @Override
  public void executeEvent(String eventName, EventObject event) {
    fireEventEx(widgetListener, eventName, event, true);
  }

  /**
   * Expands the specified string resolving any embedded variables or functions.
   *
   * @param value
   *          the value to expand
   * @return the expanded string
   */
  public String expandString(String value) {
    return expandString(value, false);
  }

  @Override
  public String expandString(String value, boolean encode) {
    if (value == null) {
      return "";
    }

    int n = value.indexOf('{');

    if ((n == -1) &&!encode) {
      return value;
    }

    StringWriter sw = new StringWriter();

    try {
      expandStringEx(value, encode, sw, n);
    } catch(IOException e) {
      Platform.ignoreException(null, e);
    }

    return sw.toString();
  }

  @Override
  public void expandString(String value, boolean encode, Writer writer) {
    try {
      if (value == null) {
        return;
      }

      int n = value.indexOf('{');

      if (n == -1) {
        if (encode) {
          URLEncoder.encode(value, "iso-8859-1", writer);
        } else {
          writer.write(value);
        }

        return;
      }

      expandStringEx(value, encode, writer, n);
    } catch(IOException e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  /**
   * Indicates that the data loading task has completed.
   */
  @Override
  public void finishedLoading() {
    if (Platform.isUIThread()) {
      finishedLoadingEx();

      return;
    }

    Runnable r = new Runnable() {
      @Override
      public void run() {
        finishedLoadingEx();
      }
    };

    Platform.invokeLater(r);
  }

  @Override
  public void finishedParsing() {}

  @Override
  public void fireEvent(String eventName, EventObject event) {
    fireEventEx(widgetListener, eventName, event, false);
  }

  /**
   * Fires the action associated with the named keystroke
   *
   * @param name
   *          the name of the keystroke
   */
  public void fireKeystrokeAction(String name) {}

  /**
   * Fires the original action associated with the named keystroke This is the
   * original action for a keystroke that was overridden by a keystroke mapping
   * that invokes JavaScript code.
   *
   * @param name
   *          the name of the keystroke
   */
  public void fireOriginalKeystrokeAction(String name) {}

  /**
   * Request that the widget obtains focus (JavaScript Method)
   */
  public void focus() {
    requestFocus();
  }

  /**
   * Focuses the next widget in the navigation sequence
   */
  public abstract void focusNextWidget();

  /**
   * Focuses the previous widget in the navigation sequence
   */
  public abstract void focusPreviousWidget();

  /**
   * Load the data referenced by the specified link. For list type widgets
   * (lists, trees, tables, etc), the data will be appended to any existing data
   *
   * @param link
   *          the link reference
   * @param deferred
   *          true if this is a deferred load; false otherwise
   */
  public void handleActionLink(ActionLink link, boolean deferred) {
    if (isDisposed()) {
      return;
    }

    if (!deferred) {
      startedLoading();
    }

    try {
      if ((widgetDataLink != null) && (widgetDataLink != link)) {
        widgetDataLink.setContext(null);
      }

      widgetDataLink = link;
      sourceURL      = link.getURL(this);
      activeLink     = link;

      if ((sourceURL != null) && CollectionURLConnection.isCollectionURL(sourceURL)) {
        handleCollection(CollectionURLConnection.getCollection(sourceURL), 0);
      } else {
        DataItemParserHandler.parse(this, link, columnCount, this);
      }
    } catch(IOException e) {
      throw ApplicationException.runtimeException(e);
    } finally {
      activeLink = null;
      link.close();

      if (!deferred) {
        finishedLoading();
      }
    }
  }

  /**
   * Load the data referenced by the specified collection. For list type widgets
   * (lists, trees, tables, etc), the data will be appended to any existing data
   *
   * @param c
   *          the collection
   * @param skip
   *          the number of initial items to skip
   */
  public void handleCollection(Collection<RenderableDataItem> c, int skip) {
    if (isDesignMode()) {
      this.finishedLoading();

      return;
    }

    if (c != null) {
      this.startedParsing();
      this.startedLoading();

      try {
        handleCollectionEx(c, skip);
      } finally {
        this.finishedParsing();
        this.finishedLoading();
      }
    }
  }

  /**
   * Load the data referenced by the specified URL. For list type widgets
   * (lists, trees, tables, etc), the data will be appended to any existing data
   *
   * @param url
   *          the URL
   * @see #setDataLink
   */
  public void handleDataURL(String url) {
    handleActionLink(new ActionLink(this, url, null), false);
  }

  /**
   * Load the data referenced by the specified URL. For list type widgets
   * (lists, trees, tables, etc), the data will be appended to any existing data
   *
   * @param url
   *          the URL
   * @see #setDataLink
   */
  public void handleDataURL(URL url) {
    handleActionLink(new ActionLink(this, url, null), false);
  }

  /**
   * Load the data referenced by the specified URL. For list type widgets
   * (lists, trees, tables, etc), the data will be appended to any existing data
   *
   * @param url
   *          the URL
   * @param deferred
   *          true to defer loading of the link; false otherwise
   * @return the Future for the deferred task or null
   * @see #setDataLink
   */
  public iCancelableFuture handleDataURL(String url, boolean deferred) {
    ActionLink link = new ActionLink(this, url, null);

    if (deferred) {
      loadStarted(link);

      return getAppContext().executeWorkerTask(this.createDataLoadTask(link, true));
    } else {
      handleActionLink(link, false);

      return null;
    }
  }

  /**
   * Load the data referenced by the specified URL. For list type widgets
   * (lists, trees, tables, etc), the data will be appended to any existing data
   *
   * @param mimeType
   *          a MIME type to use to override the one returned by the server
   * @param url
   *          the URL
   * @see #setDataLink
   */
  public void handleDataURL(String mimeType, String url) {
    handleActionLink(new ActionLink(this, url, mimeType), false);
  }

  /**
   * Load the data referenced by the specified URL. For list type widgets
   * (lists, trees, tables, etc), the data will be appended to any existing data
   *
   * @param mimeType
   *          a MIME type to use to override the one returned by the server
   * @param url
   *          the URL
   * @see #setDataLink
   */
  @Override
  public void handleDataURL(String mimeType, URL url) {
    handleActionLink(new ActionLink(this, url, mimeType), false);
  }

  /**
   * Load the data referenced by the specified URL. For list type widgets
   * (lists, trees, tables, etc), the data will be appended to any existing data
   *
   * @param url
   *          the URL
   * @param deferred
   *          true to defer loading of the link; false otherwise
   * @see #setDataLink
   */
  public void handleDataURL(URL url, boolean deferred) {
    ActionLink link = new ActionLink(this, url, null);

    if (deferred) {
      loadStarted(link);
      getAppContext().executeWorkerTask(this.createDataLoadTask(link, true));
    } else {
      handleActionLink(link, false);
    }
  }

  @Override
  public void handleException(Throwable e) {
    if (!inExceptionHandler &&!isDisposed()) {
      try {
        inExceptionHandler = true;

        if (isEventEnabled(iConstants.EVENT_ERROR)) {
          DataEvent de = new DataEvent(getContainerComponent(), e);

          executeEvent(iConstants.EVENT_ERROR, de);

          if (de.isConsumed()) {
            return;
          }
        }
      } finally {
        inExceptionHandler = false;
      }

      final iContainer p = getParent();

      if (p != null) {
        p.handleException(e);

        return;
      } else {    // can be null for a parent that is not on the screen
        getAppContext().getWindowViewer().handleException(e);

        return;
      }
    }

    throw ApplicationException.runtimeException(e);
  }

  /**
   * This method is called to populate the widget from the specified data The
   * format of the data is identified by it MIME type. The data is processed in
   * the same manner as data from a URL
   *
   * @param mimeType
   *          the MIME type of the data
   * @param data
   *          the data to load
   */
  public void handleInlineData(String mimeType, String data) {
    handleActionLink(new ActionLink(mimeType, data), false);
  }

  /**
   * Load the data from the specified reader. For list type widgets (lists,
   * trees, tables, etc), the data will be appended to any existing data
   *
   * @param mimeType
   *          a MIME type to use to override the one returned by the server
   * @param reader
   *          the reader containing the data
   */
  public void handleReader(String mimeType, Reader reader) {
    startedLoading();

    try {
      DataItemParserHandler.parse(this, mimeType, reader, columnCount, this, null);
      finishedLoading();
    } catch(Exception ex) {
      throw ApplicationException.runtimeException(ex);
    }
  }

  @Override
  public int hashCode() {
    return System.identityHashCode(this);
  }

  @Override
  public void imageLoaded(UIImage image) {
    if (!isDisposed()) {
      repaint();
    }
  }

  @Override
  public boolean importData(iTransferable transferable, DropInformation drop) {
    drop.setTargetWidget(this);
    drop.setTransferable(transferable);

    if ((getWidgetListener() != null) && getWidgetListener().isEnabled(iConstants.EVENT_DROP)) {
      DataEvent e = new DataEvent(this, drop);

      // run now because transferable is transient
      getWidgetListener().evaluate(iConstants.EVENT_DROP, e, false);

      return !e.isConsumed();
    } else {
      try {
        if (fileDroppingAllowed || urlDroppingAllowed) {
          List<URL> list = drop.getURLList();

          if (list != null) {
            return importURL(list, drop);
          }
        }
      } catch(Exception ignore) {}

      return importDataEx(transferable, drop);
    }
  }

  /**
   * Parses the specified string and creates a data column from it
   *
   * @param value
   *          the value to create the item for
   * @param ldSeparator
   *          optional separator for parsing out linkedData
   *
   * @return the newly created item
   * @throws IOException
   */
  public Column parseColumn(String value, char ldSeparator) throws IOException {
    DataItemCSVParser p = perThreadParser.get();

    p.setLinkedDataSeparator(ldSeparator);

    return p.parseColumnItem(this, value, this);
  }

  /**
   * Parses the specified string and creates a data item from it
   *
   * @param value
   *          the value to create the item for
   *
   * @return the newly created item
   * @throws IOException
   */
  public RenderableDataItem parseDataItem(String value) throws IOException {
    return parseDataItem(value, '\0');
  }

  /**
   * Parses the specified string and creates a data item from it
   *
   * @param value
   *          the value to create the item for
   * @param ldSeparator
   *          optional separator for parsing out linkedData
   *
   * @return the newly created item
   * @throws IOException
   */
  public RenderableDataItem parseDataItem(String value, char ldSeparator) throws IOException {
    DataItemCSVParser p = perThreadParser.get();

    p.setLinkedDataSeparator(ldSeparator);

    return p.parseDataItem(this, value, this);
  }

  /**
   * Parses the data referenced by the specified URL.
   *
   * @param link
   *          the link to parse
   * @param tabular
   *          true if the data represents table data; false for list data
   * @return a list containing the parsed items
   *
   * @throws Exception
   *           if an error occurs
   */
  public List<RenderableDataItem> parseDataLink(ActionLink link, boolean tabular) throws Exception {
    return DataItemParserHandler.parse(this, link, tabular
            ? -1
            : 0);
  }

  /**
   * Parses ,in the background, the data referenced by the specified URL.
   *
   * @param link
   *          the link to parse
   * @param tabular
   *          true if the data represents table data; false for list data
   * @param cb
   *          a callback to receive the results
   * @return a handle to use to be able to cancel the operation;
   *
   */
  public iCancelable parseDataLink(final ActionLink link, final boolean tabular, final iFunctionCallback cb) {
    ActionLink.ReturnDataType type = tabular
                                     ? ActionLink.ReturnDataType.TABLE
                                     : ActionLink.ReturnDataType.LIST;

    return Platform.getContent(this, link, type, cb);
  }

  /**
   * Parses the data referenced by the specified URL.
   *
   * @param url
   *          the url
   * @param tabular
   *          true if the data represents table data; false for list data
   * @return a list containing the parsed items
   *
   * @throws Exception
   *           if an error occurs
   */
  public List<RenderableDataItem> parseDataURL(String url, boolean tabular) throws Exception {
    return parseDataURL(url, '\0', '\0');
  }

  /**
   * Parses the data referenced by the specified URL.
   *
   * @param url
   *          the URL
   * @param colSep
   *          the column separator
   * @return a list containing the parsed items
   *
   * @throws Exception
   *           if an error occurs
   */
  public List<RenderableDataItem> parseDataURL(String url, char colSep) throws Exception {
    return parseDataURL(url, colSep, '\0');
  }

  /**
   * Parses the data referenced by the specified URL.
   *
   * @param url
   *          the url
   * @param tabular
   *          true if the data represents table data; false for list data
   * @param cb
   *          a callback to receive the results
   * @return a handle to use to be able to cancel the operation;
   *
   */
  public iCancelable parseDataURL(String url, boolean tabular, iFunctionCallback cb) {
    return parseDataURL(url, '\0', '\0', cb);
  }

  /**
   * Parses the data referenced by the specified URL.
   *
   * @param url
   *          the URL
   * @param colSep
   *          the column separator
   * @param ldSep
   *          optional linked data separator
   * @return a list containing the parsed items
   *
   * @throws Exception
   *           if an error occurs
   */
  public List<RenderableDataItem> parseDataURL(String url, char colSep, char ldSep) throws Exception {
    ActionLink link = new ActionLink(this, url, null);

    if (colSep != '\0') {
      link.setColumnSeparator(colSep);
    }

    link.setLinkedDataSeparator(ldSep);

    return DataItemParserHandler.parse(this, link, (colSep != '\0')
            ? -1
            : 0);
  }

  /**
   * Parses the data referenced by the specified URL.
   *
   * @param url
   *          the URL
   * @param colSep
   *          the column separator
   * @param cb
   *          a callback to receive the results
   * @return a handle to use to be able to cancel the operation;
   *
   */
  public iCancelable parseDataURL(String url, char colSep, char ldSep, iFunctionCallback cb) {
    ActionLink link = new ActionLink(this, url, null);
    boolean    tabular;

    if (ldSep != '\0') {
      link.setLinkedDataSeparator(ldSep);
    }

    if (colSep != '\0') {
      link.setColumnSeparator(colSep);
      tabular = true;
    } else {
      tabular = false;
    }

    ActionLink.ReturnDataType type = tabular
                                     ? ActionLink.ReturnDataType.TABLE
                                     : ActionLink.ReturnDataType.LIST;

    return Platform.getContent(this, link, type, cb);
  }

  /**
   * Populates a renderable data item from the specified item configuration
   *
   * @param widget
   *          the widget to populate the item for (can be null)
   * @param item
   *          the item configuration
   * @param di
   *          optional item to use to store the configured data (if not
   *          specified then a new one is created)
   *
   * @return the newly created item of the passed in item if it was not null
   */
  public static RenderableDataItem populateItem(iWidget widget, DataItem item, RenderableDataItem di) {
    if (di == null) {
      di = new RenderableDataItem();
    }

    if (widget == null) {
      widget = Platform.getContextRootViewer();
    }

    iPlatformAppContext app      = widget.getAppContext();
    Class               cvtclass = null;
    int                 type     = RenderableDataItem.fromSPOTType(item.valueType.intValue());
    String              s        = item.converterClass.getValue();
    Object              context  = item.valueContext.getValue();

    if (s != null) {
      try {
        cvtclass = app.getDataConverterClass(s);
      } catch(ClassNotFoundException ex) {
        widget.handleException(ex);
      }
    } else if (context != null) {
      cvtclass = RenderableDataItem.getDefaultConverterClass(type);
    }

    if (cvtclass != null) {
      di.setConverterClass(cvtclass);

      iDataConverter cvt = app.getDataConverter(cvtclass);

      context = cvt.createContext(widget, (String) context);
      di.setDataConverter(cvt);
    }

    s = item.linkedDataconverterClass.getValue();

    if ((s != null) && (s.length() > 0)) {
      try {
        cvtclass = app.getDataConverterClass(s);

        if (cvtclass != null) {
          iDataConverter cvt = Platform.getDataConverter(cvtclass);

          di.setLinkedDataConverter(cvt);
          s = item.linkedDataContext.getValue();

          if (s != null) {
            di.setLinkedDataContext(cvt.createContext(widget, s));
          }
        }
      } catch(ClassNotFoundException ex) {
        cvtclass = null;
        widget.handleException(ex);
      }
    }

    String value = item.value.getValue();
    String data  = item.linkedData.getValue();

    if ((value != null) && value.startsWith("Label")) {
      s = null;
    }

    di.setValues(value, type, data, widget.getIcon(item.icon), context);

    if (item.disabledIcon.spot_hasValue()) {
      di.setDisabledIcon(widget.getIcon(item.disabledIcon));
    }

    if (item.alternateIcon.spot_hasValue()) {
      di.setAlternateIcon(widget.getIcon(item.alternateIcon));
    }

    if (item.scaleIcon.booleanValue()) {
      float f = SNumber.floatValue(item.scaleIcon.spot_getAttribute("percent"));

      if (f > 1) {
        f = f / 100;
      }

      if (f <= 0) {
        f = 1;
      }

      di.setScaleIcon(true, f);
    }

    s = item.fgColor.getValue();

    if (s != null) {
      di.setForeground(UIColorHelper.getColor(s));
    }

    GridCell cell = item.getGridCell();

    if (cell != null) {
      PaintBucket pb = new PaintBucket();

      UIColorHelper.configure(widget, cell, pb);

      if ((pb.getBackgroundPainter() == null) && (pb.getImagePainter() == null)) {
        di.setBackground(pb.getBackgroundColor());
      } else {
        di.setComponentPainter(pb.getComponentPainter(false));
      }

      di.setBorder(pb.getBorder());
    }

    UIFont f = UIFontHelper.getFont(widget, item.font);

    if (f != null) {
      di.setFont(f);
    }

    if (!item.enabled.booleanValue()) {
      di.setEnabled(false);
    }

    s = item.customProperties.getValue();

    if (s != null) {
      di.setCustomProperties(DataParser.parseNameValuePairs(item.customProperties));
    }

    di.setHorizontalAlignment(item.getHorizontalAlignment());
    di.setVerticalAlignment(item.getVerticalAlignment());
    di.setIconPosition(item.getIconPosition());
    di.setVisible(item.visible.booleanValue());
    di.setRowSpan(item.rowSpan.intValue());
    di.setColumnSpan(item.columnSpan.intValue());
    di.setTooltip(item.tooltip.getValue());
    di.setDraggingAllowed(item.draggingAllowed.booleanValue());
    di.setCursorName(item.cursorName.getValue());
    s = item.cursorName.spot_getAttribute("cursorDisplayType");

    if ((s != null) && (s.length() > 0)) {
      di.setCursorDisplayType(CursorDisplayType.valueOf(s.toUpperCase(Locale.US)));
    }

    switch(item.orientation.intValue()) {
      case DataItem.COrientation.horizontal :
        di.setOrientation(RenderableDataItem.Orientation.HORIZONTAL);

        break;

      case DataItem.COrientation.vertical_up :
        di.setOrientation(RenderableDataItem.Orientation.VERTICAL_DOWN);

        break;

      case DataItem.COrientation.vertical_down :
        di.setOrientation(RenderableDataItem.Orientation.VERTICAL_DOWN);

        break;
    }

    SPOTSet set;

    set = item.getImportDataFlavors();

    if ((set != null) && (set.getCount() > 0)) {
      di.setImportDataFlavors(set.stringValues());
    }

    set = item.getExportDataFlavors();

    if ((set != null) && (set.getCount() > 0)) {
      di.setExportDataFlavors(set.stringValues());
    }

    s = item.spot_getAttribute("selected");

    if ((s != null) && s.equalsIgnoreCase("true") && (widget instanceof aPlatformWidget)) {
      ((aPlatformWidget) widget).addSelected(di);
    }

    s = item.spot_getAttribute("expanded");

    if ((s != null) && s.equalsIgnoreCase("true") && (widget instanceof aPlatformWidget)) {
      ((aPlatformWidget) widget).addExpanded(di);
    }

    s = item.spot_getAttribute("selectable");

    if (s != null) {
      di.setSelectable(s.equalsIgnoreCase("true"));
    }

    s = item.spot_getAttribute("editable");

    if (s != null) {
      di.setEditable(s.equalsIgnoreCase("true"));
    }

    Link l = item.getActionLink();

    if (l != null) {
      di.setActionListener(new ActionLink(widget, l));
    }

    return di;
  }

  public Object putAttribute(String key, Object value) {
    if (value == null) {
      return removeCustomProperty(key);
    }

    return setCustomProperty(key, value);
  }

  /**
   * Registers a keystroke action for the widget
   *
   * @param ks
   *          the string representation of the keystroke
   * @param code
   *          the code to execute for the keystroke
   * @param when
   *          when the action should be invoked. One of WHEN_IN_FOCUSED_WINDOW,
   *          WHEN_FOCUSED, WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
   *
   * @return true if the keystroke was registered false; otherwise
   */
  public boolean registerKeystroke(String ks, Object code, int when) {
    return false;
  }

  @Override
  public void reload(boolean context) {
    if (widgetDataLink != null) {
      clearContents();
      setDataLink(widgetDataLink);
    }
  }

  /**
   * Convenience method for removing an arbitrary item from the list
   *
   * @param item
   *          the item to remove
   */
  @Override
  public boolean remove(Object item) {
    int n = (item instanceof RenderableDataItem)
            ? indexOf(item)
            : this.indexOfValue(item);

    if (n != -1) {
      this.remove(n);

      return true;
    }

    return false;
  }

  /**
   * Removes a viewer specific attribute.
   *
   * @param key
   *          the attribute key
   * @return the value for the specified user key
   */
  @Override
  public Object removeAttribute(String key) {
    return removeCustomProperty(key);
  }

  @Override
  public void removeBackgroundImage() {
    setBackgroundImage(null, 0, null);
  }

  /**
   * Removes the event handler for the specified event. Once removed, the
   * handler will no longer be invoked for events of the specified type
   *
   * @param event
   *          the name event to remove the handler for
   *
   * @param code
   *          the specific handler code tor remove or null to remove all
   *          handlers
   */
  public void removeEvent(String event, Object code) {
    this.removeEventHandler(event, code);
  }

  @Override
  public Object removeEventHandler(String event, Object code) {
    aWidgetListener l = this.getWidgetListener();

    if ((l != null) && (event != null)) {
      return l.removeEventHandler(event, code);
    }

    return null;
  }

  /**
   * Removes the event handler for the specified event. Once removed, the
   * handler will no longer be invoked for events of the specified type
   *
   * @param event
   *          the name event to remove the handler for
   *
   * @param code
   *          the specific handler code tor remove or null to remove all
   *          handlers
   */
  public void removeEventListener(String event, Object code) {
    this.removeEventHandler(event, code);
  }

  @Override
  public void repaint() {
    getContainerComponent().repaint();
  }

  @Override
  public void reset() {}

  @Override
  public void selectAll() {}

  /**
   * Shows the popup menu for the widget
   */
  public abstract void showPopupMenu(int x, int y);

  /**
   * Invoked when a widget is about to start loading data I will fire the
   * started loading event
   */
  public void startedLoading() {
    if (Platform.isUIThread()) {
      startedLoadingEx();

      return;
    }

    aWidgetListener wl = getWidgetListener();

    if ((wl == null) ||!wl.isEnabled(iConstants.EVENT_STARTED_LOADING)) {
      return;
    }

    Runnable r = new Runnable() {
      @Override
      public void run() {
        startedLoadingEx();
      }
    };

    Platform.invokeLater(r);
  }

  public void startedLoadingEx() {
    aWidgetListener wl = getWidgetListener();

    if ((wl != null) && wl.isEnabled(iConstants.EVENT_STARTED_LOADING)) {
      EventObject e = new EventObject(this);

      wl.evaluate(iConstants.EVENT_STARTED_LOADING, e, false);
    }
  }

  @Override
  public void startedParsing() {}

  /**
   * Returns the widgets contents as a JSON encoded string
   *
   * @return the widgets contents as a JSON encoded string
   */
  public String toJSON() {
    StringWriter sw = new StringWriter();

    writeJSONValue(true, sw);

    return sw.toString();
  }

  /**
   * Returns a string representation of the widget. In general, the
   * <code>toString</code> method returns a widget's current selection or value
   *
   * @return a string representation of the widget.
   */
  @Override
  public String toString() {
    String s = getValueAsString();

    return (s == null)
           ? ""
           : s;
  }

  @Override
  public void triggerActionEvent() {
    triggerActionEvent("triggerActionEvent");
  }

  @Override
  public void triggerActionEvent(String cmd) {
    ActionEvent ae = new ActionEvent(getDataComponent());

    actionPerformed(ae);
  }

  /**
   * Unregisters the component as belonging to a specified widget
   *
   * @param comp
   *          the component to unregister
   */
  public void unregisterWithWidget(iPlatformComponent comp) {
    if (comp.getWidget() == this) {
      comp.setWidget(null);
    }
  }

  /**
   * Causes the widget to revalidate and repaint
   *
   * @see #updateEx()
   */
  @Override
  public void update() {
    if (isDisposed()) {
      return;
    }

    if (Platform.isUIThread()) {
      updateEx();
    } else {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          updateEx();
        }
      });
    }
  }

  @Override
  public void writeHTTPContent(boolean first, Writer writer, String boundary) {
    try {
      String name  = getHTTPFormName();
      Object value = this.getHTTPFormValue();

      if ((name == null) ||!getDataComponent().isEnabled() || (value == null)) {
        return;
      }

      if (value instanceof String) {
        FormHelper.writeFieldHeader(first, writer, boundary, name);
        writer.write(Functions.utf8String((String) value));
        writer.write("\r\n");
      } else if (value instanceof int[]) {
        FormHelper.writeHTTPContent(first, writer, boundary, name, (int[]) value);
      } else {
        FormHelper.writeHTTPContent(first, writer, boundary, name, (String[]) value, true);
      }
    } catch(IOException e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  @Override
  public boolean writeHTTPValue(boolean first, Writer writer) {
    try {
      String name  = getHTTPFormName();
      Object value = this.getHTTPFormValue();

      if ((name == null) ||!getDataComponent().isEnabled() || (value == null)) {
        return false;
      }

      if (value instanceof String) {
        if (!first) {
          writer.write('&');
        }

        URLEncoder.encode(name, "iso-8859-1", writer);
        writer.write('=');
        URLEncoder.encode((String) value, "iso-8859-1", writer);

        return true;
      } else if (value instanceof int[]) {
        return FormHelper.writeHTTPValue(first, writer, name, (int[]) value);
      } else {
        return FormHelper.writeHTTPValue(first, writer, name, (String[]) value);
      }
    } catch(IOException e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  @Override
  public boolean writeJSONValue(boolean first, Writer writer) {
    try {
      String name  = getHTTPFormName();
      Object value = this.getHTTPFormValue();

      if ((name == null) ||!getDataComponent().isEnabled() || (value == null)) {
        return false;
      }

      if (value instanceof String) {
        if (!first) {
          writer.write(",\n\t");
        }

        JSONHelper.encode(name, writer);
        writer.write(": ");
        JSONHelper.encode((String) value, writer);

        return true;
      } else if (value instanceof int[]) {
        return FormHelper.writeJSONValue(first, writer, name, (int[]) value);
      } else {
        return FormHelper.writeJSONValue(first, writer, name, (String[]) value);
      }
    } catch(IOException e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  @Override
  public void setAllowContainerToDisable(boolean allow) {
    this.allowContainerToDisable = allow;
  }

  @Override
  public void setAttribute(String key, Object value) {
    setCustomProperty(key, value);
  }

  @Override
  public void setBackground(UIColor bg) {
    getContainerComponent().setBackground(bg);
    getContainerComponent().repaint();
  }

  @Override
  public void setBackgroundImage(UIImage image, int opacity, RenderType type) {
    setOverlayPainter((image == null)
                      ? null
                      : new UIImagePainter(image, opacity, type), true);
    getContainerComponent().repaint();
  }

  @Override
  public void setBackgroundOverlayPainter(iPlatformPainter painter) {
    iPlatformComponentPainter cp = this.getComponentPainter(true, true);

    if (cp != null) {
      cp.setBackgroundOverlayPainter(painter);
      getContainerComponent().repaint();
    }
  }

  @Override
  public void setBackgroundPainter(iBackgroundPainter painter) {
    iPlatformComponentPainter cp = this.getComponentPainter(true, true);

    cp.setBackgroundPainter(painter, true);
    getContainerComponent().repaint();
  }

  @Override
  public void setBorder(iPlatformBorder border) {
    getContainerComponent().setBorder(border);
    getContainerComponent().repaint();
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    getContainerComponent().setComponentPainter(cp);
    getContainerComponent().repaint();
  }

  @Override
  public void setCopyingAllowed(boolean allowed) {
    copyingAllowed = allowed;
  }

  @Override
  public void setCursorName(String name) {}

  @Override
  public void setDataItems(DataItem items) {
    clearContents();

    if (items != null) {
      try {
        DataItemParserHandler.parse(this, columnCount, items, this);
      } catch(IOException ex) {
        throw ApplicationException.runtimeException(ex);
      }
    }
  }

  @Override
  public void setDataLink(ActionLink link) {
    setDataLink(link, true);
  }

  @Override
  public iCancelableFuture setDataLink(ActionLink link, boolean defer) {
    clearContents();
    startedParsing();

    if (defer) {
      loadStarted(link);

      return getAppContext().executeWorkerTask(this.createDataLoadTask(link, true));
    } else {
      handleActionLink(link, false);

      return null;
    }
  }

  @Override
  public void setDataURL(String url) {
    setDataLink(new ActionLink(this, url, null), true);
  }

  @Override
  public void setDataURL(URL url) {
    setDataLink(new ActionLink(this, url, null), true);
  }

  @Override
  public iCancelableFuture setDataURL(String url, boolean defer) {
    return setDataLink(new ActionLink(this, url, null), defer);
  }

  @Override
  public void setDeletingAllowed(boolean allowed) {
    deletingAllowed = allowed;
  }

  /**
   * Sets the disabled state of the widget
   *
   * @param disabled
   *          true to disable; false to enable
   */
  public void setDisabled(boolean disabled) {
    setEnabled(!disabled);
  }

  /**
   * Sets the paint use to designate the viewer as disabled
   *
   * @param color
   *          the color use to designate the viewer as disabled
   */
  public void setDisabledColor(UIColor color) {
    getDataComponent().setDisabledColor(color);
  }

  @Override
  public void setDisabledIcon(iPlatformIcon icon) {
    iPlatformComponent c = this.getDataComponent();

    if (c instanceof iActionComponent) {
      ((iActionComponent) c).setDisabledIcon(icon);
    } else {
      disabledIcon = icon;
    }
  }

  @Override
  public void setEnabled(boolean enabled) {
    iPlatformComponent dc = getDataComponent();
    iPlatformComponent cc = getContainerComponent();

    dc.setEnabled(enabled);

    if (cc != dc) {
      cc.setEnabled(enabled);
    }
  }

  @Override
  public void setEventHandler(String event, Object code, boolean append) {
    String se = aWidgetListener.fromWebEventEx(event);

    if (se != null) {
      event = se;
    }

    WidgetListener l = (WidgetListener) this.getWidgetListener();

    if (l == null) {
      l = (WidgetListener) createWidgetListener(this, new HashMap(), getScriptHandler());
      setWidgetListener(l);
    }

    l.setEventHandler(event, code, append);

    iPlatformComponent c = getDataComponent();

    c.removeMouseListener(l);
    c.removeMouseMotionListener(l);

    if (l.isMouseEventsEnabled()) {
      c.addMouseListener(l);
    }

    if (l.isMouseMotionEventsEnabled()) {
      c.addMouseMotionListener(l);
    }

    c.removeKeyListener(l);

    if (l.isKeyEventsEnabled()) {
      c.addKeyListener(l);
    }

    c.removeFocusListener(l);

    if (l.isEnabled(iConstants.EVENT_FOCUS) || l.isEnabled(iConstants.EVENT_BLUR)) {
      c.addFocusListener(l);
    }

    c.removeViewListener(l);

    if (l.isEnabled(iConstants.EVENT_RESIZE) || l.isEnabled(iConstants.EVENT_MOVED)
        || l.isEnabled(iConstants.EVENT_SHOWN) || l.isEnabled(iConstants.EVENT_HIDDEN)) {
      c.addViewListener(l);
    }

    if (l.isEnabled(iConstants.EVENT_CHANGE)) {
      if (this instanceof iListHandler) {
        ((iListHandler) this).removeSelectionChangeListener(l);
        ((iListHandler) this).addSelectionChangeListener(l);
      } else if (this instanceof iChangeable) {
        ((iChangeable) this).removeChangeListener(l);
        ((iChangeable) this).addChangeListener(l);
      } else if (this instanceof aTextFieldWidget) {
        ((aTextFieldWidget) this).removeTextChangeListener(l);
        ((aTextFieldWidget) this).addTextChangeListener(l);
      }
    }

    if (l.isEnabled(iConstants.EVENT_ACTION) && (this instanceof iActionable)) {
      ((iActionable) this).removeActionListener(l);
      ((iActionable) this).addActionListener(l);
    }

    if ((this instanceof iTreeHandler) && (event.startsWith("onWill") || event.startsWith("onHas"))) {
      ((iTreeHandler) this).removeExpandedListener(l);
      ((iTreeHandler) this).removeExpansionListener(l);

      if (l.isExpandedEventsEnabled()) {
        ((iTreeHandler) this).addExpandedListener(l);
      }

      if (l.isExpansionEventsEnabled()) {
        ((iTreeHandler) this).addExpansionListener(l);
      }
    }
  }

  @Override
  public void setFocusPainted(boolean focusPainted) {
    getContainerComponent().setFocusPainted(focusPainted);
  }

  @Override
  public void setFocusable(boolean focusable) {
    getDataComponent().setFocusable(focusable);
    getContainerComponent().setFocusable(focusable);
  }

  @Override
  public void setFont(UIFont font) {
    getDataComponent().setFont(font);
  }

  @Override
  public void setForeground(UIColor fg) {
    getDataComponent().setForeground(fg);
    getDataComponent().repaint();
  }

  @Override
  public void setFromHTTPFormValue(Object value) {
    setValue(value);
  }

  /**
   * Convenience method for setting the value of a widget
   *
   * @param value
   *          the value
   */
  public void setInnerHTML(String value) {
    setValue(value);
  }

  /**
   * Convenience method for setting the value of a widget
   *
   * @param value
   *          the value
   */
  public void setInnerText(String value) {
    setValue(value);
  }

  /**
   * Sets the preferred size of the widget.
   *
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void setMinimumSize(int width, int height) {
    iPlatformComponent c = getContainerComponent();
    String             w = null;
    String             h = null;

    if (height != -1) {
      h = height + "px";
    }

    if (width != -1) {
      w = width + "px";
    }

    c.putClientProperty(iConstants.RARE_MIN_WIDTH_PROPERTY, w);
    c.putClientProperty(iConstants.RARE_MIN_HEIGHT_PROPERTY, h);
  }

  /**
   * Sets the preferred size of the widget. The parameters are strings so that
   * logical units can be specified
   *
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void setMinimumSize(String width, String height) {
    setMinimumSize(formComponent, width, height, true);
  }

  protected void setMinimumSize(iPlatformComponent c, String width, String height, boolean always) {
    if (height != null) {
      if (height.equals("-1")) {
        height = null;
      } else {
        height = expandString(height, false);
      }
    }

    if (width != null) {
      if (width.equals("-1")) {
        width = null;
      } else {
        width = expandString(width, false);
      }
    }

    if (always || (width != null)) {
      c.putClientProperty(iConstants.RARE_MIN_WIDTH_PROPERTY, width);
    }

    if (always || (height != null)) {
      c.putClientProperty(iConstants.RARE_MIN_HEIGHT_PROPERTY, height);
    }
  }

  /**
   * Sets the name of the widget. Replaces the widgets existing name
   *
   * @param name
   *          the new name for the widget
   */
  public void setName(String name) {
    iWidget w = getFormViewer().getWidget(widgetName);

    if ((w != null) && (w != this)) {
      throw new ApplicationException(getAppContext().getResourceAsString("Rare.runtime.text.widgetExists"), name);
    } else if (w != null) {
      getFormViewer().unregisterNamedItem(widgetName);
    }

    widgetName = name;
    getFormViewer().registerNamedItem(name, w);
  }

  /**
   * Convenience method for inserting an arbitrary item to the list
   *
   * @param index
   *          the index where the item is to be inserted
   * @param item
   *          the item to insert
   */
  public void setObject(int index, Object item) {
    set(index, createItem(item));
  }

  @Override
  public void setOpaque(boolean opaque) {
    getDataComponent().setOpaque(opaque);
  }

  @Override
  public void setOrientation(Orientation orientation) {
    super.setOrientation(orientation);

    iPlatformComponent c = getDataComponent();

    if (c instanceof iActionComponent) {
      ((iActionComponent) c).setOrientation(orientation);
    }
  }

  @Override
  public void setOverlayPainter(iPlatformPainter painter) {
    setOverlayPainter(painter, false);
  }

  @Override
  public void setParent(iContainer parent) {
    if ((iWidget)parent != this) { //cast for jave->objc
      parentContainer = parent;
    }
  }

  @Override
  public void setPopupContainer(iPopup popup) {
    popupContainer = popup;
  }

  /**
   * Sets the set containing MenuItem elements to be used for the widgets popup
   * menu
   *
   * @param set
   *          the set of o menu items
   */
  public void setPopupMenuSet(SPOTSet set) {
    menuSet = set;

    if (menuSet != null) {
      menuSet.spot_setParent(null);    // break relationship to prevent memory leak
    }
  }

  /**
   * Sets the preferred size of the widget
   *
   * @param size
   *          the size
   */
  public void setPreferredSize(UIDimension size) {
    setPreferredSize(size.width, size.height);
  }

  /**
   * Sets the preferred size of the widget
   *
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void setPreferredSize(float width, float height) {
    iPlatformComponent c = getContainerComponent();
    String             w = null;
    String             h = null;

    if (height >= 0) {
      h = height + "px";
    }

    if (width >= 0) {
      w = width + "px";
    }

    c.putClientProperty(iConstants.RARE_WIDTH_PROPERTY, w);
    c.putClientProperty(iConstants.RARE_HEIGHT_PROPERTY, h);
  }

  /**
   * Sets the preferred size of the widget. The parameters are strings so that
   * logical units can be specified
   *
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void setPreferredSize(String width, String height) {
    setPreferredSize(getContainerComponent(), width, height, true);
  }

  protected void setPreferredSize(iPlatformComponent c, String width, String height, boolean always) {
    if (height != null) {
      if (height.equals("-1")) {
        height = null;
      } else {
        height = expandString(height, false);
      }
    }

    if (width != null) {
      if (width.equals("-1")) {
        width = null;
      } else {
        width = expandString(width, false);
      }
    }

    if (always || (width != null)) {
      c.putClientProperty(iConstants.RARE_WIDTH_PROPERTY, width);
    }

    if (always || (height != null)) {
      c.putClientProperty(iConstants.RARE_HEIGHT_PROPERTY, height);
    }
  }

  /**
   * Sets whether the widget is required to have a value in order to be valid
   * for submission
   *
   * @param required
   *          true if the widget is required to have a value in order to be
   *          valid for submission; false otherwise
   */
  public void setRequired(boolean required) {
    this.required = required;
  }

  @Override
  public void setRootItem(RenderableDataItem item) {
    setValue(item);
  }

  @Override
  public void setScriptingContext(WidgetContext context) {
    scriptingContex = context;
  }

  @Override
  public void setSelectable(boolean selectable) {}

  @Override
  public void setSelected(boolean selected) {
    this.getDataComponent().setSelected(selected);
  }

  /**
   * Sets the size of the widget
   *
   * @param width
   *          the width to set
   * @param height
   *          the height
   */
  public void setSize(float width, float height) {
    setPreferredSize(width, height);
  }

  @Override
  public void setSizeLocked(boolean sizeLocked) {
    getContainerComponent().setSizeLocked(sizeLocked);
  }

  @Override
  public void setTextDirection(String direction) {}

  @Override
  public void setTitle(String title) {
    widgetTitle = title;

    if (titleLabel != null) {
      Utils.setMnemonicAndText(titleLabel, title);
    }
  }

  @Override
  public void setTooltip(CharSequence tooltip) {
    super.setTooltip(tooltip);

    if (dataComponent instanceof iActionComponent) {
      ((iActionComponent) dataComponent).setToolTipText(tooltip);
    }
  }

  @Override
  public void setVisible(boolean visible) {
    getContainerComponent().setVisible(visible);
  }

  /**
   * Sets the link that the widgets was loaded from. This method does no load
   * any data. It merely sets the link as the source of the widget's current
   * data so that relative URL's will be relative to the specified link
   *
   * @param link
   *          the link for the widget's data
   */
  public void setWidgetDataLink(ActionLink link) {
    widgetDataLink = link;

    try {
      sourceURL = link.getURL(this);
    } catch(MalformedURLException e) {
      Platform.ignoreException(null, e);
    }
  }

  /**
   * Sets the widget listener
   *
   * @param listener
   *          the widget listener
   */
  public void setWidgetListener(aWidgetListener listener) {
    this.widgetListener = listener;
  }

  @Override
  public iPlatformAppContext getAppContext() {
    iViewer v = getViewer();

    return (v == null)
           ? Platform.getAppContext()
           : v.getAppContext();
  }

  @Override
  public Object getApplicationContext() {
    return getAppContext();
  }

  @Override
  public Object getAttribute(String key) {
    Object o   = null;
    int    len = (key == null)
                 ? 0
                 : key.length();

    if (len == 0) {
      return null;
    }

    char c = key.charAt(0);

    if (c == '%') {
      return getWidgetAttribute(key);
    }

    if (c == '@') {
      c = key.charAt(1);

      if (c == '$') {
        if (key.equalsIgnoreCase("@$screenSize")) {
          return UIScreen.getRelativeScreenSizeName();
        }

        if (key.equalsIgnoreCase("@$screenOrientation")) {
          return UIScreen.getOrientationName();
        }
      }

      return getAppContext().getData(key.substring(1));
    }

    if ((c == 'r') && key.startsWith(iConstants.RESOURCE_PREFIX)) {
      key = key.substring(9);
      o   = getAppContext().getUIDefaults().get(key);

      return (o == null)
             ? getAppContext().getResourceAsString(key)
             : o;
    }

    if (c == '$') {
      try {
        return Platform.getFunctionHandler().execute(this, key);
      } catch(ParseException ex) {
        return key;
      }
    }

    o = getCustomProperty(key);

    if (o != null) {
      return getScriptHandler().unwrap(o);
    }

    return getAttributeEx(c, key);
  }

  @Override
  public UIColor getBackground() {
    return getContainerComponent().getBackground();
  }

  /**
   * Returns the background color for the color configuration string.
   *
   * @param color
   *          the color configuration string.
   *
   * @return a color object represented by the specified color configuration
   *         string.
   */
  public UIColor getBackgroundColor(String color) {
    return UIColorHelper.getBackgroundColor(color);
  }

  @Override
  public iPlatformPainter getBackgroundOverlayPainter() {
    return getOverlayPainter(true);
  }

  @Override
  public iBackgroundPainter getBackgroundPainter() {
    iPlatformComponentPainter cp = this.getComponentPainter(true, true);

    return (cp == null)
           ? null
           : cp.getBackgroundPainter();
  }

  @Override
  public URL getBaseURL() {
    return getViewer().getBaseURL();
  }

  @Override
  public iPlatformBorder getBorder() {
    return getContainerComponent().getBorder();
  }

  //
  // /**
  // * Returns an HTML 5 compliant canvas context that can be used
  // * to draw on the widget's background
  // *
  // * @return the canvas
  // */
  // public iCanvas getCanvas() {
  // return Canvas.getCanvas(this, true);
  // }

  /**
   * Get height of an element (HTML compatibility)
   *
   * @return height of an element relative to the layout.
   */
  public int getClientHeight() {
    return getHeight();
  }

  /**
   * Get width of an element (HTML compatibility)
   *
   * @return width of an element
   */
  public int getClientWidth() {
    return getWidth();
  }

  /**
   * Converts the specified string to a color
   *
   * @param color
   *          the string representing the color
   *
   * @return the color represented by the specified string
   */
  public UIColor getColor(String color) {
    if (color == null) {
      return null;
    }

    if (color.indexOf(',') != -1) {
      return ColorUtils.getBackgroundColor(color);
    }

    return UIColorHelper.getColor(color);
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    iPlatformComponentPainter cp = getContainerComponent().getComponentPainter();

    if (cp == null) {
      cp = getDataComponent().getComponentPainter();
    }

    return cp;
  }

  @Override
  public URLConnection getConnection(String file) throws IOException {
    return (URLConnection) getAppContext().openConnection(getURL(file)).getConnectionObject();
  }

  @Override
  public iPlatformComponent getContainerComponent() {
    return formComponent;
  }

  @Override
  public iContainer getContainerViewer() {
    return getParent();
  }

  @Override
  public iPlatformComponent getDataComponent() {
    return dataComponent;
  }

  @Override
  public URL getDataURL() {
    return sourceURL;
  }

  @Override
  public iPlatformIcon getDisabledIcon() {
    iPlatformComponent c = this.getContainerComponent();

    if (c instanceof iActionComponent) {
      return ((iActionComponent) c).getDisabledIcon();
    } else {
      return disabledIcon;
    }
  }

  @Override
  public iPlatformIcon getDragIcon() {
    iPlatformIcon icon;

    if (this instanceof iListHandler) {
      iListHandler list = (iListHandler) this;
      int          n    = list.getSelectedIndexCount();

      if (n == 1) {
        icon = list.getSelectedItem().getIcon();

        if (icon == null) {
          icon = getIcon();
        }
      } else {
        icon = getAppContext().getResourceAsIcon("Rare.icon.dragMultiple");
      }
    } else {
      icon = getIcon();
    }

    return (icon == null)
           ? getAppContext().getResourceAsIcon("Rare.icon.dragSingle")
           : icon;
  }

  @Override
  public Object getEventHandler(String event) {
    event = getEventName(event);

    aWidgetListener wl = getWidgetListener();

    if (wl != null) {
      return wl.getEventHandler(event);
    }

    return null;
  }

  @Override
  public UIFont getFont() {
    UIFont f = getDataComponent().getFont();

    return (f == null)
           ? UIFontHelper.getDefaultFont()
           : f;
  }

  /**
   * Returns the UI font corresponding the specified font configuration object
   *
   * @param fi
   *          the font configuration object
   *
   * @return the corresponding UI font
   */
  public UIFont getFont(com.appnativa.rare.spot.Font fi) {
    return (fi == null)
           ? null
           : UIFontHelper.getFont(this, fi);
  }

  /**
   * Returns the UI font corresponding the specified font configuration criteria
   *
   * @param family
   *          the font family
   * @param style
   *          the font style
   * @param ssize
   *          the string representation of the font
   * @param monospaced
   *          true to use a monospaced font; false otherwise
   *
   * @return the corresponding UI font
   */
  public UIFont getFont(String family, int style, String ssize, boolean monospaced) {
    UIFont base = getDataComponent().getFontEx();

    if (base == null) {
      base = FontUtils.getDefaultFont();
    }

    return FontUtils.getFont(base, family, style, ssize, monospaced, false, false);
  }

  @Override
  public UIColor getForeground() {
    return getDataComponent().getForeground();
  }

  /**
   * Get the parent
   *
   * @see #getFormViewer()
   * @return the parent
   */
  public iFormViewer getForm() {
    return getFormViewer();
  }

  @Override
  public iFormViewer getFormViewer() {
    iContainer c = getParent();

    return (c == null)
           ? null
           : c.getFormViewer();
  }

  /**
   * Gets the HTTP form name for the widget
   *
   * @return the HTTP form name for the widget
   */
  @Override
  public String getHTTPFormName() {
    return getName();
  }

  /**
   * Gets the HTTP form value for the widget.
   *
   * @return the HTTP form value for the widget
   */
  @Override
  public Object getHTTPFormValue() {
    return getSelectionAsString();
  }

  @Override
  public int getHeight() {
    return getContainerComponent().getHeight();
  }

  @Override
  public iPlatformIcon getIcon() {
    iPlatformComponent c = this.getDataComponent();

    if (c instanceof iActionComponent) {
      return ((iActionComponent) c).getIcon();
    }

    return displayIcon;
  }

  @Override
  public iPlatformIcon getIcon(SPOTPrintableString icon) {
    if ((icon == null) ||!icon.spot_hasValue()) {
      return null;
    }

    return getImageIconEx(icon, true);
  }

  /**
   * Resolves the specified string icon reference to an icon instance The string
   * may contain embedded variables or functions
   *
   * @param icon
   *          the string icon reference
   * @return the icon instance or null if the icon does not exist or cannot be
   *         loaded
   */
  public iPlatformIcon getIcon(String icon) {
    return getIcon(icon, null, null, PlatformHelper.getScaledImageDensity());
  }

  @Override
  public iPlatformIcon getIcon(String icon, String description) {
    return getIcon(icon, description, null, PlatformHelper.getScaledImageDensity());
  }

  @Override
  public iPlatformIcon getIcon(URL url, String description) {
    return getIcon(url, description, null, PlatformHelper.getScaledImageDensity());
  }

  public iPlatformIcon getIcon(String icon, String description, float density) {
    return getIcon(icon, description, null, density);
  }

  public iPlatformIcon getIcon(URL url, String description, float density) {
    return getIcon(url, description, null, density);
  }

  /**
   * Resolves the specified string icon reference to an icon instance The string
   * may contain embedded variables or functions
   *
   * @param icon
   *          the string icon reference
   * @param description
   *          the description for the icon
   * @param size
   *          the size for the icon. THe icon will be scaled accordingly
   * @param density
   *          the density of the icon (use zero if unknown)
   *
   * @return the icon instance or null if the icon does not exist or cannot be
   *         loaded
   */
  public iPlatformIcon getIcon(String icon, String description, UIDimension size, float density) {
    if ((icon == null) || (icon.length() == 0)) {
      return null;
    }

    if (icon.indexOf('=') != -1) {
      return UIImageHelper.createStateListIcon(icon, this);
    }

    if (icon.indexOf('{') != -1) {
      icon = expandString(icon, false);
    }

    UIImage img = UIImageHelper.getImage(this, icon, Platform.isUIThread(), size, null, density);

    return (img == null)
           ? null
           : new UIImageIcon(img, description);
  }

  /**
   * Get the image referenced by the specified URL
   *
   * @param url
   *          the URL
   * @param description
   *          the description for the icon
   * @param size
   *          the size for the icon. THe icon will be scaled accordingly
   * @param density
   *          the density of the icon (use zero if unknown)
   *
   * @return the icon instance or null if the icon does not exist or cannot be
   *         loaded
   */
  public iPlatformIcon getIcon(URL url, String description, UIDimension size, float density) {
    if (url == null) {
      return null;
    }

    UIImageIcon ic = null;

    try {
      ic = PlatformHelper.createIcon(url, description, Platform.isUIThread(), density);

      if (ic != null) {
        ic.isImageLoaded(this);
      }
    } catch(Exception e) {
      Platform.ignoreException(null, e);
    }

    return ic;
  }

  @Override
  public UIImage getImage(SPOTPrintableString image) {
    UIImageIcon icon = getImageIconEx(image, false);

    return (icon == null)
           ? null
           : icon.getImage();
  }

  @Override
  public UIImage getImage(String image) {
    return getImage(image, true, null, null, PlatformHelper.getUnscaledImageDensity());
  }

  @Override
  public UIImage getImage(URL url) {
    return getImage(url, true, null, null, PlatformHelper.getUnscaledImageDensity());
  }

  /**
   * Resolves the specified string image to an image instance The string may
   * contain embedded variables or functions
   *
   * @param image
   *          the string image for the image
   * @param deferred
   *          true to defer the loading of the image; false to load inline
   * @param size
   *          the size to scale the image to (can be null)
   * @param st
   *          the scaling type to use when scaling the image
   * @param density
   *          the density of the icon (use zero if unknown)
   *
   * @return the image instance or null if the image does not exist or cannot be
   *         loaded
   */
  public UIImage getImage(String image, boolean deferred, UIDimension size, ScalingType st, float density) {
    return UIImageHelper.getImage(this, image, deferred, size, st, density);
  }

  /**
   * Get the image referenced by the specified URL
   *
   * @param url
   *          the URL
   * @param deferred
   *          true to defer the loading of the image; false to load inline
   * @param size
   *          the size to scale the image to (can be null)
   * @param st
   *          the scaling type to use when scaling the image
   * @return the image instance or null if the image does not exist or cannot be
   *         loaded
   */
  public UIImage getImage(URL url, boolean deferred, UIDimension size, ScalingType st, float density) {
    return UIImageHelper.getImage(this, url, deferred, size, st, density);
  }

  /**
   * Convenience method that returns the value of the widget as a non-null
   * string
   *
   * @return the value of the widget or an empty string if the value is null
   */
  public String getInnerHTML() {
    return getInnerText();
  }

  /**
   * Convenience method that returns the value of the widget as a non-null
   * string
   *
   * @return the value of the widget or an empty string if the value is null
   */
  public String getInnerText() {
    Object value = getValue();

    return (value == null)
           ? ""
           : value.toString();
  }

  /**
   * Gets the action for the named keystroke
   *
   * @param name
   *          the name of the keystroke
   *
   * @return the associated action or null
   * @throws ApplicationException
   *           if the name of the keystroke is invalid
   */
  public UIAction getKeystrokeAction(String name) {
    String nostroke = getAppContext().getResourceAsString("Rare.runtime.text.noSuchKeystroke");

    throw new ApplicationException(PlatformHelper.format(nostroke, name));
  }

  /**
   * Gets the widgets linked data as a string. If the widget's link data has no
   * been explicitly set then the linked data associated with the widget's
   * current selection is returned
   *
   * @return the widgets linked data as a string.
   */
  public String getLinkedDataAsString() {
    Object o = getLinkedData();

    if (o != null) {
      return o.toString();
    }

    return getSelectionDataAsString();
  }

  /**
   * Gets the location of the widget within it's parent container
   *
   * @return the location of the widget within it's parent container
   */
  @Override
  public UIPoint getLocationInParent() {
    return getContainerComponent().getLocation();
  }

  /**
   * Gets the location of the widget on the screen
   *
   * @return the location of the widget on the screen
   */
  @Override
  public UIPoint getLocationOnScreen() {
    return getContainerComponent().getLocationOnScreen();
  }

  @Override
  public String getName() {
    return widgetName;
  }

  /**
   * Get height of an element relative to the layout (HTML compatibility)
   *
   * @return height of an element relative to the layout.
   */
  public int getOffsetHeight() {
    return getHeight();
  }

  /**
   * Get the number of pixels that the upper left corner of the widget is offset
   * from it parent (HTML compatibility).
   *
   * @return The number of pixels that the upper left corner of the widget is
   *         offset from it parent
   */
  public int getOffsetLeft() {
    return getContainerComponent().getX();
  }

  /**
   * Returns the widget's parent (HTML compatibility).
   *
   * @return the widget's parent
   */
  public iContainer getOffsetParent() {
    return getParent();
  }

  /**
   * Get the number of pixels that the upper top corner of the widget is offset
   * from it parent (HTML compatibility)
   *
   * @return The number of pixels that the upper top corner of the widget is
   *         offset from it parent
   */
  public int getOffsetTop() {
    return getContainerComponent().getY();
  }

  /**
   * Get width of an element relative to the layout (HTML compatibility)
   *
   * @return width of an element relative to the layout.
   */
  public int getOffsetWidth() {
    return getWidth();
  }

  /**
   * Gets the location for a component overlay. The location is calculated to be
   * left aligned
   *
   * @param top
   *          true for the overlay to be at the top of the component; false for
   *          at the bottom
   *
   * @return the location for a popup
   */
  public UIPoint getOverlayLocation(boolean top) {
    iPlatformComponent comp = getContainerComponent();
    UIPoint            loc  = comp.getLocationOnScreen();

    if (!top) {
      loc.y += comp.getHeight();
    }

    return loc;
  }

  @Override
  public iPlatformPainter getOverlayPainter() {
    return getOverlayPainter(false);
  }

  @Override
  public iContainer getParent() {
    return parentContainer;
  }

  /**
   * Returns the widget's parent (HTML compatibility).
   *
   * @return the widget's parent
   */
  public iContainer getParentElement() {
    return getParent();
  }

  @Override
  public String getPathName() {
    if (widgetName == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder(widgetName);
    iContainer    v  = getParent();

    while(v != null) {
      sb.insert(0, '/');
      sb.insert(0, v.getName());
      v = v.getParent();
    }

    sb.insert(0, '/');

    return sb.toString();
  }

  /**
   * Gets the location for a popup. The location is calculated to be left
   * aligned directly below this component
   *
   * @return the location for a popup
   */
  public UIPoint getPopupLocation() {
    iPlatformComponent comp = getContainerComponent();
    UIPoint            loc  = comp.getLocationOnScreen();

    loc.y += comp.getHeight();

    return loc;
  }

  /**
   * Gets the popup menu for the widget
   *
   * @return the widget's popup menu or null if it does not have one
   */
  public UIPopupMenu getPopupMenu() {
    if (popupMenu != null) {
      return popupMenu;
    }

    if ((menuSet != null)) {
      popupMenu = new UIPopupMenu(this, menuSet, showDefaultMenu);
      menuSet   = null;
    } else if (showDefaultMenu) {
      popupMenu = new UIPopupMenu(this, null, true);
    }

    return popupMenu;
  }

  /**
   * Gets the preferred size of the widget
   *
   * @return the preferred size of the widget
   */
  @Override
  public UIDimension getPreferredSize() {
    if (getContainerComponent() != null) {
      return getContainerComponent().getPreferredSize();
    }

    return new UIDimension(0, 0);
  }

  /**
   * Gets the preferred size of the widget given the maximum width
   *
   * @param maxWidth
   *          the maximum allowed width
   *
   * @return the preferred size of the widget
   */
  @Override
  public UIDimension getPreferredSize(int maxWidth) {
    UIDimension size = new UIDimension();

    getContainerComponent().getPreferredSize(size, maxWidth);

    return size;
  }

  public int getPromptWidth() {
    return promptWidth;
  }

  @Override
  public Reader getReader(String file) throws IOException {
    URL url = getURL(file);

    return getAppContext().getReader(url);
  }

  @Override
  public iPlatformComponent getRenderingComponent() {
    iPlatformComponent c = super.getRenderingComponent();

    if (c != null) {
      return c;
    }

    iPlatformComponent cc = this.getContainerComponent();

    if (cc instanceof iPlatformComponent) {
      return cc;
    }

    return null;
  }

  /**
   * Get the number of pixels that the upper left corner of the widget is offset
   * from the left edge of the screen
   *
   * @return The number of pixels that the upper left corner of the widget is
   *         offset the left edge of the screen
   */
  @Override
  public int getScreenLeft() {
    return getContainerComponent().getLocationOnScreen().intX();
  }

  /**
   * Get the number of pixels that the upper left corner of the widget is offset
   * from the top edge of the screen
   *
   * @return The number of pixels that the upper left corner of the widget is
   *         offset the top edge of the screen
   */
  @Override
  public int getScreenTop() {
    return getContainerComponent().getLocationOnScreen().intY();
  }

  @Override
  public iScriptHandler getScriptHandler() {
    return getWindow().getScriptHandler();
  }

  @Override
  public WidgetContext getScriptingContext() {
    if (scriptingContex == null) {
      scriptingContex = getScriptHandler().createScriptingContext(this);
    }

    return scriptingContex;
  }

  @Override
  public abstract Object getSelection();

  @Override
  public String getSelectionAsString() {
    Object o = getSelection();

    if (o instanceof Object[]) {
      return Helper.toString((Object[]) o, "\t");
    }

    return (o == null)
           ? null
           : o.toString();
  }

  @Override
  public Object getSelectionData() {
    Object o = getSelection();

    if (o instanceof RenderableDataItem) {
      return ((RenderableDataItem) o).getLinkedData();
    }

    return null;
  }

  @Override
  public String getSelectionDataAsString() {
    Object o = getSelectionData();

    if (o instanceof Object[]) {
      return Helper.toString((Object[]) o, "\t");
    }

    return (o == null)
           ? null
           : o.toString();
  }

  /**
   * Gets the size of the widget
   *
   * @return the size of the widget
   */
  public UIDimension getSize() {
    return getContainerComponent().getSize();
  }

  @Override
  public InputStream getStream(String file) throws IOException {
    return getAppContext().openConnection(getURL(file)).getInputStream();
  }

  @Override
  public iTabDocument getTabDocument() {
    iPlatformComponent c = getContainerComponent();

    while(c != null) {
      if (c instanceof iTabDocument) {
        return (iTabDocument) c;
      }

      c = c.getParent();
    }

    return null;
  }

  @Override
  public int getTabIndex() {
    iPlatformComponent c = getContainerComponent();

    while(c != null) {
      if (c instanceof iTabDocument) {
        return ((iTabDocument) c).getTabIndex();
      }

      c = c.getParent();
    }

    return -1;
  }

  @Override
  public iTabPaneViewer getTabPaneViewer() {
    iPlatformComponent c = getContainerComponent();

    while(c != null) {
      if (c instanceof iTabDocument) {
        return ((iTabDocument) c).getTabPaneViewer();
      }

      c = c.getParent();
    }

    return null;
  }

  @Override
  public String getTextDirection() {
    return "ltr";
  }

  @Override
  public String getTitle() {
    return widgetTitle;
  }

  @Override
  public iPlatformComponent getTitleLabel() {
    return titleLabel;
  }

  @Override
  public Object getTransferData(TransferFlavor flavor, iTransferable transferable) {
    if (flavor.isStringFlavor()) {
      return getSelectionAsString();
    } else if (flavor.isWidgetFlavor()) {
      return this;
    } else if (flavor.isItemFlavor()) {
      return this.getSelection();
    }

    return null;
  }

  @Override
  public int getType() {
    return RenderableDataItem.TYPE_WIDGET;
  }

  /**
   * Get the native window object that hosts the widget
   *
   * @return the native window object that hosts the widget
   */
  @Override
  public Object getUIWindow() {
    iContainer p = getParent();

    if (p != null) {
      return p.getUIWindow();
    }

    return getAppContext().getWindowViewer().getUIWindow();
  }

  @Override
  public URL getURL(String url) throws MalformedURLException {
    return getAppContext().createURL(this, expandString(url, false));
  }

  @Override
  public iURLResolver getURLResolver() {
    return this;
  }

  /**
   * Resolves the specified string into a URL and returns the URL's content as a
   * string
   *
   * @param url
   *          the string to resolve into a URL
   *
   * @param charSet
   * @return the URL's content as a string
   */
  public String getURLText(String url, String charSet) {
    if (url == null) {
      return null;
    }

    if (url.startsWith(iConstants.RESOURCE_PREFIX)) {
      return getAppContext().getResourceAsString(url.substring(9));
    }

    iURLConnection conn = null;

    try {
      URL u = getURL(url);

      conn = getAppContext().openConnection(u);

      if (charSet != null) {
        conn.setDefaultCharset(charSet);
      }

      return conn.getContentAsString();
    } catch(Exception ex) {
      return null;
    } finally {
      if (conn != null) {
        conn.dispose();
      }
    }
  }

  @Override
  public Object getValue() {
    Object o = getSelection();

    if (!selectAllAllowed) {
      if (o instanceof RenderableDataItem) {
        o = ((RenderableDataItem) o).getValue();
      }
    } else if (o instanceof RenderableDataItem[]) {
      RenderableDataItem[] a   = (RenderableDataItem[]) o;
      int                  len = a.length;
      Object[]             ao  = new Object[len];
      RenderableDataItem   item;

      for (int i = 0; i < len; i++) {
        item  = a[i];    // added thei sit so J2Objc will work with it;
        ao[i] = item.getValue();
      }

      o = ao;
    }

    return o;
  }

  /**
   * Gets the value of the widget. The value is converted to a number and that
   * number is returned. A null value or a value that that cannot be represented
   * as a number is returned as zero (0). This method will not throw a
   * NumberFormatException
   *
   * @return a <code>double</code> representing the widgets value
   */
  public double getValueAsDouble() {
    Object o = getValue();

    if (o instanceof RenderableDataItem) {
      o = ((RenderableDataItem) o).getValue();

      if (o instanceof Number) {
        return ((Number) o).doubleValue();
      }
    }

    return (o == null)
           ? 0
           : SNumber.doubleValue(o.toString());
  }

  /**
   * Gets the value of the widget. The value is converted to a number and that
   * number is returned. A null value or a value that that cannot be represented
   * as a number is returned as zero (0). This method will not throw a
   * NumberFormatException
   *
   * @return a <code>int</code> representing the widgets value
   */
  public int getValueAsInt() {
    Object o = getValue();

    if (o instanceof RenderableDataItem) {
      o = ((RenderableDataItem) o).getValue();

      if (o instanceof Number) {
        return ((Number) o).intValue();
      }
    }

    return (o == null)
           ? 0
           : SNumber.intValue(o.toString());
  }

  @Override
  public String getValueAsString() {
    Object o = getValue();

    return (o == null)
           ? null
           : o.toString();
  }

  @Override
  public iViewer getViewer() {
    return getParent();
  }

  @Override
  public int getWidgetCount() {
    return 0;
  }

  /**
   * Gets the widget that specified component belongs to
   *
   * @param component
   *          the component
   *
   * @return the widget that specified component belongs to
   */
  public static iWidget getWidgetForComponent(iPlatformComponent component) {
    if (component != null) {
      return (iWidget) component.getClientProperty(iConstants.RARE_WIDGET_COMPONENT_PROPERTY);
    }

    return null;
  }

  /**
   * Gets the widget listener
   *
   * @return the widget listener
   */
  public aWidgetListener getWidgetListener() {
    return widgetListener;
  }

  @Override
  public WidgetType getWidgetType() {
    return widgetType;
  }

  @Override
  public int getWidth() {
    if (getContainerComponent() != null) {
      return getContainerComponent().getWidth();
    }

    return 0;
  }

  @Override
  public WindowViewer getWindow() {
    // check with parent first in case the parent window is set but the
    // component has not been added to the hierarchy
    iContainer p = getParent();

    if (p instanceof WindowViewer) {
      return (WindowViewer) p;
    }

    if (p != null) {
      return p.getWindow();
    }

    return getAppContext().getWindowViewer();
  }

  @Override
  public boolean hasPopupMenu() {
    if (popupMenu != null) {
      return true;
    }

    return (menuSet != null) || showDefaultMenu;
  }

  @Override
  public boolean hasSelection() {
    return getSelection() != null;
  }

  @Override
  public boolean hasValue() {
    return getValue() != null;
  }

  @Override
  public boolean isAllowContainerToDisable() {
    return allowContainerToDisable;
  }

  @Override
  public boolean isDesignMode() {
    return false;
  }

  @Override
  public boolean isDisposed() {
    return disposed;
  }

  @Override
  public boolean isEnabled() {
    return !disposed && getContainerComponent().isEnabled() && getDataComponent().isEnabled();
  }

  @Override
  public boolean isEventEnabled(String event) {
    aWidgetListener l = getWidgetListener();

    if (l == null) {
      return false;
    }

    return l.isEnabled(getEventName(event));
  }

  @Override
  public boolean isFocusPainted() {
    return getContainerComponent().isFocusPainted();
  }

  public boolean isFocusableInCurrentState() {
    return dataComponent.isEnabled() && dataComponent.isVisible() && dataComponent.isFocusable();
  }

  @Override
  public boolean isMultipartContent() {
    return false;
  }

  /**
   * Returns whether the widget is required to have a value in order to be valid
   * for submission
   *
   * @return true if the widget is required to have a value in order to be valid
   *         for submission; false otherwise
   */
  @Override
  public boolean isRequired() {
    return required;
  }

  @Override
  public boolean isSelectable() {
    return getDataComponent().isSelectable();
  }

  @Override
  public boolean isSelected() {
    return getDataComponent().isSelected();
  }

  @Override
  public boolean isShowing() {
    return !disposed && getContainerComponent().isShowing();
  }

  @Override
  public boolean isSizeLocked() {
    return false;
  }

  @Override
  public boolean isSubmittable() {
    return isSubmittable && (getName() != null) && getDataComponent().isShowing();
  }

  @Override
  public boolean isTextDirectionSet() {
    return false;
  }

  @Override
  public boolean isType(String type) {
    return getWidgetType().toString().equalsIgnoreCase(type);
  }

  @Override
  public boolean isValidForSubmission(boolean showerror) {
    return true;
  }

  @Override
  public boolean isViewer() {
    return false;
  }

  @Override
  public boolean isVisible() {
    return !disposed && getContainerComponent().isVisible();
  }

  /**
   * Adds the specified item to this widget expanded item set. This method is
   * called when an item is created from a DataItem configuration object
   *
   * @param item
   *          the item to add
   */
  protected void addExpanded(RenderableDataItem item) {
    if (itemAttributes == null) {
      itemAttributes = new ItemAttributes();
    }

    itemAttributes.addExpanded(item);
  }

  /**
   * Adds the specified item to this widget selected item set. This method is
   * called when an item is created from a DataItem configuration object
   *
   * @param item
   *          the item to add
   */
  protected void addSelected(RenderableDataItem item) {
    if (itemAttributes == null) {
      itemAttributes = new ItemAttributes();
    }

    itemAttributes.addSelected(item);
  }

  /**
   * Configures the widget from the specified configuration. As part to the
   * configuration it may be necessary to embed the existing from component in
   * another component. If that happens, this method will return the component
   * that the form component was embedded in. The caller is responsible for
   * replacing their existing form component with the returned component.
   * <p>
   * This method does not configure the widget's size nor does it handle drag
   * and drop. If also does no fire the onConfigure event.
   * <p>
   * If the widget does not override the <code>getContainer()</code> and
   * <B>getDataComponent()</B> methods then the <code>dataComponent</code> and
   * <code>formComponent</code> fields must be set before this method is called.
   * <p>
   * The typical configuration sequence is:
   * <ul>
   * <li>Call the component factory to create the component
   * <li>Set the dataComponent and formComponent fields (e.g. via
   * <code>setComponents()</code> )
   * <li>Call this method to perform the standard configuration. If this method
   * returns a value then set the formComponent variable to the returned value
   * <li>Configure the widgets size (e.g. via a call to
   * <code>configureSize()</code>)
   * <li>Configure D&amp;D if necessary
   * <li>Call <code>fireConfigureEvent()</code> to fire the configuration event
   * </ul>
   *
   * @param cfg
   *          the configuration
   * @param border
   *          true to configure the border; false otherwise
   * @param textMenus
   *          true to create text menus (cut/copy/paste/) for the widget; false
   *          otherwise
   * @param margin
   *          true to configure the widget's margin; false otherwise
   * @param fire_created
   *          true to fire the onCreated event; false otherwise
   *
   */
  protected void configure(Widget cfg, boolean border, boolean textMenus, boolean margin, boolean fire_created) {
    iPlatformComponent  panel = null;
    iPlatformComponent  comp  = getDataComponent();
    iPlatformComponent  fcomp = getContainerComponent();
    iPlatformAppContext app   = getAppContext();

    if (isDesignMode()) {
      this.setLinkedData(cfg);
    }

    registerWithWidget(comp);

    if (fcomp != comp) {
      registerWithWidget(fcomp);
    }

    if (fire_created) {
      this.fireConfigureEvent(cfg, iConstants.EVENT_CREATED);
    }

    if (cfg.submitable.spot_valueWasSet()) {
      isSubmittable = cfg.submitable.booleanValue();
    }

    String s;

    if (widgetName == null) {    // will not override a set value
      widgetName = cfg.name.getValue();

      if ((widgetName == null) || (widgetName.length() == 0)) {
        widgetName    = Utils.makeWidgetName(cfg, this);
        isSubmittable = false;
      }
    }

    if (cfg.customProperties.spot_getLinkedData() instanceof Map) {    // was
      // parsed
      // prior to
      // this call
      handleCustomProperties(cfg, (Map) cfg.customProperties.spot_getLinkedData());
      cfg.customProperties.spot_setLinkedData(null);
    } else {
      s = cfg.customProperties.getValue();

      if ((s != null) && (s.length() > 0)) {
        handleCustomProperties(cfg, DataParser.parseNameValuePairs(cfg.customProperties));
      }
    }

    // Setup the event handler and then fire the onCreated event
    iScriptHandler sh  = getScriptHandler();
    Map            map = isDesignMode()
                         ? null
                         : aWidgetListener.createEventMap(cfg, widgetName, sh);

    if (map != null) {
      setWidgetListener(createWidgetListener(this, map, getScriptHandler()));
    }

    if (cfg.copyingAllowed.spot_valueWasSet()) {
      copyingAllowed = cfg.copyingAllowed.booleanValue();
    }

    if (cfg.pastingAllowed.spot_valueWasSet()) {
      pastingAllowed = cfg.pastingAllowed.booleanValue();
    }

    if (cfg.deletingAllowed.spot_valueWasSet()) {
      deletingAllowed = cfg.deletingAllowed.booleanValue();
    }

    required = cfg.required.booleanValue();

    if (cfg.draggingAllowed.spot_valueWasSet()) {
      draggingAllowed = cfg.draggingAllowed.booleanValue();
    } else {
      draggingAllowed = app.areAllWidgetsDraggable();
    }

    dropMode = cfg.dropTracking.intValue();

    if (!cfg.focusable.booleanValue()) {
      setFocusable(false);
    } else if (cfg.focusable.spot_valueWasSet()) {
      setFocusable(true);
    }

    UIFont f = null;

    if (cfg.font.spot_valueWasSet()) {
      f = UIFontHelper.getFont(this, cfg.font);
    }

    if (f != null) {
      comp.setFont(f);

      if (comp != fcomp) {
        fcomp.setFont(f);
      }
    }

    UIColor c = null;

    s = cfg.fgColor.getValue();

    if ((s != null) && (s.length() > 0)) {
      c = UIColorHelper.getColor(s);
    }

    if (c != null) {
      comp.setForeground(c);

      if (comp != fcomp) {
        fcomp.setForeground(c);
      }
    }

    if (widgetTitle == null) {    // will not override a set value
      widgetTitle = cfg.title.getValue();

      if (widgetTitle == null) {
        widgetTitle = "";
      }
    }

    int titleDisplay = cfg.titleLocation.intValue();

    if (titleDisplay == Widget.CTitleLocation.auto) {
      titleDisplay = getDefaultTitleLocation();
    }

    boolean lockable = "true".equalsIgnoreCase(cfg.spot_getAttribute("lockable"));

    if (border) {
      Margin          m  = margin
                           ? cfg.getContentPadding()
                           : null;
      iPlatformBorder mb = (m == null)
                           ? null
                           : m.getBorder();

      if (titleDisplay == Widget.CTitleLocation.center_left) {
        panel = BorderUtils.setBorderType(this, fcomp, cfg.getBorders(), null, -1, mb, lockable);
      } else {
        panel = BorderUtils.setBorderType(this, fcomp, cfg.getBorders(), widgetTitle, titleDisplay, mb, lockable);
      }

      if (panel == fcomp) {
        panel = null;
      } else {
        this.registerWithWidget(panel);
        formComponent = fcomp = panel;
      }
    }

    // begin-setup background color
    s = cfg.bgColor.getValue();

    if ((s != null) && (s.length() > 0)) {
      try {
        if (s.equals("transparent")) {
          comp.setOpaque(false);

          if (fcomp != comp) {
            fcomp.setOpaque(false);
          }
        } else {
          ColorUtils.configureBackgroundPainter(this, cfg.bgColor);
        }
      } catch(IllegalArgumentException e) {
        Platform.ignoreException("configureColor", e);
      }
    }

    // end-setup background color
    if (cfg.bgImageURL.spot_valueWasSet()) {
      ObjectHolder     oh = (ObjectHolder) cfg.bgImageURL.spot_getLinkedData();
      iPlatformPainter ip = null;

      if (oh != null) {
        cfg.bgImageURL.spot_setLinkedData(null);

        if (oh.value != null) {
          setOverlayPainter(ip = (iPlatformPainter) oh.value, true);
          oh.value = null;
        }
      } else {
        setOverlayPainter(ip = Utils.configureImage(this, null, cfg.bgImageURL, false), true);
      }

      if (ip != null) {
        ip.setDisposable(true);
      }
    }

    if (cfg.overlayImageURL.spot_valueWasSet()) {
      ObjectHolder oh = (ObjectHolder) cfg.overlayImageURL.spot_getLinkedData();

      if (oh != null) {
        if (oh.value != null) {
          setOverlayPainter((iPlatformPainter) oh.value, false);
          oh.value = null;
        }
      } else {
        setOverlayPainter(Utils.configureImage(this, null, cfg.overlayImageURL, false), false);
      }
    }

    if (cfg.cursorName.spot_valueWasSet()) {
      setCursorName(cfg.cursorName.getValue());
    }

    if (!cfg.enabled.booleanValue()) {
      setEnabled(false);
    }

    if (!cfg.visible.booleanValue()) {
      setVisible(false);
    }

    Link link = cfg.getActionLink();

    if (link != null) {
      setActionListener(createActionLink(link));
    }

    if (cfg.focusPainted.spot_valueWasSet()) {
      setFocusPainted(cfg.focusPainted.booleanValue());
    }

    configureMenus(comp, cfg, textMenus);
    this.initializeListeners(getWidgetListener());

    iWidgetCustomizer customizer = app.getWidgetCustomizer();

    if (customizer != null) {
      iParentComponent cp = customizer.widgetConfigured(this, cfg);

      if (cp != null) {
        cp.add(fcomp);
        formComponent = fcomp = cp;
      }
    }

    if (getContainerComponent().isFocusPainted()) {
      getComponentPainter(true, true);    // create a component painter if
      // necessary to paint focus
    }

    configureSize(getContainerComponent(), cfg.bounds);
  }

  /**
   * Configures supported actions for a widget
   *
   * @param comp
   *          the component to enable the actions for
   * @param actions
   *          the set of actions
   */
  protected void configureActions(iPlatformComponent comp, SPOTSet actions) {}

  /**
   * Configures the widget for generic drag and drop support
   *
   * @param comp
   *          the component to configure
   * @param cfg
   *          the configuration to use
   */
  protected void configureGenericDnD(iPlatformComponent comp, Widget cfg) {
    if ((cfg != null) && cfg.dropTracking.spot_valueWasSet()) {
      droppingAllowed = dropMode != Widget.CDropTracking.none;
    }

    if (draggingAllowed) {
      PlatformHelper.configureDragging(this, comp);
    }

    if (draggingAllowed || pastingAllowed || droppingAllowed || copyingAllowed) {
      PlatformHelper.configureDropTracking(this, comp, dropMode);
    }

    registerCutCopyPaste(comp);
  }

  /**
   * Configures the popup menu for a widget
   *
   * @param comp
   *          the component to attach the menu to
   * @param cfg
   *          the widget configuration
   * @param textMenus
   *          true to create text menus (cut/copy/paste/) for the widget; false
   *          otherwise
   */
  protected abstract void configureMenus(iPlatformComponent comp, Widget cfg, boolean textMenus);

  /**
   * Configures a widgets size based on it configuration
   *
   * @param bounds
   *          the bounds configuration
   */
  protected void configureSize(iPlatformComponent c, Rectangle bounds) {
    setPreferredSize(c, bounds.width.getValue(), bounds.height.getValue(), false);
    setMinimumSize(c, bounds.width.spot_getAttribute("min"), bounds.height.spot_getAttribute("min"), false);
  }

  /**
   * Creates an <code>ActionLink</code> from a Link configuration object
   *
   * @param link
   *          the link configuration
   *
   * @return an <code>ActionLink</code> from a Link configuration object
   */
  protected ActionLink createActionLink(Link link) {
    return new ActionLink(this, link);
  }

  /**
   * Creates the widget's import and export data flavors from the specified
   * configuration
   *
   * @param cfg
   *          the widget's configuration
   */
  protected void createDataFlavors(Widget cfg) {
    SPOTSet set = cfg.getImportDataFlavors();
    int     len = (set == null)
                  ? 0
                  : set.getCount();

    if (len > 0) {
      importDataFlavors = new String[len];

      for (int i = 0; i < len; i++) {
        importDataFlavors[i] = set.stringValueAt(i);

        if (importDataFlavors[i].equals("file")) {
          fileDroppingAllowed = true;
        }

        if (importDataFlavors[i].equals("url")) {
          urlDroppingAllowed = true;
        }
      }
    }

    set = cfg.getExportDataFlavors();
    len = (set == null)
          ? 0
          : set.getCount();

    if (len > 0) {
      exportDataFlavors = new String[len];

      String s;
      int    n;

      for (int i = 0; i < len; i++) {
        s = set.stringValueAt(i);
        n = s.indexOf('=');

        if (n == -1) {
          exportDataFlavors[i] = s;
        } else {
          exportDataFlavors[i] = s.substring(0, n);
          s                    = s.substring(n + 1);

          if (s.length() > 0) {
            if (flavorFunctions == null) {
              flavorFunctions = new HashMap<String, Object>();
            }

            flavorFunctions.put(exportDataFlavors[i], s);
          }
        }
      }
    }
  }

  /**
   * Creates a worker task for loading the widgets data
   *
   * @param link
   *          the action link
   * @param updateStatus
   *          true to call the async load status handler when the task completes
   *          or an error occurs; false otherwise
   *
   * @return the worker task
   */
  protected Runnable createDataLoadRunnable(final ActionLink link, final boolean updateStatus) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        if (!isDisposed()) {
          try {
            handleActionLink(link, false);

            if (updateStatus) {
              loadCompleted(link);
            }
          } catch(Exception ex) {
            errorOccured(link, ex, updateStatus);
          }
        }
      }
    };

    return task;
  }

  /**
   * Creates a worker task for loading the widgets data and invokes the
   * startedLoading() method
   *
   * @param link
   *          the action link
   * @param updateStatus
   *          true to call the async load status handler when the task completes
   *          or an error occurs; false otherwise
   * @return the worker task
   */
  protected iWorkerTask createDataLoadTask(final ActionLink link, final boolean updateStatus) {
    iWorkerTask task = new iWorkerTask() {
      volatile boolean canceled;
      private boolean  done;
      @Override
      public Object compute() {
        if (!canceled &&!isDisposed()) {
          done = false;

          try {
            handleActionLink(link, true);

            if (updateStatus) {
              loadCompleted(link);
            }
          } catch(Exception ex) {
            errorOccured(link, ex, updateStatus);
          } finally {
            done = true;
          }
        }

        return null;
      }
      @Override
      public void finish(Object result) {
        if (!isDisposed()) {
          finishedLoading();
        }
      }
      @Override
      public void cancel(boolean canInterrupt) {
        canceled = true;

        if (canInterrupt) {
          try {
            link.close();
          } catch(Exception ignore) {}
        }
      }
      @Override
      public boolean isCanceled() {
        return canceled;
      }
      @Override
      public boolean isDone() {
        return done || canceled;
      }
    };

    startedLoading();

    return task;
  }

  protected abstract aWidgetListener createWidgetListener(iWidget widget, Map map, iScriptHandler scriptHandler);

  protected void errorOccured(final ActionLink link, final Throwable error, final boolean updateStatus) {
    if (!Platform.isUIThread()) {
      Runnable r = new Runnable() {
        @Override
        public void run() {
          if (isDisposed()) {
            return;
          }

          if (updateStatus) {
            getAppContext().getAsyncLoadStatusHandler().errorOccured(aWidget.this, link, error);
          }

          handleException(error);
        }
      };

      Platform.invokeLater(r);
    } else {
      if (updateStatus) {
        getAppContext().getAsyncLoadStatusHandler().errorOccured(aWidget.this, link, error);
      }

      handleException(error);
    }
  }

  protected void expandStringEx(String value, boolean encode, Writer writer, int n) throws IOException {
    if (n == -1) {
      if (encode) {
        URLEncoder.encode(value, "ISO-8859-1", writer);
      } else {
        writer.write(value);
      }

      return;
    }

    char        a[] = value.toCharArray();
    int         len = a.length;
    CharScanner sc  = getScanner();

    sc.reset(a, 0, len, false);

    String s;
    Object o;
    int    p = 0;

    do {
      n = n - p;
      writer.write(a, p, n);
      sc.consume(n + 1);
      p = sc.getPosition();
      n = sc.indexOf('}', true, true);

      if (n == -1) {
        break;
      }

      n = n - p;
      s = sc.getString(p, n);
      o = getAttribute(s);
      sc.reset(a, 0, len, false);    // reset because getAttribute() can reuse the
      // scanned
      sc.consume(p);

      if (o != null) {
        s = o.toString();

        if (encode) {
          try {
            URLEncoder.encode(s, "ISO-8859-1", writer);
          } catch(IOException e) {
            Platform.ignoreException(null, e);
          }
        } else {
          writer.write(s);
        }
      }

      sc.consume(n + 1);
      p = sc.getPosition();
      n = sc.indexOf('{', false, false);
    } while(n != -1);

    n = sc.getLength();

    if (n > 0) {
      writer.write(a, sc.getRelPosition(), n);
    }
  }

  protected void finishedLoadingEx() {
    if (isDisposed()) {
      return;
    }

    aWidgetListener wl = getWidgetListener();

    if ((wl != null) && wl.isEnabled(iConstants.EVENT_FINISHED_LOADING)) {
      EventObject e = new EventObject(this);

      wl.evaluate(iConstants.EVENT_FINISHED_LOADING, e, false);
    }
  }

  /**
   * Fires a onConfigure event if there is a handler registered
   *
   * @param cfg
   *          the configuration that the event is being fired for
   * @param event
   *          the event name
   */
  protected void fireConfigureEvent(Widget cfg, String event) {
    if (isDesignMode()) {
      return;
    }

    if (event.equals(iConstants.EVENT_CREATED)) {
      String code = cfg.spot_getAttribute("onCreated");

      if ((code != null) && (code.length() > 0)) {
        DataEvent e = new DataEvent(this, cfg);

        aWidgetListener.evaluate(this, this.getScriptHandler(), code, e);
      }
    } else {
      aWidgetListener wl = getWidgetListener();

      if ((wl != null) && wl.isEnabled(event)) {
        DataEvent e = new DataEvent(this, cfg);

        // fire in line to assure onConfigure is called before widgets for child
        // widgets
        wl.evaluate(event, e, false);
      }
    }
  }

  protected void fireEventEx(aWidgetListener wl, String eventName, EventObject event, boolean inline) {
    eventName = getEventName(eventName);

    if ((wl == null) || (eventName == null) || (event == null)) {
      return;
    }

    if (inline) {
      wl.evaluate(eventName, event, false);
    } else {
      wl.execute(eventName, event);
    }
  }

  /**
   * Called by WidgetFocusListener so a widget can track it's focus internally
   *
   * @param e
   *          the focus event
   */
  protected void focusEvent(FocusEvent e) {}

  /**
   * Load the data referenced by the specified collection. For list type widgets
   * (lists, trees, tables, etc), the data will be appended to any existing data
   *
   * @param c
   *          the collection
   * @param skip
   *          the number of initial items to skip
   */
  protected void handleCollectionEx(Collection<RenderableDataItem> c, int skip) {
    if (isDesignMode()) {
      return;
    }

    if (c != null) {
      Iterator<RenderableDataItem> it = c.iterator();

      while((skip > 0) && it.hasNext()) {
        it.next();
        skip--;
      }

      while(it.hasNext()) {
        addParsedRow(it.next());
      }
    }
  }

  /**
   * Handles widget custom properties. The default behavior is to just attach
   * the properties to this widget
   *
   * @param cfg
   *          the configuration
   * @param properties
   *          the properties
   */
  protected void handleCustomProperties(Widget cfg, Map<String, Object> properties) {
    Platform.handlePlatformProperties(this, cfg, properties);

    if (getCustomProperties() == null) {
      setCustomProperties(properties);
    } else {
      getCustomProperties().putAll(properties);
    }
  }

  /**
   * Extracts a data url for the specified configuration. If a valid URL exists
   * an <code>ActionLink</code> is created and passed to
   * <code>setActionLink</code>
   *
   * @param cfg
   *          the configuration
   * @see #setDataLink
   */
  protected void handleDataURL(Widget cfg) {
    if ((cfg == null) || (cfg.dataURL.getValue() == null)) {
      return;
    }

    if (!Platform.isUIThread()) {
      handleDataURL(cfg, false);

      return;
    }

    boolean deferred = true;
    String  s        = cfg.dataURL.spot_getAttribute("deferred");

    if (s != null) {
      deferred = !s.equalsIgnoreCase("false") || Platform.isUIThread();
    } else {
      s = cfg.dataURL.spot_getAttribute("inline");

      if ("true".equals(s)) {
        deferred = false;
      }
    }

    handleDataURL(cfg, deferred);
  }

  /**
   * Extracts a data url for the specified configuration. If a valid URL exists
   * an <code>ActionLink</code> is created and passed to
   * <code>setActionLink</code>
   *
   * @param cfg
   *          the configuration
   * @param deferred
   *          true to defer loading of the link; false otherwise
   *
   * @see #setDataLink
   */
  protected void handleDataURL(Widget cfg, boolean deferred) {
    if (isDesignMode() || (cfg == null) || (cfg.dataURL.getValue() == null)) {
      finishedLoading();

      return;
    }

    sourceURL = null;

    ActionLink ref = ActionLink.getActionLink(this, cfg.dataURL, 0);

    startedParsing();

    if (deferred) {
      loadStarted(ref);
      getAppContext().executeWorkerTask(this.createDataLoadTask(ref, true));
    } else {
      handleActionLink(ref, false);
    }
  }

  /**
   * Static method to handle items that were flagged as selected during parsing
   *
   * @param comp
   *          the list component the selections are for
   * @param at
   *          the set of items
   */
  protected static void handleSelections(iListHandler comp, ItemAttributes at) {
    if (at == null) {
      return;
    }

    int index;

    if (at.select != null) {
      index = comp.indexOf(at.select);

      if (index != -1) {
        comp.setSelectedIndex(index);
      }
    }

    ArrayList<RenderableDataItem> list = at.selected;
    int                           len  = (list == null)
            ? 0
            : list.size();

    for (int i = 0; i < len; i++) {
      index = comp.indexOf(list.get(i));

      if (index != -1) {
        comp.setSelectedIndex(index);
      }
    }
  }

  /**
   * Handles data import when a drop event handler is not installed. The default
   * is not to import any data
   *
   * @param t
   *          the transferable
   * @param drop
   *          the drop information
   *
   * @return true if data was imported; false otherwise
   */
  protected boolean importDataEx(final iTransferable t, final DropInformation drop) {
    return false;
  }

  /**
   * Handles the importing of a URL list of URLS* that was dropped on the
   * component. The default is to call the setValue method with the string
   * representation of the first URL
   *
   * @param urls
   *          the urls to import
   * @param drop
   *          the drop information
   *
   * @return true if data was imported; false otherwise
   */
  protected boolean importURL(List<URL> urls, DropInformation drop) {
    String val = null;

    if ((urls != null) && (!urls.isEmpty())) {
      URL u = urls.get(0);

      val = JavaURLConnection.toExternalForm(u);
    }

    if (val != null) {
      setValue(val);
    }

    return val != null;
  }

  /**
   * Called by the configure method to initialize listeners for the widget
   *
   * @param listener
   *          the widget listener (could be null if there are no registered
   *          event handlers)
   */
  protected abstract void initializeListeners(aWidgetListener listener);

  protected void loadCompleted(final ActionLink link) {
    if (!Platform.isUIThread()) {
      Runnable r = new Runnable() {
        @Override
        public void run() {
          if (isDisposed()) {
            return;
          }

          getAppContext().getAsyncLoadStatusHandler().loadCompleted(aWidget.this, link);
        }
      };

      Platform.invokeLater(r);
    } else {
      getAppContext().getAsyncLoadStatusHandler().loadCompleted(aWidget.this, link);
    }
  }

  protected void loadStarted(final ActionLink link) {
    getAppContext().getAsyncLoadStatusHandler().loadStarted(aWidget.this, link, null);
  }

  protected void popupMenuClosing(UIPopupMenu menu) {}

  /**
   * Registers a widget's component for cut, copy, and paste functionality
   *
   * @param comp
   *          the component to register
   */
  protected void registerCutCopyPaste(iPlatformComponent comp) {
    if (copyingAllowed || pastingAllowed || selectAllAllowed || deletingAllowed) {
      ActionHelper.registerCutCopyPasteBindings(comp);
      ActionHelper.setCopyEnabled(comp, this.copyingAllowed);
      ActionHelper.setCutEnabled(comp, this.deletingAllowed);
      ActionHelper.setPasteEnabled(comp, this.pastingAllowed);
      ActionHelper.setSelectAllEnabled(comp, this.selectAllAllowed);
    }
  }

  /**
   * Registers the component as belonging to this widget
   *
   * @param component
   *          the component to register
   */
  protected void registerWithWidget(iPlatformComponent component) {
    component.setWidget(this);
  }

  /**
   * Uninitializes listeners
   *
   * @param listener
   *          the widget listener
   */
  protected void uninitializeListeners(aWidgetListener listener) {}

  /**
   * This method handles the actually updating of the widget and is meant to be
   * called on the AWT thread Sub-classes should override this method instead of
   * update()
   */
  protected void updateEx() {
    iPlatformComponent c  = getContainerComponent();
    iPlatformComponent dc = getDataComponent();

    if ((dc == null) || (c == null)) {
      return;
    }

    c.revalidate();
    c.repaint();
  }

  protected void setOverlayPainter(iPlatformPainter painter, boolean background) {
    iPainterSupport ps = getPainterSupport(background);

    if (ps != null) {
      iPlatformComponentPainter cp = ps.getComponentPainter();

      if (cp == null) {
        cp = new UIComponentPainter();
        ps.setComponentPainter(cp);
      }

      if (background) {
        cp.setBackgroundOverlayPainter(painter);
      } else {
        cp.setOverlayPainter(painter);
      }

      getContainerComponent().repaint();
    }
  }

  /**
   * Extended get attribute method that sub classes can override
   *
   * @param firstChar
   *          the first character of the key
   * @param key
   *          the key
   *
   * @return the value
   */
  protected Object getAttributeEx(char firstChar, String key) {
    if (firstChar == '/') {
      int n = key.indexOf('/', 1);

      if (n != -1) {
        iViewer v = getAppContext().getViewer(key.substring(1, n));

        if (v != null) {
          return v.getAttribute(key.substring(n + 1));
        }
      }
    }

    int n = key.indexOf('/', 1);

    if (n != -1) {
      iViewer v = getAppContext().getViewer(key.substring(0, n));

      if (v != null) {
        return v.getAttribute(key.substring(n + 1));
      }
    }

    return null;
  }

  /**
   * Gets the component painter for the widget
   *
   * @param create
   *          true to create one if none exists; false to return null if none
   *          exists
   * @param outer
   *          true to check the container first; false to check the data
   *          component first
   *
   * @return the component painter or null;
   */
  protected iPlatformComponentPainter getComponentPainter(boolean create, boolean outer) {
    iPainterSupport ps = getPainterSupport(outer);

    if (ps != null) {
      iPlatformComponentPainter cp = ps.getComponentPainter();

      if ((cp == null) && create) {
        cp = new UIComponentPainter();
        ps.setComponentPainter(cp);
      }

      return cp;
    }

    return null;
  }

  /**
   * Gets the default title location for a widget
   *
   * @return the default title location for a widget
   */
  protected int getDefaultTitleLocation() {
    if (isViewer()) {
      return Widget.CTitleLocation.not_displayed;
    } else {
      return Widget.CTitleLocation.center_left;
    }
  }

  /**
   * Gets the object being dropped on
   *
   * @param drop
   *          the drop information
   * @return the object being dropped on
   */
  protected Object getDropTarget(DropInformation drop) {
    return null;
  }

  protected String getEventName(String event) {
    if (event.indexOf('.') == -1) {
      String s = aWidgetListener.fromWebEvent(event);

      if (s != null) {
        event = s;
      }

      event = iConstants.EVENT_PREFIX + event;
    }

    return event;
  }

  protected UIImageIcon getImageIconEx(SPOTPrintableString image, boolean forIcon) {
    String href = image.getValue();

    if (href == null) {
      return null;
    }

    String s = image.spot_getAttribute("slice");

    if (s != null) {
      href += "[" + s + "]";
    }

    s = image.spot_getAttribute("size");

    UIDimension size = null;

    if ((s != null) && (s.length() > 0)) {
      size = Utils.getSize(s.trim());
    }

    boolean defer = true;

    if ("true".equalsIgnoreCase(image.spot_getAttribute("waitForLoad"))) {
      defer = false;
    }

    if ("false".equalsIgnoreCase(image.spot_getAttribute("deferred"))) {
      defer = false;
    }

    float density = 0;

    s = image.spot_getAttribute("density");

    if (s != null) {
      density = SNumber.floatValue(s);
    }

    if (density == 0) {
      density = forIcon
                ? PlatformHelper.getScaledImageDensity()
                : PlatformHelper.getUnscaledImageDensity();
    }

    ScalingType st = null;

    s = image.spot_getAttribute("scaling");

    if (s != null) {
      try {
        st = ScalingType.valueOf(s.toUpperCase(Locale.US));
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }
    }

    if ((size == null) && (st == null) && href.startsWith(iConstants.RESOURCE_PREFIX)) {
      UIImageIcon ic = (UIImageIcon) Platform.getResourceAsIcon(href.substring(iConstants.RESOURCE_PREFIX_LENGTH));

      return (ic == null)
             ? new UIImageIcon(UIImageIcon.getBrokenImage())
             : ic;
    }

    UIImage img = getImage(href, defer, size, st, density);

    return new UIImageIcon((img == null)
                           ? UIImageIcon.getBrokenImage()
                           : img);
  }

  protected abstract iPainterSupport getPainterSupport(boolean background);

  /**
   * Gets a scanner for parsing
   *
   * @return a scanner for parsing
   */
  protected final CharScanner getScanner() {
    return perThreadScanner.get();
  }

  /**
   * Invoked to retrieve transfer data when the flavor does not have a flavor
   * converter function associated with it
   *
   * @param flavor
   *          the data flavor
   * @param transferable
   *          the transferable
   *
   * @return the data to be transfered
   *
   */
  protected Object getTransferDataEx(TransferFlavor flavor, iTransferable transferable) {
    if (TransferFlavor.stringFlavor.equals(flavor)) {
      return getSelectionAsString();
    } else if (TransferFlavor.widgetFlavor.equals(flavor)) {
      return this;
    } else if (TransferFlavor.itemFlavor.equals(flavor)) {
      return this.getSelection();
    }

    return null;
  }

  /**
   * Gets the lowest level UI component in this widget's hierarchy
   *
   * @return the lowest level UI component in this widget's hierarchy
   */
  protected iPlatformComponent getUIComponent() {
    iPlatformComponent c = getDataComponent();

    if (c == null) {
      c = getContainerComponent();
    }

    if (c == null) {
      iContainer v = getParent();

      if (v != null) {
        if (v instanceof aPlatformWidget) {
          return ((aPlatformWidget) v).getUIComponent();
        }

        c = v.getDataComponent();
      }
    }

    return (c == null)
           ? getAppContext().getWindowViewer().getDataComponent()
           : c;
  }

  /**
   * Gets a widget specific attribute
   *
   * @param name
   *          the name of the attribute
   * @return the attribute's value of null if the attribute does not exist
   */
  protected String getWidgetAttribute(String name) {
    if (name.equals(iConstants.WIDGET_ATT_SELECTION_DATA)) {
      return this.getSelectionDataAsString();
    }

    if (name.equals(iConstants.WIDGET_ATT_SELECTION_VALUE)) {
      return this.getSelectionAsString();
    }

    if (name.equals(iConstants.WIDGET_ATT_DATA)) {
      return this.getLinkedDataAsString();
    }

    if (name.equals(iConstants.WIDGET_ATT_VALUE)) {
      return this.getValueAsString();
    }

    if (name.equals(iConstants.WIDGET_ATT_SUBMIT_VALUE)) {
      Object value = getHTTPFormValue();

      if (value instanceof String) {
        return (String) value;
      } else if (value instanceof int[]) {
        return Helper.toString((int[]) value, false);
      } else if (value instanceof String[]) {
        return Helper.toString((String[]) value, ",");
      } else {
        return "";
      }
    }

    if (name.equals(iConstants.WIDGET_ATT_TITLE)) {
      return widgetTitle;
    }

    if (name.equals(iConstants.WIDGET_ATT_NAME)) {
      return widgetName;
    }

    if (name.equals(iConstants.WIDGET_ATT_NAME)) {
      return widgetName;
    }

    if (name.equals(iConstants.APPLICATION_DOC_SERVERBASE)) {
      String s = Functions.documentServerBase();

      return (s == null)
             ? ""
             : s;
    }

    URL url = null;

    do {
      if (name.equals(iConstants.WIDGET_CONTEXTURL)) {
        url = this.getViewer().getContextURL();

        break;
      }

      if (name.equals(iConstants.WIDGET_BASEURL)) {
        url = this.getViewer().getBaseURL();

        break;
      }

      if (name.equals(iConstants.APPLICATION_SERVERBASE)) {
        url = getAppContext().getApplicationURL();

        if ((url == null) ||!url.getProtocol().startsWith("http")) {
          url = getAppContext().getDocumentBase();
        }

        if (url == null) {
          url = getAppContext().getApplicationURL();
        }

        break;
      }

      if (name.equals(iConstants.APPLICATION_CODEBASE)) {
        url = getAppContext().getCodeBase();

        break;
      }

      if (name.equals(iConstants.APPLICATION_DOCBASE)) {
        url = getAppContext().getDocumentBase();

        break;
      }

      if (name.equals(iConstants.APPLICATION_URL)) {
        url = getAppContext().getApplicationURL();

        break;
      }
    } while(false);

    return (url == null)
           ? ""
           : JavaURLConnection.toExternalForm(url);
  }

  /**
   * Returns whether a handler was registered for a drop event
   *
   * @return true if a handler was registered for a drop event; false otherwise
   */
  protected boolean isDropEventEnabled() {
    return ((getWidgetListener() != null) && getWidgetListener().isEnabled(iConstants.EVENT_DROP));
  }

  private iPlatformPainter getOverlayPainter(boolean background) {
    iPlatformComponentPainter cp = this.getComponentPainter(true, background);

    if (cp == null) {
      return null;
    }

    return background
           ? cp.getBackgroundOverlayPainter()
           : cp.getOverlayPainter();
  }

  /**
   * This class provides a container for storing a list of expanded and or
   * selected items This is to store items that have been flagged as expanded or
   * selected during the parsing and loading or data items.
   *
   * It is the widget's responsibility to process these once data has been
   * loaded
   */
  public static class ItemAttributes {

    /** a holder for a single checked item */
    public RenderableDataItem check;

    /** a holder for a set of checked items */
    public ArrayList<RenderableDataItem> checked;

    /** a holder for a single expanded item */
    public RenderableDataItem expand;

    /** a holder for a set of expanded items */
    public ArrayList<RenderableDataItem> expanded;

    /** a holder for a single selected item */
    public RenderableDataItem select;

    /** a holder for a set of selected items */
    public ArrayList<RenderableDataItem> selected;

    /**
     * Adds an item that has a selected attribute set
     *
     * @param item
     *          the item
     */
    public void addChecked(RenderableDataItem item) {
      if (check != null) {
        if (checked == null) {
          checked = new ArrayList<RenderableDataItem>(5);
        }

        checked.add(item);
      } else {
        check = item;
      }
    }

    /**
     * Adds an item that has an expanded attribute set
     *
     * @param item
     *          the item
     */
    public void addExpanded(RenderableDataItem item) {
      if (expand != null) {
        if (expanded == null) {
          expanded = new ArrayList<RenderableDataItem>(5);
        }

        expanded.add(item);
      } else {
        expand = item;
      }
    }

    /**
     * Adds an item that has a selected attribute set
     *
     * @param item
     *          the item
     */
    public void addSelected(RenderableDataItem item) {
      if (select != null) {
        if (selected == null) {
          selected = new ArrayList<RenderableDataItem>(5);
        }

        selected.add(item);
      } else {
        select = item;
      }
    }
  }
}
