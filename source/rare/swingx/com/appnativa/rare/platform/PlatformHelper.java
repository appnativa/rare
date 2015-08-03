/*
 * @(#)PlatformHelper.java   2012-04-28
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.CookieHandler;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;
import javax.swing.text.JTextComponent;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.net.CookieManager;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.platform.swing.Applet;
import com.appnativa.rare.platform.swing.PlatformImpl;
import com.appnativa.rare.platform.swing.Rare;
import com.appnativa.rare.platform.swing.ui.DataItemListModel;
import com.appnativa.rare.platform.swing.ui.PopupListBoxHandler;
import com.appnativa.rare.platform.swing.ui.util.ImageHelper;
import com.appnativa.rare.platform.swing.ui.util.ImageUtils;
import com.appnativa.rare.platform.swing.ui.util.RectangleShape;
import com.appnativa.rare.platform.swing.ui.util.SoundClip;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.util.SwingPath;
import com.appnativa.rare.platform.swing.ui.view.AnimationPanel;
import com.appnativa.rare.platform.swing.ui.view.ButtonView;
import com.appnativa.rare.platform.swing.ui.view.FrameView;
import com.appnativa.rare.platform.swing.ui.view.GlassPanel;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.platform.swing.ui.view.SeparatorView;
import com.appnativa.rare.platform.swing.ui.view.SpacerView;
import com.appnativa.rare.platform.swing.ui.view.iView;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.AlertPanel;
import com.appnativa.rare.ui.AnimationComponent;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.ToolBarHolder;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFont.UIFontResource;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIMenuItem;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.UISound;
import com.appnativa.rare.ui.UITarget;
import com.appnativa.rare.ui.WindowDeviceConfiguration;
import com.appnativa.rare.ui.aFocusedAction;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformMenuBar;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.dnd.DefaultTransferHandler;
import com.appnativa.rare.ui.dnd.DragSourceAdapter;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.renderer.Renderers;
import com.appnativa.rare.viewer.MenuBarViewer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.widget.BeanWidget;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.FilterableList;

public class PlatformHelper extends aPlatformHelper {
  private static JLabel  _fontLabel;
  private static boolean sunGraphicsFailed;

  public static CharSequence checkForHTML(String text, UIFont uiFont) {
    return text;
  }

  public static void clearSessionCookies() {
  }

  public static PopupListBoxHandler createPopupListBoxHandler(iWidget w, iPlatformListDataModel model, boolean forMenu) {
    return new PopupListBoxHandler(w, model, forMenu);
  }

  public static iPlatformComponent componentForEvent(EventObject uiEvent) {
    if (uiEvent.getSource() instanceof JComponent) {
      iPlatformComponent c = Component.fromView((JComponent) uiEvent.getSource());

      return (c == null) ? null : new Component((JComponent) uiEvent.getSource());
    }

    return null;
  }

  public static void configureDragging(iWidget widget, iPlatformComponent comp) {
    DragSourceAdapter.configure(comp.getView());
  }

  public static void configureDropTracking(iWidget widget, iPlatformComponent comp, int dropMode) {
    DefaultTransferHandler.setTransferHandler(widget, comp.getView(), dropMode);
  }

  public static com.appnativa.rare.ui.event.ActionEvent createActionEvent(ActionEvent e) {
    com.appnativa.rare.ui.event.ActionEvent ae = new com.appnativa.rare.ui.event.ActionEvent(e.getSource(), e);

    ae.setActionCommand(e.getActionCommand());

    return ae;
  }

  public static AnimationComponent createAnimationComponent(iWidget context) {
    return new AnimationComponent(new AnimationPanel());
  }

  public static Object createBean(BeanWidget w, Bean cfg) {
    return w.getAppContext().getComponentCreator().getBean(w.getViewer(), cfg);
  }

  public static iPlatformComponent createBorderPanel(iPlatformComponent comp, iPlatformBorder border) {
    if ((comp.getView() instanceof JScrollPane) && (!border.isPaintLast() || !border.isRectangular())) {
      ContainerPanel cp = new ContainerPanel(comp);

      cp.setBorderPanel(true);
      comp = cp;
    }

    return comp;
  }

  public static Object createColorWheel(iWidget context) {
    return null;
  }

  public static JComponent createDateButtonView() {
    ButtonView v = new ButtonView();

    v.setFont(FontUtils.getDefaultFont());
    v.setForeground(ColorUtils.getForeground());

    return v;
  }

  public static UIImageIcon createDisabledIcon(Icon icon) {
    java.awt.Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

    if (c == null) {
      c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
    }

    if (c == null) {
      c = Platform.getAppContext().getRootViewer().getDataComponent().getView();
    }

    return ImageHelper.createDisabledIcon(c, icon);
  }

  public static iPlatformIcon createDisabledIconIfNeeded(Icon icon) {
    return createDisabledIcon(icon);
  }

  public static UIImageIcon createIcon(URL url, String description, boolean defer, float density) {
    if (url == null) {
      return null;
    }

    return ImageHelper.createIcon(url, description, null, defer, density);
  }

  public static UIImage createImage(URL url, boolean defer, float density) throws IOException {
    if (url == null) {
      return null;
    }

    return ImageHelper.createImage(url, defer, density);
  }

  public static java.awt.event.KeyEvent createKeyEvent(KeyEvent e) {
    return (java.awt.event.KeyEvent) e.getNativeEvent();
  }

  public static iActionComponent createLabel(iPlatformComponent context) {
    LabelView v = new LabelView();

    v.setFont(FontUtils.getDefaultFont());
    v.setForeground(ColorUtils.getForeground());

    return new ActionComponent(v);
  }

  public static iPlatformListDataModel createListDataModel(iWidget context, List<RenderableDataItem> items) {
    DataItemListModel dm = new DataItemListModel(context, null);

    if (items != null) {
      dm.addAll(items);
    }

    return dm;
  }

  public static iPlatformMenuBar createMenuBar(iContainer parent, MenuBar menuBar) {
    MenuBarViewer mb = new MenuBarViewer(parent);

    if (parent.isDesignMode()) {
      mb.setLinkedData(menuBar);
      mb.setDesignMode(true);
    }

    mb.configure(menuBar);

    return mb;
  }

  public static UIMenuItem createMenuItem(UIAction action, boolean topLevel) {
    if (action == null) {
      return new UIMenuItem(action, topLevel ? new JMenu() : new JMenuItem());
    }

    return new UIMenuItem(action, topLevel ? new JMenu() : new JMenuItem());
  }

  public static iActionComponent createNakedButton(iPlatformComponent context, boolean parentPaints, int autoRepeatDelay) {
    ButtonView v = new ButtonView();

    if (autoRepeatDelay > 0) {
      v.setAutoRepeats(autoRepeatDelay);
    }

    v.setInvalidateParent(parentPaints);

    ActionComponent a = new ActionComponent(v);

    a.setFont(FontUtils.getDefaultFont());
    a.setForeground(ColorUtils.getForeground());

    return a;
  }

  public static ActionEvent createNativeActionEvent(com.appnativa.rare.ui.event.ActionEvent e) {
    Object se = e.getSourceEvent();

    if (se instanceof ActionEvent) {
      return (ActionEvent) se;
    }

    return new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand(), System.currentTimeMillis(), 0);
  }

  public static iPlatformPath createPath() {
    return new SwingPath();
  }

  public static iPlatformRenderingComponent createRenderer(String className, aWidget aWidget) throws ClassNotFoundException {
    return Renderers.createRenderer(className);
  }

  public static iPlatformComponent createSeparatorComponent(iPlatformComponent context) {
    return new Component(new SeparatorView(false));
  }

  public static iPlatformShape createShape(float x, float y, float width, float height) {
    return new RectangleShape(x, y, width, height);
  }

  public static iPlatformComponent createSpacerComponent(iPlatformComponent context) {
    return new Component(new SpacerView());
  }

  public static iPlatformIcon createStateListIcon(String icon, iWidget context) {
    return null;
  }

  public static iParentComponent createTargetContainer(iPlatformAppContext app) {
    return new Container(new FrameView());
  }

  public static ToolBarHolder createToolBarHolder(iContainer viewer, SPOTSet toolbars) {
    return ToolBarHolder.create(viewer, toolbars);
  }

  public static void defaultFontUpdated(UIFont font) {
    final String[] FONT_PROPERTY_NAMES = { "List.font", "TableHeader.font", "Panel.font", "TextArea.font", "ToggleButton.font",
        "ComboBox.font", "ScrollPane.font", "Spinner.font", "RadioButtonMenuItem.font", "Slider.font", "EditorPane.font",
        "OptionPane.font", "ToolBar.font", "Tree.font", "CheckBoxMenuItem.font", "FileChooser.listFont", "Rare.html.font",
        "Rare.richtext.font", "Table.font", "MenuBar.font", "PopupMenu.font", "Label.font", "MenuItem.font", "TextField.font",
        "TextPane.font", "CheckBox.font", "ProgressBar.font", "FormattedTextField.font", "ColorChooser.font", "Menu.font",
        "PasswordField.font", "Viewport.font", "TabbedPane.font", "RadioButton.font", "ToolTip.font", "Button.font",
        "JideButton.font", "JideSplitButton.font", "JideTabbedPane.font", "DockableFrame.font", "JideLabel.font",
        "OptionPane.messageFont", "SidePane.font", "CollapsiblePane.titleFont", "StatusBar.font", "TitledBorder.font",
        "CommandBar.titleBarFont", "DockableFrameTitlePane.font", "Menu.acceleratorFont", "JideTabbedPane.selectedTabFont",
        "CommandBar.font", };

    Platform.getUIDefaults().put("Label.font", font);

    UIDefaults defaults = UIManager.getDefaults();

    if (!(font instanceof UIFontResource)) {
      font = new UIFontResource(font);
    }

    for (int i = 0; i < FONT_PROPERTY_NAMES.length; i++) {
      Font of = defaults.getFont(FONT_PROPERTY_NAMES[i]);

      if (!(of instanceof UIResource)) {
        continue;
      }

      if ((of == null) || (of.getStyle() == UIFont.PLAIN)) {
        defaults.put(FONT_PROPERTY_NAMES[i], font);
      } else {
        defaults.put(FONT_PROPERTY_NAMES[i], font.deriveUIResourceFont(of.getStyle()));
      }
    }
  }

  public static JScrollPane findScrollPane(iPlatformComponent c) {
    java.awt.Component node = c.getView();
    java.awt.Component p;

    while (node != null) {
      p = node.getParent();

      if (p instanceof JScrollPane) {
        return (JScrollPane) p;
      }

      node = p;
    }

    return null;
  }

  public static void handleCookieExtraction(URLConnection conn) {
    handleCookieExtraction(conn.getURL().toString(), conn);
  }

  public static void handleCookieExtraction(String url, URLConnection conn) {
  }

  public static String handleCookieInjection(URLConnection conn) {
    return handleCookieInjection(conn.getURL().toString(), conn);
  }

  public static String handleCookieInjection(String url, URLConnection conn) {
    return url;
  }

  public static void handleSelectionChange(iPlatformComponent c, boolean hasSelection) {
    Boolean selected = (Boolean) c.getClientProperty(iConstants.RARE_SELECTION_PROPERTY);

    if (selected == null) {
      selected = Boolean.FALSE;
    }

    if (hasSelection) {
      if (!selected) {
        c.putClientProperty(iConstants.RARE_SELECTION_PROPERTY, Boolean.TRUE);
      }
    } else {
      if (selected) {
        c.putClientProperty(iConstants.RARE_SELECTION_PROPERTY, Boolean.FALSE);
      }
    }
  }

  public static void hideVirtualKeyboard(iPlatformComponent c) {
  }

  public static void hideVirtualKeyboard(iWidget context) {
  }

  public static void initialize() {
    float screenDpi = Toolkit.getDefaultToolkit().getScreenResolution();

    ScreenUtils.initilize(1, 1, screenDpi, screenDpi, screenDpi, 1);

    Font f = UIManager.getFont("Label.font");

    FontUtils.setSystemFont(new UIFont(f));
    if (!Applet.isRunningAsApplet() && !Rare.isWebStart()) {
      CookieHandler.setDefault(new CookieManager());
    }

  }

  public static void layout(iPlatformComponent c, float x, float y, float w, float h) {
    c.setBounds(x, y, w, h);
  }

  public static Class loadClass(String name) throws ClassNotFoundException {
    return Class.forName(name);
  }

  /**
   * Loads a new true type font
   *
   * @param context
   *          the context
   * @param name
   *          the name for the font
   * @param in
   *          the stream from which to load the font
   */
  public static UIFont loadFont(String name, URL location, String type) {
    try {
      InputStream in = Platform.getAppContext().openConnection(location).getInputStream();
      int format = UIFont.TRUETYPE_FONT;

      if ((type != null) && type.equalsIgnoreCase("type1")) {
        format = UIFont.TYPE1_FONT;
      }

      UIFont f = new UIFont(Font.createFont(format, in));

      FontUtils.addCustomFont(name, f);

      return f;
    } catch (Exception ex) {
      Platform.ignoreException("Could no load font:" + name, ex);

      return null;
    }
  }

  public static void loadIcon(iPlatformAppContext app, UIImageIcon ic) {
    executeBackgroundTask(ic, false);
  }

  public static iPlatformComponent makeScrollPane(iWidget context, ScrollPane cfg, iPlatformComponent comp) {
    JScrollPane sp = context.getAppContext().getComponentCreator().getScrollPane(context, cfg);

    if (comp != null) {
      sp.setViewportView(comp.getView());
    }

    if (cfg != null) {
      SwingHelper.configureScrollPane(context, sp, cfg);
    }

    return new Container(sp);
  }

  public static void paintIcon(iPlatformGraphics g, iPlatformIcon icon, float x, float y, float width, float height) {
  }

  public static void performHapticFeedback(Object view) {
  }

  public static iPlatformComponent resolveBeanComponent(Object bean) {
    if (bean instanceof JComponent) {
      return new Component((JComponent) bean);
    }

    return (iPlatformComponent) bean;
  }

  public static UIImage scaleImage(UIImage image, int width, int height) {
    if (width != image.getWidth() || height != image.getHeight()) {
      image = new UIImage(ImageUtils.getScaledInstance(image.getBufferedImage(), width, height, image.getScalingType()));
    }
    return image;
  }

  public static void showVirtualKeyboard(iWidget context) {
  }

  public static void systemAlert(iWidget context, Object message, iPlatformIcon icon, final iActionListener listener) {
    final AlertPanel p = AlertPanel.ok(context, null, message, icon);
    iFunctionCallback cb = null;
    if (listener != null) {
      cb = new iFunctionCallback() {

        @Override
        public void finished(boolean canceled, Object returnValue) {
          listener.actionPerformed(new com.appnativa.rare.ui.event.ActionEvent(Platform.getWindowViewer()));
        }
      };
    }
    p.showDialog(cb);
  }

  public static UIRectangle toUIRectangle(Rectangle2D fxrect, UIRectangle rect) {
    if (rect == null) {
      return new UIRectangle((int) fxrect.getMinX(), (int) fxrect.getMinY(), (int) fxrect.getWidth(), (int) fxrect.getHeight());
    }

    rect.setBounds((int) fxrect.getMinX(), (int) fxrect.getMinY(), (int) fxrect.getWidth(), (int) fxrect.getHeight());

    return rect;
  }

  public static boolean useInFormLayoutMeasureHeights(iPlatformComponent component) {
    return true;
  }

  public static void setAutoRepeats(iActionComponent comp, int interval) {
    if (comp.getView() instanceof ButtonView) {
      ((ButtonView) comp.getView()).setAutoRepeats(interval);
    }
  }

  public static boolean setComponentAlpha(iPlatformComponent component, float alpha) {
    if ((component != null) && (component.getView() instanceof JPanelEx) && !component.isDisposed()) {
      ((JPanelEx) component.getView()).setComposite(AlphaComposite.SrcOver.derive(alpha));

      return true;
    }

    return false;
  }

  /**
   * Set a cookie value for the given URL
   *
   * @param url
   *          the URL
   * @param value
   *          the cookie value
   */
  public static void setCookieValue(URL url, String value) {
    try {
      CookieHandler handler = CookieHandler.getDefault();

      if (handler != null) {
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();

        values.add(value);
        headers.put("Cookie", values);
        handler.put(url.toURI(), headers);
      }
    } catch (Exception ex) {
      throw new ApplicationException(ex);
    }
  }

  public static void setLabelForComponent(iPlatformComponent c, Object l) {
  }

  public static void setMinimumSizeEx(iPlatformComponent comp, int w, int h) {
    if (comp.getView() instanceof JComponent) {
      JComponent c = comp.getView();

      c.setMaximumSize(new Dimension(w, h));
    }
  }

  public static void setMinimumSizeEx(iPlatformComponent comp, String width, String height) {
    setMinimumSizeEx(comp, ScreenUtils.toPlatformPixelWidth(width, comp, 0), ScreenUtils.toPlatformPixelHeight(height, comp, 0));
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
    int n = -1;

    if ((len > 0) && !text.startsWith("<html>")) {
      n = text.indexOf('_');
    }

    if ((n != -1) && (n < len - 1)) {
      if (n == 0) {
        mn = text.charAt(1);
        text = text.substring(1);
      } else {
        mn = text.charAt(n + 1);
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
  public static String setMnemonicAndText(UIMenuItem item, String text) {
    char mn = 0;

    if (text == null) {
      text = "";
    }

    int len = text.length();
    int n = -1;

    if ((len > 0) && !text.startsWith("<html>")) {
      n = text.indexOf('_');
    }

    if ((n != -1) && (n < len - 1)) {
      if (n == 0) {
        mn = text.charAt(1);
        text = text.substring(1);
      } else {
        mn = text.charAt(n + 1);
        text = text.substring(0, n) + text.substring(n + 1);
      }
    }

    item.setText(text);

    if (mn != 0) {
      item.setMnemonic(mn);
    }

    return text;
  }

  public static void setOrientation(iPlatformComponent component, int cfgOrientation) {
    if ((component != null) && (component.getView() instanceof JComponent)) {
      final JComponent l = component.getView();

      switch (cfgOrientation) {
        case Label.COrientation.horizontal:
          setOrientation(l, Orientation.HORIZONTAL);

          break;

        case Label.COrientation.vertical_up:
          setOrientation(l, Orientation.VERTICAL_UP);

          break;

        case Label.COrientation.vertical_down:
          setOrientation(l, Orientation.VERTICAL_DOWN);

          break;

        default:
          break;
      }
    }
  }

  public static void setOrientation(iPlatformComponent component, Orientation orientation) {
    if ((component != null) && (component.getView() instanceof JComponent)) {
      setOrientation(component.getView(), orientation);
    }
  }

  public static void setOrientation(JComponent view, Orientation orientation) {
    if (orientation != null) {
      if (view instanceof LabelView) {
        ((LabelView) view).setOrientation(orientation);
      } else if (view instanceof iView) {
        iView v = (iView) view;

        switch (orientation) {
          case VERTICAL_DOWN:
            v.setTransformEx(AffineTransform.getRotateInstance(-Math.PI / 2));

            break;

          case VERTICAL_UP:
            v.setTransformEx(AffineTransform.getRotateInstance(Math.PI / 2));

            break;

          default:
            v.setTransformEx(null);

            break;
        }
      }
    }
  }

  public static void setPreferredSizeEx(iPlatformComponent c, com.appnativa.rare.spot.Rectangle bounds) {
    setPreferredSizeEx(c, bounds.width.getValue(), bounds.height.getValue());
  }

  public static void setPreferredSizeEx(iPlatformComponent c, Widget cfg) {
    setPreferredSizeEx(c, cfg.bounds.width.getValue(), cfg.bounds.height.getValue());
  }

  public static void setPreferredSizeEx(iPlatformComponent c, String width, String height) {
    if ((width != null) && (width.length() > 0) && !width.equals("-1")) {
      c.putClientProperty(iConstants.RARE_WIDTH_PROPERTY, width);
    }

    if ((height != null) && (height.length() > 0) && !height.equals("-1")) {
      c.putClientProperty(iConstants.RARE_HEIGHT_PROPERTY, height);
    }
  }

  public static void setScreenOrientation(Object orientation) {
  }

  public static void setShortcut(UIMenuItem mi, String keystroke) {
    KeyStroke ks = SwingHelper.getKeyStroke(keystroke);

    if ((ks != null) && (mi.getMenuItem() instanceof JMenuItem)) {
      ((JMenuItem) mi.getMenuItem()).setAccelerator(ks);
    }
  }

  public static void setTargetRenderType(UITarget target, RenderType rt) {
    if (rt == null) {
      rt = RenderType.STRETCHED;
    }

    java.awt.Component v = target.getContainerComponent().getView();

    if (v instanceof FrameView) {
      ((FrameView) v).setViewRenderType(rt);
    }
  }

  public static void setText(iPlatformComponent comp, String text) {
    java.awt.Component c = comp.getView();

    if (c instanceof JLabel) {
      ((JLabel) c).setText(text);
    } else if (c instanceof AbstractButton) {
      ((AbstractButton) c).setText(text);
    } else if (c instanceof JTextComponent) {
      ((JTextComponent) c).setText(text);
    }
  }

  public static void setTextAlignment(iPlatformComponent comp, HorizontalAlign ha, VerticalAlign va) {
    SwingHelper.setHorizontalAlignment(comp.getView(), ha);
    SwingHelper.setVerticalAlignment(comp.getView(), va);
  }

  public static void setTextAlignment(java.awt.Component comp, HorizontalAlign ha, VerticalAlign va) {
    SwingHelper.setHorizontalAlignment(comp, ha);
    SwingHelper.setVerticalAlignment(comp, va);
  }

  public static void setUseDarkStatusBarText(boolean dark) {
  }

  public static ClassLoader getApplicationClassLoader() {
    return ((PlatformImpl) Platform.getPlatform()).getApplicationClassLoader();
  }

  /**
   * Returns a list of all available font names
   *
   * @return a list of all available font names
   */
  public static List<String> getAvailableFontNames() {
    String[] sa = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    int len = sa.length;
    FilterableList<String> list = new FilterableList<String>(len);

    FontUtils.addCustomFontNamesToList(list);

    UIFont f;

    for (int i = 0; i < len; i++) {
      f = new UIFont(sa[i], UIFont.PLAIN, 10);

      if (f.canDisplay('A')) {
        list.add(sa[i]);
      }
    }

    return list;
  }

  /**
   * Returns a list of all available fonts
   *
   * @return a list of all available fonts
   */
  public static List<UIFont> getAvailableFonts() {
    UIFont f = FontUtils.getDefaultFont();
    int size = f.getSize();
    String[] sa = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    int len = sa.length;
    FilterableList<UIFont> list = new FilterableList<UIFont>(len);

    FontUtils.addCustomFontsToList(list);

    for (int i = 0; i < len; i++) {
      f = new UIFont(sa[i], UIFont.PLAIN, size);

      if (f.canDisplay('A')) {
        list.add(f);
      }
    }

    return list;
  }

  public static int getCharacterWidth(UIFont f) {
    if (_fontLabel == null) {
      _fontLabel = new JLabel();
    }

    return _fontLabel.getFontMetrics(f).charWidth('m');
  }

  public static UIColor getColorStateList(Map<String, String> map) {
    return ColorUtils.getSimpleColorStateList(map);
  }

  /**
   * Returns an action to perform a copy operation.
   *
   * @return the copy action
   */
  public static aFocusedAction getCopyAction() {
    return ActionHelper.getCopyAction();
  }

  /**
   * Returns an action to perform a cut operation.
   *
   * @return the cut action
   */
  public static aFocusedAction getCutAction() {
    return ActionHelper.getCutAction();
  }

  public static String getDefaultRowHeight() {
    String s = Platform.getUIDefaults().getString("Rare.List.rowHeight");

    return (s == null) ? "1ln" : s;
  }

  /**
   * Returns an action to perform a cut operation.
   *
   * @return the cut action
   */
  public static aFocusedAction getDeleteAction() {
    return ActionHelper.getDeleteAction();
  }

  public static Object getDeviceConfiguration() {
    return new WindowDeviceConfiguration(getScreenSize());
  }

  public static Object getConfiguration(iPlatformComponent comp) {
    WindowViewer w = Platform.getWindowViewer(comp);
    if (w == null) {
      w = Platform.getWindowViewer();
    }
    return new WindowDeviceConfiguration(w.getSize());
  }

  public static UIColor getDrawableStateList(Map<String, String> map) {
    return ColorUtils.getSimpleDrawableStateList(map);
  }

  public static File getFile(iPlatformComponent context, String title, boolean open, boolean dironly, File dir, String extfilters) {
    return SwingHelper.getFileWithNativeDialog(context.getView(), title, open, dironly, dir, extfilters);
  }

  public static float getFontHeight(UIFont font, boolean full) {
    if (_fontLabel == null) {
      _fontLabel = new JLabel();
    }

    FontMetrics fm = _fontLabel.getFontMetrics(font);
    float h = fm.getHeight();

    if (full) {
      h += fm.getDescent();
    }

    return h;
  }

  public static int getFormsDefaultScreenResolution() {
    return (int) UIScreen.getScreenDPI();
  }

  public static UIImage getImageFromResourceFileName(String name) {
    String file = Rare.makeResourcePath(name);
    UIImage img = null;

    try {
      img = ImageHelper.createImage(fileToURL(file), false, 0);
      img.setResourceName(name);
    } catch (IOException e) {
      Platform.ignoreException(null, e);
    }

    return img;
  }

  public static float getLineHeight(UIFont font) {
    if (_fontLabel == null) {
      _fontLabel = new JLabel();
    }

    _fontLabel.setText("ABCWypgYPG");

    int h = _fontLabel.getPreferredSize().height;

    return h;
  }

  public static UIFont getMonospacedFont(int size) {
    String name = "Monospaced";

    if (Platform.isMac()) {
      name = "Monaco";
    } else if (Platform.isWindows()) {
      name = "Lucida Sans Typewriter";
    }

    UIFont f = new UIFont(name, UIFont.PLAIN, size);

    if ("Dialog".equals(f.getFamily())) {
      f = new UIFont("Monospaced", UIFont.PLAIN, size);
    }

    return f;
  }

  /**
   * Returns an action to perform a paste operation.
   *
   * @return the paste action
   */
  public static aFocusedAction getPasteAction() {
    return ActionHelper.getPasteAction();
  }

  /**
   * Returns an action to perform a redo operation.
   *
   * @return the redo action
   */
  public static aFocusedAction getRedoAction() {
    return ActionHelper.getRedoAction();
  }

  public static Object getResourceAsDrawable(String name) {
    return ((AppContext) Platform.getAppContext()).getManagedResource(name);
  }

  public static UIColor getResourceColor(String color) {
    if (color == null) {
      return null;
    }

    boolean good = false;
    boolean pattern = false;

    if (color.startsWith("drawable/")) {
      color = color.substring(9);
      good = true;
    } else if (color.startsWith("pattern/")) {
      good = true;
      color = color.substring(8);
      pattern = true;
    }

    if (!good) {
      return null;
    }

    UIImage img = ((AppContext) Platform.getAppContext()).getManagedResource(color);

    if (img == null) {
      return null;
    }

    UIImagePainter ip = new UIImagePainter(img);

    if (!pattern) {
      ip.setRenderType(RenderType.STRETCHED);
    }

    return new UIColorShade(ip);
  }

  public static Object getResourceId(String propvalue) {
    return null;
  }

  public static float getScaledImageDensity() {
    return 1;
  }

  public static int getScreen(iPlatformComponent comp) {
    java.awt.Component c = comp.getView();
    Window w = SwingUtilities.windowForComponent(c);
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

  public static UIRectangle getScreenBounds() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Rectangle r = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();

    return new UIRectangle(r.x, r.y, r.width, r.height);
  }

  public static UIRectangle getScreenBounds(int monitor) {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] gs = ge.getScreenDevices();
    Rectangle r = gs[monitor].getDefaultConfiguration().getBounds();

    return new UIRectangle(r.x, r.y, r.width, r.height);
  }

  public static int getScreenCount() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    return ge.getScreenDevices().length;
  }

  public static String getScreenDensityName() {
    return "mdpi";
  }

  public static int getScreenHeight() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Rectangle r = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();

    return r.height;
  }

  public static int getScreenHeight(int screen) {
    Rectangle r = getScreenBoundsEx(screen);

    return (r == null) ? 0 : r.height;
  }

  public static Object getScreenOrientation() {
    return new WindowDeviceConfiguration(getScreenSize());
  }

  public static int getScreenRotation() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Rectangle r = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
    return r.width > r.height ? 90 : 0;
  }

  public static int getScreenRotation(Object orientation) {
    if (orientation instanceof WindowDeviceConfiguration) {
      return ((WindowDeviceConfiguration) orientation).isWider() ? 90 : 0;
    }
    return getScreenRotation();
  }

  public static UIDimension getScreenSize() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Rectangle r = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();

    return new UIDimension(r.width, r.height);
  }

  public static UIDimension getScreenSize(int screen) {
    Rectangle r = getScreenBoundsEx(screen);

    return (r == null) ? null : new UIDimension(r.width, r.height);
  }

  public static UIDimension getScreenSizeForConfiguration(Object configuration) {
    if (configuration instanceof WindowDeviceConfiguration) {
      return ((WindowDeviceConfiguration) configuration).getSize();
    }
    return getScreenSize();
  }

  public static int getScreenWidth() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Rectangle r = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();

    return r.width;
  }

  public static int getScreenWidth(int screen) {
    Rectangle r = getScreenBoundsEx(screen);

    return (r == null) ? 0 : r.width;
  }

  /**
   * Returns an action to perform a cut operation.
   *
   * @return the cut action
   */
  public static aFocusedAction getSelectAllAction() {
    return ActionHelper.getSelectAllAction();
  }

  public static UIMenuItem getSeparatorMenuItem() {
    return new UIMenuItem(new JSeparator());
  }

  public static int getSreenYPositionOffset() {
    return 0;
  }

  public static float getTouchSlop() {
    return 4;
  }

  /**
   * Returns an action to perform a undo operation.
   *
   * @return the undo action
   */
  public static aFocusedAction getUndoAction() {
    return ActionHelper.getUndoAction();
  }

  public static float getUnscaledImageDensity() {
    return 1;
  }

  public static UIRectangle getUsableScreenBounds() {
    return getUsableScreenBounds(0);
  }

  public static UIRectangle getUsableScreenBounds(int screen) {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] gs = ge.getScreenDevices();
    GraphicsConfiguration gc = gs[screen].getDefaultConfiguration();
    Rectangle r = sunGraphicsFailed ? null : getUsableBoundsEx(gc);

    if (r == null) {
      r = ge.getMaximumWindowBounds();
    }

    return new UIRectangle(r.x, r.y, r.width, r.height);
  }

  public static UIRectangle getUsableScreenBounds(iPlatformComponent c) {
    int n = (c == null) ? -1 : getScreen(c);

    if (n == -1) {
      n = 0;
    }

    return getUsableScreenBounds(n);
  }

  public static boolean hasEscapeButton() {
    return true;
  }

  public static boolean isDarkTheme() {
    return !"light".equals(Platform.getUIDefaults().getString("Rare.theme"));
  }

  public static boolean isHighDensity() {
    return false;
  }

  public static boolean isLandscapeOrientation(Object orientation) {
    if (orientation instanceof WindowDeviceConfiguration) {
      return ((WindowDeviceConfiguration) orientation).isWider();
    }
    return (orientation == null) || Boolean.TRUE.equals(orientation);
  }

  public static boolean isLeftToRightOrientation(iParentComponent c) {
    return c.getView().getComponentOrientation().isLeftToRight();
  }

  public static boolean isLowDensity() {
    return true;
  }

  public static boolean isMediumDensity() {
    return false;
  }

  public static boolean isMouseClick(Point startPoint, long mousePressedTime, java.awt.event.MouseEvent releaseEvent) {
    if ((startPoint == null) || (releaseEvent == null)) {
      return false;
    }

    return isMouseClick(startPoint.x, startPoint.y, mousePressedTime, releaseEvent);
  }

  public static boolean isMouseClick(UIPoint startPoint, long mousePressedTime, MouseEvent releaseEvent) {
    if ((startPoint == null) || (releaseEvent == null)) {
      return false;
    }

    return isMouseClick(UIScreen.snapToPosition(startPoint.x), UIScreen.snapToPosition(startPoint.y), mousePressedTime,
        (java.awt.event.MouseEvent) releaseEvent.getNativeEvent());
  }

  public static boolean isMouseClick(int x, int y, long startTime, java.awt.event.MouseEvent releaseEvent) {
    Integer t = (Integer) Platform.getUIDefaults().get("Rare.Pointer.clickThreshold");

    if (t == null) {
      t = 500;
    }

    if (releaseEvent.getWhen() - startTime > t) {
      return false;
    }

    t = (Integer) Platform.getUIDefaults().get("Rare.Pointer.clickSize");

    if (t == null) {
      t = 4;
    }

    return (Math.abs(x - releaseEvent.getXOnScreen()) <= t) && (Math.abs(y - releaseEvent.getYOnScreen()) <= t);
  }

  static Rectangle getScreenBoundsEx(int monitor) {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] gs = ge.getScreenDevices();

    return gs[monitor].getDefaultConfiguration().getBounds();
  }

  private static Rectangle getUsableBoundsEx(GraphicsConfiguration gc) {
    try {
      Class cls = Class.forName("sun.java2d.SunGraphicsEnvironment");
      Method m = cls.getMethod("getUsableBounds", GraphicsDevice.class);

      return (Rectangle) m.invoke(null, gc.getDevice());
    } catch (Exception ignore) {
      sunGraphicsFailed = true;
      ignore.printStackTrace();

      return null;
    }
  }

  public static boolean areImagesUpsideDown() {
    return false;
  }

  public static JComponent createGlassView(boolean overlayContainer) {
    return new GlassPanel(overlayContainer);
  }

  public static UISound getSoundResource(String sound) throws Exception {
    if (sound.indexOf('.') == -1) {
      sound += ".mp3";
    }
    if (!sound.startsWith("/")) {
      sound = Rare.makeResourcePath(sound);
    }
    return getSound(Platform.getAppContext().getResourceURL(sound));
  }

  public static UISound getSound(URL url) throws Exception {

    SoundClip clip = null;
    String ext = url.getFile().toLowerCase();

    if (ext.endsWith(".mp3")) {
      Class cls = Platform.loadClass("javazoom.jl.decoder.Decoder");
      cls = Platform.loadClass("com.appnativa.rare.util.MP3SoundClip");
      clip = (SoundClip) cls.getConstructor(URL.class).newInstance(url);
      clip.open();
    }
    if (clip == null) {
      clip = new SoundClip(url);
      clip.open();
    }
    return clip == null ? null : new UISound(clip);

  }

  public static void playSound(UISound uiSound) {
    Clip clip = (Clip) uiSound.getPlatformSound();
    if (clip != null) {
      if (!clip.isOpen()) {
        try {
          clip.open();
        } catch (LineUnavailableException e) {
          throw new ApplicationException(e);
        }
      }
      clip.setFramePosition(0);
      clip.start();
    }
  }

  public static void pauseSound(UISound uiSound) {
    Clip clip = (Clip) uiSound.getPlatformSound();
    if ((clip != null) && !clip.isRunning()) {
      clip.stop();
    }
  }

  public static void resumeSound(UISound uiSound) {
    Clip clip = (Clip) uiSound.getPlatformSound();
    if ((clip != null) && !clip.isRunning()) {
      clip.start();
    }
  }

  public static void stopSound(UISound uiSound) {
    Clip clip = (Clip) uiSound.getPlatformSound();
    if ((clip != null) && clip.isRunning()) {
      clip.stop();
    }
  }

  public static void disposeOfSound(UISound uiSound) {
    Clip clip = (Clip) uiSound.getPlatformSound();
    if ((clip != null)) {
      clip.close();
    }
  }

  public static Object setVolume(UISound uiSound, int percent) {
    Clip clip = (Clip) uiSound.getPlatformSound();
    try {
      if (clip.isControlSupported(FloatControl.Type.VOLUME)) {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);

        volumeControl.setValue((float) (percent / 100.0));
      } else if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
        float value = (float) (percent / 100.0);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log((value == 0.0) ? 0.0001 : value) / Math.log(10.0) * 20.0);

        gainControl.setValue(dB);
      }
    } catch (Exception e) {
      Platform.ignoreException(null, e);
    }

    return uiSound.getPlatformSound();
  }

  public static void beep() {
    UIManager.getLookAndFeel().provideErrorFeedback(null);
  }

}
