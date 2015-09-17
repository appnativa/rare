//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/ListBoxViewer.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREListBoxViewer_H_
#define _RAREListBoxViewer_H_

@class IOSIntArray;
@class IOSObjectArray;
@class RAREListView;
@class RARERenderableDataItem;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREUTFilterableList;
@class RAREiListHandler_SelectionModeEnum;
@class RAREiListView_EditingModeEnum;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiContainer;
@protocol RAREiFunctionCallback;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aListViewer.h"

@interface RAREListBoxViewer : RAREaListViewer {
 @public
  IOSIntArray *selectedIndexes_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)dispose;
- (void)rowChangedWithInt:(int)index;
- (void)rowChangedWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)rowsChangedWithInt:(int)firstRow
                   withInt:(int)lastRow;
- (void)startEditingWithBoolean:(BOOL)animate
          withRAREUIActionArray:(IOSObjectArray *)actions;
- (void)stopEditingWithBoolean:(BOOL)animate;
- (void)setAutoSizeRowsToFitWithBoolean:(BOOL)autoSize;
- (void)setEditModeNotifierWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)setRowEditingWidgetWithRAREiWidget:(id<RAREiWidget>)widget
                               withBoolean:(BOOL)centerVertically;
- (void)setSelectionModeWithRAREiListHandler_SelectionModeEnum:(RAREiListHandler_SelectionModeEnum *)selectionMode;
- (void)setShowLastDividerWithBoolean:(BOOL)show;
- (int)getEditingRow;
- (int)getLastEditedRow;
+ (void)handleCustomPropertiesWithRAREListView:(RAREListView *)v
                            withRARESPOTWidget:(RARESPOTWidget *)cfg
                               withJavaUtilMap:(id<JavaUtilMap>)properties;
- (RARERenderableDataItem *)createDefaultSectionProrotype;
- (void)createModelAndComponentsWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)handleCustomPropertiesWithRARESPOTWidget:(RARESPOTWidget *)cfg
                                 withJavaUtilMap:(id<JavaUtilMap>)properties;
- (void)handleViewerConfigurationChangedWithBoolean:(BOOL)reset;
- (void)handleViewerConfigurationWillChangeWithId:(id)newConfig;
- (id<JavaUtilList>)updateListWithFilteringIndexWithRAREUTFilterableList:(RAREUTFilterableList *)list;
- (void)setEditingModeWithRAREiListView_EditingModeEnum:(RAREiListView_EditingModeEnum *)mode
                                            withBoolean:(BOOL)autoEnd
                                            withBoolean:(BOOL)allowSwiping;
- (void)setFlingThresholdWithInt:(int)threshold;
- (void)setSelectFlingedWithBoolean:(BOOL)select;
- (void)setWholeViewFlingWithBoolean:(BOOL)wholeViewFling;
- (void)copyAllFieldsTo:(RAREListBoxViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREListBoxViewer, selectedIndexes_, IOSIntArray *)

typedef RAREListBoxViewer ComAppnativaRareViewerListBoxViewer;

#endif // _RAREListBoxViewer_H_
