//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/DragHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREDragHandler_H_
#define _RAREDragHandler_H_

@class RAREMouseEvent;
@class RAREWindowViewer;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"
#include "com/appnativa/rare/ui/listener/iMouseMotionListener.h"

@interface RAREDragHandler : NSObject < RAREiMouseMotionListener, RAREiMouseListener > {
 @public
  float startX_;
  float startY_;
  float winX_;
  float winY_;
  __weak RAREWindowViewer *window_;
}

- (id)initWithRAREWindowViewer:(RAREWindowViewer *)windowViewer;
- (void)addAsWindowDraggerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)dispose;
- (void)mouseDraggedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)removeAsWindowDraggerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (BOOL)wantsLongPress;
- (BOOL)wantsMouseMovedEvents;
- (void)copyAllFieldsTo:(RAREDragHandler *)other;
@end

typedef RAREDragHandler ComAppnativaRarePlatformAppleUiDragHandler;

#endif // _RAREDragHandler_H_
