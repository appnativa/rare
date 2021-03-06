//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUIMatteBorder.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaUIMatteBorder_H_
#define _RAREaUIMatteBorder_H_

@class RAREUIColor;
@class RAREUIInsets;
@class RAREUIStroke;
@protocol RAREiPlatformGraphics;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/aUIPlatformBorder.h"

@interface RAREaUIMatteBorder : RAREaUIPlatformBorder {
 @public
  RAREUIInsets *insets_;
  RAREUIStroke *lineStroke_;
  RAREUIColor *lineColor_;
}

- (id)initWithRAREUIInsets:(RAREUIInsets *)borderInsets
           withRAREUIColor:(RAREUIColor *)matteColor;
- (id)initWithFloat:(float)top
          withFloat:(float)right
          withFloat:(float)bottom
          withFloat:(float)left
    withRAREUIColor:(RAREUIColor *)matteColor;
- (void)clipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height;
- (id)clone;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)last;
- (void)setInsetsWithRAREUIInsets:(RAREUIInsets *)insets;
- (void)setInsetsWithInt:(int)top
                 withInt:(int)right
                 withInt:(int)bottom
                 withInt:(int)left;
- (void)setLineColorWithRAREUIColor:(RAREUIColor *)c;
- (void)setLineStyleWithNSString:(NSString *)style;
- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets;
- (RAREUIColor *)getLineColor;
- (BOOL)isPaintLast;
- (BOOL)isFocusAware;
- (void)setLineStrokeWithRAREUIStroke:(RAREUIStroke *)stroke;
- (RAREUIStroke *)getLineStroke;
- (RAREUIColor *)getDisabledLineColor;
- (RAREUIColor *)getPaintColorWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g;
- (void)copyAllFieldsTo:(RAREaUIMatteBorder *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUIMatteBorder, insets_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaUIMatteBorder, lineStroke_, RAREUIStroke *)
J2OBJC_FIELD_SETTER(RAREaUIMatteBorder, lineColor_, RAREUIColor *)

typedef RAREaUIMatteBorder ComAppnativaRareUiBorderAUIMatteBorder;

#endif // _RAREaUIMatteBorder_H_
