//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/iCanvasComponent.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiCanvasComponent_H_
#define _RAREiCanvasComponent_H_

@protocol RAREiContext;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"

@protocol RAREiCanvasComponent < NSObject, JavaObject >
- (id<RAREiPlatformComponent>)getPlatformComponent;
- (id<RAREiContext>)getContext;
- (void)setContextWithRAREiContext:(id<RAREiContext>)context;
@end

#define ComAppnativaRareUiCanvasICanvasComponent RAREiCanvasComponent

#endif // _RAREiCanvasComponent_H_
