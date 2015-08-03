//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/AlignmentSpan.java
//
//  Created by decoteaud on 5/11/15.
//

#include "IOSClass.h"
#include "android/text/TextUtils.h"
#include "android/text/style/AlignmentSpan.h"
#include "java/lang/IllegalArgumentException.h"


static RAREAlignmentSpan_AlignmentEnum *RAREAlignmentSpan_AlignmentEnum_CENTER;
static RAREAlignmentSpan_AlignmentEnum *RAREAlignmentSpan_AlignmentEnum_LEFT;
static RAREAlignmentSpan_AlignmentEnum *RAREAlignmentSpan_AlignmentEnum_RIGHT;
IOSObjectArray *RAREAlignmentSpan_AlignmentEnum_values;

@implementation RAREAlignmentSpan_AlignmentEnum

+ (RAREAlignmentSpan_AlignmentEnum *)CENTER {
  return RAREAlignmentSpan_AlignmentEnum_CENTER;
}
+ (RAREAlignmentSpan_AlignmentEnum *)LEFT {
  return RAREAlignmentSpan_AlignmentEnum_LEFT;
}
+ (RAREAlignmentSpan_AlignmentEnum *)RIGHT {
  return RAREAlignmentSpan_AlignmentEnum_RIGHT;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREAlignmentSpan_AlignmentEnum class]) {
    RAREAlignmentSpan_AlignmentEnum_CENTER = [[RAREAlignmentSpan_AlignmentEnum alloc] initWithNSString:@"CENTER" withInt:0];
    RAREAlignmentSpan_AlignmentEnum_LEFT = [[RAREAlignmentSpan_AlignmentEnum alloc] initWithNSString:@"LEFT" withInt:1];
    RAREAlignmentSpan_AlignmentEnum_RIGHT = [[RAREAlignmentSpan_AlignmentEnum alloc] initWithNSString:@"RIGHT" withInt:2];
    RAREAlignmentSpan_AlignmentEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREAlignmentSpan_AlignmentEnum_CENTER, RAREAlignmentSpan_AlignmentEnum_LEFT, RAREAlignmentSpan_AlignmentEnum_RIGHT, nil } count:3 type:[IOSClass classWithClass:[RAREAlignmentSpan_AlignmentEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREAlignmentSpan_AlignmentEnum_values];
}

+ (RAREAlignmentSpan_AlignmentEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREAlignmentSpan_AlignmentEnum_values count]; i++) {
    RAREAlignmentSpan_AlignmentEnum *e = RAREAlignmentSpan_AlignmentEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREAlignmentSpan_AlignmentEnum"};
  static J2ObjcClassInfo _RAREAlignmentSpan_AlignmentEnum = { "Alignment", "android.text.style", "AlignmentSpan", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREAlignmentSpan_AlignmentEnum;
}

@end

@interface RAREAlignmentSpan : NSObject
@end

@implementation RAREAlignmentSpan

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getAlignment", NULL, "LRAREAlignmentSpan_AlignmentEnum", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREAlignmentSpan = { "AlignmentSpan", "android.text.style", NULL, 0x201, 1, methods, 0, NULL, 0, NULL};
  return &_RAREAlignmentSpan;
}

@end
@implementation RAREAlignmentSpan_Standard

- (id)initWithRAREAlignmentSpan_AlignmentEnum:(RAREAlignmentSpan_AlignmentEnum *)align {
  if (self = [super init]) {
    mAlignment_ = align;
  }
  return self;
}

- (int)getSpanTypeId {
  return AndroidTextTextUtils_ALIGNMENT_SPAN;
}

- (RAREAlignmentSpan_AlignmentEnum *)getAlignment {
  return mAlignment_;
}

- (void)copyAllFieldsTo:(RAREAlignmentSpan_Standard *)other {
  [super copyAllFieldsTo:other];
  other->mAlignment_ = mAlignment_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getAlignment", NULL, "LRAREAlignmentSpan_AlignmentEnum", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "mAlignment_", NULL, 0x12, "LRAREAlignmentSpan_AlignmentEnum" },
  };
  static J2ObjcClassInfo _RAREAlignmentSpan_Standard = { "Standard", "android.text.style", "AlignmentSpan", 0x9, 1, methods, 1, fields, 0, NULL};
  return &_RAREAlignmentSpan_Standard;
}

@end