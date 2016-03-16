//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/AccessibleContext.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/AccessibleContext.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"

@implementation RARESPOTAccessibleContext

- (id)init {
  return [self initRARESPOTAccessibleContextWithBoolean:YES];
}

- (id)initRARESPOTAccessibleContextWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    name_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    description__ = [[SPOTPrintableString alloc] init];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTAccessibleContextWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    name_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    description__ = [[SPOTPrintableString alloc] init];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 2;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"name" withISPOTElement:name_];
  [self spot_addElementWithNSString:@"description" withISPOTElement:description__];
}

- (void)copyAllFieldsTo:(RARESPOTAccessibleContext *)other {
  [super copyAllFieldsTo:other];
  other->description__ = description__;
  other->name_ = name_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "name_", NULL, 0x1, "LSPOTPrintableString" },
    { "description__", "description", 0x1, "LSPOTPrintableString" },
  };
  static J2ObjcClassInfo _RARESPOTAccessibleContext = { "AccessibleContext", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 2, fields, 0, NULL};
  return &_RARESPOTAccessibleContext;
}

@end
