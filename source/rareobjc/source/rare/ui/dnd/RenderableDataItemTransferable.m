//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/dnd/RenderableDataItemTransferable.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/dnd/RenderableDataItemTransferable.h"
#include "com/appnativa/rare/ui/dnd/TransferFlavor.h"
#include "com/appnativa/rare/ui/dnd/UnsupportedFlavorException.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/util/Helper.h"
#include "java/io/IOException.h"
#include "java/util/Arrays.h"
#include "java/util/List.h"

@implementation RARERenderableDataItemTransferable

- (id)initWithJavaUtilList:(id<JavaUtilList>)items {
  if (self = [super init]) {
    dataItems_ = items;
  }
  return self;
}

- (id)initWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  if (self = [super init]) {
    dataItems_ = [JavaUtilArrays asListWithNSObjectArray:[IOSObjectArray arrayWithObjects:(id[]){ item } count:1 type:[IOSClass classWithClass:[NSObject class]]]];
  }
  return self;
}

- (void)clear {
  dataItems_ = nil;
  dataIcon_ = nil;
}

- (void)setDataIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  self->dataIcon_ = icon;
}

- (id<RAREiPlatformIcon>)getDataIcon {
  return dataIcon_;
}

- (id<JavaUtilList>)getItems {
  return dataItems_;
}

- (id)getTransferDataWithRARETransferFlavor:(RARETransferFlavor *)flavor {
  if (flavor == [RARETransferFlavor itemFlavor]) {
    return dataItems_;
  }
  if (flavor == [RARETransferFlavor stringFlavor]) {
    return [RAREUTHelper toStringWithJavaUtilList:dataItems_ withNSString:@"\n"];
  }
  return nil;
}

- (IOSObjectArray *)getTransferFlavors {
  return [IOSObjectArray arrayWithObjects:(id[]){ [RARETransferFlavor itemFlavor] } count:1 type:[IOSClass classWithClass:[RARETransferFlavor class]]];
}

- (BOOL)isTransferFlavorSupportedWithRARETransferFlavor:(RARETransferFlavor *)flavor {
  return flavor == [RARETransferFlavor itemFlavor];
}

- (void)copyAllFieldsTo:(RARERenderableDataItemTransferable *)other {
  [super copyAllFieldsTo:other];
  other->dataIcon_ = dataIcon_;
  other->dataItems_ = dataItems_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getDataIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getItems", NULL, "LJavaUtilList", 0x1, NULL },
    { "getTransferDataWithRARETransferFlavor:", NULL, "LNSObject", 0x1, "RAREUnsupportedFlavorException;JavaIoIOException" },
    { "getTransferFlavors", NULL, "LIOSObjectArray", 0x1, NULL },
    { "isTransferFlavorSupportedWithRARETransferFlavor:", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "dataIcon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "dataItems_", NULL, 0x4, "LJavaUtilList" },
  };
  static J2ObjcClassInfo _RARERenderableDataItemTransferable = { "RenderableDataItemTransferable", "com.appnativa.rare.ui.dnd", NULL, 0x1, 5, methods, 2, fields, 0, NULL};
  return &_RARERenderableDataItemTransferable;
}

@end
