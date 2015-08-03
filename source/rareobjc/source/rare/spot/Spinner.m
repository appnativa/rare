//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Spinner.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/Spinner.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "java/lang/Boolean.h"
#include "java/lang/Integer.h"

@implementation RARESPOTSpinner

- (id)init {
  return [self initRARESPOTSpinnerWithBoolean:YES];
}

- (id)initRARESPOTSpinnerWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    visibleCharacters_ = [[SPOTInteger alloc] initWithJavaLangInteger:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:0] withJavaLangInteger:nil withBoolean:YES];
    editable_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    isCircular_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    buttonsVisible_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    selectedIndex_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withBoolean:NO];
    autoSelect_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    useDesktopStyle_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTSpinnerWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    visibleCharacters_ = [[SPOTInteger alloc] initWithJavaLangInteger:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:0] withJavaLangInteger:nil withBoolean:YES];
    editable_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    isCircular_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    buttonsVisible_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    selectedIndex_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withBoolean:NO];
    autoSelect_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    useDesktopStyle_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 7;
  self->attributeSizeHint_ += 1;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onChange" withNSString:nil];
  [self spot_addElementWithNSString:@"visibleCharacters" withISPOTElement:visibleCharacters_];
  [self spot_addElementWithNSString:@"editable" withISPOTElement:editable_];
  [self spot_addElementWithNSString:@"isCircular" withISPOTElement:isCircular_];
  [self spot_addElementWithNSString:@"buttonsVisible" withISPOTElement:buttonsVisible_];
  [self spot_addElementWithNSString:@"selectedIndex" withISPOTElement:selectedIndex_];
  [self spot_addElementWithNSString:@"autoSelect" withISPOTElement:autoSelect_];
  [self spot_addElementWithNSString:@"useDesktopStyle" withISPOTElement:useDesktopStyle_];
}

- (void)copyAllFieldsTo:(RARESPOTSpinner *)other {
  [super copyAllFieldsTo:other];
  other->autoSelect_ = autoSelect_;
  other->buttonsVisible_ = buttonsVisible_;
  other->editable_ = editable_;
  other->isCircular_ = isCircular_;
  other->selectedIndex_ = selectedIndex_;
  other->useDesktopStyle_ = useDesktopStyle_;
  other->visibleCharacters_ = visibleCharacters_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "visibleCharacters_", NULL, 0x1, "LSPOTInteger" },
    { "editable_", NULL, 0x1, "LSPOTBoolean" },
    { "isCircular_", NULL, 0x1, "LSPOTBoolean" },
    { "buttonsVisible_", NULL, 0x1, "LSPOTBoolean" },
    { "selectedIndex_", NULL, 0x1, "LSPOTInteger" },
    { "autoSelect_", NULL, 0x1, "LSPOTBoolean" },
    { "useDesktopStyle_", NULL, 0x1, "LSPOTBoolean" },
  };
  static J2ObjcClassInfo _RARESPOTSpinner = { "Spinner", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 7, fields, 0, NULL};
  return &_RARESPOTSpinner;
}

@end