//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-canvas/com/appnativa/rare/ui/canvas/CanvasComponent.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARECanvasComponent_H_
#define _RARECanvasComponent_H_

@class RAREAppleGraphics;
@class RAREUIRectangle;
@class RAREView;
@protocol RAREiContext;
@protocol RAREiPlatformComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/canvas/iCanvasComponent.h"

@interface RARECanvasComponent : RAREComponent < RAREiCanvasComponent > {
 @public
  id<RAREiContext> context_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)w;
- (void)setContextWithRAREiContext:(id<RAREiContext>)context;
- (id<RAREiContext>)getContext;
- (id<RAREiPlatformComponent>)getPlatformComponent;
- (void)copyAllFieldsTo:(RARECanvasComponent *)other;
@end

J2OBJC_FIELD_SETTER(RARECanvasComponent, context_, id<RAREiContext>)

typedef RARECanvasComponent ComAppnativaRareUiCanvasCanvasComponent;

@interface RARECanvasComponent_CanvasView : RAREParentView {
 @public
  id<RAREiContext> context_;
}

- (id)init;
- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect;
- (void)setContextWithRAREiContext:(id<RAREiContext>)context;
- (void)copyAllFieldsTo:(RARECanvasComponent_CanvasView *)other;
@end

J2OBJC_FIELD_SETTER(RARECanvasComponent_CanvasView, context_, id<RAREiContext>)

#endif // _RARECanvasComponent_H_
