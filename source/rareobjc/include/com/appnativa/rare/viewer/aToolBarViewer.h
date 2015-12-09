//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aToolBarViewer.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaToolBarViewer_H_
#define _RAREaToolBarViewer_H_

@class RAREBeanWidget;
@class RARESPOTPushButton;
@class RARESPOTToolBar;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREUIAction;
@protocol RAREiContainer;
@protocol RAREiPlatformComponent;
@protocol RAREiTarget;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iToolBar.h"
#include "com/appnativa/rare/viewer/aContainer.h"

@interface RAREaToolBarViewer : RAREaContainer < RAREiToolBar > {
 @public
  BOOL buttonShowTextDefault_;
}

+ (RARESPOTPushButton *)toolbar_button;
+ (void)setToolbar_button:(RARESPOTPushButton *)toolbar_button;
+ (RARESPOTPushButton *)hyperlink_toolbar_button;
+ (void)setHyperlink_toolbar_button:(RARESPOTPushButton *)hyperlink_toolbar_button;
- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)addWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (id<RAREiWidget>)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (id<RAREiWidget>)addWithRAREUIAction:(RAREUIAction *)a;
- (id<RAREiWidget>)addWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (void)addWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)reverseWidgetOrder;
- (id<RAREiWidget>)addWithNSString:(NSString *)name
        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (id<RAREiWidget>)addWithNSString:(NSString *)name
                  withRAREUIAction:(RAREUIAction *)a;
- (void)addDefaultActions;
- (void)addExpader;
- (void)setAsExpanderWithRAREiWidget:(id<RAREiWidget>)widget
                         withBoolean:(BOOL)expander;
- (void)addSeparator;
- (id<RAREiWidget>)addWidgetWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)createComponentsWithBoolean:(BOOL)horizontal;
- (void)dispose;
- (void)targetAcquiredWithRAREiTarget:(id<RAREiTarget>)target;
- (id<RAREiWidget>)removeWidgetWithNSString:(NSString *)name;
- (void)setHorizontalWithBoolean:(BOOL)horizontal;
- (void)setParentHorizontalWithRAREBeanWidget:(RAREBeanWidget *)widget
                                  withBoolean:(BOOL)horizontal;
- (void)setSretchButtonsToFillSpaceWithBoolean:(BOOL)stretch;
- (void)setToolbarNameWithNSString:(NSString *)name;
- (id<RAREiPlatformComponent>)getComponent;
- (NSString *)getToolbarName;
- (BOOL)isHolder;
- (BOOL)isHorizontal;
- (void)setComponentSpacingWithInt:(int)spacing;
- (int)getComponentSpacingWithInt:(int)spacing;
- (void)addComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (void)configureExWithRARESPOTToolBar:(RARESPOTToolBar *)cfg;
- (void)copyAllFieldsTo:(RAREaToolBarViewer *)other;
@end

typedef RAREaToolBarViewer ComAppnativaRareViewerAToolBarViewer;

#endif // _RAREaToolBarViewer_H_
