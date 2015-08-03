//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/TableView.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARETableView_H_
#define _RARETableView_H_

@class RAREColumn;
@class RARERenderableDataItem;
@class RARETableComponent;
@class RARETableHeader;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIRectangle;
@class RAREUIStroke;
@class RAREUTIntList;
@class RAREaTableBasedView_RowView;
@class RAREiTableComponent_TableTypeEnum;
@protocol JavaLangCharSequence;
@protocol RAREiTreeItem;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/ListView.h"

@interface RARETableView : RAREListView {
 @public
  RARETableHeader *header_;
  BOOL autoSizeColumns_;
  RAREUTIntList *columnWidths_;
  RAREUTIntList *columnWidthsIndex_;
  RAREUIDimension *computeSize_;
  BOOL heightsDirty_;
  BOOL horizontalScollEnabled_;
  BOOL needSizeToFitCall_;
  __weak RARETableComponent *tableComponent_;
  int oldWidth_TableView_;
  int oldHeight_TableView_;
  RAREiTableComponent_TableTypeEnum *tableType_;
}

+ (RARERenderableDataItem *)NULL_REPLACEMENT;
+ (void)setNULL_REPLACEMENT:(RARERenderableDataItem *)NULL_REPLACEMENT;
- (id)init;
- (id)initWithId:(id)proxy;
- (void)columnSelectedWithInt:(int)row
                      withInt:(int)column;
- (void)columnSizesUpdated;
- (void)createHeader;
- (id)createNativeColumnViewWithInt:(int)row
                            withInt:(int)modelCol
                     withRAREColumn:(RAREColumn *)col
         withRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)disposeEx;
- (id<JavaLangCharSequence>)getColumnTitleWithRAREColumn:(RAREColumn *)c;
- (int)getGridRowHeight;
- (RARETableHeader *)getHeader;
- (id)getHeaderProxy;
- (int)getRowHeightWithInt:(int)row;
- (int)getSelectedColumn;
- (float)getSelectionPaintEndXWithFloat:(float)currentEndX;
- (float)getSelectionPaintStartXWithFloat:(float)currentStartX;
- (RARETableComponent *)getTableComponent;
- (BOOL)isScrolling;
- (BOOL)isScrollingEx;
- (BOOL)isColumnSelectedWithInt:(int)col;
- (BOOL)isColumnSelectionAllowed;
- (BOOL)isTable;
- (void)layoutWithId:(id)parentUIView
withRARERenderableDataItem:(RARERenderableDataItem *)rowItem
             withInt:(int)parentWidth
             withInt:(int)parentHeight;
- (void)rectOfRowWithInt:(int)row
     withRAREUIRectangle:(RAREUIRectangle *)rect;
- (void)refreshItems;
- (void)removeRowHeights;
- (void)renderItemWithInt:(int)row
withRARERenderableDataItem:(RARERenderableDataItem *)item
withRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view
              withBoolean:(BOOL)isSelected
              withBoolean:(BOOL)isPressed
        withRAREiTreeItem:(id<RAREiTreeItem>)ti;
- (void)setAutoSizeColumnsWithBoolean:(BOOL)autoSizeColumns;
- (void)setHorizontalScollEnabledWithBoolean:(BOOL)enabled;
- (void)setShowGridLinesWithBoolean:(BOOL)horizontal
                        withBoolean:(BOOL)vertical
                    withRAREUIColor:(RAREUIColor *)color
                   withRAREUIStroke:(RAREUIStroke *)stroke;
- (void)setTableComponentWithRARETableComponent:(RARETableComponent *)tableComponent;
- (void)sizeColumnsToFit;
- (void)sizeRowsToFit;
- (void)sizeToFit;
- (void)makeSelectionVisible;
- (BOOL)updateColumnSizesWithInt:(int)width
                         withInt:(int)height;
- (void)updateRenderInsetsForCheckBoxWithFloat:(float)left
                                     withFloat:(float)right;
+ (id)createTableProxy;
- (RAREiTableComponent_TableTypeEnum *)getTableType;
- (void)setTableTypeWithRAREiTableComponent_TableTypeEnum:(RAREiTableComponent_TableTypeEnum *)tableType;
- (void)copyAllFieldsTo:(RARETableView *)other;
@end

J2OBJC_FIELD_SETTER(RARETableView, header_, RARETableHeader *)
J2OBJC_FIELD_SETTER(RARETableView, columnWidths_, RAREUTIntList *)
J2OBJC_FIELD_SETTER(RARETableView, columnWidthsIndex_, RAREUTIntList *)
J2OBJC_FIELD_SETTER(RARETableView, computeSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RARETableView, tableType_, RAREiTableComponent_TableTypeEnum *)

typedef RARETableView ComAppnativaRareUiTableTableView;

#endif // _RARETableView_H_
