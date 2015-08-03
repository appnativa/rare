//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Label.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/Label.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "java/lang/Boolean.h"
#include "java/lang/Integer.h"

@implementation RARESPOTLabel

- (id)init {
  return [self initRARESPOTLabelWithBoolean:YES];
}

- (id)initRARESPOTLabelWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    icon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    value_ = [[SPOTPrintableString alloc] init];
    labelFor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    textHAlignment_ = [[RARESPOTLabel_CTextHAlignment alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLabel_CTextHAlignment_auto] withNSString:@"auto" withBoolean:NO];
    textVAlignment_ = [[RARESPOTLabel_CTextVAlignment alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLabel_CTextVAlignment_auto] withNSString:@"auto" withBoolean:NO];
    iconPosition_ = [[RARESPOTLabel_CIconPosition alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLabel_CIconPosition_auto] withNSString:@"auto" withBoolean:NO];
    scaleIcon_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    orientation_ = [[RARESPOTLabel_COrientation alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLabel_COrientation_auto] withNSString:@"auto" withBoolean:NO];
    supportHyperLinks_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    wordWrap_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTLabelWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    icon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    value_ = [[SPOTPrintableString alloc] init];
    labelFor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    textHAlignment_ = [[RARESPOTLabel_CTextHAlignment alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLabel_CTextHAlignment_auto] withNSString:@"auto" withBoolean:NO];
    textVAlignment_ = [[RARESPOTLabel_CTextVAlignment alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLabel_CTextVAlignment_auto] withNSString:@"auto" withBoolean:NO];
    iconPosition_ = [[RARESPOTLabel_CIconPosition alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLabel_CIconPosition_auto] withNSString:@"auto" withBoolean:NO];
    scaleIcon_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    orientation_ = [[RARESPOTLabel_COrientation alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLabel_COrientation_auto] withNSString:@"auto" withBoolean:NO];
    supportHyperLinks_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    wordWrap_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 10;
  self->attributeSizeHint_ += 2;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onChange" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onAction" withNSString:nil];
  [self spot_addElementWithNSString:@"icon" withISPOTElement:icon_];
  [((SPOTPrintableString *) nil_chk(icon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"value" withISPOTElement:value_];
  [self spot_addElementWithNSString:@"labelFor" withISPOTElement:labelFor_];
  [self spot_addElementWithNSString:@"textHAlignment" withISPOTElement:textHAlignment_];
  [self spot_addElementWithNSString:@"textVAlignment" withISPOTElement:textVAlignment_];
  [self spot_addElementWithNSString:@"iconPosition" withISPOTElement:iconPosition_];
  [self spot_addElementWithNSString:@"scaleIcon" withISPOTElement:scaleIcon_];
  [((SPOTBoolean *) nil_chk(scaleIcon_)) spot_defineAttributeWithNSString:@"percent" withNSString:nil];
  [self spot_addElementWithNSString:@"orientation" withISPOTElement:orientation_];
  [self spot_addElementWithNSString:@"supportHyperLinks" withISPOTElement:supportHyperLinks_];
  [self spot_addElementWithNSString:@"wordWrap" withISPOTElement:wordWrap_];
}

- (void)copyAllFieldsTo:(RARESPOTLabel *)other {
  [super copyAllFieldsTo:other];
  other->icon_ = icon_;
  other->iconPosition_ = iconPosition_;
  other->labelFor_ = labelFor_;
  other->orientation_ = orientation_;
  other->scaleIcon_ = scaleIcon_;
  other->supportHyperLinks_ = supportHyperLinks_;
  other->textHAlignment_ = textHAlignment_;
  other->textVAlignment_ = textVAlignment_;
  other->value_ = value_;
  other->wordWrap_ = wordWrap_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "icon_", NULL, 0x1, "LSPOTPrintableString" },
    { "value_", NULL, 0x1, "LSPOTPrintableString" },
    { "labelFor_", NULL, 0x1, "LSPOTPrintableString" },
    { "textHAlignment_", NULL, 0x1, "LRARESPOTLabel_CTextHAlignment" },
    { "textVAlignment_", NULL, 0x1, "LRARESPOTLabel_CTextVAlignment" },
    { "iconPosition_", NULL, 0x1, "LRARESPOTLabel_CIconPosition" },
    { "scaleIcon_", NULL, 0x1, "LSPOTBoolean" },
    { "orientation_", NULL, 0x1, "LRARESPOTLabel_COrientation" },
    { "supportHyperLinks_", NULL, 0x1, "LSPOTBoolean" },
    { "wordWrap_", NULL, 0x1, "LSPOTBoolean" },
  };
  static J2ObjcClassInfo _RARESPOTLabel = { "Label", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 10, fields, 0, NULL};
  return &_RARESPOTLabel;
}

@end
@implementation RARESPOTLabel_CTextHAlignment

static IOSIntArray * RARESPOTLabel_CTextHAlignment__nchoices_;
static IOSObjectArray * RARESPOTLabel_CTextHAlignment__schoices_;

+ (int)getAuto {
  return RARESPOTLabel_CTextHAlignment_auto;
}

+ (int)left {
  return RARESPOTLabel_CTextHAlignment_left;
}

+ (int)right {
  return RARESPOTLabel_CTextHAlignment_right;
}

+ (int)leading {
  return RARESPOTLabel_CTextHAlignment_leading;
}

+ (int)trailing {
  return RARESPOTLabel_CTextHAlignment_trailing;
}

+ (int)center {
  return RARESPOTLabel_CTextHAlignment_center;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTLabel_CTextHAlignment__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTLabel_CTextHAlignment__schoices_;
}

- (id)init {
  return [self initRARESPOTLabel_CTextHAlignmentWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTLabel_CTextHAlignment__schoices_;
    _nChoices_ = RARESPOTLabel_CTextHAlignment__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTLabel_CTextHAlignmentWithJavaLangInteger:(JavaLangInteger *)ival
                                              withNSString:(NSString *)sval
                                       withJavaLangInteger:(JavaLangInteger *)idefaultval
                                              withNSString:(NSString *)sdefaultval
                                               withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTLabel_CTextHAlignment__schoices_;
    _nChoices_ = RARESPOTLabel_CTextHAlignment__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTLabel_CTextHAlignmentWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{auto(0), left(1), right(2), leading(3), trailing(4), center(5) }";
}

+ (void)initialize {
  if (self == [RARESPOTLabel_CTextHAlignment class]) {
    RARESPOTLabel_CTextHAlignment__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4, 5 } count:6];
    RARESPOTLabel_CTextHAlignment__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"auto", @"left", @"right", @"leading", @"trailing", @"center" } count:6 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "auto__", "auto", 0x19, "I" },
    { "left_", NULL, 0x19, "I" },
    { "right_", NULL, 0x19, "I" },
    { "leading_", NULL, 0x19, "I" },
    { "trailing_", NULL, 0x19, "I" },
    { "center_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTLabel_CTextHAlignment = { "CTextHAlignment", "com.appnativa.rare.spot", "Label", 0x9, 1, methods, 8, fields, 0, NULL};
  return &_RARESPOTLabel_CTextHAlignment;
}

@end
@implementation RARESPOTLabel_CTextVAlignment

static IOSIntArray * RARESPOTLabel_CTextVAlignment__nchoices_;
static IOSObjectArray * RARESPOTLabel_CTextVAlignment__schoices_;

+ (int)getAuto {
  return RARESPOTLabel_CTextVAlignment_auto;
}

+ (int)top {
  return RARESPOTLabel_CTextVAlignment_top;
}

+ (int)bottom {
  return RARESPOTLabel_CTextVAlignment_bottom;
}

+ (int)center {
  return RARESPOTLabel_CTextVAlignment_center;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTLabel_CTextVAlignment__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTLabel_CTextVAlignment__schoices_;
}

- (id)init {
  return [self initRARESPOTLabel_CTextVAlignmentWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTLabel_CTextVAlignment__schoices_;
    _nChoices_ = RARESPOTLabel_CTextVAlignment__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTLabel_CTextVAlignmentWithJavaLangInteger:(JavaLangInteger *)ival
                                              withNSString:(NSString *)sval
                                       withJavaLangInteger:(JavaLangInteger *)idefaultval
                                              withNSString:(NSString *)sdefaultval
                                               withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTLabel_CTextVAlignment__schoices_;
    _nChoices_ = RARESPOTLabel_CTextVAlignment__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTLabel_CTextVAlignmentWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{auto(0), top(1), bottom(2), center(5) }";
}

+ (void)initialize {
  if (self == [RARESPOTLabel_CTextVAlignment class]) {
    RARESPOTLabel_CTextVAlignment__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 5 } count:4];
    RARESPOTLabel_CTextVAlignment__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"auto", @"top", @"bottom", @"center" } count:4 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "auto__", "auto", 0x19, "I" },
    { "top_", NULL, 0x19, "I" },
    { "bottom_", NULL, 0x19, "I" },
    { "center_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTLabel_CTextVAlignment = { "CTextVAlignment", "com.appnativa.rare.spot", "Label", 0x9, 1, methods, 6, fields, 0, NULL};
  return &_RARESPOTLabel_CTextVAlignment;
}

@end
@implementation RARESPOTLabel_CIconPosition

static IOSIntArray * RARESPOTLabel_CIconPosition__nchoices_;
static IOSObjectArray * RARESPOTLabel_CIconPosition__schoices_;

+ (int)getAuto {
  return RARESPOTLabel_CIconPosition_auto;
}

+ (int)left {
  return RARESPOTLabel_CIconPosition_left;
}

+ (int)right {
  return RARESPOTLabel_CIconPosition_right;
}

+ (int)leading {
  return RARESPOTLabel_CIconPosition_leading;
}

+ (int)trailing {
  return RARESPOTLabel_CIconPosition_trailing;
}

+ (int)top_center {
  return RARESPOTLabel_CIconPosition_top_center;
}

+ (int)top_left {
  return RARESPOTLabel_CIconPosition_top_left;
}

+ (int)top_right {
  return RARESPOTLabel_CIconPosition_top_right;
}

+ (int)bottom_center {
  return RARESPOTLabel_CIconPosition_bottom_center;
}

+ (int)bottom_left {
  return RARESPOTLabel_CIconPosition_bottom_left;
}

+ (int)bottom_right {
  return RARESPOTLabel_CIconPosition_bottom_right;
}

+ (int)right_justified {
  return RARESPOTLabel_CIconPosition_right_justified;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTLabel_CIconPosition__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTLabel_CIconPosition__schoices_;
}

- (id)init {
  return [self initRARESPOTLabel_CIconPositionWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTLabel_CIconPosition__schoices_;
    _nChoices_ = RARESPOTLabel_CIconPosition__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTLabel_CIconPositionWithJavaLangInteger:(JavaLangInteger *)ival
                                            withNSString:(NSString *)sval
                                     withJavaLangInteger:(JavaLangInteger *)idefaultval
                                            withNSString:(NSString *)sdefaultval
                                             withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTLabel_CIconPosition__schoices_;
    _nChoices_ = RARESPOTLabel_CIconPosition__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTLabel_CIconPositionWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{auto(0), left(1), right(2), leading(3), trailing(4), top_center(5), top_left(6), top_right(7), bottom_center(8), bottom_left(9), bottom_right(10), right_justified(11) }";
}

+ (void)initialize {
  if (self == [RARESPOTLabel_CIconPosition class]) {
    RARESPOTLabel_CIconPosition__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 } count:12];
    RARESPOTLabel_CIconPosition__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"auto", @"left", @"right", @"leading", @"trailing", @"top_center", @"top_left", @"top_right", @"bottom_center", @"bottom_left", @"bottom_right", @"right_justified" } count:12 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "auto__", "auto", 0x19, "I" },
    { "left_", NULL, 0x19, "I" },
    { "right_", NULL, 0x19, "I" },
    { "leading_", NULL, 0x19, "I" },
    { "trailing_", NULL, 0x19, "I" },
    { "top_center_", NULL, 0x19, "I" },
    { "top_left_", NULL, 0x19, "I" },
    { "top_right_", NULL, 0x19, "I" },
    { "bottom_center_", NULL, 0x19, "I" },
    { "bottom_left_", NULL, 0x19, "I" },
    { "bottom_right_", NULL, 0x19, "I" },
    { "right_justified_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTLabel_CIconPosition = { "CIconPosition", "com.appnativa.rare.spot", "Label", 0x9, 1, methods, 14, fields, 0, NULL};
  return &_RARESPOTLabel_CIconPosition;
}

@end
@implementation RARESPOTLabel_COrientation

static IOSIntArray * RARESPOTLabel_COrientation__nchoices_;
static IOSObjectArray * RARESPOTLabel_COrientation__schoices_;

+ (int)getAuto {
  return RARESPOTLabel_COrientation_auto;
}

+ (int)horizontal {
  return RARESPOTLabel_COrientation_horizontal;
}

+ (int)vertical_up {
  return RARESPOTLabel_COrientation_vertical_up;
}

+ (int)vertical_down {
  return RARESPOTLabel_COrientation_vertical_down;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTLabel_COrientation__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTLabel_COrientation__schoices_;
}

- (id)init {
  return [self initRARESPOTLabel_COrientationWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTLabel_COrientation__schoices_;
    _nChoices_ = RARESPOTLabel_COrientation__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTLabel_COrientationWithJavaLangInteger:(JavaLangInteger *)ival
                                           withNSString:(NSString *)sval
                                    withJavaLangInteger:(JavaLangInteger *)idefaultval
                                           withNSString:(NSString *)sdefaultval
                                            withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTLabel_COrientation__schoices_;
    _nChoices_ = RARESPOTLabel_COrientation__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTLabel_COrientationWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{auto(0), horizontal(1), vertical_up(2), vertical_down(3) }";
}

+ (void)initialize {
  if (self == [RARESPOTLabel_COrientation class]) {
    RARESPOTLabel_COrientation__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3 } count:4];
    RARESPOTLabel_COrientation__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"auto", @"horizontal", @"vertical_up", @"vertical_down" } count:4 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "auto__", "auto", 0x19, "I" },
    { "horizontal_", NULL, 0x19, "I" },
    { "vertical_up_", NULL, 0x19, "I" },
    { "vertical_down_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTLabel_COrientation = { "COrientation", "com.appnativa.rare.spot", "Label", 0x9, 1, methods, 6, fields, 0, NULL};
  return &_RARESPOTLabel_COrientation;
}

@end