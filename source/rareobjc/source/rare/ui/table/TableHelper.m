//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/table/TableHelper.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/border/UILineBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformItemRenderer.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/UIBackgroundPainter.h"
#include "com/appnativa/rare/ui/renderer/UILabelRenderer.h"
#include "com/appnativa/rare/ui/table/TableHelper.h"
#include "com/appnativa/util/FilterableList.h"
#include "com/appnativa/util/iFilterableList.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Math.h"
#include "java/util/ArrayList.h"
#include "java/util/List.h"

@implementation RARETableHelper

static int RARETableHelper_maxRowsForSizeCalculation_ = 0;
static RAREUILabelRenderer * RARETableHelper_computeSizeRenderer_;
static int RARETableHelper_defaultPreferredRows_;

+ (int)maxRowsForSizeCalculation {
  return RARETableHelper_maxRowsForSizeCalculation_;
}

+ (int *)maxRowsForSizeCalculationRef {
  return &RARETableHelper_maxRowsForSizeCalculation_;
}

+ (RAREUILabelRenderer *)computeSizeRenderer {
  return RARETableHelper_computeSizeRenderer_;
}

+ (void)setComputeSizeRenderer:(RAREUILabelRenderer *)computeSizeRenderer {
  RARETableHelper_computeSizeRenderer_ = computeSizeRenderer;
}

+ (int)defaultPreferredRows {
  return RARETableHelper_defaultPreferredRows_;
}

+ (int *)defaultPreferredRowsRef {
  return &RARETableHelper_defaultPreferredRows_;
}

+ (int)getFlatItemCountWithJavaUtilList:(id<JavaUtilList>)rows {
  int count = 0;
  for (RARERenderableDataItem * __strong row in nil_chk(rows)) {
    if (row != nil) {
      count += [row size];
    }
  }
  return count;
}

+ (id<RAREUTiFilterableList>)getSubListWithJavaUtilList:(id<JavaUtilList>)list
                                                withInt:(int)startColumn
                                                withInt:(int)endColumn
                              withRAREUTiFilterableList:(id<RAREUTiFilterableList>)outList {
  int len = [((id<JavaUtilList>) nil_chk(list)) size];
  int count = endColumn - startColumn;
  if (outList == nil) {
    outList = [[RAREUTFilterableList alloc] initWithInt:len];
  }
  for (int i = 0; i < len; i++) {
    RARERenderableDataItem *row = [[RARERenderableDataItem alloc] init];
    [row ensureCapacityWithInt:count];
    [((id<RAREUTiFilterableList>) nil_chk(outList)) addWithId:row];
    RARERenderableDataItem *orow = [list getWithInt:i];
    [row setColumnSpanWithInt:[((RARERenderableDataItem *) nil_chk(orow)) getColumnSpan]];
    [row setLinkedDataWithId:[orow getLinkedData]];
    for (int col = startColumn; col <= endColumn; col++) {
      [row addWithId:[orow getItemExWithInt:col]];
    }
  }
  return outList;
}

+ (int)calculateColumnSizesWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                  withRAREColumnArray:(IOSObjectArray *)cols
                                              withInt:(int)tableWidth
                                          withBoolean:(BOOL)fit {
  if (tableWidth < 1) {
    return 0;
  }
  id<JavaUtilList> llist = nil;
  RAREColumn *leftOverCol = nil;
  int size = 0;
  int w;
  int columnCount = (int) [((IOSObjectArray *) nil_chk(cols)) count];
  RAREColumn *c;
  int leftOver;
  for (int i = 0; i < columnCount; i++) {
    c = IOSObjectArray_Get(cols, i);
    if ((((RAREColumn *) nil_chk(c))->preferedWidth_ == 0) && [c isVisible]) {
      if (leftOverCol == nil) {
        leftOverCol = c;
      }
      else {
        if (llist == nil) {
          llist = [[JavaUtilArrayList alloc] init];
          [llist addWithId:leftOverCol];
        }
        [((id<JavaUtilList>) nil_chk(llist)) addWithId:c];
      }
      continue;
    }
    w = [c calculatePreferedWidthWithRAREiPlatformComponent:comp withFloat:tableWidth];
    [c setWidthWithInt:w];
    if ([c isVisible]) {
      size += w;
    }
  }
  size = tableWidth - size;
  leftOver = size;
  if ((size > 0) && (columnCount > 0)) {
    if (llist != nil) {
      int len = [llist size];
      size /= len;
      for (int i = 0; i < len; i++) {
        [((RAREColumn *) nil_chk([llist getWithInt:i])) setWidthWithInt:size];
      }
    }
    else if (leftOverCol != nil) {
      [leftOverCol setWidthWithInt:size];
    }
    else {
      int len = 0;
      for (int i = 0; i < columnCount; i++) {
        c = IOSObjectArray_Get(cols, i);
        if (!((RAREColumn *) nil_chk(c))->sizeFixed_ && [c isVisible]) {
          len++;
        }
      }
      if (len > 0) {
        size /= len;
        for (int i = 0; i < columnCount; i++) {
          c = IOSObjectArray_Get(cols, i);
          if (!((RAREColumn *) nil_chk(c))->sizeFixed_ && [c isVisible]) {
            [c setWidthWithInt:size + [c getWidth]];
          }
        }
      }
    }
  }
  else if ((size < 0) && (columnCount > 0) && fit) {
    if (llist != nil) {
      int len = [llist size];
      size = (int) [JavaLangMath ceilWithDouble:(float) -size / (float) len];
      for (int i = 0; i < len; i++) {
        c = [llist getWithInt:i];
        [c setWidthWithInt:[JavaLangMath maxWithInt:0 withInt:[((RAREColumn *) nil_chk(c)) getWidth] + size]];
      }
    }
    else if (leftOverCol != nil) {
      [leftOverCol setWidthWithInt:[JavaLangMath maxWithInt:0 withInt:[leftOverCol getWidth] + size]];
    }
  }
  return leftOver;
}

+ (void)calculateItemSizeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
                      withRAREiPlatformItemRenderer:(id<RAREiPlatformItemRenderer>)renderer
                                     withRAREColumn:(RAREColumn *)col
                         withRARERenderableDataItem:(RARERenderableDataItem *)item
                                            withInt:(int)row
                         withRARERenderableDataItem:(RARERenderableDataItem *)rowItem
                                withRAREUIDimension:(RAREUIDimension *)size
                                            withInt:(int)maxWidth
                                            withInt:(int)minHeight {
  id<RAREiPlatformComponent> c = [((RARERenderableDataItem *) nil_chk(item)) getRenderingComponent];
  if (c != nil) {
    (void) [c getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
  }
  else {
    id<RAREiPlatformRenderingComponent> rc = (col == nil) ? nil : [col getCellRenderer];
    if (rc == nil) {
      rc = RARETableHelper_computeSizeRenderer_;
      if (rc == nil) {
        rc = RARETableHelper_computeSizeRenderer_ = [[RAREUILabelRenderer alloc] init];
      }
    }
    id<JavaLangCharSequence> text = [((id<RAREiPlatformItemRenderer>) nil_chk(renderer)) configureRenderingComponentWithRAREiPlatformComponent:component withRAREiPlatformRenderingComponent:rc withRARERenderableDataItem:item withInt:row withBoolean:NO withBoolean:NO withRAREColumn:col withRARERenderableDataItem:rowItem];
    (void) [((id<RAREiPlatformComponent>) nil_chk([((id<RAREiPlatformRenderingComponent>) nil_chk(rc)) getComponentWithJavaLangCharSequence:text withRARERenderableDataItem:item])) getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
    ((RAREUIDimension *) nil_chk(size))->height_ = [JavaLangMath maxWithFloat:size->height_ withFloat:minHeight];
  }
}

+ (void)calculateListSizeWithJavaUtilList:(id<JavaUtilList>)list
               withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
            withRAREiPlatformItemRenderer:(id<RAREiPlatformItemRenderer>)renderer
                           withRAREColumn:(RAREColumn *)col
                      withRAREUIDimension:(RAREUIDimension *)size
                                  withInt:(int)maxItems
                                  withInt:(int)maxWidth
                                  withInt:(int)minHeight {
  float width = 0;
  float height = 0;
  int len = [((id<JavaUtilList>) nil_chk(list)) size];
  if (maxItems == -1) {
    maxItems = [RARETableHelper getMaxRowsForSizeCalculation];
  }
  if (maxItems < 1) {
    maxItems = len;
  }
  len = [JavaLangMath minWithInt:len withInt:maxItems];
  for (int i = 0; i < len; i++) {
    [RARETableHelper calculateItemSizeWithRAREiPlatformComponent:component withRAREiPlatformItemRenderer:renderer withRAREColumn:col withRARERenderableDataItem:[list getWithInt:i] withInt:i withRARERenderableDataItem:nil withRAREUIDimension:size withInt:maxWidth withInt:minHeight];
    width = [JavaLangMath maxWithFloat:width withFloat:((RAREUIDimension *) nil_chk(size))->width_];
    height += size->height_;
  }
  ((RAREUIDimension *) nil_chk(size))->width_ = width;
  size->height_ = height;
}

+ (int)calculateRowHeightWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
                      withRAREiPlatformItemRenderer:(id<RAREiPlatformItemRenderer>)renderer
                     withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)tm
                                            withInt:(int)row
                                withRAREColumnArray:(IOSObjectArray *)columns
                                        withBoolean:(BOOL)preferred
                                            withInt:(int)minHeight
                                       withIntArray:(IOSIntArray *)viewPositions {
  RARERenderableDataItem *rowItem = [((id<RAREiPlatformListDataModel>) nil_chk(tm)) getWithInt:row];
  RARERenderableDataItem *item;
  int len = (int) [((IOSObjectArray *) nil_chk(columns)) count];
  float height = 0;
  RAREUIDimension *size = [[RAREUIDimension alloc] init];
  RAREColumn *c;
  for (int i = 0; i < len; i++) {
    c = IOSObjectArray_Get(columns, i);
    if ([((RAREColumn *) nil_chk(c)) isVisible]) {
      item = [((RARERenderableDataItem *) nil_chk(rowItem)) getItemExWithInt:i];
      if (item != nil) {
        int w;
        int span = [item getColumnSpan];
        if (span == -1) {
          span = len - i;
        }
        else if (span == 0) {
          span = 1;
        }
        if (preferred) {
          w = 0;
        }
        else {
          w = [RARETableHelper getSpanWidthWithInt:i withInt:span withRAREColumnArray:columns withIntArray:viewPositions];
        }
        [RARETableHelper calculateItemSizeWithRAREiPlatformComponent:component withRAREiPlatformItemRenderer:renderer withRAREColumn:c withRARERenderableDataItem:item withInt:row withRARERenderableDataItem:rowItem withRAREUIDimension:size withInt:w withInt:minHeight];
        i += (span - 1);
      }
    }
    if (size->height_ > height) {
      height = size->height_;
    }
  }
  return (int) [JavaLangMath ceilWithDouble:height];
}

+ (RAREUIColor *)getBottomMarginColorWithRAREUIColor:(RAREUIColor *)marginColor {
  RAREUIColor *bottomMarginColor = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.TableHeader.bottomMarginColor"];
  if (bottomMarginColor == nil) {
    bottomMarginColor = marginColor;
  }
  return bottomMarginColor;
}

+ (RAREPaintBucket *)getDefaultPainterWithRAREUIColor:(RAREUIColor *)bg {
  RAREPaintBucket *pb = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getPaintBucketWithNSString:@"Rare.TableHeader.background"];
  if (pb != nil) {
    return pb;
  }
  RAREUIColor *c = (bg == nil) ? [RAREColorUtils getBackground] : bg;
  RAREUIBackgroundPainter *bp = (RAREUIBackgroundPainter *) check_class_cast([((RAREUIBackgroundPainter *) nil_chk([RAREUIBackgroundPainter BGCOLOR_GRADIENT_PAINTER_MID])) clone], [RAREUIBackgroundPainter class]);
  [((RAREUIBackgroundPainter *) nil_chk(bp)) setBackgroundColorWithRAREUIColor:c];
  ;
  pb = [[RAREPaintBucket alloc] initWithRAREiBackgroundPainter:bp];
  if (bg == [RAREColorUtils getBackground]) {
    [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) putWithNSString:@"Rare.TableHeader.background" withId:pb];
  }
  return pb;
}

+ (int)getDefaultPreferredRows {
  if (RARETableHelper_defaultPreferredRows_ == 0) {
    RARETableHelper_defaultPreferredRows_ = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIntWithNSString:@"Rare.List.defaultPreferredRows" withInt:1];
  }
  return RARETableHelper_defaultPreferredRows_;
}

+ (RAREUIColor *)getMarginColor {
  RAREUIColor *marginColor = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.TableHeader.marginColor"];
  if (marginColor == nil) {
    marginColor = [RAREaUILineBorder getDefaultLineColor];
  }
  return marginColor;
}

+ (int)getMaxRowsForSizeCalculation {
  if (RARETableHelper_maxRowsForSizeCalculation_ == -1) {
    RARETableHelper_maxRowsForSizeCalculation_ = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIntWithNSString:@"Rare.List.maxRowsForSizeCalculation" withInt:100];
  }
  return RARETableHelper_maxRowsForSizeCalculation_;
}

+ (int)getMinimumListHeightWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)list
                                              withInt:(int)minVisibleRowCount
                                              withInt:(int)rowHeight {
  int h = rowHeight;
  float len = (minVisibleRowCount == 0) ? 1 : minVisibleRowCount;
  if (len == 0) {
    len = 1;
  }
  if (h < 1) {
    h = [RAREScreenUtils lineHeightWithRAREiPlatformComponent:list];
  }
  h *= len;
  return h;
}

+ (int)getPreferredListHeightWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)list
                                                withInt:(int)visibleRowCount
                                                withInt:(int)rowHeight
                                                withInt:(int)numberOfRows {
  int h = rowHeight;
  float len = visibleRowCount;
  if (len == 0) {
    len = [RARETableHelper getDefaultPreferredRows];
    if ((numberOfRows < len) && (numberOfRows > 0)) {
      len = numberOfRows;
    }
  }
  if (len == 0) {
    len = 1;
  }
  if (h < 1) {
    h = [RAREScreenUtils lineHeightWithRAREiPlatformComponent:list];
  }
  h *= len;
  return h;
}

+ (RAREPaintBucket *)getPressedPainterWithRAREUIColor:(RAREUIColor *)bg {
  RAREPaintBucket *pb = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getPaintBucketWithNSString:@"Rare.TableHeader.pressedBackground"];
  if (pb == nil) {
    RAREUIColor *c = (bg == nil) ? [RAREColorUtils getBackground] : bg;
    RAREUIBackgroundPainter *bp = (RAREUIBackgroundPainter *) check_class_cast([((RAREUIBackgroundPainter *) nil_chk([RAREUIBackgroundPainter BGCOLOR_GRADIENT_PAINTER_DK_DK])) clone], [RAREUIBackgroundPainter class]);
    [((RAREUIBackgroundPainter *) nil_chk(bp)) setBackgroundColorWithRAREUIColor:c];
    ;
    pb = [[RAREPaintBucket alloc] initWithRAREiBackgroundPainter:bp];
    if (c == [RAREColorUtils getBackground]) {
      [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) putWithNSString:@"Rare.TableHeader.pressedBackground" withId:pb];
    }
  }
  return pb;
}

+ (int)getSpanWidthWithInt:(int)start
                   withInt:(int)span
       withRAREColumnArray:(IOSObjectArray *)columns
              withIntArray:(IOSIntArray *)viewPositions {
  int width = 0;
  int len = (int) [((IOSObjectArray *) nil_chk(columns)) count];
  if (span == -1) {
    span = len;
  }
  span += start;
  if (span > len) {
    span = len;
  }
  float d = [RAREScreenUtils PLATFORM_PIXELS_1];
  while (start < span) {
    RAREColumn *c;
    if (viewPositions != nil) {
      c = IOSObjectArray_Get(columns, IOSIntArray_Get(viewPositions, start++));
    }
    else {
      c = IOSObjectArray_Get(columns, start++);
    }
    if ([((RAREColumn *) nil_chk(c)) isVisible]) {
      width += [c getWidth] + d;
    }
  }
  if (width > 0) {
    width -= d;
  }
  return width;
}

+ (int)getPreferredSpanWidthWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                               withInt:(int)tableWidth
                                               withInt:(int)start
                                               withInt:(int)span
                                   withRAREColumnArray:(IOSObjectArray *)columns
                                          withIntArray:(IOSIntArray *)viewPositions {
  int width = 0;
  int len = (int) [((IOSObjectArray *) nil_chk(columns)) count];
  if (span == -1) {
    span = len;
  }
  span += start;
  if (span > len) {
    span = len;
  }
  float d = [RAREScreenUtils PLATFORM_PIXELS_1];
  while (start < span) {
    RAREColumn *c;
    if (viewPositions != nil) {
      c = IOSObjectArray_Get(columns, IOSIntArray_Get(viewPositions, start++));
    }
    else {
      c = IOSObjectArray_Get(columns, start++);
    }
    if ([((RAREColumn *) nil_chk(c)) isVisible]) {
      width += [c calculatePreferedWidthWithRAREiPlatformComponent:comp withFloat:tableWidth] + d;
    }
  }
  if (width > 0) {
    width -= d;
  }
  return width;
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getSubListWithJavaUtilList:withInt:withInt:withRAREUTiFilterableList:", NULL, "LRAREUTiFilterableList", 0x9, NULL },
    { "getBottomMarginColorWithRAREUIColor:", NULL, "LRAREUIColor", 0x9, NULL },
    { "getDefaultPainterWithRAREUIColor:", NULL, "LRAREPaintBucket", 0x9, NULL },
    { "getMarginColor", NULL, "LRAREUIColor", 0x9, NULL },
    { "getPressedPainterWithRAREUIColor:", NULL, "LRAREPaintBucket", 0x9, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "maxRowsForSizeCalculation_", NULL, 0xa, "I" },
    { "computeSizeRenderer_", NULL, 0xa, "LRAREUILabelRenderer" },
    { "defaultPreferredRows_", NULL, 0xa, "I" },
  };
  static J2ObjcClassInfo _RARETableHelper = { "TableHelper", "com.appnativa.rare.ui.table", NULL, 0x1, 5, methods, 3, fields, 0, NULL};
  return &_RARETableHelper;
}

@end
