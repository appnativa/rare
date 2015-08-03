//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/viewer/TableViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/apple/ui/util/AppleHelper.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/spot/ScrollPane.h"
#include "com/appnativa/rare/spot/Table.h"
#include "com/appnativa/rare/spot/TreeTable.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UISelectionModelGroup.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformListHandler.h"
#include "com/appnativa/rare/ui/iTableModel.h"
#include "com/appnativa/rare/ui/iTreeHandler.h"
#include "com/appnativa/rare/ui/renderer/ListItemRenderer.h"
#include "com/appnativa/rare/ui/table/MultiDataItemTableModel.h"
#include "com/appnativa/rare/ui/table/MultiTableContainer.h"
#include "com/appnativa/rare/ui/table/MultiTableLayout.h"
#include "com/appnativa/rare/ui/table/MultiTableTableComponent.h"
#include "com/appnativa/rare/ui/table/MultipleListHandler.h"
#include "com/appnativa/rare/ui/table/TableComponent.h"
#include "com/appnativa/rare/ui/table/TableListHandler.h"
#include "com/appnativa/rare/ui/table/TableView.h"
#include "com/appnativa/rare/ui/table/TreeTableComponent.h"
#include "com/appnativa/rare/ui/table/TreeTableItemRenderer.h"
#include "com/appnativa/rare/ui/table/TreeTableListHandler.h"
#include "com/appnativa/rare/ui/table/TreeTableView.h"
#include "com/appnativa/rare/ui/table/aTableHeader.h"
#include "com/appnativa/rare/ui/table/iTableComponent.h"
#include "com/appnativa/rare/ui/tree/DataItemTreeModel.h"
#include "com/appnativa/rare/viewer/ListBoxViewer.h"
#include "com/appnativa/rare/viewer/TableViewer.h"
#include "com/appnativa/rare/viewer/aTableViewer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "java/util/Map.h"

@implementation RARETableViewer

- (id)init {
  return [self initRARETableViewerWithRAREiContainer:nil];
}

- (id)initRARETableViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRARETableViewerWithRAREiContainer:parent];
}

- (void)dispose {
  if (![self isDisposable]) {
    return;
  }
  if ((selectionModelGroup_ != nil) && (selectionModel_ != nil)) {
    [selectionModelGroup_ removeWithId:selectionModel_];
  }
  [super dispose];
  selectionModelGroup_ = nil;
}

- (void)editCellWithInt:(int)row
                withInt:(int)column {
  [((RARETableView *) nil_chk([self getTableView])) editCellWithInt:row withInt:column];
}

- (void)sizeColumnToFitWithInt:(int)col {
}

- (void)sizeColumnsToFit {
}

- (void)setAutoSizeRowsToFitWithBoolean:(BOOL)autoSize {
  [((RARETableView *) nil_chk([self getTableView])) setAutoSizeRowsWithBoolean:autoSize];
}

- (void)setSelectionModeWithRAREiListHandler_SelectionModeEnum:(RAREiListHandler_SelectionModeEnum *)selectionMode {
  [super setSelectionModeWithRAREiListHandler_SelectionModeEnum:selectionMode];
  [((RARETableView *) nil_chk([self getTableView])) setSelectionModeWithRAREiListHandler_SelectionModeEnum:selectionMode];
  if ((selectionMode == [RAREiListHandler_SelectionModeEnum NONE]) || (selectionMode == [RAREiListHandler_SelectionModeEnum INVISIBLE])) {
    [((RAREListItemRenderer *) nil_chk([((RARETableView *) nil_chk([self getTableView])) getItemRenderer])) setSelectionPaintedWithBoolean:NO];
  }
}

- (void)setShowLastDividerWithBoolean:(BOOL)show {
  [((RARETableView *) check_class_cast([self getDataView], [RARETableView class])) setShowLastDividerWithBoolean:show];
}

- (int)getEditingColumn {
  return [((RARETableView *) nil_chk([self getTableView])) getEditingColumn];
}

- (int)getEditingRow {
  return [((RARETableView *) nil_chk([self getTableView])) getEditingRow];
}

- (BOOL)isEditing {
  return [((RARETableView *) nil_chk([self getTableView])) isEditing];
}

- (void)createModelAndComponentsWithRARESPOTViewer:(RARESPOTViewer *)vcfg {
  RARESPOTTable *cfg = (RARESPOTTable *) check_class_cast(vcfg, [RARESPOTTable class]);
  RAREListItemRenderer *lr;
  RARETableView *list;
  if ([cfg isKindOfClass:[RARESPOTTreeTable class]]) {
    list = [[RARETreeTableView alloc] init];
  }
  else {
    list = [[RARETableView alloc] init];
  }
  if ([vcfg isKindOfClass:[RARESPOTTreeTable class]]) {
    RARESPOTTreeTable *tcfg = (RARESPOTTreeTable *) check_class_cast(vcfg, [RARESPOTTreeTable class]);
    tableHandler_ = [[RARETreeTableComponent alloc] initWithRARETableView:list withRARESPOTTreeTable:tcfg];
    tableModel_ = [((RARETreeTableComponent *) check_class_cast(tableHandler_, [RARETreeTableComponent class])) getTableModel];
    treeHandler_ = [((RARETreeTableComponent *) check_class_cast(tableHandler_, [RARETreeTableComponent class])) getTreeHandler];
    [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setIndentByWithInt:[((SPOTInteger *) nil_chk(((RARESPOTTreeTable *) nil_chk(tcfg))->indentBy_)) intValue]];
    [((RARETreeTableComponent *) check_class_cast(tableHandler_, [RARETreeTableComponent class])) setExpandAllWithBoolean:[((SPOTBoolean *) nil_chk(tcfg->expandAll_)) booleanValue]];
    [((RARETreeTableComponent *) check_class_cast(tableHandler_, [RARETreeTableComponent class])) setExpandableColumnWithInt:[((SPOTInteger *) nil_chk(tcfg->expandableColumn_)) intValue]];
    listComponent_ = [[RARETreeTableListHandler alloc] initWithRARETableComponent:(RARETreeTableComponent *) check_class_cast(tableHandler_, [RARETreeTableComponent class]) withRAREiPlatformListDataModel:tableModel_ withRAREDataItemTreeModel:[((RARETreeTableComponent *) check_class_cast(tableHandler_, [RARETreeTableComponent class])) getTreeModel]];
    lr = [[RARETreeTableItemRenderer alloc] initWithBoolean:[((SPOTBoolean *) nil_chk(((RARESPOTTable *) nil_chk(cfg))->columnSelectionAllowed_)) booleanValue]];
    [((RARETableView *) nil_chk(list)) setItemRendererWithRAREListItemRenderer:lr];
    dataComponent_ = [listComponent_ getListComponent];
  }
  else {
    tableHandler_ = [[RARETableComponent alloc] initWithRARETableView:list withRARESPOTTable:cfg withBoolean:YES];
    tableModel_ = [((RARETableComponent *) check_class_cast(tableHandler_, [RARETableComponent class])) getTableModel];
    listComponent_ = [[RARETableListHandler alloc] initWithRARETableComponent:(RARETableComponent *) check_class_cast(tableHandler_, [RARETableComponent class]) withRAREiPlatformListDataModel:tableModel_];
    lr = [[RAREListItemRenderer alloc] initWithBoolean:[((SPOTBoolean *) nil_chk(((RARESPOTTable *) nil_chk(cfg))->columnSelectionAllowed_)) booleanValue] || [((SPOTBoolean *) nil_chk(cfg->displayAsGridView_)) booleanValue]];
    [((RARETableView *) nil_chk(list)) setItemRendererWithRAREListItemRenderer:lr];
    int mtype = [self getMiltiTableConfigurationTypeWithRARESPOTTable:cfg];
    dataComponent_ = [listComponent_ getListComponent];
    if (mtype != 0) {
      if ([((SPOTBoolean *) nil_chk(cfg->rowHeaderFooterSelectionPainted_)) booleanValue]) {
        [self adjustMultiTableRendererWithRAREaListItemRenderer:lr withRAREiTableComponent_TableTypeEnum:[RAREiTableComponent_TableTypeEnum MAIN]];
      }
      RARETableView *tv;
      tableModel_ = [[RAREMultiDataItemTableModel alloc] initWithRAREiTableModel:tableModel_];
      [tableModel_ setWidgetWithRAREiWidget:self];
      RAREMultiTableContainer *sp = [[RAREMultiTableContainer alloc] initWithRAREMultiTableLayout:[[RAREMultiTableLayout alloc] init]];
      RAREMultiTableTableComponent *mt = [[RAREMultiTableTableComponent alloc] initWithRAREiPlatformComponent:sp withRAREMultiDataItemTableModel:(RAREMultiDataItemTableModel *) check_class_cast(tableModel_, [RAREMultiDataItemTableModel class])];
      [tableModel_ addDataModelListenerWithRAREiDataModelListener:sp];
      [((RARETableComponent *) check_class_cast(tableHandler_, [RARETableComponent class])) setBackgroundWithRAREUIColor:[RAREUIColor TRANSPARENT]];
      id<RAREiPlatformListHandler> h1 = nil;
      id<RAREiPlatformListHandler> h2 = nil;
      if ((mtype & 1) != 0) {
        RARETableComponent *tc = [[RARETableComponent alloc] initWithRARETableView:tv = [[RARETableView alloc] init] withRARESPOTTable:cfg withBoolean:NO];
        [tc setBackgroundWithRAREUIColor:[RAREUIColor TRANSPARENT]];
        [tc setTableTypeWithRAREiTableComponent_TableTypeEnum:[RAREiTableComponent_TableTypeEnum HEADER]];
        [self registerWithWidgetWithRAREiPlatformComponent:tc];
        [mt setRowHeaderTableWithRAREiTableComponent:tc];
        [sp addWithRAREiPlatformComponent:tc];
        h1 = [[RARETableListHandler alloc] initWithRARETableComponent:tc withRAREiPlatformListDataModel:[tc getModel]];
        [tv setItemRendererWithRAREListItemRenderer:lr = [[RAREListItemRenderer alloc] initWithBoolean:NO]];
        [((RAREMultiDataItemTableModel *) check_class_cast(tableModel_, [RAREMultiDataItemTableModel class])) setHeaderModelWithRAREiTableModel:[tc getModel]];
        [lr setPaintRowBackgroundWithBoolean:NO];
        if ([cfg->rowHeaderFooterSelectionPainted_ booleanValue]) {
          [self adjustMultiTableRendererWithRAREaListItemRenderer:lr withRAREiTableComponent_TableTypeEnum:[RAREiTableComponent_TableTypeEnum HEADER]];
        }
        else {
          [lr setSelectionPaintedWithBoolean:NO];
        }
        [((RAREaTableHeader *) nil_chk([tableHandler_ getTableHeader])) setPaintLeftMarginWithBoolean:YES];
      }
      [tableHandler_ setTableTypeWithRAREiTableComponent_TableTypeEnum:[RAREiTableComponent_TableTypeEnum MAIN]];
      [self registerWithWidgetWithRAREiPlatformComponent:[tableHandler_ getPlatformComponent]];
      [sp addWithRAREiPlatformComponent:[tableHandler_ getPlatformComponent]];
      [mt setMainTableWithRAREiTableComponent:tableHandler_];
      dataComponent_ = sp;
      tableHandler_ = mt;
      if ((mtype & 2) != 0) {
        RARETableComponent *tc = [[RARETableComponent alloc] initWithRARETableView:tv = [[RARETableView alloc] init] withRARESPOTTable:cfg withBoolean:NO];
        [tc setTableTypeWithRAREiTableComponent_TableTypeEnum:[RAREiTableComponent_TableTypeEnum FOOTER]];
        [tc setBackgroundWithRAREUIColor:[RAREUIColor TRANSPARENT]];
        [self registerWithWidgetWithRAREiPlatformComponent:tc];
        [mt setRowFooterTableWithRAREiTableComponent:tc];
        [sp addWithRAREiPlatformComponent:tc];
        h2 = [[RARETableListHandler alloc] initWithRARETableComponent:tc withRAREiPlatformListDataModel:[tc getModel]];
        if (h1 == nil) {
          h1 = h2;
          h2 = nil;
        }
        [tv setItemRendererWithRAREListItemRenderer:lr = [[RAREListItemRenderer alloc] initWithBoolean:NO]];
        [((RAREMultiDataItemTableModel *) check_class_cast(tableModel_, [RAREMultiDataItemTableModel class])) setFooterModelWithRAREiTableModel:[tc getModel]];
        if ([cfg->rowHeaderFooterSelectionPainted_ booleanValue]) {
          [self adjustMultiTableRendererWithRAREaListItemRenderer:lr withRAREiTableComponent_TableTypeEnum:[RAREiTableComponent_TableTypeEnum FOOTER]];
        }
        else {
          [lr setSelectionPaintedWithBoolean:NO];
        }
        [((RAREaTableHeader *) nil_chk([((id<RAREiTableComponent>) nil_chk([mt getMainTable])) getTableHeader])) setPaintRightMarginWithBoolean:YES];
      }
      listComponent_ = [[RAREMultipleListHandler alloc] initWithRAREMultiTableTableComponent:mt withRAREiPlatformListHandler:listComponent_ withRAREiPlatformListHandler:h1 withRAREiPlatformListHandler:h2];
      [sp setBackgroundWithRAREUIColor:[RAREUIColor WHITE]];
    }
  }
  formComponent_ = [((id<RAREiTableComponent>) nil_chk(tableHandler_)) getPlatformComponent];
  formComponent_ = [RAREAppleHelper configureScrollPaneWithRAREiWidget:self withRAREiPlatformComponent:formComponent_ withRAREView:list withRARESPOTScrollPane:[((RARESPOTTable *) nil_chk(cfg)) getScrollPane]];
}

- (void)handleCustomPropertiesWithRARESPOTWidget:(RARESPOTWidget *)cfg
                                 withJavaUtilMap:(id<JavaUtilMap>)properties {
  [super handleCustomPropertiesWithRARESPOTWidget:cfg withJavaUtilMap:properties];
  [RAREListBoxViewer handleCustomPropertiesWithRAREListView:[self getTableView] withRARESPOTWidget:cfg withJavaUtilMap:properties];
}

+ (void)registerForUse {
  [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) registerWidgetClassWithNSString:[RAREPlatform getSPOTNameWithIOSClass:[IOSClass classWithClass:[RARESPOTTable class]]] withIOSClass:[IOSClass classWithClass:[RARETableViewer class]]];
}

- (void)setFlingThresholdWithInt:(int)threshold {
  [((RARETableView *) nil_chk([self getTableView])) setFlingThresholdWithInt:threshold];
}

- (void)setSelectFlingedWithBoolean:(BOOL)select {
  [((RARETableView *) nil_chk([self getTableView])) setSelectFlingedWithBoolean:select];
}

- (void)setWholeViewFlingWithBoolean:(BOOL)wholeViewFling {
  [((RARETableView *) nil_chk([self getTableView])) setWholeViewFlingWithBoolean:wholeViewFling];
}

- (RARETableView *)getTableView {
  return [((RARETableComponent *) check_class_cast([((id<RAREiTableComponent>) nil_chk(tableHandler_)) getMainTable], [RARETableComponent class])) getTableView];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isEditing", NULL, "Z", 0x1, NULL },
    { "createModelAndComponentsWithRARESPOTViewer:", NULL, "V", 0x4, NULL },
    { "handleCustomPropertiesWithRARESPOTWidget:withJavaUtilMap:", NULL, "V", 0x4, NULL },
    { "registerForUse", NULL, "V", 0xc, NULL },
    { "setFlingThresholdWithInt:", NULL, "V", 0x4, NULL },
    { "setSelectFlingedWithBoolean:", NULL, "V", 0x4, NULL },
    { "setWholeViewFlingWithBoolean:", NULL, "V", 0x4, NULL },
    { "getTableView", NULL, "LRARETableView", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARETableViewer = { "TableViewer", "com.appnativa.rare.viewer", NULL, 0x1, 8, methods, 0, NULL, 0, NULL};
  return &_RARETableViewer;
}

@end
