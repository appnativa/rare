//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/ViewerCreator.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREViewerCreator_H_
#define _RAREViewerCreator_H_

@class JavaLangException;
@class RAREActionLink;
@class RARESPOTViewer;
@protocol RAREViewerCreator_iCallback;
@protocol RAREiFunctionCallback;
@protocol RAREiViewer;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/util/iCancelable.h"
#include "java/lang/Runnable.h"

@interface RAREViewerCreator : NSObject < JavaLangRunnable, RAREUTiCancelable > {
 @public
  id<RAREViewerCreator_iCallback> callback__;
  BOOL cancelRunnableQueued_;
  BOOL canceled_;
  __weak id<RAREiWidget> contextWidget_;
  BOOL createViewer__;
  BOOL handleClosedExceptions_;
  RAREActionLink *link_;
  NSString *target_;
  id<JavaLangRunnable> cancelRunnable_;
  BOOL done_;
  BOOL notifiedOfStatus_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
       withRAREActionLink:(RAREActionLink *)link
withRAREViewerCreator_iCallback:(id<RAREViewerCreator_iCallback>)callback
              withBoolean:(BOOL)createViewer;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context
       withRAREActionLink:(RAREActionLink *)link
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)callback
              withBoolean:(BOOL)createViewer;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context
       withRAREActionLink:(RAREActionLink *)link
             withNSString:(NSString *)target
              withBoolean:(BOOL)createViewer;
- (void)dispose;
- (void)cancelWithBoolean:(BOOL)canInterrupt;
+ (RAREViewerCreator *)createConfigurationWithRAREiWidget:(id<RAREiWidget>)context
                                       withRAREActionLink:(RAREActionLink *)link
                          withRAREViewerCreator_iCallback:(id<RAREViewerCreator_iCallback>)callback;
+ (RAREViewerCreator *)createConfigurationWithRAREiWidget:(id<RAREiWidget>)context
                                       withRAREActionLink:(RAREActionLink *)link
                                withRAREiFunctionCallback:(id<RAREiFunctionCallback>)callback;
+ (RAREViewerCreator *)createViewerWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREActionLink:(RAREActionLink *)link
                   withRAREViewerCreator_iCallback:(id<RAREViewerCreator_iCallback>)callback;
+ (RAREViewerCreator *)createViewerWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREActionLink:(RAREActionLink *)link
                         withRAREiFunctionCallback:(id<RAREiFunctionCallback>)callback;
+ (RAREViewerCreator *)createViewerWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREActionLink:(RAREActionLink *)link
                                      withNSString:(NSString *)target;
- (void)notifyOfStartIfNecessary;
- (void)clear;
- (void)run;
- (void)setCancelRunnableWithJavaLangRunnable:(id<JavaLangRunnable>)r;
- (void)setHandleClosedExceptionsWithBoolean:(BOOL)handle;
- (id<JavaLangRunnable>)getCancelRunnable;
- (BOOL)isCanceled;
- (BOOL)isDone;
- (BOOL)isHandleClosedExceptions;
- (void)callbackWithRARESPOTViewer:(RARESPOTViewer *)cfg;
- (void)createViewerWithRARESPOTViewer:(RARESPOTViewer *)cfg;
- (void)errorWithJavaLangException:(JavaLangException *)e;
- (void)errorExWithJavaLangException:(JavaLangException *)e;
- (void)handleCancelRunnable;
- (BOOL)noGood;
- (id<RAREiWidget>)getContext;
- (void)setContextWithRAREiWidget:(id<RAREiWidget>)context;
- (void)copyAllFieldsTo:(RAREViewerCreator *)other;
@end

J2OBJC_FIELD_SETTER(RAREViewerCreator, callback__, id<RAREViewerCreator_iCallback>)
J2OBJC_FIELD_SETTER(RAREViewerCreator, link_, RAREActionLink *)
J2OBJC_FIELD_SETTER(RAREViewerCreator, target_, NSString *)
J2OBJC_FIELD_SETTER(RAREViewerCreator, cancelRunnable_, id<JavaLangRunnable>)

typedef RAREViewerCreator ComAppnativaRareUiViewerCreator;

@protocol RAREViewerCreator_iCallback < NSObject, JavaObject >
- (void)configCreatedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
                  withRARESPOTViewer:(RARESPOTViewer *)config;
- (void)errorHappenedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
               withJavaLangException:(JavaLangException *)e;
- (void)startingOperationWithRAREiWidget:(id<RAREiWidget>)context
                      withRAREActionLink:(RAREActionLink *)link;
- (void)viewerCreatedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
                     withRAREiViewer:(id<RAREiViewer>)viewer;
@end

@interface RAREViewerCreator_$1 : NSObject < RAREViewerCreator_iCallback > {
 @public
  id<RAREiFunctionCallback> val$callback_;
}

- (void)viewerCreatedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
                     withRAREiViewer:(id<RAREiViewer>)viewer;
- (void)errorHappenedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
               withJavaLangException:(JavaLangException *)e;
- (void)configCreatedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
                  withRARESPOTViewer:(RARESPOTViewer *)config;
- (void)startingOperationWithRAREiWidget:(id<RAREiWidget>)context
                      withRAREActionLink:(RAREActionLink *)link;
- (id)initWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREViewerCreator_$1, val$callback_, id<RAREiFunctionCallback>)

@interface RAREViewerCreator_$2 : NSObject < JavaLangRunnable > {
 @public
  RAREViewerCreator *this$0_;
  RARESPOTViewer *val$cfg_;
}

- (void)run;
- (id)initWithRAREViewerCreator:(RAREViewerCreator *)outer$
             withRARESPOTViewer:(RARESPOTViewer *)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREViewerCreator_$2, this$0_, RAREViewerCreator *)
J2OBJC_FIELD_SETTER(RAREViewerCreator_$2, val$cfg_, RARESPOTViewer *)

@interface RAREViewerCreator_$3 : NSObject < JavaLangRunnable > {
 @public
  RAREViewerCreator *this$0_;
  RARESPOTViewer *val$cfg_;
}

- (void)run;
- (id)initWithRAREViewerCreator:(RAREViewerCreator *)outer$
             withRARESPOTViewer:(RARESPOTViewer *)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREViewerCreator_$3, this$0_, RAREViewerCreator *)
J2OBJC_FIELD_SETTER(RAREViewerCreator_$3, val$cfg_, RARESPOTViewer *)

@interface RAREViewerCreator_$4 : NSObject < JavaLangRunnable > {
 @public
  RAREViewerCreator *this$0_;
  JavaLangException *val$e_;
}

- (void)run;
- (id)initWithRAREViewerCreator:(RAREViewerCreator *)outer$
          withJavaLangException:(JavaLangException *)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREViewerCreator_$4, this$0_, RAREViewerCreator *)
J2OBJC_FIELD_SETTER(RAREViewerCreator_$4, val$e_, JavaLangException *)

#endif // _RAREViewerCreator_H_
