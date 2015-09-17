//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/aPlatformHelper.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREaPlatformHelper_H_
#define _RAREaPlatformHelper_H_

@class IOSClass;
@class IOSObjectArray;
@class JavaIoFile;
@class JavaLangReflectConstructor;
@class JavaLangThrowable;
@class JavaNetURL;
@class JavaNetURLConnection;
@class JavaUtilEventObject;
@class JavaUtilHashMap;
@class RAREAnimationComponent;
@class RAREBeanWidget;
@class RARECancelableOperation;
@class RAREMouseEvent;
@class RAREPopupListBoxHandler;
@class RAREPopupWindow;
@class RARERenderTypeEnum;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RARESPOTBean;
@class RARESPOTMenuBar;
@class RARESPOTScrollPane;
@class RAREUIAction;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIImage;
@class RAREUIImageIcon;
@class RAREUIMenuItem;
@class RAREUIPoint;
@class RAREUIRectangle;
@class RAREUISound;
@class RAREUTOrderedProperties;
@class RAREView;
@class RAREWindow;
@class RAREaFocusedAction;
@class RAREaPlatformHelper_PackageHelper;
@class RAREaViewer;
@class RAREaWidget;
@protocol JavaLangCharSequence;
@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentCallable;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREUTiPreferences;
@protocol RAREiActionComponent;
@protocol RAREiCancelableFuture;
@protocol RAREiContainer;
@protocol RAREiParentComponent;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformListDataModel;
@protocol RAREiPlatformMenuBar;
@protocol RAREiPlatformPath;
@protocol RAREiPlatformRenderingComponent;
@protocol RAREiPlatformShape;
@protocol RAREiPopup;
@protocol RAREiTarget;
@protocol RAREiWidget;
@protocol RAREiWorkerTask;

#import "JreEmulation.h"
#include "com/appnativa/rare/iWeakReference.h"
#include "com/appnativa/util/iPackageHelper.h"

@interface RAREaPlatformHelper : NSObject {
}

+ (id)imagesQueue;
+ (void)setImagesQueue:(id)imagesQueue;
+ (RAREaPlatformHelper_PackageHelper *)packageHelper;
+ (void)setPackageHelper:(RAREaPlatformHelper_PackageHelper *)packageHelper;
+ (id)tasksQueue;
+ (void)setTasksQueue:(id)tasksQueue;
+ (BOOL)scriptingMode;
+ (BOOL *)scriptingModeRef;
+ (BOOL)areImagesUpsideDown;
+ (RAREView *)createGlassViewWithBoolean:(BOOL)overlayContainer;
- (void)addPackageMappingWithNSString:(NSString *)packageName
                         withNSString:(NSString *)classPrefix;
+ (id<JavaLangCharSequence>)checkForHTMLWithNSString:(NSString *)text
                                      withRAREUIFont:(RAREUIFont *)font;
+ (void)clearSessionCookies;
+ (void)closeWindowWithRAREWindow:(RAREWindow *)window;
+ (id<RAREiPlatformComponent>)componentForEventWithJavaUtilEventObject:(JavaUtilEventObject *)uiEvent;
+ (void)configureDraggingWithRAREiWidget:(id<RAREiWidget>)widget
              withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
+ (void)configureDropTrackingWithRAREiWidget:(id<RAREiWidget>)widget
                  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                     withInt:(int)dropMode;
+ (void)configureKeystrokesWithRAREiWidget:(id<RAREiWidget>)w
                withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                              withNSString:(NSString *)set;
+ (RAREAnimationComponent *)createAnimationComponentWithRAREiWidget:(id<RAREiWidget>)context;
+ (id)createBeanWithRAREBeanWidget:(RAREBeanWidget *)w
                  withRARESPOTBean:(RARESPOTBean *)cfg;
+ (id<RAREiPlatformComponent>)createBorderPanelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                                  withRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
+ (RAREWindow *)createDialogWithRAREWindow:(RAREWindow *)parent
                               withBoolean:(BOOL)transparent
                               withBoolean:(BOOL)decorated
                               withBoolean:(BOOL)modal;
+ (id<RAREiPlatformIcon>)createDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
+ (id<RAREiPlatformIcon>)createDisabledIconIfNeededWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
+ (RAREUIImage *)createDisabledImageWithRAREUIImage:(RAREUIImage *)image;
+ (RAREUIImageIcon *)createIconWithJavaNetURL:(JavaNetURL *)url
                                 withNSString:(NSString *)description_
                                  withBoolean:(BOOL)defer
                                    withFloat:(float)density;
+ (RAREUIImage *)createImageWithJavaNetURL:(JavaNetURL *)url
                               withBoolean:(BOOL)defer
                                 withFloat:(float)density;
+ (id<RAREiActionComponent>)createLabelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)context;
+ (id<RAREiPlatformRenderingComponent>)createLabelRendererWithRAREiWidget:(id<RAREiWidget>)context;
+ (id<RAREiPlatformListDataModel>)createListDataModelWithRAREiWidget:(id<RAREiWidget>)context
                                                    withJavaUtilList:(id<JavaUtilList>)items;
+ (id<RAREiPlatformMenuBar>)createMenuBarWithRAREiContainer:(id<RAREiContainer>)viewer
                                        withRARESPOTMenuBar:(RARESPOTMenuBar *)mb;
+ (RAREUIMenuItem *)createMenuItemWithRAREUIAction:(RAREUIAction *)a
                                       withBoolean:(BOOL)topLevelMenu;
+ (id<RAREiPlatformPath>)createPath;
+ (id<RAREiPopup>)createPopup;
+ (RAREPopupWindow *)createPopupWithBoolean:(BOOL)modal
                                withBoolean:(BOOL)transparent;
+ (RAREPopupListBoxHandler *)createPopupListBoxHandlerWithRAREiWidget:(id<RAREiWidget>)w
                                       withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)model
                                                          withBoolean:(BOOL)forMenu;
+ (id<RAREiPlatformRenderingComponent>)createRendererWithNSString:(NSString *)s
                                                  withRAREiWidget:(id<RAREiWidget>)context;
+ (id<RAREiPlatformComponent>)createSeparatorComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)context;
+ (id<RAREiPlatformShape>)createShapeWithRAREUIRectangle:(RAREUIRectangle *)r;
+ (id<RAREiPlatformShape>)createShapeWithFloat:(float)x
                                     withFloat:(float)y
                                     withFloat:(float)width
                                     withFloat:(float)height;
+ (id<RAREiPlatformComponent>)createSpacerComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)context;
+ (id<RAREiPlatformIcon>)createStateListIconWithNSString:(NSString *)icon
                                         withRAREiWidget:(id<RAREiWidget>)context;
+ (id<RAREiParentComponent>)createTargetContainerWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app;
+ (id<RAREiWeakReference>)createWeakReferenceWithId:(id)value;
+ (RAREaWidget *)createWidgetWithIOSClass:(IOSClass *)cls
                       withRAREiContainer:(id<RAREiContainer>)parent;
+ (void)defaultFontUpdatedWithRAREUIFont:(RAREUIFont *)font;
+ (id<RAREiCancelableFuture>)executeBackgroundTaskWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable
                                                                     withBoolean:(BOOL)shuttingDown;
+ (id<RAREiCancelableFuture>)executeBackgroundTaskWithJavaLangRunnable:(id<JavaLangRunnable>)runnable
                                                           withBoolean:(BOOL)shuttingDown;
+ (id<RAREiCancelableFuture>)executeSwingWorkerTaskWithRAREiWorkerTask:(id<RAREiWorkerTask>)task
                                                           withBoolean:(BOOL)shuttingDown;
+ (JavaNetURL *)fileToURLWithJavaIoFile:(JavaIoFile *)f;
+ (JavaNetURL *)fileToURLWithNSString:(NSString *)filename;
+ (NSString *)formatWithNSString:(NSString *)spec
               withNSObjectArray:(IOSObjectArray *)values;
+ (void)handleCookieExtractionWithJavaNetURLConnection:(JavaNetURLConnection *)conn;
+ (void)handleCookieExtractionWithNSString:(NSString *)url
                  withJavaNetURLConnection:(JavaNetURLConnection *)conn;
+ (NSString *)handleCookieInjectionWithJavaNetURLConnection:(JavaNetURLConnection *)conn;
+ (NSString *)handleCookieInjectionWithNSString:(NSString *)url
                       withJavaNetURLConnection:(JavaNetURLConnection *)conn;
+ (NSString *)hmacSHAWithNSString:(NSString *)val
                     withNSString:(NSString *)key
                      withBoolean:(BOOL)base64;
+ (void)initializeThreadingServiceWithInt:(int)max
                                  withInt:(int)imageMax OBJC_METHOD_FAMILY_NONE;
+ (void)layoutWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withFloat:(float)x
                               withFloat:(float)y
                               withFloat:(float)w
                               withFloat:(float)h;
+ (void)layoutFrameContainerWithRARERenderTypeEnum:(RARERenderTypeEnum *)renderType
                          withRAREiParentComponent:(id<RAREiParentComponent>)target
                        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)view
                                         withFloat:(float)width
                                         withFloat:(float)height;
+ (IOSClass *)loadClassWithNSString:(NSString *)name;
+ (void)loadIconWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                        withRAREUIImageIcon:(RAREUIImageIcon *)ic;
+ (id<RAREiPlatformComponent>)makeResizableWithRAREiWidget:(id<RAREiWidget>)w
                                               withBoolean:(BOOL)createCorner
                                     withRAREiPlatformIcon:(id<RAREiPlatformIcon>)cornerIcon;
+ (id<RAREiPlatformComponent>)makeScrollPaneWithRAREaViewer:(RAREaViewer *)context
                                     withRARESPOTScrollPane:(RARESPOTScrollPane *)cfg
                                 withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
+ (void)performHapticFeedbackWithId:(id)view;
+ (id<RAREiPlatformComponent>)resolveBeanComponentWithId:(id)bean;
+ (RAREUIImage *)scaleImageWithRAREUIImage:(RAREUIImage *)image
                                   withInt:(int)width
                                   withInt:(int)height;
+ (void)stopBackgroundThreads;
+ (BOOL)supportsSyncUpdateWithScreenRefreshWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (void)syncUpdateWithScreenRefreshWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                                  withBoolean:(BOOL)sync;
+ (JavaLangThrowable *)unwrapJavaScriptExceptionWithJavaLangThrowable:(JavaLangThrowable *)e;
+ (BOOL)useInFormLayoutMeasureHeightsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
+ (void)setAutoRepeatsWithRAREiActionComponent:(id<RAREiActionComponent>)comp
                                       withInt:(int)interval;
+ (void)setCookieWithNSString:(NSString *)cookieHeader
               withJavaNetURL:(JavaNetURL *)url
                 withNSString:(NSString *)value;
+ (void)setCookieValueWithJavaNetURL:(JavaNetURL *)url
                        withNSString:(NSString *)value;
+ (void)setLabelForComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                                withId:(id)l;
+ (void)setShortcutWithRAREUIMenuItem:(RAREUIMenuItem *)mi
                         withNSString:(NSString *)keystroke;
+ (void)setStrictScriptingModeWithBoolean:(BOOL)strict;
+ (void)setTargetRenderTypeWithRAREiTarget:(id<RAREiTarget>)target
                    withRARERenderTypeEnum:(RARERenderTypeEnum *)rt;
+ (void)setTextWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                             withNSString:(NSString *)string;
+ (void)setTextAlignmentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
    withRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)ha
      withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)va;
+ (void)setUseDarkStatusBarTextWithBoolean:(BOOL)dark;
+ (id)getBackgroundQueue;
+ (RAREUIColor *)getColorStateListWithJavaUtilMap:(id<JavaUtilMap>)map;
+ (JavaLangReflectConstructor *)getConstructorWithIOSClass:(IOSClass *)cls
                                         withIOSClassArray:(IOSObjectArray *)params;
+ (RAREaFocusedAction *)getCopyAction;
+ (RAREaFocusedAction *)getCutAction;
+ (RAREaFocusedAction *)getDeleteAction;
+ (RAREUIColor *)getDrawableStateListWithJavaUtilMap:(id<JavaUtilMap>)map;
+ (id<JavaUtilList>)getDropedItemsWithRAREaWidget:(RAREaWidget *)dest
                                  withRAREiWidget:(id<RAREiWidget>)source
                                      withBoolean:(BOOL)copy_
                                      withBoolean:(BOOL)text;
+ (JavaIoFile *)getFileWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)context
                                     withNSString:(NSString *)title
                                      withBoolean:(BOOL)open
                                      withBoolean:(BOOL)dironly
                                   withJavaIoFile:(JavaIoFile *)dir
                                     withNSString:(NSString *)extfilters;
+ (NSString *)getIOSClassNameWithNSString:(NSString *)javaFullClassName;
+ (id)getImagesBackgroundQueue;
+ (RAREUIFont *)getMonospacedFontWithInt:(int)size;
+ (NSString *)getPackageNameWithIOSClass:(IOSClass *)cls;
+ (RAREaFocusedAction *)getPasteAction;
+ (id<RAREUTiPreferences>)getPreferencesWithNSString:(NSString *)appKey;
+ (RAREaFocusedAction *)getRedoAction;
+ (id)getResourceAsDrawableWithNSString:(NSString *)name;
+ (RAREUIColor *)getResourceColorWithNSString:(NSString *)color;
+ (id)getResourceIdWithNSString:(NSString *)name;
+ (float)getScaledImageDensity;
+ (RAREaFocusedAction *)getSelectAllAction;
+ (RAREUIMenuItem *)getSeparatorMenuItem;
+ (BOOL)getStrictScriptingMode;
+ (RAREaFocusedAction *)getUndoAction;
+ (float)getUnscaledImageDensity;
+ (RAREUIDimension *)getWindowDecorationSize;
+ (BOOL)isDarkTheme;
+ (BOOL)isMouseClickWithRAREUIPoint:(RAREUIPoint *)startPoint
                           withLong:(long long int)startTime
                 withRAREMouseEvent:(RAREMouseEvent *)releaseEvent;
+ (BOOL)isOptimizationEnabled;
+ (void)setOptimizationEnabledWithBoolean:(BOOL)enabled;
+ (void)enqueueWithId:(id)queue
withRARECancelableOperation:(RARECancelableOperation *)cop;
+ (void)stopSoundWithRAREUISound:(RAREUISound *)uiSound;
+ (void)disposeOfSoundWithRAREUISound:(RAREUISound *)uiSound;
+ (void)playSoundWithRAREUISound:(RAREUISound *)uiSound;
+ (void)pauseSoundWithRAREUISound:(RAREUISound *)uiSound;
+ (void)resumeSoundWithRAREUISound:(RAREUISound *)uiSound;
+ (id)setVolumeWithRAREUISound:(RAREUISound *)uiSound
                       withInt:(int)percent;
- (id)init;
@end

typedef RAREaPlatformHelper ComAppnativaRarePlatformAPlatformHelper;

@interface RAREaPlatformHelper_PackageHelper : NSObject < RAREUTiPackageHelper > {
 @public
  NSString *rareSPOTPrefix_;
}

+ (RAREUTOrderedProperties *)packageMap;
+ (void)setPackageMap:(RAREUTOrderedProperties *)packageMap;
+ (JavaUtilHashMap *)reversePackageMap;
+ (void)setReversePackageMap:(JavaUtilHashMap *)reversePackageMap;
- (id)init;
- (void)addPackageMappingWithNSString:(NSString *)packageName
                         withNSString:(NSString *)classPrefix;
- (IOSClass *)loadClassWithNSString:(NSString *)name;
- (IOSClass *)getFieldClassWithId:(id)field;
- (NSString *)getIOSClassNameWithNSString:(NSString *)name;
- (NSString *)getPackageNameWithIOSClass:(IOSClass *)cls;
- (NSString *)createIOSClassNameWithNSString:(NSString *)name;
- (NSString *)findClassNamePrefixWithNSString:(NSString *)name;
- (IOSClass *)loadClassExWithNSString:(NSString *)name;
- (void)copyAllFieldsTo:(RAREaPlatformHelper_PackageHelper *)other;
@end

J2OBJC_FIELD_SETTER(RAREaPlatformHelper_PackageHelper, rareSPOTPrefix_, NSString *)

@interface RAREaPlatformHelper_WeakReferenceEx : NSObject < RAREiWeakReference > {
 @public
  __weak id value_;
}

- (id)initWithId:(id)value;
- (void)clear;
- (id)get;
- (void)copyAllFieldsTo:(RAREaPlatformHelper_WeakReferenceEx *)other;
@end

#endif // _RAREaPlatformHelper_H_
