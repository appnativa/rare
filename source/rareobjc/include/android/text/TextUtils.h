//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/TextUtils.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _AndroidTextTextUtils_H_
#define _AndroidTextTextUtils_H_

@class IOSCharArray;
@class IOSClass;
@class IOSObjectArray;
@class JavaUtilRegexPattern;
@protocol AndroidTextSpannable;
@protocol AndroidTextSpanned;
@protocol JavaLangCharSequence;

#import "JreEmulation.h"
#include "java/lang/Enum.h"
#include "java/lang/Iterable.h"
#include "java/util/Iterator.h"

#define AndroidTextTextUtils_ABSOLUTE_SIZE_SPAN 16
#define AndroidTextTextUtils_ALIGNMENT_SPAN 1
#define AndroidTextTextUtils_ANNOTATION 18
#define AndroidTextTextUtils_BACKGROUND_COLOR_SPAN 12
#define AndroidTextTextUtils_BULLET_SPAN 8
#define AndroidTextTextUtils_CENTER_SPAN 72
#define AndroidTextTextUtils_CSS_STYLE_SPAN 71
#define AndroidTextTextUtils_FOREGROUND_COLOR_SPAN 2
#define AndroidTextTextUtils_LEADING_MARGIN_SPAN 10
#define AndroidTextTextUtils_QUOTE_SPAN 9
#define AndroidTextTextUtils_RELATIVE_SIZE_SPAN 3
#define AndroidTextTextUtils_SCALE_X_SPAN 4
#define AndroidTextTextUtils_STRIKETHROUGH_SPAN 5
#define AndroidTextTextUtils_STYLE_SPAN 7
#define AndroidTextTextUtils_SUBSCRIPT_SPAN 15
#define AndroidTextTextUtils_SUPERSCRIPT_SPAN 14
#define AndroidTextTextUtils_TEXT_APPEARANCE_SPAN 17
#define AndroidTextTextUtils_TYPEFACE_SPAN 13
#define AndroidTextTextUtils_UNDERLINE_SPAN 6
#define AndroidTextTextUtils_URL_SPAN 11

@interface AndroidTextTextUtils : NSObject {
}

+ (IOSObjectArray *)EMPTY_STRING_ARRAY;
+ (void)setEMPTY_STRING_ARRAY:(IOSObjectArray *)EMPTY_STRING_ARRAY;
+ (int)ALIGNMENT_SPAN;
+ (int)FOREGROUND_COLOR_SPAN;
+ (int)RELATIVE_SIZE_SPAN;
+ (int)SCALE_X_SPAN;
+ (int)STRIKETHROUGH_SPAN;
+ (int)UNDERLINE_SPAN;
+ (int)STYLE_SPAN;
+ (int)BULLET_SPAN;
+ (int)QUOTE_SPAN;
+ (int)LEADING_MARGIN_SPAN;
+ (int)URL_SPAN;
+ (int)BACKGROUND_COLOR_SPAN;
+ (int)TYPEFACE_SPAN;
+ (int)SUPERSCRIPT_SPAN;
+ (int)SUBSCRIPT_SPAN;
+ (int)ABSOLUTE_SIZE_SPAN;
+ (int)TEXT_APPEARANCE_SPAN;
+ (int)ANNOTATION;
+ (int)CSS_STYLE_SPAN;
+ (int)CENTER_SPAN;
+ (id)sLock;
+ (void)setSLock:(id)sLock;
+ (IOSCharArray *)sTemp;
+ (void)setSTemp:(IOSCharArray *)sTemp;
- (id)init;
+ (void)getCharsWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                                 withInt:(int)start
                                 withInt:(int)end
                           withCharArray:(IOSCharArray *)dest
                                 withInt:(int)destoff;
+ (int)indexOfWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                              withChar:(unichar)ch;
+ (int)indexOfWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                              withChar:(unichar)ch
                               withInt:(int)start;
+ (int)indexOfWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                              withChar:(unichar)ch
                               withInt:(int)start
                               withInt:(int)end;
+ (int)lastIndexOfWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                                  withChar:(unichar)ch;
+ (int)lastIndexOfWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                                  withChar:(unichar)ch
                                   withInt:(int)last;
+ (int)lastIndexOfWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                                  withChar:(unichar)ch
                                   withInt:(int)start
                                   withInt:(int)last;
+ (int)indexOfWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
              withJavaLangCharSequence:(id<JavaLangCharSequence>)needle;
+ (int)indexOfWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
              withJavaLangCharSequence:(id<JavaLangCharSequence>)needle
                               withInt:(int)start;
+ (int)indexOfWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
              withJavaLangCharSequence:(id<JavaLangCharSequence>)needle
                               withInt:(int)start
                               withInt:(int)end;
+ (BOOL)regionMatchesWithJavaLangCharSequence:(id<JavaLangCharSequence>)one
                                      withInt:(int)toffset
                     withJavaLangCharSequence:(id<JavaLangCharSequence>)two
                                      withInt:(int)ooffset
                                      withInt:(int)len;
+ (NSString *)substringWithJavaLangCharSequence:(id<JavaLangCharSequence>)source
                                        withInt:(int)start
                                        withInt:(int)end;
+ (NSString *)joinWithJavaLangCharSequence:(id<JavaLangCharSequence>)delimiter
                         withNSObjectArray:(IOSObjectArray *)tokens;
+ (NSString *)joinWithJavaLangCharSequence:(id<JavaLangCharSequence>)delimiter
                      withJavaLangIterable:(id<JavaLangIterable>)tokens;
+ (IOSObjectArray *)splitWithNSString:(NSString *)text
                         withNSString:(NSString *)expression;
+ (IOSObjectArray *)splitWithNSString:(NSString *)text
             withJavaUtilRegexPattern:(JavaUtilRegexPattern *)pattern;
+ (id<JavaLangCharSequence>)stringOrSpannedStringWithJavaLangCharSequence:(id<JavaLangCharSequence>)source;
+ (BOOL)isEmptyWithJavaLangCharSequence:(id<JavaLangCharSequence>)str;
+ (int)getTrimmedLengthWithJavaLangCharSequence:(id<JavaLangCharSequence>)s;
+ (BOOL)equalsWithJavaLangCharSequence:(id<JavaLangCharSequence>)a
              withJavaLangCharSequence:(id<JavaLangCharSequence>)b;
+ (void)copySpansFromWithAndroidTextSpanned:(id<AndroidTextSpanned>)source
                                    withInt:(int)start
                                    withInt:(int)end
                               withIOSClass:(IOSClass *)kind
                   withAndroidTextSpannable:(id<AndroidTextSpannable>)dest
                                    withInt:(int)destoff OBJC_METHOD_FAMILY_NONE;
+ (IOSCharArray *)obtainWithInt:(int)len;
+ (void)recycleWithCharArray:(IOSCharArray *)temp;
+ (NSString *)htmlEncodeWithNSString:(NSString *)s;
+ (id<JavaLangCharSequence>)concatWithJavaLangCharSequenceArray:(IOSObjectArray *)text;
+ (BOOL)isGraphicWithJavaLangCharSequence:(id<JavaLangCharSequence>)str;
+ (BOOL)isGraphicWithChar:(unichar)c;
+ (BOOL)isDigitsOnlyWithJavaLangCharSequence:(id<JavaLangCharSequence>)str;
+ (BOOL)isPrintableAsciiWithChar:(unichar)c;
+ (BOOL)isPrintableAsciiOnlyWithJavaLangCharSequence:(id<JavaLangCharSequence>)str;
+ (BOOL)delimitedStringContainsWithNSString:(NSString *)delimitedString
                                   withChar:(unichar)delimiter
                               withNSString:(NSString *)item;
@end

@protocol AndroidTextTextUtils_StringSplitter < JavaLangIterable, NSObject, JavaObject >
- (void)setStringWithNSString:(NSString *)string;
@end

@interface AndroidTextTextUtils_SimpleStringSplitter : NSObject < AndroidTextTextUtils_StringSplitter, JavaUtilIterator > {
 @public
  NSString *mString_;
  unichar mDelimiter_;
  int mPosition_;
  int mLength_;
}

- (id)initWithChar:(unichar)delimiter;
- (void)setStringWithNSString:(NSString *)string;
- (id<JavaUtilIterator>)iterator;
- (BOOL)hasNext;
- (NSString *)next;
- (void)remove;
- (void)copyAllFieldsTo:(AndroidTextTextUtils_SimpleStringSplitter *)other;
@end

J2OBJC_FIELD_SETTER(AndroidTextTextUtils_SimpleStringSplitter, mString_, NSString *)

typedef enum {
  AndroidTextTextUtils_TruncateAt_START = 0,
  AndroidTextTextUtils_TruncateAt_MIDDLE = 1,
  AndroidTextTextUtils_TruncateAt_END = 2,
  AndroidTextTextUtils_TruncateAt_MARQUEE = 3,
} AndroidTextTextUtils_TruncateAt;

@interface AndroidTextTextUtils_TruncateAtEnum : JavaLangEnum < NSCopying > {
}
+ (AndroidTextTextUtils_TruncateAtEnum *)START;
+ (AndroidTextTextUtils_TruncateAtEnum *)MIDDLE;
+ (AndroidTextTextUtils_TruncateAtEnum *)END;
+ (AndroidTextTextUtils_TruncateAtEnum *)MARQUEE;
+ (IOSObjectArray *)values;
+ (AndroidTextTextUtils_TruncateAtEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _AndroidTextTextUtils_H_
