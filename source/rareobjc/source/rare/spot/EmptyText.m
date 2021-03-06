//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/EmptyText.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/EmptyText.h"
#include "com/appnativa/rare/spot/Font.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/ClassCastException.h"

@implementation RARESPOTEmptyText

- (id)init {
  return [self initRARESPOTEmptyTextWithBoolean:YES];
}

- (id)initRARESPOTEmptyTextWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    value_ = [[SPOTPrintableString alloc] init];
    font_ = nil;
    fgColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTEmptyTextWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    value_ = [[SPOTPrintableString alloc] init];
    font_ = nil;
    fgColor_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 3;
  self->attributeSizeHint_ += 1;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"showBeforeLoad" withNSString:nil];
  [self spot_addElementWithNSString:@"value" withISPOTElement:value_];
  [self spot_addElementWithNSString:@"font" withISPOTElement:font_];
  [self spot_addElementWithNSString:@"fgColor" withISPOTElement:fgColor_];
}

- (RARESPOTFont *)getFont {
  return font_;
}

- (RARESPOTFont *)getFontReference {
  if (font_ == nil) {
    font_ = [[RARESPOTFont alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"font" withISPOTElement:font_];
  }
  return font_;
}

- (void)setFontWithISPOTElement:(id<iSPOTElement>)reference {
  font_ = (RARESPOTFont *) check_class_cast(reference, [RARESPOTFont class]);
  (void) [self spot_setReferenceWithNSString:@"font" withISPOTElement:reference];
}

- (void)copyAllFieldsTo:(RARESPOTEmptyText *)other {
  [super copyAllFieldsTo:other];
  other->fgColor_ = fgColor_;
  other->font_ = font_;
  other->value_ = value_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getFont", NULL, "LRARESPOTFont", 0x1, NULL },
    { "getFontReference", NULL, "LRARESPOTFont", 0x1, NULL },
    { "setFontWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "value_", NULL, 0x1, "LSPOTPrintableString" },
    { "font_", NULL, 0x4, "LRARESPOTFont" },
    { "fgColor_", NULL, 0x1, "LSPOTPrintableString" },
  };
  static J2ObjcClassInfo _RARESPOTEmptyText = { "EmptyText", "com.appnativa.rare.spot", NULL, 0x1, 5, methods, 3, fields, 0, NULL};
  return &_RARESPOTEmptyText;
}

@end
