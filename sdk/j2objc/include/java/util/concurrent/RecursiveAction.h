//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/RecursiveAction.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentRecursiveAction_H_
#define _JavaUtilConcurrentRecursiveAction_H_

@class JavaLangVoid;

#import "JreEmulation.h"
#include "java/util/concurrent/ForkJoinTask.h"

#define JavaUtilConcurrentRecursiveAction_serialVersionUID 5232453952276485070

@interface JavaUtilConcurrentRecursiveAction : JavaUtilConcurrentForkJoinTask {
}

- (void)compute;
- (id)getRawResult;
- (void)setRawResultWithId:(id)mustBeNull;
- (BOOL)exec;
- (id)init;
@end

#endif // _JavaUtilConcurrentRecursiveAction_H_