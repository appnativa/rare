//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/text/iHTMLEncoder.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/text/iHTMLEncoder.h"
#include "java/lang/CharSequence.h"


@interface RAREiHTMLEncoder : NSObject
@end

@implementation RAREiHTMLEncoder

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getPlainTextWithNSString:", NULL, "LNSString", 0x401, NULL },
    { "applyFontWithNSString:withRAREUIFont:", NULL, "LJavaLangCharSequence", 0x401, NULL },
    { "parseHTMLWithNSString:withRAREUIFont:", NULL, "LJavaLangCharSequence", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiHTMLEncoder = { "iHTMLEncoder", "com.appnativa.rare.ui.text", NULL, 0x201, 3, methods, 0, NULL, 0, NULL};
  return &_RAREiHTMLEncoder;
}

@end
