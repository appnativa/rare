//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/org/ccil/cowan/tagsoup/AttributesImpl.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "java/lang/ArrayIndexOutOfBoundsException.h"
#include "java/lang/System.h"
#include "org/ccil/cowan/tagsoup/AttributesImpl.h"
#include "org/xml/sax/Attributes.h"

@implementation RAREAttributesImpl

- (id)init {
  if (self = [super init]) {
    length_ = 0;
    data_ = nil;
  }
  return self;
}

- (id)initWithOrgXmlSaxAttributes:(id<OrgXmlSaxAttributes>)atts {
  if (self = [super init]) {
    [self setAttributesWithOrgXmlSaxAttributes:atts];
  }
  return self;
}

- (int)getLength {
  return length_;
}

- (NSString *)getURIWithInt:(int)index {
  if (index >= 0 && index < length_) {
    return IOSObjectArray_Get(nil_chk(data_), index * 5);
  }
  else {
    return nil;
  }
}

- (NSString *)getLocalNameWithInt:(int)index {
  if (index >= 0 && index < length_) {
    return IOSObjectArray_Get(nil_chk(data_), index * 5 + 1);
  }
  else {
    return nil;
  }
}

- (NSString *)getQNameWithInt:(int)index {
  if (index >= 0 && index < length_) {
    return IOSObjectArray_Get(nil_chk(data_), index * 5 + 2);
  }
  else {
    return nil;
  }
}

- (NSString *)getTypeWithInt:(int)index {
  if (index >= 0 && index < length_) {
    return IOSObjectArray_Get(nil_chk(data_), index * 5 + 3);
  }
  else {
    return nil;
  }
}

- (NSString *)getValueWithInt:(int)index {
  if (index >= 0 && index < length_) {
    return IOSObjectArray_Get(nil_chk(data_), index * 5 + 4);
  }
  else {
    return nil;
  }
}

- (int)getIndexWithNSString:(NSString *)uri
               withNSString:(NSString *)localName {
  int max = length_ * 5;
  for (int i = 0; i < max; i += 5) {
    if ([((NSString *) IOSObjectArray_Get(nil_chk(data_), i)) isEqual:uri] && [((NSString *) IOSObjectArray_Get(data_, i + 1)) isEqual:localName]) {
      return i / 5;
    }
  }
  return -1;
}

- (int)getIndexWithNSString:(NSString *)qName {
  int max = length_ * 5;
  for (int i = 0; i < max; i += 5) {
    if ([((NSString *) IOSObjectArray_Get(nil_chk(data_), i + 2)) isEqual:qName]) {
      return i / 5;
    }
  }
  return -1;
}

- (NSString *)getTypeWithNSString:(NSString *)uri
                     withNSString:(NSString *)localName {
  int max = length_ * 5;
  for (int i = 0; i < max; i += 5) {
    if ([((NSString *) IOSObjectArray_Get(nil_chk(data_), i)) isEqual:uri] && [((NSString *) IOSObjectArray_Get(data_, i + 1)) isEqual:localName]) {
      return IOSObjectArray_Get(data_, i + 3);
    }
  }
  return nil;
}

- (NSString *)getTypeWithNSString:(NSString *)qName {
  int max = length_ * 5;
  for (int i = 0; i < max; i += 5) {
    if ([((NSString *) IOSObjectArray_Get(nil_chk(data_), i + 2)) isEqual:qName]) {
      return IOSObjectArray_Get(data_, i + 3);
    }
  }
  return nil;
}

- (NSString *)getValueWithNSString:(NSString *)uri
                      withNSString:(NSString *)localName {
  int max = length_ * 5;
  for (int i = 0; i < max; i += 5) {
    if ([((NSString *) IOSObjectArray_Get(nil_chk(data_), i)) isEqual:uri] && [((NSString *) IOSObjectArray_Get(data_, i + 1)) isEqual:localName]) {
      return IOSObjectArray_Get(data_, i + 4);
    }
  }
  return nil;
}

- (NSString *)getValueWithNSString:(NSString *)qName {
  int max = length_ * 5;
  for (int i = 0; i < max; i += 5) {
    if ([((NSString *) IOSObjectArray_Get(nil_chk(data_), i + 2)) isEqual:qName]) {
      return IOSObjectArray_Get(data_, i + 4);
    }
  }
  return nil;
}

- (void)clear {
  if (data_ != nil) {
    for (int i = 0; i < (length_ * 5); i++) (void) IOSObjectArray_Set(data_, i, nil);
  }
  length_ = 0;
}

- (void)setAttributesWithOrgXmlSaxAttributes:(id<OrgXmlSaxAttributes>)atts {
  [self clear];
  length_ = [((id<OrgXmlSaxAttributes>) nil_chk(atts)) getLength];
  if (length_ > 0) {
    data_ = [IOSObjectArray arrayWithLength:length_ * 5 type:[IOSClass classWithClass:[NSString class]]];
    for (int i = 0; i < length_; i++) {
      (void) IOSObjectArray_Set(data_, i * 5, [atts getURIWithInt:i]);
      (void) IOSObjectArray_Set(data_, i * 5 + 1, [atts getLocalNameWithInt:i]);
      (void) IOSObjectArray_Set(data_, i * 5 + 2, [atts getQNameWithInt:i]);
      (void) IOSObjectArray_Set(data_, i * 5 + 3, [atts getTypeWithInt:i]);
      (void) IOSObjectArray_Set(data_, i * 5 + 4, [atts getValueWithInt:i]);
    }
  }
}

- (void)addAttributeWithNSString:(NSString *)uri
                    withNSString:(NSString *)localName
                    withNSString:(NSString *)qName
                    withNSString:(NSString *)type
                    withNSString:(NSString *)value {
  [self ensureCapacityWithInt:length_ + 1];
  (void) IOSObjectArray_Set(nil_chk(data_), length_ * 5, uri);
  (void) IOSObjectArray_Set(data_, length_ * 5 + 1, localName);
  (void) IOSObjectArray_Set(data_, length_ * 5 + 2, qName);
  (void) IOSObjectArray_Set(data_, length_ * 5 + 3, type);
  (void) IOSObjectArray_Set(data_, length_ * 5 + 4, value);
  length_++;
}

- (void)setAttributeWithInt:(int)index
               withNSString:(NSString *)uri
               withNSString:(NSString *)localName
               withNSString:(NSString *)qName
               withNSString:(NSString *)type
               withNSString:(NSString *)value {
  if (index >= 0 && index < length_) {
    (void) IOSObjectArray_Set(nil_chk(data_), index * 5, uri);
    (void) IOSObjectArray_Set(data_, index * 5 + 1, localName);
    (void) IOSObjectArray_Set(data_, index * 5 + 2, qName);
    (void) IOSObjectArray_Set(data_, index * 5 + 3, type);
    (void) IOSObjectArray_Set(data_, index * 5 + 4, value);
  }
  else {
    [self badIndexWithInt:index];
  }
}

- (void)removeAttributeWithInt:(int)index {
  if (index >= 0 && index < length_) {
    if (index < length_ - 1) {
      [JavaLangSystem arraycopyWithId:data_ withInt:(index + 1) * 5 withId:data_ withInt:index * 5 withInt:(length_ - index - 1) * 5];
    }
    index = (length_ - 1) * 5;
    (void) IOSObjectArray_Set(nil_chk(data_), index++, nil);
    (void) IOSObjectArray_Set(data_, index++, nil);
    (void) IOSObjectArray_Set(data_, index++, nil);
    (void) IOSObjectArray_Set(data_, index++, nil);
    (void) IOSObjectArray_Set(data_, index, nil);
    length_--;
  }
  else {
    [self badIndexWithInt:index];
  }
}

- (void)setURIWithInt:(int)index
         withNSString:(NSString *)uri {
  if (index >= 0 && index < length_) {
    (void) IOSObjectArray_Set(nil_chk(data_), index * 5, uri);
  }
  else {
    [self badIndexWithInt:index];
  }
}

- (void)setLocalNameWithInt:(int)index
               withNSString:(NSString *)localName {
  if (index >= 0 && index < length_) {
    (void) IOSObjectArray_Set(nil_chk(data_), index * 5 + 1, localName);
  }
  else {
    [self badIndexWithInt:index];
  }
}

- (void)setQNameWithInt:(int)index
           withNSString:(NSString *)qName {
  if (index >= 0 && index < length_) {
    (void) IOSObjectArray_Set(nil_chk(data_), index * 5 + 2, qName);
  }
  else {
    [self badIndexWithInt:index];
  }
}

- (void)setTypeWithInt:(int)index
          withNSString:(NSString *)type {
  if (index >= 0 && index < length_) {
    (void) IOSObjectArray_Set(nil_chk(data_), index * 5 + 3, type);
  }
  else {
    [self badIndexWithInt:index];
  }
}

- (void)setValueWithInt:(int)index
           withNSString:(NSString *)value {
  if (index >= 0 && index < length_) {
    (void) IOSObjectArray_Set(nil_chk(data_), index * 5 + 4, value);
  }
  else {
    [self badIndexWithInt:index];
  }
}

- (void)ensureCapacityWithInt:(int)n {
  if (n <= 0) {
    return;
  }
  int max;
  if (data_ == nil || (int) [data_ count] == 0) {
    max = 25;
  }
  else if ((int) [data_ count] >= n * 5) {
    return;
  }
  else {
    max = (int) [data_ count];
  }
  while (max < n * 5) {
    max *= 2;
  }
  IOSObjectArray *newData = [IOSObjectArray arrayWithLength:max type:[IOSClass classWithClass:[NSString class]]];
  if (length_ > 0) {
    [JavaLangSystem arraycopyWithId:data_ withInt:0 withId:newData withInt:0 withInt:length_ * 5];
  }
  data_ = newData;
}

- (void)badIndexWithInt:(int)index {
  NSString *msg = [NSString stringWithFormat:@"Attempt to modify attribute at illegal index: %d", index];
  @throw [[JavaLangArrayIndexOutOfBoundsException alloc] initWithNSString:msg];
}

- (void)copyAllFieldsTo:(RAREAttributesImpl *)other {
  [super copyAllFieldsTo:other];
  other->data_ = data_;
  other->length_ = length_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getURIWithInt:", NULL, "LNSString", 0x1, NULL },
    { "getLocalNameWithInt:", NULL, "LNSString", 0x1, NULL },
    { "getQNameWithInt:", NULL, "LNSString", 0x1, NULL },
    { "getTypeWithInt:", NULL, "LNSString", 0x1, NULL },
    { "getValueWithInt:", NULL, "LNSString", 0x1, NULL },
    { "getTypeWithNSString:withNSString:", NULL, "LNSString", 0x1, NULL },
    { "getTypeWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "getValueWithNSString:withNSString:", NULL, "LNSString", 0x1, NULL },
    { "getValueWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "ensureCapacityWithInt:", NULL, "V", 0x2, NULL },
    { "badIndexWithInt:", NULL, "V", 0x2, "JavaLangArrayIndexOutOfBoundsException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "length_", NULL, 0x0, "I" },
    { "data_", NULL, 0x0, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RAREAttributesImpl = { "AttributesImpl", "org.ccil.cowan.tagsoup", NULL, 0x1, 11, methods, 2, fields, 0, NULL};
  return &_RAREAttributesImpl;
}

@end
