//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/event/MouseEventEx.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/platform/EventHelper.h"
#include "com/appnativa/rare/ui/event/MouseEventEx.h"
#include "com/appnativa/rare/ui/event/UITouch.h"

@implementation RAREMouseEventEx

- (id)initWithId:(id)source
          withId:(id)me
         withInt:(int)modifiers {
  return [super initWithId:source withId:[RAREEventHelper getTouchWithId:source withId:me] withInt:modifiers];
}

- (id)initWithId:(id)source
          withId:(id)me1
         withInt:(int)modifiers
          withId:(id)me2
       withFloat:(float)x
       withFloat:(float)y {
  return [super initWithId:source withId:[RAREEventHelper getTouchWithId:source withId:me1] withInt:modifiers withId:[RAREEventHelper getTouchWithId:source withId:me2] withFloat:x withFloat:y];
}

- (id)initWithId:(id)source
          withId:(id)me {
  return [super initWithId:source withId:[RAREEventHelper getTouchWithId:source withId:me]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREMouseEventEx = { "MouseEventEx", "com.appnativa.rare.ui.event", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREMouseEventEx;
}

@end
