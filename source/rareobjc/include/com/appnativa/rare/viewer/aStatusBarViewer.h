//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-statusbar/com/appnativa/rare/viewer/aStatusBarViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaStatusBarViewer_H_
#define _RAREaStatusBarViewer_H_

@class RARESPOTStatusBar;
@class RARESPOTViewer;
@class RAREWindowViewer;
@class RAREaStatusBar;
@class RAREaStatusBar_ProgressStatusBarItem;
@class RAREaWidgetListener;
@protocol RAREiActionComponent;
@protocol RAREiContainer;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iStatusBar.h"
#include "com/appnativa/rare/viewer/aContainer.h"

@interface RAREaStatusBarViewer : RAREaContainer < RAREiStatusBar > {
 @public
  NSString *defaultStatus_;
  id<RAREiActionComponent> overwriteInsert_;
  RAREaStatusBar_ProgressStatusBarItem *progressBar_;
  RAREaStatusBar *statusBar_;
}

+ (id<RAREiPlatformBorder>)label_border;
+ (void)setLabel_border:(id<RAREiPlatformBorder>)label_border;
- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)addAsWindowDraggerWithRAREWindowViewer:(RAREWindowViewer *)w;
- (void)clearContents;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)configureForPopup;
- (void)createBasic;
- (void)createMainWindowDefault;
- (void)progressAbort;
- (void)progressComplete;
- (void)progressStartWithBoolean:(BOOL)showCancelButton;
- (void)progressStartWithBoolean:(BOOL)indeterminate
                    withNSString:(NSString *)message
                          withId:(id)cancelAction;
- (void)progressStartWithBoolean:(BOOL)indeterminate
                    withNSString:(NSString *)message
                          withId:(id)cancelAction
                          withId:(id)completeAction;
- (void)progressStartIndeterminateWithBoolean:(BOOL)showCancelButton;
- (void)showMessageWithNSString:(NSString *)msg;
- (void)toggleVisibility;
- (void)setCancelActionWithId:(id)action;
- (void)setInsertOverwriteWithBoolean:(BOOL)insert;
- (void)setInsertOverwriteEnabledWithBoolean:(BOOL)enabled;
- (void)setMaxHistoryWithInt:(int)max;
- (void)setProgressStatusWithNSString:(NSString *)msg;
- (void)setProgressUpdateWithInt:(int)value;
- (id<RAREiPlatformComponent>)getComponent;
- (int)getMaxHistory;
- (NSString *)getMessage;
- (id<RAREiPlatformComponent>)getProgressStatusBarItem;
- (BOOL)isProgressBarShowing;
- (void)clearConfigurationWithBoolean:(BOOL)dispose;
- (void)configureExWithRARESPOTStatusBar:(RARESPOTStatusBar *)cfg;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l OBJC_METHOD_FAMILY_NONE;
- (RAREaStatusBar *)createStatusBarAndComponentsWithRARESPOTStatusBar:(RARESPOTStatusBar *)cfg;
- (void)uninitializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l;
- (void)copyAllFieldsTo:(RAREaStatusBarViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaStatusBarViewer, defaultStatus_, NSString *)
J2OBJC_FIELD_SETTER(RAREaStatusBarViewer, overwriteInsert_, id<RAREiActionComponent>)
J2OBJC_FIELD_SETTER(RAREaStatusBarViewer, progressBar_, RAREaStatusBar_ProgressStatusBarItem *)
J2OBJC_FIELD_SETTER(RAREaStatusBarViewer, statusBar_, RAREaStatusBar *)

typedef RAREaStatusBarViewer ComAppnativaRareViewerAStatusBarViewer;

#endif // _RAREaStatusBarViewer_H_
