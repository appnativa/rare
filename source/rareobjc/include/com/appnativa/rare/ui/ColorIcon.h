//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/ColorIcon.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREColorIcon_H_
#define _RAREColorIcon_H_

@class RAREUIColor;
@class RAREUIImage;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aUIPlatformIcon.h"

@interface RAREColorIcon : RAREaUIPlatformIcon {
 @public
  int iconHeight_;
  int iconWidth_;
  RAREUIColor *color_;
}

+ (RAREUIImage *)nullColor;
+ (void)setNullColor:(RAREUIImage *)nullColor;
+ (int)imageHeight;
+ (int *)imageHeightRef;
+ (int)imageWidth;
+ (int *)imageWidthRef;
- (id)initWithRAREUIColor:(RAREUIColor *)color;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height;
- (void)setColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setIconHeightWithInt:(int)iconHeight;
- (void)setIconWidthWithInt:(int)iconWidth;
- (RAREUIColor *)getColor;
- (id<RAREiPlatformIcon>)getDisabledVersion;
- (int)getIconHeight;
- (int)getIconWidth;
- (void)copyAllFieldsTo:(RAREColorIcon *)other;
@end

J2OBJC_FIELD_SETTER(RAREColorIcon, color_, RAREUIColor *)

typedef RAREColorIcon ComAppnativaRareUiColorIcon;

#endif // _RAREColorIcon_H_
