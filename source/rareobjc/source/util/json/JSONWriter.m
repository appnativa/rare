//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-json/com/appnativa/util/json/JSONWriter.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/util/json/JSONException.h"
#include "com/appnativa/util/json/JSONObject.h"
#include "com/appnativa/util/json/JSONWriter.h"
#include "java/io/IOException.h"
#include "java/io/Writer.h"
#include "java/lang/Boolean.h"
#include "java/lang/Double.h"
#include "java/lang/Long.h"

@implementation RAREUTJSONWriter

- (id)initWithJavaIoWriter:(JavaIoWriter *)w {
  if (self = [super init]) {
    self->comma_ = NO;
    self->mode_ = 'i';
    self->stack_ = [IOSObjectArray arrayWithLength:RAREUTJSONWriter_maxdepth type:[IOSClass classWithClass:[RAREUTJSONObject class]]];
    self->top_ = 0;
    self->writer_ = w;
  }
  return self;
}

- (RAREUTJSONWriter *)appendWithNSString:(NSString *)s {
  if (s == nil) {
    @throw [[RAREUTJSONException alloc] initWithNSString:@"Null pointer"];
  }
  if (self->mode_ == 'o' || self->mode_ == 'a') {
    @try {
      if (self->comma_ && self->mode_ == 'a') {
        [((JavaIoWriter *) nil_chk(self->writer_)) writeWithInt:','];
      }
      [((JavaIoWriter *) nil_chk(self->writer_)) writeWithNSString:s];
    }
    @catch (JavaIoIOException *e) {
      @throw [[RAREUTJSONException alloc] initWithJavaLangThrowable:e];
    }
    if (self->mode_ == 'o') {
      self->mode_ = 'k';
    }
    self->comma_ = YES;
    return self;
  }
  @throw [[RAREUTJSONException alloc] initWithNSString:@"Value out of sequence."];
}

- (RAREUTJSONWriter *)array {
  if (self->mode_ == 'i' || self->mode_ == 'o' || self->mode_ == 'a') {
    [self pushWithRAREUTJSONObject:nil];
    (void) [self appendWithNSString:@"["];
    self->comma_ = NO;
    return self;
  }
  @throw [[RAREUTJSONException alloc] initWithNSString:@"Misplaced array."];
}

- (RAREUTJSONWriter *)endWithChar:(unichar)m
                         withChar:(unichar)c {
  if (self->mode_ != m) {
    @throw [[RAREUTJSONException alloc] initWithNSString:m == 'o' ? @"Misplaced endObject." : @"Misplaced endArray."];
  }
  [self popWithChar:m];
  @try {
    [((JavaIoWriter *) nil_chk(self->writer_)) writeWithInt:c];
  }
  @catch (JavaIoIOException *e) {
    @throw [[RAREUTJSONException alloc] initWithJavaLangThrowable:e];
  }
  self->comma_ = YES;
  return self;
}

- (RAREUTJSONWriter *)endArray {
  return [self endWithChar:'a' withChar:']'];
}

- (RAREUTJSONWriter *)endObject {
  return [self endWithChar:'k' withChar:'}'];
}

- (RAREUTJSONWriter *)keyWithNSString:(NSString *)s {
  if (s == nil) {
    @throw [[RAREUTJSONException alloc] initWithNSString:@"Null key."];
  }
  if (self->mode_ == 'k') {
    @try {
      (void) [((RAREUTJSONObject *) IOSObjectArray_Get(nil_chk(stack_), top_ - 1)) putOnceWithNSString:s withId:[JavaLangBoolean getTRUE]];
      if (self->comma_) {
        [((JavaIoWriter *) nil_chk(self->writer_)) writeWithInt:','];
      }
      [((JavaIoWriter *) nil_chk(self->writer_)) writeWithNSString:[RAREUTJSONObject quoteWithNSString:s]];
      [self->writer_ writeWithInt:':'];
      self->comma_ = NO;
      self->mode_ = 'o';
      return self;
    }
    @catch (JavaIoIOException *e) {
      @throw [[RAREUTJSONException alloc] initWithJavaLangThrowable:e];
    }
  }
  @throw [[RAREUTJSONException alloc] initWithNSString:@"Misplaced key."];
}

- (RAREUTJSONWriter *)object {
  if (self->mode_ == 'i') {
    self->mode_ = 'o';
  }
  if (self->mode_ == 'o' || self->mode_ == 'a') {
    (void) [self appendWithNSString:@"{"];
    [self pushWithRAREUTJSONObject:[[RAREUTJSONObject alloc] init]];
    self->comma_ = NO;
    return self;
  }
  @throw [[RAREUTJSONException alloc] initWithNSString:@"Misplaced object."];
}

- (void)popWithChar:(unichar)c {
  if (self->top_ <= 0) {
    @throw [[RAREUTJSONException alloc] initWithNSString:@"Nesting error."];
  }
  unichar m = IOSObjectArray_Get(nil_chk(self->stack_), self->top_ - 1) == nil ? 'a' : 'k';
  if (m != c) {
    @throw [[RAREUTJSONException alloc] initWithNSString:@"Nesting error."];
  }
  self->top_ -= 1;
  self->mode_ = self->top_ == 0 ? 'd' : IOSObjectArray_Get(self->stack_, self->top_ - 1) == nil ? 'a' : 'k';
}

- (void)pushWithRAREUTJSONObject:(RAREUTJSONObject *)jo {
  if (self->top_ >= RAREUTJSONWriter_maxdepth) {
    @throw [[RAREUTJSONException alloc] initWithNSString:@"Nesting too deep."];
  }
  (void) IOSObjectArray_Set(nil_chk(self->stack_), self->top_, jo);
  self->mode_ = jo == nil ? 'a' : 'k';
  self->top_ += 1;
}

- (RAREUTJSONWriter *)valueWithBoolean:(BOOL)b {
  return [self appendWithNSString:b ? @"true" : @"false"];
}

- (RAREUTJSONWriter *)valueWithDouble:(double)d {
  return [self valueWithId:[[JavaLangDouble alloc] initWithDouble:d]];
}

- (RAREUTJSONWriter *)valueWithLong:(long long int)l {
  return [self appendWithNSString:[JavaLangLong toStringWithLong:l]];
}

- (RAREUTJSONWriter *)valueWithId:(id)o {
  return [self appendWithNSString:[RAREUTJSONObject valueToStringWithId:o]];
}

- (void)copyAllFieldsTo:(RAREUTJSONWriter *)other {
  [super copyAllFieldsTo:other];
  other->comma_ = comma_;
  other->mode_ = mode_;
  other->stack_ = stack_;
  other->top_ = top_;
  other->writer_ = writer_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "appendWithNSString:", NULL, "LRAREUTJSONWriter", 0x2, "RAREUTJSONException" },
    { "array", NULL, "LRAREUTJSONWriter", 0x1, "RAREUTJSONException" },
    { "endWithChar:withChar:", NULL, "LRAREUTJSONWriter", 0x2, "RAREUTJSONException" },
    { "endArray", NULL, "LRAREUTJSONWriter", 0x1, "RAREUTJSONException" },
    { "endObject", NULL, "LRAREUTJSONWriter", 0x1, "RAREUTJSONException" },
    { "keyWithNSString:", NULL, "LRAREUTJSONWriter", 0x1, "RAREUTJSONException" },
    { "object", NULL, "LRAREUTJSONWriter", 0x1, "RAREUTJSONException" },
    { "popWithChar:", NULL, "V", 0x2, "RAREUTJSONException" },
    { "pushWithRAREUTJSONObject:", NULL, "V", 0x2, "RAREUTJSONException" },
    { "valueWithBoolean:", NULL, "LRAREUTJSONWriter", 0x1, "RAREUTJSONException" },
    { "valueWithDouble:", NULL, "LRAREUTJSONWriter", 0x1, "RAREUTJSONException" },
    { "valueWithLong:", NULL, "LRAREUTJSONWriter", 0x1, "RAREUTJSONException" },
    { "valueWithId:", NULL, "LRAREUTJSONWriter", 0x1, "RAREUTJSONException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "maxdepth_", NULL, 0x1a, "I" },
    { "mode_", NULL, 0x4, "C" },
    { "writer_", NULL, 0x4, "LJavaIoWriter" },
  };
  static J2ObjcClassInfo _RAREUTJSONWriter = { "JSONWriter", "com.appnativa.util.json", NULL, 0x1, 13, methods, 3, fields, 0, NULL};
  return &_RAREUTJSONWriter;
}

@end
