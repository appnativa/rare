//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/renderer/UIComponentRenderer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/exception/ApplicationException.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/renderer/UIComponentRenderer.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/Exception.h"

@implementation RAREUIComponentRenderer

- (id)init {
  return [self initRAREUIComponentRendererWithRAREiPlatformComponent:nil];
}

- (id)initRAREUIComponentRendererWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  return [super initWithRAREiPlatformComponent:comp];
}

- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  return [self initRAREUIComponentRendererWithRAREiPlatformComponent:comp];
}

- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                  withRARESPOTWidget:(RARESPOTWidget *)config {
  return [super initWithRAREiPlatformComponent:comp withRARESPOTWidget:config];
}

- (id)createNewNativeView {
  if (renderingComponent_ != nil) {
    @try {
      return [[((RAREView *) nil_chk([renderingComponent_ getView])) getClass] newInstance];
    }
    @catch (JavaLangException *e) {
      @throw [[RAREApplicationException alloc] initWithJavaLangThrowable:e];
    }
  }
  @throw [[RAREApplicationException alloc] initWithNSString:@"renderingComponent is null"];
}

- (void)dispose {
  config_ = nil;
  if (renderingComponent_ != nil) {
    [renderingComponent_ dispose];
    renderingComponent_ = nil;
  }
}

- (void)clearRenderer {
  [super clearRenderer];
  if (renderingComponent_ != nil) {
    [((RAREView *) nil_chk([renderingComponent_ getView])) clearVisualState];
  }
}

- (id<RAREiPlatformRenderingComponent>)newCopy {
  if (config_ != nil) {
    return [[RAREUIComponentRenderer alloc] initWithRAREiPlatformComponent:[((id<RAREiWidget>) nil_chk([((RAREWindowViewer *) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getWindowViewer])) createWidgetWithRARESPOTWidget:config_])) getContainerComponent] withRARESPOTWidget:config_];
  }
  return [[RAREUIComponentRenderer alloc] initWithRAREiPlatformComponent:[((id<RAREiPlatformComponent>) nil_chk(renderingComponent_)) copy__]];
}

- (void)prepareForReuseWithInt:(int)row
                       withInt:(int)column {
  if (renderingComponent_ != nil) {
    [((RAREView *) nil_chk([renderingComponent_ getView])) clearVisualState];
  }
}

- (void)setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)ha
                      withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)va {
}

- (void)setNativeViewWithId:(id)proxy {
  if (renderingComponent_ != nil) {
    [((RAREView *) nil_chk([renderingComponent_ getView])) setProxyWithId:proxy];
  }
}

- (RAREView *)getNativeView {
  return [((id<RAREiPlatformComponent>) nil_chk(renderingComponent_)) getView];
}

- (void)setRenderingViewWithRAREView:(RAREView *)view {
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createNewNativeView", NULL, "LNSObject", 0x1, NULL },
    { "newCopy", NULL, "LRAREiPlatformRenderingComponent", 0x1, NULL },
    { "getNativeView", NULL, "LRAREView", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREUIComponentRenderer = { "UIComponentRenderer", "com.appnativa.rare.ui.renderer", NULL, 0x1, 3, methods, 0, NULL, 0, NULL};
  return &_RAREUIComponentRenderer;
}

@end
