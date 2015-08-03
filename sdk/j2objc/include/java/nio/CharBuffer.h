//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/nio/src/main/java/common/java/nio/CharBuffer.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaNioCharBuffer_H_
#define _JavaNioCharBuffer_H_

@class IOSCharArray;
@class JavaNioByteOrder;

#import "JreEmulation.h"
#include "java/lang/Appendable.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Comparable.h"
#include "java/lang/Readable.h"
#include "java/nio/Buffer.h"

@interface JavaNioCharBuffer : JavaNioBuffer < JavaLangComparable, JavaLangCharSequence, JavaLangAppendable, JavaLangReadable > {
}

+ (JavaNioCharBuffer *)allocateWithInt:(int)capacity OBJC_METHOD_FAMILY_NONE;
+ (JavaNioCharBuffer *)wrapWithCharArray:(IOSCharArray *)array;
+ (JavaNioCharBuffer *)wrapWithCharArray:(IOSCharArray *)array
                                 withInt:(int)start
                                 withInt:(int)len;
+ (JavaNioCharBuffer *)wrapWithJavaLangCharSequence:(id<JavaLangCharSequence>)chseq;
+ (JavaNioCharBuffer *)wrapWithJavaLangCharSequence:(id<JavaLangCharSequence>)chseq
                                            withInt:(int)start
                                            withInt:(int)end;
- (id)initWithInt:(int)capacity;
- (IOSCharArray *)array;
- (int)arrayOffset;
- (JavaNioCharBuffer *)asReadOnlyBuffer;
- (unichar)charAtWithInt:(int)index;
- (JavaNioCharBuffer *)compact;
- (int)compareToWithId:(JavaNioCharBuffer *)otherBuffer;
- (JavaNioCharBuffer *)duplicate;
- (BOOL)isEqual:(id)other;
- (unichar)get;
- (JavaNioCharBuffer *)getWithCharArray:(IOSCharArray *)dest;
- (JavaNioCharBuffer *)getWithCharArray:(IOSCharArray *)dest
                                withInt:(int)off
                                withInt:(int)len;
- (unichar)getWithInt:(int)index;
- (BOOL)hasArray;
- (NSUInteger)hash;
- (BOOL)isDirect;
- (int)sequenceLength;
- (JavaNioByteOrder *)order;
- (IOSCharArray *)protectedArray;
- (int)protectedArrayOffset;
- (BOOL)protectedHasArray;
- (JavaNioCharBuffer *)putWithChar:(unichar)c;
- (JavaNioCharBuffer *)putWithCharArray:(IOSCharArray *)src;
- (JavaNioCharBuffer *)putWithCharArray:(IOSCharArray *)src
                                withInt:(int)off
                                withInt:(int)len;
- (JavaNioCharBuffer *)putWithJavaNioCharBuffer:(JavaNioCharBuffer *)src;
- (JavaNioCharBuffer *)putWithInt:(int)index
                         withChar:(unichar)c;
- (JavaNioCharBuffer *)putWithNSString:(NSString *)str;
- (JavaNioCharBuffer *)putWithNSString:(NSString *)str
                               withInt:(int)start
                               withInt:(int)end;
- (JavaNioCharBuffer *)slice;
- (id<JavaLangCharSequence>)subSequenceFrom:(int)start to:(int)end;
- (NSString *)sequenceDescription;
- (JavaNioCharBuffer *)appendWithChar:(unichar)c;
- (JavaNioCharBuffer *)appendWithJavaLangCharSequence:(id<JavaLangCharSequence>)csq;
- (JavaNioCharBuffer *)appendWithJavaLangCharSequence:(id<JavaLangCharSequence>)csq
                                              withInt:(int)start
                                              withInt:(int)end;
- (int)readWithJavaNioCharBuffer:(JavaNioCharBuffer *)target;
@end

#endif // _JavaNioCharBuffer_H_
