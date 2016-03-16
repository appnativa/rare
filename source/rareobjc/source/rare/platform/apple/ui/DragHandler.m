//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/DragHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/apple/ui/DragHandler.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "java/lang/Math.h"

@implementation RAREDragHandler

- (id)initWithRAREWindowViewer:(RAREWindowViewer *)windowViewer {
  if (self = [super init]) {
    window_ = windowViewer;
  }
  return self;
}

- (void)addAsWindowDraggerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  [((RAREComponent *) check_class_cast(c, [RAREComponent class])) removeMouseMotionListenerWithRAREiMouseMotionListener:self];
  [((RAREComponent *) check_class_cast(c, [RAREComponent class])) removeMouseListenerWithRAREiMouseListener:self];
  [((RAREComponent *) check_class_cast(c, [RAREComponent class])) addMouseMotionListenerWithRAREiMouseMotionListener:self];
  [((RAREComponent *) check_class_cast(c, [RAREComponent class])) addMouseListenerWithRAREiMouseListener:self];
}

- (void)dispose {
  window_ = nil;
}

- (void)mouseDraggedWithRAREMouseEvent:(RAREMouseEvent *)e {
  float xCoord = [((RAREMouseEvent *) nil_chk(e)) getScreenX];
  float yCoord = [e getScreenY];
  [((RAREWindowViewer *) nil_chk(window_)) moveToWithFloat:[JavaLangMath roundWithFloat:winX_ + (xCoord - startX_)] withFloat:[JavaLangMath roundWithFloat:winY_ + (yCoord - startY_)]];
}

- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e {
  startX_ = [((RAREMouseEvent *) nil_chk(e)) getScreenX];
  startY_ = [e getScreenY];
  winX_ = [((RAREWindowViewer *) nil_chk(window_)) getScreenX];
  winY_ = [window_ getScreenY];
}

- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)removeAsWindowDraggerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  [((RAREComponent *) check_class_cast(c, [RAREComponent class])) removeMouseMotionListenerWithRAREiMouseMotionListener:self];
}

- (BOOL)wantsLongPress {
  return NO;
}

- (BOOL)wantsMouseMovedEvents {
  return NO;
}

- (void)copyAllFieldsTo:(RAREDragHandler *)other {
  [super copyAllFieldsTo:other];
  other->startX_ = startX_;
  other->startY_ = startY_;
  other->winX_ = winX_;
  other->winY_ = winY_;
  other->window_ = window_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "wantsLongPress", NULL, "Z", 0x1, NULL },
    { "wantsMouseMovedEvents", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "startX_", NULL, 0x0, "F" },
    { "startY_", NULL, 0x0, "F" },
    { "winX_", NULL, 0x0, "F" },
    { "winY_", NULL, 0x0, "F" },
    { "window_", NULL, 0x0, "LRAREWindowViewer" },
  };
  static J2ObjcClassInfo _RAREDragHandler = { "DragHandler", "com.appnativa.rare.platform.apple.ui", NULL, 0x1, 2, methods, 5, fields, 0, NULL};
  return &_RAREDragHandler;
}

@end
