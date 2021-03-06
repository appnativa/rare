//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iComposite.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/iComposite.h"
#include "java/lang/IllegalArgumentException.h"


@interface RAREiComposite : NSObject
@end

@implementation RAREiComposite

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "deriveWithFloat:", NULL, "LRAREiComposite", 0x401, NULL },
    { "getAlpha", NULL, "F", 0x401, NULL },
    { "getCompositeType", NULL, "LRAREiComposite_CompositeTypeEnum", 0x401, NULL },
    { "getName", NULL, "LNSString", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiComposite = { "iComposite", "com.appnativa.rare.ui", NULL, 0x201, 4, methods, 0, NULL, 0, NULL};
  return &_RAREiComposite;
}

@end

static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_SRC_OVER;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_SRC_ATOP;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_SRC_IN;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_SRC_OUT;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_DST_OVER;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_DST_ATOP;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_DST_IN;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_DST_OUT;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_CLEAR;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_XOR;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_LIGHTEN;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_DARKEN;
static RAREiComposite_CompositeTypeEnum *RAREiComposite_CompositeTypeEnum_COPY;
IOSObjectArray *RAREiComposite_CompositeTypeEnum_values;

@implementation RAREiComposite_CompositeTypeEnum

+ (RAREiComposite_CompositeTypeEnum *)SRC_OVER {
  return RAREiComposite_CompositeTypeEnum_SRC_OVER;
}
+ (RAREiComposite_CompositeTypeEnum *)SRC_ATOP {
  return RAREiComposite_CompositeTypeEnum_SRC_ATOP;
}
+ (RAREiComposite_CompositeTypeEnum *)SRC_IN {
  return RAREiComposite_CompositeTypeEnum_SRC_IN;
}
+ (RAREiComposite_CompositeTypeEnum *)SRC_OUT {
  return RAREiComposite_CompositeTypeEnum_SRC_OUT;
}
+ (RAREiComposite_CompositeTypeEnum *)DST_OVER {
  return RAREiComposite_CompositeTypeEnum_DST_OVER;
}
+ (RAREiComposite_CompositeTypeEnum *)DST_ATOP {
  return RAREiComposite_CompositeTypeEnum_DST_ATOP;
}
+ (RAREiComposite_CompositeTypeEnum *)DST_IN {
  return RAREiComposite_CompositeTypeEnum_DST_IN;
}
+ (RAREiComposite_CompositeTypeEnum *)DST_OUT {
  return RAREiComposite_CompositeTypeEnum_DST_OUT;
}
+ (RAREiComposite_CompositeTypeEnum *)CLEAR {
  return RAREiComposite_CompositeTypeEnum_CLEAR;
}
+ (RAREiComposite_CompositeTypeEnum *)XOR {
  return RAREiComposite_CompositeTypeEnum_XOR;
}
+ (RAREiComposite_CompositeTypeEnum *)LIGHTEN {
  return RAREiComposite_CompositeTypeEnum_LIGHTEN;
}
+ (RAREiComposite_CompositeTypeEnum *)DARKEN {
  return RAREiComposite_CompositeTypeEnum_DARKEN;
}
+ (RAREiComposite_CompositeTypeEnum *)COPY {
  return RAREiComposite_CompositeTypeEnum_COPY;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiComposite_CompositeTypeEnum class]) {
    RAREiComposite_CompositeTypeEnum_SRC_OVER = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"SRC_OVER" withInt:0];
    RAREiComposite_CompositeTypeEnum_SRC_ATOP = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"SRC_ATOP" withInt:1];
    RAREiComposite_CompositeTypeEnum_SRC_IN = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"SRC_IN" withInt:2];
    RAREiComposite_CompositeTypeEnum_SRC_OUT = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"SRC_OUT" withInt:3];
    RAREiComposite_CompositeTypeEnum_DST_OVER = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"DST_OVER" withInt:4];
    RAREiComposite_CompositeTypeEnum_DST_ATOP = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"DST_ATOP" withInt:5];
    RAREiComposite_CompositeTypeEnum_DST_IN = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"DST_IN" withInt:6];
    RAREiComposite_CompositeTypeEnum_DST_OUT = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"DST_OUT" withInt:7];
    RAREiComposite_CompositeTypeEnum_CLEAR = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"CLEAR" withInt:8];
    RAREiComposite_CompositeTypeEnum_XOR = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"XOR" withInt:9];
    RAREiComposite_CompositeTypeEnum_LIGHTEN = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"LIGHTEN" withInt:10];
    RAREiComposite_CompositeTypeEnum_DARKEN = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"DARKEN" withInt:11];
    RAREiComposite_CompositeTypeEnum_COPY = [[RAREiComposite_CompositeTypeEnum alloc] initWithNSString:@"COPY" withInt:12];
    RAREiComposite_CompositeTypeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiComposite_CompositeTypeEnum_SRC_OVER, RAREiComposite_CompositeTypeEnum_SRC_ATOP, RAREiComposite_CompositeTypeEnum_SRC_IN, RAREiComposite_CompositeTypeEnum_SRC_OUT, RAREiComposite_CompositeTypeEnum_DST_OVER, RAREiComposite_CompositeTypeEnum_DST_ATOP, RAREiComposite_CompositeTypeEnum_DST_IN, RAREiComposite_CompositeTypeEnum_DST_OUT, RAREiComposite_CompositeTypeEnum_CLEAR, RAREiComposite_CompositeTypeEnum_XOR, RAREiComposite_CompositeTypeEnum_LIGHTEN, RAREiComposite_CompositeTypeEnum_DARKEN, RAREiComposite_CompositeTypeEnum_COPY, nil } count:13 type:[IOSClass classWithClass:[RAREiComposite_CompositeTypeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiComposite_CompositeTypeEnum_values];
}

+ (RAREiComposite_CompositeTypeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiComposite_CompositeTypeEnum_values count]; i++) {
    RAREiComposite_CompositeTypeEnum *e = RAREiComposite_CompositeTypeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiComposite_CompositeTypeEnum"};
  static J2ObjcClassInfo _RAREiComposite_CompositeTypeEnum = { "CompositeType", "com.appnativa.rare.ui", "iComposite", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiComposite_CompositeTypeEnum;
}

@end
