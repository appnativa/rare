//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/exception/AbortOperationException.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/exception/AbortOperationException.h"
#include "com/appnativa/rare/iPlatformAppContext.h"

@implementation RAREAbortOperationException

- (id)init {
  return [super init];
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                          withBoolean:(BOOL)shutdown {
  if (self = [super init]) {
    self->shutdown__ = shutdown;
    self->appContext_ = app;
  }
  return self;
}

- (BOOL)shutdown {
  return shutdown__;
}

- (id<RAREiPlatformAppContext>)getAppContext {
  return appContext_;
}

- (void)copyAllFieldsTo:(RAREAbortOperationException *)other {
  [super copyAllFieldsTo:other];
  other->appContext_ = appContext_;
  other->shutdown__ = shutdown__;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "shutdown", NULL, "Z", 0x1, NULL },
    { "getAppContext", NULL, "LRAREiPlatformAppContext", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "appContext_", NULL, 0x0, "LRAREiPlatformAppContext" },
  };
  static J2ObjcClassInfo _RAREAbortOperationException = { "AbortOperationException", "com.appnativa.rare.exception", NULL, 0x1, 2, methods, 1, fields, 0, NULL};
  return &_RAREAbortOperationException;
}

@end
