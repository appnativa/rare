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

package com.appnativa.rare.platform;

import android.app.Activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;

import android.net.Uri;

import android.text.Html.ImageGetter;

import android.util.DisplayMetrics;

import android.view.Display;
import android.view.HapticFeedbackConstants;
import android.view.Surface;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;

import android.webkit.CookieManager;

import android.widget.AbsListView;
import android.widget.TextView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.PlatformImpl;
import com.appnativa.rare.platform.android.iActivity;
import com.appnativa.rare.platform.android.ui.ActionBarHandler;
import com.appnativa.rare.platform.android.ui.DataItemListModel;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.PopupListBoxHandler;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.util.AndroidPath;
import com.appnativa.rare.platform.android.ui.util.AndroidRect;
import com.appnativa.rare.platform.android.ui.util.ImageHelper;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.platform.android.ui.view.AnimationView;
import com.appnativa.rare.platform.android.ui.view.ButtonViewEx;
import com.appnativa.rare.platform.android.ui.view.FrameView;
import com.appnativa.rare.platform.android.ui.view.GlassView;
import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.platform.android.ui.view.SeparatorView;
import com.appnativa.rare.platform.android.ui.view.SpacerView;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.AnimationComponent;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIMenuItem;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIPopupMenu;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UISound;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.WindowDeviceConfiguration;
import com.appnativa.rare.ui.aFocusedAction;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPath;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformMenuBar;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.renderer.Renderers;
import com.appnativa.rare.viewer.FormViewer;
import com.appnativa.rare.viewer.MenuBarViewer;
import com.appnativa.rare.viewer.aViewer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.BeanWidget;
import com.appnativa.rare.widget.aPlatformWidget;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharScanner;
import com.appnativa.util.FilePreferences;
import com.appnativa.util.FilterableList;

import java.io.File;
import java.io.IOException;

import java.net.CookieHandler;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PlatformHelper extends aPlatformHelper {
  public static final HashMap<String, Integer>  colorStates         = new HashMap<String, Integer>();
  private static int[]                          ZERO_INT_ARRAY      = new int[0];
  private static float                          charWidthMultiplier = 1;
  private static final ThreadLocal<CharScanner> perThreadScanner    = new ThreadLocal<CharScanner>() {
    protected synchronized CharScanner initialValue() {
      return new CharScanner();
    }
  };
  private static Paint          paint = new Paint();
  private static DisplayMetrics metrics;

  static {
    colorStates.put("pressed", android.R.attr.state_pressed);
    colorStates.put("selected", android.R.attr.state_selected);
    colorStates.put("focused", android.R.attr.state_focused);
    colorStates.put("enabled", android.R.attr.state_enabled);
    colorStates.put("checked", android.R.attr.state_checked);
    colorStates.put("expanded", android.R.attr.state_expanded);
    colorStates.put("not_pressed", -android.R.attr.state_pressed);
    colorStates.put("not_selected", -android.R.attr.state_selected);
    colorStates.put("not_focused", -android.R.attr.state_focused);
    colorStates.put("not_enabled", -android.R.attr.state_enabled);
    colorStates.put("disabled", -android.R.attr.state_enabled);
    colorStates.put("not_checked", -android.R.attr.state_checked);
    colorStates.put("not_expanded", -android.R.attr.state_expanded);
    colorStates.put("collapsed", -android.R.attr.state_expanded);
    colorStates.put("not_empty", -android.R.attr.state_empty);
    colorStates.put("not_active", -android.R.attr.state_active);
  }

  public static PopupListBoxHandler createPopupListBoxHandler(iWidget w, iPlatformListDataModel model,
          boolean forMenu) {
    return new PopupListBoxHandler(w, model, forMenu);
  }

  public static CharSequence checkForHTML(String text, UIFont font) {
    return LabelView.checkText(text, (ImageGetter) Platform.getWindowViewer());
  }

  public static void clearSessionCookies() {
    try {
      CookieHandler handler = CookieHandler.getDefault();

      if (handler instanceof com.appnativa.rare.net.CookieManager) {
        ((com.appnativa.rare.net.CookieManager) handler).clear();
      }

      CookieManager cm = CookieManager.getInstance();

      cm.removeAllCookie();
    } catch(Exception e) {
      Platform.ignoreException(e);
    }
  }

  public static iPlatformComponent componentForEvent(EventObject uiEvent) {
    if (uiEvent.getSource() instanceof View) {
      iPlatformComponent c = Component.fromView((View) uiEvent.getSource());

      return (c == null)
             ? null
             : new Component((View) uiEvent.getSource());
    }

    return null;
  }

  public static void configureDragging(iWidget widget, iPlatformComponent comp) {}

  public static void configureDropTracking(iWidget widget, iPlatformComponent comp, int dropMode) {}

  public static AnimationComponent createAnimationComponent(iWidget context) {
    if (context == null) {
      context = Platform.getContextRootViewer();
    }

    AnimationView vg = new AnimationView(context.getAppContext().getActivity());

    return new AnimationComponent(vg);
  }

  public static Object createBean(BeanWidget w, Bean cfg) {
    return w.getAppContext().getComponentCreator().getBean(w.getViewer(), cfg);
  }

  public static iPlatformComponent createBorderPanel(iPlatformComponent comp, iPlatformBorder border) {
    if (comp.getView() instanceof AbsListView) {
      comp.setOpaque(false);

      ContainerPanel cp = new ContainerPanel(comp);

      cp.setBorderPanel(true);
      comp = cp;
      comp.setBackground(ColorUtils.getListBackground());
    }

    return comp;
  }

  public static Object createColorWheel(iWidget context) {
    return null;
  }

  public static View createDateButtonView() {
    return new ButtonViewEx(Platform.getAppContext().getActivity(), false);
  }

  public static iPlatformIcon createDisabledIcon(iPlatformIcon icon) {
    return ImageHelper.createDisabledIcon(icon);
  }

  public static iPlatformIcon createDisabledIconIfNeeded(iPlatformIcon icon) {
    return ImageHelper.createDisabledIcon(icon);
  }

  public static UIImageIcon createIcon(URL url, String description, boolean defer, float density) {
    return ImageHelper.createIcon(url, description, defer, density);
  }

  public static UIImage createImage(URL url, boolean defer, float density) throws IOException {
    return ImageHelper.createImage(url, defer, density);
  }

  public static iPlatformGraphics createImageGraphics(int width, int height) {
    Bitmap bmp = ImageUtils.createCompatibleBitmap(width, height);

    return new AndroidGraphics(bmp);
  }

  public static iActionComponent createLabel(iPlatformComponent context) {
    if (context == null) {
      context = Platform.getContextRootViewer().getContainerComponent();
    }

    return new ActionComponent(new LabelView(context.getView().getContext()));
  }

  public static iPlatformRenderingComponent createLabelRenderer(iWidget context) {
    return Renderers.createLabelRenderer(((aPlatformWidget) context).getAndroidContext());
  }

  public static iPlatformListDataModel createListDataModel(iWidget context, List<RenderableDataItem> items) {
    DataItemListModel dm = new DataItemListModel(context, null);

    if (items != null) {
      dm.addAll(items);
    }

    return dm;
  }

  public static iPlatformMenuBar createMenuBar(iViewer context, MenuBar mb) {
    if ("true".equals(mb.spot_getAttribute("installAsActionBar"))) {
      MenuBarViewer mv = new MenuBarViewer(null);

      mv.configure(mb);

      return mv;
    } else {
      iPlatformMenuBar menuBar = new UIPopupMenu(context, mb);
      UIColor          bg      = ColorUtils.getBackgroundColor(mb.bgColor);
      iPlatformBorder  b       = BorderUtils.createBorder(context, mb.getBorders(), null);
      Activity         ra      = AppContext.getRootActivity();

      if ((bg != null) || (b != null)) {
        Drawable d;

        if (bg == null) {
          d = b.getDrawable(null);
        } else if (b == null) {
          d = bg.getDrawable();
        } else {
          d = new LayerDrawable(new Drawable[] { bg.getDrawable(), b.getDrawable(null) });
        }

        ActionBarHandler.setBackgroundDrawable(ra, d);
      }

      if (ra instanceof iActivity) {
        ((iActivity) ra).updateOptionsMenu(menuBar, true);
      }

      return menuBar;
    }
  }

  public static UIMenuItem createMenuItem(UIAction action, boolean topLevel) {
    return new UIMenuItem(action);
  }

  public static iActionComponent createNakedButton(iPlatformComponent context, boolean parentPaints,
          int autoRepeatDelay) {
    ButtonViewEx v = new ButtonViewEx(context.getView().getContext(), false);

    v.setInvalidateParent(parentPaints);
    v.setBackground(NullDrawable.getStatefulInstance());

    if (autoRepeatDelay > 0) {
      v.setAutoRepeats(autoRepeatDelay);
    }

    return new ActionComponent(v);
  }

  public static iPlatformPath createPath() {
    return new AndroidPath();
  }

  public static iPath createPath(iPlatformPath currentPath) {
    return new AndroidPath(currentPath.getPath());
  }

  public static iPlatformRenderingComponent createRenderer(String s, iWidget context) throws ClassNotFoundException {
    return Renderers.createRenderer(s, ((aPlatformWidget) context).getAndroidContext());
  }

  public static iPlatformComponent createSeparatorComponent(iPlatformComponent context) {
    if (context == null) {
      context = Platform.getContextRootViewer().getContainerComponent();
    }

    return new Component(new SeparatorView(context.getView().getContext()));
  }

  public static iPlatformShape createShape(UIRectangle r) {
    return new AndroidRect(r.x, r.y, r.x + r.width, r.y + r.height);
  }

  public static iPlatformShape createShape(double x, double y, double width, double height) {
    return new AndroidRect((float) x, (float) y, (float) (x + width), (float) (y + height));
  }

  public static iPlatformComponent createSpacerComponent(iPlatformComponent context) {
    return new Component(new SpacerView(context.getView().getContext()));
  }

  public static iPlatformIcon createStateListIcon(String icon, iWidget context) {
    return ImageHelper.getDrawableStateList(icon, context);
  }

  public static iParentComponent createTargetContainer(iPlatformAppContext app) {
    return new Container(new FrameView(app.getActivity()));
  }

  public static void defaultFontUpdated(UIFont font) {}

  public static void handleCookieExtraction(URLConnection conn) {
    handleCookieExtraction(conn.getURL().toString(), conn);
  }

  public static void handleCookieExtraction(String url, URLConnection conn) {
    String s = conn.getHeaderField("Set-Cookie");

    if (s == null) {
      s = conn.getHeaderField("set-cookie");
    }

    if (s != null) {
      CookieManager.getInstance().setCookie(url, s);
    }
  }

  public static String handleCookieInjection(URLConnection conn) {
    return handleCookieInjection(conn.getURL().toString(), conn);
  }

  public static String handleCookieInjection(String url, URLConnection conn) {
    String cookies = CookieManager.getInstance().getCookie(url);

    if (cookies != null) {
      conn.addRequestProperty("Cookie", cookies);
    }

    return url;
  }

  public static void hideVirtualKeyboard(iPlatformComponent c) {
    hideVirtualKeyboard(c.getView());
  }

  public static void hideVirtualKeyboard(iWidget context) {
    hideVirtualKeyboard(context.getDataComponent().getView());
  }

  public static boolean hasPhysicalKeyboard() {
    return false;
  }

  public static boolean hasPointingDevice() {
    return false;
  }

  public static void hideVirtualKeyboard(View view) {
    if (view == null) {
      view = Platform.getWindowViewer().getDataView();
    }

    InputMethodManager imm =
      (InputMethodManager) Platform.getAppContext().getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  public static void initialize(Activity a, Display display) {
    metrics = new DisplayMetrics();
    display.getMetrics(metrics);
    charWidthMultiplier = metrics.density;    // scaledDensity;///1.5f;
    ScreenUtils.initilize(metrics.density, metrics.density, 160, metrics.xdpi, metrics.ydpi, 1 / metrics.density);

    Typeface tf = paint.getTypeface();

    if (tf == null) {
      tf = Typeface.DEFAULT;
    }

    FontUtils.setSystemFont(new UIFont(tf, paint.getTextSize()));
    FilePreferences.initialize(a.getFilesDir().getAbsolutePath());
  }

  public static Class loadClass(String name) throws ClassNotFoundException {
    return Class.forName(name);
  }

  public static UIFont loadFont(String name, URL location, String type) {
    return null;
  }

  public static void loadIcon(iPlatformAppContext app, UIImageIcon icon) {
    if ((icon != null) &&!icon.isImageLoaded(null)) {
      app.executeBackgroundTask(icon);
    }
  }

  public static iPlatformComponent makeResizable(iWidget w, boolean createCorner, iPlatformIcon cornerIcon) {
    ActionComponent c = new ActionComponent(new LabelView(w.getAppContext().getActivity()));

    c.setIcon(cornerIcon);

    return c;
  }

  public static iPlatformComponent makeScrollPane(aViewer context, ScrollPane cfg, iPlatformComponent comp) {
    return AndroidHelper.makeScrollPane(context, cfg, comp);
  }

  public static boolean needsHTMLToWrapLabel() {
    return false;
  }

  public static void performHapticFeedback(Object o) {
    View view = null;

    if (o instanceof iWidget) {
      view = ((iWidget) o).getDataComponent().getView();
    } else if (o instanceof View) {
      view = (View) view;
    }

    if (view != null) {
      view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,
                                 HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
    }
  }

  public static void reinitialize(Display display) {
    metrics = new DisplayMetrics();
    display.getMetrics(metrics);
  }

  public static iPlatformComponent resolveBeanComponent(Object bean) {
    if (bean instanceof View) {
      return new Component((View) bean);
    } else {
      return (iPlatformComponent) bean;
    }
  }

  public static UIImage scaleImage(UIImage image, int width, int height) {
    return ImageHelper.scaleImage(image, width, height);
  }

  public static void showVirtualKeyboard(iWidget context) {
    showVirtualKeyboard(context.getDataComponent().getView());
  }

  public static void showVirtualKeyboard(View view) {
    InputMethodManager imm =
      (InputMethodManager) Platform.getAppContext().getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
  }

  public static boolean useInFormLayoutMeasureHeights(iPlatformComponent component) {
    return (component.getView() instanceof TextView);
  }

  public static void setAutoRepeats(iActionComponent comp, int interval) {
    if (comp.getView() instanceof ButtonViewEx) {
      ((ButtonViewEx) comp.getView()).setAutoRepeats(interval);
    }
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
    CookieManager cm = CookieManager.getInstance();

    cm.setCookie(url.toString(), value);
  }

  public static void setLabelForComponent(iPlatformComponent dataComponent, Object l) {}

  public static void setScreenOrientation(Object orientation) {
    Integer newo = (Integer) orientation;

    if ((newo != null) && (newo != AppContext.getContext().getActivity().getRequestedOrientation())) {
      AppContext.getContext().getActivity().setRequestedOrientation(newo);
    }
  }

  public static void setShortcut(UIMenuItem mi, String keystroke) {}

  public static void setTargetRenderType(iTarget target, RenderType rt) {
    if (rt == null) {
      rt = RenderType.STRETCHED;
    }

    View v = target.getContainerComponent().getView();

    if (v instanceof FrameView) {
      ((FrameView) v).setViewRenderType(rt);
    }
  }

  public static void setText(iPlatformComponent comp, String string) {
    View v = comp.getView();

    if (v instanceof TextView) {
      ((TextView) v).setText(string);
    }
  }

  public static void setTextAlignment(iPlatformComponent comp, HorizontalAlign ha, VerticalAlign va) {
    View view = comp.getView();

    if (view instanceof TextView) {
      int hg = AndroidHelper.getGravity(ha);
      int vg = AndroidHelper.getGravity(va);

      ((TextView) view).setGravity(hg | vg);
    }
  }

  public static void setUseDarkStatusBarText(boolean dark) {}

  public static ClassLoader getApplicationClassLoader() {
    return ((PlatformImpl) Platform.getPlatform()).getApplicationClassLoader();
  }

  /**
   * Returns a list of all the names of available fonts in the system
   *
   * @return a list of all the names of available fonts in the system
   */
  public static List<String> getAvailableFontNames() {
    List<String> list       = new FilterableList<String>();
    File         temp       = new File("/system/fonts/");
    String       fontSuffix = ".ttf";

    FontUtils.addCustomFontNamesToList(list);

    for (File font : temp.listFiles()) {
      String fontName = font.getName();

      if (fontName.endsWith(fontSuffix)) {
        list.add(fontName.subSequence(0, fontName.lastIndexOf(fontSuffix)).toString());
      }
    }

    return list;
  }

  /**
   * Returns a list of all available fonts in the system
   *
   * @return a list of all available fonts in the system
   */
  public static List<UIFont> getAvailableFonts() {
    UIFont                 f          = FontUtils.getDefaultFont();
    int                    size       = f.getSize();
    File                   temp       = new File("/system/fonts/");
    String                 fontSuffix = ".ttf";
    FilterableList<UIFont> list       = new FilterableList<UIFont>();

    FontUtils.addCustomFontsToList(list);

    for (File font : temp.listFiles()) {
      String fontName = font.getName();

      if (fontName.endsWith(fontSuffix)) {
        list.add(new UIFont(fontName.subSequence(0, fontName.lastIndexOf(fontSuffix)).toString(), UIFont.PLAIN, size));
      }
    }

    return list;
  }

  public static int getCharacterWidth(UIFont f) {
    paint.setTextSize(f.getSize() * charWidthMultiplier);
    paint.setTypeface(f.getTypeface());

    float w = paint.measureText("m");

    return (int) Math.ceil(w);
  }

  /**
   * Returns a color shade for a the specified colors
   *
   * @return a color object represented by the specified colors as an android
   *         color state list
   */
  public static UIColorShade getColorStateList(UIColor fg, UIColor disabled) {
    int states[][] = new int[2][];

    states[0] = new int[] { -android.R.attr.state_enabled };
    states[1] = ZERO_INT_ARRAY;

    int colors[] = new int[] { disabled.getColor(), fg.getColor() };

    return new UIColorShade(new ColorStateList(states, colors));
  }

  /**
   * Returns a color shade for a the specified colors
   *
   * @return a color object represented by the specified colors as an android
   *         color state list
   */
  public static UIColorShade getColorStateList(UIColor fg, UIColor disabled, UIColor pressed) {
    int states[][] = new int[3][];

    states[0] = new int[] { -android.R.attr.state_enabled };
    states[1] = new int[] { android.R.attr.state_pressed };
    states[2] = ZERO_INT_ARRAY;

    int colors[] = new int[] { disabled.getColor(), pressed.getColor(), fg.getColor() };

    return new UIColorShade(new ColorStateList(states, colors));
  }

  /**
   * Returns a color shade for a string representing a color state list
   *
   * @param map
   *          the color state map
   *
   * @return a color object represented by the specified color string
   */
  public static UIColorShade getColorStateList(Map<String, String> map) {
    if (map == null) {
      return null;
    }

    CharScanner                     sc = perThreadScanner.get();
    String                          color;
    Entry<String, String>           e;
    Iterator<Entry<String, String>> it = map.entrySet().iterator();
    UIColor                         c;
    int                             states[][] = new int[map.size()][];
    int                             colors[]   = new int[map.size()];
    int                             a[];
    int                             i = 0;
    Integer                         state;
    List<String>                    list = new ArrayList<String>(3);

    while(it.hasNext()) {
      e     = it.next();
      color = e.getValue();
      c     = ColorUtils.getColor(color);

      if (c == null) {
        c = ColorUtils.TRANSPARENT_COLOR;
      }

      colors[i] = c.getColor();
      color     = e.getKey();
      sc.reset(color);

      if ("normal".equals(color)) {
        states[i++] = ZERO_INT_ARRAY;

        continue;
      }

      list = sc.getTokens(',', true, list);
      a    = new int[list.size()];

      for (int n = 0; n < a.length; n++) {
        state = colorStates.get(list.get(n));

        if (state == null) {
          a[n] = android.R.attr.state_window_focused;
          Platform.debugLog("Unknow state (" + list.get(n) + ") in color state list");
        } else {
          a[n] = state;
        }
      }

      states[i++] = a;
    }

    return new UIColorShade(new ColorStateList(states, colors));
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

  /**
   * Returns an action to perform a cut operation.
   *
   * @return the cut action
   */
  public static aFocusedAction getDeleteAction() {
    return ActionHelper.getDeleteAction();
  }

  public static Object getDeviceConfiguration() {
    return Platform.getAppContext().getActivity().getResources().getConfiguration();
  }

  public static DisplayMetrics getDisplayMetrics() {
    return metrics;
  }

  /**
   * Returns a color shade for a string representing a drawable state list
   *
   * @param map
   *          the drawable state map
   *
   * @return a color object represented by the specified color string
   */
  public static UIColorShade getDrawableStateList(Map<String, String> map) {
    CharScanner sc = perThreadScanner.get();
    String      color;

    if (map == null) {
      return null;
    }

    Entry<String, String>           e;
    Iterator<Entry<String, String>> it = map.entrySet().iterator();
    UIColor                         c;
    int                             a[];
    Integer                         state;
    List<String>                    list = new ArrayList<String>(3);
    StateListDrawable               sl   = new StateListDrawable();

    while(it.hasNext()) {
      e     = it.next();
      color = e.getValue();
      c     = ColorUtils.getBackgroundColor(color);

      if (c == null) {
        c = ColorUtils.TRANSPARENT_COLOR;
      }

      color = e.getKey();
      sc.reset(color);

      if ("normal".equals(color)) {
        sl.addState(new int[0], c.getDrawable());

        continue;
      }

      list = sc.getTokens(',', true, list);
      a    = new int[list.size()];

      for (int n = 0; n < a.length; n++) {
        state = colorStates.get(list.get(n));

        if (state == null) {
          a[n] = android.R.attr.state_window_focused;
          Platform.debugLog("Unknow state (" + list.get(n) + ") in color state list");
        } else {
          a[n] = state;
        }
      }

      sl.addState(a, c.getDrawable());
    }

    return new UIColorShade(sl, 0);
  }

  public static List<RenderableDataItem> getDropedItems(aWidget dest, iWidget source, boolean copy, boolean text) {
    Object o;

    if (source instanceof iListHandler) {
      if (!text && ((dest == source) || (dest instanceof iListHandler))) {
        o = ((iListHandler) source).getSelections();
      } else {
        o = ((iListHandler) source).getSelectionsAsStrings();
      }
    } else {
      if (dest == source) {
        o = source.getSelection();
      } else {
        o = source.getSelectionAsString();
      }
    }

    return Utils.getItems(o, dest, copy);
  }

  public static File getFile(iPlatformComponent context, String title, boolean open, boolean dironly, File dir,
                             String extfilters) {
    return null;
  }

  public static float getFontHeight(UIFont f, boolean full) {
    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    paint.setTypeface(f.getTypeface());
    paint.setTextSize(f.getSize());

    FontMetrics fm = paint.getFontMetrics();
    float       h;

    if (full) {
      h = Math.abs(fm.top) + Math.abs(fm.bottom);
    } else {
      h = Math.abs(fm.ascent) + Math.abs(fm.descent);
    }

    return h * ScreenUtils.PLATFORM_PIXELS_1;
  }

  public static int getFormsDefaultScreenResolution() {
    DisplayMetrics metrics = new DisplayMetrics();

    AppContext.getRootActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

    return (int) (metrics.density * 160);
  }

  public static UIImage getImageFromResourceFileName(String name) {
    int n = name.lastIndexOf('.');

    if (n != -1) {
      name = name.substring(0, n);
    }

    return ((AppContext) Platform.getAppContext()).getResourceAsImage(name);
  }

  public static UIPoint getLocationOnScreen(iPlatformComponent c, UIPoint pt) {
    if (pt == null) {
      pt = new UIPoint();
    }

    pt.x = 0;
    pt.y = 0;

    View       v = c.getView();
    ViewParent vp;
    int        x = v.getLeft();
    int        y = v.getLeft();
    int        n;

    while(v != null) {
      vp = v.getParent();

      if (vp instanceof View) {
        v = (View) vp;
      } else {
        break;
      }

      Component cc = Component.fromView(v);

      if (cc != null) {
        switch(cc.getOrientation()) {
          case VERTICAL_DOWN :
            n = x;
            x = y;
            y = n;

            break;

          case VERTICAL_UP :
            n = x;
            x = y;
            y = v.getWidth() - n;

            break;

          default :
            break;
        }
      }

      pt.x += x;
      pt.y += y;
      x    = v.getLeft();
      y    = v.getLeft();
    }

    pt.x += x;
    pt.y += y;

    return pt;
  }

  public static UIFont getMonospacedFont(int size) {
    String name = "Monospaced";
    UIFont f    = new UIFont(name, UIFont.PLAIN, size);

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
    return Platform.getAppContext().getResourceAsDrawable(name);
  }

  public static UIColor getResourceColor(String color) {
    try {
      Context ctx = Platform.getAppContext().getActivity();
      int     id;

      if (color.contains(":")) {
        id = ctx.getResources().getIdentifier(color, null, null);

        return new UIColor(ctx.getResources().getColor(id));
      } else {
        boolean drawable = false;
        boolean pattern  = false;

        if (color.startsWith("drawable/")) {
          color    = color.substring(9);
          drawable = true;
        } else if (color.startsWith("pattern/")) {
          color   = color.substring(8);
          pattern = true;
        }

        id = ctx.getResources().getIdentifier(color, drawable
                ? "drawable"
                : "color", ctx.getPackageName());

        Drawable d = ctx.getResources().getDrawable(id);

        if (!pattern) {
          return new UIColorShade(ctx.getResources().getDrawable(id), id);
        }

        return new UIColorShade(new UIImagePainter(new UIImage(d, id)));
      }
    } catch(Exception e) {
      Platform.ignoreException("Missing resource color:" + color, null);

      return UIColor.TRANSPARENT;
    }
  }

  public static Object getResourceComponentView(iWidget context, String name) {
    return AndroidHelper.getResourceComponentView(name);
  }

  public static Object getResourceId(String name) {
    return Platform.getAppContext().getResourceId(name);
  }

  public static float getScaledImageDensity() {
    return 1;
  }

  public static int getScreen(iPlatformComponent c) {
    if ((c != null) && c.isDisplayable()) {
      return 0;
    }

    return -1;
  }

  public static UIRectangle getScreenBounds() {
    return new UIRectangle(0, 0, metrics.widthPixels, metrics.heightPixels);
  }

  public static UIRectangle getScreenBounds(int monitor) {
    if (monitor != 0) {
      return null;
    }

    return new UIRectangle(0, 0, metrics.widthPixels, metrics.heightPixels);
  }

  public static int getScreenCount() {
    return 1;
  }

  public static String getScreenDensityName() {
    switch(metrics.densityDpi) {
      case DisplayMetrics.DENSITY_HIGH :
        return "high";

      case DisplayMetrics.DENSITY_LOW :
        return "low";

      default :
        if (metrics.densityDpi == 320) {
          return "xhigh";
        }

        return "medium";
    }
  }

  public static int getScreenHeight() {
    return metrics.heightPixels;
  }

  public static int getScreenHeight(int screen) {
    if (screen != 0) {
      return 0;
    }

    return metrics.heightPixels;
  }

  public static Object getScreenOrientation() {
    return AppContext.getContext().getActivity().getRequestedOrientation();
  }

  public static int getScreenRotation() {
    return getScreenRotation(AppContext.getRootActivity().getWindowManager().getDefaultDisplay().getRotation());
  }

  public static int getScreenRotation(int rotation) {
    switch(rotation) {
      case Surface.ROTATION_180 :
        return 180;

      case Surface.ROTATION_270 :
        return -90;

      case Surface.ROTATION_90 :
        return 90;

      default :
        return 0;
    }
  }

  public static int getScreenRotation(Object orientation) {
    if (orientation instanceof Number) {
      return getScreenRotation(((Number) orientation).intValue());
    } else if (orientation instanceof Configuration) {
      Configuration cfg = (Configuration) orientation;
      int           rot = getScreenRotation();

      switch(cfg.orientation) {
        case Configuration.ORIENTATION_LANDSCAPE :
          if ((rot != 90) && (rot != -90)) {
            rot = 90;
          }

          break;

        default :
          if ((rot != 0) && (rot != 180)) {
            rot = 0;
          }

          break;
      }

      return rot;
    } else if (orientation instanceof WindowDeviceConfiguration) {
      WindowDeviceConfiguration cfg = (WindowDeviceConfiguration) orientation;

      return (cfg.width > cfg.height)
             ? 90
             : 0;
    }

    return 0;
  }

  public static UIDimension getScreenSize() {
    return new UIDimension(metrics.widthPixels, metrics.heightPixels);
  }

  public static UIDimension getScreenSize(int monitor) {
    if (monitor != 0) {
      return null;
    }

    return new UIDimension(metrics.widthPixels, metrics.heightPixels);
  }

  public static UIDimension getScreenSizeForConfiguration(Object configuration) {
    if (configuration instanceof Configuration) {
      Configuration cfg = (Configuration) configuration;

      return new UIDimension(cfg.screenWidthDp * metrics.density, cfg.screenHeightDp * metrics.density);
    } else if (configuration instanceof WindowDeviceConfiguration) {
      WindowDeviceConfiguration cfg = (WindowDeviceConfiguration) configuration;

      return cfg.getSize();
    }

    return null;
  }

  public static int getScreenWidth() {
    return metrics.widthPixels;
  }

  public static int getScreenWidth(int screen) {
    if (screen != 0) {
      return 0;
    }

    return metrics.widthPixels;
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
    return UIMenuItem.MENU_SEPARATOR;
  }

  public static float getTouchSlop() {
    return ViewConfiguration.get(Platform.getAppContext().getActivity()).getScaledTouchSlop();
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
    return metrics.density;
  }

  public static UIRectangle getUsableScreenBounds() {
    return getUsableScreenBounds(0);
  }

  public static UIRectangle getUsableScreenBounds(int screen) {
    if (screen != 0) {
      return null;
    }

    Rect rect = new Rect();

    Platform.getWindowViewer().getContainerView().getWindowVisibleDisplayFrame(rect);

    UIRectangle r = new UIRectangle(rect.left, rect.top, rect.width(), rect.height());

    return r;
  }

  public static UIRectangle getUsableScreenBounds(iPlatformComponent c) {
    return getUsableScreenBounds(0);
  }

  public static iWidget getWidget(iWidget context, Widget wc) {
    if (wc == null) {
      return null;
    }

    return FormViewer.createWidget(context.getContainerViewer(), wc);
  }

  public static boolean hasEscapeButton() {
    return true;
  }

  public static boolean isDarkTheme() {
    return !"light".equals(Platform.getUIDefaults().getString("Rare.theme"));
  }

  public static boolean isHighDensity() {
    return metrics.densityDpi == DisplayMetrics.DENSITY_HIGH;
  }

  public static boolean isKeyboardVisible() {
    UIRectangle d = getUsableScreenBounds();
    int         h = getScreenHeight();

    return h - d.height > ScreenUtils.platformPixels(100);
  }

  public static boolean isLandscapeOrientation(Object orientation) {
    if (orientation instanceof Number) {
      switch(((Number) orientation).intValue()) {
        case Surface.ROTATION_180 :
          return false;

        case Surface.ROTATION_270 :
          return true;

        case Surface.ROTATION_90 :
          return true;

        default :
          return false;
      }
    } else if (orientation instanceof Configuration) {
      Configuration cfg = (Configuration) orientation;

      return cfg.screenWidthDp > cfg.screenHeightDp;
    } else if (orientation instanceof WindowDeviceConfiguration) {
      WindowDeviceConfiguration cfg = (WindowDeviceConfiguration) orientation;

      return cfg.width > cfg.height;
    }

    return true;
  }

  public static boolean isLeftToRightOrientation(iParentComponent target) {
    return true;
  }

  public static boolean isLowDensity() {
    return metrics.densityDpi == DisplayMetrics.DENSITY_LOW;
  }

  public static boolean isMediumDensity() {
    return metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM;
  }

  public static boolean areImagesUpsideDown() {
    return false;
  }

  public static View createGlassView(boolean overlayContainer) {
    return new GlassView(Platform.getAppContext().getActivity(), overlayContainer);
  }

  public static Object getConfiguration(iPlatformComponent comp) {
    return getDeviceConfiguration();
  }

  public static UISound getSoundResource(String name) {
    MediaPlayer p = null;
    Integer     id;

    id = AppContext.getResourceId(AppContext.getAndroidContext(), "raw/" + name);

    if (id != null) {
      p = MediaPlayer.create(AppContext.getAndroidContext(), id);
    }

    return (p == null)
           ? null
           : new UISound(p);
  }

  public static UISound getSound(URL url) throws Exception {
    Uri         u = Uri.parse(url.toURI().toString());
    MediaPlayer p = MediaPlayer.create(AppContext.getAndroidContext(), u);

    return (p == null)
           ? null
           : new UISound(p);
  }

  public static void stopSound(Object platformSound) {
    MediaPlayer p = (MediaPlayer) platformSound;

    p.stop();
  }

  public static void disposeOfSound(Object platformSound) {
    MediaPlayer p = (MediaPlayer) platformSound;

    p.release();
  }

  public static void pauseSound(Object platformSound) {
    MediaPlayer p = (MediaPlayer) platformSound;

    p.pause();
  }

  public static void playSound(Object platformSound) {
    MediaPlayer p = (MediaPlayer) platformSound;

    p.seekTo(0);
    p.start();
  }

  public static void resumeSound(Object platformSound) {
    MediaPlayer p = (MediaPlayer) platformSound;

    p.start();
  }

  public static Object setVolume(Object platformSound, int percent) {
    MediaPlayer p      = (MediaPlayer) platformSound;
    final float volume = (float) (1 - (Math.log(100 - percent) / Math.log(100)));

    p.setVolume(volume, volume);

    return p;
  }

  public static void playNotificationSound() {
    try {
      Uri      notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      Ringtone r            = RingtoneManager.getRingtone(Platform.getAppContext().getActivity(), notification);

      r.play();
    } catch(Exception e) {
      Platform.ignoreException(null, e);;
    }
  }

  public static void beep() {
    try {
      final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 50);

      tg.startTone(ToneGenerator.TONE_PROP_BEEP);
    } catch(Exception e) {
      Platform.ignoreException(null, e);;
    }
  }

  public static boolean lockOrientation(Boolean landscape) {
    Activity a           = Platform.getAppContext().getActivity();
    int      orientation = a.getResources().getConfiguration().orientation;

    if (landscape == null) {
      switch(orientation) {
        case Configuration.ORIENTATION_LANDSCAPE :
          a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

          break;

        case Configuration.ORIENTATION_PORTRAIT :
          a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

          break;

        default :
          if (ScreenUtils.isWider()) {
            a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
          } else {
            a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
          }

          break;
      }
    } else if (landscape) {
      if (orientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
        a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
      }
    } else {
      if (orientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
        a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      }
    }

    return true;
  }

  public static void unlockOrientation() {
    Activity a = Platform.getAppContext().getActivity();

    if (a.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
      a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }
  }
}
