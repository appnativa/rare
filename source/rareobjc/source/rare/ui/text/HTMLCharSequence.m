//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/text/HTMLCharSequence.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/text/HTMLCharSequence.h"
#include "com/appnativa/rare/ui/text/NSAttributedStringHTMLEncoder.h"
#include "com/appnativa/rare/ui/text/PlainTextHTMLEncoder.h"
#include "com/appnativa/rare/ui/text/iHTMLEncoder.h"
#include "java/lang/CharSequence.h"

@implementation RAREHTMLCharSequence

static id<RAREiHTMLEncoder> RAREHTMLCharSequence_htmlEncoder_;

+ (id<RAREiHTMLEncoder>)htmlEncoder {
  return RAREHTMLCharSequence_htmlEncoder_;
}

+ (void)setHtmlEncoder:(id<RAREiHTMLEncoder>)htmlEncoder {
  RAREHTMLCharSequence_htmlEncoder_ = htmlEncoder;
}

- (id)initWithNSString:(NSString *)text
                withId:(id)attributedText {
  if (self = [super init]) {
    self->text_ = text;
    self->attributedText_ = attributedText;
  }
  return self;
}

- (unichar)charAtWithInt:(int)index {
  return [((NSString *) nil_chk(text_)) charAtWithInt:index];
}

+ (id<JavaLangCharSequence>)checkSequenceWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                                                   withRAREUIFont:(RAREUIFont *)font {
  if ([(id) s isKindOfClass:[RAREHTMLCharSequence class]]) {
    return s;
  }
  if (!([(id) s isKindOfClass:[NSString class]])) {
    return s;
  }
  NSString *str = (NSString *) check_class_cast(s, [NSString class]);
  if ([((NSString *) nil_chk(str)) hasPrefix:@"<html>"]) {
    if ((font != nil) && ([font isStrikeThrough] || [font isUnderline])) {
      return [((id<RAREiHTMLEncoder>) nil_chk([RAREHTMLCharSequence getEncoder])) parseHTMLWithNSString:str withRAREUIFont:font];
    }
    else {
      return [((id<RAREiHTMLEncoder>) nil_chk([RAREHTMLCharSequence getEncoder])) parseHTMLWithNSString:str withRAREUIFont:nil];
    }
  }
  if ([((RAREUIFont *) nil_chk(font)) isStrikeThrough] || [font isUnderline]) {
    return [((id<RAREiHTMLEncoder>) nil_chk([RAREHTMLCharSequence getEncoder])) applyFontWithNSString:str withRAREUIFont:font];
  }
  return s;
}

- (int)sequenceLength {
  return [((NSString *) nil_chk(text_)) sequenceLength];
}

+ (id<JavaLangCharSequence>)parseHTMLSequenceWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                                                       withRAREUIFont:(RAREUIFont *)font {
  NSString *str = (NSString *) check_class_cast(s, [NSString class]);
  if ([((NSString *) nil_chk(str)) hasPrefix:@"<html>"]) {
    return [((id<RAREiHTMLEncoder>) nil_chk([RAREHTMLCharSequence getEncoder])) parseHTMLWithNSString:str withRAREUIFont:font];
  }
  if ([((RAREUIFont *) nil_chk(font)) isStrikeThrough] || [font isUnderline]) {
    return [((id<RAREiHTMLEncoder>) nil_chk([RAREHTMLCharSequence getEncoder])) applyFontWithNSString:str withRAREUIFont:font];
  }
  return s;
}

- (id<JavaLangCharSequence>)subSequenceFrom:(int)beginIndex to:(int)endIndex {
  return [((NSString *) nil_chk(text_)) substring:beginIndex endIndex:endIndex];
}

- (NSString *)sequenceDescription {
  return text_;
}

- (id)getAttributedText {
  return attributedText_;
}

- (NSString *)getPlainText {
  if(attributedText_) {
    return [(NSAttributedString*)attributedText_ string];
  }
  return text_;
}

+ (NSString *)getPlainTextWithNSString:(NSString *)html {
  return [((id<RAREiHTMLEncoder>) nil_chk([RAREHTMLCharSequence getEncoder])) getPlainTextWithNSString:html];
}

+ (id<RAREiHTMLEncoder>)getEncoder {
  if (RAREHTMLCharSequence_htmlEncoder_ == nil) {
    RAREHTMLCharSequence_htmlEncoder_ = (id<RAREiHTMLEncoder>) check_protocol_cast([RAREPlatform createObjectWithNSString:@"com.appnativa.rare.ui.text.HTMLEncoder"], @protocol(RAREiHTMLEncoder));
    if (RAREHTMLCharSequence_htmlEncoder_ == nil) {
      if ([RAREPlatform isIOS] && ([RAREPlatform getOsVersion] >= 7)) {
        RAREHTMLCharSequence_htmlEncoder_ = [[RARENSAttributedStringHTMLEncoder alloc] init];
      }
      else {
        RAREHTMLCharSequence_htmlEncoder_ = [[RAREPlainTextHTMLEncoder alloc] init];
      }
    }
  }
  return RAREHTMLCharSequence_htmlEncoder_;
}

- (void)copyAllFieldsTo:(RAREHTMLCharSequence *)other {
  [super copyAllFieldsTo:other];
  other->attributedText_ = attributedText_;
  other->text_ = text_;
}

- (NSString *)description {
  return [self sequenceDescription];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "checkSequenceWithJavaLangCharSequence:withRAREUIFont:", NULL, "LJavaLangCharSequence", 0x9, NULL },
    { "parseHTMLSequenceWithJavaLangCharSequence:withRAREUIFont:", NULL, "LJavaLangCharSequence", 0x9, NULL },
    { "getAttributedText", NULL, "LNSObject", 0x1, NULL },
    { "getPlainText", NULL, "LNSString", 0x101, NULL },
    { "getPlainTextWithNSString:", NULL, "LNSString", 0x9, NULL },
    { "getEncoder", NULL, "LRAREiHTMLEncoder", 0xa, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "htmlEncoder_", NULL, 0x9, "LRAREiHTMLEncoder" },
    { "attributedText_", NULL, 0x4, "LNSObject" },
    { "text_", NULL, 0x4, "LNSString" },
  };
  static J2ObjcClassInfo _RAREHTMLCharSequence = { "HTMLCharSequence", "com.appnativa.rare.ui.text", NULL, 0x1, 6, methods, 3, fields, 0, NULL};
  return &_RAREHTMLCharSequence;
}

@end
