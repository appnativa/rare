//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/PopupWindow.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/platform/apple/ui/view/PopupWindow.h"
#include "com/appnativa/rare/platform/apple/ui/view/Window.h"
#include "com/appnativa/rare/platform/apple/ui/view/aWindow.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/WindowPane.h"
#include "com/appnativa/rare/ui/WindowTarget.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/ExpansionEvent.h"
#include "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/util/IdentityArrayList.h"
#include "java/lang/Integer.h"
#include "java/lang/Math.h"
#include "java/lang/Short.h"
#include "java/util/Map.h"
#import "AppleHelper.h"
 #import "APView+Component.h"
 #import "RAREAPPopupWindow.h"

@implementation RAREPopupWindow

- (id)init {
  return [self initRAREPopupWindowWithBoolean:NO];
}

- (id)initRAREPopupWindowWithBoolean:(BOOL)decorated {
  if (self = [self initRAREPopupWindowWithId:[RAREPopupWindow createProxy]]) {
    if (decorated) {
      [self setDecoratedWithBoolean:decorated];
    }
  }
  return self;
}

- (id)initWithBoolean:(BOOL)decorated {
  return [self initRAREPopupWindowWithBoolean:decorated];
}

- (void)disposeEx {
  [super disposeEx];
  listenerList_ = nil;
}

- (id)initRAREPopupWindowWithId:(id)nsview {
  if (self = [super initWithId:nsview]) {
    x_ = JavaLangShort_MIN_VALUE;
    y_ = JavaLangShort_MIN_VALUE;
    requestFocusWhenShown_ = YES;
  }
  return self;
}

- (id)initWithId:(id)nsview {
  return [self initRAREPopupWindowWithId:nsview];
}

- (void)addPopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l {
  if (listenerList_ == nil) {
    listenerList_ = [[RAREEventListenerList alloc] init];
    id<RAREiPopupMenuListener> pl = [[RAREPopupWindow_$1 alloc] initWithRAREPopupWindow:self];
    [self setPopupMenuListenerWithRAREiPopupMenuListener:pl];
  }
  [((RAREEventListenerList *) nil_chk(listenerList_)) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiPopupMenuListener)] withId:l];
}

- (void)cancelPopupWithBoolean:(BOOL)useAnimation {
  [((RAREAPPopupWindow*)proxy_) cancelPopup: useAnimation];
}

- (void)hidePopup {
  [self setVisibleWithBoolean:NO];
}

- (void)removePopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l {
  if (listenerList_ != nil) {
    [listenerList_ removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiPopupMenuListener)] withId:l];
  }
}

- (void)showModalPopup {
  [self setModalWithBoolean:YES];
  [self showPopupWithRAREiPlatformComponent:nil withFloat:JavaLangShort_MIN_VALUE withFloat:JavaLangShort_MIN_VALUE];
}

- (void)showPopup {
  [self showPopupWithRAREiPlatformComponent:nil withFloat:x_ withFloat:y_];
}

- (void)showPopupWithFloat:(float)x
                 withFloat:(float)y {
  [self showPopupWithRAREiPlatformComponent:nil withFloat:x withFloat:y];
}

- (void)setVisibleExWithBoolean:(BOOL)visible {
  [super setVisibleExWithBoolean:visible];
  if (!visible) {
    [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREPopupWindow_$2 alloc] initWithRAREPopupWindow:self]];
  }
}

- (void)showPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)ref
                                  withFloat:(float)x
                                  withFloat:(float)y {
  self->x_ = x;
  self->y_ = y;
  [RAREPlatformHelper hideVirtualKeyboardWithRAREiPlatformComponent:ref];
  if (!sizeSet_) {
    [self pack];
  }
  do {
    if ((x == JavaLangShort_MIN_VALUE) && (y == JavaLangShort_MIN_VALUE) && (ref == nil)) {
      [self centerOnScreen];
      break;
    }
    if (x == JavaLangShort_MIN_VALUE) {
      y = (((RAREUIDimension *) nil_chk([RAREScreenUtils getSize]))->width_ - [self getWidth]) / 2;
    }
    if (y == JavaLangShort_MIN_VALUE) {
      y = (((RAREUIDimension *) nil_chk([RAREScreenUtils getSize]))->height_ - [self getHeight]) / 2;
    }
    if (ref != nil) {
      RAREUIPoint *loc = [ref getLocationOnScreen];
      y += ((RAREUIPoint *) nil_chk(loc))->y_;
      x += loc->x_;
      RAREUIDimension *size = [ref getOrientedSizeWithRAREUIDimension:nil];
      y += ((RAREUIDimension *) nil_chk(size))->height_;
    }
    [self setLocationWithFloat:[JavaLangMath roundWithFloat:x] withFloat:[JavaLangMath roundWithFloat:y]];
  }
  while (NO);
  [self setVisibleWithBoolean:YES];
  if (requestFocusWhenShown_) {
    [self requestFocus];
  }
}

- (void)setDecoratedWithBoolean:(BOOL)decorated {
  [((RAREAPPopupWindow*)proxy_) setDecorated: decorated];
}

- (void)setFocusableWithBoolean:(BOOL)focusable {
  ((RAREAPPopupWindow*)proxy_).focusable=focusable;
}

- (void)setOptionsWithJavaUtilMap:(id<JavaUtilMap>)options {
}

- (void)setPopupLocationWithFloat:(float)x
                        withFloat:(float)y {
  self->x_ = x;
  self->y_ = y;
}

- (void)setPopupOwnerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
}

- (void)setRequestFocusWhenShownWithBoolean:(BOOL)requestFocusWhenShown {
  self->requestFocusWhenShown_ = requestFocusWhenShown;
}

- (void)setTimeoutWithInt:(int)timeout {
  ((RAREAPPopupWindow*)proxy_).timeout=(NSInteger)ceil(timeout/1000.0);
}

- (BOOL)handleOutsideTouch {
  if ([self isTransient]) {
    RAREUTIdentityArrayList *windows = [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getActiveWindows];
    int len = [((RAREUTIdentityArrayList *) nil_chk(windows)) size];
    if ((len > 0) && ([windows getWithInt:len - 1] == self)) {
      [self cancelPopupWithBoolean:YES];
      return YES;
    }
    return NO;
  }
  return YES;
}

- (void)setTransientWithBoolean:(BOOL)istransient {
  ((RAREAPPopupWindow*)proxy_).transient=istransient;
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size {
  [self getPreferredSizeWithRAREUIDimension:size withFloat:0];
}

- (BOOL)isFocusable {
  return ((RAREAPPopupWindow*)proxy_).focusable;
}

- (BOOL)isRequestFocusWhenShown {
  return requestFocusWhenShown_;
}

- (BOOL)isTransient {
  return ((RAREAPPopupWindow*)proxy_).transient;
}

+ (id)createProxy {
  return [RAREAPPopupWindow new];
}

- (void)createTarget {
  NSString *name = [NSString stringWithFormat:@"_popup_window_%@", [JavaLangInteger toHexStringWithInt:(int) [self hash]]];
  target_ = [[RAREWindowTarget alloc] initWithRAREiPlatformAppContext:[RAREPlatform getAppContext] withNSString:name withRAREiParentComponent:rootPane_];
}

- (void)setPopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l {
  [((RAREAPPopupWindow*)proxy_) setPopupMenuListener: l];
}

- (void)copyAllFieldsTo:(RAREPopupWindow *)other {
  [super copyAllFieldsTo:other];
  other->listenerList_ = listenerList_;
  other->requestFocusWhenShown_ = requestFocusWhenShown_;
  other->x_ = x_;
  other->y_ = y_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithId:", NULL, NULL, 0x4, NULL },
    { "cancelPopupWithBoolean:", NULL, "V", 0x101, NULL },
    { "setVisibleExWithBoolean:", NULL, "V", 0x4, NULL },
    { "setDecoratedWithBoolean:", NULL, "V", 0x101, NULL },
    { "setFocusableWithBoolean:", NULL, "V", 0x101, NULL },
    { "setTimeoutWithInt:", NULL, "V", 0x101, NULL },
    { "handleOutsideTouch", NULL, "Z", 0x4, NULL },
    { "setTransientWithBoolean:", NULL, "V", 0x101, NULL },
    { "isFocusable", NULL, "Z", 0x101, NULL },
    { "isRequestFocusWhenShown", NULL, "Z", 0x1, NULL },
    { "isTransient", NULL, "Z", 0x101, NULL },
    { "createProxy", NULL, "LNSObject", 0x10c, NULL },
    { "createTarget", NULL, "V", 0x4, NULL },
    { "setPopupMenuListenerWithRAREiPopupMenuListener:", NULL, "V", 0x104, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "x_", NULL, 0x0, "F" },
    { "y_", NULL, 0x0, "F" },
    { "listenerList_", NULL, 0x0, "LRAREEventListenerList" },
  };
  static J2ObjcClassInfo _RAREPopupWindow = { "PopupWindow", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 14, methods, 3, fields, 0, NULL};
  return &_RAREPopupWindow;
}

@end
@implementation RAREPopupWindow_$1

- (void)popupMenuWillBecomeVisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e {
  if (this$0_->listenerList_ != nil) {
    [RAREUtils firePopupEventWithRAREEventListenerList:this$0_->listenerList_ withRAREExpansionEvent:e withBoolean:YES];
  }
}

- (void)popupMenuWillBecomeInvisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e {
  if (this$0_->listenerList_ != nil) {
    [RAREUtils firePopupEventWithRAREEventListenerList:this$0_->listenerList_ withRAREExpansionEvent:e withBoolean:NO];
  }
}

- (void)popupMenuCanceledWithRAREExpansionEvent:(RAREExpansionEvent *)e {
  if (this$0_->listenerList_ != nil) {
    [RAREUtils firePopupCanceledEventWithRAREEventListenerList:this$0_->listenerList_ withRAREExpansionEvent:e];
  }
}

- (id)initWithRAREPopupWindow:(RAREPopupWindow *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREPopupWindow" },
  };
  static J2ObjcClassInfo _RAREPopupWindow_$1 = { "$1", "com.appnativa.rare.platform.apple.ui.view", "PopupWindow", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREPopupWindow_$1;
}

@end
@implementation RAREPopupWindow_$2

- (void)run {
  [this$0_ dispose];
}

- (id)initWithRAREPopupWindow:(RAREPopupWindow *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREPopupWindow" },
  };
  static J2ObjcClassInfo _RAREPopupWindow_$2 = { "$2", "com.appnativa.rare.platform.apple.ui.view", "PopupWindow", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREPopupWindow_$2;
}

@end
