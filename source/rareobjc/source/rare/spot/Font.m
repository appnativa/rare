//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Font.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/Font.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "java/lang/Boolean.h"
#include "java/lang/Integer.h"

@implementation RARESPOTFont

- (id)init {
  return [self initRARESPOTFontWithBoolean:YES];
}

- (id)initRARESPOTFontWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    family_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    size_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    style_ = [[RARESPOTFont_CStyle alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTFont_CStyle_normal] withNSString:@"normal" withBoolean:YES];
    monospaced_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    underlined_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    strikeThrough_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTFontWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    family_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    size_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    style_ = [[RARESPOTFont_CStyle alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTFont_CStyle_normal] withNSString:@"normal" withBoolean:YES];
    monospaced_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    underlined_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    strikeThrough_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 6;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"family" withISPOTElement:family_];
  [((SPOTPrintableString *) nil_chk(family_)) spot_defineAttributeWithNSString:@"is_property" withNSString:nil];
  [self spot_addElementWithNSString:@"size" withISPOTElement:size_];
  [self spot_addElementWithNSString:@"style" withISPOTElement:style_];
  [self spot_addElementWithNSString:@"monospaced" withISPOTElement:monospaced_];
  [self spot_addElementWithNSString:@"underlined" withISPOTElement:underlined_];
  [self spot_addElementWithNSString:@"strikeThrough" withISPOTElement:strikeThrough_];
}

- (void)copyAllFieldsTo:(RARESPOTFont *)other {
  [super copyAllFieldsTo:other];
  other->family_ = family_;
  other->monospaced_ = monospaced_;
  other->size_ = size_;
  other->strikeThrough_ = strikeThrough_;
  other->style_ = style_;
  other->underlined_ = underlined_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "family_", NULL, 0x1, "LSPOTPrintableString" },
    { "size_", NULL, 0x1, "LSPOTPrintableString" },
    { "style_", NULL, 0x1, "LRARESPOTFont_CStyle" },
    { "monospaced_", NULL, 0x1, "LSPOTBoolean" },
    { "underlined_", NULL, 0x1, "LSPOTBoolean" },
    { "strikeThrough_", NULL, 0x1, "LSPOTBoolean" },
  };
  static J2ObjcClassInfo _RARESPOTFont = { "Font", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 6, fields, 0, NULL};
  return &_RARESPOTFont;
}

@end
@implementation RARESPOTFont_CStyle

static IOSIntArray * RARESPOTFont_CStyle__nchoices_;
static IOSObjectArray * RARESPOTFont_CStyle__schoices_;

+ (int)normal {
  return RARESPOTFont_CStyle_normal;
}

+ (int)bold {
  return RARESPOTFont_CStyle_bold;
}

+ (int)italic {
  return RARESPOTFont_CStyle_italic;
}

+ (int)bold_italic {
  return RARESPOTFont_CStyle_bold_italic;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTFont_CStyle__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTFont_CStyle__schoices_;
}

- (id)init {
  return [self initRARESPOTFont_CStyleWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTFont_CStyle__schoices_;
    _nChoices_ = RARESPOTFont_CStyle__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTFont_CStyleWithJavaLangInteger:(JavaLangInteger *)ival
                                    withNSString:(NSString *)sval
                             withJavaLangInteger:(JavaLangInteger *)idefaultval
                                    withNSString:(NSString *)sdefaultval
                                     withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTFont_CStyle__schoices_;
    _nChoices_ = RARESPOTFont_CStyle__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTFont_CStyleWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{normal(0), bold(1), italic(2), bold_italic(3) }";
}

+ (void)initialize {
  if (self == [RARESPOTFont_CStyle class]) {
    RARESPOTFont_CStyle__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3 } count:4];
    RARESPOTFont_CStyle__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"normal", @"bold", @"italic", @"bold_italic" } count:4 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "normal_", NULL, 0x19, "I" },
    { "bold_", NULL, 0x19, "I" },
    { "italic_", NULL, 0x19, "I" },
    { "bold_italic_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTFont_CStyle = { "CStyle", "com.appnativa.rare.spot", "Font", 0x9, 1, methods, 6, fields, 0, NULL};
  return &_RARESPOTFont_CStyle;
}

@end