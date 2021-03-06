//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/renderer/UICompositeRenderer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/apple/ui/view/FormsView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/renderer/UICompositeRenderer.h"
#include "com/appnativa/rare/ui/renderer/UILabelRenderer.h"
#include "com/appnativa/rare/ui/renderer/aCompositeRenderer.h"

@implementation RAREUICompositeRenderer

- (id)init {
  return [super init];
}

- (id)initWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)rc {
  return [super initWithRAREiPlatformRenderingComponent:rc];
}

- (id)createNewNativeView {
  return [RAREFormsView createProxy];
}

- (id<RAREiPlatformRenderingComponent>)newCopy {
  id<RAREiPlatformRenderingComponent> rc = (renderingComponent_ == nil) ? [[RAREUILabelRenderer alloc] init] : ((id) [renderingComponent_ newCopy]);
  return [self setupNewCopyWithRAREaCompositeRenderer:[[RAREUICompositeRenderer alloc] initWithRAREiPlatformRenderingComponent:rc]];
}

- (void)prepareForReuseWithInt:(int)row
                       withInt:(int)column {
  if (renderingComponent_ != nil) {
    [renderingComponent_ prepareForReuseWithInt:row withInt:column];
  }
  [((RAREUILabelRenderer *) nil_chk(iconLabel_)) prepareForReuseWithInt:row withInt:column];
  [((RAREView *) nil_chk(view_)) clearVisualState];
}

- (void)clearRenderer {
  [super clearRenderer];
  [((RAREView *) nil_chk(view_)) clearVisualState];
}

- (void)setNativeViewWithId:(id)proxy {
  [((RAREView *) nil_chk([self getView])) setProxyWithId:proxy];
}

- (void)setRenderingViewWithRAREView:(RAREView *)view {
  [super setViewWithRAREView:view];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createNewNativeView", NULL, "LNSObject", 0x1, NULL },
    { "newCopy", NULL, "LRAREiPlatformRenderingComponent", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREUICompositeRenderer = { "UICompositeRenderer", "com.appnativa.rare.ui.renderer", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_RAREUICompositeRenderer;
}

@end
