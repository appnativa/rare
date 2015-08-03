//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/Component.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/platform/apple/ui/util/ActionMap.h"
#include "com/appnativa/rare/platform/apple/ui/util/ImageUtils.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UICursor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIScreen.h"
#include "com/appnativa/rare/ui/aComponent.h"
#include "com/appnativa/rare/ui/effects/aAnimator.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/KeyEvent.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/iGestureListener.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/listener/iKeyListener.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"
#include "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#include "com/appnativa/rare/ui/listener/iViewListener.h"
#include "com/appnativa/rare/ui/painter/UISimpleBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformPainter.h"
#include "java/lang/Boolean.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/Exception.h"
#include "java/lang/InternalError.h"
#include "java/lang/UnsupportedOperationException.h"

@implementation RAREComponent

- (id)initWithRAREView:(RAREView *)view {
  if (self = [super init]) {
    self->view_ = view;
    [((RAREView *) nil_chk(view)) setComponentWithRAREComponent:self];
    useBorderInSizeCalculation_ = YES;
  }
  return self;
}

- (id)init {
  if (self = [super init]) {
    useBorderInSizeCalculation_ = YES;
  }
  return self;
}

- (void)addFlingListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
  [self removeGestureListenerWithRAREiGestureListener:l];
  [((RAREEventListenerList *) nil_chk([self getEventListenerList])) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiGestureListener)] withId:l];
  if (!flingGestureListenerAdded_) {
    flingGestureListenerAdded_ = YES;
    [((RAREView *) nil_chk(view_)) setFlingGestureListenerWithRAREiGestureListener:self];
  }
}

- (void)addInteactionListener {
  if (!interactionListenerAdded_) {
    [((RAREView *) nil_chk(view_)) setMouseListenerWithRAREiMouseListener:self];
    interactionListenerAdded_ = YES;
  }
}

- (void)addKeyListenerWithRAREiKeyListener:(id<RAREiKeyListener>)l {
  [super addKeyListenerWithRAREiKeyListener:l];
  [((RAREView *) nil_chk(view_)) setKeyboardListenerWithRAREiKeyListener:l];
}

- (void)addLongPressListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
  [self removeGestureListenerWithRAREiGestureListener:l];
  [((RAREEventListenerList *) nil_chk([self getEventListenerList])) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiGestureListener)] withId:l];
  if (!longPressGestureListenerAdded_) {
    longPressGestureListenerAdded_ = YES;
    [((RAREView *) nil_chk(view_)) setLongPressGestureListenerWithRAREiGestureListener:self];
  }
}

- (void)addMouseListenerWithRAREiMouseListener:(id<RAREiMouseListener>)l {
  [super addMouseListenerWithRAREiMouseListener:l];
  [((RAREView *) nil_chk(view_)) setMouseListenerWithRAREiMouseListener:self];
}

- (void)addMouseMotionListenerWithRAREiMouseMotionListener:(id<RAREiMouseMotionListener>)l {
  [super addMouseMotionListenerWithRAREiMouseMotionListener:l];
  [((RAREView *) nil_chk(view_)) setMouseMotionListenerWithRAREiMouseMotionListener:self];
}

- (void)addRotateListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
  [self removeGestureListenerWithRAREiGestureListener:l];
  [((RAREEventListenerList *) nil_chk([self getEventListenerList])) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiGestureListener)] withId:l];
  if (!rotateGestureListenerAdded_) {
    rotateGestureListenerAdded_ = YES;
    [((RAREView *) nil_chk(view_)) setRotateGestureListenerWithRAREiGestureListener:self];
  }
}

- (void)addScaleListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
  [self removeGestureListenerWithRAREiGestureListener:l];
  [((RAREEventListenerList *) nil_chk([self getEventListenerList])) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiGestureListener)] withId:l];
  if (!scaleGestureListenerAdded_) {
    scaleGestureListenerAdded_ = YES;
    [((RAREView *) nil_chk(view_)) setScaleGestureListenerWithRAREiGestureListener:self];
  }
}

- (void)addViewListenerWithRAREiViewListener:(id<RAREiViewListener>)l {
  [super addViewListenerWithRAREiViewListener:l];
  [((RAREView *) nil_chk(view_)) setViewListenerWithRAREiViewListener:self];
}

- (void)bringToFront {
  [((RAREView *) nil_chk(view_)) bringToFront];
}

- (RAREUIImage *)capture {
  return [RAREImageUtils createImageWithRAREiPlatformComponent:self];
}

- (RAREComponent *)cloneWithWithRAREView:(RAREView *)v {
  @try {
    RAREComponent *c = (RAREComponent *) check_class_cast([super clone], [RAREComponent class]);
    ((RAREComponent *) nil_chk(c))->listenerList_ = nil;
    c->view_ = v;
    return c;
  }
  @catch (JavaLangCloneNotSupportedException *ex) {
    @throw [[JavaLangInternalError alloc] initWithNSString:@"CloneNotSupportedException"];
  }
}

- (RAREComponent *)copy__ {
  @try {
    IOSClass *cls = [((RAREView *) nil_chk(view_)) getClass];
    RAREView *v = (RAREView *) check_class_cast([cls newInstance], [RAREView class]);
    RAREComponent *c = (RAREComponent *) check_class_cast([super clone], [RAREComponent class]);
    ((RAREComponent *) nil_chk(c))->listenerList_ = nil;
    c->view_ = v;
    return c;
  }
  @catch (JavaLangCloneNotSupportedException *ex) {
    @throw [[JavaLangInternalError alloc] initWithNSString:@"CloneNotSupportedException"];
  }
  @catch (JavaLangException *ex) {
    @throw [[JavaLangUnsupportedOperationException alloc] initWithJavaLangThrowable:ex];
  }
}

- (void)dispatchEventWithRAREKeyEvent:(RAREKeyEvent *)ke {
  [((RAREView *) nil_chk(view_)) dispatchEventWithRAREKeyEvent:ke];
}

- (void)dispatchEventWithRAREMouseEvent:(RAREMouseEvent *)me {
  [((RAREView *) nil_chk(view_)) dispatchEventWithRAREMouseEvent:me];
}

- (void)dispose {
  [super dispose];
  if (view_ != nil) {
    [view_ dispose];
  }
  view_ = nil;
}

+ (RAREComponent *)findFromViewWithRAREView:(RAREView *)view {
  id<RAREiPlatformComponent> o = (view == nil) ? nil : [view getComponent];
  while ((o != nil) && (view != nil)) {
    view = [((RAREView *) nil_chk(view)) getParent];
    if (view != nil) {
      o = [view getComponent];
    }
  }
  return (RAREComponent *) check_class_cast(o, [RAREComponent class]);
}

+ (RAREComponent *)fromViewWithRAREView:(RAREView *)view {
  return (view == nil) ? nil : (RAREComponent *) check_class_cast([view getComponent], [RAREComponent class]);
}

- (void)onFlingWithId:(id)view
   withRAREMouseEvent:(RAREMouseEvent *)e1
   withRAREMouseEvent:(RAREMouseEvent *)e2
            withFloat:(float)velocityX
            withFloat:(float)velocityY {
  if (listenerList_ != nil) {
    IOSObjectArray *listeners = [listenerList_ getListenerList];
    for (int i = (int) [((IOSObjectArray *) nil_chk(listeners)) count] - 2; i >= 0; i -= 2) {
      if (IOSObjectArray_Get(listeners, i) == [IOSClass classWithProtocol:@protocol(RAREiGestureListener)]) {
        [((id<RAREiGestureListener>) check_protocol_cast(IOSObjectArray_Get(listeners, i + 1), @protocol(RAREiGestureListener))) onFlingWithId:view withRAREMouseEvent:e1 withRAREMouseEvent:e2 withFloat:velocityX withFloat:velocityY];
      }
    }
  }
}

- (void)onLongPressWithId:(id)view
       withRAREMouseEvent:(RAREMouseEvent *)e {
  if (listenerList_ != nil) {
    IOSObjectArray *listeners = [listenerList_ getListenerList];
    for (int i = (int) [((IOSObjectArray *) nil_chk(listeners)) count] - 2; i >= 0; i -= 2) {
      if (IOSObjectArray_Get(listeners, i) == [IOSClass classWithProtocol:@protocol(RAREiGestureListener)]) {
        [((id<RAREiGestureListener>) check_protocol_cast(IOSObjectArray_Get(listeners, i + 1), @protocol(RAREiGestureListener))) onLongPressWithId:view withRAREMouseEvent:e];
      }
    }
  }
}

- (void)onRotateWithId:(id)view
               withInt:(int)type
             withFloat:(float)rotation
             withFloat:(float)velocity
             withFloat:(float)focusX
             withFloat:(float)focusY {
  if (listenerList_ != nil) {
    IOSObjectArray *listeners = [listenerList_ getListenerList];
    for (int i = (int) [((IOSObjectArray *) nil_chk(listeners)) count] - 2; i >= 0; i -= 2) {
      if (IOSObjectArray_Get(listeners, i) == [IOSClass classWithProtocol:@protocol(RAREiGestureListener)]) {
        [((id<RAREiGestureListener>) check_protocol_cast(IOSObjectArray_Get(listeners, i + 1), @protocol(RAREiGestureListener))) onRotateWithId:view withInt:type withFloat:rotation withFloat:velocity withFloat:focusX withFloat:focusY];
      }
    }
  }
}

- (void)onScaleEventWithId:(id)view
                   withInt:(int)type
                    withId:(id)sgd
                 withFloat:(float)factor {
  if (listenerList_ != nil) {
    IOSObjectArray *listeners = [listenerList_ getListenerList];
    for (int i = (int) [((IOSObjectArray *) nil_chk(listeners)) count] - 2; i >= 0; i -= 2) {
      if (IOSObjectArray_Get(listeners, i) == [IOSClass classWithProtocol:@protocol(RAREiGestureListener)]) {
        [((id<RAREiGestureListener>) check_protocol_cast(IOSObjectArray_Get(listeners, i + 1), @protocol(RAREiGestureListener))) onScaleEventWithId:view withInt:type withId:sgd withFloat:factor];
      }
    }
  }
}

- (void)removeGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
  [((RAREEventListenerList *) nil_chk([self getEventListenerList])) removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiGestureListener)] withId:l];
}

- (void)repaint {
  [((RAREView *) nil_chk(view_)) repaint];
}

- (void)requestFocus {
  [((RAREView *) nil_chk(view_)) requestFocus];
}

- (void)revalidate {
  [((RAREView *) nil_chk(view_)) revalidate];
  [view_ repaint];
  if ((parent_ != nil) && [self needsHiearachyInvalidated]) {
    [parent_ revalidate];
  }
}

- (void)sendToBack {
  [((RAREView *) nil_chk(view_)) sendToBack];
}

- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg {
  backgroundColor_ = bg;
  id<RAREiPlatformPainter> p = (bg == nil) ? nil : [RAREColorUtils getPainterWithRAREUIColor:bg];
  if ((p == nil) && (bg != nil)) {
    p = [[RAREUISimpleBackgroundPainter alloc] initWithRAREUIColor:bg];
  }
  if ([(id) p conformsToProtocol: @protocol(RAREiBackgroundPainter)]) {
    [((id<RAREiPlatformComponentPainter>) nil_chk([self getComponentPainterWithBoolean:YES])) setBackgroundPainterWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>) check_protocol_cast(p, @protocol(RAREiBackgroundPainter)) withBoolean:YES];
  }
}

- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border {
  if ([((RAREView *) nil_chk(view_)) getBorder] != border) {
    [((id<RAREiPlatformComponentPainter>) nil_chk([self getComponentPainterWithBoolean:YES])) setBorderWithRAREiPlatformBorder:border];
  }
  else if (border != nil) {
    [((id<RAREiPlatformComponentPainter>) nil_chk([self getComponentPainterWithBoolean:YES])) setBorderWithRAREiPlatformBorder:border];
  }
}

- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height {
  BOOL changed = (width != [self getWidth]) || (height != [self getHeight]);
  [((RAREView *) nil_chk(view_)) setBoundsWithFloat:x withFloat:y withFloat:width withFloat:height];
  if (wantsViewResizeEvent_ && changed) {
    [self componentEventWithBoolean:NO withBoolean:YES withBoolean:NO];
  }
}

- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp {
  id<RAREiPlatformComponentPainter> ocp = [((RAREView *) nil_chk(view_)) getComponentPainter];
  [view_ setComponentPainterWithRAREiPlatformComponentPainter:cp];
  [self firePropertyChangeWithNSString:[RAREiConstants RARE_COMPONENT_PAINTER_PROPERTY] withId:ocp withId:cp];
}

- (void)setCursorWithRAREUICursor:(RAREUICursor *)cursor {
  [((RAREView *) nil_chk(view_)) setCursorWithRAREUICursor:cursor];
}

- (void)setEnabledWithBoolean:(BOOL)enabled {
  [((RAREView *) nil_chk(view_)) setEnabledWithBoolean:enabled];
}

- (void)setFocusableWithBoolean:(BOOL)focusable {
  [((RAREView *) nil_chk(view_)) setFocusableWithBoolean:focusable];
}

- (void)setFontWithRAREUIFont:(RAREUIFont *)f {
  [((RAREView *) nil_chk(view_)) setFontWithRAREUIFont:f];
}

- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg {
  foregroundSet_ = fg != nil;
  [((RAREView *) nil_chk(view_)) setForegroundColorWithRAREUIColor:fg];
  if ((fg != nil) && ([view_ getComponentPainter] != nil)) {
    [((id<RAREiPlatformComponentPainter>) nil_chk([view_ getComponentPainter])) setForegroundColorWithRAREUIColor:fg];
  }
}

- (void)setLocationWithFloat:(float)x
                   withFloat:(float)y {
  [((RAREView *) nil_chk(view_)) setLocationWithFloat:x withFloat:y];
}

- (void)setOpaqueWithBoolean:(BOOL)opaque {
  [super setOpaqueWithBoolean:opaque];
  if (!opaque) {
    [((RAREView *) nil_chk(view_)) makeTransparent];
  }
}

- (void)setParentWithRAREiParentComponent:(id<RAREiParentComponent>)parent {
  self->parent_ = parent;
}

- (void)setSizeWithFloat:(float)width
               withFloat:(float)height {
  BOOL changed = (width != [self getWidth]) || (height != [self getHeight]);
  [((RAREView *) nil_chk(view_)) setSizeWithFloat:width withFloat:height];
  if (wantsViewResizeEvent_ && changed) {
    [self componentEventWithBoolean:NO withBoolean:YES withBoolean:NO];
  }
}

- (void)setSizeWithInt:(int)width
               withInt:(int)height {
  [((RAREView *) nil_chk(view_)) setSizeWithFloat:width withFloat:height];
}

- (void)setVisibleWithBoolean:(BOOL)visible {
  [((RAREView *) nil_chk(view_)) setVisibleWithBoolean:visible];
}

- (RAREActionMap *)getActionMap {
  if (actionMap_ == nil) {
    actionMap_ = [[RAREActionMap alloc] init];
    [self addKeyListenerWithRAREiKeyListener:actionMap_];
  }
  return actionMap_;
}

- (RAREUIColor *)getBackgroundEx {
  RAREUIColor *bg = [((RAREView *) nil_chk(view_)) getBackgroundColor];
  return (bg == nil) ? backgroundColor_ : bg;
}

- (id<RAREiPlatformBorder>)getBorder {
  return [((RAREView *) nil_chk(view_)) getBorder];
}

- (RAREUIRectangle *)getBounds {
  return [((RAREView *) nil_chk(view_)) getBounds];
}

- (RAREUIRectangle *)getBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect {
  return [((RAREView *) nil_chk(view_)) getBoundsWithRAREUIRectangle:rect];
}

- (RAREUIFont *)getFontEx {
  return [((RAREView *) nil_chk(view_)) getFont];
}

- (RAREUIColor *)getForegroundEx {
  return foregroundSet_ ? [((RAREView *) nil_chk(view_)) getForegroundColor] : nil;
}

- (int)getHeight {
  return [RAREUIScreen snapToSizeWithFloat:[((RAREView *) nil_chk(view_)) getHeight]];
}

- (RAREUIPoint *)getLocationWithRAREUIPoint:(RAREUIPoint *)loc {
  return [((RAREView *) nil_chk(view_)) getLocationWithRAREUIPoint:loc];
}

- (RAREUIPoint *)getLocationOnScreenWithRAREUIPoint:(RAREUIPoint *)loc {
  if (loc == nil) {
    loc = [[RAREUIPoint alloc] init];
  }
  id<RAREiParentComponent> pc = [self getParent];
  RARERenderableDataItem_OrientationEnum *o = (pc == nil) ? [RARERenderableDataItem_OrientationEnum HORIZONTAL] : [pc getOrientation];
  if ((o != [RARERenderableDataItem_OrientationEnum VERTICAL_DOWN]) && (o != [RARERenderableDataItem_OrientationEnum VERTICAL_UP])) {
    return [((RAREView *) nil_chk(view_)) getLocationOnScreenWithRAREUIPoint:loc];
  }
  RAREUIPoint *p = [((RAREView *) nil_chk([((id<RAREiParentComponent>) nil_chk(pc)) getView])) getLocationOnScreenWithRAREUIPoint:nil];
  (void) [self getOrientedLocationWithRAREUIPoint:loc];
  ((RAREUIPoint *) nil_chk(loc))->x_ += ((RAREUIPoint *) nil_chk(p))->x_;
  loc->y_ += p->y_;
  return loc;
}

- (id)getNativeView {
  return (view_ == nil) ? nil : [view_ getProxy];
}

- (id<RAREiParentComponent>)getParent {
  return parent_;
}

- (id)getProxy {
  return (view_ == nil) ? nil : [view_ getProxy];
}

- (int)getRight {
  return [self getX] + [self getWidth];
}

- (RAREUIDimension *)getSize {
  return [self getSizeWithRAREUIDimension:[[RAREUIDimension alloc] init]];
}

- (RAREUIDimension *)getSizeWithRAREUIDimension:(RAREUIDimension *)size {
  if (size == nil) {
    size = [[RAREUIDimension alloc] init];
  }
  [((RAREView *) nil_chk(view_)) getSizeWithRAREUIDimension:size];
  return size;
}

- (int)getTop {
  return [self getY];
}

- (RAREView *)getView {
  return view_;
}

- (int)getWidth {
  return [RAREUIScreen snapToSizeWithFloat:[((RAREView *) nil_chk(view_)) getWidth]];
}

- (int)getX {
  return [RAREUIScreen snapToPositionWithFloat:[((RAREView *) nil_chk(view_)) getX]];
}

- (int)getY {
  return [RAREUIScreen snapToPositionWithFloat:[((RAREView *) nil_chk(view_)) getY]];
}

- (BOOL)hasBeenFocused {
  return [((RAREView *) nil_chk(view_)) hasBeenFocused];
}

- (BOOL)hasHadInteraction {
  return [((RAREView *) nil_chk(view_)) hasHadInteraction];
}

- (BOOL)hasMouseListeners {
  return (view_ == nil) ? NO : [view_ getMouseListener] != nil;
}

- (BOOL)hasMouseMotionListeners {
  return (view_ == nil) ? NO : [view_ getMouseMotionListener] != nil;
}

- (BOOL)isDisplayable {
  return (view_ != nil) && [view_ isDisplayable];
}

- (BOOL)isEnabled {
  return [((RAREView *) nil_chk(view_)) isEnabled];
}

- (BOOL)isFocusOwner {
  return [((RAREView *) nil_chk(view_)) isFocusedOrChildOfFocused];
}

- (BOOL)isFocusable {
  return [((RAREView *) nil_chk(view_)) isFocusable];
}

- (BOOL)isPartOfAnimation {
  return [RAREaAnimator isAnimatingWithRAREiPlatformComponent:self];
}

- (BOOL)isPressed {
  return [((RAREView *) nil_chk(view_)) isPressed];
}

- (BOOL)isSelected {
  return [((RAREView *) nil_chk(view_)) isSelected];
}

- (BOOL)isShowing {
  return (view_ == nil) ? NO : [view_ isShowing];
}

- (BOOL)isVisible {
  return (view_ == nil) ? NO : [view_ isVisible];
}

- (void)makeOrphan {
  if (view_ != nil) {
    [view_ makeOrphan];
  }
  parent_ = nil;
}

- (void)interacted {
  interactionListenerAdded_ = NO;
  if (listenerList_ != nil) {
    if ([listenerList_ getListenerCountWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiMouseListener)]] == 0) {
      [((RAREView *) nil_chk(view_)) setMouseListenerWithRAREiMouseListener:nil];
    }
  }
  [self putClientPropertyWithNSString:[RAREiConstants RARE_HAD_INTERACTION_PROPERTY] withId:[JavaLangBoolean getTRUE]];
  [self firePropertyChangeWithNSString:[RAREiConstants RARE_HAD_INTERACTION_PROPERTY] withId:[JavaLangBoolean getFALSE] withId:[JavaLangBoolean getTRUE]];
}

- (BOOL)needsHiearachyInvalidated {
  return YES;
}

- (void)setViewWithRAREView:(RAREView *)view {
  if (self->view_ != nil) {
    [self->view_ setComponentWithRAREComponent:nil];
  }
  self->view_ = view;
  [((RAREView *) nil_chk(view)) setComponentWithRAREComponent:self];
}

- (id<RAREiPlatformComponentPainter>)getComponentPainterEx {
  return [((RAREView *) nil_chk(view_)) getComponentPainter];
}

- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size {
  [((RAREView *) nil_chk(view_)) getMinimumSizeWithRAREUIDimension:size];
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  [((RAREView *) nil_chk(view_)) getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
}

- (void)copyAllFieldsTo:(RAREComponent *)other {
  [super copyAllFieldsTo:other];
  other->actionMap_ = actionMap_;
  other->backgroundColor_ = backgroundColor_;
  other->flingGestureListenerAdded_ = flingGestureListenerAdded_;
  other->foregroundSet_ = foregroundSet_;
  other->longPressGestureListenerAdded_ = longPressGestureListenerAdded_;
  other->parent_ = parent_;
  other->rotateGestureListenerAdded_ = rotateGestureListenerAdded_;
  other->scaleGestureListenerAdded_ = scaleGestureListenerAdded_;
  other->view_ = view_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x4, NULL },
    { "capture", NULL, "LRAREUIImage", 0x1, NULL },
    { "cloneWithWithRAREView:", NULL, "LRAREComponent", 0x1, NULL },
    { "copy__", NULL, "LRAREComponent", 0x1, NULL },
    { "findFromViewWithRAREView:", NULL, "LRAREComponent", 0x9, NULL },
    { "fromViewWithRAREView:", NULL, "LRAREComponent", 0x9, NULL },
    { "getActionMap", NULL, "LRAREActionMap", 0x1, NULL },
    { "getBackgroundEx", NULL, "LRAREUIColor", 0x1, NULL },
    { "getBorder", NULL, "LRAREiPlatformBorder", 0x1, NULL },
    { "getBounds", NULL, "LRAREUIRectangle", 0x1, NULL },
    { "getBoundsWithRAREUIRectangle:", NULL, "LRAREUIRectangle", 0x1, NULL },
    { "getFontEx", NULL, "LRAREUIFont", 0x1, NULL },
    { "getForegroundEx", NULL, "LRAREUIColor", 0x1, NULL },
    { "getLocationWithRAREUIPoint:", NULL, "LRAREUIPoint", 0x1, NULL },
    { "getLocationOnScreenWithRAREUIPoint:", NULL, "LRAREUIPoint", 0x1, NULL },
    { "getNativeView", NULL, "LNSObject", 0x1, NULL },
    { "getParent", NULL, "LRAREiParentComponent", 0x1, NULL },
    { "getProxy", NULL, "LNSObject", 0x1, NULL },
    { "getSize", NULL, "LRAREUIDimension", 0x1, NULL },
    { "getSizeWithRAREUIDimension:", NULL, "LRAREUIDimension", 0x1, NULL },
    { "getView", NULL, "LRAREView", 0x1, NULL },
    { "hasBeenFocused", NULL, "Z", 0x1, NULL },
    { "hasHadInteraction", NULL, "Z", 0x1, NULL },
    { "hasMouseListeners", NULL, "Z", 0x1, NULL },
    { "hasMouseMotionListeners", NULL, "Z", 0x1, NULL },
    { "isDisplayable", NULL, "Z", 0x1, NULL },
    { "isEnabled", NULL, "Z", 0x1, NULL },
    { "isFocusOwner", NULL, "Z", 0x1, NULL },
    { "isFocusable", NULL, "Z", 0x1, NULL },
    { "isPartOfAnimation", NULL, "Z", 0x1, NULL },
    { "isPressed", NULL, "Z", 0x1, NULL },
    { "isSelected", NULL, "Z", 0x1, NULL },
    { "isShowing", NULL, "Z", 0x1, NULL },
    { "isVisible", NULL, "Z", 0x1, NULL },
    { "makeOrphan", NULL, "V", 0x0, NULL },
    { "interacted", NULL, "V", 0x4, NULL },
    { "needsHiearachyInvalidated", NULL, "Z", 0x4, NULL },
    { "setViewWithRAREView:", NULL, "V", 0x4, NULL },
    { "getComponentPainterEx", NULL, "LRAREiPlatformComponentPainter", 0x4, NULL },
    { "getMinimumSizeExWithRAREUIDimension:", NULL, "V", 0x4, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "view_", NULL, 0x4, "LRAREView" },
    { "scaleGestureListenerAdded_", NULL, 0x0, "Z" },
    { "rotateGestureListenerAdded_", NULL, 0x0, "Z" },
    { "flingGestureListenerAdded_", NULL, 0x0, "Z" },
    { "longPressGestureListenerAdded_", NULL, 0x0, "Z" },
    { "backgroundColor_", NULL, 0x4, "LRAREUIColor" },
    { "foregroundSet_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREComponent = { "Component", "com.appnativa.rare.ui", NULL, 0x1, 41, methods, 7, fields, 0, NULL};
  return &_RAREComponent;
}

@end