//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/aCanvas.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaCanvas_H_
#define _RAREaCanvas_H_

@class IOSObjectArray;
@class RAREUIImage;
@protocol RAREiContext;
@protocol RAREiPlatformComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/canvas/iCanvas.h"

@interface RAREaCanvas : NSObject < RAREiCanvas > {
 @public
  BOOL backgroundCanvas_;
  int height_;
  id<RAREiContext> theContext_;
  id<RAREiWidget> theWidget_;
  int width_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)w
              withBoolean:(BOOL)backgroundCanvas;
- (void)clear;
- (void)dispose;
- (void)repaint;
- (NSString *)toDataURLWithNSObjectArray:(IOSObjectArray *)args;
- (void)setHeightWithInt:(int)height;
- (void)setSizeWithInt:(int)width
               withInt:(int)height
           withBoolean:(BOOL)clear;
- (void)setWidthWithInt:(int)width;
- (id<RAREiPlatformComponent>)getCanvasComponent;
- (id<RAREiContext>)getContextWithNSString:(NSString *)type;
- (int)getHeight;
- (RAREUIImage *)getImageWithBoolean:(BOOL)copy_;
- (int)getWidth;
- (id<RAREiContext>)createContextWithNSString:(NSString *)type;
- (void)uninstall;
- (void)installWithRAREiWidget:(id<RAREiWidget>)w;
- (void)copyAllFieldsTo:(RAREaCanvas *)other;
@end

J2OBJC_FIELD_SETTER(RAREaCanvas, theContext_, id<RAREiContext>)
J2OBJC_FIELD_SETTER(RAREaCanvas, theWidget_, id<RAREiWidget>)

typedef RAREaCanvas ComAppnativaRareUiCanvasACanvas;

#endif // _RAREaCanvas_H_
