//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/dnd/iTransferSupport.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiTransferSupport_H_
#define _RAREiTransferSupport_H_

@protocol JavaUtilList;

#import "JreEmulation.h"

@protocol RAREiTransferSupport < NSObject, JavaObject >
- (BOOL)canCopy;
- (BOOL)canDelete;
- (BOOL)canImportWithJavaUtilList:(id<JavaUtilList>)flavors;
- (BOOL)canMove;
- (BOOL)canSelectAll;
- (void)performCopy;
- (void)performCut;
- (id)deleteSelectedDataWithBoolean:(BOOL)returnData;
- (void)performPaste;
- (void)selectAll;
- (id)getSelection;
- (BOOL)hasSelection;
@end

#define ComAppnativaRareUiDndITransferSupport RAREiTransferSupport

#endif // _RAREiTransferSupport_H_
