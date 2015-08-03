//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/dnd/iFlavorCreator.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiFlavorCreator_H_
#define _RAREiFlavorCreator_H_

@class IOSObjectArray;
@class RARETransferFlavor;

#import "JreEmulation.h"

@protocol RAREiFlavorCreator < NSObject, JavaObject >
- (RARETransferFlavor *)getStringFlavor;
- (RARETransferFlavor *)getImageFlavor;
- (RARETransferFlavor *)getHTMLFlavor;
- (RARETransferFlavor *)getFileListFlavor;
- (RARETransferFlavor *)getURLFlavor;
- (RARETransferFlavor *)getURLListFlavor;
- (RARETransferFlavor *)createFlavorWithNSString:(NSString *)name
                               withNSStringArray:(IOSObjectArray *)mimeTypes;
- (RARETransferFlavor *)createFlavorWithId:(id)platformFlavor;
@end

#define ComAppnativaRareUiDndIFlavorCreator RAREiFlavorCreator

#endif // _RAREiFlavorCreator_H_