//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/ui/table/TableStyle.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UICursor.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/table/TableStyle.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/IllegalArgumentException.h"

@implementation RARETableStyle

- (id)init {
  if (self = [super init]) {
    gridLineStroke_ = nil;
    headerHotspotSize_ = 0;
    showHorizontalLines_ = NO;
    showVerticalLines_ = NO;
    hiliteSortColumn_ = YES;
    fixedRowSize_ = YES;
    extendBackgroundRendering_ = YES;
    columnSortingAllowed_ = YES;
    columnSelectionAllowed_ = YES;
    columnResizingAllowed_ = YES;
    columnReorderingAllowed_ = YES;
  }
  return self;
}

- (RARETableStyle *)copy__ {
  @try {
    return (RARETableStyle *) check_class_cast([self clone], [RARETableStyle class]);
  }
  @catch (JavaLangCloneNotSupportedException *ex) {
    return nil;
  }
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RARETableStyle *)other {
  [super copyAllFieldsTo:other];
  other->backgroundHilite_ = backgroundHilite_;
  other->backgroundHiliteColor_ = backgroundHiliteColor_;
  other->colHeaderHotspotsSupported_ = colHeaderHotspotsSupported_;
  other->columnReorderingAllowed_ = columnReorderingAllowed_;
  other->columnResizingAllowed_ = columnResizingAllowed_;
  other->columnSelectionAllowed_ = columnSelectionAllowed_;
  other->columnSortingAllowed_ = columnSortingAllowed_;
  other->extendBackgroundRendering_ = extendBackgroundRendering_;
  other->fixedRowSize_ = fixedRowSize_;
  other->gridColor_ = gridColor_;
  other->gridLineStroke_ = gridLineStroke_;
  other->headerAction_ = headerAction_;
  other->headerBottomMarginColor_ = headerBottomMarginColor_;
  other->headerCellPainter_ = headerCellPainter_;
  other->headerFillerPainter_ = headerFillerPainter_;
  other->headerFont_ = headerFont_;
  other->headerForeground_ = headerForeground_;
  other->headerHotspotAction_ = headerHotspotAction_;
  other->headerHotspotCursor_ = headerHotspotCursor_;
  other->headerHotspotIcon_ = headerHotspotIcon_;
  other->headerHotspotSize_ = headerHotspotSize_;
  other->headerMarginColor_ = headerMarginColor_;
  other->hiliteSortColumn_ = hiliteSortColumn_;
  other->rowHeaderFooterSelectionPainted_ = rowHeaderFooterSelectionPainted_;
  other->showFocusRectangle_ = showFocusRectangle_;
  other->showHorizontalLines_ = showHorizontalLines_;
  other->showVerticalLines_ = showVerticalLines_;
  other->sortColumnHiliteColor_ = sortColumnHiliteColor_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "copy__", NULL, "LRARETableStyle", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "gridLineStroke_", NULL, 0x1, "LRAREUIStroke" },
    { "headerHotspotSize_", NULL, 0x1, "I" },
    { "showHorizontalLines_", NULL, 0x1, "Z" },
    { "showVerticalLines_", NULL, 0x1, "Z" },
    { "hiliteSortColumn_", NULL, 0x1, "Z" },
    { "fixedRowSize_", NULL, 0x1, "Z" },
    { "extendBackgroundRendering_", NULL, 0x1, "Z" },
    { "columnSortingAllowed_", NULL, 0x1, "Z" },
    { "columnSelectionAllowed_", NULL, 0x1, "Z" },
    { "columnResizingAllowed_", NULL, 0x1, "Z" },
    { "columnReorderingAllowed_", NULL, 0x1, "Z" },
    { "backgroundHilite_", NULL, 0x1, "LRARETableStyle_BackgroundHighlightEnum" },
    { "backgroundHiliteColor_", NULL, 0x1, "LRAREUIColor" },
    { "colHeaderHotspotsSupported_", NULL, 0x1, "Z" },
    { "gridColor_", NULL, 0x1, "LRAREUIColor" },
    { "headerAction_", NULL, 0x1, "LNSString" },
    { "headerFont_", NULL, 0x1, "LRAREUIFont" },
    { "headerForeground_", NULL, 0x1, "LRAREUIColor" },
    { "headerMarginColor_", NULL, 0x1, "LRAREUIColor" },
    { "headerBottomMarginColor_", NULL, 0x1, "LRAREUIColor" },
    { "headerHotspotAction_", NULL, 0x1, "LNSString" },
    { "headerHotspotCursor_", NULL, 0x1, "LRAREUICursor" },
    { "headerHotspotIcon_", NULL, 0x1, "LRAREiPlatformIcon" },
    { "headerCellPainter_", NULL, 0x1, "LRAREPaintBucket" },
    { "headerFillerPainter_", NULL, 0x1, "LRAREPaintBucket" },
    { "rowHeaderFooterSelectionPainted_", NULL, 0x1, "Z" },
    { "showFocusRectangle_", NULL, 0x1, "Z" },
    { "sortColumnHiliteColor_", NULL, 0x1, "LRAREUIColor" },
  };
  static J2ObjcClassInfo _RARETableStyle = { "TableStyle", "com.appnativa.rare.ui.table", NULL, 0x1, 1, methods, 28, fields, 0, NULL};
  return &_RARETableStyle;
}

@end

static RARETableStyle_BackgroundHighlightEnum *RARETableStyle_BackgroundHighlightEnum_ROW;
static RARETableStyle_BackgroundHighlightEnum *RARETableStyle_BackgroundHighlightEnum_COLUMN;
IOSObjectArray *RARETableStyle_BackgroundHighlightEnum_values;

@implementation RARETableStyle_BackgroundHighlightEnum

+ (RARETableStyle_BackgroundHighlightEnum *)ROW {
  return RARETableStyle_BackgroundHighlightEnum_ROW;
}
+ (RARETableStyle_BackgroundHighlightEnum *)COLUMN {
  return RARETableStyle_BackgroundHighlightEnum_COLUMN;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RARETableStyle_BackgroundHighlightEnum class]) {
    RARETableStyle_BackgroundHighlightEnum_ROW = [[RARETableStyle_BackgroundHighlightEnum alloc] initWithNSString:@"ROW" withInt:0];
    RARETableStyle_BackgroundHighlightEnum_COLUMN = [[RARETableStyle_BackgroundHighlightEnum alloc] initWithNSString:@"COLUMN" withInt:1];
    RARETableStyle_BackgroundHighlightEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RARETableStyle_BackgroundHighlightEnum_ROW, RARETableStyle_BackgroundHighlightEnum_COLUMN, nil } count:2 type:[IOSClass classWithClass:[RARETableStyle_BackgroundHighlightEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RARETableStyle_BackgroundHighlightEnum_values];
}

+ (RARETableStyle_BackgroundHighlightEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RARETableStyle_BackgroundHighlightEnum_values count]; i++) {
    RARETableStyle_BackgroundHighlightEnum *e = RARETableStyle_BackgroundHighlightEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRARETableStyle_BackgroundHighlightEnum"};
  static J2ObjcClassInfo _RARETableStyle_BackgroundHighlightEnum = { "BackgroundHighlight", "com.appnativa.rare.ui.table", "TableStyle", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RARETableStyle_BackgroundHighlightEnum;
}

@end
