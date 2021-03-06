//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-tree/com/appnativa/rare/ui/table/DataItemTreeModelForTable.java
//
//  Created by decoteaud on 9/15/14.
//

#include "com/appnativa/rare/platform/apple/ui/view/TreeView.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/table/DataItemTreeModelForTable.h"
#include "com/appnativa/rare/ui/tree/DataItemTreeModel.h"
#include "com/appnativa/rare/ui/tree/TreeItemEx.h"
#include "com/appnativa/rare/ui/tree/iTreeItem.h"
#include "java/util/Comparator.h"

@implementation RAREDataItemTreeModelForTable

- (id)initWithRARETreeView:(RARETreeView *)tree {
  if (self = [super init]) {
    theTree_ = tree;
  }
  return self;
}

- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator {
  @try {
    sorting_DataItemTreeModelForTable_ = YES;
    [super sortWithJavaUtilComparator:comparator];
  }
  @finally {
    sorting_DataItemTreeModelForTable_ = NO;
  }
}

- (void)setExpandAllWithBoolean:(BOOL)expandAll {
  self->expandall_ = expandAll;
}

- (id<RAREiTreeItem>)createTreeItemWithRARERenderableDataItem:(RARERenderableDataItem *)item
                                            withRAREiTreeItem:(id<RAREiTreeItem>)parent {
  return [[RAREDataItemTreeModelForTable_TreeTableItem alloc] initWithRAREDataItemTreeModelForTable:self withRARERenderableDataItem:item withRARETreeItemEx:(RARETreeItemEx *) check_class_cast(parent, [RARETreeItemEx class])];
}

- (void)fireRootChanged {
  if (expandall_ && !sorting_DataItemTreeModelForTable_) {
    [((RARETreeItemEx *) nil_chk([self getRootTreeItem])) expandAll];
  }
  [super fireRootChanged];
}

- (void)copyAllFieldsTo:(RAREDataItemTreeModelForTable *)other {
  [super copyAllFieldsTo:other];
  other->expandall_ = expandall_;
  other->sorting_DataItemTreeModelForTable_ = sorting_DataItemTreeModelForTable_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createTreeItemWithRARERenderableDataItem:withRAREiTreeItem:", NULL, "LRAREiTreeItem", 0x4, NULL },
    { "fireRootChanged", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "expandall_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREDataItemTreeModelForTable = { "DataItemTreeModelForTable", "com.appnativa.rare.ui.table", NULL, 0x1, 2, methods, 1, fields, 0, NULL};
  return &_RAREDataItemTreeModelForTable;
}

@end
@implementation RAREDataItemTreeModelForTable_TreeTableItem

- (id)initWithRAREDataItemTreeModelForTable:(RAREDataItemTreeModelForTable *)outer$
                 withRARERenderableDataItem:(RARERenderableDataItem *)di
                         withRARETreeItemEx:(RARETreeItemEx *)parent {
  this$0_ = outer$;
  return [super initWithRARERenderableDataItem:di withRAREaDataItemTreeModel:outer$ withRARETreeItemEx:parent];
}

- (BOOL)isLeaf {
  RARERenderableDataItem *di = [this$0_ getExpandableItemExWithRARERenderableDataItem:item_];
  return (di == nil) || [di isEmpty];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isLeaf", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREDataItemTreeModelForTable" },
  };
  static J2ObjcClassInfo _RAREDataItemTreeModelForTable_TreeTableItem = { "TreeTableItem", "com.appnativa.rare.ui.table", "DataItemTreeModelForTable", 0x0, 1, methods, 1, fields, 0, NULL};
  return &_RAREDataItemTreeModelForTable_TreeTableItem;
}

@end
