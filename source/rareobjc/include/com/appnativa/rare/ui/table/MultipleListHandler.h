//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/MultipleListHandler.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREMultipleListHandler_H_
#define _RAREMultipleListHandler_H_

@class RAREListSynchronizer;
@class RAREMultiTableTableComponent;
@protocol RAREiPlatformListHandler;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/table/aMultipleListHandler.h"

@interface RAREMultipleListHandler : RAREaMultipleListHandler {
 @public
  RAREListSynchronizer *listSynchronizer_;
}

- (id)initWithRAREMultiTableTableComponent:(RAREMultiTableTableComponent *)mtc
              withRAREiPlatformListHandler:(id<RAREiPlatformListHandler>)main
              withRAREiPlatformListHandler:(id<RAREiPlatformListHandler>)handler1
              withRAREiPlatformListHandler:(id<RAREiPlatformListHandler>)handler2;
- (void)dispose;
- (void)copyAllFieldsTo:(RAREMultipleListHandler *)other;
@end

J2OBJC_FIELD_SETTER(RAREMultipleListHandler, listSynchronizer_, RAREListSynchronizer *)

typedef RAREMultipleListHandler ComAppnativaRareUiTableMultipleListHandler;

#endif // _RAREMultipleListHandler_H_