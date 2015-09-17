//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/ui/table/aTableHeader.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREaTableHeader_H_
#define _RAREaTableHeader_H_

@class IOSIntArray;
@class IOSObjectArray;
@class RAREBasicSelectionModel;
@class RAREColumn;
@class RAREMouseEvent;
@class RAREPaintBucket;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIImage;
@class RAREUIInsets;
@class RAREUILabelRenderer;
@class RAREUIPoint;
@class RAREaListItemRenderer;
@class RAREaTableHeader_SizeTypeEnum;
@class RAREiTableComponent_GridViewTypeEnum;
@protocol JavaLangCharSequence;
@protocol JavaUtilList;
@protocol RAREUTiFilterableList;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformItemRenderer;
@protocol RAREiTableComponent;
@protocol RAREiTableModel;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/XPContainer.h"
#include "com/appnativa/rare/ui/iImageObserver.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"
#include "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#include "java/lang/Enum.h"

@interface RAREaTableHeader : RAREXPContainer < RAREiImageObserver > {
 @public
  IOSObjectArray *columns_;
  IOSObjectArray *multiTableColumns_;
  RAREUIInsets *cellInsets_;
  RAREUIInsets *headerPadding_;
  int pressedColumn_;
  int sortColumn_;
  BOOL showHeaderMargin_;
  BOOL autoSizedColumns_;
  RAREUIColor *bottomMarginColor_;
  BOOL chagingColumns_;
  BOOL columnSelectionAllowed_;
  BOOL descending_;
  RAREiTableComponent_GridViewTypeEnum *gridViewMode_;
  RAREPaintBucket *headerCellPainter_;
  NSString *headerHeight_;
  int headerHeightNum_;
  BOOL headerTracksSections_;
  int iconPadding_;
  RAREUIColor *marginColor_;
  int measuredHeight_;
  RAREUIDimension *preferredSize_;
  RAREPaintBucket *pressedHeaderPainter_;
  BOOL propertyTabe_;
  id<JavaUtilList> propertyTableActions_;
  RAREColumn *propertyTableHeaderColumnDescription_;
  RAREUILabelRenderer *renderingComponent_;
  RAREBasicSelectionModel *selectionModel_;
  int selectionPaintEndCol_;
  int selectionPaintStartCol_;
  BOOL sortingAllowed_;
  RAREUIDimension *computeSize_;
  id<RAREUTiFilterableList> originalItems_;
  int flatCount_;
  id<RAREUTiFilterableList> wrappRows_;
  BOOL paintLeftMargin_;
  BOOL paintRightMargin_;
  int lastVisibleColumn_;
  int firstVisibleColumn_;
}

+ (int)columnSizePad;
+ (int *)columnSizePadRef;
+ (id<RAREiPlatformIcon>)sort_asc;
+ (void)setSort_asc:(id<RAREiPlatformIcon>)sort_asc;
+ (id<RAREiPlatformIcon>)sort_dsc;
+ (void)setSort_dsc:(id<RAREiPlatformIcon>)sort_dsc;
- (id)init;
- (void)setMultiTableColumnsWithJavaUtilList:(id<JavaUtilList>)columns;
- (IOSObjectArray *)getMultiTableColumns;
- (void)addSpaceWithRAREaListItemRenderer:(RAREaListItemRenderer *)renderer
                                  withInt:(int)extra;
- (int)calculateColumnWidthWithRAREiTableModel:(id<RAREiTableModel>)tm
                                       withInt:(int)col
                                withRAREColumn:(RAREColumn *)c
                                       withInt:(int)maxRows
                           withRAREUIDimension:(RAREUIDimension *)size
             withRAREaTableHeader_SizeTypeEnum:(RAREaTableHeader_SizeTypeEnum *)type;
- (void)clearSelection;
- (BOOL)columnInRowClickedWithInt:(int)row
                          withInt:(int)col;
+ (IOSObjectArray *)createGridColumnsWithRAREColumnArray:(IOSObjectArray *)columns
                                        withJavaUtilList:(id<JavaUtilList>)rows;
- (void)dispose;
- (BOOL)handleGridViewWithInt:(int)width
                      withInt:(int)height
                      withInt:(int)rowHeght
                  withBoolean:(BOOL)contentsChanged;
- (BOOL)handleMouseReleaseWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)imageLoadedWithRAREUIImage:(RAREUIImage *)image;
- (BOOL)paintColumnWithRAREColumn:(RAREColumn *)col
        withRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                        withFloat:(float)x
                        withFloat:(float)y
                        withFloat:(float)width
                        withFloat:(float)height;
- (BOOL)paintColumnWithInt:(int)i
 withRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                 withFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height;
- (void)reduceColumnSizesWithRAREiPlatformItemRenderer:(id<RAREiPlatformItemRenderer>)renderer
                                               withInt:(int)toMuch;
- (void)revalidate;
- (BOOL)sizeColumnsToFitTableData;
- (void)sortWithRAREColumn:(RAREColumn *)c;
- (BOOL)wantsLongPress;
+ (id<RAREUTiFilterableList>)wrapItemsWithRAREUTiFilterableList:(id<RAREUTiFilterableList>)rows
                                                        withInt:(int)count;
- (void)setBottomMarginColorWithRAREUIColor:(RAREUIColor *)bottomMarginColor;
- (void)setColumnIconWithInt:(int)col
       withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setColumnSelectionAllowedWithBoolean:(BOOL)columnSelectionAllowed;
- (void)setColumnTitleWithInt:(int)col
     withJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)setColumnVisibleWithInt:(int)col
                    withBoolean:(BOOL)visible;
- (void)setColumnVisibleExWithInt:(int)col
                      withBoolean:(BOOL)visible;
- (void)setColumnsWithJavaUtilList:(id<JavaUtilList>)columns;
- (void)setFontWithRAREUIFont:(RAREUIFont *)f;
- (void)setGridViewTypeWithRAREiTableComponent_GridViewTypeEnum:(RAREiTableComponent_GridViewTypeEnum *)gridViewMode;
- (void)setHeaderCellPainterWithRAREPaintBucket:(RAREPaintBucket *)headerPainter;
- (void)setHeaderHeightWithNSString:(NSString *)height;
- (void)setHeaderTracksSectionsWithBoolean:(BOOL)headerTracksSections;
- (void)setMarginColorWithRAREUIColor:(RAREUIColor *)marginColor;
- (void)setPressedHeaderPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setPropertyTabeWithBoolean:(BOOL)propertyTabe;
- (void)setPropertyTableActionsWithJavaUtilList:(id<JavaUtilList>)propertyTableActions;
- (void)setPropertyTableHeaderColumnDescriptionWithRAREColumn:(RAREColumn *)propertyTableHeaderColumnDescription;
- (void)setSelectedIndexWithInt:(int)index;
- (void)setSelectedIndicesWithIntArray:(IOSIntArray *)indices;
- (void)setShowHeaderMarginWithBoolean:(BOOL)showHeaderMargin;
- (void)setSortColumnWithInt:(int)sortColumn
                 withBoolean:(BOOL)descending;
- (void)setSortingAllowedWithBoolean:(BOOL)sortingAllowed;
- (RAREUIColor *)getBottomMarginColor;
- (RAREColumn *)getColumnAtWithInt:(int)index;
- (RAREColumn *)getColumnForViewAtWithInt:(int)viewColumn;
- (int)getColumnCount;
- (int)getColumnIndexAtWithFloat:(float)x
                       withFloat:(float)y;
- (IOSObjectArray *)getColumns;
- (int)getDynamicColumnsWidth;
- (int)getFirstVisibleColumnInView;
- (int)getLastVisibleColumnInView;
- (int)getFirstVisibleColumn;
- (int)getLastVisibleColumn;
- (int)getColumnXWithInt:(int)column;
- (RAREiTableComponent_GridViewTypeEnum *)getGridViewType;
- (RAREUIPoint *)getHotspotPopupLocationWithInt:(int)column;
- (RAREUIColor *)getMarginColor;
- (int)getMeasuredHeight;
- (RAREUIDimension *)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (void)getColumnSizeWithRAREiPlatformItemRenderer:(id<RAREiPlatformItemRenderer>)renderer
                                           withInt:(int)col
                                    withRAREColumn:(RAREColumn *)c
                 withRAREaTableHeader_SizeTypeEnum:(RAREaTableHeader_SizeTypeEnum *)type
                                         withFloat:(float)tableWidth
                               withRAREUIDimension:(RAREUIDimension *)size;
- (RAREUIDimension *)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
- (int)getPreferredHeight;
- (id<JavaUtilList>)getPropertyTableActions;
- (RAREColumn *)getPropertyTableHeaderColumnDescription;
- (int)getSelectedColumn;
- (int)getSelectedColumnCount;
- (IOSIntArray *)getSelectedColumnIndices;
- (int)getSelectionPaintEndColumn;
- (int)getSelectionPaintStartColumn;
- (int)getGridColumnWidthWithRAREUIDimension:(RAREUIDimension *)reuseableSize;
- (int)getOriginalsGridViewsRowCount;
- (void)getSizeWithRAREUIDimension:(RAREUIDimension *)size
                       withBoolean:(BOOL)minimum
                         withFloat:(float)tableWidth;
- (int)getSpanWidthWithInt:(int)start
                   withInt:(int)span;
- (id<RAREiTableComponent>)getTableComponent;
- (id<RAREiPlatformComponent>)getTableComponentEx;
- (int)getVisibleColumnCount;
- (int)getRowHeightWithInt:(int)row
withRAREiPlatformItemRenderer:(id<RAREiPlatformItemRenderer>)renderer
                   withInt:(int)defaultHeight;
- (int)getWidth;
- (BOOL)isAutoSizedColumns;
- (BOOL)isColumnSelectionAllowed;
- (BOOL)isColumnVisibleWithInt:(int)col;
- (BOOL)isGridView;
- (BOOL)isHeaderTracksSections;
- (BOOL)isPropertyTabe;
- (BOOL)isShowHeaderMargin;
- (BOOL)isSortingAllowed;
- (int)calculateMinHeight;
- (BOOL)handleMousePressWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)repaintColumnWithInt:(int)col;
- (int)getColumnIndexForClickedColumnWithInt:(int)col;
- (void)resetSelectionPaintColumns;
- (void)resetTableModelWithRAREUTiFilterableList:(id<RAREUTiFilterableList>)rows;
- (void)setupDafaultPainter;
- (void)tableHadInteraction;
- (void)updateGridColumnWidthsWithInt:(int)width;
- (void)setColumnPressedWithInt:(int)col
                    withBoolean:(BOOL)pressed;
- (void)setColumnsEx;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size;
- (RAREPaintBucket *)getPressedPainter;
- (id<RAREiPlatformIcon>)getSortIconWithBoolean:(BOOL)descending;
- (IOSObjectArray *)getWrappedColumnsWithInt:(int)count;
- (BOOL)isPaintRightMargin;
- (void)setPaintRightMarginWithBoolean:(BOOL)paintRightMargin;
- (BOOL)isPaintLeftMargin;
- (void)setPaintLeftMarginWithBoolean:(BOOL)paintLeftMargin;
- (void)copyAllFieldsTo:(RAREaTableHeader *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTableHeader, columns_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, multiTableColumns_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, cellInsets_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, headerPadding_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, bottomMarginColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, gridViewMode_, RAREiTableComponent_GridViewTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, headerCellPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, headerHeight_, NSString *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, marginColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, preferredSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, pressedHeaderPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, propertyTableActions_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREaTableHeader, propertyTableHeaderColumnDescription_, RAREColumn *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, renderingComponent_, RAREUILabelRenderer *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, selectionModel_, RAREBasicSelectionModel *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, computeSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREaTableHeader, originalItems_, id<RAREUTiFilterableList>)
J2OBJC_FIELD_SETTER(RAREaTableHeader, wrappRows_, id<RAREUTiFilterableList>)

typedef RAREaTableHeader ComAppnativaRareUiTableATableHeader;

typedef enum {
  RAREaTableHeader_SizeType_MIN = 0,
  RAREaTableHeader_SizeType_PREFERRED = 1,
  RAREaTableHeader_SizeType_FIT = 2,
} RAREaTableHeader_SizeType;

@interface RAREaTableHeader_SizeTypeEnum : JavaLangEnum < NSCopying > {
}
+ (RAREaTableHeader_SizeTypeEnum *)MIN;
+ (RAREaTableHeader_SizeTypeEnum *)PREFERRED;
+ (RAREaTableHeader_SizeTypeEnum *)FIT;
+ (IOSObjectArray *)values;
+ (RAREaTableHeader_SizeTypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface RAREaTableHeader_MouseListener : NSObject < RAREiMouseListener, RAREiMouseMotionListener > {
 @public
  RAREaTableHeader *this$0_;
  RAREUIPoint *downPoint_;
  float downY_;
  int originalPressedColumn_;
  float slop_;
}

- (id)initWithRAREaTableHeader:(RAREaTableHeader *)outer$;
- (void)mouseDraggedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e;
- (BOOL)wantsLongPress;
- (BOOL)wantsMouseMovedEvents;
- (void)copyAllFieldsTo:(RAREaTableHeader_MouseListener *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTableHeader_MouseListener, this$0_, RAREaTableHeader *)
J2OBJC_FIELD_SETTER(RAREaTableHeader_MouseListener, downPoint_, RAREUIPoint *)

#endif // _RAREaTableHeader_H_
