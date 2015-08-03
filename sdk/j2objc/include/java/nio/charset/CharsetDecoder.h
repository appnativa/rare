//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/nio/charset/CharsetDecoder.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaNioCharsetCharsetDecoder_H_
#define _JavaNioCharsetCharsetDecoder_H_

@class JavaNioByteBuffer;
@class JavaNioCharBuffer;
@class JavaNioCharsetCharset;
@class JavaNioCharsetCoderResult;
@class JavaNioCharsetCodingErrorAction;

#import "JreEmulation.h"

@interface JavaNioCharsetCharsetDecoder : NSObject {
 @public
  float averageCharsPerByte__;
  float maxCharsPerByte__;
  JavaNioCharsetCharset *cs_;
  NSString *replacementChars_;
  int status_;
}

- (id)initWithJavaNioCharsetCharset:(JavaNioCharsetCharset *)charset
                          withFloat:(float)averageCharsPerByte
                          withFloat:(float)maxCharsPerByte;
- (float)averageCharsPerByte;
- (JavaNioCharsetCharset *)charset;
- (JavaNioCharBuffer *)decodeWithJavaNioByteBuffer:(JavaNioByteBuffer *)inArg;
- (JavaNioCharsetCoderResult *)decodeWithJavaNioByteBuffer:(JavaNioByteBuffer *)inArg
                                     withJavaNioCharBuffer:(JavaNioCharBuffer *)outArg
                                               withBoolean:(BOOL)endOfInput;
- (JavaNioCharsetCoderResult *)decodeLoopWithJavaNioByteBuffer:(JavaNioByteBuffer *)inArg
                                         withJavaNioCharBuffer:(JavaNioCharBuffer *)outArg;
- (JavaNioCharsetCharset *)detectedCharset;
- (JavaNioCharsetCoderResult *)flushWithJavaNioCharBuffer:(JavaNioCharBuffer *)outArg;
- (BOOL)isAutoDetecting;
- (BOOL)isCharsetDetected;
- (float)maxCharsPerByte;
- (JavaNioCharsetCharsetDecoder *)onMalformedInputWithJavaNioCharsetCodingErrorAction:(JavaNioCharsetCodingErrorAction *)newAction;
- (JavaNioCharsetCharsetDecoder *)onUnmappableCharacterWithJavaNioCharsetCodingErrorAction:(JavaNioCharsetCodingErrorAction *)newAction;
- (NSString *)replacement;
- (JavaNioCharsetCharsetDecoder *)replaceWithWithNSString:(NSString *)replacement;
- (JavaNioCharsetCharsetDecoder *)reset;
- (void)copyAllFieldsTo:(JavaNioCharsetCharsetDecoder *)other;
@end

J2OBJC_FIELD_SETTER(JavaNioCharsetCharsetDecoder, cs_, JavaNioCharsetCharset *)
J2OBJC_FIELD_SETTER(JavaNioCharsetCharsetDecoder, replacementChars_, NSString *)

#endif // _JavaNioCharsetCharsetDecoder_H_