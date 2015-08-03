//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/Column.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREColumn_H_
#define _RAREColumn_H_

@class IOSClass;
@class RAREColumn_RenderDetailEnum;
@class RAREPaintBucket;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RAREScreenUtils_UnitEnum;
@class RAREUIFont;
@class RAREUIPopupMenu;
@class SPOTPrintableString;
@class SPOTSet;
@protocol JavaLangCharSequence;
@protocol RAREiActionListener;
@protocol RAREiPlatformCellEditingComponent;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformRenderingComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "java/lang/Enum.h"

#define RAREColumn_FOOTER 2
#define RAREColumn_FOOTER_INDEX 4
#define RAREColumn_FOOTER_INDEX_NORMAL 8
#define RAREColumn_FOOTER_NORMAL 6
#define RAREColumn_HEADER 1
#define RAREColumn_HEADER_INDEX 3
#define RAREColumn_HEADER_INDEX_NORMAL 7
#define RAREColumn_HEADER_NORMAL 5
#define RAREColumn_INDEX_NORMAL 9
#define RAREColumn_NORMAL 0
#define RAREColumn_WIDTH_TYPE_MAX 2
#define RAREColumn_WIDTH_TYPE_MIN 1
#define RAREColumn_WIDTH_TYPE_NORMAL 0

@interface RAREColumn : RARERenderableDataItem {
 @public
  float maxWidth_;
  float minWidth_;
  float preferedWidth_;
  BOOL sortable_;
  BOOL sizeFixed_;
  BOOL showable_;
  BOOL moveable_;
  BOOL hideable_;
  BOOL wordWrap_;
  BOOL headerWordWrap_;
  BOOL overrideSelectionBackground_;
  NSString *categoryName_;
  NSString *columnName_;
  RAREScreenUtils_UnitEnum *widthUnit_;
  int firstTable_;
  id<RAREiActionListener> headerActionListener_;
  RAREUIFont *headerFont_;
  SPOTSet *menuSet_;
  RAREUIPopupMenu *popupMenu_;
  id<JavaLangCharSequence> columnTitle_;
  BOOL indexColumn_;
  BOOL headerColumn_;
  BOOL footerColumn_;
  int renderType_;
  RARERenderableDataItem_VerticalAlignEnum *headerVerticalAlign_;
  RARERenderableDataItem_IconPositionEnum *headerIconPosition_;
  RARERenderableDataItem_HorizontalAlignEnum *headerHorizontalAlign_;
  RAREColumn_RenderDetailEnum *renderDetail_;
  id<RAREiPlatformRenderingComponent> cellRenderer_;
  id<RAREiPlatformRenderingComponent> headerCellRenderer_;
  IOSClass *dataClass_;
  id<RAREiPlatformIcon> headerIcon_;
  RAREPaintBucket *headerPainter_;
  RAREPaintBucket *headerRolloverPainter_;
  RAREPaintBucket *headerSelectionPainter_;
  RAREPaintBucket *itemPainter_;
  RAREPaintBucket *itemSelectionPainter_;
  RAREScreenUtils_UnitEnum *maxWidthUnit_;
  RAREScreenUtils_UnitEnum *minWidthUnit_;
  float headerIconScaleFactor_;
  BOOL scaleHeaderIcon_Column_;
  id<RAREiPlatformCellEditingComponent> cellEditor_;
}

+ (int)FOOTER;
+ (int)FOOTER_INDEX;
+ (int)FOOTER_INDEX_NORMAL;
+ (int)FOOTER_NORMAL;
+ (int)HEADER;
+ (int)HEADER_INDEX;
+ (int)HEADER_INDEX_NORMAL;
+ (int)HEADER_NORMAL;
+ (int)INDEX_NORMAL;
+ (int)NORMAL;
+ (BOOL)defaultHeaderWordWrap;
+ (BOOL *)defaultHeaderWordWrapRef;
+ (int)WIDTH_TYPE_MAX;
+ (int)WIDTH_TYPE_MIN;
+ (int)WIDTH_TYPE_NORMAL;
- (float)getIconScaleFactor;
- (BOOL)isScaleHeaderIcon;
- (void)setScaleHeaderIconWithBoolean:(BOOL)scale_
                            withFloat:(float)scaleFactor;
- (float)getHeaderIconScaleFactor;
- (id)init;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)title
                           withInt:(int)type;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)title
                           withInt:(int)type
                            withId:(id)data;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)title
                            withId:(id)value
                           withInt:(int)type
                            withId:(id)data
             withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (int)calculateMinimumWidthWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                             withFloat:(float)tableWidth;
- (int)calculatePreferedWidthWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                              withFloat:(float)tableWidth;
- (id)clone;
- (void)convertWithRAREiWidget:(id<RAREiWidget>)w
    withRARERenderableDataItem:(RARERenderableDataItem *)di;
- (BOOL)isEqual:(id)o;
- (id)convertWithRAREiWidget:(id<RAREiWidget>)w
                withNSString:(NSString *)value;
- (id)convertWithRAREiWidget:(id<RAREiWidget>)w
  withRARERenderableDataItem:(RARERenderableDataItem *)di
                withNSString:(NSString *)value;
- (RARERenderableDataItem *)convertToItem;
+ (int)fromSPOTTypeWithInt:(int)type;
- (void)setupConverterWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)name
              withSPOTPrintableString:(SPOTPrintableString *)e;
- (void)setupConverterWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)name
                         withNSString:(NSString *)context
                               withId:(id)min
                               withId:(id)max
                          withBoolean:(BOOL)convertRange;
- (id<JavaLangCharSequence>)toCharSequenceWithRAREiWidget:(id<RAREiWidget>)w
                               withRARERenderableDataItem:(RARERenderableDataItem *)di;
- (id<JavaLangCharSequence>)toCharSequenceWithRAREiWidget:(id<RAREiWidget>)w
                               withRARERenderableDataItem:(RARERenderableDataItem *)di
                                                   withId:(id)value;
- (NSString *)description;
- (void)setCellRendererWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)r;
- (void)setCellRendererWithNSString:(NSString *)cls;
- (void)setCellEditorWithRAREiPlatformCellEditingComponent:(id<RAREiPlatformCellEditingComponent>)editor;
- (id<RAREiPlatformCellEditingComponent>)getCellEditor;
- (void)setColumnMaxWidthWithNSString:(NSString *)s;
- (void)setColumnMenuWithSPOTSet:(SPOTSet *)menu;
- (void)setColumnMenuWithRAREUIPopupMenu:(RAREUIPopupMenu *)menu;
- (void)setColumnMinWidthWithNSString:(NSString *)s;
- (void)setColumnTitleWithJavaLangCharSequence:(id<JavaLangCharSequence>)columnTitle;
- (void)setColumnWidthWithSPOTPrintableString:(SPOTPrintableString *)width;
- (void)setColumnWidthWithNSString:(NSString *)s;
- (void)setDateTimeFormatWithNSString:(NSString *)format;
- (void)setHeaderActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)setHeaderFontWithRAREUIFont:(RAREUIFont *)headerFont;
- (void)setHeaderHorizontalAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)align;
- (void)setHeaderIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)headerIcon;
- (void)setHeaderIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)position;
- (void)setHeaderPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setHeaderRollOverPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setHeaderSelectionPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setHeaderVerticalAlignmentWithRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)align;
- (void)setItemPainterWithRAREPaintBucket:(RAREPaintBucket *)itemPainter;
- (void)setItemSelectionPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setJavaClassWithIOSClass:(IOSClass *)cls;
- (void)setNumberFormatWithNSString:(NSString *)format;
- (void)setRenderDetailWithRAREColumn_RenderDetailEnum:(RAREColumn_RenderDetailEnum *)renderDetail;
- (void)setRenderTypeWithInt:(int)renderType;
- (id<RAREiPlatformRenderingComponent>)getCellRenderer;
- (id<JavaLangCharSequence>)getColumnTitle;
- (id<RAREiActionListener>)getHeaderActionListener;
- (id<RAREiPlatformComponentPainter>)getHeaderComponentPainter;
- (RAREUIFont *)getHeaderFont;
- (RARERenderableDataItem_HorizontalAlignEnum *)getHeaderHorizontalAlignment;
- (id<RAREiPlatformIcon>)getHeaderIcon;
- (RARERenderableDataItem_IconPositionEnum *)getHeaderIconPosition;
- (RAREPaintBucket *)getHeaderPainter;
- (RAREPaintBucket *)getHeaderRollOverPainter;
- (RAREPaintBucket *)getHeaderSelectionPainter;
- (RARERenderableDataItem_VerticalAlignEnum *)getHeaderVerticalAlignment;
- (RAREPaintBucket *)getItemPainter;
- (RAREPaintBucket *)getItemPainterWithBoolean:(BOOL)selected;
- (RAREPaintBucket *)getItemSelectionPainter;
- (RAREUIPopupMenu *)getPopupMenuWithRAREiWidget:(id<RAREiWidget>)context;
- (RAREColumn_RenderDetailEnum *)getRenderDetail;
- (int)getRenderType;
- (BOOL)hasPopupMenu;
- (BOOL)isFooterColumn;
- (BOOL)isHeaderColumn;
- (BOOL)isHeaderOrFooterColumn;
- (BOOL)isIndexColumn;
- (BOOL)isRenderNormal;
- (void)dealloc;
- (void)setColumnWidthWithNSString:(NSString *)s
                           withInt:(int)type;
- (id<RAREiPlatformRenderingComponent>)getHeaderCellRenderer;
- (void)setHeaderCellRendererWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)headerCellRenderer;
- (void)copyAllFieldsTo:(RAREColumn *)other;
@end

J2OBJC_FIELD_SETTER(RAREColumn, categoryName_, NSString *)
J2OBJC_FIELD_SETTER(RAREColumn, columnName_, NSString *)
J2OBJC_FIELD_SETTER(RAREColumn, widthUnit_, RAREScreenUtils_UnitEnum *)
J2OBJC_FIELD_SETTER(RAREColumn, headerActionListener_, id<RAREiActionListener>)
J2OBJC_FIELD_SETTER(RAREColumn, headerFont_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREColumn, menuSet_, SPOTSet *)
J2OBJC_FIELD_SETTER(RAREColumn, popupMenu_, RAREUIPopupMenu *)
J2OBJC_FIELD_SETTER(RAREColumn, columnTitle_, id<JavaLangCharSequence>)
J2OBJC_FIELD_SETTER(RAREColumn, headerVerticalAlign_, RARERenderableDataItem_VerticalAlignEnum *)
J2OBJC_FIELD_SETTER(RAREColumn, headerIconPosition_, RARERenderableDataItem_IconPositionEnum *)
J2OBJC_FIELD_SETTER(RAREColumn, headerHorizontalAlign_, RARERenderableDataItem_HorizontalAlignEnum *)
J2OBJC_FIELD_SETTER(RAREColumn, renderDetail_, RAREColumn_RenderDetailEnum *)
J2OBJC_FIELD_SETTER(RAREColumn, cellRenderer_, id<RAREiPlatformRenderingComponent>)
J2OBJC_FIELD_SETTER(RAREColumn, headerCellRenderer_, id<RAREiPlatformRenderingComponent>)
J2OBJC_FIELD_SETTER(RAREColumn, dataClass_, IOSClass *)
J2OBJC_FIELD_SETTER(RAREColumn, headerIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RAREColumn, headerPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREColumn, headerRolloverPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREColumn, headerSelectionPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREColumn, itemPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREColumn, itemSelectionPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREColumn, maxWidthUnit_, RAREScreenUtils_UnitEnum *)
J2OBJC_FIELD_SETTER(RAREColumn, minWidthUnit_, RAREScreenUtils_UnitEnum *)
J2OBJC_FIELD_SETTER(RAREColumn, cellEditor_, id<RAREiPlatformCellEditingComponent>)

typedef RAREColumn ComAppnativaRareUiColumn;

typedef enum {
  RAREColumn_RenderDetail_AUTO = 0,
  RAREColumn_RenderDetail_ALL = 1,
  RAREColumn_RenderDetail_TEXT_AND_ICON = 2,
  RAREColumn_RenderDetail_TEXT_ONLY = 3,
  RAREColumn_RenderDetail_ICON_ONLY = 4,
} RAREColumn_RenderDetail;

@interface RAREColumn_RenderDetailEnum : JavaLangEnum < NSCopying > {
}
+ (RAREColumn_RenderDetailEnum *)AUTO;
+ (RAREColumn_RenderDetailEnum *)ALL;
+ (RAREColumn_RenderDetailEnum *)TEXT_AND_ICON;
+ (RAREColumn_RenderDetailEnum *)TEXT_ONLY;
+ (RAREColumn_RenderDetailEnum *)ICON_ONLY;
+ (IOSObjectArray *)values;
+ (RAREColumn_RenderDetailEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREColumn_H_