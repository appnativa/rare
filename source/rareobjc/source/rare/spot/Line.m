//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Line.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/Line.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTLine

- (id)init {
  return [self initRARESPOTLineWithBoolean:YES];
}

- (id)initRARESPOTLineWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    horizontal_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    leftLabel_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    leftIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    rightLabel_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:80 withBoolean:YES];
    rightIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    lines_ = nil;
    position_ = [[RARESPOTLine_CPosition alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLine_CPosition_center] withNSString:@"center" withBoolean:NO];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTLineWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    horizontal_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    leftLabel_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    leftIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    rightLabel_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:80 withBoolean:YES];
    rightIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    lines_ = nil;
    position_ = [[RARESPOTLine_CPosition alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLine_CPosition_center] withNSString:@"center" withBoolean:NO];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 7;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"horizontal" withISPOTElement:horizontal_];
  [self spot_addElementWithNSString:@"leftLabel" withISPOTElement:leftLabel_];
  [self spot_addElementWithNSString:@"leftIcon" withISPOTElement:leftIcon_];
  [((SPOTPrintableString *) nil_chk(leftIcon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [leftIcon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [leftIcon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [leftIcon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [leftIcon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"rightLabel" withISPOTElement:rightLabel_];
  [self spot_addElementWithNSString:@"rightIcon" withISPOTElement:rightIcon_];
  [((SPOTPrintableString *) nil_chk(rightIcon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [rightIcon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [rightIcon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [rightIcon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [rightIcon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"lines" withISPOTElement:lines_];
  [self spot_addElementWithNSString:@"position" withISPOTElement:position_];
}

- (SPOTSet *)getLines {
  return lines_;
}

- (SPOTSet *)getLinesReference {
  if (lines_ == nil) {
    lines_ = [[SPOTSet alloc] initWithNSString:@"lineStyle" withISPOTElement:[[RARESPOTLine_CLineStyle alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLine_CLineStyle_separator] withNSString:@"separator" withBoolean:NO] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"lines" withISPOTElement:lines_];
  }
  return lines_;
}

- (void)setLinesWithISPOTElement:(id<iSPOTElement>)reference {
  lines_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"lines" withISPOTElement:reference];
}

- (RARESPOTLine_CLineStyle *)addLineWithInt:(int)line {
  RARESPOTLine_CLineStyle *e = [[RARESPOTLine_CLineStyle alloc] initWithInt:line];
  [((SPOTSet *) nil_chk([self getLinesReference])) addWithISPOTElement:e];
  return e;
}

- (RARESPOTLine_CLineStyle *)addLineWithNSString:(NSString *)line {
  RARESPOTLine_CLineStyle *e = [[RARESPOTLine_CLineStyle alloc] init];
  [e setValueWithNSString:line];
  [((SPOTSet *) nil_chk([self getLinesReference])) addWithISPOTElement:e];
  return e;
}

+ (RARESPOTLine_CLineStyle *)createLineWithNSString:(NSString *)line {
  RARESPOTLine_CLineStyle *e = [[RARESPOTLine_CLineStyle alloc] init];
  [e setValueWithNSString:line];
  return e;
}

- (RARESPOTLine_CLineStyle *)setLineWithInt:(int)line {
  RARESPOTLine_CLineStyle *e = [[RARESPOTLine_CLineStyle alloc] initWithInt:line];
  [((SPOTSet *) nil_chk([self getLinesReference])) clear];
  [((SPOTSet *) nil_chk([self getLinesReference])) addWithISPOTElement:e];
  return e;
}

- (RARESPOTLine_CLineStyle *)setLineWithNSString:(NSString *)line {
  RARESPOTLine_CLineStyle *e = [[RARESPOTLine_CLineStyle alloc] init];
  [e setValueWithNSString:line];
  [((SPOTSet *) nil_chk([self getLinesReference])) clear];
  [((SPOTSet *) nil_chk([self getLinesReference])) addWithISPOTElement:e];
  return e;
}

- (void)setLineAttributeWithNSString:(NSString *)name
                        withNSString:(NSString *)value {
  if ((lines_ == nil) || ([lines_ getCount] == 0)) {
    SPOTEnumerated *e = [[RARESPOTLine_CLineStyle alloc] initWithInt:RARESPOTLine_CLineStyle_separator];
    [((SPOTSet *) nil_chk([self getLinesReference])) addWithISPOTElement:e];
  }
  [((id<iSPOTElement>) nil_chk([((SPOTSet *) nil_chk(lines_)) getExWithInt:0])) spot_setAttributeWithNSString:name withNSString:value];
}

- (RARESPOTLine_CLineStyle *)getLine {
  if ((lines_ == nil) || ([lines_ getCount] == 0)) {
    SPOTEnumerated *e = [[RARESPOTLine_CLineStyle alloc] initWithInt:RARESPOTLine_CLineStyle_separator];
    [((SPOTSet *) nil_chk([self getLinesReference])) addWithISPOTElement:e];
  }
  return (RARESPOTLine_CLineStyle *) check_class_cast([((SPOTSet *) nil_chk(lines_)) getWithInt:0], [RARESPOTLine_CLineStyle class]);
}

- (void)copyAllFieldsTo:(RARESPOTLine *)other {
  [super copyAllFieldsTo:other];
  other->horizontal_ = horizontal_;
  other->leftIcon_ = leftIcon_;
  other->leftLabel_ = leftLabel_;
  other->lines_ = lines_;
  other->position_ = position_;
  other->rightIcon_ = rightIcon_;
  other->rightLabel_ = rightLabel_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getLines", NULL, "LSPOTSet", 0x1, NULL },
    { "getLinesReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setLinesWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "addLineWithInt:", NULL, "LRARESPOTLine_CLineStyle", 0x1, NULL },
    { "addLineWithNSString:", NULL, "LRARESPOTLine_CLineStyle", 0x1, NULL },
    { "createLineWithNSString:", NULL, "LRARESPOTLine_CLineStyle", 0x9, NULL },
    { "setLineWithInt:", NULL, "LRARESPOTLine_CLineStyle", 0x1, NULL },
    { "setLineWithNSString:", NULL, "LRARESPOTLine_CLineStyle", 0x1, NULL },
    { "getLine", NULL, "LRARESPOTLine_CLineStyle", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "horizontal_", NULL, 0x1, "LSPOTBoolean" },
    { "leftLabel_", NULL, 0x1, "LSPOTPrintableString" },
    { "leftIcon_", NULL, 0x1, "LSPOTPrintableString" },
    { "rightLabel_", NULL, 0x1, "LSPOTPrintableString" },
    { "rightIcon_", NULL, 0x1, "LSPOTPrintableString" },
    { "lines_", NULL, 0x4, "LSPOTSet" },
    { "position_", NULL, 0x1, "LRARESPOTLine_CPosition" },
  };
  static J2ObjcClassInfo _RARESPOTLine = { "Line", "com.appnativa.rare.spot", NULL, 0x1, 11, methods, 7, fields, 0, NULL};
  return &_RARESPOTLine;
}

@end
@implementation RARESPOTLine_CLineStyle

static IOSIntArray * RARESPOTLine_CLineStyle__nchoices_;
static IOSObjectArray * RARESPOTLine_CLineStyle__schoices_;

+ (int)separator {
  return RARESPOTLine_CLineStyle_separator;
}

+ (int)solid {
  return RARESPOTLine_CLineStyle_solid;
}

+ (int)dashed {
  return RARESPOTLine_CLineStyle_dashed;
}

+ (int)dotted {
  return RARESPOTLine_CLineStyle_dotted;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTLine_CLineStyle__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTLine_CLineStyle__schoices_;
}

- (id)init {
  return [self initRARESPOTLine_CLineStyleWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTLine_CLineStyle__schoices_;
    _nChoices_ = RARESPOTLine_CLineStyle__nchoices_;
    [self spot_setAttributes];
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTLine_CLineStyleWithJavaLangInteger:(JavaLangInteger *)ival
                                        withNSString:(NSString *)sval
                                 withJavaLangInteger:(JavaLangInteger *)idefaultval
                                        withNSString:(NSString *)sdefaultval
                                         withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTLine_CLineStyle__schoices_;
    _nChoices_ = RARESPOTLine_CLineStyle__nchoices_;
    [self spot_setAttributes];
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTLine_CLineStyleWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{separator(1), solid(2), dashed(3), dotted(4) }";
}

- (void)spot_setAttributes {
  self->attributeSizeHint_ += 4;
  [self spot_defineAttributeWithNSString:@"color" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"thickness" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"leftOffset" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"rightOffset" withNSString:nil];
}

+ (void)initialize {
  if (self == [RARESPOTLine_CLineStyle class]) {
    RARESPOTLine_CLineStyle__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 1, 2, 3, 4 } count:4];
    RARESPOTLine_CLineStyle__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"separator", @"solid", @"dashed", @"dotted" } count:4 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_setAttributes", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "separator_", NULL, 0x19, "I" },
    { "solid_", NULL, 0x19, "I" },
    { "dashed_", NULL, 0x19, "I" },
    { "dotted_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTLine_CLineStyle = { "CLineStyle", "com.appnativa.rare.spot", "Line", 0x9, 2, methods, 6, fields, 0, NULL};
  return &_RARESPOTLine_CLineStyle;
}

@end
@implementation RARESPOTLine_CPosition

static IOSIntArray * RARESPOTLine_CPosition__nchoices_;
static IOSObjectArray * RARESPOTLine_CPosition__schoices_;

+ (int)top {
  return RARESPOTLine_CPosition_top;
}

+ (int)center {
  return RARESPOTLine_CPosition_center;
}

+ (int)bottom {
  return RARESPOTLine_CPosition_bottom;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTLine_CPosition__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTLine_CPosition__schoices_;
}

- (id)init {
  return [self initRARESPOTLine_CPositionWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTLine_CPosition__schoices_;
    _nChoices_ = RARESPOTLine_CPosition__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTLine_CPositionWithJavaLangInteger:(JavaLangInteger *)ival
                                       withNSString:(NSString *)sval
                                withJavaLangInteger:(JavaLangInteger *)idefaultval
                                       withNSString:(NSString *)sdefaultval
                                        withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTLine_CPosition__schoices_;
    _nChoices_ = RARESPOTLine_CPosition__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTLine_CPositionWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{top(1), center(2), bottom(3) }";
}

+ (void)initialize {
  if (self == [RARESPOTLine_CPosition class]) {
    RARESPOTLine_CPosition__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 1, 2, 3 } count:3];
    RARESPOTLine_CPosition__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"top", @"center", @"bottom" } count:3 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "top_", NULL, 0x19, "I" },
    { "center_", NULL, 0x19, "I" },
    { "bottom_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTLine_CPosition = { "CPosition", "com.appnativa.rare.spot", "Line", 0x9, 1, methods, 5, fields, 0, NULL};
  return &_RARESPOTLine_CPosition;
}

@end
