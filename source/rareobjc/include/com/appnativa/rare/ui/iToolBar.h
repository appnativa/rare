//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iToolBar.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiToolBar_H_
#define _RAREiToolBar_H_

@class RARESPOTWidget;
@class RAREUIAction;
@protocol RAREiPlatformComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"

@protocol RAREiToolBar < NSObject, JavaObject >
- (id<RAREiWidget>)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)addWithRAREiWidget:(id<RAREiWidget>)widget;
- (id<RAREiWidget>)addWithRAREUIAction:(RAREUIAction *)a;
- (id<RAREiWidget>)addWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (id<RAREiWidget>)addWithNSString:(NSString *)name
        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (id<RAREiWidget>)addWithNSString:(NSString *)name
                  withRAREUIAction:(RAREUIAction *)a;
- (void)addSeparator;
- (void)dispose;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setHorizontalWithBoolean:(BOOL)horizontal;
- (void)setSretchButtonsToFillSpaceWithBoolean:(BOOL)stretch;
- (void)setToolbarNameWithNSString:(NSString *)name;
- (id<RAREiPlatformComponent>)getComponent;
- (NSString *)getToolbarName;
- (id<RAREiWidget>)getWidgetWithNSString:(NSString *)name;
- (BOOL)isHolder;
- (id<RAREiWidget>)removeWidgetWithNSString:(NSString *)name;
- (void)removeWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)removeAllWidgets;
@end

#define ComAppnativaRareUiIToolBar RAREiToolBar

#endif // _RAREiToolBar_H_
