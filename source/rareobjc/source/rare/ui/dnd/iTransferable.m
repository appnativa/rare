//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/dnd/iTransferable.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/dnd/TransferFlavor.h"
#include "com/appnativa/rare/ui/dnd/UnsupportedFlavorException.h"
#include "com/appnativa/rare/ui/dnd/iTransferable.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "java/io/IOException.h"


@interface RAREiTransferable : NSObject
@end

@implementation RAREiTransferable

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getTransferDataWithRARETransferFlavor:", NULL, "LNSObject", 0x401, "RAREUnsupportedFlavorException;JavaIoIOException" },
    { "isTransferFlavorSupportedWithRARETransferFlavor:", NULL, "Z", 0x401, NULL },
    { "getTransferFlavors", NULL, "LIOSObjectArray", 0x401, NULL },
    { "getDataIcon", NULL, "LRAREiPlatformIcon", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiTransferable = { "iTransferable", "com.appnativa.rare.ui.dnd", NULL, 0x201, 4, methods, 0, NULL, 0, NULL};
  return &_RAREiTransferable;
}

@end
