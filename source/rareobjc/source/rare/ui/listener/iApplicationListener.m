//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/listener/iApplicationListener.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/ui/listener/iApplicationListener.h"


@interface RAREiApplicationListener : NSObject
@end

@implementation RAREiApplicationListener

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "allowClosingWithRAREiPlatformAppContext:", NULL, "Z", 0x401, NULL },
    { "applicationClosingWithRAREiPlatformAppContext:", NULL, "V", 0x401, NULL },
    { "applicationInitializedWithRAREiPlatformAppContext:", NULL, "V", 0x401, NULL },
    { "applicationPausedWithRAREiPlatformAppContext:", NULL, "V", 0x401, NULL },
    { "applicationResumedWithRAREiPlatformAppContext:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiApplicationListener = { "iApplicationListener", "com.appnativa.rare.ui.listener", NULL, 0x201, 5, methods, 0, NULL, 0, NULL};
  return &_RAREiApplicationListener;
}

@end
