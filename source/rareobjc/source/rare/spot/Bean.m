//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Bean.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/Bean.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"

@implementation RARESPOTBean

- (id)init {
  return [self initRARESPOTBeanWithBoolean:YES];
}

- (id)initRARESPOTBeanWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    beanClass_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    beanJAR_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    beanURL_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    beanProperties_ = [[SPOTAny alloc] initWithNSString:@"com.appnativa.spot.SPOTSequence" withBoolean:YES];
    failureMessage_ = [[SPOTPrintableString alloc] init];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTBeanWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    beanClass_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    beanJAR_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    beanURL_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    beanProperties_ = [[SPOTAny alloc] initWithNSString:@"com.appnativa.spot.SPOTSequence" withBoolean:YES];
    failureMessage_ = [[SPOTPrintableString alloc] init];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 5;
  self->attributeSizeHint_ += 7;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onItemAdded" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onAction" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onChange" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onWillExpand" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onWillCollapse" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onHasCollapsed" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onHasExpanded" withNSString:nil];
  [self spot_addElementWithNSString:@"beanClass" withISPOTElement:beanClass_];
  [self spot_addElementWithNSString:@"beanJAR" withISPOTElement:beanJAR_];
  [self spot_addElementWithNSString:@"beanURL" withISPOTElement:beanURL_];
  [self spot_addElementWithNSString:@"beanProperties" withISPOTElement:beanProperties_];
  [self spot_addElementWithNSString:@"failureMessage" withISPOTElement:failureMessage_];
}

- (void)copyAllFieldsTo:(RARESPOTBean *)other {
  [super copyAllFieldsTo:other];
  other->beanClass_ = beanClass_;
  other->beanJAR_ = beanJAR_;
  other->beanProperties_ = beanProperties_;
  other->beanURL_ = beanURL_;
  other->failureMessage_ = failureMessage_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "beanClass_", NULL, 0x1, "LSPOTPrintableString" },
    { "beanJAR_", NULL, 0x1, "LSPOTPrintableString" },
    { "beanURL_", NULL, 0x1, "LSPOTPrintableString" },
    { "beanProperties_", NULL, 0x1, "LSPOTAny" },
    { "failureMessage_", NULL, 0x1, "LSPOTPrintableString" },
  };
  static J2ObjcClassInfo _RARESPOTBean = { "Bean", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 5, fields, 0, NULL};
  return &_RARESPOTBean;
}

@end
