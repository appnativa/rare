//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iTableModel.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/iTableModel.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/iFilter.h"
#include "com/appnativa/util/iFilterableList.h"
#include "com/appnativa/util/iStringConverter.h"
#include "java/lang/CharSequence.h"
#include "java/util/Collection.h"
#include "java/util/Comparator.h"
#include "java/util/List.h"


@interface RAREiTableModel : NSObject
@end

@implementation RAREiTableModel

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "filterWithInt:", NULL, "V", 0x401, NULL },
    { "resetModified", NULL, "V", 0x401, NULL },
    { "getColumnCount", NULL, "I", 0x401, NULL },
    { "isModified", NULL, "Z", 0x401, NULL },
    { "addIndexToFilteredListWithInt:", NULL, "V", 0x401, NULL },
    { "addRowWithRARERenderableDataItem:", NULL, "V", 0x401, NULL },
    { "addRowWithInt:withRARERenderableDataItem:", NULL, "V", 0x401, NULL },
    { "addRowExWithRARERenderableDataItem:", NULL, "V", 0x401, NULL },
    { "addRowExWithInt:withRARERenderableDataItem:", NULL, "V", 0x401, NULL },
    { "addRowsWithJavaUtilCollection:", NULL, "V", 0x401, NULL },
    { "addRowsWithInt:withJavaUtilCollection:", NULL, "V", 0x401, NULL },
    { "addRowsExWithJavaUtilCollection:", NULL, "V", 0x401, NULL },
    { "addRowsExWithInt:withJavaUtilCollection:", NULL, "V", 0x401, NULL },
    { "addToFilteredListWithRARERenderableDataItem:", NULL, "V", 0x401, NULL },
    { "addToFilteredListWithInt:withRARERenderableDataItem:", NULL, "V", 0x401, NULL },
    { "clearTable", NULL, "V", 0x401, NULL },
    { "clearTableData", NULL, "V", 0x401, NULL },
    { "clearTableDataEx", NULL, "V", 0x401, NULL },
    { "concatWithJavaUtilListArray:", NULL, "LJavaUtilList", 0x481, NULL },
    { "createEmptyCopy", NULL, "LRAREiTableModel", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "filterWithRAREUTiFilter:", NULL, "Z", 0x401, NULL },
    { "filterWithNSString:withBoolean:", NULL, "Z", 0x401, NULL },
    { "filterWithNSString:withBoolean:withBoolean:withBoolean:", NULL, "Z", 0x401, NULL },
    { "findWithRAREUTiFilter:withInt:", NULL, "I", 0x401, NULL },
    { "findWithNSString:withInt:withBoolean:", NULL, "I", 0x401, NULL },
    { "indexForRowWithRARERenderableDataItem:", NULL, "I", 0x401, NULL },
    { "joinWithNSString:", NULL, "LNSString", 0x401, NULL },
    { "moveRowWithInt:withInt:", NULL, "V", 0x401, NULL },
    { "refilter", NULL, "Z", 0x401, NULL },
    { "removeRowWithInt:", NULL, "V", 0x401, NULL },
    { "resetModelWithJavaUtilList:withRAREUTiFilterableList:", NULL, "V", 0x401, NULL },
    { "resetRowsWithRAREUTiFilterableList:", NULL, "V", 0x401, NULL },
    { "reverse", NULL, "LJavaUtilList", 0x401, NULL },
    { "sliceWithInt:", NULL, "LJavaUtilList", 0x401, NULL },
    { "sliceWithInt:withInt:", NULL, "LJavaUtilList", 0x401, NULL },
    { "sortWithJavaUtilComparator:", NULL, "V", 0x401, NULL },
    { "sortWithInt:withBoolean:withBoolean:", NULL, "V", 0x401, NULL },
    { "sortExWithInt:withBoolean:withBoolean:withBoolean:", NULL, "LJavaUtilList", 0x401, NULL },
    { "spliceWithInt:withInt:", NULL, "LJavaUtilList", 0x401, NULL },
    { "spliceWithInt:withInt:withRARERenderableDataItemArray:", NULL, "LJavaUtilList", 0x481, NULL },
    { "spliceListWithInt:withInt:withJavaUtilList:", NULL, "LJavaUtilList", 0x401, NULL },
    { "tableChanged", NULL, "V", 0x401, NULL },
    { "tableDataChanged", NULL, "V", 0x401, NULL },
    { "tableItemsModified", NULL, "V", 0x401, NULL },
    { "unfilter", NULL, "Z", 0x401, NULL },
    { "updateForInsertionOrDeletion", NULL, "V", 0x401, NULL },
    { "setActiveColumnWithInt:", NULL, "V", 0x401, NULL },
    { "setAllowSortingWithBoolean:", NULL, "V", 0x401, NULL },
    { "setConverterWithRAREUTiStringConverter:", NULL, "V", 0x401, NULL },
    { "setExpandableColumnWithInt:", NULL, "V", 0x401, NULL },
    { "setRowWithInt:withRARERenderableDataItem:", NULL, "V", 0x401, NULL },
    { "setValueAtWithId:withInt:withInt:", NULL, "V", 0x401, NULL },
    { "getColumnWithInt:", NULL, "LRAREColumn", 0x401, NULL },
    { "getColumns", NULL, "LJavaUtilList", 0x401, NULL },
    { "getComparator", NULL, "LJavaUtilComparator", 0x401, NULL },
    { "getConverter", NULL, "LRAREUTiStringConverter", 0x401, NULL },
    { "getExpandableColumn", NULL, "I", 0x401, NULL },
    { "getFilterableList", NULL, "LRAREUTiFilterableList", 0x401, NULL },
    { "getItemAtWithInt:withInt:", NULL, "LRARERenderableDataItem", 0x401, NULL },
    { "getItemDescriptionWithInt:withInt:", NULL, "LRAREColumn", 0x401, NULL },
    { "getOperatingColumn", NULL, "I", 0x401, NULL },
    { "getRowWithInt:", NULL, "LRARERenderableDataItem", 0x401, NULL },
    { "getRowCount", NULL, "I", 0x401, NULL },
    { "getRowsEx", NULL, "LRAREUTiFilterableList", 0x401, NULL },
    { "getSortColumn", NULL, "I", 0x401, NULL },
    { "getTooltipWithInt:withInt:", NULL, "LJavaLangCharSequence", 0x401, NULL },
    { "getUnfilteredList", NULL, "LJavaUtilList", 0x401, NULL },
    { "getValueAtWithInt:withInt:", NULL, "LNSObject", 0x401, NULL },
    { "getWidget", NULL, "LRAREiWidget", 0x401, NULL },
    { "isAllowSorting", NULL, "Z", 0x401, NULL },
    { "isFiltered", NULL, "Z", 0x401, NULL },
    { "isSortDescending", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiTableModel = { "iTableModel", "com.appnativa.rare.ui", NULL, 0x201, 73, methods, 0, NULL, 0, NULL};
  return &_RAREiTableModel;
}

@end