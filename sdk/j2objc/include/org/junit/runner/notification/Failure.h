//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/junit/runner/notification/Failure.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgJunitRunnerNotificationFailure_H_
#define _OrgJunitRunnerNotificationFailure_H_

@class JavaLangThrowable;
@class OrgJunitRunnerDescription;

#import "JreEmulation.h"
#include "java/io/Serializable.h"

#define OrgJunitRunnerNotificationFailure_serialVersionUID 1

@interface OrgJunitRunnerNotificationFailure : NSObject < JavaIoSerializable > {
 @public
  OrgJunitRunnerDescription *fDescription_;
  JavaLangThrowable *fThrownException_;
}

- (id)initWithOrgJunitRunnerDescription:(OrgJunitRunnerDescription *)description_
                  withJavaLangThrowable:(JavaLangThrowable *)thrownException;
- (NSString *)getTestHeader;
- (OrgJunitRunnerDescription *)getDescription;
- (JavaLangThrowable *)getException;
- (NSString *)description;
- (NSString *)getTrace;
- (NSString *)getMessage;
- (void)copyAllFieldsTo:(OrgJunitRunnerNotificationFailure *)other;
@end

J2OBJC_FIELD_SETTER(OrgJunitRunnerNotificationFailure, fDescription_, OrgJunitRunnerDescription *)
J2OBJC_FIELD_SETTER(OrgJunitRunnerNotificationFailure, fThrownException_, JavaLangThrowable *)

#endif // _OrgJunitRunnerNotificationFailure_H_
