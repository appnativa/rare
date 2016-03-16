//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/aPlatformTableBasedView.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaPlatformTableBasedView_H_
#define _RAREaPlatformTableBasedView_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaUtilArrayList;
@class RAREAppleGraphics;
@class RAREColumn;
@class RAREListSynchronizer;
@class RAREPaintBucket;
@class RARERenderableDataItem;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_OrientationEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RAREScrollBarView;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIImage;
@class RAREUIPoint;
@class RAREUIRectangle;
@class RAREUIScrollingEdgePainter;
@class RAREUTIdentityArrayList;
@class RAREUTIntList;
@class RAREView;
@class RAREaListItemRenderer;
@class RAREaPlatformTableBasedView_ContentView;
@class RAREiListView_EditingModeEnum;
@protocol JavaLangCharSequence;
@protocol JavaUtilMap;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiTreeItem;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/aTableBasedView.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/event/iDataModelListener.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/iScrollerSupport.h"

@interface RAREaPlatformTableBasedView : RAREaTableBasedView < RAREiDataModelListener, RAREiScrollerSupport > {
 @public
  int dataOffset_;
  JavaUtilArrayList *rows_;
  BOOL useEditingAnimation_;
  RAREUIImage *checkedImage_;
  BOOL needsContentView_;
  BOOL showHorizontalGridLines_;
  BOOL showVertivalGridLines_;
  RAREUIImage *uncheckedImage_;
  RAREListSynchronizer *listSynchronizer_;
  RAREUIScrollingEdgePainter *scrollingEdgePainter_;
  int offsetX_;
  int offsetY_;
  RAREScrollBarView *hsb_;
  RAREScrollBarView *vsb_;
  RAREUTIdentityArrayList *hScrollSynchronizer_;
  RAREUTIdentityArrayList *vScrollSynchronizer_;
  BOOL inOnScrollChanged_;
}

+ (RARERenderableDataItem *)NULL_ITEM;
- (id)initWithId:(id)nsview;
- (id<RAREiScrollerSupport>)getScrollerSupport;
- (BOOL)isAtBottomEdge;
- (BOOL)isAtLeftEdge;
- (BOOL)isAtRightEdge;
- (BOOL)isAtTopEdge;
- (void)addSelectionIndexWithInt:(int)index;
- (void)clearSelections;
- (void)clearSelectionsWithBoolean:(BOOL)notify;
- (void)setScrollingEdgePainterWithRAREUIScrollingEdgePainter:(RAREUIScrollingEdgePainter *)painter;
- (RAREUIScrollingEdgePainter *)getScrollingEdgePainter;
- (void)columnSelectedWithInt:(int)row
                      withInt:(int)column;
+ (id)createUITableCellView;
- (void)editCellWithInt:(int)row
                withInt:(int)col;
- (void)editRowExWithInt:(int)index;
- (void)refreshItems;
- (void)removeSelectionWithInt:(int)index;
- (RAREUIRectangle *)getCellRectWithInt:(int)row
                                withInt:(int)col
                            withBoolean:(BOOL)includeMargin;
- (void)renderItemWithInt:(int)row
withRARERenderableDataItem:(RARERenderableDataItem *)item
withRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view
              withBoolean:(BOOL)isSelected
              withBoolean:(BOOL)isPressed
        withRAREiTreeItem:(id<RAREiTreeItem>)ti;
- (void)clickRowWithInt:(int)index;
- (void)repaintRowWithInt:(int)index;
- (void)repaintVisibleRows;
- (void)removePressedHilightWithInt:(int)index;
- (void)repaintRowsWithInt:(int)row0
                   withInt:(int)row1;
- (int)rowAtPointWithFloat:(float)x
                 withFloat:(float)y;
- (void)rowChangedWithInt:(int)index;
- (void)rowsChangedWithIntArray:(IOSIntArray *)index;
- (void)rowsChangedWithInt:(int)firstRow
                   withInt:(int)lastRow;
- (void)scrollToLeftEdge;
- (void)scrollToRightEdge;
- (void)scrollToTopEdge;
- (void)scrollToBottomEdge;
- (void)moveLeftRightWithBoolean:(BOOL)left
                     withBoolean:(BOOL)block;
- (void)moveUpDownWithBoolean:(BOOL)up
                  withBoolean:(BOOL)block;
- (void)scrollRowToTopWithInt:(int)index;
- (void)scrollRowToBottomWithInt:(int)index;
- (void)setContentOffsetWithFloat:(float)x
                        withFloat:(float)y;
- (void)scrollRowToVisibleWithInt:(int)index;
- (void)selectAll;
- (void)setAccessoryTypeWithNSString:(NSString *)style
                         withBoolean:(BOOL)editing;
- (void)setAutoSizeRowsWithBoolean:(BOOL)autoSizeRows;
- (void)setCellStyleWithNSString:(NSString *)style;
- (void)setEditingWithBoolean:(BOOL)edit
                  withBoolean:(BOOL)animated;
- (void)setEditingModeWithRAREiListView_EditingModeEnum:(RAREiListView_EditingModeEnum *)mode;
- (void)setSelectableWithBoolean:(BOOL)selectable;
- (void)setSelectedIndexWithInt:(int)index;
- (void)setSelectedIndexesWithIntArray:(IOSIntArray *)indices;
- (void)setSeparatorStyleWithNSString:(NSString *)style;
- (void)setShowVertivalGridLinesWithBoolean:(BOOL)showVertivalGridLines;
- (void)setShowsHorizontalScrollIndicatorWithBoolean:(BOOL)show;
- (void)setShowsVerticalScrollIndicatorWithBoolean:(BOOL)show;
- (void)setUseEditingAnimationWithBoolean:(BOOL)useEditingAnimation;
- (RAREUIRectangle *)getCellBoundsWithInt:(int)row0
                                  withInt:(int)row1;
- (int)getFirstVisibleIndex;
- (int)getLastVisibleIndex;
- (int)getRowHeight;
- (int)getSelectedColumn;
- (int)getSelectedIndex;
- (RAREUIPoint *)getContentOffset;
- (RAREUIDimension *)getContentSize;
- (int)getSelectedIndexCount;
- (IOSIntArray *)getSelectedIndexes;
- (BOOL)isColumnSelectedWithInt:(int)col;
- (BOOL)isColumnSelectionAllowed;
- (BOOL)isEditing;
- (BOOL)isMultipleSelectionAllowed;
- (BOOL)isRowSelectedWithInt:(int)index;
- (BOOL)isScrollView;
- (BOOL)isScrolling;
- (BOOL)isShowVertivalGridLines;
- (BOOL)isUseEditingAnimation;
- (void)structureChangedWithId:(id)source;
+ (id)createContentViewProxy;
- (void)disposeEx;
- (void)viewDidScrollWithFloat:(float)x
                     withFloat:(float)y;
+ (void)disposeOfRenderersWithId:(id)renderers;
- (int)getPressedColumn;
- (void)someDataChanged;
- (void)itemDeselectedWithInt:(int)index;
- (void)itemSelectedWithInt:(int)index;
- (void)layoutItemViewWithId:(id)parentUIView
                     withInt:(int)viewIndex
                     withInt:(int)x
                     withInt:(int)width
                     withInt:(int)height;
- (void)moveWithInt:(int)from
            withInt:(int)to;
- (void)moveWithRAREUTIntList:(RAREUTIntList *)from
                      withInt:(int)to;
- (void)reloadVisibleRows;
- (void)removeWithInt:(int)index;
- (void)removeWithRAREUTIntList:(RAREUTIntList *)indexes;
- (void)setupNewRenderingCellWithId:(id)nativeView;
- (RAREPaintBucket *)getSelectionPainter;
- (void)setMultipleSelectionWithBoolean:(BOOL)multiple;
- (void)setRowHeightExWithInt:(int)height;
- (RAREaTableBasedView_RowView *)getViewForRowWithInt:(int)index;
- (BOOL)isTable;
- (RAREListSynchronizer *)getListSynchronizer;
- (void)setListSynchronizerWithRAREListSynchronizer:(RAREListSynchronizer *)listSynchronizer;
- (void)setScrollBarViewsWithRAREScrollBarView:(RAREScrollBarView *)hsb
                         withRAREScrollBarView:(RAREScrollBarView *)vsb;
- (void)setHeaderViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss;
- (void)setFooterViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss;
- (void)setRowHeaderViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss;
- (void)setRowFooterViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)rowFooterView;
- (void)syncScrollingWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss
                                  withBoolean:(BOOL)vertical;
- (void)turnOffScrollBarVisibilityWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss
                                               withBoolean:(BOOL)horizontal;
- (void)copyAllFieldsTo:(RAREaPlatformTableBasedView *)other;
@end

J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView, rows_, JavaUtilArrayList *)
J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView, checkedImage_, RAREUIImage *)
J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView, uncheckedImage_, RAREUIImage *)
J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView, listSynchronizer_, RAREListSynchronizer *)
J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView, scrollingEdgePainter_, RAREUIScrollingEdgePainter *)
J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView, hsb_, RAREScrollBarView *)
J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView, vsb_, RAREScrollBarView *)
J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView, hScrollSynchronizer_, RAREUTIdentityArrayList *)
J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView, vScrollSynchronizer_, RAREUTIdentityArrayList *)

typedef RAREaPlatformTableBasedView ComAppnativaRarePlatformAppleUiViewAPlatformTableBasedView;

@interface RAREaPlatformTableBasedView (NativeMethods)
- (void)setAllowsSelectionDuringEditingWithBoolean:(BOOL)allowsSelectionDuringEditing;
@end

@interface RAREaPlatformTableBasedView_RowViewEx : RAREaTableBasedView_RowView {
 @public
  __weak RAREaPlatformTableBasedView *this$1_;
  id<RAREiPlatformRenderingComponent> renderingComponent_;
  RAREaPlatformTableBasedView_ContentView *contentView_;
}

- (id)initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)outer$;
- (id)initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)outer$
                                   withId:(id)proxy;
- (void)hideRowEditingComponentWithBoolean:(BOOL)animate;
- (void)showRowEditingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
                                              withBoolean:(BOOL)animate;
- (void)setAccessoryTypeWithNSString:(NSString *)style;
- (void)setAccessoryViewWithId:(id)view;
- (void)setBackgroundViewWithId:(id)view;
- (void)setCustomPropertiesWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)setCustomRenderingComponentWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)renderingComponent;
- (void)setDetailTextLabelWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setEditingWithBoolean:(BOOL)editing;
- (void)setEditingAccessoryTypeWithNSString:(NSString *)style;
- (void)setEditingAccessoryViewWithId:(id)view;
- (void)setEditingImageWithRAREUIImage:(RAREUIImage *)image;
- (void)setFontWithRAREUIFont:(RAREUIFont *)font;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setIconGapWithInt:(int)iconGap;
- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)iconPosition;
- (void)setImageWithRAREUIImage:(RAREUIImage *)image;
- (void)setMarginWithFloat:(float)top
                 withFloat:(float)right
                 withFloat:(float)bottom
                 withFloat:(float)left;
- (void)setRenderingViewWithRAREView:(RAREView *)view;
- (void)setSelectedBackgroundViewWithId:(id)view;
- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setTextAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)hal
                          withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)val;
- (void)setWordWrapWithBoolean:(BOOL)wrap;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth;
- (BOOL)isPressed;
- (BOOL)isSelected;
- (BOOL)isWordWrap;
- (void)setForegroundColorExWithRAREUIColor:(RAREUIColor *)fg;
- (id)getContentView;
- (void)copyAllFieldsTo:(RAREaPlatformTableBasedView_RowViewEx *)other;
@end

J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView_RowViewEx, renderingComponent_, id<RAREiPlatformRenderingComponent>)
J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView_RowViewEx, contentView_, RAREaPlatformTableBasedView_ContentView *)

@interface RAREaPlatformTableBasedView_TableRowView : RAREaPlatformTableBasedView_RowViewEx {
 @public
  __weak RAREaPlatformTableBasedView *this$2_;
  RAREUIDimension *prefSize_;
}

- (id)initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)outer$;
- (id)initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)outer$
                                   withId:(id)proxy;
- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect;
- (void)renderWithRAREView:(RAREView *)parent
withRAREiPlatformComponent:(id<RAREiPlatformComponent>)list
 withRAREaListItemRenderer:(RAREaListItemRenderer *)lr
                   withInt:(int)row
withRARERenderableDataItem:(RARERenderableDataItem *)rowItem
       withRAREColumnArray:(IOSObjectArray *)columns
               withBoolean:(BOOL)isSelected
               withBoolean:(BOOL)isPressed
         withRAREiTreeItem:(id<RAREiTreeItem>)ti
              withIntArray:(IOSIntArray *)viewPositions;
- (void)disposeEx;
- (void)dealloc;
- (void)copyAllFieldsTo:(RAREaPlatformTableBasedView_TableRowView *)other;
@end

J2OBJC_FIELD_SETTER(RAREaPlatformTableBasedView_TableRowView, prefSize_, RAREUIDimension *)

@interface RAREaPlatformTableBasedView_ContentView : RAREParentView {
}

- (id)initWithId:(id)proxy;
- (BOOL)isMouseTransparent;
- (void)setCellRenderersWithId:(id)renderers;
- (id)getCellRenderers;
- (id)getViewAtWithInt:(int)index;
- (float)getViewWidthWithId:(id)proxy;
- (float)getViewXWithId:(id)proxy;
@end

@interface RAREaPlatformTableBasedView_UITableCellViewRenderingComponent : RAREActionComponent < NSCopying, RAREiPlatformRenderingComponent > {
 @public
  __weak RAREaPlatformTableBasedView *this$0_;
}

- (id)initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)outer$;
- (void)columnMovedWithInt:(int)from
                   withInt:(int)to;
- (void)disposeOfRenderers;
- (void)clearRenderer;
- (id)initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)outer$
withRAREaPlatformTableBasedView_RowViewEx:(RAREaPlatformTableBasedView_RowViewEx *)view;
- (id)createNewNativeView;
- (id<RAREiPlatformRenderingComponent>)newCopy OBJC_METHOD_FAMILY_NONE;
- (void)prepareForReuseWithInt:(int)row
                       withInt:(int)col;
- (void)setBoundsWithInt:(int)x
                 withInt:(int)y
                 withInt:(int)w
                 withInt:(int)h;
- (void)setColumnWidthWithInt:(int)width;
- (void)setNativeViewWithId:(id)proxy;
- (void)setOptionsWithJavaUtilMap:(id<JavaUtilMap>)options;
- (void)setOrientationWithRARERenderableDataItem_OrientationEnum:(RARERenderableDataItem_OrientationEnum *)o;
- (void)setRenderingViewWithRAREView:(RAREView *)view;
- (void)setRowViewWithRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view;
- (void)setViewWithRAREView:(RAREView *)view;
- (id<RAREiPlatformComponent>)getComponent;
- (id<RAREiPlatformComponent>)getComponentWithJavaLangCharSequence:(id<JavaLangCharSequence>)value
                                        withRARERenderableDataItem:(RARERenderableDataItem *)item;
- (id<RAREiPlatformComponent>)getComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)list
                                                              withId:(id)value
                                          withRARERenderableDataItem:(RARERenderableDataItem *)item
                                                             withInt:(int)row
                                                         withBoolean:(BOOL)isSelected
                                                         withBoolean:(BOOL)hasFocus
                                                      withRAREColumn:(RAREColumn *)col
                                          withRARERenderableDataItem:(RARERenderableDataItem *)rowItem
                                                         withBoolean:(BOOL)handleAll;
@end

#endif // _RAREaPlatformTableBasedView_H_
