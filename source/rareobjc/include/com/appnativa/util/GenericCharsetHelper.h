//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-apple-porting/com/appnativa/util/GenericCharsetHelper.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREUTGenericCharsetHelper_H_
#define _RAREUTGenericCharsetHelper_H_

@class IOSByteArray;
@class IOSCharArray;
@class JavaLangIndexOutOfBoundsException;
@class JavaNioCharsetCharset;
@class RAREUTByteArray;
@class RAREUTCharArray;

#import "JreEmulation.h"
#include "com/appnativa/util/iCharsetHelper.h"

@interface RAREUTGenericCharsetHelper : NSObject < RAREUTiCharsetHelper > {
 @public
  JavaNioCharsetCharset *charset_;
  JavaLangIndexOutOfBoundsException *ex_;
}

- (id)initWithJavaNioCharsetCharset:(JavaNioCharsetCharset *)charset;
- (id)initWithNSString:(NSString *)cs;
- (id<RAREUTiCharsetHelper>)copy__ OBJC_METHOD_FAMILY_NONE;
- (int)getByteLengthWithCharArray:(IOSCharArray *)chars
                          withInt:(int)pos
                          withInt:(int)len;
- (IOSByteArray *)getBytesWithNSString:(NSString *)string;
- (IOSByteArray *)getBytesWithCharArray:(IOSCharArray *)chars
                                withInt:(int)charPos
                                withInt:(int)charLen;
- (int)getBytesWithCharArray:(IOSCharArray *)chars
                     withInt:(int)charPos
                     withInt:(int)charLen
               withByteArray:(IOSByteArray *)bytes
                     withInt:(int)bytePos;
- (int)getBytesWithCharArray:(IOSCharArray *)chars
                     withInt:(int)charPos
                     withInt:(int)charLen
         withRAREUTByteArray:(RAREUTByteArray *)bytes
                     withInt:(int)bytePos;
- (int)getCharLengthWithByteArray:(IOSByteArray *)bytes
                          withInt:(int)pos
                          withInt:(int)len;
- (int)getCharsWithByteArray:(IOSByteArray *)bytes
                     withInt:(int)bytePos
                     withInt:(int)byteLen
               withCharArray:(IOSCharArray *)chars
                     withInt:(int)charPos;
- (int)getCharsWithByteArray:(IOSByteArray *)bytes
                     withInt:(int)bytePos
                     withInt:(int)byteLen
         withRAREUTCharArray:(RAREUTCharArray *)chars
                     withInt:(int)charPos;
- (NSString *)getEncoding;
- (NSString *)getStringWithByteArray:(IOSByteArray *)bytes;
- (NSString *)getStringWithByteArray:(IOSByteArray *)bytes
                             withInt:(int)offset
                             withInt:(int)length;
- (BOOL)isByteLenghthSupported;
- (BOOL)isCharLengthSupported;
- (int)nsEncoding;
- (void)copyAllFieldsTo:(RAREUTGenericCharsetHelper *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTGenericCharsetHelper, charset_, JavaNioCharsetCharset *)
J2OBJC_FIELD_SETTER(RAREUTGenericCharsetHelper, ex_, JavaLangIndexOutOfBoundsException *)

typedef RAREUTGenericCharsetHelper ComAppnativaUtilGenericCharsetHelper;

#endif // _RAREUTGenericCharsetHelper_H_
