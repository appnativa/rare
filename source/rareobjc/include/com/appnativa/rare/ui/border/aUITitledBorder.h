//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUITitledBorder.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaUITitledBorder_H_
#define _RAREaUITitledBorder_H_

@class RAREUIColor;
@class RAREUIFont;
@class RAREUIInsets;
@class RAREUIRectangle;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformShape;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/aUIPlatformBorder.h"

#define RAREaUITitledBorder_ABOVE_BOTTOM 4
#define RAREaUITitledBorder_ABOVE_TOP 1
#define RAREaUITitledBorder_BELOW_BOTTOM 6
#define RAREaUITitledBorder_BELOW_TOP 3
#define RAREaUITitledBorder_BOTTOM 5
#define RAREaUITitledBorder_CENTER 2
#define RAREaUITitledBorder_DEFAULT_JUSTIFICATION 0
#define RAREaUITitledBorder_DEFAULT_POSITION 0
#define RAREaUITitledBorder_LEADING 4
#define RAREaUITitledBorder_LEFT 1
#define RAREaUITitledBorder_RIGHT 3
#define RAREaUITitledBorder_TOP 2
#define RAREaUITitledBorder_TRAILING 5

@interface RAREaUITitledBorder : RAREaUIPlatformBorder {
 @public
  NSString *title_;
  id<RAREiPlatformBorder> titleBorder_;
  RAREUIColor *titleColor_;
  RAREUIFont *titleFont_;
  int titleJustification_;
  int titlePosition_;
}

+ (int)ABOVE_BOTTOM;
+ (int)ABOVE_TOP;
+ (int)BELOW_BOTTOM;
+ (int)BELOW_TOP;
+ (int)BOTTOM;
+ (int)CENTER;
+ (int)DEFAULT_JUSTIFICATION;
+ (int)DEFAULT_POSITION;
+ (int)LEADING;
+ (int)LEFT;
+ (int)RIGHT;
+ (int)TOP;
+ (int)TRAILING;
+ (float)EDGE_SPACING;
+ (float)TEXT_SPACING;
+ (float)TEXT_HORIZONTAL_SPACING;
+ (id<RAREiPlatformBorder>)defaultBorder;
+ (void)setDefaultBorder:(id<RAREiPlatformBorder>)defaultBorder;
- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
- (id)initWithNSString:(NSString *)title;
- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border
                     withNSString:(NSString *)title;
- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border
                     withNSString:(NSString *)title
                          withInt:(int)titleJustification
                          withInt:(int)titlePosition;
- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border
                     withNSString:(NSString *)title
                          withInt:(int)titleJustification
                          withInt:(int)titlePosition
                   withRAREUIFont:(RAREUIFont *)titleFont;
- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border
                     withNSString:(NSString *)title
                          withInt:(int)titleJustification
                          withInt:(int)titlePosition
                   withRAREUIFont:(RAREUIFont *)titleFont
                  withRAREUIColor:(RAREUIColor *)titleColor;
- (void)clipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height;
- (BOOL)clipsContents;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)last;
+ (int)toTitlePositionWithNSString:(NSString *)title;
- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
- (void)setTitleWithNSString:(NSString *)title;
- (void)setTitleColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setTitleFontWithRAREUIFont:(RAREUIFont *)font;
- (void)setTitleJustificationWithInt:(int)justification;
- (void)setTitleLocationWithNSString:(NSString *)loc;
- (void)setTitlePositionWithInt:(int)pos;
- (id<RAREiPlatformBorder>)getBorder;
- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets;
- (id<RAREiPlatformShape>)getShapeWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                  withFloat:(float)x
                                                  withFloat:(float)y
                                                  withFloat:(float)width
                                                  withFloat:(float)height;
- (NSString *)getTitle;
- (RAREUIColor *)getTitleColor;
- (RAREUIFont *)getTitleFont;
- (int)getTitleJustification;
- (int)getTitlePosition;
- (BOOL)isBorderOpaque;
- (BOOL)isPaintLast;
- (BOOL)isRectangular;
+ (int)convertLeadTrailWithInt:(int)pos;
- (void)paintIinnerBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b
                       withRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                       withFloat:(float)x
                                       withFloat:(float)y
                                       withFloat:(float)width
                                       withFloat:(float)height
                                     withBoolean:(BOOL)end;
- (id<RAREiPlatformBorder>)getDefaultBorder;
+ (RAREUIColor *)getDefaultTitleColor;
+ (RAREUIFont *)getDefaultTitleFont;
- (RAREUIRectangle *)getInnerRectWithRAREUIRectangle:(RAREUIRectangle *)r
                                           withFloat:(float)x
                                           withFloat:(float)y
                                           withFloat:(float)width
                                           withFloat:(float)height;
- (void)copyAllFieldsTo:(RAREaUITitledBorder *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUITitledBorder, title_, NSString *)
J2OBJC_FIELD_SETTER(RAREaUITitledBorder, titleBorder_, id<RAREiPlatformBorder>)
J2OBJC_FIELD_SETTER(RAREaUITitledBorder, titleColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaUITitledBorder, titleFont_, RAREUIFont *)

typedef RAREaUITitledBorder ComAppnativaRareUiBorderAUITitledBorder;

#endif // _RAREaUITitledBorder_H_
