//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/SeparatorMenuItem.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/ui/SeparatorMenuItem.h"
#import "RAREAPMenuItem.h"

@implementation RARESeparatorMenuItem

- (id)init {
  return [super initWithId:[RARESeparatorMenuItem createProxy]];
}

+ (id)createProxy {
  return [RAREAPMenuItem separatorItem];
}

- (BOOL)isSeparator {
  return YES;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createProxy", NULL, "LNSObject", 0x108, NULL },
    { "isSeparator", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RARESeparatorMenuItem = { "SeparatorMenuItem", "com.appnativa.rare.ui", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_RARESeparatorMenuItem;
}

@end
