//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/painter/UICompoundPainter.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREUICompoundPainter_H_
#define _RAREUICompoundPainter_H_

@protocol RAREiPlatformPainter;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/painter/aUICompoundPainter.h"

@interface RAREUICompoundPainter : RAREaUICompoundPainter {
}

- (id)initWithRAREiPlatformPainter:(id<RAREiPlatformPainter>)firstPainter
          withRAREiPlatformPainter:(id<RAREiPlatformPainter>)secondPainter;
- (void)disposeEx;
- (BOOL)canUseMainLayer;
- (BOOL)canUseLayer;
@end

typedef RAREUICompoundPainter ComAppnativaRareUiPainterUICompoundPainter;

#endif // _RAREUICompoundPainter_H_