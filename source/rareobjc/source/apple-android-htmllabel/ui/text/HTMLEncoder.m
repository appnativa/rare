//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/com/appnativa/rare/ui/text/HTMLEncoder.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/text/HTMLCharSequence.h"
#include "com/appnativa/rare/ui/text/HTMLEncoder.h"
#include "com/appnativa/rare/ui/text/HTMLParser.h"
#include "java/lang/CharSequence.h"
#include "java/lang/StringBuilder.h"

@implementation RAREHTMLEncoder

- (id)init {
  return [super init];
}

- (id<JavaLangCharSequence>)applyFontWithNSString:(NSString *)text
                                   withRAREUIFont:(RAREUIFont *)font {
  return [RAREHTMLParser fromFontWithNSString:text withRAREUIFont:font];
}

- (id<JavaLangCharSequence>)parseHTMLWithNSString:(NSString *)html
                                   withRAREUIFont:(RAREUIFont *)base {
  return [RAREHTMLParser fromHtmlWithNSString:html withJavaLangStringBuilder:nil withRAREUIFont:base];
}

- (NSString *)getPlainTextWithNSString:(NSString *)source {
  return [((JavaLangStringBuilder *) nil_chk([RAREHTMLParser getPlainTextWithNSString:source withAndroidTextHtml_ImageGetter:nil withAndroidTextHtml_TagHandler:nil withJavaLangStringBuilder:[[JavaLangStringBuilder alloc] init]])) description];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "applyFontWithNSString:withRAREUIFont:", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "parseHTMLWithNSString:withRAREUIFont:", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "getPlainTextWithNSString:", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREHTMLEncoder = { "HTMLEncoder", "com.appnativa.rare.ui.text", NULL, 0x1, 3, methods, 0, NULL, 0, NULL};
  return &_RAREHTMLEncoder;
}

@end
