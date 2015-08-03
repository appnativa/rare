//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-apple-porting/com/appnativa/util/Base64.java
//
//  Created by decoteaud on 7/14/15.
//

#ifndef _RAREUTBase64_H_
#define _RAREUTBase64_H_

@class IOSByteArray;
@class JavaIoInputStream;
@class JavaIoOutputStream;

#import "JreEmulation.h"
#include "java/io/FilterInputStream.h"
#include "java/io/FilterOutputStream.h"

#define RAREUTBase64_DECODE 0
#define RAREUTBase64_DONT_BREAK_LINES 8
#define RAREUTBase64_ENCODE 1
#define RAREUTBase64_EQUALS_SIGN 61
#define RAREUTBase64_EQUALS_SIGN_ENC -1
#define RAREUTBase64_MAX_LINE_LENGTH 76
#define RAREUTBase64_NEW_LINE 10
#define RAREUTBase64_NO_OPTIONS 0
#define RAREUTBase64_WHITE_SPACE_ENC -5

@interface RAREUTBase64 : NSObject {
}

+ (int)DECODE;
+ (int)DONT_BREAK_LINES;
+ (int)ENCODE;
+ (int)NO_OPTIONS;
+ (IOSByteArray *)DECODABET;
+ (NSString *)PREFERRED_ENCODING;
+ (IOSByteArray *)_NATIVE_ALPHABET;
+ (IOSByteArray *)ALPHABET;
- (id)init;
+ (IOSByteArray *)decodeWithByteArray:(IOSByteArray *)source;
+ (IOSByteArray *)decodeWithNSString:(NSString *)s;
+ (IOSByteArray *)decodeWithByteArray:(IOSByteArray *)source
                              withInt:(int)off
                              withInt:(int)len;
+ (IOSByteArray *)decodeFromFileWithNSString:(NSString *)filename;
+ (BOOL)decodeToFileWithNSString:(NSString *)dataToDecode
                    withNSString:(NSString *)filename;
+ (JavaIoInputStream *)decodingStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (NSString *)encodeWithByteArray:(IOSByteArray *)source;
+ (NSString *)encodeWithByteArray:(IOSByteArray *)source
                     withNSString:(NSString *)encoding;
+ (NSString *)encodeWithByteArray:(IOSByteArray *)source
                          withInt:(int)off
                          withInt:(int)len
                     withNSString:(NSString *)encoding;
+ (NSString *)encodeUTF8WithNSString:(NSString *)source;
+ (NSString *)decodeUTF8WithNSString:(NSString *)source;
+ (NSString *)encodeBytesWithByteArray:(IOSByteArray *)source;
+ (NSString *)encodeBytesWithByteArray:(IOSByteArray *)source
                               withInt:(int)options;
+ (NSString *)encodeBytesWithByteArray:(IOSByteArray *)source
                               withInt:(int)off
                               withInt:(int)len;
+ (NSString *)encodeBytesWithByteArray:(IOSByteArray *)source
                               withInt:(int)off
                               withInt:(int)len
                               withInt:(int)options;
+ (NSString *)encodeFromFileWithNSString:(NSString *)filename;
+ (BOOL)encodeToFileWithByteArray:(IOSByteArray *)dataToEncode
                     withNSString:(NSString *)filename;
+ (int)decode4to3WithByteArray:(IOSByteArray *)source
                       withInt:(int)srcOffset
                 withByteArray:(IOSByteArray *)destination
                       withInt:(int)destOffset;
+ (IOSByteArray *)encode3to4WithByteArray:(IOSByteArray *)b4
                            withByteArray:(IOSByteArray *)threeBytes
                                  withInt:(int)numSigBytes;
+ (IOSByteArray *)encode3to4WithByteArray:(IOSByteArray *)source
                                  withInt:(int)srcOffset
                                  withInt:(int)numSigBytes
                            withByteArray:(IOSByteArray *)destination
                                  withInt:(int)destOffset;
@end

typedef RAREUTBase64 ComAppnativaUtilBase64;

@interface RAREUTBase64_InputStream : JavaIoFilterInputStream {
 @public
  BOOL breakLines_;
  IOSByteArray *buffer_;
  int bufferLength_;
  BOOL encode_;
  int lineLength_;
  int numSigBytes_;
  int position_;
}

- (id)initWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (id)initWithJavaIoInputStream:(JavaIoInputStream *)inArg
                        withInt:(int)options;
- (int)read;
- (int)readWithByteArray:(IOSByteArray *)dest
                 withInt:(int)off
                 withInt:(int)len;
- (void)copyAllFieldsTo:(RAREUTBase64_InputStream *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTBase64_InputStream, buffer_, IOSByteArray *)

@interface RAREUTBase64_OutputStream : JavaIoFilterOutputStream {
 @public
  IOSByteArray *b4_;
  BOOL breakLines_;
  IOSByteArray *buffer_;
  int bufferLength_;
  BOOL encode_;
  int lineLength_;
  int position_;
  BOOL suspendEncoding__;
}

- (id)initWithJavaIoOutputStream:(JavaIoOutputStream *)outArg;
- (id)initWithJavaIoOutputStream:(JavaIoOutputStream *)outArg
                         withInt:(int)options;
- (void)close;
- (void)flushBase64;
- (void)resumeEncoding;
- (void)suspendEncoding;
- (void)writeWithInt:(int)theByte;
- (void)writeWithByteArray:(IOSByteArray *)theBytes
                   withInt:(int)off
                   withInt:(int)len;
- (void)copyAllFieldsTo:(RAREUTBase64_OutputStream *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTBase64_OutputStream, b4_, IOSByteArray *)
J2OBJC_FIELD_SETTER(RAREUTBase64_OutputStream, buffer_, IOSByteArray *)

#endif // _RAREUTBase64_H_