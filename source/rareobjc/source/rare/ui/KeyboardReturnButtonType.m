//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/KeyboardReturnButtonType.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/KeyboardReturnButtonType.h"
#include "java/lang/IllegalArgumentException.h"


static RAREKeyboardReturnButtonTypeEnum *RAREKeyboardReturnButtonTypeEnum_DEFAULT_TYPE;
static RAREKeyboardReturnButtonTypeEnum *RAREKeyboardReturnButtonTypeEnum_GO_TYPE;
static RAREKeyboardReturnButtonTypeEnum *RAREKeyboardReturnButtonTypeEnum_JOIN_TYPE;
static RAREKeyboardReturnButtonTypeEnum *RAREKeyboardReturnButtonTypeEnum_NEXT_TYPE;
static RAREKeyboardReturnButtonTypeEnum *RAREKeyboardReturnButtonTypeEnum_SEARCH_TYPE;
static RAREKeyboardReturnButtonTypeEnum *RAREKeyboardReturnButtonTypeEnum_SEND_TYPE;
static RAREKeyboardReturnButtonTypeEnum *RAREKeyboardReturnButtonTypeEnum_DONE_TYPE;
static RAREKeyboardReturnButtonTypeEnum *RAREKeyboardReturnButtonTypeEnum_NONE_TYPE;
IOSObjectArray *RAREKeyboardReturnButtonTypeEnum_values;

@implementation RAREKeyboardReturnButtonTypeEnum

+ (RAREKeyboardReturnButtonTypeEnum *)DEFAULT_TYPE {
  return RAREKeyboardReturnButtonTypeEnum_DEFAULT_TYPE;
}
+ (RAREKeyboardReturnButtonTypeEnum *)GO_TYPE {
  return RAREKeyboardReturnButtonTypeEnum_GO_TYPE;
}
+ (RAREKeyboardReturnButtonTypeEnum *)JOIN_TYPE {
  return RAREKeyboardReturnButtonTypeEnum_JOIN_TYPE;
}
+ (RAREKeyboardReturnButtonTypeEnum *)NEXT_TYPE {
  return RAREKeyboardReturnButtonTypeEnum_NEXT_TYPE;
}
+ (RAREKeyboardReturnButtonTypeEnum *)SEARCH_TYPE {
  return RAREKeyboardReturnButtonTypeEnum_SEARCH_TYPE;
}
+ (RAREKeyboardReturnButtonTypeEnum *)SEND_TYPE {
  return RAREKeyboardReturnButtonTypeEnum_SEND_TYPE;
}
+ (RAREKeyboardReturnButtonTypeEnum *)DONE_TYPE {
  return RAREKeyboardReturnButtonTypeEnum_DONE_TYPE;
}
+ (RAREKeyboardReturnButtonTypeEnum *)NONE_TYPE {
  return RAREKeyboardReturnButtonTypeEnum_NONE_TYPE;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREKeyboardReturnButtonTypeEnum class]) {
    RAREKeyboardReturnButtonTypeEnum_DEFAULT_TYPE = [[RAREKeyboardReturnButtonTypeEnum alloc] initWithNSString:@"DEFAULT_TYPE" withInt:0];
    RAREKeyboardReturnButtonTypeEnum_GO_TYPE = [[RAREKeyboardReturnButtonTypeEnum alloc] initWithNSString:@"GO_TYPE" withInt:1];
    RAREKeyboardReturnButtonTypeEnum_JOIN_TYPE = [[RAREKeyboardReturnButtonTypeEnum alloc] initWithNSString:@"JOIN_TYPE" withInt:2];
    RAREKeyboardReturnButtonTypeEnum_NEXT_TYPE = [[RAREKeyboardReturnButtonTypeEnum alloc] initWithNSString:@"NEXT_TYPE" withInt:3];
    RAREKeyboardReturnButtonTypeEnum_SEARCH_TYPE = [[RAREKeyboardReturnButtonTypeEnum alloc] initWithNSString:@"SEARCH_TYPE" withInt:4];
    RAREKeyboardReturnButtonTypeEnum_SEND_TYPE = [[RAREKeyboardReturnButtonTypeEnum alloc] initWithNSString:@"SEND_TYPE" withInt:5];
    RAREKeyboardReturnButtonTypeEnum_DONE_TYPE = [[RAREKeyboardReturnButtonTypeEnum alloc] initWithNSString:@"DONE_TYPE" withInt:6];
    RAREKeyboardReturnButtonTypeEnum_NONE_TYPE = [[RAREKeyboardReturnButtonTypeEnum alloc] initWithNSString:@"NONE_TYPE" withInt:7];
    RAREKeyboardReturnButtonTypeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREKeyboardReturnButtonTypeEnum_DEFAULT_TYPE, RAREKeyboardReturnButtonTypeEnum_GO_TYPE, RAREKeyboardReturnButtonTypeEnum_JOIN_TYPE, RAREKeyboardReturnButtonTypeEnum_NEXT_TYPE, RAREKeyboardReturnButtonTypeEnum_SEARCH_TYPE, RAREKeyboardReturnButtonTypeEnum_SEND_TYPE, RAREKeyboardReturnButtonTypeEnum_DONE_TYPE, RAREKeyboardReturnButtonTypeEnum_NONE_TYPE, nil } count:8 type:[IOSClass classWithClass:[RAREKeyboardReturnButtonTypeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREKeyboardReturnButtonTypeEnum_values];
}

+ (RAREKeyboardReturnButtonTypeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREKeyboardReturnButtonTypeEnum_values count]; i++) {
    RAREKeyboardReturnButtonTypeEnum *e = RAREKeyboardReturnButtonTypeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREKeyboardReturnButtonTypeEnum"};
  static J2ObjcClassInfo _RAREKeyboardReturnButtonTypeEnum = { "KeyboardReturnButtonType", "com.appnativa.rare.ui", NULL, 0x4011, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREKeyboardReturnButtonTypeEnum;
}

@end
