//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/renderer/UIIconRenderer.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/renderer/UIIconRenderer.h"
#include "com/appnativa/rare/ui/renderer/UILabelRenderer.h"
#include "java/lang/CharSequence.h"

@implementation RAREUIIconRenderer

- (id)init {
  return [super init];
}

- (id<RAREiPlatformRenderingComponent>)newCopy {
  return [self setupNewCopyWithRAREUILabelRenderer:[[RAREUIIconRenderer alloc] init]];
}

- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
}

- (id<RAREiPlatformComponent>)getComponentWithId:(id)value
                      withRARERenderableDataItem:(RARERenderableDataItem *)item {
  [self setTextWithJavaLangCharSequence:@""];
  return self;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "newCopy", NULL, "LRAREiPlatformRenderingComponent", 0x1, NULL },
    { "getComponentWithId:withRARERenderableDataItem:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREUIIconRenderer = { "UIIconRenderer", "com.appnativa.rare.ui.renderer", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_RAREUIIconRenderer;
}

@end
