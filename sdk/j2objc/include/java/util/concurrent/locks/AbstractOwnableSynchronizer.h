//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/locks/AbstractOwnableSynchronizer.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentLocksAbstractOwnableSynchronizer_H_
#define _JavaUtilConcurrentLocksAbstractOwnableSynchronizer_H_

@class JavaLangThread;

#import "JreEmulation.h"
#include "java/io/Serializable.h"

#define JavaUtilConcurrentLocksAbstractOwnableSynchronizer_serialVersionUID 3737899427754241961

@interface JavaUtilConcurrentLocksAbstractOwnableSynchronizer : NSObject < JavaIoSerializable > {
 @public
  JavaLangThread *exclusiveOwnerThread_;
}

- (id)init;
- (void)setExclusiveOwnerThreadWithJavaLangThread:(JavaLangThread *)t;
- (JavaLangThread *)getExclusiveOwnerThread;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLocksAbstractOwnableSynchronizer *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksAbstractOwnableSynchronizer, exclusiveOwnerThread_, JavaLangThread *)

#endif // _JavaUtilConcurrentLocksAbstractOwnableSynchronizer_H_
