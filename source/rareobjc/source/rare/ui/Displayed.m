//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/Displayed.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/Displayed.h"
#include "java/lang/IllegalArgumentException.h"


static RAREDisplayedEnum *RAREDisplayedEnum_ALWAYS;
static RAREDisplayedEnum *RAREDisplayedEnum_WHEN_NOT_FOCUSED;
static RAREDisplayedEnum *RAREDisplayedEnum_WHEN_FOCUSED;
static RAREDisplayedEnum *RAREDisplayedEnum_WHEN_WINDOW_NOT_FOCUSED;
static RAREDisplayedEnum *RAREDisplayedEnum_WHEN_WINDOW_FOCUSED;
static RAREDisplayedEnum *RAREDisplayedEnum_WHEN_WIDGET_NOT_FOCUSED;
static RAREDisplayedEnum *RAREDisplayedEnum_WHEN_WIDGET_FOCUSED;
static RAREDisplayedEnum *RAREDisplayedEnum_WHEN_PARENT_WIDGET_NOT_FOCUSED;
static RAREDisplayedEnum *RAREDisplayedEnum_WHEN_PARENT_WIDGET_FOCUSED;
static RAREDisplayedEnum *RAREDisplayedEnum_WHEN_CHILD_WIDGET_NOT_FOCUSED;
static RAREDisplayedEnum *RAREDisplayedEnum_WHEN_CHILD_WIDGET_FOCUSED;
static RAREDisplayedEnum *RAREDisplayedEnum_WHEN_EMPTY;
static RAREDisplayedEnum *RAREDisplayedEnum_BEFORE_INTERACTION;
static RAREDisplayedEnum *RAREDisplayedEnum_BEFORE_FIRST_FOCUS;
IOSObjectArray *RAREDisplayedEnum_values;

@implementation RAREDisplayedEnum

+ (RAREDisplayedEnum *)ALWAYS {
  return RAREDisplayedEnum_ALWAYS;
}
+ (RAREDisplayedEnum *)WHEN_NOT_FOCUSED {
  return RAREDisplayedEnum_WHEN_NOT_FOCUSED;
}
+ (RAREDisplayedEnum *)WHEN_FOCUSED {
  return RAREDisplayedEnum_WHEN_FOCUSED;
}
+ (RAREDisplayedEnum *)WHEN_WINDOW_NOT_FOCUSED {
  return RAREDisplayedEnum_WHEN_WINDOW_NOT_FOCUSED;
}
+ (RAREDisplayedEnum *)WHEN_WINDOW_FOCUSED {
  return RAREDisplayedEnum_WHEN_WINDOW_FOCUSED;
}
+ (RAREDisplayedEnum *)WHEN_WIDGET_NOT_FOCUSED {
  return RAREDisplayedEnum_WHEN_WIDGET_NOT_FOCUSED;
}
+ (RAREDisplayedEnum *)WHEN_WIDGET_FOCUSED {
  return RAREDisplayedEnum_WHEN_WIDGET_FOCUSED;
}
+ (RAREDisplayedEnum *)WHEN_PARENT_WIDGET_NOT_FOCUSED {
  return RAREDisplayedEnum_WHEN_PARENT_WIDGET_NOT_FOCUSED;
}
+ (RAREDisplayedEnum *)WHEN_PARENT_WIDGET_FOCUSED {
  return RAREDisplayedEnum_WHEN_PARENT_WIDGET_FOCUSED;
}
+ (RAREDisplayedEnum *)WHEN_CHILD_WIDGET_NOT_FOCUSED {
  return RAREDisplayedEnum_WHEN_CHILD_WIDGET_NOT_FOCUSED;
}
+ (RAREDisplayedEnum *)WHEN_CHILD_WIDGET_FOCUSED {
  return RAREDisplayedEnum_WHEN_CHILD_WIDGET_FOCUSED;
}
+ (RAREDisplayedEnum *)WHEN_EMPTY {
  return RAREDisplayedEnum_WHEN_EMPTY;
}
+ (RAREDisplayedEnum *)BEFORE_INTERACTION {
  return RAREDisplayedEnum_BEFORE_INTERACTION;
}
+ (RAREDisplayedEnum *)BEFORE_FIRST_FOCUS {
  return RAREDisplayedEnum_BEFORE_FIRST_FOCUS;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREDisplayedEnum class]) {
    RAREDisplayedEnum_ALWAYS = [[RAREDisplayedEnum alloc] initWithNSString:@"ALWAYS" withInt:0];
    RAREDisplayedEnum_WHEN_NOT_FOCUSED = [[RAREDisplayedEnum alloc] initWithNSString:@"WHEN_NOT_FOCUSED" withInt:1];
    RAREDisplayedEnum_WHEN_FOCUSED = [[RAREDisplayedEnum alloc] initWithNSString:@"WHEN_FOCUSED" withInt:2];
    RAREDisplayedEnum_WHEN_WINDOW_NOT_FOCUSED = [[RAREDisplayedEnum alloc] initWithNSString:@"WHEN_WINDOW_NOT_FOCUSED" withInt:3];
    RAREDisplayedEnum_WHEN_WINDOW_FOCUSED = [[RAREDisplayedEnum alloc] initWithNSString:@"WHEN_WINDOW_FOCUSED" withInt:4];
    RAREDisplayedEnum_WHEN_WIDGET_NOT_FOCUSED = [[RAREDisplayedEnum alloc] initWithNSString:@"WHEN_WIDGET_NOT_FOCUSED" withInt:5];
    RAREDisplayedEnum_WHEN_WIDGET_FOCUSED = [[RAREDisplayedEnum alloc] initWithNSString:@"WHEN_WIDGET_FOCUSED" withInt:6];
    RAREDisplayedEnum_WHEN_PARENT_WIDGET_NOT_FOCUSED = [[RAREDisplayedEnum alloc] initWithNSString:@"WHEN_PARENT_WIDGET_NOT_FOCUSED" withInt:7];
    RAREDisplayedEnum_WHEN_PARENT_WIDGET_FOCUSED = [[RAREDisplayedEnum alloc] initWithNSString:@"WHEN_PARENT_WIDGET_FOCUSED" withInt:8];
    RAREDisplayedEnum_WHEN_CHILD_WIDGET_NOT_FOCUSED = [[RAREDisplayedEnum alloc] initWithNSString:@"WHEN_CHILD_WIDGET_NOT_FOCUSED" withInt:9];
    RAREDisplayedEnum_WHEN_CHILD_WIDGET_FOCUSED = [[RAREDisplayedEnum alloc] initWithNSString:@"WHEN_CHILD_WIDGET_FOCUSED" withInt:10];
    RAREDisplayedEnum_WHEN_EMPTY = [[RAREDisplayedEnum alloc] initWithNSString:@"WHEN_EMPTY" withInt:11];
    RAREDisplayedEnum_BEFORE_INTERACTION = [[RAREDisplayedEnum alloc] initWithNSString:@"BEFORE_INTERACTION" withInt:12];
    RAREDisplayedEnum_BEFORE_FIRST_FOCUS = [[RAREDisplayedEnum alloc] initWithNSString:@"BEFORE_FIRST_FOCUS" withInt:13];
    RAREDisplayedEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREDisplayedEnum_ALWAYS, RAREDisplayedEnum_WHEN_NOT_FOCUSED, RAREDisplayedEnum_WHEN_FOCUSED, RAREDisplayedEnum_WHEN_WINDOW_NOT_FOCUSED, RAREDisplayedEnum_WHEN_WINDOW_FOCUSED, RAREDisplayedEnum_WHEN_WIDGET_NOT_FOCUSED, RAREDisplayedEnum_WHEN_WIDGET_FOCUSED, RAREDisplayedEnum_WHEN_PARENT_WIDGET_NOT_FOCUSED, RAREDisplayedEnum_WHEN_PARENT_WIDGET_FOCUSED, RAREDisplayedEnum_WHEN_CHILD_WIDGET_NOT_FOCUSED, RAREDisplayedEnum_WHEN_CHILD_WIDGET_FOCUSED, RAREDisplayedEnum_WHEN_EMPTY, RAREDisplayedEnum_BEFORE_INTERACTION, RAREDisplayedEnum_BEFORE_FIRST_FOCUS, nil } count:14 type:[IOSClass classWithClass:[RAREDisplayedEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREDisplayedEnum_values];
}

+ (RAREDisplayedEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREDisplayedEnum_values count]; i++) {
    RAREDisplayedEnum *e = RAREDisplayedEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREDisplayedEnum"};
  static J2ObjcClassInfo _RAREDisplayedEnum = { "Displayed", "com.appnativa.rare.ui", NULL, 0x4011, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREDisplayedEnum;
}

@end
