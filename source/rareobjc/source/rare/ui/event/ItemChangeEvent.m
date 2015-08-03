//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/ItemChangeEvent.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/event/ItemChangeEvent.h"
#include "java/lang/StringBuilder.h"

@implementation RAREItemChangeEvent

- (id)initWithId:(id)source
          withId:(id)oldValue
          withId:(id)newValue {
  if (self = [super initWithId:source]) {
    self->oldValue_ = oldValue;
    self->newValue_ = newValue;
  }
  return self;
}

- (void)rejectWithNSString:(NSString *)message {
  rejectMessage_ = message;
  rejected_ = YES;
}

- (NSString *)description {
  JavaLangStringBuilder *s = [[JavaLangStringBuilder alloc] initWithNSString:@"oldValue="];
  if (oldValue_ == nil) {
    (void) [s appendWithNSString:@"null"];
  }
  else {
    (void) [((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([s appendWithChar:'"'])) appendWithId:oldValue_])) appendWithChar:'"'];
  }
  (void) [s appendWithNSString:@", newValue="];
  if (newValue_ == nil) {
    (void) [s appendWithNSString:@"null"];
  }
  else {
    (void) [((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([s appendWithChar:'"'])) appendWithId:newValue_])) appendWithChar:'"'];
  }
  return [s description];
}

- (void)setNewValueWithId:(id)value {
  self->newValue_ = value;
}

- (id)getNewValue {
  return newValue_;
}

- (id)getOldValue {
  return oldValue_;
}

- (NSString *)getRejectionMessage {
  return rejectMessage_;
}

- (BOOL)isRejected {
  return rejected_;
}

- (void)copyAllFieldsTo:(RAREItemChangeEvent *)other {
  [super copyAllFieldsTo:other];
  other->newValue_ = newValue_;
  other->oldValue_ = oldValue_;
  other->rejectMessage_ = rejectMessage_;
  other->rejected_ = rejected_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getNewValue", NULL, "LNSObject", 0x1, NULL },
    { "getOldValue", NULL, "LNSObject", 0x1, NULL },
    { "getRejectionMessage", NULL, "LNSString", 0x1, NULL },
    { "isRejected", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREItemChangeEvent = { "ItemChangeEvent", "com.appnativa.rare.ui.event", NULL, 0x1, 4, methods, 0, NULL, 0, NULL};
  return &_RAREItemChangeEvent;
}

@end