//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iCollapsible.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/event/iExpandedListener.h"
#include "com/appnativa/rare/ui/event/iExpansionListener.h"
#include "com/appnativa/rare/ui/iCollapsible.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "java/lang/CharSequence.h"
#include "java/util/Map.h"


@interface RAREiCollapsible : NSObject
@end

@implementation RAREiCollapsible

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addExpandedListenerWithRAREiExpandedListener:", NULL, "V", 0x401, NULL },
    { "addExpansionListenerWithRAREiExpansionListener:", NULL, "V", 0x401, NULL },
    { "collapsePane", NULL, "V", 0x401, NULL },
    { "disposePane", NULL, "V", 0x401, NULL },
    { "expandPane", NULL, "V", 0x401, NULL },
    { "removeExpandedListenerWithRAREiExpandedListener:", NULL, "V", 0x401, NULL },
    { "removeExpansionListenerWithRAREiExpansionListener:", NULL, "V", 0x401, NULL },
    { "togglePane", NULL, "V", 0x401, NULL },
    { "setCollapsedWithBoolean:", NULL, "V", 0x401, NULL },
    { "setContentWithRAREiPlatformComponent:", NULL, "V", 0x401, NULL },
    { "setEventsEnabledWithBoolean:", NULL, "V", 0x401, NULL },
    { "setExpandOnDragOverWithBoolean:", NULL, "V", 0x401, NULL },
    { "setTitleBackgroundWithRAREUIColor:", NULL, "V", 0x401, NULL },
    { "setTitleBorderWithRAREiPlatformBorder:", NULL, "V", 0x401, NULL },
    { "setTitleFontWithRAREUIFont:", NULL, "V", 0x401, NULL },
    { "setTitleForegroundWithRAREUIColor:", NULL, "V", 0x401, NULL },
    { "setTitleIconWithRAREiPlatformIcon:", NULL, "V", 0x401, NULL },
    { "setTitleTextWithJavaLangCharSequence:", NULL, "V", 0x401, NULL },
    { "setTitleTextHAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:", NULL, "V", 0x401, NULL },
    { "setUserControllableWithBoolean:", NULL, "V", 0x401, NULL },
    { "getContent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getPane", NULL, "LRAREiParentComponent", 0x401, NULL },
    { "getTitle", NULL, "LJavaLangCharSequence", 0x401, NULL },
    { "getTitleComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getTitleIcon", NULL, "LRAREiPlatformIcon", 0x401, NULL },
    { "isEventsEnabled", NULL, "Z", 0x401, NULL },
    { "isExpanded", NULL, "Z", 0x401, NULL },
    { "isUserControllable", NULL, "Z", 0x401, NULL },
    { "setCollapseIconWithRAREiPlatformIcon:", NULL, "V", 0x401, NULL },
    { "setCollapseTipWithNSString:", NULL, "V", 0x401, NULL },
    { "setCollapsedTitleWithNSString:", NULL, "V", 0x401, NULL },
    { "setExpandIconWithRAREiPlatformIcon:", NULL, "V", 0x401, NULL },
    { "setExpandTipWithNSString:", NULL, "V", 0x401, NULL },
    { "setShowTitleWithBoolean:", NULL, "V", 0x401, NULL },
    { "setTitleIconOnLeftWithBoolean:", NULL, "V", 0x401, NULL },
    { "setTitleOpaqueWithBoolean:", NULL, "V", 0x401, NULL },
    { "setTitleProviderWithRAREiCollapsible_iTitleProvider:", NULL, "V", 0x401, NULL },
    { "setToggleOnTitleSingleClickWithBoolean:", NULL, "V", 0x401, NULL },
    { "setAnimateTransitionsWithBoolean:", NULL, "V", 0x401, NULL },
    { "setAnimatorOptionsWithJavaUtilMap:", NULL, "V", 0x401, NULL },
    { "setUseChevronWithBoolean:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiCollapsible = { "iCollapsible", "com.appnativa.rare.ui", NULL, 0x201, 41, methods, 0, NULL, 0, NULL};
  return &_RAREiCollapsible;
}

@end

@interface RAREiCollapsible_iTitleProvider : NSObject
@end

@implementation RAREiCollapsible_iTitleProvider

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getCollapsedTitleWithRAREiCollapsible:withRAREiPlatformComponent:", NULL, "LNSString", 0x401, NULL },
    { "getExpandedTitleWithRAREiCollapsible:withRAREiPlatformComponent:", NULL, "LNSString", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiCollapsible_iTitleProvider = { "iTitleProvider", "com.appnativa.rare.ui", "iCollapsible", 0x209, 2, methods, 0, NULL, 0, NULL};
  return &_RAREiCollapsible_iTitleProvider;
}

@end
