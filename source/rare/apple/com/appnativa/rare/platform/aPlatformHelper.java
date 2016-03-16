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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import com.appnativa.rare.CancelableFutureWrapper;
import com.appnativa.rare.Platform;
import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iWeakReference;
import com.appnativa.rare.iWorkerTask;
import com.appnativa.rare.platform.apple.AppContext;
import com.appnativa.rare.platform.apple.ui.DataItemListModel;
import com.appnativa.rare.platform.apple.ui.PopupListBoxHandler;
import com.appnativa.rare.platform.apple.ui.StateListIcon;
import com.appnativa.rare.platform.apple.ui.util.AppleHelper;
import com.appnativa.rare.platform.apple.ui.util.BezierPath;
import com.appnativa.rare.platform.apple.ui.util.ImageHelper;
import com.appnativa.rare.platform.apple.ui.util.ImageUtils;
import com.appnativa.rare.platform.apple.ui.view.AnimationView;
import com.appnativa.rare.platform.apple.ui.view.ButtonView;
import com.appnativa.rare.platform.apple.ui.view.CustomButtonView;
import com.appnativa.rare.platform.apple.ui.view.DialogWindow;
import com.appnativa.rare.platform.apple.ui.view.FrameView;
import com.appnativa.rare.platform.apple.ui.view.GlassView;
import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.platform.apple.ui.view.PopupWindow;
import com.appnativa.rare.platform.apple.ui.view.SeparatorView;
import com.appnativa.rare.platform.apple.ui.view.SpacerView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.platform.apple.ui.view.Window;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.AnimationComponent;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.MenuItem;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.SeparatorMenuItem;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIMenuItem;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aFocusedAction;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformMenuBar;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.ui.renderer.Renderers;
import com.appnativa.rare.ui.text.HTMLCharSequence;
import com.appnativa.rare.viewer.MenuBarViewer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.BeanWidget;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTHelper;
import com.appnativa.spot.iSPOTConstants;
import com.appnativa.util.CharScanner;
import com.appnativa.util.OrderedProperties;
import com.appnativa.util.iPackageHelper;
import com.appnativa.util.iPreferences;
import com.google.j2objc.annotations.Weak;

/*-[
 #import "java/lang/reflect/Field.h"
 #import "AppleHelper.h"
 #import "RAREAPView.h"
 #import "java/lang/NoSuchMethodException.h"
 #import <CommonCrypto/CommonHMAC.h>
 #import <com/appnativa/util/Base64.h>
 ]-*/
//http://mobile.tutsplus.com/tutorials/iphone/ios-sdk-keeping-content-from-underneath-the-keyboard/
public class aPlatformHelper {
  static Object          imagesQueue;
  static PackageHelper   packageHelper;
  static Object          tasksQueue;
  private static boolean scriptingMode;

  public static boolean areImagesUpsideDown() {
    return true;
  }

  public static View createGlassView(boolean overlayContainer) {
    return new GlassView(overlayContainer);
  }

  public void addPackageMapping(String packageName, String classPrefix) {
    if (packageHelper != null) {
      packageHelper.addPackageMapping(packageName, classPrefix);
    }
  }

  public static CharSequence checkForHTML(String text, UIFont font) {
    if (text == null) {
      return text;
    }

    return HTMLCharSequence.checkSequence(text, font);
  }

  public static native void clearSessionCookies()
  /*-[
    NSArray* cookies= [NSArray arrayWithArray: [[NSHTTPCookieStorage sharedHTTPCookieStorage] cookies]];
    if(cookies) {
      [cookies enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        [[NSHTTPCookieStorage sharedHTTPCookieStorage] deleteCookie:(NSHTTPCookie*)obj];
      }];
    }
  ]-*/
  ;

  public static void closeWindow(Window window) {
    if (window != null) {
      iPlatformComponent c = window.getComponent();
      iWidget            w = (c == null)
                             ? null
                             : c.getWidget();

      if (w instanceof WindowViewer) {
        ((WindowViewer) w).close();
      } else if (window instanceof PopupWindow) {
        ((PopupWindow) window).hidePopup();
      } else {
        window.close();
      }
    }
  }

  public static iPlatformComponent componentForEvent(java.util.EventObject uiEvent) {
    Object source = uiEvent.getSource();

    if (source instanceof iPlatformComponent) {
      return (iPlatformComponent) source;
    }

    if (source instanceof View) {
      return ((View) source).getComponent();
    }

    return null;
  }

  public static void configureDragging(iWidget widget, iPlatformComponent comp) {}

  public static void configureDropTracking(iWidget widget, iPlatformComponent comp, int dropMode) {}

  /**
   * Configures keystroke mappings for a widget
   *
   * @param w
   *          the widget context
   * @param comp
   *          the component to attach the mappings to
   * @param set
   *          the set of keystroke mappings
   */
  public static void configureKeystrokes(iWidget w, iPlatformComponent comp, String set) {}

  public static AnimationComponent createAnimationComponent(iWidget context) {
    return new AnimationComponent(new AnimationView());
  }

  public static Object createBean(BeanWidget w, Bean cfg) {
    return Platform.getAppContext().getWindowManager().getComponentCreator().getBean(w, cfg);
  }

  public static iPlatformComponent createBorderPanel(iPlatformComponent comp, iPlatformBorder border) {
    if ((!border.canUseMainLayer() && comp.getView().isScrollView())) {
      ContainerPanel cp = new ContainerPanel(comp);

      comp.getView().setUsePainterBorder(false);
      cp.setBorderPanel(true);

      return cp;
    }

    return comp;
  }

  public static Window createDialog(Window parent, boolean transparent, boolean decorated, boolean modal) {
    DialogWindow w = new DialogWindow(parent, decorated);

    w.setModal(modal);

    return w;
  }

  public static iPlatformIcon createDisabledIcon(iPlatformIcon icon) {
    return ImageHelper.createDisabledIcon(icon);
  }

  public static iPlatformIcon createDisabledIconIfNeeded(iPlatformIcon icon) {
    return ImageHelper.createDisabledIcon(icon);
  }

  public static UIImage createDisabledImage(UIImage image) {
    return ImageHelper.createDisabledImage(image);
  }

  public static UIImageIcon createIcon(URL url, String description, boolean defer, float density) {
    UIImageIcon icon = ImageHelper.createIcon(url, defer, density);

    if ((icon != null) && (description != null)) {
      icon.setDescription(description);
    }

    return icon;
  }

  public static UIImage createImage(URL url, boolean defer, float density) throws IOException {
    return ImageHelper.createImage(url, defer, density);
  }

  public static iActionComponent createLabel(iPlatformComponent context) {
    return new ActionComponent(new LabelView());
  }

  public static iPlatformRenderingComponent createLabelRenderer(iWidget context) {
    return Renderers.createLabelRenderer();
  }

  public static iPlatformListDataModel createListDataModel(iWidget context, List<RenderableDataItem> items) {
    DataItemListModel dm = new DataItemListModel(context, null);

    if (items != null) {
      dm.addAll(items);
    }

    return dm;
  }

  public static iPlatformMenuBar createMenuBar(iContainer viewer, MenuBar mb) {
    MenuBarViewer mv = new MenuBarViewer(null);

    mv.configure(mb);

    return mv;
  }

  public static UIMenuItem createMenuItem(UIAction a, boolean topLevelMenu) {
    return new UIMenuItem(a, new MenuItem(false));
  }

  public static iPlatformPath createPath() {
    return new BezierPath();
  }

  public static iPopup createPopup() {
    return new PopupWindow();
  }

  public static PopupWindow createPopup(boolean modal, boolean transparent) {
    PopupWindow p = new PopupWindow();

    if (!transparent) {
      p.setBackgroundColor(ColorUtils.getBackground());
    }

    p.setModal(modal);

    return p;
  }

  public static PopupListBoxHandler createPopupListBoxHandler(iWidget w, iPlatformListDataModel model,
          boolean forMenu) {
    return new PopupListBoxHandler(w, model, forMenu);
  }

  public static iPlatformRenderingComponent createRenderer(String s, iWidget context) throws ClassNotFoundException {
    return Renderers.createRenderer(s);
  }

  public static iPlatformComponent createSeparatorComponent(iPlatformComponent context) {
    return new Component(new SeparatorView());
  }

  public static iPlatformShape createShape(UIRectangle r) {
    return new UIRectangle(r.x, r.y, r.width, r.height);
  }

  public static iPlatformShape createShape(float x, float y, float width, float height) {
    return new UIRectangle(x, y, width, height);
  }

  public static iPlatformComponent createSpacerComponent(iPlatformComponent context) {
    return new Component(new SpacerView());
  }

  public static iPlatformIcon createStateListIcon(String icon, iWidget context) {
    CharScanner         sc  = new CharScanner();
    Map<String, String> map = new HashMap<String, String>(5);

    map = CharScanner.parseOptionStringEx(sc, map, ';', true);

    return new StateListIcon(PainterHolder.createFromIconStateMap(map, sc));
  }

  public static iParentComponent createTargetContainer(iPlatformAppContext app) {
    ContainerPanel c = new ContainerPanel();

    return c;
  }

  public static iWeakReference createWeakReference(Object value) {
    return new WeakReferenceEx(value);
  }

  public native static aWidget createWidget(Class cls, iContainer parent) throws Exception
  /*-[
  RAREaWidget* w=(RAREaWidget*)[cls.objcClass alloc];
  return [((RAREaWidget*)w) initWithRAREiContainer: parent];
  ]-*/
  ;

  public static void defaultFontUpdated(UIFont font) {}

  public static iCancelableFuture executeBackgroundTask(Callable callable, boolean shuttingDown) {
    if (shuttingDown || (tasksQueue == null)) {
      return CancelableFutureWrapper.NULL_CANCELABLE_FUTURE;
    }

    CancelableOperation op = CancelableOperation.create(callable);

    if ((imagesQueue != null) && (callable instanceof UIImage)) {
      enqueue(imagesQueue, op);
    } else {
      enqueue(tasksQueue, op);
    }

    return op;
  }

  public static iCancelableFuture executeBackgroundTask(Runnable runnable, boolean shuttingDown) {
    if (shuttingDown || (tasksQueue == null)) {
      return CancelableFutureWrapper.NULL_CANCELABLE_FUTURE;
    }

    CancelableOperation op = CancelableOperation.create(runnable);

    if ((imagesQueue != null) && (runnable instanceof UIImageIcon)) {
      enqueue(imagesQueue, op);
    } else {
      enqueue(tasksQueue, op);
    }

    return op;
  }

  public static iCancelableFuture executeSwingWorkerTask(iWorkerTask task, boolean shuttingDown) {
    if (shuttingDown || (tasksQueue == null)) {
      return CancelableFutureWrapper.NULL_CANCELABLE_FUTURE;
    }

    CancelableOperation op = CancelableOperation.create(task);

    enqueue(tasksQueue, op);

    return op;
  }

  public static native URL fileToURL(File f)
  /*-[
  NSURL* url=[[NSURL alloc]initFileURLWithPath:[f getAbsolutePath]];
  return [[JavaNetURL alloc] initWithId:url];
  ]-*/
  ;

  public static native URL fileToURL(String filename)
  /*-[
    NSURL* url=[[NSURL alloc]initFileURLWithPath:filename];
    return [[JavaNetURL alloc] initWithId:url];
   ]-*/
  ;

  public static String format(String spec, Object... values) {
    char[]        format   = spec.toCharArray();
    StringBuilder expanded = new StringBuilder(format.length + 10);
    final int     len      = format.length;
    char          c;
    int           i    = 0;
    int           n    = 0;
    int           vlen = values.length;

    while(i < len) {
      c = format[i++];

      switch(c) {
        case '%' :
          if (i == len) {
            expanded.append(c);
          } else {
            c = format[i];

            if (c != '%') {
              if (n < vlen) {
                expanded.append(values[n++]);
              }

              i++;
            } else {
              expanded.append('%');
            }
          }

          break;

        default :
          expanded.append(c);

          break;
      }
    }

    return expanded.toString();
  }

  public static void handleCookieExtraction(java.net.URLConnection conn) {
    // NO-OP
  }

  public static void handleCookieExtraction(String url, java.net.URLConnection conn) {
    // NO-OP
  }

  public static String handleCookieInjection(java.net.URLConnection conn) {
    return null;
  }

  public static String handleCookieInjection(String url, java.net.URLConnection conn) {
    return url;
  }

  /**
   * Computes a Hash-based Message Authentication Code (HMAC) using the SHA hash
   * function.
   *
   * @param val
   *          the value
   * @param key
   *          the key
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   *
   * @return the results of the computation
   */
  public static native String hmacSHA(String val, String key, boolean base64)    /*-[
                                                                                 if(!val || !key) {
                                                                                 return @"";
                                                                                 }
                                                                                 const char *cKey  = [key cStringUsingEncoding:NSUTF8StringEncoding];
                                                                                 const char *cData = [val cStringUsingEncoding:NSUTF8StringEncoding];
                                                                                 unsigned char cHMAC[CC_SHA256_DIGEST_LENGTH];
                                                                                 CCHmac(kCCHmacAlgSHA256, cKey, strlen(cKey), cData, strlen(cData), cHMAC);
                                                                                 if(base64) {
                                                                                 IOSByteArray * ba=[IOSByteArray arrayWithBytes:(char*)cHMAC count:CC_SHA256_DIGEST_LENGTH];
                                                                                 return [RAREUTBase64 encodeBytesWithByteArray:ba];
                                                                                 }
                                                                                 return [[NSString alloc] initWithBytes:cHMAC
                                                                                 length:CC_SHA256_DIGEST_LENGTH
                                                                                 encoding:NSASCIIStringEncoding];
                                                                                 ]-*/
  ;

  public static native void initializeThreadingService(int max, int imageMax)
  /*-[
    RAREaPlatformHelper_tasksQueue_ = [[NSOperationQueue alloc] init];
    if(max>0) {
      [((NSOperationQueue*)RAREaPlatformHelper_tasksQueue_) setMaxConcurrentOperationCount: max];
      [((NSOperationQueue*)RAREaPlatformHelper_tasksQueue_) setName: @"taskQueue"];
    }
    if(imageMax>0) {
      RAREaPlatformHelper_imagesQueue_=[[NSOperationQueue alloc] init];
      [((NSOperationQueue*)RAREaPlatformHelper_imagesQueue_) setMaxConcurrentOperationCount: imageMax];
      [((NSOperationQueue*)RAREaPlatformHelper_imagesQueue_) setName: @"imagesQueue"];
    }
  ]-*/
  ;

  public static native void setMaxBackgroundThreadCount(int max)
  /*-[
    if(max>0) {
      [((NSOperationQueue*)RAREaPlatformHelper_tasksQueue_) setMaxConcurrentOperationCount: max];
    }
  ]-*/
  ;
  public static void layout(iPlatformComponent c, float x, float y, float w, float h) {
    c.getView().setBounds(x, y, w, h);
  }

  public static void layoutFrameContainer(RenderType renderType, iParentComponent target, iPlatformComponent view,
          float width, float height) {
    UIInsets    insets = target.getInsets(null);
    float       top    = insets.top;
    float       bottom = target.getHeight() - insets.bottom;
    float       left   = insets.left;
    float       right  = target.getWidth() - insets.right;
    UIDimension d      = view.getPreferredSize();

    d.height = (d.height > (bottom - top))
               ? bottom - top
               : d.height;
    d.width  = (d.width > (right - left))
               ? right - left
               : d.width;

    float x = left;
    float y = top;

    switch(renderType) {
      case LOWER_LEFT :
        break;

      case LOWER_MIDDLE :
        x = Math.max(left, (right - left - d.width) / 2);

        break;

      case LOWER_RIGHT :
        x = Math.max(left, right - d.width);

        break;

      case UPPER_LEFT :
        y = Math.max(top, bottom - d.height);

        break;

      case UPPER_MIDDLE :
        x = Math.max(left, (right - left - d.width) / 2);
        y = Math.max(top, bottom - d.height);

        break;

      case UPPER_RIGHT :
        x = Math.max(left, right - d.width);
        y = Math.max(top, bottom - d.height);

        break;

      case LEFT_MIDDLE :
        y = Math.max(top, (bottom - top - d.height) / 2);

        break;

      case RIGHT_MIDDLE :
        x = Math.max(left, right - d.width);
        y = Math.max(top, (bottom - top - d.height) / 2);

        break;

      case CENTERED :
        x = Math.max(left, (right - d.width) / 2);
        y = Math.max(top, (bottom - d.height) / 2);

        break;

      case STRETCHED :
        x        = 0;
        y        = 0;
        d.width  = width;
        d.height = height;

        break;

      case STRETCH_WIDTH :
        x       = 0;
        y       = 0;
        d.width = width;

        break;

      case STRETCH_HEIGHT :
        x        = 0;
        y        = 0;
        d.height = height;

        break;

      case STRETCH_WIDTH_MIDDLE :
        x       = 0;
        y       = Math.max(top, (bottom - d.height) / 2);
        d.width = width;

        break;

      case STRETCH_HEIGHT_MIDDLE :
        x        = Math.max(left, (right - d.width) / 2);
        y        = 0;
        d.height = height;

        break;

      default :
        break;
    }

    view.setBounds(x, y, d.width, d.height);
  }

  public static Class loadClass(String name) throws ClassNotFoundException {
    return packageHelper.loadClass(name);
  }

  public static void loadIcon(iPlatformAppContext app, UIImageIcon ic) {
    executeBackgroundTask(ic, false);
  }

  public static iPlatformComponent makeResizable(iWidget w, boolean createCorner, iPlatformIcon cornerIcon) {
    ActionComponent c = new ActionComponent(new LabelView());

    c.setIcon(cornerIcon);

    return c;
  }

  public static iPlatformComponent makeScrollPane(aViewer context, ScrollPane cfg, iPlatformComponent comp) {
    return AppleHelper.makeScrollPane(context, cfg, comp);
  }

  public static void performHapticFeedback(Object view) {}

  public static iPlatformComponent resolveBeanComponent(Object bean) {
    if (bean instanceof iPlatformComponent) {
      return (iPlatformComponent) bean;
    }

    Component c = new Component((View) bean);

    if ((bean instanceof SpacerView) || (bean instanceof SeparatorView)) {
      c.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, RenderableDataItem.HorizontalAlign.FILL);
      c.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, RenderableDataItem.VerticalAlign.FILL);
    }

    return c;
  }

  public static UIImage scaleImage(UIImage image, int width, int height) {
    ScalingType st = image.getScalingType();

    return ImageUtils.getScaledImage(image, width, height, (st == null)
            ? ScalingType.BILINEAR
            : st);
  }

  public static native void stopBackgroundThreads()
  /*-[
    if(RAREaPlatformHelper_tasksQueue_) {
      [((NSOperationQueue*)RAREaPlatformHelper_tasksQueue_) setSuspended: YES];
      [((NSOperationQueue*)RAREaPlatformHelper_tasksQueue_) cancelAllOperations];
      RAREaPlatformHelper_tasksQueue_=nil;
    }
    if(RAREaPlatformHelper_imagesQueue_) {
      [((NSOperationQueue*)RAREaPlatformHelper_imagesQueue_) setSuspended: YES];
      [((NSOperationQueue*)RAREaPlatformHelper_imagesQueue_) cancelAllOperations];
      RAREaPlatformHelper_imagesQueue_=nil;
    }
  ]-*/
  ;

  public static native boolean supportsSyncUpdateWithScreenRefresh(iPlatformComponent c)
  /*-[
  #if TARGET_OS_IPHONE
    return [c.getView.getProxy isKindOfClass:[RAREAPView  class]];
  #endif
    return false;
  ]-*/
  ;

  public static native void syncUpdateWithScreenRefresh(iPlatformComponent c, boolean sync)
  /*-[
  #if TARGET_OS_IPHONE
    ((RAREAPView*)c.getView.getProxy).syncWithDisplayRefresh=sync;
  #endif
  ]-*/
  ;

  public static Throwable unwrapJavaScriptException(Throwable e) {
    return e;
  }

  public static boolean useInFormLayoutMeasureHeights(iPlatformComponent component) {
    return true;
  }

  public static void setAutoRepeats(iActionComponent comp, int interval) {
    if (comp.getView() instanceof ButtonView) {
      ((ButtonView) comp.getView()).setAutoRepeats(interval);
    } else if (comp.getView() instanceof CustomButtonView) {
      ((CustomButtonView) comp.getView()).setAutoRepeats(interval);
    }
  }

  public static native void setCookie(String cookieHeader, URL url, String value)
  /*-[
    NSNumber* ver= [NSNumber numberWithInt: [cookieHeader isEqualToString:@"Cookie"] ? 0 : 1];
    NSURL *u=(NSURL*)url.getNSURL;
    NSDictionary *d=[NSDictionary dictionaryWithObjectsAndKeys:u,NSHTTPCookieOriginURL,value,NSHTTPCookieValue,ver, NSHTTPCookieVersion, nil];
    [[NSHTTPCookieStorage sharedHTTPCookieStorage] setCookie:[NSHTTPCookie cookieWithProperties:d]];
  ]-*/
  ;

  /**
   * Set a cookie value for the given URL
   *
   * @param url
   *          the URL
   * @param value
   *          the cookie value
   */
  public static void setCookieValue(java.net.URL url, String value) {
    setCookie("Cookie", url, value);
  }

  public static void setLabelForComponent(iPlatformComponent comp, Object l) {}

  public static void setShortcut(UIMenuItem mi, String keystroke) {}

  public static void setStrictScriptingMode(boolean strict) {
    scriptingMode = strict;
  }

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
    comp.getView().setText(string);
  }

  public static void setTextAlignment(iPlatformComponent comp, HorizontalAlign ha, VerticalAlign va) {
    comp.getView().setTextAlignment(ha, va);
  }

  public static void setUseDarkStatusBarText(boolean dark) {}

  public static Object getBackgroundQueue() {
    return tasksQueue;
  }

  public static UIColor getColorStateList(Map<String, String> map) {
    return ColorUtils.getSimpleColorStateList(map);
  }

  public native static Constructor getConstructor(Class cls, Class<?>... params)
  /*-[
    NSMutableString *name = [@"init" mutableCopy];
    BOOL first = YES;
    for (int i = 0; i < [params count]; i++) {
      IOSClass *cls = [params objectAtIndex:i];
      if (first) {
        [name appendString:@"With"];
        first = NO;
      } else {
        [name appendString:@"with"];
      }
      if(cls.objcClass) {
        [name appendFormat:@"%@:", [cls getSimpleName]];
      }
      else {
        [name appendFormat:@"%@:", NSStringFromProtocol(cls.objcProtocol)];
      }
    }
    SEL selector = NSSelectorFromString(name);
    if (cls != nil && ![cls.objcClass instancesRespondToSelector:selector]) {
      while (cls != nil) {
        cls = [cls getSuperclass];
        if ([cls.objcClass instancesRespondToSelector:selector]) {
          break;
        }
      }
    }
    if(cls==nil) {
      return nil;
    }
    return [JavaLangReflectConstructor constructorWithSelector:selector withClass:cls withMetadata: nil];
  ]-*/
  ;

  
  public static String getDefaultRowHeight() {
    String s = Platform.getUIDefaults().getString("Rare.List.rowHeight");
    if(s==null) {
      s=Platform.hasPointingDevice() ? "1ln" : "1.75ln";
    }
    return s;
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

  public static UIColor getDrawableStateList(Map<String, String> map) {
    return ColorUtils.getSimpleDrawableStateList(map);
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

  public static String getIOSClassName(String javaFullClassName) {
    return packageHelper.getIOSClassName(javaFullClassName);
  }

  public static Object getImagesBackgroundQueue() {
    return (imagesQueue == null)
           ? tasksQueue
           : imagesQueue;
  }

  public static UIFont getMonospacedFont(int size) {
    if (Platform.isIOS()) {
      return new UIFont("Courier New", UIFont.PLAIN, size);
    }

    return new UIFont("Monaco", UIFont.PLAIN, size);
  }

  public static String getPackageName(Class cls) {
    return packageHelper.getPackageName(cls);
  }

  /**
   * Returns an action to perform a paste operation.
   *
   * @return the paste action
   */
  public static aFocusedAction getPasteAction() {
    return ActionHelper.getPasteAction();
  }

  public static iPreferences getPreferences(String appKey) {
    return new NSUserDefaultsPreferences(appKey);
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

    boolean good    = false;
    boolean pattern = false;

    if (color.startsWith("drawable/")) {
      color = color.substring(9);
      good  = true;
    } else if (color.startsWith("pattern/")) {
      good    = true;
      color   = color.substring(8);
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
      ImageUtils.setResourceName(img.getProxy(), color);
    }

    return new UIColorShade(ip);
  }

  public static Object getResourceId(String name) {
    return name;
  }

  public static float getScaledImageDensity() {
    return 1;
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
    return new UIMenuItem(new SeparatorMenuItem());
  }

  public static boolean getStrictScriptingMode() {
    return scriptingMode;
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

  public static UIDimension getWindowDecorationSize() {
    return new UIDimension(0, 0);
  }

  public static boolean isDarkTheme() {
    return !"light".equals(Platform.getUIDefaults().getString("Rare.theme"));
  }

  public static boolean isMouseClick(UIPoint startPoint, long startTime, MouseEvent releaseEvent) {
    if ((startPoint == null) || (releaseEvent == null)) {
      return false;
    }

    Integer t = (Integer) Platform.getUIDefaults().get("Rare.Pointer.clickThreshold");

    if (t == null) {
      t = 500;
    }

    if (releaseEvent.getWhen() - startTime > t) {
      return false;
    }

    t = (Integer) Platform.getUIDefaults().get("Rare.Pointer.clickSize");

    if (t == null) {
      t = Platform.isTouchDevice()
          ? UIScreen.platformPixels(32)
          : 4;
    }

    UIPoint p = releaseEvent.getLocationOnScreen();

    return (Math.abs(startPoint.x - p.x) <= t) && (Math.abs(startPoint.y - p.y) <= t);
  }

  public static boolean isOptimizationEnabled() {
    return true;
  }

  public static void setOptimizationEnabled(boolean enabled) {}

  private static native void enqueue(Object queue, CancelableOperation cop)
  /*-[
    [((NSOperationQueue*)queue) addOperation:(NSOperation*)cop->proxy_];
  ]-*/
  ;

  static class PackageHelper implements iPackageHelper {
    public static OrderedProperties packageMap        = new OrderedProperties();
    public static HashMap           reversePackageMap = new HashMap();
    String                          rareSPOTPrefix;

    public PackageHelper() {
      SPOTHelper.setPackageHelper(this);

      try {
        packageMap.load(new FileInputStream(AppContext.makeResourcePath("raw", "pkg", "properties")));

        String apppkg = AppContext.makeResourcePath(null, "appnativa_user_pkg", "properties");

        if (apppkg != null) {
          packageMap.load(new FileInputStream(apppkg));
        }

        rareSPOTPrefix = packageMap.getProperty(Platform.RARE_SPOT_PACKAGE_NAME);

        Iterator it = packageMap.entrySet().iterator();
        Entry    e;

        while(it.hasNext()) {
          e = (Entry) it.next();
          reversePackageMap.put(e.getValue(), e.getKey());
        }
      } catch(Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
    }

    public void addPackageMapping(String packageName, String classPrefix) {
      packageMap.put(packageName, classPrefix);
      reversePackageMap.put(classPrefix, packageName);
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
      return loadClassEx(getIOSClassName(name));
    }

    @Override
    public native Class getFieldClass(Object field)
    /*-[
      return [(JavaLangReflectField*)field getType];
    ]-*/
    ;

    public String getIOSClassName(String name) {
      String prefix = findClassNamePrefix(name);

      if (prefix != null) {
        int n = name.lastIndexOf('.');

        name = prefix + name.substring(n + 1);
      } else {
        name = createIOSClassName(name);
      }

      name = name.replace('$', '_');

      return name;
    }

    @Override
    public String getPackageName(Class cls) {
      String name = cls.getName();
      int    n    = name.lastIndexOf('.');

      if (n != -1) {
        return name.substring(0, n);
      }

      if (name.startsWith("SPOT")) {
        return iSPOTConstants.SPOT_PACKAGE_NAME;
      }

      if (name.startsWith(rareSPOTPrefix)) {
        return Platform.RARE_SPOT_PACKAGE_NAME;
      }

      StringBuilder sb  = new StringBuilder();
      int           len = name.length();

      for (int i = 0; i < len; i++) {
        char c = name.charAt(i);

        if (Character.isLowerCase(c)) {
          break;
        }

        sb.append(c);
      }

      len = sb.length();

      while(len > 0) {
        name = (String) reversePackageMap.get(sb.toString());

        if (name != null) {
          return name;
        }

        sb.setLength(--len);
      }

      return "";
    }

    protected String createIOSClassName(String name) {
      int           len = name.length();
      StringBuilder sb  = new StringBuilder(len);
      boolean       up  = true;

      for (int i = 0; i < len; i++) {
        char c = name.charAt(i);

        if (c == '.') {
          up = true;

          continue;
        }

        if (up) {
          c  = Character.toUpperCase(c);
          up = false;
        }

        sb.append(c);
      }

      return sb.toString();
    }

    protected String findClassNamePrefix(String name) {
      int n = name.lastIndexOf('.');

      if (n == -1) {
        return null;
      }

      name = name.substring(0, n);

      String cls = packageMap.getProperty(name);

      return (cls == null)
             ? findClassNamePrefix(name)
             : cls;
    }

    protected native Class loadClassEx(String name) throws ClassNotFoundException
    /*-[
      return [AppleHelper loadClass: name];
    ]-*/
    ;
  }


  static class WeakReferenceEx implements iWeakReference {
    @Weak
    Object value;

    public WeakReferenceEx(Object value) {
      this.value = value;
    }

    @Override
    public void clear() {
      value = null;
    }

    @Override
    public Object get() {
      return value;
    }
  }


}
