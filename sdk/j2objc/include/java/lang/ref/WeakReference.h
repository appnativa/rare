//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/lang/ref/WeakReference.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaLangRefWeakReference_H_
#define _JavaLangRefWeakReference_H_

@class JavaLangRefReferenceQueue;

#import "JreEmulation.h"
#include "java/lang/ref/Reference.h"

@interface JavaLangRefWeakReference : JavaLangRefReference {
}

- (id)initWithId:(id)r;
- (id)initWithId:(id)r
withJavaLangRefReferenceQueue:(JavaLangRefReferenceQueue *)q;
@end

#endif // _JavaLangRefWeakReference_H_
