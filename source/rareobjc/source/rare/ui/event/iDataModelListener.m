//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/iDataModelListener.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/event/iDataModelListener.h"
#include "java/util/List.h"


@interface RAREiDataModelListener : NSObject
@end

@implementation RAREiDataModelListener

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "contentsChangedWithId:", NULL, "V", 0x401, NULL },
    { "contentsChangedWithId:withInt:withInt:", NULL, "V", 0x401, NULL },
    { "intervalAddedWithId:withInt:withInt:", NULL, "V", 0x401, NULL },
    { "intervalRemovedWithId:withInt:withInt:withJavaUtilList:", NULL, "V", 0x401, NULL },
    { "structureChangedWithId:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiDataModelListener = { "iDataModelListener", "com.appnativa.rare.ui.event", NULL, 0x201, 5, methods, 0, NULL, 0, NULL};
  return &_RAREiDataModelListener;
}

@end
