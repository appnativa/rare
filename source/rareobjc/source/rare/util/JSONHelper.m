//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/util/JSONHelper.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSCharArray.h"
#include "IOSClass.h"
#include "com/appnativa/rare/util/JSONHelper.h"
#include "com/appnativa/util/CharArray.h"
#include "java/io/IOException.h"
#include "java/io/Writer.h"
#include "java/lang/Character.h"
#include "java/lang/ThreadLocal.h"

@implementation RAREJSONHelper

static IOSCharArray * RAREJSONHelper_hex_;
static JavaLangThreadLocal * RAREJSONHelper_perThreadCABuffer_;

+ (IOSCharArray *)hex {
  return RAREJSONHelper_hex_;
}

+ (void)setHex:(IOSCharArray *)hex {
  RAREJSONHelper_hex_ = hex;
}

+ (JavaLangThreadLocal *)perThreadCABuffer {
  return RAREJSONHelper_perThreadCABuffer_;
}

+ (void)setPerThreadCABuffer:(JavaLangThreadLocal *)perThreadCABuffer {
  RAREJSONHelper_perThreadCABuffer_ = perThreadCABuffer;
}

- (id)init {
  return [super init];
}

+ (JavaIoWriter *)getWiterWithJavaIoWriter:(JavaIoWriter *)w {
  return [[RAREJSONHelper_JSONStringWriter alloc] initWithJavaIoWriter:w];
}

+ (void)encodeWithNSString:(NSString *)pString
          withJavaIoWriter:(JavaIoWriter *)buffer {
  if ((pString == nil) || ([pString sequenceLength] == 0)) {
    return;
  }
  RAREUTCharArray *ca = [((RAREUTCharArray *) nil_chk([((JavaLangThreadLocal *) nil_chk(RAREJSONHelper_perThreadCABuffer_)) get])) setWithNSString:pString];
  int len = ((RAREUTCharArray *) nil_chk(ca))->_length_;
  IOSCharArray *a = ca->A_;
  unichar c;
  int i = 0;
  [((JavaIoWriter *) nil_chk(buffer)) writeWithInt:'"'];
  while (i < len) {
    c = IOSCharArray_Get(nil_chk(a), i++);
    if (c == '"') {
      [buffer writeWithNSString:@"\\\""];
    }
    else if (c == '\\') {
      [buffer writeWithNSString:@"\\\\"];
    }
    else if (c == '/') {
      [buffer writeWithNSString:@"\\/"];
    }
    else if (c == 0x0008) {
      [buffer writeWithNSString:@"\\b"];
    }
    else if (c == 0x000c) {
      [buffer writeWithNSString:@"\\f"];
    }
    else if (c == 0x000a) {
      [buffer writeWithNSString:@"\\n"];
    }
    else if (c == 0x000d) {
      [buffer writeWithNSString:@"\\r"];
    }
    else if (c == 0x0009) {
      [buffer writeWithNSString:@"\\t"];
    }
    else if ([JavaLangCharacter isISOControlWithChar:c]) {
      [RAREJSONHelper unicodeWithChar:c withJavaIoWriter:buffer];
    }
    else {
      [buffer writeWithInt:c];
    }
  }
  if ((int) [((IOSCharArray *) nil_chk(a)) count] > 4096) {
    ca->_length_ = 4096;
    [ca trimToSize];
  }
  [buffer writeWithInt:'"'];
}

+ (void)encodeWithCharArray:(IOSCharArray *)a
                    withInt:(int)pos
                    withInt:(int)len
           withJavaIoWriter:(JavaIoWriter *)buffer
                withBoolean:(BOOL)quote {
  if ((a == nil) || (len == 0)) {
    return;
  }
  unichar c;
  int i = pos;
  len += pos;
  if (quote) {
    [((JavaIoWriter *) nil_chk(buffer)) writeWithInt:'"'];
  }
  while (i < len) {
    c = IOSCharArray_Get(nil_chk(a), i++);
    if (c == '"') {
      [((JavaIoWriter *) nil_chk(buffer)) writeWithNSString:@"\\\""];
    }
    else if (c == '\\') {
      [((JavaIoWriter *) nil_chk(buffer)) writeWithNSString:@"\\\\"];
    }
    else if (c == '/') {
      [((JavaIoWriter *) nil_chk(buffer)) writeWithNSString:@"\\/"];
    }
    else if (c == 0x0008) {
      [((JavaIoWriter *) nil_chk(buffer)) writeWithNSString:@"\\b"];
    }
    else if (c == 0x000c) {
      [((JavaIoWriter *) nil_chk(buffer)) writeWithNSString:@"\\f"];
    }
    else if (c == 0x000a) {
      [((JavaIoWriter *) nil_chk(buffer)) writeWithNSString:@"\\n"];
    }
    else if (c == 0x000d) {
      [((JavaIoWriter *) nil_chk(buffer)) writeWithNSString:@"\\r"];
    }
    else if (c == 0x0009) {
      [((JavaIoWriter *) nil_chk(buffer)) writeWithNSString:@"\\t"];
    }
    else if ([JavaLangCharacter isISOControlWithChar:c]) {
      [RAREJSONHelper unicodeWithChar:c withJavaIoWriter:buffer];
    }
    else {
      [((JavaIoWriter *) nil_chk(buffer)) writeWithInt:c];
    }
  }
  if (quote) {
    [((JavaIoWriter *) nil_chk(buffer)) writeWithInt:'"'];
  }
}

+ (void)unicodeWithChar:(unichar)c
       withJavaIoWriter:(JavaIoWriter *)buffer {
  [((JavaIoWriter *) nil_chk(buffer)) writeWithNSString:@"\\u"];
  int n = c;
  for (int i = 0; i < 4; ++i) {
    int digit = (n & (int) 0xf000) >> 12;
    [buffer writeWithInt:IOSCharArray_Get(nil_chk(RAREJSONHelper_hex_), digit)];
    n <<= 4;
  }
}

+ (void)initialize {
  if (self == [RAREJSONHelper class]) {
    RAREJSONHelper_hex_ = [@"0123456789ABCDEF" toCharArray];
    RAREJSONHelper_perThreadCABuffer_ = [[RAREJSONHelper_$1 alloc] init];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "getWiterWithJavaIoWriter:", NULL, "LJavaIoWriter", 0x9, NULL },
    { "encodeWithNSString:withJavaIoWriter:", NULL, "V", 0x9, "JavaIoIOException" },
    { "encodeWithCharArray:withInt:withInt:withJavaIoWriter:withBoolean:", NULL, "V", 0x9, "JavaIoIOException" },
    { "unicodeWithChar:withJavaIoWriter:", NULL, "V", 0xa, "JavaIoIOException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "hex_", NULL, 0x8, "LIOSCharArray" },
    { "perThreadCABuffer_", NULL, 0xa, "LJavaLangThreadLocal" },
  };
  static J2ObjcClassInfo _RAREJSONHelper = { "JSONHelper", "com.appnativa.rare.util", NULL, 0x1, 5, methods, 2, fields, 0, NULL};
  return &_RAREJSONHelper;
}

@end
@implementation RAREJSONHelper_JSONStringWriter

- (id)initWithJavaIoWriter:(JavaIoWriter *)writer {
  if (self = [super init]) {
    self->writer_ = writer;
  }
  return self;
}

- (void)writeWithCharArray:(IOSCharArray *)cbuf
                   withInt:(int)off
                   withInt:(int)len {
  [RAREJSONHelper encodeWithCharArray:cbuf withInt:off withInt:len withJavaIoWriter:writer_ withBoolean:NO];
}

- (void)flush {
  [((JavaIoWriter *) nil_chk(writer_)) flush];
}

- (void)close {
  [((JavaIoWriter *) nil_chk(writer_)) close];
}

- (void)copyAllFieldsTo:(RAREJSONHelper_JSONStringWriter *)other {
  [super copyAllFieldsTo:other];
  other->writer_ = writer_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "writeWithCharArray:withInt:withInt:", NULL, "V", 0x1, "JavaIoIOException" },
    { "flush", NULL, "V", 0x1, "JavaIoIOException" },
    { "close", NULL, "V", 0x1, "JavaIoIOException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "writer_", NULL, 0x0, "LJavaIoWriter" },
  };
  static J2ObjcClassInfo _RAREJSONHelper_JSONStringWriter = { "JSONStringWriter", "com.appnativa.rare.util", "JSONHelper", 0x9, 3, methods, 1, fields, 0, NULL};
  return &_RAREJSONHelper_JSONStringWriter;
}

@end
@implementation RAREJSONHelper_$1

- (RAREUTCharArray *)initialValue {
  @synchronized(self) {
    {
      return [[RAREUTCharArray alloc] initWithInt:32];
    }
  }
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initialValue", NULL, "LRAREUTCharArray", 0x24, NULL },
  };
  static const char *superclass_type_args[] = {"LRAREUTCharArray"};
  static J2ObjcClassInfo _RAREJSONHelper_$1 = { "$1", "com.appnativa.rare.util", "JSONHelper", 0x8000, 1, methods, 0, NULL, 1, superclass_type_args};
  return &_RAREJSONHelper_$1;
}

@end