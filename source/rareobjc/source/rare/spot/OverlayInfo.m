//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/OverlayInfo.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/OverlayInfo.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "java/lang/Integer.h"

@implementation RARESPOTOverlayInfo

- (id)init {
  return [self initRARESPOTOverlayInfoWithBoolean:YES];
}

- (id)initRARESPOTOverlayInfoWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    widget_ = [[SPOTAny alloc] initWithNSString:@"com.appnativa.rare.spot.Widget"];
    location_ = [[RARESPOTOverlayInfo_CLocation alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTOverlayInfo_CLocation_lower_right] withNSString:@"lower_right" withBoolean:NO];
    displayed_ = [[RARESPOTOverlayInfo_CDisplayed alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTOverlayInfo_CDisplayed_always] withNSString:@"always" withBoolean:NO];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTOverlayInfoWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    widget_ = [[SPOTAny alloc] initWithNSString:@"com.appnativa.rare.spot.Widget"];
    location_ = [[RARESPOTOverlayInfo_CLocation alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTOverlayInfo_CLocation_lower_right] withNSString:@"lower_right" withBoolean:NO];
    displayed_ = [[RARESPOTOverlayInfo_CDisplayed alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTOverlayInfo_CDisplayed_always] withNSString:@"always" withBoolean:NO];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 3;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"widget" withISPOTElement:widget_];
  [self spot_addElementWithNSString:@"location" withISPOTElement:location_];
  [self spot_addElementWithNSString:@"displayed" withISPOTElement:displayed_];
}

- (void)copyAllFieldsTo:(RARESPOTOverlayInfo *)other {
  [super copyAllFieldsTo:other];
  other->displayed_ = displayed_;
  other->location_ = location_;
  other->widget_ = widget_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "widget_", NULL, 0x1, "LSPOTAny" },
    { "location_", NULL, 0x1, "LRARESPOTOverlayInfo_CLocation" },
    { "displayed_", NULL, 0x1, "LRARESPOTOverlayInfo_CDisplayed" },
  };
  static J2ObjcClassInfo _RARESPOTOverlayInfo = { "OverlayInfo", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 3, fields, 0, NULL};
  return &_RARESPOTOverlayInfo;
}

@end
@implementation RARESPOTOverlayInfo_CLocation

static IOSIntArray * RARESPOTOverlayInfo_CLocation__nchoices_;
static IOSObjectArray * RARESPOTOverlayInfo_CLocation__schoices_;

+ (int)upper_left {
  return RARESPOTOverlayInfo_CLocation_upper_left;
}

+ (int)upper_middle {
  return RARESPOTOverlayInfo_CLocation_upper_middle;
}

+ (int)upper_right {
  return RARESPOTOverlayInfo_CLocation_upper_right;
}

+ (int)left_middle {
  return RARESPOTOverlayInfo_CLocation_left_middle;
}

+ (int)centered {
  return RARESPOTOverlayInfo_CLocation_centered;
}

+ (int)right_middle {
  return RARESPOTOverlayInfo_CLocation_right_middle;
}

+ (int)lower_left {
  return RARESPOTOverlayInfo_CLocation_lower_left;
}

+ (int)lower_middle {
  return RARESPOTOverlayInfo_CLocation_lower_middle;
}

+ (int)lower_right {
  return RARESPOTOverlayInfo_CLocation_lower_right;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTOverlayInfo_CLocation__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTOverlayInfo_CLocation__schoices_;
}

- (id)init {
  return [self initRARESPOTOverlayInfo_CLocationWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTOverlayInfo_CLocation__schoices_;
    _nChoices_ = RARESPOTOverlayInfo_CLocation__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTOverlayInfo_CLocationWithJavaLangInteger:(JavaLangInteger *)ival
                                              withNSString:(NSString *)sval
                                       withJavaLangInteger:(JavaLangInteger *)idefaultval
                                              withNSString:(NSString *)sdefaultval
                                               withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTOverlayInfo_CLocation__schoices_;
    _nChoices_ = RARESPOTOverlayInfo_CLocation__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTOverlayInfo_CLocationWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{upper_left(1), upper_middle(2), upper_right(3), left_middle(4), centered(5), right_middle(6), lower_left(7), lower_middle(8), lower_right(9) }";
}

+ (void)initialize {
  if (self == [RARESPOTOverlayInfo_CLocation class]) {
    RARESPOTOverlayInfo_CLocation__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 1, 2, 3, 4, 5, 6, 7, 8, 9 } count:9];
    RARESPOTOverlayInfo_CLocation__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"upper_left", @"upper_middle", @"upper_right", @"left_middle", @"centered", @"right_middle", @"lower_left", @"lower_middle", @"lower_right" } count:9 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "upper_left_", NULL, 0x19, "I" },
    { "upper_middle_", NULL, 0x19, "I" },
    { "upper_right_", NULL, 0x19, "I" },
    { "left_middle_", NULL, 0x19, "I" },
    { "centered_", NULL, 0x19, "I" },
    { "right_middle_", NULL, 0x19, "I" },
    { "lower_left_", NULL, 0x19, "I" },
    { "lower_middle_", NULL, 0x19, "I" },
    { "lower_right_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTOverlayInfo_CLocation = { "CLocation", "com.appnativa.rare.spot", "OverlayInfo", 0x9, 1, methods, 11, fields, 0, NULL};
  return &_RARESPOTOverlayInfo_CLocation;
}

@end
@implementation RARESPOTOverlayInfo_CDisplayed

static IOSIntArray * RARESPOTOverlayInfo_CDisplayed__nchoices_;
static IOSObjectArray * RARESPOTOverlayInfo_CDisplayed__schoices_;

+ (int)always {
  return RARESPOTOverlayInfo_CDisplayed_always;
}

+ (int)when_not_focused {
  return RARESPOTOverlayInfo_CDisplayed_when_not_focused;
}

+ (int)when_empty {
  return RARESPOTOverlayInfo_CDisplayed_when_empty;
}

+ (int)before_interaction {
  return RARESPOTOverlayInfo_CDisplayed_before_interaction;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTOverlayInfo_CDisplayed__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTOverlayInfo_CDisplayed__schoices_;
}

- (id)init {
  return [self initRARESPOTOverlayInfo_CDisplayedWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTOverlayInfo_CDisplayed__schoices_;
    _nChoices_ = RARESPOTOverlayInfo_CDisplayed__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTOverlayInfo_CDisplayedWithJavaLangInteger:(JavaLangInteger *)ival
                                               withNSString:(NSString *)sval
                                        withJavaLangInteger:(JavaLangInteger *)idefaultval
                                               withNSString:(NSString *)sdefaultval
                                                withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTOverlayInfo_CDisplayed__schoices_;
    _nChoices_ = RARESPOTOverlayInfo_CDisplayed__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTOverlayInfo_CDisplayedWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{always(1), when_not_focused(2), when_empty(3), before_interaction(4) }";
}

+ (void)initialize {
  if (self == [RARESPOTOverlayInfo_CDisplayed class]) {
    RARESPOTOverlayInfo_CDisplayed__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 1, 2, 3, 4 } count:4];
    RARESPOTOverlayInfo_CDisplayed__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"always", @"when_not_focused", @"when_empty", @"before_interaction" } count:4 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "always_", NULL, 0x19, "I" },
    { "when_not_focused_", NULL, 0x19, "I" },
    { "when_empty_", NULL, 0x19, "I" },
    { "before_interaction_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTOverlayInfo_CDisplayed = { "CDisplayed", "com.appnativa.rare.spot", "OverlayInfo", 0x9, 1, methods, 6, fields, 0, NULL};
  return &_RARESPOTOverlayInfo_CDisplayed;
}

@end
