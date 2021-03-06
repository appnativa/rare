//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/aTableBasedView.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaTableBasedView_H_
#define _RAREaTableBasedView_H_

@class IOSIntArray;
@class JavaBeansPropertyChangeEvent;
@class RAREActionEvent;
@class RAREAppleGraphics;
@class RAREListItemRenderer;
@class RAREMouseEvent;
@class RAREPaintBucket;
@class RARERenderableDataItem;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIImage;
@class RAREUIRectangle;
@class RAREUIStroke;
@class RAREUTIntList;
@class RAREView;
@class RAREaListItemRenderer;
@class RAREaTableBasedView_RowView;
@class RAREiListHandler_SelectionModeEnum;
@class RAREiListHandler_SelectionTypeEnum;
@class RAREiListView_EditingModeEnum;
@protocol JavaUtilList;
@protocol RAREiItemChangeListener;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformListDataModel;
@protocol RAREiScrollerSupport;
@protocol RAREiTreeItem;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/event/iDataModelListener.h"
#include "com/appnativa/rare/ui/iListView.h"

#define RAREaTableBasedView_PAD_SIZE 2

@interface RAREaTableBasedView : RAREParentView < RAREiActionListener, RAREiDataModelListener, RAREiListView > {
 @public
  IOSIntArray *EMPTY_ARRAY_;
  BOOL selectable_;
  BOOL fixedRowSize_;
  BOOL editable_;
  BOOL showLastDivider_;
  BOOL extendBackgroundRendering_;
  BOOL allowsSelectionDuringEditing_;
  id<RAREiActionListener> actionListener_;
  RAREUIColor *alternatingColor_;
  BOOL alternatingColumns_;
  BOOL autoEndEditing_;
  BOOL centerEditingComponentVertically_;
  id<RAREiItemChangeListener> changeListener_;
  int checkboxHeight_;
  int checkboxWidth_;
  BOOL deletingAllowed_;
  RAREUIColor *dividerLineColor_;
  RAREUIStroke *dividerStroke_;
  BOOL draggingAllowed_;
  RAREiListView_EditingModeEnum *editingMode_;
  BOOL editingSelectionAllowed_;
  BOOL editingSwipingAllowed_;
  int effectiveMinRowHeight_;
  int indicatorHeight_;
  int indicatorWidth_;
  RAREListItemRenderer *itemRenderer_;
  int leftOffset_;
  __weak id<RAREiPlatformListDataModel> listModel_;
  RAREPaintBucket *lostFoucsSelectionPainter_;
  int minRowHeight_;
  int minVisibleRows_;
  int popupMenuIndex_;
  RAREPaintBucket *pressedPainter_;
  int rightOffset_;
  int rowHeight_;
  RAREPaintBucket *selectionPainter_;
  RAREiListHandler_SelectionTypeEnum *selectionType_;
  BOOL showDivider_;
  BOOL singleClickAction_;
  int visibleRows_;
  BOOL keepSelectionVisible_;
  RAREUIDimension *rowHeightCalSize_;
  float oldWidth_aTableBasedView_;
}

+ (int)ICON_GAP;
+ (int)PAD_SIZE;
+ (int)SELECTION_ICON_SIZE;
+ (int)INDICATOR_SLOP;
+ (int *)INDICATOR_SLOPRef;
- (id)initWithId:(id)nsview;
- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)w
                 withFloat:(float)h;
- (BOOL)isKeepSelectionVisible;
- (void)setKeepSelectionVisibleWithBoolean:(BOOL)keepSelectionVisible;
- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (void)addSelectionIndexWithInt:(int)index;
- (int)getSelectedIndexCount;
- (IOSIntArray *)getSelectedIndexes;
- (IOSIntArray *)getCheckedIndexes;
- (void)setContextMenuIndexWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)clearContextMenuIndex;
- (void)contentsChangedWithId:(id)source;
- (void)contentsChangedWithId:(id)source
                      withInt:(int)index0
                      withInt:(int)index1;
- (void)editCellWithInt:(int)row
                withInt:(int)col;
- (void)intervalAddedWithId:(id)source
                    withInt:(int)index0
                    withInt:(int)index1;
- (void)intervalRemovedWithId:(id)source
                      withInt:(int)index0
                      withInt:(int)index1
             withJavaUtilList:(id<JavaUtilList>)removed;
- (void)paintRowWithRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view
                          withRAREAppleGraphics:(RAREAppleGraphics *)g
                     withRARERenderableDataItem:(RARERenderableDataItem *)item
                            withRAREUIRectangle:(RAREUIRectangle *)rect
                              withRAREiTreeItem:(id<RAREiTreeItem>)ti;
- (void)propertyChangeWithJavaBeansPropertyChangeEvent:(JavaBeansPropertyChangeEvent *)e;
- (void)refreshItems;
- (void)repaintRowWithInt:(int)index;
- (void)repaintVisibleRows;
- (void)removePressedHilightWithInt:(int)index;
- (void)reloadVisibleRows;
- (void)someDataChanged;
- (void)repaintRowWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (int)rowAtPointWithFloat:(float)x
                 withFloat:(float)y;
- (void)rowChangedWithInt:(int)index;
- (void)rowChangedWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)rowsChangedWithInt:(int)firstRow
                   withInt:(int)lastRow;
- (void)rowsDeletedWithInt:(int)firstRow
                   withInt:(int)lastRow;
- (void)rowsInsertedWithInt:(int)firstRow
                    withInt:(int)lastRow;
- (void)structureChangedWithId:(id)source;
- (void)setActionListenerWithRAREiActionListener:(id<RAREiActionListener>)actionListener;
- (void)setAllowsSelectionDuringEditingWithBoolean:(BOOL)allowsSelectionDuringEditing;
- (void)setAlternatingColorWithRAREUIColor:(RAREUIColor *)alternatingColor;
- (void)setAlternatingColumnsWithBoolean:(BOOL)alternatingColumns;
- (void)setAlternatingRowColorWithRAREUIColor:(RAREUIColor *)alternatingColor;
- (void)setAutoEndEditingWithBoolean:(BOOL)autoEndEditing;
- (void)setAutoHilightWithBoolean:(BOOL)autoHilight;
- (void)makeSelectionVisible;
- (void)setDividerLineWithRAREUIColor:(RAREUIColor *)color
                     withRAREUIStroke:(RAREUIStroke *)stroke;
- (void)setEditableWithBoolean:(BOOL)editable;
- (void)setEditingModeWithRAREiListView_EditingModeEnum:(RAREiListView_EditingModeEnum *)mode;
- (void)setEditingSwipingAllowedWithBoolean:(BOOL)editingSwipingAllowed;
- (void)setExtendBackgroundRenderingWithBoolean:(BOOL)extendBackgroundRendering;
- (void)setFlingThresholdWithInt:(int)i;
- (void)setItemRendererWithRAREListItemRenderer:(RAREListItemRenderer *)lr;
- (void)setListModelWithRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)listModel;
- (void)setMinRowHeightWithInt:(int)min;
- (void)setMinimumVisibleRowCountWithInt:(int)rows;
- (void)setRowHeightWithInt:(int)height;
- (void)setSelectFlingedWithBoolean:(BOOL)b;
- (void)setSelectableWithBoolean:(BOOL)selectable;
- (void)setSelectedIndexWithInt:(int)index;
- (void)setSelectedIndexesWithIntArray:(IOSIntArray *)indices;
- (void)setSelectedItemWithRARERenderableDataItem:(RARERenderableDataItem *)value;
- (void)setSelectionChangeListenerWithRAREiItemChangeListener:(id<RAREiItemChangeListener>)changeListener;
- (void)setSelectionModeWithRAREiListHandler_SelectionModeEnum:(RAREiListHandler_SelectionModeEnum *)selectionMode;
- (void)setSelectionTypeWithRAREiListHandler_SelectionTypeEnum:(RAREiListHandler_SelectionTypeEnum *)type;
- (void)setShowDividerWithBoolean:(BOOL)show;
- (void)setShowLastDividerWithBoolean:(BOOL)show;
- (void)setVisibleRowCountWithInt:(int)rows;
- (void)setWholeViewFlingWithBoolean:(BOOL)b;
- (id<RAREiActionListener>)getActionListener;
- (RAREUIColor *)getAlternatingColor;
- (void)updateForColorChange;
- (RAREUIColor *)getAlternatingRowColor;
- (id<RAREiItemChangeListener>)getChangeListener;
- (int)getHilightedIndex;
- (id<RAREiPlatformComponent>)getListComponent;
- (int)getMinRowHeight;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth;
- (int)getContextMenuIndex;
- (int)getRowHeightWithInt:(int)row
                 withFloat:(float)maxWidth;
- (int)getRowCount;
- (int)getRowHeight;
- (int)getSelectedIndex;
- (RAREiListHandler_SelectionTypeEnum *)getSelectionType;
- (int)getVisibleRowCount;
- (BOOL)isAllowsSelectionDuringEditing;
- (BOOL)isAlternatingColumns;
- (BOOL)isAutoEndEditing;
- (BOOL)isAutoHilight;
- (BOOL)isEditingSwipingAllowed;
- (BOOL)isExtendBackgroundRendering;
- (BOOL)isMultipleSelectionAllowed;
- (BOOL)isRowSelectedWithInt:(int)row;
- (BOOL)isRowSizeFixed;
- (BOOL)isSelectable;
- (BOOL)checkForCellHotspotWithInt:(int)row
                         withFloat:(float)x
                         withFloat:(float)y
                         withFloat:(float)width
                         withFloat:(float)height;
- (void)disposeEx;
- (void)mouseClickedWithRAREMouseEvent:(RAREMouseEvent *)e
                           withBoolean:(BOOL)mouseDown
                             withFloat:(float)width
                             withFloat:(float)height;
- (void)resetHeightInfoWithInt:(int)index0
                       withInt:(int)index1;
- (void)selectionChangedWithInt:(int)oldIndex
                        withInt:(int)newIndex;
- (void)selectionChangedWithRAREUTIntList:(RAREUTIntList *)oldIndexes
                        withRAREUTIntList:(RAREUTIntList *)newIndexes;
- (void)setupNewRenderingCellWithId:(id)nativeView;
- (void)setMultipleSelectionWithBoolean:(BOOL)multiple;
- (void)setRowHeightExWithInt:(int)height;
- (RAREPaintBucket *)getPressedPainter;
- (float)getSelectionPaintEndXWithFloat:(float)currentEndX;
- (float)getSelectionPaintStartXWithFloat:(float)currentStartX;
- (RAREaTableBasedView_RowView *)getViewForRowWithInt:(int)index;
- (BOOL)isSelectableWithInt:(int)row
 withRARERenderableDataItem:(RARERenderableDataItem *)item
          withRAREiTreeItem:(id<RAREiTreeItem>)ti;
- (BOOL)isSelectableWithInt:(int)row
                    withInt:(int)col
 withRARERenderableDataItem:(RARERenderableDataItem *)item;
- (int)getFirstVisibleIndex;
- (RAREaListItemRenderer *)getItemRenderer;
- (int)getLastVisibleIndex;
- (id<RAREiScrollerSupport>)getScrollerSupport;
- (BOOL)isSingleClickAction;
- (void)scrollRowToBottomWithInt:(int)param0;
- (void)scrollRowToTopWithInt:(int)param0;
- (void)scrollRowToVisibleWithInt:(int)param0;
- (void)setSingleClickActionWithBoolean:(BOOL)param0;
- (void)copyAllFieldsTo:(RAREaTableBasedView *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTableBasedView, EMPTY_ARRAY_, IOSIntArray *)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, actionListener_, id<RAREiActionListener>)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, alternatingColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, changeListener_, id<RAREiItemChangeListener>)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, dividerLineColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, dividerStroke_, RAREUIStroke *)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, editingMode_, RAREiListView_EditingModeEnum *)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, itemRenderer_, RAREListItemRenderer *)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, lostFoucsSelectionPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, pressedPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, selectionPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, selectionType_, RAREiListHandler_SelectionTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaTableBasedView, rowHeightCalSize_, RAREUIDimension *)

typedef RAREaTableBasedView ComAppnativaRarePlatformAppleUiViewATableBasedView;

@interface RAREaTableBasedView_RowView : RAREParentView {
 @public
  __weak RAREaTableBasedView *this$0_;
  int indent_;
  id<RAREiPlatformIcon> checkboxIcon_;
  int column_;
  id<RAREiPlatformIcon> indicator_;
  int row_;
  BOOL checkedInEditMode_;
  BOOL editing_;
}

- (id)initWithRAREaTableBasedView:(RAREaTableBasedView *)outer$
                           withId:(id)proxy;
- (void)hideRowEditingComponentWithBoolean:(BOOL)animate;
- (void)paintWithRAREAppleGraphics:(RAREAppleGraphics *)g
               withRAREUIRectangle:(RAREUIRectangle *)rect;
- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect;
- (RAREPaintBucket *)getBackgroundPaintWithBoolean:(BOOL)pressed
                                       withBoolean:(BOOL)selected;
- (void)showRowEditingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
                                              withBoolean:(BOOL)animate;
- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp;
- (void)setEditingWithBoolean:(BOOL)editing;
- (void)setImageWithRAREUIImage:(RAREUIImage *)image;
- (void)setNativeViewWithId:(id)proxy;
- (void)setPaintHandlerEnabledWithBoolean:(BOOL)enabled;
- (void)disposeEx;
- (void)prepareForReuseWithInt:(int)row
                       withInt:(int)col;
- (BOOL)isMouseTransparent;
- (void)setFocusListenerEnabledWithBoolean:(BOOL)enabled;
- (void)setKeyBoardHandlerEnabledWithBoolean:(BOOL)enabled;
- (void)setMouseHandlerEnabledWithBoolean:(BOOL)enabled;
- (void)setMouseMotionHandlerEnabledWithBoolean:(BOOL)enabled;
- (void)copyAllFieldsTo:(RAREaTableBasedView_RowView *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTableBasedView_RowView, checkboxIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RAREaTableBasedView_RowView, indicator_, id<RAREiPlatformIcon>)

#endif // _RAREaTableBasedView_H_
