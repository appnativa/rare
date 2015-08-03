//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/TreeTableComponent.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/spot/Table.h"
#include "com/appnativa/rare/spot/TreeTable.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/iTableModel.h"
#include "com/appnativa/rare/ui/iTreeHandler.h"
#include "com/appnativa/rare/ui/table/DataItemTreeModelForTable.h"
#include "com/appnativa/rare/ui/table/TableView.h"
#include "com/appnativa/rare/ui/table/TreeTableComponent.h"
#include "com/appnativa/rare/ui/table/TreeTableView.h"
#include "com/appnativa/rare/ui/table/aTableHeader.h"
#include "com/appnativa/rare/ui/tree/DataItemTreeModel.h"
#include "com/appnativa/rare/ui/tree/aTreeHandler.h"
#include "com/appnativa/rare/util/SubItemComparator.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/util/FilterableList.h"
#include "com/appnativa/util/iFilterableList.h"
#include "java/util/Comparator.h"
#include "java/util/List.h"

@implementation RARETreeTableComponent

- (id)initWithRARESPOTTreeTable:(RARESPOTTreeTable *)cfg {
  return [self initRARETreeTableComponentWithRARETableView:[[RARETreeTableView alloc] init] withRARESPOTTreeTable:cfg];
}

- (id)initRARETreeTableComponentWithRARETableView:(RARETableView *)table
                            withRARESPOTTreeTable:(RARESPOTTreeTable *)cfg {
  return [super initWithRARETableView:table withRARESPOTTable:cfg withBoolean:YES];
}

- (id)initWithRARETableView:(RARETableView *)table
      withRARESPOTTreeTable:(RARESPOTTreeTable *)cfg {
  return [self initRARETreeTableComponentWithRARETableView:table withRARESPOTTreeTable:cfg];
}

- (void)initialize__WithRARETableView:(RARETableView *)table
                    withRARESPOTTable:(RARESPOTTable *)cfg {
  [super initialize__WithRARETableView:table withRARESPOTTable:cfg];
  RARESPOTTreeTable *tt = (RARESPOTTreeTable *) check_class_cast(cfg, [RARESPOTTreeTable class]);
  RARETreeTableView *tv = (RARETreeTableView *) check_class_cast(tableView_, [RARETreeTableView class]);
  [((RARETreeTableView *) nil_chk(tv)) setShowRootHandlesWithBoolean:[((SPOTBoolean *) nil_chk(((RARESPOTTreeTable *) nil_chk(tt))->showRootHandles_)) booleanValue]];
  [tv setParentItemsSelectableWithBoolean:[((SPOTBoolean *) nil_chk(tt->parentItemsSelectable_)) booleanValue]];
  [tv setToggleOnTwistyOnlyWithBoolean:[tt->parentItemsSelectable_ booleanValue]];
  [((RAREDataItemTreeModelForTable *) nil_chk(treeModel_)) setExpandableColumnWithInt:[((SPOTInteger *) nil_chk(tt->expandableColumn_)) intValue]];
  [treeModel_ setExpandAllWithBoolean:[((SPOTBoolean *) nil_chk(tt->expandAll_)) booleanValue]];
}

- (void)dispose {
  [super dispose];
  if (treeHandler_ != nil) {
    [treeHandler_ dispose];
  }
  if (treeModel_ != nil) {
    [treeModel_ dispose];
  }
  treeHandler_ = nil;
  treeModel_ = nil;
  itemComparator_ = nil;
}

- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)c {
  [((RAREaTreeHandler *) nil_chk(treeHandler_)) sortWithJavaUtilComparator:c];
}

- (void)sortWithInt:(int)col
        withBoolean:(BOOL)descending
        withBoolean:(BOOL)useLinkedData {
  sortColumn_ = col;
  if (itemComparator_ == nil) {
    itemComparator_ = [[RARESubItemComparator alloc] initWithRAREiTableModel:tableModel_];
  }
  [((RARESubItemComparator *) nil_chk(itemComparator_)) setOptionsWithInt:col withBoolean:descending];
  [itemComparator_ setUseLinkedDataWithBoolean:useLinkedData];
  self->descending_ = descending;
  [((RAREaTreeHandler *) nil_chk(treeHandler_)) sortWithJavaUtilComparator:itemComparator_];
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setSortColumnWithInt:col withBoolean:descending];
}

- (void)setExpandAllWithBoolean:(BOOL)expandAll {
  [((RAREDataItemTreeModelForTable *) nil_chk(treeModel_)) setExpandAllWithBoolean:expandAll];
}

- (void)setExpandableColumnWithInt:(int)col {
  [((RAREDataItemTreeModelForTable *) nil_chk(treeModel_)) setExpandableColumnWithInt:col];
}

- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)widget {
  [super setWidgetWithRAREiWidget:widget];
  [((RAREDataItemTreeModelForTable *) nil_chk(treeModel_)) setWidgetWithRAREiWidget:widget];
}

- (id<JavaUtilList>)getRawRows {
  return [((RAREDataItemTreeModelForTable *) nil_chk(treeModel_)) getRawRows];
}

- (id<RAREiTreeHandler>)getTreeHandler {
  return treeHandler_;
}

- (RAREDataItemTreeModel *)getTreeModel {
  return treeModel_;
}

- (void)resetTableExWithRAREUTiFilterableList:(id<RAREUTiFilterableList>)columns
                    withRAREUTiFilterableList:(id<RAREUTiFilterableList>)rows {
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setColumnsWithJavaUtilList:columns];
  [((id<RAREiTableModel>) nil_chk(tableModel_)) resetModelWithJavaUtilList:columns withRAREUTiFilterableList:[[RAREUTFilterableList alloc] init]];
  [((RAREDataItemTreeModelForTable *) nil_chk(treeModel_)) setListModelWithRAREiPlatformListDataModel:tableModel_];
  [treeModel_ setAllWithJavaUtilCollection:rows];
}

- (void)createTableModel {
  [super createTableModel];
  RARETreeTableView *tv = (RARETreeTableView *) check_class_cast(tableView_, [RARETreeTableView class]);
  [((RARETreeTableView *) nil_chk(tv)) setShowRootWithBoolean:NO];
  treeModel_ = [[RAREDataItemTreeModelForTable alloc] initWithRARETreeView:tv];
  [treeModel_ setListModelWithRAREiPlatformListDataModel:tableModel_];
  treeHandler_ = [[RARETreeTableComponent_TreeHandler alloc] initWithRARETreeTableComponent:self withRARETreeTableView:tv withRAREDataItemTreeModelForTable:treeModel_];
  [tv setTreeModelWithRAREDataItemTreeModel:treeModel_];
}

- (void)copyAllFieldsTo:(RARETreeTableComponent *)other {
  [super copyAllFieldsTo:other];
  other->itemComparator_ = itemComparator_;
  other->treeHandler_ = treeHandler_;
  other->treeModel_ = treeModel_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initialize__WithRARETableView:withRARESPOTTable:", NULL, "V", 0x4, NULL },
    { "getRawRows", NULL, "LJavaUtilList", 0x1, NULL },
    { "getTreeHandler", NULL, "LRAREiTreeHandler", 0x1, NULL },
    { "getTreeModel", NULL, "LRAREDataItemTreeModel", 0x1, NULL },
    { "resetTableExWithRAREUTiFilterableList:withRAREUTiFilterableList:", NULL, "V", 0x4, NULL },
    { "createTableModel", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "treeHandler_", NULL, 0x0, "LRAREaTreeHandler" },
    { "treeModel_", NULL, 0x0, "LRAREDataItemTreeModelForTable" },
    { "itemComparator_", NULL, 0x0, "LRARESubItemComparator" },
  };
  static J2ObjcClassInfo _RARETreeTableComponent = { "TreeTableComponent", "com.appnativa.rare.ui.table", NULL, 0x1, 6, methods, 3, fields, 0, NULL};
  return &_RARETreeTableComponent;
}

@end
@implementation RARETreeTableComponent_TreeHandler

- (id)initWithRARETreeTableComponent:(RARETreeTableComponent *)outer$
               withRARETreeTableView:(RARETreeTableView *)tv
   withRAREDataItemTreeModelForTable:(RAREDataItemTreeModelForTable *)treeModel {
  this$0_ = outer$;
  return [super initWithRAREiTree:tv withRAREDataItemTreeModel:treeModel];
}

- (BOOL)hasListeners {
  return this$0_->listenerList_ != nil;
}

- (RAREEventListenerList *)getEventListenerList {
  return [this$0_ getEventListenerList];
}

- (BOOL)isAutoScrollOnExpansion {
  return [((RARETreeTableView *) check_class_cast(theTree_, [RARETreeTableView class])) isAutoScrollOnExpansion];
}

- (void)setAutoScrollOnExpansionWithBoolean:(BOOL)autoScrollOnExpansion {
  [((RARETreeTableView *) check_class_cast(theTree_, [RARETreeTableView class])) setAutoScrollOnExpansionWithBoolean:autoScrollOnExpansion];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "hasListeners", NULL, "Z", 0x4, NULL },
    { "getEventListenerList", NULL, "LRAREEventListenerList", 0x4, NULL },
    { "isAutoScrollOnExpansion", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRARETreeTableComponent" },
  };
  static J2ObjcClassInfo _RARETreeTableComponent_TreeHandler = { "TreeHandler", "com.appnativa.rare.ui.table", "TreeTableComponent", 0x0, 3, methods, 1, fields, 0, NULL};
  return &_RARETreeTableComponent_TreeHandler;
}

@end