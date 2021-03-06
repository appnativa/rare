//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/Component.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREComponent_H_
#define _RAREComponent_H_

@class RAREActionMap;
@class RAREKeyEvent;
@class RAREMouseEvent;
@class RAREUIColor;
@class RAREUICursor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIImage;
@class RAREUIPoint;
@class RAREUIRectangle;
@class RAREView;
@protocol RAREiKeyListener;
@protocol RAREiMouseListener;
@protocol RAREiMouseMotionListener;
@protocol RAREiParentComponent;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiViewListener;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aComponent.h"
#include "com/appnativa/rare/ui/iGestureListener.h"

@interface RAREComponent : RAREaComponent < RAREiGestureListener > {
 @public
  RAREView *view_;
  RAREActionMap *actionMap_;
  id<RAREiParentComponent> parent_;
  BOOL scaleGestureListenerAdded_;
  BOOL rotateGestureListenerAdded_;
  BOOL flingGestureListenerAdded_;
  BOOL longPressGestureListenerAdded_;
  RAREUIColor *backgroundColor_;
  BOOL foregroundSet_;
  RAREUIColor *fgColor_;
}

- (id)initWithRAREView:(RAREView *)view;
- (id)init;
- (void)addFlingListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l;
- (void)addInteactionListener;
- (void)addKeyListenerWithRAREiKeyListener:(id<RAREiKeyListener>)l;
- (void)addLongPressListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l;
- (void)addMouseListenerWithRAREiMouseListener:(id<RAREiMouseListener>)l;
- (void)addMouseMotionListenerWithRAREiMouseMotionListener:(id<RAREiMouseMotionListener>)l;
- (void)addRotateListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l;
- (void)addScaleListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l;
- (void)addViewListenerWithRAREiViewListener:(id<RAREiViewListener>)l;
- (void)bringToFront;
- (RAREUIImage *)capture;
- (RAREComponent *)cloneWithWithRAREView:(RAREView *)v;
- (RAREComponent *)copy__ OBJC_METHOD_FAMILY_NONE;
- (void)dispatchEventWithRAREKeyEvent:(RAREKeyEvent *)ke;
- (void)dispatchEventWithRAREMouseEvent:(RAREMouseEvent *)me;
- (void)dispose;
+ (RAREComponent *)findFromViewWithRAREView:(RAREView *)view;
+ (RAREComponent *)fromViewWithRAREView:(RAREView *)view;
- (void)onFlingWithId:(id)view
   withRAREMouseEvent:(RAREMouseEvent *)e1
   withRAREMouseEvent:(RAREMouseEvent *)e2
            withFloat:(float)velocityX
            withFloat:(float)velocityY;
- (void)onLongPressWithId:(id)view
       withRAREMouseEvent:(RAREMouseEvent *)e;
- (void)onRotateWithId:(id)view
               withInt:(int)type
             withFloat:(float)rotation
             withFloat:(float)velocity
             withFloat:(float)focusX
             withFloat:(float)focusY;
- (void)onScaleEventWithId:(id)view
                   withInt:(int)type
                    withId:(id)sgd
                 withFloat:(float)factor;
- (void)removeGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l;
- (void)repaint;
- (void)requestFocus;
- (void)revalidate;
- (void)sendToBack;
- (BOOL)setAlphaWithFloat:(float)alpha;
- (float)getAlpha;
- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg;
- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height;
- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp;
- (void)setCursorWithRAREUICursor:(RAREUICursor *)cursor;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setFocusableWithBoolean:(BOOL)focusable;
- (void)setFontWithRAREUIFont:(RAREUIFont *)f;
- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg;
- (void)setLocationWithFloat:(float)x
                   withFloat:(float)y;
- (void)setOpaqueWithBoolean:(BOOL)opaque;
- (void)setParentWithRAREiParentComponent:(id<RAREiParentComponent>)parent;
- (void)setSizeWithFloat:(float)width
               withFloat:(float)height;
- (void)setSizeWithInt:(int)width
               withInt:(int)height;
- (void)setVisibleWithBoolean:(BOOL)visible;
- (RAREActionMap *)getActionMap;
- (RAREUIColor *)getBackgroundEx;
- (id<RAREiPlatformBorder>)getBorder;
- (RAREUIRectangle *)getBounds;
- (RAREUIRectangle *)getBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect;
- (RAREUIFont *)getFontEx;
- (RAREUIColor *)getForegroundEx;
- (int)getHeight;
- (RAREUIPoint *)getLocationWithRAREUIPoint:(RAREUIPoint *)loc;
- (RAREUIPoint *)getLocationOnScreenWithRAREUIPoint:(RAREUIPoint *)loc;
- (id)getNativeView;
- (id<RAREiParentComponent>)getParent;
- (id)getProxy;
- (int)getRight;
- (RAREUIDimension *)getSize;
- (RAREUIDimension *)getSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (int)getTop;
- (RAREView *)getView;
- (int)getWidth;
- (int)getX;
- (int)getY;
- (BOOL)hasBeenFocused;
- (BOOL)hasHadInteraction;
- (BOOL)hasMouseListeners;
- (BOOL)hasMouseMotionListeners;
- (BOOL)isDisplayable;
- (BOOL)isEnabled;
- (BOOL)isFocusOwner;
- (BOOL)isFocusable;
- (BOOL)isPartOfAnimation;
- (BOOL)isPressed;
- (BOOL)isSelected;
- (BOOL)isShowing;
- (BOOL)isVisible;
- (void)makeOrphan;
- (void)interacted;
- (BOOL)needsHiearachyInvalidated;
- (void)setViewWithRAREView:(RAREView *)view;
- (id<RAREiPlatformComponentPainter>)getComponentPainterEx;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
- (void)copyAllFieldsTo:(RAREComponent *)other;
@end

J2OBJC_FIELD_SETTER(RAREComponent, view_, RAREView *)
J2OBJC_FIELD_SETTER(RAREComponent, actionMap_, RAREActionMap *)
J2OBJC_FIELD_SETTER(RAREComponent, parent_, id<RAREiParentComponent>)
J2OBJC_FIELD_SETTER(RAREComponent, backgroundColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREComponent, fgColor_, RAREUIColor *)

typedef RAREComponent ComAppnativaRareUiComponent;

#endif // _RAREComponent_H_
