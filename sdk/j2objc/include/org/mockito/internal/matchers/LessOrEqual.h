//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/matchers/LessOrEqual.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalMatchersLessOrEqual_H_
#define _OrgMockitoInternalMatchersLessOrEqual_H_

@protocol JavaLangComparable;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "org/mockito/internal/matchers/CompareTo.h"

#define OrgMockitoInternalMatchersLessOrEqual_serialVersionUID -6648773374429103565

@interface OrgMockitoInternalMatchersLessOrEqual : OrgMockitoInternalMatchersCompareTo < JavaIoSerializable > {
}

- (id)initWithJavaLangComparable:(id<JavaLangComparable>)value;
- (NSString *)getName;
- (BOOL)matchResultWithInt:(int)result;
@end

#endif // _OrgMockitoInternalMatchersLessOrEqual_H_
