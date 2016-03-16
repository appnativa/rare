//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Region.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/CollapsibleInfo.h"
#include "com/appnativa/rare/spot/GridCell.h"
#include "com/appnativa/rare/spot/Margin.h"
#include "com/appnativa/rare/spot/Region.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTRegion

- (id)init {
  return [self initRARESPOTRegionWithBoolean:YES];
}

- (id)initRARESPOTRegionWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    name_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    horizontalFill_ = [[RARESPOTRegion_CHorizontalFill alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTRegion_CHorizontalFill_maximum] withNSString:@"maximum" withBoolean:NO];
    verticalFill_ = [[RARESPOTRegion_CVerticalFill alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTRegion_CVerticalFill_maximum] withNSString:@"maximum" withBoolean:NO];
    visible_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    contentPadding_ = nil;
    viewer_ = [[SPOTAny alloc] initWithNSString:@"com.appnativa.rare.spot.Widget" withBoolean:YES];
    dataURL_ = [[SPOTPrintableString alloc] init];
    collapsibleInfo_ = nil;
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTRegionWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    name_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    horizontalFill_ = [[RARESPOTRegion_CHorizontalFill alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTRegion_CHorizontalFill_maximum] withNSString:@"maximum" withBoolean:NO];
    verticalFill_ = [[RARESPOTRegion_CVerticalFill alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTRegion_CVerticalFill_maximum] withNSString:@"maximum" withBoolean:NO];
    visible_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    contentPadding_ = nil;
    viewer_ = [[SPOTAny alloc] initWithNSString:@"com.appnativa.rare.spot.Widget" withBoolean:YES];
    dataURL_ = [[SPOTPrintableString alloc] init];
    collapsibleInfo_ = nil;
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 8;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"name" withISPOTElement:name_];
  [self spot_addElementWithNSString:@"horizontalFill" withISPOTElement:horizontalFill_];
  [self spot_addElementWithNSString:@"verticalFill" withISPOTElement:verticalFill_];
  [self spot_addElementWithNSString:@"visible" withISPOTElement:visible_];
  [self spot_addElementWithNSString:@"contentPadding" withISPOTElement:contentPadding_];
  [self spot_addElementWithNSString:@"viewer" withISPOTElement:viewer_];
  [self spot_addElementWithNSString:@"dataURL" withISPOTElement:dataURL_];
  [((SPOTPrintableString *) nil_chk(dataURL_)) spot_defineAttributeWithNSString:@"mimeType" withNSString:nil];
  [dataURL_ spot_defineAttributeWithNSString:@"deferred" withNSString:@"true"];
  [self spot_addElementWithNSString:@"collapsibleInfo" withISPOTElement:collapsibleInfo_];
}

- (RARESPOTMargin *)getContentPadding {
  return contentPadding_;
}

- (RARESPOTMargin *)getContentPaddingReference {
  if (contentPadding_ == nil) {
    contentPadding_ = [[RARESPOTMargin alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"contentPadding" withISPOTElement:contentPadding_];
  }
  return contentPadding_;
}

- (void)setContentPaddingWithISPOTElement:(id<iSPOTElement>)reference {
  contentPadding_ = (RARESPOTMargin *) check_class_cast(reference, [RARESPOTMargin class]);
  (void) [self spot_setReferenceWithNSString:@"contentPadding" withISPOTElement:reference];
}

- (RARESPOTCollapsibleInfo *)getCollapsibleInfo {
  return collapsibleInfo_;
}

- (RARESPOTCollapsibleInfo *)getCollapsibleInfoReference {
  if (collapsibleInfo_ == nil) {
    collapsibleInfo_ = [[RARESPOTCollapsibleInfo alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"collapsibleInfo" withISPOTElement:collapsibleInfo_];
  }
  return collapsibleInfo_;
}

- (void)setCollapsibleInfoWithISPOTElement:(id<iSPOTElement>)reference {
  collapsibleInfo_ = (RARESPOTCollapsibleInfo *) check_class_cast(reference, [RARESPOTCollapsibleInfo class]);
  (void) [self spot_setReferenceWithNSString:@"collapsibleInfo" withISPOTElement:reference];
}

- (RARESPOTGridCell_CBorder *)setBorderWithInt:(int)border {
  RARESPOTGridCell_CBorder *e = [[RARESPOTGridCell_CBorder alloc] initWithInt:border];
  [((SPOTSet *) nil_chk([self getBordersReference])) clear];
  [((SPOTSet *) nil_chk([self getBordersReference])) addWithISPOTElement:e];
  return e;
}

- (RARESPOTGridCell_CBorder *)setBorderWithNSString:(NSString *)border {
  RARESPOTGridCell_CBorder *e = [[RARESPOTGridCell_CBorder alloc] init];
  [e setValueWithNSString:border];
  [((SPOTSet *) nil_chk([self getBordersReference])) clear];
  [((SPOTSet *) nil_chk([self getBordersReference])) addWithISPOTElement:e];
  return e;
}

- (RARESPOTGridCell_CBorder *)getBorder {
  if ((borders_ == nil) || ([borders_ getCount] == 0)) {
    SPOTEnumerated *e = [[RARESPOTGridCell_CBorder alloc] initWithInt:RARESPOTGridCell_CBorder_standard];
    [((SPOTSet *) nil_chk([self getBordersReference])) addWithISPOTElement:e];
  }
  return (RARESPOTGridCell_CBorder *) check_class_cast([((SPOTSet *) nil_chk(borders_)) getWithInt:0], [RARESPOTGridCell_CBorder class]);
}

- (void)setBorderAttributeWithNSString:(NSString *)name
                          withNSString:(NSString *)value {
  if ((borders_ == nil) || ([borders_ getCount] == 0)) {
    SPOTEnumerated *e = [[RARESPOTGridCell_CBorder alloc] initWithInt:RARESPOTGridCell_CBorder_standard];
    [((SPOTSet *) nil_chk([self getBordersReference])) addWithISPOTElement:e];
  }
  [((id<iSPOTElement>) nil_chk([((SPOTSet *) nil_chk(borders_)) getExWithInt:0])) spot_setAttributeWithNSString:name withNSString:value];
}

- (void)copyAllFieldsTo:(RARESPOTRegion *)other {
  [super copyAllFieldsTo:other];
  other->collapsibleInfo_ = collapsibleInfo_;
  other->contentPadding_ = contentPadding_;
  other->dataURL_ = dataURL_;
  other->horizontalFill_ = horizontalFill_;
  other->name_ = name_;
  other->verticalFill_ = verticalFill_;
  other->viewer_ = viewer_;
  other->visible_ = visible_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getContentPadding", NULL, "LRARESPOTMargin", 0x1, NULL },
    { "getContentPaddingReference", NULL, "LRARESPOTMargin", 0x1, NULL },
    { "setContentPaddingWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getCollapsibleInfo", NULL, "LRARESPOTCollapsibleInfo", 0x1, NULL },
    { "getCollapsibleInfoReference", NULL, "LRARESPOTCollapsibleInfo", 0x1, NULL },
    { "setCollapsibleInfoWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "setBorderWithInt:", NULL, "LRARESPOTGridCell_CBorder", 0x1, NULL },
    { "setBorderWithNSString:", NULL, "LRARESPOTGridCell_CBorder", 0x1, NULL },
    { "getBorder", NULL, "LRARESPOTGridCell_CBorder", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "name_", NULL, 0x1, "LSPOTPrintableString" },
    { "horizontalFill_", NULL, 0x1, "LRARESPOTRegion_CHorizontalFill" },
    { "verticalFill_", NULL, 0x1, "LRARESPOTRegion_CVerticalFill" },
    { "visible_", NULL, 0x1, "LSPOTBoolean" },
    { "contentPadding_", NULL, 0x4, "LRARESPOTMargin" },
    { "viewer_", NULL, 0x1, "LSPOTAny" },
    { "dataURL_", NULL, 0x1, "LSPOTPrintableString" },
    { "collapsibleInfo_", NULL, 0x4, "LRARESPOTCollapsibleInfo" },
  };
  static J2ObjcClassInfo _RARESPOTRegion = { "Region", "com.appnativa.rare.spot", NULL, 0x1, 11, methods, 8, fields, 0, NULL};
  return &_RARESPOTRegion;
}

@end
@implementation RARESPOTRegion_CHorizontalFill

static IOSIntArray * RARESPOTRegion_CHorizontalFill__nchoices_;
static IOSObjectArray * RARESPOTRegion_CHorizontalFill__schoices_;

+ (int)maximum {
  return RARESPOTRegion_CHorizontalFill_maximum;
}

+ (int)preferred {
  return RARESPOTRegion_CHorizontalFill_preferred;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTRegion_CHorizontalFill__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTRegion_CHorizontalFill__schoices_;
}

- (id)init {
  return [self initRARESPOTRegion_CHorizontalFillWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTRegion_CHorizontalFill__schoices_;
    _nChoices_ = RARESPOTRegion_CHorizontalFill__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTRegion_CHorizontalFillWithJavaLangInteger:(JavaLangInteger *)ival
                                               withNSString:(NSString *)sval
                                        withJavaLangInteger:(JavaLangInteger *)idefaultval
                                               withNSString:(NSString *)sdefaultval
                                                withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTRegion_CHorizontalFill__schoices_;
    _nChoices_ = RARESPOTRegion_CHorizontalFill__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTRegion_CHorizontalFillWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{maximum(1), preferred(2) }";
}

+ (void)initialize {
  if (self == [RARESPOTRegion_CHorizontalFill class]) {
    RARESPOTRegion_CHorizontalFill__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 1, 2 } count:2];
    RARESPOTRegion_CHorizontalFill__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"maximum", @"preferred" } count:2 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "maximum_", NULL, 0x19, "I" },
    { "preferred_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTRegion_CHorizontalFill = { "CHorizontalFill", "com.appnativa.rare.spot", "Region", 0x9, 1, methods, 4, fields, 0, NULL};
  return &_RARESPOTRegion_CHorizontalFill;
}

@end
@implementation RARESPOTRegion_CVerticalFill

static IOSIntArray * RARESPOTRegion_CVerticalFill__nchoices_;
static IOSObjectArray * RARESPOTRegion_CVerticalFill__schoices_;

+ (int)maximum {
  return RARESPOTRegion_CVerticalFill_maximum;
}

+ (int)preferred {
  return RARESPOTRegion_CVerticalFill_preferred;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTRegion_CVerticalFill__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTRegion_CVerticalFill__schoices_;
}

- (id)init {
  return [self initRARESPOTRegion_CVerticalFillWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTRegion_CVerticalFill__schoices_;
    _nChoices_ = RARESPOTRegion_CVerticalFill__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTRegion_CVerticalFillWithJavaLangInteger:(JavaLangInteger *)ival
                                             withNSString:(NSString *)sval
                                      withJavaLangInteger:(JavaLangInteger *)idefaultval
                                             withNSString:(NSString *)sdefaultval
                                              withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTRegion_CVerticalFill__schoices_;
    _nChoices_ = RARESPOTRegion_CVerticalFill__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTRegion_CVerticalFillWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{maximum(1), preferred(2) }";
}

+ (void)initialize {
  if (self == [RARESPOTRegion_CVerticalFill class]) {
    RARESPOTRegion_CVerticalFill__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 1, 2 } count:2];
    RARESPOTRegion_CVerticalFill__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"maximum", @"preferred" } count:2 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "maximum_", NULL, 0x19, "I" },
    { "preferred_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTRegion_CVerticalFill = { "CVerticalFill", "com.appnativa.rare.spot", "Region", 0x9, 1, methods, 4, fields, 0, NULL};
  return &_RARESPOTRegion_CVerticalFill;
}

@end
