//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/CollapsiblePane.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/CollapsibleInfo.h"
#include "com/appnativa/rare/spot/CollapsiblePane.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/spot/SPOTSequence.h"

@implementation RARESPOTCollapsiblePane

- (id)init {
  return [self initRARESPOTCollapsiblePaneWithBoolean:YES];
}

- (id)initRARESPOTCollapsiblePaneWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    collapsibleInfo_ = [[RARESPOTCollapsibleInfo alloc] init];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTCollapsiblePaneWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    collapsibleInfo_ = [[RARESPOTCollapsibleInfo alloc] init];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 1;
  self->attributeSizeHint_ += 4;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onWillExpand" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onWillCollapse" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onHasCollapsed" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onHasExpanded" withNSString:nil];
  [self spot_addElementWithNSString:@"collapsibleInfo" withISPOTElement:collapsibleInfo_];
  [((RARESPOTViewer_CDisableBehavior *) nil_chk(disableBehavior_)) spot_setDefaultValueWithInt:0 withNSString:@"disable_container"];
  [disableBehavior_ spot_setOptionalWithBoolean:NO];
}

- (void)copyAllFieldsTo:(RARESPOTCollapsiblePane *)other {
  [super copyAllFieldsTo:other];
  other->collapsibleInfo_ = collapsibleInfo_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "collapsibleInfo_", NULL, 0x1, "LRARESPOTCollapsibleInfo" },
  };
  static J2ObjcClassInfo _RARESPOTCollapsiblePane = { "CollapsiblePane", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 1, fields, 0, NULL};
  return &_RARESPOTCollapsiblePane;
}

@end
