//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/nio/charset/UnsupportedCharsetException.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaNioCharsetUnsupportedCharsetException_H_
#define _JavaNioCharsetUnsupportedCharsetException_H_

#import "JreEmulation.h"
#include "java/lang/IllegalArgumentException.h"

#define JavaNioCharsetUnsupportedCharsetException_serialVersionUID 1490765524727386367

@interface JavaNioCharsetUnsupportedCharsetException : JavaLangIllegalArgumentException {
 @public
  NSString *charsetName_;
}

- (id)initWithNSString:(NSString *)charsetName;
- (NSString *)getCharsetName;
- (void)copyAllFieldsTo:(JavaNioCharsetUnsupportedCharsetException *)other;
@end

J2OBJC_FIELD_SETTER(JavaNioCharsetUnsupportedCharsetException, charsetName_, NSString *)

#endif // _JavaNioCharsetUnsupportedCharsetException_H_
