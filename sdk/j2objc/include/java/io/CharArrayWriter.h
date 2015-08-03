//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/io/CharArrayWriter.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaIoCharArrayWriter_H_
#define _JavaIoCharArrayWriter_H_

@class IOSCharArray;
@protocol JavaLangCharSequence;

#import "JreEmulation.h"
#include "java/io/Writer.h"

@interface JavaIoCharArrayWriter : JavaIoWriter {
 @public
  IOSCharArray *buf_;
  int count_;
}

- (id)init;
- (id)initWithInt:(int)initialSize;
- (void)close;
- (void)expandWithInt:(int)i;
- (void)flush;
- (void)reset;
- (int)size;
- (IOSCharArray *)toCharArray;
- (NSString *)description;
- (void)writeWithCharArray:(IOSCharArray *)c
                   withInt:(int)offset
                   withInt:(int)len;
- (void)writeWithInt:(int)oneChar;
- (void)writeWithNSString:(NSString *)str
                  withInt:(int)offset
                  withInt:(int)len;
- (void)writeToWithJavaIoWriter:(JavaIoWriter *)outArg;
- (JavaIoCharArrayWriter *)appendWithChar:(unichar)c;
- (JavaIoCharArrayWriter *)appendWithJavaLangCharSequence:(id<JavaLangCharSequence>)csq;
- (JavaIoCharArrayWriter *)appendWithJavaLangCharSequence:(id<JavaLangCharSequence>)csq
                                                  withInt:(int)start
                                                  withInt:(int)end;
- (void)copyAllFieldsTo:(JavaIoCharArrayWriter *)other;
@end

J2OBJC_FIELD_SETTER(JavaIoCharArrayWriter, buf_, IOSCharArray *)

#endif // _JavaIoCharArrayWriter_H_
