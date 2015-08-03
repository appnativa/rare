//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UITarget.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iExceptionHandler.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/ui/RenderType.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UITarget.h"
#include "com/appnativa/rare/ui/effects/aTransitionAnimator.h"
#include "com/appnativa/rare/ui/effects/iTransitionAnimator.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformWindowManager.h"
#include "com/appnativa/rare/ui/iTargetContainer.h"
#include "com/appnativa/rare/ui/iWindowManager.h"
#include "com/appnativa/rare/ui/painter/UIComponentPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/viewer/iTarget.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "java/lang/Exception.h"
#include "java/lang/Throwable.h"

@implementation RAREUITarget

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name {
  if (self = [self initRAREUITargetWithRAREiPlatformAppContext:app withNSString:name withRAREiParentComponent:[RAREaPlatformHelper createTargetContainerWithRAREiPlatformAppContext:app] withBoolean:YES withRAREiTarget_iListener:nil]) {
    [((id<RAREiParentComponent>) nil_chk(targetContainer_)) setOpaqueWithBoolean:NO];
  }
  return self;
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
            withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener {
  if (self = [self initRAREUITargetWithRAREiPlatformAppContext:app withNSString:name withRAREiParentComponent:[RAREaPlatformHelper createTargetContainerWithRAREiPlatformAppContext:app] withBoolean:YES withRAREiTarget_iListener:listener]) {
    [((id<RAREiParentComponent>) nil_chk(targetContainer_)) setOpaqueWithBoolean:NO];
  }
  return self;
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
             withRAREiParentComponent:(id<RAREiParentComponent>)container {
  return [self initRAREUITargetWithRAREiPlatformAppContext:app withNSString:name withRAREiParentComponent:container withBoolean:YES];
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
                          withBoolean:(BOOL)register_
            withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener {
  if (self = [self initRAREUITargetWithRAREiPlatformAppContext:app withNSString:name withRAREiParentComponent:[RAREaPlatformHelper createTargetContainerWithRAREiPlatformAppContext:app] withBoolean:register_ withRAREiTarget_iListener:listener]) {
    [((id<RAREiParentComponent>) nil_chk(targetContainer_)) setOpaqueWithBoolean:NO];
  }
  return self;
}

- (id)initRAREUITargetWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                     withNSString:(NSString *)name
                         withRAREiParentComponent:(id<RAREiParentComponent>)container
                                      withBoolean:(BOOL)register_ {
  return [self initRAREUITargetWithRAREiPlatformAppContext:app withNSString:name withRAREiParentComponent:container withBoolean:register_ withRAREiTarget_iListener:nil];
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
             withRAREiParentComponent:(id<RAREiParentComponent>)container
                          withBoolean:(BOOL)register_ {
  return [self initRAREUITargetWithRAREiPlatformAppContext:app withNSString:name withRAREiParentComponent:container withBoolean:register_];
}

- (id)initRAREUITargetWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                     withNSString:(NSString *)name
                         withRAREiParentComponent:(id<RAREiParentComponent>)container
                                      withBoolean:(BOOL)register_
                        withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener {
  if (self = [super init]) {
    self->appContext_ = app;
    self->targetName_ = name;
    self->targetContainer_ = container;
    self->listener_ = listener;
    if (register_) {
      [((id<RAREiPlatformWindowManager>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk(app)) getWindowManager])) registerTargetWithNSString:targetName_ withRAREiTarget:self];
    }
    [((id<RAREiParentComponent>) nil_chk(targetContainer_)) putClientPropertyWithNSString:[RAREiConstants RARE_TARGET_COMPONENT_PROPERTY] withId:self];
  }
  return self;
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
             withRAREiParentComponent:(id<RAREiParentComponent>)container
                          withBoolean:(BOOL)register_
            withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener {
  return [self initRAREUITargetWithRAREiPlatformAppContext:app withNSString:name withRAREiParentComponent:container withBoolean:register_ withRAREiTarget_iListener:listener];
}

- (void)activate {
  [((id<RAREiParentComponent>) nil_chk(targetContainer_)) setVisibleWithBoolean:YES];
  if (theViewer_ != nil) {
    [theViewer_ requestFocus];
  }
  else {
    [targetContainer_ requestFocus];
  }
}

- (void)disposeWithBoolean:(BOOL)disposeviewer {
  if (targetContainer_ != nil) {
    BOOL disposed = [targetContainer_ isDisposed];
    if (!disposed) {
      [targetContainer_ setVisibleWithBoolean:NO];
    }
    id<RAREiWindowManager> wm = [((id<RAREiPlatformAppContext>) nil_chk(appContext_)) getWindowManager];
    if (wm == nil) {
      return;
    }
    [((id<RAREiWindowManager>) nil_chk(wm)) removeTargetWithNSString:targetName_];
    @try {
      id<RAREiViewer> v;
      if (disposed) {
        v = theViewer_;
        if (v != nil) {
          [v targetLostWithRAREiTarget:self];
        }
      }
      else {
        v = [self removeViewer];
      }
      if (v != nil) {
        if (disposeviewer) {
          [v dispose];
        }
      }
    }
    @catch (JavaLangThrowable *e) {
      [((id<RAREiExceptionHandler>) nil_chk([appContext_ getDefaultExceptionHandler])) ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
    }
    @finally {
      targetContainer_ = nil;
    }
  }
  if ((transitionAnimator_ != nil) && [transitionAnimator_ isAutoDispose]) {
    [transitionAnimator_ dispose];
  }
  targetContainer_ = nil;
  theViewer_ = nil;
  appContext_ = nil;
  transitionAnimator_ = nil;
  animator_ = nil;
}

- (void)reloadViewer {
  id<RAREiViewer> v = theViewer_;
  if (v != nil) {
    [v reloadWithBoolean:NO];
  }
}

- (id<RAREiViewer>)removeViewer {
  if (theViewer_ != nil) {
    [self removeComponentExWithRAREiPlatformComponent:[theViewer_ getContainerComponent]];
    @try {
      [theViewer_ targetLostWithRAREiTarget:self];
    }
    @catch (JavaLangException *ignore) {
    }
    [self setComponentWithRAREiPlatformComponent:nil];
    id<RAREiViewer> v = theViewer_;
    theViewer_ = nil;
    return v;
  }
  return nil;
}

- (void)repaint {
  [((id<RAREiParentComponent>) nil_chk(targetContainer_)) repaint];
}

- (void)update {
  [((id<RAREiParentComponent>) nil_chk(targetContainer_)) revalidate];
}

- (void)setBackgroundPainterWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)painter {
  [RAREaUIComponentPainter setBackgroundPainterWithRAREiPainterSupport:targetContainer_ withRAREiBackgroundPainter:painter];
}

- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)painter {
  [((id<RAREiParentComponent>) nil_chk(targetContainer_)) setComponentPainterWithRAREiPlatformComponentPainter:painter];
}

- (void)setIgnoreViewerRenderTypeWithBoolean:(BOOL)ignoreViewerRenderType {
  self->ignoreViewerRenderType_ = ignoreViewerRenderType;
}

- (void)setLinkedDataWithId:(id)data {
  linkedData_ = data;
}

- (void)setLockedWithBoolean:(BOOL)lock {
  [((id<RAREiParentComponent>) nil_chk(targetContainer_)) setLockedWithBoolean:lock];
}

- (void)setLockedColorWithRAREUIColor:(RAREUIColor *)color {
  [((id<RAREiParentComponent>) nil_chk(targetContainer_)) setDisabledColorWithRAREUIColor:color];
}

- (void)setManualUpdateWithBoolean:(BOOL)manual {
  self->manualUpdate_ = manual;
}

- (void)setNameWithNSString:(NSString *)name {
  targetName_ = name;
}

- (void)setPopupWindowWithBoolean:(BOOL)popupWindow {
  self->popupWindow_ = popupWindow;
}

- (BOOL)setRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)renderType {
  if ([(id) targetContainer_ conformsToProtocol: @protocol(RAREiTargetContainer)]) {
    [((id<RAREiTargetContainer>) check_protocol_cast(targetContainer_, @protocol(RAREiTargetContainer))) setRenderTypeWithRARERenderTypeEnum:renderType];
    return YES;
  }
  return NO;
}

- (void)setTransitionAnimatorWithRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)animator {
  self->transitionAnimator_ = animator;
}

- (id<RAREiViewer>)setViewerWithRAREiViewer:(id<RAREiViewer>)viewer {
  if ((viewer == nil) && (theViewer_ == nil)) {
    return nil;
  }
  if (transitionAnimator_ != nil) {
    id<RAREiViewer> v = theViewer_;
    [self setViewerWithRAREiViewer:viewer withRAREiTransitionAnimator:transitionAnimator_ withRAREiFunctionCallback:nil];
    return v;
  }
  if ((viewer != nil) && (viewer == theViewer_) && [self isChildOfTargetContainerWithRAREiPlatformComponent:[viewer getContainerComponent]]) {
    if (![self isManualUpdate]) {
      [self update];
    }
    return nil;
  }
  if ((viewer != nil) && ([viewer getTarget] != nil)) {
    (void) [((id<RAREiTarget>) nil_chk([viewer getTarget])) removeViewer];
  }
  return [self setViewerExWithRAREiViewer:viewer];
}

- (void)setViewerWithRAREiViewer:(id<RAREiViewer>)viewer
     withRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)animator
       withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb {
  if ((viewer == nil) && (theViewer_ == nil)) {
    return;
  }
  if ((animator != nil) && [animator isRunning]) {
    [((id<RAREiParentComponent>) nil_chk(targetContainer_)) removeAll];
    [animator cancel];
  }
  if ((viewer != nil) && (viewer == theViewer_) && [self isChildOfTargetContainerWithRAREiPlatformComponent:[viewer getContainerComponent]]) {
    if (![self isManualUpdate]) {
      [self update];
    }
    if (cb != nil) {
      [cb finishedWithBoolean:NO withId:nil];
    }
    return;
  }
  if ((viewer != nil) && ([viewer getTarget] != nil)) {
    (void) [((id<RAREiTarget>) nil_chk([viewer getTarget])) removeViewer];
  }
  id<RAREiPlatformComponent> outc = nil;
  if (animator != nil) {
    outc = [RAREaTransitionAnimator resolveTransitionComponentWithRAREiParentComponent:targetContainer_ withRAREiPlatformComponent:(theViewer_ == nil) ? nil : [theViewer_ getContainerComponent]];
  }
  RAREUIRectangle *r = (outc == nil) ? nil : [outc getBounds];
  if ((outc != nil) && ((r->width_ > 0) && (r->height_ > 0))) {
    [((id<RAREiTransitionAnimator>) nil_chk(animator)) setOutgoingComponentWithRAREiPlatformComponent:outc];
  }
  else {
    outc = nil;
  }
  id<RAREiViewer> v = [self setViewerExWithRAREiViewer:viewer];
  if (outc != nil) {
    id<RAREiFunctionCallback> mycb = nil;
    if (cb != nil) {
      mycb = [[RAREUITarget_$1 alloc] initWithRAREiFunctionCallback:cb withRAREiViewer:v];
    }
    id<RAREiPlatformComponent> inc = (viewer == nil) ? nil : [viewer getContainerComponent];
    if (inc == nil) {
      inc = [RAREaTransitionAnimator resolveTransitionComponentWithRAREiParentComponent:targetContainer_ withRAREiPlatformComponent:nil];
    }
    [((id<RAREiTransitionAnimator>) nil_chk(animator)) setIncommingComponentWithRAREiPlatformComponent:inc];
    [animator animateWithRAREiParentComponent:targetContainer_ withRAREUIRectangle:r withRAREiFunctionCallback:mycb];
  }
  else {
    if (cb != nil) {
      [cb finishedWithBoolean:NO withId:nil];
    }
  }
}

- (void)setVisibleWithBoolean:(BOOL)visible {
  [((id<RAREiParentComponent>) nil_chk(targetContainer_)) setVisibleWithBoolean:visible];
}

- (id<RAREiParentComponent>)getContainerComponent {
  return targetContainer_;
}

- (id)getLinkedData {
  return linkedData_;
}

- (NSString *)getName {
  return targetName_;
}

- (RARERenderTypeEnum *)getRenderType {
  return ([(id) targetContainer_ conformsToProtocol: @protocol(RAREiTargetContainer)]) ? [((id<RAREiTargetContainer>) check_protocol_cast(targetContainer_, @protocol(RAREiTargetContainer))) getRenderType] : nil;
}

- (RAREUIDimension *)getTargetSize {
  return [((id<RAREiParentComponent>) nil_chk(targetContainer_)) getSize];
}

- (id<RAREiTransitionAnimator>)getTransitionAnimator {
  return transitionAnimator_;
}

- (id<RAREiViewer>)getViewer {
  return theViewer_;
}

- (BOOL)isDisposed {
  return targetContainer_ == nil;
}

- (BOOL)isHeadless {
  return NO;
}

- (BOOL)isIgnoreViewerRenderType {
  return ignoreViewerRenderType_;
}

- (BOOL)isLocked {
  return [((id<RAREiParentComponent>) nil_chk(targetContainer_)) isLocked];
}

- (BOOL)isManualUpdate {
  return manualUpdate_;
}

- (BOOL)isPopupWindow {
  return popupWindow_;
}

- (BOOL)isVisible {
  return [((id<RAREiParentComponent>) nil_chk(targetContainer_)) isVisible];
}

- (void)removeComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  if (comp != nil) {
    [((id<RAREiParentComponent>) nil_chk(targetContainer_)) removeWithRAREiPlatformComponent:comp];
  }
}

- (void)removeViewerEx {
  id<RAREiViewer> v = theViewer_;
  theViewer_ = nil;
  if (v != nil) {
    [v targetLostWithRAREiTarget:self];
    [self removeComponentExWithRAREiPlatformComponent:[v getContainerComponent]];
    if (listener_ != nil) {
      [listener_ viewerRemovedWithRAREiViewer:theViewer_];
    }
  }
}

- (void)setComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  [self setComponentExWithRAREiPlatformComponent:comp];
  if ((comp != nil) && ![self isManualUpdate]) {
    [self update];
  }
}

- (void)setComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  if (comp != nil) {
    [((id<RAREiParentComponent>) nil_chk(targetContainer_)) addWithRAREiPlatformComponent:comp];
  }
}

- (id<RAREiViewer>)setViewerExWithRAREiViewer:(id<RAREiViewer>)viewer {
  id<RAREiViewer> v = theViewer_;
  [self removeViewerEx];
  if (viewer != nil) {
    if (!ignoreViewerRenderType_) {
      [RAREaPlatformHelper setTargetRenderTypeWithRAREiTarget:self withRARERenderTypeEnum:[viewer getRenderType]];
    }
    theViewer_ = viewer;
    [self setComponentExWithRAREiPlatformComponent:[theViewer_ getContainerComponent]];
    [viewer pageHome];
    [viewer targetAcquiredWithRAREiTarget:self];
    if (![self isManualUpdate]) {
      [self update];
    }
    if (listener_ != nil) {
      [listener_ viewerSetWithRAREiViewer:theViewer_];
    }
  }
  else {
    [self setComponentWithRAREiPlatformComponent:nil];
  }
  return v;
}

- (BOOL)isChildOfTargetContainerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  return [((id<RAREiPlatformComponent>) nil_chk(c)) getParent] == targetContainer_;
}

- (void)copyAllFieldsTo:(RAREUITarget *)other {
  [super copyAllFieldsTo:other];
  other->animator_ = animator_;
  other->appContext_ = appContext_;
  other->ignoreViewerRenderType_ = ignoreViewerRenderType_;
  other->linkedData_ = linkedData_;
  other->listener_ = listener_;
  other->manualUpdate_ = manualUpdate_;
  other->partialOutAnimation_ = partialOutAnimation_;
  other->popupWindow_ = popupWindow_;
  other->targetContainer_ = targetContainer_;
  other->targetName_ = targetName_;
  other->theViewer_ = theViewer_;
  other->transitionAnimator_ = transitionAnimator_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "removeViewer", NULL, "LRAREiViewer", 0x1, NULL },
    { "setRenderTypeWithRARERenderTypeEnum:", NULL, "Z", 0x1, NULL },
    { "setViewerWithRAREiViewer:", NULL, "LRAREiViewer", 0x1, NULL },
    { "getContainerComponent", NULL, "LRAREiParentComponent", 0x1, NULL },
    { "getLinkedData", NULL, "LNSObject", 0x1, NULL },
    { "getName", NULL, "LNSString", 0x1, NULL },
    { "getRenderType", NULL, "LRARERenderTypeEnum", 0x1, NULL },
    { "getTargetSize", NULL, "LRAREUIDimension", 0x1, NULL },
    { "getTransitionAnimator", NULL, "LRAREiTransitionAnimator", 0x1, NULL },
    { "getViewer", NULL, "LRAREiViewer", 0x1, NULL },
    { "isDisposed", NULL, "Z", 0x1, NULL },
    { "isHeadless", NULL, "Z", 0x1, NULL },
    { "isIgnoreViewerRenderType", NULL, "Z", 0x1, NULL },
    { "isLocked", NULL, "Z", 0x1, NULL },
    { "isManualUpdate", NULL, "Z", 0x1, NULL },
    { "isPopupWindow", NULL, "Z", 0x1, NULL },
    { "isVisible", NULL, "Z", 0x1, NULL },
    { "removeComponentExWithRAREiPlatformComponent:", NULL, "V", 0x4, NULL },
    { "removeViewerEx", NULL, "V", 0x4, NULL },
    { "setComponentWithRAREiPlatformComponent:", NULL, "V", 0x4, NULL },
    { "setComponentExWithRAREiPlatformComponent:", NULL, "V", 0x4, NULL },
    { "setViewerExWithRAREiViewer:", NULL, "LRAREiViewer", 0x4, NULL },
    { "isChildOfTargetContainerWithRAREiPlatformComponent:", NULL, "Z", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "partialOutAnimation_", NULL, 0x0, "Z" },
    { "animator_", NULL, 0x4, "LRAREiTransitionAnimator" },
    { "appContext_", NULL, 0x4, "LRAREiPlatformAppContext" },
    { "ignoreViewerRenderType_", NULL, 0x4, "Z" },
    { "linkedData_", NULL, 0x4, "LNSObject" },
    { "listener_", NULL, 0x4, "LRAREiTarget_iListener" },
    { "manualUpdate_", NULL, 0x4, "Z" },
    { "popupWindow_", NULL, 0x4, "Z" },
    { "targetContainer_", NULL, 0x4, "LRAREiParentComponent" },
    { "targetName_", NULL, 0x4, "LNSString" },
    { "theViewer_", NULL, 0x4, "LRAREiViewer" },
    { "transitionAnimator_", NULL, 0x4, "LRAREiTransitionAnimator" },
  };
  static J2ObjcClassInfo _RAREUITarget = { "UITarget", "com.appnativa.rare.ui", NULL, 0x1, 23, methods, 12, fields, 0, NULL};
  return &_RAREUITarget;
}

@end
@implementation RAREUITarget_DelegatingTarget

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
             withRAREiParentComponent:(id<RAREiParentComponent>)container
                          withBoolean:(BOOL)register_ {
  return [self initRAREUITarget_DelegatingTargetWithRAREiPlatformAppContext:app withNSString:name withRAREiParentComponent:container withBoolean:register_ withRAREiTarget_iListener:nil];
}

- (id)initRAREUITarget_DelegatingTargetWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                                      withNSString:(NSString *)name
                                          withRAREiParentComponent:(id<RAREiParentComponent>)container
                                                       withBoolean:(BOOL)register_
                                         withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener {
  if (self = [super initWithRAREiPlatformAppContext:app withNSString:name withRAREiParentComponent:[RAREaPlatformHelper createTargetContainerWithRAREiPlatformAppContext:app] withBoolean:register_ withRAREiTarget_iListener:listener]) {
    [((id<RAREiParentComponent>) nil_chk(targetContainer_)) addWithRAREiPlatformComponent:container];
    delegateComponent_ = container;
  }
  return self;
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
             withRAREiParentComponent:(id<RAREiParentComponent>)container
                          withBoolean:(BOOL)register_
            withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener {
  return [self initRAREUITarget_DelegatingTargetWithRAREiPlatformAppContext:app withNSString:name withRAREiParentComponent:container withBoolean:register_ withRAREiTarget_iListener:listener];
}

- (void)removeComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  if (comp != nil) {
    [((id<RAREiParentComponent>) nil_chk(delegateComponent_)) removeWithRAREiPlatformComponent:comp];
  }
}

- (void)setComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  if (comp != nil) {
    [((id<RAREiParentComponent>) nil_chk(delegateComponent_)) addWithRAREiPlatformComponent:comp];
  }
}

- (void)copyAllFieldsTo:(RAREUITarget_DelegatingTarget *)other {
  [super copyAllFieldsTo:other];
  other->delegateComponent_ = delegateComponent_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "removeComponentExWithRAREiPlatformComponent:", NULL, "V", 0x4, NULL },
    { "setComponentExWithRAREiPlatformComponent:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "delegateComponent_", NULL, 0x0, "LRAREiParentComponent" },
  };
  static J2ObjcClassInfo _RAREUITarget_DelegatingTarget = { "DelegatingTarget", "com.appnativa.rare.ui", "UITarget", 0x9, 2, methods, 1, fields, 0, NULL};
  return &_RAREUITarget_DelegatingTarget;
}

@end
@implementation RAREUITarget_$1

- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue {
  [((id<RAREiFunctionCallback>) nil_chk(val$cb_)) finishedWithBoolean:NO withId:val$v_];
}

- (id)initWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$0
                    withRAREiViewer:(id<RAREiViewer>)capture$1 {
  val$cb_ = capture$0;
  val$v_ = capture$1;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "val$cb_", NULL, 0x1012, "LRAREiFunctionCallback" },
    { "val$v_", NULL, 0x1012, "LRAREiViewer" },
  };
  static J2ObjcClassInfo _RAREUITarget_$1 = { "$1", "com.appnativa.rare.ui", "UITarget", 0x8000, 0, NULL, 2, fields, 0, NULL};
  return &_RAREUITarget_$1;
}

@end