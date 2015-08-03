/*
 * @(#)Utilities.java   2011-02-10
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.platform.swing.ui.util.Java2DUtils;
import com.appnativa.rare.spot.AccessibleContext;
import com.appnativa.rare.spot.ActionItem;
import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.spot.ItemDescription;
import com.appnativa.rare.spot.Margin;
import com.appnativa.rare.spot.Rectangle;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIMenu;
import com.appnativa.rare.ui.UISoundHelper;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iCollapsible;
import com.appnativa.rare.ui.iComposite.CompositeType;
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.rare.ui.painter.iGradientPainter;
import com.appnativa.rare.ui.painter.iGradientPainter.Direction;
import com.appnativa.rare.ui.painter.iImagePainter;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.viewer.TableViewer;
import com.appnativa.rare.viewer.TreeViewer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SDFNode;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTConstants;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.studio.properties.SPOTInspector;
import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.SNumber;
import com.appnativa.util.SimpleURLResolver;
import com.appnativa.util.Streams;
import com.appnativa.util.StringCache;
import com.appnativa.util.iURLResolver;
import com.jgoodies.forms.layout.BoundedSize;
import com.jgoodies.forms.layout.ConstantSize;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.Size;
import com.jgoodies.forms.layout.Sizes;

/**
 *
 * @author decoteaud
 */
public class Utilities {
  private static final char[] http_equiv = "http-equiv=\"content-type\"".toCharArray();
  private static final char[] content    = "content".toCharArray();
  private static List<String> rareIcons;
  private static List<String> rareStrings;

  public static RGB colorToRGB(Color color) {
    if (color == null) {
      return Display.getDefault().getSystemColor(SWT.COLOR_BLACK).getRGB();
    }

    return new RGB(color.getRed(), color.getGreen(), color.getBlue());
  }

  @SuppressWarnings({ "rawtypes", "unchecked", "resource" })
  public static Map<String, String> columnsToMap(List<Column> list) {
    Map       map = new HashMap<String, String>(list.size());
    Object    v;
    String    s;
    CharArray ca = new CharArray();

    for (Column c : list) {
      v = c.getValue();

      if (v instanceof iSPOTElement) {
        s = toString((iSPOTElement) v);
      } else if (v instanceof Color) {
        s = UIColor.fromColor((Color) v).toString();
      } else {
        s = (v == null)
            ? null
            : v.toString();
      }

      if (s != null) {
        ca.set(s);
        ca.replace('\"', '\'');
        s = ca.toString();
      }

      map.put(c.getColumnTitle(), (v == null)
                                  ? null
                                  : s);
    }

    return map;
  }

  public static void configurePropertiesPopupMenu(WindowViewer window, TableViewer table) {
    UIMenu             menu = (UIMenu) window.getNativeEvent().getSource();
    RenderableDataItem item = table.getSelectedItem();

    if (item != null) {
      iSPOTElement e = (iSPOTElement) item.getLinkedData();

      if (e != null) {
        String name = e.spot_getName();

        if (e.spot_hasDefinedAttributes()) {
          menu.add(0, window.getAction("Studio.propertypane.editAttributes"));
        }

        String prop = SPOTInspector.getPropertyType(e);

        if ("url".equals(prop)) {
          if (e.spot_isAttributeSupported("inline")) {
            menu.add(0, window.getAction("Studio.propertypane.inlineURLEditor"));
          } else if (e.spot_getName().contains("icon") || e.spot_getName().contains("Icon")) {
            menu.add(0, window.getAction("Studio.propertypane.selectResourceIcon"));
          }
        } else if (name.equals("value") || name.equals("title") || name.equals("tooltip") || name.equals("leftLabel")
                   || name.equals("rightLabel")) {
          if (e instanceof SPOTPrintableString) {
            menu.add(0, window.getAction("Studio.propertypane.selectResourceString"));
          }
        }
      }
    }
  }

  public static ImageData convertToSWT(BufferedImage bufferedImage) {
    if (bufferedImage.getColorModel() instanceof DirectColorModel) {
      DirectColorModel colorModel = (DirectColorModel) bufferedImage.getColorModel();
      PaletteData      palette    = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(),
                                      colorModel.getBlueMask());
      ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(),
                                     palette);

      for (int y = 0; y < data.height; y++) {
        for (int x = 0; x < data.width; x++) {
          int rgb   = bufferedImage.getRGB(x, y);
          int pixel = palette.getPixel(new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF));

          data.setPixel(x, y, pixel);

          if (colorModel.hasAlpha()) {
            data.setAlpha(x, y, (rgb >> 24) & 0xFF);
          }
        }
      }

      return data;
    } else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
      IndexColorModel colorModel = (IndexColorModel) bufferedImage.getColorModel();
      int             size       = colorModel.getMapSize();
      byte[]          reds       = new byte[size];
      byte[]          greens     = new byte[size];
      byte[]          blues      = new byte[size];

      colorModel.getReds(reds);
      colorModel.getGreens(greens);
      colorModel.getBlues(blues);

      RGB[] rgbs = new RGB[size];

      for (int i = 0; i < rgbs.length; i++) {
        rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
      }

      PaletteData palette = new PaletteData(rgbs);
      ImageData   data    = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
                              colorModel.getPixelSize(), palette);

      data.transparentPixel = colorModel.getTransparentPixel();

      WritableRaster raster     = bufferedImage.getRaster();
      int[]          pixelArray = new int[1];

      for (int y = 0; y < data.height; y++) {
        for (int x = 0; x < data.width; x++) {
          raster.getPixel(x, y, pixelArray);
          data.setPixel(x, y, pixelArray[0]);
        }
      }

      return data;
    }

    return null;
  }

  public static Image convertToSWTImage(BufferedImage bufferedImage) {
    ImageData data = convertToSWT(makeCompatible(bufferedImage));
    Image     img  = (data == null)
                     ? null
                     : new Image(Display.getDefault(), data);

    return img;
  }

  public static Widget.CBorder createBorderConfig(String type) {
    Widget.CBorder border = new Widget.CBorder();

    border.setValue(type);

    return border;
  }

  public static SPOTPrintableString createConfigString(String value) {
    if (value == null) {
      return null;
    }

    value = value.trim();

    int                 n = value.indexOf('[');
    int                 p = value.lastIndexOf('[');
    String              s;
    Map<String, String> map = null;

    if ((n != -1) || (p == -1)) {
      map = Collections.emptyMap();
      s   = value;
    } else {
      s   = value.substring(0, n).trim();
      map = CharScanner.parseOptionStringEx(value.substring(n + 1, p), ',');
    }

    SPOTPrintableString e = new SPOTPrintableString(s);

    e.spot_addAttributes(map);

    return e;
  }

  public static Cursor createCursor(String name, BufferedImage image) {
    if (image == null) {
      return null;
    }

    Toolkit       t = Toolkit.getDefaultToolkit();
    Dimension     d = t.getBestCursorSize(20, 20);
    BufferedImage b = Java2DUtils.createCompatibleImage(d.width, d.height);    // BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB_PRE);
    Graphics2D g = b.createGraphics();

    g.drawImage(image, 4, 4, 16, 16, null);
    g.setColor(Color.black);
    g.drawLine(0, 3, 7, 3);
    g.drawLine(3, 0, 3, 7);
    g.dispose();

    if (name == null) {
      name = "Widget Cursor";
    }

    return java.awt.Toolkit.getDefaultToolkit().createCustomCursor(b, new Point(3, 3), name);
  }

  public static iSPOTElement createElement(SDFNode node) throws Exception {
    String type = node.getNodeName();

    if (type.indexOf('.') == -1) {
      type = Platform.RARE_SPOT_PACKAGE_NAME + "." + type;
    }

    Class  cls     = PlatformHelper.loadClass(type);
    Widget element = (Widget) cls.newInstance();

    if (!element.fromSDF(node)) {
      return null;
    }

    return element;
  }

  public static iImagePainter createImagePainterFromString(iWidget context, File base, SPOTPrintableString s)
          throws IOException {
    String value = s.getValue();

    if ((value != null) && (value.length() > 0)) {
      s.setValue(urlString(base, value));
    }

    iImagePainter ip = Utils.configureImage(context, null, s, false);

    value = ip.getSourceLocation();

    if ((value != null) && URLHelper.canBeFileURL(value)) {
      try {
        URL  u = new URL(value);
        File f = new FileEx(u.toURI());

        ip.setSourceLocation(f.getCanonicalPath());
      } catch(URISyntaxException ex) {}
    } else {
      ip.setSourceLocation(s.getValue());
    }

    return ip;
  }

  public static iImagePainter createImagePainterFromString(iWidget context, File base, String value)
          throws IOException {
    SPOTPrintableString ps = createConfigString(value);

    return createImagePainterFromString(context, base, ps);
  }

  public static List createList(int len) {
    if (len < 1) {
      return new ArrayList();
    }

    return new ArrayList(len);
  }

  public static Map createMap(int len) {
    if (len < 1) {
      return new HashMap();
    }

    return new HashMap(len);
  }

  public static void errorDialogWithStackTrace(String msg, Throwable t) {
    StringWriter sw = new StringWriter();
    PrintWriter  pw = new PrintWriter(sw);

    t.printStackTrace(pw);

    final String trace         = sw.toString();    // stack trace as a string
    List<Status> childStatuses = new ArrayList<Status>();

    for (String line : trace.split(System.getProperty("line.separator"))) {
      childStatuses.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, line));
    }

    MultiStatus ms = new MultiStatus(Activator.PLUGIN_ID, IStatus.ERROR, childStatuses.toArray(new Status[] {}),    // convert to array of statuses
                                     t.getLocalizedMessage(), t);

    ErrorDialog.openError(null, Studio.getResourceAsString("Studio.text.errorTitle"), msg, ms);
  }

  public static void escapeTabsAndLinefeeds(CharArray ca) {
    int len = ca._length;

    if (len > 0) {
      try {
        len--;

        char   c;
        char[] a = ca.A;

        for (int i = len; i >= 0; i--) {
          c = a[i];

          switch(c) {
            case '\n' :
              ca.A[i] = 'n';
              ca.insert(i, '\\');

              break;

            case '\t' :
              ca.A[i] = 't';
              ca.insert(i, '\\');

              break;

            case '\r' :
              ca.A[i] = 'r';
              ca.insert(i, '\\');

              break;
          }
        }
      } catch(Exception ignore) {}
    }
  }

  public static void expandAllCollapsible(iWidget w) {
    iViewer      v = w.getViewer();
    iCollapsible c = v.getCollapsiblePane();

    if (c != null) {
      c.expandPane();
    }

    if (v == w) {
      v = v.getParent();
    }

    while(v != null) {
      c = v.getCollapsiblePane();

      if (c != null) {
        c.expandPane();
      }

      v = v.getParent();
    }
  }

  public static iWidget findTopDesignContainer(iWidget w) {
    if ((w == null) ||!w.isDesignMode()) {
      return null;
    }

    while((w.getParent() != null) && w.getParent().isDesignMode()) {
      w = w.getParent();
    }

    return w;
  }

  public static iWidget findWidget(iContainer root, Widget cfg) {
    if (cfg == null) {
      return null;
    }

    if (root.getLinkedData() == cfg) {
      return root;
    }

    iWidget w;
    int     len = root.getWidgetCount();

    if (len > 0) {
      List<iWidget> list = root.getWidgetList();

      for (int i = 0; i < len; i++) {
        w = list.get(i);

        if (w.getLinkedData() == cfg) {
          return w;
        }

        if (w instanceof iContainer) {
          w = findWidget((iContainer) w, cfg);

          if (w != null) {
            return w;
          }
        }
      }
    }

    return null;
  }

  public static void fixSPOTReference(SPOTSequence root, iSPOTElement cfg) {
    if ((root == null) || (cfg == null)) {
      return;
    }

    iSPOTElement p    = cfg.spot_getParent();
    String       name = cfg.spot_getName();

    while(p != null) {
      if (p == root) {
        break;
      }

      if (p instanceof SPOTSequence) {
        if (((SPOTSequence) p).spot_elementFor(name) == null) {
          break;
        }
      }

      cfg  = p;
      name = cfg.spot_getName();
      p    = p.spot_getParent();
    }

    if ((p == null) ||!(p instanceof SPOTSequence)) {
      p = root;
    }

    SPOTSequence seq = (SPOTSequence) p;

    if (seq.spot_hasNamedElement(name) && (seq.spot_elementFor(name) == null)) {
      seq.spot_setReferenceVariable(name, cfg);
    }

    if (p != root) {
      fixSPOTReference(root, p);
    }
  }

  public static void handleProcessIO(Process proc, Writer w) {
    try {
      InputStreamReader r   = new InputStreamReader(proc.getInputStream());
      char[]            buf = new char[256];
      int               len;

      while((len = r.read(buf)) > 0) {
        writeMessage(w, buf, 0, len);
      }
    } catch(Throwable e) {
      e.printStackTrace();
    }
  }

  public static iSPOTElement loadObject(iWidget context, IFile file) throws Exception {
    InputStreamReader r = new InputStreamReader(file.getContents());

    return DataParser.loadSPOTObjectSDF(context, r, null, null, null);
  }

  public static iSPOTElement loadSPOTObject(final URL base) throws Exception {
    return loadSPOTObject(new InputStreamReader(base.openStream()), base, null);
  }

  public static iSPOTElement loadSPOTObject(Reader reader, final URL base, iSPOTElement element) throws Exception {
    iURLResolver ur = new SimpleURLResolver(base);
    SDFNode      node;

    node = SDFNode.parse(reader, ur, base.toString(), true);
    node = node.getFirstNode();

    if (node == null) {
      throw new ApplicationException("Invalid configuration");
    }

    if (element == null) {
      String  type  = node.getNodeName();
      boolean isset = false;

      if (type.equals("Set") && node.hasAttributes()) {
        String s = (String) node.getNodeAttributes().get("type");

        if (s != null) {
          type  = s;
          isset = true;
        }
      }

      if (type.indexOf('.') == -1) {
        type = Platform.RARE_SPOT_PACKAGE_NAME + "." + type;
      }

      Class cls = Class.forName(type);

      element = (iSPOTElement) cls.newInstance();

      if (isset) {
        SPOTSet set = new SPOTSet("Set", element, -1, -1, true);

        set.spot_setName("Set");
        element = set;
      }
    }

    if (!element.fromSDF(node)) {
      throw new ApplicationException("Invalid configuration");
    }

    return element;
  }

  public static SPOTSet loadSPOTSet(URL base, Class cls) throws Exception {
    return loadSPOTSet(new InputStreamReader(base.openStream()), base, cls);
  }

  public static SPOTSet loadSPOTSet(Reader reader, URL base, Class cls) throws Exception {
    SPOTSet set = new SPOTSet("Set", cls);

    return (SPOTSet) loadSPOTObject(reader, base, set);
  }

  public static File makeFile(File base, String relativePath) {
    if ((relativePath == null) || (relativePath.length() == 0)) {
      return null;
    }

    try {
      if (!URLHelper.canBeFileURL(relativePath)) {
        return new FileEx(relativePath);
      }

      URL u = new URL(base.toURI().toURL(), relativePath);

      return new FileEx(u.toURI());
    } catch(Exception ex) {
      return new FileEx(relativePath);
    }
  }

  public static void openBinaryFile(URL url) {
    if (Desktop.isDesktopSupported()) {
      try {
        if (URLHelper.isFileURL(url)) {
          Desktop.getDesktop().open(URLHelper.getFile(url));
        } else {
          Desktop.getDesktop().browse(url.toURI());
        }
      } catch(Exception ex) {
        UISoundHelper.errorSound();
      }
    } else {
      UISoundHelper.errorSound();
    }
  }

  @SuppressWarnings("resource")
  public static String preprocessDocumentText(ActionLink al, String text) {
    try {
      if (al.getContentType().startsWith(iConstants.HTML_MIME_TYPE)) {
        int n = text.indexOf("</head>");

        n = (n == -1)
            ? text.indexOf("</HEAD>")
            : n;

        while(n != -1) {
          CharScanner sc = new CharScanner(text.substring(0, n));

          sc.toLowerCase();
          n = sc.indexOf(http_equiv, true, false, false);

          if (n == -1) {
            break;
          }

          sc.consume(n);
          n = sc.indexOf(content, true, false, false);

          if (n == -1) {
            break;
          }

          sc.reset();
          sc.consume(n);
          sc.trim();

          int[] tok = sc.findToken('\"', true, false);

          if (tok == null) {
            break;
          }

          tok = sc.findToken('\"', true, false);

          String charset = al.getCharset();
          String cs      = JavaURLConnection.getCharset(sc.getToken(tok), null);

          if (cs != null) {
            cs = Charset.forName(cs).displayName();

            if ((charset == null) ||!cs.equalsIgnoreCase(charset)) {
              al.close();
              al.setCharset(cs);
              text = al.getContentAsString();
            }
          }
        }
      }
    } catch(Exception ignore) {
      ignore.printStackTrace();
    }

    return text;
  }

  public static void recursiveSetEnabled(Control ctrl, boolean enabled) {
    if (ctrl instanceof Composite) {
      Composite comp = (Composite) ctrl;

      for (Control c : comp.getChildren()) {
        recursiveSetEnabled(c, enabled);
      }
    } else {
      ctrl.setEnabled(enabled);
    }
  }

  public static List sortColumns(List list) {
    Comparator c = new Comparator() {
      public int compare(Object o1, Object o2) {
        Column c1 = (Column) o1;
        Column c2 = (Column) o2;

        return c1.getColumnTitle().toString().compareTo(c2.getColumnTitle().toString());
      }
    };

    Collections.sort(list, c);

    return list;
  }

  public static RGB stringToRGB(String color) {
    return colorToRGB(ColorUtils.getColor(color));
  }

  public static String[] toLowerCase(Object[] values) {
    int    len = values.length;
    String a[] = new String[len];

    for (int i = 0; i < len; i++) {
      a[i] = values[i].toString().toLowerCase();
    }

    return a;
  }

  public static String toSDF(iSPOTElement e) {
    StringWriter w = new StringWriter();

    try {
      e.toSDF(w);
    } catch(IOException ignore) {}

    return w.toString();
  }

  public static String toString(iSPOTElement e) {
    CharArray ca = toStringEx(e);

    return (ca == null)
           ? null
           : ca.toString();
  }

  public static CharArray toStringEx(iSPOTElement e) {
    if (e == null) {
      return null;
    }

    CharArray ca = new CharArray();
    Writer    w  = Streams.charArrayWriter(ca);

    try {
      switch(e.spot_getType()) {
        case iSPOTConstants.SPOT_TYPE_ANY :
        case iSPOTConstants.SPOT_TYPE_SET :
        case iSPOTConstants.SPOT_TYPE_SEQUENCE :
          e.toSDF(w, "", 0, false, false);
          ca.trim();
          ca.replace('\n', ';');
          ca.replace('\"', '\'');

          int n = ca.indexOf("{; ");

          while(n != -1) {
            ca.remove(n + 1, n + 3);
            n = ca.indexOf("{; ");
          }

          if ((ca._length > 2) && (ca.A[ca._length - 2] == ';')) {
            ca._length--;
            ca.A[ca._length - 1] = '}';
          }

          break;

        default :
          String s = e.spot_stringValue();

          if (s != null) {
            w.append(s);
          }

          if (e.spot_attributesWereSet()) {
            w.append(' ');
            SDFNode.writeAttributes(w, 0, e.spot_getAttributes());
            escapeTabsAndLinefeeds(ca);
          }

          break;
      }
    } catch(IOException ignore) {}

    ca.trim();

    if (ca.length() == 0) {
      return null;
    }

    return ca;
  }

  public static String toTruncatedString(iSPOTElement e) {
    if ((e instanceof Font) || (e instanceof Rectangle) || (e instanceof AccessibleContext)
        || (e instanceof ActionItem) || (e instanceof DataItem) || (e instanceof ItemDescription)
        || (e instanceof Margin)) {
      return toString(e);
    }

    String s = e.spot_getClassShortName();

    return s + "{...}";
  }

  @SuppressWarnings("resource")
  public static String unNormalizeString(String value, boolean event) {
    int len = (value == null)
              ? 0
              : value.length();

    if (len > 0) {
      CharArray ca = new CharArray();

      try {
        ca    = CharScanner.cleanQuoted(value, ca);
        value = ca.toString();
      } catch(ParseException ignore) {}
    }

    return value;
  }

  public static String urlString(File file) throws MalformedURLException {
    if (file == null) {
      return null;
    }

    if (!URLHelper.canBeFileURL(file.getName())) {
      return file.getName();
    }

    return JavaURLConnection.toExternalForm(file.toURI().toURL());
  }

  public static SPOTSequence wrapSPOTElement(iSPOTElement e) {
    SPOTSequence seq  = new SPOTSequence();
    String       name = e.spot_getName();

    if (name == null) {
      name = e.spot_getClassShortName();
    }

    seq.spot_addElement(name, e);

    return seq;
  }

  public static void writeMessage(final Writer w, final String msg) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          w.write(msg);
        } catch(IOException ex) {}
      }
    });
  }

  public static void writeMessage(final Writer w, final char[] msg, final int off, final int len) {
    try {
      SwingUtilities.invokeAndWait(new Runnable() {
        public void run() {
          try {
            w.write(msg, off, len);
          } catch(IOException ignore) {}
        }
      });
    } catch(Exception ignore) {}
  }

  public static void setConfiguration(SPOTPrintableString bg, iGradientPainter gp) {
    Color gradientColors[] = gp.getGradientColors();
    Color backgroundColor  = gp.getBackgroundColor();

    if (gradientColors == null) {
      bg.setValue(UIColor.fromColor(backgroundColor).toString());

      return;
    }

    StringBuilder sb  = new StringBuilder();
    int           len = gradientColors.length;

    for (int i = 0; i < len; i++) {
      Color c = gradientColors[i];

      if (c != null) {
        if ((c instanceof UIColorShade) && ((UIColorShade) c).getColorKey() != null) {
          sb.append(((UIColorShade) c).getColorKey());
        } else {
          sb.append(UIColor.fromColor(c).toString());
        }
      }

      sb.append(", ");
    }

    sb.setLength(sb.length() - 2);
    bg.setValue(sb.toString());
    bg.spot_setAttribute("type", gp.getGradientType().toString().toLowerCase());

    if (gp.getGradientDirection() != Direction.VERTICAL_TOP) {
      bg.spot_setAttribute("direction", gp.getGradientDirection().toString().toLowerCase());
    }

    if (gp.getGradientMagnitude() != 100) {
      bg.spot_setAttribute("magnitude", StringCache.valueOf(gp.getGradientMagnitude()));
    }

    float[] distribution = gp.getGradientDistribution();

    if (!ColorUtils.isStandardDistribution(distribution)) {
      sb.setLength(0);
      len = distribution.length;

      for (int i = 0; i < len; i++) {
        sb.append(SNumber.toString(distribution[i])).append(", ");
      }

      bg.spot_setAttribute("direction", sb.toString());
    }
  }

  public static void setConfiguration(URL base, SPOTPrintableString bg, iImagePainter ip) throws MalformedURLException {
    if (ip.getSourceLocation() == null) {
      UISoundHelper.errorSound();

      return;
    }

    bg.setValue(URLHelper.makeRelativePath(base, ip.getSourceLocation()));
    bg.spot_setAttribute("opacity", StringCache.valueOf((int) ip.getComposite().getAlpha() * 255 / 100));

    if (ip.getRenderType() != RenderType.TILED) {
      bg.spot_setAttribute("renderType", ip.getRenderType().toString().toLowerCase());
    }

    if (ip.getRenderSpace() != RenderSpace.COMPONENT) {
      bg.spot_setAttribute("renderSpace", ip.getRenderSpace().toString().toLowerCase());
    }

    if (ip.getDisplayed() != Displayed.ALWAYS) {
      bg.spot_setAttribute("displayed", ip.getDisplayed().toString().toLowerCase());
    }

    if (ip.getComposite().getCompositeType() != CompositeType.SRC_OVER) {
      bg.spot_setAttribute("composite", ip.getComposite().getCompositeType().toString().toLowerCase());
    }
  }

  public static Map<String, UIAction> getActions(iWidget ctx, com.appnativa.rare.spot.Application app) {
    SPOTSet set = null;

    try {
      set = DataParser.loadSPOTSet(ctx, app.actionItemsURL, ActionItem.class);
    } catch(Exception ignore) {
      ignore.printStackTrace();
    }

    int len = (set == null)
              ? 0
              : set.getCount();

    if (len == 0) {
      return null;
    }

    ActionItem            item;
    UIAction              sa;
    Map<String, UIAction> map = new HashMap<String, UIAction>(len);

    for (int i = 0; i < len; i++) {
      item = (ActionItem) set.get(i);
      sa   = UIAction.createAction(ctx, item);
      map.put(sa.getActionName(), sa);
    }

    return map;
  }

  public static String getApplicationRoot(URL dir, com.appnativa.rare.spot.Application cfg) throws Exception {
    String s = cfg.applicationRoot.getValue();

    if ((s == null) ||!dir.getProtocol().equals("file")) {
      return s;
    }

    s = dir.toURI().toURL().getFile();

    int n = s.indexOf(':');

    if (n != -1) {
      n = s.indexOf('/', n);

      if (n != -1) {
        s = s.substring(n);
      }
    }

    return s;
  }

  public static BufferedImage getBufferedImage(iWidget w) {
    switch(w.getWidgetType()) {
      case PushButton :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.pushbutton");

      case CheckBox :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.checkbox");

      case RadioButton :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.radiobutton");

      case ComboBox :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.combobox");

      case ListBox :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.listbox");

      case CheckBoxList :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.checkboxlist");

      case Tree :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.tree");

      case CheckBoxTree :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.checkboxtree");

      case Spinner :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.list_spinner");

      case SplitPane :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.vsplitpane");

      case ProgressBar :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.progressbar");

      case MenuBar :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.menubar");

      case ToolBar :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.toolbar");

      case StatusBar :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.statusbar");

      case TabPane :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.tabpane");

      case Table :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.table");

      case Line :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.hline");

      case Slider :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.hslider");

      case FileUploadField :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.fileuploadfield");

      case TextField :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.textfield");

      case TextArea :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.textarea");

      case DocumentPane :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.documentpane");

      case CollapsiblePane :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.collapsiblepane");

      case GridPane :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.gridpane");

      case StackPane :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.stackpane");

      case Form :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.form");

      case GroupBox :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.groupbox");

      case DateChooser :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.date_chooser");

      case ColorChooser :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.color_chooser");

      case Navigator :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.navigator");

      case ImagePane :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.imagepane");

      case Chart :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.piechart");

      case Label :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.label");

      case Bean :
      case Custom :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.bean");

      case PasswordField :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.passwordfield");

      case ScrollPane :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.scrollpane");

      case FlashPlayer :
      case MediaPlayer :
      case WebBrowser :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.browser");

      case WidgetPane :
        return Studio.getResourceAsBufferedImage("Studio.icon.toolbox.widgetpane");

      case Window :
        break;

      default :
        break;
    }

    return null;
  }

  public static Color[] getColors(iWidget context, String s) {
    return ColorUtils.getColors(s, 1);
  }

  public static long getFileTime(iWidget ctx, SPOTPrintableString ps) {
    return getFileTime(ctx, ps, null);
  }

  public static long getFileTime(iWidget ctx, SPOTPrintableString ps, File appFile) {
    if (ps.spot_hasValue()) {
      ActionLink link = new ActionLink(ctx, ps);

      try {
        URL url = link.getURL(ctx);

        if (URLHelper.isFileURL(url)) {
          return URLHelper.getFile(url).lastModified();
        }

        if (appFile != null) {
          return appFile.lastModified();
        }
      } catch(Exception ex) {
        return 0;
      }
    }

    return 0;
  }

  public static FormLayout getFormLayout(String rows, String cols) {
    return new FormLayout(cols, rows);
  }

  public static int getGroupNumber(SPOTSet set, int pos) {
    int    len = (set == null)
                 ? 0
                 : set.size();
    String s;
    String row = StringCache.valueOf(pos + 1);

    for (int i = 0; i < len; i++) {
      s = set.stringValueAt(i);

      if (s != null) {
        s = "," + s + ",";

        if (s.indexOf(row) != -1) {
          return i + 1;
        }
      }
    }

    return 0;
  }

  public static UIImageIcon getIcon(AppContext appContext, iWidget w) {
    switch(w.getWidgetType()) {
      case PushButton :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.pushbutton");

      case CheckBox :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.checkbox");

      case RadioButton :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.radiobutton");

      case ComboBox :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.combobox");

      case ListBox :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.listbox");

      case CheckBoxList :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.checkboxlist");

      case Tree :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.tree");

      case CheckBoxTree :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.checkboxtree");

      case Spinner :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.list_spinner");

      case SplitPane :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.vsplitpane");

      case ProgressBar :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.progressbar");

      case MenuBar :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.menubar");

      case ToolBar :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.toolbar");

      case StatusBar :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.statusbar");

      case TabPane :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.tabpane");

      case Table :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.table");

      case Line :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.hline");

      case Slider :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.hslider");

      case TextField :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.textfield");

      case TextArea :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.textarea");

      case DocumentPane :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.documentpane");

      case CollapsiblePane :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.collapsiblepane");

      case GridPane :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.gridpane");

      case StackPane :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.stackpane");

      case Form :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.form");

      case GroupBox :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.groupbox");

      case DateChooser :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.date_chooser");

      case ColorChooser :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.color_chooser");

      case Navigator :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.navigator");

      case ImagePane :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.imagepane");

      case Chart :
        return appContext.getResourceAsIcon("Studio.icon.toolbox.piechart");

      default :
        return null;
    }
  }

  public static UIDimension getPreferredSize(com.appnativa.rare.spot.Rectangle rect) {
    String      s;
    UIDimension d = ScreenUtils.getScreenSize();
    int         w = rect.getWidthPixels(null);

    if (w == -1) {
      s = rect.width.spot_getAttribute("min");

      if (s != null) {
        w = ScreenUtils.toPlatformPixelWidth(s, null, d.width);
      }
    }

    int h = rect.getHeightPixels(null);

    if (h == -1) {
      s = rect.height.spot_getAttribute("min");

      if (s != null) {
        h = ScreenUtils.toPlatformPixelWidth(s, null, d.height);
      }
    }

    d.setSize(w, h);

    return d;
  }

  public static List<String> getProjectIcons(AppContext app) {
    List<String> list = new ArrayList<String>();

    addIcons(app.getResourceIcons(), list);

    return list;
  }

  public static List<String> getProjectStrings(AppContext app) {
    List<String> list = new ArrayList<String>();

    addStrings(app.getResourceStrings(), list);

    return list;
  }

  public static List<String> getRareIcons(AppContext app) {
    List<String> list = rareIcons;

    if (list == null) {
      try {
        URL         u      = Activator.class.getResource("rare_icons.txt");
        InputStream stream = u.openStream();

        Collections.sort(list = Streams.streamToStrings(stream));
        stream.close();
      } catch(Exception e) {
        e.printStackTrace();
      }

      rareIcons = list;
    }

    return list;
  }

  public static List<String> getRareStrings(AppContext app) {
    List<String> list = rareStrings;

    if (list == null) {
      list = new ArrayList<String>();

      if (Studio.rareResourceBundle != null) {
        try {
          for (Enumeration<String> e = Studio.rareResourceBundle.getKeys(); e.hasMoreElements(); ) {
            String s = e.nextElement();

            if (s.startsWith("Rare.text.") || s.startsWith("Rare.action.")) {
              list.add(s);
            }
          }
        } catch(Exception ignore) {}

        Collections.sort(list);
      }

      rareStrings = list;
    }

    return list;
  }

  public static Reader getReader(IFile file) throws Exception {
    return new InputStreamReader(file.getContents());
  }

  public static String getSizeSize(Size size, boolean lower) {
    if (size instanceof BoundedSize) {
      Size sz = ((BoundedSize) size).getLowerBound();

      if (!lower) {
        sz = ((BoundedSize) size).getUpperBound();
      }

      if (sz == null) {
        return null;
      }

      return getSizeSize(sz, lower);
    }

    if (size instanceof ConstantSize) {
      return SNumber.toString(((ConstantSize) size).getValue());
    }

    if (size == Sizes.DEFAULT) {
      return "Default";
    }

    if (size == Sizes.PREFERRED) {
      return "Preferred";
    }

    if (size == Sizes.MINIMUM) {
      return "Minimum";
    }

    return null;
  }

  public static String getSizeType(Size size) {
    if (size instanceof BoundedSize) {
      return "bounded";
    }

    if (size instanceof ConstantSize) {
      return "constant";
    }

    return "component";
  }

  public static String getSizeUnit(Size size) {
    if (size instanceof BoundedSize) {
      Size sz = ((BoundedSize) size).getLowerBound();

      if (sz == null) {
        ((BoundedSize) size).getUpperBound();
      }

      if (sz == null) {
        return null;
      }

      return getSizeUnit(sz);
    }

    if (size instanceof ConstantSize) {
      String s = ((ConstantSize) size).getUnit().toString();

      if (s.startsWith("Dialog units")) {
        s = "Dialog units";
      }

      return s;
    }

    return null;
  }

  public static boolean hasPopupMenu(RenderableDataItem item) {
    if (item == null) {
      return false;
    }

    iSPOTElement e = (iSPOTElement) item.getLinkedData();

    if (e == null) {
      return false;
    }

    String name = e.spot_getName();
    // FIX ME
    // iDataConverter cvt = item.getDataConverter();
    // if ((cvt == null) || (cvt instanceof SPOTConverter)) {
    // if (e.spot_hasDefinedAttributes()) {
    // return true;
    // }
    // }
    String prop = SPOTInspector.getPropertyType(e);

    if ("url".equals(prop)) {
      if (e.spot_isAttributeSupported("inline")) {
        return true;
      } else if (e.spot_getName().contains("icon") || e.spot_getName().contains("Icon")) {
        return true;
      }
    } else if (name.equals("value") || name.equals("title") || name.equals("tooltip")) {
      if (e instanceof SPOTPrintableString) {
        return true;
      }
    }

    return false;
  }

  public static boolean hasSameObjects(List list1, List list2) {
    if ((list1 == null) || (list2 == null)) {
      return list1 == list2;
    }

    int len = list1.size();

    if (list2.size() != len) {
      return false;
    }

    for (int i = 0; i < len; i++) {
      if (list1.get(i) != list2.get(i)) {
        return false;
      }
    }

    return true;
  }

  public static boolean isEvent(String name) {
    return ((name.length() > 2) && name.startsWith("on") && Character.isUpperCase(name.charAt(2)));
  }

  public static boolean isValidName(String name) {
    if (name.endsWith(".sdf")) {
      name = name.substring(0, name.length() - 4);
    }

    char[] a   = name.toCharArray();
    int    len = a.length;

    if (len < 1) {
      return false;
    }

    if (!Character.isJavaIdentifierStart(a[0])) {
      return false;
    }

    for (int i = 1; i < len; i++) {
      if (!Character.isJavaIdentifierPart(a[i])) {
        return false;
      }
    }

    return true;
  }

  static void addIcons(Map<String, UIImageIcon> map, List<String> list) {
    if ((map == null) || (map.size() == 0)) {
      return;
    }

    String[] a = map.keySet().toArray(new String[map.size()]);

    Arrays.sort(a);

    for (String s : a) {
      if (!s.startsWith("Rare.")) {
        list.add(s);
      }
    }
  }

  static void addStrings(Map<String, String> map, List<String> list) {
    if ((map == null) || (map.size() == 0)) {
      return;
    }

    String[] a = map.keySet().toArray(new String[map.size()]);

    Arrays.sort(a);

    for (String s : a) {
      if (!s.startsWith("Rare.")) {
        list.add(s);
      }
    }
  }

  static int findConfiguration(TreeViewer tree, Widget wc) {
    iWidget w;
    int     len = tree.size();

    for (int i = 0; i < len; i++) {
      w = (iWidget) tree.get(i).getLinkedData();

      if ((w != null) && (w.getLinkedData() == wc)) {
        return i;
      }
    }

    return -1;
  }

  private static BufferedImage makeCompatible(BufferedImage image) {
    if (image.getColorModel() instanceof DirectColorModel) {
      return image;
    }

    int           w      = image.getWidth();
    int           h      = image.getHeight();
    BufferedImage result = Java2DUtils.createCompatibleImage(image, w, h);
    Graphics2D    g      = result.createGraphics();

    g.drawRenderedImage(image, new AffineTransform());    //or some other drawImage function
    g.dispose();

    return result;
  }

  private static String urlString(File base, String s) throws MalformedURLException {
    if (s==null || !URLHelper.canBeFileURL(s)) {
      return s;
    }

    URL u = new URL(base.toURI().toURL(), s);

    return JavaURLConnection.toExternalForm(u);
  }
}
