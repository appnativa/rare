//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/painter/UIImagePainter.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREUIImagePainter_H_
#define _RAREUIImagePainter_H_

@class RAREDisplayedEnum;
@class RARERenderTypeEnum;
@class RAREUIImage;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/painter/aUIImagePainter.h"

@interface RAREUIImagePainter : RAREaUIImagePainter {
}

- (id)init;
- (id)initWithRAREUIImage:(RAREUIImage *)image;
- (id)initWithRAREUIImage:(RAREUIImage *)image
                  withInt:(int)opacity
   withRARERenderTypeEnum:(RARERenderTypeEnum *)type;
- (id)initWithRAREUIImage:(RAREUIImage *)image
                  withInt:(int)opacity
   withRARERenderTypeEnum:(RARERenderTypeEnum *)type
    withRAREDisplayedEnum:(RAREDisplayedEnum *)displayed;
- (BOOL)canUseLayer;
@end

typedef RAREUIImagePainter ComAppnativaRareUiPainterUIImagePainter;

#endif // _RAREUIImagePainter_H_
