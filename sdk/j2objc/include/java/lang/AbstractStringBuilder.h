//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/lang/AbstractStringBuilder.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaLangAbstractStringBuilder_H_
#define _JavaLangAbstractStringBuilder_H_

@class IOSCharArray;
@class JavaLangStringIndexOutOfBoundsException;
@protocol JavaLangCharSequence;

#import "JreEmulation.h"

#define JavaLangAbstractStringBuilder_INITIAL_CAPACITY 16

@interface JavaLangAbstractStringBuilder : NSObject {
 @public
  IOSCharArray *value_;
  int count_;
  BOOL shared_;
}

+ (int)INITIAL_CAPACITY;
- (IOSCharArray *)getValue;
- (IOSCharArray *)shareValue;
- (void)setWithCharArray:(IOSCharArray *)val
                 withInt:(int)len;
- (id)init;
- (id)initWithInt:(int)capacity;
- (id)initWithNSString:(NSString *)string;
- (void)enlargeBufferWithInt:(int)min;
- (void)appendNull;
- (void)append0WithCharArray:(IOSCharArray *)chars;
- (void)append0WithCharArray:(IOSCharArray *)chars
                     withInt:(int)offset
                     withInt:(int)length;
- (void)append0WithChar:(unichar)ch;
- (void)append0WithNSString:(NSString *)string;
- (void)append0WithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                                withInt:(int)start
                                withInt:(int)end;
- (int)capacity;
- (unichar)charAtWithInt:(int)index;
- (JavaLangStringIndexOutOfBoundsException *)indexAndLengthWithInt:(int)index;
- (JavaLangStringIndexOutOfBoundsException *)startEndAndLengthWithInt:(int)start
                                                              withInt:(int)end;
- (void)delete0WithInt:(int)start
               withInt:(int)end;
- (void)deleteCharAt0WithInt:(int)index;
- (void)ensureCapacityWithInt:(int)min;
- (void)getCharsWithInt:(int)start
                withInt:(int)end
          withCharArray:(IOSCharArray *)dst
                withInt:(int)dstStart;
- (void)insert0WithInt:(int)index
         withCharArray:(IOSCharArray *)chars;
- (void)insert0WithInt:(int)index
         withCharArray:(IOSCharArray *)chars
               withInt:(int)start
               withInt:(int)length;
- (void)insert0WithInt:(int)index
              withChar:(unichar)ch;
- (void)insert0WithInt:(int)index
          withNSString:(NSString *)string;
- (void)insert0WithInt:(int)index
withJavaLangCharSequence:(id<JavaLangCharSequence>)s
               withInt:(int)start
               withInt:(int)end;
- (int)length;
- (void)moveWithInt:(int)size
            withInt:(int)index;
- (void)replace0WithInt:(int)start
                withInt:(int)end
           withNSString:(NSString *)string;
- (void)reverse0;
- (void)setCharAtWithInt:(int)index
                withChar:(unichar)ch;
- (void)setLengthWithInt:(int)length;
- (NSString *)substringWithInt:(int)start;
- (NSString *)substringWithInt:(int)start
                       withInt:(int)end;
- (NSString *)description;
- (NSString *)toString0;
- (id<JavaLangCharSequence>)subSequenceWithInt:(int)start
                                       withInt:(int)end;
- (int)indexOfWithNSString:(NSString *)string;
- (int)indexOfWithNSString:(NSString *)subString
                   withInt:(int)start;
- (int)lastIndexOfWithNSString:(NSString *)string;
- (int)lastIndexOfWithNSString:(NSString *)subString
                       withInt:(int)start;
- (void)trimToSize;
- (int)codePointAtWithInt:(int)index;
- (int)codePointBeforeWithInt:(int)index;
- (int)codePointCountWithInt:(int)start
                     withInt:(int)end;
- (int)offsetByCodePointsWithInt:(int)index
                         withInt:(int)codePointOffset;
- (void)copyAllFieldsTo:(JavaLangAbstractStringBuilder *)other;
@end

J2OBJC_FIELD_SETTER(JavaLangAbstractStringBuilder, value_, IOSCharArray *)

#endif // _JavaLangAbstractStringBuilder_H_
