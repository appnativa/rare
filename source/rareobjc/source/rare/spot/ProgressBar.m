//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/ProgressBar.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/ProgressBar.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "java/lang/Boolean.h"
#include "java/lang/Integer.h"

@implementation RARESPOTProgressBar

- (id)init {
  return [self initRARESPOTProgressBarWithBoolean:YES];
}

- (id)initRARESPOTProgressBarWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    value_ = [[SPOTInteger alloc] initWithNSString:nil withLong:0 withBoolean:NO];
    horizontal_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    indeterminate_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showText_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    progressText_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    graphicSize_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:32 withBoolean:YES];
    labelSide_ = [[RARESPOTProgressBar_CLabelSide alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTProgressBar_CLabelSide_auto] withNSString:@"auto" withBoolean:NO];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTProgressBarWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    value_ = [[SPOTInteger alloc] initWithNSString:nil withLong:0 withBoolean:NO];
    horizontal_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    indeterminate_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showText_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    progressText_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    graphicSize_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:32 withBoolean:YES];
    labelSide_ = [[RARESPOTProgressBar_CLabelSide alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTProgressBar_CLabelSide_auto] withNSString:@"auto" withBoolean:NO];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 7;
  self->attributeSizeHint_ += 1;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onChange" withNSString:nil];
  [self spot_addElementWithNSString:@"value" withISPOTElement:value_];
  [self spot_addElementWithNSString:@"horizontal" withISPOTElement:horizontal_];
  [self spot_addElementWithNSString:@"indeterminate" withISPOTElement:indeterminate_];
  [((SPOTBoolean *) nil_chk(indeterminate_)) spot_defineAttributeWithNSString:@"useSpinner" withNSString:nil];
  [self spot_addElementWithNSString:@"showText" withISPOTElement:showText_];
  [self spot_addElementWithNSString:@"progressText" withISPOTElement:progressText_];
  [self spot_addElementWithNSString:@"graphicSize" withISPOTElement:graphicSize_];
  [self spot_addElementWithNSString:@"labelSide" withISPOTElement:labelSide_];
}

- (void)copyAllFieldsTo:(RARESPOTProgressBar *)other {
  [super copyAllFieldsTo:other];
  other->graphicSize_ = graphicSize_;
  other->horizontal_ = horizontal_;
  other->indeterminate_ = indeterminate_;
  other->labelSide_ = labelSide_;
  other->progressText_ = progressText_;
  other->showText_ = showText_;
  other->value_ = value_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "value_", NULL, 0x1, "LSPOTInteger" },
    { "horizontal_", NULL, 0x1, "LSPOTBoolean" },
    { "indeterminate_", NULL, 0x1, "LSPOTBoolean" },
    { "showText_", NULL, 0x1, "LSPOTBoolean" },
    { "progressText_", NULL, 0x1, "LSPOTPrintableString" },
    { "graphicSize_", NULL, 0x1, "LSPOTPrintableString" },
    { "labelSide_", NULL, 0x1, "LRARESPOTProgressBar_CLabelSide" },
  };
  static J2ObjcClassInfo _RARESPOTProgressBar = { "ProgressBar", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 7, fields, 0, NULL};
  return &_RARESPOTProgressBar;
}

@end
@implementation RARESPOTProgressBar_CLabelSide

static IOSIntArray * RARESPOTProgressBar_CLabelSide__nchoices_;
static IOSObjectArray * RARESPOTProgressBar_CLabelSide__schoices_;

+ (int)getAuto {
  return RARESPOTProgressBar_CLabelSide_auto;
}

+ (int)left {
  return RARESPOTProgressBar_CLabelSide_left;
}

+ (int)right {
  return RARESPOTProgressBar_CLabelSide_right;
}

+ (int)top {
  return RARESPOTProgressBar_CLabelSide_top;
}

+ (int)bottom {
  return RARESPOTProgressBar_CLabelSide_bottom;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTProgressBar_CLabelSide__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTProgressBar_CLabelSide__schoices_;
}

- (id)init {
  return [self initRARESPOTProgressBar_CLabelSideWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTProgressBar_CLabelSide__schoices_;
    _nChoices_ = RARESPOTProgressBar_CLabelSide__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTProgressBar_CLabelSideWithJavaLangInteger:(JavaLangInteger *)ival
                                               withNSString:(NSString *)sval
                                        withJavaLangInteger:(JavaLangInteger *)idefaultval
                                               withNSString:(NSString *)sdefaultval
                                                withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTProgressBar_CLabelSide__schoices_;
    _nChoices_ = RARESPOTProgressBar_CLabelSide__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTProgressBar_CLabelSideWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{auto(0), left(1), right(2), top(3), bottom(4) }";
}

+ (void)initialize {
  if (self == [RARESPOTProgressBar_CLabelSide class]) {
    RARESPOTProgressBar_CLabelSide__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4 } count:5];
    RARESPOTProgressBar_CLabelSide__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"auto", @"left", @"right", @"top", @"bottom" } count:5 type:[IOSClass classWithClass:[NSString class]]];
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
    { "top_", NULL, 0x19, "I" },
    { "bottom_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTProgressBar_CLabelSide = { "CLabelSide", "com.appnativa.rare.spot", "ProgressBar", 0x9, 1, methods, 7, fields, 0, NULL};
  return &_RARESPOTProgressBar_CLabelSide;
}

@end
