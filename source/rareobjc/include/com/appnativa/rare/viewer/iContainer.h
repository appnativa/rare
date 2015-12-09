//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/iContainer.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiContainer_H_
#define _RAREiContainer_H_

@class RARESPOTWidget;
@class RAREUISelectionModelGroup;
@class RAREiContainer_LayoutEnum;
@class RAREiViewer_DisableBehaviorEnum;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiListHandler;
@protocol RAREiParentComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "java/lang/Enum.h"

@protocol RAREiContainer < RAREiViewer, NSObject, JavaObject >
- (RAREUISelectionModelGroup *)addToSelectionGroupWithNSString:(NSString *)name
                                          withRAREiListHandler:(id<RAREiListHandler>)comp
                                                        withId:(id)model
                                                       withInt:(int)position;
- (id<RAREiWidget>)addWidgetWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (id<RAREiWidget>)addWidgetWithRAREiParentComponent:(id<RAREiParentComponent>)panel
                                  withRARESPOTWidget:(RARESPOTWidget *)cfg
                       withRAREiContainer_LayoutEnum:(RAREiContainer_LayoutEnum *)fm;
- (BOOL)handleFocusWithRAREiWidget:(id<RAREiWidget>)from
                       withBoolean:(BOOL)next;
- (id<RAREiWidget>)createWidgetWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (BOOL)isTextDirectionSet;
- (void)addWidgetWithRAREiWidget:(id<RAREiWidget>)widget
                          withId:(id)constraints
                         withInt:(int)position;
- (void)removeFromSelectionGroupWithNSString:(NSString *)name
                                      withId:(id)model;
- (void)removeWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (RAREiViewer_DisableBehaviorEnum *)getDisableBehavior;
- (id<RAREiWidget>)getWidgetWithInt:(int)index;
- (id<RAREiWidget>)getWidgetWithNSString:(NSString *)name;
- (id<RAREiWidget>)getWidgetFromPathWithNSString:(NSString *)path;
- (id<JavaUtilList>)getWidgetList;
- (id<JavaUtilList>)getWidgetNames;
- (id<JavaUtilMap>)getWidgets;
- (BOOL)isHogFocus;
- (void)setHogFocusWithBoolean:(BOOL)hog;
@end

#define ComAppnativaRareViewerIContainer RAREiContainer

typedef enum {
  RAREiContainer_Layout_ABSOLUTE = 0,
  RAREiContainer_Layout_TABLE = 1,
  RAREiContainer_Layout_FORMS = 2,
  RAREiContainer_Layout_FLOW = 3,
  RAREiContainer_Layout_CUSTOM = 4,
} RAREiContainer_Layout;

@interface RAREiContainer_LayoutEnum : JavaLangEnum < NSCopying > {
}
+ (RAREiContainer_LayoutEnum *)ABSOLUTE;
+ (RAREiContainer_LayoutEnum *)TABLE;
+ (RAREiContainer_LayoutEnum *)FORMS;
+ (RAREiContainer_LayoutEnum *)FLOW;
+ (RAREiContainer_LayoutEnum *)CUSTOM;
+ (IOSObjectArray *)values;
+ (RAREiContainer_LayoutEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREiContainer_H_
