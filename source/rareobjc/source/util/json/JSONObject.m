//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-json/com/appnativa/util/json/JSONObject.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/util/SNumber.h"
#include "com/appnativa/util/json/JSONArray.h"
#include "com/appnativa/util/json/JSONException.h"
#include "com/appnativa/util/json/JSONObject.h"
#include "com/appnativa/util/json/JSONString.h"
#include "com/appnativa/util/json/JSONTokener.h"
#include "java/io/IOException.h"
#include "java/io/Writer.h"
#include "java/lang/Boolean.h"
#include "java/lang/Double.h"
#include "java/lang/Exception.h"
#include "java/lang/Float.h"
#include "java/lang/Integer.h"
#include "java/lang/Long.h"
#include "java/lang/StringBuilder.h"
#include "java/lang/reflect/Field.h"
#include "java/util/Arrays.h"
#include "java/util/Collection.h"
#include "java/util/HashMap.h"
#include "java/util/Iterator.h"
#include "java/util/List.h"
#include "java/util/Map.h"
#include "java/util/Set.h"
#include "java/util/TreeSet.h"

@implementation RAREUTJSONObject

static BOOL RAREUTJSONObject_QUOTE_FOR_7BIT_ = NO;
static id RAREUTJSONObject_NULL__;

+ (BOOL)QUOTE_FOR_7BIT {
  return RAREUTJSONObject_QUOTE_FOR_7BIT_;
}

+ (BOOL *)QUOTE_FOR_7BITRef {
  return &RAREUTJSONObject_QUOTE_FOR_7BIT_;
}

+ (id)getNULL {
  return RAREUTJSONObject_NULL__;
}

- (id)initRAREUTJSONObject {
  if (self = [super init]) {
    self->map_ = [[JavaUtilHashMap alloc] init];
  }
  return self;
}

- (id)init {
  return [self initRAREUTJSONObject];
}

- (id)initWithRAREUTJSONObject:(RAREUTJSONObject *)jo
             withNSStringArray:(IOSObjectArray *)names {
  if (self = [self initRAREUTJSONObject]) {
    for (int i = 0; i < (int) [((IOSObjectArray *) nil_chk(names)) count]; i += 1) {
      (void) [self putOnceWithNSString:IOSObjectArray_Get(names, i) withId:[((RAREUTJSONObject *) nil_chk(jo)) optWithNSString:IOSObjectArray_Get(names, i)]];
    }
  }
  return self;
}

- (id)initRAREUTJSONObjectWithRAREUTJSONTokener:(RAREUTJSONTokener *)x {
  return [self initRAREUTJSONObjectWithNSString:nil withRAREUTJSONTokener:x];
}

- (id)initWithRAREUTJSONTokener:(RAREUTJSONTokener *)x {
  return [self initRAREUTJSONObjectWithRAREUTJSONTokener:x];
}

- (id)initRAREUTJSONObjectWithNSString:(NSString *)name
                 withRAREUTJSONTokener:(RAREUTJSONTokener *)x {
  if (self = [self initRAREUTJSONObject]) {
    unichar c;
    NSString *key;
    self->name_ = name;
    id<RAREUTJSONTokener_iWatcher> watcher = [((RAREUTJSONTokener *) nil_chk(x)) getWatcher];
    NSString *valueName;
    BOOL usePath = [x isUsePathAsName];
    if ([x nextClean] != '{') {
      @throw [x syntaxErrorWithNSString:@"A JSONObject text must begin with '{'"];
    }
    if (watcher != nil) {
      [watcher willParseObjectWithRAREUTJSONObject:self];
    }
    @try {
      for (; ; ) {
        c = [x nextClean];
        switch (c) {
          case 0:
          @throw [x syntaxErrorWithNSString:@"A JSONObject text must end with '}'"];
          case '}':
          return self;
          default:
          [x back];
          key = [nil_chk([x nextValueWithNSString:nil]) description];
        }
        c = [x nextClean];
        if (c == '=') {
          if ([x next] != '>') {
            [x back];
          }
        }
        else if (c != ':') {
          @throw [x syntaxErrorWithNSString:@"Expected a ':' after a key"];
        }
        valueName = usePath ? [x makePathNameWithNSString:name withNSString:key] : key;
        if (watcher != nil && ![watcher keyEncounteredWithRAREUTJSONObject:self withNSString:valueName]) {
          [x setTerminateParsingWithBoolean:YES];
          return self;
        }
        id value = [x nextValueWithNSString:valueName];
        if (watcher != nil) {
          value = [watcher valueEncounteredWithRAREUTJSONObject:self withNSString:valueName withId:value];
          if ([x isTerminateParsing]) {
            return self;
          }
          if (value != nil) {
            (void) [self putOnceWithNSString:key withId:value];
          }
        }
        else {
          (void) [self putOnceWithNSString:key withId:value];
        }
        switch ([x nextClean]) {
          case ';':
          case ',':
          if ([x nextClean] == '}') {
            return self;
          }
          [x back];
          break;
          case '}':
          return self;
          default:
          @throw [x syntaxErrorWithNSString:@"Expected a ',' or '}'"];
        }
      }
    }
    @finally {
      if (watcher != nil) {
        [watcher didParseObjectWithRAREUTJSONObject:self];
      }
    }
  }
  return self;
}

- (id)initWithNSString:(NSString *)name
 withRAREUTJSONTokener:(RAREUTJSONTokener *)x {
  return [self initRAREUTJSONObjectWithNSString:name withRAREUTJSONTokener:x];
}

- (id)initWithJavaUtilMap:(id<JavaUtilMap>)map {
  if (self = [super init]) {
    self->map_ = (map == nil) ? [[JavaUtilHashMap alloc] init] : ((id) map);
  }
  return self;
}

- (id)clone {
  return [[RAREUTJSONObject alloc] initWithJavaUtilMap:[[JavaUtilHashMap alloc] initWithJavaUtilMap:self->map_]];
}

- (id)initWithId:(id)object
withNSStringArray:(IOSObjectArray *)names {
  if (self = [self initRAREUTJSONObject]) {
    IOSClass *c = [nil_chk(object) getClass];
    for (int i = 0; i < (int) [((IOSObjectArray *) nil_chk(names)) count]; i += 1) {
      NSString *name = IOSObjectArray_Get(names, i);
      @try {
        (void) [self putOptWithNSString:name withId:[((JavaLangReflectField *) nil_chk([c getField:name])) getWithId:object]];
      }
      @catch (JavaLangException *e) {
      }
    }
  }
  return self;
}

- (id)initWithNSString:(NSString *)source {
  return [self initRAREUTJSONObjectWithRAREUTJSONTokener:[[RAREUTJSONTokener alloc] initWithNSString:source]];
}

- (RAREUTJSONObject *)accumulateWithNSString:(NSString *)key
                                      withId:(id)value {
  [RAREUTJSONObject testValidityWithId:value];
  id o = [self optWithNSString:key];
  if (o == nil) {
    (void) [self putWithNSString:key withId:[value isKindOfClass:[RAREUTJSONArray class]] ? [((RAREUTJSONArray *) [[RAREUTJSONArray alloc] init]) putWithId:value] : value];
  }
  else if ([o isKindOfClass:[RAREUTJSONArray class]]) {
    (void) [((RAREUTJSONArray *) check_class_cast(o, [RAREUTJSONArray class])) putWithId:value];
  }
  else {
    (void) [self putWithNSString:key withJavaUtilCollection:[((RAREUTJSONArray *) nil_chk([((RAREUTJSONArray *) [[RAREUTJSONArray alloc] init]) putWithId:o])) putWithId:value]];
  }
  return self;
}

- (RAREUTJSONObject *)appendWithNSString:(NSString *)key
                                  withId:(id)value {
  [RAREUTJSONObject testValidityWithId:value];
  id o = [self optWithNSString:key];
  if (o == nil) {
    (void) [self putWithNSString:key withJavaUtilCollection:[((RAREUTJSONArray *) [[RAREUTJSONArray alloc] init]) putWithId:value]];
  }
  else if ([o isKindOfClass:[RAREUTJSONArray class]]) {
    (void) [self putWithNSString:key withJavaUtilCollection:[((RAREUTJSONArray *) check_class_cast(o, [RAREUTJSONArray class])) putWithId:value]];
  }
  else {
    @throw [[RAREUTJSONException alloc] initWithNSString:[NSString stringWithFormat:@"JSONObject[%@] is not a JSONArray.", key]];
  }
  return self;
}

+ (NSString *)doubleToStringWithDouble:(double)d {
  if ([JavaLangDouble isInfiniteWithDouble:d] || [JavaLangDouble isNaNWithDouble:d]) {
    return @"null";
  }
  NSString *s = [JavaLangDouble toStringWithDouble:d];
  if ([((NSString *) nil_chk(s)) indexOf:'.'] > 0 && [s indexOf:'e'] < 0 && [s indexOf:'E'] < 0) {
    while ([s hasSuffix:@"0"]) {
      s = [s substring:0 endIndex:[s sequenceLength] - 1];
    }
    if ([((NSString *) nil_chk(s)) hasSuffix:@"."]) {
      s = [s substring:0 endIndex:[s sequenceLength] - 1];
    }
  }
  return s;
}

- (BOOL)getBooleanWithNSString:(NSString *)key {
  id o = [self getWithId:key];
  if (o != nil) {
    if ([o isEqual:[JavaLangBoolean getFALSE]] || ([o isKindOfClass:[NSString class]] && [((NSString *) check_class_cast(o, [NSString class])) equalsIgnoreCase:@"false"])) {
      return NO;
    }
    else if ([o isEqual:[JavaLangBoolean getTRUE]] || ([o isKindOfClass:[NSString class]] && [((NSString *) check_class_cast(o, [NSString class])) equalsIgnoreCase:@"true"])) {
      return YES;
    }
  }
  @throw [[RAREUTJSONException alloc] initWithNSString:[NSString stringWithFormat:@"JSONObject[%@] is not a Boolean.", [RAREUTJSONObject quoteWithNSString:key]]];
}

- (double)getDoubleWithNSString:(NSString *)key {
  id o = [self getWithId:key];
  @try {
    if ([o isKindOfClass:[NSString class]]) {
      return [RAREUTSNumber doubleValueWithNSString:(NSString *) check_class_cast(o, [NSString class])];
    }
    return [((NSNumber *) check_class_cast(o, [NSNumber class])) doubleValue];
  }
  @catch (JavaLangException *e) {
    @throw [[RAREUTJSONException alloc] initWithNSString:[NSString stringWithFormat:@"JSONObject[%@] is not a number.", [RAREUTJSONObject quoteWithNSString:key]]];
  }
}

- (int)getIntWithNSString:(NSString *)key {
  id o = [self getWithId:key];
  return [o isKindOfClass:[NSNumber class]] ? [((NSNumber *) check_class_cast(o, [NSNumber class])) intValue] : (int) [self getDoubleWithNSString:key];
}

- (RAREUTJSONArray *)getJSONArrayWithNSString:(NSString *)key {
  id o = [self getWithId:key];
  if ([o isKindOfClass:[RAREUTJSONArray class]]) {
    return (RAREUTJSONArray *) check_class_cast(o, [RAREUTJSONArray class]);
  }
  if (o == nil) {
    return nil;
  }
  @throw [[RAREUTJSONException alloc] initWithNSString:[NSString stringWithFormat:@"JSONObject[%@] is not a JSONArray.", [RAREUTJSONObject quoteWithNSString:key]]];
}

- (RAREUTJSONObject *)getJSONObjectWithNSString:(NSString *)key {
  id o = [self getWithId:key];
  if ([o isKindOfClass:[RAREUTJSONObject class]]) {
    return (RAREUTJSONObject *) check_class_cast(o, [RAREUTJSONObject class]);
  }
  if (o == nil) {
    return nil;
  }
  @throw [[RAREUTJSONException alloc] initWithNSString:[NSString stringWithFormat:@"JSONObject[%@] is not a JSONObject.", [RAREUTJSONObject quoteWithNSString:key]]];
}

- (long long int)getLongWithNSString:(NSString *)key {
  id o = [self getWithId:key];
  return [o isKindOfClass:[NSNumber class]] ? [((NSNumber *) check_class_cast(o, [NSNumber class])) longLongValue] : (long long int) [self getDoubleWithNSString:key];
}

+ (IOSObjectArray *)getNamesWithRAREUTJSONObject:(RAREUTJSONObject *)jo {
  int length = [((RAREUTJSONObject *) nil_chk(jo)) length];
  if (length == 0) {
    return nil;
  }
  id<JavaUtilIterator> i = [jo keys];
  IOSObjectArray *names = [IOSObjectArray arrayWithLength:length type:[IOSClass classWithClass:[NSString class]]];
  int j = 0;
  while ([((id<JavaUtilIterator>) nil_chk(i)) hasNext]) {
    (void) IOSObjectArray_Set(names, j, (NSString *) check_class_cast([i next], [NSString class]));
    j += 1;
  }
  return names;
}

- (NSString *)getName {
  return name_;
}

- (NSString *)getStringWithNSString:(NSString *)key {
  return [nil_chk([self getWithId:key]) description];
}

- (BOOL)hasWithNSString:(NSString *)key {
  return [((id<JavaUtilMap>) nil_chk(self->map_)) containsKeyWithId:key];
}

- (BOOL)isNullWithNSString:(NSString *)key {
  return [nil_chk(RAREUTJSONObject_NULL__) isEqual:[self optWithNSString:key]];
}

- (id<JavaUtilIterator>)keys {
  return [((id<JavaUtilSet>) nil_chk([((id<JavaUtilMap>) nil_chk(self->map_)) keySet])) iterator];
}

- (int)length {
  return [((id<JavaUtilMap>) nil_chk(self->map_)) size];
}

- (RAREUTJSONArray *)names {
  RAREUTJSONArray *ja = [[RAREUTJSONArray alloc] init];
  id<JavaUtilIterator> keys = [self keys];
  while ([((id<JavaUtilIterator>) nil_chk(keys)) hasNext]) {
    (void) [ja putWithId:[keys next]];
  }
  return [ja length] == 0 ? nil : ja;
}

+ (NSString *)numberToStringWithNSNumber:(NSNumber *)n {
  if (n == nil) {
    @throw [[RAREUTJSONException alloc] initWithNSString:@"Null pointer"];
  }
  [RAREUTJSONObject testValidityWithId:n];
  NSString *s = [((NSNumber *) nil_chk(n)) description];
  if ([((NSString *) nil_chk(s)) indexOf:'.'] > 0 && [s indexOf:'e'] < 0 && [s indexOf:'E'] < 0) {
    while ([s hasSuffix:@"0"]) {
      s = [s substring:0 endIndex:[s sequenceLength] - 1];
    }
    if ([((NSString *) nil_chk(s)) hasSuffix:@"."]) {
      s = [s substring:0 endIndex:[s sequenceLength] - 1];
    }
  }
  return s;
}

- (id)optWithNSString:(NSString *)key {
  return key == nil ? nil : [((id<JavaUtilMap>) nil_chk(self->map_)) getWithId:key];
}

- (BOOL)optBooleanWithNSString:(NSString *)key {
  return [self optBooleanWithNSString:key withBoolean:NO];
}

- (BOOL)optBooleanWithNSString:(NSString *)key
                   withBoolean:(BOOL)defaultValue {
  id o = [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  if (o != nil) {
    if ([o isEqual:[JavaLangBoolean getFALSE]] || ([o isKindOfClass:[NSString class]] && [((NSString *) check_class_cast(o, [NSString class])) equalsIgnoreCase:@"false"])) {
      return NO;
    }
    else if ([o isEqual:[JavaLangBoolean getTRUE]] || ([o isKindOfClass:[NSString class]] && [((NSString *) check_class_cast(o, [NSString class])) equalsIgnoreCase:@"true"])) {
      return YES;
    }
  }
  return defaultValue;
}

- (RAREUTJSONObject *)putWithNSString:(NSString *)key
               withJavaUtilCollection:(id<JavaUtilCollection>)value {
  if ([(id) value isKindOfClass:[RAREUTJSONArray class]]) {
    (void) [((id<JavaUtilMap>) nil_chk(map_)) putWithId:key withId:value];
  }
  else {
    (void) [((id<JavaUtilMap>) nil_chk(map_)) putWithId:key withId:[[RAREUTJSONArray alloc] initWithJavaUtilCollection:value]];
  }
  return self;
}

- (double)optDoubleWithNSString:(NSString *)key {
  return [self optDoubleWithNSString:key withDouble:JavaLangDouble_NaN];
}

- (double)optDoubleWithNSString:(NSString *)key
                     withDouble:(double)defaultValue {
  @try {
    id o = [self optWithNSString:key];
    if (o != nil) {
      return [o isKindOfClass:[NSNumber class]] ? [((NSNumber *) check_class_cast(o, [NSNumber class])) doubleValue] : [((JavaLangDouble *) [[JavaLangDouble alloc] initWithNSString:(NSString *) check_class_cast(o, [NSString class])]) doubleValue];
    }
  }
  @catch (JavaLangException *e) {
  }
  return defaultValue;
}

- (int)optIntWithNSString:(NSString *)key {
  return [self optIntWithNSString:key withInt:0];
}

- (int)optIntWithNSString:(NSString *)key
                  withInt:(int)defaultValue {
  @try {
    if ([((id<JavaUtilMap>) nil_chk(map_)) containsKeyWithId:key]) {
      return [self getIntWithNSString:key];
    }
  }
  @catch (JavaLangException *e) {
  }
  return defaultValue;
}

- (RAREUTJSONArray *)optJSONArrayWithNSString:(NSString *)key {
  id o = [self optWithNSString:key];
  return [o isKindOfClass:[RAREUTJSONArray class]] ? (RAREUTJSONArray *) check_class_cast(o, [RAREUTJSONArray class]) : nil;
}

- (RAREUTJSONObject *)optJSONObjectWithNSString:(NSString *)key {
  id o = [self optWithNSString:key];
  return [o isKindOfClass:[RAREUTJSONObject class]] ? (RAREUTJSONObject *) check_class_cast(o, [RAREUTJSONObject class]) : nil;
}

- (long long int)optLongWithNSString:(NSString *)key {
  return [self optLongWithNSString:key withLong:0];
}

- (long long int)optLongWithNSString:(NSString *)key
                            withLong:(long long int)defaultValue {
  @try {
    if ([((id<JavaUtilMap>) nil_chk(map_)) containsKeyWithId:key]) {
      return [self getLongWithNSString:key];
    }
  }
  @catch (JavaLangException *e) {
  }
  return defaultValue;
}

- (NSString *)optStringWithNSString:(NSString *)key {
  return [self optStringWithNSString:key withNSString:@""];
}

- (NSString *)optStringWithNSString:(NSString *)key
                       withNSString:(NSString *)defaultValue {
  id o = [self optWithNSString:key];
  return o != nil ? [o description] : defaultValue;
}

- (RAREUTJSONObject *)putWithNSString:(NSString *)key
                          withBoolean:(BOOL)value {
  (void) [self putWithNSString:key withId:value ? [JavaLangBoolean getTRUE] : [JavaLangBoolean getFALSE]];
  return self;
}

- (RAREUTJSONObject *)putWithNSString:(NSString *)key
                           withDouble:(double)value {
  (void) [self putWithNSString:key withId:[[JavaLangDouble alloc] initWithDouble:value]];
  return self;
}

- (RAREUTJSONObject *)putWithNSString:(NSString *)key
                              withInt:(int)value {
  (void) [self putWithNSString:key withId:[JavaLangLong valueOfWithLong:value]];
  return self;
}

- (RAREUTJSONObject *)putWithNSString:(NSString *)key
                             withLong:(long long int)value {
  (void) [self putWithNSString:key withId:[JavaLangLong valueOfWithLong:value]];
  return self;
}

- (RAREUTJSONObject *)putWithNSString:(NSString *)key
                      withJavaUtilMap:(id<JavaUtilMap>)value {
  if (key == nil) {
    @throw [[RAREUTJSONException alloc] initWithNSString:@"Null key."];
  }
  if (value != nil) {
    if (!([(id) value isKindOfClass:[RAREUTJSONObject class]])) {
      value = [[RAREUTJSONObject alloc] initWithJavaUtilMap:value];
    }
    (void) [((id<JavaUtilMap>) nil_chk(self->map_)) putWithId:key withId:value];
  }
  else {
    (void) [self removeWithNSString:key];
  }
  return self;
}

- (RAREUTJSONObject *)putWithNSString:(NSString *)key
                               withId:(id)value {
  if (key == nil) {
    @throw [[RAREUTJSONException alloc] initWithNSString:@"Null key."];
  }
  if (value != nil) {
    [RAREUTJSONObject testValidityWithId:value];
    (void) [((id<JavaUtilMap>) nil_chk(self->map_)) putWithId:key withId:value];
  }
  else {
    (void) [self removeWithNSString:key];
  }
  return self;
}

- (RAREUTJSONObject *)putOnceWithNSString:(NSString *)key
                                   withId:(id)value {
  if (key != nil && value != nil) {
    if ([self optWithNSString:key] != nil) {
      @throw [[RAREUTJSONException alloc] initWithNSString:[NSString stringWithFormat:@"Duplicate key \"%@\"", key]];
    }
    (void) [self putWithNSString:key withId:value];
  }
  return self;
}

- (RAREUTJSONObject *)putOptWithNSString:(NSString *)key
                                  withId:(id)value {
  if (key != nil && value != nil) {
    (void) [self putWithNSString:key withId:value];
  }
  return self;
}

+ (NSString *)quoteWithNSString:(NSString *)string {
  if (string == nil || [string sequenceLength] == 0) {
    return @"\"\"";
  }
  unichar b;
  unichar c = 0;
  int i;
  int len = [((NSString *) nil_chk(string)) sequenceLength];
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] initWithInt:len + 4];
  NSString *t;
  (void) [sb appendWithChar:'"'];
  for (i = 0; i < len; i += 1) {
    b = c;
    c = [string charAtWithInt:i];
    switch (c) {
      case '\\':
      case '"':
      (void) [sb appendWithChar:'\\'];
      (void) [sb appendWithChar:c];
      break;
      case '/':
      if (b == '<') {
        (void) [sb appendWithChar:'\\'];
      }
      (void) [sb appendWithChar:c];
      break;
      case 0x0008:
      (void) [sb appendWithNSString:@"\\b"];
      break;
      case 0x0009:
      (void) [sb appendWithNSString:@"\\t"];
      break;
      case 0x000a:
      (void) [sb appendWithNSString:@"\\n"];
      break;
      case 0x000c:
      (void) [sb appendWithNSString:@"\\f"];
      break;
      case 0x000d:
      (void) [sb appendWithNSString:@"\\r"];
      break;
      default:
      if (c < ' ' || (c > 126 && RAREUTJSONObject_QUOTE_FOR_7BIT_) || (c >= 0x0080 && c < 0x00a0) || (c >= 0x2000 && c < 0x2100)) {
        t = [NSString stringWithFormat:@"000%@", [JavaLangInteger toHexStringWithInt:c]];
        (void) [sb appendWithNSString:[NSString stringWithFormat:@"\\u%@", [t substring:[t sequenceLength] - 4]]];
      }
      else {
        (void) [sb appendWithChar:c];
      }
    }
  }
  (void) [sb appendWithChar:'"'];
  return [sb description];
}

- (id)removeWithNSString:(NSString *)key {
  return [((id<JavaUtilMap>) nil_chk(self->map_)) removeWithId:key];
}

- (id<JavaUtilIterator>)sortedKeys {
  return [((JavaUtilTreeSet *) [[JavaUtilTreeSet alloc] initWithJavaUtilCollection:[((id<JavaUtilMap>) nil_chk(self->map_)) keySet]]) iterator];
}

+ (id)stringToValueWithNSString:(NSString *)s {
  if ([((NSString *) nil_chk(s)) isEqual:@""]) {
    return s;
  }
  if ([s equalsIgnoreCase:@"true"]) {
    return [JavaLangBoolean getTRUE];
  }
  if ([s equalsIgnoreCase:@"false"]) {
    return [JavaLangBoolean getFALSE];
  }
  if ([s equalsIgnoreCase:@"null"]) {
    return RAREUTJSONObject_NULL__;
  }
  unichar b = [s charAtWithInt:0];
  if ((b >= '0' && b <= '9') || b == '.' || b == '-' || b == '+') {
    if (b == '0') {
      if ([s sequenceLength] > 2 && ([s charAtWithInt:1] == 'x' || [s charAtWithInt:1] == 'X')) {
        @try {
          return [JavaLangLong valueOfWithLong:[JavaLangLong parseLongWithNSString:[s substring:2] withInt:16]];
        }
        @catch (JavaLangException *e) {
        }
      }
      else {
        @try {
          return [JavaLangLong valueOfWithLong:[JavaLangLong parseLongWithNSString:s withInt:8]];
        }
        @catch (JavaLangException *e) {
        }
      }
    }
    @try {
      return [[RAREUTSNumber alloc] initWithNSString:s];
    }
    @catch (JavaLangException *f) {
    }
  }
  return s;
}

+ (void)testValidityWithId:(id)o {
  if (o != nil) {
    if ([o isKindOfClass:[JavaLangDouble class]]) {
      if ([((JavaLangDouble *) check_class_cast(o, [JavaLangDouble class])) isInfinite] || [((JavaLangDouble *) check_class_cast(o, [JavaLangDouble class])) isNaN]) {
        @throw [[RAREUTJSONException alloc] initWithNSString:@"JSON does not allow non-finite numbers."];
      }
    }
    else if ([o isKindOfClass:[JavaLangFloat class]]) {
      if ([((JavaLangFloat *) check_class_cast(o, [JavaLangFloat class])) isInfinite] || [((JavaLangFloat *) check_class_cast(o, [JavaLangFloat class])) isNaN]) {
        @throw [[RAREUTJSONException alloc] initWithNSString:@"JSON does not allow non-finite numbers."];
      }
    }
  }
}

- (RAREUTJSONArray *)toJSONArrayWithRAREUTJSONArray:(RAREUTJSONArray *)names {
  if (names == nil || [names length] == 0) {
    return nil;
  }
  RAREUTJSONArray *ja = [[RAREUTJSONArray alloc] init];
  for (int i = 0; i < [((RAREUTJSONArray *) nil_chk(names)) length]; i += 1) {
    (void) [ja putWithId:[self optWithNSString:[names getStringWithInt:i]]];
  }
  return ja;
}

- (NSString *)description {
  @try {
    id<JavaUtilIterator> keys = [self keys];
    JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] initWithNSString:@"{"];
    while ([((id<JavaUtilIterator>) nil_chk(keys)) hasNext]) {
      if ([sb sequenceLength] > 1) {
        (void) [sb appendWithChar:','];
      }
      id o = [keys next];
      (void) [sb appendWithNSString:[RAREUTJSONObject quoteWithNSString:[nil_chk(o) description]]];
      (void) [sb appendWithChar:':'];
      (void) [sb appendWithNSString:[RAREUTJSONObject valueToStringWithId:[((id<JavaUtilMap>) nil_chk(self->map_)) getWithId:o]]];
    }
    (void) [sb appendWithChar:'}'];
    return [sb description];
  }
  @catch (JavaLangException *e) {
    return nil;
  }
}

- (NSString *)toStringWithInt:(int)indentFactor {
  return [self toStringWithInt:indentFactor withInt:0];
}

- (NSString *)toStringWithInt:(int)indentFactor
                      withInt:(int)indent {
  int j;
  int n = [self length];
  if (n == 0) {
    return @"{}";
  }
  id<JavaUtilIterator> keys = [self sortedKeys];
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] initWithNSString:@"{"];
  int newindent = indent + indentFactor;
  id o;
  if (n == 1) {
    o = [((id<JavaUtilIterator>) nil_chk(keys)) next];
    (void) [sb appendWithNSString:[RAREUTJSONObject quoteWithNSString:[nil_chk(o) description]]];
    (void) [sb appendWithNSString:@": "];
    (void) [sb appendWithNSString:[RAREUTJSONObject valueToStringWithId:[((id<JavaUtilMap>) nil_chk(self->map_)) getWithId:o] withInt:indentFactor withInt:indent]];
  }
  else {
    while ([((id<JavaUtilIterator>) nil_chk(keys)) hasNext]) {
      o = [keys next];
      if ([sb sequenceLength] > 1) {
        (void) [sb appendWithNSString:@",\n"];
      }
      else {
        (void) [sb appendWithChar:0x000a];
      }
      for (j = 0; j < newindent; j += 1) {
        (void) [sb appendWithChar:' '];
      }
      (void) [sb appendWithNSString:[RAREUTJSONObject quoteWithNSString:[nil_chk(o) description]]];
      (void) [sb appendWithNSString:@": "];
      (void) [sb appendWithNSString:[RAREUTJSONObject valueToStringWithId:[((id<JavaUtilMap>) nil_chk(self->map_)) getWithId:o] withInt:indentFactor withInt:newindent]];
    }
    if ([sb sequenceLength] > 1) {
      (void) [sb appendWithChar:0x000a];
      for (j = 0; j < indent; j += 1) {
        (void) [sb appendWithChar:' '];
      }
    }
  }
  (void) [sb appendWithChar:'}'];
  return [sb description];
}

+ (NSString *)valueToStringWithId:(id)value {
  if (value == nil || [value isEqual:nil]) {
    return @"null";
  }
  if ([value conformsToProtocol: @protocol(RAREUTJSONString)]) {
    id o;
    @try {
      o = [((id<RAREUTJSONString>) check_protocol_cast(value, @protocol(RAREUTJSONString))) toJSONString];
    }
    @catch (JavaLangException *e) {
      @throw [[RAREUTJSONException alloc] initWithJavaLangThrowable:e];
    }
    if ([o isKindOfClass:[NSString class]]) {
      return (NSString *) check_class_cast(o, [NSString class]);
    }
    @throw [[RAREUTJSONException alloc] initWithNSString:[NSString stringWithFormat:@"Bad value from toJSONString: %@", o]];
  }
  if ([value isKindOfClass:[NSNumber class]]) {
    return [RAREUTJSONObject numberToStringWithNSNumber:(NSNumber *) check_class_cast(value, [NSNumber class])];
  }
  if ([value isKindOfClass:[JavaLangBoolean class]] || [value isKindOfClass:[RAREUTJSONObject class]] || [value isKindOfClass:[RAREUTJSONArray class]]) {
    return [nil_chk(value) description];
  }
  if ([value conformsToProtocol: @protocol(JavaUtilMap)]) {
    return [((RAREUTJSONObject *) [[RAREUTJSONObject alloc] initWithJavaUtilMap:(id<JavaUtilMap>) check_protocol_cast(value, @protocol(JavaUtilMap))]) description];
  }
  if ([value conformsToProtocol: @protocol(JavaUtilCollection)]) {
    return [((RAREUTJSONArray *) [[RAREUTJSONArray alloc] initWithJavaUtilCollection:(id<JavaUtilCollection>) check_protocol_cast(value, @protocol(JavaUtilCollection))]) description];
  }
  if ([[IOSObjectArray iosClassWithType:[IOSClass classWithClass:[NSObject class]]] isInstance:value]) {
    return [((RAREUTJSONArray *) [[RAREUTJSONArray alloc] initWithJavaUtilList:[JavaUtilArrays asListWithNSObjectArray:(IOSObjectArray *) check_class_cast(value, [IOSObjectArray class])]]) description];
  }
  return [RAREUTJSONObject quoteWithNSString:[nil_chk(value) description]];
}

+ (NSString *)valueToStringWithId:(id)value
                          withInt:(int)indentFactor
                          withInt:(int)indent {
  if (value == nil || [value isEqual:nil]) {
    return @"null";
  }
  @try {
    if ([value conformsToProtocol: @protocol(RAREUTJSONString)]) {
      id o = [((id<RAREUTJSONString>) check_protocol_cast(value, @protocol(RAREUTJSONString))) toJSONString];
      if ([o isKindOfClass:[NSString class]]) {
        return (NSString *) check_class_cast(o, [NSString class]);
      }
    }
  }
  @catch (JavaLangException *e) {
  }
  if ([value isKindOfClass:[NSNumber class]]) {
    return [RAREUTJSONObject numberToStringWithNSNumber:(NSNumber *) check_class_cast(value, [NSNumber class])];
  }
  if ([value isKindOfClass:[JavaLangBoolean class]]) {
    return [nil_chk(value) description];
  }
  if ([value isKindOfClass:[RAREUTJSONObject class]]) {
    return [((RAREUTJSONObject *) check_class_cast(value, [RAREUTJSONObject class])) toStringWithInt:indentFactor withInt:indent];
  }
  if ([value isKindOfClass:[RAREUTJSONArray class]]) {
    return [((RAREUTJSONArray *) check_class_cast(value, [RAREUTJSONArray class])) toStringWithInt:indentFactor withInt:indent];
  }
  if ([value conformsToProtocol: @protocol(JavaUtilMap)]) {
    return [((RAREUTJSONObject *) [[RAREUTJSONObject alloc] initWithJavaUtilMap:(id<JavaUtilMap>) check_protocol_cast(value, @protocol(JavaUtilMap))]) toStringWithInt:indentFactor withInt:indent];
  }
  if ([value conformsToProtocol: @protocol(JavaUtilCollection)]) {
    return [((RAREUTJSONArray *) [[RAREUTJSONArray alloc] initWithJavaUtilCollection:(id<JavaUtilCollection>) check_protocol_cast(value, @protocol(JavaUtilCollection))]) toStringWithInt:indentFactor withInt:indent];
  }
  if ([[IOSObjectArray iosClassWithType:[IOSClass classWithClass:[NSObject class]]] isInstance:value]) {
    return [((RAREUTJSONArray *) [[RAREUTJSONArray alloc] initWithJavaUtilList:[JavaUtilArrays asListWithNSObjectArray:(IOSObjectArray *) check_class_cast(value, [IOSObjectArray class])]]) description];
  }
  return [RAREUTJSONObject quoteWithNSString:[nil_chk(value) description]];
}

- (JavaIoWriter *)writeWithJavaIoWriter:(JavaIoWriter *)writer {
  @try {
    BOOL b = NO;
    id<JavaUtilIterator> keys = [self keys];
    [((JavaIoWriter *) nil_chk(writer)) writeWithInt:'{'];
    while ([((id<JavaUtilIterator>) nil_chk(keys)) hasNext]) {
      if (b) {
        [writer writeWithInt:','];
      }
      id k = [keys next];
      [writer writeWithNSString:[RAREUTJSONObject quoteWithNSString:[nil_chk(k) description]]];
      [writer writeWithInt:':'];
      id v = [((id<JavaUtilMap>) nil_chk(self->map_)) getWithId:k];
      if ([v isKindOfClass:[RAREUTJSONObject class]]) {
        (void) [((RAREUTJSONObject *) check_class_cast(v, [RAREUTJSONObject class])) writeWithJavaIoWriter:writer];
      }
      else if ([v isKindOfClass:[RAREUTJSONArray class]]) {
        (void) [((RAREUTJSONArray *) check_class_cast(v, [RAREUTJSONArray class])) writeWithJavaIoWriter:writer];
      }
      else {
        [writer writeWithNSString:[RAREUTJSONObject valueToStringWithId:v]];
      }
      b = YES;
    }
    [writer writeWithInt:'}'];
    return writer;
  }
  @catch (JavaIoIOException *e) {
    @throw [[RAREUTJSONException alloc] initWithJavaLangThrowable:e];
  }
}

- (id<JavaUtilCollection>)values {
  return [((id<JavaUtilMap>) nil_chk(map_)) values];
}

- (int)size {
  return [((id<JavaUtilMap>) nil_chk(map_)) size];
}

- (id)removeWithId:(id)key {
  return [((id<JavaUtilMap>) nil_chk(map_)) removeWithId:key];
}

- (void)putAllWithJavaUtilMap:(id<JavaUtilMap>)m {
  [((id<JavaUtilMap>) nil_chk(map_)) putAllWithJavaUtilMap:m];
}

- (id)putWithId:(id)key
         withId:(id)value {
  return [((id<JavaUtilMap>) nil_chk(map_)) putWithId:key withId:value];
}

- (id<JavaUtilSet>)keySet {
  return [((id<JavaUtilMap>) nil_chk(map_)) keySet];
}

- (BOOL)isEmpty {
  return [((id<JavaUtilMap>) nil_chk(map_)) isEmpty];
}

- (NSUInteger)hash {
  return [((id<JavaUtilMap>) nil_chk(map_)) hash];
}

- (id)optWithNSStringArray:(IOSObjectArray *)key {
  int len = (int) [((IOSObjectArray *) nil_chk(key)) count];
  if (len == 1) {
    return [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  }
  RAREUTJSONObject *o = (RAREUTJSONObject *) check_class_cast([((id<JavaUtilMap>) nil_chk(map_)) getWithId:IOSObjectArray_Get(key, 0)], [RAREUTJSONObject class]);
  if (o == nil) {
    return nil;
  }
  for (int i = 1; o != nil && i < len - 1; i++) {
    o = [((RAREUTJSONObject *) nil_chk(o)) optJSONObjectWithNSString:IOSObjectArray_Get(key, i)];
    if (o == nil) {
      return nil;
    }
  }
  return [((RAREUTJSONObject *) nil_chk(o)) optWithNSString:IOSObjectArray_Get(key, len - 1)];
}

- (id)getWithId:(id)key {
  return [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
}

- (BOOL)isEqual:(id)o {
  return [((id<JavaUtilMap>) nil_chk(map_)) isEqual:o];
}

- (id<JavaUtilSet>)entrySet {
  return [((id<JavaUtilMap>) nil_chk(map_)) entrySet];
}

- (BOOL)containsValueWithId:(id)value {
  return [((id<JavaUtilMap>) nil_chk(map_)) containsValueWithId:value];
}

- (BOOL)containsKeyWithId:(id)key {
  return [((id<JavaUtilMap>) nil_chk(map_)) containsKeyWithId:key];
}

- (void)clear {
  [((id<JavaUtilMap>) nil_chk(map_)) clear];
}

- (id<JavaUtilMap>)getObjectMap {
  return map_;
}

+ (void)initialize {
  if (self == [RAREUTJSONObject class]) {
    RAREUTJSONObject_NULL__ = [[RAREUTJSONObject_Null alloc] init];
  }
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREUTJSONObject *)other {
  [super copyAllFieldsTo:other];
  other->map_ = map_;
  other->name_ = name_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithRAREUTJSONObject:withNSStringArray:", NULL, NULL, 0x1, "RAREUTJSONException" },
    { "initWithRAREUTJSONTokener:", NULL, NULL, 0x1, "RAREUTJSONException" },
    { "initWithNSString:withRAREUTJSONTokener:", NULL, NULL, 0x1, "RAREUTJSONException" },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "initWithNSString:", NULL, NULL, 0x1, "RAREUTJSONException" },
    { "accumulateWithNSString:withId:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "appendWithNSString:withId:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "doubleToStringWithDouble:", NULL, "LNSString", 0x9, NULL },
    { "getBooleanWithNSString:", NULL, "Z", 0x1, "RAREUTJSONException" },
    { "getDoubleWithNSString:", NULL, "D", 0x1, "RAREUTJSONException" },
    { "getIntWithNSString:", NULL, "I", 0x1, "RAREUTJSONException" },
    { "getJSONArrayWithNSString:", NULL, "LRAREUTJSONArray", 0x1, "RAREUTJSONException" },
    { "getJSONObjectWithNSString:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "getLongWithNSString:", NULL, "J", 0x1, "RAREUTJSONException" },
    { "getNamesWithRAREUTJSONObject:", NULL, "LIOSObjectArray", 0x9, NULL },
    { "getName", NULL, "LNSString", 0x1, NULL },
    { "getStringWithNSString:", NULL, "LNSString", 0x1, "RAREUTJSONException" },
    { "hasWithNSString:", NULL, "Z", 0x1, NULL },
    { "isNullWithNSString:", NULL, "Z", 0x1, NULL },
    { "keys", NULL, "LJavaUtilIterator", 0x1, NULL },
    { "names", NULL, "LRAREUTJSONArray", 0x1, NULL },
    { "numberToStringWithNSNumber:", NULL, "LNSString", 0x9, "RAREUTJSONException" },
    { "optWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "optBooleanWithNSString:", NULL, "Z", 0x1, NULL },
    { "optBooleanWithNSString:withBoolean:", NULL, "Z", 0x1, NULL },
    { "putWithNSString:withJavaUtilCollection:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "optJSONArrayWithNSString:", NULL, "LRAREUTJSONArray", 0x1, NULL },
    { "optJSONObjectWithNSString:", NULL, "LRAREUTJSONObject", 0x1, NULL },
    { "optStringWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "optStringWithNSString:withNSString:", NULL, "LNSString", 0x1, NULL },
    { "putWithNSString:withBoolean:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "putWithNSString:withDouble:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "putWithNSString:withInt:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "putWithNSString:withLong:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "putWithNSString:withJavaUtilMap:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "putWithNSString:withId:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "putOnceWithNSString:withId:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "putOptWithNSString:withId:", NULL, "LRAREUTJSONObject", 0x1, "RAREUTJSONException" },
    { "quoteWithNSString:", NULL, "LNSString", 0x9, NULL },
    { "removeWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "sortedKeys", NULL, "LJavaUtilIterator", 0x1, NULL },
    { "stringToValueWithNSString:", NULL, "LNSObject", 0x9, NULL },
    { "testValidityWithId:", NULL, "V", 0x8, "RAREUTJSONException" },
    { "toJSONArrayWithRAREUTJSONArray:", NULL, "LRAREUTJSONArray", 0x1, "RAREUTJSONException" },
    { "toStringWithInt:", NULL, "LNSString", 0x1, "RAREUTJSONException" },
    { "toStringWithInt:withInt:", NULL, "LNSString", 0x0, "RAREUTJSONException" },
    { "valueToStringWithId:", NULL, "LNSString", 0x8, "RAREUTJSONException" },
    { "valueToStringWithId:withInt:withInt:", NULL, "LNSString", 0x8, "RAREUTJSONException" },
    { "writeWithJavaIoWriter:", NULL, "LJavaIoWriter", 0x1, "RAREUTJSONException" },
    { "values", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "removeWithId:", NULL, "LNSObject", 0x1, NULL },
    { "putWithId:withId:", NULL, "LNSObject", 0x1, NULL },
    { "keySet", NULL, "LJavaUtilSet", 0x1, NULL },
    { "isEmpty", NULL, "Z", 0x1, NULL },
    { "optWithNSStringArray:", NULL, "LNSObject", 0x81, NULL },
    { "getWithId:", NULL, "LNSObject", 0x1, NULL },
    { "entrySet", NULL, "LJavaUtilSet", 0x1, NULL },
    { "containsValueWithId:", NULL, "Z", 0x1, NULL },
    { "containsKeyWithId:", NULL, "Z", 0x1, NULL },
    { "getObjectMap", NULL, "LJavaUtilMap", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "QUOTE_FOR_7BIT_", NULL, 0x9, "Z" },
    { "map_", NULL, 0x4, "LJavaUtilMap" },
    { "name_", NULL, 0x4, "LNSString" },
    { "NULL__", "NULL", 0x19, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREUTJSONObject = { "JSONObject", "com.appnativa.util.json", NULL, 0x1, 60, methods, 4, fields, 0, NULL};
  return &_RAREUTJSONObject;
}

@end
@implementation RAREUTJSONObject_Null

- (id)clone {
  return self;
}

- (BOOL)isEqual:(id)object {
  return object == nil || object == self;
}

- (NSString *)description {
  return @"null";
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clone", NULL, "LNSObject", 0x14, NULL },
  };
  static J2ObjcClassInfo _RAREUTJSONObject_Null = { "Null", "com.appnativa.util.json", "JSONObject", 0x1a, 1, methods, 0, NULL, 0, NULL};
  return &_RAREUTJSONObject_Null;
}

@end
