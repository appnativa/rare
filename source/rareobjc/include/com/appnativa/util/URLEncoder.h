//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/URLEncoder.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTURLEncoder_H_
#define _RAREUTURLEncoder_H_

@class IOSByteArray;
@class JavaIoOutputStream;
@class JavaIoWriter;
@class JavaLangStringBuilder;
@class JavaUtilBitSet;

#import "JreEmulation.h"

@interface RAREUTURLEncoder : NSObject {
}

+ (NSString *)charset;
+ (void)setCharset:(NSString *)charset;
+ (JavaUtilBitSet *)WWW_FORM_URL;
+ (JavaUtilBitSet *)ALL_URL;
- (id)init;
+ (IOSByteArray *)decodeWithByteArray:(IOSByteArray *)bytes;
+ (id)decodeWithId:(id)pObject;
+ (NSString *)decodeWithNSString:(NSString *)pString;
+ (NSString *)decodeWithNSString:(NSString *)pString
                    withNSString:(NSString *)charset;
+ (IOSByteArray *)decodeUrlWithByteArray:(IOSByteArray *)bytes;
+ (IOSByteArray *)encodeWithJavaUtilBitSet:(JavaUtilBitSet *)urlsafe
                             withByteArray:(IOSByteArray *)bytes;
+ (void)encodeWithJavaUtilBitSet:(JavaUtilBitSet *)urlsafe
                   withByteArray:(IOSByteArray *)bytes
          withJavaIoOutputStream:(JavaIoOutputStream *)buffer;
+ (void)encodeWithJavaUtilBitSet:(JavaUtilBitSet *)urlsafe
                   withByteArray:(IOSByteArray *)bytes
       withJavaLangStringBuilder:(JavaLangStringBuilder *)buffer;
+ (void)encodeWithJavaUtilBitSet:(JavaUtilBitSet *)urlsafe
                   withByteArray:(IOSByteArray *)bytes
                withJavaIoWriter:(JavaIoWriter *)buffer;
+ (void)encodeWithNSString:(NSString *)pString
              withNSString:(NSString *)charset
    withJavaIoOutputStream:(JavaIoOutputStream *)buffer;
+ (IOSByteArray *)encodeComponentWithByteArray:(IOSByteArray *)bytes;
+ (NSString *)encodeComponentWithNSString:(NSString *)pString;
+ (NSString *)encodeComponentWithNSString:(NSString *)pString
                             withNSString:(NSString *)charset;
+ (void)encodeComponentWithNSString:(NSString *)pString
                       withNSString:(NSString *)charset
             withJavaIoOutputStream:(JavaIoOutputStream *)buffer;
+ (void)encodeComponentWithNSString:(NSString *)pString
                       withNSString:(NSString *)charset
          withJavaLangStringBuilder:(JavaLangStringBuilder *)buffer;
+ (void)encodeComponentWithNSString:(NSString *)pString
                       withNSString:(NSString *)charset
                   withJavaIoWriter:(JavaIoWriter *)buffer;
+ (IOSByteArray *)encodeUrlWithByteArray:(IOSByteArray *)bytes;
+ (NSString *)encodeUrlWithNSString:(NSString *)pString;
+ (NSString *)encodeUrlWithNSString:(NSString *)pString
                       withNSString:(NSString *)charset;
+ (void)encodeUrlWithNSString:(NSString *)pString
                 withNSString:(NSString *)charset
    withJavaLangStringBuilder:(JavaLangStringBuilder *)buffer;
+ (void)encodeUrlWithNSString:(NSString *)pString
                 withNSString:(NSString *)charset
             withJavaIoWriter:(JavaIoWriter *)buffer;
+ (NSString *)getDefaultCharset;
@end

typedef RAREUTURLEncoder ComAppnativaUtilURLEncoder;

#endif // _RAREUTURLEncoder_H_
