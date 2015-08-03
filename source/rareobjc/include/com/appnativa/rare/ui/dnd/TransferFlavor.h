//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/dnd/TransferFlavor.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARETransferFlavor_H_
#define _RARETransferFlavor_H_

@class IOSObjectArray;
@class JavaIoReader;
@class JavaUtilArrayList;
@protocol JavaUtilList;
@protocol JavaUtilSet;
@protocol RAREiFlavorCreator;
@protocol RAREiTransferable;

#import "JreEmulation.h"

@interface RARETransferFlavor : NSObject {
 @public
  JavaUtilArrayList *mimeTypes_;
  NSString *name_;
  id platfromFlavor_;
}

+ (NSString *)CUSTOM_DATAFLAVOR_MIME_TYPE;
+ (NSString *)WIDGET_DATAFLAVOR_MIME_TYPE;
+ (RARETransferFlavor *)fileListFlavor;
+ (RARETransferFlavor *)htmlFlavor;
+ (RARETransferFlavor *)imageFlavor;
+ (RARETransferFlavor *)itemFlavor;
+ (RARETransferFlavor *)stringFlavor;
+ (RARETransferFlavor *)urlFlavor;
+ (RARETransferFlavor *)urlListFlavor;
+ (RARETransferFlavor *)widgetFlavor;
+ (id<RAREiFlavorCreator>)flavorCreator;
- (id)initWithNSString:(NSString *)name
                withId:(id)platfromFlavor
       withJavaUtilSet:(id<JavaUtilSet>)types;
- (id)initWithNSString:(NSString *)name
                withId:(id)platfromFlavor
     withNSStringArray:(IOSObjectArray *)types;
+ (void)addDataFlavorWithNSString:(NSString *)name
                 withJavaUtilList:(id<JavaUtilList>)list
                      withBoolean:(BOOL)export_;
+ (IOSObjectArray *)createSupportedDataFlavorsWithNSStringArray:(IOSObjectArray *)flavorNames
                                                    withBoolean:(BOOL)export_;
- (BOOL)isEqual:(id)o;
+ (RARETransferFlavor *)fromPlatformFlavorWithId:(id)flavor;
+ (RARETransferFlavor *)getDataFlavorWithNSStringArray:(IOSObjectArray *)flavorNames
                                          withNSString:(NSString *)name;
+ (RARETransferFlavor *)getDataFlavorWithRARETransferFlavorArray:(IOSObjectArray *)supportedFlavors
                                                    withNSString:(NSString *)flavor;
+ (NSString *)getDataFlavorNameWithNSStringArray:(IOSObjectArray *)flavorNames
                          withRARETransferFlavor:(RARETransferFlavor *)flavor;
- (id<JavaUtilList>)getMimeTypes;
- (NSString *)getName;
- (id)getPlatformFlavor;
- (JavaIoReader *)getReaderForTextWithRAREiTransferable:(id<RAREiTransferable>)transferable;
+ (RARETransferFlavor *)getStringDataFlavorWithRARETransferFlavorArray:(IOSObjectArray *)flavor;
- (BOOL)hasPlatformFlavorWithId:(id)flavor;
+ (BOOL)isDataFlavorSupportedWithNSStringArray:(IOSObjectArray *)flavorNames
                        withRARETransferFlavor:(RARETransferFlavor *)flavor;
+ (BOOL)isDataFlavorSupportedWithRARETransferFlavorArray:(IOSObjectArray *)supportedFlavors
                                            withNSString:(NSString *)flavor;
- (BOOL)isStringFlavor;
- (BOOL)isWidgetFlavor;
- (BOOL)isItemFlavor;
- (BOOL)isMimeTypeSupportedWithNSString:(NSString *)type;
- (void)copyAllFieldsTo:(RARETransferFlavor *)other;
@end

J2OBJC_FIELD_SETTER(RARETransferFlavor, mimeTypes_, JavaUtilArrayList *)
J2OBJC_FIELD_SETTER(RARETransferFlavor, name_, NSString *)
J2OBJC_FIELD_SETTER(RARETransferFlavor, platfromFlavor_, id)

typedef RARETransferFlavor ComAppnativaRareUiDndTransferFlavor;

#endif // _RARETransferFlavor_H_