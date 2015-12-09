//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUIBevelBorder.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaUIBevelBorder_H_
#define _RAREaUIBevelBorder_H_

@class RAREUIBevelBorder;
@class RAREUIColor;
@class RAREUIInsets;
@protocol RAREiPlatformGraphics;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/aUIPlatformBorder.h"

#define RAREaUIBevelBorder_LOWERED 0
#define RAREaUIBevelBorder_RAISED 1

@interface RAREaUIBevelBorder : RAREaUIPlatformBorder {
 @public
  int arcHeight_;
  int arcWidth_;
  float thickness_;
  RAREUIInsets *mainInsets_;
  int bevelType_;
  BOOL flatBottom_;
  RAREUIColor *highlightInner_;
  RAREUIColor *highlightOuter_;
  RAREUIColor *shadowInner_;
  RAREUIColor *shadowOuter_;
  int colorAlpha_;
  BOOL fourColorBevel_;
  RAREUIInsets *padding_;
}

+ (int)LOWERED;
+ (int)RAISED;
+ (RAREUIBevelBorder *)sharedBevelLoweredBorder;
+ (void)setSharedBevelLoweredBorder:(RAREUIBevelBorder *)sharedBevelLoweredBorder;
+ (RAREUIBevelBorder *)sharedBevelRaisedBorder;
+ (void)setSharedBevelRaisedBorder:(RAREUIBevelBorder *)sharedBevelRaisedBorder;
+ (RAREUIBevelBorder *)sharedLoweredBorder;
+ (void)setSharedLoweredBorder:(RAREUIBevelBorder *)sharedLoweredBorder;
+ (RAREUIBevelBorder *)sharedRaisedBorder;
+ (void)setSharedRaisedBorder:(RAREUIBevelBorder *)sharedRaisedBorder;
- (id)initWithInt:(int)bevelType;
- (id)initWithInt:(int)bevelType
      withBoolean:(BOOL)fourcolor;
- (id)initWithInt:(int)bevelType
  withRAREUIColor:(RAREUIColor *)highlight
  withRAREUIColor:(RAREUIColor *)shadow
      withBoolean:(BOOL)fourColor;
- (id)initWithInt:(int)bevelType
  withRAREUIColor:(RAREUIColor *)highlightOuterColor
  withRAREUIColor:(RAREUIColor *)highlightInnerColor
  withRAREUIColor:(RAREUIColor *)shadowOuterColor
  withRAREUIColor:(RAREUIColor *)shadowInnerColor;
- (id)initWithInt:(int)bevelType
  withRAREUIColor:(RAREUIColor *)highlightOuterColor
  withRAREUIColor:(RAREUIColor *)highlightInnerColor
  withRAREUIColor:(RAREUIColor *)shadowOuterColor
  withRAREUIColor:(RAREUIColor *)shadowInnerColor
      withBoolean:(BOOL)fourColor;
- (id)clone;
+ (RAREUIBevelBorder *)createBorderWithInt:(int)bevelType
                               withBoolean:(BOOL)fourcolor;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)last;
+ (void)resetBorderCache;
- (void)setColorAlphaWithInt:(int)colorAlpha;
- (void)setInsetsPaddingWithFloat:(float)top
                        withFloat:(float)right
                        withFloat:(float)bottom
                        withFloat:(float)left;
- (void)setColorsWithRAREUIColor:(RAREUIColor *)so
                 withRAREUIColor:(RAREUIColor *)ho;
- (void)setColorsWithRAREUIColor:(RAREUIColor *)so
                 withRAREUIColor:(RAREUIColor *)si
                 withRAREUIColor:(RAREUIColor *)ho
                 withRAREUIColor:(RAREUIColor *)hi;
- (void)setFlatBottomWithBoolean:(BOOL)flat;
- (void)setFourColorBevelWithBoolean:(BOOL)fourcolor;
- (void)setInsetsWithRAREUIInsets:(RAREUIInsets *)inArg;
- (void)setNoBottomWithBoolean:(BOOL)noBottom;
- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets;
- (RAREUIInsets *)getBorderInsetsExWithRAREUIInsets:(RAREUIInsets *)insets;
- (int)getColorAlpha;
- (RAREUIColor *)getHighlightInnerColor;
- (RAREUIColor *)getHighlightOuterColor;
- (RAREUIColor *)getShadowInnerColor;
- (RAREUIColor *)getShadowOuterColor;
- (BOOL)isFourColorBevel;
- (BOOL)isPadForArc;
- (BOOL)isPaintLast;
- (void)paintBevelWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                  withFloat:(float)x
                                  withFloat:(float)y
                                  withFloat:(float)w
                                  withFloat:(float)h
                            withRAREUIColor:(RAREUIColor *)top
                            withRAREUIColor:(RAREUIColor *)bottom
                            withRAREUIColor:(RAREUIColor *)top2
                            withRAREUIColor:(RAREUIColor *)bottom2;
- (void)paintRaisedBevelWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                        withFloat:(float)x
                                        withFloat:(float)y
                                        withFloat:(float)w
                                        withFloat:(float)h;
- (void)paintLoweredBevelWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                         withFloat:(float)x
                                         withFloat:(float)y
                                         withFloat:(float)w
                                         withFloat:(float)h;
- (void)paintLinesWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                  withFloat:(float)x
                                  withFloat:(float)y
                                  withFloat:(float)w
                                  withFloat:(float)h
                            withRAREUIColor:(RAREUIColor *)top
                            withRAREUIColor:(RAREUIColor *)bottom
                            withRAREUIColor:(RAREUIColor *)top2
                            withRAREUIColor:(RAREUIColor *)bottom2
                                    withInt:(int)ah
                                    withInt:(int)aw
                                withBoolean:(BOOL)drawtop;
- (void)copyAllFieldsTo:(RAREaUIBevelBorder *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUIBevelBorder, mainInsets_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaUIBevelBorder, highlightInner_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaUIBevelBorder, highlightOuter_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaUIBevelBorder, shadowInner_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaUIBevelBorder, shadowOuter_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaUIBevelBorder, padding_, RAREUIInsets *)

typedef RAREaUIBevelBorder ComAppnativaRareUiBorderAUIBevelBorder;

#endif // _RAREaUIBevelBorder_H_
