//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/HeadlessTarget.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/iExceptionHandler.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/ui/HeadlessTarget.h"
#include "com/appnativa/rare/ui/RenderType.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/effects/iTransitionAnimator.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformWindowManager.h"
#include "com/appnativa/rare/ui/iWindowManager.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/viewer/iTarget.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "java/lang/Throwable.h"

@implementation RAREHeadlessTarget

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)appContext
                         withNSString:(NSString *)targetName
            withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener {
  if (self = [super init]) {
    self->appContext_ = appContext;
    self->targetName_ = targetName;
    self->listener_ = listener;
  }
  return self;
}

- (void)activate {
  if (theViewer_ != nil) {
    [theViewer_ requestFocus];
  }
}

- (void)disposeWithBoolean:(BOOL)disposeviewer {
  if (!disposed_) {
    disposed_ = YES;
    id<RAREiWindowManager> wm = [((id<RAREiPlatformAppContext>) nil_chk(appContext_)) getWindowManager];
    if (wm == nil) {
      return;
    }
    [((id<RAREiWindowManager>) nil_chk(wm)) removeTargetWithNSString:targetName_];
    @try {
      id<RAREiViewer> v = [self removeViewer];
      if (v != nil) {
        if (disposeviewer || [v isAutoDispose]) {
          [v dispose];
        }
      }
    }
    @catch (JavaLangThrowable *e) {
      [((id<RAREiExceptionHandler>) nil_chk([appContext_ getDefaultExceptionHandler])) ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
    }
    @finally {
      appContext_ = nil;
      linkedData_ = nil;
      listener_ = nil;
    }
  }
}

- (void)reloadViewer {
  if (theViewer_ != nil) {
    [theViewer_ reloadWithBoolean:NO];
  }
}

- (id<RAREiViewer>)removeViewer {
  if (theViewer_ != nil) {
    [theViewer_ targetLostWithRAREiTarget:self];
    id<RAREiViewer> v = theViewer_;
    if (listener_ != nil) {
      [listener_ viewerRemovedWithRAREiViewer:v];
    }
    theViewer_ = nil;
    return v;
  }
  return nil;
}

- (void)repaint {
  if (theViewer_ != nil) {
    [theViewer_ repaint];
  }
}

- (void)update {
  if (theViewer_ != nil) {
    [theViewer_ update];
  }
}

- (void)setBackgroundPainterWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)painter {
}

- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)painter {
}

- (void)setLinkedDataWithId:(id)data {
  linkedData_ = data;
}

- (void)setLockedWithBoolean:(BOOL)lock {
  self->locked_ = lock;
  if (theViewer_ != nil) {
    [theViewer_ setEnabledWithBoolean:!lock];
  }
}

- (void)setNameWithNSString:(NSString *)name {
  targetName_ = name;
}

- (id<RAREiViewer>)setViewerWithRAREiViewer:(id<RAREiViewer>)viewer {
  id<RAREiViewer> v = [self removeViewer];
  if (viewer != nil) {
    if ([viewer getTarget] != nil) {
      (void) [((id<RAREiTarget>) nil_chk([viewer getTarget])) removeViewer];
    }
    theViewer_ = viewer;
    if (listener_ != nil) {
      [listener_ viewerSetWithRAREiViewer:v];
    }
    [viewer pageHome];
    [viewer targetAcquiredWithRAREiTarget:self];
    if (locked_) {
      [viewer setEnabledWithBoolean:NO];
    }
  }
  return v;
}

- (void)setVisibleWithBoolean:(BOOL)visible {
  if (theViewer_ != nil) {
    [theViewer_ setVisibleWithBoolean:visible];
  }
}

- (id<RAREiParentComponent>)getContainerComponent {
  return (id<RAREiParentComponent>) check_protocol_cast(((theViewer_ == nil) ? nil : [theViewer_ getContainerComponent]), @protocol(RAREiParentComponent));
}

- (id)getLinkedData {
  return linkedData_;
}

- (NSString *)getName {
  return targetName_;
}

- (RAREUIDimension *)getTargetSize {
  return (theViewer_ == nil) ? [[RAREUIDimension alloc] initWithFloat:0 withFloat:0] : [((id<RAREiPlatformComponent>) nil_chk([theViewer_ getContainerComponent])) getSize];
}

- (id<RAREiViewer>)getViewer {
  return theViewer_;
}

- (BOOL)isHeadless {
  return YES;
}

- (BOOL)isLocked {
  return locked_;
}

- (BOOL)isPopupWindow {
  return NO;
}

- (BOOL)isVisible {
  return (theViewer_ == nil) ? NO : [theViewer_ isVisible];
}

- (void)setTransitionAnimatorWithRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)animator {
}

- (id<RAREiTransitionAnimator>)getTransitionAnimator {
  return nil;
}

- (BOOL)setRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)renderType {
  return NO;
}

- (RARERenderTypeEnum *)getRenderType {
  return nil;
}

- (void)copyAllFieldsTo:(RAREHeadlessTarget *)other {
  [super copyAllFieldsTo:other];
  other->appContext_ = appContext_;
  other->disposed_ = disposed_;
  other->linkedData_ = linkedData_;
  other->listener_ = listener_;
  other->locked_ = locked_;
  other->targetName_ = targetName_;
  other->theViewer_ = theViewer_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "removeViewer", NULL, "LRAREiViewer", 0x1, NULL },
    { "setViewerWithRAREiViewer:", NULL, "LRAREiViewer", 0x1, NULL },
    { "getContainerComponent", NULL, "LRAREiParentComponent", 0x1, NULL },
    { "getLinkedData", NULL, "LNSObject", 0x1, NULL },
    { "getName", NULL, "LNSString", 0x1, NULL },
    { "getTargetSize", NULL, "LRAREUIDimension", 0x1, NULL },
    { "getViewer", NULL, "LRAREiViewer", 0x1, NULL },
    { "isHeadless", NULL, "Z", 0x1, NULL },
    { "isLocked", NULL, "Z", 0x1, NULL },
    { "isPopupWindow", NULL, "Z", 0x1, NULL },
    { "isVisible", NULL, "Z", 0x1, NULL },
    { "getTransitionAnimator", NULL, "LRAREiTransitionAnimator", 0x1, NULL },
    { "setRenderTypeWithRARERenderTypeEnum:", NULL, "Z", 0x1, NULL },
    { "getRenderType", NULL, "LRARERenderTypeEnum", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "appContext_", NULL, 0x4, "LRAREiPlatformAppContext" },
    { "linkedData_", NULL, 0x4, "LNSObject" },
    { "listener_", NULL, 0x4, "LRAREiTarget_iListener" },
    { "targetName_", NULL, 0x4, "LNSString" },
    { "theViewer_", NULL, 0x4, "LRAREiViewer" },
    { "locked_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREHeadlessTarget = { "HeadlessTarget", "com.appnativa.rare.ui", NULL, 0x1, 14, methods, 6, fields, 0, NULL};
  return &_RAREHeadlessTarget;
}

@end