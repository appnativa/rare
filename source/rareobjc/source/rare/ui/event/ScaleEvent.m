//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/ScaleEvent.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/EventHelper.h"
#include "com/appnativa/rare/ui/event/ScaleEvent.h"
#include "java/lang/IllegalArgumentException.h"

@implementation RAREScaleEvent

- (id)initWithId:(id)source
          withId:(id)sgd
withRAREScaleEvent_TypeEnum:(RAREScaleEvent_TypeEnum *)type
       withFloat:(float)scaleFactor {
  if (self = [super initWithId:source]) {
    self->type_ = type;
    scaleGestureDetector_ = sgd;
    self->scaleFactor_ = scaleFactor;
  }
  return self;
}

- (float)getCurrentSpan {
  return [RAREEventHelper getScaleCurrentSpanWithId:scaleGestureDetector_];
}

- (RAREScaleEvent_TypeEnum *)getEventType {
  return type_;
}

- (float)getFocusX {
  return [RAREEventHelper getScaleFocusXWithId:scaleGestureDetector_];
}

- (float)getFocusY {
  return [RAREEventHelper getScaleFocusYWithId:scaleGestureDetector_];
}

- (float)getPreviousSpan {
  return [RAREEventHelper getScalePreviousSpanWithId:scaleGestureDetector_];
}

- (float)getScaleFactor {
  return scaleFactor_;
}

- (id)getScaleGestureDetector {
  return scaleGestureDetector_;
}

- (BOOL)isTypeWithRAREScaleEvent_TypeEnum:(RAREScaleEvent_TypeEnum *)type {
  return self->type_ == type;
}

- (void)copyAllFieldsTo:(RAREScaleEvent *)other {
  [super copyAllFieldsTo:other];
  other->scaleFactor_ = scaleFactor_;
  other->scaleGestureDetector_ = scaleGestureDetector_;
  other->type_ = type_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getEventType", NULL, "LRAREScaleEvent_TypeEnum", 0x1, NULL },
    { "getScaleGestureDetector", NULL, "LNSObject", 0x1, NULL },
    { "isTypeWithRAREScaleEvent_TypeEnum:", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "type_", NULL, 0x12, "LRAREScaleEvent_TypeEnum" },
    { "scaleFactor_", NULL, 0x12, "F" },
    { "scaleGestureDetector_", NULL, 0x12, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREScaleEvent = { "ScaleEvent", "com.appnativa.rare.ui.event", NULL, 0x1, 3, methods, 3, fields, 0, NULL};
  return &_RAREScaleEvent;
}

@end

static RAREScaleEvent_TypeEnum *RAREScaleEvent_TypeEnum_SCALE;
static RAREScaleEvent_TypeEnum *RAREScaleEvent_TypeEnum_SCALE_BEGIN;
static RAREScaleEvent_TypeEnum *RAREScaleEvent_TypeEnum_SCALE_END;
IOSObjectArray *RAREScaleEvent_TypeEnum_values;

@implementation RAREScaleEvent_TypeEnum

+ (RAREScaleEvent_TypeEnum *)SCALE {
  return RAREScaleEvent_TypeEnum_SCALE;
}
+ (RAREScaleEvent_TypeEnum *)SCALE_BEGIN {
  return RAREScaleEvent_TypeEnum_SCALE_BEGIN;
}
+ (RAREScaleEvent_TypeEnum *)SCALE_END {
  return RAREScaleEvent_TypeEnum_SCALE_END;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREScaleEvent_TypeEnum class]) {
    RAREScaleEvent_TypeEnum_SCALE = [[RAREScaleEvent_TypeEnum alloc] initWithNSString:@"SCALE" withInt:0];
    RAREScaleEvent_TypeEnum_SCALE_BEGIN = [[RAREScaleEvent_TypeEnum alloc] initWithNSString:@"SCALE_BEGIN" withInt:1];
    RAREScaleEvent_TypeEnum_SCALE_END = [[RAREScaleEvent_TypeEnum alloc] initWithNSString:@"SCALE_END" withInt:2];
    RAREScaleEvent_TypeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREScaleEvent_TypeEnum_SCALE, RAREScaleEvent_TypeEnum_SCALE_BEGIN, RAREScaleEvent_TypeEnum_SCALE_END, nil } count:3 type:[IOSClass classWithClass:[RAREScaleEvent_TypeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREScaleEvent_TypeEnum_values];
}

+ (RAREScaleEvent_TypeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREScaleEvent_TypeEnum_values count]; i++) {
    RAREScaleEvent_TypeEnum *e = RAREScaleEvent_TypeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREScaleEvent_TypeEnum"};
  static J2ObjcClassInfo _RAREScaleEvent_TypeEnum = { "Type", "com.appnativa.rare.ui.event", "ScaleEvent", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREScaleEvent_TypeEnum;
}

@end
