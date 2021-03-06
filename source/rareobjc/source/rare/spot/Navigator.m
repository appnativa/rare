//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Navigator.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/ActionItem.h"
#include "com/appnativa/rare/spot/GridCell.h"
#include "com/appnativa/rare/spot/Navigator.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTNavigator

- (id)init {
  return [self initRARESPOTNavigatorWithBoolean:YES];
}

- (id)initRARESPOTNavigatorWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    type_ = [[RARESPOTNavigator_CType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTNavigator_CType_hiearchical] withNSString:@"hiearchical" withBoolean:NO];
    selectedIndex_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withBoolean:NO];
    showBackButton_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    useTextForTooltip_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showIconsOnly_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    buttonsSameSize_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    separatorLineColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    pressedPainter_ = nil;
    selectionPainter_ = nil;
    textHAlignment_ = [[RARESPOTNavigator_CTextHAlignment alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTNavigator_CTextHAlignment_auto] withNSString:@"auto" withBoolean:NO];
    textVAlignment_ = [[RARESPOTNavigator_CTextVAlignment alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTNavigator_CTextVAlignment_auto] withNSString:@"auto" withBoolean:NO];
    iconPosition_ = [[RARESPOTNavigator_CIconPosition alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTNavigator_CIconPosition_auto] withNSString:@"auto" withBoolean:NO];
    scaleIcon_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    actions_ = [[SPOTSet alloc] initWithNSString:@"action" withISPOTElement:[[RARESPOTActionItem alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTNavigatorWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    type_ = [[RARESPOTNavigator_CType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTNavigator_CType_hiearchical] withNSString:@"hiearchical" withBoolean:NO];
    selectedIndex_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withBoolean:NO];
    showBackButton_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    useTextForTooltip_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showIconsOnly_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    buttonsSameSize_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    separatorLineColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    pressedPainter_ = nil;
    selectionPainter_ = nil;
    textHAlignment_ = [[RARESPOTNavigator_CTextHAlignment alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTNavigator_CTextHAlignment_auto] withNSString:@"auto" withBoolean:NO];
    textVAlignment_ = [[RARESPOTNavigator_CTextVAlignment alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTNavigator_CTextVAlignment_auto] withNSString:@"auto" withBoolean:NO];
    iconPosition_ = [[RARESPOTNavigator_CIconPosition alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTNavigator_CIconPosition_auto] withNSString:@"auto" withBoolean:NO];
    scaleIcon_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    actions_ = [[SPOTSet alloc] initWithNSString:@"action" withISPOTElement:[[RARESPOTActionItem alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 14;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"type" withISPOTElement:type_];
  [self spot_addElementWithNSString:@"selectedIndex" withISPOTElement:selectedIndex_];
  [self spot_addElementWithNSString:@"showBackButton" withISPOTElement:showBackButton_];
  [self spot_addElementWithNSString:@"useTextForTooltip" withISPOTElement:useTextForTooltip_];
  [self spot_addElementWithNSString:@"showIconsOnly" withISPOTElement:showIconsOnly_];
  [self spot_addElementWithNSString:@"buttonsSameSize" withISPOTElement:buttonsSameSize_];
  [self spot_addElementWithNSString:@"separatorLineColor" withISPOTElement:separatorLineColor_];
  [self spot_addElementWithNSString:@"pressedPainter" withISPOTElement:pressedPainter_];
  [self spot_addElementWithNSString:@"selectionPainter" withISPOTElement:selectionPainter_];
  [self spot_addElementWithNSString:@"textHAlignment" withISPOTElement:textHAlignment_];
  [self spot_addElementWithNSString:@"textVAlignment" withISPOTElement:textVAlignment_];
  [self spot_addElementWithNSString:@"iconPosition" withISPOTElement:iconPosition_];
  [self spot_addElementWithNSString:@"scaleIcon" withISPOTElement:scaleIcon_];
  [((SPOTBoolean *) nil_chk(scaleIcon_)) spot_defineAttributeWithNSString:@"percent" withNSString:nil];
  [self spot_addElementWithNSString:@"actions" withISPOTElement:actions_];
}

- (RARESPOTGridCell *)getPressedPainter {
  return pressedPainter_;
}

- (RARESPOTGridCell *)getPressedPainterReference {
  if (pressedPainter_ == nil) {
    pressedPainter_ = [[RARESPOTGridCell alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"pressedPainter" withISPOTElement:pressedPainter_];
    [pressedPainter_ spot_defineAttributeWithNSString:@"foreground" withNSString:nil];
    [pressedPainter_ spot_defineAttributeWithNSString:@"font" withNSString:nil];
  }
  return pressedPainter_;
}

- (void)setPressedPainterWithISPOTElement:(id<iSPOTElement>)reference {
  pressedPainter_ = (RARESPOTGridCell *) check_class_cast(reference, [RARESPOTGridCell class]);
  (void) [self spot_setReferenceWithNSString:@"pressedPainter" withISPOTElement:reference];
}

- (RARESPOTGridCell *)getSelectionPainter {
  return selectionPainter_;
}

- (RARESPOTGridCell *)getSelectionPainterReference {
  if (selectionPainter_ == nil) {
    selectionPainter_ = [[RARESPOTGridCell alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"selectionPainter" withISPOTElement:selectionPainter_];
    [selectionPainter_ spot_defineAttributeWithNSString:@"foreground" withNSString:nil];
    [selectionPainter_ spot_defineAttributeWithNSString:@"foreground" withNSString:nil];
    [selectionPainter_ spot_defineAttributeWithNSString:@"foreground" withNSString:nil];
    [selectionPainter_ spot_defineAttributeWithNSString:@"foreground" withNSString:nil];
    [selectionPainter_ spot_defineAttributeWithNSString:@"foreground" withNSString:nil];
    [selectionPainter_ spot_defineAttributeWithNSString:@"font" withNSString:nil];
  }
  return selectionPainter_;
}

- (void)setSelectionPainterWithISPOTElement:(id<iSPOTElement>)reference {
  selectionPainter_ = (RARESPOTGridCell *) check_class_cast(reference, [RARESPOTGridCell class]);
  (void) [self spot_setReferenceWithNSString:@"selectionPainter" withISPOTElement:reference];
}

- (void)copyAllFieldsTo:(RARESPOTNavigator *)other {
  [super copyAllFieldsTo:other];
  other->actions_ = actions_;
  other->buttonsSameSize_ = buttonsSameSize_;
  other->iconPosition_ = iconPosition_;
  other->pressedPainter_ = pressedPainter_;
  other->scaleIcon_ = scaleIcon_;
  other->selectedIndex_ = selectedIndex_;
  other->selectionPainter_ = selectionPainter_;
  other->separatorLineColor_ = separatorLineColor_;
  other->showBackButton_ = showBackButton_;
  other->showIconsOnly_ = showIconsOnly_;
  other->textHAlignment_ = textHAlignment_;
  other->textVAlignment_ = textVAlignment_;
  other->type_ = type_;
  other->useTextForTooltip_ = useTextForTooltip_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getPressedPainter", NULL, "LRARESPOTGridCell", 0x1, NULL },
    { "getPressedPainterReference", NULL, "LRARESPOTGridCell", 0x1, NULL },
    { "setPressedPainterWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getSelectionPainter", NULL, "LRARESPOTGridCell", 0x1, NULL },
    { "getSelectionPainterReference", NULL, "LRARESPOTGridCell", 0x1, NULL },
    { "setSelectionPainterWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "type_", NULL, 0x1, "LRARESPOTNavigator_CType" },
    { "selectedIndex_", NULL, 0x1, "LSPOTInteger" },
    { "showBackButton_", NULL, 0x1, "LSPOTBoolean" },
    { "useTextForTooltip_", NULL, 0x1, "LSPOTBoolean" },
    { "showIconsOnly_", NULL, 0x1, "LSPOTBoolean" },
    { "buttonsSameSize_", NULL, 0x1, "LSPOTBoolean" },
    { "separatorLineColor_", NULL, 0x1, "LSPOTPrintableString" },
    { "pressedPainter_", NULL, 0x4, "LRARESPOTGridCell" },
    { "selectionPainter_", NULL, 0x4, "LRARESPOTGridCell" },
    { "textHAlignment_", NULL, 0x1, "LRARESPOTNavigator_CTextHAlignment" },
    { "textVAlignment_", NULL, 0x1, "LRARESPOTNavigator_CTextVAlignment" },
    { "iconPosition_", NULL, 0x1, "LRARESPOTNavigator_CIconPosition" },
    { "scaleIcon_", NULL, 0x1, "LSPOTBoolean" },
    { "actions_", NULL, 0x1, "LSPOTSet" },
  };
  static J2ObjcClassInfo _RARESPOTNavigator = { "Navigator", "com.appnativa.rare.spot", NULL, 0x1, 8, methods, 14, fields, 0, NULL};
  return &_RARESPOTNavigator;
}

@end
@implementation RARESPOTNavigator_CType

static IOSIntArray * RARESPOTNavigator_CType__nchoices_;
static IOSObjectArray * RARESPOTNavigator_CType__schoices_;

+ (int)hiearchical {
  return RARESPOTNavigator_CType_hiearchical;
}

+ (int)toggle {
  return RARESPOTNavigator_CType_toggle;
}

+ (int)option {
  return RARESPOTNavigator_CType_option;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTNavigator_CType__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTNavigator_CType__schoices_;
}

- (id)init {
  return [self initRARESPOTNavigator_CTypeWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTNavigator_CType__schoices_;
    _nChoices_ = RARESPOTNavigator_CType__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTNavigator_CTypeWithJavaLangInteger:(JavaLangInteger *)ival
                                        withNSString:(NSString *)sval
                                 withJavaLangInteger:(JavaLangInteger *)idefaultval
                                        withNSString:(NSString *)sdefaultval
                                         withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTNavigator_CType__schoices_;
    _nChoices_ = RARESPOTNavigator_CType__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTNavigator_CTypeWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{hiearchical(0), toggle(1), option(2) }";
}

+ (void)initialize {
  if (self == [RARESPOTNavigator_CType class]) {
    RARESPOTNavigator_CType__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2 } count:3];
    RARESPOTNavigator_CType__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"hiearchical", @"toggle", @"option" } count:3 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "hiearchical_", NULL, 0x19, "I" },
    { "toggle_", NULL, 0x19, "I" },
    { "option_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTNavigator_CType = { "CType", "com.appnativa.rare.spot", "Navigator", 0x9, 1, methods, 5, fields, 0, NULL};
  return &_RARESPOTNavigator_CType;
}

@end
@implementation RARESPOTNavigator_CTextHAlignment

static IOSIntArray * RARESPOTNavigator_CTextHAlignment__nchoices_;
static IOSObjectArray * RARESPOTNavigator_CTextHAlignment__schoices_;

+ (int)getAuto {
  return RARESPOTNavigator_CTextHAlignment_auto;
}

+ (int)left {
  return RARESPOTNavigator_CTextHAlignment_left;
}

+ (int)right {
  return RARESPOTNavigator_CTextHAlignment_right;
}

+ (int)leading {
  return RARESPOTNavigator_CTextHAlignment_leading;
}

+ (int)trailing {
  return RARESPOTNavigator_CTextHAlignment_trailing;
}

+ (int)center {
  return RARESPOTNavigator_CTextHAlignment_center;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTNavigator_CTextHAlignment__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTNavigator_CTextHAlignment__schoices_;
}

- (id)init {
  return [self initRARESPOTNavigator_CTextHAlignmentWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTNavigator_CTextHAlignment__schoices_;
    _nChoices_ = RARESPOTNavigator_CTextHAlignment__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTNavigator_CTextHAlignmentWithJavaLangInteger:(JavaLangInteger *)ival
                                                  withNSString:(NSString *)sval
                                           withJavaLangInteger:(JavaLangInteger *)idefaultval
                                                  withNSString:(NSString *)sdefaultval
                                                   withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTNavigator_CTextHAlignment__schoices_;
    _nChoices_ = RARESPOTNavigator_CTextHAlignment__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTNavigator_CTextHAlignmentWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{auto(0), left(1), right(2), leading(3), trailing(4), center(5) }";
}

+ (void)initialize {
  if (self == [RARESPOTNavigator_CTextHAlignment class]) {
    RARESPOTNavigator_CTextHAlignment__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4, 5 } count:6];
    RARESPOTNavigator_CTextHAlignment__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"auto", @"left", @"right", @"leading", @"trailing", @"center" } count:6 type:[IOSClass classWithClass:[NSString class]]];
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
  static J2ObjcClassInfo _RARESPOTNavigator_CTextHAlignment = { "CTextHAlignment", "com.appnativa.rare.spot", "Navigator", 0x9, 1, methods, 8, fields, 0, NULL};
  return &_RARESPOTNavigator_CTextHAlignment;
}

@end
@implementation RARESPOTNavigator_CTextVAlignment

static IOSIntArray * RARESPOTNavigator_CTextVAlignment__nchoices_;
static IOSObjectArray * RARESPOTNavigator_CTextVAlignment__schoices_;

+ (int)getAuto {
  return RARESPOTNavigator_CTextVAlignment_auto;
}

+ (int)top {
  return RARESPOTNavigator_CTextVAlignment_top;
}

+ (int)bottom {
  return RARESPOTNavigator_CTextVAlignment_bottom;
}

+ (int)center {
  return RARESPOTNavigator_CTextVAlignment_center;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTNavigator_CTextVAlignment__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTNavigator_CTextVAlignment__schoices_;
}

- (id)init {
  return [self initRARESPOTNavigator_CTextVAlignmentWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTNavigator_CTextVAlignment__schoices_;
    _nChoices_ = RARESPOTNavigator_CTextVAlignment__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTNavigator_CTextVAlignmentWithJavaLangInteger:(JavaLangInteger *)ival
                                                  withNSString:(NSString *)sval
                                           withJavaLangInteger:(JavaLangInteger *)idefaultval
                                                  withNSString:(NSString *)sdefaultval
                                                   withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTNavigator_CTextVAlignment__schoices_;
    _nChoices_ = RARESPOTNavigator_CTextVAlignment__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTNavigator_CTextVAlignmentWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{auto(0), top(1), bottom(2), center(5) }";
}

+ (void)initialize {
  if (self == [RARESPOTNavigator_CTextVAlignment class]) {
    RARESPOTNavigator_CTextVAlignment__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 5 } count:4];
    RARESPOTNavigator_CTextVAlignment__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"auto", @"top", @"bottom", @"center" } count:4 type:[IOSClass classWithClass:[NSString class]]];
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
  static J2ObjcClassInfo _RARESPOTNavigator_CTextVAlignment = { "CTextVAlignment", "com.appnativa.rare.spot", "Navigator", 0x9, 1, methods, 6, fields, 0, NULL};
  return &_RARESPOTNavigator_CTextVAlignment;
}

@end
@implementation RARESPOTNavigator_CIconPosition

static IOSIntArray * RARESPOTNavigator_CIconPosition__nchoices_;
static IOSObjectArray * RARESPOTNavigator_CIconPosition__schoices_;

+ (int)getAuto {
  return RARESPOTNavigator_CIconPosition_auto;
}

+ (int)left {
  return RARESPOTNavigator_CIconPosition_left;
}

+ (int)right {
  return RARESPOTNavigator_CIconPosition_right;
}

+ (int)leading {
  return RARESPOTNavigator_CIconPosition_leading;
}

+ (int)trailing {
  return RARESPOTNavigator_CIconPosition_trailing;
}

+ (int)top_center {
  return RARESPOTNavigator_CIconPosition_top_center;
}

+ (int)top_left {
  return RARESPOTNavigator_CIconPosition_top_left;
}

+ (int)top_right {
  return RARESPOTNavigator_CIconPosition_top_right;
}

+ (int)bottom_center {
  return RARESPOTNavigator_CIconPosition_bottom_center;
}

+ (int)bottom_left {
  return RARESPOTNavigator_CIconPosition_bottom_left;
}

+ (int)bottom_right {
  return RARESPOTNavigator_CIconPosition_bottom_right;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTNavigator_CIconPosition__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTNavigator_CIconPosition__schoices_;
}

- (id)init {
  return [self initRARESPOTNavigator_CIconPositionWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTNavigator_CIconPosition__schoices_;
    _nChoices_ = RARESPOTNavigator_CIconPosition__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTNavigator_CIconPositionWithJavaLangInteger:(JavaLangInteger *)ival
                                                withNSString:(NSString *)sval
                                         withJavaLangInteger:(JavaLangInteger *)idefaultval
                                                withNSString:(NSString *)sdefaultval
                                                 withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTNavigator_CIconPosition__schoices_;
    _nChoices_ = RARESPOTNavigator_CIconPosition__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTNavigator_CIconPositionWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{auto(0), left(1), right(2), leading(3), trailing(4), top_center(5), top_left(6), top_right(7), bottom_center(8), bottom_left(9), bottom_right(10) }";
}

+ (void)initialize {
  if (self == [RARESPOTNavigator_CIconPosition class]) {
    RARESPOTNavigator_CIconPosition__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 } count:11];
    RARESPOTNavigator_CIconPosition__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"auto", @"left", @"right", @"leading", @"trailing", @"top_center", @"top_left", @"top_right", @"bottom_center", @"bottom_left", @"bottom_right" } count:11 type:[IOSClass classWithClass:[NSString class]]];
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
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTNavigator_CIconPosition = { "CIconPosition", "com.appnativa.rare.spot", "Navigator", 0x9, 1, methods, 13, fields, 0, NULL};
  return &_RARESPOTNavigator_CIconPosition;
}

@end
