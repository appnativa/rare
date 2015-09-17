//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aListViewer.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREaListViewer_H_
#define _RAREaListViewer_H_

@class IOSIntArray;
@class IOSObjectArray;
@class RAREActionEvent;
@class RAREColumn;
@class RAREDropInformation;
@class RARERenderableDataItem;
@class RARESPOTListBox;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREUIColor;
@class RAREUIPoint;
@class RAREUIRectangle;
@class RAREUISelectionModelGroup;
@class RAREUIStroke;
@class RAREUTFilterableList;
@class RAREaWidgetListener;
@class RAREiListHandler_SelectionModeEnum;
@class RAREiListHandler_SelectionTypeEnum;
@class RAREiListView_EditingModeEnum;
@class SPOTPrintableString;
@protocol JavaUtilCollection;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREUTiFilter;
@protocol RAREiActionListener;
@protocol RAREiContainer;
@protocol RAREiFunctionCallback;
@protocol RAREiHyperlinkListener;
@protocol RAREiItemChangeListener;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformItemRenderer;
@protocol RAREiPlatformListDataModel;
@protocol RAREiPlatformListHandler;
@protocol RAREiScrollerSupport;
@protocol RAREiToolBar;
@protocol RAREiTransferable;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iActionable.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/viewer/aPlatformViewer.h"
#include "java/lang/Runnable.h"
#include "java/util/Comparator.h"

@interface RAREaListViewer : RAREaPlatformViewer < RAREiActionable, RAREiListHandler > {
 @public
  int initiallySelectedIndex_;
  BOOL addIndexToList_;
  RAREColumn *itemDescription_;
  id<RAREiPlatformListHandler> listComponent_;
  id<RAREiPlatformListDataModel> listModel_;
  RARERenderableDataItem *prototypeSectionItem_;
  RAREiListHandler_SelectionModeEnum *selectionMode_;
  id selectionModel_;
  RAREUISelectionModelGroup *selectionModelGroup_;
  id<JavaUtilComparator> sorter_;
  int submitColumn_;
  int submitValueType_;
  id<JavaUtilList> tempList_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)handleViewerConfigurationChangedWithBoolean:(BOOL)reset;
- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (BOOL)addWithId:(RARERenderableDataItem *)item;
- (void)addWithInt:(int)index
            withId:(RARERenderableDataItem *)item;
- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)addAllWithRARERenderableDataItemArray:(IOSObjectArray *)a;
- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)addAllWithInt:(int)index
     withJavaUtilList:(id<JavaUtilList>)items
          withBoolean:(BOOL)insertMode;
- (void)addExWithRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)addIndexToFilteredListWithInt:(int)index;
- (void)addParsedRowWithRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)addSelectionChangeListenerWithRAREiItemChangeListener:(id<RAREiItemChangeListener>)l;
- (void)addSelectionIndexWithInt:(int)index;
- (BOOL)canImportWithRARETransferFlavorArray:(IOSObjectArray *)flavors
                     withRAREDropInformation:(RAREDropInformation *)drop;
- (RAREUISelectionModelGroup *)getSelectionModelGroup;
- (void)clear;
- (void)clearContents;
- (void)clearPopupMenuIndex;
- (void)clearSelection;
- (void)clearTempList;
- (void)convertWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)copySelectedItemsWithInt:(int)index
                     withBoolean:(BOOL)insertMode
                     withBoolean:(BOOL)delete_ OBJC_METHOD_FAMILY_NONE;
- (id)deleteSelectedDataWithBoolean:(BOOL)returnData;
- (void)dispose;
- (BOOL)isKeepSelectionVisible;
- (void)setKeepSelectionVisibleWithBoolean:(BOOL)keepSelectionVisible;
- (BOOL)filterWithRAREUTiFilter:(id<RAREUTiFilter>)filter;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains
               withBoolean:(BOOL)nullPasses
               withBoolean:(BOOL)emptyPasses;
- (int)findAndSelectWithRAREUTiFilter:(id<RAREUTiFilter>)filter
                              withInt:(int)start;
- (int)findAndSelectWithNSString:(NSString *)filter
                         withInt:(int)start
                     withBoolean:(BOOL)contains;
- (void)fireActionForSelected;
- (void)moveWithInt:(int)from
            withInt:(int)to;
- (void)refreshItems;
- (RARERenderableDataItem *)removeWithInt:(int)index;
- (BOOL)removeWithId:(id)item;
- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)removeDataWithRAREiTransferable:(id<RAREiTransferable>)t;
- (void)removeRowsWithIntArray:(IOSIntArray *)indexes;
- (void)removeSelectionWithInt:(int)index;
- (void)removeSelectionChangeListenerWithRAREiItemChangeListener:(id<RAREiItemChangeListener>)l;
- (void)repaintRowWithInt:(int)row;
- (void)reset;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)rowChangedWithInt:(int)index;
- (void)rowChangedWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)rowsChangedWithInt:(int)firstRow
                   withInt:(int)lastRow;
- (void)scrollRowToTopWithInt:(int)row;
- (void)scrollRowToBottomWithInt:(int)row;
- (void)scrollRowToVisibleWithInt:(int)row;
- (void)makeSelectionVisible;
- (void)selectAll;
- (void)sizeRowsToFit;
- (void)sortWithBoolean:(BOOL)descending;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (void)startEditingWithBoolean:(BOOL)animate
          withRAREUIActionArray:(IOSObjectArray *)actions;
- (void)stopEditingWithBoolean:(BOOL)animate;
- (BOOL)unfilter;
- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)rows;
- (void)setAlternatingRowColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setAutoHilightWithBoolean:(BOOL)autoHilight;
- (void)setAutoSizeRowsToFitWithBoolean:(BOOL)autoSize;
- (void)setChangeEventsEnabledWithBoolean:(BOOL)enabled;
- (void)setChangeSelColorOnLostFocusWithBoolean:(BOOL)change;
- (void)setDataEventsEnabledWithBoolean:(BOOL)enabled;
- (void)setDeselectEventsDisabledWithBoolean:(BOOL)disabled;
- (void)setDividerLineWithRAREUIColor:(RAREUIColor *)c
                     withRAREUIStroke:(RAREUIStroke *)stroke;
- (void)setEditModeNotifierWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)setFromHTTPFormValueWithId:(id)value;
- (void)setHandleFirstFocusSelectionWithBoolean:(BOOL)handle;
- (void)setHyperlinkListenerWithRAREiHyperlinkListener:(id<RAREiHyperlinkListener>)l;
- (void)setItemDescriptionWithRAREColumn:(RAREColumn *)itemDescription;
- (void)setItemsWithJavaUtilCollection:(id<JavaUtilCollection>)items;
- (void)setListSelectableWithBoolean:(BOOL)selectable;
- (void)setListSelectionTypeWithRAREiListHandler_SelectionTypeEnum:(RAREiListHandler_SelectionTypeEnum *)type;
- (void)setMinRowHeightWithInt:(int)min;
- (void)setMinimumVisibleRowCountWithInt:(int)rows;
- (void)setRowEditingWidgetWithRAREiWidget:(id<RAREiWidget>)widget
                               withBoolean:(BOOL)centerVertically;
- (void)setRowHeightWithInt:(int)height;
- (void)setSelectedIndexWithInt:(int)index;
- (void)setSelectedIndexesWithIntArray:(IOSIntArray *)indices;
- (void)setSelectedItemWithRARERenderableDataItem:(RARERenderableDataItem *)value;
- (void)setSelectionModeWithRAREiListHandler_SelectionModeEnum:(RAREiListHandler_SelectionModeEnum *)selectionMode;
- (void)setShowDividerWithBoolean:(BOOL)show;
- (void)setShowLastDividerWithBoolean:(BOOL)show;
- (void)setSingleClickActionWithBoolean:(BOOL)singleClick;
- (void)setValueWithId:(id)value;
- (void)setVisibleRowCountWithInt:(int)rows;
- (RAREUIColor *)getAlternatingRowColor;
- (int)getAnchorSelectionIndex;
- (int)getEditModeMarkCount;
- (IOSIntArray *)getEditModeMarkedIndices;
- (IOSObjectArray *)getEditModeMarkedItems;
- (int)getEditingRow;
- (int)getFirstVisibleIndex;
- (id)getHTTPFormValue;
- (id<RAREiPlatformIcon>)getIcon;
- (RARERenderableDataItem *)getItemAtWithRAREUIPoint:(RAREUIPoint *)p;
- (int)getItemCount;
- (RAREColumn *)getItemDescription;
- (id<RAREiPlatformItemRenderer>)getItemRenderer;
- (int)getLastEditedRow;
- (int)getLastVisibleIndex;
- (id<RAREiPlatformComponent>)getListComponent;
- (RAREiListHandler_SelectionTypeEnum *)getListSelectionType;
- (int)getMaxSelectionIndex;
- (int)getMinSelectionIndex;
- (int)getMinimumVisibleRowCount;
- (RAREUIPoint *)getOverlayLocationWithInt:(int)row
                               withBoolean:(BOOL)top;
- (RAREUIPoint *)getPopupLocationWithInt:(int)index;
- (int)getPopupMenuIndex;
- (RARERenderableDataItem *)getPopupMenuItem;
- (int)getPreferredHeightWithInt:(int)row;
- (RAREUIRectangle *)getRowBoundsWithInt:(int)row0
                                 withInt:(int)row1;
- (int)getRowCount;
- (int)getRowHeight;
- (int)getRowIndexAtWithRAREUIPoint:(RAREUIPoint *)p;
- (int)getRowIndexAtWithFloat:(float)x
                    withFloat:(float)y;
- (id<JavaUtilList>)getRows;
- (int)getSelectedIndex;
- (int)getSelectedIndexCount;
- (IOSIntArray *)getSelectedIndexes;
- (RARERenderableDataItem *)getSelectedItem;
- (id)getSelection;
- (NSString *)getSelectionAsString;
- (id)getSelectionData;
- (RAREiListHandler_SelectionModeEnum *)getSelectionMode;
- (IOSObjectArray *)getSelections;
- (IOSObjectArray *)getSelectionsAsStrings;
- (IOSObjectArray *)getSelectionsDataAsStrings;
- (int)getVisibleRowCount;
- (BOOL)hasSelection;
- (BOOL)hasValue;
- (BOOL)isChangeEventsEnabled;
- (BOOL)isChangeSelColorOnLostFocus;
- (BOOL)isDataEventsEnabled;
- (BOOL)isEditing;
- (BOOL)isHandleFirstFocusSelection;
- (BOOL)isListSelectable;
- (BOOL)isRowSelectedWithInt:(int)row;
- (BOOL)isRowSelectedWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)isSubmittable;
- (BOOL)isTabular;
- (id<RAREiScrollerSupport>)getScrollerSupport;
- (RARERenderableDataItem *)createDefaultSectionProrotype;
- (void)configureExWithRARESPOTListBox:(RARESPOTListBox *)cfg;
- (void)configureSelectionModelGroupWithSPOTPrintableString:(SPOTPrintableString *)selectionGroupName
                                                     withId:(id)selectionModel;
- (id<RAREiToolBar>)createEditingToolbarIfNecessaryWithRARESPOTListBox:(RARESPOTListBox *)cfg;
- (void)createModelAndComponentsWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)finishedLoadingEx;
- (void)handleCustomPropertiesWithRARESPOTWidget:(RARESPOTWidget *)cfg
                                 withJavaUtilMap:(id<JavaUtilMap>)properties;
- (void)handleInitialStuff;
- (BOOL)importDataExWithRAREiTransferable:(id<RAREiTransferable>)t
                  withRAREDropInformation:(RAREDropInformation *)drop;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l OBJC_METHOD_FAMILY_NONE;
- (id<JavaUtilList>)updateListWithFilteringIndexWithRAREUTFilterableList:(RAREUTFilterableList *)list;
- (void)setEditingModeWithRAREiListView_EditingModeEnum:(RAREiListView_EditingModeEnum *)mode
                                            withBoolean:(BOOL)autoEnd
                                            withBoolean:(BOOL)allowSwiping;
- (void)setFlingThresholdWithInt:(int)threshold;
- (void)setListComponentWithRAREiPlatformListHandler:(id<RAREiPlatformListHandler>)comp;
- (void)setSelectFlingedWithBoolean:(BOOL)select;
- (void)setWholeViewFlingWithBoolean:(BOOL)wholeViewFling;
- (id<JavaUtilList>)getTempList;
- (NSString *)getWidgetAttributeWithNSString:(NSString *)name;
- (void)copyAllFieldsTo:(RAREaListViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaListViewer, itemDescription_, RAREColumn *)
J2OBJC_FIELD_SETTER(RAREaListViewer, listComponent_, id<RAREiPlatformListHandler>)
J2OBJC_FIELD_SETTER(RAREaListViewer, listModel_, id<RAREiPlatformListDataModel>)
J2OBJC_FIELD_SETTER(RAREaListViewer, prototypeSectionItem_, RARERenderableDataItem *)
J2OBJC_FIELD_SETTER(RAREaListViewer, selectionMode_, RAREiListHandler_SelectionModeEnum *)
J2OBJC_FIELD_SETTER(RAREaListViewer, selectionModel_, id)
J2OBJC_FIELD_SETTER(RAREaListViewer, selectionModelGroup_, RAREUISelectionModelGroup *)
J2OBJC_FIELD_SETTER(RAREaListViewer, sorter_, id<JavaUtilComparator>)
J2OBJC_FIELD_SETTER(RAREaListViewer, tempList_, id<JavaUtilList>)

typedef RAREaListViewer ComAppnativaRareViewerAListViewer;

@interface RAREaListViewer_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaListViewer *this$0_;
  id<JavaUtilCollection> val$rows_;
}

- (void)run;
- (id)initWithRAREaListViewer:(RAREaListViewer *)outer$
       withJavaUtilCollection:(id<JavaUtilCollection>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREaListViewer_$1, this$0_, RAREaListViewer *)
J2OBJC_FIELD_SETTER(RAREaListViewer_$1, val$rows_, id<JavaUtilCollection>)

@interface RAREaListViewer_$2 : NSObject < JavaLangRunnable > {
 @public
  RAREaListViewer *this$0_;
  id<JavaUtilCollection> val$items_;
}

- (void)run;
- (id)initWithRAREaListViewer:(RAREaListViewer *)outer$
       withJavaUtilCollection:(id<JavaUtilCollection>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREaListViewer_$2, this$0_, RAREaListViewer *)
J2OBJC_FIELD_SETTER(RAREaListViewer_$2, val$items_, id<JavaUtilCollection>)

@interface RAREaListViewer_$3 : NSObject < JavaUtilComparator > {
}

- (int)compareWithId:(id)o1
              withId:(id)o2;
- (id)init;
@end

#endif // _RAREaListViewer_H_
