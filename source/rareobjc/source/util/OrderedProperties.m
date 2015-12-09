//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/OrderedProperties.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSCharArray.h"
#include "IOSClass.h"
#include "com/appnativa/util/CharArray.h"
#include "com/appnativa/util/CharScanner.h"
#include "com/appnativa/util/OrderedProperties.h"
#include "java/io/BufferedReader.h"
#include "java/io/IOException.h"
#include "java/io/InputStream.h"
#include "java/io/InputStreamReader.h"
#include "java/io/OutputStream.h"
#include "java/io/OutputStreamWriter.h"
#include "java/io/Reader.h"
#include "java/lang/Character.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/Integer.h"
#include "java/lang/NullPointerException.h"
#include "java/lang/StringBuilder.h"
#include "java/lang/System.h"
#include "java/util/ArrayList.h"
#include "java/util/Date.h"
#include "java/util/Iterator.h"
#include "java/util/LinkedHashMap.h"
#include "java/util/List.h"
#include "java/util/Set.h"

@implementation RAREUTOrderedProperties

static NSString * RAREUTOrderedProperties_lineSeparator_;

+ (NSString *)lineSeparator {
  return RAREUTOrderedProperties_lineSeparator_;
}

+ (void)setLineSeparator:(NSString *)lineSeparator {
  RAREUTOrderedProperties_lineSeparator_ = lineSeparator;
}

- (id)init {
  if (self = [super init]) {
    defaults_ = nil;
    preserveDuplicates_ = NO;
    slashComment_ = NO;
  }
  return self;
}

- (id)initWithRAREUTOrderedProperties:(RAREUTOrderedProperties *)properties {
  if (self = [super init]) {
    defaults_ = properties;
    preserveDuplicates_ = NO;
    slashComment_ = NO;
  }
  return self;
}

- (void)load__WithJavaIoInputStream:(JavaIoInputStream *)inArg {
  @synchronized(self) {
    {
      if (inArg == nil) {
        @throw [[JavaLangNullPointerException alloc] init];
      }
      [self load__WithJavaIoReader:[[JavaIoInputStreamReader alloc] initWithJavaIoInputStream:inArg withNSString:@"ISO-8859-1"]];
    }
  }
}

- (void)load__WithJavaIoReader:(JavaIoReader *)inArg {
  @synchronized(self) {
    {
      if (inArg == nil) {
        @throw [[JavaLangNullPointerException alloc] init];
      }
      int mode = RAREUTOrderedProperties_NONE, unicode = 0, count = 0;
      unichar nextChar;
      IOSCharArray *buf = [IOSCharArray arrayWithLength:40];
      int offset = 0, keyLength = -1, intVal;
      BOOL firstChar = YES;
      JavaIoBufferedReader *br = [[JavaIoBufferedReader alloc] initWithJavaIoReader:inArg];
      while (YES) {
        intVal = [br read];
        if (intVal == -1) {
          break;
        }
        nextChar = (unichar) intVal;
        if (offset == (int) [buf count]) {
          IOSCharArray *newBuf = [IOSCharArray arrayWithLength:(int) [buf count] * 2];
          [JavaLangSystem arraycopyWithId:buf withInt:0 withId:newBuf withInt:0 withInt:offset];
          buf = newBuf;
        }
        if (mode == RAREUTOrderedProperties_UNICODE) {
          int digit = [JavaLangCharacter digitWithChar:nextChar withInt:16];
          if (digit >= 0) {
            unicode = (unicode << 4) + digit;
            if (++count < 4) {
              continue;
            }
          }
          else if (count <= 4) {
            @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"Invalid Unicode sequence: illegal character"];
          }
          mode = RAREUTOrderedProperties_NONE;
          (*IOSCharArray_GetRef(buf, offset++)) = (unichar) unicode;
          if (nextChar != 0x000a) {
            continue;
          }
        }
        if (mode == RAREUTOrderedProperties_SLASH) {
          mode = RAREUTOrderedProperties_NONE;
          switch (nextChar) {
            case 0x000d:
            mode = RAREUTOrderedProperties_CONTINUE;
            continue;
            case 0x000a:
            mode = RAREUTOrderedProperties_IGNORE;
            continue;
            case 'b':
            nextChar = 0x0008;
            break;
            case 'f':
            nextChar = 0x000c;
            break;
            case 'n':
            nextChar = 0x000a;
            break;
            case 'r':
            nextChar = 0x000d;
            break;
            case 't':
            nextChar = 0x0009;
            break;
            case 'u':
            mode = RAREUTOrderedProperties_UNICODE;
            unicode = count = 0;
            continue;
          }
        }
        else {
          switch (nextChar) {
            case '#':
            case '!':
            if (firstChar) {
              while (YES) {
                intVal = [br read];
                if (intVal == -1) {
                  break;
                }
                nextChar = (unichar) intVal;
                if ((nextChar == 0x000d) || (nextChar == 0x000a)) {
                  break;
                }
              }
              continue;
            }
            break;
            case 0x000a:
            if (mode == RAREUTOrderedProperties_CONTINUE) {
              mode = RAREUTOrderedProperties_IGNORE;
              continue;
            }
            case 0x000d:
            mode = RAREUTOrderedProperties_NONE;
            firstChar = YES;
            if ((offset > 0) || ((offset == 0) && (keyLength == 0))) {
              if (keyLength == -1) {
                keyLength = offset;
              }
              NSString *temp = [NSString stringWithCharacters:buf offset:0 length:offset];
              (void) [self putWithId:[temp substring:0 endIndex:keyLength] withId:[temp substring:keyLength]];
            }
            keyLength = -1;
            offset = 0;
            continue;
            case '\\':
            if (mode == RAREUTOrderedProperties_KEY_DONE) {
              keyLength = offset;
            }
            mode = RAREUTOrderedProperties_SLASH;
            continue;
            case ':':
            case '=':
            if (keyLength == -1) {
              mode = RAREUTOrderedProperties_NONE;
              keyLength = offset;
              continue;
            }
            break;
          }
          if ([JavaLangCharacter isWhitespaceWithChar:nextChar]) {
            if (mode == RAREUTOrderedProperties_CONTINUE) {
              mode = RAREUTOrderedProperties_IGNORE;
            }
            if ((offset == 0) || (offset == keyLength) || (mode == RAREUTOrderedProperties_IGNORE)) {
              continue;
            }
            if (keyLength == -1) {
              mode = RAREUTOrderedProperties_KEY_DONE;
              continue;
            }
          }
          if ((mode == RAREUTOrderedProperties_IGNORE) || (mode == RAREUTOrderedProperties_CONTINUE)) {
            mode = RAREUTOrderedProperties_NONE;
          }
        }
        firstChar = NO;
        if (mode == RAREUTOrderedProperties_KEY_DONE) {
          keyLength = offset;
          mode = RAREUTOrderedProperties_NONE;
        }
        (*IOSCharArray_GetRef(buf, offset++)) = nextChar;
      }
      if ((mode == RAREUTOrderedProperties_UNICODE) && (count <= 4)) {
        @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"Invalid Unicode sequence"];
      }
      if ((keyLength == -1) && (offset > 0)) {
        keyLength = offset;
      }
      if (keyLength >= 0) {
        NSString *temp = [NSString stringWithCharacters:buf offset:0 length:offset];
        NSString *key = [temp substring:0 endIndex:keyLength];
        int vo = keyLength;
        if (stripLeadingSpaces_) {
          while (vo < offset) {
            if ([JavaLangCharacter isWhitespaceWithChar:IOSCharArray_Get(buf, vo)]) {
              vo++;
            }
          }
        }
        NSString *value = [temp substring:vo];
        if (mode == RAREUTOrderedProperties_SLASH) {
          value = [NSString stringWithFormat:@"%@\x00", value];
        }
        (void) [self putWithId:key withId:value];
      }
    }
  }
}

- (id)putWithId:(id)key
         withId:(id)value {
  id o = [super putWithId:key withId:value];
  if ([self isPreserveDuplicates] && (o != nil)) {
    id<JavaUtilList> l;
    if ([o isKindOfClass:[NSString class]]) {
      l = [[JavaUtilArrayList alloc] init];
      [l addWithId:(NSString *) check_class_cast(o, [NSString class])];
    }
    else {
      l = (id<JavaUtilList>) check_protocol_cast(o, @protocol(JavaUtilList));
    }
    [((id<JavaUtilList>) nil_chk(l)) addWithId:value];
    (void) [super putWithId:key withId:l];
  }
  return o;
}

- (void)storeWithJavaIoOutputStream:(JavaIoOutputStream *)outArg
                       withNSString:(NSString *)comment {
  @synchronized(self) {
    {
      if (RAREUTOrderedProperties_lineSeparator_ == nil) {
        RAREUTOrderedProperties_lineSeparator_ = [JavaLangSystem getPropertyWithNSString:@"line.separator"];
      }
      JavaLangStringBuilder *buffer = [[JavaLangStringBuilder alloc] initWithInt:200];
      JavaIoOutputStreamWriter *writer = [[JavaIoOutputStreamWriter alloc] initWithJavaIoOutputStream:outArg withNSString:@"ISO8859_1"];
      if (comment != nil) {
        [writer writeWithNSString:@"#"];
        [writer writeWithNSString:comment];
        [writer writeWithNSString:RAREUTOrderedProperties_lineSeparator_];
      }
      [writer writeWithNSString:@"#"];
      [writer writeWithNSString:[((JavaUtilDate *) [[JavaUtilDate alloc] init]) description]];
      [writer writeWithNSString:RAREUTOrderedProperties_lineSeparator_];
      for (id<JavaUtilIterator> e = [((id<JavaUtilSet>) nil_chk([self keySet])) iterator]; [((id<JavaUtilIterator>) nil_chk(e)) hasNext]; ) {
        NSString *key = (NSString *) check_class_cast([e next], [NSString class]);
        NSString *val = (NSString *) check_class_cast([self getWithId:key], [NSString class]);
        [self dumpStringWithJavaLangStringBuilder:buffer withNSString:key withBoolean:YES];
        (void) [buffer appendWithChar:'='];
        [self dumpStringWithJavaLangStringBuilder:buffer withNSString:(NSString *) check_class_cast(val, [NSString class]) withBoolean:NO];
        (void) [buffer appendWithNSString:RAREUTOrderedProperties_lineSeparator_];
        [writer writeWithNSString:[buffer description]];
        [buffer setLengthWithInt:0];
      }
      [writer flush];
    }
  }
}

- (NSString *)stripCommentWithNSString:(NSString *)str {
  if ((str == nil) || ((int) [str sequenceLength] == 0)) {
    return nil;
  }
  if (strBuffer_ == nil) {
    strBuffer_ = [[RAREUTCharArray alloc] init];
  }
  (void) [((RAREUTCharArray *) nil_chk(strBuffer_)) setWithNSString:str];
  (void) [strBuffer_ trim];
  int n;
  if (!slashComment_) {
    n = [RAREUTCharScanner indexOfWithCharArray:strBuffer_->A_ withInt:0 withInt:strBuffer_->_length_ withChar:'#' withBoolean:YES withBoolean:NO withChar:'(' withChar:')'];
  }
  else {
    n = [RAREUTCharScanner indexOfWithCharArray:strBuffer_->A_ withInt:0 withInt:strBuffer_->_length_ withChar:'/' withBoolean:YES withBoolean:NO withChar:'(' withChar:')'];
    if ((n != -1) && (n + 1) < strBuffer_->_length_) {
      if (IOSCharArray_Get(nil_chk(strBuffer_->A_), n + 1) != '/') {
        n = -1;
      }
    }
  }
  if (n == -1) {
    return str;
  }
  return [((NSString *) nil_chk([((NSString *) nil_chk(str)) substring:0 endIndex:n])) trim];
}

- (NSString *)stripCommentWithId:(id)o
withRAREUTOrderedProperties_iValidator:(id<RAREUTOrderedProperties_iValidator>)validator {
  if ([o isKindOfClass:[NSString class]]) {
    return [self stripCommentWithNSString:(NSString *) check_class_cast(o, [NSString class]) withRAREUTOrderedProperties_iValidator:validator];
  }
  if ([o conformsToProtocol: @protocol(JavaUtilList)]) {
    id<JavaUtilList> list = (id<JavaUtilList>) check_protocol_cast(o, @protocol(JavaUtilList));
    int len = [((id<JavaUtilList>) nil_chk(list)) size];
    for (int i = 0; i < len; i++) {
      NSString *s = [self stripCommentWithNSString:(NSString *) check_class_cast([list getWithInt:i], [NSString class]) withRAREUTOrderedProperties_iValidator:validator];
      if (s != nil) {
        return s;
      }
    }
  }
  return nil;
}

- (NSString *)stripCommentWithNSString:(NSString *)str
withRAREUTOrderedProperties_iValidator:(id<RAREUTOrderedProperties_iValidator>)validator {
  int len = (str == nil) ? 0 : [str sequenceLength];
  if (len == 0) {
    return nil;
  }
  if (strBuffer_ == nil) {
    strBuffer_ = [[RAREUTCharArray alloc] init];
  }
  (void) [((RAREUTCharArray *) nil_chk(strBuffer_)) setWithNSString:str];
  (void) [strBuffer_ trim];
  if (strBuffer_->_length_ == 0) {
    return nil;
  }
  int n;
  if (!slashComment_) {
    n = [RAREUTCharScanner indexOfWithCharArray:strBuffer_->A_ withInt:0 withInt:strBuffer_->_length_ withChar:'#' withBoolean:YES withBoolean:NO withChar:'(' withChar:')'];
  }
  else {
    n = [RAREUTCharScanner indexOfWithCharArray:strBuffer_->A_ withInt:0 withInt:strBuffer_->_length_ withChar:'/' withBoolean:YES withBoolean:NO withChar:'(' withChar:')'];
    if ((n != -1) && (n + 1) < strBuffer_->_length_) {
      if (IOSCharArray_Get(nil_chk(strBuffer_->A_), n + 1) != '/') {
        n = -1;
      }
    }
  }
  int p = -1;
  if (n == -1) {
    if (IOSCharArray_Get(nil_chk(strBuffer_->A_), strBuffer_->_length_ - 1) == ']') {
      p = [strBuffer_ lastIndexOfWithInt:'['];
    }
  }
  else if (n > 0) {
    strBuffer_->_length_ = n;
    (void) [strBuffer_ rightTrim];
    if (IOSCharArray_Get(nil_chk(strBuffer_->A_), strBuffer_->_length_ - 1) == ']') {
      p = [strBuffer_ lastIndexOfWithInt:'['];
    }
  }
  if ((n == -1) && (p == -1)) {
    if ((validator != nil) && ![validator isValidWithRAREUTCharArray:nil withInt:0]) {
      return nil;
    }
    return str;
  }
  if (p != -1) {
    if ((validator != nil) && ![validator isValidWithRAREUTCharArray:strBuffer_ withInt:p]) {
      return nil;
    }
    n = p;
  }
  return (n == len) ? str : [((NSString *) nil_chk([((NSString *) nil_chk(str)) substring:0 endIndex:n])) trim];
}

- (void)setPreserveDuplicatesWithBoolean:(BOOL)preserveDuplicates {
  self->preserveDuplicates_ = preserveDuplicates;
}

- (id)setPropertyWithNSString:(NSString *)name
                 withNSString:(NSString *)value {
  return [self putWithId:name withId:value];
}

- (void)setSlashCommentWithBoolean:(BOOL)slashComment {
  self->slashComment_ = slashComment;
}

- (void)setStripLeadingSpacesWithBoolean:(BOOL)stripLeadingSpaces {
  self->stripLeadingSpaces_ = stripLeadingSpaces;
}

- (NSString *)getPropertyWithNSString:(NSString *)name {
  id result = [super getWithId:name];
  NSString *property = ([result isKindOfClass:[NSString class]]) ? (NSString *) check_class_cast(result, [NSString class]) : nil;
  if ((property == nil) && (defaults_ != nil)) {
    property = [defaults_ getPropertyWithNSString:name];
  }
  return property;
}

- (NSString *)getPropertyWithNSString:(NSString *)name
                         withNSString:(NSString *)defaultValue {
  id result = [super getWithId:name];
  NSString *property = ([result isKindOfClass:[NSString class]]) ? (NSString *) check_class_cast(result, [NSString class]) : nil;
  if ((property == nil) && (defaults_ != nil)) {
    property = [defaults_ getPropertyWithNSString:name];
  }
  if (property == nil) {
    return defaultValue;
  }
  return property;
}

- (BOOL)isPreserveDuplicates {
  return preserveDuplicates_;
}

- (BOOL)isSlashComment {
  return slashComment_;
}

- (BOOL)isStripLeadingSpaces {
  return stripLeadingSpaces_;
}

- (void)dumpStringWithJavaLangStringBuilder:(JavaLangStringBuilder *)buffer
                               withNSString:(NSString *)string
                                withBoolean:(BOOL)key {
  int i = 0;
  if (!key && (i < [((NSString *) nil_chk(string)) sequenceLength]) && ([string charAtWithInt:i] == ' ')) {
    (void) [((JavaLangStringBuilder *) nil_chk(buffer)) appendWithNSString:@"\\ "];
    i++;
  }
  for (; i < [((NSString *) nil_chk(string)) sequenceLength]; i++) {
    unichar ch = [string charAtWithInt:i];
    switch (ch) {
      case 0x0009:
      (void) [((JavaLangStringBuilder *) nil_chk(buffer)) appendWithNSString:@"\\t"];
      break;
      case 0x000a:
      (void) [((JavaLangStringBuilder *) nil_chk(buffer)) appendWithNSString:@"\\n"];
      break;
      case 0x000c:
      (void) [((JavaLangStringBuilder *) nil_chk(buffer)) appendWithNSString:@"\\f"];
      break;
      case 0x000d:
      (void) [((JavaLangStringBuilder *) nil_chk(buffer)) appendWithNSString:@"\\r"];
      break;
      default:
      if (([@"\\#!=:" indexOf:ch] >= 0) || (key && (ch == ' '))) {
        (void) [((JavaLangStringBuilder *) nil_chk(buffer)) appendWithChar:'\\'];
      }
      if ((ch >= ' ') && (ch <= '~')) {
        (void) [((JavaLangStringBuilder *) nil_chk(buffer)) appendWithChar:ch];
      }
      else {
        NSString *hex = [JavaLangInteger toHexStringWithInt:ch];
        (void) [((JavaLangStringBuilder *) nil_chk(buffer)) appendWithNSString:@"\\u"];
        for (int j = 0; j < 4 - [((NSString *) nil_chk(hex)) sequenceLength]; j++) {
          (void) [buffer appendWithNSString:@"0"];
        }
        (void) [buffer appendWithNSString:hex];
      }
    }
  }
}

- (void)copyAllFieldsTo:(RAREUTOrderedProperties *)other {
  [super copyAllFieldsTo:other];
  other->defaults_ = defaults_;
  other->preserveDuplicates_ = preserveDuplicates_;
  other->slashComment_ = slashComment_;
  other->strBuffer_ = strBuffer_;
  other->stripLeadingSpaces_ = stripLeadingSpaces_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "load__WithJavaIoInputStream:", NULL, "V", 0x21, "JavaIoIOException" },
    { "load__WithJavaIoReader:", NULL, "V", 0x21, "JavaIoIOException" },
    { "putWithId:withId:", NULL, "LNSObject", 0x1, NULL },
    { "storeWithJavaIoOutputStream:withNSString:", NULL, "V", 0x21, "JavaIoIOException" },
    { "stripCommentWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "stripCommentWithId:withRAREUTOrderedProperties_iValidator:", NULL, "LNSString", 0x1, NULL },
    { "stripCommentWithNSString:withRAREUTOrderedProperties_iValidator:", NULL, "LNSString", 0x1, NULL },
    { "setPropertyWithNSString:withNSString:", NULL, "LNSObject", 0x1, NULL },
    { "getPropertyWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "getPropertyWithNSString:withNSString:", NULL, "LNSString", 0x1, NULL },
    { "isPreserveDuplicates", NULL, "Z", 0x1, NULL },
    { "isSlashComment", NULL, "Z", 0x1, NULL },
    { "isStripLeadingSpaces", NULL, "Z", 0x1, NULL },
    { "dumpStringWithJavaLangStringBuilder:withNSString:withBoolean:", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "NONE_", NULL, 0x1a, "I" },
    { "SLASH_", NULL, 0x1a, "I" },
    { "UNICODE_", NULL, 0x1a, "I" },
    { "CONTINUE_", NULL, 0x1a, "I" },
    { "KEY_DONE_", NULL, 0x1a, "I" },
    { "IGNORE_", NULL, 0x1a, "I" },
    { "lineSeparator_", NULL, 0xa, "LNSString" },
    { "defaults_", NULL, 0x4, "LRAREUTOrderedProperties" },
  };
  static J2ObjcClassInfo _RAREUTOrderedProperties = { "OrderedProperties", "com.appnativa.util", NULL, 0x1, 14, methods, 8, fields, 0, NULL};
  return &_RAREUTOrderedProperties;
}

@end

@interface RAREUTOrderedProperties_iValidator : NSObject
@end

@implementation RAREUTOrderedProperties_iValidator

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isValidWithRAREUTCharArray:withInt:", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREUTOrderedProperties_iValidator = { "iValidator", "com.appnativa.util", "OrderedProperties", 0x201, 1, methods, 0, NULL, 0, NULL};
  return &_RAREUTOrderedProperties_iValidator;
}

@end
