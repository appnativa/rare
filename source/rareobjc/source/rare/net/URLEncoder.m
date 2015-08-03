//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/net/URLEncoder.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSByteArray.h"
#include "IOSClass.h"
#include "com/appnativa/rare/exception/ApplicationException.h"
#include "com/appnativa/rare/net/URLEncoder.h"
#include "java/io/ByteArrayOutputStream.h"
#include "java/io/IOException.h"
#include "java/io/OutputStream.h"
#include "java/io/UnsupportedEncodingException.h"
#include "java/io/Writer.h"
#include "java/lang/ArrayIndexOutOfBoundsException.h"
#include "java/lang/Character.h"
#include "java/lang/StringBuilder.h"
#include "java/util/BitSet.h"

@implementation RAREURLEncoder

static NSString * RAREURLEncoder_charset_ = @"iso-8859-1";
static JavaUtilBitSet * RAREURLEncoder_WWW_FORM_URL_;
static JavaUtilBitSet * RAREURLEncoder_ALL_URL_;

+ (NSString *)charset {
  return RAREURLEncoder_charset_;
}

+ (void)setCharset:(NSString *)charset {
  RAREURLEncoder_charset_ = charset;
}

+ (JavaUtilBitSet *)WWW_FORM_URL {
  return RAREURLEncoder_WWW_FORM_URL_;
}

+ (JavaUtilBitSet *)ALL_URL {
  return RAREURLEncoder_ALL_URL_;
}

- (id)init {
  return [super init];
}

+ (IOSByteArray *)decodeWithByteArray:(IOSByteArray *)bytes {
  return [RAREURLEncoder decodeUrlWithByteArray:bytes];
}

+ (id)decodeWithId:(id)pObject {
  if (pObject == nil) {
    return nil;
  }
  else if ([pObject isKindOfClass:[IOSByteArray class]]) {
    return [RAREURLEncoder decodeWithByteArray:(IOSByteArray *) check_class_cast(pObject, [IOSByteArray class])];
  }
  else if ([pObject isKindOfClass:[NSString class]]) {
    return [RAREURLEncoder decodeWithNSString:(NSString *) check_class_cast(pObject, [NSString class])];
  }
  else {
    @throw [[RAREApplicationException alloc] initWithNSString:[NSString stringWithFormat:@"Objects of type %@ cannot be URL decoded", [[pObject getClass] getName]]];
  }
}

+ (NSString *)decodeWithNSString:(NSString *)pString {
  if (pString == nil) {
    return nil;
  }
  @try {
    return [RAREURLEncoder decodeWithNSString:pString withNSString:[RAREURLEncoder getDefaultCharset]];
  }
  @catch (JavaIoUnsupportedEncodingException *e) {
    @throw [[RAREApplicationException alloc] initWithNSString:[((JavaIoUnsupportedEncodingException *) nil_chk(e)) getMessage]];
  }
}

+ (NSString *)decodeWithNSString:(NSString *)pString
                    withNSString:(NSString *)charset {
  if (pString == nil) {
    return nil;
  }
  return [NSString stringWithBytes:[RAREURLEncoder decodeWithByteArray:[((NSString *) nil_chk(pString)) getBytesWithCharsetName:@"US-ASCII"]] charsetName:charset];
}

+ (IOSByteArray *)decodeUrlWithByteArray:(IOSByteArray *)bytes {
  if (bytes == nil) {
    return nil;
  }
  JavaIoByteArrayOutputStream *buffer = [[JavaIoByteArrayOutputStream alloc] init];
  for (int i = 0; i < (int) [((IOSByteArray *) nil_chk(bytes)) count]; i++) {
    int b = IOSByteArray_Get(bytes, i);
    if (b == '+') {
      [buffer writeWithInt:' '];
    }
    else if (b == '%') {
      @try {
        int u = [JavaLangCharacter digitWithChar:(unichar) IOSByteArray_Get(bytes, ++i) withInt:16];
        int l = [JavaLangCharacter digitWithChar:(unichar) IOSByteArray_Get(bytes, ++i) withInt:16];
        if ((u == -1) || (l == -1)) {
          @throw [[RAREApplicationException alloc] initWithNSString:@"Invalid URL encoding"];
        }
        [buffer writeWithInt:(unichar) ((u << 4) + l)];
      }
      @catch (JavaLangArrayIndexOutOfBoundsException *e) {
        @throw [[RAREApplicationException alloc] initWithNSString:@"Invalid URL encoding"];
      }
    }
    else {
      [buffer writeWithInt:b];
    }
  }
  return [buffer toByteArray];
}

+ (IOSByteArray *)encodeWithByteArray:(IOSByteArray *)bytes {
  return [RAREURLEncoder encodeUrlWithJavaUtilBitSet:RAREURLEncoder_WWW_FORM_URL_ withByteArray:bytes];
}

+ (id)encodeWithId:(id)pObject {
  if (pObject == nil) {
    return nil;
  }
  else if ([pObject isKindOfClass:[IOSByteArray class]]) {
    return [RAREURLEncoder encodeWithByteArray:(IOSByteArray *) check_class_cast(pObject, [IOSByteArray class])];
  }
  else if ([pObject isKindOfClass:[NSString class]]) {
    return [RAREURLEncoder encodeWithNSString:(NSString *) check_class_cast(pObject, [NSString class])];
  }
  else {
    @throw [[RAREApplicationException alloc] initWithNSString:[NSString stringWithFormat:@"Objects of type %@ cannot be URL encoded", [[pObject getClass] getName]]];
  }
}

+ (NSString *)encodeWithNSString:(NSString *)pString {
  if (pString == nil) {
    return nil;
  }
  @try {
    return [RAREURLEncoder encodeWithNSString:pString withNSString:[RAREURLEncoder getDefaultCharset]];
  }
  @catch (JavaIoUnsupportedEncodingException *e) {
    @throw [[RAREApplicationException alloc] initWithNSString:[((JavaIoUnsupportedEncodingException *) nil_chk(e)) getMessage]];
  }
}

+ (NSString *)encodeExWithNSString:(NSString *)pString {
  if (pString == nil) {
    return nil;
  }
  @try {
    if ((pString == nil) || ([pString sequenceLength] == 0)) {
      return pString;
    }
    return [NSString stringWithBytes:[RAREURLEncoder encodeUrlWithJavaUtilBitSet:RAREURLEncoder_ALL_URL_ withByteArray:[((NSString *) nil_chk(pString)) getBytesWithCharsetName:[RAREURLEncoder getDefaultCharset]]] charsetName:@"US-ASCII"];
  }
  @catch (JavaIoUnsupportedEncodingException *e) {
    @throw [[RAREApplicationException alloc] initWithNSString:[((JavaIoUnsupportedEncodingException *) nil_chk(e)) getMessage]];
  }
}

+ (NSString *)encodeWithNSString:(NSString *)pString
                    withNSString:(NSString *)charset {
  if ((pString == nil) || ([pString sequenceLength] == 0)) {
    return pString;
  }
  return [NSString stringWithBytes:[RAREURLEncoder encodeWithByteArray:[((NSString *) nil_chk(pString)) getBytesWithCharsetName:charset]] charsetName:@"US-ASCII"];
}

+ (void)encodeWithNSString:(NSString *)pString
              withNSString:(NSString *)charset
    withJavaIoOutputStream:(JavaIoOutputStream *)buffer {
  if ((pString == nil) || ([pString sequenceLength] == 0)) {
    return;
  }
  [RAREURLEncoder encodeUrlWithJavaUtilBitSet:RAREURLEncoder_WWW_FORM_URL_ withByteArray:[((NSString *) nil_chk(pString)) getBytesWithCharsetName:charset] withJavaIoOutputStream:buffer];
}

+ (void)encodeWithNSString:(NSString *)pString
              withNSString:(NSString *)charset
 withJavaLangStringBuilder:(JavaLangStringBuilder *)buffer {
  if (pString == nil) {
    return;
  }
  [RAREURLEncoder encodeUrlWithJavaUtilBitSet:RAREURLEncoder_WWW_FORM_URL_ withByteArray:[((NSString *) nil_chk(pString)) getBytesWithCharsetName:charset] withJavaLangStringBuilder:buffer];
}

+ (void)encodeWithNSString:(NSString *)pString
              withNSString:(NSString *)charset
          withJavaIoWriter:(JavaIoWriter *)buffer {
  if ((pString == nil) || ([pString sequenceLength] == 0)) {
    return;
  }
  [RAREURLEncoder encodeUrlWithJavaUtilBitSet:RAREURLEncoder_WWW_FORM_URL_ withByteArray:[((NSString *) nil_chk(pString)) getBytesWithCharsetName:charset] withJavaIoWriter:buffer];
}

+ (IOSByteArray *)encodeUrlWithJavaUtilBitSet:(JavaUtilBitSet *)urlsafe
                                withByteArray:(IOSByteArray *)bytes {
  if (bytes == nil) {
    return nil;
  }
  if (urlsafe == nil) {
    urlsafe = RAREURLEncoder_WWW_FORM_URL_;
  }
  JavaIoByteArrayOutputStream *buffer = [[JavaIoByteArrayOutputStream alloc] init];
  for (int i = 0; i < (int) [((IOSByteArray *) nil_chk(bytes)) count]; i++) {
    int b = IOSByteArray_Get(bytes, i);
    if (b < 0) {
      b = 256 + b;
    }
    if ([((JavaUtilBitSet *) nil_chk(urlsafe)) getWithInt:b]) {
      if (b == ' ') {
        b = '+';
      }
      [buffer writeWithInt:b];
    }
    else {
      [buffer writeWithInt:'%'];
      unichar hex1 = [JavaLangCharacter toUpperCaseWithChar:[JavaLangCharacter forDigitWithInt:(b >> 4) & (int) 0xF withInt:16]];
      unichar hex2 = [JavaLangCharacter toUpperCaseWithChar:[JavaLangCharacter forDigitWithInt:b & (int) 0xF withInt:16]];
      [buffer writeWithInt:hex1];
      [buffer writeWithInt:hex2];
    }
  }
  return [buffer toByteArray];
}

+ (void)encodeUrlWithJavaUtilBitSet:(JavaUtilBitSet *)urlsafe
                      withByteArray:(IOSByteArray *)bytes
             withJavaIoOutputStream:(JavaIoOutputStream *)buffer {
  if (bytes == nil) {
    return;
  }
  if (urlsafe == nil) {
    urlsafe = RAREURLEncoder_WWW_FORM_URL_;
  }
  for (int i = 0; i < (int) [((IOSByteArray *) nil_chk(bytes)) count]; i++) {
    int b = IOSByteArray_Get(bytes, i);
    if (b < 0) {
      b = 256 + b;
    }
    if ([((JavaUtilBitSet *) nil_chk(urlsafe)) getWithInt:b]) {
      if (b == ' ') {
        b = '+';
      }
      [((JavaIoOutputStream *) nil_chk(buffer)) writeWithInt:b];
    }
    else {
      [((JavaIoOutputStream *) nil_chk(buffer)) writeWithInt:'%'];
      unichar hex1 = [JavaLangCharacter toUpperCaseWithChar:[JavaLangCharacter forDigitWithInt:(b >> 4) & (int) 0xF withInt:16]];
      unichar hex2 = [JavaLangCharacter toUpperCaseWithChar:[JavaLangCharacter forDigitWithInt:b & (int) 0xF withInt:16]];
      [buffer writeWithInt:hex1];
      [buffer writeWithInt:hex2];
    }
  }
}

+ (void)encodeUrlWithJavaUtilBitSet:(JavaUtilBitSet *)urlsafe
                      withByteArray:(IOSByteArray *)bytes
          withJavaLangStringBuilder:(JavaLangStringBuilder *)buffer {
  if (bytes == nil) {
    return;
  }
  if (urlsafe == nil) {
    urlsafe = RAREURLEncoder_WWW_FORM_URL_;
  }
  for (int i = 0; i < (int) [((IOSByteArray *) nil_chk(bytes)) count]; i++) {
    int b = IOSByteArray_Get(bytes, i);
    if (b < 0) {
      b = 256 + b;
    }
    if ([((JavaUtilBitSet *) nil_chk(urlsafe)) getWithInt:b]) {
      if (b == ' ') {
        b = '+';
      }
      (void) [((JavaLangStringBuilder *) nil_chk(buffer)) appendWithChar:(unichar) b];
    }
    else {
      (void) [((JavaLangStringBuilder *) nil_chk(buffer)) appendWithChar:'%'];
      unichar hex1 = [JavaLangCharacter toUpperCaseWithChar:[JavaLangCharacter forDigitWithInt:(b >> 4) & (int) 0xF withInt:16]];
      unichar hex2 = [JavaLangCharacter toUpperCaseWithChar:[JavaLangCharacter forDigitWithInt:b & (int) 0xF withInt:16]];
      (void) [buffer appendWithChar:hex1];
      (void) [buffer appendWithChar:hex2];
    }
  }
}

+ (void)encodeUrlWithJavaUtilBitSet:(JavaUtilBitSet *)urlsafe
                      withByteArray:(IOSByteArray *)bytes
                   withJavaIoWriter:(JavaIoWriter *)buffer {
  if (bytes == nil) {
    return;
  }
  if (urlsafe == nil) {
    urlsafe = RAREURLEncoder_WWW_FORM_URL_;
  }
  for (int i = 0; i < (int) [((IOSByteArray *) nil_chk(bytes)) count]; i++) {
    int b = IOSByteArray_Get(bytes, i);
    if (b < 0) {
      b = 256 + b;
    }
    if ([((JavaUtilBitSet *) nil_chk(urlsafe)) getWithInt:b]) {
      if (b == ' ') {
        b = '+';
      }
      [((JavaIoWriter *) nil_chk(buffer)) writeWithInt:(unichar) b];
    }
    else {
      [((JavaIoWriter *) nil_chk(buffer)) writeWithInt:'%'];
      unichar hex1 = [JavaLangCharacter toUpperCaseWithChar:[JavaLangCharacter forDigitWithInt:(b >> 4) & (int) 0xF withInt:16]];
      unichar hex2 = [JavaLangCharacter toUpperCaseWithChar:[JavaLangCharacter forDigitWithInt:b & (int) 0xF withInt:16]];
      [buffer writeWithInt:hex1];
      [buffer writeWithInt:hex2];
    }
  }
}

+ (NSString *)getDefaultCharset {
  return RAREURLEncoder_charset_;
}

+ (void)initialize {
  if (self == [RAREURLEncoder class]) {
    RAREURLEncoder_WWW_FORM_URL_ = [[JavaUtilBitSet alloc] initWithInt:256];
    RAREURLEncoder_ALL_URL_ = [[JavaUtilBitSet alloc] initWithInt:256];
    {
      for (int i = 'a'; i <= 'z'; i++) {
        [RAREURLEncoder_WWW_FORM_URL_ setWithInt:i];
        [RAREURLEncoder_ALL_URL_ setWithInt:i];
      }
      for (int i = 'A'; i <= 'Z'; i++) {
        [RAREURLEncoder_WWW_FORM_URL_ setWithInt:i];
        [RAREURLEncoder_ALL_URL_ setWithInt:i];
      }
      for (int i = '0'; i <= '9'; i++) {
        [RAREURLEncoder_WWW_FORM_URL_ setWithInt:i];
        [RAREURLEncoder_ALL_URL_ setWithInt:i];
      }
      [RAREURLEncoder_ALL_URL_ setWithInt:'_'];
      [RAREURLEncoder_ALL_URL_ setWithInt:'.'];
      [RAREURLEncoder_WWW_FORM_URL_ setWithInt:'/'];
      [RAREURLEncoder_WWW_FORM_URL_ setWithInt:'-'];
      [RAREURLEncoder_WWW_FORM_URL_ setWithInt:'_'];
      [RAREURLEncoder_WWW_FORM_URL_ setWithInt:'.'];
      [RAREURLEncoder_WWW_FORM_URL_ setWithInt:'*'];
    }
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "decodeWithByteArray:", NULL, "LIOSByteArray", 0x9, "RAREApplicationException" },
    { "decodeWithId:", NULL, "LNSObject", 0x9, "RAREApplicationException" },
    { "decodeWithNSString:", NULL, "LNSString", 0x9, "RAREApplicationException" },
    { "decodeWithNSString:withNSString:", NULL, "LNSString", 0x9, "RAREApplicationException;JavaIoUnsupportedEncodingException" },
    { "decodeUrlWithByteArray:", NULL, "LIOSByteArray", 0x19, "RAREApplicationException" },
    { "encodeWithByteArray:", NULL, "LIOSByteArray", 0x9, NULL },
    { "encodeWithId:", NULL, "LNSObject", 0x9, "RAREApplicationException" },
    { "encodeWithNSString:", NULL, "LNSString", 0x9, "RAREApplicationException" },
    { "encodeExWithNSString:", NULL, "LNSString", 0x9, "RAREApplicationException" },
    { "encodeWithNSString:withNSString:", NULL, "LNSString", 0x9, "JavaIoUnsupportedEncodingException" },
    { "encodeWithNSString:withNSString:withJavaIoOutputStream:", NULL, "V", 0x9, "JavaIoUnsupportedEncodingException;JavaIoIOException" },
    { "encodeWithNSString:withNSString:withJavaLangStringBuilder:", NULL, "V", 0x9, "JavaIoUnsupportedEncodingException" },
    { "encodeWithNSString:withNSString:withJavaIoWriter:", NULL, "V", 0x9, "JavaIoUnsupportedEncodingException;JavaIoIOException" },
    { "encodeUrlWithJavaUtilBitSet:withByteArray:", NULL, "LIOSByteArray", 0x19, NULL },
    { "encodeUrlWithJavaUtilBitSet:withByteArray:withJavaIoOutputStream:", NULL, "V", 0x19, "JavaIoIOException" },
    { "encodeUrlWithJavaUtilBitSet:withByteArray:withJavaLangStringBuilder:", NULL, "V", 0x19, NULL },
    { "encodeUrlWithJavaUtilBitSet:withByteArray:withJavaIoWriter:", NULL, "V", 0x19, "JavaIoIOException" },
    { "getDefaultCharset", NULL, "LNSString", 0x9, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "charset_", NULL, 0xa, "LNSString" },
    { "WWW_FORM_URL_", NULL, 0x1c, "LJavaUtilBitSet" },
    { "ALL_URL_", NULL, 0x1c, "LJavaUtilBitSet" },
  };
  static J2ObjcClassInfo _RAREURLEncoder = { "URLEncoder", "com.appnativa.rare.net", NULL, 0x1, 18, methods, 3, fields, 0, NULL};
  return &_RAREURLEncoder;
}

@end