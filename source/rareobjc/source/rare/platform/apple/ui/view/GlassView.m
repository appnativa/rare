//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/GlassView.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/platform/apple/ui/view/GlassView.h"
#import "RAREOverlayView.h"

@implementation RAREGlassView

- (id)initWithBoolean:(BOOL)overlayContainer {
  return [super initWithId:[RAREGlassView createProxyWithBoolean:overlayContainer]];
}

+ (id)createProxyWithBoolean:(BOOL)overlayContainer {
  RAREOverlayView* v=[[RAREOverlayView alloc]init];
  [v setActAsGlass: !overlayContainer];
  return v;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createProxyWithBoolean:", NULL, "LNSObject", 0x108, NULL },
  };
  static J2ObjcClassInfo _RAREGlassView = { "GlassView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREGlassView;
}

@end
