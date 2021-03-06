//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/aPlatformImpl.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/converters/iDataConverter.h"
#include "com/appnativa/rare/iFunctionHandler.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/iTimer.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/platform/aPlatformImpl.h"
#include "com/appnativa/rare/platform/aRare.h"
#include "com/appnativa/rare/platform/apple/Rare.h"
#include "com/appnativa/rare/platform/apple/Timer.h"
#include "com/appnativa/rare/platform/apple/aAppContextImpl.h"
#include "com/appnativa/rare/platform/apple/ui/util/AppleCacheReference.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/CharScanner.h"
#include "com/appnativa/util/ObjectCache.h"
#include "com/appnativa/util/SNumber.h"
#include "java/io/File.h"
#include "java/lang/ClassNotFoundException.h"
#include "java/lang/Runnable.h"
#include "java/lang/ThreadLocal.h"
#include "java/lang/UnsupportedOperationException.h"
#include "java/net/URL.h"
#import "AppleHelper.h"

@implementation RAREaPlatformImpl

static JavaLangThreadLocal * RAREaPlatformImpl_perThreadScanner_;

+ (JavaLangThreadLocal *)perThreadScanner {
  return RAREaPlatformImpl_perThreadScanner_;
}

+ (void)setPerThreadScanner:(JavaLangThreadLocal *)perThreadScanner {
  RAREaPlatformImpl_perThreadScanner_ = perThreadScanner;
}

- (id)initWithRAREaAppContextImpl:(RAREaAppContextImpl *)context {
  if (self = [super init]) {
    platformName_ = @"Apple";
    appContext_ = context;
    [self getPlatformInfo];
    [RAREUTObjectCache setReferenceCreatorWithRAREUTObjectCache_iCacheReferenceCreator:[[RAREaPlatformImpl_$2 alloc] init]];
    [RAREUTSNumber setMaxDigitsWithInt:10];
  }
  return self;
}

- (BOOL)browseURLWithJavaNetURL:(JavaNetURL *)url {
  return [((RAREaAppContextImpl *) nil_chk(appContext_)) browseURLWithJavaNetURL:url];
}

- (BOOL)canGenerateByteCode {
  return NO;
}

- (id)createChartHandler {
  return [RAREPlatform createObjectWithNSString:@"com.appnativa.rare.ui.chart.coreplot.ChartHandler"];
}

- (BOOL)createDirectoryWithJavaIoFile:(JavaIoFile *)file {
  return [self createDirectoryWithNSString:[((JavaIoFile *) nil_chk(file)) getAbsolutePath]];
}

- (id<RAREiPlatformComponent>)createErrorComponentWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                                           withNSString:(NSString *)message {
  @throw [[JavaLangUnsupportedOperationException alloc] initWithNSString:@"Not supported yet."];
}

- (id<RAREiPlatformComponent>)createPlatformComponentWithId:(id)nativeComponent {
  id<RAREiPlatformComponent> pc = [RAREComponent fromViewWithRAREView:(RAREView *) check_class_cast(nativeComponent, [RAREView class])];
  if (pc == nil) {
    pc = [[RAREComponent alloc] initWithRAREView:(RAREView *) check_class_cast(nativeComponent, [RAREView class])];
  }
  return pc;
}

- (id<RAREiTimer>)createTimerWithNSString:(NSString *)name {
  return [[RARETimer alloc] initWithNSString:name];
}

+ (void)defaultFontUpdatedWithRAREUIFont:(RAREUIFont *)font {
}

- (BOOL)deleteDirectoryWithJavaIoFile:(JavaIoFile *)path {
  return (path == nil) ? YES : [RAREaPlatformImpl deleteDirectoryWithNSString:[path getAbsolutePath]];
}

- (id<RAREiPlatformComponent>)findPlatformComponentWithId:(id)o {
  if ([o conformsToProtocol: @protocol(RAREiPlatformComponent)]) {
    return (id<RAREiPlatformComponent>) check_protocol_cast(o, @protocol(RAREiPlatformComponent));
  }
  if (!([o isKindOfClass:[RAREView class]])) {
    return nil;
  }
  while ([o isKindOfClass:[RAREView class]]) {
    id<RAREiPlatformComponent> c = [((RAREView *) check_class_cast(o, [RAREView class])) getComponent];
    if (c != nil) {
      return c;
    }
    o = [((RAREView *) check_class_cast(o, [RAREView class])) getParent];
  }
  return nil;
}

- (id<RAREiWidget>)findWidgetForComponentWithId:(id)o {
  if ([o isKindOfClass:[RAREComponent class]]) {
    RAREComponent *c = (RAREComponent *) check_class_cast(o, [RAREComponent class]);
    if ([((RAREComponent *) nil_chk(c)) getWidget] != nil) {
      return [c getWidget];
    }
    o = [((RAREView *) nil_chk([c getView])) getParent];
  }
  if (!([o isKindOfClass:[RAREView class]])) {
    return nil;
  }
  while ([o isKindOfClass:[RAREView class]]) {
    id<RAREiPlatformComponent> c = [((RAREView *) check_class_cast(o, [RAREView class])) getComponent];
    if (([(id) c isKindOfClass:[RAREComponent class]]) && [((RAREComponent *) check_class_cast(c, [RAREComponent class])) getWidget] != nil) {
      return [((RAREComponent *) check_class_cast(c, [RAREComponent class])) getWidget];
    }
    o = [((RAREView *) check_class_cast(o, [RAREView class])) getParent];
  }
  return nil;
}

- (void)invokeLaterWithJavaLangRunnable:(id<JavaLangRunnable>)r {
  [AppleHelper invokeLater: r];
}

- (void)invokeLaterWithJavaLangRunnable:(id<JavaLangRunnable>)r
                                withInt:(int)delay {
  [AppleHelper invokeLater: r delay: delay];
}

- (IOSClass *)loadClassWithNSString:(NSString *)name {
  return [RAREaPlatformHelper loadClassWithNSString:name];
}

- (BOOL)mailToWithNSString:(NSString *)uri {
  NSURL *url = [[NSURL alloc] initWithString: uri];
  
  #if TARGET_OS_IPHONE
  [[UIApplication sharedApplication] openURL:url];
  #else
  [[NSWorkspace sharedWorkspace] openURL: url];
  #endif
  return YES;
}

- (BOOL)mailToWithNSString:(NSString *)address
              withNSString:(NSString *)subject
              withNSString:(NSString *)body {
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
}

- (void)registerWithWidgetWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
                                     withRAREiWidget:(id<RAREiWidget>)context {
  [((id<RAREiPlatformComponent>) nil_chk(component)) setWidgetWithRAREiWidget:context];
}

- (void)unregisterWithWidgetWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  [((id<RAREiPlatformComponent>) nil_chk(component)) setWidgetWithRAREiWidget:nil];
}

- (int)getAndroidVersion {
  return 0;
}

- (id<RAREiPlatformAppContext>)getAppContext {
  return appContext_;
}

- (int)getAppInstanceCount {
  return 1;
}

- (JavaIoFile *)getCacheDir {
  if (cacheDir_ == nil) {
    JavaIoFile *f = [[JavaIoFile alloc] initWithNSString:[self getCacheDirName]];
    if ([self createDirectoryWithJavaIoFile:f]) {
      cacheDir_ = f;
    }
  }
  return cacheDir_;
}

- (id<RAREiDataConverter>)getDataConverterWithIOSClass:(IOSClass *)cls {
  return [RARERare getDataConverterWithIOSClass:cls];
}

- (IOSClass *)getDataConverterClassWithNSString:(NSString *)name {
  return [RARERare getDataConverterClassWithNSString:name];
}

- (id<RAREiFunctionHandler>)getFunctionHandler {
  return [((RAREaAppContextImpl *) nil_chk(appContext_)) getFunctionHandler];
}

- (NSString *)getOsType {
  return platformIsMAC_ ? @"os x" : @"ios";
}

- (float)getOsVersion {
  return platformVersion_;
}

- (id<RAREiPlatformComponent>)getPlatformComponentWithId:(id)source {
  if ([source conformsToProtocol: @protocol(RAREiPlatformComponent)]) {
    return (id<RAREiPlatformComponent>) check_protocol_cast(source, @protocol(RAREiPlatformComponent));
  }
  if ([source isKindOfClass:[RAREView class]]) {
    return [((RAREView *) check_class_cast(source, [RAREView class])) getComponent];
  }
  return nil;
}

- (NSString *)getPlatformType {
  return platformName_;
}

- (double)getPlatformVersion {
  return platformVersion_;
}

- (RAREWindowViewer *)getWindowViewerForComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  return [RAREPlatform getWindowViewer];
}

- (BOOL)isAndroid {
  return NO;
}

- (BOOL)isDebugEnabled {
  return [RAREaRare isDebugEnabled];
}

- (BOOL)isDebuggingEnabled {
  return NO;
}

- (BOOL)isDescendingFromWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)container {
  if ((c == nil) || ([c getView] == nil) || (container == nil) || ([container getView] == nil)) {
    return NO;
  }
  return [((RAREView *) nil_chk([((id<RAREiPlatformComponent>) nil_chk(c)) getView])) isDescendantOfWithRAREView:[((id<RAREiPlatformComponent>) nil_chk(container)) getView]];
}

- (BOOL)isHTMLSupportedInLabels {
  return YES;
}

- (BOOL)isIOS {
  return platformIsIOS_;
}

- (BOOL)isIPad {
  return platformIsIPad_;
}

- (BOOL)isIPhone {
  return platformIsIOS_ && !platformIsIPad_;
}

- (BOOL)isInitialized {
  return YES;
}

- (BOOL)isJava {
  return NO;
}

- (BOOL)isJavaFX {
  return NO;
}

- (BOOL)isLinux {
  return NO;
}

- (BOOL)isMac {
  return platformIsMAC_;
}

- (BOOL)isShuttingDown {
  return [((RAREaAppContextImpl *) nil_chk(appContext_)) isShuttingDown];
}

- (BOOL)isSwing {
  return NO;
}

- (BOOL)isTouchDevice {
  return platformIsIOS_;
}

- (BOOL)isTouchableDevice {
  return platformIsIOS_;
}

- (BOOL)isUIThread {
  return [NSThread isMainThread];
}

- (BOOL)isUnix {
  return NO;
}

- (BOOL)isWindows {
  return NO;
}

- (BOOL)createDirectoryWithNSString:(NSString *)path {
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
  
}

+ (BOOL)deleteDirectoryWithNSString:(NSString *)dir {
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
}

- (NSString *)getCacheDirName {
  NSArray  *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
  NSString *documentsDirectory = [paths objectAtIndex:0];
  return [NSString stringWithFormat:@"%@/RARECache", documentsDirectory];
}

- (void)getPlatformInfo {
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
}

+ (void)initialize {
  if (self == [RAREaPlatformImpl class]) {
    RAREaPlatformImpl_perThreadScanner_ = [[RAREaPlatformImpl_$1 alloc] init];
  }
}

- (void)copyAllFieldsTo:(RAREaPlatformImpl *)other {
  [super copyAllFieldsTo:other];
  other->appContext_ = appContext_;
  other->cacheDir_ = cacheDir_;
  other->menubarHeight_ = menubarHeight_;
  other->platformIsIOS_ = platformIsIOS_;
  other->platformIsIPad_ = platformIsIPad_;
  other->platformIsMAC_ = platformIsMAC_;
  other->platformName_ = platformName_;
  other->platformVersion_ = platformVersion_;
  other->platformVersionString_ = platformVersionString_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithRAREaAppContextImpl:", NULL, NULL, 0x4, NULL },
    { "browseURLWithJavaNetURL:", NULL, "Z", 0x1, NULL },
    { "canGenerateByteCode", NULL, "Z", 0x1, NULL },
    { "createChartHandler", NULL, "LNSObject", 0x1, NULL },
    { "createDirectoryWithJavaIoFile:", NULL, "Z", 0x1, NULL },
    { "createErrorComponentWithRAREiPlatformIcon:withNSString:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "createPlatformComponentWithId:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "createTimerWithNSString:", NULL, "LRAREiTimer", 0x1, NULL },
    { "deleteDirectoryWithJavaIoFile:", NULL, "Z", 0x1, NULL },
    { "findPlatformComponentWithId:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "findWidgetForComponentWithId:", NULL, "LRAREiWidget", 0x1, NULL },
    { "invokeLaterWithJavaLangRunnable:", NULL, "V", 0x101, NULL },
    { "invokeLaterWithJavaLangRunnable:withInt:", NULL, "V", 0x101, NULL },
    { "loadClassWithNSString:", NULL, "LIOSClass", 0x1, "JavaLangClassNotFoundException" },
    { "mailToWithNSString:", NULL, "Z", 0x101, NULL },
    { "mailToWithNSString:withNSString:withNSString:", NULL, "Z", 0x101, NULL },
    { "getAppContext", NULL, "LRAREiPlatformAppContext", 0x1, NULL },
    { "getCacheDir", NULL, "LJavaIoFile", 0x1, NULL },
    { "getDataConverterWithIOSClass:", NULL, "LRAREiDataConverter", 0x1, NULL },
    { "getDataConverterClassWithNSString:", NULL, "LIOSClass", 0x1, "JavaLangClassNotFoundException" },
    { "getFunctionHandler", NULL, "LRAREiFunctionHandler", 0x1, NULL },
    { "getOsType", NULL, "LNSString", 0x1, NULL },
    { "getPlatformComponentWithId:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getPlatformType", NULL, "LNSString", 0x1, NULL },
    { "getWindowViewerForComponentWithRAREiPlatformComponent:", NULL, "LRAREWindowViewer", 0x1, NULL },
    { "isAndroid", NULL, "Z", 0x1, NULL },
    { "isDebugEnabled", NULL, "Z", 0x1, NULL },
    { "isDebuggingEnabled", NULL, "Z", 0x1, NULL },
    { "isDescendingFromWithRAREiPlatformComponent:withRAREiPlatformComponent:", NULL, "Z", 0x1, NULL },
    { "isHTMLSupportedInLabels", NULL, "Z", 0x1, NULL },
    { "isIOS", NULL, "Z", 0x1, NULL },
    { "isIPad", NULL, "Z", 0x1, NULL },
    { "isIPhone", NULL, "Z", 0x1, NULL },
    { "isInitialized", NULL, "Z", 0x1, NULL },
    { "isJava", NULL, "Z", 0x1, NULL },
    { "isJavaFX", NULL, "Z", 0x1, NULL },
    { "isLinux", NULL, "Z", 0x1, NULL },
    { "isMac", NULL, "Z", 0x1, NULL },
    { "isShuttingDown", NULL, "Z", 0x1, NULL },
    { "isSwing", NULL, "Z", 0x1, NULL },
    { "isTouchDevice", NULL, "Z", 0x1, NULL },
    { "isTouchableDevice", NULL, "Z", 0x1, NULL },
    { "isUIThread", NULL, "Z", 0x101, NULL },
    { "isUnix", NULL, "Z", 0x1, NULL },
    { "isWindows", NULL, "Z", 0x1, NULL },
    { "createDirectoryWithNSString:", NULL, "Z", 0x102, NULL },
    { "deleteDirectoryWithNSString:", NULL, "Z", 0x10a, NULL },
    { "getCacheDirName", NULL, "LNSString", 0x102, NULL },
    { "getPlatformInfo", NULL, "V", 0x102, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "perThreadScanner_", NULL, 0xa, "LJavaLangThreadLocal" },
    { "cacheDir_", NULL, 0x0, "LJavaIoFile" },
    { "appContext_", NULL, 0x12, "LRAREaAppContextImpl" },
  };
  static J2ObjcClassInfo _RAREaPlatformImpl = { "aPlatformImpl", "com.appnativa.rare.platform", NULL, 0x401, 49, methods, 3, fields, 0, NULL};
  return &_RAREaPlatformImpl;
}

@end
@implementation RAREaPlatformImpl_$1

- (RAREUTCharScanner *)initialValue {
  @synchronized(self) {
    {
      return [[RAREUTCharScanner alloc] init];
    }
  }
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initialValue", NULL, "LRAREUTCharScanner", 0x24, NULL },
  };
  static const char *superclass_type_args[] = {"LRAREUTCharScanner"};
  static J2ObjcClassInfo _RAREaPlatformImpl_$1 = { "$1", "com.appnativa.rare.platform", "aPlatformImpl", 0x8000, 1, methods, 0, NULL, 1, superclass_type_args};
  return &_RAREaPlatformImpl_$1;
}

@end
@implementation RAREaPlatformImpl_$2

- (id<RAREUTObjectCache_iCacheReference>)createCacheReferenceWithId:(id)key
                                                             withId:(id)value {
  return [[RAREAppleCacheReference alloc] initWithId:key withId:value];
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createCacheReferenceWithId:withId:", NULL, "LRAREUTObjectCache_iCacheReference", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREaPlatformImpl_$2 = { "$2", "com.appnativa.rare.platform", "aPlatformImpl", 0x8000, 1, methods, 0, NULL, 0, NULL};
  return &_RAREaPlatformImpl_$2;
}

@end
