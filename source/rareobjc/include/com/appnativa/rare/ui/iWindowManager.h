//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iWindowManager.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiWindowManager_H_
#define _RAREiWindowManager_H_

@class IOSObjectArray;
@class JavaLangThrowable;
@class JavaNetURL;
@class RAREActionLink;
@class RARESPOTMainWindow;
@class RARESPOTStatusBar;
@class RARESPOTToolBar;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREUIFont;
@class RAREUIPopupMenu;
@class RAREWindowViewer;
@class RAREaWidgetListener;
@class SPOTSet;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiContainer;
@protocol RAREiFrame;
@protocol RAREiPopup;
@protocol RAREiScriptHandler;
@protocol RAREiStatusBar;
@protocol RAREiTarget;
@protocol RAREiToolBar;
@protocol RAREiViewer;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iWindow.h"

@protocol RAREiWindowManager < RAREiWindow, NSObject, JavaObject >
- (id<RAREiViewer>)activateViewerWithRAREActionLink:(RAREActionLink *)link;
- (id<RAREiViewer>)activateViewerWithRAREiWidget:(id<RAREiWidget>)context
                              withRAREActionLink:(RAREActionLink *)link;
- (id<RAREiViewer>)activateViewerWithRAREiWidget:(id<RAREiWidget>)context
                              withRARESPOTViewer:(RARESPOTViewer *)cfg
                                    withNSString:(NSString *)target;
- (void)asyncActivateViewerWithRAREActionLink:(RAREActionLink *)link;
- (void)asyncActivateViewerWithRAREiWidget:(id<RAREiWidget>)context
                        withRAREActionLink:(RAREActionLink *)link;
- (void)changeTargetNameWithNSString:(NSString *)oldname
                        withNSString:(NSString *)newname
                     withRAREiTarget:(id<RAREiTarget>)target;
- (void)clearTargetWithNSString:(NSString *)target;
- (void)configureWithRARESPOTMainWindow:(RARESPOTMainWindow *)cfg;
- (id<RAREiWindow>)createDialogWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)target
                                  withNSString:(NSString *)title
                                   withBoolean:(BOOL)modal;
- (id<RAREiWindow>)createDialogWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)title
                                   withBoolean:(BOOL)modal;
- (id<RAREiWindow>)createDialogWithRAREiWidget:(id<RAREiWidget>)context
                               withJavaUtilMap:(id<JavaUtilMap>)options;
- (id<RAREiPopup>)createPopupWithRAREiWidget:(id<RAREiWidget>)context;
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
                               withJavaUtilMap:(id<JavaUtilMap>)options;
- (id<RAREiWindow>)createWindowWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)target
                                  withNSString:(NSString *)title;
- (void)dispose;
- (void)handleExceptionWithJavaLangThrowable:(JavaLangThrowable *)e;
- (void)onConfigurationChangedWithBoolean:(BOOL)reset;
- (void)onConfigurationWillChangeWithId:(id)newConfig;
- (RAREWindowViewer *)openViewerWindowWithRAREActionLink:(RAREActionLink *)link
                                                  withId:(id)viewerValue;
- (void)registerTargetWithNSString:(NSString *)name
                   withRAREiTarget:(id<RAREiTarget>)target;
- (void)registerViewerWithNSString:(NSString *)name
                   withRAREiViewer:(id<RAREiViewer>)viewer;
- (void)removeTargetWithNSString:(NSString *)target;
- (void)resetWithBoolean:(BOOL)reloadViewers;
- (void)unRegisterTargetWithNSString:(NSString *)name;
- (void)unRegisterViewerWithNSString:(NSString *)name;
- (void)setContextURLWithJavaNetURL:(JavaNetURL *)url;
- (void)setDefaultFontWithRAREUIFont:(RAREUIFont *)font;
- (float)setRelativeFontSizeWithFloat:(float)size;
- (void)setStatusWithNSString:(NSString *)status;
- (id<RAREiViewer>)setViewerWithNSString:(NSString *)target
                         withRAREiWidget:(id<RAREiWidget>)context
                         withRAREiViewer:(id<RAREiViewer>)viewer
                         withJavaUtilMap:(id<JavaUtilMap>)options;
- (id<RAREiViewer>)setViewerExWithNSString:(NSString *)target
                           withRAREiWidget:(id<RAREiWidget>)context
                           withRAREiViewer:(id<RAREiViewer>)viewer;
- (RAREUIFont *)getDefaultFont;
- (RAREUIFont *)getDefaultMonospacedFont;
- (id<RAREiFrame>)getFrameWithNSString:(NSString *)name;
- (IOSObjectArray *)getFrames;
- (id<RAREiContainer>)getRootViewer;
- (id<RAREiScriptHandler>)getScriptHandler;
- (id<RAREiTarget>)getTargetWithNSString:(NSString *)name;
- (IOSObjectArray *)getTargets;
- (NSString *)getTitle;
- (JavaNetURL *)getURLWithNSString:(NSString *)url;
- (int)getUsableScreenHeight;
- (int)getUsableScreenWidth;
- (id<RAREiViewer>)getViewerWithNSString:(NSString *)name;
- (id<RAREiViewer>)getViewerByTargetNameWithNSString:(NSString *)target;
- (IOSObjectArray *)getViewers;
- (RAREaWidgetListener *)getWidgetListener;
- (id<RAREiViewer>)getWorkspaceViewer;
- (BOOL)isDisposed;
- (id<JavaUtilList>)getWindowIcons;
- (void)setWindowIconsWithJavaUtilList:(id<JavaUtilList>)icons;
- (id<RAREiWindow>)getMainWindow;
@end

#define ComAppnativaRareUiIWindowManager RAREiWindowManager

#endif // _RAREiWindowManager_H_
