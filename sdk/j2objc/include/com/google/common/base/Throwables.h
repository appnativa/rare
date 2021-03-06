//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/base/Throwables.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonBaseThrowables_RESTRICT
#define ComGoogleCommonBaseThrowables_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonBaseThrowables_RESTRICT

#if !defined (_ComGoogleCommonBaseThrowables_) && (ComGoogleCommonBaseThrowables_INCLUDE_ALL || ComGoogleCommonBaseThrowables_INCLUDE)
#define _ComGoogleCommonBaseThrowables_

@class IOSClass;
@class JavaLangRuntimeException;
@class JavaLangThrowable;
@protocol JavaUtilList;

@interface ComGoogleCommonBaseThrowables : NSObject {
}

- (id)init;
+ (void)propagateIfInstanceOfWithJavaLangThrowable:(JavaLangThrowable *)throwable
                                      withIOSClass:(IOSClass *)declaredType;
+ (void)propagateIfPossibleWithJavaLangThrowable:(JavaLangThrowable *)throwable;
+ (void)propagateIfPossibleWithJavaLangThrowable:(JavaLangThrowable *)throwable
                                    withIOSClass:(IOSClass *)declaredType;
+ (void)propagateIfPossibleWithJavaLangThrowable:(JavaLangThrowable *)throwable
                                    withIOSClass:(IOSClass *)declaredType1
                                    withIOSClass:(IOSClass *)declaredType2;
+ (JavaLangRuntimeException *)propagateWithJavaLangThrowable:(JavaLangThrowable *)throwable;
+ (JavaLangThrowable *)getRootCauseWithJavaLangThrowable:(JavaLangThrowable *)throwable;
+ (id<JavaUtilList>)getCausalChainWithJavaLangThrowable:(JavaLangThrowable *)throwable;
+ (NSString *)getStackTraceAsStringWithJavaLangThrowable:(JavaLangThrowable *)throwable;
@end
#endif
