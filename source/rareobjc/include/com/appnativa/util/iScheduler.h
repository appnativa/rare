//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/iScheduler.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREUTiScheduler_H_
#define _RAREUTiScheduler_H_

@protocol JavaLangRunnable;
@protocol RAREUTiCancelable;

#import "JreEmulation.h"

@protocol RAREUTiScheduler < NSObject, JavaObject >
- (id<RAREUTiCancelable>)scheduleTaskWithJavaLangRunnable:(id<JavaLangRunnable>)task;
@end

#define ComAppnativaUtilIScheduler RAREUTiScheduler

#endif // _RAREUTiScheduler_H_
