//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/WindowEvent.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/event/WindowEvent.h"
#include "java/lang/IllegalArgumentException.h"

@implementation RAREWindowEvent

- (id)initWithId:(id)source
withRAREWindowEvent_TypeEnum:(RAREWindowEvent_TypeEnum *)type {
  if (self = [super initWithId:source]) {
    self->type_ = type;
  }
  return self;
}

- (void)reject {
  consumed_ = YES;
}

- (RAREWindowEvent_TypeEnum *)getType {
  return type_;
}

- (void)copyAllFieldsTo:(RAREWindowEvent *)other {
  [super copyAllFieldsTo:other];
  other->type_ = type_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getType", NULL, "LRAREWindowEvent_TypeEnum", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREWindowEvent = { "WindowEvent", "com.appnativa.rare.ui.event", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREWindowEvent;
}

@end

static RAREWindowEvent_TypeEnum *RAREWindowEvent_TypeEnum_Opened;
static RAREWindowEvent_TypeEnum *RAREWindowEvent_TypeEnum_WillClose;
static RAREWindowEvent_TypeEnum *RAREWindowEvent_TypeEnum_Closed;
IOSObjectArray *RAREWindowEvent_TypeEnum_values;

@implementation RAREWindowEvent_TypeEnum

+ (RAREWindowEvent_TypeEnum *)Opened {
  return RAREWindowEvent_TypeEnum_Opened;
}
+ (RAREWindowEvent_TypeEnum *)WillClose {
  return RAREWindowEvent_TypeEnum_WillClose;
}
+ (RAREWindowEvent_TypeEnum *)Closed {
  return RAREWindowEvent_TypeEnum_Closed;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREWindowEvent_TypeEnum class]) {
    RAREWindowEvent_TypeEnum_Opened = [[RAREWindowEvent_TypeEnum alloc] initWithNSString:@"Opened" withInt:0];
    RAREWindowEvent_TypeEnum_WillClose = [[RAREWindowEvent_TypeEnum alloc] initWithNSString:@"WillClose" withInt:1];
    RAREWindowEvent_TypeEnum_Closed = [[RAREWindowEvent_TypeEnum alloc] initWithNSString:@"Closed" withInt:2];
    RAREWindowEvent_TypeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREWindowEvent_TypeEnum_Opened, RAREWindowEvent_TypeEnum_WillClose, RAREWindowEvent_TypeEnum_Closed, nil } count:3 type:[IOSClass classWithClass:[RAREWindowEvent_TypeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREWindowEvent_TypeEnum_values];
}

+ (RAREWindowEvent_TypeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREWindowEvent_TypeEnum_values count]; i++) {
    RAREWindowEvent_TypeEnum *e = RAREWindowEvent_TypeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREWindowEvent_TypeEnum"};
  static J2ObjcClassInfo _RAREWindowEvent_TypeEnum = { "Type", "com.appnativa.rare.ui.event", "WindowEvent", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREWindowEvent_TypeEnum;
}

@end
