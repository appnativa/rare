//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-statusbar/com/appnativa/rare/ui/aStatusBar.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/spot/Label.h"
#include "com/appnativa/rare/spot/PushButton.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/XPContainer.h"
#include "com/appnativa/rare/ui/aBorderPanel.h"
#include "com/appnativa/rare/ui/aFormsPanel.h"
#include "com/appnativa/rare/ui/aStatusBar.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iProgressBar.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/viewer/aStatusBarViewer.h"
#include "com/appnativa/rare/widget/LabelWidget.h"
#include "com/appnativa/rare/widget/PushButtonWidget.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/iCancelable.h"
#include "java/lang/Exception.h"

@implementation RAREaStatusBar

- (id)initWithId:(id)view {
  return [super initWithId:view];
}

- (void)dispose {
  [super dispose];
  progressStatusBar_ = nil;
}

- (void)configureWithRAREaStatusBarViewer:(RAREaStatusBarViewer *)viewer
                                  withInt:(int)history {
  [self setLayoutWithNSString:@"FILL:DEFAULT:GROW(1.0),d,d" withNSString:@"d"];
  progressStatusBar_ = [[RAREaStatusBar_ProgressStatusBarItem alloc] initWithRAREaStatusBarViewer:viewer withRAREiProgressBar:[self createProgressBar] withInt:history];
  [self addComponentWithRAREiPlatformComponent:progressStatusBar_ withInt:0 withInt:0];
}

- (id<RAREiProgressBar>)createProgressBar {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREaStatusBar_ProgressStatusBarItem *)getProgressStatusBarItem {
  return progressStatusBar_;
}

- (void)copyAllFieldsTo:(RAREaStatusBar *)other {
  [super copyAllFieldsTo:other];
  other->progressStatusBar_ = progressStatusBar_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createProgressBar", NULL, "LRAREiProgressBar", 0x404, NULL },
    { "getProgressStatusBarItem", NULL, "LRAREaStatusBar_ProgressStatusBarItem", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "progressStatusBar_", NULL, 0x4, "LRAREaStatusBar_ProgressStatusBarItem" },
  };
  static J2ObjcClassInfo _RAREaStatusBar = { "aStatusBar", "com.appnativa.rare.ui", NULL, 0x401, 2, methods, 1, fields, 0, NULL};
  return &_RAREaStatusBar;
}

@end
@implementation RAREaStatusBar_ProgressStatusBarItem

static RAREUIInsets * RAREaStatusBar_ProgressStatusBarItem_insets_ProgressStatusBarItem_;

+ (RAREUIInsets *)insets {
  return RAREaStatusBar_ProgressStatusBarItem_insets_ProgressStatusBarItem_;
}

+ (void)setInsets:(RAREUIInsets *)insets_ProgressStatusBarItem {
  RAREaStatusBar_ProgressStatusBarItem_insets_ProgressStatusBarItem_ = insets_ProgressStatusBarItem;
}

- (id)initWithRAREaStatusBarViewer:(RAREaStatusBarViewer *)viewer
              withRAREiProgressBar:(id<RAREiProgressBar>)progress
                           withInt:(int)history {
  if (self = [super init]) {
    defaultStatus_ = @"";
    self->viewer_ = viewer;
    progressBar_ = progress;
    cancelButton_ = [self createCancelButton];
    statusLabel_ = [self createStatusLabel];
    [((id<RAREiActionComponent>) nil_chk(statusLabel_)) setMarginWithRAREUIInsets:RAREaStatusBar_ProgressStatusBarItem_insets_ProgressStatusBarItem_];
    [self setLeftViewWithRAREiPlatformComponent:statusLabel_];
    [self setCenterViewWithRAREiPlatformComponent:[((id<RAREiProgressBar>) nil_chk(progressBar_)) getComponent]];
    [self setRightViewWithRAREiPlatformComponent:cancelButton_];
    [((id<RAREiPlatformComponent>) nil_chk([progressBar_ getComponent])) setVisibleWithBoolean:NO];
    [((id<RAREiActionComponent>) nil_chk(cancelButton_)) setVisibleWithBoolean:NO];
  }
  return self;
}

- (void)cancelPerformed {
  id action = self->cancelAction_;
  [self setStatusWithNSString:[self getDefaultStatus]];
  @try {
    if ([action conformsToProtocol: @protocol(RAREUTiCancelable)]) {
      [((id<RAREUTiCancelable>) check_protocol_cast(action, @protocol(RAREUTiCancelable))) cancelWithBoolean:YES];
    }
    else if ([action conformsToProtocol: @protocol(RAREiActionListener)]) {
      [((id<RAREiActionListener>) check_protocol_cast(action, @protocol(RAREiActionListener))) actionPerformedWithRAREActionEvent:[[RAREActionEvent alloc] initWithId:viewer_ withNSString:@"pregressBarCancelButton"]];
    }
    else if (action != nil) {
      (void) [RAREaWidgetListener evaluateWithRAREiWidget:viewer_ withRAREiScriptHandler:[((RAREaStatusBarViewer *) nil_chk(viewer_)) getScriptHandler] withId:action withJavaUtilEventObject:nil];
    }
  }
  @catch (JavaLangException *ex) {
    [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:ex];
  }
  @finally {
    action = nil;
  }
}

- (void)completePerformed {
  id action = self->completeAction_;
  [self setStatusWithNSString:[self getDefaultStatus]];
  @try {
    if ([action conformsToProtocol: @protocol(RAREUTiCancelable)]) {
      [((id<RAREUTiCancelable>) check_protocol_cast(action, @protocol(RAREUTiCancelable))) cancelWithBoolean:YES];
    }
    else if ([action conformsToProtocol: @protocol(RAREiActionListener)]) {
      [((id<RAREiActionListener>) check_protocol_cast(action, @protocol(RAREiActionListener))) actionPerformedWithRAREActionEvent:[[RAREActionEvent alloc] initWithId:viewer_ withNSString:@"progressBarComplete"]];
    }
    else if (action != nil) {
      (void) [RAREaWidgetListener evaluateWithRAREiWidget:viewer_ withRAREiScriptHandler:[((RAREaStatusBarViewer *) nil_chk(viewer_)) getScriptHandler] withId:action withJavaUtilEventObject:nil];
    }
  }
  @catch (JavaLangException *ex) {
    [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:ex];
  }
  @finally {
    action = nil;
  }
}

- (void)setCancelActionWithId:(id)action {
  cancelAction_ = action;
}

- (void)setCompleteActionWithId:(id)action {
  completeAction_ = action;
}

- (void)setDefaultStatusWithNSString:(NSString *)status {
  defaultStatus_ = status;
}

- (void)setIndeterminateWithBoolean:(BOOL)indeterminate {
  [((id<RAREiProgressBar>) nil_chk(progressBar_)) setIndeterminateWithBoolean:indeterminate];
}

- (void)setProgressWithInt:(int)value {
  [((id<RAREiProgressBar>) nil_chk(progressBar_)) setValueWithInt:value];
}

- (void)setProgressStatusWithNSString:(NSString *)status {
  [((id<RAREiPlatformComponent>) nil_chk([((id<RAREiProgressBar>) nil_chk(progressBar_)) getComponent])) setVisibleWithBoolean:YES];
  [((id<RAREiActionComponent>) nil_chk(statusLabel_)) setTextWithJavaLangCharSequence:status];
  [self revalidate];
  [self repaint];
}

- (void)setShowIconOnlyWithBoolean:(BOOL)showIconOnly {
  self->showIconOnly_ = showIconOnly;
}

- (void)setStatusWithNSString:(NSString *)status {
  cancelAction_ = nil;
  completeAction_ = nil;
  [((id<RAREiPlatformComponent>) nil_chk([((id<RAREiProgressBar>) nil_chk(progressBar_)) getComponent])) setVisibleWithBoolean:NO];
  [((id<RAREiActionComponent>) nil_chk(cancelButton_)) setVisibleWithBoolean:NO];
  [((id<RAREiActionComponent>) nil_chk(statusLabel_)) setTextWithJavaLangCharSequence:status];
  [self revalidate];
  [self repaint];
}

- (id<RAREiActionComponent>)getCancelButton {
  return cancelButton_;
}

- (NSString *)getDefaultStatus {
  return defaultStatus_;
}

- (id<RAREiProgressBar>)getProgressBar {
  return progressBar_;
}

- (id<RAREiActionComponent>)getStatusLabel {
  return statusLabel_;
}

- (BOOL)isFocusable {
  return NO;
}

- (BOOL)isShowIconOnly {
  return showIconOnly_;
}

- (id<RAREiActionComponent>)createCancelButton {
  RAREWindowViewer *w = [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getWindowViewer];
  RARESPOTPushButton *cfg = (RARESPOTPushButton *) check_class_cast([((RAREWindowViewer *) nil_chk(w)) createConfigurationObjectWithNSString:@"PushButton"], [RARESPOTPushButton class]);
  [((SPOTPrintableString *) nil_chk(((RARESPOTPushButton *) nil_chk(cfg))->value_)) setValueWithNSString:[((id<RAREiPlatformAppContext>) nil_chk([w getAppContext])) getResourceAsStringWithNSString:@"Rare.text.cancel"]];
  id<RAREiActionComponent> b = (id<RAREiActionComponent>) check_protocol_cast([((RAREPushButtonWidget *) nil_chk([RAREaPushButtonWidget createWithRAREiContainer:w withRARESPOTPushButton:cfg])) getDataComponent], @protocol(RAREiActionComponent));
  [((id<RAREiActionComponent>) nil_chk(b)) addActionListenerWithRAREiActionListener:[[RAREaStatusBar_ProgressStatusBarItem_$1 alloc] init]];
  return b;
}

- (id<RAREiActionComponent>)createStatusLabel {
  RAREWindowViewer *w = [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getWindowViewer];
  RARESPOTLabel *cfg = (RARESPOTLabel *) check_class_cast([((RAREWindowViewer *) nil_chk(w)) createConfigurationObjectWithNSString:@"Label"], [RARESPOTLabel class]);
  return (id<RAREiActionComponent>) check_protocol_cast([((RARELabelWidget *) nil_chk([RAREaLabelWidget createWithRAREiContainer:viewer_ withRARESPOTLabel:cfg])) getDataComponent], @protocol(RAREiActionComponent));
}

+ (void)initialize {
  if (self == [RAREaStatusBar_ProgressStatusBarItem class]) {
    RAREaStatusBar_ProgressStatusBarItem_insets_ProgressStatusBarItem_ = [[RAREUIInsets alloc] initWithInt:0 withInt:8 withInt:0 withInt:0];
  }
}

- (void)copyAllFieldsTo:(RAREaStatusBar_ProgressStatusBarItem *)other {
  [super copyAllFieldsTo:other];
  other->cancelAction_ = cancelAction_;
  other->cancelButton_ = cancelButton_;
  other->completeAction_ = completeAction_;
  other->defaultStatus_ = defaultStatus_;
  other->progressBar_ = progressBar_;
  other->showIconOnly_ = showIconOnly_;
  other->statusLabel_ = statusLabel_;
  other->viewer_ = viewer_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getCancelButton", NULL, "LRAREiActionComponent", 0x1, NULL },
    { "getDefaultStatus", NULL, "LNSString", 0x1, NULL },
    { "getProgressBar", NULL, "LRAREiProgressBar", 0x1, NULL },
    { "getStatusLabel", NULL, "LRAREiActionComponent", 0x1, NULL },
    { "isFocusable", NULL, "Z", 0x1, NULL },
    { "isShowIconOnly", NULL, "Z", 0x1, NULL },
    { "createCancelButton", NULL, "LRAREiActionComponent", 0x14, NULL },
    { "createStatusLabel", NULL, "LRAREiActionComponent", 0x14, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "insets_ProgressStatusBarItem_", "insets", 0xa, "LRAREUIInsets" },
    { "cancelButton_", NULL, 0x0, "LRAREiActionComponent" },
    { "progressBar_", NULL, 0x0, "LRAREiProgressBar" },
    { "statusLabel_", NULL, 0x0, "LRAREiActionComponent" },
    { "viewer_", NULL, 0x0, "LRAREaStatusBarViewer" },
  };
  static J2ObjcClassInfo _RAREaStatusBar_ProgressStatusBarItem = { "ProgressStatusBarItem", "com.appnativa.rare.ui", "aStatusBar", 0x9, 8, methods, 5, fields, 0, NULL};
  return &_RAREaStatusBar_ProgressStatusBarItem;
}

@end
@implementation RAREaStatusBar_ProgressStatusBarItem_$1

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREaStatusBar_ProgressStatusBarItem_$1 = { "$1", "com.appnativa.rare.ui", "aStatusBar$ProgressStatusBarItem", 0x8000, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREaStatusBar_ProgressStatusBarItem_$1;
}

@end
