//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aWindowViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaWindowViewer_H_
#define _RAREaWindowViewer_H_

@class IOSObjectArray;
@class JavaIoFile;
@class JavaLangThrowable;
@class JavaNetURL;
@class JavaUtilDate;
@class JavaUtilEventObject;
@class RAREActionBar;
@class RAREActionLink;
@class RAREActionLink_ReturnDataTypeEnum;
@class RAREErrorInformation;
@class RAREPushButtonWidget;
@class RARERenderableDataItem;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREScriptingEvent;
@class RAREUIAction;
@class RAREUIColor;
@class RAREUICursor;
@class RAREUIDimension;
@class RAREUIImage;
@class RAREUIImageIcon;
@class RAREUIPopupMenu;
@class RAREUIRectangle;
@class RAREWindowViewer;
@class RAREaUIImageIcon;
@class RAREaWindowViewer_BrowserNavigator;
@class RAREaWindowViewer_WindowLocation;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol JavaLangCharSequence;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREUTiCancelable;
@protocol RAREiActionListener;
@protocol RAREiCancelableFuture;
@protocol RAREiContainer;
@protocol RAREiDataCollection;
@protocol RAREiDataCollectionHandler;
@protocol RAREiFormViewer;
@protocol RAREiFrame;
@protocol RAREiFunctionCallback;
@protocol RAREiImagePainter;
@protocol RAREiPlatformAnimator;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformMenuBar;
@protocol RAREiPlatformWindowManager;
@protocol RAREiScriptHandler;
@protocol RAREiScriptHandler_iScriptRunnable;
@protocol RAREiStatusBar;
@protocol RAREiTarget;
@protocol RAREiTimer;
@protocol RAREiToolBarHolder;
@protocol RAREiViewer;
@protocol RAREiWidget;
@protocol RAREiWindowListener;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iWindow.h"
#include "com/appnativa/rare/viewer/aContainer.h"
#include "java/lang/Runnable.h"

@interface RAREaWindowViewer : RAREaContainer < RAREiWindow > {
 @public
  BOOL canClose_;
  id<RAREiTimer> clearStatusTimer_;
  id<RAREiScriptHandler> scriptHandler_;
  RAREScriptingEvent *theEvent_;
  id<RAREiWindow> theWindow_;
  RAREScriptingEvent *timerEvent_;
  id<RAREiPlatformWindowManager> windowManager_;
  RAREaWindowViewer *windowParent_;
  RAREUIDimension *windowSize_;
  id<RAREiTimer> windowTimer_;
}

+ (RAREaWindowViewer_WindowLocation *)_location;
+ (void)set_location:(RAREaWindowViewer_WindowLocation *)_location;
+ (RAREaWindowViewer_BrowserNavigator *)_navigator;
+ (void)set_navigator:(RAREaWindowViewer_BrowserNavigator *)_navigator;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)ctx
                         withNSString:(NSString *)name
                      withRAREiWindow:(id<RAREiWindow>)win
                withRAREaWindowViewer:(RAREaWindowViewer *)parent
               withRAREiScriptHandler:(id<RAREiScriptHandler>)sh;
- (void)activateViewerWithRAREActionLink:(RAREActionLink *)link;
- (void)activateViewerWithRAREiViewer:(id<RAREiViewer>)viewer
                         withNSString:(NSString *)target;
- (void)activateViewerWithId:(id)href
                withNSString:(NSString *)target;
- (void)activateViewerWithRAREiWidget:(id<RAREiWidget>)context
                      withRAREiViewer:(id<RAREiViewer>)viewer
                         withNSString:(NSString *)target;
- (void)activateViewerWithRAREiWidget:(id<RAREiWidget>)context
                               withId:(id)href
                         withNSString:(NSString *)target;
- (void)addOverlayWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)addOverlayWithRAREiWidget:(id<RAREiWidget>)w;
- (void)addManagedOverlayWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)addManagedOverlayWithRAREiWidget:(id<RAREiWidget>)w;
- (void)addWindowDraggerWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)addWindowListenerWithRAREiWindowListener:(id<RAREiWindowListener>)l;
- (void)alertWithId:(id)message;
- (void)alertWithId:(id)message
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)alertWithNSString:(NSString *)title
                   withId:(id)message
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)beep;
- (void)blur;
- (BOOL)browseWithNSString:(NSString *)url;
- (BOOL)browseWithJavaNetURL:(JavaNetURL *)url;
- (BOOL)canDrop;
- (void)center;
- (void)centerWithRAREWindowViewer:(RAREWindowViewer *)win;
- (void)clearContents;
- (void)clearForm;
- (void)clearInstance;
- (void)clearIntervalWithRAREUTiCancelable:(id<RAREUTiCancelable>)task;
- (void)clearSessionCookies;
- (void)clearTargetWithNSString:(NSString *)target;
- (void)clearTimeoutWithRAREUTiCancelable:(id<RAREUTiCancelable>)task;
- (void)close;
- (void)closePopupWindowsWithBoolean:(BOOL)all;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)confirmWithId:(id)message
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)confirmWithId:(id)title
               withId:(id)message
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)confirmWithId:(id)title
               withId:(id)message
         withNSString:(NSString *)ok
         withNSString:(NSString *)cancel
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)copyToClipboardWithNSString:(NSString *)value OBJC_METHOD_FAMILY_NONE;
- (RAREActionLink *)createActionLinkWithId:(id)url;
- (RAREActionLink *)createActionLinkWithRAREiWidget:(id<RAREiWidget>)context
                                             withId:(id)url;
- (id<RAREiPlatformAnimator>)createAnimatorWithNSString:(NSString *)animation;
- (id<RAREiScriptHandler_iScriptRunnable>)createCancelableRunnerWithId:(id)code
                                                                withId:(id)cancelCode
                                                          withNSString:(NSString *)lang;
- (id<iSPOTElement>)createConfigurationObjectWithRAREActionLink:(RAREActionLink *)link;
- (id<iSPOTElement>)createConfigurationObjectWithNSString:(NSString *)name;
- (id<RAREUTiCancelable>)createConfigurationObjectWithRAREActionLink:(RAREActionLink *)link
                                           withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<iSPOTElement>)createConfigurationObjectWithNSString:(NSString *)name
                                             withNSString:(NSString *)templateName;
- (id<RAREiDataCollection>)createDataCollectionWithRAREActionLink:(RAREActionLink *)link
                                        withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREiDataCollection>)createDataCollectionWithRAREiWidget:(id<RAREiWidget>)context
                                              withJavaUtilList:(id<JavaUtilList>)list;
- (id<RAREiDataCollection>)createDataCollectionWithRAREActionLink:(RAREActionLink *)link
                                                      withBoolean:(BOOL)tabular
                                        withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREiDataCollection>)createDataCollectionWithNSString:(NSString *)name
                                         withRAREActionLink:(RAREActionLink *)link
                                                withBoolean:(BOOL)tabular
                                  withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREiDataCollection>)createDataCollectionWithNSString:(NSString *)handler
                                               withNSString:(NSString *)name
                                         withRAREActionLink:(RAREActionLink *)link
                                            withJavaUtilMap:(id<JavaUtilMap>)attributes
                                                withBoolean:(BOOL)tabular
                                  withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREiImagePainter>)createImagePainterWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (id<RAREiImagePainter>)createImagePainterWithRAREUIImage:(RAREUIImage *)image;
- (RAREActionLink *)createInlineActionLinkWithNSString:(NSString *)data
                                          withNSString:(NSString *)mimeType;
- (JavaNetURL *)createInlineURLWithNSString:(NSString *)data
                               withNSString:(NSString *)mimeType;
- (RAREUIPopupMenu *)createPopupMenuWithRAREiWidget:(id<RAREiWidget>)context;
- (RAREUIPopupMenu *)createPopupMenuWithRAREiWidget:(id<RAREiWidget>)context
                                       withNSString:(NSString *)url;
- (RAREUIPopupMenu *)createPopupMenuWithRAREiWidget:(id<RAREiWidget>)context
                                       withNSString:(NSString *)url
                                        withBoolean:(BOOL)addDefaultItems;
- (id<RAREiScriptHandler_iScriptRunnable>)createRunnableWithNSObjectArray:(IOSObjectArray *)args;
- (id<RAREiViewer>)createViewerWithRAREActionLink:(RAREActionLink *)link;
- (id<RAREiViewer>)createViewerWithNSString:(NSString *)url;
- (id<RAREUTiCancelable>)createViewerWithRAREActionLink:(RAREActionLink *)link
                              withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREiViewer>)createViewerWithRAREiWidget:(id<RAREiWidget>)context
                    withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (id<RAREiViewer>)createViewerWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)url;
- (id<RAREiViewer>)createViewerWithRAREiWidget:(id<RAREiWidget>)parent
                            withRARESPOTWidget:(RARESPOTWidget *)cfg;
- (id<RAREUTiCancelable>)createViewerWithNSString:(NSString *)url
                        withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREUTiCancelable>)createViewerWithRAREiWidget:(id<RAREiWidget>)context
                                        withNSString:(NSString *)url
                           withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREiViewer>)createViewerWithRAREiWidget:(id<RAREiWidget>)parent
                            withRARESPOTWidget:(RARESPOTWidget *)cfg
                                withJavaNetURL:(JavaNetURL *)context;
- (id<RAREiWidget>)createWidgetWithRAREiWidget:(id<RAREiWidget>)context
                    withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (id<RAREiWidget>)createWidgetWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)type;
- (id<RAREiWidget>)createWidgetWithRAREiWidget:(id<RAREiWidget>)context
                            withRARESPOTWidget:(RARESPOTWidget *)cfg;
- (void)deiconify;
- (void)dispose;
- (void)disposeOfWindow;
- (void)errorWithId:(id)message;
- (void)errorWithId:(id)message
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)errorWithNSString:(NSString *)title
                   withId:(id)message
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)errorFeedback;
- (id)evalWithNSObjectArray:(IOSObjectArray *)args;
- (void)executeActionWithNSString:(NSString *)actionName;
+ (NSString *)fileToURLStringWithJavaIoFile:(JavaIoFile *)file;
- (NSString *)formatDateWithJavaUtilDate:(JavaUtilDate *)date
                             withBoolean:(BOOL)time;
- (NSString *)formatDateWithJavaUtilDate:(JavaUtilDate *)date
                            withNSString:(NSString *)format;
- (void)handleExceptionWithJavaLangThrowable:(JavaLangThrowable *)e;
- (void)hideProgressPopup;
- (void)hideProgressPopupWithBoolean:(BOOL)force;
- (void)hideVirtualKeyboard;
- (void)hideWaitCursor;
- (void)hideWaitCursorWithBoolean:(BOOL)force;
- (void)hideWindow;
- (void)iconify;
- (void)invokeLaterWithId:(id)code;
- (void)invokeLaterWithId:(id)code
                   withId:(id)data;
- (void)invokeLaterWithRAREiWidget:(id<RAREiWidget>)context
                            withId:(id)code
                            withId:(id)data;
- (void)loadScriptWithRAREActionLink:(RAREActionLink *)link;
- (void)loadScriptWithNSString:(NSString *)url;
- (void)loadScriptWithRAREActionLink:(RAREActionLink *)link
                         withBoolean:(BOOL)strict
                         withBoolean:(BOOL)optimized;
- (void)loadScriptWithNSString:(NSString *)url
                   withBoolean:(BOOL)strict
                   withBoolean:(BOOL)optimized;
- (BOOL)mailToWithNSString:(NSString *)uri;
- (BOOL)mailToWithNSString:(NSString *)address
              withNSString:(NSString *)subject
              withNSString:(NSString *)body;
- (void)maximize;
- (void)minimize;
- (void)moveByWithFloat:(float)x
              withFloat:(float)y;
- (void)moveToWithFloat:(float)x
              withFloat:(float)y;
- (long long int)nanoTime;
- (void)normal;
- (JavaUtilDate *)now;
- (void)okCancelWithId:(id)message
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)okCancelWithNSString:(NSString *)title
                      withId:(id)message
   withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (RAREWindowViewer *)openWithNSString:(NSString *)url;
- (RAREWindowViewer *)openWithRARESPOTViewer:(RARESPOTViewer *)cfg
                                withNSString:(NSString *)winoptions;
- (RAREWindowViewer *)openWithRAREActionLink:(RAREActionLink *)link
                             withJavaUtilMap:(id<JavaUtilMap>)options
                                      withId:(id)viewerValue;
- (RAREWindowViewer *)openWithRAREActionLink:(RAREActionLink *)link
                                withNSString:(NSString *)winoptions
                                      withId:(id)viewerValue;
- (RAREWindowViewer *)openWithNSString:(NSString *)url
                          withNSString:(NSString *)winoptions
                                withId:(id)viewerValue;
- (RAREWindowViewer *)openDialogWithRAREiViewer:(id<RAREiViewer>)v;
- (RAREWindowViewer *)openDialogWithNSString:(NSString *)url;
- (RAREWindowViewer *)openDialogWithRAREActionLink:(RAREActionLink *)link
                                      withNSString:(NSString *)title
                                            withId:(id)viewerValue;
- (RAREWindowViewer *)openDialogWithNSString:(NSString *)url
                                withNSString:(NSString *)title
                                      withId:(id)viewerValue;
- (void)pack;
- (void)promptWithNSString:(NSString *)prompt
 withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)promptWithNSString:(NSString *)prompt
                    withId:(id)value
 withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)promptWithNSString:(NSString *)title
              withNSString:(NSString *)prompt
                    withId:(id)value
 withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)registerCollectionHandlerWithNSString:(NSString *)name
               withRAREiDataCollectionHandler:(id<RAREiDataCollectionHandler>)ch;
- (void)reloadForm;
- (void)removeOverlayWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)removeOverlayWithRAREiWidget:(id<RAREiWidget>)w;
- (void)removeWindowDraggerWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)removeWindowListenerWithRAREiWindowListener:(id<RAREiWindowListener>)l;
- (void)resizeByWithInt:(int)width
                withInt:(int)height;
- (void)resizeToWithInt:(int)width
                withInt:(int)height;
- (id<RAREUTiCancelable>)sendFormDataWithRAREiWidget:(id<RAREiWidget>)context
                                  withRAREActionLink:(RAREActionLink *)link
                                     withJavaUtilMap:(id<JavaUtilMap>)data
                                         withBoolean:(BOOL)multipart
               withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)returnType
                           withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)showProgressPopupWithJavaLangCharSequence:(id<JavaLangCharSequence>)message;
- (void)showProgressPopupWithJavaLangCharSequence:(id<JavaLangCharSequence>)message
                            withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable;
- (void)showWaitCursor;
- (void)showWaitCursorWithRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable;
- (void)showWindow;
- (void)showWindowWithInt:(int)x
                  withInt:(int)y;
- (id<RAREiCancelableFuture>)spawnWithNSObjectArray:(IOSObjectArray *)args;
- (void)submitForm;
- (void)systemAlertWithId:(id)message;
- (void)systemAlertWithId:(id)message
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
  withRAREiActionListener:(id<RAREiActionListener>)listener;
- (void)toBack;
- (void)toFront;
- (void)updateProgressPopupWithJavaLangCharSequence:(id<JavaLangCharSequence>)message;
- (id<RAREUTiCancelable>)uploadDataWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREActionLink:(RAREActionLink *)link
                                            withId:(id)data
                                      withNSString:(NSString *)name
                                      withNSString:(NSString *)mimeType
                                      withNSString:(NSString *)fileName
             withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)returnType
                         withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)yesNoWithId:(id)message
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)yesNoWithNSString:(NSString *)title
                   withId:(id)message
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)yesNoWithNSString:(NSString *)title
                   withId:(id)message
             withNSString:(NSString *)yes
             withNSString:(NSString *)no
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)yesNoCancelWithId:(id)message
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)yesNoCancelWithNSString:(NSString *)title
                         withId:(id)message
      withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)yesNoCancelWithNSString:(NSString *)title
                         withId:(id)message
                   withNSString:(NSString *)yes
                   withNSString:(NSString *)no
      withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)yesNoCancelWithNSString:(NSString *)title
                         withId:(id)message
                   withNSString:(NSString *)yes
                   withNSString:(NSString *)no
                   withNSString:(NSString *)cancel
      withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height;
- (void)setCanCloseWithBoolean:(BOOL)can;
- (void)setCookie2ValueWithJavaNetURL:(JavaNetURL *)url
                         withNSString:(NSString *)value;
- (void)setCookieValueWithJavaNetURL:(JavaNetURL *)url
                        withNSString:(NSString *)value;
- (void)setDefaultButtonWithRAREPushButtonWidget:(RAREPushButtonWidget *)widget;
- (void)setEventExWithRAREScriptingEvent:(RAREScriptingEvent *)e;
- (void)setFrameTitleAndIconWithNSString:(NSString *)frame
                            withNSString:(NSString *)title
                   withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setInsertOverwriteWithBoolean:(BOOL)overwrite;
- (id<RAREUTiCancelable>)setIntervalWithId:(id)code
                                  withLong:(long long int)interval;
- (void)setLocationWithFloat:(float)x
                   withFloat:(float)y;
- (id<RAREiPlatformMenuBar>)setMenuBarWithRAREiPlatformMenuBar:(id<RAREiPlatformMenuBar>)mb;
- (void)setRelativeFontSizeWithFloat:(float)size;
- (void)setResizableWithBoolean:(BOOL)resizable;
- (void)setStatusWithNSString:(NSString *)status;
- (id<RAREiStatusBar>)setStatusBarWithRAREiStatusBar:(id<RAREiStatusBar>)sb;
- (void)setStatusItemWithNSString:(NSString *)item
                     withNSString:(NSString *)value;
- (void)setStatusItemEnabledWithNSString:(NSString *)item
                             withBoolean:(BOOL)enabled;
- (void)setThenClearStatusWithNSString:(NSString *)status
                               withInt:(int)seconds;
- (id<RAREUTiCancelable>)setTimeoutWithId:(id)code
                                 withLong:(long long int)timeout;
- (void)setTitleWithNSString:(NSString *)title;
- (id<RAREiToolBarHolder>)setToolBarHolderWithRAREiToolBarHolder:(id<RAREiToolBarHolder>)tbh;
- (id)getWithNSString:(NSString *)name;
- (RAREUIAction *)getActionWithNSString:(NSString *)name;
- (RAREActionBar *)getActionBar;
- (id<JavaUtilMap>)getActions;
- (JavaNetURL *)getApplicationURL;
- (RAREUIRectangle *)getBounds;
- (NSString *)getClipboardContents;
- (JavaNetURL *)getCodeBase;
- (id<RAREiPlatformComponent>)getComponent;
- (id<RAREUTiCancelable>)getContentWithRAREiWidget:(id<RAREiWidget>)context
                                            withId:(id)url
                                            withId:(id)returnType
                         withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREUTiCancelable>)getContentAsJSONWithId:(id)url
                      withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREUTiCancelable>)getContentAsListWithRAREiWidget:(id<RAREiWidget>)context
                                                  withId:(id)url
                                             withBoolean:(BOOL)tabular
                               withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREUTiCancelable>)getContentAsStringWithId:(id)url
                        withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (JavaNetURL *)getContextURL;
- (id<JavaUtilList>)getCookieList;
- (NSString *)getCookies;
- (RAREUICursor *)getCursorWithNSString:(NSString *)name;
- (id<RAREiDataCollection>)getDataCollectionWithNSString:(NSString *)name;
- (RAREUIImageIcon *)getDelayedIconWithJavaNetURL:(JavaNetURL *)url
                             withRAREaUIImageIcon:(RAREaUIImageIcon *)delayedIcon
                                          withInt:(int)constraints
                                      withBoolean:(BOOL)startLoading;
- (RAREUIImageIcon *)getDelayedIconWithJavaNetURL:(JavaNetURL *)url
                             withRAREaUIImageIcon:(RAREaUIImageIcon *)delayedIcon
                                  withRAREUIColor:(RAREUIColor *)bg
                                      withBoolean:(BOOL)startLoading;
- (RAREUIImage *)getDelayedImageWithJavaNetURL:(JavaNetURL *)url;
- (RAREUIImage *)getDelayedImageWithJavaNetURL:(JavaNetURL *)url
                                       withInt:(int)size
                                       withInt:(int)constraints
         withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)st
                               withRAREUIColor:(RAREUIColor *)bg;
- (float)getDevicePixelRatio;
- (RAREErrorInformation *)getErrorInformationWithId:(id)error;
- (RAREErrorInformation *)getErrorInformationWithId:(id)error
                                       withNSString:(NSString *)title;
- (RAREScriptingEvent *)getEvent;
- (id<RAREiPlatformComponent>)getFocusOwner;
- (id<RAREiWidget>)getFocusedWidget;
- (id<RAREiFormViewer>)getFormViewer;
- (id<RAREiFrame>)getFrameWithNSString:(NSString *)name;
- (IOSObjectArray *)getFrames;
- (id<JavaUtilMap>)getIcons;
- (RAREUIDimension *)getInnerSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (int)getInnerHeight;
- (int)getInnerWidth;
- (RAREaWindowViewer_WindowLocation *)getLocation;
- (id<RAREiPlatformMenuBar>)getMenuBar;
- (id)getNamedItemWithNSString:(NSString *)name;
- (JavaUtilEventObject *)getNativeEvent;
- (RAREaWindowViewer_BrowserNavigator *)getNavigator;
- (id<RAREiContainer>)getParent;
- (RARERenderableDataItem *)getParentItem;
- (id<RAREiPlatformComponent>)getPermanentFocusOwner;
- (id<RAREiWidget>)getPermanentFocusedWidget;
- (float)getRelativeFontSize;
- (RAREUIImageIcon *)getResourceIconWithNSString:(NSString *)name;
- (id<RAREiTarget>)getRootTarget;
- (id<RAREiViewer>)getRootViewer;
- (double)getRuntimeVersion;
- (int)getScreenX;
- (int)getScreenY;
- (id<RAREiScriptHandler>)getScriptHandler;
- (id)getSelection;
- (RAREaWindowViewer *)getSelf;
- (RAREUIDimension *)getSize;
- (JavaNetURL *)getSourceURL;
- (NSString *)getStatus;
- (id<RAREiStatusBar>)getStatusBar;
- (NSString *)getStringWithNSString:(NSString *)name;
- (NSString *)getStringWithNSString:(NSString *)name
                  withNSObjectArray:(IOSObjectArray *)args;
- (NSString *)getStringExWithNSString:(NSString *)name
                    withNSStringArray:(IOSObjectArray *)args;
- (id<JavaUtilMap>)getStrings;
- (id<RAREiTarget>)getTargetWithNSString:(NSString *)name;
- (NSString *)getTargetName;
- (IOSObjectArray *)getTargets;
- (NSString *)getTitle;
- (id<RAREiToolBarHolder>)getToolBarHolder;
- (RAREaWindowViewer *)getTop;
- (id)getUIWindow;
- (id<RAREiViewer>)getViewerWithNSString:(NSString *)name;
- (IOSObjectArray *)getViewers;
- (id<RAREiWidget>)getWidgetWithNSString:(NSString *)name;
- (int)getWidgetCount;
- (id<RAREiWidget>)getWidgetExWithNSStringArray:(IOSObjectArray *)name;
- (RAREWindowViewer *)getWindow;
- (BOOL)isApplet;
- (BOOL)isClosingAllowed;
- (BOOL)isMaximized;
- (BOOL)isRetainInitialWidgetValues;
- (BOOL)isWebStart;
- (RAREScriptingEvent *)getTimerEvent;
- (id<JavaUtilList>)getWidgetListEx;
- (id<RAREiScriptHandler_iScriptRunnable>)createRunnerWithNSString:(NSString *)name
                                                            withId:(id)code
                                                      withNSString:(NSString *)language;
- (void)loadScriptExWithRAREActionLink:(RAREActionLink *)link
                           withBoolean:(BOOL)use
                           withBoolean:(BOOL)strict
                           withBoolean:(BOOL)optimized;
- (void)loadScriptExWithNSString:(NSString *)url
                     withBoolean:(BOOL)use
                     withBoolean:(BOOL)strict
                     withBoolean:(BOOL)optimized;
- (id<RAREUTiCancelable>)scheduleTaskWithNSString:(NSString *)name
                                           withId:(id)code
                                         withLong:(long long int)timeout
                                      withBoolean:(BOOL)repeats;
- (void)copyAllFieldsTo:(RAREaWindowViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaWindowViewer, clearStatusTimer_, id<RAREiTimer>)
J2OBJC_FIELD_SETTER(RAREaWindowViewer, scriptHandler_, id<RAREiScriptHandler>)
J2OBJC_FIELD_SETTER(RAREaWindowViewer, theEvent_, RAREScriptingEvent *)
J2OBJC_FIELD_SETTER(RAREaWindowViewer, theWindow_, id<RAREiWindow>)
J2OBJC_FIELD_SETTER(RAREaWindowViewer, timerEvent_, RAREScriptingEvent *)
J2OBJC_FIELD_SETTER(RAREaWindowViewer, windowManager_, id<RAREiPlatformWindowManager>)
J2OBJC_FIELD_SETTER(RAREaWindowViewer, windowParent_, RAREaWindowViewer *)
J2OBJC_FIELD_SETTER(RAREaWindowViewer, windowSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREaWindowViewer, windowTimer_, id<RAREiTimer>)

typedef RAREaWindowViewer ComAppnativaRareViewerAWindowViewer;

@interface RAREaWindowViewer_WindowLocation : NSObject {
 @public
  RAREaWindowViewer *this$0_;
}

- (NSString *)description;
- (void)setHrefWithNSString:(NSString *)url;
- (NSString *)getHash;
- (NSString *)getHost;
- (NSString *)getHostname;
- (NSString *)getHref;
- (NSString *)getPathname;
- (int)getPort;
- (NSString *)getProtocol;
- (NSString *)getSearch;
- (JavaNetURL *)getUrl;
- (id)initWithRAREaWindowViewer:(RAREaWindowViewer *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaWindowViewer_WindowLocation, this$0_, RAREaWindowViewer *)

@interface RAREaWindowViewer_BrowserNavigator : NSObject {
}

- (NSString *)getAppCodeName;
- (NSString *)getAppName;
- (NSString *)getAppVersion;
- (BOOL)getCookieEnabled;
- (NSString *)getLanguage;
- (NSString *)getPlatform;
- (NSString *)getUserAgent;
- (id)init;
@end

@interface RAREaWindowViewer_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaWindowViewer *this$0_;
}

- (void)run;
- (id)initWithRAREaWindowViewer:(RAREaWindowViewer *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaWindowViewer_$1, this$0_, RAREaWindowViewer *)

@interface RAREaWindowViewer_$2 : NSObject < JavaLangRunnable > {
 @public
  RAREaWindowViewer *this$0_;
  id<RAREiStatusBar> val$sb_;
  NSString *val$status_;
}

- (void)run;
- (id)initWithRAREaWindowViewer:(RAREaWindowViewer *)outer$
             withRAREiStatusBar:(id<RAREiStatusBar>)capture$0
                   withNSString:(NSString *)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREaWindowViewer_$2, this$0_, RAREaWindowViewer *)
J2OBJC_FIELD_SETTER(RAREaWindowViewer_$2, val$sb_, id<RAREiStatusBar>)
J2OBJC_FIELD_SETTER(RAREaWindowViewer_$2, val$status_, NSString *)

@interface RAREaWindowViewer_$3 : NSObject < JavaLangRunnable > {
 @public
  id<JavaLangRunnable> val$r_;
}

- (void)run;
- (id)initWithJavaLangRunnable:(id<JavaLangRunnable>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREaWindowViewer_$3, val$r_, id<JavaLangRunnable>)

@interface RAREaWindowViewer_$4 : NSObject < JavaLangRunnable > {
 @public
  RAREaWindowViewer *this$0_;
  id<RAREiScriptHandler_iScriptRunnable> val$r_;
}

- (void)run;
- (id)initWithRAREaWindowViewer:(RAREaWindowViewer *)outer$
withRAREiScriptHandler_iScriptRunnable:(id<RAREiScriptHandler_iScriptRunnable>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREaWindowViewer_$4, this$0_, RAREaWindowViewer *)
J2OBJC_FIELD_SETTER(RAREaWindowViewer_$4, val$r_, id<RAREiScriptHandler_iScriptRunnable>)

#endif // _RAREaWindowViewer_H_