//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/viewer/CheckBoxTreeViewer.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARECheckBoxTreeViewer_H_
#define _RARECheckBoxTreeViewer_H_

@class IOSIntArray;
@class RARETreeView;
@class RAREiListHandler_SelectionTypeEnum;
@protocol RAREiContainer;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aCheckBoxTreeViewer.h"

@interface RARECheckBoxTreeViewer : RAREaCheckBoxTreeViewer {
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)clearCheckMarks;
- (void)setCheckedRowsWithIntArray:(IOSIntArray *)indices;
- (void)setLinkSelectionWithBoolean:(BOOL)linked;
- (void)setListSelectionTypeWithRAREiListHandler_SelectionTypeEnum:(RAREiListHandler_SelectionTypeEnum *)type;
- (void)setManageChildNodeSelectionsWithBoolean:(BOOL)manage;
- (void)setRowCheckedWithInt:(int)row
                 withBoolean:(BOOL)checked;
- (BOOL)hasCheckedRows;
- (BOOL)isRowCheckedWithInt:(int)row;
- (RARETreeView *)getListView;
+ (void)registerForUse;
- (void)setIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)checked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)unchecked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)indeterminate;
@end

typedef RARECheckBoxTreeViewer ComAppnativaRareViewerCheckBoxTreeViewer;

#endif // _RARECheckBoxTreeViewer_H_
