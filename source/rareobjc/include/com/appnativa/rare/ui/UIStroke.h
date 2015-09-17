//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UIStroke.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREUIStroke_H_
#define _RAREUIStroke_H_

@class IOSFloatArray;
@class RAREUIStroke_CapEnum;
@class RAREUIStroke_JoinEnum;

#import "JreEmulation.h"
#include "java/lang/Enum.h"

@interface RAREUIStroke : NSObject < NSCopying > {
 @public
  RAREUIStroke_CapEnum *cap_;
  RAREUIStroke_JoinEnum *join_;
  float miterLimit_;
  float width_;
  IOSFloatArray *dashIntervals_;
  float dashPhase_;
}

+ (RAREUIStroke *)SOLID_STROKE;
+ (RAREUIStroke *)DOTTED_STROKE;
+ (RAREUIStroke *)DASHED_STROKE;
+ (RAREUIStroke *)HALF_SOLID_STROKE;
+ (RAREUIStroke *)HALF_DOTTED_STROKE;
+ (RAREUIStroke *)HALF_DASHED_STROKE;
- (id)init;
- (id)initWithFloat:(float)width;
- (id)initWithFloat:(float)width
withRAREUIStroke_CapEnum:(RAREUIStroke_CapEnum *)cap
withRAREUIStroke_JoinEnum:(RAREUIStroke_JoinEnum *)join;
- (id)initWithFloat:(float)width
     withFloatArray:(IOSFloatArray *)dashIntervals
          withFloat:(float)dashhase;
- (id)initWithFloat:(float)width
withRAREUIStroke_CapEnum:(RAREUIStroke_CapEnum *)cap
withRAREUIStroke_JoinEnum:(RAREUIStroke_JoinEnum *)join
          withFloat:(float)miterlimit;
- (id)initWithFloat:(float)width
withRAREUIStroke_CapEnum:(RAREUIStroke_CapEnum *)cap
withRAREUIStroke_JoinEnum:(RAREUIStroke_JoinEnum *)join
     withFloatArray:(IOSFloatArray *)dashIntervals
          withFloat:(float)dashhase;
- (id)clone;
- (BOOL)isEqual:(id)o;
- (void)setDashIntervalWithFloatArray:(IOSFloatArray *)intervals
                            withFloat:(float)phase;
- (void)setWidthWithFloat:(float)width;
+ (RAREUIStroke *)getStrokeWithNSString:(NSString *)style;
- (BOOL)isEqualButForWidthWithRAREUIStroke:(RAREUIStroke *)stroke;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RAREUIStroke *)other;
@end

J2OBJC_FIELD_SETTER(RAREUIStroke, cap_, RAREUIStroke_CapEnum *)
J2OBJC_FIELD_SETTER(RAREUIStroke, join_, RAREUIStroke_JoinEnum *)
J2OBJC_FIELD_SETTER(RAREUIStroke, dashIntervals_, IOSFloatArray *)

typedef RAREUIStroke ComAppnativaRareUiUIStroke;

typedef enum {
  RAREUIStroke_Cap_BUTT = 0,
  RAREUIStroke_Cap_ROUND = 1,
  RAREUIStroke_Cap_SQUARE = 2,
} RAREUIStroke_Cap;

@interface RAREUIStroke_CapEnum : JavaLangEnum < NSCopying > {
}
+ (RAREUIStroke_CapEnum *)BUTT;
+ (RAREUIStroke_CapEnum *)ROUND;
+ (RAREUIStroke_CapEnum *)SQUARE;
+ (IOSObjectArray *)values;
+ (RAREUIStroke_CapEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

typedef enum {
  RAREUIStroke_Join_BEVEL = 0,
  RAREUIStroke_Join_ROUND = 1,
  RAREUIStroke_Join_MITER = 2,
} RAREUIStroke_Join;

@interface RAREUIStroke_JoinEnum : JavaLangEnum < NSCopying > {
}
+ (RAREUIStroke_JoinEnum *)BEVEL;
+ (RAREUIStroke_JoinEnum *)ROUND;
+ (RAREUIStroke_JoinEnum *)MITER;
+ (IOSObjectArray *)values;
+ (RAREUIStroke_JoinEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREUIStroke_H_
