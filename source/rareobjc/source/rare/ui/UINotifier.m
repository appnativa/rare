//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UINotifier.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/spot/PushButton.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UINotifier.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIScreen.h"
#include "com/appnativa/rare/ui/border/UICompoundBorder.h"
#include "com/appnativa/rare/ui/border/UIEmptyBorder.h"
#include "com/appnativa/rare/ui/border/UILineBorder.h"
#include "com/appnativa/rare/ui/event/ExpansionEvent.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformWindowManager.h"
#include "com/appnativa/rare/ui/iPopup.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/widget/PushButtonWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Exception.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/Math.h"
#include "java/lang/Runnable.h"

@implementation RAREUINotifier

static RAREUICompoundBorder * RAREUINotifier_defaultBorder_;

+ (RAREUICompoundBorder *)defaultBorder {
  return RAREUINotifier_defaultBorder_;
}

+ (void)setDefaultBorder:(RAREUICompoundBorder *)defaultBorder {
  RAREUINotifier_defaultBorder_ = defaultBorder;
}

- (id)initWithNSString:(NSString *)text
               withInt:(int)length
 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
  withJavaLangRunnable:(id<JavaLangRunnable>)runner {
  if (self = [super init]) {
    RAREWindowViewer *w = [RAREPlatform getWindowViewer];
    id<RAREiPopup> p = [((id<RAREiPlatformWindowManager>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getWindowManager])) createPopupWithRAREiWidget:w];
    [((id<RAREiPopup>) nil_chk(p)) setTransientWithBoolean:YES];
    [p setTimeoutWithInt:length];
    RARESPOTPushButton *cfg = (RARESPOTPushButton *) check_class_cast([((RAREWindowViewer *) nil_chk(w)) createConfigurationObjectWithNSString:@"PushButton" withNSString:@"Rare.Notifier.button"], [RARESPOTPushButton class]);
    if ([((SPOTPrintableString *) nil_chk(((RARESPOTPushButton *) nil_chk(cfg))->templateName_)) getValue] == nil) {
      [((RARESPOTPushButton_CButtonStyle *) nil_chk(cfg->buttonStyle_)) setValueWithInt:RARESPOTPushButton_CButtonStyle_toggle];
    }
    [((SPOTBoolean *) nil_chk(cfg->wordWrap_)) setValueWithBoolean:YES];
    RAREPushButtonWidget *l = [RAREaPushButtonWidget createWithRAREiContainer:w withRARESPOTPushButton:cfg];
    if ([cfg->templateName_ getValue] == nil) {
      [((RAREPushButtonWidget *) nil_chk(l)) setBackgroundWithRAREUIColor:[RAREColorUtils getBackground]];
      [l setBorderWithRAREiPlatformBorder:[RAREUINotifier getDefaultBorder]];
      [l setMinimumSizeWithNSString:nil withNSString:@"2ln"];
    }
    [((RAREPushButtonWidget *) nil_chk(l)) setTextWithJavaLangCharSequence:text];
    if (icon != nil) {
      [l setIconWithRAREiPlatformIcon:icon];
    }
    [p setContentWithRAREiPlatformComponent:[l getContainerComponent]];
    [p addPopupMenuListenerWithRAREiPopupMenuListener:[[RAREUINotifier_$1 alloc] initWithRAREUINotifier:self withJavaLangRunnable:runner]];
    popupWindow_ = p;
    pushButton_ = l;
  }
  return self;
}

- (id<RAREiPopup>)getPopup {
  return popupWindow_;
}

- (RAREPushButtonWidget *)getPushButton {
  return pushButton_;
}

- (void)showWithFloat:(float)x
            withFloat:(float)y {
  [((id<RAREiPopup>) nil_chk(popupWindow_)) showPopupWithFloat:x withFloat:y];
}

- (void)showWithRAREUINotifier_LocationEnum:(RAREUINotifier_LocationEnum *)location {
  if (location == nil) {
    location = [RAREUINotifier_LocationEnum CENTER];
  }
  RAREUIDimension *size = [[RAREUIDimension alloc] init];
  RAREUIRectangle *rect = [((RAREWindowViewer *) nil_chk([RAREPlatform getWindowViewer])) getBounds];
  [((id<RAREiPopup>) nil_chk(popupWindow_)) getPreferredSizeWithRAREUIDimension:size];
  float x = [JavaLangMath maxWithFloat:0 withFloat:((RAREUIRectangle *) nil_chk(rect))->x_ + ((rect->width_ - size->width_) / 2)];
  float y;
  switch ([location ordinal]) {
    case RAREUINotifier_Location_TOP:
    y = rect->y_ + [RAREUIScreen PLATFORM_PIXELS_1];
    break;
    case RAREUINotifier_Location_BOTTOM:
    y = rect->y_ + rect->height_ - size->height_ - [RAREUIScreen PLATFORM_PIXELS_1];
    break;
    default:
    y = [JavaLangMath maxWithFloat:0 withFloat:rect->y_ + ((rect->height_ - size->height_) / 2)];
    break;
  }
  [self showWithFloat:x withFloat:y];
}

- (void)dispose {
  @try {
    if (popupWindow_ != nil) {
      [popupWindow_ dispose];
    }
    if (pushButton_ != nil) {
      [pushButton_ dispose];
    }
  }
  @catch (JavaLangException *e) {
    [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
  }
  popupWindow_ = nil;
  pushButton_ = nil;
}

+ (void)playSound {
}

+ (void)showMessageWithNSString:(NSString *)text {
  [RAREUINotifier showMessageWithNSString:text withRAREUINotifier_LengthEnum:[RAREUINotifier_LengthEnum SHORT] withRAREUINotifier_LocationEnum:[RAREUINotifier_LocationEnum CENTER]];
}

+ (void)showMessageWithNSString:(NSString *)text
                        withInt:(int)length
withRAREUINotifier_LocationEnum:(RAREUINotifier_LocationEnum *)location {
  [RAREUINotifier showMessageWithNSString:text withInt:length withRAREUINotifier_LocationEnum:location withRAREiPlatformIcon:nil withJavaLangRunnable:nil];
}

+ (void)showMessageWithNSString:(NSString *)text
                        withInt:(int)length
withRAREUINotifier_LocationEnum:(RAREUINotifier_LocationEnum *)location
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
           withJavaLangRunnable:(id<JavaLangRunnable>)runner {
  RAREUINotifier *notifier = [[RAREUINotifier alloc] initWithNSString:text withInt:length withRAREiPlatformIcon:icon withJavaLangRunnable:runner];
  [notifier showWithRAREUINotifier_LocationEnum:location];
}

+ (void)showMessageWithNSString:(NSString *)text
  withRAREUINotifier_LengthEnum:(RAREUINotifier_LengthEnum *)length
withRAREUINotifier_LocationEnum:(RAREUINotifier_LocationEnum *)location {
  int to;
  switch ([length ordinal]) {
    case RAREUINotifier_Length_LONG:
    to = 3000;
    break;
    case RAREUINotifier_Length_MEDIUM:
    to = 2000;
    break;
    default:
    to = 1500;
    break;
  }
  [RAREUINotifier showMessageWithNSString:text withInt:to withRAREUINotifier_LocationEnum:location];
}

+ (id<RAREiPlatformBorder>)getDefaultBorder {
  if (RAREUINotifier_defaultBorder_ == nil) {
    RAREUILineBorder *lb = [[RAREUILineBorder alloc] initWithRAREUIColor:[RAREaUILineBorder getDefaultLineColor] withFloat:[RAREUIScreen PLATFORM_PIXELS_1] withFloat:[RAREUIScreen PLATFORM_PIXELS_6]];
    RAREUIEmptyBorder *eb = [[RAREUIEmptyBorder alloc] initWithFloat:[RAREUIScreen PLATFORM_PIXELS_6]];
    RAREUINotifier_defaultBorder_ = [[RAREUICompoundBorder alloc] initWithRAREiPlatformBorder:lb withRAREiPlatformBorder:eb];
  }
  return RAREUINotifier_defaultBorder_;
}

- (void)copyAllFieldsTo:(RAREUINotifier *)other {
  [super copyAllFieldsTo:other];
  other->popupWindow_ = popupWindow_;
  other->pushButton_ = pushButton_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getPopup", NULL, "LRAREiPopup", 0x1, NULL },
    { "getPushButton", NULL, "LRAREPushButtonWidget", 0x1, NULL },
    { "dispose", NULL, "V", 0x4, NULL },
    { "getDefaultBorder", NULL, "LRAREiPlatformBorder", 0x8, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "defaultBorder_", NULL, 0x8, "LRAREUICompoundBorder" },
    { "popupWindow_", NULL, 0x0, "LRAREiPopup" },
    { "pushButton_", NULL, 0x0, "LRAREPushButtonWidget" },
  };
  static J2ObjcClassInfo _RAREUINotifier = { "UINotifier", "com.appnativa.rare.ui", NULL, 0x1, 4, methods, 3, fields, 0, NULL};
  return &_RAREUINotifier;
}

@end

static RAREUINotifier_LengthEnum *RAREUINotifier_LengthEnum_SHORT;
static RAREUINotifier_LengthEnum *RAREUINotifier_LengthEnum_MEDIUM;
static RAREUINotifier_LengthEnum *RAREUINotifier_LengthEnum_LONG;
IOSObjectArray *RAREUINotifier_LengthEnum_values;

@implementation RAREUINotifier_LengthEnum

+ (RAREUINotifier_LengthEnum *)SHORT {
  return RAREUINotifier_LengthEnum_SHORT;
}
+ (RAREUINotifier_LengthEnum *)MEDIUM {
  return RAREUINotifier_LengthEnum_MEDIUM;
}
+ (RAREUINotifier_LengthEnum *)LONG {
  return RAREUINotifier_LengthEnum_LONG;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREUINotifier_LengthEnum class]) {
    RAREUINotifier_LengthEnum_SHORT = [[RAREUINotifier_LengthEnum alloc] initWithNSString:@"SHORT" withInt:0];
    RAREUINotifier_LengthEnum_MEDIUM = [[RAREUINotifier_LengthEnum alloc] initWithNSString:@"MEDIUM" withInt:1];
    RAREUINotifier_LengthEnum_LONG = [[RAREUINotifier_LengthEnum alloc] initWithNSString:@"LONG" withInt:2];
    RAREUINotifier_LengthEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREUINotifier_LengthEnum_SHORT, RAREUINotifier_LengthEnum_MEDIUM, RAREUINotifier_LengthEnum_LONG, nil } count:3 type:[IOSClass classWithClass:[RAREUINotifier_LengthEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREUINotifier_LengthEnum_values];
}

+ (RAREUINotifier_LengthEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREUINotifier_LengthEnum_values count]; i++) {
    RAREUINotifier_LengthEnum *e = RAREUINotifier_LengthEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREUINotifier_LengthEnum"};
  static J2ObjcClassInfo _RAREUINotifier_LengthEnum = { "Length", "com.appnativa.rare.ui", "UINotifier", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREUINotifier_LengthEnum;
}

@end

static RAREUINotifier_LocationEnum *RAREUINotifier_LocationEnum_TOP;
static RAREUINotifier_LocationEnum *RAREUINotifier_LocationEnum_CENTER;
static RAREUINotifier_LocationEnum *RAREUINotifier_LocationEnum_BOTTOM;
IOSObjectArray *RAREUINotifier_LocationEnum_values;

@implementation RAREUINotifier_LocationEnum

+ (RAREUINotifier_LocationEnum *)TOP {
  return RAREUINotifier_LocationEnum_TOP;
}
+ (RAREUINotifier_LocationEnum *)CENTER {
  return RAREUINotifier_LocationEnum_CENTER;
}
+ (RAREUINotifier_LocationEnum *)BOTTOM {
  return RAREUINotifier_LocationEnum_BOTTOM;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREUINotifier_LocationEnum class]) {
    RAREUINotifier_LocationEnum_TOP = [[RAREUINotifier_LocationEnum alloc] initWithNSString:@"TOP" withInt:0];
    RAREUINotifier_LocationEnum_CENTER = [[RAREUINotifier_LocationEnum alloc] initWithNSString:@"CENTER" withInt:1];
    RAREUINotifier_LocationEnum_BOTTOM = [[RAREUINotifier_LocationEnum alloc] initWithNSString:@"BOTTOM" withInt:2];
    RAREUINotifier_LocationEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREUINotifier_LocationEnum_TOP, RAREUINotifier_LocationEnum_CENTER, RAREUINotifier_LocationEnum_BOTTOM, nil } count:3 type:[IOSClass classWithClass:[RAREUINotifier_LocationEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREUINotifier_LocationEnum_values];
}

+ (RAREUINotifier_LocationEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREUINotifier_LocationEnum_values count]; i++) {
    RAREUINotifier_LocationEnum *e = RAREUINotifier_LocationEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREUINotifier_LocationEnum"};
  static J2ObjcClassInfo _RAREUINotifier_LocationEnum = { "Location", "com.appnativa.rare.ui", "UINotifier", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREUINotifier_LocationEnum;
}

@end
@implementation RAREUINotifier_$1

- (void)popupMenuCanceledWithRAREExpansionEvent:(RAREExpansionEvent *)arg0 {
}

- (void)popupMenuWillBecomeInvisibleWithRAREExpansionEvent:(RAREExpansionEvent *)arg0 {
  [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREUINotifier_$1_$1 alloc] initWithRAREUINotifier_$1:self]];
}

- (void)popupMenuWillBecomeVisibleWithRAREExpansionEvent:(RAREExpansionEvent *)arg0 {
}

- (id)initWithRAREUINotifier:(RAREUINotifier *)outer$
        withJavaLangRunnable:(id<JavaLangRunnable>)capture$0 {
  this$0_ = outer$;
  val$runner_ = capture$0;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREUINotifier" },
    { "val$runner_", NULL, 0x1012, "LJavaLangRunnable" },
  };
  static J2ObjcClassInfo _RAREUINotifier_$1 = { "$1", "com.appnativa.rare.ui", "UINotifier", 0x8000, 0, NULL, 2, fields, 0, NULL};
  return &_RAREUINotifier_$1;
}

@end
@implementation RAREUINotifier_$1_$1

- (void)run {
  [this$0_->this$0_ dispose];
  if (this$0_->val$runner_ != nil) {
    [this$0_->val$runner_ run];
  }
}

- (id)initWithRAREUINotifier_$1:(RAREUINotifier_$1 *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREUINotifier_$1" },
  };
  static J2ObjcClassInfo _RAREUINotifier_$1_$1 = { "$1", "com.appnativa.rare.ui", "UINotifier$$1", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREUINotifier_$1_$1;
}

@end
