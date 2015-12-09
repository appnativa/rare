//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/EventHelper.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/platform/EventHelper.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/event/KeyEvent.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/event/ScaleGestureObject.h"
#include "com/appnativa/rare/ui/event/UITouch.h"
#import "com/appnativa/rare/platform/apple/ui/view/View.h"
#import "APView+Component.h"

@implementation RAREEventHelper

- (id)init {
  return [super init];
}

+ (RAREMouseEvent_TypeEnum *)getMouseEventTypeWithId:(id)source
                                              withId:(id)motionEvent {
  return ((RAREUITouch *) nil_chk(motionEvent))->type_;
}

+ (RAREUITouch *)getTouchWithId:(id)source
                         withId:(id)motionEvent {
  if([motionEvent isKindOfClass:[RAREUITouch class]]) {
    return (RAREUITouch*)motionEvent;
  }
  
  RAREUITouch* t=[RAREUITouch new];
  NSSet* set=[((UIEvent*)motionEvent) allTouches];
  if(!set) {
    return t;
  }
  
  UITouch* touch=[set anyObject];
  UIWindow *window = touch.window;
  UIView* view=(UIView*)((RAREView*)source)->proxy_;
  CGPoint p = [touch locationInView:view];
  t->x_=p.x;
  t->y_=p.y;
  
  p = [touch locationInView:nil];
  CGPoint wp= [UIScreen convertPoint:p withScreen:window.screen];
  t->wx_=wp.x;
  t->wy_=wp.y;
  
  p.x += window.frame.origin.x;
  p.y += window.frame.origin.y;
  p = [UIScreen convertPoint: p withScreen:window.screen];
  t->sx_=p.x;
  t->sy_=p.y;
  
  t->clickCount_=(int)touch.tapCount;
  t->time_=((UIEvent*)motionEvent).timestamp*1000;
  switch(touch.phase) {
    case UITouchPhaseBegan:
    t->type_=[RAREMouseEvent_TypeEnum MOUSE_DOWN];
    break;
    case UITouchPhaseCancelled:
    case UITouchPhaseEnded:
    t->type_=[RAREMouseEvent_TypeEnum MOUSE_UP];
    break;
    case UITouchPhaseMoved:
    t->type_=[RAREMouseEvent_TypeEnum MOUSE_DRAG];
    break;
    default:
    t->type_=[RAREMouseEvent_TypeEnum MOUSE_UNKNOWN];
    break;
    
  }
  return t;
}

+ (RAREMouseEvent *)retargetWithRAREMouseEvent:(RAREMouseEvent *)e
                                  withRAREView:(RAREView *)view {
  return [[RAREMouseEvent alloc] initWithId:view withId:[((RAREMouseEvent *) nil_chk(e)) getNativeStartEvent] withInt:[e getModifiers] withId:[e getNativeEndEvent] withFloat:[e getGestureX] withFloat:[e getGestureY]];
}

+ (int)getClickCountWithId:(id)source
                    withId:(id)motionEvent {
  return ((RAREUITouch *) nil_chk(motionEvent))->clickCount_;
}

+ (long long int)getEventTimeWithId:(id)source
                             withId:(id)motionEvent {
  return ((RAREUITouch *) nil_chk(motionEvent))->time_;
}

+ (int)getKeyCharWithId:(id)keyEvent {
  return 0;
}

+ (int)getKeyCodeWithId:(id)keyEvent {
  return 0;
}

+ (RAREUIPoint *)getMouseLocationInWindowWithId:(id)source
                                         withId:(id)motionEvent {
  RAREUITouch *t = (RAREUITouch *) check_class_cast(motionEvent, [RAREUITouch class]);
  return [[RAREUIPoint alloc] initWithFloat:((RAREUITouch *) nil_chk(t))->wx_ withFloat:t->wy_];
}

+ (RAREUIPoint *)getMouseLocationOnScreenWithId:(id)source
                                         withId:(id)motionEvent {
  RAREUITouch *t = (RAREUITouch *) check_class_cast(motionEvent, [RAREUITouch class]);
  return [[RAREUIPoint alloc] initWithFloat:((RAREUITouch *) nil_chk(t))->sx_ withFloat:t->sy_];
}

+ (RAREUIPoint *)getMousePointWithId:(id)source
                              withId:(id)motionEvent {
  RAREUITouch *t = (RAREUITouch *) check_class_cast(motionEvent, [RAREUITouch class]);
  return [[RAREUIPoint alloc] initWithFloat:((RAREUITouch *) nil_chk(t))->x_ withFloat:t->y_];
}

+ (float)getMouseXWithId:(id)source
                  withId:(id)motionEvent {
  return ((RAREUITouch *) nil_chk(motionEvent))->x_;
}

+ (float)getMouseXInWindowWithId:(id)source
                          withId:(id)motionEvent {
  return ((RAREUITouch *) nil_chk(motionEvent))->wx_;
}

+ (float)getMouseXOnScreenWithId:(id)source
                          withId:(id)motionEvent {
  return ((RAREUITouch *) nil_chk(motionEvent))->sx_;
}

+ (float)getMouseYWithId:(id)source
                  withId:(id)motionEvent {
  return ((RAREUITouch *) nil_chk(motionEvent))->y_;
}

+ (float)getMouseYInWindowWithId:(id)source
                          withId:(id)motionEvent {
  return ((RAREUITouch *) nil_chk(motionEvent))->wy_;
}

+ (float)getMouseYOnScreenWithId:(id)source
                          withId:(id)motionEvent {
  return ((RAREUITouch *) nil_chk(motionEvent))->sy_;
}

+ (float)getScaleCurrentSpanWithId:(id)scaleGestureDetector {
  RAREScaleGestureObject *gp = (RAREScaleGestureObject *) check_class_cast(scaleGestureDetector, [RAREScaleGestureObject class]);
  return [((RAREScaleGestureObject *) nil_chk(gp)) getCurrentSpan];
}

+ (float)getScaleFocusXWithId:(id)scaleGestureDetector {
  RAREScaleGestureObject *gp = (RAREScaleGestureObject *) check_class_cast(scaleGestureDetector, [RAREScaleGestureObject class]);
  return [((RAREScaleGestureObject *) nil_chk(gp)) getFocusX];
}

+ (float)getScaleFocusYWithId:(id)scaleGestureDetector {
  RAREScaleGestureObject *gp = (RAREScaleGestureObject *) check_class_cast(scaleGestureDetector, [RAREScaleGestureObject class]);
  return [((RAREScaleGestureObject *) nil_chk(gp)) getFocusY];
}

+ (float)getScalePreviousSpanWithId:(id)scaleGestureDetector {
  RAREScaleGestureObject *gp = (RAREScaleGestureObject *) check_class_cast(scaleGestureDetector, [RAREScaleGestureObject class]);
  return [((RAREScaleGestureObject *) nil_chk(gp)) getPreviousSpan];
}

+ (int)getWhichWithId:(id)keyEvent {
  return 0;
}

+ (BOOL)isAltKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isBackspaceKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isDeleteKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isDownArrowKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isEndKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isEnterKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isEscapeKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isHomeKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isLeftArrowKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isLeftnArrowKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isMetaKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isPageDownKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isPageUpKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isRightArrowKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isShiftKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isTabKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (BOOL)isUpArrowKeyPressedWithId:(id)keyEvent {
  return NO;
}

+ (RAREKeyEvent_TypeEnum *)getKeyTypeWithId:(id)nativeEvent {
  return [RAREKeyEvent_TypeEnum KEY_UNKNOWN];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getMouseEventTypeWithId:withId:", NULL, "LRAREMouseEvent_TypeEnum", 0x9, NULL },
    { "getTouchWithId:withId:", NULL, "LRAREUITouch", 0x109, NULL },
    { "retargetWithRAREMouseEvent:withRAREView:", NULL, "LRAREMouseEvent", 0x9, NULL },
    { "getMouseLocationInWindowWithId:withId:", NULL, "LRAREUIPoint", 0x9, NULL },
    { "getMouseLocationOnScreenWithId:withId:", NULL, "LRAREUIPoint", 0x9, NULL },
    { "getMousePointWithId:withId:", NULL, "LRAREUIPoint", 0x9, NULL },
    { "isAltKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isBackspaceKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isDeleteKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isDownArrowKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isEndKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isEnterKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isEscapeKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isHomeKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isLeftArrowKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isLeftnArrowKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isMetaKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isPageDownKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isPageUpKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isRightArrowKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isShiftKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isTabKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "isUpArrowKeyPressedWithId:", NULL, "Z", 0x9, NULL },
    { "getKeyTypeWithId:", NULL, "LRAREKeyEvent_TypeEnum", 0x9, NULL },
  };
  static J2ObjcClassInfo _RAREEventHelper = { "EventHelper", "com.appnativa.rare.platform", NULL, 0x1, 24, methods, 0, NULL, 0, NULL};
  return &_RAREEventHelper;
}

@end
