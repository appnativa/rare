//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/ToggleButtonView.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/platform/apple/ui/view/ToggleButtonView.h"
#import "RAREUIControl.h"

@implementation RAREToggleButtonView

- (id)init {
  if (self = [super init]) {
    [self setButtonStyle];
  }
  return self;
}

- (void)setButtonStyle {
  ((RAREUIControl*)proxy_).isToggle= YES;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setButtonStyle", NULL, "V", 0x102, NULL },
  };
  static J2ObjcClassInfo _RAREToggleButtonView = { "ToggleButtonView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREToggleButtonView;
}

@end