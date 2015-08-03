//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUIBalloonBorder.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaUIBalloonBorder_H_
#define _RAREaUIBalloonBorder_H_

@class RAREUIColor;
@class RAREUIInsets;
@class RAREUIRectangle;
@class RAREaUIBalloonBorder_PeakLocationEnum;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformPath;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/UILineBorder.h"
#include "java/lang/Enum.h"

@interface RAREaUIBalloonBorder : RAREUILineBorder {
 @public
  float peakSize_;
  float origPeakSize_;
  RAREaUIBalloonBorder_PeakLocationEnum *peakLocation_;
  float peakOffset_;
  BOOL autoLocatePeak_;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arc;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arc
withRAREaUIBalloonBorder_PeakLocationEnum:(RAREaUIBalloonBorder_PeakLocationEnum *)pl;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arc
withRAREaUIBalloonBorder_PeakLocationEnum:(RAREaUIBalloonBorder_PeakLocationEnum *)pl
                withFloat:(float)peakSize;
- (id)clone;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)last;
- (void)setPeakLocationWithRAREaUIBalloonBorder_PeakLocationEnum:(RAREaUIBalloonBorder_PeakLocationEnum *)peakLocation;
- (void)setPeakOffsetWithFloat:(float)peakOffset;
- (void)setPeakSizeWithFloat:(float)peakSize;
- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets;
- (RAREaUIBalloonBorder_PeakLocationEnum *)getPeakLocation;
- (float)getPeakOffset;
- (float)getPeakSize;
- (BOOL)isPaintLast;
- (BOOL)isRectangular;
- (RAREUIInsets *)adjustForPeakWithRAREUIInsets:(RAREUIInsets *)insets;
- (id<RAREiPlatformPath>)createBorderPathWithRAREiPlatformPath:(id<RAREiPlatformPath>)p
                                                     withFloat:(float)x
                                                     withFloat:(float)y
                                                     withFloat:(float)width
                                                     withFloat:(float)height
                                                     withFloat:(float)aw
                                                     withFloat:(float)ah
                                                   withBoolean:(BOOL)clip;
- (RAREUIColor *)getColor;
- (BOOL)isAutoLocatePeak;
- (void)autoLocatePeakForProposedBoundsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)owner
                                              withRAREUIRectangle:(RAREUIRectangle *)r;
- (void)setAutoLocatePeakWithBoolean:(BOOL)autoLocatePeak;
- (void)copyAllFieldsTo:(RAREaUIBalloonBorder *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUIBalloonBorder, peakLocation_, RAREaUIBalloonBorder_PeakLocationEnum *)

typedef RAREaUIBalloonBorder ComAppnativaRareUiBorderAUIBalloonBorder;

typedef enum {
  RAREaUIBalloonBorder_PeakLocation_LL_BOTTOM = 0,
  RAREaUIBalloonBorder_PeakLocation_LL_LEFT = 1,
  RAREaUIBalloonBorder_PeakLocation_LR_RIGHT = 2,
  RAREaUIBalloonBorder_PeakLocation_LR_BOTTOM = 3,
  RAREaUIBalloonBorder_PeakLocation_UL_TOP = 4,
  RAREaUIBalloonBorder_PeakLocation_UL_LEFT = 5,
  RAREaUIBalloonBorder_PeakLocation_UR_TOP = 6,
  RAREaUIBalloonBorder_PeakLocation_UR_RIGHT = 7,
} RAREaUIBalloonBorder_PeakLocation;

@interface RAREaUIBalloonBorder_PeakLocationEnum : JavaLangEnum < NSCopying > {
}
+ (RAREaUIBalloonBorder_PeakLocationEnum *)LL_BOTTOM;
+ (RAREaUIBalloonBorder_PeakLocationEnum *)LL_LEFT;
+ (RAREaUIBalloonBorder_PeakLocationEnum *)LR_RIGHT;
+ (RAREaUIBalloonBorder_PeakLocationEnum *)LR_BOTTOM;
+ (RAREaUIBalloonBorder_PeakLocationEnum *)UL_TOP;
+ (RAREaUIBalloonBorder_PeakLocationEnum *)UL_LEFT;
+ (RAREaUIBalloonBorder_PeakLocationEnum *)UR_TOP;
+ (RAREaUIBalloonBorder_PeakLocationEnum *)UR_RIGHT;
+ (IOSObjectArray *)values;
+ (RAREaUIBalloonBorder_PeakLocationEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREaUIBalloonBorder_H_
