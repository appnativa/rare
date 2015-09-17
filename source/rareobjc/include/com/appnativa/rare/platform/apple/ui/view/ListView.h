//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/ListView.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREListView_H_
#define _RAREListView_H_

@class IOSIntArray;
@class IOSObjectArray;
@class RAREActionEvent;
@class RAREAppleGraphics;
@class RARECheckListManager;
@class RAREListItemRenderer;
@class RAREMouseEvent;
@class RARERenderableDataItem;
@class RARESectionIndex;
@class RAREUIAction;
@class RAREUIColor;
@class RAREUIComponentRenderer;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIInsets;
@class RAREUIRectangle;
@class RAREUTCharacterIndex;
@class RAREUTIntList;
@class RAREaTableBasedView_RowView;
@protocol RAREListView_iIOSRenderingCallback;
@protocol RAREiFunctionCallback;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformListDataModel;
@protocol RAREiToolBar;
@protocol RAREiTreeItem;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/aPlatformTableBasedView.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iGestureListener.h"
#include "java/util/Comparator.h"

@interface RAREListView : RAREaPlatformTableBasedView {
 @public
  RAREUIInsets *rinsets_;
  int checkboxLeftXSlop_;
  RAREUIInsets *borderInsets_;
  RARECheckListManager *checkListManager_;
  BOOL columnSizesInitialized_;
  IOSObjectArray *editActions_;
  id<RAREiFunctionCallback> editModeNotifier_;
  id<RAREiToolBar> editToolbar_;
  BOOL editing_;
  id<RAREiPlatformComponent> editingComponent_;
  id<RAREiGestureListener> flingGestureListener_;
  BOOL linkedSelection_;
  RAREUIAction *markAll_;
  id<RAREListView_iIOSRenderingCallback> renderingCallback_;
  RAREUTCharacterIndex *characterIndex_;
  RARESectionIndex *sectionIndex_;
  RAREUIComponentRenderer *sectionRenderer_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)grouped;
- (id)initWithId:(id)proxy;
- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e
                                   withInt:(int)index;
- (void)addSelectionIndexWithInt:(int)index;
- (void)borderChangedWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)newBorder;
- (void)clearCheckedItems;
- (void)clearSelections;
- (void)editModeChangeAllMarksWithBoolean:(BOOL)mark;
- (void)hideRowEditingComponentWithBoolean:(BOOL)animate;
- (void)hideRowEditingComponentEx;
- (void)paintRowWithRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view
                          withRAREAppleGraphics:(RAREAppleGraphics *)g
                     withRARERenderableDataItem:(RARERenderableDataItem *)item
                            withRAREUIRectangle:(RAREUIRectangle *)rect
                              withRAREiTreeItem:(id<RAREiTreeItem>)ti;
- (void)renderItemWithInt:(int)row
withRARERenderableDataItem:(RARERenderableDataItem *)item
withRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view
              withBoolean:(BOOL)isSelected
              withBoolean:(BOOL)isPressed
        withRAREiTreeItem:(id<RAREiTreeItem>)ti;
- (void)renderSectionWithId:(id)contentProxy
                     withId:(id)labelProxy;
- (void)rowsDeletedWithInt:(int)firstRow
                   withInt:(int)lastRow;
- (void)rowsInsertedWithInt:(int)firstRow
                    withInt:(int)lastRow;
- (void)startEditingWithRAREUIActionArray:(IOSObjectArray *)actions
                              withBoolean:(BOOL)animate;
- (void)stopEditingWithBoolean:(BOOL)animate;
- (void)updateSectionIndex;
- (void)setCheckboxLeftXSlopWithInt:(int)checkboxLeftXSlop;
- (void)setCheckedRowsWithIntArray:(IOSIntArray *)indices;
- (void)setEditModeNotifierWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)editModeNotifier;
- (void)setEditingToolbarWithRAREiToolBar:(id<RAREiToolBar>)tb;
- (void)setIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)checked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)unchecked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)indeterminate;
- (void)setItemRendererWithRAREListItemRenderer:(RAREListItemRenderer *)lr;
- (void)setLinkSelectionWithBoolean:(BOOL)linked;
- (void)setListModelWithRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)listModel;
- (void)setPaintHandlerEnabledWithBoolean:(BOOL)enabled;
- (void)setRenderingCallbackWithRAREListView_iIOSRenderingCallback:(id<RAREListView_iIOSRenderingCallback>)cb;
- (void)setRowCheckedWithInt:(int)row
                 withBoolean:(BOOL)checked;
- (void)setRowEditingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                             withBoolean:(BOOL)centerVertically;
- (void)setSectionIndexWithRARESectionIndex:(RARESectionIndex *)sectionIndex;
- (void)setSectionIndexWithRAREUTCharacterIndex:(RAREUTCharacterIndex *)ci
                                        withInt:(int)listSize
                     withRARERenderableDataItem:(RARERenderableDataItem *)headerPrototype;
- (void)setSelectedIndexWithInt:(int)index;
- (void)setShowSectionIndexWithBoolean:(BOOL)show;
- (void)setSingleClickActionWithBoolean:(BOOL)singleClickAction;
- (int)getCheckboxLeftXSlop;
- (id<RAREiFunctionCallback>)getEditModeNotifier;
- (RAREListItemRenderer *)getItemRenderer;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (id<RAREiTreeItem>)getTreeItemWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)hasCheckedRows;
- (BOOL)isRowCheckedWithInt:(int)row;
- (BOOL)isScrollView;
- (BOOL)isSingleClickAction;
- (void)calculateOffset;
- (BOOL)canDragWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)checForLinkedSelectionWithInt:(int)row
                          withBoolean:(BOOL)selected;
- (BOOL)checkForCellHotspotWithInt:(int)row
                         withFloat:(float)x
                         withFloat:(float)y
                         withFloat:(float)width
                         withFloat:(float)height;
- (RARECheckListManager *)createCheckListManager;
- (void)disposeEx;
- (void)editModeDeleteMarkedItems;
- (int)geIndentWithInt:(int)row;
- (void)itemDeselectedWithInt:(int)index;
- (void)itemSelectedWithInt:(int)index;
- (void)moveWithInt:(int)from
            withInt:(int)to;
- (void)moveWithRAREUTIntList:(RAREUTIntList *)from
                      withInt:(int)to;
- (void)toggleCheckedStateWithInt:(int)row;
- (void)toggleEditModeItemCheckedWithInt:(int)row;
- (void)updateActions;
- (void)updateRenderInsetsForCheckBoxWithFloat:(float)left
                                     withFloat:(float)right;
- (void)setListModelExWithRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)listModel;
- (void)setRowEditingGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l;
- (int)getIndentWithInt:(int)row;
- (BOOL)isOnCheckBoxWithFloat:(float)x
                    withFloat:(float)y
                    withFloat:(float)width
                    withFloat:(float)height
                      withInt:(int)indent;
- (BOOL)isTree;
+ (id)createProxyWithBoolean:(BOOL)grouped;
- (void)copyAllFieldsTo:(RAREListView *)other;
@end

J2OBJC_FIELD_SETTER(RAREListView, rinsets_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREListView, borderInsets_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREListView, checkListManager_, RARECheckListManager *)
J2OBJC_FIELD_SETTER(RAREListView, editActions_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(RAREListView, editModeNotifier_, id<RAREiFunctionCallback>)
J2OBJC_FIELD_SETTER(RAREListView, editToolbar_, id<RAREiToolBar>)
J2OBJC_FIELD_SETTER(RAREListView, editingComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREListView, flingGestureListener_, id<RAREiGestureListener>)
J2OBJC_FIELD_SETTER(RAREListView, markAll_, RAREUIAction *)
J2OBJC_FIELD_SETTER(RAREListView, renderingCallback_, id<RAREListView_iIOSRenderingCallback>)
J2OBJC_FIELD_SETTER(RAREListView, characterIndex_, RAREUTCharacterIndex *)
J2OBJC_FIELD_SETTER(RAREListView, sectionIndex_, RARESectionIndex *)
J2OBJC_FIELD_SETTER(RAREListView, sectionRenderer_, RAREUIComponentRenderer *)

typedef RAREListView ComAppnativaRarePlatformAppleUiViewListView;

@protocol RAREListView_iIOSRenderingCallback < NSObject, JavaObject >
- (BOOL)renderItemWithInt:(int)row
withRARERenderableDataItem:(RARERenderableDataItem *)item
withRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view
              withBoolean:(BOOL)isSelected
              withBoolean:(BOOL)isPressed
        withRAREiTreeItem:(id<RAREiTreeItem>)ti;
@end

@interface RAREListView_RowEditingGestureListener : NSObject < RAREiGestureListener > {
 @public
  RAREListView *this$0_;
}

- (void)onFlingWithId:(id)view
   withRAREMouseEvent:(RAREMouseEvent *)e1
   withRAREMouseEvent:(RAREMouseEvent *)e2
            withFloat:(float)velocityX
            withFloat:(float)velocityY;
- (void)onLongPressWithId:(id)view
       withRAREMouseEvent:(RAREMouseEvent *)e;
- (void)onRotateWithId:(id)view
               withInt:(int)type
             withFloat:(float)rotation
             withFloat:(float)velocity
             withFloat:(float)focusX
             withFloat:(float)focusY;
- (void)onScaleEventWithId:(id)view
                   withInt:(int)type
                    withId:(id)sgd
                 withFloat:(float)factor;
- (id)initWithRAREListView:(RAREListView *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREListView_RowEditingGestureListener, this$0_, RAREListView *)

@interface RAREListView_SectionHeader : RAREView {
 @public
  id label_;
}

- (id)initWithId:(id)nsview
          withId:(id)label;
- (void)resetWithId:(id)contentProxy
             withId:(id)labelProxy;
- (void)setFontWithRAREUIFont:(RAREUIFont *)font;
- (void)setForegroundColorExWithRAREUIColor:(RAREUIColor *)fg;
- (void)copyAllFieldsTo:(RAREListView_SectionHeader *)other;
@end

J2OBJC_FIELD_SETTER(RAREListView_SectionHeader, label_, id)

@interface RAREListView_$1 : NSObject < RAREiActionListener > {
 @public
  RAREListView *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREListView:(RAREListView *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREListView_$1, this$0_, RAREListView *)

@interface RAREListView_$2 : NSObject < RAREiActionListener > {
 @public
  RAREListView *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREListView:(RAREListView *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREListView_$2, this$0_, RAREListView *)

@interface RAREListView_$3 : NSObject < JavaUtilComparator > {
}

- (int)compareWithId:(id)o1
              withId:(id)o2;
- (id)init;
@end

#endif // _RAREListView_H_
