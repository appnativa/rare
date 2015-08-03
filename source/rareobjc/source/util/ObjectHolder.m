//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/ObjectHolder.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/util/ObjectHolder.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/InternalError.h"
#include "java/lang/StringBuilder.h"

@implementation RAREUTObjectHolder

- (id)initWithId:(id)value {
  if (self = [super init]) {
    [self setWithId:nil withId:value withLong:0];
  }
  return self;
}

- (id)initWithRAREUTObjectHolder:(RAREUTObjectHolder *)holder {
  if (self = [super init]) {
    if (holder != nil) {
      [self setWithId:holder->type_ withId:holder->value_ withLong:holder->flags_];
    }
  }
  return self;
}

- (id)initWithId:(id)value
        withLong:(long long int)flags {
  if (self = [super init]) {
    [self setWithId:type_ withId:nil withLong:flags];
  }
  return self;
}

- (id)initWithId:(id)type
          withId:(id)value {
  if (self = [super init]) {
    [self setWithId:type withId:value];
  }
  return self;
}

- (id)initWithId:(id)type
          withId:(id)value
        withLong:(long long int)flags {
  if (self = [super init]) {
    [self setWithId:type withId:value withLong:flags];
  }
  return self;
}

- (id)initWithId:(id)source
          withId:(id)type
          withId:(id)value {
  if (self = [super init]) {
    [self setWithId:type withId:value];
    self->source_ = source;
  }
  return self;
}

- (id)initWithId:(id)source
          withId:(id)type
          withId:(id)value
        withLong:(long long int)flags {
  if (self = [super init]) {
    [self setWithId:type withId:value withLong:flags];
    self->source_ = source;
  }
  return self;
}

- (void)clear {
  self->source_ = nil;
  self->type_ = nil;
  self->value_ = nil;
  self->flags_ = 0;
}

- (void)dispose {
  [self clear];
}

- (id)clone {
  @try {
    return [super clone];
  }
  @catch (JavaLangCloneNotSupportedException *e) {
    @throw [[JavaLangInternalError alloc] initWithNSString:@"Clone suhould be supported"];
  }
}

- (NSString *)description {
  if ((type_ == nil) && (value_ == nil)) {
    return @"";
  }
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] init];
  if (source_ != nil) {
    (void) [((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"["])) appendWithId:source_])) appendWithNSString:@"]"];
  }
  if (type_ != nil) {
    (void) [sb appendWithId:type_];
  }
  (void) [sb appendWithNSString:@"="];
  if (value_ != nil) {
    (void) [sb appendWithId:value_];
  }
  if (flags_ != 0) {
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"|"])) appendWithLong:flags_];
  }
  return [sb description];
}

- (BOOL)typeEqualsWithId:(id)type {
  if (type == nil) {
    return self->type_ == nil;
  }
  else {
    return [type isEqual:self->type_];
  }
}

- (BOOL)valueEqualsWithId:(id)value {
  if (value == nil) {
    return self->value_ == nil;
  }
  else {
    return [value isEqual:self->value_];
  }
}

- (void)setWithId:(id)type
           withId:(id)value {
  self->type_ = type;
  self->value_ = value;
}

- (void)setWithId:(id)type
           withId:(id)value
         withLong:(long long int)flags {
  self->type_ = type;
  self->value_ = value;
  self->flags_ = flags;
}

- (void)setSourceWithId:(id)source {
  self->source_ = source;
}

- (void)setTypeWithId:(id)type {
  self->type_ = type;
}

- (void)setValueWithId:(id)value {
  self->value_ = value;
}

- (id)getSource {
  return source_;
}

- (id)getType {
  return type_;
}

- (id)getValue {
  return value_;
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREUTObjectHolder *)other {
  [super copyAllFieldsTo:other];
  other->flags_ = flags_;
  other->source_ = source_;
  other->type_ = type_;
  other->value_ = value_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "typeEqualsWithId:", NULL, "Z", 0x1, NULL },
    { "valueEqualsWithId:", NULL, "Z", 0x1, NULL },
    { "setWithId:withId:", NULL, "V", 0x11, NULL },
    { "setWithId:withId:withLong:", NULL, "V", 0x11, NULL },
    { "getSource", NULL, "LNSObject", 0x1, NULL },
    { "getType", NULL, "LNSObject", 0x1, NULL },
    { "getValue", NULL, "LNSObject", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "flags_", NULL, 0x1, "J" },
    { "source_", NULL, 0x1, "LNSObject" },
    { "type_", NULL, 0x1, "LNSObject" },
    { "value_", NULL, 0x1, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREUTObjectHolder = { "ObjectHolder", "com.appnativa.util", NULL, 0x1, 8, methods, 4, fields, 0, NULL};
  return &_RAREUTObjectHolder;
}

@end
