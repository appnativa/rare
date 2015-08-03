//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/renderer/WordWrapLabelRenderer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/platform/apple/ui/view/LabelView.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/renderer/Renderers.h"
#include "com/appnativa/rare/ui/renderer/WordWrapLabelRenderer.h"

@implementation RAREWordWrapLabelRenderer

- (id)init {
  return [self initRAREWordWrapLabelRendererWithRARELabelView:[[RARELabelView alloc] init]];
}

- (id)initRAREWordWrapLabelRendererWithRARELabelView:(RARELabelView *)label {
  if (self = [super initWithRARELabelView:label]) {
    [((RARELabelView *) nil_chk(label)) setWordWrapWithBoolean:YES];
  }
  return self;
}

- (id)initWithRARELabelView:(RARELabelView *)label {
  return [self initRAREWordWrapLabelRendererWithRARELabelView:label];
}

- (id<RAREiPlatformRenderingComponent>)newCopy {
  return [RARERenderers setupNewCopyWithRAREiPlatformRenderingComponent:self withRAREiPlatformRenderingComponent:[[RAREWordWrapLabelRenderer alloc] init]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "newCopy", NULL, "LRAREiPlatformRenderingComponent", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREWordWrapLabelRenderer = { "WordWrapLabelRenderer", "com.appnativa.rare.ui.renderer", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREWordWrapLabelRenderer;
}

@end
