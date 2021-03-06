//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-tree/com/appnativa/rare/viewer/TreeViewer.java
//
//  Created by decoteaud on 9/15/14.
//

#include "IOSIntArray.h"
#include "com/appnativa/rare/platform/apple/ui/DataItemListModel.h"
#include "com/appnativa/rare/platform/apple/ui/ListBoxListHandler.h"
#include "com/appnativa/rare/platform/apple/ui/util/AppleHelper.h"
#include "com/appnativa/rare/platform/apple/ui/view/ListView.h"
#include "com/appnativa/rare/platform/apple/ui/view/TreeView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/spot/ScrollPane.h"
#include "com/appnativa/rare/spot/Tree.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/TreeComponent.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"
#include "com/appnativa/rare/ui/iTreeHandler.h"
#include "com/appnativa/rare/ui/renderer/ListItemRenderer.h"
#include "com/appnativa/rare/ui/renderer/TreeItemRenderer.h"
#include "com/appnativa/rare/ui/tree/DataItemTreeModel.h"
#include "com/appnativa/rare/viewer/ListBoxViewer.h"
#include "com/appnativa/rare/viewer/TreeViewer.h"
#include "com/appnativa/rare/viewer/aListViewer.h"
#include "com/appnativa/rare/viewer/iFormViewer.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"
#include "java/util/Map.h"

@implementation RARETreeViewer

- (id)init {
  return [self initRARETreeViewerWithRAREiFormViewer:nil];
}

- (id)initRARETreeViewerWithRAREiFormViewer:(id<RAREiFormViewer>)fv {
  return [super initWithRAREiFormViewer:fv];
}

- (id)initWithRAREiFormViewer:(id<RAREiFormViewer>)fv {
  return [self initRARETreeViewerWithRAREiFormViewer:fv];
}

- (void)cancelEditing {
  if (treeHandler_ != nil) {
  }
}

- (void)setAutoSizeRowsToFitWithBoolean:(BOOL)autoSize {
  [((RARETreeComponent *) check_class_cast(dataComponent_, [RARETreeComponent class])) setAutoSizeRowsWithBoolean:autoSize];
}

- (void)setSelectionModeWithRAREiListHandler_SelectionModeEnum:(RAREiListHandler_SelectionModeEnum *)selectionMode {
  [super setSelectionModeWithRAREiListHandler_SelectionModeEnum:selectionMode];
  RARETreeView *v = (RARETreeView *) check_class_cast([self getDataView], [RARETreeView class]);
  [((RARETreeView *) nil_chk(v)) setSelectionModeWithRAREiListHandler_SelectionModeEnum:selectionMode];
  if ((selectionMode == [RAREiListHandler_SelectionModeEnum NONE]) || (selectionMode == [RAREiListHandler_SelectionModeEnum INVISIBLE])) {
    [((RAREListItemRenderer *) nil_chk([v getItemRenderer])) setSelectionPaintedWithBoolean:NO];
  }
}

- (void)setShowLastDividerWithBoolean:(BOOL)show {
  [((RARETreeView *) check_class_cast([self getDataView], [RARETreeView class])) setShowLastDividerWithBoolean:show];
}

- (void)handleCustomPropertiesWithRARESPOTWidget:(RARESPOTWidget *)cfg
                                 withJavaUtilMap:(id<JavaUtilMap>)properties {
  [super handleCustomPropertiesWithRARESPOTWidget:cfg withJavaUtilMap:properties];
  [RAREListBoxViewer handleCustomPropertiesWithRAREListView:(RAREListView *) check_class_cast([self getDataView], [RAREListView class]) withRARESPOTWidget:cfg withJavaUtilMap:properties];
}

- (void)setTreeIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)expanded
                    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)collapsed {
  [((RARETreeComponent *) check_class_cast(dataComponent_, [RARETreeComponent class])) setTreeIconsWithRAREiPlatformIcon:expanded withRAREiPlatformIcon:collapsed];
}

- (void)createModelAndComponentsWithRARESPOTViewer:(RARESPOTViewer *)vcfg {
  RARESPOTTree *cfg = (RARESPOTTree *) check_class_cast(vcfg, [RARESPOTTree class]);
  RARETreeView *tree = [[RARETreeView alloc] init];
  dataComponent_ = formComponent_ = [[RARETreeComponent alloc] initWithRARETreeView:tree];
  RARETreeComponent *tc = (RARETreeComponent *) check_class_cast(dataComponent_, [RARETreeComponent class]);
  treeHandler_ = [tc getTreeHandler];
  treeModel_ = [tc getTreeModel];
  listModel_ = [tc getListModel];
  [((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) setWidgetWithRAREiWidget:self];
  formComponent_ = [RAREAppleHelper configureScrollPaneWithRAREiWidget:self withRAREiPlatformComponent:formComponent_ withRAREView:tree withRARESPOTScrollPane:[((RARESPOTTree *) nil_chk(cfg)) getScrollPane]];
  RARETreeItemRenderer *lr = [[RARETreeItemRenderer alloc] initWithRARETreeViewer:self];
  [tree setItemRendererWithRAREaTreeItemRenderer:lr];
  listComponent_ = [[RAREListBoxListHandler alloc] initWithRAREaPlatformTableBasedView:tree withRAREiPlatformListDataModel:listModel_];
}

- (void)handleViewerConfigurationChangedWithBoolean:(BOOL)reset {
  IOSIntArray *n = [self getSelectedIndexes];
  if ((n == nil) || ((int) [n count] == 0)) {
    if ((selectedIndexes_ != nil) && ((int) [selectedIndexes_ count] > 0)) {
      BOOL enabled = [self isChangeEventsEnabled];
      [self setChangeEventsEnabledWithBoolean:NO];
      [self setSelectedIndexesWithIntArray:selectedIndexes_];
      [self setChangeEventsEnabledWithBoolean:enabled];
    }
  }
  selectedIndexes_ = nil;
  [super handleViewerConfigurationChangedWithBoolean:reset];
}

- (void)handleViewerConfigurationWillChangeWithId:(id)newConfig {
  selectedIndexes_ = [self getSelectedIndexes];
  [super handleViewerConfigurationWillChangeWithId:newConfig];
}

- (void)setFlingThresholdWithInt:(int)threshold {
  [((RARETreeView *) check_class_cast([self getDataView], [RARETreeView class])) setFlingThresholdWithInt:threshold];
}

- (void)setSelectFlingedWithBoolean:(BOOL)select {
  [((RARETreeView *) check_class_cast([self getDataView], [RARETreeView class])) setSelectFlingedWithBoolean:select];
}

- (void)setWholeViewFlingWithBoolean:(BOOL)wholeViewFling {
  [((RARETreeView *) check_class_cast([self getDataView], [RARETreeView class])) setWholeViewFlingWithBoolean:wholeViewFling];
}

- (void)copyAllFieldsTo:(RARETreeViewer *)other {
  [super copyAllFieldsTo:other];
  other->selectedIndexes_ = selectedIndexes_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "handleCustomPropertiesWithRARESPOTWidget:withJavaUtilMap:", NULL, "V", 0x4, NULL },
    { "createModelAndComponentsWithRARESPOTViewer:", NULL, "V", 0x4, NULL },
    { "handleViewerConfigurationChangedWithBoolean:", NULL, "V", 0x4, NULL },
    { "handleViewerConfigurationWillChangeWithId:", NULL, "V", 0x4, NULL },
    { "setFlingThresholdWithInt:", NULL, "V", 0x4, NULL },
    { "setSelectFlingedWithBoolean:", NULL, "V", 0x4, NULL },
    { "setWholeViewFlingWithBoolean:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARETreeViewer = { "TreeViewer", "com.appnativa.rare.viewer", NULL, 0x1, 7, methods, 0, NULL, 0, NULL};
  return &_RARETreeViewer;
}

@end
