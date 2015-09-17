//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/View.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/Window.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/iGestureListener.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#import <UIKit/UIKit.h>
 #import "AppleHelper.h"
 #import "APView+Component.h"
 #import "RAREAPView.h"
 #import "RAREAPWindow.h"
 #import "RAREGestures.h"
 #import "RARECAGradientLayer.h"
 #import "RARECALayer.h"

@implementation RAREView

- (id)initWithId:(id)nsview {
  return [super initWithId:nsview];
}

- (id)init {
  return [super init];
}

- (void)bringToFront {
  UIView * v=(UIView*)proxy_;
  if(v.superview) {
    [v.superview bringSubviewToFront: v];
  }
}

- (void)setAlphaWithFloat:(float)alpha {
  UIView * v=(UIView*)proxy_;
  [v setAlpha:alpha];
}

- (void)sendToBack {
  UIView * v=(UIView*)proxy_;
  if(v.superview) {
    [v.superview sendSubviewToBack: v];
  }
}

- (BOOL)setBackgroundColorExWithRAREUIColor:(RAREUIColor *)bg {
  UIView* v=(UIView*)proxy_;
  v.backgroundColor=bg ? (UIColor*)[bg getAPColor] : [UIColor clearColor];
  return YES;
}

- (void)clearFocus {
  [((UIResponder*)proxy_) resignFirstResponder];
}

- (void)focusNextView {
  UIView* v=(UIView*)proxy_;
  if(v.window) {
    UIResponder *r=[v nextResponder];
    if(r) {
      [r becomeFirstResponder];
    }
  }
}

- (void)focusPreviousView {
}

- (BOOL)inSameWindowWithRAREView:(RAREView *)otherView {
  return ((UIView*)proxy_).window == ((UIView*)otherView->proxy_).window;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g {
  UIView* v=(UIView*)proxy_;
  CGContextRef ctx=(__bridge CGContextRef)[g getContextRef];
  [v.layer renderInContext:ctx];
}

- (void)performClick {
  if ([proxy_ isKindOfClass:[UIControl class]]) {
    [((UIControl*)proxy_) sendActionsForControlEvents: UIControlEventTouchDown|UIControlEventTouchUpInside];
  }
}

- (void)repaint {
  if([NSThread isMainThread]) {
    [((UIView*)proxy_) setNeedsDisplay];
  }
  else {
    dispatch_async(dispatch_get_main_queue(), ^{
      [((UIView*)proxy_) setNeedsDisplay];
    });//end block
  }
}

- (BOOL)requestFocus {
  UIView* v=(UIView*)proxy_;
  if(!v.window) {
    return NO;
  }
  if(![self isFocusable]) {
    return NO;
  }
  return [v becomeFirstResponder];
}

- (void)revalidate {
  if([NSThread isMainThread]) {
    [((UIView*)proxy_) setNeedsLayout];
    CALayer* l=((UIView*)proxy_).layer;
    if([l isKindOfClass:[RARECAGradientLayer class]]) {
      [((RARECAGradientLayer*)l) repaint];
    }
    else {
      [l setNeedsDisplay];
    }
  }
  else {
    dispatch_async(dispatch_get_main_queue(), ^{
      [((UIView*)proxy_) setNeedsLayout];
      CALayer* l=((UIView*)proxy_).layer;
      if([l isKindOfClass:[RARECAGradientLayer class]]) {
        [((RARECAGradientLayer*)l) repaint];
      }
      else {
        [l setNeedsDisplay];
      }
    });//end block
  }
  [((UIView*)proxy_) setNeedsDisplay];
}

- (void)set3DTransformWithId:(id)tx {
  UIView *v=(UIView*)proxy_;
  CATransform3D value;
  [tx getValue:&value];
  v.layer.transform= value;
}

+ (RAREView *)viewFromProxyWithId:(id)proxy {
  return (RAREView*)[((UIView*)proxy) sparView];
}

- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)w
                 withFloat:(float)h {
  UIView* v=(UIView*)proxy_;
  CGRect frame= CGRectMake(x,y,w,h);
  [v setFrame:frame];
}

- (void)setClipMaskWithId:(id)nativepath {
  [AppleHelper setClipMaskWithLayer: [((UIView*)proxy_) layer] withPath: nativepath];
}

- (void)setComponentWithRAREComponent:(RAREComponent *)component {
  self->component_ = component;
  if (component != nil) {
    [self setViewWithRAREView:self];
  }
}

- (void)setFlingGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
  UIView* v=(UIView*)proxy_;
  UIGestureRecognizer * r= [[RAREFlingGestureRecognizer alloc] initWithListener:l];
  [v addGestureRecognizer:r];
}

- (void)setFontWithRAREUIFont:(RAREUIFont *)f {
  font_ = f;
}

- (void)setLocationWithFloat:(float)x
                   withFloat:(float)y {
  UIView* view=(UIView*)proxy_;
  CGRect frame=view.frame;
  frame.origin.x=x;
  frame.origin.y=y;
  view.frame=frame;
}

- (void)setCenterWithFloat:(float)x
                 withFloat:(float)y {
  UIView* view=(UIView*)proxy_;
  view.center=CGPointMake(x,y);
}

- (void)setLongPressGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
  UIView* v=(UIView*)proxy_;
  UIGestureRecognizer * r= [[RARELongPressGestureRecognizer alloc] initWithListener:l];
  [v addGestureRecognizer:r];
}

- (void)setMarginWithRAREUIInsets:(RAREUIInsets *)insets {
  if (insets == nil) {
    [self setMarginWithFloat:0 withFloat:0 withFloat:0 withFloat:0];
  }
  else {
    [self setMarginWithFloat:insets->top_ withFloat:insets->right_ withFloat:insets->bottom_ withFloat:insets->left_];
  }
}

- (void)setPaintHandlerEnabledWithBoolean:(BOOL)enabled {
  [((UIView*)proxy_) setPaintHandlerEnabled: enabled];
}

- (void)setProxyWithId:(id)proxy {
  self->proxy_ = proxy;
  if (proxy != nil) {
    [self setViewWithRAREView:self];
  }
}

- (void)setRotateGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
  UIView* v=(UIView*)proxy_;
  UIGestureRecognizer * r= [[RARERotateGestureRecognizer alloc] initWithListener:l];
  [v addGestureRecognizer:r];
}

- (void)setRotationWithInt:(int)rotation {
  if(rotation_!=rotation)      {
    rotation_ = rotation;
    UIView *v=(UIView*)proxy_;
    if(rotation==0) {
      v.layer.transform=CATransform3DIdentity;
    }
    else{
      v.layer.transform = CATransform3DMakeRotation(rotation / 180.0 * M_PI, 0.0, 0.0, 1.0);
    }
    rotation_=rotation;
  }
}

- (void)setScaleGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
  UIView* v=(UIView*)proxy_;
  UIGestureRecognizer * r= [[RAREScaleGestureRecognizer alloc] initWithListener:l];
  [v addGestureRecognizer:r];
}

- (void)setSizeWithFloat:(float)width
               withFloat:(float)height {
  UIView* view=(UIView*)proxy_;
  CGRect frame=view.frame;
  frame.size.width=width;
  frame.size.height=height;
  view.frame=frame;
}

- (RAREUIRectangle *)getBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect {
  CGRect frame= [((UIView*)proxy_) frame];
  if(!rect) {
    return [RAREUIRectangle fromRect: frame];
  }
  else {
    [rect setWithRect:frame];
    return rect;
  }
}

- (float)getHeight {
  return [((UIView*)proxy_) bounds].size.height;
}

- (RAREUIPoint *)getLocationWithRAREUIPoint:(RAREUIPoint *)loc {
  CGRect frame= [((UIView*)proxy_) frame];
  if(loc) {
    [loc setLocationWithFloat:frame.origin.x withFloat:frame.origin.y];
    return loc;
  }
  else {
    return [[RAREUIPoint alloc] initWithFloat:frame.origin.x withFloat:frame.origin.y];
  }
}

- (RAREUIPoint *)getLocationOnScreenWithRAREUIPoint:(RAREUIPoint *)loc {
  UIView* view=(UIView*)proxy_;
  CGPoint p=[view getLocationOnScreenEx];
  if(loc==nil) {
    loc=[[RAREUIPoint alloc] initWithFloat:p.x withFloat:p.y];
  }
  else {
    [loc setLocationWithFloat:p.x withFloat:p.y];
  }
  return loc;
}

- (RAREUIPoint *)getLocationOnScreen2DWithRAREUIPoint:(RAREUIPoint *)loc {
  UIView* view=(UIView*)proxy_;
  CGPoint p=[view getLocationOnScreenEx];
  if(loc==nil) {
    loc=[[RAREUIPoint alloc] initWithFloat:p.x withFloat:p.y];
  }
  else {
    [loc setLocationWithFloat:p.x withFloat:p.y];
  }
  return loc;
}

- (RAREView *)getParent {
  return (RAREView *) check_class_cast(parentView_, [RAREView class]);
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  CGSize s= [((UIView*)proxy_) getPreferredSize: maxWidth];
  size->width_=s.width< 0 ? 0 : ceilf(s.width);
  size->height_=s.height<0 ? 0 : ceilf(s.height);
}

- (void)getSizeWithRAREUIDimension:(RAREUIDimension *)size {
  CGRect frame= [((UIView*)proxy_) frame];
  size->width_=ceilf(frame.size.width);
  size->height_=ceilf(frame.size.height);
}

- (float)getWidth {
  return [((UIView*)proxy_) bounds].size.width;
}

- (RAREWindow *)getWindow {
  RAREAPWindow* window=(RAREAPWindow*)((UIView*)proxy_).window;
  return window.sparWindow;
}

- (float)getX {
  return [((UIView*)proxy_) frame].origin.x;
}

- (float)getY {
  return [((UIView*)proxy_) frame].origin.y;
}

- (BOOL)hasBeenFocused {
  return [((UIView*)proxy_) hasBeenFocused];
}

- (BOOL)hasHadInteraction {
  return [((UIView*)proxy_) hasHadInteraction];
}

- (void)setHasHadInteraction {
  [((UIView*)proxy_) setHasHadInteraction];
}

- (BOOL)isDescendantOfWithRAREView:(RAREView *)view {
  return [((UIView*)proxy_) isDescendantOfView:(UIView*)view->proxy_];
}

- (BOOL)isDisplayable {
  return [((UIView*)proxy_) window]!=nil;
}

- (BOOL)isFocusable {
  UIView* v=(UIView*)proxy_;
  if(v.hidden) {
    return NO;
  }
  return [v canBecomeFirstResponder];
}

- (BOOL)isFocused {
  return [((UIView*)proxy_) isFirstResponder];
}

- (BOOL)isFocusedOrChildOfFocused {
  UIView *v=(UIView*)proxy_;
  if([v isFirstResponder]) {
    return YES;
  }
  UIView* sv=[v superview];
  while(sv) {
    if([sv isFirstResponder]) {
      return YES;
    }
    sv=[sv superview];
  }
  return NO;
}

- (BOOL)isOpaque {
  return ![((UIView*)proxy_) isOpaque];
}

- (BOOL)isShowing {
  return ![((UIView*)proxy_) isHidden] && [self isDisplayable];
}

- (BOOL)isVisible {
  return ![((UIView*)proxy_) isHidden];
}

- (id)addInteractionGestureListener {
  RAREInteractionGestureRecognizer *r=[RAREInteractionGestureRecognizer new];
  ((UIView*)proxy_).userInteractionEnabled=YES;
  [((UIView*)proxy_) addGestureRecognizer:  r];
  return r;
}

- (void)addMouseGestureListener {
  UIView* v=(UIView*)proxy_;
  v.userInteractionEnabled=YES;
  [v addGestureRecognizer:[RAREMouseHandlerGestureRecognizer new]];
}

- (void)disposeEx {
  UIView* v=(UIView*)proxy_;
  NSArray  *a=[v gestureRecognizers];
  NSUInteger len=a.count;
  for(NSUInteger i=0;i<len;i++) {
    id r=[a objectAtIndex:i];
    if([r conformsToProtocol:@protocol(RAREGestureListenerProtocol)]) {
      [v removeGestureRecognizer:(UIGestureRecognizer *)r];
      [r sparDispose];
    }
  }
  [v sparDispose];
}

- (void)hadInteraction {
  if (component_ != nil) {
    [self removeGestureListenerWithId:interactionGestureListener_];
    interactionGestureListener_ = nil;
    if (componentPainter_ != nil) {
      [self revalidate];
    }
  }
}

- (void)handleWantsFirstInteraction {
  if ((interactionGestureListener_ == nil) && ![self hasHadInteraction]) {
    interactionGestureListener_ = [self addInteractionGestureListener];
  }
}

- (void)removeGestureListenerWithId:(id)r {
  UIView* v=(UIView*)proxy_;
  if(r) {
    [v removeGestureRecognizer:(UIGestureRecognizer *)r];
    if([r conformsToProtocol:@protocol(RAREGestureListenerProtocol)]) {
      [r sparDispose];
    }
  }
}

- (void)setEnabledExWithBoolean:(BOOL)b {
  UIView* v=(UIView*)proxy_;
  if([v isKindOfClass:[UIControl class]]) {
    [((UIControl*)v) setEnabled:b];
  }
  else {
    if (b) {
      if(enabledAlpha_ > 0) {
        v.alpha=enabledAlpha_;
      }
      v.userInteractionEnabled = enabledInteraction_ ;
    }
    else {
      enabledAlpha_=v.alpha;
      enabledInteraction_=v.userInteractionEnabled;
      v.alpha=v.alpha*0.5;
      v.userInteractionEnabled = NO ;
    }
  }
}

- (void)setFocusListenerEnabledWithBoolean:(BOOL)enabled {
  [((UIView*)proxy_) setFocusListenerEnabled: enabled];
}

- (void)setForegroundColorExWithRAREUIColor:(RAREUIColor *)fg {
}

- (void)setKeyBoardListenerEnabledWithBoolean:(BOOL)enabled {
  [((UIView*)proxy_) setKeyBoardListenerEnabled: enabled];
}

- (void)setMouseListenerEnabledWithBoolean:(BOOL)enabled {
  if(enabled) {
    ((UIView*)proxy_).userInteractionEnabled=YES;
  }
  [((UIView*)proxy_) setMouseListenerEnabled: enabled];
}

- (void)setMouseMotionListenerEnabledWithBoolean:(BOOL)enabled {
  if(enabled) {
    ((UIView*)proxy_).userInteractionEnabled=YES;
  }
  [((UIView*)proxy_) setMouseMotionListenerEnabled: enabled];
}

- (void)setViewWithRAREView:(RAREView *)v {
  [((UIView*)proxy_) setSparView: v];
}

- (void)setVisibleExWithBoolean:(BOOL)visible {
  [((UIView*)proxy_) setHidden:!visible];
}

- (id)getLayer {
  return ((UIView*)proxy_).layer;
}

- (BOOL)isPaintEnabled {
  return [((UIView*)proxy_) isPaintEnabled];
}

- (void)copyAllFieldsTo:(RAREView *)other {
  [super copyAllFieldsTo:other];
  other->interactionGestureListener_ = interactionGestureListener_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x4, NULL },
    { "bringToFront", NULL, "V", 0x101, NULL },
    { "setAlphaWithFloat:", NULL, "V", 0x101, NULL },
    { "sendToBack", NULL, "V", 0x101, NULL },
    { "setBackgroundColorExWithRAREUIColor:", NULL, "Z", 0x101, NULL },
    { "clearFocus", NULL, "V", 0x101, NULL },
    { "focusNextView", NULL, "V", 0x101, NULL },
    { "focusPreviousView", NULL, "V", 0x101, NULL },
    { "inSameWindowWithRAREView:", NULL, "Z", 0x101, NULL },
    { "paintWithRAREiPlatformGraphics:", NULL, "V", 0x101, NULL },
    { "performClick", NULL, "V", 0x101, NULL },
    { "repaint", NULL, "V", 0x101, NULL },
    { "requestFocus", NULL, "Z", 0x101, NULL },
    { "revalidate", NULL, "V", 0x101, NULL },
    { "set3DTransformWithId:", NULL, "V", 0x101, NULL },
    { "viewFromProxyWithId:", NULL, "LRAREView", 0x109, NULL },
    { "setBoundsWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "setClipMaskWithId:", NULL, "V", 0x101, NULL },
    { "setFlingGestureListenerWithRAREiGestureListener:", NULL, "V", 0x101, NULL },
    { "setLocationWithFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "setCenterWithFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "setLongPressGestureListenerWithRAREiGestureListener:", NULL, "V", 0x101, NULL },
    { "setPaintHandlerEnabledWithBoolean:", NULL, "V", 0x101, NULL },
    { "setRotateGestureListenerWithRAREiGestureListener:", NULL, "V", 0x101, NULL },
    { "setRotationWithInt:", NULL, "V", 0x101, NULL },
    { "setScaleGestureListenerWithRAREiGestureListener:", NULL, "V", 0x101, NULL },
    { "setSizeWithFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "getBoundsWithRAREUIRectangle:", NULL, "LRAREUIRectangle", 0x101, NULL },
    { "getHeight", NULL, "F", 0x101, NULL },
    { "getLocationWithRAREUIPoint:", NULL, "LRAREUIPoint", 0x101, NULL },
    { "getLocationOnScreenWithRAREUIPoint:", NULL, "LRAREUIPoint", 0x101, NULL },
    { "getLocationOnScreen2DWithRAREUIPoint:", NULL, "LRAREUIPoint", 0x101, NULL },
    { "getParent", NULL, "LRAREView", 0x1, NULL },
    { "getPreferredSizeWithRAREUIDimension:withFloat:", NULL, "V", 0x101, NULL },
    { "getSizeWithRAREUIDimension:", NULL, "V", 0x101, NULL },
    { "getWidth", NULL, "F", 0x101, NULL },
    { "getWindow", NULL, "LRAREWindow", 0x101, NULL },
    { "getX", NULL, "F", 0x101, NULL },
    { "getY", NULL, "F", 0x101, NULL },
    { "hasBeenFocused", NULL, "Z", 0x101, NULL },
    { "hasHadInteraction", NULL, "Z", 0x101, NULL },
    { "setHasHadInteraction", NULL, "V", 0x101, NULL },
    { "isDescendantOfWithRAREView:", NULL, "Z", 0x101, NULL },
    { "isDisplayable", NULL, "Z", 0x101, NULL },
    { "isFocusable", NULL, "Z", 0x101, NULL },
    { "isFocused", NULL, "Z", 0x101, NULL },
    { "isFocusedOrChildOfFocused", NULL, "Z", 0x101, NULL },
    { "isOpaque", NULL, "Z", 0x101, NULL },
    { "isShowing", NULL, "Z", 0x101, NULL },
    { "isVisible", NULL, "Z", 0x101, NULL },
    { "addInteractionGestureListener", NULL, "LNSObject", 0x104, NULL },
    { "addMouseGestureListener", NULL, "V", 0x104, NULL },
    { "disposeEx", NULL, "V", 0x104, NULL },
    { "hadInteraction", NULL, "V", 0x4, NULL },
    { "handleWantsFirstInteraction", NULL, "V", 0x4, NULL },
    { "removeGestureListenerWithId:", NULL, "V", 0x104, NULL },
    { "setEnabledExWithBoolean:", NULL, "V", 0x104, NULL },
    { "setFocusListenerEnabledWithBoolean:", NULL, "V", 0x104, NULL },
    { "setForegroundColorExWithRAREUIColor:", NULL, "V", 0x4, NULL },
    { "setKeyBoardListenerEnabledWithBoolean:", NULL, "V", 0x104, NULL },
    { "setMouseListenerEnabledWithBoolean:", NULL, "V", 0x104, NULL },
    { "setMouseMotionListenerEnabledWithBoolean:", NULL, "V", 0x104, NULL },
    { "setViewWithRAREView:", NULL, "V", 0x104, NULL },
    { "setVisibleExWithBoolean:", NULL, "V", 0x104, NULL },
    { "getLayer", NULL, "LNSObject", 0x104, NULL },
    { "isPaintEnabled", NULL, "Z", 0x104, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "interactionGestureListener_", NULL, 0x0, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREView = { "View", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 66, methods, 1, fields, 0, NULL};
  return &_RAREView;
}

@end
