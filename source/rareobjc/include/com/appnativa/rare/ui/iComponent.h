//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiComponent_H_
#define _RAREiComponent_H_

@class RAREKeyEvent;
@class RAREMouseEvent;
@class RARERenderableDataItem_OrientationEnum;
@class RAREUIColor;
@class RAREUICursor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIPoint;
@class RAREUIRectangle;
@protocol JavaBeansPropertyChangeListener;
@protocol RAREiFocusListener;
@protocol RAREiKeyListener;
@protocol RAREiMouseListener;
@protocol RAREiMouseMotionListener;
@protocol RAREiParentComponent;
@protocol RAREiPlatformBorder;
@protocol RAREiTextChangeListener;
@protocol RAREiViewListener;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/painter/iPainterSupport.h"

@protocol RAREiComponent < RAREiPainterSupport, NSObject, JavaObject >
- (void)addFocusListenerWithRAREiFocusListener:(id<RAREiFocusListener>)l;
- (void)addKeyListenerWithRAREiKeyListener:(id<RAREiKeyListener>)l;
- (void)addMouseListenerWithRAREiMouseListener:(id<RAREiMouseListener>)l;
- (void)addMouseMotionListenerWithRAREiMouseMotionListener:(id<RAREiMouseMotionListener>)l;
- (void)addTextChangeListenerWithRAREiTextChangeListener:(id<RAREiTextChangeListener>)l;
- (void)addPropertyChangeListenerWithNSString:(NSString *)propertyName
          withJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener;
- (void)removePropertyChangeListenerWithNSString:(NSString *)propertyName
             withJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener;
- (void)addViewListenerWithRAREiViewListener:(id<RAREiViewListener>)l;
- (BOOL)setAlphaWithFloat:(float)alpha;
- (float)getAlpha;
- (BOOL)adjustMinimumHeightForWidth;
- (void)bringToFront;
- (void)dispatchEventWithRAREKeyEvent:(RAREKeyEvent *)keyEvent;
- (void)dispatchEventWithRAREMouseEvent:(RAREMouseEvent *)mouseEvent;
- (void)dispose;
- (BOOL)heightChangesBasedOnWidth;
- (void)putClientPropertyWithNSString:(NSString *)key
                               withId:(id)value;
- (void)removeFocusListenerWithRAREiFocusListener:(id<RAREiFocusListener>)l;
- (void)removeFromParent;
- (void)removeKeyListenerWithRAREiKeyListener:(id<RAREiKeyListener>)l;
- (void)removeMouseListenerWithRAREiMouseListener:(id<RAREiMouseListener>)l;
- (void)removeMouseMotionListenerWithRAREiMouseMotionListener:(id<RAREiMouseMotionListener>)l;
- (void)removeTextChangeListenerWithRAREiTextChangeListener:(id<RAREiTextChangeListener>)l;
- (void)removeViewListenerWithRAREiViewListener:(id<RAREiViewListener>)l;
- (void)repaint;
- (void)requestFocus;
- (void)revalidate;
- (void)sendToBack;
- (void)updateUI;
- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg;
- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
- (void)setBoundsWithRAREUIRectangle:(RAREUIRectangle *)bounds;
- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height;
- (void)setCursorWithRAREUICursor:(RAREUICursor *)cursor;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setFocusPaintedWithBoolean:(BOOL)focusPainted;
- (void)setFocusableWithBoolean:(BOOL)focusable;
- (void)setFontWithRAREUIFont:(RAREUIFont *)f;
- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg;
- (void)setLocationWithFloat:(float)x
                   withFloat:(float)y;
- (void)setOpaqueWithBoolean:(BOOL)opaque;
- (void)setScaleIconWithBoolean:(BOOL)scale_
                      withFloat:(float)scaleFactor;
- (void)setSelectedWithBoolean:(BOOL)selected;
- (void)setSizeWithFloat:(float)width
               withFloat:(float)height;
- (void)setPreferredSizeWithFloat:(float)width
                        withFloat:(float)height;
- (void)setSizeLockedWithBoolean:(BOOL)sizeLocked;
- (void)setVisibleWithBoolean:(BOOL)visible;
- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (RAREUIColor *)getBackground;
- (RAREUIColor *)getBackgroundEx;
- (id<RAREiPlatformBorder>)getBorder;
- (RAREUIRectangle *)getBounds;
- (id)getClientPropertyWithNSString:(NSString *)key;
- (RAREUIFont *)getFont;
- (RAREUIFont *)getFontEx;
- (RAREUIColor *)getForeground;
- (RAREUIColor *)getForegroundEx;
- (int)getHeight;
- (float)getIconScaleFactor;
- (RAREUIRectangle *)getInnerBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect;
- (RAREUIDimension *)getInnerSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (RAREUIPoint *)getLocation;
- (RAREUIPoint *)getLocationWithRAREUIPoint:(RAREUIPoint *)loc;
- (RAREUIPoint *)getLocationOnScreen;
- (RAREUIPoint *)getLocationOnScreenWithRAREUIPoint:(RAREUIPoint *)loc;
- (RAREUIDimension *)getMinimumSize;
- (RAREUIDimension *)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (id)getNativeView;
- (RARERenderableDataItem_OrientationEnum *)getOrientation;
- (RAREUIDimension *)getOrientedSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (id<RAREiParentComponent>)getParent;
- (RAREUIDimension *)getPreferredSize;
- (RAREUIDimension *)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (RAREUIDimension *)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                               withFloat:(float)maxWidth;
- (RAREUIDimension *)getSize;
- (RAREUIDimension *)getSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (id<RAREiWidget>)getWidget;
- (int)getWidth;
- (int)getX;
- (int)getY;
- (BOOL)hasChildren;
- (BOOL)isBackgroundSet;
- (BOOL)isDisplayable;
- (BOOL)isDisposed;
- (BOOL)isEnabled;
- (BOOL)isFocusOwner;
- (BOOL)isFocusPainted;
- (BOOL)isFocusable;
- (BOOL)isFontSet;
- (BOOL)isForegroundSet;
- (BOOL)isLeftToRight;
- (BOOL)isOpaque;
- (BOOL)isScaleIcon;
- (BOOL)isSelectable;
- (BOOL)isSelected;
- (BOOL)isShowing;
- (BOOL)isSizeLocked;
- (BOOL)isVisible;
- (RAREUIDimension *)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                             withFloat:(float)maxWidth;
@end

#define ComAppnativaRareUiIComponent RAREiComponent

#endif // _RAREiComponent_H_
