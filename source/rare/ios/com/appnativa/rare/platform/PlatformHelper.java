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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.apple.ui.view.CustomButtonView;
import com.appnativa.rare.platform.apple.ui.view.TextFieldView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.platform.apple.ui.view.Window;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIMenuItem;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UISound;
import com.appnativa.rare.ui.WindowDeviceConfiguration;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTHelper;

import java.net.URL;

import java.util.List;
import java.util.Map;

/*-[
 #import <UIKit/UIKit.h>
 #import <CoreText/CoreText.h>
 #import "CoreText/CTFontManager.h"
 #import "java/lang/reflect/Field.h"
 #import "AppleHelper.h"
 #import "java/lang/NoSuchMethodException.h"
 #import "APView+Component.h"
 #import "RAREAPApplication.h"
 #import "com/appnativa/rare/viewer/WindowViewer.h"
 #import "RAREUIViewController.h"
 #import <AudioToolbox/AudioToolbox.h>
 ]-*/
public class PlatformHelper extends aPlatformHelper {
  public static final float screenDpi = 72;
  static float              density   = 1;
  static UIColor            background;
  static UIColor            foreground;
  static UIFont             systemFont;

  private PlatformHelper() {}

  public static void clearSessionCookies() {}

  public static View createDateButtonView() {
    return createNakedButtonView(false);
  }

  public static Window createWindow(boolean modal, boolean transparent, boolean decorated) {
    Window w = new Window();

    w.setDecorated(decorated);

    if (!transparent) {
      w.setBackgroundColor(ColorUtils.getBackground());
    }

    w.setModal(modal);

    return w;
  }

  public native static void beep()    /*-[
                                      #if TARGET_OS_IPHONE
                                      #else
                                      NSBeep();
                                      #endif
                                      ]-*/
  ;

  public static iActionComponent createNakedButton(iPlatformComponent context, boolean parentPaints,
          int autoRepeatDelay) {
    CustomButtonView b = new CustomButtonView();

    b.setCallNeedsDisplayOnSuper(parentPaints);
    b.setPaintHandlerEnabled(true);

    if (autoRepeatDelay > 0) {
      b.setAutoRepeats(autoRepeatDelay);
    }

    return new ActionComponent(b);
  }

  public static View createNakedButtonView(boolean parentPaints) {
    CustomButtonView v = new CustomButtonView();

    v.setCallNeedsDisplayOnSuper(parentPaints);

    return v;
  }

  public static void handleCustomProperties(MainWindow cfg, Map<String, Object> properties) {
    setTheme(ColorUtils.getBackground().isDarkColor());
  }

  public static void hideVirtualKeyboard(iPlatformComponent c) {
    View v = c.getView();

    if (v instanceof TextFieldView) {
      ((TextFieldView) v).setShowKeyBoard(false);
    }
  }

  public static void hideVirtualKeyboard(iWidget context) {
    View v = context.getDataComponent().getView();

    if (v instanceof TextFieldView) {
      ((TextFieldView) v).setShowKeyBoard(false);
    }
  }

  public static void initialize() {
    initializeUIDefaults();
    FontUtils.setSystemFont(systemFont);
    ScreenUtils.initilize(density, 1, 72, 72, 72, 1);
    packageHelper = new PackageHelper();
    SPOTHelper.setPackageHelper(packageHelper);
  }

  public native static void loadFont(String name, URL location, String type)
  /*-[
    NSURL* fonturl=((NSURL*)location->proxy_);
    CTFontManagerRegisterFontsForURL((__bridge CFURLRef)(fonturl), kCTFontManagerScopeProcess,nil);
  ]-*/
  ;

  public static native void performHapticFeedback(Object view)
  /*-[
    AudioServicesPlaySystemSound(kSystemSoundID_Vibrate);
  ]-*/
  ;

  public static void showVirtualKeyboard(iWidget context) {
    View v = context.getDataComponent().getView();

    if (v instanceof TextFieldView) {
      ((TextFieldView) v).setShowKeyBoard(true);
    }
  }

  public static native UIDimension stringSize(UIFont font, String text, UIDimension size)
  /*-[
    if(size==nil)  {
      size=[[RAREUIDimension alloc]init];
    }
    if(text==nil) {
            size->width_=0;
            size->height_=0;
    }
    else {
      CGSize ss=[text sizeWithFont:(UIFont*)[font getIOSProxy]];
      size->width_ = (int)ceil(ss.width);
      size->height_ = (int)ceil(ss.height);
    }
    return size;
  ]-*/
  ;

  public native static int stringWidth(UIFont font, String text)
  /*-[
    CGSize ss=[text sizeWithFont:(UIFont*)[font getIOSProxy]];
    return (int)ceil(ss.width);
  ]-*/
  ;

  public native static void systemAlert(iWidget context, Object message, iPlatformIcon icon, iActionListener listener)
  /*-[
    [[UIApplication sharedApplication] cancelAllLocalNotifications];

    UILocalNotification *localNotification = [[UILocalNotification alloc] init];

    NSDate *now = [NSDate date];
    NSDate *dateToFire = [now dateByAddingTimeInterval:1];

    localNotification.fireDate = dateToFire;
    localNotification.alertBody = [message description];
    localNotification.soundName = UILocalNotificationDefaultSoundName;

    NSDictionary *infoDict = [NSMutableDictionary dictionaryWithObjectsAndKeys:listener, @"listener",nil];
    localNotification.userInfo = infoDict;

    [[UIApplication sharedApplication] scheduleLocalNotification:localNotification];
  ]-*/
  ;

  public native static void setScreenOrientation(Object orientation)
  /*-[
    if([orientation isKindOfClass:[NSNumber class]]) {
      NSUInteger o= [((NSNumber *) orientation) unsignedIntegerValue];
      UIWindow* window=[[RAREAPApplication getInstance] getMainWindow];
      [((RAREUIViewController*)window.rootViewController) setOrientationEx:o];
    }
  ]-*/
  ;

  public static void setShortcut(UIMenuItem mi, String keystroke) {}

  public native static void setTheme(boolean dark)
  /*-[
    if (floor(NSFoundationVersionNumber) > NSFoundationVersionNumber_iOS_6_1) {
      [UIApplication sharedApplication].statusBarStyle=dark ? UIStatusBarStyleLightContent : UIStatusBarStyleDefault;
    }
    else {
      [UIApplication sharedApplication].statusBarStyle=dark ? UIStatusBarStyleBlackTranslucent : UIStatusBarStyleDefault;
    }
  ]-*/
  ;

  /**
   * Returns a list of all the names of available fonts in the system
   *
   * @return a list of all the names of available fonts in the system
   */
  public native static List<String> getAvailableFontNames()
  /*-[
    NSArray* names=[UIFont familyNames];
    return [AppleHelper toArrayList:names];
  ]-*/
  ;

  /**
   * Returns a list of all available fonts in the system
   *
   * @return a list of all available fonts in the system
   */
  public native static List<UIFont> getAvailableFonts()
  /*-[
    RAREUIFont* font;
    NSArray* names=[UIFont familyNames];
    int len=(int)[names count];
    int size=[UIFont systemFontSize];
    JavaUtilArrayList* list=[[JavaUtilArrayList alloc] initWithInt:len];
    for(int i=0;i<len;i++) {
      font=[[RAREUIFont alloc]initWithNSString:[names objectAtIndex:i] withInt:0 withInt:size];
      [list addWithId:font];
    }
    return list;
    ]-*/
  ;

  public static int getCharacterWidth(UIFont font) {
    return stringWidth(font, "m");
  }

  public static String getDefaultRowHeight() {
    String s = Platform.getUIDefaults().getString("Rare.List.rowHeight");

    return (s == null)
           ? "1.75ln"
           : s;
  }

  public static Object getDeviceConfiguration() {
    return getScreenOrientation();
  }

  public native static float getFontHeight(UIFont font, boolean full)
  /*-[
    UIFont *f=(UIFont*)[font getIOSProxy];
    return full ? f.lineHeight-f.descender : f.lineHeight;
  ]-*/
  ;

  public static int getFormsDefaultScreenResolution() {
    return 72;
  }

  public native static UIImage getImageFromResourceFileName(String name)
  /*-[
    UIImage* img=[UIImage imageNamed: name];
    return img ? [[RAREUIImage alloc] initWithId: img] : nil;
  ]-*/
  ;

  public static int getScreen(iPlatformComponent c) {
    return (c == null)
           ? -1
           : getScreenEx(c.getProxy());
  }

  public native static UIRectangle getScreenBounds()
  /*-[
    UIScreen* s=[UIScreen mainScreen];
    return [RAREUIRectangle fromRect:[s orientedBounds]];
  ]-*/
  ;

  public native static UIRectangle getScreenBounds(int screen)
  /*-[
    if(screen==-1) {
      return nil;
    }
    UIScreen* s=[UIScreen screens][screen];
    return [RAREUIRectangle fromRect:[s orientedBounds]];
  ]-*/
  ;

  public static native int getScreenCount()
  /*-[
    return (int)[[UIScreen screens] count];
  ]-*/
  ;

  public static String getScreenDensityName() {
    return (ScreenUtils.getDensity() > 1)
           ? "xhdpi"
           : "mdpi";
  }

  public native static int getScreenHeight()
  /*-[
    return (int)[UIScreen currentSize].height;
  ]-*/
  ;

  public static native int getScreenHeight(int screen)
  /*-[
    if(screen==-1) {
      return 0;
    }
    UIScreen* s=[UIScreen screens][screen];
    return [s orientedBounds].size.height;
  ]-*/
  ;

  public native static Object getScreenOrientation()
  /*-[
    UIWindow* window=[[RAREAPApplication getInstance] getMainWindow];
    NSUInteger o=[(RAREUIViewController*)window.rootViewController getOrientationEx];
    return [NSNumber numberWithUnsignedInteger:o];
  ]-*/
  ;

  public static native int getScreenRotation()
  /*-[
    int rotationAngle = 0;
    UIInterfaceOrientation orientation = [UIApplication sharedApplication].statusBarOrientation;
    if (orientation == UIDeviceOrientationPortraitUpsideDown) rotationAngle = 180;
    else if (orientation == UIDeviceOrientationLandscapeLeft) rotationAngle = 90;
    else if (orientation == UIDeviceOrientationLandscapeRight) rotationAngle = -90;
    return rotationAngle;
  ]-*/
  ;

  public native static int getScreenRotation(Object orientation)
  /*-[
   if([orientation isKindOfClass:[NSNumber class]]) {
     int o=[((NSNumber*)orientation) intValue];
     if (o == UIDeviceOrientationPortraitUpsideDown) return 180;
     else if (o == UIDeviceOrientationLandscapeLeft) return 90;
     else if (o == UIDeviceOrientationLandscapeRight) return -90;
   }
   return 0;
   ]-*/
  ;

  public static int getScreenRotationForConfiguration(Object configuration) {
    if (configuration == null) {
      return getScreenRotation();
    }

    if (configuration instanceof WindowDeviceConfiguration) {
      WindowDeviceConfiguration cfg = (WindowDeviceConfiguration) configuration;

      return (cfg.width > cfg.height)
             ? 90
             : 0;
    }

    return getScreenRotation(configuration);
  }

  public native static UIDimension getScreenSize()
  /*-[
    UIScreen* screen=[UIScreen mainScreen];
    CGSize size=[screen orientedBounds].size;
    return [[RAREUIDimension alloc] initWithFloat:size.width withFloat:size.height];
  ]-*/
  ;

  public native static UIDimension getScreenSize(int screen)
  /*-[
    if(screen==-1) {
      return nil;
    }
    UIScreen* s=[UIScreen screens][screen];
    CGSize size=[s orientedBounds].size;
    return [[RAREUIDimension alloc] initWithFloat:size.width withFloat:size.height];
  ]-*/
  ;

  public static UIDimension getScreenSizeForConfiguration(Object configuration) {
    if (configuration instanceof WindowDeviceConfiguration) {
      WindowDeviceConfiguration cfg = (WindowDeviceConfiguration) configuration;

      return cfg.getSize();
    }

    return getScreenSizeForConfigurationEx(configuration);
  }

  native static UIDimension getScreenSizeForConfigurationEx(Object configuration)
  /*-[
    UIScreen* screen=[UIScreen mainScreen];
    CGSize size;
    if([configuration isKindOfClass:[NSNumber class]]) {
      if([screen respondsToSelector:@selector(nativeBounds)]) {
        size=screen.nativeBounds.size;
      }
      else {
        size=[screen bounds].size;
      }
      int o=[((NSNumber*)configuration) intValue];
      if(o == UIDeviceOrientationLandscapeLeft || o==UIDeviceOrientationLandscapeRight) {
        CGFloat w=size.width;
        size.width=size.height;
        size.height=w;
      }
    }
    else {
      size=[screen orientedBounds].size;
    }
    return [[RAREUIDimension alloc] initWithFloat:size.width withFloat:size.height];
  ]-*/
  ;

  public native static int getScreenWidth()
  /*-[
    return (int)[UIScreen currentSize].width;
  ]-*/
  ;

  public static native int getScreenWidth(int screen)
  /*-[
    if(screen==-1) {
      return 0;
    }
    UIScreen* s=[UIScreen screens][screen];
    return [s orientedBounds].size.width;
  ]-*/
  ;

  public static UIColor getSystemBackground() {
    return background;
  }

  public static UIFont getSystemFont() {
    return systemFont;
  }

  public static UIColor getSystemForeground() {
    return foreground;
  }

  public static float getTouchSlop() {
    return 10;
  }

  public static UIRectangle getUsableScreenBounds() {
    return getUsableScreenBounds(0);
  }

  public static UIRectangle getUsableScreenBounds(iPlatformComponent c) {
    int n = (c == null)
            ? -1
            : getScreen(c);

    if (n == -1) {
      n = 0;
    }

    return getUsableScreenBounds(n);
  }

  public native static UIRectangle getUsableScreenBounds(int screen)
  /*-[
    if(screen==-1) {
      return 0;
    }
    UIScreen* s=[UIScreen screens][screen];
    return [RAREUIRectangle fromRect:[s orientedFrame]];
  ]-*/
  ;

  public static boolean hasEscapeButton() {
    return false;
  }

  public static boolean isHighDensity() {
    return density > 1.5f;
  }

  public static boolean isLandscapeOrientation(Object orientation) {
    if (orientation instanceof WindowDeviceConfiguration) {
      WindowDeviceConfiguration cfg = (WindowDeviceConfiguration) orientation;

      return cfg.width > cfg.height;
    }

    return isLandscapeOrientationEx(orientation);
  }

  native static boolean isLandscapeOrientationEx(Object orientation)
  /*-[
    if([orientation isKindOfClass:[NSNumber class]]) {
      int o=[((NSNumber*)orientation) intValue];
      return (o == UIDeviceOrientationLandscapeLeft || o==UIDeviceOrientationLandscapeRight);
    }
   return NO;
  ]-*/
  ;

  public static boolean isLeftToRightOrientation(iParentComponent target) {
    return true;
  }

  public static boolean isLowDensity() {
    return density == 1f;
  }

  public static boolean isMediumDensity() {
    return (density > 1) && (density < 2);
  }

  native static int getScreenEx(Object nsview)
  /*-[
    UIWindow* window;
    if([nsview isKindOfClass:[UIWindow class]]) {
      window=(UIWindow*)nsview;
    }
    else {
      UIView *view=(UIView*)nsview;
      window=[view window];
    }
    if(window==nil) {
      return -1;
    }
    UIScreen* screen=[window screen];
    if(screen==nil) {
      return -1;
    }
    return (int)[[UIScreen screens] indexOfObject:screen];
  ]-*/
  ;

  private native static void initializeUIDefaults()
  /*-[
  //    UILabel* l=[UILabel new];
  //    UIColor* c=l.textColor;
  //    CGFloat  red;
  //    CGFloat  green;
  //    CGFloat  blue;
  //    CGFloat  alpha;
  //    if(c && [c getRed:&red green:&green blue:&blue alpha:&alpha]) {
  //      RAREPlatformHelper_foreground_=[[RAREUIColor alloc] initWithInt:red*255 withInt:green*255 withInt:blue*255 withInt:alpha*255];
  //    }
  //    else {
  //      RAREPlatformHelper_foreground_=[RAREUIColor BLACK];
  //    }
  //    c=[[RAREAPApplication getInstance] getMainWindow].backgroundColor;
  //    if(c && [c getRed:&red green:&green blue:&blue alpha:&alpha]) {
  //      RAREPlatformHelper_background_=[[RAREUIColor alloc] initWithInt:red*255 withInt:green*255 withInt:blue*255 withInt:alpha*255];
  //    }
  //    else {
  //      RAREPlatformHelper_background_=[RAREUIColor WHITE];
  //    }
    RAREPlatformHelper_foreground_=[RAREUIColor BLACK];
    RAREPlatformHelper_background_=[RAREUIColor WHITE];
    UIFont* font=[UIFont systemFontOfSize:[UIFont systemFontSize]];
    RAREPlatformHelper_systemFont_=[[RAREUIFont alloc]initWithId:font];
    RAREPlatformHelper_density_=[UIScreen mainScreen].scale;
  ]-*/
  ;

  private native static int getMonitor(View view)
  /*-[
    UIView* v=((UIView*)view->proxy_);
    if(!v.window) {
      return -1;
    }
    UIScreen* s=[[v window] screen];
    return (int)[[UIScreen screens] indexOfObject: s];
  ]-*/
  ;

  public static boolean lockOrientation(Boolean landscape) {
    lockOrientationEx((landscape == null)
                      ? isLandscapeOrientation(null)
                      : landscape.booleanValue());

    return true;
  }

  public native static void lockOrientationEx(boolean landscape)
  /*-[
    UIWindow* window=[[RAREAPApplication getInstance] getMainWindow];
    [(RAREUIViewController*)window.rootViewController lockOrientation: landscape];
  ]-*/
  ;

  public native static void unlockOrientation()
  /*-[
    UIWindow* window=[[RAREAPApplication getInstance] getMainWindow];
    [(RAREUIViewController*)window.rootViewController unlockOrientation];
  ]-*/
  ;

  public static Object createColorWheel(iWidget context) {
    return null;
  }

  public native static void setUseDarkStatusBarText(boolean dark)
  /*-[
    UIWindow* window=[[RAREAPApplication getInstance] getMainWindow];
    [((RAREUIViewController*)window.rootViewController) setUseDarkStatusBarText: dark];
  ]-*/
  ;

  public native static boolean setComponentAlpha(iPlatformComponent component, float alpha)
  /*-[
    UIView* v=(UIView*)[component getProxy];
    v.alpha=alpha;
    return YES;
  ]-*/
  ;

  public static Object getConfiguration(iPlatformComponent comp) {
    return getDeviceConfiguration();
  }

  public static void setOptimizationEnabled(boolean enabled) {}

  public static UISound getSoundResource(String sound) {
    // TODO Auto-generated method stub
    return null;
  }

  public static UISound getSound(URL resourceURL) {
    // TODO Auto-generated method stub
    return null;
  }
}
