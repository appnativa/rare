//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/GridCell.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/GridCell.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTGridCell

- (id)init {
  return [self initRARESPOTGridCellWithBoolean:YES];
}

- (id)initRARESPOTGridCellWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    bgColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:128 withBoolean:YES];
    bgImageURL_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    borders_ = nil;
    templateName_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTGridCellWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    bgColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:128 withBoolean:YES];
    bgImageURL_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    borders_ = nil;
    templateName_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 4;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"bgColor" withISPOTElement:bgColor_];
  [((SPOTPrintableString *) nil_chk(bgColor_)) spot_defineAttributeWithNSString:@"type" withNSString:@"linear"];
  [bgColor_ spot_defineAttributeWithNSString:@"direction" withNSString:@"vertical_top"];
  [bgColor_ spot_defineAttributeWithNSString:@"magnitude" withNSString:nil];
  [bgColor_ spot_defineAttributeWithNSString:@"distribution" withNSString:nil];
  [bgColor_ spot_defineAttributeWithNSString:@"radius" withNSString:nil];
  [self spot_addElementWithNSString:@"bgImageURL" withISPOTElement:bgImageURL_];
  [((SPOTPrintableString *) nil_chk(bgImageURL_)) spot_defineAttributeWithNSString:@"opacity" withNSString:@"100%"];
  [bgImageURL_ spot_defineAttributeWithNSString:@"renderType" withNSString:@"tiled"];
  [bgImageURL_ spot_defineAttributeWithNSString:@"waitForLoad" withNSString:@"false"];
  [bgImageURL_ spot_defineAttributeWithNSString:@"renderSpace" withNSString:@"within_border"];
  [bgImageURL_ spot_defineAttributeWithNSString:@"composite" withNSString:@"src_over"];
  [bgImageURL_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [bgImageURL_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"borders" withISPOTElement:borders_];
  [self spot_addElementWithNSString:@"templateName" withISPOTElement:templateName_];
  [((SPOTPrintableString *) nil_chk(templateName_)) spot_defineAttributeWithNSString:@"context" withNSString:nil];
}

- (SPOTSet *)getBorders {
  return borders_;
}

- (SPOTSet *)getBordersReference {
  if (borders_ == nil) {
    borders_ = [[SPOTSet alloc] initWithNSString:@"border" withISPOTElement:[[RARESPOTGridCell_CBorder alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTGridCell_CBorder_standard] withNSString:@"standard" withBoolean:YES] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"borders" withISPOTElement:borders_];
  }
  return borders_;
}

- (void)setBordersWithISPOTElement:(id<iSPOTElement>)reference {
  borders_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"borders" withISPOTElement:reference];
}

+ (int)getBorderTypeWithNSString:(NSString *)name {
  RARESPOTGridCell_CBorder *bt = [[RARESPOTGridCell_CBorder alloc] init];
  return [bt getChoiceIndexByNameWithNSString:name];
}

- (RARESPOTGridCell_CBorder *)addBorderWithInt:(int)border {
  RARESPOTGridCell_CBorder *e = [[RARESPOTGridCell_CBorder alloc] initWithInt:border];
  [((SPOTSet *) nil_chk([self getBordersReference])) addWithISPOTElement:e];
  return e;
}

- (RARESPOTGridCell_CBorder *)addBorderWithNSString:(NSString *)border {
  RARESPOTGridCell_CBorder *e = [[RARESPOTGridCell_CBorder alloc] init];
  [e setValueWithNSString:border];
  [((SPOTSet *) nil_chk([self getBordersReference])) addWithISPOTElement:e];
  return e;
}

- (void)copyAllFieldsTo:(RARESPOTGridCell *)other {
  [super copyAllFieldsTo:other];
  other->bgColor_ = bgColor_;
  other->bgImageURL_ = bgImageURL_;
  other->borders_ = borders_;
  other->templateName_ = templateName_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getBorders", NULL, "LSPOTSet", 0x1, NULL },
    { "getBordersReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setBordersWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "addBorderWithInt:", NULL, "LRARESPOTGridCell_CBorder", 0x1, NULL },
    { "addBorderWithNSString:", NULL, "LRARESPOTGridCell_CBorder", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "bgColor_", NULL, 0x1, "LSPOTPrintableString" },
    { "bgImageURL_", NULL, 0x1, "LSPOTPrintableString" },
    { "borders_", NULL, 0x4, "LSPOTSet" },
    { "templateName_", NULL, 0x1, "LSPOTPrintableString" },
  };
  static J2ObjcClassInfo _RARESPOTGridCell = { "GridCell", "com.appnativa.rare.spot", NULL, 0x1, 7, methods, 4, fields, 0, NULL};
  return &_RARESPOTGridCell;
}

@end
@implementation RARESPOTGridCell_CBorder

static IOSIntArray * RARESPOTGridCell_CBorder__nchoices_;
static IOSObjectArray * RARESPOTGridCell_CBorder__schoices_;

+ (int)none {
  return RARESPOTGridCell_CBorder_none;
}

+ (int)standard {
  return RARESPOTGridCell_CBorder_standard;
}

+ (int)line {
  return RARESPOTGridCell_CBorder_line;
}

+ (int)empty {
  return RARESPOTGridCell_CBorder_empty;
}

+ (int)raised {
  return RARESPOTGridCell_CBorder_raised;
}

+ (int)lowered {
  return RARESPOTGridCell_CBorder_lowered;
}

+ (int)bevel_raised {
  return RARESPOTGridCell_CBorder_bevel_raised;
}

+ (int)bevel_lowered {
  return RARESPOTGridCell_CBorder_bevel_lowered;
}

+ (int)etched_raised {
  return RARESPOTGridCell_CBorder_etched_raised;
}

+ (int)etched_lowered {
  return RARESPOTGridCell_CBorder_etched_lowered;
}

+ (int)frame_raised {
  return RARESPOTGridCell_CBorder_frame_raised;
}

+ (int)frame_lowered {
  return RARESPOTGridCell_CBorder_frame_lowered;
}

+ (int)drop_shadow {
  return RARESPOTGridCell_CBorder_drop_shadow;
}

+ (int)shadow {
  return RARESPOTGridCell_CBorder_shadow;
}

+ (int)group_box {
  return RARESPOTGridCell_CBorder_group_box;
}

+ (int)icon {
  return RARESPOTGridCell_CBorder_icon;
}

+ (int)matte {
  return RARESPOTGridCell_CBorder_matte;
}

+ (int)back {
  return RARESPOTGridCell_CBorder_back;
}

+ (int)balloon {
  return RARESPOTGridCell_CBorder_balloon;
}

+ (int)titled {
  return RARESPOTGridCell_CBorder_titled;
}

+ (int)custom {
  return RARESPOTGridCell_CBorder_custom;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTGridCell_CBorder__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTGridCell_CBorder__schoices_;
}

- (id)init {
  return [self initRARESPOTGridCell_CBorderWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTGridCell_CBorder__schoices_;
    _nChoices_ = RARESPOTGridCell_CBorder__nchoices_;
    [self spot_setAttributes];
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTGridCell_CBorderWithJavaLangInteger:(JavaLangInteger *)ival
                                         withNSString:(NSString *)sval
                                  withJavaLangInteger:(JavaLangInteger *)idefaultval
                                         withNSString:(NSString *)sdefaultval
                                          withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTGridCell_CBorder__schoices_;
    _nChoices_ = RARESPOTGridCell_CBorder__nchoices_;
    [self spot_setAttributes];
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTGridCell_CBorderWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{none(0), standard(1), line(2), empty(3), raised(4), lowered(5), bevel_raised(6), bevel_lowered(7), etched_raised(8), etched_lowered(9), frame_raised(10), frame_lowered(11), drop_shadow(12), shadow(13), group_box(14), icon(15), matte(16), back(17), balloon(18), titled(19), custom(31) }";
}

- (void)spot_setAttributes {
  self->attributeSizeHint_ += 21;
  [self spot_defineAttributeWithNSString:@"color" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"thickness" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"style" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"cornerArc" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"padForArc" withNSString:@"true"];
  [self spot_defineAttributeWithNSString:@"insets" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"title" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"titleLocation" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"titleFont" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"titleColor" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"icon" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"flatTop" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"flatBottom" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"noBottom" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"noTop" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"noLeft" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"noRight" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"class" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"renderType" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"opacity" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"customProperties" withNSString:nil];
}

+ (void)initialize {
  if (self == [RARESPOTGridCell_CBorder class]) {
    RARESPOTGridCell_CBorder__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 31 } count:21];
    RARESPOTGridCell_CBorder__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"none", @"standard", @"line", @"empty", @"raised", @"lowered", @"bevel_raised", @"bevel_lowered", @"etched_raised", @"etched_lowered", @"frame_raised", @"frame_lowered", @"drop_shadow", @"shadow", @"group_box", @"icon", @"matte", @"back", @"balloon", @"titled", @"custom" } count:21 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_setAttributes", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "none_", NULL, 0x19, "I" },
    { "standard_", NULL, 0x19, "I" },
    { "line_", NULL, 0x19, "I" },
    { "empty_", NULL, 0x19, "I" },
    { "raised_", NULL, 0x19, "I" },
    { "lowered_", NULL, 0x19, "I" },
    { "bevel_raised_", NULL, 0x19, "I" },
    { "bevel_lowered_", NULL, 0x19, "I" },
    { "etched_raised_", NULL, 0x19, "I" },
    { "etched_lowered_", NULL, 0x19, "I" },
    { "frame_raised_", NULL, 0x19, "I" },
    { "frame_lowered_", NULL, 0x19, "I" },
    { "drop_shadow_", NULL, 0x19, "I" },
    { "shadow_", NULL, 0x19, "I" },
    { "group_box_", NULL, 0x19, "I" },
    { "icon_", NULL, 0x19, "I" },
    { "matte_", NULL, 0x19, "I" },
    { "back_", NULL, 0x19, "I" },
    { "balloon_", NULL, 0x19, "I" },
    { "titled_", NULL, 0x19, "I" },
    { "custom_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTGridCell_CBorder = { "CBorder", "com.appnativa.rare.spot", "GridCell", 0x9, 2, methods, 23, fields, 0, NULL};
  return &_RARESPOTGridCell_CBorder;
}

@end
