//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-carousel/com/appnativa/rare/ui/carousel/MotionHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/carousel/MotionHandler.h"
#include "com/appnativa/rare/ui/carousel/aCarouselPanel.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/Math.h"

@implementation RAREMotionHandler

- (id)initRAREMotionHandlerWithRAREaCarouselPanel:(RAREaCarouselPanel *)panel
           withRAREMotionHandler_iScrollingParent:(id<RAREMotionHandler_iScrollingParent>)parentView {
  if (self = [super init]) {
    mouseDownX_ = 0;
    overFlow_ = [RAREScreenUtils platformPixelsfWithFloat:50];
    scrollX_ = overFlow_ / -2;
    scrollXBase_ = scrollX_;
    self->panel_ = panel;
    self->parentView_ = parentView;
    threshold_ = [RAREScreenUtils platformPixelsfWithFloat:![RAREPlatform isTouchDevice] ? 5 : 20];
  }
  return self;
}

- (id)initWithRAREaCarouselPanel:(RAREaCarouselPanel *)panel
withRAREMotionHandler_iScrollingParent:(id<RAREMotionHandler_iScrollingParent>)parentView {
  return [self initRAREMotionHandlerWithRAREaCarouselPanel:panel withRAREMotionHandler_iScrollingParent:parentView];
}

- (id)initWithRAREaCarouselPanel:(RAREaCarouselPanel *)panel {
  return [self initRAREMotionHandlerWithRAREaCarouselPanel:panel withRAREMotionHandler_iScrollingParent:nil];
}

- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)setScrollingComponentWithRAREMotionHandler_iScrollingParent:(id<RAREMotionHandler_iScrollingParent>)c {
  parentView_ = c;
}

- (BOOL)wantsMouseMovedEvents {
  return NO;
}

- (void)mouseDraggedWithRAREMouseEvent:(RAREMouseEvent *)e {
  float x = [((RAREMouseEvent *) nil_chk(e)) getX];
  float distanceX = mouseDownX_ - x;
  if (!dragged_ && ([JavaLangMath absWithFloat:distanceX] < threshold_)) {
    return;
  }
  dragged_ = YES;
  float nx = lastX_ - x;
  if ([JavaLangMath absWithFloat:nx] < threshold_) {
    return;
  }
  lastX_ = x;
  if (distanceX > 0) {
    distanceX = [JavaLangMath minWithFloat:distanceX withFloat:overFlow_];
  }
  else if (distanceX < 0) {
    distanceX = [JavaLangMath maxWithFloat:distanceX withFloat:-overFlow_];
  }
  if (nx < 0) {
    if ([((RAREaCarouselPanel *) nil_chk(panel_)) getSelectedIndex] > 0) {
      [panel_ scrollRight];
      scrollX_ = scrollXBase_;
    }
    else {
      scrollX_ = scrollXBase_ - distanceX;
    }
  }
  else {
    if ([((RAREaCarouselPanel *) nil_chk(panel_)) getSelectedIndex] + 1 < [panel_ getItemCount]) {
      [panel_ scrollLeft];
      scrollX_ = scrollXBase_;
    }
    else {
      scrollX_ = scrollXBase_ - distanceX;
    }
  }
  if (![RAREUTSNumber isEqualWithFloat:scrollX_ withFloat:scrollXBase_]) {
    [((RAREaCarouselPanel *) nil_chk(panel_)) setBoundsWithFloat:scrollX_ withFloat:0 withFloat:[((id<RAREMotionHandler_iScrollingParent>) nil_chk(parentView_)) getWidth] + overFlow_ withFloat:[parentView_ getHeight]];
  }
  [((id<RAREMotionHandler_iScrollingParent>) nil_chk(parentView_)) repaint];
}

- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e {
  mouseDownX_ = [((RAREMouseEvent *) nil_chk(e)) getX];
  lastX_ = mouseDownX_;
  dragged_ = NO;
}

- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e {
  if (![RAREUTSNumber isEqualWithFloat:scrollX_ withFloat:scrollXBase_]) {
    scrollX_ = scrollXBase_;
    [((RAREaCarouselPanel *) nil_chk(panel_)) setBoundsWithFloat:scrollX_ withFloat:0 withFloat:[((id<RAREMotionHandler_iScrollingParent>) nil_chk(parentView_)) getWidth] + overFlow_ withFloat:[parentView_ getHeight]];
    [parentView_ repaint];
  }
  else if (!dragged_) {
    int index = [parentView_ getIndexOfComponentAtWithFloat:mouseDownX_ withFloat:[((id<RAREMotionHandler_iScrollingParent>) nil_chk(parentView_)) getHeight] / 2];
    if ((index != -1) && (index != [((RAREaCarouselPanel *) nil_chk(panel_)) getSelectedIndex])) {
      [panel_ animateSelectWithInt:index];
    }
  }
  if (dragged_) {
    [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREMotionHandler_$1 alloc] initWithRAREMotionHandler:self]];
  }
  dragged_ = NO;
}

- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e {
  [self mouseReleasedWithRAREMouseEvent:e];
}

- (BOOL)wantsLongPress {
  return NO;
}

- (float)getScrollX {
  return scrollX_;
}

- (BOOL)isScrolling {
  return dragged_;
}

- (float)getOverflow {
  return overFlow_;
}

- (void)layoutChildWithFloat:(float)width
                   withFloat:(float)height {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setBoundsWithFloat:scrollX_ withFloat:0 withFloat:width + overFlow_ withFloat:height];
}

- (void)dispose {
  parentView_ = nil;
  panel_ = nil;
}

- (void)copyAllFieldsTo:(RAREMotionHandler *)other {
  [super copyAllFieldsTo:other];
  other->dragged_ = dragged_;
  other->lastX_ = lastX_;
  other->mouseDownX_ = mouseDownX_;
  other->overFlow_ = overFlow_;
  other->panel_ = panel_;
  other->parentView_ = parentView_;
  other->scrollX_ = scrollX_;
  other->scrollXBase_ = scrollXBase_;
  other->threshold_ = threshold_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "wantsMouseMovedEvents", NULL, "Z", 0x1, NULL },
    { "wantsLongPress", NULL, "Z", 0x1, NULL },
    { "isScrolling", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "panel_", NULL, 0x0, "LRAREaCarouselPanel" },
    { "parentView_", NULL, 0x0, "LRAREMotionHandler_iScrollingParent" },
    { "mouseDownX_", NULL, 0x0, "F" },
    { "overFlow_", NULL, 0x0, "F" },
    { "scrollX_", NULL, 0x0, "F" },
    { "scrollXBase_", NULL, 0x0, "F" },
    { "dragged_", NULL, 0x0, "Z" },
    { "lastX_", NULL, 0x0, "F" },
    { "threshold_", NULL, 0x0, "F" },
  };
  static J2ObjcClassInfo _RAREMotionHandler = { "MotionHandler", "com.appnativa.rare.ui.carousel", NULL, 0x1, 3, methods, 9, fields, 0, NULL};
  return &_RAREMotionHandler;
}

@end

@interface RAREMotionHandler_iScrollingParent : NSObject
@end

@implementation RAREMotionHandler_iScrollingParent

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getIndexOfComponentAtWithFloat:withFloat:", NULL, "I", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREMotionHandler_iScrollingParent = { "iScrollingParent", "com.appnativa.rare.ui.carousel", "MotionHandler", 0x201, 1, methods, 0, NULL, 0, NULL};
  return &_RAREMotionHandler_iScrollingParent;
}

@end
@implementation RAREMotionHandler_$1

- (void)run {
  [((RAREaCarouselPanel *) nil_chk(this$0_->panel_)) fireChangeEvent];
}

- (id)initWithRAREMotionHandler:(RAREMotionHandler *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREMotionHandler" },
  };
  static J2ObjcClassInfo _RAREMotionHandler_$1 = { "$1", "com.appnativa.rare.ui.carousel", "MotionHandler", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREMotionHandler_$1;
}

@end
