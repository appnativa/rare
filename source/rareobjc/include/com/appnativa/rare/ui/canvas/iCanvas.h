//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/iCanvas.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiCanvas_H_
#define _RAREiCanvas_H_

@class IOSObjectArray;
@class RAREUIImage;
@protocol RAREiContext;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"

@protocol RAREiCanvas < NSObject, JavaObject >
- (id<RAREiPlatformComponent>)getCanvasComponent;
- (void)dispose;
- (void)repaint;
- (void)clear;
- (NSString *)toDataURLWithNSObjectArray:(IOSObjectArray *)args;
- (void)setHeightWithInt:(int)height;
- (void)setSizeWithInt:(int)width
               withInt:(int)height
           withBoolean:(BOOL)clear;
- (void)setWidthWithInt:(int)width;
- (id<RAREiContext>)getContextWithNSString:(NSString *)type;
- (int)getHeight;
- (RAREUIImage *)getImageWithBoolean:(BOOL)copy_;
- (int)getWidth;
@end

#define ComAppnativaRareUiCanvasICanvas RAREiCanvas

#endif // _RAREiCanvas_H_
