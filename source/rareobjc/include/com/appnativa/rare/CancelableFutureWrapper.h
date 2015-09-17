//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/CancelableFutureWrapper.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARECancelableFutureWrapper_H_
#define _RARECancelableFutureWrapper_H_

@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaUtilConcurrentFuture;

#import "JreEmulation.h"
#include "com/appnativa/rare/iCancelableFuture.h"

@interface RARECancelableFutureWrapper : NSObject < RAREiCancelableFuture > {
 @public
  id<JavaUtilConcurrentFuture> future_;
}

+ (id<RAREiCancelableFuture>)NULL_CANCELABLE_FUTURE;
- (id)initWithJavaUtilConcurrentFuture:(id<JavaUtilConcurrentFuture>)future;
- (void)cancelWithBoolean:(BOOL)mayInterruptIfRunning;
- (id)get;
- (id)getWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (BOOL)isCanceled;
- (BOOL)isDone;
- (void)copyAllFieldsTo:(RARECancelableFutureWrapper *)other;
@end

J2OBJC_FIELD_SETTER(RARECancelableFutureWrapper, future_, id<JavaUtilConcurrentFuture>)

typedef RARECancelableFutureWrapper ComAppnativaRareCancelableFutureWrapper;

#endif // _RARECancelableFutureWrapper_H_
