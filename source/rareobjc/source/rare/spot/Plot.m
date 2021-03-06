//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Plot.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/Margin.h"
#include "com/appnativa/rare/spot/Plot.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTReal.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTPlot

- (id)init {
  return [self initRARESPOTPlotWithBoolean:YES];
}

- (id)initRARESPOTPlotWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    bgImageURL_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    contentPadding_ = nil;
    bgColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:128 withBoolean:YES];
    borderColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    gridLine_ = [[RARESPOTPlot_CGridLine alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPlot_CGridLine_auto] withNSString:@"auto" withBoolean:NO];
    shapes_ = [[RARESPOTPlot_CShapes alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPlot_CShapes_filled] withNSString:@"filled" withBoolean:NO];
    labels_ = [[RARESPOTPlot_CLabels alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPlot_CLabels_values] withNSString:@"values" withBoolean:NO];
    fgAlpha_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:255] withNSNumber:[JavaLangInteger valueOfWithInt:255] withBoolean:YES];
    lineThickness_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withNSString:@"255" withBoolean:YES];
    outlineThickness_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withNSString:@"20" withNSString:@"1" withBoolean:YES];
    templateName_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTPlotWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    bgImageURL_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    contentPadding_ = nil;
    bgColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:128 withBoolean:YES];
    borderColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    gridLine_ = [[RARESPOTPlot_CGridLine alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPlot_CGridLine_auto] withNSString:@"auto" withBoolean:NO];
    shapes_ = [[RARESPOTPlot_CShapes alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPlot_CShapes_filled] withNSString:@"filled" withBoolean:NO];
    labels_ = [[RARESPOTPlot_CLabels alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPlot_CLabels_values] withNSString:@"values" withBoolean:NO];
    fgAlpha_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:255] withNSNumber:[JavaLangInteger valueOfWithInt:255] withBoolean:YES];
    lineThickness_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withNSString:@"255" withBoolean:YES];
    outlineThickness_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withNSString:@"20" withNSString:@"1" withBoolean:YES];
    templateName_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 11;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"bgImageURL" withISPOTElement:bgImageURL_];
  [self spot_addElementWithNSString:@"contentPadding" withISPOTElement:contentPadding_];
  [self spot_addElementWithNSString:@"bgColor" withISPOTElement:bgColor_];
  [((SPOTPrintableString *) nil_chk(bgColor_)) spot_defineAttributeWithNSString:@"type" withNSString:@"linear"];
  [bgColor_ spot_defineAttributeWithNSString:@"direction" withNSString:@"vertical_top"];
  [bgColor_ spot_defineAttributeWithNSString:@"magnitude" withNSString:nil];
  [bgColor_ spot_defineAttributeWithNSString:@"distribution" withNSString:nil];
  [bgColor_ spot_defineAttributeWithNSString:@"radius" withNSString:nil];
  [self spot_addElementWithNSString:@"borderColor" withISPOTElement:borderColor_];
  [self spot_addElementWithNSString:@"gridLine" withISPOTElement:gridLine_];
  [((RARESPOTPlot_CGridLine *) nil_chk(gridLine_)) spot_defineAttributeWithNSString:@"color" withNSString:nil];
  [self spot_addElementWithNSString:@"shapes" withISPOTElement:shapes_];
  [((RARESPOTPlot_CShapes *) nil_chk(shapes_)) spot_defineAttributeWithNSString:@"outlineColor" withNSString:nil];
  [shapes_ spot_defineAttributeWithNSString:@"filledColor" withNSString:nil];
  [self spot_addElementWithNSString:@"labels" withISPOTElement:labels_];
  [((RARESPOTPlot_CLabels *) nil_chk(labels_)) spot_defineAttributeWithNSString:@"format" withNSString:nil];
  [labels_ spot_defineAttributeWithNSString:@"fgColor" withNSString:nil];
  [labels_ spot_defineAttributeWithNSString:@"bgColor" withNSString:nil];
  [labels_ spot_defineAttributeWithNSString:@"border" withNSString:nil];
  [labels_ spot_defineAttributeWithNSString:@"offset" withNSString:nil];
  [labels_ spot_defineAttributeWithNSString:@"font" withNSString:nil];
  [self spot_addElementWithNSString:@"fgAlpha" withISPOTElement:fgAlpha_];
  [self spot_addElementWithNSString:@"lineThickness" withISPOTElement:lineThickness_];
  [self spot_addElementWithNSString:@"outlineThickness" withISPOTElement:outlineThickness_];
  [self spot_addElementWithNSString:@"templateName" withISPOTElement:templateName_];
  [((SPOTPrintableString *) nil_chk(templateName_)) spot_defineAttributeWithNSString:@"context" withNSString:nil];
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

- (void)copyAllFieldsTo:(RARESPOTPlot *)other {
  [super copyAllFieldsTo:other];
  other->bgColor_ = bgColor_;
  other->bgImageURL_ = bgImageURL_;
  other->borderColor_ = borderColor_;
  other->contentPadding_ = contentPadding_;
  other->fgAlpha_ = fgAlpha_;
  other->gridLine_ = gridLine_;
  other->labels_ = labels_;
  other->lineThickness_ = lineThickness_;
  other->outlineThickness_ = outlineThickness_;
  other->shapes_ = shapes_;
  other->templateName_ = templateName_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getContentPadding", NULL, "LRARESPOTMargin", 0x1, NULL },
    { "getContentPaddingReference", NULL, "LRARESPOTMargin", 0x1, NULL },
    { "setContentPaddingWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "bgImageURL_", NULL, 0x1, "LSPOTPrintableString" },
    { "contentPadding_", NULL, 0x4, "LRARESPOTMargin" },
    { "bgColor_", NULL, 0x1, "LSPOTPrintableString" },
    { "borderColor_", NULL, 0x1, "LSPOTPrintableString" },
    { "gridLine_", NULL, 0x1, "LRARESPOTPlot_CGridLine" },
    { "shapes_", NULL, 0x1, "LRARESPOTPlot_CShapes" },
    { "labels_", NULL, 0x1, "LRARESPOTPlot_CLabels" },
    { "fgAlpha_", NULL, 0x1, "LSPOTInteger" },
    { "lineThickness_", NULL, 0x1, "LSPOTReal" },
    { "outlineThickness_", NULL, 0x1, "LSPOTReal" },
    { "templateName_", NULL, 0x1, "LSPOTPrintableString" },
  };
  static J2ObjcClassInfo _RARESPOTPlot = { "Plot", "com.appnativa.rare.spot", NULL, 0x1, 5, methods, 11, fields, 0, NULL};
  return &_RARESPOTPlot;
}

@end
@implementation RARESPOTPlot_CGridLine

static IOSIntArray * RARESPOTPlot_CGridLine__nchoices_;
static IOSObjectArray * RARESPOTPlot_CGridLine__schoices_;

+ (int)getAuto {
  return RARESPOTPlot_CGridLine_auto;
}

+ (int)solid {
  return RARESPOTPlot_CGridLine_solid;
}

+ (int)dashed {
  return RARESPOTPlot_CGridLine_dashed;
}

+ (int)dotted {
  return RARESPOTPlot_CGridLine_dotted;
}

+ (int)none {
  return RARESPOTPlot_CGridLine_none;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTPlot_CGridLine__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTPlot_CGridLine__schoices_;
}

- (id)init {
  return [self initRARESPOTPlot_CGridLineWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTPlot_CGridLine__schoices_;
    _nChoices_ = RARESPOTPlot_CGridLine__nchoices_;
    [self spot_setAttributes];
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTPlot_CGridLineWithJavaLangInteger:(JavaLangInteger *)ival
                                       withNSString:(NSString *)sval
                                withJavaLangInteger:(JavaLangInteger *)idefaultval
                                       withNSString:(NSString *)sdefaultval
                                        withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTPlot_CGridLine__schoices_;
    _nChoices_ = RARESPOTPlot_CGridLine__nchoices_;
    [self spot_setAttributes];
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTPlot_CGridLineWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{auto(0), solid(1), dashed(2), dotted(3), none(4) }";
}

- (void)spot_setAttributes {
  self->attributeSizeHint_ += 1;
  [self spot_defineAttributeWithNSString:@"color" withNSString:nil];
}

+ (void)initialize {
  if (self == [RARESPOTPlot_CGridLine class]) {
    RARESPOTPlot_CGridLine__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4 } count:5];
    RARESPOTPlot_CGridLine__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"auto", @"solid", @"dashed", @"dotted", @"none" } count:5 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_setAttributes", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "auto__", "auto", 0x19, "I" },
    { "solid_", NULL, 0x19, "I" },
    { "dashed_", NULL, 0x19, "I" },
    { "dotted_", NULL, 0x19, "I" },
    { "none_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTPlot_CGridLine = { "CGridLine", "com.appnativa.rare.spot", "Plot", 0x9, 2, methods, 7, fields, 0, NULL};
  return &_RARESPOTPlot_CGridLine;
}

@end
@implementation RARESPOTPlot_CShapes

static IOSIntArray * RARESPOTPlot_CShapes__nchoices_;
static IOSObjectArray * RARESPOTPlot_CShapes__schoices_;

+ (int)none {
  return RARESPOTPlot_CShapes_none;
}

+ (int)filled {
  return RARESPOTPlot_CShapes_filled;
}

+ (int)outlined {
  return RARESPOTPlot_CShapes_outlined;
}

+ (int)filled_and_outlined {
  return RARESPOTPlot_CShapes_filled_and_outlined;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTPlot_CShapes__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTPlot_CShapes__schoices_;
}

- (id)init {
  return [self initRARESPOTPlot_CShapesWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTPlot_CShapes__schoices_;
    _nChoices_ = RARESPOTPlot_CShapes__nchoices_;
    [self spot_setAttributes];
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTPlot_CShapesWithJavaLangInteger:(JavaLangInteger *)ival
                                     withNSString:(NSString *)sval
                              withJavaLangInteger:(JavaLangInteger *)idefaultval
                                     withNSString:(NSString *)sdefaultval
                                      withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTPlot_CShapes__schoices_;
    _nChoices_ = RARESPOTPlot_CShapes__nchoices_;
    [self spot_setAttributes];
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTPlot_CShapesWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{none(0), filled(1), outlined(2), filled_and_outlined(3) }";
}

- (void)spot_setAttributes {
  self->attributeSizeHint_ += 2;
  [self spot_defineAttributeWithNSString:@"outlineColor" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"filledColor" withNSString:nil];
}

+ (void)initialize {
  if (self == [RARESPOTPlot_CShapes class]) {
    RARESPOTPlot_CShapes__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3 } count:4];
    RARESPOTPlot_CShapes__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"none", @"filled", @"outlined", @"filled_and_outlined" } count:4 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_setAttributes", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "none_", NULL, 0x19, "I" },
    { "filled_", NULL, 0x19, "I" },
    { "outlined_", NULL, 0x19, "I" },
    { "filled_and_outlined_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTPlot_CShapes = { "CShapes", "com.appnativa.rare.spot", "Plot", 0x9, 2, methods, 6, fields, 0, NULL};
  return &_RARESPOTPlot_CShapes;
}

@end
@implementation RARESPOTPlot_CLabels

static IOSIntArray * RARESPOTPlot_CLabels__nchoices_;
static IOSObjectArray * RARESPOTPlot_CLabels__schoices_;

+ (int)values {
  return RARESPOTPlot_CLabels_values;
}

+ (int)tooltips {
  return RARESPOTPlot_CLabels_tooltips;
}

+ (int)linked_data {
  return RARESPOTPlot_CLabels_linked_data;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTPlot_CLabels__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTPlot_CLabels__schoices_;
}

- (id)init {
  return [self initRARESPOTPlot_CLabelsWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTPlot_CLabels__schoices_;
    _nChoices_ = RARESPOTPlot_CLabels__nchoices_;
    [self spot_setAttributes];
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTPlot_CLabelsWithJavaLangInteger:(JavaLangInteger *)ival
                                     withNSString:(NSString *)sval
                              withJavaLangInteger:(JavaLangInteger *)idefaultval
                                     withNSString:(NSString *)sdefaultval
                                      withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTPlot_CLabels__schoices_;
    _nChoices_ = RARESPOTPlot_CLabels__nchoices_;
    [self spot_setAttributes];
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTPlot_CLabelsWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{values(0), tooltips(1), linked_data(2) }";
}

- (void)spot_setAttributes {
  self->attributeSizeHint_ += 6;
  [self spot_defineAttributeWithNSString:@"format" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"fgColor" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"bgColor" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"border" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"offset" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"font" withNSString:nil];
}

+ (void)initialize {
  if (self == [RARESPOTPlot_CLabels class]) {
    RARESPOTPlot_CLabels__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2 } count:3];
    RARESPOTPlot_CLabels__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"values", @"tooltips", @"linked_data" } count:3 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_setAttributes", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "values_", NULL, 0x19, "I" },
    { "tooltips_", NULL, 0x19, "I" },
    { "linked_data_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTPlot_CLabels = { "CLabels", "com.appnativa.rare.spot", "Plot", 0x9, 2, methods, 5, fields, 0, NULL};
  return &_RARESPOTPlot_CLabels;
}

@end
