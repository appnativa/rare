//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-apple-porting/com/appnativa/util/GenericCharsetHelper.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSByteArray.h"
#include "IOSCharArray.h"
#include "IOSClass.h"
#include "com/appnativa/util/ByteArray.h"
#include "com/appnativa/util/CharArray.h"
#include "com/appnativa/util/FormatException.h"
#include "com/appnativa/util/GenericCharsetHelper.h"
#include "com/appnativa/util/iCharsetHelper.h"
#include "java/io/UnsupportedEncodingException.h"
#include "java/lang/IndexOutOfBoundsException.h"
#include "java/lang/UnsupportedOperationException.h"
#include "java/nio/charset/Charset.h"
#import "java/lang/IndexOutOfBoundsException.h"
#import "RAREJREPatches.h"

@implementation RAREUTGenericCharsetHelper

- (id)initRAREUTGenericCharsetHelperWithJavaNioCharsetCharset:(JavaNioCharsetCharset *)charset {
  if (self = [super init]) {
    self->charset_ = charset;
  }
  return self;
}

- (id)initWithJavaNioCharsetCharset:(JavaNioCharsetCharset *)charset {
  return [self initRAREUTGenericCharsetHelperWithJavaNioCharsetCharset:charset];
}

- (id)initWithNSString:(NSString *)cs {
  return [self initRAREUTGenericCharsetHelperWithJavaNioCharsetCharset:[JavaNioCharsetCharset forNameWithNSString:cs]];
}

- (id<RAREUTiCharsetHelper>)copy__ {
  return [[RAREUTGenericCharsetHelper alloc] initWithJavaNioCharsetCharset:charset_];
}

- (int)getByteLengthWithCharArray:(IOSCharArray *)chars
                          withInt:(int)pos
                          withInt:(int)len {
  @throw [[JavaLangUnsupportedOperationException alloc] initWithNSString:@"Not supported yet."];
}

- (IOSByteArray *)getBytesWithNSString:(NSString *)string {
  NSData* data=[string dataUsingEncoding:self.nsEncoding];
  char* bytes=(char*)data.bytes;
  return [IOSByteArray arrayWithBytes:bytes count:data.length];
}

- (IOSByteArray *)getBytesWithCharArray:(IOSCharArray *)chars
                                withInt:(int)charPos
                                withInt:(int)charLen {
  NSString* string=[NSString stringWithCharacters:chars offset:charPos length:charLen];
  return [self getBytesWithNSString:string];
}

- (int)getBytesWithCharArray:(IOSCharArray *)chars
                     withInt:(int)charPos
                     withInt:(int)charLen
               withByteArray:(IOSByteArray *)bytes
                     withInt:(int)bytePos {
  NSString* string=[NSString stringWithCharacters:chars offset:charPos length:charLen];
  IOSByteArray* ba=[string getBytesWithEncoding:self.nsEncoding];
  NSRange range={0,ba.count};
  [ba  arraycopy:range destination:bytes offset:bytePos];
  return (int)ba.count;
}

- (int)getBytesWithCharArray:(IOSCharArray *)chars
                     withInt:(int)charPos
                     withInt:(int)charLen
         withRAREUTByteArray:(RAREUTByteArray *)bytes
                     withInt:(int)bytePos {
  NSString* string=[NSString stringWithCharacters:chars offset:charPos length:charLen];
  IOSByteArray* ba=[string getBytesWithEncoding:self.nsEncoding];
  NSRange range={0,ba.count};
  [bytes ensureCapacityWithInt:(int)(ba.count+bytePos)];
  [ba  arraycopy:range destination:bytes->A_ offset:bytePos];
  return (int)ba.count;
}

- (int)getCharLengthWithByteArray:(IOSByteArray *)bytes
                          withInt:(int)pos
                          withInt:(int)len {
  @throw [[JavaLangUnsupportedOperationException alloc] initWithNSString:@"Not supported yet."];
}

- (int)getCharsWithByteArray:(IOSByteArray *)bytes
                     withInt:(int)bytePos
                     withInt:(int)byteLen
               withCharArray:(IOSCharArray *)chars
                     withInt:(int)charPos {
  NSString* string=[NSString stringWithBytes:bytes offset:bytePos length:byteLen encoding:self.nsEncoding];
  int len=(int)string.length;
  if(len>chars.count-charPos) {
    @throw [[JavaLangIndexOutOfBoundsException alloc] initWithNSString:@"Not enough space in char array to hold data"];
  }
  [string getCharacters:[chars charRefAtIndex:charPos]];
  return len;
}

- (int)getCharsWithByteArray:(IOSByteArray *)bytes
                     withInt:(int)bytePos
                     withInt:(int)byteLen
         withRAREUTCharArray:(RAREUTCharArray *)chars
                     withInt:(int)charPos {
  NSString* string=[NSString stringWithBytes:bytes offset:bytePos length:byteLen encoding:self.nsEncoding];
  [chars ensureCapacityWithInt:(int)(string.length+charPos)];
  [chars setLengthWithInt:charPos];
  [chars appendWithNSString:string];
  return (int)string.length;
}

- (NSString *)getEncoding {
  return [((JavaNioCharsetCharset *) nil_chk(charset_)) name];
}

- (NSString *)getStringWithByteArray:(IOSByteArray *)bytes {
  return [self getStringWithByteArray:bytes withInt:0 withInt:(int) [((IOSByteArray *) nil_chk(bytes)) count]];
}

- (NSString *)getStringWithByteArray:(IOSByteArray *)bytes
                             withInt:(int)offset
                             withInt:(int)length {
  @try {
    return [NSString stringWithBytes:bytes offset:offset length:length charsetName:[((JavaNioCharsetCharset *) nil_chk(charset_)) name]];
  }
  @catch (JavaIoUnsupportedEncodingException *ex) {
    @throw [[RAREUTFormatException alloc] initWithNSString:[((JavaIoUnsupportedEncodingException *) nil_chk(ex)) description]];
  }
}

- (BOOL)isByteLenghthSupported {
  return NO;
}

- (BOOL)isCharLengthSupported {
  return NO;
}

- (int)nsEncoding {
  return (int)[charset_ getEncoding];
}

- (void)copyAllFieldsTo:(RAREUTGenericCharsetHelper *)other {
  [super copyAllFieldsTo:other];
  other->charset_ = charset_;
  other->ex_ = ex_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithNSString:", NULL, NULL, 0x1, "JavaIoUnsupportedEncodingException" },
    { "copy__", NULL, "LRAREUTiCharsetHelper", 0x1, NULL },
    { "getBytesWithNSString:", NULL, "LIOSByteArray", 0x101, NULL },
    { "getBytesWithCharArray:withInt:withInt:", NULL, "LIOSByteArray", 0x101, NULL },
    { "getBytesWithCharArray:withInt:withInt:withByteArray:withInt:", NULL, "I", 0x101, NULL },
    { "getBytesWithCharArray:withInt:withInt:withRAREUTByteArray:withInt:", NULL, "I", 0x101, NULL },
    { "getCharLengthWithByteArray:withInt:withInt:", NULL, "I", 0x1, "RAREUTFormatException" },
    { "getCharsWithByteArray:withInt:withInt:withCharArray:withInt:", NULL, "I", 0x101, "RAREUTFormatException" },
    { "getCharsWithByteArray:withInt:withInt:withRAREUTCharArray:withInt:", NULL, "I", 0x101, "RAREUTFormatException" },
    { "getEncoding", NULL, "LNSString", 0x1, NULL },
    { "getStringWithByteArray:", NULL, "LNSString", 0x1, "RAREUTFormatException" },
    { "getStringWithByteArray:withInt:withInt:", NULL, "LNSString", 0x1, "RAREUTFormatException" },
    { "isByteLenghthSupported", NULL, "Z", 0x1, NULL },
    { "isCharLengthSupported", NULL, "Z", 0x1, NULL },
    { "nsEncoding", NULL, "I", 0x100, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "charset_", NULL, 0x4, "LJavaNioCharsetCharset" },
    { "ex_", NULL, 0x0, "LJavaLangIndexOutOfBoundsException" },
  };
  static J2ObjcClassInfo _RAREUTGenericCharsetHelper = { "GenericCharsetHelper", "com.appnativa.util", NULL, 0x1, 15, methods, 2, fields, 0, NULL};
  return &_RAREUTGenericCharsetHelper;
}

@end
