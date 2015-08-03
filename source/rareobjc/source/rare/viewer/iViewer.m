//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/iViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/ui/RenderType.h"
#include "com/appnativa/rare/ui/effects/iAnimator.h"
#include "com/appnativa/rare/ui/event/iExpandedListener.h"
#include "com/appnativa/rare/ui/iCollapsible.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPopup.h"
#include "com/appnativa/rare/ui/print/iPageSetup.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/viewer/iTarget.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "java/io/Writer.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/net/URL.h"
#include "java/util/List.h"
#include "java/util/Map.h"


@interface RAREiViewer : NSObject
@end

@implementation RAREiViewer

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setLoadAnimatorWithRAREiAnimator:", NULL, "V", 0x401, NULL },
    { "canPrint", NULL, "Z", 0x401, NULL },
    { "canSave", NULL, "Z", 0x401, NULL },
    { "configureWithRARESPOTViewer:", NULL, "V", 0x401, NULL },
    { "createPageSetup", NULL, "LRAREiPageSetup", 0x401, NULL },
    { "downArrow", NULL, "V", 0x401, NULL },
    { "leftArrow", NULL, "V", 0x401, NULL },
    { "onConfigurationChangedWithBoolean:", NULL, "V", 0x401, NULL },
    { "onConfigurationWillChangeWithId:", NULL, "V", 0x401, NULL },
    { "pageDown", NULL, "V", 0x401, NULL },
    { "pageEnd", NULL, "V", 0x401, NULL },
    { "pageHome", NULL, "V", 0x401, NULL },
    { "pageEndHorizontal", NULL, "V", 0x401, NULL },
    { "pageHomeHorizontal", NULL, "V", 0x401, NULL },
    { "pageLeft", NULL, "V", 0x401, NULL },
    { "pageRight", NULL, "V", 0x401, NULL },
    { "pageSetupWithRAREiPageSetup:", NULL, "V", 0x401, NULL },
    { "pageUp", NULL, "V", 0x401, NULL },
    { "register__", NULL, "V", 0x401, NULL },
    { "registerNamedItemWithNSString:withId:", NULL, "LNSObject", 0x401, NULL },
    { "rightArrow", NULL, "V", 0x401, NULL },
    { "saveWithJavaIoWriter:", NULL, "V", 0x401, NULL },
    { "showAsDialogWithJavaUtilMap:", NULL, "LRAREWindowViewer", 0x401, NULL },
    { "showAsDialogWithNSString:withBoolean:", NULL, "LRAREWindowViewer", 0x401, NULL },
    { "showAsPopupWithRAREiPlatformComponent:withJavaUtilMap:", NULL, "LRAREiPopup", 0x401, NULL },
    { "showAsWindowWithJavaUtilMap:", NULL, "LRAREWindowViewer", 0x401, NULL },
    { "targetAcquiredWithRAREiTarget:", NULL, "V", 0x401, NULL },
    { "targetLostWithRAREiTarget:", NULL, "V", 0x401, NULL },
    { "unregisterWithBoolean:", NULL, "V", 0x401, NULL },
    { "unregisterNamedItemWithNSString:", NULL, "LNSObject", 0x401, NULL },
    { "upArrow", NULL, "V", 0x401, NULL },
    { "setAppContextWithRAREiPlatformAppContext:", NULL, "V", 0x401, NULL },
    { "setAutoDisposeWithBoolean:", NULL, "V", 0x401, NULL },
    { "setAutoUnregisterWithBoolean:", NULL, "V", 0x401, NULL },
    { "setCollapsedTitleWithNSString:", NULL, "V", 0x401, NULL },
    { "setContextURLWithJavaNetURL:", NULL, "V", 0x401, NULL },
    { "setDisposableWithBoolean:", NULL, "V", 0x401, NULL },
    { "setViewerActionLinkWithRAREActionLink:", NULL, "V", 0x401, NULL },
    { "getBaseURL", NULL, "LJavaNetURL", 0x401, NULL },
    { "getCollapsedTitle", NULL, "LNSString", 0x401, NULL },
    { "getCollapsiblePane", NULL, "LRAREiCollapsible", 0x401, NULL },
    { "getContextURL", NULL, "LJavaNetURL", 0x401, NULL },
    { "getExpandedListener", NULL, "LRAREiExpandedListener", 0x401, NULL },
    { "getNamedItemWithNSString:", NULL, "LNSObject", 0x401, NULL },
    { "getNames", NULL, "LJavaUtilList", 0x401, NULL },
    { "getPrintComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getSourceURL", NULL, "LJavaNetURL", 0x401, NULL },
    { "getTarget", NULL, "LRAREiTarget", 0x401, NULL },
    { "getViewerActionLink", NULL, "LRAREActionLink", 0x401, NULL },
    { "getViewerURL", NULL, "LJavaNetURL", 0x401, NULL },
    { "isAutoDispose", NULL, "Z", 0x401, NULL },
    { "isAutoUnregister", NULL, "Z", 0x401, NULL },
    { "isBackPressedHandled", NULL, "Z", 0x401, NULL },
    { "isDisposable", NULL, "Z", 0x401, NULL },
    { "isDisposed", NULL, "Z", 0x401, NULL },
    { "isExternalViewer", NULL, "Z", 0x401, NULL },
    { "isRegistered", NULL, "Z", 0x401, NULL },
    { "isTabPaneViewer", NULL, "Z", 0x401, NULL },
    { "isWindowOnlyViewer", NULL, "Z", 0x401, NULL },
    { "getRenderType", NULL, "LRARERenderTypeEnum", 0x401, NULL },
    { "setRenderTypeWithRARERenderTypeEnum:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiViewer = { "iViewer", "com.appnativa.rare.viewer", NULL, 0x201, 61, methods, 0, NULL, 0, NULL};
  return &_RAREiViewer;
}

@end

static RAREiViewer_DisableBehaviorEnum *RAREiViewer_DisableBehaviorEnum_DISABLE_WIDGETS;
static RAREiViewer_DisableBehaviorEnum *RAREiViewer_DisableBehaviorEnum_DISABLE_CONTAINER;
static RAREiViewer_DisableBehaviorEnum *RAREiViewer_DisableBehaviorEnum_DISABLE_BOTH;
IOSObjectArray *RAREiViewer_DisableBehaviorEnum_values;

@implementation RAREiViewer_DisableBehaviorEnum

+ (RAREiViewer_DisableBehaviorEnum *)DISABLE_WIDGETS {
  return RAREiViewer_DisableBehaviorEnum_DISABLE_WIDGETS;
}
+ (RAREiViewer_DisableBehaviorEnum *)DISABLE_CONTAINER {
  return RAREiViewer_DisableBehaviorEnum_DISABLE_CONTAINER;
}
+ (RAREiViewer_DisableBehaviorEnum *)DISABLE_BOTH {
  return RAREiViewer_DisableBehaviorEnum_DISABLE_BOTH;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiViewer_DisableBehaviorEnum class]) {
    RAREiViewer_DisableBehaviorEnum_DISABLE_WIDGETS = [[RAREiViewer_DisableBehaviorEnum alloc] initWithNSString:@"DISABLE_WIDGETS" withInt:0];
    RAREiViewer_DisableBehaviorEnum_DISABLE_CONTAINER = [[RAREiViewer_DisableBehaviorEnum alloc] initWithNSString:@"DISABLE_CONTAINER" withInt:1];
    RAREiViewer_DisableBehaviorEnum_DISABLE_BOTH = [[RAREiViewer_DisableBehaviorEnum alloc] initWithNSString:@"DISABLE_BOTH" withInt:2];
    RAREiViewer_DisableBehaviorEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiViewer_DisableBehaviorEnum_DISABLE_WIDGETS, RAREiViewer_DisableBehaviorEnum_DISABLE_CONTAINER, RAREiViewer_DisableBehaviorEnum_DISABLE_BOTH, nil } count:3 type:[IOSClass classWithClass:[RAREiViewer_DisableBehaviorEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiViewer_DisableBehaviorEnum_values];
}

+ (RAREiViewer_DisableBehaviorEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiViewer_DisableBehaviorEnum_values count]; i++) {
    RAREiViewer_DisableBehaviorEnum *e = RAREiViewer_DisableBehaviorEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiViewer_DisableBehaviorEnum"};
  static J2ObjcClassInfo _RAREiViewer_DisableBehaviorEnum = { "DisableBehavior", "com.appnativa.rare.viewer", "iViewer", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiViewer_DisableBehaviorEnum;
}

@end
