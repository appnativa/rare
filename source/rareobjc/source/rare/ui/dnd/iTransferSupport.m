//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/dnd/iTransferSupport.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/dnd/iTransferSupport.h"
#include "java/util/List.h"


@interface RAREiTransferSupport : NSObject
@end

@implementation RAREiTransferSupport

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "canCopy", NULL, "Z", 0x401, NULL },
    { "canDelete", NULL, "Z", 0x401, NULL },
    { "canImportWithJavaUtilList:", NULL, "Z", 0x401, NULL },
    { "canMove", NULL, "Z", 0x401, NULL },
    { "canSelectAll", NULL, "Z", 0x401, NULL },
    { "performCopy", NULL, "V", 0x401, NULL },
    { "performCut", NULL, "V", 0x401, NULL },
    { "deleteSelectedDataWithBoolean:", NULL, "LNSObject", 0x401, NULL },
    { "performPaste", NULL, "V", 0x401, NULL },
    { "selectAll", NULL, "V", 0x401, NULL },
    { "getSelection", NULL, "LNSObject", 0x401, NULL },
    { "hasSelection", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiTransferSupport = { "iTransferSupport", "com.appnativa.rare.ui.dnd", NULL, 0x201, 12, methods, 0, NULL, 0, NULL};
  return &_RAREiTransferSupport;
}

@end