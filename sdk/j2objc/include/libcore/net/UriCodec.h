//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/libcore/net/UriCodec.java
//
//  Created by tball on 11/23/13.
//

#ifndef _LibcoreNetUriCodec_H_
#define _LibcoreNetUriCodec_H_

@class JavaLangStringBuilder;
@class JavaNioCharsetCharset;

#import "JreEmulation.h"

@interface LibcoreNetUriCodec : NSObject {
}

- (BOOL)isRetainedWithChar:(unichar)c;
- (NSString *)validateWithNSString:(NSString *)uri
                           withInt:(int)start
                           withInt:(int)end
                      withNSString:(NSString *)name;
+ (void)validateSimpleWithNSString:(NSString *)s
                      withNSString:(NSString *)legal;
- (void)appendEncodedWithJavaLangStringBuilder:(JavaLangStringBuilder *)builder
                                  withNSString:(NSString *)s
                     withJavaNioCharsetCharset:(JavaNioCharsetCharset *)charset
                                   withBoolean:(BOOL)isPartiallyEncoded;
- (NSString *)encodeWithNSString:(NSString *)s
       withJavaNioCharsetCharset:(JavaNioCharsetCharset *)charset;
- (void)appendEncodedWithJavaLangStringBuilder:(JavaLangStringBuilder *)builder
                                  withNSString:(NSString *)s;
- (void)appendPartiallyEncodedWithJavaLangStringBuilder:(JavaLangStringBuilder *)builder
                                           withNSString:(NSString *)s;
+ (NSString *)decodeWithNSString:(NSString *)s
                     withBoolean:(BOOL)convertPlus
       withJavaNioCharsetCharset:(JavaNioCharsetCharset *)charset
                     withBoolean:(BOOL)throwOnFailure;
+ (int)hexToIntWithChar:(unichar)c;
+ (NSString *)decodeWithNSString:(NSString *)s;
+ (void)appendHexWithJavaLangStringBuilder:(JavaLangStringBuilder *)builder
                              withNSString:(NSString *)s
                 withJavaNioCharsetCharset:(JavaNioCharsetCharset *)charset;
+ (void)appendHexWithJavaLangStringBuilder:(JavaLangStringBuilder *)sb
                                  withByte:(char)b;
- (id)init;
@end

#endif // _LibcoreNetUriCodec_H_