//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/util/Primitives.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalUtilPrimitives_H_
#define _OrgMockitoInternalUtilPrimitives_H_

@class IOSClass;
@protocol JavaUtilMap;

#import "JreEmulation.h"

@interface OrgMockitoInternalUtilPrimitives : NSObject {
}

+ (id<JavaUtilMap>)primitiveTypes;
+ (void)setPrimitiveTypes:(id<JavaUtilMap>)primitiveTypes;
+ (id<JavaUtilMap>)primitiveOrWrapperDefaultValues;
+ (void)setPrimitiveOrWrapperDefaultValues:(id<JavaUtilMap>)primitiveOrWrapperDefaultValues;
+ (IOSClass *)primitiveTypeOfWithIOSClass:(IOSClass *)clazz;
+ (BOOL)isPrimitiveOrWrapperWithIOSClass:(IOSClass *)type;
+ (id)defaultValueForPrimitiveOrWrapperWithIOSClass:(IOSClass *)primitiveOrWrapperType;
- (id)init;
@end

#endif // _OrgMockitoInternalUtilPrimitives_H_
