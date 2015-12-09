//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/StateListIcon.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/platform/apple/ui/StateListIcon.h"
#include "com/appnativa/rare/ui/iPaintedButton.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/painter/PainterHolder.h"

@implementation RAREStateListIcon

- (id)initWithRAREPainterHolder:(RAREPainterHolder *)ph {
  if (self = [super init]) {
    painterHolder_ = ph;
    icon_ = ((RAREPainterHolder *) nil_chk(ph))->normalIcon_;
  }
  return self;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height {
  if (icon_ != nil) {
    [icon_ paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height];
  }
}

- (id<RAREiPlatformIcon>)getDisabledVersion {
  return ((RAREPainterHolder *) nil_chk(painterHolder_))->disabledIcon_;
}

- (id<RAREiPlatformIcon>)getIconWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state {
  return [((RAREPainterHolder *) nil_chk(painterHolder_)) getIconWithRAREiPaintedButton_ButtonStateEnum:state];
}

- (int)getIconHeight {
  return (icon_ == nil) ? 0 : [icon_ getIconHeight];
}

- (int)getIconWidth {
  return (icon_ == nil) ? 0 : [icon_ getIconWidth];
}

- (void)copyAllFieldsTo:(RAREStateListIcon *)other {
  [super copyAllFieldsTo:other];
  other->icon_ = icon_;
  other->painterHolder_ = painterHolder_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getDisabledVersion", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getIconWithRAREiPaintedButton_ButtonStateEnum:", NULL, "LRAREiPlatformIcon", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "icon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "painterHolder_", NULL, 0x4, "LRAREPainterHolder" },
  };
  static J2ObjcClassInfo _RAREStateListIcon = { "StateListIcon", "com.appnativa.rare.platform.apple.ui", NULL, 0x1, 2, methods, 2, fields, 0, NULL};
  return &_RAREStateListIcon;
}

@end
