//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/renderer/Renderers.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/renderer/Renderers.h"
#include "com/appnativa/rare/ui/renderer/UILabelRenderer.h"
#include "java/lang/ClassNotFoundException.h"

@implementation RARERenderers

- (id)init {
  return [super init];
}

+ (id<RAREiPlatformRenderingComponent>)createLabelRenderer {
  return [[RAREUILabelRenderer alloc] init];
}

+ (id<RAREiPlatformRenderingComponent>)createRendererWithNSString:(NSString *)className_ {
  if ([((NSString *) nil_chk(className_)) indexOf:'.'] == -1) {
    if (![className_ hasPrefix:@"UI"]) {
      className_ = [NSString stringWithFormat:@"UI%@", className_];
    }
    className_ = [NSString stringWithFormat:@"com.appnativa.rare.ui.renderer.%@", className_];
  }
  return (id<RAREiPlatformRenderingComponent>) check_protocol_cast([RAREPlatform createObjectWithNSString:className_], @protocol(RAREiPlatformRenderingComponent));
}

+ (id<RAREiPlatformRenderingComponent>)setupNewCopyWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)oldrc
                                                   withRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)newrc {
  return newrc;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "createLabelRenderer", NULL, "LRAREiPlatformRenderingComponent", 0x9, NULL },
    { "createRendererWithNSString:", NULL, "LRAREiPlatformRenderingComponent", 0x9, "JavaLangClassNotFoundException" },
    { "setupNewCopyWithRAREiPlatformRenderingComponent:withRAREiPlatformRenderingComponent:", NULL, "LRAREiPlatformRenderingComponent", 0x9, NULL },
  };
  static J2ObjcClassInfo _RARERenderers = { "Renderers", "com.appnativa.rare.ui.renderer", NULL, 0x1, 4, methods, 0, NULL, 0, NULL};
  return &_RARERenderers;
}

@end
