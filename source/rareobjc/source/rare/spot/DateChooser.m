//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/DateChooser.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/DateChooser.h"
#include "com/appnativa/rare/spot/GridCell.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTDateTime.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTDateChooser

- (id)init {
  return [self initRARESPOTDateChooserWithBoolean:YES];
}

- (id)initRARESPOTDateChooserWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    editable_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:YES];
    value_ = [[SPOTDateTime alloc] init];
    minValue_ = [[SPOTDateTime alloc] init];
    maxValue_ = [[SPOTDateTime alloc] init];
    displayType_ = [[RARESPOTDateChooser_CDisplayType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTDateChooser_CDisplayType_combo_box] withNSString:@"combo_box" withBoolean:NO];
    showPopupButton_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showPopupAsDialog_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    popupPainter_ = nil;
    format_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    monthDisplayCols_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:12] withNSNumber:[JavaLangInteger valueOfWithInt:1] withBoolean:NO];
    monthDisplayRows_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:12] withNSNumber:[JavaLangInteger valueOfWithInt:1] withBoolean:NO];
    autoResizeRowsColumns_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showOkButton_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showNoneButton_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showTodayButton_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showTime_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showNavigationButtons_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    selectionMode_ = [[RARESPOTDateChooser_CSelectionMode alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTDateChooser_CSelectionMode_single] withNSString:@"single" withBoolean:YES];
    selectionType_ = [[RARESPOTDateChooser_CSelectionType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTDateChooser_CSelectionType_all] withNSString:@"all" withBoolean:YES];
    converterClass_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    valueContext_ = [[SPOTPrintableString alloc] init];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTDateChooserWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    editable_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:YES];
    value_ = [[SPOTDateTime alloc] init];
    minValue_ = [[SPOTDateTime alloc] init];
    maxValue_ = [[SPOTDateTime alloc] init];
    displayType_ = [[RARESPOTDateChooser_CDisplayType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTDateChooser_CDisplayType_combo_box] withNSString:@"combo_box" withBoolean:NO];
    showPopupButton_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showPopupAsDialog_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    popupPainter_ = nil;
    format_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    monthDisplayCols_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:12] withNSNumber:[JavaLangInteger valueOfWithInt:1] withBoolean:NO];
    monthDisplayRows_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:12] withNSNumber:[JavaLangInteger valueOfWithInt:1] withBoolean:NO];
    autoResizeRowsColumns_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showOkButton_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showNoneButton_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showTodayButton_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showTime_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showNavigationButtons_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    selectionMode_ = [[RARESPOTDateChooser_CSelectionMode alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTDateChooser_CSelectionMode_single] withNSString:@"single" withBoolean:YES];
    selectionType_ = [[RARESPOTDateChooser_CSelectionType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTDateChooser_CSelectionType_all] withNSString:@"all" withBoolean:YES];
    converterClass_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    valueContext_ = [[SPOTPrintableString alloc] init];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 21;
  self->attributeSizeHint_ += 3;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onAction" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onWillExpand" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onWillCollapse" withNSString:nil];
  [self spot_addElementWithNSString:@"editable" withISPOTElement:editable_];
  [self spot_addElementWithNSString:@"value" withISPOTElement:value_];
  [self spot_addElementWithNSString:@"minValue" withISPOTElement:minValue_];
  [self spot_addElementWithNSString:@"maxValue" withISPOTElement:maxValue_];
  [self spot_addElementWithNSString:@"displayType" withISPOTElement:displayType_];
  [self spot_addElementWithNSString:@"showPopupButton" withISPOTElement:showPopupButton_];
  [((SPOTBoolean *) nil_chk(showPopupButton_)) spot_defineAttributeWithNSString:@"icon" withNSString:nil];
  [showPopupButton_ spot_defineAttributeWithNSString:@"pressedIcon" withNSString:nil];
  [showPopupButton_ spot_defineAttributeWithNSString:@"disabledIcon" withNSString:nil];
  [showPopupButton_ spot_defineAttributeWithNSString:@"border" withNSString:nil];
  [showPopupButton_ spot_defineAttributeWithNSString:@"scaleIcons" withNSString:nil];
  [showPopupButton_ spot_defineAttributeWithNSString:@"bgColor" withNSString:nil];
  [showPopupButton_ spot_defineAttributeWithNSString:@"pressedPainter" withNSString:nil];
  [showPopupButton_ spot_defineAttributeWithNSString:@"disabledPainter" withNSString:nil];
  [self spot_addElementWithNSString:@"showPopupAsDialog" withISPOTElement:showPopupAsDialog_];
  [((SPOTBoolean *) nil_chk(showPopupAsDialog_)) spot_defineAttributeWithNSString:@"dialogTitle" withNSString:nil];
  [self spot_addElementWithNSString:@"popupPainter" withISPOTElement:popupPainter_];
  [self spot_addElementWithNSString:@"format" withISPOTElement:format_];
  [self spot_addElementWithNSString:@"monthDisplayCols" withISPOTElement:monthDisplayCols_];
  [self spot_addElementWithNSString:@"monthDisplayRows" withISPOTElement:monthDisplayRows_];
  [self spot_addElementWithNSString:@"autoResizeRowsColumns" withISPOTElement:autoResizeRowsColumns_];
  [self spot_addElementWithNSString:@"showOkButton" withISPOTElement:showOkButton_];
  [self spot_addElementWithNSString:@"showNoneButton" withISPOTElement:showNoneButton_];
  [self spot_addElementWithNSString:@"showTodayButton" withISPOTElement:showTodayButton_];
  [self spot_addElementWithNSString:@"showTime" withISPOTElement:showTime_];
  [((SPOTBoolean *) nil_chk(showTime_)) spot_defineAttributeWithNSString:@"timeOnlyChooser" withNSString:nil];
  [showTime_ spot_defineAttributeWithNSString:@"ampmFormat" withNSString:@"true"];
  [self spot_addElementWithNSString:@"showNavigationButtons" withISPOTElement:showNavigationButtons_];
  [self spot_addElementWithNSString:@"selectionMode" withISPOTElement:selectionMode_];
  [self spot_addElementWithNSString:@"selectionType" withISPOTElement:selectionType_];
  [self spot_addElementWithNSString:@"converterClass" withISPOTElement:converterClass_];
  [self spot_addElementWithNSString:@"valueContext" withISPOTElement:valueContext_];
}

- (RARESPOTGridCell *)getPopupPainter {
  return popupPainter_;
}

- (RARESPOTGridCell *)getPopupPainterReference {
  if (popupPainter_ == nil) {
    popupPainter_ = [[RARESPOTGridCell alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"popupPainter" withISPOTElement:popupPainter_];
  }
  return popupPainter_;
}

- (void)setPopupPainterWithISPOTElement:(id<iSPOTElement>)reference {
  popupPainter_ = (RARESPOTGridCell *) check_class_cast(reference, [RARESPOTGridCell class]);
  (void) [self spot_setReferenceWithNSString:@"popupPainter" withISPOTElement:reference];
}

- (void)copyAllFieldsTo:(RARESPOTDateChooser *)other {
  [super copyAllFieldsTo:other];
  other->autoResizeRowsColumns_ = autoResizeRowsColumns_;
  other->converterClass_ = converterClass_;
  other->displayType_ = displayType_;
  other->editable_ = editable_;
  other->format_ = format_;
  other->maxValue_ = maxValue_;
  other->minValue_ = minValue_;
  other->monthDisplayCols_ = monthDisplayCols_;
  other->monthDisplayRows_ = monthDisplayRows_;
  other->popupPainter_ = popupPainter_;
  other->selectionMode_ = selectionMode_;
  other->selectionType_ = selectionType_;
  other->showNavigationButtons_ = showNavigationButtons_;
  other->showNoneButton_ = showNoneButton_;
  other->showOkButton_ = showOkButton_;
  other->showPopupAsDialog_ = showPopupAsDialog_;
  other->showPopupButton_ = showPopupButton_;
  other->showTime_ = showTime_;
  other->showTodayButton_ = showTodayButton_;
  other->value_ = value_;
  other->valueContext_ = valueContext_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getPopupPainter", NULL, "LRARESPOTGridCell", 0x1, NULL },
    { "getPopupPainterReference", NULL, "LRARESPOTGridCell", 0x1, NULL },
    { "setPopupPainterWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "editable_", NULL, 0x1, "LSPOTBoolean" },
    { "value_", NULL, 0x1, "LSPOTDateTime" },
    { "minValue_", NULL, 0x1, "LSPOTDateTime" },
    { "maxValue_", NULL, 0x1, "LSPOTDateTime" },
    { "displayType_", NULL, 0x1, "LRARESPOTDateChooser_CDisplayType" },
    { "showPopupButton_", NULL, 0x1, "LSPOTBoolean" },
    { "showPopupAsDialog_", NULL, 0x1, "LSPOTBoolean" },
    { "popupPainter_", NULL, 0x4, "LRARESPOTGridCell" },
    { "format_", NULL, 0x1, "LSPOTPrintableString" },
    { "monthDisplayCols_", NULL, 0x1, "LSPOTInteger" },
    { "monthDisplayRows_", NULL, 0x1, "LSPOTInteger" },
    { "autoResizeRowsColumns_", NULL, 0x1, "LSPOTBoolean" },
    { "showOkButton_", NULL, 0x1, "LSPOTBoolean" },
    { "showNoneButton_", NULL, 0x1, "LSPOTBoolean" },
    { "showTodayButton_", NULL, 0x1, "LSPOTBoolean" },
    { "showTime_", NULL, 0x1, "LSPOTBoolean" },
    { "showNavigationButtons_", NULL, 0x1, "LSPOTBoolean" },
    { "selectionMode_", NULL, 0x1, "LRARESPOTDateChooser_CSelectionMode" },
    { "selectionType_", NULL, 0x1, "LRARESPOTDateChooser_CSelectionType" },
    { "converterClass_", NULL, 0x1, "LSPOTPrintableString" },
    { "valueContext_", NULL, 0x1, "LSPOTPrintableString" },
  };
  static J2ObjcClassInfo _RARESPOTDateChooser = { "DateChooser", "com.appnativa.rare.spot", NULL, 0x1, 5, methods, 21, fields, 0, NULL};
  return &_RARESPOTDateChooser;
}

@end
@implementation RARESPOTDateChooser_CDisplayType

static IOSIntArray * RARESPOTDateChooser_CDisplayType__nchoices_;
static IOSObjectArray * RARESPOTDateChooser_CDisplayType__schoices_;

+ (int)combo_box {
  return RARESPOTDateChooser_CDisplayType_combo_box;
}

+ (int)single_calendar {
  return RARESPOTDateChooser_CDisplayType_single_calendar;
}

+ (int)multiple_calendar {
  return RARESPOTDateChooser_CDisplayType_multiple_calendar;
}

+ (int)button {
  return RARESPOTDateChooser_CDisplayType_button;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTDateChooser_CDisplayType__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTDateChooser_CDisplayType__schoices_;
}

- (id)init {
  return [self initRARESPOTDateChooser_CDisplayTypeWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTDateChooser_CDisplayType__schoices_;
    _nChoices_ = RARESPOTDateChooser_CDisplayType__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTDateChooser_CDisplayTypeWithJavaLangInteger:(JavaLangInteger *)ival
                                                 withNSString:(NSString *)sval
                                          withJavaLangInteger:(JavaLangInteger *)idefaultval
                                                 withNSString:(NSString *)sdefaultval
                                                  withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTDateChooser_CDisplayType__schoices_;
    _nChoices_ = RARESPOTDateChooser_CDisplayType__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTDateChooser_CDisplayTypeWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{combo_box(0), single_calendar(2), multiple_calendar(3), button(4) }";
}

+ (void)initialize {
  if (self == [RARESPOTDateChooser_CDisplayType class]) {
    RARESPOTDateChooser_CDisplayType__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 2, 3, 4 } count:4];
    RARESPOTDateChooser_CDisplayType__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"combo_box", @"single_calendar", @"multiple_calendar", @"button" } count:4 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "combo_box_", NULL, 0x19, "I" },
    { "single_calendar_", NULL, 0x19, "I" },
    { "multiple_calendar_", NULL, 0x19, "I" },
    { "button_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTDateChooser_CDisplayType = { "CDisplayType", "com.appnativa.rare.spot", "DateChooser", 0x9, 1, methods, 6, fields, 0, NULL};
  return &_RARESPOTDateChooser_CDisplayType;
}

@end
@implementation RARESPOTDateChooser_CSelectionMode

static IOSIntArray * RARESPOTDateChooser_CSelectionMode__nchoices_;
static IOSObjectArray * RARESPOTDateChooser_CSelectionMode__schoices_;

+ (int)none {
  return RARESPOTDateChooser_CSelectionMode_none;
}

+ (int)single {
  return RARESPOTDateChooser_CSelectionMode_single;
}

+ (int)multiple {
  return RARESPOTDateChooser_CSelectionMode_multiple;
}

+ (int)block {
  return RARESPOTDateChooser_CSelectionMode_block;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTDateChooser_CSelectionMode__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTDateChooser_CSelectionMode__schoices_;
}

- (id)init {
  return [self initRARESPOTDateChooser_CSelectionModeWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTDateChooser_CSelectionMode__schoices_;
    _nChoices_ = RARESPOTDateChooser_CSelectionMode__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTDateChooser_CSelectionModeWithJavaLangInteger:(JavaLangInteger *)ival
                                                   withNSString:(NSString *)sval
                                            withJavaLangInteger:(JavaLangInteger *)idefaultval
                                                   withNSString:(NSString *)sdefaultval
                                                    withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTDateChooser_CSelectionMode__schoices_;
    _nChoices_ = RARESPOTDateChooser_CSelectionMode__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTDateChooser_CSelectionModeWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{none(0), single(1), multiple(2), block(3) }";
}

+ (void)initialize {
  if (self == [RARESPOTDateChooser_CSelectionMode class]) {
    RARESPOTDateChooser_CSelectionMode__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3 } count:4];
    RARESPOTDateChooser_CSelectionMode__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"none", @"single", @"multiple", @"block" } count:4 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "none_", NULL, 0x19, "I" },
    { "single_", NULL, 0x19, "I" },
    { "multiple_", NULL, 0x19, "I" },
    { "block_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTDateChooser_CSelectionMode = { "CSelectionMode", "com.appnativa.rare.spot", "DateChooser", 0x9, 1, methods, 6, fields, 0, NULL};
  return &_RARESPOTDateChooser_CSelectionMode;
}

@end
@implementation RARESPOTDateChooser_CSelectionType

static IOSIntArray * RARESPOTDateChooser_CSelectionType__nchoices_;
static IOSObjectArray * RARESPOTDateChooser_CSelectionType__schoices_;

+ (int)all {
  return RARESPOTDateChooser_CSelectionType_all;
}

+ (int)weekdays {
  return RARESPOTDateChooser_CSelectionType_weekdays;
}

+ (int)weekend {
  return RARESPOTDateChooser_CSelectionType_weekend;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTDateChooser_CSelectionType__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTDateChooser_CSelectionType__schoices_;
}

- (id)init {
  return [self initRARESPOTDateChooser_CSelectionTypeWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTDateChooser_CSelectionType__schoices_;
    _nChoices_ = RARESPOTDateChooser_CSelectionType__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTDateChooser_CSelectionTypeWithJavaLangInteger:(JavaLangInteger *)ival
                                                   withNSString:(NSString *)sval
                                            withJavaLangInteger:(JavaLangInteger *)idefaultval
                                                   withNSString:(NSString *)sdefaultval
                                                    withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTDateChooser_CSelectionType__schoices_;
    _nChoices_ = RARESPOTDateChooser_CSelectionType__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTDateChooser_CSelectionTypeWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{all(0), weekdays(1), weekend(2) }";
}

+ (void)initialize {
  if (self == [RARESPOTDateChooser_CSelectionType class]) {
    RARESPOTDateChooser_CSelectionType__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2 } count:3];
    RARESPOTDateChooser_CSelectionType__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"all", @"weekdays", @"weekend" } count:3 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "all_", NULL, 0x19, "I" },
    { "weekdays_", NULL, 0x19, "I" },
    { "weekend_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTDateChooser_CSelectionType = { "CSelectionType", "com.appnativa.rare.spot", "DateChooser", 0x9, 1, methods, 5, fields, 0, NULL};
  return &_RARESPOTDateChooser_CSelectionType;
}

@end
