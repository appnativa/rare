//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/exception/RestartException.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/exception/RestartException.h"

@implementation RARERestartException

- (id)init {
  return [super init];
}

- (void)setContextObjectWithId:(id)object {
  self->contextObject_ = object;
}

- (void)setTrackingCountWithInt:(int)count {
  self->trackingCount_ = count;
}

- (id)getContextObject {
  return contextObject_;
}

- (int)getTrackingCount {
  return trackingCount_;
}

- (int)incrementAndGetTrackingCount {
  return ++trackingCount_;
}

- (void)copyAllFieldsTo:(RARERestartException *)other {
  [super copyAllFieldsTo:other];
  other->contextObject_ = contextObject_;
  other->trackingCount_ = trackingCount_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getContextObject", NULL, "LNSObject", 0x1, NULL },
  };
  static J2ObjcClassInfo _RARERestartException = { "RestartException", "com.appnativa.rare.exception", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RARERestartException;
}

@end
