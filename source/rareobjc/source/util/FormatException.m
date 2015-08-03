//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/FormatException.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/util/FormatException.h"
#include "com/appnativa/util/Helper.h"
#include "java/lang/Exception.h"

@implementation RAREUTFormatException

- (id)init {
  return [super init];
}

- (id)initWithJavaLangException:(JavaLangException *)e {
  return [super initWithJavaLangThrowable:e];
}

- (id)initWithNSString:(NSString *)message {
  return [super initWithNSString:message];
}

- (id)initWithNSString:(NSString *)msgspec
          withNSString:(NSString *)msgparam {
  return [super initWithNSString:[RAREUTHelper expandStringWithNSString:msgspec withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ msgparam } count:1 type:[IOSClass classWithClass:[NSString class]]]]];
}

- (id)initWithNSString:(NSString *)msgspec
          withNSString:(NSString *)msgparam
          withNSString:(NSString *)msgparam2 {
  return [super initWithNSString:[RAREUTHelper expandStringWithNSString:msgspec withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ msgparam, msgparam2 } count:2 type:[IOSClass classWithClass:[NSString class]]]]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREUTFormatException = { "FormatException", "com.appnativa.util", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREUTFormatException;
}

@end
