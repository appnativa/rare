//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/porting/src/java/beans/PropertyChangeEvent.java
//
//  Created by decoteaud on 4/16/14.
//

#include "java/beans/PropertyChangeEvent.h"

@implementation JavaBeansPropertyChangeEvent

- (id)initWithId:(id)source
    withNSString:(NSString *)propertyName
          withId:(id)oldValue
          withId:(id)newValue {
  if (self = [super initWithId:source]) {
    self->propertyName_ = propertyName;
    self->oldValue_ = oldValue;
    self->newValue_ = newValue;
  }
  return self;
}

- (NSString *)getPropertyName {
  return propertyName_;
}

- (void)setPropagationIdWithId:(id)propagationId {
  self->propagationId_ = propagationId;
}

- (id)getPropagationId {
  return propagationId_;
}

- (id)getOldValue {
  return oldValue_;
}

- (id)getNewValue {
  return newValue_;
}

- (void)copyAllFieldsTo:(JavaBeansPropertyChangeEvent *)other {
  [super copyAllFieldsTo:other];
  other->newValue_ = newValue_;
  other->oldValue_ = oldValue_;
  other->propagationId_ = propagationId_;
  other->propertyName_ = propertyName_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getPropertyName", NULL, "LNSString", 0x1, NULL },
    { "getPropagationId", NULL, "LNSObject", 0x1, NULL },
    { "getOldValue", NULL, "LNSObject", 0x1, NULL },
    { "getNewValue", NULL, "LNSObject", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "propertyName_", NULL, 0x0, "LNSString" },
    { "oldValue_", NULL, 0x0, "LNSObject" },
    { "newValue_", NULL, 0x0, "LNSObject" },
    { "propagationId_", NULL, 0x0, "LNSObject" },
  };
  static J2ObjcClassInfo _JavaBeansPropertyChangeEvent = { "PropertyChangeEvent", "java.beans", NULL, 0x1, 4, methods, 4, fields, 0, NULL};
  return &_JavaBeansPropertyChangeEvent;
}

@end
