//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/BidirectionalListIterator.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/util/BidirectionalListIterator.h"
#include "java/lang/Math.h"
#include "java/util/List.h"
#include "java/util/NoSuchElementException.h"

@implementation RAREUTBidirectionalListIterator

- (id)initWithJavaUtilList:(id<JavaUtilList>)list {
  if (self = [super init]) {
    index_ = -1;
    mark__ = -1;
    self->list_ = list;
  }
  return self;
}

- (id)previous {
  if (index_ == -2) {
    index_ = [((id<JavaUtilList>) nil_chk(list_)) size];
  }
  int n = index_ - 1;
  if ((n > -1) && (n < [((id<JavaUtilList>) nil_chk(list_)) size])) {
    return [list_ getWithInt:--index_];
  }
  @throw [[JavaUtilNoSuchElementException alloc] init];
}

- (void)home {
  index_ = -1;
}

- (int)getSize {
  return [((id<JavaUtilList>) nil_chk(list_)) size];
}

- (void)end {
  index_ = -2;
}

- (void)mark {
  mark__ = index_;
}

- (void)reset {
  index_ = mark__;
}

- (int)getPosition {
  return index_;
}

- (BOOL)hasPrevious {
  if (index_ == -2) {
    return [((id<JavaUtilList>) nil_chk(list_)) size] > 0;
  }
  int n = index_ - 1;
  if ((n > -1) && (n < [((id<JavaUtilList>) nil_chk(list_)) size])) {
    return YES;
  }
  return NO;
}

- (int)getRelativePositionWithId:(id)item {
  int n = [((id<JavaUtilList>) nil_chk(list_)) indexOfWithId:item];
  if (n == -1) {
    return -1;
  }
  float f = ((float) n) / ((float) [list_ size]);
  return (int) (f * 100);
}

- (int)getPositionWithId:(id)item {
  return [((id<JavaUtilList>) nil_chk(list_)) indexOfWithId:item];
}

- (int)setPositionWithInt:(int)position {
  int ret = 0;
  int n = position;
  if ((n < [((id<JavaUtilList>) nil_chk(list_)) size]) && (n >= -1)) {
    if (index_ < n) {
      ret = 1;
    }
    else if (index_ > n) {
      ret = -1;
    }
    index_ = n;
  }
  return ret;
}

- (int)setRelativePositionWithInt:(int)percent {
  int ret = 0;
  percent = [JavaLangMath absWithInt:percent];
  float f = fmodf(((float) percent) / 100.0f, 1.0f);
  int n = (int) ([((id<JavaUtilList>) nil_chk(list_)) size] * f);
  if ((n < [list_ size]) && (n >= 0)) {
    if (index_ < n) {
      ret = 1;
    }
    else if (index_ > n) {
      ret = -1;
    }
    index_ = n;
  }
  return ret;
}

- (id)get {
  if ((index_ > -1) && (index_ < [((id<JavaUtilList>) nil_chk(list_)) size])) {
    return [list_ getWithInt:index_];
  }
  @throw [[JavaUtilNoSuchElementException alloc] init];
}

- (BOOL)hasNext {
  int len = [((id<JavaUtilList>) nil_chk(list_)) size];
  int n = index_ + 1;
  return (n > -1) && (n < len);
}

- (id)next {
  int len = [((id<JavaUtilList>) nil_chk(list_)) size];
  int n = index_ + 1;
  if ((n > -1) && (n < len)) {
    return [list_ getWithInt:++index_];
  }
  @throw [[JavaUtilNoSuchElementException alloc] init];
}

- (void)remove {
  int len = [((id<JavaUtilList>) nil_chk(list_)) size];
  if ((index_ > 0) && (index_ < len)) {
    (void) [list_ removeWithInt:index_];
  }
  if (index_ >= [list_ size]) {
    index_ = -2;
  }
}

- (BOOL)isFixedSize {
  return YES;
}

- (void)copyAllFieldsTo:(RAREUTBidirectionalListIterator *)other {
  [super copyAllFieldsTo:other];
  other->index_ = index_;
  other->list_ = list_;
  other->mark__ = mark__;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "previous", NULL, "TT", 0x1, NULL },
    { "hasPrevious", NULL, "Z", 0x1, NULL },
    { "get", NULL, "TT", 0x1, NULL },
    { "hasNext", NULL, "Z", 0x1, NULL },
    { "next", NULL, "TT", 0x1, NULL },
    { "isFixedSize", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREUTBidirectionalListIterator = { "BidirectionalListIterator", "com.appnativa.util", NULL, 0x1, 6, methods, 0, NULL, 0, NULL};
  return &_RAREUTBidirectionalListIterator;
}

@end
