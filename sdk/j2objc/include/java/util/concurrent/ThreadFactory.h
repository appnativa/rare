//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/ThreadFactory.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentThreadFactory_H_
#define _JavaUtilConcurrentThreadFactory_H_

@class JavaLangThread;
@protocol JavaLangRunnable;

#import "JreEmulation.h"

@protocol JavaUtilConcurrentThreadFactory < NSObject, JavaObject >
- (JavaLangThread *)newThreadWithJavaLangRunnable:(id<JavaLangRunnable>)r OBJC_METHOD_FAMILY_NONE;
@end

#endif // _JavaUtilConcurrentThreadFactory_H_
