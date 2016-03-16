//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/BorderLayoutView.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREBorderLayoutView_H_
#define _RAREBorderLayoutView_H_

@class IOSObjectArray;
@class RARELocationEnum;
@class RAREUIDimension;
@class RAREUIInsets;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/FormsView.h"

@interface RAREBorderLayoutView : RAREFormsView {
 @public
  BOOL topBottomPriority_;
  RAREUIInsets *padding_;
  BOOL useCrossPattern_;
}

+ (IOSObjectArray *)specs;
+ (void)setSpecs:(IOSObjectArray *)specs;
- (id)init;
- (id)initWithId:(id)proxy;
- (id)initWithId:(id)proxy
    withNSString:(NSString *)encodedColumnSpecs
    withNSString:(NSString *)encodedRowSpecs;
- (id)initWithNSString:(NSString *)encodedColumnSpecs
          withNSString:(NSString *)encodedRowSpecs;
- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                 withRARELocationEnum:(RARELocationEnum *)constraints;
- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withId:(id)constraints
                              withInt:(int)position;
- (void)adjustSizeForPaddingWithRAREUIDimension:(RAREUIDimension *)size;
- (id<RAREiPlatformComponent>)getBottomView;
- (id<RAREiPlatformComponent>)getCenterView;
- (id<RAREiPlatformComponent>)getComponentAtWithRARELocationEnum:(RARELocationEnum *)location;
- (id<RAREiPlatformComponent>)getLeftView;
- (id<RAREiPlatformComponent>)getRightView;
- (id<RAREiPlatformComponent>)getTopView;
- (void)removeAtLocationWithRARELocationEnum:(RARELocationEnum *)loc;
- (void)setBottomViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setCenterViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setLeftViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setPaddingWithRAREUIInsets:(RAREUIInsets *)inArg;
- (void)setRightViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setTopBottomPriorityWithBoolean:(BOOL)topBottomPriority;
- (void)setTopViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setUseCrossPatternWithBoolean:(BOOL)useCrossPattern;
- (void)copyAllFieldsTo:(RAREBorderLayoutView *)other;
@end

J2OBJC_FIELD_SETTER(RAREBorderLayoutView, padding_, RAREUIInsets *)

typedef RAREBorderLayoutView ComAppnativaRarePlatformAppleUiViewBorderLayoutView;

#endif // _RAREBorderLayoutView_H_
