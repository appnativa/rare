//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/matchers/GreaterOrEqual.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalMatchersGreaterOrEqual_H_
#define _OrgMockitoInternalMatchersGreaterOrEqual_H_

@protocol JavaLangComparable;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "org/mockito/internal/matchers/CompareTo.h"

#define OrgMockitoInternalMatchersGreaterOrEqual_serialVersionUID 87695769061286092

@interface OrgMockitoInternalMatchersGreaterOrEqual : OrgMockitoInternalMatchersCompareTo < JavaIoSerializable > {
}

- (id)initWithJavaLangComparable:(id<JavaLangComparable>)value;
- (NSString *)getName;
- (BOOL)matchResultWithInt:(int)result;
@end

#endif // _OrgMockitoInternalMatchersGreaterOrEqual_H_
