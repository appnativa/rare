//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UIBorderHelper.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREUIBorderHelper_H_
#define _RAREUIBorderHelper_H_

@class RARESPOTMargin;
@class RARESPOTWidget;
@class RAREUIColor;
@class RAREUIEmptyBorder;
@class SPOTSet;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"

@interface RAREUIBorderHelper : NSObject {
}

+ (RAREUIEmptyBorder *)EMPTY_BORDER;
+ (RAREUIEmptyBorder *)ONE_PIXEL_EMPTY_BORDER;
+ (RAREUIEmptyBorder *)TWO_PIXEL_EMPTY_BORDER;
+ (RAREUIEmptyBorder *)TWO_LEFT_PIXEL_EMPTY_BORDER;
+ (RAREUIEmptyBorder *)THREE_LEFT_PIXEL_EMPTY_BORDER;
+ (RAREUIEmptyBorder *)ONE_TOP_PIXEL_EMPTY_BORDER;
+ (RAREUIEmptyBorder *)ONE_LEFT_PIXEL_EMPTY_BORDER;
+ (RAREUIEmptyBorder *)ONE_BOTTOM_PIXEL_EMPTY_BORDER;
+ (id<RAREiPlatformBorder>)createBorderWithSPOTSet:(SPOTSet *)borders;
+ (id<RAREiPlatformBorder>)createBorderWithNSString:(NSString *)border;
+ (id<RAREiPlatformBorder>)createBorderWithSPOTSet:(SPOTSet *)borders
                           withRAREiPlatformBorder:(id<RAREiPlatformBorder>)standard;
+ (id<RAREiPlatformBorder>)createEmptyBorderWithRARESPOTMargin:(RARESPOTMargin *)m;
+ (RAREUIColor *)findBorderColorWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
+ (id<RAREiPlatformComponent>)setBorderTypeWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                        withRARESPOTWidget:(RARESPOTWidget *)cfg
                                               withBoolean:(BOOL)title
                                               withBoolean:(BOOL)margin;
+ (id<RAREiPlatformComponent>)setBorderTypeWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                               withSPOTSet:(SPOTSet *)borders
                                              withNSString:(NSString *)title
                                                   withInt:(int)tdisplay
                                   withRAREiPlatformBorder:(id<RAREiPlatformBorder>)margin
                                               withBoolean:(BOOL)lockable;
+ (id)getEmptyBorder;
- (id)init;
@end

typedef RAREUIBorderHelper ComAppnativaRareUiUIBorderHelper;

#endif // _RAREUIBorderHelper_H_
