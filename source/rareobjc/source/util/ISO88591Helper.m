//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/ISO88591Helper.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSByteArray.h"
#include "IOSCharArray.h"
#include "IOSClass.h"
#include "com/appnativa/util/ByteArray.h"
#include "com/appnativa/util/CharArray.h"
#include "com/appnativa/util/FormatException.h"
#include "com/appnativa/util/ISO88591Helper.h"
#include "com/appnativa/util/iCharsetHelper.h"
#include "java/io/IOException.h"
#include "java/io/OutputStream.h"
#include "java/io/UnsupportedEncodingException.h"
#include "java/lang/ThreadLocal.h"

@implementation RAREUTISO88591Helper

static IOSByteArray * RAREUTISO88591Helper_ZERO_LENGTH_BYTES_;
static NSString * RAREUTISO88591Helper_ZERO_LENGTH_STRING_ = @"";
static JavaLangThreadLocal * RAREUTISO88591Helper_perThreadCABuffer_;
static RAREUTISO88591Helper * RAREUTISO88591Helper_instance_;

+ (IOSByteArray *)ZERO_LENGTH_BYTES {
  return RAREUTISO88591Helper_ZERO_LENGTH_BYTES_;
}

+ (void)setZERO_LENGTH_BYTES:(IOSByteArray *)ZERO_LENGTH_BYTES {
  RAREUTISO88591Helper_ZERO_LENGTH_BYTES_ = ZERO_LENGTH_BYTES;
}

+ (NSString *)ZERO_LENGTH_STRING {
  return RAREUTISO88591Helper_ZERO_LENGTH_STRING_;
}

+ (void)setZERO_LENGTH_STRING:(NSString *)ZERO_LENGTH_STRING {
  RAREUTISO88591Helper_ZERO_LENGTH_STRING_ = ZERO_LENGTH_STRING;
}

+ (JavaLangThreadLocal *)perThreadCABuffer {
  return RAREUTISO88591Helper_perThreadCABuffer_;
}

+ (void)setPerThreadCABuffer:(JavaLangThreadLocal *)perThreadCABuffer {
  RAREUTISO88591Helper_perThreadCABuffer_ = perThreadCABuffer;
}

+ (RAREUTISO88591Helper *)instance {
  return RAREUTISO88591Helper_instance_;
}

+ (void)setInstance:(RAREUTISO88591Helper *)instance {
  RAREUTISO88591Helper_instance_ = instance;
}

- (id)init {
  return [super init];
}

- (id<RAREUTiCharsetHelper>)copy__ {
  return self;
}

- (int)getByteLengthWithCharArray:(IOSCharArray *)chars
                          withInt:(int)pos
                          withInt:(int)len {
  return len;
}

- (IOSByteArray *)getBytesWithNSString:(NSString *)string {
  if (string == nil) {
    return nil;
  }
  if ([((NSString *) nil_chk(string)) sequenceLength] == 0) {
    return RAREUTISO88591Helper_ZERO_LENGTH_BYTES_;
  }
  @try {
    return [string getBytesWithCharsetName:@"ISO-8859-1"];
  }
  @catch (JavaIoUnsupportedEncodingException *ex) {
    return [string getBytes];
  }
}

- (void)getBytesWithNSString:(NSString *)string
      withJavaIoOutputStream:(JavaIoOutputStream *)os {
  if (string == nil) {
    return;
  }
  RAREUTCharArray *ca = (RAREUTCharArray *) check_class_cast([((JavaLangThreadLocal *) nil_chk(RAREUTISO88591Helper_perThreadCABuffer_)) get], [RAREUTCharArray class]);
  (void) [((RAREUTCharArray *) nil_chk(ca)) setWithNSString:string];
  int charLen = [ca sequenceLength];
  if (charLen == 0) {
    return;
  }
  int i = 0;
  IOSCharArray *chars = ca->A_;
  while (i < charLen) {
    [((JavaIoOutputStream *) nil_chk(os)) writeWithInt:(char) IOSCharArray_Get(nil_chk(chars), i++)];
  }
}

- (int)getBytesWithCharArray:(IOSCharArray *)chars
               withByteArray:(IOSByteArray *)bytes
                     withInt:(int)bytePos {
  if (chars == nil) {
    return 0;
  }
  return [self getBytesWithCharArray:chars withInt:0 withInt:(int) [((IOSCharArray *) nil_chk(chars)) count] withByteArray:bytes withInt:bytePos];
}

- (IOSByteArray *)getBytesWithCharArray:(IOSCharArray *)chars
                                withInt:(int)charPos
                                withInt:(int)charLen {
  if (chars == nil) {
    return nil;
  }
  if (charLen == 0) {
    return RAREUTISO88591Helper_ZERO_LENGTH_BYTES_;
  }
  IOSByteArray *bytes = [IOSByteArray arrayWithLength:charLen];
  int i = 0;
  while (i < charLen) {
    (*IOSByteArray_GetRef(bytes, i++)) = (char) IOSCharArray_Get(nil_chk(chars), charPos++);
  }
  return bytes;
}

- (void)getBytesWithCharArray:(IOSCharArray *)chars
                      withInt:(int)charPos
                      withInt:(int)charLen
       withJavaIoOutputStream:(JavaIoOutputStream *)os {
  if (chars == nil) {
    return;
  }
  if (charLen == 0) {
    return;
  }
  int i = 0;
  while (i < charLen) {
    [((JavaIoOutputStream *) nil_chk(os)) writeWithInt:(char) IOSCharArray_Get(nil_chk(chars), charPos++)];
  }
}

- (int)getBytesWithCharArray:(IOSCharArray *)chars
                     withInt:(int)charPos
                     withInt:(int)charLen
               withByteArray:(IOSByteArray *)bytes
                     withInt:(int)bytePos {
  if ((chars == nil) || (charLen == 0)) {
    return 0;
  }
  int olen = charLen;
  charLen += charPos;
  while (charPos < charLen) {
    (*IOSByteArray_GetRef(nil_chk(bytes), bytePos++)) = (char) IOSCharArray_Get(nil_chk(chars), charPos++);
  }
  return olen;
}

- (int)getBytesWithCharArray:(IOSCharArray *)chars
                     withInt:(int)charPos
                     withInt:(int)charLen
         withRAREUTByteArray:(RAREUTByteArray *)bytes
                     withInt:(int)bytePos {
  if ((chars == nil) || (charLen == 0)) {
    return 0;
  }
  int olen = charLen;
  charLen += charPos;
  [((RAREUTByteArray *) nil_chk(bytes)) ensureCapacityWithInt:bytePos + olen];
  IOSByteArray *A = bytes->A_;
  while (charPos < charLen) {
    (*IOSByteArray_GetRef(nil_chk(A), bytePos++)) = (char) IOSCharArray_Get(nil_chk(chars), charPos++);
  }
  return olen;
}

- (int)getCharLengthWithByteArray:(IOSByteArray *)bytes
                          withInt:(int)pos
                          withInt:(int)len {
  return len;
}

- (int)getCharsWithByteArray:(IOSByteArray *)bytes
               withCharArray:(IOSCharArray *)chars
                     withInt:(int)charPos {
  if (bytes == nil) {
    return 0;
  }
  int len = (int) [((IOSByteArray *) nil_chk(bytes)) count];
  int i = 0;
  while (i < len) {
    (*IOSCharArray_GetRef(nil_chk(chars), charPos++)) = (unichar) (IOSByteArray_Get(bytes, i++) & (int) 0xff);
  }
  return len;
}

- (int)getCharsWithByteArray:(IOSByteArray *)bytes
                     withInt:(int)bytePos
                     withInt:(int)byteLen
               withCharArray:(IOSCharArray *)chars
                     withInt:(int)charPos {
  if ((bytes == nil) || (byteLen == 0)) {
    return 0;
  }
  int olen = byteLen;
  byteLen += bytePos;
  while (bytePos < byteLen) {
    (*IOSCharArray_GetRef(nil_chk(chars), charPos++)) = (unichar) (IOSByteArray_Get(nil_chk(bytes), bytePos++) & (int) 0xff);
  }
  return olen;
}

- (int)getCharsWithByteArray:(IOSByteArray *)bytes
                     withInt:(int)bytePos
                     withInt:(int)byteLen
         withRAREUTCharArray:(RAREUTCharArray *)chars
                     withInt:(int)charPos {
  if ((bytes == nil) || (byteLen == 0)) {
    return 0;
  }
  int olen = byteLen;
  byteLen += bytePos;
  [((RAREUTCharArray *) nil_chk(chars)) ensureLengthWithInt:charPos + olen];
  IOSCharArray *A = chars->A_;
  while (bytePos < byteLen) {
    (*IOSCharArray_GetRef(nil_chk(A), charPos++)) = (unichar) (IOSByteArray_Get(nil_chk(bytes), bytePos++) & (int) 0xff);
  }
  return olen;
}

- (NSString *)getEncoding {
  return @"ISO-8859-1";
}

+ (RAREUTISO88591Helper *)getInstance {
  if (RAREUTISO88591Helper_instance_ == nil) {
    RAREUTISO88591Helper_instance_ = [[RAREUTISO88591Helper alloc] init];
  }
  return RAREUTISO88591Helper_instance_;
}

- (NSString *)getStringWithByteArray:(IOSByteArray *)bytes {
  if (bytes == nil) {
    return nil;
  }
  return [self getStringWithByteArray:bytes withInt:0 withInt:(int) [((IOSByteArray *) nil_chk(bytes)) count]];
}

- (NSString *)getStringWithByteArray:(IOSByteArray *)bytes
                             withInt:(int)offset
                             withInt:(int)length {
  if (length == 0) {
    return (bytes == nil) ? nil : RAREUTISO88591Helper_ZERO_LENGTH_STRING_;
  }
  RAREUTCharArray *ca = (RAREUTCharArray *) check_class_cast([((JavaLangThreadLocal *) nil_chk(RAREUTISO88591Helper_perThreadCABuffer_)) get], [RAREUTCharArray class]);
  ((RAREUTCharArray *) nil_chk(ca))->_length_ = [self getCharsWithByteArray:bytes withInt:offset withInt:length withRAREUTCharArray:ca withInt:0];
  return [ca description];
}

- (BOOL)isByteLenghthSupported {
  return YES;
}

- (BOOL)isCharLengthSupported {
  return YES;
}

+ (void)initialize {
  if (self == [RAREUTISO88591Helper class]) {
    RAREUTISO88591Helper_ZERO_LENGTH_BYTES_ = [IOSByteArray arrayWithBytes:(char[]){  } count:0];
    RAREUTISO88591Helper_perThreadCABuffer_ = [[RAREUTISO88591Helper_$1 alloc] init];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "copy__", NULL, "LRAREUTiCharsetHelper", 0x1, NULL },
    { "getByteLengthWithCharArray:withInt:withInt:", NULL, "I", 0x11, NULL },
    { "getBytesWithNSString:", NULL, "LIOSByteArray", 0x1, NULL },
    { "getBytesWithNSString:withJavaIoOutputStream:", NULL, "V", 0x1, "JavaIoIOException" },
    { "getBytesWithCharArray:withInt:withInt:", NULL, "LIOSByteArray", 0x1, NULL },
    { "getBytesWithCharArray:withInt:withInt:withJavaIoOutputStream:", NULL, "V", 0x1, "JavaIoIOException" },
    { "getCharsWithByteArray:withCharArray:withInt:", NULL, "I", 0x1, "RAREUTFormatException" },
    { "getCharsWithByteArray:withInt:withInt:withCharArray:withInt:", NULL, "I", 0x1, "RAREUTFormatException" },
    { "getCharsWithByteArray:withInt:withInt:withRAREUTCharArray:withInt:", NULL, "I", 0x1, "RAREUTFormatException" },
    { "getEncoding", NULL, "LNSString", 0x1, NULL },
    { "getInstance", NULL, "LRAREUTISO88591Helper", 0x9, NULL },
    { "getStringWithByteArray:", NULL, "LNSString", 0x1, "RAREUTFormatException" },
    { "getStringWithByteArray:withInt:withInt:", NULL, "LNSString", 0x1, "RAREUTFormatException" },
    { "isByteLenghthSupported", NULL, "Z", 0x11, NULL },
    { "isCharLengthSupported", NULL, "Z", 0x11, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "ZERO_LENGTH_BYTES_", NULL, 0xa, "LIOSByteArray" },
    { "ZERO_LENGTH_STRING_", NULL, 0xa, "LNSString" },
    { "perThreadCABuffer_", NULL, 0xa, "LJavaLangThreadLocal" },
    { "instance_", NULL, 0xa, "LRAREUTISO88591Helper" },
  };
  static J2ObjcClassInfo _RAREUTISO88591Helper = { "ISO88591Helper", "com.appnativa.util", NULL, 0x11, 16, methods, 4, fields, 0, NULL};
  return &_RAREUTISO88591Helper;
}

@end
@implementation RAREUTISO88591Helper_$1

- (id)initialValue {
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
    { "initialValue", NULL, "LNSObject", 0x24, NULL },
  };
  static J2ObjcClassInfo _RAREUTISO88591Helper_$1 = { "$1", "com.appnativa.util", "ISO88591Helper", 0x8000, 1, methods, 0, NULL, 0, NULL};
  return &_RAREUTISO88591Helper_$1;
}

@end