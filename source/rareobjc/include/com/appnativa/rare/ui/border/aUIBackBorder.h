//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUIBackBorder.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaUIBackBorder_H_
#define _RAREaUIBackBorder_H_

@class RAREUIColor;
@class RAREUIInsets;
@class RAREaUILineBorder;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformPath;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/UILineBorder.h"

@interface RAREaUIBackBorder : RAREUILineBorder {
}

- (id)initWithRAREUIColor:(RAREUIColor *)color;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arc;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arcWidth
                withFloat:(float)arcHeight;
- (void)setCornerArcWithFloat:(float)arc;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)end;
- (RAREaUILineBorder *)setThicknessWithFloat:(float)thickness;
- (id<RAREiPlatformPath>)createBorderPathWithRAREiPlatformPath:(id<RAREiPlatformPath>)p
                                                     withFloat:(float)x
                                                     withFloat:(float)y
                                                     withFloat:(float)width
                                                     withFloat:(float)height
                                                     withFloat:(float)aw
                                                     withFloat:(float)ah
                                                   withBoolean:(BOOL)clip;
- (RAREUIInsets *)calculateInsetsWithRAREUIInsets:(RAREUIInsets *)insets
                                      withBoolean:(BOOL)pad;
@end

typedef RAREaUIBackBorder ComAppnativaRareUiBorderAUIBackBorder;

#endif // _RAREaUIBackBorder_H_
