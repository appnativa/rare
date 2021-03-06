//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/dnd/RenderableDataItemTransferable.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARERenderableDataItemTransferable_H_
#define _RARERenderableDataItemTransferable_H_

@class IOSObjectArray;
@class RARERenderableDataItem;
@class RARETransferFlavor;
@protocol JavaUtilList;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/dnd/iTransferable.h"

@interface RARERenderableDataItemTransferable : NSObject < RAREiTransferable > {
 @public
  id<RAREiPlatformIcon> dataIcon_;
  id<JavaUtilList> dataItems_;
}

- (id)initWithJavaUtilList:(id<JavaUtilList>)items;
- (id)initWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)clear;
- (void)setDataIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (id<RAREiPlatformIcon>)getDataIcon;
- (id<JavaUtilList>)getItems;
- (id)getTransferDataWithRARETransferFlavor:(RARETransferFlavor *)flavor;
- (IOSObjectArray *)getTransferFlavors;
- (BOOL)isTransferFlavorSupportedWithRARETransferFlavor:(RARETransferFlavor *)flavor;
- (void)copyAllFieldsTo:(RARERenderableDataItemTransferable *)other;
@end

J2OBJC_FIELD_SETTER(RARERenderableDataItemTransferable, dataIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARERenderableDataItemTransferable, dataItems_, id<JavaUtilList>)

typedef RARERenderableDataItemTransferable ComAppnativaRareUiDndRenderableDataItemTransferable;

#endif // _RARERenderableDataItemTransferable_H_
