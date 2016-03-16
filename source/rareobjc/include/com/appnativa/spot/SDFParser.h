//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SDFParser.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _SDFParser_H_
#define _SDFParser_H_

@class IOSCharArray;
@class JavaIoReader;
@class JavaLangRuntimeException;
@class SDFNode;
@class SDFParser_Tokenizer;
@protocol JavaUtilMap;
@protocol SDFParser_iCallback;

#import "JreEmulation.h"
#include "com/appnativa/util/ReaderTokenizer.h"

#define SDFParser_PARSER_CONTEXT_A_NAME 12
#define SDFParser_PARSER_CONTEXT_A_VALUE 13
#define SDFParser_PARSER_CONTEXT_BODY 3
#define SDFParser_PARSER_CONTEXT_COLON 5
#define SDFParser_PARSER_CONTEXT_COLON_COLON 6
#define SDFParser_PARSER_CONTEXT_COMMAND 2
#define SDFParser_PARSER_CONTEXT_EOA 15
#define SDFParser_PARSER_CONTEXT_EOB 11
#define SDFParser_PARSER_CONTEXT_EOV 10
#define SDFParser_PARSER_CONTEXT_IDENTIFIER 4
#define SDFParser_PARSER_CONTEXT_MODIFIER 14
#define SDFParser_PARSER_CONTEXT_REF_NAME 8
#define SDFParser_PARSER_CONTEXT_REF_VALUE 9
#define SDFParser_PARSER_CONTEXT_ROOT 1
#define SDFParser_PARSER_CONTEXT_TYPE 7

@interface SDFParser : NSObject {
 @public
  SDFParser_Tokenizer *tokenizer_;
  BOOL _stop_;
  NSString *fileName_;
  BOOL ignoreComments_;
}

+ (int)PARSER_CONTEXT_A_NAME;
+ (int)PARSER_CONTEXT_A_VALUE;
+ (int)PARSER_CONTEXT_BODY;
+ (int)PARSER_CONTEXT_COLON;
+ (int)PARSER_CONTEXT_COLON_COLON;
+ (int)PARSER_CONTEXT_COMMAND;
+ (int)PARSER_CONTEXT_EOA;
+ (int)PARSER_CONTEXT_EOB;
+ (int)PARSER_CONTEXT_EOV;
+ (int)PARSER_CONTEXT_IDENTIFIER;
+ (int)PARSER_CONTEXT_MODIFIER;
+ (int)PARSER_CONTEXT_REF_NAME;
+ (int)PARSER_CONTEXT_REF_VALUE;
+ (int)PARSER_CONTEXT_ROOT;
+ (int)PARSER_CONTEXT_TYPE;
+ (IOSCharArray *)defEndHeredoc;
+ (void)setDefEndHeredoc:(IOSCharArray *)defEndHeredoc;
- (id)init;
- (id)initWithJavaIoReader:(JavaIoReader *)r;
- (void)parseWithSDFParser_iCallback:(id<SDFParser_iCallback>)cb;
- (void)stopParsing;
- (void)setFileNameWithNSString:(NSString *)fileName;
- (void)setIgnoreCommentsWithBoolean:(BOOL)ignore;
- (void)setReaderWithJavaIoReader:(JavaIoReader *)r;
- (NSString *)getFileName;
- (int)getTokenizerLineNumber;
- (BOOL)isIgnoreComments;
- (JavaLangRuntimeException *)exceptionWithNSString:(NSString *)s;
- (NSString *)unexpectedWithChar:(unichar)c;
- (NSString *)unexpectedWithNSString:(NSString *)c;
- (NSString *)unexpectedEOF;
- (NSString *)unmatchedWithChar:(unichar)c;
- (void)copyAllFieldsTo:(SDFParser *)other;
@end

J2OBJC_FIELD_SETTER(SDFParser, tokenizer_, SDFParser_Tokenizer *)
J2OBJC_FIELD_SETTER(SDFParser, fileName_, NSString *)

typedef SDFParser ComAppnativaSpotSDFParser;

@protocol SDFParser_iCallback < NSObject, JavaObject >
- (void)addCommentWithNSString:(NSString *)comment;
- (SDFNode *)addValueWithNSString:(NSString *)name
                     withNSString:(NSString *)value
                      withBoolean:(BOOL)preformatted
                     withNSString:(NSString *)pretag
                  withJavaUtilMap:(id<JavaUtilMap>)attributes;
- (SDFNode *)endBlockWithJavaUtilMap:(id<JavaUtilMap>)attributes;
- (void)handleCommandWithNSString:(NSString *)cmd
                     withNSString:(NSString *)params;
- (SDFNode *)startBlockWithNSString:(NSString *)name
                       withNSString:(NSString *)refName
                       withNSString:(NSString *)refValue;
@end

@interface SDFParser_Tokenizer : RAREUTReaderTokenizer {
 @public
  NSString *preTag_;
}

- (id)initWithSDFParser:(SDFParser *)outer$
       withJavaIoReader:(JavaIoReader *)r;
- (void)init__ OBJC_METHOD_FAMILY_NONE;
- (void)parseNumbers;
- (NSString *)readCommandParams;
- (NSString *)readPreformatted;
- (NSString *)readToEOL;
- (void)stripPreformattedWhitespace;
- (void)stripWhitespace;
- (void)copyAllFieldsTo:(SDFParser_Tokenizer *)other;
@end

J2OBJC_FIELD_SETTER(SDFParser_Tokenizer, preTag_, NSString *)

#endif // _SDFParser_H_
