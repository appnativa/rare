//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/ReaderTokenizer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSByteArray.h"
#include "IOSCharArray.h"
#include "IOSClass.h"
#include "com/appnativa/util/CharScanner.h"
#include "com/appnativa/util/ReaderTokenizer.h"
#include "java/io/IOException.h"
#include "java/io/InputStream.h"
#include "java/io/Reader.h"
#include "java/lang/Double.h"
#include "java/lang/NullPointerException.h"
#include "java/lang/NumberFormatException.h"
#include "java/lang/StringBuilder.h"

@implementation RAREUTReaderTokenizer

+ (int)TT_COMMENT {
  return RAREUTReaderTokenizer_TT_COMMENT;
}

+ (int)TT_EOF {
  return RAREUTReaderTokenizer_TT_EOF;
}

+ (int)TT_EOL {
  return RAREUTReaderTokenizer_TT_EOL;
}

+ (int)TT_NUMBER {
  return RAREUTReaderTokenizer_TT_NUMBER;
}

+ (int)TT_WORD {
  return RAREUTReaderTokenizer_TT_WORD;
}

- (id)initWithJavaIoReader:(JavaIoReader *)r {
  if (self = [super init]) {
    unicodeChars_ = [IOSCharArray arrayWithLength:4];
    if (r == nil) {
      @throw [[JavaLangNullPointerException alloc] init];
    }
    [self init_tokinizer];
    inReader_ = r;
  }
  return self;
}

- (void)commentCharWithInt:(int)ch {
  if ((0 <= ch) && (ch < (int) [((IOSByteArray *) nil_chk(tokenTypes_)) count])) {
    (*IOSByteArray_GetRef(tokenTypes_, ch)) = RAREUTReaderTokenizer_TOKEN_COMMENT;
  }
}

- (void)eolIsSignificantWithBoolean:(BOOL)flag {
  isEOLSignificant_ = flag;
}

- (int)lineno {
  return lineNumber_;
}

- (void)lowerCaseModeWithBoolean:(BOOL)flag {
  forceLowercase_ = flag;
}

- (int)nextToken {
  if (pushBackToken_) {
    pushBackToken_ = NO;
    if (ttype_ != RAREUTReaderTokenizer_TT_UNKNOWN) {
      return ttype_;
    }
  }
  comments_ = nil;
  sval_ = nil;
  int currentChar = (peekChar_ == -2) ? [self read] : peekChar_;
  if (lastCr_ && (currentChar == 0x000a)) {
    lastCr_ = NO;
    currentChar = [self read];
  }
  if (currentChar == -1) {
    return (ttype_ = RAREUTReaderTokenizer_TT_EOF);
  }
  char currentType = (currentChar > 255) ? RAREUTReaderTokenizer_TOKEN_WORD : IOSByteArray_Get(nil_chk(tokenTypes_), currentChar);
  while ((currentType & RAREUTReaderTokenizer_TOKEN_WHITE) != 0) {
    if (currentChar == 0x000d) {
      lineNumber_++;
      if (isEOLSignificant_) {
        lastCr_ = YES;
        peekChar_ = -2;
        return (ttype_ = RAREUTReaderTokenizer_TT_EOL);
      }
      if ((currentChar = [self read]) == 0x000a) {
        currentChar = [self read];
      }
    }
    else if (currentChar == 0x000a) {
      lineNumber_++;
      if (isEOLSignificant_) {
        peekChar_ = -2;
        return (ttype_ = RAREUTReaderTokenizer_TT_EOL);
      }
      currentChar = [self read];
    }
    else {
      currentChar = [self read];
    }
    if (currentChar == -1) {
      return (ttype_ = RAREUTReaderTokenizer_TT_EOF);
    }
    currentType = (currentChar > 255) ? RAREUTReaderTokenizer_TOKEN_WORD : IOSByteArray_Get(nil_chk(tokenTypes_), currentChar);
  }
  if ((currentType & RAREUTReaderTokenizer_TOKEN_DIGIT) != 0) {
    JavaLangStringBuilder *digits = [[JavaLangStringBuilder alloc] initWithInt:20];
    BOOL haveDecimal = NO, checkJustNegative = currentChar == '-';
    while (YES) {
      if (currentChar == '.') {
        haveDecimal = YES;
      }
      (void) [digits appendWithChar:(unichar) currentChar];
      currentChar = [self read];
      if (((currentChar < '0') || (currentChar > '9')) && (haveDecimal || (currentChar != '.'))) {
        break;
      }
    }
    peekChar_ = currentChar;
    if (checkJustNegative && ([digits sequenceLength] == 1)) {
      return (ttype_ = '-');
    }
    @try {
      nval_ = [[JavaLangDouble valueOfWithNSString:[digits description]] doubleValue];
    }
    @catch (JavaLangNumberFormatException *e) {
      nval_ = 0;
    }
    return (ttype_ = RAREUTReaderTokenizer_TT_NUMBER);
  }
  if ((currentType & RAREUTReaderTokenizer_TOKEN_WORD) != 0) {
    JavaLangStringBuilder *word = [[JavaLangStringBuilder alloc] initWithInt:20];
    while (YES) {
      (void) [word appendWithChar:(unichar) currentChar];
      currentChar = [self read];
      if ((currentChar == -1) || ((currentChar < 256) && (IOSByteArray_Get(nil_chk(tokenTypes_), currentChar) & (RAREUTReaderTokenizer_TOKEN_WORD | RAREUTReaderTokenizer_TOKEN_DIGIT)) == 0)) {
        break;
      }
    }
    peekChar_ = currentChar;
    sval_ = forceLowercase_ ? [((NSString *) nil_chk([word description])) lowercaseString] : [word description];
    return (ttype_ = RAREUTReaderTokenizer_TT_WORD);
  }
  if (currentType == RAREUTReaderTokenizer_TOKEN_QUOTE) {
    int matchQuote = currentChar;
    JavaLangStringBuilder *quoteString = [[JavaLangStringBuilder alloc] init];
    int peekOne = [self read];
    while ((peekOne >= 0) && (peekOne != matchQuote) && (peekOne != 0x000d) && (peekOne != 0x000a)) {
      BOOL readPeek = YES;
      if (peekOne == '\\') {
        int c1 = [self read];
        if ((c1 <= '7') && (c1 >= '0')) {
          int digitValue = c1 - '0';
          c1 = [self read];
          if ((c1 > '7') || (c1 < '0')) {
            readPeek = NO;
          }
          else {
            digitValue = digitValue * 8 + (c1 - '0');
            c1 = [self read];
            if ((digitValue > 037) || (c1 > '7') || (c1 < '0')) {
              readPeek = NO;
            }
            else {
              digitValue = digitValue * 8 + (c1 - '0');
            }
          }
          if (!readPeek) {
            (void) [quoteString appendWithChar:(unichar) digitValue];
            peekOne = c1;
          }
          else {
            peekOne = digitValue;
          }
        }
        else if (c1 == 'u') {
          int n = 0;
          IOSCharArray *a = unicodeChars_;
          while (n < 4) {
            c1 = [self read];
            if ((c1 == matchQuote) || (c1 == -1)) {
              break;
            }
            (*IOSCharArray_GetRef(nil_chk(a), n++)) = (unichar) c1;
          }
          if (n < 4) {
            (void) [quoteString appendWithNSString:@"u"];
            for (int i = 0; i < n; i++) {
              (void) [quoteString appendWithChar:IOSCharArray_Get(nil_chk(a), i)];
            }
            peekOne = c1;
            readPeek = NO;
          }
          else {
            peekOne = [RAREUTCharScanner unicodeStringToCharWithCharArray:a withInt:0];
          }
        }
        else {
          switch (c1) {
            case 'a':
            peekOne = (int) 0x7;
            break;
            case 'b':
            peekOne = (int) 0x8;
            break;
            case 'f':
            peekOne = (int) 0xc;
            break;
            case 'n':
            peekOne = (int) 0xA;
            break;
            case 'r':
            peekOne = (int) 0xD;
            break;
            case 't':
            peekOne = (int) 0x9;
            break;
            case 'v':
            peekOne = (int) 0xB;
            break;
            default:
            peekOne = c1;
          }
        }
      }
      if (readPeek) {
        (void) [quoteString appendWithChar:(unichar) peekOne];
        peekOne = [self read];
      }
    }
    if (peekOne == matchQuote) {
      peekOne = [self read];
    }
    peekChar_ = peekOne;
    ttype_ = matchQuote;
    sval_ = [quoteString description];
    return ttype_;
  }
  if ((currentChar == '/') && (slashSlashComments__ || slashStarComments__)) {
    if ((currentChar = [self read]) == '*' && slashStarComments__) {
      if (returnComments_) {
        comments_ = [[JavaLangStringBuilder alloc] initWithNSString:@"/*"];
      }
      int peekOne = [self read];
      while (YES) {
        currentChar = peekOne;
        peekOne = [self read];
        if (currentChar == -1) {
          peekChar_ = -1;
          return (ttype_ = RAREUTReaderTokenizer_TT_EOF);
        }
        if (currentChar == 0x000d) {
          if (peekOne == 0x000a) {
            if (returnComments_) {
              (void) [((JavaLangStringBuilder *) nil_chk(comments_)) appendWithChar:0x000d];
              (void) [comments_ appendWithChar:0x000a];
              peekOne = [self read];
              lineNumber_++;
              continue;
            }
            else {
              peekOne = [self read];
            }
          }
          lineNumber_++;
        }
        else if (currentChar == 0x000a) {
          lineNumber_++;
        }
        else if ((currentChar == '*') && (peekOne == '/')) {
          peekChar_ = [self read];
          if (returnComments_) {
            (void) [((JavaLangStringBuilder *) nil_chk(comments_)) appendWithNSString:@"*/"];
            sval_ = [comments_ description];
            comments_ = nil;
            return RAREUTReaderTokenizer_TT_COMMENT;
          }
          return [self nextToken];
        }
        if (returnComments_) {
          (void) [((JavaLangStringBuilder *) nil_chk(comments_)) appendWithChar:(unichar) currentChar];
        }
      }
    }
    else if ((currentChar == '/') && slashSlashComments__) {
      if (returnComments_) {
        comments_ = [[JavaLangStringBuilder alloc] initWithNSString:@"//"];
      }
      while ((currentChar = [self read]) >= 0 && (currentChar != 0x000d) && (currentChar != 0x000a)) {
        if (returnComments_) {
          (void) [((JavaLangStringBuilder *) nil_chk(comments_)) appendWithChar:(unichar) currentChar];
        }
      }
      peekChar_ = currentChar;
      if (returnComments_) {
        sval_ = [((JavaLangStringBuilder *) nil_chk(comments_)) description];
        comments_ = nil;
        return RAREUTReaderTokenizer_TT_COMMENT;
      }
      return [self nextToken];
    }
    else if (currentType != RAREUTReaderTokenizer_TOKEN_COMMENT) {
      peekChar_ = currentChar;
      return (ttype_ = '/');
    }
  }
  if (currentType == RAREUTReaderTokenizer_TOKEN_COMMENT) {
    while ((currentChar = [self read]) >= 0 && (currentChar != 0x000d) && (currentChar != 0x000a)) {
    }
    peekChar_ = currentChar;
    return [self nextToken];
  }
  peekChar_ = [self read];
  return (ttype_ = currentChar);
}

- (void)ordinaryCharWithInt:(int)ch {
  if ((0 <= ch) && (ch < (int) [((IOSByteArray *) nil_chk(tokenTypes_)) count])) {
    (*IOSByteArray_GetRef(tokenTypes_, ch)) = 0;
  }
}

- (void)ordinaryCharsWithInt:(int)low
                     withInt:(int)hi {
  if (low < 0) {
    low = 0;
  }
  if (hi > (int) [((IOSByteArray *) nil_chk(tokenTypes_)) count]) {
    hi = (int) [tokenTypes_ count] - 1;
  }
  for (int i = low; i <= hi; i++) {
    (*IOSByteArray_GetRef(tokenTypes_, i)) = 0;
  }
}

- (void)parseNumbers {
  for (int i = '0'; i <= '9'; i++) {
    (*IOSByteArray_GetRef(nil_chk(tokenTypes_), i)) |= RAREUTReaderTokenizer_TOKEN_DIGIT;
  }
  (*IOSByteArray_GetRef(nil_chk(tokenTypes_), '.')) |= RAREUTReaderTokenizer_TOKEN_DIGIT;
  (*IOSByteArray_GetRef(tokenTypes_, '-')) |= RAREUTReaderTokenizer_TOKEN_DIGIT;
}

- (void)pushBack {
  pushBackToken_ = YES;
}

- (void)quoteCharWithInt:(int)ch {
  if ((0 <= ch) && (ch < (int) [((IOSByteArray *) nil_chk(tokenTypes_)) count])) {
    (*IOSByteArray_GetRef(tokenTypes_, ch)) = RAREUTReaderTokenizer_TOKEN_QUOTE;
  }
}

- (void)resetSyntax {
  for (int i = 0; i < 256; i++) {
    (*IOSByteArray_GetRef(nil_chk(tokenTypes_), i)) = 0;
  }
}

- (void)slashSlashCommentsWithBoolean:(BOOL)flag {
  slashSlashComments__ = flag;
}

- (void)slashStarCommentsWithBoolean:(BOOL)flag {
  slashStarComments__ = flag;
}

- (NSString *)description {
  JavaLangStringBuilder *result = [[JavaLangStringBuilder alloc] init];
  (void) [result appendWithNSString:@"Token["];
  switch (ttype_) {
    case RAREUTReaderTokenizer_TT_EOF:
    (void) [result appendWithNSString:@"EOF"];
    break;
    case RAREUTReaderTokenizer_TT_EOL:
    (void) [result appendWithNSString:@"EOL"];
    break;
    case RAREUTReaderTokenizer_TT_NUMBER:
    (void) [result appendWithNSString:@"n="];
    (void) [result appendWithDouble:nval_];
    break;
    case RAREUTReaderTokenizer_TT_WORD:
    (void) [result appendWithNSString:sval_];
    break;
    default:
    if ((ttype_ == RAREUTReaderTokenizer_TT_UNKNOWN) || (IOSByteArray_Get(nil_chk(tokenTypes_), ttype_) == RAREUTReaderTokenizer_TOKEN_QUOTE)) {
      (void) [result appendWithNSString:sval_];
    }
    else {
      (void) [result appendWithChar:'\''];
      (void) [result appendWithChar:(unichar) ttype_];
      (void) [result appendWithChar:'\''];
    }
  }
  (void) [result appendWithNSString:@"], line "];
  (void) [result appendWithInt:lineNumber_];
  return [result description];
}

- (void)whitespaceCharsWithInt:(int)low
                       withInt:(int)hi {
  if (low < 0) {
    low = 0;
  }
  if (hi > (int) [((IOSByteArray *) nil_chk(tokenTypes_)) count]) {
    hi = (int) [tokenTypes_ count] - 1;
  }
  for (int i = low; i <= hi; i++) {
    (*IOSByteArray_GetRef(tokenTypes_, i)) = RAREUTReaderTokenizer_TOKEN_WHITE;
  }
}

- (void)wordCharsWithInt:(int)low
                 withInt:(int)hi {
  if (low < 0) {
    low = 0;
  }
  if (hi > (int) [((IOSByteArray *) nil_chk(tokenTypes_)) count]) {
    hi = (int) [tokenTypes_ count] - 1;
  }
  for (int i = low; i <= hi; i++) {
    (*IOSByteArray_GetRef(tokenTypes_, i)) |= RAREUTReaderTokenizer_TOKEN_WORD;
  }
}

- (void)setReturnCommentsWithBoolean:(BOOL)returnComments {
  self->returnComments_ = returnComments;
}

- (void)init_tokinizer {
  ttype_ = RAREUTReaderTokenizer_TT_UNKNOWN;
  peekChar_ = -2;
  lineNumber_ = 1;
  tokenTypes_ = [IOSByteArray arrayWithLength:256];
  [self wordCharsWithInt:'A' withInt:'Z'];
  [self wordCharsWithInt:'a' withInt:'z'];
  [self wordCharsWithInt:160 withInt:255];
  [self whitespaceCharsWithInt:0 withInt:32];
  [self commentCharWithInt:'/'];
  [self quoteCharWithInt:'"'];
  [self quoteCharWithInt:'\''];
  [self parseNumbers];
}

- (int)read {
  if (inStream_ == nil) {
    return [((JavaIoReader *) nil_chk(inReader_)) read];
  }
  return [((JavaIoInputStream *) nil_chk(inStream_)) read];
}

- (void)copyAllFieldsTo:(RAREUTReaderTokenizer *)other {
  [super copyAllFieldsTo:other];
  other->comments_ = comments_;
  other->forceLowercase_ = forceLowercase_;
  other->inReader_ = inReader_;
  other->inStream_ = inStream_;
  other->isEOLSignificant_ = isEOLSignificant_;
  other->lastCr_ = lastCr_;
  other->lineNumber_ = lineNumber_;
  other->nval_ = nval_;
  other->peekChar_ = peekChar_;
  other->pushBackToken_ = pushBackToken_;
  other->returnComments_ = returnComments_;
  other->slashSlashComments__ = slashSlashComments__;
  other->slashStarComments__ = slashStarComments__;
  other->sval_ = sval_;
  other->tokenTypes_ = tokenTypes_;
  other->ttype_ = ttype_;
  other->unicodeChars_ = unicodeChars_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "nextToken", NULL, "I", 0x1, "JavaIoIOException" },
    { "init_tokinizer", NULL, "V", 0x2, NULL },
    { "read", NULL, "I", 0x2, "JavaIoIOException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "TT_COMMENT_", NULL, 0x19, "I" },
    { "TT_EOF_", NULL, 0x19, "I" },
    { "TT_EOL_", NULL, 0x19, "I" },
    { "TT_NUMBER_", NULL, 0x19, "I" },
    { "TT_WORD_", NULL, 0x19, "I" },
    { "TOKEN_COMMENT_", NULL, 0x1a, "B" },
    { "TOKEN_DIGIT_", NULL, 0x1a, "B" },
    { "TOKEN_QUOTE_", NULL, 0x1a, "B" },
    { "TOKEN_WHITE_", NULL, 0x1a, "B" },
    { "TOKEN_WORD_", NULL, 0x1a, "B" },
    { "TT_UNKNOWN_", NULL, 0x1a, "I" },
    { "nval_", NULL, 0x1, "D" },
    { "sval_", NULL, 0x1, "LNSString" },
    { "ttype_", NULL, 0x1, "I" },
  };
  static J2ObjcClassInfo _RAREUTReaderTokenizer = { "ReaderTokenizer", "com.appnativa.util", NULL, 0x1, 3, methods, 14, fields, 0, NULL};
  return &_RAREUTReaderTokenizer;
}

@end
