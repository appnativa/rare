//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/TreeComponent.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/platform/apple/ui/DataItemListModel.h"
#include "com/appnativa/rare/platform/apple/ui/view/TreeView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/TreeComponent.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/aComponent.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"
#include "com/appnativa/rare/ui/iTreeHandler.h"
#include "com/appnativa/rare/ui/tree/DataItemTreeModel.h"
#include "com/appnativa/rare/ui/tree/aDataItemTreeModel.h"
#include "com/appnativa/rare/ui/tree/aTreeHandler.h"

@implementation RARETreeComponent

- (id)initWithRARETreeView:(RARETreeView *)tree {
  if (self = [super initWithRAREParentView:tree]) {
    RAREDataItemTreeModel *treeModel = [[RAREDataItemTreeModel alloc] init];
    [treeModel setTreeWithRAREiTree:tree];
    [treeModel setListModelWithRAREiPlatformListDataModel:[[RAREDataItemListModel alloc] init]];
    [((RARETreeView *) nil_chk(tree)) setTreeModelWithRAREDataItemTreeModel:treeModel];
    treeHandler_ = [[RARETreeComponent_$1 alloc] initWithRARETreeComponent:self withRARETreeView:tree withRAREDataItemTreeModel:treeModel];
    [self setForegroundWithRAREUIColor:[RAREColorUtils getListForeground]];
    [self setBackgroundWithRAREUIColor:[RAREColorUtils getListBackground]];
    RAREUIColor *c = [RAREColorUtils getListDisabledForeground];
    if (c == nil) {
      c = [RAREColorUtils getDisabledForeground];
    }
    [self setDisabledColorWithRAREUIColor:c];
  }
  return self;
}

- (void)dispose {
  [super dispose];
  if (treeHandler_ != nil) {
    [((RAREaTreeHandler *) check_class_cast(treeHandler_, [RAREaTreeHandler class])) dispose];
    treeHandler_ = nil;
  }
}

- (void)setTreeIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)expanded
                    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)collapsed {
  [((RARETreeView *) check_class_cast(view_, [RARETreeView class])) setTreeIconsWithRAREiPlatformIcon:expanded withRAREiPlatformIcon:collapsed];
}

- (RAREDataItemListModel *)getListModel {
  return (RAREDataItemListModel *) check_class_cast([((RAREDataItemTreeModel *) nil_chk([self getTreeModel])) getListModel], [RAREDataItemListModel class]);
}

- (id<RAREiTreeHandler>)getTreeHandler {
  return treeHandler_;
}

- (RAREDataItemTreeModel *)getTreeModel {
  return (RAREDataItemTreeModel *) check_class_cast([((RARETreeView *) check_class_cast(view_, [RARETreeView class])) getTreeModel], [RAREDataItemTreeModel class]);
}

- (void)setAutoSizeRowsWithBoolean:(BOOL)autoSize {
  [((RARETreeView *) check_class_cast(view_, [RARETreeView class])) setAutoSizeRowsWithBoolean:autoSize];
}

- (void)copyAllFieldsTo:(RARETreeComponent *)other {
  [super copyAllFieldsTo:other];
  other->treeHandler_ = treeHandler_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getListModel", NULL, "LRAREDataItemListModel", 0x1, NULL },
    { "getTreeHandler", NULL, "LRAREiTreeHandler", 0x1, NULL },
    { "getTreeModel", NULL, "LRAREDataItemTreeModel", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "treeHandler_", NULL, 0x0, "LRAREiTreeHandler" },
  };
  static J2ObjcClassInfo _RARETreeComponent = { "TreeComponent", "com.appnativa.rare.ui", NULL, 0x1, 3, methods, 1, fields, 0, NULL};
  return &_RARETreeComponent;
}

@end
@implementation RARETreeComponent_$1

- (BOOL)hasListeners {
  return this$0_->listenerList_ != nil;
}

- (RAREEventListenerList *)getEventListenerList {
  return [this$0_ getEventListenerList];
}

- (BOOL)isAutoScrollOnExpansion {
  return [((RARETreeView *) check_class_cast(this$0_->view_, [RARETreeView class])) isAutoScrollOnExpansion];
}

- (void)setAutoScrollOnExpansionWithBoolean:(BOOL)autoScrollOnExpansion {
  [((RARETreeView *) check_class_cast(this$0_->view_, [RARETreeView class])) setAutoScrollOnExpansionWithBoolean:autoScrollOnExpansion];
}

- (id)initWithRARETreeComponent:(RARETreeComponent *)outer$
               withRARETreeView:(RARETreeView *)arg$0
      withRAREDataItemTreeModel:(RAREDataItemTreeModel *)arg$1 {
  this$0_ = outer$;
  return [super initWithRAREiTree:arg$0 withRAREDataItemTreeModel:arg$1];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "hasListeners", NULL, "Z", 0x4, NULL },
    { "getEventListenerList", NULL, "LRAREEventListenerList", 0x4, NULL },
    { "isAutoScrollOnExpansion", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRARETreeComponent" },
  };
  static J2ObjcClassInfo _RARETreeComponent_$1 = { "$1", "com.appnativa.rare.ui", "TreeComponent", 0x8000, 3, methods, 1, fields, 0, NULL};
  return &_RARETreeComponent_$1;
}

@end
