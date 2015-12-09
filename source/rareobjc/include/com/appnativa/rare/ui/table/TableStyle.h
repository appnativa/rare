//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/ui/table/TableStyle.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARETableStyle_H_
#define _RARETableStyle_H_

@class RAREPaintBucket;
@class RARETableStyle_BackgroundHighlightEnum;
@class RAREUIColor;
@class RAREUICursor;
@class RAREUIFont;
@class RAREUIStroke;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "java/lang/Enum.h"

@interface RARETableStyle : NSObject < NSCopying > {
 @public
  RAREUIStroke *gridLineStroke_;
  int headerHotspotSize_;
  BOOL showHorizontalLines_;
  BOOL showVerticalLines_;
  BOOL hiliteSortColumn_;
  BOOL fixedRowSize_;
  BOOL extendBackgroundRendering_;
  BOOL columnSortingAllowed_;
  BOOL columnSelectionAllowed_;
  BOOL columnResizingAllowed_;
  BOOL columnReorderingAllowed_;
  RARETableStyle_BackgroundHighlightEnum *backgroundHilite_;
  RAREUIColor *backgroundHiliteColor_;
  BOOL colHeaderHotspotsSupported_;
  RAREUIColor *gridColor_;
  NSString *headerAction_;
  RAREUIFont *headerFont_;
  RAREUIColor *headerForeground_;
  RAREUIColor *headerMarginColor_;
  RAREUIColor *headerBottomMarginColor_;
  NSString *headerHotspotAction_;
  RAREUICursor *headerHotspotCursor_;
  id<RAREiPlatformIcon> headerHotspotIcon_;
  RAREPaintBucket *headerCellPainter_;
  RAREPaintBucket *headerFillerPainter_;
  BOOL rowHeaderFooterSelectionPainted_;
  BOOL showFocusRectangle_;
  RAREUIColor *sortColumnHiliteColor_;
}

- (id)init;
- (RARETableStyle *)copy__ OBJC_METHOD_FAMILY_NONE;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RARETableStyle *)other;
@end

J2OBJC_FIELD_SETTER(RARETableStyle, gridLineStroke_, RAREUIStroke *)
J2OBJC_FIELD_SETTER(RARETableStyle, backgroundHilite_, RARETableStyle_BackgroundHighlightEnum *)
J2OBJC_FIELD_SETTER(RARETableStyle, backgroundHiliteColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARETableStyle, gridColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARETableStyle, headerAction_, NSString *)
J2OBJC_FIELD_SETTER(RARETableStyle, headerFont_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RARETableStyle, headerForeground_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARETableStyle, headerMarginColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARETableStyle, headerBottomMarginColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARETableStyle, headerHotspotAction_, NSString *)
J2OBJC_FIELD_SETTER(RARETableStyle, headerHotspotCursor_, RAREUICursor *)
J2OBJC_FIELD_SETTER(RARETableStyle, headerHotspotIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARETableStyle, headerCellPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RARETableStyle, headerFillerPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RARETableStyle, sortColumnHiliteColor_, RAREUIColor *)

typedef RARETableStyle ComAppnativaRareUiTableTableStyle;

typedef enum {
  RARETableStyle_BackgroundHighlight_ROW = 0,
  RARETableStyle_BackgroundHighlight_COLUMN = 1,
} RARETableStyle_BackgroundHighlight;

@interface RARETableStyle_BackgroundHighlightEnum : JavaLangEnum < NSCopying > {
}
+ (RARETableStyle_BackgroundHighlightEnum *)ROW;
+ (RARETableStyle_BackgroundHighlightEnum *)COLUMN;
+ (IOSObjectArray *)values;
+ (RARETableStyle_BackgroundHighlightEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RARETableStyle_H_
