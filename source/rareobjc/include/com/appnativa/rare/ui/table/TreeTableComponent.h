//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/TreeTableComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARETreeTableComponent_H_
#define _RARETreeTableComponent_H_

@class RAREDataItemTreeModel;
@class RAREDataItemTreeModelForTable;
@class RAREEventListenerList;
@class RARESPOTTable;
@class RARESPOTTreeTable;
@class RARESubItemComparator;
@class RARETableView;
@class RARETreeTableView;
@protocol JavaUtilComparator;
@protocol JavaUtilList;
@protocol RAREUTiFilterableList;
@protocol RAREiPlatformIcon;
@protocol RAREiTreeHandler;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/table/TableComponent.h"
#include "com/appnativa/rare/ui/tree/aTreeHandler.h"

@interface RARETreeTableComponent : RARETableComponent {
 @public
  RAREaTreeHandler *treeHandler_;
  RAREDataItemTreeModelForTable *treeModel_;
  RARESubItemComparator *itemComparator_;
}

- (id)initWithRARESPOTTreeTable:(RARESPOTTreeTable *)cfg;
- (id)initWithRARETableView:(RARETableView *)table
      withRARESPOTTreeTable:(RARESPOTTreeTable *)cfg;
- (void)initialize__WithRARETableView:(RARETableView *)table
                    withRARESPOTTable:(RARESPOTTable *)cfg OBJC_METHOD_FAMILY_NONE;
- (void)setTreeIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)expanded
                    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)collapsed;
- (void)dispose;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)c;
- (void)sortWithInt:(int)col
        withBoolean:(BOOL)descending
        withBoolean:(BOOL)useLinkedData;
- (void)setExpandAllWithBoolean:(BOOL)expandAll;
- (void)setExpandableColumnWithInt:(int)col;
- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (id<JavaUtilList>)getRawRows;
- (id<RAREiTreeHandler>)getTreeHandler;
- (RAREDataItemTreeModel *)getTreeModel;
- (void)resetTableExWithRAREUTiFilterableList:(id<RAREUTiFilterableList>)columns
                    withRAREUTiFilterableList:(id<RAREUTiFilterableList>)rows;
- (void)createTableModel;
- (void)copyAllFieldsTo:(RARETreeTableComponent *)other;
@end

J2OBJC_FIELD_SETTER(RARETreeTableComponent, treeHandler_, RAREaTreeHandler *)
J2OBJC_FIELD_SETTER(RARETreeTableComponent, treeModel_, RAREDataItemTreeModelForTable *)
J2OBJC_FIELD_SETTER(RARETreeTableComponent, itemComparator_, RARESubItemComparator *)

typedef RARETreeTableComponent ComAppnativaRareUiTableTreeTableComponent;

@interface RARETreeTableComponent_TreeHandler : RAREaTreeHandler {
 @public
  __weak RARETreeTableComponent *this$0_;
}

- (id)initWithRARETreeTableComponent:(RARETreeTableComponent *)outer$
               withRARETreeTableView:(RARETreeTableView *)tv
   withRAREDataItemTreeModelForTable:(RAREDataItemTreeModelForTable *)treeModel;
- (BOOL)hasListeners;
- (RAREEventListenerList *)getEventListenerList;
- (BOOL)isAutoScrollOnExpansion;
- (void)setAutoScrollOnExpansionWithBoolean:(BOOL)autoScrollOnExpansion;
@end

#endif // _RARETreeTableComponent_H_
