//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/junit/runners/model/InitializationError.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgJunitRunnersModelInitializationError_H_
#define _OrgJunitRunnersModelInitializationError_H_

@class JavaLangThrowable;
@protocol JavaUtilList;

#import "JreEmulation.h"
#include "java/lang/Exception.h"

#define OrgJunitRunnersModelInitializationError_serialVersionUID 1

@interface OrgJunitRunnersModelInitializationError : JavaLangException {
 @public
  id<JavaUtilList> fErrors_;
}

- (id)initWithJavaUtilList:(id<JavaUtilList>)errors;
- (id)initWithJavaLangThrowable:(JavaLangThrowable *)error;
- (id)initWithNSString:(NSString *)string;
- (id<JavaUtilList>)getCauses;
- (void)copyAllFieldsTo:(OrgJunitRunnersModelInitializationError *)other;
@end

J2OBJC_FIELD_SETTER(OrgJunitRunnersModelInitializationError, fErrors_, id<JavaUtilList>)

#endif // _OrgJunitRunnersModelInitializationError_H_
