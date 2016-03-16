//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/ui/WaitCursorHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREWaitCursorHandler_H_
#define _RAREWaitCursorHandler_H_

@class RAREUIColor;
@class RAREUIImage;
@class RAREUTMutableInteger;
@class RAREWaitCursorHandler_SpinnerDialog;
@protocol JavaLangCharSequence;
@protocol RAREUTiCancelable;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentPainter;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "java/lang/Runnable.h"

@interface RAREWaitCursorHandler : NSObject {
}

+ (RAREUTMutableInteger *)cursorCount;
+ (RAREWaitCursorHandler_SpinnerDialog *)dialog;
+ (void)setDialog:(RAREWaitCursorHandler_SpinnerDialog *)dialog;
+ (void)onConfigurationChanged;
+ (void)showProgressPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                           withJavaLangCharSequence:(id<JavaLangCharSequence>)message
                              withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable;
+ (void)showProgressPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                           withJavaLangCharSequence:(id<JavaLangCharSequence>)message
                              withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                                            withInt:(int)delay;
+ (void)startWaitCursorWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                            withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable;
+ (void)startWaitCursorWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                            withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                                          withInt:(int)delay;
+ (void)stopWaitCursorWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                     withBoolean:(BOOL)force;
+ (void)stopWaitCursorWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
+ (void)updateProgressPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                             withJavaLangCharSequence:(id<JavaLangCharSequence>)message;
+ (void)startWaitCursorExWithJavaLangCharSequence:(id<JavaLangCharSequence>)message
                            withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                                          withInt:(int)delay;
+ (void)stopWaitCursorExWithBoolean:(BOOL)force;
+ (void)hideProgressPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                        withBoolean:(BOOL)force;
- (id)init;
@end

typedef RAREWaitCursorHandler ComAppnativaRareUiWaitCursorHandler;

@interface RAREWaitCursorHandler_SpinnerDialog : RAREView {
 @public
  id<RAREUTiCancelable> cancelable_;
}

+ (id<RAREiPlatformComponentPainter>)resuableComponentPainter;
+ (void)setResuableComponentPainter:(id<RAREiPlatformComponentPainter>)resuableComponentPainter;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)message
             withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                           withInt:(int)delay;
+ (RAREUIImage *)getCancelImageWithBoolean:(BOOL)cancelable;
+ (id)createProxyWithJavaLangCharSequence:(id<JavaLangCharSequence>)message
                          withRAREUIImage:(RAREUIImage *)cancelImage
                                  withInt:(int)delay;
- (void)dismiss;
- (void)show;
+ (RAREWaitCursorHandler_SpinnerDialog *)showWithJavaLangCharSequence:(id<JavaLangCharSequence>)message
                                                withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                                                              withInt:(int)delay;
- (void)setColorWithRAREUIColor:(RAREUIColor *)spinnerColor
                withRAREUIColor:(RAREUIColor *)labelColor;
- (void)setMessageWithJavaLangCharSequence:(id<JavaLangCharSequence>)message;
- (void)dialogCanceled;
- (void)copyAllFieldsTo:(RAREWaitCursorHandler_SpinnerDialog *)other;
@end

J2OBJC_FIELD_SETTER(RAREWaitCursorHandler_SpinnerDialog, cancelable_, id<RAREUTiCancelable>)

@interface RAREWaitCursorHandler_StartRunnable : NSObject < JavaLangRunnable > {
 @public
  id<RAREUTiCancelable> cancelable_;
  id<JavaLangCharSequence> message_;
  int delay_;
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)message
             withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                           withInt:(int)delay;
- (void)run;
- (void)copyAllFieldsTo:(RAREWaitCursorHandler_StartRunnable *)other;
@end

J2OBJC_FIELD_SETTER(RAREWaitCursorHandler_StartRunnable, cancelable_, id<RAREUTiCancelable>)
J2OBJC_FIELD_SETTER(RAREWaitCursorHandler_StartRunnable, message_, id<JavaLangCharSequence>)

@interface RAREWaitCursorHandler_StopRunnable : NSObject < JavaLangRunnable > {
 @public
  BOOL force_;
}

- (id)initWithBoolean:(BOOL)force;
- (void)run;
- (void)copyAllFieldsTo:(RAREWaitCursorHandler_StopRunnable *)other;
@end

@interface RAREWaitCursorHandler_UpdateRunnable : NSObject < JavaLangRunnable > {
 @public
  id<JavaLangCharSequence> message_;
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)message;
- (void)run;
- (void)copyAllFieldsTo:(RAREWaitCursorHandler_UpdateRunnable *)other;
@end

J2OBJC_FIELD_SETTER(RAREWaitCursorHandler_UpdateRunnable, message_, id<JavaLangCharSequence>)

#endif // _RAREWaitCursorHandler_H_
