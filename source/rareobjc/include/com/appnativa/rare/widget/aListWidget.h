//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/widget/aListWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaListWidget_H_
#define _RAREaListWidget_H_

@class IOSIntArray;
@class IOSObjectArray;
@class RAREActionEvent;
@class RAREColumn;
@class RARERenderableDataItem;
@class RAREUIColor;
@class RAREUIPoint;
@class RAREUIRectangle;
@class RAREUIStroke;
@class RAREaWidgetListener;
@class RAREiListHandler_SelectionModeEnum;
@class RAREiListHandler_SelectionTypeEnum;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilList;
@protocol RAREUTiFilter;
@protocol RAREiContainer;
@protocol RAREiItemChangeListener;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformItemRenderer;
@protocol RAREiPlatformListHandler;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iActionable.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"

@interface RAREaListWidget : RAREaPlatformWidget < RAREiActionable, RAREiListHandler > {
 @public
  int initiallySelectedIndex_;
  RAREUIColor *alternatingColor_;
  RAREColumn *itemDescription_;
  id<RAREiPlatformListHandler> listComponent_;
  int submitValueType_;
  id<JavaUtilList> tempList_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (BOOL)addWithId:(RARERenderableDataItem *)item;
- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)addAllWithInt:(int)index
     withJavaUtilList:(id<JavaUtilList>)items
          withBoolean:(BOOL)insertMode;
- (void)addExWithRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)addIndexToFilteredListWithInt:(int)index;
- (void)addSelectionChangeListenerWithRAREiItemChangeListener:(id<RAREiItemChangeListener>)l;
- (void)addSelectionIndexWithInt:(int)index;
- (void)makeSelectionVisible;
- (void)clear;
- (void)clearContents;
- (void)clearEx;
- (void)clearContextMenuIndex;
- (void)clearSelection;
- (void)convertWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)copySelectedItemsWithInt:(int)index
                     withBoolean:(BOOL)insertMode
                     withBoolean:(BOOL)delete_ OBJC_METHOD_FAMILY_NONE;
- (id)deleteSelectedDataWithBoolean:(BOOL)returnData;
- (void)dispose;
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
- (void)upArrow;
- (void)downArrow;
- (void)refreshItems;
- (RARERenderableDataItem *)removeWithInt:(int)index;
- (void)repaintRowWithInt:(int)row;
- (BOOL)removeWithId:(id)item;
- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)removeRowsWithIntArray:(IOSIntArray *)indexes;
- (void)removeSelectionWithInt:(int)index;
- (void)removeSelectionChangeListenerWithRAREiItemChangeListener:(id<RAREiItemChangeListener>)l;
- (void)reset;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)scrollRowToTopWithInt:(int)row;
- (void)scrollRowToBottomWithInt:(int)row;
- (void)scrollRowToVisibleWithInt:(int)row;
- (void)selectAll;
- (void)sizeRowsToFit;
- (void)sortWithBoolean:(BOOL)descending;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)setAlternatingHilightColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setChangeEventsEnabledWithBoolean:(BOOL)enabled;
- (void)setChangeSelColorOnLostFocusWithBoolean:(BOOL)change;
- (void)setDividerLineWithRAREUIColor:(RAREUIColor *)c
                     withRAREUIStroke:(RAREUIStroke *)stroke;
- (void)setHandleFirstFocusSelectionWithBoolean:(BOOL)handle;
- (void)setListSelectableWithBoolean:(BOOL)selectable;
- (void)setListSelectionTypeWithRAREiListHandler_SelectionTypeEnum:(RAREiListHandler_SelectionTypeEnum *)type;
- (void)setSelectionModeWithRAREiListHandler_SelectionModeEnum:(RAREiListHandler_SelectionModeEnum *)selectionMode;
- (void)setMinRowHeightWithInt:(int)min;
- (void)setMinimumVisibleRowCountWithInt:(int)rows;
- (void)setRowHeightWithInt:(int)height;
- (void)setSelectedIndexWithInt:(int)index;
- (void)setSelectedIndexesWithIntArray:(IOSIntArray *)indices;
- (void)setSelectedItemWithRARERenderableDataItem:(RARERenderableDataItem *)value;
- (void)setValueWithId:(id)value;
- (void)setVisibleRowCountWithInt:(int)rows;
- (RAREUIColor *)getAlternatingHilightColor;
- (int)getAnchorSelectionIndex;
- (id<RAREiPlatformIcon>)getIcon;
- (RARERenderableDataItem *)getItemAtWithRAREUIPoint:(RAREUIPoint *)p;
- (int)getItemCount;
- (RAREColumn *)getItemDescription;
- (id<RAREiPlatformItemRenderer>)getItemRenderer;
- (id<RAREiPlatformComponent>)getListComponent;
- (RAREiListHandler_SelectionTypeEnum *)getListSelectionType;
- (int)getMaxSelectionIndex;
- (int)getMinSelectionIndex;
- (int)getMinimumVisibleRowCount;
- (RAREUIPoint *)getPopupLocationWithInt:(int)index;
- (int)getContextMenuIndex;
- (RARERenderableDataItem *)getContextMenuItem;
- (int)getPreferredHeightWithInt:(int)row;
- (RAREUIRectangle *)getRowBoundsWithInt:(int)row0
                                 withInt:(int)row1;
- (int)getRowCount;
- (int)getRowIndexAtWithRAREUIPoint:(RAREUIPoint *)p;
- (int)getRowIndexAtWithFloat:(float)x
                    withFloat:(float)y;
- (id<JavaUtilList>)getRows;
- (int)getSelectedIndex;
- (int)getSelectedIndexCount;
- (IOSIntArray *)getSelectedIndexes;
- (IOSIntArray *)getCheckedIndexes;
- (RARERenderableDataItem *)getSelectedItem;
- (id)getSelection;
- (NSString *)getSelectionAsString;
- (id)getSelectionData;
- (IOSObjectArray *)getSelections;
- (IOSObjectArray *)getSelectionsAsStrings;
- (IOSObjectArray *)getSelectionsDataAsStrings;
- (int)getVisibleRowCount;
- (BOOL)hasSelection;
- (BOOL)hasValue;
- (BOOL)isChangeEventsEnabled;
- (BOOL)isChangeSelColorOnLostFocus;
- (BOOL)isHandleFirstFocusSelection;
- (BOOL)isListSelectable;
- (BOOL)isRowSelectedWithInt:(int)row;
- (BOOL)isRowSelectedWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)isSubmittable;
- (BOOL)isTabular;
- (void)finishedLoadingEx;
- (void)handleInitialStuff;
- (void)setListComponentWithRAREiPlatformListHandler:(id<RAREiPlatformListHandler>)comp;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)listener OBJC_METHOD_FAMILY_NONE;
- (id<JavaUtilList>)getTempList;
- (NSString *)getWidgetAttributeWithNSString:(NSString *)name;
- (RAREUIColor *)getAlternatingRowColor;
- (int)getFirstVisibleIndex;
- (int)getLastVisibleIndex;
- (int)getRowHeight;
- (BOOL)isDataEventsEnabled;
- (BOOL)isKeepSelectionVisible;
- (void)setAlternatingRowColorWithRAREUIColor:(RAREUIColor *)param0;
- (void)setAutoHilightWithBoolean:(BOOL)param0;
- (void)setDataEventsEnabledWithBoolean:(BOOL)param0;
- (void)setDeselectEventsDisabledWithBoolean:(BOOL)param0;
- (void)setKeepSelectionVisibleWithBoolean:(BOOL)param0;
- (void)setShowDividerWithBoolean:(BOOL)param0;
- (void)setSingleClickActionWithBoolean:(BOOL)param0;
- (void)copyAllFieldsTo:(RAREaListWidget *)other;
@end

J2OBJC_FIELD_SETTER(RAREaListWidget, alternatingColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaListWidget, itemDescription_, RAREColumn *)
J2OBJC_FIELD_SETTER(RAREaListWidget, listComponent_, id<RAREiPlatformListHandler>)
J2OBJC_FIELD_SETTER(RAREaListWidget, tempList_, id<JavaUtilList>)

typedef RAREaListWidget ComAppnativaRareWidgetAListWidget;

@interface RAREaListWidget_$1 : NSObject < RAREiActionListener > {
 @public
  RAREaListWidget *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREaListWidget:(RAREaListWidget *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaListWidget_$1, this$0_, RAREaListWidget *)

#endif // _RAREaListWidget_H_
