//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/matchers/InstanceOf.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalMatchersInstanceOf_H_
#define _OrgMockitoInternalMatchersInstanceOf_H_

@class IOSClass;
@protocol OrgHamcrestDescription;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "org/mockito/ArgumentMatcher.h"

#define OrgMockitoInternalMatchersInstanceOf_serialVersionUID 517358915876138366

@interface OrgMockitoInternalMatchersInstanceOf : OrgMockitoArgumentMatcher < JavaIoSerializable > {
 @public
  IOSClass *clazz_;
}

- (id)initWithIOSClass:(IOSClass *)clazz;
- (BOOL)matchesWithId:(id)actual;
- (void)describeToWithOrgHamcrestDescription:(id<OrgHamcrestDescription>)description_;
- (void)copyAllFieldsTo:(OrgMockitoInternalMatchersInstanceOf *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalMatchersInstanceOf, clazz_, IOSClass *)

#endif // _OrgMockitoInternalMatchersInstanceOf_H_
