//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/CollapsibleInfo.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/CollapsibleInfo.h"
#include "com/appnativa/rare/spot/Font.h"
#include "com/appnativa/rare/spot/GridCell.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTCollapsibleInfo

- (id)init {
  return [self initRARESPOTCollapsibleInfoWithBoolean:YES];
}

- (id)initRARESPOTCollapsibleInfoWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    icon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    initiallyCollapsed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    title_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    collapsedTitle_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    collapseTip_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    expandTip_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    titleCell_ = nil;
    titleFont_ = [[RARESPOTFont alloc] init];
    expandOnDragover_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    expander_ = [[RARESPOTCollapsibleInfo_CExpander alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTCollapsibleInfo_CExpander_twisty] withNSString:@"twisty" withBoolean:NO];
    animateTransitions_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    toggleOnTitleSingleClick_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    userControllable_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showTitleBar_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    opaqueTitleBar_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    templateName_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTCollapsibleInfoWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    icon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    initiallyCollapsed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    title_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    collapsedTitle_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    collapseTip_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    expandTip_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    titleCell_ = nil;
    titleFont_ = [[RARESPOTFont alloc] init];
    expandOnDragover_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    expander_ = [[RARESPOTCollapsibleInfo_CExpander alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTCollapsibleInfo_CExpander_twisty] withNSString:@"twisty" withBoolean:NO];
    animateTransitions_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    toggleOnTitleSingleClick_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    userControllable_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showTitleBar_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    opaqueTitleBar_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    templateName_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 16;
  self->attributeSizeHint_ += 4;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onWillExpand" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onWillCollapse" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onHasCollapsed" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onHasExpanded" withNSString:nil];
  [self spot_addElementWithNSString:@"icon" withISPOTElement:icon_];
  [((SPOTPrintableString *) nil_chk(icon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"initiallyCollapsed" withISPOTElement:initiallyCollapsed_];
  [self spot_addElementWithNSString:@"title" withISPOTElement:title_];
  [self spot_addElementWithNSString:@"collapsedTitle" withISPOTElement:collapsedTitle_];
  [self spot_addElementWithNSString:@"collapseTip" withISPOTElement:collapseTip_];
  [self spot_addElementWithNSString:@"expandTip" withISPOTElement:expandTip_];
  [self spot_addElementWithNSString:@"titleCell" withISPOTElement:titleCell_];
  [self spot_addElementWithNSString:@"titleFont" withISPOTElement:titleFont_];
  [self spot_addElementWithNSString:@"expandOnDragover" withISPOTElement:expandOnDragover_];
  [self spot_addElementWithNSString:@"expander" withISPOTElement:expander_];
  [((RARESPOTCollapsibleInfo_CExpander *) nil_chk(expander_)) spot_defineAttributeWithNSString:@"expandIcon" withNSString:nil];
  [expander_ spot_defineAttributeWithNSString:@"collapseIcon" withNSString:nil];
  [expander_ spot_defineAttributeWithNSString:@"iconOnTheLeft" withNSString:nil];
  [self spot_addElementWithNSString:@"animateTransitions" withISPOTElement:animateTransitions_];
  [((SPOTBoolean *) nil_chk(animateTransitions_)) spot_defineAttributeWithNSString:@"duration" withNSString:nil];
  [animateTransitions_ spot_defineAttributeWithNSString:@"acceleration" withNSString:nil];
  [animateTransitions_ spot_defineAttributeWithNSString:@"deceleration" withNSString:nil];
  [animateTransitions_ spot_defineAttributeWithNSString:@"diagonal" withNSString:nil];
  [animateTransitions_ spot_defineAttributeWithNSString:@"diagonalAnchor" withNSString:nil];
  [animateTransitions_ spot_defineAttributeWithNSString:@"fade" withNSString:nil];
  [self spot_addElementWithNSString:@"toggleOnTitleSingleClick" withISPOTElement:toggleOnTitleSingleClick_];
  [self spot_addElementWithNSString:@"userControllable" withISPOTElement:userControllable_];
  [self spot_addElementWithNSString:@"showTitleBar" withISPOTElement:showTitleBar_];
  [self spot_addElementWithNSString:@"opaqueTitleBar" withISPOTElement:opaqueTitleBar_];
  [self spot_addElementWithNSString:@"templateName" withISPOTElement:templateName_];
  [((SPOTPrintableString *) nil_chk(templateName_)) spot_defineAttributeWithNSString:@"context" withNSString:nil];
}

- (RARESPOTGridCell *)getTitleCell {
  return titleCell_;
}

- (RARESPOTGridCell *)getTitleCellReference {
  if (titleCell_ == nil) {
    titleCell_ = [[RARESPOTGridCell alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"titleCell" withISPOTElement:titleCell_];
    [titleCell_ spot_defineAttributeWithNSString:@"foreground" withNSString:nil];
  }
  return titleCell_;
}

- (void)setTitleCellWithISPOTElement:(id<iSPOTElement>)reference {
  titleCell_ = (RARESPOTGridCell *) check_class_cast(reference, [RARESPOTGridCell class]);
  (void) [self spot_setReferenceWithNSString:@"titleCell" withISPOTElement:reference];
}

- (void)copyAllFieldsTo:(RARESPOTCollapsibleInfo *)other {
  [super copyAllFieldsTo:other];
  other->animateTransitions_ = animateTransitions_;
  other->collapseTip_ = collapseTip_;
  other->collapsedTitle_ = collapsedTitle_;
  other->expandOnDragover_ = expandOnDragover_;
  other->expandTip_ = expandTip_;
  other->expander_ = expander_;
  other->icon_ = icon_;
  other->initiallyCollapsed_ = initiallyCollapsed_;
  other->opaqueTitleBar_ = opaqueTitleBar_;
  other->showTitleBar_ = showTitleBar_;
  other->templateName_ = templateName_;
  other->title_ = title_;
  other->titleCell_ = titleCell_;
  other->titleFont_ = titleFont_;
  other->toggleOnTitleSingleClick_ = toggleOnTitleSingleClick_;
  other->userControllable_ = userControllable_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getTitleCell", NULL, "LRARESPOTGridCell", 0x1, NULL },
    { "getTitleCellReference", NULL, "LRARESPOTGridCell", 0x1, NULL },
    { "setTitleCellWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "icon_", NULL, 0x1, "LSPOTPrintableString" },
    { "initiallyCollapsed_", NULL, 0x1, "LSPOTBoolean" },
    { "title_", NULL, 0x1, "LSPOTPrintableString" },
    { "collapsedTitle_", NULL, 0x1, "LSPOTPrintableString" },
    { "collapseTip_", NULL, 0x1, "LSPOTPrintableString" },
    { "expandTip_", NULL, 0x1, "LSPOTPrintableString" },
    { "titleCell_", NULL, 0x4, "LRARESPOTGridCell" },
    { "titleFont_", NULL, 0x1, "LRARESPOTFont" },
    { "expandOnDragover_", NULL, 0x1, "LSPOTBoolean" },
    { "expander_", NULL, 0x1, "LRARESPOTCollapsibleInfo_CExpander" },
    { "animateTransitions_", NULL, 0x1, "LSPOTBoolean" },
    { "toggleOnTitleSingleClick_", NULL, 0x1, "LSPOTBoolean" },
    { "userControllable_", NULL, 0x1, "LSPOTBoolean" },
    { "showTitleBar_", NULL, 0x1, "LSPOTBoolean" },
    { "opaqueTitleBar_", NULL, 0x1, "LSPOTBoolean" },
    { "templateName_", NULL, 0x1, "LSPOTPrintableString" },
  };
  static J2ObjcClassInfo _RARESPOTCollapsibleInfo = { "CollapsibleInfo", "com.appnativa.rare.spot", NULL, 0x1, 5, methods, 16, fields, 0, NULL};
  return &_RARESPOTCollapsibleInfo;
}

@end
@implementation RARESPOTCollapsibleInfo_CExpander

static IOSIntArray * RARESPOTCollapsibleInfo_CExpander__nchoices_;
static IOSObjectArray * RARESPOTCollapsibleInfo_CExpander__schoices_;

+ (int)twisty {
  return RARESPOTCollapsibleInfo_CExpander_twisty;
}

+ (int)chevron {
  return RARESPOTCollapsibleInfo_CExpander_chevron;
}

+ (int)custom {
  return RARESPOTCollapsibleInfo_CExpander_custom;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTCollapsibleInfo_CExpander__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTCollapsibleInfo_CExpander__schoices_;
}

- (id)init {
  return [self initRARESPOTCollapsibleInfo_CExpanderWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTCollapsibleInfo_CExpander__schoices_;
    _nChoices_ = RARESPOTCollapsibleInfo_CExpander__nchoices_;
    [self spot_setAttributes];
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTCollapsibleInfo_CExpanderWithJavaLangInteger:(JavaLangInteger *)ival
                                                  withNSString:(NSString *)sval
                                           withJavaLangInteger:(JavaLangInteger *)idefaultval
                                                  withNSString:(NSString *)sdefaultval
                                                   withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTCollapsibleInfo_CExpander__schoices_;
    _nChoices_ = RARESPOTCollapsibleInfo_CExpander__nchoices_;
    [self spot_setAttributes];
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTCollapsibleInfo_CExpanderWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{twisty(0), chevron(1), custom(2) }";
}

- (void)spot_setAttributes {
  self->attributeSizeHint_ += 3;
  [self spot_defineAttributeWithNSString:@"expandIcon" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"collapseIcon" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"iconOnTheLeft" withNSString:nil];
}

+ (void)initialize {
  if (self == [RARESPOTCollapsibleInfo_CExpander class]) {
    RARESPOTCollapsibleInfo_CExpander__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2 } count:3];
    RARESPOTCollapsibleInfo_CExpander__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"twisty", @"chevron", @"custom" } count:3 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_setAttributes", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "twisty_", NULL, 0x19, "I" },
    { "chevron_", NULL, 0x19, "I" },
    { "custom_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTCollapsibleInfo_CExpander = { "CExpander", "com.appnativa.rare.spot", "CollapsibleInfo", 0x9, 2, methods, 5, fields, 0, NULL};
  return &_RARESPOTCollapsibleInfo_CExpander;
}

@end
