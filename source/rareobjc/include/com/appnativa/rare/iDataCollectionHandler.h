//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iDataCollectionHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiDataCollectionHandler_H_
#define _RAREiDataCollectionHandler_H_

@class RAREActionLink;
@class RARESPOTDataCollection;
@protocol JavaUtilMap;
@protocol RAREiDataCollection;
@protocol RAREiFunctionCallback;
@protocol RAREiPlatformAppContext;

#import "JreEmulation.h"

@protocol RAREiDataCollectionHandler < NSObject, JavaObject >
- (id<RAREiDataCollection>)createCollectionWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)context
                                            withRARESPOTDataCollection:(RARESPOTDataCollection *)collection
                                             withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREiDataCollection>)createCollectionWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)context
                                                          withNSString:(NSString *)name
                                                    withRAREActionLink:(RAREActionLink *)link
                                                       withJavaUtilMap:(id<JavaUtilMap>)attributes
                                             withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)disposeWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)context;
@end

#define ComAppnativaRareIDataCollectionHandler RAREiDataCollectionHandler

#endif // _RAREiDataCollectionHandler_H_
