//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/painter/iPlatformPainter.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiPlatformPainter_H_
#define _RAREiPlatformPainter_H_

@protocol RAREiPlatformGraphics;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/painter/iPainter.h"

@protocol RAREiPlatformPainter < RAREiPainter, NSObject, JavaObject >
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                               withInt:(int)orientation;
- (BOOL)canUseMainLayer;
- (BOOL)canUseLayer;
- (int)getModCount;
- (BOOL)isSingleColorPainter;
@end

#define ComAppnativaRareUiPainterIPlatformPainter RAREiPlatformPainter

#endif // _RAREiPlatformPainter_H_
