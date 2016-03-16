//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/RadioButtonView.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/platform/apple/ui/view/CustomButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/RadioButtonView.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#import "RAREUIControl.h"

@implementation RARERadioButtonView

- (id)init {
  if (self = [super init]) {
    [self setButtonStyle];
    [self setDefaultIcons];
  }
  return self;
}

- (void)setDefaultIcons {
  if ([((RAREUIColor *) nil_chk([RAREColorUtils getForeground])) isDarkColor]) {
    [self setIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.off.light"]];
    [self setPressedIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.off.pressed.light"]];
    [self setDisabledIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.off.disabled.light"]];
    [self setSelectedIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.on.light"]];
    [self setPressedSelectedIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.on.pressed.light"]];
    [self setDisabledSelectedIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.on.disabled.light"]];
  }
  else {
    [self setIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.off.dark"]];
    [self setPressedIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.off.pressed.dark"]];
    [self setDisabledIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.off.disabled.dark"]];
    [self setSelectedIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.on.dark"]];
    [self setPressedSelectedIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.on.pressed.dark"]];
    [self setDisabledSelectedIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.radiobutton.on.disabled.dark"]];
  }
}

- (void)setButtonStyle {
  ((RAREUIControl*)proxy_).radioButtonStyle= YES;
  ((RAREUIControl*)proxy_).isToggle= YES;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setDefaultIcons", NULL, "V", 0x4, NULL },
    { "setButtonStyle", NULL, "V", 0x102, NULL },
  };
  static J2ObjcClassInfo _RARERadioButtonView = { "RadioButtonView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_RARERadioButtonView;
}

@end
