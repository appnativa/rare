//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-canvas/com/appnativa/rare/ui/canvas/Canvas.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARECanvas_H_
#define _RARECanvas_H_

@class IOSObjectArray;
@protocol RAREiContext;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/canvas/aCanvas.h"

@interface RARECanvas : RAREaCanvas {
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)w
              withBoolean:(BOOL)backgroundCanvas;
- (NSString *)toDataURLWithNSObjectArray:(IOSObjectArray *)args;
- (id<RAREiContext>)createContextWithNSString:(NSString *)type;
@end

typedef RARECanvas ComAppnativaRareUiCanvasCanvas;

#endif // _RARECanvas_H_
