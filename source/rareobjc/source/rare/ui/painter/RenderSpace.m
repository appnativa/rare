//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/RenderSpace.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/painter/RenderSpace.h"
#include "java/lang/IllegalArgumentException.h"


static RARERenderSpaceEnum *RARERenderSpaceEnum_WITHIN_MARGIN;
static RARERenderSpaceEnum *RARERenderSpaceEnum_COMPONENT;
static RARERenderSpaceEnum *RARERenderSpaceEnum_WITHIN_BORDER;
IOSObjectArray *RARERenderSpaceEnum_values;

@implementation RARERenderSpaceEnum

+ (RARERenderSpaceEnum *)WITHIN_MARGIN {
  return RARERenderSpaceEnum_WITHIN_MARGIN;
}
+ (RARERenderSpaceEnum *)COMPONENT {
  return RARERenderSpaceEnum_COMPONENT;
}
+ (RARERenderSpaceEnum *)WITHIN_BORDER {
  return RARERenderSpaceEnum_WITHIN_BORDER;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RARERenderSpaceEnum class]) {
    RARERenderSpaceEnum_WITHIN_MARGIN = [[RARERenderSpaceEnum alloc] initWithNSString:@"WITHIN_MARGIN" withInt:0];
    RARERenderSpaceEnum_COMPONENT = [[RARERenderSpaceEnum alloc] initWithNSString:@"COMPONENT" withInt:1];
    RARERenderSpaceEnum_WITHIN_BORDER = [[RARERenderSpaceEnum alloc] initWithNSString:@"WITHIN_BORDER" withInt:2];
    RARERenderSpaceEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RARERenderSpaceEnum_WITHIN_MARGIN, RARERenderSpaceEnum_COMPONENT, RARERenderSpaceEnum_WITHIN_BORDER, nil } count:3 type:[IOSClass classWithClass:[RARERenderSpaceEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RARERenderSpaceEnum_values];
}

+ (RARERenderSpaceEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RARERenderSpaceEnum_values count]; i++) {
    RARERenderSpaceEnum *e = RARERenderSpaceEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRARERenderSpaceEnum"};
  static J2ObjcClassInfo _RARERenderSpaceEnum = { "RenderSpace", "com.appnativa.rare.ui.painter", NULL, 0x4011, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RARERenderSpaceEnum;
}

@end
