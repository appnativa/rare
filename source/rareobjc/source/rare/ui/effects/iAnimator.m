//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/iAnimator.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/effects/iAnimator.h"
#include "com/appnativa/rare/ui/effects/iAnimatorListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/util/Map.h"


@implementation RAREiAnimator

+ (int)INFINITE {
  return RAREiAnimator_INFINITE;
}

- (id)copyWithZoneWithNSZone:(NSZone *)zone {
  return [self clone];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addListenerWithRAREiAnimatorListener:", NULL, "V", 0x401, NULL },
    { "animateWithRAREiPlatformComponent:withId:", NULL, "V", 0x401, NULL },
    { "cancel", NULL, "V", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "handlePropertiesWithJavaUtilMap:", NULL, "V", 0x401, NULL },
    { "removeListenerWithRAREiAnimatorListener:", NULL, "V", 0x401, NULL },
    { "stop", NULL, "V", 0x401, NULL },
    { "setAccelerationWithFloat:", NULL, "V", 0x401, NULL },
    { "setAutoReverseWithBoolean:", NULL, "V", 0x401, NULL },
    { "setDecelerationWithFloat:", NULL, "V", 0x401, NULL },
    { "setDirectionWithRAREiAnimator_DirectionEnum:", NULL, "V", 0x401, NULL },
    { "setDurationWithInt:", NULL, "V", 0x401, NULL },
    { "setRepeatCountWithInt:", NULL, "V", 0x401, NULL },
    { "isRunning", NULL, "Z", 0x401, NULL },
    { "getDirection", NULL, "LRAREiAnimator_DirectionEnum", 0x401, NULL },
    { "clone", NULL, "LNSObject", 0x401, NULL },
    { "isAutoOrient", NULL, "Z", 0x401, NULL },
    { "setAutoOrientWithBoolean:", NULL, "V", 0x401, NULL },
    { "isSizingAnimation", NULL, "Z", 0x401, NULL },
    { "restoreComponentWithRAREiPlatformComponent:", NULL, "V", 0x401, NULL },
    { "setViewEventsEnabledWithRAREiPlatformComponent:withBoolean:", NULL, "V", 0x401, NULL },
    { "setAnimatingPropertyWithRAREiPlatformComponent:withBoolean:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "INFINITE_", NULL, 0x19, "I" },
  };
  static J2ObjcClassInfo _RAREiAnimator = { "iAnimator", "com.appnativa.rare.ui.effects", NULL, 0x201, 22, methods, 1, fields, 0, NULL};
  return &_RAREiAnimator;
}

@end

static RAREiAnimator_DirectionEnum *RAREiAnimator_DirectionEnum_FORWARD;
static RAREiAnimator_DirectionEnum *RAREiAnimator_DirectionEnum_BACKWARD;
IOSObjectArray *RAREiAnimator_DirectionEnum_values;

@implementation RAREiAnimator_DirectionEnum

+ (RAREiAnimator_DirectionEnum *)FORWARD {
  return RAREiAnimator_DirectionEnum_FORWARD;
}
+ (RAREiAnimator_DirectionEnum *)BACKWARD {
  return RAREiAnimator_DirectionEnum_BACKWARD;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiAnimator_DirectionEnum class]) {
    RAREiAnimator_DirectionEnum_FORWARD = [[RAREiAnimator_DirectionEnum alloc] initWithNSString:@"FORWARD" withInt:0];
    RAREiAnimator_DirectionEnum_BACKWARD = [[RAREiAnimator_DirectionEnum alloc] initWithNSString:@"BACKWARD" withInt:1];
    RAREiAnimator_DirectionEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiAnimator_DirectionEnum_FORWARD, RAREiAnimator_DirectionEnum_BACKWARD, nil } count:2 type:[IOSClass classWithClass:[RAREiAnimator_DirectionEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiAnimator_DirectionEnum_values];
}

+ (RAREiAnimator_DirectionEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiAnimator_DirectionEnum_values count]; i++) {
    RAREiAnimator_DirectionEnum *e = RAREiAnimator_DirectionEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiAnimator_DirectionEnum"};
  static J2ObjcClassInfo _RAREiAnimator_DirectionEnum = { "Direction", "com.appnativa.rare.ui.effects", "iAnimator", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiAnimator_DirectionEnum;
}

@end
