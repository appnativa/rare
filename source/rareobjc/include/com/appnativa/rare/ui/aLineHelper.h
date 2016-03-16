//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aLineHelper.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaLineHelper_H_
#define _RAREaLineHelper_H_

@class RAREUIColor;
@class RAREUIStroke;
@class RAREaLineHelper_Line;
@class RAREaLineHelper_StrokeStyleEnum;
@protocol JavaUtilList;
@protocol RAREiPlatformGraphics;

#import "JreEmulation.h"
#include "java/lang/Enum.h"

@interface RAREaLineHelper : NSObject {
 @public
  BOOL horizontal_;
  float thickness_;
  int position_;
  id<JavaUtilList> lines_;
  int padding_;
}

+ (RAREaLineHelper_Line *)STANDARD_LINE;
+ (void)setSTANDARD_LINE:(RAREaLineHelper_Line *)STANDARD_LINE;
- (id)initWithBoolean:(BOOL)horizontal;
- (void)addLineWithRAREaLineHelper_Line:(RAREaLineHelper_Line *)line;
- (BOOL)isStandardLine;
- (void)dispose;
- (RAREaLineHelper_Line *)createLine;
- (RAREaLineHelper_Line *)createLineWithNSString:(NSString *)style
                                       withFloat:(float)thickness
                                 withRAREUIColor:(RAREUIColor *)color
                                         withInt:(int)loff
                                         withInt:(int)roff;
- (RAREaLineHelper_Line *)createLineWithRAREaLineHelper_StrokeStyleEnum:(RAREaLineHelper_StrokeStyleEnum *)style
                                                              withFloat:(float)thickness
                                                        withRAREUIColor:(RAREUIColor *)color
                                                                withInt:(int)loff
                                                                withInt:(int)roff;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)w
                             withFloat:(float)h;
- (RAREaLineHelper_Line *)removeLineWithInt:(int)pos;
- (void)removeLines;
- (void)setHorizontalWithBoolean:(BOOL)horizontal;
- (void)setPositionWithInt:(int)position;
- (RAREaLineHelper_Line *)getLineWithInt:(int)pos;
- (int)getPosition;
+ (RAREUIStroke *)getStrokeWithNSString:(NSString *)style
                              withFloat:(float)thickness;
+ (RAREUIStroke *)getStrokeWithRAREaLineHelper_StrokeStyleEnum:(RAREaLineHelper_StrokeStyleEnum *)style
                                                     withFloat:(float)thickness;
- (float)getThickness;
- (BOOL)hasValue;
- (BOOL)isHorizontal;
- (void)calculateThickness;
- (void)thicknessRecalculated;
- (int)getPadding;
- (void)setPaddingWithInt:(int)padding;
- (void)copyAllFieldsTo:(RAREaLineHelper *)other;
@end

J2OBJC_FIELD_SETTER(RAREaLineHelper, lines_, id<JavaUtilList>)

typedef RAREaLineHelper ComAppnativaRareUiALineHelper;

typedef enum {
  RAREaLineHelper_StrokeStyle_SOLID = 0,
  RAREaLineHelper_StrokeStyle_DOTTED = 1,
  RAREaLineHelper_StrokeStyle_DASHED = 2,
} RAREaLineHelper_StrokeStyle;

@interface RAREaLineHelper_StrokeStyleEnum : JavaLangEnum < NSCopying > {
}
+ (RAREaLineHelper_StrokeStyleEnum *)SOLID;
+ (RAREaLineHelper_StrokeStyleEnum *)DOTTED;
+ (RAREaLineHelper_StrokeStyleEnum *)DASHED;
+ (IOSObjectArray *)values;
+ (RAREaLineHelper_StrokeStyleEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface RAREaLineHelper_Line : NSObject {
 @public
  int leftOffset_;
  int rightOffset_;
  float thickness_;
  RAREUIColor *color_;
  BOOL standard_;
  RAREUIStroke *stroke_;
}

- (id)init;
- (id)initWithRAREaLineHelper_StrokeStyleEnum:(RAREaLineHelper_StrokeStyleEnum *)style
                                    withFloat:(float)thickness
                              withRAREUIColor:(RAREUIColor *)color
                                      withInt:(int)loff
                                      withInt:(int)roff;
- (void)paintHorizontalWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                       withFloat:(float)x
                                       withFloat:(float)y
                                       withFloat:(float)w;
- (void)paintStandardWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                     withFloat:(float)x
                                     withFloat:(float)y
                                     withFloat:(float)w
                                     withFloat:(float)h
                                   withBoolean:(BOOL)horizontal;
- (void)paintVerticalWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                     withFloat:(float)x
                                     withFloat:(float)y
                                     withFloat:(float)h;
- (void)setColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setLeftOffsetWithInt:(int)leftOffset;
- (void)setRightOffsetWithInt:(int)rightOffset;
- (void)setStyleWithRAREaLineHelper_StrokeStyleEnum:(RAREaLineHelper_StrokeStyleEnum *)style;
- (void)setThicknessWithFloat:(float)thickness;
- (RAREUIColor *)getColor;
- (int)getLeftOffset;
- (int)getRightOffset;
- (float)getThickness;
- (void)copyAllFieldsTo:(RAREaLineHelper_Line *)other;
@end

J2OBJC_FIELD_SETTER(RAREaLineHelper_Line, color_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaLineHelper_Line, stroke_, RAREUIStroke *)

#endif // _RAREaLineHelper_H_
