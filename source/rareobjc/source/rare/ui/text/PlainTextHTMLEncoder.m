//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/text/PlainTextHTMLEncoder.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/text/PlainTextHTMLEncoder.h"
#include "java/lang/CharSequence.h"

@implementation RAREPlainTextHTMLEncoder

- (id)init {
  return [super init];
}

- (id<JavaLangCharSequence>)applyFontWithNSString:(NSString *)text
                                   withRAREUIFont:(RAREUIFont *)font {
  return text;
}

- (id<JavaLangCharSequence>)parseHTMLWithNSString:(NSString *)html
                                   withRAREUIFont:(RAREUIFont *)base {
  return [self getPlainTextWithNSString:html];
}

- (NSString *)getPlainTextWithNSString:(NSString *)html {
  NSScanner *theScanner;
  NSString *text = nil;
  theScanner = [NSScanner scannerWithString:html];
  
  while ([theScanner isAtEnd] == NO) {
    
    [theScanner scanUpToString:@"<" intoString:NULL] ;
    
    [theScanner scanUpToString:@">" intoString:&text] ;
    
    html = [html stringByReplacingOccurrencesOfString:[NSString stringWithFormat:@"%@>", text] withString:@""];
  }
  //
  html = [html stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
  
  return html;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "applyFontWithNSString:withRAREUIFont:", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "parseHTMLWithNSString:withRAREUIFont:", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "getPlainTextWithNSString:", NULL, "LNSString", 0x101, NULL },
  };
  static J2ObjcClassInfo _RAREPlainTextHTMLEncoder = { "PlainTextHTMLEncoder", "com.appnativa.rare.ui.text", NULL, 0x1, 3, methods, 0, NULL, 0, NULL};
  return &_RAREPlainTextHTMLEncoder;
}

@end
