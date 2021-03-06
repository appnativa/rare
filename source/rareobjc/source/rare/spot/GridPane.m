//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/GridPane.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/GridPane.h"
#include "com/appnativa/rare/spot/GroupBox.h"
#include "com/appnativa/rare/spot/Region.h"
#include "com/appnativa/rare/spot/ScrollPane.h"
#include "com/appnativa/rare/spot/SplitPane.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTGridPane

- (id)init {
  return [self initRARESPOTGridPaneWithBoolean:YES];
}

- (id)initRARESPOTGridPaneWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    rows_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:24] withNSNumber:[JavaLangInteger valueOfWithInt:1] withBoolean:NO];
    columns_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:80] withNSNumber:[JavaLangInteger valueOfWithInt:1] withBoolean:NO];
    columnSpacing_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:24] withNSNumber:[JavaLangInteger valueOfWithInt:4] withBoolean:NO];
    rowSpacing_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:24] withNSNumber:[JavaLangInteger valueOfWithInt:2] withBoolean:NO];
    columnGrouping_ = nil;
    rowGrouping_ = nil;
    actAsFormViewer_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    scrollPane_ = nil;
    regions_ = [[SPOTSet alloc] initWithNSString:@"region" withISPOTElement:[[RARESPOTRegion alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTGridPaneWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    rows_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:24] withNSNumber:[JavaLangInteger valueOfWithInt:1] withBoolean:NO];
    columns_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:80] withNSNumber:[JavaLangInteger valueOfWithInt:1] withBoolean:NO];
    columnSpacing_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:24] withNSNumber:[JavaLangInteger valueOfWithInt:4] withBoolean:NO];
    rowSpacing_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withNSNumber:[JavaLangInteger valueOfWithInt:24] withNSNumber:[JavaLangInteger valueOfWithInt:2] withBoolean:NO];
    columnGrouping_ = nil;
    rowGrouping_ = nil;
    actAsFormViewer_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    scrollPane_ = nil;
    regions_ = [[SPOTSet alloc] initWithNSString:@"region" withISPOTElement:[[RARESPOTRegion alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 9;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"rows" withISPOTElement:rows_];
  [self spot_addElementWithNSString:@"columns" withISPOTElement:columns_];
  [self spot_addElementWithNSString:@"columnSpacing" withISPOTElement:columnSpacing_];
  [self spot_addElementWithNSString:@"rowSpacing" withISPOTElement:rowSpacing_];
  [self spot_addElementWithNSString:@"columnGrouping" withISPOTElement:columnGrouping_];
  [self spot_addElementWithNSString:@"rowGrouping" withISPOTElement:rowGrouping_];
  [self spot_addElementWithNSString:@"actAsFormViewer" withISPOTElement:actAsFormViewer_];
  [self spot_addElementWithNSString:@"scrollPane" withISPOTElement:scrollPane_];
  [self spot_addElementWithNSString:@"regions" withISPOTElement:regions_];
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
  int len = [((SPOTSet *) nil_chk(regions_)) size];
  for (int i = 0; i < len; i++) {
    RARESPOTRegion *r = (RARESPOTRegion *) check_class_cast([regions_ getWithInt:i], [RARESPOTRegion class]);
    id<iSPOTElement> v = [((SPOTAny *) nil_chk(((RARESPOTRegion *) nil_chk(r))->viewer_)) getValue];
    if ([(id) v isKindOfClass:[RARESPOTGroupBox class]]) {
      RARESPOTWidget *w = [((RARESPOTGroupBox *) check_class_cast(v, [RARESPOTGroupBox class])) findWidgetWithNSString:name withBoolean:useNameMap];
      if (w != nil) {
        return w;
      }
    }
    else if ([(id) v isKindOfClass:[RARESPOTGridPane class]]) {
      RARESPOTWidget *w = [((RARESPOTGridPane *) check_class_cast(v, [RARESPOTGridPane class])) findWidgetWithNSString:name withBoolean:useNameMap];
      if (w != nil) {
        return w;
      }
    }
    else if ([(id) v isKindOfClass:[RARESPOTSplitPane class]]) {
      RARESPOTWidget *w = [((RARESPOTSplitPane *) check_class_cast(v, [RARESPOTSplitPane class])) findWidgetWithNSString:name withBoolean:useNameMap];
      if (w != nil) {
        return w;
      }
    }
  }
  return nil;
}

- (RARESPOTWidget *)findWidgetWithInt:(int)x
                              withInt:(int)y {
  int len = [((SPOTSet *) nil_chk(regions_)) getCount];
  RARESPOTRegion *r;
  for (int i = 0; i < len; i++) {
    r = (RARESPOTRegion *) check_class_cast([regions_ getExWithInt:i], [RARESPOTRegion class]);
    if (([((RARESPOTRegion *) nil_chk(r)) getX] == x) && ([r getY] == y)) {
      return (RARESPOTWidget *) check_class_cast([((SPOTAny *) nil_chk(r->viewer_)) getValue], [RARESPOTWidget class]);
    }
  }
  return nil;
}

- (RARESPOTRegion *)findRegionWithNSString:(NSString *)name {
  int len = [((SPOTSet *) nil_chk(regions_)) getCount];
  RARESPOTRegion *r;
  for (int i = 0; i < len; i++) {
    r = (RARESPOTRegion *) check_class_cast([regions_ getExWithInt:i], [RARESPOTRegion class]);
    if ([((NSString *) nil_chk(name)) isEqual:[((SPOTPrintableString *) nil_chk(((RARESPOTRegion *) nil_chk(r))->name_)) getValue]]) {
      return r;
    }
  }
  return nil;
}

- (RARESPOTRegion *)findRegionWithInt:(int)x
                              withInt:(int)y {
  int len = [((SPOTSet *) nil_chk(regions_)) getCount];
  RARESPOTRegion *r;
  for (int i = 0; i < len; i++) {
    r = (RARESPOTRegion *) check_class_cast([regions_ getExWithInt:i], [RARESPOTRegion class]);
    if (([((RARESPOTRegion *) nil_chk(r)) getX] == x) && ([r getY] == y)) {
      return r;
    }
  }
  return nil;
}

- (void)copyAllFieldsTo:(RARESPOTGridPane *)other {
  [super copyAllFieldsTo:other];
  other->actAsFormViewer_ = actAsFormViewer_;
  other->columnGrouping_ = columnGrouping_;
  other->columnSpacing_ = columnSpacing_;
  other->columns_ = columns_;
  other->regions_ = regions_;
  other->rowGrouping_ = rowGrouping_;
  other->rowSpacing_ = rowSpacing_;
  other->rows_ = rows_;
  other->scrollPane_ = scrollPane_;
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
    { "getScrollPane", NULL, "LRARESPOTScrollPane", 0x1, NULL },
    { "getScrollPaneReference", NULL, "LRARESPOTScrollPane", 0x1, NULL },
    { "setScrollPaneWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "findWidgetWithNSString:withBoolean:", NULL, "LRARESPOTWidget", 0x1, NULL },
    { "findWidgetWithInt:withInt:", NULL, "LRARESPOTWidget", 0x1, NULL },
    { "findRegionWithNSString:", NULL, "LRARESPOTRegion", 0x1, NULL },
    { "findRegionWithInt:withInt:", NULL, "LRARESPOTRegion", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "rows_", NULL, 0x1, "LSPOTInteger" },
    { "columns_", NULL, 0x1, "LSPOTInteger" },
    { "columnSpacing_", NULL, 0x1, "LSPOTInteger" },
    { "rowSpacing_", NULL, 0x1, "LSPOTInteger" },
    { "columnGrouping_", NULL, 0x4, "LSPOTSet" },
    { "rowGrouping_", NULL, 0x4, "LSPOTSet" },
    { "actAsFormViewer_", NULL, 0x1, "LSPOTBoolean" },
    { "scrollPane_", NULL, 0x4, "LRARESPOTScrollPane" },
    { "regions_", NULL, 0x1, "LSPOTSet" },
  };
  static J2ObjcClassInfo _RARESPOTGridPane = { "GridPane", "com.appnativa.rare.spot", NULL, 0x1, 15, methods, 9, fields, 0, NULL};
  return &_RARESPOTGridPane;
}

@end
