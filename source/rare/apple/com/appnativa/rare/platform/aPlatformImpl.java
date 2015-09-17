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
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.iFunctionHandler;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iTimer;
import com.appnativa.rare.platform.apple.Rare;
import com.appnativa.rare.platform.apple.Timer;
import com.appnativa.rare.platform.apple.aAppContextImpl;
import com.appnativa.rare.platform.apple.ui.util.AppleCacheReference;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharScanner;
import com.appnativa.util.ObjectCache;
import com.appnativa.util.ObjectCache.iCacheReference;
import com.appnativa.util.ObjectCache.iCacheReferenceCreator;
import com.appnativa.util.SNumber;
/*-[
#import "AppleHelper.h"
]-*/

import java.io.File;

import java.net.URL;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aPlatformImpl extends aPlatform {
  private static ThreadLocal<CharScanner> perThreadScanner = new ThreadLocal<CharScanner>() {
    @Override
    protected synchronized CharScanner initialValue() {
      return new CharScanner();
    }
  };
  File                          cacheDir;
  private String                platformName = "Apple";
  private final aAppContextImpl appContext;
  private int                   menubarHeight;
  private boolean               platformIsIOS;
  private boolean               platformIsIPad;
  private boolean               platformIsMAC;
  private float                 platformVersion;
  private String                platformVersionString;

  protected aPlatformImpl(aAppContextImpl context) {
    appContext = context;
    getPlatformInfo();
    ObjectCache.setReferenceCreator(new iCacheReferenceCreator() {
      @Override
      public iCacheReference createCacheReference(Object key, Object value) {
        return new AppleCacheReference(key, value);
      }
    });
    SNumber.setMaxDigits(10);
  }

  @Override
  public boolean browseURL(URL url) {
    return appContext.browseURL(url);
  }

  @Override
  public boolean canGenerateByteCode() {
    return false;
  }

  @Override
  public Object createChartHandler() {
    return createObject("com.appnativa.rare.ui.chart.coreplot.ChartHandler");
  }

  public boolean createDirectory(File file) {
    return createDirectory(file.getAbsolutePath());
  }

  public iPlatformComponent createErrorComponent(iPlatformIcon icon, String message) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Object createObject(String className) {
    try {
      return PlatformHelper.loadClass(className).newInstance();
    } catch(Exception e) {
      Platform.ignoreException(null, e);

      return null;
    }
  }

  @Override
  public iPlatformComponent createPlatformComponent(Object nativeComponent) {
    iPlatformComponent pc = Component.fromView((View) nativeComponent);

    if (pc == null) {
      pc = new Component((View) nativeComponent);
    }

    return pc;
  }

  @Override
  public iTimer createTimer(String name) {
    return new Timer(name);
  }

  public static void defaultFontUpdated(UIFont font) {}

  @Override
  public boolean deleteDirectory(File path) {
    return (path == null)
           ? true
           : deleteDirectory(path.getAbsolutePath());
  }

  @Override
  public iPlatformComponent findPlatformComponent(Object o) {
    if (o instanceof iPlatformComponent) {
      return (iPlatformComponent) o;
    }

    if (!(o instanceof View)) {
      return null;
    }

    while(o instanceof View) {
      iPlatformComponent c = ((View) o).getComponent();

      if (c != null) {
        return c;
      }

      o = ((View) o).getParent();
    }

    return null;
  }

  @Override
  public iWidget findWidgetForComponent(Object o) {
    if (o instanceof Component) {
      Component c = (Component) o;

      if (c.getWidget() != null) {
        return c.getWidget();
      }

      o = c.getView().getParent();
    }

    if (!(o instanceof View)) {
      return null;
    }

    while(o instanceof View) {
      iPlatformComponent c = ((View) o).getComponent();

      if ((c instanceof Component) && ((Component) c).getWidget() != null) {
        return ((Component) c).getWidget();
      }

      o = ((View) o).getParent();
    }

    return null;
  }

  @Override
  public native void invokeLater(Runnable r)
  /*-[
     [AppleHelper invokeLater: r];
   ]-*/
  ;

  @Override
  public native void invokeLater(Runnable r, int delay)
  /*-[
     [AppleHelper invokeLater: r delay: delay];
   ]-*/
  ;

  @Override
  public Class loadClass(String name) throws ClassNotFoundException {
    return PlatformHelper.loadClass(name);
  }

  @Override
  public native boolean mailTo(String uri)    /*-[
     NSURL *url = [[NSURL alloc] initWithString: uri];

   #if TARGET_OS_IPHONE
     [[UIApplication sharedApplication] openURL:url];
   #else
    [[NSWorkspace sharedWorkspace] openURL: url];
   #endif
     return YES;
  ]-*/
  ;

  @Override
  public native boolean mailTo(String address, String subject, String body)    /*-[
    if(!body) body=@"";
    if(!subject) subject=@"";

     NSURL *url = [[NSURL alloc] initWithString:[NSString stringWithFormat:@"mailto:?to=%@&subject=%@&body=%@",
     [address stringByAddingPercentEscapesUsingEncoding:NSASCIIStringEncoding],
        [body stringByAddingPercentEscapesUsingEncoding:NSASCIIStringEncoding],
     [subject stringByAddingPercentEscapesUsingEncoding:NSASCIIStringEncoding]]];

 #if TARGET_OS_IPHONE
     [[UIApplication sharedApplication] openURL:url];
 #else
    [[NSWorkspace sharedWorkspace] openURL: url];
 #endif
    return YES;
  ]-*/
  ;

  @Override
  public void registerWithWidget(iPlatformComponent component, iWidget context) {
    component.setWidget(context);
  }

  @Override
  public void unregisterWithWidget(iPlatformComponent component) {
    component.setWidget(null);
  }

  @Override
  public int getAndroidVersion() {
    return 0;
  }

  @Override
  public iPlatformAppContext getAppContext() {
    return appContext;
  }

  @Override
  public int getAppInstanceCount() {
    return 1;
  }

  @Override
  public File getCacheDir() {
    if (cacheDir == null) {
      File f = new File(getCacheDirName());

      if (createDirectory(f)) {
        cacheDir = f;
      }
    }

    return cacheDir;
  }

  @Override
  public iDataConverter getDataConverter(Class cls) {
    return Rare.getDataConverter(cls);
  }

  @Override
  public Class getDataConverterClass(String name) throws ClassNotFoundException {
    return Rare.getDataConverterClass(name);
  }

  @Override
  public iFunctionHandler getFunctionHandler() {
    return appContext.getFunctionHandler();
  }

  @Override
  public String getOsType() {
    return platformIsMAC
           ? "os x"
           : "ios";
  }

  @Override
  public float getOsVersion() {
    return platformVersion;
  }

  @Override
  public iPlatformComponent getPlatformComponent(Object source) {
    if (source instanceof iPlatformComponent) {
      return (iPlatformComponent) source;
    }

    if (source instanceof View) {
      return ((View) source).getComponent();
    }

    return null;
  }

  @Override
  public String getPlatformType() {
    return platformName;
  }

  @Override
  public double getPlatformVersion() {
    return platformVersion;
  }

  @Override
  public WindowViewer getWindowViewerForComponent(iPlatformComponent c) {
    return Platform.getWindowViewer();
  }

  @Override
  public boolean isAndroid() {
    return false;
  }

  @Override
  public boolean isDebugEnabled() {
    return aRare.isDebugEnabled();
  }

  @Override
  public boolean isDebuggingEnabled() {
    return false;
  }

  @Override
  public boolean isDescendingFrom(iPlatformComponent c, iPlatformComponent container) {
    if ((c == null) || (c.getView() == null) || (container == null) || (container.getView() == null)) {
      return false;
    }

    return c.getView().isDescendantOf(container.getView());
  }

  @Override
  public boolean isHTMLSupportedInLabels() {
    return true;
  }

  @Override
  public boolean isIOS() {
    return platformIsIOS;
  }

  public boolean isIPad() {
    return platformIsIPad;
  }

  public boolean isIPhone() {
    return platformIsIOS &&!platformIsIPad;
  }

  @Override
  public boolean isInitialized() {
    return true;
  }

  @Override
  public boolean isJava() {
    return false;
  }

  @Override
  public boolean isJavaFX() {
    return false;
  }

  @Override
  public boolean isLinux() {
    return false;
  }

  @Override
  public boolean isMac() {
    return platformIsMAC;
  }

  public boolean isShuttingDown() {
    return appContext.isShuttingDown();
  }

  @Override
  public boolean isSwing() {
    return false;
  }

  @Override
  public boolean isTouchDevice() {
    return platformIsIOS;
  }

  @Override
  public boolean isTouchableDevice() {
    return platformIsIOS;
  }

  @Override
  public native boolean isUIThread()    /*-[
    return [NSThread isMainThread];
  ]-*/
  ;

  @Override
  public boolean isUnix() {
    return false;
  }

  @Override
  public boolean isWindows() {
    return false;
  }

  private native boolean createDirectory(String path)
  /*-[
    NSFileManager *fm = [NSFileManager defaultManager];
    NSError *error=nil;
    BOOL isDir = NO;
    if (![fm fileExistsAtPath:path isDirectory:&isDir])
    {
      return [fm createDirectoryAtPath:path
           withIntermediateDirectories:YES
                            attributes:nil
                                 error:&error];
    }
    else {
      return isDir;
    }

   ]-*/
  ;

  private static native boolean deleteDirectory(String dir)    /*-[
NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
NSString *documentsDirectoryPath = [paths objectAtIndex:0];
NSFileManager *fm = [NSFileManager defaultManager];
NSString *directory = [documentsDirectoryPath stringByAppendingPathComponent:dir];
NSLog(@"Removing items at path: %@",directory);
NSError *error = nil;
[fm removeItemAtPath:directory error:&error];
if(error) {
  NSLog(@"Error items at path: '%@'\n %@",directory,[error description]);
}
return error==nil;
]-*/
  ;

  private native String getCacheDirName()    /*-[
   NSArray  *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
   NSString *documentsDirectory = [paths objectAtIndex:0];
   return [NSString stringWithFormat:@"%@/RARECache", documentsDirectory];
  ]-*/
  ;

  private native void getPlatformInfo()
  /*-[
   NSDictionary *systemVersionDictionary =
   [NSDictionary dictionaryWithContentsOfFile:@"/System/Library/CoreServices/SystemVersion.plist"];

   platformName_ =[systemVersionDictionary objectForKey:@"ProductName"];
   platformVersionString_ =[systemVersionDictionary objectForKey:@"ProductVersion"];
   platformVersion_ = [platformVersionString_ doubleValue];
   platformIsMAC_=true;
   #if TARGET_OS_IPHONE
   platformIsIOS_=true;
   platformIsMAC_=false;
   platformVersionString_= [[UIDevice currentDevice] systemVersion];
   platformVersion_= [[[UIDevice currentDevice] systemVersion] doubleValue];

   appContext_->landscapeMode_=UIInterfaceOrientationIsLandscape([UIApplication sharedApplication].statusBarOrientation);

   #ifdef UI_USER_INTERFACE_IDIOM
   platformIsIPad_= (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad);
   #endif
   #else
                menubarHeight_=[[[NSApplication sharedApplication] mainMenu] menuBarHeight];
   #endif
  ]-*/
  ;
}
