//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/CheckBoxListViewer.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARECheckBoxListViewer_H_
#define _RARECheckBoxListViewer_H_

@class IOSIntArray;
@class RAREiListHandler_SelectionTypeEnum;
@protocol RAREiContainer;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aCheckBoxListViewer.h"

@interface RARECheckBoxListViewer : RAREaCheckBoxListViewer {
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)clearCheckMarks;
- (void)setCheckedRowsWithIntArray:(IOSIntArray *)indices;
- (void)setLinkSelectionWithBoolean:(BOOL)linked;
- (void)setListSelectionTypeWithRAREiListHandler_SelectionTypeEnum:(RAREiListHandler_SelectionTypeEnum *)type;
- (void)setRowCheckedWithInt:(int)row
                 withBoolean:(BOOL)checked;
- (BOOL)hasCheckedRows;
- (BOOL)isRowCheckedWithInt:(int)row;
- (void)setIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)checked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)unchecked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)indeterminate;
@end

typedef RARECheckBoxListViewer ComAppnativaRareViewerCheckBoxListViewer;

#endif // _RARECheckBoxListViewer_H_
