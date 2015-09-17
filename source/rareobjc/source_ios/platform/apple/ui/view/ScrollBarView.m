//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/ScrollBarView.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/apple/ui/view/ScrollBarView.h"
#include "com/appnativa/rare/platform/apple/ui/view/ScrollView.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/event/ChangeEvent.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "java/lang/Math.h"

@implementation RAREScrollBarView

- (id)initWithBoolean:(BOOL)horizontal {
  if (self = [super init]) {
    blockIncrement_ = 0;
    maximim_ = 100;
    minimim_ = 0;
    unitIncrement_ = 0;
    value_ = 0;
    visibleAmount_ = 10;
    self->horizontal_ = horizontal;
  }
  return self;
}

- (id)initWithRAREScrollView:(RAREScrollView *)sv
                 withBoolean:(BOOL)horizontal {
  if (self = [super init]) {
    blockIncrement_ = 0;
    maximim_ = 100;
    minimim_ = 0;
    unitIncrement_ = 0;
    value_ = 0;
    visibleAmount_ = 10;
    scrollView_ = sv;
    self->horizontal_ = horizontal;
  }
  return self;
}

- (void)dispose {
  scrollView_ = nil;
}

- (BOOL)isShowAlways {
  return showAlways_;
}

- (void)setShowAlwaysWithBoolean:(BOOL)showAlways {
  if (scrollView_ != nil) {
    if (horizontal_) {
    }
    else {
    }
  }
  self->showAlways_ = showAlways;
}

- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  [((RAREEventListenerList *) nil_chk([self getEventListenerList])) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
}

- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  if ([self hasListener]) {
    [((RAREEventListenerList *) nil_chk([self getEventListenerList])) removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
  }
}

- (void)setBlockIncrementWithDouble:(double)b {
  blockIncrement_ = b;
}

- (void)setMaximumWithDouble:(double)max {
  self->maximim_ = max;
}

- (void)setMinimumWithDouble:(double)min {
  self->minimim_ = min;
}

- (void)setUnitIncrementWithDouble:(double)u {
  unitIncrement_ = u;
}

- (void)setValueWithDouble:(double)v {
  if (v != value_) {
    direction_ = (v < [self getValue]) ? -1 : 1;
    v = [JavaLangMath minWithDouble:maximim_ withDouble:v];
    v = [JavaLangMath maxWithDouble:minimim_ withDouble:v];
    value_ = v;
    if ([self hasListener]) {
      if (changeEvent_ == nil) {
        changeEvent_ = [[RAREChangeEvent alloc] initWithId:self];
      }
      [self notifyListenersWithRAREChangeEvent:changeEvent_];
    }
  }
}

- (void)setVisibleAmountWithDouble:(double)v {
  visibleAmount_ = v;
}

- (int)getDirection {
  return direction_;
}

- (double)getBlockIncrement {
  return blockIncrement_;
}

- (double)getMaximum {
  return maximim_;
}

- (double)getMinimum {
  return minimim_;
}

- (double)getUnitIncrement {
  return unitIncrement_;
}

- (double)getValue {
  return value_;
}

- (double)getVisibleAmount {
  return visibleAmount_;
}

- (BOOL)isAdjusting {
  return NO;
}

- (RAREEventListenerList *)getEventListenerList {
  if (listenerList_ == nil) {
    listenerList_ = [[RAREEventListenerList alloc] init];
  }
  return listenerList_;
}

- (BOOL)hasListener {
  return listenerList_ != nil;
}

- (void)notifyListenersWithRAREChangeEvent:(RAREChangeEvent *)e {
  [RAREUtils fireChangeEventWithRAREEventListenerList:[self getEventListenerList] withRAREChangeEvent:changeEvent_];
}

- (void)setThemeWithBoolean:(BOOL)b {
}

- (void)copyAllFieldsTo:(RAREScrollBarView *)other {
  [super copyAllFieldsTo:other];
  other->blockIncrement_ = blockIncrement_;
  other->direction_ = direction_;
  other->horizontal_ = horizontal_;
  other->listenerList_ = listenerList_;
  other->maximim_ = maximim_;
  other->minimim_ = minimim_;
  other->scrollView_ = scrollView_;
  other->showAlways_ = showAlways_;
  other->unitIncrement_ = unitIncrement_;
  other->value_ = value_;
  other->visibleAmount_ = visibleAmount_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isShowAlways", NULL, "Z", 0x1, NULL },
    { "isAdjusting", NULL, "Z", 0x1, NULL },
    { "getEventListenerList", NULL, "LRAREEventListenerList", 0x4, NULL },
    { "hasListener", NULL, "Z", 0x4, NULL },
    { "notifyListenersWithRAREChangeEvent:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "scrollView_", NULL, 0x4, "LRAREScrollView" },
    { "horizontal_", NULL, 0x0, "Z" },
    { "blockIncrement_", NULL, 0x4, "D" },
    { "maximim_", NULL, 0x4, "D" },
    { "minimim_", NULL, 0x4, "D" },
    { "unitIncrement_", NULL, 0x4, "D" },
    { "value_", NULL, 0x4, "D" },
    { "visibleAmount_", NULL, 0x4, "D" },
    { "direction_", NULL, 0x4, "I" },
    { "listenerList_", NULL, 0x4, "LRAREEventListenerList" },
  };
  static J2ObjcClassInfo _RAREScrollBarView = { "ScrollBarView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 5, methods, 10, fields, 0, NULL};
  return &_RAREScrollBarView;
}

@end
