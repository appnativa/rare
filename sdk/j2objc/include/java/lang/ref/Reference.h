//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/lang/ref/Reference.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaLangRefReference_H_
#define _JavaLangRefReference_H_

@class JavaLangRefReferenceQueue;

#import "JreEmulation.h"

@interface JavaLangRefReference : NSObject {
 @public
  __weak id referent_;
  __weak JavaLangRefReferenceQueue *queue_;
  JavaLangRefReference *queueNext_;
  JavaLangRefReference *pendingNext_;
}

- (id)init;
- (id)initWithId:(id)r
withJavaLangRefReferenceQueue:(JavaLangRefReferenceQueue *)q;
- (void)clear;
- (BOOL)enqueueInternal;
- (BOOL)enqueue;
- (id)get;
- (BOOL)isEnqueued;
- (void)dealloc;
- (void)initReferent OBJC_METHOD_FAMILY_NONE;
- (void)strengthenReferent;
- (void)weakenReferent;
- (void)deallocReferent;
- (void)copyAllFieldsTo:(JavaLangRefReference *)other;
@end

J2OBJC_FIELD_SETTER(JavaLangRefReference, queueNext_, JavaLangRefReference *)
J2OBJC_FIELD_SETTER(JavaLangRefReference, pendingNext_, JavaLangRefReference *)

#endif // _JavaLangRefReference_H_