//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/viewer/aCheckBoxTreeViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaCheckBoxTreeViewer_H_
#define _RAREaCheckBoxTreeViewer_H_

@class IOSIntArray;
@class IOSObjectArray;
@class RARECheckBoxTreeViewer;
@class RAREItemChangeEvent;
@class RARERenderableDataItem;
@class RARESPOTCheckBoxList;
@class RARESPOTTree;
@class RAREaCheckBoxTreeViewer_CheckBoxChangeHandler;
@protocol RAREiContainer;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/iItemChangeListener.h"
#include "com/appnativa/rare/viewer/TreeViewer.h"

@interface RAREaCheckBoxTreeViewer : RARETreeViewer {
 @public
  RAREaCheckBoxTreeViewer_CheckBoxChangeHandler *checkBoxChangeHandler_;
  BOOL linkedSelection_;
  BOOL settingFromHTTPFormValue_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)clearCheckMarks;
+ (RARECheckBoxTreeViewer *)createWithRAREiContainer:(id<RAREiContainer>)parent
                            withRARESPOTCheckBoxList:(RARESPOTCheckBoxList *)cfg;
- (void)dispose;
- (void)removeCheckBoxChangedHandler;
- (void)swapWithInt:(int)index1
            withInt:(int)index2;
- (void)setCheckBoxChangedHandlerWithNSString:(NSString *)handler;
- (void)setCheckboxTrailingWithBoolean:(BOOL)value;
- (void)setCheckedItemWithRARERenderableDataItem:(RARERenderableDataItem *)item
                                     withBoolean:(BOOL)checked;
- (void)setCheckedRowsWithIntArray:(IOSIntArray *)indices;
- (void)setFromHTTPFormValueWithId:(id)value;
- (void)setLinkSelectionWithBoolean:(BOOL)linked;
- (void)setManageChildNodeSelectionsWithBoolean:(BOOL)manage;
- (void)setRowCheckedWithInt:(int)row
                 withBoolean:(BOOL)checked;
- (void)setSelectedIndexWithInt:(int)index;
- (void)setSelectedIndexesWithIntArray:(IOSIntArray *)indices;
- (IOSObjectArray *)getCheckedData;
- (IOSObjectArray *)getCheckedDataAsStrings;
- (id)getCheckedItem;
- (NSString *)getCheckedItemAsString;
- (IOSObjectArray *)getCheckedItems;
- (IOSObjectArray *)getCheckedItemsAsStrings;
- (RARERenderableDataItem *)getCheckedRowItem;
- (IOSIntArray *)getCheckedRows;
- (id)getHTTPFormValue;
- (BOOL)getLinkSelection;
- (BOOL)hasCheckedRows;
- (BOOL)isCheckboxTrailing;
- (BOOL)isRowCheckedWithInt:(int)row;
- (BOOL)isValidForSubmissionWithBoolean:(BOOL)showerror;
- (void)configureExWithRARESPOTTree:(RARESPOTTree *)cfg;
- (void)finishedLoadingEx;
- (void)setIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)checked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)unchecked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)indeterminate;
- (void)copyAllFieldsTo:(RAREaCheckBoxTreeViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaCheckBoxTreeViewer, checkBoxChangeHandler_, RAREaCheckBoxTreeViewer_CheckBoxChangeHandler *)

typedef RAREaCheckBoxTreeViewer ComAppnativaRareViewerACheckBoxTreeViewer;

@interface RAREaCheckBoxTreeViewer_CheckBoxChangeHandler : NSObject < RAREiItemChangeListener > {
 @public
  RAREaCheckBoxTreeViewer *this$0_;
  NSString *handler_;
  id scriptCode_;
}

- (void)itemChangedWithRAREItemChangeEvent:(RAREItemChangeEvent *)e;
- (void)setHandlerWithNSString:(NSString *)handler;
- (id)initWithRAREaCheckBoxTreeViewer:(RAREaCheckBoxTreeViewer *)outer$;
- (void)copyAllFieldsTo:(RAREaCheckBoxTreeViewer_CheckBoxChangeHandler *)other;
@end

J2OBJC_FIELD_SETTER(RAREaCheckBoxTreeViewer_CheckBoxChangeHandler, this$0_, RAREaCheckBoxTreeViewer *)
J2OBJC_FIELD_SETTER(RAREaCheckBoxTreeViewer_CheckBoxChangeHandler, handler_, NSString *)
J2OBJC_FIELD_SETTER(RAREaCheckBoxTreeViewer_CheckBoxChangeHandler, scriptCode_, id)

#endif // _RAREaCheckBoxTreeViewer_H_
