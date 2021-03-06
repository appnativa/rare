//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/util/ResizeCornerHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIScreen.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/util/ResizeCornerHandler.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "java/lang/Math.h"

@implementation RAREResizeCornerHandler

- (void)addAsWindowResizerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if (c != nil) {
    [c removeMouseMotionListenerWithRAREiMouseMotionListener:self];
    [c addMouseMotionListenerWithRAREiMouseMotionListener:self];
    [c removeMouseListenerWithRAREiMouseListener:self];
    [c addMouseListenerWithRAREiMouseListener:self];
  }
}

- (void)dispose {
  windowComponent_ = nil;
}

- (void)mouseDraggedWithRAREMouseEvent:(RAREMouseEvent *)e {
  if (windowComponent_ == nil) {
    return;
  }
  RAREUIPoint *p = [((RAREMouseEvent *) nil_chk(e)) getLocationOnScreen];
  if (p == nil) {
    return;
  }
  float xCoord = ((RAREUIPoint *) nil_chk(p))->x_;
  float yCoord = p->y_;
  float dx = (xCoord - startX_);
  float dy = (yCoord - startY_);
  RAREUIDimension *d = [[RAREUIDimension alloc] initWithRAREUIDimension:[((id<RAREiPlatformComponent>) nil_chk(windowComponent_)) getSize]];
  id<RAREiPlatformComponent> source = [e getComponent];
  d->width_ += dx;
  d->height_ += dy;
  if (d->height_ + winY_ > ((RAREUIRectangle *) nil_chk(screenSize_))->height_) {
    d->height_ = screenSize_->height_ - winY_;
  }
  if (d->width_ + winX_ > screenSize_->width_) {
    d->width_ = screenSize_->width_ - winX_;
  }
  d->height_ = [JavaLangMath maxWithFloat:d->height_ withFloat:50 + [((id<RAREiPlatformComponent>) nil_chk(source)) getWidth]];
  d->width_ = [JavaLangMath maxWithFloat:d->width_ withFloat:50 + [source getHeight]];
  if ((d->width_ != [windowComponent_ getWidth]) || (d->height_ != [windowComponent_ getHeight])) {
    [windowComponent_ setSizeWithFloat:[RAREUIScreen snapToSizeWithFloat:d->width_] withFloat:[RAREUIScreen snapToSizeWithFloat:d->height_]];
  }
}

- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e {
  windowComponent_ = nil;
  RAREUIPoint *p = [((RAREMouseEvent *) nil_chk(e)) getLocationOnScreen];
  RAREWindowViewer *w = [RAREPlatform getWindowViewerWithRAREiPlatformComponent:[e getComponent]];
  if (w != nil) {
    windowComponent_ = [w getComponent];
    startX_ = ((RAREUIPoint *) nil_chk(p))->x_;
    startY_ = p->y_;
    p = [((id<RAREiPlatformComponent>) nil_chk(windowComponent_)) getLocationOnScreen];
    winX_ = ((RAREUIPoint *) nil_chk(p))->x_;
    winY_ = p->y_;
    screenSize_ = [RAREScreenUtils getUsableScreenBoundsWithRAREiPlatformComponent:windowComponent_];
  }
}

- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e {
  windowComponent_ = nil;
}

- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)removeAsWindowResizerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if (c != nil) {
    [c removeMouseListenerWithRAREiMouseListener:self];
    [c removeMouseMotionListenerWithRAREiMouseMotionListener:self];
  }
}

- (BOOL)wantsLongPress {
  return NO;
}

- (BOOL)wantsMouseMovedEvents {
  return NO;
}

- (id)init {
  return [super init];
}

- (void)copyAllFieldsTo:(RAREResizeCornerHandler *)other {
  [super copyAllFieldsTo:other];
  other->screenSize_ = screenSize_;
  other->startX_ = startX_;
  other->startY_ = startY_;
  other->winX_ = winX_;
  other->winY_ = winY_;
  other->windowComponent_ = windowComponent_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "wantsLongPress", NULL, "Z", 0x1, NULL },
    { "wantsMouseMovedEvents", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "screenSize_", NULL, 0x0, "LRAREUIRectangle" },
    { "startX_", NULL, 0x0, "F" },
    { "startY_", NULL, 0x0, "F" },
    { "winX_", NULL, 0x0, "F" },
    { "winY_", NULL, 0x0, "F" },
  };
  static J2ObjcClassInfo _RAREResizeCornerHandler = { "ResizeCornerHandler", "com.appnativa.rare.util", NULL, 0x1, 2, methods, 5, fields, 0, NULL};
  return &_RAREResizeCornerHandler;
}

@end
