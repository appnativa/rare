//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/junit/runners/model/MultipleFailureException.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgJunitRunnersModelMultipleFailureException_H_
#define _OrgJunitRunnersModelMultipleFailureException_H_

@protocol JavaUtilList;

#import "JreEmulation.h"
#include "java/lang/Exception.h"

#define OrgJunitRunnersModelMultipleFailureException_serialVersionUID 1

@interface OrgJunitRunnersModelMultipleFailureException : JavaLangException {
 @public
  id<JavaUtilList> fErrors_;
}

- (id)initWithJavaUtilList:(id<JavaUtilList>)errors;
- (id<JavaUtilList>)getFailures;
- (NSString *)getMessage;
+ (void)assertEmptyWithJavaUtilList:(id<JavaUtilList>)errors;
- (void)copyAllFieldsTo:(OrgJunitRunnersModelMultipleFailureException *)other;
@end

J2OBJC_FIELD_SETTER(OrgJunitRunnersModelMultipleFailureException, fErrors_, id<JavaUtilList>)

#endif // _OrgJunitRunnersModelMultipleFailureException_H_
