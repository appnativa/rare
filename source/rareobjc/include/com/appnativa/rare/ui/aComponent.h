//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaComponent_H_
#define _RAREaComponent_H_

@class IOSClass;
@class JavaUtilHashMap;
@class RARECellConstraints;
@class RAREChangeEvent;
@class RAREEventListenerList;
@class RAREKeyEvent;
@class RAREMouseEvent;
@class RAREPaintBucket;
@class RAREPropertyChangeSupportEx;
@class RARERenderableDataItem_OrientationEnum;
@class RAREUIColor;
@class RAREUICursor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIImage;
@class RAREUIInsets;
@class RAREUIPoint;
@class RAREUIRectangle;
@class RAREView;
@protocol JavaBeansPropertyChangeListener;
@protocol JavaLangCharSequence;
@protocol RAREiParentComponent;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iImageObserver.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/listener/iFocusListener.h"
#include "com/appnativa/rare/ui/listener/iKeyListener.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"
#include "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#include "com/appnativa/rare/ui/listener/iTextChangeListener.h"
#include "com/appnativa/rare/ui/listener/iViewListener.h"
#include "com/appnativa/rare/ui/painter/iPainterSupport.h"

@interface RAREaComponent : NSObject < RAREiPainterSupport, RAREiPlatformComponent, RAREiMouseListener, RAREiMouseMotionListener, RAREiKeyListener, RAREiViewListener, RAREiTextChangeListener, RAREiFocusListener, RAREiImageObserver > {
 @public
  RAREUIInsets *computeInsets_;
  BOOL useBorderInSizeCalculation_;
  RARERenderableDataItem_OrientationEnum *orientation_;
  BOOL checkMinWhenCalculatingPreferred_;
  RAREPropertyChangeSupportEx *changeSupport_;
  id constraints_;
  BOOL disposed_;
  RAREPaintBucket *focusPaint_;
  BOOL focusPainted_;
  BOOL interactionListenerAdded_;
  RAREEventListenerList *listenerList_;
  BOOL locked_;
  JavaUtilHashMap *properties_;
  NSString *sageHeight_;
  NSString *sageMinHeight_;
  NSString *sageMinWidth_;
  NSString *sageWidth_;
  BOOL sizeLocked_;
  BOOL wantsMoveEvents_;
  BOOL wantsViewResizeEvent_;
  id<RAREiWidget> widget_;
  BOOL opaque_;
  RAREChangeEvent *changeEvent_;
}

- (id)init;
- (void)addFocusListenerWithRAREiFocusListener:(id<RAREiFocusListener>)l;
- (BOOL)isScaleIcon;
- (float)getIconScaleFactor;
- (void)setScaleIconWithBoolean:(BOOL)scale_
                      withFloat:(float)scaleFactor;
- (void)addKeyListenerWithRAREiKeyListener:(id<RAREiKeyListener>)l;
- (void)addMouseListenerWithRAREiMouseListener:(id<RAREiMouseListener>)l;
- (void)addMouseMotionListenerWithRAREiMouseMotionListener:(id<RAREiMouseMotionListener>)l;
- (void)addPropertyChangeListenerWithNSString:(NSString *)propertyName
          withJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener;
- (void)addTextChangeListenerWithRAREiTextChangeListener:(id<RAREiTextChangeListener>)l;
- (void)addViewListenerWithRAREiViewListener:(id<RAREiViewListener>)l;
- (BOOL)adjustMinimumHeightForWidth;
- (void)dispose;
- (void)focusChangedWithId:(id)view
               withBoolean:(BOOL)hasFocus
                    withId:(id)oppositeView
               withBoolean:(BOOL)temporary;
- (BOOL)heightChangesBasedOnWidth;
- (void)imageLoadedWithRAREUIImage:(RAREUIImage *)image;
- (void)keyPressedWithRAREKeyEvent:(RAREKeyEvent *)e;
- (void)keyReleasedWithRAREKeyEvent:(RAREKeyEvent *)e;
- (void)keyTypedWithRAREKeyEvent:(RAREKeyEvent *)e;
- (void)mouseDraggedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)putClientPropertyWithNSString:(NSString *)key
                               withId:(id)value;
- (void)removeFocusListenerWithRAREiFocusListener:(id<RAREiFocusListener>)l;
- (void)removeFromParent;
- (void)removeKeyListenerWithRAREiKeyListener:(id<RAREiKeyListener>)l;
- (void)removeMouseListenerWithRAREiMouseListener:(id<RAREiMouseListener>)l;
- (void)removeMouseMotionListenerWithRAREiMouseMotionListener:(id<RAREiMouseMotionListener>)l;
- (void)removePropertyChangeListenerWithNSString:(NSString *)propertyName
             withJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener;
- (void)removeTextChangeListenerWithRAREiTextChangeListener:(id<RAREiTextChangeListener>)l;
- (void)removeViewListenerWithRAREiViewListener:(id<RAREiViewListener>)l;
- (void)requestFocusWithBoolean:(BOOL)temporary;
- (BOOL)shouldStopEditingWithId:(id)source;
- (void)textChangedWithId:(id)source;
- (BOOL)textChangingWithId:(id)source
                   withInt:(int)startIndex
                   withInt:(int)endIndex
  withJavaLangCharSequence:(id<JavaLangCharSequence>)replacementString;
- (void)updateUI;
- (void)viewHiddenWithRAREChangeEvent:(RAREChangeEvent *)e;
- (void)viewResizedWithRAREChangeEvent:(RAREChangeEvent *)e;
- (void)viewShownWithRAREChangeEvent:(RAREChangeEvent *)e;
- (BOOL)wantsLongPress;
- (BOOL)wantsMouseMovedEvents;
- (BOOL)wantsResizeEvent;
- (void)setBoundsWithRAREUIRectangle:(RAREUIRectangle *)bounds;
- (void)setBoundsWithInt:(int)x
                 withInt:(int)y
                 withInt:(int)width
                 withInt:(int)height;
- (void)setFocusPaintWithRAREPaintBucket:(RAREPaintBucket *)focusPaint;
- (void)setFocusPaintedWithBoolean:(BOOL)focusPainted;
- (void)setLockedWithBoolean:(BOOL)lock;
- (void)setOrientationWithRARERenderableDataItem_OrientationEnum:(RARERenderableDataItem_OrientationEnum *)orientation;
- (void)setSelectedWithBoolean:(BOOL)selected;
- (void)setSizeLockedWithBoolean:(BOOL)sizeLocked;
- (void)setPreferredSizeWithFloat:(float)width
                        withFloat:(float)height;
- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (RAREUIColor *)getBackground;
- (RAREUIColor *)getBackgroundEx;
- (int)getBottom;
- (id)getClientPropertyWithNSString:(NSString *)key;
- (id<RAREiPlatformComponentPainter>)getComponentPainter;
- (RAREUIInsets *)getFocusInsetsWithRAREUIInsets:(RAREUIInsets *)insets;
- (RAREPaintBucket *)getFocusPaintWithRAREPaintBucket:(RAREPaintBucket *)def;
- (RAREUIFont *)getFont;
- (RAREUIFont *)getFontEx;
- (RAREUIColor *)getForeground;
- (RAREUIColor *)getForegroundEx;
- (id<RAREiWidget>)getHostWidget;
- (RAREUIRectangle *)getInnerBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect;
- (RAREUIDimension *)getInnerSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (int)getLeft;
- (RAREUIPoint *)getLocation;
- (RAREUIPoint *)getLocationWithRAREUIPoint:(RAREUIPoint *)loc;
- (RAREUIPoint *)getLocationOnScreen;
- (RAREUIDimension *)getMinimumSize;
- (RAREUIDimension *)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (RAREUIDimension *)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                             withFloat:(float)maxWidth;
- (RARERenderableDataItem_OrientationEnum *)getOrientation;
- (RAREUIPoint *)getOrientedLocationWithRAREUIPoint:(RAREUIPoint *)loc;
- (RAREUIDimension *)getOrientedSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (RAREUIDimension *)getPreferredSize;
- (RAREUIDimension *)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (RAREUIDimension *)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                               withFloat:(float)maxWidth;
- (int)getRight;
- (RAREUIDimension *)getSize;
- (int)getTop;
- (id<RAREiWidget>)getWidget;
- (BOOL)hasChildren;
- (BOOL)hasMouseListeners;
- (BOOL)hasMouseMotionListeners;
- (BOOL)isAnimating;
- (BOOL)isBackgroundSet;
- (BOOL)isDisposed;
- (BOOL)isFocusPainted;
- (BOOL)isFontSet;
- (BOOL)isForegroundSet;
- (BOOL)isLeftToRight;
- (BOOL)isLocked;
- (BOOL)isMouseOver;
- (BOOL)isOpaque;
- (void)setOpaqueWithBoolean:(BOOL)opaque;
- (BOOL)isPartOfAnimation;
- (BOOL)isPressed;
- (BOOL)isSelectable;
- (BOOL)isSelected;
- (BOOL)isSizeLocked;
- (void)firePropertyChangeWithNSString:(NSString *)propertyName
                                withId:(id)oldValue
                                withId:(id)newValue;
- (void)interacted;
- (BOOL)needsHiearachyInvalidated;
+ (void)populateMeasuredSizeCacheWithRAREiParentComponent:(id<RAREiParentComponent>)parent
                                              withBoolean:(BOOL)populateMin;
- (void)putClientPropertyExWithNSString:(NSString *)key
                                 withId:(id)value;
- (id)getClientPropertyExWithNSString:(NSString *)key;
- (id<RAREiPlatformComponentPainter>)getComponentPainterWithBoolean:(BOOL)create;
- (id<RAREiPlatformComponentPainter>)getComponentPainterEx;
- (RAREEventListenerList *)getEventListenerList;
+ (RARECellConstraints *)getMeasuredCellConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                                                  withBoolean:(BOOL)populateMin;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
- (BOOL)hasListenersWithIOSClass:(IOSClass *)cls;
- (void)componentEventWithBoolean:(BOOL)shown
                      withBoolean:(BOOL)resized
                      withBoolean:(BOOL)moved;
- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)param0;
- (RAREUIImage *)capture;
- (id<RAREiPlatformComponent>)copy__ OBJC_METHOD_FAMILY_NONE;
- (void)dispatchEventWithRAREKeyEvent:(RAREKeyEvent *)param0;
- (void)dispatchEventWithRAREMouseEvent:(RAREMouseEvent *)param0;
- (id<RAREiParentComponent>)getParent;
- (id)getProxy;
- (RAREView *)getView;
- (BOOL)hasBeenFocused;
- (BOOL)hasHadInteraction;
- (void)bringToFront;
- (float)getAlpha;
- (id<RAREiPlatformBorder>)getBorder;
- (RAREUIRectangle *)getBounds;
- (int)getHeight;
- (RAREUIPoint *)getLocationOnScreenWithRAREUIPoint:(RAREUIPoint *)param0;
- (id)getNativeView;
- (RAREUIDimension *)getSizeWithRAREUIDimension:(RAREUIDimension *)param0;
- (int)getWidth;
- (int)getX;
- (int)getY;
- (BOOL)isDisplayable;
- (BOOL)isEnabled;
- (BOOL)isFocusOwner;
- (BOOL)isFocusable;
- (BOOL)isShowing;
- (BOOL)isVisible;
- (void)repaint;
- (void)requestFocus;
- (void)revalidate;
- (void)sendToBack;
- (BOOL)setAlphaWithFloat:(float)param0;
- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)param0;
- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)param0;
- (void)setBoundsWithFloat:(float)param0
                 withFloat:(float)param1
                 withFloat:(float)param2
                 withFloat:(float)param3;
- (void)setCursorWithRAREUICursor:(RAREUICursor *)param0;
- (void)setEnabledWithBoolean:(BOOL)param0;
- (void)setFocusableWithBoolean:(BOOL)param0;
- (void)setFontWithRAREUIFont:(RAREUIFont *)param0;
- (void)setForegroundWithRAREUIColor:(RAREUIColor *)param0;
- (void)setLocationWithFloat:(float)param0
                   withFloat:(float)param1;
- (void)setSizeWithFloat:(float)param0
               withFloat:(float)param1;
- (void)setVisibleWithBoolean:(BOOL)param0;
- (void)copyAllFieldsTo:(RAREaComponent *)other;
@end

J2OBJC_FIELD_SETTER(RAREaComponent, computeInsets_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaComponent, orientation_, RARERenderableDataItem_OrientationEnum *)
J2OBJC_FIELD_SETTER(RAREaComponent, changeSupport_, RAREPropertyChangeSupportEx *)
J2OBJC_FIELD_SETTER(RAREaComponent, constraints_, id)
J2OBJC_FIELD_SETTER(RAREaComponent, focusPaint_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaComponent, listenerList_, RAREEventListenerList *)
J2OBJC_FIELD_SETTER(RAREaComponent, properties_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(RAREaComponent, sageHeight_, NSString *)
J2OBJC_FIELD_SETTER(RAREaComponent, sageMinHeight_, NSString *)
J2OBJC_FIELD_SETTER(RAREaComponent, sageMinWidth_, NSString *)
J2OBJC_FIELD_SETTER(RAREaComponent, sageWidth_, NSString *)
J2OBJC_FIELD_SETTER(RAREaComponent, widget_, id<RAREiWidget>)
J2OBJC_FIELD_SETTER(RAREaComponent, changeEvent_, RAREChangeEvent *)

typedef RAREaComponent ComAppnativaRareUiAComponent;

#endif // _RAREaComponent_H_
