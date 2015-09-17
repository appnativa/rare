//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/ui/WaitCursorHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/PainterUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/WaitCursorHandler.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/util/MutableInteger.h"
#include "com/appnativa/util/iCancelable.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Exception.h"
#import "RAREWaitCursorView.h"

@implementation RAREWaitCursorHandler

static RAREUTMutableInteger * RAREWaitCursorHandler_cursorCount_;
static RAREWaitCursorHandler_SpinnerDialog * RAREWaitCursorHandler_dialog_;

+ (RAREUTMutableInteger *)cursorCount {
  return RAREWaitCursorHandler_cursorCount_;
}

+ (RAREWaitCursorHandler_SpinnerDialog *)dialog {
  return RAREWaitCursorHandler_dialog_;
}

+ (void)setDialog:(RAREWaitCursorHandler_SpinnerDialog *)dialog {
  RAREWaitCursorHandler_dialog_ = dialog;
}

+ (void)onConfigurationChanged {
}

+ (void)showProgressPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                           withJavaLangCharSequence:(id<JavaLangCharSequence>)message
                              withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable {
  [RAREPlatform runOnUIThreadWithJavaLangRunnable:[[RAREWaitCursorHandler_StartRunnable alloc] initWithJavaLangCharSequence:message withRAREUTiCancelable:cancelable withInt:0]];
}

+ (void)showProgressPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                           withJavaLangCharSequence:(id<JavaLangCharSequence>)message
                              withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                                            withInt:(int)delay {
  [RAREPlatform runOnUIThreadWithJavaLangRunnable:[[RAREWaitCursorHandler_StartRunnable alloc] initWithJavaLangCharSequence:message withRAREUTiCancelable:cancelable withInt:delay]];
}

+ (void)startWaitCursorWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                            withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable {
  [RAREWaitCursorHandler showProgressPopupWithRAREiPlatformComponent:comp withJavaLangCharSequence:nil withRAREUTiCancelable:cancelable withInt:[((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIntWithNSString:@"Rare.WaitCursorHandler.delay" withInt:200]];
}

+ (void)startWaitCursorWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                            withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                                          withInt:(int)delay {
  [RAREWaitCursorHandler showProgressPopupWithRAREiPlatformComponent:comp withJavaLangCharSequence:nil withRAREUTiCancelable:cancelable withInt:delay];
}

+ (void)stopWaitCursorWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                     withBoolean:(BOOL)force {
  [RAREPlatform runOnUIThreadWithJavaLangRunnable:[[RAREWaitCursorHandler_StopRunnable alloc] initWithBoolean:force]];
}

+ (void)stopWaitCursorWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  [RAREPlatform runOnUIThreadWithJavaLangRunnable:[[RAREWaitCursorHandler_StopRunnable alloc] initWithBoolean:NO]];
}

+ (void)updateProgressPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                             withJavaLangCharSequence:(id<JavaLangCharSequence>)message {
  if (RAREWaitCursorHandler_dialog_ == nil) {
    return;
  }
  [RAREPlatform runOnUIThreadWithJavaLangRunnable:[[RAREWaitCursorHandler_UpdateRunnable alloc] initWithJavaLangCharSequence:message]];
}

+ (void)startWaitCursorExWithJavaLangCharSequence:(id<JavaLangCharSequence>)message
                            withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                                          withInt:(int)delay {
  @synchronized (RAREWaitCursorHandler_cursorCount_) {
    if ((RAREWaitCursorHandler_dialog_ == nil) || ![RAREWaitCursorHandler_dialog_ isShowing]) {
      [((RAREUTMutableInteger *) nil_chk(RAREWaitCursorHandler_cursorCount_)) setWithInt:1];
      @try {
        RAREWaitCursorHandler_dialog_ = [RAREWaitCursorHandler_SpinnerDialog showWithJavaLangCharSequence:message withRAREUTiCancelable:cancelable withInt:delay];
      }
      @catch (JavaLangException *e) {
        if (RAREWaitCursorHandler_dialog_ == nil) {
          @try {
            RAREWaitCursorHandler_dialog_ = [RAREWaitCursorHandler_SpinnerDialog showWithJavaLangCharSequence:message withRAREUTiCancelable:cancelable withInt:delay];
          }
          @catch (JavaLangException *ex) {
          }
        }
      }
    }
    else {
      [((RAREUTMutableInteger *) nil_chk(RAREWaitCursorHandler_cursorCount_)) incrementAndGet];
    }
  }
}

+ (void)stopWaitCursorExWithBoolean:(BOOL)force {
  @synchronized (RAREWaitCursorHandler_cursorCount_) {
    if (force || ([((RAREUTMutableInteger *) nil_chk(RAREWaitCursorHandler_cursorCount_)) decrementAndGet] < 1)) {
      @try {
        if (RAREWaitCursorHandler_dialog_ != nil) {
          [RAREWaitCursorHandler_dialog_ dismiss];
          [RAREWaitCursorHandler_dialog_ dispose];
        }
      }
      @catch (JavaLangException *ignore) {
      }
      @finally {
        [((RAREUTMutableInteger *) nil_chk(RAREWaitCursorHandler_cursorCount_)) setWithInt:0];
        RAREWaitCursorHandler_dialog_ = nil;
      }
    }
  }
}

+ (void)hideProgressPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                        withBoolean:(BOOL)force {
  [RAREWaitCursorHandler stopWaitCursorWithRAREiPlatformComponent:comp withBoolean:force];
}

- (id)init {
  return [super init];
}

+ (void)initialize {
  if (self == [RAREWaitCursorHandler class]) {
    RAREWaitCursorHandler_cursorCount_ = [[RAREUTMutableInteger alloc] initWithInt:0];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "startWaitCursorExWithJavaLangCharSequence:withRAREUTiCancelable:withInt:", NULL, "V", 0xa, NULL },
    { "stopWaitCursorExWithBoolean:", NULL, "V", 0xa, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "cursorCount_", NULL, 0x18, "LRAREUTMutableInteger" },
    { "dialog_", NULL, 0x8, "LRAREWaitCursorHandler_SpinnerDialog" },
  };
  static J2ObjcClassInfo _RAREWaitCursorHandler = { "WaitCursorHandler", "com.appnativa.rare.ui", NULL, 0x1, 2, methods, 2, fields, 0, NULL};
  return &_RAREWaitCursorHandler;
}

@end
@implementation RAREWaitCursorHandler_SpinnerDialog

static id<RAREiPlatformComponentPainter> RAREWaitCursorHandler_SpinnerDialog_resuableComponentPainter_;

+ (id<RAREiPlatformComponentPainter>)resuableComponentPainter {
  return RAREWaitCursorHandler_SpinnerDialog_resuableComponentPainter_;
}

+ (void)setResuableComponentPainter:(id<RAREiPlatformComponentPainter>)resuableComponentPainter {
  RAREWaitCursorHandler_SpinnerDialog_resuableComponentPainter_ = resuableComponentPainter;
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)message
             withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                           withInt:(int)delay {
  if (self = [super initWithId:[RAREWaitCursorHandler_SpinnerDialog createProxyWithJavaLangCharSequence:message withRAREUIImage:[RAREWaitCursorHandler_SpinnerDialog getCancelImageWithBoolean:cancelable != nil] withInt:delay]]) {
    self->cancelable_ = cancelable;
  }
  return self;
}

+ (RAREUIImage *)getCancelImageWithBoolean:(BOOL)cancelable {
  if (cancelable) {
    RAREUIImageIcon *ic = (RAREUIImageIcon *) check_class_cast([RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.cancel"], [RAREUIImageIcon class]);
    return [((RAREUIImageIcon *) nil_chk(ic)) getImage];
  }
  return nil;
}

+ (id)createProxyWithJavaLangCharSequence:(id<JavaLangCharSequence>)message
                          withRAREUIImage:(RAREUIImage *)cancelImage
                                  withInt:(int)delay {
  return [[RAREWaitCursorView alloc] initWithMessage:message cancelButtonImage: cancelImage delay: delay];
}

- (void)dismiss {
  [((RAREWaitCursorView*)proxy_) dismiss];
}

- (void)show {
  [((RAREWaitCursorView*)proxy_) show];
}

+ (RAREWaitCursorHandler_SpinnerDialog *)showWithJavaLangCharSequence:(id<JavaLangCharSequence>)message
                                                withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                                                              withInt:(int)delay {
  RAREWaitCursorHandler_SpinnerDialog *dialog = [[RAREWaitCursorHandler_SpinnerDialog alloc] initWithJavaLangCharSequence:message withRAREUTiCancelable:cancelable withInt:delay];
  if (RAREWaitCursorHandler_SpinnerDialog_resuableComponentPainter_ == nil) {
    RAREWaitCursorHandler_SpinnerDialog_resuableComponentPainter_ = [RAREPainterUtils createProgressPopupPainter];
  }
  dialog->componentPainter_ = RAREWaitCursorHandler_SpinnerDialog_resuableComponentPainter_;
  RAREUIColor *fg = [((id<RAREiPlatformComponentPainter>) nil_chk(RAREWaitCursorHandler_SpinnerDialog_resuableComponentPainter_)) getForegroundColor];
  if (fg == nil) {
    fg = [RAREColorUtils getForeground];
  }
  RAREUIColor *color = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.AnimatedSpinner.color"];
  if (color == nil) {
    color = fg;
  }
  [dialog setColorWithRAREUIColor:color withRAREUIColor:fg];
  [dialog show];
  return dialog;
}

- (void)setColorWithRAREUIColor:(RAREUIColor *)spinnerColor
                withRAREUIColor:(RAREUIColor *)labelColor {
  [((RAREWaitCursorView*)proxy_) setSpinnerColor: spinnerColor labelColor: labelColor];
}

- (void)setMessageWithJavaLangCharSequence:(id<JavaLangCharSequence>)message {
  [((RAREWaitCursorView*)proxy_) setMessage: message];
}

- (void)dialogCanceled {
  @synchronized ([RAREWaitCursorHandler cursorCount]) {
    id<RAREUTiCancelable> c = cancelable_;
    cancelable_ = nil;
    [self dismiss];
    [self dispose];
    (void) [RAREWaitCursorHandler setDialog:nil];
    [((RAREUTMutableInteger *) nil_chk([RAREWaitCursorHandler cursorCount])) setWithInt:0];
    if (c != nil) {
      [c cancelWithBoolean:YES];
    }
  }
}

- (void)copyAllFieldsTo:(RAREWaitCursorHandler_SpinnerDialog *)other {
  [super copyAllFieldsTo:other];
  other->cancelable_ = cancelable_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getCancelImageWithBoolean:", NULL, "LRAREUIImage", 0x8, NULL },
    { "createProxyWithJavaLangCharSequence:withRAREUIImage:withInt:", NULL, "LNSObject", 0x109, NULL },
    { "dismiss", NULL, "V", 0x101, NULL },
    { "show", NULL, "V", 0x101, NULL },
    { "showWithJavaLangCharSequence:withRAREUTiCancelable:withInt:", NULL, "LRAREWaitCursorHandler_SpinnerDialog", 0x9, NULL },
    { "setColorWithRAREUIColor:withRAREUIColor:", NULL, "V", 0x100, NULL },
    { "setMessageWithJavaLangCharSequence:", NULL, "V", 0x101, NULL },
    { "dialogCanceled", NULL, "V", 0x0, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "cancelable_", NULL, 0x0, "LRAREUTiCancelable" },
    { "resuableComponentPainter_", NULL, 0x8, "LRAREiPlatformComponentPainter" },
  };
  static J2ObjcClassInfo _RAREWaitCursorHandler_SpinnerDialog = { "SpinnerDialog", "com.appnativa.rare.ui", "WaitCursorHandler", 0x9, 8, methods, 2, fields, 0, NULL};
  return &_RAREWaitCursorHandler_SpinnerDialog;
}

@end
@implementation RAREWaitCursorHandler_StartRunnable

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)message
             withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable
                           withInt:(int)delay {
  if (self = [super init]) {
    self->message_ = message;
    self->cancelable_ = cancelable;
    self->delay_ = delay;
  }
  return self;
}

- (void)run {
  [RAREWaitCursorHandler startWaitCursorExWithJavaLangCharSequence:message_ withRAREUTiCancelable:cancelable_ withInt:delay_];
}

- (void)copyAllFieldsTo:(RAREWaitCursorHandler_StartRunnable *)other {
  [super copyAllFieldsTo:other];
  other->cancelable_ = cancelable_;
  other->delay_ = delay_;
  other->message_ = message_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "cancelable_", NULL, 0x0, "LRAREUTiCancelable" },
    { "message_", NULL, 0x0, "LJavaLangCharSequence" },
    { "delay_", NULL, 0x0, "I" },
  };
  static J2ObjcClassInfo _RAREWaitCursorHandler_StartRunnable = { "StartRunnable", "com.appnativa.rare.ui", "WaitCursorHandler", 0xa, 0, NULL, 3, fields, 0, NULL};
  return &_RAREWaitCursorHandler_StartRunnable;
}

@end
@implementation RAREWaitCursorHandler_StopRunnable

- (id)initWithBoolean:(BOOL)force {
  if (self = [super init]) {
    self->force_ = force;
  }
  return self;
}

- (void)run {
  [RAREWaitCursorHandler stopWaitCursorExWithBoolean:force_];
}

- (void)copyAllFieldsTo:(RAREWaitCursorHandler_StopRunnable *)other {
  [super copyAllFieldsTo:other];
  other->force_ = force_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "force_", NULL, 0x0, "Z" },
  };
  static J2ObjcClassInfo _RAREWaitCursorHandler_StopRunnable = { "StopRunnable", "com.appnativa.rare.ui", "WaitCursorHandler", 0xa, 0, NULL, 1, fields, 0, NULL};
  return &_RAREWaitCursorHandler_StopRunnable;
}

@end
@implementation RAREWaitCursorHandler_UpdateRunnable

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)message {
  if (self = [super init]) {
    self->message_ = message;
  }
  return self;
}

- (void)run {
  if ([RAREWaitCursorHandler dialog] != nil) {
    [[RAREWaitCursorHandler dialog] setMessageWithJavaLangCharSequence:message_];
  }
}

- (void)copyAllFieldsTo:(RAREWaitCursorHandler_UpdateRunnable *)other {
  [super copyAllFieldsTo:other];
  other->message_ = message_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "message_", NULL, 0x0, "LJavaLangCharSequence" },
  };
  static J2ObjcClassInfo _RAREWaitCursorHandler_UpdateRunnable = { "UpdateRunnable", "com.appnativa.rare.ui", "WaitCursorHandler", 0xa, 0, NULL, 1, fields, 0, NULL};
  return &_RAREWaitCursorHandler_UpdateRunnable;
}

@end
