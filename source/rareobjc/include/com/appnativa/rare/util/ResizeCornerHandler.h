//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/util/ResizeCornerHandler.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREResizeCornerHandler_H_
#define _RAREResizeCornerHandler_H_

@class RAREMouseEvent;
@class RAREUIRectangle;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"
#include "com/appnativa/rare/ui/listener/iMouseMotionListener.h"

@interface RAREResizeCornerHandler : NSObject < RAREiMouseListener, RAREiMouseMotionListener > {
 @public
  RAREUIRectangle *screenSize_;
  float startX_;
  float startY_;
  float winX_;
  float winY_;
  id<RAREiPlatformComponent> windowComponent_;
}

- (void)addAsWindowResizerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)dispose;
- (void)mouseDraggedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)removeAsWindowResizerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (BOOL)wantsLongPress;
- (BOOL)wantsMouseMovedEvents;
- (id)init;
- (void)copyAllFieldsTo:(RAREResizeCornerHandler *)other;
@end

J2OBJC_FIELD_SETTER(RAREResizeCornerHandler, screenSize_, RAREUIRectangle *)
J2OBJC_FIELD_SETTER(RAREResizeCornerHandler, windowComponent_, id<RAREiPlatformComponent>)

typedef RAREResizeCornerHandler ComAppnativaRareUtilResizeCornerHandler;

#endif // _RAREResizeCornerHandler_H_