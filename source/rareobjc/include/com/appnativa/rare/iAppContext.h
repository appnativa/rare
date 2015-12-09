//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iAppContext.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiAppContext_H_
#define _RAREiAppContext_H_

@class IOSClass;
@class JavaIoInputStream;
@class JavaIoReader;
@class JavaLangBoolean;
@class JavaNetURL;
@class JavaNetURLConnection;
@class RAREActionLink;
@class RAREDateContext;
@class RAREPaintBucket;
@class RAREUIAction;
@class RAREUIImage;
@class RAREUIImageIcon;
@class RAREUIProperties;
@class RAREUISound;
@class RAREUTIdentityArrayList;
@class RAREWindowViewer;
@class RAREaFocusedAction;
@protocol JavaLangRunnable;
@protocol JavaUtilMap;
@protocol RAREUTiURLResolver;
@protocol RAREiApplicationListener;
@protocol RAREiAsyncLoadStatusHandler;
@protocol RAREiConfigurationListener;
@protocol RAREiConnectionHandler;
@protocol RAREiContainer;
@protocol RAREiDataCollection;
@protocol RAREiDataCollectionHandler;
@protocol RAREiDataConverter;
@protocol RAREiExceptionHandler;
@protocol RAREiFunctionCallback;
@protocol RAREiMultipartMimeHandler;
@protocol RAREiPlatformAnimator;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformWindowManager;
@protocol RAREiPrintHandler;
@protocol RAREiResourceFinder;
@protocol RAREiScriptHandler;
@protocol RAREiSpeechEnabler;
@protocol RAREiURLConnection;
@protocol RAREiViewer;
@protocol RAREiWidget;
@protocol RAREiWidgetCustomizer;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/iExecutionHandler.h"

@protocol RAREiAppContext < RAREiExecutionHandler, NSObject, JavaObject >
- (BOOL)areAllLabelsDraggable;
- (BOOL)areAllTextFieldsDroppable;
- (BOOL)okForOSWithISPOTElement:(id<iSPOTElement>)e;
- (BOOL)okForOSWithNSString:(NSString *)value;
- (id<RAREiPlatformAnimator>)getResourceAsAnimatorWithNSString:(NSString *)animator;
- (NSString *)rewriteURLWithRAREiWidget:(id<RAREiWidget>)widget
                         withJavaNetURL:(JavaNetURL *)url
                                 withId:(id)code;
- (BOOL)isPlatformColorTheme;
- (BOOL)isDefaultBackgroundDark;
- (void)addApplicationListenerWithRAREiApplicationListener:(id<RAREiApplicationListener>)listener;
- (void)addConfigurationListenerWithRAREiConfigurationListener:(id<RAREiConfigurationListener>)listener;
- (void)addURLPrefixMappingWithNSString:(NSString *)prefix
                           withNSString:(NSString *)mapping;
- (void)addJarURLWithJavaNetURL:(JavaNetURL *)url;
- (void)registerFocusedActionWithRAREaFocusedAction:(RAREaFocusedAction *)action;
- (void)addResourceStringWithNSString:(NSString *)name
                         withNSString:(NSString *)value;
- (BOOL)areAllWidgetsDraggable;
- (BOOL)areViewersLocalByDefault;
- (NSString *)getCustomPropertyPrefix;
- (BOOL)browseURLWithJavaNetURL:(JavaNetURL *)url;
- (void)clearData;
- (void)clearDataWithNSString:(NSString *)prefix;
- (void)clearStatusBar;
- (void)closePopupWindowsWithBoolean:(BOOL)all;
- (BOOL)isPopupWindowShowing;
- (BOOL)isDialogWindowShowing;
- (id<RAREiDataCollection>)createCollectionWithNSString:(NSString *)handler
                                           withNSString:(NSString *)name
                                     withRAREActionLink:(RAREActionLink *)link
                                        withJavaUtilMap:(id<JavaUtilMap>)attributes
                                            withBoolean:(BOOL)tabular
                              withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (JavaNetURL *)createURLWithRAREiWidget:(id<RAREiWidget>)context
                            withNSString:(NSString *)url;
- (JavaNetURL *)createURLWithJavaNetURL:(JavaNetURL *)context
                           withNSString:(NSString *)url;
- (void)dispose;
- (BOOL)isDisposed;
- (void)exit;
- (BOOL)ignoreFormatExceptions;
- (NSString *)loadScriptCodeWithRAREActionLink:(RAREActionLink *)link
                                   withBoolean:(BOOL)runOnce;
- (void)lockOrientationWithJavaLangBoolean:(JavaLangBoolean *)landscape;
- (id<RAREiURLConnection>)openConnectionWithJavaNetURL:(JavaNetURL *)url;
- (id<RAREiURLConnection>)openConnectionWithJavaNetURL:(JavaNetURL *)url
                                          withNSString:(NSString *)mimeType;
- (id<RAREiConnectionHandler>)getApplicationConnectionHandler;
- (void)setApplicationConnectionHandlerWithRAREiConnectionHandler:(id<RAREiConnectionHandler>)h;
- (id)putDataWithId:(id)key
             withId:(id)value;
- (void)putDataWithJavaUtilMap:(id<JavaUtilMap>)data
                   withBoolean:(BOOL)clearFirst;
- (void)registerCollectionHandlerWithNSString:(NSString *)name
               withRAREiDataCollectionHandler:(id<RAREiDataCollectionHandler>)ch;
- (void)registerWidgetClassWithNSString:(NSString *)type
                           withIOSClass:(IOSClass *)cls;
- (void)registerDataCollectionWithRAREiDataCollection:(id<RAREiDataCollection>)dc;
- (void)removeApplicationListenerWithRAREiApplicationListener:(id<RAREiApplicationListener>)listener;
- (void)removeConfigurationListenerWithRAREiConfigurationListener:(id<RAREiConfigurationListener>)listener;
- (id)removeDataWithId:(id)key;
- (void)resetRunOnceWithRAREActionLink:(RAREActionLink *)link;
- (void)unlockOrientation;
- (void)unregisterDataCollectionWithRAREiDataCollection:(id<RAREiDataCollection>)dc;
- (void)setAsyncLoadStatusHandlerWithRAREiAsyncLoadStatusHandler:(id<RAREiAsyncLoadStatusHandler>)handler;
- (void)setContextURLWithJavaNetURL:(JavaNetURL *)url;
- (void)setContextURLWithJavaNetURL:(JavaNetURL *)url
                       withNSString:(NSString *)appRoot;
- (void)setDefaultExceptionHandlerWithRAREiExceptionHandler:(id<RAREiExceptionHandler>)eh;
- (void)setMultipartMimeHandlerWithRAREiMultipartMimeHandler:(id<RAREiMultipartMimeHandler>)multipartMimeHandler;
- (void)setResourceFinderWithRAREiResourceFinder:(id<RAREiResourceFinder>)rf;
- (void)setSpeechEnablerWithRAREiSpeechEnabler:(id<RAREiSpeechEnabler>)speechEnabler;
- (void)setURLUserInfoWithJavaUtilMap:(id<JavaUtilMap>)mappings;
- (void)setURLUserInfoWithJavaNetURL:(JavaNetURL *)path
                        withNSString:(NSString *)info;
- (void)setWidgetCustomizerWithRAREiWidgetCustomizer:(id<RAREiWidgetCustomizer>)customizer;
- (RAREUIAction *)getActionWithNSString:(NSString *)name;
- (id<JavaUtilMap>)getActions;
- (RAREUTIdentityArrayList *)getActiveWindows;
- (NSString *)getApplicationName;
- (JavaNetURL *)getApplicationURL;
- (id<RAREiAsyncLoadStatusHandler>)getAsyncLoadStatusHandler;
- (RAREPaintBucket *)getAutoHilightPainter;
- (BOOL)getAutoLocalizeDateFormats;
- (BOOL)getAutoLocalizeNumberFormats;
- (JavaNetURL *)getCodeBase;
- (NSString *)getContentAsStringWithJavaNetURL:(JavaNetURL *)url;
- (JavaNetURL *)getContextURL;
- (id)getDataWithId:(id)key;
- (id<RAREiDataCollection>)getDataCollectionWithNSString:(NSString *)name;
- (id<RAREiDataConverter>)getDataConverterWithIOSClass:(IOSClass *)cls;
- (IOSClass *)getDataConverterClassWithNSString:(NSString *)name;
- (RAREDateContext *)getDefaultDateContext;
- (RAREDateContext *)getDefaultDateTimeContext;
- (id<RAREiExceptionHandler>)getDefaultExceptionHandler;
- (NSString *)getDefaultScrptingLanguage;
- (RAREDateContext *)getDefaultTimeContext;
- (id<RAREUTiURLResolver>)getDefaultURLResolver;
- (JavaNetURL *)getDocumentBase;
- (id<RAREiPlatformComponent>)getFocusOwner;
- (int)getIdentityInt;
- (JavaIoInputStream *)getInputStreamWithJavaNetURL:(JavaNetURL *)url;
- (int)getItemPaddingHeight;
- (RAREPaintBucket *)getListItemFocusPainter;
- (RAREPaintBucket *)getLostFocusSelectionPainter;
- (RAREPaintBucket *)getPressedPainter;
- (id<RAREiMultipartMimeHandler>)getMultipartMimeHandler;
- (NSString *)getName;
- (id<RAREiPlatformComponent>)getPermanentFocusOwner;
- (id<RAREiPrintHandler>)getPrintHandler;
- (JavaIoReader *)getReaderWithJavaNetURL:(JavaNetURL *)url;
- (JavaIoReader *)getReaderWithJavaNetURLConnection:(JavaNetURLConnection *)conn;
- (RAREUISound *)getSoundWithNSString:(NSString *)sound;
- (RAREUIImageIcon *)getResourceAsIconWithNSString:(NSString *)name;
- (RAREUIImageIcon *)addResourceIconWithNSString:(NSString *)name
                             withRAREUIImageIcon:(RAREUIImageIcon *)icon;
- (RAREUIImageIcon *)getResourceAsIconExWithNSString:(NSString *)name;
- (RAREUIImage *)getResourceAsImageWithNSString:(NSString *)name;
- (NSString *)getResourceAsStringWithNSString:(NSString *)name;
- (id<RAREiResourceFinder>)getResourceFinder;
- (id<JavaUtilMap>)getResourceIcons;
- (id<JavaUtilMap>)getResourceStrings;
- (JavaNetURL *)getResourceURLWithNSString:(NSString *)path;
- (id<RAREiContainer>)getRootViewer;
- (NSString *)getScriptTypeWithRAREActionLink:(RAREActionLink *)link;
- (id<RAREiScriptHandler>)getScriptingManager;
- (RAREPaintBucket *)getSelectionPainter;
- (id<RAREiSpeechEnabler>)getSpeechEnabler;
- (RAREUIProperties *)getUIDefaults;
- (JavaNetURL *)getURLWithNSString:(NSString *)url;
- (id<RAREiViewer>)getViewerWithNSString:(NSString *)name;
- (id<RAREiWidgetCustomizer>)getWidgetCustomizer;
- (RAREPaintBucket *)getWidgetFocusPainter;
- (id<RAREiWidget>)getWidgetFromPathWithNSString:(NSString *)path;
- (IOSClass *)getWidgetHandlerWithNSString:(NSString *)mimeType;
- (id<RAREiPlatformWindowManager>)getWindowManager;
- (RAREWindowViewer *)getWindowViewer;
- (RAREWindowViewer *)getMainWindowViewer;
- (BOOL)isChangeSelColorOnLostFocus;
- (BOOL)isDebugEnabled;
- (BOOL)isDynamicNameLookupEnabled;
- (BOOL)isInSandbox;
- (BOOL)isOrientationLocked;
- (BOOL)isShuttingDown;
- (BOOL)isOverlapAutoToolTips;
- (BOOL)isWebContext;
- (BOOL)isWebStart;
- (void)setLowMemoryWarningHandlerWithJavaLangRunnable:(id<JavaLangRunnable>)runnable;
@end

#define ComAppnativaRareIAppContext RAREiAppContext

#endif // _RAREiAppContext_H_
