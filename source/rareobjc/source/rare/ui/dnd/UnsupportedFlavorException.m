//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/dnd/UnsupportedFlavorException.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/ui/dnd/TransferFlavor.h"
#include "com/appnativa/rare/ui/dnd/UnsupportedFlavorException.h"

@implementation RAREUnsupportedFlavorException

- (id)initWithRARETransferFlavor:(RARETransferFlavor *)flavor {
  return [super initWithNSString:[NSString stringWithFormat:@"flavor = %@", [NSString valueOf:flavor]]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREUnsupportedFlavorException = { "UnsupportedFlavorException", "com.appnativa.rare.ui.dnd", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREUnsupportedFlavorException;
}

@end
