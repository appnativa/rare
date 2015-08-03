/*
 * @(#)SwingHelper.java   2012-01-09
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.util;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToolTip;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.UIResource;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.View;

import org.jdesktop.swingx.graphics.BlendComposite;
import org.jdesktop.swingx.graphics.BlendComposite.BlendingMode;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.platform.swing.Rare;
import com.appnativa.rare.platform.swing.ui.view.JButtonEx;
import com.appnativa.rare.platform.swing.ui.view.JViewportEx;
import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.platform.swing.ui.view.ScrollPaneEx;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.GraphicsComposite;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iComposite.CompositeType;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iRenderingComponent;
import com.appnativa.rare.ui.dnd.TransferFlavor;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.viewer.FormViewer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTEnumerated;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;
import com.appnativa.util.ISO88591Helper;
import com.appnativa.util.UTF8Helper;

/**
 *
 * @author Don DeCoteau
 */
public class SwingHelper {
  public final static String            RARE_DATAFLAVOR_MIME_TYPE = DataFlavor.javaJVMLocalObjectMimeType + ";class="
                                                                      + Rare.class.getName();

  /** solid stroke */
  public static final Stroke            SOLID_STROKE              = new BasicStroke(1);
  public static final Stroke            FOCUS_STROKE              = new BasicStroke(1f, BasicStroke.CAP_BUTT,
                                                                      BasicStroke.JOIN_MITER, 1f, new float[] { 1.25f,1.25f }, 0f);

  /** dotted stroke */
  public static final Stroke            DOTTED_STROKE             = new BasicStroke(1f, BasicStroke.CAP_BUTT,
                                                                      BasicStroke.JOIN_MITER, 1f, new float[] { 1.25f,1.25f  }, 0f);

  /** dashed stroke */
  public static final Stroke            DASHED_STROKE             = new BasicStroke(1f, BasicStroke.CAP_BUTT,
                                                                      BasicStroke.JOIN_MITER, 1f, new float[] { 2f,2f }, 0f);

  public static final Stroke            HALF_SOLID_STROKE              = new BasicStroke(.5f);

  /** dotted stroke */
  public static final Stroke            HALF_DOTTED_STROKE             = new BasicStroke(.5f, BasicStroke.CAP_BUTT,
                                                                      BasicStroke.JOIN_MITER, 1f, new float[] { 1.25f,1.25f }, 0f);

  /** dashed stroke */
  public static final Stroke            HALF_DASHED_STROKE             = new BasicStroke(.5f, BasicStroke.CAP_BUTT,
                                                                      BasicStroke.JOIN_MITER, 1f, new float[] { 2f,2f }, 0f);
  final static int                      AUTOSCROLL_INSET_SIZE     = 20;
  final static int                      SCROLL_AMOUNT             = 10;
  static RenderingHints                 renderingHints            = null;
  static ColorUIResource                backgroundColorResource;
  protected static final String         ACTION_NAME_DECREMENT     = "decrement";
  protected static final String         ACTION_NAME_INCREMENT     = "increment";
  private static JToolTip               toolTip                   = new JToolTip();
  private static ThreadLocal<Rectangle> perThreadViewRect         = new ThreadLocal<Rectangle>() {
                                                                    @Override
                                                                    protected synchronized Rectangle initialValue() {
                                                                      return new Rectangle();
                                                                    }
                                                                  };
  private static ThreadLocal<Rectangle> perThreadTextRect         = new ThreadLocal<Rectangle>() {
                                                                    @Override
                                                                    protected synchronized Rectangle initialValue() {
                                                                      return new Rectangle();
                                                                    }
                                                                  };
  private static ThreadLocal<Insets>    perThreadInsets           = new ThreadLocal<Insets>() {
                                                                    @Override
                                                                    protected synchronized Insets initialValue() {
                                                                      return new Insets(0, 0, 0, 0);
                                                                    }
                                                                  };
  private static ThreadLocal<Rectangle> perThreadIconRect         = new ThreadLocal<Rectangle>() {
                                                                    @Override
                                                                    protected synchronized Rectangle initialValue() {
                                                                      return new Rectangle();
                                                                    }
                                                                  };

  /**
   * Used to tell a text component, being used as an editor for table or tree,
   * how many clicks it took to start editing.
   */
  private static final StringBuilder    SKIP_CLICK_COUNT          = new StringBuilder("skipClickCount");
  private static FontMetrics            toolTipFontMetrics;

  static {
    try {
      Toolkit tk = Toolkit.getDefaultToolkit();

      renderingHints = (RenderingHints) (tk.getDesktopProperty("awt.font.desktophints"));
      tk.addPropertyChangeListener("awt.font.desktophints", new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          if (evt.getNewValue() instanceof RenderingHints) {
            renderingHints = (RenderingHints) evt.getNewValue();
          }
        }
      });
    } catch (Throwable ignore) {
    }
  }

  private SwingHelper() {
  }

  @SuppressWarnings("resource")
  public static void addFileNameExtensionFilters(JFileChooser chooser, String filters) {
    chooser.resetChoosableFileFilters();

    if ((filters == null) || (filters.length() == 0)) {
      return;
    }

    CharScanner sc = new CharScanner(filters);
    List<String> a = sc.getTokens(';');
    List<String> b;
    int len = a.size();
    String s;
    int n;

    for (int i = 0; i < len; i++) {
      s = a.get(i).trim();
      n = s.indexOf('=');

      if (n == -1) {
        continue;
      }

      sc.reset(s.substring(n + 1).trim());

      try {
        s = CharScanner.cleanQuoted(s.toCharArray(), 0, n);
      } catch (ParseException ex) {
        s = s.substring(0, n);
      }

      b = sc.getTokens(',');

      if ((b.size() == 0) || (b.get(0).length() == 0)) {
        continue;
      }

      chooser.addChoosableFileFilter(new FileNameExtensionFilter(s, b));
    }
  }

  public static Map<iViewer, Object> addViewerReference(iViewer v, String viewerKey) {
    iPlatformAppContext app = v.getAppContext();
    Map<iViewer, Object> map = (Map<iViewer, Object>) app.getData(viewerKey);

    if (map == null) {
      map = new WeakHashMap<iViewer, Object>();
      app.putData(viewerKey, map);
    }

    synchronized (map) {
      map.put(v, null);
    }

    return map;
  }

  public static void calculateLabelRects(JLabel label, Rectangle viewR, Rectangle iconR, Rectangle textR, Insets insets,
      int viewWidth, int viewHeight) {
    String text = label.getText();
    int width = (viewWidth == -1) ? label.getWidth() : viewWidth;
    int height = (viewHeight == -1) ? label.getHeight() : viewHeight;

    insets = label.getInsets(insets);
    viewR.x = insets.left;
    viewR.y = insets.top;
    viewR.width = width - (insets.left + insets.right);
    viewR.height = height - (insets.top + insets.bottom);

    final int igap = label.getIconTextGap();
    final int htp = label.getHorizontalTextPosition();
    final int vtp = label.getVerticalTextPosition();
    final Icon icon = label.isEnabled() ? label.getIcon() : label.getDisabledIcon();

    if ((viewWidth != -1) && (viewHeight != -1)) {
      View view = (View) label.getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

      if (view != null) {
        if (icon != null) {
          if ((htp == JLabel.CENTER) && ((vtp == JLabel.TOP) || (vtp == JLabel.BOTTOM))) {
            viewR.height -= (igap + icon.getIconHeight());
          } else {
            viewR.width -= (igap + icon.getIconWidth());
          }
        }

        view.setSize(viewR.width, viewR.height);
      }
    }

    iconR.x = iconR.y = iconR.width = iconR.height = 0;
    textR.x = textR.y = textR.width = textR.height = 0;

    Font f = label.getFont();

    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    SwingUtilities.layoutCompoundLabel(label, label.getFontMetrics(f), text, icon, label.getVerticalAlignment(),
        label.getHorizontalAlignment(), vtp, htp, viewR, iconR, textR, igap);
    textR.x -= insets.left;
  }

  public static void center(Component parent, Component comp) {
    comp.setLocation(getCenterPoint(parent, comp));
  }

  public static void centerOnScreen(Component win) {
    int n = getMonitor(win);
    UIRectangle screenSize = PlatformHelper.getUsableScreenBounds((n < 0) ? 0 : n);
    Dimension winSize = win.getSize();
    float x = screenSize.width / 2 - (winSize.width / 2) + screenSize.x;
    float y = screenSize.height / 2 - (winSize.height / 2) + screenSize.y;

    if (x < screenSize.x) {
      x = screenSize.x;
    }

    if (y < screenSize.y) {
      y = screenSize.y;
    }

    win.setLocation((int)x, (int)y);
  }

  /**
   * Closes all open browser windows
   * 
   * @param app
   *          the application context
   */
  public static void closePopupWindows(iPlatformAppContext app, String viewerKey) {
    if (app.isShuttingDown()) {
      return;
    }

    Map<iViewer, Object> map = (Map<iViewer, Object>) app.getData(viewerKey);

    if (map == null) {
      return;
    }

    app.putData(viewerKey, null);

    synchronized (map) {
      iViewer v;
      Iterator<iViewer> it = map.keySet().iterator();

      while (it.hasNext()) {
        v = it.next();

        if (v != null) {
          v.dispose();
        }
      }
    }

    map.clear();
  }

  /**
   * Configures keystroke mappings for a widget
   *
   * @param w
   *          the widget context
   * @param comp
   *          the component to attach the mappings to
   * @param set
   *          the set of keystroke mappings
   * @param mapType
   *          the map type
   */
  public static void configureKeystrokes(iWidget w, JComponent comp, String set, int mapType) {
    if ((set == null) || (set.length() == 0)) {
      return;
    }

    comp.putClientProperty(iConstants.RARE_KEYSTROKES_PROPERTY, set);

    Map<String, String> map = CharScanner.parseOptionStringEx(set, ',');
    Iterator<Entry<String, String>> it = map.entrySet().iterator();
    Entry<String, String> e;
    String name, code;
    iPlatformAppContext app = AppContext.getContext();
    String err = app.getResourceAsString("Rare.runtime.text.noSuchKeystroke");

    while (it.hasNext()) {
      e = it.next();
      name = e.getKey();
      code = e.getValue();

      if ((code == null) || (code.length() == 0)) {
        continue;
      }

      if (!ActionHelper.registerKeystroke(w, comp, name, code, mapType)) {
        Platform.ignoreException("registerKeyStroke", new ApplicationException(err, name));
      }
    }
  }

  public static void configureScrollBar(iWidget context, JScrollPane pane, JScrollBar b, SPOTEnumerated cfg) {
    String ccode = cfg.spot_getAttribute("onChange");

    if ((ccode != null)) {
      b.setModel(new DefaultBoundedRangeModelEx(b, ccode));
    }

    String s = cfg.spot_getAttribute("minimum");

    if ((s != null) && (s.length() > 0)) {
      try {
        b.setMinimum(Integer.parseInt(s));
      } catch (NumberFormatException ignore) {
      }
    }

    s = cfg.spot_getAttribute("maximum");

    if ((s != null) && (s.length() > 0)) {
      try {
        b.setMaximum(Integer.parseInt(s));
      } catch (NumberFormatException ignore) {
      }
    }

    s = cfg.spot_getAttribute("extent");

    if ((s != null) && (s.length() > 0)) {
      try {
        b.setVisibleAmount(Integer.parseInt(s));
      } catch (NumberFormatException ignore) {
      }
    }

    s = cfg.spot_getAttribute("ui");

    if ((s != null) && (s.indexOf('.') != -1)) {
      b.setUI((ScrollBarUI) Platform.createObject(s));
    }

    s = cfg.spot_getAttribute("opaque");

    if ("false".equals(s)) {
      b.setOpaque(false);
    }

    if (pane instanceof ScrollPaneEx) {
      ScrollPaneEx sp = (ScrollPaneEx) pane;

      if (b == pane.getHorizontalScrollBar()) {
        if (cfg.spot_getAttribute("adjPrefSizeForHidden") != null) {
          sp.setAdjustPrefSizeForHiddenHoriz("true".equals(cfg.spot_getAttribute("adjPrefSizeForHidden")));
        }
      } else {
        if (cfg.spot_getAttribute("adjPrefSizeForHidden") != null) {
          sp.setAdjustPrefSizeForHiddenVert("true".equals(cfg.spot_getAttribute("adjPrefSizeForHidden")));
        }
      }
    }

    ccode = cfg.spot_getAttribute("onConfigure");

    if (ccode != null) {
      DataEvent e = new DataEvent(b, cfg);

      aWidgetListener.evaluate(context, context.getScriptHandler(), ccode, e);
    }
  }

  public static void configureScrollPane(iWidget context, JScrollPane pane, ScrollPane cfg) {
    if ((pane != null) && (cfg != null)) {
      configureScrollPanePolicy(context, pane, cfg);
      configureScrollPaneHeaderFooter(context, pane, cfg);
      configureScrollPaneCorners(context, pane, cfg);

      if (!context.isDesignMode()) {
        configureScrollBar(context, pane, pane.getHorizontalScrollBar(), cfg.horizontalScrollbar);
        configureScrollBar(context, pane, pane.getVerticalScrollBar(), cfg.verticalScrollbar);
      }
    }
  }

  /**
   * Configures scroll pane corner based on the value of a ui property
   *
   * @param pane
   *          the scroll pane
   * @param o
   *          property value
   * @param corner
   *          the corner
   */
  public static void configureScrollPaneCornerFromUIProperty(JScrollPane pane, Object o, String corner) {
    if (o != null) {
      ActionComponent l = new ActionComponent(new LabelView());

      if (o instanceof UIColor) {
        l.setBackground((UIColor) o);
      } else if (o instanceof iBackgroundPainter) {
        UIComponentPainter.setBackgroundPainter(l, (iBackgroundPainter) o);
      } else if (o instanceof PaintBucket) {
        ((PaintBucket) o).install(l);
      } else if (o instanceof iPlatformPainter) {
        UIComponentPainter.setBackgroundOverlayPainter(l, (iPlatformPainter) o);
      }

      pane.setCorner(corner, l.getView());
    }
  }

  /**
   * Configures scroll pane corners based on the specified configuration
   *
   * @param context
   *          the widget context
   * @param pane
   *          the scroll pane
   * @param cfg
   *          the configuration
   */
  public static void configureScrollPaneCorners(iWidget context, JScrollPane pane, ScrollPane cfg) {
    Widget wc = null;
    iWidget w;

    wc = (Widget) cfg.urCorner.getValue();

    if (wc != null) {
      w = FormViewer.createWidget( context.getContainerViewer(), wc);

      if (w != null) {
        w.setFocusable(false);
        context.getFormViewer().registerNamedItem(w.getName(), w);
        pane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, w.getContainerComponent().getView());
      }
    }

    wc = (Widget) cfg.lrCorner.getValue();

    if (wc != null) {
      w = FormViewer.createWidget( context.getContainerViewer(), wc);

      if (w != null) {
        w.setFocusable(false);
        context.getFormViewer().registerNamedItem(w.getName(), w);
        pane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, w.getContainerComponent().getView());
      }
    }
  }

  public static void configureScrollPaneHeaderFooter(iWidget context, JScrollPane pane, ScrollPane cfg) {
    Widget wc = null;
    iWidget w;

    wc = (Widget) cfg.columnHeader.getValue();

    if (wc != null) {
      w = FormViewer.createWidget(context.getContainerViewer(), wc);

      if (w != null) {
        pane.setColumnHeaderView(w.getContainerComponent().getView());
        context.getFormViewer().registerNamedItem(w.getName(), w);
      }
    }

    wc = (Widget) cfg.rowHeader.getValue();

    if (wc != null) {
      w = FormViewer.createWidget( context.getContainerViewer(), wc);

      if (w != null) {
        context.getFormViewer().registerNamedItem(w.getName(), w);
        pane.setRowHeaderView(w.getContainerComponent().getView());
      }
    }

    if (pane instanceof ScrollPaneEx) {
      wc = (Widget) cfg.columnFooter.getValue();

      if (wc != null) {
        w = FormViewer.createWidget(context.getContainerViewer(), wc);

        if (w != null) {
          context.getFormViewer().registerNamedItem(w.getName(), w);
          ((ScrollPaneEx) pane).setColumnFooterView(w.getContainerComponent().getView());
        }
      }

      wc = (Widget) cfg.rowFooter.getValue();

      if (wc != null) {
        w = FormViewer.createWidget( context.getContainerViewer(), wc);

        if (w != null) {
          context.getFormViewer().registerNamedItem(w.getName(), w);
          ((ScrollPaneEx) pane).setRowFooterView(w.getContainerComponent().getView());
        }
      }
    }
  }

  /**
   * Configures scroll pane policy based on the specified configuration
   *
   * @param context
   *          the widget context
   * @param pane
   *          the scroll pane
   * @param cfg
   *          the configuration
   */
  public static void configureScrollPanePolicy(iWidget context, JScrollPane pane, ScrollPane cfg) {
    if (pane.getViewport() instanceof JViewportEx) {
      ((JViewportEx) pane.getViewport()).setForceSizeMatch(true);
    }

    if (pane instanceof ScrollPaneEx) {
      ScrollPaneEx jpane = (ScrollPaneEx) pane;

      if (cfg.extendVerticalScrollbar.spot_valueWasSet()) {
        jpane.setExtendVerticalScrollbar(cfg.extendVerticalScrollbar.booleanValue());
      }

      if (cfg.extendHorizontalScrollbar.spot_valueWasSet()) {
        jpane.setExtendHorizontalScrollbar(cfg.extendHorizontalScrollbar.booleanValue());
      }
    }

    pane.setWheelScrollingEnabled(cfg.wheelScrollingAllowed.booleanValue());

    switch (cfg.horizontalScrollbar.intValue()) {
      case ScrollPane.CHorizontalScrollbar.hidden:
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        break;

      case ScrollPane.CHorizontalScrollbar.show_always:
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        break;

      default:
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        break;
    }

    switch (cfg.verticalScrollbar.intValue()) {
      case ScrollPane.CVerticalScrollbar.hidden:
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        break;

      case ScrollPane.CVerticalScrollbar.show_always:
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        break;

      default:
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        break;
    }
  }

  public static List<TransferFlavor> convertDataFlavorsToTransferFlavors(List<DataFlavor> list) {
    ArrayList<TransferFlavor> tlist = new ArrayList<TransferFlavor>(list.size());

    for (DataFlavor d : list) {
      tlist.add(new TransferFlavor(d.getHumanPresentableName(), d, d.getMimeType()));
    }

    return tlist;
  }

  public static void copyLabelAttributes(JLabel from, JLabel to) {
    to.setBackground(from.getBackground());
    to.setForeground(from.getForeground());
    to.setBorder(from.getBorder());
    to.setIcon(from.getIcon());
    to.setText(from.getText());
    to.setHorizontalAlignment(from.getHorizontalAlignment());
    to.setHorizontalTextPosition(from.getHorizontalTextPosition());
    to.setVerticalAlignment(from.getVerticalAlignment());
    to.setVerticalTextPosition(from.getVerticalTextPosition());
    to.setIconTextGap(from.getIconTextGap());
    to.setDisabledIcon(from.getDisabledIcon());
    to.setDisplayedMnemonic(from.getDisplayedMnemonic());
    to.setDisplayedMnemonicIndex(from.getDisplayedMnemonicIndex());
    to.setEnabled(from.isEnabled());
    to.setCursor(from.getCursor());
    to.setFont(from.getFont());
    to.setUI(from.getUI());
  }

  public static DataFlavor createSageDataFlavor(String name) {
    return new DataFlavor(RARE_DATAFLAVOR_MIME_TYPE, name);
  }

  /**
   * Implements autoscrolling.
   *
   * @param comp
   *          the component
   * @param cursorLocn
   *          the cursor location
   */
  public static void defaultAutoScroll(JComponent comp, Point cursorLocn) {
    Rectangle visible = comp.getVisibleRect();
    int x = 0, y = 0, width = 0, height = 0;

    // Scroll left.
    if (cursorLocn.x < visible.x + AUTOSCROLL_INSET_SIZE) {
      x = -SCROLL_AMOUNT;
      width = SCROLL_AMOUNT;
    } // Scroll right.
    else if (cursorLocn.x > visible.x + visible.width - AUTOSCROLL_INSET_SIZE) {
      x = visible.width + SCROLL_AMOUNT;
      width = SCROLL_AMOUNT;
    }

    // Scroll up.
    if (cursorLocn.y < visible.y + AUTOSCROLL_INSET_SIZE) {
      y = -SCROLL_AMOUNT;
      height = SCROLL_AMOUNT;
    } // Scroll down.
    else if (cursorLocn.y > visible.y + visible.height - AUTOSCROLL_INSET_SIZE) {
      y = visible.height + SCROLL_AMOUNT;
      height = SCROLL_AMOUNT;
    }

    ((JComponent) comp.getParent()).scrollRectToVisible(new Rectangle(x, y, width, height));
  }

  public static Insets defaultGetAutoscrollInsets(JComponent comp) {
    Rectangle visible = comp.getVisibleRect();
    Dimension size = comp.getSize();
    int top = 0, left = 0, bottom = 0, right = 0;

    if (visible.y > 0) {
      top = visible.y + AUTOSCROLL_INSET_SIZE;
    }

    if (visible.x > 0) {
      left = visible.x + AUTOSCROLL_INSET_SIZE;
    }

    if (visible.y + visible.height < size.height) {
      bottom = size.height - visible.y - visible.height + AUTOSCROLL_INSET_SIZE;
    }

    if (visible.x + visible.width < size.width) {
      right = size.width - visible.x - visible.width + AUTOSCROLL_INSET_SIZE;
    }

    return new Insets(top, left, bottom, right);
  }

  public static boolean deleteDirectory(File path) {
    if (path.exists()) {
      File[] files = path.listFiles();

      for (int i = 0; i < files.length; i++) {
        if (files[i].isDirectory()) {
          deleteDirectory(files[i]);
        } else {
          try {
            files[i].delete();
          } catch (Exception ignore) {
          }
        }
      }
    }

    try {
      return (path.delete());
    } catch (Exception ignore) {
      return false;
    }
  }

  public static void drawString(Graphics g, String text, int x, int y) {
    drawString(g, text, x, y, null, null);
  }

  public static void drawString(JComponent c, Graphics g, String s, Font f, Color fg) {
    if (fg == null) {
      fg = c.getForeground();
    }

    if (f == null) {
      f = c.getFont();
    }

    Insets in = c.getInsets();
    int height = c.getHeight();
    FontMetrics fm = c.getFontMetrics(f);
    int fh = fm.getHeight();
    int dy = (height - fh) / 2;

    drawString(g, s, in.left, height - dy - fm.getDescent(), f, fg);
  }

  public static void drawString(Graphics g, String text, int x, int y, Font font, Color color) {
    Graphics2D g2d = (Graphics2D) g;
    RenderingHints oldHints = null;
    Color oc = g2d.getColor();
    Font of = g2d.getFont();

    if (renderingHints != null) {
      oldHints = g2d.getRenderingHints();
      g2d.addRenderingHints(renderingHints);
    }

    if (font != null) {
      g2d.setFont(font);
    }

    if (color != null) {
      g2d.setColor(color);
    }

    g2d.drawString(text, x, y);

    if (oldHints != null) {
      g2d.addRenderingHints(oldHints);
    }

    g2d.setFont(of);
    g2d.setColor(oc);
  }

  /**
   * Fills a gradient using the start color and end color specified.
   *
   * @param g
   *          the gradient
   * @param s
   *          the shape to fill
   * @param start
   *          the start color
   * @param end
   *          the end color
   * @param vertical
   *          true for a vertical gradient; false for horizontal
   */
  public static void fillGradient(Graphics2D g, Shape s, Color start, Color end, boolean vertical) {
    Rectangle r = s.getBounds();
    Paint gp = new GradientPaint(0, 0, start, vertical ? 0 : r.width, vertical ? r.height : 0, end);
    Object o = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);

    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setPaint(gp);
    g.fill(s);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, o);
  }

  public static JScrollPane findScrollPane(Component c) {
    while (c != null) {
      if (c instanceof JScrollPane) {
        JScrollPane sp = (JScrollPane) c;

        if (((sp.getHorizontalScrollBar() != null) && sp.getHorizontalScrollBar().isVisible())
            || ((sp.getVerticalScrollBar() != null) && sp.getVerticalScrollBar().isVisible())) {
          return sp;
        }
      }

      c = c.getParent();
    }

    return null;
  }

  public static iViewer findViewer(iPlatformAppContext app, String viewerKey, String url) throws MalformedURLException {
    Map<iViewer, Object> map = (Map<iViewer, Object>) app.getData(viewerKey);

    if (map != null) {
      url = app.rewriteURL(app.getRootViewer(), app.getRootViewer().getURL(url), null);

      synchronized (map) {
        iViewer v;
        Iterator<iViewer> it = map.keySet().iterator();

        while (it.hasNext()) {
          v = it.next();

          if ((v != null) && url.equals(v.getSelection())) {
            return v;
          }
        }
      }
    }

    return null;
  }

  public static boolean focusFirstFocusableComponent(Component c) {
    if (c == null) {
      return false;
    }

    if (c instanceof JComponent) {
      if (((JComponent) c).isRequestFocusEnabled() && c.requestFocusInWindow()) {
        return true;
      }
    }

    if (c.isFocusable() && c.requestFocusInWindow()) {
      return true;
    }

    if (!(c instanceof Container)) {
      return false;
    }

    return focusFirstFocusableChild((Container) c);
  }

  public static void installPaint(PaintBucket pb, JComponent c) {
    if (pb.getBackgroundColor() != null) {
      c.setBackground(pb.getBackgroundColor());
    }

    if (pb.getForegroundColor() != null) {
      c.setForeground(pb.getForegroundColor());
    }

    if (pb.getBorder() != null) {
      c.setBorder(pb.getBorder());
    }

    if (pb.getFont() != null) {
      c.setFont(pb.getFont());
    }
  }

  public static JPanel labelComponent(String prompt, int psize, JComponent field) {
    JPanel panel = new javax.swing.JPanel();
    JLabel label = new JLabel(prompt);

    label.setHorizontalAlignment(SwingConstants.TRAILING);
    label.setOpaque(false);
    panel.setLayout(new java.awt.BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    panel.setOpaque(false);
    panel.add(label, java.awt.BorderLayout.LINE_START);
    panel.add(field, java.awt.BorderLayout.CENTER);

    if (psize != -1) {
      label.setPreferredSize(new Dimension(psize, -1));
    }

    return panel;
  }

  public static void makeComponentVisibleToUser(Component c) {
    if (c.isVisible()) {
      JScrollPane sp = null;
      Component cc = c.getParent();

      if (cc instanceof JViewport) {
        cc = cc.getParent();

        if (cc != null) {
          cc = cc.getParent();
        }
      }

      if (cc != null) {
        sp = findScrollPane(cc);
      }

      if ((sp != null) && sp.isShowing() && (sp.getHorizontalScrollBar().isVisible() || sp.getVerticalScrollBar().isVisible())) {
        JViewport vp = sp.getViewport();
        Point p = c.getLocation();

        p = SwingUtilities.convertPoint(c.getParent(), p, vp);
        p.x -= 2;
        p.y -= 2;

        Dimension d = vp.getExtentSize();
        Rectangle r = new Rectangle(p.x, p.y, c.getWidth(), c.getHeight());

        if (r.width > d.width) {
          r.width = d.width;
        }

        if (r.height > d.height) {
          r.height = d.height;
        }

        vp.scrollRectToVisible(r);
      }
    }
  }

  public static String makePassword(char[] passc) {
    MessageDigest md = null;

    try {
      md = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException ex) {
      return null;
    }

    int len = passc.length;
    byte[] b = UTF8Helper.getInstance().getBytes(passc, 0, len);
    byte[] dig = md.digest(b);
    int i = 0;

    while (i < len) {
      passc[i++] = 0;
    }

    len = b.length;
    i = 0;

    while (i < len) {
      b[i++] = 0;
    }

    return ISO88591Helper.getInstance().getString(dig);
  }

  public static MouseEvent newMouseEvent(Component comp, int id, MouseEvent e) {
    return new MouseEvent(comp, id, e.getWhen(), e.getModifiers() | e.getModifiersEx(), e.getX(), e.getY(), e.getClickCount(),
        e.isPopupTrigger());
  }

  public static void paintCenteredIcon(iPlatformGraphics g, iPlatformIcon icon, float x, float y, float width, float height) {
    float iw = icon.getIconWidth();
    float ih = icon.getIconHeight();

    icon.paintIcon(g.getView(), g.getGraphics(), (int) (x + (width - iw) / 2), (int) (y + (height - ih) / 2));
  }

  public static String readRegistryValue(boolean user, String path, String key) throws Exception {
    final Class clz = Class.forName("java.util.prefs.WindowsPreferences");
    final Field f = clz.getDeclaredField(user ? "userRoot" : "systemRoot");

    f.setAccessible(true);

    final Object root = f.get(null);
    final Method openKey = clz.getDeclaredMethod("openKey", byte[].class, int.class, int.class);

    openKey.setAccessible(true);

    final Method closeKey = clz.getDeclaredMethod("closeKey", int.class);

    closeKey.setAccessible(true);

    final Method winRegQueryValue = clz.getDeclaredMethod("WindowsRegQueryValueEx", int.class, byte[].class);

    winRegQueryValue.setAccessible(true);

    byte[] valb = null;
    String vals = null;
    Integer handle = -1;

    handle = (Integer) openKey.invoke(root, toCSTR(path), 0x20019, 0x20019);
    valb = (byte[]) winRegQueryValue.invoke(root, handle, toCSTR(key));
    vals = ((valb != null) ? new String(valb).trim() : null);
    closeKey.invoke(root, handle);

    return vals;
  }

  public static void removeViewerReference(iViewer v, String viewerKey) {
    iPlatformAppContext app = v.getAppContext();
    Map<iViewer, Object> map = (Map<iViewer, Object>) app.getData(viewerKey);

    if (map != null) {
      synchronized (map) {
        map.remove(v);
      }
    }
  }

  public static String replace(String s, String what, String with) {
    if ((what == null) || (s == null)) {
      return s;
    }

    int n = s.indexOf(what);

    if (n == -1) {
      return s;
    }

    if (with == null) {
      with = "";
    }

    int p = what.length();

    return s.substring(0, n) + with + s.substring(n + p);
  }

  public static void requestFocusLater(final Component c) {
    Platform.invokeLater(new Runnable() {
      @Override
      public void run() {
        c.requestFocusInWindow();
      }
    });
  }

  public static void retargetKeyEvent(int id, KeyEvent e, Component target) {
    if ((target == null) || ((target == e.getSource()) && (id == e.getID()))) {
      return;
    }

    target.dispatchEvent(e);
  }

  public static void retargetMouseEvent(int id, MouseEvent e, Component target) {
    if ((target == null) || ((target == e.getSource()) && (id == e.getID()))) {
      return;
    }

    if (e.isConsumed()) {
      return;
    }

    target.dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e, target));
  }

  /**
   * Runs the specified runnable, stopping the execution of the
   * 
   * @param r
   *          the runnable
   * @param timeout
   *          the number of milliseconds to wait before timing out
   * @return true if the task completed before the timeout; false otherwise
   */
  @SuppressWarnings("deprecation")
  public static boolean timedRun(Runnable r, int timeout) {
    Thread t = new Thread(r);

    t.start();

    try {
      t.join(timeout);
    } catch (InterruptedException ignore) {
    }

    boolean ok = !t.isAlive();

    if (!ok) {
      try {
        t.stop();
      } catch (Throwable ignore) {
      }
    }

    return ok;
  }

  public static UIRectangle toUIRectangle(Rectangle r) {
    return new UIRectangle(r.x, r.y, r.width, r.height);
  }

  public static void toggleTextActions(JTextComponent textComp) {
    Action a;

    try {
      if (textComp.getSelectionEnd() == textComp.getSelectionStart()) {
        a = textComp.getActionMap().get(DefaultEditorKit.copyAction);

        if (a != null) {
          a.setEnabled(false);
        }

        a = textComp.getActionMap().get(DefaultEditorKit.cutAction);

        if (a != null) {
          a.setEnabled(false);
        }
      } else {
        a = textComp.getActionMap().get(DefaultEditorKit.copyAction);

        if (a != null) {
          a.setEnabled(true);
        }

        a = textComp.getActionMap().get(DefaultEditorKit.cutAction);

        if (a != null) {
          a.setEnabled(textComp.isEditable());
        }
      }

      a = textComp.getActionMap().get(DefaultEditorKit.selectAllAction);

      if (a != null) {
        a.setEnabled(true);
      }
    } catch (Exception e) {
      Platform.ignoreException(null, e);
    }
  }

  public static void updateComponentTreeUI(JComponent c) {
    if (c != null) {
      SwingUtilities.updateComponentTreeUI(c);
    }
  }

  public static Font updateFont(JComponent comp, Font f, boolean b) {
    // TODO Auto-generated method stub
    return null;
  }

  public static Dimension setDimension(Dimension d, UIDimension ud) {
    if (d == null) {
      d = new Dimension();
    }

    d.setSize(ud.width, ud.height);

    return d;
  }

  /**
   * Sets the horizontal text alignment
   *
   * @param c
   *          the component (ABstractButton or JLabel, or iRenderingComponent)
   * @param align
   *          the alignment
   */
  public static void setHorizontalAlignment(Object c, HorizontalAlign align) {
    int alignment;

    switch (align) {
      case CENTER:
        alignment = SwingConstants.CENTER;

        break;

      case RIGHT:
        alignment = SwingConstants.RIGHT;

        break;

      case LEFT:
        alignment = SwingConstants.LEFT;

        break;

      case TRAILING:
        alignment = SwingConstants.TRAILING;

        break;

      default:
        alignment = SwingConstants.LEADING;

        break;
    }

    if (c instanceof iRenderingComponent) {
      c = ((iRenderingComponent) c).getComponent().getView();
    }

    if (c instanceof AbstractButton) {
      ((AbstractButton) c).setHorizontalAlignment(alignment);
    } else if (c instanceof JLabel) {
      ((JLabel) c).setHorizontalAlignment(alignment);
    }
  }

  /**
   * Sets the icon position
   *
   * @param c
   *          the component (ABstractButton or JLabel, or iRenderingComponent)
   * @param ip
   *          the icon position
   */
  public static void setIconPosition(Object c, IconPosition ip) {
    int vposition = -1;
    int position;
    if (c instanceof LabelView) {
      ((LabelView) c).setIconRightJustified(false);
    } else if (c instanceof JButtonEx) {
      ((JButtonEx) c).setIconRightJustified(false);
    }

    switch (ip) {
      case LEFT:
        position = SwingConstants.RIGHT;

        break;

      case RIGHT:
        position = SwingConstants.LEFT;

        break;

      case TRAILING:
        position = SwingConstants.LEADING;

        break;

      case TOP_CENTER:
        position = SwingConstants.CENTER;
        vposition = SwingConstants.BOTTOM;

        break;

      case BOTTOM_CENTER:
        position = SwingConstants.CENTER;
        vposition = SwingConstants.TOP;

        break;

      case BOTTOM_LEFT:
        position = SwingConstants.TRAILING;
        vposition = SwingConstants.BOTTOM;

        break;

      case BOTTOM_RIGHT:
        position = SwingConstants.LEADING;
        vposition = SwingConstants.BOTTOM;

        break;

      case TOP_LEFT:
        position = SwingConstants.TRAILING;
        vposition = SwingConstants.TOP;

        break;

      case TOP_RIGHT:
        position = SwingConstants.LEFT;
        vposition = SwingConstants.TOP;

        break;

      case RIGHT_JUSTIFIED:
        position = SwingConstants.LEADING;

        if (c instanceof LabelView) {
          ((LabelView) c).setIconRightJustified(true);
        } else if (c instanceof JButtonEx) {
          ((JButtonEx) c).setIconRightJustified(true);
        }

        break;

      default:
        position = SwingConstants.TRAILING;

        break;
    }

    if (c instanceof iRenderingComponent) {
      c = ((iRenderingComponent) c).getComponent().getView();
    }

    if (c instanceof AbstractButton) {
      ((AbstractButton) c).setHorizontalTextPosition(position);

      if (vposition != -1) {
        ((AbstractButton) c).setVerticalTextPosition(vposition);
      }
    } else if (c instanceof JLabel) {
      ((JLabel) c).setHorizontalTextPosition(position);

      if (vposition != -1) {
        ((JLabel) c).setVerticalTextPosition(vposition);
      }
    }
  }

  public static Insets setInsets(Insets insets, UIInsets uinsets) {
    if (insets == null) {
      return new Insets(uinsets.intTop(), uinsets.intLeft(), uinsets.intBottom(), uinsets.intRight());
    }

    insets.set(uinsets.intTop(), uinsets.intLeft(), uinsets.intBottom(), uinsets.intRight());

    return insets;
  }

  /**
   * Sets the {@code SKIP_CLICK_COUNT} client property on the component if it is
   * an instance of {@code JTextComponent} with a {@code DefaultCaret}. This
   * property, used for text components acting as editors in a table or tree,
   * tells {@code DefaultCaret} how many clicks to skip before starting
   * selection.
   * 
   * @param comp
   * @param count
   */
  public static void setSkipClickCount(Component comp, int count) {
    if ((comp instanceof JTextComponent) && ((JTextComponent) comp).getCaret() instanceof DefaultCaret) {
      ((JTextComponent) comp).putClientProperty(SKIP_CLICK_COUNT, count);
    }
  }

  public static UIDimension setUIDimension(UIDimension ud, Dimension d) {
    if (ud == null) {
      ud = new UIDimension();
    }

    ud.setSize(d.width, d.height);

    return ud;
  }

  public static UIInsets setUIInsets(UIInsets uinsets, Insets insets) {
    if (uinsets == null) {
      return new UIInsets(insets.top, insets.right, insets.bottom, insets.left);
    }

    uinsets.set(insets.top, insets.right, insets.bottom, insets.left);

    return uinsets;
  }

  public static UIPoint setUIPoint(UIPoint up, Point p) {
    if (up == null) {
      up = new UIPoint();
    }

    up.set(p.x, p.y);

    return up;
  }

   public static UIRectangle setUIRectangle(UIRectangle ur, Rectangle r) {
    if (ur == null) {
      ur = new UIRectangle();
    }

    ur.setBounds(r.x, r.y, r.width, r.height);

    return ur;
  }

  /**
   * Sets the vertical text alignment
   *
   * @param c
   *          the component (ABstractButton or JLabel, or iRenderingComponent)
   * @param align
   *          the alignment
   */
  public static void setVerticalAlignment(Object c, VerticalAlign align) {
    int alignment;

    switch (align) {
      case CENTER:
        alignment = SwingConstants.CENTER;

        break;

      case TOP:
        alignment = SwingConstants.TOP;

        break;

      case BOTTOM:
        alignment = SwingConstants.BOTTOM;

        break;

      default:
        alignment = SwingConstants.CENTER;

        break;
    }

    if (c instanceof iRenderingComponent) {
      c = ((iRenderingComponent) c).getComponent().getView();
    }

    if (c instanceof AbstractButton) {
      ((AbstractButton) c).setVerticalAlignment(alignment);
    } else if (c instanceof JLabel) {
      ((JLabel) c).setVerticalAlignment(alignment);
    }
  }

  public static Color getBackgroundColorResource() {
    if (backgroundColorResource == null) {
      backgroundColorResource = new ColorUIResource(ColorUtils.getBackground());
    }

    return backgroundColorResource;
  }

  public static Point getCenterPoint(Component parent, Component comp) {
    Dimension cSize = comp.getSize();
    Dimension pSize = parent.getSize();
    Point loc = parent.getLocationOnScreen();

    loc.x = ((pSize.width - cSize.width) / 2) + loc.x;
    loc.y = ((pSize.height - cSize.height) / 2) + loc.y;

    if (loc.x < 0) {
      loc.x = 0;
    }

    if (loc.y < 0) {
      loc.y = 0;
    }

    return loc;
  }

  public static long getDirectorySize(File path) {
    if (!path.isDirectory()) {
      return path.length();
    }

    long size = 0;

    if (path.exists()) {
      File[] files = path.listFiles();

      for (int i = 0; i < files.length; i++) {
        if (files[i].isDirectory()) {
          size += getDirectorySize(files[i]);
        } else {
          size += files[i].length();
        }
      }
    }

    return size;
  }

  public static File getFile(Component parent, String title, boolean open, boolean dironly) {
    return getFile(parent, title, open, dironly, null, null, null);
  }

  public static File getFile(Component parent, String title, boolean open, boolean dironly, File dir) {
    return getFile(parent, title, open, dironly, dir, null, null);
  }

  public static File getFile(Component parent, String title, boolean open, boolean dironly, File dir, String extfilters) {
    return getFile(parent, title, open, dironly, dir, extfilters, null);
  }

  public static File getFile(Component parent, String title, boolean open, boolean dironly, File dir, String extfilters,
      FileFilter filter) {
    boolean fdok = Platform.isMac() || !dironly;

    if (fdok && !Platform.getUIDefaults().getBoolean("Rare.FileChooser.useSwingAlways", false)) {
      return getFileWithNativeDialog(parent, title, open, dironly, dir, extfilters);
    }

    File file = null;
    ConfirmingFileChooser fileChooser = new ConfirmingFileChooser(dir);

    if (extfilters != null) {
      addFileNameExtensionFilters(fileChooser, extfilters);
    }

    if (filter != null) {
      fileChooser.addChoosableFileFilter(filter);
    }

    if (title != null) {
      fileChooser.setDialogTitle(title);
    }

    if (open) {
      if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
        file = fileChooser.getSelectedFile();
      }
    } else {
      if (dironly) {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      }

      if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
        file = fileChooser.getSelectedFile();
      }
    }

    return file;
  }

  public static File getFileWithNativeDialog(Component parent, String title, boolean open, boolean dironly, File dir,
      String extfilters) {
    File file = null;
    Window win = SwingUtilities.getWindowAncestor(parent);
    FileDialog fileChooser;

    if (win instanceof Dialog) {
      fileChooser = new FileDialog((Dialog) win);
    } else {
      fileChooser = new FileDialog((Frame) win);
    }

    if (title != null) {
      fileChooser.setTitle(title);
    }

    if (dir != null) {
      fileChooser.setDirectory(dir.getAbsolutePath());
    }

    fileChooser.setMode(open ? FileDialog.LOAD : FileDialog.SAVE);

    if (dironly && !Platform.isMac()) {
      fileChooser.setFilenameFilter(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          File f = new File(dir, name);

          return f.isDirectory();
        }
      });
    } else if (extfilters != null) {
      addFileNameExtensionFilters(fileChooser, extfilters);
    }

    try {
      if (dironly) {
        System.setProperty("apple.awt.fileDialogForDirectories", "true");
      } else {
        System.setProperty("apple.awt.fileDialogForDirectories", "false");
      }

      fileChooser.setVisible(true);
    } finally {
      if (dironly) {
        System.setProperty("apple.awt.fileDialogForDirectories", "false");
      }
    }

    if ((fileChooser.getDirectory() == null) || (fileChooser.getFile() == null)) {
      file = null;
    } else {
      file = new File(fileChooser.getDirectory(), fileChooser.getFile());
    }

    return file;
  }

  public static RenderableDataItem.HorizontalAlign getHorizontalAlignment(int alignment) {
    switch (alignment) {
      case Label.CTextHAlignment.left:
        return RenderableDataItem.HorizontalAlign.LEFT;

      case Label.CTextHAlignment.center:
        return RenderableDataItem.HorizontalAlign.CENTER;

      case Label.CTextHAlignment.right:
        return RenderableDataItem.HorizontalAlign.RIGHT;

      case Label.CTextHAlignment.leading:
        return RenderableDataItem.HorizontalAlign.LEADING;

      case Label.CTextHAlignment.trailing:
        return RenderableDataItem.HorizontalAlign.TRAILING;

      default:
        return RenderableDataItem.HorizontalAlign.AUTO;
    }
  }

  public static RenderableDataItem.IconPosition getIconPosition(int position) {
    switch (position) {
      case Label.CIconPosition.left:
        return RenderableDataItem.IconPosition.LEFT;

      case Label.CIconPosition.right:
        return RenderableDataItem.IconPosition.RIGHT;

      case Label.CIconPosition.top_left:
        return RenderableDataItem.IconPosition.TOP_LEFT;

      case Label.CIconPosition.top_right:
        return RenderableDataItem.IconPosition.TOP_RIGHT;

      case Label.CIconPosition.top_center:
        return RenderableDataItem.IconPosition.TOP_CENTER;

      case Label.CIconPosition.bottom_left:
        return RenderableDataItem.IconPosition.BOTTOM_LEFT;

      case Label.CIconPosition.bottom_right:
        return RenderableDataItem.IconPosition.BOTTOM_RIGHT;

      case Label.CIconPosition.bottom_center:
        return RenderableDataItem.IconPosition.BOTTOM_CENTER;

      case Label.CIconPosition.trailing:
        return RenderableDataItem.IconPosition.TRAILING;

      case Label.CIconPosition.leading:
        return RenderableDataItem.IconPosition.LEADING;

      case Label.CIconPosition.right_justified:
        return RenderableDataItem.IconPosition.RIGHT_JUSTIFIED;

      default:
        return RenderableDataItem.IconPosition.AUTO;
    }
  }

  public static Composite resolveInstance(GraphicsComposite composite) {
    Composite ac = SwingHelper.getInstance(composite.getCompositeType(), composite.getAlpha());
    composite.setPlatformComposite(ac);
    return ac;

  }

  public static Composite getInstance(CompositeType type, float alpha) {
    int rule = AlphaComposite.SRC_OVER;

    switch (type) {
      case DST_IN:
        rule = AlphaComposite.DST_IN;

        break;

      case DST_OUT:
        rule = AlphaComposite.DST_OUT;

        break;

      case DST_ATOP:
        rule = AlphaComposite.DST_ATOP;

        break;

      case DST_OVER:
        rule = AlphaComposite.DST_OVER;

        break;

      case SRC_IN:
        rule = AlphaComposite.SRC_IN;

        break;

      case SRC_OUT:
        rule = AlphaComposite.SRC_OUT;

        break;

      case SRC_ATOP:
        rule = AlphaComposite.SRC_ATOP;

        break;
      case XOR:
        rule = AlphaComposite.XOR;

        break;
      case LIGHTEN:
        return BlendComposite.getInstance(BlendingMode.LIGHTEN, alpha);
      case DARKEN:
        return BlendComposite.getInstance(BlendingMode.DARKEN, alpha);
      default:
        break;
    }

    return AlphaComposite.getInstance(rule, alpha);
  }

  public static KeyStroke getKeyStroke(String s) {
    if (s == null) {
      return null;
    }

    if (Platform.isMac()) {
      s = replace(s, "cmd", "meta");
    } else {
      s = replace(s, "cmd", "control");
    }

    return KeyStroke.getKeyStroke(s);
  }

  public static int getMonitor(Component c) {
    Window w = (c instanceof Window) ? (Window) c : SwingUtilities.windowForComponent(c);
    GraphicsConfiguration gc = (w == null) ? null : w.getGraphicsConfiguration();
    GraphicsDevice gd = (gc == null) ? null : gc.getDevice();
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] gs = ge.getScreenDevices();

    for (int i = 0; i < gs.length; i++) {
      if (gs[i].equals(gd)) {
        return i;
      }
    }

    return -1;
  }

  public static Point getPopupPoint(Component parent, Component comp) {
    Dimension pSize = parent.getSize();
    Point loc = parent.getLocationOnScreen();

    loc.y += pSize.height;

    return loc;
  }

  public static Stroke getStroke(UIStroke stroke) {
    return getStroke(stroke,false);
  }
  public static Stroke getStroke(UIStroke stroke,boolean half) {
    if (stroke == UIStroke.SOLID_STROKE) {
      return half ? HALF_SOLID_STROKE : SOLID_STROKE;
    }

    if (stroke == UIStroke.DOTTED_STROKE) {
      return half ? HALF_DOTTED_STROKE: DOTTED_STROKE;
    }

    if (stroke == UIStroke.DASHED_STROKE) {
      return half ? HALF_DASHED_STROKE: DASHED_STROKE;
    }

    int cap;
    int join;

    switch (stroke.cap) {
      case BUTT:
        cap = BasicStroke.CAP_BUTT;

        break;

      case ROUND:
        cap = BasicStroke.CAP_ROUND;

        break;

      default:
        cap = BasicStroke.CAP_SQUARE;

        break;
    }

    switch (stroke.join) {
      case BEVEL:
        join = BasicStroke.JOIN_BEVEL;

        break;

      case MITER:
        join = BasicStroke.JOIN_MITER;

        break;

      default:
        join = BasicStroke.JOIN_ROUND;

        break;
    }

    if ((stroke.dashIntervals != null) && (stroke.dashIntervals.length > 0)) {
      return new BasicStroke(stroke.width, cap, join, stroke.miterLimit, stroke.dashIntervals, stroke.dashPhase);
    } else {
      return new BasicStroke(stroke.width, cap, join, stroke.miterLimit);
    }
  }

  public static Rectangle getToolTipRect(JLabel label, String text, int width, int height, boolean overlapping) {
    Rectangle viewR = perThreadViewRect.get();
    Rectangle iconR = perThreadIconRect.get();
    Rectangle textR = perThreadTextRect.get();
    Insets insets = label.getInsets(perThreadInsets.get());

    viewR.x = insets.left;
    viewR.y = insets.top;
    viewR.width = width - (insets.left + insets.right);
    viewR.height = height - (insets.top + insets.bottom);
    iconR.x = iconR.y = iconR.width = iconR.height = 0;
    textR.x = textR.y = textR.width = textR.height = 0;

    Font f = null;

    if (!overlapping) {
      f = Platform.getUIDefaults().getFont("ToolTip.font");
    }

    if (f == null) {
      f = label.getFont();
    }

    if (toolTipFontMetrics == null) {
      toolTipFontMetrics = toolTip.getFontMetrics(f);
    } else {
      if (toolTipFontMetrics.getFont() != f) {
        toolTipFontMetrics = toolTip.getFontMetrics(f);
      }
    }

    Icon icon = label.isEnabled() ? label.getIcon() : label.getDisabledIcon();

    SwingUtilities.layoutCompoundLabel(label, toolTipFontMetrics, text, icon, label.getVerticalAlignment(),
        label.getHorizontalAlignment(), label.getVerticalTextPosition(), label.getHorizontalTextPosition(), viewR, iconR, textR,
        label.getIconTextGap());
    textR.x -= insets.left;
    insets = toolTip.getInsets(insets);
    textR.x -= insets.left;
    textR.y -= insets.top;

    return textR;
  }

  public static RenderableDataItem.VerticalAlign getVerticalAlignment(int alignment) {
    switch (alignment) {
      case Label.CTextVAlignment.top:
        return RenderableDataItem.VerticalAlign.TOP;

      case Label.CTextVAlignment.center:
        return RenderableDataItem.VerticalAlign.CENTER;

      case Label.CTextVAlignment.bottom:
        return RenderableDataItem.VerticalAlign.BOTTOM;

      default:
        return RenderableDataItem.VerticalAlign.AUTO;
    }
  }

  public static Map<iViewer, Object> getViewerReference(iPlatformAppContext app, String viewerKey, boolean create) {
    Map<iViewer, Object> map = (Map<iViewer, Object>) app.getData(viewerKey);

    if ((map == null) && create) {
      map = new WeakHashMap<iViewer, Object>();
      app.putData(viewerKey, map);
    }

    return map;
  }

  public static boolean isContinueImageUpdate(JComponent comp, Image img) {
    // TODO Auto-generated method stub
    return false;
  }

  public static boolean isHorizontalScrollBarHiddenAlways(JScrollPane pane) {
    return (pane != null) && (pane.getHorizontalScrollBarPolicy() == JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  }

  public static boolean isInTraversalEvent() {
    // try {
    // if (!Rare.isInSandbox() && EventQueue.isDispatchThread()) {
    // AWTEvent e = EventQueue.getCurrentEvent();
    //
    // if (e instanceof sun.awt.CausedFocusEvent) {
    // sun.awt.CausedFocusEvent ce = (sun.awt.CausedFocusEvent) e;
    //
    // switch(ce.getCause()) {
    // case TRAVERSAL_BACKWARD :
    // case TRAVERSAL_FORWARD :
    // case TRAVERSAL :
    // case TRAVERSAL_DOWN :
    // case TRAVERSAL_UP :
    // return true;
    //
    // default :
    // break;
    // }
    // }
    // }
    // } catch(Throwable e) {}
    return false;
  }

  public static boolean isNonRectangularBorder(Border border) {
    if (border instanceof iPlatformBorder) {
      return !((iPlatformBorder) border).isRectangular();
    }

    return false;
  }

  public static boolean isStandardBorder(Border b) {
    if (b instanceof UIResource) {
      return true;
    }

    while (b instanceof CompoundBorder) {
      b = ((CompoundBorder) b).getInsideBorder();
    }

    return (b instanceof UIResource);
  }

  public static boolean isVerticalScrollBarHiddenAlways(JScrollPane pane) {
    return (pane != null) && (pane.getVerticalScrollBarPolicy() == JScrollPane.VERTICAL_SCROLLBAR_NEVER);
  }

  @SuppressWarnings("resource")
  private static void addFileNameExtensionFilters(FileDialog fd, String filters) {
    if ((filters == null) || (filters.length() == 0)) {
      return;
    }

    CharScanner sc = new CharScanner(filters);
    List<String> a = sc.getTokens(';');
    List<String> b;
    int len = a.size();
    String s;
    int n;
    List<String> exts = new ArrayList<String>();

    for (int i = 0; i < len; i++) {
      s = a.get(i).trim();
      n = s.indexOf('=');

      if (n == -1) {
        sc.reset(s);
      } else {
        sc.reset(s.substring(n + 1).trim());
      }

      try {
        s = CharScanner.cleanQuoted(s.toCharArray(), 0, n);
      } catch (ParseException ex) {
        s = s.substring(0, n);
      }

      b = sc.getTokens(',');

      if ((b.size() == 0) || (b.get(0).length() == 0)) {
        continue;
      }

      exts.addAll(b);
    }

    if (exts.size() > 0) {
      if (Platform.isWindows()) {
        s = "*." + Helper.toString(exts, ";*.");
        fd.setFile(s);
      } else {
        fd.setFilenameFilter(new FileNameExtensionFilter("", exts));
      }
    }
  }

  private static boolean focusFirstFocusableChild(Container c) {
    int len = c.getComponentCount();

    for (int i = 0; i < len; i++) {
      if (focusFirstFocusableComponent(c)) {
        return true;
      }
    }

    return false;
  }

  private static byte[] toCSTR(String str) {
    byte[] result = new byte[str.length() + 1];

    for (int i = 0; i < str.length(); i++) {
      result[i] = (byte) str.charAt(i);
    }

    result[str.length()] = 0;

    return result;
  }

  public static class ConfirmingFileChooser extends JFileChooser {

    /**
     * Constructs a new instance
     *
     * @param dir
     *          the default directory
     */
    public ConfirmingFileChooser(File dir) {
      super((dir == null) ? new File(".") : dir);
    }

    @Override
    public void approveSelection() {
      if (getDialogType() == SAVE_DIALOG) {
        File file = getSelectedFile();

        if (!file.isDirectory() && file.exists()) {
          StringBuilder sb = new StringBuilder();

          sb.append("File ");
          sb.append(file.getPath());
          sb.append(" already exists.\n\nReplace it?");

          int overwrite = JOptionPane.showConfirmDialog(this, sb, "Overwrite", JOptionPane.OK_CANCEL_OPTION);

          if (overwrite == JOptionPane.CANCEL_OPTION) {
            return;
          }
        }
      }

      super.approveSelection();
    }
  }

  public static class DefaultBoundedRangeModelEx extends DefaultBoundedRangeModel {
    int             direction = 1;
    boolean         blockRecursion;
    Object          changeCode;
    WeakReference   reference;
    private boolean blockEvents;

    public DefaultBoundedRangeModelEx(JComponent comp, Object changeCode) {
      reference = new WeakReference(comp);
      this.changeCode = changeCode;
    }

    public void fireStateChanged(boolean blockRecursion) {
      this.blockRecursion = blockRecursion;

      try {
        super.fireStateChanged();
      } finally {
        this.blockRecursion = false;
      }
    }

    @Override
    public String toString() {
      String modelString = "dir=" + getDirection() + ", " + "value=" + getValue() + ", " + "extent=" + getExtent() + ", " + "min="
          + getMinimum() + ", " + "max=" + getMaximum() + ", " + "adj=" + getValueIsAdjusting();

      return "DefaultBoundedRangeModelEx[" + modelString + "]";
    }

    /**
     * @param blockEvents
     *          the blockEvents to set
     */
    public void setBlockEvents(boolean blockEvents) {
      this.blockEvents = blockEvents;
    }

    @Override
    public void setValue(int n) {
      direction = (n < getValue()) ? -1 : 1;
      super.setValue(n);
    }

    public JComponent getComponent() {
      return (JComponent) reference.get();
    }

    public int getDirection() {
      return direction;
    }

    /**
     * @return the blockEvents
     */
    public boolean isBlockEvents() {
      return blockEvents;
    }

    @Override
    protected void fireStateChanged() {
      if (blockRecursion || blockEvents) {
        return;
      }

      JComponent c = (JComponent) reference.get();
      iWidget w = Platform.findWidgetForComponent(c);

      if (w != null) {
        iScriptHandler sh = w.getScriptHandler();

        if (changeCode instanceof String) {
          changeCode = sh.compile(w.getScriptingContext(), w.getPathName() + ".scrollbar.onChange", (String) changeCode);
        }

        if (changeEvent == null) {
          changeEvent = new ChangeEvent(this);
        }

        aWidgetListener.evaluate(w, sh, changeCode, iConstants.EVENT_CHANGE, changeEvent);
      }
    }
  }

  public static class DragHandler extends MouseAdapter implements MouseMotionListener {
    private Point     _startPoint = new Point(0, 0);
    private Component _component;

    public DragHandler(Component c) {
      _component = c;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      Component source = e.getComponent();
      Point pt = e.getLocationOnScreen();

      if (pt == null) {
        pt = new Point(e.getX() + source.getLocationOnScreen().x, e.getY() + source.getLocationOnScreen().y);
      }

      if (_component instanceof Frame) {
        int state = ((Frame) _component).getExtendedState();

        if ((state & Frame.MAXIMIZED_BOTH) != 0) {
          return;
        }
      }

      _component.setLocation(pt.x - _startPoint.x, pt.y - _startPoint.y);
      Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
      _startPoint = e.getPoint();

      Component source = (Component) e.getSource();

      _startPoint = SwingUtilities.convertPoint(source, _startPoint, _component);
    }
  }

  private static class FileNameExtensionFilter extends FileFilter implements FilenameFilter {

    /**  */
    boolean      allFiles;

    /**  */
    String       description;

    /**  */
    List<String> extensions;

    public FileNameExtensionFilter(String desc, List<String> exts) {
      extensions = exts;

      int len = extensions.size();
      String s;
      StringBuilder sb = new StringBuilder(desc).append(" (");

      for (int i = 0; i < len; i++) {
        s = extensions.get(i);

        if (s.equals("*") || s.equals("*.*")) {
          allFiles = true;
        }

        if (s.startsWith(".*")) {
          sb.append(s);
          extensions.set(i, s.substring(2));
        } else {
          sb.append("*.").append(s);
        }

        sb.append(", ");
      }

      len = sb.length();
      sb.setCharAt(len - 2, ')');
      sb.setLength(len - 1);
      description = sb.toString();
    }

    @Override
    public boolean accept(File f) {
      if (allFiles || f.isDirectory()) {
        return true;
      }

      if (!f.isFile()) {
        return false;
      }

      String s = f.getName();
      int n = s.indexOf('.');

      if (n == -1) {
        return false;
      }

      s = s.substring(n + 1);

      int len = extensions.size();

      for (int i = 0; i < len; i++) {
        if (extensions.get(i).equalsIgnoreCase(s)) {
          return true;
        }
      }

      return false;
    }

    @Override
    public boolean accept(File dir, String name) {
      return accept(new File(dir, name));
    }

    @Override
    public String getDescription() {
      return description;
    }
  }
}
