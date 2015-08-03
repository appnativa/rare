//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/WindowManager.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREWindowManager_H_
#define _RAREWindowManager_H_

@class JavaLangThrowable;
@class RAREAppContext;
@class RAREFrame;
@class RARESPOTMainWindow;
@class RAREaWidgetListener;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiPlatformAppContext;
@protocol RAREiPopup;
@protocol RAREiScriptHandler;
@protocol RAREiWidget;
@protocol RAREiWindow;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/UITarget.h"
#include "com/appnativa/rare/ui/aWindowManager.h"

@interface RAREWindowManager : RAREaWindowManager {
}

- (id)initWithRAREAppContext:(RAREAppContext *)app;
- (BOOL)supportsMultipleWindowIcons;
- (void)configureWithRARESPOTMainWindow:(RARESPOTMainWindow *)cfg;
- (id<RAREiPopup>)createPopupWithRAREiWidget:(id<RAREiWidget>)context;
- (id<RAREiWindow>)createWindowWithRAREiWidget:(id<RAREiWidget>)context
                               withJavaUtilMap:(id<JavaUtilMap>)options;
- (id<RAREiWindow>)createWindowWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)target
                                  withNSString:(NSString *)title;
- (void)onConfigurationChangedWithBoolean:(BOOL)reset;
- (void)onConfigurationWillChangeWithId:(id)newConfig;
- (RAREaWidgetListener *)createWidgetListenerWithRAREiWidget:(id<RAREiWidget>)widget
                                             withJavaUtilMap:(id<JavaUtilMap>)map
                                      withRAREiScriptHandler:(id<RAREiScriptHandler>)scriptHandler;
- (void)handleCustomPropertiesWithRARESPOTMainWindow:(RARESPOTMainWindow *)cfg
                                     withJavaUtilMap:(id<JavaUtilMap>)properties;
- (void)showErrorDialogWithJavaLangThrowable:(JavaLangThrowable *)e;
- (void)setWindowIconsExWithJavaUtilList:(id<JavaUtilList>)icons;
@end

typedef RAREWindowManager ComAppnativaRareUiWindowManager;

@interface RAREWindowManager_WorkspaceTarget : RAREUITarget {
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                        withRAREFrame:(RAREFrame *)f;
@end

#endif // _RAREWindowManager_H_