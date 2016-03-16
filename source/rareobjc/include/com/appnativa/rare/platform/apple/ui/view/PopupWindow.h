//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/PopupWindow.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREPopupWindow_H_
#define _RAREPopupWindow_H_

@class RAREEventListenerList;
@class RAREExpansionEvent;
@class RAREUIDimension;
@protocol JavaUtilMap;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/Window.h"
#include "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#include "com/appnativa/rare/ui/iPopup.h"
#include "java/lang/Runnable.h"

@interface RAREPopupWindow : RAREWindow < RAREiPopup > {
 @public
  float x_;
  float y_;
  BOOL requestFocusWhenShown_;
  RAREEventListenerList *listenerList_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)decorated;
- (void)disposeEx;
- (id)initWithId:(id)nsview;
- (void)addPopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l;
- (void)cancelPopupWithBoolean:(BOOL)useAnimation;
- (void)hidePopup;
- (void)removePopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l;
- (void)showModalPopup;
- (void)showPopup;
- (void)showPopupWithFloat:(float)x
                 withFloat:(float)y;
- (void)setVisibleExWithBoolean:(BOOL)visible;
- (void)showPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)ref
                                  withFloat:(float)x
                                  withFloat:(float)y;
- (void)setDecoratedWithBoolean:(BOOL)decorated;
- (void)setFocusableWithBoolean:(BOOL)focusable;
- (void)setOptionsWithJavaUtilMap:(id<JavaUtilMap>)options;
- (void)setPopupLocationWithFloat:(float)x
                        withFloat:(float)y;
- (void)setPopupOwnerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (void)setRequestFocusWhenShownWithBoolean:(BOOL)requestFocusWhenShown;
- (void)setTimeoutWithInt:(int)timeout;
- (BOOL)handleOutsideTouch;
- (void)setTransientWithBoolean:(BOOL)istransient;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (BOOL)isFocusable;
- (BOOL)isRequestFocusWhenShown;
- (BOOL)isTransient;
+ (id)createProxy;
- (void)createTarget;
- (void)setPopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l;
- (void)copyAllFieldsTo:(RAREPopupWindow *)other;
@end

J2OBJC_FIELD_SETTER(RAREPopupWindow, listenerList_, RAREEventListenerList *)

typedef RAREPopupWindow ComAppnativaRarePlatformAppleUiViewPopupWindow;

@interface RAREPopupWindow_$1 : NSObject < RAREiPopupMenuListener > {
 @public
  RAREPopupWindow *this$0_;
}

- (void)popupMenuWillBecomeVisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuWillBecomeInvisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuCanceledWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (id)initWithRAREPopupWindow:(RAREPopupWindow *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREPopupWindow_$1, this$0_, RAREPopupWindow *)

@interface RAREPopupWindow_$2 : NSObject < JavaLangRunnable > {
 @public
  RAREPopupWindow *this$0_;
}

- (void)run;
- (id)initWithRAREPopupWindow:(RAREPopupWindow *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREPopupWindow_$2, this$0_, RAREPopupWindow *)

#endif // _RAREPopupWindow_H_
