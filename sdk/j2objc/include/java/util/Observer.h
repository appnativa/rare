//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/Observer.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilObserver_H_
#define _JavaUtilObserver_H_

@class JavaUtilObservable;

#import "JreEmulation.h"

@protocol JavaUtilObserver < NSObject, JavaObject >
- (void)updateWithJavaUtilObservable:(JavaUtilObservable *)observable
                              withId:(id)data;
@end

#endif // _JavaUtilObserver_H_
