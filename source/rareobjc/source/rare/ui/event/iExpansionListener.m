//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/iExpansionListener.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/exception/ExpandVetoException.h"
#include "com/appnativa/rare/ui/event/ExpansionEvent.h"
#include "com/appnativa/rare/ui/event/iExpansionListener.h"


@interface RAREiExpansionListener : NSObject
@end

@implementation RAREiExpansionListener

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "itemWillCollapseWithRAREExpansionEvent:", NULL, "V", 0x401, "RAREExpandVetoException" },
    { "itemWillExpandWithRAREExpansionEvent:", NULL, "V", 0x401, "RAREExpandVetoException" },
  };
  static J2ObjcClassInfo _RAREiExpansionListener = { "iExpansionListener", "com.appnativa.rare.ui.event", NULL, 0x201, 2, methods, 0, NULL, 0, NULL};
  return &_RAREiExpansionListener;
}

@end
