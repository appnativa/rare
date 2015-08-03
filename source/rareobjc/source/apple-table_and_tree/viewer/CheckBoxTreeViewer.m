//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/viewer/CheckBoxTreeViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/apple/ui/view/TreeView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/spot/CheckBoxTree.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/viewer/CheckBoxTreeViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"

@implementation RARECheckBoxTreeViewer

- (id)init {
  return [self initRARECheckBoxTreeViewerWithRAREiContainer:nil];
}

- (id)initRARECheckBoxTreeViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRARECheckBoxTreeViewerWithRAREiContainer:parent];
}

- (void)clearCheckMarks {
  [((RARETreeView *) nil_chk([self getListView])) clearCheckedItems];
}

- (void)setCheckedRowsWithIntArray:(IOSIntArray *)indices {
  [((RARETreeView *) nil_chk([self getListView])) setCheckedRowsWithIntArray:indices];
}

- (void)setLinkSelectionWithBoolean:(BOOL)linked {
  [super setLinkSelectionWithBoolean:linked];
  [((RARETreeView *) nil_chk([self getListView])) setLinkSelectionWithBoolean:linkedSelection_];
}

- (void)setListSelectionTypeWithRAREiListHandler_SelectionTypeEnum:(RAREiListHandler_SelectionTypeEnum *)type {
  [super setListSelectionTypeWithRAREiListHandler_SelectionTypeEnum:type];
  [((RARETreeView *) nil_chk([self getListView])) setSelectionTypeWithRAREiListHandler_SelectionTypeEnum:type];
}

- (void)setManageChildNodeSelectionsWithBoolean:(BOOL)manage {
  [((RARETreeView *) nil_chk([self getListView])) setManageCheckboxSelectionWithBoolean:manage];
}

- (void)setRowCheckedWithInt:(int)row
                 withBoolean:(BOOL)checked {
  [((RARETreeView *) nil_chk([self getListView])) setRowCheckedWithInt:row withBoolean:checked];
}

- (BOOL)hasCheckedRows {
  return [((RARETreeView *) nil_chk([self getListView])) hasCheckedRows];
}

- (BOOL)isRowCheckedWithInt:(int)row {
  return [((RARETreeView *) nil_chk([self getListView])) isRowCheckedWithInt:row];
}

- (RARETreeView *)getListView {
  return (RARETreeView *) check_class_cast([self getDataView], [RARETreeView class]);
}

+ (void)registerForUse {
  [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) registerWidgetClassWithNSString:[RAREPlatform getSPOTNameWithIOSClass:[IOSClass classWithClass:[RARESPOTCheckBoxTree class]]] withIOSClass:[IOSClass classWithClass:[RARECheckBoxTreeViewer class]]];
}

- (void)setIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)checked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)unchecked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)indeterminate {
  [((RARETreeView *) check_class_cast([self getDataView], [RARETreeView class])) setIconsWithRAREiPlatformIcon:checked withRAREiPlatformIcon:unchecked withRAREiPlatformIcon:indeterminate];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "hasCheckedRows", NULL, "Z", 0x1, NULL },
    { "isRowCheckedWithInt:", NULL, "Z", 0x1, NULL },
    { "getListView", NULL, "LRARETreeView", 0x0, NULL },
    { "registerForUse", NULL, "V", 0xc, NULL },
    { "setIconsWithRAREiPlatformIcon:withRAREiPlatformIcon:withRAREiPlatformIcon:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARECheckBoxTreeViewer = { "CheckBoxTreeViewer", "com.appnativa.rare.viewer", NULL, 0x1, 5, methods, 0, NULL, 0, NULL};
  return &_RARECheckBoxTreeViewer;
}

@end
