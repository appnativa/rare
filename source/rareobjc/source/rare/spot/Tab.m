//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Tab.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/Rectangle.h"
#include "com/appnativa/rare/spot/Tab.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "java/lang/Boolean.h"

@implementation RARESPOTTab

- (id)init {
  return [self initRARESPOTTabWithBoolean:YES];
}

- (id)initRARESPOTTabWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    title_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:355 withNSString:@"Tab" withBoolean:NO];
    icon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    disabledIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    alternateIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    loadOnActivation_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    reloadOnActivation_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    fgColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    tooltip_ = [[SPOTPrintableString alloc] init];
    linkedData_ = [[SPOTPrintableString alloc] init];
    enabled_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTTabWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    title_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:355 withNSString:@"Tab" withBoolean:NO];
    icon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    disabledIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    alternateIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    loadOnActivation_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    reloadOnActivation_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    fgColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    tooltip_ = [[SPOTPrintableString alloc] init];
    linkedData_ = [[SPOTPrintableString alloc] init];
    enabled_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 10;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"title" withISPOTElement:title_];
  [self spot_addElementWithNSString:@"icon" withISPOTElement:icon_];
  [((SPOTPrintableString *) nil_chk(icon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"disabledIcon" withISPOTElement:disabledIcon_];
  [((SPOTPrintableString *) nil_chk(disabledIcon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [disabledIcon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [disabledIcon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [disabledIcon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [disabledIcon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"alternateIcon" withISPOTElement:alternateIcon_];
  [((SPOTPrintableString *) nil_chk(alternateIcon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [alternateIcon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [alternateIcon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [alternateIcon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [alternateIcon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"loadOnActivation" withISPOTElement:loadOnActivation_];
  [self spot_addElementWithNSString:@"reloadOnActivation" withISPOTElement:reloadOnActivation_];
  [self spot_addElementWithNSString:@"fgColor" withISPOTElement:fgColor_];
  [self spot_addElementWithNSString:@"tooltip" withISPOTElement:tooltip_];
  [self spot_addElementWithNSString:@"linkedData" withISPOTElement:linkedData_];
  [self spot_addElementWithNSString:@"enabled" withISPOTElement:enabled_];
}

- (id)initWithNSString:(NSString *)title
          withNSString:(NSString *)targetName
          withNSString:(NSString *)icon
          withNSString:(NSString *)data {
  if (self = [self initRARESPOTTabWithBoolean:NO]) {
    [self setValuesWithNSString:title withNSString:targetName withNSString:icon withNSString:data];
  }
  return self;
}

- (void)setValuesWithNSString:(NSString *)title
                 withNSString:(NSString *)name
                 withNSString:(NSString *)icon {
  [((SPOTPrintableString *) nil_chk(self->icon_)) setValueWithNSString:icon];
  [((SPOTPrintableString *) nil_chk(self->name_)) setValueWithNSString:name];
  [((SPOTPrintableString *) nil_chk(self->title_)) setValueWithNSString:title];
}

- (void)copyAllFieldsTo:(RARESPOTTab *)other {
  [super copyAllFieldsTo:other];
  other->alternateIcon_ = alternateIcon_;
  other->disabledIcon_ = disabledIcon_;
  other->enabled_ = enabled_;
  other->fgColor_ = fgColor_;
  other->icon_ = icon_;
  other->linkedData_ = linkedData_;
  other->loadOnActivation_ = loadOnActivation_;
  other->reloadOnActivation_ = reloadOnActivation_;
  other->title_ = title_;
  other->tooltip_ = tooltip_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "title_", NULL, 0x1, "LSPOTPrintableString" },
    { "icon_", NULL, 0x1, "LSPOTPrintableString" },
    { "disabledIcon_", NULL, 0x1, "LSPOTPrintableString" },
    { "alternateIcon_", NULL, 0x1, "LSPOTPrintableString" },
    { "loadOnActivation_", NULL, 0x1, "LSPOTBoolean" },
    { "reloadOnActivation_", NULL, 0x1, "LSPOTBoolean" },
    { "fgColor_", NULL, 0x1, "LSPOTPrintableString" },
    { "tooltip_", NULL, 0x1, "LSPOTPrintableString" },
    { "linkedData_", NULL, 0x1, "LSPOTPrintableString" },
    { "enabled_", NULL, 0x1, "LSPOTBoolean" },
  };
  static J2ObjcClassInfo _RARESPOTTab = { "Tab", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 10, fields, 0, NULL};
  return &_RARESPOTTab;
}

@end
