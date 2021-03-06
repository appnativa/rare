//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/CharScanner.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTCharScanner_H_
#define _RAREUTCharScanner_H_

@class IOSCharArray;
@class IOSIntArray;
@class RAREUTCharArray;
@protocol JavaUtilList;
@protocol JavaUtilMap;

#import "JreEmulation.h"
#include "java/io/Reader.h"
#include "java/lang/ThreadLocal.h"

#define RAREUTCharScanner_DQT '"'
#define RAREUTCharScanner_SQT '\''

@interface RAREUTCharScanner : JavaIoReader {
 @public
  unichar foundDelimiter_;
  int currentLen_;
  int currentPos_;
  IOSCharArray *oContent_;
  int originalPos_;
  unichar paramEndChar_;
  unichar paramStartChar_;
  IOSCharArray *theContent_;
  IOSCharArray *theDelims_;
  IOSCharArray *trimChars_;
  BOOL rightTrim_;
  BOOL leftTrim_;
  int markPos_;
  int markLen_;
  unichar QT_;
}

+ (NSString *)emptyString;
+ (JavaLangThreadLocal *)perThreadCharArray;
+ (void)setPerThreadCharArray:(JavaLangThreadLocal *)perThreadCharArray;
+ (JavaLangThreadLocal *)perThreadCharArray2;
+ (void)setPerThreadCharArray2:(JavaLangThreadLocal *)perThreadCharArray2;
+ (JavaLangThreadLocal *)perThreadScanner;
+ (void)setPerThreadScanner:(JavaLangThreadLocal *)perThreadScanner;
+ (IOSCharArray *)lfChars;
- (id)init;
- (id)initWithCharArray:(IOSCharArray *)chars;
- (id)initWithNSString:(NSString *)str;
- (id)initWithCharArray:(IOSCharArray *)chars
            withBoolean:(BOOL)copy_;
- (id)initWithRAREUTCharArray:(RAREUTCharArray *)str
                  withBoolean:(BOOL)copy_;
- (id)initWithCharArray:(IOSCharArray *)chars
                withInt:(int)pos
                withInt:(int)len;
- (id)initWithCharArray:(IOSCharArray *)chars
                withInt:(int)pos
                withInt:(int)len
            withBoolean:(BOOL)copy_;
- (BOOL)backup;
+ (void)charToHTMLStringWithChar:(unichar)c
             withRAREUTCharArray:(RAREUTCharArray *)outArg;
+ (RAREUTCharArray *)charToHexStringWithChar:(unichar)c
                         withRAREUTCharArray:(RAREUTCharArray *)ca;
+ (RAREUTCharArray *)charToUnicodeStringWithChar:(unichar)c
                             withRAREUTCharArray:(RAREUTCharArray *)ca
                                     withBoolean:(BOOL)add_u;
- (void)chopWithInt:(int)num;
+ (NSString *)cleanQuotedWithNSString:(NSString *)str;
+ (RAREUTCharArray *)cleanQuotedWithNSString:(NSString *)str
                         withRAREUTCharArray:(RAREUTCharArray *)ret;
+ (NSString *)cleanQuotedWithCharArray:(IOSCharArray *)chars
                               withInt:(int)pos
                               withInt:(int)len;
+ (RAREUTCharArray *)cleanQuotedWithCharArray:(IOSCharArray *)chars
                                      withInt:(int)pos
                                      withInt:(int)len
                          withRAREUTCharArray:(RAREUTCharArray *)ret;
+ (int)cleanQuotedExWithCharArray:(IOSCharArray *)chars
                          withInt:(int)pos
                          withInt:(int)len;
+ (int)cleanQuotedExWithCharArray:(IOSCharArray *)chars
                          withInt:(int)pos
                          withInt:(int)len
                      withBoolean:(BOOL)flatten;
- (void)clear;
- (void)close;
- (int)consumeWithInt:(int)len;
+ (int)countTokensWithNSString:(NSString *)s
                      withChar:(unichar)c;
+ (NSString *)encodeWithNSString:(NSString *)str;
+ (RAREUTCharArray *)encodeWithNSString:(NSString *)str
                    withRAREUTCharArray:(RAREUTCharArray *)outArg;
+ (void)encodeWithCharArray:(IOSCharArray *)chars
                    withInt:(int)pos
                    withInt:(int)len
        withRAREUTCharArray:(RAREUTCharArray *)outArg;
+ (NSString *)escapeWithNSString:(NSString *)s;
+ (RAREUTCharArray *)escapeWithNSString:(NSString *)s
                            withBoolean:(BOOL)do_quote
                    withRAREUTCharArray:(RAREUTCharArray *)outArg;
+ (RAREUTCharArray *)escapeWithCharArray:(IOSCharArray *)chars
                                 withInt:(int)pos
                                 withInt:(int)len
                             withBoolean:(BOOL)do_quote
                     withRAREUTCharArray:(RAREUTCharArray *)outArg;
- (IOSIntArray *)findTokenWithChar:(unichar)c;
- (IOSIntArray *)findTokenWithBoolean:(BOOL)checkQuote
                          withBoolean:(BOOL)checkParam;
- (IOSIntArray *)findTokenWithChar:(unichar)c
                       withBoolean:(BOOL)checkQuote
                       withBoolean:(BOOL)checkParam;
- (IOSIntArray *)findTokenWithInt:(int)pos
                          withInt:(int)len
                         withChar:(unichar)c;
+ (IOSIntArray *)findTokenWithCharArray:(IOSCharArray *)chars
                               withChar:(unichar)c
                            withBoolean:(BOOL)checkQuote
                            withBoolean:(BOOL)checkParam;
+ (IOSIntArray *)findTokenWithCharArray:(IOSCharArray *)chars
                                withInt:(int)pos
                                withInt:(int)len
                               withChar:(unichar)c;
- (IOSIntArray *)findTokenWithInt:(int)pos
                          withInt:(int)len
                      withBoolean:(BOOL)checkQuote
                      withBoolean:(BOOL)checkParam;
+ (IOSIntArray *)findTokenWithCharArray:(IOSCharArray *)chars
                                withInt:(int)pos
                                withInt:(int)len
                               withChar:(unichar)c
                            withBoolean:(BOOL)checkQuote
                            withBoolean:(BOOL)checkParam;
+ (IOSIntArray *)findTokenWithCharArray:(IOSCharArray *)chars
                                withInt:(int)pos
                                withInt:(int)len
                          withCharArray:(IOSCharArray *)delims
                            withBoolean:(BOOL)checkQuote
                            withBoolean:(BOOL)checkParam;
- (IOSIntArray *)findTokenAndTrimWithChar:(unichar)c;
- (int)indexOfWithChar:(unichar)c;
- (int)indexOfWithChar:(unichar)c
           withBoolean:(BOOL)checkQuote
           withBoolean:(BOOL)checkParam;
- (int)indexOfWithCharArray:(IOSCharArray *)chars
                withBoolean:(BOOL)checkQuote
                withBoolean:(BOOL)checkParam
                withBoolean:(BOOL)ignorecase;
+ (int)indexOfWithCharArray:(IOSCharArray *)chars
                    withInt:(int)pos
                    withInt:(int)len
                   withChar:(unichar)c;
+ (int)indexOfWithCharArray:(IOSCharArray *)chars
                    withInt:(int)pos
                    withInt:(int)len
                   withChar:(unichar)c
                withBoolean:(BOOL)checkQuote
                withBoolean:(BOOL)checkParam;
+ (int)indexOfWithCharArray:(IOSCharArray *)chars
                    withInt:(int)pos
                    withInt:(int)len
              withCharArray:(IOSCharArray *)a
                withBoolean:(BOOL)checkQuote
                withBoolean:(BOOL)ignorecase;
+ (int)indexOfWithCharArray:(IOSCharArray *)chars
                    withInt:(int)pos
                    withInt:(int)len
                   withChar:(unichar)c
                withBoolean:(BOOL)checkQuote
                withBoolean:(BOOL)checkParam
                   withChar:(unichar)ps
                   withChar:(unichar)pe;
+ (int)indexOfWithCharArray:(IOSCharArray *)chars
                    withInt:(int)pos
                    withInt:(int)len
              withCharArray:(IOSCharArray *)a
                withBoolean:(BOOL)checkQuote
                withBoolean:(BOOL)checkParam
                   withChar:(unichar)ps
                   withChar:(unichar)pe
                withBoolean:(BOOL)ignorecase;
- (int)indexOfNonWhiteSpaceWithInt:(int)start;
- (int)lastIndexOfWithChar:(unichar)c
               withBoolean:(BOOL)checkQuote
               withBoolean:(BOOL)checkParam;
+ (int)lastIndexOfWithCharArray:(IOSCharArray *)chars
                        withInt:(int)pos
                        withInt:(int)len
                       withChar:(unichar)c;
+ (int)lastIndexOfWithCharArray:(IOSCharArray *)chars
                        withInt:(int)pos
                        withInt:(int)len
                       withChar:(unichar)c
                    withBoolean:(BOOL)checkQuote
                    withBoolean:(BOOL)checkParam;
+ (int)lastIndexOfWithCharArray:(IOSCharArray *)chars
                        withInt:(int)pos
                        withInt:(int)len
                       withChar:(unichar)c
                    withBoolean:(BOOL)checkQuote
                    withBoolean:(BOOL)checkParam
                       withChar:(unichar)ps
                       withChar:(unichar)pe;
- (void)markWithInt:(int)readAheadLimit;
- (BOOL)markSupported;
- (NSString *)nextToken;
- (NSString *)nextTokenWithChar:(unichar)c;
- (NSString *)nextTokenWithBoolean:(BOOL)checkQuote
                       withBoolean:(BOOL)checkParam;
- (NSString *)nextTokenWithChar:(unichar)c
                    withBoolean:(BOOL)trim;
- (NSString *)nextTokenWithChar:(unichar)c
                    withBoolean:(BOOL)checkQuote
                    withBoolean:(BOOL)checkParam;
- (NSString *)nextTokenWithChar:(unichar)c
                    withBoolean:(BOOL)checkQuote
                    withBoolean:(BOOL)checkParam
                    withBoolean:(BOOL)trim;
- (NSString *)nextTokenWithChar:(unichar)c
                    withBoolean:(BOOL)checkQuote
                    withBoolean:(BOOL)checkParam
                    withBoolean:(BOOL)trim
                    withBoolean:(BOOL)unquote;
+ (id<JavaUtilMap>)parseOptionStringWithNSString:(NSString *)value
                                        withChar:(unichar)c;
+ (id<JavaUtilMap>)parseOptionStringWithNSString:(NSString *)value
                                 withJavaUtilMap:(id<JavaUtilMap>)outArg
                                        withChar:(unichar)c
                                     withBoolean:(BOOL)unquote;
+ (id<JavaUtilMap>)parseOptionStringExWithNSString:(NSString *)value
                                          withChar:(unichar)c;
+ (id<JavaUtilMap>)parseOptionStringExWithNSString:(NSString *)value
                                          withChar:(unichar)c
                                       withBoolean:(BOOL)unquote;
- (int)read;
- (int)readWithCharArray:(IOSCharArray *)chars
                 withInt:(int)pos
                 withInt:(int)len;
- (IOSCharArray *)readCharsWithInt:(int)len;
- (NSString *)readStringWithInt:(int)len;
- (void)reset;
- (void)resetWithCharArray:(IOSCharArray *)chars;
- (void)resetWithNSString:(NSString *)str;
- (void)resetWithRAREUTCharArray:(RAREUTCharArray *)ca
                     withBoolean:(BOOL)copy_;
- (void)resetWithNSString:(NSString *)str
                  withInt:(int)pos
                  withInt:(int)len;
- (void)resetWithCharArray:(IOSCharArray *)chars
                   withInt:(int)pos
                   withInt:(int)len
               withBoolean:(BOOL)copy_;
- (long long int)skipWithLong:(long long int)s;
- (BOOL)skipTokenWithChar:(unichar)c;
- (BOOL)startsWithWithCharArray:(IOSCharArray *)prefix;
- (BOOL)startsWithWithNSString:(NSString *)prefix;
- (IOSIntArray *)stripWithIntArray:(IOSIntArray *)tok
                     withCharArray:(IOSCharArray *)bad;
- (IOSIntArray *)stripWithCharArray:(IOSCharArray *)chars
                       withIntArray:(IOSIntArray *)tok
                      withCharArray:(IOSCharArray *)bad;
+ (IOSIntArray *)stripWithCharArray:(IOSCharArray *)chars
                            withInt:(int)pos
                            withInt:(int)len
                      withCharArray:(IOSCharArray *)bad
                        withBoolean:(BOOL)left
                        withBoolean:(BOOL)right;
- (RAREUTCharScanner *)toLowerCase;
- (RAREUTCharScanner *)toLowerCaseWithIntArray:(IOSIntArray *)tok;
- (NSString *)description;
- (RAREUTCharScanner *)toUpperCase;
- (RAREUTCharScanner *)toUpperCaseWithIntArray:(IOSIntArray *)tok;
- (RAREUTCharScanner *)trim;
- (IOSIntArray *)trimWithIntArray:(IOSIntArray *)tok;
- (IOSIntArray *)trimWithCharArray:(IOSCharArray *)chars
                      withIntArray:(IOSIntArray *)tok;
+ (IOSIntArray *)trimWithCharArray:(IOSCharArray *)chars
                           withInt:(int)pos
                           withInt:(int)len
                       withBoolean:(BOOL)left
                       withBoolean:(BOOL)right;
- (IOSIntArray *)trimEx;
- (RAREUTCharScanner *)unesacpe;
+ (NSString *)unescapeWithNSString:(NSString *)s;
+ (NSString *)unescapeExWithCharArray:(IOSCharArray *)chars
                              withInt:(int)pos
                              withInt:(int)len;
+ (int)unescapeStringExWithCharArray:(IOSCharArray *)chars
                             withInt:(int)pos
                             withInt:(int)len;
+ (unichar)unicodeStringToCharWithCharArray:(IOSCharArray *)chars
                                    withInt:(int)pos;
- (RAREUTCharScanner *)unquoteWithBoolean:(BOOL)trim;
- (IOSIntArray *)unquoteWithIntArray:(IOSIntArray *)tok
                         withBoolean:(BOOL)trim;
- (NSString *)unquoteWithNSString:(NSString *)str
                      withBoolean:(BOOL)trim;
- (void)unreadWithIntArray:(IOSIntArray *)tok;
- (void)setLeftTrimWithBoolean:(BOOL)trim;
- (void)setParameternCharactersWithChar:(unichar)left
                               withChar:(unichar)right;
- (void)setPosAndLengthWithInt:(int)pos
                       withInt:(int)len;
- (void)setQuoteCharWithChar:(unichar)c;
- (void)setRightTrimWithBoolean:(BOOL)trim;
- (void)setTokenDelimitersWithCharArray:(IOSCharArray *)delims;
- (void)setTrimCharsWithCharArray:(IOSCharArray *)chars;
- (int)getCharWithInt:(int)pos;
- (NSString *)getConsumed;
- (IOSCharArray *)getContent;
- (int)getCurrentChar;
- (int)getLastChar;
- (int)getLastCharWithIntArray:(IOSIntArray *)tok;
- (NSString *)getLeftOver;
- (RAREUTCharArray *)getLeftOverCB;
- (void)getLeftOverCBWithRAREUTCharArray:(RAREUTCharArray *)outArg;
- (IOSCharArray *)getLeftOverChars;
- (IOSIntArray *)getLeftOverToken;
- (int)getLength;
+ (int)getLongestLineCharCountWithNSString:(NSString *)str;
- (id<JavaUtilMap>)getOptionsWithChar:(unichar)c
                          withBoolean:(BOOL)unquote;
- (id<JavaUtilMap>)getOptionsWithJavaUtilMap:(id<JavaUtilMap>)outArg
                                    withChar:(unichar)c
                                 withBoolean:(BOOL)unquote;
+ (NSString *)getPieceWithNSString:(NSString *)s
                          withChar:(unichar)tok
                           withInt:(int)pos;
+ (NSString *)getPieceWithNSString:(NSString *)s
                      withNSString:(NSString *)tok
                           withInt:(int)pos;
+ (NSString *)getPieceWithNSString:(NSString *)s
                          withChar:(unichar)tok
                           withInt:(int)start
                           withInt:(int)end;
+ (NSString *)getPieceWithNSString:(NSString *)s
                      withNSString:(NSString *)tok
                           withInt:(int)start
                           withInt:(int)end;
- (int)getPosition;
- (int)getRelPosition;
- (NSString *)getRelStringWithInt:(int)pos
                          withInt:(int)len;
- (NSString *)getStringWithInt:(int)pos
                       withInt:(int)len;
- (NSString *)getTokenWithIntArray:(IOSIntArray *)tok;
- (BOOL)getTokenWithIntArray:(IOSIntArray *)tok
         withRAREUTCharArray:(RAREUTCharArray *)ca
                 withBoolean:(BOOL)trim;
- (IOSCharArray *)getTokenCharsWithIntArray:(IOSIntArray *)tok;
- (id<JavaUtilList>)getTokensWithChar:(unichar)c;
- (id<JavaUtilList>)getTokensWithChar:(unichar)c
                          withBoolean:(BOOL)trim;
- (id<JavaUtilList>)getTokensWithChar:(unichar)c
                          withBoolean:(BOOL)trim
                     withJavaUtilList:(id<JavaUtilList>)list;
+ (id<JavaUtilList>)getTokensWithNSString:(NSString *)str
                                 withChar:(unichar)c
                              withBoolean:(BOOL)trim;
- (id<JavaUtilList>)getTokensWithChar:(unichar)c
                          withBoolean:(BOOL)checkQuote
                          withBoolean:(BOOL)checkParam
                          withBoolean:(BOOL)trim;
+ (id<JavaUtilList>)getTokensWithNSString:(NSString *)str
                                 withChar:(unichar)c
                              withBoolean:(BOOL)trim
                         withJavaUtilList:(id<JavaUtilList>)list;
- (id<JavaUtilList>)getTokensWithChar:(unichar)c
                          withBoolean:(BOOL)checkQuote
                          withBoolean:(BOOL)checkParam
                          withBoolean:(BOOL)trim
                     withJavaUtilList:(id<JavaUtilList>)list;
- (int)getTokenCountWithChar:(unichar)c
                 withBoolean:(BOOL)checkQuote
                 withBoolean:(BOOL)checkParam;
+ (id<JavaUtilList>)getTokensWithNSString:(NSString *)str
                                 withChar:(unichar)c
                              withBoolean:(BOOL)checkQuote
                              withBoolean:(BOOL)checkParam
                              withBoolean:(BOOL)trim;
+ (id<JavaUtilList>)getTokensWithNSString:(NSString *)str
                                 withChar:(unichar)c
                              withBoolean:(BOOL)checkQuote
                              withBoolean:(BOOL)checkParam
                              withBoolean:(BOOL)trim
                         withJavaUtilList:(id<JavaUtilList>)list;
+ (BOOL)isTokenCharWithInt:(int)i
                  withChar:(unichar)c
             withCharArray:(IOSCharArray *)a
                   withInt:(int)len;
+ (unichar)htmlStringToCharacterWithCharArray:(IOSCharArray *)chars
                                      withInt:(int)pos
                                      withInt:(int)len;
+ (id<JavaUtilMap>)parseOptionStringExWithRAREUTCharScanner:(RAREUTCharScanner *)sc
                                            withJavaUtilMap:(id<JavaUtilMap>)outArg
                                                   withChar:(unichar)c
                                                withBoolean:(BOOL)unquote;
+ (void)stripWithIntArray:(IOSIntArray *)tok
            withCharArray:(IOSCharArray *)chars
                  withInt:(int)pos
                  withInt:(int)len
            withCharArray:(IOSCharArray *)bad
              withBoolean:(BOOL)left
              withBoolean:(BOOL)right;
+ (void)trimWithIntArray:(IOSIntArray *)tok
           withCharArray:(IOSCharArray *)chars
                 withInt:(int)pos
                 withInt:(int)len
             withBoolean:(BOOL)left
             withBoolean:(BOOL)right;
- (void)copyAllFieldsTo:(RAREUTCharScanner *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTCharScanner, oContent_, IOSCharArray *)
J2OBJC_FIELD_SETTER(RAREUTCharScanner, theContent_, IOSCharArray *)
J2OBJC_FIELD_SETTER(RAREUTCharScanner, theDelims_, IOSCharArray *)
J2OBJC_FIELD_SETTER(RAREUTCharScanner, trimChars_, IOSCharArray *)

typedef RAREUTCharScanner ComAppnativaUtilCharScanner;

@interface RAREUTCharScanner_$1 : JavaLangThreadLocal {
}

- (id)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

@interface RAREUTCharScanner_$2 : JavaLangThreadLocal {
}

- (id)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

@interface RAREUTCharScanner_$3 : JavaLangThreadLocal {
}

- (id)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

#endif // _RAREUTCharScanner_H_
