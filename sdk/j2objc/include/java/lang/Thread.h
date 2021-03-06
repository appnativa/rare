//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: Classes/java/lang/Thread.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaLangThread_H_
#define _JavaLangThread_H_

@class IOSObjectArray;
@class JavaLangClassLoader;
@class JavaLangThreadGroup;
@class JavaLangThread_StateEnum;
@class JavaLangThrowable;
@protocol JavaLangThread_UncaughtExceptionHandler;

#import "JreEmulation.h"
#include "java/lang/Enum.h"
#include "java/lang/Runnable.h"

#define JavaLangThread_MAX_PRIORITY 10
#define JavaLangThread_MIN_PRIORITY 1
#define JavaLangThread_NANOS_PER_MILLI 1000000
#define JavaLangThread_NORM_PRIORITY 5
#define JavaLangThread_POLL_INTERVAL 20

@interface JavaLangThread : NSObject < JavaLangRunnable > {
 @public
  id nsThread_;
  id vmThread_;
  BOOL isDaemon__;
  BOOL interrupted__;
  BOOL running_;
  JavaLangClassLoader *contextClassLoader_;
  id blocker_;
  __weak JavaLangThreadGroup *threadGroup_;
  int parkState_;
  id parkBlocker_;
}

+ (JavaLangThreadGroup *)systemThreadGroup;
+ (void)setSystemThreadGroup:(JavaLangThreadGroup *)systemThreadGroup;
+ (JavaLangThreadGroup *)mainThreadGroup;
+ (void)setMainThreadGroup:(JavaLangThreadGroup *)mainThreadGroup;
+ (long long int)threadOrdinalNum;
+ (long long int *)threadOrdinalNumRef;
+ (id<JavaLangThread_UncaughtExceptionHandler>)defaultUncaughtHandler;
+ (void)setDefaultUncaughtHandler:(id<JavaLangThread_UncaughtExceptionHandler>)defaultUncaughtHandler;
+ (int)MAX_PRIORITY;
+ (int)MIN_PRIORITY;
+ (int)NORM_PRIORITY;
+ (NSString *)THREAD;
+ (NSString *)KEY_PREFIX;
+ (NSString *)JAVA_THREAD;
+ (NSString *)TARGET;
+ (NSString *)THREAD_ID;
+ (NSString *)UNCAUGHT_HANDLER;
- (id)init;
- (id)initWithJavaLangRunnable:(id<JavaLangRunnable>)runnable;
- (id)initWithJavaLangRunnable:(id<JavaLangRunnable>)runnable
                  withNSString:(NSString *)threadName;
- (id)initWithNSString:(NSString *)threadName;
- (id)initWithJavaLangThreadGroup:(JavaLangThreadGroup *)group
             withJavaLangRunnable:(id<JavaLangRunnable>)runnable;
- (id)initWithJavaLangThreadGroup:(JavaLangThreadGroup *)group
             withJavaLangRunnable:(id<JavaLangRunnable>)runnable
                     withNSString:(NSString *)threadName
                         withLong:(long long int)stack;
- (id)initWithJavaLangThreadGroup:(JavaLangThreadGroup *)group
             withJavaLangRunnable:(id<JavaLangRunnable>)runnable
                     withNSString:(NSString *)threadName;
- (id)initWithJavaLangThreadGroup:(JavaLangThreadGroup *)group
                     withNSString:(NSString *)threadName;
- (id)initWithJavaLangThreadGroup:(JavaLangThreadGroup *)group
                     withNSString:(NSString *)threadName
                      withBoolean:(BOOL)createThread;
- (void)createWithJavaLangThreadGroup:(JavaLangThreadGroup *)group
                 withJavaLangRunnable:(id<JavaLangRunnable>)target
                         withNSString:(NSString *)name
                             withLong:(long long int)stack
                          withBoolean:(BOOL)createThread;
+ (void)initializeThreadClass OBJC_METHOD_FAMILY_NONE;
+ (JavaLangThread *)currentThread;
- (void)start;
- (void)run;
- (void)run0WithId:(id)arg;
- (void)cancelNativeThread;
+ (int)activeCount;
- (BOOL)isDaemon;
- (void)setDaemonWithBoolean:(BOOL)isDaemon;
+ (int)enumerateWithJavaLangThreadArray:(IOSObjectArray *)threads;
- (long long int)getId;
- (NSString *)getName;
- (void)setNameWithNSString:(NSString *)name;
- (int)getPriority;
- (void)setPriorityWithInt:(int)priority;
- (void)setPriority0WithInt:(int)priority;
- (JavaLangThread_StateEnum *)getState;
- (JavaLangThreadGroup *)getThreadGroup;
- (IOSObjectArray *)getStackTrace;
- (int)countStackFrames;
- (void)interrupt;
+ (BOOL)interrupted;
- (BOOL)isInterrupted;
- (void)join;
- (void)joinWithLong:(long long int)millis;
- (void)joinWithLong:(long long int)millis
             withInt:(int)nanos;
- (BOOL)isAlive;
- (void)checkAccess;
+ (void)sleepWithLong:(long long int)millis;
+ (void)sleepWithLong:(long long int)millis
              withInt:(int)nanos;
+ (void)yield;
- (void)yield0;
+ (long long int)getNextThreadId;
+ (BOOL)holdsLockWithId:(id)object;
- (JavaLangClassLoader *)getContextClassLoader;
- (void)setContextClassLoaderWithJavaLangClassLoader:(JavaLangClassLoader *)cl;
- (NSString *)description;
- (void)unpark;
- (void)parkForWithLong:(long long int)nanos;
- (void)parkUntilWithLong:(long long int)time;
+ (id<JavaLangThread_UncaughtExceptionHandler>)getDefaultUncaughtExceptionHandler;
+ (void)setDefaultUncaughtExceptionHandlerWithJavaLangThread_UncaughtExceptionHandler:(id<JavaLangThread_UncaughtExceptionHandler>)handler;
- (id<JavaLangThread_UncaughtExceptionHandler>)getUncaughtExceptionHandler;
- (void)setUncaughtExceptionHandlerWithJavaLangThread_UncaughtExceptionHandler:(id<JavaLangThread_UncaughtExceptionHandler>)handler;
- (void)copyAllFieldsTo:(JavaLangThread *)other;
@end

J2OBJC_FIELD_SETTER(JavaLangThread, nsThread_, id)
J2OBJC_FIELD_SETTER(JavaLangThread, vmThread_, id)
J2OBJC_FIELD_SETTER(JavaLangThread, contextClassLoader_, JavaLangClassLoader *)
J2OBJC_FIELD_SETTER(JavaLangThread, blocker_, id)
J2OBJC_FIELD_SETTER(JavaLangThread, parkBlocker_, id)

typedef enum {
  JavaLangThread_State_NEW = 0,
  JavaLangThread_State_RUNNABLE = 1,
  JavaLangThread_State_BLOCKED = 2,
  JavaLangThread_State_WAITING = 3,
  JavaLangThread_State_TIMED_WAITING = 4,
  JavaLangThread_State_TERMINATED = 5,
} JavaLangThread_State;

@interface JavaLangThread_StateEnum : JavaLangEnum < NSCopying > {
}
+ (JavaLangThread_StateEnum *)NEW;
+ (JavaLangThread_StateEnum *)RUNNABLE;
+ (JavaLangThread_StateEnum *)BLOCKED;
+ (JavaLangThread_StateEnum *)WAITING;
+ (JavaLangThread_StateEnum *)TIMED_WAITING;
+ (JavaLangThread_StateEnum *)TERMINATED;
+ (IOSObjectArray *)values;
+ (JavaLangThread_StateEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#define JavaLangThread_ParkState_PARKED 3
#define JavaLangThread_ParkState_PREEMPTIVELY_UNPARKED 2
#define JavaLangThread_ParkState_UNPARKED 1

@interface JavaLangThread_ParkState : NSObject {
}

- (id)init;
@end

@protocol JavaLangThread_UncaughtExceptionHandler < NSObject, JavaObject >
- (void)uncaughtExceptionWithJavaLangThread:(JavaLangThread *)t
                      withJavaLangThrowable:(JavaLangThrowable *)e;
@end

@interface JavaLangThread_SystemUncaughtExceptionHandler : NSObject < JavaLangThread_UncaughtExceptionHandler > {
}

- (void)uncaughtExceptionWithJavaLangThread:(JavaLangThread *)t
                      withJavaLangThrowable:(JavaLangThrowable *)e;
- (id)init;
@end

#endif // _JavaLangThread_H_
