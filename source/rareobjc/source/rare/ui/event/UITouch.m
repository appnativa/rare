//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/event/UITouch.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/event/UITouch.h"

@implementation RAREUITouch

- (id)init {
  if (self = [super init]) {
    type_ = [RAREMouseEvent_TypeEnum MOUSE_UNKNOWN];
  }
  return self;
}

- (void)copyAllFieldsTo:(RAREUITouch *)other {
  [super copyAllFieldsTo:other];
  other->clickCount_ = clickCount_;
  other->sx_ = sx_;
  other->sy_ = sy_;
  other->time_ = time_;
  other->type_ = type_;
  other->wx_ = wx_;
  other->wy_ = wy_;
  other->x_ = x_;
  other->y_ = y_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "sx_", NULL, 0x1, "F" },
    { "sy_", NULL, 0x1, "F" },
    { "time_", NULL, 0x1, "J" },
    { "wx_", NULL, 0x1, "F" },
    { "wy_", NULL, 0x1, "F" },
    { "x_", NULL, 0x1, "F" },
    { "y_", NULL, 0x1, "F" },
    { "clickCount_", NULL, 0x1, "I" },
    { "type_", NULL, 0x1, "LRAREMouseEvent_TypeEnum" },
  };
  static J2ObjcClassInfo _RAREUITouch = { "UITouch", "com.appnativa.rare.ui.event", NULL, 0x1, 0, NULL, 9, fields, 0, NULL};
  return &_RAREUITouch;
}

@end
