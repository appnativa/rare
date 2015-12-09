//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/ControlView.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/apple/ui/view/ControlView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/event/ChangeEvent.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#import "com/appnativa/rare/ui/text/HTMLCharSequence.h"

@implementation RAREControlView

- (id)initWithId:(id)uicontrol {
  return [super initWithId:uicontrol];
}

- (void)setChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  changeListener_ = l;
}

- (id)init {
  return [super init];
}

- (void)borderChangedWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)newBorder {
  [super borderChangedWithRAREiPlatformBorder:newBorder];
  if (newBorder == nil) {
    [self setMarginWithFloat:2 withFloat:2 withFloat:2 withFloat:2];
  }
  else {
    [self setMarginWithRAREUIInsets:[newBorder getBorderInsetsWithRAREUIInsets:nil]];
  }
}

- (void)handleChangeEvent {
  if (changeListener_ != nil) {
    if (changeEvent_ == nil) {
      changeEvent_ = [[RAREChangeEvent alloc] initWithId:self];
    }
    [changeListener_ stateChangedWithJavaUtilEventObject:changeEvent_];
  }
}

- (void)setSelectedWithBoolean:(BOOL)selected {
  ((UIControl*)proxy_).selected=selected;
}

- (BOOL)isPressed {
  return ((UIControl*)proxy_).highlighted;
}

- (BOOL)isSelected {
  return ((UIControl*)proxy_).selected;
}

- (void)setEnabledExWithBoolean:(BOOL)b {
  UIView* v=(UIView*)proxy_;
  v.userInteractionEnabled = b;
}

- (void)copyAllFieldsTo:(RAREControlView *)other {
  [super copyAllFieldsTo:other];
  other->changeListener_ = changeListener_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x4, NULL },
    { "handleChangeEvent", NULL, "V", 0x4, NULL },
    { "setSelectedWithBoolean:", NULL, "V", 0x101, NULL },
    { "isPressed", NULL, "Z", 0x101, NULL },
    { "isSelected", NULL, "Z", 0x101, NULL },
    { "setEnabledExWithBoolean:", NULL, "V", 0x104, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "changeListener_", NULL, 0x4, "LRAREiChangeListener" },
  };
  static J2ObjcClassInfo _RAREControlView = { "ControlView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 6, methods, 1, fields, 0, NULL};
  return &_RAREControlView;
}

@end
