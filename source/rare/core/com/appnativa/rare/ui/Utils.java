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
import com.appnativa.rare.converters.aConverter;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.CollapsibleInfo;
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.Line;
import com.appnativa.rare.spot.Link;
import com.appnativa.rare.spot.Margin;
import com.appnativa.rare.spot.NameValuePair;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.spot.WidgetPane;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.WindowEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.event.iExpandedListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.rare.ui.painter.UICellPainter;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iImagePainter;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.viewer.iContainer.Layout;
import com.appnativa.rare.viewer.iTabPaneViewer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTAny;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.ByteArray;
import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.FilterableList;
import com.appnativa.util.Helper;
import com.appnativa.util.ISO88591Helper;
import com.appnativa.util.SNumber;
import com.appnativa.util.UTF8Helper;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import java.net.MalformedURLException;
import java.net.URL;

import java.text.DecimalFormatSymbols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class Utils {
  public final static int[]               EMPTY_INTS   = new int[0];
  private final static UIInsets           EMPTY_INSETS = new UIInsets();
  static UIDimension                      minButtonSize;
  static UIDimension                      minTextFieldSize;
  private static ThreadLocal<CharScanner> perThreadScanner = new ThreadLocal<CharScanner>() {
    @Override
    protected synchronized CharScanner initialValue() {
      return new CharScanner();
    }
  };
  private static ThreadLocal<CharArray> perThreadCharArray = new ThreadLocal<CharArray>() {
    @Override
    protected synchronized CharArray initialValue() {
      return new CharArray();
    }
  };
  private static ThreadLocal<ByteArray> perThreadByteArray = new ThreadLocal<ByteArray>() {
    @Override
    protected synchronized ByteArray initialValue() {
      return new ByteArray();
    }
  };
  private static RenderableDataItem defaultItem = new RenderableDataItem("");

  public static void adjustButtonSize(UIDimension size) {
    if (minButtonSize == null) {
      Integer w = Platform.getUIDefaults().getInteger("Rare.Button.minimumWidth");
      Integer h = Platform.getUIDefaults().getInteger("Rare.Button.minimumHeight");

      if ((w == null) && (h == null)) {
        adjustTextFieldSize(size);
        minButtonSize = minTextFieldSize;

        return;
      }

      UIDimension d = new UIDimension();

      if (w != null) {
        d.width = w.intValue();
      }

      if (h != null) {
        d.height = h.intValue();
      }

      minButtonSize = d;
    }

    size.width  = Math.max(size.width, minButtonSize.width);
    size.height = Math.max(size.height, minButtonSize.height);
  }

  public static void adjustTextFieldSize(UIDimension size) {
    if (minTextFieldSize == null) {
      Integer     w = Platform.getUIDefaults().getInteger("Rare.TextField.minimumWidth");
      Integer     h = Platform.getUIDefaults().getInteger("Rare.TextField.minimumHeight");
      UIDimension d = new UIDimension();

      if (w != null) {
        d.width = w.intValue();
      }

      if (h != null) {
        d.height = h.intValue();
      }

      minTextFieldSize = d;
    }

    size.width  = Math.max(size.width, minTextFieldSize.width);
    size.height = Math.max(size.height, minTextFieldSize.height);
  }

  public static UIRectangle[] computeDifference(final UIRectangle rect1, final UIRectangle rect2) {
    if ((rect1 == null) || rect1.isEmpty() || (rect2 == null) || rect2.isEmpty()) {
      return new UIRectangle[0];
    }

    UIRectangle isection = rect1.intersection(rect2);

    if (isection.isEmpty()) {
      return new UIRectangle[0];
    }

    ArrayList reminders = new ArrayList(4);

    substract(rect1, isection, reminders);

    return (UIRectangle[]) reminders.toArray(new UIRectangle[0]);
  }

  /**
   * Configures and returns a cell painter from the specified grid cell
   * configuration object
   *
   * @param context
   *          the widget context
   * @param cell
   *          the cell configuration
   * @return the painter or null if cell is null or contains no paint
   */
  public static UICellPainter configureCellPainter(iWidget context, GridCell cell) {
    UICellPainter cp = null;

    if (cell != null) {
      cp            = new UICellPainter();
      cp.x          = cell.getX();
      cp.y          = cell.getY();
      cp.width      = cell.getWidth();
      cp.height     = cell.getHeight();
      cp.row        = cp.y;
      cp.column     = cp.x;
      cp.columnSpan = cp.width;
      cp.rowSpan    = cp.height;

      if (cell.bgColor.spot_valueWasSet()) {
        ColorUtils.configureBackgroundPainter(cp, cell.bgColor.getValue(), cell.bgColor.spot_getAttributesEx(), false);
      }

      if (cell.bgImageURL.spot_valueWasSet()) {
        cp.setImagePainter(Utils.configureImage(context, null, cell.bgImageURL, false));
      }

      iPlatformBorder b = BorderUtils.createBorder(context, cell.getBorders(), null);

      if (b != null) {
        cp.setBorder(b);
      }
    }

    return cp;
  }

  /**
   * Configures and returns a set of cell painter from the specified set of grid
   * cell configuration objects
   *
   * @param context
   *          the widget context
   * @param set
   *          the set of cell the cell configurations
   * @return the set of painters or null if the set is empty
   */
  public static UICellPainter[] configureCellPainters(iWidget context, SPOTSet set) {
    int len = (set == null)
              ? 0
              : set.getCount();

    if (len == 0) {
      return null;
    }

    UICellPainter[] painters = new UICellPainter[len];

    for (int i = 0; i < len; i++) {
      painters[i] = configureCellPainter(context, (GridCell) set.getEx(i));
    }

    return painters;
  }

  public static void configureCollapsible(iWidget context, iCollapsible pane, CollapsibleInfo cfg) {
    String s = cfg.title.getValue();

    if (s != null) {
      pane.setTitleText(context.expandString(s, false));
    }

    s = cfg.collapsedTitle.getValue();

    if (s != null) {
      pane.setCollapsedTitle(context.expandString(s, false));
    }

    s = cfg.expandTip.getValue();

    if (s != null) {
      pane.setExpandTip(context.expandString(s, false));
    }

    s = cfg.collapseTip.getValue();

    if (s != null) {
      pane.setCollapseTip(context.expandString(s, false));
    }

    iPlatformIcon icon = context.getIcon(cfg.icon);

    if (icon != null) {
      pane.setTitleIcon(icon);
    }

    switch(cfg.expander.intValue()) {
      case CollapsibleInfo.CExpander.custom :
        pane.setCollapseIcon(context.getIcon(cfg.expander.spot_getAttribute("collapseIcon"), "collapse icon"));
        pane.setExpandIcon(context.getIcon(cfg.expander.spot_getAttribute("expandIcon"), "expand icon"));
        pane.setTitleIconOnLeft("true".equalsIgnoreCase(cfg.expander.spot_getAttribute("iconOnTheLeft")));

        break;

      case CollapsibleInfo.CExpander.chevron :
        pane.setUseChevron(true);

        break;

      default :
        break;
    }

    PaintBucket pb = UIColorHelper.configure(context, cfg.getTitleCell(), null);

    if (pb != null) {
      if (pb.getBorder() != null) {
        pane.setTitleBorder(pb.getBorder());
      }

      UIColor c = pb.getForegroundColor();

      if (c != null) {
        pane.setTitleForeground(c);
      }

      c = pb.getBackgroundColor();

      if (c != null) {
        pane.setTitleBackground(c);
      }

      iPlatformComponent comp = pane.getTitleComponent();

      s = cfg.getTitleCell().height.getValue();

      if ((s != null) && (s.length() > 0) &&!s.equals("-1")) {
        comp.putClientProperty(iConstants.RARE_HEIGHT_PROPERTY, s);
      }

      if (pb.getComponentPainter(false) != null) {
        if (comp instanceof iPainterSupport) {
          ((iPainterSupport) comp).setComponentPainter(pb.getComponentPainter(false));
        }
      }
    }

    if (cfg.titleFont.spot_hasValue()) {
      pane.setTitleFont(UIFontHelper.getFont(context, cfg.titleFont));
    }

    if (!cfg.showTitleBar.booleanValue()) {
      pane.setShowTitle(false);
    }

    if (cfg.expandOnDragover.booleanValue()) {
      pane.setExpandOnDragOver(true);
    }

    if (!context.isDesignMode() && cfg.animateTransitions.booleanValue()) {
      pane.setAnimateTransitions(true);
      pane.setAnimatorOptions(cfg.animateTransitions.spot_getAttributesEx());
    }

    pane.setUserControllable(cfg.userControllable.booleanValue());

    if (cfg.initiallyCollapsed.booleanValue()) {
      pane.setCollapsed(true);
    }

    if (!cfg.opaqueTitleBar.booleanValue()) {
      pane.setTitleOpaque(false);
    }
  }

  public static iImagePainter configureImage(iWidget context, iImagePainter painter, Map<String, String> attrs) {
    String s = null;

    try {
      if (attrs != null) {
        CharScanner scanner = perThreadScanner.get();

        s = attrs.get("renderType");

        if ((s != null) && (s.length() > 0)) {
          try {
            scanner.reset(s);
            scanner.unquote(true).toUpperCase();
            painter.setRenderType(RenderType.valueOf(scanner.getLeftOver()));
          } catch(Exception e) {
            Platform.ignoreException(
                Helper.expandString(
                  context.getAppContext().getResourceAsString("Rare.runtime.text.badAttributeValue"), s,
                  "renderType"), null);
          }
        }

        s = attrs.get("renderSpace");

        if ((s != null) && (s.length() > 0)) {
          try {
            scanner.reset(s);
            scanner.unquote(true).toUpperCase();
            painter.setRenderSpace(RenderSpace.valueOf(scanner.getLeftOver()));
          } catch(Exception e) {
            Platform.ignoreException(
                Helper.expandString(
                  context.getAppContext().getResourceAsString("Rare.runtime.text.badAttributeValue"), s,
                  "renderSpace"), null);
          }
        }

        s = attrs.get("displayed");

        if ((s != null) && (s.length() > 0)) {
          try {
            scanner.reset(s);
            scanner.unquote(true).toUpperCase();
            painter.setDisplayed(Displayed.valueOf(scanner.getLeftOver()));
          } catch(Exception e) {
            Platform.ignoreException(
                Helper.expandString(
                  context.getAppContext().getResourceAsString("Rare.runtime.text.badAttributeValue"), s,
                  "displayed"), null);
          }
        }

        float alpha = 1;

        s = attrs.get("opacity");

        if ((s != null) && (s.length() > 0)) {
          alpha = SNumber.floatValue(s);

          if (s.endsWith("%")) {
            alpha /= 100;
          }

          if (alpha > 1) {
            alpha = (alpha % 256) / 255.0f;
          }
        }

        iComposite.CompositeType ct = iComposite.CompositeType.SRC_OVER;

        s = attrs.get("composite");

        if (s != null) {
          try {
            scanner.reset(s);
            scanner.unquote(true).toUpperCase();
            ct = iComposite.CompositeType.valueOf(scanner.getLeftOver());
          } catch(Exception e) {
            Platform.ignoreException(
                Helper.expandString(
                  context.getAppContext().getResourceAsString("Rare.runtime.text.badAttributeValue"), s,
                  "composite"), null);
          }
        }

        if ((ct != iComposite.CompositeType.SRC_OVER) || (alpha != 1)) {
          painter.setComposite(new GraphicsComposite(ct, alpha));
        }

        s = attrs.get("scaling");

        if (s != null) {
          scanner.reset(s);
          scanner.unquote(true).toUpperCase();
          painter.setScalingType(ScalingType.valueOf(scanner.getLeftOver()));
        }
      }
    } catch(Exception ex) {
      Platform.ignoreException(s, ex);
    }

    return painter;
  }

  /**
   * Configures a component's icon scaling
   *
   * @param context
   *          the context
   * @param painter
   *          the image painter to configure (can be null)
   * @param imgURL
   *          the configuration object
   * @param emptyOk
   *          true to all the creation of a painter without and image; false
   *          otherwise
   * @return returns the configured painter (if one was not passed in, then a
   *         new one was created)
   */
  public static iImagePainter configureImage(iWidget context, iImagePainter painter, SPOTPrintableString imgURL,
          boolean emptyOk) {
    UIImage img = context.getImage(imgURL);

    if ((img == null) &&!emptyOk) {
      return null;
    }

    if (painter == null) {
      painter = new UIImagePainter();
    }

    painter.setImage(img);
    painter.setSourceLocation((img == null)
                              ? null
                              : img.getLocation());

    return configureImage(context, painter, imgURL.spot_getAttributesEx());
  }

  /**
   * Configures a component's icon scaling
   *
   * @param context
   *          the context
   * @param painter
   *          the image painter to configure (can be null)
   * @param url
   *          the url to the image
   * @param attrs
   *          the image attributes
   * @param emptyOk
   *          true to all the creation of a painter without and image; false
   *          otherwise
   * @return returns the configured painter (if one was not passed in, then a
   *         new one was created)
   */
  public static iImagePainter configureImage(iWidget context, iImagePainter painter, String url,
          Map<String, String> attrs, boolean emptyOk) {
    if (url != null) {
      url = url.trim();

      if (url.length() == 0) {
        url = null;
      }
    }

    UIImage img = (url == null)
                  ? null
                  : context.getImage(url);

    if ((img == null) &&!emptyOk) {
      return null;
    }

    if (painter == null) {
      painter = new UIImagePainter();
    }

    painter.setImage(img);
    painter.setSourceLocation(url);
    ;

    return configureImage(context, painter, attrs);
  }

  public static Object createAbsoluteConstraints(int x, int y, int width, int height) {
    return new UIRectangle(ScreenUtils.platformPixels(x), ScreenUtils.platformPixels(y),
                           ScreenUtils.platformPixels(width), ScreenUtils.platformPixels(height));
  }

  public static CellConstraints createCellConstraints(Widget cfg, CellConstraints cc, UIRectangle table) {
    int x;
    int y;
    int h;
    int w;

    if (table == null) {
      x = cfg.bounds.getX();
      y = cfg.bounds.getY();
    } else {
      x = (int) table.x;
      y = (int) table.y;
    }

    h = cfg.rowSpan.intValue();
    w = cfg.columnSpan.intValue();

    if (w < 0) {
      w = (table == null)
          ? -1
          : (int) table.width - x;
    }

    if (h < 0) {
      h = (table == null)
          ? -1
          : (int) table.height - y;
    }

    if (x < 0) {
      x = 0;
    }

    if (y < 0) {
      y = 0;
    }

    CellConstraints.Alignment vAlign = CellConstraints.DEFAULT;
    CellConstraints.Alignment hAlign = CellConstraints.DEFAULT;

    switch(cfg.verticalAlign.intValue()) {
      case Widget.CVerticalAlign.top :
        vAlign = CellConstraints.TOP;

        break;

      case Widget.CVerticalAlign.bottom :
        vAlign = CellConstraints.BOTTOM;

        break;

      case Widget.CVerticalAlign.full :
        vAlign = CellConstraints.FILL;

        break;

      default :
        break;
    }

    switch(cfg.horizontalAlign.intValue()) {
      case Widget.CHorizontalAlign.center :
        hAlign = CellConstraints.CENTER;

        break;

      case Widget.CHorizontalAlign.left :
        hAlign = CellConstraints.LEFT;

        break;

      case Widget.CHorizontalAlign.right :
        hAlign = CellConstraints.RIGHT;

        break;

      case Widget.CHorizontalAlign.full :
        hAlign = CellConstraints.FILL;

        break;

      default :
        break;
    }

    if (cfg instanceof Line) {
      Line l = (Line) cfg;

      if (l.horizontal.booleanValue()) {
        if (hAlign == CellConstraints.DEFAULT) {
          hAlign = CellConstraints.FILL;
        }
      } else if (vAlign == CellConstraints.DEFAULT) {
        vAlign = CellConstraints.FILL;
      }
    }

    Margin m = cfg.getCellPadding();

    if (cc == null) {
      cc = new CellConstraints();
    }

    if (m != null) {
      cc.insets = m.getInsets();
    } else {
      cc.insets.set(0, 0, 0, 0);
    }

    return cc.xywh(x + 1, y + 1, w, h, hAlign, vAlign);
  }

  public static CellConstraints createCellConstraints(int x, int y, int colSpan, int rowSpan, HorizontalAlign ha,
          VerticalAlign va) {
    CellConstraints.Alignment vAlign = CellConstraints.DEFAULT;
    CellConstraints.Alignment hAlign = CellConstraints.DEFAULT;

    switch(va) {
      case TOP :
        vAlign = CellConstraints.TOP;

        break;

      case BOTTOM :
        vAlign = CellConstraints.BOTTOM;

        break;

      case FILL :
        vAlign = CellConstraints.FILL;

        break;

      default :
        break;
    }

    switch(ha) {
      case CENTER :
        hAlign = CellConstraints.CENTER;

        break;

      case LEFT :
        hAlign = CellConstraints.LEFT;

        break;

      case RIGHT :
        hAlign = CellConstraints.RIGHT;

        break;

      case FILL :
        hAlign = CellConstraints.FILL;

        break;

      default :
        break;
    }

    return new CellConstraints(x + 1, y + 1, colSpan, rowSpan, hAlign, vAlign);
  }

  public static Object createConstraints(iPlatformComponent panel, Widget cfg) {
    FormLayout fl = null;
    Layout     lo;

    if (panel instanceof iFormsPanel) {
      fl = ((iFormsPanel) panel).getFormLayout();
      lo = ((iFormsPanel) panel).isTableLayout()
           ? Layout.TABLE
           : Layout.FORMS;
    } else {
      lo = Layout.ABSOLUTE;
    }

    switch(lo) {
      case TABLE : {
        CellConstraints cc   = new CellConstraints();
        int             rows = fl.getRowCount() * 2 - 1;
        int             cols = fl.getRowCount() * 2 - 1;
        UIRectangle     r    = new UIRectangle(cols, rows);

        cc       = createCellConstraints(cfg, cc, r);
        cc.gridX = cc.gridX * 2 - 1;
        cc.gridY = cc.gridX * 2 - 1;

        if (cc.gridX < 0) {
          cc.gridX = 0;
        }

        if (cc.gridY < 0) {
          cc.gridY = 0;
        }

        return cc;
      }

      case FORMS :
        return createCellConstraints(cfg, new CellConstraints(), null);

      case ABSOLUTE :
        return createAbsoluteConstraints(cfg.bounds.getX(), cfg.bounds.getY(), cfg.bounds.getWidth(),
                                         cfg.bounds.getHeight());

      case FLOW :
        return createCellConstraints(cfg, new CellConstraints(), null);

      default :
        return null;
    }
  }

  /**
   * Creates and image painter from a painter string
   *
   * @param context
   *          the context
   * @param painter
   *          the painter string
   *
   * @return the image painter or null
   */
  public static iImagePainter createImagePainter(iWidget context, String painter) {
    if (painter == null) {
      return null;
    }

    Map<String, String> map = null;
    int                 n   = painter.indexOf('[');

    if (n != -1) {
      int p = painter.indexOf(']');

      if (p > n) {
        String s = painter.substring(n + 1, p);

        painter = painter.substring(0, n);
        map     = CharScanner.parseOptionStringEx(s, ',');
      }
    }

    return configureImage(context, null, painter, map, false);
  }

  /**
   * Creates a link for the viewer
   *
   * @param context
   *          the widget context
   * @param target
   *          the target
   * @param viewer
   *          the viewer definition
   *
   * @return an Action link that will target the specified region
   */
  public static ActionLink createLink(iWidget context, String target, SPOTAny viewer) {
    ActionLink link = null;
    Widget     w    = (Widget) viewer.getValue();

    if (w != null) {
      Viewer v = (w instanceof Viewer)
                 ? (Viewer) w
                 : new WidgetPane(w);

      link = new ActionLink(context, v);
      link.setTargetName(target);
    } else {
      String s = viewer.spot_getAttribute("url");

      if ((s != null) && (s.length() > 0)) {
        URL url;

        try {
          url = context.getURL(s);
        } catch(MalformedURLException ex) {
          throw ApplicationException.runtimeException(ex);
        }

        link = new ActionLink(context, url);
      }
    }

    return link;
  }

  public static iPath createPath() {
    return PlatformHelper.createPath();
  }

  /**
   * Finds the lowest level target in the specified component's hierarchy
   *
   * @param c
   *          the component
   *
   * @return the lowest level target in the specified component's hierarchy
   */
  public static iTarget findTargetForComponent(iPlatformComponent c) {
    iTarget t = null;

    while((t == null) && (c != null)) {
      t = (iTarget) c.getClientProperty(iConstants.RARE_TARGET_COMPONENT_PROPERTY);

      if (t == null) {
        c = c.getParent();
      }
    }

    return t;
  }

  public static void fireActionEvent(EventListenerList listenerList, ActionEvent ae) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iActionListener.class) {
        ((iActionListener) listeners[i + 1]).actionPerformed(ae);
      }
    }
  }

  public static void fireChangeEvent(EventListenerList listenerList, ChangeEvent e) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iChangeListener.class) {
        ((iChangeListener) listeners[i + 1]).stateChanged(e);
      }
    }
  }

  public static void fireExpansionEvent(EventListenerList listenerList, ExpansionEvent e, boolean expanded) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iExpandedListener.class) {
        if (expanded) {
          ((iExpandedListener) listeners[i + 1]).itemHasExpanded(e);
        } else {
          ((iExpandedListener) listeners[i + 1]).itemHasCollapsed(e);
        }
      }
    }
  }

  public static void fireItemChanged(EventListenerList listenerList, ItemChangeEvent e) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iItemChangeListener.class) {
        ((iItemChangeListener) listeners[i + 1]).itemChanged(e);
      }
    }
  }

  public static void firePopupCanceledEvent(EventListenerList listenerList, ExpansionEvent e) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iPopupMenuListener.class) {
        ((iPopupMenuListener) listeners[i + 1]).popupMenuCanceled(e);
      }
    }
  }

  public static void firePopupEvent(EventListenerList listenerList, ExpansionEvent e, boolean popupingUp) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iPopupMenuListener.class) {
        if (popupingUp) {
          ((iPopupMenuListener) listeners[i + 1]).popupMenuWillBecomeVisible(e);
        } else {
          ((iPopupMenuListener) listeners[i + 1]).popupMenuWillBecomeInvisible(e);
        }
      }
    }
  }

  public static void firePropertyChangeEvent(Object source, EventListenerList listenerList, String name,
          Object oldValue, Object newValue) {
    Object[]            listeners = listenerList.getListenerList();
    PropertyChangeEvent pce       = null;

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == PropertyChangeListener.class) {
        if (pce == null) {
          pce = new PropertyChangeEvent(source, name, oldValue, newValue);
        }

        ((PropertyChangeListener) listeners[i + 1]).propertyChange(pce);
      }
    }
  }

  public static void fireWindowEvent(EventListenerList listenerList, WindowEvent e) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iWindowListener.class) {
        ((iWindowListener) listeners[i + 1]).windowEvent(e);
      }
    }
  }

  public static String fixTarget(int type) {
    switch(type) {
      case Link.CTarget._null :
        return iTarget.TARGET_NULL;

      case Link.CTarget._self :
        return iTarget.TARGET_SELF;

      case Link.CTarget._parent :
        return iTarget.TARGET_PARENT;

      case Link.CTarget._new_popup :
        return iTarget.TARGET_NEW_POPUP;

      case Link.CTarget._workspace :
        return iTarget.TARGET_WORKSPACE;

      default :
        return iTarget.TARGET_NEW_WINDOW;
    }
  }

  public static String fixTarget(String target) {
    if (target.length() > 0) {
      char c = target.charAt(0);

      if ((c > 47) && (c < 58)) {
        return fixTarget(SNumber.intValue(target));
      }
    }

    return target;
  }

  public static String makeInvalidRangeString(int min, int max) {
    if ((min == -1) && (max == -1)) {
      return null;
    }

    String s = null;

    if (min == -1) {
      s = Platform.getResourceAsString("Rare.runtime.text.fieldValueToSmall");
      s = Helper.expandString(s, String.valueOf(min));
    } else if (max == -1) {
      s = Platform.getResourceAsString("Rare.runtime.text.fieldValueToBig");
      s = Helper.expandString(s, String.valueOf(max));
    } else {
      s = Platform.getResourceAsString("Rare.runtime.text.fieldValueNotInRange");
      s = Helper.expandString(s, String.valueOf(min), String.valueOf(max));
    }

    s += Platform.getResourceAsString("Rare.runtime.text.fieldInvalidCharsSufffix");

    return s;
  }

  public static String makeWidgetName(Widget cfg, iWidget w) {
    String s = cfg.getClass().getName();
    int    n = s.lastIndexOf('.');

    if (n != -1) {
      s = s.substring(n + 1);
    }

    return "a" + s + "__" + System.identityHashCode(w);
  }

  public static Object nameValuePairGetValue(iWidget context, NameValuePair pair) {
    Object         ctx;
    iDataConverter cvt;
    String         cc;
    String         s;
    String         type;
    Object         o;

    o    = s = pair.getValue();
    cc   = pair.spot_getAttribute("converterClass");
    type = pair.spot_getAttribute("type");

    if (((cc != null) && (cc.length() > 0)) || ((type != null) && (type.length() > 0))) {
      try {
        cvt = aConverter.createConverter(context, cc, RenderableDataItem.typeOf(s));

        if (cvt != null) {
          cc  = pair.spot_getAttribute("converterContext");
          ctx = null;

          if ((cc != null) && (cc.length() > 0)) {
            ctx = cvt.createContext(context, cc);
          }

          o = cvt.objectFromString(context, s, ctx);
        }
      } catch(Exception ex) {
        Platform.ignoreException(null, ex);
      }
    }

    return o;
  }

  public static Map nameValuePairSetToMap(iWidget context, SPOTSet set, Map map) {
    if (context == null) {
      context = Platform.getContextRootViewer();
    }

    int len = set.getCount();

    if (len == 0) {
      return null;
    }

    if (map == null) {
      map = new HashMap();
    }

    NameValuePair  p;
    Object         ctx;
    iDataConverter cvt;
    String         cc;
    String         s;
    String         type;
    Object         o;

    for (int i = 0; i < len; i++) {
      p    = (NameValuePair) set.get(i);
      o    = s = p.getValue();
      cc   = p.spot_getAttribute("converterClass");
      type = p.spot_getAttribute("type");

      if (((cc != null) && (cc.length() > 0)) || ((type != null) && (type.length() > 0))) {
        try {
          cvt = aConverter.createConverter(context, cc, RenderableDataItem.typeOf(s));

          if (cvt != null) {
            cc  = p.spot_getAttribute("converterContext");
            ctx = null;

            if ((cc != null) && (cc.length() > 0)) {
              ctx = cvt.createContext(context, cc);
            }

            o = cvt.objectFromString(context, s, ctx);
          }
        } catch(Exception ex) {
          Platform.ignoreException(null, ex);
        }
      }

      map.put(p.getName(), o);
    }

    return map;
  }

  public static void paintCenteredIcon(iPlatformGraphics g, iPlatformIcon icon, float x, float y, float width,
          float height) {
    int iw = icon.getIconWidth();
    int ih = icon.getIconHeight();

    x += (width - iw) / 2;
    y += (height - ih) / 2;
    icon.paint(g, x, y, width, height);
  }

  public static Map<String, String> resolveOptions(iWidget widget, Map<String, String> origOptions,
          Map<String, String> resolvedOptions) {
    if (origOptions == null) {
      return resolvedOptions;
    }

    if (resolvedOptions == null) {
      resolvedOptions = new HashMap<String, String>(origOptions.size());
    }

    Map<String, String>             map = resolvedOptions;
    Iterator<Entry<String, String>> it  = origOptions.entrySet().iterator();
    Entry<String, String>           e;
    String                          value;
    String                          nv;

    while(it.hasNext()) {
      e     = it.next();
      value = e.getValue();

      if ((value == null) || (value.length() == 0)) {
        continue;
      }

      nv = widget.expandString(value, false);
      map.put(e.getKey().toLowerCase(Locale.ENGLISH), nv);
    }

    return map;
  }

  public static Object resolveUIProperty(iWidget context, String name, String propvalue) {
    Object              value = propvalue;
    iPlatformAppContext app   = (context == null)
                                ? Platform.getAppContext()
                                : context.getAppContext();

    try {
      do {
        if (propvalue == null) {
          break;
        }

        CharScanner scanner = perThreadScanner.get();

        scanner.reset(propvalue);
        scanner.unquote(true);
        value = null;

        int tok[] = scanner.findToken('|');

        if (tok == null) {
          value = scanner.getLeftOver();

          break;
        }

        scanner.toLowerCase(tok);

        String type = scanner.getToken(tok);

        scanner.unquote(true).trim();
        propvalue = scanner.getLeftOver();

        if (type.equals("integer")) {
          value = Integer.valueOf(SNumber.intValue(propvalue));

          break;
        }

        if (type.equals("long")) {
          value = Long.valueOf(SNumber.longValue(propvalue));

          break;
        }

        if (type.equals("double")) {
          value = Double.valueOf(SNumber.doubleValue(propvalue));

          break;
        }

        if (type.equals("float")) {
          value = Float.valueOf(SNumber.floatValue(propvalue));

          break;
        }

        if (type.equals("boolean")) {
          value = Boolean.valueOf(propvalue.equalsIgnoreCase("true"));

          break;
        }

        if ((type.indexOf("color") != -1) && (propvalue.length() > 0)) {
          UIColor c;

          if (type.startsWith("background") || (propvalue.indexOf(',') != -1)) {
            c = ColorUtils.getBackgroundColor(propvalue);
          } else {
            c = ColorUtils.getColor(propvalue);
          }

          if ((ColorUtils.KEEP_COLOR_KEYS == Boolean.TRUE) || type.contains("shade")) {
            if (!(c instanceof UIColorShade)) {
              c = new UIColorShade(c, name);
            } else {
              UIColorShade cs = (UIColorShade) c.clone();

              cs.setColorKey(name);
              c = cs;
            }
          } else {
            c = (c instanceof UIColorShade)
                ? (UIColorShade) c
                : new UIColorShade(c, name);
          }

          value = c;

          break;
        }

        if (type.equals("border")) {
          value = app.getUIDefaults().getBorder(propvalue);

          if (value == null) {
            value = BorderUtils.createBorder(context, propvalue, app.getUIDefaults().getBorder(name));
          }

          break;
        }

        if (type.equals("icon")) {
          value = context.getIcon(propvalue, name);

          if (value == null) {
            if (propvalue.startsWith(iConstants.RESOURCE_PREFIX)) {
              value =
                context.getAppContext().getResourceAsIcon(propvalue.substring(iConstants.RESOURCE_PREFIX.length()));
            } else {
              try {
                value = context.getIcon(propvalue, propvalue);
              } catch(Exception ex) {
                throw new ApplicationException(ex);
              }
            }
          }

          break;
        }

        if (type.equals("insets") || type.equals("margin")) {
          char         c    = (propvalue.indexOf(';') != -1)
                              ? ';'
                              : ',';
          List<String> list = CharScanner.getTokens(propvalue, c, true);
          int          len  = list.size();

          if (len != 4) {
            value = null;

            break;
          }

          UIInsets insets = new UIInsets(0, 0, 0, 0);

          propvalue = list.get(0);

          if (propvalue.startsWith("(") || propvalue.startsWith("{")) {
            propvalue = propvalue.substring(1).trim();
          }

          insets.top    = ScreenUtils.platformPixels(SNumber.intValue(propvalue));
          insets.bottom = SNumber.intValue(list.get(2));

          if (type.charAt(0) == 'm') {
            insets.right = ScreenUtils.platformPixels(SNumber.intValue(list.get(1)));
            insets.left  = ScreenUtils.platformPixels(SNumber.intValue(list.get(3)));
          } else {
            insets.left  = ScreenUtils.platformPixels(SNumber.intValue(list.get(1)));
            insets.right = ScreenUtils.platformPixels(SNumber.intValue(list.get(3)));
          }

          value = insets;
        }

        if (type.equals("font")) {
          value = app.getUIDefaults().getFont(propvalue);

          if (value == null) {
            UIFont f = app.getUIDefaults().getFont(name);

            value = FontUtils.parseFont(context, f, propvalue);
          }

          break;
        }

        if (type.equals("gridcell")) {
          GridCell cell = (GridCell) DataParser.loadSPOTObjectSDF(context, new StringReader(propvalue), new GridCell(),
                            null, null);

          value = UIColorHelper.getPaintBucket(context, cell);

          break;
        }

        if (type.equals("cellpainter")) {
          GridCell cell = (GridCell) DataParser.loadSPOTObjectSDF(context, new StringReader(propvalue), new GridCell(),
                            null, null);

          value = configureCellPainter(context, cell);

          break;
        }

        if (type.equals("backgroundpainter")) {
          value = ColorUtils.getBackgroundPainter(propvalue);

          break;
        }

        if (type.equals("imagepainter")) {
          value = Utils.createImagePainter(context, propvalue);

          break;
        }

        if (type.equals("drawable")) {
          value = PlatformHelper.getResourceAsDrawable(propvalue);

          break;
        }

        if (type.equals("spot")) {
          value = DataParser.loadSPOTObjectSDF(context, new StringReader(propvalue), null, null, null);

          break;
        }

        if (type.equals("property")) {
          value = app.getUIDefaults().get(propvalue);

          break;
        }

        if (type.equals("string")) {
          value = context.expandString(propvalue, false);

          break;
        }

        if (type.equals("dimension")) {
          value = Utils.getSize(propvalue);

          break;
        }

        if (type.equals("url")) {
          value = context.getURL(propvalue);
        }

        if (type.equals("style")) {
          value = PlatformHelper.getResourceId(propvalue);
        }
      } while(false);

      return value;
    } catch(Exception e) {
      Platform.ignoreException("resolveUIProperty", e);

      return null;
    }
  }

  public static void setupWindowOptions(Frame frame, Map options) {
    if (options == null) {
      frame.setMovable(true);
      frame.setResizable(true);

      return;
    }

    boolean            resizable = true;
    boolean            movable   = true;
    float              height    = -1;
    float              left      = -1;
    float              top       = -1;
    float              width     = -1;
    Object             o;
    iPlatformComponent win        = frame.getComponent();
    UIDimension        screenSize = ScreenUtils.getScreenSize();

    o = options.get("left");

    if (o instanceof String) {
      left = ScreenUtils.toPlatformPixelWidth((String) o, win, screenSize.width);
    } else if (o instanceof Number) {
      left = ((Number) o).intValue();
    }

    o = options.get("top");

    if (o instanceof String) {
      top = ScreenUtils.toPlatformPixelHeight((String) o, win, screenSize.height);
    } else if (o instanceof Number) {
      top = ((Number) o).intValue();
    }

    o = options.get("height");

    if (o instanceof String) {
      height = ScreenUtils.toPlatformPixelHeight((String) o, win, screenSize.height);
    } else if (o instanceof Number) {
      height = ((Number) o).intValue();
    }

    o = options.get("width");

    if (o instanceof String) {
      width = ScreenUtils.toPlatformPixelWidth((String) o, win, screenSize.width);
    } else if (o instanceof Number) {
      width = ((Number) o).intValue();
    }

    String s;

    o = options.get("border");

    if (o != null) {
      iPlatformBorder b = null;

      if (o instanceof String) {
        s = ((String) o).trim();
        b = UIBorderHelper.createBorder(s);
      }

      if (b != null) {
        win.setBorder(b);
      }
    }

    o = options.get("resizable");

    if (o instanceof Boolean) {
      resizable = ((Boolean) o);
    } else if (o instanceof String) {
      s = (String) o;

      if ((s != null) && (s.length() > 0)) {
        resizable = SNumber.booleanValue(s);
      }
    }

    o = options.get("movable");

    if (o instanceof Boolean) {
      movable = ((Boolean) o);
    } else if (o instanceof String) {
      s = (String) o;

      if ((s != null) && (s.length() > 0)) {
        movable = SNumber.booleanValue(s);
      }
    }

    frame.setResizable(resizable);
    frame.setMovable(movable);
    s = (String) options.get("title");

    if ((s == null) || (s.length() == 0)) {
      s = Platform.getAppContext().getWindowManager().getTitle();
    } else {
      s = s.trim();
    }

    frame.setTitle(s);

    UIImageIcon icon = null;

    o = options.get("icon");

    if (o instanceof UIImageIcon) {
      icon = (UIImageIcon) o;
    } else {
      s = (o instanceof String)
          ? (String) o
          : null;

      if ((s == null) || (s.length() == 0)) {
        s = (String) options.get("iconurl");
      }

      if ((s != null) && (s.length() > 0)) {}

      icon = (UIImageIcon) Platform.getWindowViewer().getIcon(s);
    }

    if (icon != null) {}

    UIColor c = null;

    o = options.get("bgcolor");

    if (o instanceof UIColor) {
      c = (UIColor) o;
    } else {
      s = (o instanceof String)
          ? (String) o
          : null;

      if ((s != null) && (s.length() > 0)) {
        c = UIColorHelper.getColor(s);
      }
    }

    if (c != null) {
      win.setBackground(c);
    }

    if ((width > 0) || (height > 0)) {
      if ((width < 1) || (height < 1)) {
        frame.setPartialSize(new UIDimension(width, height));
      } else {
        frame.setSize(width, height);
      }
    }

    if ((left > -1) || (top > -1)) {
      if ((left < 0) || (top < 0)) {
        frame.setPartialLocation(new UIPoint(left, top));
      } else {
        frame.setLocation(left, top);
      }
    }

    if (!frame.isUndecorated() && Platform.isTouchDevice()) {
      if (win.getBorder() == null) {
        win.setBorder(BorderUtils.createShadowBorder(ScreenUtils.PLATFORM_PIXELS_7));
      }

      s = frame.getTitle();

      if ((s == null) || (s.length() == 0)) {
        frame.setTitle(Platform.getAppContext().getWindowManager().getTitle());
      }
    }
  }

  /**
   * Strips the amphersand designating a mnemonic from a string
   *
   * @param text
   *          the text
   * @return the cleaned text
   */
  public static String stripMnemonic(String text) {
    if (text == null) {
      text = "";
    }

    int len = text.length();
    int n   = -1;

    if ((len > 0) &&!text.startsWith("<html>")) {
      n = text.indexOf('&');
    }

    if ((n != -1) && (n < len - 1)) {
      if (text.charAt(n + 1) != '&') {
        if (n == 0) {
          text = text.substring(1);
        } else {
          text = text.substring(0, n) + text.substring(n + 1);
        }
      } else {
        text = text.substring(0, n) + text.substring(n + 1);
      }
    }

    return text;
  }

  public static void transformPoint(UIPoint pt, int rotation, float width, float height) {
    float x = pt.x;
    float y = pt.y;
    float n;

    switch(rotation) {
      case 180 :
        y = height - y;
        x = width - x;

        break;

      case 90 :
        n      = x;
        x      = y;
        y      = width - n;
        n      = width;
        width  = height;
        height = n;

        break;

      case -90 :
      case 270 :
        n      = x;
        x      = height - y;
        y      = n;
        n      = width;
        width  = height;
        height = n;

        break;

      default :
        break;
    }

    pt.x = x;
    pt.y = y;
  }

  public static String utf8String(String value) {
    if ((value == null) || (value.length() == 0)) {
      return value;
    }

    CharArray ca = perThreadCharArray.get();
    ByteArray ba = perThreadByteArray.get();

    ca.set(value);
    ba._length = UTF8Helper.getInstance().getBytes(ca.A, 0, ca._length, ba, 0);
    ca._length = 0;
    ba.getChars(ca, ISO88591Helper.getInstance());

    String s = ca.toString();

    if (ca.A.length > 65538) {
      ca._length = 256;
      ca.trimToSize();
    }

    if (ba.A.length > 65538) {
      ba._length = 256;
      ba.trimToSize();
    }

    return s;
  }

  public static void setBackground(iPainterSupport c, Object color) {
    if (color instanceof PaintBucket) {
      PaintBucket               pb = (PaintBucket) color;
      iPlatformComponentPainter cp = pb.getComponentPainter(true);

      if (cp != null) {
        c.setComponentPainter(cp);
      }

      return;
    }

    if (color instanceof UIColor) {
      iPlatformComponentPainter cp = c.getComponentPainter();

      if (cp == null) {
        cp = new UIComponentPainter();
        c.setComponentPainter(cp);
      }

      cp.setBackgroundColor((UIColor) color);

      return;
    }

    if (color instanceof iBackgroundPainter) {
      iPlatformComponentPainter cp = c.getComponentPainter();

      if (cp == null) {
        cp = new UIComponentPainter();
        c.setComponentPainter(cp);
      }

      cp.setBackgroundPainter((iBackgroundPainter) color, false);
    }
  }

  public static void setBackground(iPlatformComponent c, Object color) {
    if (color instanceof PaintBucket) {
      ((PaintBucket) color).install(c);

      return;
    }

    if (color instanceof UIColor) {
      c.setBackground((UIColor) color);

      return;
    }

    if ((color instanceof iBackgroundPainter) && (c instanceof iPainterSupport)) {
      Utils.setBackgroundPainter((iPainterSupport) c, (iBackgroundPainter) color);
    }
  }

  /**
   * Sets the background color an item that has painters support
   *
   * @param ps
   *          the painter support interface
   * @param bg
   *          the background color
   */
  public static void setBackgroundColor(iPainterSupport ps, UIColor bg) {
    if (ps == null) {
      return;
    }

    iPlatformComponentPainter cp = ps.getComponentPainter();

    if (cp == null) {
      cp = new UIComponentPainter();
      ps.setComponentPainter(cp);
    }

    cp.setBackgroundColor(bg);
  }

  /**
   * Sets the background overlay painter for an item that has painters support
   *
   * @param ps
   *          the painter support interface
   * @param p
   *          the painter
   */
  public static void setBackgroundOverlayPainter(iPainterSupport ps, iPlatformPainter p) {
    if (ps == null) {
      return;
    }

    iPlatformComponentPainter cp = ps.getComponentPainter();

    if (cp == null) {
      cp = new UIComponentPainter();
      ps.setComponentPainter(cp);
    }

    cp.setBackgroundOverlayPainter(p);
  }

  /**
   * Sets the background painter for an item that has painter support
   *
   * @param ps
   *          the painter support interface
   * @param bp
   *          the background painter
   */
  public static void setBackgroundPainter(iPainterSupport ps, iBackgroundPainter bp) {
    if (ps == null) {
      return;
    }

    iPlatformComponentPainter cp = ps.getComponentPainter();

    if (cp == null) {
      cp = new UIComponentPainter();
      ps.setComponentPainter(cp);
    }

    cp.setBackgroundPainter(bp, false);
  }

  public static void setIconAndAlignment(iPlatformRenderingComponent rc, RenderableDataItem item,
          RenderableDataItem row, Column col, boolean enabled, boolean center, boolean top, boolean seticon,
          boolean alternateState, iPlatformIcon delayedIcon) {
    if (item == null) {
      if ((col == null) && (row == null)) {
        rc.setIcon(null);

        return;
      }

      item = defaultItem;
    }

    boolean column = item instanceof Column;
    Column  c      = null;

    if (column) {
      c = (Column) item;
    }

    iPlatformIcon icon;
    iPlatformIcon dicon = null;

    if (column) {
      icon = c.getHeaderIcon();
    } else {
      icon = item.getIcon();

      if (alternateState) {
        dicon = item.getAlternateIcon();

        if (dicon != null) {
          icon = dicon;
        }

        dicon = null;
      }

      if ((icon == null) && (col != null)) {
        if (alternateState) {
          icon = col.getAlternateIcon();
        }

        if (icon == null) {
          icon = col.getIcon();
        }
      }
    }

    if (!enabled) {
      if (seticon) {
        dicon = item.getDisabledIcon();

        if ((dicon == null) && (col != null)) {
          dicon = col.getDisabledIcon();
        }

        if ((dicon == null) && (icon != null)) {
          dicon = icon.getDisabledVersion();
        }

        icon = dicon;
      }
    }

    if (seticon) {
      if ((delayedIcon != null) && (icon != null) && (icon instanceof aUIImageIcon)
          &&!((aUIImageIcon) icon).isImageLoaded(null)) {
        icon = delayedIcon;
      }

      rc.setIcon(icon);

      float f = 0;

      if (item.isScaleIcon()) {
        f = item.getIconScaleFactor();
      } else if ((col != null) && col.isScaleIcon()) {
        f = col.getIconScaleFactor();
      }

      rc.setScaleIcon(f > 0, f);
    }

    RenderableDataItem.VerticalAlign   va;
    RenderableDataItem.HorizontalAlign ha;

    if (top) {
      va = RenderableDataItem.VerticalAlign.TOP;
    } else {
      if (column) {
        va = c.getHeaderVerticalAlignment();
      } else {
        va = item.getVerticalAlignment();

        if (va == RenderableDataItem.VerticalAlign.AUTO) {
          if (row != null) {
            va = row.getVerticalAlignment();
          }
        }

        if ((col != null) && (va == RenderableDataItem.VerticalAlign.AUTO)) {
          va = col.getVerticalAlignment();
        }
      }
    }

    if (center) {
      ha = RenderableDataItem.HorizontalAlign.CENTER;
    } else {
      if (column) {
        ha = c.getHeaderHorizontalAlignment();
      } else {
        ha = item.getHorizontalAlignment();

        if (ha == RenderableDataItem.HorizontalAlign.AUTO) {
          if (row != null) {
            ha = row.getHorizontalAlignment();
          }
        }

        if ((col != null) && (ha == RenderableDataItem.HorizontalAlign.AUTO)) {
          ha = col.getHorizontalAlignment();
        }
      }
    }

    rc.setAlignment(ha, va);

    IconPosition ip;

    if (column) {
      ip = c.getHeaderIconPosition();
    } else {
      ip = item.getIconPosition();

      if (ip == RenderableDataItem.IconPosition.LEADING) {
        ip = RenderableDataItem.IconPosition.LEADING;
      }

      if (ip == RenderableDataItem.IconPosition.AUTO) {
        if (row != null) {
          ip = row.getIconPosition();
        }
      }

      if ((col != null) && (ip == RenderableDataItem.IconPosition.AUTO)) {
        ip = col.getIconPosition();
      }
    }

    rc.setIconPosition(ip);

    Orientation o = item.getOrientation();

    if (((o == null) || (o == Orientation.AUTO)) && (row != null)) {
      o = row.getOrientation();
    }

    if (((o == null) || (o == Orientation.AUTO)) && (col != null)) {
      o = col.getOrientation();
    }

    if (o != null) {
      rc.setOrientation(o);
    }
  }

  /**
   * Sets the value for a menu item. If the text contains an ampersand the
   * letter following the ampersand will be used as the mnemonic
   *
   * @param item
   *          the item
   * @param text
   *          the text
   * @return the text that was set
   */
  public static String setMnemonicAndText(aUIMenuItem item, String text) {
    char mn = 0;

    if (text == null) {
      text = "";
    }

    int len = text.length();
    int n   = -1;

    if ((len > 0) &&!text.startsWith("<html>")) {
      n = text.indexOf('_');
    }

    if ((n != -1) && (n < len - 1)) {
      if (n == 0) {
        mn   = text.charAt(1);
        text = text.substring(1);
      } else {
        mn   = text.charAt(n + 1);
        text = text.substring(0, n) + text.substring(n + 1);
      }
    }

    item.setText(text);

    if (mn != 0) {
      item.setMnemonic(mn);
    }

    return text;
  }

  /**
   * Sets the value for a menu item. If the text contains an ampersand the
   * letter following the ampersand will be used as the mnemonic
   *
   * @param item
   *          the item
   * @param text
   *          the text
   * @return the text that was set
   */
  public static String setMnemonicAndText(iActionComponent item, String text) {
    char mn = 0;

    if (text == null) {
      text = "";
    }

    int len = text.length();
    int n   = -1;

    if ((len > 0) &&!text.startsWith("<html>")) {
      n = text.indexOf('_');
    }

    if ((n != -1) && (n < len - 1)) {
      if (n == 0) {
        mn   = text.charAt(1);
        text = text.substring(1);
      } else {
        mn   = text.charAt(n + 1);
        text = text.substring(0, n) + text.substring(n + 1);
      }
    }

    item.setText(text);

    if (mn != 0) {
      item.setMnemonic(mn);
    }

    return text;
  }

  public static void setOrientation(iActionComponent component, int cfgOrientation) {
    switch(cfgOrientation) {
      case Label.COrientation.horizontal :
        component.setOrientation(Orientation.HORIZONTAL);

        break;

      case Label.COrientation.vertical_up :
        component.setOrientation(Orientation.VERTICAL_UP);

        break;

      case Label.COrientation.vertical_down :
        component.setOrientation(Orientation.VERTICAL_DOWN);

        break;

      default :
        break;
    }
  }

  public static void setWindowSizeAndLocationFromPartial(Frame frame, UIPoint partialLocation,
          UIDimension partialSize) {
    UIDimension d = frame.getComponent().getPreferredSize();

    if (partialSize != null) {
      if (partialSize.width > 0) {
        d.width = partialSize.width;
      }

      if (partialSize.height > 0) {
        d.height = partialSize.height;
      }

      frame.setSize(d.width, d.height);
    }

    if (partialLocation != null) {
      UIDimension sd = ScreenUtils.getScreenSize(frame.getComponent());

      if (partialLocation.x < 0) {
        partialLocation.x = (sd.width - d.width) / 2;
      }

      if (partialLocation.x < 0) {
        partialLocation.y = (sd.height - d.height) / 2;
      }

      frame.setLocation(partialLocation.x, partialLocation.y);
    }
  }

  public static UIRectangle getBounds(iPlatformComponent comp, Map options) {
    UIDimension screenSize = ScreenUtils.getScreenSize();
    Object      o          = options.get("left");
    float       left       = -1;
    float       top        = -1;
    float       height     = -1;
    float       width      = -1;

    if (o instanceof String) {
      left = ScreenUtils.toPlatformPixelWidth((String) o, comp, screenSize.width);
    } else if (o instanceof Number) {
      left = ((Number) o).intValue();
    }

    o = options.get("top");

    if (o instanceof String) {
      top = ScreenUtils.toPlatformPixelHeight((String) o, comp, screenSize.height);
    } else if (o instanceof Number) {
      top = ((Number) o).intValue();
    }

    o = options.get("height");

    if (o instanceof String) {
      height = ScreenUtils.toPlatformPixelHeight((String) o, comp, screenSize.height);
    } else if (o instanceof Number) {
      height = ((Number) o).intValue();
    }

    o = options.get("width");

    if (o instanceof String) {
      width = ScreenUtils.toPlatformPixelWidth((String) o, comp, screenSize.width);
    } else if (o instanceof Number) {
      width = ((Number) o).intValue();
    }

    if (screenSize.width < width) {
      width = screenSize.width - 10;
    }

    if (screenSize.height < height) {
      height = screenSize.height - 50;
    }

    if ((height == -1) && (width != -1)) {
      height = screenSize.height - 50;
    }

    if ((width == -1) && (height != -1)) {
      width = screenSize.width - 10;
    }

    return new UIRectangle(top, left, width, height);
  }

  public static String getDecimalSymbols() {
    DecimalFormatSymbols s  = new DecimalFormatSymbols();
    StringBuilder        sb = new StringBuilder();

    sb.append(s.getCurrencySymbol());
    sb.append(s.getDecimalSeparator());
    sb.append(s.getGroupingSeparator());
    sb.append(s.getMinusSign());
    sb.append(s.getPercent());

    return sb.toString();
  }

  public static int getFlags(String flags, Map<String, Integer> map) {
    int          flag = 0;
    List<String> list = CharScanner.getTokens(flags, '|', true);
    final int    len  = (list == null)
                        ? 0
                        : list.size();

    for (int i = 0; i < len; i++) {
      Integer n = map.get(list.get(i));

      if (n != null) {
        flag |= n;
      }
    }

    return flag;
  }

  public static RenderableDataItem.HorizontalAlign getHorizontalAlignment(int alignment) {
    switch(alignment) {
      case Label.CTextHAlignment.left :
        return RenderableDataItem.HorizontalAlign.LEFT;

      case Label.CTextHAlignment.center :
        return RenderableDataItem.HorizontalAlign.CENTER;

      case Label.CTextHAlignment.right :
        return RenderableDataItem.HorizontalAlign.RIGHT;

      case Label.CTextHAlignment.leading :
        return RenderableDataItem.HorizontalAlign.LEADING;

      case Label.CTextHAlignment.trailing :
        return RenderableDataItem.HorizontalAlign.TRAILING;

      default :
        return RenderableDataItem.HorizontalAlign.AUTO;
    }
  }

  public static RenderableDataItem.IconPosition getIconPosition(int position) {
    switch(position) {
      case Label.CIconPosition.left :
        return RenderableDataItem.IconPosition.LEFT;

      case Label.CIconPosition.right :
        return RenderableDataItem.IconPosition.RIGHT;

      case Label.CIconPosition.top_left :
        return RenderableDataItem.IconPosition.TOP_LEFT;

      case Label.CIconPosition.top_right :
        return RenderableDataItem.IconPosition.TOP_RIGHT;

      case Label.CIconPosition.top_center :
        return RenderableDataItem.IconPosition.TOP_CENTER;

      case Label.CIconPosition.bottom_left :
        return RenderableDataItem.IconPosition.BOTTOM_LEFT;

      case Label.CIconPosition.bottom_right :
        return RenderableDataItem.IconPosition.BOTTOM_RIGHT;

      case Label.CIconPosition.bottom_center :
        return RenderableDataItem.IconPosition.BOTTOM_CENTER;

      case Label.CIconPosition.trailing :
        return RenderableDataItem.IconPosition.TRAILING;

      case Label.CIconPosition.leading :
        return RenderableDataItem.IconPosition.LEADING;

      case Label.CIconPosition.right_justified :
        return RenderableDataItem.IconPosition.RIGHT_JUSTIFIED;

      default :
        return RenderableDataItem.IconPosition.AUTO;
    }
  }

  public static iPlatformIcon[] getIcons(iWidget context, String s, int max) {
    if ((s == null) || (s.length() == 0)) {
      return null;
    }

    iPlatformIcon[] icons = new iPlatformIcon[max];
    List<String>    list  = CharScanner.getTokens(s, ',', true);
    int             len   = list.size();

    icons[0] = (len > 0)
               ? context.getIcon(list.get(0), null)
               : null;
    icons[1] = (len > 1)
               ? context.getIcon(list.get(1), null)
               : null;
    icons[2] = (len > 2)
               ? context.getIcon(list.get(2), null)
               : null;
    icons[3] = (len > 3)
               ? context.getIcon(list.get(3), null)
               : null;

    return icons;
  }

  public static UIInsets getInsets(String s) {
    if ((s == null) || (s.length() == 0)) {
      return null;
    }

    List<String> list = CharScanner.getTokens(s, ',', true);
    int          len  = list.size();
    int          t    = (len > 0)
                        ? ScreenUtils.platformPixels(SNumber.floatValue(list.get(0)))
                        : 0;
    int          r    = (len > 1)
                        ? ScreenUtils.platformPixels(SNumber.floatValue(list.get(1)))
                        : 0;
    int          b    = (len > 2)
                        ? ScreenUtils.platformPixels(SNumber.floatValue(list.get(2)))
                        : 0;
    int          l    = (len > 3)
                        ? ScreenUtils.platformPixels(SNumber.floatValue(list.get(3)))
                        : 0;

    return new UIInsets(t, r, b, l);
  }

  public static List<RenderableDataItem> getItems(Object o, aWidget w, boolean copy) {
    if (o instanceof RenderableDataItem[]) {
      RenderableDataItem[] a = (RenderableDataItem[]) o;

      if (copy) {
        a = RenderableDataItem.deepCopy(a);
      }

      return Arrays.asList(a);
    }

    if (o instanceof Object[]) {
      Object[]                           a    = (Object[]) o;
      int                                len  = a.length;
      FilterableList<RenderableDataItem> list = new FilterableList<RenderableDataItem>(len);
      RenderableDataItem                 di;

      for (int i = 0; i < len; i++) {
        o = a[i];

        if (o instanceof RenderableDataItem) {
          di = ((RenderableDataItem) o);

          if (copy) {
            di = di.deepCopy();
          }
        } else {
          di = w.createItem(o);
        }

        list.add(di);
      }
    }

    if (copy && (o instanceof RenderableDataItem)) {
      o = ((RenderableDataItem) o).deepCopy();
    }

    return Collections.singletonList(w.createItem(o));
  }

  public static JavaURLConnection getLocaleSpecificConnection(String urlWithoutExtension, String ext)
          throws IOException {
    String[] lp = Helper.getLocalResourcePostfix(Locale.getDefault());

    if ((ext != null) && (ext.length() == 0)) {
      ext = null;
    }

    if ((lp != null) && (lp.length > 0)) {
      StringBuilder sb = new StringBuilder(urlWithoutExtension.length() + 10);

      sb.append(urlWithoutExtension);

      int sblen = sb.length();

      for (int i = 0; i < lp.length; i++) {
        sb.setLength(sblen);
        sb.append(lp[i]);

        if (ext != null) {
          sb.append('.').append(ext);
        }

        try {
          JavaURLConnection conn = new JavaURLConnection(new URL(sb.toString()).openConnection());

          return conn;
        } catch(IOException e) {}
      }
    }

    if (ext != null) {
      urlWithoutExtension = urlWithoutExtension + "." + ext;
    }

    return new JavaURLConnection(new URL(urlWithoutExtension).openConnection());
  }

  public static FileInputStream getLocaleSpecificFileInputStream(String fileWithoutExtension, String ext)
          throws IOException {
    String[] lp = Helper.getLocalResourcePostfix(Locale.getDefault());

    if ((ext != null) && (ext.length() == 0)) {
      ext = null;
    }

    if ((lp != null) && (lp.length > 0)) {
      StringBuilder sb = new StringBuilder(fileWithoutExtension.length() + 10);

      sb.append(fileWithoutExtension);

      int sblen = sb.length();

      for (int i = 0; i < lp.length; i++) {
        sb.setLength(sblen);
        sb.append(lp[i]);

        if (ext != null) {
          sb.append('.').append(ext);
        }

        try {
          File f = new File(sb.toString());

          if (f.exists()) {
            FileInputStream fin = new FileInputStream(f);

            return fin;
          }
        } catch(IOException e) {}
      }
    }

    if (ext != null) {
      fileWithoutExtension = fileWithoutExtension + "." + ext;
    }

    return new FileInputStream(fileWithoutExtension);
  }

  public static UIInsets getMargin(String s) {
    UIInsets in = getInsets(s);

    if (in != null) {
      float l = in.left;

      in.left = in.right;
      in.left = l;
    }

    return in;
  }

  public static void getProposedBoundsForLocation(UIRectangle r, int x, int y, UIDimension size) {
    UIDimension ss = ScreenUtils.getUsableScreenSize();

    r.x         = x;
    r.y         = y;
    r.width     = size.width;
    r.height    = size.height;
    size.width  = Math.min(ss.width, size.width);
    size.height = Math.min(ss.height, size.height);

    if (x + size.width > ss.width) {
      r.x = ss.width - size.width;
    }

    if (y + size.height > ss.height) {
      r.y = ss.height - size.height;
    }
  }

  public static void getProposedPopupBounds(UIRectangle r, iPlatformComponent owner, UIDimension contentSize,
          float popupFraction, HorizontalAlign contentAlignment, iPlatformBorder popupBorder, boolean scrollable) {
    UIDimension ss;
    UIPoint     loc;

    if (Platform.getUIDefaults().getBoolean("Rare.Popup.restrictToWindow", false)) {
      WindowViewer w = Platform.getWindowViewer(owner);

      if (w == null) {
        w = Platform.getWindowViewer();
      }

      loc   = owner.getLocationOnScreen();
      loc.x -= w.getScreenX();
      loc.y -= w.getScreenY();
      ss    = ScreenUtils.getUsableScreenSize(owner);

      float ww = ss.width;
      float hh = ss.height;

      ss = w.getSize();

      float tw = w.getScreenX() + ss.width;
      float th = w.getScreenY() + ss.height;

      if (tw > ww) {
        ss.width -= (tw - ww);
      }

      if (th > hh) {
        ss.height -= (th - hh);
      }
    } else {
      ss  = ScreenUtils.getUsableScreenSize(owner);
      loc = owner.getLocationOnScreen();
    }

    ss.width -= ScreenUtils.PLATFORM_PIXELS_4;

    UIDimension size        = owner.getOrientedSize(null);
    float       ownerWidth  = size.width;
    float       ownerHeight = size.height;
    float       ownerY      = loc.y;
    float       ownerX      = loc.x;
    float       width       = ownerWidth;
    float       height      = ownerHeight * 2;

    r.x = 0;
    r.y = 0;

    float n = ss.height - ownerHeight;

    if (n > (ss.height * 0.5f)) {    // if larger than 50% of the screen the set
      // the usable screen height to this amount
      ss.height = n;
    }

    if (popupFraction != 0) {
      width = (int) (ownerWidth * popupFraction);

      if (width < 1) {
        width = ownerWidth;
      }
    } else if (width < ownerWidth) {
      width = ownerWidth;
    }

    UIInsets in = (popupBorder == null)
                  ? EMPTY_INSETS
                  : popupBorder.getBorderInsets((UIInsets) null);

    if (contentSize != null) {
      size        = contentSize;
      size.width  += in.left + in.right;
      size.height += in.top + in.bottom;
      size.width  = Math.min(ss.width, size.width);
      size.height = Math.min(ss.height, size.height);

      switch(contentAlignment) {
        case LEADING :
        case LEFT :
          r.x   = 0;
          width = size.width;

          break;

        case RIGHT :
        case TRAILING :
          if (size.width < width) {
            r.x   = width - size.width;
            width = size.width;
          }

          break;

        case CENTER :
          if (size.width < width) {
            r.x   = (width - size.width) / 2;
            width = size.width;
          }

          break;

        default :
          if ((width < size.width) && (popupFraction == 0)) {
            width = size.width;
          }

          break;
      }

      height = size.height;
    }

    if (width > ss.width) {
      width = ss.width;
    }

    if (height > ss.height) {
      height = ss.height;
    }

    if (scrollable) {
      if (height + ownerHeight + ownerY > ss.height) {
        float bh = ss.height - ownerY - ownerHeight;

        if (bh > ownerY) {
          height = bh;
        } else if (height > bh) {
          height = Math.min(height, ownerY - ScreenUtils.PLATFORM_PIXELS_10);
        }
      }
    }

    if (popupFraction != 0) {
      r.x = (ownerWidth - width) / 2;
    } else if (width > ownerWidth) {
      if (width + ownerX > ss.width) {
        r.x = -(width - ownerWidth);

        if (r.x + ownerX < 0) {
          r.x = -ownerX;
        } else if (width > (ownerX + ownerWidth)) {
          r.x += (width - ownerX) + (int) ((ss.width - width) / 2) + ScreenUtils.PLATFORM_PIXELS_1;
        }
      }
    }

    if ((height + ownerHeight + ownerY) > ss.height) {
      r.y = -(height + ownerHeight);

      if (height > ownerY) {
        r.y += (height - ownerY) + (ss.height - height) / 2;

        if (r.x - ownerWidth >= 0) {
          r.x += ownerWidth;
        } else if (r.x + ownerWidth + width < ss.width) {
          if (r.x < 0) {
            r.x -= ownerWidth;
            r.x += ScreenUtils.PLATFORM_PIXELS_1;
          } else {
            r.x += ownerWidth;
          }
        }
      }
    }

    if (r.x + ownerX < ScreenUtils.PLATFORM_PIXELS_2) {
      r.x += ScreenUtils.PLATFORM_PIXELS_2;
    }

    r.x      = (float) Math.round(r.x);
    r.y      = (float) Math.round(r.y);
    r.width  = (float) Math.ceil(width);
    r.height = (float) Math.ceil(height);
  }

  public static UIRectangle getRectangle(String s) {
    if ((s == null) || (s.length() == 0)) {
      return null;
    }

    List<String> list = CharScanner.getTokens(s, ',', true);
    int          len  = list.size();
    UIRectangle  r    = new UIRectangle();

    r.x      = (len > 0)
               ? SNumber.intValue(list.get(0))
               : 0;
    r.y      = (len > 1)
               ? SNumber.intValue(list.get(1))
               : 0;
    r.width  = (len > 2)
               ? SNumber.intValue(list.get(2))
               : 0;
    r.height = (len > 3)
               ? SNumber.intValue(list.get(3))
               : 0;

    return r;
  }

  public static UIPoint getPoint(String s) {
    if ((s == null) || (s.length() == 0)) {
      return null;
    }

    int     n = s.indexOf(',');
    UIPoint p = new UIPoint();

    p.x = SNumber.intValue(s);

    if (n != -1) {
      p.y = SNumber.intValue(s.substring(n + 1));
    }

    return p;
  }

  public static RenderType getRenderType(int halign, int valign) {
    RenderType type = null;

    switch(valign) {
      case Widget.CVerticalAlign.top :
        switch(halign) {
          case Widget.CHorizontalAlign.center :
            type = RenderType.UPPER_MIDDLE;

            break;

          case Widget.CHorizontalAlign.left :
            type = RenderType.UPPER_LEFT;

            break;

          case Widget.CHorizontalAlign.right :
            type = RenderType.UPPER_RIGHT;

            break;

          case Widget.CHorizontalAlign.full :
            type = RenderType.STRETCH_WIDTH;

            break;
        }

        break;

      case Widget.CVerticalAlign.auto :
      case Widget.CVerticalAlign.center :
        switch(halign) {
          case Widget.CHorizontalAlign.center :
            type = RenderType.CENTERED;

            break;

          case Widget.CHorizontalAlign.left :
            type = RenderType.LEFT_MIDDLE;

            break;

          case Widget.CHorizontalAlign.right :
            type = RenderType.RIGHT_MIDDLE;

            break;

          case Widget.CHorizontalAlign.full :
            type = RenderType.STRETCH_WIDTH_MIDDLE;
        }

        break;

      case Widget.CVerticalAlign.bottom :
        switch(halign) {
          case Widget.CHorizontalAlign.center :
            type = RenderType.LOWER_MIDDLE;

            break;

          case Widget.CHorizontalAlign.left :
            type = RenderType.LOWER_LEFT;

            break;

          case Widget.CHorizontalAlign.right :
            type = RenderType.LOWER_RIGHT;

            break;

          case Widget.CHorizontalAlign.full :
            type = RenderType.STRETCH_WIDTH;

            break;
        }

        break;

      case Widget.CVerticalAlign.full :
        switch(halign) {
          case Widget.CHorizontalAlign.left :
            type = RenderType.STRETCH_HEIGHT;

            break;

          case Widget.CHorizontalAlign.full :
            type = RenderType.STRETCHED;

            break;

          case Widget.CHorizontalAlign.center :
            type = RenderType.STRETCH_HEIGHT_MIDDLE;

            break;
        }
    }

    return type;
  }

  public static RenderType[] getRenderTypes(String s, int max) {
    if ((s == null) || (s.length() == 0)) {
      return null;
    }

    RenderType[] rts  = new RenderType[max];
    List<String> list = CharScanner.getTokens(s, ',', true);
    int          len  = list.size();

    rts[0] = (len > 0)
             ? RenderType.valueOfEx(list.get(0))
             : null;
    rts[1] = (len > 1)
             ? RenderType.valueOfEx(list.get(1))
             : null;
    rts[2] = (len > 2)
             ? RenderType.valueOfEx(list.get(2))
             : null;
    rts[3] = (len > 3)
             ? RenderType.valueOfEx(list.get(3))
             : null;

    return rts;
  }

  public static void getSimpleSize(CharSequence cs, UIFont font, UIDimension size) {
    int clen  = 0;
    int mclen = 0;

    if (cs == null) {
      cs = "";
    }

    int           ln  = 0;
    int           len = cs.length();
    int           pos = 0;
    UIFontMetrics fm  = UIFontMetrics.getMetrics(font);

    for (int i = 0; i < len; i++) {
      Character ca = cs.charAt(i);

      if (ca == '\r') {
        continue;
      }

      if (ca == '\n') {
        String s = cs.subSequence(pos, i).toString();

        mclen = Math.max(fm.stringWidth(s), mclen);
        clen  = 0;
        pos   = i;
      } else {
        if (clen == 0) {
          pos = i;
          ln++;
        }

        clen++;
      }
    }

    if (ln == 0) {
      ln = 1;
    }

    if ((clen > 0) && (pos < len)) {
      String s = cs.subSequence(pos, len).toString();

      mclen = Math.max(fm.stringWidth(s), mclen);
    }

    size.width  = mclen + 4;
    size.height = ln + (int) FontUtils.getFontHeight(font, true);
  }

  public static UIDimension getSize(String s) {
    if ((s == null) || (s.length() == 0)) {
      return null;
    }

    char[] a   = s.toCharArray();
    int    len = a.length;
    int    x   = SNumber.intValue(a, 0, len, false);
    int    y   = 0;

    if (x < 0) {
      x = 0;
    }

    int i = CharArray.indexOf(a, 0, len, 'x', 0);

    if (i == -1) {
      i = CharArray.indexOf(a, 0, len, ',', 0);
    }

    if (i != -1) {
      i++;

      while((i < len) && (a[i] < 33)) {
        i++;
      }

      y = SNumber.intValue(a, i, len - (i), false);
    }

    return new UIDimension(ScreenUtils.platformPixels(x), ScreenUtils.platformPixels(y));
  }

  /**
   * Gets a slice of the specified image
   *
   * @param image
   *          the image
   * @param rect
   *          the rectangular slice
   *
   * @return the slice
   */
  public static UIImage getSlice(UIImage image, UIRectangle rect) {
    if ((rect.width == 0) && (rect.height == 0)) {
      return getSlice(image, (int) rect.x, (int) rect.y);
    } else {
      return image.getSubimage((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height);
    }
  }

  /**
   * Gets a slice of the specified image. The image is assumed to be made up of
   * uniform slices
   *
   * @param image
   *          the image
   * @param pos
   *          the position of the slice
   * @param size
   *          the uniform size of slices
   *
   * @return the slice
   */
  public static UIImage getSlice(UIImage image, int pos, int size) {
    int width  = image.getWidth();
    int height = image.getHeight();
    int h      = size;
    int w      = size;
    int x      = 0;
    int y      = 0;

    if (height > width) {
      w = width;
      y = pos * size;
    } else {
      h = height;
      x = pos * size;
    }

    return image.getSlice(x, y, w, h);
  }

  public static iPaintedButton.ButtonState getState(boolean enabled, boolean pressed, boolean selected,
          boolean mouseOver) {
    if (!enabled) {
      return selected
             ? iPaintedButton.ButtonState.DISABLED_SELECTED
             : iPaintedButton.ButtonState.DISABLED;
    }

    if (selected) {
      return pressed
             ? iPaintedButton.ButtonState.PRESSED_SELECTED
             : iPaintedButton.ButtonState.SELECTED;
    }

    if (pressed) {
      return iPaintedButton.ButtonState.PRESSED;
    }

    if (mouseOver) {
      return iPaintedButton.ButtonState.ROLLOVER;
    }

    return iPaintedButton.ButtonState.DEFAULT;
  }

  public static String getTarget(Link link) {
    String s = link.regionName.getValue();

    if (s != null) {
      s = s.trim();
    }

    if ((s != null) && (s.length() > 0)) {
      return s;
    }

    switch(link.target.intValue()) {
      case Link.CTarget._null :
        return iTarget.TARGET_NULL;

      case Link.CTarget._self :
        return iTarget.TARGET_SELF;

      case Link.CTarget._parent :
        return iTarget.TARGET_PARENT;

      case Link.CTarget._new_window :
        return iTarget.TARGET_NEW_WINDOW;

      case Link.CTarget._new_popup :
        return iTarget.TARGET_NEW_POPUP;

      case Link.CTarget._workspace :
        return iTarget.TARGET_WORKSPACE;

      default :
        return link.regionName.getValue();
    }
  }

  /**
   * Gets the target that specified component belongs to
   *
   * @param c
   *          the component
   *
   * @return the target that specified component belongs to
   */
  public static iTarget getTargetForComponent(iPlatformComponent c) {
    return (iTarget) c.getClientProperty(iConstants.RARE_TARGET_COMPONENT_PROPERTY);
  }

  public static RenderableDataItem.VerticalAlign getVerticalAlignment(int alignment) {
    switch(alignment) {
      case Label.CTextVAlignment.top :
        return RenderableDataItem.VerticalAlign.TOP;

      case Label.CTextVAlignment.center :
        return RenderableDataItem.VerticalAlign.CENTER;

      case Label.CTextVAlignment.bottom :
        return RenderableDataItem.VerticalAlign.BOTTOM;

      default :
        return RenderableDataItem.VerticalAlign.AUTO;
    }
  }

  public static Viewer getViewerConfiguration(Widget wc) {
    if (wc == null) {
      return null;
    } else {
      if (wc instanceof Viewer) {
        return (Viewer) wc;
      }

      WidgetPane wcfg = new WidgetPane();

      wcfg.widget.setValue(wc);

      return wcfg;
    }
  }

  public static iWidget getWidget(iWidget context, Widget wc) {
    if (wc == null) {
      return null;
    }

    return aContainer.createWidget(context.getContainerViewer(), wc);
  }

  public static int getWidgetCount(iTabPaneViewer tpv) {
    int len   = tpv.getTabCount();
    int count = 0;

    for (int i = 0; i < len; i++) {
      if (tpv.getTabDocument(i).getTarget().getViewer() != null) {
        count++;
      }
    }

    return count;
  }

  public static boolean isInValidSet(String validSet, CharSequence value, boolean checkDigit) {
    if ((validSet != null) && (value != null)) {
      int  len = value.length();
      char c;

      for (int i = 0; i < len; i++) {
        c = value.charAt(i);

        if (checkDigit && Character.isDigit(c)) {
          return true;
        }

        if (validSet.indexOf(c) == -1) {    // use charAt because in general it
          // should be only one char
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Returns whether the specified URL is a valid base URL
   *
   * @param url
   *          the URL
   * @return true if the specified URL is a valid base URL; false otherwise
   */
  public static boolean isValidBaseURL(URL url) {
    if (url == null) {
      return false;
    }

    String protocol = url.getHost();    // test for custom protocols and no stream
    // handler permissions

    if (iConstants.INLINE_PROTOCOL_HOSTSTRING.equals(protocol)
        || iConstants.SCRIPT_PROTOCOL_HOSTSTRING.equals(protocol)
        || iConstants.COLLECTION_PROTOCOL_HOSTSTRING.equals(protocol)) {
      return false;
    }

    protocol = url.getProtocol();

    return !iConstants.INLINE_PROTOCOL_STRING.equals(protocol) &&!iConstants.SCRIPT_PROTOCOL_STRING.equals(protocol)
           &&!iConstants.COLLECTION_PROTOCOL_STRING.equals(protocol);
  }

  private static void substract(final UIRectangle rect, final UIRectangle isection, final ArrayList remainders) {
    float isectionRight  = isection.x + isection.width;
    float rectRight      = rect.x + rect.width;
    float isectionBottom = isection.y + isection.height;
    float rectBottom     = rect.y + rect.height;

    if (isection.y > rect.y) {
      remainders.add(new UIRectangle(rect.x, rect.y, rect.width, isection.y - rect.y));
    }

    if (isection.x > rect.x) {
      remainders.add(new UIRectangle(rect.x, isection.y, isection.x - rect.x, isection.height));
    }

    if (isectionRight < rectRight) {
      remainders.add(new UIRectangle(isectionRight, isection.y, rectRight - isectionRight, isection.height));
    }

    if (isectionBottom < rectBottom) {
      remainders.add(new UIRectangle(rect.x, isectionBottom, rect.width, rectBottom - isectionBottom));
    }
  }
}
