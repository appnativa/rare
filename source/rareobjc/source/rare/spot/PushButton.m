//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/PushButton.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/Button.h"
#include "com/appnativa/rare/spot/PushButton.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "java/lang/Boolean.h"
#include "java/lang/Integer.h"

@implementation RARESPOTPushButton

- (id)init {
  return [self initRARESPOTPushButtonWithBoolean:YES];
}

- (id)initRARESPOTPushButtonWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    actionType_ = [[RARESPOTPushButton_CActionType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPushButton_CActionType_scripted] withNSString:@"scripted" withBoolean:NO];
    buttonStyle_ = [[RARESPOTPushButton_CButtonStyle alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPushButton_CButtonStyle_standard] withNSString:@"standard" withBoolean:NO];
    orientation_ = [[RARESPOTPushButton_COrientation alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPushButton_COrientation_auto] withNSString:@"auto" withBoolean:NO];
    defaultMenuIndex_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withNSNumber:[JavaLangInteger valueOfWithInt:100] withNSNumber:[JavaLangInteger valueOfWithInt:-1] withBoolean:NO];
    popupWidget_ = [[SPOTAny alloc] initWithNSString:@"com.appnativa.rare.spot.Widget" withBoolean:YES];
    popupAnimator_ = [[SPOTPrintableString alloc] init];
    focusedAction_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    enabledOnSelectionOnly_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    actionRepeats_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    useSharedBorderForPopup_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTPushButtonWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    actionType_ = [[RARESPOTPushButton_CActionType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPushButton_CActionType_scripted] withNSString:@"scripted" withBoolean:NO];
    buttonStyle_ = [[RARESPOTPushButton_CButtonStyle alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPushButton_CButtonStyle_standard] withNSString:@"standard" withBoolean:NO];
    orientation_ = [[RARESPOTPushButton_COrientation alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTPushButton_COrientation_auto] withNSString:@"auto" withBoolean:NO];
    defaultMenuIndex_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withNSNumber:[JavaLangInteger valueOfWithInt:100] withNSNumber:[JavaLangInteger valueOfWithInt:-1] withBoolean:NO];
    popupWidget_ = [[SPOTAny alloc] initWithNSString:@"com.appnativa.rare.spot.Widget" withBoolean:YES];
    popupAnimator_ = [[SPOTPrintableString alloc] init];
    focusedAction_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    enabledOnSelectionOnly_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    actionRepeats_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    useSharedBorderForPopup_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 10;
  self->attributeSizeHint_ += 2;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onWillExpand" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onWillCollapse" withNSString:nil];
  [self spot_addElementWithNSString:@"actionType" withISPOTElement:actionType_];
  [self spot_addElementWithNSString:@"buttonStyle" withISPOTElement:buttonStyle_];
  [self spot_addElementWithNSString:@"orientation" withISPOTElement:orientation_];
  [self spot_addElementWithNSString:@"defaultMenuIndex" withISPOTElement:defaultMenuIndex_];
  [self spot_addElementWithNSString:@"popupWidget" withISPOTElement:popupWidget_];
  [((SPOTAny *) nil_chk(popupWidget_)) spot_defineAttributeWithNSString:@"transient" withNSString:@"true"];
  [popupWidget_ spot_defineAttributeWithNSString:@"resizable" withNSString:@"false"];
  [popupWidget_ spot_defineAttributeWithNSString:@"closeOnAction" withNSString:@"true"];
  [popupWidget_ spot_defineAttributeWithNSString:@"matchBackground" withNSString:@"true"];
  [popupWidget_ spot_defineAttributeWithNSString:@"okWidget" withNSString:nil];
  [popupWidget_ spot_defineAttributeWithNSString:@"cancelWidget" withNSString:nil];
  [popupWidget_ spot_defineAttributeWithNSString:@"contentPadding" withNSString:nil];
  [popupWidget_ spot_defineAttributeWithNSString:@"url" withNSString:nil];
  [popupWidget_ spot_defineAttributeWithNSString:@"scrollable" withNSString:nil];
  [self spot_addElementWithNSString:@"popupAnimator" withISPOTElement:popupAnimator_];
  [((SPOTPrintableString *) nil_chk(popupAnimator_)) spot_defineAttributeWithNSString:@"duration" withNSString:nil];
  [popupAnimator_ spot_defineAttributeWithNSString:@"direction" withNSString:nil];
  [popupAnimator_ spot_defineAttributeWithNSString:@"acceleration" withNSString:nil];
  [popupAnimator_ spot_defineAttributeWithNSString:@"deceleration" withNSString:nil];
  [popupAnimator_ spot_defineAttributeWithNSString:@"horizontal" withNSString:nil];
  [popupAnimator_ spot_defineAttributeWithNSString:@"fadeIn" withNSString:nil];
  [popupAnimator_ spot_defineAttributeWithNSString:@"fadeOut" withNSString:nil];
  [popupAnimator_ spot_defineAttributeWithNSString:@"customProperties" withNSString:nil];
  [self spot_addElementWithNSString:@"focusedAction" withISPOTElement:focusedAction_];
  [self spot_addElementWithNSString:@"enabledOnSelectionOnly" withISPOTElement:enabledOnSelectionOnly_];
  [self spot_addElementWithNSString:@"actionRepeats" withISPOTElement:actionRepeats_];
  [((SPOTBoolean *) nil_chk(actionRepeats_)) spot_defineAttributeWithNSString:@"delay" withNSString:@"300"];
  [self spot_addElementWithNSString:@"useSharedBorderForPopup" withISPOTElement:useSharedBorderForPopup_];
  [((SPOTBoolean *) nil_chk(useSharedBorderForPopup_)) spot_defineAttributeWithNSString:@"color" withNSString:nil];
  [useSharedBorderForPopup_ spot_defineAttributeWithNSString:@"thickness" withNSString:nil];
  [useSharedBorderForPopup_ spot_defineAttributeWithNSString:@"cornerArc" withNSString:@"4"];
  [((RARESPOTButton_CSubmitValue *) nil_chk(submitValue_)) spot_setDefaultValueWithInt:3 withNSString:@"text"];
  [submitValue_ spot_setOptionalWithBoolean:NO];
}

- (void)copyAllFieldsTo:(RARESPOTPushButton *)other {
  [super copyAllFieldsTo:other];
  other->actionRepeats_ = actionRepeats_;
  other->actionType_ = actionType_;
  other->buttonStyle_ = buttonStyle_;
  other->defaultMenuIndex_ = defaultMenuIndex_;
  other->enabledOnSelectionOnly_ = enabledOnSelectionOnly_;
  other->focusedAction_ = focusedAction_;
  other->orientation_ = orientation_;
  other->popupAnimator_ = popupAnimator_;
  other->popupWidget_ = popupWidget_;
  other->useSharedBorderForPopup_ = useSharedBorderForPopup_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "actionType_", NULL, 0x1, "LRARESPOTPushButton_CActionType" },
    { "buttonStyle_", NULL, 0x1, "LRARESPOTPushButton_CButtonStyle" },
    { "orientation_", NULL, 0x1, "LRARESPOTPushButton_COrientation" },
    { "defaultMenuIndex_", NULL, 0x1, "LSPOTInteger" },
    { "popupWidget_", NULL, 0x1, "LSPOTAny" },
    { "popupAnimator_", NULL, 0x1, "LSPOTPrintableString" },
    { "focusedAction_", NULL, 0x1, "LSPOTBoolean" },
    { "enabledOnSelectionOnly_", NULL, 0x1, "LSPOTBoolean" },
    { "actionRepeats_", NULL, 0x1, "LSPOTBoolean" },
    { "useSharedBorderForPopup_", NULL, 0x1, "LSPOTBoolean" },
  };
  static J2ObjcClassInfo _RARESPOTPushButton = { "PushButton", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 10, fields, 0, NULL};
  return &_RARESPOTPushButton;
}

@end
@implementation RARESPOTPushButton_CActionType

static IOSIntArray * RARESPOTPushButton_CActionType__nchoices_;
static IOSObjectArray * RARESPOTPushButton_CActionType__schoices_;

+ (int)scripted {
  return RARESPOTPushButton_CActionType_scripted;
}

+ (int)submit_form {
  return RARESPOTPushButton_CActionType_submit_form;
}

+ (int)clear_form {
  return RARESPOTPushButton_CActionType_clear_form;
}

+ (int)popup_menu {
  return RARESPOTPushButton_CActionType_popup_menu;
}

+ (int)popup_widget {
  return RARESPOTPushButton_CActionType_popup_widget;
}

+ (int)link {
  return RARESPOTPushButton_CActionType_link;
}

+ (int)list_toggle {
  return RARESPOTPushButton_CActionType_list_toggle;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTPushButton_CActionType__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTPushButton_CActionType__schoices_;
}

- (id)init {
  return [self initRARESPOTPushButton_CActionTypeWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTPushButton_CActionType__schoices_;
    _nChoices_ = RARESPOTPushButton_CActionType__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTPushButton_CActionTypeWithJavaLangInteger:(JavaLangInteger *)ival
                                               withNSString:(NSString *)sval
                                        withJavaLangInteger:(JavaLangInteger *)idefaultval
                                               withNSString:(NSString *)sdefaultval
                                                withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTPushButton_CActionType__schoices_;
    _nChoices_ = RARESPOTPushButton_CActionType__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTPushButton_CActionTypeWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{scripted(0), submit_form(1), clear_form(2), popup_menu(3), popup_widget(4), link(5), list_toggle(6) }";
}

+ (void)initialize {
  if (self == [RARESPOTPushButton_CActionType class]) {
    RARESPOTPushButton_CActionType__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4, 5, 6 } count:7];
    RARESPOTPushButton_CActionType__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"scripted", @"submit_form", @"clear_form", @"popup_menu", @"popup_widget", @"link", @"list_toggle" } count:7 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "scripted_", NULL, 0x19, "I" },
    { "submit_form_", NULL, 0x19, "I" },
    { "clear_form_", NULL, 0x19, "I" },
    { "popup_menu_", NULL, 0x19, "I" },
    { "popup_widget_", NULL, 0x19, "I" },
    { "link_", NULL, 0x19, "I" },
    { "list_toggle_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTPushButton_CActionType = { "CActionType", "com.appnativa.rare.spot", "PushButton", 0x9, 1, methods, 9, fields, 0, NULL};
  return &_RARESPOTPushButton_CActionType;
}

@end
@implementation RARESPOTPushButton_CButtonStyle

static IOSIntArray * RARESPOTPushButton_CButtonStyle__nchoices_;
static IOSObjectArray * RARESPOTPushButton_CButtonStyle__schoices_;

+ (int)standard {
  return RARESPOTPushButton_CButtonStyle_standard;
}

+ (int)toolbar {
  return RARESPOTPushButton_CButtonStyle_toolbar;
}

+ (int)hyperlink {
  return RARESPOTPushButton_CButtonStyle_hyperlink;
}

+ (int)hyperlink_always_underline {
  return RARESPOTPushButton_CButtonStyle_hyperlink_always_underline;
}

+ (int)toggle {
  return RARESPOTPushButton_CButtonStyle_toggle;
}

+ (int)split_toolbar {
  return RARESPOTPushButton_CButtonStyle_split_toolbar;
}

+ (int)toggle_toolbar {
  return RARESPOTPushButton_CButtonStyle_toggle_toolbar;
}

+ (int)platform {
  return RARESPOTPushButton_CButtonStyle_platform;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTPushButton_CButtonStyle__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTPushButton_CButtonStyle__schoices_;
}

- (id)init {
  return [self initRARESPOTPushButton_CButtonStyleWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTPushButton_CButtonStyle__schoices_;
    _nChoices_ = RARESPOTPushButton_CButtonStyle__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTPushButton_CButtonStyleWithJavaLangInteger:(JavaLangInteger *)ival
                                                withNSString:(NSString *)sval
                                         withJavaLangInteger:(JavaLangInteger *)idefaultval
                                                withNSString:(NSString *)sdefaultval
                                                 withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTPushButton_CButtonStyle__schoices_;
    _nChoices_ = RARESPOTPushButton_CButtonStyle__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTPushButton_CButtonStyleWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{standard(0), toolbar(1), hyperlink(2), hyperlink_always_underline(4), toggle(6), split_toolbar(7), toggle_toolbar(8), platform(9) }";
}

+ (void)initialize {
  if (self == [RARESPOTPushButton_CButtonStyle class]) {
    RARESPOTPushButton_CButtonStyle__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 4, 6, 7, 8, 9 } count:8];
    RARESPOTPushButton_CButtonStyle__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"standard", @"toolbar", @"hyperlink", @"hyperlink_always_underline", @"toggle", @"split_toolbar", @"toggle_toolbar", @"platform" } count:8 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "standard_", NULL, 0x19, "I" },
    { "toolbar_", NULL, 0x19, "I" },
    { "hyperlink_", NULL, 0x19, "I" },
    { "hyperlink_always_underline_", NULL, 0x19, "I" },
    { "toggle_", NULL, 0x19, "I" },
    { "split_toolbar_", NULL, 0x19, "I" },
    { "toggle_toolbar_", NULL, 0x19, "I" },
    { "platform_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTPushButton_CButtonStyle = { "CButtonStyle", "com.appnativa.rare.spot", "PushButton", 0x9, 1, methods, 10, fields, 0, NULL};
  return &_RARESPOTPushButton_CButtonStyle;
}

@end
@implementation RARESPOTPushButton_COrientation

static IOSIntArray * RARESPOTPushButton_COrientation__nchoices_;
static IOSObjectArray * RARESPOTPushButton_COrientation__schoices_;

+ (int)getAuto {
  return RARESPOTPushButton_COrientation_auto;
}

+ (int)horizontal {
  return RARESPOTPushButton_COrientation_horizontal;
}

+ (int)vertical_up {
  return RARESPOTPushButton_COrientation_vertical_up;
}

+ (int)vertical_down {
  return RARESPOTPushButton_COrientation_vertical_down;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTPushButton_COrientation__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTPushButton_COrientation__schoices_;
}

- (id)init {
  return [self initRARESPOTPushButton_COrientationWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTPushButton_COrientation__schoices_;
    _nChoices_ = RARESPOTPushButton_COrientation__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTPushButton_COrientationWithJavaLangInteger:(JavaLangInteger *)ival
                                                withNSString:(NSString *)sval
                                         withJavaLangInteger:(JavaLangInteger *)idefaultval
                                                withNSString:(NSString *)sdefaultval
                                                 withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTPushButton_COrientation__schoices_;
    _nChoices_ = RARESPOTPushButton_COrientation__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTPushButton_COrientationWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{auto(0), horizontal(1), vertical_up(2), vertical_down(3) }";
}

+ (void)initialize {
  if (self == [RARESPOTPushButton_COrientation class]) {
    RARESPOTPushButton_COrientation__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3 } count:4];
    RARESPOTPushButton_COrientation__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"auto", @"horizontal", @"vertical_up", @"vertical_down" } count:4 type:[IOSClass classWithClass:[NSString class]]];
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
  static J2ObjcClassInfo _RARESPOTPushButton_COrientation = { "COrientation", "com.appnativa.rare.spot", "PushButton", 0x9, 1, methods, 6, fields, 0, NULL};
  return &_RARESPOTPushButton_COrientation;
}

@end
