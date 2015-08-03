//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/CancelableWorker.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARECancelableWorker_H_
#define _RARECancelableWorker_H_

@protocol RAREiWorkerTask;

#import "JreEmulation.h"
#include "com/appnativa/util/iCancelable.h"
#include "java/lang/Runnable.h"

@interface RARECancelableWorker : NSObject < RAREUTiCancelable, JavaLangRunnable > {
 @public
  BOOL canceled_;
  BOOL done_;
  id<RAREiWorkerTask> task_;
  id result_;
}

- (id)initWithRAREiWorkerTask:(id<RAREiWorkerTask>)task;
- (void)cancelWithBoolean:(BOOL)canInterrupt;
- (void)run;
- (BOOL)isCanceled;
- (BOOL)isDone;
- (void)copyAllFieldsTo:(RARECancelableWorker *)other;
@end

J2OBJC_FIELD_SETTER(RARECancelableWorker, task_, id<RAREiWorkerTask>)
J2OBJC_FIELD_SETTER(RARECancelableWorker, result_, id)

typedef RARECancelableWorker ComAppnativaRareCancelableWorker;

#endif // _RARECancelableWorker_H_