//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/TableListHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARETableListHandler_H_
#define _RARETableListHandler_H_

@class RARETableComponent;
@protocol RAREiPlatformListDataModel;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/ListBoxListHandler.h"

@interface RARETableListHandler : RAREListBoxListHandler {
 @public
  RARETableComponent *tableComponent_;
}

- (id)initWithRARETableComponent:(RARETableComponent *)table
  withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)model;
- (void)scrollToLeftEdge;
- (void)scrollToRightEdge;
- (void)moveLeftRightWithBoolean:(BOOL)left
                     withBoolean:(BOOL)block;
- (void)makeSelectionVisible;
- (void)copyAllFieldsTo:(RARETableListHandler *)other;
@end

J2OBJC_FIELD_SETTER(RARETableListHandler, tableComponent_, RARETableComponent *)

typedef RARETableListHandler ComAppnativaRareUiTableTableListHandler;

#endif // _RARETableListHandler_H_
