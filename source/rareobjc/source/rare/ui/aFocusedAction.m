//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aFocusedAction.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/spot/ActionItem.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/aFocusedAction.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/widget/iWidget.h"

@implementation RAREaFocusedAction

- (id)initWithNSString:(NSString *)name {
  return [super initWithNSString:name];
}

- (id)initWithRAREUIAction:(RAREUIAction *)a {
  return [super initWithRAREaUIAction:a];
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
   withRARESPOTActionItem:(RARESPOTActionItem *)item {
  return [super initWithRAREiWidget:context withRARESPOTActionItem:item];
}

- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)text
 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  return [super initWithNSString:name withJavaLangCharSequence:text withRAREiPlatformIcon:icon];
}

- (void)update {
  [self updateWithRAREiPlatformComponent:[((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getPermanentFocusOwner]];
}

- (void)updateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)permanentFocusOwner {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "updateWithRAREiPlatformComponent:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREaFocusedAction = { "aFocusedAction", "com.appnativa.rare.ui", NULL, 0x401, 1, methods, 0, NULL, 0, NULL};
  return &_RAREaFocusedAction;
}

@end
