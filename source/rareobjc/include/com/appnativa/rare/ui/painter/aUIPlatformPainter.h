//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/painter/aUIPlatformPainter.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaUIPlatformPainter_H_
#define _RAREaUIPlatformPainter_H_

@protocol RAREiPlatformGraphics;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/painter/aUIPainter.h"

@interface RAREaUIPlatformPainter : RAREaUIPainter {
}

- (id)init;
- (BOOL)canUseLayer;
- (BOOL)canUseMainLayer;
- (void)updateModCount;
- (void)fillWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height
                              withInt:(int)orientation;
@end

typedef RAREaUIPlatformPainter ComAppnativaRareUiPainterAUIPlatformPainter;

#endif // _RAREaUIPlatformPainter_H_
