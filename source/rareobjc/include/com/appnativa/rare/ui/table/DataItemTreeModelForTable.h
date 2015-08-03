//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/DataItemTreeModelForTable.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREDataItemTreeModelForTable_H_
#define _RAREDataItemTreeModelForTable_H_

@class RARERenderableDataItem;
@class RARETreeView;
@protocol JavaUtilComparator;
@protocol RAREiTreeItem;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/tree/DataItemTreeModel.h"
#include "com/appnativa/rare/ui/tree/TreeItemEx.h"

@interface RAREDataItemTreeModelForTable : RAREDataItemTreeModel {
 @public
  BOOL expandall_;
  BOOL sorting_DataItemTreeModelForTable_;
}

- (id)initWithRARETreeView:(RARETreeView *)tree;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (void)setExpandAllWithBoolean:(BOOL)expandAll;
- (id<RAREiTreeItem>)createTreeItemWithRARERenderableDataItem:(RARERenderableDataItem *)item
                                            withRAREiTreeItem:(id<RAREiTreeItem>)parent;
- (void)fireRootChanged;
- (void)copyAllFieldsTo:(RAREDataItemTreeModelForTable *)other;
@end

typedef RAREDataItemTreeModelForTable ComAppnativaRareUiTableDataItemTreeModelForTable;

@interface RAREDataItemTreeModelForTable_TreeTableItem : RARETreeItemEx {
 @public
  RAREDataItemTreeModelForTable *this$0_;
}

- (id)initWithRAREDataItemTreeModelForTable:(RAREDataItemTreeModelForTable *)outer$
                 withRARERenderableDataItem:(RARERenderableDataItem *)di
                         withRARETreeItemEx:(RARETreeItemEx *)parent;
- (BOOL)isLeaf;
@end

J2OBJC_FIELD_SETTER(RAREDataItemTreeModelForTable_TreeTableItem, this$0_, RAREDataItemTreeModelForTable *)

#endif // _RAREDataItemTreeModelForTable_H_