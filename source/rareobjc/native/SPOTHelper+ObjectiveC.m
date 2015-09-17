//
//  SPOTHelper+ObjectiveC.m
//  RareOSX
//
//  Created by Don DeCoteau on 8/15/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "AppleHelper.h"
#import "SPOTHelper+ObjectiveC.h"
#include "java/lang/StringBuilder.h"
#include "java/lang/Character.h"
#include "com/appnativa/spot/SPOTException.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/Helper.h"

@implementation SPOTHelper (ObjectiveC)
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wobjc-protocol-method-implementation"
#pragma clang diagnostic ignored "-Warc-performSelector-leaks"
+ (void)setReferenceVariableWithISPOTElement:(id<iSPOTElement>)parent
                                withNSString:(NSString *)name
                            withISPOTElement:(id<iSPOTElement>)element {
    @try {
      NSMutableString *str = [NSMutableString stringWithFormat:@"set%@WithISPOTElement:", name];
      unichar ch = [str characterAtIndex:3];
      [str replaceCharactersInRange:NSMakeRange(3, 1) withString:[[NSString stringWithCharacters:&ch length:1] uppercaseString]];
      [parent performSelector:NSSelectorFromString(str) withObject: element];
    }
    @catch (JavaLangException *ex) {
      @throw [[SPOTException alloc] initWithJavaLangThrowable:ex];
    }
}
  
+ (id<iSPOTElement>)elementFromNameWithJavaUtilMap:(id<JavaUtilMap>)refClassMap
                                  withISPOTElement:(id<iSPOTElement>)parent
                                      withNSString:(NSString *)name {
  @try {
    NSMutableString *str = [NSMutableString stringWithFormat:@"get%@Reference", name];
    unichar ch = [str characterAtIndex:3];
    [str replaceCharactersInRange:NSMakeRange(3, 1) withString:[[NSString stringWithCharacters:&ch length:1] uppercaseString]];
    SEL sel=NSSelectorFromString(str);
    if([parent respondsToSelector:sel]) {
      id<iSPOTElement> e = (id<iSPOTElement>)[AppleHelper invokeSelector:sel onTarget: parent];
      if (e != nil) {
        [refClassMap putWithId:name withId:[e getClass]];
        return e;
      }
    }
  }
  @catch (JavaLangException *ex) {
    @throw [[SPOTException alloc] initWithJavaLangThrowable:[RAREUTHelper pealExceptionWithJavaLangThrowable:ex]];
  }
  IOSClass *cls = (IOSClass *) [((id<JavaUtilMap>) nil_chk(refClassMap)) getWithId:name];
  if (cls == nil) {
    @try {
      JavaLangReflectField *f = [[parent getClass] getDeclaredField:name];
      cls = [SPOTHelper getFieldClassWithJavaLangReflectField:f];
      [refClassMap putWithId:name withId:cls];
    }
    @catch (JavaLangException *ex) {
      return nil;
    }
  }
  @try {
    return (id<iSPOTElement>) [cls.objcClass new];
  }
  @catch (JavaLangException *ex1) {
    return nil;
  }
}
#pragma clang diagnostic pop

@end
