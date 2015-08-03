//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Form.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/Form.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "java/lang/Boolean.h"

@implementation RARESPOTForm

- (id)init {
  return [self initRARESPOTFormWithBoolean:YES];
}

- (id)initRARESPOTFormWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    actAsFormViewer_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    submitAttributes_ = [[SPOTPrintableString alloc] init];
    retainInitialFieldValues_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    defaultButtonName_ = [[SPOTPrintableString alloc] init];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTFormWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    actAsFormViewer_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    submitAttributes_ = [[SPOTPrintableString alloc] init];
    retainInitialFieldValues_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    defaultButtonName_ = [[SPOTPrintableString alloc] init];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 4;
  self->attributeSizeHint_ += 2;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onSubmit" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onReset" withNSString:nil];
  [self spot_addElementWithNSString:@"actAsFormViewer" withISPOTElement:actAsFormViewer_];
  [self spot_addElementWithNSString:@"submitAttributes" withISPOTElement:submitAttributes_];
  [((SPOTPrintableString *) nil_chk(submitAttributes_)) spot_defineAttributeWithNSString:@"mimeType" withNSString:nil];
  [self spot_addElementWithNSString:@"retainInitialFieldValues" withISPOTElement:retainInitialFieldValues_];
  [self spot_addElementWithNSString:@"defaultButtonName" withISPOTElement:defaultButtonName_];
}

- (void)copyAllFieldsTo:(RARESPOTForm *)other {
  [super copyAllFieldsTo:other];
  other->actAsFormViewer_ = actAsFormViewer_;
  other->defaultButtonName_ = defaultButtonName_;
  other->retainInitialFieldValues_ = retainInitialFieldValues_;
  other->submitAttributes_ = submitAttributes_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "actAsFormViewer_", NULL, 0x1, "LSPOTBoolean" },
    { "submitAttributes_", NULL, 0x1, "LSPOTPrintableString" },
    { "retainInitialFieldValues_", NULL, 0x1, "LSPOTBoolean" },
    { "defaultButtonName_", NULL, 0x1, "LSPOTPrintableString" },
  };
  static J2ObjcClassInfo _RARESPOTForm = { "Form", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 4, fields, 0, NULL};
  return &_RARESPOTForm;
}

@end