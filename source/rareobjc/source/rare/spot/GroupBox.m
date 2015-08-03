//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/GroupBox.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/GridCell.h"
#include "com/appnativa/rare/spot/GridPane.h"
#include "com/appnativa/rare/spot/GroupBox.h"
#include "com/appnativa/rare/spot/Rectangle.h"
#include "com/appnativa/rare/spot/ScrollPane.h"
#include "com/appnativa/rare/spot/SplitPane.h"
#include "com/appnativa/rare/spot/StackPane.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"
#include "java/util/HashMap.h"
#include "java/util/Map.h"

@implementation RARESPOTGroupBox

- (id)init {
  return [self initRARESPOTGroupBoxWithBoolean:YES];
}

- (id)initRARESPOTGroupBoxWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    layout_ = [[RARESPOTGroupBox_CLayout alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTGroupBox_CLayout_table] withNSString:@"table" withBoolean:NO];
    rows_ = [[SPOTPrintableString alloc] initWithNSString:nil withNSString:@"1" withBoolean:NO];
    columns_ = [[SPOTPrintableString alloc] initWithNSString:nil withNSString:@"1" withBoolean:NO];
    columnSpacing_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:24] withNSNumber:[JavaLangInteger valueOfWithInt:4] withBoolean:NO];
    rowSpacing_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:24] withNSNumber:[JavaLangInteger valueOfWithInt:2] withBoolean:NO];
    columnGrouping_ = nil;
    rowGrouping_ = nil;
    cellPainters_ = nil;
    submitValue_ = [[RARESPOTGroupBox_CSubmitValue alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTGroupBox_CSubmitValue_widget_values] withNSString:@"widget_values" withBoolean:NO];
    widgets_ = [[SPOTSet alloc] initWithNSString:@"widget" withISPOTElement:[[SPOTAny alloc] initWithNSString:@"com.appnativa.rare.spot.Widget"] withInt:-1 withInt:-1 withBoolean:YES];
    scrollPane_ = nil;
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTGroupBoxWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    layout_ = [[RARESPOTGroupBox_CLayout alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTGroupBox_CLayout_table] withNSString:@"table" withBoolean:NO];
    rows_ = [[SPOTPrintableString alloc] initWithNSString:nil withNSString:@"1" withBoolean:NO];
    columns_ = [[SPOTPrintableString alloc] initWithNSString:nil withNSString:@"1" withBoolean:NO];
    columnSpacing_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:24] withNSNumber:[JavaLangInteger valueOfWithInt:4] withBoolean:NO];
    rowSpacing_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:24] withNSNumber:[JavaLangInteger valueOfWithInt:2] withBoolean:NO];
    columnGrouping_ = nil;
    rowGrouping_ = nil;
    cellPainters_ = nil;
    submitValue_ = [[RARESPOTGroupBox_CSubmitValue alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTGroupBox_CSubmitValue_widget_values] withNSString:@"widget_values" withBoolean:NO];
    widgets_ = [[SPOTSet alloc] initWithNSString:@"widget" withISPOTElement:[[SPOTAny alloc] initWithNSString:@"com.appnativa.rare.spot.Widget"] withInt:-1 withInt:-1 withBoolean:YES];
    scrollPane_ = nil;
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 11;
  self->attributeSizeHint_ += 1;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onItemChanged" withNSString:nil];
  [self spot_addElementWithNSString:@"layout" withISPOTElement:layout_];
  [((RARESPOTGroupBox_CLayout *) nil_chk(layout_)) spot_defineAttributeWithNSString:@"layoutManager" withNSString:nil];
  [layout_ spot_defineAttributeWithNSString:@"customInfo" withNSString:nil];
  [layout_ spot_defineAttributeWithNSString:@"panelComponent" withNSString:nil];
  [self spot_addElementWithNSString:@"rows" withISPOTElement:rows_];
  [self spot_addElementWithNSString:@"columns" withISPOTElement:columns_];
  [self spot_addElementWithNSString:@"columnSpacing" withISPOTElement:columnSpacing_];
  [self spot_addElementWithNSString:@"rowSpacing" withISPOTElement:rowSpacing_];
  [self spot_addElementWithNSString:@"columnGrouping" withISPOTElement:columnGrouping_];
  [self spot_addElementWithNSString:@"rowGrouping" withISPOTElement:rowGrouping_];
  [self spot_addElementWithNSString:@"cellPainters" withISPOTElement:cellPainters_];
  [self spot_addElementWithNSString:@"submitValue" withISPOTElement:submitValue_];
  [self spot_addElementWithNSString:@"widgets" withISPOTElement:widgets_];
  [self spot_addElementWithNSString:@"scrollPane" withISPOTElement:scrollPane_];
}

- (SPOTSet *)getColumnGrouping {
  return columnGrouping_;
}

- (SPOTSet *)getColumnGroupingReference {
  if (columnGrouping_ == nil) {
    columnGrouping_ = [[SPOTSet alloc] initWithNSString:@"group" withISPOTElement:[[SPOTPrintableString alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"columnGrouping" withISPOTElement:columnGrouping_];
  }
  return columnGrouping_;
}

- (void)setColumnGroupingWithISPOTElement:(id<iSPOTElement>)reference {
  columnGrouping_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"columnGrouping" withISPOTElement:reference];
}

- (SPOTSet *)getRowGrouping {
  return rowGrouping_;
}

- (SPOTSet *)getRowGroupingReference {
  if (rowGrouping_ == nil) {
    rowGrouping_ = [[SPOTSet alloc] initWithNSString:@"group" withISPOTElement:[[SPOTPrintableString alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"rowGrouping" withISPOTElement:rowGrouping_];
  }
  return rowGrouping_;
}

- (void)setRowGroupingWithISPOTElement:(id<iSPOTElement>)reference {
  rowGrouping_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"rowGrouping" withISPOTElement:reference];
}

- (SPOTSet *)getCellPainters {
  return cellPainters_;
}

- (SPOTSet *)getCellPaintersReference {
  if (cellPainters_ == nil) {
    cellPainters_ = [[SPOTSet alloc] initWithNSString:@"cell" withISPOTElement:[[RARESPOTGridCell alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"cellPainters" withISPOTElement:cellPainters_];
  }
  return cellPainters_;
}

- (void)setCellPaintersWithISPOTElement:(id<iSPOTElement>)reference {
  cellPainters_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"cellPainters" withISPOTElement:reference];
}

- (RARESPOTScrollPane *)getScrollPane {
  return scrollPane_;
}

- (RARESPOTScrollPane *)getScrollPaneReference {
  if (scrollPane_ == nil) {
    scrollPane_ = [[RARESPOTScrollPane alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"scrollPane" withISPOTElement:scrollPane_];
  }
  return scrollPane_;
}

- (void)setScrollPaneWithISPOTElement:(id<iSPOTElement>)reference {
  scrollPane_ = (RARESPOTScrollPane *) check_class_cast(reference, [RARESPOTScrollPane class]);
  (void) [self spot_setReferenceWithNSString:@"scrollPane" withISPOTElement:reference];
}

- (RARESPOTWidget *)findWidgetWithNSString:(NSString *)name
                               withBoolean:(BOOL)useNameMap {
  if (useNameMap && (_widgetNameMap_ == nil)) {
    _widgetNameMap_ = [self createWidgetMap];
  }
  if (_widgetNameMap_ != nil) {
    return [_widgetNameMap_ getWithId:name];
  }
  int len = [((SPOTSet *) nil_chk(widgets_)) getCount];
  RARESPOTWidget *w;
  for (int i = 0; i < len; i++) {
    w = (RARESPOTWidget *) check_class_cast([widgets_ getExWithInt:i], [RARESPOTWidget class]);
    if ([((NSString *) nil_chk(name)) isEqual:[((SPOTPrintableString *) nil_chk(((RARESPOTWidget *) nil_chk(w))->name_)) getValue]]) {
      return w;
    }
  }
  for (int i = 0; i < len; i++) {
    w = (RARESPOTWidget *) check_class_cast([widgets_ getExWithInt:i], [RARESPOTWidget class]);
    if ([w isKindOfClass:[RARESPOTGroupBox class]]) {
      w = [((RARESPOTGroupBox *) check_class_cast(w, [RARESPOTGroupBox class])) findWidgetWithNSString:name withBoolean:useNameMap];
      if (w != nil) {
        return w;
      }
    }
    else if ([w isKindOfClass:[RARESPOTSplitPane class]]) {
      w = [((RARESPOTSplitPane *) check_class_cast(w, [RARESPOTSplitPane class])) findWidgetWithNSString:name withBoolean:useNameMap];
      if (w != nil) {
        return w;
      }
    }
    else if ([w isKindOfClass:[RARESPOTGridPane class]]) {
      w = [((RARESPOTGridPane *) check_class_cast(w, [RARESPOTGridPane class])) findWidgetWithNSString:name withBoolean:useNameMap];
      if (w != nil) {
        return w;
      }
    }
    else if ([w isKindOfClass:[RARESPOTStackPane class]]) {
      w = [((RARESPOTStackPane *) check_class_cast(w, [RARESPOTStackPane class])) findWidgetWithNSString:name withBoolean:useNameMap];
      if (w != nil) {
        return w;
      }
    }
  }
  return nil;
}

- (RARESPOTWidget *)findWidgetWithNSString:(NSString *)name {
  return [self findWidgetWithNSString:name withBoolean:NO];
}

- (id<JavaUtilMap>)createWidgetMap {
  int len = [((SPOTSet *) nil_chk(widgets_)) getCount];
  if (len == 0) {
    return nil;
  }
  JavaUtilHashMap *map = [[JavaUtilHashMap alloc] initWithInt:len];
  RARESPOTWidget *w;
  for (int i = 0; i < len; i++) {
    w = (RARESPOTWidget *) check_class_cast([widgets_ getExWithInt:i], [RARESPOTWidget class]);
    if ([((SPOTPrintableString *) nil_chk(((RARESPOTWidget *) nil_chk(w))->name_)) getValue] != nil) {
      (void) [map putWithId:[w->name_ getValue] withId:w];
    }
  }
  return map;
}

- (RARESPOTWidget *)getWidgetWithInt:(int)index {
  return (RARESPOTWidget *) check_class_cast([((SPOTSet *) nil_chk(widgets_)) getExWithInt:index], [RARESPOTWidget class]);
}

- (RARESPOTWidget *)removeWidgetAtWithInt:(int)index {
  RARESPOTWidget *w = (RARESPOTWidget *) check_class_cast([((SPOTSet *) nil_chk(widgets_)) getExWithInt:index], [RARESPOTWidget class]);
  if (w != nil) {
    (void) [widgets_ removeWithInt:index];
  }
  return w;
}

- (int)getWidgetCount {
  return [((SPOTSet *) nil_chk(widgets_)) getCount];
}

- (RARESPOTWidget *)findWidgetWithInt:(int)x
                              withInt:(int)y {
  int len = [((SPOTSet *) nil_chk(widgets_)) getCount];
  RARESPOTWidget *w;
  for (int i = 0; i < len; i++) {
    w = (RARESPOTWidget *) check_class_cast([widgets_ getExWithInt:i], [RARESPOTWidget class]);
    if (([((RARESPOTRectangle *) nil_chk(((RARESPOTWidget *) nil_chk(w))->bounds_)) getX] == x) && ([w->bounds_ getY] == y)) {
      return w;
    }
  }
  return nil;
}

- (void)addWidgetWithRARESPOTWidget:(RARESPOTWidget *)w {
  [((SPOTSet *) nil_chk(widgets_)) addWithISPOTElement:w];
}

- (void)removeWidgetWithRARESPOTWidget:(RARESPOTWidget *)w {
  [((SPOTSet *) nil_chk(widgets_)) removeExWithISPOTElement:w];
}

- (void)copyAllFieldsTo:(RARESPOTGroupBox *)other {
  [super copyAllFieldsTo:other];
  other->_widgetNameMap_ = _widgetNameMap_;
  other->cellPainters_ = cellPainters_;
  other->columnGrouping_ = columnGrouping_;
  other->columnSpacing_ = columnSpacing_;
  other->columns_ = columns_;
  other->layout_ = layout_;
  other->rowGrouping_ = rowGrouping_;
  other->rowSpacing_ = rowSpacing_;
  other->rows_ = rows_;
  other->scrollPane_ = scrollPane_;
  other->submitValue_ = submitValue_;
  other->widgets_ = widgets_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getColumnGrouping", NULL, "LSPOTSet", 0x1, NULL },
    { "getColumnGroupingReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setColumnGroupingWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getRowGrouping", NULL, "LSPOTSet", 0x1, NULL },
    { "getRowGroupingReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setRowGroupingWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getCellPainters", NULL, "LSPOTSet", 0x1, NULL },
    { "getCellPaintersReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setCellPaintersWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getScrollPane", NULL, "LRARESPOTScrollPane", 0x1, NULL },
    { "getScrollPaneReference", NULL, "LRARESPOTScrollPane", 0x1, NULL },
    { "setScrollPaneWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "findWidgetWithNSString:withBoolean:", NULL, "LRARESPOTWidget", 0x1, NULL },
    { "findWidgetWithNSString:", NULL, "LRARESPOTWidget", 0x1, NULL },
    { "createWidgetMap", NULL, "LJavaUtilMap", 0x1, NULL },
    { "getWidgetWithInt:", NULL, "LRARESPOTWidget", 0x1, NULL },
    { "removeWidgetAtWithInt:", NULL, "LRARESPOTWidget", 0x1, NULL },
    { "findWidgetWithInt:withInt:", NULL, "LRARESPOTWidget", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "layout_", NULL, 0x1, "LRARESPOTGroupBox_CLayout" },
    { "rows_", NULL, 0x1, "LSPOTPrintableString" },
    { "columns_", NULL, 0x1, "LSPOTPrintableString" },
    { "columnSpacing_", NULL, 0x1, "LSPOTInteger" },
    { "rowSpacing_", NULL, 0x1, "LSPOTInteger" },
    { "columnGrouping_", NULL, 0x4, "LSPOTSet" },
    { "rowGrouping_", NULL, 0x4, "LSPOTSet" },
    { "cellPainters_", NULL, 0x4, "LSPOTSet" },
    { "submitValue_", NULL, 0x1, "LRARESPOTGroupBox_CSubmitValue" },
    { "widgets_", NULL, 0x1, "LSPOTSet" },
    { "scrollPane_", NULL, 0x4, "LRARESPOTScrollPane" },
  };
  static J2ObjcClassInfo _RARESPOTGroupBox = { "GroupBox", "com.appnativa.rare.spot", NULL, 0x1, 20, methods, 11, fields, 0, NULL};
  return &_RARESPOTGroupBox;
}

@end
@implementation RARESPOTGroupBox_CLayout

static IOSIntArray * RARESPOTGroupBox_CLayout__nchoices_;
static IOSObjectArray * RARESPOTGroupBox_CLayout__schoices_;

+ (int)absolute {
  return RARESPOTGroupBox_CLayout_absolute;
}

+ (int)table {
  return RARESPOTGroupBox_CLayout_table;
}

+ (int)forms {
  return RARESPOTGroupBox_CLayout_forms;
}

+ (int)flow {
  return RARESPOTGroupBox_CLayout_flow;
}

+ (int)custom {
  return RARESPOTGroupBox_CLayout_custom;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTGroupBox_CLayout__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTGroupBox_CLayout__schoices_;
}

- (id)init {
  return [self initRARESPOTGroupBox_CLayoutWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTGroupBox_CLayout__schoices_;
    _nChoices_ = RARESPOTGroupBox_CLayout__nchoices_;
    [self spot_setAttributes];
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTGroupBox_CLayoutWithJavaLangInteger:(JavaLangInteger *)ival
                                         withNSString:(NSString *)sval
                                  withJavaLangInteger:(JavaLangInteger *)idefaultval
                                         withNSString:(NSString *)sdefaultval
                                          withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTGroupBox_CLayout__schoices_;
    _nChoices_ = RARESPOTGroupBox_CLayout__nchoices_;
    [self spot_setAttributes];
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTGroupBox_CLayoutWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{absolute(1), table(2), forms(3), flow(4), custom(10) }";
}

- (void)spot_setAttributes {
  self->attributeSizeHint_ += 3;
  [self spot_defineAttributeWithNSString:@"layoutManager" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"customInfo" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"panelComponent" withNSString:nil];
}

+ (void)initialize {
  if (self == [RARESPOTGroupBox_CLayout class]) {
    RARESPOTGroupBox_CLayout__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 1, 2, 3, 4, 10 } count:5];
    RARESPOTGroupBox_CLayout__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"absolute", @"table", @"forms", @"flow", @"custom" } count:5 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_setAttributes", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "absolute_", NULL, 0x19, "I" },
    { "table_", NULL, 0x19, "I" },
    { "forms_", NULL, 0x19, "I" },
    { "flow_", NULL, 0x19, "I" },
    { "custom_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTGroupBox_CLayout = { "CLayout", "com.appnativa.rare.spot", "GroupBox", 0x9, 2, methods, 7, fields, 0, NULL};
  return &_RARESPOTGroupBox_CLayout;
}

@end
@implementation RARESPOTGroupBox_CSubmitValue

static IOSIntArray * RARESPOTGroupBox_CSubmitValue__nchoices_;
static IOSObjectArray * RARESPOTGroupBox_CSubmitValue__schoices_;

+ (int)widget_values {
  return RARESPOTGroupBox_CSubmitValue_widget_values;
}

+ (int)viewer_linked_data {
  return RARESPOTGroupBox_CSubmitValue_viewer_linked_data;
}

+ (int)viewer_value {
  return RARESPOTGroupBox_CSubmitValue_viewer_value;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTGroupBox_CSubmitValue__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTGroupBox_CSubmitValue__schoices_;
}

- (id)init {
  return [self initRARESPOTGroupBox_CSubmitValueWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTGroupBox_CSubmitValue__schoices_;
    _nChoices_ = RARESPOTGroupBox_CSubmitValue__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTGroupBox_CSubmitValueWithJavaLangInteger:(JavaLangInteger *)ival
                                              withNSString:(NSString *)sval
                                       withJavaLangInteger:(JavaLangInteger *)idefaultval
                                              withNSString:(NSString *)sdefaultval
                                               withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTGroupBox_CSubmitValue__schoices_;
    _nChoices_ = RARESPOTGroupBox_CSubmitValue__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTGroupBox_CSubmitValueWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{widget_values(0), viewer_linked_data(1), viewer_value(2) }";
}

+ (void)initialize {
  if (self == [RARESPOTGroupBox_CSubmitValue class]) {
    RARESPOTGroupBox_CSubmitValue__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2 } count:3];
    RARESPOTGroupBox_CSubmitValue__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"widget_values", @"viewer_linked_data", @"viewer_value" } count:3 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "widget_values_", NULL, 0x19, "I" },
    { "viewer_linked_data_", NULL, 0x19, "I" },
    { "viewer_value_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTGroupBox_CSubmitValue = { "CSubmitValue", "com.appnativa.rare.spot", "GroupBox", 0x9, 1, methods, 5, fields, 0, NULL};
  return &_RARESPOTGroupBox_CSubmitValue;
}

@end
