//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/DataItemTableModel.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/iTableModel.h"
#include "com/appnativa/rare/ui/table/DataItemTableModel.h"
#include "com/appnativa/rare/ui/table/aDataItemTableModel.h"
#include "com/appnativa/util/iFilter.h"

@implementation RAREDataItemTableModel

- (id)init {
  if (self = [super init]) {
    uniformHeight_ = YES;
  }
  return self;
}

- (id<RAREiTableModel>)createEmptyCopy {
  return [[RAREDataItemTableModel alloc] init];
}

- (void)refreshItems {
  [self fireTableDataChanged];
}

- (void)rowChangedWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  int n = [self indexForRowWithRARERenderableDataItem:item];
  if (n != -1) {
    [((RARERenderableDataItem *) nil_chk(item)) setHeightWithInt:-1];
    [item setSpanningDataWithId:nil];
    [super fireTableRowsUpdatedWithInt:n withInt:n];
  }
}

- (void)rowsChangedWithIntArray:(IOSIntArray *)index {
  int min = 0;
  int max = 0;
  {
    IOSIntArray *a__ = index;
    int const *b__ = ((IOSIntArray *) nil_chk(a__))->buffer_;
    int const *e__ = b__ + a__->size_;
    while (b__ < e__) {
      int i = (*b__++);
      if (i < min) {
        min = i;
      }
      if (i > max) {
        max = i;
      }
      if (!uniformHeight_) {
        [((RARERenderableDataItem *) nil_chk([self getWithInt:i])) setHeightWithInt:-1];
      }
    }
  }
  [super fireTableRowsUpdatedWithInt:min withInt:max];
}

- (void)setColumnDescriptionWithRAREColumn:(RAREColumn *)itemDescription {
}

- (id<RAREUTiFilter>)getLastFilter {
  return nil;
}

- (void)fireTableRowsUpdatedWithInt:(int)firstRow
                            withInt:(int)lastRow {
  if (!uniformHeight_) {
    if (firstRow < lastRow) {
      for (int i = firstRow; i <= lastRow; i++) {
        [((RARERenderableDataItem *) nil_chk([self getWithInt:i])) setHeightWithInt:-1];
      }
    }
    else {
      for (int i = lastRow; i <= firstRow; i++) {
        [((RARERenderableDataItem *) nil_chk([self getWithInt:i])) setHeightWithInt:-1];
      }
    }
  }
  [super fireTableRowsUpdatedWithInt:firstRow withInt:lastRow];
}

- (void)fireTableDataChanged {
  if (!uniformHeight_) {
    int len = [self size];
    for (int i = 0; i < len; i++) {
      [((RARERenderableDataItem *) nil_chk([self getWithInt:i])) setHeightWithInt:-1];
    }
  }
  [super fireTableDataChanged];
}

- (void)fireTableRowsInsertedWithInt:(int)firstRow
                             withInt:(int)lastRow {
  if (!uniformHeight_) {
    if (firstRow < lastRow) {
      for (int i = firstRow; i <= lastRow; i++) {
        [((RARERenderableDataItem *) nil_chk([self getWithInt:i])) setHeightWithInt:-1];
      }
    }
    else {
      for (int i = lastRow; i <= firstRow; i++) {
        [((RARERenderableDataItem *) nil_chk([self getWithInt:i])) setHeightWithInt:-1];
      }
    }
  }
  [super fireTableRowsInsertedWithInt:firstRow withInt:lastRow];
}

- (BOOL)isUniformHeight {
  return uniformHeight_;
}

- (void)setUniformHeightWithBoolean:(BOOL)uniformHeight {
  self->uniformHeight_ = uniformHeight;
}

- (void)setEditingWithBoolean:(BOOL)b {
}

- (void)copyAllFieldsTo:(RAREDataItemTableModel *)other {
  [super copyAllFieldsTo:other];
  other->uniformHeight_ = uniformHeight_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createEmptyCopy", NULL, "LRAREiTableModel", 0x1, NULL },
    { "rowsChangedWithIntArray:", NULL, "V", 0x81, NULL },
    { "getLastFilter", NULL, "LRAREUTiFilter", 0x1, NULL },
    { "fireTableDataChanged", NULL, "V", 0x4, NULL },
    { "fireTableRowsInsertedWithInt:withInt:", NULL, "V", 0x4, NULL },
    { "isUniformHeight", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREDataItemTableModel = { "DataItemTableModel", "com.appnativa.rare.ui.table", NULL, 0x1, 6, methods, 0, NULL, 0, NULL};
  return &_RAREDataItemTableModel;
}

@end
