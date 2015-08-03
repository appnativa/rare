//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/ui/table/iTableComponent.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformItemRenderer.h"
#include "com/appnativa/rare/ui/iTableModel.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/table/TableStyle.h"
#include "com/appnativa/rare/ui/table/aTableHeader.h"
#include "com/appnativa/rare/ui/table/iTableComponent.h"
#include "java/lang/CharSequence.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/util/Comparator.h"
#include "java/util/List.h"


@interface RAREiTableComponent : NSObject
@end

@implementation RAREiTableComponent

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isSortDescending", NULL, "Z", 0x401, NULL },
    { "convertModelIndexToViewWithInt:", NULL, "I", 0x401, NULL },
    { "convertViewIndexToModelWithInt:", NULL, "I", 0x401, NULL },
    { "getFirstVisibleColumnIndex", NULL, "I", 0x401, NULL },
    { "getLastVisibleColumnIndex", NULL, "I", 0x401, NULL },
    { "getTableHeader", NULL, "LRAREaTableHeader", 0x401, NULL },
    { "createColumnWithNSString:withId:withInt:withId:withRAREiPlatformIcon:", NULL, "LRAREColumn", 0x401, NULL },
    { "moveColumnWithInt:withInt:", NULL, "V", 0x401, NULL },
    { "resetTableWithJavaUtilList:withJavaUtilList:", NULL, "V", 0x401, NULL },
    { "sizeRowsToFit", NULL, "V", 0x401, NULL },
    { "sortWithJavaUtilComparator:", NULL, "V", 0x401, NULL },
    { "sortWithInt:", NULL, "V", 0x401, NULL },
    { "sortWithInt:withBoolean:withBoolean:", NULL, "V", 0x401, NULL },
    { "stopEditing", NULL, "V", 0x401, NULL },
    { "setColumnIconWithInt:withRAREiPlatformIcon:", NULL, "V", 0x401, NULL },
    { "setColumnTitleWithInt:withJavaLangCharSequence:", NULL, "V", 0x401, NULL },
    { "setColumnVisibleWithInt:withBoolean:", NULL, "V", 0x401, NULL },
    { "setGridViewTypeWithRAREiTableComponent_GridViewTypeEnum:", NULL, "V", 0x401, NULL },
    { "setHeaderBackgroundWithRAREPaintBucket:", NULL, "V", 0x401, NULL },
    { "setRowHeightWithInt:withInt:", NULL, "V", 0x401, NULL },
    { "setSelectedColumnIndexWithInt:", NULL, "V", 0x401, NULL },
    { "setSelectedColumnIndicesWithIntArray:", NULL, "V", 0x401, NULL },
    { "setShowHorizontalLinesWithBoolean:", NULL, "V", 0x401, NULL },
    { "setShowVerticalLinesWithBoolean:", NULL, "V", 0x401, NULL },
    { "setSortColumnWithInt:withBoolean:", NULL, "V", 0x401, NULL },
    { "setStyleWithRARETableStyle:", NULL, "V", 0x401, NULL },
    { "setTable", NULL, "V", 0x401, NULL },
    { "getCellRectWithInt:withInt:withBoolean:", NULL, "LRAREUIRectangle", 0x401, NULL },
    { "getColumnCount", NULL, "I", 0x401, NULL },
    { "getColumnIndexAtWithFloat:withFloat:", NULL, "I", 0x401, NULL },
    { "getGridViewType", NULL, "LRAREiTableComponent_GridViewTypeEnum", 0x401, NULL },
    { "getItemRenderer", NULL, "LRAREiPlatformItemRenderer", 0x401, NULL },
    { "getModel", NULL, "LRAREiTableModel", 0x401, NULL },
    { "getPlatformComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getSelectedColumn", NULL, "I", 0x401, NULL },
    { "getSelectedColumnCount", NULL, "I", 0x401, NULL },
    { "getSelectedColumns", NULL, "LIOSIntArray", 0x401, NULL },
    { "getSortColumn", NULL, "I", 0x401, NULL },
    { "getVisibleColumnCount", NULL, "I", 0x401, NULL },
    { "isAutoSizeRows", NULL, "Z", 0x401, NULL },
    { "isEditing", NULL, "Z", 0x401, NULL },
    { "getViewRect", NULL, "LRAREUIRectangle", 0x401, NULL },
    { "getMainTable", NULL, "LRAREiTableComponent", 0x401, NULL },
    { "getRowHeaderTable", NULL, "LRAREiTableComponent", 0x401, NULL },
    { "getRowFooterTable", NULL, "LRAREiTableComponent", 0x401, NULL },
    { "getColumnHeaderTable", NULL, "LRAREiTableComponent", 0x401, NULL },
    { "getColumnFooterTable", NULL, "LRAREiTableComponent", 0x401, NULL },
    { "getTableType", NULL, "LRAREiTableComponent_TableTypeEnum", 0x401, NULL },
    { "setTableTypeWithRAREiTableComponent_TableTypeEnum:", NULL, "V", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "isMainTable", NULL, "Z", 0x401, NULL },
    { "isMultiTableComponent", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiTableComponent = { "iTableComponent", "com.appnativa.rare.ui.table", NULL, 0x201, 52, methods, 0, NULL, 0, NULL};
  return &_RAREiTableComponent;
}

@end

static RAREiTableComponent_GridViewTypeEnum *RAREiTableComponent_GridViewTypeEnum_VERTICAL_WRAP;
static RAREiTableComponent_GridViewTypeEnum *RAREiTableComponent_GridViewTypeEnum_HORIZONTAL_WRAP;
IOSObjectArray *RAREiTableComponent_GridViewTypeEnum_values;

@implementation RAREiTableComponent_GridViewTypeEnum

+ (RAREiTableComponent_GridViewTypeEnum *)VERTICAL_WRAP {
  return RAREiTableComponent_GridViewTypeEnum_VERTICAL_WRAP;
}
+ (RAREiTableComponent_GridViewTypeEnum *)HORIZONTAL_WRAP {
  return RAREiTableComponent_GridViewTypeEnum_HORIZONTAL_WRAP;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiTableComponent_GridViewTypeEnum class]) {
    RAREiTableComponent_GridViewTypeEnum_VERTICAL_WRAP = [[RAREiTableComponent_GridViewTypeEnum alloc] initWithNSString:@"VERTICAL_WRAP" withInt:0];
    RAREiTableComponent_GridViewTypeEnum_HORIZONTAL_WRAP = [[RAREiTableComponent_GridViewTypeEnum alloc] initWithNSString:@"HORIZONTAL_WRAP" withInt:1];
    RAREiTableComponent_GridViewTypeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiTableComponent_GridViewTypeEnum_VERTICAL_WRAP, RAREiTableComponent_GridViewTypeEnum_HORIZONTAL_WRAP, nil } count:2 type:[IOSClass classWithClass:[RAREiTableComponent_GridViewTypeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiTableComponent_GridViewTypeEnum_values];
}

+ (RAREiTableComponent_GridViewTypeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiTableComponent_GridViewTypeEnum_values count]; i++) {
    RAREiTableComponent_GridViewTypeEnum *e = RAREiTableComponent_GridViewTypeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiTableComponent_GridViewTypeEnum"};
  static J2ObjcClassInfo _RAREiTableComponent_GridViewTypeEnum = { "GridViewType", "com.appnativa.rare.ui.table", "iTableComponent", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiTableComponent_GridViewTypeEnum;
}

@end

static RAREiTableComponent_TableTypeEnum *RAREiTableComponent_TableTypeEnum_HEADER;
static RAREiTableComponent_TableTypeEnum *RAREiTableComponent_TableTypeEnum_MAIN;
static RAREiTableComponent_TableTypeEnum *RAREiTableComponent_TableTypeEnum_FOOTER;
static RAREiTableComponent_TableTypeEnum *RAREiTableComponent_TableTypeEnum_MULTI;
IOSObjectArray *RAREiTableComponent_TableTypeEnum_values;

@implementation RAREiTableComponent_TableTypeEnum

+ (RAREiTableComponent_TableTypeEnum *)HEADER {
  return RAREiTableComponent_TableTypeEnum_HEADER;
}
+ (RAREiTableComponent_TableTypeEnum *)MAIN {
  return RAREiTableComponent_TableTypeEnum_MAIN;
}
+ (RAREiTableComponent_TableTypeEnum *)FOOTER {
  return RAREiTableComponent_TableTypeEnum_FOOTER;
}
+ (RAREiTableComponent_TableTypeEnum *)MULTI {
  return RAREiTableComponent_TableTypeEnum_MULTI;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiTableComponent_TableTypeEnum class]) {
    RAREiTableComponent_TableTypeEnum_HEADER = [[RAREiTableComponent_TableTypeEnum alloc] initWithNSString:@"HEADER" withInt:0];
    RAREiTableComponent_TableTypeEnum_MAIN = [[RAREiTableComponent_TableTypeEnum alloc] initWithNSString:@"MAIN" withInt:1];
    RAREiTableComponent_TableTypeEnum_FOOTER = [[RAREiTableComponent_TableTypeEnum alloc] initWithNSString:@"FOOTER" withInt:2];
    RAREiTableComponent_TableTypeEnum_MULTI = [[RAREiTableComponent_TableTypeEnum alloc] initWithNSString:@"MULTI" withInt:3];
    RAREiTableComponent_TableTypeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiTableComponent_TableTypeEnum_HEADER, RAREiTableComponent_TableTypeEnum_MAIN, RAREiTableComponent_TableTypeEnum_FOOTER, RAREiTableComponent_TableTypeEnum_MULTI, nil } count:4 type:[IOSClass classWithClass:[RAREiTableComponent_TableTypeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiTableComponent_TableTypeEnum_values];
}

+ (RAREiTableComponent_TableTypeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiTableComponent_TableTypeEnum_values count]; i++) {
    RAREiTableComponent_TableTypeEnum *e = RAREiTableComponent_TableTypeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiTableComponent_TableTypeEnum"};
  static J2ObjcClassInfo _RAREiTableComponent_TableTypeEnum = { "TableType", "com.appnativa.rare.ui.table", "iTableComponent", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiTableComponent_TableTypeEnum;
}

@end