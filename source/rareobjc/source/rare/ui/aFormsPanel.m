//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aFormsPanel.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSBooleanArray.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/jgoodies/forms/layout/CellConstraints.h"
#include "com/appnativa/jgoodies/forms/layout/ColumnSpec.h"
#include "com/appnativa/jgoodies/forms/layout/ConstantSize.h"
#include "com/appnativa/jgoodies/forms/layout/FormLayout.h"
#include "com/appnativa/jgoodies/forms/layout/FormSpec.h"
#include "com/appnativa/jgoodies/forms/layout/RowSpec.h"
#include "com/appnativa/jgoodies/forms/layout/Size.h"
#include "com/appnativa/jgoodies/forms/layout/Sizes.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/aComponent.h"
#include "com/appnativa/rare/ui/aFormsPanel.h"
#include "com/appnativa/rare/ui/iFormsPanel.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "java/lang/StringBuilder.h"

@implementation RAREaFormsPanel

static RARERowSpec * RAREaFormsPanel_emptyRowSpec_;
static RAREColumnSpec * RAREaFormsPanel_emptyColumnSpec_;
static RARERowSpec * RAREaFormsPanel_defaultRowSpec_;
static RAREColumnSpec * RAREaFormsPanel_defaultColumnSpec_;

+ (RARERowSpec *)emptyRowSpec {
  return RAREaFormsPanel_emptyRowSpec_;
}

+ (void)setEmptyRowSpec:(RARERowSpec *)emptyRowSpec {
  RAREaFormsPanel_emptyRowSpec_ = emptyRowSpec;
}

+ (RAREColumnSpec *)emptyColumnSpec {
  return RAREaFormsPanel_emptyColumnSpec_;
}

+ (void)setEmptyColumnSpec:(RAREColumnSpec *)emptyColumnSpec {
  RAREaFormsPanel_emptyColumnSpec_ = emptyColumnSpec;
}

+ (RARERowSpec *)defaultRowSpec {
  return RAREaFormsPanel_defaultRowSpec_;
}

+ (void)setDefaultRowSpec:(RARERowSpec *)defaultRowSpec {
  RAREaFormsPanel_defaultRowSpec_ = defaultRowSpec;
}

+ (RAREColumnSpec *)defaultColumnSpec {
  return RAREaFormsPanel_defaultColumnSpec_;
}

+ (void)setDefaultColumnSpec:(RAREColumnSpec *)defaultColumnSpec {
  RAREaFormsPanel_defaultColumnSpec_ = defaultColumnSpec;
}

- (id)init {
  return [super init];
}

- (id)initWithId:(id)view {
  return [super initWithId:view];
}

- (int)addColumnWithInt:(int)index
           withNSString:(NSString *)colspec {
  RAREFormLayout *l = [self getFormLayout];
  RAREColumnSpec *c = [RAREColumnSpec decodeWithNSString:(colspec == nil) ? @"FILL:DEFAULT:NONE" : colspec];
  if ((index < 0) || (index >= [((RAREFormLayout *) nil_chk(l)) getColumnCount])) {
    [((RAREFormLayout *) nil_chk(l)) appendColumnWithRAREColumnSpec:c];
    return [l getColumnCount] - 1;
  }
  else {
    [l insertColumnWithRAREiParentComponent:self withInt:index + 1 withRAREColumnSpec:c];
    return index;
  }
}

- (void)addColumnSpacingWithInt:(int)space {
  id<RARESize> s = (space == 0) ? [RARESizes ZERO] : [RARESizes pixelWithInt:space];
  RAREColumnSpec *c = [[RAREColumnSpec alloc] initWithRAREFormSpec_DefaultAlignment:[RAREColumnSpec DEFAULT] withRARESize:s withDouble:0];
  RAREFormLayout *l = [self getFormLayout];
  int len = [((RAREFormLayout *) nil_chk(l)) getColumnCount];
  for (int i = len; i > 1; i--) {
    [l insertColumnWithRAREiParentComponent:self withInt:i withRAREColumnSpec:c];
  }
}

- (void)addComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                       withInt:(int)col
                                       withInt:(int)row {
  [self addComponentWithRAREiPlatformComponent:comp withInt:col withInt:row withInt:1 withInt:1];
}

- (void)addComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                       withInt:(int)col
                                       withInt:(int)row
                                       withInt:(int)rowspan
                                       withInt:(int)colspan {
  [self ensureGridWithInt:col + 1 withInt:row + 1 withBoolean:NO withBoolean:NO withInt:0 withInt:0];
  [self addWithRAREiPlatformComponent:comp withId:[self createConstraintsWithInt:col withInt:row withInt:rowspan withInt:colspan]];
}

- (int)addRowWithInt:(int)index
        withNSString:(NSString *)rowspec {
  RAREFormLayout *l = [self getFormLayout];
  RARERowSpec *r = [RARERowSpec decodeWithNSString:(rowspec == nil) ? @"CENTER:DEFAULT:NONE" : rowspec];
  if ((index < 0) || (index >= [((RAREFormLayout *) nil_chk(l)) getRowCount])) {
    [((RAREFormLayout *) nil_chk(l)) appendRowWithRARERowSpec:r];
    return [l getRowCount] - 1;
  }
  else {
    [l insertRowWithRAREiParentComponent:self withInt:index + 1 withRARERowSpec:r];
    return index;
  }
}

- (void)addRowSpacingWithInt:(int)space {
  id<RARESize> s = (space == 0) ? [RARESizes ZERO] : [RARESizes pixelWithInt:space];
  RARERowSpec *r = [[RARERowSpec alloc] initWithRAREFormSpec_DefaultAlignment:[RARERowSpec DEFAULT] withRARESize:s withDouble:0];
  RAREFormLayout *l = [self getFormLayout];
  int len = [((RAREFormLayout *) nil_chk(l)) getRowCount];
  for (int i = len; i > 1; i--) {
    [l insertRowWithRAREiParentComponent:self withInt:i withRARERowSpec:r];
  }
}

- (void)addSpacerColumnWithInt:(int)space {
  id<RARESize> s = (space == 0) ? [RARESizes ZERO] : [RARESizes pixelWithInt:space];
  RAREColumnSpec *c = [[RAREColumnSpec alloc] initWithRAREFormSpec_DefaultAlignment:[RAREColumnSpec DEFAULT] withRARESize:s withDouble:0];
  RAREFormLayout *l = [self getFormLayout];
  [((RAREFormLayout *) nil_chk(l)) appendColumnWithRAREColumnSpec:c];
}

- (void)addSpacerRowWithInt:(int)space {
  RAREFormLayout *l = [self getFormLayout];
  id<RARESize> s = (space == 0) ? [RARESizes ZERO] : [RARESizes pixelWithInt:space];
  RARERowSpec *r = [[RARERowSpec alloc] initWithRAREFormSpec_DefaultAlignment:[RARERowSpec DEFAULT] withRARESize:s withDouble:0];
  [((RAREFormLayout *) nil_chk(l)) appendRowWithRARERowSpec:r];
}

- (void)addSpacingWithInt:(int)rowSpace
                  withInt:(int)columnSpace {
  [self addColumnSpacingWithInt:columnSpace];
  [self addRowSpacingWithInt:rowSpace];
}

- (RARECellConstraints *)createConstraintsWithInt:(int)col
                                          withInt:(int)row
                                          withInt:(int)rowspan
                                          withInt:(int)colspan {
  row++;
  col++;
  return [[RARECellConstraints alloc] initWithInt:col withInt:row withInt:colspan withInt:rowspan];
}

- (void)dispose {
  if (![self isDisposed]) {
    RAREFormLayout *l = [self getFormLayout];
    if (l != nil) {
      [l dispose];
    }
    [super dispose];
  }
}

- (void)ensureGridWithInt:(int)cols
                  withInt:(int)rows
                  withInt:(int)rspacing
                  withInt:(int)cspacing {
  [self ensureGridWithInt:cols withInt:rows withBoolean:NO withBoolean:NO withInt:rspacing withInt:cspacing];
}

- (void)fillEmptySpace {
  RAREFormLayout *l = [self getFormLayout];
  RAREFormLayout_LayoutInfo *info = [self getLayoutInfo];
  int len = [((RAREFormLayout *) nil_chk(l)) getRowCount];
  for (int i = 0; i < len; i++) {
    if (tableLayout_ && (i % 2 == 0)) {
      continue;
    }
    if ([RAREaFormsPanel isRowEmptyWithInt:i withRAREFormLayout_LayoutInfo:info]) {
      [l setRowSpecWithInt:i + 1 withRARERowSpec:RAREaFormsPanel_emptyRowSpec_];
    }
  }
  len = [l getColumnCount];
  for (int i = 0; i < len; i++) {
    if (tableLayout_ && (i % 2 == 0)) {
      continue;
    }
    if ([RAREaFormsPanel isColumnEmptyWithInt:i withRAREFormLayout_LayoutInfo:info]) {
      [l setColumnSpecWithInt:i + 1 withRAREColumnSpec:RAREaFormsPanel_emptyColumnSpec_];
    }
  }
}

- (void)growColumnWithInt:(int)col {
  [((RAREFormLayout *) nil_chk([self getFormLayout])) setColumnSpecWithInt:col + 1 withRAREColumnSpec:[RAREColumnSpec decodeWithNSString:@"FILL:DEFAULT:GROW(1.0)"]];
}

- (void)growRowWithInt:(int)row {
  [((RAREFormLayout *) nil_chk([self getFormLayout])) setRowSpecWithInt:row + 1 withRARERowSpec:[RARERowSpec decodeWithNSString:@"CENTER:DEFAULT:GROW(1.0)"]];
}

- (void)removeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  [super removeWithRAREiPlatformComponent:c];
  if (c != nil) {
    RAREFormLayout *l = [self getFormLayout];
    if (l != nil) {
      [l removeLayoutComponentWithRAREiPlatformComponent:c];
    }
  }
}

- (void)removeColumnWithInt:(int)col {
  RAREFormLayout *l = [self getFormLayout];
  [((RAREFormLayout *) nil_chk(l)) removeColumnWithRAREiParentComponent:self withInt:col + 1];
}

- (void)removeRowWithInt:(int)row {
  RAREFormLayout *l = [self getFormLayout];
  [((RAREFormLayout *) nil_chk(l)) removeRowWithRAREiParentComponent:self withInt:row + 1];
}

- (void)setColumnGroupsWithIntArray2:(IOSObjectArray *)cg {
  [((RAREFormLayout *) nil_chk([self getFormLayout])) setColumnGroupsWithIntArray2:cg];
}

- (void)setColumnSpecWithInt:(int)col
                withNSString:(NSString *)spec {
  [((RAREFormLayout *) nil_chk([self getFormLayout])) setColumnSpecWithInt:col + 1 withRAREColumnSpec:[RAREColumnSpec decodeWithNSString:spec]];
}

- (void)setLayoutWithNSString:(NSString *)cstr
                 withNSString:(NSString *)rstr {
  [((RAREFormLayout *) nil_chk([self getFormLayout])) setLayoutWithNSString:cstr withNSString:rstr];
}

- (void)setRowGroupsWithIntArray2:(IOSObjectArray *)rg {
  [((RAREFormLayout *) nil_chk([self getFormLayout])) setRowGroupsWithIntArray2:rg];
}

- (void)setRowSpecWithInt:(int)row
             withNSString:(NSString *)spec {
  [((RAREFormLayout *) nil_chk([self getFormLayout])) setRowSpecWithInt:row + 1 withRARERowSpec:[RARERowSpec decodeWithNSString:spec]];
}

- (void)setSpacerRowWithInt:(int)row
                    withInt:(int)space {
  RAREFormLayout *l = [self getFormLayout];
  RARERowSpec *r = [RARERowSpec decodeWithNSString:[NSString stringWithFormat:@"CENTER:%dPX:NONE", space]];
  [((RAREFormLayout *) nil_chk(l)) setRowSpecWithInt:row + 1 withRARERowSpec:r];
}

- (void)setTableLayoutWithBoolean:(BOOL)tableLayout {
  self->tableLayout_ = tableLayout;
}

- (RARECellConstraints *)getCellConstraintsWithRAREUIPoint:(RAREUIPoint *)p {
  if (p == nil) {
    return nil;
  }
  RAREFormLayout_LayoutInfo *info = [self getLayoutInfo];
  IOSIntArray *cols = ((RAREFormLayout_LayoutInfo *) nil_chk(info))->columnOrigins_;
  IOSIntArray *rows = info->rowOrigins_;
  int w = [info getWidth];
  int h = [info getHeight];
  int cw;
  int rh;
  RAREUIRectangle *r = [[RAREUIRectangle alloc] init];
  for (int y = 0; y < (int) [((IOSIntArray *) nil_chk(rows)) count]; y++) {
    for (int x = 0; x < (int) [((IOSIntArray *) nil_chk(cols)) count]; x++) {
      if (x + 1 == (int) [cols count]) {
        cw = w - IOSIntArray_Get(cols, x) + 1;
      }
      else {
        cw = IOSIntArray_Get(cols, x + 1) - IOSIntArray_Get(cols, x) + 1;
      }
      if (y + 1 == (int) [rows count]) {
        rh = h - IOSIntArray_Get(rows, y) + 1;
      }
      else {
        rh = IOSIntArray_Get(rows, y + 1) - IOSIntArray_Get(rows, y) + 1;
      }
      [r setBoundsWithFloat:IOSIntArray_Get(cols, x) withFloat:IOSIntArray_Get(rows, y) withFloat:cw withFloat:rh];
      if ([r containsWithRAREUIPoint:p]) {
        return [[RARECellConstraints alloc] initWithInt:x + 1 withInt:y + 1];
      }
    }
  }
  return nil;
}

- (int)getColumnWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  RARECellConstraints *cc = [self getCellConstraintsWithRAREiPlatformComponent:component];
  return (cc == nil) ? -1 : cc->gridX_ - 1;
}

- (int)getColumnWithRAREUIPoint:(RAREUIPoint *)p {
  RARECellConstraints *cc = [self getCellConstraintsWithRAREUIPoint:p];
  return (cc == nil) ? -1 : cc->gridX_ - 1;
}

- (int)getColumnCount {
  return [((RAREFormLayout *) nil_chk([self getFormLayout])) getColumnCount];
}

- (NSString *)getColumns {
  return [self getColumnsWithInt:-1];
}

- (NSString *)getColumnsWithInt:(int)skip {
  return [RAREaFormsPanel getColumnsWithRAREFormLayout:[self getFormLayout] withInt:skip];
}

+ (NSString *)getColumnsWithRAREFormLayout:(RAREFormLayout *)l
                                   withInt:(int)skip {
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] initWithInt:80];
  int len = [((RAREFormLayout *) nil_chk(l)) getColumnCount];
  for (int i = 0; i < len; i++) {
    if (i != skip) {
      (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:[((RAREColumnSpec *) nil_chk([l getColumnSpecWithInt:i + 1])) encodeEx]])) appendWithChar:','];
    }
  }
  [sb setLengthWithInt:[sb sequenceLength] - 1];
  return [sb description];
}

- (id<RAREiPlatformComponent>)getComponent {
  return self;
}

- (id)getComponentConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  return [self getCellConstraintsWithRAREiPlatformComponent:component];
}

- (int)getFormHeight {
  return [((RAREFormLayout_LayoutInfo *) nil_chk([self getLayoutInfo])) getHeight];
}

- (RAREFormLayout *)getFormLayout {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (int)getFormWidth {
  return [((RAREFormLayout_LayoutInfo *) nil_chk([self getLayoutInfo])) getWidth];
}

- (id<RAREiPlatformComponent>)getGridComponentAtWithInt:(int)col
                                                withInt:(int)row {
  return [((RAREFormLayout *) nil_chk([self getFormLayout])) getComponentAtWithRAREiParentComponent:self withInt:col withInt:row];
}

- (int)getRowWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  RARECellConstraints *cc = [self getCellConstraintsWithRAREiPlatformComponent:component];
  return (cc == nil) ? -1 : cc->gridY_ - 1;
}

- (int)getRowWithRAREUIPoint:(RAREUIPoint *)p {
  RARECellConstraints *cc = [self getCellConstraintsWithRAREUIPoint:p];
  return (cc == nil) ? -1 : cc->gridY_ - 1;
}

- (int)getRowCount {
  return [((RAREFormLayout *) nil_chk([self getFormLayout])) getRowCount];
}

- (NSString *)getRows {
  return [self getRowsWithInt:-1];
}

- (NSString *)getRowsWithInt:(int)skip {
  return [RAREaFormsPanel getRowsWithRAREFormLayout:[self getFormLayout] withInt:skip];
}

+ (NSString *)getRowsWithRAREFormLayout:(RAREFormLayout *)l
                                withInt:(int)skip {
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] initWithInt:80];
  int len = [((RAREFormLayout *) nil_chk(l)) getRowCount];
  for (int i = 0; i < len; i++) {
    if (i != skip) {
      (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:[((RARERowSpec *) nil_chk([l getRowSpecWithInt:i + 1])) encodeEx]])) appendWithChar:','];
    }
  }
  [sb setLengthWithInt:[sb sequenceLength] - 1];
  return [sb description];
}

+ (BOOL)isColumnEmptyWithInt:(int)column
withRAREFormLayout_LayoutInfo:(RAREFormLayout_LayoutInfo *)info {
  return (IOSIntArray_Get(nil_chk(((RAREFormLayout_LayoutInfo *) nil_chk(info))->columnOrigins_), column + 1) - IOSIntArray_Get(info->columnOrigins_, column)) < 2;
}

- (IOSBooleanArray *)isColumnRowComponentsHiddenWithInt:(int)col
                                                withInt:(int)row {
  return [((RAREFormLayout *) nil_chk([self getFormLayout])) isColumnRowComponentsHiddenWithRAREiParentComponent:self withInt:col withInt:row];
}

+ (BOOL)isRowEmptyWithInt:(int)row
withRAREFormLayout_LayoutInfo:(RAREFormLayout_LayoutInfo *)info {
  return (IOSIntArray_Get(nil_chk(((RAREFormLayout_LayoutInfo *) nil_chk(info))->rowOrigins_), row + 1) - IOSIntArray_Get(info->rowOrigins_, row)) < 2;
}

- (BOOL)isTableLayout {
  return tableLayout_;
}

- (void)ensureGridWithInt:(int)cols
                  withInt:(int)rows
              withBoolean:(BOOL)growRow
              withBoolean:(BOOL)growColumn
                  withInt:(int)rspacing
                  withInt:(int)cspacing {
  RAREFormLayout *l = [self getFormLayout];
  rows -= [((RAREFormLayout *) nil_chk(l)) getRowCount];
  cols -= [l getColumnCount];
  if (rows > 0) {
    RARERowSpec *r;
    if (growRow) {
      r = [RARERowSpec decodeWithNSString:@"CENTER:DEFAULT:GROW(1.0)"];
    }
    else {
      r = [RARERowSpec decodeWithNSString:@"CENTER:DEFAULT:NONE"];
    }
    for (int i = 0; i < rows; i++) {
      if (rspacing > 0) {
        [self addSpacerRowWithInt:rspacing];
      }
      [l appendRowWithRARERowSpec:r];
    }
  }
  if (cols > 0) {
    RAREColumnSpec *c;
    if (growColumn) {
      c = [RAREColumnSpec decodeWithNSString:@"FILL:DEFAULT:GROW(1.0)"];
    }
    else {
      c = [RAREColumnSpec decodeWithNSString:@"FILL:DEFAULT:NONE"];
    }
    for (int i = 0; i < cols; i++) {
      if (cspacing > 0) {
        [self addSpacerColumnWithInt:cspacing];
      }
      [l appendColumnWithRAREColumnSpec:c];
    }
  }
}

- (RAREFormLayout_LayoutInfo *)getLayoutInfo {
  return [((RAREFormLayout *) nil_chk([self getFormLayout])) getLayoutInfoWithRAREiParentComponent:self];
}

- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  (void) [((RAREFormLayout *) nil_chk([self getFormLayout])) getMinimumSizeWithRAREiParentComponent:self withRAREUIDimension:size withFloat:maxWidth];
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  (void) [((RAREFormLayout *) nil_chk([self getFormLayout])) getPreferredSizeWithRAREiParentComponent:self withRAREUIDimension:size withFloat:maxWidth];
}

- (RARECellConstraints *)getCellConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)setCellPaintersWithRAREiPlatformPainterArray:(IOSObjectArray *)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

+ (void)initialize {
  if (self == [RAREaFormsPanel class]) {
    RAREaFormsPanel_emptyRowSpec_ = [RARERowSpec decodeWithNSString:@"14dlu"];
    RAREaFormsPanel_emptyColumnSpec_ = [RAREColumnSpec decodeWithNSString:@"14dlu"];
    RAREaFormsPanel_defaultRowSpec_ = [RARERowSpec decodeWithNSString:@"d"];
    RAREaFormsPanel_defaultColumnSpec_ = [RAREColumnSpec decodeWithNSString:@"d"];
  }
}

- (void)copyAllFieldsTo:(RAREaFormsPanel *)other {
  [super copyAllFieldsTo:other];
  other->tableLayout_ = tableLayout_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createConstraintsWithInt:withInt:withInt:withInt:", NULL, "LRARECellConstraints", 0x1, NULL },
    { "getCellConstraintsWithRAREUIPoint:", NULL, "LRARECellConstraints", 0x1, NULL },
    { "getColumns", NULL, "LNSString", 0x1, NULL },
    { "getColumnsWithInt:", NULL, "LNSString", 0x1, NULL },
    { "getColumnsWithRAREFormLayout:withInt:", NULL, "LNSString", 0x9, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getComponentConstraintsWithRAREiPlatformComponent:", NULL, "LNSObject", 0x1, NULL },
    { "getFormLayout", NULL, "LRAREFormLayout", 0x401, NULL },
    { "getGridComponentAtWithInt:withInt:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getRows", NULL, "LNSString", 0x1, NULL },
    { "getRowsWithInt:", NULL, "LNSString", 0x1, NULL },
    { "getRowsWithRAREFormLayout:withInt:", NULL, "LNSString", 0x9, NULL },
    { "isColumnEmptyWithInt:withRAREFormLayout_LayoutInfo:", NULL, "Z", 0x9, NULL },
    { "isColumnRowComponentsHiddenWithInt:withInt:", NULL, "LIOSBooleanArray", 0x1, NULL },
    { "isRowEmptyWithInt:withRAREFormLayout_LayoutInfo:", NULL, "Z", 0x9, NULL },
    { "isTableLayout", NULL, "Z", 0x1, NULL },
    { "ensureGridWithInt:withInt:withBoolean:withBoolean:withInt:withInt:", NULL, "V", 0x4, NULL },
    { "getLayoutInfo", NULL, "LRAREFormLayout_LayoutInfo", 0x4, NULL },
    { "getMinimumSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "getCellConstraintsWithRAREiPlatformComponent:", NULL, "LRARECellConstraints", 0x401, NULL },
    { "setCellPaintersWithRAREiPlatformPainterArray:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "emptyRowSpec_", NULL, 0x9, "LRARERowSpec" },
    { "emptyColumnSpec_", NULL, 0x9, "LRAREColumnSpec" },
    { "defaultRowSpec_", NULL, 0x9, "LRARERowSpec" },
    { "defaultColumnSpec_", NULL, 0x9, "LRAREColumnSpec" },
    { "tableLayout_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREaFormsPanel = { "aFormsPanel", "com.appnativa.rare.ui", NULL, 0x401, 22, methods, 5, fields, 0, NULL};
  return &_RAREaFormsPanel;
}

@end
