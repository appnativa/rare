//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/viewer/CanvasViewer.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARECanvasViewer_H_
#define _RARECanvasViewer_H_

@class IOSObjectArray;
@class RARESPOTViewer;
@class RAREUIImage;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol RAREiCanvasComponent;
@protocol RAREiContainer;
@protocol RAREiContext;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/canvas/iCanvas.h"
#include "com/appnativa/rare/viewer/aPlatformViewer.h"

@interface RARECanvasViewer : RAREaPlatformViewer < RAREiCanvas > {
 @public
  id<RAREiCanvasComponent> canvasComponent_;
  id<RAREiCanvas> realCanvas_;
  NSString *contextType_;
  RAREiImagePainter_ScalingTypeEnum *scalingType_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)clearContents;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)dispose;
- (NSString *)toDataURLWithNSObjectArray:(IOSObjectArray *)args;
- (void)setHeightWithInt:(int)height;
- (void)setSizeWithInt:(int)width
               withInt:(int)height;
- (void)setSizeWithInt:(int)width
               withInt:(int)height
           withBoolean:(BOOL)clear;
- (void)setWidthWithInt:(int)width;
- (id<RAREiPlatformComponent>)getCanvasComponent;
- (id<RAREiContext>)getContextWithNSString:(NSString *)id_;
- (NSString *)getContextType;
- (RAREUIImage *)getImageWithBoolean:(BOOL)copy_;
+ (void)registerForUse;
- (void)copyAllFieldsTo:(RARECanvasViewer *)other;
@end

J2OBJC_FIELD_SETTER(RARECanvasViewer, canvasComponent_, id<RAREiCanvasComponent>)
J2OBJC_FIELD_SETTER(RARECanvasViewer, realCanvas_, id<RAREiCanvas>)
J2OBJC_FIELD_SETTER(RARECanvasViewer, contextType_, NSString *)
J2OBJC_FIELD_SETTER(RARECanvasViewer, scalingType_, RAREiImagePainter_ScalingTypeEnum *)

typedef RARECanvasViewer ComAppnativaRareViewerCanvasViewer;

#endif // _RARECanvasViewer_H_
