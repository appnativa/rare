//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUIEtchedBorder.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREaUIEtchedBorder_H_
#define _RAREaUIEtchedBorder_H_

@class RAREUIColor;
@class RAREUIInsets;
@protocol RAREiPlatformGraphics;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/aUIPlatformBorder.h"

#define RAREaUIEtchedBorder_LOWERED 1
#define RAREaUIEtchedBorder_RAISED 0

@interface RAREaUIEtchedBorder : RAREaUIPlatformBorder {
 @public
  int etchType_;
  RAREUIColor *highlight_;
  RAREUIColor *shadow_;
}

+ (int)LOWERED;
+ (int)RAISED;
+ (float)INSETS_SIZE;
- (id)init;
- (id)initWithInt:(int)etchType;
- (id)initWithRAREUIColor:(RAREUIColor *)highlightColor
          withRAREUIColor:(RAREUIColor *)shadowColor;
- (id)initWithInt:(int)etchType
  withRAREUIColor:(RAREUIColor *)highlightColor
  withRAREUIColor:(RAREUIColor *)shadowColor;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)last;
- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets;
- (int)getEtchType;
- (RAREUIColor *)getHighlightColor;
- (RAREUIColor *)getShadowColor;
- (void)copyAllFieldsTo:(RAREaUIEtchedBorder *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUIEtchedBorder, highlight_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaUIEtchedBorder, shadow_, RAREUIColor *)

typedef RAREaUIEtchedBorder ComAppnativaRareUiBorderAUIEtchedBorder;

#endif // _RAREaUIEtchedBorder_H_
