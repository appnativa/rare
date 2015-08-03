//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-apple-porting/com/appnativa/util/xml/XMLUtils.java
//
//  Created by decoteaud on 7/14/15.
//

#include "IOSCharArray.h"
#include "IOSClass.h"
#include "com/appnativa/util/CharArray.h"
#include "com/appnativa/util/Helper.h"
#include "com/appnativa/util/xml/XMLUtils.h"
#include "java/io/IOException.h"
#include "java/io/Writer.h"
#include "java/lang/StringBuilder.h"
#include "java/lang/ThreadLocal.h"
#include "java/util/Iterator.h"
#include "java/util/Map.h"
#include "java/util/Set.h"

@implementation RAREUTXMLUtils

static NSString * RAREUTXMLUtils_lineSeparator_ = @"\x0d\n";
static IOSCharArray * RAREUTXMLUtils_amplt_;
static IOSCharArray * RAREUTXMLUtils_ampgt_;
static IOSCharArray * RAREUTXMLUtils_ampquot_;
static IOSCharArray * RAREUTXMLUtils_ampapos_;
static IOSCharArray * RAREUTXMLUtils_ampamp_;
static IOSCharArray * RAREUTXMLUtils_padding_;
static JavaLangThreadLocal * RAREUTXMLUtils_perThreadCharArray_;

+ (NSString *)lineSeparator {
  return RAREUTXMLUtils_lineSeparator_;
}

+ (IOSCharArray *)amplt {
  return RAREUTXMLUtils_amplt_;
}

+ (IOSCharArray *)ampgt {
  return RAREUTXMLUtils_ampgt_;
}

+ (IOSCharArray *)ampquot {
  return RAREUTXMLUtils_ampquot_;
}

+ (IOSCharArray *)ampapos {
  return RAREUTXMLUtils_ampapos_;
}

+ (IOSCharArray *)ampamp {
  return RAREUTXMLUtils_ampamp_;
}

+ (IOSCharArray *)padding {
  return RAREUTXMLUtils_padding_;
}

+ (JavaLangThreadLocal *)perThreadCharArray {
  return RAREUTXMLUtils_perThreadCharArray_;
}

+ (void)setPerThreadCharArray:(JavaLangThreadLocal *)perThreadCharArray {
  RAREUTXMLUtils_perThreadCharArray_ = perThreadCharArray;
}

+ (NSString *)decodeWithRAREUTCharArray:(RAREUTCharArray *)value {
  RAREUTCharArray *out = (RAREUTCharArray *) check_class_cast([((JavaLangThreadLocal *) nil_chk(RAREUTXMLUtils_perThreadCharArray_)) get], [RAREUTCharArray class]);
  ((RAREUTCharArray *) nil_chk(out))->_length_ = 0;
  return [((RAREUTCharArray *) nil_chk([RAREUTXMLUtils decodeWithRAREUTCharArray:value withInt:0 withInt:((RAREUTCharArray *) nil_chk(value))->_length_ withRAREUTCharArray:out])) description];
}

+ (NSString *)decodeWithNSString:(NSString *)value {
  RAREUTCharArray *v = [[RAREUTCharArray alloc] initWithNSString:value];
  RAREUTCharArray *out = (RAREUTCharArray *) check_class_cast([((JavaLangThreadLocal *) nil_chk(RAREUTXMLUtils_perThreadCharArray_)) get], [RAREUTCharArray class]);
  ((RAREUTCharArray *) nil_chk(out))->_length_ = 0;
  return [((RAREUTCharArray *) nil_chk([RAREUTXMLUtils decodeWithRAREUTCharArray:v withInt:0 withInt:v->_length_ withRAREUTCharArray:out])) description];
}

+ (unichar)decodeCharacterWithRAREUTCharArray:(RAREUTCharArray *)ca
                                      withInt:(int)pos
                                      withInt:(int)len {
  if (IOSCharArray_Get(nil_chk(((RAREUTCharArray *) nil_chk(ca))->A_), pos) == '#') {
    int n = 0;
    len--;
    pos++;
    len += pos;
    unichar c;
    while (pos < len) {
      c = IOSCharArray_Get(ca->A_, pos++);
      if ((c < 48) || (c > 57)) {
        break;
      }
      n *= 10;
      n += (int) (c - 48);
    }
    return (unichar) n;
  }
  switch (len) {
    case 2:
    if ([ca regionMatchesWithBoolean:NO withInt:pos withCharArray:RAREUTXMLUtils_amplt_ withInt:0 withInt:2]) {
      return '<';
    }
    if ([ca regionMatchesWithBoolean:NO withInt:pos withCharArray:RAREUTXMLUtils_ampgt_ withInt:0 withInt:2]) {
      return '>';
    }
    break;
    case 3:
    if ([ca regionMatchesWithBoolean:NO withInt:pos withCharArray:RAREUTXMLUtils_ampamp_ withInt:0 withInt:3]) {
      return '<';
    }
    if ([ca regionMatchesWithBoolean:NO withInt:pos withCharArray:RAREUTXMLUtils_ampgt_ withInt:0 withInt:3]) {
      return '>';
    }
    break;
    case 4:
    if ([ca regionMatchesWithBoolean:NO withInt:pos withCharArray:RAREUTXMLUtils_ampquot_ withInt:0 withInt:4]) {
      return '"';
    }
    if ([ca regionMatchesWithBoolean:NO withInt:pos withCharArray:RAREUTXMLUtils_ampapos_ withInt:0 withInt:4]) {
      return '\'';
    }
    break;
    default:
    break;
  }
  return 0;
}

+ (void)dumpAttributesWithJavaIoWriter:(JavaIoWriter *)outArg
                       withJavaUtilMap:(id<JavaUtilMap>)attributes {
  if ((attributes == nil) || ([attributes isEmpty])) {
    return;
  }
  id<JavaUtilIterator> it = [((id<JavaUtilSet>) nil_chk([((id<JavaUtilMap>) nil_chk(attributes)) entrySet])) iterator];
  id<JavaUtilMap_Entry> me;
  NSString *value;
  while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
    me = (id<JavaUtilMap_Entry>) check_protocol_cast([it next], @protocol(JavaUtilMap_Entry));
    value = (NSString *) check_class_cast([((id<JavaUtilMap_Entry>) nil_chk(me)) getValue], [NSString class]);
    if (value != nil) {
      [((JavaIoWriter *) nil_chk(outArg)) writeWithInt:' '];
      [outArg writeWithNSString:(NSString *) check_class_cast([me getKey], [NSString class])];
      [outArg writeWithNSString:@"=\""];
      [outArg writeWithNSString:value];
      [outArg writeWithInt:'"'];
    }
  }
}

+ (NSString *)encodeWithNSString:(NSString *)value {
  if (value == nil) {
    return nil;
  }
  RAREUTCharArray *out = (RAREUTCharArray *) check_class_cast([((JavaLangThreadLocal *) nil_chk(RAREUTXMLUtils_perThreadCharArray_)) get], [RAREUTCharArray class]);
  ((RAREUTCharArray *) nil_chk(out))->_length_ = 0;
  return [((RAREUTCharArray *) nil_chk([RAREUTXMLUtils encodeWithNSString:value withRAREUTCharArray:out])) description];
}

+ (RAREUTCharArray *)encodeWithNSString:(NSString *)value
                    withRAREUTCharArray:(RAREUTCharArray *)outArg {
  if (value == nil) {
    return nil;
  }
  IOSCharArray *b = [((NSString *) nil_chk(value)) toCharArray];
  return [RAREUTXMLUtils encodeWithCharArray:b withInt:0 withInt:(int) [((IOSCharArray *) nil_chk(b)) count] withRAREUTCharArray:outArg];
}

+ (RAREUTCharArray *)encodeWithCharArray:(IOSCharArray *)chars
                                 withInt:(int)pos
                                 withInt:(int)len
                     withRAREUTCharArray:(RAREUTCharArray *)outArg {
  if (outArg == nil) {
    outArg = [[RAREUTCharArray alloc] initWithInt:len + 16];
  }
  len += pos;
  for (int i = pos; i < len; i++) {
    [RAREUTXMLUtils encodeChararacterWithChar:IOSCharArray_Get(nil_chk(chars), i) withRAREUTCharArray:outArg];
  }
  return outArg;
}

+ (NSString *)escapeWithNSString:(NSString *)value {
  return [RAREUTXMLUtils escapeWithNSString:value withBoolean:NO];
}

+ (NSString *)escapeWithNSString:(NSString *)value
                     withBoolean:(BOOL)whitespace {
  if (value == nil) {
    return nil;
  }
  IOSCharArray *b = [((NSString *) nil_chk(value)) toCharArray];
  return [RAREUTXMLUtils escapeWithCharArray:b withInt:0 withInt:(int) [((IOSCharArray *) nil_chk(b)) count] withBoolean:whitespace];
}

+ (RAREUTCharArray *)escapeWithNSString:(NSString *)value
                            withBoolean:(BOOL)whitespace
                    withRAREUTCharArray:(RAREUTCharArray *)outArg {
  if (value == nil) {
    return nil;
  }
  IOSCharArray *b = [((NSString *) nil_chk(value)) toCharArray];
  return [RAREUTXMLUtils escapeWithCharArray:b withInt:0 withInt:(int) [((IOSCharArray *) nil_chk(b)) count] withBoolean:whitespace withRAREUTCharArray:outArg];
}

+ (NSString *)escapeWithCharArray:(IOSCharArray *)chars
                          withInt:(int)pos
                          withInt:(int)len
                      withBoolean:(BOOL)whitespace {
  RAREUTCharArray *out = (RAREUTCharArray *) check_class_cast([((JavaLangThreadLocal *) nil_chk(RAREUTXMLUtils_perThreadCharArray_)) get], [RAREUTCharArray class]);
  ((RAREUTCharArray *) nil_chk(out))->_length_ = 0;
  return [((RAREUTCharArray *) nil_chk([RAREUTXMLUtils escapeWithCharArray:chars withInt:pos withInt:len withBoolean:whitespace withRAREUTCharArray:out])) description];
}

+ (RAREUTCharArray *)escapeWithCharArray:(IOSCharArray *)chars
                                 withInt:(int)pos
                                 withInt:(int)len
                             withBoolean:(BOOL)whitespace
                     withRAREUTCharArray:(RAREUTCharArray *)outArg {
  if (outArg == nil) {
    outArg = [[RAREUTCharArray alloc] initWithInt:len + 16];
  }
  len += pos;
  for (int i = pos; i < len; i++) {
    [RAREUTXMLUtils escapeChararacterWithChar:IOSCharArray_Get(nil_chk(chars), i) withRAREUTCharArray:outArg withBoolean:whitespace];
  }
  return outArg;
}

+ (void)makeElementStringWithJavaLangStringBuilder:(JavaLangStringBuilder *)outArg
                                      withNSString:(NSString *)name
                                      withNSString:(NSString *)value
                                           withInt:(int)depth {
  [RAREUTHelper writePaddingWithJavaLangStringBuilder:outArg withInt:depth];
  if (value == nil) {
    (void) [((JavaLangStringBuilder *) nil_chk(outArg)) appendWithChar:'<'];
    (void) [outArg appendWithNSString:name];
    (void) [outArg appendWithNSString:@"/>"];
    (void) [outArg appendWithNSString:RAREUTXMLUtils_lineSeparator_];
    return;
  }
  (void) [((JavaLangStringBuilder *) nil_chk(outArg)) appendWithChar:'<'];
  (void) [outArg appendWithNSString:name];
  (void) [outArg appendWithChar:'>'];
  if (([((NSString *) nil_chk(value)) sequenceLength] > 60)) {
    (void) [outArg appendWithNSString:RAREUTXMLUtils_lineSeparator_];
    [RAREUTHelper writePaddingWithJavaLangStringBuilder:outArg withInt:depth + 1];
    (void) [outArg appendWithNSString:value];
    (void) [outArg appendWithNSString:RAREUTXMLUtils_lineSeparator_];
    [RAREUTHelper writePaddingWithJavaLangStringBuilder:outArg withInt:depth];
  }
  else {
    (void) [outArg appendWithNSString:value];
  }
  int n = [((NSString *) nil_chk(name)) indexOf:' '];
  if (n != -1) {
    name = [name substring:0 endIndex:n];
  }
  (void) [outArg appendWithChar:'<'];
  (void) [outArg appendWithChar:'/'];
  (void) [outArg appendWithNSString:name];
  (void) [outArg appendWithChar:'>'];
  (void) [outArg appendWithNSString:RAREUTXMLUtils_lineSeparator_];
}

+ (void)makeElementStringWithJavaIoWriter:(JavaIoWriter *)outArg
                             withNSString:(NSString *)name
                             withNSString:(NSString *)value
                                  withInt:(int)depth
                          withJavaUtilMap:(id<JavaUtilMap>)attributes {
  [RAREUTHelper writePaddingWithJavaIoWriter:outArg withInt:depth];
  if (value == nil) {
    [((JavaIoWriter *) nil_chk(outArg)) writeWithInt:'<'];
    [outArg writeWithNSString:name];
    [RAREUTXMLUtils dumpAttributesWithJavaIoWriter:outArg withJavaUtilMap:attributes];
    [outArg writeWithNSString:@"/>"];
    [outArg writeWithNSString:RAREUTXMLUtils_lineSeparator_];
    return;
  }
  [((JavaIoWriter *) nil_chk(outArg)) writeWithInt:'<'];
  [outArg writeWithNSString:name];
  [RAREUTXMLUtils dumpAttributesWithJavaIoWriter:outArg withJavaUtilMap:attributes];
  [outArg writeWithInt:'>'];
  if (([((NSString *) nil_chk(value)) sequenceLength] > 60)) {
    [outArg writeWithNSString:RAREUTXMLUtils_lineSeparator_];
    [RAREUTHelper writePaddingWithJavaIoWriter:outArg withInt:depth + 1];
    [outArg writeWithNSString:value];
    [outArg writeWithNSString:RAREUTXMLUtils_lineSeparator_];
    [RAREUTHelper writePaddingWithJavaIoWriter:outArg withInt:depth];
  }
  else {
    [outArg writeWithNSString:value];
  }
  int n = [((NSString *) nil_chk(name)) indexOf:' '];
  if (n != -1) {
    name = [name substring:0 endIndex:n];
  }
  [outArg writeWithInt:'<'];
  [outArg writeWithInt:'/'];
  [outArg writeWithNSString:name];
  [outArg writeWithInt:'>'];
  [outArg writeWithNSString:RAREUTXMLUtils_lineSeparator_];
}

+ (void)writeSpacesWithJavaIoWriter:(JavaIoWriter *)outArg
                            withInt:(int)spaces {
  if (spaces == 0) {
    return;
  }
  int len = (int) [((IOSCharArray *) nil_chk(RAREUTXMLUtils_padding_)) count];
  while (spaces > len) {
    [((JavaIoWriter *) nil_chk(outArg)) writeWithCharArray:RAREUTXMLUtils_padding_ withInt:0 withInt:len];
    spaces -= len;
  }
  if (spaces > 0) {
    [((JavaIoWriter *) nil_chk(outArg)) writeWithCharArray:RAREUTXMLUtils_padding_ withInt:0 withInt:spaces];
  }
}

+ (RAREUTCharArray *)decodeWithRAREUTCharArray:(RAREUTCharArray *)value
                                       withInt:(int)start
                                       withInt:(int)len
                           withRAREUTCharArray:(RAREUTCharArray *)outArg {
  int pos;
  if (outArg == nil) {
    outArg = [[RAREUTCharArray alloc] initWithInt:len];
  }
  if ((pos = [RAREUTXMLUtils indexOfWithCharArray:((RAREUTCharArray *) nil_chk(value))->A_ withInt:start withInt:len withChar:'&']) == -1) {
    (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithCharArray:value->A_ withInt:start withInt:start + len];
    return outArg;
  }
  int n;
  unichar c;
  do {
    n = pos - start;
    (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithCharArray:value->A_ withInt:start withInt:n];
    start = pos + 1;
    len -= (n + 1);
    n = [RAREUTXMLUtils indexOfWithCharArray:value->A_ withInt:start withInt:len withChar:';'];
    if (n == -1) {
      (void) [outArg appendWithCharArray:value->A_ withInt:start withInt:len];
      break;
    }
    c = [RAREUTXMLUtils decodeCharacterWithRAREUTCharArray:value withInt:start withInt:n - start];
    if (c != 0) {
      (void) [outArg appendWithChar:c];
    }
    len -= ((n + 1) - start);
    start = n + 1;
    pos = [RAREUTXMLUtils indexOfWithCharArray:value->A_ withInt:start withInt:len withChar:'&'];
    if (pos == -1) {
      (void) [outArg appendWithCharArray:value->A_ withInt:start withInt:len];
      len = 0;
    }
  }
  while (len > 0);
  return outArg;
}

+ (int)indexOfWithCharArray:(IOSCharArray *)chars
                    withInt:(int)pos
                    withInt:(int)len
                   withChar:(unichar)c {
  unichar d = 0;
  BOOL bInQuote = NO;
  int i = pos;
  int n = pos + len;
  if (pos >= n) {
    return -1;
  }
  while (i < n) {
    d = IOSCharArray_Get(nil_chk(chars), i);
    if ((d == c) && !bInQuote) {
      return i;
    }
    if (d == '"') {
      if (bInQuote) {
        if ((i + 1) < n) {
          if (IOSCharArray_Get(chars, i + 1) == '"') {
            i++;
          }
          else {
            bInQuote = NO;
          }
        }
      }
      else {
        bInQuote = YES;
      }
    }
    i++;
  }
  return -1;
}

+ (void)encodeChararacterWithChar:(unichar)c
              withRAREUTCharArray:(RAREUTCharArray *)outArg {
  if ((c < 40) || (c > 126)) {
    (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithChar:'&'];
    (void) [outArg appendWithChar:'#'];
    (void) [outArg appendWithInt:(int) c];
    (void) [outArg appendWithChar:';'];
  }
  else {
    (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithChar:c];
  }
}

+ (void)escapeChararacterWithChar:(unichar)c
              withRAREUTCharArray:(RAREUTCharArray *)outArg
                      withBoolean:(BOOL)whitespace {
  if (c == '<') {
    (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithNSString:@"&lt;"];
  }
  else if (c == '>') {
    (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithNSString:@"&gt;"];
  }
  else if (c == '"') {
    (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithNSString:@"&quot;"];
  }
  else if (c == '&') {
    (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithNSString:@"&amp;"];
  }
  else if (c == ' ') {
    if (!whitespace) {
      (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithChar:' '];
    }
    else if (((RAREUTCharArray *) nil_chk(outArg))->_length_ == 0) {
      (void) [outArg appendWithNSString:@"&nbsp;"];
    }
    else if ((IOSCharArray_Get(nil_chk(outArg->A_), outArg->_length_ - 1) == ' ') || (IOSCharArray_Get(outArg->A_, outArg->_length_ - 1) == '>')) {
      (void) [outArg appendWithNSString:@"&nbsp;"];
    }
    else {
      (void) [outArg appendWithChar:' '];
    }
  }
  else if (c == 0x0009) {
    if (whitespace) {
      (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithNSString:@"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"];
    }
    else {
      (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithChar:0x0009];
    }
  }
  else if (c == 0x000a) {
    if (whitespace) {
      (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithNSString:@"<br/>"];
    }
    else {
      (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithChar:0x000a];
    }
  }
  else {
    if ((c < 40) || (c > 126)) {
      (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithChar:'&'];
      (void) [outArg appendWithChar:'#'];
      (void) [outArg appendWithInt:(int) c];
      (void) [outArg appendWithChar:';'];
    }
    else {
      (void) [((RAREUTCharArray *) nil_chk(outArg)) appendWithChar:c];
    }
  }
}

- (id)init {
  return [super init];
}

+ (void)initialize {
  if (self == [RAREUTXMLUtils class]) {
    RAREUTXMLUtils_amplt_ = [IOSCharArray arrayWithCharacters:(unichar[]){ 'l', 't' } count:2];
    RAREUTXMLUtils_ampgt_ = [IOSCharArray arrayWithCharacters:(unichar[]){ 'g', 't' } count:2];
    RAREUTXMLUtils_ampquot_ = [IOSCharArray arrayWithCharacters:(unichar[]){ 'q', 'u', 'o', 't' } count:4];
    RAREUTXMLUtils_ampapos_ = [IOSCharArray arrayWithCharacters:(unichar[]){ 'a', 'p', 'o', 's' } count:4];
    RAREUTXMLUtils_ampamp_ = [IOSCharArray arrayWithCharacters:(unichar[]){ 'a', 'm', 'p' } count:3];
    RAREUTXMLUtils_padding_ = [RAREUTHelper getPadding];
    RAREUTXMLUtils_perThreadCharArray_ = [[RAREUTXMLUtils_$1 alloc] init];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "decodeWithRAREUTCharArray:", NULL, "LNSString", 0x9, NULL },
    { "decodeWithNSString:", NULL, "LNSString", 0x9, NULL },
    { "dumpAttributesWithJavaIoWriter:withJavaUtilMap:", NULL, "V", 0x9, "JavaIoIOException" },
    { "encodeWithNSString:", NULL, "LNSString", 0x9, NULL },
    { "encodeWithNSString:withRAREUTCharArray:", NULL, "LRAREUTCharArray", 0x9, NULL },
    { "encodeWithCharArray:withInt:withInt:withRAREUTCharArray:", NULL, "LRAREUTCharArray", 0x9, NULL },
    { "escapeWithNSString:", NULL, "LNSString", 0x9, NULL },
    { "escapeWithNSString:withBoolean:", NULL, "LNSString", 0x9, NULL },
    { "escapeWithNSString:withBoolean:withRAREUTCharArray:", NULL, "LRAREUTCharArray", 0x9, NULL },
    { "escapeWithCharArray:withInt:withInt:withBoolean:", NULL, "LNSString", 0x9, NULL },
    { "escapeWithCharArray:withInt:withInt:withBoolean:withRAREUTCharArray:", NULL, "LRAREUTCharArray", 0x9, NULL },
    { "makeElementStringWithJavaIoWriter:withNSString:withNSString:withInt:withJavaUtilMap:", NULL, "V", 0x9, "JavaIoIOException" },
    { "writeSpacesWithJavaIoWriter:withInt:", NULL, "V", 0x9, "JavaIoIOException" },
    { "decodeWithRAREUTCharArray:withInt:withInt:withRAREUTCharArray:", NULL, "LRAREUTCharArray", 0x8, NULL },
    { "indexOfWithCharArray:withInt:withInt:withChar:", NULL, "I", 0x8, NULL },
    { "encodeChararacterWithChar:withRAREUTCharArray:", NULL, "V", 0xa, NULL },
    { "escapeChararacterWithChar:withRAREUTCharArray:withBoolean:", NULL, "V", 0xa, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "lineSeparator_", NULL, 0x19, "LNSString" },
    { "amplt_", NULL, 0x1a, "LIOSCharArray" },
    { "ampgt_", NULL, 0x1a, "LIOSCharArray" },
    { "ampquot_", NULL, 0x1a, "LIOSCharArray" },
    { "ampapos_", NULL, 0x1a, "LIOSCharArray" },
    { "ampamp_", NULL, 0x1a, "LIOSCharArray" },
    { "padding_", NULL, 0x1a, "LIOSCharArray" },
    { "perThreadCharArray_", NULL, 0xa, "LJavaLangThreadLocal" },
  };
  static J2ObjcClassInfo _RAREUTXMLUtils = { "XMLUtils", "com.appnativa.util.xml", NULL, 0x1, 17, methods, 8, fields, 0, NULL};
  return &_RAREUTXMLUtils;
}

@end
@implementation RAREUTXMLUtils_$1

- (id)initialValue {
  @synchronized(self) {
    {
      return [[RAREUTCharArray alloc] initWithInt:32];
    }
  }
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initialValue", NULL, "LNSObject", 0x24, NULL },
  };
  static J2ObjcClassInfo _RAREUTXMLUtils_$1 = { "$1", "com.appnativa.util.xml", "XMLUtils", 0x8000, 1, methods, 0, NULL, 0, NULL};
  return &_RAREUTXMLUtils_$1;
}

@end
