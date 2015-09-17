//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/text/Segment.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSCharArray.h"
#include "com/appnativa/rare/ui/text/Segment.h"
#include "java/lang/CharSequence.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/StringIndexOutOfBoundsException.h"
#include "java/text/CharacterIterator.h"

@implementation RARESegment

- (id)init {
  return [self initRARESegmentWithCharArray:nil withInt:0 withInt:0];
}

- (id)initRARESegmentWithCharArray:(IOSCharArray *)array
                           withInt:(int)offset
                           withInt:(int)count {
  if (self = [super init]) {
    self->array_ = array;
    self->offset_ = offset;
    self->count_ = count;
    self->pos_ = 0;
    self->isPartial_ = NO;
  }
  return self;
}

- (id)initWithCharArray:(IOSCharArray *)array
                withInt:(int)offset
                withInt:(int)count {
  return [self initRARESegmentWithCharArray:array withInt:offset withInt:count];
}

- (id)clone {
  id clone;
  @try {
    clone = [super clone];
  }
  @catch (JavaLangCloneNotSupportedException *e) {
    clone = nil;
  }
  return clone;
}

- (unichar)current {
  if ((pos_ < 0) || (pos_ >= count_ + offset_)) {
    return JavaTextCharacterIterator_DONE;
  }
  return IOSCharArray_Get(nil_chk(array_), pos_);
}

- (unichar)first {
  pos_ = offset_;
  if ([self isEmpty]) {
    return JavaTextCharacterIterator_DONE;
  }
  return IOSCharArray_Get(nil_chk(array_), pos_);
}

- (int)getBeginIndex {
  return offset_;
}

- (int)getEndIndex {
  return offset_ + count_;
}

- (int)getIndex {
  return pos_;
}

- (BOOL)isPartialReturn {
  return isPartial_;
}

- (unichar)last {
  if ([self isEmpty]) {
    pos_ = offset_ + count_;
    return JavaTextCharacterIterator_DONE;
  }
  pos_ = offset_ + count_ - 1;
  return IOSCharArray_Get(nil_chk(array_), pos_);
}

- (unichar)next {
  pos_++;
  if (pos_ >= offset_ + count_) {
    pos_ = offset_ + count_;
    return JavaTextCharacterIterator_DONE;
  }
  return IOSCharArray_Get(nil_chk(array_), pos_);
}

- (unichar)previous {
  if (pos_ == offset_) {
    return JavaTextCharacterIterator_DONE;
  }
  return IOSCharArray_Get(nil_chk(array_), --pos_);
}

- (unichar)setIndexWithInt:(int)position {
  if ((position < 0) || (position > offset_ + count_)) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:[NSString stringWithFormat:@"position:%d", position]];
  }
  pos_ = position;
  if (position == offset_ + count_) {
    return JavaTextCharacterIterator_DONE;
  }
  return IOSCharArray_Get(nil_chk(array_), pos_);
}

- (void)setPartialReturnWithBoolean:(BOOL)p {
  isPartial_ = p;
  return;
}

- (NSString *)sequenceDescription {
  return (array_ != nil) ? [NSString stringWithCharacters:array_ offset:offset_ length:count_] : @"";
}

- (BOOL)isEmpty {
  if ((count_ == 0) || (array_ == nil) || ((int) [array_ count] == 0)) {
    return YES;
  }
  return NO;
}

- (unichar)charAtWithInt:(int)index {
  if ((index < offset_) || (index >= (offset_ + count_))) {
    @throw [[JavaLangStringIndexOutOfBoundsException alloc] initWithInt:index];
  }
  return IOSCharArray_Get(nil_chk(array_), offset_ + index);
}

- (int)sequenceLength {
  return count_;
}

- (id<JavaLangCharSequence>)subSequenceFrom:(int)start to:(int)end {
  if ((start < offset_) || (start >= (offset_ + count_))) {
    @throw [[JavaLangStringIndexOutOfBoundsException alloc] initWithInt:start];
  }
  if ((end < offset_) || (end >= (offset_ + count_))) {
    @throw [[JavaLangStringIndexOutOfBoundsException alloc] initWithInt:end];
  }
  if (start > end) {
    @throw [[JavaLangStringIndexOutOfBoundsException alloc] initWithInt:start];
  }
  return [[RARESegment alloc] initWithCharArray:array_ withInt:offset_ + start withInt:end - start];
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RARESegment *)other {
  [super copyAllFieldsTo:other];
  other->array_ = array_;
  other->count_ = count_;
  other->isPartial_ = isPartial_;
  other->offset_ = offset_;
  other->pos_ = pos_;
}

- (NSString *)description {
  return [self sequenceDescription];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "isPartialReturn", NULL, "Z", 0x1, NULL },
    { "isEmpty", NULL, "Z", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "array_", NULL, 0x1, "LIOSCharArray" },
    { "count_", NULL, 0x1, "I" },
    { "offset_", NULL, 0x1, "I" },
  };
  static J2ObjcClassInfo _RARESegment = { "Segment", "com.appnativa.rare.ui.text", NULL, 0x1, 3, methods, 3, fields, 0, NULL};
  return &_RARESegment;
}

@end
