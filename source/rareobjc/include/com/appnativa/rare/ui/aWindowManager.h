//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aWindowManager.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaWindowManager_H_
#define _RAREaWindowManager_H_

@class IOSObjectArray;
@class JavaIoIOException;
@class JavaLangException;
@class JavaLangThrowable;
@class JavaNetMalformedURLException;
@class JavaNetURL;
@class JavaUtilEventObject;
@class JavaUtilHashMap;
@class RAREActionLink;
@class RAREHTTPException;
@class RAREInvalidConfigurationException;
@class RARESPOTMainWindow;
@class RARESPOTStatusBar;
@class RARESPOTToolBar;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIPopupMenu;
@class RAREUIRectangle;
@class RAREWindowViewer;
@class RAREaWidgetListener;
@class SPOTSet;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiContainer;
@protocol RAREiFrame;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentFactory;
@protocol RAREiPlatformMenuBar;
@protocol RAREiPopup;
@protocol RAREiScriptHandler;
@protocol RAREiStatusBar;
@protocol RAREiTarget;
@protocol RAREiToolBar;
@protocol RAREiToolBarHolder;
@protocol RAREiViewer;
@protocol RAREiWidget;
@protocol RAREiWindow;
@protocol RAREiWindowListener;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/ViewerCreator.h"
#include "com/appnativa/rare/ui/iPlatformWindowManager.h"
#include "java/lang/Runnable.h"

@interface RAREaWindowManager : NSObject < RAREiPlatformWindowManager > {
 @public
  JavaUtilHashMap *activeViewers_;
  JavaUtilHashMap *theTargets_;
  id<RAREiPlatformAppContext> appContext_;
  id<RAREiPlatformComponentFactory> componentCreator_;
  JavaNetURL *contextURL_;
  RAREUIFont *defaultFont_;
  BOOL disposed_;
  id<RAREiWindow> mainFrame_;
  id<RAREiPlatformMenuBar> menuBar_;
  id<RAREiScriptHandler> scriptHandler_;
  RAREaWidgetListener *widgetListener_;
  id<RAREiTarget> workspaceTarget_;
  id<JavaLangRunnable> aboutDialogRunner_;
  id<JavaUtilList> windowIcons_;
}

+ (NSString *)BASE_VIEWER;
+ (void)setBASE_VIEWER:(NSString *)BASE_VIEWER;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app;
- (id<RAREiViewer>)activateViewerWithRAREActionLink:(RAREActionLink *)link;
- (id<RAREiViewer>)activateViewerWithRAREiWidget:(id<RAREiWidget>)context
                              withRAREActionLink:(RAREActionLink *)link;
- (id<RAREiViewer>)activateViewerWithRAREiWidget:(id<RAREiWidget>)context
                              withRARESPOTViewer:(RARESPOTViewer *)cfg
                                    withNSString:(NSString *)target;
- (void)addWindowListenerWithRAREiWindowListener:(id<RAREiWindowListener>)l;
- (void)asyncActivateViewerWithRAREActionLink:(RAREActionLink *)link;
- (void)asyncActivateViewerWithRAREiWidget:(id<RAREiWidget>)context
                        withRAREActionLink:(RAREActionLink *)link;
- (void)center;
- (void)changeTargetNameWithNSString:(NSString *)oldname
                        withNSString:(NSString *)newname
                     withRAREiTarget:(id<RAREiTarget>)target;
- (void)clearTargetWithNSString:(NSString *)target;
- (void)close;
- (void)configureStandardStuffWithRARESPOTMainWindow:(RARESPOTMainWindow *)cfg;
- (id<RAREiWindow>)createDialogWithRAREiWidget:(id<RAREiWidget>)context
                               withJavaUtilMap:(id<JavaUtilMap>)options;
- (id<RAREiWindow>)createDialogWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)title
                                   withBoolean:(BOOL)modal;
- (id<RAREiWindow>)createDialogWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)target
                                  withNSString:(NSString *)title
                                   withBoolean:(BOOL)modal;
- (RAREUIPopupMenu *)createPopupMenuWithRAREiWidget:(id<RAREiWidget>)context
                                     withJavaNetURL:(JavaNetURL *)url
                                        withBoolean:(BOOL)addTextItems;
- (RAREUIPopupMenu *)createPopupMenuWithRAREUIPopupMenu:(RAREUIPopupMenu *)menu
                                        withRAREiWidget:(id<RAREiWidget>)context
                                            withSPOTSet:(SPOTSet *)menus
                                            withBoolean:(BOOL)addTextItems;
- (id<RAREiStatusBar>)createStatusBarWithRARESPOTStatusBar:(RARESPOTStatusBar *)cfg;
- (id<RAREiToolBar>)createToolBarWithBoolean:(BOOL)horizontal;
- (id<RAREiToolBar>)createToolBarWithRARESPOTToolBar:(RARESPOTToolBar *)cfg;
+ (id<RAREiToolBarHolder>)createToolBarHolderWithRAREiContainer:(id<RAREiContainer>)viewer
                                                    withSPOTSet:(SPOTSet *)toolbars;
- (id<RAREiViewer>)createViewerWithRAREActionLink:(RAREActionLink *)link;
- (id<RAREiViewer>)createViewerWithRAREiWidget:(id<RAREiWidget>)context
                            withRAREActionLink:(RAREActionLink *)link;
- (id<RAREiViewer>)createViewerWithRAREiWidget:(id<RAREiWidget>)context
                            withRARESPOTViewer:(RARESPOTViewer *)cfg
                                withJavaNetURL:(JavaNetURL *)contextURL;
- (id<RAREiViewer>)createViewerWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)mimeType
                            withRARESPOTViewer:(RARESPOTViewer *)cfg
                                withJavaNetURL:(JavaNetURL *)contextURL;
- (RARESPOTWidget *)createWidgetConfigWithRAREActionLink:(RAREActionLink *)link;
- (id<RAREiWindow>)createWindowWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)target
                                  withNSString:(NSString *)title;
- (id<RAREiWindow>)createWindowOrDialogWithRAREiWidget:(id<RAREiWidget>)context
                                          withNSString:(NSString *)target
                                          withNSString:(NSString *)title
                                           withBoolean:(BOOL)modal
                                           withBoolean:(BOOL)dialog;
- (void)dispose;
- (void)disposeOfWindow;
- (void)handleExceptionWithRAREHTTPException:(RAREHTTPException *)e;
- (void)handleExceptionWithRAREInvalidConfigurationException:(RAREInvalidConfigurationException *)e;
- (void)handleExceptionWithJavaIoIOException:(JavaIoIOException *)e;
- (void)handleExceptionWithJavaNetMalformedURLException:(JavaNetMalformedURLException *)e;
- (void)handleExceptionWithJavaLangThrowable:(JavaLangThrowable *)e;
- (void)hideWindow;
- (void)moveByWithFloat:(float)x
              withFloat:(float)y;
- (void)moveToWithFloat:(float)x
              withFloat:(float)y;
- (RAREWindowViewer *)openViewerWindowWithRAREActionLink:(RAREActionLink *)link
                                                  withId:(id)viewerValue;
- (void)pack;
- (void)registerTargetWithNSString:(NSString *)name
                   withRAREiTarget:(id<RAREiTarget>)target;
- (void)registerViewerWithNSString:(NSString *)name
                   withRAREiViewer:(id<RAREiViewer>)viewer;
- (void)removeTargetWithNSString:(NSString *)target;
- (void)removeWindowListenerWithRAREiWindowListener:(id<RAREiWindowListener>)l;
- (void)resetWithBoolean:(BOOL)reloadViewers;
- (id<JavaLangRunnable>)runnableForActivateViewerWithRAREActionLink:(RAREActionLink *)link;
- (id<JavaLangRunnable>)runnableForActivateViewerWithRAREiWidget:(id<RAREiWidget>)context
                                              withRARESPOTViewer:(RARESPOTViewer *)cfg
                                                    withNSString:(NSString *)target;
- (void)showAboutDialog;
- (void)showWindow;
- (void)showWindowWithInt:(int)x
                  withInt:(int)y;
- (void)toBack;
- (void)toFront;
- (void)unRegisterTargetWithNSString:(NSString *)name;
- (void)unRegisterViewerWithNSString:(NSString *)name;
- (void)update;
- (void)setAboutDialogRunnerWithJavaLangRunnable:(id<JavaLangRunnable>)aboutDialogRunner;
- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height;
- (void)setCanCloseWithBoolean:(BOOL)can;
- (void)setContextURLWithJavaNetURL:(JavaNetURL *)url;
- (void)setDefaultFontWithRAREUIFont:(RAREUIFont *)font;
- (void)setLocationWithFloat:(float)x
                   withFloat:(float)y;
- (id<RAREiPlatformMenuBar>)setMenuBarWithRAREiPlatformMenuBar:(id<RAREiPlatformMenuBar>)mb;
- (float)setRelativeFontSizeWithFloat:(float)size;
- (void)setResizableWithBoolean:(BOOL)resizable;
- (void)setScriptHandlerWithRAREiScriptHandler:(id<RAREiScriptHandler>)scriptHandler;
- (void)setSizeWithFloat:(float)width
               withFloat:(float)height;
- (void)setStatusWithNSString:(NSString *)status;
- (id<RAREiStatusBar>)setStatusBarWithRAREiStatusBar:(id<RAREiStatusBar>)sb;
- (void)setTitleWithNSString:(NSString *)title;
- (id<RAREiToolBarHolder>)setToolBarHolderWithRAREiToolBarHolder:(id<RAREiToolBarHolder>)tbh;
- (id<RAREiViewer>)setViewerWithNSString:(NSString *)target
                         withRAREiWidget:(id<RAREiWidget>)context
                         withRAREiViewer:(id<RAREiViewer>)viewer
                         withJavaUtilMap:(id<JavaUtilMap>)options;
- (id<RAREiViewer>)setViewerExWithNSString:(NSString *)target
                           withRAREiWidget:(id<RAREiWidget>)context
                           withRAREiViewer:(id<RAREiViewer>)viewer;
- (void)setWindowIconsWithJavaUtilList:(id<JavaUtilList>)icons;
- (id<JavaLangRunnable>)getAboutDialogRunner;
- (id<RAREiPlatformAppContext>)getAppContext;
- (RAREUIRectangle *)getBounds;
- (id<RAREiPlatformComponent>)getComponent;
- (id<RAREiPlatformComponentFactory>)getComponentCreator;
- (RAREUIFont *)getDefaultFont;
- (RAREUIFont *)getDefaultMonospacedFont;
- (id<RAREiFrame>)getFrameWithNSString:(NSString *)name;
- (IOSObjectArray *)getFrames;
- (RAREUIDimension *)getInnerSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (int)getHeight;
- (id<RAREiWindow>)getMainWindow;
- (id<RAREiPlatformMenuBar>)getMenuBar;
- (id<RAREiContainer>)getRootViewer;
- (int)getScreenX;
- (int)getScreenY;
- (id<RAREiScriptHandler>)getScriptHandler;
- (RAREUIDimension *)getSize;
- (id<RAREiStatusBar>)getStatusBar;
- (id<RAREiTarget>)getTarget;
- (id<RAREiTarget>)getTargetWithNSString:(NSString *)name;
- (NSString *)getTargetName;
- (IOSObjectArray *)getTargets;
- (NSString *)getTitle;
- (id<RAREiToolBarHolder>)getToolBarHolder;
- (id)getUIWindow;
- (JavaNetURL *)getURLWithNSString:(NSString *)url;
- (int)getUsableScreenHeight;
- (int)getUsableScreenWidth;
- (id<RAREiContainer>)getViewer;
- (id<RAREiViewer>)getViewerWithNSString:(NSString *)name;
- (id<RAREiViewer>)getViewerByTargetNameWithNSString:(NSString *)target;
- (IOSObjectArray *)getViewers;
- (RAREaWidgetListener *)getWidgetListener;
- (int)getWidth;
- (id<JavaUtilList>)getWindowIcons;
- (id<RAREiContainer>)getWindowViewer;
- (id<RAREiViewer>)getWorkspaceViewer;
- (BOOL)isDisposed;
- (BOOL)isDesignMode;
- (void)createScriptHandlerWithRARESPOTMainWindow:(RARESPOTMainWindow *)cfg;
- (id<RAREiWidget>)createTitleWidgetWithRARESPOTMainWindow:(RARESPOTMainWindow *)cfg;
- (RAREaWidgetListener *)createWidgetListenerWithRAREiWidget:(id<RAREiWidget>)widget
                                             withJavaUtilMap:(id<JavaUtilMap>)map
                                      withRAREiScriptHandler:(id<RAREiScriptHandler>)scriptHandler;
- (void)destroyTargets;
- (void)destroyViewers;
- (id)fireEventWithNSString:(NSString *)eventName
    withJavaUtilEventObject:(JavaUtilEventObject *)event
                withBoolean:(BOOL)inline_;
- (void)handleCustomPropertiesWithRARESPOTMainWindow:(RARESPOTMainWindow *)cfg
                                     withJavaUtilMap:(id<JavaUtilMap>)properties;
- (void)handleExceptionExWithJavaLangThrowable:(JavaLangThrowable *)e;
- (void)showErrorDialogWithJavaLangThrowable:(JavaLangThrowable *)e;
- (BOOL)supportsMultipleWindowIcons;
- (void)setWindowIconsExWithJavaUtilList:(id<JavaUtilList>)icons;
- (void)setWorkspaceViewerWithRAREiViewer:(id<RAREiViewer>)v;
- (NSString *)getViewerClassNameWithRARESPOTViewer:(RARESPOTViewer *)cfg;
- (void)configureWithRARESPOTMainWindow:(RARESPOTMainWindow *)param0;
- (id<RAREiPopup>)createPopupWithRAREiWidget:(id<RAREiWidget>)param0;
- (id<RAREiWindow>)createWindowWithRAREiWidget:(id<RAREiWidget>)param0
                               withJavaUtilMap:(id<JavaUtilMap>)param1;
- (void)onConfigurationChangedWithBoolean:(BOOL)param0;
- (void)onConfigurationWillChangeWithId:(id)param0;
- (void)copyAllFieldsTo:(RAREaWindowManager *)other;
@end

J2OBJC_FIELD_SETTER(RAREaWindowManager, activeViewers_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(RAREaWindowManager, theTargets_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(RAREaWindowManager, appContext_, id<RAREiPlatformAppContext>)
J2OBJC_FIELD_SETTER(RAREaWindowManager, componentCreator_, id<RAREiPlatformComponentFactory>)
J2OBJC_FIELD_SETTER(RAREaWindowManager, contextURL_, JavaNetURL *)
J2OBJC_FIELD_SETTER(RAREaWindowManager, defaultFont_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREaWindowManager, mainFrame_, id<RAREiWindow>)
J2OBJC_FIELD_SETTER(RAREaWindowManager, menuBar_, id<RAREiPlatformMenuBar>)
J2OBJC_FIELD_SETTER(RAREaWindowManager, scriptHandler_, id<RAREiScriptHandler>)
J2OBJC_FIELD_SETTER(RAREaWindowManager, widgetListener_, RAREaWidgetListener *)
J2OBJC_FIELD_SETTER(RAREaWindowManager, workspaceTarget_, id<RAREiTarget>)
J2OBJC_FIELD_SETTER(RAREaWindowManager, aboutDialogRunner_, id<JavaLangRunnable>)
J2OBJC_FIELD_SETTER(RAREaWindowManager, windowIcons_, id<JavaUtilList>)

typedef RAREaWindowManager ComAppnativaRareUiAWindowManager;

@interface RAREaWindowManager_ViewerActivator : NSObject < JavaLangRunnable > {
 @public
  RAREaWindowManager *this$0_;
  RAREActionLink *theLink_;
}

- (id)initWithRAREaWindowManager:(RAREaWindowManager *)outer$
              withRAREActionLink:(RAREActionLink *)link;
- (id)initWithRAREaWindowManager:(RAREaWindowManager *)outer$
                 withRAREiWidget:(id<RAREiWidget>)context
              withRARESPOTViewer:(RARESPOTViewer *)cfg
                    withNSString:(NSString *)target;
- (void)run;
- (void)copyAllFieldsTo:(RAREaWindowManager_ViewerActivator *)other;
@end

J2OBJC_FIELD_SETTER(RAREaWindowManager_ViewerActivator, this$0_, RAREaWindowManager *)
J2OBJC_FIELD_SETTER(RAREaWindowManager_ViewerActivator, theLink_, RAREActionLink *)

@interface RAREaWindowManager_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaWindowManager *this$0_;
  JavaLangThrowable *val$e_;
}

- (void)run;
- (id)initWithRAREaWindowManager:(RAREaWindowManager *)outer$
           withJavaLangThrowable:(JavaLangThrowable *)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREaWindowManager_$1, this$0_, RAREaWindowManager *)
J2OBJC_FIELD_SETTER(RAREaWindowManager_$1, val$e_, JavaLangThrowable *)

@interface RAREaWindowManager_$2 : NSObject < RAREViewerCreator_iCallback > {
 @public
  RAREaWindowManager *this$0_;
  id<RAREiContainer> val$winviewer_;
  id val$viewerValue_;
  id<RAREiWindow> val$win_;
  id<JavaUtilMap> val$opts_;
  RAREWindowViewer *val$w_;
}

- (void)viewerCreatedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
                     withRAREiViewer:(id<RAREiViewer>)viewer;
- (void)errorHappenedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
               withJavaLangException:(JavaLangException *)e;
- (void)configCreatedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
                  withRARESPOTViewer:(RARESPOTViewer *)config;
- (void)startingOperationWithRAREiWidget:(id<RAREiWidget>)context
                      withRAREActionLink:(RAREActionLink *)link;
- (id)initWithRAREaWindowManager:(RAREaWindowManager *)outer$
              withRAREiContainer:(id<RAREiContainer>)capture$0
                          withId:(id)capture$1
                 withRAREiWindow:(id<RAREiWindow>)capture$2
                 withJavaUtilMap:(id<JavaUtilMap>)capture$3
            withRAREWindowViewer:(RAREWindowViewer *)capture$4;
@end

J2OBJC_FIELD_SETTER(RAREaWindowManager_$2, this$0_, RAREaWindowManager *)
J2OBJC_FIELD_SETTER(RAREaWindowManager_$2, val$winviewer_, id<RAREiContainer>)
J2OBJC_FIELD_SETTER(RAREaWindowManager_$2, val$viewerValue_, id)
J2OBJC_FIELD_SETTER(RAREaWindowManager_$2, val$win_, id<RAREiWindow>)
J2OBJC_FIELD_SETTER(RAREaWindowManager_$2, val$opts_, id<JavaUtilMap>)
J2OBJC_FIELD_SETTER(RAREaWindowManager_$2, val$w_, RAREWindowViewer *)

#endif // _RAREaWindowManager_H_