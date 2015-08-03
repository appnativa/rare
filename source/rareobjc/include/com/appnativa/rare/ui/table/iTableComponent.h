//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/ui/table/iTableComponent.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiTableComponent_H_
#define _RAREiTableComponent_H_

@class IOSIntArray;
@class RAREColumn;
@class RAREPaintBucket;
@class RARETableStyle;
@class RAREUIRectangle;
@class RAREaTableHeader;
@class RAREiTableComponent_GridViewTypeEnum;
@class RAREiTableComponent_TableTypeEnum;
@protocol JavaLangCharSequence;
@protocol JavaUtilComparator;
@protocol JavaUtilList;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformItemRenderer;
@protocol RAREiTableModel;

#import "JreEmulation.h"
#include "java/lang/Enum.h"

@protocol RAREiTableComponent < NSObject, JavaObject >
- (BOOL)isSortDescending;
- (int)convertModelIndexToViewWithInt:(int)index;
- (int)convertViewIndexToModelWithInt:(int)index;
- (int)getFirstVisibleColumnIndex;
- (int)getLastVisibleColumnIndex;
- (RAREaTableHeader *)getTableHeader;
- (RAREColumn *)createColumnWithNSString:(NSString *)title
                                  withId:(id)value
                                 withInt:(int)type
                                  withId:(id)data
                   withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)moveColumnWithInt:(int)column
                  withInt:(int)targetColumn;
- (void)resetTableWithJavaUtilList:(id<JavaUtilList>)columns
                  withJavaUtilList:(id<JavaUtilList>)rows;
- (void)sizeRowsToFit;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)c;
- (void)sortWithInt:(int)col;
- (void)sortWithInt:(int)col
        withBoolean:(BOOL)descending
        withBoolean:(BOOL)useLinkedData;
- (void)stopEditing;
- (void)setColumnIconWithInt:(int)col
       withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setColumnTitleWithInt:(int)col
     withJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)setColumnVisibleWithInt:(int)col
                    withBoolean:(BOOL)visible;
- (void)setGridViewTypeWithRAREiTableComponent_GridViewTypeEnum:(RAREiTableComponent_GridViewTypeEnum *)type;
- (void)setHeaderBackgroundWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setRowHeightWithInt:(int)row
                    withInt:(int)h;
- (void)setSelectedColumnIndexWithInt:(int)index;
- (void)setSelectedColumnIndicesWithIntArray:(IOSIntArray *)indices;
- (void)setShowHorizontalLinesWithBoolean:(BOOL)b;
- (void)setShowVerticalLinesWithBoolean:(BOOL)b;
- (void)setSortColumnWithInt:(int)col
                 withBoolean:(BOOL)descending;
- (void)setStyleWithRARETableStyle:(RARETableStyle *)tableStyle;
- (void)setTable;
- (RAREUIRectangle *)getCellRectWithInt:(int)row
                                withInt:(int)column
                            withBoolean:(BOOL)includeMargin;
- (int)getColumnCount;
- (int)getColumnIndexAtWithFloat:(float)x
                       withFloat:(float)y;
- (RAREiTableComponent_GridViewTypeEnum *)getGridViewType;
- (id<RAREiPlatformItemRenderer>)getItemRenderer;
- (id<RAREiTableModel>)getModel;
- (id<RAREiPlatformComponent>)getPlatformComponent;
- (int)getSelectedColumn;
- (int)getSelectedColumnCount;
- (IOSIntArray *)getSelectedColumns;
- (int)getSortColumn;
- (int)getVisibleColumnCount;
- (BOOL)isAutoSizeRows;
- (BOOL)isEditing;
- (RAREUIRectangle *)getViewRect;
- (id<RAREiTableComponent>)getMainTable;
- (id<RAREiTableComponent>)getRowHeaderTable;
- (id<RAREiTableComponent>)getRowFooterTable;
- (id<RAREiTableComponent>)getColumnHeaderTable;
- (id<RAREiTableComponent>)getColumnFooterTable;
- (RAREiTableComponent_TableTypeEnum *)getTableType;
- (void)setTableTypeWithRAREiTableComponent_TableTypeEnum:(RAREiTableComponent_TableTypeEnum *)type;
- (void)dispose;
- (BOOL)isMainTable;
- (BOOL)isMultiTableComponent;
@end

#define ComAppnativaRareUiTableITableComponent RAREiTableComponent

typedef enum {
  RAREiTableComponent_GridViewType_VERTICAL_WRAP = 0,
  RAREiTableComponent_GridViewType_HORIZONTAL_WRAP = 1,
} RAREiTableComponent_GridViewType;

@interface RAREiTableComponent_GridViewTypeEnum : JavaLangEnum < NSCopying > {
}
+ (RAREiTableComponent_GridViewTypeEnum *)VERTICAL_WRAP;
+ (RAREiTableComponent_GridViewTypeEnum *)HORIZONTAL_WRAP;
+ (IOSObjectArray *)values;
+ (RAREiTableComponent_GridViewTypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

typedef enum {
  RAREiTableComponent_TableType_HEADER = 0,
  RAREiTableComponent_TableType_MAIN = 1,
  RAREiTableComponent_TableType_FOOTER = 2,
  RAREiTableComponent_TableType_MULTI = 3,
} RAREiTableComponent_TableType;

@interface RAREiTableComponent_TableTypeEnum : JavaLangEnum < NSCopying > {
}
+ (RAREiTableComponent_TableTypeEnum *)HEADER;
+ (RAREiTableComponent_TableTypeEnum *)MAIN;
+ (RAREiTableComponent_TableTypeEnum *)FOOTER;
+ (RAREiTableComponent_TableTypeEnum *)MULTI;
+ (IOSObjectArray *)values;
+ (RAREiTableComponent_TableTypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREiTableComponent_H_
