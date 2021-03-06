//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/Base64.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSByteArray.h"
#include "IOSClass.h"
#include "com/appnativa/util/Base64.h"
#include "com/appnativa/util/UTF8Helper.h"
#include "java/io/BufferedInputStream.h"
#include "java/io/File.h"
#include "java/io/FileInputStream.h"
#include "java/io/FileOutputStream.h"
#include "java/io/FilterInputStream.h"
#include "java/io/FilterOutputStream.h"
#include "java/io/IOException.h"
#include "java/io/InputStream.h"
#include "java/io/OutputStream.h"
#include "java/io/PrintStream.h"
#include "java/io/UnsupportedEncodingException.h"
#include "java/lang/Exception.h"
#include "java/lang/Integer.h"
#include "java/lang/System.h"

@implementation RAREUTBase64

static IOSByteArray * RAREUTBase64_DECODABET_;
static NSString * RAREUTBase64_PREFERRED_ENCODING_ = @"UTF-8";
static IOSByteArray * RAREUTBase64__NATIVE_ALPHABET_;
static IOSByteArray * RAREUTBase64_ALPHABET_;

+ (int)DECODE {
  return RAREUTBase64_DECODE;
}

+ (int)DONT_BREAK_LINES {
  return RAREUTBase64_DONT_BREAK_LINES;
}

+ (int)ENCODE {
  return RAREUTBase64_ENCODE;
}

+ (int)GZIP {
  return RAREUTBase64_GZIP;
}

+ (int)NO_OPTIONS {
  return RAREUTBase64_NO_OPTIONS;
}

+ (IOSByteArray *)DECODABET {
  return RAREUTBase64_DECODABET_;
}

+ (NSString *)PREFERRED_ENCODING {
  return RAREUTBase64_PREFERRED_ENCODING_;
}

+ (IOSByteArray *)_NATIVE_ALPHABET {
  return RAREUTBase64__NATIVE_ALPHABET_;
}

+ (IOSByteArray *)ALPHABET {
  return RAREUTBase64_ALPHABET_;
}

- (id)init {
  return [super init];
}

+ (IOSByteArray *)decodeWithByteArray:(IOSByteArray *)source {
  return [RAREUTBase64 decodeWithByteArray:source withInt:0 withInt:(int) [((IOSByteArray *) nil_chk(source)) count]];
}

+ (IOSByteArray *)decodeWithNSString:(NSString *)s {
  IOSByteArray *bytes;
  @try {
    bytes = [((NSString *) nil_chk(s)) getBytesWithCharsetName:RAREUTBase64_PREFERRED_ENCODING_];
  }
  @catch (JavaIoUnsupportedEncodingException *uee) {
    bytes = [((NSString *) nil_chk(s)) getBytes];
  }
  bytes = [RAREUTBase64 decodeWithByteArray:bytes withInt:0 withInt:(int) [((IOSByteArray *) nil_chk(bytes)) count]];
  return bytes;
}

+ (IOSByteArray *)decodeWithByteArray:(IOSByteArray *)source
                              withInt:(int)off
                              withInt:(int)len {
  int len34 = len * 3 / 4;
  IOSByteArray *outBuff = [IOSByteArray arrayWithLength:len34];
  int outBuffPosn = 0;
  IOSByteArray *b4 = [IOSByteArray arrayWithLength:4];
  int b4Posn = 0;
  int i = 0;
  char sbiCrop = 0;
  char sbiDecode = 0;
  for (i = off; i < off + len; i++) {
    sbiCrop = (char) (IOSByteArray_Get(nil_chk(source), i) & (int) 0x7f);
    sbiDecode = IOSByteArray_Get(nil_chk(RAREUTBase64_DECODABET_), sbiCrop);
    if (sbiDecode >= RAREUTBase64_WHITE_SPACE_ENC) {
      if (sbiDecode >= RAREUTBase64_EQUALS_SIGN_ENC) {
        (*IOSByteArray_GetRef(b4, b4Posn++)) = sbiCrop;
        if (b4Posn > 3) {
          outBuffPosn += [RAREUTBase64 decode4to3WithByteArray:b4 withInt:0 withByteArray:outBuff withInt:outBuffPosn];
          b4Posn = 0;
          if (sbiCrop == RAREUTBase64_EQUALS_SIGN) {
            break;
          }
        }
      }
    }
    else {
      [((JavaIoPrintStream *) nil_chk([JavaLangSystem err])) printlnWithNSString:[NSString stringWithFormat:@"Bad Base64 input character at %d: %d(decimal)", i, IOSByteArray_Get(source, i)]];
      return nil;
    }
  }
  IOSByteArray *out = [IOSByteArray arrayWithLength:outBuffPosn];
  [JavaLangSystem arraycopyWithId:outBuff withInt:0 withId:out withInt:0 withInt:outBuffPosn];
  return out;
}

+ (IOSByteArray *)decodeFromFileWithNSString:(NSString *)filename {
  IOSByteArray *decodedData = nil;
  RAREUTBase64_InputStream *bis = nil;
  @try {
    JavaIoFile *file = [[JavaIoFile alloc] initWithNSString:filename];
    IOSByteArray *buffer = nil;
    int length = 0;
    int numBytes = 0;
    if ([file length] > JavaLangInteger_MAX_VALUE) {
      [((JavaIoPrintStream *) nil_chk([JavaLangSystem err])) printlnWithNSString:[NSString stringWithFormat:@"File is too big for this convenience method (%lld bytes).", [file length]]];
      return nil;
    }
    buffer = [IOSByteArray arrayWithLength:(int) [file length]];
    bis = [[RAREUTBase64_InputStream alloc] initWithJavaIoInputStream:[[JavaIoBufferedInputStream alloc] initWithJavaIoInputStream:[[JavaIoFileInputStream alloc] initWithJavaIoFile:file]] withInt:RAREUTBase64_DECODE];
    while ((numBytes = [bis readWithByteArray:buffer withInt:length withInt:4096]) >= 0) {
      length += numBytes;
    }
    decodedData = [IOSByteArray arrayWithLength:length];
    [JavaLangSystem arraycopyWithId:buffer withInt:0 withId:decodedData withInt:0 withInt:length];
  }
  @catch (JavaIoIOException *e) {
    [((JavaIoPrintStream *) nil_chk([JavaLangSystem err])) printlnWithNSString:[NSString stringWithFormat:@"Error decoding from file %@", filename]];
  }
  @finally {
    @try {
      [((RAREUTBase64_InputStream *) nil_chk(bis)) close];
    }
    @catch (JavaLangException *e) {
    }
  }
  return decodedData;
}

+ (BOOL)decodeToFileWithNSString:(NSString *)dataToDecode
                    withNSString:(NSString *)filename {
  BOOL success = NO;
  RAREUTBase64_OutputStream *bos = nil;
  @try {
    bos = [[RAREUTBase64_OutputStream alloc] initWithJavaIoOutputStream:[[JavaIoFileOutputStream alloc] initWithNSString:filename] withInt:RAREUTBase64_DECODE];
    [bos writeWithByteArray:[((NSString *) nil_chk(dataToDecode)) getBytesWithCharsetName:RAREUTBase64_PREFERRED_ENCODING_]];
    success = YES;
  }
  @catch (JavaIoIOException *e) {
    success = NO;
  }
  @finally {
    @try {
      [((RAREUTBase64_OutputStream *) nil_chk(bos)) close];
    }
    @catch (JavaLangException *e) {
    }
  }
  return success;
}

+ (NSString *)encodeWithByteArray:(IOSByteArray *)source {
  return [RAREUTBase64 encodeWithByteArray:source withInt:0 withInt:(int) [((IOSByteArray *) nil_chk(source)) count] withNSString:@"ISO-8859-1"];
}

+ (NSString *)encodeUTF8WithNSString:(NSString *)source {
  IOSByteArray *a = [((RAREUTUTF8Helper *) nil_chk([RAREUTUTF8Helper getInstance])) getBytesWithNSString:source];
  return [RAREUTBase64 encodeWithByteArray:a withInt:0 withInt:(int) [((IOSByteArray *) nil_chk(a)) count] withNSString:@"ISO-8859-1"];
}

+ (NSString *)decodeUTF8WithNSString:(NSString *)source {
  IOSByteArray *a = [RAREUTBase64 decodeWithNSString:source];
  return [((RAREUTUTF8Helper *) nil_chk([RAREUTUTF8Helper getInstance])) getStringWithByteArray:a];
}

+ (NSString *)encodeWithByteArray:(IOSByteArray *)source
                     withNSString:(NSString *)encoding {
  return [RAREUTBase64 encodeWithByteArray:source withInt:0 withInt:(int) [((IOSByteArray *) nil_chk(source)) count] withNSString:encoding];
}

+ (NSString *)encodeWithByteArray:(IOSByteArray *)source
                          withInt:(int)off
                          withInt:(int)len
                     withNSString:(NSString *)encoding {
  BOOL breakLines = YES;
  int len43 = len * 4 / 3;
  IOSByteArray *outBuff = [IOSByteArray arrayWithLength:(len43) + ((len % 3) > 0 ? 4 : 0) + (breakLines ? (len43 / RAREUTBase64_MAX_LINE_LENGTH) : 0)];
  int d = 0;
  int e = 0;
  int len2 = len - 2;
  int lineLength = 0;
  for (; d < len2; d += 3, e += 4) {
    (void) [RAREUTBase64 encode3to4WithByteArray:source withInt:d + off withInt:3 withByteArray:outBuff withInt:e];
    lineLength += 4;
    if (breakLines && (lineLength == RAREUTBase64_MAX_LINE_LENGTH)) {
      (*IOSByteArray_GetRef(outBuff, e + 4)) = RAREUTBase64_NEW_LINE;
      e++;
      lineLength = 0;
    }
  }
  if (d < len) {
    (void) [RAREUTBase64 encode3to4WithByteArray:source withInt:d + off withInt:len - d withByteArray:outBuff withInt:e];
    e += 4;
  }
  @try {
    return [NSString stringWithBytes:outBuff offset:0 length:e charsetName:encoding];
  }
  @catch (JavaIoUnsupportedEncodingException *uue) {
    return [NSString stringWithBytes:outBuff offset:0 length:e];
  }
}

+ (NSString *)encodeBytesWithByteArray:(IOSByteArray *)source {
  return [RAREUTBase64 encodeBytesWithByteArray:source withInt:0 withInt:(int) [((IOSByteArray *) nil_chk(source)) count] withInt:RAREUTBase64_NO_OPTIONS];
}

+ (NSString *)encodeBytesWithByteArray:(IOSByteArray *)source
                               withInt:(int)options {
  return [RAREUTBase64 encodeBytesWithByteArray:source withInt:0 withInt:(int) [((IOSByteArray *) nil_chk(source)) count] withInt:options];
}

+ (NSString *)encodeBytesWithByteArray:(IOSByteArray *)source
                               withInt:(int)off
                               withInt:(int)len {
  return [RAREUTBase64 encodeBytesWithByteArray:source withInt:off withInt:len withInt:RAREUTBase64_NO_OPTIONS];
}

+ (NSString *)encodeBytesWithByteArray:(IOSByteArray *)source
                               withInt:(int)off
                               withInt:(int)len
                               withInt:(int)options {
  int dontBreakLines = (options & RAREUTBase64_DONT_BREAK_LINES);
  BOOL breakLines = dontBreakLines == 0;
  int len43 = len * 4 / 3;
  IOSByteArray *outBuff = [IOSByteArray arrayWithLength:(len43) + ((len % 3) > 0 ? 4 : 0) + (breakLines ? (len43 / RAREUTBase64_MAX_LINE_LENGTH) : 0)];
  int d = 0;
  int e = 0;
  int len2 = len - 2;
  int lineLength = 0;
  for (; d < len2; d += 3, e += 4) {
    (void) [RAREUTBase64 encode3to4WithByteArray:source withInt:d + off withInt:3 withByteArray:outBuff withInt:e];
    lineLength += 4;
    if (breakLines && (lineLength == RAREUTBase64_MAX_LINE_LENGTH)) {
      (*IOSByteArray_GetRef(outBuff, e + 4)) = RAREUTBase64_NEW_LINE;
      e++;
      lineLength = 0;
    }
  }
  if (d < len) {
    (void) [RAREUTBase64 encode3to4WithByteArray:source withInt:d + off withInt:len - d withByteArray:outBuff withInt:e];
    e += 4;
  }
  @try {
    return [NSString stringWithBytes:outBuff offset:0 length:e charsetName:RAREUTBase64_PREFERRED_ENCODING_];
  }
  @catch (JavaIoUnsupportedEncodingException *uue) {
    return [NSString stringWithBytes:outBuff offset:0 length:e];
  }
}

+ (NSString *)encodeFromFileWithNSString:(NSString *)filename {
  NSString *encodedData = nil;
  RAREUTBase64_InputStream *bis = nil;
  @try {
    JavaIoFile *file = [[JavaIoFile alloc] initWithNSString:filename];
    IOSByteArray *buffer = [IOSByteArray arrayWithLength:(int) ([file length] * 1.4)];
    int length = 0;
    int numBytes = 0;
    bis = [[RAREUTBase64_InputStream alloc] initWithJavaIoInputStream:[[JavaIoBufferedInputStream alloc] initWithJavaIoInputStream:[[JavaIoFileInputStream alloc] initWithJavaIoFile:file]] withInt:RAREUTBase64_ENCODE];
    while ((numBytes = [bis readWithByteArray:buffer withInt:length withInt:4096]) >= 0) {
      length += numBytes;
    }
    encodedData = [NSString stringWithBytes:buffer offset:0 length:length charsetName:RAREUTBase64_PREFERRED_ENCODING_];
  }
  @catch (JavaIoIOException *e) {
    [((JavaIoPrintStream *) nil_chk([JavaLangSystem err])) printlnWithNSString:[NSString stringWithFormat:@"Error encoding from file %@", filename]];
  }
  @finally {
    @try {
      [((RAREUTBase64_InputStream *) nil_chk(bis)) close];
    }
    @catch (JavaLangException *e) {
    }
  }
  return encodedData;
}

+ (BOOL)encodeToFileWithByteArray:(IOSByteArray *)dataToEncode
                     withNSString:(NSString *)filename {
  BOOL success = NO;
  RAREUTBase64_OutputStream *bos = nil;
  @try {
    bos = [[RAREUTBase64_OutputStream alloc] initWithJavaIoOutputStream:[[JavaIoFileOutputStream alloc] initWithNSString:filename] withInt:RAREUTBase64_ENCODE];
    [bos writeWithByteArray:dataToEncode];
    success = YES;
  }
  @catch (JavaIoIOException *e) {
    success = NO;
  }
  @finally {
    @try {
      [((RAREUTBase64_OutputStream *) nil_chk(bos)) close];
    }
    @catch (JavaLangException *e) {
    }
  }
  return success;
}

+ (JavaIoInputStream *)decodingStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg {
  return [[RAREUTBase64_InputStream alloc] initWithJavaIoInputStream:inArg];
}

+ (int)decode4to3WithByteArray:(IOSByteArray *)source
                       withInt:(int)srcOffset
                 withByteArray:(IOSByteArray *)destination
                       withInt:(int)destOffset {
  if (IOSByteArray_Get(nil_chk(source), srcOffset + 2) == RAREUTBase64_EQUALS_SIGN) {
    int outBuff = ((IOSByteArray_Get(nil_chk(RAREUTBase64_DECODABET_), IOSByteArray_Get(source, srcOffset)) & (int) 0xFF) << 18) | ((IOSByteArray_Get(RAREUTBase64_DECODABET_, IOSByteArray_Get(source, srcOffset + 1)) & (int) 0xFF) << 12);
    (*IOSByteArray_GetRef(nil_chk(destination), destOffset)) = (char) ((int) (((unsigned int) outBuff) >> 16));
    return 1;
  }
  else if (IOSByteArray_Get(source, srcOffset + 3) == RAREUTBase64_EQUALS_SIGN) {
    int outBuff = ((IOSByteArray_Get(nil_chk(RAREUTBase64_DECODABET_), IOSByteArray_Get(source, srcOffset)) & (int) 0xFF) << 18) | ((IOSByteArray_Get(RAREUTBase64_DECODABET_, IOSByteArray_Get(source, srcOffset + 1)) & (int) 0xFF) << 12) | ((IOSByteArray_Get(RAREUTBase64_DECODABET_, IOSByteArray_Get(source, srcOffset + 2)) & (int) 0xFF) << 6);
    (*IOSByteArray_GetRef(nil_chk(destination), destOffset)) = (char) ((int) (((unsigned int) outBuff) >> 16));
    (*IOSByteArray_GetRef(destination, destOffset + 1)) = (char) ((int) (((unsigned int) outBuff) >> 8));
    return 2;
  }
  else {
    @try {
      int outBuff = ((IOSByteArray_Get(nil_chk(RAREUTBase64_DECODABET_), IOSByteArray_Get(source, srcOffset)) & (int) 0xFF) << 18) | ((IOSByteArray_Get(RAREUTBase64_DECODABET_, IOSByteArray_Get(source, srcOffset + 1)) & (int) 0xFF) << 12) | ((IOSByteArray_Get(RAREUTBase64_DECODABET_, IOSByteArray_Get(source, srcOffset + 2)) & (int) 0xFF) << 6) | ((IOSByteArray_Get(RAREUTBase64_DECODABET_, IOSByteArray_Get(source, srcOffset + 3)) & (int) 0xFF));
      (*IOSByteArray_GetRef(nil_chk(destination), destOffset)) = (char) (outBuff >> 16);
      (*IOSByteArray_GetRef(destination, destOffset + 1)) = (char) (outBuff >> 8);
      (*IOSByteArray_GetRef(destination, destOffset + 2)) = (char) (outBuff);
      return 3;
    }
    @catch (JavaLangException *e) {
      [((JavaIoPrintStream *) nil_chk([JavaLangSystem out])) printlnWithNSString:[NSString stringWithFormat:@"%d: %d", IOSByteArray_Get(source, srcOffset), (IOSByteArray_Get(nil_chk(RAREUTBase64_DECODABET_), IOSByteArray_Get(source, srcOffset)))]];
      [[JavaLangSystem out] printlnWithNSString:[NSString stringWithFormat:@"%d: %d", IOSByteArray_Get(source, srcOffset + 1), (IOSByteArray_Get(RAREUTBase64_DECODABET_, IOSByteArray_Get(source, srcOffset + 1)))]];
      [[JavaLangSystem out] printlnWithNSString:[NSString stringWithFormat:@"%d: %d", IOSByteArray_Get(source, srcOffset + 2), (IOSByteArray_Get(RAREUTBase64_DECODABET_, IOSByteArray_Get(source, srcOffset + 2)))]];
      [[JavaLangSystem out] printlnWithNSString:[NSString stringWithFormat:@"%d: %d", IOSByteArray_Get(source, srcOffset + 3), (IOSByteArray_Get(RAREUTBase64_DECODABET_, IOSByteArray_Get(source, srcOffset + 3)))]];
      return -1;
    }
  }
}

+ (IOSByteArray *)encode3to4WithByteArray:(IOSByteArray *)b4
                            withByteArray:(IOSByteArray *)threeBytes
                                  withInt:(int)numSigBytes {
  (void) [RAREUTBase64 encode3to4WithByteArray:threeBytes withInt:0 withInt:numSigBytes withByteArray:b4 withInt:0];
  return b4;
}

+ (IOSByteArray *)encode3to4WithByteArray:(IOSByteArray *)source
                                  withInt:(int)srcOffset
                                  withInt:(int)numSigBytes
                            withByteArray:(IOSByteArray *)destination
                                  withInt:(int)destOffset {
  int inBuff = ((numSigBytes > 0) ? ((int) (((unsigned int) (IOSByteArray_Get(nil_chk(source), srcOffset) << 24)) >> 8)) : 0) | ((numSigBytes > 1) ? ((int) (((unsigned int) (IOSByteArray_Get(nil_chk(source), srcOffset + 1) << 24)) >> 16)) : 0) | ((numSigBytes > 2) ? ((int) (((unsigned int) (IOSByteArray_Get(nil_chk(source), srcOffset + 2) << 24)) >> 24)) : 0);
  switch (numSigBytes) {
    case 3:
    (*IOSByteArray_GetRef(nil_chk(destination), destOffset)) = IOSByteArray_Get(nil_chk(RAREUTBase64_ALPHABET_), ((int) (((unsigned int) inBuff) >> 18)));
    (*IOSByteArray_GetRef(destination, destOffset + 1)) = IOSByteArray_Get(RAREUTBase64_ALPHABET_, ((int) (((unsigned int) inBuff) >> 12)) & (int) 0x3f);
    (*IOSByteArray_GetRef(destination, destOffset + 2)) = IOSByteArray_Get(RAREUTBase64_ALPHABET_, ((int) (((unsigned int) inBuff) >> 6)) & (int) 0x3f);
    (*IOSByteArray_GetRef(destination, destOffset + 3)) = IOSByteArray_Get(RAREUTBase64_ALPHABET_, (inBuff) & (int) 0x3f);
    return destination;
    case 2:
    (*IOSByteArray_GetRef(nil_chk(destination), destOffset)) = IOSByteArray_Get(nil_chk(RAREUTBase64_ALPHABET_), ((int) (((unsigned int) inBuff) >> 18)));
    (*IOSByteArray_GetRef(destination, destOffset + 1)) = IOSByteArray_Get(RAREUTBase64_ALPHABET_, ((int) (((unsigned int) inBuff) >> 12)) & (int) 0x3f);
    (*IOSByteArray_GetRef(destination, destOffset + 2)) = IOSByteArray_Get(RAREUTBase64_ALPHABET_, ((int) (((unsigned int) inBuff) >> 6)) & (int) 0x3f);
    (*IOSByteArray_GetRef(destination, destOffset + 3)) = RAREUTBase64_EQUALS_SIGN;
    return destination;
    case 1:
    (*IOSByteArray_GetRef(nil_chk(destination), destOffset)) = IOSByteArray_Get(nil_chk(RAREUTBase64_ALPHABET_), ((int) (((unsigned int) inBuff) >> 18)));
    (*IOSByteArray_GetRef(destination, destOffset + 1)) = IOSByteArray_Get(RAREUTBase64_ALPHABET_, ((int) (((unsigned int) inBuff) >> 12)) & (int) 0x3f);
    (*IOSByteArray_GetRef(destination, destOffset + 2)) = RAREUTBase64_EQUALS_SIGN;
    (*IOSByteArray_GetRef(destination, destOffset + 3)) = RAREUTBase64_EQUALS_SIGN;
    return destination;
    default:
    return destination;
  }
}

+ (void)initialize {
  if (self == [RAREUTBase64 class]) {
    RAREUTBase64_DECODABET_ = [IOSByteArray arrayWithBytes:(char[]){ -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9 } count:127];
    RAREUTBase64__NATIVE_ALPHABET_ = [IOSByteArray arrayWithBytes:(char[]){ (char) 'A', (char) 'B', (char) 'C', (char) 'D', (char) 'E', (char) 'F', (char) 'G', (char) 'H', (char) 'I', (char) 'J', (char) 'K', (char) 'L', (char) 'M', (char) 'N', (char) 'O', (char) 'P', (char) 'Q', (char) 'R', (char) 'S', (char) 'T', (char) 'U', (char) 'V', (char) 'W', (char) 'X', (char) 'Y', (char) 'Z', (char) 'a', (char) 'b', (char) 'c', (char) 'd', (char) 'e', (char) 'f', (char) 'g', (char) 'h', (char) 'i', (char) 'j', (char) 'k', (char) 'l', (char) 'm', (char) 'n', (char) 'o', (char) 'p', (char) 'q', (char) 'r', (char) 's', (char) 't', (char) 'u', (char) 'v', (char) 'w', (char) 'x', (char) 'y', (char) 'z', (char) '0', (char) '1', (char) '2', (char) '3', (char) '4', (char) '5', (char) '6', (char) '7', (char) '8', (char) '9', (char) '+', (char) '/' } count:64];
    {
      IOSByteArray *__bytes;
      @try {
        __bytes = [@"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/" getBytesWithCharsetName:RAREUTBase64_PREFERRED_ENCODING_];
      }
      @catch (JavaIoUnsupportedEncodingException *use) {
        __bytes = RAREUTBase64__NATIVE_ALPHABET_;
      }
      RAREUTBase64_ALPHABET_ = __bytes;
    }
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "decodeWithByteArray:", NULL, "LIOSByteArray", 0x9, NULL },
    { "decodeWithNSString:", NULL, "LIOSByteArray", 0x9, NULL },
    { "decodeWithByteArray:withInt:withInt:", NULL, "LIOSByteArray", 0x9, NULL },
    { "decodeFromFileWithNSString:", NULL, "LIOSByteArray", 0x9, NULL },
    { "decodeToFileWithNSString:withNSString:", NULL, "Z", 0x9, NULL },
    { "encodeWithByteArray:", NULL, "LNSString", 0x9, NULL },
    { "encodeUTF8WithNSString:", NULL, "LNSString", 0x9, NULL },
    { "decodeUTF8WithNSString:", NULL, "LNSString", 0x9, NULL },
    { "encodeWithByteArray:withNSString:", NULL, "LNSString", 0x9, NULL },
    { "encodeWithByteArray:withInt:withInt:withNSString:", NULL, "LNSString", 0x9, NULL },
    { "encodeBytesWithByteArray:", NULL, "LNSString", 0x9, NULL },
    { "encodeBytesWithByteArray:withInt:", NULL, "LNSString", 0x9, NULL },
    { "encodeBytesWithByteArray:withInt:withInt:", NULL, "LNSString", 0x9, NULL },
    { "encodeBytesWithByteArray:withInt:withInt:withInt:", NULL, "LNSString", 0x9, NULL },
    { "encodeFromFileWithNSString:", NULL, "LNSString", 0x9, NULL },
    { "encodeToFileWithByteArray:withNSString:", NULL, "Z", 0x9, NULL },
    { "decodingStreamWithJavaIoInputStream:", NULL, "LJavaIoInputStream", 0x9, NULL },
    { "decode4to3WithByteArray:withInt:withByteArray:withInt:", NULL, "I", 0xa, NULL },
    { "encode3to4WithByteArray:withByteArray:withInt:", NULL, "LIOSByteArray", 0xa, NULL },
    { "encode3to4WithByteArray:withInt:withInt:withByteArray:withInt:", NULL, "LIOSByteArray", 0xa, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "DECODE_", NULL, 0x19, "I" },
    { "DONT_BREAK_LINES_", NULL, 0x19, "I" },
    { "ENCODE_", NULL, 0x19, "I" },
    { "GZIP_", NULL, 0x19, "I" },
    { "NO_OPTIONS_", NULL, 0x19, "I" },
    { "DECODABET_", NULL, 0x1a, "LIOSByteArray" },
    { "EQUALS_SIGN_", NULL, 0x1a, "B" },
    { "EQUALS_SIGN_ENC_", NULL, 0x1a, "B" },
    { "MAX_LINE_LENGTH_", NULL, 0x1a, "I" },
    { "NEW_LINE_", NULL, 0x1a, "B" },
    { "PREFERRED_ENCODING_", NULL, 0x1a, "LNSString" },
    { "WHITE_SPACE_ENC_", NULL, 0x1a, "B" },
    { "_NATIVE_ALPHABET_", NULL, 0x1a, "LIOSByteArray" },
    { "ALPHABET_", NULL, 0x1a, "LIOSByteArray" },
  };
  static J2ObjcClassInfo _RAREUTBase64 = { "Base64", "com.appnativa.util", NULL, 0x1, 21, methods, 14, fields, 0, NULL};
  return &_RAREUTBase64;
}

@end
@implementation RAREUTBase64_InputStream

- (id)initWithJavaIoInputStream:(JavaIoInputStream *)inArg {
  return [self initRAREUTBase64_InputStreamWithJavaIoInputStream:inArg withInt:RAREUTBase64_DECODE];
}

- (id)initRAREUTBase64_InputStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg
                                                withInt:(int)options {
  if (self = [super initWithJavaIoInputStream:inArg]) {
    self->breakLines_ = (options & RAREUTBase64_DONT_BREAK_LINES) != RAREUTBase64_DONT_BREAK_LINES;
    self->encode_ = (options & RAREUTBase64_ENCODE) == RAREUTBase64_ENCODE;
    self->bufferLength_ = encode_ ? 4 : 3;
    self->buffer_ = [IOSByteArray arrayWithLength:bufferLength_];
    self->position_ = -1;
    self->lineLength_ = 0;
  }
  return self;
}

- (id)initWithJavaIoInputStream:(JavaIoInputStream *)inArg
                        withInt:(int)options {
  return [self initRAREUTBase64_InputStreamWithJavaIoInputStream:inArg withInt:options];
}

- (int)read {
  if (position_ < 0) {
    if (encode_) {
      IOSByteArray *b3 = [IOSByteArray arrayWithLength:3];
      int numBinaryBytes = 0;
      for (int i = 0; i < 3; i++) {
        @try {
          int b = [((JavaIoInputStream *) nil_chk(in_)) read];
          if (b >= 0) {
            (*IOSByteArray_GetRef(b3, i)) = (char) b;
            numBinaryBytes++;
          }
        }
        @catch (JavaIoIOException *e) {
          if (i == 0) {
            @throw e;
          }
        }
      }
      if (numBinaryBytes > 0) {
        (void) [RAREUTBase64 encode3to4WithByteArray:b3 withInt:0 withInt:numBinaryBytes withByteArray:buffer_ withInt:0];
        position_ = 0;
        numSigBytes_ = 4;
      }
      else {
        return -1;
      }
    }
    else {
      IOSByteArray *b4 = [IOSByteArray arrayWithLength:4];
      int i = 0;
      for (i = 0; i < 4; i++) {
        int b = 0;
        do {
          b = [((JavaIoInputStream *) nil_chk(in_)) read];
        }
        while ((b >= 0) && (IOSByteArray_Get(nil_chk([RAREUTBase64 DECODABET]), b & (int) 0x7f) <= RAREUTBase64_WHITE_SPACE_ENC));
        if (b < 0) {
          break;
        }
        (*IOSByteArray_GetRef(b4, i)) = (char) b;
      }
      if (i == 4) {
        numSigBytes_ = [RAREUTBase64 decode4to3WithByteArray:b4 withInt:0 withByteArray:buffer_ withInt:0];
        position_ = 0;
      }
      else if (i == 0) {
        return -1;
      }
      else {
        @throw [[JavaIoIOException alloc] initWithNSString:@"Improperly padded Base64 input."];
      }
    }
  }
  if (position_ >= 0) {
    if (position_ >= numSigBytes_) {
      return -1;
    }
    if (encode_ && breakLines_ && (lineLength_ >= RAREUTBase64_MAX_LINE_LENGTH)) {
      lineLength_ = 0;
      return 0x000a;
    }
    else {
      lineLength_++;
      int b = IOSByteArray_Get(nil_chk(buffer_), position_++);
      if (position_ >= bufferLength_) {
        position_ = -1;
      }
      return b & (int) 0xFF;
    }
  }
  else {
    @throw [[JavaIoIOException alloc] initWithNSString:@"Error in Base64 code reading stream."];
  }
}

- (int)readWithByteArray:(IOSByteArray *)dest
                 withInt:(int)off
                 withInt:(int)len {
  int i;
  int b;
  for (i = 0; i < len; i++) {
    b = [self read];
    if (b >= 0) {
      (*IOSByteArray_GetRef(nil_chk(dest), off + i)) = (char) b;
    }
    else if (i == 0) {
      return -1;
    }
    else {
      break;
    }
  }
  return i;
}

- (void)copyAllFieldsTo:(RAREUTBase64_InputStream *)other {
  [super copyAllFieldsTo:other];
  other->breakLines_ = breakLines_;
  other->buffer_ = buffer_;
  other->bufferLength_ = bufferLength_;
  other->encode_ = encode_;
  other->lineLength_ = lineLength_;
  other->numSigBytes_ = numSigBytes_;
  other->position_ = position_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "read", NULL, "I", 0x1, "JavaIoIOException" },
    { "readWithByteArray:withInt:withInt:", NULL, "I", 0x1, "JavaIoIOException" },
  };
  static J2ObjcClassInfo _RAREUTBase64_InputStream = { "InputStream", "com.appnativa.util", "Base64", 0x9, 2, methods, 0, NULL, 0, NULL};
  return &_RAREUTBase64_InputStream;
}

@end
@implementation RAREUTBase64_OutputStream

- (id)initWithJavaIoOutputStream:(JavaIoOutputStream *)outArg {
  return [self initRAREUTBase64_OutputStreamWithJavaIoOutputStream:outArg withInt:RAREUTBase64_ENCODE];
}

- (id)initRAREUTBase64_OutputStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg
                                                  withInt:(int)options {
  if (self = [super initWithJavaIoOutputStream:outArg]) {
    self->breakLines_ = (options & RAREUTBase64_DONT_BREAK_LINES) != RAREUTBase64_DONT_BREAK_LINES;
    self->encode_ = (options & RAREUTBase64_ENCODE) == RAREUTBase64_ENCODE;
    self->bufferLength_ = encode_ ? 3 : 4;
    self->buffer_ = [IOSByteArray arrayWithLength:bufferLength_];
    self->position_ = 0;
    self->lineLength_ = 0;
    self->suspendEncoding__ = NO;
    self->b4_ = [IOSByteArray arrayWithLength:4];
  }
  return self;
}

- (id)initWithJavaIoOutputStream:(JavaIoOutputStream *)outArg
                         withInt:(int)options {
  return [self initRAREUTBase64_OutputStreamWithJavaIoOutputStream:outArg withInt:options];
}

- (void)close {
  [self flushBase64];
  [super close];
  buffer_ = nil;
  out_ = nil;
}

- (void)flushBase64 {
  if (position_ > 0) {
    if (encode_) {
      [((JavaIoOutputStream *) nil_chk(out_)) writeWithByteArray:[RAREUTBase64 encode3to4WithByteArray:b4_ withByteArray:buffer_ withInt:position_]];
      position_ = 0;
    }
    else {
      @throw [[JavaIoIOException alloc] initWithNSString:@"Base64 input not properly padded."];
    }
  }
}

- (void)resumeEncoding {
  self->suspendEncoding__ = NO;
}

- (void)suspendEncoding {
  [self flushBase64];
  self->suspendEncoding__ = YES;
}

- (void)writeWithInt:(int)theByte {
  if (suspendEncoding__) {
    [((JavaIoOutputStream *) nil_chk(out_)) writeWithInt:theByte];
    return;
  }
  if (encode_) {
    (*IOSByteArray_GetRef(nil_chk(buffer_), position_++)) = (char) theByte;
    if (position_ >= bufferLength_) {
      [((JavaIoOutputStream *) nil_chk(out_)) writeWithByteArray:[RAREUTBase64 encode3to4WithByteArray:b4_ withByteArray:buffer_ withInt:bufferLength_]];
      lineLength_ += 4;
      if (breakLines_ && (lineLength_ >= RAREUTBase64_MAX_LINE_LENGTH)) {
        [out_ writeWithInt:RAREUTBase64_NEW_LINE];
        lineLength_ = 0;
      }
      position_ = 0;
    }
  }
  else {
    if (IOSByteArray_Get(nil_chk([RAREUTBase64 DECODABET]), theByte & (int) 0x7f) > RAREUTBase64_WHITE_SPACE_ENC) {
      (*IOSByteArray_GetRef(nil_chk(buffer_), position_++)) = (char) theByte;
      if (position_ >= bufferLength_) {
        int len = [RAREUTBase64 decode4to3WithByteArray:buffer_ withInt:0 withByteArray:b4_ withInt:0];
        [((JavaIoOutputStream *) nil_chk(out_)) writeWithByteArray:b4_ withInt:0 withInt:len];
        position_ = 0;
      }
    }
    else if (IOSByteArray_Get([RAREUTBase64 DECODABET], theByte & (int) 0x7f) != RAREUTBase64_WHITE_SPACE_ENC) {
      @throw [[JavaIoIOException alloc] initWithNSString:@"Invalid character in Base64 data."];
    }
  }
}

- (void)writeWithByteArray:(IOSByteArray *)theBytes
                   withInt:(int)off
                   withInt:(int)len {
  if (suspendEncoding__) {
    [((JavaIoOutputStream *) nil_chk(out_)) writeWithByteArray:theBytes withInt:off withInt:len];
    return;
  }
  for (int i = 0; i < len; i++) {
    [self writeWithInt:IOSByteArray_Get(nil_chk(theBytes), off + i)];
  }
}

- (void)copyAllFieldsTo:(RAREUTBase64_OutputStream *)other {
  [super copyAllFieldsTo:other];
  other->b4_ = b4_;
  other->breakLines_ = breakLines_;
  other->buffer_ = buffer_;
  other->bufferLength_ = bufferLength_;
  other->encode_ = encode_;
  other->lineLength_ = lineLength_;
  other->position_ = position_;
  other->suspendEncoding__ = suspendEncoding__;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "close", NULL, "V", 0x1, "JavaIoIOException" },
    { "flushBase64", NULL, "V", 0x1, "JavaIoIOException" },
    { "suspendEncoding", NULL, "V", 0x1, "JavaIoIOException" },
    { "writeWithInt:", NULL, "V", 0x1, "JavaIoIOException" },
    { "writeWithByteArray:withInt:withInt:", NULL, "V", 0x1, "JavaIoIOException" },
  };
  static J2ObjcClassInfo _RAREUTBase64_OutputStream = { "OutputStream", "com.appnativa.util", "Base64", 0x9, 5, methods, 0, NULL, 0, NULL};
  return &_RAREUTBase64_OutputStream;
}

@end
