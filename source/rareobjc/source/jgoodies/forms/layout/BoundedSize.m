//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/layout/BoundedSize.java
//
//  Created by decoteaud on 5/11/15.
//

#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/jgoodies/forms/layout/BoundedSize.h"
#include "com/jgoodies/forms/layout/FormLayout.h"
#include "com/jgoodies/forms/layout/Size.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/Math.h"
#include "java/lang/NullPointerException.h"
#include "java/lang/StringBuffer.h"
#include "java/util/List.h"

@implementation RAREJGBoundedSize

- (id)initWithRAREJGSize:(id<RAREJGSize>)basis
          withRAREJGSize:(id<RAREJGSize>)lowerBound
          withRAREJGSize:(id<RAREJGSize>)upperBound {
  if (self = [super init]) {
    if (basis == nil) @throw [[JavaLangNullPointerException alloc] initWithNSString:@"The basis of a bounded size must not be null."];
    if ((lowerBound == nil) && (upperBound == nil)) @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"A bounded size must have a non-null lower or upper bound."];
    self->basis_ = basis;
    self->lowerBound_ = lowerBound;
    self->upperBound_ = upperBound;
  }
  return self;
}

- (id<RAREJGSize>)getBasis {
  return basis_;
}

- (id<RAREJGSize>)getLowerBound {
  return lowerBound_;
}

- (id<RAREJGSize>)getUpperBound {
  return upperBound_;
}

- (int)maximumSizeWithRAREiParentComponent:(id<RAREiParentComponent>)container
                          withJavaUtilList:(id<JavaUtilList>)components
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)minMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)prefMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)defaultMeasure {
  int size = [((id<RAREJGSize>) nil_chk(basis_)) maximumSizeWithRAREiParentComponent:container withJavaUtilList:components withRAREJGFormLayout_Measure:minMeasure withRAREJGFormLayout_Measure:prefMeasure withRAREJGFormLayout_Measure:defaultMeasure];
  if (lowerBound_ != nil) {
    size = [JavaLangMath maxWithInt:size withInt:[lowerBound_ maximumSizeWithRAREiParentComponent:container withJavaUtilList:components withRAREJGFormLayout_Measure:minMeasure withRAREJGFormLayout_Measure:prefMeasure withRAREJGFormLayout_Measure:defaultMeasure]];
  }
  if (upperBound_ != nil) {
    size = [JavaLangMath minWithInt:size withInt:[upperBound_ maximumSizeWithRAREiParentComponent:container withJavaUtilList:components withRAREJGFormLayout_Measure:minMeasure withRAREJGFormLayout_Measure:prefMeasure withRAREJGFormLayout_Measure:defaultMeasure]];
  }
  return size;
}

- (BOOL)compressible {
  return [((id<RAREJGSize>) nil_chk([self getBasis])) compressible];
}

- (BOOL)isEqual:(id)object {
  if (self == object) return YES;
  if (!([object isKindOfClass:[RAREJGBoundedSize class]])) return NO;
  RAREJGBoundedSize *size = (RAREJGBoundedSize *) check_class_cast(object, [RAREJGBoundedSize class]);
  return [((id<RAREJGSize>) nil_chk(basis_)) isEqual:((RAREJGBoundedSize *) nil_chk(size))->basis_] && ((lowerBound_ == nil && size->lowerBound_ == nil) || (lowerBound_ != nil && [lowerBound_ isEqual:size->lowerBound_])) && ((upperBound_ == nil && size->upperBound_ == nil) || (upperBound_ != nil && [upperBound_ isEqual:size->upperBound_]));
}

- (NSUInteger)hash {
  int hashValue = [((id<RAREJGSize>) nil_chk(basis_)) hash];
  if (lowerBound_ != nil) {
    hashValue = hashValue * 37 + [lowerBound_ hash];
  }
  if (upperBound_ != nil) {
    hashValue = hashValue * 37 + [upperBound_ hash];
  }
  return hashValue;
}

- (NSString *)description {
  return [self encode];
}

- (NSString *)encodeEx {
  return [self encode];
}

- (NSString *)encode {
  JavaLangStringBuffer *buffer = [[JavaLangStringBuffer alloc] initWithNSString:@"["];
  if (lowerBound_ != nil) {
    (void) [buffer appendWithNSString:[lowerBound_ encode]];
    (void) [buffer appendWithChar:','];
  }
  (void) [buffer appendWithNSString:[((id<RAREJGSize>) nil_chk(basis_)) encode]];
  if (upperBound_ != nil) {
    (void) [buffer appendWithChar:','];
    (void) [buffer appendWithNSString:[upperBound_ encode]];
  }
  (void) [buffer appendWithChar:']'];
  return [buffer description];
}

- (void)copyAllFieldsTo:(RAREJGBoundedSize *)other {
  [super copyAllFieldsTo:other];
  other->basis_ = basis_;
  other->lowerBound_ = lowerBound_;
  other->upperBound_ = upperBound_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getBasis", NULL, "LRAREJGSize", 0x1, NULL },
    { "getLowerBound", NULL, "LRAREJGSize", 0x1, NULL },
    { "getUpperBound", NULL, "LRAREJGSize", 0x1, NULL },
    { "compressible", NULL, "Z", 0x1, NULL },
    { "encodeEx", NULL, "LNSString", 0x1, NULL },
    { "encode", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "basis_", NULL, 0x12, "LRAREJGSize" },
    { "lowerBound_", NULL, 0x12, "LRAREJGSize" },
    { "upperBound_", NULL, 0x12, "LRAREJGSize" },
  };
  static J2ObjcClassInfo _RAREJGBoundedSize = { "BoundedSize", "com.jgoodies.forms.layout", NULL, 0x11, 6, methods, 3, fields, 0, NULL};
  return &_RAREJGBoundedSize;
}

@end