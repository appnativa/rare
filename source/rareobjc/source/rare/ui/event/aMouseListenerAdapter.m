//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/aMouseListenerAdapter.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/event/aMouseListenerAdapter.h"

@implementation RAREaMouseListenerAdapter

- (id)init {
  return [super init];
}

- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (BOOL)wantsLongPress {
  return NO;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "wantsLongPress", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREaMouseListenerAdapter = { "aMouseListenerAdapter", "com.appnativa.rare.ui.event", NULL, 0x401, 1, methods, 0, NULL, 0, NULL};
  return &_RAREaMouseListenerAdapter;
}

@end
