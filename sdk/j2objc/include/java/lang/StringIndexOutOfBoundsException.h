//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/lang/StringIndexOutOfBoundsException.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaLangStringIndexOutOfBoundsException_H_
#define _JavaLangStringIndexOutOfBoundsException_H_

#import "JreEmulation.h"
#include "java/lang/IndexOutOfBoundsException.h"

#define JavaLangStringIndexOutOfBoundsException_serialVersionUID -6762910422159637258

@interface JavaLangStringIndexOutOfBoundsException : JavaLangIndexOutOfBoundsException {
}

- (id)init;
- (id)initWithInt:(int)index;
- (id)initWithNSString:(NSString *)detailMessage;
- (id)initWithNSString:(NSString *)s
               withInt:(int)index;
- (id)initWithInt:(int)sourceLength
          withInt:(int)index;
- (id)initWithNSString:(NSString *)s
               withInt:(int)offset
               withInt:(int)count;
- (id)initWithInt:(int)sourceLength
          withInt:(int)offset
          withInt:(int)count;
@end

#endif // _JavaLangStringIndexOutOfBoundsException_H_
