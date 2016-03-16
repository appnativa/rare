//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SDFParser.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSCharArray.h"
#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/spot/SDFNode.h"
#include "com/appnativa/spot/SDFParser.h"
#include "com/appnativa/util/CharArray.h"
#include "com/appnativa/util/CharScanner.h"
#include "com/appnativa/util/ReaderTokenizer.h"
#include "java/io/IOException.h"
#include "java/io/Reader.h"
#include "java/lang/Integer.h"
#include "java/lang/RuntimeException.h"
#include "java/lang/StringBuilder.h"
#include "java/text/ParseException.h"
#include "java/util/LinkedHashMap.h"
#include "java/util/Map.h"

@implementation SDFParser

static IOSCharArray * SDFParser_defEndHeredoc_;

+ (int)PARSER_CONTEXT_A_NAME {
  return SDFParser_PARSER_CONTEXT_A_NAME;
}

+ (int)PARSER_CONTEXT_A_VALUE {
  return SDFParser_PARSER_CONTEXT_A_VALUE;
}

+ (int)PARSER_CONTEXT_BODY {
  return SDFParser_PARSER_CONTEXT_BODY;
}

+ (int)PARSER_CONTEXT_COLON {
  return SDFParser_PARSER_CONTEXT_COLON;
}

+ (int)PARSER_CONTEXT_COLON_COLON {
  return SDFParser_PARSER_CONTEXT_COLON_COLON;
}

+ (int)PARSER_CONTEXT_COMMAND {
  return SDFParser_PARSER_CONTEXT_COMMAND;
}

+ (int)PARSER_CONTEXT_EOA {
  return SDFParser_PARSER_CONTEXT_EOA;
}

+ (int)PARSER_CONTEXT_EOB {
  return SDFParser_PARSER_CONTEXT_EOB;
}

+ (int)PARSER_CONTEXT_EOV {
  return SDFParser_PARSER_CONTEXT_EOV;
}

+ (int)PARSER_CONTEXT_IDENTIFIER {
  return SDFParser_PARSER_CONTEXT_IDENTIFIER;
}

+ (int)PARSER_CONTEXT_MODIFIER {
  return SDFParser_PARSER_CONTEXT_MODIFIER;
}

+ (int)PARSER_CONTEXT_REF_NAME {
  return SDFParser_PARSER_CONTEXT_REF_NAME;
}

+ (int)PARSER_CONTEXT_REF_VALUE {
  return SDFParser_PARSER_CONTEXT_REF_VALUE;
}

+ (int)PARSER_CONTEXT_ROOT {
  return SDFParser_PARSER_CONTEXT_ROOT;
}

+ (int)PARSER_CONTEXT_TYPE {
  return SDFParser_PARSER_CONTEXT_TYPE;
}

+ (IOSCharArray *)defEndHeredoc {
  return SDFParser_defEndHeredoc_;
}

+ (void)setDefEndHeredoc:(IOSCharArray *)defEndHeredoc {
  SDFParser_defEndHeredoc_ = defEndHeredoc;
}

- (id)init {
  if (self = [super init]) {
    tokenizer_ = nil;
    fileName_ = nil;
    ignoreComments_ = YES;
    _stop_ = NO;
  }
  return self;
}

- (id)initWithJavaIoReader:(JavaIoReader *)r {
  if (self = [super init]) {
    tokenizer_ = [[SDFParser_Tokenizer alloc] initWithSDFParser:self withJavaIoReader:r];
    fileName_ = nil;
    ignoreComments_ = YES;
    _stop_ = NO;
  }
  return self;
}

- (void)parseWithSDFParser_iCallback:(id<SDFParser_iCallback>)cb {
  int ctx = SDFParser_PARSER_CONTEXT_ROOT;
  int tok = 0;
  SDFParser_Tokenizer *tz = tokenizer_;
  NSString *identifier = nil;
  NSString *refName = nil;
  NSString *refValue = nil;
  NSString *value = nil;
  id<JavaUtilMap> attributes = nil;
  int depth = 0;
  BOOL preformatted = NO;
  BOOL blockend = NO;
  NSString *aname = nil;
  NSString *comment = nil;
  NSString *pretag = nil;
  SDFNode *node;
  _stop_ = NO;
  while (!_stop_ && (tok = [((SDFParser_Tokenizer *) nil_chk(tz)) nextToken]) != RAREUTReaderTokenizer_TT_EOF) {
    switch (tok) {
      case RAREUTReaderTokenizer_TT_COMMENT:
      comment = ((SDFParser_Tokenizer *) nil_chk(tz))->sval_;
      case RAREUTReaderTokenizer_TT_EOL:
      switch (ctx) {
        case SDFParser_PARSER_CONTEXT_COMMAND:
        ctx = SDFParser_PARSER_CONTEXT_ROOT;
        if (comment != nil) {
          [((id<SDFParser_iCallback>) nil_chk(cb)) addCommentWithNSString:comment];
          comment = nil;
        }
        break;
        case SDFParser_PARSER_CONTEXT_BODY:
        if (comment != nil) {
          [((id<SDFParser_iCallback>) nil_chk(cb)) addCommentWithNSString:comment];
          comment = nil;
        }
        break;
        case SDFParser_PARSER_CONTEXT_TYPE:
        case SDFParser_PARSER_CONTEXT_EOA:
        case SDFParser_PARSER_CONTEXT_EOB:
        case SDFParser_PARSER_CONTEXT_EOV:
        node = nil;
        if (blockend) {
          node = [((id<SDFParser_iCallback>) nil_chk(cb)) endBlockWithJavaUtilMap:attributes];
        }
        else {
          if (value == nil) {
            if ((identifier == nil) && (comment != nil)) {
              [((id<SDFParser_iCallback>) nil_chk(cb)) addCommentWithNSString:comment];
            }
            node = [((id<SDFParser_iCallback>) nil_chk(cb)) addValueWithNSString:nil withNSString:identifier withBoolean:preformatted withNSString:pretag withJavaUtilMap:attributes];
          }
          else {
            node = [((id<SDFParser_iCallback>) nil_chk(cb)) addValueWithNSString:identifier withNSString:value withBoolean:preformatted withNSString:pretag withJavaUtilMap:attributes];
          }
        }
        if ((comment != nil) && (node != nil)) {
          [node setNodeCommentWithNSString:comment];
        }
        comment = nil;
        refName = nil;
        refValue = nil;
        identifier = nil;
        value = nil;
        ctx = SDFParser_PARSER_CONTEXT_BODY;
        preformatted = NO;
        aname = nil;
        blockend = NO;
        attributes = nil;
        if (depth == 0) {
          _stop_ = YES;
        }
        break;
      }
      break;
      case RAREUTReaderTokenizer_TT_WORD:
      case '\'':
      case '"':
      switch (ctx) {
        case SDFParser_PARSER_CONTEXT_COMMAND:
        identifier = ((SDFParser_Tokenizer *) nil_chk(tz))->sval_;
        value = [tz readCommandParams];
        [((id<SDFParser_iCallback>) nil_chk(cb)) handleCommandWithNSString:identifier withNSString:value];
        identifier = nil;
        value = nil;
        ctx = SDFParser_PARSER_CONTEXT_ROOT;
        break;
        case SDFParser_PARSER_CONTEXT_ROOT:
        case SDFParser_PARSER_CONTEXT_BODY:
        identifier = ((SDFParser_Tokenizer *) nil_chk(tz))->sval_;
        ctx = SDFParser_PARSER_CONTEXT_TYPE;
        break;
        case SDFParser_PARSER_CONTEXT_TYPE:
        value = ((SDFParser_Tokenizer *) nil_chk(tz))->sval_;
        ctx = SDFParser_PARSER_CONTEXT_EOV;
        break;
        case SDFParser_PARSER_CONTEXT_REF_NAME:
        if ((tok == '\'') || (tok == '"')) {
          @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
        }
        refName = ((SDFParser_Tokenizer *) nil_chk(tz))->sval_;
        ctx = SDFParser_PARSER_CONTEXT_TYPE;
        break;
        case SDFParser_PARSER_CONTEXT_REF_VALUE:
        refValue = ((SDFParser_Tokenizer *) nil_chk(tz))->sval_;
        ctx = SDFParser_PARSER_CONTEXT_TYPE;
        if ((tok == '\'') || (tok == '"')) {
          refValue = [NSString stringWithFormat:@"'%@'", refValue];
        }
        break;
        case SDFParser_PARSER_CONTEXT_A_NAME:
        if ((tok == '\'') || (tok == '"')) {
          @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
        }
        aname = ((SDFParser_Tokenizer *) nil_chk(tz))->sval_;
        ctx = SDFParser_PARSER_CONTEXT_A_VALUE;
        break;
        case SDFParser_PARSER_CONTEXT_A_VALUE:
        if (aname == nil) {
          (void) [self unexpectedWithNSString:((SDFParser_Tokenizer *) nil_chk(tz))->sval_];
        }
        if (attributes == nil) {
          attributes = [[JavaUtilLinkedHashMap alloc] init];
        }
        (void) [((id<JavaUtilMap>) nil_chk(attributes)) putWithId:aname withId:((SDFParser_Tokenizer *) nil_chk(tz))->sval_];
        aname = nil;
        ctx = SDFParser_PARSER_CONTEXT_A_NAME;
        break;
      }
      break;
      case ':':
      switch (ctx) {
        case SDFParser_PARSER_CONTEXT_BODY:
        case SDFParser_PARSER_CONTEXT_TYPE:
        ctx = SDFParser_PARSER_CONTEXT_TYPE;
        break;
        default:
        @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
      }
      break;
      case ';':
      switch (ctx) {
        case SDFParser_PARSER_CONTEXT_TYPE:
        case SDFParser_PARSER_CONTEXT_EOV:
        case SDFParser_PARSER_CONTEXT_EOB:
        case SDFParser_PARSER_CONTEXT_EOA:
        if (blockend) {
          (void) [((id<SDFParser_iCallback>) nil_chk(cb)) endBlockWithJavaUtilMap:attributes];
        }
        else {
          if ((ctx == SDFParser_PARSER_CONTEXT_TYPE) || (value == nil)) {
            (void) [((id<SDFParser_iCallback>) nil_chk(cb)) addValueWithNSString:nil withNSString:identifier withBoolean:preformatted withNSString:pretag withJavaUtilMap:attributes];
          }
          else {
            (void) [((id<SDFParser_iCallback>) nil_chk(cb)) addValueWithNSString:identifier withNSString:value withBoolean:preformatted withNSString:pretag withJavaUtilMap:attributes];
          }
        }
        refName = nil;
        refValue = nil;
        identifier = nil;
        value = nil;
        ctx = SDFParser_PARSER_CONTEXT_BODY;
        preformatted = NO;
        aname = nil;
        blockend = NO;
        attributes = nil;
        break;
        case SDFParser_PARSER_CONTEXT_A_NAME:
        case SDFParser_PARSER_CONTEXT_A_VALUE:
        @throw [self exceptionWithNSString:[self unmatchedWithChar:']']];
        default:
        @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
      }
      break;
      case '<':
      switch ([((SDFParser_Tokenizer *) nil_chk(tz)) nextToken]) {
        case '<':
        if (ctx == SDFParser_PARSER_CONTEXT_TYPE) {
          value = [tz readPreformatted];
          ctx = SDFParser_PARSER_CONTEXT_EOV;
          preformatted = YES;
          pretag = tz->preTag_;
          break;
        }
        default:
        @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
      }
      break;
      case '>':
      @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
      case '{':
      switch (ctx) {
        case SDFParser_PARSER_CONTEXT_ROOT:
        case SDFParser_PARSER_CONTEXT_BODY:
        case SDFParser_PARSER_CONTEXT_TYPE:
        if (comment != nil) {
          [((id<SDFParser_iCallback>) nil_chk(cb)) addCommentWithNSString:comment];
          comment = nil;
        }
        (void) [((id<SDFParser_iCallback>) nil_chk(cb)) startBlockWithNSString:identifier withNSString:refName withNSString:refValue];
        depth++;
        refName = nil;
        refValue = nil;
        identifier = nil;
        value = nil;
        ctx = SDFParser_PARSER_CONTEXT_BODY;
        preformatted = NO;
        aname = nil;
        blockend = NO;
        attributes = nil;
        comment = nil;
        break;
        default:
        @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
      }
      break;
      case '}':
      switch (ctx) {
        case SDFParser_PARSER_CONTEXT_BODY:
        depth--;
        if (depth < 0) {
          @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
        }
        ctx = SDFParser_PARSER_CONTEXT_EOB;
        blockend = YES;
        break;
        case SDFParser_PARSER_CONTEXT_EOV:
        case SDFParser_PARSER_CONTEXT_EOA:
        case SDFParser_PARSER_CONTEXT_TYPE:
        case SDFParser_PARSER_CONTEXT_EOB:
        if (blockend) {
          (void) [((id<SDFParser_iCallback>) nil_chk(cb)) endBlockWithJavaUtilMap:attributes];
        }
        else {
          if ((ctx == SDFParser_PARSER_CONTEXT_TYPE) || (value == nil)) {
            (void) [((id<SDFParser_iCallback>) nil_chk(cb)) addValueWithNSString:nil withNSString:identifier withBoolean:preformatted withNSString:pretag withJavaUtilMap:attributes];
          }
          else {
            (void) [((id<SDFParser_iCallback>) nil_chk(cb)) addValueWithNSString:identifier withNSString:value withBoolean:preformatted withNSString:pretag withJavaUtilMap:attributes];
          }
        }
        refName = nil;
        refValue = nil;
        identifier = nil;
        value = nil;
        preformatted = NO;
        aname = nil;
        depth--;
        if (depth < 0) {
          @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
        }
        ctx = SDFParser_PARSER_CONTEXT_EOB;
        blockend = YES;
        attributes = nil;
        break;
        default:
        @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
      }
      break;
      case ',':
      switch (ctx) {
        case SDFParser_PARSER_CONTEXT_A_NAME:
        if (aname != nil) {
          if (attributes == nil) {
            attributes = [[JavaUtilLinkedHashMap alloc] init];
          }
          (void) [((id<JavaUtilMap>) nil_chk(attributes)) putWithId:aname withId:nil];
        }
        aname = nil;
        break;
        default:
        @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
      }
      break;
      case '[':
      switch (ctx) {
        case SDFParser_PARSER_CONTEXT_TYPE:
        case SDFParser_PARSER_CONTEXT_EOV:
        case SDFParser_PARSER_CONTEXT_EOB:
        ctx = SDFParser_PARSER_CONTEXT_A_NAME;
        aname = nil;
        break;
        default:
        @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
      }
      break;
      case ']':
      switch (ctx) {
        case SDFParser_PARSER_CONTEXT_A_NAME:
        ctx = SDFParser_PARSER_CONTEXT_EOA;
        if (aname != nil) {
          if (attributes == nil) {
            attributes = [[JavaUtilLinkedHashMap alloc] init];
          }
          (void) [((id<JavaUtilMap>) nil_chk(attributes)) putWithId:aname withId:nil];
          ctx = SDFParser_PARSER_CONTEXT_EOA;
        }
        aname = nil;
        break;
        default:
        @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
      }
      break;
      case '$':
      switch (ctx) {
        case SDFParser_PARSER_CONTEXT_TYPE:
        ctx = SDFParser_PARSER_CONTEXT_REF_VALUE;
        refName = ((SDFParser_Tokenizer *) nil_chk(tz))->sval_;
        break;
        default:
        @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
      }
      break;
      case '/':
      case '@':
      switch (ctx) {
        case SDFParser_PARSER_CONTEXT_ROOT:
        ctx = SDFParser_PARSER_CONTEXT_COMMAND;
        break;
        case SDFParser_PARSER_CONTEXT_TYPE:
        ctx = SDFParser_PARSER_CONTEXT_REF_NAME;
        refName = ((SDFParser_Tokenizer *) nil_chk(tz))->sval_;
        break;
        default:
        @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedWithChar:(unichar) tok], ctx]];
      }
      break;
    }
  }
  switch (ctx) {
    case SDFParser_PARSER_CONTEXT_COMMAND:
    break;
    case SDFParser_PARSER_CONTEXT_TYPE:
    case SDFParser_PARSER_CONTEXT_EOV:
    case SDFParser_PARSER_CONTEXT_EOA:
    case SDFParser_PARSER_CONTEXT_EOB:
    if (blockend) {
      (void) [((id<SDFParser_iCallback>) nil_chk(cb)) endBlockWithJavaUtilMap:attributes];
    }
    else {
      (void) [((id<SDFParser_iCallback>) nil_chk(cb)) addValueWithNSString:identifier withNSString:value withBoolean:preformatted withNSString:pretag withJavaUtilMap:attributes];
      @throw [self exceptionWithNSString:[self unexpectedEOF]];
    }
    break;
  }
  if (depth != 0) {
    @throw [self exceptionWithNSString:[NSString stringWithFormat:@"%@ ctx=%d", [self unexpectedEOF], ctx]];
  }
}

- (void)stopParsing {
  _stop_ = YES;
}

- (void)setFileNameWithNSString:(NSString *)fileName {
  self->fileName_ = fileName;
}

- (void)setIgnoreCommentsWithBoolean:(BOOL)ignore {
  self->ignoreComments_ = ignore;
  [((SDFParser_Tokenizer *) nil_chk(tokenizer_)) setReturnCommentsWithBoolean:!ignore];
}

- (void)setReaderWithJavaIoReader:(JavaIoReader *)r {
  tokenizer_ = [[SDFParser_Tokenizer alloc] initWithSDFParser:self withJavaIoReader:r];
}

- (NSString *)getFileName {
  return fileName_;
}

- (int)getTokenizerLineNumber {
  return [((SDFParser_Tokenizer *) nil_chk(tokenizer_)) lineno];
}

- (BOOL)isIgnoreComments {
  return ignoreComments_;
}

- (JavaLangRuntimeException *)exceptionWithNSString:(NSString *)s {
  if (fileName_ != nil) {
    s = [NSString stringWithFormat:@"%@%@", s, [NSString stringWithFormat:@" (%@)", fileName_]];
  }
  return [[JavaLangRuntimeException alloc] initWithNSString:s];
}

- (NSString *)unexpectedWithChar:(unichar)c {
  return [NSString formatWithNSString:@"Unexpected character '%s' on line #%d" withNSObjectArray:[IOSObjectArray arrayWithObjects:(id[]){ [NSString valueOfChar:c], [JavaLangInteger valueOfWithInt:[((SDFParser_Tokenizer *) nil_chk(tokenizer_)) lineno]] } count:2 type:[IOSClass classWithClass:[NSObject class]]]];
}

- (NSString *)unexpectedWithNSString:(NSString *)c {
  return [NSString formatWithNSString:@"Unexpected identifier '%s' on line #%d" withNSObjectArray:[IOSObjectArray arrayWithObjects:(id[]){ c, [JavaLangInteger valueOfWithInt:[((SDFParser_Tokenizer *) nil_chk(tokenizer_)) lineno]] } count:2 type:[IOSClass classWithClass:[NSObject class]]]];
}

- (NSString *)unexpectedEOF {
  return @"Unexpected end of file reached";
}

- (NSString *)unmatchedWithChar:(unichar)c {
  return [NSString formatWithNSString:@"Unmatched '%s' found on line #%d" withNSObjectArray:[IOSObjectArray arrayWithObjects:(id[]){ [NSString valueOfChar:c], [JavaLangInteger valueOfWithInt:[((SDFParser_Tokenizer *) nil_chk(tokenizer_)) lineno]] } count:2 type:[IOSClass classWithClass:[NSObject class]]]];
}

+ (void)initialize {
  if (self == [SDFParser class]) {
    SDFParser_defEndHeredoc_ = [IOSCharArray arrayWithCharacters:(unichar[]){ '>', '>' } count:2];
  }
}

- (void)copyAllFieldsTo:(SDFParser *)other {
  [super copyAllFieldsTo:other];
  other->_stop_ = _stop_;
  other->fileName_ = fileName_;
  other->ignoreComments_ = ignoreComments_;
  other->tokenizer_ = tokenizer_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "parseWithSDFParser_iCallback:", NULL, "V", 0x1, "JavaIoIOException" },
    { "getFileName", NULL, "LNSString", 0x1, NULL },
    { "isIgnoreComments", NULL, "Z", 0x1, NULL },
    { "exceptionWithNSString:", NULL, "LJavaLangRuntimeException", 0x0, NULL },
    { "unexpectedWithChar:", NULL, "LNSString", 0x0, NULL },
    { "unexpectedWithNSString:", NULL, "LNSString", 0x0, NULL },
    { "unexpectedEOF", NULL, "LNSString", 0x0, NULL },
    { "unmatchedWithChar:", NULL, "LNSString", 0x0, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "PARSER_CONTEXT_A_NAME_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_A_VALUE_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_BODY_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_COLON_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_COLON_COLON_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_COMMAND_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_EOA_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_EOB_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_EOV_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_IDENTIFIER_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_MODIFIER_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_REF_NAME_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_REF_VALUE_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_ROOT_", NULL, 0x19, "I" },
    { "PARSER_CONTEXT_TYPE_", NULL, 0x19, "I" },
    { "defEndHeredoc_", NULL, 0xa, "LIOSCharArray" },
    { "tokenizer_", NULL, 0x0, "LSDFParser_Tokenizer" },
  };
  static J2ObjcClassInfo _SDFParser = { "SDFParser", "com.appnativa.spot", NULL, 0x1, 8, methods, 17, fields, 0, NULL};
  return &_SDFParser;
}

@end

@interface SDFParser_iCallback : NSObject
@end

@implementation SDFParser_iCallback

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addCommentWithNSString:", NULL, "V", 0x401, NULL },
    { "addValueWithNSString:withNSString:withBoolean:withNSString:withJavaUtilMap:", NULL, "LSDFNode", 0x401, NULL },
    { "endBlockWithJavaUtilMap:", NULL, "LSDFNode", 0x401, NULL },
    { "handleCommandWithNSString:withNSString:", NULL, "V", 0x401, NULL },
    { "startBlockWithNSString:withNSString:withNSString:", NULL, "LSDFNode", 0x401, NULL },
  };
  static J2ObjcClassInfo _SDFParser_iCallback = { "iCallback", "com.appnativa.spot", "SDFParser", 0x209, 5, methods, 0, NULL, 0, NULL};
  return &_SDFParser_iCallback;
}

@end
@implementation SDFParser_Tokenizer

- (id)initWithSDFParser:(SDFParser *)outer$
       withJavaIoReader:(JavaIoReader *)r {
  if (self = [super initWithJavaIoReader:r]) {
    [self wordCharsWithInt:33 withInt:33];
    [self wordCharsWithInt:35 withInt:35];
    [self wordCharsWithInt:37 withInt:38];
    [self wordCharsWithInt:40 withInt:43];
    [self wordCharsWithInt:45 withInt:46];
    [self wordCharsWithInt:48 withInt:57];
    [self wordCharsWithInt:63 withInt:63];
    [self wordCharsWithInt:95 withInt:95];
    [self wordCharsWithInt:124 withInt:124];
    [self wordCharsWithInt:126 withInt:126];
    [self eolIsSignificantWithBoolean:YES];
    [self slashSlashCommentsWithBoolean:YES];
    [self slashStarCommentsWithBoolean:YES];
  }
  return self;
}

- (void)init__ {
  [self wordCharsWithInt:'a' withInt:'z'];
  [self wordCharsWithInt:'A' withInt:'Z'];
  [self wordCharsWithInt:128 + 32 withInt:255];
  [self whitespaceCharsWithInt:0 withInt:' '];
  [self commentCharWithInt:'/'];
  [self quoteCharWithInt:'"'];
  [self quoteCharWithInt:'\''];
  [self wordCharsWithInt:33 withInt:33];
  [self wordCharsWithInt:35 withInt:35];
  [self wordCharsWithInt:37 withInt:38];
  [self wordCharsWithInt:40 withInt:43];
  [self wordCharsWithInt:45 withInt:46];
  [self wordCharsWithInt:48 withInt:57];
  [self wordCharsWithInt:63 withInt:63];
  [self wordCharsWithInt:95 withInt:95];
  [self wordCharsWithInt:124 withInt:124];
  [self wordCharsWithInt:126 withInt:126];
  [self eolIsSignificantWithBoolean:YES];
  [self slashSlashCommentsWithBoolean:YES];
  [self slashStarCommentsWithBoolean:YES];
}

- (void)parseNumbers {
}

- (NSString *)readCommandParams {
  [self resetSyntax];
  [self whitespaceCharsWithInt:10 withInt:10];
  [self whitespaceCharsWithInt:13 withInt:13];
  [self slashSlashCommentsWithBoolean:NO];
  [self slashStarCommentsWithBoolean:NO];
  int tok;
  RAREUTCharArray *sb = [[RAREUTCharArray alloc] init];
  while ((tok = [self nextToken]) != RAREUTReaderTokenizer_TT_EOL) {
    (void) [sb appendWithChar:(unichar) tok];
  }
  [self init__];
  int end = [sb sequenceLength] - 1;
  if ((end > -1) && ([sb charAtWithInt:end] == 0x000d)) {
    (void) [sb setLengthWithInt:end];
  }
  (void) [sb trim];
  @try {
    return [RAREUTCharScanner cleanQuotedWithCharArray:sb->A_ withInt:0 withInt:sb->_length_];
  }
  @catch (JavaTextParseException *ex) {
    return [sb description];
  }
}

- (NSString *)readPreformatted {
  [self resetSyntax];
  [self eolIsSignificantWithBoolean:NO];
  [self slashSlashCommentsWithBoolean:NO];
  [self slashStarCommentsWithBoolean:NO];
  int tok;
  preTag_ = nil;
  RAREUTCharArray *sb = [[RAREUTCharArray alloc] init];
  int testLen;
  IOSCharArray *edoc;
  tok = [self nextToken];
  if (tok < 33) {
    edoc = [SDFParser defEndHeredoc];
    testLen = 1;
    if (tok == RAREUTReaderTokenizer_TT_EOL) {
      [self pushBack];
    }
    else {
      [self stripPreformattedWhitespace];
    }
  }
  else {
    (void) [sb appendWithChar:(unichar) tok];
    while ((tok = [self nextToken]) != RAREUTReaderTokenizer_TT_EOL) {
      if (tok < 33) {
        break;
      }
      (void) [sb appendWithChar:(unichar) tok];
    }
    edoc = [sb toCharArray];
    preTag_ = [NSString stringWithCharacters:edoc];
    testLen = (int) [((IOSCharArray *) nil_chk(edoc)) count] - 1;
    sb->_length_ = 0;
    if (tok != RAREUTReaderTokenizer_TT_EOL) {
      [self stripWhitespace];
    }
  }
  while ((tok = [self nextToken]) != RAREUTReaderTokenizer_TT_EOF) {
    (void) [sb appendWithChar:(unichar) tok];
    if ((sb->_length_ > testLen) && [sb endsWithWithCharArray:edoc]) {
      sb->_length_ -= (int) [((IOSCharArray *) nil_chk(edoc)) count];
      break;
    }
  }
  [self init__];
  if (edoc == [SDFParser defEndHeredoc]) {
    int n = [sb indexOfWithNSString:@"\n"];
    if (n == 0) {
      (void) [sb deleteCharAtWithInt:0];
    }
    else if ((n == 1) && ([sb charAtWithInt:0] == 0x000a)) {
      (void) [sb removeWithInt:0 withInt:2];
    }
    n = sb->_length_ - 1;
    if (n > 0) {
      if (IOSCharArray_Get(nil_chk(sb->A_), n) == 0x000a) {
        if ((n > 1) && (IOSCharArray_Get(sb->A_, n - 1) == 0x000d)) {
          sb->_length_ = n - 1;
        }
        else {
          sb->_length_ = n;
        }
      }
    }
  }
  return [sb description];
}

- (NSString *)readToEOL {
  [self resetSyntax];
  [self whitespaceCharsWithInt:10 withInt:10];
  [self whitespaceCharsWithInt:13 withInt:13];
  [self slashSlashCommentsWithBoolean:NO];
  [self slashStarCommentsWithBoolean:NO];
  int tok;
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] init];
  while ((tok = [self nextToken]) != RAREUTReaderTokenizer_TT_EOL) {
    (void) [sb appendWithChar:(unichar) tok];
  }
  [self init__];
  int end = [sb sequenceLength] - 1;
  if ((end > -1) && ([sb charAtWithInt:end] == 0x000d)) {
    [sb setLengthWithInt:end];
  }
  return [sb description];
}

- (void)stripPreformattedWhitespace {
  int tok;
  while ((tok = [self nextToken]) != RAREUTReaderTokenizer_TT_EOL) {
    if ((tok > 32) || (tok == RAREUTReaderTokenizer_TT_EOF)) {
      [self pushBack];
      break;
    }
  }
  if (tok == RAREUTReaderTokenizer_TT_EOL) {
    [self pushBack];
  }
}

- (void)stripWhitespace {
  int tok;
  while ((tok = [self nextToken]) != RAREUTReaderTokenizer_TT_EOL) {
    if ((tok > 32) || (tok == RAREUTReaderTokenizer_TT_EOF)) {
      [self pushBack];
      break;
    }
  }
}

- (void)copyAllFieldsTo:(SDFParser_Tokenizer *)other {
  [super copyAllFieldsTo:other];
  other->preTag_ = preTag_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithSDFParser:withJavaIoReader:", NULL, NULL, 0x0, NULL },
    { "readCommandParams", NULL, "LNSString", 0x1, "JavaIoIOException" },
    { "readPreformatted", NULL, "LNSString", 0x1, "JavaIoIOException" },
    { "readToEOL", NULL, "LNSString", 0x1, "JavaIoIOException" },
    { "stripPreformattedWhitespace", NULL, "V", 0x2, "JavaIoIOException" },
    { "stripWhitespace", NULL, "V", 0x2, "JavaIoIOException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "preTag_", NULL, 0x0, "LNSString" },
  };
  static J2ObjcClassInfo _SDFParser_Tokenizer = { "Tokenizer", "com.appnativa.spot", "SDFParser", 0x0, 6, methods, 1, fields, 0, NULL};
  return &_SDFParser_Tokenizer;
}

@end
