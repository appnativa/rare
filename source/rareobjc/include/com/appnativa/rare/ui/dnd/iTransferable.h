//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/dnd/iTransferable.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiTransferable_H_
#define _RAREiTransferable_H_

@class IOSObjectArray;
@class RARETransferFlavor;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"

@protocol RAREiTransferable < NSObject, JavaObject >
- (id)getTransferDataWithRARETransferFlavor:(RARETransferFlavor *)flavor;
- (BOOL)isTransferFlavorSupportedWithRARETransferFlavor:(RARETransferFlavor *)flavor;
- (IOSObjectArray *)getTransferFlavors;
- (id<RAREiPlatformIcon>)getDataIcon;
@end

#define ComAppnativaRareUiDndITransferable RAREiTransferable

#endif // _RAREiTransferable_H_
