//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/util/concurrent/AbstractScheduledService.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonUtilConcurrentAbstractScheduledService_RESTRICT
#define ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonUtilConcurrentAbstractScheduledService_RESTRICT
#if ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_INCLUDE
#define ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_INCLUDE 1
#endif
#if ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$2_INCLUDE
#define ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_INCLUDE 1
#endif
#if ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$1_INCLUDE
#define ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_

@class ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler;
@class ComGoogleCommonUtilConcurrentAbstractService;
@class ComGoogleCommonUtilConcurrentService_StateEnum;
@class JavaLangThrowable;
@class JavaUtilLoggingLogger;
@protocol ComGoogleCommonUtilConcurrentListenableFuture;
@protocol ComGoogleCommonUtilConcurrentService_Listener;
@protocol JavaUtilConcurrentExecutor;
@protocol JavaUtilConcurrentScheduledExecutorService;

#define ComGoogleCommonUtilConcurrentService_RESTRICT 1
#define ComGoogleCommonUtilConcurrentService_INCLUDE 1
#include "com/google/common/util/concurrent/Service.h"

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService : NSObject < ComGoogleCommonUtilConcurrentService > {
 @public
  ComGoogleCommonUtilConcurrentAbstractService *delegate_;
}

+ (JavaUtilLoggingLogger *)logger;
- (id)init;
- (void)runOneIteration;
- (void)startUp;
- (void)shutDown;
- (ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler *)scheduler;
- (id<JavaUtilConcurrentScheduledExecutorService>)executor;
- (NSString *)serviceName;
- (NSString *)description;
- (id<ComGoogleCommonUtilConcurrentListenableFuture>)start;
- (ComGoogleCommonUtilConcurrentService_StateEnum *)startAndWait;
- (BOOL)isRunning;
- (ComGoogleCommonUtilConcurrentService_StateEnum *)state;
- (id<ComGoogleCommonUtilConcurrentListenableFuture>)stop;
- (ComGoogleCommonUtilConcurrentService_StateEnum *)stopAndWait;
- (void)addListenerWithComGoogleCommonUtilConcurrentService_Listener:(id<ComGoogleCommonUtilConcurrentService_Listener>)listener
                                      withJavaUtilConcurrentExecutor:(id<JavaUtilConcurrentExecutor>)executor;
- (JavaLangThrowable *)failureCause;
- (void)copyAllFieldsTo:(ComGoogleCommonUtilConcurrentAbstractScheduledService *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService, delegate_, ComGoogleCommonUtilConcurrentAbstractService *)
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_

@class ComGoogleCommonUtilConcurrentAbstractService;
@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentFuture;
@protocol JavaUtilConcurrentScheduledExecutorService;

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler : NSObject {
}

+ (ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler *)newFixedDelayScheduleWithLong:(long long int)initialDelay
                                                                                          withLong:(long long int)delay
                                                                withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler *)newFixedRateScheduleWithLong:(long long int)initialDelay
                                                                                         withLong:(long long int)period
                                                               withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit OBJC_METHOD_FAMILY_NONE;
- (id<JavaUtilConcurrentFuture>)scheduleWithComGoogleCommonUtilConcurrentAbstractService:(ComGoogleCommonUtilConcurrentAbstractService *)service
                                          withJavaUtilConcurrentScheduledExecutorService:(id<JavaUtilConcurrentScheduledExecutorService>)executor
                                                                    withJavaLangRunnable:(id<JavaLangRunnable>)runnable;
- (id)init;
@end
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$1_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$1_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$1_

@class ComGoogleCommonUtilConcurrentAbstractService;
@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentFuture;
@protocol JavaUtilConcurrentScheduledExecutorService;

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$1 : ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler {
 @public
  long long int val$initialDelay_;
  long long int val$delay_;
  JavaUtilConcurrentTimeUnitEnum *val$unit_;
}

- (id<JavaUtilConcurrentFuture>)scheduleWithComGoogleCommonUtilConcurrentAbstractService:(ComGoogleCommonUtilConcurrentAbstractService *)service
                                          withJavaUtilConcurrentScheduledExecutorService:(id<JavaUtilConcurrentScheduledExecutorService>)executor
                                                                    withJavaLangRunnable:(id<JavaLangRunnable>)task;
- (id)initWithLong:(long long int)capture$0
          withLong:(long long int)capture$1
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)capture$2;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$1, val$unit_, JavaUtilConcurrentTimeUnitEnum *)
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$2_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$2_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$2_

@class ComGoogleCommonUtilConcurrentAbstractService;
@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentFuture;
@protocol JavaUtilConcurrentScheduledExecutorService;

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$2 : ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler {
 @public
  long long int val$initialDelay_;
  long long int val$period_;
  JavaUtilConcurrentTimeUnitEnum *val$unit_;
}

- (id<JavaUtilConcurrentFuture>)scheduleWithComGoogleCommonUtilConcurrentAbstractService:(ComGoogleCommonUtilConcurrentAbstractService *)service
                                          withJavaUtilConcurrentScheduledExecutorService:(id<JavaUtilConcurrentScheduledExecutorService>)executor
                                                                    withJavaLangRunnable:(id<JavaLangRunnable>)task;
- (id)initWithLong:(long long int)capture$0
          withLong:(long long int)capture$1
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)capture$2;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler_$2, val$unit_, JavaUtilConcurrentTimeUnitEnum *)
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_

@class ComGoogleCommonUtilConcurrentAbstractScheduledService;
@class JavaUtilConcurrentLocksReentrantLock;
@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentFuture;
@protocol JavaUtilConcurrentScheduledExecutorService;

#define ComGoogleCommonUtilConcurrentAbstractService_RESTRICT 1
#define ComGoogleCommonUtilConcurrentAbstractService_INCLUDE 1
#include "com/google/common/util/concurrent/AbstractService.h"

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate : ComGoogleCommonUtilConcurrentAbstractService {
 @public
  __weak ComGoogleCommonUtilConcurrentAbstractScheduledService *this$0_;
  id<JavaUtilConcurrentFuture> runningTask_;
  id<JavaUtilConcurrentScheduledExecutorService> executorService_;
  JavaUtilConcurrentLocksReentrantLock *lock_ServiceDelegate_;
  id<JavaLangRunnable> task_;
}

- (void)doStart;
- (void)doStop;
- (id)initWithComGoogleCommonUtilConcurrentAbstractScheduledService:(ComGoogleCommonUtilConcurrentAbstractScheduledService *)outer$;
- (void)copyAllFieldsTo:(ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate, runningTask_, id<JavaUtilConcurrentFuture>)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate, executorService_, id<JavaUtilConcurrentScheduledExecutorService>)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate, lock_ServiceDelegate_, JavaUtilConcurrentLocksReentrantLock *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate, task_, id<JavaLangRunnable>)
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_ServiceDelegateTask_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_ServiceDelegateTask_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_ServiceDelegateTask_

@class ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate;

#define JavaLangRunnable_RESTRICT 1
#define JavaLangRunnable_INCLUDE 1
#include "java/lang/Runnable.h"

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_ServiceDelegateTask : NSObject < JavaLangRunnable > {
 @public
  __weak ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate *this$0_;
}

- (void)run;
- (id)initWithComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate:(ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate *)outer$;
@end
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_$1_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_$1_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_$1_

@class ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate;

#define JavaLangRunnable_RESTRICT 1
#define JavaLangRunnable_INCLUDE 1
#include "java/lang/Runnable.h"

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_$1 : NSObject < JavaLangRunnable > {
 @public
  ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate *this$0_;
}

- (void)run;
- (id)initWithComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate:(ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate *)outer$;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_$1, this$0_, ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate *)
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_$2_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_$2_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_$2_

@class ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate;

#define JavaLangRunnable_RESTRICT 1
#define JavaLangRunnable_INCLUDE 1
#include "java/lang/Runnable.h"

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_$2 : NSObject < JavaLangRunnable > {
 @public
  ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate *this$0_;
}

- (void)run;
- (id)initWithComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate:(ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate *)outer$;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate_$2, this$0_, ComGoogleCommonUtilConcurrentAbstractScheduledService_ServiceDelegate *)
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_

@class ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_Schedule;
@class ComGoogleCommonUtilConcurrentAbstractService;
@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentFuture;
@protocol JavaUtilConcurrentScheduledExecutorService;

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler : ComGoogleCommonUtilConcurrentAbstractScheduledService_Scheduler {
}

- (id<JavaUtilConcurrentFuture>)scheduleWithComGoogleCommonUtilConcurrentAbstractService:(ComGoogleCommonUtilConcurrentAbstractService *)service
                                          withJavaUtilConcurrentScheduledExecutorService:(id<JavaUtilConcurrentScheduledExecutorService>)executor
                                                                    withJavaLangRunnable:(id<JavaLangRunnable>)runnable;
- (ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_Schedule *)getNextSchedule;
- (id)init;
@end
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_ReschedulableCallable_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_ReschedulableCallable_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_ReschedulableCallable_

@class ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler;
@class ComGoogleCommonUtilConcurrentAbstractService;
@class JavaLangVoid;
@class JavaUtilConcurrentLocksReentrantLock;
@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentFuture;
@protocol JavaUtilConcurrentScheduledExecutorService;

#define ComGoogleCommonUtilConcurrentForwardingFuture_RESTRICT 1
#define ComGoogleCommonUtilConcurrentForwardingFuture_INCLUDE 1
#include "com/google/common/util/concurrent/ForwardingFuture.h"

#define JavaUtilConcurrentCallable_RESTRICT 1
#define JavaUtilConcurrentCallable_INCLUDE 1
#include "java/util/concurrent/Callable.h"

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_ReschedulableCallable : ComGoogleCommonUtilConcurrentForwardingFuture < JavaUtilConcurrentCallable > {
 @public
  ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler *this$0_;
  id<JavaLangRunnable> wrappedRunnable_;
  id<JavaUtilConcurrentScheduledExecutorService> executor_;
  ComGoogleCommonUtilConcurrentAbstractService *service_;
  JavaUtilConcurrentLocksReentrantLock *lock_;
  id<JavaUtilConcurrentFuture> currentFuture_;
}

- (id)initWithComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler:(ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler *)outer$
                                   withComGoogleCommonUtilConcurrentAbstractService:(ComGoogleCommonUtilConcurrentAbstractService *)service
                                     withJavaUtilConcurrentScheduledExecutorService:(id<JavaUtilConcurrentScheduledExecutorService>)executor
                                                               withJavaLangRunnable:(id<JavaLangRunnable>)runnable;
- (id)call;
- (void)reschedule;
- (BOOL)cancelWithBoolean:(BOOL)mayInterruptIfRunning;
- (id<JavaUtilConcurrentFuture>)delegate;
- (void)copyAllFieldsTo:(ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_ReschedulableCallable *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_ReschedulableCallable, this$0_, ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_ReschedulableCallable, wrappedRunnable_, id<JavaLangRunnable>)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_ReschedulableCallable, executor_, id<JavaUtilConcurrentScheduledExecutorService>)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_ReschedulableCallable, service_, ComGoogleCommonUtilConcurrentAbstractService *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_ReschedulableCallable, lock_, JavaUtilConcurrentLocksReentrantLock *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_ReschedulableCallable, currentFuture_, id<JavaUtilConcurrentFuture>)
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_Schedule_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_Schedule_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_Schedule_

@class JavaUtilConcurrentTimeUnitEnum;

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_Schedule : NSObject {
 @public
  long long int delay_;
  JavaUtilConcurrentTimeUnitEnum *unit_;
}

- (id)initWithLong:(long long int)delay
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (void)copyAllFieldsTo:(ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_Schedule *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_CustomScheduler_Schedule, unit_, JavaUtilConcurrentTimeUnitEnum *)
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_$1_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_$1_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_$1_

@class JavaLangThread;
@protocol JavaLangRunnable;

#define JavaUtilConcurrentThreadFactory_RESTRICT 1
#define JavaUtilConcurrentThreadFactory_INCLUDE 1
#include "java/util/concurrent/ThreadFactory.h"

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_$1 : NSObject < JavaUtilConcurrentThreadFactory > {
 @public
  NSString *val$serviceName_;
}

- (JavaLangThread *)newThreadWithJavaLangRunnable:(id<JavaLangRunnable>)runnable OBJC_METHOD_FAMILY_NONE;
- (id)initWithNSString:(NSString *)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_$1, val$serviceName_, NSString *)
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractScheduledService_$2_) && (ComGoogleCommonUtilConcurrentAbstractScheduledService_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractScheduledService_$2_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractScheduledService_$2_

@class ComGoogleCommonUtilConcurrentService_StateEnum;
@class JavaLangThrowable;
@protocol JavaUtilConcurrentScheduledExecutorService;

#define ComGoogleCommonUtilConcurrentService_RESTRICT 1
#define ComGoogleCommonUtilConcurrentService_Listener_INCLUDE 1
#include "com/google/common/util/concurrent/Service.h"

@interface ComGoogleCommonUtilConcurrentAbstractScheduledService_$2 : NSObject < ComGoogleCommonUtilConcurrentService_Listener > {
 @public
  id<JavaUtilConcurrentScheduledExecutorService> val$executor_;
}

- (void)starting;
- (void)running;
- (void)stoppingWithComGoogleCommonUtilConcurrentService_StateEnum:(ComGoogleCommonUtilConcurrentService_StateEnum *)from;
- (void)terminatedWithComGoogleCommonUtilConcurrentService_StateEnum:(ComGoogleCommonUtilConcurrentService_StateEnum *)from;
- (void)failedWithComGoogleCommonUtilConcurrentService_StateEnum:(ComGoogleCommonUtilConcurrentService_StateEnum *)from
                                           withJavaLangThrowable:(JavaLangThrowable *)failure;
- (id)initWithJavaUtilConcurrentScheduledExecutorService:(id<JavaUtilConcurrentScheduledExecutorService>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractScheduledService_$2, val$executor_, id<JavaUtilConcurrentScheduledExecutorService>)
#endif
