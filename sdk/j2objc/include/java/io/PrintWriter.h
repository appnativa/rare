//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/io/PrintWriter.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaIoPrintWriter_H_
#define _JavaIoPrintWriter_H_

@class IOSCharArray;
@class IOSObjectArray;
@class JavaIoFile;
@class JavaIoOutputStream;
@class JavaUtilLocale;
@protocol JavaLangCharSequence;

#import "JreEmulation.h"
#include "java/io/Writer.h"

@interface JavaIoPrintWriter : JavaIoWriter {
 @public
  JavaIoWriter *out_;
  BOOL ioError_;
  BOOL autoFlush_;
}

- (id)initWithJavaIoOutputStream:(JavaIoOutputStream *)outArg;
- (id)initWithJavaIoOutputStream:(JavaIoOutputStream *)outArg
                     withBoolean:(BOOL)autoFlush;
- (id)initWithJavaIoWriter:(JavaIoWriter *)wr;
- (id)initWithJavaIoWriter:(JavaIoWriter *)wr
               withBoolean:(BOOL)autoFlush;
- (id)initWithJavaIoFile:(JavaIoFile *)file;
- (id)initWithJavaIoFile:(JavaIoFile *)file
            withNSString:(NSString *)csn;
- (id)initWithNSString:(NSString *)fileName;
- (id)initWithNSString:(NSString *)fileName
          withNSString:(NSString *)csn;
- (BOOL)checkError;
- (void)clearError;
- (void)close;
- (void)flush;
- (JavaIoPrintWriter *)formatWithNSString:(NSString *)format
                        withNSObjectArray:(IOSObjectArray *)args;
- (JavaIoPrintWriter *)formatWithJavaUtilLocale:(JavaUtilLocale *)l
                                   withNSString:(NSString *)format
                              withNSObjectArray:(IOSObjectArray *)args;
- (JavaIoPrintWriter *)printfWithNSString:(NSString *)format
                        withNSObjectArray:(IOSObjectArray *)args;
- (JavaIoPrintWriter *)printfWithJavaUtilLocale:(JavaUtilLocale *)l
                                   withNSString:(NSString *)format
                              withNSObjectArray:(IOSObjectArray *)args;
- (void)printWithCharArray:(IOSCharArray *)charArray;
- (void)printWithChar:(unichar)ch;
- (void)printWithDouble:(double)dnum;
- (void)printWithFloat:(float)fnum;
- (void)printWithInt:(int)inum;
- (void)printWithLong:(long long int)lnum;
- (void)printWithId:(id)obj;
- (void)printWithNSString:(NSString *)str;
- (void)printWithBoolean:(BOOL)bool_;
- (void)println;
- (void)printlnWithCharArray:(IOSCharArray *)chars;
- (void)printlnWithChar:(unichar)c;
- (void)printlnWithDouble:(double)d;
- (void)printlnWithFloat:(float)f;
- (void)printlnWithInt:(int)i;
- (void)printlnWithLong:(long long int)l;
- (void)printlnWithId:(id)obj;
- (void)printlnWithNSString:(NSString *)str;
- (void)printlnWithBoolean:(BOOL)b;
- (void)setError;
- (void)writeWithCharArray:(IOSCharArray *)buf;
- (void)writeWithCharArray:(IOSCharArray *)buf
                   withInt:(int)offset
                   withInt:(int)count;
- (void)writeWithInt:(int)oneChar;
- (void)doWriteWithCharArray:(IOSCharArray *)buf
                     withInt:(int)offset
                     withInt:(int)count;
- (void)writeWithNSString:(NSString *)str;
- (void)writeWithNSString:(NSString *)str
                  withInt:(int)offset
                  withInt:(int)count;
- (JavaIoPrintWriter *)appendWithChar:(unichar)c;
- (JavaIoPrintWriter *)appendWithJavaLangCharSequence:(id<JavaLangCharSequence>)csq;
- (JavaIoPrintWriter *)appendWithJavaLangCharSequence:(id<JavaLangCharSequence>)csq
                                              withInt:(int)start
                                              withInt:(int)end;
- (void)copyAllFieldsTo:(JavaIoPrintWriter *)other;
@end

J2OBJC_FIELD_SETTER(JavaIoPrintWriter, out_, JavaIoWriter *)

#endif // _JavaIoPrintWriter_H_